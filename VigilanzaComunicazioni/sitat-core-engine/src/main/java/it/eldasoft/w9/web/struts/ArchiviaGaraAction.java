package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.ListaAction;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Mirco Franzoni - Eldasoft S.p.A. Treviso
 *
 */

public class ArchiviaGaraAction extends ListaAction {

	/** Logger Log4J di classe. */
  private static Logger     logger = Logger.getLogger(ArchiviaGaraAction.class);

  public ActionForward archiviaGara(ActionMapping mapping, ActionForm form,
	      HttpServletRequest request, HttpServletResponse response)
	      throws IOException, ServletException {
    String target = CostantiGeneraliStruts.FORWARD_OK;
    try {
    	   W9Manager w9Manager = (W9Manager) UtilitySpring.getBean(
    		        "w9Manager", request.getSession().getServletContext(), W9Manager.class);
    	Long codgara = new Long(request.getParameter("codgara"));
    	String archivia = request.getParameter("archivia");
    	w9Manager.archiviaGara(codgara, archivia.equals("true"));
    } catch (GestoreException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("errore durante l'archiviazione della gara", e);
      aggiungiMessaggio(request, "errors.gestoreException.*.archiviagara.error", e.getMessage());
    } catch (Exception ex) {
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        logger.error("errore durante l'archiviazione della gara", ex);
        aggiungiMessaggio(request, "errors.gestoreException.*.archiviagara.error", ex.getMessage());
      }
    ActionForward vaiA = mapping.findForward(target);
    String messageKey = null;


      try {
        vaiA = UtilityTags.getUtilityHistory(request.getSession()).back(request);
      } catch (JspException e) {
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        vaiA = mapping.findForward(target);
        messageKey = "errors.applicazione.inaspettataException";
        logger.error(this.resBundleGenerale.getString(messageKey), e);
        this.aggiungiMessaggio(request, messageKey);
      }
    return vaiA;
  }

}
