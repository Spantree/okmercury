package co.okmercury

import org.bson.types.ObjectId

class Answer {
	ObjectId id
	User user
	Question question
	QuestionOption userAnswer
	String userAnswerExplanation
	Set<ObjectId> acceptableAnswerIds = []
	Importance importance
	Date lastModifiedDate
}
