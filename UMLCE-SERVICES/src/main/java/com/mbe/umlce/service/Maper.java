package com.mbe.umlce.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

@WebService(name = "MaperPortType", targetNamespace = "http://service.umce.fyp.com")
@MTOM
public interface Maper {

	@WebMethod
	public String mapClassToCode(byte[] diagram, byte[] codeZip)
			throws Exception, java.lang.Exception;

	@WebMethod
	public String mapSequenceToCode(byte[] diagram, byte[] codeZip)
			throws Exception, java.lang.Exception;

	@WebMethod
	public String mapPackageToCode(byte[] diagram, byte[] codeZip)
			throws Exception, java.lang.Exception;

}
