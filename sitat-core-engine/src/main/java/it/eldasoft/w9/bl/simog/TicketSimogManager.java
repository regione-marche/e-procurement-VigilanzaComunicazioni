package it.eldasoft.w9.bl.simog;

import it.avlp.simog.massload.xmlbeans.ChiudiSessione;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneResponseDocument;
import it.avlp.simog.massload.xmlbeans.Login;
import it.avlp.simog.massload.xmlbeans.LoginDocument;
import it.avlp.simog.massload.xmlbeans.LoginResponse;
import it.avlp.simog.massload.xmlbeans.LoginResponseDocument;
import it.avlp.simog.massload.xmlbeans.ResponseCheckLogin;
import it.avlp.simog.massload.xmlbeans.ResponseChiudiSession;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.w9.common.CostantiSimog;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Manager per la gestione del ticket necessario alle chiamate ai servizi Simog in
 * configurazione Osservatorio. 
 * Se non dispone del ticket effettua login e ritorna il nuovo ticket.
 * Una volta terminato di usare il ticket chiamare il metodo rilasciaTicket.
 * 
 * @author Luca.Giacomazzo
 */
public class TicketSimogManager {

  /**
   * Questo manager e' usato solo in modalita' Regione dalla classe GeneraIdGaraCigManager
   */
  
  // SIMOGWS_WSSMANAGER_APP_18 errore simog in caso di sessione gia' aperta
  
  /**
   * Gli errori SIMOGWS_WSSMANAGER_APP_nn con nn 01,..,18 sono tutti errori relativi alla sessione.
   * 
   * Gli errori SIMOGWS_TICKETMANAGER* sono errori relativi al ticket o alle collaborazioni.
   */
  
  static Logger logger = Logger.getLogger(TicketSimogManager.class);
  
  private TicketSimogBean ticketSimogBean = null;
  
  /**
   * Numero di ticket distribuiti ai metodi che fanno accesso ai servizi SIMOG.
   */
  private int numeroTicketDistribuiti = 0;
  
  /**
   * Ritorna il ticket per l'accesso ai diversi metodi dei servizi SIMOG.
   * 
   * @return the ticket
   */
  public TicketSimogBean getTicket() {
    if (this.ticketSimogBean == null || this.numeroTicketDistribuiti < 1) {
    	this.ticketSimogBean = null;
    	this.numeroTicketDistribuiti = 0; 
      synchronized (this) {
    	if (this.ticketSimogBean == null || this.numeroTicketDistribuiti < 1) {
    	  this.ticketSimogBean = null;
    	  this.numeroTicketDistribuiti = 0;
    	  this.ticketSimogBean = this.eseguiLoginServiziSimog();
    	  if (this.ticketSimogBean.isSuccess()) {
            this.numeroTicketDistribuiti++;
          } else {
        	this.ticketSimogBean = null;
            this.numeroTicketDistribuiti = 0;
          }
          
          if (logger.isDebugEnabled()) {
            logger.debug("Numero Ticket Simog Distribuiti=" + this.numeroTicketDistribuiti);
          }
          
          return this.ticketSimogBean;
        } else {
          synchronized (this) {
            this.numeroTicketDistribuiti++;
            
            if (logger.isDebugEnabled()) {
              logger.debug("Numero Ticket Simog Distribuiti=" + this.numeroTicketDistribuiti);
            }
            
            return this.ticketSimogBean;
          }
        }
      }
    } else {
      synchronized (this) {
    	if (this.numeroTicketDistribuiti < 1) {
    	  if (logger.isDebugEnabled()) {
    	    logger.debug("Numero Ticket Simog Distribuiti e' minore di 1. Bisogna effettuare un nuovo login ai servizi SIMOG");
    	  }
    	  // Numero di ticket distribuiti minore di 1. Quindi bisogna effettuare di nuovo 
    	  // login ai servzi SIMOG per richiedere un nuovo ticket.
    	  this.ticketSimogBean = null;
    	  this.numeroTicketDistribuiti = 0;
    	  
    	  this.ticketSimogBean = this.eseguiLoginServiziSimog();
    	  if (this.ticketSimogBean.isSuccess()) {
            this.numeroTicketDistribuiti++;
          } else {
        	this.ticketSimogBean = null;
            this.numeroTicketDistribuiti = 0;
          }
    	  
    	  return null;
    	} else {
	      this.numeroTicketDistribuiti++;
	        
	      if (logger.isDebugEnabled()) {
	        logger.debug("Numero Ticket Simog Distribuiti=" + this.numeroTicketDistribuiti);
	      }
	        
	      return this.ticketSimogBean;
    	}
      }
    }
  }
  
