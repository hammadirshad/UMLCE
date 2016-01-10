package com.mbe.umlce.service.imp;

import javax.xml.rpc.ServiceException;

import com.mbe.umlce.Service;

import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;

public class MaperLocator extends org.apache.axis.client.Service implements
		Maper {
	private static final long serialVersionUID = 1L;
	// Use to get a proxy class for MaperPort
	private String MaperPort_address = null;

	public MaperLocator() {
		MaperPort_address = Service.getURL() + "/maper";
	}

	public MaperLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public MaperLocator(String wsdlLoc, QName sName) throws ServiceException {
		super(wsdlLoc, sName);
	}

	public String getMaperPortAddress() {
		return MaperPort_address;
	}

	// The WSDD service name defaults to the port name.
	private String MaperPortWSDDServiceName = "MaperPort";

	public String getMaperPortWSDDServiceName() {
		return MaperPortWSDDServiceName;
	}

	public void setMaperPortWSDDServiceName(String name) {
		MaperPortWSDDServiceName = name;
	}

	public com.mbe.umlce.service.MaperPortType getMaperPort()
			throws ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(MaperPort_address);
		} catch (java.net.MalformedURLException e) {
			throw new ServiceException(e);
		}
		return getMaperPort(endpoint);
	}

	public com.mbe.umlce.service.MaperPortType getMaperPort(
			java.net.URL portAddress) throws ServiceException {
		try {
			MaperPortBindingStub _stub = new MaperPortBindingStub(portAddress,
					this);
			_stub.setPortName(getMaperPortWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setMaperPortEndpointAddress(String address) {
		MaperPort_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws ServiceException {
		try {
			if (com.mbe.umlce.service.MaperPortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				MaperPortBindingStub _stub = new MaperPortBindingStub(
						new java.net.URL(MaperPort_address), this);
				_stub.setPortName(getMaperPortWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new ServiceException(t);
		}
		throw new ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(QName portName,
			Class serviceEndpointInterface) throws ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		String inputPortName = portName.getLocalPart();
		if ("MaperPort".equals(inputPortName)) {
			return getMaperPort();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public QName getServiceName() {
		return new QName("http://imp.service.umce.fyp.com", "Maper");
	}

	private HashSet ports = null;

	public Iterator getPorts() {
		if (ports == null) {
			ports = new HashSet();
			ports.add(new QName("http://imp.service.umce.fyp.com", "MaperPort"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(String portName, String address)
			throws ServiceException {

		if ("MaperPort".equals(portName)) {
			setMaperPortEndpointAddress(address);
		} else { // Unknown Port Name
			throw new ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(QName portName, String address)
			throws ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
