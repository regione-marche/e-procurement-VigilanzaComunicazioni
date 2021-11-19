package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreW9AGGIMultiplo extends AbstractGestoreEntita {

  public String getEntita() {
    return "W9AGGI";
  }

  private GeneManager geneManager;

  public GeneManager getGeneManager() {
    return geneManager;
  }

  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  public void gestioneW9AGGIdaW9APPA(HttpServletRequest request,
      TransactionStatus status, DataColumnContainer dataColumnContainer)
      throws GestoreException {

    try {
      if (dataColumnContainer.isColumn("NUMERO_W9AGGI")) {

        DefaultGestoreEntita gestoreW9AGGI = new DefaultGestoreEntita("W9AGGI",
            request);
        DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
            dataColumnContainer.getColumns("W9AGGI", 0));

        int numeroImpreseAggiudicatarie = dataColumnContainer.getLong(
            "NUMERO_W9AGGI").intValue();

        for (int i = 1; i < numeroImpreseAggiudicatarie; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          boolean deleteOccorrenza = newDataColumnContainer.isColumn("DEL_W9AGGI")
              && "1".equals(newDataColumnContainer.getString("DEL_W9AGGI"));

          if (deleteOccorrenza) {
            if (newDataColumnContainer.isColumn("MOD_W9AGGI")) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9AGGI.DEL_W9AGGI", "W9AGGI.MOD_W9AGGI",
                  "W9AGGI.INS_W9AGGI", "IMPR.NOMEST_AUSILIARIA",
                  "IMPR.NOMEST" });
              gestoreW9AGGI.elimina(status, newDataColumnContainer);
            }
          }
        }
        for (int i = 1; i <= numeroImpreseAggiudicatarie; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          if (newDataColumnContainer.isModifiedTable("W9AGGI")) {
            if (newDataColumnContainer.isColumn("INS_W9AGGI")
                && "1".equals(newDataColumnContainer.getString("INS_W9AGGI"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9AGGI"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9AGGI.INS_W9AGGI", "W9AGGI.DEL_W9AGGI",
                  "IMPR.NOMEST_AUSILIARIA", "IMPR.NOMEST" });

              int num = 0;
              String sqlSelectMaxNumaggi = "select max(NUM_AGGI)+1 "
                  + "from W9AGGI where CODGARA = "
                  + newDataColumnContainer.getColumn("W9AGGI.CODGARA").getValue()
                  + " and CODLOTT = "
                  + newDataColumnContainer.getColumn("W9AGGI.CODLOTT").getValue()
                  + " and NUM_APPA = "
                  + newDataColumnContainer.getColumn("W9AGGI.NUM_APPA").getValue();

              Long numMaxNumaggi = (Long) this.geneManager.getSql().getObject(
                  sqlSelectMaxNumaggi, new Object[] {});
              if (numMaxNumaggi != null) {
                num = Integer.parseInt(numMaxNumaggi + "");
              } else {
                num = 1;
              }
              newDataColumnContainer.getColumn("W9AGGI.NUM_AGGI").setValue(
                  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(num)));
              newDataColumnContainer.getColumn("W9AGGI.NUM_AGGI").setChiave(
                  true);

              gestoreW9AGGI.inserisci(status, newDataColumnContainer);
            } else if (newDataColumnContainer.isColumn("MOD_W9AGGI")
                && "1".equals(newDataColumnContainer.getString("MOD_W9AGGI"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9AGGI"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9AGGI.MOD_W9AGGI", "W9AGGI.DEL_W9AGGI",
                  "IMPR.NOMEST_AUSILIARIA", "IMPR.NOMEST" });
              gestoreW9AGGI.update(status, newDataColumnContainer);
            }
          }
        }
      }
    } catch (SQLException se) {
      throw new GestoreException(
          "Errore durante la gestione delle imprese aggiudicatarie",
          "gestione.impreseAggiudicatarie", se);
    }
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

  public void preInsert(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

  public void preUpdate(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

}
