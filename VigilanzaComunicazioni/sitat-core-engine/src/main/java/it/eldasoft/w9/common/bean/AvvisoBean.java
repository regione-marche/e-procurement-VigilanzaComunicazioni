package it.eldasoft.w9.common.bean;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * Bean con gli attributi necessari alla creazione della fase Avviso.
 * 
 * @author Luca.Giacomazzo
 */
public class AvvisoBean implements Serializable {

  /** UID */
	private static final long serialVersionUID = 1752347998964680801L;
	private long codiceAvviso;
  private HttpServletRequest requestHttp;
  private long codiceSistema;
  
  private DatiComuniBean datiComuni;
  
  /**
   * @return the codiceAvviso
   */
  public long getCodiceAvviso() {
    return codiceAvviso;
  }

  /**
   * @param codiceAvviso the codiceAvviso to set
   */
  public void setCodiceAvviso(long codiceAvviso) {
    this.codiceAvviso = codiceAvviso;
  }

  /**
   * @return the requestHttp
   */
  public HttpServletRequest getRequestHttp() {
    return requestHttp;
  }

  /**
   * @param requestHttp the requestHttp to set
   */
  public void setRequestHttp(HttpServletRequest requestHttp) {
    this.requestHttp = requestHttp;
  }
  
  /**
   * @return the codiceSistema
   */
  public long getCodiceSistema() {
    return codiceSistema;
  }

  /**
   * @param codiceSistema the codiceSistema to set
   */
  public void setCodiceSistema(long codiceSistema) {
    this.codiceSistema = codiceSistema;
  }

  /**
   * @return the datiComuni
   */
  public DatiComuniBean getDatiComuni() {
    return datiComuni;
  }

  /**
   * @param datiComuni the datiComuni to set
   */
  public void setDatiComuni(DatiComuniBean datiComuni) {
    this.datiComuni = datiComuni;
  }
  
}
