/* 
 *
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.transaction.TransactionStatus;

/**
 * Gestore non standard per gestire le PTAPPROVAZIONI
 *
 * @author Cristian Febas
 */
public class GestoreApprovazioni extends AbstractGestoreEntita {


    @Override
    public String getEntita() {
        return "PTAPPROVAZIONI";
    }

    public GestoreApprovazioni() {
        super(false);
      }

    @Override
    public void postDelete(DataColumnContainer datiForm)
            throws GestoreException {
    }


    @Override
    public void postInsert(DataColumnContainer datiForm)
            throws GestoreException {
    }


    @Override
    public void postUpdate(DataColumnContainer datiForm)
            throws GestoreException {
    }


    @Override
    public void preDelete(TransactionStatus status, DataColumnContainer datiForm)
            throws GestoreException {
    }

    @Override
    public void preInsert(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
    	String tipoFlussoFabbisogni = ConfigManager.getValore("it.eldasoft.pt.TipoFlussoFabbisogni");
      ////////////////////////
      String updateFABBISOGNI = "UPDATE FABBISOGNI SET STATO = ? WHERE CONINT = ?";
      String updateFABBISOGNI_QE = "UPDATE FABBISOGNI SET STATO = ?, QE_SL = null WHERE CONINT = ?";
      String updateFABBISOGNI_QE_1 = "UPDATE FABBISOGNI SET STATO = ?, QE_SL = ? WHERE CONINT = ?";
      //Long  conint = datiForm.getLong("PTAPPROVAZIONI.CONINT");
      Long conint = null;
      Timestamp  dataAppr = datiForm.getData("PTAPPROVAZIONI.DATAAPPR");
      Long  sysAppr = datiForm.getLong("PTAPPROVAZIONI.SYSAPPR");
      String  utenteAppr = datiForm.getString("PTAPPROVAZIONI.UTENTEAPPR");
      Long  faseAppr = datiForm.getLong("PTAPPROVAZIONI.FASEAPPR");
      Long  esitoAppr = datiForm.getLong("PTAPPROVAZIONI.ESITOAPPR");
      String  noteAppr = datiForm.getString("PTAPPROVAZIONI.NOTEAPPR");      
      String dichiarazione = "2";
      //se esito approvazione è approvato metto il campo dichiarazione a 1 = Si
      if (esitoAppr.equals(new Long(1))) {
    	  dichiarazione = "1";
      }
      //////////////////////////////////
      //Raccolta fabbisogni: modifiche per prototipo dicembre 2018
      //vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018)
      String funzionalita =  getRequest().getParameter("funzionalita");   
      String chiavi_concatenate =  getRequest().getParameter("fabbisogni");
      String[] chiavi = new String[] {};
      chiavi = chiavi_concatenate.split(";;"); 
      ////////////////////////////////// 
      //APRILE 2019
      String sqlSelectCancellazione = "";
      String contri_PTstr = null;
      Long contri_PT = null;
      String[] conint_PTstr = new String[] {};

      if ("RDP_RimuoviRichiediRevisione".equals(funzionalita)) {
        contri_PTstr =  getRequest().getParameter("contri_PT");
        contri_PTstr = UtilityStringhe.convertiNullInStringaVuota(contri_PTstr);
        contri_PT = new Long(contri_PTstr);
        String conint_PT_concatenate =  getRequest().getParameter("conint_PT");
        conint_PT_concatenate = UtilityStringhe.convertiNullInStringaVuota(conint_PT_concatenate);
        conint_PTstr = conint_PT_concatenate.split(";;"); 
      }
      ////////////////////////
      try {
          for (int i = 0; i < chiavi.length; i++) {
            //////////////////////////////////////////////////////////////////////////////////
            //APRILE 2019
            if ("RDP_RimuoviRichiediRevisione".equals(funzionalita)) {
              Long conint_PT = new Long(conint_PTstr[i]);
              // Cancellazione dei dati del CUP
              try {
                  String sqlDeleteCUPLOC = "delete from cuploc where id in " + "(select id from cupdati where contri=? and conint=?)";
                  this.sqlManager.update(sqlDeleteCUPLOC, new Object[] { contri_PT, conint_PT });

                  String sqlDeleteCUPDATI = "delete from cupdati where contri=? and conint=?";
                  this.sqlManager.update(sqlDeleteCUPDATI, new Object[] { contri_PT, conint_PT });

              } catch (SQLException e) {
                  throw new GestoreException("Errore durante la cancellazione dei dati di richiesta CUP associati all'intervento", "cancellazione.intervento", e);
              }
              //Cancellazione record di PTAPPROVAZIONI collegato all'intervento
              try {
                this.sqlManager.update("delete from PTAPPROVAZIONI where id_programma=? and id_interv_programma=?", new Object[] { contri_PT, conint_PT });
              } catch (Exception e) {
                throw new GestoreException("Errore durante la cancellazione di un'Approvazione", e.toString());
              }
              //cancellazione Immobili ed Intervento
              String[] listaEntita = new String[] { "IMMTRAI","INTTRI" };
              for (int k = 0; k < listaEntita.length; k++) {
                  sqlSelectCancellazione = "DELETE FROM " + listaEntita[k] + " WHERE CONTRI=? AND CONINT=?";
                  try {
                      this.sqlManager.update(sqlSelectCancellazione, new Object[] { contri_PT, conint_PT });
                  } catch (SQLException e) {
                      throw new GestoreException("Errore durante la cancellazione di un Intervento", "cancellazione.intervento", e);
                  }
              }
              //cancellazione RIS_CAPITOLO e aggiornamento importi
              try {
                  this.sqlManager.update("delete from RIS_CAPITOLO where CONTRI=? and CONINT=?", new Object[] { contri_PT, conint_PT });
  
                  PtManager m = (PtManager) UtilitySpring.getBean("ptManager", getServletContext(), PtManager.class);
                  m.aggiornaCostiPiatri(contri_PT);
                  // se lo stato attuale del programma è pubblicato o esportato lo
                  // metto in modifica
                  m.updateStatoProgramma(contri_PT, new Long(3), new Long(2), new Long(5));
  
              } catch (Exception e) {
                  throw new GestoreException("Errore durante il ricalcolo dei costi complessivi del Programma", "aggiornamento.ricalcoloCostiProgramma", e);
              }
            }
            //////////////////////////////////////////////////////////////////////////////////
            if ("RDP_RimuoviRichiediRevisione".equals(funzionalita) || "RDP_RevisioneInterventiAnnoPrecedente".equals(funzionalita)) {
              conint = new Long(chiavi[i]);   
            } else {
              DataColumnContainer fabbisogniItem = new DataColumnContainer(chiavi[i]);          
              conint = (fabbisogniItem.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();
            }
            //////////////////////////////////////////////////////////////////////////////////
            
            String selectMaxNumappr = "select max(numappr) from PTAPPROVAZIONI where conint = ?";
            Long maxNumappr = (Long) sqlManager.getObject(selectMaxNumappr, new Object[] { conint });
            if (maxNumappr == null) maxNumappr = new Long(0);
            maxNumappr ++;
            String insertPTAPPROVAZIONI = "insert into PTAPPROVAZIONI( CONINT, NUMAPPR, DATAAPPR, SYSAPPR, UTENTEAPPR, FASEAPPR, ESITOAPPR, NOTEAPPR, DICHIARAZIONE)" +
            " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            this.sqlManager.update( insertPTAPPROVAZIONI, new Object[] {conint, maxNumappr, dataAppr, sysAppr, utenteAppr, faseAppr, esitoAppr, noteAppr, dichiarazione});
            //////////////////////////////////
            //Raccolta fabbisogni: modifiche per prototipo dicembre 2018
            //vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018)
            if ("RAF_ApprovaRespingi".equals(funzionalita)) {
              if (new Long(1).equals(esitoAppr)) {
            	  if (tipoFlussoFabbisogni.equals("2")) {
            		  this.sqlManager.update(updateFABBISOGNI_QE_1, new Object[] {new Long(15), "1", conint });
            	  } else {
            		  this.sqlManager.update(updateFABBISOGNI_QE_1, new Object[] {new Long(4), "1", conint });
            	  }
              } else if (new Long(2).equals(esitoAppr)) {
                this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(31), conint });
              } else if (new Long(3).equals(esitoAppr)) {
                this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(11), conint });
              }
            } else if ("RDP_RichiediRevisioneRespingi".equals(funzionalita)) {
              if (new Long(2).equals(esitoAppr)) {
                this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(31), conint });
              } else if (new Long(3).equals(esitoAppr)) {
                this.sqlManager.update(updateFABBISOGNI_QE, new Object[] {new Long(11), conint });
              }
            } else if ("RDP_RimuoviRichiediRevisione".equals(funzionalita) || 
                       "RDP_RevisioneInterventiAnnoPrecedente".equals(funzionalita)) {
              this.sqlManager.update(updateFABBISOGNI_QE, new Object[] {new Long(11), conint });
            } else if ("RDS_ApprovaRichiediRevisione".equals(funzionalita)) {
                if (new Long(1).equals(esitoAppr)) {
                    this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(15), conint });
                } else if (new Long(3).equals(esitoAppr)) {
                    this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(11), conint });
                }
            } else if ("RDS_RichiediRevisione".equals(funzionalita)) {
            	this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(11), conint });
            } else if ("RDS_Annulla".equals(funzionalita)) {
            	this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(32), conint });
            } else if ("RDP_ImportaInterventiAnnullati".equals(funzionalita)) {
              
            }
          }
          //////////////////////////////////
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore nell'approvazione/respinta della proposta",
            "Approvazione/Respinta proposta ", e);
      }
      
    }


    @Override
    public void preUpdate(TransactionStatus status, DataColumnContainer datiForm )
            throws GestoreException {
    }






}