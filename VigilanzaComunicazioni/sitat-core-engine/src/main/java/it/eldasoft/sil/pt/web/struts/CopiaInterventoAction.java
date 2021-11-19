package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumnContainer;

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
public class CopiaInterventoAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
	private static Logger               logger        = Logger.getLogger(CopiaInterventoAction.class);
  
	/** manager per operazioni di interrogazione del db. */
	private SqlManager          sqlManager;
  
	/** query per interrogazione programmi. */
	private static final String SQL_SELECT_ID = "select ID from PIATRI where CONTRI = ?";

	/**
	   * @param sqlManager
	   *        manager da settare internamente alla classe.
	   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   * @see it.eldasoft.gene.commons.web.struts.ActionBase#runAction(org.apache.struts.action.ActionMapping,
   *      org.apache.struts.action.ActionForm,
   *      javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String target = CostantiGeneraliStruts.FORWARD_OK;
    DataColumnContainer key;
    
    try {
      String idProgramma = "";
      String chiave = (String) request.getParameter("chiave");

      if (chiave.indexOf(";;") != -1) {
		  key = new DataColumnContainer(chiave.split(";;")[0]);
	  } else {
	      key = new DataColumnContainer(chiave);
	  }

      request.getSession().setAttribute("interventoCopiato", chiave);

      Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
      //String conint = (key.getColumnsBySuffix("CONINT", true))[0].getValue().toString();

      idProgramma = (String) sqlManager.getObject(SQL_SELECT_ID,
          new Object[] { contri });
      request.getSession().setAttribute("idProgramma", idProgramma);

    } catch (Exception e) {
      logger.error("Problema nel recupero dell\'ID del Programma.", e);
      aggiungiMessaggio(request, "errors.copiaIntervento.recuperoId");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }

    return mapping.findForward(target);
  }

}
