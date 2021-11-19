package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import org.springframework.transaction.TransactionStatus;

public class GestoreW9MISSIC extends AbstractGestoreChiaveNumericaEntitaW9 {

  @Override
  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA", "CODLOTT" };
  }

  @Override
  public String getCampoNumericoChiave() {
    return "NUM_MISS";
  }

  @Override
  public String getEntita() {
    return "W9MISSIC";
  }

  @Override
  public void postDelete(DataColumnContainer arg0) throws GestoreException {

  }

  @Override
  public void postInsert(DataColumnContainer arg0) throws GestoreException {

  }

  @Override
  public void postUpdate(DataColumnContainer arg0) throws GestoreException {

  }

  @Override
  public void preDelete(TransactionStatus arg0, DataColumnContainer impl)
  throws GestoreException {

  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {

    GestoreW9DOCFASE gestoreW9DOCFASE = new GestoreW9DOCFASE();
    AbstractGestoreChiaveNumerica gestore = new DefaultGestoreEntitaChiaveNumerica(
        "W9DOCFASE", "NUMDOC", new String[] { "CODGARA", "CODLOTT",
            "FASE_ESECUZIONE", "NUM_FASE" }, this.getRequest());
    gestoreW9DOCFASE.setForm(this.getForm());
    gestoreW9DOCFASE.gestisciAggiornamentiRecordSchedaMultipla(status, impl,
        gestore, "W9DOCFASE", new DataColumn[] {impl.getColumn("W9MISSIC.CODGARA"), impl.getColumn("W9MISSIC.CODLOTT"), impl.getColumn("W9FASI.FASE_ESECUZIONE"), impl.getColumn("W9MISSIC.NUM_MISS") }, null);

  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {

    super.preInsert(status, impl);
    
    GestoreW9DOCFASE gestoreW9DOCFASE = new GestoreW9DOCFASE();
    
    AbstractGestoreChiaveNumerica gestore = new DefaultGestoreEntitaChiaveNumerica(
        "W9DOCFASE", "NUMDOC", new String[] { "CODGARA", "CODLOTT",
            "FASE_ESECUZIONE", "NUM_FASE" }, this.getRequest());
    gestoreW9DOCFASE.setForm(this.getForm());
    gestoreW9DOCFASE.gestisciAggiornamentiRecordSchedaMultipla(status, impl,
        gestore, "W9DOCFASE", new DataColumn[] {impl.getColumn("W9MISSIC.CODGARA"), impl.getColumn("W9MISSIC.CODLOTT"), impl.getColumn("W9FASI.FASE_ESECUZIONE"), impl.getColumn("W9MISSIC.NUM_MISS") }, null);

    
  }
}
