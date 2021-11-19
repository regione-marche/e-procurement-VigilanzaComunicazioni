package it.eldasoft.w9.bl;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import it.avlp.simog.massload.xmlbeans.AccordiBonariType;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.AdesioneType;
import it.avlp.simog.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.AvanzamentiType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.ChiudiSessione;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneResponseDocument;
import it.avlp.simog.massload.xmlbeans.CollaudoType;
import it.avlp.simog.massload.xmlbeans.ConclusioneType;
import it.avlp.simog.massload.xmlbeans.ConsultaGara;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DatiCollaudoType;
import it.avlp.simog.massload.xmlbeans.InizioType;
import it.avlp.simog.massload.xmlbeans.Login;
import it.avlp.simog.massload.xmlbeans.LoginDocument;
import it.avlp.simog.massload.xmlbeans.LoginResponse;
import it.avlp.simog.massload.xmlbeans.LoginResponseDocument;
import it.avlp.simog.massload.xmlbeans.ResponseChiudiSession;
import it.avlp.simog.massload.xmlbeans.ResponseConsultaGara;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaEsclusoType;
import it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SospensioniType;
import it.avlp.simog.massload.xmlbeans.StipulaType;
import it.avlp.simog.massload.xmlbeans.SubappaltiType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.VarianteType;
import it.avlp.simog.massload.xmlbeans.VariantiType;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.bl.simog.TicketSimogBean;
import it.eldasoft.w9.bl.simog.TicketSimogManager;
import it.eldasoft.w9.common.CostantiSimog;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;

public class AllineaIndiciSimogManager {

  /**
   * Logger di classe.
   */
  private static Logger logger = Logger.getLogger(AllineaIndiciSimogManager.class);

  /** Manager per le transazioni SQL. */
  private SqlManager sqlManager;

  /** Manager dell ticket per l'accesso ai servizi Simog. */
  private TicketSimogManager  ticketSimogManager;
  
