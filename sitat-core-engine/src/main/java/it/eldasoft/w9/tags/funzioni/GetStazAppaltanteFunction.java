/*
 * Created on 24/ago/09
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

public class GetStazAppaltanteFunction extends AbstractFunzioneTag {
	public GetStazAppaltanteFunction() {
		super(1, new Class[] { PageContext.class });
	}

	public String function(PageContext pageContext, Object[] params)
			throws JspException {
		int sysCon;

		ProfiloUtente profiloUtente = (ProfiloUtente) pageContext.getAttribute(
				CostantiGenerali.PROFILO_UTENTE_SESSIONE,
				PageContext.SESSION_SCOPE);

		sysCon = profiloUtente.getId();

		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean(
				"sqlManager", pageContext, SqlManager.class);

		try {
			List<?> listaStazioniAppaltanti = null;

			listaStazioniAppaltanti = sqlManager.getListVector(
					"select UFFINT.CODEIN, UFFINT.NOMEIN, UFFINT.CFEIN " 
					+"from USR_EIN, UFFINT "
					+"where SYSCON =? and UFFINT.CODEIN= USR_EIN.CODEIN",
					new Object[] { sysCon });

			pageContext.setAttribute("listaStazioniAppaltanti",
					listaStazioniAppaltanti, PageContext.REQUEST_SCOPE);

		} catch (SQLException e) {
			throw new JspException(
					"Errore nell'estrarre le stazioni appaltanti ", e);
		}

		return null;
	}

}
