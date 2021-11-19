package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

public class GestoreIMMTRAIMultiplo extends AbstractGestoreEntita {

  @Override
  public String getEntita() {
    return "IMMTRAI";
  }

  private GeneManager geneManager;

  @Override
  public GeneManager getGeneManager() {
    return geneManager;
  }

  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  public void gestioneIMMTRAIdaINTTRI(HttpServletRequest request, TransactionStatus status,
      DataColumnContainer dataColumnContainer, ServletContext servletContext)
      throws GestoreException {

    try {
      if (dataColumnContainer.isColumn("NUMERO_IMMTRAI")) {

        Long contri = (Long) request.getAttribute("contri");
        //Long progCUI = (Long) request.getAttribute("progCUI");

        PtManager ptManager = (PtManager) UtilitySpring.getBean("ptManager",
            servletContext, PtManager.class);
        
        ////////////////////////////////////
        //Nuova gestione Calcolo CUIIM (febbraio 2019)
        String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
        String maxCUIIM = ptManager.calcolaCUIImmobile(contri, codein);
        ////////////////////////////////////

        DefaultGestoreEntita gestoreIMMTRAI = new DefaultGestoreEntita(
            "IMMTRAI", request);
        DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
            dataColumnContainer.getColumns("IMMTRAI", 0));

        int numeroImmobiliPresenti = dataColumnContainer.getLong(
            "NUMERO_IMMTRAI").intValue();

        for (int i = 1; i <= numeroImmobiliPresenti; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          boolean deleteOccorrenza = newDataColumnContainer.isColumn("DEL_IMMTRAI")
              && "1".equals(newDataColumnContainer.getString("DEL_IMMTRAI"));

          if (deleteOccorrenza) {
            if (newDataColumnContainer.isColumn("MOD_IMMTRAI")) {
              newDataColumnContainer.removeColumns(new String[] {
                  "IMMTRAI.DEL_IMMTRAI", "IMMTRAI.MOD_IMMTRAI",
                  "IMMTRAI.INS_IMMTRAI" });
              gestoreIMMTRAI.elimina(status, newDataColumnContainer);
            }
          }
        }
        for (int i = 1; i <= numeroImmobiliPresenti; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

          if (newDataColumnContainer.isModifiedTable("IMMTRAI")) {
            if (newDataColumnContainer.isColumn("INS_IMMTRAI")
                && "1".equals(newDataColumnContainer.getString("INS_IMMTRAI"))
                && "0".equals(newDataColumnContainer.getString("DEL_IMMTRAI"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "IMMTRAI.INS_IMMTRAI", "IMMTRAI.DEL_IMMTRAI" });

              int num = 0;
              String sqlSelectImmobili = "select max(NUMIMM)+1 "
                  + "from IMMTRAI where CONTRI = "
                  + newDataColumnContainer.getColumn("IMMTRAI.CONTRI").getValue()
                  + " and CONINT = "
                  + newDataColumnContainer.getColumn("IMMTRAI.CONINT").getValue();

              Long numImmobili = (Long) this.geneManager.getSql().getObject(
                  sqlSelectImmobili, new Object[] {});
              if (numImmobili != null) {
                num = Integer.parseInt(numImmobili + "");
              } else {
                num = 1;
              }
              newDataColumnContainer.getColumn("IMMTRAI.NUMIMM").setValue(
                  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(num)));
              newDataColumnContainer.getColumn("IMMTRAI.NUMIMM").setChiave(true);

              //calcolo del CUI dell'immobile

              if (newDataColumnContainer.isColumn("IMMTRAI.CUIIMM")) {
                String cuiimm = null;
                ////////////////////////////////////
                //Nuova gestione Calcolo CUIIM (febbraio 2019)
                //...
                ////////////////////////////////////
                if (newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getValue() != null) {
                  //Verifico  il FORMATO
                  cuiimm = newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getStringValue();
                  Long conint = (Long) newDataColumnContainer.getColumn("IMMTRAI.CONINT").getValue().getValue();
                  if (!ptManager.verificaFormatoCuiImmobile(contri,conint,cuiimm)) {
                    throw new GestoreException(
                        "Il CUI dell'immobile non rispetta il formato previsto",
                        "formatocui.immobili");
                  }
                } else {
                  ////////////////////////////////////
                  //Nuova gestione Calcolo CUIIM (febbraio 2019)
                    maxCUIIM = ptManager.incrementaCUIImmobile(maxCUIIM);
                    
                    newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").setValue(
                        new JdbcParametro(JdbcParametro.TIPO_TESTO, maxCUIIM));
                    ////////////////////////////////////
                }
              }
              gestoreIMMTRAI.inserisci(status, newDataColumnContainer);
            } else if (newDataColumnContainer.isColumn("MOD_IMMTRAI")
                && "1".equals(newDataColumnContainer.getString("MOD_IMMTRAI"))
                && "0".equals(newDataColumnContainer.getString("DEL_IMMTRAI"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "IMMTRAI.MOD_IMMTRAI", "IMMTRAI.DEL_IMMTRAI" });

              ////////////////////////////////////
              //Nuova gestione Calcolo CUIIM (febbraio 2019)
              //...
              ////////////////////////////////////
              if (newDataColumnContainer.isColumn("IMMTRAI.CUIIMM") && newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getValue() != null) {
                //Verifico  il FORMATO
                String cuiimm = newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getStringValue();
                Long conint = (Long) newDataColumnContainer.getColumn("IMMTRAI.CONINT").getValue().getValue();
                if (!ptManager.verificaFormatoCuiImmobile(contri,conint,cuiimm)) {
                  throw new GestoreException(
                      "Il CUI dell'immobile non rispetta il formato previsto",
                      "formatocui.immobili");
                }
              }
              gestoreIMMTRAI.update(status, newDataColumnContainer);
            }
          }
        }
      }
    } catch (SQLException se) {
      throw new GestoreException(
          "Errore durante la gestione degli Immobili da trasferire",
          "gestione.immobili", se);
    }
  }

  public void gestioneIMMTRAIdaOITRI(HttpServletRequest request, TransactionStatus status,
	      DataColumnContainer dataColumnContainer, ServletContext servletContext)
	      throws GestoreException {

	    try {
	      if (dataColumnContainer.isColumn("NUMERO_IMMTRAI")) {

	        Long contri = (Long) request.getAttribute("contri");
	        Long conint = new Long(0);
	        //Long progCUI = (Long) request.getAttribute("progCUI");
	        Long numoi = (Long) request.getAttribute("numoi");
	        PtManager ptManager = (PtManager) UtilitySpring.getBean("ptManager",
	            servletContext, PtManager.class);

	        ////////////////////////////////////
	        //Nuova gestione Calcolo CUIIM (febbraio 2019)
	        String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
	        String maxCUIIM = ptManager.calcolaCUIImmobile(contri, codein);
	        ////////////////////////////////////
	        
	        DefaultGestoreEntita gestoreIMMTRAI = new DefaultGestoreEntita("IMMTRAI", request);
	        DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
	            dataColumnContainer.getColumns("IMMTRAI", 0));

	        int numeroImmobiliPresenti = dataColumnContainer.getLong("NUMERO_IMMTRAI").intValue();

	        for (int i = 1; i <= numeroImmobiliPresenti; i++) {
	          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
	              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

	          boolean deleteOccorrenza = newDataColumnContainer.isColumn("DEL_IMMTRAI")
	              && "1".equals(newDataColumnContainer.getString("DEL_IMMTRAI"));

	          newDataColumnContainer.addColumn("IMMTRAI.NUMOI", 
	        		  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numoi));
	          
	          if (deleteOccorrenza) {
	            if (newDataColumnContainer.isColumn("MOD_IMMTRAI")) {
	              newDataColumnContainer.removeColumns(new String[] {
	                  "IMMTRAI.DEL_IMMTRAI", "IMMTRAI.MOD_IMMTRAI",
	                  "IMMTRAI.INS_IMMTRAI" });
	              gestoreIMMTRAI.elimina(status, newDataColumnContainer);
	            }
	          }
	        }
	        for (int i = 1; i <= numeroImmobiliPresenti; i++) {
	          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
	              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));

	          newDataColumnContainer.addColumn("IMMTRAI.NUMOI", 
	        		  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numoi));

	          if (newDataColumnContainer.isModifiedTable("IMMTRAI")) {
	            if (newDataColumnContainer.isColumn("INS_IMMTRAI")
	                && "1".equals(newDataColumnContainer.getString("INS_IMMTRAI"))
	                && "0".equals(newDataColumnContainer.getString("DEL_IMMTRAI"))) {
	              newDataColumnContainer.removeColumns(new String[] {
	                  "IMMTRAI.INS_IMMTRAI", "IMMTRAI.DEL_IMMTRAI" });

	              int num = 0;
	              String sqlSelectImmobili = "select max(NUMIMM)+1 "
	                  + "from IMMTRAI where CONTRI = "
	                  + newDataColumnContainer.getColumn("IMMTRAI.CONTRI").getValue()
	                  + " and CONINT = "
	                  + newDataColumnContainer.getColumn("IMMTRAI.CONINT").getValue();

	              Long numImmobili = (Long) this.geneManager.getSql().getObject(
	                  sqlSelectImmobili, new Object[] {});
	              if (numImmobili != null) {
	                num = Integer.parseInt(numImmobili + "");
	              } else {
	                num = 1;
	              }
	              newDataColumnContainer.getColumn("IMMTRAI.NUMIMM").setValue(
	                  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(num)));
	              newDataColumnContainer.getColumn("IMMTRAI.NUMIMM").setChiave(true);

	              //calcolo del CUI dell'immobile
	              if (newDataColumnContainer.isColumn("IMMTRAI.CUIIMM")) {
	                String cuiimm = null;
	                ////////////////////////////////////
	                //Nuova gestione Calcolo CUIIM (febbraio 2019)
	                //...
	                ////////////////////////////////////
	                
	                if (newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getValue() != null) {
	                  //Verifico  il FORMATO
	                  cuiimm = newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getStringValue();

	                  if (!ptManager.verificaFormatoCuiImmobile(contri,conint,cuiimm)) {
	                    throw new GestoreException(
	                        "Il CUI dell'immobile non rispetta il formato previsto",
	                        "formatocui.immobili");
	                  }
	                } else {
	                  ////////////////////////////////////
	                  //Nuova gestione Calcolo CUIIM (febbraio 2019)
	                    maxCUIIM = ptManager.incrementaCUIImmobile(maxCUIIM);
	                  ////////////////////////////////////
	                    newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").setValue(
	                        new JdbcParametro(JdbcParametro.TIPO_TESTO, maxCUIIM));
	                }
	              }

	              gestoreIMMTRAI.inserisci(status, newDataColumnContainer);
	            } else if (newDataColumnContainer.isColumn("MOD_IMMTRAI")
	                && "1".equals(newDataColumnContainer.getString("MOD_IMMTRAI"))
	                && "0".equals(newDataColumnContainer.getString("DEL_IMMTRAI"))) {
	              newDataColumnContainer.removeColumns(new String[] {
	                  "IMMTRAI.MOD_IMMTRAI", "IMMTRAI.DEL_IMMTRAI" });

	              String cuiimm = null;
	              ////////////////////////////////////
                  //Nuova gestione Calcolo CUIIM (febbraio 2019)
	              //...
	              ////////////////////////////////////

	              if (newDataColumnContainer.isColumn("IMMTRAI.CUIIMM") && 
	                      newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getValue() != null) {
	                //Verifico  il FORMATO
	                cuiimm = newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getStringValue();

	                if (!ptManager.verificaFormatoCuiImmobile(contri,conint,cuiimm)) {
	                  throw new GestoreException(
	                      "Il CUI dell'immobile non rispetta il formato previsto",
	                      "formatocui.immobili");
	                }
	              }
	              gestoreIMMTRAI.update(status, newDataColumnContainer);
	            }
	          }
	        }
	      }
	    } catch (SQLException se) {
	      throw new GestoreException(
	          "Errore durante la gestione degli Immobili da trasferire",
	          "gestione.immobili", se);
	    }
	  }
  
  
  @Override
  public void postDelete(DataColumnContainer arg0) throws GestoreException {

  }

  @Override
  public void postInsert(DataColumnContainer arg0) throws GestoreException {

  }

  @Override
  public void postUpdate(DataColumnContainer arg0) throws GestoreException {

  }

  @Override
  public void preDelete(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

  @Override
  public void preInsert(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

  @Override
  public void preUpdate(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

  public void gestioneIMMTRAIdaFABBISOGNI(HttpServletRequest request, TransactionStatus status,
      DataColumnContainer dataColumnContainer, ServletContext servletContext)
      throws GestoreException {
  
    try {
      if (dataColumnContainer.isColumn("NUMERO_IMMTRAI")) {
  
//        Long contri = (Long) request.getAttribute("contri");
//        Long progCUI = (Long) request.getAttribute("progCUI");
//  
//        PtManager ptManager = (PtManager) UtilitySpring.getBean("ptManager",
//            servletContext, PtManager.class);
  
  
        DefaultGestoreEntita gestoreIMMTRAI = new DefaultGestoreEntita(
            "IMMTRAI", request);
        DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
            dataColumnContainer.getColumns("IMMTRAI", 0));
  
        int numeroImmobiliPresenti = dataColumnContainer.getLong(
            "NUMERO_IMMTRAI").intValue();
  
        for (int i = 1; i <= numeroImmobiliPresenti; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));
  
          boolean deleteOccorrenza = newDataColumnContainer.isColumn("DEL_IMMTRAI")
              && "1".equals(newDataColumnContainer.getString("DEL_IMMTRAI"));
  
          if (deleteOccorrenza) {
            if (newDataColumnContainer.isColumn("MOD_IMMTRAI")) {
              newDataColumnContainer.removeColumns(new String[] {
                  "IMMTRAI.DEL_IMMTRAI", "IMMTRAI.MOD_IMMTRAI",
                  "IMMTRAI.INS_IMMTRAI" });
              gestoreIMMTRAI.elimina(status, newDataColumnContainer);
            }
          }
        }
        for (int i = 1; i <= numeroImmobiliPresenti; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));
  
          if (newDataColumnContainer.isModifiedTable("IMMTRAI")) {
            if (newDataColumnContainer.isColumn("INS_IMMTRAI")
                && "1".equals(newDataColumnContainer.getString("INS_IMMTRAI"))
                && "0".equals(newDataColumnContainer.getString("DEL_IMMTRAI"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "IMMTRAI.INS_IMMTRAI", "IMMTRAI.DEL_IMMTRAI" });
  
              int num = 0;
              String sqlSelectImmobili = "select max(NUMIMM)+1 "
                  + "from IMMTRAI where CONTRI = "
                  + newDataColumnContainer.getColumn("IMMTRAI.CONTRI").getValue()
                  + " and CONINT = "
                  + newDataColumnContainer.getColumn("IMMTRAI.CONINT").getValue();
  
              Long numImmobili = (Long) this.geneManager.getSql().getObject(
                  sqlSelectImmobili, new Object[] {});
              if (numImmobili != null) {
                num = Integer.parseInt(numImmobili + "");
              } else {
                num = 1;
              }
              newDataColumnContainer.getColumn("IMMTRAI.NUMIMM").setValue(
                  new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(num)));
              newDataColumnContainer.getColumn("IMMTRAI.NUMIMM").setChiave(true);
  
              //calcolo del CUI dell'immobile
//  
//              if (newDataColumnContainer.isColumn("IMMTRAI.CUIIMM")) {
//                String cuiimm = null;
//                String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
//                String cfamm = (String) this.geneManager.getSql().getObject("select CFEIN from UFFINT where CODEIN=?", new Object[]{codein});
//                cfamm = UtilityStringhe.convertiNullInStringaVuota(cfamm);
//  
//                if (newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getValue() != null) {
//                  //Verifico  il FORMATO
//                  cuiimm = newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getStringValue();
//                  Long conint = (Long) newDataColumnContainer.getColumn("IMMTRAI.CONINT").getValue().getValue();
//                  if (!ptManager.verificaFormatoCuiImmobile(contri,conint,cuiimm)) {
//                    throw new GestoreException(
//                        "Il CUI dell'immobile non rispetta il formato previsto",
//                        "formatocui.immobili");
//                  }
//                } else {
//                  progCUI++;
//  
//                  String stranno = null;
//                  String selAnno = "select ANNTRI from PIATRI where CONTRI=?";
//                  Long anno = (Long) this.geneManager.getSql().getObject(selAnno, new Object[]{contri});
//                  if (anno != null) {
//                    stranno = anno.toString();
//                    UtilityStringhe.convertiNullInStringaVuota(stranno);
//                  }
//  
//                  String strprog = progCUI.toString();
//                  strprog = UtilityStringhe.fillLeft(strprog, '0', 5);
//  
//                  if (!"".equals(cfamm) && !"".equals(stranno) && !"".equals(strprog)) {
//                    cuiimm = "I" + cfamm + stranno + strprog;
//  
//                    newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").setValue(
//                        new JdbcParametro(JdbcParametro.TIPO_TESTO, cuiimm));
//  
//                  }
//                }
//              }
              gestoreIMMTRAI.inserisci(status, newDataColumnContainer);
            } else if (newDataColumnContainer.isColumn("MOD_IMMTRAI")
                && "1".equals(newDataColumnContainer.getString("MOD_IMMTRAI"))
                && "0".equals(newDataColumnContainer.getString("DEL_IMMTRAI"))) {
              newDataColumnContainer.removeColumns(new String[] {
                  "IMMTRAI.MOD_IMMTRAI", "IMMTRAI.DEL_IMMTRAI" });
  
              String cuiimm = null;
              String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
              String selCFAMM = "select CFEIN from UFFINT where CODEIN = ?";
              String cfamm = (String) this.geneManager.getSql().getObject(selCFAMM, new Object[]{codein});
              cfamm = UtilityStringhe.convertiNullInStringaVuota(cfamm);
  
//              if (newDataColumnContainer.isColumn("IMMTRAI.CUIIMM") && newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getValue() != null) {
//                //Verifico  il FORMATO
//                cuiimm = newDataColumnContainer.getColumn("IMMTRAI.CUIIMM").getValue().getStringValue();
//                Long conint = (Long) newDataColumnContainer.getColumn("IMMTRAI.CONINT").getValue().getValue();
//                if (!ptManager.verificaFormatoCuiImmobile(contri,conint,cuiimm)) {
//                  throw new GestoreException(
//                      "Il CUI dell'immobile non rispetta il formato previsto",
//                      "formatocui.immobili");
//                }
//              }
              gestoreIMMTRAI.update(status, newDataColumnContainer);
            }
          }
        }
      }
    } catch (SQLException se) {
      throw new GestoreException(
          "Errore durante la gestione degli Immobili da trasferire",
          "gestione.immobili", se);
    }
  }
}
