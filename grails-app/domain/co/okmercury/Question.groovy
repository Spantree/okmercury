package co.okmercury

import org.bson.types.ObjectId

class Question {
	ObjectId id
	ObjectId assignedId
	String question
	Set<QuestionOption> options = []
	Set<ObjectId> usersIdsThatHaveAnswered = []
	User createdBy
	Date createdDate
	Date lastModifiedDate
	
	static hasMany = [options: QuestionOption]
}
