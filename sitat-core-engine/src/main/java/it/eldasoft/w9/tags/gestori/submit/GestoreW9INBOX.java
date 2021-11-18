package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

/**
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9INBOX extends AbstractGestoreChiaveNumerica {

  public GestoreW9INBOX() {
	super(false);
  }
	
  @Override
  public String getCampoNumericoChiave() {
    return "IDCOMUN";
  }

  @Override
  public String[] getAltriCampiChiave() {
    return null;
  }

  @Override
  public String getEntita() {
    return "W9INBOX";
  }

  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
    // Cancellazione del record di W9FLUSSI associato al record di W9INBOX in cancellazione
    // (i record sono legati attraverso il campo IDCOMUN).
    Long idcomun = datiForm.getLong("W9INBOX.IDCOMUN");

    try {
      this.sqlManager.update("delete from W9FLUSSI where IDCOMUN=?", new Object[]{ idcomun });
      this.sqlManager.update("delete from W9FLUSSI_ELIMINATI where IDCOMUN=?", new Object[]{ idcomun });
      this.sqlManager.update("delete from W9INBOX where IDCOMUN=?", new Object[]{ idcomun });
    } catch (SQLException e) {
      throw new GestoreException("Errore nella cancellazione del record in W9FLUSSI associato al record "
          + "di W9INBOX in cancellazione (IDCOMUN = " + idcomun.longValue() + ")", null, e);
    }
  }

  @Override
  public void postDelete(DataColumnContainer datiForm) throws GestoreException {
  
  }

  @Override
  public void postInsert(DataColumnContainer datiForm) throws GestoreException {
  
  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {

  }

  @Override
  public void postUpdate(DataColumnContainer datiForm) throws GestoreException {

  }

}