package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * 
 * @author Mirco 09.07.2012
 *
 * dato il numero del programma estrae i lavori in economia associati
 */

public class GestioneLavoriEconomiaFunction extends AbstractFunzioneTag {

	/**
	 * 
	 */
  public GestioneLavoriEconomiaFunction() {
    super(1, new Class[] { String.class });
  }

  /**
   * @param pageContext pageContext
   * @param params codice del programma
   * 
   * @return return
   * @throws JspException JspException
   */
  public final String function(final PageContext pageContext, final Object[] params)
      throws JspException {

	 
	  Long contri = null;
    try {
    	String codice = params[0].toString();
    	
    	DataColumnContainer key = new DataColumnContainer(codice);
    	contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
    	SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
   
      List listaLavoriInEconomia = null;

      listaLavoriInEconomia = sqlManager.getListVector(
          "select CONTRI, CONECO, DESCRI, CUPPRG, STIMA "
              + "from ECOTRI "
              + "where CONTRI = ? "
              + "order by CONECO",
          new Object[] { contri});

      pageContext.setAttribute("listaLavoriInEconomia",
    		  listaLavoriInEconomia, PageContext.REQUEST_SCOPE);

    } catch (Exception e) {
      throw new JspException("Errore nell'estrarre i lavori in economia per il "
          + "lavoro "
          + contri, e);
    }

    return null;
  }

}
