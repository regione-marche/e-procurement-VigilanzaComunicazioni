package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.ListaAction;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.transaction.TransactionStatus;

public class CopiaPubblicazioneAction extends ListaAction {

  /** Logger Log4J di classe. */
  private static Logger logger = Logger.getLogger(CopiaPubblicazioneAction.class);

  public ActionForward copiaPubblicazione(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String target = CostantiGeneraliStruts.FORWARD_OK;

    DataColumnContainer campiDaCopiare = null;
    DataColumnContainer campiDaInserire = null;

    List<?> listaOccorrenzeDaCopiare = null;
    Long contri = new Long(request.getParameter("contri"));

    GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager", request.getSession().getServletContext(), GeneManager.class);

    List<Vector<?>> columns = null;
    TransactionStatus status = null;
    Boolean error = true;

    String selectContriFrom = "select CONTRI from PIATRI where CONTRI = ?";

    try {
      status = geneManager.getSql().startTransaction();

      // recupero l'ID del programma
      String id = (String) geneManager.getSql().getObject("select ID from PIATRI where CONTRI = ?", new Object[] { contri });
      String descrizione = (String) geneManager.getSql().getObject("select BRETRI from PIATRI where CONTRI = ?", new Object[] { contri });
      if (descrizione.indexOf(" - Aggiornamento") != -1) {
        descrizione = descrizione.substring(0, descrizione.indexOf(" - Aggiornamento"));
      }
      descrizione = descrizione.concat(" - Aggiornamento");

      // calcolo il nuovo ID
      // LP 81000490466 2010 101
      String tipoId = "%" + id.subSequence(0, 17).toString() + "%";

      String newId = (String) geneManager.getSql().getObject("select max(ID) from PIATRI where ID like ? ", new Object[] { tipoId });
      Long newContatore = new Long(newId.substring(newId.length()-3).toString()) + 1;
      newId = newId.substring(0, newId.length() - 3) + "000".substring(newContatore.toString().length()) + newContatore ;
      //descrizione = descrizione + (newContatore - 1);

      // interrogo il database per sapere il nome delle tabelle ed escludo
      String selectColumns = "*";
      // Distinguo l'istruzione per DB, seleziono tutte le colonne
      // esclusi i campi BLOB
      String dbms = ConfigManager.getValore(CostantiGenerali.PROP_DATABASE);
      if ("ORA".equals(dbms)) {
          selectColumns = " select column_name,data_type from USER_TAB_COLUMNS where table_name= ? and data_type <> 'BLOB'";
      }
      if ("MSQ".equals(dbms)) {
          selectColumns = " SELECT name FROM sys.columns WHERE object_id = OBJECT_ID(?) and name <> 'FILE_ALLEGATO'";
      }
      if ("DB2".equals(dbms)) {
          selectColumns = " select colname,typename from syscat.columns where tabname = ? and typename <> 'BLOB'";
      }
      if ("POS".equals(dbms)) {
          selectColumns = " SELECT column_name,data_type FROM information_schema.columns where upper(table_name) = ? AND table_schema=current_schema AND upper(data_type) <> 'BYTEA' ";
      }

      columns = geneManager.getSql().getListVector(selectColumns, new Object[] { "PIATRI" });

      String select = " select ";

      for (int i = 0; i < columns.size(); i++) {
          select = select + columns.get(i).get(0).toString() + ",";
      }
      select = select.substring(0, (select.length() - 1)) + " from PIATRI where contri = ?";

      // recupero i capi dati dalla tabella del programma
      campiDaCopiare = new DataColumnContainer(geneManager.getSql(), "PIATRI", select, new Object[] { contri });
      campiDaInserire = new DataColumnContainer(geneManager.getSql(), "PIATRI", selectContriFrom, new Object[] { contri });

      for (int i = 0; i < columns.size(); i++) {
        String colonna = "PIATRI" + "." + columns.get(i).get(0).toString();
        if (campiDaCopiare.getObject(colonna) != null && !(columns.get(i).get(0).toString().equals("CONTRI"))) {                
          campiDaInserire.addColumn(colonna, campiDaCopiare.getColumn(colonna).getTipoCampo(), campiDaCopiare.getColumn(colonna).getValue());
        }
      }

      // Dalla copia si escludono CONTRI, ID, BRETRI, ID_RICEVUTO, ID_GENERATO, i campi delle sezioni approvazioni e adozioni
      campiDaInserire.removeColumns(new String[] { "PIATRI.CONTRI", "PIATRI.ID", "PIATRI.BRETRI", "PIATRI.ID_RICEVUTO", "PIATRI.ID_GENERATO", 
          "PIATRI.TAPTRI", "PIATRI.NAPTRI", "PIATRI.DPAPPROV", "PIATRI.DAPTRI", "PIATRI.TITAPPROV", "PIATRI.URLAPPROV",
          "PIATRI.TADOZI", "PIATRI.NADOZI", "PIATRI.DPADOZI", "PIATRI.DADOZI", "PIATRI.TITADOZI", "PIATRI.URLADOZI" });

      // calcolo il nuovo contri
      Long newContri = (Long) geneManager.getSql().getObject("select max(CONTRI)+1 from PIATRI ", new Object[] {});
      // setto il nuovo contri
      campiDaInserire.addColumn("PIATRI.CONTRI", JdbcParametro.TIPO_NUMERICO, newContri);

      // setto il nuovo ID
      campiDaInserire.addColumn("PIATRI.ID", JdbcParametro.TIPO_TESTO, newId);

      // setto il nuovo ID
      campiDaInserire.addColumn("PIATRI.BRETRI", JdbcParametro.TIPO_TESTO, descrizione);
      
      // inserisco nel database la tabella
      campiDaInserire.insert("PIATRI", geneManager.getSql());

      // mi occupo delle tabelle figlie
      // (aggiungendo tabelle nel db vanno aggiornate le liste)
      String[] selectTabelleProgramma = new String[5];

      selectTabelleProgramma[0] = "select * from inttri  where contri = ? ";
      selectTabelleProgramma[1] = "select * from inrtri  where contri = ? ";
      selectTabelleProgramma[2] = "select * from oitri   where contri = ? ";
      selectTabelleProgramma[3] = "select * from immtrai where contri = ? ";
      selectTabelleProgramma[4] = "select * from ris_capitolo where contri = ? ";

      String[] tabelleProgramma = new String[5];

      tabelleProgramma[0] = "INTTRI";
      tabelleProgramma[1] = "INRTRI";
      tabelleProgramma[2] = "OITRI";
      tabelleProgramma[3] = "IMMTRAI";
      tabelleProgramma[4] = "RIS_CAPITOLO";

      // ciclo sulle tabelle indicate
      for (int i = 0; i < tabelleProgramma.length; i++) {
        // ripeto per ogni tabella l'iter di Piatri modificando solo il contri
        String selectAll = "select * from " + tabelleProgramma[i];
        columns = geneManager.getSql().getListVector(selectColumns, new Object[] { tabelleProgramma[i] });
        
        select = " select ";
        for (int j = 0; j < columns.size(); j++) {
          select = select + columns.get(j).get(0).toString() + ",";
        }
        select = select.substring(0, (select.length() - 1)) + " from " + tabelleProgramma[i] + " where contri = ?";

        String tabellaContri = tabelleProgramma[i] + ".CONTRI";
        selectContriFrom = "select CONTRI from " + tabelleProgramma[i] + " where CONTRI = ?";
        listaOccorrenzeDaCopiare = geneManager.getSql().getListHashMap(

        selectTabelleProgramma[i], new Object[] { contri });

        if (listaOccorrenzeDaCopiare != null && listaOccorrenzeDaCopiare.size() > 0) {
          campiDaCopiare = new DataColumnContainer(geneManager.getSql(),
              tabelleProgramma[i], selectAll, new Object[] {});
          campiDaInserire = new DataColumnContainer(geneManager.getSql(), "PIATRI", selectContriFrom, new Object[] { contri });
          for (int row = 0; row < listaOccorrenzeDaCopiare.size(); row++) {
            campiDaCopiare.setValoriFromMap(
                ((HashMap<?, ?>) listaOccorrenzeDaCopiare.get(row)), true);

            campiDaCopiare.setValue(tabellaContri, newContri);

            campiDaCopiare.insert(tabelleProgramma[i], geneManager.getSql());
          }
        }
        
        /////////////////////////////////////////
        //Aprile 2019
        //ogni record di INTTRI copiato dovrà anche copiare i record di PTAPPROVAZIONI con ID_PROGRAMMA=PIATRI.CONTRI, calcolando il campo chiave NUMAPPR
        if(tabelleProgramma[i].equals("INTTRI")){
          String selectApprovazioni = "select * from ptapprovazioni where id_programma = ? order by conint ";
          List<?> listaApprovazioniSorgente = geneManager.getSql().getListHashMap(
              selectApprovazioni, new Object[] { contri });
          DataColumnContainer campiApprDaCopiare = null;
          if (listaApprovazioniSorgente != null && listaApprovazioniSorgente.size() > 0) {
            campiApprDaCopiare = new DataColumnContainer(geneManager.getSql(),
                "PTAPPROVAZIONI", "select * from PTAPPROVAZIONI" , new Object[] {});
            Long newConintAppr = new Long(0);
            Long newNumappr = new Long(0);
            for (int rowAppr = 0; rowAppr < listaApprovazioniSorgente.size(); rowAppr++) {
              campiApprDaCopiare.setValoriFromMap(
                  ((HashMap<?, ?>) listaApprovazioniSorgente.get(rowAppr)), true);
              newConintAppr = campiApprDaCopiare.getLong("PTAPPROVAZIONI.CONINT");
              newNumappr = (Long) geneManager.getSql().getObject("select max(NUMAPPR)+1 from PTAPPROVAZIONI where CONINT=? ",
                  new Object[] { newConintAppr });
              campiApprDaCopiare.setValue("PTAPPROVAZIONI.ID_PROGRAMMA", newContri);
              campiApprDaCopiare.setValue("PTAPPROVAZIONI.NUMAPPR", newNumappr);
              campiApprDaCopiare.insert("PTAPPROVAZIONI", geneManager.getSql());
            }
          }
        }
        ////////////////////////////////////////
      }
      error = false;
    } catch (GestoreException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("errore nella duplicazione del programma", e);
      aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", e.getMessage());
    } catch (Exception ex) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("errore nella duplicazione del programma", ex);
      aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", ex.getMessage());
    } finally {
      try {
        if (error) {
            geneManager.getSql().rollbackTransaction(status);
        } else {
            try {
                geneManager.getSql().commitTransaction(status);
            } catch (SQLException e) {
                geneManager.getSql().rollbackTransaction(status);
            }
        }
      } catch (SQLException e) {
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
          logger.error("errore nella duplicazione del programma", e);
          aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", e.getMessage());
      }
    }

    ActionForward vaiA = mapping.findForward(target);
    String messageKey = null;

    try {
      vaiA = UtilityTags.getUtilityHistory(request.getSession()).back(request);
    } catch (JspException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      vaiA = mapping.findForward(target);
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey);
    }

    // return super.leggi(mapping, form, request, response);
    return vaiA;
    // return super.apri(mapping, form, request, response);
    // return mapping.findForward(target);
  }
}
