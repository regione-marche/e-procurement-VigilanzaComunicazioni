package it.eldasoft.sil.pt.tags.gestori.plugin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestoreOITRI extends AbstractGestorePreload {

  public GestoreOITRI(BodyTagSupportGene tag) {
    super(tag);
  }

  @Override
  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {
  }

  @Override
  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda)
      throws JspException {

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        page, SqlManager.class);
    String codice = "";
    DataColumnContainer key = null;
    Long contri = null;
    Long numoi = null;

    if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
      codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      key = new DataColumnContainer(codice);
      try {
        contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
        numoi = (key.getColumnsBySuffix("NUMOI", true))[0].getValue().longValue();
    
    
        List< ? > datiComuniList = sqlManager.getListVector(
            "select ISTAT from OITRI where CONTRI=? AND NUMOI=?",
            new Object[] { contri, numoi });

        String codComune = "";
        String descrComune = "";
        String descrProvincia = "";
        if (datiComuniList.size() > 0) {
          codComune = ((List< ? >) datiComuniList.get(0)).get(0).toString();
          if (codComune != null && !codComune.equals("")) {
            String sqlEtrazioneDatiComune =
              "select tb1.tabcod3, tabsche.tabdesc " +
               " from tabsche, tabsche tb1, OITRI " +
               "where tabsche.tabcod='S2003' and tabsche.tabcod1='09' " +
                " and tb1.tabcod='S2003' and tb1.tabcod1='07' " +
                " and OITRI.istat = tabsche.tabcod3 and " + 
                  sqlManager.getDBFunction("substr", new String[] {"tabsche.tabcod3", "4", "3"}) + " = " +
                  sqlManager.getDBFunction("substr", new String[] {"tb1.tabcod2", "2", "3"}) +
                " and tabsche.tabcod3=? and oitri.contri=? and oitri.numoi=?";
            List< ? > datiListComune = sqlManager.getListVector(sqlEtrazioneDatiComune,
                new Object[] { codComune, contri, numoi });
            if (datiListComune.size() > 0) {
              descrComune = ((List< ? >) datiListComune.get(0)).get(1).toString();
              descrProvincia = ((List< ? >) datiListComune.get(0)).get(0).toString();
            }
          }
        }

        page.setAttribute("descrComune", descrComune, PageContext.REQUEST_SCOPE);
        page.setAttribute("descrProvincia", descrProvincia, PageContext.REQUEST_SCOPE);

      } catch (SQLException sqle) {
        throw new JspException("Errore nell'esecuzione della query per l'estrazione dei dati ", sqle);
      } catch (Exception exc) {
        throw new JspException("Errore nel caricamento dati ", exc);
      }
    }
  }

}
