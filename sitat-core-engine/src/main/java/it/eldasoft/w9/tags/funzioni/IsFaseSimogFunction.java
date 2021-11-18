package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.w9.common.CostantiSimog;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Classe per la determinazione se una fase è oggetto di monitoraggio simog.
 */
public class IsFaseSimogFunction extends AbstractFunzioneTag {

  private static final String FASE_SIMOG_NO                 = "no";
  private static final String FASE_SIMOG_SI                 = "si";

    
  public IsFaseSimogFunction() {
    super(2, new Class[] { PageContext.class, String.class});
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    String isFaseSimog = FASE_SIMOG_NO;

    try {
    	
    	Long tipofase = new Long(params[1].toString());
        if (CostantiSimog.TipiSchede.containsKey(tipofase)) {
        	isFaseSimog = FASE_SIMOG_SI; 
        }
    } catch (Exception exc) {
      throw new JspException(
          "Errore in fase determinazione se una fase è oggetto di monitoraggio simog",
          exc);
    }
    return isFaseSimog;
  }

}