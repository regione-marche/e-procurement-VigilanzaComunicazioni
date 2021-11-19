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

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.sil.pt.bl.ControlliValidazioneCUPManager;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luca.Giacomazzo - Eldasoft S.p.A. Treviso
 *
 */

public class AvviaRichiestaCUPAction extends ActionBaseNoOpzioni {

	/** FORWARD_CONTROLLO forward genera cup controllo. */
  protected static final String          FORWARD_CONTROLLO = "generacupcontrollo";
  /** FORWARD_RICHIESTA forward genera cup richiesta. */
  protected static final String          FORWARD_RICHIESTA = "generacuprichiesta";

  /** Logger Log4J di classe. */
  private static Logger                          logger            = Logger.getLogger(AvviaRichiestaCUPAction.class);

  /** Manager per i controlli di validazione del cup. */
  private ControlliValidazioneCUPManager controlliValidazioneCUPManager;

  /**
   * @param controlliValidazioneCUPManager
   *        manager da settare internamente alla classe.
   */
  public void setControlliValidazioneCUPManager(
      ControlliValidazioneCUPManager controlliValidazioneCUPManager) {
    this.controlliValidazioneCUPManager = controlliValidazioneCUPManager;
  }

  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
    	logger.debug("AvviaRichiestaCUPAction: inizio metodo");
    }

    String target = FORWARD_RICHIESTA;
    String messageKey = null;

    Long id = new Long(request.getParameter("id"));

    int numeroErrori = 0;

    try {

      HashMap<String, Object> infoValidazione = this.controlliValidazioneCUPManager.validate(id);

      if (infoValidazione.get("numeroErrori") != null) {
        numeroErrori = ((Long) infoValidazione.get("numeroErrori")).intValue();
      }

      if (numeroErrori > 0) {
        target = FORWARD_CONTROLLO;
        request.getSession().setAttribute("id", id);
        request.getSession().setAttribute("numeroPopUp", "1");
      } else {
        request.getSession().setAttribute("id", id);
        request.getSession().setAttribute("numeroPopUp", "1");
      }
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
    	logger.debug("AvviaRichiestaCUPAction: fine metodo");
      
      }

    return mapping.findForward(target);

  }

}
