package com.mbe.umlce.dataobject.result;

public class MappingErrors {

	private String elementName;
	private String errorDiscrption;

	public MappingErrors() {

	}

	
	public MappingErrors(String elementName, String errorDiscrption) {
		super();
		this.elementName = elementName;
		this.errorDiscrption = errorDiscrption;
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
