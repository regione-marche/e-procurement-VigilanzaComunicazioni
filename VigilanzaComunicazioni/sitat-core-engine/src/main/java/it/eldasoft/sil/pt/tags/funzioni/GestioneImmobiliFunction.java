package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.spring.UtilitySpring;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GestioneImmobiliFunction extends AbstractFunzioneTag {

  public GestioneImmobiliFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
	  Long contri = null, conint = null, numoi = null;
    String codice = params[0].toString();
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    DataColumnContainer key = new DataColumnContainer(codice);
    try {
      contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
    if (key.getColumnsBySuffix("CONINT", true).length > 0) {
    	conint = (key.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();
    } else {
    	conint =  new Long(0);
    }
    
    if (key.getColumnsBySuffix("NUMOI", true).length > 0) {
      numoi = (key.getColumnsBySuffix("NUMOI", true))[0].getValue().longValue();
    } else {
      numoi =  new Long(0);
    }
    
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

      List listaImmobiliDaTrasfInterv = null;
      if (contri != null) {
        Long norma = (Long) sqlManager.getObject(
            "select NORMA from PIATRI where CONTRI = ? ", new Object[] { contri });

        //Aggiunta condizione per fabbisogni(contri = 0) ---> gestire come nuova normativa(norma=2)
        if ( (new Long(2).equals(norma)) || (new Long(0).equals(contri)) ) {
          listaImmobiliDaTrasfInterv = sqlManager.getListVector(
              "select CONTRI, NUMIMM, ANNIMM, DESIMM, VALIMM," +
              "CUIIMM, COMIST, tabsche.tabdesc, tb1.tabcod3, tb1.tabdesc, tabsche.tabcod3, tabsche.tabcod4," +
              " NUTS, TITCOR, IMMDISP, PROGDISM, TIPDISP," +
              "VA1IMM, VA2IMM, VA3IMM, VA9IMM "
                  + "from IMMTRAI "
                  + "left join TABSCHE on comist = tabsche.tabcod3 "
                  + "left join TABSCHE TB1 on "
                  + "tabsche.tabcod='S2003' "
                  + "and tabsche.tabcod1='09' "
                  + "and tb1.tabcod='S2003' "
                  + "and tb1.tabcod1='07' "
                  + "and " + sqlManager.getDBFunction("SUBSTR", new String[]{"tabsche.tabcod3","4","3"})
                  + " = " + sqlManager.getDBFunction("SUBSTR", new String[]{"tb1.tabcod2","2","3"})
                  + " where CONTRI = ? "
                  + "and CONINT = ? "
                  + "and coalesce(NUMOI,0) = ? "
                  + "order by NUMIMM",
                  new Object[] { contri, conint, numoi });
        } else {
          listaImmobiliDaTrasfInterv = sqlManager.getListVector(
              "select CONTRI, NUMIMM, ANNIMM, DESIMM, PROIMM, VALIMM "
                  + "from IMMTRAI "
                  + "where CONTRI = ? "
                  + "and CONINT = ? order by NUMIMM",
              new Object[] { contri, conint });
        }

      }


      pageContext.setAttribute("listaImmobiliDaTrasfInterv",
          listaImmobiliDaTrasfInterv, PageContext.REQUEST_SCOPE);

    } catch (Exception e) {
      throw new JspException("Errore nell'estrarre gli immobili per "
          + "l'intervento "
          + contri, e);
    }

    return null;
  }

}
