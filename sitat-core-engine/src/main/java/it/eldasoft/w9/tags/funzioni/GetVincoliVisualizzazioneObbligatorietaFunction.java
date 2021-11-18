package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetVincoliVisualizzazioneObbligatorietaFunction extends AbstractFunzioneTag {

	/** Costruttore. */
	public GetVincoliVisualizzazioneObbligatorietaFunction() {
		super(2, new Class[] { PageContext.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, Object[] params) throws JspException {
		Long tipologiaPubblicazione = new Long((String) params[1]);
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
		try {
			// vincoli di visualizzazione
			String campiVisualizzabili = (String) sqlManager.getObject("select CAMPI_VIS from W9CF_PUBB where W9CF_PUBB.ID = ? ", new Object[] { tipologiaPubblicazione });
			pageContext.setAttribute("campiVisualizzabili", campiVisualizzabili, PageContext.REQUEST_SCOPE);
			
			// vincoli di obbligatorieta'
			String campiObbligatori = (String) sqlManager.getObject("select CAMPI_OBB from W9CF_PUBB where W9CF_PUBB.ID = ? ", new Object[] { tipologiaPubblicazione });
			pageContext.setAttribute("campiObbligatori", campiObbligatori, PageContext.REQUEST_SCOPE);
			
			// ricavo il tipo di Atto se è un Esito o un Bando
			String tipo = (String) sqlManager.getObject("select TIPO from W9CF_PUBB where W9CF_PUBB.ID = ? ", new Object[] { tipologiaPubblicazione });
			pageContext.setAttribute("tipoAtto", tipo, PageContext.REQUEST_SCOPE);
			
		} catch (SQLException e) {
			throw new JspException("Errore nell'estrazione dei vincoli di obbligatorieta' e visualizzazione di una pubblicazione", e);
		}
		return null;
	}

}