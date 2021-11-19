package it.eldasoft.w9.common;

/**
 * Classe di costanti ad uso del Web service SITAT.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public final class CostantiFlussi {

  /**
   * Tag principale della richiesta di approvazione del programma per un appalto
   * per lavori.
   */
  public static final String MAIN_TAG_RICHIESTA_APPROVAZIONE_PROGRAMMA_LAVORI                  = "richiesta_RichiestaRispostaSincrona_ApprovazioneProgrammaLavori";

  /**
   * Tag principale della richiesta di approvazione del programma per un appalto
   * per forniture e servizi.
   */
  public static final String MAIN_TAG_RICHIESTA_APPROVAZIONE_PROGRAMMA_FORNITURE_SERVIZI       = "richiesta_RichiestaRispostaSincrona_ApprovazioneProgrammaFornitureServizi";

  /**
   * Tag principale della richiesta di approvazione del programma per un appalto
   * per opere pubbliche secondo l'ex DLgs 50/2016.
   */
  public static final String MAIN_TAG_RICHIESTA_APPROVAZIONE_PROGRAMMA_TRIENNALE_OPERE_PUBBLICHE = "richiesta_RichiestaRispostaSincrona_ApprovazioneProgrammaTriennaleOperePubbliche"; 

  /**
   * Tag principale della richiesta di approvazione del programma per un appalto
   * per acquisti secondo l'ex DLgs 50/2016.
   */
  public static final String MAIN_TAG_RICHIESTA_APPROVAZIONE_PROGRAMMA_BIENNALE_ACQUISTI         = "richiesta_RichiestaRispostaSincrona_ApprovazioneProgrammaBiennaleAcquisti";
  
  /**
   * Tag principale della richiesta di anagrafica lotto e/o gara.
   */
  public static final String MAIN_TAG_RICHIESTA_ANAGRAFICA_LOTTO_GARA                          = "richiesta_RichiestaRispostaSincrona_AnagraficaLottoGara";

  /**
   * Tag principale della richiesta di anagrafica semplificata e bando di gara.
   */
  public static final String MAIN_TAG_RICHIESTA_ANAGRAFICA_SEMPLIFICATA_E_BANDO                = "richiesta_RichiestaRispostaSincrona_AnagraficaSemplificataeBando";
  
  /**
   * Tag principale della richiesta di pubblicazione bando.
   */
  public static final String MAIN_TAG_RICHIESTA_PUBBLICAZIONE_BANDO                            = "richiesta_RichiestaRispostaSincrona_PubblicazioneBando";

  /**
   * Tag principale della richiesta di pubblicazione avviso.
   */
  public static final String MAIN_TAG_RICHIESTA_PUBBLICAZIONE_AVVISO                           = "richiesta_RichiestaRispostaSincrona_PubblicazioneAvviso";

  /**
   * Tag principale della richiesta di aggiudicazione appalto sotto 150.000 euro.
   */
  public static final String MAIN_TAG_RICHIESTA_AGGIUDICAZIONE_APPALTO_SOTTO_150000            = "richiesta_RichiestaRispostaSincrona_AggiudicazioneAppaltoSotto150000";

  /**
   * Tag principale della richiesta di inizio contratto appalto sotto 150.000.
   * euro
   */
  public static final String MAIN_TAG_RICHIESTA_INIZIO_CONTRATTO_APPALTO_SOTTO_150000          = "richiesta_RichiestaRispostaSincrona_InizioContrattoSotto150000";

  /**
   * Tag principale della richiesta di conclusione contratto appalto sotto
   * 150.000 euro.
   */
  public static final String MAIN_TAG_RICHIESTA_CONCLUSIONE_CONTRATTO_APPALTO_SOTTO_150000     = "richiesta_RichiestaRispostaSincrona_ConclusioneContrattoSotto150000";

  /**
   * Tag principale della richiesta di invio scheda sicurezza.
   */
  public static final String MAIN_TAG_RICHIESTA_SCHEDA_SICUREZZA                               = "richiesta_RichiestaRispostaSincrona_SchedaSicurezza";

  /**
   * Tag principale della richiesta di invio esito negativo verifica idoneita'
   * tecnico-professionale.
   */
  public static final String MAIN_TAG_RICHIESTA_ESITO_NEG_CHECK_IDONEITA                       = "richiesta_RichiestaRispostaSincrona_EsitoNegCheckIdoneita";

  /**
   * Tag principale della richiesta di invio esito negativo verifica regolarita'
   * contributiva ed assicurativa.
   */
  public static final String MAIN_TAG_RICHIESTA_ESITO_NEG_CHECK_REGOLARITA                     = "richiesta_RichiestaRispostaSincrona_EsitoNegCheckRegolarita";

  /**
   * Tag principale della richiesta di inadempienze sulla sicurezza.
   */
  public static final String MAIN_TAG_RICHIESTA_CHECK_MISURE_SICUREZZA                         = "richiesta_RichiestaRispostaSincrona_CheckMisureSicurezza";

  /**
   * Tag principale della richiesta di inadempienze sulla sicurezza.
   */
  public static final String MAIN_TAG_RICHIESTA_INADEMPIENZE_SICUREZZA                         = "richiesta_RichiestaRispostaSincrona_InadempienzeSicurezza";

  /**
   * Tag principale della richiesta di segnalazione infortunio.
   */
  public static final String MAIN_TAG_RICHIESTA_INFORTUNIO                                     = "richiesta_RichiestaRispostaSincrona_Infortunio";

  /**
   * Tag principale della richiesta di scheda cantiere.
   */
  public static final String MAIN_TAG_RICHIESTA_APERTURA_CANTIERE                              = "richiesta_RichiestaRispostaSincrona_AperturaCantiere";

  /**
   * Tag principale della richiesta di aggiudicazione appalto sopra 150.000 euro.
   */
  public static final String MAIN_TAG_RICHIESTA_AGGIUDICAZIONE_APPALTO_SOPRA_150000            = "richiesta_RichiestaRispostaSincrona_AggiudicazioneAppaltoSopra150000";

  /**
   * Tag principale della richiesta di inizio contratto appalto sopra 150.000
   * euro.
   */
  public static final String MAIN_TAG_RICHIESTA_INIZIO_CONTRATTO_APPALTO_SOPRA_150000          = "richiesta_RichiestaRispostaSincrona_InizioContrattoSopra150000";

  /**
   * Tag principale della richiesta di avanzamento contratto appalto sopra
   * 150.000 euro.
   */
  public static final String MAIN_TAG_RICHIESTA_AVANZAMENTO_CONTRATTO_APPALTO_SOPRA_150000     = "richiesta_RichiestaRispostaSincrona_AvanzamentoContrattoSopra150000";

  /**
   * Tag principale della richiesta di conclusione contratto appalto sopra
   * 150.000 euro.
   */
  public static final String MAIN_TAG_RICHIESTA_CONCLUSIONE_CONTRATTO_APPALTO_SOPRA_150000     = "richiesta_RichiestaRispostaSincrona_ConclusioneContrattoSopra150000";

  /**
   * Tag principale della richiesta di collaudo contratto appalto sopra 150.000
   * euro.
   */
  public static final String MAIN_TAG_RICHIESTA_COLLAUDO_CONTRATTO_APPALTO_SOPRA_150000        = "richiesta_RichiestaRispostaSincrona_CollaudoContrattoSopra150000";

  /**
   * Tag principale della richiesta di sospensione contratto appalto sopra
   * 150.000 euro.
   */
  public static final String MAIN_TAG_RICHIESTA_SOSPENSIONE_CONTRATTO_APPALTO_SOPRA_150000     = "richiesta_RichiestaRispostaSincrona_SospensioneContrattoSopra150000";

  /**
   * Tag principale della richiesta di variante contratto appalto sopra 150.000
   * euro.
   */
  public static final String MAIN_TAG_RICHIESTA_VARIANTE_CONTRATTO_APPALTO_SOPRA_150000        = "richiesta_RichiestaRispostaSincrona_VarianteContrattoSopra150000";

  /**
   * Tag principale della richiesta di accordo bonario contratto appalto sopra
   * 150.000 euro.
   */
  public static final String MAIN_TAG_RICHIESTA_ACCORDO_BONARIO_CONTRATTO_APPALTO_SOPRA_150000 = "richiesta_RichiestaRispostaSincrona_AccordoBonarioContrattoSopra150000";

  /**
   * Tag principale della richiesta di subappalto contratto appalto sopra
   * 150.000 euro.
   */
  public static final String MAIN_TAG_RICHIESTA_SUBAPPALTO_CONTRATTO_APPALTO_SOPRA_150000      = "richiesta_RichiestaRispostaSincrona_SubappaltoContrattoSopra150000";

  /**
   * Tag principale della richiesta di recesso contratto appalto sopra 150.000
   * euro.
   */
  public static final String MAIN_TAG_RICHIESTA_RECESSO_CONTRATTO_APPALTO_SOPRA_150000         = "richiesta_RichiestaRispostaSincrona_RecessoContrattoSopra150000";

  /**
   * Tag principale della richiesta di stipula accordo quadro.
   */
  public static final String MAIN_TAG_RICHIESTA_STIPULA_ACCORDO_QUADRO                         = "richiesta_RichiestaRispostaSincrona_StipulaAccordoQuadro";

  /**
   * Tag principale della richiesta di Adesione accordo quadro.
   */
  public static final String MAIN_TAG_RICHIESTA_ADESIONE_ACCORDO_QUADRO                        = "richiesta_RichiestaRispostaSincrona_AdesioneAccordoQuadro";

  /**
   * Tag principale della richiesta di Comunicazione esito.
   */
  public static final String MAIN_TAG_RICHIESTA_COMUNICAZIONE_ESITO                            = "richiesta_RichiestaRispostaSincrona_ComunicazioneEsito";

  /**
   * Tag principale della richiesta di Gare enti nazionali o inferiori a 40.000 euro.
   */
  public static final String MAIN_TAG_RICHIESTA_GARE_ENTI_NAZIONALI_O_SOTTO_40000              = "richiesta_RichiestaRispostaSincrona_GaraEnteNazionaleSotto40000";

  /**
   * Tag principale della richiesta di Elenco imprese invitate/partecipanti.
   */
  public static final String MAIN_TAG_RICHIESTA_ELENCO_IMPRESE_INVITATE_PARTECIPANTI           = "richiesta_RichiestaRispostaSincrona_ElencoImpreseInvitatePartecipanti";
  
  /**
   * Tag principale della richiesta di Avanzamento semplificato contratto appalto.
   */
  public static final String MAIN_TAG_RICHIESTA_AVANZAMENTO_SEMPLIFICATO_CONTRATTO_APPALTO     = "richiesta_RichiestaRispostaSincrona_AvanzamentoContrattoAppaltoSemplificato";

  /**
   * Tag principale della richiesta di Pubblicazione documenti della gara / lotti.
   */
  public static final String MAIN_TAG_RICHIESTA_PUBBLICAZIONE_DOCUMENTI = "richiesta_RichiestaRispostaSincrona_PubblicazioneDocumenti";
  
  /**
   * Tag principale della richiesta di Pubblicazione documenti della gara / lotti.
   */
  public static final String MAIN_TAG_RICHIESTA_ELIMINAZIONE_SCHEDA = "richiesta_RichiestaRispostaSincrona_EliminazioneScheda";
  
  /** Costruttore vuoto. */
  private CostantiFlussi() {
  }
}