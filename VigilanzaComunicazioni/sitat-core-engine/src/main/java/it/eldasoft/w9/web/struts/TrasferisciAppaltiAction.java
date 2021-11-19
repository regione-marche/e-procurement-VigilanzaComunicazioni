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
 * Action per eseguire il trasferimento di avvisi e appalti ad un utente della stessa stazione applatante.
 */
public class TrasferisciAppaltiAction extends ActionBaseNoOpzioni {

	static Logger logger = Logger.getLogger(TrasferisciAppaltiAction.class);

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
		
		String procedura = request.getParameter("procedura");
		String groupUtenti = request.getParameter("groupUtenti");
		String chiavi = request.getParameter("chiavi");
		String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
	    String[] listaChiavi = chiavi.split("--");
	    String where = "1 = 0";
		TransactionStatus transazioneCancellazione = null;
		
		try {
			transazioneCancellazione = sqlManager.startTransaction();
			
			Long nuovoPropietario = new Long(groupUtenti);
			if (procedura.equals("avvisi")) {
				for(int i = 0; i < listaChiavi.length; i++) {
					DataColumnContainer key = new DataColumnContainer(listaChiavi[i]);
					Long idavviso = (key.getColumnsBySuffix("IDAVVISO", true))[0].getValue().longValue();
			        where += " OR IDAVVISO = " + idavviso;
				}
				this.sqlManager.update("UPDATE avviso SET syscon = ? WHERE codein = ? AND (" + where + ")",  new Object[]{nuovoPropietario, codein});
			} else if (procedura.equals("gare")) {
				for(int i = 0; i < listaChiavi.length; i++) {
					DataColumnContainer key = new DataColumnContainer(listaChiavi[i]);
					Long codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
			        where += " OR CODGARA = " + codgara;
				}
				this.sqlManager.update("UPDATE w9gara SET syscon = ? WHERE " + where,  new Object[]{nuovoPropietario});
			} else if (procedura.equals("programmi")) {
				for(int i = 0; i < listaChiavi.length; i++) {
					DataColumnContainer key = new DataColumnContainer(listaChiavi[i]);
					Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
			        where += " OR CONTRI = " + contri;
				}
				this.sqlManager.update("UPDATE piatri SET syscon = ? WHERE " + where,  new Object[]{nuovoPropietario});
			}
			
			sqlManager.commitTransaction(transazioneCancellazione);
		} catch (Exception ex) {
			request.setAttribute("procedura", procedura);
			request.setAttribute("chiavi", chiavi);
			try {
				// tento il rollback
				sqlManager.rollbackTransaction(transazioneCancellazione);
			} catch (SQLException sqle) {
				logger.error("Errore nell'esecuzione del rollback del Trasferimento Appalti ", ex);
			}
			aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", ex.getMessage());
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
		} 
		return mapping.findForward(target);
	}

}