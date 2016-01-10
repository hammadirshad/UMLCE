package com.mbe.umlce.beans;

import java.util.Date;

public class AssignmentUserPlagarismReportBean {
	
	int id;
	String name;
	Integer title;
	Date submissiondate;
	Integer plagepercentage;
	
	
	public AssignmentUserPlagarismReportBean(int id, String name,
			Integer title, Date submissiondate, Integer plagepercentage) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.submissiondate = submissiondate;
		this.plagepercentage = plagepercentage;
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
	public Integer getTitle() {
		return title;
	}
	public void setTitle(Integer title) {
		this.title = title;
	}
	public Date getSubmissiondate() {
		return submissiondate;
	}
	public void setSubmissiondate(Date submissiondate) {
		this.submissiondate = submissiondate;
	}
	public Integer getPlagepercentage() {
		return plagepercentage;
	}
	public void setPlagepercentage(Integer plagepercentage) {
		this.plagepercentage = plagepercentage;
	}
	
	
}
