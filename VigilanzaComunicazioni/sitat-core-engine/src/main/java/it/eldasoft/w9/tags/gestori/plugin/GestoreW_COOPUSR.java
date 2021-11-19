package it.eldasoft.w9.tags.gestori.plugin;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiSitatSA;

/**
 * Gestore inizializzazione della scheda W_COOPUSR
 * 
 * @author Mirco.Franzoni
 */
public class GestoreW_COOPUSR extends AbstractGestorePreload {

	public GestoreW_COOPUSR(BodyTagSupportGene tag) {
		super(tag);
	}

	public void doAfterFetch(PageContext page, String modoAperturaScheda)
			throws JspException {
	}

	public void doBeforeBodyProcessing(PageContext pageContext,
			String modoAperturaScheda) throws JspException {
		
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
		String codice = "";
		DataColumnContainer key = null;
		Long id_richiesta = null;
		try {
			codice = (String) UtilityTags.getParametro(pageContext,	UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
			key = new DataColumnContainer(codice);
			id_richiesta = (key.getColumnsBySuffix("ID_RICHIESTA", true))[0].getValue().longValue();
			String codein = (String) sqlManager.getObject("select CODEIN from W_COOPUSR where ID_RICHIESTA=?", new Object[] { id_richiesta });
			if (codein != null) {
				Long nrRichiesteXSA = (Long) sqlManager.getObject("select count(*) from W_COOPUSR where CODEIN=?", new Object[] { codein });
				if (nrRichiesteXSA > 1) {
					UtilityStruts.addMessage(pageContext.getRequest(),"warning","errors.generico",new String[] { "Esistono più richieste credenziali per questo Ente" });
				}
			}
			String profiliDisponibili = ConfigManager.getValore(CostantiGenerali.PROP_REG_PROFILI_DISPONIBILI);
			if (profiliDisponibili != null && !"".equals(profiliDisponibili.trim())) {
				String[] arrayProfiliDisponibili = profiliDisponibili.split(";");
				String codiciProfilo = "";
				for (int i = 0; i < arrayProfiliDisponibili.length; i++) {
					codiciProfilo += "'" + arrayProfiliDisponibili[i] + "'";
					if (i < arrayProfiliDisponibili.length - 1) {
						codiciProfilo += ",";
					}
				}
				String selectServizi = "select COD_PROFILO, NOME from W_PROFILI where COD_PROFILO in (" + codiciProfilo + ")";
				List< ? > datiServiziSelezionati = sqlManager.getListVector(selectServizi, new Object[] {});
				pageContext.setAttribute("datiServiziSelezionati", datiServiziSelezionati, PageContext.REQUEST_SCOPE);
			}
		        
		} catch (Exception e) {
			throw new JspException("Errore durante la verifica dell'esistenza di più richieste inoltrate per la stessa SA",e);
		}
	}
}