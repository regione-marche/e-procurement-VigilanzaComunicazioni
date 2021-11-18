package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * Ritorna il numero di Avanzamento lavori essitenti per il lotto. 
 * 
 * @author luca.giacomazzo
 */
public class GetNumeroSalFunction extends AbstractFunzioneTag {

  public GetNumeroSalFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
    
    String key = params[1].toString();
    Long numeroSal = null;
    String aggiudicazioneAttiva = (String)pageContext.getSession().getAttribute("aggiudicazioneSelezionata");
    try {
      DataColumnContainer keys = new DataColumnContainer(key);
      Long codgara = null;
      Long codlotto = null;
      Long numappa = null;
      Long numavan = null;
      codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      codlotto = (keys.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      if (aggiudicazioneAttiva == null) {
    	  numavan = (keys.getColumnsBySuffix("NUM_AVAN", true))[0].getValue().longValue();
    	  numappa = (Long) sqlManager.getObject("select NUM_APPA from W9AVAN where CODGARA=? and CODLOTT=? and NUM_AVAN=?",
    	          new Object[] { codgara, codlotto, numavan });
      } else {
    	  numappa = new Long(aggiudicazioneAttiva);
      }
      
      numeroSal = (Long) sqlManager.getObject("select count(*) from W9AVAN where CODGARA=? and CODLOTT=? and NUM_APPA=?",
	          new Object[] { codgara, codlotto, numappa });

    } catch (SQLException sqle) {
      throw new JspException(
          "Errore in fase di esecuzione della query per determinare il permesso dell'utente sulla gara/lotto in analisi",
          sqle);
    } catch (Exception exc) {
      throw new JspException(
          "Errore in fase di esecuzione della query per determinare il permesso dell'utente sulla gara/lotto in analisi",
          exc);
    }
    // TODO Auto-generated method stub
    if (numeroSal != null)
      return numeroSal.toString();
    else
      return null;
  }

}
