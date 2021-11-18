package it.eldasoft.sil.pt.bl;

import it.eldasoft.cup.ws.DettaglioCUP;
import it.eldasoft.cup.ws.LocalizzazioneCUP;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.w9.common.CostantiSitatSA;
import it.mef.serviziCUP.ElaborazioniCUPProxy;
import it.mef.serviziCUP.types.EsitoElaborazioneCUP_Type;
import it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaChiusuraRevocaCUP_Type;
import it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type;
import it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type;
import it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaListaCUP_Type;
import it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_EsitoGenerazioneCUP_Type;
import it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_ListaCUP_Type;
import it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_RisultatoChiusuraRevocaCUP_Type;
import it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

public class GestioneServiziCUPManager {

  static Logger                 logger                                   = Logger.getLogger(GestioneServiziCUPManager.class);

  private SqlManager            sqlManager;

  private GestioneXMLCUPManager gestioneXMLCUPManager;

  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  public void setGestioneXMLCUPManager(
      GestioneXMLCUPManager gestioneXMLCUPManager) {
    this.gestioneXMLCUPManager = gestioneXMLCUPManager;
  }

  public String richiestaGenerazioneCUP(Long id, String cupwsuser,
      String cupwspass, Long syscon) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("richiestaGenerazioneCUP: inizio metodo");

    String cup = null;

