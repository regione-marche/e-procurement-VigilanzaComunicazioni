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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaInfortunioDocument;
import it.toscana.rete.rfc.sitat.types.Tab16Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della segnalazione infortunio
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class InfortunioHandler extends AbstractRequestHandler {

  Logger logger = Logger.getLogger(InfortunioHandler.class);

  @Override
  protected String getNomeFlusso() {
    return "Segnalazione infortuni";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_INFORTUNIO;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaInfortunioDocument.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaInfortunioDocument doc = (RichiestaRichiestaRispostaSincronaInfortunioDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaInfortunio().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaInfortunioDocument doc = (RichiestaRichiestaRispostaSincronaInfortunioDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaInfortunio().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaInfortunioDocument doc = (RichiestaRichiestaRispostaSincronaInfortunioDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInfortunio().getFase();
	  
    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di richiesta infortunio di un lotto non presente in banca dati");
      continua = false;
    }
	  
	  if (continua && !this.isFaseAbilitata(fase, CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI)) {
	  	logger.error("La richiesta di primo invio richiesta infortunio non abilitata (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di richiesta infortunio non abiltata");
      continua = false;
	  }
    
	  if (continua && this.existsFase(fase, CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI)) {
      logger.error("La richiesta di primo invio richiesta infortunio per un lotto con infortunio gia' presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di richiesta infortunio di un lotto con infortunio presente in banca dati");
      continua = false;
    }
	  
	  // warnings
	  if (continua && !ignoreWarnings) {
	  	if (! this.isFaseVisualizzabile(fase, CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI)) {
	      logger.error("La richiesta di primo invio ha un lotto che non prevista");
	      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
	          "Primo invio di richiesta infortunio di un lotto che non prevista");
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
    RichiestaRichiestaRispostaSincronaInfortunioDocument doc = (RichiestaRichiestaRispostaSincronaInfortunioDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInfortunio().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di rettifica ha un cig ("
          + fase.getW3FACIG()
          + ") di un lotto non presente in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di richiesta infortunio di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsFase(fase, CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI)) {
      logger.error("La richiesta di rettifica segnalazioni infortuni non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di richiesta infortunio non presente in banca dati");
      continua = false;
    }
	  
    if (continua) {
    	this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    }
  }
  
  /**
   * Centralizza la gestione dei casi Integrazione e Rettifica in quanto da
   * analisi allo stato attuale non c'e' alcuna differenza
   * 
   * @param documento
   * @param datiAggiornamento
   * @return
   * @throws GestoreException
   * @throws SQLException
   */
  private void manageIntegrazioneORettifica(
      RichiestaRichiestaRispostaSincronaInfortunioDocument doc,
      DataColumnContainer datiAggiornamento) throws GestoreException,
      SQLException {
    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaInfortunio().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false);
  }
  
  /**
   * Elimina tutte le occorrenze dal DB dei dati da aggiornare (W9INFOR)
   * 
   * @param fase
   *        elemento fase del flusso
   * @throws SQLException
   */
  private void deleteDatiFlusso(FaseEstesaType fase) throws SQLException, GestoreException {
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
	    
    this.sqlManager.update("delete from w9infor where codgara = ? and codlott = ? and num_infor = ?",
        new Object[] { codGara, codLott, fase.getW3NUM5() }, 1);
  }
  
  /**
   * Effettua l'inserimento vero e proprio dei dati presenti nel flusso
   * 
   * @param doc
   *        documento XML della richiesta
   * @param datiAggiornamento
   *        container con i dati del flusso
   * @throws SQLException
   * @throws GestoreException
   */
  private void insertDatiFlusso(
      RichiestaRichiestaRispostaSincronaInfortunioDocument doc,
      DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException,
      GestoreException {

    Tab16Type tab16 = doc.getRichiestaRichiestaRispostaSincronaInfortunio().getTab16();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInfortunio().getFase();
    
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1]; 
    String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());
    
    this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);
    
    // predisposizione dati W9INFOR (tab16): i dati facoltativi vengono testati
    // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
    DataColumnContainer dccInfortunio = new DataColumnContainer(
            new DataColumn[] {
                new DataColumn("W9INFOR.CODGARA", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, codGara)),
                new DataColumn("W9INFOR.CODLOTT", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, codLott)),
                new DataColumn("W9INFOR.NUM_INFOR", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, fase.getW3NUM5())) });
    
    dccInfortunio.addColumn("W9INFOR.NUM_APPA", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));
    
    dccInfortunio.addColumn("W9INFOR.NINFORT", JdbcParametro.TIPO_NUMERICO,
            new Long(tab16.getW3NINFORT()));
    dccInfortunio.addColumn("W9INFOR.NGIORNATE", JdbcParametro.TIPO_NUMERICO,
            new Long(tab16.getW3NGIORNATE()));
    if (tab16.isSetW3IMORTE())
    	dccInfortunio.addColumn("W9INFOR.IMORTE", JdbcParametro.TIPO_NUMERICO,
            new Long(tab16.getW3IMORTE()));
    if (tab16.isSetW3IPERMA())
    	dccInfortunio.addColumn("W9INFOR.IPERMA", JdbcParametro.TIPO_NUMERICO,
            new Long(tab16.getW3IPERMA()));
    
    String pkImpresa = this.gestioneImpresa(tab16.getArch3(), pkUffint);

    dccInfortunio.addColumn("W9INFOR.CODIMP", JdbcParametro.TIPO_TESTO,
    		pkImpresa);
    
    dccInfortunio.insert("W9INFOR", this.sqlManager);

    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo
    // stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

}