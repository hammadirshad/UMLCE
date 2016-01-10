package com.mbe.umlce.service;

import java.rmi.RemoteException;

import com.mbe.umlce.service.imp.IdentifierLocator;

public class IdentifierPortTypeProxy implements IdentifierPortType {
	private String _endpoint = null;
	private IdentifierPortType identifierPortType = null;

	public IdentifierPortTypeProxy() {
		_initIdentifierPortTypeProxy();
	}

	public IdentifierPortTypeProxy(String endpoint) {
		_endpoint = endpoint;
		_initIdentifierPortTypeProxy();
	}

	private void _initIdentifierPortTypeProxy() {
		try {
			identifierPortType = (new IdentifierLocator()).getIdentifierPort();
			if (identifierPortType != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) identifierPortType)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) identifierPortType)
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
		if (identifierPortType != null)
			((javax.xml.rpc.Stub) identifierPortType)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public IdentifierPortType getIdentifierPortType() {
		if (identifierPortType == null)
			_initIdentifierPortTypeProxy();
		return identifierPortType;
	}

	public String classDiagramMistakes(byte[] arg0) throws RemoteException,
			Exception {
		if (identifierPortType == null)
			_initIdentifierPortTypeProxy();
		return identifierPortType.classDiagramMistakes(arg0);
	}

	public String usecaseDiagramMistakes(byte[] arg0) throws RemoteException,
			Exception {
		if (identifierPortType == null)
			_initIdentifierPortTypeProxy();
		return identifierPortType.usecaseDiagramMistakes(arg0);
	}

	public String systemSequenceDiagramMistakes(byte[] arg0)
			throws RemoteException, Exception {
		if (identifierPortType == null)
			_initIdentifierPortTypeProxy();
		return identifierPortType.systemSequenceDiagramMistakes(arg0);
	}

	public String domainModelMistakes(byte[] arg0) throws RemoteException,
			Exception {
		if (identifierPortType == null)
			_initIdentifierPortTypeProxy();
		return identifierPortType.domainModelMistakes(arg0);
	}

	public String activityDiagramMistakes(byte[] arg0) throws RemoteException,
			Exception {
		if (identifierPortType == null)
			_initIdentifierPortTypeProxy();
		return identifierPortType.activityDiagramMistakes(arg0);
	}

	public String stateMachineMistakes(byte[] arg0) throws RemoteException,
			Exception {
		if (identifierPortType == null)
			_initIdentifierPortTypeProxy();
		return identifierPortType.stateMachineMistakes(arg0);
	}

	public String sequenceDiagramMistakes(byte[] arg0) throws RemoteException,
			Exception {
		if (identifierPortType == null)
			_initIdentifierPortTypeProxy();
		return identifierPortType.sequenceDiagramMistakes(arg0);
	}

}