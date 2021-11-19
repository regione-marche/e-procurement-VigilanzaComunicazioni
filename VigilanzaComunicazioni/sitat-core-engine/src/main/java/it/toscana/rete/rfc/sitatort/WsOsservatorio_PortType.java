/**
 * WsOsservatorio_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatort;

public interface WsOsservatorio_PortType extends java.rmi.Remote {
    public it.toscana.rete.rfc.sitatort.ResponseConsultaGara getGaraXML(java.lang.String codiceCIG, java.lang.String idGara, java.lang.String cfrup, java.lang.String cfsa, boolean controlliPreliminari) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatort.ResponseElencoFeedback getElencoFeedback(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatort.TipoFeedbackType tipoFeedBack, it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType getElencoSchede(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatort.ResponseSchedaType getScheda(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegata(java.lang.String idAvGara, java.lang.String cfRup, java.lang.String indiceColl) throws java.rmi.RemoteException;
    public it.toscana.rete.rfc.sitatort.ResponseLoginRPNT getLoginRPNT(java.lang.String cfRup) throws java.rmi.RemoteException;
}
