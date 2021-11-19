package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.Arch1Type;
import it.toscana.rete.rfc.sitat.types.Arch2Type;
import it.toscana.rete.rfc.sitat.types.Arch3Type;
import it.toscana.rete.rfc.sitat.types.Arch4Type;
import it.toscana.rete.rfc.sitat.types.Arch5Type;
import it.toscana.rete.rfc.sitat.types.Arch6Type;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.FlagSNType.Enum;
import it.toscana.rete.rfc.sitat.types.W3020Type;
import it.toscana.rete.rfc.sitat.types.W3998Type;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * Handler del Design Pattern "Chain of Responsibility" per definire
 * l'interfaccia standard di tutti i gestori della catena di
 * responsabilit&agrave; per il processing degli eventi ricevuti. All'interno
 * &egrave; utilizzato inoltre anche il Design Pattern "Template" in modo da
 * generalizzare il pi&ugrave; possibile la struttura dell'algoritmo e lasciare
 * la ridefinizione di alcuni passi ai singoli elementi della catena.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public abstract class AbstractRequestHandler {

  // Tipi di invio
	/** Richiesta cancellazione. */
	public static final int       RICHIESTA_CANCELLAZIONE  = -1;
  /** Primo invio. */
  public static final int       PRIMO_INVIO  = 1;
  /** Rettifica. */
  public static final int       RETTIFICA    = 2;

  /** Testo per richiesta cancellazione. */
  public static final String    TEXT_RICHIESTA_CANCELLAZIONE  = "richiesta cancellazione";
  /** Testo per il primo invio. */
  public static final String    TEXT_PRIMO_INVIO  = "primo invio";
  /** Testo per la rettifica di un flusso. */
  public static final String    TEXT_RETTIFICA    = "rettifica o integrazione";

  /** Lotto da esportare. */
  public static final String    LOTTO_DA_ESPORTARE = "1";
  /** Lotto esportato. */
  public static final String    LOTTO_ESPORTATO    = "2";
  /** Logger di classe. */
  Logger                         logger = Logger.getLogger(AbstractRequestHandler.class);

  /**
   * 
   */
  private AbstractRequestHandler nextHandler;

  /**
   * Managere delle transazioni SQL.
   */
  protected SqlManager           sqlManager;

  /**
   * Manager del generatore di chiavi.
   */
  protected GenChiaviManager     genChiaviManager;

  /**
   * Manager di funzionalita' generali.
   */
  private GeneManager            geneManager;

  /**
   * Manager di funzionalita' specifiche di Sitat.
   */
  protected W9Manager             w9Manager;
  
  /**
   * Set nextHanlder.
   * 
   * @param nextHandler
   *        nextHandler da settare internamente alla classe.
   */
  public void setNextHandler(AbstractRequestHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  /**
   * Sel sqlManager.
   * 
   * @param sqlManager
   *        sqlManager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   * Set genChiaviManager.
   * 
   * @param genChiaviManager
   *        genChiaviManager da settare internamente alla classe.
   */
  public void setGenChiaviManager(GenChiaviManager genChiaviManager) {
    this.genChiaviManager = genChiaviManager;
  }

  /**
   * Set geneManager.
   * 
   * @param geneManager
   *        geneManager da settare internamente alla classe.
   */
  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }
  
  /**
   * Set W9Manager.
   * 
   * @param w9Manager the w9Manager to set
   */
  public void setW9Manager(W9Manager w9Manager) {
    this.w9Manager = w9Manager;
  }

  /**
   * Processa la richiesta se &egrave; di propria competenza, altrimenti demanda
   * al gestore successivo della catena.<br>
   * Se la gestione &egrave; di propria competenza, effettua la
   * deserializzazione della richiesta, effettua i controlli, quindi procede
   * all'aggiornamento del database, oppure termina segnalando nella W9INBOX
   * l'errore rilevato
   * 
   * @param xmlEvento
   *        messaggio XML da processare
   * @param datiAggiornamento
   *        container con i dati delle informazioni da aggiornare nel DB
   * @param ignoreWarnings
   *        true se i warnings vanno solamente tracciati e si procede inserendo
   *        i dati, false se i warning non vanno trascurati, e quindi oltre alla
   *        tracciatura devono bloccare l'inserimento dei dati.
   */
  public void processEvento(String xmlEvento, DataColumnContainer datiAggiornamento,
      boolean ignoreWarnings) throws GestoreException, SQLException {

    if (xmlEvento != null && xmlEvento.indexOf(this.getMainTagRequest()) > 0) {
      // se e' presente il tag principale, allora si puo' provare a processare
      // la richiesta

      logger.debug("Gestione della richiesta: " + xmlEvento);

      try {
        XmlObject documento = this.getXMLDocument(xmlEvento);

        if (this.isTest(documento)) {
          // non si dovrebbe verificare mai, vuol dire che arriva un messaggio
          // che dovrebbe essere filtrato dal proxy applicativo
          this.setEsito(datiAggiornamento, StatoComunicazione.STATO_MESSAGGIO_TEST.getStato(),
          "Test di richiesta erroneamente ricevuta dall'Osservatorio");
        } else {
          // si entra nella gestione effettiva del messaggio
          FaseEstesaType faseInvio = this.getFaseInvio(documento);

          // Verifica della versione del file XML
          String versioneTracciatoXML = ConfigManager.getValore("it.eldasoft.rt.sitatproxy.versioneTracciatoXML");

          if (StringUtils.isNotEmpty(versioneTracciatoXML)
              && versioneTracciatoXML.trim().equalsIgnoreCase(faseInvio.getW9FLVERXML().trim())) {

            // Verifica dell'esistenza della stazione applatante che ha inviato il flusso
            if (this.existsStazioneAppaltante(faseInvio)) {
              if (faseInvio.getW3PGTINVIO2() == W3998Type.X_1) {
                this.managePrimoInvio(documento, datiAggiornamento, ignoreWarnings);
              } else if (faseInvio.getW3PGTINVIO2() == W3998Type.X_2) {
                this.manageRettifica(documento, datiAggiornamento, ignoreWarnings);
              } else if (faseInvio.getW3PGTINVIO2() == W3998Type.X_99) {
                // questa tipologia di invio riguarda messaggi di test che arrivano
                // fino all'Osservatorio, di cui va trascurata l'elaborazione
                datiAggiornamento.getColumn("W9INBOX.STACOM").setObjectValue(
                    StatoComunicazione.STATO_MESSAGGIO_TEST.getStato());
                datiAggiornamento.getColumn("W9INBOX.MSG").setObjectValue(
                    this.getNomeFlusso() + " - Test di richiesta");
              } else if (faseInvio.getW3PGTINVIO2() == W3998Type.X_1_2) {
            	  //Richiesta Elimina fase
            	  this.managePrimoInvio(documento, datiAggiornamento, ignoreWarnings);
              }
              this.updateW9Flussi(xmlEvento, faseInvio, datiAggiornamento);
              
              // Aggiornamento della situazione della gara e del lotto dopo il primo invio
              // di qualsiasi flusso, ad eccezione dei flussi Comunicazione Esito e Anagrafica Gara/Lotto.
              // Per il flusso Comunicazione Esito e' necessario calcolare la situazione gara lotto 
              // perche' una rettifica potrebbe dire che un lotto non e' stato aggiudicato.
              // Per il flusso Anagrafica gara/lotto e' necessario calcolare la situazione gara lotto
              // perche' la rettifica prevede la cancellazione delle occerrenze esistenti e quindi e' 
              // necessario rivalorizzare i campi SITUAZIONE di W9GARA e W9LOTT.
              /*if (faseInvio.getW3FASEESEC() != W3020Type.X_991      // Piani triennali per FS
                  && faseInvio.getW3FASEESEC() != W3020Type.X_981     // Piani biennali per acquisti
                  && faseInvio.getW3FASEESEC() != W3020Type.X_992     // Piani triennali per Lavori
                  && faseInvio.getW3FASEESEC() != W3020Type.X_982     // Piani triennali per opere pubbliche
                  && faseInvio.getW3FASEESEC() != W3020Type.X_989     // Pubblicazione avviso
                  && faseInvio.getW3FASEESEC() != W3020Type.X_983) {  // Gara per enti nazionali
                */

              if (this.isEsito(datiAggiornamento) && faseInvio.getW3PGTINVIO2() != W3998Type.X_1_2) {
                // Aggiornamento dello stato di gara e lotto solo nel caso l'importazione
                // del flusso sia terminata con successo e che la richiesta non sia una richiesta di cancellazione.
                if (UtilitySITAT.isArea1(Integer.parseInt(faseInvio.getW3FASEESEC().toString()))) {
                  this.aggiornaSituazioneGaraLotto(faseInvio, datiAggiornamento);
                }
              }
            } else {
              this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
              "Il flusso e' stato inviato da una stazione appaltante non presente in banca dati");
            }
          } else {
            if (StringUtils.isNotEmpty(versioneTracciatoXML)) {
              this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
              "La versione del file xml non e' supportata dall'applicativo");
            } else {
              this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
              "Non e' stata specificata la versione del file xml supportata dall'applicativo");
            }
          }
          logger.debug("Esito dell'elaborazione della richiesta: "
              + datiAggiornamento.getLong("W9INBOX.STACOM").longValue());
        }

      } catch (XmlException e) {
        logger.error("Errore inaspettato in fase di interpretazione del messaggio XML ricevuto", e);
        // se il messaggio per qualche motivo inspiegabile non viene
        // deserializzato da XMLBeans, allora non e' interpretabile per cui si
        // mette in errore la comunicazione
        try {
          datiAggiornamento.getColumn("W9INBOX.STACOM").setObjectValue(StatoComunicazione.STATO_ERRATA.getStato());
          datiAggiornamento.getColumn("W9INBOX.MSG").setObjectValue("Formato XML della richiesta non valido");
        } catch (GestoreException e1) {
          // in questa gestione non si entra in quanto le operazioni su
          // datiAggiornamento sono formalmente corrette
        }
      }

      logger.debug("Fine della gestione della richiesta: " + xmlEvento);

    } else {
      // l'evento non e' di propria competenza, si passa la richiesta al
      // successore
      this.forwardSendEvento(xmlEvento, datiAggiornamento, ignoreWarnings);
    }
  }

  /**
   * Inserimento dei campi nel record W9FASI, o aggiornamento del campo DAEXPORT.
   * 
   * Alcune fasi sono gestite da Simog (quelle con fase da 1 a 12 e 987), ma se
   * queste per qualche motivo sono relative ad un lotto con un importo totale
   * inferiore a 40000 euro, queste non devono essere comunque inviate a Simog.
   * Inoltre se il lotto non viene aggiudicato e se l'importo del lotto e' superiore
   * a 40000 euro, allora i dati della fase 'Comunicazione esito' devono essere
   * inviati a Simog.
   * 
   * @param codGara
   *        codice gara
   * @param codLott
   *        codice lotto
   * @param fase
   *        fase
   * @param datiAggiornamento
   *        contenitore dati da aggiornare
   * @param primoInvio 
   * 		Primo Invio
   * @throws GestoreException GestoreException
   * @throws SQLException SQLException
   */  
  protected void updateW9Fasi(final Long codGara, final Long codLott, final FaseEstesaType fase, 
		  final DataColumnContainer datiAggiornamento, final boolean primoInvio) 
  				throws GestoreException, SQLException {
  	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio, null, null);
  }
  
  /**
   * Inserimento dei campi nel record W9FASI, con valorizzazione idSchedaLocale e idSchedaSimog, o aggiornamento del campo DAEXPORT.
   * 
   * Alcune fasi sono gestite da Simog (quelle con fase da 1 a 12 e 987), ma se
   * queste per qualche motivo sono relative ad un lotto con un importo totale
   * inferiore a 40000 euro, queste non devono essere comunque inviate a Simog.
   * Inoltre se il lotto non viene aggiudicato e se l'importo del lotto e' superiore
   * a 40000 euro, allora i dati della fase 'Comunicazione esito' devono essere
   * inviati a Simog.
   * 
   * @param codGara			codice gara
   * @param codLott			codice lotto
   * @param fase 				fase
   * @param datiAggiornamento			contenitore dati da aggiornare
   * @param primoInvio						Primo Invio
   * @param idSchedaLocale				IdSchedaLocale
   * @param idSchedaSimog	 				IdSchedaSimog
   * @throws GestoreException 		GestoreException
   * @throws SQLException 				SQLException
   */
  protected void updateW9Fasi(final Long codGara, final Long codLott, final FaseEstesaType fase, 
		  final DataColumnContainer datiAggiornamento, final boolean primoInvio, String idSchedaLocale, String idSchedaSimog) 
  				throws GestoreException, SQLException {

    boolean isS2 = UtilitySITAT.isS2(codGara, codLott, this.sqlManager);
    
	  if (primoInvio) {
	    // predisposizione dati W9FASI: i dati facoltativi vengono testati
	    // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
	    datiAggiornamento.addColumn("W9FASI.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
	    datiAggiornamento.addColumn("W9FASI.CODLOTT", JdbcParametro.TIPO_NUMERICO, codLott);
	    datiAggiornamento.addColumn("W9FASI.FASE_ESECUZIONE",
	        JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3FASEESEC().toString()));
	    datiAggiornamento.addColumn("W9FASI.NUM_APPA",
		        JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));

	    /*
	     Per le seguenti fasi non bisogna valorizzare il campo W9FASI.DAEXPORT
	     perche' sono fasi non previste in SIMOG

	      FASE_ESECUZIONE=101   ElencoImprese
	      FASE_ESECUZIONE=102   Avanzamento semplificato
	      FASE_ESECUZIONE=985   Conclusione semplificata
	      FASE_ESECUZIONE=986   Inizio semplificata
	      FASE_ESECUZIONE=994   Infortuni
	      FASE_ESECUZIONE=995   Inadempienze sicurezza
	      FASE_ESECUZIONE=996   Inidoneità contributiva
	      FASE_ESECUZIONE=997   Inidoneità tecnico professionale
	      FASE_ESECUZIONE=998   Scheda cantiere
	     */
	    
	    switch (fase.getW3FASEESEC().intValue()) {
	    case W3020Type.INT_X_101:
	    case W3020Type.INT_X_102:
	    case W3020Type.INT_X_985:
	    case W3020Type.INT_X_986:
	    case W3020Type.INT_X_994:
	    case W3020Type.INT_X_995:
	    case W3020Type.INT_X_996:
	    case W3020Type.INT_X_997:
	    case W3020Type.INT_X_998:
	      datiAggiornamento.addColumn("W9FASI.DAEXPORT", JdbcParametro.TIPO_TESTO, null);
	      break;

      /*
       * FASE_ESECUZIONE=984   Comunicazione Esito
       */
	    case W3020Type.INT_X_984:
	      if (isS2) {
	        Long esitoProcedura = (Long) this.sqlManager.getObject("select ESITO_PROCEDURA from W9ESITO where CODGARA=? and CODLOTT=?",
	            new Object[] { codGara, codLott });
	        if (esitoProcedura != null && esitoProcedura.longValue() != 1) {
	          datiAggiornamento.addColumn("W9FASI.DAEXPORT", JdbcParametro.TIPO_TESTO, LOTTO_DA_ESPORTARE);
	        } else {
	          datiAggiornamento.addColumn("W9FASI.DAEXPORT", JdbcParametro.TIPO_TESTO, null);
	        }
	      } else {
	        datiAggiornamento.addColumn("W9FASI.DAEXPORT", JdbcParametro.TIPO_TESTO, null);
	      }
	      break;
	      
	    case W3020Type.INT_X_1:
	    case W3020Type.INT_X_2:
	    case W3020Type.INT_X_3:
	    case W3020Type.INT_X_4:
	    case W3020Type.INT_X_5:
	    case W3020Type.INT_X_6:
	    case W3020Type.INT_X_7:
	    case W3020Type.INT_X_8:
	    case W3020Type.INT_X_9:
	    case W3020Type.INT_X_10:
      case W3020Type.INT_X_11:
      case W3020Type.INT_X_12:
        if (isS2) {
          datiAggiornamento.addColumn("W9FASI.DAEXPORT", JdbcParametro.TIPO_TESTO, LOTTO_DA_ESPORTARE);
        } else {
          datiAggiornamento.addColumn("W9FASI.DAEXPORT", JdbcParametro.TIPO_TESTO, null);
        }
        break;
      case W3020Type.INT_X_987:
	      if (UtilitySITAT.isImportoLottoSoprasoglia(codGara, codLott, this.sqlManager)) {
	        datiAggiornamento.addColumn("W9FASI.DAEXPORT", JdbcParametro.TIPO_TESTO, LOTTO_DA_ESPORTARE);
	      } else {
	        datiAggiornamento.addColumn("W9FASI.DAEXPORT", JdbcParametro.TIPO_TESTO, null);
	      }
	      break;
	    }
	    
	    datiAggiornamento.addColumn("W9FASI.NUM", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()));
	    if (StringUtils.isEmpty(idSchedaLocale)) {
	    	datiAggiornamento.addColumn("W9FASI.ID_SCHEDA_LOCALE", JdbcParametro.TIPO_TESTO,
	    			fase.getW3FACIG() + "_"
	    					+ UtilityStringhe.fillLeft(fase.getW3FASEESEC().toString(), '0', 3)
	    					+ "_" + UtilityStringhe.fillLeft("" + fase.getW3NUM5(), '0', 3));
	    } else {
	    	datiAggiornamento.addColumn("W9FASI.ID_SCHEDA_LOCALE", JdbcParametro.TIPO_TESTO, idSchedaLocale);
	    }
	    
	    if (StringUtils.isNotEmpty(idSchedaSimog)) {
	    	datiAggiornamento.addColumn("W9FASI.ID_SCHEDA_SIMOG", JdbcParametro.TIPO_TESTO, idSchedaSimog);
	    }
	    
		  datiAggiornamento.insert("W9FASI", this.sqlManager);
	  
	  } else {
	    
	    switch (fase.getW3FASEESEC().intValue()) {
  		  case W3020Type.INT_X_101:
  		  case W3020Type.INT_X_102:
  		  case W3020Type.INT_X_985:
  		  case W3020Type.INT_X_986:
  		  case W3020Type.INT_X_994:
  		  case W3020Type.INT_X_995:
  		  case W3020Type.INT_X_996:
  		  case W3020Type.INT_X_997:
  		  case W3020Type.INT_X_998:
  		    this.sqlManager.update(
  	          "update W9FASI set DAEXPORT=null where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
  	            new Object[]{ codGara, codLott, new Long(fase.getW3FASEESEC().toString()), 
  	              new Long(fase.getW3NUM5())}, 1);
			break;
			
        /*
         * FASE_ESECUZIONE=984   Comunicazione Esito
         */
  		  case W3020Type.INT_X_984:
          if (isS2) {
            Long esitoProcedura = (Long) this.sqlManager.getObject("select ESITO_PROCEDURA from W9ESITO where CODGARA=? and CODLOTT=?",
                new Object[] { codGara, codLott });
            if (esitoProcedura != null && esitoProcedura.longValue() != 1) {
              this.sqlManager.update(
                  "update W9FASI set DAEXPORT=? where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
                    new Object[]{LOTTO_DA_ESPORTARE, codGara, codLott, new Long(fase.getW3FASEESEC().toString()), 
                      new Long(fase.getW3NUM5())}, 1);
              
            } else {
              this.sqlManager.update(
                  "update W9FASI set DAEXPORT=null where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
                    new Object[]{ codGara, codLott, new Long(fase.getW3FASEESEC().toString()), 
                      new Long(fase.getW3NUM5())}, 1);
            }
          } else {
            this.sqlManager.update(
                "update W9FASI set DAEXPORT=null where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
                  new Object[]{ codGara, codLott, new Long(fase.getW3FASEESEC().toString()), 
                    new Long(fase.getW3NUM5())}, 1);
          }
  		  break;
  		  
  		  case W3020Type.INT_X_1:
  		  case W3020Type.INT_X_2:
  	    case W3020Type.INT_X_3:
  	    case W3020Type.INT_X_4:
  	    case W3020Type.INT_X_5:
  	    case W3020Type.INT_X_6:
  	    case W3020Type.INT_X_7:
  	    case W3020Type.INT_X_8:
  	    case W3020Type.INT_X_9:
  	    case W3020Type.INT_X_10:
  	    case W3020Type.INT_X_11:
  	    case W3020Type.INT_X_12:
  	      if (isS2) {
  	        this.sqlManager.update(
  	            "update W9FASI set DAEXPORT=? where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
  	              new Object[]{LOTTO_DA_ESPORTARE, codGara, codLott, new Long(fase.getW3FASEESEC().toString()), 
  	                new Long(fase.getW3NUM5())}, 1);
  	      } else {
  	        this.sqlManager.update(
  	            "update W9FASI set DAEXPORT=null where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
  	              new Object[]{ codGara, codLott, new Long(fase.getW3FASEESEC().toString()), 
  	                new Long(fase.getW3NUM5())}, 1);
  	      }
  	      break;
        case W3020Type.INT_X_987:
          if (UtilitySITAT.isImportoLottoSoprasoglia(codGara, codLott, this.sqlManager)) {
            this.sqlManager.update(
                "update W9FASI set DAEXPORT=? where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
                  new Object[]{LOTTO_DA_ESPORTARE, codGara, codLott, new Long(fase.getW3FASEESEC().toString()), 
                    new Long(fase.getW3NUM5())}, 1);
          } else {
            this.sqlManager.update(
                "update W9FASI set DAEXPORT=null where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
                  new Object[]{ codGara, codLott, new Long(fase.getW3FASEESEC().toString()), 
                    new Long(fase.getW3NUM5())}, 1);
          }
          break;
	    }
	  }
  }

  /**
   * Aggiornamento dei campi del record W9FLUSSI, valorizzando i campi KEY01, KEY02 (ed altri campi)
   * per metterlo in relazione con la W9FASI.
   * 
   * @param xmlEvento
   *        XML dell'evento gestito
   * @param faseInvio
   *        fase di invio definita nell'XML
   * @param datiAggiornamento
   *        contenitore dati da aggiornare
   * @throws GestoreException GestoreException
   * @throws SQLException SQLException
   */
  private void updateW9Flussi(final String xmlEvento, final FaseType faseInvio,
      final DataColumnContainer datiAggiornamento) throws GestoreException, SQLException {

    Long idFlusso = datiAggiornamento.getLong("W9FLUSSI.IDFLUSSO");

    Long key01 = null;
    if ((faseInvio.getW3FASEESEC() == W3020Type.X_991
        || faseInvio.getW3FASEESEC() == W3020Type.X_992
        || faseInvio.getW3FASEESEC() == W3020Type.X_981
        || faseInvio.getW3FASEESEC() == W3020Type.X_982)
        && datiAggiornamento.isColumn("PIATRI.CONTRI")) {
      key01 = datiAggiornamento.getLong("PIATRI.CONTRI");
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_988
        && datiAggiornamento.isColumn("W9GARA.CODGARA")) {
      key01 = datiAggiornamento.getLong("W9GARA.CODGARA");
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_901
        && datiAggiornamento.isColumn("W9PUBBLICAZIONI.CODGARA")) {
      key01 = datiAggiornamento.getLong("W9PUBBLICAZIONI.CODGARA");
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_989
        && datiAggiornamento.isColumn("AVVISO.IDAVVISO")) {
      key01 = datiAggiornamento.getLong("AVVISO.IDAVVISO");
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_983
        && datiAggiornamento.isColumn("W9GARA_ENTINAZ.CODGARA")) {
      key01 = datiAggiornamento.getLong("W9GARA_ENTINAZ.CODGARA");
    } else {
      if (datiAggiornamento.isColumn("W9FASI.CODGARA")) {
        key01 = datiAggiornamento.getLong("W9FASI.CODGARA");
      }
    }

    Long key02 = null;
    if (faseInvio.getW3FASEESEC() == W3020Type.X_988
        || faseInvio.getW3FASEESEC() == W3020Type.X_983
        || faseInvio.getW3FASEESEC() == W3020Type.X_991
        || faseInvio.getW3FASEESEC() == W3020Type.X_992) {
      key02 = null;
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_988
        && datiAggiornamento.isColumn("W9LOTT.CODLOTT")) {
      key02 = datiAggiornamento.getLong("W9LOTT.CODLOTT");
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_989) {
      key02 = datiAggiornamento.getLong("AVVISO.CODSISTEMA");
    } else {
      if (datiAggiornamento.isColumn("W9FASI.CODLOTT")) {
        key02 = datiAggiornamento.getLong("W9FASI.CODLOTT");
      }
    }

    String noteInvio = null;
    if (faseInvio.isSetW9NOTINVIO()) {
      noteInvio = faseInvio.getW9NOTINVIO();
    }
    Timestamp dataAttuale = new Timestamp(new Date().getTime());
    this.sqlManager.update("update W9FLUSSI set KEY01=?, KEY02=?, NOTEINVIO=?, DATIMP=? where IDFLUSSO=?",
        new Object[]{key01, key02, noteInvio, dataAttuale, idFlusso});
  }

  /**
   * Inoltra la gestione dell'evento al gestore successivo nella catena.
   * 
   * @param xmlEvento
   *        messaggio XML da processare
   * @param datiAggiornamento
   *        container con i dati delle informazioni da aggiornare nel DB
   * @param ignoreWarnings
   *        true se i warnings vanno solamente tracciati e si procede inserendo
   *        i dati, false se i warning non vanno trascurati, e quindi oltre alla
   *        tracciatura devono bloccare l'inserimento dei dati
   * @throws GestoreException GestoreException
   * @throws SQLException SQLException
   */
  protected void forwardSendEvento(String xmlEvento,
      DataColumnContainer datiAggiornamento, final boolean ignoreWarnings)
  throws GestoreException, SQLException {
    if (nextHandler != null) {
      this.nextHandler.processEvento(xmlEvento, datiAggiornamento, ignoreWarnings);
    } else {
      throw new RuntimeException("Catena di gestori eventi interrotta");
    }
  }

  /**
   * Estrae FaseType a partire dallo stream XML del flusso.
   * 
   * @param xmlEvento xmlEvento
   * @return Ritorna FaseType a partire dallo stream XML del flusso
   */
  protected FaseType forwardGetFaseEvento(String xmlEvento){
    if (nextHandler != null) {
      return this.nextHandler.getFaseEvento(xmlEvento);
    } else {
      throw new RuntimeException("Catena di gestori eventi interrotta");
    }
  }

  /**
   * Ritorna il nome del flusso da utilizzare nella prima parte dei messaggi per
   * la tracciatura del log.
   * 
   * @return nome del flusso
   */
  protected abstract String getNomeFlusso();

  /**
   * Ritorna il tag principale del messaggio XML da gestire.
   * 
   * @return tag principale senza namespace
   */
  protected abstract String getMainTagRequest();

  /**
   * Ritorna il documento XMLBeans ottenuto deserializzando la richiesta XML.
   * 
   * @param xmlEvento
   *        stringa XML con l'evento da gestire
   * 
   * @return oggetto rappresentante il contenuto della stringa XML
   * 
   * @throws XmlException XmlException
   */
  protected abstract XmlObject getXMLDocument(String xmlEvento)
  throws XmlException;

  /**
   * Effettua il test del documento, verificando se ha l'attributo test="true"
   * e' presente nella prima riga della richiesta da gestire.
   * 
   * @param documento
   *        documento rappresentante la richiesta XML
   * @return true se l'attributo e' settato a true, false altrimenti
   */
  protected abstract boolean isTest(XmlObject documento);

  /**
   * Estrae l'elemento fase della richiesta.
   * 
   * @param documento
   *        documento rappresentante la richiesta XML
   * @return fase
   */
  protected abstract FaseEstesaType getFaseInvio(XmlObject documento);

  /**
   * Gestisce il caso di primo invio, effettuando i controlli, le
   * inizializzazioni e gli inserimenti previsti.<br>
   * Il metodo si deve preoccupare di aggiornare lo stato della comunicazione
   * (W9INBOX.STACOM) e l'eventuale messaggio di errore (W9INBOX.MSG)
   * 
   * @param documento
   *        documento rappresentante la richiesta XML
   * @param datiAggiornamento
   *        contenitore dei dati da utilizzare per l'aggiornamento
   * @param ignoreWarnings
   *        true se i warnings vanno solamente tracciati e si procede inserendo
   *        i dati, false se i warning non vanno trascurati, e quindi oltre alla
   *        tracciatura devono bloccare l'inserimento dei dati
   * @throws GestoreException GestoreException
   * @throws SQLException SQLException
   */
  protected abstract void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException;

  /**
   * Gestisce il caso di rettifica invio precedente, effettuando i controlli, le
   * inizializzazioni e gli inserimenti previsti.<br>
   * Il metodo si deve preoccupare di aggiornare lo stato della comunicazione
   * (W9INBOX.STACOM) e l'eventuale messaggio di errore (W9INBOX.MSG)
   * 
   * @param documento
   *        documento rappresentante la richiesta XML
   * @param datiAggiornamento
   *        contenitore dei dati da utilizzare per l'aggiornamento
   * @param ignoreWarnings
   *        true se i warnings vanno solamente tracciati e si procede inserendo
   *        i dati, false se i warning non vanno trascurati, e quindi oltre alla
   *        tracciatura devono bloccare l'inserimento dei dati
   * @throws GestoreException GestoreException
   * @throws SQLException SQLException
   */
  protected abstract void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException;

  /**
   * Setta il messaggio di errore e lo stato nei corrispondenti campi di
   * W9INBOX. Il messaggio viene settato solo se diverso da null, ed in tal caso
   * come prefisso viene indicato il nome del flusso.
   * 
   * @param datiAggiornamento
   *        contenitore da aggiornare
   * @param codiceEsito
   *        costante della classe StatoComunicazione indicante l'esito
   * @param msg
   *        messaggio
   * @throws GestoreException GestoreException
   */
  protected final void setEsito(final DataColumnContainer datiAggiornamento,
      final Long codiceEsito, final String msg) throws GestoreException {
    datiAggiornamento.getColumn("W9INBOX.STACOM").setObjectValue(codiceEsito);
    if (msg != null) {
      datiAggiornamento.getColumn("W9INBOX.MSG").setObjectValue(
          this.getNomeFlusso() + " - " + msg);
    }
  }

  /**
   * Determina se il campo W9INBOX.STACOM e' pari 2 (flusso importato con successo), false altrimenti.
   * 
   * @param datiAggiornamento DataColumnContainer
   * @return Ritorna true se il campo W9INBOX.STACOM e' pari 2 (flusso importato con successo), false altrimenti.
   * @throws GestoreException GestoreException
   */
  protected final boolean isEsito(final DataColumnContainer datiAggiornamento) throws GestoreException {
    boolean result = false;
    
    if (datiAggiornamento.isColumn("W9INBOX.STACOM")) {
      Long statoComunicazione = (Long) datiAggiornamento.getColumn("W9INBOX.STACOM").getValue().getValue();
      if (StatoComunicazione.STATO_IMPORTATA.getStato().equals(statoComunicazione)) {
        result = true;
      }
    }
    
    return result;
  }
  
  /**
   * Converte il flag S/N del tracciato XSD nell'equivalente per il salvataggio
   * su DB.
   * 
   * @param campoFlag
   *        valore del campo flag
   * 
   * @return "1" se l'input vale "S", "2" se vale "N"
   */
  protected final String getFlagSNDB(final Enum campoFlag) {
    if ("S".equals(campoFlag.toString())) {
      return "1";
    } else {
      return "2";
    }
  }

  /**
   * Estrae la chiave della stazione appaltante in UFFINT individuata dal codice
   * fiscale dell'elemento in input. Se non esiste alcuna occorrenza nella
   * tabella, effettua l'inserimento con i dati ricevuti e ritorna l'id
   * dell'elemento inserito,
   * 
   * @param arch1
   *        elemento dell'anagrafica ufficio intestatario da ricercare
   * @return id dell'elemento nel DB
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected final String getStazioneAppaltante(final Arch1Type arch1) throws SQLException,
  GestoreException {
    String pk = this.getCodiceUfficioIntestatario(arch1.getCFEIN());

    if (pk == null) {
      // se non esiste, si inserisce previa creazione della chiave
      pk = this.geneManager.calcolaCodificaAutomatica("UFFINT", "CODEIN");

      DataColumn codiceUfficio = new DataColumn("UFFINT.CODEIN",
          new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
      DataColumn cap = new DataColumn("UFFINT.CAPEIN", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, arch1.getCAPEIN()));
      DataColumn codiceFiscale = new DataColumn("UFFINT.CFEIN",
          new JdbcParametro(JdbcParametro.TIPO_TESTO, arch1.getCFEIN()));
      DataColumn email = new DataColumn("UFFINT.EMAIIN", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, arch1.getEMAIIN()));
      DataColumn fax = new DataColumn("UFFINT.FAXEIN", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, arch1.getFAXEIN()));
      DataColumn codiceIstat = new DataColumn("UFFINT.CODCIT",
          new JdbcParametro(JdbcParametro.TIPO_TESTO, arch1.getCODCIT()));
      DataColumn naturaGiuridica = new DataColumn("UFFINT.TIPOIN",
          new JdbcParametro(JdbcParametro.TIPO_NUMERICO, Long.parseLong(arch1.getTIPOIN().toString())));
      DataColumn civico = new DataColumn("UFFINT.NCIEIN", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, arch1.getNCIEIN()));
      DataColumn denominazione = new DataColumn("UFFINT.NOMEIN",
          new JdbcParametro(JdbcParametro.TIPO_TESTO, arch1.getNOMEIN()));
      DataColumn telefono = new DataColumn("UFFINT.TELEIN", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, arch1.getTELEIN()));
      DataColumn via = new DataColumn("UFFINT.VIAEIN", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, arch1.getVIAEIN()));

      DataColumnContainer dcc = new DataColumnContainer(new DataColumn[] {
          codiceUfficio, cap, codiceFiscale, email, fax, codiceIstat,
          naturaGiuridica, civico, denominazione, telefono, via });
      // i campi facoltativi del tracciato vanno settati separatamente previo controllo
      if (arch1.isSetGPROFCO()) {
        dcc.addColumn("UFFINT.PROFCO", JdbcParametro.TIPO_TESTO,
            arch1.getGPROFCO());
      }
      dcc.insert("UFFINT", this.sqlManager);
    }

    return pk;
  }

  /**
   * Estrae la chiave del tecnico in TECNI individuato dal codice fiscale
   * dell'elemento in input. Se non esiste alcuna occorrenza nella tabella,
   * effettua l'inserimento con i dati ricevuti e ritorna l'id dell'elemento
   * inserito.
   * 
   * @param arch2
   *        elemento dell'anagrafica tecnico da ricercare
   * @param pkUffint
   *        stazione appaltante di filtro nella ricerca
   *        @return Ritorna l'id del tecnico
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected final String getTecnico(final Arch2Type arch2, final String pkUffint)
  throws SQLException, GestoreException {
    String pk = (String) this.sqlManager.getObject(
        "select codtec from tecni where cgentei = ? and cftec = ?",
        new Object[] { pkUffint, arch2.getCFTEC1() });

    boolean eseguiInsert = false;

    if (pk == null) {
      // se non esiste, si inserisce previa creazione della chiave
      pk = this.geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
      eseguiInsert = true;
    }

    DataColumn codiceTecnico = new DataColumn("TECNI.CODTEC",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
    DataColumn uffint = new DataColumn("TECNI.CGENTEI", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, pkUffint));
    DataColumn codiceFiscale = new DataColumn("TECNI.CFTEC", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, arch2.getCFTEC1()));
    DataColumn codiceIstat = new DataColumn("TECNI.CITTEC", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, arch2.getGCITTECI()));
    DataColumn cognome = new DataColumn("TECNI.COGTEI", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, arch2.getCOGTEI()));
    DataColumn nome = new DataColumn("TECNI.NOMETEI", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, arch2.getNOMETEI()));
    DataColumn nometec = null;
    if (arch2.isSetNOMETEI()) {
      nometec = new DataColumn("TECNI.NOMTEC", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, arch2.getCOGTEI() + " " + arch2.getNOMETEI()));
    } else {
      nometec = new DataColumn("TECNI.NOMTEC", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, arch2.getCOGTEI()));
    }

    DataColumnContainer dcc = new DataColumnContainer(new DataColumn[] {
        codiceTecnico, uffint, codiceFiscale, codiceIstat, cognome, nome, nometec });
    // i campi facoltativi del tracciato vanno settati separatamente previo
    // controllo
    if (arch2.isSetINDTEC1()) {
      dcc.addColumn("TECNI.INDTEC", JdbcParametro.TIPO_TESTO, arch2.getINDTEC1());
    }
    if (arch2.isSetNCITEC1()) {
      dcc.addColumn("TECNI.NCITEC", JdbcParametro.TIPO_TESTO, arch2.getNCITEC1());
    }
    if (arch2.isSetLOCTEC1()) {
      dcc.addColumn("TECNI.LOCTEC", JdbcParametro.TIPO_TESTO, arch2.getLOCTEC1());
    }
    if (arch2.isSetCAPTEC1()) {
      dcc.addColumn("TECNI.CAPTEC", JdbcParametro.TIPO_TESTO, arch2.getCAPTEC1());
    }
    if (arch2.isSetTELTEC1()) {
      dcc.addColumn("TECNI.TELTEC", JdbcParametro.TIPO_TESTO, arch2.getTELTEC1());
    }
    if (arch2.isSetFAXTEC1()) {
      dcc.addColumn("TECNI.FAXTEC", JdbcParametro.TIPO_TESTO, arch2.getFAXTEC1());
    }
    if (arch2.isSetGEMATECI()) {
      dcc.addColumn("TECNI.EMATEC", JdbcParametro.TIPO_TESTO, arch2.getGEMATECI());
    }
    if (arch2.isSetPROTEC1()) {
      dcc.addColumn("TECNI.PROTEC", JdbcParametro.TIPO_TESTO, arch2.getPROTEC1());
    }

    if (eseguiInsert) {
      dcc.insert("TECNI", this.sqlManager);
    } else {
      codiceTecnico.setChiave(true);
      codiceTecnico.setOriginalValue(codiceTecnico.getValue());
      dcc.update("TECNI", this.sqlManager);
    }

    return pk;
  }

  /**
   * Estrae la chiave dell'impresa in IMPR individuata dal codice fiscale
   * dell'elemento in input. Se non esiste alcuna occorrenza nella tabella,
   * effettua l'inserimento con i dati ricevuti e ritorna l'id dell'elemento
   * inserito
   * 
   * @param arch3
   *        elemento dell'anagrafica impresa da ricercare
   * @param pkUffint
   *        stazione appaltante di filtro nella ricerca
   * 
   * @return id dell'elemento nel DB
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected String gestioneImpresa(Arch3Type arch3, String pkUffint)
  throws SQLException, GestoreException {
    
    boolean isSetCF = arch3.isSetCFIMP();
    
    String pk = null;
    boolean eseguiInsert = false;
    
    if (isSetCF) {
      pk = (String) this.sqlManager.getObject(
        "select CODIMP from IMPR where CGENIMP=? and CFIMP=? and NAZIMP=?",
        new Object[] { pkUffint, arch3.getCFIMP(),
            Long.parseLong(arch3.getGNAZIMP().toString()) });
    } else {
      pk = (String) this.sqlManager.getObject(
          "select CODIMP from IMPR where CGENIMP=? and NOMEST=? and NAZIMP=?",
          new Object[] { pkUffint, arch3.getNOMIMP(),
              Long.parseLong(arch3.getGNAZIMP().toString()) });
    }

    if (pk == null) {
      // se non esiste, si inserisce previa creazione della chiave
      pk = this.geneManager.calcolaCodificaAutomatica("IMPR", "CODIMP");
      eseguiInsert = true;
    }

    DataColumn codiceImpresa = new DataColumn("IMPR.CODIMP",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
    DataColumn uffint = new DataColumn("IMPR.CGENIMP", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, pkUffint));
    DataColumn nome = new DataColumn("IMPR.NOMEST", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, arch3.getNOMIMP()));
    DataColumn nomeImp = new DataColumn("IMPR.NOMIMP", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, StringUtils.substring(arch3.getNOMIMP(), 0, 60)));
    DataColumn nazione = new DataColumn("IMPR.NAZIMP", new JdbcParametro(
        JdbcParametro.TIPO_NUMERICO,
        Long.parseLong(arch3.getGNAZIMP().toString())));

    DataColumnContainer dcc = new DataColumnContainer(new DataColumn[] {
        codiceImpresa, uffint, nome, nomeImp, nazione });

    // i campi facoltativi del tracciato vanno settati separatamente previo controllo
    if (arch3.isSetCFIMP()) {
      dcc.addColumn("IMPR.CFIMP", JdbcParametro.TIPO_TESTO, arch3.getCFIMP());
    }
    if (arch3.isSetVINIMP()) {
      dcc.addColumn("IMPR.TIPIMP", JdbcParametro.TIPO_NUMERICO,
          Long.parseLong(arch3.getVINIMP().toString()));
    }
    if (arch3.isSetPIVIMP()) {
      dcc.addColumn("IMPR.PIVIMP", JdbcParametro.TIPO_TESTO, arch3.getPIVIMP());
    }
    if (arch3.isSetINDIMP()) {
      dcc.addColumn("IMPR.INDIMP", JdbcParametro.TIPO_TESTO, arch3.getINDIMP());
    }
    if (arch3.isSetNCIIMP()) {
      dcc.addColumn("IMPR.NCIIMP", JdbcParametro.TIPO_TESTO, arch3.getNCIIMP());
    }
    if (arch3.isSetPROIMP()) {
      dcc.addColumn("IMPR.PROIMP", JdbcParametro.TIPO_TESTO, arch3.getPROIMP());
    }
    if (arch3.isSetCAPIMP()) {
      dcc.addColumn("IMPR.CAPIMP", JdbcParametro.TIPO_TESTO, arch3.getCAPIMP());
    }
    if (arch3.isSetLOCIMP()) {
      dcc.addColumn("IMPR.LOCIMP", JdbcParametro.TIPO_TESTO, arch3.getLOCIMP());
    }
    if (arch3.isSetGCODCITI()) {
      dcc.addColumn("IMPR.CODCIT", JdbcParametro.TIPO_TESTO, arch3.getGCODCITI());
    }
    if (arch3.isSetTELIMP()) {
      dcc.addColumn("IMPR.TELIMP", JdbcParametro.TIPO_TESTO, arch3.getTELIMP());
    }
    if (arch3.isSetFAXIMP()) {
      dcc.addColumn("IMPR.FAXIMP", JdbcParametro.TIPO_TESTO, arch3.getFAXIMP());
    }
    if (arch3.isSetIMTECEL()) {
      dcc.addColumn("IMPR.TELCEL", JdbcParametro.TIPO_TESTO, arch3.getIMTECEL());
    }
    if (arch3.isSetGEMAIIP()) {
      dcc.addColumn("IMPR.EMAIIP", JdbcParametro.TIPO_TESTO, arch3.getGEMAIIP());
    }
    if (arch3.isSetGEMAI2IP()) {
      dcc.addColumn("IMPR.EMAI2IP", JdbcParametro.TIPO_TESTO, arch3.getGEMAI2IP());
    }
    if (arch3.isSetGINDWEBI()) {
      dcc.addColumn("IMPR.INDWEB", JdbcParametro.TIPO_TESTO, arch3.getGINDWEBI());
    }
    if (arch3.isSetNCCIAA()) {
      dcc.addColumn("IMPR.NCCIAA", JdbcParametro.TIPO_TESTO, arch3.getNCCIAA());
    }
    if (arch3.isSetGNATGIUI()) {
      dcc.addColumn("IMPR.NATGIUI", JdbcParametro.TIPO_NUMERICO, 
          Long.parseLong(arch3.getGNATGIUI().toString()));
    }

    if (eseguiInsert) {
      dcc.insert("IMPR", this.sqlManager);
    } else {
      codiceImpresa.setChiave(true);
      codiceImpresa.setOriginalValue(codiceImpresa.getValue());
      dcc.update("IMPR", this.sqlManager);
    }

    // Gestione del tecnico dell'impresa (TEIM)
    String[] chiaviTecnicoImpresa = null;
    Arch4Type[] arrayArch4 = arch3.getListaArch4Array();
    if (arrayArch4 != null && arrayArch4.length > 0) {
      HashMap< String, Arch4Type> hashArch4 = new HashMap< String, Arch4Type>();
      for (int yu = 0; yu < arrayArch4.length; yu++) {
        if (arrayArch4[yu].isSetCFTIM()) {
          if (!hashArch4.containsKey(arrayArch4[yu].getCFTIM())) {
            hashArch4.put(arrayArch4[yu].getCFTIM(), arrayArch4[yu]);
          }
        } else {
          if (!hashArch4.containsKey(arrayArch4[yu].getCOGTIM())) {
            hashArch4.put(arrayArch4[yu].getCOGTIM(), arrayArch4[yu]);
          }
        }
      }
      
      chiaviTecnicoImpresa = new String[hashArch4.size()];
      Iterator<Entry<String, Arch4Type>> iter = hashArch4.entrySet().iterator();
  
      int o = 0;
      while (iter.hasNext()) {
        chiaviTecnicoImpresa[o] = this.gestioneTecnicoImpresa(iter.next().getValue(), pkUffint);
        o++;
      }
    }

    // Gestione della tabella IMPLEG, cioe' la tabella associativa tra IMPR e TEIM
    // delete dell'associazione impresa e legali rappresentanti
    this.sqlManager.update("DELETE FROM IMPLEG WHERE CODIMP2=?", new Object[]{ pk });

    DataColumn idIMPLEG = new DataColumn("IMPLEG.ID", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
        this.genChiaviManager.getNextId("IMPLEG")));
    idIMPLEG.setChiave(true);
    DataColumn codiceImpresaIMPLEG = new DataColumn("IMPLEG.CODIMP2",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
    
    if (chiaviTecnicoImpresa != null) {
      for (int i = 0; i < chiaviTecnicoImpresa.length; i++) {
        Arch4Type arch4 = arch3.getListaArch4Array(i);
  
        DataColumn codiceLegRapp = new DataColumn("IMPLEG.CODLEG",
            new JdbcParametro(JdbcParametro.TIPO_TESTO, chiaviTecnicoImpresa[i]));
        String denominazioneTecnico = null;
        if (StringUtils.isNotEmpty(arch4.getCOGTIM())) {
          denominazioneTecnico = arch4.getCOGTIM();
        }
        if (StringUtils.isNotEmpty(arch4.getNOMETIM())) {
          if (StringUtils.isNotEmpty(denominazioneTecnico)) {
            denominazioneTecnico = denominazioneTecnico + " " + arch4.getCOGTIM();
          } else {
            denominazioneTecnico = arch4.getCOGTIM();
          }
        }
        
        DataColumn cognomeNomeTecnico = new DataColumn("IMPLEG.NOMLEG", 
            new JdbcParametro(JdbcParametro.TIPO_TESTO,
                StringUtils.substring(denominazioneTecnico, 0, 61)));
        DataColumnContainer dccImpLeg = new DataColumnContainer(new DataColumn[] { 
            idIMPLEG, codiceImpresaIMPLEG, codiceLegRapp, cognomeNomeTecnico});

        dccImpLeg.insert("IMPLEG", this.sqlManager);
      }
    }
    
    return pk;
  }

  /**
   * Estrae la chiave del tecnico impresa in TEIM individuata dal cognome e nome
   * del tecnico dell'elemento in input. Se non esiste alcuna occorrenza nella
   * tabella, effettua l'inserimento con i dati ricevuti e ritorna l'id
   * dell'elemento inserito
   * 
   * @param arch4
   *        elemento dell'anagrafica tecnico impresa da ricercare
   * @param pkUffint
   *        stazione appaltante di filtro nella ricerca
   * 
   * @return id dell'elemento nel DB
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected final String gestioneTecnicoImpresa(final Arch4Type arch4, final String pkUffint)
      throws SQLException, GestoreException {

    String pk = null;
    boolean eseguiInsert = false;
    
    if (arch4.isSetCFTIM()) {
      pk = (String) this.sqlManager.getObject(
          "select codtim from teim where cgentim = ? and UPPER(cftim) = ? ",
          new Object[] { pkUffint, arch4.getCFTIM().toUpperCase() });
    } else if (arch4.isSetCOGTIM() && arch4.isSetNOMETIM()) {
      pk = (String) this.sqlManager.getObject(
          "select codtim from teim where cgentim = ? and cogtim = ? and nometim = ? ",
          new Object[] { pkUffint, arch4.getCOGTIM(), arch4.getNOMETIM() });
    }

    if (pk == null) {
      // se non esiste, si inserisce previa creazione della chiave
      pk = this.geneManager.calcolaCodificaAutomatica("TEIM", "CODTIM");
      eseguiInsert = true;
    }

    DataColumn codiceTecnicoImpresa = new DataColumn("TEIM.CODTIM",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
    DataColumn uffint = new DataColumn("TEIM.CGENTIM", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, pkUffint));
    DataColumn cognome = new DataColumn("TEIM.COGTIM", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, arch4.getCOGTIM()));
    DataColumn nome = new DataColumn("TEIM.NOMETIM", new JdbcParametro(
        JdbcParametro.TIPO_TESTO, arch4.getNOMETIM()));
    String denominazioneTeim = null;
    if (StringUtils.isNotEmpty(arch4.getCOGTIM())) {
      denominazioneTeim = arch4.getCOGTIM();
    }
    if (StringUtils.isNotEmpty(arch4.getNOMETIM())) {
      if (StringUtils.isNotEmpty(denominazioneTeim)) {
        denominazioneTeim = denominazioneTeim + " " + arch4.getNOMETIM();
      } else {
        denominazioneTeim = arch4.getNOMETIM();
      }
    }
    
    DataColumn denominazione = new DataColumn("TEIM.NOMTIM",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, denominazioneTeim));

    DataColumn codiceFiscaleTecImp = new DataColumn("TEIM.CFTIM",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, 
            arch4.isSetCFTIM() ? arch4.getCFTIM().toUpperCase() : null));

    DataColumnContainer dcc = new DataColumnContainer(new DataColumn[] {
        codiceTecnicoImpresa, uffint, cognome, nome, denominazione, codiceFiscaleTecImp});

    if (eseguiInsert) {
      dcc.insert("TEIM", this.sqlManager);
    } else {
      codiceTecnicoImpresa.setChiave(true);
      codiceTecnicoImpresa.setOriginalValue(codiceTecnicoImpresa.getValue());
      dcc.update("TEIM", this.sqlManager);
    }

    return pk;
  }

  /**
   * Estrae la chiave dell'ufficio in UFFICI individuata dal codice
   * dell'elemento in input. Se non esiste alcuna occorrenza nella
   * tabella, effettua l'inserimento con i dati ricevuti e ritorna l'id
   * dell'elemento inserito
   * 
   * @param arch6
   *        elemento dell'anagrafica uffici da ricercare
   * @param pkUffint
   *        stazione appaltante di filtro nella ricerca
   * 
   * @return id dell'elemento nel DB
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected final Long getUfficio(final Arch6Type arch6, final String pkUffint)
  throws SQLException, GestoreException {
    Long pk = (Long) this.sqlManager.getObject(
        "select id from uffici where codein = ? and denom = ?",
        new Object[] { pkUffint, arch6.getW9UFFDENOM() });

    boolean eseguiInsert = false;

    if (pk == null) {
      // se non esiste, si inserisce previa creazione della chiave
      pk = new Long(
          this.genChiaviManager.getMaxId("UFFICI", "ID") + 1);
      eseguiInsert = true;
    }

    DataColumn id = new DataColumn("UFFICI.ID",
        new JdbcParametro(JdbcParametro.TIPO_NUMERICO, pk));
    DataColumn uffint = new DataColumn("UFFICI.CODEIN",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, pkUffint));
    DataColumn denominazione = new DataColumn("UFFICI.DENOM",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, arch6.getW9UFFDENOM()));
    
    DataColumnContainer duff = new DataColumnContainer(new DataColumn[] {
        id, uffint, denominazione });

    if (eseguiInsert) {
    	duff.insert("UFFICI", this.sqlManager);
    } else {
    	id.setChiave(true);
    	id.setOriginalValue(id.getValue());

    	duff.update("UFFICI", this.sqlManager);
    }
    return pk;
  }

  /**
   * Estrae la chiave del centro di costo in CENTRICOSTO individuata dal codice
   * del centro dell'elemento in input. Se non esiste alcuna occorrenza nella
   * tabella, effettua l'inserimento con i dati ricevuti e ritorna l'id
   * dell'elemento inserito
   * 
   * @param arch5
   *        elemento dell'anagrafica centro di costo da ricercare
   * @param pkUffint
   *        stazione appaltante di filtro nella ricerca
   * 
   * @return id dell'elemento nel DB
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected final Long getCentroCosto(final Arch5Type arch5, final String pkUffint)
  throws SQLException, GestoreException {
    Long pk = (Long) this.sqlManager.getObject(
        "select idcentro from centricosto where codein = ? and codcentro = ?",
        new Object[] { pkUffint, arch5.getW9CCCODICE() });

    boolean eseguiInsert = false;

    if (pk == null) {
      // se non esiste, si inserisce previa creazione della chiave
      pk = new Long(
          this.genChiaviManager.getMaxId("CENTRICOSTO", "IDCENTRO") + 1);
      eseguiInsert = true;
    }

    DataColumn idCentro = new DataColumn("CENTRICOSTO.IDCENTRO",
        new JdbcParametro(JdbcParametro.TIPO_NUMERICO, pk));
    DataColumn uffint = new DataColumn("CENTRICOSTO.CODEIN",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, pkUffint));
    DataColumn codice = new DataColumn("CENTRICOSTO.CODCENTRO",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, arch5.getW9CCCODICE()));
    DataColumn denominazione = new DataColumn("CENTRICOSTO.DENOMCENTRO",
        new JdbcParametro(JdbcParametro.TIPO_TESTO, arch5.getW9CCDENOM()));
    DataColumn tipologia = null;
    if (arch5.isSetW9CCTIPO()) {
      tipologia = new DataColumn("CENTRICOSTO.TIPOLOGIA",
          new JdbcParametro(JdbcParametro.TIPO_NUMERICO, Long.parseLong(arch5.getW9CCTIPO().toString())));
    } else {
      tipologia = new DataColumn("CENTRICOSTO.TIPOLOGIA",
          new JdbcParametro(JdbcParametro.TIPO_NUMERICO, null));
    }
    
    DataColumnContainer dcc = new DataColumnContainer(new DataColumn[] {
        idCentro, uffint, codice, denominazione, tipologia });

    if (eseguiInsert) {
      dcc.insert("CENTRICOSTO", this.sqlManager);
    } else {
      idCentro.setChiave(true);
      idCentro.setOriginalValue(idCentro.getValue());

      dcc.update("CENTRICOSTO", this.sqlManager);
    }
    return pk;
  }
  
  /**
   * Estrae codice gara e codice lotto da W9LOTT a partire dal codice CIG.
   * 
   * @param codiceCig Codice CIG del lotto
   * @return Ritorna un oggetto Long[] di dimensione 2, con primo
   *         elemento codGara e secondo elemento codLott
   * 
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected final Long[] getCodGaraCodLottByCIG(final String codiceCig) throws SQLException, GestoreException {
    Long[] result = new Long[2];

    Vector< ? > datiW9LOTT = this.sqlManager.getVector(
        "select CODGARA, CODLOTT from W9LOTT where CIG=? ", new Object[]{codiceCig});

    Long codGara = SqlManager.getValueFromVectorParam(datiW9LOTT, 0).longValue();
    Long codLott = SqlManager.getValueFromVectorParam(datiW9LOTT, 1).longValue();

    if (codGara != null) {
      result[0] = codGara;
    }
    if (codLott != null) {
      result[1] = codLott;
    }
    return result;
  }

  /**
   * Ritorna il codice dell'ufficio intestatario a partire del CF dell'ufficio stesso.
   * 
   * @param cfUffInt CF dell'ufficio intestatario
   * @return Ritorna il codice dell'ufficio intestatario a partire del CF
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected final String getCodiceUfficioIntestatario(final String cfUffInt)
  throws SQLException, GestoreException {
    return (String) this.sqlManager.getObject(
        "select CODEIN from UFFINT where CFEIN = ?", new Object[]{cfUffInt});
  }

  /**
   * Verifica se esiste in banca dati un appalto per il lotto indicato a partire
   * dal cig fornito nel tracciato e dal progressivo di aggiudicazione.
   * 
   * @param fase
   *        elemento fase del flusso
   * @return true se l'elemento esiste, false altrimenti
   * @throws SQLException SQLException
   */
  protected final boolean existsAppalto(final FaseEstesaType fase) throws SQLException {
    Long numeroOccorrenze = (Long) this.sqlManager.getObject(
        "select count(w9appa.codgara) from w9appa, w9lott "
        + "where w9appa.codgara = w9lott.codgara and w9appa.codlott = w9lott.codlott "
        + "and w9appa.num_appa = ? and w9lott.cig = ?",
        new Object[] { new Long(fase.getW9NUMAPPA()) ,fase.getW3FACIG() });
    return numeroOccorrenze.intValue() > 0;
  }

  /**
   * Verifica se esiste in banca dati il lotto a partire dal cig fornito nel tracciato.
   * 
   * @param fase
   *        elemento fase del flusso
   * @return true se l'elemento esiste, false altrimenti
   * @throws SQLException SQLException
   */
  protected final boolean existsLotto(final FaseEstesaType fase) throws SQLException {
    return UtilitySITAT.existsLotto(fase.getW3FACIG(), sqlManager);
  }

  /**
   * Verifica se esiste in banca dati la stessa fase di inizio
   * contratto a partire dal cig fornito nel tracciato.
   * 
   * @param fase
   *        elemento fase del flusso
   * @return true se l'elemento esiste, false altrimenti
   * @return
   * @throws SQLException
   */
  protected boolean existsIniz(FaseEstesaType fase) throws SQLException {
    Long numeroOccorrenze = (Long) this.sqlManager.getObject(
        "select count(w9iniz.codgara) from w9iniz, w9lott "
        + "where w9iniz.codgara = w9lott.codgara and w9iniz.codlott = w9lott.codlott and "
        + "w9iniz.num_iniz = ? and w9lott.cig = ?",
        new Object[] { new Long(fase.getW3NUM5()), fase.getW3FACIG() });
    return numeroOccorrenze.intValue() == 1;
  }
  
  /**
   * Ritorna la descrizione del tipo di invio (in forma di testo) a partire dal tipo di invio.
   * 
   * @param tipoInvio Tipo di invio
   * @return Ritorna la descrizione del tipo di invio (in forma di testo) a partire dal tipo di invio
   */
  public final String getDescrioneTipoInvio(final int tipoInvio) {
    String result = null;
    switch (tipoInvio) {
      case PRIMO_INVIO:
        result = TEXT_PRIMO_INVIO;
        break;
      case RETTIFICA:
        result = TEXT_RETTIFICA;
        break;
      default:
        break;
    }

    return result;
  }

  /**
   * Aggiornamento dello stato di esportazione del lotto (W9LOTT.DAEXPORT) dopo l'importazione
   * di un flusso in ORT, sia per primo invio, per rettifica che per integrazione, solo se 
   * l'importo totale del lotto e' maggiore o uguale a 40000 euro (isS2).
   * 
   * @param codGara Codice gara
   * @param codLott Codcie lotto
   * @param statoExport Stato dell'export
   * @throws SQLException SQLException
   */
  protected void aggiornaStatoExportLotto(final Long codGara, final Long codLott) throws SQLException {
    String statoExport = AbstractRequestHandler.LOTTO_ESPORTATO;
    if (UtilitySITAT.isS2(codGara, codLott, this.sqlManager)) {
      statoExport = AbstractRequestHandler.LOTTO_DA_ESPORTARE;
    }
    this.sqlManager.update("update W9LOTT set DAEXPORT=? where CODGARA=? and CODLOTT=?",
        new Object[]{statoExport, codGara, codLott }, 1);
  }

  /**
   * Estrae l'elemento fase della richiesta.
   * 
   * @param xmlEvento XmlEvento
   * @return Ritorna l'elemento fase della richiesta
   */
  public FaseType getFaseEvento(final String xmlEvento) {

    FaseType fase = null;

    if (xmlEvento != null && xmlEvento.indexOf(this.getMainTagRequest()) > 0) {
      // se e' presente il tag principale, allora si puo' provare a processare la richiesta

      logger.debug("getFaseEvento: inizio della gestione della richiesta: " + xmlEvento);

      try {
        XmlObject documento = this.getXMLDocument(xmlEvento);
        fase = this.getFaseInvio(documento);

      } catch (XmlException e) {
        logger.error("Errore inaspettato in fase di interpretazione del messaggio XML ricevuto", e);
        // se il messaggio per qualche motivo inspiegabile non viene
        // deserializzato da XMLBeans
      }

      logger.debug("getFaseEvento: fine della gestione della richiesta: " + xmlEvento);

    } else {
      // l'evento non e' di propria competenza, si passa la richiesta al successore
      fase = this.forwardGetFaseEvento(xmlEvento);
    }

    return fase;
  }

  /**
   * Verifica se esiste in banca dati il legame tra imprese e legale rappresentante.
   * 
   * @param impresa rapp chiavi delle entita'
   * @param rapp Legale rappresentante
   * @return true se l'elemento esiste, false altrimenti
   * @throws SQLException SQLException
   */
  protected boolean existsLegame(final String impresa, final String rapp) throws SQLException {
    Long numeroOccorrenze = (Long) this.sqlManager.getObject(
        "select count(codimp2) from impleg "
        + "where codimp2 = ? and codleg = ? ",
        new Object[] { impresa, rapp });
    return numeroOccorrenze.intValue() > 0;
  }

  /**
   * Verifica se esiste in banca dati la fase passata come argomento per l'appalto specificato nella fase
   * cercando nella tabella W9FASI in join con la W9LOTT.
   * 
   * @param fase FaseEstesaType
   * @param faseEsecuzione Fase esecuzione
   * @return Ritorna true se la fase esiste, false altrimenti
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected boolean existsFase(final FaseEstesaType fase, final int faseEsecuzione)
      throws SQLException, GestoreException {
  	
  	boolean result = false;
  	
  	switch (faseEsecuzione) {
		case CostantiW9.A03:
		case CostantiW9.A24:
		case CostantiW9.A22:
		case CostantiW9.A04:
		case CostantiW9.A05:
		case CostantiW9.A21:
		case CostantiW9.A06:
		case CostantiW9.A07:
		case CostantiW9.A20:
		case CostantiW9.A09:
		case CostantiW9.A10:
			result = UtilitySITAT.existsFase(fase.getW9NUMAPPA(), fase.getW3FACIG(), faseEsecuzione, this.sqlManager);
			break;
			
		case CostantiW9.A08:
		case CostantiW9.A25:
		case CostantiW9.A12:
		case CostantiW9.A13:
		case CostantiW9.A14:
		case CostantiW9.A15:
		case CostantiW9.A16:
		case CostantiW9.B02:
		case CostantiW9.B03:
		case CostantiW9.B06:
		case CostantiW9.B07:
		case CostantiW9.B08:
			return UtilitySITAT.existsFase(fase.getW9NUMAPPA(), fase.getW3FACIG(), faseEsecuzione, fase.getW3NUM5(), this.sqlManager);
		default:
			break;
		}
  	
    return result;
  }
  
  /**
   * Verifica se esiste in banca dati la stazione appaltante attraverso il codice
   * fiscale della stazione appaltante stessa presente nell'oggetto FaseType.
   * 
   * @param fase Fase esecuzione
   * @return Ritorna true se esiste, false altrimenti
   * @throws SQLException SQLException
   */
  private boolean existsStazioneAppaltante(final FaseType fase) throws SQLException {
    Long numeroOccorrenze = (Long) this.sqlManager.getObject(
        "select count(codein) from uffint where cfein = ?",
        new Object[] { fase.getW9FLCFSA() });
    return numeroOccorrenze.intValue() > 0;
  }

  /**
   * Aggiornamento della situazione lotti/gara in funzione della fase di esecuzione.
   * 
   * @param faseInvio FaseInvio
   * @param datiAggiornamento DataColumnContainer
   * @throws SQLException SQLException 
   * @throws GestoreException GestoreException
   */
  private void aggiornaSituazioneGaraLotto(FaseEstesaType faseInvio, DataColumnContainer datiAggiornamento)
      throws SQLException, GestoreException {
    Long codgara = null;
    Long codlott = null;
    String codiceCIG = null;

    codiceCIG = ((FaseEstesaType) faseInvio).getW3FACIG();
    HashMap<String, Long> hm = UtilitySITAT.getCodGaraCodLottByCIG(codiceCIG, this.sqlManager);
    codgara = hm.get("CODGARA");
    codlott = hm.get("CODLOTT");
     
    this.w9Manager.updateSituazioneGaraLotto(codgara, codlott);
  }
  
  protected boolean isFaseAbilitata(final FaseEstesaType fase, final int faseEsecuzione)
  		throws SQLException, GestoreException {
    Long[] hm = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    
    return UtilitySITAT.isFaseAbilitata(hm[0], hm[1], new Long(fase.getW9NUMAPPA()), faseEsecuzione, this.sqlManager);
  }
  
  protected boolean isFaseVisualizzabile(final FaseEstesaType fase, final int faseEsecuzione)
  		throws SQLException, GestoreException {
    Long[] hm = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    
    return UtilitySITAT.isFaseVisualizzabile(hm[0], hm[1], faseEsecuzione, this.sqlManager);
  }
  
}