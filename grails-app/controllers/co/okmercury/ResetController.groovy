package co.okmercury

import org.bson.types.ObjectId

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBObject

class ResetController {
	QuestionService questionService
	def mongo
	
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
			question: "How much do you use technology in your events already?",
			options: [
				"Not at all",
				"A little here and there",
				"A fair amount",
				"Central to our events"
			]
		],
		[
			question: "My primary role is...",
			options: [
				"Event Organizer",
				"Event Designer",
				"Supplier",
				"Software Side"
			]
		],
		[
			question: "My main intent here is...",
			options: [
				"Networking",
				"Buying",
				"Selling",
				"Educational",
				"Seeing what's out there"
			]
		],
		[
			question: "How many events do you run each year?",
			options: [
				"None",
				"1 or 2",
				"3 to 5",
				"5 to 10",
				"More than 10",
				"More than 100"
			]
		],
		[
			question: "I'd love to learn the most about...",
			options: [
				"Hackathons",
				"Registration Solutions",
				"Hosted Buyer Solutions",
				"Attendee Tracking",
				"Name Badge Technology"
			]
		],
		[
			question: "My biggest fear with technology is...",
			options: [
				"It won't work",
				"My users won't know how to use it",
				"I won't know how to use it",
				"None",
			]
		]
	]
	
	def reset() {
		User user = User.findByEmail('cedric')
		
		log.info "Deleting all the things!"
		DB db = mongo.getDB('okmercury')
		DBObject allQuery = [:] as BasicDBObject
		db.userMatch.remove(allQuery)
		db.questionMatch.remove(allQuery)
		db.answer.remove(allQuery)
		db.questionOption.remove(allQuery)
		db.question.remove(allQuery)
		
		questions.each { Map map ->
			ObjectId id = new ObjectId()
			log.info "Adding question: '${map.question}' as ${id}"
			questionService.updateQuestion(id.toString(), map.question, map.options, user)
		}
		[:]
	}
}
