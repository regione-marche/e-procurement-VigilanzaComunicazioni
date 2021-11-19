package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.db.datautils.DataColumnContainer;

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

public class VerificaAnnualitaRiferimentoAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
  private static Logger     logger = Logger.getLogger(VerificaAnnualitaRiferimentoAction.class);

  /** manager per operazioni di interrogazione del db. */
  private SqlManager          sqlManager;

	/**
   * @param sqlManager
   *        manager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManager) {
	  this.sqlManager = sqlManager;
  }

  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
	  response.setHeader("cache-control", "no-cache");
	  response.setContentType("text/text;charset=utf-8");
	  PrintWriter out = response.getWriter();
	  String compare = "";
	  Long annualita1, annualita2;
	  try {
		  String codice = request.getParameter("codice");
		  DataColumnContainer keys = new DataColumnContainer(codice);
		  Long contri = (keys.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
		  Long nprogint = new Long(request.getParameter("nprogint"));
		  
		  annualita1 = (Long) sqlManager.getObject("select annrif from INTTRI where contri = ? and nprogint = ? ", new Object[] { contri, nprogint });
		  String action = request.getParameter("action");
		  if (action.equals("prev")) {
			  nprogint--;
		  } else {
			  nprogint++;
		  }
		  annualita2 = (Long) sqlManager.getObject("select annrif from INTTRI where contri = ? and nprogint = ? ", new Object[] { contri, nprogint });
		  if (annualita1 != null && annualita2 != null && annualita1.equals(annualita2)) {
			  compare = "equal";
		  }
	  } catch (Exception e) {
		  logger.error("errore nell'aggiornamento della sequenza degli interventi", e);
	  }
	  out.print(compare);
	  out.flush();
	  return null;
  }

}
