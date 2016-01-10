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
@Table(name="MAPPINGERROR")
public class MappingMistake {
	@Id
	@SequenceGenerator(name = "mappingSeq", sequenceName = "MAPPING_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mappingSeq")
	@Column(name = "MAPPING_ID", unique = true, nullable = false)
	private int id;
	
	@Column(name="Description")
	private String errorDescription;
	private String elementName;
	@ManyToOne(targetEntity=User.class)
	private User mistaker;
	@ManyToOne(targetEntity=Assignment.class)
	private Assignment assignment;
	@ManyToOne(targetEntity=AssignmentSubmission.class)
	private AssignmentSubmission assignmentSub;
	
	public MappingMistake()
	{
		
	}
	

	public MappingMistake(String errorDescription, String elementName,
			User mistaker, Assignment assignment,
			AssignmentSubmission assignmentSub) {
		super();
		this.errorDescription = errorDescription;
		this.elementName = elementName;
		this.mistaker = mistaker;
		this.assignment = assignment;
		this.assignmentSub = assignmentSub;
	}





	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
