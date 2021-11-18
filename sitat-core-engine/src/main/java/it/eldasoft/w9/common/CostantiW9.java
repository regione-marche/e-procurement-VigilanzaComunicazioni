package it.eldasoft.w9.common;

/**
 * Codifica dei flussi SITAT.
 * 
 * @author Luca.Giacomazzo
 */
public final class CostantiW9 {

  /**
   * Costruttore.
   */
  private CostantiW9() {
  }

  /**
   * Area di invio per le fasi di gara.
   */
  public static final int AREA_FASI_DI_GARA = 1;

  /**
   * Area di invio per anagrafica gara/lotti.
   */
  public static final int AREA_ANAGRAFICA_GARE = 2;

  /**
   * Area di invio per avvisi di gara.
   */
  public static final int AREA_AVVISI = 3;

  /**
   * Area di invio per programmi triennali o annuali.
   */
  public static final int AREA_PROGRAMMA_TRIENNALI_ANNUALI = 4;

  /**
   * Area di invio per Gare di enti nazionali e sotto i 40.000 euro.
   */
  public static final int AREA_GARE_ENTINAZIONALI = 5;

  /**
   * Anagrafica gara lotti.
   */
  public static final int A03 = 988;
  /**
   * Anagrafica gara lotti.
   */
  public static final int ANAGRAFICA_GARA_LOTTI = A03;
  

  /**
   * Fase semplificata aggiudicazione/affidamento appalto (sotto 40000).
   */
  public static final int A04 = 987;
  /**
   * Fase semplificata aggiudicazione/affidamento appalto (sotto 40000).
   */
  public static final int FASE_SEMPLIFICATA_AGGIUDICAZIONE = A04;

  /**
   * Fase aggiudicazione/affidamento appalto (sopra 40000).
   */
  public static final int A05 = 1;
  /**
   * Fase aggiudicazione/affidamento appalto (sopra 40000).
   */
  public static final int AGGIUDICAZIONE_SOPRA_SOGLIA = A05;

  /**
   * Fase iniziale esecuzione contratto (sopra 40000).
   */
  public static final int A06 = 2;
  /**
   *  Fase iniziale esecuzione contratto (sopra 40000).
   */
  public static final int INIZIO_CONTRATTO_SOPRA_SOGLIA = A06;

  /**
   * Fase semplificata iniziale esecuzione contratto (sotto 40000).
   */
  public static final int A07 = 986;
  /**
   * Fase semplificata iniziale esecuzione contratto (sotto 40000).
   */
  public static final int FASE_SEMPLIFICATA_INIZIO_CONTRATTO = A07;

  /**
   * Fase esecuzione e avanzamento del contratto (sopra 40000).
   */
  public static final int A08 = 3;
  /**
   * Fase esecuzione e avanzamento del contratto (sopra 40000).
   */
  public static final int AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA = A08;

  /**
   * Fase di conclusione del contratto (sopra 40000).
   */
  public static final int A09 = 4;
  /**
   * Fase di conclusione del contratto (sopra 40000).
   */
  public static final int CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA = A09;

  /**
   * Fase semplificata di conclusione del contratto (sotto 40000).
   */
  public static final int A10 = 985;
  /**
   * Fase semplificata di conclusione del contratto (sotto 40000).
   */
  public static final int FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO = A10;

  /**
   * Fase di collaudo del contratto.
   */
  public static final int A11 = 5;
  /**
   * Fase di collaudo del contratto.
   */
  public static final int COLLAUDO_CONTRATTO = A11;

  /**
   * Sospensione del contratto.
   */
  public static final int A12 = 6;
  /**
   * Sospensione del contratto.
   */
  public static final int SOSPENSIONE_CONTRATTO = A12;

  /**
   * Variante del contratto.
   */
  public static final int A13 = 7;
  /**
   * Variante del contratto.
   */
  public static final int VARIANTE_CONTRATTO = A13;

  /**
   * Accordi bonari.
   */
  public static final int A14 = 8;
  /**
   * Accordi bonari.
   */
  public static final int ACCORDO_BONARIO = A14;

  /**
   * Subappalti.
   */
  public static final int A15 = 9;
  /**
   * Subappalti.
   */
  public static final int SUBAPPALTO = A15;

  /**
   * Istanza di recesso.
   */
  public static final int A16 = 10;
  /**
   * Istanza di recesso.
   */
  public static final int ISTANZA_RECESSO = A16;

  /**
   * Stipula accordo quadro.
   */
  public static final int A20 = 11;
  /**
   * Stipula accordo quadro.
   */
  public static final int STIPULA_ACCORDO_QUADRO = A20;

  /**
   * Adesione accordo quadro.
   */
  public static final int A21 = 12;
  /**
   * Adesione accordo quadro.
   */
  public static final int ADESIONE_ACCORDO_QUADRO = A21;

  /**
   * Comunicazione esito.
   */
  public static final int A22 = 984;
  /**
   * Comunicazione esito.
   */
  public static final int COMUNICAZIONE_ESITO = A22;

