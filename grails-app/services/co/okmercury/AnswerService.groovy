package co.okmercury

import org.bson.types.ObjectId

class AnswerService {
	ImportanceService importanceService
	QuestionService questionService
	MatchService matchService
	
	void addUserToQuestionsAnswered(User user, Question question) {
		questionService.collection.update([_id: question.id], ['$addToSet': [userIdsThatHaveAnswered: user.id]])
	}
	
	Answer saveAnswerSkipped(User user, String questionId, String userAnswerId) {
		Question question = Question.get(questionId)
		Answer answer = Answer.findByUserAndQuestion(user, question)
		if(!answer) {
			answer = new Answer(user: user, question: question)
		}
		answer.skipped = true;
		answer.acceptableAnswerIds = new HashSet<ObjectId>()
		answer.lastModifiedDate = new Date()
		
		answer.save(flush: true)
		if(answer.errors.hasErrors()) {
			log.error "Error saving answer for user ${user.id}: ${answer.errors.allErrors}"
		} else {
			log.info "Successfully saved answer for user ${user.id}"
			addUserToQuestionsAnswered(user, question)
		}
		return answer
	}
	
	Answer saveAnswer(User user, String questionId, String userAnswerId, def acceptableQuestionOptionIds, String importanceName, String userAnswerExplanation = null) {
		Question question = Question.get(questionId)
		Answer answer = Answer.findByUserAndQuestion(user, question)
		if(!answer) {
			answer = new Answer(user: user, question: question)
		}
		answer.userAnswer = QuestionOption.get(userAnswerId)
		if(answer.acceptableAnswerIds == null) {
			answer.acceptableAnswerIds = new HashSet<ObjectId>()
		}
		answer.acceptableAnswerIds.clear()
		answer.acceptableAnswerIds.addAll(acceptableQuestionOptionIds.collect { new ObjectId(it) })
		answer.importance = importanceService.getImportanceByName(importanceName)
		answer.lastModifiedDate = new Date()
		answer.userAnswerExplanation = userAnswerExplanation
		answer.skipped = false;
		answer.save(flush: true)
		if(answer.errors.hasErrors()) {
			log.error "Error saving answer for user ${user.id}: ${answer.errors.allErrors}"
		} else {
			log.info "Successfully saved answer for user ${user.id}"
			addUserToQuestionsAnswered(user, question)
			matchService.handleAnswer(answer.id)
		}
		return answer
	}
}
