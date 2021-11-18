package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Mirco.Franzoni - Eldasoft S.p.A. Treviso
 *
 */

public class CambiaRUPAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
  private static Logger     logger = Logger.getLogger(CambiaRUPAction.class);

  /** ptManager manager della classe. */
  private PtManager ptManager;

  /**
   * @param manager
   *        manager da settare internamente alla classe.
   */
  public void setPtManager(PtManager manager) {
    ptManager = manager;
  }

  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
	String target = CostantiGeneraliStruts.FORWARD_OK;
    // Parametri di ricerca
    String interventi = request.getParameter("interventi");
    String newRup = request.getParameter("newRup"); 
    
    try {
      this.ptManager.cambiaRUP(interventi, newRup);
    } catch (GestoreException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("errore nell'aggiornamento del rup negli interventi selezionati", e);
      aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", e.getMessage());
    }
    if (target != null) {
    	return mapping.findForward(target);
    } else {
    	return null;
    }
  }

}
