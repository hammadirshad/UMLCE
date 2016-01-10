package com.mbe.umlce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="EVALUATION_ERROR_DETAIL")
public class EvalErrorDetail {
	
	@Id
	@SequenceGenerator(name = "evalDetailSeq", sequenceName = "EVAL_DETAIL_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evalDetailSeq")
	@Column(name = "EVAL_DETAIL_ID", unique = true, nullable = false)
	private int id;
	
	private String elementName;
	private String description;
	@ManyToOne(targetEntity=EvaluationError.class)
	private EvaluationError error;
	
	
	public EvalErrorDetail()
	{
		
	}
	
	public EvalErrorDetail(String elementName, String description,
			EvaluationError error) {
		super();
		this.elementName = elementName;
		this.description = description;
		this.error = error;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public EvaluationError getError() {
		return error;
	}
	public void setError(EvaluationError error) {
		this.error = error;
	}
	
	
	
}
