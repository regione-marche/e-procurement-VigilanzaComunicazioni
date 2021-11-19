/**
 * AliceProgrammi_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.sil.pt.ws.AliceProgrammi;

public interface AliceProgrammi_PortType extends java.rmi.Remote {
    public java.lang.String getListaInterventi(int contri) throws java.rmi.RemoteException;
    public java.lang.String getSchedaIntervento(int contri, int conint) throws java.rmi.RemoteException;
    public java.lang.String getListaProgrammi(java.lang.String id, int anno, int tipo) throws java.rmi.RemoteException;
    public void setSchedaIntervento(java.lang.String in, int contri, javax.xml.rpc.holders.StringHolder out, javax.xml.rpc.holders.BooleanHolder result) throws java.rmi.RemoteException;
    public it.eldasoft.sil.pt.ws.AliceProgrammi.EsitoInserisciProgramma inserisciProgramma(java.lang.String login, java.lang.String password, java.lang.String id, java.lang.String xmlProgramma) throws java.rmi.RemoteException;
}
