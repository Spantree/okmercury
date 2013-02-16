package co.okmercury

import org.bson.types.ObjectId

class QuestionOption {
	ObjectId id
	Question question
	String answer
	Float order
	
	static belongsTo = Question
}
