package com.mbe.umlce.service.imp;

import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import com.mbe.umlce.Service;

public class CheckerLocator extends org.apache.axis.client.Service implements
		com.mbe.umlce.service.imp.Checker {

	private static final long serialVersionUID = 1L;

	public CheckerLocator() {
	}

	public CheckerLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public CheckerLocator(String wsdlLoc, QName sName) throws ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for CheckerPort
	private String CheckerPort_address = Service.getURL() + "/plagiarism";

	public String getCheckerPortAddress() {
		return CheckerPort_address;
	}

	// The WSDD service name defaults to the port name.
	private String CheckerPortWSDDServiceName = "CheckerPort";

	public String getCheckerPortWSDDServiceName() {
		return CheckerPortWSDDServiceName;
	}

	public void setCheckerPortWSDDServiceName(String name) {
		CheckerPortWSDDServiceName = name;
	}

	public com.mbe.umlce.service.CheckerPortType getCheckerPort()
			throws ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(CheckerPort_address);
		} catch (java.net.MalformedURLException e) {
			throw new ServiceException(e);
		}
		return getCheckerPort(endpoint);
	}

	public com.mbe.umlce.service.CheckerPortType getCheckerPort(
			java.net.URL portAddress) throws ServiceException {
		try {
			com.mbe.umlce.service.imp.CheckerPortBindingStub _stub = new com.mbe.umlce.service.imp.CheckerPortBindingStub(
					portAddress, this);
			_stub.setPortName(getCheckerPortWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setCheckerPortEndpointAddress(String address) {
		CheckerPort_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws ServiceException {
		try {
			if (com.mbe.umlce.service.CheckerPortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				com.mbe.umlce.service.imp.CheckerPortBindingStub _stub = new com.mbe.umlce.service.imp.CheckerPortBindingStub(
						new java.net.URL(CheckerPort_address), this);
				_stub.setPortName(getCheckerPortWSDDServiceName());
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
		if ("CheckerPort".equals(inputPortName)) {
			return getCheckerPort();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public QName getServiceName() {
		return new QName("http://imp.service.umce.fyp.com", "Checker");
	}

	private HashSet ports = null;

	public Iterator getPorts() {
		if (ports == null) {
			ports = new HashSet();
			ports.add(new QName("http://imp.service.umce.fyp.com",
					"CheckerPort"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(String portName, String address)
			throws ServiceException {

		if ("CheckerPort".equals(portName)) {
			setCheckerPortEndpointAddress(address);
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
