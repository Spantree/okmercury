package co.okmercury

import org.bson.types.ObjectId

class TestUserMatchController {
	def questionService
	
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
		
		testQuestions.each{
			questions << questionService.updateQuestion( it.qid, it.questionText, it.optionStrings )
		}
		
		render("Done")
	}
}
