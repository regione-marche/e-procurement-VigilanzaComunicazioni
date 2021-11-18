package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * Function per estrarre uutti gli utenti associati alla stazione appaltante. 
 */
public class GetUtentiStazioneAppaltanteFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GetUtentiStazioneAppaltanteFunction() {
    super(1, new Class[] { PageContext.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
	  HttpSession sessione = pageContext.getSession();
	  String stazioneAppaltante = (String) sessione.getAttribute(
    	      CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
	  try {
		  SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
				  pageContext, SqlManager.class);
    
		  List< ? > utenti = sqlManager.getListVector(
				  "select syscon, sysute, syscf from usrsys where not (syspwbou like '%ou89|%') and syscon in (select syscon from usr_ein where codein=?) "
				  , new Object[] { stazioneAppaltante });

		  pageContext.setAttribute("utenti", utenti, PageContext.REQUEST_SCOPE);

	  } catch (SQLException e) {
		  throw new JspException("Errore nell'estrarre gli utenti associati alla stazione appaltante ", e);
	  } 
	  return null;
  }

}
