package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.PubblicazioneType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiType;
import it.toscana.rete.rfc.sitat.types.Tab41Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della pubblicazione dei documenti di gara/lotti.
 * 
 * @author Luca.Giacomazzo - Maggioli S.p.A.
 *
 */
public class PubblicazioneDocumentiHandler extends AbstractRequestHandler {

  //TODO Classe da rimuovere
  
  @Override
  protected String getNomeFlusso() {
    return "PubblicazioneDocumenti";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_PUBBLICAZIONE_DOCUMENTI;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument doc = (RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument doc = (RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument) documento;
    return (FaseEstesaType) doc.getRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument doc = 
        (RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument) documento;
    
    PubblicazioneType pubblicazione = doc.getRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti().getPubblicazione();
    
    String idavGara = pubblicazione.getW3IDGARA();
    Long numeroPubblicazione = new Long(pubblicazione.getW9PBNUMPUBB());
    
    if (UtilitySITAT.existsGara(idavGara, this.sqlManager)) {
      if (UtilitySITAT.existsPubblicazione(idavGara, numeroPubblicazione, this.sqlManager)) {
        logger.error("La richiesta di primo invio ha gia' una pubblicazione documenti" +
        		" con CODGARA=" + UtilitySITAT.getCodGaraByIdAvGara(idavGara, sqlManager)
            + ", NUM_PUBB=" + numeroPubblicazione
            + " presente in DB");
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
            "Primo invio di pubblicazione documenti gia' presente in banca dati");
        return;
      } else {
        // TODO codice da abilitare e da testare per la 3.0.0 fase 2 o versioni successive
        // Commentato in fase 1
        // Questo codice controlla che tutti i CIG presenti nel XML ricevuto
        // siano lotti della gara sulla base dati di ORT
        /*String[] arrayCIG = doc.getRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti().getCigLottiArray();
        int numeroCIG = arrayCIG.length;
        
        String sqlParamCIG = null;
        StringBuffer strBufferCig = null;
        if (numeroCIG > 1) {
          strBufferCig = new StringBuffer("'");
          for (String strCig : arrayCIG) {
            strBufferCig.append(strCig);
            strBufferCig.append("','");
          }
          sqlParamCIG = strBufferCig.substring(0, strBufferCig.length()-2);
        } else {
          sqlParamCIG = "'" + arrayCIG[0] + "'";
        }

        Long numeroLottiDiGara = (Long) this.sqlManager.getObject(
            "select count(L.CODLOTT) from W9LOTT l, W9GARA g where " +
              "l.CODGARA=g.CODGARA and g.IDAVGARA=? and l.CIG in (" + sqlParamCIG + ")", new Object[] { idavGara }); 
        
        if (numeroCIG != numeroLottiDiGara) {
          logger.error("La richiesta di primo invio ha dei CIG che non appartengono alla gara con IDAVGARA="+ idavGara +
              ". In particolare i CIG " + sqlParamCIG + " non appartengono tutti alla gara con CODGARA=" +
              UtilitySITAT.getCodGaraByIdAvGara(idavGara, sqlManager));
          
          this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
              "Rettifica di pubblicazione documenti con CIG non appartenenti alla gara");
          return;
        }*/
      }
    } else {
      // non esiste la gara con IDAVGARA indicato
      logger.error("La richiesta di primo invio di una pubblicazione documenti" +
          " relativi ad una gara non presente in base dati (Gara con IDAVGARA=" + idavGara + " inesistente in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di pubblicazione documenti di una gara non presente in banca dati");
      return;
    }
    
    // warnings
    //if(!ignoreWarnings) {
    //}
    // SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI SI AGGIORNA IL DB
    this.insertDatiFlusso(doc, datiAggiornamento);
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument doc = 
      (RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument) documento;
    PubblicazioneType pubblicazione = doc.getRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti().getPubblicazione();

    String idavGara = pubblicazione.getW3IDGARA();
    Long numeroPubblicazione = new Long(pubblicazione.getW9PBNUMPUBB());

    if (UtilitySITAT.existsGara(idavGara, this.sqlManager)) {
      if (!UtilitySITAT.existsPubblicazione(idavGara, numeroPubblicazione, this.sqlManager)) {
        logger.error("Richiesta di rettifica di una pubblicazione documenti" +
            " con CODGARA=" + UtilitySITAT.getCodGaraByIdAvGara(idavGara, sqlManager)
            + ", NUM_PUBB=" + numeroPubblicazione
            + " non presente in DB");
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
            "Rettifica di pubblicazione documenti non presente in banca dati");
        return;
      } else {
        // TODO codice da abilitare e da testare per la 3.0.0 fase 2 o versioni successive
        // Commentato in fase 1
        // Questo codice controlla che tutti i CIG presenti nel XML ricevuto
        // siano lotti della gara sulla base dati di ORT
        /*String[] arrayCIG = doc.getRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti().getCigLottiArray();
        
        int numeroCIG = arrayCIG.length;
        
        String sqlParamCIG = null;
        StringBuffer strBufferCig = null;
        if (numeroCIG > 1) {
          strBufferCig = new StringBuffer("'");
          for (String strCig : arrayCIG) {
            strBufferCig.append(strCig);
            strBufferCig.append("','");
          }
          sqlParamCIG = strBufferCig.substring(0, strBufferCig.length()-2);
        } else {
          sqlParamCIG = "'" + arrayCIG[0] + "'";
        }

        Long numeroLottiDiGara = (Long) this.sqlManager.getObject(
            "select count(L.CODLOTT) from W9LOTT l, W9GARA g where " +
              "l.CODGARA=g.CODGARA and g.IDAVGARA=? and l.CIG in (" + sqlParamCIG + ")", new Object[] { idavGara }); 
        
        if (numeroCIG != numeroLottiDiGara) {
          logger.error("La richiesta di rettifica ha dei CIG che non appartengono alla gara con IDAVGARA="+ idavGara +
              ". In particolare i CIG " + sqlParamCIG + " non appartengono tutti alla gara con CODGARA=" +
              UtilitySITAT.getCodGaraByIdAvGara(idavGara, sqlManager));
          
          this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
              "Rettifica di pubblicazione documenti con CIG non appartenenti alla gara");
          return;
        }*/
      }
    } else {
      // non esiste la gara con IDAVGARA indicato
      logger.error("La richiesta di rettifica di una pubblicazione documenti" +
          " relativi ad una gara non presente in base dati (Gara con IDAVGARA=" + idavGara + " inesistente in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di pubblicazione documenti di una gara non presente in banca dati");
      return;
    }

