package com.mbe.umlce.dataobject;

public class Errors {
	private String type;
	private String errorName;
	private String modelType;
	private String elementName;
	private String errorDiscrption;

	public Errors() {
	}

	public Errors(String type, String errorName, String modelType,
			String elementName, String errorDiscrption) {
		super();
		this.type = type;
		this.errorName = errorName;
		this.modelType = modelType;
		this.elementName = elementName;
		this.errorDiscrption = errorDiscrption;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
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

	public String getErrorDiscrption() {
		return errorDiscrption;
	}

	public void setErrorDiscrption(String errorDiscrption) {
		this.errorDiscrption = errorDiscrption;
	}

}
