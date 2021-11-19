package it.eldasoft.sil.pt.bl;

import it.cup.ws.chiusurarevoca.in.CUPCHIUSURAREVOCADocument;
import it.cup.ws.chiusurarevoca.in.CUPCHIUSURAREVOCADocument.CUPCHIUSURAREVOCA;
import it.cup.ws.chiusurarevoca.in.RICHIESTACHIUSURAREVOCACUPDocument;
import it.cup.ws.chiusurarevoca.in.RICHIESTACHIUSURAREVOCACUPDocument.RICHIESTACHIUSURAREVOCACUP;
import it.cup.ws.chiusurarevoca.out.DETTAGLIOCUPCHIUSURAREVOCADocument.DETTAGLIOCUPCHIUSURAREVOCA;
import it.cup.ws.chiusurarevoca.out.RICHIESTACHIUSURAREVOCACUPOUTDocument;
import it.cup.ws.dettaglio.in.RICHIESTADETTAGLIOCUPDocument;
import it.cup.ws.dettaglio.in.RICHIESTADETTAGLIOCUPDocument.RICHIESTADETTAGLIOCUP;
import it.cup.ws.dettaglio.out.CODICETIPOLOGIACOPFINANZDocument.CODICETIPOLOGIACOPFINANZ;
import it.cup.ws.dettaglio.out.CONCESSIONEINCENTIVIUNITAPRODUTTIVEDocument.CONCESSIONEINCENTIVIUNITAPRODUTTIVE.TipoIndAreaRifer.Enum;
import it.cup.ws.dettaglio.out.DETTAGLIOCUPDocument;
import it.cup.ws.generazione.in.ACQUISTOBENIDocument.ACQUISTOBENI;
import it.cup.ws.generazione.in.ATTIVECONOMICABENEFICIARIOATECO2007Document.ATTIVECONOMICABENEFICIARIOATECO2007;
import it.cup.ws.generazione.in.CONCESSIONECONTRIBUTINOUNITAPRODUTTIVEDocument.CONCESSIONECONTRIBUTINOUNITAPRODUTTIVE;
import it.cup.ws.generazione.in.CONCESSIONEINCENTIVIUNITAPRODUTTIVEDocument.CONCESSIONEINCENTIVIUNITAPRODUTTIVE;
import it.cup.ws.generazione.in.CUPCUMULATIVODocument.CUPCUMULATIVO;
import it.cup.ws.generazione.in.CUPGENERAZIONEDocument.CUPGENERAZIONE;
import it.cup.ws.generazione.in.DATIGENERALIPROGETTODocument.DATIGENERALIPROGETTO;
import it.cup.ws.generazione.in.DATIGENERALIPROGETTODocument.DATIGENERALIPROGETTO.Cumulativo;
import it.cup.ws.generazione.in.FINANZIAMENTODocument.FINANZIAMENTO;
import it.cup.ws.generazione.in.FINANZIAMENTODocument.FINANZIAMENTO.FinanzaProgetto;
import it.cup.ws.generazione.in.FINANZIAMENTODocument.FINANZIAMENTO.Sponsorizzazione;
import it.cup.ws.generazione.in.LAVORIPUBBLICIDocument.LAVORIPUBBLICI;
import it.cup.ws.generazione.in.LAVORIPUBBLICIDocument.LAVORIPUBBLICI.StrInfrastrUnica;
import it.cup.ws.generazione.in.LAVORIPUBBLICIDocument.LAVORIPUBBLICI.TipoIndAreaRifer;
import it.cup.ws.generazione.in.LOCALIZZAZIONEDocument.LOCALIZZAZIONE;
import it.cup.ws.generazione.in.MASTERDocument.MASTER;
import it.cup.ws.generazione.in.PARTECIPAZIONARIECONFERIMCAPITALEDocument.PARTECIPAZIONARIECONFERIMCAPITALE;
import it.cup.ws.generazione.in.REALIZZACQUISTOSERVIZIFORMAZIONEDocument.REALIZZACQUISTOSERVIZIFORMAZIONE;
import it.cup.ws.generazione.in.REALIZZACQUISTOSERVIZINOFORMAZIONERICERCADocument.REALIZZACQUISTOSERVIZINOFORMAZIONERICERCA;
import it.cup.ws.generazione.in.REALIZZACQUISTOSERVIZIRICERCADocument.REALIZZACQUISTOSERVIZIRICERCA;
import it.cup.ws.generazione.in.RICHIESTAGENERAZIONECUPDocument;
import it.cup.ws.generazione.in.RICHIESTAGENERAZIONECUPDocument.RICHIESTAGENERAZIONECUP;
import it.cup.ws.generazione.out.CODICECUPDocument.CODICECUP;
import it.cup.ws.generazione.out.DETTAGLIOGENERAZIONECUPDocument;
import it.cup.ws.generazione.out.DETTAGLIOGENERAZIONECUPDocument.DETTAGLIOGENERAZIONECUP;
import it.cup.ws.lista.in.CRITERIRICERCACUPDocument.CRITERIRICERCACUP;
import it.cup.ws.lista.in.PROGETTIDICOMPETENZADocument.PROGETTIDICOMPETENZA.SoloProgettiUtente;
import it.cup.ws.lista.in.RICHIESTALISTACUPDocument;
import it.cup.ws.lista.in.RICHIESTALISTACUPDocument.RICHIESTALISTACUP;
import it.cup.ws.lista.out.DETTAGLIOCUPDocument.DETTAGLIOCUP;
import it.cup.ws.lista.out.LISTACUPDocument;
import it.cup.ws.lista.out.LISTACUPDocument.LISTACUP;
import it.eldasoft.cup.ws.DettaglioCUP;
import it.eldasoft.cup.ws.LocalizzazioneCUP;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

public class GestioneXMLCUPManager {

  static Logger      logger = Logger.getLogger(GestioneXMLCUPManager.class);

  /** Manager SQL per le operazioni su database */
  private SqlManager sqlManager;

  /**
   *
   * @return
   */
  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  /**
   * Set SqlManager
   *
   * @param sqlManager
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   * Controlla esistenza del codice CUP
   *
   * @param id
   * @return
   * @throws GestoreException
   */
  public boolean esisteCodiceCUP(Long id) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("esisteCodiceCUP: inizio metodo");

    boolean esisteCodiceCUP = false;

