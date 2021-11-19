/**
 * CUPWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

public interface CUPWS extends java.rmi.Remote {
    public it.eldasoft.cup.ws.EsitoInserisciCUP inserisciCUP(java.lang.String login, java.lang.String password, java.lang.String codein, it.eldasoft.cup.ws.DatiCUP datiCUP) throws java.rmi.RemoteException;
    public it.eldasoft.cup.ws.EsitoConsultaCUP consultaCUP(java.lang.String uuid) throws java.rmi.RemoteException;
    public it.eldasoft.cup.ws.StazioneAppaltanteType[] listaStazioniAppaltanti(java.lang.String in) throws java.rmi.RemoteException;
}
