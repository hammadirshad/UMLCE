package com.mbe.umlce.service;

import java.rmi.RemoteException;

import com.mbe.umlce.service.imp.CheckerLocator;

public class CheckerPortTypeProxy implements CheckerPortType {

	private String _endpoint = null;
	private CheckerPortType checkerPortType = null;

	public CheckerPortTypeProxy() {
		_initCheckerPortTypeProxy();
	}

	public CheckerPortTypeProxy(String endpoint) {
		_endpoint = endpoint;
		_initCheckerPortTypeProxy();
	}

	private void _initCheckerPortTypeProxy() {
		try {
			checkerPortType = (new CheckerLocator()).getCheckerPort();
			if (checkerPortType != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) checkerPortType)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) checkerPortType)
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
		if (checkerPortType != null)
			((javax.xml.rpc.Stub) checkerPortType)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public CheckerPortType getCheckerPortType() {
		if (checkerPortType == null)
			_initCheckerPortTypeProxy();
		return checkerPortType;
	}

	public String checkActiityDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception {
		if (checkerPortType == null)
			_initCheckerPortTypeProxy();
		return checkerPortType.checkActiityDiagramPlagiarism(arg0);
	}

	public String stateMachinelagiarism(byte[] arg0) throws RemoteException,
			Exception {
		if (checkerPortType == null)
			_initCheckerPortTypeProxy();
		return checkerPortType.stateMachinelagiarism(arg0);
	}

	public String checkSequenceDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception {
		if (checkerPortType == null)
			_initCheckerPortTypeProxy();
		return checkerPortType.checkSequenceDiagramPlagiarism(arg0);
	}

	public String checkDomainModelPlagiarism(byte[] arg0)
			throws RemoteException, Exception {
		if (checkerPortType == null)
			_initCheckerPortTypeProxy();
		return checkerPortType.checkDomainModelPlagiarism(arg0);
	}

	public String checkUsecaseDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception {
		if (checkerPortType == null)
			_initCheckerPortTypeProxy();
		return checkerPortType.checkUsecaseDiagramPlagiarism(arg0);
	}

	public String checkClassDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception {
		if (checkerPortType == null)
			_initCheckerPortTypeProxy();
		return checkerPortType.checkClassDiagramPlagiarism(arg0);
	}

	public String checkSystemSequenceDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception {
		if (checkerPortType == null)
			_initCheckerPortTypeProxy();
		return checkerPortType.checkSystemSequenceDiagramPlagiarism(arg0);
	}

}