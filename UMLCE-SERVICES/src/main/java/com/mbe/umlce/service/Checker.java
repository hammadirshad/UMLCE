package com.mbe.umlce.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

@WebService(name = "CheckerPortType", targetNamespace = "http://service.umce.fyp.com")
@MTOM
public interface Checker {

	@WebMethod
	public String checkClassDiagramPlagiarism(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String checkUsecaseDiagramPlagiarism(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String checkSequenceDiagramPlagiarism(byte[] file) throws Exception, java.lang.Exception;
	
	@WebMethod
	public String checkSystemSequenceDiagramPlagiarism(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String checkDomainModelPlagiarism(byte[] file) throws Exception, java.lang.Exception;
	
	@WebMethod
	public String checkActiityDiagramPlagiarism(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String StateMachinelagiarism(byte[] file) throws Exception, java.lang.Exception;
}
