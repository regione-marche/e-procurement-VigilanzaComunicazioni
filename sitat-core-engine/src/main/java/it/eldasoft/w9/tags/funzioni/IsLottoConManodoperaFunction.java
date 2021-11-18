package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Lettura del campo W9LOTT.MANOD per determinare se il lotto di gara prevede manodopera o meno.
 * 
 * @author Luca.Giacomazzo
 */
public class IsLottoConManodoperaFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public IsLottoConManodoperaFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    String result = "false";

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

    String[] par = ((String) params[0]).split(";");

    try {
      String manod = (String) sqlManager.getObject(
          "select MANOD from W9LOTT where CODGARA=? and CODLOTT=?",
          new Object[]{new Long(par[0]), new Long(par[1])});

      if ("1".equals(manod)) {
        result = "true";
      }
    } catch (NumberFormatException e) {
      throw new JspException("Errore nell'estrazione dalla base dati del campo W9LOTT.MANOD", e);
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrazione dalla base dati del campo W9LOTT.MANOD", e);
    }
    pageContext.setAttribute("isLottoConManodopera", result, PageContext.REQUEST_SCOPE);
    return null;
  }

}
