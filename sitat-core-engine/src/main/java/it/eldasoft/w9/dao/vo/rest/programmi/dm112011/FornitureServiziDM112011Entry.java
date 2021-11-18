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

/**
 * Dati di un intervento forniture e servizi.
 * 
 * @author Mirco.Franzoni
 */
public class FornitureServiziDM112011Entry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private String codiceInterno;
	
	private String descrizione;
	
	private String istat;

	private String nuts;
	
	private String esenteCup;
	
	private String cup;
	
	private String cpv;
	
	private String settore;
	
	private Long priorita;
	
	private String normativaRiferimento;
	
	private String strumentoProgrammazione;
	
	private String previstaManodopera;
	
	private Double risorseMutuo;
	
	private Double risorsePrivati;
	
	private Double risorseBilancio;
	
	private Double risorseAltro;

	private Double importoRisorseFinanziarie;

	private Double importoRisorseFinanziarieRegionali;

	private Long meseAvvioProcedura;
	
	private DatiGeneraliTecnicoEntry rup;
	
	public void setSettore(String settore) {
		this.settore = settore;
	}

	public String getSettore() {
		return settore;
	}

	public void setCodiceInterno(String codiceInterno) {
		this.codiceInterno = codiceInterno;
	}

	public String getCodiceInterno() {
		return codiceInterno;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setEsenteCup(String esenteCup) {
		this.esenteCup = esenteCup;
	}

	public String getEsenteCup() {
		return esenteCup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCup() {
		return cup;
	}

	public void setCpv(String cpv) {
		this.cpv = cpv;
	}

	public String getCpv() {
		return cpv;
	}

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

	public void setPriorita(Long priorita) {
		this.priorita = priorita;
	}

	public Long getPriorita() {
		return priorita;
	}

	public void setRup(DatiGeneraliTecnicoEntry rup) {
		this.rup = rup;
	}

	public DatiGeneraliTecnicoEntry getRup() {
		return rup;
	}

	public void setNormativaRiferimento(String normativaRiferimento) {
		this.normativaRiferimento = normativaRiferimento;
	}

	public String getNormativaRiferimento() {
		return normativaRiferimento;
	}

	public void setStrumentoProgrammazione(String strumentoProgrammazione) {
		this.strumentoProgrammazione = strumentoProgrammazione;
	}

	public String getStrumentoProgrammazione() {
		return strumentoProgrammazione;
	}

	public void setPrevistaManodopera(String previstaManodopera) {
		this.previstaManodopera = previstaManodopera;
	}

	public String getPrevistaManodopera() {
		return previstaManodopera;
	}

	public void setRisorseMutuo(Double risorseMutuo) {
		this.risorseMutuo = risorseMutuo;
	}

	public Double getRisorseMutuo() {
		return risorseMutuo;
	}

	public void setRisorsePrivati(Double risorsePrivati) {
		this.risorsePrivati = risorsePrivati;
	}

	public Double getRisorsePrivati() {
		return risorsePrivati;
	}

	public void setRisorseBilancio(Double risorseBilancio) {
		this.risorseBilancio = risorseBilancio;
	}

	public Double getRisorseBilancio() {
		return risorseBilancio;
	}

	public void setRisorseAltro(Double risorseAltro) {
		this.risorseAltro = risorseAltro;
	}

	public Double getRisorseAltro() {
		return risorseAltro;
	}

	public void setImportoRisorseFinanziarie(Double importoRisorseFinanziarie) {
		this.importoRisorseFinanziarie = importoRisorseFinanziarie;
	}

	public Double getImportoRisorseFinanziarie() {
		return importoRisorseFinanziarie;
	}

	public void setImportoRisorseFinanziarieRegionali(
			Double importoRisorseFinanziarieRegionali) {
		this.importoRisorseFinanziarieRegionali = importoRisorseFinanziarieRegionali;
	}

	public Double getImportoRisorseFinanziarieRegionali() {
		return importoRisorseFinanziarieRegionali;
	}

	public void setMeseAvvioProcedura(Long meseAvvioProcedura) {
		this.meseAvvioProcedura = meseAvvioProcedura;
	}

	public Long getMeseAvvioProcedura() {
		return meseAvvioProcedura;
	}
}
