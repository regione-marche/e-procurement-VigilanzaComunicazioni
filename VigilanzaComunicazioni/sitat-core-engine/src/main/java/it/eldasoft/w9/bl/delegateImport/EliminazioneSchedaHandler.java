package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della Richiesta di Cancellazione
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class EliminazioneSchedaHandler extends AbstractRequestHandler {

  Logger logger = Logger.getLogger(EliminazioneSchedaHandler.class);

  @Override
  protected String getNomeFlusso() {
    return "Richiesta cancellazione";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_ELIMINAZIONE_SCHEDA;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
	  RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument doc = (RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaEliminazioneScheda().getTest();
  }

  @Override
  protected FaseType getFaseInvio(XmlObject documento) {
	  RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument doc = (RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaEliminazioneScheda().getFase();
  }

  //Gestiamo comunque solamente il primo invio per tutte le richieste di cancellazione
  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument doc = (RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaEliminazioneScheda().getFase();
	  
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di cancellazione ha un cig ("
          + fase.getW3FACIG()
          + ") di un lotto non presente in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "richiesta cancellazione per una fase di un lotto non presente in banca dati");
      // si termina con questo errore
      return;
    }
	
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    datiAggiornamento.addColumn("W9FASI.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
	datiAggiornamento.addColumn("W9FASI.CODLOTT", JdbcParametro.TIPO_NUMERICO, codLott);
	
	if (!this.existsFase(fase.getW3FACIG(), Integer.parseInt(fase.getW3FASEESEC().toString()), fase.getW3NUM5())){
      logger.error("La richiesta di primo invio ha un cig ("
          + fase.getW3FACIG()
          + ") di un lotto con infortunio presente sul DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "richiesta cancellazione per una fase non presente in banca dati");
      // si termina con questo errore
      return;
    }
	  
    // SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI SI
    // AGGIORNA IL DB
	//Essendo una richiesta di cancellazione metto lo stato a 4 che indica che la richiesta deve essere evasa
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), null);
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
    ;
  }

}