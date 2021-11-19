/**
 * AliceProgrammi_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.sil.pt.ws.AliceProgrammi;

public class AliceProgrammi_ServiceLocator extends org.apache.axis.client.Service implements it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammi_Service {

    public AliceProgrammi_ServiceLocator() {
    }


    public AliceProgrammi_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AliceProgrammi_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AliceProgrammiSOAP
    private java.lang.String AliceProgrammiSOAP_address = "http://localhost:8080/AliceProgrammi/services/AliceProgrammiSOAP";

    public java.lang.String getAliceProgrammiSOAPAddress() {
        return AliceProgrammiSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AliceProgrammiSOAPWSDDServiceName = "AliceProgrammiSOAP";

    public java.lang.String getAliceProgrammiSOAPWSDDServiceName() {
        return AliceProgrammiSOAPWSDDServiceName;
    }

    public void setAliceProgrammiSOAPWSDDServiceName(java.lang.String name) {
        AliceProgrammiSOAPWSDDServiceName = name;
    }

    public it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammi_PortType getAliceProgrammiSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AliceProgrammiSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAliceProgrammiSOAP(endpoint);
    }

    public it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammi_PortType getAliceProgrammiSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammiSOAPStub _stub = new it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammiSOAPStub(portAddress, this);
            _stub.setPortName(getAliceProgrammiSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAliceProgrammiSOAPEndpointAddress(java.lang.String address) {
        AliceProgrammiSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammi_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammiSOAPStub _stub = new it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammiSOAPStub(new java.net.URL(AliceProgrammiSOAP_address), this);
                _stub.setPortName(getAliceProgrammiSOAPWSDDServiceName());
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
        if ("AliceProgrammiSOAP".equals(inputPortName)) {
            return getAliceProgrammiSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.pt.sil.eldasoft.it/AliceProgrammi/", "AliceProgrammi");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.pt.sil.eldasoft.it/AliceProgrammi/", "AliceProgrammiSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AliceProgrammiSOAP".equals(portName)) {
            setAliceProgrammiSOAPEndpointAddress(address);
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
