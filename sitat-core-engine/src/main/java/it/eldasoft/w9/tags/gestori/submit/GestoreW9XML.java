package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreW9XML extends AbstractGestoreEntita {

  @Override
  public String getEntita() {
    return "W9XML";
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
    
    // si ricava il valore delle chiavi primarie di W9XML
    Long codgara = impl.getColumn("W9XML.CODGARA").getValue().longValue();
    Long codlott = impl.getColumn("W9XML.CODLOTT").getValue().longValue();
    Long numeroXml = impl.getColumn("W9XML.NUMXML").getValue().longValue();
            
    try {
      this.getGeneManager().getSql().execute("DELETE FROM W9XMLANOM where " +
      		"CODGARA=" + codgara.toString() +
          " and CODLOTT=" + codlott.toString() +
          " and NUMXML=" + numeroXml.toString());
    } catch (SQLException e) {
      throw new GestoreException("Errore durante la cancellazione delle anomalie collegate ad un feedback",
          "cancellazione.feedback", e);
    }
    
  }

  @Override
  public void preInsert(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {
  }

  @Override
  public void preUpdate(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {
  }

}
