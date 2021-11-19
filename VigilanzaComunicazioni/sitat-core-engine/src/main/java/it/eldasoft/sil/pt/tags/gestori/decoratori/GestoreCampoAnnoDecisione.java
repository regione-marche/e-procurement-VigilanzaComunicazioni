package it.eldasoft.sil.pt.tags.gestori.decoratori;


import java.util.Vector;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.tags.decorators.campi.AbstractGestoreCampo;
import it.eldasoft.utils.utility.UtilityDate;

public class GestoreCampoAnnoDecisione extends AbstractGestoreCampo {

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

    this.getCampo().setTipo("EN4");
    this.getCampo().getValori().clear();

    int anno_corrente = UtilityDate.getDataOdiernaAsDate().getYear();
    anno_corrente += 1900;

    this.getCampo().addValore("", "");
    
    for (int i = -4; i < 5; i++) {
      this.getCampo().addValore("" + (anno_corrente + i), "" + (anno_corrente + i));
    }
  }

  public String postHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

  public String preHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

}
