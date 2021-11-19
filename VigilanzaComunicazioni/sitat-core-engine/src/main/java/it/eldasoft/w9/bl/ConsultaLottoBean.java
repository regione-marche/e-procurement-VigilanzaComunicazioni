package it.eldasoft.w9.bl;

public class ConsultaLottoBean {

  private String  codiceCIG;
  private boolean caricato;
  private boolean esistente;
  private String  msg;
  private Long codgara;
  
  public ConsultaLottoBean(String codiceCIG) {
    this.codiceCIG = codiceCIG;
    this.caricato = false;
    this.esistente = false;
    this.msg = null;
  }
  
  public ConsultaLottoBean(String codiceCIG, boolean caricato, boolean esistente) {
    this.codiceCIG = codiceCIG;
    this.caricato = caricato;
    this.esistente = esistente;
    this.msg = null;
  }
  
  public ConsultaLottoBean(String codiceCIG, boolean caricato, boolean esistente, String messaggio) {
    this.codiceCIG = codiceCIG;
    this.caricato = caricato;
    this.esistente = esistente;
    this.msg = messaggio;
  }

  /**
   * @return the caricato
   */
  public boolean isCaricato() {
    return caricato;
  }

  /**
   * @param caricato the caricato to set
   */
  public void setCaricato(boolean caricato) {
    this.caricato = caricato;
  }

  /**
   * @return the msg
   */
  public String getMsg() {
    return msg;
  }

  /**
   * @param msg the msg to set
   */
  public void setMsg(String msg) {
    this.msg = msg;
  }

  /**
   * @return the codiceCIG
   */
  public String getCodiceCIG() {
    return codiceCIG;
  }

  /**
   * @return the esistente
   */
  public boolean isEsistente() {
    return esistente;
  }

  /**
   * @param esistente the esistente to set
   */
  public void setEsistente(boolean esistente) {
    this.esistente = esistente;
  }

public void setCodgara(Long codgara) {
	this.codgara = codgara;
}

public Long getCodgara() {
	return codgara;
}
  
}
