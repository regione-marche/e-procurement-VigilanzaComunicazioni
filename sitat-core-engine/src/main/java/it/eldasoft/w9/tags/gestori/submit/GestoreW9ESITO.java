package it.eldasoft.w9.tags.gestori.submit;


import java.sql.SQLException;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

public class GestoreW9ESITO extends AbstractGestoreChiaveNumericaEntitaW9 {

  Logger logger = Logger.getLogger(GestoreW9ESITO.class);
  
  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA" };
  }

  public String getCampoNumericoChiave() {
    return "CODLOTT";
  }

  public String getEntita() {
    return "W9ESITO";
  }

  public void postDelete(DataColumnContainer impl) throws GestoreException {
  }

  public void postInsert(DataColumnContainer impl) throws GestoreException {
  }

  public void postUpdate(DataColumnContainer arg0) throws GestoreException {
  }

  public void preDelete(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
  }

  public void preInsert(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
    
    try {
      this.salvaPubblicazioneEsito(status, impl);
    } catch (SQLException se) {
      logger.error("Errore nel salvataggio dati in W9PUES durante l'insert della scheda dell'esito (W9ESITO)", se);
      throw new GestoreException("Errore nel salvataggio di pubblicazione",
          "gestione.esitiPubblicazioneGara" , se);
    }
  }

  /**
   * Metodo per l'insert/update in W9PUES
   * 
   * @param impl
   * @throws SQLException
   * @throws GestoreException 
   */
  private void salvaPubblicazioneEsito(TransactionStatus status, DataColumnContainer impl) throws SQLException, GestoreException {
    if (impl.isModifiedTable("W9PUES")) {
      
      impl.setValue("W9PUES.CODGARA", impl.getLong("W9ESITO.CODGARA"));
      impl.setOriginalValue("W9PUES.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, impl.getLong("W9ESITO.CODGARA")));
      
      impl.setValue("W9PUES.CODLOTT", impl.getLong("W9ESITO.CODLOTT"));
      impl.setOriginalValue("W9PUES.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, impl.getLong("W9ESITO.CODLOTT")));
      
      impl.setValue("W9PUES.NUM_INIZ", new Long(1));
      impl.setOriginalValue("W9PUES.NUM_INIZ", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
      
      // Inserimento dell'occorrenza in W9PUES
      impl.setValue("W9PUES.NUM_PUES", new Long(1));
      impl.setOriginalValue("W9PUES.NUM_PUES", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
      
      long occorrenzePUES = this.geneManager.countOccorrenze("W9PUES", " CODGARA=? and CODLOTT=? and NUM_INIZ=1 and NUM_PUES=1",
          new Object[]{ impl.getLong("W9ESITO.CODGARA"), impl.getLong("W9ESITO.CODLOTT") });
      
      if (occorrenzePUES > 0) {
        // Aggiornamento dell'occorrenza in W9PUES
        this.update(status, impl, new GestoreW9PUES());
      } else {
        // Inserimento in W9PUES
        this.inserisci(status, impl, new GestoreW9PUES());
      }
    }
  }

  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
    
    try {
      this.salvaPubblicazioneEsito(status, impl);
    } catch (SQLException se) {
      logger.error("Errore nel salvataggio dati in W9PUES durante l'update della scheda dell'esito (W9ESITO)", se);
      throw new GestoreException("Errore nel salvataggio di pubblicazione", 
          "gestione.esitiPubblicazioneGara", se);
    }
  }
 
}