  /**
   * Metodo per indicare al TicketSimogManager che si e' terminato di 
   * usare il ticket precedentemente fornito. 
   */
  public void rilasciaTicket() {
    synchronized(this) {
      if (this.numeroTicketDistribuiti > 0) {
        this.numeroTicketDistribuiti--;
      }
      
      if (this.numeroTicketDistribuiti == 0) {
        if (this.ticketSimogBean != null) {
          this.eseguiLogoutServiziSimog();
          this.ticketSimogBean = null;
        }
      }
      
      // Per evitare situazioni incoerenti si fanno due controlli ulteriori
      // su ticket e numeroTicketDistribuiti
      if (this.ticketSimogBean != null && this.numeroTicketDistribuiti == 0) {
        this.eseguiLogoutServiziSimog();
        this.ticketSimogBean = null;
      }
      
      if (this.ticketSimogBean == null && this.numeroTicketDistribuiti != 0) {
        this.numeroTicketDistribuiti = 0;
      }
      
      if (logger.isDebugEnabled()) {
        logger.debug("Numero Ticket Simog Distribuiti=" + this.numeroTicketDistribuiti);
      }
    }
  }
  
  /**
   * 
   */
  private TicketSimogBean eseguiLoginServiziSimog() {
    int tipoAccessoServiziSIMOG = 0;
    
    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoServiziSIMOG = Integer.parseInt(a);
    }
    
    TicketSimogBean ticketBean = null;
    String msgError = null;
    
    // Gestione della connessione
    String urlServiziSimog = null;
    String loginServiziSimog = null;
    String passwordServiziSimog = null;
    
    a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    if (StringUtils.isNotEmpty(a)) {
      urlServiziSimog = a;
    } else {
      logger.error("L'indirizzo per la connessione alla PDD non e' definito");
    }
    
    a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
    if (StringUtils.isNotEmpty(a)) {
      loginServiziSimog = a;
    } else {
      logger.error("Login per la connessione ai servizi SIMOG non e' definita");
    }
    
