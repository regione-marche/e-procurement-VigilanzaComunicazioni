/**
 * 
 */
package it.eldasoft.w9.web.struts;

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

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;

public class CancellazionePubblicazioneAction extends ActionBaseNoOpzioni {

	/** Logger di classe. */
	private static Logger logger = Logger.getLogger(CancellazionePubblicazioneAction.class);

	private SqlManager sqlManager;

	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	@Override
	protected ActionForward runAction(ActionMapping mapping, ActionForm form, 
	    HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("runAction: inizio metodo");
		}

		TransactionStatus transazioneCancellazione = null;

		String target = CostantiGeneraliStruts.FORWARD_OK;
		Long codiceGara = new Long(request.getParameter("codgara"));
		Long numeroPubblicazione = new Long(request.getParameter("numpubb"));
		Object[] parametri = new Object[] { codiceGara, numeroPubblicazione };

		try {
			// inizializzo la transazione
			transazioneCancellazione = sqlManager.startTransaction();

			// cancello tutti i documenti della pubblicazione
			sqlManager.update("DELETE from W9DOCGARA WHERE CODGARA = ? AND NUM_PUBB = ? ", parametri);
			// cancello le associazioni ai lotti della pubblicazione
			sqlManager.update("DELETE from W9PUBLOTTO WHERE CODGARA = ? AND NUM_PUBB = ? ", parametri);
			// cancello la pubblicazione
			sqlManager.update("DELETE from W9PUBBLICAZIONI WHERE CODGARA = ? AND NUM_PUBB = ? ", parametri);

			// committo la transazione
			sqlManager.commitTransaction(transazioneCancellazione);
		} catch (SQLException e) {
			logger.error("Errore nella cancellazione della pubblicazione " + numeroPubblicazione + " riferita alla gara " + codiceGara, e);
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
			try {
				// tento il rollback
				sqlManager.rollbackTransaction(transazioneCancellazione);
			} catch (SQLException sqle) {
				logger.error("Errore nell'esecuzione del rollback della cancellazione ", e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("runAction: fine metodo");
		}

		return mapping.findForward(target);
	}

}
