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


public class GestoreW9FINAMultiplo extends AbstractGestoreEntita {

  public String getEntita() {
    return "W9FINA";
  }

  private GeneManager geneManager;

  public GeneManager getGeneManager() {
    return geneManager;
  }

  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  public void gestioneW9FINAdaW9APPA(HttpServletRequest request,
      TransactionStatus status, DataColumnContainer dataColumnContainer)
      throws GestoreException {

    try {
      if (dataColumnContainer.isColumn("NUMERO_W9FINA")) {

        DefaultGestoreEntita gestoreW9FINA = new DefaultGestoreEntita(
            "W9FINA", request);
        DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
            dataColumnContainer.getColumns("W9FINA", 0));

        int numeroFinanziamenti = dataColumnContainer.getLong(
            "NUMERO_W9FINA").intValue();

        for (int i = 1; i < numeroFinanziamenti; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          boolean deleteOccorrenza = newDataColumnContainer.isColumn("DEL_W9FINA")
              && "1".equals(newDataColumnContainer.getString("DEL_W9FINA"));

          if (deleteOccorrenza) {
            if (newDataColumnContainer.isColumn("MOD_W9FINA")) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9FINA.DEL_W9FINA", "W9FINA.MOD_W9FINA",
                  "W9FINA.INS_W9FINA" });
              gestoreW9FINA.elimina(status, newDataColumnContainer);
            }
          }
        }
        for (int i = 1; i <= numeroFinanziamenti; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          if (newDataColumnContainer.isModifiedTable("W9FINA")) {
            if (newDataColumnContainer.isColumn("INS_W9FINA")
                && "1".equals(newDataColumnContainer.getString("INS_W9FINA"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9FINA"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9FINA.INS_W9FINA", "W9FINA.DEL_W9FINA" });

              int num = 0;
              String sqlSelectMaxNum = "select max(NUM_FINA)+1 "
                  + "from W9FINA where CODGARA = "
                  + newDataColumnContainer.getColumn("W9FINA.CODGARA").getValue()
                  + " and CODLOTT = "
                  + newDataColumnContainer.getColumn("W9FINA.CODLOTT").getValue()
                  + " and NUM_APPA = "
                  + newDataColumnContainer.getColumn("W9FINA.NUM_APPA").getValue();

              Long numMaxNum = (Long) this.geneManager.getSql().getObject(
                  sqlSelectMaxNum, new Object[] {});
              if (numMaxNum != null) {
                num = Integer.parseInt(numMaxNum + "");
              } else {
                num = 1;
              }
              newDataColumnContainer.getColumn("W9FINA.NUM_FINA").setValue(
                  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(num)));
              newDataColumnContainer.getColumn("W9FINA.NUM_FINA").setChiave(true);

              gestoreW9FINA.inserisci(status, newDataColumnContainer);
            } else if (newDataColumnContainer.isColumn("MOD_W9FINA")
                && "1".equals(newDataColumnContainer.getString("MOD_W9FINA"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9FINA"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9FINA.MOD_W9FINA", "W9FINA.DEL_W9FINA" });
              gestoreW9FINA.update(status, newDataColumnContainer);
            }
          }
        }
      }
    } catch (SQLException se) {
      throw new GestoreException(
          "Errore durante la gestione dei finanziamenti",
          "gestione.finanziamenti", se);
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
