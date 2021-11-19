/*
 * Created on 24/giu/2010
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.bl;

import it.avlp.simog.massload.xmlbeans.Collaborazioni;
import it.avlp.simog.massload.xmlbeans.ConsultaGara;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaNumeroGara;
import it.avlp.simog.massload.xmlbeans.ConsultaNumeroGaraDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaNumeroGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.ResponseConsultaGara;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.avlp.smartCig.risultato.RisultatoType;
import it.avlp.smartCig.user.LoggedUserInfoType;
import it.avlp.smartCig.ws.ConsultaComunicazioneRequest;
import it.avlp.smartCig.ws.ConsultaComunicazioneResponse;
import it.avlp.smartCig.ws.Services;
import it.avlp.smartCig.ws.ServicesServiceLocator;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.bl.simog.AccessoSimogManager;
import it.eldasoft.w9.bl.simog.TicketSimogBean;
import it.eldasoft.w9.bl.simog.TicketSimogManager;
import it.eldasoft.w9.common.CostantiSimog;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Stefano.Cestaro
 */
public class GeneraIdGaraCigManager {

  /**
   * Logger della classe.
   */
  private static Logger logger = Logger.getLogger(GeneraIdGaraCigManager.class);

  /**
   * Manager del ticket sui servizi Simog.
   */
  private TicketSimogManager ticketSimogManager;
  
  /** Manager per l'accesso ai servizi Simog in configurazione Vigilanza */
  private AccessoSimogManager accessoSimogManager;  

  public void setTicketSimogManager(final TicketSimogManager ticketSimogManager) {
    this.ticketSimogManager = ticketSimogManager;
  }

