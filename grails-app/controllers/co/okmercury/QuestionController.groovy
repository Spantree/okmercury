package co.okmercury

import grails.converters.JSON

import org.bson.types.ObjectId

class QuestionController {
	QuestionService questionService
	
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
		Question question = questionService.updateQuestion(id, json.questionText, json.possibleAnswers)
		if(question.errors.hasErrors()) {
			response.status = 500
			render ([success: false, errors: question.errros.allErrors] as JSON).toString()
		} else {
			render ([success: true] as JSON).toString()
		}
	}

	def list() {
		[questions: Question.list()]
	}
	
	def answer() {
		render(view: 'answer')
	}
}
