package co.okmercury

import org.bson.types.ObjectId

class ResetController {
	QuestionService questionService
	
	def questions = [
		[
			question: "I work for...",
			options: [
				"Myself",
				"A small company (2-10 employees)",
				"A medium-sized company (10-200 employees)",
				"A large company (200+ employees)",
				"I am currently unemployed"
			]
		],
		[
			question: "I primarily deal with furnishings for...",
			options: [
				"The Home",
				"The Office",
				"Industrial Spaces",
				"Outdoor Spaces",
				"Hotel, Restaurant and Hospitality Sector"	
			]	
		],
		[
			question: "My role can best be described as...",
			options: [
				"Retail",
				"Design and Consulting",
				"Media and Marketing"
			]
		],
		[
			question: "I am located...",
			options: [
				"On the East Coast",
				"In the Midwest",
				"On the West Coast",
				"In the South",
				"Outside the United States"	
			]	
		]
	]
	
	def reset() {
		User user = User.findByEmail('cedric')
		
		log.info "Deleting all the things!"
		
		QuestionMatch.deleteAll()
		UserMatch.deleteAll()
		Answer.deleteAll()
		QuestionOption.deleteAll()
		Question.deleteAll()
		questions.each { Map map ->
			ObjectId id = new ObjectId()
			log.info "Adding question: '${map.question}' as ${id}"
			questionService.updateQuestion(id.toString(), map.question, map.options, user)
		}
		render "Done!"
	}
}
