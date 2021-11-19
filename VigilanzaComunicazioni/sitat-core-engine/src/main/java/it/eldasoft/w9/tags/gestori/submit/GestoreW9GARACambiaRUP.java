package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

public class GestoreW9GARACambiaRUP extends AbstractGestoreChiaveNumerica {

  @Override
  public String[] getAltriCampiChiave() {
    return null;
  }

  @Override
  public String getCampoNumericoChiave() {
    return "CODGARA";
  }

  @Override
  public String getEntita() {
    return "W9GARA";
  }

  @Override
  public void postDelete(DataColumnContainer arg0) throws GestoreException {
  }

  @Override
  public void postInsert(DataColumnContainer impl) throws GestoreException {
  }

  @Override
  public void postUpdate(DataColumnContainer impl) throws GestoreException {

  }

  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {
  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {

  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {

    Long codgara = impl.getLong("W9GARA.CODGARA");
    String rup = impl.getString("W9GARA.RUP");

    this.getRequest().setAttribute("CODGARA", codgara.toString());

    try {

      this.sqlManager.update("update w9lott set rup = ? where codgara = ?",
          new Object[] { rup, codgara });
      this.getRequest().setAttribute("RISULTATO", "CAMBIARUPESEGUITO");


    } catch (SQLException e) {
      this.getRequest().setAttribute("RISULTATO", "ERRORI");
      throw new GestoreException(e.getMessage(), null, e);

    } catch (Throwable t) {
      this.getRequest().setAttribute("RISULTATO", "ERRORI");
      throw new GestoreException("Errore generico durante il cambiamento del RUP", null, t);
    }

  }

}