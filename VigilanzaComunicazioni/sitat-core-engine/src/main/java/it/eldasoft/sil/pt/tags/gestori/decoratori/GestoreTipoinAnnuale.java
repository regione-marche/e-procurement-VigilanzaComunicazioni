/*
 * Created on 05-nov-2009
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.tags.gestori.decoratori;

import java.sql.SQLException;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.bl.TabellatiManager;
import it.eldasoft.gene.tags.decorators.campi.AbstractGestoreCampoTabellato;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestoreTipoinAnnuale extends AbstractGestoreCampoTabellato {

  public GestoreTipoinAnnuale() {
    super(true, "T5");
  }

  public SqlSelect getSql() {
    SqlManager sql = (SqlManager) UtilitySpring.getBean("sqlManager",
                this.getPageContext(), SqlManager.class);
    String resQuery = ""; 
    String tabellato = "";

    try {
        tabellato = (String) sql.getObject("select c0c_tab1 from c0campi where c0c_mne_ber = ? ", 
                                        new Object[] {"FSTIPO"});
        
        String numTabella = "" + TabellatiManager.getNumeroTabellaByCodice(tabellato);
        
        if (!numTabella.equals("2")) {
            resQuery = "select tab" + numTabella + "tip, tab" + numTabella + "desc from tab" + numTabella + 
                     " where tab" + numTabella + "cod = ? and tab" + numTabella + "tip <> ?";
        } else {
            resQuery = "select tab2tip, tab2d2 from tab2 where tab2cod = ? and tab2tip <> ?";
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return new SqlSelect(resQuery, new Object[] {tabellato, "L"});
  }
  
}
