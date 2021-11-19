/*
 * Created on 30/07/10
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.utility.UtilityStringhe;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Funzione per l'inizializzazione dell'attributo UPDATE_LISTA
 *
 * @author C.F.
 */
public class InizializzaUpdateListaFunction extends AbstractFunzioneTag {

  public InizializzaUpdateListaFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {


    String updateLista = UtilityTags.getParametro(pageContext,
        UtilityTags.DEFAULT_HIDDEN_UPDATE_LISTA);

    updateLista = UtilityStringhe.convertiNullInStringaVuota(updateLista);
    if("".equals(updateLista) || "1".equals(updateLista)){
      updateLista = "0";
    }else{
      updateLista = "1";
    }

    pageContext.setAttribute(UtilityTags.DEFAULT_HIDDEN_UPDATE_LISTA,
        updateLista, PageContext.REQUEST_SCOPE);

    return null;
  }

}
