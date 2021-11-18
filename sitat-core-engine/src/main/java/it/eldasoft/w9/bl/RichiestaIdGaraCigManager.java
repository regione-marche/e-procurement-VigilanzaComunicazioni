/*
 * Created on 17/mar/2010
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */

package it.eldasoft.w9.bl;

import it.avlp.simog.massload.xmlbeans.CPVSecondariaType;
import it.avlp.simog.massload.xmlbeans.ChiudiSessione;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneResponseDocument;
import it.avlp.simog.massload.xmlbeans.Collaborazione;
import it.avlp.simog.massload.xmlbeans.Collaborazioni;
import it.avlp.simog.massload.xmlbeans.ConsultaGara;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaNumeroGara;
import it.avlp.simog.massload.xmlbeans.ConsultaNumeroGaraDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaNumeroGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.GaraType;
import it.avlp.simog.massload.xmlbeans.Login;
import it.avlp.simog.massload.xmlbeans.LoginDocument;
import it.avlp.simog.massload.xmlbeans.LoginResponse;
import it.avlp.simog.massload.xmlbeans.LoginResponseDocument;
import it.avlp.simog.massload.xmlbeans.LottoType;
import it.avlp.simog.massload.xmlbeans.ResponseCheckLogin;
import it.avlp.simog.massload.xmlbeans.ResponseChiudiSession;
import it.avlp.simog.massload.xmlbeans.ResponseConsultaGara;
import it.avlp.simog.massload.xmlbeans.SchedaGaraCig;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.avlp.smartCig.comunicazione.ComunicazioneType;
import it.avlp.smartCig.risultato.RisultatoType;
import it.avlp.smartCig.user.LoggedUserInfoType;
import it.avlp.smartCig.ws.ConsultaComunicazioneRequest;
import it.avlp.smartCig.ws.ConsultaComunicazioneResponse;
import it.avlp.smartCig.ws.Services;
import it.avlp.smartCig.ws.ServicesServiceLocator;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.w9.bl.simog.AccessoSimogManager;
import it.eldasoft.w9.bl.simog.TicketSimogBean;
import it.eldasoft.w9.bl.simog.TicketSimogManager;
import it.eldasoft.w9.common.CostantiSimog;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.W3Z05Type;
import it.toscana.rete.rfc.sitatsa.client.WsOsservatorioProxy;
import it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_PortType;
import it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_ServiceLocator;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

/**
 * Classe di utilita' per la lettura dell'identificativo della gara (IDGARA) e
 * dei lotti (CIG).
 * 
 * @author Stefano.Cestaro
 * 
 */
public class RichiestaIdGaraCigManager {

  /**
   * Logger di classe.
   */
  private static Logger logger = Logger.getLogger(RichiestaIdGaraCigManager.class);

  /**   Manager per le transazioni SQL.   */
  private SqlManager sqlManager;
  
  /** Manager del ticket per l'accesso ai servizi Simog in configurazione Osservatorio */
  private TicketSimogManager ticketSimogManager;
  
  /** Manager per l'accesso ai servizi Simog in configurazione Vigilanza */
  private AccessoSimogManager accessoSimogManager;  
  
  /** Manager per l'importazione dell'anagrafica gara-lotti */
  private ConsultaGaraUnificatoManager consultaGaraUnificatoManager;
  
  /**
   * Set sqlManager.
   * 
   * @param sqlManager the sqlManager to set
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
  
  /**
   * Set TicketSimogManager.
   * @param ticketSimogManager
   */
  public void setTicketSimogManager(TicketSimogManager ticketSimogManager) {
    this.ticketSimogManager = ticketSimogManager;
  }
  
  /**
   * Set consultaGaraUnificatoManager.
   * 
   * @param consultaGaraUnificatoManager
   *        consultaGaraUnificatoManager da settare internamente alla classe.
   */
  public void setConsultaGaraUnificatoManager(ConsultaGaraUnificatoManager consultaGaraUnificatoManager) {
    this.consultaGaraUnificatoManager = consultaGaraUnificatoManager;
  }
  
  /**
   * Set accessoSimogManager.
   * 
   * @param accessoSimogManager
   *        accessoSimogManager da settare internamente alla classe.
   */
  public void setAccessoSimogManager(AccessoSimogManager accessoSimogManager) {
    this.accessoSimogManager = accessoSimogManager;
  }
  
