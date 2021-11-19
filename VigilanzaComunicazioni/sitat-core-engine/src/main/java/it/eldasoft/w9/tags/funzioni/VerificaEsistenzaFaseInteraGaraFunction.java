package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Verifica l'esistenza di una particolare fase
 * per tutta la gara (indipendentemente dal lotto)
 * 
 * Parametri:
 * 1 - PageContext
 * 2 - Codice della gara
 * 3 - Numero della fase da cercare
 * 
 * @author Stefano.Cestaro
 *
 */

public class VerificaEsistenzaFaseInteraGaraFunction extends AbstractFunzioneTag {

  public VerificaEsistenzaFaseInteraGaraFunction() {
    super(3, new Class[] { PageContext.class, String.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
    String existsFaseGara = "false";

    try {
      Long codgara = new Long((String) params[1]);
      Long faseEsecuzione = new Long((String) params[2]);
      if (UtilitySITAT.existsFaseInteraGara(codgara, faseEsecuzione.intValue(), sqlManager)) {
        existsFaseGara = "true";
      }
    } catch (SQLException e) {
      throw new JspException(
          "Errore nel controllo di esistenza delle fase dell'intera gara", e);
    }
    return existsFaseGara;
  }

}
