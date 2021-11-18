package it.eldasoft.w9.bl;

import java.io.Serializable;

public class IndicatorePreliminareBean implements Serializable {

  /** UID */
	private static final long serialVersionUID = 3161641400770781075L;
	
	private String descrizione;
  private String unitaDiMisura;
  private String affidabilitaStima;
  private String contrattiSimiliStima;
  private String sogliaInferiore;
  private String percentile25;
  private String percentile50;
  private String percentile75;
  private String sogliaSuperiore;
  private String media;
  private String stessoComune;
  private String stessaProvincia;
  private String stessaRegione;
  private String descrizioneIncongruita;
  
  public IndicatorePreliminareBean() {
    this.descrizione = null;
    this.unitaDiMisura = null;
    this.affidabilitaStima = null;
    this.contrattiSimiliStima = null;
    this.sogliaInferiore = null;
    this.percentile25 = null;
    this.percentile50 = null;
    this.percentile75 = null;
    this.sogliaSuperiore = null;    
    this.media = null;
    this.stessoComune = null;
    this.stessaProvincia = null;
    this.stessaRegione = null;
    this.descrizioneIncongruita = null;
  }

  /**
   * @return the descrizione
   */
  public String getDescrizione() {
    return descrizione;
  }

  /**
   * @param descrizione the descrizione to set
   */
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   * @return the media
   */
  public String getMedia() {
    return media;
  }

  /**
   * @param media the media to set
   */
  public void setMedia(String media) {
    this.media = media;
  }

  /**
   * @return the sogliaInferiore
   */
  public String getSogliaInferiore() {
    return sogliaInferiore;
  }

  /**
   * @param sogliaInferiore the sogliaInferiore to set
   */
  public void setSogliaInferiore(String sogliaInferiore) {
    this.sogliaInferiore = sogliaInferiore;
  }

  /**
   * @return the sogliaSuperiore
   */
  public String getSogliaSuperiore() {
    return sogliaSuperiore;
  }

  /**
   * @param sogliaSuperiore the sogliaSuperiore to set
   */
  public void setSogliaSuperiore(String sogliaSuperiore) {
    this.sogliaSuperiore = sogliaSuperiore;
  }

  /**
   * @return the unitaDiMisura
   */
  public String getUnitaDiMisura() {
    return unitaDiMisura;
  }

  /**
   * @param unitaDiMisura the unitaDiMisura to set
   */
  public void setUnitaDiMisura(String unitaDiMisura) {
    this.unitaDiMisura = unitaDiMisura;
  }

  /**
   * @return the affidabilitaStima
   */
  public String getAffidabilitaStima() {
    return affidabilitaStima;
  }

  /**
   * @param affidabilitaStima the affidabilitaStima to set
   */
  public void setAffidabilitaStima(String affidabilitaStima) {
    this.affidabilitaStima = affidabilitaStima;
  }

  /**
   * @return the contrattiSimiliStima
   */
  public String getContrattiSimiliStima() {
    return contrattiSimiliStima;
  }

  /**
   * @param contrattiSimiliStima the contrattiSimiliStima to set
   */
  public void setContrattiSimiliStima(String contrattiSimiliStima) {
    this.contrattiSimiliStima = contrattiSimiliStima;
  }

  /**
   * @return the percentile25
   */
  public String getPercentile25() {
    return percentile25;
  }

  /**
   * @param percentile25 the percentile25 to set
   */
  public void setPercentile25(String percentile25) {
    this.percentile25 = percentile25;
  }

  /**
   * @return the percentile50
   */
  public String getPercentile50() {
    return percentile50;
  }

  /**
   * @param percentile50 the percentile50 to set
   */
  public void setPercentile50(String percentile50) {
    this.percentile50 = percentile50;
  }

  /**
   * @return the percentile75
   */
  public String getPercentile75() {
    return percentile75;
  }

  /**
   * @param percentile75 the percentile75 to set
   */
  public void setPercentile75(String percentile75) {
    this.percentile75 = percentile75;
  }

  /**
   * @return the stessoComune
   */
  public String getStessoComune() {
    return stessoComune;
  }

  /**
   * @param stessoComune the stessoComune to set
   */
  public void setStessoComune(String stessoComune) {
    this.stessoComune = stessoComune;
  }

  /**
   * @return the stessaProvincia
   */
  public String getStessaProvincia() {
    return stessaProvincia;
  }

  /**
   * @param stessaProvincia the stessaProvincia to set
   */
  public void setStessaProvincia(String stessaProvincia) {
    this.stessaProvincia = stessaProvincia;
  }

  /**
   * @return the stessaRegione
   */
  public String getStessaRegione() {
    return stessaRegione;
  }

  /**
   * @param stessaRegione the stessaRegione to set
   */
  public void setStessaRegione(String stessaRegione) {
    this.stessaRegione = stessaRegione;
  }

  /**
   * @return the descrizioneIncongruita
   */
  public String getDescrizioneIncongruita() {
    return descrizioneIncongruita;
  }

  /**
   * @param descrizioneIncongruita the descrizioneIncongruita to set
   */
  public void setDescrizioneIncongruita(String descrizioneIncongruita) {
    this.descrizioneIncongruita = descrizioneIncongruita;
  }

}
