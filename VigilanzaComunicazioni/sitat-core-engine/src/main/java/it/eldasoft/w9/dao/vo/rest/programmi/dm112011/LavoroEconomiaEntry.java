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
 * Dati di un lavoro in economia.
 * 
 * @author Mirco.Franzoni
 */

public class LavoroEconomiaEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;
	
	private String descrizione;
	
	private String cup;

	private Double stimaLavori;

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCup() {
		return cup;
	}

	public void setStimaLavori(Double stimaLavori) {
		this.stimaLavori = stimaLavori;
	}

	public Double getStimaLavori() {
		return stimaLavori;
	}

}
