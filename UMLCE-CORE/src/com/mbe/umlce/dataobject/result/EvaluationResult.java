package com.mbe.umlce.dataobject.result;

import java.io.Serializable;
import java.util.ArrayList;

public class EvaluationResult implements Serializable {

	private static final long serialVersionUID = -6862147403752758971L;
	private double totalMarks;
	private double studentMarks;
	private ArrayList<EvaluationResultError> errors = new ArrayList<EvaluationResultError>();

	public EvaluationResult() {
	}

	public EvaluationResult(double totalMarks, double studentMarks,
			ArrayList<EvaluationResultError> errors) {
		super();
		this.totalMarks = totalMarks;
		this.studentMarks = studentMarks;
		this.errors = errors;
	}

	public double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}

	public double getStudentMarks() {
		return studentMarks;
	}

	public void setStudentMarks(double studentMarks) {
		this.studentMarks = studentMarks;
	}

	public ArrayList<EvaluationResultError> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<EvaluationResultError> errors) {
		this.errors = errors;
	}

	public void addErrors(EvaluationResultError errors) {
		this.errors.add(errors);
	}

}
