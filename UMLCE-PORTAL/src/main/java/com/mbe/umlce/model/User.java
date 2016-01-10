package com.mbe.umlce.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PORTAL_USER")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "userSeq", sequenceName = "USER_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
	@Column(name = "USER_ID", unique = true, nullable = false)
	private int id;
	private String fname;
	private String lname;
	private String gender;
	private String enabled;
	@Column(unique = true, nullable = false)
	private String username;
	private String password;
	private String email;
	private String phone;
	private String address;
	@Lob 
	private Blob picture;

	// @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PORTAL_USER_ROLE", 
	joinColumns = { @JoinColumn(name = "PORTAL_USER_USER_ID", referencedColumnName = "USER_ID") }, 
	inverseJoinColumns = { @JoinColumn(name = "ROLES_ROLE_ID", referencedColumnName = "ROLE_ID") })
	private Set<Role> roles = new HashSet<Role>();

	public User() {
	}

	public User(String fname, String lname, String gender, String enabled,
			String username, String password, String email, String phone,
			String address, Set<Role> roles) {
		this.fname = fname;
		this.lname = lname;
		this.gender = gender;
		this.enabled = enabled;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.roles = roles;
	}

	public User(String fname, String lname, String gender, String enabled,
			String username, String password, String email, String phone,
			String address) {
		this.fname = fname;
		this.lname = lname;
		this.gender = gender;
		this.enabled = enabled;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

}