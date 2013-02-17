package co.okmercury

import java.util.concurrent.locks.ReentrantLock

import org.bson.types.ObjectId

import com.mongodb.AggregationOutput
import com.mongodb.BasicDBObject
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.DBObject
import com.mongodb.WriteConcern

class MatchService {
	UserService userService
	def mongo
	
	ReentrantLock questionHyperLock = new ReentrantLock()
	ReentrantLock userMatchHyperLock = new ReentrantLock()
	Map<Object, ReentrantLock> questionLocks = [:]
	Map<Object, ReentrantLock> userMatchLocks = [:]
	
	DBCollection getQuestionMatchCollection() {
		mongo.getDB('okmercury').getCollection('questionMatch')
	}
	
	DBCollection getUserMatchCollection() {
		mongo.getDB('okmercury').getCollection('userMatch')
	}
	
	ReentrantLock getLock(ReentrantLock hyperlock, Map<Object, ReentrantLock> lockMap, Object key) {
		hyperlock.lock()
		try {
			ReentrantLock lock = lockMap[key]
			if(!lock) {
				lock = new ReentrantLock()
				questionLocks[key] = lock
			}
			return lock
		} catch(Exception e) {
			return null
		} finally {
			hyperlock.unlock()
		}
	}
	
	List<Answer> getOtherUserAnswers(Answer answer) {
		return Answer.where {
			question == answer.question
			user != answer.user
			skipped != true
		}.list()
	}
	
	void upsertQuestionMatch(Answer answerA, Answer answerB) {
		AnswerScore aScore = new AnswerScore(answerA, answerB)
		AnswerScore bScore = new AnswerScore(answerB, answerA)
		
		DBObject criteria = [
			questionId: answerA.question.id,
			userAId: answerA.user.id,
			userBId: answerB.user.id
		] as BasicDBObject
		
		DBObject update = [$set: [
			scoreForUserA: aScore.points,
			pointsPossibleForUserA: aScore.pointsPossible,
			scoreForUserB: bScore.points,
			pointsPossibleForUserB: bScore.pointsPossible
		]] as BasicDBObject
		
		update['$set'].putAll(criteria)
		
		// perform an 'upsert' for the score
		questionMatchCollection.update(criteria, update, true, false, WriteConcern.SAFE)
	}
	
	Float getMarginOfError(int commonQuestions) {
		if(commonQuestions < 2)
			return 1
		else
			return (1F/commonQuestions.floatValue())
	}
	
	Float scorePercentage(int points, int pointsPossible) {
		return points.floatValue()/pointsPossible.floatValue()
	}
	
	void updateUserMatch(User principalUser, User matchUser, User userA) {
		String key = "${principalUser.id}->${matchUser.id}"
		ReentrantLock lock = getLock(userMatchHyperLock, userMatchLocks, key)
		lock.lock()
		try {
			User userB = (userA == principalUser ? matchUser : principalUser)
			
			DBObject match = [userAId: userA.id, userBId: userB.id] as BasicDBObject
			
			log.info "Match: ${match}"
			
			DBObject group = [
				_id: [userAId: '$userAId', userBId: '$userBId'],
				principalPoints: [$sum: '$scoreForUserA'],
				principalPointsPossible: [$sum : '$pointsPossibleForUserA'],
				matchPoints: [$sum: '$scoreForUserB'],
				matchPointsPossible: [$sum :'$pointsPossibleForUserB'],
				questionsInCommon: [$sum: 1]
			] as BasicDBObject
		
			AggregationOutput out = questionMatchCollection.aggregate([$match: match], [$group: group])
			
			Iterator<DBObject> resultsIterator = out.results().iterator()
			
			if(resultsIterator.hasNext()) {
				DBObject results = resultsIterator.next()
				
				Float marginOfError = getMarginOfError(results.questionsInCommon)
				Float principalPercentageScore = scorePercentage(results.principalPoints, results.principalPointsPossible)
				Float matchPercentageScore = scorePercentage(results.matchPoints, results.matchPointsPossible)
				Float overallScore = Math.min((1F-marginOfError), Math.sqrt(principalPercentageScore * matchPercentageScore))
				
				DBObject criteria = [principalUserId: principalUser.id, matchUserId: matchUser.id] as BasicDBObject
				
				DBObject update = [$set: [
					principalPoints: results.principalPoints,
					principalPointsPossible: results.principalPointsPossible,
					principalPercentageScore: principalPercentageScore,
					matchPoints: results.matchPoints,
					matchPointsPossible: results.matchPointsPossible,
					matchPercentageScore: matchPercentageScore,
					questionsInCommon: results.questionsInCommon,
					overallScore: overallScore
				]] as BasicDBObject
				
				update['$set'].putAll(criteria)
				
				log.info "criteria: ${criteria}"
				
				userMatchCollection.update(criteria, update, true, false, WriteConcern.SAFE)
			} else {
				log.error "No aggregation results found for user match ${key}"
			}
		} finally {
			lock.unlock()
		}
	}
	
	def handleAnswer(ObjectId answerId) {
		Answer answer = Answer.get(answerId)
		ReentrantLock lock = getLock(questionHyperLock, questionLocks, answer.question.id)
		lock.lock()
		try {
			List<Answer> otherAnswers = getOtherUserAnswers(answer)
			otherAnswers.each { Answer otherAnswer ->
				Answer answerA, answerB
				if(answer.user.id < otherAnswer.user.id) {
					answerA = answer
					answerB = otherAnswer
				} else {
					answerA = otherAnswer
					answerB = answer
				}
				upsertQuestionMatch(answerA, answerB)
				updateUserMatch(answerA.user, answerB.user, answerA.user)
				updateUserMatch(answerB.user, answerA.user, answerA.user)
			}
		} finally {
			lock.unlock()
		}
	}
	
	List<DBObject> getBestMatchesForUser(User user, String sortField = 'overallScore') {
		DBObject criteria = [principalUserId: user.id] as BasicDBObject
		DBObject sortMap = ["${sortField}": -1] as BasicDBObject
		return userMatchCollection.find(criteria).toArray()?.sort {
			println it
			Float v = it[sortField]
			return v != null ? -v : -Integer.MAX_VALUE
		}
	}
	
	DBObject getBestMatchForUser(User user) {
		List matches = getBestMatchesForUser(user)
		return matches ? matches[0] : null
	}
}
