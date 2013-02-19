package co.okmercury

import org.bson.types.ObjectId

import com.mongodb.WriteConcern

class QuestionMatch {
	ObjectId id

	ObjectId questionId
	ObjectId userAId
	ObjectId userBId

	Integer scoreForUserA
	Integer pointsPossibleForUserA
	Integer scoreForUserB
	Integer pointsPossibleForUserB

	static mapping = {
		compoundIndex questionId: 1, userAId: 1, userBId: 1
		writeConcern WriteConcern.SAFE
	}
}
