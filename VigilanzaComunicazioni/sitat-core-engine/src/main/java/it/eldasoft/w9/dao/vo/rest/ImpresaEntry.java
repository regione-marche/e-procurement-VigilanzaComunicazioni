/*
 * Created on 01/giu/2017
 *
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.eldasoft.w9.dao.vo.rest;

import java.io.Serializable;

/**
 * Dati generali di una impresa.
 *
 * @author Mirco.Franzoni
 */

public class ImpresaEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private String ragioneSociale;

	private Long formaGiuridica;
	
	private String indirizzo;

	private String numeroCivico;

	private String localita;

	private String provincia;

	private String cap;

	private String telefono;

	private String fax;

	private String codiceFiscale;

	private String partitaIva;

	private String numeroCCIAA;
	
	private Long codiceNazione;

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public String getLocalita() {
		return localita;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCap() {
		return cap;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFax() {
		return fax;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setNumeroCCIAA(String numeroCCIAA) {
		this.numeroCCIAA = numeroCCIAA;
	}

	public String getNumeroCCIAA() {
		return numeroCCIAA;
	}

	public void setFormaGiuridica(Long formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}

	public Long getFormaGiuridica() {
		return formaGiuridica;
	}

	public void setCodiceNazione(Long codiceNazione) {
		this.codiceNazione = codiceNazione;
	}

	public Long getCodiceNazione() {
		return codiceNazione;
	}


}
