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
 * Dati di un'immobile.
 * 
 * @author Mirco.Franzoni
 */
public class ImmobileEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private String cui;
	
	private String istat;

	private String nuts;
	
	private Long trasferimentoTitoloCorrispettivo;
	
	private Long immobileDisponibile;
	
	private Long inclusoProgrammaDismissione;
	
	private String descrizione;
	
	private Long tipoDisponibilita;
	
	private Double valoreStimato1;
	
	private Double valoreStimato2;
	
	private Double valoreStimato3;

	private Double valoreStimatoSucc;
	
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

	public void setTrasferimentoTitoloCorrispettivo(
			Long trasferimentoTitoloCorrispettivo) {
		this.trasferimentoTitoloCorrispettivo = trasferimentoTitoloCorrispettivo;
	}

	public Long getTrasferimentoTitoloCorrispettivo() {
		return trasferimentoTitoloCorrispettivo;
	}

	public void setImmobileDisponibile(Long immobileDisponibile) {
		this.immobileDisponibile = immobileDisponibile;
	}

	public Long getImmobileDisponibile() {
		return immobileDisponibile;
	}

	public void setInclusoProgrammaDismissione(
			Long inclusoProgrammaDismissione) {
		this.inclusoProgrammaDismissione = inclusoProgrammaDismissione;
	}

	public Long getInclusoProgrammaDismissione() {
		return inclusoProgrammaDismissione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setValoreStimato1(Double valoreStimato1) {
		this.valoreStimato1 = valoreStimato1;
	}

	public Double getValoreStimato1() {
		return valoreStimato1;
	}

	public void setValoreStimato2(Double valoreStimato2) {
		this.valoreStimato2 = valoreStimato2;
	}

	public Double getValoreStimato2() {
		return valoreStimato2;
	}

	public void setValoreStimato3(Double valoreStimato3) {
		this.valoreStimato3 = valoreStimato3;
	}

	public Double getValoreStimato3() {
		return valoreStimato3;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getCui() {
		return cui;
	}

	public void setTipoDisponibilita(Long tipoDisponibilita) {
		this.tipoDisponibilita = tipoDisponibilita;
	}

	public Long getTipoDisponibilita() {
		return tipoDisponibilita;
	}

	public void setValoreStimatoSucc(Double valoreStimatoSucc) {
		this.valoreStimatoSucc = valoreStimatoSucc;
	}

	public Double getValoreStimatoSucc() {
		return valoreStimatoSucc;
	}
}
