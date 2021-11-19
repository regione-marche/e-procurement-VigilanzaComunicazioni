package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * 
 * @author Luca.Giacomazzo
 */
public class GetIncarichiProfessionaliFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public GetIncarichiProfessionaliFunction() {
    super(1, new Class[] { String.class });
  }

  public String function(PageContext pageContext, Object[] params) throws JspException {
    String codice = params[0].toString();
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    DataColumnContainer key = new DataColumnContainer(codice);
    Long codgara = (Long) (key.getColumnsBySuffix("CODGARA", true))[0].getValue().getValue();
    Long codlott = (Long) (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().getValue();
    String sezione = (key.getColumnsBySuffix("SEZIONE", true))[0].getValue().toString();
    
    Long num = null;
    if (key.isColumn("NUM_APPA")) {
    	num = (Long) (key.getColumnsBySuffix("NUM_APPA", true))[0].getValue().getValue();
    } else if (key.isColumn("NUM_INIZ")) {
        num = (Long) (key.getColumnsBySuffix("NUM_INIZ", true))[0].getValue().getValue();
    } else if (key.isColumn("NUM_CANT")) {
    	num = (Long) (key.getColumnsBySuffix("NUM_CANT", true))[0].getValue().getValue();
    } else if (key.isColumn("NUM_COLL")) {
    	num = (Long) (key.getColumnsBySuffix("NUM_COLL", true))[0].getValue().getValue();
    }
    
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
    try {
      if ("RR".equals(sezione)) {
        String artE1 = (String) sqlManager.getObject(
            "select ART_E1 from W9LOTT where CODGARA=? and CODLOTT=?",
            new Object[]{codgara, codlott});

        if ("1".equals(artE1)) {
          sezione = "RE";
        } else {
          sezione = "RS";
        }
      }

      List< ? > listaIncarichiProfessionali = null;
      if (sezione.equals("PA")) {
        listaIncarichiProfessionali = sqlManager.getListVector(
            "select CODGARA, CODLOTT, NUM, SEZIONE, NUM_INCA, ID_RUOLO, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, DATA_CONS_PROG_ESTERNA, CODTEC "
            + "from W9INCA "
            + "where CODGARA = ? "
            + "and CODLOTT = ? and NUM = ? and (SEZIONE = 'PA' OR SEZIONE = 'RA') order by NUM_INCA",
            new Object[]{codgara, codlott, num});
      } else if (sezione.equals("RA")) {
        
        String selectW9Inca = "select CODGARA, CODLOTT, NUM, SEZIONE, NUM_INCA, ID_RUOLO, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, DATA_CONS_PROG_ESTERNA, CODTEC "
          + "from W9INCA where CODGARA = ? and CODLOTT = ? and NUM = ? and SEZIONE = ?";
        
        String isLotto = (String) params[params.length-1];
        if (StringUtils.contains(isLotto, "ISLOTTO")) {
          selectW9Inca = selectW9Inca.concat(" and ID_RUOLO in (14,15,16,17)");
        }
        
        selectW9Inca = selectW9Inca.concat(" order by NUM_INCA");
        listaIncarichiProfessionali = sqlManager.getListVector(selectW9Inca,
              new Object[]{codgara, codlott, num, sezione});
      } else {
        listaIncarichiProfessionali = sqlManager.getListVector(
            "select CODGARA, CODLOTT, NUM, SEZIONE, NUM_INCA, ID_RUOLO, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, DATA_CONS_PROG_ESTERNA, CODTEC "
            + "from W9INCA "
            + "where CODGARA = ? "
            + "and CODLOTT = ? and NUM = ? and SEZIONE = ? order by NUM_INCA",
            new Object[]{codgara, codlott, num, sezione});
      }
      
      pageContext.setAttribute("listaIncarichiProfessionali", listaIncarichiProfessionali,
          PageContext.REQUEST_SCOPE);
      pageContext.setAttribute("sezioneArtE1", sezione, PageContext.REQUEST_SCOPE);
    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrarre gli incarichi professionali ", e);
    }
    return null;
  }

}