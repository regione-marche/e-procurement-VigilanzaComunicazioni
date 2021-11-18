package it.eldasoft.w9.tags.gestori.submit;
 
import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreW9INIZ extends AbstractGestoreChiaveNumericaEntitaW9 {

  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA", "CODLOTT" };
  }

  public String getCampoNumericoChiave() {
    return "NUM_INIZ";
  }

  public String getEntita() {
    return "W9INIZ";
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

  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {

    // Gestione dei dati della pubblicazione 
    if (impl.isModifiedTable("W9PUES")) {
      try {
        this.salvaPubblicazioneEsito(status, impl);
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore nella gestione della sezione 'Pubblicazione esito di gara'",
            "gestione.esitiPubblicazioneGara", e);
      }
    }
    
    // Gestione dei dati della scheda sicurezza
    if (impl.isColumn("W9SIC.CODGARA")) {
      try {
        if (impl.getLong("W9SIC.CODGARA") == null) {
          impl.setValue("W9SIC.CODGARA", impl.getLong("W9INIZ.CODGARA"));
          impl.setValue("W9SIC.CODLOTT", impl.getLong("W9INIZ.CODLOTT"));
          impl.setValue("W9SIC.NUM_INIZ", impl.getLong("W9INIZ.NUM_INIZ"));
          AbstractGestoreChiaveNumerica gestoreW9SIC = new DefaultGestoreEntitaChiaveNumerica("W9SIC", "NUM_SIC", new String[] { "CODGARA", "CODLOTT" }, this.getRequest());
  		  this.inserisci(status, impl, gestoreW9SIC);
        } else {
          impl.update("W9SIC", this.getSqlManager());
        }
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore nella gestione dei dati della scheda sicurezza",
            "gestione.schedasicurezza", e);
      }
    }
    
    
    // Gestione dei dati della scheda Misure aggiuntive e migliorative per la sicurezza
    if (impl.isColumn("W9MISSIC.CODGARA")) {
      try {
        if (impl.getLong("W9MISSIC.CODGARA") == null) {
          impl.setValue("W9MISSIC.CODGARA", impl.getLong("W9INIZ.CODGARA"));
          impl.setValue("W9MISSIC.CODLOTT", impl.getLong("W9INIZ.CODLOTT"));
          impl.setValue("W9MISSIC.NUM_INIZ", impl.getLong("W9INIZ.NUM_INIZ"));
          
          AbstractGestoreChiaveNumerica gestoreW9MISSIC = new DefaultGestoreEntitaChiaveNumerica("W9MISSIC", "NUM_MISS", new String[] { "CODGARA", "CODLOTT" }, this.getRequest());
  		  this.inserisci(status, impl, gestoreW9MISSIC);
        } else {
          impl.update("W9MISSIC", this.getSqlManager());
        }
        
        GestoreW9DOCFASE gestoreW9DOCFASE = new GestoreW9DOCFASE();
        AbstractGestoreChiaveNumerica gestore = new DefaultGestoreEntitaChiaveNumerica(
            "W9DOCFASE", "NUMDOC", new String[] { "CODGARA", "CODLOTT",
                "FASE_ESECUZIONE", "NUM_FASE" }, this.getRequest());
        gestoreW9DOCFASE.setForm(this.getForm());
        gestoreW9DOCFASE.gestisciAggiornamentiRecordSchedaMultipla(status, impl,
            gestore, "W9DOCFASE", new DataColumn[] {impl.getColumn("W9MISSIC.CODGARA"), 
            impl.getColumn("W9MISSIC.CODLOTT"), impl.getColumn("W9FASI.FASE_ESECUZIONE"), 
            impl.getColumn("W9MISSIC.NUM_MISS") }, null);
        
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore nella gestione dei dati della scheda misure aggiuntive e migliorative per la sicurezza",
            "gestione.schedasicurezza", e);
      }
    }
    
    // gestione schede multiple incarichi professionali
    AbstractGestoreChiaveNumerica gestoreW9INCA = new DefaultGestoreEntitaChiaveNumerica(
        "W9INCA", "NUM_INCA", new String[] { "CODGARA", "CODLOTT", "NUM" }, this.getRequest());
	  gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9INCA, "W9INCA",
	      new DataColumn[] {impl.getColumn("W9INIZ.CODGARA"), impl.getColumn("W9INIZ.CODLOTT"),
				impl.getColumn("W9INIZ.NUM_INIZ")}, null);
  }

  public void preInsert(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

    super.preInsert(status, impl);
    try {
      if (impl.isColumn("W9INIZ.NUM_INIZ")) {
    	  impl.addColumn("W9SIC.CODGARA", JdbcParametro.TIPO_NUMERICO, impl.getLong("W9INIZ.CODGARA"));
  		  impl.addColumn("W9SIC.CODLOTT", JdbcParametro.TIPO_NUMERICO, impl.getLong("W9INIZ.CODLOTT"));
  		  impl.addColumn("W9SIC.NUM_INIZ", JdbcParametro.TIPO_NUMERICO, impl.getLong("W9INIZ.NUM_INIZ"));
  		
  		  if (! this.geneManager.getProfili().checkProtec((String) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO), "PAGE", "VIS", "W9.W9INIZ-scheda.SICUREZZA")) {
  			  impl.addColumn("W9SIC.PIANSCIC", JdbcParametro.TIPO_TESTO, "2");
    		  impl.addColumn("W9SIC.DIROPE", JdbcParametro.TIPO_TESTO, "2");
    		  impl.addColumn("W9SIC.TUTOR", JdbcParametro.TIPO_TESTO, "2");
  	      }
  		  AbstractGestoreChiaveNumerica gestoreW9SIC = new DefaultGestoreEntitaChiaveNumerica("W9SIC", "NUM_SIC", new String[] { "CODGARA", "CODLOTT" }, this.getRequest());
  		  this.inserisci(status, impl, gestoreW9SIC);
      }
    } catch (Exception e) {
        throw new GestoreException(
            "Errore nella gestione dei dati della scheda sicurezza",
            "gestione.schedasicurezza", e);
    }
    
    // Gestione dei dati della pubblicazione 
    if (impl.isModifiedTable("W9PUES")) {
      try {
        this.salvaPubblicazioneEsito(status, impl);
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore nella gestione della sezione 'Pubblicazione esito di gara'",
            "gestione.esitiPubblicazioneGara", e);
      }
    }
  }

  /**
   * @param status
   * @param impl
   * @throws GestoreException
   * @throws SQLException
   */
  private void salvaPubblicazioneEsito(TransactionStatus status,
      DataColumnContainer impl) throws GestoreException, SQLException {
    // gestione dati nella tabella W9PUES
    
    impl.setValue("W9PUES.CODGARA", impl.getLong("W9INIZ.CODGARA"));
    impl.setOriginalValue("W9PUES.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, impl.getLong("W9INIZ.CODGARA")));
    
    impl.setValue("W9PUES.CODLOTT", impl.getLong("W9INIZ.CODLOTT"));
    impl.setOriginalValue("W9PUES.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, impl.getLong("W9INIZ.CODLOTT")));
    
    impl.setValue("W9PUES.NUM_INIZ", impl.getLong("W9INIZ.NUM_INIZ"));
    impl.setOriginalValue("W9PUES.NUM_INIZ", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, impl.getLong("W9INIZ.NUM_INIZ")));
    
    impl.setValue("W9PUES.NUM_PUES", new Long(1));
    impl.setOriginalValue("W9PUES.NUM_PUES", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));

    long occorrenzePUES = this.geneManager.countOccorrenze("W9PUES", " CODGARA=? and CODLOTT=? and NUM_INIZ=? and NUM_PUES=1 ",
        new Object[]{ impl.getLong("W9INIZ.CODGARA"), impl.getLong("W9INIZ.CODLOTT"), impl.getLong("W9INIZ.NUM_INIZ") });
    
    if (occorrenzePUES == 0) {
      this.inserisci(status, impl, new GestoreW9PUES());
    } else {
      this.update(status, impl, new GestoreW9PUES());
    }
  }

}
