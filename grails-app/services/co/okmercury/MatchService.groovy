package co.okmercury

import com.mongodb.AggregationOutput
import com.mongodb.DBCollection;

class MatchService {
	
	def mongo
	
	DBCollection getQuestionMatchCollection() {
		mongo.getDB('okmercury').getCollection('questionMatch')
	}
	
	def generateQuestionMatches(Answer answerA) {
		User userA = answerA.user;
		Question questionAnswered = answerA.question
		
		Answer.findAll{ question == questionAnswered && user != userA }.each { Answer answerB ->
			User userB = answerB.person
			
			int answerAWeight = answerA.importance.getWeight()
			int answerBWeight = answerB.importance.getWeight()
			
			boolean acceptableForUserA = answerA.acceptableAnswers.contains(answerB.userAnswer)
			boolean acceptableForUserB = answerB.acceptableAnswers.contains(answerA.userAnswer)
			
			QuestionMatch qMatch = new QuestionMatch();
			qMatch.userA = userA
			qMatch.userB = userB
			qMatch.pointsPossibleForUserA = answerA.importance.getWeight()
			
			if(acceptableForUserA) {
				qMatch.scoreForUserA = qMatch.pointsPossibleForUserA
			}
			else {
				qMatch.scoreForUserA = 0
			}
			
			qMatch.pointsPossibleForUserB = answerB.importance.getWeight()
			
			if(acceptableForUserB) {
				qMatch.scoreForUserB = qMatch.pointsPossibleForUserB
			}
			else {
				qMatch.scoreForUserB = 0
			}
			
			qMatch.save()
		}
	}
	
	def generateUserMatches(User principalUser) {
		
		User.findAll().each { User matchUser ->
			
			AggregationOutput aggOut1 = questionMatchCollection.aggregate(
				[ $group :
					[ principalPoints : 
						[ $sum : "$scoreForUserA" ],
					principalPointsPossible :
						[ $sum : "$pointsPossibleForUserA" ],
					matchPoints :
						[ $sum : "$scoreForUserB" ],
					matchPointsPossible :
						[ $sum : "$pointsPossibleForUserB" ],
					totalQuestions :
						[ $sum : 1 ]
					]
				], 
				[ $match : 
					[ 
						"userA.id" : principalUser.id, 
						"userB.id" : matchUser.id
					] 
				] 
			)
			
			AggregationOutput aggOut2 = questionMatchCollection.aggregate(
				[ $group :
					[ principalPoints :
						[ $sum : "$scoreForUserB" ],
					principalPointsPossible :
						[ $sum : "$pointsPossibleForUserB" ],
					matchPoints :
						[ $sum : "$scoreForUserA" ],
					matchPointsPossible :
						[ $sum : "$pointsPossibleForUserA" ],
					totalQuestions :
						[ $sum : 1 ]
					]
				],
				[ $match :
					[
						"userA.id" : matchUser.id,
						"userB.id" : principalUser.id
					]
				]
			)
			
			UserMatch um = new UserMatch()
			um.principalUser = principalUser
			um.matchUser = matchUser
			
			um.principalPoints = aggOut1.results()[0].principalPoints + aggOut2.results()[0].principalPoints
			um.principalPointsPossible = aggOut1.results()[0].principalPointsPossible + aggOut2.results()[0].principalPointsPossible
			
			um.matchPoints = aggOut1.results()[0].matchPoints + aggOut2.results()[0].matchPoints
			um.matchPointsPossible = aggOut1.results()[0].matchPointsPossible + aggOut2.results()[0].matchPointsPossible
			
			if( um.principalPointsPossible > 0 ) {
				um.principalPercentageScore = um.principalPoints/um.principalPointsPossible * 100.0f
			}
			else {
				um.principalPercentageScore = 0.0f
			}
			
			if( um.matchPercentageScore > 0 ) {
				um.matchPercentageScore = um.matchPoints/um.matchPointsPossible * 100.0f
			}
			else {
				um.matchPercentageScore = 0.0f
			}
			
			um.overallScore = Math.sqrt(um.principalPercentageScore * um.matchPercentageScore)
			
			um.save()
			
		}
		
		
	
	}
}
