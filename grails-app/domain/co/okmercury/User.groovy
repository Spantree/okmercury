package co.okmercury

import org.bson.types.ObjectId

import com.mongodb.WriteConcern
import com.synergyj.grails.plugins.avatar.util.MD5Util

class User {
	ObjectId id
	String firstName
	String lastName
	String jobTitle
	String companyName
	String email
	String password
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

	String getGravatarHash() {
		if(this.@gravatarHash) {
			return this.@gravatarHash
		} else if(email) {
			return MD5Util.md5Hex(email)
		} else {
			return null
		}
	}

	static transients = ['name']
	
	static mapping = {
		writeConcern WriteConcern.SAFE
	}
}
