/**
 * ServicesServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.avlp.smartCig.ws;

public class ServicesServiceLocator extends org.apache.axis.client.Service implements ServicesService {

    public ServicesServiceLocator() {
    }


    public ServicesServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicesServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServicesSoap11
    private java.lang.String ServicesSoap11_address = "http://localhost:8080/TracciabilitaWS/ServizioGestioneComunicazioni";

    public java.lang.String getServicesSoap11Address() {
        return ServicesSoap11_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServicesSoap11WSDDServiceName = "ServicesSoap11";

    public java.lang.String getServicesSoap11WSDDServiceName() {
        return ServicesSoap11WSDDServiceName;
    }

    public void setServicesSoap11WSDDServiceName(java.lang.String name) {
        ServicesSoap11WSDDServiceName = name;
    }

    public Services getServicesSoap11() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServicesSoap11_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServicesSoap11(endpoint);
    }

    public Services getServicesSoap11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ServicesSoap11Stub _stub = new ServicesSoap11Stub(portAddress, this);
            _stub.setPortName(getServicesSoap11WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServicesSoap11EndpointAddress(java.lang.String address) {
        ServicesSoap11_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (Services.class.isAssignableFrom(serviceEndpointInterface)) {
                ServicesSoap11Stub _stub = new ServicesSoap11Stub(new java.net.URL(ServicesSoap11_address), this);
                _stub.setPortName(getServicesSoap11WSDDServiceName());
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
        if ("ServicesSoap11".equals(inputPortName)) {
            return getServicesSoap11();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "ServicesService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("smartCig.ws.comunicazioni_v1.0.0", "ServicesSoap11"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServicesSoap11".equals(portName)) {
            setServicesSoap11EndpointAddress(address);
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
