package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Function che, a partire dalla chiave di USR_EIN, determina se il tecnico
 * associato all'utente nella USRSYS per la stazione appaltante e' un RUP di
 * qualche gara
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class CheckRUPFunction extends AbstractFunzioneTag {

  public CheckRUPFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  /**
   * @see it.eldasoft.gene.tags.utils.AbstractFunzioneTag#function(javax.servlet.jsp.PageContext,
   *      java.lang.Object[])
   */
  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {

    String isRUP = "false";

    SqlManager manager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    // lettura della chiave dell'utente da eliminare
    String key = (String)params[1];

    try {
      // estraggo i messaggi sul lavoro in input
      HashMap<String, JdbcParametro> map = UtilityTags.stringParamsToHashMap(
          key, null);
      String pkUffint = map.get("USR_EIN.CODEIN").getStringValue();
      String pkUsrsys = map.get("USR_EIN.SYSCON").getStringValue();

      Long numeroOccorrenze = (Long) manager.getObject("select count(codgara) "
          + "from tecni, w9gara "
          + "where tecni.syscon = ? "
          + "and tecni.cgentei = ? "
          + "and tecni.codtec = w9gara.rup", new Object[] {
          new Integer(pkUsrsys), pkUffint });
      if (numeroOccorrenze.longValue() > 0) isRUP = "true";

    } catch (NumberFormatException e) {
      throw new JspException(e);
    } catch (SQLException e) {
      throw new JspException(e);
    }

    return isRUP;
  }

}
