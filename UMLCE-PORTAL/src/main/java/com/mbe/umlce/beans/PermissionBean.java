package com.mbe.umlce.beans;




public class PermissionBean {

	private int id;
	private String name;
	private String[] levels;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getLevels() {
		return levels;
	}

	public void setLevels(String[] levels) {
		this.levels = levels;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
