/*
 * Created on marzo 2016
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.bl.system.MailManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.struts.UploadFileForm;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.utils.MailUtils;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.mail.IMailSender;
import it.eldasoft.utils.mail.MailSenderException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.Log4JLogChute;
import org.springframework.transaction.TransactionStatus;

/**
 * Gestore per la Richiesta delle credenziali per i servizi di cooperazione applicativa
 *
 *
 */
public class GestoreFormRichiestaCredenziali extends AbstractGestoreChiaveNumerica {

  private static final String REGISTRAZIONE_UTENTE_CAPTCHA_ERRORE              = "registrazione.utente.captcha.errore";

  private static final String MSG_ERROR_ENTE_INESISTENTE = "gestoreFormRegistrazione.enteInesistente";

  private static final String MSG_ERROR_APPDISP_NOPRES = "gestoreFormRegistrazione.appDispNoPres";


  /**
   * Logger per tracciare messaggio di debug
   */
  static Logger               logger                    = Logger.getLogger(GestoreFormRichiestaCredenziali.class);

  public String[] getAltriCampiChiave() {
	    return null;
	  }

	  public String getCampoNumericoChiave() {
	    return "ID_RICHIESTA";
	  }

	  
	  public String getEntita() {
		    return "W_COOPUSR";
		  }

  public void preDelete(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

  }


