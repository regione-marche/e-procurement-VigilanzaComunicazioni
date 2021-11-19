package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Lettura del campo W9GARA.PROV_DATO per determinare se la gara è stata creata per uno SMARTCIG
 * 
 * @author Mirco.Franzoni
 */
public class IsGaraSmartCigFunction extends AbstractFunzioneTag {


	private static final String SMARTCIG_NO                 = "no";
	private static final String SMARTCIG_SI                 = "si";

	/**
	 * Costruttore.
	 */
	public IsGaraSmartCigFunction() {
		super(2, new Class[] { PageContext.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, Object[] params)
	throws JspException {
		String result = SMARTCIG_NO;
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
		try {
			Long codgara = new Long((String) params[1]);
			Long provDato = (Long) sqlManager.getObject(
					"select PROV_DATO from W9GARA where CODGARA=?", new Object[]{codgara});

			if (new Long(4).equals(provDato)) {
				result = SMARTCIG_SI;
			}
		} catch (NumberFormatException e) {
			throw new JspException("Errore nell'estrazione dalla base dati del campo W9GARA.PROV_DATO", e);
		} catch (SQLException e) {
			throw new JspException("Errore nell'estrazione dalla base dati del campo W9GARA.PROV_DATO", e);
		}
		return result;
	}

}
