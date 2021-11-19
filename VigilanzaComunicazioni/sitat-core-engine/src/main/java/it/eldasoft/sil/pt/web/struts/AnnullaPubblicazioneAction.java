package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.ListaAction;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.spring.UtilitySpring;

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

public class AnnullaPubblicazioneAction extends ListaAction {

	/** Logger Log4J di classe. */
  private static Logger     logger = Logger.getLogger(AnnullaPubblicazioneAction.class);

  public ActionForward annullaPubblicazione(ActionMapping mapping, ActionForm form,
	      HttpServletRequest request, HttpServletResponse response)
	      throws IOException, ServletException {
    String target = CostantiGeneraliStruts.FORWARD_OK;
    try {
    	   PtManager ptManager = (PtManager) UtilitySpring.getBean(
    		        "ptManager", request.getSession().getServletContext(), PtManager.class);
    	String contri = request.getParameter("contri");
      ptManager.annullaPubblicazione(new Long(contri), request);
    } catch (GestoreException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("errore nell'annullamento della pubblicazione del programma", e);
      aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", e.getMessage());
    } catch (Exception ex) {
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        logger.error("errore nell'annullamento della pubblicazione del programma", ex);
        aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", ex.getMessage());
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
    
    //return super.leggi(mapping, form, request, response);
    return vaiA;
    //return super.apri(mapping, form, request, response);
    //return mapping.findForward(target);
  }

}
