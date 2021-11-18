package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.bl.admin.AccountManager;
import it.eldasoft.gene.bl.admin.ProfiliManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.CostantiGeneraliAccount;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreTECNI;
import it.eldasoft.utils.profiles.OpzioniUtente;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.sql.comp.SqlComposerException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionStatus;

public class GestoreUSR_EIN extends AbstractGestoreEntita {

  private AccountManager accountManager;

  public void setRequest(HttpServletRequest request) {
    super.setRequest(request);
    accountManager = (AccountManager) UtilitySpring.getBean("accountManager",
        this.getServletContext(), AccountManager.class);
  }

  @Override
  public String getEntita() {
    return "USR_EIN";
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
    String pkUffint = datiForm.getColumn("USR_EIN.CODEIN").getValue().stringValue();
    Long pkUsrsys = datiForm.getColumn("USR_EIN.SYSCON").getValue().longValue();

    try {
      Long numeroAltreAssociazioniAccount = (Long) this.sqlManager.getObject(
          "select count(syscon) from usr_ein where syscon = ? and codein <> ?",
          new Object[] { pkUsrsys, pkUffint });

      if (numeroAltreAssociazioniAccount.longValue() == 0) {
        // si elimina l'account in USRSYS solo se e' associato alla stazione
        // appaltante in analisi, per cui eliminata l'associazione non sarebbe
        // associato a nient'altro
        this.accountManager.deleteAccountFromDeleteUffint(new Integer(
            pkUsrsys.intValue()));
      }
    } catch (DataAccessException e) {
      throw new GestoreException(
          "Errore durante l'eliminazione dell'account di USRSYS e di tutte le occorrenze nelle tabelle figlie",
          "", e);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore durante la lettura del numero di associazioni dell'account con altre stazioni appaltanti",
          "", e);
    }

