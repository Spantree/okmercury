package co.okmercury

import grails.converters.JSON

import org.bson.types.ObjectId

class QuestionController {
	QuestionService questionService
	AnswerService answerService
	
	def create() {
		ObjectId id = new ObjectId()
		redirect(uri: "/question/${id}", params: params)
	}
	
	def edit() {
		Question question = Question.findByAssignedId(params.id)
		[
			id: params.id,
			questionText: question?.question ?: '',
			options: question?.options?.collect { it.answer } ?: [],
			success: params.success
		]
	}
	
	def save() {
		def json = request.JSON
		String id = params.id
		Question question = questionService.updateQuestion(id, json.questionText, json.possibleAnswers, session.user)
		if(question.errors.hasErrors()) {
			response.status = 500
			render ([success: false, errors: question.errors.allErrors] as JSON).toString()
		} else {
			render ([success: true] as JSON).toString()
		}
	}

	def list() {
		[questions: Question.list()]
	}

	def answer() {
		Question question = Question.get(new ObjectId(params.questionId))
		render(view: 'answer', model: [
			user: session.user,
			question: question,
			options: question.options.sort { it.order },
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
	
	def nextUnansweredQuestionForUser() {
		ObjectId questionId = questionService.getNextUnansweredQuestionForUser(session.user)
		if(questionId) {
			redirect(uri: "/user/${session.user.id}/question/${questionId}")
		} else {
			render(view: 'done')
		}
	}
}
