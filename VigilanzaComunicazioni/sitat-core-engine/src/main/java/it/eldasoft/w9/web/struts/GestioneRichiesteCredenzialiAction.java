package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.bl.admin.AccountManager;
import it.eldasoft.gene.bl.admin.ProfiliManager;
import it.eldasoft.gene.bl.system.MailManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.CostantiGeneraliAccount;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.utils.MailUtils;
import it.eldasoft.gene.web.struts.admin.CostantiDettaglioAccount;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.mail.IMailSender;
import it.eldasoft.utils.mail.MailSenderException;
import it.eldasoft.utils.profiles.OpzioniUtente;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.tags.gestori.submit.GestoreW_APPLICATION_KEYS;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.Log4JLogChute;
import org.springframework.transaction.TransactionStatus;

/**
 * Action per gestire le richieste delle credenziali ai servizi di cooperazione.
 */
public class GestioneRichiesteCredenzialiAction extends ActionBaseNoOpzioni {

	static Logger logger = Logger
			.getLogger(GestioneRichiesteCredenzialiAction.class);

	private SqlManager sqlManager;

	private AccountManager accountManager;

	private ProfiliManager profiliManager;

	private MailManager    mailManager;
	
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	public void setProfiliManager(ProfiliManager profiliManager) {
		this.profiliManager = profiliManager;
	}

