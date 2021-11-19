package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetTiprogFunction extends AbstractFunzioneTag {

  public GetTiprogFunction() {
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
    String tiprog = "";
    String anntri = "";
    String id = "";
    String aggiornamento = "";
    try {
      DataColumnContainer key = new DataColumnContainer(codice);
      Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);
      Vector<?> listaTiprog = null;

      listaTiprog = sqlManager.getVector(
          "select TIPROG, ANNTRI, ID from PIATRI where CONTRI = ?", new Object[] { contri });
      if(listaTiprog!= null){
        tiprog = listaTiprog.get(0).toString();
        anntri = listaTiprog.get(1).toString();
        id = listaTiprog.get(2).toString();
        if (id.length() == 20) {
            aggiornamento = id.substring(17);
        }
        pageContext.setAttribute("anntri", anntri);
        pageContext.setAttribute("aggiornamento", aggiornamento);
      }
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre il campo tiprog", e);
    } catch (GestoreException e) {
      throw new JspException("Errore nell'estrarre il campo tiprog", e);
    }
    return tiprog;
  }

}
