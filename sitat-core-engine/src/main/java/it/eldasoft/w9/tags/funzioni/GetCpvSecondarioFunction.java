package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

public class GetCpvSecondarioFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GetCpvSecondarioFunction() {
    super(1, new Class[] { String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
	Long codgara = null;
	Long codlott = null;
    String codice = params[0].toString();
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    DataColumnContainer key = new DataColumnContainer(codice);
    
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
      codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      List< ? > listaCpvSecondario = sqlManager.getListVector(
          "select CODGARA, CODLOTT, NUM_CPV, CPV "
          + "from W9CPV where CODGARA = ? and CODLOTT = ? order by NUM_CPV", 
          new Object[] { codgara, codlott });

      pageContext.setAttribute("listaCpvSecondario", listaCpvSecondario, PageContext.REQUEST_SCOPE);

    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrarre i cpv secondari per il lotto con codgara:"
          + codgara
          + " e codlott:"
          + codlott, e);
    } catch (GestoreException g) {
      throw new JspException("Errore nel determinare i valori di COGDARA e CODLOTT", g);
    }

    return null;
  }

}
