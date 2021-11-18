package it.eldasoft.w9.bl.sa;

import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.bl.delegate.AnagraficaLottoGaraHandler;
import it.eldasoft.w9.bl.delegate.ElencoImpreseInvitatePartecipantiHandler;
import it.eldasoft.w9.common.CostantiSimog;
import it.eldasoft.w9.common.SituazioneGaraLotto;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument;
import it.toscana.rete.rfc.sitatort.EsitoType;
import it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType;
import it.toscana.rete.rfc.sitatsa.client.FaseType;
import it.toscana.rete.rfc.sitatsa.client.FeedbackType;
import it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_PortType;
import it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_ServiceLocator;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.springframework.transaction.TransactionStatus;

/**
 * Classe per implementazione della logica per l'accesso ai metodi getElencoSchede, getElencoFeedback
 * e getScheda messi a disposizione dal WsOsservatorio.
 * 
 * @author Luca.Giacomazzo
 */
public class SaManager {

  /**
   * Logger della classe.
   */
  private static Logger logger = Logger.getLogger(SaManager.class);
  
  private SqlManager sqlManager;
  
  private W9Manager w9Manager;
  
  private GenChiaviManager genChiaviManager;
  
  private GeneManager geneManager;
   
  private AnagraficaLottoGaraHandler anagraficaLottoGaraHandler;
  
  private ElencoImpreseInvitatePartecipantiHandler elencoImpreseInvitatePartecipantiHandler;
  
  /**
   * Set SqlManager.
   * @param sqlMan the sqlManager to set
   */
  public void setSqlManager(SqlManager sqlMan) {
    this.sqlManager = sqlMan;
  }
  
  /**
   * Set W9Manager.
   * @param w9Manager the w9Manager to set
   */
  public void setW9Manager(W9Manager w9Manager) {
    this.w9Manager = w9Manager;
  }

  /**
   * Set GenChiaviManager.
   * @param genChiaviManager
   */
  public void setGenChiaviManager(GenChiaviManager genChiaviManager) {
    this.genChiaviManager = genChiaviManager;
  }
  
