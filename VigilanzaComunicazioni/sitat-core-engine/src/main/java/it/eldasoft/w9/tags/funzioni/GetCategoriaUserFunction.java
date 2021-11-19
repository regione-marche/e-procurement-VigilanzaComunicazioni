package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetCategoriaUserFunction extends AbstractFunzioneTag {

  public GetCategoriaUserFunction() {
    super(1, new Class[] { PageContext.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    Long categoria = null;
    try {
    	 ProfiloUtente profilo = (ProfiloUtente) pageContext.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
         categoria = (Long) sqlManager.getObject("select syscateg from usrsys where syscon = ?", new Object[] {new Long(profilo.getId())});
    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrazione della categoria dell'utente",
          e);
    }
    //pageContext.setAttribute("categoriaUser", categoria.toString(), PageContext.REQUEST_SCOPE);
    if (categoria != null) {
    	return categoria.toString();
    } else {
    	return "0";
    }

  }

}
