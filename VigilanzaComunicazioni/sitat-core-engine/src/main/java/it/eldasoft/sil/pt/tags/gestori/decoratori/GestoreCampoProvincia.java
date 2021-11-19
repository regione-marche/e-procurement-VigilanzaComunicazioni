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

public class GestoreCampoProvincia extends AbstractGestoreCampo {

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

    String selectTABSCHE = "select tabcod2, tabdesc from "
        + " tabsche where tabcod = 'S2003' and tabcod1 = '07'"
        + " order by tabdesc ";

    this.getCampo().addValore("-1", "TUTTE LE PROVINCE");
    
    if (datiRiga != null) {
      String regione = (String) datiRiga.get("REGIONE");
      if (!"-1".equals(regione) && !"".equals(regione)) {
        try {
          List<?> datiTABSCHE = sqlManager.getListVector(selectTABSCHE,
              new Object[] {});
          for (int i = 0; i < datiTABSCHE.size(); i++) {
            String tabcod2 = (String) SqlManager.getValueFromVectorParam(
                datiTABSCHE.get(i), 0).getValue();
            String tabdesc = (String) SqlManager.getValueFromVectorParam(
                datiTABSCHE.get(i), 1).getValue();
    
            // Verifico se la provincia appartiene ad un determinata regione
            // controllando i codici nella lista dei comuni
            String provincia = tabcod2.substring(1);
            String selectTABSCHE_Comune = "select count(*) from tabsche "
                + "where tabcod = 'S2003' "
                + "and tabcod1 = '09' "
                + "and tabcod3 like ?";
    
            Long conteggio = (Long) sqlManager.getObject(selectTABSCHE_Comune,
                new Object[] { regione + provincia + "%" });
            if (conteggio != null && conteggio.longValue() > 0) {
              this.getCampo().addValore(tabcod2.substring(1), tabdesc);
            }
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
