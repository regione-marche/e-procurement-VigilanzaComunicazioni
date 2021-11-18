package it.eldasoft.sil.pt.tags.funzioni;

import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityStringhe;

public class GetProgressivoCalcolatoPTFunction extends AbstractFunzioneTag {

	public GetProgressivoCalcolatoPTFunction() {
		super(2, new Class[] { PageContext.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, Object[] params) throws JspException {
		String codicePianoTriennale = (String) params[1];

		String ret = null;
		Long progressivoCalcolato = new Long(0);
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

		String datiComuniPianiTriennali = "select coalesce(anntri,0), coalesce(cenint,''), coalesce(tiprog,0) from piatri where contri = ? ";

		String maxCuiInterventi = "select coalesce(max(cuiint),'') from inttri where contri in (select contri from piatri where anntri = ? and cenint = ? and tiprog = ? )";

		String maxCuiInterventiNonRiproposti = "select coalesce(max(cuiint),'') from inrtri where contri in (select contri from piatri where anntri = ? and cenint = ? and tiprog = ? )";

		try {
			Vector datiCalcoloMaxCui = sqlManager.getVector(datiComuniPianiTriennali, new Object[] { codicePianoTriennale });

			if (datiCalcoloMaxCui.size() > 0) {
				Object[] parametriCui = new Object[] { new Long(datiCalcoloMaxCui.get(0).toString()), datiCalcoloMaxCui.get(1).toString(), new Long(datiCalcoloMaxCui.get(2).toString()) };

				String maxCuiInt = (String) sqlManager.getObject(maxCuiInterventi, parametriCui);
				String maxCuiIntNRip = (String) sqlManager.getObject(maxCuiInterventiNonRiproposti, parametriCui);

				if (StringUtils.isNotEmpty(maxCuiInt)) {
					progressivoCalcolato = new Long(maxCuiInt.substring((maxCuiInt.length()-5), maxCuiInt.length()));
				}
				if (StringUtils.isNotEmpty(maxCuiIntNRip)) {
					Long progressivoCalcolatoTemp = new Long(maxCuiIntNRip.substring((maxCuiIntNRip.length()-5), maxCuiIntNRip.length()));
					if (progressivoCalcolatoTemp > progressivoCalcolato) {
						progressivoCalcolato = progressivoCalcolatoTemp;
					}
				}
			}

			progressivoCalcolato = progressivoCalcolato + 1;

			ret = UtilityStringhe.fillLeft(progressivoCalcolato.toString(), '0', 5);

		} catch (SQLException e) {
			throw new JspException("Errore nel calcolo del progressivo per l'intervento " + codicePianoTriennale, e);
		}

		return ret;
	}
}
