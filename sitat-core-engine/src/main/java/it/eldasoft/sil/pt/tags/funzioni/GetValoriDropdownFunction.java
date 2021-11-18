package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetValoriDropdownFunction extends AbstractFunzioneTag {

  public GetValoriDropdownFunction() {
    super(1, new Class[] { String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    String entita = params[0].toString();
    try {

      if (entita != null && entita.equals("INTTRI")) {
        List<?> listaValoriCatint = sqlManager.getListVector(
            "select tabcod1,tabcod2,tabdesc from tabsche where tabcod=? order by tabcod2",
            new Object[] { "S2006" });

        List<?> listaValoriInterv = sqlManager.getListVector(
            "select tabcod1,tabcod2,tabdesc from tabsche where TABCOD=? and TABCOD2<> '0' order by tabcod1, tabcod2",
            new Object[] { "S2005" });

        List<?> listaValoriSezint = sqlManager.getListVector(
            "select tabcod1,tabdesc from tabsche where TABCOD=? order by tabcod1",
            new Object[] { "S2008" });

        List<?> listaValoriFiintr = sqlManager.getListVector(
            "select tabcod1,tabdesc from tabsche where TABCOD=? order by tabcod3",
            new Object[] { "S2016" });

        List<?> listaValoriStapro = sqlManager.getListVector(
            "select tabcod1,tabdesc from tabsche where TABCOD=? order by tabcod3",
            new Object[] { "S2017" });

        List<?> listaValoriTcpint = sqlManager.getListVector(
            "select tabcod1,tabdesc from tabsche where TABCOD=? order by tabcod1",
            new Object[] { "S2015" });

        pageContext.setAttribute("listaValoriCatint", listaValoriCatint,
            PageContext.REQUEST_SCOPE);

        pageContext.setAttribute("listaValoriInterv", listaValoriInterv,
            PageContext.REQUEST_SCOPE);

        pageContext.setAttribute("listaValoriSezint", listaValoriSezint,
            PageContext.REQUEST_SCOPE);

        pageContext.setAttribute("listaValoriFiintr", listaValoriFiintr,
            PageContext.REQUEST_SCOPE);

        pageContext.setAttribute("listaValoriStapro", listaValoriStapro,
            PageContext.REQUEST_SCOPE);

        pageContext.setAttribute("listaValoriTcpint", listaValoriTcpint,
            PageContext.REQUEST_SCOPE);
      }
     
     
     } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrazione dei dati per la popolazione della/e dropdown list",
          e);
    }
    return null;
  }

}
