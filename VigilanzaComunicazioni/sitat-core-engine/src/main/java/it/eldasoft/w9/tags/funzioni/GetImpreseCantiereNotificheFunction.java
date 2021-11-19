package it.eldasoft.w9.tags.funzioni;

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

public class GetImpreseCantiereNotificheFunction extends AbstractFunzioneTag {

  /* Costruttore. */
  public GetImpreseCantiereNotificheFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {

    String codice = params[0].toString();
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    DataColumnContainer key = new DataColumnContainer(codice);
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
      Long codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      Long codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      Long numCant = (key.getColumnsBySuffix("NUM_CANT", true))[0].getValue().longValue();
      List< ? > listaImpreseCantiereNotifiche = sqlManager.getListVector(
          "select CODGARA, CODLOTT, NUM_CANT, NUM_IMP, CODIMP, CIPDURC, PROTDURC, DATDURC "
          + "from W9CANTIMP "
          + "where CODGARA = ? and CODLOTT = ? and NUM_CANT = ? order by NUM_IMP",
          new Object[] { codgara, codlott, numCant });

      pageContext.setAttribute("listaImpreseCantiereNotifiche",
          listaImpreseCantiereNotifiche, PageContext.REQUEST_SCOPE);

    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre imprese aggiudicatarie ", e);
    } catch (GestoreException g) {
      throw new JspException("Errore nel determinare i valori di COGDARA, CODLOTT e NUM_APPA per le imprese aggiudicatarie", g);
    }
    return null;
  }

}
