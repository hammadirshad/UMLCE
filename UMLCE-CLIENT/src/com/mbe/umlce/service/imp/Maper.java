package com.mbe.umlce.service.imp;

import java.net.URL;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface Maper extends Service {
	public String getMaperPortAddress();

	public com.mbe.umlce.service.MaperPortType getMaperPort()
			throws ServiceException;

	public com.mbe.umlce.service.MaperPortType getMaperPort(URL portAddress)
			throws ServiceException;
}
