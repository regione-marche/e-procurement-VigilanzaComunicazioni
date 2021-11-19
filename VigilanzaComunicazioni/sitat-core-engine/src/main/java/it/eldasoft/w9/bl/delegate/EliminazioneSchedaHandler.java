package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
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
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
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
	  
    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di cancellazione fase di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Richiesta cancellazione di una fase per un lotto non presente in banca dati");
      continua = false;
    }
	
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    datiAggiornamento.addColumn("W9FASI.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
    datiAggiornamento.addColumn("W9FASI.CODLOTT", JdbcParametro.TIPO_NUMERICO, codLott);
	
    if (continua && !this.existsFase(fase, Integer.parseInt(fase.getW3FASEESEC().toString()))){
      logger.error("La richiesta di cancellazione della fase " + fase.getW3FASEESEC().toString() 
      		+ " non presente in DB (cig = " + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Richiesta cancellazione per una fase non presente in banca dati");
      continua = false;
    }
	  
    if (continua) {
    	// Essendo una richiesta di cancellazione metto lo stato a 4 che indica che la richiesta deve essere evasa
    	this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), null);
    }
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
    ;
  }

}