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
 * @author Cristian.Febas - Maggioli S.p.A. Treviso
 *
 */
public class ContrassegnaFabbisognoAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
	private static Logger               logger        = Logger.getLogger(ContrassegnaFabbisognoAction.class);

	  /** ptManager manager della classe. */
	  private PtManager ptManager;

	  /**
	   * @param manager
	   *        manager da settare internamente alla classe.
	   */
	  public void setPtManager(PtManager manager) {
	    ptManager = manager;
	  }

  /**
   * @see it.eldasoft.gene.commons.web.struts.ActionBase#runAction(org.apache.struts.action.ActionMapping,
   *      org.apache.struts.action.ActionForm,
   *      javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String target = CostantiGeneraliStruts.FORWARD_OK;

    String fabbisogni = request.getParameter("fabbisogni");
    String funzionalita = request.getParameter("funzionalita");
    String dataAvvioProcedura = request.getParameter("dataAvvioProcedura");
    try {
      this.ptManager.contrassegnaFabbisogno(fabbisogni,funzionalita,dataAvvioProcedura);
    } catch (GestoreException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("errore nell'aggiornamento dei fabbisogni", e);
      aggiungiMessaggio(request, "errors.fabbisogni.contrassegnaFabbisogni");
    }
    return mapping.findForward(target);
  }

}
