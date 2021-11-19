package it.eldasoft.sil.pt.tags.funzioni;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

public class GetPubblicaFunction extends AbstractFunzioneTag {

  public GetPubblicaFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    String codice = params[1].toString();
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    String pubblica = "1";
    try {
      DataColumnContainer key = new DataColumnContainer(codice);
      Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);
      List<?> listaTiprog = null;

      listaTiprog = sqlManager.getListVector(
          "select PUBBLICA from PIATRI where CONTRI = ?", new Object[] { contri });
      pubblica = ((List<?>) listaTiprog.get(0)).get(0).toString();

    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre il campo pubblica", e);
    } catch (GestoreException e) {
      throw new JspException("Errore nell'estrarre il campo pubblica", e);
    }
    return pubblica;
  }

}
