package com.mbe.umlce.beans;

import org.hibernate.validator.constraints.NotEmpty;


public class ServiceBean {
	
	@NotEmpty(message="* Field is required!")
	private String host;
	@NotEmpty(message="* Field is required!")
	private String port;
	private String service;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String i) {
		this.port = i;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}

}
