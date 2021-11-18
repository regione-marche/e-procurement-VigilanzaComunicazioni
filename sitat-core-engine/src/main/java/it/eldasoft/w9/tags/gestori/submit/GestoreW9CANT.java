package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreW9CANT extends AbstractGestoreChiaveNumericaEntitaW9 {

  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA", "CODLOTT" };
  }

  public String getCampoNumericoChiave() {
    return "NUM_CANT";
  }

  public String getEntita() {
    return "W9CANT";
  }

  public void postDelete(DataColumnContainer arg0) throws GestoreException {

  }

  public void postInsert(DataColumnContainer arg0) throws GestoreException {

  }

  public void postUpdate(DataColumnContainer arg0) throws GestoreException {

  }

  public void preDelete(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
    
    String sqlSelectCancellazione = "";
    GeneManager geneManager = this.getGeneManager();

    // si ricavano i valori della chiave primaria di W9LOTT
    Long codgara = impl.getLong("W9CANT.CODGARA");
    Long codlott = impl.getLong("W9CANT.CODLOTT");
    Long numCant = impl.getLong("W9CANT.NUM_CANT");
    
    // l'array listaEntita contiene le tabelle in cui verranno fatte le le
    // eliminazioni degli elementi collegati
    String[] listaEntita = new String[] { "W9CANTDEST", "W9CANTIMP", "W9INCA" };

    for (int k = 0; k < listaEntita.length; k++) {
      sqlSelectCancellazione = "DELETE FROM "
          + listaEntita[k]
          + " WHERE CODGARA = "
          + codgara
          + " AND CODLOTT = "
          + codlott
          + " AND NUM_CANT = "
          + numCant;
      
      if (k == 2) {
        sqlSelectCancellazione += " AND SEZIONE='NP' ";
      }

      try {
        geneManager.getSql().execute(sqlSelectCancellazione);
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore durante la cancellazione di una scheda cantiere/notifiche preliminari", null, e);
      }
    }

  }

  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {

    // Inserimento in W9CANTDEST
    int i = 1;
    DataColumnContainer implDestinatari = null;
    DataColumnContainer tempImplDestinatari = null;

    try {
      if (impl.isColumn("W9CANTDEST.DEST_" + i)) {
          sqlManager.update("delete from W9CANTDEST where CODGARA=? and CODLOTT=? and NUM_CANT=?", 
                  new Object[] {impl.getLong("W9CANT.CODGARA"), impl.getLong("W9CANT.CODLOTT"), impl.getLong("W9CANT.NUM_CANT")} );
      }
      while (impl.isColumn("W9CANTDEST.DEST_" + i)) {
        DataColumn[] campiImpl = impl.getColumnsBySuffix("DEST_" + i, true);
        tempImplDestinatari = new DataColumnContainer(campiImpl);
        implDestinatari = new DataColumnContainer(tempImplDestinatari.getColumnsBySuffix("_" + i, false));
        
        Long destinazione = implDestinatari.getLong("W9CANTDEST.DEST");
        if (destinazione != null && destinazione.longValue() == 1) {
          implDestinatari.setValue("W9CANTDEST.DEST", implDestinatari.getLong("W9CANTDEST.VALORE_DEST"));
          implDestinatari.setOriginalValue("W9CANTDEST.DEST", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(0)));
          implDestinatari.removeColumns(new String[] { "W9CANTDEST.VALORE_DEST" });
          implDestinatari.addColumn("W9CANTDEST.CODGARA", impl.getLong("W9CANT.CODGARA"));
          //implDestinatari.getColumn("W9CANTDEST.CODGARA").setObjectOriginalValue(impl.getLong("W9CANT.CODGARA"));
          implDestinatari.addColumn("W9CANTDEST.CODLOTT", impl.getLong("W9CANT.CODLOTT"));
          //implDestinatari.getColumn("W9CANTDEST.CODLOTT").setObjectOriginalValue(impl.getLong("W9CANT.CODLOTT"));
          implDestinatari.addColumn("W9CANTDEST.NUM_CANT", impl.getLong("W9CANT.NUM_CANT"));
          //implDestinatari.getColumn("W9CANTDEST.NUM_CANT").setObjectOriginalValue(impl.getLong("W9CANT.NUM_CANT"));
          implDestinatari.getColumn("W9CANTDEST.CODGARA").setChiave(true);
          implDestinatari.getColumn("W9CANTDEST.CODLOTT").setChiave(true);
          implDestinatari.getColumn("W9CANTDEST.NUM_CANT").setChiave(true);
          implDestinatari.getColumn("W9CANTDEST.DEST").setChiave(true);
          
          implDestinatari.insert("W9CANTDEST", sqlManager);

        }
        // Rimozione delle colonne perche' danno problemi al salvataggio delle imprese 
        impl.removeColumns(new String[] {"W9CANTDEST.DEST_"+i, "W9CANTDEST.VALORE_DEST_"+i });
        i++;
      }
    } catch (SQLException e) {
      throw new GestoreException("Errore nel salvataggio dei destinatari (W9CANTDEST)", null, e);
    }
    
    // gestione schede multiple imprese
    AbstractGestoreChiaveNumerica gestoreW9CANTIMP = new DefaultGestoreEntitaChiaveNumerica(
        "W9CANTIMP", "NUM_IMP", new String[]{"CODGARA", "CODLOTT", "NUM_CANT"}, this.getRequest());
    gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9CANTIMP, "W9CANTIMP",
        new DataColumn[] { impl.getColumn("W9CANT.CODGARA"), impl.getColumn("W9CANT.CODLOTT"),
        impl.getColumn("W9CANT.NUM_CANT") }, null);

    // gestione schede multiple incarichi professionali
    AbstractGestoreChiaveNumerica gestoreW9INCA = new DefaultGestoreEntitaChiaveNumerica(
        "W9INCA", "NUM_INCA", new String[]{"CODGARA", "CODLOTT", "NUM"}, this.getRequest());

    gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9INCA, "W9INCA",
        new DataColumn[]{impl.getColumn("W9CANT.CODGARA"), impl.getColumn("W9CANT.CODLOTT"),
        impl.getColumn("W9CANT.NUM_CANT")}, null);
  }

  public void preInsert(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
    
    super.preInsert(status, impl);
    
    Long codGara = impl.getLong("W9CANT.CODGARA");
    Long codLott = impl.getLong("W9CANT.CODLOTT");
    
    long numeroSchedeCantiere = this.geneManager.countOccorrenze("W9CANT", " CODGARA=? ",
        new Object[]{codGara});
    
    numeroSchedeCantiere++;
    
    // inserimento in W9CANTDEST
    int i = 1;
    DataColumnContainer implDestinatari = null;
    DataColumnContainer tempImplDestinatari = null;
    while (impl.isColumn("W9CANTDEST.DEST_" + i)) {
      DataColumn[] campiImpl = impl.getColumnsBySuffix("DEST_" + i, true);
      tempImplDestinatari = new DataColumnContainer(campiImpl);
      implDestinatari = new DataColumnContainer(tempImplDestinatari.getColumnsBySuffix("_" + i, false));

      Long idCondizione = implDestinatari.getLong("W9CANTDEST.DEST");
      if (idCondizione != null && idCondizione.longValue() == 1) {
        implDestinatari.setValue("W9CANTDEST.DEST", implDestinatari.getLong("W9CANTDEST.VALORE_DEST"));
        implDestinatari.setOriginalValue("W9CANTDEST.DEST", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(0)));
        implDestinatari.removeColumns(new String[] { "W9CANTDEST.VALORE_DEST" });
        implDestinatari.addColumn("W9CANTDEST.CODGARA", codGara);
        implDestinatari.addColumn("W9CANTDEST.CODLOTT", codLott);
        implDestinatari.addColumn("W9CANTDEST.NUM_CANT", new Long(numeroSchedeCantiere));
        implDestinatari.getColumn("W9CANTDEST.CODGARA").setChiave(true);
        implDestinatari.getColumn("W9CANTDEST.CODLOTT").setChiave(true);
        implDestinatari.getColumn("W9CANTDEST.NUM_CANT").setChiave(true);
        implDestinatari.getColumn("W9CANTDEST.DEST").setChiave(true);
        
        try {
          implDestinatari.insert("W9CANTDEST", sqlManager);
        } catch (SQLException e) {
          throw new GestoreException("Errore nel salvataggio dei destinatari (W9CANTDEST)", null, e);
        }
      }
      // Rimozione delle colonne perche' danno problemi al salvataggio delle imprese
      impl.removeColumns(new String[] {"W9CANTDEST.DEST_"+i, "W9CANTDEST.VALORE_DEST_"+i });
      i++;
    }

    // gestione schede multiple imprese
    AbstractGestoreChiaveNumerica gestoreW9CANTIMP = new DefaultGestoreEntitaChiaveNumerica(
        "W9CANTIMP", "NUM_IMP", new String[]{"CODGARA", "CODLOTT", "NUM_CANT"}, this.getRequest());
    gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9CANTIMP, "W9CANTIMP",
        new DataColumn[] { impl.getColumn("W9CANT.CODGARA"), impl.getColumn("W9CANT.CODLOTT"),
        impl.getColumn("W9CANT.NUM_CANT") }, null);
    

  }
}
