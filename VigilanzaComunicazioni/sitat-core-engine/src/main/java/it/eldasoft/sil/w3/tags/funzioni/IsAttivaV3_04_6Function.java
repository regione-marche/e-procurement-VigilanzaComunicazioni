/*
 * Created on 06-Feb-2012
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.w3.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.utils.UtilitySITAT;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class IsAttivaV3_04_6Function extends AbstractFunzioneTag {

  public IsAttivaV3_04_6Function() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {

    String attiva = "false";
    Long numgara = null;
    if (!((String)params[1]).equals("")) {
    	numgara = new Long((String)params[1]);
    }
    
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
            pageContext, SqlManager.class);
    
    attiva = UtilitySITAT.v3_04_6_Attiva(sqlManager, numgara)?"true":"false";
    
    return attiva;

  }

}