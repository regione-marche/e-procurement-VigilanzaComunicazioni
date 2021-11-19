/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.eldasoft.w9.dao.vo.rest.programmi.fornitureservizi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eldasoft.w9.dao.vo.rest.DatiGeneraliTecnicoEntry;

/**
 * Dati per la pubblicazione di un programma per forniture e servizi.
 * 
 * @author Mirco.Franzoni
 */
public class PubblicaProgrammaFornitureServiziEntry implements Serializable {
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
	
	private DatiGeneraliTecnicoEntry referente;
	
	private List<AcquistoEntry> acquisti = new ArrayList<AcquistoEntry>();

	private List<AcquistoNonRipropostoEntry> acquistiNonRiproposti = new ArrayList<AcquistoNonRipropostoEntry>();

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

	public void setReferente(DatiGeneraliTecnicoEntry referente) {
		this.referente = referente;
	}

	public DatiGeneraliTecnicoEntry getReferente() {
		return referente;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setAcquisti(List<AcquistoEntry> acquisti) {
		this.acquisti = acquisti;
	}

	public List<AcquistoEntry> getAcquisti() {
		return acquisti;
	}

	public void setAcquistiNonRiproposti(List<AcquistoNonRipropostoEntry> acquistiNonRiproposti) {
		this.acquistiNonRiproposti = acquistiNonRiproposti;
	}

	public List<AcquistoNonRipropostoEntry> getAcquistiNonRiproposti() {
		return acquistiNonRiproposti;
	}

}
