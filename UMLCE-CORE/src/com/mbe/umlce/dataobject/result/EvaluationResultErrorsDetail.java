package com.mbe.umlce.dataobject.result;

public class EvaluationResultErrorsDetail {

	public EvaluationResultErrorsDetail() {
	}

	public EvaluationResultErrorsDetail(String elementName,
			String errorDiscption) {
		super();
		this.elementName = elementName;
		this.errorDiscption = errorDiscption;
	}

	private String elementName;
	private String errorDiscption;

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getErrorDiscption() {
		return errorDiscption;
	}

	public void setErrorDiscption(String errorDiscption) {
		this.errorDiscption = errorDiscption;
	}

}
