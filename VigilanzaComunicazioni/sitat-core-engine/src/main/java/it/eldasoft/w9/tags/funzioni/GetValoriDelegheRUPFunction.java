package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetValoriDelegheRUPFunction extends AbstractFunzioneTag {

  public GetValoriDelegheRUPFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    try {

    	 ProfiloUtente profilo = (ProfiloUtente) pageContext.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
         boolean isAdmin = profilo.getAbilitazioneStd().equals("A") || profilo.getAbilitazioneStd().equals("C");
         // Codice stazione appaltante
         String codein = (String) pageContext.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
         // Ricavo la lista Rup
         List<?> listaRUP;
         if (isAdmin) {
        	 listaRUP = sqlManager.getListVector(
        	          "select t.codtec, t.nomtec from tecni t left join usrsys u on t.syscon = u.syscon where cgentei = ? and u.syscateg = 1",
        	          new Object[] { codein });
         } else {
        	 listaRUP = sqlManager.getListVector(
       	          "select codtec, nomtec from tecni where syscon = ? and cgentei = ?",
       	          new Object[] { new Long(profilo.getId()), codein });
         }
         pageContext.setAttribute("listaRUP", listaRUP,
          PageContext.REQUEST_SCOPE);

    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrazione dei dati per la popolazione della/e dropdown list",
          e);
    }
    return null;
  }

}
