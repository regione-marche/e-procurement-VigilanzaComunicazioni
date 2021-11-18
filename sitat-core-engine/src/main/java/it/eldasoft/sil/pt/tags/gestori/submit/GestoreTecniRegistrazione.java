/*
 * Created on Ott 17, 2012
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.admin.AccountManager;
import it.eldasoft.gene.bl.admin.ProfiliManager;
import it.eldasoft.gene.bl.permessi.PermessiManager;
import it.eldasoft.gene.bl.system.MailManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.CostantiGeneraliAccount;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.utils.MailUtils;
import it.eldasoft.gene.web.struts.admin.CostantiDettaglioAccount;
import it.eldasoft.gene.web.struts.permessi.PermessiAccountEntitaForm;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.mail.IMailSender;
import it.eldasoft.utils.mail.MailSenderException;
import it.eldasoft.utils.profiles.OpzioniUtente;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.sql.comp.SqlComposerException;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

/**
 * Gestore dell'entità TECNI per la registrazione di un nuovo utente applicativo
 * 
 * 
 */
public class GestoreTecniRegistrazione extends AbstractGestoreEntita {

  private static final String MSG_ERROR_LOGIN_DUPLICATO = "gestoreTecnico.loginDuplicato";
  private static final String REGISTRAZIONE_UTENTE_ERRORE                      = "registrazione.utente.errore";
  /**
   * Logger per tracciare messaggio di debug
   */
  static Logger               logger                    = Logger.getLogger(GestoreTecniRegistrazione.class);

  public String getEntita() {
    return "TECNI";
  }

  public void preDelete(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

  }

  public void postDelete(DataColumnContainer impl) throws GestoreException {

  }

  /**
   * Invio mail di registrazione.
   * 
   * @param account
   * @throws GestoreException
   */
  private void inviaMail(Account account) throws GestoreException {

    String nomeMittente = ConfigManager.getValore(CostantiGenerali.PROP_TITOLO_APPLICATIVO);
    String mailAmministratore = ConfigManager.getValore(CostantiGenerali.PROP_INDIRIZZO_MAIL_AMMINISTRATORE);

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

      ICriptazioneByte decriptatore = FactoryCriptazioneByte.getInstance(
          ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI), account.getLogin().getBytes(),
          ICriptazioneByte.FORMATO_DATO_CIFRATO);
      String login = new String(decriptatore.getDatoNonCifrato());

      decriptatore = FactoryCriptazioneByte.getInstance(ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
          account.getPassword().getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
      String password = new String(decriptatore.getDatoNonCifrato());

      String strAbilitazione = "Disabilitato";

      if (account.getUtenteDisabilitato().intValue() == new Integer(CostantiDettaglioAccount.ABILITATO).intValue()) {
        oggettoMail = resBundleGenerale.getString(CostantiGenerali.RESOURCE_OGGETTO_MAIL_REGISTRAZIONE_AUTOMATICA);
        oggettoMail = UtilityStringhe.replaceParametriMessageBundle(oggettoMail, new String[] { nomeMittente });

        //testoMail = resBundleGenerale.getString(CostantiGenerali.RESOURCE_TESTO_MAIL_REGISTRAZIONE_AUTOMATICA);
        //testoMail = UtilityStringhe.replaceParametriMessageBundle(testoMail, new String[] { account.getNome(), nomeMittente, login,
        //    password, nomeMittente });

        testoMail = resBundleGenerale.getString("registrazione.mail.automatica.aliprog4.testo");
        testoMail = UtilityStringhe.replaceParametriMessageBundle(testoMail, new String[] { account.getNome(), login, password });
        
        mailSender.send(account.getEmail(), oggettoMail, testoMail);
        strAbilitazione = "Abilitato";
        invioMailUtente = true;
      }

      if (mailAmministratore != null && !"".equals(mailAmministratore.trim())) {
        oggettoMail = resBundleGenerale.getString(CostantiGenerali.RESOURCE_OGGETTO_MAIL_REGISTRAZIONE_AMMINISTRATORE);
        oggettoMail = UtilityStringhe.replaceParametriMessageBundle(oggettoMail, new String[] { nomeMittente });

        testoMail = resBundleGenerale.getString(CostantiGenerali.RESOURCE_TESTO_MAIL_REGISTRAZIONE_AMMINISTRATORE);
        testoMail = UtilityStringhe.replaceParametriMessageBundle(testoMail, new String[] { account.getNome(), login, password,
            strAbilitazione, nomeMittente });

        mailSender.send(mailAmministratore, oggettoMail, testoMail);
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

    } catch (CriptazioneException e) {
      throw new GestoreException("Errore durante l'inserimento dell'occorrenza di USRSYS !", "updateUTENT", e);
    }
  }

