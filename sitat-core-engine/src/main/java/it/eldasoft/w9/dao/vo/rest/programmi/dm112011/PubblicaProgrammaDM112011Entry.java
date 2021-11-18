/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.eldasoft.w9.dao.vo.rest.programmi.dm112011;

import it.eldasoft.w9.dao.vo.rest.DatiGeneraliTecnicoEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Dati per la pubblicazione di un programma.
 * 
 * @author Mirco.Franzoni
 */
public class PubblicaProgrammaDM112011Entry implements Serializable {
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
	
	private String note1;
	
	private String note2;
	
	private String note2b;
	
	private String note3;
	
	private String note4;
	
	private Double accantonamento;
	
	private DatiGeneraliTecnicoEntry referente;
	
	private List<InterventoDM112011Entry> interventi = new ArrayList<InterventoDM112011Entry>();

	private List<FornitureServiziDM112011Entry> fornitureEServizi = new ArrayList<FornitureServiziDM112011Entry>();

	private List<LavoroEconomiaEntry> lavoriEconomia = new ArrayList<LavoroEconomiaEntry>();
	
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

	public void setLavoriEconomia(List<LavoroEconomiaEntry> lavoriEconomia) {
		this.lavoriEconomia = lavoriEconomia;
	}

	public List<LavoroEconomiaEntry> getLavoriEconomia() {
		return lavoriEconomia;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNote1() {
		return note1;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getNote2() {
		return note2;
	}

	public void setNote2b(String note2b) {
		this.note2b = note2b;
	}

	public String getNote2b() {
		return note2b;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}

	public String getNote3() {
		return note3;
	}

	public void setNote4(String note4) {
		this.note4 = note4;
	}

	public String getNote4() {
		return note4;
	}

	public void setAccantonamento(Double accantonamento) {
		this.accantonamento = accantonamento;
	}

	public Double getAccantonamento() {
		return accantonamento;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setInterventi(List<InterventoDM112011Entry> interventi) {
		this.interventi = interventi;
	}

	public List<InterventoDM112011Entry> getInterventi() {
		return interventi;
	}

	public void setFornitureEServizi(List<FornitureServiziDM112011Entry> fornitureEServizi) {
		this.fornitureEServizi = fornitureEServizi;
	}

	public List<FornitureServiziDM112011Entry> getFornitureEServizi() {
		return fornitureEServizi;
	}

}
