/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.eldasoft.w9.dao.vo.rest.programmi.lavori;

import it.eldasoft.w9.dao.vo.rest.DatiGeneraliTecnicoEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Dati di un intervento lavori.
 * 
 * @author Mirco.Franzoni
 */
public class InterventoEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;
	
	private Long numeroProgressivo;
	
	private String cui;
	
	private String codiceInterno;
	
	private String descrizione;
	
	private Long anno;
	
	private String esenteCup;
	
	private String cup;
	
	private String cpv;
	
	private String istat;

	private String nuts;
	
	private Long priorita;
	
	private String lottoFunzionale;
	
	private String lavoroComplesso;
	
	private String tipologia;
	
	private String categoria;
	
	private String scadenzaFinanziamento; 
	
	private String finalita;
	
	private String conformitaUrbanistica;
	
	private String conformitaAmbientale;
	
	private String statoProgettazione;
	
	private Double risorseVincolatePerLegge1;
	
	private Double risorseVincolatePerLegge2;
	
	private Double risorseVincolatePerLegge3;
	
	private Double risorseVincolatePerLeggeSucc;
	
	private Double risorseMutuo1;
	
	private Double risorseMutuo2;
	
	private Double risorseMutuo3;
	
	private Double risorseMutuoSucc;
	
	private Double risorsePrivati1;
	
	private Double risorsePrivati2;
	
	private Double risorsePrivati3;
	
	private Double risorsePrivatiSucc;
	
	private Double risorseBilancio1;
	
	private Double risorseBilancio2;
	
	private Double risorseBilancio3;
	
	private Double risorseBilancioSucc;
	
	private Double risorseArt3_1;
	
	private Double risorseArt3_2;
	
	private Double risorseArt3_3;
	
	private Double risorseArt3_Succ;
	
	private Double risorseImmobili1;
	
	private Double risorseImmobili2;
	
	private Double risorseImmobili3;
	
	private Double risorseImmobiliSucc;
	
	private Double risorseAltro1;
	
	private Double risorseAltro2;
	
	private Double risorseAltro3;
	
	private Double risorseAltroSucc;

	private Double speseSostenute;
	
	private String tipologiaCapitalePrivato;
	
	private Long meseAvvioProcedura;
	
	private String delega;
	
	private String codiceSoggettoDelegato;
	
	private String nomeSoggettoDelegato;
	
	private Long variato;
	
	private String note;

	private String direzioneGenerale;
	
	private String strutturaOperativa;
	
	private String referenteDati;
	
	private String dirigenteResponsabile;
	
	private Long proceduraAffidamento;
	
	private Long acquistoVerdi;
	
	private String normativaRiferimento;
	
	private String oggettoVerdi;
	
	private String cpvVerdi;
	
	private Double importoNettoIvaVerdi;
	
	private Double importoIvaVerdi;
	
	private Double importoTotVerdi;
	
	private Long acquistoMaterialiRiciclati;
	
	private String oggettoMatRic;
	
	private String cpvMatRic;
	
	private Double importoNettoIvaMatRic;
	
	private Double importoIvaMatRic;
	
	private Double importoTotMatRic;
	
	private String coperturaFinanziaria;
	
	private Long valutazione;
	
	private DatiGeneraliTecnicoEntry rup;
	
	private List<ImmobileEntry> immobili = new ArrayList<ImmobileEntry>();

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getCui() {
		return cui;
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

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	public Long getAnno() {
		return anno;
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

	public void setLottoFunzionale(String lottoFunzionale) {
		this.lottoFunzionale = lottoFunzionale;
	}

	public String getLottoFunzionale() {
		return lottoFunzionale;
	}

	public void setLavoroComplesso(String lavoroComplesso) {
		this.lavoroComplesso = lavoroComplesso;
	}

	public String getLavoroComplesso() {
		return lavoroComplesso;
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

	public void setScadenzaFinanziamento(String scadenzaFinanziamento) {
		this.scadenzaFinanziamento = scadenzaFinanziamento;
	}

	public String getScadenzaFinanziamento() {
		return scadenzaFinanziamento;
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

	public void setRisorseVincolatePerLeggeSucc(
			Double risorseVincolatePerLeggeSucc) {
		this.risorseVincolatePerLeggeSucc = risorseVincolatePerLeggeSucc;
	}

	public Double getRisorseVincolatePerLeggeSucc() {
		return risorseVincolatePerLeggeSucc;
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

	public void setRisorseMutuoSucc(Double risorseMutuoSucc) {
		this.risorseMutuoSucc = risorseMutuoSucc;
	}

	public Double getRisorseMutuoSucc() {
		return risorseMutuoSucc;
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

	public void setRisorsePrivatiSucc(Double risorsePrivatiSucc) {
		this.risorsePrivatiSucc = risorsePrivatiSucc;
	}

	public Double getRisorsePrivatiSucc() {
		return risorsePrivatiSucc;
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

	public void setRisorseBilancioSucc(Double risorseBilancioSucc) {
		this.risorseBilancioSucc = risorseBilancioSucc;
	}

	public Double getRisorseBilancioSucc() {
		return risorseBilancioSucc;
	}

	public void setRisorseArt3_1(Double risorseArt3_1) {
		this.risorseArt3_1 = risorseArt3_1;
	}

	public Double getRisorseArt3_1() {
		return risorseArt3_1;
	}

	public void setRisorseArt3_2(Double risorseArt3_2) {
		this.risorseArt3_2 = risorseArt3_2;
	}

	public Double getRisorseArt3_2() {
		return risorseArt3_2;
	}

	public void setRisorseArt3_3(Double risorseArt3_3) {
		this.risorseArt3_3 = risorseArt3_3;
	}

	public Double getRisorseArt3_3() {
		return risorseArt3_3;
	}

	public void setRisorseArt3_Succ(Double risorseArt3_Succ) {
		this.risorseArt3_Succ = risorseArt3_Succ;
	}

	public Double getRisorseArt3_Succ() {
		return risorseArt3_Succ;
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

	public void setRisorseImmobiliSucc(Double risorseImmobiliSucc) {
		this.risorseImmobiliSucc = risorseImmobiliSucc;
	}

	public Double getRisorseImmobiliSucc() {
		return risorseImmobiliSucc;
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

	public void setRisorseAltroSucc(Double risorseAltroSucc) {
		this.risorseAltroSucc = risorseAltroSucc;
	}

	public Double getRisorseAltroSucc() {
		return risorseAltroSucc;
	}

	public void setSpeseSostenute(Double speseSostenute) {
		this.speseSostenute = speseSostenute;
	}

	public Double getSpeseSostenute() {
		return speseSostenute;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setRup(DatiGeneraliTecnicoEntry rup) {
		this.rup = rup;
	}

	public DatiGeneraliTecnicoEntry getRup() {
		return rup;
	}

	public void setImmobili(List<ImmobileEntry> immobili) {
		this.immobili = immobili;
	}

	public List<ImmobileEntry> getImmobili() {
		return immobili;
	}

	public void setTipologiaCapitalePrivato(String tipologiaCapitalePrivato) {
		this.tipologiaCapitalePrivato = tipologiaCapitalePrivato;
	}

	public String getTipologiaCapitalePrivato() {
		return tipologiaCapitalePrivato;
	}

	public void setCodiceSoggettoDelegato(String codiceSoggettoDelegato) {
		this.codiceSoggettoDelegato = codiceSoggettoDelegato;
	}

	public String getCodiceSoggettoDelegato() {
		return codiceSoggettoDelegato;
	}

	public void setNomeSoggettoDelegato(String nomeSoggettoDelegato) {
		this.nomeSoggettoDelegato = nomeSoggettoDelegato;
	}

	public String getNomeSoggettoDelegato() {
		return nomeSoggettoDelegato;
	}

	public void setVariato(Long variato) {
		this.variato = variato;
	}

	public Long getVariato() {
		return variato;
	}

	public void setNumeroProgressivo(Long numeroProgressivo) {
		this.numeroProgressivo = numeroProgressivo;
	}

	public Long getNumeroProgressivo() {
		return numeroProgressivo;
	}

	public void setDelega(String delega) {
		this.delega = delega;
	}

	public String getDelega() {
		return delega;
	}

	public void setMeseAvvioProcedura(Long meseAvvioProcedura) {
		this.meseAvvioProcedura = meseAvvioProcedura;
	}

	public Long getMeseAvvioProcedura() {
		return meseAvvioProcedura;
	}

	public void setDirezioneGenerale(String direzioneGenerale) {
		this.direzioneGenerale = direzioneGenerale;
	}

	public String getDirezioneGenerale() {
		return direzioneGenerale;
	}

	public void setStrutturaOperativa(String strutturaOperativa) {
		this.strutturaOperativa = strutturaOperativa;
	}

	public String getStrutturaOperativa() {
		return strutturaOperativa;
	}

	public void setReferenteDati(String referenteDati) {
		this.referenteDati = referenteDati;
	}

	public String getReferenteDati() {
		return referenteDati;
	}

	public void setDirigenteResponsabile(String dirigenteResponsabile) {
		this.dirigenteResponsabile = dirigenteResponsabile;
	}

	public String getDirigenteResponsabile() {
		return dirigenteResponsabile;
	}

	public void setProceduraAffidamento(Long proceduraAffidamento) {
		this.proceduraAffidamento = proceduraAffidamento;
	}

	public Long getProceduraAffidamento() {
		return proceduraAffidamento;
	}

	public void setAcquistoVerdi(Long acquistoVerdi) {
		this.acquistoVerdi = acquistoVerdi;
	}

	public Long getAcquistoVerdi() {
		return acquistoVerdi;
	}

	public void setNormativaRiferimento(String normativaRiferimento) {
		this.normativaRiferimento = normativaRiferimento;
	}

	public String getNormativaRiferimento() {
		return normativaRiferimento;
	}

	public void setOggettoVerdi(String oggettoVerdi) {
		this.oggettoVerdi = oggettoVerdi;
	}

	public String getOggettoVerdi() {
		return oggettoVerdi;
	}

	public void setCpvVerdi(String cpvVerdi) {
		this.cpvVerdi = cpvVerdi;
	}

	public String getCpvVerdi() {
		return cpvVerdi;
	}

	public void setImportoNettoIvaVerdi(Double importoNettoIvaVerdi) {
		this.importoNettoIvaVerdi = importoNettoIvaVerdi;
	}

	public Double getImportoNettoIvaVerdi() {
		return importoNettoIvaVerdi;
	}

	public void setImportoIvaVerdi(Double importoIvaVerdi) {
		this.importoIvaVerdi = importoIvaVerdi;
	}

	public Double getImportoIvaVerdi() {
		return importoIvaVerdi;
	}

	public void setImportoTotVerdi(Double importoTotVerdi) {
		this.importoTotVerdi = importoTotVerdi;
	}

	public Double getImportoTotVerdi() {
		return importoTotVerdi;
	}

	public void setAcquistoMaterialiRiciclati(Long acquistoMaterialiRiciclati) {
		this.acquistoMaterialiRiciclati = acquistoMaterialiRiciclati;
	}

	public Long getAcquistoMaterialiRiciclati() {
		return acquistoMaterialiRiciclati;
	}

	public void setOggettoMatRic(String oggettoMatRic) {
		this.oggettoMatRic = oggettoMatRic;
	}

	public String getOggettoMatRic() {
		return oggettoMatRic;
	}

	public void setCpvMatRic(String cpvMatRic) {
		this.cpvMatRic = cpvMatRic;
	}

	public String getCpvMatRic() {
		return cpvMatRic;
	}

	public void setImportoNettoIvaMatRic(Double importoNettoIvaMatRic) {
		this.importoNettoIvaMatRic = importoNettoIvaMatRic;
	}

	public Double getImportoNettoIvaMatRic() {
		return importoNettoIvaMatRic;
	}

	public void setImportoIvaMatRic(Double importoIvaMatRic) {
		this.importoIvaMatRic = importoIvaMatRic;
	}

	public Double getImportoIvaMatRic() {
		return importoIvaMatRic;
	}

	public void setImportoTotMatRic(Double importoTotMatRic) {
		this.importoTotMatRic = importoTotMatRic;
	}

	public Double getImportoTotMatRic() {
		return importoTotMatRic;
	}

	public void setCoperturaFinanziaria(String coperturaFinanziaria) {
		this.coperturaFinanziaria = coperturaFinanziaria;
	}

	public String getCoperturaFinanziaria() {
		return coperturaFinanziaria;
	}

	public void setValutazione(Long valutazione) {
		this.valutazione = valutazione;
	}

	public Long getValutazione() {
		return valutazione;
	}

}
