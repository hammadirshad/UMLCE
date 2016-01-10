package com.mbe.umlce.beans;

public class Plagarism {

	private String student1;
	private String student2;
	private Float plagPersentage;
	
	public Plagarism(String student1, String student2, Float plagPersentage) {
		super();
		this.student1 = student1;
		this.student2 = student2;
		this.plagPersentage = plagPersentage;
	}
	public String getStudent1() {
		return student1;
	}
	public void setStudent1(String student1) {
		this.student1 = student1;
	}
	public String getStudent2() {
		return student2;
	}
	public void setStudent2(String student2) {
		this.student2 = student2;
	}
	public Float getPlagPersentage() {
		return plagPersentage;
	}
	public void setPlagPersentage(Float plagPersentage) {
		this.plagPersentage = plagPersentage;
	}
	
	
}
