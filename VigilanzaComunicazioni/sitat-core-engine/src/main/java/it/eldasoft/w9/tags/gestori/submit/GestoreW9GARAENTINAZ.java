package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import java.sql.SQLException;
import java.util.Vector;

import org.springframework.transaction.TransactionStatus;

public class GestoreW9GARAENTINAZ extends AbstractGestoreChiaveNumerica {

  public String[] getAltriCampiChiave() {
    return null;
  }

  public String getCampoNumericoChiave() {
    return "CODGARA";
  }

  /**
   * @return Ritorna l'entita' di cui la classe gestisce i dati.
   */
  public final String getEntita() {
    return "W9GARA_ENTINAZ";
  }

  @Override
  public void postDelete(final DataColumnContainer arg0) throws GestoreException {
  }

  @Override
  public void postInsert(final DataColumnContainer impl) throws GestoreException {
  }

  @Override
  public void postUpdate(final DataColumnContainer impl) throws GestoreException {
  }

  @Override
  public void preDelete(final TransactionStatus status, final DataColumnContainer impl)
      throws GestoreException {
    String sqlSelectCancellazione = "";
    GeneManager gene = this.getGeneManager();

    // si ricava il valore della chiave primaria di W9GARA
    Long codgara = impl.getColumn("W9GARA_ENTINAZ.CODGARA").getValue().longValue();

    // l'array listaEntita contiene le tabelle in cui verranno fatte le
    // eliminazioni degli elementi collegati ad una gara
    String[] listaEntita = new String[] { "W9LOTT_ENTINAZ", "W9DOCGARA" };

    for (int k = 0; k < listaEntita.length; k++) {
      sqlSelectCancellazione = "DELETE FROM "
          + listaEntita[k]
          + " WHERE CODGARA = "
          + codgara;
      try {
        gene.getSql().execute(sqlSelectCancellazione);
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore durante la cancellazione di una Gara",
            "cancellazione.gara", e);
      }
    }
  }
  
  @Override
  public void preInsert(final TransactionStatus status, final DataColumnContainer impl)
      throws GestoreException {

    DataColumn[] arrayDataColumns = impl.getColumnsBySuffix("_1", true);
    // Ricerca dei campi con suffisso '_1' che rappresentano i campi della prima
    // sezione dinamica dell'entita' W9LOTT_ENTINAZ nel tab 'Lotti ed esiti'
    if (arrayDataColumns == null || (arrayDataColumns != null && arrayDataColumns.length == 0)) {
      this.preInsertConOffset(status, impl);
    }
  }

  @Override
  public void preUpdate(final TransactionStatus status, final DataColumnContainer impl)
      throws GestoreException {

    // Gestore per il salvataggio dei documenti che costituiscono il bando di gara
    GestoreW9DOCGARA gestoreW9DOCGARA = new GestoreW9DOCGARA();
    AbstractGestoreChiaveNumerica gestore = new DefaultGestoreEntitaChiaveNumerica(
        "W9DOCGARA", "NUMDOC", new String[] { "CODGARA" }, this.getRequest());
    gestoreW9DOCGARA.setForm(this.getForm());

    gestoreW9DOCGARA.gestisciAggiornamentiRecordSchedaMultipla(status, impl,
        gestore, "W9DOCGARA",
        new DataColumn[] { impl.getColumn("W9GARA_ENTINAZ.CODGARA") }, null);

    GestoreW9LOTTENTINAZ gestoreW9LOTTENTINAZ = new GestoreW9LOTTENTINAZ();
    
    AbstractGestoreChiaveNumerica gestoreE = new DefaultGestoreEntitaChiaveNumerica(
        "W9LOTT_ENTINAZ", "CODLOTT", new String[] { "CODGARA" }, this.getRequest());
    gestoreW9LOTTENTINAZ.setForm(this.getForm());
    
    //if (impl.isColumn("W9LOTT_ENTINAZ.CODGARA_1")) {
      gestoreW9LOTTENTINAZ.gestisciAggiornamentiRecordSchedaMultipla(status, impl,
          gestoreE, "W9LOTT_ENTINAZ",
          new DataColumn[] { impl.getColumn("W9GARA_ENTINAZ.CODGARA") }, null);
      
      Long codiceGara = impl.getColumn("W9GARA_ENTINAZ.CODGARA").getValue().longValue();
      
      // A questo punto bisogna aggiornare il campo W9GARA_ENTINAZ.NLOTTI, che
      // contiene il numero totale di lotti, con il numero effettivo di lotti creati.
      try {
        this.sqlManager.update("update W9GARA_ENTINAZ wg set wg.NLOTTI = ("
            + " select count(*) from W9LOTT_ENTINAZ wl where wl.CODGARA = wg.CODGARA ) "
            +	" where wg.CODGARA = ? ", new Object[]{ codiceGara }, 1);
      } catch (SQLException e) {
        throw new GestoreException("Errore nell'aggiornamento del campo W9GARA_ENTINAZ.NLOTTI ", null, e);
      }
    //}
  }

  /**
   * Prima dell'inserimento calcolo la chiave con offset (cioe' il campo 
   * numerico deve essere maggiore di un prefissato valore).
   * 
   * @param status TransactionStatus
   * @param impl DataColumnContainer
   * @throws GestoreException GestoreException
   */
  private void preInsertConOffset(final TransactionStatus status, final DataColumnContainer impl)
      throws GestoreException {
    StringBuffer where = new StringBuffer("");
    StringBuffer select = new StringBuffer("");
    int numeroAltriCampiChiave = 0;
    if (this.getAltriCampiChiave() != null) {
      numeroAltriCampiChiave = this.getAltriCampiChiave().length;
    }
    Object[] param = new Object[numeroAltriCampiChiave];
    // Creo la where
    for (int i = 0; i < numeroAltriCampiChiave; i++) {
      if (i > 0) {
        where.append(" and ");
      }
      where.append(this.getAltriCampiChiave()[i]);
      where.append(" = ?");
      param[i] = impl.getObject(this.getEntita()
          + "."
          + this.getAltriCampiChiave()[i]);
    }
    select.append("select max(");
    select.append(this.getCampoNumericoChiave());
    select.append(") from ");
    select.append(this.getEntita());
    if (where.length() > 0) {
      select.append(" where ");
      select.append(where);
    }
    
    try {
      Vector< ? > ret = this.getSqlManager().getVector(select.toString(), param);
      Long max = SqlManager.getValueFromVectorParam(ret, 0).longValue();
      if (max == null) {
        max = new Long(3000000);
      }
      // Se non esiste la colonna la inserisco
      if (!impl.isColumn(this.getEntita() + "." + this.getCampoNumericoChiave())) {
        impl.addColumn(this.getEntita() + "." + this.getCampoNumericoChiave(),
            JdbcParametro.TIPO_NUMERICO);
      }
      impl.setValue(this.getEntita() + "." + this.getCampoNumericoChiave(),
          new Long(max.longValue() + 1));
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nel calcolo del massimo valore per il campo "
              + this.getEntita()
              + "."
              + this.getCampoNumericoChiave(), "calcoloNumerico", e);
    }
  }
  
}