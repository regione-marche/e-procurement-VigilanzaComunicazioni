package it.eldasoft.w9.common.bean;

import java.io.Serializable;

/**
 * Bean con gli attributi comuni necessari alla creazione della Fase / FaseEstesa
 * (parte comune a tutti i flussi).
 * 
 * @author Luca.Giacomazzo
 */
public class DatiComuniBean implements Serializable {

  /** UID */
	private static final long serialVersionUID = 6125802062759893983L;
	private long codiceFase;
  private long progressivoFase;
  private long codiceCompositore;
  private String nomeCompositore;
  private String mailCompositore;
  private Long tipoInvio;
  private String noteInvio;
  private String cfStazioneAppaltante;
  private Boolean esito;
  private Long daExport;

  /**
   * @return the daExport
   */
  public Long getDaExport() {
  	return daExport;
  }

  /**
   * @param daExport the daExport to set
   */
  public void setDaExport(Long daExport) {
	this.daExport = daExport;
  }

  /**
   * @return the codiceFase
   */
  public long getCodiceFase() {
    return this.codiceFase;
  }

  /**
   * @param codiceFase the codiceFase to set
   */
  public void setCodiceFase(long codiceFase) {
    this.codiceFase = codiceFase;
  }
  
  /**
   * @return the progressivoFase
   */
  public long getProgressivoFase() {
    return progressivoFase;
  }

  /**
   * @param progressivoFase the progressivoFase to set
   */
  public void setProgressivoFase(long progressivoFase) {
    this.progressivoFase = progressivoFase;
  }

  /**
   * @return the codiceCompositore
   */
  public long getCodiceCompositore() {
    return codiceCompositore;
  }

  /**
   * @param codiceCompositore the codiceCompositore to set
   */
  public void setCodiceCompositore(long codiceCompositore) {
    this.codiceCompositore = codiceCompositore;
  }

  /**
   * @return the nomeCompositore
   */
  public String getNomeCompositore() {
    return nomeCompositore;
  }

  /**
   * @param nomeCompositore the nomeCompositore to set
   */
  public void setNomeCompositore(String nomeCompositore) {
    this.nomeCompositore = nomeCompositore;
  }

  /**
   * @return the mailCompositore
   */
  public String getMailCompositore() {
    return mailCompositore;
  }

  /**
   * @param mailCompositore the mailCompositore to set
   */
  public void setMailCompositore(String mailCompositore) {
    this.mailCompositore = mailCompositore;
  }

  /**
   * @return the tipoInvio
   */
  public Long getTipoInvio() {
    return tipoInvio;
  }

  /**
   * @param tipoInvio the tipoInvio to set
   */
  public void setTipoInvio(Long tipoInvio) {
    this.tipoInvio = tipoInvio;
  }

  /**
   * @return the noteInvio
   */
  public String getNoteInvio() {
    return noteInvio;
  }

  /**
   * @param noteInvio the noteInvio to set
   */
  public void setNoteInvio(String noteInvio) {
    this.noteInvio = noteInvio;
  }

  /**
   * @return the cfStazioneAppaltante
   */
  public String getCfStazioneAppaltante() {
    return cfStazioneAppaltante;
  }

  /**
   * @param cfStazioneAppaltante the cfStazioneAppaltante to set
   */
  public void setCfStazioneAppaltante(String cfStazioneAppaltante) {
    this.cfStazioneAppaltante = cfStazioneAppaltante;
  }

  /**
   * @return the esito
   */
  public Boolean getEsito() {
    return esito;
  }

  /**
   * @param esito the esito to set
   */
  public void setEsito(Boolean esito) {
    this.esito = esito;
  }
 
}
