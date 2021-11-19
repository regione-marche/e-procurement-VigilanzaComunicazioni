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

public class GestoreW9INCAMultiplo extends AbstractGestoreEntita {

  public String getEntita() {
    return "W9INCA";
  }

  private GeneManager geneManager;

  public GeneManager getGeneManager() {
    return geneManager;
  }

  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  public void gestioneW9INCAdaW9(HttpServletRequest request,
      TransactionStatus status, DataColumnContainer dataColumnContainer)
      throws GestoreException {

    try {
      if (dataColumnContainer.isColumn("NUMERO_W9INCA")) {

        DefaultGestoreEntita gestoreW9INCA = new DefaultGestoreEntita("W9INCA",
            request);
        DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
            dataColumnContainer.getColumns("W9INCA", 0));

        int numeroIncarichiProfessionali = dataColumnContainer.getLong(
            "NUMERO_W9INCA").intValue();

        for (int i = 1; i < numeroIncarichiProfessionali; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          boolean deleteOccorrenza = newDataColumnContainer.isColumn("DEL_W9INCA")
              && "1".equals(newDataColumnContainer.getString("DEL_W9INCA"));

          if (deleteOccorrenza) {
            if (newDataColumnContainer.isColumn("MOD_W9INCA")) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9INCA.DEL_W9INCA", "W9INCA.MOD_W9INCA",
                  "W9INCA.INS_W9INCA", "TECNI.NOMTEC" });
              gestoreW9INCA.elimina(status, newDataColumnContainer);
            }
          }
        }
        for (int i = 1; i <= numeroIncarichiProfessionali; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          if (newDataColumnContainer.isModifiedTable("W9INCA")) {
            if (newDataColumnContainer.isColumn("INS_W9INCA")
                && "1".equals(newDataColumnContainer.getString("INS_W9INCA"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9INCA"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9INCA.INS_W9INCA", "W9INCA.DEL_W9INCA", "TECNI.NOMTEC" });

              int num_inca = 0;
              String sqlSelectMaxNumInca = "";
              if (newDataColumnContainer.getColumn("W9INCA.SEZIONE").getValue().toString().equals(
                  "PA")
                  || newDataColumnContainer.getColumn("W9INCA.SEZIONE").getValue().toString().equals(
                      "RA")) {
                sqlSelectMaxNumInca = "select max(NUM_INCA)+1 "
                    + "from W9INCA where CODGARA = "
                    + newDataColumnContainer.getColumn("W9INCA.CODGARA").getValue()
                    + " and CODLOTT = "
                    + newDataColumnContainer.getColumn("W9INCA.CODLOTT").getValue()
                    + " and NUM = "
                    + newDataColumnContainer.getColumn("W9INCA.NUM").getValue()
                    + " and SEZIONE = 'PA' OR SEZIONE = 'RA'";
              } else {
                sqlSelectMaxNumInca = "select max(NUM_INCA)+1 "
                    + "from W9INCA where CODGARA = "
                    + newDataColumnContainer.getColumn("W9INCA.CODGARA").getValue()
                    + " and CODLOTT = "
                    + newDataColumnContainer.getColumn("W9INCA.CODLOTT").getValue()
                    + " and NUM = "
                    + newDataColumnContainer.getColumn("W9INCA.NUM").getValue()
                    + " and SEZIONE = '"
                    + newDataColumnContainer.getColumn("W9INCA.SEZIONE").getValue()
                    + "'";
              }
              Long numMaxNum = (Long) this.geneManager.getSql().getObject(
                  sqlSelectMaxNumInca, new Object[] {});
              if (numMaxNum != null) {
                num_inca = Integer.parseInt(numMaxNum + "");
              } else {
                num_inca = 1;
              }
              newDataColumnContainer.getColumn("W9INCA.NUM_INCA").setValue(
                  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(
                      num_inca)));
              newDataColumnContainer.getColumn("W9INCA.NUM_INCA").setChiave(
                  true);

              gestoreW9INCA.inserisci(status, newDataColumnContainer);
            } else if (newDataColumnContainer.isColumn("MOD_W9INCA")
                && "1".equals(newDataColumnContainer.getString("MOD_W9INCA"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9INCA"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9INCA.MOD_W9INCA", "W9INCA.DEL_W9INCA", "TECNI.NOMTEC" });
              gestoreW9INCA.update(status, newDataColumnContainer);
            }
          }
        }
      }
    } catch (SQLException se) {
      throw new GestoreException(
          "Errore durante la gestione degli incarichi professionali",
          "gestione.incarichiProfessionali", se);
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
