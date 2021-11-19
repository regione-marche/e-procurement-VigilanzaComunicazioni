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
 * costituiscono la documentazione dell'avviso.
 * 
 * @author Mirco.Franzoni
 */
public class GestioneDocumentazioneAvvisoFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GestioneDocumentazioneAvvisoFunction() {
    super(4, new Class[]{PageContext.class, String.class, String.class, String.class});
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {

    String codein = (String) params[1];
    Long idavviso = new Long((String) params[2]);
    Long codsistema = new Long((String) params[3]);
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
      List< ? > listaDocumentiPubblicazione = sqlManager.getListVector(
          "select CODEIN, IDAVVISO, CODSISTEMA, NUMDOC, TITOLO, URL, "
          + sqlManager.getDBFunction("length", new String[] { "FILE_ALLEGATO" }) + " as ALLEGATO" 
          + " from W9DOCAVVISO where CODEIN = ? and IDAVVISO = ? and CODSISTEMA = ? order by NUMDOC asc",
          new Object[]{ codein, idavviso, codsistema });

      if (listaDocumentiPubblicazione != null && listaDocumentiPubblicazione.size() > 0) {
        pageContext.setAttribute("documentiPubblicazione", listaDocumentiPubblicazione, PageContext.REQUEST_SCOPE);
        pageContext.setAttribute("isDocPubblicazioneEstratti", Boolean.TRUE, PageContext.REQUEST_SCOPE);
      } else {
        pageContext.setAttribute("isDocPubblicazioneEstratti", Boolean.FALSE, PageContext.REQUEST_SCOPE);
      }
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre le pubblicazioni dell'avviso " + codein + ", " + idavviso + ", " + codsistema, e);
    }
    return null;
  }

}