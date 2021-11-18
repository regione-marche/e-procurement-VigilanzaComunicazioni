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

public class GestoreW9LOTTCATEMultiplo extends AbstractGestoreEntita {

  public String getEntita() {
    return "W9LOTTCATE";
  }

  private GeneManager geneManager;

  public GeneManager getGeneManager() {
    return geneManager;
  }

  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  public void gestioneW9LOTTCATEdaW9LOTT(HttpServletRequest request,
      TransactionStatus status, DataColumnContainer dataColumnContainer)
      throws GestoreException {

    try {
      if (dataColumnContainer.isColumn("NUMERO_W9LOTTCATE")) {

        DefaultGestoreEntita gestoreW9LOTTCATE = new DefaultGestoreEntita(
            "W9LOTTCATE", request);
        DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
            dataColumnContainer.getColumns("W9LOTTCATE", 0));

        int numeroCategorie = dataColumnContainer.getLong("NUMERO_W9LOTTCATE").intValue();

        for (int i = 1; i < numeroCategorie; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          boolean deleteOccorrenza = newDataColumnContainer.isColumn("DEL_W9LOTTCATE")
              && "1".equals(newDataColumnContainer.getString("DEL_W9LOTTCATE"));

          if (deleteOccorrenza) {
            if (newDataColumnContainer.isColumn("MOD_W9LOTTCATE")) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9LOTTCATE.DEL_W9LOTTCATE", "W9LOTTCATE.MOD_W9LOTTCATE",
                  "W9LOTTCATE.INS_W9LOTTCATE" });
              gestoreW9LOTTCATE.elimina(status, newDataColumnContainer);
            }
          }
        }
        for (int i = 1; i <= numeroCategorie; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          if (newDataColumnContainer.isModifiedTable("W9LOTTCATE")) {
            if (newDataColumnContainer.isColumn("INS_W9LOTTCATE")
                && "1".equals(newDataColumnContainer.getString("INS_W9LOTTCATE"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9LOTTCATE"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9LOTTCATE.INS_W9LOTTCATE", "W9LOTTCATE.DEL_W9LOTTCATE" });

              int num = 0;
              String sqlSelectMaxNum = "select max(NUM_CATE)+1 "
                  + "from W9LOTTCATE where CODGARA = "
                  + newDataColumnContainer.getColumn("W9LOTTCATE.CODGARA").getValue()
                  + " and CODLOTT = "
                  + newDataColumnContainer.getColumn("W9LOTTCATE.CODLOTT").getValue();

              Long numMaxNum = (Long) this.geneManager.getSql().getObject(
                  sqlSelectMaxNum, new Object[] {});
              if (numMaxNum != null) {
                num = Integer.parseInt(numMaxNum + "");
              } else {
                num = 1;
              }
              newDataColumnContainer.getColumn("W9LOTTCATE.NUM_CATE").setValue(
                  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(num)));
              newDataColumnContainer.getColumn("W9LOTTCATE.NUM_CATE").setChiave(
                  true);

              gestoreW9LOTTCATE.inserisci(status, newDataColumnContainer);
            } else if (newDataColumnContainer.isColumn("MOD_W9LOTTCATE")
                && "1".equals(newDataColumnContainer.getString("MOD_W9LOTTCATE"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9LOTTCATE"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9LOTTCATE.MOD_W9LOTTCATE", "W9LOTTCATE.DEL_W9LOTTCATE" });
              gestoreW9LOTTCATE.update(status, newDataColumnContainer);
            }
          }
        }
      }
    } catch (SQLException se) {
      throw new GestoreException(
          "Errore durante la gestione delle altre categorie",
          "gestione.altreCategorie", se);
    }
  }

  public void cancellazioneW9LOTTCATEdaW9LOTT(HttpServletRequest request,
      TransactionStatus status, DataColumnContainer dataColumnContainer)
      throws GestoreException {
    String sqlCanc = "DELETE from W9LOTTCATE where CODGARA = "
        + dataColumnContainer.getColumn("W9LOTT.CODGARA").getValue()
        + " and CODLOTT = "
        + dataColumnContainer.getColumn("W9LOTT.CODLOTT").getValue();

    try {
      this.geneManager.getSql().execute(sqlCanc);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore durante la cancellazione delle altre categorie",
          "elimina.altreCategorie", e);
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
