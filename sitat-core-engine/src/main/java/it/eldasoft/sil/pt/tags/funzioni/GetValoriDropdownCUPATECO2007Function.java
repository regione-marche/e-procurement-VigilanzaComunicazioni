package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetValoriDropdownCUPATECO2007Function extends AbstractFunzioneTag {

  public GetValoriDropdownCUPATECO2007Function() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    try {

      List<?> listaValoriSezione = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupcod3, cupdesc from cuptab where cupcod = ? order by cupdesc",
          new Object[] { "CU008" });

      List<?> listaValoriDivisione = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupcod3, cupdesc from cuptab where cupcod = ? order by cupdesc",
          new Object[] { "CU009" });

      List<?> listaValoriGruppo = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupcod3, cupdesc from cuptab where cupcod = ? order by cupdesc",
          new Object[] { "CU010" });

      List<?> listaValoriClasse = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupcod3, cupdesc from cuptab where cupcod = ? order by cupdesc",
          new Object[] { "CU011" });

      List<?> listaValoriCategoria = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupcod3, cupdesc from cuptab where cupcod = ? order by cupdesc",
          new Object[] { "CU012" });

      List<?> listaValoriSottoCategoria = sqlManager.getListVector(
          "select cupcod1, cupcod2, cupcod3, cupdesc from cuptab where cupcod = ? order by cupdesc",
          new Object[] { "CU013" });

      pageContext.setAttribute("listaValoriSezione", listaValoriSezione,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriDivisione", listaValoriDivisione,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriGruppo", listaValoriGruppo,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriClasse", listaValoriClasse,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriCategoria", listaValoriCategoria,
          PageContext.REQUEST_SCOPE);

      pageContext.setAttribute("listaValoriSottoCategoria", listaValoriSottoCategoria,
          PageContext.REQUEST_SCOPE);

    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrazione dei dati per la popolazione della/e dropdown list",
          e);
    }
    return null;
  }

}
