package it.eldasoft.w9.bl;

import java.io.Serializable;

/**
 * Bean per il report degli indicatori del lotto.
 * 
 * @author luca.giacomazzo
 */
public class IndicatoreBean implements Serializable {

  /**  UID  */
	private static final long serialVersionUID = 5114282562918370827L;
	
	private String descrizione;
  private String congruo;
  private Boolean calcolabile;
  private String sogliaInferiore;
  private String sogliaSuperiore;
  private String unitaDiMisura;
  private String valore;
  private String tipo;
  private String tipoSoglia;
  private String descrizioneIncongruita;
  private String livello;
  private String intervallo;
  private String asterisco;
  private String affidabilitaStima;
  private String contrattiSimiliStima;
  
  public IndicatoreBean() {
    this.descrizione = null;
    this.congruo = null;
    this.calcolabile = null;
    this.sogliaInferiore = null;
    this.sogliaSuperiore = null;
    this.unitaDiMisura = null;
    this.valore = null;
    this.tipo = null;
    this.tipoSoglia = null;
    this.descrizioneIncongruita = null;
    this.livello = null;
    this.intervallo = null;
    this.asterisco = null;
    this.affidabilitaStima = null;
    this.contrattiSimiliStima = null;
  }

  /**
   * @return the calcolabile
   */
  public Boolean getCalcolabile() {
    return calcolabile;
  }

  /**
   * @param calcolabile the calcolabile to set
   */
  public void setCalcolabile(Boolean calcolabile) {
    this.calcolabile = calcolabile;
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
   * @return the congruo
   */
  public String getCongruo() {
    return congruo;
  }

  /**
   * @param congruo the congruo to set
   */
  public void setCongruo(String congruo) {
    this.congruo = congruo;
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
   * @return the valore
   */
  public String getValore() {
    return this.valore;
  }

  /**
   * @param strSql the valore to set
   */
  public void setValore(String valore) {
    this.valore = valore;
  }

  /**
   * @return the tipo
   */
  public String getTipo() {
    return tipo;
  }

  /**
   * @param tipo the tipo to set
   */
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  /**
   * @return the tipoSoglia
   */
  public String getTipoSoglia() {
    return tipoSoglia;
  }

  /**
   * @param tipoSoglia the tipoSoglia to set
   */
  public void setTipoSoglia(String tipoSoglia) {
    this.tipoSoglia = tipoSoglia;
  }

  /**
   * @return the descrizioneIncongruita
   */
  public String getDescrizioneIncongruita() {
    return descrizioneIncongruita;
  }

  /**
   * @param fase the descrizioneIncongruita to set
   */
  public void setDescrizioneIncongruita(String descrizioneIncongruita) {
    this.descrizioneIncongruita = descrizioneIncongruita;
  }

  /**
   * @return the livello
   */
  public String getLivello() {
    return livello;
  }

  /**
   * @param livello the livello to set
   */
  public void setLivello(String livello) {
    this.livello = livello;
  }

  /**
   * @return the intervallo
   */
  public String getIntervallo() {
    return intervallo;
  }

  /**
   * @param intervallo the intervallo to set
   */
  public void setIntervallo(String intervallo) {
    this.intervallo = intervallo;
  }

  /**
   * @return the asterisco
   */
  public String getAsterisco() {
    return asterisco;
  }

  /**
   * @param asterisco the asterisco to set
   */
  public void setAsterisco(String asterisco) {
    this.asterisco = asterisco;
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

}
