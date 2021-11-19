package it.eldasoft.w9.common.bean;

import java.io.Serializable;

/**
 * Bean con gli attributi necessari alla creazione della fase Pubblicazione Documenti.
 * 
 * @author Luca.Giacomazzo
 */
public class PubblicazioneBean implements Serializable {

  /** UID	 */
	private static final long serialVersionUID = -4267039277683616710L;
	private long codiceGara;
  private long numeroPubblicazione;
  
  private String messaggioDiErrore;

  private DatiComuniBean datiComuni;
  
  /**
   * @return the codiceGara
   */
  public long getCodiceGara() {
    return codiceGara;
  }

  /**
   * @param codiceGara the codiceGara to set
   */
  public void setCodiceGara(long codiceGara) {
    this.codiceGara = codiceGara;
  }

  /**
   * @return the datiComuni
   */
  public DatiComuniBean getDatiComuniXml() {
    return datiComuni;
  }

  /**
   * @param datiComuni the datiComuniXml to set
   */
  public void setDatiComuni(DatiComuniBean datiComuniXml) {
    this.datiComuni = datiComuniXml;
  }

  /**
   * @return the messaggioDiErrore
   */
  public String getMessaggioDiErrore() {
    return messaggioDiErrore;
  }

  /**
   * @param messaggioDiErrore the messaggioDiErrore to set
   */
  public void setMessaggioDiErrore(String messaggioDiErrore) {
    this.messaggioDiErrore = messaggioDiErrore;
  }

  /**
   * @return the numeroPubblicazione
   */
  public long getNumeroPubblicazione() {
    return numeroPubblicazione;
  }

  /**
   * @param numeroPubblicazione the numeroPubblicazione to set
   */
  public void setNumeroPubblicazione(long numeroPubblicazione) {
    this.numeroPubblicazione = numeroPubblicazione;
  }

  /**
   * @return the datiComuni
   */
  public DatiComuniBean getDatiComuni() {
    return datiComuni;
  }
  
  
}
