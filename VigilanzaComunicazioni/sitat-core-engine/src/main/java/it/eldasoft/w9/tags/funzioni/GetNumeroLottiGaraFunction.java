package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 * Determina numero dei lotti della gara necessario per determinare se
 * visualizzare il filtro per il filtraggio della lista lotti o meno. 
 * 
 * @author Luca.Giacomazzo
 */
public class GetNumeroLottiGaraFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public GetNumeroLottiGaraFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] arg1) throws JspException {
    if (StringUtils.isNotEmpty(arg1[0].toString())) {
      Long codGara = Long.parseLong(arg1[1].toString());
      
      GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager", pageContext, GeneManager.class);
      Long numeroLotti = new Long(geneManager.countOccorrenze("W9LOTT", " CODGARA=? ", new Object[]{ codGara }));
      
      if (numeroLotti != null) {
        return numeroLotti.toString();
      } else {
        return "0";
      }
    } else {
      return "0";
    }
  }

}
