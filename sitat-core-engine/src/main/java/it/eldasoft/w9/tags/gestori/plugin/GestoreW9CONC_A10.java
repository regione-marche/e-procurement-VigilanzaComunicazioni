/**
 * 
 */
package it.eldasoft.w9.tags.gestori.plugin;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;

/**
 * @author luca.giacomazzo
 *
 */
public class GestoreW9CONC_A10 extends AbstractGestorePreload {

  public GestoreW9CONC_A10(BodyTagSupportGene tag) {
    super(tag);
  }
  
  @Override
  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

  }

  @Override
  public void doBeforeBodyProcessing(PageContext pageContext, String modoAperturaScheda)
      throws JspException {

    String key = UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    
    DataColumnContainer dcc = new DataColumnContainer(key);
    Long codGara = (Long) (dcc.getColumnsBySuffix("CODGARA", true))[0].getValue().getValue();
    Long codLott = (Long) (dcc.getColumnsBySuffix("CODLOTT", true))[0].getValue().getValue();
    Long numappa = null;
    
    try {
    	SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
    	if (UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
    		numappa = new Long(pageContext.getSession().getAttribute("aggiudicazioneSelezionata").toString());
    	} else {
    		Long numConc = (dcc.getColumnsBySuffix("NUM_CONC", true))[0].getValue().longValue();
      	  	numappa = (Long)sqlManager.getObject("SELECT NUM_APPA FROM W9CONC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_CONC = ?", new Object[]{codGara, codLott, numConc});
    	}
      if (! UtilitySITAT.existsFase(codGara, codLott, numappa, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, sqlManager) &&
            ! UtilitySITAT.existsFase(codGara, codLott, numappa, CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO, sqlManager) &&
              ! UtilitySITAT.existsFase(codGara, codLott, numappa, CostantiW9.STIPULA_ACCORDO_QUADRO, sqlManager)) {
       // Se non esiste la fase iniziale, allora si e'in presenza
       // di una interruzione anticipata del contratto
       pageContext.setAttribute("interruzioneAnticipata", "1", PageContext.REQUEST_SCOPE);
      }
    } catch (Exception e) {
      throw new JspException("Errore nell'inizializzazione della scheda W9CONC " +
          "- Fase semplificata conclusione contratto (A10) – appalto", e);
    }

  }

}
