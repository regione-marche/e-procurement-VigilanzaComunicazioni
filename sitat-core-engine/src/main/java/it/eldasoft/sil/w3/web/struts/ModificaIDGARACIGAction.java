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
package it.eldasoft.sil.w3.web.struts;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.w3.bl.GestioneServiziIDGARACIGManager;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ModificaIDGARACIGAction extends ActionBaseNoOpzioni {

  protected static final String           FORWARD_SUCCESS = "modificaidgaracigsuccess";
  protected static final String           FORWARD_ERROR   = "modificaidgaracigerror";

  static Logger                           logger          = Logger.getLogger(ModificaIDGARACIGAction.class);

  private GestioneServiziIDGARACIGManager gestioneServiziIDGARACIGManager;

  public void setGestioneServiziIDGARACIGManager(
      GestioneServiziIDGARACIGManager gestioneServiziIDGARACIGManager) {
    this.gestioneServiziIDGARACIGManager = gestioneServiziIDGARACIGManager;
  }

  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    if (logger.isDebugEnabled())
      logger.debug("ModificaIDGARACIGAction: inizio metodo");

    String target = FORWARD_SUCCESS;
    String messageKey = null;

    try {
      String entita = request.getParameter("entita");
      Long numgara = new Long(request.getParameter("numgara"));
      Long numlott = new Long(request.getParameter("numlott"));
      String recuperaUser = request.getParameter("recuperauser");
      String recuperaPassword = request.getParameter("recuperapassword");
      String memorizza = request.getParameter("memorizza");

      ProfiloUtente profilo = (ProfiloUtente) request.getSession().getAttribute(
          CostantiGenerali.PROFILO_UTENTE_SESSIONE);
      Long syscon = new Long(profilo.getId());

      String codrup = request.getParameter("codrup");
      String simogwsuser = null;
      String simogwspass = null;

      // Leggo le eventuali credenziali memorizzate
      HashMap<String, String> hMapSIMOGWSUserPass = new HashMap<String, String>();
      hMapSIMOGWSUserPass = this.gestioneServiziIDGARACIGManager.recuperaSIMOGWSUserPass(syscon, codrup);

      // Gestione USER
      if (recuperaUser != null && "1".equals(recuperaUser)) {
        simogwsuser = ((String) hMapSIMOGWSUserPass.get("simogwsuser"));
      } else {
        simogwsuser = request.getParameter("simogwsuser");
      }

      // Gestione PASSWORD
      if (recuperaPassword != null && "1".equals(recuperaPassword)) {
        simogwspass = ((String) hMapSIMOGWSUserPass.get("simogwspass"));
      } else {
        simogwspass = request.getParameter("simogwspass");
      }

      // Gestione memorizzazione delle credenziali o eventuale cancellazione
      // (se richiesta)
      if (memorizza == null) {
        //this.gestioneServiziIDGARACIGManager.cancellaSIMOGWSUserPass(syscon, codrup);
      } else {
        this.gestioneServiziIDGARACIGManager.memorizzaSIMOGWSUserPass(syscon, codrup,
            simogwsuser, simogwspass);
      }

      // Invio al web service
      if ("W3GARA".equals(entita)) {
        this.gestioneServiziIDGARACIGManager.modificaGARA(simogwsuser,
            simogwspass, numgara);
      } else if ("W3LOTT".equals(entita)) {
        this.gestioneServiziIDGARACIGManager.modificaLOTTO(simogwsuser,
            simogwspass, numgara, numlott);
      }

      target = FORWARD_SUCCESS;
      request.getSession().setAttribute("numgara", numgara);
      request.getSession().setAttribute("numeroPopUp", "1");
    } catch (GestoreException e) {
      target = FORWARD_ERROR;
      messageKey = "errors.gestioneIDGARACIG.error";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey, e.getMessage());
    } catch (Throwable e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey);
    }

    if (messageKey != null) response.reset();

    if (logger.isDebugEnabled())
      logger.debug("ModificaIDGARACIGAction: fine metodo");

    return mapping.findForward(target);

  }

}
