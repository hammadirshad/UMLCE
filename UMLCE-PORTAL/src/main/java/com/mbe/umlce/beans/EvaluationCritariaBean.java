package com.mbe.umlce.beans;

import java.util.ArrayList;

import com.fyp.umce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.model.Assignment;

public class EvaluationCritariaBean {
	
	private ArrayList<EvaluationCriteria> mistakes;
	private ArrayList<EvaluationCriteria> classes;
	private ArrayList<EvaluationCriteria> concepts;
	private ArrayList<EvaluationCriteria> lifelines;
	private ArrayList<EvaluationCriteria> oprations;
	private ArrayList<EvaluationCriteria> actors;
	private ArrayList<EvaluationCriteria> usecases;
	private ArrayList<EvaluationCriteria> actions;
	private ArrayList<EvaluationCriteria> decisions;
	private ArrayList<EvaluationCriteria> nodes;
	private Assignment assignment;
	private double totalMarks;
	
	public ArrayList<EvaluationCriteria> getActions() {
		return actions;
	}
	public void setActions(ArrayList<EvaluationCriteria> actions) {
		this.actions = actions;
	}
	public ArrayList<EvaluationCriteria> getDecisions() {
		return decisions;
	}
	public void setDecisions(ArrayList<EvaluationCriteria> decisions) {
		this.decisions = decisions;
	}
	public ArrayList<EvaluationCriteria> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<EvaluationCriteria> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<EvaluationCriteria> getMistakes() {
		return mistakes;
	}
	public void setMistakes(ArrayList<EvaluationCriteria> mistakes) {
		this.mistakes = mistakes;
	}
	public ArrayList<EvaluationCriteria> getClasses() {
		return classes;
	}
	public void setClasses(ArrayList<EvaluationCriteria> classes) {
		this.classes = classes;
	}
	public ArrayList<EvaluationCriteria> getConcepts() {
		return concepts;
	}
	public void setConcepts(ArrayList<EvaluationCriteria> concepts) {
		this.concepts = concepts;
	}
	public ArrayList<EvaluationCriteria> getLifelines() {
		return lifelines;
	}
	public void setLifelines(ArrayList<EvaluationCriteria> lifelines) {
		this.lifelines = lifelines;
	}
	public ArrayList<EvaluationCriteria> getOprations() {
		return oprations;
	}
	public void setOprations(ArrayList<EvaluationCriteria> oprations) {
		this.oprations = oprations;
	}
	public ArrayList<EvaluationCriteria> getActors() {
		return actors;
	}
	public void setActors(ArrayList<EvaluationCriteria> actors) {
		this.actors = actors;
	}
	public ArrayList<EvaluationCriteria> getUsecases() {
		return usecases;
	}
	public void setUsecases(ArrayList<EvaluationCriteria> usecases) {
		this.usecases = usecases;
	}
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	public double getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}
	
	

}
