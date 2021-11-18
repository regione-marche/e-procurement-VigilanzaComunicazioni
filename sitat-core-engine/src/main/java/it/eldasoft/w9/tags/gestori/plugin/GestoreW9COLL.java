package it.eldasoft.w9.tags.gestori.plugin;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;

public class GestoreW9COLL extends AbstractGestorePreload {

  public GestoreW9COLL(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

  }

  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda)
      throws JspException {
    String codice = "";
    DataColumnContainer key = null;
    String num_coll = "";
    if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
      try {
        codice = (String) UtilityTags.getParametro(page,
            UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
        key = new DataColumnContainer(codice);
        num_coll = (key.getColumnsBySuffix("NUM_COLL", true))[0].getValue().toString();

        page.setAttribute("num_coll", num_coll, PageContext.REQUEST_SCOPE);
      } catch (Exception exc) {
        throw new JspException("Errore nel caricamento dati del Collaudo", exc);
      }
    }

  }
}