  /**
   * Esito negativo verifica idoneita' tecnico-professionale dell'impresa aggiudicataria.
   */
  public static final int B02 = 997;
  /**
   * Esito negativo verifica idoneita' tecnico-professionale dell'impresa aggiudicataria.
   */
  public static final int ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA = B02;

  /**
   * Esito negativo verifica regolarita' contributiva ed assicurativa (versione 2.0.5)
   * Mancata aggiud. def. o pagamento per irregolarita' contrib. ed assicurativa (art.17c.1 e 2 LR 38/07) (versione 2.1.0)
   */
  public static final int B03 = 996;
  /**
   * Esito negativo verifica regolarita' contributiva ed assicurativa (versione 2.0.5)
   * Mancata aggiud. def. o pagamento per irregolarita' contrib. ed assicurativa (art.17c.1 e 2 LR 38/07) (versione 2.1.0)
   * public static final int B03 = 996;
   */
  public static final int ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA = B03;

  /**
   * Misure aggiuntive e migliorative sicurezza.
   */
  public static final int B04 = 993;
  /**
   * Misure aggiuntive e migliorative sicurezza.
   */
  public static final int MISURE_AGGIUNTIVE_SICUREZZA = B04;

  /**
   * Inadempienze predisposizioni sicurezza e regolarita' lavori (versione 2.0.5).
   * Inadempienze disposizioni sicurezza e regolarita' del lavoro ai sensi dell’art.23 LR 38/07 (versione 2.1.0)
   */
  public static final int B06 = 995;
  /**
   * Inadempienze predisposizioni sicurezza e regolarita' lavori (versione 2.0.5).
   * Inadempienze disposizioni sicurezza e regolarita' del lavoro ai sensi dell’art.23 LR 38/07 (versione 2.1.0)
   */
  public static final int INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI = B06;

  /**
   * Scheda segnalazione infortuni.
   */
  public static final int B07 = 994;
  /**
   * Scheda segnalazione infortuni.
   */
  public static final int SCHEDA_SEGNALAZIONI_INFORTUNI = B07;

  /**
   * Scheda cantiere/notifica preliminare.
   */
  public static final int B08 = 998;
  /**
   * Scheda cantiere/notifica preliminare.
   */
  public static final int APERTURA_CANTIERE = B08;

  /**
   * Pubblicazione avviso.
   */
  public static final int PUBBLICAZIONE_AVVISO = 989;

  /**
   * Pubblicazione programma triennale lavori.
   */
  public static final int PROGRAMMA_TRIENNALE_LAVORI = 992;

  /**
   *  Pubblicazione programma triennale forniture e servizi.
   */
  public static final int PROGRAMMA_TRIENNALE_FORNITURE_SERVIZI = 991;

  /**
   * Gare per enti nazionali o sotto i 40.000 euro.
   */
  public static final int A23 = 983;
  
  /**
   * Gare per enti nazionali o sotto i 40.000 euro.
   */
  public static final int GARE_ENTI_NAZIONALI_O_SOTTO_40000 = A23;
  
  /**
   * Elenco imprese invitate/partecipanti. 
   */
  public static final int A24 = 101;
  
  /**
   * Elenco imprese invitate/partecipanti. 
   */
  public static final int ELENCO_IMPRESE_INVITATE_PARTECIPANTI = A24;
  
  /**
   * Avanzamento contratto appalto semplificato.
   */
  public static final int A25 = 102;
  
  /**
   * Avanzamento contratto appalto semplificato.
   */
  public static final int AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO = A25;
  
  /**
   * Anagrafica semplificata e bando di gara.
   */
  public static final int A26 = 13;
  
  /**
   * Anagrafica semplificata e bando di gara.
   */
  public static final int ANAGRAFICA_SEMPLIFICATA_BANDO_DI_GARA = A26;
  
  /**
   * Pubblicazione documenti della gara / lotti.
   */
  public static final int D01 = 901;
  
  /**
   * Pubblicazione documenti della gara / lotti.
   */
  public static final int PUBBLICAZIONE_DOCUMENTI = D01;
  
  /**
   * Programma biennale acquisti secondo il DLgs. 50/2016
   */
  public static final int PROGRAMMA_BIENNALE_ACQUISTI = 981;
  
  /**
   * Programma triennale opere pubbliche secondo il DLgs. 50/2016
   */
  public static final int PROGRAMMA_TRIENNALE_OPERE_PUBBLICHE = 982;
  
  
  /**
   * Nome variabile di sessione che memorizza se l'utente ha il campo USRSYS.SYSCF
   * valorizzato e non e' un tecnico della S.A. attiva.
   */
  public static final String IS_USER_CONCF_NO_TECNICO_SA = "isUserConCfNoTecnicoSA";

  /**
   * Nome variabile di sessione che memorizza se l'utente e' un tecnico della S.A. attiva.
   */
  public static final String IS_USER_TECNICO_SA = "isUserTecnicoSA";
  
}