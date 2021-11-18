/*
 * Created on 27/06/2013
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
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.common.CostantiSitatSA;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Controlla se sono valorizzate le properties per il WS CUP
 *
 * @author Cristian Febas
 */
public class IsIntegrazioneWsCUPFunction extends AbstractFunzioneTag {

  public IsIntegrazioneWsCUPFunction(){
    super(1, new Class[]{PageContext.class});
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {

    String isIntegrazioneWsCUP="false";
    String urlIntegrazione = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_TYPE);
    if(urlIntegrazione != null && !"".equals(urlIntegrazione)){
      isIntegrazioneWsCUP ="true";
    }
    return isIntegrazioneWsCUP;
  }

}