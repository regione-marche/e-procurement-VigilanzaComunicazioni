package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Funzione per il caricamento dei lotti ed esiti per gare degli enti nazionali o sotto i 40.000 euro.
 * 
 * @author Luca.Giacomazzo
 */
public class GetLottiEsitiEntinazFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public GetLottiEsitiEntinazFunction() {
    super(1, new Class[] { String.class });
  }

  /**
   * Implementazione del metodo AbstractFunzioneTag.function.
   * 
   * @param pageContext PageContext
   * @param params Array di Object
   * @throws JspException JspException
   * 
   */
  @Override
  public String function(final PageContext pageContext, final Object[] params)
  throws JspException {

    String codice = params[0].toString();
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }

    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }

    DataColumnContainer key = new DataColumnContainer(codice);
    Long codgara = (Long) (key.getColumnsBySuffix("CODGARA", true))[0].getValue().getValue();

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

    try {
      List< ? > listaLottiEsiti = sqlManager.getListVector(
          "select CODGARA, CODLOTT, CIG, OGGETTO, IMPORTO_TOT, CPV, TABCPV.CPVDESC as CPVDESC, ID_CATEGORIA_PREVALENTE, "
          +       "CLASCAT, IMP_AGG, IMPORTO_AGGIUDICAZIONE, DATA_VERB_AGGIUDICAZIONE, "
          +       "(select 1 from W9LOTT_ENTINAZ w2 WHERE w2.FILE_ALLEGATO is not null AND " + sqlManager.getDBFunction("length", new String[] { "w2.FILE_ALLEGATO" }) + " > 0 and w2.CODGARA = w1.CODGARA and w2.CODLOTT = w1.CODLOTT) as CTRLBLOB "
          +  "from W9LOTT_ENTINAZ w1 left outer join TABCPV on w1.CPV = TABCPV.CPVCOD4 "
          + "where w1.CODGARA = ? "
          + "order by w1.CODLOTT asc", new Object[]{ codgara });

      pageContext.setAttribute("listaLottiEsiti", listaLottiEsiti, PageContext.REQUEST_SCOPE);

    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre gli incarichi professionali ", e);
    }
    return null;
  }

}