  /**
   * Consulta gara, per scaricare la lista dei CIG della gara indicata. La lista dei CIG viene salvata
   * nell'attributo <i>listaCIG</i> dell'oggetto <i>consultaGaraLottiBean</i>
   * 
   * @param idAvGara IDAVGARA
   * @param codUffInt codice ufficio intestatario
   * @param syscon SYSCON della USRSYS
   * @param simoguser se valorizzato la chiamata ai servizi Simog sarà fatta con questo utente
   * @param simogpass se valorizzato la chiamata ai servizi Simog sarà fatta con questa password
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException SQLException
   */
  public HashMap<String, Object> consultaGara(final String idAvGara, final String codUffInt, final int syscon,
      ConsultaGaraLottiBean consultaGaraLottiBean, final String simoguser, final String simogpass)
          throws SQLException, GestoreException, CriptazioneException {

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGara: inizio metodo");
    }
    
    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    
    // Valore di default per il tipo di accesso ai servizi Simog
    int tipoAccessoWSSimog = 0;

    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
    
    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoWSSimog = Integer.parseInt(a);
    }
    
    switch (tipoAccessoWSSimog) {
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
      this.consultaGaraAccessoDiretto(idAvGara, codUffInt, syscon, resultMap, consultaGaraLottiBean, simoguser, simogpass);
      break;
      
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS:
      this.consultaGaraAccessoIndiretto(idAvGara, codUffInt, syscon, resultMap, consultaGaraLottiBean);
      break;
    
    default:
      String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
          "indicato il tipo di accesso al WS Simog per la funzione 'Consulta gara'";
      logger.error(msgError);
      
      resultMap.put("esito", Boolean.FALSE);
      resultMap.put("errorMsg", msgError + ". Contattare un amministratore");
      break;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGara: fine metodo");
    }
    return resultMap;
  }
  
  
  /**
   * Consulta lotto. Il <i>codiceCIG</> deve essere valorizzato.
   * 
   * @param codiceCIG codice CIG
   * @param idAvGara IDAVGARA
   * @param codUffInt codice ufficio intestatario
   * @param syscon SYSCON della USRSYS
   * @param simoguser se valorizzato la chiamata ai servizi Simog sarà fatta con questo utente
   * @param simogpass se valorizzato la chiamata ai servizi Simog sarà fatta con questa password
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException SQLException
   */
  public HashMap<String, Object> consultaLotto(final String codiceCIG, final String idAvGara,
      final String codUffInt, final int syscon, final String simoguser, final String simogpass)
          throws SQLException, GestoreException, CriptazioneException {

    if (logger.isDebugEnabled()) {
      logger.debug("consultaLotto: inizio metodo");
    }
    
    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    
    // Valore di default per il tipo di accesso ai servizi Simog
    int tipoAccessoWSSimog = 0;

    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
    
    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoWSSimog = Integer.parseInt(a);
    }
    
    switch (tipoAccessoWSSimog) {
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
    //case CostantiSimog.SA_CONSULTA_GARA_ACCESSO:
      this.consultaLottoAccessoDiretto(codiceCIG, idAvGara, codUffInt, syscon, resultMap, simoguser, simogpass, true);
      break;
      
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS:
    	//aggiungo alla resultMap simoguser, simogpass da passare al servizio SitatOR come credenziali del RUP per recupare un eventuale smartcig
    	if (simoguser != null) {
    		resultMap.put("simoguser", simoguser);
       	 	resultMap.put("simogpass", simogpass);
    	}
    	this.consultaLottoAccessoIndiretto(codiceCIG, idAvGara, codUffInt, syscon, resultMap);
      break;
    
    default:
      String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
      		"indicato il tipo di accesso al WS Simog per la funzione 'Consulta gara'";
      logger.error(msgError);
      
      resultMap.put("esito", Boolean.FALSE);
      resultMap.put("errorMsg", msgError + ". Contattare un amministratore");
      break;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultaLotto: fine metodo");
    }
    return resultMap;
  }
 
  /**
   * Riallinea dati SIMOG.
   * 
   * @param codiceCIG codice CIG
   * @param codGara codice della gara
   * @param codUffInt codice ufficio intestatario
   * @param syscon SYSCON della USRSYS
   * @param simoguser se valorizzato la chiamata ai servizi Simog sara' fatta con questo utente
   * @param simogpass se valorizzato la chiamata ai servizi Simog sara' fatta con questa password
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException SQLException
   */
  public HashMap<String, Object> riallineaDatiSimog(final String codiceCIG, final long codGara,
      final String codUffInt, final int syscon, final String simoguser, final String simogpass) 
          throws SQLException {

    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimog: inizio metodo");
    }

    HashMap<String, Object> resultMap = new HashMap<String, Object>();

    int tipoAccessoWSSimog = 0;

    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);

    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoWSSimog = Integer.parseInt(a);
    }
    
    switch (tipoAccessoWSSimog) {
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
      this.riallineaDatiSimogAccessoDiretto(codiceCIG, codGara, codUffInt, syscon, resultMap, simoguser, simogpass);
      break;
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS:
      this.riallineaDatiSimogAccessoIndiretto(codiceCIG, codGara, codUffInt, syscon, resultMap);
      break;
    
    default:
      String msgError = "Errore nella configurazione dell'applicazione: non e' stato "
        + "indicato il tipo di accesso al servizio Simog per la funzione 'Consulta gara'";
      logger.error(msgError);
      
      resultMap.put("esito", Boolean.FALSE);
      resultMap.put("errorMsg", msgError + ". Contattare un amministratore");
      break;
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimog: fine metodo");
    }
    return resultMap;
  }
  
  /**
   * Metodo per determinare l'esistenza di un lotto con codice CIG specificato.
   * 
   * @param codiceCIG Codice CIG
   * @return Ritorna true se esiste il lotto con codice CIG specificato, false altrimenti
   * @throws SQLException SQLException
   */
  public boolean getEsistenzaCodiceCIG(final String[] codiceCIG) throws SQLException {
    if (logger.isDebugEnabled()) {
      logger.debug("getEsistenzaCodiceCIG: inizio metodo");
    }
    boolean result = false;

    if (codiceCIG != null && codiceCIG.length > 0) {
      StringBuffer strBuf = new StringBuffer("'");
      try {
        for (int i = 0; i < codiceCIG.length; i++) {
          if (StringUtils.isNotEmpty(codiceCIG[i])) {
            strBuf.append(codiceCIG[i]);
            if (i + 1 == codiceCIG.length) {
              strBuf.append("'");
            } else {
              strBuf.append("', '");
            }
          }
        }
        
        Long numeroOccorrenze = null;
        if (strBuf.length() > 1) {
          numeroOccorrenze = (Long) this.sqlManager.getObject(
              StringUtils.replace("select COUNT(*) from W9LOTT where CIG in (##)",
                  "##", strBuf.toString()), null);
        }
        if (numeroOccorrenze != null && numeroOccorrenze.longValue() > 0) {
          result = true;
        }
      } catch (SQLException e) {
        logger.error("Errore nel determinare l'esistenza di un lotto con codice CIG " +
            "specificato (CIG=" + strBuf.toString() + ")", e);
        throw e;
      }
    }
    if (logger.isDebugEnabled()) {
      logger.debug("getEsistenzaCodiceCIG: fine metodo");
    }
    return result;
  }

  /**
   * Metodo per determinare l'esistenza di una gara con codice gara specificato
   * (per codice gara si intende il campo IDAVGARA.W9GARA).
   * 
   * @param idGara Codice della gara di ritorno da SIMOG
   * @return Ritorna true se esiste la gara con codice specificato, false altrimenti
   * @throws SQLException SQLException
   */
  public boolean isGaraEsistente(final String idGara)
      throws SQLException {
    if (logger.isDebugEnabled()) {
      logger.debug("isGaraEsistente: inizio metodo");
    }
    
    boolean result = false;
    
    try {
      Long numeroOccorrenze = (Long) this.sqlManager.getObject (
          "select COUNT(CODGARA) from W9GARA where IDAVGARA=?",
          new Object[] { "" + idGara });
    
      if (numeroOccorrenze != null && numeroOccorrenze.longValue() > 0) {
        result = true;
      }
    } catch (SQLException e) {
      logger.error("Errore nel determinare l'esistenza di una gara con codice gara "
          + "specificato (IDAVGARA = '" + idGara + "')", e);
      throw e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("isGaraEsistente: fine metodo");
    }
    return result;
  }
  
  /**
   * Metodo per determinare il numero di lotti per una gara con codice gara specificato
   * (per codice gara si intende il campo IDAVGARA.W9GARA) presente nel database.
   * 
   * @param idGara Codice della gara di ritorno da SIMOG
   * @return Ritorna numero di lotti presenti per una gara
   * @throws SQLException SQLException
   */
	public Long countLottifromIdGara(final String idGara) throws SQLException {

		if (logger.isDebugEnabled()) {
			logger.debug("countLottifromIdGara: inizio metodo");
		}

		Long numeroLotti = new Long(0);
		String countLottiString = "select count(w9lott.codlott) from w9lott " 
			+ "join w9gara on w9lott.codgara = w9gara.codgara  where w9gara.idavgara = ? ";

		try {
			numeroLotti = (Long) this.sqlManager.getObject(
					countLottiString, new Object[] { "" + idGara });
		} catch (SQLException e) {
			logger.error("Errore nel determinare il numero di lotti per la gara con codice gara " 
					+ "specificato (IDAVGARA = '" + idGara + "')", e);
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("countLottifromIdGara: fine metodo");
		}
		return numeroLotti;
	}

  /**
   * Metodo per determinare se l'utente e' il RUP (rappresentante unico della pratica) della gara
   * che si e' chiesto di importare, a partire dal numero di gara (IDGARA).
   * 
   * @param idAvGara Numero della gara
   * @param codUffInt Codice dell'ufficio intestatario
   * @param syscon Syscon della USRSYS
   * @return Ritorna true se l'utente e' il RUP della gara, false altrimenti
   * @throws SQLException SQLException
   */
  public boolean isUserRupDellaGara(final String idAvGara, final String codUffInt, final int syscon)
      throws SQLException {
    if (logger.isDebugEnabled()) {
      logger.debug("isRupGaraUtente: inizio metodo");
    }
    boolean result = false;

    try {
      Long numeroOccorrenze = (Long) this.sqlManager.getObject(
          "select COUNT(CODGARA) from W9GARA where (CODGARA=? or IDAVGARA=?) and CODEIN=? and RUP in " +
            " (select CODTEC from TECNI where SYSCON=? and CGENTEI=?) ",
          new Object[] { idAvGara, idAvGara, codUffInt, new Long(syscon), codUffInt });
      if (numeroOccorrenze != null && numeroOccorrenze.longValue() > 0) {
        result = true;
      }
    } catch (SQLException e) {
      logger.error("Errore nel determinare se l'utente e' il RUP della gara che si e' chiesto di importare "
          + " (IDAVGARA = '" + idAvGara + "')", e);
      throw e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("isRupGaraUtente: fine metodo");
    }
    return result;
  }

  /**
   * Consulta gara con accesso al WS Simog.
   * 
   * @param codiceCIG
   * @param idAvGara
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @param simoguser sempre valorizzato: la chiamata ai servizi Simog sarà fatta con questo utente
   * @param simogpass sempre valorizzato: la chiamata ai servizi Simog sarà fatta con questa password   
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException
   */
  private void consultaGaraAccessoDiretto(final String idAvGara, final String codUffInt, 
      final int syscon, HashMap<String, Object> resultMap, ConsultaGaraLottiBean consultaGaraLottiBean,
      final String simoguser, final String simogpass) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraAccessoDiretto: inizio metodo (con credenziali simog)");
    }

    String urlWsSimogPDD = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    if (!StringUtils.isNotEmpty(urlWsSimogPDD)) {
      logger.error("URL della PDD per accesso ai servizi SIMOG non e' definita");
    }

    if (StringUtils.isNotEmpty(urlWsSimogPDD)
        && StringUtils.isNotEmpty(simoguser)
          && StringUtils.isNotEmpty(simogpass)) {

      SimogWSPDDServiceStub simogWsPddServiceStub = null;
      it.avlp.simog.massload.xmlbeans.ResponseCheckLogin responseCheckLogin = null;
      String ticketSessioneSIMOG = null;
      
      try {
        simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimogPDD);
        
        this.ticketSimogManager.setProxy(simogWsPddServiceStub);
        
        LoginDocument loginDoc = LoginDocument.Factory.newInstance();
        Login loginIn = Login.Factory.newInstance();
        loginIn.setLogin(simoguser);
        loginIn.setPassword(simogpass);
        loginDoc.setLogin(loginIn);

        logger.info("Invocazione metodo login verso i Servizi SIMOG");
        LoginResponseDocument loginResponseDoc = simogWsPddServiceStub.login(loginDoc);
        logger.info("Invocato metodo login verso i Servizi SIMOG");
        
        LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
        
        if (loginResponse.isSetReturn() && loginResponse.getReturn().getSuccess()) {
          responseCheckLogin = loginResponse.getReturn();
          ticketSessioneSIMOG = responseCheckLogin.getTicket();
          logger.info("Login su SIMOG eseguito con successo (Ticket=" + ticketSessioneSIMOG + ")");

          String schede = "3.04.4.1.6";
          
          ConsultaNumeroGaraDocument consultaNumeroGaraDoc =
              ConsultaNumeroGaraDocument.Factory.newInstance();
          ConsultaNumeroGara consultaNumeroGara = ConsultaNumeroGara.Factory.newInstance();
          consultaNumeroGara.setIdGara(idAvGara);
          consultaNumeroGara.setSchede(schede);
          consultaNumeroGara.setTicket(ticketSessioneSIMOG);
          consultaNumeroGaraDoc.setConsultaNumeroGara(consultaNumeroGara);
          
          logger.info("Invocazione metodo consultaNumeroGara verso i Servizi SIMOG (Ticket="
              + ticketSessioneSIMOG + ", IdGara=" + idAvGara + ")");
          ConsultaNumeroGaraResponseDocument consultaNumeroGaraResponseDoc =
              simogWsPddServiceStub.consultaNumeroGara(consultaNumeroGaraDoc);
          logger.info("Invocato metodo consultaNumeroGara verso i Servizi SIMOG (Ticket="
              + ticketSessioneSIMOG + ", IdGara=" + idAvGara + ")");
          
          if (consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().isSetReturn()) {
            if (consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getSuccess()) {
              if (consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().isSetSchedaGaraCig()) {
                SchedaGaraCig schedaGaraCig = consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getSchedaGaraCig();
                
                if (schedaGaraCig.getGara().isSetDATACANCELLAZIONEGARA()) {
                  String strMsg = "La gara con IdGara=" + idAvGara
                    + " per conto della stazione appaltante con codUffInt='" + codUffInt
                    + "' non e' stata importata perche' su SIMOG risulta essere cancellata (in data " + 
                    UtilityDate.convertiData(schedaGaraCig.getGara().getDATACANCELLAZIONEGARA().getTime(), UtilityDate.FORMATO_GG_MM_AAAA)
                    + ")";
                
                  logger.error(strMsg);
                
                  resultMap.put("esito", Boolean.FALSE);
                  resultMap.put("errorMsg", "Gara non importata perche' e' stata cancellata su SIMOG");
                } else {
                  LinkedList<String> listaCig = consultaGaraLottiBean.getListaCig();
                  String[] arrayCig = consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getSchedaGaraCig().getCIGArray();
                  for (int ir=0; ir < arrayCig.length; ir++) {
                    listaCig.add(arrayCig[ir]);
                  }
                  consultaGaraLottiBean.setNumeroTotaleLotti(arrayCig.length);

                  resultMap.put("esito", Boolean.TRUE);
                }
              } else {
                // La risposta del metodo consultaNumeroGara non ha valorizzatto l'oggetto SchedaGaraCig.
                logger.error("Errore nella risposta al metodo consultaNumeroGara di SIMOG, perche' priva "
                    + "dell'oggetto che dovrebbe contenere l'array dei CIG della gara con IDGARA="
                    + idAvGara);

                resultMap.put("esito", Boolean.FALSE);
                resultMap.put("errorMsg", "Errore nel formato dei dati ricevuti da SIMOG (2)");
              }
            } else {
              // Il metodo consultaNumeroGara non e' terminato con successo.
              String msg = "Il metodo consultaNumeroGara di SIMOG e' fallito alla richiesta dei CIG della "
                + "gara con IDGARA=" + idAvGara;
              if (consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().isSetError()) {
                msg = msg.concat(". Dettaglio dell'errore: "
                    + consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getError());
              }
              logger.error(msg);

              resultMap.put("esito", Boolean.FALSE);
              resultMap.put("errorMsg", "Dettaglio dell'errore: "
                  + consultaNumeroGaraResponseDoc.getConsultaNumeroGaraResponse().getReturn().getError());
            }
          } else {
            // La risposta del metodo consultaNumeroGara non ha valorizzatto l'oggetto Return.
            logger.error("Errore nella risposta al metodo consultaNumeroGara di SIMOG, perche' priva "
                + "dell'oggetto che dovrebbe rappresentare la risposta della chiamata relativa alla "
                + "gara con IDGARA=" + idAvGara);
            
            resultMap.put("esito", Boolean.FALSE);
            resultMap.put("errorMsg", "Errore nel formato dei dati ricevuti da SIMOG (1)");
          }

          // Chiusura della connessione     
          ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticketSessioneSIMOG);
        
          ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
          chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
          
          ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = 
              simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
          logger.info("Invocazione del metodo chiudiSessione verso i Servizi SIMOG (Ticket="
              + ticketSessioneSIMOG + ")");
          ResponseChiudiSession responseChiudiSessione = 
              chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
          logger.info("Invocato il metodo chiudiSessione verso i Servizi SIMOG (Ticket="
              + ticketSessioneSIMOG + ")");
          
          if (!responseChiudiSessione.getSuccess()) {
            logger.error("La chiusura della connessione identificata dal ticket "
                + ticketSessioneSIMOG
                + " ha generato il seguente errore: "
                + responseChiudiSessione.getError());
          } else {
            logger.info("Logout al WS SIMOG avvenuta con successo. La sessione con ticket "
                + ticketSessioneSIMOG
                + " e' stata chiusa.");
            ticketSessioneSIMOG = null;
          }
        } else {
          String message = null;
          if (loginResponse.getReturn().isSetError()) {
            message = "Il tentativo di login ai servizi SIMOG e' terminato con errore e con la seguente descrizione: ".concat(loginResponse.getReturn().getError());
          } else {
            message = "Il tentativo di login ai servizi SIMOG e' terminato con errore generico (senza descrizione)";
          }
          logger.error(message);

          resultMap.put("errorMsg", message);
          resultMap.put("esito", Boolean.FALSE);
        }
      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con la porta di dominio per l'accesso al WS Simog "
            + "durante la chiamata consultaGara durante la funzione 'Carica lotto da SIMOG' con "
            + "codiceCIG=*, IdAvGara=" + idAvGara
            + ", da parte dell'utente syscon = " + syscon
            + " per conto della stazione appaltante con codUffInt=" + codUffInt, re);
        
        resultMap.put("esito", Boolean.FALSE);

      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento alla porta di dominio per l'accesso "
            + "al WS SIMOG durante la chiamata consultaGara con codiceCIG=*, IdAvGara='" + idAvGara
            + "', da parte dell'utente syscon = " + syscon
            + " per conto della stazione appaltante con codUffInt='" + codUffInt + "'", t);
        
        resultMap.put("esito", Boolean.FALSE);
      } finally {
        
        if (Boolean.FALSE.equals(resultMap.get("esito")) && !resultMap.containsKey("errorMsg")) {
          resultMap.put("errorMsg", "Il servizio SIMOG &egrave; momentaneamente non disponibile o "
              + "non risponde correttamente. Riprovare pi&ugrave; tardi. Se il problema persiste, "
              + "segnalarlo all'amministratore di sistema.");
        }
        
        if (StringUtils.isNotEmpty(ticketSessioneSIMOG)) {
          // Si prova a chiudere la connessione con il WS Simog nonostante vi
          // siano appena stati problemi di interazione con il WS stesso
     
          ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticketSessioneSIMOG);
        
          ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
          chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
          
          try {
            ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = 
                simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
            chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
          } catch(RemoteException re) {
            logger.error("Errore nell'interazione con il WS SIMOG nell'ulteriore tentativo "
                + "di chiudere la connessione al WS stesso. La sessione rimane quindi attiva "
                + "e non si potra' riconnettersi fino allo scadere della stessa.", re);
          }
        }
      }
    } else {
      // Errore nella configurazione URL, login e password

      String message = "Errore durante la connessione al web service SIMOG: i parametri di connessione "
          + "al server non sono specificati correttamente. Contattare un amministratore.";
      logger.error("Il tentativo di connessione ha generato il seguente errore: " + message);

      resultMap.put("errorMsg", message);
      resultMap.put("esito", Boolean.FALSE);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraAccessoDiretto: fine metodo (con credenziali simog)");
    }
  }
  
  
  
  /**
   * Consulta gara con accesso al WS Simog.
   * 
   * @param codiceCIG
   * @param idAvGara
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @param simoguser sempre valorizzato: la chiamata ai servizi Simog sarà fatta con questo utente
   * @param simogpass sempre valorizzato: la chiamata ai servizi Simog sarà fatta con questa password   
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException
   */
  private void consultaLottoAccessoDiretto(final String codiceCIG, final String idAvGara,
      final String codUffInt, final int syscon, HashMap<String, Object> resultMap,
      final String simoguser, final String simogpass, boolean isCfgVigilanza) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaLottoAccessoDiretto: inizio metodo (con credenziali simog)");
    }

    String urlWsSimogPDD = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    if (StringUtils.isEmpty(urlWsSimogPDD)) {
      logger.error("URL della PDD per accesso ai servizi SIMOG non e' definita");
    }
    
    String loginWsSimog = null; /*ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
    if (StringUtils.isEmpty(loginWsSimog)) {
      logger.error("Login per la connessione ai servizi SIMOG non e' definita");
    }*/
    
    String passwordWsSimog = null; /*ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD);
    if (StringUtils.isNotEmpty(passwordWsSimog)) {
      try {
        ICriptazioneByte cript = FactoryCriptazioneByte.getInstance(
            ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
            passwordWsSimog.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
        passwordWsSimog = new String(cript.getDatoNonCifrato());
      } catch (CriptazioneException e) {
        logger.error("Errore nella decriptazione della password di accesso ai servizi SIMOG", e);
      }
    } else {
      logger.error("Password per la connessione ai servizi SIMOG non e' definita");
    }*/
    
    if (simoguser != null) {
      loginWsSimog = simoguser;
    }
    
    if (simogpass != null) {
      passwordWsSimog = simogpass;
    }
    
    boolean isSmartCIG = false;
    String urlSmartCIG = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_SMARTCIG_URL);
    
    if (UtilitySITAT.isSmartCigValido(codiceCIG)) {
    	isSmartCIG = true;
    	if (StringUtils.isEmpty(urlSmartCIG)) {
            logger.error("URL per accesso ai servizi SIMOG-smartCIG non e' definita");
        }
    }
    if (StringUtils.isNotEmpty(urlWsSimogPDD)
        && StringUtils.isNotEmpty(loginWsSimog)
        && StringUtils.isNotEmpty(passwordWsSimog)
        && (StringUtils.isNotEmpty(urlSmartCIG) || !isSmartCIG)) {

      SimogWSPDDServiceStub simogWsPddServiceStub = null;
      it.avlp.simog.massload.xmlbeans.ResponseCheckLogin responseCheckLogin = null;
      String ticketSessioneSIMOG = null;
      
      try {
        simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimogPDD);
        
        this.ticketSimogManager.setProxy(simogWsPddServiceStub);
        
        LoginDocument loginDoc = LoginDocument.Factory.newInstance();
        Login loginIn = Login.Factory.newInstance();
        loginIn.setLogin(loginWsSimog);
        loginIn.setPassword(passwordWsSimog);
        loginDoc.setLogin(loginIn);

        logger.info("Invocazione metodo login verso i Servizi SIMOG");
        LoginResponseDocument loginResponseDoc = simogWsPddServiceStub.login(loginDoc);
        logger.info("Invocato metodo login verso i Servizi SIMOG");
        
        LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
        
        if (loginResponse.isSetReturn() && loginResponse.getReturn().getSuccess()) {
          responseCheckLogin = loginResponse.getReturn();
          ticketSessioneSIMOG = responseCheckLogin.getTicket();
          logger.info("Login su SIMOG eseguito con successo (Ticket=" + ticketSessioneSIMOG + ")");

          ConsultaLottoBean consultaLottoBean = new ConsultaLottoBean(codiceCIG, true, false);
          
          if (UtilitySITAT.existsLotto(codiceCIG, this.sqlManager)) {
            consultaLottoBean = new ConsultaLottoBean(codiceCIG, false, true, "Lotto esistente");
            consultaLottoBean.setCodgara((Long) this.sqlManager.getObject("select CODGARA from W9GARA where idAVGARA=?", new Object[] { "" + idAvGara }));
          } else {
            
        	  if (isSmartCIG) {
        		  ServicesServiceLocator WSLocator = new ServicesServiceLocator();
    	    	  WSLocator.setServicesSoap11EndpointAddress(urlSmartCIG);
    	    	  Services simogWS = WSLocator.getServicesSoap11();
    	    	  
    	    	  ConsultaComunicazioneRequest richiesta = new ConsultaComunicazioneRequest();
    	    	  richiesta.setCig(codiceCIG);
    	    	  ConsultaComunicazioneResponse comunicazioneResponse = null;
    	    	  LoggedUserInfoType user = new LoggedUserInfoType();
    	    	  
    	    	  user.setTicket(ticketSessioneSIMOG);
    	    	  Collaborazioni collaborazioni = responseCheckLogin.getColl();
    	    	  
    	    	  if (collaborazioni != null && collaborazioni.getCollaborazioniArray() != null && collaborazioni.getCollaborazioniArray().length>0) {
    	    	    	for(Collaborazione collaborazione:collaborazioni.getCollaborazioniArray()) {
    	    	        	user.setIndex(collaborazione.getIndex());
    	    	        	richiesta.setUser(user);
    	    	        	try {
    	    	        		comunicazioneResponse = simogWS.consultaComunicazione(richiesta);
    	    	        		if (comunicazioneResponse.getComunicazione() != null) {
    	    	        			break;
    	    	        		}
    	    	        	} catch (Exception ex) {
    	    	        		logger.error(ex);
    	    	        	}
    	    	        }
    	    	  } else {
    	    	    	user.setIndex("0");
    	    	    	richiesta.setUser(user);
    	    	    	comunicazioneResponse = simogWS.consultaComunicazione(richiesta);
    	    	  }
    	    	  
    	    	  if (comunicazioneResponse.getComunicazione() == null) {
  	    	    	RisultatoType risultato = comunicazioneResponse.getCodiceRisultato();
  	    	    	logger.error("La risposta del metodo consultaComunicazione verso i Servizi SIMOG-smartCIG (Ticket="
  	                        + ticketSessioneSIMOG + ", IdGara=" + idAvGara + ", CIG=" + codiceCIG +
  	                        ") non ha restituito alcun informazione significativa");
  	                consultaLottoBean = new ConsultaLottoBean(codiceCIG, false, false, 
  	                        "Lotto non importato per risposta incompleta dai servizi SIMOG");
  	                consultaLottoBean.setMsg(risultato.getDescrizione().getValue());
    	    	  } else {
    	    		  consultaLottoBean = this.consultaGaraUnificatoManager.consultaGaraSmartCIGUnificatoWS(
  	    				  comunicazioneResponse.getComunicazione(), codUffInt, syscon, isCfgVigilanza);
    	    	  }
    	    	  /*
    	    	  if (collaborazioni != null && collaborazioni.getCollaborazioniArray() != null && collaborazioni.getCollaborazioniArray().length>0) {
    	    		  user.setIndex(collaborazioni.getCollaborazioniArray(0).getIndex());
    	    	  } else {
    	    		  user.setIndex("0");
    	    	  }
    	    	  richiesta.setUser(user);
    	    	  
    	    	  logger.info("Invocazione del metodo consultaComunicazione verso i Servizi SIMOG-smartCIG (Ticket="
    	  	            + ticketSessioneSIMOG + ", CIG=" + codiceCIG + ")");
    	    	  comunicazioneResponse = simogWS.consultaComunicazione(richiesta);
          		
    	    	  logger.info("Invocato il metodo consultaComunicazione verso i Servizi SIMOG-smartCIG (Ticket="
    	  	            + ticketSessioneSIMOG + ", CIG=" + codiceCIG + ")");
    	    	  
    	    	  if (comunicazioneResponse.getComunicazione() == null) {
    	    	    	RisultatoType risultato = comunicazioneResponse.getCodiceRisultato();
    	    	    	logger.error("La risposta del metodo consultaComunicazione verso i Servizi SIMOG-smartCIG (Ticket="
    	                        + ticketSessioneSIMOG + ", IdGara=" + idAvGara + ", CIG=" + codiceCIG +
    	                        ") non ha restituito alcun informazione significativa");
    	                consultaLottoBean = new ConsultaLottoBean(codiceCIG, false, false, 
    	                        "Lotto non importato per risposta incompleta dai servizi SIMOG");
    	                consultaLottoBean.setMsg(risultato.getDescrizione().getValue());
    	    	  } else {
    	    		  consultaLottoBean = this.consultaGaraUnificatoManager.consultaGaraSmartCIGUnificatoWS(
    	    				  comunicazioneResponse.getComunicazione(), codUffInt, syscon, isCfgVigilanza);
    	    	  }
    	    	  */
        	  } else {
        		  ConsultaGaraDocument consultaGaraDoc = ConsultaGaraDocument.Factory.newInstance();
              ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
              consultaGara.setTicket(ticketSessioneSIMOG);
              consultaGara.setSchede(CostantiSimog.CONSULTA_GARA_SCHEDE);

              consultaGara.setCIG(codiceCIG);
              consultaGaraDoc.setConsultaGara(consultaGara);

              logger.info("Invocazione del metodo consultaGara verso i Servizi SIMOG (Ticket="
                  + ticketSessioneSIMOG + ", IdGara=" + idAvGara + ", CIG=" + codiceCIG + ")");
              ConsultaGaraResponseDocument consultaGaraResponseDoc = simogWsPddServiceStub.consultaGara(consultaGaraDoc);
              logger.info("Invocato il metodo consultaGara verso i Servizi SIMOG (Ticket="
                  + ticketSessioneSIMOG + ", IdGara=" + idAvGara + ", CIG=" + codiceCIG + ")");

              if (consultaGaraResponseDoc.getConsultaGaraResponse().isSetReturn()) {
                ResponseConsultaGara responseConsultaGara =
                  consultaGaraResponseDoc.getConsultaGaraResponse().getReturn();

                if (responseConsultaGara.getSuccess()) {
                  // inserimento diretto nel DB del cig
                  SchedaType schedaType = responseConsultaGara.getGaraXML();
                  GaraType garaType = responseConsultaGara.getGaraXML().getDatiGara().getGara();
                  LottoType lottoType = responseConsultaGara.getGaraXML().getDatiGara().getLotto();

                  consultaLottoBean = this.consultaGaraUnificatoManager.consultaGaraUnificatoWS(
                      codiceCIG, idAvGara, codUffInt, syscon, schedaType, garaType, lottoType, isCfgVigilanza);
                } else {
                  if (StringUtils.isNotEmpty(responseConsultaGara.getError())) {
                    logger.error("Risposta negativa alla chiamata consultaGara verso i Servizi SIMOG (Ticket="
                        + ticketSessioneSIMOG + ", IdGara=" + idAvGara + ", CIG=" + codiceCIG +
                        "): " + responseConsultaGara.getError());
                  } else {
                    logger.error("Risposta negativa alla chiamata consultaGara verso i Servizi SIMOG (Ticket="
                        + ticketSessioneSIMOG + ", IdGara=" + idAvGara + ", CIG=" + codiceCIG +
                        ") senza indicare una causa");
                  }
                  
                  consultaLottoBean = new ConsultaLottoBean(codiceCIG, false, false);
                  if (StringUtils.isNotEmpty(responseConsultaGara.getError())) {
                    consultaLottoBean.setMsg(responseConsultaGara.getError());
                  } else {
                    consultaLottoBean.setMsg("Lotto non importato per risposta incompleta dai servizi SIMOG");
                  }
                }

              } else {
                logger.error("La risposta del metodo consultaGara verso i Servizi SIMOG (Ticket="
                    + ticketSessioneSIMOG + ", IdGara=" + idAvGara + ", CIG=" + codiceCIG +
                    ") non ha restituito alcun informazione significativa");
                consultaLottoBean = new ConsultaLottoBean(codiceCIG, false, false, 
                    "Lotto non importato per risposta incompleta dai servizi SIMOG");
              }
        	  }
          }

          resultMap.put("consultaLottoBean", consultaLottoBean);
          resultMap.put("esito", new Boolean(consultaLottoBean.isCaricato() || consultaLottoBean.isEsistente()));
          
          // Chiusura della connessione     
          ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticketSessioneSIMOG);
        
          ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
          chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
          
          ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = 
              simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
          logger.info("Invocazione del metodo chiudiSessione verso i Servizi SIMOG (Ticket="
              + ticketSessioneSIMOG + ", Codice CIG=" + codiceCIG + ")");
          ResponseChiudiSession responseChiudiSessione = 
              chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
          logger.info("Invocato il metodo chiudiSessione verso i Servizi SIMOG (Ticket="
              + ticketSessioneSIMOG + ", Codice CIG=" + codiceCIG + ")");
          
          if (!responseChiudiSessione.getSuccess()) {
            logger.error("La chiusura della connessione identificata dal ticket "
                + ticketSessioneSIMOG
                + " ha generato il seguente errore: "
                + responseChiudiSessione.getError());
          } else {
            logger.info("Logout al WS SIMOG avvenuta con successo. La sessione con ticket "
                + ticketSessioneSIMOG
                + " e' stata chiusa.");
            ticketSessioneSIMOG = null;
          }
        } else {
          String message = null;
          if (loginResponse.getReturn().isSetError()) {
            message = "Il tentativo di login ai servizi SIMOG e' terminato con errore e con la seguente descrizione: ".concat(loginResponse.getReturn().getError());
          } else {
            message = "Il tentativo di login ai servizi SIMOG e' terminato con errore generico (senza descrizione)";
          }
          logger.error(message);

          resultMap.put("errorMsg", message);
          resultMap.put("esito", Boolean.FALSE);
        }
      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con la porta di dominio per l'accesso al WS Simog "
            + "durante la chiamata consultaGara durante la funzione 'Carica lotto da SIMOG' con "
            + "codiceCIG=" + codiceCIG
            + ", IdAvGara=" + idAvGara
            + ", da parte dell'utente syscon = " + syscon
            + " per conto della stazione appaltante con codUffInt=" + codUffInt, re);
        
        resultMap.put("esito", Boolean.FALSE);
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento alla porta di dominio per l'accesso "
            + "al WS SIMOG durante la chiamata consultaGara con codiceCIG='"
            + codiceCIG + "', IdAvGara='" + idAvGara + "', da parte dell'utente syscon = "
            + syscon + " per conto della stazione appaltante con codUffInt='"
            + codUffInt + "'", t);
        
        resultMap.put("esito", Boolean.FALSE);
      } finally {
        
    	  if (Boolean.FALSE.equals(resultMap.get("esito")) && resultMap.containsKey("consultaLottoBean")) {
    		  ConsultaLottoBean consultaLottoBean = (ConsultaLottoBean)resultMap.get("consultaLottoBean");
              resultMap.put("errorMsg", consultaLottoBean.getMsg());
          } else if (Boolean.FALSE.equals(resultMap.get("esito")) && !resultMap.containsKey("errorMsg")) {
            	resultMap.put("errorMsg", "Il servizio SIMOG &egrave; momentaneamente non disponibile o "
              + "non risponde correttamente. Riprovare pi&ugrave; tardi. Se il problema persiste, "
              + "segnalarlo all'amministratore di sistema.");
          }
        
        if (StringUtils.isNotEmpty(ticketSessioneSIMOG)) {
          // Si prova a chiudere la connessione con il WS Simog nonostante vi
          // siano appena stati problemi di interazione con il WS stesso
     
          ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticketSessioneSIMOG);
        
          ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
          chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
          
          try {
            ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = 
                simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
            chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
          } catch(RemoteException re) {
            logger.error("Errore nell'interazione con il WS SIMOG nell'ulteriore tentativo "
                + "di chiudere la connessione al WS stesso. La sessione rimane quindi attiva "
                + "e non si potra' riconnettersi fino allo scadere della stessa.", re);
          }
        }
      }
    } else {
      // Errore nella configurazione URL, login e password

      String message = "Errore durante la connessione al web service SIMOG: i parametri di connessione "
          + "al server non sono specificati correttamente. Contattare un amministratore.";
      logger.error("Il tentativo di connessione ha generato il seguente errore: " + message);

      resultMap.put("errorMsg", message);
      resultMap.put("esito", Boolean.FALSE);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaLottoAccessoDiretto: fine metodo (con credenziali simog)");
    }
  }
  
  
  /**
   * Consulta Gara indiretto attraverso SitatORT, che accede al WS Simog.
   * 
   * @param idAvGara
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException
   */
  private void consultaGaraAccessoIndiretto(final String idAvGara, final String codUffInt, final int syscon, 
      HashMap<String, Object> resultMap, ConsultaGaraLottiBean consultaGaraLottiBean) 
          throws SQLException, GestoreException {

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraAccessoIndiretto: inizio metodo");
    }
    
    String urlWsORT = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    
    if (StringUtils.isNotEmpty(urlWsORT)) {
      //String[] codiciCIG = null;

      String cfsa = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", new Object[] { codUffInt } );
      String cfrup = (String) this.sqlManager.getObject("select CFTEC from TECNI where SYSCON=? and CGENTEI=?",
          new Object[] { new Long(syscon),  codUffInt} );
      String codTec = null;
      
      if (StringUtils.isEmpty(cfrup)) {
        codTec = (String) this.sqlManager.getObject("select CODTEC from TECNI where SYSCON is null and CGENTEI=? and "
            + "UPPER(CFTEC)=(select UPPER(SYSCF) from USRSYS where SYSCON=? and SYSCF is not null)", 
                new Object[] { codUffInt, new Long(syscon) });
        
        if (StringUtils.isNotEmpty(codTec)) {
          this.consultaGaraUnificatoManager.updateTecnico(syscon, codTec);
          
          cfrup = (String) this.sqlManager.getObject( "select CFTEC from TECNI where CODTEC=?",
              new Object[] { codTec } );
        } else {
        	cfrup = (String) this.sqlManager.getObject( "select SYSCF from USRSYS where SYSCON=?", new Object[] { syscon } );
          //cfrup = this.consultaGaraUnificatoManager.insertTecnico(codUffInt, syscon);
        }
      }

      WsOsservatorioProxy wsOsservatorioProxy = new WsOsservatorioProxy(urlWsORT);
      
      try {
        org.apache.axis.client.Stub stubClient = (Stub) wsOsservatorioProxy.getWsOsservatorio_PortType();
        
        if (logger.isDebugEnabled()) {
          logger.debug("Prima. Default timeout: " + HTTPConstants.SO_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
              + " ." + HTTPConstants.CONNECTION_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT));
        }
        
        String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
        if (StringUtils.isNotEmpty(strTimeOut)) {
          logger.debug("Valorizzazione del timeout nella richiesta al WsOsservatorio");
          stubClient.setTimeout(new Integer(strTimeOut).intValue());
        }
        
        if (logger.isDebugEnabled()) {
          logger.debug("Dopo. Default timeout: " + HTTPConstants.SO_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
              + " ." + HTTPConstants.CONNECTION_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT)
              + " . stubClient.getTimeout()=" + stubClient.getTimeout());
        }
        
        it.toscana.rete.rfc.sitatsa.client.ResponseConsultaGara responseConsultaGara = null;

        logger.info("Invocazione del metodo getGaraXML del WsOsservatorio (IdGara="
            + idAvGara + ", CIG=*, cfrup=" + cfrup + ", cfsa=" + cfsa + ")");

        responseConsultaGara = wsOsservatorioProxy.getGaraXML(null, idAvGara, cfrup, cfsa, true);
        
        logger.info("Invocato il metodo getGaraXML del WsOsservatorio (IdGara="
            + idAvGara + ", CIG=*, cfrup=" + cfrup + ", cfsa=" + cfsa + ")");
        if (responseConsultaGara.isSuccess() && StringUtils.isNotEmpty(responseConsultaGara.getGaraXML())) {
          ConsultaNumeroGaraResponseDocument consultaNumeroGaraResponseDocument = ConsultaNumeroGaraResponseDocument.Factory.parse(responseConsultaGara.getGaraXML());
          if (consultaNumeroGaraResponseDocument.getConsultaNumeroGaraResponse() != null && 
              consultaNumeroGaraResponseDocument.getConsultaNumeroGaraResponse().isSetReturn() &&
              consultaNumeroGaraResponseDocument.getConsultaNumeroGaraResponse().getReturn().getSuccess() &&
              consultaNumeroGaraResponseDocument.getConsultaNumeroGaraResponse().getReturn().isSetSchedaGaraCig()) {
            SchedaGaraCig schedaGaraCig = consultaNumeroGaraResponseDocument.getConsultaNumeroGaraResponse().getReturn().getSchedaGaraCig();
            
            if (schedaGaraCig.getGara().isSetDATACANCELLAZIONEGARA()) {
              String strMsg = "La gara con IdGara=" + idAvGara
                + " per conto della stazione appaltante con codUffInt='" + codUffInt
                + "' non e' stata importata perche' su SIMOG risulta essere cancellata (in data " + 
                UtilityDate.convertiData(schedaGaraCig.getGara().getDATACANCELLAZIONEGARA().getTime(), UtilityDate.FORMATO_GG_MM_AAAA)
                + ")";
              
              logger.error(strMsg);
              
              resultMap.put("esito", Boolean.FALSE);
              resultMap.put("errorMsg", "Gara non importata perche' e' stata cancellata su SIMOG");
              
            } else if (schedaGaraCig.getCIGArray() != null && schedaGaraCig.getCIGArray().length > 0) {
              LinkedList<String> listaCig = consultaGaraLottiBean.getListaCig();
              String[] arrayCig = schedaGaraCig.getCIGArray();
              for (int ir=0; ir < arrayCig.length; ir++) {
                listaCig.add(arrayCig[ir]);
              }
              consultaGaraLottiBean.setNumeroTotaleLotti(arrayCig.length);

              resultMap.put("esito", Boolean.TRUE);
            }
          } else {
            String strMsg = "La richiesta a SIMOG dei CIG associati alla gara con IdGara=" + idAvGara
              + " per conto della stazione appaltante con codUffInt='" + codUffInt
              + "' e' terminata con esito negativo. Dalla risposta non e' stato possibile risalire all'array dei CIG della gara.";
            if (StringUtils.isNotEmpty(responseConsultaGara.getError())) {
              strMsg = strMsg.concat(" Di seguito il messaggio di ritorno da SIMOG: ").concat(responseConsultaGara.getError());
            } 
            logger.error(strMsg);
 
            resultMap.put("esito", Boolean.FALSE);
            resultMap.put("errorMsg", responseConsultaGara.getError());
          }
        } else {
          String strMsg = "La richiesta a SIMOG dei CIG associati alla gara con IdGara=" + idAvGara
          + " e' terminata con esito negativo. Di seguito il messaggio di ritorno da SIMOG: "; 
            logger.error(strMsg.concat(responseConsultaGara.getError()));
 
          resultMap.put("esito", Boolean.FALSE);
          resultMap.put("errorMsg", responseConsultaGara.getError());
        }

      } catch (XmlException xe) {
        logger.error("Errore nella deserializzazione della risposta della chiamata consultaGara "
            + "durante la funzione 'Carica gara da SIMOG' con IdAvGara='" + idAvGara
            + "', da parte dell'utente syscon = " + syscon + " per conto della stazione appaltante con codUffInt='"
            + codUffInt + "'", xe);
        
        resultMap.put("esito", Boolean.FALSE);
      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con il WS Osservatorio per la chiamata consultaGara "
            + "durante la funzione 'Carica lotto da SIMOG' con codiceCIG=*, IdAvGara='" + idAvGara
            + "', da parte dell'utente syscon = " + syscon
            + " per conto della stazione appaltante con codUffInt='"
            + codUffInt + "'", re);
        
        resultMap.put("esito", Boolean.FALSE);
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento al WS Osservatorio per la chiamata "
            + "consultaGara durante la funzione 'Carica lotto da SIMOG' con codiceCIG=+, IdAvGara='" + idAvGara
            + "', da parte dell'utente syscon = " + syscon 
            + " per conto della stazione appaltante con codUffInt='" + codUffInt + "'", t);

        resultMap.put("esito", Boolean.FALSE);
      } finally {
        if (Boolean.FALSE.equals(resultMap.get("esito")) && !resultMap.containsKey("errorMsg")) {
          resultMap.put("errorMsg", "Il servizio SIMOG e' momentaneamente non disponibile o non "
              + "risponde correttamente. Riprovare piu' tardi. Se il problema persiste, segnalarlo "
              + "all'amministratore di sistema.");
        }
      }

    } else {
      logger.error("Errore di configurazione: manca l'indirizzo per la connessione al "
          + "WS Ossevatorio per la chiamata consultaGara. Contattare un amministratore.");
      resultMap.put("esito", Boolean.FALSE);
      resultMap.put("errorMsg", "Errore di configurazione: manca l'indirizzo per la connessione "
          + "al WS Osservatorio. Contattare un amministratore.");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraAccessoIndiretto: fine metodo");
    }
  }
  
  
  /**
   * Consulta lotto indiretto attraverso SitatORT, che accede al WS Simog.
   * 
   * @param codiceCIG
   * @param idAvGara
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException
   */
  private void consultaLottoAccessoIndiretto(final String codiceCIG, final String idAvGara,
      final String codUffInt, final int syscon, HashMap<String, Object> resultMap) 
          throws SQLException, GestoreException {

    if (logger.isDebugEnabled()) {
      logger.debug("consultaLottoAccessoIndiretto: inizio metodo");
    }

    boolean isSmartCIG = false;
	if (UtilitySITAT.isSmartCigValido(codiceCIG)) {
	   	isSmartCIG = true;
	}
    ConsultaLottoBean consultaLottoBean = null;
    
    if ((!isSmartCIG && UtilitySITAT.existsLotto(codiceCIG, this.sqlManager)) || (isSmartCIG && UtilitySITAT.existsSmartCigSA(codiceCIG, codUffInt, this.sqlManager))) {
      consultaLottoBean = new ConsultaLottoBean(codiceCIG, false, true, "Lotto esistente");
      resultMap.put("errorMsg", consultaLottoBean.getMsg());
      resultMap.put("esito", false);
  	  resultMap.put("consultaLottoBean", consultaLottoBean);
    } else {
      String urlWsORT = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
      
      if (StringUtils.isNotEmpty(urlWsORT)) {
        WsOsservatorioProxy wsOsservatorioProxy = new WsOsservatorioProxy(urlWsORT);
        String cfsa = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?",
            new Object[] { codUffInt } );
        
        try {
          org.apache.axis.client.Stub stubClient = (Stub) wsOsservatorioProxy.getWsOsservatorio_PortType();
          
          if (logger.isDebugEnabled()) {
            logger.debug("Prima. Default timeout: " + HTTPConstants.SO_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
                + " ." + HTTPConstants.CONNECTION_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT));
          }
          
          String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
          if (StringUtils.isNotEmpty(strTimeOut)) {
            logger.debug("Valorizzazione del timeout nella richiesta al WsOsservatorio");
            stubClient.setTimeout(new Integer(strTimeOut).intValue());
          }
          
          if (logger.isDebugEnabled()) {
            logger.debug("Dopo. Default timeout: " + HTTPConstants.SO_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
                + " ." + HTTPConstants.CONNECTION_TIMEOUT + "=" + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT)
                + " . stubClient.getTimeout()=" + stubClient.getTimeout());
          }
          
          it.toscana.rete.rfc.sitatsa.client.ResponseConsultaGara responseConsultaGara = null;
          //verifico se ho valorizzato le credenziali del RUP per recuperare i dati di uno SmartCIG
          String cigConCredenziali = codiceCIG;
          if (resultMap.containsKey("simoguser") && resultMap.containsKey("simogpass")) {
        	  cigConCredenziali += "#" + resultMap.get("simoguser") + "#" + resultMap.get("simogpass");
          }
          logger.info("Invocazione del metodo getGaraXML del WsOsservatorio (IdGara="
              + idAvGara + ", CIG=" + cigConCredenziali + ", cfsa=" + cfsa + ", syscon=" + syscon + ")");
          // Si effettua la chiamata all'osservatorio senza il controllo preliminare dei dati.
          // In questo modo si permette l'importazione di gare a più lotti con eventuali lotti
          // non perfezionati o cancellati. Il servizio per ora non viene modificato per
          // questione di tempo.
          responseConsultaGara = wsOsservatorioProxy.getGaraXML(cigConCredenziali, idAvGara, null, cfsa, true);
          logger.info("Invocato il metodo getGaraXML del WsOsservatorio (IdGara="
              + idAvGara + ", CIG=" + cigConCredenziali + ", cfsa=" + cfsa + ", syscon=" + syscon + ")");
          
          if (responseConsultaGara.isSuccess()) {
            // inserimento diretto nel DB del cig
       	    if (isSmartCIG) {
       	    	XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(responseConsultaGara.getGaraXML().getBytes()));
       	    	ComunicazioneType schedaSmartCIG = (ComunicazioneType)decoder.readObject();
    		    decoder.close();
       	    	consultaLottoBean = this.consultaGaraUnificatoManager.consultaGaraSmartCIGUnificatoWS(
       	    			schedaSmartCIG, codUffInt, syscon, false);
       	    } else {
       	    	ConsultaGaraResponseDocument consultaGaraResponseDocument = 
                    ConsultaGaraResponseDocument.Factory.parse(responseConsultaGara.getGaraXML());

                consultaLottoBean = this.consultaGaraUnificatoManager.consultaGaraUnificatoPDD(
                    codiceCIG, idAvGara, codUffInt, syscon, 
                    consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML(),
                    consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().getDatiGara().getGara(),
                    consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().getDatiGara().getLotto(),
                    false);
       	    }
          } else {
            consultaLottoBean = new ConsultaLottoBean(codiceCIG, false, false);
            if (StringUtils.isNotEmpty(responseConsultaGara.getError())) {
              consultaLottoBean.setMsg(responseConsultaGara.getError());
            } else {
              consultaLottoBean.setMsg("Lotto non importato per risposta incompleta dai servizi SIMOG");
            }
                          
            StringBuffer strBuf = new StringBuffer("Il CIG=");
            strBuf.append(consultaLottoBean.getCodiceCIG());
            //strBuf.append(" con CFRUP= ");
            //strBuf.append(cfrup);
            strBuf.append(" non e' stato caricato perche' i servizi Simog hanno risposto con esito negativo");
            
            if (StringUtils.isNotEmpty(responseConsultaGara.getError())) {
              strBuf.append(" con il seguente messaggio di errore: ");
              strBuf.append(responseConsultaGara.getError());
            }
            logger.error(strBuf.toString());
            resultMap.put("errorMsg", responseConsultaGara.getError());
          }
      	  
          resultMap.put("errorMsg", consultaLottoBean.getMsg());
          resultMap.put("esito", new Boolean(consultaLottoBean.isCaricato() || consultaLottoBean.isEsistente()));
      	  resultMap.put("consultaLottoBean", consultaLottoBean);
      	  
        } catch (XmlException xe) {
          logger.error("Errore nella deserializzazione della risposta della chiamata consultaGara "
              + "durante la funzione 'Carica gara da SIMOG' con IdAvGara='" + idAvGara
              + "', da parte dell'utente syscon = " + syscon + " per conto della stazione appaltante con codUffInt='"
              + codUffInt + "'", xe);
          
          resultMap.put("esito", Boolean.FALSE);
        } catch (RemoteException re) {
          logger.error("Errore nell'interazione con il WS Osservatorio per la chiamata consultaGara "
              + "durante la funzione 'Carica lotto da SIMOG' con codiceCIG='" + codiceCIG
              + "', IdAvGara='" + idAvGara + "', da parte dell'utente syscon = "
              + syscon + " per conto della stazione appaltante con codUffInt='"
              + codUffInt + "'", re);
          
          resultMap.put("esito", Boolean.FALSE);
        } catch (Throwable t) {
          logger.error("Errore inaspettato nel collegamento al WS Osservatorio per la chiamata "
              + "consultaGara durante la funzione 'Carica lotto da SIMOG' con codiceCIG='"
              + codiceCIG + "', IdAvGara='" + idAvGara + "', da parte dell'utente syscon = "
              + syscon + " per conto della stazione appaltante con codUffInt='"
              + codUffInt + "'", t);
  
          resultMap.put("esito", Boolean.FALSE);
        } finally {
          if (Boolean.FALSE.equals(resultMap.get("esito")) 
                && !resultMap.containsKey("consultaLottoBean") 
                    && !resultMap.containsKey("errorMsg")) {
            resultMap.put("errorMsg", "Il servizio SIMOG e' momentaneamente non disponibile o non "
                + "risponde correttamente. Riprovare piu' tardi. Se il problema persiste, segnalarlo "
                + "all'amministratore di sistema.");
          }
        }
  
      } else {
        logger.error("Errore di configurazione: manca l'indirizzo per la connessione al "
            + "WS Ossevatorio per la chiamata consultaGara. Contattare un amministratore.");
        resultMap.put("esito", Boolean.FALSE);
        resultMap.put("errorMsg", "Errore di configurazione: manca l'indirizzo per la connessione "
            + "al WS Osservatorio. Contattare un amministratore.");
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaLottoAccessoIndiretto: fine metodo");
    }
  }
  
  private HashMap<String, Object> consultaGaraAccessoDirettoXmlSimog(final String codiceCIG,
      final long syscon, final String codUffInt, final String simogUser, String simogPassword) {

    logger.debug("consultaGaraAccessoDirettoXmlSimog: inizio metodo");
    
    HashMap<String, Object> hm = new HashMap<String, Object>();
    String ticketSimog = null;
    
    TicketSimogBean ticketSimogBean = this.accessoSimogManager.simogLogin(simogUser, simogPassword);
    if (ticketSimogBean.isSuccess()) {
      ticketSimog = ticketSimogBean.getTicketSimog();
      
      try {
        String urlWsSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
        SimogWSPDDServiceStub simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimog);
        this.accessoSimogManager.setProxy(simogWsPddServiceStub);
        this.accessoSimogManager.setTimeout(simogWsPddServiceStub);

        ConsultaGaraDocument consultaGaraDoc = ConsultaGaraDocument.Factory.newInstance();
        ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
        consultaGara.setTicket(ticketSimog);
        consultaGara.setSchede(CostantiSimog.CONSULTA_GARA_SCHEDE);

        consultaGara.setCIG(codiceCIG);
        consultaGaraDoc.setConsultaGara(consultaGara);

        logger.info("Invocazione del metodo consultaGara verso i Servizi SIMOG (Ticket="
            + ticketSimog + ", CIG=" + codiceCIG + ")");
        ConsultaGaraResponseDocument consultaGaraResponseDoc = simogWsPddServiceStub.consultaGara(consultaGaraDoc);
        logger.info("Invocato il metodo consultaGara verso i Servizi SIMOG (Ticket="
            + ticketSimog + ", CIG=" + codiceCIG + ")");

        if (consultaGaraResponseDoc.getConsultaGaraResponse().isSetReturn()) {
          ResponseConsultaGara responseConsultaGara =
            consultaGaraResponseDoc.getConsultaGaraResponse().getReturn();

          if (responseConsultaGara.getSuccess()) {
            hm.put("esito", Boolean.TRUE);
            hm.put("garaXml", consultaGaraResponseDoc.xmlText()); // GaraXML().toString());
          } else {
            hm.put("esito", Boolean.FALSE);
            hm.put("msgErr", responseConsultaGara.getError());
          }
        } else {
          hm.put("esito", Boolean.FALSE);
          hm.put("msgErr", "Errore del formato della risposta da SIMOG");
        }

      } catch (AxisFault af) {
        logger.error("Errore inaspettato nella inizializzazione del client dei servizi SIMOG nell'operazione di login", af);
        ticketSimogBean.setSuccess(false);
        ticketSimogBean.setMsgError("Errore inaspettato nel tentativo di login ai servizi SIMOG");
        
        hm.put("esito", Boolean.FALSE);
        hm.put("msgErr", "Errore inaspettato nel tentativo di login ai servizi SIMOG");
        
      } catch (RemoteException re) {
        logger.error("Errore remoto inaspettato (quindi lato server) nell'interazione con i servizi SIMOG nell'operazione di login", re);
        ticketSimogBean.setSuccess(false);
        ticketSimogBean.setMsgError("Errore remoto inaspettato nel tentativo di login ai servizi SIMOG");
        
        hm.put("esito", Boolean.FALSE);
        hm.put("msgErr", "Errore remoto inaspettato nel tentativo di login ai servizi SIMOG");
        // } catch (IOException ioe) {
        // logger.error("Errore inaspettato nell'interazione con i servizi SIMOG nell'operazione di login", ioe);
      } catch (Exception e) {
        logger.error("Errore inaspettato nell'interazione con i servizi SIMOG nell'operazione di login", e);
        ticketSimogBean.setSuccess(false);
        ticketSimogBean.setMsgError("Errore inaspettato nel tentativo di login ai servizi SIMOG");
        
        hm.put("esito", Boolean.FALSE);
        hm.put("msgErr", "Errore inaspettato nel tentativo di login ai servizi SIMOG");
      }
    } else {
      logger.error("Login ai servizi SIMOG: manca login e/o password");
      ticketSimogBean.setSuccess(false);
      ticketSimogBean.setMsgError("Parametri insufficienti per l'operazione login ai servizi SIMOG");
      
      hm.put("esito", Boolean.FALSE);
      hm.put("msgErr", ticketSimogBean.getMsgError());
    }
   
    if (StringUtils.isNotEmpty(ticketSimog)) {
      this.accessoSimogManager.simogLogout(ticketSimog);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraAccessoDirettoXmlSimog: fine metodo");
    }

    return hm;
  }
  
  /**
   * Consulta gara indiretto attraverso SitatORT, che accede al WS Simog attraverso la PDD per il 
   * recupero da del Xml di un lotto di gara.
   * 
   * @param codiceCIG
   * @param idAvGara
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException
   */
  private HashMap<String, Object> consultaGaraAccessoIndirettoXmlSimog(final String codiceCIG,
      final long syscon, final String codUffInt) {

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraAccessoIndirettoPddXmlSimog: inizio metodo");
    }
    
    HashMap<String, Object> hm = new HashMap<String, Object>();
    String urlWsORT = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    
    if (StringUtils.isNotEmpty(urlWsORT)) {
      WsOsservatorioProxy wsOsservatorio = new WsOsservatorioProxy(urlWsORT);
      
      try {
        org.apache.axis.client.Stub stubClient = (Stub) wsOsservatorio.getWsOsservatorio_PortType();
        
        String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
        if (StringUtils.isNotEmpty(strTimeOut)) {
          if (logger.isDebugEnabled()) {
            logger.debug("Prima. Default timeout: " + HTTPConstants.SO_TIMEOUT + "="
                + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
                + " ." + HTTPConstants.CONNECTION_TIMEOUT + "="
                + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT));
          }
        
          logger.debug("Valorizzazione del timeout nella richiesta al WsOsservatorio");
          stubClient.setTimeout(new Integer(strTimeOut).intValue());
        
          if (logger.isDebugEnabled()) {
            logger.debug("Dopo. Default timeout: " + HTTPConstants.SO_TIMEOUT + "="
                + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
                + " ." + HTTPConstants.CONNECTION_TIMEOUT + "="
                + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT)
                + " . stubClient.getTimeout()=" + stubClient.getTimeout());
          }
        } 
        it.toscana.rete.rfc.sitatsa.client.ResponseConsultaGara responseConsultaGara = null;

        // Ricezione dei dati di tutti i lotti e memorizzazione nell'oggetto
        // hmCigResponseConsultaGara
        logger.info("Invocazione del metodo getGaraXML del WsOsservatorio (CIG=" + codiceCIG
            + ", syscon=" + syscon + ", codein=" + codUffInt + ")");
        // Si effettua la chiamata all'osservatorio senza il controllo preliminare dei dati.
        // In questo modo si permette l'importazione di gare a più lotti con eventuali lotti
        // non perfezionati o cancellati. Il servizio per ora non viene modificato per
        // questione di tempo.
        responseConsultaGara = wsOsservatorio.getGaraXML(codiceCIG, null, null, null, false);
        logger.info("Invocato il metodo getGaraXML del WsOsservatorio (CIG=" + codiceCIG
            + ", syscon=" + syscon + ", codein=" + codUffInt + ")");
        
        if (responseConsultaGara.isSuccess()) {
          hm.put("esito", Boolean.TRUE);
          hm.put("garaXml", responseConsultaGara.getGaraXML());
        } else {
          hm.put("esito", Boolean.FALSE);
          hm.put("msgErr", responseConsultaGara.getError());
        }

      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con il WS Osservatorio per la chiamata consultaGara "
            + "durante la funzione 'Carica lotto da SIMOG' con codiceCIG='" + codiceCIG
            + " da parte dell'utente syscon = " + syscon 
            + " per conto della stazione appaltante con codUffInt='" + codUffInt + "'", re);
        
        hm.put("esito", Boolean.FALSE);
      /*} catch (ServiceException se) {
        logger.error("Errore nel collegamento al WS Osservatorio per la chiamata consultaGara "
            + "durante la funzione 'Carica lotto da SIMOG' con codiceCIG='" + codiceCIG
            + " da parte dell'utente syscon = " + syscon + 
            " per conto della stazione appaltante con codUffInt='" + codUffInt + "'", se);
        
        hm.put("esito", Boolean.FALSE);*/
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento al WS Osservatorio per la chiamata "
            + "consultaGara durante la funzione 'Carica lotto da SIMOG' con codiceCIG='"
            + codiceCIG + " da parte dell'utente syscon = " + syscon
            + " per conto della stazione appaltante con codUffInt='" + codUffInt + "'", t);

        hm.put("esito", Boolean.FALSE);
      } finally {
        if (Boolean.FALSE.equals(hm.get("esito"))) {
          hm.put("msgErr", "Il servizio SIMOG e' momentaneamente non disponibile o non "
              + "risponde correttamente. Riprovare piu' tardi. Se il problema persiste, segnalarlo "
              + "all'amministratore di sistema.");
        }
      }

    } else {
      logger.error("Errore di configurazione: manca l'indirizzo per la connessione al "
          + "WS Ossevatorio per la chiamata consultaGara. Contattare un amministratore.");
      hm.put("esito", Boolean.FALSE);
      hm.put("errorMsg", "Errore di configurazione: manca l'indirizzo per la connessione "
          + "al WS Osservatorio. Contattare un amministratore.");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraAccessoIndirettoPddXmlSimog: fine metodo");
    }
    
    return hm;
  }
  
  /**
   * Consulta gara indiretto attraverso SitatORT, che accede al WS Simog attraverso la PDD per il 
   * recupero da SIMOG del xml di un lotto di gara.
   * 
   * @param idAvGara
   * @param idAvGara
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException
   */
  private HashMap<String, Object> consultaGaraAccessoIndirettoPddCigGaraSimog(final String idAvGara,
      final long syscon, final String codUffInt) {

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraAccessoIndirettoPddCigGaraSimog: inizio metodo");
    }
    
    HashMap<String, Object> hm = new HashMap<String, Object>();
    String urlWsORT = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    
    if (StringUtils.isNotEmpty(urlWsORT)) {
      WsOsservatorioProxy wsOsservatorio = new WsOsservatorioProxy(urlWsORT);
      String[] codiciCig = null;
      
      try {
        org.apache.axis.client.Stub stubClient = (Stub) wsOsservatorio.getWsOsservatorio_PortType();
        
        String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
        if (StringUtils.isNotEmpty(strTimeOut)) {
          if (logger.isDebugEnabled()) {
            logger.debug("Prima. Default timeout: " + HTTPConstants.SO_TIMEOUT + "="
                + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
                + " ." + HTTPConstants.CONNECTION_TIMEOUT + "="
                + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT));
          }
        
          logger.debug("Valorizzazione del timeout nella richiesta al WsOsservatorio");
          stubClient.setTimeout(new Integer(strTimeOut).intValue());
        
          if (logger.isDebugEnabled()) {
            logger.debug("Dopo. Default timeout: " + HTTPConstants.SO_TIMEOUT + "="
                + stubClient._getProperty(HTTPConstants.SO_TIMEOUT)
                + " ." + HTTPConstants.CONNECTION_TIMEOUT + "="
                + stubClient._getProperty(HTTPConstants.CONNECTION_TIMEOUT)
                + " . stubClient.getTimeout()=" + stubClient.getTimeout());
          }
        } 
        it.toscana.rete.rfc.sitatsa.client.ResponseConsultaGara responseConsultaGara = null;

        // Ricezione dei dati di tutti i lotti e memorizzazione nell'oggetto hmCigResponseConsultaGara
        logger.info("Invocazione del metodo getGaraXML del WsOsservatorio (IdAvGara=" + idAvGara
            + ", syscon=" + syscon + ", codein=" + codUffInt + ")");

        // Si effettua la chiamata all'osservatorio senza il controllo preliminare dei dati.
        // In questo modo si permette l'importazione di gare a più lotti con eventuali lotti
        // non perfezionati o cancellati.
        responseConsultaGara = wsOsservatorio.getGaraXML(null, idAvGara, null, null, false);

        logger.info("Invocato il metodo getGaraXML del WsOsservatorio (IdAvGara=" + idAvGara
            + ", syscon=" + syscon + ", codein=" + codUffInt + ")");
        
        if (responseConsultaGara.isSuccess()) {
          ConsultaNumeroGaraResponseDocument consultaNumeroGaraResponseDocument = ConsultaNumeroGaraResponseDocument.Factory.parse(responseConsultaGara.getGaraXML());
          
          codiciCig = consultaNumeroGaraResponseDocument.getConsultaNumeroGaraResponse().getReturn().getSchedaGaraCig().getCIGArray();
          hm.put("codiciCig", codiciCig);
          hm.put("esito", Boolean.TRUE);
          
        } else {
          String strMsg = "La richiesta a SIMOG dei CIG associati alla gara con IdGara=" + idAvGara
          + " e' terminata con esito negativo. Di seguito il messaggio di ritorno da SIMOG: "; 
          logger.error(strMsg.concat(responseConsultaGara.getError()));
          
          hm.put("esito", Boolean.FALSE);
          hm.put("msgErr", responseConsultaGara.getError());
          codiciCig = null;
        }

      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con il WS Osservatorio per la chiamata consultaGara "
            + "durante la funzione 'Importa da SIMOG / Prendi in carico' con idAvGara='" + idAvGara
            + " da parte dell'utente syscon = " + syscon 
            + " per conto della stazione appaltante con codUffInt=" + codUffInt, re);
        
        hm.put("esito", Boolean.FALSE);
        hm.put("errorMsg", "Errore nel recupero dei dati su SIMOG");

      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento al WS Osservatorio per la chiamata "
            + "consultaGara durante la funzione 'Importa da SIMOG / Prendi in carico' con idAvGara='"
            + idAvGara + " da parte dell'utente syscon = " + syscon
            + " per conto della stazione appaltante con codUffInt=" + codUffInt, t);

        hm.put("esito", Boolean.FALSE);
      } finally {
        if (Boolean.FALSE.equals(hm.get("esito"))) {
          hm.put("msgErr", "Il servizio SIMOG e' momentaneamente non disponibile o non "
              + "risponde correttamente. Riprovare piu' tardi. Se il problema persiste, segnalarlo "
              + "all'amministratore di sistema.");
        }
      }

    } else {
      logger.error("Errore di configurazione: manca l'indirizzo per la connessione al "
          + "WS Ossevatorio per la chiamata consultaGara. Contattare un amministratore.");
      hm.put("esito", Boolean.FALSE);
      hm.put("errorMsg", "Errore di configurazione: manca l'indirizzo per la connessione "
          + "al WS Osservatorio. Contattare un amministratore.");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraAccessoIndirettoPddCigGaraSimog: fine metodo");
    }
    return hm;
  }
      
  /**
   * Riallineamento dati Gara e Lotti dal Simog
   * 
   * @param codiceCIG
   * @param codGara
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @param simoguser se valorizzato la chiamata ai servizi Simog sarà fatta con questo utente
   * @param simogpass se valorizzato la chiamata ai servizi Simog sarà fatta con questa password
   * @throws SQLException
   */
  private synchronized void riallineaDatiSimogAccessoDiretto(final String codiceCIG,
      final long codGara, final String codUffInt, final int syscon,
      HashMap<String, Object> resultMap, final String simoguser, final String simogpass) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimogAccessoDiretto: inizio metodo");
    }
    
    String urlWsSimogPDD = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);

    if (!StringUtils.isNotEmpty(urlWsSimogPDD)) {
      logger.error("URL della PDD per accesso ai servizi SIMOG non e' definita");
    }
    
    String loginWsSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
    if (StringUtils.isEmpty(simoguser)) {
      if (!StringUtils.isNotEmpty(loginWsSimog)) {
        logger.error("Login per accesso ai servizi SIMOG non e' definita");
      }
    } else {
      loginWsSimog = simoguser;
    }
    
    String passwordWsSimog = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD);
    if (StringUtils.isEmpty(simogpass)) {
      if (StringUtils.isNotEmpty(passwordWsSimog)) {
        try {
          ICriptazioneByte cript = FactoryCriptazioneByte.getInstance(
              ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
              passwordWsSimog.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
          passwordWsSimog = new String(cript.getDatoNonCifrato());
        } catch (CriptazioneException e) {
          logger.error("Errore nella decriptazione della password di accesso ai servizi SIMOG", e);
        }
      } else {
        logger.error("Passowrd per accesso ai servizi SIMOG non e' definita");
      }
    } else {
      passwordWsSimog = simogpass;
    }
    
    SimogWSPDDServiceStub simogWSOssStub = null;
    
    if (StringUtils.isNotEmpty(urlWsSimogPDD)
        && StringUtils.isNotEmpty(loginWsSimog)
        && StringUtils.isNotEmpty(passwordWsSimog)) {

      try {
        ResponseCheckLogin responseCheckLogin = null;
        
       	simogWSOssStub = new SimogWSPDDServiceStub(urlWsSimogPDD);
       	this.ticketSimogManager.setProxy(simogWSOssStub);

        LoginDocument loginDoc = LoginDocument.Factory.newInstance();
        Login loginIn = Login.Factory.newInstance();
        loginIn.setLogin(loginWsSimog);
        loginIn.setPassword(passwordWsSimog);
        loginDoc.setLogin(loginIn);

        LoginResponseDocument loginResponseDoc = simogWSOssStub.login(loginDoc);
        LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
        if (loginResponse.isSetReturn()) {
          responseCheckLogin = loginResponse.getReturn();

          if (responseCheckLogin.getSuccess()) {
            String ticket = responseCheckLogin.getTicket();
  
            // Per accordi con Regione Toscana e SIMOG le collaborazioni non vengono popolate.
            // Al momento l'istruzione rispostaLogin.getColl() non deve essere usata.
            // Per il valore da assegnare a schede, si seguono le indicazioni degli accordi
            // menzionati: schede viene sempre settato a '0'
            
            ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
            consultaGara.setTicket(ticket);
            consultaGara.setSchede(CostantiSimog.CONSULTA_GARA_SCHEDE);
            consultaGara.setCIG(codiceCIG);
            
            ConsultaGaraDocument consultaGaraDoc = ConsultaGaraDocument.Factory.newInstance();
            consultaGaraDoc.setConsultaGara(consultaGara);
            
            this.ticketSimogManager.setProxy(simogWSOssStub);
            
            ConsultaGaraResponseDocument consultaGaraResponseDoc = simogWSOssStub.consultaGara(consultaGaraDoc);
            
            ResponseConsultaGara responseConsultaGara =
                consultaGaraResponseDoc.getConsultaGaraResponse().getReturn();
            
            this.riallineaDatiSimog_PDD_accessoDiretto(codiceCIG, codGara,
                responseConsultaGara, codUffInt, syscon, resultMap);

            // Chiusura della connessione
            ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
            chiudiSessione.setTicket(ticket);
          
            ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
            chiudiSessioneDoc.setChiudiSessione(chiudiSessione);

            simogWSOssStub = new SimogWSPDDServiceStub(urlWsSimogPDD);

            this.ticketSimogManager.setProxy(simogWSOssStub);
            
            ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = simogWSOssStub.chiudiSessione(chiudiSessioneDoc);
            ResponseChiudiSession responseChiudiSessione = chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();

            if (!responseChiudiSessione.getSuccess()) {
              if (!responseChiudiSessione.getSuccess()) {
                logger.error("La chiusura della connessione identificata dal ticket "
                    + ticket
                    + " ha generato il seguente errore: "
                    + responseChiudiSessione.getError());
              }
            }
          } else {
            String message = "Errore durante la connessione alla porta di dominio per il servizio SIMOG: ";
            message += responseCheckLogin.getError();
            logger.error(message);
  
            resultMap.put("esito", Boolean.FALSE);
            resultMap.put("errorMsg", message);
          }
        } else {
          
          //la loginResponse non ha nulla di settato al suo interno!!!
        }
      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con la porta di dominio per accesso al metodo consultaGara del "
            + "WS Simog con codiceCIG=" + codiceCIG + ", codiceGara=" + codGara
            + ", da parte dell'utente syscon = " + syscon
            + " per conto della stazione appaltante con codUffInt=" + codUffInt, re);
      } catch (SQLException e) {
        logger.error("Errore nelle operazioni SQL di aggiornamento delle tabelle W9GARA, W9LOTT "
            + "e W9LOTTCATE durante la funzione 'Carica lotto da SIMOG' con codiceCIG="
            + codiceCIG + ", codiceGara=" + codGara + "', da parte dell'utente syscon = "
            + syscon + " per conto della stazione appaltante con codUffInt=" + codUffInt, e);
        throw e;
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento alla porta di dominio per l'accesso "
            + "al WS SIMOG durante la chiamata consultaGara con codiceCIG="
            + codiceCIG + ", codiceGara=" + codGara + ", da parte dell'utente syscon = "
            + syscon + " per conto della stazione appaltante con codUffInt="
            + codUffInt, t);
        
        resultMap.put("esito", Boolean.FALSE);
        
      } finally {
        if (!resultMap.containsKey("esito")) {
          resultMap.put("esito", Boolean.FALSE);
        }
        if (Boolean.FALSE.equals(resultMap.get("esito")) && !resultMap.containsKey("errorMsg")) {
          resultMap.put("errorMsg", "Errore nell'interazione con la porta di dominio per accesso al " +
          		"metodo consultaGara del WS Simog. Contattare un amministratore");
        }
      }
    } else {
      logger.error("Errore di configurazione: manca l'indirizzo per la connessione alla porta di dominio per "
          + "accesso al metodo consultaGara del WS Simog la chiamata consultaGara. Contattare un amministratore.");
      resultMap.put("esito", Boolean.FALSE);
      resultMap.put("errorMsg", "Errore di configurazione: manca l'indirizzo per la connessione alla porta di " +
      		"dominio per l'accesso al metodo consultaGara del WS Simog. Contattare un amministratore.");
    }
        
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimogAccessoDiretto: fine metodo");
    }
  }

  

  
  /**
   * Riallinea dati da Simog, con accesso attraverso SitatORT.
   *  
   * @param codiceCIG
   * @param codGara
   * @param codUffInt
   * @param syscon
   * @param resultMap
   * @throws SQLException
   */
  private synchronized void riallineaDatiSimogAccessoIndiretto(final String codiceCIG,
      final long codGara, final String codUffInt, final int syscon,
      HashMap<String, Object> resultMap) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimogAccessoIndiretto: inizio metodo");
    }
    
    String urlWsORT = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    String cfsa = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?",
        new Object[] { codUffInt });
    String cfrup = (String) this.sqlManager.getObject(
        "select CFTEC from TECNI where SYSCON=? and CGENTEI=?",
        new Object[] { new Long(syscon),  codUffInt});
    
    if (StringUtils.isNotEmpty(urlWsORT)) {
      WsOsservatorio_ServiceLocator locatorIdGaraCig = new WsOsservatorio_ServiceLocator();
      locatorIdGaraCig.setWsOsservatorioEndpointAddress(urlWsORT);

      try {
        WsOsservatorio_PortType servizioOsservatorio = locatorIdGaraCig.getWsOsservatorio();

        if (logger.isDebugEnabled()) {
          logger.debug("Riallinea dati SIMOG: la connessione al servizio per la richiesta "
              + "consultaGara e' avvenuta correttamente");
        }
        it.toscana.rete.rfc.sitatsa.client.ResponseConsultaGara responseConsultaGara = 
            servizioOsservatorio.getGaraXML(codiceCIG, null, cfrup, cfsa, true);
        
        this.riallineaDatiSimog_PDD_accessoIndiretto(codiceCIG, codGara, responseConsultaGara,
            codUffInt, syscon, resultMap);

      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con il WS Osservatorio per la chiamata consultaGara "
            + "durante la funzione 'Carica lotto da SIMOG' con codiceCIG='" + codiceCIG + "', codiceGara='" + codGara
            + "', da parte dell'utente syscon = " + syscon + " per conto della stazione appaltante con codUffInt='"
            + codUffInt + "'", re);
        
        resultMap.put("esito", Boolean.FALSE);
      } catch (ServiceException se) {
        logger.error("Errore nel collegamento al WS Osservatorio per la chiamata consultaGara "
            + "durante la funzione 'Carica lotto da SIMOG' con codiceCIG='" + codiceCIG + "', codiceGara='" + codGara
            + "', da parte dell'utente syscon = " + syscon + " per conto della stazione appaltante con codUffInt='"
            + codUffInt + "'", se);
        
        resultMap.put("esito", Boolean.FALSE);
      } catch (SQLException e) {
        logger.error("Errore nelle operazioni SQL di aggiornamento delle tabelle W9GARA, W9LOTT e W9LOTTCATE "
            + "durante la funzione 'Carica lotto da SIMOG' con codiceCIG='" + codiceCIG + "', codiceGara='" + codGara
            + "', da parte dell'utente syscon = " + syscon + " per conto della stazione appaltante con codUffInt='"
            + codUffInt + "'", e);
        
        throw e;
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento alla porta di dominio per l'accesso "
            + "al WS SIMOG durante la chiamata consultaGara con codiceCIG="
            + codiceCIG + ", da parte dell'utente syscon = "
            + syscon + " per conto della stazione appaltante con codUffInt="
            + codUffInt, t);
        
        resultMap.put("esito", Boolean.FALSE);
      } finally {
        if (!resultMap.containsKey("esito")) {
          resultMap.put("esito", Boolean.FALSE);
        }
        if (Boolean.FALSE.equals(resultMap.get("esito")) && !resultMap.containsKey("errorMsg")) {
          resultMap.put("errorMsg", "Il servizio SIMOG e' momentaneamente non disponibile o non risponde correttamente. Riprovare piu' tardi. Se il problema persiste, segnalarlo all'amministratore di sistema.");
          //resultMap.put("errorMsg", "Errore durante l'operazione 'Carica lotto da SIMOG'. Contattare un amministratore");
        }
      }
    } else {
      logger.error("Errore di configurazione: manca l'indirizzo per la connessione al WS Osservatorio per "
          + "la chiamata consultaGara. Contattare un amministratore.");
      resultMap.put("esito", Boolean.FALSE);
      resultMap.put("errorMsg", "Errore di configurazione: manca l'indirizzo per la connessione al WS "
          + "Osservatorio. Contattare un amministratore.");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimogAccessoIndiretto: fine metodo");
    }
  }
  
  private synchronized void riallineaDatiSimog_PDD_accessoIndiretto(
      final String codiceCIG, final long codGara,
      it.toscana.rete.rfc.sitatsa.client.ResponseConsultaGara responseConsultaGaraPDD,
      final String codUffInt, final int syscon,
      HashMap<String, Object> resultMap) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimog_PDD_accessoIndiretto: inizio metodo");
    }

    try {
      if (responseConsultaGaraPDD.isSuccess()) {
        
        boolean operazioneEseguita = false;
        
        try {
          ConsultaGaraResponseDocument consultaGaraResponseDocument = ConsultaGaraResponseDocument.Factory.parse(
                  responseConsultaGaraPDD.getGaraXML());
  
          GaraType garaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().getDatiGara().getGara();
          LottoType lottoType =
            consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().getDatiGara().getLotto();
          
          String modoRealizzazioneGaraInSimog = null;
          String flagLottoEsclusoInSimog = null;
          BigDecimal importoLottoInSimog = lottoType.getIMPORTOLOTTO();
          
          if (StringUtils.isNotEmpty(garaType.getMODOREALIZZAZIONE())) {
            modoRealizzazioneGaraInSimog = garaType.getMODOREALIZZAZIONE();
          }
          if (StringUtils.isNotEmpty(lottoType.getFLAGESCLUSO())) {
            flagLottoEsclusoInSimog = lottoType.getFLAGESCLUSO().toString();
          }
          
          if (this.isGaraLottoSimogCompatibile(codiceCIG, importoLottoInSimog,
              flagLottoEsclusoInSimog, modoRealizzazioneGaraInSimog)) {
            this.riallineaDatiSimogUnificatoPDD(codiceCIG, codGara, codUffInt, syscon,
                consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML(),
                garaType, lottoType, resultMap);
  
          } else {
            logger.info("RiallineaDatiSimog: operazione interotta perche' gara/lotto scaricati da SIMOG sono " +
                "incompatbili con i dati di gara/lotto nella base dati");
            
            resultMap.put("esito", Boolean.FALSE);
            resultMap.put("errorMsg", "ATTENZIONE: le schede gia' inserite sono incompatibili con i dati presenti in SIMOG. Contattare un amministratore");
          }
        
          operazioneEseguita = true;
          
        } catch (ClassCastException cce) {
          logger.error("Errore nel parsing del XML ricevuto da Simog (1)", cce);
          operazioneEseguita = false;
        }

        if (! operazioneEseguita) {

          try {
            it.avlp.simog.massload.xmlbeans.SchedaDocument schedaDocumentType = 
              it.avlp.simog.massload.xmlbeans.SchedaDocument.Factory.parse(
                responseConsultaGaraPDD.getGaraXML());
            
            SchedaType schedaType = schedaDocumentType.getScheda();
            GaraType garaType = schedaType.getDatiGara().getGara();
            LottoType lottoType = schedaType.getDatiGara().getLotto();

            String modoRealizzazioneGaraInSimog = null;
            String flagLottoEsclusoInSimog = null;
            BigDecimal importoLottoInSimog = lottoType.getIMPORTOLOTTO();
            
            if (StringUtils.isNotEmpty(garaType.getMODOREALIZZAZIONE())) {
              modoRealizzazioneGaraInSimog = garaType.getMODOREALIZZAZIONE();
            }
            if (StringUtils.isNotEmpty(lottoType.getFLAGESCLUSO())) {
              flagLottoEsclusoInSimog = lottoType.getFLAGESCLUSO().toString();
            }

            if (this.isGaraLottoSimogCompatibile(codiceCIG, importoLottoInSimog, flagLottoEsclusoInSimog,
                modoRealizzazioneGaraInSimog)) {
              this.riallineaDatiSimogUnificatoPDD(codiceCIG, codGara, codUffInt,
                  syscon, schedaType, garaType, lottoType, resultMap);
            } else {
              logger.info("RiallineaDatiSimog: operazione interotta perche' gara/lotto scaricati da SIMOG sono " +
                  "incompatbili con i dati di gara/lotto nella base dati");
              
              resultMap.put("esito", Boolean.FALSE);
              resultMap.put("errorMsg", "ATTENZIONE: le schede gia' inserite sono incompatibili con i dati presenti in SIMOG. Contattare un amministratore");
            }
            operazioneEseguita =  true;
          } catch (ClassCastException cce) {
            logger.error("Errore nel parsing del XML ricevuto da Simog (2)", cce);
            operazioneEseguita =  false;
          }
        }
      } else {
        logger.error("La richiesta consultaGara ha risposto con esito negativo.");
        logger.error("Messaggio di ritorno dal WS di AVCP: " + responseConsultaGaraPDD.getError());
  
        resultMap.put("esito", Boolean.FALSE);
        resultMap.put("errorMsg", responseConsultaGaraPDD.getError()); 
      }
    } catch (XmlException xe) {
      logger.error("Errore nella deserializzazione della risposta della chiamata consultaGara "
          + "durante la funzione 'Carica lotto da SIMOG' con codiceCIG='" + codiceCIG + "', codiceGara='" + codGara
          + "', da parte dell'utente syscon = " + syscon + " per conto della stazione appaltante con codUffInt='"
          + codUffInt + "'", xe);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimog_PDD_accessoIndiretto: fine metodo");
    }
  }
  
  private synchronized void riallineaDatiSimog_PDD_accessoDiretto(
      final String codiceCIG, long codGara,
      final it.avlp.simog.massload.xmlbeans.ResponseConsultaGara responseConsultaGaraPDD,
      final String codUffInt, final int syscon, HashMap<String, Object> resultMap) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimog_PDD_accessoDiretto: inizio metodo");
    }
    
    if (responseConsultaGaraPDD.getSuccess()) {
  
      it.avlp.simog.massload.xmlbeans.GaraType garaType =
        responseConsultaGaraPDD.getGaraXML().getDatiGara().getGara();
      it.avlp.simog.massload.xmlbeans.LottoType lottoType =
        responseConsultaGaraPDD.getGaraXML().getDatiGara().getLotto();
      
      String modoRealizzazioneGaraInSimog = null;
      String flagLottoEsclusoInSimog = null;
      BigDecimal importoLottoInSimog = lottoType.getIMPORTOLOTTO();
      
      if (StringUtils.isNotEmpty(garaType.getMODOREALIZZAZIONE())) {
        modoRealizzazioneGaraInSimog = garaType.getMODOREALIZZAZIONE();
      }
      if (StringUtils.isNotEmpty(lottoType.getFLAGESCLUSO())) {
        flagLottoEsclusoInSimog = lottoType.getFLAGESCLUSO().toString();
      }
      
      if (this.isGaraLottoSimogCompatibile(codiceCIG, importoLottoInSimog, flagLottoEsclusoInSimog,
          modoRealizzazioneGaraInSimog)) {
        this.riallineaDatiSimogUnificatoPDD(codiceCIG, codGara, codUffInt, syscon,
            responseConsultaGaraPDD.getGaraXML(),
            responseConsultaGaraPDD.getGaraXML().getDatiGara().getGara(),
            responseConsultaGaraPDD.getGaraXML().getDatiGara().getLotto(), resultMap);
      } else {
        logger.info("RiallineaDatiSimog: operazione interotta perche' gara/lotto scaricati da SIMOG sono " +
            "incompatbili con i dati di gara/lotto nella base dati");
        
        resultMap.put("esito", Boolean.FALSE);
        resultMap.put("errorMsg", "ATTENZIONE: le schede gia' inserite sono incompatibili con i dati presenti in SIMOG. Contattare un amministratore");
      }

    } else {
      logger.error("La richiesta consultaGara con codice CIG pari a '" + codiceCIG + "' e' terminata "
          + "con esito negativo, fornendo il seguente messaggio: " + responseConsultaGaraPDD.getError());

      resultMap.put("errorMsg", responseConsultaGaraPDD.getError());
      resultMap.put("esito", Boolean.FALSE);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimog_PDD_accessoDiretto: fine metodo");
    }
  }
  
  private synchronized void riallineaDatiSimogUnificatoPDD(
      final String codiceCIG, final long codGara, final String codUffInt, final int syscon,
      final it.avlp.simog.massload.xmlbeans.SchedaType schedaType,
      final it.avlp.simog.massload.xmlbeans.GaraType garaType,
      final it.avlp.simog.massload.xmlbeans.LottoType lottoType,
      HashMap<String, Object> resultMap) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimogUnificatoPDD: inizio metodo");
    }

    GregorianCalendar dataCreazioneGara = null;
    if (garaType.isSetDATACREAZIONE()) {
      dataCreazioneGara = new GregorianCalendar(
          garaType.getDATACREAZIONE().get(Calendar.YEAR), 
          garaType.getDATACREAZIONE().get(Calendar.MONTH), 
          garaType.getDATACREAZIONE().get(Calendar.DAY_OF_MONTH), 0, 0, 0);
      dataCreazioneGara.set(Calendar.MILLISECOND, 0);
    }
    
    GregorianCalendar dataPerfezionamentoBando = null;
    if (garaType.isSetDATAPERFEZIONAMENTOBANDO()) {
      dataPerfezionamentoBando = new GregorianCalendar(
          garaType.getDATAPERFEZIONAMENTOBANDO().get(Calendar.YEAR),
          garaType.getDATAPERFEZIONAMENTOBANDO().get(Calendar.MONTH),
          garaType.getDATAPERFEZIONAMENTOBANDO().get(Calendar.DAY_OF_MONTH), 0, 0, 0);
      dataPerfezionamentoBando.set(Calendar.MILLISECOND, 0);
    }
    
    // I campi da aggiornare in W9GARA sono: OGGETTO, IMPORTO_GARA, DATA_PUBBLICAZIONE, TIPO_APP, 
    // ID_MODO_INDIZIONE, FLAG_ENTE_SPECIALE, CIG_ACCQUADRO, DURATA_ACCQUADRO, SOMMA_URGENZA e VER_SIMOG
      
    List<String> listaSqlGaraCampi = new ArrayList<String>();
    List<Object> listaSqlGaraParams = new ArrayList<Object>();
    listaSqlGaraCampi.add("OGGETTO=? ");
    listaSqlGaraParams.add(garaType.getOGGETTO());
    listaSqlGaraCampi.add("IMPORTO_GARA=? ");
    listaSqlGaraParams.add(new Double(garaType.getIMPORTOGARA().doubleValue()));

    if (garaType.isSetDATAPERFEZIONAMENTOBANDO()) {
      listaSqlGaraCampi.add("DATA_PUBBLICAZIONE=? ");
      listaSqlGaraParams.add(garaType.getDATAPERFEZIONAMENTOBANDO().getTime());
    } else {
      listaSqlGaraCampi.add("DATA_PUBBLICAZIONE=? ");
      listaSqlGaraParams.add(null);
    }

    if (garaType.isSetMODOREALIZZAZIONE()) {
      listaSqlGaraCampi.add("TIPO_APP=? ");
      listaSqlGaraParams.add(new Long(garaType.getMODOREALIZZAZIONE()));
    } else {
      listaSqlGaraCampi.add("TIPO_APP=? ");
      listaSqlGaraParams.add(new Long(garaType.getMODOREALIZZAZIONE()));
    }

    if (garaType.isSetMODOINDIZIONE()) {
      listaSqlGaraCampi.add("ID_MODO_INDIZIONE=? ");
      listaSqlGaraParams.add(new Long(garaType.getMODOINDIZIONE()));
    } else {
      listaSqlGaraCampi.add("ID_MODO_INDIZIONE=? ");
      listaSqlGaraParams.add(null);
    }
    
    if (garaType.isSetTIPOSCHEDA()) {
      listaSqlGaraCampi.add("FLAG_ENTE_SPECIALE=? ");
      listaSqlGaraParams.add(garaType.getTIPOSCHEDA().toString());
    } else {
      listaSqlGaraCampi.add("FLAG_ENTE_SPECIALE=? ");
      listaSqlGaraParams.add(null);
    }

    if (garaType.isSetCIGACCQUADRO()) {
      listaSqlGaraCampi.add("CIG_ACCQUADRO=? ");
      listaSqlGaraParams.add(garaType.getCIGACCQUADRO());
    } else {
      listaSqlGaraCampi.add("CIG_ACCQUADRO=? ");
      listaSqlGaraParams.add(null);
    }
    
    if(garaType.isSetDURATAACCQUADROCONVENZIONEGARA()) {
      listaSqlGaraCampi.add("DURATA_ACCQUADRO=? ");
      listaSqlGaraParams.add(new Long(garaType.getDURATAACCQUADROCONVENZIONEGARA()));
    } else if (schedaType.isSetDatiScheda() && 
        schedaType.getDatiScheda().getDatiComuni().isSetDURATAACCQUADROCONVENZIONE()) {
      listaSqlGaraCampi.add("DURATA_ACCQUADRO=? ");
      listaSqlGaraParams.add(schedaType.getDatiScheda().getDatiComuni().getDURATAACCQUADROCONVENZIONE());
    } else {
      listaSqlGaraCampi.add("DURATA_ACCQUADRO=? ");
      listaSqlGaraParams.add(null);
    }
    
    if (garaType.isSetURGENZADL133()) {
      listaSqlGaraCampi.add("SOMMA_URGENZA=? ");
      listaSqlGaraParams.add(it.avlp.simog.massload.xmlbeans.FlagSNType.S.equals(garaType.getURGENZADL133()) ? "1" : "2" );
    } else {
      listaSqlGaraCampi.add("SOMMA_URGENZA=? ");
      listaSqlGaraParams.add(null);
    }
    
    listaSqlGaraCampi.add("VER_SIMOG=? ");
    int versioneSIMOG = UtilitySITAT.getVersioneSimog(dataCreazioneGara, dataPerfezionamentoBando);
 		listaSqlGaraParams.add(new Long(versioneSIMOG));
    
 		if (garaType.isSetDATACREAZIONE()) {
 		  listaSqlGaraCampi.add("DATA_CREAZIONE=? ");
 		  listaSqlGaraParams.add(garaType.getDATACREAZIONE().getTime());
 		} else {
 		  listaSqlGaraCampi.add("DATA_CREAZIONE=? ");
 		  listaSqlGaraParams.add(null);
 		}
 		
 		if (versioneSIMOG >= 5) {
      if (garaType.isSetFLAGSAAGENTEGARA()) {
        listaSqlGaraCampi.add("FLAG_SA_AGENTE=? ");
        listaSqlGaraParams.add(it.avlp.simog.massload.xmlbeans.FlagSNType.S.equals(garaType.getFLAGSAAGENTEGARA()) ? "1" : "2");
      } else {
        listaSqlGaraCampi.add("FLAG_SA_AGENTE=? ");
        listaSqlGaraParams.add(null);
      }
      if (garaType.isSetDENAMMAGENTEGARA()) {
        listaSqlGaraCampi.add("DENOM_SA_AGENTE=? ");
        listaSqlGaraParams.add(garaType.getDENAMMAGENTEGARA().toString());
      } else {
        listaSqlGaraCampi.add("DENOM_SA_AGENTE=? ");
        listaSqlGaraParams.add(null);
      }
      if (garaType.isSetCFAMMAGENTEGARA()) {  
        listaSqlGaraCampi.add("CF_SA_AGENTE=? ");
        listaSqlGaraParams.add(garaType.getCFAMMAGENTEGARA().toString());
      } else {
        listaSqlGaraCampi.add("CF_SA_AGENTE=? ");
        listaSqlGaraParams.add(null);
      }
      if (garaType.isSetIDFDELEGATE()) {
        listaSqlGaraCampi.add("ID_F_DELEGATE=? ");
        listaSqlGaraParams.add(new Long(garaType.getIDFDELEGATE()));
      } else {
        listaSqlGaraCampi.add("ID_F_DELEGATE=? ");
        listaSqlGaraParams.add(null);
      }
 		}
    // Valorizzazione del centro di costo W9GARA.IDCC e creazione di un nuovo record
    // in CENTRICOSTO, se non esistente
    String codiceCentroCostoFromSIMOG = garaType.getIDSTAZIONEAPPALTANTE();
    String descrCentroCostoFromSIMOG = garaType.getDENOMSTAZIONEAPPALTANTE();      
    Long idCentroCosto = (Long) this.sqlManager.getObject(
        "select IDCENTRO from CENTRICOSTO where UPPER(CODCENTRO)=? and CODEIN=?",
        new Object[] { codiceCentroCostoFromSIMOG.toUpperCase(), codUffInt });
    
    if (idCentroCosto == null) {
      Long maxIdCentroCosto = (Long) this.sqlManager.getObject("select max(IDCENTRO)+1 from CENTRICOSTO", 
          new Object[]{});
      if (maxIdCentroCosto == null) {
        idCentroCosto = new Long(1);
      } else {
        idCentroCosto = maxIdCentroCosto;
      }
      this.sqlManager.update("INSERT INTO CENTRICOSTO (IDCENTRO,CODEIN,CODCENTRO,DENOMCENTRO) VALUES (?,?,?,?)",
          new Object[] { idCentroCosto, codUffInt, codiceCentroCostoFromSIMOG, descrCentroCostoFromSIMOG } );
    } else {
      this.sqlManager.update("UPDATE CENTRICOSTO set DENOMCENTRO=? where IDCENTRO=?", 
          new Object[] { StringUtils.substring(descrCentroCostoFromSIMOG,0,250), idCentroCosto });
    }
    
    listaSqlGaraCampi.add("IDCC=?");
    listaSqlGaraParams.add(idCentroCosto);
    
    listaSqlGaraParams.add(new Long(codGara));

    // Update di W9GARA
    String sqlUpdateW9GARA = "update W9GARA set LISTACAMPI where CODGARA=?";

    StringBuffer listaCampi = new StringBuffer("");
    for (int ir = 0; ir < listaSqlGaraCampi.size(); ir++) {
      listaCampi.append(listaSqlGaraCampi.get(ir) + ", ");
    }

    sqlUpdateW9GARA = sqlUpdateW9GARA.replaceAll("LISTACAMPI", 
        listaCampi.toString().substring(0, listaCampi.length() - 2));
    this.sqlManager.update(sqlUpdateW9GARA, listaSqlGaraParams.toArray());
    
    // I campi da aggiornare in W9LOTT sono OGGETTO, SOMMA_URGENZA, IMPORTO_LOTTO,
    // IMPORTO_ATTUAZIONE_SICUREZZA, IMPORTO_TOT, CPV, ID_SCELTA_CONTRAENTE,
    // ID_CATEGORIA_PREVALENTE, FLAG_ENTE_SPECIALE, TIPO_CONTRATTO
    
    List<String> listaSqlLottoCampi = new ArrayList<String>();
    List<Object> listaSqlLottoParams = new ArrayList<Object>();
    
    listaSqlLottoCampi.add("OGGETTO=?");
    listaSqlLottoParams.add(lottoType.getOGGETTO());
    
    listaSqlLottoCampi.add("IMPORTO_LOTTO=?");
    if (lottoType.isSetIMPORTOATTUAZIONESICUREZZA()) {
      listaSqlLottoParams.add(new Double(lottoType.getIMPORTOLOTTO().doubleValue()
          - new Double(lottoType.getIMPORTOATTUAZIONESICUREZZA()).doubleValue()));
    } else {
      listaSqlLottoParams.add(new Double(lottoType.getIMPORTOLOTTO().doubleValue()));
    }
    
    listaSqlLottoCampi.add("CPV=?");
    listaSqlLottoParams.add(lottoType.getCPV());

    listaSqlLottoCampi.add("ID_SCELTA_CONTRAENTE=?");
    listaSqlLottoParams.add(new Long(lottoType.getIDSCELTACONTRAENTE()));
    
    //verifico se ID_SCELTA_CONTRAENTE_50 è già valorizzato per questa gara/cig
    if (dataPerfezionamentoBando.before(new GregorianCalendar(2013, 9, 29, 0, 0, 0))) {
    	listaSqlLottoCampi.add("ID_SCELTA_CONTRAENTE_50=?");
     	listaSqlLottoParams.add(this.getIdSceltaContraente50(lottoType.getIDSCELTACONTRAENTE()));
    } else {
      listaSqlLottoCampi.add("ID_SCELTA_CONTRAENTE_50=?");
      listaSqlLottoParams.add(null);
    }
    
    if (lottoType.isSetSOMMAURGENZA()) {
      listaSqlLottoCampi.add("SOMMA_URGENZA=?");
      listaSqlLottoParams.add(
          it.avlp.simog.massload.xmlbeans.FlagSNType.S.equals(lottoType.getSOMMAURGENZA()) ? "1" : "2");
    } else {
      listaSqlLottoCampi.add("SOMMA_URGENZA=?");
      listaSqlLottoParams.add(null);
    }

    listaSqlLottoCampi.add("ID_CATEGORIA_PREVALENTE=?");
    String categoria = (String) sqlManager.getObject(
        "select CODSITAT from W9CODICI_AVCP where CODAVCP=? and TABCOD='W3z03' ",
        new Object[] { lottoType.getIDCATEGORIAPREVALENTE() });
    
    // se manca corrispondenza inserisco il codice non decodificato
    if (categoria==null) {
    	categoria = lottoType.getIDCATEGORIAPREVALENTE();
    }
    listaSqlLottoParams.add(categoria);

    if (garaType.isSetTIPOSCHEDA()) {
      listaSqlLottoCampi.add("FLAG_ENTE_SPECIALE=?");
      listaSqlLottoParams.add(garaType.getTIPOSCHEDA().toString());
    }
    
    listaSqlLottoCampi.add("IMPORTO_ATTUAZIONE_SICUREZZA=?");
    if (lottoType.isSetIMPORTOATTUAZIONESICUREZZA()) {
      listaSqlLottoParams.add(new Double(lottoType.getIMPORTOATTUAZIONESICUREZZA()));
    } else {
      listaSqlLottoParams.add(new Double(0));
    }

    listaSqlLottoCampi.add("IMPORTO_TOT=?");
    listaSqlLottoParams.add(new Double(lottoType.getIMPORTOLOTTO().doubleValue()));

    if (lottoType.isSetTIPOCONTRATTO()) {
      listaSqlLottoCampi.add("TIPO_CONTRATTO=?");
      listaSqlLottoParams.add(lottoType.getTIPOCONTRATTO().toString());

      if (lottoType.getTIPOCONTRATTO().toString().equalsIgnoreCase(W3Z05Type.L.toString())) {
        listaSqlLottoCampi.add("MANOD=?");
        listaSqlLottoParams.add("1");
      }
    }

    listaSqlLottoCampi.add("DAEXPORT=?");
    listaSqlLottoParams.add("1");

    // Valorizzazione campo ART_E1: S -> 1, N o null -> 2
    listaSqlLottoCampi.add("ART_E1=?");
    int versioneSimog = UtilitySITAT.getVersioneSimog(this.sqlManager, codGara);
    if (versioneSimog <= 2) {
      if (lottoType.isSetIDESCLUSIONE()) {
        String strArtEsclusione = lottoType.getIDESCLUSIONE();
        int artEsclusione = Integer.parseInt(strArtEsclusione);
        if (artEsclusione <= 7 || artEsclusione == 11) {
          listaSqlLottoParams.add("1");
        } else {
          listaSqlLottoParams.add("2");
        }
      } else {
        listaSqlLottoParams.add("2");
      }
    } else {
      listaSqlLottoParams.add("2");
    }

    
    if (lottoType.isSetCUPLOTTO()) {
      it.avlp.simog.massload.xmlbeans.CUPLOTTOType cupLotto = lottoType.getCUPLOTTO();

      // Per ora si legge solo il primo codice CUP presente nell'array CODICICUP
      // e si suppone che il CUP sia legato al lotto a cui e' associato 
      it.avlp.simog.massload.xmlbeans.DatiCUPType datiCup = cupLotto.getCODICICUPArray(0);
      listaSqlLottoCampi.add("CUP=?");
      listaSqlLottoParams.add(datiCup.getCUP());
      listaSqlLottoCampi.add("CUPESENTE=?");
      listaSqlLottoParams.add("2");
    } else {
      listaSqlLottoCampi.add("CUP=null");
      listaSqlLottoCampi.add("CUPESENTE=?");
      listaSqlLottoParams.add("1");
    }

    if (lottoType.isSetDATAPUBBLICAZIONE()) {
      listaSqlLottoCampi.add("DATA_PUBBLICAZIONE=?");
      listaSqlLottoParams.add(lottoType.getDATAPUBBLICAZIONE().getTime());
    }
   
    if (schedaType.isSetDatiScheda()) {
      if (!schedaType.getDatiScheda().getDatiComuni().isSetIDSCHEDASIMOG()) {
        listaSqlLottoCampi.add("ID_SCHEDA_LOCALE=?");
        if (schedaType.getDatiScheda().getDatiComuni().isSetIDSCHEDALOCALE()) {
          listaSqlLottoParams.add(schedaType.getDatiScheda().getDatiComuni().getIDSCHEDALOCALE());
        } else {
          listaSqlLottoParams.add(codiceCIG);
        }
      }
      //if (schedaType.getDatiScheda().getDatiComuni().isSetIDSCHEDASIMOG()) {
      //  listaSqlLottoCampi.add("ID_SCHEDA_SIMOG=?");
      //  listaSqlLottoParams.add(schedaType.getDatiScheda().getDatiComuni().getIDSCHEDASIMOG());
      //} else {
      //  listaSqlLottoCampi.add("ID_SCHEDA_SIMOG=null");
      //}
    } else {
  	  listaSqlLottoCampi.add("ID_SCHEDA_LOCALE=?");
  	  listaSqlLottoParams.add(codiceCIG);
  	  
  	  //listaSqlLottoCampi.add("ID_SCHEDA_SIMOG=null");
    }
    
    if (versioneSimog > 2) {
    	listaSqlLottoCampi.add("CONTRATTO_ESCLUSO_ALLEGGERITO=?");
    	if (lottoType.isSetFLAGESCLUSO()) {
    		if ("S".equals(lottoType.getFLAGESCLUSO())) {
    			listaSqlLottoParams.add("1");
    		} else {
    			listaSqlLottoParams.add("2");
    		}
    	} else {
    		listaSqlLottoParams.add("2");
    	}
  
    	listaSqlLottoCampi.add("ESCLUSIONE_REGIME_SPECIALE=?");
    	if (lottoType.isSetFLAGREGIME()) {
    		if ("S".equals(lottoType.getFLAGREGIME())) {
    			listaSqlLottoParams.add("1");
    		} else {
    			listaSqlLottoParams.add("2");
    		}
    	} else {
    		listaSqlLottoParams.add("2");
    	}
    }
    listaSqlLottoCampi.add("EXSOTTOSOGLIA=?");
    // ExSottoSoglia: se lotto.Importo < 150k e lotto.DataPubblicazione < 29/10/2013
    Double tempImportoLotto = new Double(lottoType.getIMPORTOLOTTO().doubleValue());
    boolean isDataPubbPrecedente =  false;
    boolean isImportoLottoSottoSoglia = false;
    if (tempImportoLotto.compareTo(new Double(150000)) < 0) {
      isImportoLottoSottoSoglia = true;
    }
    if (isImportoLottoSottoSoglia && dataPerfezionamentoBando != null) {
      if (dataPerfezionamentoBando.before(new GregorianCalendar(2013, 9, 29, 0, 0, 0))) {
        isDataPubbPrecedente = true;
      }
    }
    if (isImportoLottoSottoSoglia && isDataPubbPrecedente) {
      listaSqlLottoParams.add("1");
    } else {
      listaSqlLottoParams.add("2");
    }
    
    // Inserimento W9LOTT
    String sqlUpdateW9LOTT = "update W9LOTT set LISTACAMPI where CODGARA=? and CIG=?";
    listaCampi = new StringBuffer("");
    for (int ir = 0; ir < listaSqlLottoCampi.size(); ir++) {
      listaCampi.append(listaSqlLottoCampi.get(ir) + ", ");
    }

    listaSqlLottoParams.add(new Long(codGara));
    listaSqlLottoParams.add(codiceCIG);
    
    sqlUpdateW9LOTT = sqlUpdateW9LOTT.replaceAll("LISTACAMPI", 
        listaCampi.toString().substring(0, listaCampi.length() - 2));
    this.sqlManager.update(sqlUpdateW9LOTT, listaSqlLottoParams.toArray());

    // Aggiornamenti dei CPV secondari
    Long codLott = (Long) this.sqlManager.getObject("select CODLOTT from W9LOTT where CODGARA=? and CIG=?",
        new Object[] {codGara, codiceCIG } );
    // Cancellazione dei CPV secondari esistenti
    this.sqlManager.update("delete from W9CPV where CODGARA=? and CODLOTT=?", 
        new Object[] { codGara, codLott });
    // Inserimento dei CPV secondari provenienti da SIMOG
    if (lottoType.getCPVSecondariaArray() != null && lottoType.getCPVSecondariaArray().length > 0) {
      CPVSecondariaType[] cpvSecondari = lottoType.getCPVSecondariaArray();
      
      Object[] sqlW9CpvParams = new Object[4];
      sqlW9CpvParams[0] = codGara;
      sqlW9CpvParams[1] = codLott; // progressivo per lotto
      for (int cpvSec=0; cpvSec < cpvSecondari.length; cpvSec++) {
        CPVSecondariaType cpvSecondario = cpvSecondari[cpvSec];
        sqlW9CpvParams[2] = new Long((cpvSec + 1)); // progressivo
        sqlW9CpvParams[3] = cpvSecondario.getCODCPVSECONDARIA();
        this.sqlManager.update("insert into W9CPV (CODGARA, CODLOTT, NUM_CPV, CPV) values (?,?,?,?)", 
            sqlW9CpvParams);
      }
    }
    
    resultMap.put("esito", Boolean.TRUE);
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSimogUnificatoPDD: fine metodo");
    }
  }
  
  /**
   * Metodo richiamato dai metodi riallineaDatiSimog* e serve per determinare se la 
   * gara/lotto ricevuti da SIMOG (via XML) ha caratterisitiche compatibili con la 
   * gara/lotto esistenti nella base dati di SitatSA. Se i dati provenienti da SIMOG
   * sono compatibili con quelli presenti in DB, allora si continua l'operazione di
   * aggiornamento dell'anagrafica gara/lotto, altrimenti si blocca l'operazione e
   * si ritorna a video con un opportuno messaggio di errore.
   * In particolare il metodo ritorna false se:
   * - se LottoType.IMPORTO_LOTTO<40.000 or LottoType. FLAG_ESCLUSO='S' e nella base dati sono 
   *   verificate le condizioni: (isS2 and not isE1) ed esistono le fasi A05 o A06 
   * - se LottoType.IMPORTO_LOTTO>=40.000 or LottoType. FLAG_ESCLUSO='N' e nella base dati sono 
   *   verificate le condizioni: (not isS2 and isE1) ed esistono le fasi A04 o A07
   * - se GaraType.MODO_REALIZZAZIONE<>9,17,18 ed esiste la fase A20  
   * - se GaraType.MODO_REALIZZAZIONE=9,17,18 ed esistono le fasi A06 o A07
   * - se GaraType.MODO_REALIZZAZIONE<>11 e nella base dati e' verificata la condizione: isAAQ
   *   ed esiste la fase A21
   * - se GaraType.MODO_REALIZZAZIONE=11 e nella base dati e' verificata la condizione: not isAAQ
   *   ed esistono le fasi A04 o A05
   *  
   * @param codiceCIG
   * @param garaType
   * @param lottoType
   * @return Ritorna true se gara/lotto SIMOG e' compatibile con gara/lotto esistente in DB, false altrimenti
   * @throws SQLException
   */
  private boolean isGaraLottoSimogCompatibile(final String codiceCIG, BigDecimal importoLottoInSimog,
      String flagLottoEsclusoInSimog, String modoRealizzazioneGaraInSimog) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("isGaraLottoSimogCompatibile: inizio metodo");
    }
    
    boolean result = true;
    
    // Controlli preliminari prima dell'aggiornamento dell'anagrafica gara/lotto
    boolean isS2 = UtilitySITAT.isS2(codiceCIG, this.sqlManager);
    boolean isAAQ = UtilitySITAT.isAAQ(codiceCIG, this.sqlManager);
    boolean isSAQ = UtilitySITAT.isSAQ(codiceCIG, this.sqlManager);
    
    Long numAppa = new Long(1); 
    Long numeroAppalti = (Long) this.sqlManager.getObject(
    		"select count(NUM_APPA) from W9APPA a, W9LOTT l where a.CODGARA=l.CODGARA and a.CODLOTT=l.CODLOTT and l.CIG=?",
    		new Object[] { codiceCIG });
    
    if (numeroAppalti.longValue() > 1) {
    	numAppa = (Long) this.sqlManager.getObject(
      		"select max(NUM_APPA) from W9APPA a, W9LOTT l where a.CODGARA=l.CODGARA and l.CIG=?", new Object[] { codiceCIG });
    }
    
    boolean esisteA04 = UtilitySITAT.existsFase(numAppa.intValue(), codiceCIG, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, this.sqlManager);
    boolean esisteA05 = UtilitySITAT.existsFase(numAppa.intValue(), codiceCIG, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, this.sqlManager);
    boolean esisteA06 = UtilitySITAT.existsFase(numAppa.intValue(), codiceCIG, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, this.sqlManager);
    boolean esisteA07 = UtilitySITAT.existsFase(numAppa.intValue(), codiceCIG, CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO, this.sqlManager);
    boolean esisteA20 = UtilitySITAT.existsFase(numAppa.intValue(), codiceCIG, CostantiW9.STIPULA_ACCORDO_QUADRO, this.sqlManager);
    boolean esisteA21 = UtilitySITAT.existsFase(numAppa.intValue(), codiceCIG, CostantiW9.ADESIONE_ACCORDO_QUADRO, this.sqlManager);

    boolean test1, test2, test3, test4, test5, test6 = true;
    test1 = test2 = test3 = test4 = test5 = test6 = true;
    
    if ((importoLottoInSimog.doubleValue() < 40000 || ("S".equals(flagLottoEsclusoInSimog) ))
        && isS2 && (esisteA05 || esisteA06)) {
      result = false;
    	test1 = false;
      
      logger.info("isGaraLottoSimogCompatibile: il lotto con CIG=" + codiceCIG + " e la relativa gara non verificano la seguente condizione: "
          + "(LottoType.IMPORTO_LOTTO < 40.000 or LottoType.FLAG_ESCLUSO =’S’) and (S2 and NOT E1) and (esisteFaseA05 or esisteFaseA6)");
    }
    
    if (result) {
      if ((importoLottoInSimog.doubleValue() >= 40000 || "N".equals(flagLottoEsclusoInSimog))
          && !isS2 && (esisteA04 || esisteA07)) {
        result = false;
      	test2 = false;
        
        logger.info("isGaraLottoSimogCompatibile: il lotto con CIG=" + codiceCIG + " e la relativa gara non verificano la seguente condizione: "
            + "(LottoType.IMPORTO_LOTTO >= 40.000 or LottoType.FLAG_ESCLUSO =’S’) and (NOT S2 and E1) and (esisteFaseA04 or esisteFaseA7)");
      }
    }
    
    if (result) {
    	if ("-9-17-18-".indexOf("-".concat(modoRealizzazioneGaraInSimog).concat("-")) < 0 && isSAQ && esisteA20) {
        //result = false;
    		test3 = false;
        
        logger.info("isGaraLottoSimogCompatibile: il lotto con CIG=" + codiceCIG + " e la relativa gara non verificano la seguente condizione: "
            + "(GaraType.MODO_REALIZZAZIONE<> 9,17,18) and esisteFaseA20");
      }
    }
    
    if (result) {
    	if ("-9-17-18-".indexOf("-".concat(modoRealizzazioneGaraInSimog).concat("-")) >= 0 && !isSAQ && (esisteA06 || esisteA07)) {
        result = false;
    		test4= false;
        
        logger.info("isGaraLottoSimogCompatibile: il lotto con CIG=" + codiceCIG + " e la relativa gara non verificano la seguente condizione: "
            + "(GaraType.MODO_REALIZZAZIONE=9,17,18) and (esisteFaseA06 or esisteFaseA07)");
      }
    }
    
    if (result) {
      if (!"11".equals(modoRealizzazioneGaraInSimog) && esisteA21) {
        result = false;
      	test5 = false;
        
        logger.info("isGaraLottoSimogCompatibile: il lotto con CIG=" + codiceCIG + " e la relativa gara non verificano la seguente condizione: "
            + "(GaraType.MODO_REALIZZAZIONE<>11) and esisteFaseA21");
      }
    }
    
    if (result) {
      if ("11".equals(modoRealizzazioneGaraInSimog) && !isAAQ && (esisteA04 || esisteA05)) {
        result = false;
      	test6 = false;
        
        logger.info("isGaraLottoSimogCompatibile: il lotto con CIG=" + codiceCIG + " e la relativa gara non verificano la seguente condizione: "
            + "(GaraType.MODO_REALIZZAZIONE=11) and (esisteFaseA04 or esisteFaseA05)");
      }
    }
    
    result = test1 && test2 && test3 && test4 && test5 && test6;
    
    if (logger.isDebugEnabled()) {
      logger.debug("isGaraLottoSimogCompatibile: fine metodo");
    }
    return result;
  }
  
  private Long getIdSceltaContraente50(String idSceltaContraente) {
	  Long result=null;
	  if (idSceltaContraente != null) {
		  if (idSceltaContraente.equals("1")) {
			  return new Long(1);
		  } else if (idSceltaContraente.equals("2")) {
			  return new Long(12);
		  } else if (idSceltaContraente.equals("4")) {
			  return new Long(6);
		  } else if (idSceltaContraente.equals("6")) {
			  return new Long(17);
		  } else if (idSceltaContraente.equals("7")) {
			  return null;
		  } else if (idSceltaContraente.equals("8")) {
			  return new Long(18);
		  } else if (idSceltaContraente.equals("9")) {
			  return new Long(5);
		  } else if (idSceltaContraente.equals("10")) {
			  return new Long(6);
		  } else if (idSceltaContraente.equals("11")) {
			  return null;
		  } else if (idSceltaContraente.equals("12")) {
			  return new Long(2);
		  } else if (idSceltaContraente.equals("13")) {
			  return new Long(12);
		  } else if (idSceltaContraente.equals("14")) {
			  return null;
		  } else if (idSceltaContraente.equals("15")) {
			  return new Long(2);
		  } else if (idSceltaContraente.equals("16")) {
			  return new Long(2);
		  } else if (idSceltaContraente.equals("17")) {
			  return new Long(9);
		  } else if (idSceltaContraente.equals("18")) {
			  return new Long(10);
		  } else if (idSceltaContraente.equals("19")) {
			  return new Long(11);
		  } else if (idSceltaContraente.equals("20")) {
			  return new Long(13);
		  } else if (idSceltaContraente.equals("25")) {
			  return null;
		  } else if (idSceltaContraente.equals("31")) {
			  return new Long(2);
		  }
	  }
	  return result;
  }
  
  /**
   * Recupero da Simog dei Cig di una gara a partire del numero gara (IdAvGara).
   * 
   * @param idAvGara
   * @param syscon
   * @param codUffint
   * @return
   * @throws SQLException
   * @throws GestoreException
   */
  public HashMap<String, Object> getCigGaraSimog(String idAvGara, final long syscon, final String codUffint) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("getCigGaraSimog: inizio metodo");
    }
    
    // Valore di default per il tipo di accesso ai servizi Simog
    int tipoAccessoWSSimog = 0;
    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
    
    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoWSSimog = Integer.parseInt(a);
    }
    
    HashMap<String, Object> hm = null;
    switch (tipoAccessoWSSimog) {
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS:
      hm = this.consultaGaraAccessoIndirettoPddCigGaraSimog(idAvGara, syscon, codUffint);
      break;

    default:
      String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
          "indicato il tipo di accesso al WS Simog per la funzione 'Consulta gara'. Contattare un amministratore";
      logger.error(msgError);
      hm = new HashMap<String, Object>();
      hm.put("esito", Boolean.FALSE);
      hm.put("msgErr", "errors.migrazioneSA.noConfigurazione");
      break;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getCigGaraSimog: fine metodo");
    }
    
    return hm;
  }

  /**
   * Consulta gara.
   * 
   * @param codiceCIG codice CIG
   * @param syscon SYSCON della USRSYS
   * @param codUffInt codice ufficio intestatario
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException SQLException
   * @throws CriptazioneException CriptazioneException
   * @throws GestoreException GestoreException
   */
  public HashMap<String, Object> getXmlSimog(final String codiceCIG, final long syscon, final String codUffint, 
      final String simogUser, final String simogPassword) {

    if (logger.isDebugEnabled()) {
      logger.debug("getXmlSimog: inizio metodo");
    }
    
    // Valore di default per il tipo di accesso ai servizi Simog
    int tipoAccessoWSSimog = 0;
    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
    
    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoWSSimog = Integer.parseInt(a);
    }
    
    HashMap<String, Object> hm = null;
    switch (tipoAccessoWSSimog) {
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
    case CostantiSimog.ORT_CONSULTA_GARA_VIA_WS:
      hm = this.consultaGaraAccessoDirettoXmlSimog(codiceCIG, syscon, codUffint, simogUser, simogPassword);
      break;
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS:
      hm = this.consultaGaraAccessoIndirettoXmlSimog(codiceCIG, syscon, codUffint);
      break;

    default:
      String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
          "indicato il tipo di accesso al WS Simog per la funzione 'Consulta gara'";
      logger.error(msgError);
      hm = new HashMap<String, Object>();
      hm.put("esito", Boolean.FALSE);
      hm.put("errorMsg", "errors.migrazioneSA.noConfigurazione");
      break;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getXmlSimog: fine metodo");
    }
    
    return hm;
  }

  /**
   * Login RPNT sui servizi SIMOG.
   * 
   * @param codiceCIG codice CIG
   * @param syscon SYSCON della USRSYS
   * @param codUffInt codice ufficio intestatario
   * @return Ritorna una HashMap con oggetti Stringa-valore contentente il risultato delle operazioni
   * @throws SQLException SQLException
   * @throws CriptazioneException CriptazioneException
   * @throws GestoreException GestoreException
   */
  public HashMap<String, Object> getLoginRPNT(final String cfRup, final String simogUser, final String simogPassword) {

    if (logger.isDebugEnabled()) {
      logger.debug("getXmlSimog: inizio metodo");
    }
    
    // Valore di default per il tipo di accesso ai servizi Simog
    int tipoAccessoWSSimog = 0;
    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
    
    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoWSSimog = Integer.parseInt(a);
    }
    
    HashMap<String, Object> hm = null;
    switch (tipoAccessoWSSimog) {
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
    case CostantiSimog.ORT_CONSULTA_GARA_VIA_WS:
      this.accessoSimogManager.getLoginRPNT(cfRup, simogUser, simogPassword);
      break;
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS:
      this.accessoSimogManager.getLoginRPNTIndiretto(cfRup);
      break;

    default:
      String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
          "indicato il tipo di accesso al WS Simog per la funzione 'Consulta gara'";
      logger.error(msgError);
      hm = new HashMap<String, Object>();
      hm.put("esito", Boolean.FALSE);
      hm.put("errorMsg", "errors.migrazioneSA.noConfigurazione");
      break;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getXmlSimog: fine metodo");
    }
    
    return hm;
  }
  
}