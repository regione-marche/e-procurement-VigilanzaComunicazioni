/**
 * ElaborazioniCUP_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.mef.serviziCUP;

public interface ElaborazioniCUP_PortType extends java.rmi.Remote {
    public it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type richiestaRispostaSincrona_RichiestaDettaglioCUP(it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type parameters) throws java.rmi.RemoteException;
    public it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_ListaCUP_Type richiestaRispostaSincrona_ListaCUP(it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaListaCUP_Type parameters) throws java.rmi.RemoteException;
    public it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_EsitoGenerazioneCUP_Type richiestaRispostaSincrona_RichiestaGenerazioneCUP(it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type parameters) throws java.rmi.RemoteException;
    public it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_RisultatoChiusuraRevocaCUP_Type richiestaRispostaSincrona_RichiestaChiusuraRevocaCUP(it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaChiusuraRevocaCUP_Type parameters) throws java.rmi.RemoteException;
}
