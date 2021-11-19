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
 * Dati di un intervento non riproposto.
 * 
 * @author Mirco.Franzoni
 */
public class InterventoNonRipropostoEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private String cui;

	private String cup;
	
	private String descrizione;
	
	private Double importo;
	
	private Long priorita;
	
	private String motivo;

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getCui() {
		return cui;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCup() {
		return cup;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public Double getImporto() {
		return importo;
	}

	public void setPriorita(Long priorita) {
		this.priorita = priorita;
	}

	public Long getPriorita() {
		return priorita;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getMotivo() {
		return motivo;
	}
}
