package com.mbe.umlce.service;

import java.rmi.RemoteException;

import com.mbe.umlce.service.imp.EvaluatorLocator;

public class EvaluatorPortTypeProxy implements EvaluatorPortType {
	private String _endpoint = null;
	private EvaluatorPortType evaluatorPortType = null;

	public EvaluatorPortTypeProxy() {
		_initEvaluatorPortTypeProxy();
	}

	public EvaluatorPortTypeProxy(String endpoint) {
		_endpoint = endpoint;
		_initEvaluatorPortTypeProxy();
	}

	private void _initEvaluatorPortTypeProxy() {
		try {
			evaluatorPortType = (new EvaluatorLocator()).getEvaluatorPort();
			if (evaluatorPortType != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) evaluatorPortType)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) evaluatorPortType)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (evaluatorPortType != null)
			((javax.xml.rpc.Stub) evaluatorPortType)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public EvaluatorPortType getEvaluatorPortType() {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType;
	}

	public String getSystemSequenceDiagramDetail(byte[] arg0)
			throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getSystemSequenceDiagramDetail(arg0);
	}

	public String getStateMachineDetail(byte[] arg0) throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getStateMachineDetail(arg0);
	}

	public String getActivityDiagramMistakeList() throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getActivityDiagramMistakeList();
	}

	public String getDomainModelMistakeList() throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getDomainModelMistakeList();
	}

	public String getDomainModelDetail(byte[] arg0) throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getDomainModelDetail(arg0);
	}

	public String getUsecaseDiagramDetail(byte[] arg0) throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getUsecaseDiagramDetail(arg0);
	}

	public String evaluateStateMachine(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.evaluateStateMachine(arg0, arg1, arg2, arg3);
	}

	public String getUsecaseMistakeList() throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getUsecaseMistakeList();
	}

	public String getSequenceDiagramMistakeList() throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getSequenceDiagramMistakeList();
	}

	public String getStateMachineMistakeList() throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getStateMachineMistakeList();
	}

	public String getActivityDiagramDetail(byte[] arg0) throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getActivityDiagramDetail(arg0);
	}

	public String getSequenceDiagramDetail(byte[] arg0) throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getSequenceDiagramDetail(arg0);
	}

	public String getClassDiagramMistakeList() throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getClassDiagramMistakeList();
	}

	public String getClassDiagramDetail(byte[] arg0) throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getClassDiagramDetail(arg0);
	}

	public String evaluateClassDiagram(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.evaluateClassDiagram(arg0, arg1, arg2, arg3);
	}

	public String evaluateSequenceDiagram(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType
				.evaluateSequenceDiagram(arg0, arg1, arg2, arg3);
	}

	public String evaluateSystemSequenceDiagram(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.evaluateSystemSequenceDiagram(arg0, arg1,
				arg2, arg3);
	}

	public String evaluateDomainModel(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.evaluateDomainModel(arg0, arg1, arg2, arg3);
	}

	public String evaluateUsecaseDiagram(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.evaluateUsecaseDiagram(arg0, arg1, arg2, arg3);
	}

	public String evaluateActivityDiagram(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType
				.evaluateActivityDiagram(arg0, arg1, arg2, arg3);
	}

	public String getSystemSequenceDiagramMistakeList() throws RemoteException,
			Exception {
		if (evaluatorPortType == null)
			_initEvaluatorPortTypeProxy();
		return evaluatorPortType.getSystemSequenceDiagramMistakeList();
	}

}