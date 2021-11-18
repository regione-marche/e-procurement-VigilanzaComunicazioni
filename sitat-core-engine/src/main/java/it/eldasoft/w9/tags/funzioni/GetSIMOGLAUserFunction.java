package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;


public class GetSIMOGLAUserFunction extends AbstractFunzioneTag {

  public GetSIMOGLAUserFunction() {
    super(1, new Class[] { PageContext.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    
    ProfiloUtente profilo = (ProfiloUtente) this.getRequest().getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    Long syscon = new Long(profilo.getId());

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    
    String simoguser = "";
    
    try {
    	simoguser = (String) sqlManager.getObject("select simoguser from W9LOADER_APPALTO_USR where syscon = ?", new Object[] {syscon});
    } catch (SQLException e) {
      throw new JspException("Errore nella lettura della username dalla tabella W9LOADER_APPALTO_USR", e);
    }
      
    pageContext.setAttribute("simoguser",simoguser, PageContext.REQUEST_SCOPE);
    
    return null;
    
  }

}
