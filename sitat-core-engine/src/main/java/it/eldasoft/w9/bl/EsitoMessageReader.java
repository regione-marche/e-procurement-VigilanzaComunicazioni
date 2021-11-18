package it.eldasoft.w9.bl;

/**
 * Classe per la definizione dell'esito dell'operazione di scaricamento dei
 * messaggi dal CART e conseguente salvataggio sul DB di Sitat ORT.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class EsitoMessageReader {

  /** Esito dell'operazione di scaricamento dei messaggi. */
  private boolean esito;
  /** Numero di elementi letti dalla coda messaggi. */
  private int     numeroMsgLetti;
  /** Numero di elementi salvati nel DB. */
  private int     numeroMsgSalvati;
  /** Messaggio eventuale di errore in caso di esito negativo. */
  private String  msgErrore;

  /** Costruttore. */
  public EsitoMessageReader() {
    this.esito = true;
    this.numeroMsgLetti = 0;
    this.numeroMsgSalvati = 0;
    this.msgErrore = null;
  }

  /**
   * 
   * @return Ritorna esito.
   */
  public boolean isEsito() {
    return esito;
  }

  /**
   * Set dell'attributo esito.
   * 
   * @param esito
   *        esito da settare internamente alla classe.
   */
  public void setEsito(final boolean esito) {
    this.esito = esito;
  }

  /**
   * @return Ritorna numeroMsgLetti.
   */
  public int getNumeroMsgLetti() {
    return numeroMsgLetti;
  }

  /**
   * Set del numero di messaggi letti.
   * 
   * @param numeroMsgLetti
   *        numeroMsgLetti da settare internamente alla classe.
   */
  public void setNumeroMsgLetti(final int numeroMsgLetti) {
    this.numeroMsgLetti = numeroMsgLetti;
  }

  /**
   * Ritorna il nuemro di messaggi salvati.
   * 
   * @return Ritorna numeroMsgSalvati.
   */
  public int getNumeroMsgSalvati() {
    return numeroMsgSalvati;
  }

  /**
   * Set del numero di messaggi salvati.
   * 
   * @param numeroMsgSalvati
   *        numeroMsgSalvati da settare internamente alla classe.
   */
  public void setNumeroMsgSalvati(final int numeroMsgSalvati) {
    this.numeroMsgSalvati = numeroMsgSalvati;
  }

  /**
   * Ritorna il messaggio d'errore.
   * 
   * @return Ritorna msgErrore.
   */
  public String getMsgErrore() {
    return msgErrore;
  }

  /**
   * Set del messaggio d'errore.
   * 
   * @param msgErrore
   *        msgErrore da settare internamente alla classe.
   */
  public void setMsgErrore(final String msgErrore) {
    this.msgErrore = msgErrore;
  }

}
