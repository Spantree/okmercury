package co.okmercury

import groovy.transform.EqualsAndHashCode

import org.bson.types.ObjectId

@EqualsAndHashCode(includes=["id"])
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
