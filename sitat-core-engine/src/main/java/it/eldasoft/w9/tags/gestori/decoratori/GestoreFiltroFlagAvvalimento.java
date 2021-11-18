package it.eldasoft.w9.tags.gestori.decoratori;

import it.eldasoft.gene.web.struts.tags.gestori.decoratori.GestoreCampoTabellatoGen;

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

/**
 * Filtro per il tabellato associato al campo Flag Avvallimento.
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreFiltroFlagAvvalimento extends GestoreCampoTabellatoGen {

  /** Query per flag Avvallimento. */
  private final String queryTabellatoFlagAvvallimentoA04 = "Select TAB2TIP, TAB2D2 from TAB2 Where TAB2COD = 'W3z09' and TAB2TIP IN ('0','1','2') Order by TAB2TIP";
  
  /**
   * Costruttore.
   */
  public GestoreFiltroFlagAvvalimento() {
    super(false, "T2");
  }

  @Override
  public SqlSelect getSql() {
    try {
      PageContext page = this.getPageContext();
      
      if (page.getRequest().getParameter("keyW9appa") != null
          && !page.getRequest().getParameter("keyW9appa").equals("")) {

        HashMap< ?, ? > datiRiga = (HashMap< ?, ? >) this.getPageContext().getAttribute(
            "datiRiga", PageContext.REQUEST_SCOPE);
        if (datiRiga != null) {
          return new SqlSelect(queryTabellatoFlagAvvallimentoA04, new Object[] {});
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
