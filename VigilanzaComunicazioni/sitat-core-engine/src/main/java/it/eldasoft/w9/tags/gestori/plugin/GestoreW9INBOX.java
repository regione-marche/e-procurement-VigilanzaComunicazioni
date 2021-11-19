package it.eldasoft.w9.tags.gestori.plugin;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * Gestore inizializzazione della scheda INBOX
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9INBOX extends AbstractGestorePreload {

  public GestoreW9INBOX(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext page, String modoAperturaScheda)
      throws JspException {
  }

  public void doBeforeBodyProcessing(PageContext pageContext, String modoAperturaScheda)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    
    // # key=W9INBOX.IDCOMUN=N:32169
    String key = (String) UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    Long idComun = new Long(key.substring(key.indexOf(":")+1));
    try {
      Long area = (Long) sqlManager.getObject("select AREA from W9FLUSSI where IDCOMUN=?", new Object[]{idComun});
      if(area == null) {
    	  area = (Long) sqlManager.getObject("select AREA from W9FLUSSI_ELIMINATI where IDCOMUN=?", new Object[]{idComun});
      }
      if(area != null)
        pageContext.setAttribute("area", area, PageContext.REQUEST_SCOPE);
    } catch (SQLException e) {
      throw new JspException("Errore nel recupero del campo W9FLUSSI.AREA per inizializzazione della scheda", e);
    }
  }

}