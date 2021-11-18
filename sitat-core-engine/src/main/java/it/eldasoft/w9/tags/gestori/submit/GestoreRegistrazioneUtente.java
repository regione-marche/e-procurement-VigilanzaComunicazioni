package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.LoginManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.bl.admin.AccountManager;
import it.eldasoft.gene.bl.admin.ProfiliManager;
import it.eldasoft.gene.bl.system.MailManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.CostantiGeneraliAccount;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.utils.MailUtils;
import it.eldasoft.gene.web.struts.admin.CostantiDettaglioAccount;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.mail.IMailSender;
import it.eldasoft.utils.mail.MailSenderException;
import it.eldasoft.utils.profiles.OpzioniUtente;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.sql.comp.SqlComposerException;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.transaction.TransactionStatus;

public class GestoreRegistrazioneUtente extends AbstractGestoreEntita {

  private static final String REGISTRAZIONE_UTENTE_MAIL_JNDI_NAME             = "registrazione.utente.mail.jndi.name";
  
  private static final String REGISTRAZIONE_UTENTE_LOGIN_DUPLICATO             = "registrazione.utente.login.duplicato";
  private static final String REGISTRAZIONE_UTENTE_CAPTCHA_ERRORE              = "registrazione.utente.captcha.errore";
  private static final String REGISTRAZIONE_UTENTE_ERRORE                      = "registrazione.utente.errore";
  private static final String REGISTRAZIONE_UTENTE_VELOCITY_MODEL_PATH         = "it.eldasoft.registrazione.utente.velocity.model.path";
  
  private static final String REGISTRAZIONE_UTENTE_MAIL_MITTENTE               = "it.eldasoft.registrazione.utente.mail.indirizzoMittente";
  private static final String REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE         = "it.eldasoft.registrazione.utente.mail.indirizzoAmministratore";
  
  private static final String REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE_OGGETTO          = "it.eldasoft.registrazione.utente.mail.amministratore.oggetto";
  private static final String REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE_VELOCITY_MODEL   = "it.eldasoft.registrazione.utente.mail.amministratore.model";
  
  private static final String REGISTRAZIONE_UTENTE_MAIL_UTENTE_OGGETTO         = "it.eldasoft.registrazione.utente.mail.utente.oggetto";
  private static final String REGISTRAZIONE_UTENTE_MAIL_UTENTE_VELOCITY_MODEL  = "it.eldasoft.registrazione.utente.mail.utente.model";

  static Logger               logger                                           = Logger.getLogger(GestoreRegistrazioneUtente.class);

  /**
   * @return Ritorna l'entita' associata al gestore.
   */
  public String getEntita() {
    return "TECNI";
  }

