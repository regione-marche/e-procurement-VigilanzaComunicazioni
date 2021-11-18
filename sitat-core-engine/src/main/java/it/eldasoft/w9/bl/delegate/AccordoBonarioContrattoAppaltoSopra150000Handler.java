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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab25Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'accordo bonario contratto di appalto sopra 40000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class AccordoBonarioContrattoAppaltoSopra150000Handler extends
AbstractRequestHandler {

  Logger logger = Logger.getLogger(AccordoBonarioContrattoAppaltoSopra150000Handler.class);

  @Override
  protected String getNomeFlusso() {
    return "Accordo bonario";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_ACCORDO_BONARIO_CONTRATTO_APPALTO_SOPRA_150000;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio accordo bonario ha un cig ("
          + fase.getW3FACIG() + ") di un lotto non presente in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
        "Primo invio di accordo bonario di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di primo invio accordo bonario fa riferimento ad un lotto (cig = "
          + fase.getW3FACIG() + ") non aggiudicato");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
        "Primo invio di accordo bonario di un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && !this.isFaseAbilitata(fase, CostantiW9.ACCORDO_BONARIO)) {
      logger.error("La richiesta di primo invio accordo bonario non abilitato (cig="
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
        "Primo invio di accordo bonario non abiltato");
      continua = false;
    }

    if (continua && this.existsFase(fase, CostantiW9.ACCORDO_BONARIO)) {
      logger.error("La richiesta di primo invio accordo bonario gia' presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
        "Primo invio di accordo bonario di un lotto gia' presente in banca dati");
      continua = false;
    }

    // warnings
    if (continua && !ignoreWarnings) {
      if (! this.isFaseVisualizzabile(fase, CostantiW9.ACCORDO_BONARIO)) {
        logger.error("La richiesta di primo invio accordo bonario non previsto per il lotto con CIG="
            + fase.getW3FACIG());
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
          "Primo invio di accordo bonario non previsto");
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
    RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getFase();

    boolean continua = true;

    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di rettifica accordo bonario per un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
        "Rettifica accordo bonario per un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di rettifica accordo bonario di un lotto non aggiudicato (cig="
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
        "Rettifica accordo bonario di un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && !this.existsFase(fase, CostantiW9.ACCORDO_BONARIO)) {
      logger.error("La richiesta di rettifica accordo bonario non presente in DB (cig="
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di accordo bonario non presente in banca dati");
      continua = false;
    }

    if (continua) {
    	this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    }
  }

  /**
   * Inserimento dati del flusso in DB.
   * 
   * @param doc RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document
   * @param datiAggiornamento DataColumnContainer
   * @param primoInvio Primo Invio
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private void insertDatiFlusso(final
      RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc,
      final DataColumnContainer datiAggiornamento, final boolean primoInvio)
  throws SQLException, GestoreException {
    Tab25Type tab25 = doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getTab25();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getFase();

    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];

    if (primoInvio) {
	    String idSchedaLocale = null;
	    String idSchedaSimog = null;
	    if (tab25.isSetW9FASCHLOC()) {
	    	idSchedaLocale = tab25.getW9FASCHLOC();
	  	}
	  	if (tab25.isSetW9FASCHSIM()) {
	  		idSchedaSimog = tab25.getW9FASCHSIM();
	  	}
	  	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio, idSchedaLocale, idSchedaSimog);
    } else { 
    	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);
    }
    
    // predisposizione dati W9ACCO (tab18): i dati facoltativi vengono testati
    // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
    DataColumnContainer dccAcco = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9ACCO.CODGARA", new JdbcParametro(
            JdbcParametro.TIPO_NUMERICO, codGara)),
            new DataColumn("W9ACCO.CODLOTT", new JdbcParametro(
                JdbcParametro.TIPO_NUMERICO, codLott)),
                new DataColumn("W9ACCO.NUM_ACCO", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });

    dccAcco.addColumn("W9ACCO.NUM_APPA", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));
    
    dccAcco.addColumn("W9ACCO.DATA_ACCORDO", JdbcParametro.TIPO_DATA,
        tab25.getW3DATAACC().getTime());

    if (tab25.isSetW3NUMRISE()) {
      dccAcco.addColumn("W9ACCO.NUM_RISERVE", JdbcParametro.TIPO_NUMERICO,
          new Long(tab25.getW3NUMRISE()));
    }

    if (tab25.isSetW3ONERIDE()) {
      dccAcco.addColumn("W9ACCO.ONERI_DERIVANTI", JdbcParametro.TIPO_DECIMALE,
          tab25.getW3ONERIDE());
    }
    
    dccAcco.insert("W9ACCO", this.sqlManager);

    this.aggiornaStatoExportLotto(codGara, codLott);

    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo
    // stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

  private void manageIntegrazioneORettifica(
      RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc,
      DataColumnContainer datiAggiornamento) throws SQLException,
      GestoreException {

    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false);

  }

  /**
   * Cancellazione dei dati del flusso.
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
        "delete from w9acco where codgara = ? and codlott = ? and num_acco = ?",
        new Object[] { codGara, codLott, fase.getW3NUM5() }, 1);
  }

}