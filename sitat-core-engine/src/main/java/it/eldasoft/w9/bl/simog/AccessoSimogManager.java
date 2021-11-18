package it.eldasoft.w9.bl.simog;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import it.avlp.simog.massload.xmlbeans.ChiudiSessione;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneResponseDocument;
import it.avlp.simog.massload.xmlbeans.Collaborazione;
import it.avlp.simog.massload.xmlbeans.Collaborazioni;
import it.avlp.simog.massload.xmlbeans.Login;
import it.avlp.simog.massload.xmlbeans.LoginDocument;
import it.avlp.simog.massload.xmlbeans.LoginRPNT;
import it.avlp.simog.massload.xmlbeans.LoginRPNTDocument;
import it.avlp.simog.massload.xmlbeans.LoginRPNTResponse;
import it.avlp.simog.massload.xmlbeans.LoginRPNTResponseDocument;
import it.avlp.simog.massload.xmlbeans.LoginResponse;
import it.avlp.simog.massload.xmlbeans.LoginResponseDocument;
import it.avlp.simog.massload.xmlbeans.ResponseCheckLogin;
import it.avlp.simog.massload.xmlbeans.ResponseChiudiSession;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.common.CostantiSimog;
import it.toscana.rete.rfc.sitatsa.client.ResponseLoginRPNT;
import it.toscana.rete.rfc.sitatsa.client.WsOsservatorioProxy;

import org.apache.axis.client.Stub;
import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Gestione di login, loginRPNT e logout sui servizi SIMOG
 * 
 * @author luca.giacomazzo
 */
public class AccessoSimogManager {
  
  /**
   * Logger di classe.
   */
  private static Logger logger = Logger.getLogger(AccessoSimogManager.class);
  
  /**
   * Login ai servizi SIMOG.
   * 
   * @param simoguser
   * @param simogpass
   * @return 
   */
  public TicketSimogBean simogLogin(final String simoguser, final String simogpass) {
    TicketSimogBean ticketSimogBean = new TicketSimogBean();
    
    logger.debug("simogLogin: inizio metodo");
    
    String urlWsSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    if (!StringUtils.isNotEmpty(urlWsSimog)) {
      logger.error("URL per accesso ai servizi SIMOG non e' definita");
    }

    if (StringUtils.isNotEmpty(urlWsSimog)) {
      if (StringUtils.isNotEmpty(simoguser)
          && StringUtils.isNotEmpty(simogpass)) {

        SimogWSPDDServiceStub simogWsPddServiceStub = null;
        ResponseCheckLogin responseCheckLogin = null;
        String ticketSessioneSIMOG = null;
        
        try {
          simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimog);
          
          this.setProxy(simogWsPddServiceStub);
          this.setTimeout(simogWsPddServiceStub);
          
          LoginDocument loginDoc = LoginDocument.Factory.newInstance();
          Login loginIn = Login.Factory.newInstance();
          loginIn.setLogin(simoguser);
          loginIn.setPassword(simogpass);
          loginDoc.setLogin(loginIn);
  
          logger.info("Invocazione metodo login verso i Servizi SIMOG");
          LoginResponseDocument loginResponseDoc = simogWsPddServiceStub.login(loginDoc);
          logger.info("Invocato metodo login verso i Servizi SIMOG");
          
          LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
          
          if (loginResponse.isSetReturn()) {
            if (loginResponse.getReturn().getSuccess()) {
              if (loginResponse.getReturn().isSetTicket() && loginResponse.getReturn().isSetColl()) {
                responseCheckLogin = loginResponse.getReturn();
                ticketSessioneSIMOG = responseCheckLogin.getTicket();
                logger.info("Login su SIMOG eseguito con successo (Ticket=" + ticketSessioneSIMOG + ")");
                
                ticketSimogBean.setSuccess(true);
                ticketSimogBean.setTicketSimog(ticketSessioneSIMOG);
                ticketSimogBean.setCollaborazioni(responseCheckLogin.getColl());
              } else {
                logger.error("La login su SIMOG ha fornito una risposta priva del ticket e/o dell'array delle collaborazioni");
                ticketSimogBean.setSuccess(false);
                ticketSimogBean.setMsgError("");
              }
            } else {
              String msg = "La login su SIMOG terminata con errore";
              if (loginResponse.getReturn().isSetError()) {
                logger.error(msg + ". Questo il messaggio di errore: " + loginResponse.getReturn().getError());
                ticketSimogBean.setMsgError(loginResponse.getReturn().getError());
              } else {
                logger.error(msg + ", ma senza indicare il messaggio di errore");
                ticketSimogBean.setMsgError(msg);
              }
              ticketSimogBean.setSuccess(false);
              
            }
          } else {
            // risposta senza oggetto settato
            ticketSimogBean.setSuccess(false);
            ticketSimogBean.setMsgError("Problemi nella risposta di SIMOG al tentativo di login");
          }
        } catch (AxisFault af) {
          logger.error("Errore inaspettato nella inizializzazione del client dei servizi SIMOG nell'operazione di login", af);
          ticketSimogBean.setSuccess(false);
          ticketSimogBean.setMsgError("Errore inaspettato nel tentativo di login ai servizi SIMOG");
        } catch (RemoteException re) {
          logger.error("Errore remoto inaspettato (quindi lato server) nell'interazione con i servizi SIMOG nell'operazione di login", re);
          ticketSimogBean.setSuccess(false);
          ticketSimogBean.setMsgError("Errore remoto inaspettato nel tentativo di login ai servizi SIMOG");
        } catch (Exception e) {
          logger.error("Errore inaspettato nell'interazione con i servizi SIMOG nell'operazione di login", e);
          ticketSimogBean.setSuccess(false);
          ticketSimogBean.setMsgError("Errore inaspettato nel tentativo di login ai servizi SIMOG");
        }
      } else {
        logger.error("Login ai servizi SIMOG: manca login e/o password");
        ticketSimogBean.setSuccess(false);
        ticketSimogBean.setMsgError("Parametri insufficienti per l'operazione login ai servizi SIMOG");
      }
    } else {
      // Errore mancano parametri di connessione...
      String message = "Errore durante la connessione ai servizi SIMOG: i parametri di connessione "
        + "al server non sono specificati correttamente. Contattare un amministratore.";
      logger.error(message);

      ticketSimogBean.setMsgError(message);
      ticketSimogBean.setSuccess(false);
    }
    
