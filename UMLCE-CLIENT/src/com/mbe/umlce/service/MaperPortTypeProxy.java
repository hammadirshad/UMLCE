package com.mbe.umlce.service;

import java.rmi.RemoteException;

import com.mbe.umlce.service.imp.MaperLocator;

public class MaperPortTypeProxy implements MaperPortType {
	private String _endpoint = null;
	private MaperPortType maperPortType = null;

	public MaperPortTypeProxy() {
		_initMaperPortTypeProxy();
	}

	public MaperPortTypeProxy(String endpoint) {
		_endpoint = endpoint;
		_initMaperPortTypeProxy();
	}

	private void _initMaperPortTypeProxy() {
		try {
			maperPortType = (new MaperLocator()).getMaperPort();
			if (maperPortType != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) maperPortType)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) maperPortType)
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
		if (maperPortType != null)
			((javax.xml.rpc.Stub) maperPortType)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public MaperPortType getMaperPortType() {
		if (maperPortType == null)
			_initMaperPortTypeProxy();
		return maperPortType;
	}

	public String mapPackageToCode(byte[] arg0, byte[] arg1)
			throws RemoteException, Exception {
		if (maperPortType == null)
			_initMaperPortTypeProxy();
		return maperPortType.mapPackageToCode(arg0, arg1);
	}

	public String mapClassToCode(byte[] arg0, byte[] arg1)
			throws RemoteException, Exception {
		if (maperPortType == null)
			_initMaperPortTypeProxy();
		return maperPortType.mapClassToCode(arg0, arg1);
	}

	public String mapSequenceToCode(byte[] arg0, byte[] arg1)
			throws RemoteException, Exception {
		if (maperPortType == null)
			_initMaperPortTypeProxy();
		return maperPortType.mapSequenceToCode(arg0, arg1);
	}

}