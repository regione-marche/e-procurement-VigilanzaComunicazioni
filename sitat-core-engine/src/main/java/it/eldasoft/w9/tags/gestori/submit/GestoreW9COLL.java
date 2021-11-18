package it.eldasoft.w9.tags.gestori.submit;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreW9COLL extends AbstractGestoreChiaveNumericaEntitaW9 {

  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA", "CODLOTT" };
  }

  public String getCampoNumericoChiave() {
    return "NUM_COLL";
  }

  public String getEntita() {
    return "W9COLL";
  }

  public void postDelete(DataColumnContainer arg0) throws GestoreException {

  }

  public void postInsert(DataColumnContainer arg0) throws GestoreException {

  }

  public void postUpdate(DataColumnContainer arg0) throws GestoreException {

  }

  public void preDelete(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
    // gestione schede multiple incarichi professionali
//    GestoreW9INCAMultiplo gest = new GestoreW9INCAMultiplo();
//    gest.setGeneManager(this.geneManager);
//    gest.gestioneW9INCAdaW9(this.getRequest(), status, impl);
	  
	  AbstractGestoreChiaveNumerica GestoreW9INCA = new DefaultGestoreEntitaChiaveNumerica("W9INCA", "NUM_INCA", 
    		new String[] { "CODGARA", "CODLOTT", "NUM" }, this.getRequest());
	  gestisciAggiornamentiRecordSchedaMultipla(status, impl,
			GestoreW9INCA, "W9INCA", new DataColumn[] {
				impl.getColumn("W9COLL.CODGARA"),
				impl.getColumn("W9COLL.CODLOTT"),
				impl.getColumn("W9COLL.NUM_COLL")}, null);
  }

  public void preInsert(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {

    super.preInsert(status, impl);

  }
}
