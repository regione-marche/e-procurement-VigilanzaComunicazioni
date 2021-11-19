package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 * Funzione per l'inizializzazione della lista delle imprese invitate/partecipanti.
 * 
 * @author Luca.Giacomazzo
 */
public class GetImpreseInvitateFunction extends AbstractFunzioneTag {

  /**
   * @param numParam
   * @param paramClasses
   */
  public GetImpreseInvitateFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {
    
    String chiave = (String) params[0];
    String[] arrayChiave = chiave.split(";");
    
    Long codgara = Long.parseLong(arrayChiave[0]);
    Long codlott = Long.parseLong(arrayChiave[1]);
    Long numImpr = null;
    Long numRaggr = null;
    
    if (arrayChiave.length > 2 && StringUtils.isNotEmpty(arrayChiave[2])) {
      numImpr = Long.parseLong(arrayChiave[2]);
    }
    
    
    
    if (arrayChiave.length > 3 && StringUtils.isNotEmpty(arrayChiave[3])) {
      numRaggr = Long.parseLong(arrayChiave[3]);
    }

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
    List< ? > listVectorW9Imprese =  null;
    
    try {
    	
      String queryNumeroRaggr = "select num_ragg from w9imprese where codgara=? and codlott=? and num_impr=?";
      if (numImpr == null){queryNumeroRaggr = "select num_ragg from w9imprese where codgara=? and codlott=? and num_impr IS NULL";}
    	
      Long numeroRaggr = (Long) sqlManager.getObject(queryNumeroRaggr,
          new Object[] {codgara, codlott, numImpr} );
      
      if (numeroRaggr == null) {
        // estrazione di una impresa singola
    	String queryListVectorImprese =  "select w.codgara, w.codlott, w.num_impr, w.num_ragg, w.codimp, i.nomest, w.ruolo, w.partecip, w.tipoagg " +
        " from w9imprese w, impr i where w.codgara=? and w.codlott=? and w.num_impr=? and i.codimp=w.codimp "; 
    	
    	if (numImpr == null){queryListVectorImprese =  "select w.codgara, w.codlott, w.num_impr, w.num_ragg, w.codimp, i.nomest, w.ruolo, w.partecip, w.tipoagg " +
            " from w9imprese w, impr i where w.codgara=? and w.codlott=? and w.num_impr IS NULL and i.codimp=w.codimp ";}
    	
        listVectorW9Imprese = sqlManager.getListVector(
        		queryListVectorImprese,
            new Object[] {codgara, codlott, numImpr} );
      } else {
        // estrazione di un gruppo di imprese
        listVectorW9Imprese = sqlManager.getListVector(
            "select w.codgara, w.codlott, w.num_impr, w.num_ragg, w.codimp, i.nomest, w.ruolo, w.partecip, w.tipoagg from w9imprese w, impr i where w.codgara=? and w.codlott=? and w.num_impr in (" +
              "select num_impr from w9imprese where codgara=? and codlott=? and num_ragg=?) and i.codimp = w.codimp ",
            new Object[] {codgara, codlott, codgara, codlott, numeroRaggr} );
      }

      if (listVectorW9Imprese != null && listVectorW9Imprese.size() > 0) {
        pageContext.setAttribute("listaImpreseInvitatePartecipanti", listVectorW9Imprese, PageContext.REQUEST_SCOPE);
      }
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre le imprese invitate/partecipanti", e);
    }
    return null;
  }

}
