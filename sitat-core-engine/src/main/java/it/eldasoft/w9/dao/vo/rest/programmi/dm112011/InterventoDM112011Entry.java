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
 * Dati di un intervento.
 * 
 * @author Mirco.Franzoni
 */
public class InterventoDM112011Entry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private Long numeroProgressivo;
	
	private String codiceInterno;
	
	private String descrizione;
	
	private Long annualitaRiferimento;
	
	private String tipologia;
	
	private String categoria;
	
	private String subCategoria;
	
	private String istat;

	private String nuts;
	
	private String esenteCup;
	
	private String cup;
	
	private String cpv;
	
	private String annuale;
	
	private String finalita;
	
	private Long priorita;
	
	private String conformitaUrbanistica;
	
	private String conformitaAmbientale;
	
	private String statoProgettazione;
	
	private Long annoInizioLavori;
	
	private Long trimestreInizioLavori;
	
	private Long annoFineLavori;
	
	private Long trimestreFineLavori;
	
	private Double risorseVincolatePerLegge1;
	
	private Double risorseVincolatePerLegge2;
	
	private Double risorseVincolatePerLegge3;
	
	private Double risorseMutuo1;
	
	private Double risorseMutuo2;
	
	private Double risorseMutuo3;
	
	private Double risorseImmobili1;
	
	private Double risorseImmobili2;
	
	private Double risorseImmobili3;
	
	private Double risorseBilancio1;
	
	private Double risorseBilancio2;
	
	private Double risorseBilancio3;
	
	private Double risorseAltro1;
	
	private Double risorseAltro2;
	
	private Double risorseAltro3;
	
	private Double risorsePrivati1;
	
	private Double risorsePrivati2;
	
	private Double risorsePrivati3;

	private String tipologiaCapitalePrivato;
	
	private DatiGeneraliTecnicoEntry rup;
	
	private List<ImmobileDM112011Entry> immobili = new ArrayList<ImmobileDM112011Entry>();

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

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setFinalita(String finalita) {
		this.finalita = finalita;
	}

	public String getFinalita() {
		return finalita;
	}

	public void setConformitaUrbanistica(String conformitaUrbanistica) {
		this.conformitaUrbanistica = conformitaUrbanistica;
	}

	public String getConformitaUrbanistica() {
		return conformitaUrbanistica;
	}

	public void setConformitaAmbientale(String conformitaAmbientale) {
		this.conformitaAmbientale = conformitaAmbientale;
	}

	public String getConformitaAmbientale() {
		return conformitaAmbientale;
	}

	public void setStatoProgettazione(String statoProgettazione) {
		this.statoProgettazione = statoProgettazione;
	}

	public String getStatoProgettazione() {
		return statoProgettazione;
	}

	public void setRisorseVincolatePerLegge1(Double risorseVincolatePerLegge1) {
		this.risorseVincolatePerLegge1 = risorseVincolatePerLegge1;
	}

	public Double getRisorseVincolatePerLegge1() {
		return risorseVincolatePerLegge1;
	}

	public void setRisorseVincolatePerLegge2(Double risorseVincolatePerLegge2) {
		this.risorseVincolatePerLegge2 = risorseVincolatePerLegge2;
	}

	public Double getRisorseVincolatePerLegge2() {
		return risorseVincolatePerLegge2;
	}

	public void setRisorseVincolatePerLegge3(Double risorseVincolatePerLegge3) {
		this.risorseVincolatePerLegge3 = risorseVincolatePerLegge3;
	}

	public Double getRisorseVincolatePerLegge3() {
		return risorseVincolatePerLegge3;
	}

	public void setRisorseMutuo1(Double risorseMutuo1) {
		this.risorseMutuo1 = risorseMutuo1;
	}

	public Double getRisorseMutuo1() {
		return risorseMutuo1;
	}

	public void setRisorseMutuo2(Double risorseMutuo2) {
		this.risorseMutuo2 = risorseMutuo2;
	}

	public Double getRisorseMutuo2() {
		return risorseMutuo2;
	}

	public void setRisorseMutuo3(Double risorseMutuo3) {
		this.risorseMutuo3 = risorseMutuo3;
	}

	public Double getRisorseMutuo3() {
		return risorseMutuo3;
	}

	public void setRisorsePrivati1(Double risorsePrivati1) {
		this.risorsePrivati1 = risorsePrivati1;
	}

	public Double getRisorsePrivati1() {
		return risorsePrivati1;
	}

	public void setRisorsePrivati2(Double risorsePrivati2) {
		this.risorsePrivati2 = risorsePrivati2;
	}

	public Double getRisorsePrivati2() {
		return risorsePrivati2;
	}

	public void setRisorsePrivati3(Double risorsePrivati3) {
		this.risorsePrivati3 = risorsePrivati3;
	}

	public Double getRisorsePrivati3() {
		return risorsePrivati3;
	}

	public void setRisorseBilancio1(Double risorseBilancio1) {
		this.risorseBilancio1 = risorseBilancio1;
	}

	public Double getRisorseBilancio1() {
		return risorseBilancio1;
	}

	public void setRisorseBilancio2(Double risorseBilancio2) {
		this.risorseBilancio2 = risorseBilancio2;
	}

	public Double getRisorseBilancio2() {
		return risorseBilancio2;
	}

	public void setRisorseBilancio3(Double risorseBilancio3) {
		this.risorseBilancio3 = risorseBilancio3;
	}

	public Double getRisorseBilancio3() {
		return risorseBilancio3;
	}

	public void setRisorseImmobili1(Double risorseImmobili1) {
		this.risorseImmobili1 = risorseImmobili1;
	}

	public Double getRisorseImmobili1() {
		return risorseImmobili1;
	}

	public void setRisorseImmobili2(Double risorseImmobili2) {
		this.risorseImmobili2 = risorseImmobili2;
	}

	public Double getRisorseImmobili2() {
		return risorseImmobili2;
	}

	public void setRisorseImmobili3(Double risorseImmobili3) {
		this.risorseImmobili3 = risorseImmobili3;
	}

	public Double getRisorseImmobili3() {
		return risorseImmobili3;
	}

	public void setRisorseAltro1(Double risorseAltro1) {
		this.risorseAltro1 = risorseAltro1;
	}

	public Double getRisorseAltro1() {
		return risorseAltro1;
	}

	public void setRisorseAltro2(Double risorseAltro2) {
		this.risorseAltro2 = risorseAltro2;
	}

	public Double getRisorseAltro2() {
		return risorseAltro2;
	}

	public void setRisorseAltro3(Double risorseAltro3) {
		this.risorseAltro3 = risorseAltro3;
	}

	public Double getRisorseAltro3() {
		return risorseAltro3;
	}

	public void setRup(DatiGeneraliTecnicoEntry rup) {
		this.rup = rup;
	}

	public DatiGeneraliTecnicoEntry getRup() {
		return rup;
	}

	public void setSubCategoria(String subCategoria) {
		this.subCategoria = subCategoria;
	}

	public String getSubCategoria() {
		return subCategoria;
	}

	public void setAnnoInizioLavori(Long annoInizioLavori) {
		this.annoInizioLavori = annoInizioLavori;
	}

	public Long getAnnoInizioLavori() {
		return annoInizioLavori;
	}

	public void setTrimestreInizioLavori(Long trimestreInizioLavori) {
		this.trimestreInizioLavori = trimestreInizioLavori;
	}

	public Long getTrimestreInizioLavori() {
		return trimestreInizioLavori;
	}

	public void setAnnoFineLavori(Long annoFineLavori) {
		this.annoFineLavori = annoFineLavori;
	}

	public Long getAnnoFineLavori() {
		return annoFineLavori;
	}

	public void setTrimestreFineLavori(Long trimestreFineLavori) {
		this.trimestreFineLavori = trimestreFineLavori;
	}

	public Long getTrimestreFineLavori() {
		return trimestreFineLavori;
	}

	public void setTipologiaCapitalePrivato(String tipologiaCapitalePrivato) {
		this.tipologiaCapitalePrivato = tipologiaCapitalePrivato;
	}

	public String getTipologiaCapitalePrivato() {
		return tipologiaCapitalePrivato;
	}

	public void setAnnualitaRiferimento(Long annualitaRiferimento) {
		this.annualitaRiferimento = annualitaRiferimento;
	}

	public Long getAnnualitaRiferimento() {
		return annualitaRiferimento;
	}

	public void setAnnuale(String annuale) {
		this.annuale = annuale;
	}

	public String getAnnuale() {
		return annuale;
	}

	public void setImmobili(List<ImmobileDM112011Entry> immobili) {
		this.immobili = immobili;
	}

	public List<ImmobileDM112011Entry> getImmobili() {
		return immobili;
	}

	public void setNumeroProgressivo(Long numeroProgressivo) {
		this.numeroProgressivo = numeroProgressivo;
	}

	public Long getNumeroProgressivo() {
		return numeroProgressivo;
	}
	
}
