package co.okmercury

import org.bson.types.ObjectId

import com.mongodb.DBCollection
import com.mongodb.WriteConcern

class QuestionService {
	DBCollection getCollection() {
		Question.collection
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
		new QuestionOption(
			question: question,
			answer: optionString,
			order: order	
		)
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
		question.createdBy = createdByUser
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
}