    try {

      Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type richiestaGenerazioneCUP = new Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type();
      richiestaGenerazioneCUP.setTitoloRichiesta("Richiesta Generazione CUP");

      // Creazione del contenuto XML per la richiesta di generazione
      String richiestaDatiXML = this.gestioneXMLCUPManager.richiestaGenerazioneCUPXML(
          id, cupwsuser, cupwspass);

      richiestaGenerazioneCUP.setRichiesta(richiestaDatiXML.getBytes());

      Risposta_RichiestaRispostaSincrona_EsitoGenerazioneCUP_Type rispostaGenerazioneCUP = null;
      String type = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_TYPE);
      if (type.equals("0")) { //chiamata al WS
    	  ElaborazioniCUPProxy service = this.getServizioElaborazioniCUP();
    	  rispostaGenerazioneCUP = service.richiestaRispostaSincrona_RichiestaGenerazioneCUP(richiestaGenerazioneCUP);

      } else {	//chiamata alla PDD
    	  String url = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_URL) + "RichiestaRispostaSincrona_RichiestaGenerazioneCUP";
    	  String username = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_USER);
    	  String password = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_PASS);
    	  ProxyTrasparenteUtility proxy = new ProxyTrasparenteUtility (url, username, password);
    	  rispostaGenerazioneCUP = proxy.invokeEsitoGenerazioneCUP(richiestaGenerazioneCUP);
      }
      
      // Analisi della risposta alla richiesta di generazione CUP
      EsitoElaborazioneCUP_Type esitoElaborazione = rispostaGenerazioneCUP.getEsitoElaborazione();
      String esito_elaborazione = esitoElaborazione.getValue();
      if ("ELABORAZIONE_ESEGUITA".equals(esito_elaborazione)) {
          // Risposta
          String rispostaDatiXML = new String(
              rispostaGenerazioneCUP.getRisposta());

          // Lettura della risposta
          cup = this.gestioneXMLCUPManager.dettaglioGenerazioneCUPXML(id,
              rispostaDatiXML);

        } else {
          throw new GestoreException(
              "L'elaborazione della richiesta di generazione CUP restituisce il seguente esito: "
                  + esito_elaborazione, "dettaglioGenerazioneXMLCUP.esito",
              new Object[] { esito_elaborazione }, null);
        }
    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service di elaborazione CUP",
          "richiestaCUP.ws.error", e);

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service di elaborazione CUP",
          "richiestaCUP.ws.error", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("richiestaGenerazioneCUP: fine metodo");

    return cup;

  }

  /**
   * Richiesta lista CUP
   *
   * @param id
   * @param cupwsuser
   * @param cupwspass
   * @return
   * @throws GestoreException
   * @throws ServiceException
   * @throws RemoteException
   */
  public List<Vector<?>> richiestaListaCUP(String cupwsuser, String cupwspass,
      Long syscon, HashMap<String, String> hMapParametri)
      throws GestoreException {

    List<Vector<?>> risultatoListaCUP = null;

    if (logger.isDebugEnabled())
      logger.debug("richiestaListaCUP: inizio metodo");

    try {
      // Gestione della richiesta di ricerca della lista dei CUP ed invio dei
      // dati
      Richiesta_RichiestaRispostaSincrona_RichiestaListaCUP_Type richiestaListaCUP = new Richiesta_RichiestaRispostaSincrona_RichiestaListaCUP_Type();
      richiestaListaCUP.setTitoloRichiesta("Richiesta Lista CUP");

      // Creazione del contenuto XML per la richiesta di lista CUP assegnati
      String richiestaDatiXML = this.gestioneXMLCUPManager.richiestaListaCUPXML(
          cupwsuser, cupwspass, hMapParametri);
      richiestaListaCUP.setRichiesta(richiestaDatiXML.getBytes());

      Risposta_RichiestaRispostaSincrona_ListaCUP_Type rispostaListaCUP = null;
      String type = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_TYPE);
      if (type.equals("0")) { //chiamata al WS
    	  ElaborazioniCUPProxy service = this.getServizioElaborazioniCUP();
    	  rispostaListaCUP = service.richiestaRispostaSincrona_ListaCUP(richiestaListaCUP);

      } else {	//chiamata alla PDD
    	  String url = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_URL) + "RichiestaRispostaSincrona_ListaCUP";
    	  String username = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_USER);
    	  String password = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_PASS);
    	  ProxyTrasparenteUtility proxy = new ProxyTrasparenteUtility (url, username, password);
    	  rispostaListaCUP = proxy.invokeListaCUP(richiestaListaCUP);
      }
      
      // Analisi della risposta alla richiesta di lista
      EsitoElaborazioneCUP_Type esitoElaborazione = rispostaListaCUP.getEsitoElaborazione();
      String esito_elaborazione = esitoElaborazione.getValue();
      if ("ELABORAZIONE_ESEGUITA".equals(esito_elaborazione)) {

          // Risposta
          String rispostaDatiXML = new String(rispostaListaCUP.getRisposta());

          // Lettura della risposta e creazione lista con i soli campi necessari
          risultatoListaCUP = this.gestioneXMLCUPManager.listaCUPXML(rispostaDatiXML);

        } else {

          throw new GestoreException(
              "L'elaborazione della richiesta di ricerca delle lista CUP restituisce il seguente esito: "
                  + esito_elaborazione, "dettaglioListaXMLCUP.esito",
              new Object[] { esito_elaborazione }, null);
        }
    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service di elaborazione CUP",
          "dettaglioListaXMLCUP.ws.error", e);

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service di elaborazione CUP",
          "dettaglioListaXMLCUP.ws.error", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("richiestaListaCUP: fine metodo");

    return risultatoListaCUP;

  }


  /**
   * Richiesta Dettaglio CUP
   *
   * @param id
   * @param cupwsuser
   * @param cupwspass
   * @return
   * @throws GestoreException
   * @throws ServiceException
   * @throws RemoteException
   */
  public DettaglioCUP richiestaDettaglioCUP(String cupwsuser, String cupwspass,
      Long syscon, HashMap<String, String> hMapParametri)
      throws GestoreException {

    DettaglioCUP risultatoDettaglioCUP = null;

    if (logger.isDebugEnabled())
      logger.debug("richiestaDettaglioCUP: inizio metodo");

    try {
      // Gestione della richiesta di ricerca del dettaglio CUP
      Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type richiestaDettaglioCUP = new Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type();
      richiestaDettaglioCUP.setTitoloRichiesta("Richiesta Dettaglio CUP");

      // Creazione del contenuto XML per la richiesta di lista CUP assegnati
      String richiestaDatiXML = this.gestioneXMLCUPManager.richiestaDettaglioCUPXML(
          cupwsuser, cupwspass, hMapParametri);
      richiestaDettaglioCUP.setRichiesta(richiestaDatiXML.getBytes());

      Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type rispostaDettaglioCUP = null;
      String type = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_TYPE);
      if (type.equals("0")) { //chiamata al WS
    	  ElaborazioniCUPProxy service = this.getServizioElaborazioniCUP();
    	  rispostaDettaglioCUP = service.richiestaRispostaSincrona_RichiestaDettaglioCUP(richiestaDettaglioCUP);

      } else {	//chiamata alla PDD
    	  String url = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_URL) + "RichiestaRispostaSincrona_RichiestaDettaglioCUP";
    	  String username = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_USER);
    	  String password = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_PASS);
    	  ProxyTrasparenteUtility proxy = new ProxyTrasparenteUtility (url, username, password);
    	  rispostaDettaglioCUP = proxy.invokeRisultatoDettaglioCUP(richiestaDettaglioCUP);
      }
      

      // Analisi della risposta alla richiesta di dettaglio
      EsitoElaborazioneCUP_Type esitoElaborazione = rispostaDettaglioCUP.getEsitoElaborazione();
      String esito_elaborazione = esitoElaborazione.getValue();
      if ("ELABORAZIONE_ESEGUITA".equals(esito_elaborazione)) {

          // Risposta
          String rispostaDatiXML = new String(rispostaDettaglioCUP.getRisposta());

          // Lettura della risposta e creazione lista con i soli campi necessari
          risultatoDettaglioCUP = this.gestioneXMLCUPManager.dettaglioCUPXML(rispostaDatiXML);

        } else {

          throw new GestoreException(
              "L'elaborazione della richiesta di ricerca del dettaglio CUP restituisce il seguente esito: "
                  + esito_elaborazione, "dettaglioListaXMLCUP.esito",
              new Object[] { esito_elaborazione }, null);
        }
    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service di elaborazione CUP",
          "dettaglioDettaglioXMLCUP.ws.error", e);

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service di elaborazione CUP",
          "dettaglioDettaglioXMLCUP.ws.error", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("richiestaDettaglioCUP: fine metodo");

    return risultatoDettaglioCUP;

  }

  /**
   * Richiesta Chiusura Revoca CUP
   *
   * @param id
   * @param cupwsuser
   * @param cupwspass
   * @return
   * @throws GestoreException
   * @throws ServiceException
   * @throws RemoteException
   */
  public boolean richiestaChiusuraRevocaCUP(String cupwsuser, String cupwspass,
      Long syscon, HashMap<String, String> hMapParametri)
      throws GestoreException {

    boolean risultatoChiusuraRevocaCUP = false;

    if (logger.isDebugEnabled())
      logger.debug("richiestaChiusuraRevocaCUP: inizio metodo");

    try {
      // Gestione della richiesta di ricerca del dettaglio CUP
      Richiesta_RichiestaRispostaSincrona_RichiestaChiusuraRevocaCUP_Type richiestaChiusuraRevocaCUP = new Richiesta_RichiestaRispostaSincrona_RichiestaChiusuraRevocaCUP_Type();
      richiestaChiusuraRevocaCUP.setTitoloRichiesta("Richiesta Chiusura Revoca CUP");
      String id = (hMapParametri.get("id"));
      // Creazione del contenuto XML per la richiesta di lista CUP assegnati
      String richiestaDatiXML = this.gestioneXMLCUPManager.richiestaChiusuraRevocaCUPXML(
          cupwsuser, cupwspass, hMapParametri);
      richiestaChiusuraRevocaCUP.setRichiesta(richiestaDatiXML.getBytes());

      Risposta_RichiestaRispostaSincrona_RisultatoChiusuraRevocaCUP_Type rispostaChiusuraRevocaCUP = null;
      String type = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_TYPE);
      if (type.equals("0")) { //chiamata al WS
    	  ElaborazioniCUPProxy service = this.getServizioElaborazioniCUP();
    	  rispostaChiusuraRevocaCUP = service.richiestaRispostaSincrona_RichiestaChiusuraRevocaCUP(richiestaChiusuraRevocaCUP);

      } else {	//chiamata alla PDD
    	  String url = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_URL) + "RichiestaRispostaSincrona_RichiestaChiusuraRevocaCUP";
    	  String username = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_USER);
    	  String password = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_PDD_PASS);
    	  ProxyTrasparenteUtility proxy = new ProxyTrasparenteUtility (url, username, password);
    	  rispostaChiusuraRevocaCUP = proxy.invokeRisultatoChiusuraRevocaCUP(richiestaChiusuraRevocaCUP);
      }
      

      // Analisi della risposta alla richiesta di dettaglio
      EsitoElaborazioneCUP_Type esitoElaborazione = rispostaChiusuraRevocaCUP.getEsitoElaborazione();
      String esito_elaborazione = esitoElaborazione.getValue();
      if ("ELABORAZIONE_ESEGUITA".equals(esito_elaborazione)) {

          // Risposta
          String rispostaDatiXML = new String(rispostaChiusuraRevocaCUP.getRisposta());

          risultatoChiusuraRevocaCUP = true;
          
          // Lettura della risposta
          risultatoChiusuraRevocaCUP = this.gestioneXMLCUPManager.dettaglioChiusuraRevocaCUPXML(Long.parseLong(id), rispostaDatiXML);

        } else {

          throw new GestoreException(
              "L'elaborazione della richiesta di chiusura/revoca CUP restituisce il seguente esito: "
                  + esito_elaborazione, "errors.richiestaCUP.error",
              new Object[] { esito_elaborazione }, null);
        }
    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service di elaborazione CUP",
          "dettaglioDettaglioXMLCUP.ws.error", e);

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service di elaborazione CUP",
          "dettaglioDettaglioXMLCUP.ws.error", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("richiestaChiusuraRevocaCUP: fine metodo");

    return risultatoChiusuraRevocaCUP;

  }
  

  /**
   * Gestione del collegamento al servizio di elaborazioni CUP Restituisce il
   * servizio "ElaborazioniCUP_PortType"
   *
   * @return
   * @throws GestoreException
   * @throws ServiceException
   */
  private ElaborazioniCUPProxy getServizioElaborazioniCUP()
      throws GestoreException, ServiceException {

    // Indirizzo web service
    String url = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_WS_URL);
    if (url == null || "".equals(url)) {
      throw new GestoreException(
          "L'indirizzo per la connessione al web service non e' definito",
          "elaborazioneCUP.url.ws");
    }
