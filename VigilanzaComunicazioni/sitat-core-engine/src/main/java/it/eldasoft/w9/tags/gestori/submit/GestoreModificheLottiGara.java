package it.eldasoft.w9.tags.gestori.submit;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import org.apache.struts.upload.FormFile;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.TransactionStatus;

/**
 * Gestore non standard per la modifica massiva delle schede dei lotti della gara.
 * 
 * @author Mirco.Franzoni
 */
public class GestoreModificheLottiGara extends AbstractGestoreEntita {

  public GestoreModificheLottiGara() {
    super(false);
  }

  @Override
  public String getEntita() {
    return "W9GARA";
  }

  @Override
  public void postDelete(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void postInsert(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void postUpdate(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer datiForm)
  throws GestoreException {
  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer datiForm)
  throws GestoreException{

	  try {
		  Long codgara = new Long(UtilityStruts.getParametroString(this.getRequest(), "codgara"));
	      String tipoScheda = UtilityStruts.getParametroString(this.getRequest(), "tipoScheda");
	      //SCHEDA ANAGRAFICA LOTTO
	      if (tipoScheda.equals("anagrafica-lotto")) {
			  //Scelta contraente art 50
	    	  Long idSceltaContraente50 = datiForm.getLong("ID_SCELTA_CONTRAENTE_50");
	    	  if (idSceltaContraente50 != null) {
	    		  this.sqlManager.update("UPDATE W9LOTT SET ID_SCELTA_CONTRAENTE_50 = ? WHERE CODGARA = ?",
	        	          new Object[]{idSceltaContraente50, codgara });
	    	  }
	    	  //Criterio aggiudicazione
	    	  Long idModoGara = datiForm.getLong("ID_MODO_GARA");
	    	  if (idModoGara != null) {
	    		  this.sqlManager.update("UPDATE W9LOTT SET ID_MODO_GARA = ? WHERE CODGARA = ?",
	        	          new Object[]{idModoGara, codgara });
	    	  }
	    	  //URL piattaforma e-procurement
	    	  String urlEPROC = datiForm.getString("URL_EPROC");
	    	  if (urlEPROC != null) {
	    		  this.sqlManager.update("UPDATE W9LOTT SET URL_EPROC = ? WHERE CODGARA = ?",
	        	          new Object[]{urlEPROC, codgara });
	    	  }
	    	  //URL profilo del committente
	    	  String urlCommittente = datiForm.getString("URL_COMMITTENTE");
	    	  if (urlCommittente != null) {
	    		  this.sqlManager.update("UPDATE W9LOTT SET URL_COMMITTENTE = ? WHERE CODGARA = ?",
	        	          new Object[]{urlCommittente, codgara });
	    	  }
	    	  //Numero intervento CUI
	    	  String cuiint = datiForm.getString("CUIINT");
	    	  if (cuiint != null) {
	    		  this.sqlManager.update("UPDATE W9LOTT SET CUIINT = ? WHERE CODGARA = ?",
	        	          new Object[]{cuiint, codgara });
	    	  }
	    	  //Scelta contraente
	    	  Long idSceltaContraente = datiForm.getLong("ID_SCELTA_CONTRAENTE");
	    	  if (idSceltaContraente != null) {
	    		  this.sqlManager.update("UPDATE W9LOTT SET ID_SCELTA_CONTRAENTE = ? WHERE CODGARA = ?",
	        	          new Object[]{idSceltaContraente, codgara });
	    	  }
	    	  //Luogo ISTAT
	    	  String luogoISTAT = datiForm.getString("LUOGO_ISTAT");
	    	  if (luogoISTAT != null) {
	    		  this.sqlManager.update("UPDATE W9LOTT SET LUOGO_ISTAT = ? WHERE CODGARA = ?",
	        	          new Object[]{luogoISTAT, codgara });
	    	  }
	    	  //Tipo Prestazione
	    	  Long idTipoPrestazione = datiForm.getLong("ID_TIPO_PRESTAZIONE");
	    	  if (idTipoPrestazione != null) {
	    		  this.sqlManager.update("UPDATE W9LOTT SET ID_TIPO_PRESTAZIONE = ? WHERE CODGARA = ?",
	        	          new Object[]{idTipoPrestazione, codgara });
	    	  }
	    	  //Modalita' di acquisizione forniture / servizi
		      int i = 1;
		      boolean delete = false;
		      Long numAppalto = new Long(1);
		      while (datiForm.isColumn("W9APPAFORN.ID_APPALTO_" + i)) {
		        Long idAppalto = datiForm.getLong("W9APPAFORN.ID_APPALTO_" + i);
		        if (idAppalto != null) {
		        	//Se il valore e' SI
		        	if (idAppalto.longValue() == 1) {
		        		Long valueAppalto = datiForm.getLong("W9APPAFORN.VALORE_ID_APPALTO_" + i); 
			        	//cancello tutti i record in W9APPAFORN se non li ho gia' cancellati
		        		if (!delete) {
		        			this.sqlManager.update("DELETE FROM W9APPAFORN WHERE CODGARA = ? ", new Object[]{codgara});
		        			delete = true;
		        		}
		        		//Per tutti i lotti della gara inserisco un record con l'ID APPALTO specificato
		        		this.sqlManager.update("INSERT INTO W9APPAFORN(CODGARA, CODLOTT, NUM_APPAF, ID_APPALTO) SELECT CODGARA, CODLOTT, " + numAppalto + ", " + valueAppalto + " FROM W9LOTT WHERE CODGARA = ? ",
			        	          new Object[]{codgara});
		        		numAppalto ++;
		        	}
		        }
		        i++;
		      }
	    	  //Modalita' tipologia lavoro
		      i = 1;
		      delete = false;
		      numAppalto = new Long(1);
		      while (datiForm.isColumn("W9APPALAV.ID_APPALTO_" + i)) {
		        Long idAppalto = datiForm.getLong("W9APPALAV.ID_APPALTO_" + i);
		        if (idAppalto != null) {
		        	//Se il valore e' SI
		        	if (idAppalto.longValue() == 1) {
		        		Long valueAppalto = datiForm.getLong("W9APPALAV.VALORE_ID_APPALTO_" + i); 
			        	//cancello tutti i record in W9APPLALAV se non li ho gia' cancellati
		        		if (!delete) {
		        			this.sqlManager.update("DELETE FROM W9APPALAV WHERE CODGARA = ? ", new Object[]{codgara});
		        			delete = true;
		        		}
		        		//Per tutti i lotti della gara inserisco un record con l'ID APPALTO specificato
		        		this.sqlManager.update("INSERT INTO W9APPALAV(CODGARA, CODLOTT, NUM_APPAL, ID_APPALTO) SELECT CODGARA, CODLOTT, " + numAppalto + ", " + valueAppalto + " FROM W9LOTT WHERE CODGARA = ? ",
			        	          new Object[]{codgara});
		        		numAppalto ++;
		        	}
		        }
		        i++;
		      }
		      //Condizioni che giustificano il ricorso alla procedura negoziata
		      i = 1;
		      delete = false;
		      Long numCondizione = new Long(1);
		      if (idSceltaContraente != null && idSceltaContraente == 4) {
		    	  while (datiForm.isColumn("W9COND.ID_CONDIZIONE_" + i)) {
		    		  Long idCondizione = datiForm.getLong("W9COND.ID_CONDIZIONE_" + i);
				      if (idCondizione != null) {
				        //Se il valore e' SI
				       	if (idCondizione.longValue() == 1) {
				        	Long valueCondizione = datiForm.getLong("W9COND.VALORE_ID_CONDIZIONE_" + i); 
					       	//cancello tutti i record in W9COND se non li ho gia' cancellati
				        	if (!delete) {
				        		this.sqlManager.update("DELETE FROM W9COND WHERE CODGARA = ? ", new Object[]{codgara});
				        		delete = true;
				        	}
				        	//Per tutti i lotti della gara inserisco un record con l'ID CONDIZIONE specificato
				        	this.sqlManager.update("INSERT INTO W9COND(CODGARA, CODLOTT, NUM_COND, ID_CONDIZIONE) SELECT CODGARA, CODLOTT, " + numCondizione + ", " + valueCondizione + " FROM W9LOTT WHERE CODGARA = ? ",
					       	          new Object[]{codgara});
				        	numCondizione ++;
				       	}
				      }
				      i++;
				 }
		      }
		      
	      }
	      //SCHEDA FASE COMUNICAZIONE ESITO
	      else if (tipoScheda.equals("comunicazione-esito")) {
	    	  //Esito Procedura
	    	  Long esitoProcedura = datiForm.getLong("ESITO_PROCEDURA");
	    	  if (esitoProcedura != null) {
	    		  this.sqlManager.update("UPDATE W9ESITO SET ESITO_PROCEDURA = ? WHERE CODGARA = ?",
	        	          new Object[]{esitoProcedura, codgara });
	    	  }
	    	  //Data verbale aggiudicazione
	    	  Timestamp data = datiForm.getData("DATA_VERB_AGGIUDICAZIONE");
	    	  if (data != null) {
	    		  this.sqlManager.update("UPDATE W9ESITO SET DATA_VERB_AGGIUDICAZIONE = ? WHERE CODGARA = ?",
	        	          new Object[]{data, codgara });
	    	  }
	    	  //File allegato
	    	  FormFile esitoFile = this.getForm().getSelezioneFile();
	    	  if (esitoFile != null) {
	    		  final double MAX_FILE_SIZE = Math.pow(2, 20) * 10;
	    		  // se il dato e' stato aggiornato, allora si crea la colonna con lo
	              // stream ricevuto mediante upload
	              if (esitoFile.getFileSize() > MAX_FILE_SIZE) {
	                throw new GestoreException("Il file selezionato ha una dimensione "
	                    + "superiore al massimo consentito (10 MB)", "upload.overflow",
	                    null);
	              } else {
	            	  ByteArrayOutputStream baos = null;
	            	  LobHandler lobHandler = new DefaultLobHandler(); // reusable object
	            	  baos = new ByteArrayOutputStream();
	            	  baos.write(esitoFile.getFileData());
	            	  this.sqlManager.update("UPDATE W9ESITO SET FILE_ALLEGATO = ? WHERE CODGARA = ?",
		        	          new Object[]{new SqlLobValue(baos.toByteArray(), lobHandler), codgara });
	              }
	    	  }
	      }
	      //SCHEDA FASE AGGIUDICAZIONE
	      else if (tipoScheda.equals("aggiudicazione")) {
	    	  //Data di scadenza per la presentazione delle offerte
	    	  Timestamp data = datiForm.getData("DATA_SCADENZA_PRES_OFFERTA");
	    	  if (data != null) {
	    		  this.sqlManager.update("UPDATE W9APPA SET DATA_SCADENZA_PRES_OFFERTA = ? WHERE CODGARA = ?",
	        	          new Object[]{data, codgara });
	    	  }
	    	  //L'appalto rientra in un accordo quadro?
	    	  String flagAccordoQuadro = datiForm.getString("FLAG_ACCORDO_QUADRO");
	    	  if (flagAccordoQuadro != null) {
	    		  this.sqlManager.update("UPDATE W9APPA SET FLAG_ACCORDO_QUADRO = ? WHERE CODGARA = ?",
	        	          new Object[]{flagAccordoQuadro, codgara });
	    	  }
	    	  //e' stata utilizzata la procedura accelerata?
	    	  String flagProceduraAccellerata = datiForm.getString("PROCEDURA_ACC");
	    	  if (flagProceduraAccellerata != null) {
	    		  this.sqlManager.update("UPDATE W9APPA SET PROCEDURA_ACC = ? WHERE CODGARA = ?",
	        	          new Object[]{flagProceduraAccellerata, codgara });
	    	  }
	    	  //e' stata effettuata la preinformazione?
	    	  String flagPreinformazione = datiForm.getString("PREINFORMAZIONE");
	    	  if (flagPreinformazione != null) {
	    		  this.sqlManager.update("UPDATE W9APPA SET PREINFORMAZIONE = ? WHERE CODGARA = ?",
	        	          new Object[]{flagPreinformazione, codgara });
	    	  }
	    	  //e' stato utilizzato il termine ridotto?
	    	  String flagTermineRidotto = datiForm.getString("TERMINE_RIDOTTO");
	    	  if (flagTermineRidotto != null) {
	    		  this.sqlManager.update("UPDATE W9APPA SET TERMINE_RIDOTTO = ? WHERE CODGARA = ?",
	        	          new Object[]{flagTermineRidotto, codgara });
	    	  }
	      }
	      //SCHEDA FASE INIZIALE
	      else if (tipoScheda.equals("fase-iniziale")) {
	    	  //Gazzetta Ufficiale Comunita' Europea
	    	  Timestamp data = datiForm.getData("DATA_GUCE");
	    	  if (data != null) {
	    		  this.sqlManager.update("UPDATE W9PUES SET DATA_GUCE = ? WHERE CODGARA = ?",
	        	          new Object[]{data, codgara });
	    	  }
	    	  //Gazzetta ufficiale Repubblica Italiana
	    	  data = datiForm.getData("DATA_GURI");
	    	  if (data != null) {
	    		  this.sqlManager.update("UPDATE W9PUES SET DATA_GURI = ? WHERE CODGARA = ?",
	        	          new Object[]{data, codgara });
	    	  }
	    	  //Albo pretorio del comuni ove si eseguono i lavori
	    	  data = datiForm.getData("DATA_ALBO");
	    	  if (data != null) {
	    		  this.sqlManager.update("UPDATE W9PUES SET DATA_ALBO = ? WHERE CODGARA = ?",
	        	          new Object[]{data, codgara });
	    	  }
	    	  //Numero quotidiani nazionali
	    	  Long quotidianiNazionali = datiForm.getLong("QUOTIDIANI_NAZ");
	    	  if (quotidianiNazionali != null) {
	    		  this.sqlManager.update("UPDATE W9PUES SET QUOTIDIANI_NAZ = ? WHERE CODGARA = ?",
	        	          new Object[]{quotidianiNazionali, codgara });
	    	  }
	    	  //Numero quotidiani locali
	    	  Long quotidianiLocali = datiForm.getLong("QUOTIDIANI_REG");
	    	  if (quotidianiLocali != null) {
	    		  this.sqlManager.update("UPDATE W9PUES SET QUOTIDIANI_REG = ? WHERE CODGARA = ?",
	        	          new Object[]{quotidianiLocali, codgara });
	    	  }
	    	  //Profilo del committente
	    	  String profiloCommittente = datiForm.getString("PROFILO_COMMITTENTE");
	    	  if (profiloCommittente != null) {
	    		  this.sqlManager.update("UPDATE W9PUES SET PROFILO_COMMITTENTE = ? WHERE CODGARA = ?",
	        	          new Object[]{profiloCommittente, codgara });
	    	  }
	    	  //Sito Informatico Ministero Infrastrutture
	    	  String sitoMinistero = datiForm.getString("SITO_MINISTERO_INF_TRASP");
	    	  if (sitoMinistero != null) {
	    		  this.sqlManager.update("UPDATE W9PUES SET SITO_MINISTERO_INF_TRASP = ? WHERE CODGARA = ?",
	        	          new Object[]{sitoMinistero, codgara });
	    	  }
	    	  //Sito Informatico Osservatorio Contratti Pubblici
	    	  String sitoOCP = datiForm.getString("SITO_OSSERVATORIO_CP");
	    	  if (sitoOCP != null) {
	    		  this.sqlManager.update("UPDATE W9PUES SET SITO_OSSERVATORIO_CP = ? WHERE CODGARA = ?",
	        	          new Object[]{sitoOCP, codgara });
	    	  }
	    	  
	      }
	      // Esito positivo dell'intera operazione: generazione del pdf e update del campo PIATRI.FILE_ALLEGATO
	      this.getRequest().setAttribute("RISULTATO", "OK");
	      //this.setStopProcess(true);
	  } catch (Exception ex) {
		  this.getRequest().setAttribute("RISULTATO", "KO");
		  throw new GestoreException(
		            "Errore durante l'aggiornamento massivo delle schede dei lotti", null, ex);
		  //this.setStopProcess(false);
	  } 
  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm)
  throws GestoreException {
  }

}
