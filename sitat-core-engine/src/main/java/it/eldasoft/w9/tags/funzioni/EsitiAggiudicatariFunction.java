package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9AggiudicatarieManager;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class EsitiAggiudicatariFunction extends AbstractFunzioneTag {

	public EsitiAggiudicatariFunction() {
		super(3, new Class[] { PageContext.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, final Object[] params) throws JspException {

		// recupero tutti i valori necessari alla visualizzazione della lista
		W9AggiudicatarieManager w9AggiudicatarieManager = (W9AggiudicatarieManager) UtilitySpring.getBean("w9AggiudicatarieManager", pageContext, W9AggiudicatarieManager.class);

		Long codiceGara = null;
		Long numPubb = null;

		String tempKey = (String) params[1];

		codiceGara = new Long(tempKey.split(";")[0].split(":")[1]);
		numPubb = new Long(tempKey.split(";")[1].split(":")[1]);
		
		List<Vector<?>> risultatoListaAggiudicatari = new Vector<Vector<?>>();
		try {
			risultatoListaAggiudicatari = w9AggiudicatarieManager.getListaAggiudicatariEsito(pageContext, codiceGara, numPubb);
		} catch (SQLException e) {
			throw new JspException("Errore nel recupero dei dati dal database.", e);

		} catch (GestoreException e) {
			throw new JspException("Errore nel nell'esecuzione della function.", e);
		}

		pageContext.setAttribute("risultatoListaAggiudicatari", risultatoListaAggiudicatari, PageContext.REQUEST_SCOPE);
		String resultStringISDEL = null;
		this.getRequest().setAttribute("isFasePartecipante", resultStringISDEL);

		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

		
		// determino se la gara e' in una fase di accordo quadro
		String resultStringISAQ = null;
		
		try {
			if (UtilitySITAT.isSAQ(codiceGara, sqlManager)) {
				resultStringISAQ = "isAccordoQuadro";
			}
		} catch (SQLException e) {
			throw new JspException("Errore nel recupero dei dati dal database.", e);
		}

		this.getRequest().setAttribute("isAccordoQuadro", resultStringISAQ);

		return null;
	}

}
