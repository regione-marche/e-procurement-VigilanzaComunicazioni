package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.sil.pt.bl.PtManager;

import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GestioneValidazioneFunction extends AbstractFunzioneTag {

  public GestioneValidazioneFunction() {
    super(5, new Class[] { PageContext.class, String.class, String.class,
        String.class, String.class });

  }

  public String function(PageContext pageContext, Object params[])
      throws JspException {
    PtManager managerValidazione = (PtManager) UtilitySpring.getBean(
        "ptManager", pageContext,
        PtManager.class);

    //HashMap<String, Object> infoValidazione = managerValidazione.validateXmlDocumentProgrammiTriennali(params);
    HashMap<String, Object> infoValidazione = managerValidazione.validateProgrammi(params);
    pageContext.setAttribute("titolo", infoValidazione.get("titolo"));
    pageContext.setAttribute("listaControlli", infoValidazione.get("listaControlli"));
    pageContext.setAttribute("numeroWarning", infoValidazione.get("numeroWarning"));
    pageContext.setAttribute("numeroErrori", infoValidazione.get("numeroErrori"));

    return null;
  }

}
