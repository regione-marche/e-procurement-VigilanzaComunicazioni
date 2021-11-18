package it.eldasoft.w9.tags.gestori.plugin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestoreW9AGGI extends AbstractGestorePreload {

  /** Query per estrarre W9AGGI.CODIMP. */
  private static final String SQL_ESTRAZIONE_DATI_W9AGGI = "select CODIMP from W9AGGI where CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ?";
  /** Query per estrazione del campo IMPR.NOMEST. */
  private static final String SQL_ESTRAZIONE_IMPR        = "select NOMEST from IMPR where codimp = ? ";

  /** Costruttore. */
  public GestoreW9AGGI(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

  }

  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        page, SqlManager.class);
    String codice = "";
    DataColumnContainer key = null;
    Long codgara = null;
    Long codlott = null;
    Long numappa = null;
    if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
      codice = (String) UtilityTags.getParametro(page,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      key = new DataColumnContainer(codice);
      
      try {
        codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
        codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
        numappa = (key.getColumnsBySuffix("NUM_APPA", true))[0].getValue().longValue();
        List< ? > datiList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_W9AGGI,
            new Object[] { codgara, codlott, numappa });
        String codimpr = "";
        String nomeimpr = "";
        if (datiList.size() > 0) {
          codimpr = ((List< ? >) datiList.get(0)).get(0).toString();
          if (codimpr != null && !codimpr.equals("")) {
            List< ? > nomeimprList = sqlManager.getListVector(SQL_ESTRAZIONE_IMPR,
                new Object[] { codimpr });
            if (nomeimprList.size() > 0) {
              nomeimpr = ((List< ? >) nomeimprList.get(0)).get(0).toString();
            }
          }
        }
        page.setAttribute("nomeimpr", nomeimpr, PageContext.REQUEST_SCOPE);
      } catch (SQLException sqle) {
        throw new JspException(
            "Errore nell'esecuzione della query per l'estrazione dei dati del Soggetto Aggiudicatario",
            sqle);
      } catch (Exception exc) {
        throw new JspException("Errore nel caricamento dati del Soggetto Aggiudicatario", exc);
      }
    }
  }

}
