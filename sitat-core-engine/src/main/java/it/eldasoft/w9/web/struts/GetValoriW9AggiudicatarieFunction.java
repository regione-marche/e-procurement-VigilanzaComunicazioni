package it.eldasoft.w9.web.struts;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9AggiudicatarieManager;

public class GetValoriW9AggiudicatarieFunction extends AbstractFunzioneTag {

	public GetValoriW9AggiudicatarieFunction() {
		super(3, new Class[] { PageContext.class, String.class });
		// TODO Auto-generated constructor stub
	}

	@Override
	public String function(PageContext pageContext, final Object[] params) throws JspException {

		W9AggiudicatarieManager w9AggiudicatarieManager = (W9AggiudicatarieManager) UtilitySpring.getBean("w9AggiudicatarieManager", pageContext, W9AggiudicatarieManager.class);

		Long codiceGara = null;
		Long numAppa = null;
		Long codiceLotto = null;

		String tempKey = (String) params[1];

		codiceGara = new Long(tempKey.split(";")[0].split(":")[1]);
		numAppa = new Long(tempKey.split(";")[1].split(":")[1]);
		codiceLotto = new Long(tempKey.split(";")[2].split(":")[1]);

		List<Vector<?>> risultatoListaW9Aggiudicatarie = new Vector<Vector<?>>();
		try {
			risultatoListaW9Aggiudicatarie = w9AggiudicatarieManager.getListaW9Aggiudicatarie(pageContext, codiceGara, numAppa, codiceLotto);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new JspException("Errore nel recupero dei dati dal database.", e);

		} catch (GestoreException e) {
			// TODO Auto-generated catch block
			throw new JspException("Errore nel nell'esecuzione della function.", e);
		}

		pageContext.setAttribute("risultatoListaW9Aggiudicatarie", risultatoListaW9Aggiudicatarie, PageContext.REQUEST_SCOPE);

		return null;
	}

}
