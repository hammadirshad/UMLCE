package com.mbe.umlce.dataobject;

import java.util.ArrayList;

import com.mbe.umlce.dataobjects.stateMachine.StateDetails;
import com.mbe.umlce.dataobjects.stateMachine.TransitionDetails;

public class SM {
	
	
	private String name;
	
	private ArrayList<TransitionDetails> transitions;
	private String type;
	
	public SM() {
		
	}


	public SM(String name, ArrayList<TransitionDetails> transitions, String type) {
		super();
		this.name = name;
		this.transitions = transitions;
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public ArrayList<TransitionDetails> getTransitions() {
		return transitions;
	}


	public void setTransitions(ArrayList<TransitionDetails> transitions) {
		this.transitions = transitions;
	}
	
	
	public void addTransition(TransitionDetails trans)
	{
		transitions.add(trans);
	}
	
}
