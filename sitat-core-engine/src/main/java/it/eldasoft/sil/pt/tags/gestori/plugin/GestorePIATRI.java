package it.eldasoft.sil.pt.tags.gestori.plugin;

import java.sql.SQLException;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestorePIATRI extends AbstractGestorePreload {

  public GestorePIATRI(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

  }

  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda) throws JspException {
    String codice = "";
    DataColumnContainer key = null;  
    Long contri = null;
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", page, SqlManager.class);

    try {

	    if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
	      codice = (String) UtilityTags.getParametro(page,UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
          key = new DataColumnContainer(codice);
          contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
          String SQL_CTRL_BLOB         = "select " + sqlManager.getDBFunction("length", new String[] {"FILE_ALLEGATO"}) + " as FILE_ALLEGATO from PIATRI where CONTRI = ? ";
	      String ctrlBlob = "" + sqlManager.getObject(SQL_CTRL_BLOB, new Object[] { contri });
	      if (ctrlBlob == null || ctrlBlob.trim().equals("") || ctrlBlob.trim().equals("0") || ctrlBlob.trim().equals("null"))
	          page.setAttribute("ctrlBlob", "false", PageContext.REQUEST_SCOPE);
          else
	          page.setAttribute("ctrlBlob", "true", PageContext.REQUEST_SCOPE);
	    }
	    
    } catch (SQLException sqle) {
	    throw new JspException("Errore nell'esecuzione della query per l'estrazione dei dati della Programma", sqle);
	} catch (Exception exc) {
	    throw new JspException("Errore nel caricamento dati del Programma", exc);
	}

  }

}
