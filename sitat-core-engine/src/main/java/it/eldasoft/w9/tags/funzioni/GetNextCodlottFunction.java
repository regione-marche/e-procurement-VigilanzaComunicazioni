package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/** 
 * Function per estrarre il max(codlott)+1.
 */
public class GetNextCodlottFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GetNextCodlottFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    Long codgara = new Long(params[1].toString());
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
      List listCount = null;

      listCount = sqlManager.getListVector(
          "select max(codlott) from w9lott where codgara = ?",
          new Object[] { codgara });
      int count = 1;
      if (listCount.size() > 0
          && !((List) listCount.get(0)).get(0).toString().equals("")) {
        count = 1 + Integer.parseInt(((List) listCount.get(0)).get(0).toString());
      }
      pageContext.setAttribute("count", count, PageContext.REQUEST_SCOPE);

    } catch (SQLException e) {
      throw new JspException("Errore nel calcolare il valore conint", e);
    }
    return null;
  }

}
