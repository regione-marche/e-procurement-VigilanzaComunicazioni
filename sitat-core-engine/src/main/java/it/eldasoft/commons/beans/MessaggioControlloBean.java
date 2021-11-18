package it.eldasoft.commons.beans;

/**
 * Bean per i messaggi di controllo da portare a video.
 * 
 * @author Luca.Giacomazzo
 */
public class MessaggioControlloBean {

  private String tipoMessaggio;
  private String pagina;
  private String descrizioneCampo;
  private String messaggio; 
  
  public MessaggioControlloBean() {
    this.tipoMessaggio = null;
    this.pagina = null;
    this.descrizioneCampo = null;
    this.messaggio = null;
  }
  
  public MessaggioControlloBean(String tipoMessaggio, String pagina, String descrizioneCampo, String messaggio) {
    this.tipoMessaggio = tipoMessaggio;
    this.pagina = pagina;
    this.descrizioneCampo = descrizioneCampo;
    this.messaggio = messaggio;
  }

  /**
   * @return the tipoMessaggio
   */
  public String getTipoMessaggio() {
    return tipoMessaggio;
  }

  /**
   * @param tipoMessaggio the tipoMessaggio to set
   */
  public void setTipoMessaggio(String tipoMessaggio) {
    this.tipoMessaggio = tipoMessaggio;
  }

  /**
   * @return the pagina
   */
  public String getPagina() {
    return pagina;
  }

  /**
   * @param pagina the pagina to set
   */
  public void setPagina(String pagina) {
    this.pagina = pagina;
  }

  /**
   * @return the descrizioneCampo
   */
  public String getDescrizioneCampo() {
    return descrizioneCampo;
  }

  /**
   * @param descrizioneCampo the descrizioneCampo to set
   */
  public void setDescrizioneCampo(String descrizioneCampo) {
    this.descrizioneCampo = descrizioneCampo;
  }

  /**
   * @return the messaggio
   */
  public String getMessaggio() {
    return messaggio;
  }

  /**
   * @param messaggio the messaggio to set
   */
  public void setMessaggio(String messaggio) {
    this.messaggio = messaggio;
  }
  
}
