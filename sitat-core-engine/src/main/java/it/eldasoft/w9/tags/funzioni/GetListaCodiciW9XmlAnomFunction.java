package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetListaCodiciW9XmlAnomFunction extends AbstractFunzioneTag {

	/** Costruttore. */
	public GetListaCodiciW9XmlAnomFunction() {
		super(1, new Class[] { PageContext.class });
	}

	@Override
	public String function(PageContext pageContext, Object[] params) throws JspException {
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
		try {
			String selectCodici = "select distinct(codice) from W9XMLANOM order by codice asc";
			List listaCodici = sqlManager.getListVector(selectCodici, null);

			pageContext.setAttribute("listaCodici", listaCodici, PageContext.REQUEST_SCOPE);

		} catch (SQLException e) {
			throw new JspException("Errore nell'estrazione dei vincoli di obbligatorieta' e visualizzazione di una pubblicazione", e);
		}
		return null;
	}

}
