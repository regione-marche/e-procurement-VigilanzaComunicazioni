/*
 * Created on 15/nov/2010
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.web.struts;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.sil.pt.bl.GestioneServiziCUPManager;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luca.Giacomazzo - Eldasoft S.p.A. Treviso
 *
 */
public class RichiestaListaCUPAction extends ActionBaseNoOpzioni {

	/** FORWARD_SUCCESS forward richiesta lista cup avvenuta con successo. */
  protected static final String          FORWARD_SUCCESS   = "listacupsuccess";
  /** FORWARD_ERROR forward per richiesta lista cup ha generato un errore. */
  protected static final String          FORWARD_ERROR     = "listacuperror";
  /** Logger Log4J di classe. */
  private static Logger                          logger            = Logger.getLogger(RichiestaListaCUPAction.class);
  /** manager per operazioni di interrogazione del db. */
  private GestioneServiziCUPManager      gestioneServiziCUPManager;

  /**
   * @param gestioneServiziCUPManager
   *        manager da settare internamente alla classe.
   */
  public void setGestioneServiziCUPManager(
      GestioneServiziCUPManager gestioneServiziCUPManager) {
    this.gestioneServiziCUPManager = gestioneServiziCUPManager;
  }

  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
      logger.debug("RichiestaListaCUPAction: inizio metodo");
    }

    String target = FORWARD_SUCCESS;
    String messageKey = null;
    
    try {    
    	String recuperaUser = request.getParameter("recuperauser");
      String recuperaPassword = request.getParameter("recuperapassword");
      String memorizza = request.getParameter("memorizza");

      
      ProfiloUtente profilo = (ProfiloUtente) request.getSession().getAttribute(
          CostantiGenerali.PROFILO_UTENTE_SESSIONE);
      Long syscon = new Long(profilo.getId());
  
      String cupwsuser = null;
      String cupwspass = null;
      // Leggo le eventuali credenziali memorizzate
      HashMap<String, String> hMapCUPWSUserPass = new HashMap<String, String>();
      hMapCUPWSUserPass = this.gestioneServiziCUPManager.recuperaCUPWSUserPass(syscon);

      // Gestione USER
      if (recuperaUser != null && "1".equals(recuperaUser)) {
        cupwsuser = ((String) hMapCUPWSUserPass.get("cupwsuser"));
      } else {
        cupwsuser = request.getParameter("cupwsuser");
      }

      // Gestione PASSWORD
      if (recuperaPassword != null && "1".equals(recuperaPassword)) {
        cupwspass = ((String) hMapCUPWSUserPass.get("cupwspass"));
      } else {
        cupwspass = request.getParameter("cupwspass");
      }

      // Gestione memorizzazione delle credenziali o eventuale cancellazione
      // (se richiesta)
      if (memorizza == null) {
        this.gestioneServiziCUPManager.gestioneCUPWSUserPass(syscon, cupwsuser, cupwspass, false);
      } else {
        this.gestioneServiziCUPManager.gestioneCUPWSUserPass(syscon, cupwsuser, cupwspass, true);
      }

      // Parametri di ricerca
      HashMap<String, String> hMapParametri = new HashMap<String, String>();
      hMapParametri.put("annoDecisione", request.getParameter("annoDecisione"));
      hMapParametri.put("codiceCUP", request.getParameter("codiceCUP"));
      hMapParametri.put("dataGenerazioneDa",
          request.getParameter("dataGenerazioneDa"));
      hMapParametri.put("dataGenerazioneA",
          request.getParameter("dataGenerazioneA"));
      hMapParametri.put("natura", request.getParameter("natura"));
      hMapParametri.put("tipologia", request.getParameter("tipologia"));
      hMapParametri.put("descrizione", request.getParameter("descrizione"));

      // Nome del campo CUP della finestra chiamante
      String campoCUP = request.getParameter("campoCUP");

      List<Vector< ? >> risultatoListaCUP = this.gestioneServiziCUPManager.richiestaListaCUP(
          cupwsuser, cupwspass, syscon, hMapParametri);
      target = FORWARD_SUCCESS;
      request.setAttribute("campoCUP", campoCUP);
      request.setAttribute("risultatoListaCUP", risultatoListaCUP);

            
    } catch (Exception e) {
      target = FORWARD_ERROR;
      messageKey = "errors.richiestaListaCUP.error";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey, e.getMessage());
    } catch (Throwable e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey);
    }

    if (messageKey != null) {
    	response.reset();
    }

    if (logger.isDebugEnabled()) {
    	logger.debug("RichiestaListaCUPAction: fine metodo");
    }

    return mapping.findForward(target);

  }

}
