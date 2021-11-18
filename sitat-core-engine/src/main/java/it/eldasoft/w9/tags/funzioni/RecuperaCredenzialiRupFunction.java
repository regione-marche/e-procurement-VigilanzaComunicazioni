package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Classe per recuperare le credenziali Simog del Rup della gara.
 * 
 */
public class RecuperaCredenzialiRupFunction extends AbstractFunzioneTag {

	private static final String CREDENZIALI_NO                 = "no";
	private static final String CREDENZIALI_SI                 = "si";

	public RecuperaCredenzialiRupFunction() {
		super(2, new Class[] { PageContext.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, Object[] params)
	throws JspException {
		String esistonoLeCredenziali = CREDENZIALI_NO;

		try {
			SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

			String key = (String) params[1];

			if (key == null || key.equals("")) {
				key = UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
			}

			if (key == null || key.equals("")) {
				key = (String) UtilityTags.getParametro(pageContext,
						UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
			}
			DataColumnContainer keys = new DataColumnContainer(key);

			if (key != null) {

				Long codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
				String cfTec = (String) sqlManager.getObject("select TECNI.CFTEC from W9GARA join TECNI on W9GARA.RUP = TECNI.CODTEC where CODGARA = ?", 
						new Object[] { codgara});
				if (cfTec != null && !cfTec.equals("")) {
					//select simoguser from W9LOADER_APPALTO_USR where syscon = ?
					Vector< ? > datiUsrsys = sqlManager.getVector(
							"select SIMOGUSER, SIMOGPASS from W9LOADER_APPALTO_USR where SYSCON in (select SYSCON from USRSYS where UPPER(SYSCF)= ? ) ",
							new Object[] { cfTec.toUpperCase() });
					if (datiUsrsys != null && datiUsrsys.size() > 0) {
						String simoguser = SqlManager.getValueFromVectorParam(datiUsrsys, 0).getStringValue();
						String simogpass = SqlManager.getValueFromVectorParam(datiUsrsys, 1).getStringValue();
						// Decodifica della password
						ICriptazioneByte cupwspassICriptazioneByte = null;
						cupwspassICriptazioneByte = FactoryCriptazioneByte.getInstance(ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI), simogpass.getBytes(),
								ICriptazioneByte.FORMATO_DATO_CIFRATO);
						String simogpassDecriptata = new String(cupwspassICriptazioneByte.getDatoNonCifrato());

						pageContext.setAttribute("simogpassrup", simogpassDecriptata, PageContext.REQUEST_SCOPE);
						pageContext.setAttribute("simoguserrup",simoguser, PageContext.REQUEST_SCOPE);
						esistonoLeCredenziali = CREDENZIALI_SI;
					}
				}
			}
		} catch (SQLException sqle) {
			throw new JspException(
					"Errore in fase di esecuzione della query per recuperare le credenziali Simog del Rup della gara", sqle);
		} catch (Exception exc) {
			throw new JspException(
					"Errore in fase di esecuzione della query per recuperare le credenziali Simog del Rup della gara", exc);
		}
		return esistonoLeCredenziali;
	}

}