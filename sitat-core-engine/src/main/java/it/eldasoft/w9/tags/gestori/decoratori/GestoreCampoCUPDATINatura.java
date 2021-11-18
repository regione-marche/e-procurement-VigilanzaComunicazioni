/*
 * Created on 28/11/2011
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.tags.gestori.decoratori;

import it.eldasoft.gene.tags.decorators.campi.AbstractGestoreCampoTabellato;

public class GestoreCampoCUPDATINatura extends AbstractGestoreCampoTabellato {

  public GestoreCampoCUPDATINatura() {
    super(false, "T2");
  }

  public SqlSelect getSql() {
    return new SqlSelect("select cupcod1, "
        + "cupdesc from cuptab where cupcod = ? "
        + "and cupcod2 = '--' order by cupcod1", new Object[] { "CU001" });
  }
}
