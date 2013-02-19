package co.okmercury

import org.apache.commons.lang.math.RandomUtils
import org.bson.types.ObjectId

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBObject
import com.mongodb.WriteConcern

class ResetController {
	UserService userService
	QuestionService questionService
	MatchService matchService
	AnswerService answerService
	def mongo

	def questions = [
		[
			question: "I work for...",
			options: [
				"Myself",
				"A small company (2-10 employees)",
				"A medium-sized company (10-200 employees)",
				"A large company (200+ employees)",
				"I am currently looking for employment"
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
			question: "I primarily to business in...",
			options: [
				"The Midwest",
				"The East Coast",
				"The West Coast",
				"Outside the United States",
				"All Over"
			]
		],
		[
			question: "Are you a Mac or PC?",
			options: [
				"Mac",
				"PC",
				"None of the Above"
			]
		],
		[
			question: "At events, I prefer attendee contact information to be...",
			options: [
				"A closely-guarded secret",
				"Shared with everyone"
			]
		],
		[
			question: "At my events, I prefer attendee contact information to be...",
			options: [
				"A closely-guarded secret",
				"Shared with Everyone"
			]
		],
		[
			question: "My average event hosts...",
			options: [
				"Less than 100 people",
				"100 - 250 people",
				"251 - 1000 people",
				"More than 1000 people",
				"N/A"
			]
		],
		[
			question: "My average event needs...",
			options: [
				"Less than 5000 sq ft of floor space",
				"5000 - 10,000 sq ft of floor space",
				"10,001 - 50,000 sq ft of floor space",
				"More than 50,001 sq ft of floor space",
				"N/A"
			]
		],
		[
			question: "My average event needs...",
			options: [
				"Less than 5000 sq ft of floor space",
				"5000 - 10,000 sq ft of floor space",
				"10,001 - 50,000 sq ft of floor space",
				"50,001 - 100,000 sq ft of floor space",
				"More than 100,000 sq ft of floor space"
			]
		],
		[
			question: "The most important aspect for my events' location is...",
			options: [
				"Cost",
				"Resort-like atmosphere",
				"Proximity to Industry",
				"Accessibility to Public Transportation",
				"Facilities and Accomodations"
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
		],
		
	]

	def userMaps = [
		[
			firstName: "Cedric", lastName: "Hurst", email: "cedric@spantree.net",
			companyName: "Spantree Technology Group, LLC", jobTitle: "Principal & Lead Software Architect"
		],
		[
			firstName: "Gary", lastName: "Turovsky", email: "gary@spantree.net",
			companyName: "Spantree Technology Group, LLC", jobTitle: "Senior Software Engineer"
		],
		[
			firstName: "Hilary", lastName: "Freemason", email: "hmason@notarealemail.com",
			companyName: "Hilton Hotels", jobTitle: "CMO", gravatarHash: "c87a84cf3b06077036deade1b44aa45e"	
		],
		[
			firstName: "Tom", lastName: "Preston", email: "mojombo@notarealemail.com",
			companyName: "Extreme Events", jobTitle: "Cofounder", gravatarHash: "25c7c18223fb42a4c6ae1c8db6f50f9b"	
		],
		[
			firstName: "Rebecca", lastName: "Murray", email: "rmurphey@notarealemail.com",
			companyName: "The City of Austin", jobTitle: "Director", gravatarHash: "0177cdce6af15e10db15b6bf5dc4e0b0"	
		]
	]

	def deleteStuff() {
		log.info "Deleting all the things!"
		DB db = mongo.getDB('okmercury')
		DBObject allQuery = [:] as BasicDBObject
		db.userMatch.remove([email: [$ne: 'admin@okmercury.co']], WriteConcern.SAFE)
		db.questionMatch.remove(allQuery, WriteConcern.SAFE)
		db.answer.remove(allQuery, WriteConcern.SAFE)
		db.questionOption.remove(allQuery, WriteConcern.SAFE)
		db.question.remove(allQuery, WriteConcern.SAFE)
		db.user.remove(allQuery, WriteConcern.SAFE)
	}
	
	def addUsers() {
		userMaps.collect { Map map ->
			userService.createUser(
				map.email,
				'password',
				map.firstName,
				map.lastName,
				map.jobTitle,
				map.companyName,
				map.gravatarHash
			)
		}
	}
	
	def addQuestions() {
		List<User> users = User.list()
		Random r = new Random()
		questions.each { Map map ->
			Collections.shuffle(users, r)
			ObjectId id = new ObjectId()
			log.info "Adding question: '${map.question}' as ${id}"
			questionService.updateQuestion(id.toString(), map.question, map.options, users[0])
		}
	}
	
	def addFakeAnswers() {
		List<User> users = User.list()
		List<Question> questions = Question.list()
		List<Importance> importances = Importance.class.getEnumConstants()
		Random r = new Random()
		questions.each { Question question ->
			List<QuestionOption> options = QuestionOption.where { question == question }.list().findAll {
				it.answer != "I am currently unemployed"
			}
			users.each { User user ->
				boolean shouldAnswer = r.nextInt() % 10 != 7
				if(shouldAnswer) {
					boolean shouldSkip = r.nextInt() % 10 == 0
					if(shouldSkip) {
						answerService.saveAnswerSkipped(user, question.id.toString(), "")
					} else {
						Collections.shuffle(options, r)
						Collections.shuffle(importances, r)
						QuestionOption userAnswer = options[0]
		
						Collections.shuffle(options, r)
						Importance importance = importances[0]
						List<String> acceptableAnswerIds = []
						if(importance != Importance.IRRELEVANT) {
							int numberAcceptable = RandomUtils.nextInt(r, options.size()-1)
							acceptableAnswerIds = options[1..numberAcceptable].collect { it.id.toString() }
						}
						answerService.saveAnswer(
							user,
							question.id.toString(),
							userAnswer.id.toString(),
							acceptableAnswerIds,
							importance.name(),
							""
						)
					}
				}
			}
		}
	}
	
	def reset() {
		deleteStuff()
		addUsers()
		addQuestions()
		addFakeAnswers()
		[:]
	}
}
