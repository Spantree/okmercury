package co.okmercury

import grails.plugins.springsecurity.Secured
import grails.converters.JSON

import org.bson.types.ObjectId

class QuestionController {
	QuestionService questionService
	AnswerService answerService
	def springSecurityService
	
	def edit() {
		Question question = params.id? Question.findByAssignedId(params.id) : null
		[
			id: params.id,
			questionText: question?.question ?: '',
			options: question?.options?.collect { it.answer } ?: [],
			success: params.success
		]
	}
	
	def save() {
		def json = request.JSON
		String id = params.id ?: new ObjectId()
		Question question = questionService.updateQuestion(id, json.questionText, json.possibleAnswers, springSecurityService.getCurrentUser())
		if(question.errors.hasErrors()) {
			response.status = 500
			render ([success: false, errors: question.errors.allErrors] as JSON).toString()
		} else {
			render ([success: true] as JSON).toString()
		}
	}

	@Secured(['ROLE_USER'])
	def list() {
		def answerMap = [:]
		
		User loggedInUser = springSecurityService.getCurrentUser()

		Answer.where {
			user == loggedInUser
		}.list().each { Answer answer ->
			if(answer.question) {
				answerMap[answer.question.id] = answer
			}
		}

		[questions: Question.list(), answerMap: answerMap, user : loggedInUser]
	}

	def answer() {
		Question question = Question.get(new ObjectId(params.questionId))
		render(view: 'answer', model: [
			user: springSecurityService.getCurrentUser(),
			question: answerQuestion,
			previousAnswer: previousAnswer,
			options: answerQuestion.options.sort { it.order },
			importanceOptions: Importance.class.getEnumConstants()
		])
	}
	
	def saveAnswer() {
		def json = request.JSON
		Answer answer = answerService.saveAnswer(springSecurityService.getCurrentUser(), params.questionId, json.userAnswer, json.acceptableOptions, json.importance, json.userAnswerExplanation)
		if(answer.errors.hasErrors()) {
			response.status = 500
			render ([success: false, errors: answer.errors.allErrors] as JSON).toString()
		} else {
			render ([success: true] as JSON).toString()
		}
	}
	
	def skipAnswer() {
		def json = request.JSON
		User loggedInUser = springSecurityService.getCurrentUser()
		Answer answer = answerService.saveAnswerSkipped(loggedInUser, params.questionId, json.userAnswer)
		if(answer.errors.hasErrors()) {
			response.status = 500
			render ([success: false, errors: answer.errors.allErrors] as JSON).toString()
		} else {
			render ([success: true] as JSON).toString()
		}
	}
	
	def done() {
		render(view: 'done', model: [user : springSecurityService.getCurrentUser()])
	}
	
	def nextUnansweredQuestionForUser() {
		User loggedInUser = springSecurityService.getCurrentUser()
		ObjectId questionId = questionService.getNextUnansweredQuestionForUser(loggedInUser)
		if(questionId) {
			redirect(uri: "/user/${loggedInUser.id}/question/${questionId}")
		} else {
			redirect(uri: "/user/${loggedInUser.id}/question/done")
		}
	}
}
