package co.okmercury

import org.bson.types.ObjectId

class User {
	ObjectId id
	String firstName
	String lastName
	String email
	String passwordHash
	String gravatarHash
	List<String> roles

	String getName() {
		if(firstName && lastName) {
			return "${firstName} ${lastName}"
		} else if(firstName) {
			return firstName
		} else {
			return email
		}
	}

	static transients = ['name']
}
