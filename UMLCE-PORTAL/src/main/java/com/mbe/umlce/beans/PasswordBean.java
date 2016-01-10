package com.mbe.umlce.beans;

import org.hibernate.validator.constraints.NotEmpty;

import com.mbe.umlce.validation.FieldMatch;

@FieldMatch(first = "newpass", second = "renewpass", message = "The password fields must match")
public class PasswordBean {
	
	@NotEmpty(message="* Field is required!")
	private String oldpass;
	@NotEmpty(message="* Field is required!")
	private String newpass;
	@NotEmpty(message="* Field is required!")
	private String renewpass;
	
	public String getOldpass() {
		return oldpass;
	}
	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
	}
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	public String getRenewpass() {
		return renewpass;
	}
	public void setRenewpass(String renewpass) {
		this.renewpass = renewpass;
	}
	
	

}