    // warnings
    //if(!ignoreWarnings) {
    //}
    // SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI SI
    // AGGIORNA IL DB
    
    Long codiceGara = UtilitySITAT.getCodGaraByIdAvGara(idavGara, this.sqlManager);
    // Cancellazione delle occorrenze esistenti
    this.sqlManager.update(
        "delete from W9PUBBLICAZIONI where CODGARA=? and NUM_PUBB=?", 
        new Object[] { codiceGara, numeroPubblicazione });
    this.sqlManager.update(
        "delete from W9PUBLOTTO where CODGARA=? and NUM_PUBB=?", 
        new Object[] { codiceGara, numeroPubblicazione });
    this.sqlManager.update(
        "delete from W9DOCGARA where CODGARA=? and NUM_PUBB=?", 
        new Object[] { codiceGara, numeroPubblicazione });
    
    // Inserimento delle nuove occorrenze
    this.insertDatiFlusso(doc, datiAggiornamento);
  }
  
  /**
   * Effettua l'inserimento vero e proprio dei dati presenti nel flusso.
   * 
   * @param doc
   *        documento XML della richiesta
   * @param datiAggiornamento
   *        container con i dati del flusso
   * @throws SQLException
   * @throws GestoreException
   */
  private void insertDatiFlusso(
      RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument doc,
      DataColumnContainer datiAggiornamento) throws SQLException, GestoreException {

    RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiType richiesta = 
      doc.getRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti();
    
    PubblicazioneType pubblicazione = richiesta.getPubblicazione();
    String idavGara = pubblicazione.getW3IDGARA();
    Long numeroPubblicazione = new Long(pubblicazione.getW9PBNUMPUBB());
    Long codiceGara = UtilitySITAT.getCodGaraByIdAvGara(idavGara, sqlManager);

    // W9PUBBLICAZIONI
    datiAggiornamento.addColumn("W9PUBBLICAZIONI.CODGARA", JdbcParametro.TIPO_NUMERICO, codiceGara);
    datiAggiornamento.addColumn("W9PUBBLICAZIONI.NUM_PUBB", JdbcParametro.TIPO_NUMERICO, numeroPubblicazione);
    datiAggiornamento.getColumn("W9PUBBLICAZIONI.CODGARA").setChiave(true);
    datiAggiornamento.getColumn("W9PUBBLICAZIONI.NUM_PUBB").setChiave(true);

    datiAggiornamento.addColumn("W9PUBBLICAZIONI.TIPDOC",
        JdbcParametro.TIPO_NUMERICO, new Long(pubblicazione.getW9PBTIPDOC()));

    if (pubblicazione.isSetW9PBDESCRIZ()) {
        datiAggiornamento.addColumn("W9PUBBLICAZIONI.DESCRIZ", JdbcParametro.TIPO_TESTO, pubblicazione.getW9PBDESCRIZ());
    }
    
    if (pubblicazione.isSetW9PBDATAPUBB()) {
      datiAggiornamento.addColumn("W9PUBBLICAZIONI.DATAPUBB", JdbcParametro.TIPO_DATA, pubblicazione.getW9PBDATAPUBB().getTime());
    }
    
    if (pubblicazione.isSetW9PBDATASCAD()) {
      datiAggiornamento.addColumn("W9PUBBLICAZIONI.DATASCAD", JdbcParametro.TIPO_DATA, pubblicazione.getW9PBDATASCAD().getTime());
    }

    if (pubblicazione.isSetW9PBDATADEC()) {
      datiAggiornamento.addColumn("W9PUBBLICAZIONI.DATA_DECRETO", JdbcParametro.TIPO_DATA, pubblicazione.getW9PBDATADEC().getTime());
    }
    
    if (pubblicazione.isSetW9PBNUMPR()) {
        datiAggiornamento.addColumn("W9PUBBLICAZIONI.NUM_PROVVEDIMENTO", JdbcParametro.TIPO_TESTO, pubblicazione.getW9PBNUMPR());
    }
    
    if (pubblicazione.isSetW9PBDATAPR()) {
      datiAggiornamento.addColumn("W9PUBBLICAZIONI.DATA_PROVVEDIMENTO", JdbcParametro.TIPO_DATA, pubblicazione.getW9PBDATAPR().getTime());
    }
    
    if (pubblicazione.isSetW9PBDATAST()) {
      datiAggiornamento.addColumn("W9PUBBLICAZIONI.DATA_STIPULA", JdbcParametro.TIPO_DATA, pubblicazione.getW9PBDATAST().getTime());
    }
    
    if (pubblicazione.isSetW9PBNUMPER()) {
        datiAggiornamento.addColumn("W9PUBBLICAZIONI.NUM_REPERTORIO", JdbcParametro.TIPO_TESTO, pubblicazione.getW9PBNUMPER());
    }
    
    datiAggiornamento.insert("W9PUBBLICAZIONI", this.sqlManager);

    // W9PUBLOTTO
    datiAggiornamento.addColumn("W9PUBLOTTO.CODGARA", JdbcParametro.TIPO_NUMERICO, codiceGara);
    datiAggiornamento.addColumn("W9PUBLOTTO.NUM_PUBB", JdbcParametro.TIPO_NUMERICO, numeroPubblicazione);
    datiAggiornamento.addColumn("W9PUBLOTTO.CODLOTT", JdbcParametro.TIPO_NUMERICO, null);
    datiAggiornamento.getColumn("W9PUBLOTTO.CODGARA").setChiave(true);
    datiAggiornamento.getColumn("W9PUBLOTTO.NUM_PUBB").setChiave(true);
    datiAggiornamento.getColumn("W9PUBLOTTO.CODLOTT").setChiave(true);
    
    String[] arrayCIG = richiesta.getCigLottiArray();
    for (int i = 0; i < arrayCIG.length; i++) {
      Long codlott = (Long) sqlManager.getObject("select CODLOTT from W9LOTT where CODGARA=? and CIG=?",
          new Object[] { codiceGara, arrayCIG[i] });
      if (codlott != null) {
        datiAggiornamento.setValue("W9PUBLOTTO.CODLOTT", codlott);
        datiAggiornamento.insert("W9PUBLOTTO", this.sqlManager);
      } else {
        logger.error("La pubblicazione CODGARA=" + codiceGara + ", NUM_PUBB=" + numeroPubblicazione +
            " non e' associabile al CIG=" + arrayCIG[i] + " perche' relativo ad un'altra gara.");
      }
    }

    // W9DOCGARA
    datiAggiornamento.addColumn("W9DOCGARA.CODGARA", JdbcParametro.TIPO_NUMERICO, codiceGara);
    datiAggiornamento.addColumn("W9DOCGARA.NUM_PUBB", JdbcParametro.TIPO_NUMERICO, numeroPubblicazione);
    datiAggiornamento.addColumn("W9DOCGARA.NUMDOC", JdbcParametro.TIPO_NUMERICO, null);
    datiAggiornamento.getColumn("W9DOCGARA.CODGARA").setChiave(true);
    datiAggiornamento.getColumn("W9DOCGARA.NUM_PUBB").setChiave(true);
    datiAggiornamento.getColumn("W9DOCGARA.NUMDOC").setChiave(true);
    datiAggiornamento.addColumn("W9DOCGARA.TITOLO", JdbcParametro.TIPO_TESTO, null);
    datiAggiornamento.addColumn("W9DOCGARA.URL", JdbcParametro.TIPO_TESTO, null);
    datiAggiornamento.addColumn("W9DOCGARA.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, null);
    
    Tab41Type[] arrayDocumenti = richiesta.getDocumentiArray();
    for (int i = 0; i < arrayDocumenti.length; i++) {
      Tab41Type documento = arrayDocumenti[i];
      datiAggiornamento.setValue("W9DOCGARA.NUMDOC", new Long(i+1));
      
      datiAggiornamento.setValue("W9DOCGARA.TITOLO", documento.getW9DGTITOLO());
      if (documento.isSetFile()) {
        // aggiornamento del campo BLOB per il PDF del file allegato
        ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
        try {
          fileAllegato.write(documento.getFile());
        } catch (IOException e) {
          throw new GestoreException(
              "Errore durante la lettura del file allegato presente nella richiesta XML",
              "", e);
        }
        datiAggiornamento.setValue("W9DOCGARA.FILE_ALLEGATO", fileAllegato);
      } else {
        datiAggiornamento.setValue("W9DOCGARA.FILE_ALLEGATO", null);
      }
      
      if (documento.isSetW9DGURL()) {
        datiAggiornamento.setValue("W9DOCGARA.URL", documento.getW9DGURL());
      } else {
        datiAggiornamento.setValue("W9DOCGARA.URL", null);
      }
      datiAggiornamento.insert("W9DOCGARA", this.sqlManager);
    }
    
    // se la procedura di aggiornamento e' andata a buon fine,
    // si aggiorna lo stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

}