  public void preDelete(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {
  }

  public void postDelete(DataColumnContainer datiForm) throws GestoreException {
  }

  /**
   * Gestione e creazione del nuovo utente (USRSYS), del tecnico (TECNI), dell'eventuale nuovo ufficio intestatario (UFFINT).
   * 
   * @param status
   *        Stato
   * @param datiForm
   *        Dati della form
   */
  public void preInsert(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {

    if (logger.isDebugEnabled()) {
      logger.debug("GestoreRegistrazioneUtente-preInsert: inizio metodo");
    }
    synchronized (GestoreRegistrazioneUtente.class) {

      GeneManager gene = (GeneManager) UtilitySpring.getBean("geneManager", this.getServletContext(), GeneManager.class);
      AccountManager accountManager = (AccountManager) UtilitySpring.getBean("accountManager", this.getServletContext(),
          AccountManager.class);
      ProfiliManager profiliManager = (ProfiliManager) UtilitySpring.getBean("profiliManager", this.getServletContext(),
          ProfiliManager.class);

      String login = datiForm.getString("USRSYS.SYSNOM");
      String password = datiForm.getString("USRSYS.SYSPWD");
      String intestazione = datiForm.getString("TECNI.COGTEI") + " " + datiForm.getString("TECNI.NOMETEI");

      // Controllo preliminare mediante CAPTCHA
      if (!this.rpHash(this.getRequest().getParameter("realperson")).equals(this.getRequest().getParameter("realpersonHash"))) {
        throw new GestoreException("Il codice di controllo digitato non corrisponde a quello visualizzato",
            REGISTRAZIONE_UTENTE_CAPTCHA_ERRORE, null);
      }

      // Utente USRSYS
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
          
          // gestione email
          account.setEmail(datiForm.getString("TECNI.EMATEC"));
          account.setDataInserimento(new Date());
          account.setAbilitazioneStd(CostantiGeneraliAccount.DEFAULT_ABILITAZIONE_STANDARD);
          account.setFlagLdap(new Integer(0));

          // gestione codice fiscale
          account.setCodfisc(datiForm.getString("TECNI.CFTEC"));
          
          
          if ("1".equals(registrazioneAutomatica)) {
            account.setUtenteDisabilitato(new Integer(CostantiDettaglioAccount.ABILITATO));
          } else {
            account.setUtenteDisabilitato(new Integer(CostantiDettaglioAccount.DISABILITATO));
          }
          accountManager.insertAccount(account);

          // Inserimento nella storia dell'account in modo da evitare la richiesta
          // di modifica della password al successivo login
          accountManager.insertStoriaAccount(account.getIdAccount(), account.getLogin(), 
              account.getLoginCriptata(), account.getPassword());

          // Gestione gruppi
          boolean gruppiDis = false;
          String gruppiDisabilitati = ConfigManager.getValore(CostantiGenerali.PROP_GRUPPI_DISABILITATI);
          if ("1".equals(gruppiDisabilitati)) {
            gruppiDis = true;
          }
          String codiceApplicazione = ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE);

          // Profili scelta durante la registrazione
          String[] listaApplicativi = this.getRequest().getParameterValues("applicativi");
          if (listaApplicativi != null && listaApplicativi.length > 0) {
            profiliManager.updateAssociazioneProfiliAccount(account.getIdAccount(), listaApplicativi, codiceApplicazione, gruppiDis);
          }

        } else {
          throw new GestoreException("Il codice fiscale / login indicato e' gia' utilizzato", REGISTRAZIONE_UTENTE_LOGIN_DUPLICATO, null);
        }
      } catch (CriptazioneException e) {
        throw new GestoreException("Errore durante la registrazione del nuovo utente", REGISTRAZIONE_UTENTE_ERRORE, e);
      } catch (SqlComposerException sce) {
        throw new GestoreException("Errore durante la registrazione del nuovo utente", REGISTRAZIONE_UTENTE_ERRORE, sce);
      }

      // Gestione UFFINT
      String codein = datiForm.getString("UFFINT.CODEIN");
      try {
        if (codein != null && !"".equals(codein.trim())) {
          // La stazione appaltante esiste gia'.
          // Non deve essere inserita alcuna riga in UFFINT ma
          // si tratta solo di associare il nuovo utente creato in USRSYS
          // con la stazione appaltante
        } else {
          // La stazione appaltante non esiste, e' necessario inserirne una nuova
          codein = gene.calcolaCodificaAutomatica("UFFINT", "CODEIN");
          datiForm.setValue("UFFINT.CODEIN", codein);
          datiForm.insert("UFFINT", gene.getSql());
        }
        datiForm.addColumn("USR_EIN.SYSCON", JdbcParametro.TIPO_NUMERICO, new Long(account.getIdAccount()));
        datiForm.addColumn("USR_EIN.CODEIN", JdbcParametro.TIPO_TESTO, codein);
        datiForm.insert("USR_EIN", gene.getSql());
      } catch (SQLException e) {
        throw new GestoreException("Errore durante la registrazione del nuovo utente", REGISTRAZIONE_UTENTE_ERRORE, e);
      }

      // Gestione TECNI
      datiForm.setValue("TECNI.CODTEC", gene.calcolaCodificaAutomatica("TECNI", "CODTEC"));
      datiForm.addColumn("TECNI.NOMTEC", JdbcParametro.TIPO_TESTO, intestazione);
      // Gestione associazione USRSYS - TECNI
      datiForm.setValue("TECNI.SYSCON", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Integer(account.getIdAccount())));
      // Associazione UFFINT - TECNI
      datiForm.addColumn("TECNI.CGENTEI", JdbcParametro.TIPO_TESTO, codein);

    }
    if (logger.isDebugEnabled()) {
      logger.debug("GestoreRegistrazioneUtente-preInsert: fine metodo");
    }

  }

  /**
   * Gestione della comunicazione email.
   * 
   * @param datiForm
   *        Dati della form
   * 
   */
  public void afterInsertEntita(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {

    if (logger.isDebugEnabled()) {
      logger.debug("GestoreRegistrazioneUtente-postInsert: inizio metodo");
    }

    String login = datiForm.getString("USRSYS.SYSNOM");

    LoginManager loginManager = (LoginManager) UtilitySpring.getBean("loginManager",
        this.getServletContext(), LoginManager.class);
    
    try {
      Account account = loginManager.getAccountByLogin(login);
      
      if (StringUtils.isNotEmpty(ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_JNDI_NAME))) {
        this.inviaMailNotificaJndi(account, datiForm);
      } else {
        this.inviaMailNotifica(account, datiForm);
      }
    } catch (CriptazioneException ce) {
      throw new GestoreException("Errore durante la registrazione del nuovo utente",
          REGISTRAZIONE_UTENTE_ERRORE, ce);
    } catch (SqlComposerException sce) {
      throw new GestoreException("Errore durante la registrazione del nuovo utente",
          REGISTRAZIONE_UTENTE_ERRORE, sce);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("GestoreRegistrazioneUtente-postInsert: fine metodo");
    }
  }

  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {

  }

  public void postUpdate(DataColumnContainer datiForm) throws GestoreException {

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

  /**
   * Invio mail notifiche registrazione all'utente e all'amministratore in modalita' JNDI.
   * 
   * @param account
   *        Account.
   * @param datiForm
   *        Dati della form di registrazione.
   * @throws GestoreException
   */
  private void inviaMailNotificaJndi(Account account, DataColumnContainer datiForm) throws GestoreException {
    ByteArrayOutputStream log =  null;
    
    try {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      javax.mail.Session session = (Session) envCtx.lookup(ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_JNDI_NAME));

      if (logger.isDebugEnabled()) {
        session.setDebug(true);
        log = new ByteArrayOutputStream();
        session.setDebugOut(new PrintStream(log));
      }

      // Configurazione velocity
      VelocityEngine velocityEngine = new VelocityEngine();
      String velocityModelPath = ConfigManager.getValore(REGISTRAZIONE_UTENTE_VELOCITY_MODEL_PATH);
      if (StringUtils.isNotEmpty(velocityModelPath.trim())) {
        
        velocityEngine.setProperty("resource.loader", "file");
        velocityEngine.setProperty("file.resource.loader.class", 
            "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        velocityEngine.setProperty("file.resource.loader.path", velocityModelPath);
        velocityEngine.setProperty("file.resource.loader.cache", "false");
        velocityEngine.init();

        // Dati della form di registrazione
        VelocityContext velocityContext = new VelocityContext();
        DataColumn[] colonne = datiForm.getColumns(null, 0);
        if (colonne != null && colonne.length > 0) {
          for (int iColonne = 0; iColonne < colonne.length; iColonne++) {
            if ("T".equals(Character.toString(colonne[iColonne].getTipoCampo()))) {
              String inputValue = colonne[iColonne].getValue().getStringValue();
              velocityContext.put(colonne[iColonne].getTable().getName() + "_" + colonne[iColonne].getName(), convStringHTML(inputValue));
            } else {
              velocityContext.put(colonne[iColonne].getTable().getName() + "_" + colonne[iColonne].getName(), colonne[iColonne].getValue());
            }
          }
        }

        // Dati della stazione appaltante
        String codein = datiForm.getString("UFFINT.CODEIN");
        if (codein != null) {
          List< ? > datiUffint = this.sqlManager.getVector(
              "select nomein, tipoin, viaein, nciein, citein, proein, capein, codcit, telein, faxein, emaiin from uffint where codein = ?",
              new Object[] {codein });
          if (datiUffint != null && datiUffint.size() > 0) {
            velocityContext.put("UFFINT_NOMEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 0).getValue()));
            Long tipoin = (Long) SqlManager.getValueFromVectorParam(datiUffint, 1).getValue();
            if (tipoin != null) {
              String tipoinDescrizione = (String) this.sqlManager.getObject(
                  "select tab1desc from tab1 where tab1cod = ? and tab1tip = ?",
                  new Object[] {"G_044", tipoin });
              velocityContext.put("UFFINT_TIPOIN", convStringHTML(tipoinDescrizione));
            }
            velocityContext.put("UFFINT_VIAEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 2).getValue()));
            velocityContext.put("UFFINT_NCIEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 3).getValue()));
            velocityContext.put("UFFINT_CITEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 4).getValue()));
            velocityContext.put("UFFINT_PROEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 5).getValue()));
            velocityContext.put("UFFINT_CAPEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 6).getValue()));
            velocityContext.put("UFFINT_CODCIT", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 7).getValue()));
            velocityContext.put("UFFINT_TELEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 8).getValue()));
            velocityContext.put("UFFINT_FAXEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 9).getValue()));
            velocityContext.put("UFFINT_EMAIIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 10).getValue()));
          }
        }

        // Lista degli applicativi richiesti
        String[] applicativi = this.getRequest().getParameterValues("applicativi");
        if (applicativi != null) {
          ArrayList<HashMap<String, String>> listaApplicativi = new ArrayList<HashMap<String, String>>();
          for (int iApplicativi = 0; iApplicativi < applicativi.length; iApplicativi++) {
            List< ? > datiWPROFILI = this.sqlManager.getVector("select nome, descrizione from w_profili where cod_profilo = ? and codapp = ?",
                new Object[] {applicativi[iApplicativi], ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE) });
            if (datiWPROFILI != null && datiWPROFILI.size() > 0) {
              HashMap<String, String> hMapApplicativo = new HashMap<String, String>();
              hMapApplicativo.put("cod_profilo", convStringHTML((String) applicativi[iApplicativi]));
              hMapApplicativo.put("nome", convStringHTML((String) SqlManager.getValueFromVectorParam(datiWPROFILI, 0).getValue()));
              hMapApplicativo.put("descrizione", convStringHTML((String) SqlManager.getValueFromVectorParam(datiWPROFILI, 1).getValue()));
              listaApplicativi.add(hMapApplicativo);
            }
          }
          velocityContext.put("listaApplicativi", listaApplicativi);
        }

        // Messaggio per l'amministratore
        String messaggioMail = this.getRequest().getParameter("messaggioMail");
        velocityContext.put("messaggioMail", convStringHTML(messaggioMail));

        // Stato dell'account
        if (new Integer(CostantiDettaglioAccount.ABILITATO).intValue() == account.getUtenteDisabilitato().intValue()) {
          velocityContext.put("statoRegistrazioneUtente", "abilitato");
        } else {
          velocityContext.put("statoRegistrazioneUtente", "disabilitato");
        }

        // Oggetto e testo email di riepilogo per l'utente
        String registrazioneUtenteOggetto = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_UTENTE_OGGETTO);
        String registrazioneUtenteModel = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_UTENTE_VELOCITY_MODEL);
        String indirizzoMailMittente = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_MITTENTE);
        
        if (StringUtils.isNotEmpty(StringUtils.trim(registrazioneUtenteOggetto))
            && StringUtils.isNotEmpty(StringUtils.trim(registrazioneUtenteModel))) {
          
          Template templateUtente = velocityEngine.getTemplate(registrazioneUtenteModel);
          StringWriter writerUtente = new StringWriter();
          templateUtente.merge(velocityContext, writerUtente);
          String registrazioneUtenteTesto = writerUtente.toString();
          
          try {
            Message message = new MimeMessage(session);
            if (StringUtils.isNotEmpty(indirizzoMailMittente)) { 
              // Set mittente della mail
              message.setFrom(new InternetAddress(indirizzoMailMittente));
            }
            // Set destinatario della mail
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(account.getEmail()));
            // Set oggetto della mail
            message.setSubject(registrazioneUtenteOggetto);
            // Set testo mail
            message.setContent(registrazioneUtenteTesto, "text/html");
            message.setSentDate(new Date());
            // Invio della mail tramite sorgente JNDI
            Transport.send(message);

          } catch (MessagingException ms) {
            logger.error("Errore nell'invio della mail di conferma registrazione all'utente "
                + account.getNome() + " (SYSCON=" + account.getIdAccount() + ")", ms);
          }
        } else {
          logger.error("Impossiible inviare la mail di conferma registrazione all'utente "
              + account.getNome() + " (SYSCON=" + account.getIdAccount() + ")"
              + " per la mancata valorizzazione delle property: "
              + REGISTRAZIONE_UTENTE_MAIL_UTENTE_OGGETTO + " e/o "
              + REGISTRAZIONE_UTENTE_MAIL_UTENTE_VELOCITY_MODEL);
        }

        // Oggetto e testo della email di richiesta per l'amministratore
        String registrazioneAmministratoreOggetto = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE_OGGETTO);
        String registrazioneAmministratoreModel = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE_VELOCITY_MODEL);
        // Mail all'amministratore di SitatSA
        String indirizzoMailAmministratore = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE);
        
        if (StringUtils.isNotEmpty(StringUtils.trim(registrazioneAmministratoreOggetto))
            && StringUtils.isNotEmpty(StringUtils.trim(registrazioneAmministratoreModel))
            && StringUtils.isNotEmpty(StringUtils.trim(indirizzoMailAmministratore))) {
          
          Template templateAmministratore = velocityEngine.getTemplate(registrazioneAmministratoreModel);
          StringWriter writerAmministratore = new StringWriter();
          templateAmministratore.merge(velocityContext, writerAmministratore);
          String registrazioneAmministratoreTesto = writerAmministratore.toString();
          
          try {
            Message message = new MimeMessage(session);
            if (StringUtils.isNotEmpty(indirizzoMailMittente)) {
              // Set mittente della mail
              message.setFrom(new InternetAddress(indirizzoMailMittente));
            }
            // Set destinatario della mail
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(indirizzoMailAmministratore));
            // Set oggetto della mail
            message.setSubject(registrazioneAmministratoreOggetto);
            // Set testo mail
            message.setContent(registrazioneAmministratoreTesto, "text/html");
            message.setSentDate(new Date());
            // Invio della mail tramite sorgente JNDI
            Transport.send(message);
            
          } catch (MessagingException ms) {
            logger.error("Errore nell'invio della mail di avvenuta registrazione dell'utente "
                + account.getNome() + " (SYSCON=" + account.getIdAccount() + ")" + " all'amministratore", ms);
          }
        } else {
          logger.error("Impossiible inviare la mail di avvenuta registrazione dell'utente "
              + account.getNome() + " (SYSCON=" + account.getIdAccount() + ")" + " all'amministratore"
              + " per la mancata valorizzazione delle property: "
              + REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE_OGGETTO + " o "
              + REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE_VELOCITY_MODEL + " o "
              + CostantiGenerali.PROP_INDIRIZZO_MAIL_AMMINISTRATORE);
        }
      } else {
        logger.error("Impossibile inviare le mail di conferma registrazione perche' non e' stata configurata "
            + "la cartella contenente i template Velocity");
        //TODO: gestire il caso di non configurazione velocity
        
      }
    } catch (ResourceNotFoundException rnfe) {
      logger.error("Errore nel recupero di messaggi dal resources da parte di Velocity", rnfe);
    } catch (ParseErrorException pee) {
      logger.error("Errore nel parsing di messaggi da parte di Velocity", pee);
    } catch (Exception e) {
      logger.error("Errore inaspettato nell'invio mail durante la registrazione di un utente", e);
    } finally {
      if (logger.isDebugEnabled()) {
        logger.info("JAVAMAIL debug mode ON\n" + log.toString());
        try {
          log.close();
        } catch (IOException e) {
        }
      }
    }
  }

  /**
   * Conversione della stringa per HTML.
   * 
   * @param input
   *        Stringa in input.
   * @return
   */
  public static String convStringHTML(final String input) {
    String result = "";

    if (input != null) {
    	StringTokenizer tokenizer = new StringTokenizer(input, "\u0026<>\"\u20AC\u00E0\u00E8\u00E9\u00EC\u00F2\u00F9", true);
        int tokenCount = tokenizer.countTokens();

        if (tokenCount == 1) {
          result = input.trim();
        } else {
          /*
           * text.length + (tokenCount * 10) Creo il buffer grande in modo da non richiedere un'espansione
           * del buffer che e' molto costosa in termini d'utilizzo del processore
           */
          StringBuffer buffer = new StringBuffer(input.length() + tokenCount * 10);
          while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.length() == 1) {
              switch (token.charAt(0)) {
              case '\u0026':   // Unicode Character 'AMPERSAND' (&)
                buffer.append("&amp;");
                break;
              case '<':
                buffer.append("&lt;");
                break;
              case '>':
                buffer.append("&gt;");
                break;
              case '"':
                buffer.append("&quot;");
                break;
              case '\u00E0':   // Unicode Character 'LATIN SMALL LETTER A WITH GRAVE'
                buffer.append("&agrave;");
                break;
              case '\u00E8':  // Unicode Character 'LATIN SMALL LETTER E WITH GRAVE'
                buffer.append("&egrave;");
                break;
              case '\u00E9':  // Unicode Character 'LATIN SMALL LETTER E WITH ACUTE'
                buffer.append("&eacute;");
                break;
              case '\u00EC':  // Unicode Character 'LATIN SMALL LETTER I WITH GRAVE'
                buffer.append("&igrave;");
                break;
              case '\u00F2':  // Unicode Character 'LATIN SMALL LETTER O WITH GRAVE'
                buffer.append("&ograve;");
                break;
              case '\u00F9':  // Unicode Character 'LATIN SMALL LETTER U WITH GRAVE'
                buffer.append("&ugrave;");
                break;
              case '\u20AC':  // Unicode Character 'EURO SIGN'
                buffer.append("&euro;");
              default:
                buffer.append(token);
              }
            } else {
              buffer.append(token);
            }
          }

          result = buffer.toString().trim();
        }
    }
    
    return result;
  }

  /**
   * Invio mail notifiche registrazione all'utente e all'amministratore in modalita' normale.
   *  
   * @param account
   * @param datiForm
   * @throws GestoreException
   */
  private void inviaMailNotifica(Account account, DataColumnContainer datiForm) throws GestoreException {

    boolean mailSenderExists = false;

    try {
      MailManager mailManager = (MailManager) UtilitySpring.getBean("mailManager",
          this.getServletContext(), MailManager.class);
      IMailSender iMailSender = MailUtils.getInstance(mailManager, 
          ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE),
          CostantiGenerali.PROP_CONFIGURAZIONE_MAIL_STANDARD);

      mailSenderExists = true;

      // Configurazione velocity
      VelocityEngine velocityEngine = new VelocityEngine();
      String velocityModelPath = ConfigManager.getValore(REGISTRAZIONE_UTENTE_VELOCITY_MODEL_PATH);
      if (velocityModelPath != null && !"".equals(velocityModelPath.trim())) {
        velocityEngine.setProperty("resource.loader", "file");
        velocityEngine.setProperty("file.resource.loader.class", 
            "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        velocityEngine.setProperty("file.resource.loader.path", velocityModelPath);
        velocityEngine.setProperty("file.resource.loader.cache", "false");
        velocityEngine.init();

        // Dati della form di registrazione
        VelocityContext velocityContext = new VelocityContext();
        DataColumn[] colonne = datiForm.getColumns(null, 0);
        if (colonne != null && colonne.length > 0) {
          for (int iColonne = 0; iColonne < colonne.length; iColonne++) {
            if ("T".equals(Character.toString(colonne[iColonne].getTipoCampo()))) {
              String inputValue = colonne[iColonne].getValue().getStringValue();
              velocityContext.put(colonne[iColonne].getTable().getName() + "_" + colonne[iColonne].getName(), convStringHTML(inputValue));
            } else {
              velocityContext.put(colonne[iColonne].getTable().getName() + "_" + colonne[iColonne].getName(), colonne[iColonne].getValue());
            }
          }
        }

        // Dati della stazione appaltante
        String codein = datiForm.getString("UFFINT.CODEIN");
        if (codein != null) {
          List< ? > datiUffint = this.sqlManager.getVector(
              "select nomein, tipoin, viaein, nciein, citein, proein, capein, codcit, telein, faxein, emaiin from uffint where codein = ?",
              new Object[] {codein });
          if (datiUffint != null && datiUffint.size() > 0) {
            velocityContext.put("UFFINT_NOMEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 0).getValue()));
            Long tipoin = (Long) SqlManager.getValueFromVectorParam(datiUffint, 1).getValue();
            if (tipoin != null) {
              String tipoinDescrizione = (String) this.sqlManager.getObject(
                  "select tab1desc from tab1 where tab1cod = ? and tab1tip = ?",
                  new Object[] {"G_044", tipoin });
              velocityContext.put("UFFINT_TIPOIN", convStringHTML(tipoinDescrizione));
            }
            velocityContext.put("UFFINT_VIAEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 2).getValue()));
            velocityContext.put("UFFINT_NCIEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 3).getValue()));
            velocityContext.put("UFFINT_CITEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 4).getValue()));
            velocityContext.put("UFFINT_PROEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 5).getValue()));
            velocityContext.put("UFFINT_CAPEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 6).getValue()));
            velocityContext.put("UFFINT_CODCIT", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 7).getValue()));
            velocityContext.put("UFFINT_TELEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 8).getValue()));
            velocityContext.put("UFFINT_FAXEIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 9).getValue()));
            velocityContext.put("UFFINT_EMAIIN", convStringHTML((String) SqlManager.getValueFromVectorParam(datiUffint, 10).getValue()));
          }
        }

        // Lista degli applicativi richiesti
        String[] applicativi = this.getRequest().getParameterValues("applicativi");
        if (applicativi != null) {
          ArrayList<HashMap<String, String>> listaApplicativi = new ArrayList<HashMap<String, String>>();
          for (int iApplicativi = 0; iApplicativi < applicativi.length; iApplicativi++) {
            List< ? > datiWPROFILI = this.sqlManager.getVector("select nome, descrizione from w_profili where cod_profilo = ? and codapp = ?",
                new Object[] {applicativi[iApplicativi], ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE) });
            if (datiWPROFILI != null && datiWPROFILI.size() > 0) {
              HashMap<String, String> hMapApplicativo = new HashMap<String, String>();
              hMapApplicativo.put("cod_profilo", convStringHTML((String) applicativi[iApplicativi]));
              hMapApplicativo.put("nome", convStringHTML((String) SqlManager.getValueFromVectorParam(datiWPROFILI, 0).getValue()));
              hMapApplicativo.put("descrizione", convStringHTML((String) SqlManager.getValueFromVectorParam(datiWPROFILI, 1).getValue()));
              listaApplicativi.add(hMapApplicativo);
            }
          }
          velocityContext.put("listaApplicativi", listaApplicativi);
        }

        // Messaggio per l'amministratore
        String messaggioMail = this.getRequest().getParameter("messaggioMail");
        velocityContext.put("messaggioMail", convStringHTML(messaggioMail));

        // Stato dell'account
        if (new Integer(CostantiDettaglioAccount.ABILITATO).intValue() == account.getUtenteDisabilitato().intValue()) {
          velocityContext.put("statoRegistrazioneUtente", "abilitato");
        } else {
          velocityContext.put("statoRegistrazioneUtente", "disabilitato");
        }

        // Oggetto e testo email di riepilogo per l'utente
        String registrazioneUtenteOggetto = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_UTENTE_OGGETTO);
        String registrazioneUtenteModel = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_UTENTE_VELOCITY_MODEL);

        if (StringUtils.isNotEmpty(StringUtils.trim(registrazioneUtenteOggetto))
            && StringUtils.isNotEmpty(StringUtils.trim(registrazioneUtenteModel))) {
          Template templateUtente = velocityEngine.getTemplate(registrazioneUtenteModel);
          StringWriter writerUtente = new StringWriter();
          templateUtente.merge(velocityContext, writerUtente);
          String registrazioneUtenteTesto = writerUtente.toString();
          iMailSender.send(account.getEmail(), registrazioneUtenteOggetto, registrazioneUtenteTesto, true);
        }

        // Email amministratore
        String indirizzoMailAmministratore = ConfigManager.getValore(CostantiGenerali.PROP_INDIRIZZO_MAIL_AMMINISTRATORE);
        if (StringUtils.isNotEmpty(StringUtils.trim(indirizzoMailAmministratore))) {

          // Oggetto e testo della email di richiesta per l'amministratore
          String registrazioneAmministratoreOggetto = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE_OGGETTO);
          String registrazioneAmministratoreModel = ConfigManager.getValore(REGISTRAZIONE_UTENTE_MAIL_AMMINISTRATORE_VELOCITY_MODEL);

          if (StringUtils.isNotEmpty(StringUtils.trim(registrazioneAmministratoreOggetto))
              && StringUtils.isNotEmpty(StringUtils.trim(registrazioneAmministratoreModel))) {
            Template templateAmministratore = velocityEngine.getTemplate(registrazioneAmministratoreModel);
            StringWriter writerAmministratore = new StringWriter();
            templateAmministratore.merge(velocityContext, writerAmministratore);
            String registrazioneAmministratoreTesto = writerAmministratore.toString();
            iMailSender.send(indirizzoMailAmministratore, registrazioneAmministratoreOggetto, registrazioneAmministratoreTesto, true);
          }
        }
      }
    } catch (MailSenderException ms) {
      String logMessageKey = ms.getChiaveResourceBundle();
      String logMessageError = resBundleGenerale.getString(logMessageKey);
      for (int i = 0; ms.getParametri() != null && i < ms.getParametri().length; i++) {
        logMessageError = logMessageError.replaceAll(UtilityStringhe.getPatternParametroMessageBundle(i), (String) ms.getParametri()[i]);
      }
      logger.error(logMessageError, ms);
      if (!mailSenderExists) {
        logger.error(logMessageError, ms);
      } else {
        logMessageKey = "warnings.registrazione.mancatoInvioMailUtente";
        logMessageError = resBundleGenerale.getString(logMessageKey);
        UtilityStruts.addMessage(this.getRequest(), "warning", logMessageKey, null);
        logger.warn(logMessageError, ms);
      }
    } catch (Exception e) {
      // Modificare per errore Velocity
      e.printStackTrace();
    }
  }

  @Override
  public void postInsert(DataColumnContainer datiForm) throws GestoreException {
  }
  
  
}