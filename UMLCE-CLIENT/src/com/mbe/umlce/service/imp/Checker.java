package com.mbe.umlce.service.imp;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

import com.mbe.umlce.service.CheckerPortType;

import java.net.URL;

public interface Checker extends Service {
	public String getCheckerPortAddress();

	public CheckerPortType getCheckerPort() throws ServiceException;

	public CheckerPortType getCheckerPort(URL portAddress)
			throws ServiceException;
}
