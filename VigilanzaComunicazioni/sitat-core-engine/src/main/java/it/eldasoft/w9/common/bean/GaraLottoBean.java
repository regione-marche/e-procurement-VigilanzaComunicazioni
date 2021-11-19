package it.eldasoft.w9.common.bean;

import java.io.Serializable;

/**
 * Bean con gli attributi necessari alla creazione delle diverse fasi di gara, 
 * compresa l'anagrafica gara/lotto.
 * 
 * @author Luca.Giacomazzo
 */
public class GaraLottoBean implements Serializable {

  /** UID */
	private static final long serialVersionUID = -8397270702198978322L;
	private long codiceGara;
  private long codiceLotto;
  private boolean exportIncarichiProfessionali;
  
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
   * @return the codiceLotto
   */
  public long getCodiceLotto() {
    return codiceLotto;
  }

  /**
   * @param codiceLotto the codiceLotto to set
   */
  public void setCodiceLotto(long codiceLotto) {
    this.codiceLotto = codiceLotto;
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
   * @return the exportIncarichiProfessionali
   */
  public boolean isExportIncarichiProfessionali() {
    return exportIncarichiProfessionali;
  }

  /**
   * @param exportIncarichiProfessionali the exportIncarichiProfessionali to set
   */
  public void
      setExportIncarichiProfessionali(boolean exportIncarichiProfessionali) {
    this.exportIncarichiProfessionali = exportIncarichiProfessionali;
  }
  
}
