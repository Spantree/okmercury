package co.okmercury

import grails.plugin.cache.Cacheable

import org.bson.types.ObjectId

class UserService {
	@Cacheable('email')
	User getUserByEmail(String email) {
		User.findByEmail(email)
	}
	
	@Cacheable('email')
	User getById(ObjectId id) {
		User.get(id)
	}
}
