package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumnContainer;

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
 * Action per eseguire il trasferimento di tutti gli avvisi bandi e programmi da un utente a un'altro.
 */
public class TrasferisciDatiSubentroAction extends ActionBaseNoOpzioni {

	static Logger logger = Logger.getLogger(TrasferisciDatiSubentroAction.class);

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
		
		String programmi = request.getParameter("programmi");
		String avvisi = request.getParameter("avvisi");
		String bandi = request.getParameter("bandi");
		String nuovoReferente = request.getParameter("nuovoReferente");
		String vecchioReferente = request.getParameter("vecchioReferente");
		String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
		TransactionStatus transazioneSubentro = null;
		
		try {
			transazioneSubentro = sqlManager.startTransaction();
			
			String[] nuovoUtente =  nuovoReferente.split(";");
			String[] veccchioUtente =  vecchioReferente.split(";");
			Long nuovoPropietario = new Long(nuovoUtente[0]);
			Long vecchioPropietario = new Long(veccchioUtente[0]);
			if (avvisi != null && avvisi.equals("on")) {
				this.sqlManager.update("UPDATE avviso SET syscon = ? WHERE codein = ? AND syscon = ?",  new Object[]{nuovoPropietario, codein, vecchioPropietario});
			}
			if (bandi != null && bandi.equals("on")) {
				this.sqlManager.update("UPDATE w9gara SET syscon = ? WHERE codein = ? AND syscon = ?",  new Object[]{nuovoPropietario, codein, vecchioPropietario});
			}
			if (programmi != null && programmi.equals("on")) {
				this.sqlManager.update("UPDATE piatri SET syscon = ? WHERE cenint = ? AND syscon = ?",  new Object[]{nuovoPropietario, codein, vecchioPropietario});
			}
			sqlManager.commitTransaction(transazioneSubentro);
		} catch (Exception ex) {
			try {
				// tento il rollback
				sqlManager.rollbackTransaction(transazioneSubentro);
			} catch (SQLException sqle) {
				logger.error("Errore nell'esecuzione del rollback del Trasferimento Dati Subentro ", ex);
			}
			aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", ex.getMessage());
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
		} 
		return mapping.findForward(target);
	}

}