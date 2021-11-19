/**
 * CUPWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

public class CUPWSServiceLocator extends org.apache.axis.client.Service implements it.eldasoft.cup.ws.CUPWSService {

    public CUPWSServiceLocator() {
    }


    public CUPWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CUPWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CUPWS
    private java.lang.String CUPWS_address = "http://localhost:8080/AliceProgrammi/services/CUPWS";

    public java.lang.String getCUPWSAddress() {
        return CUPWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CUPWSWSDDServiceName = "CUPWS";

    public java.lang.String getCUPWSWSDDServiceName() {
        return CUPWSWSDDServiceName;
    }

    public void setCUPWSWSDDServiceName(java.lang.String name) {
        CUPWSWSDDServiceName = name;
    }

    public it.eldasoft.cup.ws.CUPWS getCUPWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CUPWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCUPWS(endpoint);
    }

    public it.eldasoft.cup.ws.CUPWS getCUPWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eldasoft.cup.ws.CUPWSBindingStub _stub = new it.eldasoft.cup.ws.CUPWSBindingStub(portAddress, this);
            _stub.setPortName(getCUPWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCUPWSEndpointAddress(java.lang.String address) {
        CUPWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eldasoft.cup.ws.CUPWS.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eldasoft.cup.ws.CUPWSBindingStub _stub = new it.eldasoft.cup.ws.CUPWSBindingStub(new java.net.URL(CUPWS_address), this);
                _stub.setPortName(getCUPWSWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CUPWS".equals(inputPortName)) {
            return getCUPWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "CUPWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.cup.eldasoft.it/", "CUPWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CUPWS".equals(portName)) {
            setCUPWSEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
