/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.eldasoft.w9.dao.vo.rest.programmi.lavori;

import java.io.Serializable;

/**
 * Altri Dati di un'opera incompiuta.
 * 
 * @author Mirco.Franzoni
 */
public class AltriDatiOperaIncompiutaEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private String istat;

	private String nuts;
	
	private String tipologiaIntervento;
	
	private String categoriaIntervento;
	
	private String requisitiCapitolato;
	
	private String requisitiApprovato;
	
	private String unitaMisura;

	private Double dimensione;
	
	private String sponsorizzazione;
	
	private String finanzaDiProgetto;
	
	private Double costoProgetto;
	
	private Double finanziamento;
	
	private String coperturaStatale;
	
	private String coperturaRegionale;
	
	private String coperturaProvinciale;
	
	private String coperturaComunale;
	
	private String coperturaAltro;
	
	private String coperturaComunitaria;
	
	private String coperturaPrivata;

	public void setIstat(String istat) {
		this.istat = istat;
	}

	public String getIstat() {
		return istat;
	}

	public void setNuts(String nuts) {
		this.nuts = nuts;
	}

	public String getNuts() {
		return nuts;
	}

	public void setTipologiaIntervento(String tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}

	public String getTipologiaIntervento() {
		return tipologiaIntervento;
	}

	public void setCategoriaIntervento(String categoriaIntervento) {
		this.categoriaIntervento = categoriaIntervento;
	}

	public String getCategoriaIntervento() {
		return categoriaIntervento;
	}

	public void setRequisitiCapitolato(String requisitiCapitolato) {
		this.requisitiCapitolato = requisitiCapitolato;
	}

	public String getRequisitiCapitolato() {
		return requisitiCapitolato;
	}

	public void setRequisitiApprovato(String requisitiApprovato) {
		this.requisitiApprovato = requisitiApprovato;
	}

	public String getRequisitiApprovato() {
		return requisitiApprovato;
	}

	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	public String getUnitaMisura() {
		return unitaMisura;
	}

	public void setDimensione(Double dimensione) {
		this.dimensione = dimensione;
	}

	public Double getDimensione() {
		return dimensione;
	}

	public void setSponsorizzazione(String sponsorizzazione) {
		this.sponsorizzazione = sponsorizzazione;
	}

	public String getSponsorizzazione() {
		return sponsorizzazione;
	}

	public void setFinanzaDiProgetto(String finanzaDiProgetto) {
		this.finanzaDiProgetto = finanzaDiProgetto;
	}

	public String getFinanzaDiProgetto() {
		return finanzaDiProgetto;
	}

	public void setCostoProgetto(Double costoProgetto) {
		this.costoProgetto = costoProgetto;
	}

	public Double getCostoProgetto() {
		return costoProgetto;
	}

	public void setFinanziamento(Double finanziamento) {
		this.finanziamento = finanziamento;
	}

	public Double getFinanziamento() {
		return finanziamento;
	}

	public void setCoperturaStatale(String coperturaStatale) {
		this.coperturaStatale = coperturaStatale;
	}

	public String getCoperturaStatale() {
		return coperturaStatale;
	}

	public void setCoperturaRegionale(String coperturaRegionale) {
		this.coperturaRegionale = coperturaRegionale;
	}

	public String getCoperturaRegionale() {
		return coperturaRegionale;
	}

	public void setCoperturaProvinciale(String coperturaProvinciale) {
		this.coperturaProvinciale = coperturaProvinciale;
	}

	public String getCoperturaProvinciale() {
		return coperturaProvinciale;
	}

	public void setCoperturaComunale(String coperturaComunale) {
		this.coperturaComunale = coperturaComunale;
	}

	public String getCoperturaComunale() {
		return coperturaComunale;
	}

	public void setCoperturaAltro(String coperturaAltro) {
		this.coperturaAltro = coperturaAltro;
	}

	public String getCoperturaAltro() {
		return coperturaAltro;
	}

	public void setCoperturaComunitaria(String coperturaComunitaria) {
		this.coperturaComunitaria = coperturaComunitaria;
	}

	public String getCoperturaComunitaria() {
		return coperturaComunitaria;
	}

	public void setCoperturaPrivata(String coperturaPrivata) {
		this.coperturaPrivata = coperturaPrivata;
	}

	public String getCoperturaPrivata() {
		return coperturaPrivata;
	}
	
}
