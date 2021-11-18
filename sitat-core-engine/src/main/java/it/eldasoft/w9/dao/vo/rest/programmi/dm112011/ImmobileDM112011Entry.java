/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.eldasoft.w9.dao.vo.rest.programmi.dm112011;

import java.io.Serializable;

/**
 * Dati di un immobile.
 * 
 * @author Mirco.Franzoni
 */
public class ImmobileDM112011Entry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private String descrizione;
	
	private Long tipoProprieta;

	private Double valoreStimato;

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setTipoProprieta(Long tipoProprieta) {
		this.tipoProprieta = tipoProprieta;
	}

	public Long getTipoProprieta() {
		return tipoProprieta;
	}

	public void setValoreStimato(Double valoreStimato) {
		this.valoreStimato = valoreStimato;
	}

	public Double getValoreStimato() {
		return valoreStimato;
	}

}
