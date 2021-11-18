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

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

/**
 * Gestore del campo codice CPV (seconda parte)
 * 
 * @author Marco.Franceschin
 * 
 */
public class GestoreCampoTABCPV_TABCOD2 extends GestoreCampoTabellatoGen {

  public GestoreCampoTABCPV_TABCOD2() {
    super(false, "T2");
  }

  public SqlSelect getSql() {
    HashMap datiRiga = (HashMap) this.getPageContext().getAttribute("datiRiga",
        PageContext.REQUEST_SCOPE);
    if (datiRiga != null) {
      return new SqlSelect(
          "Select CPVCOD2, CPVDESC from TABCPV "
              + "Where CPVCOD2 <> '00' and CPVCOD3 = '00' and CPVCOD1 = ? and CPVCOD0= ? and CPVCOD = 'S2020' "
              + "Order by CPVDESC", new Object[] { datiRiga.get("CPVCOD1"), datiRiga.get("CPVCOD0") });
    }
    return null;
  }

}
