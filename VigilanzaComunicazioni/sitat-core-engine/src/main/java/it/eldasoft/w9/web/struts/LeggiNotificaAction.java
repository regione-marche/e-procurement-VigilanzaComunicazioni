package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Mirco.Franzoni - Eldasoft S.p.A. Treviso
 *
 */

public class LeggiNotificaAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
  private static Logger     logger = Logger.getLogger(LeggiNotificaAction.class);

  private SqlManager          			sqlManager;

  /**
   * @param sqlManager
   *        sqlManager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
	  boolean successProcess = false;
	  response.setHeader("cache-control", "no-cache");
	  response.setContentType("text/text;charset=utf-8");
	  PrintWriter out = response.getWriter();
	  TransactionStatus status = null;
	  try {
		  status = this.sqlManager.startTransaction();
		  Long codice = Long.parseLong(request.getParameter("notecod"));
		  this.sqlManager.update("update g_noteavvisi set statonota = 90 where notecod = ?", new Object[] {codice});
		  out.print("success");
		  successProcess = true;
	  } catch (Exception e) {
		  logger.error("errore nell'aggiornamento dello stato della nota", e);
		  out.print("error");
	  } finally {
		  if (status != null) {
			  try {
	        if (successProcess) {
	          this.sqlManager.commitTransaction(status);
	        } else {
	          this.sqlManager.rollbackTransaction(status);
	        }
	      } catch (SQLException e) {
	      }
		  }
	  }
	  out.flush();
	  return null;
  }

}
