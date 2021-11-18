package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/** 
 * Function per estrarre il max(nr_lotto)+1.
 */
public class GetListaCIGEsitiFunction extends AbstractFunzioneTag {

	/** Costruttore. */
	public GetListaCIGEsitiFunction() {
		super(3, new Class[] { PageContext.class, String.class, String.class });
	}

	public String function(PageContext pageContext, Object[] params)
	throws JspException {
		String listaCIG = "";
		Long ig_gara = null;
		Long num_pubb = null;
		try {
			ig_gara = new Long((String) params[1]);
			num_pubb = new Long((String) params[2]);
			SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

			List<?> listCIG = sqlManager.getListVector(
					"select CIG from W9PUBLOTTO left join W9LOTT on W9PUBLOTTO.CODGARA=W9LOTT.CODGARA and W9PUBLOTTO.CODLOTT=W9LOTT.CODLOTT where W9PUBLOTTO.CODGARA = ? and W9PUBLOTTO.NUM_PUBB = ?",
					new Object[] { ig_gara, num_pubb });
			int countCig = listCIG.size();
			if (countCig > 0) {
				if (countCig>10) {
					countCig = 10;
				}
				for (int i = 0; i < countCig; i++) {
					if (!((List<?>) listCIG.get(i)).get(0).toString().equals("")) {
						listaCIG += ((List<?>) listCIG.get(i)).get(0).toString() + " ";
					}
				}
				if (listCIG.size()>10) {
					listaCIG += "...";
				}
			}
		
		} catch (Exception e) {
			throw new JspException("Errore nel calcolare il valore LISTA CIG ESITI", e);
		}
		return listaCIG;
	}

}
