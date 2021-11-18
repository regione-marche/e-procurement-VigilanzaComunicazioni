package it.eldasoft.sil.pt.tags.gestori.submit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

/**
 * Gestore per il cambio di Utente / Stazione appaltante per i Fabbisogni
 * 
 * @author Mirco.Franzoni
 */
public class GestoreCambiaUtenteSA extends AbstractGestoreEntita {

  @Override
  public String getEntita() {
    return "FABBISOGNI";
  }

  public GestoreCambiaUtenteSA(){
    super(false);
  }
  
  @Override
  public void postDelete(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void postInsert(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void postUpdate(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
    
    try {
    	String archiviFiltrati = ConfigManager.getValore("it.eldasoft.associazioneUffintAbilitata.archiviFiltrati");
    	boolean tecniFiltrato = (archiviFiltrati.indexOf("TECNI;") != -1);
      String listafabbisogni = UtilityStruts.getParametroString(this.getRequest(), "listafabbisogni");
      String[] chiavi = new String[] {};
      chiavi = listafabbisogni.split(";;");
      String codein = datiForm.getString("FABBISOGNI.CODEIN");
      String groupUtenti = UtilityStruts.getParametroString(this.getRequest(), "groupUtenti");
      Long syscon = new Long(groupUtenti);
      for (int i = 0; i < chiavi.length; i++) {
          DataColumnContainer fabbisogniItem = new DataColumnContainer(
                  chiavi[i]);

          Long conint = (fabbisogniItem.getColumnsBySuffix("CONINT", true))[0]
                  .getValue().longValue();
        
          this.sqlManager.update("UPDATE fabbisogni SET codein = ? , syscon = ? WHERE conint = ? ", new Object[] {codein, syscon, conint });
          if (tecniFiltrato) {
       	  		String codiceTecnicoSorgente = (String) this.sqlManager.getObject("select CODRUP from INTTRI where CONTRI=0 and CONINT = ?", new Object[] { conint });
        	    String codiceTecnicoDestinazione = null;
        	    if (StringUtils.isNotEmpty(codiceTecnicoSorgente)) {
        	          codiceTecnicoDestinazione = (String) this.sqlManager.getObject(
        	              "select CODTEC from TECNI where CGENTEI=? and CFTEC=(select CFTEC from TECNI where CODTEC=?)",
        	              new Object[] { codein, codiceTecnicoSorgente });
        	          if (StringUtils.isEmpty(codiceTecnicoDestinazione)) {
        	            // Non esiste il tecnico associato alla nuova Stazione appaltante.
        	            // Si provvede ad inserirne uno, copaiando i dati dal tecnico sorgente
        	            DataColumnContainer dccTecni = new DataColumnContainer(this.sqlManager, "TECNI",
        	                "select CODTEC, CGENTEI, CFTEC, CITTEC, COGTEI, NOMETEI, NOMTEC, INDTEC, NCITEC, LOCTEC, CAPTEC, TELTEC, " +
        	                "FAXTEC, EMATEC, PROTEC, SYSCON from TECNI where CODTEC=? ", new Object[] { codiceTecnicoSorgente });
        	      
        	            this.copiaValori(dccTecni);
        	            dccTecni.getColumn("TECNI.CODTEC").setChiave(true);
        	            codiceTecnicoDestinazione = this.geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
        	            dccTecni.getColumn("TECNI.CODTEC").setObjectValue(codiceTecnicoDestinazione);
        	            dccTecni.getColumn("TECNI.CGENTEI").setObjectValue(codein);
        	            dccTecni.insert("TECNI", this.sqlManager);
        	          }
        	    }
        	    this.sqlManager.update("update INTTRI set CODRUP = ? where CONINT = ? ", new Object[] {codiceTecnicoDestinazione , conint });
          }
      } 
      
      this.getRequest().setAttribute("RISULTATO", "CAMBIAUTENTESAESEGUITO");
    } 
    catch (Exception ex) {
        this.getRequest().setAttribute("RISULTATO", "KO");
        throw new GestoreException("Errore durante l'aggiornamento Utente e SA per i fabbisogni", null, ex);
      }
  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
  }

  private void copiaValori(DataColumnContainer dcc) throws GestoreException {
	    HashMap<String, DataColumn> hm = dcc.getColonne();
	    Set<String> set = hm.keySet();
	    Iterator<String> iter = set.iterator();
	    while(iter.hasNext()) {
	      String tmp = iter.next();
	      dcc.setValue(tmp, dcc.getColumn(tmp).getOriginalValue().getValue());
	      dcc.getColumn(tmp).setObjectOriginalValue(null);
	    }
  }
}
