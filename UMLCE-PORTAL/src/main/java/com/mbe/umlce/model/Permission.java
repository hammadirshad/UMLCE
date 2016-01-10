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
@Table(name = "Permission")
public class Permission {

	@Id
	@SequenceGenerator(name = "permissionSeq", sequenceName = "PERMISSION_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissionSeq")
	@Column(name = "PERMISSION_ID", unique = true, nullable = false)
	private int id;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PERMISSION", joinColumns = { @JoinColumn(name = "PERMISSIONS_PERMISSION_ID", referencedColumnName = "PERMISSION_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ROLE_ID", referencedColumnName = "ROLE_ID") })
	private Set<Role> roles;

//	@ManyToMany(targetEntity = PermissionLevel.class)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSION_PERMISSION_LEVEL", joinColumns = { @JoinColumn(name = "PERMISSIONS_PERMISSION_ID", referencedColumnName = "PERMISSION_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERMISSIONLEVELS_LEVEL_ID", referencedColumnName = "LEVEL_ID") })
	private Set<PermissionLevel> permissionLevels;

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

	public Set<PermissionLevel> getPermissionLevels() {
		return permissionLevels;
	}

	public void setPermissionLevels(Set<PermissionLevel> permissionLevels) {
		this.permissionLevels = permissionLevels;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
