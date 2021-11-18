package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.PlugInBase;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Function per estrarre i valori del tabellato W3998. 
 */
public class GetValoriDropdownFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public GetValoriDropdownFunction() {
    super(1, new Class[] { String.class });
  }

  /**
   * Implementazione del metodo AbstractFunzioneTag.function.
   * 
   * @param pageContext PageContext
   * @param params Array di parametri
   * @return Ritorna sempre null. Le inizializzazioni vengono inserite nel request.
   * @throws JspException JspException
   */
  @Override
  public String function(final PageContext pageContext, final Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    String entita = params[0].toString();
    
    try {
      if (entita != null && entita.equals("FLUSSI")) {
        List< ? > listaValoriTinvio2 = null;
        
        String strSql = "SELECT TAB1TIP, TAB1DESC FROM TAB1 WHERE TAB1COD='W3998'";
        
        if (PlugInBase.isSviluppo()) {
          strSql = strSql.concat(" AND TAB1TIP NOT IN (1, 99) ");
        } else {
          strSql = strSql.concat(" AND TAB1TIP NOT IN (99) ");
        }
        
        listaValoriTinvio2 = sqlManager.getListVector(strSql, null);
        
        pageContext.setAttribute("listaValoriTinvio2", listaValoriTinvio2, PageContext.REQUEST_SCOPE);
      }
    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrazione dei dati per la popolazione della/e dropdown list", e);
    }
    return null;
  }

}
