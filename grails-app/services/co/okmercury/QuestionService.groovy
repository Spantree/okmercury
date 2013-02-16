package co.okmercury

import org.bson.types.ObjectId

import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.Mongo

class QuestionService {
	def mongo
	
	DBCollection getCollection() {
		mongo.getDB('okmercury').getCollection('question')
	}
	
	Question upsert(String id) {
		ObjectId oid = new ObjectId(id)
		Question question = Question.findByAssignedId(oid)
		if(!question) {
			question = new Question(assignedId: oid)
		}
		return question
	}
	
	// TODO: Make this actually perform an upsert
	QuestionOption upsertOption(Question question, String optionString, Float order) {
		QuestionOption option = QuestionOption.findByQuestionAndOrder(question, order)
		if(!option) {
			option = new QuestionOption(question: question, order: order)
		}
		option.answer = optionString
		return option
	}
	
	QuestionOption saveOption(QuestionOption option) {
		option.save(flush: true)
		if(option.errors.hasErrors()) {
			log.error "Error saving question option ${option.answer} for question ${option.question.id}: ${option.errors.allErrors}"
		} else {
			log.info "Successfully saved question option for question ${option.question.id}"
		}
		return option
	}
	
	Question saveQuestion(Question question) {
		question.save(flush: true)
		if(question.errors.hasErrors()) {
			log.error "Error saving question ${question.id}: ${question.errors.allErrors}"
		} else {
			log.info "Successfully saved question ${question.id}"
		}
	}
	
	Question updateQuestion(String id, String questionText, List<String> optionStrings, User createdByUser = null) {
		Map<Object, List<String>> errors = [:]
		Question question = upsert(id)
		question.question = questionText
		int i = 1
		List<QuestionOption> options = optionStrings.collect { String option ->
			upsertOption(question, option, i++)
		}
		
		QuestionOption.where {
			eq 'question', question
			order >= i
		}.deleteAll()
			
		if(!question.createdBy) {
			question.createdBy = createdByUser
		}
		Date now = new Date()
		if(!question.createdDate) {
			question.createdDate = now
		}
		question.lastModifiedDate = now
		saveQuestion(question)
		if(!question.errors.hasErrors()) {
			options.each { QuestionOption option ->
				saveOption(option)
				if(option.errors.hasErrors()) {
					errors[option] = option.errors.allErrors
				}
			}
		}
		if(!errors) {
			question.options.addAll(options)
			saveQuestion(question)
		}
		return question
	}
	
	ObjectId getNextUnansweredQuestionForUser(User user) {
		DBObject obj = collection.findOne([:])
		return obj['_id']
	}
}
