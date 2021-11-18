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
import it.toscana.rete.rfc.sitat.types.ListaTab51Type;
import it.toscana.rete.rfc.sitat.types.ListaTab52Type;
import it.toscana.rete.rfc.sitat.types.ListaTab53Type;
import it.toscana.rete.rfc.sitat.types.ListaTab54Type;
import it.toscana.rete.rfc.sitat.types.ListaTab55Type;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument;
import it.toscana.rete.rfc.sitat.types.Tab101Type;
import it.toscana.rete.rfc.sitat.types.Tab184Type;
import it.toscana.rete.rfc.sitat.types.Tab41Type;
import it.toscana.rete.rfc.sitat.types.Tab4Type;
import it.toscana.rete.rfc.sitat.types.Tab51Type;
import it.toscana.rete.rfc.sitat.types.Tab52Type;
import it.toscana.rete.rfc.sitat.types.Tab53Type;
import it.toscana.rete.rfc.sitat.types.Tab54Type;
import it.toscana.rete.rfc.sitat.types.Tab55Type;
import it.toscana.rete.rfc.sitat.types.Tab5Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'anagrafica lotto e/o gara.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class AnagraficaLottoGaraHandler extends AbstractRequestHandler {

  //TODO Classe da rimuovere
  
  Logger logger = Logger.getLogger(AnagraficaLottoGaraHandler.class);

  @Override
  protected String getNomeFlusso() {
    return "Anagrafica gara/lotti";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_ANAGRAFICA_LOTTO_GARA;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc = (RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc = (RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument) documento;
    return (FaseEstesaType) doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
    throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc = (RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument) documento;
    Tab4Type tab4 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab4();

    boolean isPrimoInvioConAnagraficaSemplificataEsistente = false;
    
    if (UtilitySITAT.existsGara(tab4.getW3IDGARA(), this.sqlManager)) {
      // Se la gara esiste gia' nella base dati, allora si controlla che sia una
      // anagrafica semplificata. Se lo e', si agisce come rettifica/integrazione,
      // altrimenti si da errore perche' gara gia' esistente
      
      Long codGara = UtilitySITAT.getCodGaraByIdAvGara(tab4.getW3IDGARA(), this.sqlManager);
      
      if (UtilitySITAT.existsFlussoAnagrafica(codGara, 
          CostantiW9.ANAGRAFICA_GARA_LOTTI, this.sqlManager)) {

        logger.error("La richiesta di primo invio ha un id (IDAVGARA=" + tab4.getW3IDGARA()
            + ") di una anagrafica gara esistente in DB");
        
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
            "Primo invio di una anagrafica gara gia' esistente in banca dati");
        return;
      } else {
        isPrimoInvioConAnagraficaSemplificataEsistente = true;
      }
    }

    for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().sizeOfTab5TypeArray(); i++) {
      Tab5Type tab5 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().getTab5TypeArray(i);
      if (UtilitySITAT.existsLotto(tab5.getW3CIG(), this.sqlManager)) {
        logger.error("Il lotto " + (i + 1) + " della richiesta ha un codice cig ("
            + tab5.getW3CIG() + ") gia' presente nella base dati.");
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
            "Primo Invio di anagrafica lotto gara con lotto non valido (codice cig gia' presente)"
            + tab5.getW3CIG());
        return;
      }
    }
    
    if (isPrimoInvioConAnagraficaSemplificataEsistente) {
      this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    } else {
      this.insertDatiFlusso(doc, datiAggiornamento, true, null, null, new Long(0));
    }
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc = (RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument) documento;
    Tab4Type tab4 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab4();

    if (tab4.getW3IDGARA() == null || tab4.getW3IDGARA().equals("")) {
      logger.error("La richiesta di rettifica non ha un id valorizzato");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di una anagrafica gara che non ha un id valorizzato");
      return;
    }

    if (!UtilitySITAT.existsGara(tab4.getW3IDGARA(), this.sqlManager)) {
      logger.error("La richiesta di rettifica ha un id ("
          + tab4.getW3IDGARA()
          + ") di una anagrafica gara che non esiste in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di una anagrafica gara che non esistente in banca dati");
      return;
    }

    for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().sizeOfTab5TypeArray(); i++) {
      Tab5Type tab5 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().getTab5TypeArray(i);
      if (tab5.getW3CIG() == null
          || tab5.getW3CIG().equalsIgnoreCase("in attesa")) {
        logger.error("La richiesta di rettifica ha un lotto in attesa");
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
            "La Rettifica ha un lotto in attesa");
        return;
      }
    }
    this.manageIntegrazioneORettifica(doc, datiAggiornamento);
  }

  /**
   * Inserimento dell'anagrafica gara e lotti.
   * 
   * @param doc
   * @param datiAggiornamento
   * @param primoInvio
   * @param codGaraDeleted
   * @param hmCig
   * @throws SQLException
   * @throws GestoreException
   */
  private void insertDatiFlusso(
      RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc,
      DataColumnContainer datiAggiornamento, boolean primoInvio,
      Long codGaraDeleted, HashMap<String, HashMap<String, Object>> hmCig,
      Long maxCodLott) throws SQLException, GestoreException {

    // Gare
    Tab4Type tab4 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab4();

    String pkUffint = this.getStazioneAppaltante(tab4.getArch1());

    Long pkIdcc = null;
    if (tab4.isSetArch5()) {
      pkIdcc = this.getCentroCosto(tab4.getArch5(), pkUffint);
    }

    Long pkIduff = null;
    if (tab4.isSetArch6()) {
    	pkIduff = this.getUfficio(tab4.getArch6(), pkUffint);
    }
    
    Long codGara = new Long(
        this.genChiaviManager.getMaxId("W9GARA", "CODGARA") + 1);
    if (!primoInvio) {
      codGara = codGaraDeleted;
    }

    String codrup = this.insertDatiGara(doc, datiAggiornamento, primoInvio, tab4, 
        pkUffint, pkIdcc, codGara, pkIduff);

    this.insertDatiLotto(doc, primoInvio, hmCig, pkUffint, codGara, codrup, maxCodLott);
    
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

  /**
   * Inserimento dei dati dell'anagrafica di gara (W9GARA, W9DOCGARA, W9PUBB
   * 
   * @param doc
   * @param datiAggiornamento
   * @param primoInvio
   * @param tab4
   * @param pkUffint
   * @param pkIdcc
   * @param codGara
   * @param pkIduff
   * @return
   * @throws GestoreException
   * @throws SQLException
   */
  private String insertDatiGara(
      RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc,
      DataColumnContainer datiAggiornamento, boolean primoInvio, Tab4Type tab4,
      String pkUffint, Long pkIdcc, Long codGara, Long pkIduff) throws GestoreException,
      SQLException {
    datiAggiornamento.addColumn("W9GARA.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
    datiAggiornamento.addColumn("W9GARA.OGGETTO", JdbcParametro.TIPO_TESTO, tab4.getW3OGGETTO1());

    // *** Gestione del codice per IDAVGARA ***
    datiAggiornamento.addColumn("W9GARA.IDAVGARA", JdbcParametro.TIPO_TESTO, tab4.getW3IDGARA());

    if (tab4.isSetW3IGARA()) {
      datiAggiornamento.addColumn("W9GARA.IMPORTO_GARA",
          JdbcParametro.TIPO_DECIMALE, tab4.getW3IGARA());
    }
    datiAggiornamento.addColumn("W9GARA.NLOTTI", JdbcParametro.TIPO_NUMERICO,
        new Long(tab4.getW3NLOTTI()));

    datiAggiornamento.addColumn("W9GARA.FLAG_ENTE_SPECIALE", JdbcParametro.TIPO_TESTO,
        tab4.getW9GAFLAGENT().toString());
    if (tab4.isSetW9GAMODIND()) {
      datiAggiornamento.addColumn("W9GARA.ID_MODO_INDIZIONE", JdbcParametro.TIPO_NUMERICO,
          Long.parseLong(tab4.getW9GAMODIND().toString()));
    }
    
    datiAggiornamento.addColumn("W9GARA.TIPO_APP", JdbcParametro.TIPO_NUMERICO,
        Long.parseLong(tab4.getW3TIPOAPP().toString()));
    datiAggiornamento.addColumn("W9GARA.CODEIN", JdbcParametro.TIPO_TESTO, pkUffint);
    datiAggiornamento.addColumn("W9GARA.FLAG_SA_AGENTE",
        JdbcParametro.TIPO_TESTO,
        this.getFlagSNDB(tab4.getW3FLAGSA()).toString());
    if (tab4.isSetW3IDTIPOL()) {
      datiAggiornamento.addColumn("W9GARA.ID_TIPOLOGIA_SA",
          JdbcParametro.TIPO_NUMERICO,
          Long.parseLong(tab4.getW3IDTIPOL().toString()));
    }
    if (tab4.isSetW3GASAAGENTE()) {
      datiAggiornamento.addColumn("W9GARA.DENOM_SA_AGENTE",
          JdbcParametro.TIPO_TESTO, tab4.getW3GASAAGENTE());
    }
    if (tab4.isSetW3GACFAGENTE()) {
      datiAggiornamento.addColumn("W9GARA.CF_SA_AGENTE",
          JdbcParametro.TIPO_TESTO, tab4.getW3GACFAGENTE());
    }

    if (tab4.isSetW9GATIPROC()) {
      datiAggiornamento.addColumn("W9GARA.TIPOLOGIA_PROCEDURA",
          JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab4.getW9GATIPROC().toString()));
    }
    if (tab4.isSetW9GASTIPULA()) {
      datiAggiornamento.addColumn("W9GARA.FLAG_CENTRALE_STIPULA",
          JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab4.getW9GASTIPULA()).toString());
    }
    if (tab4.isSetW9GACIGAQ()) {
      datiAggiornamento.addColumn("W9GARA.CIG_ACCQUADRO",
          JdbcParametro.TIPO_TESTO, tab4.getW9GACIGAQ().toString());
    }
    if (tab4.isSetW3DGURI()) {
        datiAggiornamento.addColumn("W9GARA.DGURI", JdbcParametro.TIPO_DATA,
        tab4.getW3DGURI().getTime());
    }
    if (tab4.isSetW3DSCADB()) {
        datiAggiornamento.addColumn("W9GARA.DSCADE", JdbcParametro.TIPO_DATA,
        tab4.getW3DSCADB().getTime());
    }
    //if (tab4.isSetW9GARICALLUV()) {
    //datiAggiornamento.addColumn("W9GARA.RIC_ALLUV", JdbcParametro.TIPO_TESTO,
    //    this.getFlagSNDB(tab4.getW9GARICALLUV()).toString());
    //}
    
    if (tab4.isSetW9GACAM()) {
    	datiAggiornamento.addColumn("W9GARA.CAM", JdbcParametro.TIPO_TESTO,
    			this.getFlagSNDB(tab4.getW9GACAM()).toString());
    }
    
    if (tab4.isSetW9GASISMA()) {
      datiAggiornamento.addColumn("W9GARA.SISMA", JdbcParametro.TIPO_TESTO,
      		this.getFlagSNDB(tab4.getW9GASISMA()).toString());
    }
    
    if (tab4.isSetW9GAINDSEDE()) {
      datiAggiornamento.addColumn("W9GARA.INDSEDE",
          JdbcParametro.TIPO_TESTO, tab4.getW9GAINDSEDE().toString());
    }
    
    if (tab4.isSetW9GACOMSEDE()) {
      datiAggiornamento.addColumn("W9GARA.COMSEDE",
          JdbcParametro.TIPO_TESTO, tab4.getW9GACOMSEDE().toString());
    }
    
    if (tab4.isSetW9GAPROSEDE()) {
      datiAggiornamento.addColumn("W9GARA.PROSEDE",
          JdbcParametro.TIPO_TESTO, tab4.getW9GAPROSEDE().toString());
    }
    
    if (tab4.isSetArch5()) {
      datiAggiornamento.addColumn("W9GARA.IDCC", JdbcParametro.TIPO_NUMERICO, pkIdcc);
    }

    if (tab4.isSetArch6()) {
        datiAggiornamento.addColumn("W9GARA.IDUFFICIO", JdbcParametro.TIPO_NUMERICO, pkIduff);
    }
    
    Tab5Type tab5 = null;
    String codrup = "";
    if (doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().sizeOfTab5TypeArray() > 0) {
      tab5 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().getTab5TypeArray(0);
      codrup = this.getTecnico(tab5.getArch2(), pkUffint);
      datiAggiornamento.addColumn("W9GARA.RUP", JdbcParametro.TIPO_TESTO, codrup);
    }

    // Inserimento IDEGOV
    if (datiAggiornamento.isColumn("W9INBOX.IDEGOV")) {
      datiAggiornamento.addColumn("W9GARA.IDEGOV", JdbcParametro.TIPO_TESTO,
        datiAggiornamento.getString("W9INBOX.IDEGOV"));
    }
    
    if (primoInvio) {
      datiAggiornamento.addColumn("W9GARA.SITUAZIONE", JdbcParametro.TIPO_NUMERICO, new Long(1));
    }
    
    datiAggiornamento.insert("W9GARA", this.sqlManager);

    // Importazione in W9PUBB (dati di pubblicita' dell'appalto)
    if (doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().isSetTab184()) {
      Tab184Type tab184 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab184();

      DataColumnContainer dccPubblicazioni = new DataColumnContainer(
          new DataColumn[] {
              new DataColumn("W9PUBB.CODGARA", new JdbcParametro(
                  JdbcParametro.TIPO_NUMERICO, codGara)),
                  new DataColumn("W9PUBB.CODLOTT", new JdbcParametro(
                      JdbcParametro.TIPO_NUMERICO, new Long(1))),
                      new DataColumn("W9PUBB.NUM_APPA", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, new Long(1))),
                          new DataColumn("W9PUBB.NUM_PUBB", new JdbcParametro(
                              JdbcParametro.TIPO_NUMERICO, new Long(1))) });

      if (tab184.isSetW3GUCE1()) {
        dccPubblicazioni.addColumn("W9PUBB.DATA_GUCE", JdbcParametro.TIPO_DATA, tab184.getW3GUCE1().getTime());
      }
      if (tab184.isSetW3GURI1()) {
        dccPubblicazioni.addColumn("W9PUBB.DATA_GURI", JdbcParametro.TIPO_DATA, tab184.getW3GURI1().getTime());
      }
      if (tab184.isSetW3ALBO1()) {
        dccPubblicazioni.addColumn("W9PUBB.DATA_ALBO", JdbcParametro.TIPO_DATA, tab184.getW3ALBO1().getTime());
      }
      if (tab184.isSetW3NAZ1()) {
        dccPubblicazioni.addColumn("W9PUBB.QUOTIDIANI_NAZ", JdbcParametro.TIPO_NUMERICO, new Long(tab184.getW3NAZ1()));
      }
      if (tab184.isSetW3REG1()) {
        dccPubblicazioni.addColumn("W9PUBB.QUOTIDIANI_REG", JdbcParametro.TIPO_NUMERICO, new Long(tab184.getW3REG1()));
      }
      // S.C. 16/03/2011 Gli attributi W3PROFILO1, W3MIN1 e W3OSS1
      // sono diventati obbligatori
      dccPubblicazioni.addColumn("W9PUBB.PROFILO_COMMITTENTE", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab184.getW3PROFILO1()).toString());
      dccPubblicazioni.addColumn("W9PUBB.SITO_MINISTERO_INF_TRASP", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab184.getW3MIN1()).toString());
      dccPubblicazioni.addColumn("W9PUBB.SITO_OSSERVATORIO_CP", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab184.getW3OSS1()).toString());
      if (tab184.isSetW3BORE()) {
        dccPubblicazioni.addColumn("W9PUBB.DATA_BORE", JdbcParametro.TIPO_DATA, 
            tab184.getW3BORE().getTime());
      }
      if (tab184.isSetW3PERIODIC()) {
        dccPubblicazioni.addColumn("W9PUBB.PERIODICI", JdbcParametro.TIPO_NUMERICO, 
            new Long(tab184.getW3PERIODIC()));
      }

      // si richiama l'inserimento dell'occorrenza di tab184 (W9PUBB)
      dccPubblicazioni.insert("W9PUBB", this.sqlManager);
    }

    // Importazione in W9DOCGARA (documento PDF allegati)
    // Se presente oggetto listaTab41Array contiene oggetti, allora si importano
    // i documenti del bando di gara (gestione prima dell'introduzione delle pubblicazioni)
    if (doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().sizeOfListaTab41Array() > 0) {
      Tab41Type[] listaTab41 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab41Array();

      for (int iu = 0; iu < listaTab41.length; iu++) {
        Tab41Type tab41 = listaTab41[iu];

        datiAggiornamento.addColumn("W9DOCGARA.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
        datiAggiornamento.addColumn("W9DOCGARA.NUMDOC", JdbcParametro.TIPO_NUMERICO, new Long(iu + 1));
        datiAggiornamento.addColumn("W9DOCGARA.TITOLO", JdbcParametro.TIPO_TESTO, tab41.getW9DGTITOLO());
        datiAggiornamento.addColumn("W9DOCGARA.NUM_PUBB", JdbcParametro.TIPO_NUMERICO, new Long(1));
        
        if (tab41.isSetW9DGURL()) {
          datiAggiornamento.addColumn("W9DOCGARA.URL", JdbcParametro.TIPO_TESTO, tab41.getW9DGURL());  
        }

        if (tab41.isSetFile()) {
          ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
          try {
            fileAllegato.write(tab41.getFile());
          } catch (IOException e) {
            throw new GestoreException(
                "Errore durante la lettura del file allegato presente nella richiesta XML", null, e);
          }
          datiAggiornamento.addColumn("W9DOCGARA.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO,
              fileAllegato);
        }
        datiAggiornamento.insert("W9DOCGARA", this.sqlManager);
      }
    }

    return codrup;
  }

  /**
   * Inserimento dei dell'anagrafica del lotto (W9LOTT, W9APPAFORN, W9APPALAV,
   * W9COND, W9CPV, W9LOTTCATE, W9INCA.
   * 
   * @param doc
   * @param primoInvio
   * @param hmCig
   * @param pkUffint
   * @param codGara
   * @param codrup
   * @param maxCodLott
   * @throws SQLException
   * @throws GestoreException
   */
  private void insertDatiLotto(RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc,
      boolean primoInvio, HashMap<String, HashMap<String, Object>> hmCig,
      String pkUffint, Long codGara, String codrup, Long maxCodLott) throws SQLException, GestoreException {
    
    // Lotti    
    Tab5Type tab5 = null;

    for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().sizeOfTab5TypeArray(); i++) {
      tab5 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().getTab5TypeArray(i);

      Long codLott = null;
      String codiceCUI = null;
      String idSchedaSimog = null;
      String idSchedaLocale = null;
      
      String cig = tab5.getW3CIG();
      if (hmCig != null && hmCig.containsKey(cig)) {
        HashMap<String, Object> hmObjects = hmCig.get(cig); 
        
        codLott = (Long) hmObjects.get("CODLOTT");
        
        if (hmObjects.containsKey("CODCUI")) {
          codiceCUI = (String) hmObjects.get("CODCUI");
        }
        
        if (hmObjects.containsKey("ID_SCHEDA_SIMOG")) {
          idSchedaSimog = (String) hmObjects.get("ID_SCHEDA_SIMOG");
        }
        
        if (hmObjects.containsKey("ID_SCHEDA_LOCALE")) {
          idSchedaLocale = (String) hmObjects.get("ID_SCHEDA_LOCALE");
        }
      } else {
        maxCodLott++;
        codLott = maxCodLott;
      }

      DataColumnContainer dccLotto = new DataColumnContainer(new DataColumn[] {
          new DataColumn("W9LOTT.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
          new DataColumn("W9LOTT.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)) });
      
      dccLotto.addColumn("W9LOTT.OGGETTO", JdbcParametro.TIPO_TESTO, tab5.getW3OGGETTO2());
      dccLotto.addColumn("W9LOTT.SOMMA_URGENZA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab5.getW3SOMMAUR()).toString());
      dccLotto.addColumn("W9LOTT.IMPORTO_LOTTO", JdbcParametro.TIPO_DECIMALE, tab5.getW3ILOTTO());
      dccLotto.addColumn("W9LOTT.CPV", JdbcParametro.TIPO_TESTO, tab5.getW3CPV());
      dccLotto.addColumn("W9LOTT.ID_SCELTA_CONTRAENTE",
          JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab5.getW3IDSCEL2().toString()));
      
      if (tab5.isSetW9IDSCEL50()) {
        dccLotto.addColumn("W9LOTT.ID_SCELTA_CONTRAENTE_50",
            JdbcParametro.TIPO_NUMERICO, new Long(tab5.getW9IDSCEL50()));
      }
      dccLotto.addColumn("W9LOTT.ID_CATEGORIA_PREVALENTE", JdbcParametro.TIPO_TESTO,
          StringUtils.remove(tab5.getW3IDCATE4().toString(),"-"));
      dccLotto.addColumn("W9LOTT.NLOTTO", JdbcParametro.TIPO_NUMERICO,
          new Long(tab5.getW3NLOTTO()));
      dccLotto.addColumn("W9LOTT.MANOD", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab5.getW3MANOLO()).toString());

      if (tab5.isSetW3CLASCAT()) {
        dccLotto.addColumn("W9LOTT.CLASCAT", JdbcParametro.TIPO_TESTO,
            tab5.getW3CLASCAT().toString());
      }
      if (tab5.getW3COMCON() != null) {
    	  dccLotto.addColumn("W9LOTT.COMCON", JdbcParametro.TIPO_TESTO,
    	          this.getFlagSNDB(tab5.getW3COMCON()).toString());
      }
      
      if (tab5.isSetW3DESCOM() && !tab5.getW3DESCOM().toString().equals("")) {
        dccLotto.addColumn("W9LOTT.DESCOM", JdbcParametro.TIPO_NUMERICO,
            Long.parseLong(tab5.getW3DESCOM().toString()));
      }
      if (tab5.isSetT2IDCONINT()) {
        dccLotto.addColumn("W9LOTT.CODINT", JdbcParametro.TIPO_TESTO, tab5.getT2IDCONINT());
      }
      if (tab5.isSetW3CIGCOM()) {
        dccLotto.addColumn("W9LOTT.CIGCOM", JdbcParametro.TIPO_TESTO, tab5.getW3CIGCOM());
      }
      dccLotto.addColumn("W9LOTT.TIPO_CONTRATTO", JdbcParametro.TIPO_TESTO,
          tab5.getW3TIPOCON().toString());
      dccLotto.addColumn("W9LOTT.FLAG_ENTE_SPECIALE", JdbcParametro.TIPO_TESTO,
          tab5.getW3FLAGENT().toString());
      if (tab5.isSetW3MODGAR()) {
        dccLotto.addColumn("W9LOTT.ID_MODO_GARA", JdbcParametro.TIPO_NUMERICO,
            Long.parseLong(tab5.getW3MODGAR().toString()));
      }
      if (tab5.isSetW3LUOGOIS()) {
        dccLotto.addColumn("W9LOTT.LUOGO_ISTAT", JdbcParametro.TIPO_TESTO, tab5.getW3LUOGOIS());
      }
      if (tab5.isSetW3LUOGONU()) {
        dccLotto.addColumn("W9LOTT.LUOGO_NUTS", JdbcParametro.TIPO_TESTO, tab5.getW3LUOGONU());
      }
      if (tab5.isSetW9DPUBBLICAZ()) {
        dccLotto.addColumn("W9LOTT.DATA_PUBBLICAZIONE", JdbcParametro.TIPO_DATA,
            tab5.getW9DPUBBLICAZ().getTime());
      }
      if (tab5.isSetW9EXSOTTOSOGLIA()) {
        dccLotto.addColumn("W9LOTT.EXSOTTOSOGLIA", JdbcParametro.TIPO_TESTO,
            this.getFlagSNDB(tab5.getW9EXSOTTOSOGLIA()).toString());
      }
      if (tab5.isSetW9LOCUIINT()) {
        dccLotto.addColumn("W9LOTT.CUIINT", JdbcParametro.TIPO_TESTO,
            tab5.getW9LOCUIINT());
      }
      if (tab5.isSetW9LOURLEPROC()) {
        dccLotto.addColumn("W9LOTT.URL_EPROC", JdbcParametro.TIPO_TESTO,
            tab5.getW9LOURLEPROC());
      }
      dccLotto.addColumn("W9LOTT.ID_TIPO_PRESTAZIONE", JdbcParametro.TIPO_NUMERICO,
          Long.parseLong(tab5.getW3IDTIPO().toString()));

      // *** Gestione del CIG ***
      dccLotto.addColumn("W9LOTT.CIG", JdbcParametro.TIPO_TESTO, tab5.getW3CIG());

      dccLotto.addColumn("W9LOTT.IMPORTO_ATTUAZIONE_SICUREZZA",
          JdbcParametro.TIPO_DECIMALE, tab5.getW3IATTSIC());

      if (tab5.isSetW3CUP()) {
        dccLotto.addColumn("W9LOTT.CUP", JdbcParametro.TIPO_TESTO,
            tab5.getW3CUP());
      }
      dccLotto.addColumn("W9LOTT.ART_E1", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab5.getW3LOARTE1()).toString());
      dccLotto.addColumn("W9LOTT.ART_E2", JdbcParametro.TIPO_TESTO,
          this.getFlagSNDB(tab5.getW3LOARTE2()).toString());

      // Codice CUI
      if (codiceCUI != null) {
        dccLotto.addColumn("W9LOTT.CODCUI", JdbcParametro.TIPO_TESTO, codiceCUI);
      }
      
      // ID_SCHEDA_LOCALE
      if (idSchedaLocale != null) {
        dccLotto.addColumn("W9LOTT.ID_SCHEDA_LOCALE", JdbcParametro.TIPO_TESTO, idSchedaLocale);
      } else {
        dccLotto.addColumn("W9LOTT.ID_SCHEDA_LOCALE", JdbcParametro.TIPO_TESTO, tab5.getW3CIG());
      }
      
      // ID_SCHEDA_SIMOG
      if (idSchedaSimog != null) {
        dccLotto.addColumn("W9LOTT.ID_SCHEDA_SIMOG", JdbcParametro.TIPO_TESTO, idSchedaSimog);
      }
      
      if (UtilitySITAT.existsFase(codGara, codLott, new Long(1), CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, this.sqlManager)
          || UtilitySITAT.existsFase(codGara, codLott, new Long(1), CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, this.sqlManager)
          || UtilitySITAT.existsFase(codGara, codLott, new Long(1), CostantiW9.ADESIONE_ACCORDO_QUADRO, this.sqlManager)) {
        dccLotto.addColumn("W9LOTT.DAEXPORT", JdbcParametro.TIPO_TESTO, "1");
      } else {
        dccLotto.addColumn("W9LOTT.DAEXPORT", JdbcParametro.TIPO_TESTO, "2");
      }

      double importoTot = 0;
      if (tab5.getW3IATTSIC() > 0) {
        importoTot += tab5.getW3IATTSIC();
      }
      if (tab5.getW3ILOTTO() > 0) {
        importoTot += tab5.getW3ILOTTO();
      }
      dccLotto.addColumn("W9LOTT.IMPORTO_TOT", JdbcParametro.TIPO_DECIMALE, importoTot);
      if (codrup == null || codrup.trim().equals("")) {
        codrup = this.getTecnico(tab5.getArch2(), pkUffint);
      }
      dccLotto.addColumn("W9LOTT.RUP", JdbcParametro.TIPO_TESTO, codrup);
      
      if (primoInvio) {
        dccLotto.addColumn("W9LOTT.SITUAZIONE", JdbcParametro.TIPO_NUMERICO, new Long(1));
      }
      dccLotto.insert("W9LOTT", this.sqlManager);

      if (tab5.isSetListaTab51()) {
        ListaTab51Type listaTab51 = tab5.getListaTab51();
        Tab51Type tab51 = null;
        for (int j = 0; j < listaTab51.sizeOfTab51Array(); j++) {
          tab51 = listaTab51.getTab51Array(j);
          DataColumnContainer dccAppaForn = new DataColumnContainer(
              new DataColumn[] {
                  new DataColumn("W9APPAFORN.CODGARA", new JdbcParametro(
                      JdbcParametro.TIPO_NUMERICO, codGara)),
                      new DataColumn("W9APPAFORN.CODLOTT", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, codLott)),
                          new DataColumn("W9APPAFORN.NUM_APPAF", new JdbcParametro(
                              JdbcParametro.TIPO_NUMERICO, j + 1)) });
          dccAppaForn.addColumn("W9APPAFORN.ID_APPALTO",
              JdbcParametro.TIPO_NUMERICO,
              Long.parseLong(tab51.getW3IDAPP04().toString()));
          dccAppaForn.insert("W9APPAFORN", this.sqlManager);
        }
      }

      if (tab5.isSetListaTab52()) {
        ListaTab52Type listaTab52 = tab5.getListaTab52();
        Tab52Type tab52 = null;
        for (int j = 0; j < listaTab52.sizeOfTab52Array(); j++) {
          tab52 = listaTab52.getTab52Array(j);
          DataColumnContainer dccAppaLav = new DataColumnContainer(
              new DataColumn[] {
                  new DataColumn("W9APPALAV.CODGARA", new JdbcParametro(
                      JdbcParametro.TIPO_NUMERICO, codGara)),
                      new DataColumn("W9APPALAV.CODLOTT", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, codLott)),
                          new DataColumn("W9APPALAV.NUM_APPAL", new JdbcParametro(
                              JdbcParametro.TIPO_NUMERICO, j + 1)) });
          dccAppaLav.addColumn("W9APPALAV.ID_APPALTO",
              JdbcParametro.TIPO_NUMERICO,
              Long.parseLong(tab52.getW3IDAPP05().toString()));
          dccAppaLav.insert("W9APPALAV", this.sqlManager);
        }
      }

      if (tab5.isSetListaTab53()) {
        ListaTab53Type listaTab53 = tab5.getListaTab53();
        Tab53Type tab53 = null;
        for (int j = 0; j < listaTab53.sizeOfTab53Array(); j++) {
          tab53 = listaTab53.getTab53Array(j);
          DataColumnContainer dccCond = new DataColumnContainer(
              new DataColumn[] {
                  new DataColumn("W9COND.CODGARA", new JdbcParametro(
                      JdbcParametro.TIPO_NUMERICO, codGara)),
                      new DataColumn("W9COND.CODLOTT", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, codLott)),
                          new DataColumn("W9COND.NUM_COND", new JdbcParametro(
                              JdbcParametro.TIPO_NUMERICO, j + 1)) });
          dccCond.addColumn("W9COND.ID_CONDIZIONE",
              JdbcParametro.TIPO_NUMERICO,
              Long.parseLong(tab53.getW3IDCONDI().toString()));
          dccCond.insert("W9COND", this.sqlManager);
        }
      }

      if (tab5.isSetListaTab54()) {
        ListaTab54Type listaTab54 = tab5.getListaTab54();
        Tab54Type tab54 = null;
        for (int j = 0; j < listaTab54.sizeOfTab54Array(); j++) {
          tab54 = listaTab54.getTab54Array(j);
          DataColumnContainer dccCpv = new DataColumnContainer(
              new DataColumn[] {
                  new DataColumn("W9CPV.CODGARA", new JdbcParametro(
                      JdbcParametro.TIPO_NUMERICO, codGara)),
                      new DataColumn("W9CPV.CODLOTT", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, codLott)),
                          new DataColumn("W9CPV.NUM_CPV", new JdbcParametro(
                              JdbcParametro.TIPO_NUMERICO, j + 1)) });
          dccCpv.addColumn("W9CPV.CPV", JdbcParametro.TIPO_TESTO,
              tab54.getW3CPVCOMP());
          dccCpv.insert("W9CPV", this.sqlManager);
        }
      }

      if (tab5.isSetListaTab55()) {
        ListaTab55Type listaTab55 = tab5.getListaTab55();
        Tab55Type tab55 = null;
        for (int j = 0; j < listaTab55.sizeOfTab55Array(); j++) {
          tab55 = listaTab55.getTab55Array(j);
          DataColumnContainer dccLottCate = new DataColumnContainer(
              new DataColumn[] {
                  new DataColumn("W9LOTTCATE.CODGARA", new JdbcParametro(
                      JdbcParametro.TIPO_NUMERICO, codGara)),
                      new DataColumn("W9LOTTCATE.CODLOTT", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, codLott)),
                          new DataColumn("W9LOTTCATE.NUM_CATE", new JdbcParametro(
                              JdbcParametro.TIPO_NUMERICO, j + 1)) });
          dccLottCate.addColumn("W9LOTTCATE.CATEGORIA",
              JdbcParametro.TIPO_TESTO, StringUtils.remove(tab55.getW3CATEGORI().toString(),"-"));
          dccLottCate.addColumn("W9LOTTCATE.CLASCAT", JdbcParametro.TIPO_TESTO,
              tab55.getW3CLASCATCA().toString());

          dccLottCate.addColumn("W9LOTTCATE.SCORPORABILE", JdbcParametro.TIPO_TESTO,
              this.getFlagSNDB(tab55.getW9LCSCORPORA()));
          dccLottCate.addColumn("W9LOTTCATE.SUBAPPALTABILE", JdbcParametro.TIPO_TESTO,
              this.getFlagSNDB(tab55.getW9LCSUBAPPAL()));

          dccLottCate.insert("W9LOTTCATE", this.sqlManager);
        }
      }
      
      if (tab5.getTab56Array() != null && tab5.getTab56Array().length > 0) {
        for (int aio = 0; aio < tab5.getTab56Array().length; aio++) {
          Tab101Type tab101 = tab5.getTab56Array(aio);

          // Arch2Type arch2 = tab101.getArch2();
          String pkTecni = this.getTecnico(tab101.getArch2(), pkUffint);
          DataColumnContainer datiInca = new DataColumnContainer(new DataColumn[] {
              new DataColumn("W9INCA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
              new DataColumn("W9INCA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)) });
          datiInca.addColumn("W9INCA.NUM", JdbcParametro.TIPO_NUMERICO, new Long(1));
          datiInca.addColumn("W9INCA.NUM_INCA", JdbcParametro.TIPO_NUMERICO, new Long(aio));
          datiInca.addColumn("W9INCA.SEZIONE", JdbcParametro.TIPO_TESTO, "RA");
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
      if (hmCig != null && hmCig.containsKey(cig)) {
    	  hmCig.remove(cig);
      }
      
    }
  }

  /**
   * Gestione di integrazione e rettifica di un flusso.
   * @param doc
   * @param datiAggiornamento
   * @throws GestoreException
   * @throws SQLException
   */
  private void manageIntegrazioneORettifica(
      RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc,
      DataColumnContainer datiAggiornamento) throws GestoreException,
      SQLException {

    Long codgara = UtilitySITAT.getCodGaraByIdAvGara(
        doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab4().getW3IDGARA(),
        this.sqlManager);
    
    HashMap<String, HashMap<String, Object>> hmCig = this.backupDatiLotti(codgara);
    
    Long maxCodLott = (Long) this.sqlManager.getObject("select max(CODLOTT) from W9LOTT where CODGARA=?",
        new Object[] { codgara });
    
    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab4());
    this.insertDatiFlusso(doc, datiAggiornamento, false, codgara, hmCig, maxCodLott);
  }

  /**
   * In caso di aggiornamento dei lotti (e quindi in caso di cancellazione e reinserimento dei lotti),
   * si salvano alcuni campi dei lotti inserendoli in una mappa, la cui chiave e' il codice CIG di
   * ciascun lotto. 
   * I campi del lotto salvati sono: CODLOTT, CIG, CODCUI, ID_SCHEDA_SIMOG e ID_SCHEDA_LOCALE.
   * 
   * @param codgara
   * @return Ritorna una 
   * @throws SQLException
   */
  private HashMap<String, HashMap<String, Object>>
      backupDatiLotti(Long codgara) throws SQLException {
    
    List< ? > listaCodLottCigCodCUI = this.sqlManager.getListVector(
        "select CODLOTT, CIG, CODCUI, ID_SCHEDA_SIMOG, ID_SCHEDA_LOCALE "
        + "from W9LOTT where CODGARA=? ORDER by CODLOTT ASC", new Object[] { codgara });
    
    HashMap<String, HashMap<String, Object>> hmCig = new HashMap<String, HashMap<String, Object>>();
    for (int y = 0; y < listaCodLottCigCodCUI.size(); y++) {
      Vector< ? > vector = (Vector< ? >) listaCodLottCigCodCUI.get(y);
      
      Long codLott = (Long) ((JdbcParametro) vector.get(0)).getValue();
      String codiceCig = (String) ((JdbcParametro) vector.get(1)).getValue();
      String codiceCUI = (String) ((JdbcParametro) vector.get(2)).getValue();
      String idSchedaSimog = (String) ((JdbcParametro) vector.get(3)).getValue();
      String idSchedaLocale = (String) ((JdbcParametro) vector.get(4)).getValue();

      HashMap<String, Object> hmObjects = new HashMap<String, Object>();
      hmObjects.put("CODLOTT", codLott);
      
      if (StringUtils.isNotEmpty(codiceCUI)) {
        hmObjects.put("CODCUI", codiceCUI);
      }
      
      if (StringUtils.isNotEmpty(idSchedaSimog)) {
        hmObjects.put("ID_SCHEDA_SIMOG", idSchedaSimog);
      }
      
      if (StringUtils.isNotEmpty(idSchedaLocale)) {
        hmObjects.put("ID_SCHEDA_LOCALE", idSchedaLocale);
      }
      hmCig.put(codiceCig, hmObjects);
    }
    return hmCig;
  }

  /**
   * Cancellazione dell'anagrafica gara e di tutte le occorrenze figlie.
   * 
   * @param tab4 Tab4Type
   * @throws SQLException SQLException
   */
  private void deleteDatiFlusso(final Tab4Type tab4) throws SQLException {
    Long codgara = (Long) this.sqlManager.getObject(
        "select codgara from w9gara where idavgara = ?",
        new Object[] { tab4.getW3IDGARA() });

    this.deleteDatiGara(codgara);
    this.deleteDatiLotto(codgara);
  }

  /**
   * Cancellazione dei dati della gara ed entita' figlie, ma non si cancella
   * il lotto e tutte le entita' figlie (della gara).
   * 
   * @param codgara
   * @throws SQLException
   */
  private void deleteDatiGara(Long codgara) throws SQLException {
    
    this.sqlManager.update("delete from w9gara where codgara = ?",
        new Object[] { codgara }, 1);
    this.sqlManager.update("delete from w9pubb where codgara = ?",
        new Object[] { codgara });

    // Estrazione del numero di record presenti nella W9DOCGARA che sono
    // in relazione con la W9PUBBLICAZIONI. Si estrae cioe' il numero
    // di pubblicazioni con documenti
    Long numeroDocumentiDiPubblicazioni = (Long) this.sqlManager.getObject(
        "select count(*) from W9DOCGARA d where d.CODGARA=? and not exists " +
            "(select 1 from W9PUBBLICAZIONI p where d.CODGARA=p.CODGARA " +
                "and d.NUM_PUBB=p.NUM_PUBB)", new Object[] { codgara });
    if (numeroDocumentiDiPubblicazioni.longValue() > 0) {
      this.sqlManager.update("delete from w9docgara where codgara = ?",
        new Object[] { codgara });
    }
  }

  /**
   * Cancellazione dei dati del lotto e di tutte le entita' figlie.
   * 
   * @param codgara
   * @throws SQLException
   */
  private void deleteDatiLotto(Long codgara) throws SQLException {
    
    this.sqlManager.update("delete from w9lott where codgara = ?",
        new Object[] { codgara });
    this.sqlManager.update("delete from w9appaforn where codgara = ?",
        new Object[] { codgara });
    this.sqlManager.update("delete from w9appalav where codgara = ?",
        new Object[] { codgara });
    this.sqlManager.update("delete from w9cond where codgara = ?",
        new Object[] { codgara });
    this.sqlManager.update("delete from w9cpv where codgara = ?",
        new Object[] { codgara });
    this.sqlManager.update("delete from w9lottcate where codgara = ?",
        new Object[] { codgara });
    this.sqlManager.update("delete from w9inca where codgara = ?",
        new Object[] { codgara });
  }
  
  /**
   * Metodo per la gestione dell'importazione dei dati di anagrafica di gara e del lotto
   * per la funzionalita' 'Importa dati da OR'.
   * 
   * @param operazioneGara
   * @param operazioneLotto
   * @param anagraficaGaraLotto 
   * @param cfrup Codice fiscale del RUP
   * @param cfsa Codice fiscale della S.A.
   * @throws SQLException 
   * @throws GestoreException 
   */
  public void gestioneAnagraficaGaraLottoDaOR(String operazioneGara, String operazioneLotto,
      RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument anagraficaGaraLotto,
      String cfrup, String cfsa) throws SQLException, GestoreException {
    
    Long codGara = UtilitySITAT.getCodGaraByIdAvGara(
        anagraficaGaraLotto.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().
            getTab4().getW3IDGARA(), this.sqlManager);

    DataColumnContainer datiAggiornamento = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9LOTT.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)) });

    HashMap<String, HashMap<String, Object>> hmCig = this.backupDatiLotti(codGara);

    if (StringUtils.isNotEmpty(operazioneGara)) {
      if ("importa".equals(operazioneGara)) {
        this.insertDatiFlusso(anagraficaGaraLotto, datiAggiornamento, true, codGara, hmCig, new Long(0));
      }
      
      if ("sovrascrivi".equals(operazioneGara)) {
        this.manageIntegrazioneORettifica(anagraficaGaraLotto, datiAggiornamento);
      }

    } else {
      String pkUffint = this.getCodiceUfficioIntestatario(cfsa);
      String codrup = (String) this.sqlManager.getObject(
          "select CODTEC from TECNI where CFTEC=? and CGENTEI=?", 
          new Object[] {cfrup, pkUffint});

      if ("importa".equals(operazioneLotto)) {
        this.insertDatiLotto(anagraficaGaraLotto, true, hmCig, pkUffint, codGara, codrup, new Long(0));
      }
      
      if ("sovrascrivi".equals(operazioneLotto)) {
        Long maxCodLott = (Long) this.sqlManager.getObject("select max(CODLOTT) from W9LOTT where CODGARA=?",
            new Object[] { codGara });
        
        this.deleteDatiLotto(codGara);
        this.insertDatiLotto(anagraficaGaraLotto, true, hmCig, pkUffint, codGara, codrup, maxCodLott);
      }
    }
  }

}