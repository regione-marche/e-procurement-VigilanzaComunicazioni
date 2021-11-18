package it.eldasoft.sil.pt.web.struts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;

/**
 * Action per carica la lista delle opere del programma precedente da selezionare
 * per poi copiarle nel programma attuale.
 * 
 * @author Mirco.Franzoni
 *
 */
public class GetOpereProgrammaPrecedenteAction extends ActionBaseNoOpzioni {

	Logger logger = Logger.getLogger(GetOpereProgrammaPrecedenteAction.class);

	private SqlManager sqlManager;

	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	@Override
	protected ActionForward runAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("runAction: inizio metodo");
		}

		String target = CostantiGeneraliStruts.FORWARD_OK;

		Long chiavePianoTriennale = new Long(request.getParameter("codiceProgramma"));
		String codiceStazioneAppaltante = (String) request.getSession().getAttribute(
				CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
		try {

			Long annoProgramma = (Long) this.sqlManager.getObject("select ANNTRI from PIATRI where CONTRI=?",
					new Object[] { chiavePianoTriennale });

			List<Vector<JdbcParametro>> listaOpere = null; 
			
			listaOpere = this.sqlManager.getListVector(
							"select CONTRI, NUMOI, CUP, DESCRIZIONE, ANNOULTQE, AVANZAMENTO from OITRI "
							+ " where CONTRI in (select CONTRI from PIATRI where ANNTRI=? and CENINT=?) "
							+ "order by NUMOI",
							new Object[] { annoProgramma - 1, codiceStazioneAppaltante } );
			
			if (listaOpere != null && listaOpere.size() > 0) {
				request.setAttribute("listaOpere", listaOpere);
			}
		} catch (SQLException e) {
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
			logger.error("Errore nell'estrazioe della lista delle opere dei programmi dell'anno precedente", e);
			aggiungiMessaggio(request, "errors.generico", "Errore nell'estrazioe della lista delle opere incompiute dei programmi dell'anno precedente");
			this.aggiungiMessaggio(request, "errors.listaInterventiProgrammaPrecedente");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("runAction: fine metodo");
		}

		return mapping.findForward(target);
	}

}