  /**
   * Set accessoSimogManager.
   * 
   * @param accessoSimogManager
   *        accessoSimogManager da settare internamente alla classe.
   */
  public void setAccessoSimogManager(AccessoSimogManager accessoSimogManager) {
    this.accessoSimogManager = accessoSimogManager;
  }
  /**
   * Generazione di IdAvGara via WS SIMOG.
   * 
   * @param dataColumnContainer DataColumnContainer
   * @return Ritorna la HashMap stringa-valore con il risultato dell'operazione
   * 
   * @throws GestoreException GestoreException.
   */
  public HashMap<String, Object> generaIdGara(final DataColumnContainer dataColumnContainer)
  throws GestoreException {

    if (logger.isDebugEnabled()) {
      logger.debug("generaIdGara: inizio metodo");
    }
    HashMap<String , Object> risultato = new HashMap<String, Object>();

    /*
    try {
      // Gestione della connessione
      String url = ConfigManager.getValore(PROP_SIMOG_WS_URL);
      if (url == null || "".equals(url)) {
        logger.error("L'indirizzo per la connessione al web service SIMOG non e' definito");
      }

      String login = dataColumnContainer.getString("LOGIN");
      if (login == null || "".equals(login)) {
        logger.error("La login per la connessione al web service SIMOG non e' definita");
      }

      String password = dataColumnContainer.getString("PASSWORD");
      if (password == null || "".equals(password)) {
        logger.error("La password per la connessione al web service SIMOG non e' definita");
      }

      if (StringUtils.isNotEmpty(url)
          && StringUtils.isNotEmpty(login)
          && StringUtils.isNotEmpty(password)) {

        // Verifica i parametri di connessione al WS SIMOG in https e via proxy
        //this.verificaParametriConnessioneWS();

        SimogWSServiceLocator wsLocator = new SimogWSServiceLocator();
        wsLocator.setSimogWSEndpointAddress(url);
        
        SimogWS serviceSimogWS = wsLocator.getSimogWS();
        ResponseCheckLogin rispostaLogin = serviceSimogWS.login(login, password);

        if (rispostaLogin.isSuccess()) {
          String ticketSessionId = rispostaLogin.getTicket();

          logger.error("--- DEBUG ACCESSO SIMOG ---");
          logger.error("Utente SIMOG: " + login);
          logger.error("Password SIMOG: " + password);
          logger.error("--- DEBUG ACCESSO SIMOG ---");

          logger.error("--- DEBUG ACCESSO SIMOG ---");
          logger.error("--- SessionID (ticket): " + ticketSessionId);
          logger.error("--- DEBUG ACCESSO SIMOG ---");

          // Per accordi con Regione Toscana e SIMOG le collaborazioni non vengono popolate.
          // Al momento l'istruzione rispostaLogin.getColl() non deve essere usata.
          // Per il valore da assegnare all'index, si seguono le indicazioni
          // degli accordi menzionati: l'index viene sempre settato a ''
          //String index = "";

          Collaborazioni collaborazioni = rispostaLogin.getColl();
          if (collaborazioni != null) {
            if (collaborazioni.getCollaborazioni() != null && collaborazioni.getCollaborazioni().length > 0) {
              Collaborazione[] arrayColl = collaborazioni.getCollaborazioni();

              logger.error("--- DEBUG ACCESSO SIMOG ---");
              logger.error("--- Numero Collaborazioni: " + arrayColl.length + "---");
              for (int ij = 0; ij < arrayColl.length; ij++) {
                Collaborazione collaborazione = arrayColl[ij];
                if (collaborazione != null) {
                  logger.error("Collaborazione n. " + (ij + 1) + ": "
                      + "Azienda_codiceFiscale = '" + collaborazione.getAzienda_codiceFiscale() + "', "
                      + "Azienda_denominazione = " + collaborazione.getAzienda_denominazione() + "', "
                      + "IdOsservatorio = '" + collaborazione.getIdOsservatorio() + "', "
                      + "Ufficio_denominazione = '" + collaborazione.getUfficio_denominazione() + "', "
                      + "Ufficio_id = '" + collaborazione.getUfficio_id() + "', "
                      + "Ufficio_profilo = '" + collaborazione.getUfficio_profilo() + "', "
                      + "Indice di collaborazione = '" + collaborazione.getIndex() + "'.");
                } else {
                  logger.error("Collaborazione n. " + (ij + 1) + ": null.");
                }
              }

              logger.error("--- DEBUG ACCESSO SIMOG ---");
            }
          }

          // Richiesta generazione del codice identificativo della gara al
          // servizio SIMOG attraverso il metodo inserisciGara del WS
          boolean isCollaudo = false;

          // Se l'url del WS SIMOG in uso e' quella di collaudo, allora si valorizzano i campi IDSTAZIONEAPPALTANTE e
          // DENOMSTAZIONEAPPALTANTE dell'oggetto DatiGataType con due stringhe casuali, giusto per superare
          // la validazione del XML richiesto. Questo perche' l'utente Bertocchini e' un utente di sola
          // consultazione dei dati SIMOG, privo di collaborazioni.
          if (url.toLowerCase().indexOf("collaudosimog") >= 0) {
            isCollaudo = true;
          }

          //this.richiestaIdAvgaraSIMOG(serviceSimogWS, ticketSessionId, collaborazioni, dataColumnContainer, risultato, isCollaudo);
          // Chiusura della connessione
          ResponseChiudiSession rispostaChiudiSessione = serviceSimogWS.chiudiSessione(ticketSessionId);
          if (!rispostaChiudiSessione.isSuccess()) {
            if (logger.isInfoEnabled()) {
              logger.info("La chiusura della connessione identificata dal ticket "
                  + ticketSessionId + " ha generato il seguente errore: "
                  + rispostaChiudiSessione.getError());
            }
            risultato.put("ESITO", "KO");
            risultato.put("ERRORE", rispostaChiudiSessione.getError());
            risultato.put("OPERAZIONE", "chiusuraSessioneSimog");

          }
        } else {
          String message = "Errore durante la connessione al web service SIMOG: ";
          message += rispostaLogin.getError();
          if (logger.isInfoEnabled()) {
            logger.info("Il tentativo di connessione ha generato il seguente errore: "
                + rispostaLogin.getError());
          }
          risultato.put("ESITO", "KO");
          risultato.put("ERRORE", rispostaLogin.getError());
          risultato.put("OPERAZIONE", "aperturaSessioneSimog");

        }
      }
    } catch (SQLException s) {
      throw new GestoreException("Si e' verificato un errore nell'estrazione dei dati necessari alla richiesta di "
          + "inserimento nuova gara in SIMOG attraverso il WS",
          "generaidgaracig.simog.ws.sqlw9gara", s);
    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service SIMOG",
          "generaidgaracig.simog.ws.error", e);

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la connessione al web service SIMOG",
          "generaidgaracig.simog.ws.error", e);
    }
    
    */
    if (logger.isDebugEnabled()) {
      logger.debug("generaIdGara: fine metodo");
    }
    return risultato;
  }

