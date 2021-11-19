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

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Dati storico anagrafica UFFINT.
 *
 * @author Mirco.Franzoni
 */
    
public class StoricoAnagraficaEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4433185026855332865L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private String dataFineValidita;
	
	private String denominazione;
	
	private String indirizzo;
	
	private String civico;
	
	private String codiceIstat;
	
	private String localita;
	
	private String provincia; 
	
	private String cap;

	private Integer codiceNazione;
	
	private String telefono;
	
	private String fax;
	
	private String codiceFiscale;
	
	private Long tipoAmministrazione;	
	
	private String email;
	
	private String pec;
	
	private String iscuc;
	
	private String cfAnac;

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCivico() {
		return civico;
	}

	public void setCodiceIstat(String codiceIstat) {
		this.codiceIstat = codiceIstat;
	}

	public String getCodiceIstat() {
		return codiceIstat;
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

	public void setCodiceNazione(Integer codiceNazione) {
		this.codiceNazione = codiceNazione;
	}

	public Integer getCodiceNazione() {
		return codiceNazione;
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

	public void setTipoAmministrazione(Long tipoAmministrazione) {
		this.tipoAmministrazione = tipoAmministrazione;
	}

	public Long getTipoAmministrazione() {
		return tipoAmministrazione;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getPec() {
		return pec;
	}

	public void setIscuc(String iscuc) {
		this.iscuc = iscuc;
	}

	public String getIscuc() {
		return iscuc;
	}

	public void setCfAnac(String cfAnac) {
		this.cfAnac = cfAnac;
	}

	public String getCfAnac() {
		return cfAnac;
	}

	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getDataFineValidita() {
		return dataFineValidita;
	}

}
