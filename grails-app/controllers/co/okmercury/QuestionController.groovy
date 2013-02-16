package co.okmercury

import org.bson.types.ObjectId

class QuestionController {
	def create() {
		ObjectId id = new ObjectId()
		redirect(uri: "/question/${id}")
	}
	
	def edit() {
		[id: params.id]
	}

	def list() {
		
	}
}
