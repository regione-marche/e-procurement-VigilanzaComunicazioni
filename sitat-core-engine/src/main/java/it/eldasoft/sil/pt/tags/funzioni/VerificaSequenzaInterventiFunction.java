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

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.spring.UtilitySpring;

public class VerificaSequenzaInterventiFunction extends AbstractFunzioneTag {

/**
*
*/
  public VerificaSequenzaInterventiFunction() {
	  super(2, new Class[] { PageContext.class, String.class });
  }

/**
 * Questa funzione verifica la corretta sequenza degli interventi, in caso contrario provvede a stabilirla.
 */
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
	  PtManager m = (PtManager) UtilitySpring.getBean(
		        "ptManager", pageContext,
		        PtManager.class);
	  
	  boolean result = false;
	  try {
		  String codice = params[1].toString();
		  DataColumnContainer keys = new DataColumnContainer(codice);
		  Long contri = (keys.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
		  result = m.verificaSequenzaInterventi(contri);
	  } catch (Exception e) {
		  ;
	  }
	  
	  return (result)?"TRUE":"FALSE";

  }

}
