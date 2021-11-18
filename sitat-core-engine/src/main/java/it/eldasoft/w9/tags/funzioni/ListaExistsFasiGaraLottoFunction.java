package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Lista fasi esistenti di una
 * gara ed un lotto
 * 
 * 
 * @author Stefano.Cestaro
 *
 */

public class ListaExistsFasiGaraLottoFunction extends
AbstractFunzioneTag {

  public ListaExistsFasiGaraLottoFunction() {
    super(3, new Class[] { PageContext.class, String.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

    try {
      if (params[1] != "" && params[2] != "") {
        Long codgara = new Long((String) params[1]);
        Long codlott = new Long((String) params[2]);
        List<Object[]> listaExistsFasiGaraLotto = UtilitySITAT.listaExistsFasi(codgara, codlott, sqlManager);
        for (int i = 0; i < listaExistsFasiGaraLotto.size(); i++) {
          Object[] singolaFase = listaExistsFasiGaraLotto.get(i);
          pageContext.setAttribute((String) singolaFase[0], singolaFase[1], PageContext.REQUEST_SCOPE);
        }
      }
    } catch (SQLException e) {
      throw new JspException(
          "Errore nella lettura della lista delle fasi presenti", e);
    }

    return null;

  }

}
