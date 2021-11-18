package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

/**
 * Gestore non standard per il salvataggio dell'associazione fra permessi sulle gare e 
 * le stazioni appaltanti. 
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9CF_MODPERMESSI_SA extends AbstractGestoreEntita {

  public GestoreW9CF_MODPERMESSI_SA() {
    super(false);
  }

  @Override
  public String getEntita() {
    return "W9CF_MODPERMESSI_SA";
  }

  @Override
  public void postDelete(DataColumnContainer arg0) throws GestoreException {
  }

  @Override
  public void preDelete(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {
  }
  
  @Override
  public void postInsert(DataColumnContainer arg0) throws GestoreException {
  }

  @Override
  public void preInsert(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {
  }

  @Override
  public void postUpdate(DataColumnContainer arg0) throws GestoreException {
  }

  @Override
  public void preUpdate(TransactionStatus astatus, DataColumnContainer impl)
      throws GestoreException {
    Long codConf = new Long(1);
    
    try {
      this.sqlManager.update("delete from W9CF_MODPERMESSI_SA where CODCONF=?", new Object[] { codConf } );
      
      int id = 1;
      while (impl.isColumn("W9CF_MODPERMESSI_SA.APPLICA_" + id)) {
        String codein = this.getRequest().getParameter("chiave_" + id);
        if (StringUtils.isNotEmpty(codein)) {
          String applica = impl.getString("W9CF_MODPERMESSI_SA.APPLICA_" + id);
        
          this.sqlManager.update("insert into W9CF_MODPERMESSI_SA (CODCONF, CODEIN, APPLICA) values (?, ?, ?)",
              new Object[] { codConf, codein, applica });
        }
        id++;
      }
    } catch (SQLException sqle) {
      throw new GestoreException("Errore nell'aggiornamento dell'associazione permessi - stazione appaltante (W9CF_MODPERMESSI_SA)", null, sqle);
    }
  }

}