/*
    // Gestione del certificato digitale
    String trustStore = ConfigManager.getValore(
        PROP_RICHIESTA_CUP_WS_TRUSTSTORE).trim();
    String trustStorePassword = ConfigManager.getValore(
            PROP_RICHIESTA_CUP_WS_TRUSTSTOREPASSWORD).trim();


    if (trustStore != null
        && !"".equals(trustStore)
        && trustStorePassword != null
        && !"".equals(trustStorePassword)) {
      if (new File(trustStore).exists()) {
        System.setProperty("javax.net.ssl.trustStore", trustStore);
        System.setProperty("javax.net.ssl.trustStorePassword",
            trustStorePassword);
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
      } else {
        logger.warn("Il file jks con il certificato digitale non esiste nel percorso specificato");
      }
    }

    // Gestione PROXY
    String proxyHost = UtilityStringhe.convertiStringaVuotaInNull(ConfigManager.getValore(
        PROP_RICHIESTA_CUP_WS_PROXY_HOST).trim());
    String proxyPort = UtilityStringhe.convertiStringaVuotaInNull(ConfigManager.getValore(
        PROP_RICHIESTA_CUP_WS_PROXY_PORT).trim());

    if (proxyHost != null) {
      System.setProperty("proxySet", "true");
      System.setProperty("http.proxyHost", proxyHost);
      if (proxyPort != null) {
        System.setProperty("http.proxyPort", proxyPort);
      }
    }
*/
    // Connessione al servizio
    ElaborazioniCUPProxy service = new ElaborazioniCUPProxy(url);

    return service;
  }

  /**
   * Gestione credenziali di accesso al servizio di elaborazione CUP
   *
   * @param cupwsuser
   * @param syscon
   * @param inserimento
   *        TRUE se si deve inserire la riga, FALSE se deve essere cancellata
   * @throws GestoreException
   */
  public void gestioneCUPWSUserPass(Long syscon, String cupwsuser,
      String cupwspass, boolean inserimento) throws GestoreException {
    // Memorizzazione dell'utente (CUPWSUSER) utilizzato per la connessione.
    // Secondo le specifiche del CIPE alla prima richiesta devono
    // essere indicate le credenziali di accesso (CUPWSUSER e CUPWSPASS
    // specifica
    // dell'utente),
    // se la richiesta va a buon fine si deve memorizzare l'utente
    // e per tutte le successive richieste successive utilizzare l'utente con
    // una CUPWSPASS generica fornita a Regione Toscana.
    // Se un invio successivo dovesse fallire bisogna cancellare l'utenza
    // salvata
    // per ritornare alla condizione iniziale e chiedere nuovamente all'utente
    // l'inserimento delle credenziali complete
    try {
      if (inserimento == true) {
        // Controllo se esiste gia' una riga con lo stesso SYSCON
        Long conteggio = (Long) this.sqlManager.getObject(
            "select count(*) from cupusrsys where syscon = ?",
            new Object[] { syscon });

        // Codifica della password
        ICriptazioneByte cupwspassICriptazioneByte = null;
        cupwspassICriptazioneByte = FactoryCriptazioneByte.getInstance(
            ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
            cupwspass.getBytes(), ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);

        String cupwspassCriptata = new String(
            cupwspassICriptazioneByte.getDatoCifrato());

        if (conteggio != null && conteggio.longValue() > 0) {
          this.sqlManager.update(
              "update cupusrsys set cupwsuser = ?, cupwspass = ? where syscon = ?",
              new Object[] { cupwsuser, cupwspassCriptata, syscon });
        } else {
          this.sqlManager.update(
              "insert into cupusrsys (cupwsuser, cupwspass, syscon) values (?,?,?)",
              new Object[] { cupwsuser, cupwspassCriptata, syscon });
        }
      } else {
        this.sqlManager.update("delete from cupusrsys where syscon = ?",
            new Object[] { syscon });
      }

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella gestione delle credenziali di accesso al servizio di elaborazione CUP",
          "gestioneCUPWSUserPass.error", e);
    } catch (CriptazioneException e) {
      throw new GestoreException(
          "Errore nella gestione delle credenziali di accesso al servizio di elaborazione CUP",
          "gestioneCUPWSUserPass.error", e);
    }
  }

  /**
   * Recupera le credenziali di accesso al servizio di elaborazione CUP
   *
   * @param syscon
   * @return
   * @throws GestoreException
   */
  public HashMap<String, String> recuperaCUPWSUserPass(Long syscon)
      throws GestoreException {

    HashMap<String, String> hMapCUPWSUserPass = new HashMap<String, String>();

    try {
      List<?> datiCUPUSRSYS = this.sqlManager.getVector(
          "select cupwsuser, cupwspass from cupusrsys where syscon = ?",
          new Object[] { syscon });
      if (datiCUPUSRSYS != null && datiCUPUSRSYS.size() > 0) {

        String cupwsuser = (String) SqlManager.getValueFromVectorParam(
            datiCUPUSRSYS, 0).getValue();
        hMapCUPWSUserPass.put("cupwsuser", cupwsuser);

        String cupwspass = (String) SqlManager.getValueFromVectorParam(
            datiCUPUSRSYS, 1).getValue();

        // Decodifica della password
        ICriptazioneByte cupwspassICriptazioneByte = null;
        cupwspassICriptazioneByte = FactoryCriptazioneByte.getInstance(
            ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
            cupwspass.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
        String cupwspassDecriptata = new String(
            cupwspassICriptazioneByte.getDatoNonCifrato());

        hMapCUPWSUserPass.put("cupwspass", cupwspassDecriptata);

      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella gestione delle credenziali di accesso al servizio di elaborazione CUP",
          "gestioneCUPWSUserPass.error", e);
    } catch (CriptazioneException e) {
      throw new GestoreException(
          "Errore nella gestione delle credenziali di accesso al servizio di elaborazione CUP",
          "gestioneCUPWSUserPass.error", e);
    }
    return hMapCUPWSUserPass;

  }

  /**
   * Gestione delle credenziali del ws CUP
   * @param status
   * @param datiForm
   * @throws GestoreException
   */
   public String[] gestioneCredenzialiCUP(HttpServletRequest request) throws GestoreException {
     String[] credenzialiWSCUP = new String[3];
     ProfiloUtente profilo = (ProfiloUtente) request.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
     Long syscon = new Long(profilo.getId());
     String cupwsuser = null;
     String cupwspass = null;
     String sysconStr = syscon.toString();
     // Leggo le eventuali credenziali memorizzate
     HashMap<String, String> hMapCUPWSUserPass = new HashMap<String, String>();
     hMapCUPWSUserPass = this.recuperaCUPWSUserPass(syscon);
     cupwsuser = (hMapCUPWSUserPass.get("cupwsuser"));
     cupwspass = (hMapCUPWSUserPass.get("cupwspass"));

     String recuperaUser = request.getParameter("recuperauser");
     String recuperaPassword = request.getParameter("recuperapassword");
     String memorizza = request.getParameter("memorizza");

     // Gestione USER
     if (recuperaUser != null && "1".equals(recuperaUser)) {
       cupwsuser = (hMapCUPWSUserPass.get("cupwsuser"));
     } else {
       cupwsuser = request.getParameter("cupwsuser");
     }

     // Gestione PASSWORD
     if (recuperaPassword != null && "1".equals(recuperaPassword)) {
       cupwspass = (hMapCUPWSUserPass.get("cupwspass"));
     } else {
       cupwspass = request.getParameter("cupwspass");
     }

     // Gestione memorizzazione delle credenziali o eventuale cancellazione (se richiesta)
     if (memorizza == null) {
       this.gestioneCUPWSUserPass(syscon, cupwsuser, cupwspass, false);
     } else {
       this.gestioneCUPWSUserPass(syscon, cupwsuser, cupwspass, true);
     }

     credenzialiWSCUP[0] = sysconStr;
     credenzialiWSCUP[1] = cupwsuser;
     credenzialiWSCUP[2] = cupwspass;
     return credenzialiWSCUP;
   }

   /**
   *
   * @param status
   * @param datiForm
   * @throws GestoreException
   */
  public void gestioneCUPDATI(TransactionStatus status,
      DataColumnContainer datiForm,String[] credenzialiCUP, HttpServletRequest request) throws GestoreException {

    Long contri = datiForm.getColumn("INTTRI.CONTRI").getValue().longValue();
    Long conint = datiForm.getColumn("INTTRI.CONINT").getValue().longValue();
    
    
    
    String cup = datiForm.getString("INTTRI.CUPPRG");

    String cupwsuser = credenzialiCUP[1];
    String cupwspass = credenzialiCUP[2];

    ProfiloUtente profilo = (ProfiloUtente) request.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    Long syscon = new Long(profilo.getId());


       // Parametri di ricerca
       HashMap<String, String> hMapParametri = new HashMap<String, String>();
       hMapParametri.put("codiceCUP", cup);


       DettaglioCUP dettaglioCUP = this.richiestaDettaglioCUP(cupwsuser, cupwspass, syscon, hMapParametri);

       String cupMaster = dettaglioCUP.getCup_master();
       String ragioniCollegamento = dettaglioCUP.getRagioni_collegamento();

       Integer annoDecisione = dettaglioCUP.getAnno_decisione();
       java.util.Date dataDefinitivo = dettaglioCUP.getDataDefinitivo();
       // java.util.Date dataChiusura = ??
       String natura = dettaglioCUP.getNatura();
       String tipologia = dettaglioCUP.getTipologia();
       String settore = dettaglioCUP.getSettore();
       String sottoSettore = dettaglioCUP.getSottosettore();
       String categoria = dettaglioCUP.getCategoria();
       String cumulativo = dettaglioCUP.getCumulativo();
       String beneficiario = dettaglioCUP.getBeneficiario();
       String nomeStrInfrastr = dettaglioCUP.getNomeStrInfrastr();
       String strInfrastrUnica = dettaglioCUP.getStrInfrastrUnica();
       String denomImprStab = dettaglioCUP.getDenomImprStab();
       String denomImprStabPrec = dettaglioCUP.getDenomImprStabPrec();
       String ragioneSociale = dettaglioCUP.getRagioneSociale();
       String ragioneSocialePrec = dettaglioCUP.getRagioneSocialePrec();
       String partitaIVA = dettaglioCUP.getPartitaIVA();
       String denominazioneProgetto = dettaglioCUP.getDenominazioneProgetto();
       String ente = dettaglioCUP.getEnte();
       String attoAmministrativo = dettaglioCUP.getAttoAmministrativo();
       String scopoIntervento = dettaglioCUP.getScopoIntervento();
       String descrizioneIntervento = dettaglioCUP.getDescrizioneIntervento();
       String obiettivoCorso = dettaglioCUP.getObiettivoCorso();
       String modInterventoFrequenza = dettaglioCUP.getModInterventoFrequenza();
       String servizio = dettaglioCUP.getServizio();
       String bene = dettaglioCUP.getBene();
       String cpv = null;//dettaglioCUP.getCpv();
       Double costo = dettaglioCUP.getCosto();
       Double finanziamento = dettaglioCUP.getFinanziamento();
       String[] codiciTipoCopFin = dettaglioCUP.getCodTipoCopFin();

       LocalizzazioneCUP[] locN = dettaglioCUP.getLocalizzazioni();

       String sponsorizzazione = dettaglioCUP.getSponsorizzazione();
       String indirizzo = dettaglioCUP.getIndAreaRifer();
       String strumProgr = dettaglioCUP.getStrumProgr();
       String descStrumProgr = dettaglioCUP.getDescStrumProgr();
       String altreInformazioni = dettaglioCUP.getAltreInformazioni();

       String cop_statale = null;
       String cop_regionale = null;
       String cop_provinciale = null;
       String cop_comunale = null;
       String cop_altrapubblica = null;
       String cop_comunitaria = null;
       String cop_privata = null;
       String cop_pubblicaDaConfermare = null;
       for (int j = 0; j < codiciTipoCopFin.length; j++) {
         if (codiciTipoCopFin[j].equals("001")) cop_statale = "1";
         if (codiciTipoCopFin[j].equals("002")) cop_regionale = "1";
         if (codiciTipoCopFin[j].equals("003")) cop_provinciale = "1";
         if (codiciTipoCopFin[j].equals("004")) cop_comunale = "1";
         if (codiciTipoCopFin[j].equals("005")) cop_altrapubblica = "1";
         if (codiciTipoCopFin[j].equals("006")) cop_comunitaria = "1";
         if (codiciTipoCopFin[j].equals("007")) cop_privata = "1";
         if (codiciTipoCopFin[j].equals("008")) cop_pubblicaDaConfermare = "1";
       }

       try {

    	 Long conteggio = (Long) this.sqlManager.getObject("select count(*) from CUPDATI where contri = ? and conint = ?",
    	            new Object[] { contri, conint });
    	 String modo = "INS";
    	 if (conteggio != null && conteggio.longValue() > 0) {
    		   modo = "UPD";
    	 }
         if (modo.equals("UPD")) {
           sqlManager.update(
               "update CUPDATI set CUP = ? , CUP_MASTER = ? , RAGIONI_COLLEGAMENTO = ? ,"
                   + " ANNO_DECISIONE = ? , DATA_DEFINITIVO = ? , NATURA = ? , TIPOLOGIA = ? , SETTORE = ?  , SOTTOSETTORE = ?  , CATEGORIA = ? , CUMULATIVO = ? ,"
                   + " BENEFICIARIO = ? , NOME_STRU_INFRASTR = ? , STR_INFRASTR_UNICA = ? , DENOM_IMPR_STAB = ? , DENOM_IMPR_STAB_PREC = ? ,RAGIONE_SOCIALE = ? , RAGIONE_SOCIALE_PREC = ? ,"
                   + " PARTITA_IVA = ? , DENOMINAZIONE_PROGETTO = ? , ENTE = ? ,ATTO_AMMINISTR = ? , SCOPO_INTERVENTO = ? , DESCRIZIONE_INTERVENTO = ? , OBIETT_CORSO = ?,"
                   + " MOD_INTERVENTO_FREQUENZA = ? , SERVIZIO = ? , BENE = ? , CPV = ? , SPONSORIZZAZIONE = ? , COSTO = ? , FINANZIAMENTO = ? , "
                   + " COP_STATALE = ? , COP_REGIONALE = ? , COP_PROVINCIALE = ? , COP_COMUNALE = ? , COP_ALTRAPUBBLICA = ? , COP_COMUNITARIA = ? , COP_PRIVATA = ? , COP_PUBBDACONFERMARE = ? ,"
                   + " IND_AREA_RIFER = ? , STRUM_PROGR = ? , DESC_STRUM_PROGR = ? , ALTRE_INFORMAZIONI = ? "
                   + "  where CONTRI = ? AND CONINT = ?", new Object[] {cup, cupMaster, ragioniCollegamento, annoDecisione, dataDefinitivo, natura,
                   tipologia, settore, sottoSettore, categoria, cumulativo, beneficiario, nomeStrInfrastr, strInfrastrUnica, denomImprStab,
                   denomImprStabPrec, ragioneSociale, ragioneSocialePrec, partitaIVA, denominazioneProgetto, ente, attoAmministrativo,
                   scopoIntervento, descrizioneIntervento, obiettivoCorso, modInterventoFrequenza, servizio, bene, cpv, sponsorizzazione,
                   costo, finanziamento, cop_statale, cop_regionale, cop_provinciale, cop_comunale, cop_altrapubblica, cop_comunitaria,
                   cop_privata, cop_pubblicaDaConfermare, indirizzo, strumProgr, descStrumProgr, altreInformazioni, contri, conint });
           // Si vuole inizializzare la descrizione intervento dell'opera con quella del cup
           /*if (descrizioneIntervento != null && descrizioneIntervento.trim().length() > 0) {
        	   sqlManager.update("update INTTRI set DESINT = ? where CONTRI = ? AND CONINT = ?", new Object[] {descrizioneIntervento, contri, conint });
           }*/

         } else {
           sqlManager.update(
               "insert into CUPDATI (ID,CUP,CUP_MASTER,RAGIONI_COLLEGAMENTO,"
                   + " ANNO_DECISIONE,DATA_DEFINITIVO,NATURA, TIPOLOGIA , SETTORE , SOTTOSETTORE , CATEGORIA , CUMULATIVO ,"
                   + " BENEFICIARIO , NOME_STRU_INFRASTR , STR_INFRASTR_UNICA , DENOM_IMPR_STAB , DENOM_IMPR_STAB_PREC ,RAGIONE_SOCIALE , RAGIONE_SOCIALE_PREC ,"
                   + " PARTITA_IVA , DENOMINAZIONE_PROGETTO , ENTE ,ATTO_AMMINISTR , SCOPO_INTERVENTO , DESCRIZIONE_INTERVENTO , OBIETT_CORSO,"
                   + " MOD_INTERVENTO_FREQUENZA, SERVIZIO , BENE , CPV , SPONSORIZZAZIONE , COSTO , FINANZIAMENTO , "
                   + " COP_STATALE , COP_REGIONALE , COP_PROVINCIALE , COP_COMUNALE , COP_ALTRAPUBBLICA , COP_COMUNITARIA , COP_PRIVATA , COP_PUBBDACONFERMARE ,"
                   + " IND_AREA_RIFER , STRUM_PROGR , DESC_STRUM_PROGR , ALTRE_INFORMAZIONI , CONTRI, CONINT )"
                   + "values((select " + sqlManager.getDBFunction("isnull",new String[] {"max(id)","0"}) + "+1 from CUPDATI),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                   
               new Object[] {cup, cupMaster, ragioniCollegamento, annoDecisione, dataDefinitivo, natura, tipologia, settore, sottoSettore,
                   categoria, cumulativo, beneficiario, nomeStrInfrastr, strInfrastrUnica, denomImprStab, denomImprStabPrec, ragioneSociale,
                   ragioneSocialePrec, partitaIVA, denominazioneProgetto, ente, attoAmministrativo, scopoIntervento, descrizioneIntervento,
                   obiettivoCorso, modInterventoFrequenza, servizio, bene, cpv, sponsorizzazione, costo, finanziamento, cop_statale,
                   cop_regionale, cop_provinciale, cop_comunale, cop_altrapubblica, cop_comunitaria, cop_privata, cop_pubblicaDaConfermare, indirizzo, strumProgr,
                   descStrumProgr, altreInformazioni, contri, conint });
           // Si vuole inizializzare la descrizione intervento dell'opera con quella del cup
           /*if (descrizioneIntervento != null && descrizioneIntervento.trim().length() > 0) {
        	   sqlManager.update("update INTTRI set DESINT = ? where CONTRI = ? AND CONINT = ?", new Object[] {descrizioneIntervento, contri, conint });
           }*/
         }

         sqlManager.update("delete from CUPLOC where id = (select id from cupdati where contri = ? and conint = ? )", new Object[] {contri, conint });
         for (int j = 0; j < locN.length; j++) {
           sqlManager.update(
               "insert into CUPLOC(id,num,stato,regione,provincia,comune)values((select id from cupdati where contri = ? and conint = ? ),?,?,?,?,?)",
               new Object[] {contri, conint , j + 1, locN[j].getStato(), locN[j].getRegione(), locN[j].getProvincia(), locN[j].getComune() });
         }

       } catch (SQLException e) {
         throw new GestoreException("Errore in aggiornamento del cup", "", e);
       }

  }
  
}
