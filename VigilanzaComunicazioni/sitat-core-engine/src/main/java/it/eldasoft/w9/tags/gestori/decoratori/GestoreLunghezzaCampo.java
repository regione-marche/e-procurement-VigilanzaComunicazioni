package it.eldasoft.w9.tags.gestori.decoratori;

import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.tags.decorators.campi.AbstractGestoreCampo;

/**
 * Gestore del campo W9FLUSSI.XML per determinare se e' valorizzato o meno.
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreLunghezzaCampo extends AbstractGestoreCampo {

  @Override
  public String getValore(String valore) {
    if (StringUtils.isNotEmpty(valore)) {
      return "1";
    } else {
      return "0";
    }
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
  public String preHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

  @Override
  public String getHTML(boolean visualizzazione, boolean abilitato) {
    return null;
  }

  @Override
  public String postHTML(boolean visualizzazione, boolean abilitato) {
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
  protected void initGestore() {
  }

  @Override
  public String gestisciDaTrova(Vector params, DataColumn col, String conf,
      SqlManager manager) {
    return null;
  }

}
