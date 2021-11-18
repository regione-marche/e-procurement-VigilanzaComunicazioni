package it.eldasoft.sil.w3.tags.funzioni;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

public class GetSIMOGWSUserFunction extends AbstractFunzioneTag {

  public GetSIMOGWSUserFunction() {
    super(1, new Class[] { PageContext.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {

    ProfiloUtente profilo = (ProfiloUtente) this.getRequest().getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    Long syscon = new Long(profilo.getId());

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    String simogwsuser = "";

    try {
      simogwsuser = (String) sqlManager.getObject(
          "select simogwsuser from w3usrsys where syscon = ? and simogwsuser is not null and simogwspass is not null",
          new Object[] { syscon });
    } catch (SQLException e) {
      throw new JspException(
          "Errore nella lettura della username dalla tabella W3USRSYS", e);
    }

    if (simogwsuser != null && simogwsuser.trim().length() > 0)
      pageContext.setAttribute("simogwsuser", simogwsuser,
          PageContext.REQUEST_SCOPE);

    return null;

  }

}
