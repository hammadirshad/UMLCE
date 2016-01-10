package com.mbe.umlce.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Permission_Level")
public class PermissionLevel {

	@Id
	@SequenceGenerator(name = "permissionlevelSeq", sequenceName = "P_LEVEL_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissionlevelSeq")
	@Column(name = "LEVEL_ID", unique = true, nullable = false)
	private int id;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSION_PERMISSION_LEVEL", joinColumns = { @JoinColumn(name = "PERMISSIONLEVELS_LEVEL_ID", referencedColumnName = "LEVEL_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERMISSIONS_PERMISSION_ID", referencedColumnName = "PERMISSION_ID") })
	private Set<Permission> permissions;

	public PermissionLevel() {
	}

	public PermissionLevel(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

}
