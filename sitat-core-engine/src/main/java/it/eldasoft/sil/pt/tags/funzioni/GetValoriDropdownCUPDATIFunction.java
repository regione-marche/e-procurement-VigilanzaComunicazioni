package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetValoriDropdownCUPDATIFunction extends AbstractFunzioneTag {

  public GetValoriDropdownCUPDATIFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    try {

      List<?> listaValoriNatura = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupdesc from cuptab where cupcod = ? and cupcod2 = '--' order by cupcod2",
          new Object[] { "CU001" });

      List<?> listaValoriTipologia = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupdesc from cuptab where cupcod = ? and cupcod2 <> '--' order by cupcod2",
          new Object[] { "CU001" });

      List<?> listaValoriSettore = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupcod3, cupdesc from cuptab where cupcod = ? and cupcod2 = '00' order by cupcod2",
          new Object[] { "CU002" });

      List<?> listaValoriSottosettore = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupcod3, cupdesc from cuptab where cupcod = ? and cupcod2 <> '00' and cupcod3 = '000' order by cupcod2",
          new Object[] { "CU002" });

      List<?> listaValoriCategoria = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupcod3, cupdesc from cuptab where cupcod = ? and cupcod2 <> '00' and cupcod3 <> '000' order by cupcod2",
          new Object[] { "CU002" });

      List<?> listaValoriTipoIndirizzo = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupdesc from cuptab where cupcod = ?  order by cupcod1",
          new Object[] { "CU006" });

      List<?> listaValoriStrumentoProgrammazione = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupdesc from cuptab where cupcod = ?  order by cupcod1",
          new Object[] { "CU007" });


      pageContext.setAttribute("listaValoriNatura", listaValoriNatura,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriTipologia", listaValoriTipologia,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriSettore", listaValoriSettore,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriSottosettore", listaValoriSottosettore,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriCategoria", listaValoriCategoria,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriTipoIndirizzo", listaValoriTipoIndirizzo,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriStrumentoProgrammazione", listaValoriStrumentoProgrammazione,
          PageContext.REQUEST_SCOPE);
      
      GregorianCalendar gc = new GregorianCalendar();
      int annoCorrente = gc.get(Calendar.YEAR);
      List<Long> anniDiDecisione = new ArrayList<Long>();
      for (int i = 0; annoCorrente - i >= 2007; i++) {
        anniDiDecisione.add(new Long(annoCorrente - i));
      }
      pageContext.setAttribute("anniDiDecisione", anniDiDecisione);

    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrazione dei dati per la popolazione della/e dropdown list",
          e);
    }
    return null;
  }

}
