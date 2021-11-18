package it.eldasoft.w9.tags.gestori.decoratori;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.Tabellato;
import it.eldasoft.gene.tags.decorators.campi.AbstractGestoreCampo;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;

/**
 * 
 * @author luca.giacomazzo
 */
public class GestoreCampoW9ConcIdMotivoInterr extends AbstractGestoreCampo {

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
    this.getCampo().setTipo("EN5");
    this.getCampo().getValori().clear();
    
    this.getCampo().addValore("","");
    
    String key = (String) this.getPageContext().getAttribute("key", PageContext.REQUEST_SCOPE);
    DataColumnContainer dcc = new DataColumnContainer(key);
    Long codGara = (Long) (dcc.getColumnsBySuffix("CODGARA", true))[0].getValue().getValue();
    Long codLott = (Long) (dcc.getColumnsBySuffix("CODLOTT", true))[0].getValue().getValue();
    Long numappa = new Long(this.getPageContext().getSession().getAttribute("aggiudicazioneSelezionata").toString());
    //String codiceGara= key.split(";")[0].substring(key.split(";")[0].indexOf(":")+1);
    
    if (codGara != null) {
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          this.getPageContext(), SqlManager.class);
      
      try {
    	  boolean noW9INIZ = false;
    	  if (! UtilitySITAT.existsFase(codGara, codLott, numappa, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, sqlManager) &&
    	            ! UtilitySITAT.existsFase(codGara, codLott, numappa, CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO, sqlManager) &&
    	              ! UtilitySITAT.existsFase(codGara, codLott, numappa, CostantiW9.STIPULA_ACCORDO_QUADRO, sqlManager)) {
    		  noW9INIZ = true;
    	  }
        int versioneSimog = UtilitySITAT.getVersioneSimog(sqlManager, codGara); 

        List<Tabellato> listaTabellati = null;
        if (versioneSimog == 1) {
        	if (noW9INIZ) {
        		listaTabellati = (List<Tabellato>) sqlManager.getElencoTabellati(
        	              "select TAB1TIP, TAB1DESC from TAB1 where TAB1COD='W3013' and TAB1TIP in (1,3) order by TAB1TIP asc", null);
        	} else {
        		listaTabellati = (List<Tabellato>) sqlManager.getElencoTabellati(
        	              "select TAB1TIP, TAB1DESC from TAB1 where TAB1COD='W3013' and TAB1TIP <= 5 order by TAB1TIP asc", null);
        	}
        } else {
        	if (noW9INIZ) {
        		listaTabellati = (List<Tabellato>) sqlManager.getElencoTabellati(
        	              "select TAB1TIP, TAB1DESC from TAB1 where TAB1COD='W3013' and TAB1TIP in (1,7) order by TAB1TIP asc", null);
        	} else {
        		listaTabellati = (List<Tabellato>) sqlManager.getElencoTabellati(
        	              "select TAB1TIP, TAB1DESC from TAB1 where TAB1COD='W3013' and TAB1ARC is null order by TAB1TIP asc", null);
        	}
          
        }
        if (listaTabellati != null && listaTabellati.size() > 0) {
          for (int i=0; i < listaTabellati.size(); i++) {
            Tabellato tabellato = listaTabellati.get(i);
            this.getCampo().addValore(tabellato.getTipoTabellato(), tabellato.getDescTabellato());
          }
        }
      } catch (SQLException sqle) {
        
      }
    }
  }

  @Override
  public String gestisciDaTrova(Vector params, DataColumn col, String conf, SqlManager manager) {
    return null;
  }

}