  /**
   * Set sqlManager.
   * 
   * @param sqlManager
   *            the sqlManager to set
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
  
  /**
   * Set TicketSimogManager.
   * @param ticketSimogManager
   */
  public void setTicketSimogManager(TicketSimogManager ticketSimogManager) {
    this.ticketSimogManager = ticketSimogManager;
  }

  
  /**
   * Metodo per riallineare i valori dei campi W9LOTT.CUI, ID_SCHEDA_LOCALE e ID_SCHEDA_SIMOG
   * della W9LOTT e della W9FASI per tutti i flussi gestiti da SIMOG.
   * 
   * @param codiceCIG
   * @param codGara
   * @param codLott
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @param simoguser
   * @param simogpass
   * @throws SQLException
   */
  public void riallineaIndiciSimog(final String codCIG, final String codUffInt, 
      HashMap<String, Object> resultMap, final String simoguser, final String simogpass) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSimog: inizio metodo");
    }
    
    String codiceCIG = StringUtils.trim(codCIG.toUpperCase());
    
    // Controllo dell'esistenza del CIG in base dati
    if (UtilitySITAT.existsLotto(codiceCIG, this.sqlManager)) {
    
	    // Valore di default per il tipo di accesso ai servizi Simog
	    int tipoAccessoWSSimog = 0;
	    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
	    if (StringUtils.isNotEmpty(a)) {
	      tipoAccessoWSSimog = Integer.parseInt(a);
	    }
	    
	    switch (tipoAccessoWSSimog) {
	    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
	    //case CostantiSimog.SA_CONSULTA_GARA_ACCESSO:
	      this.riallineaIndiciSimogDaVigilanza(codiceCIG, codUffInt, resultMap, simoguser, simogpass);
	      break;
	    case CostantiSimog.ORT_CONSULTA_GARA_VIA_WS:
	      this.riallineaIndiciSimogDaOsservatorio(codiceCIG, codUffInt, resultMap);
	      break;
	    
	    default:
	      String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
	          "indicato il tipo di accesso ai servizi Simog";
	      logger.error(msgError);
	      
	      resultMap.put("esito", Boolean.FALSE);
	      resultMap.put("errorMsg", "nella configurazione dell'applicazione: non e' stato " +
	          "indicato il tipo di accesso ai servizi Simog");
	    }
    } else {
      logger.error("Il CIG indicato non esiste in banca dati");
      
      resultMap.put("esito", Boolean.FALSE);
      resultMap.put("errorMsg", "Il CIG indicato non esiste in banca dati");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSimog: fine metodo");
    }
  }
  
  
  /**
   * Metodo per riallineare i valori dei campi W9LOTT.CUI, ID_SCHEDA_LOCALE e ID_SCHEDA_SIMOG
   * della W9LOTT e della W9FASI per tutti i flussi gestiti da SIMOG in configurazione Vigilanza.
   * 
   * @param codiceCIG
   * @param codGara
   * @param codLott
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @param simoguser
   * @param simogpass
   * @throws SQLException
   */
  public void riallineaIndiciSimogDaVigilanza(final String codiceCIG, final String codUffInt, 
      HashMap<String, Object> resultMap, final String simoguser, final String simogpass) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSimogDaVigilanza: inizio metodo");
    }
    
    HashMap<String, Long> datiLotto = UtilitySITAT.getCodGaraCodLottByCIG(codiceCIG, this.sqlManager);
    long codGara = datiLotto.get("CODGARA").longValue();
    long codLott = datiLotto.get("CODLOTT").longValue();
    
    String urlWsSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    if (StringUtils.isEmpty(urlWsSimog)) {
      logger.error("URL della PDD per accesso ai servizi SIMOG non e' definita");
    }
    
    String loginWsSimog = null;
    
    String passwordWsSimog = null;
    
    if (simoguser != null) {
      loginWsSimog = simoguser;
    }
    
    if (simogpass != null) {
      passwordWsSimog = simogpass;
    }
    if (StringUtils.isNotEmpty(urlWsSimog)
        && StringUtils.isNotEmpty(loginWsSimog)
        && StringUtils.isNotEmpty(passwordWsSimog)) {

      SimogWSPDDServiceStub simogWsPddServiceStub = null;
      it.avlp.simog.massload.xmlbeans.ResponseCheckLogin responseCheckLogin = null;
      String ticketSessioneSIMOG = null;
      
      try {
        simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimog);
        this.ticketSimogManager.setProxy(simogWsPddServiceStub);
        
        LoginDocument loginDoc = LoginDocument.Factory.newInstance();
        Login loginIn = Login.Factory.newInstance();
        loginIn.setLogin(loginWsSimog);
        loginIn.setPassword(passwordWsSimog);
        loginDoc.setLogin(loginIn);

        logger.info("Invocazione metodo login verso i Servizi SIMOG");
        LoginResponseDocument loginResponseDoc = simogWsPddServiceStub.login(loginDoc);
        logger.info("Invocato metodo login verso i Servizi SIMOG");
        
        LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
        
        if (loginResponse.isSetReturn() && loginResponse.getReturn().getSuccess()) {
          responseCheckLogin = loginResponse.getReturn();
          ticketSessioneSIMOG = responseCheckLogin.getTicket();
          logger.info("Login su SIMOG eseguito con successo (Ticket=" + ticketSessioneSIMOG + ")");

          String schede = "3.04.2.0";
            
          ConsultaGaraDocument consultaGaraDoc = ConsultaGaraDocument.Factory.newInstance();
          ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
          consultaGara.setTicket(ticketSessioneSIMOG);
          consultaGara.setSchede(schede);
             
          consultaGara.setCIG(codiceCIG);
          consultaGaraDoc.setConsultaGara(consultaGara);

          logger.info("Invocazione del metodo consultaGara verso i Servizi SIMOG (Ticket=" + 
              ticketSessioneSIMOG + ", CIG=" + codiceCIG + ")");
          ConsultaGaraResponseDocument consultaGaraResponseDoc = simogWsPddServiceStub.consultaGara(consultaGaraDoc);
          logger.info("Invocato il metodo consultaGara verso i Servizi SIMOG (Ticket=" +
              ticketSessioneSIMOG + ", CIG=" + codiceCIG + ")");

          if (consultaGaraResponseDoc.getConsultaGaraResponse().isSetReturn()) {
            ResponseConsultaGara responseConsultaGara =
                consultaGaraResponseDoc.getConsultaGaraResponse().getReturn();

            if (responseConsultaGara.getSuccess() && responseConsultaGara.isSetGaraXML()) {
              SchedaType schedaType = responseConsultaGara.getGaraXML();
              this.riallineaIndiciSimogUnificato(codiceCIG, codGara, codLott, schedaType, resultMap);
            } else {
              if (responseConsultaGara.getSuccess() && !responseConsultaGara.isSetGaraXML()) {
                String temp = "La chiamata al metodo consultaGara di SIMOG con CIG=" + codiceCIG + 
                  " e' terminata ma non contiene l'oggetto con i dati";
                logger.error(temp);
                resultMap.put("errorMsg", temp);
                resultMap.put("esito", Boolean.FALSE);
              } else if (!responseConsultaGara.getSuccess()) {
                String temp = "La chiamata al metodo consultaGara di SIMOG con CIG=" + codiceCIG +
                  " e' terminata con errore";
                if (responseConsultaGara.isSetError()) {
                  temp = temp.concat(": " + responseConsultaGara.getError());
                }
                logger.error(temp);
                resultMap.put("errorMsg", temp);
                resultMap.put("esito", Boolean.FALSE);
              }
            }
          } else {
            logger.error("La chiamata al metodo consultaGara di SIMOG con CIG=" + codiceCIG + 
                  " e' terminata ma non contiene l'oggetto di risposta");
            resultMap.put("errorMsg", "La chiamata al metodo consultaGara di SIMOG con CIG=" + codiceCIG + 
              " e' terminata ma non contiene l'oggetto di risposta");
            resultMap.put("esito", Boolean.FALSE);
          }

          // Chiusura della connessione     
          ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticketSessioneSIMOG);
        
          ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
          chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
          
          ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = 
              simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
          logger.info("Invocazione del metodo chiudiSessione verso i Servizi SIMOG (Ticket="
              + ticketSessioneSIMOG + ")");
          ResponseChiudiSession responseChiudiSessione = 
              chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
          logger.info("Invocato il metodo chiudiSessione verso i Servizi SIMOG (Ticket="
              + ticketSessioneSIMOG + ")");
          
          if (!responseChiudiSessione.getSuccess()) {
            logger.error("La chiusura della connessione identificata dal ticket "
                + ticketSessioneSIMOG
                + " ha generato il seguente errore: "
                + responseChiudiSessione.getError());
          } else {
            logger.info("Logout al WS SIMOG avvenuta con successo. La sessione con ticket "
                + ticketSessioneSIMOG
                + " e' stata chiusa.");
            ticketSessioneSIMOG = null;
          }
        } else {
          String message = null;
          if (loginResponse.getReturn().isSetError()) {
            message = "Il tentativo di login ai servizi SIMOG e' terminato con errore e con la seguente descrizione: ".concat(loginResponse.getReturn().getError());
          } else {
            message = "Il tentativo di login ai servizi SIMOG e' terminato con errore generico (senza descrizione)";
          }
          logger.error(message);

          resultMap.put("errorMsg", message);
          resultMap.put("esito", Boolean.FALSE);
        }
      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con la porta di dominio per l'accesso al WS Simog "
            + "durante la chiamata consultaGara durante la funzione 'Carica lotto da SIMOG' con "
            + "codiceCIG=" + codiceCIG
            + ", codGara=" + codGara
            + " per conto della stazione appaltante con codUffInt=" + codUffInt, re);
        
        resultMap.put("esito", Boolean.FALSE);
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento alla porta di dominio per l'accesso "
            + "al WS SIMOG durante la chiamata consultaGara con codiceCIG='"
            + codiceCIG
            + "', codGara='" + codGara
            + " per conto della stazione appaltante con codUffInt='" + codUffInt + "'", t);
        
        resultMap.put("esito", Boolean.FALSE);
      } finally {
        
        if (Boolean.FALSE.equals(resultMap.get("esito")) && !resultMap.containsKey("errorMsg")) {
          resultMap.put("errorMsg", "Il servizio SIMOG &egrave; momentaneamente non disponibile o "
              + "non risponde correttamente. Riprovare pi&ugrave; tardi. Se il problema persiste, "
              + "segnalarlo all'amministratore di sistema.");
        }
        
        if (StringUtils.isNotEmpty(ticketSessioneSIMOG)) {
          // Si prova a chiudere la connessione con il WS Simog nonostante vi
          // siano appena stati problemi di interazione con il WS stesso
     
          ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticketSessioneSIMOG);
        
          ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
          chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
          
          try {
            ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = 
                simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
            chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
          } catch(RemoteException re) {
            logger.error("Errore nell'interazione con il WS SIMOG nell'ulteriore tentativo "
                + "di chiudere la connessione al WS stesso. La sessione rimane quindi attiva "
                + "e non si potra' riconnettersi fino allo scadere della stessa.", re);
          }
        }
      }
      
    } else {
      // Errore nella configurazione URL, login e password

      String message = "Errore durante la connessione ai servizi SIMOG: i parametri di connessione "
          + " non sono specificati correttamente. Contattare un amministratore.";
      logger.error("Il tentativo di connessione ha generato il seguente errore: " + message);

      resultMap.put("errorMsg", message);
      resultMap.put("esito", Boolean.FALSE);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSimogDaVigilanza: fine metodo");
    }
  }
  
  
  /**
   * Metodo per riallineare i valori dei campi W9LOTT.CUI, ID_SCHEDA_LOCALE e ID_SCHEDA_SIMOG
   * della W9LOTT e della W9FASI per tutti i flussi gestiti da SIMOG.
   * 
   * @param codiceCIG
   * @param codGara
   * @param codLott
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @param simoguser
   * @param simogpass
   * @throws SQLException
   */
  public void riallineaIndiciSimogDaOsservatorio(final String codiceCIG, final String codUffInt, 
      HashMap<String, Object> resultMap) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSimogDaOsservatorio: inizio metodo");
    }
    
    HashMap<String, Long> datiLotto = UtilitySITAT.getCodGaraCodLottByCIG(codiceCIG, this.sqlManager);
    long codGara = datiLotto.get("CODGARA").longValue();
    long codLott = datiLotto.get("CODLOTT").longValue();
    
    it.toscana.rete.rfc.sitatort.ResponseConsultaGara resultXML = new it.toscana.rete.rfc.sitatort.ResponseConsultaGara();
    
    // Inizializzazione dell'esito della risposta
    resultXML.setSuccess(false);

    // Gestione della connessione
    String urlPDD = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    
    boolean continua = true;
      
    if (continua) {
      if (StringUtils.isNotEmpty(urlPDD)) {
        
        SimogWSPDDServiceStub simogWsPddServiceStub = null;
        ResponseConsultaGara responseConsultaGara = null;
        
        // Richiesta del ticket all'oggetto per la gestione centralizzata della login ai servizi SIMOG
        TicketSimogBean ticketSimogBean = this.ticketSimogManager.getTicket();
        if (ticketSimogBean != null) {
          if (ticketSimogBean.isSuccess()) {
            String ticket = ticketSimogBean.getTicketSimog();
            
            try {
              String schede = "3.04.4.1.6";

              ConsultaGaraDocument consultaGaraDoc = ConsultaGaraDocument.Factory.newInstance();
              ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
              consultaGara.setTicket(ticket);
              consultaGara.setSchede(schede);
              consultaGara.setCIG(codiceCIG);
              consultaGaraDoc.setConsultaGara(consultaGara);
              
              simogWsPddServiceStub = new SimogWSPDDServiceStub(urlPDD);
              this.ticketSimogManager.setProxy(simogWsPddServiceStub);
              
              String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
              if (StringUtils.isNotEmpty(strTimeOut)) {
                Integer timeOutClient = new Integer(strTimeOut);
                simogWsPddServiceStub._getServiceClient().getOptions().setProperty(
                    HTTPConstants.SO_TIMEOUT, timeOutClient);
                simogWsPddServiceStub._getServiceClient().getOptions().setProperty(
                    HTTPConstants.CONNECTION_TIMEOUT, timeOutClient);
              }
              
              logger.info("Invocazione metodo consultaGara verso i Servizi SIMOG (Ticket=" + ticket + ", CIG=" + codiceCIG);
              ConsultaGaraResponseDocument consultaGaraResponseDoc = simogWsPddServiceStub.consultaGara(consultaGaraDoc);
              logger.info("Invocato il metodo consultaGara verso i Servizi SIMOG (Ticket=" + ticket + ", CIG=" + codiceCIG);
  
              if (consultaGaraResponseDoc.getConsultaGaraResponse().isSetReturn()) {
                responseConsultaGara = consultaGaraResponseDoc.getConsultaGaraResponse().getReturn();
                if (responseConsultaGara != null) {
                  if (responseConsultaGara.getSuccess()) {
                    
                    this.riallineaIndiciSimogUnificato(codiceCIG, codGara, codLott, 
                        responseConsultaGara.getGaraXML(), resultMap);
                  } else {
                    resultMap.put("esito", Boolean.FALSE);
                    if (StringUtils.isNotEmpty(responseConsultaGara.getError())) {
                      logger.error("La richiesta consultaGara con codice CIG pari a '" + codiceCIG
                          + "' e' terminata con esito negativo, fornendo il seguente messaggio: "
                          + responseConsultaGara.getError());
                      resultMap.put("errorMsg", "in Simog: ".concat(responseConsultaGara.getError()));
                    } else {
                      logger.error("La richiesta consultaGara con codice CIG pari a '" + codiceCIG
                          + "' e' terminata con esito negativo, ma non e' stato fornito un messaggio di errore");
                      resultMap.put("errorMsg", "non indicato dai servizi SIMOG");
                    }
                  }
                } else {
                  logger.error("La richiesta consultaGara con codice CIG pari a '" + codiceCIG
                        + "' e' terminata, ma nella risposta non e' stato fornito alcun dato");
                  resultMap.put("errorMsg", "dai servizi SIMOG: risposta priva delle informazioni necessarie");
                  resultMap.put("esito", Boolean.FALSE);
                }
                
              } else {
                logger.error("La richiesta consultaGara con codice CIG pari a '"
                    + codiceCIG + "' terminata con esito negativo a causa dell'oggetto "
                    + "it.avlp.simog.pdd.massload.xmlbeans.ResponseConsultaGara non valorizzato.");
                
                resultMap.put("esito", Boolean.FALSE);
                resultMap.put("errorMsg", "non previsto durante l'operazione di accesso dati dei servizi SIMOG"); 
              }

            } catch (RemoteException re) {
              logger.error("Errore nell'interazione con i servizi SIMOG per la chiamata " +
              		"consultaGara con codiceCIG='" + codiceCIG + "'", re);
            } catch (Throwable t) {
              logger.error("Errore inaspettato. Contattare un amministratore.", t);
            } finally {
              if (! ((Boolean) resultMap.get("esito")).booleanValue() && !resultMap.containsKey("errorMsg")) {
                resultMap.put("errorMsg", "con i servizio SIMOG e' momentaneamente non disponibile o non risponde correttamente. " +
                    "Riprovare piu' tardi. Se il problema persiste, segnalarlo all'amministratore di sistema.");
              }
              // Alla fine dell'interazione con i servizi Simog si rilascia il ticket usato
              // (all'oggetto TicketSimogManager si segnala semplicemente che si e' terminato
              // di usare il ticket)
              this.ticketSimogManager.rilasciaTicket();
            }
          } else {
            // il ticketSimogManager non e' riuscito a fornire un ticket... 
            this.ticketSimogManager.resetTicket();
            
            if (StringUtils.isNotEmpty(ticketSimogBean.getMsgError())) {
              resultMap.put("errorMsg", "in SIMOG: ".concat(ticketSimogBean.getMsgError()));
            } else {
              resultMap.put("errorMsg", "non indicato nell'operazione di login ai servizi SIMOG");
            }
            resultMap.put("esito", Boolean.FALSE);
          }
        } else {
          // il ticketSimogManager non e' riuscito a fornire un ticket,
          // probabilmente perché è rimasta una sessione aperta 
          this.ticketSimogManager.resetTicket();
          
          resultMap.put("errorMsg", "nell'operazione di login ai servizi SIMOG");
          resultMap.put("esito", Boolean.FALSE);
        }
      } else {
        logger.error("Errore di configurazione: manca l'indirizzo per la connessione al WS Osservatorio per "
            + "la chiamata consultaGara. Contattare un amministratore.");
        resultMap.put("esito", Boolean.FALSE);
        resultMap.put("errorMsg", " di configurazione: manca l'indirizzo di connessione ai servizi SIMOG. "
            + " Contattare un amministratore.");
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSimogDaOsservatorio: fine metodo");
    }
  }
  
  /**
   * Riallineamento degli indici SIMOG per i diversi flussi esitenti in base dati.
   * 
   * @param codiceCIG
   * @param codGara
   * @param codLott
   * @param schedaType
   * @param resultMap
   * @throws SQLException
   * @throws XmlException
   */
  private synchronized void riallineaIndiciSimogUnificato(
      final String codiceCIG, final long codGara, final long codLott,
      SchedaType schedaType, HashMap<String, Object> resultMap) throws SQLException, XmlException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSimogUnificato: inizio metodo");
    }

    if (schedaType.isSetDatiScheda()) {
      DatiAggiudicazioneType datiAggiudicazione = schedaType.getDatiScheda();
      
      // Dai DatiComuni dei Dati di aggiudicazione dell'oggetto ricevuto da SIMOG
      // si aggiornano ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG e CODCUI del lotto
      if (datiAggiudicazione.getDatiComuni().isSetIDSCHEDALOCALE()) {
        this.sqlManager.update("update W9LOTT set ID_SCHEDA_LOCALE=? where CIG=? and (ID_SCHEDA_LOCALE<>? or ID_SCHEDA_LOCALE is null)", 
            new Object[] { datiAggiudicazione.getDatiComuni().getIDSCHEDALOCALE(), codiceCIG,
                datiAggiudicazione.getDatiComuni().getIDSCHEDALOCALE()});
        this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.COMUNICAZIONE_ESITO, 1, -1, datiAggiudicazione.getDatiComuni().getIDSCHEDALOCALE());
      }
      
      if (datiAggiudicazione.getDatiComuni().isSetIDSCHEDASIMOG()) {
        this.sqlManager.update("update W9LOTT set ID_SCHEDA_SIMOG=? where CIG=? and (ID_SCHEDA_SIMOG<>? or ID_SCHEDA_SIMOG is null)", 
            new Object[] { datiAggiudicazione.getDatiComuni().getIDSCHEDASIMOG(), codiceCIG,
                datiAggiudicazione.getDatiComuni().getIDSCHEDASIMOG()});
        this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.COMUNICAZIONE_ESITO, 1, -1, datiAggiudicazione.getDatiComuni().getIDSCHEDASIMOG());
      } else {
        this.sqlManager.update("update W9LOTT set ID_SCHEDA_SIMOG=null where CIG=?", 
            new Object[] { codiceCIG });
        this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.COMUNICAZIONE_ESITO, 1, -1, null);
      }
      
      // Ricavo dal codice CUI il NUM_APPA Corrispondente
      for(int i=0; i<datiAggiudicazione.getSchedaCompletaArray().length; i++) {
    	  
    	  SchedaCompletaType schedaCompleta = datiAggiudicazione.getSchedaCompletaArray()[i];
    	  Long numappa = (Long) this.sqlManager.getObject("SELECT NUM_APPA FROM W9APPA WHERE CODCUI=?", new Object[] {schedaCompleta.getCUI()});
        
    	  if (numappa == null) {
    	  	numappa = (Long) this.sqlManager.getObject("SELECT max(NUM_APPA) FROM W9APPA WHERE CODGARA=? and CODLOTT=?", 
    	  			new Object[] { codGara, codLott });
    	  	if (numappa != null) {
    	  		String codCui = (String) this.sqlManager.getObject("SELECT CODCUI FROM W9APPA WHERE CODGARA=? and CODLOTT=? and NUM_APPA=?", 
    	  				new Object[] { codGara, codLott, numappa });
    	  		if (StringUtils.isNotEmpty(codCui)) {
    	  			numappa = null;
    	  		}
    	  	}
    	  }
    	  
    	  if (i == datiAggiudicazione.getSchedaCompletaArray().length - 1) {
    		  this.sqlManager.update("update W9LOTT set CODCUI=? where CIG=? and (CODCUI <> ? or CODCUI is null)", 
        	          new Object[] { schedaCompleta.getCUI(), codiceCIG, schedaCompleta.getCUI()});
    		  this.sqlManager.update("update W9APPA set CODCUI=? where CODGARA=? and CODLOTT=? and NUM_APPA=? and (CODCUI <> ? or CODCUI is null)", 
  	          new Object[] { schedaCompleta.getCUI(), codGara, codLott, numappa, schedaCompleta.getCUI()});
    	  }
    	  
    	  if (numappa != null) {
	    	  // Gestione Adesione accordo quadro
	        if (schedaCompleta.isSetAdesione()) {
	          this.aggiornaIdSchedaAdesione(codGara, codLott, numappa, schedaCompleta.getAdesione());
	        }
	
	        // Gestione Aggiudicazione
	        if (schedaCompleta.isSetAggiudicazione()) {
	          this.aggiornaIdSchedaAggiudicazione(codGara, codLott, numappa, schedaCompleta.getAggiudicazione());
	        } else {
	          this.aggiornaIdSchedaAggiudicazione(codGara, codLott, numappa, null);
	        }
	          
	        // Gestione Accordi bonari
	        if (schedaCompleta.isSetDatiAccordi()) {
	          this.aggiornaIdSchedaAccordi(codGara, codLott, numappa,	schedaCompleta.getDatiAccordi());
	        } else {
	          this.aggiornaIdSchedaAccordi(codGara, codLott, numappa, null);
	        }
	        
	        // Gestione Avanzamento contratto
	        if (schedaCompleta.isSetDatiAvanzamenti()) {
	          this.aggiornaIdSchedaAvanzamenti(codGara, codLott, numappa,	schedaCompleta.getDatiAvanzamenti());
	        } else {
	          this.aggiornaIdSchedaAvanzamenti(codGara, codLott, numappa, null);
	        }
	        
	        // Gestione Collaudo contratto
	        if (schedaCompleta.isSetDatiCollaudo()) {
	          this.aggiornaIdSchedaCollaudo(codGara, codLott, numappa, schedaCompleta.getDatiCollaudo());
	        } else {
	          this.aggiornaIdSchedaCollaudo(codGara, codLott, numappa, null);
	        }
	        
	        // Gestione Conclusione contratto
	        if (schedaCompleta.isSetDatiConclusione()) {
	          this.aggiornaIdSchedaConclusione(codGara, codLott, numappa,	schedaCompleta.getDatiConclusione());
	        } else {
	          this.aggiornaIdSchedaConclusione(codGara, codLott, numappa, null);
	        }
	          
	        // Gestione Inizio contratto
	        if (schedaCompleta.isSetDatiInizio()) {
	          this.aggiornaIdSchedaInizio(codGara, codLott, numappa, schedaCompleta.getDatiInizio().getInizio());
	        } else {
	          this.aggiornaIdSchedaInizio(codGara, codLott, numappa, null);
	        }
	        
	        // Gestione Sospensione contratto
	        if (schedaCompleta.isSetDatiSospensioni()) {
	          this.aggiornaIdSchedaSospensioni(codGara, codLott, numappa,	schedaCompleta.getDatiSospensioni());
	        } else {
	          this.aggiornaIdSchedaSospensioni(codGara, codLott, numappa, null);
	        }
	
	        if (schedaCompleta.isSetDatiStipula()) {
	          this.aggiornaIdSchedaStipula(codGara, codLott, numappa,	schedaCompleta.getDatiStipula().getStipula());
	        } else {
	          this.aggiornaIdSchedaStipula(codGara, codLott, numappa, null);
	        }
	
	        // Gestione Subappalto contratto
	        if (schedaCompleta.isSetDatiSubappalti()) {
	          this.aggiornaIdSchedaSubappalti(codGara, codLott, numappa, schedaCompleta.getDatiSubappalti());
	        } else {
	          this.aggiornaIdSchedaSubappalti(codGara, codLott, numappa, null);
	        }
	
	        // Gestione Variante contratto
	        if (schedaCompleta.isSetDatiVarianti()) {
	          this.aggiornaIdSchedaVarianti(codGara, codLott, numappa, schedaCompleta.getDatiVarianti());
	        } else {
	          this.aggiornaIdSchedaVarianti(codGara, codLott, numappa, null);
	        }
	          
	        // Gestione Contratto Escluso
	        if (schedaCompleta.isSetEscluso()) {
	          this.aggiornaIdSchedaEscluso(codGara, codLott, numappa,	schedaCompleta.getEscluso());
	        } else {
	          this.aggiornaIdSchedaEscluso(codGara, codLott, numappa, null);
	        }
	        
	        // Gestione Contratto sotto soglia
	        if (schedaCompleta.isSetSottosoglia()) {
	          this.aggiornaIdSchedaSottosoglia(codGara, codLott, numappa,	schedaCompleta.getSottosoglia());
	        } else {
	          this.aggiornaIdSchedaSottosoglia(codGara, codLott, numappa, null);
	        }
    	  }
      }
      
      resultMap.put("esito", Boolean.TRUE);
    } else {
      resultMap.put("esito", Boolean.FALSE);
      resultMap.put("errorMsg", "nell'aggiornamento degli indici delle fasi del CIG=" + codiceCIG + 
          " perche' nella risposta ricevuta da SIMOG mancano i dati");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSimogUnificato: fine metodo");
    }
  }
  
  private void aggiornaIdSchedaAdesione(final long codGara, final long codLott, final long numappa, 
      final AdesioneType adesione) throws SQLException {
    this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.ADESIONE_ACCORDO_QUADRO, -1, numappa,
        adesione.getAppalto().getIDSCHEDALOCALE());
    this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.ADESIONE_ACCORDO_QUADRO, -1, numappa,
        adesione.getAppalto().getIDSCHEDASIMOG());
  }
  
  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per la fase di
   * aggiudicazione contratto sopra e sotto soglia.
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param aggiudicazione
   * @throws SQLException
   */
  private void aggiornaIdSchedaAggiudicazione(final long codGara, final long codLott, final long numappa, 
      final AggiudicazioneType aggiudicazione) throws SQLException {
    if (aggiudicazione != null) {
      this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, -1, numappa,
          aggiudicazione.getAppalto().getIDSCHEDALOCALE());
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, -1, numappa,
          aggiudicazione.getAppalto().getIDSCHEDASIMOG());
    } else {
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, -1, numappa, null);
    }
  }
  
  private void aggiornaIdSchedaCollaudo(final long codGara, final long codLott, final long numappa, 
      final DatiCollaudoType datiCollaudo) throws SQLException {
    if (datiCollaudo != null) {
      CollaudoType collaudo = datiCollaudo.getCollaudo();
      this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.COLLAUDO_CONTRATTO, -1, numappa,
          collaudo.getIDSCHEDALOCALE());
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.COLLAUDO_CONTRATTO, -1, numappa,
            collaudo.getIDSCHEDASIMOG());
    } else {
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.COLLAUDO_CONTRATTO, -1, numappa, null);
    }
  }

  private void aggiornaIdSchedaConclusione(final long codGara, final long codLott, final long numappa, 
      final ConclusioneType conclusione) throws SQLException {
    if (conclusione != null) {
      this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
            conclusione.getIDSCHEDALOCALE());
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
            conclusione.getIDSCHEDASIMOG());
    } else {
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, -1, numappa, null);
    }
  }
  
  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per la fase di
   * inizio contratto sopra soglia.
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param inizio
   * @throws SQLException
   */
  private void aggiornaIdSchedaInizio(final long codGara, final long codLott, final long numappa, 
      final InizioType inizio) throws SQLException {
    if (inizio != null) {
      this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
          inizio.getIDSCHEDALOCALE());
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
          inizio.getIDSCHEDASIMOG());
    } else {
      this.sbiancaIdSchedaSimog(codGara, codLott, numappa, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA);
    }
  }
  
  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per le fasi di
   * sospensione contratto.
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param datiAggiudicazione
   * @throws SQLException
   */
  private void aggiornaIdSchedaSospensioni(final long codGara, final long codLott, final long numappa, 
      final SospensioniType datiSospensioni) throws SQLException {
    if (datiSospensioni != null) {
      SospensioneType[] arraySospensioni = datiSospensioni.getSospensioneArray();
      
      for (int i = 0; i < arraySospensioni.length; i++) {
        SospensioneType sospensione = arraySospensioni[i];
        // Solo se in SIMOG e' valorizzato l'ID_SCHEDA_LOCALE, allora si riesce a risalire
        // alla fase di avanzamento presente nella W9FASI ed aggiornare l'ID_SCHEDA_SIMOG
        if (sospensione.isSetIDSCHEDALOCALE() && sospensione.isSetIDSCHEDASIMOG()) {
          Long fase = this.getFase(sospensione.getIDSCHEDALOCALE());
          Long numProg = this.getProgressivoFase(sospensione.getIDSCHEDALOCALE());
          if (fase != null && numProg != null) {
            Long numeroFasi = (Long) this.sqlManager.getObject(
                "select count(*) from W9FLUSSO where ID_SCHEDA_LOCALE=? and CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
                new Object[] { sospensione.getIDSCHEDALOCALE(), codGara, codLott, fase, numProg });
            if (numeroFasi.intValue() == 1) {
              this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.SOSPENSIONE_CONTRATTO, numProg, numappa,
                  sospensione.getIDSCHEDASIMOG());
            }
          }
        }
      }
    } else {
      this.sbiancaIdSchedaSimog(codGara, codLott, numappa, CostantiW9.SOSPENSIONE_CONTRATTO);
    }
  }
  
  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per la fase di
   * stipula adesione accordo quadro.
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param stipula
   * @throws SQLException
   */
  private void aggiornaIdSchedaStipula(final long codGara, final long codLott, final long numappa, 
      final StipulaType stipula) throws SQLException {
    if (stipula != null) {
      this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.STIPULA_ACCORDO_QUADRO, -1, numappa,
          stipula.getIDSCHEDALOCALE());
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.STIPULA_ACCORDO_QUADRO, -1, numappa,
          stipula.getIDSCHEDASIMOG());
    } else {
      this.sbiancaIdSchedaSimog(codGara, codLott, numappa, CostantiW9.STIPULA_ACCORDO_QUADRO);
    }
  }
  
  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per le fasi di
   * avanzamento contratto sopra e sotto soglia.
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param datiAggiudicazione
   * @throws SQLException
   */
  private void aggiornaIdSchedaAvanzamenti(final long codGara, final long codLott, final long numappa, 
      final AvanzamentiType datiAvanzamenti) throws SQLException {
    if (datiAvanzamenti != null) {
      AvanzamentoType[] arrayAvanzamenti = datiAvanzamenti.getAvanzamentoArray();
      
      for (int i = 0; i < arrayAvanzamenti.length; i++) {
        AvanzamentoType avanzamento = arrayAvanzamenti[i];
        // Solo se in SIMOG e' valorizzato l'ID_SCHEDA_LOCALE, allora si riesce a risalire
        // alla fase di avanzamento presente nella W9FASI ed aggiornare l'ID_SCHEDA_SIMOG
        if (avanzamento.isSetIDSCHEDALOCALE() && avanzamento.isSetIDSCHEDALOCALE()) {
          Long fase = this.getFase(avanzamento.getIDSCHEDALOCALE());
          Long numProg = this.getProgressivoFase(avanzamento.getIDSCHEDALOCALE());
          if (fase != null && numProg != null) {
            Long numeroFasi = (Long) this.sqlManager.getObject(
                "select count(*) from W9FLUSSO where ID_SCHEDA_LOCALE=? and CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
                new Object[] { avanzamento.getIDSCHEDALOCALE(), codGara, codLott, fase, numProg });
            if (numeroFasi.intValue() == 1) {
              this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA, numProg, numappa,
                  avanzamento.getIDSCHEDASIMOG());
            }
          }
        }
      }
    } else {
      this.sbiancaIdSchedaSimog(codGara, codLott, numappa, CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA);
    }
  }

  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per le fasi di subappalto.
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param datiAggiudicazione
   * @throws SQLException
   */
  private void aggiornaIdSchedaSubappalti(final long codGara, final long codLott, final long numappa,
      final SubappaltiType datiSubappalti) throws SQLException {
    if (datiSubappalti != null) {
	    SubappaltoType[] arraySubappalti = datiSubappalti.getSubappaltoArray();
	    
	    for (int i = 0; i < arraySubappalti.length; i++) {
	      SubappaltoType subappalto = arraySubappalti[i];
	      // Solo se in SIMOG e' valorizzato l'ID_SCHEDA_LOCALE, allora si riesce a risalire
	      // al subappalto presente nella W9FASI ed aggiornare l'ID_SCHEDA_SIMOG
	      if (subappalto.isSetIDSCHEDALOCALE() && subappalto.isSetIDSCHEDASIMOG()) {
	        Long fase = this.getFase(subappalto.getIDSCHEDALOCALE());
	        Long numProg = this.getProgressivoFase(subappalto.getIDSCHEDALOCALE());
	        if (fase != null && numProg != null) {
	          Long numeroFasi = (Long) this.sqlManager.getObject(
	              "select count(*) from W9FLUSSI where ID_SCHEDA_LOCALE=? and CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
	              new Object[] { subappalto.getIDSCHEDALOCALE(), codGara, codLott, fase, numProg });
	          if (numeroFasi.intValue() == 1) {
	            this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.SUBAPPALTO, numProg, numappa,
	                subappalto.getIDSCHEDASIMOG());
	          }
	        }
	      }
	    }
    } else {
      this.sbiancaIdSchedaSimog(codGara, codLott, numappa, CostantiW9.SUBAPPALTO);
    }
  }

  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per le varianti.
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param datiVarianti
   * @throws SQLException
   */
  private void aggiornaIdSchedaVarianti(final long codGara, final long codLott, final long numappa,
      final VariantiType datiVarianti) throws SQLException {
    if (datiVarianti != null) {
      VarianteType[] arrayVarianti = datiVarianti.getVarianteArray();
      
      for (int i = 0; i < arrayVarianti.length; i++) {
        VarianteType variante = arrayVarianti[i];
        // Solo se in SIMOG e' valorizzato l'ID_SCHEDA_LOCALE, allora si riesce a risalire
        // alla variante presente nella W9FASI ed aggiornare l'ID_SCHEDA_SIMOG
        if (variante.getVariante().isSetIDSCHEDALOCALE() && variante.getVariante().isSetIDSCHEDASIMOG()) {
          Long fase = this.getFase(variante.getVariante().getIDSCHEDALOCALE());
          Long numProg = this.getProgressivoFase(variante.getVariante().getIDSCHEDALOCALE());
          if (fase != null && numProg != null) {
            Long numeroFasi = (Long) this.sqlManager.getObject(
                "select count(*) from W9FLUSSI where ID_SCHEDA_LOCALE=? and CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
                new Object[] { variante.getVariante().getIDSCHEDALOCALE(), codGara, codLott, fase, numProg });
            if (numeroFasi.intValue() == 1) {
              this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.VARIANTE_CONTRATTO, numProg, numappa,
                  variante.getVariante().getIDSCHEDASIMOG());
            }
          }
        }
      }
    } else {
      this.sbiancaIdSchedaSimog(codGara, codLott, numappa, CostantiW9.VARIANTE_CONTRATTO);
    }
  }
  
  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per la fase di
   * aggiudicazione contratto escluso (cioe' sotto soglia).
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param escluso
   * @throws SQLException
   */
  private void aggiornaIdSchedaEscluso(final long codGara, final long codLott, final long numappa, 
      final SchedaEsclusoType escluso) throws SQLException {
    //Long numProg = new Long(1);
    if (escluso != null) {
      this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, 
          -1, numappa, escluso.getAppalto().getIDSCHEDALOCALE());
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, 
          -1, numappa, escluso.getAppalto().getIDSCHEDASIMOG());
    } else {
      this.sbiancaIdSchedaSimog(codGara, codLott, numappa, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE);
    }
  }

  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per la fase di
   * aggiudicazione contratto sotto soglia.
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param sottoSoglia
   * @throws SQLException
   */
  private void aggiornaIdSchedaSottosoglia(final long codGara, final long codLott, final long numappa, 
      final SchedaSottosogliaType sottoSoglia) throws SQLException {
    //Long numProg = new Long(1);
    
    if (sottoSoglia != null) {
      this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE,
          -1, numappa, sottoSoglia.getAppalto().getIDSCHEDALOCALE());
      this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, 
          -1, numappa, sottoSoglia.getAppalto().getIDSCHEDASIMOG());
    } else {
      this.sbiancaIdSchedaSimog(codGara, codLott, numappa, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE);
    }
  }
  
  /**
   * Aggiornamento ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG di W9FASI per le fasi di subappalto.
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param datiAggiudicazione
   * @throws SQLException
   */
  private void aggiornaIdSchedaAccordi(final long codGara, final long codLott, final long numappa,
      final AccordiBonariType datiAccordi) throws SQLException {
    
    if (datiAccordi != null) {
      AccordoBonarioType[] arrayAccordi = datiAccordi.getAccordoBonarioArray();
      
      for (int i = 0; i < arrayAccordi.length; i++) {
        AccordoBonarioType accordo = arrayAccordi[i];
        // Solo se in SIMOG e' valorizzato l'ID_SCHEDA_LOCALE, allora si riesce a risalire
        // all'accordo bonario presente nella W9FASI ed aggiornare l'ID_SCHEDA_SIMOG
        if (accordo.isSetIDSCHEDALOCALE() && accordo.isSetIDSCHEDASIMOG()) {
          Long fase = this.getFase(accordo.getIDSCHEDALOCALE());
          Long numProg = this.getProgressivoFase(accordo.getIDSCHEDALOCALE());
          if (fase != null && numProg != null) {
            Long numeroFasi = (Long) this.sqlManager.getObject(
                "select count(*) from W9FLUSSI where ID_SCHEDA_LOCALE=? and CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
                new Object[] { accordo.getIDSCHEDALOCALE(), codGara, codLott, fase, numProg });
            if (numeroFasi.intValue() == 1) {
              this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.ACCORDO_BONARIO, numProg, numappa,
                  accordo.getIDSCHEDASIMOG());
            }
          }
        }
      }
    } else {
      this.sbiancaIdSchedaSimog(codGara, codLott, numappa, CostantiW9.ACCORDO_BONARIO);
    }
  }
  
  /**
   * Aggiornamento di W9FASI.ID_SCHEDA_LOCALE, solo se diverso da quello ricevuto da SIMOG.
   *  
   * @param codGara
   * @param codLott
   * @param fase
   * @param numProg
   * @param numappa
   * @param idSchedaLocale
   * @throws SQLException
   */
  private void aggiornaIdSchedaLocale(final long codGara, final long codLott, final long fase,
      final long numProg, final long numappa, final String idSchedaLocale) throws SQLException {
    if (logger.isDebugEnabled()) {
      logger.debug("aggiornaIdSchedaLocale: inizio metodo");
    }
    
    if (StringUtils.isNotEmpty(idSchedaLocale)) {
    	if (numProg == -1) {
    		this.sqlManager.update("update W9FASI set ID_SCHEDA_LOCALE=? where (ID_SCHEDA_LOCALE<>? or ID_SCHEDA_LOCALE is null) " +
  		      " and CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM_APPA=?",
  		          new Object[] { idSchedaLocale, idSchedaLocale, codGara, codLott, fase, numappa });
    	} else {
    		this.sqlManager.update("update W9FASI set ID_SCHEDA_LOCALE=? where (ID_SCHEDA_LOCALE<>? or ID_SCHEDA_LOCALE is null) " +
    		    " and CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
    		        new Object[] { idSchedaLocale, idSchedaLocale, codGara, codLott, fase, numProg });
    	}
    }

    if (logger.isDebugEnabled()) {
      logger.debug("aggiornaIdSchedaLocale: fine metodo");
    }
  }
  
  /**
   * Aggiornamento di W9FASI.ID_SCHEDA_SIMOG solo se diverso da quello ricevuto da SIMOG.
   * 
   * @param codGara
   * @param codLott
   * @param fase
   * @param numProg
   * @param numappa
   * @param idSchedaSimog
   * @throws SQLException
   */
  private void aggiornaIdSchedaSimog(final long codGara, final long codLott, final long fase,
      final long numProg, final long numappa, final String idSchedaSimog) throws SQLException {
    if (logger.isDebugEnabled()) {
      logger.debug("aggiornaIdSchedaSimog: inizio metodo");
    }
    if (numProg == -1) {
    	if (StringUtils.isNotEmpty(idSchedaSimog)) {
    	      this.sqlManager.update("update W9FASI set ID_SCHEDA_SIMOG=? where (ID_SCHEDA_SIMOG<>? or ID_SCHEDA_SIMOG is null) " +
    	          " and CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM_APPA=?",
    	          new Object[] { idSchedaSimog, idSchedaSimog, codGara, codLott, fase, numappa });
    	    } else {
    	      this.sqlManager.update("update W9FASI set ID_SCHEDA_SIMOG=null " +
    	          "where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM_APPA=?",
    	          new Object[] { codGara, codLott, fase, numappa });
    	    }
	} else {
		if (StringUtils.isNotEmpty(idSchedaSimog)) {
	    	
		      this.sqlManager.update("update W9FASI set ID_SCHEDA_SIMOG=? where (ID_SCHEDA_SIMOG<>? or ID_SCHEDA_SIMOG is null) " +
		          " and CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
		          new Object[] { idSchedaSimog, idSchedaSimog, codGara, codLott, fase, numProg });
		    } else {
		      this.sqlManager.update("update W9FASI set ID_SCHEDA_SIMOG=null " +
		          "where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
		          new Object[] { codGara, codLott, fase, numProg });
		    }
	}
    
    if (logger.isDebugEnabled()) {
      logger.debug("aggiornaIdSchedaSimog: fine metodo");
    }
  }

  /**
   * Sbiancamento di W9FASI.ID_SCHEDA_SIMOG per tutte le fasi .
   * 
   * @param codGara
   * @param codLott
   * @param numappa
   * @param fase
   * @param numProg
   * @param idSchedaSimog
   * @throws SQLException
   */
  private void sbiancaIdSchedaSimog(final long codGara, final long codLott, final long numappa, final long fase) throws SQLException {
    if (logger.isDebugEnabled()) {
      logger.debug("sbiancaIdSchedaSimog: inizio metodo");
    }

    this.sqlManager.update("update W9FASI set ID_SCHEDA_SIMOG=null " +
        "where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM_APPA=?",
        new Object[] { codGara, codLott, fase, numappa });

    if (logger.isDebugEnabled()) {
      logger.debug("sbiancaIdSchedaSimog: fine metodo");
    }
  }
  
  /**
   * Se l'id_scheda_locale e' nel formato \d{3}_\d{3}_\d{3}, dall'ultima tripletta
   * si ricava il progressivo della fase, convertendo la stringa in numero, dopo aver
   * rimosso gli zeri iniziali.
   * 
   * @param idSchedaLocale
   * @return Ritorna il progressivo della fase
   */
  private Long getProgressivoFase(String idSchedaLocale) {
    Long result = null;
    
    if (idSchedaLocale.matches("\\d{3}_\\d{3}_\\d{3}")) {
      String[] temp = idSchedaLocale.split("_");
      result = new Long(StringUtils.stripStart(temp[2], "0"));
    }
    return result;
  }
 
  /**
   * Se l'id_scheda_locale e' nel formato \d{3}_\d{3}_\d{3}, dalla seconda tripletta
   * si ricava la fase, convertendo la stringa in numero, dopo aver rimosso gli zeri iniziali.
   * 
   * @param idSchedaLocale
   * @return Ritorna la fase
   */
  private Long getFase(String idSchedaLocale) {
    Long result = null;
    
    if (idSchedaLocale.matches("\\d{3}_\\d{3}_\\d{3}")) {
      String[] temp = idSchedaLocale.split("_");
      result = new Long(StringUtils.stripStart(temp[1], "0"));
    }
    return result;
  }
  
}
