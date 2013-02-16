package co.okmercury

class MatchService {
	def generateQuestionMatches(Answer answerA) {
		User userA = answerA.person;
		Question questionAnswered = answerA.question
		
		Answer.findAll{ question == questionAnswered && user != userA }.each { Answer answerB ->
			User userB = answerB.person
			
			int answerAWeight = answerA.importance.getWeight()
			int answerBWeight = answerB.importance.getWeight()
			
			boolean acceptableForUserA = answerA.acceptableAnswers.contains(answerB.userAnswer)
			boolean acceptableForUserB = answerB.acceptableAnswers.contains(answerA.userAnswer)
			
			QuestionMatch qMatch = new QuestionMatch();
			qMatch.userA = userA
			qMatch.userB = userB
			qMatch.pointsPossibleForUserA = answerA.importance.getWeight()
			
			if(acceptableForUserA) {
				qMatch.scoreForUserA = qMatch.pointsPossibleForUserA
			}
			else {
				qMatch.scoreForUserA = 0
			}
			
			qMatch.pointsPossibleForUserB = answerB.importance.getWeight()
			
			if(acceptableForUserB) {
				qMatch.scoreForUserB = qMatch.pointsPossibleForUserB
			}
			else {
				qMatch.scoreForUserB = 0
			}
			
			qMatch.save()
		}
	}
	
	def generateUserMatches(User principalUser) {
		
		User.findAll().each { User matchUser ->
			
			int principalPoints = 0;
			int principalPossible = 0;
			
			int matchPoints = 0;
			int matchPointsPossible = 0;
			
			int questionsFound = 0;
			
			
			
			QuestionMatch.findAll { userA == principalUser && userB == matchUser }.each { QuestionMatch qm ->
				questionsFound++;
				
				principalPoints += qm.scoreForUserA
				principalPoints += qm.pointsPossibleForUserA
				
				
			}
		}
		
		
	
	}
}
