package it.eldasoft.sil.pt.tags.funzioni;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;


public class GetCUPWSUserFunction extends AbstractFunzioneTag {

  public GetCUPWSUserFunction() {
    super(1, new Class[] { PageContext.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    
    ProfiloUtente profilo = (ProfiloUtente) this.getRequest().getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    Long syscon = new Long(profilo.getId());

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    
    String cupwsuser = "";
    
    try {
      cupwsuser = (String) sqlManager.getObject("select cupwsuser from cupusrsys where syscon = ?", new Object[] {syscon});
    } catch (SQLException e) {
      throw new JspException("Errore nella lettura della username dalla tabella CUPUSRSYS", e);
    }
      
    pageContext.setAttribute("cupwsuser",cupwsuser, PageContext.REQUEST_SCOPE);
    
    return null;
    
  }

}
