package com.mbe.umlce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ASSIGNMENT_SUBMISSION")
public class AssignmentSubmission {
	
	@Id
	@SequenceGenerator(name = "submissionSeq", sequenceName = "SUBMISSION_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "submissionSeq")
	@Column(name = "SUBMISSION_ID", unique = true, nullable = false)
	private int id;
	@Column(length=2000,nullable=true)
	private String description;
	private Date submissionDate;
	@OneToOne(targetEntity=User.class)
	private User owner;
	@ManyToOne(targetEntity=Assignment.class)
	private Assignment assignment;
	private int attachs;
	private double marks;
	private double plagarism;
	public AssignmentSubmission()
	{
		
	}
	
	public AssignmentSubmission(String description, Date submissionDate,
			User owner, Assignment assignment,int attachs) {
		super();
		this.description = description;
		this.submissionDate = submissionDate;
		this.owner = owner;
		this.assignment = assignment;
		this.attachs=attachs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public int getAttachs() {
		return attachs;
	}

	public void setAttachs(int attachs) {
		this.attachs = attachs;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

	public double getPlagarism() {
		return plagarism;
	}

	public void setPlagarism(double plagarism) {
		this.plagarism = plagarism;
	}
	
	
}
