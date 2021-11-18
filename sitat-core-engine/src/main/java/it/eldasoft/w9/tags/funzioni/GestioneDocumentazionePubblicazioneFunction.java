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
 * costituiscono il Pubblicazione di gara.
 * 
 * @author Luca.Giacomazzo
 */
public class GestioneDocumentazionePubblicazioneFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GestioneDocumentazionePubblicazioneFunction() {
    super(3, new Class[]{PageContext.class, String.class, String.class});
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {

    Long codGara = new Long((String) params[1]);
    Long numPubb = new Long((String) params[2]);
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
      List< ? > listaDocumentiPubblicazione = sqlManager.getListVector(
          "select CODGARA, NUMDOC, TITOLO, URL, "
          + sqlManager.getDBFunction("length", new String[] { "FILE_ALLEGATO" }) + " as ALLEGATO, NUM_PUBB " 
          + " from W9DOCGARA where CODGARA=? and NUM_PUBB = ? order by NUMDOC asc",
          new Object[]{ codGara, numPubb });

      if (listaDocumentiPubblicazione != null && listaDocumentiPubblicazione.size() > 0) {
        pageContext.setAttribute("documentiPubblicazione", listaDocumentiPubblicazione, PageContext.REQUEST_SCOPE);
        pageContext.setAttribute("isDocPubblicazioneEstratti", Boolean.TRUE, PageContext.REQUEST_SCOPE);
      } else {
        pageContext.setAttribute("isDocPubblicazioneEstratti", Boolean.FALSE, PageContext.REQUEST_SCOPE);
      }
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre le pubblicazioni della gara " + codGara, e);
    }
    return null;
  }

}