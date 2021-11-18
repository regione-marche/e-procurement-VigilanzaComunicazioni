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

public class GestoreCampoRegione extends AbstractGestoreCampo {

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
        + "tabsche where tabcod = 'S2003' and tabcod1 = '05' "
        + "order by tabdesc";

    this.getCampo().addValore("-1", "TUTTE LE REGIONI");

    if (datiRiga != null) {
      String stato = (String) datiRiga.get("STATO");
      if (stato != null && "05".equals(stato)) {
        try {
          List<?> datiTABSCHE = sqlManager.getListVector(selectTABSCHE,
              new Object[] {});
          for (int i = 0; i < datiTABSCHE.size(); i++) {
            String codice = (String) SqlManager.getValueFromVectorParam(
                datiTABSCHE.get(i), 0).getValue();
            String descrizione = (String) SqlManager.getValueFromVectorParam(
                datiTABSCHE.get(i), 1).getValue();
            this.getCampo().addValore(codice.substring(1), descrizione);
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