  public void postDelete(DataColumnContainer impl) throws GestoreException {

  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {
	  
	  String codein = impl.getString("UFFINT.CODEIN");
	  if (codein == null || codein.equals("")) {
		  throw new GestoreException("Errore: Non è stato selezionato l'ente!", MSG_ERROR_ENTE_INESISTENTE, null);
	  }
	  impl.addColumn("W_COOPUSR.CODEIN", JdbcParametro.TIPO_TESTO, codein);
	  impl.addColumn("W_COOPUSR.DATA_CREAZIONE", JdbcParametro.TIPO_DATA,
	          new Timestamp(new Date().getTime()));
	  
	  String profiliDisponibili = ConfigManager.getValore(CostantiGenerali.PROP_REG_PROFILI_DISPONIBILI);
      String[] arrayProfiliDisponibili = null;
      String[] arrayApplicativiScelti = new String[1];
      profiliDisponibili = UtilityStringhe.convertiNullInStringaVuota(profiliDisponibili);
      if(!"".equals(profiliDisponibili)){
        arrayProfiliDisponibili = profiliDisponibili.split(";");
        if(arrayProfiliDisponibili.length==1){
          //Se ho un solo profilo in configurazione lo setto automaticamente
          String codProfiloUnico = arrayProfiliDisponibili[0];
          arrayApplicativiScelti[0] = codProfiloUnico;
        }else{
          //Applicativi Disponibili
          arrayApplicativiScelti = this.getRequest().getParameterValues("applicativi");
          if (arrayApplicativiScelti != null && arrayApplicativiScelti.length > 0) {
            ;
          }else{
            throw new GestoreException("Errore: Occorre scegliere almeno uno fra gli applicativi disponibili!", MSG_ERROR_APPDISP_NOPRES, null);
          }
        }
      }
      String servizi = "";
      for(int i = 0; i < arrayApplicativiScelti.length; i++) {
    	  servizi += arrayApplicativiScelti[i];
    	  if (i + 1 < arrayApplicativiScelti.length) {
    		  servizi += ";";
    	  }
      }
      impl.addColumn("W_COOPUSR.SERVIZI", JdbcParametro.TIPO_TESTO, servizi);
      
      Random rand = new Random();
      
      String clientID = impl.getString("UFFINT.CFEIN") + StringUtils.leftPad(rand.nextInt(999999999) + "", 9, '0');
      
      try {
    	  Long occorrenze = (Long) this.sqlManager.getObject("select count(*) from W_COOPUSR where CLIENT_ID = ?", new Object[] { clientID });
          while (!occorrenze.equals(new Long(0))) {
        	  clientID = impl.getString("UFFINT.CFEIN") + StringUtils.leftPad(rand.nextInt(999999999) + "", 9, '0');
        	  occorrenze = (Long) this.sqlManager.getObject("select count(*) from W_COOPUSR where CLIENT_ID = ?", new Object[] { clientID });
          }
      } catch(Exception ex) {
    	  logger.error("Errore durante il controllo di generazione del CLIENT_ID", ex);
      }
      impl.addColumn("W_COOPUSR.CLIENT_ID", JdbcParametro.TIPO_TESTO, clientID);
      impl.addColumn("W_COOPUSR.CLIENT_KEY", JdbcParametro.TIPO_TESTO, UtilitySITAT.generaPasswordRobusta());

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      String fname = "";
      boolean eseguireControlloDimTotale=false;
      double dimMaxTotaleFilesByte = 0;
      String dimMaxTotaleFiles = null;
      
      UploadFileForm mf = getForm();
      FormFile ff = mf.getSelezioneFile();
      if (ff!= null){
        fname = ff.getFileName();
        //if(fname.length()!=0 && ff.getFileSize()>0 && ff.getFileSize() <= dimMaxTotaleFilesByte) {
        if(fname.length()!=0 && ff.getFileSize()>0) {
          try {
            baos.write(ff.getFileData());
            baos.close();
          } catch (FileNotFoundException e) {
             throw new GestoreException("File da caricare non trovato", "upload", e);
          } catch (IOException e) {
            throw new GestoreException(
                "Si è verificato un errore durante la scrittura del buffer nel del file allegato ",
                "upload", e);
          }
        }else if(fname.length()!=0 && ff.getFileSize()==0){
          throw new GestoreException("Il file selezionato risulta vuoto. Per continuare specificare un altro file",
              "upload.fileVuoto", null, null);
        }else if(eseguireControlloDimTotale && ff.getFileSize() > dimMaxTotaleFilesByte){
          throw new GestoreException("La dimensione totale dei file da allegare supera il limite consentito "
              + "di " + dimMaxTotaleFiles + " MB", "upload.overflow", new String[]{dimMaxTotaleFiles}, null);
        }
      }

      //impl.addColumn("W_COOPUSR.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, baos);
      
      // Controllo preliminare mediante CAPTCHA
      if (!this.rpHash(this.getRequest().getParameter("realperson")).equals(this.getRequest().getParameter("realpersonHash"))) {
        throw new GestoreException("Il codice di controllo digitato non corrisponde a quello visualizzato",
            REGISTRAZIONE_UTENTE_CAPTCHA_ERRORE, null);
      }
      
	  super.preInsert(status, impl);
  }

  public void postInsert(DataColumnContainer impl) throws GestoreException {
	  Long idRichiesta = impl.getLong("W_COOPUSR.ID_RICHIESTA");
	  
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
      
      UploadFileForm mf = getForm();
      FormFile ff = mf.getSelezioneFile();
      String fname = ff.getFileName();
      try {
            baos.write(ff.getFileData());
            baos.close();
      } catch (FileNotFoundException e) {
             throw new GestoreException("File da caricare non trovato", "upload", e);
      } catch (IOException e) {
            throw new GestoreException(
                "Si è verificato un errore durante la scrittura del buffer nel del file allegato ",
                "upload", e);
      }
      //Recupero dal codein il nome dell'ente
      String ente = "";
      try {
    	  ente = (String) this.sqlManager.getObject("select NOMEIN from UFFINT where CODEIN = ?", new Object[] { impl.getString("UFFINT.CODEIN") });
      } catch(Exception ex) {
    	  logger.error("Errore durante l'estrazione del nominativo dell'ente", ex);
      }
     
      //Salvataggio del documento in W_DOCDIG
      boolean result = false;
	  TransactionStatus status = null;
      try {
    	  long newIDDOCDIG=1;
          Long maxIDDOCDIG = (Long) this.geneManager.getSql().getObject("select max(IDDOCDIG) from W_DOCDIG where IDPRG= ?", new Object[] {"W9"} );
          if (maxIDDOCDIG != null && maxIDDOCDIG.longValue()>0)
              newIDDOCDIG = maxIDDOCDIG.longValue() + 1;
          
          String nomeFile="";
          int len = impl.getString("FILEDAALLEGARE").length();
          int posizioneBarra = impl.getString("FILEDAALLEGARE").lastIndexOf("\\");
          nomeFile=impl.getString("FILEDAALLEGARE").substring(posizioneBarra+1,len).toUpperCase();

          status = this.sqlManager.startTransaction();
		  DataColumn[] arrayDataW_DOCDIG = new DataColumn[6]; 
		  
		  arrayDataW_DOCDIG[0] = new DataColumn("W_DOCDIG.IDPRG", new JdbcParametro(JdbcParametro.TIPO_TESTO, "W9"));
		  arrayDataW_DOCDIG[1] = new DataColumn("W_DOCDIG.IDDOCDIG", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(newIDDOCDIG)));
		  arrayDataW_DOCDIG[2] = new DataColumn("W_DOCDIG.DIGENT", new JdbcParametro(JdbcParametro.TIPO_TESTO, "W_COOPUSR"));
		  arrayDataW_DOCDIG[3] = new DataColumn("W_DOCDIG.DIGKEY1", new JdbcParametro(JdbcParametro.TIPO_TESTO, idRichiesta));
		  arrayDataW_DOCDIG[4] = new DataColumn("W_DOCDIG.DIGNOMDOC", new JdbcParametro(JdbcParametro.TIPO_TESTO, nomeFile));
		  arrayDataW_DOCDIG[5] = new DataColumn("W_DOCDIG.DIGOGG", new JdbcParametro(JdbcParametro.TIPO_BINARIO, baos));
		  
		  DataColumnContainer dataContW_DOCDIG = new DataColumnContainer(arrayDataW_DOCDIG);

		  dataContW_DOCDIG.getColumn("W_DOCDIG.IDPRG").setChiave(true);
		  dataContW_DOCDIG.getColumn("W_DOCDIG.IDDOCDIG").setChiave(true);

		  AbstractGestoreChiaveNumerica gestoreW_DOCDIG = new DefaultGestoreEntitaChiaveNumerica("W_DOCDIG", "IDDOCDIG",
		            new String[] { "IDPRG" }, this.getRequest());
		  gestoreW_DOCDIG.inserisci(status, dataContW_DOCDIG);
		  result = true;
      } catch(Exception ex) {
    	  logger.error("Errore durante il salvataggio del Modulo Richiesta Credenzaili firmato digitalmente", ex);
      } finally {
		  if (status != null) {
	          try {
	        	  if (result) {
	        		  this.sqlManager.commitTransaction(status);
	        	  } else {
	        		  this.sqlManager.rollbackTransaction(status);
	        	  }
	          } catch (SQLException e) {
	        	  logger.error("Errore durante la chiusura della transazione", e);
	          }
	      }
	  }
      
      //INVIO MAIL
      inviaMail(idRichiesta.toString(), impl.getString("W_COOPUSR.DIRIGENTE"), impl.getString("W_COOPUSR.MAIL_DIRIGENTE"), impl.getString("W_COOPUSR.TECNICO"), impl.getString("W_COOPUSR.MAIL_TECNICO"), ente, impl.getString("UFFINT.CFEIN"), fname, baos);
      
      //Al termine dell'esecuzione viene fatto il forward su redirect.jsp, 
      //che a sua volta reindirizzerà alla action speciaficata nel attributo "url"
      this.getRequest().setAttribute("url", this.getRequest().getContextPath() + "/confermaRichiestaCredenziali.jsp?idRichiesta=" + idRichiesta);
  }

