package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetDatiUffintFunction extends AbstractFunzioneTag {

  public GetDatiUffintFunction() {
    super(1, new Class[] { String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    String codein = params[0].toString();

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
      String sqlSelectValoriUffint = "select cfein from uffint where codein =?";
      String cfein = "";
      if (sqlManager.getObject(sqlSelectValoriUffint, new Object[] { codein }) != null) {
        cfein = sqlManager.getObject(sqlSelectValoriUffint,
            new Object[] { codein }).toString();
      }
      pageContext.setAttribute("cfein", cfein, PageContext.PAGE_SCOPE);

    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre i valori di uffint", e);
    }

    return null;
  }

}
