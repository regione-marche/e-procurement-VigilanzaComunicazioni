package it.eldasoft.commons.beans;

public class ControlloBean {

  private String codapp;
  private String codFunzione;
  private Long   num;
  private String entita;
  private String sezione;
  private String titolo;
  private String condizione;
  private String messaggio;
  private String tipo;
  
  public ControlloBean() {
    this.codapp = null;
    this.codFunzione = null;
    this.num = null;
    this.entita = null;
    this.sezione = null;
    this.titolo = null;
    this.condizione = null;
    this.messaggio = null;
    this.tipo = null;
  }

  /**
   * @return the codapp
   */
  public String getCodapp() {
    return codapp;
  }

  /**
   * @param codapp the codapp to set
   */
  public void setCodapp(String codapp) {
    this.codapp = codapp;
  }

  /**
   * @return the num
   */
  public Long getNum() {
    return num;
  }

  /**
   * @param num the num to set
   */
  public void setNum(Long num) {
    this.num = num;
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
   * @return the sezione
   */
  public String getSezione() {
    return sezione;
  }

  /**
   * @param sezione the sezione to set
   */
  public void setSezione(String sezione) {
    this.sezione = sezione;
  }

  /**
   * @return the titolo
   */
  public String getTitolo() {
    return titolo;
  }

  /**
   * @param titolo the titolo to set
   */
  public void setTitolo(String titolo) {
    this.titolo = titolo;
  }

  /**
   * @return the condizione
   */
  public String getCondizione() {
    return condizione;
  }

  /**
   * @param condizione the condizione to set
   */
  public void setCondizione(String condizione) {
    this.condizione = condizione;
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
   * @return the codFunzione
   */
  public String getCodFunzione() {
    return codFunzione;
  }

  /**
   * @param codFunzione the codFunzione to set
   */
  public void setCodFunzione(String codFunzione) {
    this.codFunzione = codFunzione;
  }
  
}
