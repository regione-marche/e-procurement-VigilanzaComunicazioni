/*
 * Created on 7-nov-2012
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * Classe che realizza una funzione per ottenere il tipo di atto per l'esito
 * 
 * @author Mirco.Franzoni
 */
public class GetTipoAttoEsitoFunction extends AbstractFunzioneTag {

  public GetTipoAttoEsitoFunction() {
    super(1, new Class[] { Object.class });
  }

  /**
   * @see it.eldasoft.gene.tags.utils.AbstractFunzioneTag#function(javax.servlet.jsp.PageContext,
   *      java.lang.Object[])
   */
  public String function(PageContext pageContext, Object[] params) throws JspException {
    
    Long tipo = null;
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
	try {
		// vincoli di visualizzazione
		tipo = (Long) sqlManager.getObject("select ID from W9CF_PUBB where TIPO = ? ", new Object[] { "A" });
		if (tipo == null) {
			tipo = new Long(20);
		}
	} catch (SQLException e) {
		throw new JspException("Errore nell'estrazione del tipo di atto per l'esito", e);
	}
    return tipo.toString();
  }
  
}
