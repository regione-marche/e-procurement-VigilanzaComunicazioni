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

/**
 * Function per estrarre le categorie di qualificazione SOA.
 */
public class GetCatQualificazioneSoaFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GetCatQualificazioneSoaFunction() {
    super(1, new Class[] { String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
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

    try {
      Long codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      Long codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      Long numappa = (key.getColumnsBySuffix("NUM_APPA", true))[0].getValue().longValue();
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);

      List< ? > listaCatQualificazioneSoa = sqlManager.getListVector(
          "select CODGARA, CODLOTT, NUM_APPA, NUM_REQU, ID_CATEGORIA, CLASSE_IMPORTO, PREVALENTE, SCORPORABILE, SUBAPPALTABILE "
              + "from W9REQU where CODGARA = ? and CODLOTT = ? and NUM_APPA = ?",
          new Object[] { codgara, codlott, numappa });

      pageContext.setAttribute("listaCatQualificazioneSoa",
          listaCatQualificazioneSoa, PageContext.REQUEST_SCOPE);

    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre le categorie di qualificazione SOA", e);
    } catch (GestoreException g) {
      throw new JspException("Errore nel determinare i valori di COGDARA, CODLOTT e NUM_APPA", g);
    }
    return null;
  }

}
