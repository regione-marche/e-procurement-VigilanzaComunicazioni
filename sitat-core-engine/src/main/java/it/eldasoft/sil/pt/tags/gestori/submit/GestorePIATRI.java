package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.springframework.transaction.TransactionStatus;

public class GestorePIATRI extends AbstractGestoreChiaveNumerica {

  public String[] getAltriCampiChiave() {
    return null;
  }

  public String getCampoNumericoChiave() {
    return "CONTRI";
  }

  public String getEntita() {
    return "PIATRI";
  }

  SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
  
  public void preInsert(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
	  ProfiloUtente profiloUtente = (ProfiloUtente) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
	  impl.addColumn("PIATRI.SYSCON", new Long(profiloUtente.getId()));

	Long tiprog = impl.getLong("PIATRI.TIPROG");
    Long anntri = impl.getLong("PIATRI.ANNTRI");
    String codiceId = "";
    String uffint = (String) this.getRequest().getSession().getAttribute(
  		  CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    if (tiprog != null) {
      codiceId = gestoreIdPiatri(this.getGeneManager().getSql(), uffint, tiprog, anntri);
      impl.setValue("PIATRI.ID", codiceId);
      /*if (impl.isColumn("PIATRI.NORMA")) {
    	if (tiprog.intValue() == 1) {
    	  String normaCfgLavori = ConfigManager.getValore("it.eldasoft.pt.norma.lavori");
   	      if (StringUtils.equals("2", normaCfgLavori)) {
   	    	impl.setValue("PIATRI.NORMA", new Long(2));
   	      } else {
   	    	impl.setValue("PIATRI.NORMA", new Long(1));
   	      }
        } else {
          String normaCfgFornitureSerivzi = ConfigManager.getValore("it.eldasoft.pt.norma.fornitureServizi");
          if (StringUtils.equals("2", normaCfgFornitureSerivzi)) {
     	    impl.setValue("PIATRI.NORMA", new Long(2));
   	      } else {
   	    	impl.setValue("PIATRI.NORMA", new Long(1));
   	      }
    	}
      }*/
    }
    Long programmiUfficio = new Long(-1);
    if (impl.isColumn("PIATRI.IDUFFICIO")) {
    	Long idUfficio = impl.getLong("PIATRI.IDUFFICIO");
    	if (idUfficio != null) {
    		try {
    			programmiUfficio = (Long)this.sqlManager.getObject("select count(*) from PIATRI where CENINT = ? and IDUFFICIO = ? and TIPROG = ? and ANNTRI = ?", new Object[] { uffint, idUfficio, tiprog, anntri });
    		} catch (Exception e) {
    			throw new GestoreException("Errore inaspettato nel determinare il numero di programmi per l'ufficio", null, e);
			}
    	}
    }
    //Se la descrizione breve non è valorizzata metto una descrizione di default
    //String bretri = impl.getString("PIATRI.BRETRI");
    //if (bretri == null || bretri.trim().equals("")) {
      if (tiprog.intValue() == 1) {
    	  impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
	    			"Programma triennale dei lavori " + anntri + "/" + (anntri+2)));
    	  //se è gestita la sezione "Adozione" e non quella di "Approvazione" il titolo sarà adozione
    	  if (!codiceId.endsWith("001") && !programmiUfficio.equals(new Long(0)) && impl.getData("PIATRI.DADOZI") != null && impl.getData("PIATRI.DAPTRI") == null ) {
    		  impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
    	    			"Adozione dello schema della programmazione dei lavori " + anntri + "/" + (anntri+2)));
    	  } else if (!codiceId.endsWith("001") && !programmiUfficio.equals(new Long(0)) && impl.getData("PIATRI.DAPTRI") != null){
    		  impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
    	    			"Programma triennale dei lavori " + anntri + "/" + (anntri+2) + " - Aggiornamento " + UtilityDate.convertiData(impl.getData("PIATRI.DAPTRI"), UtilityDate.FORMATO_GG_MM_AAAA)));
    	  }
    	
      } else if (tiprog.intValue() == 3) {
    	impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
      			"Programma " + anntri + "/" + (anntri+2)));
      } else {
    	impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
    			"Programma biennale degli acquisti " + anntri + "/" + (anntri+1)));
    	if (!codiceId.endsWith("001") && !programmiUfficio.equals(new Long(0)) && impl.getData("PIATRI.DAPTRI") != null){
  		  impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
  	    			"Programma biennale degli acquisti " + anntri + "/" + (anntri+1) + " - Aggiornamento " + UtilityDate.convertiData(impl.getData("PIATRI.DAPTRI"), UtilityDate.FORMATO_GG_MM_AAAA)));
  	  }
    }
    //}
    
    super.preInsert(status, impl);
    this.gestioneECOTRI(status, impl);
    GestorePIATRI.setFileAllegato(impl, this.getForm().getSelezioneFile(),
        this.getEntita());
  }

  public void postInsert(DataColumnContainer impl) throws GestoreException {
	// Controllo se per la stazione appaltante esiste gia' un programma
	// della stessa tipologia per lo stesso anno
	String codein = (String) this.getRequest().getSession().getAttribute(
		CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
	Long annoProgramma = impl.getLong("PIATRI.ANNTRI");
	try {
	  Long numero = (Long) this.sqlManager.getObject(
			  "select count(*) from PIATRI where ANNTRI=? and TIPROG=? and CENINT=?", 
			new Object[] { impl.getLong("PIATRI.ANNTRI"), impl.getLong("PIATRI.TIPROG"), codein } );

	  if (numero != null && numero.intValue() > 1) {
	    UtilityStruts.addMessage(this.getRequest(), "warning", 
	    	"warnings.piatri.insert.programmaEsistente", new Object[] { annoProgramma });
	  }
	} catch (SQLException e) {
	  throw new GestoreException("Errore inaspettato nel determinare l'unicita' del programma appena creato", null, e);
	}
  }

  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
	//String profilo = (String)this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
	/*if (impl.isColumn("PIATRI.BRETRI") && !geneManager.getProfili().checkProtec(profilo, "COLS", "MOD", "PIANI.PIATRI.BRETRI")) {
		Long anntri = impl.getLong("PIATRI.ANNTRI");
		impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO, "Programma " + anntri + "/" + (anntri+2)));
	}*/
	  String uffint = (String) this.getRequest().getSession().getAttribute(
	  		  CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
	  if (impl.isColumn("PIATRI.BRETRI") && impl.isColumn("PIATRI.TIPROG") && impl.isColumn("PIATRI.ANNTRI") && impl.isColumn("PIATRI.ID")) {
		  
		  Long contri = impl.getLong("PIATRI.CONTRI");
		  Long tiprog = impl.getLong("PIATRI.TIPROG");
		  Long anntri = impl.getLong("PIATRI.ANNTRI");
		  String codiceId = impl.getString("PIATRI.ID"); 
		  
		  Long programmiUfficio = new Long(-1);
		  if (impl.isColumn("PIATRI.IDUFFICIO")) {
		    	Long idUfficio = impl.getLong("PIATRI.IDUFFICIO");
		    	if (idUfficio != null) {
		    		try {
		    			programmiUfficio = (Long)this.sqlManager.getObject("select count(*) from PIATRI where CENINT = ? and IDUFFICIO = ? and TIPROG = ? and ANNTRI = ? and CONTRI<> ?", new Object[] { uffint, idUfficio, tiprog, anntri, contri });
		    		} catch (Exception e) {
		    			throw new GestoreException("Errore inaspettato nel determinare il numero di programmi per l'ufficio", null, e);
					}
		    	}
		  }
			if (tiprog.intValue() == 1) {
				impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
		    			"Programma triennale dei lavori " + anntri + "/" + (anntri+2)));
	    	  //se è gestita la sezione "Adozione" e non quella di "Approvazione" il titolo sarà adozione
	    	  if (!codiceId.endsWith("001") && programmiUfficio > 0 && impl.getData("PIATRI.DADOZI") != null && impl.getData("PIATRI.DAPTRI") == null ) {
	    		  impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
	    	    			"Adozione dello schema della programmazione dei lavori " + anntri + "/" + (anntri+2)));
	    	  } else if (!codiceId.endsWith("001") && programmiUfficio > 0 && impl.getData("PIATRI.DAPTRI") != null){
	    		  impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
	    	    			"Programma triennale dei lavori " + anntri + "/" + (anntri+2) + " - Aggiornamento " + UtilityDate.convertiData(impl.getData("PIATRI.DAPTRI"), UtilityDate.FORMATO_GG_MM_AAAA)));
	    	  }
		  	
		    } else if (tiprog.intValue() == 3) {
		    	impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
		      			"Programma " + anntri + "/" + (anntri+2)));
		    } else {
		    	impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
		    			"Programma biennale degli acquisti " + anntri + "/" + (anntri+1)));
		    	if (!codiceId.endsWith("001") && programmiUfficio > 0 && impl.getData("PIATRI.DAPTRI") != null){
		  		  impl.getColumn("PIATRI.BRETRI").setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO,
		  	    			"Programma biennale degli acquisti " + anntri + "/" + (anntri+1) + " - Aggiornamento " + UtilityDate.convertiData(impl.getData("PIATRI.DAPTRI"), UtilityDate.FORMATO_GG_MM_AAAA)));
		  	  }
		    }
	  }

	  this.gestioneECOTRI(status, impl);
	  GestorePIATRI.setFileAllegato(impl, this.getForm().getSelezioneFile(),
			  this.getEntita());
  }

  public void postUpdate(DataColumnContainer impl) throws GestoreException {
	  Long contri = impl.getLong("PIATRI.CONTRI");
	  PtManager m = (PtManager) UtilitySpring.getBean("ptManager",
	          getServletContext(), PtManager.class);
	  //se lo stato attuale del programma è pubblicato o esportato lo metto in modifica
	  m.updateStatoProgramma(contri, new Long(3), new Long(2), new Long(5));
	  
  }

  public void preDelete(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
    String sqlSelectCancellazione = "";

    // si ricava il valore della chiave primaria di PIATRI
    Long contri = impl.getColumn("PIATRI.CONTRI").getValue().longValue();

    // l'array listaEntita contiene le tabelle in cui verranno fatte le le
    // eliminazioni degli elementi collegati ad un programma
    String[] listaEntita = new String[] { "INTTRI", "IMMTRAI", "ECOTRI", "INRTRI", "OITRI", "RIS_CAPITOLO" };

    for (int k = 0; k < listaEntita.length; k++) {
      sqlSelectCancellazione = "DELETE FROM "
          + listaEntita[k]
          + " WHERE CONTRI = "
          + contri;
      try {
  	    this.sqlManager.execute(sqlSelectCancellazione);
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore durante la cancellazione di un Programma",
            "cancellazione.programma", e);
      }
    }
    
    //Aprile 2019
    //Cancellazione record di PTAPPROVAZIONI collegati al programma
    try {
      this.sqlManager.update("delete from PTAPPROVAZIONI where id_programma=? ", new Object[] { contri });
    } catch (Exception e) {
      throw new GestoreException(
          "Errore durante la cancellazione di un Programma",
          "cancellazione.programma", e);
    }
    
    
  }

  public void postDelete(DataColumnContainer impl) throws GestoreException {
  }

  public static String gestoreIdPiatri(SqlManager geneManager, String codein, Long tiprog, Long anntri)
      throws GestoreException {
    // calcolo del cfein
    String cfein = "";
    String sqlSelectCfein = "select cfein from uffint where codein ='"
        + codein
        + "'";
    //GeneManager geneCfein = this.getGeneManager();
    List<?> listaValoreCfein;
    try {
      listaValoreCfein = geneManager.getListVector(sqlSelectCfein,
          new Object[] {});
      cfein = SqlManager.getValueFromVectorParam(listaValoreCfein.get(0), 0).getStringValue();
    } catch (SQLException e) {
      throw new GestoreException("Errore durante il calcolo di cfein", "", e);
    }
    // calcolo dell'id
    String idParzialeInf = "";
    String idParzialeSup = "";
    String idParzialeLun = "";
    String tiprogStr = tiprog + "";
    if (tiprogStr.equals("1")) {
      idParzialeInf = "LP" + cfein + anntri + "000";
      idParzialeSup = "LP" + cfein + anntri + "999";
      idParzialeLun = "LP" + cfein + anntri + "___";
    } else if (tiprogStr.equals("2")) {
      idParzialeInf = "FS" + cfein + anntri + "000";
      idParzialeSup = "FS" + cfein + anntri + "999";
      idParzialeLun = "FS" + cfein + anntri + "___";
    } else if (tiprogStr.equals("3")) {
        idParzialeInf = "03" + cfein + anntri + "000";
        idParzialeSup = "03" + cfein + anntri + "999";
        idParzialeLun = "03" + cfein + anntri + "___";
    }

    String sqlSelectContatore = "select max (id) from piatri where id>'"
        + idParzialeInf
        + "' AND id<'"
        + idParzialeSup
        + "' and id like '"
        + idParzialeLun
        + "'";
    //GeneManager geneContatore = this.getGeneManager();
    List<?> listaValoreContatore;
    String idCalcolato = "";
    try {
      listaValoreContatore = geneManager.getListVector(
          sqlSelectContatore, new Object[] {});

      String valoreMaxIdStr = SqlManager.getValueFromVectorParam(
          listaValoreContatore.get(0), 0).getStringValue();
      if (valoreMaxIdStr != null
          && !valoreMaxIdStr.equals("")
          && valoreMaxIdStr.length() > 3) {
        valoreMaxIdStr = valoreMaxIdStr.substring(valoreMaxIdStr.length() - 3);
      } else {
        valoreMaxIdStr = "0";
      }
      long valoreMaxId = Long.parseLong(valoreMaxIdStr) + 1;
      char zero = '0';
      String idProgramma = UtilityStringhe.fillLeft(valoreMaxId + "", zero, 3);
      if (tiprogStr.equals("1")) {
        idCalcolato = "LP" + cfein + anntri + idProgramma;
      } else if (tiprogStr.equals("2")) {
        idCalcolato = "FS" + cfein + anntri + idProgramma;
      } else if (tiprogStr.equals("3")) {
        idCalcolato = "03" + cfein + anntri + idProgramma;
      }
      

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore durante il calcolo dell'id del programma",
          "calcolo.idProgramma", e);
    }
    return idCalcolato;

  }

  /**
   * Setta il campo FILE_ALLEGATO nel container in modo da svuotarlo o da
   * effettuare il salvataggio del file uploadato
   * 
   * @param dcc
   *        container dei dati inseriti nella form
   * @param formFile
   *        oggetto contenente il bytearray con il contenuto del file uploadato
   * @param entita
   *        nome dell'entita in cui inserire il BLOB
   * @throws GestoreException
   */
  public static void setFileAllegato(DataColumnContainer dcc,
      FormFile formFile, String entita) throws GestoreException {
    final double MAX_FILE_SIZE = Math.pow(2, 20) * 10;
    if (dcc.isColumn("FILEDAALLEGARE")
        && dcc.getString("FILEDAALLEGARE") != null
        && !dcc.getString("FILEDAALLEGARE").trim().equals("")) {
      ByteArrayOutputStream baos = null;
      try {
        if (dcc.getString("FILEDAALLEGARE").equals("canc")) {
          // se si deve effettuare la cancellazione, si crea una colonna con
          // valore null ma con un valore originario fittizio diverso da null,
          // in modo da forzare l'update a null
          dcc.addColumn(entita + ".FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO,
              null);
          ByteArrayOutputStream oldVal = new ByteArrayOutputStream();
          oldVal.write("FITTIZIO".getBytes());
          dcc.getColumn(entita + ".FILE_ALLEGATO").setObjectOriginalValue(
              oldVal);
        } else {
          // se il dato è stato aggiornato, allora si crea la colonna con lo
          // stream ricevuto mediante upload
          if (formFile.getFileSize() > MAX_FILE_SIZE) {
            throw new GestoreException("Il file selezionato ha una dimensione "
                + "superiore al massimo consentito (10 MB)", "upload.overflow",
                null);
          } else {
            baos = new ByteArrayOutputStream();
            baos.write(formFile.getFileData());
            dcc.addColumn(entita + ".FILE_ALLEGATO",
                JdbcParametro.TIPO_BINARIO, baos);
          }
        }
      } catch (FileNotFoundException e) {
        throw new GestoreException("File da salvare in db non trovato", "upload", e);
      } catch (IOException e) {
        throw new GestoreException(
            "Si è verificato un errore durante la scrittura del buffer per il salvataggio del file allegato su DB",
            "upload", e);
      }
    }
  }

  /**
   * Gestisce il salvataggio dei lavori in economia.
   * @param status status
   * @param datiForm datiForm
   * @throws GestoreException GestoreException
   */
  private void gestioneECOTRI(TransactionStatus status,
      DataColumnContainer datiForm) throws GestoreException {

    AbstractGestoreChiaveNumerica gestoreECOTRI = new DefaultGestoreEntitaChiaveNumerica(
        "ECOTRI", "CONECO", new String[] { "CONTRI" }, this.getRequest());
    this.gestisciAggiornamentiRecordSchedaMultipla(status, datiForm,
    		gestoreECOTRI, "ECOTRI",
        new DataColumn[] { datiForm.getColumn("PIATRI.CONTRI") }, null);

  }
  
}
