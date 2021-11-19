package it.eldasoft.w9.tags.gestori.submit;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreW9PUES extends AbstractGestoreChiaveNumerica {

  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA", "CODLOTT", "NUM_INIZ" };
  }

  public String getCampoNumericoChiave() {
    return "NUM_PUES";
  }

  public String getEntita() {
    return "W9PUES";
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