    try {
      Long conteggio = (Long) this.sqlManager.getObject(
          "select count(*) from cupdati where id = ?", new Object[] { id });
      if (conteggio != null && conteggio.longValue() > 0) {
        esisteCodiceCUP = true;
      }

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nel controllo di esistenza dei dati CUP",
          "elaborazioneCUP.error", e);
    }

    if (logger.isDebugEnabled()) logger.debug("esisteCodiceCUP: fine metodo");

    return esisteCodiceCUP;

  }

  /**
   * Creazione del contenuto XML per la richiesta del codice CUP
   *
   * @param id
   * @param cupwsuser
   * @param cupwspass
   * @return
   * @throws GestoreException
   */
  public String richiestaGenerazioneCUPXML(Long id, String cupwsuser,
      String cupwspass) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("richiestaGenerazioneCUPXML: inizio metodo");

    String richiestaDatiXML = null;

    try {
      RICHIESTAGENERAZIONECUPDocument richiestaGenerazioneCUPDocument = RICHIESTAGENERAZIONECUPDocument.Factory.newInstance();
      richiestaGenerazioneCUPDocument.documentProperties().setEncoding("UTF-8");
      RICHIESTAGENERAZIONECUP richiestaGenerazioneCUP = richiestaGenerazioneCUPDocument.addNewRICHIESTAGENERAZIONECUP();

      // Informazioni generali XML (id, utente, password...)
      richiestaGenerazioneCUP.addNewIDRICHIESTA().newCursor().setTextValue(
          id.toString());
      richiestaGenerazioneCUP.addNewUSER().newCursor().setTextValue(cupwsuser);
      richiestaGenerazioneCUP.addNewPASSWORD().newCursor().setTextValue(
          cupwspass);

      CUPGENERAZIONE CUPGenerazione = richiestaGenerazioneCUP.addNewCUPGENERAZIONE();
      CUPGenerazione.setIdProgetto("1");

      // Dati generali del progetto
      CUPGenerazione.setDATIGENERALIPROGETTO(this.getDatiGeneraliProgetto(id));

      // CUP Master
      MASTER master = this.getMaster(id);
      if (master.isSetCupMaster() && master.isSetRagioniCollegamento()) {
        CUPGenerazione.setMASTER(master);
      }

      // Localizzazione
      LOCALIZZAZIONE[] localizzazioneArray = this.getLocalizzazione(id);
      if (localizzazioneArray != null) {
        CUPGenerazione.setLOCALIZZAZIONEArray(localizzazioneArray);
      }

      // Descrizione del progetto (vari elementi in funzione dei
      // valori assegnati a "CUMULATIVO", "NATURA" e "TIPOLOGIA")
      String selectCUPDATI = "select cumulativo, natura, tipologia from cupdati where id = ?";
      List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
          new Object[] { id });
      if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

        String cumulativo = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 0).getValue();
        String natura = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 1).getValue();
        String tipologia = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 2).getValue();

        //Ateco 2007
        ATTIVECONOMICABENEFICIARIOATECO2007 ateco2007 = this.getATTIVECONOMICABENEFICIARIOATECO2007(id);
        if (ateco2007.getSezione() != null) {
        	CUPGenerazione.setATTIVECONOMICABENEFICIARIOATECO2007(ateco2007);
        }
        
        // Descrizione
        if (cumulativo != null && "1".equals(cumulativo)) {
          // CUP Cumulativo
          CUPGenerazione.addNewDESCRIZIONE().setCUPCUMULATIVO(
              this.getCUPCumulativo(id));
        } else {

          // Lavori pubblici
          if ("03".equals(natura)) {
            CUPGenerazione.addNewDESCRIZIONE().setLAVORIPUBBLICI(
                this.getLavoriPubblici(id));
          }

          // Concessione incentivi unita' produttive
          if ("07".equals(natura)) {
            CUPGenerazione.addNewDESCRIZIONE().setCONCESSIONEINCENTIVIUNITAPRODUTTIVE(
                this.getConcessioneIncentiviUnitaProduttive(id));
          }

          // Realizzazione ed acquisto servizi di ricerca
          if ("02".equals(natura) && "14".equals(tipologia)) {
            CUPGenerazione.addNewDESCRIZIONE().setREALIZZACQUISTOSERVIZIRICERCA(
                this.getRealizzAcquistoServiziRicerca(id));
          }

          // Realizzazione ed acquisto servizi di formazione
          if ("02".equals(natura) && "12".equals(tipologia)) {
            CUPGenerazione.addNewDESCRIZIONE().setREALIZZACQUISTOSERVIZIFORMAZIONE(
                this.getRealizzAcquistoServiziFormazione(id));
          }

          // Realizzazione ed acquisto servizi non di formazione e ricerca
          if ("02".equals(natura)
              && (!"12".equals(tipologia) || !"14".equals(tipologia))) {
            CUPGenerazione.addNewDESCRIZIONE().setREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA(
                this.getRealizzAcquistoServiziNoFormazioneRicerca(id));
          }

          // Acquisto beni
          if ("01".equals(natura)) {
            CUPGenerazione.addNewDESCRIZIONE().setACQUISTOBENI(
                this.getAcquistoBeni(id));
          }

          // Acquisto di partecipazioni azionarie e conferimenti di capitale
          if ("08".equals(natura)) {
            CUPGenerazione.addNewDESCRIZIONE().setPARTECIPAZIONARIECONFERIMCAPITALE(
                this.getPartecipAzionarieConferimCapitale(id));
          }

          // Concessione di contributi ad altri soggetti (diversi da unita'
          // produttive)
          if ("06".equals(natura)) {
            CUPGenerazione.addNewDESCRIZIONE().setCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE(
                this.getConcessioneContributiNoUnitaProduttive(id));
          }
        }
      }

      // Finanziamento
      CUPGenerazione.setFINANZIAMENTO(this.getFinanziamento(id));

      // Conversione in stringa
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      richiestaGenerazioneCUPDocument.save(baos);
      richiestaDatiXML = baos.toString();
      baos.close();

      // Controllo degli errori di validazione XML
      ArrayList<Object> validationErrors = new ArrayList<Object>();
      XmlOptions validationOptions = new XmlOptions();
      validationOptions.setErrorListener(validationErrors);
      boolean isValid = richiestaGenerazioneCUPDocument.validate(validationOptions);

      if (!isValid) {
        String listaErroriValidazione = "";
        Iterator<?> iter = validationErrors.iterator();
        while (iter.hasNext()) {
          listaErroriValidazione += iter.next() + "\n";
        }
        logger.error("La richiesta di generazione CUP non rispetta il formato previsto: "
            + richiestaDatiXML
            + "\n"
            + listaErroriValidazione);
        throw new GestoreException(
            "La richiesta di generazione CUP non rispetta il formato previsto",
            "richiestaGenerazioneXMLCUP.validate",
            new Object[] { listaErroriValidazione }, null);
      } else {
        // Salvataggio del contenuto XML
        this.updateRichiestaGenerazioneCUPDATI(id, richiestaDatiXML);
      }

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore generico durante la creazione del contenuto XML",
          "richiestaGenerazioneXMLCUP.error", e);
    } catch (IOException e) {
      throw new GestoreException(
          "Errore generico durante la creazione del contenuto XML",
          "richiestaGenerazioneXMLCUP.error", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("richiestaGenerazioneCUPXML: fine metodo");

    return richiestaDatiXML;

  }

  /**
   * Ricava i dati generali del progetto
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private DATIGENERALIPROGETTO getDatiGeneraliProgetto(Long id)
      throws SQLException {

    String selectCUPDATI = "select anno_decisione, " // 0
        + "cumulativo, " // 1
        + "natura, " // 2
        + "tipologia, " // 3
        + "settore, " // 4
        + "sottosettore, " // 5
        + "categoria, " // 6
        + "cpv " // 7
        + "from cupdati where id = ?";

    DATIGENERALIPROGETTO datiGeneraliProgetto = DATIGENERALIPROGETTO.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Anno della decisione
      Long anno_decisione = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (anno_decisione != null)
        datiGeneraliProgetto.setAnnoDecisione(anno_decisione.toString());

      // CUP cumulativo ?
      String cumulativo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (cumulativo != null) {
        if ("1".equals(cumulativo)) {
          datiGeneraliProgetto.setCumulativo(Cumulativo.S);
        } else if ("2".equals(cumulativo)) {
          datiGeneraliProgetto.setCumulativo(Cumulativo.N);
        }
      }

      // Natura
      String natura = (String) SqlManager.getValueFromVectorParam(datiCUPDATI,
          2).getValue();
      if (natura != null) datiGeneraliProgetto.setNatura(natura);

      // Tipologia
      String tipologia = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (tipologia != null) datiGeneraliProgetto.setTipologia(tipologia);

      // Settore
      String settore = (String) SqlManager.getValueFromVectorParam(datiCUPDATI,
          4).getValue();
      if (settore != null) datiGeneraliProgetto.setSettore(settore);

      // Sottosettore
      String sottosettore = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (sottosettore != null)
        datiGeneraliProgetto.setSottosettore(sottosettore);

      // Categoria
      String categoria = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (categoria != null) datiGeneraliProgetto.setCategoria(categoria);

      // CPV
      String cpv = (String) SqlManager.getValueFromVectorParam(datiCUPDATI, 7).getValue();

      if (cpv != null) {
        // CPV - Livello 1
        String cpv1 = this.getCPVLivelloN(cpv, 1);
        datiGeneraliProgetto.setCpv1(cpv1);

        // CPV - Livello 2
        String cpv2 = this.getCPVLivelloN(cpv, 2);
        if (cpv1.equals(cpv2)) {
          datiGeneraliProgetto.setCpv2("");
        } else {
          datiGeneraliProgetto.setCpv2(cpv2);
        }

        // CPV - Livello 3
        String cpv3 = this.getCPVLivelloN(cpv, 3);
        if (cpv2.equals(cpv3)) {
          datiGeneraliProgetto.setCpv3("");
        } else {
          datiGeneraliProgetto.setCpv3(cpv3);
        }

        // CPV - Livello 4
        String cpv4 = this.getCPVLivelloN(cpv, 4);
        if (cpv3.equals(cpv4)) {
          datiGeneraliProgetto.setCpv4("");
        } else {
          datiGeneraliProgetto.setCpv4(cpv4);
        }

        // CPV - Livello 5
        String cpv5 = this.getCPVLivelloN(cpv, 5);
        if (cpv4.equals(cpv5)) {
          datiGeneraliProgetto.setCpv5("");
        } else {
          datiGeneraliProgetto.setCpv5(cpv5);
        }

        // CPV - Livello 6
        String cpv6 = this.getCPVLivelloN(cpv, 6);
        if (cpv5.equals(cpv6)) {
          datiGeneraliProgetto.setCpv6("");
        } else {
          datiGeneraliProgetto.setCpv6(cpv6);
        }

        // CPV - Livello 7
        String cpv7 = this.getCPVLivelloN(cpv, 7);
        if (cpv6.equals(cpv7)) {
          datiGeneraliProgetto.setCpv7("");
        } else {
          datiGeneraliProgetto.setCpv7(cpv7);
        }
      }

    }
    return datiGeneraliProgetto;
  }

  /**
   * Ricava il codice di CPV di livello N
   *
   * @param cpv
   * @param livello
   * @return
   * @throws SQLException
   */
  private String getCPVLivelloN(String cpv, int livello) throws SQLException {
    String cpvcod4 = UtilityStringhe.fillRight(cpv.substring(0, livello + 1),
        '0', 8)
        + "%";
    String cpvLivelloN = (String) this.sqlManager.getObject(
        "select cpvcod4 from tabcpv where cpvcod4 like ?",
        new Object[] { cpvcod4 });
    return cpvLivelloN;
  }

  /**
   * Ricava i dati relativi ad eventuali collegamento a progetti master
   * (mediante CUP Master)
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private MASTER getMaster(Long id) throws SQLException {
    String selectCUPDATI = "select cup_master, " // 0
        + "ragioni_collegamento " // 1
        + "from cupdati where id = ?";

    MASTER masterXML = MASTER.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // CUP Master
      String cup_master = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (cup_master != null) masterXML.setCupMaster(cup_master);

      // Ragione del collegamento
      String ragioni_collegamento = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (ragioni_collegamento != null)
        masterXML.setRagioniCollegamento(ragioni_collegamento);

    }
    return masterXML;
  }

  /**
   * Ricava i dati relativi alle fonti di finanziamento del progetto
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private FINANZIAMENTO getFinanziamento(Long id) throws SQLException {
    String selectCUPDATI = "select sponsorizzazione, " // 0
        + "finanza_progetto, " // 1
        + "costo, " // 2
        + "finanziamento, " // 3
        + "cop_statale, " // 4
        + "cop_regionale, " // 5
        + "cop_provinciale, " // 6
        + "cop_comunale, " // 7
        + "cop_altrapubblica, " // 8
        + "cop_comunitaria, " // 9
        + "cop_privata, " // 10
        + "cop_pubbdaconfermare " // 11
        + "from cupdati where id = ?";

    FINANZIAMENTO finanziamentoXML = FINANZIAMENTO.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Sponsorizzazione
      String sponsorizzazione = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (sponsorizzazione != null)
        finanziamentoXML.setSponsorizzazione(Sponsorizzazione.Enum.forString(sponsorizzazione));

      // Finanza di progetto
      String finanza_progetto = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (finanza_progetto != null)
        finanziamentoXML.setFinanzaProgetto(FinanzaProgetto.Enum.forString(finanza_progetto));

      // Costo del progetto
      Double costo = (Double) SqlManager.getValueFromVectorParam(datiCUPDATI, 2).getValue();
      if (costo != null) finanziamentoXML.setCosto(this.formatImporto(costo));

      // Finanziamento del progetto
      Double finanziamento = (Double) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (finanziamento != null)
        finanziamentoXML.setFinanziamento(this.formatImporto(finanziamento));

      // Copertura finanziaria
      String cop_statale = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();

      if (cop_statale != null && "1".equals(cop_statale)) {
        finanziamentoXML.addNewCODICETIPOLOGIACOPFINANZ().newCursor().setTextValue(
            "001");
      }

      String cop_regionale = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (cop_regionale != null && "1".equals(cop_regionale)) {
        finanziamentoXML.addNewCODICETIPOLOGIACOPFINANZ().newCursor().setTextValue(
            "002");
      }

      String cop_provinciale = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (cop_provinciale != null && "1".equals(cop_provinciale)) {
        finanziamentoXML.addNewCODICETIPOLOGIACOPFINANZ().newCursor().setTextValue(
            "003");
      }

      String cop_comunale = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 7).getValue();
      if (cop_comunale != null && "1".equals(cop_comunale)) {
        finanziamentoXML.addNewCODICETIPOLOGIACOPFINANZ().newCursor().setTextValue(
            "004");
      }

      String cop_altrapubblica = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 8).getValue();
      if (cop_altrapubblica != null && "1".equals(cop_altrapubblica)) {
        finanziamentoXML.addNewCODICETIPOLOGIACOPFINANZ().newCursor().setTextValue(
            "005");
      }

      String cop_comunitaria = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 9).getValue();
      if (cop_comunitaria != null && "1".equals(cop_comunitaria)) {
        finanziamentoXML.addNewCODICETIPOLOGIACOPFINANZ().newCursor().setTextValue(
            "006");
      }

      String cop_privata = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 10).getValue();
      if (cop_privata != null && "1".equals(cop_privata)) {
        finanziamentoXML.addNewCODICETIPOLOGIACOPFINANZ().newCursor().setTextValue(
            "007");
      }
      
      String cop_pubblicaDaConfermare = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 11).getValue();
      if (cop_pubblicaDaConfermare != null && "1".equals(cop_pubblicaDaConfermare)) {
        finanziamentoXML.addNewCODICETIPOLOGIACOPFINANZ().newCursor().setTextValue(
            "008");
      }
    }
    return finanziamentoXML;
  }

  /**
   * Ricava la descrizione del progetto relativamente ai LAVORI PUBBLICI (NATURA
   * = 03)
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private LAVORIPUBBLICI getLavoriPubblici(Long id) throws SQLException {

    String selectCUPDATI = "select nome_stru_infrastr, " // 0
        + "str_infrastr_unica, " // 1
        + "tipo_ind_area_rifer, " // 2
        + "ind_area_rifer, " // 3
        + "descrizione_intervento, " // 4
        + "strum_progr, " // 5
        + "desc_strum_progr, " // 6
        + "altre_informazioni, " // 7
        + "flagLeggeObiettivo, " // 8
        + "numDeliberaCipe, " // 9
        + "annoDelibera " // 10
        + "from cupdati where id = ?";

    LAVORIPUBBLICI lavoriPubblici = LAVORIPUBBLICI.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Nome delle strutture coinvolte
      String nome_stru_infrastr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (nome_stru_infrastr != null)
        lavoriPubblici.setNomeStrInfrastr(nome_stru_infrastr);

      // Struttura unica ?
      String str_infrastr_unica = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (str_infrastr_unica != null) if ("1".equals(str_infrastr_unica)) {
        lavoriPubblici.setStrInfrastrUnica(StrInfrastrUnica.SI);
      } else if ("2".equals(str_infrastr_unica)) {
        lavoriPubblici.setStrInfrastrUnica(StrInfrastrUnica.NO);
      }

      // Tipo indirizzo
      String tipo_ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (tipo_ind_area_rifer != null)
        lavoriPubblici.setTipoIndAreaRifer(TipoIndAreaRifer.Enum.forString(tipo_ind_area_rifer));

      // Indirizzo
      String ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (ind_area_rifer != null)
        lavoriPubblici.setIndAreaRifer(ind_area_rifer);

      // Descrizione intervento
      String descrizione_intervento = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (descrizione_intervento != null)
        lavoriPubblici.setDescrizioneIntervento(descrizione_intervento);

      // Strumento di programmazione
      String strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (strum_progr != null) lavoriPubblici.setStrumProgr(strum_progr);

      // Descrizione dello strumento di programmazione
      String desc_strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (desc_strum_progr != null)
        lavoriPubblici.setDescStrumProgr(desc_strum_progr);
      else 
    	  lavoriPubblici.setDescStrumProgr("");
      // Altre informazioni
      String altre_informazioni = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 7).getValue();
      if (altre_informazioni != null)
        lavoriPubblici.setAltreInformazioni(altre_informazioni);

      // Flag Legge Obiettivo
      String flagLeggeObiettivo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 8).getValue();
      if (flagLeggeObiettivo != null)
    	  lavoriPubblici.setFlagLeggeObiettivo(flagLeggeObiettivo.equals("1")?"S":"N");
      
      // Numero delibera cipe
      Long numDeliberaCipe = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 9).getValue();
      if (numDeliberaCipe != null)
    	  lavoriPubblici.setNumDeliberaCipe(numDeliberaCipe.toString());
      
      // Anno delibera cipe
      Long annoDelibera = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 10).getValue();
      if (annoDelibera != null)
    	  lavoriPubblici.setAnnoDelibera(annoDelibera.toString());
    }

    return lavoriPubblici;
  }

  /**
   * Ricava la descrizione del progetto relativamente alle CONCESSIONI
   * INTECENTIVI UNITA' PRODUTTIVE (NATURA = 07)
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private CONCESSIONEINCENTIVIUNITAPRODUTTIVE getConcessioneIncentiviUnitaProduttive(
      Long id) throws SQLException {

    String selectCUPDATI = "select denom_impr_stab, " // 0
        + "partita_iva, " // 1
        + "denom_impr_stab_prec, " // 2
        + "tipo_ind_area_rifer, " // 3
        + "ind_area_rifer, " // 4
        + "descrizione_intervento, " // 5
        + "strum_progr, " // 6
        + "desc_strum_progr, " // 7
        + "altre_informazioni, " // 8
        + "flagLeggeObiettivo, " // 9
        + "numDeliberaCipe, " // 10
        + "annoDelibera " // 11
        + "from cupdati where id = ?";

    CONCESSIONEINCENTIVIUNITAPRODUTTIVE concessioneIncentivi = CONCESSIONEINCENTIVIUNITAPRODUTTIVE.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Denominazione impresa / stabilimento
      String denom_impr_stab = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (denom_impr_stab != null)
        concessioneIncentivi.setDenominazioneImpresaStabilimento(denom_impr_stab);

      // Partita IVA
      String partita_iva = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (partita_iva != null) concessioneIncentivi.setPartitaIva(partita_iva);

      // Denominazione impresa / stabilimento precedente
      String denom_impr_stab_prec = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (denom_impr_stab_prec != null)
        concessioneIncentivi.setDenominazioneImpresaStabilimentoPrec(denom_impr_stab_prec);

      // Tipo indirizzo
      String tipo_ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (tipo_ind_area_rifer != null)
        concessioneIncentivi.setTipoIndAreaRifer(it.cup.ws.generazione.in.CONCESSIONEINCENTIVIUNITAPRODUTTIVEDocument.CONCESSIONEINCENTIVIUNITAPRODUTTIVE.TipoIndAreaRifer.Enum.forString(tipo_ind_area_rifer));

      // Indirizzo
      String ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (ind_area_rifer != null)
        concessioneIncentivi.setIndAreaRifer(ind_area_rifer);

      // Descrizione intervento
      String descrizione_intervento = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (descrizione_intervento != null)
        concessioneIncentivi.setDescrizioneIntervento(descrizione_intervento);

      // Strumento di programmazione
      String strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (strum_progr != null) concessioneIncentivi.setStrumProgr(strum_progr);

      // Descrizione dello strumento di programmazione
      String desc_strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 7).getValue();
      if (desc_strum_progr != null)
        concessioneIncentivi.setDescStrumProgr(desc_strum_progr);
      else 
    	  concessioneIncentivi.setDescStrumProgr("");

      // Altre informazioni
      String altre_informazioni = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 8).getValue();
      if (altre_informazioni != null)
        concessioneIncentivi.setAltreInformazioni(altre_informazioni);

      // Flag Legge Obiettivo
      String flagLeggeObiettivo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 9).getValue();
      if (flagLeggeObiettivo != null)
    	  concessioneIncentivi.setFlagLeggeObiettivo(flagLeggeObiettivo.equals("1")?"S":"N");
      
      // Numero delibera cipe
      Long numDeliberaCipe = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 10).getValue();
      if (numDeliberaCipe != null)
    	  concessioneIncentivi.setNumDeliberaCipe(numDeliberaCipe.toString());
      
      // Anno delibera cipe
      Long annoDelibera = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 11).getValue();
      if (annoDelibera != null)
    	  concessioneIncentivi.setAnnoDelibera(annoDelibera.toString());
    }

    return concessioneIncentivi;
  }

  /**
   * Ricava la descrizione del progetto relativamente alla REALIZZAZIONE ED
   * ACQUISTO SERVIZI DI RICERCA (NATURA = 02 e TIPOLOGIA = 14)
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private REALIZZACQUISTOSERVIZIRICERCA getRealizzAcquistoServiziRicerca(Long id)
      throws SQLException {

    String selectCUPDATI = "select denominazione_progetto, " // 0
        + "ente, " // 1
        + "tipo_ind_area_rifer, " // 2
        + "ind_area_rifer, " // 3
        + "descrizione_intervento, " // 4
        + "strum_progr, " // 5
        + "desc_strum_progr, " // 6
        + "altre_informazioni, " // 7
        + "flagLeggeObiettivo, " // 8
        + "numDeliberaCipe, " // 9
        + "annoDelibera " // 10
        + "from cupdati where id = ?";

    REALIZZACQUISTOSERVIZIRICERCA realizzAcquistoServiziRicerca = REALIZZACQUISTOSERVIZIRICERCA.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Denominazione del progetto
      String denominazione_progetto = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (denominazione_progetto != null)
        realizzAcquistoServiziRicerca.setDenominazioneProgetto(denominazione_progetto);

      // Ente
      String ente = (String) SqlManager.getValueFromVectorParam(datiCUPDATI, 1).getValue();
      if (ente != null) realizzAcquistoServiziRicerca.setEnte(ente);

      // Tipo indirizzo
      String tipo_ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (tipo_ind_area_rifer != null)
        realizzAcquistoServiziRicerca.setTipoIndAreaRifer(it.cup.ws.generazione.in.REALIZZACQUISTOSERVIZIRICERCADocument.REALIZZACQUISTOSERVIZIRICERCA.TipoIndAreaRifer.Enum.forString(tipo_ind_area_rifer));

      // Indirizzo
      String ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (ind_area_rifer != null)
        realizzAcquistoServiziRicerca.setIndAreaRifer(ind_area_rifer);

      // Descrizione intervento
      String descrizione_intervento = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (descrizione_intervento != null)
        realizzAcquistoServiziRicerca.setDescrizioneIntervento(descrizione_intervento);

      // Strumento di programmazione
      String strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (strum_progr != null)
        realizzAcquistoServiziRicerca.setStrumProgr(strum_progr);

      // Descrizione dello strumento di programmazione
      String desc_strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (desc_strum_progr != null)
        realizzAcquistoServiziRicerca.setDescStrumProgr(desc_strum_progr);
      else 
    	  realizzAcquistoServiziRicerca.setDescStrumProgr("");
      // Altre informazioni
      String altre_informazioni = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 7).getValue();
      if (altre_informazioni != null)
        realizzAcquistoServiziRicerca.setAltreInformazioni(altre_informazioni);

      // Flag Legge Obiettivo
      String flagLeggeObiettivo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 8).getValue();
      if (flagLeggeObiettivo != null)
    	  realizzAcquistoServiziRicerca.setFlagLeggeObiettivo(flagLeggeObiettivo.equals("1")?"S":"N");
      
      // Numero delibera cipe
      Long numDeliberaCipe = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 9).getValue();
      if (numDeliberaCipe != null)
    	  realizzAcquistoServiziRicerca.setNumDeliberaCipe(numDeliberaCipe.toString());
      
      // Anno delibera cipe
      Long annoDelibera = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 10).getValue();
      if (annoDelibera != null)
    	  realizzAcquistoServiziRicerca.setAnnoDelibera(annoDelibera.toString());
    }

    return realizzAcquistoServiziRicerca;
  }

  /**
   * Ricava la descrizione del progetto relativamente alla REALIZZAZIONE ED
   * ACQUISTO SERVIZI DI FORMAZIONE (NATURA = 02 e TIPOLOGIA = 12)
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private REALIZZACQUISTOSERVIZIFORMAZIONE getRealizzAcquistoServiziFormazione(
      Long id) throws SQLException {

    String selectCUPDATI = "select denominazione_progetto, " // 0
        + "ente, " // 1
        + "tipo_ind_area_rifer, " // 2
        + "ind_area_rifer, " // 3
        + "obiett_corso, " // 4
        + "mod_intervento_frequenza, " // 5
        + "strum_progr, " // 6
        + "desc_strum_progr, " // 7
        + "altre_informazioni, " // 8
        + "flagLeggeObiettivo, " // 9
        + "numDeliberaCipe, " // 10
        + "annoDelibera " // 11
        + "from cupdati where id = ?";

    REALIZZACQUISTOSERVIZIFORMAZIONE realizzAcquistoServiziFormazione = REALIZZACQUISTOSERVIZIFORMAZIONE.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Denominazione del progetto
      String denominazione_progetto = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (denominazione_progetto != null)
        realizzAcquistoServiziFormazione.setDenomProgetto(denominazione_progetto);

      // Ente
      String ente = (String) SqlManager.getValueFromVectorParam(datiCUPDATI, 1).getValue();
      if (ente != null)
        realizzAcquistoServiziFormazione.setDenomEnteCorso(ente);

      // Tipo indirizzo
      String tipo_ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (tipo_ind_area_rifer != null)
        realizzAcquistoServiziFormazione.setTipoIndAreaRifer(it.cup.ws.generazione.in.REALIZZACQUISTOSERVIZIFORMAZIONEDocument.REALIZZACQUISTOSERVIZIFORMAZIONE.TipoIndAreaRifer.Enum.forString(tipo_ind_area_rifer));

      // Indirizzo
      String ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (ind_area_rifer != null)
        realizzAcquistoServiziFormazione.setIndAreaRifer(ind_area_rifer);

      // Descrizione intervento
      String obiett_corso = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (obiett_corso != null)
        realizzAcquistoServiziFormazione.setObiettCorso(obiett_corso);

      // Descrizione delle modalita' di intervento
      String mod_intervento_frequenza = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (mod_intervento_frequenza != null)
        realizzAcquistoServiziFormazione.setModInterventoFrequenza(mod_intervento_frequenza);

      // Strumento di programmazione
      String strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (strum_progr != null)
        realizzAcquistoServiziFormazione.setStrumProgr(strum_progr);

      // Descrizione dello strumento di programmazione
      String desc_strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 7).getValue();
      if (desc_strum_progr != null)
        realizzAcquistoServiziFormazione.setDescStrumProgr(desc_strum_progr);
      else 
    	  realizzAcquistoServiziFormazione.setDescStrumProgr("");
      // Altre informazioni
      String altre_informazioni = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 8).getValue();
      if (altre_informazioni != null)
        realizzAcquistoServiziFormazione.setAltreInformazioni(altre_informazioni);

      // Flag Legge Obiettivo
      String flagLeggeObiettivo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 9).getValue();
      if (flagLeggeObiettivo != null)
    	  realizzAcquistoServiziFormazione.setFlagLeggeObiettivo(flagLeggeObiettivo.equals("1")?"S":"N");
      
      // Numero delibera cipe
      Long numDeliberaCipe = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 10).getValue();
      if (numDeliberaCipe != null)
    	  realizzAcquistoServiziFormazione.setNumDeliberaCipe(numDeliberaCipe.toString());
      
      // Anno delibera cipe
      Long annoDelibera = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 11).getValue();
      if (annoDelibera != null)
    	  realizzAcquistoServiziFormazione.setAnnoDelibera(annoDelibera.toString());
    }

    return realizzAcquistoServiziFormazione;
  }

  /**
   * Ricava la descrizione del progetto relativamente alla REALIZZAZIONE ED
   * ACQUISTO SERVIZI non di FORMAZIONE o di RICERCA (NATURA = 02 e TIPOLOGIA
   * diversa da 12 e 14)
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private REALIZZACQUISTOSERVIZINOFORMAZIONERICERCA getRealizzAcquistoServiziNoFormazioneRicerca(
      Long id) throws SQLException {

    String selectCUPDATI = "select nome_stru_infrastr, " // 0
        + "tipo_ind_area_rifer, " // 1
        + "ind_area_rifer, " // 2
        + "servizio, " // 3
        + "strum_progr, " // 4
        + "desc_strum_progr, " // 5
        + "altre_informazioni, " // 6
        + "flagLeggeObiettivo, " // 7
        + "numDeliberaCipe, " // 8
        + "annoDelibera " // 9
        + "from cupdati where id = ?";

    REALIZZACQUISTOSERVIZINOFORMAZIONERICERCA realizzAcquistoServiziNoFormazioneRicerca = REALIZZACQUISTOSERVIZINOFORMAZIONERICERCA.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Nome dell'infrastruttura
      String nome_stru_infrastr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (nome_stru_infrastr != null)
        realizzAcquistoServiziNoFormazioneRicerca.setNomeStrInfrastr(nome_stru_infrastr);

      // Tipo indirizzo
      String tipo_ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (tipo_ind_area_rifer != null)
        realizzAcquistoServiziNoFormazioneRicerca.setTipoIndAreaRifer(it.cup.ws.generazione.in.REALIZZACQUISTOSERVIZINOFORMAZIONERICERCADocument.REALIZZACQUISTOSERVIZINOFORMAZIONERICERCA.TipoIndAreaRifer.Enum.forString(tipo_ind_area_rifer));

      // Indirizzo
      String ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (ind_area_rifer != null)
        realizzAcquistoServiziNoFormazioneRicerca.setIndAreaRifer(ind_area_rifer);

      // Servizio
      String servizio = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (servizio != null)
        realizzAcquistoServiziNoFormazioneRicerca.setServizio(servizio);

      // Strumento di programmazione
      String strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (strum_progr != null)
        realizzAcquistoServiziNoFormazioneRicerca.setStrumProgr(strum_progr);

      // Descrizione dello strumento di programmazione
      String desc_strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (desc_strum_progr != null)
        realizzAcquistoServiziNoFormazioneRicerca.setDescStrumProgr(desc_strum_progr);
      else 
    	  realizzAcquistoServiziNoFormazioneRicerca.setDescStrumProgr("");
      // Altre informazioni
      String altre_informazioni = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (altre_informazioni != null)
        realizzAcquistoServiziNoFormazioneRicerca.setAltreInformazioni(altre_informazioni);

      // Flag Legge Obiettivo
      String flagLeggeObiettivo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 7).getValue();
      if (flagLeggeObiettivo != null)
    	  realizzAcquistoServiziNoFormazioneRicerca.setFlagLeggeObiettivo(flagLeggeObiettivo.equals("1")?"S":"N");
      
      // Numero delibera cipe
      Long numDeliberaCipe = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 8).getValue();
      if (numDeliberaCipe != null)
    	  realizzAcquistoServiziNoFormazioneRicerca.setNumDeliberaCipe(numDeliberaCipe.toString());
      
      // Anno delibera cipe
      Long annoDelibera = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 9).getValue();
      if (annoDelibera != null)
    	  realizzAcquistoServiziNoFormazioneRicerca.setAnnoDelibera(annoDelibera.toString());
    }

    return realizzAcquistoServiziNoFormazioneRicerca;
  }

  /**
   * Ricava la descrizione del progetto relativamente all'ACQUISTO DI BENI
   * (NATURA = 01)
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private ACQUISTOBENI getAcquistoBeni(Long id) throws SQLException {

    String selectCUPDATI = "select nome_stru_infrastr, " // 0
        + "tipo_ind_area_rifer, " // 1
        + "ind_area_rifer, " // 2
        + "bene, " // 3
        + "strum_progr, " // 4
        + "desc_strum_progr, " // 5
        + "altre_informazioni, " // 6
        + "flagLeggeObiettivo, " // 7
        + "numDeliberaCipe, " // 8
        + "annoDelibera " // 9
        + "from cupdati where id = ?";

    ACQUISTOBENI acquistoBeni = ACQUISTOBENI.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Nome dell'infrastruttura
      String nome_stru_infrastr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (nome_stru_infrastr != null)
        acquistoBeni.setNomeStrInfrastr(nome_stru_infrastr);

      // Tipo indirizzo
      String tipo_ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (tipo_ind_area_rifer != null)
        acquistoBeni.setTipoIndAreaRifer(it.cup.ws.generazione.in.ACQUISTOBENIDocument.ACQUISTOBENI.TipoIndAreaRifer.Enum.forString(tipo_ind_area_rifer));

      // Indirizzo
      String ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (ind_area_rifer != null) acquistoBeni.setIndAreaRifer(ind_area_rifer);

      // Servizio
      String bene = (String) SqlManager.getValueFromVectorParam(datiCUPDATI, 3).getValue();
      if (bene != null) acquistoBeni.setBene(bene);

      // Strumento di programmazione
      String strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (strum_progr != null) acquistoBeni.setStrumProgr(strum_progr);

      // Descrizione dello strumento di programmazione
      String desc_strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (desc_strum_progr != null)
        acquistoBeni.setDescStrumProgr(desc_strum_progr);
      else 
    	  acquistoBeni.setDescStrumProgr("");
      // Altre informazioni
      String altre_informazioni = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (altre_informazioni != null)
        acquistoBeni.setAltreInformazioni(altre_informazioni);

      // Flag Legge Obiettivo
      String flagLeggeObiettivo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 7).getValue();
      if (flagLeggeObiettivo != null)
    	  acquistoBeni.setFlagLeggeObiettivo(flagLeggeObiettivo.equals("1")?"S":"N");
      
      // Numero delibera cipe
      Long numDeliberaCipe = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 8).getValue();
      if (numDeliberaCipe != null)
    	  acquistoBeni.setNumDeliberaCipe(numDeliberaCipe.toString());
      
      // Anno delibera cipe
      Long annoDelibera = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 9).getValue();
      if (annoDelibera != null)
    	  acquistoBeni.setAnnoDelibera(annoDelibera.toString());
      
    }

    return acquistoBeni;
  }

  /**
   * Ricava la descrizione del progetto relativamente all'ACQUISTO DI
   * PARTECIPAZIONI AZIONARIE E CONFERIMENTI DI CAPITALE (NATURA = 08)
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private PARTECIPAZIONARIECONFERIMCAPITALE getPartecipAzionarieConferimCapitale(
      Long id) throws SQLException {

    String selectCUPDATI = "select ragione_sociale, " // 0
        + "partita_iva, " // 1
        + "ragione_sociale_prec, " // 2
        + "tipo_ind_area_rifer, " // 3
        + "ind_area_rifer, " // 4
        + "finalita, " // 5
        + "strum_progr, " // 6
        + "desc_strum_progr, " // 7
        + "altre_informazioni, " // 8
        + "flagLeggeObiettivo, " // 9
        + "numDeliberaCipe, " // 10
        + "annoDelibera " // 11
        + "from cupdati where id = ?";

    PARTECIPAZIONARIECONFERIMCAPITALE partecipAzionarieConferimCapitale = PARTECIPAZIONARIECONFERIMCAPITALE.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Ragione sociale
      String ragione_sociale = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (ragione_sociale != null)
        partecipAzionarieConferimCapitale.setRagioneSociale(ragione_sociale);

      // Partita IVA
      String partita_iva = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (partita_iva != null)
        partecipAzionarieConferimCapitale.setPartitaIva(partita_iva);

      // Ragione sociale precedente
      String ragione_sociale_prec = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (ragione_sociale_prec != null)
        partecipAzionarieConferimCapitale.setRagioneSocialePrec(ragione_sociale_prec);

      // Tipo indirizzo
      String tipo_ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (tipo_ind_area_rifer != null)
        partecipAzionarieConferimCapitale.setTipoIndAreaRifer(it.cup.ws.generazione.in.PARTECIPAZIONARIECONFERIMCAPITALEDocument.PARTECIPAZIONARIECONFERIMCAPITALE.TipoIndAreaRifer.Enum.forString(tipo_ind_area_rifer));

      // Indirizzo
      String ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (ind_area_rifer != null)
        partecipAzionarieConferimCapitale.setIndAreaRifer(ind_area_rifer);

      // Finalita'
      String finalita = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (finalita != null)
        partecipAzionarieConferimCapitale.setFinalita(finalita);

      // Strumento di programmazione
      String strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (strum_progr != null)
        partecipAzionarieConferimCapitale.setStrumProgr(strum_progr);

      // Descrizione dello strumento di programmazione
      String desc_strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 7).getValue();
      if (desc_strum_progr != null)
        partecipAzionarieConferimCapitale.setDescStrumProgr(desc_strum_progr);
      else 
    	  partecipAzionarieConferimCapitale.setDescStrumProgr("");
      // Altre informazioni
      String altre_informazioni = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 8).getValue();
      if (altre_informazioni != null)
        partecipAzionarieConferimCapitale.setAltreInformazioni(altre_informazioni);

      // Flag Legge Obiettivo
      String flagLeggeObiettivo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 9).getValue();
      if (flagLeggeObiettivo != null)
    	  partecipAzionarieConferimCapitale.setFlagLeggeObiettivo(flagLeggeObiettivo.equals("1")?"S":"N");
      
      // Numero delibera cipe
      Long numDeliberaCipe = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 10).getValue();
      if (numDeliberaCipe != null)
    	  partecipAzionarieConferimCapitale.setNumDeliberaCipe(numDeliberaCipe.toString());
      
      // Anno delibera cipe
      Long annoDelibera = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 11).getValue();
      if (annoDelibera != null)
    	  partecipAzionarieConferimCapitale.setAnnoDelibera(annoDelibera.toString());
      
    }

    return partecipAzionarieConferimCapitale;
  }

  /**
   * Ricava la descrizione del progetto relativamente alla CONCESSIONE DI
   * CONTRIBUTI AD ALTRI SOGGETTI (NATURA = 06)
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private CONCESSIONECONTRIBUTINOUNITAPRODUTTIVE getConcessioneContributiNoUnitaProduttive(
      Long id) throws SQLException {

    String selectCUPDATI = "select beneficiario, " // 0
        + "partita_iva, " // 1
        + "nome_stru_infrastr, " // 2
        + "tipo_ind_area_rifer, " // 3
        + "ind_area_rifer, " // 4
        + "descrizione_intervento, " // 5
        + "strum_progr, " // 6
        + "desc_strum_progr, " // 7
        + "altre_informazioni, " // 8
        + "flagLeggeObiettivo, " // 9
        + "numDeliberaCipe, " // 10
        + "annoDelibera " // 11
        + "from cupdati where id = ?";

    CONCESSIONECONTRIBUTINOUNITAPRODUTTIVE concessioneContributiNoUnitaProduttive = CONCESSIONECONTRIBUTINOUNITAPRODUTTIVE.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Ragione sociale
      String beneficiario = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (beneficiario != null)
        concessioneContributiNoUnitaProduttive.setBeneficiario(beneficiario);

      // Partita IVA
      String partita_iva = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (partita_iva != null)
        concessioneContributiNoUnitaProduttive.setPartitaIva(partita_iva);

      // Struttura
      String nome_stru_infrastr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (nome_stru_infrastr != null)
        concessioneContributiNoUnitaProduttive.setStruttura(nome_stru_infrastr);

      // Tipo indirizzo
      String tipo_ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (tipo_ind_area_rifer != null)
        concessioneContributiNoUnitaProduttive.setTipoIndAreaRifer(it.cup.ws.generazione.in.CONCESSIONECONTRIBUTINOUNITAPRODUTTIVEDocument.CONCESSIONECONTRIBUTINOUNITAPRODUTTIVE.TipoIndAreaRifer.Enum.forString(tipo_ind_area_rifer));

      // Indirizzo
      String ind_area_rifer = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (ind_area_rifer != null)
        concessioneContributiNoUnitaProduttive.setIndAreaRifer(ind_area_rifer);

      // Finalita'
      String descrizione_intervento = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (descrizione_intervento != null)
        concessioneContributiNoUnitaProduttive.setDescIntervento(descrizione_intervento);

      // Strumento di programmazione
      String strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 6).getValue();
      if (strum_progr != null)
        concessioneContributiNoUnitaProduttive.setStrumProgr(strum_progr);

      // Descrizione dello strumento di programmazione
      String desc_strum_progr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 7).getValue();
      if (desc_strum_progr != null)
        concessioneContributiNoUnitaProduttive.setDescStrumProgr(desc_strum_progr);
      else 
    	  concessioneContributiNoUnitaProduttive.setDescStrumProgr("");
      // Altre informazioni
      String altre_informazioni = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 8).getValue();
      if (altre_informazioni != null)
        concessioneContributiNoUnitaProduttive.setAltreInformazioni(altre_informazioni);

      // Flag Legge Obiettivo
      String flagLeggeObiettivo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 9).getValue();
      if (flagLeggeObiettivo != null)
    	  concessioneContributiNoUnitaProduttive.setFlagLeggeObiettivo(flagLeggeObiettivo.equals("1")?"S":"N");
      
      // Numero delibera cipe
      Long numDeliberaCipe = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 10).getValue();
      if (numDeliberaCipe != null)
    	  concessioneContributiNoUnitaProduttive.setNumDeliberaCipe(numDeliberaCipe.toString());
      
      // Anno delibera cipe
      Long annoDelibera = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 11).getValue();
      if (annoDelibera != null)
    	  concessioneContributiNoUnitaProduttive.setAnnoDelibera(annoDelibera.toString());
      
    }

    return concessioneContributiNoUnitaProduttive;
  }

  /**
   * ATTIVECONOMICABENEFICIARIOATECO2007
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private ATTIVECONOMICABENEFICIARIOATECO2007 getATTIVECONOMICABENEFICIARIOATECO2007(Long id) throws SQLException {

    String selectCUPDATI = "select ateco_sezione, " // 0
        + "ateco_divisione, " // 1
        + "ateco_gruppo, " // 2
        + "ateco_classe, " // 3
        + "ateco_categoria, " // 4
        + "ateco_sottocategoria " // 5
        + "from cupdati where id = ?";

    ATTIVECONOMICABENEFICIARIOATECO2007 ateco2007 = ATTIVECONOMICABENEFICIARIOATECO2007.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Atto amministrativo
      String ateco_sezione = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (ateco_sezione != null)
    	  ateco2007.setSezione(ateco_sezione);

      // Scopo intervento
      String ateco_divisione = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (ateco_divisione != null)
    	  ateco2007.setDivisione(ateco_divisione);

      // Altre informazioni
      String ateco_gruppo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (ateco_gruppo != null)
    	  ateco2007.setGruppo(ateco_gruppo);

      // Flag Legge Obiettivo
      String ateco_classe = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (ateco_classe != null)
    	  ateco2007.setClasse(ateco_classe);
      
      // Numero delibera cipe
      String ateco_categoria = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (ateco_categoria != null)
    	  ateco2007.setCategoria(ateco_categoria);
      
      // Anno delibera cipe
      String ateco_sottocategoria = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (ateco_sottocategoria != null)
    	  ateco2007.setSottocategoria(ateco_sottocategoria);
      
    }

    return ateco2007;
  }
  
  /**
   * Ricava la descrizione del progetto relativamente al CUP CUMULATIVO
   * (CUMULATIVO = '1')
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private CUPCUMULATIVO getCUPCumulativo(Long id) throws SQLException {

    String selectCUPDATI = "select atto_amministr, " // 0
        + "scopo_intervento, " // 1
        + "altre_informazioni, " // 2
        + "flagLeggeObiettivo, " // 3
        + "numDeliberaCipe, " // 4
        + "annoDelibera " // 5
        + "from cupdati where id = ?";

    CUPCUMULATIVO CUPCumulativo = CUPCUMULATIVO.Factory.newInstance();

    List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
        new Object[] { id });

    if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

      // Atto amministrativo
      String atto_amministr = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 0).getValue();
      if (atto_amministr != null)
        CUPCumulativo.setAttoAmministr(atto_amministr);

      // Scopo intervento
      String scopo_intervento = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 1).getValue();
      if (scopo_intervento != null)
        CUPCumulativo.setScopoIntervento(scopo_intervento);

      // Altre informazioni
      String altre_informazioni = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 2).getValue();
      if (altre_informazioni != null)
        CUPCumulativo.setAltreInformazioni(altre_informazioni);

      // Flag Legge Obiettivo
      String flagLeggeObiettivo = (String) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 3).getValue();
      if (flagLeggeObiettivo != null)
    	  CUPCumulativo.setFlagLeggeObiettivo(flagLeggeObiettivo.equals("1")?"S":"N");
      
      // Numero delibera cipe
      Long numDeliberaCipe = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 4).getValue();
      if (numDeliberaCipe != null)
    	  CUPCumulativo.setNumDeliberaCipe(numDeliberaCipe.toString());
      
      // Anno delibera cipe
      Long annoDelibera = (Long) SqlManager.getValueFromVectorParam(
          datiCUPDATI, 5).getValue();
      if (annoDelibera != null)
    	  CUPCumulativo.setAnnoDelibera(annoDelibera.toString());
      
    }

    return CUPCumulativo;
  }

  /**
   * Gestione della localizzazione
   *
   * @param id
   * @return
   * @throws SQLException
   */
  private LOCALIZZAZIONE[] getLocalizzazione(Long id) throws SQLException {

    String selectCUPLOC = "select stato, " // 0
        + "regione, " // 1
        + "provincia, " // 2
        + "comune " // 3
        + "from cuploc where id = ?";

    LOCALIZZAZIONE[] localizzazione = null;

    List<?> datiCUPLOC = this.sqlManager.getListVector(selectCUPLOC,
        new Object[] { id });

    if (datiCUPLOC != null && datiCUPLOC.size() > 0) {
      localizzazione = new LOCALIZZAZIONE[datiCUPLOC.size()];
      for (int i = 0; i < datiCUPLOC.size(); i++) {
        localizzazione[i] = LOCALIZZAZIONE.Factory.newInstance();

        // Stato
        String stato = (String) SqlManager.getValueFromVectorParam(
            datiCUPLOC.get(i), 0).getValue();
        localizzazione[i].setStato(stato);

        // Regione
        String regione = (String) SqlManager.getValueFromVectorParam(
            datiCUPLOC.get(i), 1).getValue();
        if (!"-1".equals(regione) && regione.length() > 2) {
          regione = regione.substring(1);
        }
        localizzazione[i].setRegione(regione);

        // Provincia
        String provincia = (String) SqlManager.getValueFromVectorParam(
            datiCUPLOC.get(i), 2).getValue();
        localizzazione[i].setProvincia(provincia);

        // Comune
        String comune = (String) SqlManager.getValueFromVectorParam(
            datiCUPLOC.get(i), 3).getValue();
        if (!"-1".equals(comune) && comune.length() > 4) {
          comune = comune.substring(3);
        }
        localizzazione[i].setComune(comune);
      }
    }

    return localizzazione;
  }

  /**
   * Trasformazione dell'importo in stringa secondo il formato previsto per CUP
   * Gli importi devono essere indicati in MIGLIAIA di EURO
   *
   * @param importo
   * @return
   */
  private String formatImporto(Double importo) {
    DecimalFormatSymbols simbols = new DecimalFormatSymbols();
    simbols.setDecimalSeparator('.');
    simbols.setGroupingSeparator(' ');
    DecimalFormat decimalFormat = new DecimalFormat("###########0.###", simbols);

    importo = new Double(importo.doubleValue() / 1000);

    return decimalFormat.format(importo.doubleValue());
  }

  /**
   * Gestione della risposta di generazione CUP
   *
   * @param id
   * @param datiXMLBase64
   * @throws GestoreException
   */
  public String dettaglioGenerazioneCUPXML(Long id, String dettaglioDatiXML)
      throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("dettaglioGenerazioneCUPXML: inizio metodo");

    String cup = null;

 // Controllo utente
    if ("Utente non autorizzato".equals(dettaglioDatiXML)) {
      throw new GestoreException(
          "L'elaborazione della richiesta di generazione CUP restituisce il seguente esito: utente non autorizzato",
          "dettaglioGenerazioneXMLCUP.esito",
          new Object[] { "Utente non autorizzato" }, null);
    }

    try {
      DETTAGLIOGENERAZIONECUPDocument dettaglioGenerazioneCUPDocument = DETTAGLIOGENERAZIONECUPDocument.Factory.parse(dettaglioDatiXML);

      // Controllo degli errori di validazione XML
      ArrayList<Object> validationErrors = new ArrayList<Object>();
      XmlOptions validationOptions = new XmlOptions();
      validationOptions.setErrorListener(validationErrors);
      boolean isValid = dettaglioGenerazioneCUPDocument.validate(validationOptions);

      if (!isValid) {
        String listaErroriValidazione = "";
        Iterator<?> iter = validationErrors.iterator();
        while (iter.hasNext()) {
          listaErroriValidazione += iter.next() + "\n";
        }
        logger.error("La risposta alla richiesta di generazione CUP non rispetta il formato previsto: "
            + dettaglioDatiXML
            + "\n"
            + listaErroriValidazione);
        throw new GestoreException(
            "La risposta alla richiesta di generazione CUP non rispetta il formato previsto",
            "dettaglioGenerazioneXMLCUP.validate",
            new Object[] { listaErroriValidazione }, null);
      }

      DETTAGLIOGENERAZIONECUP dettaglioGenerazioneCUP = dettaglioGenerazioneCUPDocument.getDETTAGLIOGENERAZIONECUP();

      // Id assegnato dal CIPE
      String id_richiesta_assegnato = dettaglioGenerazioneCUP.getIDRICHIESTAASSEGNATO().newCursor().getTextValue();

      // Esito e descrizione dell'elaborazione
      String esito_elaborazione = dettaglioGenerazioneCUP.getDETTAGLIOELABORAZIONE().getESITOELABORAZIONE().newCursor().getTextValue();
      String descrizione_esito_elaborazione = dettaglioGenerazioneCUP.getDETTAGLIOELABORAZIONE().getDESCRIZIONEESITOELABORAZIONE().newCursor().getTextValue();

      if ("KO".equals(esito_elaborazione)) {

          throw new GestoreException(
              "L'elaborazione della richiesta di generazione CUP restituisce il seguente esito: "
                  + descrizione_esito_elaborazione,
              "dettaglioGenerazioneXMLCUP.esito",
              new Object[] { descrizione_esito_elaborazione }, null);
        } else {
          // Dettaglio CUP
          if (dettaglioGenerazioneCUP.isSetDETTAGLIOCUP()) {
            CODICECUP codiceCUP = dettaglioGenerazioneCUP.getDETTAGLIOCUP().getCODICECUP();
            cup = codiceCUP.newCursor().getTextValue();
          }

          // Aggiornamento della base dati (tabella CUPDATI)
          this.updateDettaglioGenerazioneCUPDATI(id, dettaglioDatiXML, cup,
              id_richiesta_assegnato, esito_elaborazione,
              descrizione_esito_elaborazione);

          // Aggiornamento delle base dati (tabella INTTRI)
          this.updateDettaglioGenerazioneINTTRI(id);
        }


    } catch (XmlException e) {
      throw new GestoreException(
              "Errore generico nella lettura della risposta: " + dettaglioDatiXML,
          "dettaglioGenerazioneXMLCUP.error", new Object[] { dettaglioDatiXML },  e);
    }

    if (logger.isDebugEnabled())
      logger.debug("dettaglioGenerazioneCUPXML: fine metodo");

    return cup;

  }
  
  /**
   * Gestione della risposta di generazione CUP
   *
   * @param id
   * @param datiXMLBase64
   * @throws GestoreException
   */
  public boolean dettaglioChiusuraRevocaCUPXML(Long id, String dettaglioDatiXML)
      throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("dettaglioChiusuraRevocaCUPXML: inizio metodo");

    boolean result = false;

 // Controllo utente
    if ("Utente non autorizzato".equals(dettaglioDatiXML)) {
      throw new GestoreException(
          "L'elaborazione della richiesta di chiusura revoca CUP restituisce il seguente esito: utente non autorizzato",
          "dettaglioGenerazioneXMLCUP.esito",
          new Object[] { "Utente non autorizzato" }, null);
    }

    try {
      RICHIESTACHIUSURAREVOCACUPOUTDocument dettaglioChiusuraRevocaCUPDocument = RICHIESTACHIUSURAREVOCACUPOUTDocument.Factory.parse(dettaglioDatiXML);
      // Controllo degli errori di validazione XML
      ArrayList<Object> validationErrors = new ArrayList<Object>();
      XmlOptions validationOptions = new XmlOptions();
      validationOptions.setErrorListener(validationErrors);
      boolean isValid = dettaglioChiusuraRevocaCUPDocument.validate(validationOptions);

      if (!isValid) {
        String listaErroriValidazione = "";
        Iterator<?> iter = validationErrors.iterator();
        while (iter.hasNext()) {
          listaErroriValidazione += iter.next() + "\n";
        }
        logger.error("La risposta alla richiesta di chiusura/revoca CUP non rispetta il formato previsto: "
            + dettaglioDatiXML
            + "\n"
            + listaErroriValidazione);
        throw new GestoreException(
            "La risposta alla richiesta di chiusura/revoca CUP non rispetta il formato previsto",
            "dettaglioGenerazioneXMLCUP.validate",
            new Object[] { listaErroriValidazione }, null);
      }

      DETTAGLIOCUPCHIUSURAREVOCA dettaglioChiusuraRevocaCUP = dettaglioChiusuraRevocaCUPDocument.getRICHIESTACHIUSURAREVOCACUPOUT().getDETTAGLIOCUPCHIUSURAREVOCAArray(0);

      // Id assegnato dal CIPE
      String statoProgetto = null;
      String messaggiScarto = null;
      if (dettaglioChiusuraRevocaCUP.getSTATODELPROGETTO() != null) {
    	  statoProgetto = dettaglioChiusuraRevocaCUP.getSTATODELPROGETTO().newCursor().getTextValue();
      }
      if (dettaglioChiusuraRevocaCUP.getMESSAGGIDISCARTO() != null) {
    	  messaggiScarto = dettaglioChiusuraRevocaCUP.getMESSAGGIDISCARTO().newCursor().getTextValue();
      }

      if (messaggiScarto != null) {

          throw new GestoreException(
              "L'elaborazione della richiesta di generazione CUP restituisce il seguente esito: "
                  + messaggiScarto,
              "dettaglioGenerazioneXMLCUP.esito",
              new Object[] { messaggiScarto.toString() }, null);
        } else {
          // chiusura / revoca ok
        	result = true;

          // Aggiornamento della base dati (tabella CUPDATI)
          this.updateDettaglioChiusuraRevocaCUPDATI(id, statoProgetto);

        }


    } catch (XmlException e) {
      throw new GestoreException(
              "Errore generico nella lettura della risposta: " + dettaglioDatiXML,
          "dettaglioGenerazioneXMLCUP.error", new Object[] { dettaglioDatiXML },  e);
    }

    if (logger.isDebugEnabled())
      logger.debug("dettaglioChiusuraRevocaCUPXML: fine metodo");

    return result;

  }
  
  /**
   * Aggiornamento della tabella CUPDATI con il file XML prodotto per la
   * richiesta di generazione del codice CUP
   *
   * @param id
   * @param datiXML
   * @throws SQLException
   */
  private void updateRichiestaGenerazioneCUPDATI(Long id, String datiXML)
      throws GestoreException {

    String updateCUPDATI = "update cupdati set richiestaxml = ? where id = ?";

    try {
      this.sqlManager.update(updateCUPDATI, new Object[] { datiXML, id });
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nell'aggiornamento della tabella CUPDATI",
          "richiestaGenerazioneXMLCUP.error", e);
    }

  }

  /**
   * Aggiornamento della tabella CUPDATI con i dati provenienti dal dettaglio
   * generazione
   *
   * @param id
   * @param datiXML
   * @param cup
   * @param id_richiesta_assegnato
   * @param esito_elaborazione
   * @param descrizione_esito_elaborazione
   * @throws GestoreException
   */
  private void updateDettaglioGenerazioneCUPDATI(Long id, String datiXML,
      String cup, String id_richiesta_assegnato, String esito_elaborazione,
      String descrizione_esito_elaborazione) throws GestoreException {

    String updateCUPDATI = "update cupdati set cup = ?, "
        + "data_definitivo = ?, "
        + "id_assegnato = ?, "
        + "esito = ?, "
        + "descrizione_esito = ?, "
        + "rispostaxml = ? where id = ?";

    try {
      this.sqlManager.update(updateCUPDATI, new Object[] { cup,
          new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
          id_richiesta_assegnato, esito_elaborazione,
          descrizione_esito_elaborazione, datiXML, id });
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nell'aggiornamento della tabella CUPDATI: " + datiXML,
          "dettaglioGenerazioneXMLCUP.error", new Object[] { datiXML },  e);
    }

  }

  /**
   * Aggiornamento della tabella CUPDATI con i dati provenienti dal dettaglio
   * generazione
   *
   * @param id
   * @param descrizione_esito_elaborazione
   * @throws GestoreException
   */
  private void updateDettaglioChiusuraRevocaCUPDATI(Long id, String descrizione_esito_elaborazione) throws GestoreException {

    String updateCUPDATI = "update cupdati set descrizione_esito = ? where id = ?";

    try {
      this.sqlManager.update(updateCUPDATI, new Object[] {descrizione_esito_elaborazione, id });
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nell'aggiornamento della tabella CUPDATI: id=" +id + " :" + descrizione_esito_elaborazione,
          "dettaglioGenerazioneXMLCUP.error", new Object[] { "id=" +id + " :" + descrizione_esito_elaborazione },  e);
    }

  }
  
  /**
   * Aggiornamento dei dati dell'intervento (INTTRI) collegato alla richiesta
   * del codice CUP
   *
   * @param id
   * @throws GestoreException
   */
  private void updateDettaglioGenerazioneINTTRI(Long id)
      throws GestoreException {

    String selectCUPDATI = "select cup, cup_master, contri, conint from cupdati where id = ?";
    String updateINTTRI = "update inttri set cupprg = ?, cupmst = ? where contri = ? and conint = ?";

    try {
      List<?> datiCUPDATI = this.sqlManager.getVector(selectCUPDATI,
          new Object[] { id });
      if (datiCUPDATI != null && datiCUPDATI.size() > 0) {
        String cup = (String) SqlManager.getValueFromVectorParam(datiCUPDATI, 0).getValue();
        String cup_master = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 1).getValue();
        Long contri = (Long) SqlManager.getValueFromVectorParam(datiCUPDATI, 2).getValue();
        Long conint = (Long) SqlManager.getValueFromVectorParam(datiCUPDATI, 3).getValue();

        if (contri != null && conint != null) {
          this.sqlManager.update(updateINTTRI, new Object[] { cup, cup_master,
              contri, conint });
        }
      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nell'aggiornamento della tabella INTTRI: " + id,
          "dettaglioGenerazioneXMLCUP.error", new Object[] { id },  e);
    }

  }

  /**
   * Richiesta di lista CUP
   *
   * @param id
   * @param cupwsuser
   * @param cupwspass
   * @return
   * @throws GestoreException
   */
  public String richiestaListaCUPXML(String cupwsuser, String cupwspass,
      HashMap<String, String> hMapParametri) throws GestoreException {

    String richiestaListaCUPXML = null;

    try {

      RICHIESTALISTACUPDocument richiestaListaCUPDocument = RICHIESTALISTACUPDocument.Factory.newInstance();
      richiestaListaCUPDocument.documentProperties().setEncoding("UTF-8");
      RICHIESTALISTACUP richiestaListaCUP = richiestaListaCUPDocument.addNewRICHIESTALISTACUP();

      // Informazioni generali
      richiestaListaCUP.addNewIDRICHIESTA().newCursor().setTextValue("1");
      richiestaListaCUP.addNewUSER().newCursor().setTextValue(cupwsuser);
      richiestaListaCUP.addNewPASSWORD().newCursor().setTextValue(cupwspass);

      // Parametri di ricerca
      CRITERIRICERCACUP criteriRicercaCUP = richiestaListaCUP.addNewCRITERIRICERCACUP();

      String annoDecisione = (hMapParametri.get("annoDecisione"));
      if (annoDecisione != null && annoDecisione != "")
        criteriRicercaCUP.addNewANNODECISIONE().newCursor().setTextValue(
            annoDecisione);

      String codiceCUP = (hMapParametri.get("codiceCUP"));
      if (codiceCUP != null && codiceCUP != "")
        criteriRicercaCUP.addNewCODICECUP().newCursor().setTextValue(codiceCUP);

      String dataGenerazioneDa = (hMapParametri.get("dataGenerazioneDa"));
      if (dataGenerazioneDa != null && dataGenerazioneDa != "")
        criteriRicercaCUP.addNewDATAGENERAZIONEDA().newCursor().setTextValue(
            dataGenerazioneDa);

      String dataGenerazioneA = (hMapParametri.get("dataGenerazioneA"));
      if (dataGenerazioneA != null && dataGenerazioneA != "")
        criteriRicercaCUP.addNewDATAGENERAZIONEA().newCursor().setTextValue(
            dataGenerazioneA);

      String natura = (hMapParametri.get("natura"));
      if (natura != null && natura != "")
        criteriRicercaCUP.addNewNATURA().newCursor().setTextValue(natura);

      String tipologia = (hMapParametri.get("tipologia"));
      if (tipologia != null && tipologia != "")
        criteriRicercaCUP.addNewTIPOLOGIA().newCursor().setTextValue(tipologia);

      String descrizione = (hMapParametri.get("descrizione"));
      if (descrizione != null && descrizione != "")
        criteriRicercaCUP.addNewDESCRIZIONE().newCursor().setTextValue(
            descrizione);

      // Progetti di competenza (S per ottenere la lista solo dei progetti
      // dell'utente corrente
      // N per ottenere una lista generica indipendentemente dall'utente che ha
      // richiesto
      // la generazione del CUP)
      criteriRicercaCUP.addNewPROGETTIDICOMPETENZA().setSoloProgettiUtente(
          SoloProgettiUtente.N);

      // Conversione in stringa
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      richiestaListaCUPDocument.save(baos);
      richiestaListaCUPXML = baos.toString();
      baos.close();

      // Controllo degli errori di validazione XML
      ArrayList<Object> validationErrors = new ArrayList<Object>();
      XmlOptions validationOptions = new XmlOptions();
      validationOptions.setErrorListener(validationErrors);
      boolean isValid = richiestaListaCUPDocument.validate(validationOptions);

      if (!isValid) {
        String listaErroriValidazione = "";
        Iterator<?> iter = validationErrors.iterator();
        while (iter.hasNext()) {
          listaErroriValidazione += iter.next() + "\n";
        }
        logger.error("La richiesta della lista dei CUP non rispetta il formato previsto: "
            + richiestaListaCUPXML
            + "\n"
            + listaErroriValidazione);
        throw new GestoreException(
            "La richiesta della lista dei CUP non rispetta il formato previsto",
            "richiestaListaXMLCUP.validate",
            new Object[] { listaErroriValidazione }, null);
      }

    } catch (IOException e) {

    }

    return richiestaListaCUPXML;

  }

  /**
   * Richiesta di dettaglio CUP
   *
   * @param id
   * @param cupwsuser
   * @param cupwspass
   * @return
   * @throws GestoreException
   */
  public String richiestaDettaglioCUPXML(String cupwsuser, String cupwspass,
      HashMap<String, String> hMapParametri) throws GestoreException {

    String richiestaDettaglioCUPXML = null;

    try {

      RICHIESTADETTAGLIOCUPDocument richiestaDettaglioCUPDocument = RICHIESTADETTAGLIOCUPDocument.Factory.newInstance();
      richiestaDettaglioCUPDocument.documentProperties().setEncoding("UTF-8");
      RICHIESTADETTAGLIOCUP richiestaDettaglioCUP = richiestaDettaglioCUPDocument.addNewRICHIESTADETTAGLIOCUP();

      // Informazioni generali
      richiestaDettaglioCUP.addNewIDRICHIESTA().newCursor().setTextValue("1");
      richiestaDettaglioCUP.addNewUSER().newCursor().setTextValue(cupwsuser);
      richiestaDettaglioCUP.addNewPASSWORD().newCursor().setTextValue(cupwspass);
      String codiceCUP = (hMapParametri.get("codiceCUP"));
      if (codiceCUP != null && codiceCUP != "")
      {
        richiestaDettaglioCUP.addNewCODICECUP().newCursor().setTextValue(codiceCUP);
      }

      // Conversione in stringa
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      richiestaDettaglioCUPDocument.save(baos);
      richiestaDettaglioCUPXML = baos.toString();
      baos.close();

      // Controllo degli errori di validazione XML
      ArrayList<Object> validationErrors = new ArrayList<Object>();
      XmlOptions validationOptions = new XmlOptions();
      validationOptions.setErrorListener(validationErrors);
      boolean isValid = richiestaDettaglioCUPDocument.validate(validationOptions);

      if (!isValid) {
        String listaErroriValidazione = "";
        Iterator<?> iter = validationErrors.iterator();
        while (iter.hasNext()) {
          listaErroriValidazione += iter.next() + "\n";
        }
        logger.error("La richiesta del dettaglio del CUP non rispetta il formato previsto: "
            + richiestaDettaglioCUPXML
            + "\n"
            + listaErroriValidazione);
        throw new GestoreException(
            "La richiesta del dettaglio CUP non rispetta il formato previsto",
            "richiestaDettagllioXMLCUP.validate",
            new Object[] { listaErroriValidazione }, null);
      }

    } catch (IOException e) {

    }

    return richiestaDettaglioCUPXML;

  }

  /**
   * Richiesta di chiusura revoca CUP
   *
   * @param id
   * @param cupwsuser
   * @param cupwspass
   * @return
   * @throws GestoreException
   */
  public String richiestaChiusuraRevocaCUPXML(String cupwsuser, String cupwspass,
      HashMap<String, String> hMapParametri) throws GestoreException {

    String richiestaChiusuraRevocaCUPXML = null;

    try {

      RICHIESTACHIUSURAREVOCACUPDocument richiestaChiusuraRevocaCUPDocument = RICHIESTACHIUSURAREVOCACUPDocument.Factory.newInstance();
      richiestaChiusuraRevocaCUPDocument.documentProperties().setEncoding("UTF-8");
      RICHIESTACHIUSURAREVOCACUP richiestaChiusuraRevocaCUP = richiestaChiusuraRevocaCUPDocument.addNewRICHIESTACHIUSURAREVOCACUP();

      // Informazioni generali
      String id = (hMapParametri.get("id"));
      if (id != null && id != "")
      {
    	  richiestaChiusuraRevocaCUP.setIDRICHIESTA(id);
      }
      
      richiestaChiusuraRevocaCUP.setUSER(cupwsuser);
      richiestaChiusuraRevocaCUP.setPASSWORD(cupwspass);
      CUPCHIUSURAREVOCA cupChiusuraRevoca = richiestaChiusuraRevocaCUP.addNewCUPCHIUSURAREVOCA();
      String codiceCUP = (hMapParametri.get("codiceCUP"));
      if (codiceCUP != null && codiceCUP != "")
      {
    	  cupChiusuraRevoca.addNewCODICECUP().newCursor().setTextValue(codiceCUP);
      }
      String operazione = (hMapParametri.get("tipoOperazione"));
      if (operazione != null && operazione != "")
      {
    	  cupChiusuraRevoca.setTipoOperazione(CUPCHIUSURAREVOCADocument.CUPCHIUSURAREVOCA.TipoOperazione.Enum.forString(operazione));
      }
      cupChiusuraRevoca.setIdProgetto("1");
      // Conversione in stringa
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      richiestaChiusuraRevocaCUPDocument.save(baos);
      richiestaChiusuraRevocaCUPXML = baos.toString();
      baos.close();

      // Controllo degli errori di validazione XML
      ArrayList<Object> validationErrors = new ArrayList<Object>();
      XmlOptions validationOptions = new XmlOptions();
      validationOptions.setErrorListener(validationErrors);
      boolean isValid = richiestaChiusuraRevocaCUPDocument.validate(validationOptions);

      if (!isValid) {
        String listaErroriValidazione = "";
        Iterator<?> iter = validationErrors.iterator();
        while (iter.hasNext()) {
          listaErroriValidazione += iter.next() + "\n";
        }
        logger.error("La richiesta della chiusura revoca del CUP non rispetta il formato previsto: "
            + richiestaChiusuraRevocaCUPXML
            + "\n"
            + listaErroriValidazione);
        throw new GestoreException(
            "La richiesta della chiusura revoca CUP non rispetta il formato previsto",
            "richiestaDettagllioXMLCUP.validate",
            new Object[] { listaErroriValidazione }, null);
      }

    } catch (IOException e) {

    }

    return richiestaChiusuraRevocaCUPXML;

  }



  /**
   * Restituisce la lista dei risultati a fronte di una ricerca CUP
   *
   * @param id
   * @param rispostaDatiXML
   * @return
   * @throws GestoreException
   */
  public List<Vector<?>> listaCUPXML(String rispostaDatiXML)
          throws GestoreException {

        if (logger.isDebugEnabled()) logger.debug("listaCUPXML: inizio metodo");

        // Controllo utente
        if ("Utente non autorizzato".equals(rispostaDatiXML)) {
          throw new GestoreException(
              "L'elaborazione della richiesta di generazione CUP restituisce il seguente esito: utente non autorizzato",
              "dettaglioGenerazioneXMLCUP.esito",
              new Object[] { "Utente non autorizzato" }, null);
        }

        List<Vector<?>> risultatoListaCUP = new Vector<Vector<?>>();

        try {

          LISTACUPDocument listaCUPDocument = LISTACUPDocument.Factory.parse(rispostaDatiXML);

          // Controllo degli errori di validazione XML
          ArrayList<Object> validationErrors = new ArrayList<Object>();
          XmlOptions validationOptions = new XmlOptions();
          validationOptions.setErrorListener(validationErrors);
          boolean isValid = listaCUPDocument.validate(validationOptions);


          if (!isValid) {
            String listaErroriValidazione = "";
            Iterator<?> iter = validationErrors.iterator();
            while (iter.hasNext()) {
              listaErroriValidazione += iter.next() + "\n";
            }
            logger.error("La risposta alla richiesta di lista CUP non rispetta il formato previsto: "
                + rispostaDatiXML
                + "\n"
                + listaErroriValidazione);
            throw new GestoreException(
                "La risposta alla richiesta di lista CUP non rispetta il formato previsto",
                "dettaglioListaXMLCUP.validate",
                new Object[] { listaErroriValidazione }, null);
          }

          LISTACUP listaCUP = listaCUPDocument.getLISTACUP();

          // Esito e descrizione dell'elaborazione
          String esito_elaborazione = listaCUP.getDETTAGLIOELABORAZIONE().getESITOELABORAZIONE().newCursor().getTextValue();
          String descrizione_esito_elaborazione = listaCUP.getDETTAGLIOELABORAZIONE().getDESCRIZIONEESITOELABORAZIONE().newCursor().getTextValue();

          if ("KO".equals(esito_elaborazione)) {
            throw new GestoreException(
                "L'elaborazione della lista CUP restituisce il seguente esito: "
                    + descrizione_esito_elaborazione, "dettaglioListaXMLCUP.esito",
                new Object[] { descrizione_esito_elaborazione }, null);
          } else {
            // Dettaglio CUP
            DETTAGLIOCUP dettaglioCUP[] = listaCUP.getDETTAGLIOCUPArray();
            for (int i = 0; i < dettaglioCUP.length; i++) {
              DETTAGLIOCUP dettaglioCUPSingolo = dettaglioCUP[i];

              // Anno della decisione
              String anno = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getAnnoDecisione();

              // Codice CUP
              String cup = dettaglioCUPSingolo.getCODICECUP().newCursor().getTextValue();

              // Data generazione
              String data = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getDataGenerazione();

              // Natura
              String natura = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getNatura();

              // Tipologia
              String tipologia = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getTipologia();

              // Descrizione delle localizzazione
              it.cup.ws.lista.out.LOCALIZZAZIONEDocument.LOCALIZZAZIONE localizzazione[] = dettaglioCUPSingolo.getLOCALIZZAZIONEArray();
              String descrizioneLocalizzazione = "";
              for (int j = 0; j < localizzazione.length; j++) {
                if (j > 0) descrizioneLocalizzazione += ", ";
                descrizioneLocalizzazione += localizzazione[j].getDescrizione();
              }

              // Costo
              String costo = dettaglioCUPSingolo.getFINANZIAMENTO().getCosto();

              // Finanziamento
              String finanziamento = dettaglioCUPSingolo.getFINANZIAMENTO().getFinanziamento();

              // Descrizione intervento
              String descrizioneSintetica = null;
              if (dettaglioCUPSingolo.getDESCRIZIONE().isSetLAVORIPUBBLICI()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getDescSintetica();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetCONCESSIONEINCENTIVIUNITAPRODUTTIVE()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getDescSintetica();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetREALIZZACQUISTOSERVIZIRICERCA()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getDescSintetica();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetREALIZZACQUISTOSERVIZIFORMAZIONE()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getDescSintetica();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA().getDescSintetica();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetACQUISTOBENI()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getACQUISTOBENI().getDescSintetica();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetPARTECIPAZIONARIECONFERIMCAPITALE()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getDescSintetica();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getDescSintetica();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetCUPCUMULATIVO()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getCUPCUMULATIVO().getDescSintetica();
              }

              Vector<String> dati = new Vector<String>();
              dati.add(anno);
              dati.add(cup);
              dati.add(data);
              dati.add(natura);
              dati.add(tipologia);
              dati.add(descrizioneSintetica);
              dati.add(descrizioneLocalizzazione);
              dati.add(costo);
              dati.add(finanziamento);

              risultatoListaCUP.add(dati);
            }
          }

        } catch (XmlException e) {
          throw new GestoreException(
              "Errore generico nella lettura della lista CUP: " + rispostaDatiXML,
              "dettaglioListaXMLCUP.error", new Object[] { rispostaDatiXML }, e);
        }

        if (logger.isDebugEnabled()) logger.debug("listaCUPXML: fine metodo");

        return risultatoListaCUP;

      }

  /**
   * cfdettaglio
   * Restituisce il dettaglio del risultato a fronte di una ricerca CUP
   *
   * @param id
   * @param rispostaDatiXML
   * @return
   * @throws GestoreException
   */
  public DettaglioCUP dettaglioCUPXML(String rispostaDatiXML)
          throws GestoreException {

        if (logger.isDebugEnabled()) logger.debug("dettaglioCUPXML: inizio metodo");

        // Controllo utente
        if ("Utente non autorizzato".equals(rispostaDatiXML)) {
          throw new GestoreException(
              "L'elaborazione della richiesta di generazione CUP restituisce il seguente esito: utente non autorizzato",
              "dettaglioGenerazioneXMLCUP.esito",
              new Object[] { "Utente non autorizzato" }, null);
        }

        // Controllo esistenza CUP
        if ("Codice CUP non valido".equals(rispostaDatiXML)) {
          throw new GestoreException(
              "L'elaborazione della richiesta di generazione CUP restituisce il seguente esito: Codice CUP non valido",
              "dettaglioGenerazioneXMLCUP.esito",
              new Object[] { "Codice CUP non valido" }, null);
        }

        DettaglioCUP risultatoDettaglioCUP = new DettaglioCUP();

        try {

          DETTAGLIOCUPDocument dettaglioCUPDocument = DETTAGLIOCUPDocument.Factory.parse(rispostaDatiXML);

          // Controllo degli errori di validazione XML
          ArrayList<Object> validationErrors = new ArrayList<Object>();
          XmlOptions validationOptions = new XmlOptions();
          validationOptions.setErrorListener(validationErrors);
          boolean isValid = dettaglioCUPDocument.validate(validationOptions);


          if (!isValid) {
            String listaErroriValidazione = "";
            Iterator<?> iter = validationErrors.iterator();
            while (iter.hasNext()) {
              listaErroriValidazione += iter.next() + "\n";
            }
            logger.error("La risposta alla richiesta di dettaglio CUP non rispetta il formato previsto: "
                + rispostaDatiXML
                + "\n"
                + listaErroriValidazione);
            throw new GestoreException(
                "La risposta alla richiesta di dettaglio CUP non rispetta il formato previsto",
                "dettaglioDettaglioXMLCUP.validate",
                new Object[] { listaErroriValidazione }, null);
          }

         it.cup.ws.dettaglio.out.DETTAGLIOCUPDocument.DETTAGLIOCUP dettaglioCUPSingolo = dettaglioCUPDocument.getDETTAGLIOCUP();


          if ("OK".equals("OK")) {//ALWAYS

            // Dettaglio CUP

            //for (int i = 0; i < dettaglioCUP.length; i++) {

              // Anno della decisione
              String anno = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getAnnoDecisione();

              // Codice CUP
              String cup = dettaglioCUPSingolo.getCODICECUP().newCursor().getTextValue();

              //cup Master
              String cupMaster = dettaglioCUPSingolo.getMASTER().getCupMaster();

              //Ragioni del collegamento
              String ragioniCollegamento = dettaglioCUPSingolo.getMASTER().getRagioniCollegamento();


              // Data generazione
              String dataDef = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getDataGenerazione();

              Date dataDefinitivo = UtilityDate.convertiData(dataDef,UtilityDate.FORMATO_GG_MM_AAAA);

              // Natura
              String idnatura = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getIdNatura();

              // Tipologia
              String idtipologia = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getIdTipologia();

              // Settore
              String idsettore = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getIdSettore();

              // Sotto Settore
              String idsottosettore = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getIdSottosettore();

              // Categoria
              String idcategoria = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getIdCategoria();

              //CPV gestione
              String cpv = "";
              String cpv1 = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getCpv1();
              String cpv2 = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getCpv2();
              String cpv3 = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getCpv3();
              String cpv4 = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getCpv4();
              String cpv5 = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getCpv5();
              String cpv6 = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getCpv6();
              String cpv7 = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getCpv7();
              cpv1 = UtilityStringhe.convertiNullInStringaVuota(cpv1);
              if(!"".equals(cpv1)) cpv = cpv1;
              cpv2 = UtilityStringhe.convertiNullInStringaVuota(cpv2);
              if(!"".equals(cpv2)) cpv = cpv2;
              cpv3 = UtilityStringhe.convertiNullInStringaVuota(cpv3);
              if(!"".equals(cpv3)) cpv = cpv3;
              cpv4 = UtilityStringhe.convertiNullInStringaVuota(cpv4);
              if(!"".equals(cpv4)) cpv = cpv4;
              cpv5 = UtilityStringhe.convertiNullInStringaVuota(cpv5);
              if(!"".equals(cpv5)) cpv = cpv5;
              cpv6 = UtilityStringhe.convertiNullInStringaVuota(cpv6);
              if(!"".equals(cpv6)) cpv = cpv6;
              cpv7 = UtilityStringhe.convertiNullInStringaVuota(cpv7);
              if(!"".equals(cpv7)) cpv = cpv7;




              // Cumulativo
              String cumulativo = dettaglioCUPSingolo.getDATIGENERALIPROGETTO().getCumulativo();
              if(cumulativo!=null && !"".equals(cumulativo)){
                if("Si".equals(cumulativo))
                {
                  cumulativo = "1";
                }else{
                  cumulativo = "2";
                }
              }

              // Sponsorizzazione
              String sponsorizzazione = "";
              String descSponsorizzazione = "";
              boolean boolSponsorizzazione = dettaglioCUPSingolo.getFINANZIAMENTO().isSetSponsorizzazione();
              if(boolSponsorizzazione)
              {
                descSponsorizzazione = dettaglioCUPSingolo.getFINANZIAMENTO().getSponsorizzazione();
                if(descSponsorizzazione.equals("NO"))
                {
                  sponsorizzazione = "N";
                }
                if(descSponsorizzazione.equals("Parziale"))
                {
                  sponsorizzazione = "P";
                }
                if(descSponsorizzazione.equals("Totale"))
                {
                  sponsorizzazione = "T";
                }

              }
              // FinanzaProgetto
              String finanzaProgetto ="";
              String descFinanzaProgetto ="";
              boolean boolFinanzaProgetto = dettaglioCUPSingolo.getFINANZIAMENTO().isSetFinanzaProgetto();
              if(boolFinanzaProgetto)
              {
                descFinanzaProgetto = dettaglioCUPSingolo.getFINANZIAMENTO().getFinanzaProgetto();
                if(descFinanzaProgetto.equals("Assistita"))
                {
                  finanzaProgetto = "A";
                }
                if(descFinanzaProgetto.equals("NO"))
                {
                  finanzaProgetto = "N";
                }
                if(descFinanzaProgetto.equals("Pura"))
                {
                  finanzaProgetto = "P";
                }

              }


              // Descrizione delle localizzazione
              it.cup.ws.dettaglio.out.LOCALIZZAZIONEDocument.LOCALIZZAZIONE localizzazione[] = dettaglioCUPSingolo.getLOCALIZZAZIONEArray();
              String descrizioneLocalizzazione = "";
              LocalizzazioneCUP[] localizzazioni = new LocalizzazioneCUP[localizzazione.length];
              for (int j = 0; j < localizzazione.length; j++) {
                if (j > 0) descrizioneLocalizzazione += ", ";
                descrizioneLocalizzazione += localizzazione[j].getDescrizione();
                LocalizzazioneCUP locSingle = new LocalizzazioneCUP();
                String _stato = localizzazione[j].getStato();
                _stato = UtilityStringhe.fillLeft(_stato, '0', 2);
                locSingle.setStato(_stato);
                String _regione = localizzazione[j].getRegione();
                _regione = UtilityStringhe.fillLeft(_regione, '0', 3);
                locSingle.setRegione(_regione);
                String _provincia = localizzazione[j].getProvincia();
                _provincia = UtilityStringhe.fillLeft(_provincia, '0', 3);
                locSingle.setProvincia(_provincia);
                String _comune = localizzazione[j].getComune();
                _comune = localizzazione[j].getCodice();
                _comune = UtilityStringhe.fillLeft(_comune, '0', 9);
                locSingle.setComune(_comune);
                localizzazioni[j] = locSingle;
              }

              // Costo
              String costoStr = dettaglioCUPSingolo.getFINANZIAMENTO().getCosto();
              //fare la verifica...
              Double costo = new Double(costoStr);

              // Finanziamento
              String finanziamentoStr = dettaglioCUPSingolo.getFINANZIAMENTO().getFinanziamento();
              Double finanziamento = new Double(finanziamentoStr);

              //Copertuta Finanziaria

              CODICETIPOLOGIACOPFINANZ[] codiciTipoCopFin = dettaglioCUPSingolo.getFINANZIAMENTO().getCODICETIPOLOGIACOPFINANZArray();

              String[] codTipoCopFin = new String[codiciTipoCopFin.length];
              for (int j = 0; j < codiciTipoCopFin.length; j++) {

                codTipoCopFin[j] = dettaglioCUPSingolo.getFINANZIAMENTO().getCODICETIPOLOGIACOPFINANZArray(j).newCursor().getTextValue();

               }

              //Altre informazioni
              String indAreaRifer = null;
              String strTipoIndAreaRifer = null;
              String strumProgr = null;
              String descStrumProgr = null;
              String altreInformazioni = null;
              String beneficiario = null;
              String nomeStrInfrastr = null;
              String strInfrastrUnica = null;
              String denomImprStab = null;
              String denomImprStabPrec = null;
              String ragioneSociale = null;
              String ragioneSocialePrec = null;
              String partitaIVA = null;
              String ente = null;
              String attoAmministrativo = null;
              String scopoIntervento = null;
              String descrizioneIntervento = null;
              String descrizioneSintetica = null;
              String denominazioneProgetto = null;
              String obiettivoCorso = null;
              String modInterventoFrequenza = null;
              String servizio = null;
              String bene = null;




              if (dettaglioCUPSingolo.getDESCRIZIONE().isSetLAVORIPUBBLICI()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getDescSintetica();
                descrizioneIntervento = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getDescrizioneIntervento();
                indAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getIndAreaRifer();
                it.cup.ws.dettaglio.out.LAVORIPUBBLICIDocument.LAVORIPUBBLICI.TipoIndAreaRifer.Enum TipoIndAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getTipoIndAreaRifer();
                if(TipoIndAreaRifer != null)
                {
                  strTipoIndAreaRifer = TipoIndAreaRifer.toString();
                }
                strumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getIdStrumProgr();
                descStrumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getDescStrumProgr();
                altreInformazioni = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getAltreInformazioni();
                nomeStrInfrastr = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getNomeStrInfrastr();
                it.cup.ws.dettaglio.out.LAVORIPUBBLICIDocument.LAVORIPUBBLICI.StrInfrastrUnica.Enum infrastrUnica = dettaglioCUPSingolo.getDESCRIZIONE().getLAVORIPUBBLICI().getStrInfrastrUnica();
                if(infrastrUnica!=null && !"".equals(infrastrUnica)){
                  it.cup.ws.dettaglio.out.LAVORIPUBBLICIDocument.LAVORIPUBBLICI.StrInfrastrUnica.Enum infrastrUnicaNO = it.cup.ws.dettaglio.out.LAVORIPUBBLICIDocument.LAVORIPUBBLICI.StrInfrastrUnica.NO;
                  it.cup.ws.dettaglio.out.LAVORIPUBBLICIDocument.LAVORIPUBBLICI.StrInfrastrUnica.Enum infrastrUnicaSI = it.cup.ws.dettaglio.out.LAVORIPUBBLICIDocument.LAVORIPUBBLICI.StrInfrastrUnica.SI;
                  if (infrastrUnicaSI.equals(infrastrUnica)) {
                    strInfrastrUnica = "1";
                  }
                  if (infrastrUnicaNO.equals(infrastrUnica)) {
                    strInfrastrUnica = "2";
                  }
                }

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetCONCESSIONEINCENTIVIUNITAPRODUTTIVE()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getDescSintetica();
                indAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getIndAreaRifer();
                Enum TipoIndAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getTipoIndAreaRifer();
                if(TipoIndAreaRifer != null)
                {
                  strTipoIndAreaRifer = TipoIndAreaRifer.toString();
                }
                strumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getIdStrumProgr();
                descStrumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getDescStrumProgr();
                altreInformazioni = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getAltreInformazioni();
                denomImprStab = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getDenominazioneImpresaStabilimento();
                denomImprStabPrec = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getDenominazioneImpresaStabilimentoPrec();
                partitaIVA = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONEINCENTIVIUNITAPRODUTTIVE().getPartitaIva();


              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetREALIZZACQUISTOSERVIZIRICERCA()) {
                descrizioneIntervento = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getDescrizioneIntervento();
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getDescSintetica();
                indAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getIndAreaRifer();
                it.cup.ws.dettaglio.out.REALIZZACQUISTOSERVIZIRICERCADocument.REALIZZACQUISTOSERVIZIRICERCA.TipoIndAreaRifer.Enum TipoIndAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getTipoIndAreaRifer();
                if(TipoIndAreaRifer != null)
                {
                  strTipoIndAreaRifer = TipoIndAreaRifer.toString();
                }
                strumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getIdStrumProgr();
                descStrumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getDescStrumProgr();
                altreInformazioni = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getAltreInformazioni();
                denominazioneProgetto = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getDenominazioneProgetto();
                ente = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIRICERCA().getEnte();


              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetREALIZZACQUISTOSERVIZIFORMAZIONE()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getDescSintetica();
                indAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getIndAreaRifer();
                it.cup.ws.dettaglio.out.REALIZZACQUISTOSERVIZIFORMAZIONEDocument.REALIZZACQUISTOSERVIZIFORMAZIONE.TipoIndAreaRifer.Enum TipoIndAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getTipoIndAreaRifer();
                if(TipoIndAreaRifer != null)
                {
                  strTipoIndAreaRifer = TipoIndAreaRifer.toString();
                }
                strumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getIdStrumProgr();
                descStrumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getDescStrumProgr();
                altreInformazioni = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getAltreInformazioni();
                denominazioneProgetto = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getDenomProgetto();
                obiettivoCorso = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getObiettCorso();
                modInterventoFrequenza = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZIFORMAZIONE().getModInterventoFrequenza();


              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA().getDescSintetica();
                indAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA().getIndAreaRifer();
                it.cup.ws.dettaglio.out.REALIZZACQUISTOSERVIZINOFORMAZIONERICERCADocument.REALIZZACQUISTOSERVIZINOFORMAZIONERICERCA.TipoIndAreaRifer.Enum TipoIndAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA().getTipoIndAreaRifer();
                if(TipoIndAreaRifer != null)
                {
                  strTipoIndAreaRifer = TipoIndAreaRifer.toString();
                }
                strumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA().getIdStrumProgr();
                descStrumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA().getDescStrumProgr();
                altreInformazioni = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA().getAltreInformazioni();
                nomeStrInfrastr = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA().getNomeStrInfrastr();
                servizio = dettaglioCUPSingolo.getDESCRIZIONE().getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA().getServizio();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetACQUISTOBENI()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getACQUISTOBENI().getDescSintetica();
                indAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getACQUISTOBENI().getIndAreaRifer();
                it.cup.ws.dettaglio.out.ACQUISTOBENIDocument.ACQUISTOBENI.TipoIndAreaRifer.Enum TipoIndAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getACQUISTOBENI().getTipoIndAreaRifer();
                if(TipoIndAreaRifer != null)
                {
                  strTipoIndAreaRifer = TipoIndAreaRifer.toString();
                }
                strumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getACQUISTOBENI().getIdStrumProgr();
                descStrumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getACQUISTOBENI().getDescStrumProgr();
                altreInformazioni = dettaglioCUPSingolo.getDESCRIZIONE().getACQUISTOBENI().getAltreInformazioni();
                nomeStrInfrastr = dettaglioCUPSingolo.getDESCRIZIONE().getACQUISTOBENI().getNomeStrInfrastr();
                bene = dettaglioCUPSingolo.getDESCRIZIONE().getACQUISTOBENI().getBene();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetPARTECIPAZIONARIECONFERIMCAPITALE()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getDescSintetica();
                indAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getIndAreaRifer();
                it.cup.ws.dettaglio.out.PARTECIPAZIONARIECONFERIMCAPITALEDocument.PARTECIPAZIONARIECONFERIMCAPITALE.TipoIndAreaRifer.Enum TipoIndAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getTipoIndAreaRifer();
                if(TipoIndAreaRifer != null)
                {
                  strTipoIndAreaRifer = TipoIndAreaRifer.toString();
                }
                strumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getIdStrumProgr();
                descStrumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getDescStrumProgr();
                altreInformazioni = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getAltreInformazioni();
                ragioneSociale = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getRagioneSociale();
                ragioneSocialePrec = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getRagioneSocialePrec();
                partitaIVA = dettaglioCUPSingolo.getDESCRIZIONE().getPARTECIPAZIONARIECONFERIMCAPITALE().getPartitaIva();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE()) {
                descrizioneIntervento = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getDescIntervento();
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getDescSintetica();
                indAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getIndAreaRifer();
                it.cup.ws.dettaglio.out.CONCESSIONECONTRIBUTINOUNITAPRODUTTIVEDocument.CONCESSIONECONTRIBUTINOUNITAPRODUTTIVE.TipoIndAreaRifer.Enum TipoIndAreaRifer = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getTipoIndAreaRifer();
                if(TipoIndAreaRifer != null)
                {
                  strTipoIndAreaRifer = TipoIndAreaRifer.toString();
                }
                strumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getIdStrumProgr();
                descStrumProgr = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getDescStrumProgr();
                altreInformazioni = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getAltreInformazioni();
                beneficiario = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getBeneficiario();
                partitaIVA = dettaglioCUPSingolo.getDESCRIZIONE().getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE().getPartitaIva();

              } else if (dettaglioCUPSingolo.getDESCRIZIONE().isSetCUPCUMULATIVO()) {
                descrizioneSintetica = dettaglioCUPSingolo.getDESCRIZIONE().getCUPCUMULATIVO().getDescSintetica();
                altreInformazioni = dettaglioCUPSingolo.getDESCRIZIONE().getCUPCUMULATIVO().getAltreInformazioni();
                attoAmministrativo = dettaglioCUPSingolo.getDESCRIZIONE().getCUPCUMULATIVO().getAttoAmministr();
                scopoIntervento = dettaglioCUPSingolo.getDESCRIZIONE().getCUPCUMULATIVO().getScopoIntervento();

              }

              Integer anno_decisione= new Integer(anno);
              risultatoDettaglioCUP.setAnno_decisione(anno_decisione);
              risultatoDettaglioCUP.setTipoIndAreaRifer(strTipoIndAreaRifer);
              risultatoDettaglioCUP.setIndAreaRifer(indAreaRifer);
              risultatoDettaglioCUP.setStrumProgr(strumProgr);
              risultatoDettaglioCUP.setDescStrumProgr(descStrumProgr);
              risultatoDettaglioCUP.setAltreInformazioni(altreInformazioni);
              Double costo_double = new Double(costo);
              risultatoDettaglioCUP.setCosto(costo_double);
              risultatoDettaglioCUP.setDataDefinitivo(dataDefinitivo);
              risultatoDettaglioCUP.setNatura(idnatura);
              risultatoDettaglioCUP.setTipologia(idtipologia);
              risultatoDettaglioCUP.setSettore(idsettore);
              risultatoDettaglioCUP.setSottosettore(idsottosettore);
              risultatoDettaglioCUP.setCategoria(idcategoria);
              risultatoDettaglioCUP.setCumulativo(cumulativo);
              risultatoDettaglioCUP.setBeneficiario(beneficiario);
              risultatoDettaglioCUP.setNomeStrInfrastr(nomeStrInfrastr);
              risultatoDettaglioCUP.setStrInfrastrUnica(strInfrastrUnica);
              risultatoDettaglioCUP.setDenomImprStab(denomImprStab);
              risultatoDettaglioCUP.setDenomImprStabPrec(denomImprStabPrec);
              risultatoDettaglioCUP.setRagioneSociale(ragioneSociale);
              risultatoDettaglioCUP.setRagioneSocialePrec(ragioneSocialePrec);
              risultatoDettaglioCUP.setPartitaIVA(partitaIVA);
              risultatoDettaglioCUP.setDenominazioneProgetto(denominazioneProgetto);
              risultatoDettaglioCUP.setEnte(ente);
              risultatoDettaglioCUP.setAttoAmministrativo(attoAmministrativo);
              risultatoDettaglioCUP.setScopoIntervento(scopoIntervento);
              risultatoDettaglioCUP.setDescrizioneIntervento(descrizioneIntervento);
              risultatoDettaglioCUP.setObiettivoCorso(obiettivoCorso);
              risultatoDettaglioCUP.setModInterventoFrequenza(modInterventoFrequenza);
              risultatoDettaglioCUP.setServizio(servizio);
              risultatoDettaglioCUP.setBene(bene);
              risultatoDettaglioCUP.setCpv(cpv);
              risultatoDettaglioCUP.setSponsorizzazione(sponsorizzazione);
              risultatoDettaglioCUP.setFinanza_progetto(finanzaProgetto);
              risultatoDettaglioCUP.setCosto(costo);
              risultatoDettaglioCUP.setFinanziamento(finanziamento);
              risultatoDettaglioCUP.setCodTipoCopFin(codTipoCopFin);
              risultatoDettaglioCUP.setLocalizzazioni(localizzazioni);


          }

        } catch (XmlException e) {
          throw new GestoreException(
              "Errore generico nella lettura del dettaglio CUP: " + rispostaDatiXML,
              "dettaglioDettaglioXMLCUP.error", new Object[] { rispostaDatiXML }, e);
        }

        if (logger.isDebugEnabled()) logger.debug("dettaglioCUPXML: fine metodo");

        return risultatoDettaglioCUP;

      }


}