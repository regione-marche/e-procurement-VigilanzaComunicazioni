package it.eldasoft.sil.pt.tags.funzioni;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

public class IsNotPubblicatoFunction extends AbstractFunzioneTag {

  public IsNotPubblicatoFunction() {
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
    String pubblicato = "true";
    try {
      DataColumnContainer key = new DataColumnContainer(codice);
      Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);

      Long righe = (Long)sqlManager.getObject("Select count(*) from ptflussi where key01 = ?", new Object[] { contri });
      if (righe > 0) {
    	  pubblicato = "false";
      }

    } catch (SQLException e) {
      throw new JspException("Errore nel recupero dei flussi", e);
    } catch (GestoreException e) {
      throw new JspException("Errore nel recupero dei flussi", e);
    }
    return pubblicato;
  }

}
