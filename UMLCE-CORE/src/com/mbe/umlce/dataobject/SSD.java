package com.mbe.umlce.dataobject;

import java.util.ArrayList;

public class SSD {
	private String collaborationName;
	private ArrayList<String> lifelines;
	private ArrayList<String> operations;
	private ArrayList<String> replyMessages;

	public SSD() {
		collaborationName = null;
		lifelines = new ArrayList<String>();
		operations = new ArrayList<String>();
		replyMessages = new ArrayList<String>();
	}

	public SSD(String collaborationName, ArrayList<String> lifelines,
			ArrayList<String> operations, ArrayList<String> replyMessages) {
		super();
		this.collaborationName = collaborationName;
		this.lifelines = lifelines;
		this.operations = operations;
		this.replyMessages = replyMessages;
	}

	// set collaboration name
	public void setCollabrationName(String name) {
		collaborationName = name;
	}

	// add lifeline in 'lifelines' ArrayList
	public void addLifeline(String lifeline) {
		lifelines.add(lifeline);
	}

	// add operation in 'operations' ArrayList
	public void addOperation(String operation) {
		operations.add(operation);
	}

	// add reply message in 'replyMessages' ArrayList
	public void addMessage(String msg) {
		replyMessages.add(msg);
	}

	public String getCollaborationName() {
		return collaborationName;
	}

	public void setCollaborationName(String collaborationName) {
		this.collaborationName = collaborationName;
	}

	public ArrayList<String> getReplyMessages() {
		return replyMessages;
	}

	public void setReplyMessages(ArrayList<String> replyMessages) {
		this.replyMessages = replyMessages;
	}

	public void setLifelines(ArrayList<String> lifelines) {
		this.lifelines = lifelines;
	}

	public void setOperations(ArrayList<String> operations) {
		this.operations = operations;
	}

	// get collaboration name
	public String getCollabrationName() {
		return collaborationName;
	}

	// get lifelines list
	public ArrayList<String> getLifelines() {
		return lifelines;
	}

	// get operations list
	public ArrayList<String> getOperations() {
		return operations;
	}

	// get reply messages list
	public ArrayList<String> getMessages() {
		return replyMessages;
	}
}
