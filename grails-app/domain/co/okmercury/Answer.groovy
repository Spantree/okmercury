package co.okmercury

class Answer {
	User user
	Question question
	QuestionOption userAnswer
	List<QuestionOption> acceptableAnswers
	Importance importance
	Date lastModifiedDate
}
