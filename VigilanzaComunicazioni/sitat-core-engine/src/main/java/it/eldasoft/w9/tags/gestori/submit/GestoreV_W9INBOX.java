/**
 * 
 */
package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

/**
 * @author Luca.Giacomazzo
 *
 */
public class GestoreV_W9INBOX extends AbstractGestoreChiaveNumerica {

	public GestoreV_W9INBOX() {
	    super(false);
  }
  /* (non-Javadoc)
   * @see it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica#getCampoNumericoChiave()
   */
  @Override
  public String getCampoNumericoChiave() {
    return "IDCOMUN";
  }

  /* (non-Javadoc)
   * @see it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica#getAltriCampiChiave()
   */
  @Override
  public String[] getAltriCampiChiave() {
    return null;
  }

  /* (non-Javadoc)
   * @see it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita#getEntita()
   */
  @Override
  public String getEntita() {
    return "V_W9INBOX";
  }

  /* (non-Javadoc)
   * @see it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita#preDelete(org.springframework.transaction.TransactionStatus, it.eldasoft.gene.db.datautils.DataColumnContainer)
   */
  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
    
	  
    Long idcomun = datiForm.getLong("V_W9INBOX.IDCOMUN");
    try {
    	this.sqlManager.update("delete from W9INBOX where IDCOMUN=?", new Object[]{ idcomun });
        this.sqlManager.update("delete from W9FLUSSI where IDCOMUN=?", new Object[]{ idcomun });
        this.sqlManager.update("delete from W9FLUSSI_ELIMINATI where IDCOMUN=?", new Object[]{ idcomun });
      } catch (SQLException e) {
        throw new GestoreException("Errore nella cancellazione del record in W9FLUSSI associato al record "
            + "di W9INBOX in cancellazione (IDCOMUN = " + idcomun.longValue() + ")", null, e);
      }
  }

  /* (non-Javadoc)
   * @see it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita#postDelete(it.eldasoft.gene.db.datautils.DataColumnContainer)
   */
  @Override
  public void postDelete(DataColumnContainer datiForm) throws GestoreException {
  
  }

  /* (non-Javadoc)
   * @see it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita#postInsert(it.eldasoft.gene.db.datautils.DataColumnContainer)
   */
  @Override
  public void postInsert(DataColumnContainer datiForm) throws GestoreException {
  
  }

  /* (non-Javadoc)
   * @see it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita#preUpdate(org.springframework.transaction.TransactionStatus, it.eldasoft.gene.db.datautils.DataColumnContainer)
   */
  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {

  }

  /* (non-Javadoc)
   * @see it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita#postUpdate(it.eldasoft.gene.db.datautils.DataColumnContainer)
   */
  @Override
  public void postUpdate(DataColumnContainer datiForm) throws GestoreException {

  }

}