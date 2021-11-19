package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;

import java.io.IOException;
import java.io.PrintWriter;

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

public class AggiornaSequenzaInterventiAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
  private static Logger     logger = Logger.getLogger(AggiornaImportiProgrammaAction.class);

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
	  response.setHeader("cache-control", "no-cache");
	  response.setContentType("text/text;charset=utf-8");
	  PrintWriter out = response.getWriter();
	  try {
		  String codice = request.getParameter("conint");
		  String nprogint = request.getParameter("nprogint").trim();
		  String action = request.getParameter("action");
		  this.ptManager.aggiornaSequenzaInterventi(codice,new Long(nprogint), action);
		  out.print("success");
	  } catch (GestoreException e) {
		  logger.error("errore nell'aggiornamento della sequenza degli interventi", e);
		  out.print("error");
	  }
	  out.flush();
	  return null;
  }

}
