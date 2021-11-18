package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetDescFromArchFunction extends AbstractFunzioneTag {

  public GetDescFromArchFunction() {
    super(3,
        new Class[] { PageContext.class, JdbcParametro.class, String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    String codice = "";
    if (params[1] != null) {
      codice = params[1].toString();
    }
    String entita = params[2].toString();
    String descrizioneDaTecn = "";
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
      if (codice != null && !codice.equals("")) {
        List listaDescrizioneFromArch = null;
        if (entita.equals("W9INCA")) {
          listaDescrizioneFromArch = sqlManager.getListVector("select NOMTEC "
              + "from TECNI "
              + "where CODTEC = ?", new Object[] { codice });
        } else if (entita.equals("W9AGGI") || entita.equals("W9CANTIMP") || entita.equals("ESITI_AGGIUDICATARI")) {
          listaDescrizioneFromArch = sqlManager.getListVector("select NOMEST "
              + "from IMPR "
              + "where CODIMP = ?", new Object[] { codice });
        }
        if (listaDescrizioneFromArch.size() > 0) {
          descrizioneDaTecn = ((List) listaDescrizioneFromArch.get(0)).get(0).toString();
        }
      }
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre la descrizione ", e);
    }

    return descrizioneDaTecn;
  }

}
