package it.eldasoft.commons.beans;

/**
 * 
 * @author luca.giacomazzo
 */
public class EntitaCampiChiaveBean {

  private String codiceApplicazione;
  private String entita;
  private ValoreCampiChiaveBean valoreCampiChiaveBean;
  
  public EntitaCampiChiaveBean() {
    this.codiceApplicazione = null;
    this.entita = null;
    this.valoreCampiChiaveBean = null;
  }
  
  public EntitaCampiChiaveBean(String codiceApplicazione, String entita) {
    this.codiceApplicazione = codiceApplicazione;
    this.entita = entita;
    this.valoreCampiChiaveBean = new ValoreCampiChiaveBean();
  }
  
  public EntitaCampiChiaveBean(String codiceApplicazione, String entita, Long valorecampoChiave1) {
    this.codiceApplicazione = codiceApplicazione;
    this.entita = entita;
    this.valoreCampiChiaveBean = new ValoreCampiChiaveBean(valorecampoChiave1);
  }
  
  public EntitaCampiChiaveBean(String codiceApplicazione, String entita, Long valorecampoChiave1, 
      Long valorecampoChiave2) {
    this.codiceApplicazione = codiceApplicazione;
    this.entita = entita;
    this.valoreCampiChiaveBean = new ValoreCampiChiaveBean(valorecampoChiave1, valorecampoChiave2);
  }
  
  public EntitaCampiChiaveBean(String codiceApplicazione, String entita, Long valorecampoChiave1,
      Long valorecampoChiave2, Long valorecampoChiave3) {
    this.codiceApplicazione = codiceApplicazione;
    this.entita = entita;
    this.valoreCampiChiaveBean = new ValoreCampiChiaveBean(valorecampoChiave1, valorecampoChiave2, 
        valorecampoChiave3);
  }
  
  public EntitaCampiChiaveBean(String codiceApplicazione, String entita, Long valorecampoChiave1, 
      Long valorecampoChiave2, Long valorecampoChiave3, Long valorecampoChiave4) {
    this.codiceApplicazione = codiceApplicazione;
    this.entita = entita;
    this.valoreCampiChiaveBean = new ValoreCampiChiaveBean(valorecampoChiave1, valorecampoChiave2, 
        valorecampoChiave3, valorecampoChiave4);
  }

  public EntitaCampiChiaveBean(String codiceApplicazione, String entita, Long valorecampoChiave1, 
      Long valorecampoChiave2, Long valorecampoChiave3, Long valorecampoChiave4, String valorecampoChiave5) {
    this.codiceApplicazione = codiceApplicazione;
    this.entita = entita;
    this.valoreCampiChiaveBean = new ValoreCampiChiaveBean(valorecampoChiave1, valorecampoChiave2, 
        valorecampoChiave3, valorecampoChiave4, valorecampoChiave5);
  }
  
  /**
   * @return the codiceApplicazione
   */
  public String getCodiceApplicazione() {
    return codiceApplicazione;
  }

  /**
   * @param codiceApplicazione the codiceApplicazione to set
   */
  public void setCodiceApplicazione(String codiceApplicazione) {
    this.codiceApplicazione = codiceApplicazione;
  }

  /**
   * @return the entita
   */
  public String getEntita() {
    return entita;
  }

  /**
   * @param entita the entita to set
   */
  public void setEntita(String entita) {
    this.entita = entita;
  }

  /**
   * @return the valoreCampiChiaveBean
   */
  public ValoreCampiChiaveBean getValoreCampiChiaveBean() {
    return valoreCampiChiaveBean;
  }

  /**
   * @param valoreCampiChiaveBean the valoreCampiChiaveBean to set
   */
  public void
      setValoreCampiChiaveBean(ValoreCampiChiaveBean valoreCampiChiaveBean) {
    this.valoreCampiChiaveBean = valoreCampiChiaveBean;
  }
  
}
