package com.mbe.umlce.beans;


public class UserEvaluationReportBean {
	
	private int id;
	private String fname;
	private String lname;
	private Double marks;
	private Double maxmarks;
	private Double minmarks;
	private Integer count;
	
	public UserEvaluationReportBean(int id, String fname, String lname,
			Double marks, Double maxmarks, Double minmarks, Integer count) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.marks = marks;
		this.maxmarks = maxmarks;
		this.minmarks = minmarks;
		this.count = count;
	}
	
	
	public Double getMaxmarks() {
		return maxmarks;
	}
	public void setMaxmarks(Double maxmarks) {
		this.maxmarks = maxmarks;
	}
	public Double getMinmarks() {
		return minmarks;
	}
	public void setMinmarks(Double minmarks) {
		this.minmarks = minmarks;
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
	public Double getMarks() {
		return marks;
	}
	public void setMarks(Double marks) {
		this.marks = marks;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	

	
	

}
