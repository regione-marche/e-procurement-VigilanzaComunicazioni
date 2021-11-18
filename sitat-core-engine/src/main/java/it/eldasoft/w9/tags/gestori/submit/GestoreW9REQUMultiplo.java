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


public class GestoreW9REQUMultiplo extends AbstractGestoreEntita {

  public String getEntita() {
    return "W9REQU";
  }

  private GeneManager geneManager;

  public GeneManager getGeneManager() {
    return geneManager;
  }

  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  public void gestioneW9REQUdaW9APPA(HttpServletRequest request,
      TransactionStatus status, DataColumnContainer dataColumnContainer)
      throws GestoreException {

    try {
      if (dataColumnContainer.isColumn("NUMERO_W9REQU")) {

        DefaultGestoreEntita gestoreW9REQU = new DefaultGestoreEntita(
            "W9REQU", request);
        DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
            dataColumnContainer.getColumns("W9REQU", 0));

        int numeroCatQualificazioneSoa = dataColumnContainer.getLong(
            "NUMERO_W9REQU").intValue();

        for (int i = 1; i < numeroCatQualificazioneSoa; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          boolean deleteOccorrenza = newDataColumnContainer.isColumn("DEL_W9REQU")
              && "1".equals(newDataColumnContainer.getString("DEL_W9REQU"));

          if (deleteOccorrenza) {
            if (newDataColumnContainer.isColumn("MOD_W9REQU")) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9REQU.DEL_W9REQU", "W9REQU.MOD_W9REQU",
                  "W9REQU.INS_W9REQU" });
              gestoreW9REQU.elimina(status, newDataColumnContainer);
            }
          }
        }
        for (int i = 1; i <= numeroCatQualificazioneSoa; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          if (newDataColumnContainer.isModifiedTable("W9REQU")) {
            if (newDataColumnContainer.isColumn("INS_W9REQU")
                && "1".equals(newDataColumnContainer.getString("INS_W9REQU"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9REQU"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9REQU.INS_W9REQU", "W9REQU.DEL_W9REQU" });

              int num = 0;
              String sqlSelectMaxNum = "select max(NUM_REQU)+1 "
                  + "from W9REQU where CODGARA = "
                  + newDataColumnContainer.getColumn("W9REQU.CODGARA").getValue()
                  + " and CODLOTT = "
                  + newDataColumnContainer.getColumn("W9REQU.CODLOTT").getValue()
                  + " and NUM_APPA = "
                  + newDataColumnContainer.getColumn("W9REQU.NUM_APPA").getValue();

              Long numMaxNum = (Long) this.geneManager.getSql().getObject(
                  sqlSelectMaxNum, new Object[] {});
              if (numMaxNum != null) {
                num = Integer.parseInt(numMaxNum + "");
              } else {
                num = 1;
              }
              newDataColumnContainer.getColumn("W9REQU.NUM_REQU").setValue(
                  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(num)));
              newDataColumnContainer.getColumn("W9REQU.NUM_REQU").setChiave(true);

              gestoreW9REQU.inserisci(status, newDataColumnContainer);
            } else if (newDataColumnContainer.isColumn("MOD_W9REQU")
                && "1".equals(newDataColumnContainer.getString("MOD_W9REQU"))
                && "0".equals(newDataColumnContainer.getString("DEL_W9REQU"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "W9REQU.MOD_W9REQU", "W9REQU.DEL_W9REQU" });
              gestoreW9REQU.update(status, newDataColumnContainer);
            }
          }
        }
      }
    } catch (SQLException se) {
      throw new GestoreException(
          "Errore durante la gestione delle categorie di qualificazione SOA",
          "gestione.catQualificazioneSoa", se);
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
