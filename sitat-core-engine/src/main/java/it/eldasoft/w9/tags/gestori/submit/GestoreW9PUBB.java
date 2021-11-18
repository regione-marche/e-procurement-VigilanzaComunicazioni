package it.eldasoft.w9.tags.gestori.submit;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreW9PUBB extends AbstractGestoreChiaveNumerica {

  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA", "CODLOTT", "NUM_APPA" };
  }

  public String getCampoNumericoChiave() {
    return "NUM_PUBB";
  }

  public String getEntita() {
    return "W9PUBB";
  }

  public void postDelete(final DataColumnContainer arg0) throws GestoreException {

  }

  public void postInsert(final DataColumnContainer arg0) throws GestoreException {

  }

  public void postUpdate(final DataColumnContainer arg0) throws GestoreException {

  }

  public void preDelete(final TransactionStatus arg0, final DataColumnContainer arg1)
      throws GestoreException {

  }

  public void preUpdate(final TransactionStatus status, final DataColumnContainer impl)
      throws GestoreException {

  }

}
