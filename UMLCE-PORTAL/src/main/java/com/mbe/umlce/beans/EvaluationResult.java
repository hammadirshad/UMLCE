package com.mbe.umlce.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class EvaluationResult implements Serializable {

	private static final long serialVersionUID = -6862147403752758971L;
	private Double totalMarks;
	private Double studentMarks;
	private ArrayList<EvaluationMistakeBean> errors = new ArrayList<EvaluationMistakeBean>();

	public EvaluationResult() {
	}

	public EvaluationResult(Double totalMarks, Double studentMarks,
			ArrayList<EvaluationMistakeBean> errors) {
		super();
		this.totalMarks = totalMarks;
		this.studentMarks = studentMarks;
		this.errors = errors;
	}

	public Double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Double totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Double getStudentMarks() {
		return studentMarks;
	}

	public void setStudentMarks(Double studentMarks) {
		this.studentMarks = studentMarks;
	}

	public ArrayList<EvaluationMistakeBean> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<EvaluationMistakeBean> errors) {
		this.errors = errors;
	}

	public void addErrors(EvaluationMistakeBean errors) {
		this.errors.add(errors);
	}

}
