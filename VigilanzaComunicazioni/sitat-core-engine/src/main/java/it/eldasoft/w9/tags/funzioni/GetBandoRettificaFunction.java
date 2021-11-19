package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetBandoRettificaFunction extends AbstractFunzioneTag {

  public GetBandoRettificaFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {
    String codice = params[1].toString();
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    DataColumnContainer key = new DataColumnContainer(codice);
    String key01 = (key.getColumnsBySuffix("KEY01", true))[0].getValue().toString();
    String key03 = (key.getColumnsBySuffix("KEY03", true))[0].getValue().toString();
    String area = (key.getColumnsBySuffix("AREA", true))[0].getValue().toString();
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
		pageContext, SqlManager.class);
    
    List< ? > listIdFlusso = null;
    try {
      if (key03.equals("990") || key03.equals("988") || key03.equals("13") || key03.equals("983")) {
        listIdFlusso = sqlManager.getListVector(
            "select IDFLUSSO from W9FLUSSI where AREA=? and KEY01=? and KEY03=?",
            new Object[] { Long.parseLong(area), Long.parseLong(key01), Long.parseLong(key03) });
      } else if (key03.equals("901")) {
        String key04 = (key.getColumnsBySuffix("KEY04", true))[0].getValue().toString();
        listIdFlusso = sqlManager.getListVector(
            "select IDFLUSSO from W9FLUSSI where AREA=? and KEY01=? and KEY03=? and key04=?",
            new Object[] { Long.parseLong(area), Long.parseLong(key01), Long.parseLong(key03), Long.parseLong(key04) });
      }
      
      if (listIdFlusso != null && listIdFlusso.size() > 0) {
        return "2";
      } else {
        return "1";
      }
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre i valori di w9flussi", e);
    }
  }

}
