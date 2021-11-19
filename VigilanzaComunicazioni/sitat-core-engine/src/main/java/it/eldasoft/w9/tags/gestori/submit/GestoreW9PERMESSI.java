package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;

/**
 * Gestore non standard per salvataggio occorrenze in W9PERMESSI.
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9PERMESSI extends AbstractGestoreEntita {

  /**
   * Costruttore di default.
   */
  public GestoreW9PERMESSI() {
    super(false);
  }

  @Override
  public String getEntita() {
    return null;
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
  public void preInsert(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {
  }

  @Override
  public void preUpdate(TransactionStatus arg0, DataColumnContainer impl)
      throws GestoreException {
    Long codGara = null;
    Long numReg  = null;
    Long permesso = null;
    
    try {
      // Modifica dei permessi
        
      for (int i=1; i <= 21; i++) {
        codGara = impl.getLong("W9PERMESSI.CODGARA_" + i);
        numReg  = impl.getLong("W9PERMESSI.NUMREG_" + i);
        permesso = impl.getLong("W9PERMESSI.PERMESSO_" + i);
        this.sqlManager.update("UPDATE W9PERMESSI set PERMESSO=? where CODGARA=? and NUMREG=?",
          new Object[] { permesso, codGara, numReg  } );
      }  
    } catch (SQLException sqle) {
      throw new GestoreException("Errore nell'aggiornamento/attribuzione dei permessi (W9PERMESSI)", null, sqle);
    }
  }

}
