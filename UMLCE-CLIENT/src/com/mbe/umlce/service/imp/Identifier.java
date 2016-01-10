package com.mbe.umlce.service.imp;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface Identifier extends Service {
	public String getIdentifierPortAddress();

	public com.mbe.umlce.service.IdentifierPortType getIdentifierPort()
			throws ServiceException;

	public com.mbe.umlce.service.IdentifierPortType getIdentifierPort(
			URL portAddress) throws ServiceException;
}
