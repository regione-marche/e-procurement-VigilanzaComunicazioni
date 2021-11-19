/*
 * Created on 23/06/10
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.tags.funzioni;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.admin.ProfiliManager;
import it.eldasoft.gene.db.domain.admin.Profilo;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

public class EsisteProfiloFunction extends AbstractFunzioneTag {

/**
*
*/
  public EsisteProfiloFunction() {
	  super(2, new Class[] { PageContext.class, String.class });
  }

/**
 * 
 */
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
	  
	  String esisteProfilo = "FALSE";
	  String codice = params[1].toString();
	 

    try {
    	 ProfiliManager profiliManager = (ProfiliManager) UtilitySpring.getBean(
    	            "profiliManager", pageContext, ProfiliManager.class);
    	// Determinazione della lista dei profili filtrati per codice applicazione
    	Profilo profilo = profiliManager.getProfiloByCodProfilo(codice);
    	if (profilo!=null)
    	{
    	  esisteProfilo = "TRUE";
    	}
    } catch (Exception e) {
      throw new JspException("Errore nella ricerca del profilo", e);
    }

    return esisteProfilo;

  }

}
