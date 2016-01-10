package com.mbe.umlce.beans;


import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;


@ManagedBean(name = "AssignmentSubmissionBean")
public class AssignmentSubmissionBean {
	
    private int id;
	@NotEmpty(message="Assignment Id is required!")
    private String query;
	private String description;
	@NotNull(message="* Submittion diagram is required!")
	@NotEmpty(message="* At least one diagram is required!")
	private ArrayList<MultipartFile> files;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<MultipartFile> files) {
		this.files = files;
	}

}
