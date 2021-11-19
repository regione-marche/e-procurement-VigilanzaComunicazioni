package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * 
 * @author Mirco 18.07.2012
 *
 * dato il numero del programma ritorna il suo stato
 */

public class GetStatoProgrammaFunction extends AbstractFunzioneTag {

	/**
	 * 
	 */
  public GetStatoProgrammaFunction() {
	  super(2, new Class[] { PageContext.class, String.class });
  }

  /**
   * @param pageContext pageContext
   * @param params codice del programma
   * 
   * @return return
   * @throws JspException JspException
   */
  public final String function(final PageContext pageContext, final Object[] params)
      throws JspException {

	  	String codice = params[1].toString();
	    if (codice == null || codice.equals("")) {
	      codice = (String) UtilityTags.getParametro(pageContext,
	          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
	    }
	    if (codice == null || codice.equals("")) {
	      codice = (String) UtilityTags.getParametro(pageContext,
	          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
	    }
	    String stato = "";
	    try {
	      DataColumnContainer key = new DataColumnContainer(codice);
	      Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
	      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
	          pageContext, SqlManager.class);
	      List statoTiprog = null;

	      statoTiprog = sqlManager.getListVector(
	          "select STATRI from PIATRI where CONTRI = ?", new Object[] { contri });
	      stato = ((List) statoTiprog.get(0)).get(0).toString();

	    } catch (SQLException e) {
	      throw new JspException("Errore nell'estrarre il campo stato del programma", e);
	    } catch (GestoreException e) {
	      throw new JspException("Errore nell'estrarre il campo stato del programma", e);
	    }
	    return stato;
  }

}
