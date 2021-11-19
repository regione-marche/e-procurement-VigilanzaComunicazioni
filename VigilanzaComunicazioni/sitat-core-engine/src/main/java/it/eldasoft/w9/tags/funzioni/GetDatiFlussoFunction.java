package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Function per estrarre i campi AREA e KEY01 del flusso. 
 */
public class GetDatiFlussoFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GetDatiFlussoFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    String codice = params[1].toString();
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    if (codice != null && codice.indexOf("IDFLUSSO") != -1) {
      DataColumnContainer key = new DataColumnContainer(codice);
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);
      try {
        Long idFlusso = (key.getColumnsBySuffix("IDFLUSSO", true))[0].getValue().longValue();
        List< ? > listIdFlusso = sqlManager.getListVector(
            "select area, key01 from w9flussi where idflusso = ?",
            new Object[] { idFlusso });
        String area = "";
        String key01 = "";
        if (listIdFlusso.size() > 0
            && !((List< ? >) listIdFlusso.get(0)).get(0).toString().equals("")) {
          area = ((List< ? >) listIdFlusso.get(0)).get(0).toString();
          key01 = ((List< ? >) listIdFlusso.get(0)).get(1).toString();
        }
        pageContext.setAttribute("area", area, PageContext.PAGE_SCOPE);
        pageContext.setAttribute("key01", key01, PageContext.PAGE_SCOPE);

      } catch (SQLException e) {
        throw new JspException("Errore nell'estrarre i valori di w9flussi", e);
      } catch (GestoreException g) {
        throw new JspException("Errore nell'estrarre AREA e KEY01 del flusso", g);
      }
    }
    return null;
  }

}