  public void preInsert(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("preInsert: inizio metodo");
    synchronized (GestoreTecniRegistrazione.class) {

      GeneManager gene = (GeneManager) UtilitySpring.getBean("geneManager", this.getServletContext(), GeneManager.class);



      // Gestione USRSYS
      AccountManager accountManager = (AccountManager) UtilitySpring.getBean("accountManager", this.getServletContext(),
          AccountManager.class);
      ProfiliManager profiliManager = (ProfiliManager) UtilitySpring.getBean("profiliManager", this.getServletContext(),
          ProfiliManager.class);

      String login = datiForm.getString("USRSYS.SYSNOM");
      String password = datiForm.getString("USRSYS.SYSPWD");
      String intestazione = datiForm.getString("TECNI.COGTEI") + " " + datiForm.getString("TECNI.NOMETEI");

      Account account = new Account();
      String registrazioneAutomatica = ConfigManager.getValore(CostantiGenerali.PROP_REGISTRAZIONE_AUTOMATICA);
      try {
        if (!accountManager.isUsedLogin(login, -1)) {
          account.setLogin(login);
          account.setPassword(password);
          account.setNome(intestazione);
          OpzioniUtente opzioniUtente = new OpzioniUtente(ConfigManager.getValore(CostantiGenerali.PROP_OPZIONI_UTENTE_DEFAULT));
          opzioniUtente.setOpzione(CostantiGeneraliAccount.OPZIONI_MENU_STRUMENTI);
          opzioniUtente.setOpzione(CostantiGeneraliAccount.OPZIONI_SICUREZZA_PASSWORD);
          account.setOpzioniUtente(opzioniUtente.toString());
          account.setEmail(datiForm.getString("TECNI.EMATEC"));
          account.setDataInserimento(new Date());
          account.setAbilitazioneStd(CostantiGeneraliAccount.DEFAULT_ABILITAZIONE_STANDARD);
          account.setFlagLdap(new Integer(0));

          // Abilitazione automatica
          if ("1".equals(registrazioneAutomatica)) {
            account.setUtenteDisabilitato(new Integer(CostantiDettaglioAccount.ABILITATO));
          } else {
            account.setUtenteDisabilitato(new Integer(CostantiDettaglioAccount.DISABILITATO));
          }

          
          accountManager.insertAccount(account);

          // Inserimento dei dati nello storico delle password (STOUTESYS) per
          // evitare che alla prima connessione il software chieda di cambiare
          // la password; utilizzo i dati login e password presi dal bean
          // account perche' sono gia' criptati
          accountManager.insertStoriaAccount(account.getIdAccount(), account.getLogin(), 
                  account.getLoginCriptata(), account.getPassword());

          // Inserimento dell'associazione con il profilo di default
          boolean gruppiDis = false;
          String gruppiDisabilitati = ConfigManager.getValore(CostantiGenerali.PROP_GRUPPI_DISABILITATI);
          if ("1".equals(gruppiDisabilitati)) {
            gruppiDis = true;
          }

          String codiceApplicazione = ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE);
          String profiloUtenteDefault = ConfigManager.getValore(CostantiGenerali.PROP_PROFILO_DEFAULT_REGISTRAZIONE);
          profiliManager.updateAssociazioneProfiliAccount(account.getIdAccount(), new String[] { profiloUtenteDefault },
              codiceApplicazione, gruppiDis);

          // Inserimento occorrenza su G_PERMESSI (l'utente mantiene la
          // proprietà della propria occorrenza di TECNI)
          PermessiManager permessiManager = (PermessiManager) UtilitySpring.getBean("permessiManager", this.getServletContext(),
              PermessiManager.class);

          // Calcolo delle chiave di TECNI
          String codtec = gene.calcolaCodificaAutomatica("TECNI", "CODTEC");
          datiForm.setValue("TECNI.CODTEC", codtec);
          PermessiAccountEntitaForm permessi = new PermessiAccountEntitaForm();
          permessi.setIdPermesso(new String[] { "0" });
          permessi.setIdAccount(new String[] { new Integer(account.getIdAccount()).toString() });
          permessi.setCondividiEntita(new String[] { "1" });
          permessi.setAutorizzazione(new String[] { "1" });
          permessi.setProprietario(new String[] { "1" });
          permessi.setCampoChiave("CODTEC");
          permessi.setValoreChiave(codtec);
          permessiManager.updateAssociazioneAccountEntita(permessi);
        } else {
          throw new GestoreException("Errore: login duplicato!", MSG_ERROR_LOGIN_DUPLICATO, null);
        }
      } catch (CriptazioneException e) {
        throw new GestoreException("Errore durante l'inserimento dell'occorrenza di USRSYS !", "updateUTENT", e);
      } catch (SqlComposerException sce) {
          throw new GestoreException("Errore durante la registrazione del nuovo utente", REGISTRAZIONE_UTENTE_ERRORE, sce);
      }

      // Inserimento nuova stazione appaltante (UFFINT)
      String codein = gene.calcolaCodificaAutomatica("UFFINT", "CODEIN");
      try {
        datiForm.addColumn("UFFINT.CODEIN", JdbcParametro.TIPO_TESTO, codein);
        if (datiForm.isColumn("UNITA_ORGANIZZATIVA")) {
        	String unitaOrganizzativa = datiForm.getString("UNITA_ORGANIZZATIVA");
        	datiForm.setValue("UFFINT.NOMEIN", datiForm.getString("UFFINT.NOMEIN") + ", " + unitaOrganizzativa);
        }
        datiForm.insert("UFFINT", gene.getSql());
        // Associazione UFFINT - USRSYS
        datiForm.addColumn("USR_EIN.SYSCON", JdbcParametro.TIPO_NUMERICO, new Long(account.getIdAccount()));
        datiForm.addColumn("USR_EIN.CODEIN", JdbcParametro.TIPO_TESTO, codein);
        datiForm.insert("USR_EIN", gene.getSql());
      } catch (SQLException e) {
        throw new GestoreException("Errore durante l'inserimento dell'ente", "updateUTENT", e);
      }

      // Associazione USRSYS - TECNI
      datiForm.setValue("TECNI.SYSCON", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Integer(account.getIdAccount())));

