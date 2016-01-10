package com.mbe.umlce.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role {

	@Id
	@SequenceGenerator(name = "roleSeq", sequenceName = "ROLE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleSeq")
	@Column(name = "ROLE_ID", unique = true, nullable = false)
	private int id;

	private String name;

	// @ManyToMany(targetEntity = User.class, cascade = CascadeType.ALL)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PORTAL_USER_ROLE", joinColumns = { @JoinColumn(name = "ROLES_ROLE_ID", referencedColumnName = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PORTAL_USER_USER_ID", referencedColumnName = "USER_ID") })
	private Set<User> users;

	// @ManyToMany(targetEntity = Permission.class)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PERMISSION", joinColumns = { @JoinColumn(name = "ROLE_ROLE_ID", referencedColumnName = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERMISSIONS_PERMISSION_ID", referencedColumnName = "PERMISSION_ID") })
	private Set<Permission> permissions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
