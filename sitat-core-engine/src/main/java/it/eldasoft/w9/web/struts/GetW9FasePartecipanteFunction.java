package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetW9FasePartecipanteFunction extends AbstractFunzioneTag {

	public GetW9FasePartecipanteFunction() {
		super(3, new Class[] { PageContext.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, final Object[] params) throws JspException {

		String resultString = null;

		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
				 
		Long codiceGara = null;
		Long numAppa = null;
		Long codiceLotto = null;

		String tempKey = (String) params[1];

		codiceGara = new Long(tempKey.split(";")[0].split(":")[1]);
		numAppa = new Long(tempKey.split(";")[1].split(":")[1]);
		codiceLotto = new Long(tempKey.split(";")[2].split(":")[1]);

		try {
			if (UtilitySITAT.existsFase(codiceGara, codiceLotto, numAppa, 101, sqlManager)){
				resultString = "presenteListaPartecipanti";
			}
		} catch (SQLException e) {
			throw new JspException("Errore nell'estrarre fase partecipante ", e);
		}
		
		

        this.getRequest().setAttribute("isFasePartecipante", resultString);
		
		return resultString;
	}

}