  /**
   * Generazione del codice CIG del lotto via WS SIMOG.
   * 
   * @param dataColumnContainer DataColumnContainer
   * @return Ritorna una HashMap stringa-valore con il risultato dell'operazione
   * @throws GestoreException GestoreException
   */
  public HashMap<String , Object> generaCig(final DataColumnContainer dataColumnContainer)
  throws GestoreException {
    if (logger.isDebugEnabled()) {
      logger.debug("generaCig: inizio metodo");
    }
    HashMap<String , Object> risultato = new HashMap<String, Object>();

    if (logger.isDebugEnabled()) {
      logger.debug("generaIdGaraCig: fine metodo");
    }

    return risultato;
  }

  /**
   * Metodo per invocare il WS consultaGara di SIMOG con ingresso
   * il codice CIG che identifica la gara.
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param idGara Numero della gara
   * @param cfrup CF del RUP
   * @param cfsa CF della S.A.
   * @param eseguiControlliPreliminari 
   * @return Ritorna l'xml contenente le informazioni di dettaglio della gara
   * @throws RemoteException RemoteException
   */
  public it.toscana.rete.rfc.sitatort.ResponseConsultaGara consultaGaraORT(
      final String codiceCIG, final String idGara, final String cfrup,
      final String cfsa, boolean eseguiControlliPreliminari) throws RemoteException {

    it.toscana.rete.rfc.sitatort.ResponseConsultaGara resultXML = 
        new it.toscana.rete.rfc.sitatort.ResponseConsultaGara();

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraORT: inizio metodo");
    }

