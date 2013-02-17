package co.okmercury;

import groovy.transform.CompileStatic

import org.bson.types.ObjectId

@CompileStatic
public class AnswerScore {
	final int points
	final int pointsPossible
	
	public AnswerScore(Answer answerA, Answer answerB) {
		pointsPossible = answerA.importance.weight
		points = answerA.acceptableAnswerIds.contains(answerB.userAnswer.id) ? pointsPossible : 0 
	}
}
