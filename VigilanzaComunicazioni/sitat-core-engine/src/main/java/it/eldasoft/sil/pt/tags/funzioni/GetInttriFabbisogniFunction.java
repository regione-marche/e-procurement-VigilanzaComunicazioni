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

public class GetInttriFabbisogniFunction extends AbstractFunzioneTag {

  public GetInttriFabbisogniFunction() {
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
    String tipint = "";
    try {
      DataColumnContainer key = new DataColumnContainer(codice);
      Long contri = new Long(0);
      Long conint = (key.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);
      Vector<?> listaDatiINTTRI = null;

      listaDatiINTTRI = sqlManager.getVector(
          "select TIPINT,STATO,AILINT,QE_SL from INTTRI,FABBISOGNI " +
          " where INTTRI.CONINT=FABBISOGNI.CONINT" +
          " and INTTRI.CONTRI = ? and INTTRI.CONINT = ? ", new Object[] { contri,conint });
      tipint = listaDatiINTTRI.get(0).toString();
      String stato  = listaDatiINTTRI.get(1).toString();
      pageContext.setAttribute("stato", stato,PageContext.REQUEST_SCOPE);
      String ailint  = listaDatiINTTRI.get(2).toString();
      pageContext.setAttribute("ailint", ailint,PageContext.REQUEST_SCOPE);
      String qe_sl  = listaDatiINTTRI.get(3).toString();
      pageContext.setAttribute("qe_sl", qe_sl,PageContext.REQUEST_SCOPE);
      
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrazione dei campi di INNTRI e FABBISOGNI", e);
    } catch (GestoreException e) {
      throw new JspException("Errore nell'estrazione dei campi di INNTRI e FABBISOGNI", e);
    }
    return tipint;
  }

}
