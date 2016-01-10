package com.mbe.umlce.beans;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.mbe.umlce.model.Role;
import com.mbe.umlce.validation.FieldMatch;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@FieldMatch(first = "password", second = "confirmpass", message = "The password fields must match")
public class UserBean {

	@NotEmpty(message="* First Name is required!")
	private String fname;
	@NotEmpty(message="* Last Name is required!")
	private String lname;
	@NotEmpty(message="* Gender is required!")
	private String gender;
	@NotEmpty(message="* Phone no is required!")
	private String phoneno;
	@NotEmpty(message="* Address is required!")
	private String address;
	@NotEmpty(message="* Username is required!")
	private String username;
	@NotEmpty(message="* Email is required!")
	@Email(message="* Email Format Not Valid!")
	private String email;
	@NotEmpty(message="* Password is required!")
	@Length(min = 6, message = "The Password must be at least 6 characters long.")
	private String password;
	@NotEmpty(message="* Confirm Password is required!")
	private String confirmpass;
	@NotNull(message="* Role is required!")
	@NotEmpty(message="At least one role is required!")
	private Set<Role> roles;
	
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

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpass() {
		return confirmpass;
	}

	public void setConfirmpass(String confirmpass) {
		this.confirmpass = confirmpass;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	
}
