package com.mbe.umlce.beans;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.mbe.umlce.model.AssignmentResource;
import com.mbe.umlce.model.User;

public class AssignmentBean {
	
    private int id;
    @NotEmpty(message="* Assignment Title is required!")
	private String title;
    @NotEmpty(message="* Diagram Type is required!")
	private String diagram;
    @NotEmpty(message="* Assignment Description is required!")
	private String description;
	private Date startDate;
    @NotNull(message="* End Date is required!")
	private Date endDate;
	private User owner;
	private boolean mistakes;
	private boolean plagarism;
	private boolean codemapping;
	private boolean evaluation;
	private ArrayList<MultipartFile> files;
	private ArrayList<MultipartFile> sourcecode;
	private ArrayList<MultipartFile> referencemodel;
	
	public AssignmentBean()
	{
		
	}
	
	public AssignmentBean(int id, String title, String diagram,
			String description, Date startDate, Date endDate, User owner,
			ArrayList<AssignmentResource> files) {
		super();
		this.id = id;
		this.title = title;
		this.diagram = diagram;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.owner = owner;
		//this.files = files;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDiagram() {
		return diagram;
	}
	public void setDiagram(String diagram) {
		this.diagram = diagram;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isMistakes() {
		return mistakes;
	}

	public void setMistakes(boolean mistakes) {
		this.mistakes = mistakes;
	}

	public boolean isPlagarism() {
		return plagarism;
	}

	public void setPlagarism(boolean plagarism) {
		this.plagarism = plagarism;
	}

	public boolean isCodemapping() {
		return codemapping;
	}

	public void setCodemapping(boolean codemapping) {
		this.codemapping = codemapping;
	}

	public boolean isEvaluation() {
		return evaluation;
	}

	public void setEvaluation(boolean evaluation) {
		this.evaluation = evaluation;
	}

	public ArrayList<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<MultipartFile> files) {
		this.files = files;
	}

	public ArrayList<MultipartFile> getSourcecode() {
		return sourcecode;
	}

	public void setSourcecode(ArrayList<MultipartFile> sourcecode) {
		this.sourcecode = sourcecode;
	}

	public ArrayList<MultipartFile> getReferencemodel() {
		return referencemodel;
	}

	public void setReferencemodel(ArrayList<MultipartFile> referencemodel) {
		this.referencemodel = referencemodel;
	}
	
	
	
}
