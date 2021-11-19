package it.eldasoft.sil.pt.tags.funzioni;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

public class GetNextConintFunctionINR extends AbstractFunzioneTag {

	public GetNextConintFunctionINR() {
		super(2, new Class[] { PageContext.class, String.class });
	}

	public String function(PageContext pageContext, Object[] params) throws JspException {
		Long contri = new Long(params[1].toString());

		try {

			SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

			List<?> listCount = null;

			listCount = sqlManager.getListVector("select max(conint) from inrtri where contri = ?", new Object[] { contri });
			int count = 1;
			if (listCount.size() > 0 && !((List<?>) listCount.get(0)).get(0).toString().equals("")) {
				count = 1 + Integer.parseInt(((List<?>) listCount.get(0)).get(0).toString());
			}
			int nprogint = Integer.parseInt(sqlManager.getObject("select count(*) from inrtri where contri = ? ", new Object[] { contri }).toString());
			pageContext.setAttribute("count", count, PageContext.REQUEST_SCOPE);
			pageContext.setAttribute("nprogint", nprogint + 1, PageContext.REQUEST_SCOPE);

		} catch (Exception e) {
			throw new JspException("Errore nel calcolare il valore conint", e);
		}
		return null;
	}

}
