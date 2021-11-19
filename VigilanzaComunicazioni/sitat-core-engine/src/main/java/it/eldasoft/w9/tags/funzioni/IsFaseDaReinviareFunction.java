package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.utils.UtilitySITAT;

/**
 * Function per determinare se una fase e' da reinviare o meno.
 * 
 * @author Luca.Giacomazzo
 */
public class IsFaseDaReinviareFunction extends AbstractFunzioneTag {

  /**
   * Costruttore di default.
   */
  public IsFaseDaReinviareFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {

    // params deve essere nel formato codgara;codlott;faseEsec o codgara;codlott;faseEsec;numFase 
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    
    String key = (String) params[1];
    String[] tmp = key.split(";");
    
    boolean isFaseDaReinviare = false;
    try {
      Long codgara = new Long(tmp[0]);
      Long codlott = new Long(tmp[1]);
      Long faseEsecuzione = new Long(tmp[2]);

      if (tmp.length == 3) {
        isFaseDaReinviare = UtilitySITAT.isFaseDaReInviare(
            codgara, codlott, faseEsecuzione.intValue(), sqlManager);
      } else {
        Long num = new Long(tmp[3]);
        isFaseDaReinviare = UtilitySITAT.isFaseDaReInviare(
            codgara, codlott, faseEsecuzione.intValue(), num, sqlManager);
      }
    } catch(SQLException se) {
      throw new JspException("Errore nel determinare se la fase e' da reinviare o meno.", se);
    }
    return "" + isFaseDaReinviare;
  }
  
}
