/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.eldasoft.w9.dao.vo.rest.programmi.lavori;

import it.eldasoft.w9.dao.vo.rest.DatiGeneraliTecnicoEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Dati per la pubblicazione di un programma per lavori.
 * 
 * @author Mirco.Franzoni
 */
public class PubblicaProgrammaLavoriEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private String id;
	
	private String codiceFiscaleSA;

	private String ufficio;
	  
	private Long anno;

	private String descrizione;
	
	private String numeroApprovazione;
	
	private String dataApprovazione; 

	private String dataPubblicazioneApprovazione; 
	  
	private String titoloAttoApprovazione;
		
	private String urlAttoApprovazione;
	
	private String numeroAdozione;
	
	private String dataAdozione; 

	private String dataPubblicazioneAdozione; 
	  
	private String titoloAttoAdozione;
		
	private String urlAttoAdozione;
	
	private DatiGeneraliTecnicoEntry referente;
	
	private List<OperaIncompiutaEntry> opereIncompiute = new ArrayList<OperaIncompiutaEntry>();

	private List<InterventoEntry> interventi = new ArrayList<InterventoEntry>();

	private List<InterventoNonRipropostoEntry> interventiNonRiproposti = new ArrayList<InterventoNonRipropostoEntry>();

	private Long idRicevuto;
	
	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setCodiceFiscaleSA(String codiceFiscaleSA) {
		this.codiceFiscaleSA = codiceFiscaleSA;
	}

	public String getCodiceFiscaleSA() {
		return codiceFiscaleSA;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	public Long getAnno() {
		return anno;
	}

	public void setNumeroApprovazione(String numeroApprovazione) {
		this.numeroApprovazione = numeroApprovazione;
	}

	public String getNumeroApprovazione() {
		return numeroApprovazione;
	}

	public void setDataApprovazione(String dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}

	public String getDataApprovazione() {
		return dataApprovazione;
	}

	public void setDataPubblicazioneApprovazione(
			String dataPubblicazioneApprovazione) {
		this.dataPubblicazioneApprovazione = dataPubblicazioneApprovazione;
	}

	public String getDataPubblicazioneApprovazione() {
		return dataPubblicazioneApprovazione;
	}

	public void setTitoloAttoApprovazione(String titoloAttoApprovazione) {
		this.titoloAttoApprovazione = titoloAttoApprovazione;
	}

	public String getTitoloAttoApprovazione() {
		return titoloAttoApprovazione;
	}

	public void setUrlAttoApprovazione(String urlAttoApprovazione) {
		this.urlAttoApprovazione = urlAttoApprovazione;
	}

	public String getUrlAttoApprovazione() {
		return urlAttoApprovazione;
	}

	public void setNumeroAdozione(String numeroAdozione) {
		this.numeroAdozione = numeroAdozione;
	}

	public String getNumeroAdozione() {
		return numeroAdozione;
	}

	public void setDataAdozione(String dataAdozione) {
		this.dataAdozione = dataAdozione;
	}

	public String getDataAdozione() {
		return dataAdozione;
	}

	public void setDataPubblicazioneAdozione(String dataPubblicazioneAdozione) {
		this.dataPubblicazioneAdozione = dataPubblicazioneAdozione;
	}

	public String getDataPubblicazioneAdozione() {
		return dataPubblicazioneAdozione;
	}

	public void setTitoloAttoAdozione(String titoloAttoAdozione) {
		this.titoloAttoAdozione = titoloAttoAdozione;
	}

	public String getTitoloAttoAdozione() {
		return titoloAttoAdozione;
	}

	public void setUrlAttoAdozione(String urlAttoAdozione) {
		this.urlAttoAdozione = urlAttoAdozione;
	}

	public String getUrlAttoAdozione() {
		return urlAttoAdozione;
	}

	public void setReferente(DatiGeneraliTecnicoEntry referente) {
		this.referente = referente;
	}

	public DatiGeneraliTecnicoEntry getReferente() {
		return referente;
	}

	public void setOpereIncompiute(List<OperaIncompiutaEntry> opereIncompiute) {
		this.opereIncompiute = opereIncompiute;
	}

	public List<OperaIncompiutaEntry> getOpereIncompiute() {
		return opereIncompiute;
	}

	public void setInterventi(List<InterventoEntry> interventi) {
		this.interventi = interventi;
	}

	public List<InterventoEntry> getInterventi() {
		return interventi;
	}

	public void setInterventiNonRiproposti(List<InterventoNonRipropostoEntry> interventiNonRiproposti) {
		this.interventiNonRiproposti = interventiNonRiproposti;
	}

	public List<InterventoNonRipropostoEntry> getInterventiNonRiproposti() {
		return interventiNonRiproposti;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getUfficio() {
		return ufficio;
	}

}
