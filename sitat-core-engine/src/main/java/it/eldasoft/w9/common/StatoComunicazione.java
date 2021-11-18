package it.eldasoft.w9.common;

/**
 * Enumerato per lo stato della comunicazione con SIMOG, contenente i valori degli 
 * stati possibili per la comunicazione, da utilizzare per il campo W9INBOX.STACOM.
 * Valori allienati al tabellato W9003.
 * 
 * @author Luca.Giacomazzo
 */
public enum StatoComunicazione {

  /** Stato iniziale della comunicazione. */
  STATO_RICEVUTA(1),
  
  /**
   * Stato della comunicazione previsto nel caso di elaborazione della
   * comunicazione avvenuta con successo.
   */
  STATO_IMPORTATA(2),

  /**
   * Stato della comunicazione previsto nel caso di elaborazione della
   * comunicazione avvenuta con errori.
   */
  STATO_ERRATA(3),

  /**
   * Stato della comunicazione previsto nel caso di elaborazione della
   * comunicazione avvenuta con errori non bloccanti.
   */
  STATO_WARNING(4),

  /**
   * Stato della comunicazione previsto nel caso di elaborazione di una
   * richiesta di test, per cui viene trascurato il contenuto.
   */
  STATO_MESSAGGIO_TEST(99);
  
  private Long stato;
  
  private StatoComunicazione(int statoCom) {
    this.stato = new Long(statoCom);
    
  }
  
  public Long getStato() {
    return this.stato;
  }
  
}