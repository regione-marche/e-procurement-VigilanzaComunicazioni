package it.eldasoft.sil.pt.tags.funzioni;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.spring.UtilitySpring;

public class RecuperoFileCondizioniDUsoFunction extends AbstractFunzioneTag {

  public RecuperoFileCondizioniDUsoFunction() {
    super(1, new Class[]{PageContext.class});

  }

  public String function(PageContext pageContext, Object[] params) throws JspException {
    
    PtManager  peManager = (PtManager) UtilitySpring.getBean("ptManager",pageContext, PtManager.class);
    return peManager.recuperaNomeFileCondizioniDUso(pageContext.getServletContext());
    
  }
}
