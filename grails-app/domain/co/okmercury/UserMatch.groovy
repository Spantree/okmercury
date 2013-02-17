package co.okmercury

import org.bson.types.ObjectId

class UserMatch {
	ObjectId principalUserId
	ObjectId matchUserId
	
	Integer principalPoints
	Integer principalPointsPossible
	
	Integer matchPoints
	Integer matchPointsPossible
	
	Integer questionsInCommon
	
	Float principalPercentageScore
	Float matchPercentageScore
	
	Float overallScore

	static mapping = {
		compoundIndex principalUserId: 1, overallScore: -1
		compoundIndex principalUserId: 1, principalScore: -1
		compoundIndex principalUserId: 1, matchScore: -1
	}
}
