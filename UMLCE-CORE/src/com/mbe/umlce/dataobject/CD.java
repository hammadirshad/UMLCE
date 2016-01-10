package com.mbe.umlce.dataobject;

import java.util.ArrayList;

public class CD {
	private String className;
	private ArrayList<String> attributes;
	private ArrayList<String> operations;
	private ArrayList<String> relationships;

	public CD() {
		className = null;
		attributes = new ArrayList<String>();
		operations = new ArrayList<String>();
		relationships = new ArrayList<String>();
	}

	public CD(String className, ArrayList<String> attributes,
			ArrayList<String> operations, ArrayList<String> relationships) {
		super();
		this.className = className;
		this.attributes = attributes;
		this.operations = operations;
		this.relationships = relationships;
	}

	// add class attributes
	public void addAttribute(String attribute) {
		attributes.add(attribute);
	}

	// add class operations
	public void addOperation(String operation) {
		operations.add(operation);
	}

	// add class relationships
	public void addRelationships(String relationship) {
		if (!relationships.contains(relationship))
			relationships.add(relationship);
	}

	// set class name
	public void setClassName(String className) {
		this.className = className;
	}

	// get class name
	public String getClassName() {
		return className;
	}

	// get all attributes
	public ArrayList<String> getAttributes() {
		return this.attributes;
	}

	// get all operations
	public ArrayList<String> getOperations() {
		return this.operations;
	}

	// get all relationships
	public ArrayList<String> getRelationships() {
		return this.relationships;
	}
}
