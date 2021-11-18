package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.EsportazioneXMLSIMOGManager;
import it.toscana.rete.rfc.sitat.types.ErroreType;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action per eseguire l'invio di una singola scheda verso SIMOG.
 */
public class EseguiInvioSchedaSimogAction extends ActionBaseNoOpzioni {

	static Logger logger = Logger.getLogger(EseguiInvioSchedaSimogAction.class);

	private EsportazioneXMLSIMOGManager esportazioneXMLSIMOGManager;
	private SqlManager sqlManager;

	public void setEsportazioneXMLSIMOGManager(EsportazioneXMLSIMOGManager esportazioneXMLSIMOGManager) {
		this.esportazioneXMLSIMOGManager = esportazioneXMLSIMOGManager;
	}

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

		String risultatoReInvio = "KO";
		String target = CostantiGeneraliStruts.FORWARD_OK;

		Long codgara = new Long(request.getParameter("codgara"));
		Long codlott = new Long(request.getParameter("codlott"));
		Long numxml = new Long(request.getParameter("numxml"));
		Long fase_esecuzione = new Long(request.getParameter("fase_esecuzione"));
		Long num =  new Long(request.getParameter("num"));
		Long idFlusso = null;
		if (request.getParameter("idflusso") != null && !request.getParameter("idflusso").equals("")) {
			idFlusso = new Long(request.getParameter("idflusso"));
		}

		try {
			ErroreType[] erroriDalLoaderAppalto = this.esportazioneXMLSIMOGManager.loaderAppalto(
					codgara, codlott, numxml, fase_esecuzione, num, idFlusso, null, null, false);
			if (erroriDalLoaderAppalto == null) {
		     Long numeroErrori = (Long) this.sqlManager.getObject(
		         "select NUM_ERRORE from W9XML where CODGARA=? and CODLOTT=? and NUMXML=?",
		          new Object[] { codgara, codlott, numxml });
				if (numeroErrori == null || (numeroErrori != null && numeroErrori.longValue() == 0)) {
					risultatoReInvio = "OK";
				}
			}

		} catch (SQLException s) {
			logger.error("Errore nella lettura del campo W9XMl.NUM_ERRORE", s);
			aggiungiMessaggio(request, "errors.database.dataAccessException");
			target = "error";
		} catch (GestoreException e) {
			logger.error("Problema nel recupero della busta soap dalla base dati.", e);
			aggiungiMessaggio(request, "errors.inviaXMLSIMOG.comunicazionewarning");
			target = "error";
		}

		request.getSession().setAttribute("RISULTATOREINVIO", risultatoReInvio);

		return mapping.findForward(target);
	}

}