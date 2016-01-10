package com.mbe.umlce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="EVALUATION_CRITARIA")
public class EvaluationCritaria {
	
	@Id
	@SequenceGenerator(name = "critariaSeq", sequenceName = "CRITARIA_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "critariaSeq")
	@Column(name = "CRITARIA_ID", unique = true, nullable = false)
	private int id;
	
	String type;
	String elementName;
	boolean essential;
	double marks;
	@ManyToOne(targetEntity=Assignment.class)
	private Assignment assignment;
	
	public EvaluationCritaria()
	{
		
	}
	
	public EvaluationCritaria(String type, String elementName,
			boolean essential, double marks, Assignment assignment) {
		super();
		this.type = type;
		this.elementName = elementName;
		this.essential = essential;
		this.marks = marks;
		this.assignment = assignment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
	
	

}
