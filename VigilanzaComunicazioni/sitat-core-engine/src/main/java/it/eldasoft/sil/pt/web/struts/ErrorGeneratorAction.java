package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luca.Giacomazzo - Eldasoft S.p.A. Treviso
 *
 */

public class ErrorGeneratorAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
  private static Logger               logger            = Logger.getLogger(ErrorGeneratorAction.class);

  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    
	  logger.error("Errore nel recupero dei dati intervento/i da incollare.");
	      aggiungiMessaggio(request, "errors.incollaIntervento.recuperoDati");
	  return mapping.findForward(CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE);
  }

}
