package com.mbe.umlce.beans;

import java.util.List;

import com.mbe.umlce.beans.EvaluationResult;
import com.mbe.umlce.beans.Plagarism;
import com.mbe.umlce.model.MappingMistake;
import com.mbe.umlce.model.Mistake;

public class AssignmentResult {

	private String name;
	private String rollNo;
	private String submitionDate;
	private String assignmentTitle;
	private String assignmentType;
	private List<Mistake> mistakes;
	private List<MappingMistake> mappingMistakes;
	private List<EvaluationResult> evaluationResult;
	private List<Plagarism> plagarisms;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getSubmitionDate() {
		return submitionDate;
	}

	public void setSubmitionDate(String submitionDate) {
		this.submitionDate = submitionDate;
	}

	public String getAssignmentTitle() {
		return assignmentTitle;
	}

	public void setAssignmentTitle(String assignmentTitle) {
		this.assignmentTitle = assignmentTitle;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public List<Mistake> getMistakes() {
		return mistakes;
	}

	public void setMistakes(List<Mistake> mistakes) {
		this.mistakes = mistakes;
	}

	public List<MappingMistake> getMappingMistakes() {
		return mappingMistakes;
	}

	public void setMappingMistakes(List<MappingMistake> mappingMistakes) {
		this.mappingMistakes = mappingMistakes;
	}

	public List<EvaluationResult> getEvaluationResult() {
		return evaluationResult;
	}

	public void setEvaluationResult(List<EvaluationResult> evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	public List<Plagarism> getPlagarisms() {
		return plagarisms;
	}

	public void setPlagarisms(List<Plagarism> plagarisms) {
		this.plagarisms = plagarisms;
	}
	
	

}
