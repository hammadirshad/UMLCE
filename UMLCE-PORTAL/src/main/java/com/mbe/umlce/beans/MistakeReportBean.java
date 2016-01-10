package com.mbe.umlce.beans;


public class MistakeReportBean {
	
	private String errorName;
	private String type;
	private String modelType;
	private String elementName;
	private Integer count;
	
	public MistakeReportBean(String errorName, String type, String modelType,
			String elementName, int count) {
		super();
		this.errorName = errorName;
		this.type = type;
		this.modelType = modelType;
		this.elementName = elementName;
		this.count = count;
	}

	
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

}
