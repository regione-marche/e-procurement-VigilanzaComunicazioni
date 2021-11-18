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
package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class UfficioSAAttivoFunction extends AbstractFunzioneTag {

  public UfficioSAAttivoFunction() {
    super(1, new Class[] { PageContext.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
	  HttpSession sessione = pageContext.getSession();
	  String stazioneAppaltante = (String) sessione.getAttribute(
    	      CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
	  ProfiloUtente profilo = (ProfiloUtente) this.getRequest().getSession().getAttribute(
		        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
	  Long syscon = new Long(profilo.getId());
	  String attiva = "0";
    
	  SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
            pageContext, SqlManager.class);
	  Long ufficiAttivi = new Long(0);
	  try {
		  ufficiAttivi = (Long)sqlManager.getObject("select count(*) from UFFICI where CODEIN = ? and CCPROG='1'",
                new Object[] { stazioneAppaltante });
		  attiva = (ufficiAttivi > 0)?"1":"0";
		  
		  Long idUfficio = (Long)sqlManager.getObject("select SYSUFFAPP from USRSYS where SYSCON = ?",
	                new Object[] { syscon });
		  if (idUfficio != null) {
			  String denominazioneUfficioAttivo = (String)sqlManager.getObject("select DENOM from UFFICI where ID = ?",
		                new Object[] { idUfficio });
			  this.getRequest().setAttribute("denominazioneUfficioAttivo", denominazioneUfficioAttivo);
		  }
		  this.getRequest().setAttribute("ufficioAttivo", idUfficio);
	  } catch (Exception ex) {
		  ;
	  }
    
	  return attiva;
  }

}