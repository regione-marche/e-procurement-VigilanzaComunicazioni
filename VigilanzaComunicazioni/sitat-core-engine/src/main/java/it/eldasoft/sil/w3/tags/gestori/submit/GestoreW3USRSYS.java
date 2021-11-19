package it.eldasoft.sil.w3.tags.gestori.submit;


import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreW3USRSYS extends AbstractGestoreEntita {

  public String getEntita() {
    return "W3USRSYS";
  }

  public void postDelete(DataColumnContainer datiForm) throws GestoreException {

  }

  public void postInsert(DataColumnContainer datiForm) throws GestoreException {

  }

  public void postUpdate(DataColumnContainer datiForm) throws GestoreException {

  }

  public void preDelete(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {

	  
	    Long syscon = datiForm.getLong("W3USRSYS.SYSCON");
	    String codrup = datiForm.getString("W3USRSYS.RUP_CODTEC");

	    this.getGeneManager().deleteTabelle(new String[] { "W3USRSYSCOLL"},
	        "SYSCON = ? AND RUP_CODTEC = ?", new Object[] { syscon, codrup });

	    
  }

  public void preInsert(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
	  
	  datiForm.addColumn("W3USRSYS.SIMOGWSUSER", datiForm.getString("TECNI.CFTEC"));

  }

  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {

  }

}
