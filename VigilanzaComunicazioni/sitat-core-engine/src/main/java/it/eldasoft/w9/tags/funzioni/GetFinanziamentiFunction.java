package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.springframework.util.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * Function per estrarre i finanziamenti. 
 */
public class GetFinanziamentiFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GetFinanziamentiFunction() {
    super(1, new Class[] { String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    String codice = params[0].toString();
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    DataColumnContainer key = new DataColumnContainer(codice);
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    try {
      Long codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      Long codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      Long numappa = (key.getColumnsBySuffix("NUM_APPA", true))[0].getValue().longValue();
      List< ? > listaFinanziamenti = sqlManager.getListVector(
          "select CODGARA, CODLOTT, NUM_APPA, NUM_FINA, ID_FINANZIAMENTO, IMPORTO_FINANZIAMENTO "
              + "from W9FINA "
              + "where CODGARA = ? "
              + "and CODLOTT = ? and NUM_APPA = ?",
          new Object[] { codgara, codlott, numappa });

      pageContext.setAttribute("listaFinanziamenti",
          listaFinanziamenti, PageContext.REQUEST_SCOPE);

      if (listaFinanziamenti == null || (listaFinanziamenti != null && listaFinanziamenti.size() == 0)) {
        // Estrazione dell'importo complessivo dell'intervento
        Double importoComplessivoIntervento = (Double) sqlManager.getObject(
            "select IMPORTO_COMPL_INTERVENTO from W9APPA where CODGARA=? and CODLOTT=? and NUM_APPA=?", 
            new Object[] { codgara, codlott, numappa });
        DecimalFormat formatter = new DecimalFormat("0.00");
        if (importoComplessivoIntervento != null && importoComplessivoIntervento.doubleValue() > 0) {
        	String strImportoComplessivoIntervento = StringUtils.replace(formatter.format(importoComplessivoIntervento), ",", ".");
			pageContext.setAttribute("importoComplessivoIntervento", strImportoComplessivoIntervento,
            	PageContext.REQUEST_SCOPE);
        }
      }
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre i finanziamenti ", e);
    } catch (GestoreException g) {
      throw new JspException("Errore nel determinare i valori di COGDARA, CODLOTT e NUM_APPA", g);
	}
    return null;
  }

}
