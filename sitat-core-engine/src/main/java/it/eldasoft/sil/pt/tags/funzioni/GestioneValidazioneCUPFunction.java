package it.eldasoft.sil.pt.tags.funzioni;


import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;

import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.sil.pt.bl.ControlliValidazioneCUPManager;

import java.util.HashMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.log4j.Logger;

public class GestioneValidazioneCUPFunction extends AbstractFunzioneTag {

  static Logger logger = Logger.getLogger(GestioneValidazioneCUPFunction.class);

  public GestioneValidazioneCUPFunction() {
    super(3, new Class[] { PageContext.class, String.class });
  }

  public String function(PageContext pageContext, Object params[])
      throws JspException {
    
    ControlliValidazioneCUPManager controlliValidazioneCUPManager = (ControlliValidazioneCUPManager) UtilitySpring.getBean(
        "controlliValidazioneCUPManager", pageContext,
        ControlliValidazioneCUPManager.class);

    HashMap infoValidazione = controlliValidazioneCUPManager.validate(params);
    pageContext.setAttribute("titolo", infoValidazione.get("titolo"));
    pageContext.setAttribute("listaControlli", infoValidazione.get("listaControlli"));
    pageContext.setAttribute("numeroWarning", infoValidazione.get("numeroWarning"));
    pageContext.setAttribute("numeroErrori", infoValidazione.get("numeroErrori"));

    return null;
    
  }

}