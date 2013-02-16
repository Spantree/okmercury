package co.okmercury;

import groovy.transform.CompileStatic

@CompileStatic
public enum Importance {
	IRRELEVANT("Irrelevant", 0),
	A_LITTLE_IMPORTANT("A little important", 1),
	SOMEWHAT_IMPORTANT("Somewhat important", 10),
	VERY_IMPORTANT("Very important", 50),
	MANDATORY("Mandatory", 250)
	
	private final String label
	private final int weight
	
	Importance(final String label, final int weight) {
		this.label = label
		this.weight = weight
	}
	String getLabel() { this.@label }
	Integer getWeight() { this.@weight }
}
