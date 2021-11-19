package it.eldasoft.sil.pt.tags.funzioni;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * Classe per l'estrazione del nome della direzione di appartenenza di un
 * utente dato il codice.
 * 
 */
public class AttivaIncollaFunction extends AbstractFunzioneTag {
	
  private static final String SQL_GET_TIPROG                 = "select TIPROG from PIATRI where CONTRI = ?";


  public AttivaIncollaFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  /**
   * Estrae la descrizione del tabellato a partire dal codice, se valorizzato
   * 
   * @see it.eldasoft.gene.tags.utils.AbstractFunzioneTag#function(javax.servlet.jsp.PageContext,
   *      java.lang.Object[])
   */
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    String attiva = "off";
    String key = params[1].toString();
    String interventoCopiato = (String) pageContext.getSession().getAttribute("interventoCopiato");
    String tipoInterventi = (String) pageContext.getSession().getAttribute("tipint");	//recupero il tipo di lista attuale (interventi o forniture e servizi)
    try {
	    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
	    
	    if (interventoCopiato != null && !interventoCopiato.trim().equals("") && key != null && !key.trim().equals("") && tipoInterventi != null && !tipoInterventi.trim().equals("")) {
		    DataColumnContainer keys = new DataColumnContainer(key);
		    Long contriAttuale = (keys.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();

		    DataColumnContainer keysCop;
	    	if (interventoCopiato.indexOf(";;") != -1) {
	    		keysCop = new DataColumnContainer(interventoCopiato.split(";;")[0]);
	    	} else {
	    		keysCop = new DataColumnContainer(interventoCopiato);
	    	}
		    Long contriCopiato = (keysCop.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
		    Long conintCopiato = (keysCop.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();
		    
		    String sqlGetTipint = "select " + sqlManager.getDBFunction("isnull",new String[] {"TIPINT","0"}) + " from INTTRI where CONTRI = ? and CONINT = ?";
		    
		    Long tipintCopiato = (Long)sqlManager.getObject(sqlGetTipint, new Object[] { contriCopiato, conintCopiato });
		    Long tipintAttuale = new Long(tipoInterventi);
		    //se gli interventi non hanno tipint valorizzato vado a vedere il tiprog dei programmi reltivi
		    if (tipintCopiato == 0) {
		    	tipintCopiato = (Long)sqlManager.getObject(SQL_GET_TIPROG, new Object[] { contriCopiato });
		    	tipintAttuale = (Long)sqlManager.getObject(SQL_GET_TIPROG, new Object[] { contriAttuale });
		    }
	    	if (tipintAttuale.equals(tipintCopiato)) {
	    		attiva = "attivo";
	    	}
	    }
	} catch (Exception e) {
		throw new JspException("Errore nel controllo dati per la funzionalità Copia-Incolla Interventi: " + e);
	}
    return attiva;
  }

}
