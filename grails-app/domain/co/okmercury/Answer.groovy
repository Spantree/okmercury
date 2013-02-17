package co.okmercury

import org.bson.types.ObjectId

class Answer {
	ObjectId id
	User user
	Question question
	QuestionOption userAnswer
	Set<ObjectId> acceptableAnswerIds = []
	Importance importance
	Date lastModifiedDate
	Boolean skipped = false
}
