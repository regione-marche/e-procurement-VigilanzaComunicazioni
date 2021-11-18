package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.ControlliValidazioneManager;

import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GestioneValidazioneFabbisogniFunction extends AbstractFunzioneTag {

  public GestioneValidazioneFabbisogniFunction() {
    super(3, new Class[] { PageContext.class, String.class, String.class});

  }

  public String function(PageContext pageContext, Object params[]) throws JspException {
    ControlliValidazioneManager managerValidazione = (ControlliValidazioneManager) UtilitySpring.getBean(
        "controlliValidazioneManager", pageContext, ControlliValidazioneManager.class);
    
    //Raccolta fabbisogni: aprile 2019. Controllo dei dati inseriti sulla proposta/fabbisogno(TAB_CONTROLLI).
    HashMap<String, Object> infoValidazione = managerValidazione.validateFabbisogni(params);
    pageContext.setAttribute("titolo", infoValidazione.get("titolo"));
    pageContext.setAttribute("listaControlli", infoValidazione.get("listaControlli"));
    pageContext.setAttribute("numeroWarning", infoValidazione.get("numeroWarning"));
    pageContext.setAttribute("numeroErrori", infoValidazione.get("numeroErrori"));

    if (infoValidazione.containsKey("controlliSuBean")) {
      pageContext.setAttribute("controlliSuBean", infoValidazione.get("controlliSuBean"));
    }
    
    return null;
  }

}
