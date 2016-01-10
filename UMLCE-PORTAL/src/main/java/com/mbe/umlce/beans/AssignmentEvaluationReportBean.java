package com.mbe.umlce.beans;

public class AssignmentEvaluationReportBean {
	
	int id;
	String name;
	Integer count;
	Double maxmarks;
	Double minmarks;
	Double marks;
	Double total;
	Integer aboveaverage;
	Integer lessaverage;
	
	public AssignmentEvaluationReportBean(int id, String name, Integer count,
			Double maxmarks, Double minmarks, Double marks, Double total,
			Integer aboveaverage, Integer lessaverage) {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
		this.maxmarks = maxmarks;
		this.minmarks = minmarks;
		this.marks = marks;
		this.total = total;
		this.aboveaverage = aboveaverage;
		this.lessaverage = lessaverage;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
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
	public Double getMarks() {
		return marks;
	}
	public void setMarks(Double marks) {
		this.marks = marks;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getAboveaverage() {
		return aboveaverage;
	}

	public void setAboveaverage(Integer aboveaverage) {
		this.aboveaverage = aboveaverage;
	}

	public Integer getLessaverage() {
		return lessaverage;
	}

	public void setLessaverage(Integer lessaverage) {
		this.lessaverage = lessaverage;
	}
	
	
}
