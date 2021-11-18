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
 * per una gara ed un lotto
 * 
 * Parametri:
 * 1 - PageContext
 * 2 - Codice della gara
 * 3 - Codice del lotto
 * 4 - Numero della fase da cercare
 * 
 * @author Stefano.Cestaro
 *
 */

public class VerificaEsistenzaFaseGaraLottoFunction extends
AbstractFunzioneTag {

  public VerificaEsistenzaFaseGaraLottoFunction() {
    super(4, new Class[] { PageContext.class, String.class, String.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

    String existsFaseGaraLotto = "false";

    try {
      Long codgara = new Long((String) params[1]);
      Long codlott = new Long((String) params[2]);
      Long faseEsecuzione = new Long((String) params[3]);
      Long numappa = (Long)sqlManager.getObject("select MAX(NUM_APPA) from W9APPA where CODGARA=? and CODLOTT=?", new Object[] { codgara, codlott });
      if (numappa == null) {
      	numappa = new Long(1);
      }
      if (UtilitySITAT.existsFase(codgara, codlott, numappa, faseEsecuzione.intValue(), sqlManager)) {
        existsFaseGaraLotto = "true";
      }
    } catch (SQLException e) {
      throw new JspException(
          "Errore nel controllo di esistenza delle fase sull'intera gara", e);
    }

    return existsFaseGaraLotto;

  }

}
