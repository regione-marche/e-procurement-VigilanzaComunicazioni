package it.eldasoft.sil.pt.tags.funzioni;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

public class GetAnnoPTFunction extends AbstractFunzioneTag {

	public GetAnnoPTFunction() {
		super(2, new Class[] { PageContext.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, Object[] params)
			throws JspException {
		String codicePianoTriennale = (String) params[1];

		String ret = null;
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean(
				"sqlManager", pageContext, SqlManager.class);

		String select = "select anntri from piatri where contri = ?";
		try {
			ret = (String) sqlManager.getObject(select,
					new Object[] { codicePianoTriennale }).toString();
		} catch (SQLException e) {
			throw new JspException(
					"Errore nell'estrarre l'anno del piano triennale "
							+ codicePianoTriennale, e);
		}

		return ret;
	}
}
