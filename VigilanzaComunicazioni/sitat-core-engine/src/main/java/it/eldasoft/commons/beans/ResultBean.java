package it.eldasoft.commons.beans;

/**
 * Oggetto per il risultato di una operazione
 * 
 * @author Luca.Giacomazzo
 */
public class ResultBean {

  private Boolean ok;
  private String[] messaggi;
  
  public ResultBean() {
    this.ok = null;
    this.messaggi = null;
  }
  
  public ResultBean(boolean isOk, String messaggio) {
    this.ok = Boolean.valueOf(isOk);
    this.messaggi = new String[] { messaggio };
  }
  
  public ResultBean(boolean isOk, String[] messaggi) {
    this.ok = Boolean.valueOf(isOk);
    this.messaggi = messaggi;
  }

  /**
   * @return the ok
   */
  public Boolean getOk() {
    return ok;
  }

  /**
   * @param ok the ok to set
   */
  public void setOk(Boolean ok) {
    this.ok = ok;
  }

  /**
   * @return the messaggi
   */
  public String[] getMessaggi() {
    return messaggi;
  }

  /**
   * @param messaggi the messaggi to set
   */
  public void setMessaggi(String[] messaggi) {
    this.messaggi = messaggi;
  }
  
}
