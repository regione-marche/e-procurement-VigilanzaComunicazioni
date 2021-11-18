package it.eldasoft.w9.common;

/**
 * Classe di costanti contenente i valori degli stati possibili per la
 * comunicazione, da utilizzare per il campo W9INBOX.STACOM.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 * 
 */
public final class CostantiStatoComunicazione {

  /**
   * Costruttore.
   */
  private CostantiStatoComunicazione() {
  }

  /** Stato iniziale della comunicazione. */
  public static final Long STATO_RICEVUTA       = new Long(1);

  /**
   * Stato della comunicazione previsto nel caso di elaborazione della
   * comunicazione avvenuta con successo.
   */
  public static final Long STATO_IMPORTATA      = new Long(2);

  /**
   * Stato della comunicazione previsto nel caso di elaborazione della
   * comunicazione avvenuta con errori.
   */
  public static final Long STATO_ERRATA         = new Long(3);

  /**
   * Stato della comunicazione previsto nel caso di elaborazione della
   * comunicazione avvenuta con errori non bloccanti.
   */
  public static final Long STATO_WARNING        = new Long(4);

  /**
   * Stato della comunicazione previsto nel caso di elaborazione di una
   * richiesta di test, per cui viene trascurato il contenuto.
   */
  public static final Long STATO_MESSAGGIO_TEST = new Long(99);
}
