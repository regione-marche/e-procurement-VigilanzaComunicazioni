package it.eldasoft.sil.pt.web.struts;
 
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.transaction.TransactionStatus;

/**
 * Action per riportare dal programma precedente gli interventi e
 * gli interventi non riproposti selezionati nel programma attuale. 
 * 
 * @author Luca.Giacomazzo
 */
public class RiportaInterventiAction extends DispatchActionBaseNoOpzioni {

    private static Logger logger = Logger.getLogger(RiportaInterventiAction.class);

    /** manager per operazioni di interrogazione del db. */
    private SqlManager  sqlManager; 
    
    private PtManager   ptManager;  
    
    /**
     * @param sqlManager
     *        manager da settare internamente alla classe.
     */
    public void setSqlManager(SqlManager sqlManager) {
      this.sqlManager = sqlManager;
    }
    
    public void setPtManager(PtManager ptManager) {
        this.ptManager = ptManager;
    }
    
    /** 
     * Metodo per riportare gli interventi da un programma all'altro.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward riportaInterventi(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException {
        
      if (logger.isDebugEnabled()) {
        logger.debug("riportaInterventi: inizio metodo");
      }
       
      String target = CostantiGeneraliStruts.FORWARD_OK;
      boolean commitTransazione = false;
      TransactionStatus status = null;
        
      String operazione = request.getParameter("operazione");
      Long contriInterventoDest = null;
      try {
        String[] keys = request.getParameterValues("keys");
            
        if (keys != null && keys.length > 0) {
          status = this.sqlManager.startTransaction();
                
          for (int i=0; i < keys.length; i++) {
            String[] chiavi = keys[i].split(";");
                    
            Long contriInterventoSorgente = new Long(chiavi[0]);
            Long conintInterventoSorgente = new Long(chiavi[1]);
            Long contriInterventoDestinazione = new Long(chiavi[2]);
            Long conintInterventoDestinazione = null;
            if (contriInterventoDest == null) {
                contriInterventoDest = new Long(chiavi[2]);
            }
            // Copia intervento
            DataColumnContainer dccInntri = new DataColumnContainer(this.sqlManager, "INTTRI", 
                "select * from INTTRI where CONTRI=? and CONINT=?",
                new Object[] { contriInterventoSorgente, conintInterventoSorgente });
            dccInntri.getColumn("INTTRI.CONTRI").setChiave(true);
            dccInntri.getColumn("INTTRI.CONINT").setChiave(true);
            dccInntri.setValue("INTTRI.CONINT", null);
            dccInntri.setOriginalValue("INTTRI.CONINT", null);
            dccInntri.setValue("CONTRI", contriInterventoDestinazione);
            dccInntri.setValue("INTTRI.VARIATO", null);
            AbstractGestoreEntita gestoreInttri = new DefaultGestoreEntitaChiaveNumerica(
                    "INTTRI", "CONINT", new String[] { "CONTRI" }, request);
            gestoreInttri.inserisci(status, dccInntri);
            conintInterventoDestinazione = dccInntri.getLong("INTTRI.CONINT");

             this.sqlManager.update(
               "update INTTRI set NPROGINT=? where CONTRI=? and CONINT=?",
               new Object[] { conintInterventoDestinazione, contriInterventoDestinazione, conintInterventoDestinazione } );
                    
             // Se non valorizzato, si valorizza il CUIINT dell'intervento di destinazione e dell'intervento sorgente
             DataColumn dcCUIINT = dccInntri.getColumn("INTTRI.CUIINT");
             if (StringUtils.isEmpty(dcCUIINT.getValue().getStringValue())) {
               String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
               String cfein = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?",
                   new Object[] { codein });
               String tipoin = (String) dccInntri.getString("INTTRI.TIPOIN");
               Long anntri = (Long) this.sqlManager.getObject("select ANNTRI from PIATRI where CONTRI=?",
                   new Object[] { contriInterventoSorgente });
               String nuovoCUIINT = this.ptManager.calcolaCUIIntervento(contriInterventoSorgente, tipoin, cfein, anntri.toString());
            
               // Aggiornamento del CUIINT dell'intervento di destinazione
               this.sqlManager.update(
                   "update INTTRI set CUIINT=? where CONTRI=? and CONINT=?",
                     new Object[] { nuovoCUIINT, contriInterventoDestinazione, conintInterventoDestinazione } );
               // Aggiornamento del CUIINT dell'intervento sorgente            
               this.sqlManager.update(
                   "update INTTRI set CUIINT=? where CONTRI=? and CONINT=?",
                   new Object[] { nuovoCUIINT, contriInterventoSorgente, conintInterventoSorgente } );
             }
                    
             if ("2".equals(operazione)) {
                    // Si riportano gli importi nelle annualita' precedenti
                    this.sqlManager.update(
                            "update INTTRI set ANNRIF=CASE WHEN coalesce(ANNRIF,1)=1 THEN ANNRIF ELSE ANNRIF-1 END," 
                                + " DV1TRI=coalesce(DV1TRI,0)+coalesce(DV2TRI,0),DV2TRI=DV3TRI,DV3TRI=DV9TRI,DV9TRI=null,"
                                + " MU1TRI=coalesce(MU1TRI,0)+coalesce(MU2TRI,0),MU2TRI=MU3TRI,MU3TRI=MU9TRI,MU9TRI=null,"
                                + " PR1TRI=coalesce(PR1TRI,0)+coalesce(PR2TRI,0),PR2TRI=PR3TRI,PR3TRI=PR9TRI,PR9TRI=null,"
                                + " BI1TRI=coalesce(BI1TRI,0)+coalesce(BI2TRI,0),BI2TRI=BI3TRI,BI3TRI=BI9TRI,BI9TRI=null,"
                                + " AP1TRI=coalesce(AP1TRI,0)+coalesce(AP2TRI,0),AP2TRI=AP3TRI,AP3TRI=AP9TRI,AP9TRI=null,"
                                + " IM1TRI=coalesce(IM1TRI,0)+coalesce(IM2TRI,0),IM2TRI=IM3TRI,IM3TRI=IM9TRI,IM9TRI=null,"
                                + " AL1TRI=coalesce(AL1TRI,0)+coalesce(AL2TRI,0),AL2TRI=AL3TRI,AL3TRI=AL9TRI,AL9TRI=null,"
                                + " DI1INT=coalesce(DI1INT,0)+coalesce(DI2INT,0),DI2INT=DI3INT,DI3INT=DI9INT,DI9INT=null,"
                                + " IV1TRI=coalesce(IV1TRI,0)+coalesce(IV2TRI,0),IV2TRI=IV3TRI,IV3TRI=IV9TRI,IV9TRI=null "
                                + " where CONTRI=? and CONINT=?",
                            new Object[] { contriInterventoDestinazione, conintInterventoDestinazione } );

                    Long tiprog = (Long)this.sqlManager.getObject("select TIPROG from PIATRI where CONTRI=?", new Object[] { contriInterventoSorgente } );
                    if (tiprog.longValue() == 2) {
                        this.sqlManager.update("Update INTTRI set "
                              + " DV2TRI=COALESCE(DV2TRI,0)+COALESCE(DV3TRI,0),DV3TRI=null, "
                              + " MU2TRI=COALESCE(MU2TRI,0)+COALESCE(MU3TRI,0),MU3TRI=null, "
                                    + " PR2TRI=COALESCE(PR2TRI,0)+COALESCE(PR3TRI,0),PR3TRI=null, "
                                    + " BI2TRI=COALESCE(BI2TRI,0)+COALESCE(BI3TRI,0),BI3TRI=null, "
                                    + " AP2TRI=COALESCE(AP2TRI,0)+COALESCE(AP3TRI,0),AP3TRI=null, "
                                    + " IM2TRI=COALESCE(IM2TRI,0)+COALESCE(IM3TRI,0),IM3TRI=null, "
                                    + " AL2TRI=COALESCE(AL2TRI,0)+COALESCE(AL3TRI,0),AL3TRI=null, "
                                    + " DI2INT=COALESCE(DI2INT,0)+COALESCE(DI3INT,0),DI3INT=null, "
                                    + " IV2TRI=COALESCE(IV2TRI,0)+COALESCE(IV3TRI,0),IV3TRI=null " 
                                    + " where CONTRI=? and CONINT=?",
                                    new Object[] { contriInterventoDestinazione, conintInterventoDestinazione } );
                    }
                        
                    // Aggiornamento del quadro econimico dell'intero programma
                    this.ptManager.aggiornaCostiPiatri(contriInterventoDest);
                  } else if ("1".equals(operazione)) {
                    // Si sbiancano tutti gli importi dell'intervento riportato
                    this.sqlManager.update(
                            "update INTTRI set DV1TRI=null,DV2TRI=null,DV3TRI=null,DV9TRI=null,"
                                + " MU1TRI=null,MU2TRI=null,MU3TRI=null,MU9TRI=null,"
                                + " PR1TRI=null,PR2TRI=null,PR3TRI=null,PR9TRI=null,"
                                + " BI1TRI=null,BI2TRI=null,BI3TRI=null,BI9TRI=null,"
                                + " AP1TRI=null,AP2TRI=null,AP3TRI=null,AP9TRI=null,"
                                + " IM1TRI=null,IM2TRI=null,IM3TRI=null,IM9TRI=null,"
                                + " AL1TRI=null,AL2TRI=null,AL3TRI=null,AL9TRI=null,"
                                + " DI1INT=null,DI2INT=null,DI3INT=null,DI9INT=null,"
                                + " TOTINT=null,"
                                + " IV1TRI=null,IV2TRI=null,IV3TRI=null,IV9TRI=null,"
                                + " DITINT=null,ICPINT=null,SPESESOST=null "
                                + " where CONTRI=? and CONINT=?",
                            new Object[] { contriInterventoDestinazione, conintInterventoDestinazione } );
                  } else if ("3".equals(operazione)) {
                    
                    // Mapping del campo SEZINT:
                    // il valore '06' (Manutenzione) ha più corrispondenze
                    // il valore '07' (Completamento) non ha corrispondenze
                    // Gli altri valori coincidono
                    //this.sqlManager.update("update INTTRI set SEZINT=null where CONTRI=? and CONINT=? and SEZINT in ('06', '07')",
                    //    new Object[] { contriInterventoDestinazione, conintInterventoDestinazione });
                    
                    // Mapping del campo INTERV:
                    /*String dbSubStrFn = this.sqlManager.getDBFunction("substr", new String[] { "INTERV", "2", "3" });
                    String dbConcatFn = this.sqlManager.getDBFunction("concat", new String[] { dbSubStrFn, "'.'" });
                    dbConcatFn = this.sqlManager.getDBFunction("concat", new String[] { dbConcatFn, "CATINT" });
                    String oldInterv = (String) this.sqlManager.getObject(
                        "select ".concat(dbConcatFn).concat(" from INTTRI where CONTRI=? and CONINT=?"),
                        new Object[] { contriInterventoDestinazione, conintInterventoDestinazione });
                    String sqlUpdateInterv = null;
                    if (oldInterv != null) {
                        if ("04.07".equals(oldInterv)) {
                              sqlUpdateInterv = "update INTTRI set INTERV='07.17' where CONTRI=? and CONINT=?";
                            } else if ("05.09".equals(oldInterv)) {
                              sqlUpdateInterv = "update INTTRI set INTERV='05.99' where CONTRI=? and CONINT=?";
                            } else if ("04.13".equals(oldInterv)) {
                              sqlUpdateInterv = "update INTTRI set INTERV='06.13' where CONTRI=? and CONINT=?";
                            } else if ("04.14".equals(oldInterv)) {
                              sqlUpdateInterv = "update INTTRI set INTERV='06.14' where CONTRI=? and CONINT=?";
                            } else if ("05.35".equals(oldInterv)) {
                              sqlUpdateInterv = "update INTTRI set INTERV='05.30' where CONTRI=? and CONINT=?";
                            } else if ("05.37".equals(oldInterv)) {
                              sqlUpdateInterv = "update INTTRI set INTERV='06.41' where CONTRI=? and CONINT=?";
                            } else if ("01.88".equals(oldInterv)) {
                              sqlUpdateInterv = "update INTTRI set INTERV='01.06' where CONTRI=? and CONINT=?";
                            } else if("10.40".equals(oldInterv) || "10.99".equals(oldInterv)) {
                              sqlUpdateInterv = "update INTTRI set INTERV='10.41' where CONTRI=? and CONINT=?";
                            } else if ("04.40".equals(oldInterv) ||  
                                       "10.41".equals(oldInterv) || 
                                       "06.90".equals(oldInterv) || "02.99".equals(oldInterv) ||
                                       "03.99".equals(oldInterv) ) {
                              sqlUpdateInterv = "update INTTRI set INTERV=null where CONTRI=? and CONINT=?";
                            } else {
                              sqlUpdateInterv = "update INTTRI set INTERV='".concat(oldInterv).concat("' where CONTRI=? and CONINT=?");
                            }
                            if (sqlUpdateInterv != null) {
                              this.sqlManager.update(sqlUpdateInterv, 
                                  new Object[] { contriInterventoDestinazione, conintInterventoDestinazione });
                            }
                    }*/
                    