  /**
   * Set GeneManager.
   * @param geneManager
   */
  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }
  
  /**
   * Set anagraficaLottoGaraHandler.
   * @param anagraficaHandler
   */
  public void setAnagraficaLottoGaraHandler(AnagraficaLottoGaraHandler anagraficaHandler) {
    this.anagraficaLottoGaraHandler = anagraficaHandler;
  }
  
  /**
   * Set ElencoImpreseInvitatePartecipantiHandler.
   * @param elencoImpreseHandler
   */
  public void setElencoImpreseInvitatePartecipantiHandler(ElencoImpreseInvitatePartecipantiHandler elencoImpreseHandler) {
    this.elencoImpreseInvitatePartecipantiHandler = elencoImpreseHandler;
  }
  
  /**
   * SitatSA richiede a SitatORT i feedback della fase (<i>faseEsecuzione</i>) relativi
   * alla gara e al lotto indicati (<i>idgara</i> e <i>cig</i>, da parte del Rup (<i>rup</i>)
   * della stazione appaltante (<i>cfsa</i>).
   *
   * @param cig Codice CIG del lotto 
   * @param idgara Codice IdGara della gara
   * @param cfrup Codice fiscale del rup
   * @param cfsa Codice fiscale della S.A.
   * @param tipoFeedBack Tipo di feedback richiesto (OR o ANAC)
   * @param faseEsecuzione Fase di cui si richiedono i feedback
   */
  public FeedbackType[] getElencoFeedback(String cig, String idgara, String cfrup, String cfsa,
    it.toscana.rete.rfc.sitatsa.client.TipoFeedbackType tipoFeedBack,
    it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType faseEsecuzione) {
  
    StringBuilder strb = null;
    if (logger.isDebugEnabled()) {
      strb = new StringBuilder("Parametri di ingresso: ");
      strb.append(", IdGara=");
      strb.append(idgara);
      strb.append(", CIG=");
      strb.append(cig);
      strb.append(", CF del RUP=");
      strb.append(cfrup);
      strb.append(", CF della SA=");
      strb.append(cfsa);
      strb.append(", fase=");
      strb.append(faseEsecuzione.getCodiceFase().getValue());
      if (faseEsecuzione.getNum() != null) {
        strb.append(", progressivo=" + faseEsecuzione.getNum());
      }

      logger.debug("getElencoFeedback: inizio metodo. ".concat(strb.toString()));
    }
    
    FeedbackType[] feedbackTypeFromORT = null;
    String urlWsORT = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    
    if (StringUtils.isNotEmpty(urlWsORT)) {
      WsOsservatorio_ServiceLocator locatorIdGaraCig = new WsOsservatorio_ServiceLocator();
      locatorIdGaraCig.setWsOsservatorioEndpointAddress(urlWsORT);

      try {
        WsOsservatorio_PortType servizioOsservatorio = locatorIdGaraCig.getWsOsservatorio();
        it.toscana.rete.rfc.sitatsa.client.ResponseElencoFeedback responseElencoFeedback = 
          servizioOsservatorio.getElencoFeedback(cig, idgara, cfrup, cfsa, tipoFeedBack, faseEsecuzione);

        if (responseElencoFeedback.isSuccess() && responseElencoFeedback.getElencoFeedback() != null &&
          responseElencoFeedback.getElencoFeedback().length > 0) {
          feedbackTypeFromORT = responseElencoFeedback.getElencoFeedback();
        } else {
          if (!responseElencoFeedback.isSuccess()) {
            logger.error("La chiamata getElencoSchede al WsOsservatorio e' terminato con il seguente errore: "
                + responseElencoFeedback.getError());
          } else {
            if (responseElencoFeedback.getElencoFeedback() == null) {
              logger.error("La chiamata getElencoFeedback al WsOsservatorio e' terminato successo, ma la risposta "
                  + "non contiene alcun dato (L'elencoFeedbak dentro l'oggetto responseElencoFeedback e' null)"
                  + "Messaggio di errore: " + responseElencoFeedback.getError());
            } else if (responseElencoFeedback.getElencoFeedback().length == 0) {
              logger.error("La chiamata getElencoFeedback al WsOsservatorio e' terminato successo, ma la risposta "
                  + "non contiene alcun dato (L'elencoFeedback dentro l'oggetto responseElencoFeedback ha dimensione 0)"
                  + "Messaggio di errore: " + responseElencoFeedback.getError());
            }
          }
        }
      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con il WS Osservatorio per la chiamata getElencoSchede "
            + "con codiceCIG=" + cig + ", codiceGara=" + idgara + ", da parte del  rup con codice fiscale= "
            + cfrup + " per conto della stazione appaltante con codice fiscale=" + cfsa, re);
        feedbackTypeFromORT = null;
      } catch (ServiceException se) {
        logger.error("Errore nel collegamento al WS Osservatorio per la chiamata getElencoSchede "
            + "con codiceCIG=" + cig + ", codiceGara=" + idgara + ", da parte del  rup con codice fiscale= "
            + cfrup + " per conto della stazione appaltante con codice fiscale=" + cfsa, se);
        feedbackTypeFromORT = null;
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento al WS Osservatorio per la chiamata getElencoSchede "
            + "con codiceCIG=" + cig + ", codiceGara=" + idgara + ", da parte del  rup con codice fiscale= "
            + cfrup + " per conto della stazione appaltante con codice fiscale=" + cfsa, t);
        feedbackTypeFromORT = null;
      }
    } else {
      logger.error("Errore di configurazione: manca l'indirizzo per la connessione al WS Osservatorio per "
          + "la chiamata getElencoFeedback. Contattare un amministratore.");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getElencoFeedback: fine metodo. ".concat(strb.toString()));
    }
    return feedbackTypeFromORT;   
  }
  
  
  /**
   * Metodo per determinare se nella base dati di SitatORT esiste una fase (fase e progressivo)
   * per il lotto indicato (<i>idgara</i> e <i>cig</i>, da parte del Rup (<i>rup</i>)
   * della stazione appaltante (<i>cfsa</i>).
   *
   * @param cig
   * @param idgara
   * @param cfrup
   * @param cfsa
   * @return
   */
  public boolean esisteSchedaInOR(FaseEsecuzioneType fase, String cig, String idgara, String cfrup, String cfsa) {
    FaseEsecuzioneType[] fasiEsistentiInORT = this.getElencoSchede(cig, idgara, cfrup, cfsa);

    boolean esisteFaseInORT = false;
    if (fasiEsistentiInORT != null && fasiEsistentiInORT.length > 0) {
      for (FaseEsecuzioneType faseEsecuzioneType : fasiEsistentiInORT) {
        if (faseEsecuzioneType.equals(fase)) {
          esisteFaseInORT = true;
          break;
        }
      }
    }
    return esisteFaseInORT;
  }
  
  /**
   * SitatSA richiede a SitatORT l'elenco delle fasi esistenti nella base dati di SitatORT
   * per il lotto indicato (<i>idgara</i> e <i>cig</i>, da parte del Rup (<i>rup</i>)
   * della stazione appaltante (<i>cfsa</i>).
   *
   * @param cig Codice CIG del lotto 
   * @param idgara Codice IdGara della gara
   * @param cfrup Codice fiscale del rup
   * @param cfsa Codice fiscale della S.A.
   */
  public FaseEsecuzioneType[] getElencoSchede(String cig, String idgara, String cfrup, String cfsa) {
    
    StringBuilder strb = new StringBuilder("");
    if (logger.isDebugEnabled()) {
      strb.append("Parametri di ingresso: ");
      strb.append(", IdGara=");
      strb.append(idgara);
      strb.append(", CIG=");
      strb.append(cig);
      strb.append(", CF del RUP=");
      strb.append(cfrup);
      strb.append(", CF della SA=");
      strb.append(cfsa);
      logger.debug("getElencoSchede: inizio metodo".concat(strb.toString()));
    }
    
    FaseEsecuzioneType[] fasiEsistentiInORT = null;
    String urlWsORT = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    
    if (StringUtils.isNotEmpty(urlWsORT)) {
      WsOsservatorio_ServiceLocator locatorIdGaraCig = new WsOsservatorio_ServiceLocator();
      locatorIdGaraCig.setWsOsservatorioEndpointAddress(urlWsORT);

      try {
        WsOsservatorio_PortType servizioOsservatorio = locatorIdGaraCig.getWsOsservatorio();
        it.toscana.rete.rfc.sitatsa.client.ResponseElencoSchedeType responseElencoSchede = 
          servizioOsservatorio.getElencoSchede(cig, idgara, cfrup, cfsa);

        if (responseElencoSchede.isSuccess() && responseElencoSchede.getElencoSchede() != null &&
          responseElencoSchede.getElencoSchede().length > 0) {
          fasiEsistentiInORT = responseElencoSchede.getElencoSchede();
        } else {
          if (!responseElencoSchede.isSuccess()) {
            logger.error("La chiamata getElencoSchede al WsOsservatorio e' terminato con il seguente errore: "
                + responseElencoSchede.getError());
          } else {
            if (responseElencoSchede.getElencoSchede() == null) {
              logger.error("La chiamata getElencoSchede al WsOsservatorio e' terminato successo, ma la risposta "
                  + "non contiene alcun dato (L'elencoSchede dentro l'oggetto responseElencoSchede e' null)"
                  + "Messaggio di errore: " + responseElencoSchede.getError());
            } else if (responseElencoSchede.getElencoSchede().length == 0) {
              logger.error("La chiamata getElencoSchede al WsOsservatorio e' terminato successo, ma la risposta "
                  + "non contiene alcun dato (L'elencoSchede dentro l'oggetto responseElencoSchede ha dimensione 0)"
                  + "Messaggio di errore: " + responseElencoSchede.getError());
            }
          }
        }
      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con il WS Osservatorio per la chiamata getElencoSchede "
            + "con codiceCIG=" + cig + ", codiceGara=" + idgara + ", da parte del  rup con codice fiscale= "
            + cfrup + " per conto della stazione appaltante con codice fiscale=" + cfsa, re);
        fasiEsistentiInORT = null;
      } catch (ServiceException se) {
        logger.error("Errore nel collegamento al WS Osservatorio per la chiamata getElencoSchede "
            + "con codiceCIG=" + cig + ", codiceGara=" + idgara + ", da parte del  rup con codice fiscale= "
            + cfrup + " per conto della stazione appaltante con codice fiscale=" + cfsa, se);
        fasiEsistentiInORT = null;
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento al WS Osservatorio per la chiamata getElencoSchede "
            + "con codiceCIG=" + cig + ", codiceGara=" + idgara + ", da parte del  rup con codice fiscale= "
            + cfrup + " per conto della stazione appaltante con codice fiscale=" + cfsa, t);
        fasiEsistentiInORT = null;
      }
    } else {
      logger.error("Errore di configurazione: manca l'indirizzo per la connessione al WS Osservatorio per "
          + "la chiamata getElencoSchede. Contattare un amministratore.");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getElencoSchede: fine metodo");
    }
    
    return fasiEsistentiInORT;
  }

  /**
   * SitatSA richiede a SitatORT la fase (<i>faseEsecuzione</i>) relativa
   * alla gara e al lotto indicati (<i>idgara</i> e <i>cig</i>, da parte del Rup (<i>rup</i>)
   * della stazione appaltante (<i>cfsa</i>).
   *
   * @param cig Codice CIG del lotto 
   * @param idgara Codice IdGara della gara
   * @param cfrup Codice fiscale del rup
   * @param cfsa Codice fiscale della S.A.
   * @param tipoFeedBack Tipo di feedback richiesto (OR o ANAC)
   * @param faseEsecuzione Fase di cui si richiedono i feedback
   */
  public it.toscana.rete.rfc.sitatsa.client.ResponseSchedaType getScheda(String cig, String idgara,
      String cfrup, String cfsa, FaseEsecuzioneType faseEsecuzione) {
    
    StringBuilder strb = new StringBuilder("");
    if (logger.isDebugEnabled()) {
      strb.append("Parametri di ingresso: ");
      strb.append(", IdGara=");
      strb.append(idgara);
      strb.append(", CIG=");
      strb.append(cig);
      strb.append(", CF del RUP=");
      strb.append(cfrup);
      strb.append(", CF della SA=");
      strb.append(cfsa);
      strb.append(", fase=");
      strb.append(faseEsecuzione.getCodiceFase().getValue());
      if (faseEsecuzione.getNum() != null) {
        strb.append(", progressivo=" + faseEsecuzione.getNum());
      }

      logger.debug("getScheda: inizio metodo. ".concat(strb.toString()));
    }
    
    it.toscana.rete.rfc.sitatsa.client.ResponseSchedaType responseScheda = null;
    String msgError = null;
    
    String urlWsORT = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);

    if (StringUtils.isNotEmpty(urlWsORT)) {
      WsOsservatorio_ServiceLocator locatorIdGaraCig = new WsOsservatorio_ServiceLocator();
      locatorIdGaraCig.setWsOsservatorioEndpointAddress(urlWsORT);

      try {
        WsOsservatorio_PortType servizioOsservatorio = locatorIdGaraCig.getWsOsservatorio();
        responseScheda = servizioOsservatorio.getScheda(cig, idgara, cfrup, cfsa, faseEsecuzione);
        
      } catch (RemoteException re) {
        logger.error("Errore nell'interazione con il WS Osservatorio per la chiamata getScheda "
            + "con codiceCIG=" + cig + ", codiceGara=" + idgara + ", da parte del  rup con codice fiscale= "
            + cfrup + " per conto della stazione appaltante con codice fiscale=" + cfsa, re);
        msgError = "Errore nell'interazione con il WS Osservatorio";
        
      } catch (ServiceException se) {
        logger.error("Errore nel collegamento al WS Osservatorio per la chiamata getScheda "
            + "con codiceCIG=" + cig + ", codiceGara=" + idgara + ", da parte del  rup con codice fiscale= "
            + cfrup + " per conto della stazione appaltante con codice fiscale=" + cfsa, se);
        msgError = "Errore nel collegamento al WS Osservatorio";
        
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel collegamento al WS Osservatorio per la chiamata getScheda "
            + "con codiceCIG=" + cig + ", codiceGara=" + idgara + ", da parte del  rup con codice fiscale= "
            + cfrup + " per conto della stazione appaltante con codice fiscale=" + cfsa, t);
        msgError = "Errore inaspettato nel collegamento al WS Osservatorio";
      } finally {
        if (msgError != null) {
            responseScheda = new it.toscana.rete.rfc.sitatsa.client.ResponseSchedaType();
            responseScheda.setSuccess(false);
            responseScheda.setError(msgError);
        }
      }
    } else {
      logger.error("Errore di configurazione: manca l'indirizzo per la connessione al WS Osservatorio per "
          + "la chiamata getScheda. Contattare un amministratore.");
      
      responseScheda = new it.toscana.rete.rfc.sitatsa.client.ResponseSchedaType();
      responseScheda.setSuccess(false);
      responseScheda.setError("Errore di configurazione: manca l'indirizzo per la connessione al WS Osservatorio. " +
      		"Contattare un amministratore");
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getScheda: fine metodo".concat(strb.toString()));
    }
    
    return responseScheda;
  }
  
  /**
   * 
   * 
   * @param idavgara
   * @param codiceCIG
   * @param operazioneAnagraficaGara
   * @param operazioneAnagraficaLotto
   * @param operazioneElencoImprese
   * @return
   */
  public boolean importaDatiDaOR(String cig, String idgara, String cfrup, String cfsa,
      String operazioneAnagraficaGara, String operazioneAnagraficaLotto, String operazioneElencoImprese) {
    
    StringBuilder strb = new StringBuilder("");
    
    if (logger.isDebugEnabled()) {
      strb.append("Parametri di ingresso: ");
      strb.append(", IdGara=");
      strb.append(idgara);
      strb.append(", CIG=");
      strb.append(cig);
      strb.append(", CF del RUP=");
      strb.append(cfrup);
      strb.append(", CF della SA=");
      strb.append(cfsa);
      strb.append(", operazioneAnagraficaGara=");
      strb.append(operazioneAnagraficaGara);
      strb.append(", operazioneAnagraficaLotto=");
      strb.append(operazioneAnagraficaLotto);
      strb.append(", operazioneElencoImprese=");
      strb.append(operazioneElencoImprese);
     
      logger.debug("importaDatiDaOR: inizio metodo. ".concat(strb.toString()));
    }
    
    String resultAnagraficaGara = null;
    String resultElencoImprese  = null;
    boolean continua = true;
    
    FaseEsecuzioneType faseEsec = new FaseEsecuzioneType();
    
    if (StringUtils.isNotEmpty(operazioneAnagraficaGara) || StringUtils.isNotEmpty(operazioneAnagraficaLotto)) {
      faseEsec.setCodiceFase(FaseType.FASE_988);
      
      it.toscana.rete.rfc.sitatsa.client.ResponseSchedaType responseScheda = this.getScheda(
          cig, idgara, cfrup, cfsa, faseEsec);
      if (responseScheda.isSuccess() && responseScheda.getSchedaXML() != null) {
        try {
          RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument anagraficaGaraLotto =
              RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument.Factory.parse(
                  responseScheda.getSchedaXML());
          
          int numeroLotti = anagraficaGaraLotto.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().sizeOfTab5TypeArray();
          
          if (numeroLotti == 1) {
            // Importazione dell'anagrafica della gara e del suo unico lotto
            this.anagraficaLottoGaraHandler.gestioneAnagraficaGaraLottoDaOR(operazioneAnagraficaGara,
                operazioneAnagraficaLotto, anagraficaGaraLotto, cfrup, cfsa);
            resultAnagraficaGara = "anagraficaGaraLottoImportata";
          } else if (numeroLotti > 1) {
            resultAnagraficaGara = "garaAPiuLotti:" + numeroLotti;
            continua = false;
          } else {
            // Cosa anomalo: l'anagrafica gara lotto ricevuta non presenta i dati del lotto relativi al CIG richiesto.
            logger.error("Errore inaspettato nell'importazione dell'anagrafica gara lotto per caso anomalo nei dati ricevuti da XML. ");
            continua = false;
          }
        } catch (XmlException e) {
          logger.error("Errore nella deserializzazione dell'oggetto RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument "
              + "ricevuto da OR attraverso il WsOsservatorio. " + strb.toString());
          resultAnagraficaGara = "errore:Errore nella lettura della risposta ricevuta da OR";
          continua = false;
        } catch (SQLException e) {
          logger.error("Errore SQL inaspettato nell'importazione dell'anagrafica gara lotto. " + strb.toString());
          resultAnagraficaGara = "errore:Errore inatteso nell'importazione dell'anagrafica gara lotto";
          continua = false;
        } catch (GestoreException e) {
          logger.error("Errore inaspettato nell'importazione dell'anagrafica gara lotto. " + strb.toString());
          resultAnagraficaGara = "errore:Errore inaspettato nell'importazione dell'anagrafica gara lotto.";
          continua = false;
        }
      } else {
        if (!responseScheda.isSuccess()) {
          logger.error("La chiamata getScheda al WsOsservatorio e' terminato con il seguente errore: "
              + responseScheda.getError());
          resultAnagraficaGara= "errore:Importazione dati da OR interrotta per errore lato OR. Dettaglio errore: ";
        } else if (responseScheda.getSchedaXML() == null) {
          logger.error("La chiamata getScheda al WsOsservatorio e' terminato successo, ma la risposta "
              + "non contiene alcun dato (La schedaXML dentro l'oggetto responseScheda e' null)"
              + "Messaggio di errore: " + responseScheda.getError());
          resultAnagraficaGara= "errore:Importazione dati da OR interrotta per errore nella risposta ricevuta da OR";
        }
        continua = false; // TODO e' giusto questo settaggio???
      }
    }
    
    if (continua && StringUtils.isNotEmpty(operazioneElencoImprese)) {
      faseEsec.setCodiceFase(FaseType.FASE_101);
      
      it.toscana.rete.rfc.sitatsa.client.ResponseSchedaType responseScheda = this.getScheda(cig, idgara,
          cfrup, cfsa, faseEsec);
      if (responseScheda.isSuccess() && responseScheda.getSchedaXML() != null) {
        
        try {
          RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument elencoImpreseInvitatePartecipantiDoc =
              RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument.Factory.parse(
                  responseScheda.getSchedaXML());
         
          this.elencoImpreseInvitatePartecipantiHandler.gestioneElencoImpreseInvitateDaOR(
              operazioneElencoImprese, elencoImpreseInvitatePartecipantiDoc, cfrup, cfsa);

        } catch (XmlException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (GestoreException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
      } else {
        if (!responseScheda.isSuccess()) {
          logger.error("La chiamata getScheda al WsOsservatorio e' terminato con il seguente errore: "
              + responseScheda.getError());
        } else {
          if (responseScheda.getSchedaXML() == null) {
            logger.error("La chiamata getScheda al WsOsservatorio e' terminato successo, ma la risposta "
                + "non contiene alcun dato (La schedaXML dentro l'oggetto responseScheda e' null)"
                + "Messaggio di errore: " + responseScheda.getError());
          }
        }
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("importaDatiDaOR: fine metodo".concat(strb.toString()));
    }
    
    return true;
  }
  
  public void insertAvviso(final Long codgara, final Long codlott, final String titoloNota, final String testonota) throws GestoreException, SQLException {
	  Long notecod = (Long) this.sqlManager.getObject("select max(notecod) from g_noteavvisi", new Object[] {});
      if (notecod == null) {
        notecod = new Long(0);
      }
      notecod = new Long(notecod.longValue() + 1);
	  DataColumnContainer dccG_NOTEAVVISI = new DataColumnContainer(new DataColumn[] { new DataColumn("G_NOTEAVVISI.NOTECOD",
	            new JdbcParametro(JdbcParametro.TIPO_NUMERICO, notecod)) });
	  dccG_NOTEAVVISI.addColumn("G_NOTEAVVISI.NOTEPRG", JdbcParametro.TIPO_TESTO, "W9");
	  dccG_NOTEAVVISI.addColumn("G_NOTEAVVISI.NOTEENT", JdbcParametro.TIPO_TESTO, "W9LOTT");
	  dccG_NOTEAVVISI.addColumn("G_NOTEAVVISI.NOTEKEY1", JdbcParametro.TIPO_TESTO, codgara.toString());
	  dccG_NOTEAVVISI.addColumn("G_NOTEAVVISI.NOTEKEY2", JdbcParametro.TIPO_TESTO, codlott.toString());
	  dccG_NOTEAVVISI.addColumn("G_NOTEAVVISI.STATONOTA", JdbcParametro.TIPO_NUMERICO, new Long(1));
	  dccG_NOTEAVVISI.addColumn("G_NOTEAVVISI.TIPONOTA", JdbcParametro.TIPO_NUMERICO, new Long(3));
	  dccG_NOTEAVVISI.addColumn("G_NOTEAVVISI.DATANOTA", JdbcParametro.TIPO_DATA, new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()));
	  dccG_NOTEAVVISI.addColumn("G_NOTEAVVISI.TITOLONOTA", JdbcParametro.TIPO_TESTO, titoloNota);
	  dccG_NOTEAVVISI.addColumn("G_NOTEAVVISI.TESTONOTA", JdbcParametro.TIPO_TESTO, testonota);
      dccG_NOTEAVVISI.insert("G_NOTEAVVISI", this.sqlManager);
  }
  /**
	 * Processo schedulato in SA che per ogni richiesta di eliminazione fatta 
	 * da SA richiede a ORT il risultato della stessa
	 * 
	 * @throws GestoreException
	 *             GestoreException
	 */
	public synchronized void allineamentoRichiesteEliminazione() throws GestoreException, SQLException {
	  
	  logger.info("allineamentoRichiesteEliminazione: inizio metodo");
	  
		boolean commitTransaction = false;
		TransactionStatus status = null;
		String sql = "select f.KEY01, f.KEY02, f.KEY03, f.KEY04, f.CFSA, f.IDFLUSSO, f.DATINV " +
				" from W9FLUSSI f, W9LOTT l where f.KEY01=l.CODGARA and f.KEY02=l.CODLOTT and l.SITUAZIONE=? and f.TINVIO2=-1";
		List< ? > listaFlussiRichiesteEliminazione = this.sqlManager.getListVector(sql, 
		    new Object[] { SituazioneGaraLotto.SITUAZIONE_RICHIESTA_CANCELLAZIONE.getSituazione() } );
		
		Long codgara, codlott, fase, num, idflusso;
		String cfsa, cig, cfrup, idGara;
		Calendar dataInvio;
		if (listaFlussiRichiesteEliminazione != null && !listaFlussiRichiesteEliminazione.isEmpty()) {
			 for (int i = 0; i < listaFlussiRichiesteEliminazione.size(); i++) {
				 Vector< ? > flussoEliminazione = (Vector< ? >) listaFlussiRichiesteEliminazione.get(i);
				 
				 codgara = null;
				 if (SqlManager.getValueFromVectorParam(flussoEliminazione, 0).getValue() != null) {
					 codgara = SqlManager.getValueFromVectorParam(flussoEliminazione, 0).longValue();
				 }
				 codlott = null;
				 if (SqlManager.getValueFromVectorParam(flussoEliminazione, 1).getValue() != null) {
					 codlott = SqlManager.getValueFromVectorParam(flussoEliminazione, 1).longValue();
				 }

				 fase = null;
				 if (SqlManager.getValueFromVectorParam(flussoEliminazione, 2).getValue() != null) {
					 fase = SqlManager.getValueFromVectorParam(flussoEliminazione, 2).longValue();
				 }

				 num = null;
				 if (SqlManager.getValueFromVectorParam(flussoEliminazione, 3).getValue() != null) {
					 num = SqlManager.getValueFromVectorParam(flussoEliminazione, 3).longValue();
				 }

				 cfsa = null;
				 if (SqlManager.getValueFromVectorParam(flussoEliminazione, 4).getValue() != null) {
					 cfsa = SqlManager.getValueFromVectorParam(flussoEliminazione, 4).stringValue();
				 }
				 
				 idflusso = null;
				 if (SqlManager.getValueFromVectorParam(flussoEliminazione, 5).getValue() != null) {
					 idflusso = SqlManager.getValueFromVectorParam(flussoEliminazione, 5).longValue();
				 }
				 
				 dataInvio = null;
				 if (SqlManager.getValueFromVectorParam(flussoEliminazione, 6).getValue() != null) {
					 dataInvio = GregorianCalendar.getInstance();
					 dataInvio.setTimeInMillis(SqlManager.getValueFromVectorParam(flussoEliminazione, 6).dataValue().getTime());
				 }
				 idGara = null;
				 cig = null;
				 cfrup = null;
				 if (codgara != null && codlott != null && fase != null && num != null && cfsa != null && dataInvio != null) {
					 idGara = (String) this.sqlManager.getObject("select IDAVGARA from W9GARA where CODGARA=? ", new Object[] { codgara });
					 cig = (String) this.sqlManager.getObject("select CIG from W9LOTT where CODGARA=? and CODLOTT=?", new Object[] { codgara, codlott });
					 cfrup = (String) this.sqlManager.getObject("select TECNI.CFTEC from W9LOTT left join TECNI ON W9LOTT.RUP=TECNI.CODTEC where CODGARA=? and CODLOTT=?", new Object[] {codgara, codlott});
					 
					 if (idGara != null && cig != null && cfrup != null) {
						 it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType faseEsecuzione = new it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType();
						 faseEsecuzione.setCodiceFase(FaseType.fromString(fase.toString()));
						 faseEsecuzione.setNum(num.intValue());
						 FeedbackType[] elenco = this.getElencoFeedback(cig, idGara, cfrup, cfsa, it.toscana.rete.rfc.sitatsa.client.TipoFeedbackType.fromString(
						     it.toscana.rete.rfc.sitatsa.client.TipoFeedbackType.CANCELLAZIONE_SCHEDE_SIMOG), faseEsecuzione);
						 if (elenco != null && elenco.length > 0) {
							 for (int j = 0; j < elenco.length; j++) {
								 FeedbackType risultato = elenco[j];
								 it.toscana.rete.rfc.sitatsa.client.EsitoType esito = risultato.getEsito();
								 if (risultato.getData().compareTo(dataInvio) >= 0 ) {
									 String motivoCancellazione = null;
									 if (risultato.getMessaggio() != null) {
										 motivoCancellazione = risultato.getMessaggio(0);
									 }
									 if (esito != null) {
										 if (esito.equals(it.toscana.rete.rfc.sitatsa.client.EsitoType.value2)) {
											 //Andata a buon fine
											 try {
												commitTransaction = false;
												status = this.sqlManager.startTransaction();
												// Copia del flusso da cancellare da W9FLUSSI a W9FLUSSI_ELIMINATI
												this.sqlManager.update("insert into W9FLUSSI_ELIMINATI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP) " +
											   		" select IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP FROM W9FLUSSI where KEY01=? and KEY02=? and KEY03=? and KEY04=? and TINVIO2>0",
											   		new Object[] { codgara, codlott, fase, num });
												// Aggiornamento di data e motivo cancellazione del flusso eliminato in W9FLUSSI_ELIMINATI
												this.sqlManager.update("update W9FLUSSI_ELIMINATI set DATCANC=?, MOTIVOCANC=? where KEY01=? and KEY02=? and KEY03=? and KEY04=? and DATCANC is null", 
												    new Object[] { new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()), motivoCancellazione, codgara, codlott, fase, num });
											  this.sqlManager.update("delete from W9FLUSSI where KEY01=? and KEY02=? and KEY03=? and KEY04=? and TINVIO2>0",
											      new Object[] { codgara, codlott, fase, num });
											  // Aggiornamento del campo TINVIO2 da 'Richiesta cancellazione' (-1) a 'Cancellazione evasa' (-2)
											  // del flusso che rappresenta la richiesta di cancellazione
											  this.sqlManager.update("update W9FLUSSI set TINVIO2=-2 where IDFLUSSO=?", new Object[] { idflusso });
											  // Aggiornamento del campo DAEXPORT alla fase appena cancellata
											  this.sqlManager.update("update W9FASI set DAEXPORT='1' where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?", 
											  		new Object[] { codgara, codlott, fase, num });
											  // Aggiornamento della situazione della gara, solo se non ci sono altre richieste di cancellazione sullo stesso lotto
											  Long numeroRichiesteCancellazioni = (Long) this.sqlManager.getObject("select count(*) from W9FLUSSI where KEY01=? and KEY02=? and TINVIO2=-1",
											      new Object[] { codgara, codlott });
												if (numeroRichiesteCancellazioni.longValue() == 0) {
													this.w9Manager.updateSituazioneGaraLotto(codgara, codlott);
												}

											   //inserimento nota in G_NOTEAVVISI
											   this.insertAvviso( codgara, codlott, "Richiesta cancellazione", (risultato.getMessaggio() != null) ? risultato.getMessaggio(0) : "");
											   commitTransaction = true;

											 } catch(SQLException e) {
												 logger.error("Errore su esito POSITIVO", e);
											 } finally {
												 if (status != null) {
													 try {
														 if (commitTransaction) {
														   this.sqlManager.commitTransaction(status);
														 } else {
														   this.sqlManager.rollbackTransaction(status);
														 }
													 } catch (SQLException e) {
													   logger.error("Errore durante la chiusura della transazione", e);
													 }
												 }
											 }
										 } else if (esito.equals(it.toscana.rete.rfc.sitatsa.client.EsitoType.value3)) {
											 //errore
											 try {
												 commitTransaction = false;
												 status = this.sqlManager.startTransaction();
												 Long idflussoRichiestaCancellazione = (Long) this.sqlManager.getObject(
													"select max(IDFLUSSO) from W9FLUSSI where KEY01=? and KEY02=? and KEY03=? and KEY04=? and TINVIO2=-1",
														new Object[] { codgara, codlott, fase, num });
                         						 // Copia della richiesta di cancellazione da W9FLUSSI a W9FLUSSI_ELIMINATI
												 this.sqlManager.update("insert into W9FLUSSI_ELIMINATI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP) " +
													"select IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP FROM W9FLUSSI WHERE IDFLUSSO=?",
												 		new Object[] { idflussoRichiestaCancellazione });
												 // Aggiornamento di data e motivo cancellazione del flusso copiato in W9FLUSSI_ELIMINATI
												 this.sqlManager.update("update W9FLUSSI_ELIMINATI set DATCANC=?, MOTIVOCANC=? where IDFLUSSO=? ", 
												     new Object[] { new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()), motivoCancellazione, idflussoRichiestaCancellazione });
												 // Cancellazione della richiesta di cancellazione dalla W9FLUSSI
						             this.sqlManager.update("delete from W9FLUSSI where IDFLUSSO=?", new Object[] { idflussoRichiestaCancellazione });
						             // Aggiornamento della situazione della gara, solo se non ci sono altre richieste di cancellazione sullo stesso lotto
						             Long numeroRichiesteCancellazioni = (Long) this.sqlManager.getObject("select count(*) from W9FLUSSI where KEY01=? and KEY02=? and TINVIO2 = -1",
						            		 new Object[] { codgara, codlott });
                         if (numeroRichiesteCancellazioni.longValue() == 0) {
                        	 this.w9Manager.updateSituazioneGaraLotto(codgara, codlott);
                         }
												 //inserimento nota in G_NOTEAVVISI
											   this.insertAvviso( codgara, codlott, "Richiesta cancellazione", (risultato.getMessaggio() != null)?risultato.getMessaggio(0):"" );
												 commitTransaction = true;
											 } catch(SQLException e) {
												 logger.error("Errore su esito ERRORE", e);
											 } finally {
												 if (status != null) {
													 try {
														 if (commitTransaction) {
															 this.sqlManager.commitTransaction(status);
											             } else {
											              	  this.sqlManager.rollbackTransaction(status);
											             }
											         } catch (SQLException e) {
											             logger.error("Errore durante la chiusura della transazione", e);
											         }
											     }
											 }
										 } else if (esito.equals(EsitoType.WARNING)) {
											 //Chiamata ancora pendente non faccio nulla
											 ;
										 }
									 }
								 }
							 }
						 }
					 }
				 }
			 }
		}
		logger.info("allineamentoRichiesteEliminazione: fine metodo");
	}
	
}
