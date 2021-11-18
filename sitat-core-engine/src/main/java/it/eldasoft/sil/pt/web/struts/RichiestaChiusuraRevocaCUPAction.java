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
public class RichiestaChiusuraRevocaCUPAction extends ActionBaseNoOpzioni {

	/** FORWARD_SUCCESS forward generazione cup avvenuta con successo. */
  protected static final String          FORWARD_SUCCESS   = "chiusurarevocacupsuccess";
  /** FORWARD_ERROR forward per generazione cup ha generato un errore. */
  protected static final String          FORWARD_ERROR     = "chiusurarevocacuperror";
  /** Logger Log4J di classe. */
  private static Logger                          logger            = Logger.getLogger(RichiestaChiusuraRevocaCUPAction.class);

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
      logger.debug("RichiestaChiusuraRevocaCUPAction: inizio metodo");
    }

    String target = FORWARD_SUCCESS;
    String messageKey = null;

    try {
      //Long id = new Long(request.getParameter("id"));
      String recuperaUser = request.getParameter("recuperauser");
      String recuperaPassword = request.getParameter("recuperapassword");
      String memorizza = request.getParameter("memorizza");
      HashMap<String, String> hMapParametri = new HashMap<String, String>();
      hMapParametri.put("tipoOperazione", request.getParameter("tipoOperazione"));
      hMapParametri.put("codiceCUP", request.getParameter("codiceCUP"));
      hMapParametri.put("id", request.getParameter("id"));
  
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
        this.gestioneServiziCUPManager.gestioneCUPWSUserPass(syscon, cupwsuser,
            cupwspass, false);
      } else {
        this.gestioneServiziCUPManager.gestioneCUPWSUserPass(syscon, cupwsuser,
            cupwspass, true);
      }

      boolean result = this.gestioneServiziCUPManager.richiestaChiusuraRevocaCUP(cupwsuser, cupwspass, syscon, hMapParametri);
      if (result) {
    	  target = FORWARD_SUCCESS;
      } else {
    	  target = FORWARD_ERROR;
      }
      //request.getSession().setAttribute("id", id);
      request.getSession().setAttribute("numeroPopUp", "1");
      //request.getSession().setAttribute("cup", cup);
    } catch (Exception e) {
      target = FORWARD_ERROR;
      messageKey = "errors.richiestaCUP.error";
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
    	logger.debug("RichiestaChiusuraRevocaCUPAction: fine metodo");
    }

    return mapping.findForward(target);

  }

}
