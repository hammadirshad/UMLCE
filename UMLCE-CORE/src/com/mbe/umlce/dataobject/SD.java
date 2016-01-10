package com.mbe.umlce.dataobject;

import java.util.ArrayList;

import com.mbe.umlce.dataobject.classDiagram.ClassStructure;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceAttribute;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceBehavior;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceCombinedFragment;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceGate;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceLifeline;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceMessage;

public class SD {

	private ArrayList<SequenceLifeline> lifelines = new ArrayList<SequenceLifeline>();
	private ArrayList<SequenceMessage> messages = new ArrayList<SequenceMessage>();
	private ArrayList<ClassStructure> classes = new ArrayList<ClassStructure>();
	private ArrayList<SequenceAttribute> attributes = new ArrayList<SequenceAttribute>();
	private ArrayList<SequenceGate> gates = new ArrayList<SequenceGate>();
	private ArrayList<SequenceBehavior> behaviors = new ArrayList<SequenceBehavior>();
	private ArrayList<SequenceCombinedFragment> fragments = new ArrayList<SequenceCombinedFragment>();

	public ArrayList<SequenceLifeline> getLifelines() {
		return lifelines;
	}

	public void setLifelines(ArrayList<SequenceLifeline> lifelines) {
		this.lifelines = lifelines;
	}

	public ArrayList<SequenceMessage> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<SequenceMessage> messages) {
		this.messages = messages;
	}

	public ArrayList<ClassStructure> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<ClassStructure> classes) {
		this.classes = classes;
	}

	public ArrayList<SequenceAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<SequenceAttribute> attributes) {
		this.attributes = attributes;
	}

	public void addMessage(SequenceMessage message) {
		this.messages.add(message);
	}

	public ArrayList<SequenceGate> getGates() {
		return gates;
	}

	public void setGates(ArrayList<SequenceGate> gates) {
		this.gates = gates;
	}

	public ArrayList<SequenceBehavior> getBehaviors() {
		return behaviors;
	}

	public void setBehaviors(ArrayList<SequenceBehavior> behaviors) {
		this.behaviors = behaviors;
	}

	public ArrayList<SequenceCombinedFragment> getFragments() {
		return fragments;
	}

	public void setFragments(ArrayList<SequenceCombinedFragment> fragments) {
		this.fragments = fragments;
	}

	public void addLifeline(SequenceLifeline lifeLine) {
		this.lifelines.add(lifeLine);
	}

	public void addClass(ClassStructure structure) {
		this.classes.add(structure);
	}

	public void addAttribute(SequenceAttribute attribute) {
		this.attributes.add(attribute);
	}

	public void addGate(SequenceGate gate) {
		this.gates.add(gate);
	}

	public void addBehavior(SequenceBehavior behavior) {
		this.behaviors.add(behavior);
	}

	public void addFragment(SequenceCombinedFragment fragment) {
		this.fragments.add(fragment);
	}
}
