package com.mbe.umlce.beans;

import java.util.List;

import com.mbe.umlce.model.EvalErrorDetail;

public class EvaluationMistakeBean {
	
	private int id;
	private String errorName;
	private Integer count;
	
	List<EvalErrorDetail> errordetail;
	
	public EvaluationMistakeBean(int id, String errorName, int count,
			List<EvalErrorDetail> errordetail2) {
		super();
		this.id = id;
		this.errorName = errorName;
		this.count = count;
		this.errordetail = errordetail2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<EvalErrorDetail> getErrordetail() {
		return errordetail;
	}

	public void setErrordetail(List<EvalErrorDetail> errordetail) {
		this.errordetail = errordetail;
	}
	
	
	

}
