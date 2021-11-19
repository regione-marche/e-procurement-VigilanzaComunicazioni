package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

/**
 * Gestore di salvataggio dei permessi di un modello
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9CF_REGPERMESSI extends AbstractGestoreEntita {

  public GestoreW9CF_REGPERMESSI() {
    super(false);
  }
  
  @Override
  public String getEntita() {
    return "W9CF_REGPERMESSI";
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
  public void preDelete(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {
  }

  @Override
  public void preInsert(TransactionStatus aStatus, DataColumnContainer impl)
      throws GestoreException {
  }

  @Override
  public void preUpdate(TransactionStatus aStatus, DataColumnContainer impl)
      throws GestoreException {
    Long codConf = null;
    Long numReg  = null;
    Long permesso = null;
    
    try {
      for (int i=1; i <= 21; i++) {
        codConf = impl.getLong("W9CF_REGPERMESSI.CODCONF_" + i);
        numReg  = impl.getLong("W9CF_REGPERMESSI.NUMREG_" + i);
        permesso = impl.getLong("W9CF_REGPERMESSI.PERMESSO_" + i);
        this.sqlManager.update("UPDATE W9CF_REGPERMESSI set PERMESSO=? where CODCONF=? and NUMREG=?",
          new Object[] { permesso, codConf, numReg  } );
      }
    } catch (SQLException sqle) {
      throw new GestoreException("Errore nell'aggiornamento dei permessi (W9CF_REGPERMESSI)", null, sqle);
    }
  }

}
