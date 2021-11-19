package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.GeneManager;
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
 * Function per verifica della condizione.
 */
public class VerificaCondizioneDaTabellatoFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public VerificaCondizioneDaTabellatoFunction() {
    super(4, new Class[] { PageContext.class, String.class,
        JdbcParametro.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    String codice = params[1].toString();
    String valoreIngresso = params[2].toString();
    String entita = params[3].toString();
    String query = "";
    
    int numeroSqlParams = 0;
    
    if (entita.equals("W9COND")) {
      query = "select ID_CONDIZIONE from W9COND where CODGARA = ? AND CODLOTT = ?";
      numeroSqlParams = 2;
    } else if (entita.equals("W9LOTTCATE1")) {
      query = "select CATEGORIA from W9LOTTCATE where CODGARA = ? AND CODLOTT = ?";
      numeroSqlParams = 2;
    } else if (entita.equals("W9LOTTCATE2")) {
      query = "select CLASCAT from W9LOTTCATE where CODGARA = ? AND CODLOTT = ?";
      numeroSqlParams = 2;
    } else if (entita.equals("W9APPALAV")) {
      query = "select ID_APPALTO from W9APPALAV where CODGARA = ? AND CODLOTT = ?";
      numeroSqlParams = 2;
    } else if (entita.equals("W9CANTDEST")) {
      query = "select DEST from W9CANTDEST where CODGARA = ? AND CODLOTT = ? AND NUM_CANT = ?";
      numeroSqlParams = 3;
    } else {
      query = "select ID_APPALTO from W9APPAFORN where CODGARA = ? AND CODLOTT = ?";
      numeroSqlParams = 2;
    }

    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    DataColumnContainer key = new DataColumnContainer(codice);
    String esiste = "0";
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
    GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager", pageContext, GeneManager.class);
    try {
      Long codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      Long codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      Long numCant = null;
      if (numeroSqlParams == 3) {
        JdbcParametro tempPar = (key.getColumnsBySuffix("NUM_CANT", true))[0].getValue();
        if (tempPar != null && tempPar.getValue() != null) {
          numCant = tempPar.longValue();
        } else {
          numCant = new Long(1 + geneManager.countOccorrenze("W9CANTDEST", "CODGARA=? and CODLOTT=?", new Object[] { codgara, codlott }));
        }
      }
      List< ? > listaId = null;
      if (numeroSqlParams == 3) {
        listaId = sqlManager.getListVector(query, new Object[] { codgara, codlott, numCant });
      } else {
        listaId = sqlManager.getListVector(query, new Object[] { codgara, codlott });
      }
      if (listaId != null && listaId.size() > 0) {
        for (int i = 0; i < listaId.size(); i++) {
          if (valoreIngresso.equals(((List< ? >) listaId.get(i)).get(0).toString())) {
            esiste = "1";
            break;
          }
        }
      }
    } catch (SQLException e) {
      throw new JspException("Errore nel verificare l'esistenza di un valore ", e);
    } catch (GestoreException g) {
      throw new JspException("Errore nel determinare i valori di COGDARA e CODLOTT", g);
    }
    return esiste;
  }

}