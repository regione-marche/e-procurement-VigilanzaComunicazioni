package it.eldasoft.w9.tags.gestori.plugin;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiSitatSA;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GestoreW9FLUSSI extends AbstractGestorePreload {

  private final String SQL_CTRL_CLOB         = "select XML from W9FLUSSI where IDFLUSSO = ? ";

  /** Costruttore. */
  public GestoreW9FLUSSI(BodyTagSupportGene tag) {
    super(tag);
  }

  @Override
  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {
  }

  @Override
  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda) throws JspException {

    String codice = "";
    DataColumnContainer key = null;
    Long idFlussi = null;
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", page, SqlManager.class);

    try {
      if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
        codice = UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
        key = new DataColumnContainer(codice);
        idFlussi = (key.getColumnsBySuffix("IDFLUSSO", true))[0].getValue().longValue();

        String ctrlBlob = "" + sqlManager.getObject(SQL_CTRL_CLOB, new Object[] { idFlussi });
        if (ctrlBlob == null || ctrlBlob.trim().equals("") || ctrlBlob.length() == 0 || ctrlBlob.trim().equals("null")) {
          page.setAttribute("ctrlClob", "false", PageContext.REQUEST_SCOPE);
        } else {
          page.setAttribute("ctrlClob", "true", PageContext.REQUEST_SCOPE);
        }
      }

      String versioneTracciatoXML = ConfigManager.getValore(CostantiSitatSA.VERSIONE_TRACCIATO_XML);
      page.setAttribute("versioneTracciatoXML", versioneTracciatoXML, PageContext.REQUEST_SCOPE);

    } catch (SQLException sqle) {
      throw new JspException("Errore nell'esecuzione della query per l'estrazione dei dati del Flusso", sqle);
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati del Flusso", exc);
    }
  }

}