    logger.debug("simogLogin: fine metodo");
    
    return ticketSimogBean;
  }
  
  
  public ResponseLoginRPNT getLoginRPNTIndiretto( final String cfRup) {
    logger.debug("getLoginRPNTIndiretto: inizio metodo");
    
    // Oggetto di risposta
    ResponseLoginRPNT responseLoginRPNT = new ResponseLoginRPNT();
    String urlWsORT = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    
    if (StringUtils.isNotEmpty(urlWsORT)) {
      WsOsservatorioProxy wsOsservatorioProxy = new WsOsservatorioProxy(urlWsORT);
      
      try {
        String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
        if (StringUtils.isNotEmpty(strTimeOut)) {
          org.apache.axis.client.Stub stubClient = (Stub) wsOsservatorioProxy.getWsOsservatorio_PortType();
          if (logger.isDebugEnabled()) {
            logger.debug("Prima. Default timeout: " + HTTPConstants.SO_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
                + " ." + HTTPConstants.CONNECTION_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT));
          }
          
          logger.debug("Valorizzazione del timeout nella richiesta al WsOsservatorio");
          stubClient.setTimeout(new Integer(strTimeOut).intValue());
 
          if (logger.isDebugEnabled()) {
            logger.debug("Dopo. Default timeout: " + HTTPConstants.SO_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
                + " ." + HTTPConstants.CONNECTION_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT)
                + " . stubClient.getTimeout()=" + stubClient.getTimeout());
          }
        }       

        logger.info("Invocazione del metodo getLoginRPNT del WsOsservatorio (cfRup=" + cfRup + ")");
        responseLoginRPNT = wsOsservatorioProxy.getLoginRPNT(cfRup);
        logger.info("Invocato il metodo getLoginRPNY del WsOsservatorio (cfRup=" + cfRup + ")");
        
      } catch (RemoteException re) {
        logger.error("Errore remoto nell'interazione con i servizi dell'osservatorio ", re);
        responseLoginRPNT.setSuccess(false);
        responseLoginRPNT.setError("Errore remoto nell'interazione con i servizi dell'osservatorio");
      } catch (Throwable t) {
        logger.error("Errore inaspettato nell'interazione con i servizi dell'osservatorio" );
        responseLoginRPNT.setSuccess(false);
        responseLoginRPNT.setError("Errore inaspettato nell'interazione con i servizi dell'osservatorio");
      }
      
    } else {
      logger.error("Errore di configurazione: manca l'indirizzo per la connessione al "
          + "WS Ossevatorio per la chiamata consultaGara. Contattare un amministratore.");
      responseLoginRPNT.setSuccess(false);
      responseLoginRPNT.setError("Errore di configurazione: manca l'indirizzo per la connessione "
          + "al WS Osservatorio. Contattare un amministratore.");
    }
    
    logger.debug("getLoginRPNTIndiretto: fine metodo");
    return responseLoginRPNT;
  }

  /**
   * Ritorna l'oggetto XML (in formato stringa) ricevuto da SIMOG come risposta al meotodo loginRPNT
   * 
   * @param simogUser
   * @param simogPassword
   * @param cfRup
   * @return
   */
  public it.toscana.rete.rfc.sitatort.ResponseLoginRPNT getLoginRPNT(final String cfRup, final String simogUser, final String simogPassword) {
    
    logger.debug("getLoginRPNT: inizio metodo");

    // Oggetto di risposta
    it.toscana.rete.rfc.sitatort.ResponseLoginRPNT responseLoginRPNT = new it.toscana.rete.rfc.sitatort.ResponseLoginRPNT();
    
    String urlWsSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);

    if (StringUtils.isNotEmpty(urlWsSimog)) {
      if (StringUtils.isNotEmpty(simogUser) && StringUtils.isNotEmpty(simogPassword)
            && StringUtils.isNotEmpty(cfRup)) {

        SimogWSPDDServiceStub simogWsPddServiceStub = null;
        ResponseCheckLogin responseCheckLogin = null;
        String ticketSessioneSIMOG = null;

        try {
          simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimog);
        
          this.setProxy(simogWsPddServiceStub);
          this.setTimeout(simogWsPddServiceStub);
        
          LoginRPNTDocument loginDoc = LoginRPNTDocument.Factory.newInstance();
          LoginRPNT loginInRPNT = LoginRPNT.Factory.newInstance();
          loginInRPNT.setLogin(simogUser);
          loginInRPNT.setPassword(simogPassword);
          loginInRPNT.setCfrup(cfRup);
          loginDoc.setLoginRPNT(loginInRPNT);
  
          logger.info("Invocazione metodo loginRPNT verso i Servizi SIMOG");
          LoginRPNTResponseDocument loginRPNTResponseDoc = simogWsPddServiceStub.loginRPNT(loginDoc);
          logger.info("Invocato metodo loginRPNT verso i Servizi SIMOG");
          
          LoginRPNTResponse loginRPNTResponse = loginRPNTResponseDoc.getLoginRPNTResponse();
          
          if (loginRPNTResponse.isSetReturn()) {
            if (loginRPNTResponse.getReturn().getSuccess()) {
              if (loginRPNTResponse.getReturn().isSetTicket()) {
                responseCheckLogin = loginRPNTResponse.getReturn();
                ticketSessioneSIMOG = responseCheckLogin.getTicket();

                responseLoginRPNT.setSuccess(true);
                responseLoginRPNT.setLoginXML(loginRPNTResponse.toString());
                
                logger.info("Login RPNT su SIMOG eseguito con successo (Ticket=" + ticketSessioneSIMOG + ")");

              } else {
                logger.error("La loginRPNT su SIMOG ha fornito una risposta priva del ticket necessario per le successive richieste");
                responseLoginRPNT.setSuccess(false);
                responseLoginRPNT.setError("Errore nel formato della risposta alla loginRPNT sui servizi SIMOG");
              }
            } else {
              String msg = "La loginRPNT su SIMOG terminata con errore";
              if (loginRPNTResponse.getReturn().isSetError()) {
                logger.error(msg + ": " + loginRPNTResponse.getReturn().getError());
                responseLoginRPNT.setError(loginRPNTResponse.getReturn().getError());
              } else {
                logger.error(msg + ", ma senza indicare alcun messaggio");
                responseLoginRPNT.setError(msg);
              }
              responseLoginRPNT.setSuccess(false);
            }
          } else {
            // risposta senza oggetto settato
            responseLoginRPNT.setSuccess(false);
            responseLoginRPNT.setError("Problemi nella risposta di SIMOG al tentativo di loginRPNT");
          }
        } catch (AxisFault af) {
          logger.error("Errore inaspettato nella inizializzazione del client dei servizi SIMOG nell'operazione di login", af);
          responseLoginRPNT.setSuccess(false);
          responseLoginRPNT.setError("Errore inaspettato nel tentativo di loginRPNT ai servizi SIMOG");
        } catch (RemoteException re) {
          logger.error("Errore remoto inaspettato (quindi lato server) nell'interazione con i servizi SIMOG nell'operazione di login", re);
          responseLoginRPNT.setSuccess(false);
          responseLoginRPNT.setError("Errore inaspettato nel tentativo di loginRPNT ai servizi SIMOG");
        } catch (Exception e) {
          logger.error("Errore inaspettato nell'interazione con i servizi SIMOG nell'operazione di login", e);
          responseLoginRPNT.setSuccess(false);
          responseLoginRPNT.setError("Errore inaspettato nel tentativo di loginRPNT ai servizi SIMOG");

        } finally {
          this.simogLogout(ticketSessioneSIMOG);
          
          
        }
      } else {
        logger.error("LoginRPNT ai servizi SIMOG: manca login e/o password e/o cfrup");
        responseLoginRPNT.setSuccess(false);
        responseLoginRPNT.setError("Parametri insufficienti per l'operazione LoginRPNT ai servizi SIMOG");
      }
    } else {
      // Errore mancano parametri di connessione
      String message = "Errore durante la connessione ai servizi SIMOG: i parametri di connessione "
        + "al servizio non sono specificati correttamente. Contattare un amministratore";
      logger.error(message);

      responseLoginRPNT.setError(message);
      responseLoginRPNT.setSuccess(false);
    }
    
    logger.debug("getLoginRPNT: fine metodo");
    
    return responseLoginRPNT;
  }
                
                
  
  /**
   * LoginRPNT ai servizi SIMOG
   * 
   * @param simogUser
   * @param simogPassword
   * @param cfRup
   * @return
   */
  public TicketSimogBean simogLoginRPNT(final String simogUser, final String simogPassword, final String cfRup) {
    TicketSimogBean ticketSimogBean = new TicketSimogBean();
    
    logger.debug("simogLoginRPNT: inizio metodo");
    
    String urlWsSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);

    if (StringUtils.isNotEmpty(urlWsSimog)) {
      if (StringUtils.isNotEmpty(simogUser)
          && StringUtils.isNotEmpty(simogPassword)
            && StringUtils.isNotEmpty(cfRup)) {

        SimogWSPDDServiceStub simogWsPddServiceStub = null;
        ResponseCheckLogin responseCheckLogin = null;
        String ticketSessioneSIMOG = null;
      
        try {
          simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimog);
        
          this.setProxy(simogWsPddServiceStub);
          this.setTimeout(simogWsPddServiceStub);
        
          LoginRPNTDocument loginDoc = LoginRPNTDocument.Factory.newInstance();
          LoginRPNT loginInRPNT = LoginRPNT.Factory.newInstance();
          loginInRPNT.setLogin(simogUser);
          loginInRPNT.setPassword(simogPassword);
          loginInRPNT.setCfrup(cfRup);
          loginDoc.setLoginRPNT(loginInRPNT);
  
          logger.info("Invocazione metodo loginRPNT verso i Servizi SIMOG");
          LoginRPNTResponseDocument loginRPNTResponseDoc = simogWsPddServiceStub.loginRPNT(loginDoc);
          logger.info("Invocato metodo loginRPNT verso i Servizi SIMOG");
          
          LoginRPNTResponse loginRPNTResponse = loginRPNTResponseDoc.getLoginRPNTResponse();
          
          if (loginRPNTResponse.isSetReturn()) {
            if (loginRPNTResponse.getReturn().getSuccess()) {
              if (loginRPNTResponse.getReturn().isSetTicket()) {
                responseCheckLogin = loginRPNTResponse.getReturn();
                ticketSessioneSIMOG = responseCheckLogin.getTicket();
                logger.info("Login su SIMOG eseguito con successo (Ticket=" + ticketSessioneSIMOG + ")");
                
                ticketSimogBean.setSuccess(true);
                ticketSimogBean.setTicketSimog(ticketSessioneSIMOG);
                ticketSimogBean.setCollaborazioni(responseCheckLogin.getColl());
              } else {
                logger.error("La login su SIMOG ha fornito una risposta priva del ticket necessario per le successive richieste");
                ticketSimogBean.setSuccess(false);
                ticketSimogBean.setMsgError("Errore nel formato della risposta alla login sui servizi SIMOG");
              }
            } else {
              String msg = "La login su SIMOG terminata con errore";
              if (loginRPNTResponse.getReturn().isSetError()) {
                logger.error(msg + ". Questo il messaggio di errore: " + loginRPNTResponse.getReturn().getError());
                ticketSimogBean.setMsgError(loginRPNTResponse.getReturn().getError());
              } else {
                logger.error(msg + ", ma senza indicare il messaggio di errore");
                ticketSimogBean.setMsgError(msg);
              }
              ticketSimogBean.setSuccess(false);
            }
          } else {
            // risposta senza oggetto settato
            ticketSimogBean.setSuccess(false);
            ticketSimogBean.setMsgError("Problemi nella risposta di SIMOG al tentativo di login");
          }
        } catch (AxisFault af) {
          logger.error("Errore inaspettato nella inizializzazione del client dei servizi SIMOG nell'operazione di login", af);
          ticketSimogBean.setSuccess(false);
          ticketSimogBean.setMsgError("Errore inaspettato nel tentativo di loginRPNT ai servizi SIMOG");
        } catch (RemoteException re) {
          logger.error("Errore remoto inaspettato (quindi lato server) nell'interazione con i servizi SIMOG nell'operazione di login", re);
          ticketSimogBean.setSuccess(false);
          ticketSimogBean.setMsgError("Errore remoto nel tentativo di loginRPNT ai servizi SIMOG");
        } catch (Exception t) {
          logger.error("Errore inaspettato nell'interazione con i servizi SIMOG nell'operazione di login", t);
          ticketSimogBean.setSuccess(false);
          ticketSimogBean.setMsgError("Errore inaspettato nel tentativo di loginRPNT ai servizi SIMOG");
        }
      } else {
        logger.error("LoginRPNT ai servizi SIMOG: manca login e/o password e/o cfrup");
        ticketSimogBean.setSuccess(false);
        ticketSimogBean.setMsgError("Parametri insufficienti per l'operazione LoginRPNT ai servizi SIMOG");
      }
    } else {
      // Errore mancano parametri di connessione
      String message = "Errore durante la connessione ai servizi SIMOG: i parametri di connessione "
        + "al servizio non sono specificati correttamente. Contattare un amministratore";
      logger.error(message);

      ticketSimogBean.setMsgError(message);
      ticketSimogBean.setSuccess(false);
    }
    
    logger.debug("simogLoginRPNT: fine metodo");
    
    return ticketSimogBean;
  }
  
  /**
   * Esegue il logout sui servizi SIMOG, invalidando quindi il ticket.
   * 
   * @param ticket

   */
  public void simogLogout(String ticket) {
    
    logger.debug("simogLogout: inizio metodo");
    
    String urlWsSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);

    if (StringUtils.isNotEmpty(urlWsSimog)) {
      if (ticket != null) {
        try {
          SimogWSPDDServiceStub simogWsServiceStub = new SimogWSPDDServiceStub(urlWsSimog);
          
          this.setProxy(simogWsServiceStub);
          this.setTimeout(simogWsServiceStub);
          
          ChiudiSessione chiudiSessione = it.avlp.simog.massload.xmlbeans.ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticket);
          ChiudiSessioneDocument chiudiSessioneDoc = it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument.Factory.newInstance();
          chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
  
          ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = simogWsServiceStub.chiudiSessione(chiudiSessioneDoc);
          ResponseChiudiSession responseChiudiSessione = chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
          
          if (responseChiudiSessione.getSuccess()) {
            logger.error("Chiusura della connessione identificata dal ticket " + ticket + " terminata con successo");
          } else {
            logger.error("La chiusura della connessione identificata dal ticket " + ticket + " ha generato il seguente errore: " + responseChiudiSessione.getError());            
          }
        } catch (RemoteException re) {
          logger.error("SIMOG-loaderAppalto: ", re);
        }
      } else {
        logger.error("Logout ai servizi SIMOG: manca il ticket");
      }
    } else {
      String message = "Errore durante la connessione ai servizi SIMOG: i parametri di connessione "
        + "al server non sono specificati correttamente. Contattare un amministratore";
      logger.error(message);
    }
    
    logger.debug("simogLogout: fine metodo");
  }

  
  public String getIndiceCollaborazione(SqlManager sqlManager, Collaborazioni arrayCollaborazioni, 
      Long codgara, boolean isLoginRPNT) throws SQLException, GestoreException {

    String indexCollaborazione = "-1";
    boolean trovato = false;
    
    if (arrayCollaborazioni != null) {
      if (arrayCollaborazioni.getCollaborazioniArray().length > 1) {
        Collaborazione coll = null;

        // ricavo l'ente e il centro di costo dalla gara
        List<?> datiGara = sqlManager.getVector("select CODEIN, IDCC from W9GARA where CODGARA = ?",
            new Object[] { codgara });

        if (SqlManager.getValueFromVectorParam(datiGara, 0).getValue() != null) {
          // ricavo i dati dell'Ente
          String codein = SqlManager.getValueFromVectorParam(datiGara, 0).toString();
          List<?> datiEnte = sqlManager.getVector("select CFEIN, NOMEIN from UFFINT where CODEIN = ?",
              new Object[] { codein });
          String cfAmministrazione = null;
          if (SqlManager.getValueFromVectorParam(datiEnte, 0).getValue() != null) {
            cfAmministrazione = SqlManager.getValueFromVectorParam(datiEnte, 0).toString();
          }

          // ricavo i dati del centro di costo
          Long idcentro = SqlManager.getValueFromVectorParam(datiGara, 1).longValue();
          List<?> datiCentroC = sqlManager.getVector(
              "select CODCENTRO, DENOMCENTRO from CENTRICOSTO where IDCENTRO = ?",
                  new Object[] { idcentro });
          String codcentro = null;
          if (SqlManager.getValueFromVectorParam(datiEnte, 0).getValue() != null) {
            codcentro = SqlManager.getValueFromVectorParam(datiCentroC, 0).toString();
          }
          for (int yi = 0; yi < arrayCollaborazioni.getCollaborazioniArray().length && !trovato; yi++) {
            coll = arrayCollaborazioni.getCollaborazioniArray()[yi];
            if (coll.getAziendaCodiceFiscale().trim().equalsIgnoreCase(cfAmministrazione.trim())
                && coll.getUfficioId().trim().equalsIgnoreCase(codcentro)) {
              indexCollaborazione = coll.getIndex();
              trovato = true;
            }
          }
        }

      } else {
        Collaborazione coll = arrayCollaborazioni.getCollaborazioniArray()[0];
        if ("66".equals(coll.getUfficioProfilo())) {
          //Se l'utente è di tipo Osservatorio Regionale
          indexCollaborazione = "-1";
        } else {
          indexCollaborazione = coll.getIndex();
        }
      }
    }
    return indexCollaborazione;
  }
  
  /**
   * Metodo per settare il proxy di rete per accedere all'esterno della intranet se
   * valorizzate le property url, port, username e pass
   * 
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
   * Set del timeout nella richiesta ai sevizi Simog nel client, se la property
   * it.eldasoft.sitat.consultaGara.clientTimeout e' valorizzata.
   * 
   * @param simogWsPddServiceStub
   */
  public void setTimeout(SimogWSPDDServiceStub simogWsPddServiceStub) {
    String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
    if (StringUtils.isNotEmpty(strTimeOut)) {
      Integer timeOutClient = new Integer(strTimeOut);
      simogWsPddServiceStub._getServiceClient().getOptions().setProperty(
          HTTPConstants.SO_TIMEOUT, timeOutClient);
      simogWsPddServiceStub._getServiceClient().getOptions().setProperty(
          HTTPConstants.CONNECTION_TIMEOUT, timeOutClient);
    }
  }
  
}
