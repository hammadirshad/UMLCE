package com.mbe.umlce.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SUBMISSION_RESOURCE")
public class SubmissionResource {
	
	@Id
	@SequenceGenerator(name = "submissionResourceSeq", sequenceName = "SUBMISSION_RESOURCE_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "submissionResourceSeq")
	@Column(name = "SUBMISSION_RESOURCE_ID", unique = true, nullable = false)
	private int id;
	private String fileName;
	@Lob
	@Column(name="submissionFile")
	private Blob file;
	@ManyToOne(targetEntity=AssignmentSubmission.class)
	private AssignmentSubmission assignmentSub;
	@ManyToOne(targetEntity=Assignment.class)
	private Assignment assignment;
	
	public SubmissionResource()
	{
		
	}
	
	
	public SubmissionResource(String fileName, Blob file,
			AssignmentSubmission assignmentSub, Assignment assignment) {
		super();
		this.fileName = fileName;
		this.file = file;
		this.assignmentSub = assignmentSub;
		this.assignment = assignment;
	}





	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Blob getFile() {
		return file;
	}
	public void setFile(Blob file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public AssignmentSubmission getAssignmentSub() {
		return assignmentSub;
	}

	public void setAssignmentSub(AssignmentSubmission assignmentSub) {
		this.assignmentSub = assignmentSub;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
}
