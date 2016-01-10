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
@Table(name="ERROR")
public class Mistake {
	@Id
	@SequenceGenerator(name = "errorSeq", sequenceName = "ERROR_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "errorSeq")
	@Column(name = "ERROR_ID", unique = true, nullable = false)
	private int id;
	
	private String errorName;
	private String type;
	private String modelType;
	@Column(name="Description")
	private String errorDescription;
	private String elementName;
	@ManyToOne(targetEntity=User.class)
	private User mistaker;
	@ManyToOne(targetEntity=Assignment.class)
	private Assignment assignment;
	@ManyToOne(targetEntity=AssignmentSubmission.class)
	private AssignmentSubmission assignmentSub;
	
	public Mistake()
	{
		
	}
	
	
	public Mistake(String errorName, String type, String modelType,
			String errorDescription, String elementName, User mistaker,
			Assignment assignment, AssignmentSubmission assignmentSubmission) {
		super();
		this.errorName = errorName;
		this.type = type;
		this.modelType = modelType;
		this.errorDescription = errorDescription;
		this.elementName = elementName;
		this.mistaker = mistaker;
		this.assignment = assignment;
		this.assignmentSub = assignmentSubmission;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}


	public User getMistaker() {
		return mistaker;
	}


	public void setMistaker(User mistaker) {
		this.mistaker = mistaker;
	}


	public Assignment getAssignment() {
		return assignment;
	}


	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}


	public AssignmentSubmission getAssignmentSub() {
		return assignmentSub;
	}


	public void setAssignmentSub(AssignmentSubmission assignmentSubmission) {
		this.assignmentSub = assignmentSubmission;
	}
	
	

	
}
