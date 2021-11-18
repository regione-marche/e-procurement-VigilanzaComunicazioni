package it.eldasoft.sil.pt.bl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import it.eldasoft.cup.ws.DatiCUP;
import it.eldasoft.cup.ws.EsitoConsultaCUP;
import it.eldasoft.cup.ws.EsitoInserisciCUP;
import it.eldasoft.cup.ws.OperazioneType;
import it.eldasoft.cup.ws.StazioneAppaltanteType;
import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.LoginManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.bl.system.LdapManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.CommunicationException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class EldasoftCUPWSManager {

  static Logger              logger = Logger.getLogger(EldasoftCUPWSManager.class);

  private SqlManager         sqlManager;

  protected GenChiaviManager genChiaviManager;

  protected LoginManager     loginManager;

  private LdapManager        ldapManager;

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  public void setGenChiaviManager(GenChiaviManager genChiaviManager) {
    this.genChiaviManager = genChiaviManager;
  }

  public void setLoginManager(LoginManager loginManager) {
    this.loginManager = loginManager;
  }

  public void setLdapManager(LdapManager ldapManager) {
    this.ldapManager = ldapManager;
  }

  /**
   * Inserimento CUP
   * 
   * @param login
   * @param password
   * @param datiCUP
   * @throws Exception
   */
  public EsitoInserisciCUP inserisciCUP(java.lang.String login, java.lang.String password, java.lang.String codein, DatiCUP datiCUP) {
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.inserisciCUP: inizio metodo");
    EsitoInserisciCUP esitoInserisciCUP = new EsitoInserisciCUP();
    try {
      // Ricavo l'identificativo dell'utente
      Long idAccountRemoto = new Long(this.getIdAccount(login, password));

      // Controllo n. 1: verifico l'esistenza della stazione appaltante
      Long conteggioUFFINT = (Long) sqlManager.getObject("select count(*) from uffint where codein = ?", new Object[] { codein });
      if (conteggioUFFINT != null && conteggioUFFINT.longValue() > 0) {
        // Controllo n. 2: verifico che la stazione appaltante sia associata
        // all'utente indicato
        Long conteggioUSE_EIN = (Long) sqlManager.getObject("select count(*) from usr_ein where syscon = ? and codein = ?", new Object[] {
            idAccountRemoto, codein });
        if (conteggioUSE_EIN != null && conteggioUSE_EIN.longValue() > 0) {
          esitoInserisciCUP = this.gestioneCUPDATI(idAccountRemoto, codein, datiCUP);
        } else {
          esitoInserisciCUP.setEsito(false);
          esitoInserisciCUP.setMessaggio(UtilityTags.getResource("errors.gestioneCUP.stazioneAppaltanteNonAssociata", null, false));
        }
      } else {
        esitoInserisciCUP.setEsito(false);
        esitoInserisciCUP.setMessaggio(UtilityTags.getResource("errors.gestioneCUP.stazioneAppaltanteInesistente", null, false));
      }
    } catch (Exception e) {
      esitoInserisciCUP.setEsito(false);
      esitoInserisciCUP.setMessaggio(e.getMessage());
    }
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.inserisciCUP: fine metodo");
    return esitoInserisciCUP;
  }

  /**
   * Consultazione CUP
   * 
   * @param uuid
   * @return
   */
  public EsitoConsultaCUP consultaCUP(java.lang.String uuid) {
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.consultaCUP: inizio metodo");
    EsitoConsultaCUP esitoConsultaCUP = new EsitoConsultaCUP();
    try {
      String cup = null;
      Long conteggio = (Long) sqlManager.getObject("select count(*) from cupdati where uuid = ?", new Object[] { uuid });
      if (conteggio != null && conteggio.longValue() > 0) {
        cup = (String) sqlManager.getObject("select cup from cupdati where uuid = ?", new Object[] { uuid });
        if (cup == null) {
          // CUP non ancora assegnato
          logger.error("Per il progetto indicato (UUID: " + uuid + ") non e' ancora stato assegnato il codice CUP");
          throw new Exception(UtilityTags.getResource("errors.consultaCUP.CUPnonAssegnato", null, false));
        } else {
          // CUP assegnato
          esitoConsultaCUP.setEsito(true);
          esitoConsultaCUP.setCup(cup);
        }
      } else {
        // Progetto non esistente
        logger.error("Il progetto indicato (UUID: " + uuid + ") non e' presente in base dati");
        throw new Exception(UtilityTags.getResource("errors.consultaCUP.UUIDnonPresente", null, false));
      }
    } catch (SQLException e) {
      esitoConsultaCUP.setEsito(false);
      esitoConsultaCUP.setMessaggio("Errore di database durante la consultazione del codice CUP");
    } catch (Exception e) {
      esitoConsultaCUP.setEsito(false);
      esitoConsultaCUP.setMessaggio(e.getMessage());
    }
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.consultaCUP: fine metodo");
    return esitoConsultaCUP;
  }

  /**
   * Restituisce la lista delle stazioni appaltanti presenti nella tabella
   * UFFINT
   * 
   * @return
   */
  public StazioneAppaltanteType[] listaStazioniAppaltanti() {
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.listaStazioniAppaltanti: inizio metodo");

    StazioneAppaltanteType[] stazioniAppaltanti = null;

    try {
      List<?> datiUFFINT = sqlManager.getListVector("select codein, nomein from uffint", new Object[] {});
      if (datiUFFINT != null && datiUFFINT.size() > 0) {
        stazioniAppaltanti = new StazioneAppaltanteType[datiUFFINT.size()];
        for (int i = 0; i < datiUFFINT.size(); i++) {
          stazioniAppaltanti[i] = new StazioneAppaltanteType();
          String codein = (String) SqlManager.getValueFromVectorParam(datiUFFINT.get(i), 0).getValue();
          String nomein = (String) SqlManager.getValueFromVectorParam(datiUFFINT.get(i), 1).getValue();
          stazioniAppaltanti[i].setCodein(codein);
          stazioniAppaltanti[i].setNomein(nomein);
        }
      }
    } catch (Throwable e) {

    }

    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.listaStazioniAppaltanti: fine metodo");

    return stazioniAppaltanti;

  }

  /**
   * Gestione dati CUP
   * 
   * @param idAccountRemoto
   * @param datiCUP
   * @throws GestoreException
   */
  private EsitoInserisciCUP gestioneCUPDATI(Long idAccountRemoto, String codein, DatiCUP datiCUP) throws GestoreException {
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.gestioneCUPDATI: inizio metodo");

    EsitoInserisciCUP esitoInserisciCUP = new EsitoInserisciCUP();
    try {
      boolean esisteCUPDATI = this.esisteCUPDATI(datiCUP.getUuid());
      Long id = null;
      if (!esisteCUPDATI) {
        id = this.getID_Nextval();
        DataColumnContainer dccCUPDATI = new DataColumnContainer(new DataColumn[] { new DataColumn("CUPDATI.ID", new JdbcParametro(
            JdbcParametro.TIPO_NUMERICO, id)) });

        // Utente proprietario
        dccCUPDATI.addColumn("CUPDATI.SYSCON", JdbcParametro.TIPO_NUMERICO, idAccountRemoto);

        // Stazione appaltante
        dccCUPDATI.addColumn("CUPDATI.CODEIN", JdbcParametro.TIPO_TESTO, codein);

        // Identificativo univoco universale
        dccCUPDATI.addColumn("CUPDATI.UUID", datiCUP.getUuid());

        // Preparazione delle colonne
        dccCUPDATI.addColumn("CUPDATI.DESCRIZIONE_INTERVENTO", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.DENOMINAZIONE_PROGETTO", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.NOME_STRU_INFRASTR", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.ANNO_DECISIONE", JdbcParametro.TIPO_NUMERICO, null);
        dccCUPDATI.addColumn("CUPDATI.NATURA", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.TIPOLOGIA", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.SETTORE", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.SOTTOSETTORE", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.CATEGORIA", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.CPV", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.CUP_MASTER", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.SPONSORIZZAZIONE", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.FINANZA_PROGETTO", JdbcParametro.TIPO_TESTO, null);
        dccCUPDATI.addColumn("CUPDATI.COSTO", JdbcParametro.TIPO_DECIMALE, null);
        dccCUPDATI.addColumn("CUPDATI.FINANZIAMENTO", JdbcParametro.TIPO_DECIMALE, null);

        // Valorizzazione delle colonne di dccCUPDATI
        this.setValueCUPDATI(dccCUPDATI, datiCUP);

        // Inserimento
        dccCUPDATI.insert("CUPDATI", this.sqlManager);

        // Esito
        esitoInserisciCUP.setEsito(true);
        esitoInserisciCUP.setTipoOperazione(OperazioneType.INS);

      } else {
        id = this.getID_UUID(datiCUP.getUuid());

        // Controllo n.1: i nuovi dati devono appartenere all'utente che
        // originariamente li ha inviati
        Long syscon = (Long) sqlManager.getObject("select syscon from cupdati where id = ?", new Object[] { id });
        if (syscon.longValue() != idAccountRemoto) {
          logger.error("Il progetto (ID: "
              + id.toString()
              + ") che si sta tentando di aggiornare e' di proprieta' di un altro utente: non e' possibile procedere nell'aggiornamento");
          throw new GestoreException(
              "Il progetto che si sta tentando di aggiornare e' di proprieta' di un altro utente: non e' possibile procedere nell'aggiornamento",
              "errors.aggiornaCUP.SYSCONDifferente", null);
        }

        // Controllo n.2: il CUP non deve essere gia' stato assegnato
        Date data_definitivo = (Date) sqlManager.getObject("select data_definitivo from cupdati where id = ?", new Object[] { id });
        if (data_definitivo != null) {
          logger.error("Il progetto (ID: "
              + id.toString()
              + ") che si sta tentando di aggiornare e' gia' stato assegnato: non e' possibile procedere nell'aggiornamento");
          throw new GestoreException(
              "Il progetto che si sta tentando di aggiornare e' gia' stato assegnato: non e' possibile procedere nell'aggiornamento",
              "errors.aggiornaCUP.CUPAssegnato", null);
        }

        DataColumnContainer dccCUPDATI = new DataColumnContainer(this.sqlManager, "CUPDATI", "select * from cupdati where id = ?",
            new Object[] { id });
        // Valorizzazione delle colonne di dccCUPDATI
        this.setValueCUPDATI(dccCUPDATI, datiCUP);
        if (dccCUPDATI.isModifiedTable("CUPDATI")) {
          dccCUPDATI.getColumn("CUPDATI.ID").setChiave(true);
          dccCUPDATI.setValue("CUPDATI.ID", id);
          dccCUPDATI.setOriginalValue("CUPDATI.ID", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, id));
          dccCUPDATI.update("CUPDATI", this.sqlManager);
        }
        // Esito
        esitoInserisciCUP.setEsito(true);
        esitoInserisciCUP.setTipoOperazione(OperazioneType.UPD);
      }
    } catch (SQLException e) {
      throw new GestoreException("Errore di database nella gestione dei dati del CUP", "errors.gestioneCUP.sqlerror", e);
    }

    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.gestioneCUPDATI: fine metodo");

    return esitoInserisciCUP;
  }

  /**
   * Aggiornamento dati CUP
   * 
   * @param dccCUPDATI
   * @param datiCUP
   * @throws GestoreException
   * @throws SQLException
   */
  private void setValueCUPDATI(DataColumnContainer dccCUPDATI, DatiCUP datiCUP) throws GestoreException {
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.aggiornaCUPDATI: inizio metodo");

    try {
      dccCUPDATI.setValue("CUPDATI.DESCRIZIONE_INTERVENTO", datiCUP.getDescrizione_intervento());
      dccCUPDATI.setValue("CUPDATI.DENOMINAZIONE_PROGETTO", datiCUP.getDescrizione_intervento());
      dccCUPDATI.setValue("CUPDATI.NOME_STRU_INFRASTR", datiCUP.getDescrizione_intervento());
      if (datiCUP.getAnno_decisione() != null) {
        dccCUPDATI.setValue("CUPDATI.ANNO_DECISIONE", datiCUP.getAnno_decisione().longValue());
      }
      if (datiCUP.getNatura() != null) {
        String natura = datiCUP.getNatura().getValue();
        dccCUPDATI.setValue("CUPDATI.NATURA", natura);
        if (datiCUP.getTipologia() != null) {
          String tipologia = datiCUP.getTipologia().getValue();
          if (this.isTipologiaCorretta(natura, tipologia)) {
            dccCUPDATI.setValue("CUPDATI.TIPOLOGIA", tipologia);
          } else {
            throw new GestoreException("Tipologia non compatibile con la natura del progetto", "errors.gestioneCUP.controlloTipologia",
                null);
          }
        }
      }

      if (datiCUP.getSettore() != null) {
        String settore = datiCUP.getSettore().getValue();
        dccCUPDATI.setValue("CUPDATI.SETTORE", settore);
        if (datiCUP.getSottosettore() != null) {
          String sottosettore = datiCUP.getSottosettore().getValue();
          if (this.isSottosettoreCorretto(settore, sottosettore)) {
            dccCUPDATI.setValue("CUPDATI.SOTTOSETTORE", sottosettore);
            if (datiCUP.getCategoria() != null) {
              String categoria = datiCUP.getCategoria().getValue();
              if (this.isCategoriaCorretta(settore, sottosettore, categoria)) {
                dccCUPDATI.setValue("CUPDATI.CATEGORIA", categoria);
              } else {
                throw new GestoreException("Categoria non compatibile con il sottosettore del progetto",
                    "errors.gestioneCUP.controlloCategoria", null);
              }
            }
          } else {
            throw new GestoreException("Sottosettore non compatibile con il settore del progetto",
                "errors.gestioneCUP.controlloSottosettore", null);
          }
        }
      }

      dccCUPDATI.setValue("CUPDATI.CPV", datiCUP.getCpv());
      dccCUPDATI.setValue("CUPDATI.CUP_MASTER", datiCUP.getCup_master());
      if (datiCUP.getSponsorizzazione() != null) {
        dccCUPDATI.setValue("CUPDATI.SPONSORIZZAZIONE", datiCUP.getSponsorizzazione().getValue());
      }
      if (datiCUP.getFinanza_progetto() != null) {
        dccCUPDATI.setValue("CUPDATI.FINANZA_PROGETTO", datiCUP.getFinanza_progetto().getValue());
      }
      if (datiCUP.getCosto() != null) {
        dccCUPDATI.setValue("CUPDATI.COSTO", datiCUP.getCosto());
      }
      if (datiCUP.getFinanziamento() != null) {
        dccCUPDATI.setValue("CUPDATI.FINANZIAMENTO", datiCUP.getFinanziamento());
      }
    } catch (SQLException e) {

    }

    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.aggiornaCUPDATI: fine metodo");
  }

  /**
   * 
   * @param settore
   * @param sottosettore
   * @param categoria
   * @return
   * @throws SQLException
   */
  private boolean isCategoriaCorretta(String settore, String sottosettore, String categoria) throws SQLException {
    boolean isCategoriaCorretta = false;
    Long countCategoria = (Long) sqlManager.getObject(
        "select count(*) from cuptab where cupcod = ? and cupcod1 = ? and cupcod2 = ? and cupcod3 = ?", new Object[] { "CU002", settore,
            sottosettore, categoria });
    if (countCategoria != null && countCategoria.longValue() > 0) {
      isCategoriaCorretta = true;
    }
    return isCategoriaCorretta;
  }

  /**
   * Controllo correttezza sottosettore in funzione del settore
   * 
   * @param settore
   * @param sottosettore
   * @return
   * @throws SQLException
   */
  private boolean isSottosettoreCorretto(String settore, String sottosettore) throws SQLException {
    boolean isSettoreCorretto = false;
    Long countSettore = (Long) sqlManager.getObject(
        "select count(*) from cuptab where cupcod = ? and cupcod1 = ? and cupcod2 = ? and cupcod3 = ?", new Object[] { "CU002", settore,
            sottosettore, "000" });
    if (countSettore != null && countSettore.longValue() > 0) {
      isSettoreCorretto = true;
    }
    return isSettoreCorretto;
  }

  /**
   * Controllo correttezza tipologia in funzione della natura
   * 
   * @param natura
   * @param tipologia
   * @return
   * @throws SQLException
   */
  private boolean isTipologiaCorretta(String natura, String tipologia) throws SQLException {
    boolean isTipologiaCorretta = false;
    Long countTipologia = (Long) sqlManager.getObject("select count(*) from cuptab where cupcod = ? and cupcod1 = ? and cupcod2 = ?",
        new Object[] { "CU001", natura, tipologia });
    if (countTipologia != null && countTipologia.longValue() > 0) {
      isTipologiaCorretta = true;
    }
    return isTipologiaCorretta;
  }

  /**
   * Calcolo il valore della chiave
   * 
   * @return
   * @throws GestoreException
   */
  private Long getID_Nextval() throws GestoreException {
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.getID_Nextval: inizio metodo");
    Long id = new Long(this.genChiaviManager.getMaxId("CUPDATI", "ID") + 1);
    Calendar cal = new GregorianCalendar();
    long millisecondi = cal.get(Calendar.MILLISECOND);
    id = new Long(id.longValue() + millisecondi);
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.getID_Nextval: fine metodo");
    return id;
  }

  /**
   * Controllo esistenza della riga in CUPDATI sulla base del codice univoco
   * universale UUID
   * 
   * @param uuid
   * @return
   * @throws GestoreException
   */
  private boolean esisteCUPDATI(String uuid) throws GestoreException {
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.esisteCUPDATI: inizio metodo");
    boolean esiste = false;
    try {
      Long conteggio = (Long) sqlManager.getObject("select count(*) from cupdati where uuid = ?", new Object[] { uuid });
      if (conteggio != null && conteggio.longValue() > 0) esiste = true;
    } catch (SQLException e) {
      throw new GestoreException("Errore durante la lettura della tabella CUPDATI", "errors.gestioneCUP.controlloCUPDATI", e);
    }
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.esisteCUPDATI: fine metodo");
    return esiste;
  }

  /**
   * Ricava il campo chiave CUPDATI.ID sulla base del valore di UUID
   * 
   * @param uuid
   * @return
   * @throws GestoreException
   */
  private Long getID_UUID(String uuid) throws GestoreException {
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.getID_UUID: inizio metodo");
    Long id = null;
    try {
      id = (Long) sqlManager.getObject("select id from cupdati where uuid = ?", new Object[] { uuid });
    } catch (SQLException e) {
      throw new GestoreException("Errore durante la lettura della tabella CUPDATI", "errors.gestioneCUP.controlloCUPDATI", e);
    }
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.getID_UUID: fine metodo");
    return id;
  }

  /**
   * Procedura di autenticazione. Restituisce l'idAccount. Serve per la
   * memorizzazione del proprietario della gara inserita
   * 
   * @param login
   * @param password
   * @return
   */
  private int getIdAccount(String login, String password) throws Exception {
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.getIdAccount: inizio metodo");
    if ("".equals(password)) password = null;
    Account account = null;
    try {
      if (login == null && password == null) {
        throw new Exception(UtilityTags.getResource("errors.login.mancanoCredenziali", null, false));
      }
      account = this.loginManager.getAccountByLoginEPassword(login, password);
      if (account != null) {
        if (account.getFlagLdap().intValue() == 1) {
          if (password == null && account.getPassword() == null) {
          } else {
            ldapManager.verificaAccount(account.getDn(), password);
          }
        }
      } else {
        throw new Exception(UtilityTags.getResource("errors.login.unknown", null, false));
      }
    } catch (CriptazioneException cr) {
      throw new Exception("Errore di autenticazione", cr);
    } catch (DataAccessException d) {
      throw new Exception(UtilityTags.getResource("errors.database.dataAccessException", null, false), d);
    } catch (AuthenticationException a) {
      throw new Exception(UtilityTags.getResource("errors.ldap.autenticazioneFallita", null, false), a);
    } catch (CommunicationException c) {
      throw new Exception(UtilityTags.getResource("errors.ldap.autenticazioneFallita", null, false), c);
    } catch (RuntimeException r) {
      throw new Exception(UtilityTags.getResource("errors.login.loginDoppia", null, false), r);
    } catch (Exception e) {
      throw e;
    } catch (Throwable t) {
      throw new Exception(UtilityTags.getResource("errors.applicazione.inaspettataException", null, false), t);
    }
    if (logger.isDebugEnabled()) logger.debug("EldasoftCUPWSManager.getIdAccount: fine metodo");
    return account.getIdAccount();
  }

}
