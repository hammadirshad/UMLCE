package com.mbe.umlce.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ASSIGNMENT_RESOURCE")
public class AssignmentResource {
	
	@Id
	@SequenceGenerator(name = "assignmentResourceSeq", sequenceName = "ASSIGNMENTRESOURCE_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignmentResourceSeq")
	@Column(name = "ASSIGNMENT_RESOURCE_ID", unique = true, nullable = false)
	private int id;
	private String sourceName;
	@Lob 
	@Column( name = "resourceFile" )
	private Blob file;
	@ManyToOne(targetEntity=Assignment.class)
	private Assignment assignment;
	
	public AssignmentResource()
	{
		
	}
	
	public AssignmentResource(String sourceName, Blob file,
			Assignment assignment) {
		super();
		this.sourceName = sourceName;
		this.file = file;
		this.assignment = assignment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public Blob getFile() {
		return file;
	}
	public void setFile(Blob file) {
		this.file = file;
	}
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
	
	
}
