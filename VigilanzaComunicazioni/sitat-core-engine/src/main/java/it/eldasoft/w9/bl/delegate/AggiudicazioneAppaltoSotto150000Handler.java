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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document;
import it.toscana.rete.rfc.sitat.types.Tab101Type;
import it.toscana.rete.rfc.sitat.types.Tab81Type;
import it.toscana.rete.rfc.sitat.types.Tab8Type;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della aggiudicazione appalto sotto 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class AggiudicazioneAppaltoSotto150000Handler extends
AbstractRequestHandler {

  Logger logger = Logger.getLogger(AggiudicazioneAppaltoSotto150000Handler.class);

  @Override
  protected String getNomeFlusso() {
    return "Fase semplificata di aggiudicazione appalto";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_AGGIUDICAZIONE_APPALTO_SOTTO_150000;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException,
      SQLException {
    RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di aggiudicazione semplificata di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && this.existsAppalto(fase)) {
      logger.error("La richiesta di primo invio di un lotto gia' aggiudicato in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Primo invio di aggiudicazione semplificata per un lotto gia' aggiudicato in banca dati");
      continua = false;
    }

    // warnings
    if (continua && !ignoreWarnings) {
    	if (! this.isFaseVisualizzabile(fase, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE)) {
        logger.error("La richiesta di primo invio aggiudicazione semplificata non prevista (not S2 and not AAQ and EA)");
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
            "Primo invio di aggiudicazione semplificata non prevista");
        continua = false;
      }
    }

    // SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI SI
    // AGGIORNA IL DB
    if (continua) {
    	this.insertDatiFlusso(doc, datiAggiornamento, true, null);
    }
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di rettifica di aggiudicazione semplificata di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di aggiudicazione semplificata di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsFase(fase, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE)) {
      logger.error("La richiesta di rettifica aggiudicazione semplificata di un lotto non aggiudicato in DB (CIG = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
      		"Rettifica di aggiudicazione semplificata di un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua) {
    	this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    }
  }

  /**
   * Effettua l'inserimento vero e proprio dei dati presenti nel flusso.
   * 
   * @param doc
   *        documento XML della richiesta
   * @param datiAggiornamento
   *        container con i dati del flusso
   * @param codcui codcui dell'aggiudicazione ritornato da simog
   * @throws SQLException
   * @throws GestoreException
   */
  private void insertDatiFlusso(
      RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc,
      DataColumnContainer datiAggiornamento, boolean primoInvio, final String codcui) throws SQLException,
      GestoreException {

    Tab8Type tab8 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getTab8();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getFase();

    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

    if (primoInvio) {
	    String idSchedaLocale = null;
	    String idSchedaSimog = null;
	    if (tab8.isSetW9FASCHLOC()) {
	    	idSchedaLocale = tab8.getW9FASCHLOC();
	  	}
	  	if (tab8.isSetW9FASCHSIM()) {
	  		idSchedaSimog = tab8.getW9FASCHSIM();
	  	}
	  	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio, idSchedaLocale, idSchedaSimog);
    } else { 
    	this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);
    }

    // predisposizione dati W9APPA (tab8): i dati facoltativi vengono testati
    // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
    DataColumnContainer dccAppalto = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9APPA.CODGARA", new JdbcParametro(
            JdbcParametro.TIPO_NUMERICO, codGara)),
            new DataColumn("W9APPA.CODLOTT", new JdbcParametro(
                JdbcParametro.TIPO_NUMERICO, codLott)),
                new DataColumn("W9APPA.NUM_APPA", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });
    if (StringUtils.isNotEmpty(codcui)) {
  		dccAppalto.addColumn("W9APPA.CODCUI", JdbcParametro.TIPO_TESTO, codcui);
  	} else if (primoInvio && tab8.isSetW9APPACODCUI()) {
   		dccAppalto.addColumn("W9APPA.CODCUI", JdbcParametro.TIPO_TESTO, tab8.getW9APPACODCUI());
    }
    dccAppalto.addColumn("W9APPA.DATA_VERB_AGGIUDICAZIONE",
        JdbcParametro.TIPO_DATA, tab8.getW3DVERB().getTime());
    if (tab8.isSetW3IMPAGGI()) {
      dccAppalto.addColumn("W9APPA.IMPORTO_AGGIUDICAZIONE",
          JdbcParametro.TIPO_DECIMALE, tab8.getW3IMPAGGI());
    }
    dccAppalto.addColumn("W9APPA.IMPORTO_DISPOSIZIONE",
        JdbcParametro.TIPO_DECIMALE, tab8.getW3IDISPOS());
    if (tab8.isSetW3ASTAELE()) {
      dccAppalto.addColumn("W9APPA.ASTA_ELETTRONICA", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab8.getW3ASTAELE()).toString());
    }
    if (tab8.isSetW3PERCRIB()) {
      dccAppalto.addColumn("W9APPA.PERC_RIBASSO_AGG",
          JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab8.getW3PERCRIB())).toString());
    }
    if (tab8.isSetW3PERCOFF()) {
      dccAppalto.addColumn("W9APPA.PERC_OFF_AUMENTO",
          JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab8.getW3PERCOFF())).toString());
    }
    dccAppalto.addColumn("W9APPA.IMPORTO_COMPL_APPALTO",
        JdbcParametro.TIPO_DECIMALE, tab8.getW3ICAPPA());
    dccAppalto.addColumn("W9APPA.IMPORTO_COMPL_INTERVENTO",
        JdbcParametro.TIPO_DECIMALE, tab8.getW3ICINTE());
    dccAppalto.addColumn("W9APPA.IMPORTO_SUBTOTALE",
        JdbcParametro.TIPO_DECIMALE, tab8.getW3ISUBTOT());
    dccAppalto.addColumn("W9APPA.IMPORTO_ATTUAZIONE_SICUREZZA",
        JdbcParametro.TIPO_DECIMALE, tab8.getW3IATTSIC());


    if (tab8.isSetW9APTIPAT()) {
      dccAppalto.addColumn("W9APPA.TIPO_ATTO", JdbcParametro.TIPO_NUMERICO, 
        Long.parseLong(tab8.getW9APTIPAT().toString()));
    }
    if (tab8.isSetW9APDATAT()) {
      dccAppalto.addColumn("W9APPA.DATA_ATTO", JdbcParametro.TIPO_DATA,
        tab8.getW9APDATAT().getTime());
    }
    if (tab8.isSetW9APNUMAT()) {
      dccAppalto.addColumn("W9APPA.NUMERO_ATTO", JdbcParametro.TIPO_TESTO,
        tab8.getW9APNUMAT());
    }
    
    if (tab8.isSetW9APIVA()) {
      dccAppalto.addColumn("W9APPA.IVA", JdbcParametro.TIPO_DECIMALE,
          new Double(new Float(tab8.getW9APIVA())).toString());
    }

    if (tab8.isSetW9APIMPIVA()) {
      dccAppalto.addColumn("W9APPA.IMPORTO_IVA", JdbcParametro.TIPO_DECIMALE,
          tab8.getW9APIMPIVA());
    }
    
    dccAppalto.addColumn("W9APPA.DATA_STIPULA",
        JdbcParametro.TIPO_DATA, tab8.getW9APDATASTI().getTime());
    dccAppalto.addColumn("W9APPA.DURATA_CON",
        JdbcParametro.TIPO_NUMERICO, new Long(tab8.getW9APDURCON()));
    
    dccAppalto.insert("W9APPA", this.sqlManager);

    String dbCodCuiLotto = (String) this.sqlManager.getObject(
    		"select CODCUI from W9LOTT where CODGARA=? and CODLOTT=?", new Object[] { codGara, codLott } );
    
    if (StringUtils.isEmpty(dbCodCuiLotto) && primoInvio && tab8.isSetW9APPACODCUI()) {
    	this.sqlManager.update("update W9LOTT set CODCUI=? where CODGARA=? and CODLOTT=?", 
    			new Object[] { tab8.getW9APPACODCUI(), codGara, codLott } );
    }
    
    // ciclo sulle imprese aggiudicatarie (W9AGGI)
    Tab81Type tab81 = null;
    for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getListaTab81().sizeOfTab81Array(); i++) {
      tab81 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getListaTab81().getTab81Array(i);
      // popolamento dei dati per l'inserimento di un intervento (tab81): si usa
      // un altro container dato che non e' un singolo elemento ma una lista di
      // elementi da inserire nella medesima tabella

      // si costruisce il container inserendo i campi chiave dell'entita' da
      // salvare
      DataColumnContainer dccAggiudicazione = new DataColumnContainer(
          new DataColumn[] {
              new DataColumn("W9AGGI.CODGARA", new JdbcParametro(
                  JdbcParametro.TIPO_NUMERICO, codGara)),
                  new DataColumn("W9AGGI.CODLOTT", new JdbcParametro(
                      JdbcParametro.TIPO_NUMERICO, codLott)),
                      new DataColumn("W9AGGI.NUM_APPA", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))),
                          new DataColumn("W9AGGI.NUM_AGGI", new JdbcParametro(
                              JdbcParametro.TIPO_NUMERICO, i + 1)) });

      // si aggiungono gli altri dati al container: per i campi non obbligatori
      // da tracciato si effettua il test con il corrispondente metodo isSetXXX

      dccAggiudicazione.addColumn("W9AGGI.ID_TIPOAGG", JdbcParametro.TIPO_NUMERICO,
          Long.parseLong(tab81.getW3IDTIPOA().toString()));
      if (tab81.isSetW3RUOLO()) {
        dccAggiudicazione.addColumn("W9AGGI.RUOLO", JdbcParametro.TIPO_NUMERICO,
            Long.parseLong(tab81.getW3RUOLO().toString()));
      }
      
      if (tab81.isSetW3FLAGAVV()) {
        dccAggiudicazione.addColumn("W9AGGI.FLAG_AVVALIMENTO", JdbcParametro.TIPO_TESTO,
            tab81.getW3FLAGAVV().toString());
      }

      String pkImpresa = this.gestioneImpresa(tab81.getArch3(), pkUffint);

      dccAggiudicazione.addColumn("W9AGGI.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);

      if (tab81.isSetArch3Avv()) {
        String pkImpresaAvv = this.gestioneImpresa(tab81.getArch3Avv(), pkUffint);
        dccAggiudicazione.addColumn("W9AGGI.CODIMP_AUSILIARIA",
            JdbcParametro.TIPO_TESTO, pkImpresaAvv);
      }

      if (tab81.isSetW3AGIDGRP()) {
    	  dccAggiudicazione.addColumn("W9AGGI.ID_GRUPPO",
    		JdbcParametro.TIPO_NUMERICO, new Long(tab81.getW3AGIDGRP()));
      }
      
      if (tab81.isSetW3AGIMPAGGI()) {
    	  dccAggiudicazione.addColumn("W9AGGI.IMPORTO_AGGIUDICAZIONE",
    		JdbcParametro.TIPO_DECIMALE, new Double(tab81.getW3AGIMPAGGI()));
      }
      
      if (tab81.isSetW3AGPERCRIB()) {
    	  dccAggiudicazione.addColumn("W9AGGI.PERC_RIBASSO_AGG",
    		JdbcParametro.TIPO_DECIMALE, new Double(tab81.getW3AGPERCRIB()));
      }
      
      if (tab81.isSetW3AGPERCOFF()) {
    	  dccAggiudicazione.addColumn("W9AGGI.PERC_OFF_AUMENTO",
    		JdbcParametro.TIPO_DECIMALE, new Double(tab81.getW3AGPERCOFF()));
      }
      
      // si richiama l'inserimento dell'occorrenza di tab81 (W9AGGI)
      dccAggiudicazione.insert("W9AGGI", this.sqlManager);
    }

    Tab101Type tab101 = null;
    int k = 0;
    for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getListaTab82().sizeOfTab82Array(); i++) {
      tab101 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getListaTab82().getTab82Array(i);

      // si costruisce il container inserendo i campi chiave dell'entita' da salvare

      if (tab101.getW3SEZIONE().equals("RE") || tab101.getW3SEZIONE().equals("RS")) {
        k = k + 1;
        DataColumnContainer dccIncarichi = new DataColumnContainer(
            new DataColumn[] {
                new DataColumn("W9INCA.CODGARA", new JdbcParametro(
                    JdbcParametro.TIPO_NUMERICO, codGara)),
                    new DataColumn("W9INCA.CODLOTT", new JdbcParametro(
                        JdbcParametro.TIPO_NUMERICO, codLott)),
                        new DataColumn("W9INCA.NUM", new JdbcParametro(
                            JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))),
                            new DataColumn("W9INCA.NUM_INCA", new JdbcParametro(
                                JdbcParametro.TIPO_NUMERICO, new Long(k))),
                                new DataColumn("W9INCA.SEZIONE", new JdbcParametro(
                                    JdbcParametro.TIPO_TESTO, tab101.getW3SEZIONE())) });

        // si aggiungono gli altri dati al container: per i campi non obbligatori
        // da tracciato si effettua il test con il corrispondente metodo isSetXXX

        String pkTecnico = this.getTecnico(tab101.getArch2(), pkUffint);
        dccIncarichi.addColumn("W9INCA.CODTEC", JdbcParametro.TIPO_TESTO, pkTecnico);

        dccIncarichi.addColumn("W9INCA.ID_RUOLO", JdbcParametro.TIPO_NUMERICO,
            new Long(tab101.getW3IDRUOLO().toString()));
        if (tab101.isSetW3CIGPROG()) {
          dccIncarichi.addColumn("W9INCA.CIG_PROG_ESTERNA",
              JdbcParametro.TIPO_TESTO, tab101.getW3CIGPROG());
        }
        if (tab101.isSetW3DATAAFF()) {
          dccIncarichi.addColumn("W9INCA.DATA_AFF_PROG_ESTERNA",
              JdbcParametro.TIPO_DATA, tab101.getW3DATAAFF().getTime());
        }
        if (tab101.isSetW3DATACON()) {
          dccIncarichi.addColumn("W9INCA.DATA_CONS_PROG_ESTERNA",
              JdbcParametro.TIPO_DATA, tab101.getW3DATACON().getTime());
        }
        // si richiama l'inserimento dell'occorrenza di tab101 (W9INCA)
        dccIncarichi.insert("W9INCA", this.sqlManager);
      }
    }

    this.aggiornaStatoExportLotto(codGara, codLott);
    
    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo
    // stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
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
      RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc,
      DataColumnContainer datiAggiornamento) throws GestoreException,
      SQLException {
	  String codcui = this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false, codcui);
  }

  /**
   * Elimina tutte le occorrenze dal DB dei dati da aggiornare (W9APPA, W9AGGI).
   * 
   * @param fase
   *        elemento fase del flusso
   * @return ritorna il codice cui ritornato dalla pubblicazione in simog
   * @throws SQLException
   */
  private String deleteDatiFlusso(FaseEstesaType fase) throws SQLException, GestoreException {
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    String codcui = (String) this.sqlManager.getObject("select codcui from w9appa where codgara = ? and codlott = ? and num_appa = ?",
    		new Object[] {codGara, codLott, new Long(fase.getW9NUMAPPA()) });
    this.sqlManager.update("delete from w9appa where codgara = ? and codlott = ? and num_appa = ?",
        new Object[] { codGara, codLott, new Long(fase.getW9NUMAPPA()) }, 1);
    this.sqlManager.update("delete from w9aggi where codgara = ? and codlott = ? and num_appa = ?",
        new Object[] { codGara, codLott, new Long(fase.getW9NUMAPPA()) });

    this.sqlManager.update(
        "delete from w9inca where codgara = ? and codlott = ? and num = ? and (sezione = ? or sezione = ?)",
        new Object[] { codGara, codLott, new Long(fase.getW3NUM5()), "RE", "RS" });
    return codcui;
  }

}