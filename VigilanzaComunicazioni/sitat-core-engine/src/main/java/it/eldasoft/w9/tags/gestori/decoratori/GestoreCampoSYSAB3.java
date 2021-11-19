package it.eldasoft.w9.tags.gestori.decoratori;

import java.util.Vector;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.tags.decorators.campi.AbstractGestoreCampo;

/**
 * Gestore campo che memorizza i privilegi sui contratti.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class GestoreCampoSYSAB3 extends AbstractGestoreCampo {

  @Override
  public String gestisciDaTrova(Vector params, DataColumn col, String conf,
      SqlManager manager) {
    return null;
  }

  @Override
  public String getClasseEdit() {
    return null;
  }

  @Override
  public String getClasseVisua() {
    return null;
  }

  @Override
  public String getHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

  @Override
  public String getValore(String valore) {
    return null;
  }

  @Override
  public String getValorePerVisualizzazione(String valore) {
    return null;
  }

  @Override
  public String getValorePreUpdateDB(String valore) {
    return null;
  }

  @Override
  protected void initGestore() {
    this.getCampo().setTipo("ET2");
    this.getCampo().getValori().clear();
    this.getCampo().addValore("", "");
    this.getCampo().addValore("A", "Amministratore (Accesso completo su tutti i contratti)");
    this.getCampo().addValore("C", "Compilatore (Accesso su tutti i contratti senza facoltà di invio)");
    this.getCampo().addValore("U", "Utente (Accesso consentito solo ai propri contratti)");  }

  @Override
  public String postHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

  @Override
  public String preHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

}
