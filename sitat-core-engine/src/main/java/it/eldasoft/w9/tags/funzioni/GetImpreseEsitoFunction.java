package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Function per estrarre le imprese aggiuidcatarie.
 */
public class GetImpreseEsitoFunction extends AbstractFunzioneTag {

	/** Costruttore. */
	public GetImpreseEsitoFunction() {
		super(1, new Class[] { String.class });
	}

	public String function(PageContext pageContext, Object[] params) throws JspException {

		String codice = params[0].toString();
		if (codice == null || codice.equals("")) {
			codice = (String) UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
		}
		if (codice == null || codice.equals("")) {
			codice = (String) UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
		}
		DataColumnContainer key = new DataColumnContainer(codice);
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

		try {
			// i valori vengono ricavati dal parametro passato key_aggiudicataria
			Long codGara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
			Long numPubb = (key.getColumnsBySuffix("NUM_PUBB", true))[0].getValue().longValue();

			Long numImpr = (key.getColumnsBySuffix("NUM_AGGI", true))[0].getValue().longValue();
			Long idGrupp = (key.getColumnsBySuffix("ID_GRUPPO", true))[0].getValue().longValue();

			String selectSQL = "";
			Long codeSQL = null;
			// distinguo se fare una select per idgruppo(impresa multipla) o numaggi(impresa singola)
			if (idGrupp != null) {
				selectSQL = "select CODGARA, NUM_PUBB, NUM_AGGI, CODIMP, ID_TIPOAGG, RUOLO, ID_GRUPPO " + "from ESITI_AGGIUDICATARI "
						+ "where CODGARA = ? and NUM_PUBB = ? and ID_GRUPPO = ? " + "order by NUM_AGGI";
				codeSQL = idGrupp;
			} else {
				selectSQL = "select CODGARA, NUM_PUBB, NUM_AGGI, CODIMP, ID_TIPOAGG, RUOLO, ID_GRUPPO " + "from ESITI_AGGIUDICATARI "
						+ "where CODGARA = ? and NUM_PUBB = ? and NUM_AGGI = ? " + "order by NUM_AGGI";
				codeSQL = numImpr;
			}

			List<?> listaImpreseAggiudicatarie = sqlManager.getListVector(selectSQL, new Object[] { codGara, numPubb, codeSQL });

			pageContext.setAttribute("listaImpreseAggiudicatarie", listaImpreseAggiudicatarie, PageContext.REQUEST_SCOPE);

		} catch (SQLException e) {
			throw new JspException("Errore nell'estrarre imprese aggiudicatarie dell'esito ", e);
		} catch (GestoreException g) {
			throw new JspException("Errore nel determinare i valori di COGDARA, NUM_PUBB per le imprese aggiudicatarie dell'esito", g);
		}
		return null;
	}

}
