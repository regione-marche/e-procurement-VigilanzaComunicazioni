/**
 * WsOsservatorio_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatsa.client;

public interface WsOsservatorio_PortType extends java.rmi.Remote {
    public it.toscana.rete.rfc.sitatsa.client.ResponseConsultaGara getGaraXML(java.lang.String codiceCIG, java.lang.String idGara, java.lang.String cfrup, java.lang.String cfsa, boolean controlliPreliminari) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatsa.client.ResponseElencoFeedback getElencoFeedback(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatsa.client.TipoFeedbackType tipoFeedBack, it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatsa.client.ResponseElencoSchedeType getElencoSchede(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatsa.client.ResponseSchedaType getScheda(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatsa.client.ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegata(java.lang.String idAvGara, java.lang.String cfRup, java.lang.String indiceColl) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatsa.client.ResponseLoginRPNT getLoginRPNT(java.lang.String cfRup) throws java.rmi.RemoteException;
}
