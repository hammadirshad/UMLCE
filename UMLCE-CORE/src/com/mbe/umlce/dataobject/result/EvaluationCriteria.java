package com.mbe.umlce.dataobject.result;



public class EvaluationCriteria {
	private String type;
	private String elementName;
	private boolean essential;
	private double marks;

	public EvaluationCriteria() {

	}

	public EvaluationCriteria(String type, String elementName,
			boolean essential, double marks) {
		super();
		this.type = type;
		this.elementName = elementName;
		this.essential = essential;
		this.marks = marks;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public boolean isEssential() {
		return essential;
	}

	public void setEssential(boolean essential) {
		this.essential = essential;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

}
