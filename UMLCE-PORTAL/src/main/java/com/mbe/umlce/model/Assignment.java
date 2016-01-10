package com.mbe.umlce.model;

import java.sql.Blob;
import java.util.Date;

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
@Table(name="ASSIGNMENT")
public class Assignment {
	
	@Id
	@SequenceGenerator(name = "assignmentSeq", sequenceName = "ASSIGNMENT_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignmentSeq")
	@Column(name = "ASSIGNMENT_ID", unique = true, nullable = false)
	private int id;
	
	private String title;
	private String diagram;
	@Column(length=1000)
	private String description;
	private Date startDate;
	private Date endDate;
	@ManyToOne(targetEntity=User.class)
	private User owner;
	private int attach;
	private boolean mistakes;
	private boolean plagarism;
	private boolean codemapping;
	private boolean evaluation;
	@Lob 
	private Blob sourceCode;
	private String sourceCodeName;
	@Lob 
	private Blob referenceModel;
	private String referenceModelName;
	private double totalMarks;
	
	public Assignment()
	{
		
	}
	
	public Assignment(String title, String diagram, String description,
			Date startDate, Date endDate, User owner, int attach,
			boolean mistakes, boolean plagarism, boolean codemapping,
			boolean evaluation, Blob sourceCode, String sourceCodeName,
			Blob referenceModel, String referenceModelName) {
		super();
		this.title = title;
		this.diagram = diagram;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.owner = owner;
		this.attach = attach;
		this.mistakes = mistakes;
		this.plagarism = plagarism;
		this.codemapping = codemapping;
		this.evaluation = evaluation;
		this.sourceCode = sourceCode;
		this.sourceCodeName = sourceCodeName;
		this.referenceModel = referenceModel;
		this.referenceModelName = referenceModelName;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAttach() {
		return attach;
	}

	public void setAttach(int attach) {
		this.attach = attach;
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

	public Blob getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(Blob sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Blob getReferenceModel() {
		return referenceModel;
	}

	public void setReferenceModel(Blob referenceModel) {
		this.referenceModel = referenceModel;
	}

	public String getSourceCodeName() {
		return sourceCodeName;
	}

	public void setSourceCodeName(String sourceCodeName) {
		this.sourceCodeName = sourceCodeName;
	}

	public String getReferenceModelName() {
		return referenceModelName;
	}

	public void setReferenceModelName(String referenceModelName) {
		this.referenceModelName = referenceModelName;
	}

	public double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}
	
	
	
}
