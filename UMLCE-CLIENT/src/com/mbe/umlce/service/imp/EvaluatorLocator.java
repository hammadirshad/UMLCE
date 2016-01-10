package com.mbe.umlce.service.imp;

import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import com.mbe.umlce.Service;

public class EvaluatorLocator extends org.apache.axis.client.Service implements Evaluator {

  private static final long serialVersionUID = 1L;

  public EvaluatorLocator() {}

  public EvaluatorLocator(org.apache.axis.EngineConfiguration config) {
    super(config);
  }

  public EvaluatorLocator(String wsdlLoc, QName sName) throws ServiceException {
    super(wsdlLoc, sName);
  }

  private String EvaluatorPort_address = Service.getURL() + "/evaluator";

  public String getEvaluatorPortAddress() {
    return EvaluatorPort_address;
  }

  // The WSDD service name defaults to the port name.
  private String EvaluatorPortWSDDServiceName = "EvaluatorPort";

  public String getEvaluatorPortWSDDServiceName() {
    return EvaluatorPortWSDDServiceName;
  }

  public void setEvaluatorPortWSDDServiceName(String name) {
    EvaluatorPortWSDDServiceName = name;
  }

  public com.mbe.umlce.service.EvaluatorPortType getEvaluatorPort() throws ServiceException {
    java.net.URL endpoint;
    try {
      endpoint = new java.net.URL(EvaluatorPort_address);
    } catch (java.net.MalformedURLException e) {
      throw new ServiceException(e);
    }
    return getEvaluatorPort(endpoint);
  }

  public com.mbe.umlce.service.EvaluatorPortType getEvaluatorPort(java.net.URL portAddress)
      throws ServiceException {
    try {
      EvaluatorPortBindingStub _stub = new EvaluatorPortBindingStub(portAddress, this);
      _stub.setPortName(getEvaluatorPortWSDDServiceName());
      return _stub;
    } catch (org.apache.axis.AxisFault e) {
      return null;
    }
  }

  public void setEvaluatorPortEndpointAddress(String address) {
    EvaluatorPort_address = address;
  }

  /**
   * For the given interface, get the stub implementation. If this service has no port for the given
   * interface, then ServiceException is thrown.
   */
  public java.rmi.Remote getPort(Class serviceEndpointInterface) throws ServiceException {
    try {
      if (com.mbe.umlce.service.EvaluatorPortType.class
          .isAssignableFrom(serviceEndpointInterface)) {
        EvaluatorPortBindingStub _stub =
            new EvaluatorPortBindingStub(new java.net.URL(EvaluatorPort_address), this);
        _stub.setPortName(getEvaluatorPortWSDDServiceName());
        return _stub;
      }
    } catch (java.lang.Throwable t) {
      throw new ServiceException(t);
    }
    throw new ServiceException("There is no stub implementation for the interface:  "
        + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
  }

  /**
   * For the given interface, get the stub implementation. If this service has no port for the given
   * interface, then ServiceException is thrown.
   */
  public java.rmi.Remote getPort(QName portName, Class serviceEndpointInterface)
      throws ServiceException {
    if (portName == null) {
      return getPort(serviceEndpointInterface);
    }
    String inputPortName = portName.getLocalPart();
    if ("EvaluatorPort".equals(inputPortName)) {
      return getEvaluatorPort();
    } else {
      java.rmi.Remote _stub = getPort(serviceEndpointInterface);
      ((org.apache.axis.client.Stub) _stub).setPortName(portName);
      return _stub;
    }
  }

  public QName getServiceName() {
    return new QName("http://imp.service.umce.fyp.com", "Evaluator");
  }

  private HashSet ports = null;

  public Iterator getPorts() {
    if (ports == null) {
      ports = new HashSet();
      ports.add(new QName("http://imp.service.umce.fyp.com", "EvaluatorPort"));
    }
    return ports.iterator();
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(String portName, String address) throws ServiceException {

    if ("EvaluatorPort".equals(portName)) {
      setEvaluatorPortEndpointAddress(address);
    } else { // Unknown Port Name
      throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
    }
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(QName portName, String address) throws ServiceException {
    setEndpointAddress(portName.getLocalPart(), address);
  }

}
