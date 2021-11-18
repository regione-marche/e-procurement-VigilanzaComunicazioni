package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Funzione per l'inizializzazione delle sezioni multiple dei file che
 * costituiscono il bando di gara.
 * 
 * @author Luca.Giacomazzo
 */
public class GestioneDocumentazioneFaseFunction extends AbstractFunzioneTag {

  public GestioneDocumentazioneFaseFunction() {
    super(5, new Class[] { PageContext.class, String.class, String.class, String.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {

    String codgara = (String) params[1];
    String codlott = (String) params[2];
    String faseEsecuzione = (String) params[3];
    String numFase = (String) params[4];

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {

      List< ? > datiW9DOCFASE = sqlManager.getListVector(
          "select codgara, codlott, fase_esecuzione, num_fase, numdoc, titolo "
              + " from w9docfase"
              + " where codgara = ? and codlott = ? and fase_esecuzione = ? and num_fase = ? order by numdoc asc",
          new Object[] { new Long(codgara), new Long(codlott), new Long(faseEsecuzione), new Long(numFase) });

      if (datiW9DOCFASE != null && datiW9DOCFASE.size() > 0) {
        pageContext.setAttribute("datiW9DOCFASE", datiW9DOCFASE, PageContext.REQUEST_SCOPE);
      }
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre i documenti della fase "
          + faseEsecuzione
          + " numero "
          + numFase
          + " della gara "
          + codgara
          + " del lotto "
          + codlott, e);
    }
    return null;
  }

}