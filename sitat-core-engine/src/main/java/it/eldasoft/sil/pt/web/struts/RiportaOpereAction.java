package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.transaction.TransactionStatus;

/**
 * Action per riportare dal programma precedente le opere selezionate. 
 * 
 * @author Mirco.Franzoni
 */
public class RiportaOpereAction extends ActionBaseNoOpzioni {

    private static Logger logger = Logger.getLogger(RiportaOpereAction.class);

    /** manager per operazioni di interrogazione del db. */
    private SqlManager  sqlManager; 
    
    /**
     * @param sqlManager
     *        manager da settare internamente alla classe.
     */
    public void setSqlManager(SqlManager sqlManager) {
      this.sqlManager = sqlManager;
    }
    
    /** 
     * Metodo per riportare le opere da un programma all'altro.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward runAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException {
        
      if (logger.isDebugEnabled()) {
        logger.debug("riportaOpere: inizio metodo");
      }
       
      String target = CostantiGeneraliStruts.FORWARD_OK;
      boolean commitTransazione = false;
      TransactionStatus status = null;
        
      Long contriOperaDest = null;
      try {
        String[] keys = request.getParameterValues("keys");
            
        if (keys != null && keys.length > 0) {
          status = this.sqlManager.startTransaction();
                
          for (int i=0; i < keys.length; i++) {
            String[] chiavi = keys[i].split(";");
                    
            Long contriOperaSorgente = new Long(chiavi[0]);
            Long numoiOperaSorgente = new Long(chiavi[1]);
            Long contriOperaDestinazione = new Long(chiavi[2]);
            Long numoiOperaDestinazione = null;
            if (contriOperaDest == null) {
            	contriOperaDest = new Long(chiavi[2]);
            }
            // Copia opera
            DataColumnContainer dccOitri = new DataColumnContainer(this.sqlManager, "OITRI", 
                "select * from OITRI where CONTRI=? and NUMOI=?",
                new Object[] { contriOperaSorgente, numoiOperaSorgente });
            dccOitri.getColumn("OITRI.CONTRI").setChiave(true);
            dccOitri.getColumn("OITRI.NUMOI").setChiave(true);
            dccOitri.setValue("OITRI.NUMOI", null);
            dccOitri.setOriginalValue("OITRI.NUMOI", null);
            dccOitri.setValue("CONTRI", contriOperaDestinazione);
            
            AbstractGestoreEntita gestoreOitri = new DefaultGestoreEntitaChiaveNumerica(
                    "OITRI", "NUMOI", new String[] { "CONTRI" }, request);
            gestoreOitri.inserisci(status, dccOitri);
            numoiOperaDestinazione = dccOitri.getLong("OITRI.NUMOI");
                   
            // Copia immobili dell'opera
            List<Vector<?>> listaNumImm = this.sqlManager.getListVector(
            		"select NUMIMM from IMMTRAI where CONTRI=? and CONINT=0 and NUMOI = ?",
                    new Object[] { contriOperaSorgente, numoiOperaSorgente });
                    
            if (listaNumImm != null && listaNumImm.size() > 0) {
            	for (int j=0; j < listaNumImm.size(); j++) {
            		Long numImm = new Long((listaNumImm.get(j)).get(0).toString());
                    DataColumnContainer implImmtrai = new DataColumnContainer(this.sqlManager, "IMMTRAI",
                            "select * from IMMTRAI where CONTRI=? and CONINT=0 and NUMOI = ? and NUMIMM=?",
                            new Object[] { contriOperaSorgente, numoiOperaSorgente, numImm });
                    implImmtrai.setValue("CONTRI", contriOperaDestinazione);
                    implImmtrai.setValue("CONINT", new Long(0));
                    implImmtrai.setValue("NUMOI", numoiOperaDestinazione);
    
                    AbstractGestoreEntita gestoreImmtrai = new DefaultGestoreEntitaChiaveNumerica(
                    		"IMMTRAI", "NUMIMM", new String[] {"CONTRI", "CONINT"}, request);
                    gestoreImmtrai.inserisci(status, implImmtrai);
                }
            }
          }
          request.setAttribute("esito", "ok");
          commitTransazione = true;
          }
        } catch (SQLException e) {
            logger.error( "Errore nel recupero dei dati delle opere da incollare.", e);
            aggiungiMessaggio(request, "errors.generico", "Errore nel recupero dei dati delle opere da incollare.");
            target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "RiportaOpere";
        } catch (GestoreException e) {
            logger.error("Errore in inserimento dei dati dell'opera incompiuta nel db.", e);
            aggiungiMessaggio(request, "errors.generico", "Errore in inserimento dei dati dell'opera incompiuta nel db.");
            target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "RiportaOpere";
        } finally {
          if (status != null) {
            try {
                if (commitTransazione) {
                    this.sqlManager.commitTransaction(status);
                } else {
                    this.sqlManager.rollbackTransaction(status);
                }
            } catch (SQLException e) {
                logger.error("Errore durante la chiusura della transazione", e);
            }
          }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("riportaOpere: fine metodo");
        }
        return mapping.findForward(target);
    }

}
