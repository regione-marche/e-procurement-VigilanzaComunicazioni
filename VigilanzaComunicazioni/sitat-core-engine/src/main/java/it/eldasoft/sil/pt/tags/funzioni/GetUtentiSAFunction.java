package it.eldasoft.sil.pt.tags.funzioni;

import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

public class GetUtentiSAFunction extends AbstractFunzioneTag {

	public GetUtentiSAFunction() {
		super(3, new Class[] { PageContext.class, String.class });
	}

	public String function(PageContext pageContext, Object[] params)
	throws JspException {
		boolean sbiancaSA = false;
		boolean sbiancaUtente = false;
		String stazioneAppaltante = params[1].toString();
		String fabbisogni = params[2].toString();
		Long utente = null;
		try {
			SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
					pageContext, SqlManager.class);

			String[] chiavi = new String[] {};
			chiavi = fabbisogni.split(";;");
			String onchange = (String)pageContext.getRequest().getAttribute("onchange");

			String codeinPrecedente = "";
			Long sysconPrec = new Long(0);
			for (int i = 0; i < chiavi.length; i++) {
				DataColumnContainer fabbisogniItem = new DataColumnContainer(
						chiavi[i]);
				Long conint = (fabbisogniItem.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();
				
				if (onchange != null && onchange.equals("true")) {
					//se la SA è stata cambiata
				} else {
					//se è la prima volta che accedo alla pagina verifico che i fabbisogni selezionati abbiano la stessa SA e utente altrimenti sbianco sia la SA che l'utente
					String codein = (String)sqlManager.getObject("Select codein from fabbisogni where conint = ?", new Object[] { conint });
					if (!codeinPrecedente.equals("") && !codein.equals(codeinPrecedente)) {
						sbiancaSA = true;
					}
					codeinPrecedente = codein;
					stazioneAppaltante = codein;
				}
				Long syscon = (Long)sqlManager.getObject("Select syscon from fabbisogni where conint = ?", new Object[] { conint });
				if (!sysconPrec.equals(new Long(0)) && !syscon.equals(sysconPrec)) {
					sbiancaUtente = true;
				}
				sysconPrec = syscon;
				utente = syscon;
			}
			
			
			
			String nomein = null;
			List< ? > utenti = null;
			if (sbiancaSA) {
				stazioneAppaltante = null;
			} else {
				nomein = (String)sqlManager.getObject("Select nomein from uffint where codein = ?", new Object[] { stazioneAppaltante });
				utenti = sqlManager.getListVector(
						"select syscon, sysute, syscf from usrsys where (sysdisab <> '1' or sysdisab is null) and (syspwbou like '%ou89|%' or syscon in (select syscon from usr_ein where codein=?)) "
						, new Object[] { stazioneAppaltante });
			}
			
			if (sbiancaUtente) {
				utente = null;
			}
			
			pageContext.setAttribute("utenti", utenti, PageContext.REQUEST_SCOPE);
			pageContext.setAttribute("stazioneAppaltante", stazioneAppaltante, PageContext.REQUEST_SCOPE);
			pageContext.setAttribute("nomein", nomein, PageContext.REQUEST_SCOPE);
			pageContext.setAttribute("sysconFabbisogno", utente, PageContext.REQUEST_SCOPE);

		} catch (Exception e) {
			throw new JspException("Errore nell'estrarre gli utenti associati alla stazione appaltante ", e);
		}
		return null;
	}

}
