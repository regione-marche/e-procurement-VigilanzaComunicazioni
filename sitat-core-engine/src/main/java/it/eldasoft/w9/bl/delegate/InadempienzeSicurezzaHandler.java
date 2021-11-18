package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument;
import it.toscana.rete.rfc.sitat.types.Tab15Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'esito negativo della verifica regolarita'
 * contributiva e assicurativa
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class InadempienzeSicurezzaHandler extends AbstractRequestHandler {

  Logger logger = Logger.getLogger(InadempienzeSicurezzaHandler.class);

  @Override
  protected String getNomeFlusso() {
    return "Inadempienze predisposizioni sicurezza e regolarita' lavoro";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_INADEMPIENZE_SICUREZZA;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc = (RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc = (RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc = (RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio inadempienze sicurezza di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di inadempienze sicurezza con lotto non esistente in banca dati");
      continua = false;
    }

    if (!this.existsAppalto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non aggiudicato in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di inadempienze sicurezza con un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && !this.isFaseAbilitata(fase, CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI)) {
    	logger.error("La richiesta di primo invio inadempienze sicurezza non abilitata (cig = " + fase.getW3FACIG() + ")");
    	this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), 
    			"Primo invio inadempienze sicurezza non abilitato");
    	continua = false;
    }
    
    if (this.existsFase(fase, CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI)) {
      logger.error("La richiesta di primo invio inadempienze sicurezza per un lotto che presenta gia' inadempienze in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di inadempienze sicurezza gia' presenti in banca dati");
      continua = false;
    }

    // Warnings
    if (continua && !ignoreWarnings) {
    	if (! this.isFaseVisualizzabile(fase, CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI)) {
        logger.error("La richiesta di primo invio inadempienze sicurezza non prevista");
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
        		"Primo invio di scheda inadempienze sicurezza non prevista");
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
    RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc = (RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di rettifica di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di Inadempienze con lotto non esistente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di rettifica di un lotto non aggiudicato in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di Inadempienze con un lotto non aggiudicato in banca dati");
      continua = false;
    }
    
    if (continua && !this.existsFase(fase, CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI)) {
      logger.error("La richiesta di rettifica di un lotto che presenta gia' inadempienze in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di inadempienze gia' presenti in banca dati");
      continua = false;
    }
    
    if (continua) {
    	this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    }
  }

  private void manageIntegrazioneORettifica(
      RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc,
      DataColumnContainer datiAggiornamento) throws SQLException,
      GestoreException {
    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false);

  }

  private void insertDatiFlusso(
      RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc,
      DataColumnContainer datiAggiornamento, boolean primoInvio)
  throws SQLException, GestoreException {
    Tab15Type tab15 = doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getTab15();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getFase();

    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

    this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

    // predisposizione dati W9INASIC (tab15): i dati facoltativi vengono testati
    // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
    DataColumnContainer dccInasic = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9INASIC.CODGARA", new JdbcParametro(
            JdbcParametro.TIPO_NUMERICO, codGara)),
            new DataColumn("W9INASIC.CODLOTT", new JdbcParametro(
                JdbcParametro.TIPO_NUMERICO, codLott)),
                new DataColumn("W9INASIC.NUM_INAD", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });

    dccInasic.addColumn("W9INASIC.NUM_APPA", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));
    
    if (tab15.isSetArch3() ){
      String pkImpresa = this.gestioneImpresa(tab15.getArch3(), pkUffint);
      dccInasic.addColumn("W9INASIC.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);
    }
    if (tab15.isSetW3COMMA3A()) {
      dccInasic.addColumn("W9INASIC.COMMA3A", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab15.getW3COMMA3A()).toString());
    }
    if (tab15.isSetW3COMMA3B()) {
      dccInasic.addColumn("W9INASIC.COMMA3B", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab15.getW3COMMA3B()).toString());
    }
    if (tab15.isSetW3COMMA45A()) {
      dccInasic.addColumn("W9INASIC.COMMA45A", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab15.getW3COMMA45A()).toString());
    }
    if (tab15.isSetW3COMMA5()) {
      dccInasic.addColumn("W9INASIC.COMMA1", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab15.getW3COMMA5()).toString());
    }
    if (tab15.isSetW3COMMA6()) {
      dccInasic.addColumn("W9INASIC.COMMA2", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab15.getW3COMMA6()).toString());
    }
    if (tab15.isSetW3COMMALTRO()) {
      dccInasic.addColumn("W9INASIC.COMMALTRO", JdbcParametro.TIPO_TESTO,
          tab15.getW3COMMALTRO());
    }
    dccInasic.addColumn("W9INASIC.DAINASIC", JdbcParametro.TIPO_DATA,
        tab15.getW3DAINASIC().getTime());

    if (tab15.isSetW3DESIC()) {
      dccInasic.addColumn("W9INASIC.DESCVIO", JdbcParametro.TIPO_TESTO,
          tab15.getW3DESIC());
    }
    // campi differenti rispetto alla vers.9

    dccInasic.insert("W9INASIC", this.sqlManager);

    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo
    // stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

  private void deleteDatiFlusso(FaseEstesaType fase) throws SQLException, GestoreException {
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];

    this.sqlManager.update(
        "delete from w9inasic where codgara = ? and codlott = ? and num_inad = ?",
        new Object[] { codGara, codLott, fase.getW3NUM5() }, 1);
  }

}