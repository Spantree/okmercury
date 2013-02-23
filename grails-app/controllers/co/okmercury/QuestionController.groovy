package co.okmercury

import grails.converters.JSON

import org.bson.types.ObjectId

class QuestionController {
	QuestionService questionService
	AnswerService answerService
	
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
		Question question = questionService.updateQuestion(id, json.questionText, json.possibleAnswers, session.user)
		if(question.errors.hasErrors()) {
			response.status = 500
			render ([success: false, errors: question.errors.allErrors] as JSON).toString()
		} else {
			render ([success: true] as JSON).toString()
		}
	}

	def list() {
		def answerMap = [:]

		Answer.where {
			user == session.user
		}.list().each { Answer answer ->
			if(answer.question) {
				answerMap[answer.question.id] = answer
			}
		}

		[questions: Question.list(), answerMap: answerMap]
	}

	def answer() {
		Question answerQuestion = Question.get(new ObjectId(params.questionId))
		User answerUser = User.get(new ObjectId(params.userId))
		Answer previousAnswer = Answer.find{ user == answerUser && question == answerQuestion }
		
		render(view: 'answer', model: [
			user: session.user,
			question: answerQuestion,
			previousAnswer: previousAnswer,
			options: answerQuestion.options.sort { it.order },
			importanceOptions: Importance.class.getEnumConstants()
		])
	}
	
	def saveAnswer() {
		def json = request.JSON
		Answer answer = answerService.saveAnswer(session.user, params.questionId, json.userAnswer, json.acceptableOptions, json.importance, json.userAnswerExplanation)
		if(answer.errors.hasErrors()) {
			response.status = 500
			render ([success: false, errors: answer.errors.allErrors] as JSON).toString()
		} else {
			render ([success: true] as JSON).toString()
		}
	}
	
	def skipAnswer() {
		def json = request.JSON
		Answer answer = answerService.saveAnswerSkipped(session.user, params.questionId, json.userAnswer)
		if(answer.errors.hasErrors()) {
			response.status = 500
			render ([success: false, errors: answer.errors.allErrors] as JSON).toString()
		} else {
			render ([success: true] as JSON).toString()
		}
	}
	
	def done() {
		render(view: 'done')
	}
	
	def nextUnansweredQuestionForUser() {
		ObjectId questionId = questionService.getNextUnansweredQuestionForUser(session.user)
		if(questionId) {
			redirect(uri: "/user/${session.user.id}/question/${questionId}")
		} else {
			redirect(uri: "/user/${session.user.id}/question/done")
		}
	}
}
