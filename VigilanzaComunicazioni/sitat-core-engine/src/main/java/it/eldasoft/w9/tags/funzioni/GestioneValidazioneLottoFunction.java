package it.eldasoft.w9.tags.funzioni;

import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.ControlliValidazioneManager;

/**
 * Funzione per la validazione di un lotto, invocata dalla pagina di dati generali del lotto.
 * 
 * @author Luca.Giacomazzo
 */
public class GestioneValidazioneLottoFunction extends AbstractFunzioneTag {

  /**
   * @param numParam
   * @param paramClasses
   */
  public GestioneValidazioneLottoFunction() {
    super(5, new Class[] { PageContext.class, String.class, String.class, String.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {

    ControlliValidazioneManager managerValidazione = (ControlliValidazioneManager) UtilitySpring.getBean(
        "controlliValidazioneManager", pageContext, ControlliValidazioneManager.class);
    
    String profiloAttivo = (String) pageContext.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
    
    String custom = (String)pageContext.getSession().getAttribute("invioCustom");
    HashMap<String, Object> infoValidazione = managerValidazione.validateLotto(params, custom, profiloAttivo);
    
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
