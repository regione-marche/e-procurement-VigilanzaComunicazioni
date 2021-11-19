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
public class GetListaCIGFunction extends AbstractFunzioneTag {

	/** Costruttore. */
	public GetListaCIGFunction() {
		super(2, new Class[] { PageContext.class, String.class });
	}

	public String function(PageContext pageContext, Object[] params)
	throws JspException {
		String listaCIG = "";
		Long ig_gara = null;
		try {
			ig_gara = new Long((String) params[1]);
			
			SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

			List<?> listCIG = sqlManager.getListVector(
					"select CIG from W9LOTT where CODGARA = ?",
					new Object[] { ig_gara });
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
			throw new JspException("Errore nel calcolare il valore LISTA CIG", e);
		}
		return listaCIG;
	}

}
