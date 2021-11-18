package it.eldasoft.w9.common;

/**
 * Enumerato per la situazione della gara o del lotto.
 * 
 * Costanti per la definizione della situazione di un lotto o di una gara.
 * Queste costanti sono allineate con il tabellato W9007 in TAB1.
 * 
 * @author Luca.Giacomazzo
 */
public enum SituazioneGaraLotto {
  
  /**
   * Situazione: in gara.
   */
  SITUAZIONE_IN_GARA(1),
  /**
   * Situazione: aggiudicato.
   */
  SITUAZIONE_AGGIUDICATO(2),
  /**
   * Situazione: concluso.
   */
  SITUAZIONE_INIZIATO(3),
  /**
   * Situazione: in esecuzione.
   */
  SITUAZIONE_IN_ESECUZIONE(4),
  /**
   * Situazione: sospeso.
   */
  SITUAZIONE_SOSPESO(5),
  /**
   * Situazione: concluso.
   */
  SITUAZIONE_CONCLUSO(6),
  /**
   * Situazione: collaudato.
   */
  SITUAZIONE_COLLAUDATO(7),
  /**
   * Situazione: annullato.
   */
  SITUAZIONE_ANNULLATO(8),
  /**
   * Situazione: annullato.
   */
  SITUAZIONE_RICHIESTA_CANCELLAZIONE(95);
  
  private int situazione;
  
  private SituazioneGaraLotto(int s) {
    this.situazione = s;
  }
  
  public int getSituazione() {
    return this.situazione;
  }
  
}