                    // Mapping del campo STAPRO:
                    /*String oldStapro = (String) this.sqlManager.getObject("select STAPRO from INTTRI where CONTRI=? and CONINT=? ",
                        new Object[] { contriInterventoDestinazione, conintInterventoDestinazione });
                    String sqlUpdateStapro = null;
                    if ("Sc".equals(oldStapro)) {
                      sqlUpdateStapro = "update INTTRI set STAPRO='1' where CONTRI=? and CONINT=?";
                    } else if ("Sf".equals(oldStapro)) {
              sqlUpdateStapro = "update INTTRI set STAPRO='2' where CONTRI=? and CONINT=?";
            } else if ("Pd".equals(oldStapro)) {
              sqlUpdateStapro = "update INTTRI set STAPRO='3' where CONTRI=? and CONINT=?";
            } else if ("Pe".equals(oldStapro)) {
              sqlUpdateStapro = "update INTTRI set STAPRO='4' where CONTRI=? and CONINT=?";
            } else if ("Pp".equals(oldStapro)) {
              sqlUpdateStapro = "update INTTRI set STAPRO=null where CONTRI=? and CONINT=?";
            }
                    
                    if (sqlUpdateStapro != null) {
              this.sqlManager.update(sqlUpdateStapro, 
                  new Object[] { contriInterventoDestinazione, conintInterventoDestinazione });
            }*/
                    
