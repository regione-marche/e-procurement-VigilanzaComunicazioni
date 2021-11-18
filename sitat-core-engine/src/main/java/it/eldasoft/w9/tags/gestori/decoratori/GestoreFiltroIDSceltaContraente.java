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
package it.eldasoft.w9.tags.gestori.decoratori;

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

/**
 * Gestore per il tabellato dell'incarico professionale. 
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreFiltroIDSceltaContraente extends GestoreCampoTabellatoGen {

  /** Query per Scheda lotto di gara. */
  private final String queryTabellatoIdSceltaContraente       = "select id, nome from w9cf_pubb order by id";

  /**
   * Costruttore.
   */
  public GestoreFiltroIDSceltaContraente() {
    super(false, "N2");
  }

  public SqlSelect getSql() {
    try {
        String  query = queryTabellatoIdSceltaContraente;
        
        HashMap< ? , ? > datiRiga = (HashMap< ? , ? >) this.getPageContext().getAttribute(
            "datiRiga", PageContext.REQUEST_SCOPE);
        if (datiRiga != null) {
          return new SqlSelect(query, new Object[] {});
        }
      
   } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}