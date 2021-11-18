package it.eldasoft.w9.web.struts;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.avlp.simog.massload.xmlbeans.ChiudiSessione;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneResponseDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaGara;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.Login;
import it.avlp.simog.massload.xmlbeans.LoginDocument;
import it.avlp.simog.massload.xmlbeans.LoginResponseDocument;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoSessione;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.bl.simog.TicketSimogBean;
import it.eldasoft.w9.bl.simog.TicketSimogManager;
import it.eldasoft.w9.common.CostantiSimog;
import it.toscana.rete.rfc.sitatort.ResponseConsultaGara;

/**
 * Action per l'estrazione in formato XML dai servizi SIMOG dei dati relativi al CIG,
 * attraverso il metodo consultaGara
 *  
 * @author luca.giacomazzo
 */
public class GetCigFromSimogAction extends DispatchActionBaseNoSessione {

  static Logger logger = Logger.getLogger(GetCigFromSimogAction.class);
  
  private TicketSimogManager ticketSimogManager;

  /**
   * @param ticketSimogManager the ticketSimogManager to set
   */
  public void setTicketSimogManager(TicketSimogManager ticketSimogManager) {
    this.ticketSimogManager = ticketSimogManager;
  }
  
  public ActionForward getCigOsservatorio(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("getCigOsservatorio: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK;

    // Inizializzazione dell'esito della risposta
    ResponseConsultaGara resultXML = new ResponseConsultaGara();  
    resultXML.setSuccess(false);
    
    List<String> listaMessaggi = new ArrayList<String>(); 
    
    String codiceCIG = request.getParameter("cig");
    
    SimogWSPDDServiceStub simogWsPddServiceStub = null;

    boolean continua = true;

    if (continua) {
      try {
        String urlServiziSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
        if (StringUtils.isNotEmpty(urlServiziSimog)) {
          TicketSimogBean ticketSimogBean = this.ticketSimogManager.getTicket();
        
          if (ticketSimogBean != null) {
            if (ticketSimogBean.isSuccess()) {
              String ticket = ticketSimogBean.getTicketSimog();
            
              ConsultaGaraDocument consultaGaraDocument = ConsultaGaraDocument.Factory.newInstance();
              ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
              consultaGara.setSchede(CostantiSimog.CONSULTA_GARA_SCHEDE);
              consultaGara.setTicket(ticket);
              consultaGara.setCIG(codiceCIG);
              consultaGaraDocument.setConsultaGara(consultaGara);
              
              simogWsPddServiceStub = new SimogWSPDDServiceStub(urlServiziSimog);
              
              ConsultaGaraResponseDocument responseConsultaGara = simogWsPddServiceStub.consultaGara(consultaGaraDocument);
              if (responseConsultaGara.getConsultaGaraResponse() != null && 
                  responseConsultaGara.getConsultaGaraResponse().getReturn() != null &&
                  responseConsultaGara.getConsultaGaraResponse().getReturn().getSuccess()) {
                SchedaType scheda = responseConsultaGara.getConsultaGaraResponse().getReturn().getGaraXML();
    
                if (scheda != null) {
                  resultXML.setGaraXML(scheda.toString());
                  resultXML.setSuccess(true);
                }
              } else {
                listaMessaggi.add("Problemi nella risposta alla chiamata del metodo consultaGara:");
                if (responseConsultaGara.getConsultaGaraResponse() == null) {
                  listaMessaggi.add("l'oggetto di risposta e' null");
                } else if (responseConsultaGara.getConsultaGaraResponse().getReturn() == null) {
                  listaMessaggi.add("l'oggetto di risposta e' privo di risultato");
                } else if (! responseConsultaGara.getConsultaGaraResponse().getReturn().getSuccess()) {
                  listaMessaggi.add("il metodo e' terminato con insuccesso");
                  if (StringUtils.isNotEmpty(responseConsultaGara.getConsultaGaraResponse().getReturn().getError())) {
                    listaMessaggi.add("con il seguente errore: ".concat(
                        responseConsultaGara.getConsultaGaraResponse().getReturn().getError()));  
                  }
                }
              }
  
              // Si rilascia il ticket usato
              this.ticketSimogManager.rilasciaTicket();
            } else {
              // Operazione di login fallita
              listaMessaggi.add("Operazioni di login fallita. Codice di errore: " + ticketSimogBean.getMsgError());
            }
          } else {
            // l'oggetto ticketSimogBean non e' valorizzato
            listaMessaggi.add("Operazioni di login fallita a causa di un errore nell'interazione con i servizi SIMOG");
          }
        } else {
          listaMessaggi.add("Impossibile continuare. Valorizzare la property ".concat(CostantiSimog.PROP_SIMOG_WS_URL).concat(" con l'URL dei servizi SIMOG"));
        }
      } catch (RemoteException re) {
        logger.error("Errore remoto nell'interazione con i servizi SIMOG", re);
        listaMessaggi.add("Errore remoto nell'interazione con i servizi SIMOG. Vedere i log per dettagli");
      }
    }
    if (logger.isDebugEnabled()) {
      logger.debug("getCigOsservatorio: fine metodo");
    }
    
    if (listaMessaggi.size() > 0) {
      request.setAttribute("listaMessaggi", listaMessaggi); 
    }
    request.setAttribute("resultXML", resultXML);
    
    return mapping.findForward(target);
  }

  
  public ActionForward getCigVigilanza(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("getCigVigilanza: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK;

    // Inizializzazione dell'esito della risposta
    ResponseConsultaGara resultXML = new ResponseConsultaGara();  
    resultXML.setSuccess(false);
    
    List<String> listaMessaggi = new ArrayList<String>(); 
    
    String codiceCIG = request.getParameter("cig");
    String cfSimog = request.getParameter("cfSimog");
    String passwordSimog = request.getParameter("passwordSimog");
    
    SimogWSPDDServiceStub simogWsPddServiceStub = null;
    String ticket = null;
    
    try {
      String urlServiziSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
      if (StringUtils.isNotEmpty(urlServiziSimog)) {
        
        simogWsPddServiceStub = new SimogWSPDDServiceStub(urlServiziSimog);
        
        this.ticketSimogManager.setProxy(simogWsPddServiceStub);
        
        LoginDocument loginDoc = LoginDocument.Factory.newInstance();
        Login loginIn = Login.Factory.newInstance();
        loginIn.setLogin(cfSimog);
        loginIn.setPassword(passwordSimog);
        loginDoc.setLogin(loginIn);

        LoginResponseDocument loginResponseDoc = simogWsPddServiceStub.login(loginDoc);
        if (loginResponseDoc.getLoginResponse() != null &&
            loginResponseDoc.getLoginResponse().isSetReturn() &&
            loginResponseDoc.getLoginResponse().getReturn() != null &&
            loginResponseDoc.getLoginResponse().getReturn().getSuccess()) {
          
          ticket = loginResponseDoc.getLoginResponse().getReturn().getTicket();
          
          ConsultaGaraDocument consultaGaraDocument = ConsultaGaraDocument.Factory.newInstance();
          ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
          consultaGara.setSchede(CostantiSimog.CONSULTA_GARA_SCHEDE);
          consultaGara.setTicket(ticket);
          consultaGara.setCIG(codiceCIG);
          consultaGaraDocument.setConsultaGara(consultaGara);
          
          ConsultaGaraResponseDocument responseConsultaGara = simogWsPddServiceStub.consultaGara(consultaGaraDocument);
          if (responseConsultaGara.getConsultaGaraResponse() != null && 
              responseConsultaGara.getConsultaGaraResponse().getReturn() != null &&
              responseConsultaGara.getConsultaGaraResponse().getReturn().getSuccess()) {
            SchedaType scheda = responseConsultaGara.getConsultaGaraResponse().getReturn().getGaraXML();

            if (scheda != null) {
              resultXML.setGaraXML(scheda.toString());
              resultXML.setSuccess(true);
            }
          } else {
            listaMessaggi.add("Problemi nella risposta alla chiamata del metodo consultaGara:");
            if (responseConsultaGara.getConsultaGaraResponse() == null) {
              listaMessaggi.add("l'oggetto di risposta e' null");
            } else if (responseConsultaGara.getConsultaGaraResponse().getReturn() == null) {
              listaMessaggi.add("l'oggetto di risposta e' privo di risultato");
            } else if (! responseConsultaGara.getConsultaGaraResponse().getReturn().getSuccess()) {
              listaMessaggi.add("il metodo e' terminato con insuccesso");
            } else if (StringUtils.isNotEmpty(responseConsultaGara.getConsultaGaraResponse().getReturn().getError())) {
                listaMessaggi.add("con il seguente errore: ".concat(
                    responseConsultaGara.getConsultaGaraResponse().getReturn().getError()));  
            }
          }
        } else {
          // Operazione di login fallita
          listaMessaggi.add("Problemi nella risposta alla chiamata del metodo login:");
          if (loginResponseDoc.getLoginResponse() == null) {
            listaMessaggi.add("l'oggetto di risposta e' null");
          } else if (!loginResponseDoc.getLoginResponse().isSetReturn()) {
            listaMessaggi.add("il metodo e' terminato con insuccesso");
          } else if (loginResponseDoc.getLoginResponse().getReturn() == null) {
            listaMessaggi.add("l'oggetto di risposta e' privo di risultato");
          } else if (!loginResponseDoc.getLoginResponse().getReturn().getSuccess()) {
            listaMessaggi.add("Operazioni di login fallita. ");
            if (StringUtils.isNotEmpty(loginResponseDoc.getLoginResponse().getReturn().getError())) {
              listaMessaggi.add("Errore: ".concat(
                  loginResponseDoc.getLoginResponse().getReturn().getError()));
            }
          }
        }
      } else {
        listaMessaggi.add("Impossibile continuare. Valorizzare la property ".concat(CostantiSimog.PROP_SIMOG_WS_URL).concat(" con l'URL dei servizi SIMOG"));
      }
    } catch (RemoteException re) {
      logger.error("Errore remoto nell'interazione con i servizi SIMOG", re);
      listaMessaggi.add("Errore remoto nell'interazione con i servizi SIMOG. Vedere i log per dettagli");
    } finally {
      if (ticket != null && simogWsPddServiceStub != null) {
        try {
          ChiudiSessioneDocument chiudiSessioneDocument = ChiudiSessioneDocument.Factory.newInstance();
          ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticket);
          chiudiSessioneDocument.setChiudiSessione(chiudiSessione);
          ChiudiSessioneResponseDocument responseChidiSessione = 
              simogWsPddServiceStub.chiudiSessione(chiudiSessioneDocument);
          
          if (responseChidiSessione.getChiudiSessioneResponse() != null &&
              responseChidiSessione.getChiudiSessioneResponse().getReturn() != null &&
              responseChidiSessione.getChiudiSessioneResponse().isSetReturn() && 
              responseChidiSessione.getChiudiSessioneResponse().getReturn().getSuccess()) {
            
            if (StringUtils.isNotEmpty(responseChidiSessione.getChiudiSessioneResponse().getReturn().getMessaggio())) {
              listaMessaggi.add("Operazione di logout conclusa con successo: ".concat(
                  responseChidiSessione.getChiudiSessioneResponse().getReturn().getMessaggio()));
            }
          } else {
            // Operazione di logout fallita
            listaMessaggi.add("Problemi nella risposta alla chiamata del metodo chiudiSessione:");
            if (responseChidiSessione.getChiudiSessioneResponse() == null) {
              listaMessaggi.add("");
            } else if (!responseChidiSessione.getChiudiSessioneResponse().isSetReturn()) {
              listaMessaggi.add("");
            } else if (responseChidiSessione.getChiudiSessioneResponse().getReturn() == null) {
              listaMessaggi.add("");
            } else if (!responseChidiSessione.getChiudiSessioneResponse().getReturn().getSuccess()) {
              listaMessaggi.add("operazione fallita");
              if (StringUtils.isNotEmpty(responseChidiSessione.getChiudiSessioneResponse().getReturn().getError())) {
                listaMessaggi.add("Errore: ".concat(
                    responseChidiSessione.getChiudiSessioneResponse().getReturn().getError()));
              }
            }
          }
        } catch (RemoteException re) {
          logger.error("Errore remoto durante l'operazione di chiusura della sessione su SIMOG", re);
          resultXML.setError("Errore remoto durante l'operazione di chiusura della sessione su SIMOG");
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getCigVigilanza: fine metodo");
    }
    
    if (listaMessaggi.size() > 0) {
      request.setAttribute("listaMessaggi", listaMessaggi); 
    }
    request.setAttribute("resultXML", resultXML);
    
    return mapping.findForward(target);
  }
  
}