                    // Aggiornamento del quadro econimico dell'intero programma
            this.ptManager.aggiornaCostiPiatri(contriInterventoDest);
                  }
                    
                    // Copia immobili dell'intervento
                  List<Vector<?>> listaNumImm = this.sqlManager.getListVector(
                        "select NUMIMM from IMMTRAI where CONTRI=? and CONINT=?",
                            new Object[] { contriInterventoSorgente, conintInterventoSorgente });
                    
                  if (listaNumImm != null && listaNumImm.size() > 0) {
                    for (int j=0; j < listaNumImm.size(); j++) {
                        Long numImm = new Long((listaNumImm.get(j)).get(0).toString());
                        DataColumnContainer implImmtrai = new DataColumnContainer(this.sqlManager, "IMMTRAI",
                            "select * from IMMTRAI where CONTRI=? and CONINT=? and NUMIMM=?",
                                new Object[] { contriInterventoSorgente, conintInterventoSorgente, numImm });
                        implImmtrai.setValue("CONTRI", contriInterventoDestinazione);
                      implImmtrai.setValue("CONINT", conintInterventoDestinazione);
    
                      AbstractGestoreEntita gestoreImmtrai = new DefaultGestoreEntitaChiaveNumerica(
                          "IMMTRAI", "NUMIMM", new String[] {"CONTRI", "CONINT"}, request);
                      gestoreImmtrai.inserisci(status, implImmtrai);
                      Long numImmInterventoDestinazione = implImmtrai.getLong("IMMTRAI.NUMIMM");
                            
                if ("1".equals(operazione)) {
                    // Si sbiancano tutti gli importi dell'intervento riportato
                    this.sqlManager.update(
                        "update IMMTRAI set VA1IMM=null,VA2IMM=null,VA3IMM=null,VA9IMM=null,VALIMM=null where CONTRI=? and CONINT=? and NUMIMM=?",
                        new Object[] { contriInterventoDestinazione, conintInterventoDestinazione, numImmInterventoDestinazione } );
                } else if ("2".equals(operazione)) {
                    // Si riportano gli importi nelle annualita' precedenti
                    this.sqlManager.update(
                        "update IMMTRAI set VA1IMM=coalesce(VA1IMM,0)+coalesce(VA2IMM,0),VA2IMM=VA3IMM,VA3IMM=VA9IMM,VA9IMM=null where CONTRI=? and CONINT=? and NUMIMM=?",
                        new Object[] { contriInterventoDestinazione, conintInterventoDestinazione, numImmInterventoDestinazione } );
                }
                    }
                }
                    
                  // Copia delle risorse per capitolo di bilancio associate all'intervento
                  List<Vector<?>> listaNumCB = this.sqlManager.getListVector("select NUMCB from RIS_CAPITOLO where CONTRI=? and CONINT=?", 
                            new Object[] { contriInterventoSorgente, conintInterventoSorgente });
                    
                  if (listaNumCB != null && listaNumCB.size() > 0) {
                    for (int j = 0; j < listaNumCB.size(); j++) {
                            Long numCB = new Long((listaNumCB.get(j)).get(0).toString());
                        
                        DataColumnContainer dccRisCapitolo = new DataColumnContainer(this.sqlManager, "RIS_CAPITOLO", 
                                    "select * from RIS_CAPITOLO where CONTRI=? and CONINT=? and NUMCB=?",
                                    new Object[] { contriInterventoSorgente, conintInterventoSorgente, numCB });
                            dccRisCapitolo.getColumn("RIS_CAPITOLO.CONTRI").setChiave(true);
                            dccRisCapitolo.getColumn("RIS_CAPITOLO.CONINT").setChiave(true);
                            dccRisCapitolo.getColumn("RIS_CAPITOLO.NUMCB").setChiave(true);
                            dccRisCapitolo.setValue("RIS_CAPITOLO.CONTRI", contriInterventoDestinazione);
                            dccRisCapitolo.setValue("RIS_CAPITOLO.CONINT", conintInterventoDestinazione);
                            
                            AbstractGestoreEntita gestoreRisCapitolo = new DefaultGestoreEntitaChiaveNumerica(
                                    "RIS_CAPITOLO", "NUMCB", new String[] { "CONTRI", "CONINT" }, request);
                            gestoreRisCapitolo.inserisci(status, dccRisCapitolo);
                            Long numCBInterventoDestinazione = dccRisCapitolo.getLong("RIS_CAPITOLO.NUMCB");
                            
                            if ("1".equals(operazione)) {
                    // Si sbiancano tutti gli importi della risorsa per capitolo di bilancio appena copiato
                    this.sqlManager.update(
                        "update RIS_CAPITOLO set "
                            + "AL1CB=null,AL2CB=null,AL3CB=null,AL9CB=null, "
                            + "AP1CB=null,AP2CB=null,AP3CB=null,AP9CB=null, "
                            + "BI1CB=null,BI2CB=null,BI3CB=null,BI9CB=null, "
                            + "DV1CB=null,DV2CB=null,DV3CB=null,DV9CB=null, "
                            + "MU1CB=null,MU2CB=null,MU3CB=null,MU9CB=null, "
                            + "TO1CB=null,TO2CB=null,TO3CB=null,TO9CB=null, "
                            + "IV1CB=null,IV2CB=null,IV3CB=null,IV9CB=null, "
                            + "IMPALTCB=null,IMPRFSCB=null,RG1CB=null,SPESESOST=null "
                            + " where CONTRI=? and CONINT=? and NUMCB=?",
                        new Object[] { contriInterventoDestinazione, conintInterventoDestinazione, numCBInterventoDestinazione } );
                    } else if ("2".equals(operazione)) {
                            // Si riportano gli importi nelle annualita' precedenti nella risorsa per capitolo di bilancio appena copiato
                            this.sqlManager.update(
                                "update RIS_CAPITOLO set "
                                        + "AL1CB=coalesce(AL1CB,0)+coalesce(AL2CB,0),AL2CB=AL3CB,AL3CB=AL9CB,AL9CB=null, "
                                        + "AP1CB=coalesce(AP1CB,0)+coalesce(AP2CB,0),AP2CB=AP3CB,AP3CB=AP9CB,AP9CB=null, "
                                        + "BI1CB=coalesce(BI1CB,0)+coalesce(BI2CB,0),BI2CB=BI3CB,BI3CB=BI9CB,BI9CB=null, "
                                        + "DV1CB=coalesce(DV1CB,0)+coalesce(DV2CB,0),DV2CB=DV3CB,DV3CB=DV9CB,DV9CB=null, "
                                        + "MU1CB=coalesce(MU1CB,0)+coalesce(MU2CB,0),MU2CB=MU3CB,MU3CB=MU9CB,MU9CB=null, "
                                        + "TO1CB=coalesce(TO1CB,0)+coalesce(TO2CB,0),TO2CB=TO3CB,TO3CB=TO9CB,TO9CB=null, "
                                        + "IV1CB=coalesce(IV1CB,0)+coalesce(IV2CB,0),IV2CB=IV3CB,IV3CB=IV9CB,IV9CB=null "
                                        + " where CONTRI=? and CONINT=? and NUMCB=?",
                                    new Object[] { contriInterventoDestinazione, conintInterventoDestinazione, numCBInterventoDestinazione } );
                                
                            Long tiprog = (Long)this.sqlManager.getObject("select TIPROG from PIATRI where CONTRI=?", new Object[] { contriInterventoSorgente } );
                            if (tiprog.longValue() == 2) {
                              this.sqlManager.update("Update RIS_CAPITOLO set "
                                            + " AL2CB=COALESCE(AL2CB,0)+COALESCE(AL3CB,0),AL3CB=null, "
                                            + " AP2CB=COALESCE(AP2CB,0)+COALESCE(AP3CB,0),AP3CB=null, "
                                            + " BI2CB=COALESCE(BI2CB,0)+COALESCE(BI3CB,0),BI3CB=null, "
                                            + " DV2CB=COALESCE(DV2CB,0)+COALESCE(DV3CB,0),DV3CB=null, "
                                            + " MU2CB=COALESCE(MU2CB,0)+COALESCE(MU3CB,0),MU3CB=null, "
                                            + " TO2CB=COALESCE(TO2CB,0)+COALESCE(TO3CB,0),TO3CB=null, "
                                            + " IV2CB=COALESCE(IV2CB,0)+COALESCE(IV3CB,0),IV3CB=null "
                                            + " where CONTRI=? and CONINT=? and NUMCB=?",
                                            new Object[] { contriInterventoDestinazione, conintInterventoDestinazione, numCBInterventoDestinazione } );
                            }
                      }
                            }
                        this.ptManager.aggiornaCostiPiatri(contriInterventoDestinazione);
                    }
                  }
                  request.setAttribute("esito", "ok");
                  commitTransazione = true;
              }
        } catch (SQLException e) {
            logger.error( "Errore nel recupero dei dati intervento/i da incollare.", e);
            aggiungiMessaggio(request, "errors.riportaIntervento.recuperoDati");
            target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "RiportaInterventi";
        } catch (GestoreException e) {
            logger.error("Errore nell\'inserimento dei dati Intervento nel db.", e);
            aggiungiMessaggio(request, "errors.riportaIntervento.inserimentoDati");
            target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "RiportaInterventi";
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
            logger.debug("riportaInterventi: inizio metodo");
        }
        return mapping.findForward(target);
    }

  /**
   * Metodo per riportare gli interventi non riproposti da un programma all'altro o per
   * far diventare un intervento del programma precedente un intervento non riproposto.
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward riportaInterventiNonRiproposti(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response)
              throws IOException, ServletException {
      
    if (logger.isDebugEnabled()) {
      logger.debug("riportaInterventiNonRiproposti: inizio metodo");
    }
    
    String target = CostantiGeneraliStruts.FORWARD_OK;
    boolean commitTransazione = false;
    TransactionStatus status = null;
    
    try {
        String[] keys = request.getParameterValues("keys");
        if (keys != null && keys.length > 0) {
          status = this.sqlManager.startTransaction();
          for (int i=0; i < keys.length; i++) {
            String[] chiavi = keys[i].split(";");
            
            Long contriInterventoSorgente = new Long(chiavi[0]);
            Long conintInterventoSorgente = new Long(chiavi[1]);
            Long contriInterventoDestinazione = new Long(chiavi[2]);
            
            Vector<?> datiInterventoSorgente = this.sqlManager.getVector(
                    "select CONTRI, CONINT, CUIINT, CUPPRG, DESINT, TOTINT, PRGINT from INTTRI where CONTRI=? and CONINT=?",
                    new Object[] {contriInterventoSorgente, conintInterventoSorgente });
            
            Long valoreCONTRI = contriInterventoDestinazione;
            Long valoreCONINT = (Long) this.sqlManager.getObject("select max(CONINT) from INRTRI where CONTRI=?",
                    new Object[] { contriInterventoDestinazione });
            if (valoreCONINT == null) {
                valoreCONINT = new Long(1);
            } else {
                valoreCONINT = new Long(valoreCONINT.longValue()+1);
            }

            String valoreCUIINT = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 2).getValue() != null) 
                valoreCUIINT = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 2).stringValue();
            else {
              String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
              String cfein = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?",
                  new Object[] { codein });
              String tipoin = (String) this.sqlManager.getObject("select TIPOIN from INTTRI where CONTRI=? and CONINT=?",
                  new Object[] { contriInterventoSorgente, conintInterventoSorgente });
              Long anntri = (Long) this.sqlManager.getObject("select ANNTRI from PIATRI where CONTRI=?",
                  new Object[] { contriInterventoSorgente });
              valoreCUIINT = this.ptManager.calcolaCUIIntervento(contriInterventoSorgente, tipoin, cfein, anntri.toString());
              
              // Aggiornamento del CUIINT dell'intervento sorgente
              this.sqlManager.update("update INTTRI set CUIINT=? where CONTRI=? and CONINT=?",
                  new Object[] { valoreCUIINT, contriInterventoSorgente, conintInterventoSorgente } );
            }

            String valoreCUPPRG = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 3).getValue() != null)
              valoreCUPPRG = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 3).stringValue();
            
            String valoreDESINT = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 4).getValue() != null)
                valoreDESINT = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 4).stringValue();
            
            Double valoreTOTINT = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 5).getValue() != null)
                valoreTOTINT = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 5).doubleValue();
            
            Long valorePRGINT = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 6).getValue() != null)
                valorePRGINT = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 6).longValue();

            String inserimentoInterventoNonRiproposto = "INSERT INTO INRTRI (CONTRI,CONINT,CUIINT,CUPPRG,DESINT,TOTINT,PRGINT) VALUES (?,?,?,?,?,?,?)";
            this.sqlManager.update(inserimentoInterventoNonRiproposto, new Object[] { 
                    valoreCONTRI, valoreCONINT, valoreCUIINT, valoreCUPPRG, valoreDESINT, valoreTOTINT, valorePRGINT });
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //MARZO 2019
            //intervento collegato ad una proposta di intervento/acquisto
            String selectApprovazioni = "select conint from PTAPPROVAZIONI where ID_PROGRAMMA=? and ID_INTERV_PROGRAMMA=?";
            Long conintPta = (Long) sqlManager.getObject(selectApprovazioni, new Object[] { contriInterventoSorgente, conintInterventoSorgente } );
            if (conintPta != null) {
              String selectMaxNumappr = "select max(numappr) from PTAPPROVAZIONI where conint = ?";
              Long maxNumappr = (Long) sqlManager.getObject(selectMaxNumappr, new Object[] { conintPta });
              if (maxNumappr == null) maxNumappr = new Long(0);
              maxNumappr ++;
              //Timestamp  dataAppr1 = (Timestamp) new Date();
              Timestamp dataAppr = new Timestamp(new Date().getTime());
              ProfiloUtente profilo = (ProfiloUtente) request.getSession().getAttribute(
                  CostantiGenerali.PROFILO_UTENTE_SESSIONE);
              Long sysAppr = new Long(profilo.getId());
              String utenteAppr = profilo.getNome();
              Long  faseAppr = new Long(3);
              Long  esitoAppr = new Long(2);
              String noteAppr = "Intervento non riproposto in programmazione";
              Long  id_programmaAppr = valoreCONTRI;
              //Long  esitoAppr = new Long(2);
              String insertPTAPPROVAZIONI = "insert into PTAPPROVAZIONI( CONINT, NUMAPPR, DATAAPPR, SYSAPPR, UTENTEAPPR, FASEAPPR, ESITOAPPR, NOTEAPPR, ID_PROGRAMMA)" +
              " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
              this.sqlManager.update( insertPTAPPROVAZIONI, new Object[] {conintPta, maxNumappr, dataAppr, sysAppr, utenteAppr, faseAppr, esitoAppr, noteAppr, id_programmaAppr});
              String updateFABBISOGNI = "UPDATE FABBISOGNI SET STATO = ? WHERE CONINT = ?";
              this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(32), conintPta });
            } 
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        
        commitTransazione = true;
        request.setAttribute("esito", "ok");
      }
    } catch (SQLException e) {
      logger.error( "Errore nel recupero dei dati intervento non riproposto da riportare.", e);
      aggiungiMessaggio(request, "errors.riportaInterventoNonRiproposto.recuperoDati");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "RiportaInterventi";
    } catch (GestoreException e) {
      logger.error("Errore nell\'inserimento dei dati Intervento nel db.", e);
      aggiungiMessaggio(request, "errors.riportaInterventoNonRiproposto.inserimentoDati");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "RiportaInterventi";
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
        logger.debug("riportaInterventiNonRiproposti: fine metodo");
    }
    
    return mapping.findForward(target);
  }

  
  /**
   * Metodo per riportare gli interventi annullati o respinti da un programma all'altro.
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward riportaInterventiAnnullatiRespinti(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response)
              throws IOException, ServletException {
      
    if (logger.isDebugEnabled()) {
      logger.debug("riportaInterventiAnnullatiRespinti: inizio metodo");
    }
    
    String target = CostantiGeneraliStruts.FORWARD_OK;
    boolean commitTransazione = false;
    TransactionStatus status = null;
    
    try {
        String[] keys = request.getParameterValues("keys");
        if (keys != null && keys.length > 0) {
          status = this.sqlManager.startTransaction();
          for (int i=0; i < keys.length; i++) {
            String[] chiavi = keys[i].split(";");
            
            Long contriInterventoSorgente = new Long(chiavi[0]);
            Long conintInterventoSorgente = new Long(chiavi[1]);
            Long contriInterventoDestinazione = new Long(chiavi[2]);
            
            Vector<?> datiInterventoSorgente = this.sqlManager.getVector(
                    "select CONTRI, CONINT, CUIINT, CUPPRG, DESINT, TOTINT, PRGINT from INTTRI where CONTRI=? and CONINT=?",
                    new Object[] {contriInterventoSorgente, conintInterventoSorgente });
            
            Long valoreCONTRI = contriInterventoDestinazione;
            Long valoreCONINT = (Long) this.sqlManager.getObject("select max(CONINT) from INRTRI where CONTRI=?",
                    new Object[] { contriInterventoDestinazione });
            if (valoreCONINT == null) {
                valoreCONINT = new Long(1);
            } else {
                valoreCONINT = new Long(valoreCONINT.longValue()+1);
            }

            String valoreCUIINT = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 2).getValue() != null) 
                valoreCUIINT = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 2).stringValue();
            else {
              String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
              String cfein = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?",
                  new Object[] { codein });
              String tipoin = (String) this.sqlManager.getObject("select TIPOIN from INTTRI where CONTRI=? and CONINT=?",
                  new Object[] { contriInterventoSorgente, conintInterventoSorgente });
              Long anntri = (Long) this.sqlManager.getObject("select ANNTRI from PIATRI where CONTRI=?",
                  new Object[] { contriInterventoSorgente });
              valoreCUIINT = this.ptManager.calcolaCUIIntervento(contriInterventoSorgente, tipoin, cfein, anntri.toString());
              
              // Aggiornamento del CUIINT dell'intervento sorgente
              this.sqlManager.update("update INTTRI set CUIINT=? where CONTRI=? and CONINT=?",
                  new Object[] { valoreCUIINT, contriInterventoSorgente, conintInterventoSorgente } );
            }

            String valoreCUPPRG = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 3).getValue() != null)
              valoreCUPPRG = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 3).stringValue();
            
            String valoreDESINT = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 4).getValue() != null)
                valoreDESINT = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 4).stringValue();
            
            Double valoreTOTINT = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 5).getValue() != null)
                valoreTOTINT = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 5).doubleValue();
            
            Long valorePRGINT = null;
            if (SqlManager.getValueFromVectorParam(datiInterventoSorgente, 6).getValue() != null)
                valorePRGINT = SqlManager.getValueFromVectorParam(datiInterventoSorgente, 6).longValue();

            Long conintptappr = (Long) this.sqlManager.getObject("select CONINT from PTAPPROVAZIONI where id_programma=? and id_interv_programma=?",
                    new Object[] { contriInterventoSorgente, conintInterventoSorgente });
            String noteappr = null;
            if (conintptappr != null) {
            	noteappr = (String) this.sqlManager.getObject("select NOTEAPPR from PTAPPROVAZIONI where ESITOAPPR=4 and CONINT=?",
                        new Object[] { conintptappr });
            }
            
            String inserimentoInterventoNonRiproposto = "INSERT INTO INRTRI (CONTRI,CONINT,CUIINT,CUPPRG,DESINT,TOTINT,PRGINT,MOTIVO) VALUES (?,?,?,?,?,?,?,?)";
            this.sqlManager.update(inserimentoInterventoNonRiproposto, new Object[] { 
                    valoreCONTRI, valoreCONINT, valoreCUIINT, valoreCUPPRG, valoreDESINT, valoreTOTINT, valorePRGINT, noteappr });

        }
        
        commitTransazione = true;
        request.setAttribute("esito", "ok");
      }
    } catch (SQLException e) {
      logger.error( "Errore nel recupero dei dati intervento annullato o respinto da riportare.", e);
      aggiungiMessaggio(request, "errors.riportaInterventoAnnullato.recuperoDati");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "RiportaInterventi";
    } catch (GestoreException e) {
      logger.error("Errore nell\'inserimento dei dati Intervento nel db.", e);
      aggiungiMessaggio(request, "errors.riportaInterventoAnnulato.inserimentoDati");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "RiportaInterventi";
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
      logger.debug("riportaInterventiAnnullatiRespinti: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
}