	public void setMailManager(MailManager mailManager) {
	    this.mailManager = mailManager;
	}
	/**
	 * @see it.eldasoft.gene.commons.web.struts.ActionBase#runAction(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward runAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String target = CostantiGeneraliStruts.FORWARD_OK;
		boolean result = false;
		Account account = null;
		Long id_richiesta = new Long(request.getParameter("id_richiesta"));
		String azione = request.getParameter("azione");
		String codapp = ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE);
		Long stato, syscon;
		String messaggioErrore = "";
		String clientID = "", clientKey = "", dirigente = "", mailDirigente = "", mailTecnico = "", codein = "", servizi = "", testoAzione = "", nomeUtente = "", passwordUtente = "", ente = "", cfEnte = "";
		if (azione != null && id_richiesta != null) {
			TransactionStatus status = null;
			try {
				Random rand = new Random();
				List<?> richiesta = sqlManager.getListVector("select STATO, CLIENT_ID, DIRIGENTE, MAIL_DIRIGENTE, CODEIN, SERVIZI, CLIENT_KEY, SYSCON, MAIL_TECNICO from W_COOPUSR where ID_RICHIESTA = ?",	new Object[] { new Long(id_richiesta) });
				List<?> richiestaSelezionata = new ArrayList<Object>();
				if (richiesta.size() > 0) {
					richiestaSelezionata = (List<?>) richiesta.get(0);
					stato = new Long(richiestaSelezionata.get(0).toString() != null && richiestaSelezionata.get(0).toString().length() > 0? richiestaSelezionata.get(0).toString():"1");
					clientID = richiestaSelezionata.get(1).toString();
					dirigente = richiestaSelezionata.get(2).toString();
					mailDirigente = richiestaSelezionata.get(3).toString();
					codein = richiestaSelezionata.get(4).toString();
					servizi = richiestaSelezionata.get(5).toString();
					clientKey = richiestaSelezionata.get(6).toString();
					syscon = new Long((richiestaSelezionata.get(7).toString() != null && richiestaSelezionata.get(7).toString().length()>0)?richiestaSelezionata.get(7).toString():"-1");
					mailTecnico = richiestaSelezionata.get(8).toString();
					List<?> datiEnte = sqlManager.getListVector("select NOMEIN, CFEIN from UFFINT where CODEIN = ?", new Object[] { codein });
					List<?> enteSelezionato = new ArrayList<Object>();
					if (datiEnte.size() > 0) {
						enteSelezionato = (List<?>) datiEnte.get(0);
						ente = enteSelezionato.get(0).toString();
						cfEnte = enteSelezionato.get(1).toString();
					}
					if (azione.equals("abilita")) {
						testoAzione = "ABILITATA";
						switch (stato.intValue()) {
						case 1:// Nuova Richiesta
							account = new Account();
							// l'utente è nuovo quindi non può essere già
							// presente in banca dati
							String username = clientID;
							while (accountManager.isUsedLogin(username, -1)) {
								username = clientID.substring(0, 11)+ StringUtils.leftPad(rand.nextInt(999999999) + "",	9, '0');
							}
							nomeUtente = username;
							account.setLogin(username);
							account.setPassword(UtilitySITAT.generaPasswordRobusta());
							passwordUtente = account.getPassword();
							account.setNome(dirigente);
							OpzioniUtente opzioniUtente = new OpzioniUtente(ConfigManager.getValore(CostantiGenerali.PROP_OPZIONI_UTENTE_DEFAULT));
							opzioniUtente.setOpzione(CostantiGeneraliAccount.OPZIONI_MENU_STRUMENTI);
							opzioniUtente.setOpzione(CostantiGeneraliAccount.OPZIONI_SICUREZZA_PASSWORD);
							account.setOpzioniUtente(opzioniUtente.toString());
							account.setEmail(mailTecnico);
							account.setDataInserimento(new Date());
							account.setAbilitazioneStd(CostantiGeneraliAccount.DEFAULT_ABILITAZIONE_LAVORI);
							account.setAbilitazioneGare(CostantiGeneraliAccount.DEFAULT_ABILITAZIONE_GARE);
							account.setAbilitazioneContratti(CostantiGeneraliAccount.DEFAULT_ABILITAZIONE_CONTRATTI);
							account.setFlagLdap(new Integer(0));
							account.setUtenteDisabilitato(new Integer(CostantiDettaglioAccount.ABILITATO));
							accountManager.insertAccount(account);
							// associazione usr_ein
							status = this.sqlManager.startTransaction();
							sqlManager.update("insert into USR_EIN(CODEIN,SYSCON) values( ?, ?)", new Object[] {codein,	new Long(account.getIdAccount()) });
							// associo i profili
							if (servizi.length() > 0) {
								String[] arrayApplicativiScelti = servizi.split(";");
								profiliManager.updateAssociazioneProfiliAccount(account.getIdAccount(),	arrayApplicativiScelti, codapp,	false);
							}
							// associo l'utenza alla richiesta
							sqlManager.update("update W_COOPUSR set SYSCON = ? where ID_RICHIESTA = ?",	new Object[] {new Long(account.getIdAccount()),	id_richiesta });
							// creo le credenziali in W_APPLICATION_KEYS
							DataColumn[] arrayDataW_APPLICATION_KEYS = new DataColumn[4];
							arrayDataW_APPLICATION_KEYS[0] = new DataColumn("W_APPLICATION_KEYS.CLIENTID",new JdbcParametro(JdbcParametro.TIPO_TESTO,clientID));
							arrayDataW_APPLICATION_KEYS[1] = new DataColumn("W_APPLICATION_KEYS.CHIAVE",new JdbcParametro(JdbcParametro.TIPO_TESTO,	""));
							arrayDataW_APPLICATION_KEYS[2] = new DataColumn("KEY_DECRYPT",new JdbcParametro(JdbcParametro.TIPO_TESTO,	clientKey));
							arrayDataW_APPLICATION_KEYS[3] = new DataColumn("W_APPLICATION_KEYS.NOTE",new JdbcParametro(JdbcParametro.TIPO_TESTO,	"Applicazione Client " + ente));
							
							DataColumnContainer dataContW_APPLICATION_KEYS = new DataColumnContainer(arrayDataW_APPLICATION_KEYS);

							dataContW_APPLICATION_KEYS.getColumn("W_APPLICATION_KEYS.CLIENTID").setChiave(true);

							GestoreW_APPLICATION_KEYS gestoreW_APPLICATION_KEYS = new GestoreW_APPLICATION_KEYS();
							gestoreW_APPLICATION_KEYS.setRequest(request);
							gestoreW_APPLICATION_KEYS.inserisci(status,	dataContW_APPLICATION_KEYS);
							// aggiorno stato e data_abilitazione della
							// richiesta
							sqlManager.update("update W_COOPUSR set STATO = 2, DATA_ABILITAZIONE = ? where ID_RICHIESTA = ?",new Object[] {	new Timestamp(new Date().getTime()),id_richiesta });
							break;
						case 3:// Richiesta disabilitata
							account = accountManager.getAccountById(syscon.intValue());
							if (account != null) {
								nomeUtente = account.getLogin();
								passwordUtente = account.getPassword();
								//decifrare password
								ICriptazioneByte regDefaultPwdICriptazioneByte = null;
						        try {
						            regDefaultPwdICriptazioneByte = FactoryCriptazioneByte.getInstance(
						            ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
						            passwordUtente.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
						            String regDefaultPwdDecriptata = new String(regDefaultPwdICriptazioneByte.getDatoNonCifrato());
						            passwordUtente = regDefaultPwdDecriptata;
						        } catch (CriptazioneException ce) {
						            throw new GestoreException("Errore nella determinazione della pwd di default del protocollo sso", null);
						        }
								account.setUtenteDisabilitato(new Integer(CostantiDettaglioAccount.ABILITATO));
								account.setNome(dirigente);
								account.setEmail(mailTecnico);
								accountManager.updateAccount(account);
								status = this.sqlManager.startTransaction();
								// associo i profili
								if (servizi.length() > 0) {
									String[] arrayApplicativiScelti = servizi.split(";");
									profiliManager.updateAssociazioneProfiliAccount(account.getIdAccount(),	arrayApplicativiScelti,	codapp, false);
								} else {
									profiliManager.updateAssociazioneProfiliAccount(account.getIdAccount(),	null,	codapp, false);
								}
								// aggiorno stato e data_abilitazione della
								// richiesta
								sqlManager.update("update W_COOPUSR set STATO = 2, DATA_ABILITAZIONE = ? where ID_RICHIESTA = ?",new Object[] {new Timestamp(new Date().getTime()),id_richiesta });
							} else {
								messaggioErrore = "Non è stato possibile disabilitare la richiesta. L'utente associato non esiste.";
							}
						default:
							break;
						}
					} else if (azione.equals("disabilita")) {
						testoAzione = "DISABILITATA";
						account = accountManager.getAccountById(syscon.intValue());
						if (account != null) {
							account.setUtenteDisabilitato(new Integer(CostantiDettaglioAccount.DISABILITATO));
							account.setNome(dirigente);
							account.setEmail(mailTecnico);
							accountManager.updateAccount(account);
							status = this.sqlManager.startTransaction();
							// aggiorno stato e data_sospensione della richiesta
							sqlManager.update("update W_COOPUSR set STATO = 3, DATA_DISABILITAZIONE = ? where ID_RICHIESTA = ?",new Object[] {new Timestamp(new Date().getTime()),id_richiesta });
						} else {
							messaggioErrore = "Non è stato possibile disabilitare la richiesta. L'utente associato non esiste.";
						}
					} else if (azione.equals("rifiuta")) {
						testoAzione = "RIFIUTATA";
						status = this.sqlManager.startTransaction();
						// aggiorno stato e data_sospensione della richiesta
						sqlManager.update("update W_COOPUSR set STATO = 4, DATA_DISABILITAZIONE = ? where ID_RICHIESTA = ?", new Object[] {new Timestamp(new Date().getTime()),id_richiesta });
					}
					
				} else {
					messaggioErrore = "La richiesta che si è cercato di elaborare non esiste.";
				}
				result = true;
			} catch (Exception ex) {
				messaggioErrore = "Errore imprevisto durante l'elaborazione della richiesta. " + ex.getMessage();
				logger.error("Errore", ex);
			} finally {
				if (status != null) {
					try {
						if (result) {
							this.sqlManager.commitTransaction(status);
						} else {
							this.sqlManager.rollbackTransaction(status);
						}
					} catch (SQLException e) {
						logger.error("Errore durante la chiusura della transazione",e);
					}
				}
			}
			if (messaggioErrore.equals("")) {
				this.inviaMail(request, mailDirigente, mailTecnico, clientID, clientKey, nomeUtente, passwordUtente, ente,	cfEnte, testoAzione);
			}
		} else {
			messaggioErrore = "Errore imprevisto durante l'elaborazione della richiesta.";
		}
		if (messaggioErrore.length() > 0) {
			aggiungiMessaggio(request, "errors.generico", messaggioErrore);
		}
		return mapping.findForward(target);
	}

	/**
	 * Invio mail di abilitazione/disabilitazione/rifiuto delle credenziali per
	 * i servizi di cooperazione applicativa.
	 * 
	 * @param account
	 * @throws GestoreException
	 */
	private void inviaMail(HttpServletRequest request,
			String mailDirigente,
			String mailTecnico, String clientID, String clientKey,
			String username, String password, String ente, String cfente,
			String azione) {
		try {
			IMailSender mailSender = MailUtils.getInstance(mailManager,ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE),	CostantiGenerali.PROP_CONFIGURAZIONE_MAIL_STANDARD);
			this.inviaMailRichiesta(request, mailSender, mailDirigente, mailTecnico, clientID,
					clientKey, username, password, azione);
			// INVIO NOTIFICA AMMINISTRATORE
			this.inviaMailNotifica(request, mailSender, ente,cfente, azione);
		} catch (MailSenderException ms) {
			String logMessageKey = ms.getChiaveResourceBundle();
			String logMessageError = resBundleGenerale.getString(logMessageKey);
			for (int i = 0; ms.getParametri() != null && i < ms.getParametri().length; i++) {
				logMessageError = logMessageError.replaceAll(UtilityStringhe.getPatternParametroMessageBundle(i),(String) ms.getParametri()[i]);
			}
			logger.error(logMessageError, ms);
		}
	}

	/**
	 * Invio mail di registrazione per l'Amministratore.
	 */
	private void inviaMailNotifica(HttpServletRequest request,
			IMailSender mailSender, String ente,
			String cfente, String azione) {

		String nomeMittente = ConfigManager.getValore(CostantiGenerali.PROP_TITOLO_APPLICATIVO);
		String mailAmministratore = ConfigManager.getValore("it.eldasoft.pubblicazioni.ws.mailAmministratore");
		try {
			String testoMail = null;
			String oggettoMail = null;
			if (mailAmministratore != null && !"".equals(mailAmministratore.trim())) {
				oggettoMail = azione + " richiesta credenziali accesso al servizio di cooperazione applicativa e interoperatibilità "	+ nomeMittente;
				testoMail = this.getTestoAzioneCredenziali(request, 2, azione,nomeMittente, ente, cfente, null, null, null, null);
				String[] destinatari = mailAmministratore.split(";");
				String destinatario = destinatari[0];
				String[] destinatariCC = null;
				if (destinatari.length > 1) {
					destinatariCC = new String[destinatari.length - 1];
					for (int i = 1; i < destinatari.length; i++) {
						destinatariCC[i - 1] = destinatari[i];
					}
				}
				mailSender.send(new String[] { destinatario }, destinatariCC,null, oggettoMail, testoMail, null);
			}
		} catch (Exception e) {
			String logMessageKey = "warnings.registrazione.mancatoInvioMailAdmin";
			String logMessageError = resBundleGenerale.getString(logMessageKey);
			if (e instanceof MailSenderException) {
				MailSenderException ms = (MailSenderException) e;
				logMessageKey = ms.getChiaveResourceBundle();
				logMessageError = resBundleGenerale.getString(logMessageKey);
				for (int i = 0; ms.getParametri() != null && i < ms.getParametri().length; i++)
					logMessageError = logMessageError.replaceAll(UtilityStringhe.getPatternParametroMessageBundle(i),(String) ms.getParametri()[i]);
				logger.error(logMessageError, e);
				logMessageKey = "warnings.registrazione.mancatoInvioMailAdmin";
				logMessageError = resBundleGenerale.getString(logMessageKey);
			} else {
				logger.error(logMessageError, e);
			}
			UtilityStruts.addMessage(request, "warning", logMessageKey, null);
			logger.warn(logMessageError, e);
		}
	}

	private void inviaMailRichiesta(HttpServletRequest request,
			IMailSender mailSender, String mailDirigente,
			String mailTecnico, String clientID, String clientKey,
			String username, String password, String azione) {
		String nomeMittente = ConfigManager.getValore(CostantiGenerali.PROP_TITOLO_APPLICATIVO);
		String testoMail = null;
		String oggettoMail = null;
		try {
			oggettoMail = azione + " richiesta credenziali accesso al servizio di cooperazione applicativa e interoperatibilità "	+ nomeMittente;
			// invio mail all'amministrazione
			testoMail = this.getTestoAzioneCredenziali(request, 1, azione, nomeMittente,	null, null, clientID, clientKey, username, password);
			String[] destinatari = new String[2];
			destinatari[0] = mailDirigente;
			destinatari[1] = mailTecnico;
			mailSender.send(destinatari, null, null, oggettoMail, testoMail,null);
		} catch (Exception e) {
			String logMessageKey = "warnings.registrazione.mancatoInvioMailUtente";
			String logMessageError = resBundleGenerale.getString(logMessageKey);
			if (e instanceof MailSenderException) {
				MailSenderException ms = (MailSenderException) e;
				logMessageKey = ms.getChiaveResourceBundle();
				logMessageError = resBundleGenerale.getString(logMessageKey);
				for (int i = 0; ms.getParametri() != null && i < ms.getParametri().length; i++)
					logMessageError = logMessageError.replaceAll(UtilityStringhe.getPatternParametroMessageBundle(i),(String) ms.getParametri()[i]);
				logger.error(logMessageError, e);
				logMessageKey = "warnings.registrazione.mancatoInvioMailUtente";
				logMessageError = resBundleGenerale.getString(logMessageKey);
			} else {
				logger.error(logMessageError, e);
			}
			UtilityStruts.addMessage(request, "warning", logMessageKey, null);
			logger.warn(logMessageError, e);
		}

	}

	private String getTestoAzioneCredenziali(HttpServletRequest request, int tipo, String azione,
			String nomeMittente, String ente, String cfente, String clientID,
			String clientKey, String username, String password)
			throws SQLException {

		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "file");
		velocityEngine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "false");
		velocityEngine.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.Log4JLogChute");
		// velocityEngine.setProperty("runtime.log.logsystem.log4j.category",
		// "velocity");
		velocityEngine.setProperty(Log4JLogChute.RUNTIME_LOG_LOG4J_LOGGER,logger.getName());

		String velocityModelPath = request.getSession().getServletContext().getRealPath("/velocitymodel/");
		velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,	velocityModelPath);
		velocityEngine.init();

		VelocityContext velocityContext = new VelocityContext();
		StringWriter writerUtente = new StringWriter();

		String velocityModel = null;

		velocityContext.put("AZIONE", azione);
		velocityContext.put("NOMEMITT", nomeMittente);

		switch (tipo) {
		case 1:
			if (azione.equals("ABILITATA")) {
				velocityContext.put("CLIENT_ID", clientID);
				velocityContext.put("CLIENT_KEY", clientKey);
				velocityContext.put("USERNAME", username);
				velocityContext.put("PASSWORD", password);
			}
			velocityModel = "azione-credenziali-mail-ricevuta.txt";
			break;
		case 2:
			velocityContext.put("ENTE", ente);
			velocityContext.put("CFENTE", cfente);
			velocityModel = "azione-credenziali-mail-notifica.txt";
			break;
		}

		Template templateUtente = velocityEngine.getTemplate(velocityModel);

		templateUtente.merge(velocityContext, writerUtente);
		String testoMail = writerUtente.toString();
		return testoMail;
	}

}