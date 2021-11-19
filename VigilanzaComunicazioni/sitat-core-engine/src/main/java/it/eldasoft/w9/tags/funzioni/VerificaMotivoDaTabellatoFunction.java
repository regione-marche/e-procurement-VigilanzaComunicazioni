package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Function per la verifica del motivo del tabellato.
 */
public class VerificaMotivoDaTabellatoFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public VerificaMotivoDaTabellatoFunction() {
    super(4, new Class[] { PageContext.class, String.class,
        JdbcParametro.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    String codice = params[1].toString();
    String valoreIngresso = params[2].toString();
    String query = "select ID_MOTIVO_VAR from W9MOTI where CODGARA = ? AND CODLOTT = ? AND NUM_VARI = ?";

    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    DataColumnContainer key = new DataColumnContainer(codice);
    String esiste = "";
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
        Long codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
        Long codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
        Long numvari = (key.getColumnsBySuffix("NUM_VARI", true))[0].getValue().longValue();
      List< ? > listaId = sqlManager.getListVector(query, new Object[] { codgara,
          codlott, numvari });
      if (listaId != null && listaId.size() > 0) {
        for (int i = 0; i < listaId.size(); i++) {
          if (valoreIngresso.equals(((List< ? >) listaId.get(i)).get(0).toString())) {
            esiste = "1";
            break;
          } else {
            esiste = "";
          }
        }
      }
    } catch (SQLException e) {
      throw new JspException("Errore nel verificare l'esistenza di un valore ", e);
    } catch (GestoreException g) {
      throw new JspException("Errore nel determinare i valori di COGDARA, CODLOTT e NUM_VARI", g);
    }
    return esiste;
  }

}