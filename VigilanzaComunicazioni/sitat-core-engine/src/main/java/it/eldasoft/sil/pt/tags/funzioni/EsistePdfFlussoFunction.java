package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Verifica se esiste il fle pdf collegato al flusso
 * 
 * @author Luca.Giacomazzo
 */
public class EsistePdfFlussoFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public EsistePdfFlussoFunction() {
	  super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    String result = "false";

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

    Long idFlusso;
    try {
    	idFlusso = new Long((String) params[1]);
    	String sqlSelectPdf = "select " + sqlManager.getDBFunction("length", new String[] {"FILE_ALLEGATO"}) + " as FILE_ALLEGATO from PTFLUSSI where IDFLUSSO = ? ";
    	String ctrlFile = "" + sqlManager.getObject(sqlSelectPdf, new Object[] { idFlusso });
	    if (ctrlFile == null || ctrlFile.trim().equals("") || ctrlFile.length() == 0 || ctrlFile.trim().equals("null")) {
	    	result = "false";
	    } else {
	    	result = "true";
	    }
    } catch (Exception e) {
      throw new JspException("Errore nella verifica dell'esistenza del pdf allegato al flusso. ", e);
    }
    return result;
  }

}