  public void preUpdate(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
	  int i = 1;
	  String serviziSelezionati = "";
	  while (impl.isColumn("SERVIZIO_" + i)) {
		  if(impl.getString("SERVIZIO_" + i) != null && impl.getString("SERVIZIO_" + i).equals("1")) {
			  serviziSelezionati += impl.getString("VALORE_SERVIZIO_" + i) + ";";
		  }
		  i++;
	  }
	  if (serviziSelezionati.length() > 0) {
		  serviziSelezionati = serviziSelezionati.substring(0, serviziSelezionati.length() -1);
	  }
	  impl.setValue("W_COOPUSR.SERVIZI", serviziSelezionati);
  }

  public void postUpdate(DataColumnContainer impl) throws GestoreException {

  }

  /**
   * Invio mail di registrazione per soggetto che si registra.
   */
  private void inviaMailRichiesta(MailManager mailManager, IMailSender mailSender, String idRichiesta, String dirigente, String mailDirigente, String tecnico, String mailTecnico) {

    String nomeMittente = ConfigManager.getValore(CostantiGenerali.PROP_TITOLO_APPLICATIVO);
    String testoMail = null;
    String oggettoMail = null;

    try {
      oggettoMail = resBundleGenerale.getString("richiesta-credenziali.mail.automatica.oggetto");
      oggettoMail = UtilityStringhe.replaceParametriMessageBundle(oggettoMail, new String[] { nomeMittente });
      //invio mail al dirigente
      testoMail = this.getTestoRichiestaCredenziali(1, dirigente, idRichiesta, nomeMittente, "", "");
      mailSender.send(mailDirigente, oggettoMail, testoMail);
      //invio mail al tecnico
      testoMail = this.getTestoRichiestaCredenziali(1, tecnico, idRichiesta, nomeMittente, "", "");
      mailSender.send(mailTecnico, oggettoMail, testoMail);
    } catch (Exception e) {
      String logMessageKey = "warnings.registrazione.mancatoInvioMailUtente";
      String logMessageError = resBundleGenerale.getString(logMessageKey);
      if (e instanceof MailSenderException) {
        MailSenderException ms = (MailSenderException)e;
        logMessageKey = ms.getChiaveResourceBundle();
        logMessageError = resBundleGenerale.getString(logMessageKey);
        for (int i = 0; ms.getParametri() != null && i < ms.getParametri().length; i++)
          logMessageError = logMessageError.replaceAll(UtilityStringhe.getPatternParametroMessageBundle(i), (String) ms.getParametri()[i]);
        logger.error(logMessageError, e);
        logMessageKey = "warnings.registrazione.mancatoInvioMailUtente";
        logMessageError = resBundleGenerale.getString(logMessageKey);
      } else {
        logger.error(logMessageError, e);
      }
      UtilityStruts.addMessage(this.getRequest(), "warning", logMessageKey, null);
      logger.warn(logMessageError, e);
    }

  }
  
