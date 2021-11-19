package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiW9;

/**
 * Gestore di submit per la tabella W9IMPRESE.
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9IMPRESE extends AbstractGestoreChiaveNumericaEntitaW9 {

  private static Logger    logger            = Logger.getLogger(GestoreW9IMPRESE.class);
  
  public GestoreW9IMPRESE()  {
    super(false);
  }
  
  @Override
  public String[] getAltriCampiChiave() {
    return new String[] {"CODGARA", "CODLOTT"};
  }

  @Override
  public String getCampoNumericoChiave() {
    return "NUM_IMPR";
  }

  @Override
  public String getEntita() {
    return "W9IMPRESE";
  }

  @Override
  public void postDelete(DataColumnContainer arg0) throws GestoreException {
  }

  public void preInsert(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

      try {
        // gestione schede multiple imprese invitate / partecipanti ----- MODIFICARE COME UPDATE!!!!!!!!!
        Long numeroRaggrup = null;
        
        long tipoAgg = impl.getLong("W9IMPRESE.TIPOAGG").longValue();
        
        if(!(new Long(3).equals(tipoAgg))){
	          Long maxNumRaggr = (Long) this.sqlManager.getObject(
	              "select max(NUM_RAGG) from W9IMPRESE where CODGARA=? and CODLOTT=? and TIPOAGG is not null and TIPOAGG <> 3 ",
	              new Object[] {impl.getLong("W9IMPRESE.CODGARA"), impl.getLong("W9IMPRESE.CODLOTT") });
	          if (maxNumRaggr != null) { // se son state gia' inserite delle fasi, sono presenti dei record, faccio il max+1
	            numeroRaggrup = new Long(maxNumRaggr.longValue() + 1);
	          } else { // nessun record inserito il max e' 1	
	            numeroRaggrup = new Long(1);
	          }
	    }
        
        long numeroSezioni = impl.getLong("NUMERO_W9IMPRESE").longValue();
  
        // Valorizzazione dei campi NUM_RAGG e PARTECIP per ciascuna sezione multipla
        for (int i = 1; i <= numeroSezioni; i++) {
          impl.setValue("W9IMPRESE.NUM_RAGG_" + i, numeroRaggrup);
        }
        
        AbstractGestoreChiaveNumerica gestoreW9IMPRESE = new DefaultGestoreEntitaChiaveNumerica(
            "W9IMPRESE", "NUM_IMPR", new String[]{"CODGARA", "CODLOTT"}, this.getRequest());
        gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9IMPRESE, "W9IMPRESE",
            new DataColumn[] { impl.getColumn("W9IMPRESE.CODGARA"), impl.getColumn("W9IMPRESE.CODLOTT"),
            impl.getColumn("W9IMPRESE.NUM_IMPR") }, null);
       
        Long maxNumImpr = (Long) this.sqlManager.getObject(
            "select max(NUM_IMPR) from W9IMPRESE where CODGARA=? and CODLOTT=? and TIPOAGG=?",
            new Object[] { impl.getLong("W9IMPRESE.CODGARA"), impl.getLong("W9IMPRESE.CODLOTT"), new Long(tipoAgg) } );
        
        impl.setValue("W9IMPRESE.NUM_IMPR", maxNumImpr);
        
        this.getRequest().setAttribute("codGara", impl.getLong("W9IMPRESE.CODGARA"));
        this.getRequest().setAttribute("codLott", impl.getLong("W9IMPRESE.CODLOTT"));
        
        // Se l'operazione di insert e' andata a buon fine (cioe' nessuna
        // eccezione) inserisco nel request l'attributo RISULTATO valorizzato con
        // "OK", che permettera' alla popup di inserimento ditta di richiamare
        // il refresh della finestra padre e di chiudere se stessa
        this.getRequest().setAttribute("RISULTATO", "OK");
        super.afterInsertEntita(status, impl);
        
      } catch (SQLException sqlEx) {
        logger.error("Errore nel salvataggio di un gruppo di imprese nel determinare il numero di raggruppamento",
            sqlEx);
        
        throw new GestoreException("Errore nel salvataggio di un gruppo di imprese", null, sqlEx);
      }
    }
  
  
  @Override
  public void postInsert(DataColumnContainer arg0) throws GestoreException {
    
  }

  @Override
  public void postUpdate(DataColumnContainer arg0) throws GestoreException {
  }

  @Override
  public void preDelete(TransactionStatus arg0, DataColumnContainer impl) throws GestoreException {

  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

      // gestione schede multiple imprese invitate / partecipanti
      long numeroSezioni = impl.getLong("NUMERO_W9IMPRESE").longValue();
      //DataColumn partecip = impl.getColumn("W9IMPRESE.PARTECIP");
      //DataColumn tipoAggiu = impl.getColumn("W9IMPRESE.TIPOAGG");
      
      long tipoAgg = impl.getLong("W9IMPRESE.TIPOAGG").longValue();
      
      // se provengo da un'impresa singola non dispongo del Numero di Raggruppamento e dovo' calcolarlo
      Long numRagg = null;

      // Lettura del NUM_RAGG: se valorizzato significato che ho modificato un ATI, un consorzio o una GEIE
      // Se non valorizzato significa che ho modificato un 
      String testNumRagg = impl.getColumn("W9IMPRESE.NUM_RAGG").getValue().getStringValue();
      if (StringUtils.isNotEmpty(testNumRagg) && !(new Long(3).equals(tipoAgg))) {
    	  numRagg = new Long(testNumRagg);
      }
      
      if (StringUtils.isEmpty(testNumRagg) && !(new Long(3).equals(tipoAgg))) {
    	  try {
    		  Long maxNumRaggr = (Long) this.sqlManager.getObject("select max(NUM_RAGG) from W9IMPRESE where CODGARA=? and CODLOTT=? and TIPOAGG is not null and TIPOAGG <> 3 ",
		          new Object[] {impl.getLong("W9IMPRESE.CODGARA"), impl.getLong("W9IMPRESE.CODLOTT") });
	          if (maxNumRaggr != null) {
	        	  numRagg = maxNumRaggr.longValue() + 1;
	          } else {
	        	  numRagg = 1L;
	          }
    	  } catch (SQLException sqlEx) {		
    		 logger.error("Errore nel salvataggio di un gruppo di imprese nel determinare il numero di raggruppamento", sqlEx);  		        
    		 throw new GestoreException("Errore nel salvataggio di un gruppo di imprese", null, sqlEx);
    	  }
      }
      
      
      
      //if (partecip.isModified() || tipoAggiu.isModified()) {
        // Valorizzazione dei campi PARTECIP e TIPOAGG per ciascuna sezione multipla
      for (int i = 1; i <= numeroSezioni; i++) {
        impl.setValue("W9IMPRESE.NUM_RAGG_" + i, numRagg);
      }
      //}
      
      AbstractGestoreChiaveNumerica gestoreW9IMPRESE = new DefaultGestoreEntitaChiaveNumerica(
          "W9IMPRESE", "NUM_IMPR", new String[]{"CODGARA", "CODLOTT"}, this.getRequest());

      gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9IMPRESE, "W9IMPRESE",
          new DataColumn[] { impl.getColumn("W9IMPRESE.CODGARA"), impl.getColumn("W9IMPRESE.CODLOTT"),
          impl.getColumn("W9IMPRESE.NUM_IMPR") }, null);

      this.getRequest().setAttribute("codGara", impl.getLong("W9IMPRESE.CODGARA"));
      this.getRequest().setAttribute("codLott", impl.getLong("W9IMPRESE.CODLOTT"));
      
      // Se l'operazione di update e' andata a buon fine (cioe' nessuna
      // eccezione) inserisco nel request l'attributo RISULTATO valorizzato con
      // "OK", che permettera' alla popup di inserimento ditta di richiamare
      // il refresh della finestra padre e di chiudere se stessa
            
      this.getRequest().setAttribute("RISULTATO", "OK");
      
      this.getRequest().setAttribute("numRagg", numRagg);
      
      super.afterUpdateEntita(status, impl);
      
    }
  
}
