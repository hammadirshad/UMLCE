package com.mbe.umlce.dataobject.result;

import java.util.ArrayList;

import com.mbe.umlce.dataobject.Errors;

public class Result {

	private EvaluationResult evaluationResult;
	private ArrayList<MappingErrors> mappingErrors;
	private ArrayList<Plagiarism> plagiarism;
	private ArrayList<Errors> errors;

	public EvaluationResult getEvaluationResult() {
		return evaluationResult;
	}

	public void setEvaluationResult(EvaluationResult evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	public ArrayList<MappingErrors> getMappingErrors() {
		return mappingErrors;
	}

	public void setMappingErrors(ArrayList<MappingErrors> mappingErrors) {
		this.mappingErrors = mappingErrors;
	}

	public ArrayList<Plagiarism> getPlagiarism() {
		return plagiarism;
	}

	public void setPlagiarism(ArrayList<Plagiarism> plagiarism) {
		this.plagiarism = plagiarism;
	}

	public ArrayList<Errors> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<Errors> errors) {
		this.errors = errors;
	}

}
