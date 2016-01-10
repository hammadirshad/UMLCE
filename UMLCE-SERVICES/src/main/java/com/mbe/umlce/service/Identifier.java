package com.mbe.umlce.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

@WebService(name = "IdentifierPortType", targetNamespace = "http://service.umce.fyp.com")
@MTOM
public interface Identifier {
	@WebMethod
	public String classDiagramMistakes(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String UsecaseDiagramMistakes(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String SequenceDiagramMistakes(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String SystemSequenceDiagramMistakes(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String DomainModelMistakes(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String ActivityDiagramMistakes(byte[] file) throws Exception, java.lang.Exception;
	
	@WebMethod
	public String StateMachineMistakes(byte[] file) throws Exception, java.lang.Exception;
}