package it.eldasoft.w9.tags.gestori.plugin;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiW9;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GestoreW9MISSIC extends AbstractGestorePreload {

  private static final String SQL_ESTRAZIONE_NOMIMP = "select IMPR.NOMEST from W9MISSIC, IMPR where CODGARA = ? AND CODLOTT = ? AND num_iniz = ? and IMPR.CODIMP=W9MISSIC.CODIMP";

  /** Costruttore. */
  public GestoreW9MISSIC(BodyTagSupportGene tag) {
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
    Long num_iniz = new Long(1);
	
    if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
      codice = (String) UtilityTags.getParametro(page,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      key = new DataColumnContainer(codice);

      try {
        codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
        codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
        num_iniz = (key.getColumnsBySuffix("NUM_INIZ", true))[0].getValue().longValue();

        String nomeimpr = "";
        if (sqlManager.getObject(SQL_ESTRAZIONE_NOMIMP, new Object[] { codgara, codlott, num_iniz }) != null) {
          nomeimpr = sqlManager.getObject(SQL_ESTRAZIONE_NOMIMP, new Object[] { codgara, codlott, num_iniz }).toString();
        }
        page.setAttribute("nomeimpr", nomeimpr, PageContext.REQUEST_SCOPE);

        // Gestione documenti allegati della fase
        String sqlW9DOCFASE = "select count(*) from w9docfase where "
            + " codgara = ? and codlott = ? and fase_esecuzione = ? and num_fase = ? "
            + " and file_allegato is not null";

        Long ctrlBlob = (Long) sqlManager.getObject(sqlW9DOCFASE, new Object[] {
            codgara, codlott, new Long(CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA), num_iniz });
        if (ctrlBlob == null || new Long(0).equals(ctrlBlob)) {
          page.setAttribute("ctrlBlob", "false", PageContext.REQUEST_SCOPE);
        } else {
          page.setAttribute("ctrlBlob", "true", PageContext.REQUEST_SCOPE);
        }

      } catch (SQLException sqle) {
        throw new JspException(
            "Errore nell'esecuzione della query per l'estrazione dei dati delle Misure Aggiuntive sulla Sicurezza", sqle);
      } catch (Exception exc) {
        throw new JspException(
            "Errore nel caricamento dati delle Misure Aggiuntive sulla Sicurezza", exc);
      }
    }
  }

}
