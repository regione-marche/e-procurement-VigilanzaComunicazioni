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
import java.util.ArrayList;
import java.util.List;

/**
 * Dati di un'opera incompiuta.
 * 
 * @author Mirco.Franzoni
 */
public class OperaIncompiutaEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private String cup;
	
	private String descrizione;
	
	private String determinazioneAmministrazione;
	  
	private String ambito;
	  
	private Long anno;

	private Double importoIntervento;
	
	private Double importoLavori;
		
	private Double oneri;
		
	private Double importoAvanzamento;
		
	private Double percentualeAvanzamento;
		
	private String causa;
		  
	private String stato;
 
	private String infrastruttura;
	
	private String discontinuitaRete;
		  
	private String fruibile;
		  
	private String ridimensionato;
		  
	private String destinazioneUso;

	private String cessione;
		  
	private String previstaVendita;
		  
	private String demolizione;
		  
	private Double oneriSito;

	private AltriDatiOperaIncompiutaEntry altriDati;

	private List<ImmobileEntry> immobili = new ArrayList<ImmobileEntry>();

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

	public void setDeterminazioneAmministrazione(
			String determinazioneAmministrazione) {
		this.determinazioneAmministrazione = determinazioneAmministrazione;
	}

	public String getDeterminazioneAmministrazione() {
		return determinazioneAmministrazione;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	public Long getAnno() {
		return anno;
	}

	public void setImportoIntervento(Double importoIntervento) {
		this.importoIntervento = importoIntervento;
	}

	public Double getImportoIntervento() {
		return importoIntervento;
	}

	public void setImportoLavori(Double importoLavori) {
		this.importoLavori = importoLavori;
	}

	public Double getImportoLavori() {
		return importoLavori;
	}

	public void setOneri(Double oneri) {
		this.oneri = oneri;
	}

	public Double getOneri() {
		return oneri;
	}

	public void setImportoAvanzamento(Double importoAvanzamento) {
		this.importoAvanzamento = importoAvanzamento;
	}

	public Double getImportoAvanzamento() {
		return importoAvanzamento;
	}

	public void setPercentualeAvanzamento(Double percentualeAvanzamento) {
		this.percentualeAvanzamento = percentualeAvanzamento;
	}

	public Double getPercentualeAvanzamento() {
		return percentualeAvanzamento;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public String getCausa() {
		return causa;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getStato() {
		return stato;
	}

	public void setInfrastruttura(String infrastruttura) {
		this.infrastruttura = infrastruttura;
	}

	public String getInfrastruttura() {
		return infrastruttura;
	}
	
	public void setDiscontinuitaRete(String discontinuitaRete) {
    this.discontinuitaRete = discontinuitaRete;
  }

  public String getDiscontinuitaRete() {
    return discontinuitaRete;
  }

	public void setFruibile(String fruibile) {
		this.fruibile = fruibile;
	}

	public String getFruibile() {
		return fruibile;
	}

	public void setRidimensionato(String ridimensionato) {
		this.ridimensionato = ridimensionato;
	}

	public String getRidimensionato() {
		return ridimensionato;
	}

	public void setDestinazioneUso(String destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public String getDestinazioneUso() {
		return destinazioneUso;
	}

	public void setCessione(String cessione) {
		this.cessione = cessione;
	}

	public String getCessione() {
		return cessione;
	}

	public void setPrevistaVendita(String previstaVendita) {
		this.previstaVendita = previstaVendita;
	}

	public String getPrevistaVendita() {
		return previstaVendita;
	}

	public void setDemolizione(String demolizione) {
		this.demolizione = demolizione;
	}

	public String getDemolizione() {
		return demolizione;
	}

	public void setOneriSito(Double oneriSito) {
		this.oneriSito = oneriSito;
	}

	public Double getOneriSito() {
		return oneriSito;
	}

	public void setAltriDati(AltriDatiOperaIncompiutaEntry altriDati) {
		this.altriDati = altriDati;
	}

	public AltriDatiOperaIncompiutaEntry getAltriDati() {
		return altriDati;
	}

	public void setImmobili(List<ImmobileEntry> immobili) {
		this.immobili = immobili;
	}

	public List<ImmobileEntry> getImmobili() {
		return immobili;
	}

}
