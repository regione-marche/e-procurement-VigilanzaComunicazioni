/**
 * WsOsservatorio_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatsa.client;

public class WsOsservatorio_ServiceLocator extends org.apache.axis.client.Service implements it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_Service {

    public WsOsservatorio_ServiceLocator() {
    }


    public WsOsservatorio_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WsOsservatorio_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WsOsservatorio
    private java.lang.String WsOsservatorio_address = "http://localhost:8080/OsservatorioServer/services/WsOsservatorio";

    public java.lang.String getWsOsservatorioAddress() {
        return WsOsservatorio_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WsOsservatorioWSDDServiceName = "WsOsservatorio";

    public java.lang.String getWsOsservatorioWSDDServiceName() {
        return WsOsservatorioWSDDServiceName;
    }

    public void setWsOsservatorioWSDDServiceName(java.lang.String name) {
        WsOsservatorioWSDDServiceName = name;
    }

    public it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_PortType getWsOsservatorio() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WsOsservatorio_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWsOsservatorio(endpoint);
    }

    public it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_PortType getWsOsservatorio(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.toscana.rete.rfc.sitatsa.client.SitatSoapBindingStub _stub = new it.toscana.rete.rfc.sitatsa.client.SitatSoapBindingStub(portAddress, this);
            _stub.setPortName(getWsOsservatorioWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWsOsservatorioEndpointAddress(java.lang.String address) {
        WsOsservatorio_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.toscana.rete.rfc.sitatsa.client.SitatSoapBindingStub _stub = new it.toscana.rete.rfc.sitatsa.client.SitatSoapBindingStub(new java.net.URL(WsOsservatorio_address), this);
                _stub.setPortName(getWsOsservatorioWSDDServiceName());
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
        if ("WsOsservatorio".equals(inputPortName)) {
            return getWsOsservatorio();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "WsOsservatorio");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://rete.toscana.it/rfc/sitatort/", "WsOsservatorio"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WsOsservatorio".equals(portName)) {
            setWsOsservatorioEndpointAddress(address);
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
