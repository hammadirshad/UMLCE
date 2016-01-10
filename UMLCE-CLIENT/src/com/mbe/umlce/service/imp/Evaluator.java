package com.mbe.umlce.service.imp;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface Evaluator extends Service {
	public String getEvaluatorPortAddress();

	public com.mbe.umlce.service.EvaluatorPortType getEvaluatorPort()
			throws ServiceException;

	public com.mbe.umlce.service.EvaluatorPortType getEvaluatorPort(
			URL portAddress) throws ServiceException;
}
