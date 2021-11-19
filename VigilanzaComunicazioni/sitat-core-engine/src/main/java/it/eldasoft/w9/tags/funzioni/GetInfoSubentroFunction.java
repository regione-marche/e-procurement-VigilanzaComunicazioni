package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONArray;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * Function che estrapola le informazioni per la maschera di subentro. 
 */
public class GetInfoSubentroFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GetInfoSubentroFunction() {
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
    
		  Long recordPresenti = (Long)sqlManager.getObject("select count(*) from piatri where cenint = ?", new Object[] { stazioneAppaltante });
		  pageContext.setAttribute("programmi", !recordPresenti.equals(new Long(0)), PageContext.REQUEST_SCOPE);
		  
		  recordPresenti = (Long)sqlManager.getObject("select count(*) from avviso where codein = ?", new Object[] { stazioneAppaltante });
		  pageContext.setAttribute("avvisi", !recordPresenti.equals(new Long(0)), PageContext.REQUEST_SCOPE);
		  
		  recordPresenti = (Long)sqlManager.getObject("select count(*) from w9gara where codein = ?", new Object[] { stazioneAppaltante });
		  pageContext.setAttribute("bandi", !recordPresenti.equals(new Long(0)), PageContext.REQUEST_SCOPE);
		  
		  String cfein = (String)sqlManager.getObject("select cfein from uffint where codein = ?", new Object[] { stazioneAppaltante });
		  pageContext.setAttribute("cfUffint", cfein, PageContext.REQUEST_SCOPE);

		  List< ? > utenti = sqlManager.getListVector(
				  "select syscon,sysute,syscf from usrsys where not (syspwbou like '%ou89|%') and syscon in (select syscon from usr_ein where codein=?) "
				  , new Object[] { stazioneAppaltante });
		  
		  String[] utentiArray = new String[utenti.size()];
		  for (int i = 0; i < utenti.size(); i++) {
			  utentiArray[i] = SqlManager.getValueFromVectorParam(utenti.get(i), 0).getStringValue();
			  utentiArray[i] += ";" + SqlManager.getValueFromVectorParam(utenti.get(i), 1).getStringValue();
			  utentiArray[i] += ";" + SqlManager.getValueFromVectorParam(utenti.get(i), 2).getStringValue();
		  }
		  JSONArray jsonUtenti = JSONArray.fromObject(utentiArray);
		  
		  pageContext.setAttribute("utenti", jsonUtenti, PageContext.REQUEST_SCOPE);

	  } catch (SQLException e) {
		  throw new JspException("Errore nell'estrarre le informazioni per la maschera di subentro ", e);
	  } 
	  return null;
  }

}
