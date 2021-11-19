/**
 * CUPWSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

public interface CUPWSService extends javax.xml.rpc.Service {
    public java.lang.String getCUPWSAddress();

    public it.eldasoft.cup.ws.CUPWS getCUPWS() throws javax.xml.rpc.ServiceException;

    public it.eldasoft.cup.ws.CUPWS getCUPWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
