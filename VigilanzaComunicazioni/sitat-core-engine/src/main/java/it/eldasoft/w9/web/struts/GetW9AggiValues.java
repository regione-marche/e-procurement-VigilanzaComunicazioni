package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetW9AggiValues extends AbstractFunzioneTag {

	public GetW9AggiValues() {
		super(3, new Class[] { PageContext.class, String.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, final Object[] params) throws JspException {

		try {
			
			SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
			
			Long codiceGara = null;
			Long numAppa = null;
			Long codiceLotto = null;

			String tempKey = (String) params[1];

			codiceGara = new Long(tempKey.split(";")[0].split(":")[1]);
			numAppa = new Long(tempKey.split(";")[1].split(":")[1]);
			codiceLotto = new Long(tempKey.split(";")[2].split(":")[1]);

			Long numeroImpresa = new Long(params[2].toString());

			// ricavo l'id Gruppo
			String sqlQueryIdGruppo = "select id_gruppo from w9aggi where codgara = ? and codlott = ? and num_appa = ? and num_aggi = ?";

			Long idGruppoRicavato = (Long) sqlManager.getObject(sqlQueryIdGruppo, new Object[] { codiceGara, codiceLotto, numAppa, numeroImpresa });

			this.getRequest().setAttribute("idGruppoRicavato", idGruppoRicavato);

			// ricavo l'id tipoAgg
			String sqlQueryIdTipoAgg = "select id_tipoagg from w9aggi where codgara = ? and codlott = ? and num_appa = ? and num_aggi = ?";

			Long idTipoAggRicavato = (Long) sqlManager.getObject(sqlQueryIdTipoAgg, new Object[] { codiceGara, codiceLotto, numAppa, numeroImpresa });

			this.getRequest().setAttribute("idTipoAggRicavato", idTipoAggRicavato);

		} catch (SQLException e) {
			throw new JspException("Errore nel recupero dei dati dal database.", e);
		}

		return null;
	}

}
