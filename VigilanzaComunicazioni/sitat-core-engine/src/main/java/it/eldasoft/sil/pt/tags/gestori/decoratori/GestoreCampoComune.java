package it.eldasoft.sil.pt.tags.gestori.decoratori;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.tags.decorators.campi.AbstractGestoreCampo;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestoreCampoComune extends AbstractGestoreCampo {

  public String gestisciDaTrova(Vector params, DataColumn col, String conf,
      SqlManager manager) {
    return null;
  }

  public String getClasseEdit() {
    return null;
  }

  public String getClasseVisua() {
    return null;
  }

  public String getHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

  public String getValore(String valore) {
    return null;
  }

  public String getValorePerVisualizzazione(String valore) {
    return null;
  }

  public String getValorePreUpdateDB(String valore) {
    return null;
  }

  protected void initGestore() {

    HashMap datiRiga = (HashMap) this.getPageContext().getAttribute("datiRiga",
        PageContext.REQUEST_SCOPE);

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        this.getPageContext(), SqlManager.class);

    this.getCampo().setTipo("ET10");
    this.getCampo().getValori().clear();

    String selectTABSCHE = "select tabcod3, tabdesc from "
        + "tabsche where tabcod = 'S2003' and tabcod1 = '09' "
        + "and tabcod3 like ? order by tabdesc";

    this.getCampo().addValore("-1", "TUTTI I COMUNI");

    if (datiRiga != null) {

      String regione = (String) datiRiga.get("REGIONE");
      String provincia = (String) datiRiga.get("PROVINCIA");

      if (!"".equals(regione)
          && !"-1".equals(regione)
          && !"".equals(provincia)
          && !"-1".equals(provincia)) {
        try {
          List<?> datiTABSCHE = sqlManager.getListVector(selectTABSCHE,
              new Object[] { regione + provincia + "%" });
          for (int i = 0; i < datiTABSCHE.size(); i++) {
            String tabcod3 = (String) SqlManager.getValueFromVectorParam(
                datiTABSCHE.get(i), 0).getValue();
            String tabdesc = (String) SqlManager.getValueFromVectorParam(
                datiTABSCHE.get(i), 1).getValue();
            this.getCampo().addValore(tabcod3, tabdesc);
          }
        } catch (SQLException e) {

        }
      }
    }
  }

  public String postHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

  public String preHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

}
