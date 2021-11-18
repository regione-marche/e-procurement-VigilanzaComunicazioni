package it.eldasoft.sil.pt.tags.funzioni;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.spring.UtilitySpring;

public class GetNextConintFunction extends AbstractFunzioneTag {

  public GetNextConintFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  public String function(PageContext pageContext, Object[] params)
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
    DataColumnContainer key = new DataColumnContainer(codice);
    try {
    Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    
      List<?> listCount = null;

      listCount = sqlManager.getListVector(
          "select max(conint) from inttri where contri = ?",
          new Object[] { contri });
      int count = 1;
      if (listCount.size() > 0
          && !((List<?>) listCount.get(0)).get(0).toString().equals("")) {
        count = 1 + Integer.parseInt(((List<?>) listCount.get(0)).get(0).toString());
      }
      int nprogint = Integer.parseInt(sqlManager.getObject("select count(*) from inttri where contri = ? ", new Object[] { contri }).toString());
      pageContext.setAttribute("count", count, PageContext.REQUEST_SCOPE);
      pageContext.setAttribute("nprogint", nprogint + 1, PageContext.REQUEST_SCOPE);

    } catch (Exception e) {
      throw new JspException("Errore nel calcolare il valore conint", e);
    }
    return null;
  }

}
