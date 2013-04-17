package co.okmercury

import org.bson.types.ObjectId

class MongoUserConnection {
	ObjectId id
	
	String userId
	String providerId
	String providerUserId
	Integer rank
	String displayName
	String profileUrl
	String imageUrl
	String accessToken
	String secret
	String refreshToken
	Long expireTime
	
	static constraints = {
		userId(blank: false, unique: ['providerId', 'providerUserId'])
		providerId(blank: false)
		providerUserId(blank: false)
		rank(blank: false)
		accessToken(blank: false)
	}
}
