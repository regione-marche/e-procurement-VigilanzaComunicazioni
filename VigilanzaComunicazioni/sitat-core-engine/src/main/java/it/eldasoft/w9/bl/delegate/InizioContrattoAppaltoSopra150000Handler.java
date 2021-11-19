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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab101Type;
import it.toscana.rete.rfc.sitat.types.Tab13Type;
import it.toscana.rete.rfc.sitat.types.Tab192Type;
import it.toscana.rete.rfc.sitat.types.Tab19Type;
import it.toscana.rete.rfc.sitat.types.Tab41Type;
import it.toscana.rete.rfc.sitat.types.Tab8Bis2Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'inizio contratto appalto sopra 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class InizioContrattoAppaltoSopra150000Handler extends AbstractRequestHandler {

  Logger logger = Logger.getLogger(InizioContrattoAppaltoSopra150000Handler.class);

  @Override
  protected String getNomeFlusso() {
    return "Fase iniziale di esecuzione del contratto";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_INIZIO_CONTRATTO_APPALTO_SOPRA_150000;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio non ha un lotto (cig = "
          + fase.getW3FACIG() + ") ");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di una Fase iniziale di esecuzione del contratto con lotto non esistente in banca dati");
      continua = false;
    }
    
    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non appaltato (cig = "
      		+ fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di una Fase iniziale di esecuzione del contratto con appalto non esistente in banca dati");
      continua = false;
    }

    if (continua && this.existsFase(fase, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA)) {
      logger.error("La richiesta di primo invio fase iniziale di esecuzione del contratto gia' esistente in DB ("
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di una Fase iniziale di esecuzione del contratto gia' esistente in banca dati");
      return;
    }

    if (!ignoreWarnings) {
    	if (! this.isFaseVisualizzabile(fase, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA)) {
        logger.error("La richiesta di primo invio di Fase iniziale di esecuzione del contratto non previsto per il lotto con CIG=" + fase.getW3FACIG()
        		+ " (S2 and NOT SAQ and EA and Ord and NOT D1)");
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
        		"Primo invio di inizio esecuzione del contratto non previsto");
        // si termina con questo errore
        return;
      }
    }
    this.insertDatiFlusso(doc, datiAggiornamento, true);
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getFase();

    boolean continua = true;
    
    if (!super.existsLotto(fase)) {
      logger.error("La richiesta di rettifica inizio contratto di un lotto non presente in DB (cig  = "
          + fase.getW3FACIG() + ") ");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di una Fase iniziale di esecuzione del contratto con lotto non esistente in banca dati");
      continua = false;
    }
    
    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di rettifica inizio contratto di un lotto non aggiudicato in DB (cig = "
          + fase.getW3FACIG() + ") ");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di una Fase iniziale di esecuzione del contratto di un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && !this.existsFase(fase, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA)) {
      logger.error("La richiesta di rettifica di un lotto con Fase iniziale di esecuzione del contratto non esistente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di una Fase iniziale di esecuzione del contratto non esistente in banca dati");
      continua = false;
    }

    if (continua) {
    	this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    }
  }

  private void insertDatiFlusso(
      RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document doc,
      DataColumnContainer datiAggiornamento, boolean primoInvio)
  throws SQLException, GestoreException {

    Tab19Type tab19 = doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getTab19();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getFase();
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

    if (primoInvio) {
	    String idSchedaLocale = null;
	    String idSchedaSimog = null;
	    if (tab19.isSetW9FASCHLOC()) {
	    	idSchedaLocale = tab19.getW9FASCHLOC();
	  	}
	  	if (tab19.isSetW9FASCHSIM()) {
	  		idSchedaSimog = tab19.getW9FASCHSIM();
	  	}
	  	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio, idSchedaLocale, idSchedaSimog);
    } else { 
    	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);
    }

    DataColumnContainer datiIniz = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9INIZ.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
        new DataColumn("W9INIZ.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott))});

    datiIniz.addColumn("W9INIZ.NUM_INIZ", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()));
    datiIniz.addColumn("W9INIZ.NUM_APPA", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));
    
    if (tab19.isSetW3DATASTI()) {
      datiIniz.addColumn("W9INIZ.DATA_STIPULA", JdbcParametro.TIPO_DATA,
          tab19.getW3DATASTI().getTime());
    }
    if (tab19.isSetW3DATAESE()) {
      datiIniz.addColumn("W9INIZ.DATA_ESECUTIVITA", JdbcParametro.TIPO_DATA,
          tab19.getW3DATAESE().getTime());
    }
    datiIniz.addColumn("W9INIZ.IMPORTO_CAUZIONE", JdbcParametro.TIPO_DECIMALE,
        tab19.getW3ICAUZIO());

    if (tab19.isSetW9ININCVIGIL()) {
      datiIniz.addColumn("W9INIZ.INCONTRI_VIGIL", JdbcParametro.TIPO_NUMERICO,
          new Long(tab19.getW9ININCVIGIL().toString()));
    }

    if (tab19.isSetW3DPROGES()) {
      datiIniz.addColumn("W9INIZ.DATA_INI_PROG_ESEC", JdbcParametro.TIPO_DATA,
          tab19.getW3DPROGES().getTime());
    }
    if (tab19.isSetW3DATAAPP()) {
      datiIniz.addColumn("W9INIZ.DATA_APP_PROG_ESEC", JdbcParametro.TIPO_DATA,
          tab19.getW3DATAAPP().getTime());
    }
    datiIniz.addColumn("W9INIZ.FLAG_FRAZIONATA", JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab19.getW3FLAGFRA()).toString());
    if (tab19.isSetW3DVERBCO()) {
      datiIniz.addColumn("W9INIZ.DATA_VERBALE_CONS", JdbcParametro.TIPO_DATA,
          tab19.getW3DVERBCO().getTime());
    }
    if (tab19.isSetW3DVERBDE()) {
      datiIniz.addColumn("W9INIZ.DATA_VERBALE_DEF", JdbcParametro.TIPO_DATA,
          tab19.getW3DVERBDE().getTime());
    }
    datiIniz.addColumn("W9INIZ.FLAG_RISERVA", JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab19.getW3RISERVA()).toString());
    if (tab19.isSetW3DVERBIN()) {
      datiIniz.addColumn("W9INIZ.DATA_VERB_INIZIO", JdbcParametro.TIPO_DATA,
          tab19.getW3DVERBIN().getTime());
    }
    if (tab19.isSetW3DTERM1()) {
      datiIniz.addColumn("W9INIZ.DATA_TERMINE", JdbcParametro.TIPO_DATA,
          tab19.getW3DTERM1().getTime());
    }
    datiIniz.insert("W9INIZ", this.sqlManager);

    // Scheda sicurezza
    if (doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().isSetTab8Bis2()) {
      Tab8Bis2Type tab8bis2 =  doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getTab8Bis2();
      DataColumnContainer dccSicurezza = new DataColumnContainer(
          new DataColumn[] {
              new DataColumn("W9SIC.CODGARA", new JdbcParametro(
                  JdbcParametro.TIPO_NUMERICO, codGara)),
                  new DataColumn("W9SIC.CODLOTT", new JdbcParametro(
                      JdbcParametro.TIPO_NUMERICO, codLott)),
                      new DataColumn("W9SIC.NUM_SIC", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });

      dccSicurezza.addColumn("W9SIC.NUM_INIZ", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()));
      
      if (tab8bis2.isSetW3PIANSCIC()) {
        dccSicurezza.addColumn("W9SIC.PIANSCIC", JdbcParametro.TIPO_TESTO,
            this.getFlagSNDB(tab8bis2.getW3PIANSCIC()).toString());
      }
      if (tab8bis2.isSetW3DIROPE()) {
        dccSicurezza.addColumn("W9SIC.DIROPE", JdbcParametro.TIPO_TESTO,
            this.getFlagSNDB(tab8bis2.getW3DIROPE()).toString());
      }
      if (tab8bis2.isSetW3SITUTOR()) {
        dccSicurezza.addColumn("W9SIC.TUTOR", JdbcParametro.TIPO_TESTO,
            this.getFlagSNDB(tab8bis2.getW3SITUTOR()));
      }

      dccSicurezza.insert("W9SIC", this.sqlManager);
    }

    Tab101Type tab101 = null;
    int k = 0;
    for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getListaTab191().sizeOfTab191Array(); i++) {
      tab101 = doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getListaTab191().getTab191Array(i);
      if (tab101.getW3SEZIONE().equals("IN")) {
        // Arch2Type arch2 = tab101.getArch2();
        String pkTecni = this.getTecnico(tab101.getArch2(), pkUffint);
        k = k + 1;
        DataColumnContainer datiInca = new DataColumnContainer(new DataColumn[] {
            new DataColumn("W9INCA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
            new DataColumn("W9INCA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)) });
        datiInca.addColumn("W9INCA.NUM", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()));
        datiInca.addColumn("W9INCA.NUM_INCA", JdbcParametro.TIPO_NUMERICO, new Long(k));
        datiInca.addColumn("W9INCA.SEZIONE", JdbcParametro.TIPO_TESTO, "IN");
        datiInca.addColumn("W9INCA.CODTEC", JdbcParametro.TIPO_TESTO, pkTecni);

        datiInca.addColumn("W9INCA.ID_RUOLO", JdbcParametro.TIPO_NUMERICO,
            new Long(tab101.getW3IDRUOLO().toString()));
        if (tab101.isSetW3CIGPROG()) {
          datiInca.addColumn("W9INCA.CIG_PROG_ESTERNA",
              JdbcParametro.TIPO_TESTO, tab101.getW3CIGPROG());
        }
        if (tab101.isSetW3DATAAFF()) {
          datiInca.addColumn("W9INCA.DATA_AFF_PROG_ESTERNA",
              JdbcParametro.TIPO_DATA, tab101.getW3DATAAFF().getTime());
        }
        if (tab101.isSetW3DATACON()) {
          datiInca.addColumn("W9INCA.DATA_CONS_PROG_ESTERNA",
              JdbcParametro.TIPO_DATA, tab101.getW3DATACON().getTime());
        }

        datiInca.insert("W9INCA", this.sqlManager);
      }
    }

    Tab192Type tab192 = doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getTab192();
    DataColumnContainer datiPues = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9PUES.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
        new DataColumn("W9PUES.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)) });

    datiPues.addColumn("W9PUES.NUM_INIZ", JdbcParametro.TIPO_NUMERICO,  new Long(fase.getW3NUM5()));
    datiPues.addColumn("W9PUES.NUM_PUES", JdbcParametro.TIPO_NUMERICO,  new Long(1));
    
    datiPues.setValue("W9PUES.CODGARA", codGara);
    datiPues.setValue("W9PUES.CODLOTT", codLott);
    datiPues.setValue("W9PUES.NUM_INIZ", new Long(fase.getW3NUM5()));
    datiPues.setValue("W9PUES.NUM_PUES", new Long(1));
    //datiPues.setOriginalValue("W9PUES.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara));
    //datiPues.setOriginalValue("W9PUES.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott));
    //datiPues.setOriginalValue("W9PUES.NUM_INIZ", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5())));
    //datiPues.setOriginalValue("W9PUES.NUM_PUES", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
    
    datiPues.getColumn("W9PUES.CODGARA").setChiave(true);
    datiPues.getColumn("W9PUES.CODLOTT").setChiave(true);
    datiPues.getColumn("W9PUES.NUM_INIZ").setChiave(true);
    datiPues.getColumn("W9PUES.NUM_PUES").setChiave(true);
    
    if (tab192.isSetW3GUCE2()) {
      datiPues.addColumn("W9PUES.DATA_GUCE", JdbcParametro.TIPO_DATA,
          tab192.getW3GUCE2().getTime());
    }
    if (tab192.isSetW3GURI2()) {
      datiPues.addColumn("W9PUES.DATA_GURI", JdbcParametro.TIPO_DATA,
          tab192.getW3GURI2().getTime());
    }
    if (tab192.isSetW3ALBO2()) {
      datiPues.addColumn("W9PUES.DATA_ALBO", JdbcParametro.TIPO_DATA,
          tab192.getW3ALBO2().getTime());
    }
    if (tab192.isSetW3NAZ2()) {
      datiPues.addColumn("W9PUES.QUOTIDIANI_NAZ",
          JdbcParametro.TIPO_NUMERICO, new Long(tab192.getW3NAZ2()));
    }
    if (tab192.isSetW3REG2()) {
      datiPues.addColumn("W9PUES.QUOTIDIANI_REG",
          JdbcParametro.TIPO_NUMERICO, new Long(tab192.getW3REG2()));
    }
    datiPues.addColumn("W9PUES.PROFILO_COMMITTENTE", JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab192.getW3PROFILO2()).toString());

    datiPues.addColumn("W9PUES.SITO_MINISTERO_INF_TRASP", JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab192.getW3MIN2()).toString());

    datiPues.addColumn("W9PUES.SITO_OSSERVATORIO_CP", JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab192.getW3OSS2()).toString());

    if (((Long) this.sqlManager.getObject("select count(*) from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=? and NUM_PUES=1", 
        new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) }) ).intValue() > 0) {
      this.sqlManager.update("delete from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=? and NUM_PUES=1", new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) });
    }
    datiPues.insert("W9PUES", this.sqlManager);
    
    // Misure aggiuntive e migliorative per la sicurezza (W9MISSIC)
    if (doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().isSetTab194()) {
      Tab13Type tab13 = doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getTab194();
        
      DataColumnContainer datiW9Missic = new DataColumnContainer(new DataColumn[] {
          new DataColumn("W9MISSIC.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
          new DataColumn("W9MISSIC.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
          new DataColumn("W9MISSIC.NUM_MISS", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5())))});
      datiW9Missic.addColumn("W9MISSIC.NUM_INIZ", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()));
      datiW9Missic.addColumn("W9MISSIC.DESCMIS", JdbcParametro.TIPO_TESTO, tab13.getW3MSDESCMIS());
      
      String pkImpresa = this.gestioneImpresa(tab13.getArch3(), pkUffint);
      datiW9Missic.addColumn("W9MISSIC.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);
      
      datiW9Missic.insert("W9MISSIC", this.sqlManager);
    }

    // Allegati alle misure aggiuntive e migliorative per la sicurezza (Dati di W9DOCGARA)
    if (doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().sizeOfTab1941Array() > 0) {
      Tab41Type[] arrayTab41 = doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getTab1941Array();

      for (int iu = 0; iu < arrayTab41.length; iu++) {
        Tab41Type tab41 = arrayTab41[iu];

        datiAggiornamento.addColumn("W9DOCFASE.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
        datiAggiornamento.addColumn("W9DOCFASE.CODLOTT", JdbcParametro.TIPO_NUMERICO, codLott);
        datiAggiornamento.addColumn("W9DOCFASE.FASE_ESECUZIONE", JdbcParametro.TIPO_NUMERICO, new Long(CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA));
        datiAggiornamento.addColumn("W9DOCFASE.NUM_FASE", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()));
        datiAggiornamento.addColumn("W9DOCFASE.NUMDOC", JdbcParametro.TIPO_NUMERICO, new Long(iu + 1));
        datiAggiornamento.addColumn("W9DOCFASE.TITOLO", JdbcParametro.TIPO_TESTO, tab41.getW9DGTITOLO());
        
        ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
        try {
          fileAllegato.write(tab41.getFile());
        } catch (IOException e) {
          throw new GestoreException(
              "Errore durante la lettura del file allegato presente nella richiesta XML", null, e);
        }
        datiAggiornamento.addColumn("W9DOCFASE.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, fileAllegato);
        datiAggiornamento.insert("W9DOCFASE", this.sqlManager);
      }
    }
    
    this.aggiornaStatoExportLotto(codGara, codLott);
    
    // se la procedura di aggiornamento e' andata a buon fine,
    // si aggiorna lo stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

  private void deleteDatiFlusso(FaseEstesaType fase) throws SQLException, GestoreException {
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];

    this.sqlManager.update(
        "delete from w9iniz where codgara = ? and codlott = ? and num_iniz = ?",
        new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) }, 1);
    this.sqlManager.update(
        "delete from w9inca where codgara = ? and codlott = ? and num = ? and sezione= 'IN'",
        new Object[] { codGara, codLott, new Long(fase.getW3NUM5())});
    this.sqlManager.update(
        "delete from w9pues where codgara = ? and codlott = ? and num_iniz = ?",
        new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) });
    this.sqlManager.update(
        "delete from w9sic where codgara = ? and codlott = ? and num_iniz = ?",
        new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) });
    this.sqlManager.update(
        "delete from w9missic where codgara = ? and codlott = ? and num_iniz = ?",
        new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) });
    this.sqlManager.update(
        "delete from w9docfase where codgara = ? and codlott = ? and fase_esecuzione = ? and num_fase = ?",
        new Object[] { codGara, codLott, new Long(CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA), new Long(fase.getW3NUM5()) });
  }

  private void manageIntegrazioneORettifica(
      RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document doc,
      DataColumnContainer datiAggiornamento) throws GestoreException,
      SQLException {
    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false);
  }
}
