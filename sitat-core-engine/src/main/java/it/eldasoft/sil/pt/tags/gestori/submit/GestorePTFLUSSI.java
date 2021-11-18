package it.eldasoft.sil.pt.tags.gestori.submit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.bl.admin.AccountManager;
import it.eldasoft.gene.bl.system.MailManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.utils.MailUtils;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.ImportExportXMLManager;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.mail.IMailSender;
import it.eldasoft.utils.mail.MailSenderException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityStringhe;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.springframework.transaction.TransactionStatus;


public class GestorePTFLUSSI extends AbstractGestoreChiaveNumerica {

  static Logger               logger   = Logger.getLogger(GestorePTFLUSSI.class);

  private static final String   PROP_PATH_ALLEGATI_MAIL                = "it.eldasoft.path.allegati.mail";
  
  
  public String[] getAltriCampiChiave() {
    return null;
  }

  public String getCampoNumericoChiave() {
    return "IDFLUSSO";
  }

  public String getEntita() {
    return "PTFLUSSI";
  }

  public void postDelete(DataColumnContainer arg0) throws GestoreException {

  }

  public void postUpdate(DataColumnContainer arg0) throws GestoreException {

  }

  public void preDelete(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

  public void preUpdate(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

  public void postInsert(DataColumnContainer impl) throws GestoreException{
   
	  PtManager m = (PtManager) UtilitySpring.getBean("ptManager", getServletContext(), PtManager.class);
	  AccountManager accountManager = (AccountManager) UtilitySpring.getBean("accountManager", this.getServletContext(), AccountManager.class);
	  ICriptazioneByte criptatore;
	  ImportExportXMLManager importExportMAnager = (ImportExportXMLManager) UtilitySpring.getBean("importExportXMLManager", getServletContext(), ImportExportXMLManager.class);
	  Long contri = null;
	  try {
		  if (impl.isColumn("PTFLUSSI.KEY01") && impl.getLong("PTFLUSSI.KEY01") != null) {
			  contri = impl.getLong("PTFLUSSI.KEY01");
			  //Creo il file PDF
			  String piatriID = "";
			  String cenint = "";
			  Long pubblica = new Long(1);
			  List< ? > piatri = this.sqlManager.getVector("select ID, PUBBLICA, CENINT from PIATRI where CONTRI = ?", new Object[] { contri });
			  if (piatri != null && piatri.size() > 0) { 
				  piatriID = (String) SqlManager.getValueFromVectorParam(piatri, 0).getValue();
				  pubblica = new Long((String) SqlManager.getValueFromVectorParam(piatri, 1).getValue());
				  cenint = (String) SqlManager.getValueFromVectorParam(piatri, 2).getValue();
			  }
			  HashMap<String, Object> params = new HashMap<String, Object>();
			  params.put("contri", contri);
			  params.put("tiprog", new Long(impl.getString("TIPROGF")));
			  params.put("piatriID", piatriID);
			  //m.creaPdf(params, this.getRequest());
			  m.creaPdfNuovaNormativa(params, this.getRequest());
			  //creo file xml programma se il programma è triennale
			  /*if (impl.isColumn("TIPROGF") && !impl.getString("TIPROGF").equals("2") ) {
				  String documentString = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>";
				  XmlObject xmlObject = null;
				  int idammin = 0;
				  try {
					  idammin = Integer.parseInt(impl.getObject("IDAMMINISTRAZIONE").toString());
				  } catch(Exception e) {
					  ;
				  }
				  xmlObject = importExportMAnager.getXMLAliProg4Programma(contri, impl.getString("TIPROGF"), idammin);
		          m.updateClobXml(impl.getLong("PTFLUSSI.IDFLUSSO"), documentString + xmlObject.toString());
			  }*/
			  //Recupero il profilo
			  int result = 1;
			  ProfiloUtente profiloUtente = (ProfiloUtente) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
			  String profilo = (String)this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
			  if (pubblica != null && pubblica == 1 && geneManager.getProfili().checkProtec(profilo, "FUNZ", "VIS", "ALT.PIANI.ALIPROGMIT")) {
				  //Utente non abilitato all'operazione di pubblicazione
			  	  //Invio mail all'utente con la procedura per attivare il servizio
			  	  criptatore = FactoryCriptazioneByte.getInstance(ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
			  	  profiloUtente.getLogin().getBytes(), ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);
			  	  String login = new String(criptatore.getDatoCifrato());
			  	  Account account = accountManager.getAccountByLogin(login);
			  	  String email = impl.getObject("EMAIL").toString();
			  	  /*if (email != null && !email.equals("")) {
			  		account.setLogin(profiloUtente.getLogin());
			  		account.setEmail(email);
			  		accountManager.updateAccount(account);
			  	  }*/
			  	  StringBuilder subentro = new StringBuilder("");
				  result = m.pubblicaProgramma(contri, impl.getLong("PTFLUSSI.IDFLUSSO"), subentro, this.getRequest());
				  switch (result) {
				  	case 0:
				  		if (subentro.length() != 0) {
				  		    //Subentro
					  		UtilityStruts.addMessage(this.getRequest(), "warning", "warnings.gestoreException.*.pubblicazione.subentro", new String[] { "l'utente " + account.getNome() + " non è abilitato al servizio ed ", subentro.toString(), cenint, account.getEmail()});
					  		inviaMail(account, profiloUtente.getLogin(), true, true, subentro.toString());
				  		} else {
				  			//Disabilitato
				  			UtilityStruts.addMessage(this.getRequest(), "warning", "warnings.gestoreException.*.pubblicazione.utente-disabilitato", new String[] { account.getNome(), account.getEmail()});
				  			inviaMail(account, profiloUtente.getLogin(), true, false, subentro.toString());
				  		}
				  		m.deleteFlussoInvalido(impl.getLong("PTFLUSSI.IDFLUSSO"));
				  		break;
				  	case 1:
				  		//Programma pubblicato con successo
				  		UtilityStruts.addMessage(this.getRequest(), "info", "info.pubblicazione.programma-pubblicato", null);
				  		break;
				  	case -1:
				  		//Programma non pubblicato errore durante i controlli
				  		throw new GestoreException("Errore generico durante la pubblicazione del programma", "pubblicazione.errore-generico");
				  	case -2:
				  		//Esiste già un programma pubblicato per l'anno e la stazione appaltante
				  		UtilityStruts.addMessage(this.getRequest(), "warning", "warnings.gestoreException.*.pubblicazione.sa-programma-exist", null);
				  		m.deleteFlussoInvalido(impl.getLong("PTFLUSSI.IDFLUSSO"));
				  		break;
				  	case -3:
				  		//Esiste già un utente abilitato alla pubblicazione per questa stazione appaltante
				  		inviaMail(account, profiloUtente.getLogin(), false, true, subentro.toString());
				  		UtilityStruts.addMessage(this.getRequest(), "warning", "warnings.gestoreException.*.pubblicazione.subentro", new String[] { "", subentro.toString(), cenint, account.getEmail()});
				  		m.deleteFlussoInvalido(impl.getLong("PTFLUSSI.IDFLUSSO"));
				  		break;					  	
				}
			  } 
			  if (result > 0) {
				  //verifico se esiste il file pdf allegato al programma
				  m.updateFileAllegatoFlussi(impl.getLong("PTFLUSSI.IDFLUSSO"), contri);
				  //cambio lo stato del programma a 'Pubblicato'
				  //m.updateStatoProgramma(contri, (pubblica != null && pubblica == 1)?new Long(2):new Long(5), null, null);
			  }
		  }
		  
	  } catch (GestoreException e) {
		  m.deleteFlussoInvalido(impl.getLong("PTFLUSSI.IDFLUSSO"));
		  logger.error("Non e' possibile inviare i dati." + e.getMessage());
		  if (e.getCodice().equals("commons.validate")) {
			  this.getRequest().setAttribute("erroriValidazione", e.getParameters());
		  }
		  throw new GestoreException(e.getMessage(), e.getCodice(), e);
	  } catch (Exception ex) {
		  m.deleteFlussoInvalido(impl.getLong("PTFLUSSI.IDFLUSSO"));
          logger.error("Non e' possibile inviare i dati in quanto non è stato possibile generare il file xml di esportazione." + ex.getMessage());
          throw new GestoreException("flusso non inserito", "inserimento.flusso");
	  }
  }

  /**
   * Invio mail di registrazione.
   * 
   * @param account
   * @param login
   * @param disabilitato utente disabilitato
   * @param subentro richiesto subentro
   * @param nomeSubentro nome dell'utente già attivo per la stazione appaltante
   * @throws GestoreException
   */
  private void inviaMail(Account account, String login, boolean disabilitato, boolean subentro, String nomeSubentro) throws GestoreException {

    //String nomeMittente = ConfigManager.getValore(CostantiGenerali.PROP_TITOLO_APPLICATIVO);
    String mailAmministratore = ConfigManager.getValore(CostantiGenerali.PROP_INDIRIZZO_MAIL_AMMINISTRATORE);
    String pathAllegati = ConfigManager.getValore(PROP_PATH_ALLEGATI_MAIL);
    if (subentro) {
    	pathAllegati += "/subentro";
    } else {
    	pathAllegati += "/abilitazione";
    }
 
    boolean creazioneMailSender = false;
    boolean invioMailUtente = false;

    try {
      MailManager mailManager = (MailManager) UtilitySpring.getBean("mailManager", this.getServletContext(), MailManager.class);

      IMailSender mailSender = MailUtils.getInstance(mailManager, 
              ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE),
              CostantiGenerali.PROP_CONFIGURAZIONE_MAIL_STANDARD);
      
      creazioneMailSender = true;
      String testoMail = null;
      String oggettoMail = null;

      //String strAbilitazione = "Disabilitato";

      
      oggettoMail = resBundleGenerale.getString("attivazione.mail.pubblicazione.oggetto");
      
      testoMail = resBundleGenerale.getString("attivazione.mail.pubblicazione.user.testo");
      if (subentro) {
    	  testoMail = UtilityStringhe.replaceParametriMessageBundle(testoMail, new String[] { account.getNome(), login, ", subentrando al Referente della Programmazione Triennale gi\u00E0 attivo " + nomeSubentro, ", in subentro"});
      } else {
    	  testoMail = UtilityStringhe.replaceParametriMessageBundle(testoMail, new String[] { account.getNome(), login, "", ""});
      }
      ByteArrayOutputStream[] allegati = null;
      String[] nomeAllegati = null;
      //Recupero i file da allegare
      if (pathAllegati != null && !pathAllegati.trim().equals("")) {
    	  pathAllegati = this.getServletContext().getRealPath("/") + pathAllegati;
    	  File folder = new File(pathAllegati);
          File[] listOfFiles = folder.listFiles();
          if (listOfFiles != null && listOfFiles.length > 0) {
        	  allegati = new ByteArrayOutputStream[listOfFiles.length];
        	  nomeAllegati = new String[listOfFiles.length];
        	  int indice = 0;
        	  for (File file : listOfFiles) {
                  if (file.isFile()) {
                	  FileInputStream fis = new FileInputStream(file);
                	  ByteArrayOutputStream allegato = new ByteArrayOutputStream();
                	  byte[] buf = new byte[1024];
                	  try {
                          for (int readNum; (readNum = fis.read(buf)) != -1;) {
                        	  allegato.write(buf, 0, readNum); //no doubt here is 0
                              //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                          }
                          allegati[indice] = allegato;
                    	  nomeAllegati[indice] = file.getName();
                      } catch (IOException ex) {
                    	  allegati[indice] = null;
                    	  nomeAllegati[indice] = file.getName();
                      }
                  }
              }
          }
      }
      
      //invio la mail all'utente con gli allegati
      mailSender.send(new String[]{account.getEmail()}, null, null, oggettoMail, testoMail, nomeAllegati, allegati);
      //mailSender.send(account.getEmail(), oggettoMail, testoMail);
      //strAbilitazione = "Disabilitato";
      invioMailUtente = true;

      if (mailAmministratore != null && !"".equals(mailAmministratore.trim())) {
        if (subentro) {
        	testoMail = resBundleGenerale.getString("attivazione.mail.subentro.admin.testo");
        	if (disabilitato) {
        		testoMail = UtilityStringhe.replaceParametriMessageBundle(testoMail, new String[] { account.getNome(), "non è abilitato al servizio e", account.getEmail() });
        	} else {
        		testoMail = UtilityStringhe.replaceParametriMessageBundle(testoMail, new String[] { account.getNome(), "", account.getEmail() });
        	}
        } else {
        	testoMail = resBundleGenerale.getString("attivazione.mail.pubblicazione.admin.testo");
        	testoMail = UtilityStringhe.replaceParametriMessageBundle(testoMail, new String[] { account.getNome(), account.getEmail() });
        }
        mailSender.send(mailAmministratore, "Pubblicazione non autorizzata", testoMail);
      }

    } catch (MailSenderException ms) {

      String logMessageKey = ms.getChiaveResourceBundle();
      String logMessageError = resBundleGenerale.getString(logMessageKey);
      for (int i = 0; ms.getParametri() != null && i < ms.getParametri().length; i++)
        logMessageError = logMessageError.replaceAll(UtilityStringhe.getPatternParametroMessageBundle(i), (String) ms.getParametri()[i]);
      logger.error(logMessageError, ms);
      if (!creazioneMailSender) {
        logger.error(logMessageError, ms);
      } else {
        if (!invioMailUtente) {
          logMessageKey = "warnings.registrazione.mancatoInvioMailUtente";
          logMessageError = resBundleGenerale.getString(logMessageKey);
          UtilityStruts.addMessage(this.getRequest(), "warning", logMessageKey, null);
        } else {
          logMessageKey = "warnings.registrazione.mancatoInvioMailAdmin";
          logMessageError = resBundleGenerale.getString(logMessageKey);
        }
        logger.warn(logMessageError, ms);

      }

    }
    catch (Exception ex) {
    	String logMessageKey = "errors.gestoreException.*.pubblicazione.errore-generico";
    	String logMessageError = resBundleGenerale.getString(logMessageKey);
    	logger.error(logMessageError, ex);
        UtilityStruts.addMessage(this.getRequest(), "error", logMessageKey, null);
	  }
  }	 
  
}