    // Valore di default per il tipo di accesso ai servizi Simog
    int tipoAccessoWSSimog = 0;

    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
    
    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoWSSimog = Integer.parseInt(a);
    }
    
    switch (tipoAccessoWSSimog) {
      
    case CostantiSimog.ORT_CONSULTA_GARA_VIA_WS:
      this.consultaGaraViaPDD(codiceCIG, idGara, cfrup, cfsa, resultXML, eseguiControlliPreliminari);
      break;
    
    default:
      String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
          "indicato il tipo di accesso ai servizi SIMOG";
      logger.error(msgError);
      
      resultXML.setSuccess(Boolean.FALSE);
      resultXML.setError(msgError + ". Contattare un amministratore");
      break;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraORT: fine metodo");
    }

    return resultXML;
  }

  private void consultaGaraViaPDD(String codiceCIG, final String idGara,
      final String cfrup, final String cfsa, it.toscana.rete.rfc.sitatort.ResponseConsultaGara resultXML, 
      boolean eseguiControlliPreliminari) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraViaPDD: inizio metodo");
    }
    
    // Inizializzazione dell'esito della risposta
    resultXML.setSuccess(false);

    // Gestione della connessione
    String urlPDD = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    
    boolean isSmartCIG = false;
    String urlSmartCIG = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_SMARTCIG_URL);
    //se sono nella configurazione SA -> OR nel caso della richiesta di uno smartcig da SA mi arrivano anche le credenziali del RUP (questo perchè per lo smartcig non esistono credenziali per l'osservatorio)
    //quando mi arriva una smartcig il formato del dato è codiceCIG#username#password
    String[] eventualiCredenzialiRup = null;
    if (StringUtils.isNotEmpty(codiceCIG)) {
    	eventualiCredenzialiRup = codiceCIG.split("#");
    }
    String simogUser = null;
    String simogPassword = null;
    
    if (eventualiCredenzialiRup != null && eventualiCredenzialiRup.length == 3) {
    	codiceCIG = eventualiCredenzialiRup[0];
    	simogUser = eventualiCredenzialiRup[1];
    	simogPassword = eventualiCredenzialiRup[2];
    }
    if (UtilitySITAT.isSmartCigValido(codiceCIG)) {
    	isSmartCIG = true;
    	if (StringUtils.isEmpty(urlSmartCIG)) {
            logger.error("URL per accesso ai servizi SIMOG-smartCIG non e' definita");
        }
    }
    
    boolean continua = true;
    	
    if (continua) {
      if (StringUtils.isNotEmpty(urlPDD) && (StringUtils.isNotEmpty(urlSmartCIG) || !isSmartCIG)) {
        
        SimogWSPDDServiceStub simogWsPddServiceStub = null;
        ResponseConsultaGara responseConsultaGara = null;
        
        // Richiesta del ticket all'oggetto per la gestione centralizzata della login ai servizi SIMOG
        TicketSimogBean ticketSimogBean = null;
        
        if (simogUser != null) {
        	ticketSimogBean = this.accessoSimogManager.simogLogin(simogUser, simogPassword);
        } else {
        	ticketSimogBean = this.ticketSimogManager.getTicket();
        }
        if (ticketSimogBean != null) {
          if (ticketSimogBean.isSuccess()) {
            String ticket = ticketSimogBean.getTicketSimog();
            //Collaborazioni collaborazioniPdd = ticketSimogBean.getCollaborazioniPDD();
            
            try {
            	if (isSmartCIG) {
            		ServicesServiceLocator WSLocator = new ServicesServiceLocator();
      	    	  	WSLocator.setServicesSoap11EndpointAddress(urlSmartCIG);
      	    	  	Services simogWS = WSLocator.getServicesSoap11();
      	    	  
      	    	  	ConsultaComunicazioneRequest richiesta = new ConsultaComunicazioneRequest();
      	    	  	richiesta.setCig(codiceCIG);
      	    	  	ConsultaComunicazioneResponse comunicazioneResponse = null;
      	    	  	LoggedUserInfoType user = new LoggedUserInfoType();
      	    	  
      	    	  	user.setTicket(ticket);
      	    	  
      	    	  	Collaborazioni collaborazioni = ticketSimogBean.getCollaborazioni();
      	    	  	if (collaborazioni != null && collaborazioni.getCollaborazioniArray() != null && collaborazioni.getCollaborazioniArray().length>0) {
      	    	  		user.setIndex(collaborazioni.getCollaborazioniArray(0).getIndex());
      	    	  	} else {
      	    	  		user.setIndex("0");
      	    	  	}
      	    	  	richiesta.setUser(user);
      	    	  
      	    	  	logger.info("Invocazione del metodo consultaComunicazione verso i Servizi SIMOG-smartCIG (Ticket="
      	  	            + ticket + ", CIG=" + codiceCIG + ")");
      	    	  	comunicazioneResponse = simogWS.consultaComunicazione(richiesta);
            		
      	    	  	logger.info("Invocato il metodo consultaComunicazione verso i Servizi SIMOG-smartCIG (Ticket="
      	  	            + ticket + ", CIG=" + codiceCIG + ")");
      	    	  
      	    	  	if (comunicazioneResponse.getComunicazione() == null) {
      	    	    	RisultatoType risultato = comunicazioneResponse.getCodiceRisultato();
      	    	    	resultXML.setSuccess(false);
      	    	    	resultXML.setGaraXML(null);
      	    	  		resultXML.setError(risultato.getDescrizione().getValue());
      	    	    	logger.error("La risposta del metodo consultaComunicazione verso i Servizi SIMOG-smartCIG (Ticket="
      	                        + ticket + ", smartCIG=" + codiceCIG +
      	                        ") non ha restituito alcun informazione significativa");
      	    	  	} else {
      	    	  		resultXML.setSuccess(true);
      	    	  		resultXML.setError(null);
      	    	  		ByteArrayOutputStream out = new ByteArrayOutputStream();
      	    	  	    XMLEncoder encoder = new XMLEncoder(out);
      	    	  	    // Handling BigDecimal
      	    	  	    encoder.setPersistenceDelegate(java.math.BigDecimal.class, encoder.getPersistenceDelegate(Double.class));
      	    	  	    encoder.writeObject(comunicazioneResponse.getComunicazione());
      	    	  	    encoder.close();
      	    	  	    resultXML.setGaraXML(out.toString());
      	    	  	}
            	} else {
            		if (StringUtils.isEmpty(codiceCIG)) {
                        ConsultaNumeroGaraDocument consultaNumeroGaraDoc = ConsultaNumeroGaraDocument.Factory.newInstance();
                        ConsultaNumeroGara consultaNumeroGara = ConsultaNumeroGara.Factory.newInstance();
                        consultaNumeroGara.setIdGara(idGara);
                        consultaNumeroGara.setSchede(CostantiSimog.CONSULTA_GARA_SCHEDE);
                        consultaNumeroGara.setTicket(ticket);
                        
                        consultaNumeroGaraDoc.setConsultaNumeroGara(consultaNumeroGara);
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
                        logger.info("Invocazione metodo consultaNumeroGara verso i Servizi SIMOG (Ticket="
                            + ticket + ", IdGara=" + idGara + ")");
                        ConsultaNumeroGaraResponseDocument consultaNumeroGaraResponseDoc =
                            simogWsPddServiceStub.consultaNumeroGara(consultaNumeroGaraDoc);
                        logger.info("Invocato metodo consultaNumeroGara verso i Servizi SIMOG (Ticket="
                            + ticket + ", IdGara=" + idGara + ")");
                        
                        if (consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().isSetReturn()) {
                          if (consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getSuccess()) {
                            if (consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getSchedaGaraCig() != null) {
                              
                              resultXML.setSuccess(Boolean.TRUE);
                              resultXML.setError(null);
                              resultXML.setGaraXML(consultaNumeroGaraResponseDoc.xmlText());
          
          							    } else {
                              // La risposta del metodo consultaNumeroGara non ha valorizzatto l'oggetto SchedaGaraCig.
                              logger.error("Errore nella risposta al metodo consultaNumeroGara di SIMOG, perche' priva "
                                  + "dell'oggetto che dovrebbe contenere l'array dei CIG della gara con IDGARA="
                                  + idGara);
                              resultXML.setGaraXML(null);
                              resultXML.setSuccess(Boolean.FALSE);
                              resultXML.setError("");
                            }
                          } else {
                            // Il metodo consultaNumeroGara non e' terminato con successo.
                            String msg = "Il metodo consultaNumeroGara di SIMOG e' fallito alla richiesta dei CIG della "
                              + "gara con IDGARA=" + idGara;
                            if (consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getError() != null) {
                              msg = msg.concat(". Dettaglio dell'errore: "
                                  + consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getError());
                            }
                            logger.error(msg);
                            resultXML.setGaraXML(null);
                            resultXML.setSuccess(Boolean.FALSE);
                            if (consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getError() != null) {
                              resultXML.setError(consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getError());
                            } else {
                              resultXML.setError("Errore per inconsistenza dei dati ricevuti da SIMOG");
                            }
                          }
                        } else {
                          // La risposta del metodo consultaNumeroGara non ha valorizzatto l'oggetto Return.
                          logger.error("Errore nella risposta al metodo consultaNumeroGara di SIMOG, perche' priva "
                              + "dell'oggetto che dovrebbe rappresentare la risposta della chiamata relativa alla "
                              + "gara con IDGARA=" + idGara);
                          resultXML.setGaraXML(null);
                          resultXML.setSuccess(Boolean.FALSE);
                          resultXML.setError("Errore per inconsistenza dei dati ricevuti da SIMOG");
                        }
                      } else {
                        ConsultaGaraDocument consultaGaraDoc = ConsultaGaraDocument.Factory.newInstance();
                        ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
                        consultaGara.setTicket(ticket);
                        consultaGara.setSchede(CostantiSimog.CONSULTA_GARA_SCHEDE);
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

                        responseConsultaGara = consultaGaraResponseDoc.getConsultaGaraResponse().getReturn();
                        if (responseConsultaGara != null) {
                          if (responseConsultaGara.getSuccess()) {
                            if (eseguiControlliPreliminari) {
                              if (this.controlliPreliminariDati_ViaPDD(responseConsultaGara, codiceCIG, idGara, cfrup, cfsa, resultXML)) {
                                if (StringUtils.isNotEmpty(responseConsultaGara.getError())) {
                                  resultXML.setError(responseConsultaGara.getError());
                                } else {
                                  resultXML.setError(null);
                                }
                                resultXML.setSuccess(true);
                                if (responseConsultaGara.getGaraXML() != null) {
                                  resultXML.setGaraXML(consultaGaraResponseDoc.xmlText());
                                } else {
                                  resultXML.setGaraXML(null);
                                }
                              }
                            } else {
                              if (responseConsultaGara.getError() != null) {
                                resultXML.setError(responseConsultaGara.getError());
                              } else {
                                resultXML.setError(null);
                              }
                              resultXML.setSuccess(true);
                              if (responseConsultaGara.getGaraXML() != null) {
                                resultXML.setGaraXML(consultaGaraResponseDoc.xmlText());
                              } else {
                                resultXML.setGaraXML(null);
                              }
                            }
                          } else {
                            if (StringUtils.isNotEmpty(responseConsultaGara.getError())) {
                              logger.error("La richiesta consultaGara con codice CIG pari a '" + codiceCIG
                                  + "' e' terminata con esito negativo, fornendo il seguente messaggio: "
                                  + responseConsultaGara.getError());
                              resultXML.setError(responseConsultaGara.getError());
                            } else {
                              logger.error("La richiesta consultaGara con codice CIG pari a '" + codiceCIG
                                  + "' e' terminata con esito negativo, ma non e' stato fornito un messaggio di errore");
                              resultXML.setError("Errore non indicato dai servizi SIMOG");
                            }
                            resultXML.setSuccess(false);
                            resultXML.setGaraXML(null);
                          }
                        } else {
                          logger.error("La richiesta consultaGara con codice CIG pari a '"
                              + codiceCIG + "' terminata con esito negativo a causa dell'oggetto "
                              + "it.avlp.simog.pdd.massload.xmlbeans.ResponseConsultaGara non valorizzato.");
                          
                          resultXML.setError("Errore non previsto durante l'operazione accesso ai dati dei servizi SIMOG");
                          resultXML.setSuccess(false);
                          resultXML.setGaraXML(null);
                        }
                      }
            	}

            } catch (RemoteException re) {
              logger.error("Errore nell'interazione con la porta di dominio per l'accesso ai "
                  + "servizi SIMOG per la chiamata consultaGara con codiceCIG='" + codiceCIG + "'", re);
            } catch (Throwable t) {
              logger.error("Errore inaspettato. Contattare un amministratore.", t);
            } finally {
              if (!resultXML.isSuccess() && StringUtils.isEmpty(resultXML.getError())) {
                resultXML.setError("Il servizio SIMOG e' momentaneamente non disponibile o non risponde correttamente. " +
                		"Riprovare piu' tardi. Se il problema persiste, segnalarlo all'amministratore di sistema.");
              }
              // Alla fine dell'interazione con i servizi Simog si rilascia il ticket usato
              // (all'oggetto TicketSimogManager si segnala semplicemente che si e' terminato
              // di usare il ticket)
              if (simogUser != null) {
            	  this.accessoSimogManager.simogLogout(ticket);
              } else {
            	  this.ticketSimogManager.rilasciaTicket();
              }
            }
          } else {
            // il ticketSimogManager non e' riuscito a fornire un ticket... 
            this.ticketSimogManager.resetTicket();
            
            if (StringUtils.isNotEmpty(ticketSimogBean.getMsgError())) {
              resultXML.setError(ticketSimogBean.getMsgError());
            } else {
              resultXML.setError("Errore non indicato nell'operazione di login ai servizi SIMOG");
            }

            resultXML.setSuccess(false);
            resultXML.setGaraXML(null);
          }
        
        } else {
          // il ticketSimogManager non e' riuscito a fornire un ticket,
          // probabilmente perché e' rimasta una sessione aperta
          this.ticketSimogManager.resetTicket();
          
          resultXML.setError("Errore nell'operazione di login ai servizi SIMOG");
          resultXML.setSuccess(false);
          resultXML.setGaraXML(null);
        }
        
      } else {
        // Errore nella configurazione URL
        String message = "Errore durante la connessione ai servizi SIMOG: i parametri di connessione "
            + "non sono specificati correttamente. Contattare un amministratore.";
        logger.error("Il tentativo di connessione ha generato il seguente errore: " + message);
  
        resultXML.setError(message);
        resultXML.setSuccess(false);
        resultXML.setGaraXML(null);
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraViaPDD: fine metodo");
    }
  }


  
  
  /**
   * Controlli preliminari della gara e dei lotti ricevuti da SIMOG con accesso via PDD.
   * 
   * @param responseConsultaGara
   * @param idAvGara
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @return Ritorna true se i controlli sono andati a buon fine, false altrimenti
   */
  private boolean controlliPreliminariDati_ViaPDD (
      final ResponseConsultaGara responseConsultaGara,
      final String codiceCIG, final String idAvGara, final String cfrup, final String cfsa,
      it.toscana.rete.rfc.sitatort.ResponseConsultaGara resultXML) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("controlliPreliminariDati_ViaPDD: inizio metodo");
    }

    boolean result = true;
    StringBuffer msgErroreGara = new StringBuffer("");
   
    SchedaType schedaType = responseConsultaGara.getGaraXML();
    //GaraType garaType = schedaType.getDatiGara().getGara();
    
    /*String garaTypeCfUtente = garaType.getCFUTENTE();
    String datiComuniCfRup = null;
    String codiceFiscaleRUP = null;
    if (schedaType.isSetDatiScheda() &&
        StringUtils.isNotEmpty(schedaType.getDatiScheda().getDatiComuni().getCFRUP())) {
      datiComuniCfRup = schedaType.getDatiScheda().getDatiComuni().getCFRUP();
    }
    
    if (StringUtils.isNotEmpty(datiComuniCfRup)) {
      codiceFiscaleRUP = new String(datiComuniCfRup);
    } else {
      codiceFiscaleRUP = new String(garaTypeCfUtente);
    }*/
    
    //String abilitaControlloCFSA = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CONSULTA_GARA_CONTROLLO_CFSA);
    //String abilitaControlloRup = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CONSULTA_GARA_CONTROLLO_RUP);
    
    /*if (((abilitaControlloCFSA == null || abilitaControlloCFSA.equals("1")) && !garaType.getCFAMMINISTRAZIONE().equalsIgnoreCase(cfsa)) &&
    		((abilitaControlloRup == null || abilitaControlloRup.equals("1")) && !codiceFiscaleRUP.equalsIgnoreCase(cfrup))) {
      msgErroreGara.append("stazione appaltante e RUP della gara diversi da quelli in uso");
    } else {
      if ((abilitaControlloCFSA == null || abilitaControlloCFSA.equals("1")) && !garaType.getCFAMMINISTRAZIONE().equalsIgnoreCase(cfsa)) {
      	msgErroreGara.append("stazione appaltante della gara diversa da quella in uso");
      } else if ((abilitaControlloRup == null || abilitaControlloRup.equals("1")) &&
  	    !codiceFiscaleRUP.equalsIgnoreCase(cfrup)) {
        if (msgErroreGara.length() > 0) {
          msgErroreGara.append(", ");
        }
        msgErroreGara.append("RUP della gara diverso da quello in uso");
      }
    }*/
    
    if (msgErroreGara.length() == 0) {
      if (schedaType.getDatiGara().getGara().isSetDATACANCELLAZIONEGARA()) {
        msgErroreGara.append("Gara cancellata");
      } else if (!schedaType.getDatiGara().isSetLotto()) {
        /*LottoType lottoType = schedaType.getDatiGara().getLotto();
  
        if (lottoType.isSetDATACANCELLAZIONELOTTO()) {
          if (msgErroreGara.length() > 0) {
            msgErroreGara.append(" e ");
          }
          msgErroreGara.append(" lotto cancellato");
        } else if (! lottoType.isSetDATAPUBBLICAZIONE()) {
          if (msgErroreGara.length() > 0) {
            msgErroreGara.append(" e ");
          }
          msgErroreGara.append(" lotto non perfezionato");
        }
      } else {*/
        if (msgErroreGara.length() > 0) {
          msgErroreGara.append(" e lotto non valorizzato");
        } else {
          msgErroreGara.append("Lotto non valorizzato");
        }
      }
    }
    
    if (msgErroreGara.length() > 0) {
      StringBuffer msgErrore = new StringBuffer(
          "Non sono stati superati i controlli preliminari sui dati ricevuti da SIMOG: ");
  
      if (StringUtils.isNotEmpty(msgErroreGara.toString())) {
        msgErrore.append(msgErroreGara);
      } 
  
      resultXML.setSuccess(Boolean.FALSE);
      resultXML.setError(msgErrore.toString());

      logger.error(msgErrore.toString());
      result = false;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("controlliPreliminariDati_ViaPDD: fine metodo");
    }
    
    return result;
  }
  
}