    a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD);
    if (StringUtils.isNotEmpty(a)) {
      try {
        ICriptazioneByte cript = FactoryCriptazioneByte.getInstance(
            ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
            a.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
        passwordServiziSimog = new String(cript.getDatoNonCifrato());
      } catch (CriptazioneException e) {
        logger.error("Errore nella decriptazione della password di accesso ai servizi SIMOG");
      }
    } else {
      logger.error("Password per la connessione ai servizi SIMOG non e' definita");
    }
    
    if (StringUtils.isNotEmpty(urlServiziSimog)
        && StringUtils.isNotEmpty(loginServiziSimog)
        && StringUtils.isNotEmpty(passwordServiziSimog)) {

      switch (tipoAccessoServiziSIMOG) {
      case CostantiSimog.ORT_CONSULTA_GARA_VIA_WS:
        try {
          ticketBean = this.eseguiLoginServiziSimogPDD(urlServiziSimog, loginServiziSimog, passwordServiziSimog);
        } catch (RemoteException e1) {
          msgError = "Errore remoto ai servizi SIMOG durante l'operazione di login";
          logger.error("Errore remoto ai servizi SIMOG durante l'operazione di login", e1);
        } catch (Throwable t) {
          logger.error("Errore inatteso durante l'operazione di login", t);
          msgError = "Errore inatteso ai servizi SIMOG durante l'operazione di login";
        } finally {
          if (msgError != null) {
            ticketBean = new TicketSimogBean();
            ticketBean.setSuccess(false);
            ticketBean.setMsgError(msgError);          
          }
        }
        break;
      
      default:
        msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
            "indicato il tipo di accesso ai servizi Simog";
        logger.error(msgError);
        ticketBean = new TicketSimogBean();
        ticketBean.setSuccess(false);
        ticketBean.setMsgError(msgError + ". Contattare un amministratore");
        
        break;
      }
    } else {
      String message = "Errore durante la connessione ai servizi SIMOG: i parametri di connessione "
        + "al server non sono specificati correttamente. Contattare un amministratore.";
      logger.error(message);
      
      ticketBean = new TicketSimogBean();
      ticketBean.setMsgError(message);
      ticketBean.setSuccess(false);
    }
    
    return ticketBean;
  }
  
  /**
   * Metodo per l'esecuzione dell'operazione chiudiSessione su Simog, con conseguente
   * invalidazione del ticket. Non interessa l'esito dell'operazione. A prescindere
   * dall'esito si prosegue come se fosse andato tutto bene.
   */
  private void eseguiLogoutServiziSimog() {
    
    int tipoAccessoServiziSIMOG = 0;
    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoServiziSIMOG = Integer.parseInt(a);
    }

    String msgError = null;
    
    // Gestione della connessione
    String urlServiziSimog = null;
    
    a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    if (StringUtils.isNotEmpty(a)) {
      urlServiziSimog = a;
    } else {
      logger.error("L'indirizzo per la connessione alla PDD non e' definito");
    }
    
    switch (tipoAccessoServiziSIMOG) {
    case CostantiSimog.ORT_CONSULTA_GARA_VIA_WS:
      try {
        this.eseguiLogoutServiziSimogPDD(urlServiziSimog);
      } catch (Throwable t) {
        logger.error("Errore inaspettato durante l'operazione chiudiSessione sui servizi Simog", t);
      }
      break;
    
    default:
      msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
          "indicato il tipo di accesso ai servizi Simog";
      logger.error(msgError);
      break;
    }
  }

  /**
   * Metodo che, dopo aver fatto login su Simog attraverso la PDD, restituisce un bean
   * contenente il ticket fornito da Simog e l'esito positivo dell'operazione se
   * l'operazione di login e' terminata con successo, oppure ritorna lo stesso bean
   * con il messaggio di errore e l'esito negativo dell'operazione.
   * In caso di eccezioni queste vengono riemesse.  
   * 
   * @param url
   * @param login
   * @param password
   * @return Ritorna TicketSimogBean con ticket valorizzato e success = true se l'operazione
   * di login e' terminata con successo, altrimenti ritorna TicketSimogBean con success = false,
   * ticket non valorizzato e il messaggio di errore di valorizzato.
   *   
   * @throws AxisFault
   * @throws RemoteException
   * @throws CriptazioneException
   */
  private TicketSimogBean eseguiLoginServiziSimogPDD(String url, String login, String password)
      throws AxisFault, RemoteException, CriptazioneException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiLoginServiziSimogPDD: inizio metodo");
    }
    
    TicketSimogBean ticketBean = new TicketSimogBean();

    SimogWSPDDServiceStub simogWsPddServiceStub = new SimogWSPDDServiceStub(url);
    ResponseCheckLogin responseCheckLogin = null;
      
    this.setProxy(simogWsPddServiceStub);
    
    LoginDocument loginDoc = LoginDocument.Factory.newInstance();
    Login loginIn = Login.Factory.newInstance();
    loginIn.setLogin(login);
    loginIn.setPassword(password);
    loginDoc.setLogin(loginIn);

    LoginResponseDocument loginResponseDoc = null; 
    
    try {
      if (logger.isInfoEnabled()) {
        logger.info("Invocazione metodo login verso i Servizi SIMOG");
      }
      
      String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
      if (StringUtils.isNotEmpty(strTimeOut)) {
        Integer timeOutClient = new Integer(strTimeOut);
        simogWsPddServiceStub._getServiceClient().getOptions().setProperty(
            HTTPConstants.SO_TIMEOUT, timeOutClient);
        simogWsPddServiceStub._getServiceClient().getOptions().setProperty(
            HTTPConstants.CONNECTION_TIMEOUT, timeOutClient);
      }
      
      logger.info("Invocazione il metodo login verso i Servizi SIMOG");
      loginResponseDoc = simogWsPddServiceStub.login(loginDoc);
      logger.info("Invocato il metodo login verso i Servizi SIMOG");
      
      if (logger.isInfoEnabled()) {
        logger.info("Invocato metodo login verso i Servizi SIMOG");
      }
    } catch (RemoteException re) {
      logger.error("Errore nell'interazione con la porta di dominio per l'accesso al "
          + "WS Simog per la chiamata consultaGara durante la funzione 'Carica lotto da "
          + "SIMOG' con codiceCIG='" + "'", re);
      throw re;
    }
    
    if (loginResponseDoc != null) {
      LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
      
      if (loginResponse.isSetReturn() || loginResponse.getReturn() != null) {
        responseCheckLogin = loginResponse.getReturn();
        
        if (responseCheckLogin.getSuccess()) {
          ticketBean.setTicketSimog(responseCheckLogin.getTicket());
          ticketBean.setCollaborazioni(responseCheckLogin.getColl());
          ticketBean.setSuccess(true);
          if (logger.isInfoEnabled()) {
            logger.info("Login ai servizi SIMOG eseguito con successo (Ticket=" + 
                responseCheckLogin.getTicket() + ")");
          }
        } else {
          logger.error("L'operazione di login su Simog e' terminata con il seguente errore: "
              + responseCheckLogin.getError());
          ticketBean.setSuccess(false);
          ticketBean.setMsgError(responseCheckLogin.getError());              
        }
      } else {
        logger.error("L'oggetto loginResponse ritornato da Simog non ha valorizzato l'oggetto ResponseCheckLogin");
        ticketBean.setSuccess(false);
        ticketBean.setMsgError("Errore durante l'operazione di login ai servizi Simog (1)");
      }
    } else {
      logger.error("L'oggetto loginResponseDoc ritornato da Simog non e' valorizzato");
      ticketBean.setSuccess(false);
      ticketBean.setMsgError("Errore durante l'operazione di login ai servizi Simog (2)");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiLoginServiziSimogPDD: fine metodo");
    }
    
    return ticketBean;
  }

  /**
   * @param simogWsPddServiceStub
   */
  public void setProxy(SimogWSPDDServiceStub simogWsPddServiceStub) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("setProxy: inizio metodo");
    }
    
    String strProxyUrl      = ConfigManager.getValore(CostantiSimog.PROP_SITAT_SERVIZI_SIMOG_PROXY_URL);
    String strProxyPort     = ConfigManager.getValore(CostantiSimog.PROP_SITAT_SERVIZI_SIMOG_PROXY_PORT);
    String strProxyUsername = ConfigManager.getValore(CostantiSimog.PROP_SITAT_SERVIZI_SIMOG_PROXY_USERNAME);
    String strProxyPassword = ConfigManager.getValore(CostantiSimog.PROP_SITAT_SERVIZI_SIMOG_PROXY_PASSWORD);
    
    if (StringUtils.isNotEmpty(strProxyUrl) && StringUtils.isNotEmpty(strProxyPort)) {
      HttpTransportProperties.ProxyProperties proxyProperties = new HttpTransportProperties.ProxyProperties();
      //proxyProperties.setDomain("");
      proxyProperties.setProxyName(strProxyUrl);
      proxyProperties.setProxyPort(Integer.parseInt(strProxyPort));
      if (StringUtils.isNotEmpty(strProxyUsername)) {
        proxyProperties.setUserName(strProxyUsername);
      }
      if (StringUtils.isNotEmpty(strProxyPassword)) {
        proxyProperties.setPassWord(strProxyPassword);
      }
      simogWsPddServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.PROXY, proxyProperties);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("setProxy: fine metodo");
    }
  }

  
  /**
   * Metodo per l'esecuzione dell'operazione chiudiSessione su Simog tramite PDD,
   * con conseguente invalidazione del ticket.
   * 
   * @param urlServiziSimog
   * @throws CriptazioneException 
   * @throws RemoteException 
   */
  private void eseguiLogoutServiziSimogPDD(String urlServiziSimog)
      throws CriptazioneException, AxisFault, RemoteException {

    ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
    chiudiSessione.setTicket(this.ticketSimogBean.getTicketSimog());
    
    ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
    chiudiSessioneDoc.setChiudiSessione(chiudiSessione);

    SimogWSPDDServiceStub simogWsPddServiceStub = new SimogWSPDDServiceStub(urlServiziSimog);
    this.setProxy(simogWsPddServiceStub);
    
    String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
    if (StringUtils.isNotEmpty(strTimeOut)) {
      Integer timeOutClient = new Integer(strTimeOut);
      simogWsPddServiceStub._getServiceClient().getOptions().setProperty(
          HTTPConstants.SO_TIMEOUT, timeOutClient);
      simogWsPddServiceStub._getServiceClient().getOptions().setProperty(
          HTTPConstants.CONNECTION_TIMEOUT, timeOutClient);
    }
    
    logger.info("Invocazione metodo chiudiSessione verso i Servizi SIMOG (Ticket=" + this.ticketSimogBean.getTicketSimog() + ")");
    ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = 
        simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
    logger.info("Invocato il metodo chiudiSessione verso i Servizi SIMOG (Ticket=" + this.ticketSimogBean.getTicketSimog() + ")");
    
    ResponseChiudiSession responseChiudiSessione =
      chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
    
    if (!responseChiudiSessione.getSuccess()) {
      logger.error("La chiusura della connessione identificata dal ticket "
          + this.ticketSimogBean.getTicketSimog()
          + " ha generato il seguente errore: "
          + responseChiudiSessione.getError());
    } else {
      logger.info("Chiusura della connessione identificata dal ticket "
    	  + this.ticketSimogBean.getTicketSimog()
          + " terminata con successo");
    }
  }
  
  /**
   * Metodo per resettare l'oggetto ticket memorizzato al livello di application.
   * Questo modo impone di ripartire da una situazione pulita.
   * La situazione peggiore   
   */
  public void resetTicket() {
    synchronized(this) {
      this.numeroTicketDistribuiti  = 0;
      
      if (this.ticketSimogBean != null) {
        this.eseguiLogoutServiziSimog();
        this.ticketSimogBean = null;
      }
      
      if (logger.isDebugEnabled()) {
        logger.debug("Numero Ticket Simog Distribuiti=" + this.numeroTicketDistribuiti);
      }
    }
  }
  
}
