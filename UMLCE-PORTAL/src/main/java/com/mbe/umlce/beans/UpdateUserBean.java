package com.mbe.umlce.beans;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UpdateUserBean {

	@NotEmpty(message="* First Name is required!")
	private String fname;
	@NotEmpty(message="* Last Name is required!")
	private String lname;
	@NotEmpty(message="* Username is required!")
	private String username;
	@NotEmpty(message="* Phone no is required!")
	private String phoneno;
	@NotEmpty(message="* Address is required!")
	private String address;
	@NotEmpty(message="* Email is required!")
	@Email(message="* Email Format Not Valid!")
	private String email;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
}
