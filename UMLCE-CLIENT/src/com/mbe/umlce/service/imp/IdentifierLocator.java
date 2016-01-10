package com.mbe.umlce.service.imp;

import javax.xml.rpc.ServiceException;

import com.mbe.umlce.Service;

import javax.xml.namespace.QName;

import java.util.HashSet;
import java.util.Iterator;

public class IdentifierLocator extends org.apache.axis.client.Service implements
		Identifier {

	private static final long serialVersionUID = 1L;

	public IdentifierLocator() {
	}

	public IdentifierLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public IdentifierLocator(String wsdlLoc, QName sName)
			throws ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for IdentifierPort
	private String IdentifierPort_address = Service.getURL() + "/identifier";

	public String getIdentifierPortAddress() {
		return IdentifierPort_address;
	}

	// The WSDD service name defaults to the port name.
	private String IdentifierPortWSDDServiceName = "IdentifierPort";

	public String getIdentifierPortWSDDServiceName() {
		return IdentifierPortWSDDServiceName;
	}

	public void setIdentifierPortWSDDServiceName(String name) {
		IdentifierPortWSDDServiceName = name;
	}

	public com.mbe.umlce.service.IdentifierPortType getIdentifierPort()
			throws ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(IdentifierPort_address);
		} catch (java.net.MalformedURLException e) {
			throw new ServiceException(e);
		}
		return getIdentifierPort(endpoint);
	}

	public com.mbe.umlce.service.IdentifierPortType getIdentifierPort(
			java.net.URL portAddress) throws ServiceException {
		try {
			IdentifierPortBindingStub _stub = new IdentifierPortBindingStub(
					portAddress, this);
			_stub.setPortName(getIdentifierPortWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setIdentifierPortEndpointAddress(String address) {
		IdentifierPort_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws ServiceException {
		try {
			if (com.mbe.umlce.service.IdentifierPortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				IdentifierPortBindingStub _stub = new IdentifierPortBindingStub(
						new java.net.URL(IdentifierPort_address), this);
				_stub.setPortName(getIdentifierPortWSDDServiceName());
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
		if ("IdentifierPort".equals(inputPortName)) {
			return getIdentifierPort();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public QName getServiceName() {
		return new QName("http://imp.service.umce.fyp.com", "Identifier");
	}

	private HashSet ports = null;

	public Iterator getPorts() {
		if (ports == null) {
			ports = new HashSet();
			ports.add(new QName("http://imp.service.umce.fyp.com",
					"IdentifierPort"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(String portName, String address)
			throws ServiceException {

		if ("IdentifierPort".equals(portName)) {
			setIdentifierPortEndpointAddress(address);
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
