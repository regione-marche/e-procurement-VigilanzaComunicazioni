package it.eldasoft.commons.beans;

/**
 * 
 * @author luca.giacomazzo
 */
public class ValoreCampiChiaveBean {

  private Long valoreCampoChiave1;
  private Long valoreCampoChiave2;
  private Long valoreCampoChiave3;
  private Long valoreCampoChiave4;
  private String valoreCampoChiave5;
  
  public ValoreCampiChiaveBean() {
    this.valoreCampoChiave1 = null;
    this.valoreCampoChiave2 = null;
    this.valoreCampoChiave3 = null;
    this.valoreCampoChiave4 = null;
    this.valoreCampoChiave5 = null;
  }
  
  public ValoreCampiChiaveBean(Long valorecampoChiave1) {
    this.valoreCampoChiave1 = valorecampoChiave1;
    this.valoreCampoChiave2 = null;
    this.valoreCampoChiave3 = null;
    this.valoreCampoChiave4 = null;
    this.valoreCampoChiave5 = null;
  }
  
  public ValoreCampiChiaveBean(Long valorecampoChiave1, Long valorecampoChiave2) {
    this.valoreCampoChiave1 = valorecampoChiave1;
    this.valoreCampoChiave2 = valorecampoChiave2;
    this.valoreCampoChiave3 = null;
    this.valoreCampoChiave4 = null;
    this.valoreCampoChiave5 = null;
  }
  
  public ValoreCampiChiaveBean(Long valorecampoChiave1, Long valorecampoChiave2, Long valorecampoChiave3) {
    this.valoreCampoChiave1 = valorecampoChiave1;
    this.valoreCampoChiave2 = valorecampoChiave2;
    this.valoreCampoChiave3 = valorecampoChiave3;
    this.valoreCampoChiave4 = null;
    this.valoreCampoChiave5 = null;
  }
  
  public ValoreCampiChiaveBean(Long valorecampoChiave1, Long valorecampoChiave2, Long valorecampoChiave3, Long valorecampoChiave4) {
    this.valoreCampoChiave1 = valorecampoChiave1;
    this.valoreCampoChiave2 = valorecampoChiave2;
    this.valoreCampoChiave3 = valorecampoChiave3;
    this.valoreCampoChiave4 = valorecampoChiave4;
    this.valoreCampoChiave5 = null;
  }

  public ValoreCampiChiaveBean(Long valorecampoChiave1, Long valorecampoChiave2, Long valorecampoChiave3, Long valorecampoChiave4, String valorecampoChiave5) {
    this.valoreCampoChiave1 = valorecampoChiave1;
    this.valoreCampoChiave2 = valorecampoChiave2;
    this.valoreCampoChiave3 = valorecampoChiave3;
    this.valoreCampoChiave4 = valorecampoChiave4;
    this.valoreCampoChiave5 = valorecampoChiave5;
  }
  
  /**
   * @return the valoreCampoChiave1
   */
  public Long getValoreCampoChiave1() {
    return valoreCampoChiave1;
  }

  /**
   * @param valoreCampoChiave1 the valoreCampoChiave1 to set
   */
  public void setValoreCampoChiave1(Long valoreCampoChiave1) {
    this.valoreCampoChiave1 = valoreCampoChiave1;
  }

  /**
   * @return the valoreCampoChiave2
   */
  public Long getValoreCampoChiave2() {
    return valoreCampoChiave2;
  }

  /**
   * @param valoreCampoChiave2 the valoreCampoChiave2 to set
   */
  public void setValoreCampoChiave2(Long valoreCampoChiave2) {
    this.valoreCampoChiave2 = valoreCampoChiave2;
  }

  /**
   * @return the valoreCampoChiave3
   */
  public Long getValoreCampoChiave3() {
    return valoreCampoChiave3;
  }

  /**
   * @param valoreCampoChiave3 the valoreCampoChiave3 to set
   */
  public void setValoreCampoChiave3(Long valoreCampoChiave3) {
    this.valoreCampoChiave3 = valoreCampoChiave3;
  }

  /**
   * @return the valoreCampoChiave4
   */
  public Long getValoreCampoChiave4() {
    return valoreCampoChiave4;
  }

  /**
   * @param valoreCampoChiave4 the valoreCampoChiave4 to set
   */
  public void setValoreCampoChiave4(Long valoreCampoChiave4) {
    this.valoreCampoChiave4 = valoreCampoChiave4;
  }

  /**
   * @return the valoreCampoChiave5
   */
  public String getValoreCampoChiave5() {
    return valoreCampoChiave5;
  }

  /**
   * @param valoreCampoChiave5 the valoreCampoChiave5 to set
   */
  public void setValoreCampoChiave5(String valoreCampoChiave5) {
    this.valoreCampoChiave5 = valoreCampoChiave5;
  }

}

