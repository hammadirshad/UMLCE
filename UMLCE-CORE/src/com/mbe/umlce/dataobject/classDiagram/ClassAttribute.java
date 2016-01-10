package com.mbe.umlce.dataobject.classDiagram;

public class ClassAttribute{
    private String attributeName;
    private String attributeType;
    private String attributeVisibility;
    private String direction;
    private Object Value;
	
    
	public ClassAttribute(String name, String type, String visibility,
			Object value) {
		super();
		attributeName = name;
		attributeType = type;
		attributeVisibility = visibility;
		Value = value;
	}
   
	public ClassAttribute() {
		attributeName = "";
		attributeType = "";
		attributeVisibility = "";
	}
	public ClassAttribute(String variableName, String variableType, String variableVisibility) {
		this.attributeName = variableName;
		this.attributeType = variableType;
		this.attributeVisibility = variableVisibility;
	}
	
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttributeVisibility() {
		return attributeVisibility;
	}

	public void setAttributeVisibility(String attributeVisibility) {
		this.attributeVisibility = attributeVisibility;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Object getValue() {
		return Value;
	}

	public void setValue(Object value) {
		Value = value;
	}

	public String printAttribute(){
		String variable = "";	
		variable+=this.attributeType+" "+this.attributeName;
		return variable;
		
	}
	public String printCompleteVariable(){
		String variable = "";
		variable+=this.attributeVisibility+" "+this.attributeType+" "+this.attributeName;
		return variable;
	}

}

