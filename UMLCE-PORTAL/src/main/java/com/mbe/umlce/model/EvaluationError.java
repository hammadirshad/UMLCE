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
@Table(name="EVALUATION_ERROR")
public class EvaluationError {
	
	@Id
	@SequenceGenerator(name = "evalErrorSeq", sequenceName = "EVAL_ERROR_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evalErrorSeq")
	@Column(name = "EVAL_ERROR_ID", unique = true, nullable = false)
	private int id;
	
	private String errorName;
	private int count;
	@ManyToOne(targetEntity=AssignmentSubmission.class)
	private AssignmentSubmission submission;
	
	
	public EvaluationError()
	{
		
	}
	
	
	public EvaluationError(String errorName, int count,
			AssignmentSubmission submission) {
		super();
		this.errorName = errorName;
		this.count = count;
		this.submission = submission;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public AssignmentSubmission getSubmission() {
		return submission;
	}
	public void setSubmission(AssignmentSubmission submission) {
		this.submission = submission;
	}
	
	
	
}
