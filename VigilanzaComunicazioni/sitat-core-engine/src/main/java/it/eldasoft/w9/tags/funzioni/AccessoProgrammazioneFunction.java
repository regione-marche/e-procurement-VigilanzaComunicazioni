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
package it.eldasoft.w9.tags.funzioni;

import java.util.List;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class AccessoProgrammazioneFunction extends AbstractFunzioneTag {

  public AccessoProgrammazioneFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {

    String attiva = "0";
    Long syscon = null;
    if (params[1] != null) {
    	syscon = new Long((String)params[1]);
    }
    if (syscon != null) {
    	SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
                pageContext, SqlManager.class);
        List< ? > listaUffici = null;
        try {
        	listaUffici = sqlManager.getListVector(
                    "select ID, DENOM from UFFICI where CCPROG='1' and CODEIN in (select CODEIN from USR_EIN where syscon=?)",
                    new Object[] { syscon });
        } catch (Exception ex) {
        	;
        }
        
        if (listaUffici != null && listaUffici.size()>0) {
        	attiva = "1";
        	this.getRequest().setAttribute("listaUffici", listaUffici);
        }
    }
    
    return attiva;

  }

}