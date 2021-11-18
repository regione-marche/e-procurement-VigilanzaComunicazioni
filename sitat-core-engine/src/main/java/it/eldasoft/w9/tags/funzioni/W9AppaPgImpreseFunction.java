package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
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

public class W9AppaPgImpreseFunction extends AbstractFunzioneTag {

	public W9AppaPgImpreseFunction() {
		super(3, new Class[] { PageContext.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, final Object[] params) throws JspException {

		// recupero tutti i valori necessari alla visualizzazione della lista
		W9AggiudicatarieManager w9AggiudicatarieManager = (W9AggiudicatarieManager) UtilitySpring.getBean("w9AggiudicatarieManager", pageContext, W9AggiudicatarieManager.class);

		Long codiceGara = null;
		Long numAppa = null;
		Long codiceLotto = null;

		String tempKey = (String) params[1];

		DataColumnContainer keys = new DataColumnContainer(tempKey);
		
		codiceGara = new Long((keys.getColumnsBySuffix("CODGARA", true))[0].getValue().toString());
		codiceLotto = new Long((keys.getColumnsBySuffix("CODLOTT", true))[0].getValue().toString());
		numAppa = new Long((keys.getColumnsBySuffix("NUM_APPA", true))[0].getValue().toString());
		

		List<Vector<?>> risultatoListaW9Aggiudicatarie = new Vector<Vector<?>>();
		try {
			risultatoListaW9Aggiudicatarie = w9AggiudicatarieManager.getListaW9Aggiudicatarie(pageContext, codiceGara, numAppa, codiceLotto);
		} catch (SQLException e) {
			throw new JspException("Errore nel recupero dei dati dal database.", e);

		} catch (GestoreException e) {
			throw new JspException("Errore nel nell'esecuzione della function.", e);
		}

		pageContext.setAttribute("risultatoListaW9Aggiudicatarie", risultatoListaW9Aggiudicatarie, PageContext.REQUEST_SCOPE);

		// determino la presenza della fase di partecipazione delle imprese
		// e pongo il risultato nella request come attributo
		String resultStringISDEL = null;

		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

		try {
			if (UtilitySITAT.existsFase(codiceGara, codiceLotto, numAppa, 101, sqlManager)) {
				resultStringISDEL = "presenteListaPartecipanti";
			}
		} catch (SQLException e) {
			throw new JspException("Errore nell'estrarre fase partecipante ", e);
		}

		this.getRequest().setAttribute("isFasePartecipante", resultStringISDEL);

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
