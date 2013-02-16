package co.okmercury

import groovy.transform.EqualsAndHashCode

import org.bson.types.ObjectId

@EqualsAndHashCode(includes=["id"])
class QuestionOption {
	ObjectId id
	Question question
	String answer
	Float order
	
	static belongsTo = Question
}
