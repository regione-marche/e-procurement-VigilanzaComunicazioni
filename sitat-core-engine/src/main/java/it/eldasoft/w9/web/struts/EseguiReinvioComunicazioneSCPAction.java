package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;

import java.io.IOException;
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
 * Action per permettere il reinvio della comunicazione ai servizi SCP.
 */
public class EseguiReinvioComunicazioneSCPAction extends ActionBaseNoOpzioni {

	static Logger logger = Logger.getLogger(EseguiReinvioComunicazioneSCPAction.class);

	private SqlManager sqlManager;

	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}
	
	/**
	 * @see it.eldasoft.gene.commons.web.struts.ActionBase#runAction(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward runAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String target = CostantiGeneraliStruts.FORWARD_OK;

		Long idcomun = new Long(request.getParameter("idcomun"));
		TransactionStatus transazioneCancellazione = null;
		try {
			// inizializzo la transazione
			transazioneCancellazione = sqlManager.startTransaction();

			// modifico lo stato della comunicazione per permettere il reinvio alla prossima schedulazione
			sqlManager.update("UPDATE W9OUTBOX SET STATO = 1 WHERE IDCOMUN = ?", new Object[] {idcomun});
			
			// committo la transazione
			sqlManager.commitTransaction(transazioneCancellazione);
		} catch (SQLException e) {
			logger.error("Errore nella modifica della comunicazione SCP " + idcomun , e);
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
			try {
				// tento il rollback
				sqlManager.rollbackTransaction(transazioneCancellazione);
			} catch (SQLException sqle) {
				logger.error("Errore nell'esecuzione del rollback nella modifica della comunicazione SCP ", e);
			}
		}
		
		return mapping.findForward(target);
	}

}