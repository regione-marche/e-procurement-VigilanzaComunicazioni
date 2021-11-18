package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument;
import it.toscana.rete.rfc.sitat.types.Tab281Type;
import it.toscana.rete.rfc.sitat.types.Tab28Type;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della stipula accordo quadro.
 * 
 * @author Luca.Giacomazzo
 */
public class StipulaAccordoQuadroHandler extends AbstractRequestHandler {

  Logger logger = Logger.getLogger(StipulaAccordoQuadroHandler.class);

  @Override
  protected String getNomeFlusso() {
    return "Stipula accordo quadro";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_STIPULA_ACCORDO_QUADRO;
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument doc = (RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getFase();
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument doc = (RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getTest();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument doc = (RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getFase();

    boolean continua = true;

    if (!super.existsLotto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di stipula accordo quadro di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !super.existsAppalto(fase)) {
      logger.error("La richiesta di primo invio stipula accordo quadro per un lotto non aggiudicato "
          + " (cig = " + fase.getW3FACIG() + ") ");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
        "Primo invio di una stipula accordo quadro per un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && this.existsFase(fase, CostantiW9.STIPULA_ACCORDO_QUADRO)) {
      logger.error("La richiesta di primo invio di una stipula accordo quadro con fase gia' "
          + " presente in DB (cig = " + fase.getW3FACIG() + ") ");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di una stipula accordo quadro gia' esistente in banca dati");
      continua = false;
    }

    if (continua && !ignoreWarnings) {
    	if (! this.isFaseVisualizzabile(fase, CostantiW9.STIPULA_ACCORDO_QUADRO)) {
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
            "Primo invio di una stipula accordo quadro non prevista");
        logger.error("Richiesta di primo invio di una stipula accordo quadro non prevista per il "
            + "lotto con cig = " + fase.getW3FACIG() + " (SAQ and EA and not D1)");
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

    this.manageRettificaOIntegrazione(documento, datiAggiornamento, ignoreWarnings, RETTIFICA);
  }

  private void manageRettificaOIntegrazione(XmlObject documento, DataColumnContainer datiAggiornamento,
      boolean ignoreWarnings, int tipoInvio) throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument doc = (RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getFase();

    boolean continua = true;
    if (!super.existsLotto(fase)) {
      logger.error("La richiesta di rettifica di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          UtilityStringhe.capitalize(this.getDescrioneTipoInvio(tipoInvio))
          + " di stipula accordo quadro di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !super.existsAppalto(fase)) {
      logger.error("La richiesta di " + this.getDescrioneTipoInvio(tipoInvio)
          + " di un lotto non aggiudicato in DB (cig = " + fase.getW3FACIG() + ") ");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          UtilityStringhe.capitalize(this.getDescrioneTipoInvio(tipoInvio))
          + " di una stipula accordo quadro per un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && !this.existsFase(fase, CostantiW9.STIPULA_ACCORDO_QUADRO)) {
      logger.error("La richiesta di rettifica di una stipula accordo quadro con stipula non presente in DB (cig = "
          + fase.getW3FACIG() + ") ");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di una stipula accordo quadro non prestente in banca dati");
      continua = false;
    }

    if (continua) {
      this.deleteDatiFlusso(fase);
      this.insertDatiFlusso(doc, datiAggiornamento, false);
    }
  }

  /**
   * Inserimento dei dati del flusso in DB.
   * 
   * @param doc RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument
   * @param datiAggiornamento DataColumnContainer
   * @param isPrimoInvio E' il primo invio?
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument doc,
      final DataColumnContainer datiAggiornamento, final boolean primoInvio) throws SQLException, GestoreException {

    Tab28Type tab28 = doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getTab28();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getFase();

    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];

    if (primoInvio) {
	    String idSchedaLocale = null;
	    String idSchedaSimog = null;
	    if (tab28.isSetW9FASCHLOC()) {
	    	idSchedaLocale = tab28.getW9FASCHLOC();
	  	}
	  	if (tab28.isSetW9FASCHSIM()) {
	  		idSchedaSimog = tab28.getW9FASCHSIM();
	  	}
	  	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio, idSchedaLocale, idSchedaSimog);
    } else { 
    	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);
    }

    DataColumnContainer datiIniz = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9INIZ.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
        new DataColumn("W9INIZ.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
        new DataColumn("W9INIZ.NUM_INIZ", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5())))});

    datiIniz.addColumn("W9INIZ.NUM_APPA", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));
    
    if (tab28.isSetW3DATASTI()) {
      datiIniz.addColumn("W9INIZ.DATA_STIPULA", JdbcParametro.TIPO_DATA,
          tab28.getW3DATASTI().getTime());
    }
    if (tab28.isSetW9INDECO()) {
      datiIniz.addColumn("W9INIZ.DATA_DECORRENZA", JdbcParametro.TIPO_DATA,
          tab28.getW9INDECO().getTime());
    }

    if (tab28.isSetW9INSCAD()) {
      datiIniz.addColumn("W9INIZ.DATA_SCADENZA", JdbcParametro.TIPO_DATA,
          tab28.getW9INSCAD().getTime());
    }

    datiIniz.insert("W9INIZ", this.sqlManager);

    if (doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().isSetTab281()) {
      Tab281Type tab281 = doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getTab281();
      DataColumnContainer datiPues = new DataColumnContainer(new DataColumn[] {
          new DataColumn("W9PUES.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
          new DataColumn("W9PUES.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott))});
      datiPues.addColumn("W9PUES.NUM_INIZ", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()));
      datiPues.addColumn("W9PUES.NUM_PUES", JdbcParametro.TIPO_NUMERICO, new Long(1));

      if (tab281.isSetW3GUCE2()) {
        datiPues.addColumn("W9PUES.DATA_GUCE", JdbcParametro.TIPO_DATA, tab281.getW3GUCE2().getTime());
      }
      if (tab281.isSetW3GURI2()) {
        datiPues.addColumn("W9PUES.DATA_GURI", JdbcParametro.TIPO_DATA, tab281.getW3GURI2().getTime());
      }
      if (tab281.isSetW3NAZ2()) {
        datiPues.addColumn("W9PUES.QUOTIDIANI_NAZ", JdbcParametro.TIPO_NUMERICO,
            new Long(tab281.getW3NAZ2()));
      }
      if (tab281.isSetW3REG2()) {
        datiPues.addColumn("W9PUES.QUOTIDIANI_REG", JdbcParametro.TIPO_NUMERICO,
            new Long(tab281.getW3REG2()));
      }
      datiPues.addColumn("W9PUES.PROFILO_COMMITTENTE", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab281.getW3PROFILO2()).toString());
      datiPues.addColumn("W9PUES.SITO_MINISTERO_INF_TRASP", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab281.getW3MIN2()).toString());
      datiPues.addColumn("W9PUES.SITO_OSSERVATORIO_CP", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab281.getW3OSS2()).toString());

      if (((Long) sqlManager.getObject("select count(*) from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=? and NUM_PUES=1", 
          new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) }) ).intValue() > 0) {
        sqlManager.update("delete from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=? and NUM_PUES=1", new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) });
      }
      
      datiPues.insert("W9PUES", this.sqlManager);
    }

    this.aggiornaStatoExportLotto(codGara, codLott);
    
    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo
    // stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

  /**
   * 
   * @param fase FaseEstesaType
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private void deleteDatiFlusso(final FaseEstesaType fase) throws SQLException, GestoreException {
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];

    this.sqlManager.update(
        "delete from w9iniz where codgara = ? and codlott = ? and num_iniz = ?", new Object[] {
            codGara, codLott, new Long(fase.getW3NUM5()) }, 1);
    this.sqlManager.update(
        "delete from w9pues where codgara = ? and codlott = ? and num_iniz = ?", new Object[] {
            codGara, codLott, new Long(fase.getW3NUM5()) });
  }

}