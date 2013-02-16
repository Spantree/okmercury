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
}
