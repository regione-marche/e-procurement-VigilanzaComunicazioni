/**
 * ElaborazioniCUP_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.mef.serviziCUP;

public class ElaborazioniCUP_ServiceLocator extends org.apache.axis.client.Service implements it.mef.serviziCUP.ElaborazioniCUP_Service {

    public ElaborazioniCUP_ServiceLocator() {
    }


    public ElaborazioniCUP_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ElaborazioniCUP_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ElaborazioniCUPPort
    private java.lang.String ElaborazioniCUPPort_address = "http://localhost:8080/ServiziCUPTest/services/ElaborazioniCUP";

    public java.lang.String getElaborazioniCUPPortAddress() {
        return ElaborazioniCUPPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ElaborazioniCUPPortWSDDServiceName = "ElaborazioniCUPPort";

    public java.lang.String getElaborazioniCUPPortWSDDServiceName() {
        return ElaborazioniCUPPortWSDDServiceName;
    }

    public void setElaborazioniCUPPortWSDDServiceName(java.lang.String name) {
        ElaborazioniCUPPortWSDDServiceName = name;
    }

    public it.mef.serviziCUP.ElaborazioniCUP_PortType getElaborazioniCUPPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ElaborazioniCUPPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getElaborazioniCUPPort(endpoint);
    }

    public it.mef.serviziCUP.ElaborazioniCUP_PortType getElaborazioniCUPPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.mef.serviziCUP.ElaborazioniCUPSoapStub _stub = new it.mef.serviziCUP.ElaborazioniCUPSoapStub(portAddress, this);
            _stub.setPortName(getElaborazioniCUPPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setElaborazioniCUPPortEndpointAddress(java.lang.String address) {
        ElaborazioniCUPPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.mef.serviziCUP.ElaborazioniCUP_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.mef.serviziCUP.ElaborazioniCUPSoapStub _stub = new it.mef.serviziCUP.ElaborazioniCUPSoapStub(new java.net.URL(ElaborazioniCUPPort_address), this);
                _stub.setPortName(getElaborazioniCUPPortWSDDServiceName());
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
        if ("ElaborazioniCUPPort".equals(inputPortName)) {
            return getElaborazioniCUPPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://serviziCUP.mef.it", "ElaborazioniCUP");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://serviziCUP.mef.it", "ElaborazioniCUPPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ElaborazioniCUPPort".equals(portName)) {
            setElaborazioniCUPPortEndpointAddress(address);
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
