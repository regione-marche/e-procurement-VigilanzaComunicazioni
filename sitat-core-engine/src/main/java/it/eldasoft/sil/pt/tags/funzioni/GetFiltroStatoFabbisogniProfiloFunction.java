package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Lettura del filtro sugli stati dei fabbisogni del profilo (tab2 PTz23)
 *
 * @author Mirco.Franzoni.
 */

public class GetFiltroStatoFabbisogniProfiloFunction extends AbstractFunzioneTag {

  public GetFiltroStatoFabbisogniProfiloFunction() {
    super(1, new Class[] { PageContext.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
	  
	  SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
		        pageContext, SqlManager.class);

	  String filtroStato="";
	  String profiloAttivo = (String) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);

	  try {
		  String descTabellato = (String) sqlManager.getObject(
		          "select tab2d2 from tab2 where tab2cod=? and tab2d1=?",
		          new Object[] { "PTz23", profiloAttivo });
		  if (descTabellato != null) {
			  filtroStato = descTabellato;
		  }
	  } catch (SQLException e) {
		  throw new JspException("Errore nella lettura del filtro sugli stati dei fabbisogni del profilo",e);
	  }

	  return filtroStato;
  }

}
