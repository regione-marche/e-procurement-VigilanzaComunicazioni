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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab26Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione del subappalto contratto di appalto sopra
 * 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class SubappaltoContrattoAppaltoSopra150000Handler extends
AbstractRequestHandler {

  Logger logger = Logger.getLogger(SubappaltoContrattoAppaltoSopra150000Handler.class);

  @Override
  protected String getNomeFlusso() {
    return "Subappalto";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_SUBAPPALTO_CONTRATTO_APPALTO_SOPRA_150000;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio subappalto per un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di subappalto di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di primo invio subappalto per un lotto non aggiudicato in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di subappalto di un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && !this.isFaseAbilitata(fase, CostantiW9.SUBAPPALTO)) {
    	logger.error("La richiesta di primo invio del subappalto non abilitato (cig = " + fase.getW3FACIG() + ")");
    	this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), 
    			"Primo invio subappalto non abilitato");
    	continua = false;
    }
    
    if (continua && this.existsFase(fase, CostantiW9.SUBAPPALTO)) {
      logger.error("La richiesta di primo invio subappalto per un lotto gia' subappaltato in DB (cig  = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di subappalto per un lotto gia' subappaltato in banca dati");
      continua = false;
    }

    // warnings
    if (continua && !ignoreWarnings) {
    	if (! this.isFaseVisualizzabile(fase, CostantiW9.SUBAPPALTO)) {
        logger.error("La richiesta di primo invio di un subappalto non previsto per CIG = " + fase.getW3FACIG());
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
        		"Primo invio di subappalto non previsto");
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
    RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di rettifica subappalto per un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di subappalto di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di rettifica subappalto per un lotto non apggiudicato in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di subappalto di un lotto non aggiudicato in banca dati");
      continua = false;
    }
    
    if (!this.existsFase(fase, CostantiW9.SUBAPPALTO)) {
      logger.error("La richiesta di rettifica subappalto di un lotto non subappaltato in DB (cig  = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di subappalto di un lotto non subappaltato in banca dati");
      continua = false;
    }

    if (continua) {
    	this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    }
  }

  /**
   * Centralizza la gestione dei casi Integrazione e Rettifica in quanto da
   * analisi allo stato attuale non c'e' alcuna differenza.
   * 
   * @param documento
   * @param datiAggiornamento
   * @return
   * @throws GestoreException
   * @throws SQLException
   */
  private void manageIntegrazioneORettifica(
      RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc,
      DataColumnContainer datiAggiornamento) throws GestoreException,
      SQLException {
    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false);
  }

  /**
   * Elimina tutte le occorrenze dal DB dei dati da aggiornare (W9SUBA).
   * 
   * @param fase
   *        elemento fase del flusso
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   * 
   */
  private void deleteDatiFlusso(final FaseEstesaType fase) throws SQLException, GestoreException {
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];

    this.sqlManager.update("delete from w9suba where codgara = ? and codlott = ? and num_suba = ?",
        new Object[] { codGara, codLott, fase.getW3NUM5() }, 1);
  }

  /**
   * Effettua l'inserimento vero e proprio dei dati presenti nel flusso.
   * 
   * @param doc documento XML della richiesta
   * @param datiAggiornamento container con i dati del flusso
   * @param primoInvio Tipo di invio
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc,
      final DataColumnContainer datiAggiornamento, final boolean primoInvio) throws SQLException,
      GestoreException {

    Tab26Type tab26 = doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getTab26();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getFase();

    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

    if (primoInvio) {
	    String idSchedaLocale = null;
	    String idSchedaSimog = null;
	    if (tab26.isSetW9FASCHLOC()) {
	    	idSchedaLocale = tab26.getW9FASCHLOC();
	  	}
	  	if (tab26.isSetW9FASCHSIM()) {
	  		idSchedaSimog = tab26.getW9FASCHSIM();
	  	}
	  	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio, idSchedaLocale, idSchedaSimog);
    } else { 
    	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);
    }

    // predisposizione dati W9SUBA (tab26): i dati facoltativi vengono testati
    // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
    DataColumnContainer dccSubappalto = new DataColumnContainer(
        new DataColumn[] {
            new DataColumn("W9SUBA.CODGARA", new JdbcParametro(
                JdbcParametro.TIPO_NUMERICO, codGara)),
                new DataColumn("W9SUBA.CODLOTT", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, codLott)),
                    new DataColumn("W9SUBA.NUM_SUBA", new JdbcParametro(
                        JdbcParametro.TIPO_NUMERICO, fase.getW3NUM5())) });

    dccSubappalto.addColumn("W9SUBA.NUM_APPA", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));
    
    if (tab26.isSetW3DATAAUT()) {
      dccSubappalto.addColumn("W9SUBA.DATA_AUTORIZZAZIONE", JdbcParametro.TIPO_DATA,
          tab26.getW3DATAAUT().getTime());
    }
    if (tab26.isSetW3OSUBA()) {
      dccSubappalto.addColumn("W9SUBA.OGGETTO_SUBAPPALTO", JdbcParametro.TIPO_TESTO, tab26.getW3OSUBA());
    }
    dccSubappalto.addColumn("W9SUBA.IMPORTO_PRESUNTO", JdbcParametro.TIPO_DECIMALE,
        tab26.getW3IPRESUN());
    dccSubappalto.addColumn("W9SUBA.IMPORTO_EFFETTIVO", JdbcParametro.TIPO_DECIMALE,
        tab26.getW3IEFFETT());
    dccSubappalto.addColumn("W9SUBA.ID_CATEGORIA", JdbcParametro.TIPO_TESTO,
        tab26.getW3IDCATE2().toString());
    dccSubappalto.addColumn("W9SUBA.ID_CPV", JdbcParametro.TIPO_TESTO,
        tab26.getW3IDCPV());

    String pkImpresa = this.gestioneImpresa(tab26.getArch3(), pkUffint);

    dccSubappalto.addColumn("W9SUBA.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);

    if (tab26.isSetArch3Impagg()) {
      String pkImpresaAgg = this.gestioneImpresa(tab26.getArch3Impagg(), pkUffint);
      dccSubappalto.addColumn("W9SUBA.CODIMP_AGGIUDICATRICE", JdbcParametro.TIPO_TESTO, pkImpresaAgg);
    }

    dccSubappalto.insert("W9SUBA", this.sqlManager);

    this.aggiornaStatoExportLotto(codGara, codLott);

    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo
    // stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

}