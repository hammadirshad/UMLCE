package com.mbe.umlce.beans;

import java.util.Set;

import com.mbe.umlce.model.Permission;


public class RoleBean {

	private int id;
	private String name;
	private Set<Permission> permissions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}