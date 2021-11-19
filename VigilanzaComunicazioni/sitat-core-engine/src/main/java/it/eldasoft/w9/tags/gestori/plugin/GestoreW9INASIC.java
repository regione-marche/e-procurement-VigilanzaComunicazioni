package it.eldasoft.w9.tags.gestori.plugin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestoreW9INASIC extends AbstractGestorePreload {

  /** Query per estrarre il campo W9INASIC.CODIMP. */
	private static final String SQL_ESTRAZIONE_DATI_W9INASIC = "select CODIMP from W9INASIC where CODGARA = ? AND CODLOTT = ? AND NUM_INAD = ?";
	/** Query per estrarre il campo IMPR.NOMEST. */
	private static final String SQL_ESTRAZIONE_IMPR = "select NOMEST from IMPR where codimp = ? ";

	/** Costruttore. */
	public GestoreW9INASIC(BodyTagSupportGene tag) {
		super(tag);
	}

	public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

	}

	public void doBeforeBodyProcessing(PageContext page,
			String modoAperturaScheda) throws JspException {
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean(
				"sqlManager", page, SqlManager.class);
		String codice = "";
		DataColumnContainer key = null;
		Long codgara = null;
		Long codlott = null;
		Long numinad = null;
		if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
			codice = (String) UtilityTags.getParametro(page,
					UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
			key = new DataColumnContainer(codice);
			try {
				codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
				codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
				numinad = (key.getColumnsBySuffix("NUM_INAD", true))[0].getValue().longValue();
				List< ? > datiList = sqlManager.getListVector(
				    SQL_ESTRAZIONE_DATI_W9INASIC, new Object[] { codgara, codlott, numinad });
				String codimpr = "";
				String nomeimpr = "";
				if (datiList.size() > 0) {
					codimpr = ((List< ? >) datiList.get(0)).get(0).toString();
					if (codimpr != null && !codimpr.equals("")) {
					  List< ? > nomeimprList = sqlManager.getListVector(SQL_ESTRAZIONE_IMPR,
					      new Object[] { codimpr });
					  if (nomeimprList.size() > 0) {
					    nomeimpr = ((List< ? >) nomeimprList.get(0)).get(0).toString();
					  }
					}
				}
				page.setAttribute("nomeimpr", nomeimpr, PageContext.REQUEST_SCOPE);
			} catch (SQLException sqle) {
				throw new JspException(
						"Errore nell'esecuzione della query per l'estrazione dei dati riguardanti Inadempienza Misure sulla Sicurezza",
						sqle);
			} catch (Exception exc) {
				throw new JspException(
						"Errore nel caricamento dati riguardanti Inadempienza Misure sulla Sicurezza", exc);
			}
		}
	}

}
