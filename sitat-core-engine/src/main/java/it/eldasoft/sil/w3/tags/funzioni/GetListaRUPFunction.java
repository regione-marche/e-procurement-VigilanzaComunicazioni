package it.eldasoft.sil.w3.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetListaRUPFunction extends AbstractFunzioneTag {

  public GetListaRUPFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    try {

    	 ProfiloUtente profilo = (ProfiloUtente) pageContext.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
         Long syscon = new Long(profilo.getId());
         // Codice stazione appaltante
         String codein = (String) pageContext.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
         // Ricavo la lista Rup
         List<?> listaRUP;
         listaRUP = sqlManager.getListVector(
   	          "select RUP_CODTEC, SIMOGWSUSER from W3USRSYS where SYSCON = ? and SIMOGWSUSER is not null and RUP_CODTEC in (SELECT CODTEC FROM TECNI WHERE CGENTEI = ?)",
   	          new Object[] { syscon, codein });
         
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
