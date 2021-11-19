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

/**
 * Function per estrarre le categorie del lotto. 
 */
public class GetCategorieFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GetCategorieFunction() {
    super(1, new Class[] { String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    String codice = params[0].toString();
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    
    try {
      DataColumnContainer key = new DataColumnContainer(codice);
      Long codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      Long codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);
    
      List< ? > listaCategorie = sqlManager.getListVector(
          "select CODGARA, CODLOTT, NUM_CATE, CATEGORIA, CLASCAT, SCORPORABILE, SUBAPPALTABILE "
              + "from W9LOTTCATE where CODGARA = ? and CODLOTT = ? ",
          new Object[] { codgara, codlott });

      pageContext.setAttribute("listaCategorie",
          listaCategorie, PageContext.REQUEST_SCOPE);

    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre le categorie ", e);
    } catch (GestoreException g) {
      throw new JspException("Errore nel determinare i valori di COGDARA e CODLOTT", g);
    }
    return null;
  }

}
