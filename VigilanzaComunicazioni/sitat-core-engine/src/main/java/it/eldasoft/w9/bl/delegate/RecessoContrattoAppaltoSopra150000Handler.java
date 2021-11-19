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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab27Type;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione del recesso del contratto di appalto sopra
 * 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class RecessoContrattoAppaltoSopra150000Handler extends
AbstractRequestHandler {

  Logger logger = Logger.getLogger(RecessoContrattoAppaltoSopra150000Handler.class);

  @Override
  protected String getNomeFlusso() {
    return "Istanza di recesso";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_RECESSO_CONTRATTO_APPALTO_SOPRA_150000;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getFase();

    boolean continua = true;

    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non presente in DB (cig = "
      		+ fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di una Istanza di recesso di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non aggiudicato in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di un Recesso del contratto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && !this.isFaseAbilitata(fase, CostantiW9.ISTANZA_RECESSO)) {
    	logger.error("La richiesta di primo invio istanza di recesso non abilitata (cig = " + fase.getW3FACIG() + ")");
    	this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), 
    			"Primo invio istanza di recesso non abilitata");
    	continua = false;
    }
    
    if (continua && this.existsFase(fase, CostantiW9.ISTANZA_RECESSO)) {
      logger.error("La richiesta di primo invio istanza di recesso per un lotto con istanza di recesso gia' esistente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di una Istanza di recesso gia' presente in banca dati");
      continua = false;
    }

    // warnings
    if (continua && !ignoreWarnings) {
    	if (! this.isFaseVisualizzabile(fase, CostantiW9.ISTANZA_RECESSO)) {
        logger.error("La richiesta di primo invio istanza di recesso non previsto per il lotto con CIG="
            + fase.getW3FACIG());
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
        		"Primo invio istanza di recesso non previsto");
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
    RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getFase();

    boolean continua = true;
    
    // esiste lotto
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di rettifica di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di una Istanza di recesso di un lotto non presente in banca dati");
      // si termina con questo errore
      continua = false;
    }

    // Il lotto in questione non ha appalto (W9APPA)
    if (!this.existsAppalto(fase)) {
      logger.error("La richiesta di rettifica di un lotto non aggiudicato sul DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di un Recesso del contratto per un lotto non aggiudicato in banca dati");
      continua = false;
    }
    
    if (continua && this.existsFase(fase, CostantiW9.ISTANZA_RECESSO)) {
      logger.error("La richiesta di rettifica istanza di recesso non presente sul DB (cig = "
          + fase.getW3NUM5() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di una Istanza di recesso non presente in banca dati");
      continua = false;
    }

    if (continua) {
    	this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    }
  }

  private void insertDatiFlusso(
      RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc,
      DataColumnContainer datiAggiornamento, boolean primoInvio)
  throws SQLException, GestoreException {

    Tab27Type tab27 = doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getTab27();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getFase();

    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];

    if (primoInvio) {
	    String idSchedaLocale = null;
	    String idSchedaSimog = null;
	    if (tab27.isSetW9FASCHLOC()) {
	    	idSchedaLocale = tab27.getW9FASCHLOC();
	  	}
	  	if (tab27.isSetW9FASCHSIM()) {
	  		idSchedaSimog = tab27.getW9FASCHSIM();
	  	}
	  	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio, idSchedaLocale, idSchedaSimog);
    } else { 
    	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);
    }

    // predisposizione dati W9CONC (tab8): i dati facoltativi vengono testati
    // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
    DataColumnContainer dccRita = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9RITA.CODGARA", new JdbcParametro(
            JdbcParametro.TIPO_NUMERICO, codGara)),
            new DataColumn("W9RITA.CODLOTT", new JdbcParametro(
                JdbcParametro.TIPO_NUMERICO, codLott)),
                new DataColumn("W9RITA.NUM_RITA", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, fase.getW3NUM5())) });

    dccRita.addColumn("W9RITA.NUM_APPA", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));
    
    dccRita.addColumn("W9RITA.DATA_TERMINE", JdbcParametro.TIPO_DATA,
        tab27.getW3DTERM2().getTime());
    if (tab27.isSetW3DCONS()) {
      dccRita.addColumn("W9RITA.DATA_CONSEGNA", JdbcParametro.TIPO_DATA,
          tab27.getW3DCONS().getTime());
    }
    dccRita.addColumn("W9RITA.DATA_IST_RECESSO", JdbcParametro.TIPO_DATA,
        tab27.getW3DATAIST().getTime());
    if (tab27.isSetW3ACCOLTA()) {
      dccRita.addColumn("W9RITA.FLAG_ACCOLTA", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab27.getW3ACCOLTA()).toString());
    }
    if (tab27.isSetW3MOTIVOS()) {
      dccRita.addColumn("W9RITA.MOTIVO_SOSP", JdbcParametro.TIPO_TESTO,
          tab27.getW3MOTIVOS());
    }

    // Gli attributi W3TIPOCOM, W3FLAGRIP, W3FLAGTAR, W3DURATAS
    // e W3RISER sono diventati obbligatori

    dccRita.addColumn("W9RITA.TIPO_COMUN", JdbcParametro.TIPO_TESTO,
        tab27.getW3TIPOCOM().toString());
    dccRita.addColumn("W9RITA.FLAG_RIPRESA", JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab27.getW3FLAGRIP()).toString());
    dccRita.addColumn("W9RITA.FLAG_TARDIVA", JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab27.getW3FLAGTAR()).toString());
    dccRita.addColumn("W9RITA.DURATA_SOSP", JdbcParametro.TIPO_NUMERICO,
        new Long(tab27.getW3DURATAS()));
    dccRita.addColumn("W9RITA.FLAG_RISERVA", JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab27.getW3RISER()).toString());
    
    if (tab27.isSetW3RITARDO()) {
      dccRita.addColumn("W9RITA.RITARDO", JdbcParametro.TIPO_NUMERICO,
          new Long(tab27.getW3RITARDO()));
    }
    if (tab27.isSetW3IONERI()) {
      dccRita.addColumn("W9RITA.IMPORTO_ONERI", JdbcParametro.TIPO_DECIMALE,
          tab27.getW3IONERI());
    }
    if (tab27.isSetW3ISPESE()) {
      dccRita.addColumn("W9RITA.IMPORTO_SPESE", JdbcParametro.TIPO_DECIMALE,
          tab27.getW3ISPESE());
    }

    dccRita.insert("W9RITA", this.sqlManager);

    this.aggiornaStatoExportLotto(codGara, codLott);

    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo
    // stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

  private void manageIntegrazioneORettifica(
      RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc,
      DataColumnContainer datiAggiornamento) throws SQLException,
      GestoreException {
    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false);
  }

  /**
   * Cancellazione dei dati del flusso dalla tabella W9RITA.
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
        "delete from w9rita where codgara = ? and codlott = ? and num_rita= ?",
        		new Object[] { codGara, codLott, fase.getW3NUM5() }, 1);
  }

}