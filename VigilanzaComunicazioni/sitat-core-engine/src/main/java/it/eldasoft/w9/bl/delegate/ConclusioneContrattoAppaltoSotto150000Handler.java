package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document;
import it.toscana.rete.rfc.sitat.types.Tab9Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della conclusione contratto appalto sotto
 * 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class ConclusioneContrattoAppaltoSotto150000Handler extends
AbstractRequestHandler {

  Logger logger = Logger.getLogger(ConclusioneContrattoAppaltoSotto150000Handler.class);

  @Override
  protected String getNomeFlusso() {
    return "Fase semplificata di conclusione appalto";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_CONCLUSIONE_CONTRATTO_APPALTO_SOTTO_150000;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000().getFase();
  }

  /**
   * Verifica se esiste in banca dati una fase iniziale per il lotto indicato
   * a partire dal cig fornito nel tracciato.
   * 
   * @param fase
   *        elemento fase del flusso
   * @return true se l'elemento esiste, false altrimenti
   * @throws SQLException
   */
  private boolean existsInizio(FaseEstesaType fase) throws SQLException {
    Long numeroOccorrenze = (Long) this.sqlManager.getObject(
        "select count(w9iniz.codgara) from w9iniz, w9lott "
        + "where w9iniz.codgara = w9lott.codgara and w9iniz.codlott = w9lott.codlott "
        + "and w9lott.cig = ? and w9iniz.num_appa = ?", new Object[] { fase.getW3FACIG(), new Long(fase.getW9NUMAPPA()) });
    return numeroOccorrenze.intValue() > 0;
  }

  /**
   * Verifica se esiste in banca dati una fase iniziale per il lotto indicato
   * a partire dal cig fornito nel tracciato.
   * 
   * @param fase
   *        elemento fase del flusso
   * @return true se l'elemento esiste, false altrimenti
   * @throws SQLException
   */
  private boolean existsConclusione(FaseEstesaType fase) throws SQLException {
    Long numeroOccorrenze = (Long) this.sqlManager.getObject(
        "select count(w9conc.codgara) from w9conc, w9lott "
        + "where w9conc.codgara = w9lott.codgara and w9conc.codlott = w9lott.codlott "
        + "and w9lott.cig = ? and w9conc.num_appa = ?",
        new Object[] { fase.getW3FACIG(), new Long(fase.getW9NUMAPPA()) });
    return numeroOccorrenze.intValue() > 0;
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non presente in DB (cig  = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di conclusione appalto semplificata di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non aggiudicato sul DB (CIG="
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di conclusione appalto semplificata di un lotto non aggiudicato in banca dati");
      continua = false;
    }
    
    if (continua && !this.isFaseAbilitata(fase, CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO)) {
    	logger.error("La richiesta di primo invio della conclusione contratto semplificata non abilitata (cig = " + fase.getW3FACIG() + ")");
    	this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), 
    			"Primo invio conclusione contratto semplificato non abilitato");
    	continua = false;
    }

    if (continua && this.existsFase(fase, CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO)) {
      logger.error("La richiesta di primo invio conclusione semplificata per  di un lotto avente gia' una fase conclusione in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di conclusione appalto semplificata di un lotto avente gia' una fase conclusione in banca dati");
      continua = false;
    }

    // warnings
    if (continua && !ignoreWarnings) {
    	if (! this.isFaseVisualizzabile(fase, CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO)) {
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
        		"Primo invio di conclusione appalto semplificata non prevista");
        continua = false;
      }
    }
    if (continua) {
    	this.insertDatiFlusso(doc, datiAggiornamento, true);
    }
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di rettifica di un lotto non presente in DB (cig ="
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di conclusione contratto semplificata di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di rettifica di un lotto non aggiudicato in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di conclusione contratto semplificata di un lotto non aggiudicato in banca dati");
      continua = false;
    }


    if (continua && !this.existsFase(fase, CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO)) {
      logger.error("La richiesta di rettifica di un lotto senza una fase conclusione sul DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di conclusione contratto semplificata di un lotto senza una fase conclusione in banca dati");
      continua = false;
    }

    if (continua) {
    	this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    }
  }

  private void manageIntegrazioneORettifica(
      RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document doc,
      DataColumnContainer datiAggiornamento) throws SQLException,
      GestoreException {
    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false);
  }

  private void insertDatiFlusso(
      RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document doc,
      DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException,
      GestoreException {

    Tab9Type tab9 = doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000().getTab9();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000().getFase();

    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];

    this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

    // predisposizione dati W9CONC (tab8): i dati facoltativi vengono testati
    // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
    DataColumnContainer dccConc = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9CONC.CODGARA", new JdbcParametro(
            JdbcParametro.TIPO_NUMERICO, codGara)),
            new DataColumn("W9CONC.CODLOTT", new JdbcParametro(
                JdbcParametro.TIPO_NUMERICO, codLott)),
                new DataColumn("W9CONC.NUM_CONC", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });

    dccConc.addColumn("W9CONC.NUM_APPA", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));
    
    dccConc.addColumn("W9CONC.INTANTIC", JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab9.getW3CSINTANTIC()).toString());
    if (tab9.isSetW3FLAGPOL()) {
      dccConc.addColumn("W9CONC.FLAG_POLIZZA", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab9.getW3FLAGPOL()).toString());
    }
    if (tab9.isSetW3DATAULT()) {
      dccConc.addColumn("W9CONC.DATA_ULTIMAZIONE", JdbcParametro.TIPO_DATA,
        tab9.getW3DATAULT().getTime());
    }
    if (tab9.isSetW9COORELAVO()) {
    	dccConc.addColumn("W9CONC.ORE_LAVORATE", JdbcParametro.TIPO_DECIMALE,
    	          new Double(tab9.getW9COORELAVO()));
    }
    if (tab9.isSetW3DATARIS()) {
      dccConc.addColumn("W9CONC.DATA_RISOLUZIONE", JdbcParametro.TIPO_DATA,
          tab9.getW3DATARIS().getTime());
    }
    if (tab9.isSetW3ONERIRI()) {
      dccConc.addColumn("W9CONC.ONERI_RISOLUZIONE",
          JdbcParametro.TIPO_DECIMALE, tab9.getW3ONERIRI());
    }
    if (tab9.isSetW3FLAGONE()) {
      dccConc.addColumn("W9CONC.FLAG_ONERI", JdbcParametro.TIPO_TESTO,
          tab9.getW3FLAGONE().toString());
    }
    if (tab9.isSetW3IDMOTI1()) {
      dccConc.addColumn("W9CONC.ID_MOTIVO_INTERR",
          JdbcParametro.TIPO_NUMERICO, new Long(tab9.getW3IDMOTI1()));
    }
    if (tab9.isSetW3IDMOTI2()) {
      dccConc.addColumn("W9CONC.ID_MOTIVO_RISOL", JdbcParametro.TIPO_NUMERICO,
          new Long(tab9.getW3IDMOTI2()));
    }
    dccConc.insert("W9CONC", this.sqlManager);

    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo
    // stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

  private void deleteDatiFlusso(FaseEstesaType fase) throws SQLException, GestoreException {
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];

    this.sqlManager.update(
        "delete from w9conc where codgara = ? and codlott = ? and num_conc = ?",
        new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) }, 1);
  }

}