/*
 * Created on 15/mar/07
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.tags.gestori.decoratori;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.utils.spring.UtilitySpring;

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

/**
 * Gestore del campo codice CPV (prima parte)
 * 
 * @author Marco.Franceschin
 * 
 */
public class GestoreCampoCATINT extends GestoreCampoTabellatoGen {

  public GestoreCampoCATINT() {
    super(false, "T3");
  }

  public SqlSelect getSql() {
	  
	  SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
			  this.getPageContext(), SqlManager.class);
	  
    HashMap datiRiga = (HashMap) this.getPageContext().getAttribute("datiRiga",
        PageContext.REQUEST_SCOPE);
    String queryCatint = "";
    Object[] filtri = null;
    if (datiRiga != null && datiRiga.get("INTTRI_INTERV") != null
            && !datiRiga.get("INTTRI_INTERV").toString().trim().equals("")) {
        	
          queryCatint = "select " + sqlManager.getDBFunction("concat", new String[] {"tabcod1",sqlManager.getDBFunction("concat",new String[] {"'-'", "tabcod2"})}) + " , " + sqlManager.getDBFunction("concat",new String[] {"tabcod2",sqlManager.getDBFunction("concat",new String[] {"' '", "tabdesc"})}) + " from tabsche where tabcod = ? and tabcod1 = ? order by tabcod2";
          //queryCatint = "select tabcod1 || '-' || tabcod2,tabcod2 || ' ' || tabdesc from tabsche where tabcod = ? and tabcod1 = ? order by tabcod2";
          filtri = new Object[] { "S2006", datiRiga.get("INTTRI_INTERV") };
        } else {
        	queryCatint = "select " + sqlManager.getDBFunction("concat", new String[] {"tabcod1",sqlManager.getDBFunction("concat",new String[] {"'-'", "tabcod2"})}) + " , " + sqlManager.getDBFunction("concat",new String[] {"tabcod2",sqlManager.getDBFunction("concat",new String[] {"' '", "tabdesc"})}) + " from tabsche where tabcod = ? order by tabcod2";
          //queryCatint = "select tabcod1 || '-' || tabcod2,tabcod2 || ' ' || tabdesc from tabsche where tabcod = ? order by tabcod2";
          filtri = new Object[] { "S2006" };
        }
    if (datiRiga != null) {
      return new SqlSelect(queryCatint, filtri);
    }
    return null;
  }

}