    String codute = null;
    try {
      codute = (String) this.sqlManager.getObject(
          "select codtec from tecni where syscon = ? and cgentei = ?",
          new Object[] { pkUsrsys, pkUffint });
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore durante la lettura della chiave del tecnico associato all'utente da " +
          "eliminare dalla stazione appaltante", null, e);
    }
    if ("1".equals(this.getRequest().getParameter("delTecnico")) && codute != null) {
      // si elimina il tecnico solo se comunicato da interfaccia da parte
      // dell'utilizzatore dell'applicativo

      datiForm.addColumn("TECNI.CODTEC", JdbcParametro.TIPO_TESTO, codute);
      datiForm.getColumn("TECNI.CODTEC").setChiave(true);
      GestoreTECNI gestoreTECNI = new GestoreTECNI();
      gestoreTECNI.setRequest(this.getRequest());
      gestoreTECNI.elimina(status, datiForm);
    } else {
      if (codute != null) {
        // se non si elimina il tecnico esistente, allora si sbianca il campo SYSCON
        try {
          this.sqlManager.update(
              "update tecni set syscon = null where codtec = ?",
              new Object[] { codute });
        } catch (SQLException e) {
          throw new GestoreException(
              "Errore durante il reset del campo SYSCON del tecnico associato all'utente da " +
              "eliminare dalla stazione appaltante", null, e);
        }
      }
    }
  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
    int idAccount = -1;
    
	datiForm.addColumn("USRSYS.SYSNOM", JdbcParametro.TIPO_TESTO, 
	datiForm.getString("USRSYS.SYSLOGIN"));
    
    if (datiForm.getLong("USRSYS.SYSCON") != null)
      idAccount = datiForm.getLong("USRSYS.SYSCON").intValue();
    this.checkUnivocitaLogin(datiForm.getString("USRSYS.SYSNOM"), idAccount);

    String sysnom = datiForm.getString("USRSYS.SYSNOM");
    // cifratura della login e della password
    this.cifraDato(datiForm, "USRSYS.SYSNOM",
        "Errore durante la cifratura della login dell'utente");
    if (datiForm.isColumn("USRSYS.SYSPWD"))
      this.cifraDato(datiForm, "USRSYS.SYSPWD",
          "Errore durante la cifratura della password dell'utente");

    // salvataggio della USRSYS
    DefaultGestoreEntitaChiaveNumerica gestoreUSRSYS = new DefaultGestoreEntitaChiaveNumerica(
        "USRSYS", "SYSCON", null, this.getRequest());
    if (datiForm.getLong("USRSYS.SYSCON") == null) {
      // inizializzazione dati USRSYS
      datiForm.addColumn("USRSYS.SYSDAT", JdbcParametro.TIPO_DATA,
          new Timestamp(new Date().getTime()));
      datiForm.addColumn("USRSYS.SYSDISAB", JdbcParametro.TIPO_NUMERICO,
          new Long(0));
      datiForm.addColumn("USRSYS.FLAG_LDAP", JdbcParametro.TIPO_NUMERICO,
          new Long(0));
      datiForm.addColumn("USRSYS.SYSLIV", JdbcParametro.TIPO_NUMERICO,
          new Long(CostantiGeneraliAccount.DEFAULT_LIVELLO_LAVORI));
      datiForm.addColumn("USRSYS.SYSLIG", JdbcParametro.TIPO_NUMERICO,
          new Long(CostantiGeneraliAccount.DEFAULT_LIVELLO_GARE));
      datiForm.addColumn("USRSYS.SYSLIC", JdbcParametro.TIPO_NUMERICO,
          new Long(CostantiGeneraliAccount.DEFAULT_LIVELLO_CONTRATTI));
      datiForm.addColumn("USRSYS.SYSABG", JdbcParametro.TIPO_TESTO,
          CostantiGeneraliAccount.DEFAULT_ABILITAZIONE_GARE);
      datiForm.addColumn("USRSYS.SYSABC", JdbcParametro.TIPO_TESTO,
          CostantiGeneraliAccount.DEFAULT_ABILITAZIONE_CONTRATTI);
      
      DataColumn dcCftec = datiForm.getColumn("TECNI.CFTEC");
      String cftec = dcCftec.getValue().stringValue();
      datiForm.addColumn("USRSYS.SYSCF", JdbcParametro.TIPO_TESTO, cftec);
      
      // set delle opzioni utente: versione snellita tenuto conto della
      // limitatezza in Sitat delle opzioni gestite e del fatto che si e' in
      // inserimento
      String opzUtente = datiForm.getString("OPZ_UTENTE");
      if (opzUtente != null) {
        if (!opzUtente.endsWith("|")) opzUtente += "|";
        datiForm.getColumn("USRSYS.SYSPWBOU").setObjectValue(opzUtente);
      } else {
        datiForm.getColumn("USRSYS.SYSPWBOU").setObjectValue("|");
      }

      gestoreUSRSYS.inserisci(status, datiForm);
      
      // Associazione dell'utente al profilo di default
      boolean gruppiDis = false;
      String gruppiDisabilitati = ConfigManager.getValore(CostantiGenerali.PROP_GRUPPI_DISABILITATI);
      if ("1".equals(gruppiDisabilitati)) gruppiDis = true;
      
      String codiceApplicazione = (String) this.getRequest().getSession().getAttribute(CostantiGenerali.MODULO_ATTIVO);
      String profiloUtenteDefault = ConfigManager.getValore(CostantiGenerali.PROP_PROFILO_DEFAULT_INSERIMENTO);
      
      if (profiloUtenteDefault != null && profiloUtenteDefault.trim().length()>0) {
        // inserisco l'associazione con il profilo di default che gestisce
        // automaticamente l'associazione al gruppi se abilitata
        ProfiliManager profiliManager = (ProfiliManager) UtilitySpring.getBean("profiliManager",
            this.getServletContext(), ProfiliManager.class);
        
        profiliManager.updateAssociazioneProfiliAccount(
            datiForm.getLong("USRSYS.SYSCON").intValue(), new String[] { profiloUtenteDefault },
            codiceApplicazione, gruppiDis);
      }
      
    } else {
      this.updateOU(datiForm);

      gestoreUSRSYS.update(status, datiForm);
    }

    // valorizzazione del SYSCON nell'associativa USR_EIN e nella TECNI
    datiForm.getColumn("USR_EIN.SYSCON").setObjectValue(
        datiForm.getColumn("USRSYS.SYSCON").getValue());
    datiForm.getColumn("TECNI.SYSCON").setObjectValue(
        datiForm.getColumn("USRSYS.SYSCON").getValue());

    // salvataggio della TECNI
    GestoreTECNI gestoreTECNI = new GestoreTECNI();
    gestoreTECNI.setRequest(this.getRequest());
    if (datiForm.getString("TECNI.CODTEC") == null) {
      gestoreTECNI.inserisci(status, datiForm);
    } else {
      gestoreTECNI.update(status, datiForm);
    }
  }

  /**
   * Aggiorna il campo delle opzioni utente nel caso di aggiornamento
   * dell'utente stesso, tenendo conto delle sole opzioni utente gestite
   * dall'applicativo, ovvero ou11 e ou12
   * 
   * @param datiForm
   * @throws GestoreException
   */
  private void updateOU(DataColumnContainer datiForm) throws GestoreException {
    OpzioniUtente ouGlobale = new OpzioniUtente(
        datiForm.getString("USRSYS.SYSPWBOU"));
    // questa e' la parte che controlla l'amministrazione utenti
    OpzioniUtente ouAdminUtenti = new OpzioniUtente(
        datiForm.getString("OPZ_UTENTE"));
    if (ouAdminUtenti.isOpzionePresente("ou11"))
      ouGlobale.setOpzione("ou11");
    else
      ouGlobale.unsetOpzione("ou11");
    if (ouAdminUtenti.isOpzionePresente("ou12"))
      ouGlobale.setOpzione("ou12");
    else
      ouGlobale.unsetOpzione("ou12");
    datiForm.getColumn("USRSYS.SYSPWBOU").setObjectValue(ouGlobale.toString());
  }

  /**
   * Utility di cifratura di un campo del container
   * 
   * @param datiForm
   *        container
   * @param nomeCampo
   *        nome del campo stringa da reperire e da cifrare
   * @param msg
   *        messaggio da inserire nel log in caso di errore
   * @throws GestoreException
   */
  private void cifraDato(DataColumnContainer datiForm, String nomeCampo,
      String msg) throws GestoreException {
    try {
      DataColumn dcLogin = datiForm.getColumn(nomeCampo);
      String login = dcLogin.getValue().stringValue();
      ICriptazioneByte criptLogin = FactoryCriptazioneByte.getInstance(
          FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, login.getBytes(),
          ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);
      dcLogin.setObjectValue(new String(criptLogin.getDatoCifrato()));
    } catch (CriptazioneException e) {
      throw new GestoreException(msg, "", e);
    }
  }

  private void checkUnivocitaLogin(String login, int idAccount)
      throws GestoreException {
    try {
      if (this.accountManager.isUsedLogin(login, idAccount))
        throw new GestoreException(
            "La login e' gia' stata utilizzata per un altro utente",
            "loginDuplicata");
    } catch (CriptazioneException e) {
      throw new GestoreException(
          "Errore durante la decifratura delle login di tutti gli utenti per la verifica se la login e' gia' utilizzata da altri",
          "", e);
    } catch (SqlComposerException sce) {
      throw new GestoreException("Errore durante la determinazione dell'univocita' della login inserita", "", sce);
    }
  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
	  
	
	datiForm.addColumn("USRSYS.SYSNOM", JdbcParametro.TIPO_TESTO, 
	datiForm.getString("USRSYS.SYSLOGIN"));
	
	  	  
    this.checkUnivocitaLogin(datiForm.getString("USRSYS.SYSNOM"),
        datiForm.getLong("USRSYS.SYSCON").intValue());

    // cifratura della login e della password
    this.cifraDato(datiForm, "USRSYS.SYSNOM",
        "Errore durante la cifratura della login dell'utente");

    this.updateOU(datiForm);

    if (datiForm.isModifiedColumn("UTENT.CFTEC")) {
      datiForm.addColumn("USRSYS.SYSCF", JdbcParametro.TIPO_TESTO,
    	datiForm.getString("UTENT.CFTEC"));
    }

    
    // salvataggio della USRSYS
    DefaultGestoreEntitaChiaveNumerica gestoreUSRSYS = new DefaultGestoreEntitaChiaveNumerica(
        "USRSYS", "SYSCON", null, this.getRequest());
    gestoreUSRSYS.update(status, datiForm);

    // salvataggio della TECNI: la si crea se si parte da un utente della USRSYS
    // associato alla SA ma ancora senza tecnico correlato
    GestoreTECNI gestoreTECNI = new GestoreTECNI();
    gestoreTECNI.setRequest(this.getRequest());
    if (datiForm.getString("TECNI.CODTEC") == null) {
      datiForm.getColumn("TECNI.SYSCON").setObjectValue(
          datiForm.getColumn("USRSYS.SYSCON").getValue());
      datiForm.getColumn("TECNI.CGENTEI").setObjectValue(
          datiForm.getColumn("USR_EIN.CODEIN").getValue());
      gestoreTECNI.inserisci(status, datiForm);
    } else {
      gestoreTECNI.update(status, datiForm);
    }

  }
}
