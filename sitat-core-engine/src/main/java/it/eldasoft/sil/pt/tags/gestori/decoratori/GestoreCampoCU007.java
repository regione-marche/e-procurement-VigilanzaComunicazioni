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
package it.eldasoft.sil.pt.tags.gestori.decoratori;

import it.eldasoft.gene.tags.decorators.campi.AbstractGestoreCampoTabellato;


public class GestoreCampoCU007 extends AbstractGestoreCampoTabellato {

	public GestoreCampoCU007() {
		super(false, "T2");
	}
	
	public SqlSelect getSql() {
		return new SqlSelect("select cupcod1, cupdesc "
		        + "from cuptab "
		        + "where cupcod = ? "
		        + "order by cupcod1", new Object[] { "CU007" });
		  }
}

