package co.okmercury
import org.bson.types.ObjectId
import com.synergyj.grails.plugins.avatar.util.MD5Util

class User {
	ObjectId id
	def springSecurityService
	String username
	String password
	String email
	Set authorities
	boolean _dirtyPassword = false
	
	String oauthProvider;
	
	String firstName
	String lastName
	
	// User-specific vars
	String gravatarHash
	String jobTitle
	String companyName

	static transients = ["springSecurityService", "_dirtyPassword", "name"]
	static constraints = {
		username blank: false, unique: true,size: 2..32
		email blank: false, unique:true,email:true
		password blank: false,size:6..64
	}

	static mapping = { password column: '`password`' }

	void setPassword(String password) {
		this.@password = password
		this._dirtyPassword = true
	}
	
	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (_dirtyPassword) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
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
}