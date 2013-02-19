package co.okmercury

import org.bson.types.ObjectId

class TestUserMatchController {
	def questionService
	def answerService

	def testQuestions = [
		[
			qid: "${new ObjectId()}",
			questionText : "Question1?",
			optionStrings : ["Q1Option1","Q1Option2","Q1Option3"]
		],
		[
			qid: "${new ObjectId()}",
			questionText : "Question2?",
			optionStrings : ["Q2Option1","Q2Option2","Q2Option3"]
		]
	]


	def test = {
		def questions = []
		User user1 = User.first()
		User user2 = User.last()

		testQuestions.each{
			Question q = questionService.updateQuestion( it.qid, it.questionText, it.optionStrings )
			
			def user1AnswerId = "${q.options[1].id}"
			def user2AnswerId = "${q.options[0].id}"
			
			def user1AcceptableAnswers = ["${q.options[0].id}", "${q.options[2].id}"]
			def user2AcceptableAnswers = ["${q.options[1].id}", "${q.options[2].id}"]
			
			answerService.saveAnswer(user1, it.qid, user1AnswerId, user1AcceptableAnswers, "Very important")
			answerService.saveAnswer(user2, it.qid, user2AnswerId, user2AcceptableAnswers, "Very important")
		}
		
		
		
		
		render("Done")
	}
}
