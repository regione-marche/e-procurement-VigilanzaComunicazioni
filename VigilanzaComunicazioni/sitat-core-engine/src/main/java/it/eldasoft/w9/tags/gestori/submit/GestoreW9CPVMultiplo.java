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


public class GestoreW9CPVMultiplo extends AbstractGestoreEntita {

  public String getEntita() {
    return "W9CPV";
  }

  private GeneManager geneManager;

  public GeneManager getGeneManager() {
    return geneManager;
  }

  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  public void gestioneW9CPVdaW9LOTT(HttpServletRequest request,
      TransactionStatus status, DataColumnContainer dataColumnContainer)
      throws GestoreException {

    try {
      if (dataColumnContainer.isColumn("NUMERO_W9CPV")) {

        DefaultGestoreEntita gestoreW9CPV = new DefaultGestoreEntita(
            "W9CPV", request);
        DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
            dataColumnContainer.getColumns("W9CPV", 0));

        int numeroCpvPresenti = dataColumnContainer.getLong(
            "NUMERO_W9CPV").intValue();

        for (int i = 1; i < numeroCpvPresenti; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          boolean deleteOccorrenza = newDataColumnContainer.isColumn("DEL_W9CPV")
              && "1".equals(newDataColumnContainer.getString("DEL_W9CPV"));

          if (deleteOccorrenza) {
            if (newDataColumnContainer.isColumn("MOD_W9CPV")) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9CPV.DEL_W9CPV", "W9CPV.MOD_W9CPV",
                  "W9CPV.INS_W9CPV" });
              gestoreW9CPV.elimina(status, newDataColumnContainer);
            }
          }
        }
        for (int i = 1; i <= numeroCpvPresenti; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          if (newDataColumnContainer.isModifiedTable("W9CPV")) {
            if (newDataColumnContainer.isColumn("INS_W9CPV")
                && "1".equals(newDataColumnContainer.getString("INS_W9CPV"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9CPV"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9CPV.INS_W9CPV", "W9CPV.DEL_W9CPV" });

              int num = 0;
              String sqlSelectCpvSecondari = "select max(NUM_CPV)+1 "
                  + "from W9CPV where CODGARA = "
                  + newDataColumnContainer.getColumn("W9CPV.CODGARA").getValue()
                  + " and CODLOTT = "
                  + newDataColumnContainer.getColumn("W9CPV.CODLOTT").getValue();

              Long numCpvSecondari = (Long) this.geneManager.getSql().getObject(
                  sqlSelectCpvSecondari, new Object[] {});
              if (numCpvSecondari != null) {
                num = Integer.parseInt(numCpvSecondari + "");
              } else {
                num = 1;
              }
              newDataColumnContainer.getColumn("W9CPV.NUM_CPV").setValue(
                  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(num)));
              newDataColumnContainer.getColumn("W9CPV.NUM_CPV").setChiave(true);

              gestoreW9CPV.inserisci(status, newDataColumnContainer);
            } else if (newDataColumnContainer.isColumn("MOD_W9CPV")
                && "1".equals(newDataColumnContainer.getString("MOD_W9CPV"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9CPV"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9CPV.MOD_W9CPV", "W9CPV.DEL_W9CPV" });
              gestoreW9CPV.update(status, newDataColumnContainer);
            }
          }
        }
      }
    } catch (SQLException se) {
      throw new GestoreException(
          "Errore durante la gestione dei cpv secondari",
          "gestione.cpvSecondari", se);
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
