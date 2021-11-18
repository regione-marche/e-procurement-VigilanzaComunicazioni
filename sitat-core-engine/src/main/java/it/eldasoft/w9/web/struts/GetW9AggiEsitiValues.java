package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetW9AggiEsitiValues extends AbstractFunzioneTag {

	public GetW9AggiEsitiValues() {
		super(3, new Class[] { PageContext.class, String.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, final Object[] params) throws JspException {

		try {
			
			SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
			
			Long codiceGara = null;
			Long numPubb = null;

			String tempKey = (String) params[1];

			codiceGara = new Long(tempKey.split(";")[0].split(":")[1]);
			numPubb = new Long(tempKey.split(";")[1].split(":")[1]);

			Long numeroImpresa = new Long(params[2].toString());

			// ricavo l'id Gruppo
			String sqlQueryIdGruppo = "select id_gruppo from esiti_aggiudicatari where codgara = ? and num_pubb = ? and num_aggi = ?";

			Long idGruppoRicavato = (Long) sqlManager.getObject(sqlQueryIdGruppo, new Object[] { codiceGara, numPubb, numeroImpresa });

			this.getRequest().setAttribute("idGruppoRicavato", idGruppoRicavato);

			// ricavo l'id tipoAgg
			String sqlQueryIdTipoAgg = "select id_tipoagg from esiti_aggiudicatari where codgara = ? and num_pubb = ? and num_aggi = ?";

			Long idTipoAggRicavato = (Long) sqlManager.getObject(sqlQueryIdTipoAgg, new Object[] { codiceGara, numPubb, numeroImpresa });

			this.getRequest().setAttribute("idTipoAggRicavato", idTipoAggRicavato);

		} catch (SQLException e) {
			throw new JspException("Errore nel recupero dei dati dal database.", e);
		}

		return null;
	}

}
