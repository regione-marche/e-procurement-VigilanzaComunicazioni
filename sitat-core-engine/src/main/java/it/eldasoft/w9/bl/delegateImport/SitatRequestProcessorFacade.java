package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAnagraficaSemplificataeBandoDocument;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument;
import it.toscana.rete.rfc.sitat.types.W3020Type;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.axis.message.SOAPBody;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.openspcoop.pdd.services.SPCoopMessage;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionStatus;

/**
 * Facade per la gestione della business logic del servizio Sitat erogato.<br>
 * La logica di business del processing delle richieste ricevute dal proxy viene
 * spostata in questa classe ed in tutte le classi delegate richiamate, in modo
 * da mantenere il modulo di lettura messaggi pulito.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class SitatRequestProcessorFacade {

  Logger                         logger = Logger.getLogger(SitatRequestProcessorFacade.class);

  private AbstractRequestHandler requestHandler;

  private SqlManager             sqlManager;

  private GenChiaviManager       genChiaviManager;

  /**
   * @param requestHandler
   *        requestHandler da settare internamente alla classe.
   */
  public void setRequestHandler(AbstractRequestHandler requestHandler) {
    this.requestHandler = requestHandler;
  }

  /**
   * @param sqlManager
   *        sqlManager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   * @param genChiaviManager
   *        genChiaviManager da settare internamente alla classe.
   */
  public void setGenChiaviManager(GenChiaviManager genChiaviManager) {
    this.genChiaviManager = genChiaviManager;
  }

  /**
   * Inizializza la transazione, estrae il messaggio dalla busta eGov, inserisce
   * un'occorrenza nella W9INBOX, e demanda la gestione della richiesta alla
   * catena, nonche' chiude la transazione a seconda dell'esito dell'operazione
   * ritornata dalla catena. L'eventuale generazione di eccezioni comporta una
   * interruzione anomala, gestita mediante segnalazione sul log, e la non
   * cancellazione del messaggio in modo da poterlo riprocessare nel seguito una
   * volta corretta l'anomalia applicativa.
   * 
   * @param message
   * 
   * @return true se la richiesta e' stata gestita e quindi va eliminata, false
   *         altrimenti
   */
  public boolean processEvento(SPCoopMessage message) {
    boolean successProcess = false;
    // estrazione dell'XML della busta di eGov
    byte[] soapMessage = message.getMessage();

    // estrazione della richiesta inviata dalla stazione appaltante, contenuta
    // nel body della busta SOAP
    String xmlEvento = null;
    try {
      SOAPEnvelope envelope = new SOAPEnvelope(new ByteArrayInputStream(
          soapMessage));
      SOAPBody body = (SOAPBody) envelope.getBody();
      java.util.Iterator< ? > it = body.getChildElements();
      if (it.hasNext()) {
        org.apache.axis.message.MessageElement bodyElement = (org.apache.axis.message.MessageElement) it.next();
        xmlEvento = bodyElement.getAsString();
      }
    } catch (Exception e) {
      // alquanto improbabile, dato che i messaggi che arrivano all'Osservatorio
      // sono controllati e inoltrati dal proxy applicativo
      logger.error("La richiesta non corrisponde ad una busta SOAP", e);
    }

    // L'operazione si compone di tre passi:
    // 1. salvataggio del messaggio nella tabella W9INBOX e alcuni campi della W9FLUSSI;
    // 2. salvataggio della fase (W9FASI) e i dati in esso contenuti (tabelle specifiche)
    // 3. aggiornamento dei record inseriti al punto 1 per aggiornare i campi STATO e MSG della W9INBOX e
    //    KEY01, KEY02, NOTEINVIO e CODOGG di W9FLUSSI (per legare il record di W9FLUSSI con la W9FASI)

    TransactionStatus status = null;
    int idW9inbox = 0;
    int passo = 1;
    try {
      // si avvia la transazione per l'aggiornamento del DB
      status = this.sqlManager.startTransaction();

      // si genera l'id per la W9INBOX
      idW9inbox = this.genChiaviManager.getNextId("W9INBOX");

      // si creano tutte le colonne di W9INBOX
      DataColumn chiaveW9inbox = new DataColumn("W9INBOX.IDCOMUN",
          new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(idW9inbox)));

      DataColumn statoComunicazione = new DataColumn("W9INBOX.STACOM",
          new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
              StatoComunicazione.STATO_RICEVUTA.getStato()));

      Timestamp dataAttuale = new Timestamp(new Date().getTime());
      DataColumn dataRicezione = new DataColumn("W9INBOX.DATRIC",
          new JdbcParametro(JdbcParametro.TIPO_DATA, dataAttuale));

      DataColumn xml = new DataColumn("W9INBOX.XML", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, new String(soapMessage)));

      DataColumn msg = new DataColumn("W9INBOX.MSG", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, null));

      DataColumn idEgov = new DataColumn("W9INBOX.IDEGOV", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, message.getSpcoopHeaderInfo().getID()));

      // si popola il contenitore e si effettua la insert in W9INBOX
      DataColumnContainer datiAggiornamento = new DataColumnContainer(
          new DataColumn[] { chiaveW9inbox, statoComunicazione, dataRicezione,
              xml, msg, idEgov });
      datiAggiornamento.insert("W9INBOX", this.sqlManager);

      // effettuato l'inserimento, si aggiornano gli original value con i value,
      // in modo da consentire successivi update del record; inoltre si imposta
      // la chiave in modo da consentire l'update per chiave, altrimenti
      // impossibile
      chiaveW9inbox.setChiave(true);
      chiaveW9inbox.setObjectOriginalValue(chiaveW9inbox.getValue());
      statoComunicazione.setObjectOriginalValue(statoComunicazione.getValue());
      dataRicezione.setObjectOriginalValue(dataRicezione.getValue());
      xml.setObjectOriginalValue(xml.getValue());
      msg.setObjectValue(msg.getValue());
      idEgov.setObjectOriginalValue(idEgov.getValue());

      // Creazione del record W9FLUSSI (sempre necessario in quanto lista e scheda della tabella
      // W9INBOX e' in relazione uno ad uno con la W9FLUSSI).
      FaseType faseInvio = this.getFase(message);
      try
      {
    	  this.insertW9FLUSSI(xmlEvento, faseInvio, datiAggiornamento);
      } catch (Exception ex) {
    	  //viene intercettata una strana eccezione che provoca una NullException in insertW9FLUSSI
    	  logger.error("Errore nel salvataggio del flusso. Richiesta XML non riconosciuta", ex);

          // Aggiornamento dello stato della comunicazione e del relativo messaggio
          statoComunicazione.setValue(new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
              StatoComunicazione.STATO_ERRATA.getStato()));
          msg.setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
          "Richiesta XML non riconosciuta"));

      }
      
      // OSS.: il messaggio viene salvato in W9FLUSSI anche se e' un messaggio di test
      // (che e' un caso poco probabile). Dopo nel metodo AbstractRequestHandler.processEvento,
      // se il messaggio e' di test, il record viene cancellato.

      // Creazione del SavePoint per poter fare il rollback delle operazione successive
      // in caso di errori nell'import del flusso nella catena degli handler
      Object savePointPostInsertW9INBOX = status.createSavepoint();

      try {
        // Inizio del secondo passo
        passo++;
        // si effettua il process della richiesta (i warning in questo caso sono bloccanti)
        this.requestHandler.processEvento(xmlEvento, datiAggiornamento, false);

        if (StatoComunicazione.STATO_MESSAGGIO_TEST.getStato().equals(
            datiAggiornamento.getLong("W9INBOX.STACOM"))) {
          // Nel caso di messaggio di test si cancella il record inserito dal metodo
          // SitatRequestProcessorFacade.insertW9FLUSSI(...), dove capire se il messaggio
          // e' di test o meno comporta interrogare tutta la catena (e avrebbe fatto
          // perdere troppo tempo)
          this.sqlManager.update("delete from W9FLUSSI where idflusso=?",
              new Object[]{datiAggiornamento.getLong("W9FLUSSI.IDFLUSSO")});
        }
      } catch (Exception ex) {
        logger.error("Errore nel salvataggio del flusso e dei dati in esso contenuti nella catena degli handler", ex);

        // Aggiornamento dello stato della comunicazione e del relativo messaggio
        statoComunicazione.setValue(new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
            StatoComunicazione.STATO_ERRATA.getStato()));
        msg.setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
        "Errore nel salvataggio del flusso e dei dati in esso contenuti. Vedere il log per ulteriori dettagli"));

        // In caso di errore nel salvataggio del flusso si fa la rollback fino al savepoint
        // precedentemente creato in modo da non cancellare il record nella W9INOBX ed
        // aggiornarne lo stato e il messaggio descrittivo dell'errore
        status.rollbackToSavepoint(savePointPostInsertW9INBOX);
      }

      // Inizio del terzo passo
      passo++;

      // si aggiorna quindi lo stato della comunicazione e l'eventuale messaggio
      // di errore, modificati dalla catena o dal catch
      datiAggiornamento.update("W9INBOX", this.sqlManager);

      successProcess = true;

    } catch (DataAccessException e) {
      if (idW9inbox == 0) {
        logger.error("Errore inaspettato durante la generazione dell'id della chiave per la tabella W9INBOX", e);
      } else {
        logger.error("Errore inaspettato durante l'interazione con la base dati", e);
      }
    } catch (SQLException e) {
      if (status == null) {
        logger.error("Impossibile aprire la transazione per salvare i dati letti nel messaggio", e);
      } else {
        logger.error("Si e' verificato un errore inaspettato durante l'interazione con la base dati", e);
      }
    } catch (Throwable e) {
      // per sicurezza si blinda il codice in modo da avere ogni eccezione
      // catchata e tracciata nel log
      logger.error("Errore inaspettato durante l'elaborazione dell'operazione", e);
    } finally {
      if (status != null) {
        try {
          // Se tutto e' andato a buon fine (successProcess==true), oppure si e' verificato un errore
          // (non gestito) al secondo o al terzo passo dell'intera operazione, allora si da il commit della transazione.
          // (Questo caso e' abbastanza remoto e permette di salvare un messaggio nella W9INOBX senza aggiornarne
          // lo stato ('messaggio ricevuto'))
          if (successProcess || (passo <= 2 && !successProcess)) {
            this.sqlManager.commitTransaction(status);
          } else {
            this.sqlManager.rollbackTransaction(status);
          }
        } catch (SQLException e) {
        }
      }
    }

    return successProcess;
  }

  /**
   * Estrae l'elemento fase della richiesta.
   * @param message SPCoopMesage
   * @return Ritorna l'elemento fase della richiesta
   */
  public FaseType getFase(SPCoopMessage message) {

    // estrazione dell'XML della busta di eGov
    byte[] soapMessage = message.getMessage();

    // estrazione della richiesta inviata dalla stazione appaltante, contenuta
    // nel body della busta SOAP
    String xmlEvento = null;
    try {
      SOAPEnvelope envelope = new SOAPEnvelope(new ByteArrayInputStream(
          soapMessage));
      SOAPBody body = (SOAPBody) envelope.getBody();
      java.util.Iterator< ? > it = body.getChildElements();
      if (it.hasNext()) {
        org.apache.axis.message.MessageElement bodyElement = (org.apache.axis.message.MessageElement) it.next();
        xmlEvento = bodyElement.getAsString();
      }
    } catch (Exception e) {
      // alquanto improbabile, dato che i messaggi che arrivano all'Osservatorio
      // sono controllati e inoltrati dal proxy applicativo
      logger.error("La richiesta non corrisponde ad una busta SOAP", e);
    }

    // si effettua il process della richiesta (i warning in questo caso sono
    // bloccanti)

    return this.requestHandler.getFaseEvento(xmlEvento);
  }

  /**
   * Inserimento del record in W9FLUSSI con i dati.
   * 
   * Oss.: il record in W9FLUSSI deve sempre essere creato per memorizzare i dati
   * quali CF della stazione appaltante,
   * 
   * 
   * @param xmlEvento stringa con l'xml
   * @param faseInvio fase di invio
   * @param datiAggiornamento DataColumnContainer
   * @throws GestoreException GestoreException
   * @throws SQLException SQLException
   */
  private void insertW9FLUSSI(final String xmlEvento, final FaseType faseInvio,
      final DataColumnContainer datiAggiornamento) throws GestoreException, SQLException, NullPointerException {

    Long idFlusso = new Long(this.genChiaviManager.getNextId("W9FLUSSI"));
    Long idFlussiEliminati = new Long(this.genChiaviManager.getMaxId("W9FLUSSI_ELIMINATI", "IDFLUSSO") + 1);
    idFlusso = Math.max(idFlusso, idFlussiEliminati);
    String codogg = null;
    Long key04 = null;
    
    Long area = new Long(UtilitySITAT.getAreaFlusso(faseInvio.getW3FASEESEC().toString()));
    if (faseInvio.getW3FASEESEC() == W3020Type.X_988 || faseInvio.getW3FASEESEC() == W3020Type.X_13) {
      area = new Long(CostantiW9.AREA_ANAGRAFICA_GARE);
      if (faseInvio.getW3FASEESEC() == W3020Type.X_988) {
        try {
          codogg = (RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument.Factory.parse(
              xmlEvento)).getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab4().getW3IDGARA();
        } catch (XmlException e) {
          logger.error("Errore nel parsing del XML del messaggio (" + W3020Type.X_988 + ")", e);
        }
      } else {
        try {
          codogg = (RichiestaRichiestaRispostaSincronaAnagraficaSemplificataeBandoDocument.Factory.parse(
              xmlEvento)).getRichiestaRichiestaRispostaSincronaAnagraficaSemplificataeBando().getAnagraficaGara().getW3IDGARA();
        } catch (XmlException e) {
          logger.error("Errore nel parsing del XML del messaggio (" + W3020Type.X_13 + ")", e);
        }
      }
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_901) {
      area = new Long(CostantiW9.AREA_ANAGRAFICA_GARE);
      try {
        codogg = (RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument.Factory.parse(
            xmlEvento)).getRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti().getPubblicazione().getW3IDGARA();
        key04 = new Long((RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument.Factory.parse(
            xmlEvento)).getRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti().getPubblicazione().getW9PBNUMPUBB());
      } catch (XmlException e) {
        logger.error("Errore nel parsing del XML del messaggio (" + W3020Type.X_901 + ")", e);
      }
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_989) {
      area = new Long(CostantiW9.AREA_AVVISI);
      try {
        codogg = "" + (RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument.Factory.parse(
            xmlEvento)).getRichiestaRichiestaRispostaSincronaPubblicazioneAvviso().getTab7().getW3PAVVISOID();
      } catch (XmlException e) {
        logger.error("Errore nel parsing del XML del messaggio (" + W3020Type.X_989 + ")", e);
      }
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_991
        || faseInvio.getW3FASEESEC() == W3020Type.X_992) {
      area = new Long(CostantiW9.AREA_PROGRAMMA_TRIENNALI_ANNUALI);
      if (faseInvio.getW3FASEESEC() == W3020Type.X_992) {
        try {
          codogg = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument.Factory.parse(
              xmlEvento)).getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori().getTab1().getT2IDCONTRI();
        } catch (XmlException e) {
          logger.error("Errore nel parsing del XML del messaggio (" + W3020Type.X_992 + ")", e);
        }
      } else {
        try {
          codogg = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument.Factory.parse(
              xmlEvento)).getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServizi().getTab1().getT2IDCONTRI();
        } catch (XmlException e) {
          logger.error("Errore nel parsing del XML del messaggio (" + W3020Type.X_991 + ")", e);
        }
      }
    } else if (faseInvio.getW3FASEESEC() == W3020Type.X_983) {
      area = new Long(CostantiW9.AREA_GARE_ENTINAZIONALI);
      try {
        codogg =  (RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document.Factory.parse(
            xmlEvento)).getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().getTab31().getW9GNIDGARA();
      } catch (XmlException e) {
        logger.error("Errore nel parsing del XML del messaggio (" + W3020Type.X_983 + ")", e);
      }
    } else {
      if (faseInvio instanceof FaseEstesaType) {
        codogg = ((FaseEstesaType) faseInvio).getW3FACIG();
      }
    }

    Long key03 = null;
    key03 = Long.parseLong(faseInvio.getW3FASEESEC().toString());
    
    if (faseInvio instanceof FaseEstesaType) {
      if (((FaseEstesaType) faseInvio).getW3NUM5() > 0) {
        key04 = new Long(((FaseEstesaType) faseInvio).getW3NUM5());
      }
    }

    Long tipoInvio = new Long(faseInvio.getW3PGTINVIO2().toString());
    Timestamp dataInvio = datiAggiornamento.getData("W9INBOX.DATRIC");
    Long idComunicazione = datiAggiornamento.getLong("W9INBOX.IDCOMUN");
    String codFiscSA = faseInvio.getW9FLCFSA();

    String autoreInvio = faseInvio.getW9FLAUTORE();
    String versioneTracciatoXML = faseInvio.getW9FLVERXML();
    this.sqlManager.update(
        "insert into W9FLUSSI (IDFLUSSO, AREA, KEY03, KEY04, TINVIO2, DATINV, XML, IDCOMUN, CFSA, CODOGG, AUTORE, VERXML) values (?,?,?,?,?,?,?,?,?,?,?,?)",
        new Object[]{idFlusso, area, key03, key04, tipoInvio, dataInvio, xmlEvento, idComunicazione, codFiscSA, codogg, autoreInvio, versioneTracciatoXML});

    // aggiungo nel dataColumnContainer il campo chiave della tabella W9FLUSSI, perche'
    // necessario nell'aggiornamento del record appena inserito dopo aver cerato il
    // record in W9FASI e i dati specifici del flusso
    datiAggiornamento.addColumn("W9FLUSSI.IDFLUSSO", JdbcParametro.TIPO_NUMERICO, idFlusso);
  }
}