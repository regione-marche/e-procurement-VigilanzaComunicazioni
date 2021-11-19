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
public class GestioneDocumentazioneBandoFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GestioneDocumentazioneBandoFunction() {
    super(2, new Class[]{PageContext.class, String.class});
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {

    Long codGara = new Long((String) params[1]);
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
      List< ? > listaDocumentiBando = sqlManager.getListVector(
          "select CODGARA, NUMDOC, TITOLO, URL, "
          + sqlManager.getDBFunction("length", new String[] { "FILE_ALLEGATO" }) + " as ALLEGATO " 
          + ", NUM_PUBB from W9DOCGARA where NUM_PUBB=1 AND CODGARA=? order by NUMDOC asc",
          new Object[]{ codGara });

      if (listaDocumentiBando != null && listaDocumentiBando.size() > 0) {
        pageContext.setAttribute("documentiBando", listaDocumentiBando, PageContext.REQUEST_SCOPE);
        pageContext.setAttribute("isDocBandoEstratti", Boolean.TRUE, PageContext.REQUEST_SCOPE);
      } else {
        pageContext.setAttribute("isDocBandoEstratti", Boolean.FALSE, PageContext.REQUEST_SCOPE);
      }
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre le pubblicazioni bando della gara " + codGara, e);
    }
    return null;
  }

}