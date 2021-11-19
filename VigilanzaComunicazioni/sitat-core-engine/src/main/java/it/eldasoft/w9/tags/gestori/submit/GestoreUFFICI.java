package it.eldasoft.w9.tags.gestori.submit;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreUFFICI extends AbstractGestoreChiaveNumerica {

  public String[] getAltriCampiChiave() {
    return null;
  }

  public String getCampoNumericoChiave() {
    return "ID";
  }

  public String getEntita() {
    return "UFFICI";
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

  public void preUpdate(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

}
