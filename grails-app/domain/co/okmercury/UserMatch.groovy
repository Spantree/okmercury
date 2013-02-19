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

	Float marginOfError
	
	static mapping = {
		compoundIndex principalUserId: 1, overallScore: -1
		compoundIndex principalUserId: 1, principalPercentageScore: -1
		compoundIndex principalUserId: 1, matchPercentageScore: -1
		compoundIndex principalUserId: 1, questionsInCommon: -1
	}
}