  /**
   * Invio mail di registrazione.
   *
   * @param account
   * @throws GestoreException
   */
  private void inviaMail(String idRichiesta, String dirigente, String mailDirigente, String tecnico, String mailTecnico, String ente, String cfente,String nomeFile, ByteArrayOutputStream stream) {
       try {
        MailManager mailManager = (MailManager) UtilitySpring.getBean("mailManager", this.getServletContext(), MailManager.class);
        IMailSender mailSender = MailUtils.getInstance(mailManager, ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE),CostantiGenerali.PROP_CONFIGURAZIONE_MAIL_STANDARD);
        this.inviaMailRichiesta(mailManager, mailSender, idRichiesta, dirigente, mailDirigente, tecnico, mailTecnico);
        //INVIO NOTIFICA AMMINISTRATORE
        this.inviaMailNotifica(mailManager, mailSender, idRichiesta, dirigente, ente, cfente, nomeFile, stream);
      } catch (MailSenderException ms) {

        String logMessageKey = ms.getChiaveResourceBundle();
        String logMessageError = resBundleGenerale.getString(logMessageKey);
        for (int i = 0; ms.getParametri() != null && i < ms.getParametri().length; i++){
          logMessageError = logMessageError.replaceAll(UtilityStringhe.getPatternParametroMessageBundle(i), (String) ms.getParametri()[i]);
        }
        logger.error(logMessageError, ms);
      }
  }

  /**
   * Invio mail di registrazione per l'Amministratore.
   */
  private void inviaMailNotifica(MailManager mailManager, IMailSender mailSender, String idRichiesta, String dirigente, String ente, String cfente, String nomeFile, ByteArrayOutputStream stream) {

    String nomeMittente = ConfigManager.getValore(CostantiGenerali.PROP_TITOLO_APPLICATIVO);
    String mailAmministratore = ConfigManager.getValore("it.eldasoft.pubblicazioni.ws.mailAmministratore");

    try {

      String testoMail = null;
      String oggettoMail = null;

      if (mailAmministratore != null && !"".equals(mailAmministratore.trim())) {
    	  oggettoMail = resBundleGenerale.getString("richiesta-credenziali.mail.automatica.oggetto");
          oggettoMail = UtilityStringhe.replaceParametriMessageBundle(oggettoMail, new String[] { nomeMittente });
          testoMail = this.getTestoRichiestaCredenziali(2, dirigente, idRichiesta, nomeMittente, ente, cfente);
          String[] destinatari = mailAmministratore.split(";");
          String destinatario = destinatari[0];
          String[] destinatariCC = null;
          if (destinatari.length > 1) {
        	  destinatariCC = new String[destinatari.length - 1];
        	  for(int i = 1; i < destinatari.length; i++) {
        		  destinatariCC[i - 1] = destinatari[i];
        	  }
          }
        nomeFile = UtilityStringhe.convertiNullInStringaVuota(nomeFile);
        
        if(!"".equals(nomeFile)){
          mailSender.send(new String[] {destinatario}, destinatariCC, null, oggettoMail, testoMail,
              new String[] {nomeFile}, new ByteArrayOutputStream[] {stream });
        }else{
          mailSender.send(mailAmministratore, oggettoMail, testoMail);
        }
      }

    } catch (Exception e) {
      String logMessageKey = "warnings.registrazione.mancatoInvioMailAdmin";
      String logMessageError = resBundleGenerale.getString(logMessageKey);
      if (e instanceof MailSenderException) {
        MailSenderException ms = (MailSenderException)e;
        logMessageKey = ms.getChiaveResourceBundle();
        logMessageError = resBundleGenerale.getString(logMessageKey);
        for (int i = 0; ms.getParametri() != null && i < ms.getParametri().length; i++)
          logMessageError = logMessageError.replaceAll(UtilityStringhe.getPatternParametroMessageBundle(i), (String) ms.getParametri()[i]);
        logger.error(logMessageError, e);
        logMessageKey = "warnings.registrazione.mancatoInvioMailAdmin";
        logMessageError = resBundleGenerale.getString(logMessageKey);
      } else {
        logger.error(logMessageError, e);
      }
      UtilityStruts.addMessage(this.getRequest(), "warning", logMessageKey, null);
      logger.warn(logMessageError, e);
    }

  }
  
  private String getTestoRichiestaCredenziali(int tipo, String nome, String id_richiesta, String nomeMittente, String ente, String cfente) throws SQLException  {

	    VelocityEngine velocityEngine = new VelocityEngine();
	    velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "file");
	    velocityEngine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
	    velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "false");
	    velocityEngine.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
	    //velocityEngine.setProperty("runtime.log.logsystem.log4j.category", "velocity");
	    velocityEngine.setProperty(Log4JLogChute.RUNTIME_LOG_LOG4J_LOGGER, logger.getName());

	    String velocityModelPath = this.getServletContext().getRealPath("/velocitymodel/");
	    velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, velocityModelPath);
	    velocityEngine.init();


	    VelocityContext velocityContext = new VelocityContext();
	    StringWriter writerUtente = new StringWriter();

	    String velocityModel = null;

	    velocityContext.put("NOME", nome);
	    velocityContext.put("ID_RICHIESTA", id_richiesta);
        velocityContext.put("NOMEMITT", nomeMittente);
        
	    switch (tipo) {
		case 1:
			velocityModel = "richiesta-credenziali-mail-ricevuta.txt";
			break;
		case 2:
			velocityModel = "richiesta-credenziali-mail-notifica.txt";
			velocityContext.put("ENTE", ente);
		    velocityContext.put("CFENTE", cfente);
			break;
		}

	    Template templateUtente = velocityEngine.getTemplate(velocityModel);

	    templateUtente.merge(velocityContext, writerUtente);
	    String testoMail = writerUtente.toString();
	    return testoMail;
	  }

  /**
   * Gestione della HashMap per il controllo mediante CAPTCHA.
   *
   * @param value
   * @return
   */
  private String rpHash(String value) {
    int hash = 5381;
    value = value.toUpperCase();
    for (int i = 0; i < value.length(); i++) {
      hash = ((hash << 5) + hash) + value.charAt(i);
    }
    return String.valueOf(hash);
  }

}