      // Associazione UFFINT - TECNI
      datiForm.addColumn("TECNI.CGENTEI", JdbcParametro.TIPO_TESTO, codein);

      // Intestazione
      datiForm.addColumn("TECNI.NOMTEC", JdbcParametro.TIPO_TESTO, intestazione);

    }
    if (logger.isDebugEnabled()) logger.debug("preInsert: fine metodo");

  }

  public void postInsert(DataColumnContainer impl) throws GestoreException {

    String login = impl.getString("USRSYS.SYSNOM");
    AccountManager accountManager = (AccountManager) UtilitySpring.getBean("accountManager", this.getServletContext(), AccountManager.class);

    ICriptazioneByte criptatore;
    try {
      criptatore = FactoryCriptazioneByte.getInstance(ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
          login.getBytes(), ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);
      login = new String(criptatore.getDatoCifrato());
      Account account = accountManager.getAccountByLogin(login);
      inviaMail(account);
    } catch (CriptazioneException e) {
      throw new GestoreException("Errore durante l'inserimento dell'occorrenza di USRSYS !", "updateUTENT", e);
    } catch (SqlComposerException sce) {
        throw new GestoreException("Errore durante la registrazione del nuovo utente",
                REGISTRAZIONE_UTENTE_ERRORE, sce);
          }

    

  }

  public void preUpdate(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

  }

  public void postUpdate(DataColumnContainer impl) throws GestoreException {

  }

}