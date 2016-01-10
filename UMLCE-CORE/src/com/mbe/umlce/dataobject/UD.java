package com.mbe.umlce.dataobject;

import java.util.ArrayList;

public class UD {
	private String systemName;
	private ArrayList<String> actors;
	private ArrayList<String> usecases;
	private ArrayList<String> associations;
	private int actorsCount;
	private int usecasesCount;
	private int includesCount;
	private int extendsCount;

	public UD() {
		systemName = null;
		actors = new ArrayList<String>();
		usecases = new ArrayList<String>();
		associations = new ArrayList<String>();

		actorsCount = 0;
		usecasesCount = 0;
		includesCount = 0;
		extendsCount = 0;
	}

	public UD(String systemName, ArrayList<String> actors,
			ArrayList<String> usecases, ArrayList<String> associations,
			int actorsCount, int usecasesCount, int includesCount,
			int extendsCount) {
		super();
		this.systemName = systemName;
		this.actors = actors;
		this.usecases = usecases;
		this.associations = associations;
		this.actorsCount = actorsCount;
		this.usecasesCount = usecasesCount;
		this.includesCount = includesCount;
		this.extendsCount = extendsCount;
	}

	// set system name
	public void setSystemName(String name) {
		systemName = name;
	}

	// add actor
	public void addActor(String actor) {
		actors.add(actor);
	}

	// add usecase
	public void addUsecase(String uc) {
		usecases.add(uc);
	}

	// set actors count
	public void setActorsCount(int count) {
		actorsCount = count;
	}

	// set usecases count
	public void setUsecasesCount(int count) {
		usecasesCount = count;
	}

	// set includes relationships count
	public void setIncludesCount(int count) {
		includesCount = count;
	}

	// set extend relationships count
	public void setExtendsCount(int count) {
		extendsCount = count;
	}

	// get system name
	public String getSystemName() {
		return systemName;
	}

	// get actors
	public ArrayList<String> getActors() {
		return actors;
	}

	// get usecases
	public ArrayList<String> getUsecases() {
		return usecases;
	}

	public ArrayList<String> getAssociations() {
		return associations;
	}

	public void setAssociations(ArrayList<String> associations) {
		this.associations = associations;
	}

	public void setActors(ArrayList<String> actors) {
		this.actors = actors;
	}

	public void setUsecases(ArrayList<String> usecases) {
		this.usecases = usecases;
	}

	// get actors count
	public int getActorsCount() {
		return actorsCount;
	}

	// get usecases count
	public int getUsecasesCount() {
		return usecasesCount;
	}

	// get include relationships count
	public int getIncludesCount() {
		return includesCount;
	}

	// get extend relationships count
	public int getExtendsCount() {
		return extendsCount;
	}
}
