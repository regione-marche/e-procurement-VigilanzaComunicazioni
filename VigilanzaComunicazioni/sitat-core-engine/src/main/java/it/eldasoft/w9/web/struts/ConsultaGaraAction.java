package it.eldasoft.w9.web.struts;

import it.avlp.simog.massload.xmlbeans.Collaborazione;
import it.avlp.simog.massload.xmlbeans.Collaborazioni;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.GaraType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.ConsultaGaraLottiBean;
import it.eldasoft.w9.bl.ConsultaLottoBean;
import it.eldasoft.w9.bl.EsportazioneXMLSIMOGManager;
import it.eldasoft.w9.bl.RichiestaIdGaraCigManager;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.bl.sa.MigrazioneSaManager;
import it.eldasoft.w9.bl.simog.AccessoSimogManager;
import it.eldasoft.w9.bl.simog.TicketSimogBean;
import it.eldasoft.w9.common.CostantiSimog;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.bean.GaraSABean;
import it.eldasoft.w9.common.bean.MigrazioneBean;
import it.eldasoft.w9.common.bean.PresaInCaricoGaraDelegataBean;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitatsa.client.ResponseLoginRPNT;
import it.toscana.rete.rfc.sitatsa.client.ResponsePresaInCaricoGaraDelegata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xmlbeans.XmlException;

/**
 * Action per la consultazione del metodo getGaraXML dal WS di ORT.
 * 
 * @author Luca.Giacomazzo
 */
public class ConsultaGaraAction extends DispatchActionBaseNoOpzioni {

  /** Logger di classe. */
  private static Logger     logger = Logger.getLogger(ConsultaGaraAction.class);

  /**
   * Manager per gestione della logica di business e delle transazioni sul db.
   */
  private RichiestaIdGaraCigManager richiestaIdGaraCigManager;

  /**
   * Manager per la gestione della logica di esportazione XML verso SIMOG.
   */
  private EsportazioneXMLSIMOGManager exportXmlSimogManager;

  /**
   * Manager per la gestione della logica di aaggiornamento dei dati della scheda 
   * Adesione accordo quadro.
   */
  private W9Manager w9Manager;
  
  /**
   * Manager per la gestione della migrazione di una gara, che in questo contesto
   * prende nome di 'Presa in carico'
   */
  private MigrazioneSaManager migrazioneSaManager;

  private AccessoSimogManager accessoSimogManager;

  private final String OPERAZIONE_IMPORTA_ANAGRAFICA_GARA_LOTTI = "1";
  
  private final String OPERAZIONE_PRESA_IN_CARICO_GARA_DELEGATA = "2";
  
  //private final String OPERAZIONE_PRESA_IN_CARICO = "3";
  
  /**
   * Manager per estrarre dati da DB.
   */
  private SqlManager sqlManager;
  
  /**
   * @param richiestaIdGaraCigManager
   *        richiestaIdGaraCigManager da settare internamente alla classe.
   */
  public void setRichiestaIdGaraCigManager(RichiestaIdGaraCigManager richiestaIdGaraCigManager) {
    this.richiestaIdGaraCigManager = richiestaIdGaraCigManager;
  }
  
  /**
   * @param exportXmlSimogManager the esportXmlSimogManager to set.
   */
  public void setEsportazioneXMLSIMOGManager(
      EsportazioneXMLSIMOGManager exportXmlSimogManager) {
    this.exportXmlSimogManager = exportXmlSimogManager;
  }
  
  /**
   * @param w9Manager the w9Manager to set.
   */
  public void setW9Manager(W9Manager w9Manager) {
    this.w9Manager = w9Manager;
  }
  
  /**
   * @param migrazioneSaManager the migrazioneSaManager to set. 
   */
  public void setMigrazioneSaManager(MigrazioneSaManager migrazioneSaManager) {
    this.migrazioneSaManager = migrazioneSaManager;
  }
  /**
   * @param migrazioneSaManager the migrazioneSaManager to set. 
   */
  public void setAccessoSimogManager(AccessoSimogManager accessoSimogManager) {
    this.accessoSimogManager = accessoSimogManager;
  }
  
  /**
   * @param sqlManager the sqlManager to set.
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
  
  public ActionForward inizializza(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("inizializza: inizio metodo");
    }
   
    String target = CostantiGeneraliStruts.FORWARD_OK + "Inizializza";
    
    boolean isUserTecnicoDellaSA = ((Boolean) request.getSession().getAttribute(
        CostantiW9.IS_USER_TECNICO_SA)).booleanValue();
    boolean isUserConCfNoTecnico = ((Boolean) request.getSession().getAttribute(
        CostantiW9.IS_USER_CONCF_NO_TECNICO_SA)).booleanValue();
    
    ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    
    if ("U".equals(profiloUtente.getAbilitazioneStd()) && !isUserTecnicoDellaSA && !isUserConCfNoTecnico) {
      // L'utente loggato non puo' eseguire l'operazione, perche' non e' ne' RUP della SA 
      // attualmente in uso, ne' e' un utente con il codice fiscale valorizzato
      this.aggiungiMessaggio(request, "errors.consultaGara.userNoTecnicoSANoCf");
      request.setAttribute("bloccaOperazione", "true");
    }
    request.getSession().setAttribute("smartcig", request.getParameter("smartcig"));
    // Qualora esistessero in sessione gli oggetti per la gestione dell'importazione di
    // gare con piu' lotti, della presa in carico di una gara o della presa in carico con
    // delega, si rimuovono tutti gli oggetti
    request.getSession().removeAttribute("sessionConsultaGaraLottiBean");
    request.getSession().removeAttribute("sessionPresaInCaricoBean");
    request.getSession().removeAttribute("presaInCaricoGaraDelegataBean");
    
    if (logger.isDebugEnabled()) {
      logger.debug("inizializza: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
  
  /**
   * In caso di gara inesistente in base dati, si scaricano i dati di gara SIMOG e in base a questi
   * si decide l'operazione da eseguire oppure bloccare nel caso che l'operazione sia di presa in carico. 
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return Rtiorna un array di Stringhe
   */
  private String contolloDatiGara(HttpServletRequest request, final String idGara_Cig, final String cfStazAppAttiva, 
      String simoguser, String simogpass, HashMap<String, Object> hmObj) {

    logger.debug("contolloDatiGara: inizio metodo");
    
    ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    String codeinStazAppAttiva = (String) request.getSession().getAttribute(
        CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    String nomeStazAppAttiva = (String) request.getSession().getAttribute(
        CostantiGenerali.NOME_UFFICIO_INTESTATARIO_ATTIVO);
    int syscon = profiloUtente.getId();
    HashMap<String, Object> hmResult = null;
    String target = null;
    String operazioneDaEseguire = null;
    String existCredenziali = null;
    try {
      String codiceCIG = null;
      if (UtilitySITAT.isCigValido(idGara_Cig)) {
        codiceCIG = new String(idGara_Cig);

      } else if (UtilitySITAT.isIdGaraValido(idGara_Cig)) {
        // Scarico i CIG della gara
        ConsultaGaraLottiBean consultaGaraLottiBean = new ConsultaGaraLottiBean(null, idGara_Cig, simoguser, simogpass);
        hmResult = this.richiestaIdGaraCigManager.consultaGara(idGara_Cig, codeinStazAppAttiva, profiloUtente.getId(),
            consultaGaraLottiBean, simoguser, simogpass);
        if (hmResult != null) {
          Boolean esito = (Boolean) hmResult.get("esito");
          if (esito.booleanValue()) {
            LinkedList<String> listaCig = consultaGaraLottiBean.getListaCig();
            codiceCIG = listaCig.get(0);
          } else {
            String strMsg = "Errore nel recupero dei CIG della gara con IdGara=" + idGara_Cig;
            if (hmResult.containsKey("errorMsg")) {
              strMsg = strMsg.concat(". " + hmResult.get("errorMsg"));
              logger.error(strMsg);
              this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS.recuperoDatiGara", ": " + (String) hmResult.get("errorMsg"));
            } else {
              logger.error(strMsg);
              this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS.recuperoDatiGara", "");
            }
            target = "erroreNoCIG";
          }
        } else {
          String strMsg = "Errore nel recupero dei CIG della gara con IdGara=" + idGara_Cig;
          logger.error(strMsg);
          this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS.recuperoDatiGara");
          target = "erroreNoCIG";
        }
      } else {
        // l'utente ha digitato qualcosa che non rappresenta ne' un CIG ne' un Id Gara
        logger.error("E' stato digitato un valore che non rappresenta ne' un CIG ne' un Id Gara");
        this.aggiungiMessaggio(request, "errors.consultaGara.codiceCigIdGaraNonValido");
        target = "erroreNoCIG";
      }

      if (request.getSession().getAttribute("sessionConsultaGaraLottiBean") == null) {
    	  if (request.getParameter("existCredenziali") != null) {
    		  existCredenziali = request.getParameter("existCredenziali");
          }
            
    	  if (existCredenziali != null && existCredenziali.equals("no")) {
              String recuperaUser = request.getParameter("recuperauser");
              String recuperaPassword = request.getParameter("recuperapassword");
              String memorizza = request.getParameter("memorizza");
      
              // Leggo le eventuali credenziali memorizzate
      
              HashMap<String, String> hMapSIMOGLAUserPass = new HashMap<String, String>();
              hMapSIMOGLAUserPass = this.exportXmlSimogManager.recuperaSIMOGLAUserPass(new Long(syscon));
      
              // Gestione USER
              if ("1".equals(recuperaUser)) {
                simoguser = ((String) hMapSIMOGLAUserPass.get("simoguser"));
              } else {
                simoguser = request.getParameter("simoguser");
              }
      
              // Gestione PASSWORD
              if ("1".equals(recuperaPassword)) {
                simogpass = ((String) hMapSIMOGLAUserPass.get("simogpass"));
              } else {
                simogpass = request.getParameter("simogpass");
              }
              // Gestione memorizzazione delle credenziali o eventuale cancellazione (se richiesta)
              if (simoguser != null && simogpass != null) {
            	  this.exportXmlSimogManager.gestioneWsSimogLoaderAppaltoUserPass(new Long(syscon),
                          simoguser, simogpass, memorizza != null);
              }
            }
      }
      
      if (StringUtils.isNotEmpty(codiceCIG)) {

        HashMap<String, Object> hm2 = this.richiestaIdGaraCigManager.getXmlSimog(
            codiceCIG, profiloUtente.getId(), codeinStazAppAttiva, simoguser, simogpass);
      
        if (((Boolean) hm2.get("esito")).booleanValue() ) {
        	String garaXml = (String) hm2.get("garaXml");
            
            ConsultaGaraResponseDocument consultaGaraResponseDocument = 
                ConsultaGaraResponseDocument.Factory.parse(garaXml);
            GaraType garaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().getDatiGara().getGara();
            SchedaType schedaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML();
            
            hmObj.put("schedaType", schedaType);
            
            if (garaType.getCFAMMINISTRAZIONE().equalsIgnoreCase(cfStazAppAttiva)) {
              operazioneDaEseguire = OPERAZIONE_IMPORTA_ANAGRAFICA_GARA_LOTTI;
            } else if (garaType.isSetCFAMMAGENTEGARA() && garaType.getCFAMMAGENTEGARA().equalsIgnoreCase(cfStazAppAttiva)) {
              // Potrebbe attivarsi la presa in carico gara delegata, cioe' se si superano i controlli preliminari
              operazioneDaEseguire = OPERAZIONE_PRESA_IN_CARICO_GARA_DELEGATA;
            } else {
              // Errore la stazione appaltante attiva da cui e' stata attivata l'operazione e' sbagliata
              if (garaType.isSetCFAMMAGENTEGARA()) {
                logger.error("Il CIG " + idGara_Cig + " non puo' essere preso in carico con delega "
                      + "perche' la stazione appaltante attiva (" + nomeStazAppAttiva + " - CF " + cfStazAppAttiva
                      + ") e' diversa dalla stazione appaltante delegante (" + garaType.getDENAMMAGENTEGARA()
                      + " - CF " + garaType.getCFAMMAGENTEGARA() +")");
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.cig.diversaSAdelegante", 
                    nomeStazAppAttiva, garaType.getDENAMMAGENTEGARA());
              } else {
                logger.error("Il CIG " + idGara_Cig + " non puo' essere preso in carico "
                    + "perche' la stazione appaltante attiva (" + nomeStazAppAttiva + " - CF " + cfStazAppAttiva
                    + ") e' diversa dalla stazione appaltante che ha in gestione la gara (" + garaType.getDENOMAMMINISTRAZIONE()
                    + " - CF " + garaType.getCFAMMINISTRAZIONE() +")");
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.cig.garaSimog.diversaSA", 
                    garaType.getDENOMAMMINISTRAZIONE());
              }
              operazioneDaEseguire = null;
              target = "erroreNoCIG";
            }
        } else {
          String tmp = "";
          if (StringUtils.isNotEmpty((String) hm2.get("msgErr"))) {
            tmp = (String) hm2.get("msgErr");
          }
          logger.error("Errore nel recupero dei dati della gara su SIMOG. Descrizione: " + tmp);
          this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.erroreServiziSimog", tmp);
          operazioneDaEseguire = null;
          target = "erroreNoCIG";
        }
      }
      
    } catch (CriptazioneException e) {
      logger.error("Errore nella decriptazione della password di accesso a SIMOG", e);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS.recuperoDatiGara", "");
      operazioneDaEseguire = null;
    } catch (SQLException e) {
      logger.error("Errore nella preparazione dei dati prima di accedere ai servizi SIMOG (1)", e);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS.recuperoDatiGara", "");
      operazioneDaEseguire = null;
    } catch (GestoreException e) {
      logger.error("Errore nella decriptazione della password di accesso a SIMOG", e);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      this.aggiungiMessaggio(request, "Errore nella preparazione dei dati prima di accedere ai servizi SIMOG (2)");
      operazioneDaEseguire = null;
    } catch (XmlException e) {
      logger.error("Errore nel parsing del XML ricevuto da SIMOG", e);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS.recuperoDatiGara", "");
      operazioneDaEseguire = null;
    } catch (Throwable t) {
      logger.error("Errore inaspettatto", t);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS.recuperoDatiGara", "");
      operazioneDaEseguire = null;
    }

    logger.debug("contolloDatiGara: fine metodo");

    if (StringUtils.isEmpty(target) && StringUtils.isNotEmpty(operazioneDaEseguire)) {
      return operazioneDaEseguire;
    } else if (StringUtils.isNotEmpty(target) && StringUtils.isEmpty(operazioneDaEseguire)) { 
      return target;
    } else {
      return "errore";
    }
  }
  
  
  public ActionForward verificaDati(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    if (logger.isDebugEnabled()) {
      logger.debug("verificaDati: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK + "VerificaDati";
    String idGara_Cig = null;
    String existCredenziali = null;
    String memorizza = null;
    String simoguser = null;
    String simogpass = null;
    String recuperauser = null;
    String recuperapassword = null;
    
    if (request.getParameter("codiceGara") != null) {
    	idGara_Cig = request.getParameter("codiceGara");
    }
    
    if (request.getParameter("existCredenziali") != null) {
      existCredenziali = request.getParameter("existCredenziali");
      if ("no".equals(existCredenziali)) {
        memorizza = request.getParameter("memorizza");
        simoguser = request.getParameter("simoguser");
        simogpass = request.getParameter("simogpass");
        recuperauser = request.getParameter("recuperauser");
        recuperapassword = request.getParameter("recuperapassword");
      }
    }
    
    ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    String codeinStazAppAttiva = (String) request.getSession().getAttribute(
        CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    String nomeStazAppAttiva = (String) request.getSession().getAttribute(
        CostantiGenerali.NOME_UFFICIO_INTESTATARIO_ATTIVO);
    String cfStazAppAttiva = null;
    
    try {
      cfStazAppAttiva = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", 
        new Object[] { codeinStazAppAttiva} );
    } catch (SQLException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("Errore nei controlli preliminari nell'operazione di caricamento 'Importa da SIMOG/Prendi in carico'", e);
      
      // In caso di SqlException si esce dal metodo
      return mapping.findForward(target);
    }
    
    boolean isUserTecnicoDellaSA = ((Boolean) request.getSession().getAttribute(
        CostantiW9.IS_USER_TECNICO_SA)).booleanValue();
    boolean isUserConCfNoTecnico = ((Boolean) request.getSession().getAttribute(
        CostantiW9.IS_USER_CONCF_NO_TECNICO_SA)).booleanValue();
    
    if (isUserTecnicoDellaSA || isUserConCfNoTecnico || (! "U".equals(profiloUtente.getAbilitazioneStd()))) {
      try {
        if (UtilitySITAT.isCigValido(idGara_Cig)) {
          if (UtilitySITAT.existsLotto(idGara_Cig, this.sqlManager)) {
            // Controllo preliminare n. 1:
            // la gara da prendere in carico deve essere una s.a. diversa da quella attiva.
            // Estrazione del CODEIN della gara associata al CIG
            String codeinGara = (String) this.sqlManager.getObject(
                "select CODEIN from W9GARA g, W9LOTT l where g.CODGARA=l.CODGARA and l.CIG=?", 
                new Object[] { idGara_Cig } );
            if (StringUtils.equals(codeinGara, codeinStazAppAttiva)) {
              // Situazione di errore, perche' si tratta dell'operazione di presa in carico di una
              // gara associata alla stazione appaltante attiva, quindi la gara non e' da migrare
              this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.cig.garaSorgente.stessaSA", 
                  nomeStazAppAttiva);
              target = "erroreNoCIG";
            } else {
              // Controllo preliminare n. 2:
              // Operazione di presa in carico o presa in carico della gara delegata avviabile se
              // su SIMOG la gara e' associata alla stazione appaltante attiva. Questo controllo e' 
              // possibile farlo dopo aver scaricato i dati della gara da SIMOG.
              
              Long codiceGara = (Long) this.sqlManager.getObject("select CODGARA from W9LOTT where CIG=?",
                  new Object[] { idGara_Cig } );
              String nomeUffintGara = (String) this.sqlManager.getObject(
                  "select NOMEIN from UFFINT where CODEIN=?", new Object[] { codeinGara });
              String cfUffintGara = (String) this.sqlManager.getObject(
                  "select CFEIN from UFFINT where CODEIN=?", new Object[] { codeinGara });
              
              // Scarico dei dati della gara da SIMOG
              HashMap<String, Object> hm2 = this.richiestaIdGaraCigManager.getXmlSimog(
                  idGara_Cig, profiloUtente.getId(), codeinStazAppAttiva, simoguser, simogpass);

              if (((Boolean) hm2.get("esito")).booleanValue() ) {
                String garaXml = (String) hm2.get("garaXml");
                
                ConsultaGaraResponseDocument consultaGaraResponseDocument = 
                    ConsultaGaraResponseDocument.Factory.parse(garaXml);
                GaraType garaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().getDatiGara().getGara();
                SchedaType schedaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML();
                
                if (garaType.getCFAMMINISTRAZIONE().equalsIgnoreCase(cfStazAppAttiva)) {
                  // Gestione della presa in carico gara
                  MigrazioneBean mb = new MigrazioneBean(1);
                  mb.setArrayCodiciGareDaMigrare(new long[] { codiceGara });
                  mb.setCodiceFiscaleStazAppOrigine(cfUffintGara);
                  mb.setCodiceStazAppOrigine(codeinGara);
                  mb.setNomeStazAppOrigine(nomeUffintGara);
                  
                  mb.passaAllaGaraSuccessiva();
                  
                  target = this.presaInCaricoGara(request, target, profiloUtente, codeinStazAppAttiva, mb, garaXml);
                  
                } else if (garaType.isSetCFAMMAGENTEGARA() && garaType.getCFAMMAGENTEGARA().equalsIgnoreCase(cfStazAppAttiva)) {
                  // Potrebbe attivarsi la presa in carico gara delegata, cioe' se si superano i controlli preliminari
                  target = this.initPresaInCaricoGaraDelegata(request, target, profiloUtente,
                          codeinStazAppAttiva, cfStazAppAttiva, codiceGara, schedaType, null);
                } else {
                  // Errore la stazione appaltante attiva da cui e' stata attivata l'operazione e' sbagliata
                  if (garaType.isSetCFAMMAGENTEGARA()) {
                    logger.error("Il CIG " + idGara_Cig + " non puo' essere preso in carico con delega "
                          + "perche' la stazione appaltante attiva (" + nomeStazAppAttiva + " - CF " + cfStazAppAttiva
                          + ") e' diversa dalla stazione appaltante delegante (" + garaType.getDENAMMAGENTEGARA()
                          + " - CF " + garaType.getCFAMMAGENTEGARA() +")");
                    this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.cig.diversaSAdelegante", 
                        nomeStazAppAttiva, garaType.getDENAMMAGENTEGARA());
                  } else {
                    logger.error("Il CIG " + idGara_Cig + " non puo' essere preso in carico "
                        + "perche' la stazione appaltante attiva (" + nomeStazAppAttiva + " - CF " + cfStazAppAttiva
                        + ") e' diversa dalla stazione appaltante che ha in gestione la gara (" + garaType.getDENOMAMMINISTRAZIONE()
                        + " - CF " + garaType.getCFAMMINISTRAZIONE() +")");
                    this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.cig.garaSimog.diversaSA", 
                        garaType.getDENOMAMMINISTRAZIONE());
                  }
                  target = "erroreNoCIG";
                }
              } else {
                String tmp = "";
                if (StringUtils.isNotEmpty((String) hm2.get("msgErr"))) {
                  tmp = (String) hm2.get("msgErr");
                }
                logger.error("Errore nel recupero dei dati della gara su SIMOG. Descrizione: " + tmp);
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.erroreServiziSimog", tmp);
                target = "erroreNoCIG";
              }
            }
          } else {
            HashMap<String, Object> hmObj = new HashMap<String, Object>();
            String esitoControlli = this.contolloDatiGara(request, idGara_Cig, cfStazAppAttiva, simoguser, simogpass, hmObj);
            
            if (OPERAZIONE_IMPORTA_ANAGRAFICA_GARA_LOTTI.equals(esitoControlli )) {
              // eseguire importazione dell'anagrafica e lotti

              // Importazione del CIG da SIMOG.
              // Si stabilira' in fase di importazione dei dati se si tratta dell'operazione di 
              // presa in carico di una gara delegata. In caso affermativo si importeranno i dati
              // comuni e l'aggiudicazione
              request.setAttribute("richiestaConfermaCreaNuovaGara", "2");
              request.setAttribute("newMetodo", "aggiornaSchedaGara");
              
              request.setAttribute("valoreCodiceCIG", idGara_Cig);
              request.setAttribute("valoreCodiceGara", idGara_Cig);
              request.setAttribute("valoreMemorizza", memorizza);
              request.setAttribute("valoreSimogUser", simoguser);
              request.setAttribute("valoreSimogPass", simogpass);
              request.setAttribute("valoreRecuperaUser", recuperauser);
              request.setAttribute("valoreRecuperaPassword", recuperapassword);
                 
            } else if (OPERAZIONE_PRESA_IN_CARICO_GARA_DELEGATA.equals(esitoControlli )) {
              // Potrebbe attivarsi la presa in carico gara delegata
              
              // In questo caso salvo i parametri del request necessari
              // all'operazione di importazione CIG da SIMOG
              HashMap<String, String> hMap = new HashMap<String, String>();
              hMap.put("richiestaConfermaCreaNuovaGara", "2");
              hMap.put("newMetodo", "aggiornaSchedaGara");
              hMap.put("valoreCodiceCIG", idGara_Cig);
              hMap.put("valoreCodiceGara", idGara_Cig);
              hMap.put("valoreMemorizza", memorizza);
              hMap.put("valoreSimogUser", simoguser);
              hMap.put("valoreSimogPass", simogpass);
              hMap.put("valoreRecuperaUser", recuperauser);
              hMap.put("valoreRecuperaPassword", recuperapassword);
              
              // Potrebbe attivarsi la presa in carico gara delegata
              SchedaType schedaType = (SchedaType) hmObj.get("schedaType");
              target = this.initPresaInCaricoGaraDelegata(request, target, profiloUtente,
                      codeinStazAppAttiva, cfStazAppAttiva, null, schedaType, hMap);
            } else if("errore".equals(esitoControlli )) {
              this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS.recuperoDatiGara");
              target = "erroreNoCIG";
            }
          }
        } else if (UtilitySITAT.isIdGaraValido(idGara_Cig)) {
          if (UtilitySITAT.existsGara(idGara_Cig, this.sqlManager)) {
            // Se la gara esiste, allora si avvia la presa in carico della gara
            
            // Estrazione del CODEIN della gara 
            String codeinGara = (String) this.sqlManager.getObject(
                "select CODEIN from W9GARA where IDAVGARA=?", new Object[]{idGara_Cig});
            if (StringUtils.equals(codeinGara, codeinStazAppAttiva)) {
              // Situazione di errore, perche' si tratta dell'operazione di presa in carico di una
              // gara associata alla stazione appaltante attiva, quindi la gara non e' da migrare
              this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.idAvGara.garaSorgente.stessaSA", 
                  nomeStazAppAttiva);
              target = "erroreNoCIG";

            } else {
              // Operazione di presa in carico avviabile se su SIMOG la gara e'
              // associata alla stazione appaltante attiva. Questo controllo e' possibile 
              // farlo dopo aver scaricato i dati della gara da SIMOG.
              Long codiceGara = UtilitySITAT.getCodGaraByIdAvGara(idGara_Cig, this.sqlManager);
              
              /*String nomeUffintGara = (String) this.sqlManager.getObject(
                  "select NOMEIN from UFFINT where CODEIN=?", new Object[] { codeinGara });
              String cfUffintGara = (String) this.sqlManager.getObject(
                  "select CFEIN from UFFINT where CODEIN=?", new Object[] { codeinGara });*/
              String codiceCigGara = (String) this.sqlManager.getObject(
                  "select CIG from W9LOTT where CODGARA=?", new Object[] { codiceGara });
              
              // Scarico dei dati della gara da SIMOG
              HashMap<String, Object> hm2 = this.richiestaIdGaraCigManager.getXmlSimog(
                  codiceCigGara, profiloUtente.getId(), codeinStazAppAttiva, simoguser, simogpass);

              if (((Boolean) hm2.get("esito")).booleanValue() ) {
                String garaXml = (String) hm2.get("garaXml");
                
                ConsultaGaraResponseDocument consultaGaraResponseDocument = 
                    ConsultaGaraResponseDocument.Factory.parse(garaXml);
                GaraType garaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().getDatiGara().getGara();
                SchedaType schedaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML();
                
                if (garaType.getCFAMMINISTRAZIONE().equalsIgnoreCase(cfStazAppAttiva)) {
                  // Gestione della presa in carico gara
                  MigrazioneBean mb = new MigrazioneBean(1);
                  mb.setArrayCodiciGareDaMigrare(new long[] { codiceGara });
                  mb.setCodiceFiscaleStazAppOrigine(cfStazAppAttiva);
                  mb.setCodiceStazAppOrigine(codeinStazAppAttiva);
                  
                  mb.passaAllaGaraSuccessiva();
                  target = this.presaInCaricoGara(request, target, profiloUtente, codeinStazAppAttiva, mb, garaXml);
                  
                } else if (garaType.isSetCFAMMAGENTEGARA() && garaType.getCFAMMAGENTEGARA().equalsIgnoreCase(cfStazAppAttiva)) {
                  // Potrebbe attivarsi la presa in carico gara delegata
                  target = this.initPresaInCaricoGaraDelegata(request, target, profiloUtente,
                          codeinStazAppAttiva, cfStazAppAttiva, codiceGara, schedaType, null);
                } else {
                  // Errore la stazione appaltante attiva da cui e' stata attivata l'operazione e' sbagliata
                  if (garaType.isSetCFAMMAGENTEGARA()) {
                    logger.error("La gara " + idGara_Cig + " non puo' essere preso in carico con delega "
                          + "perche' la stazione appaltante attiva (" + nomeStazAppAttiva + " - CF " + cfStazAppAttiva
                          + ") e' diversa dalla stazione appaltante delegante (" + garaType.getDENAMMAGENTEGARA()
                          + " - CF " + garaType.getCFAMMAGENTEGARA() +")");
                    this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.idAvGara.diversaSAdelegante", 
                        nomeStazAppAttiva, garaType.getDENAMMAGENTEGARA());
                  } else {
                    logger.error("La gara " + idGara_Cig + " non puo' essere presa in carico "
                        + "perche' la stazione appaltante attiva (" + nomeStazAppAttiva + " - CF " + cfStazAppAttiva
                        + ") e' diversa dalla stazione appaltante che ha in gestione la gara (" + garaType.getDENOMSTAZIONEAPPALTANTE()
                        + " - CF " + garaType.getCFAMMINISTRAZIONE() +")");
                    this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.idAvGara.garaSimog.diversaSA", 
                        garaType.getDENAMMAGENTEGARA());
                  }
                  target = "erroreNoCIG";
                }
              } else {
                String tmp = "";
                if (StringUtils.isNotEmpty((String) hm2.get("msgErr"))) {
                  tmp = (String) hm2.get("msgErr");
                }
                logger.error("Errore nel recupero dei dati della gara su SIMOG. Descrizione: " + tmp);
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.erroreServiziSimog", tmp);
                target = "erroreNoCIG";
              }
            }
          } else {
            HashMap<String, Object> hmObj = new HashMap<String, Object>();
            String esitoControlli = this.contolloDatiGara(request, idGara_Cig, cfStazAppAttiva, simoguser, simogpass, hmObj);
            if (OPERAZIONE_IMPORTA_ANAGRAFICA_GARA_LOTTI.equals(esitoControlli )) {
              // eseguire importazione dell'anagrafica e lotti

              // Importazione del CIG da SIMOG.
              request.setAttribute("richiestaConfermaCreaNuovaGara", "2");
              request.setAttribute("newMetodo", "aggiornaSchedaGara");

              request.setAttribute("valoreCodiceCIG", idGara_Cig);
              request.setAttribute("valoreCodiceGara", idGara_Cig);
              request.setAttribute("valoreMemorizza", memorizza);
              request.setAttribute("valoreSimogUser", simoguser);
              request.setAttribute("valoreSimogPass", simogpass);
              request.setAttribute("valoreRecuperaUser", recuperauser);
              request.setAttribute("valoreRecuperaPassword", recuperapassword);
              
            } else if (OPERAZIONE_PRESA_IN_CARICO_GARA_DELEGATA.equals(esitoControlli )) {
              // Potrebbe attivarsi la presa in carico gara delegata
              
              // In questo caso salvo i parametri del request necessari
              // all'operazione di importazione CIG da SIMOG
              HashMap<String, String> hMap = new HashMap<String, String>();
              hMap.put("richiestaConfermaCreaNuovaGara", "2");
              hMap.put("newMetodo", "aggiornaSchedaGara");
              hMap.put("valoreCodiceCIG", idGara_Cig);
              hMap.put("valoreCodiceGara", idGara_Cig);
              hMap.put("valoreMemorizza", memorizza);
              hMap.put("valoreSimogUser", simoguser);
              hMap.put("valoreSimogPass", simogpass);
              hMap.put("valoreRecuperaUser", recuperauser);
              hMap.put("valoreRecuperaPassword", recuperapassword);
              
              SchedaType schedaType = (SchedaType) hmObj.get("schedaType");
              target = this.initPresaInCaricoGaraDelegata(request, target, profiloUtente,
                      codeinStazAppAttiva, cfStazAppAttiva, null, schedaType, hMap);
            } else if("errore".equals(esitoControlli )) {
              this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS.recuperoDatiGara");
              target = "erroreNoCIG";
            }
          } 
        } else if (UtilitySITAT.isSmartCigValido(idGara_Cig)) {
        	if (UtilitySITAT.existsSmartCigSA(idGara_Cig, codeinStazAppAttiva, this.sqlManager)) {
        		this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.cig.garaSorgente.stessaSA", 
                        nomeStazAppAttiva);
                target = "erroreNoCIG";
        	} else {
        		
        		request.setAttribute("richiestaConfermaCreaNuovaGara", "2");
                request.setAttribute("newMetodo", "aggiornaSchedaGara");
                
                request.setAttribute("valoreCodiceCIG", idGara_Cig);
                request.setAttribute("valoreCodiceGara", idGara_Cig);
                request.setAttribute("valoreMemorizza", memorizza);
                request.setAttribute("valoreSimogUser", simoguser);
                request.setAttribute("valoreSimogPass", simogpass);
                request.setAttribute("valoreRecuperaUser", recuperauser);
                request.setAttribute("valoreRecuperaPassword", recuperapassword);
              }
        } else {
          this.aggiungiMessaggio(request, "errors.consultaGara.codiceCigIdGaraNonValido");
          target = "erroreNoCIG";
        }  
      } catch (SQLException e) {
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        logger.error("Errore nei controlli preliminari nell'operazione di caricamento 'Importa da SIMOG/Prendi in carico'", e);
      } catch (Throwable t) {
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        logger.error("Errore nei controlli preliminari nell'operazione di caricamento 'Importa da SIMOG/Prendi in carico'", t);
      }
    } else {
      this.aggiungiMessaggio(request, "errors.consultaGara.userNoTecnicoSANoCf");
      target = "erroreRup";
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("verificaDati: fine metodo");
    }
    return mapping.findForward(target);
  }

  /**
   * Logica per avviare la presa in carico gara per delega: controlli preliminari,set in sessione dell'oggetto 
   * PresaInCaricoGaraDelegataBean ed eventualmente la lista dei tecnici della stazione appaltante per la scelta del rup
   * 
   * @param request
   * @param target
   * @param profiloUtente
   * @param codeinStazAppAttiva
   * @param cfStazAppAttiva
   * @param codiceGara
   * @param garaType
   * @param schedaType
   * @return ritorna il target della action
   * @throws SQLException
   */
  private String initPresaInCaricoGaraDelegata(HttpServletRequest request, String target, ProfiloUtente profiloUtente,
      String codeinStazAppAttiva, String cfStazAppAttiva, Long codiceGara, SchedaType schedaType,
      HashMap<String, String> hmParametriConsultaGara) throws SQLException {
    
    // Fra i parametri il codiceGara e' fondamentale per determinare se la gara esiste nella base dati o meno.
    // Se valorizzato, allora gara esiste in DB e la presa in carico per delega consiste nella sola
    // migrazione dei dati. Se non valorizzato allora la gara non esiste in DB e la presa in carico per delega
    // consiste nell'importazione della gara e dei lotti.
    // In entrambi i casi seguira' l'importazione dell'aggiudicazione se ID_F_DELEGATE = 1 o 2 o nella creazione dell'esito
    // se ID_F_DELEGATE = 4.
    
    GaraType garaType = schedaType.getDatiGara().getGara();
    target = "sceltaTecnicoGaraDelegata";
    
    boolean isGaraDelegataConAggiudicazione = (garaType.isSetIDFDELEGATE() 
        && ("2".equals(garaType.getIDFDELEGATE()) || "1".equals(garaType.getIDFDELEGATE()))
        && schedaType.isSetDatiScheda() && schedaType.getDatiScheda().getSchedaCompletaArray() != null 
        && schedaType.getDatiScheda().getSchedaCompletaArray().length > 0
        && schedaType.getDatiScheda().getSchedaCompletaArray()[schedaType.getDatiScheda().getSchedaCompletaArray().length-1].isSetAggiudicazione()); 
    
    // In fase di sviluppo si sono riscontrati problemi nella lettura del campo datiComuni.ESITO_PROCEDURA, con l'eccezione
    // org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException nella lettura del nuovo valore 5 - Proposta di aggiudicazione
    // Sembra che xmlbeans 
    
    String esitoProcedura = null;
    if (schedaType.isSetDatiScheda()) {
      try {
        esitoProcedura = schedaType.getDatiScheda().getDatiComuni().getESITOPROCEDURA();
      } catch (org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException xvore) {
        logger.error("Errore nella lettura del ESITO_PROCEDURA nei DatiComuni ricevuti da SIMOG e questo e' l'XML ricevuto ("
            + schedaType.getDatiScheda().getDatiComuni().toString() + " )", xvore);
        esitoProcedura = null;
      }
      
      if (esitoProcedura == null) {
        String xmlDatiComuni = schedaType.getDatiScheda().getDatiComuni().toString();
        if (xmlDatiComuni.indexOf("ESITO_PROCEDURA") >= 0) {
          int inizio = xmlDatiComuni.indexOf("ESITO_PROCEDURA");
          int fine = 17;
          
          String s1 = xmlDatiComuni.substring(inizio + fine);
          int primoApiceSpazio = s1.indexOf("\" ");
          esitoProcedura = s1.substring(0, primoApiceSpazio);
        }
      }
    }
    
    boolean isGaraDelegataConPropostaDiAggiudicazione = false;
    if (garaType.isSetIDFDELEGATE() && "4".equals(garaType.getIDFDELEGATE()) &&
        schedaType.isSetDatiScheda() && schedaType.getDatiScheda().getDatiComuni() != null && "5".equals(esitoProcedura)) {
      isGaraDelegataConPropostaDiAggiudicazione = true;
    }
    
    if ( isGaraDelegataConAggiudicazione || isGaraDelegataConPropostaDiAggiudicazione) {
      
      // Presa in carico gara delegata per funzioni delegate = 1 o 2 o 4
      // Salvo in sessione un oggetto contenente garaType, schedaType, codeinStazAppAttiva, cfStazAppAttiva, idAvGara, funzioni delegate
      PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean = new PresaInCaricoGaraDelegataBean();
      presaInCaricoGaraDelegataBean.setCodiceGara(codiceGara);
      presaInCaricoGaraDelegataBean.setGaraType(garaType);
      presaInCaricoGaraDelegataBean.setSchedaType(schedaType);
      presaInCaricoGaraDelegataBean.setCodeinStazAppAttiva(codeinStazAppAttiva);
      presaInCaricoGaraDelegataBean.setCfStazAppAttiva(cfStazAppAttiva);
      presaInCaricoGaraDelegataBean.setFunzioneDelegata(garaType.getIDFDELEGATE());
      presaInCaricoGaraDelegataBean.setIdAvGara("" + garaType.getIDGARA());
      if (hmParametriConsultaGara != null) {
        presaInCaricoGaraDelegataBean.setHmParametriPerConsultaGara(hmParametriConsultaGara);
      }
      
      request.getSession().setAttribute("presaInCaricoGaraDelegataBean", presaInCaricoGaraDelegataBean);

      if ("A".equals(profiloUtente.getAbilitazioneStd()) || "C".equals(profiloUtente.getAbilitazioneStd())) {
        // Se utente amministratore o compilatore si deve chiedere chi sara'
        // il rup fra i tecnici della stazione appaltante attiva
        List<HashMap<?,?>> listaTecnici = this.sqlManager.getListHashMap(
            "select CODTEC, CFTEC, NOMTEC from TECNI where CGENTEI=? and CFTEC is not null order by NOMTEC asc", 
                new Object[]{codeinStazAppAttiva});
        if (listaTecnici != null && listaTecnici.size() > 0) {

          List<String[]> listaRup = new ArrayList<String[]>();
          
          for (int i=0; i < listaTecnici.size(); i++) {
            HashMap<?,?> mappaRiga = listaTecnici.get(i);
            String[] tecnico = new String[3];
            JdbcParametro jdbcPar = (JdbcParametro) mappaRiga.get("TECNI.CODTEC");
            if (jdbcPar != null && jdbcPar.getValue() != null) { 
              tecnico[0] = jdbcPar.getStringValue();
            }
            jdbcPar = (JdbcParametro) mappaRiga.get("TECNI.CFTEC");
            if (jdbcPar != null && jdbcPar.getValue() != null) {
              tecnico[1] = jdbcPar.getStringValue();
            }
            jdbcPar = (JdbcParametro) mappaRiga.get("TECNI.NOMTEC");
            if (jdbcPar != null && jdbcPar.getValue() != null) { 
              tecnico[2] = jdbcPar.getStringValue();
            }
            listaRup.add(tecnico);
          }
          // metto nel request la lista dei possibili RUP della gara in 
          request.setAttribute("listaTecnici", listaRup);
        } else {
          target = "erroreNoCIG";
          request.setAttribute("bloccoOperazione", "true");
          this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.notecnici");
        }
      } else {
        // il RUP della gara sara' l'utente attivo
        String codiceTecnico = (String) this.sqlManager.getObject("select CODTEC from TECNI where CGENTEI=? and SYSCON=?",
            new Object[] { codeinStazAppAttiva, profiloUtente.getId() });
        if (StringUtils.isEmpty(codiceTecnico)) {
          // Non esiste il record in TECNI. Si cerca di crea il record se l'utente ha il CF valorizzato 
          if (StringUtils.isNotEmpty(profiloUtente.getCodiceFiscale())) {
            try {
              codiceTecnico = this.w9Manager.insertTecnico(codeinStazAppAttiva, profiloUtente.getCodiceFiscale().toUpperCase());
            } catch (GestoreException ge) {
              codiceTecnico = null;
              logger.error("Errore nella creazione del tecnico da associare all'utente SYSCON=" + profiloUtente.getId(), ge);
            }
          }
        }
        
        if (StringUtils.isNotEmpty(codiceTecnico)) {
          presaInCaricoGaraDelegataBean.setCodTec(codiceTecnico);
          String cfTecnico = (String) this.sqlManager.getObject("select CFTEC from TECNI where CODTEC=?",
              new Object[] { codiceTecnico });
          presaInCaricoGaraDelegataBean.setCfRup(cfTecnico);
          String nomeTecnico = (String) this.sqlManager.getObject("select NOMTEC from TECNI where CODTEC=?",
              new Object[] { codiceTecnico });
          presaInCaricoGaraDelegataBean.setDenominazioneRup(nomeTecnico);

          request.setAttribute("passaAlCentroDiCosto", "true");
        } else {
          target = "erroreNoCIG";
          logger.error("Impossibile individuare e creare il tecnico per l'utente con SYSCON=" + profiloUtente.getId());
          request.setAttribute("bloccoOperazione", "true");
          this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.noTecnici");
        }
      }
    } else {
      target = "erroreNoCIG";
      request.setAttribute("bloccoOperazione", "true");
      logger.error("I controlli preliminari dell'operazione di sui dati presenti in SIMOG non sono stati superati. " +
      		"La S.A. delegata potrebbe non avere ancora trasmesso l'aggiudicazione o la proposta di aggiudicazione");
      this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.controlliPreliminari");
    }
    return target;
  }

  /**
   * Presa in carico di una gara, riusando il codice per la migrazione
   * 
   * @param request
   * @param target
   * @param profiloUtente
   * @param codUffint
   * @param mb
   * @param garaXml
   * @return
   */
  private String presaInCaricoGara(HttpServletRequest request, String target,
      ProfiloUtente profiloUtente, String codUffint, MigrazioneBean mb, String garaXml) {
    
    logger.debug("presaInCaricoGara: inizio metodo");
    
    boolean continua = true;
    try {
      
      GaraSABean garaSaBean = this.migrazioneSaManager.getDatiGara(mb.getCodiceGaraDaMigrare(0));
      garaSaBean.setXmlSimog(garaXml);
      ConsultaGaraResponseDocument consultaGaraResponseDocument = ConsultaGaraResponseDocument.Factory.parse(garaXml);
      SchedaType schedaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML();
      if (schedaType.isSetDatiScheda()) {
        garaSaBean.setDenominazioneCentroDiCosto(schedaType.getDatiScheda().getDatiComuni().getDENOMCC());
        garaSaBean.setCodiceCentroDiCosto(schedaType.getDatiScheda().getDatiComuni().getCODICECC());
      } else {
        continua = false;
        target = "erroreNoCIG";
        logger.error("Nei dati della gara forniti da SIMOG non e' possibile risalire ai dati del centro di costo per mancanza dei Dati Comuni");
        this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.noDatiComuni");
      }
      
      mb.setGaraBeanInElaborazione(garaSaBean);
    } catch (XmlException xe) {
      logger.error("Errore inaspettato nel parsing dei dati della gara ricevuti da SIMOG", xe);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      continua = false;
    } catch (SQLException se) {
      logger.error("Errore inaspettato nel recupero dei dati della gara da DB", se);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      continua = false;
    }

    if (continua) {
      // Controlli preliminari
      try {
        boolean result = this.migrazioneSaManager.controlloDatiGaraSABean(mb, profiloUtente.getId());
        if (result) {
          // Per la presa in carico bisogna controllare che la stazione appaltante di destinazione
          // (cioe' quella recuperata da SIMOG) sia uguale alla stazione appaltante attiva
          if (! StringUtils.equals(mb.getGaraSABean()[0].getCodeinDestinazione(), codUffint)) {
            this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.idAvGara.garaSimog.diversaSA",
                mb.getGaraSABean()[0].getDenomStazAppaltanteSimog());
            target = "erroreNoCIG";
            request.setAttribute("presaInCaricoNoControlli", "1");
          } else {
            
            // bisogna andare a video per chiedere la conferma dell'operazione all'utente
            // Prima metto in sessione l'oggetto con tutti i per la presa in carico
            request.getSession().setAttribute("sessionPresaInCaricoBean", mb);
            request.setAttribute("presaInCaricoSaSorgente", mb.getNomeStazAppOrigine());
          }
          
          mb.resetGaraInElaborazione();
        } else {
          this.aggiungiMessaggio(request, mb.getGaraSABeanInElaborazione().getDescrErrore(),
              mb.getGaraSABeanInElaborazione().getDenomStazAppaltanteSimog());
          mb.setErrore("controlli");
          target = "erroreNoCIG";
          continua = false;
        }
      } catch (GestoreException ge) {
        logger.error("Errore inaspettato nel controllo dei dati delle gare", ge);
        
        this.aggiungiMessaggio(request, "errors.migrazioneSA.erroreInaspettato");
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        continua = false;
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel controllo dei dati delle gare da migrare", t);
        this.aggiungiMessaggio(request, "errors.migrazioneSA.erroreInaspettato");
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        continua = false;
      }
    }

    logger.debug("presaInCaricoGara: fine metodo");
    
    return target;
  }
  
  
  /**
   * Presa in carico di una gara senza o con delega, riusando il codice per la migrazione
   * 
   * @param request
   * @param target
   * @param profiloUtente
   * @param codeinStazAppAttiva
   * @param mb
   * @return
   */
  public ActionForward setTecnicoGaraDelegata(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException, GestoreException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("setTecnicoGaraDelegata: inizio metodo");
    }

    String target = "sceltaCentroCostoGaraDelegata";

    ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    
    PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean = (PresaInCaricoGaraDelegataBean)
        request.getSession().getAttribute("presaInCaricoGaraDelegataBean");
    
    boolean continua = true;
    
    String codiceTecnico = null;
    if ("A".equals(profiloUtente.getAbilitazioneStd()) || "C".equals(profiloUtente.getAbilitazioneStd())) {
      if (StringUtils.isNotEmpty(request.getParameter("codiceTecnico"))) 
        codiceTecnico = request.getParameter("codiceTecnico");
      else if (StringUtils.isNotEmpty((String) request.getAttribute("codiceTecnico"))) 
        codiceTecnico = (String) request.getAttribute("codiceTecnico");

      if (StringUtils.isNotEmpty(codiceTecnico)) {
        String cfRup = null;
        String denominazioneRup = null;
        try {
          cfRup = (String) this.sqlManager.getObject("select CFTEC from TECNI where CODTEC=?", 
              new Object[] { codiceTecnico });
          denominazioneRup = (String) this.sqlManager.getObject("select NOMTEC from TECNI where CODTEC=?", 
              new Object[] { codiceTecnico });
          presaInCaricoGaraDelegataBean.setCodTec(codiceTecnico);
          presaInCaricoGaraDelegataBean.setCfRup(cfRup);
          presaInCaricoGaraDelegataBean.setDenominazioneRup(denominazioneRup);
          
        } catch (SQLException e) {
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
          logger.error("Presa in carico gara delegata. Errore nell'estrazione del CF del tecnico che sara' il RUP della gara", e);
          continua = false;
        }

      } else {
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        logger.error("Presa in carico gara delegata senza indicazione del tecnico che sara' il RUP della gara.");
        continua = false;
      }
    }
    
    if (continua && presaInCaricoGaraDelegataBean != null && StringUtils.isNotEmpty(presaInCaricoGaraDelegataBean.getCodTec())) {
      if (continua) {

        // Valore di default per il tipo di accesso ai servizi Simog
        int tipoAccessoWSSimog = 0;
        String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
        if (StringUtils.isNotEmpty(a)) {
          tipoAccessoWSSimog = Integer.parseInt(a);
        }

        Collaborazioni collaborazioni = null; 
          
        switch (tipoAccessoWSSimog) {
          case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
            String simogUser = presaInCaricoGaraDelegataBean.getHmParametriPerConsultaGara().get("valoreSimogUser");
            String simogPassword = presaInCaricoGaraDelegataBean.getHmParametriPerConsultaGara().get("valoreSimogPass");
            
            TicketSimogBean tickeSimogBean = this.accessoSimogManager.simogLogin(simogUser, simogPassword);
            if (tickeSimogBean.isSuccess()) {
              collaborazioni = tickeSimogBean.getCollaborazioni();
              
              this.accessoSimogManager.simogLogout(tickeSimogBean.getTicketSimog());
            } else {
              // Errore alla login su SIMOG
              tickeSimogBean.getMsgError();
              logger.error("Login su SIMOG terminato con errore." + tickeSimogBean.getMsgError() );
              if (StringUtils.isNotEmpty(tickeSimogBean.getMsgError())) {
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato",
                    tickeSimogBean.getMsgError());
              } else { 
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato", "");
              }
              target = "erroreNoCIG";
            }
            
        break;
          case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS:
          // contattare SIMOG per effettuare loginPRNT ed estrarre l'indice di collaborazione corretto
          // in funzione del centro di costo di destinazione
          ResponseLoginRPNT responseLoginRPNT = this.accessoSimogManager.getLoginRPNTIndiretto(
              presaInCaricoGaraDelegataBean.getCfRup());
          if (responseLoginRPNT != null) {
            if (responseLoginRPNT.isSuccess()) {
              try {
                it.avlp.simog.massload.xmlbeans.LoginRPNTResponse loginResponse =
                  it.avlp.simog.massload.xmlbeans.LoginRPNTResponse.Factory.parse(responseLoginRPNT.getLoginXML());
              
                if (loginResponse.isSetReturn() && loginResponse.getReturn().getSuccess()) {
                  collaborazioni = loginResponse.getReturn().getColl();
                }
              } catch (XmlException xe) {
                logger.error("errore nel parsing xml della risposta fornita da Simog sulle collaborazioni RPNT", xe);
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato");
                target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
              } catch (Throwable t) {
                logger.error("Errore inaspettato nella presa in carico gara delegata", t);
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato");
                target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
              }
            } else {
              logger.error("LoginRPNT su SIMOG terminato con errore." + responseLoginRPNT.getError() );
              if (StringUtils.isNotEmpty(responseLoginRPNT.getError())) {
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato",
                    responseLoginRPNT.getError());
              } else { 
                this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato", "");
              }
              target = "erroreNoCIG";
            }
          } else {
            logger.error("Risposta inconsistente all'operazione loginRPNT su SIMOG");
            this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato");
            target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
          }
          
        break;
        
        default:
          String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
              "indicato il tipo di accesso ai servizi SIMOG";
          logger.error(msgError);

          break;
        }

        if (collaborazioni != null && collaborazioni.getCollaborazioniArray() != null && collaborazioni.getCollaborazioniArray().length > 0) {
          // Se la risposta alla chiamata loginRPNT ritorna piu' collaborazioni, allora ritorna al client
          // per chiedere di selezionare il centro di costo a cui associare la gara, in modo da determinare
          // l'indice di collaborazione con cui effettuare la richiesta di presa in carico gara delegata.
          
          // Prima salvo nell'oggetto presaInCaricoGaraDelegataBean le collaborazioni ricevute da SIMOG
          presaInCaricoGaraDelegataBean.setCollaborazioni(collaborazioni);
          
          // Creo una lista di String[] una con ufficioId e ufficioDenominazione e la metto nel request.
          // Servono per creare la select per selezionare il centro di costo a cui associare la gara
          // che si sta per prendere in carico
          List<String[]> listaCodiciDenominazione = new ArrayList<String[]>();
          
          for (int i=0; i < collaborazioni.getCollaborazioniArray().length; i++) {
            it.avlp.simog.massload.xmlbeans.Collaborazione collaborazione = collaborazioni.getCollaborazioniArray()[i];
            if (collaborazione != null) {
              if (collaborazione.isSetUfficioId() && collaborazione.isSetUfficioDenominazione()) {
                listaCodiciDenominazione.add(new String[] { collaborazione.getUfficioId(), collaborazione.getUfficioDenominazione() } );
              }
            }
          }
          request.setAttribute("listaCodiciDenominazione", listaCodiciDenominazione);

        } else {
          // nessuna collaborazione fornita da Simog
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
          logger.error("La login RPNT su SIMOG non ha restituito alcun collaborazione da usare per l'operazione di presa in carico gara delegata. Impossibile continaure");
          request.setAttribute("bloccoOperazione", "true");
          this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.simog.noCollaborazioni");
        }
      } else {
        logger.error("Presa in Carico gara delegata interrotta a casua della mancanza del CF del tecnico selezionato per diventare RUP della gara");
        this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato");
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      }
    } else {
      logger.error("Presa in Carico gara delegata interrotta a casua della mancanza di dati");
      this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }

    // set nel request del parameter per disabilitare la navigazione in fase di editing
    request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
        CostantiGenerali.DISABILITA_NAVIGAZIONE);
    
    if (logger.isDebugEnabled()) {
      logger.debug("setTecnicoGaraDelegata: fine metodo");
    }
    
    return mapping.findForward(target);
  }

  public ActionForward setCentroCostoGaraDelegata(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException, GestoreException {

    logger.debug("setCentroCostoGaraDelegata: inizio metodo");

    String target = "confermaPresaInCaricoGaraDelegata";
    PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean = (PresaInCaricoGaraDelegataBean)
        request.getSession().getAttribute("presaInCaricoGaraDelegataBean");

    String codiceCentroCosto = null;
    Collaborazione collaborazione = null;

    if (request.getAttribute("centroCosto") != null) {
      codiceCentroCosto = (String) request.getAttribute("centroCosto");
    } else if (StringUtils.isNotEmpty(request.getParameter("centroCosto"))) {
      codiceCentroCosto = request.getParameter("centroCosto");
    }
    
    Collaborazioni collaborazioni = presaInCaricoGaraDelegataBean.getCollaborazioni();
    boolean trovato = false;
    for (int i=0; i < collaborazioni.getCollaborazioniArray().length && !trovato; i++) {
      Collaborazione collaborazioneTmp = collaborazioni.getCollaborazioniArray(i);
      if (codiceCentroCosto.equalsIgnoreCase(collaborazioneTmp.getUfficioId())) {
        collaborazione = collaborazioni.getCollaborazioniArray(i);
        trovato = true;
      }
    }
    
    presaInCaricoGaraDelegataBean.setCollaborazione(collaborazione);
    
    logger.debug("setCentroCostoGaraDelegata: fine metodo");
    
    return mapping.findForward(target);
  }
  
  /**
   * Esecuzione della presa in carico di una gara delegata
   * 
   * @param request
   * @param target
   * @param profiloUtente
   * @param codeinStazAppAttiva
   * @param mb
   * @return
   */
  public ActionForward eseguiPresaInCaricoGaraDelegata(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException, GestoreException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiPresaInCaricoGaraDelegata: inizio metodo");
    }

    String target = "successPrendiInCarico";
    PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean = (PresaInCaricoGaraDelegataBean)
        request.getSession().getAttribute("presaInCaricoGaraDelegataBean");

    String codiceCentroCosto = null;
    Collaborazione collaborazione = null;
    if (presaInCaricoGaraDelegataBean.getCollaborazione() == null) {
      if (request.getAttribute("centroCosto") != null) {
        codiceCentroCosto = (String) request.getAttribute("centroCosto");
      } else if (StringUtils.isNotEmpty(request.getParameter("centroCosto"))) {
        codiceCentroCosto = request.getParameter("centroCosto");
      }
      
      Collaborazioni collaborazioni = presaInCaricoGaraDelegataBean.getCollaborazioni();
      boolean trovato = false;
      for (int i=0; i < collaborazioni.getCollaborazioniArray().length && !trovato; i++) {
        Collaborazione collaborazioneTmp = collaborazioni.getCollaborazioniArray(i);
        if (codiceCentroCosto.equalsIgnoreCase(collaborazioneTmp.getUfficioId())) {
          trovato = true;
          collaborazione = collaborazioni.getCollaborazioniArray(i);
        }
      }
    } else {
      collaborazione = presaInCaricoGaraDelegataBean.getCollaborazione();
      codiceCentroCosto = collaborazione.getUfficioId();
    }
    
    if (StringUtils.isNotEmpty(presaInCaricoGaraDelegataBean.getCfRup()) && 
        collaborazione != null) {

      // Valore di default per il tipo di accesso ai servizi Simog
      int tipoAccessoWSSimog = 0;
      String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
      if (StringUtils.isNotEmpty(a)) {
        tipoAccessoWSSimog = Integer.parseInt(a);
      }

      Object responsePresaInCaricoGaraDelegataObj = null;
      try {
        switch (tipoAccessoWSSimog) {
  
        case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
          responsePresaInCaricoGaraDelegataObj = this.migrazioneSaManager.prendiInCaricoGaraDelegataDiretta(
              presaInCaricoGaraDelegataBean, collaborazione);
          break;
        case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS:
          responsePresaInCaricoGaraDelegataObj = this.migrazioneSaManager.presaInCaricoGaraDelegataIndiretta(
              presaInCaricoGaraDelegataBean, collaborazione);
          break;
        
        default:
          String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
              "indicato il tipo di accesso ai servizi SIMOG";
          logger.error(msgError);

          break;
        }

        ResponsePresaInCaricoGaraDelegata responsePresaInCaricoGaraDelegata = null;
        if (responsePresaInCaricoGaraDelegataObj instanceof it.toscana.rete.rfc.sitatsa.client.ResponsePresaInCaricoGaraDelegata) {
          responsePresaInCaricoGaraDelegata = (it.toscana.rete.rfc.sitatsa.client.ResponsePresaInCaricoGaraDelegata)
                responsePresaInCaricoGaraDelegataObj;
        } else {
          it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata responseOrt = 
            (it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata) responsePresaInCaricoGaraDelegataObj;
          responsePresaInCaricoGaraDelegata = new ResponsePresaInCaricoGaraDelegata();
          responsePresaInCaricoGaraDelegata.setError(responseOrt.getError());
          responsePresaInCaricoGaraDelegata.setSuccess(responseOrt.isSuccess());
          responsePresaInCaricoGaraDelegata.setMessaggio(responseOrt.getMessaggio());
        }
        
        if (responsePresaInCaricoGaraDelegata != null) {
          if (responsePresaInCaricoGaraDelegata.isSuccess()) {
            logger.info("Operazione presa in carico gara per delega su SIMOG terminata con successo ");
            
            if (presaInCaricoGaraDelegataBean.getCodiceGara() == null) {
              // Avvio la procedura di importazione gara e lotti e per ciascun lotto anche 
              // dell'aggiudicazione se ID_F_DELEGATE = 1 o 2. Se ID_F_DELEGATE = 4 si crea l'esito
              
              // Ripristino dei parametri necessari all'operazione di importazione gara e lotti
              HashMap<String,String> hm = presaInCaricoGaraDelegataBean.getHmParametriPerConsultaGara();
              Set<String> chiaviDellaMappa = hm.keySet();
              Iterator<String> iter = chiaviDellaMappa.iterator();
              while(iter.hasNext()) {
                String chiave = iter.next();
                request.setAttribute(chiave,hm.get(chiave));
              }
              target = "successVerificaDati";
            }
          } else {
          
            request.setAttribute("errorePresaInCaricoGaraDelegata", "error");
            
            ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
                CostantiGenerali.PROFILO_UTENTE_SESSIONE);
            
            StringBuffer strBuffer = new StringBuffer("Operazione presa in carico gara delegata fallita (IdAvGara=");
            strBuffer.append(presaInCaricoGaraDelegataBean.getIdAvGara());
            strBuffer.append(" utente ");
            strBuffer.append(profiloUtente.getNome());
            strBuffer.append(" (syscon=");
            strBuffer.append(profiloUtente.getId());
            strBuffer.append("), CF S.A.=");
            strBuffer.append(presaInCaricoGaraDelegataBean.getCfStazAppAttiva());
            
            if (StringUtils.isNotEmpty(responsePresaInCaricoGaraDelegata.getError())) {
              strBuffer.append(" Errore: ");
              strBuffer.append(responsePresaInCaricoGaraDelegata.getError());
              request.setAttribute("garaDelegataMessaggioErrore", responsePresaInCaricoGaraDelegata.getError());
            }
            
            if (StringUtils.isNotEmpty(responsePresaInCaricoGaraDelegata.getMessaggio())) {
              
              String strReturnCarriage = System.getProperty("line.separator");
              if (StringUtils.contains(responsePresaInCaricoGaraDelegata.getMessaggio(), strReturnCarriage)) {
                String[] arrayMessaggi = StringUtils.split(responsePresaInCaricoGaraDelegata.getMessaggio(), strReturnCarriage);
                if (arrayMessaggi.length == 1) {
                  strBuffer.append(" Messaggio: ");
                  strBuffer.append(arrayMessaggi[0]);
                } else {
                  strBuffer.append(" Messaggi: ");
                  for (int i=0; i< arrayMessaggi.length; i++) {
                    strBuffer.append(i+1);
                    strBuffer.append(") ");
                    strBuffer.append(arrayMessaggi[i]);
                  }
                }
                request.setAttribute("garaDelegataArrayMessaggi", arrayMessaggi);
              }
            }
            logger.error(strBuffer.toString());
  
            String strMessaggio = "";
            if (StringUtils.isNotEmpty(responsePresaInCaricoGaraDelegata.getError())) {
              strMessaggio = strMessaggio.concat("Errore: ").concat(responsePresaInCaricoGaraDelegata.getError());
            }
            
            if (StringUtils.isNotEmpty(responsePresaInCaricoGaraDelegata.getMessaggio())) {
              String strReturnCarriage = System.getProperty("line.separator");
              if (StringUtils.contains(responsePresaInCaricoGaraDelegata.getMessaggio(), strReturnCarriage)) {
                String[] arrayMessaggi = StringUtils.split(responsePresaInCaricoGaraDelegata.getMessaggio(), strReturnCarriage);
                if (arrayMessaggi.length == 1) {
                  strMessaggio = strMessaggio.concat("Messaggio: ").concat(arrayMessaggi[0]);
                } else {
                  strMessaggio = strMessaggio.concat("Messaggi: ");
                  for (int i=0; i< arrayMessaggi.length; i++) {
                    strMessaggio = strMessaggio.concat("; ").concat(arrayMessaggi[i]);
                  }
                }
              }
            }
  
            this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.operazioneFallita", strMessaggio);
          }
        }
      } catch (SQLException se) {
        logger.error("Errore durante le operazioni di migrazione della gara IdAvGara="
            + presaInCaricoGaraDelegataBean.getIdAvGara(), se);
      } finally {
        request.getSession().removeAttribute("presaInCaricoGaraDelegataBean");
      }
    } else {
      // mancano dati importanti per continuare l'operazione
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("Mancano parametri necessari all'operazione di presa in carico gara delegata (cfRup e/o collaborazione)");
      this.aggiungiMessaggio(request, "errors.consultaGara.presaInCarico.delega.erroreInaspettato");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("eseguiPresaInCaricoGaraDelegata: fine metodo");
    }

    return mapping.findForward(target);
  }
  
  public ActionForward aggiornaSchedaGara(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException, GestoreException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("aggiornaSchedaGara: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK + "AggiornaGara";
    String messageKey = null;
    String codiceCIG = null;
    String idGara_Cig = null;
    String existCredenziali = null;
    HashMap<String, Object> result = null;

    int syscon = ((ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE)).getId();
    String codUffInt = (String) request.getSession().getAttribute(
        CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    
    try {
    
      if (request.getSession().getAttribute("sessionConsultaGaraLottiBean") == null) {
        if (request.getParameter("codiceGara") != null) {
          idGara_Cig = request.getParameter("codiceGara");
        }
        if (request.getParameter("existCredenziali") != null) {
        	existCredenziali = request.getParameter("existCredenziali");
        }
        
      	String simoguser = null;
        String simogpass = null;

        if (existCredenziali != null && existCredenziali.equals("no")) {
          String recuperaUser = request.getParameter("recuperauser");
          String recuperaPassword = request.getParameter("recuperapassword");
          String memorizza = request.getParameter("memorizza");
  
          // Leggo le eventuali credenziali memorizzate
  
          HashMap<String, String> hMapSIMOGLAUserPass = new HashMap<String, String>();
          hMapSIMOGLAUserPass = this.exportXmlSimogManager.recuperaSIMOGLAUserPass(new Long(syscon));
  
          // Gestione USER
          if ("1".equals(recuperaUser)) {
            simoguser = ((String) hMapSIMOGLAUserPass.get("simoguser"));
          } else {
            simoguser = request.getParameter("simoguser");
          }
  
          // Gestione PASSWORD
          if ("1".equals(recuperaPassword)) {
            simogpass = ((String) hMapSIMOGLAUserPass.get("simogpass"));
          } else {
            simogpass = request.getParameter("simogpass");
          }
          // Gestione memorizzazione delle credenziali o eventuale cancellazione (se richiesta)
          if (simoguser != null && simogpass != null) {
        	  this.exportXmlSimogManager.gestioneWsSimogLoaderAppaltoUserPass(new Long(syscon),
                      simoguser, simogpass, memorizza != null);
          }
        }
  
        if (UtilitySITAT.isIdGaraValido(idGara_Cig)) {
          ConsultaGaraLottiBean consultaGaraLottiBean = new ConsultaGaraLottiBean("*", idGara_Cig, simoguser, simogpass);
          request.getSession().setAttribute("sessionConsultaGaraLottiBean", consultaGaraLottiBean);
          
          result = this.richiestaIdGaraCigManager.consultaGara(idGara_Cig, codUffInt,
              syscon, consultaGaraLottiBean, simoguser, simogpass);
        } else {
          result = this.richiestaIdGaraCigManager.consultaLotto(idGara_Cig, null, codUffInt,
            syscon, simoguser, simogpass);
        }
        
        if (result != null) {
          Boolean esito = (Boolean) result.get("esito");
          if (esito.booleanValue()) {
            
            // Oggetto di ritorno dalla chiamata richiestaIdGaraCigManager.consultaLotto nel caso
            // che idAvGara e cig arrivino valorizzati dal form, per caricare una gara con un
            // singolo lotto (cioe' cig != *)
            ConsultaLottoBean consultaLottoBean = null;
            
            // Oggetto di ritorno dalla chiamata richiestaIdGaraCigManager.consultaGara nel caso
            // che idAvGara e cig arrivino valorizzati dal form, per caricare una gara con un
            // piu' lotti (cioe' cig = *)
            ConsultaGaraLottiBean consultaGaraLottiBean = null;
            
            if (result.containsKey("consultaLottoBean")) {
              consultaLottoBean = (ConsultaLottoBean) result.get("consultaLottoBean");
            }
            
            if (request.getSession().getAttribute("sessionConsultaGaraLottiBean") != null) {
              consultaGaraLottiBean = (ConsultaGaraLottiBean) 
                  request.getSession().getAttribute("sessionConsultaGaraLottiBean");
            }
            
            if (consultaLottoBean != null) {
            	request.setAttribute("codgara", consultaLottoBean.getCodgara());
              if (consultaGaraLottiBean != null) {
            	  consultaGaraLottiBean.setCodgara(consultaLottoBean.getCodgara());
                if (consultaLottoBean.isCaricato()) {
                  consultaGaraLottiBean.incrementaNumeroLottiImportati();
                  if (logger.isDebugEnabled()) {
                    logger.debug("Importazione del lotto terminato con esito positivo (IdAvGara='"
                      + idGara_Cig + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                      + " per conto della SA con CODUFFINT=" + codUffInt + ")");
                  }
                } else if (consultaLottoBean.isEsistente()) {
                  consultaGaraLottiBean.incrementaNumeroLottiEsistenti();
                  if (logger.isDebugEnabled()) {
                    logger.debug("Importazione del lotto non eseguito perche' esistente in base dati (IdAvGara='"
                      + idGara_Cig + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                      + " per conto della SA con CODUFFINT=" + codUffInt + ")");
                  }
                } else {
                  consultaGaraLottiBean.incrementaNumeroLottiNonImportati();
                  if (logger.isDebugEnabled()) {
                    logger.debug("Importazione del lotto terminato con esito negativo. Dettaglio errore: "
                      + consultaLottoBean.getMsg() + " (IdAvGara='" + idGara_Cig + "', CIG='" + codiceCIG
                      + "' da parte dell'utente con SYSCON=" + syscon + 
                      " per conto della SA con CODUFFINT=" + codUffInt + ")");
                  }
                }
                target = CostantiGeneraliStruts.FORWARD_OK + "AggiornaLotto";
              } else {
                if (consultaLottoBean.isCaricato()) {
                  if (logger.isDebugEnabled()) {
                    logger.debug("Importazione del lotto terminato con esito positivo (IdAvGara='"
                      + idGara_Cig + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                      + " per conto della SA con CODUFFINT=" + codUffInt + ")");
                  }
                } else if (consultaLottoBean.isEsistente()) {
                  if (logger.isDebugEnabled()) {
                    logger.debug("Importazione del lotto non eseguito perche' esistente in base dati (IdAvGara='"
                      + idGara_Cig + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                      + " per conto della SA con CODUFFINT=" + codUffInt + ")");
                  }
                } else {
                  if (logger.isDebugEnabled()) {
                    logger.debug("Importazione del lotto terminato con esito negativo. Dettaglio errore: "
                      + consultaLottoBean.getMsg() + " (IdAvGara='" + idGara_Cig + "', CIG='" + codiceCIG
                      + "' da parte dell'utente con SYSCON=" + syscon + 
                      " per conto della SA con CODUFFINT=" + codUffInt + ")");
                  }
                }
                target = CostantiGeneraliStruts.FORWARD_OK + "AggiornaGara";
              }
            } else {
              if (logger.isDebugEnabled()) {
                logger.debug("Importazione della lista CIG della gara (IdAvGara='" + idGara_Cig + "', CIG='" + codiceCIG
                  + "' da parte dell'utente con SYSCON=" + syscon + " per conto della SA con CODUFFINT=" + codUffInt + ")");
              }
              // Avviare la procedura di importazione dei CIG
              target = CostantiGeneraliStruts.FORWARD_OK + "AggiornaLotto";
            }
          } else {
            target = "erroreAggiornaGara";
            if (logger.isDebugEnabled()) {
              logger.debug("Aggiornamento della gara e dei relativi lotti terminato con esito negativo (IDGARA='"
                + idGara_Cig + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                + " per conto della SA con CODUFFINT=" + codUffInt + ")");
            }
            this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS", ((String) result.get("errorMsg")));
            if (result.containsKey("errorMsg")) {
              request.setAttribute("errorMsgDettaglio", result.get("errorMsg"));
            }
          }
        } else {
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
          logger.error("Errore inaspettato: il metodo w9Manager.consultaGara ha restituito un oggetto null, "
              + "mentre era atteso una HashMap con almeno un oggetto Boolean indicante l'esito positivo/negativo dell'operazione.");
        }
      } else {
        ConsultaGaraLottiBean consultaGaraLottiBean = (ConsultaGaraLottiBean)
            request.getSession().getAttribute("sessionConsultaGaraLottiBean");
        
        if (consultaGaraLottiBean.getListaCig() != null && consultaGaraLottiBean.getListaCig().size() > 0) {
          if (consultaGaraLottiBean.getListaCig().size() == consultaGaraLottiBean.getNumeroTotaleLotti()) {
            // Siamo alla prima iterazione. Si usa il primo CIG per scaricare i dati della gara e capire se
            // la gara e' una presa in carico con delega o meno.
            
            
          }
          
          codiceCIG = consultaGaraLottiBean.getListaCig().removeFirst();
          idGara_Cig= consultaGaraLottiBean.getIdAvGara();
          
          result = this.richiestaIdGaraCigManager.consultaLotto(codiceCIG, idGara_Cig, codUffInt,
              syscon, consultaGaraLottiBean.getSimogUser(), consultaGaraLottiBean.getSimogPassword());
          
          if (result != null) {
            Boolean esito = (Boolean) result.get("esito");
            if (esito.booleanValue()) {
              
              // Oggetto di ritorno dalla chiamata richiestaIdGaraCigManager.consultaLotto nel caso
              // che idAvGara e cig arrivino valorizzati dal form, per caricare una gara con un
              // singolo lotto (cioe' cig != *)
              ConsultaLottoBean consultaLottoBean = null;
              
              if (result.containsKey("consultaLottoBean")) {
                consultaLottoBean = (ConsultaLottoBean) result.get("consultaLottoBean");
                request.setAttribute("codgara", consultaLottoBean.getCodgara());
                consultaGaraLottiBean.setCodgara(consultaLottoBean.getCodgara());
              }
              
              if (consultaLottoBean.isCaricato()) {
                consultaGaraLottiBean.incrementaNumeroLottiImportati();
                if (logger.isDebugEnabled()) {
                  logger.debug("Importazione del lotto terminato con esito positivo (IdAvGara='"
                    + idGara_Cig + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                    + " per conto della SA con CODUFFINT=" + codUffInt + ")");
                }
              } else if (consultaLottoBean.isEsistente()) {
                consultaGaraLottiBean.incrementaNumeroLottiEsistenti();
                if (logger.isDebugEnabled()) {
                  logger.debug("Importazione del lotto non eseguito perche' esistente in base dati (IdAvGara='"
                    + idGara_Cig + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                    + " per conto della SA con CODUFFINT=" + codUffInt + ")");
                }
              } else {
                consultaGaraLottiBean.incrementaNumeroLottiNonImportati();
                if (logger.isDebugEnabled()) {
                  logger.debug("Importazione del lotto terminato con esito negativo. Dettaglio errore: "
                    + consultaLottoBean.getMsg() + " (IdAvGara='" + idGara_Cig + "', CIG='" + codiceCIG
                    + "' da parte dell'utente con SYSCON=" + syscon + 
                    " per conto della SA con CODUFFINT=" + codUffInt + ")");
                }
              }
              // Continuare la procedura di importazione dei CIG
              target = CostantiGeneraliStruts.FORWARD_OK + "AggiornaLotto";
            } else {
              target = "erroreAggiornaGara";
              logger.info("Aggiornamento della gara e dei relativi lotti terminato con esito negativo (CODGARA='"
                  + idGara_Cig + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                  + " per conto della SA con CODUFFINT=" + codUffInt + ")");
              this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS", ((String) result.get("errorMsg")));
              if (result.containsKey("errorMsg")) {
                request.setAttribute("errorMsgDettaglio", result.get("errorMsg"));
              }
            }
          } else {
            target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
            logger.error("Errore inaspettato: il metodo w9Manager.consultaGara ha restituito un oggetto null, "
                + "mentre era atteso una HashMap con almeno un oggetto Boolean indicante l'esito positivo/negativo dell'operazione.");
          }
          
        } else {
        	request.setAttribute("codgara", consultaGaraLottiBean.getCodgara());
          // La lista dei CIG e' stata ciclato in tutti i suoi elementi, quindi e'
          // terminata l'operazione di importazione di tutti i lotti della gara
          target = CostantiGeneraliStruts.FORWARD_OK + "AggiornaGara";
          
        }
      }

    } catch (SQLException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.database.dataAccessException";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey);
    } catch (Throwable t) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), t);
      this.aggiungiMessaggio(request, messageKey);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("aggiornaSchedaGara: fine metodo");
    }
    
    return mapping.findForward(target);
  }

  public ActionForward riallineaDatiSIMOG(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSIMOG: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK + "RiallineaSIMOG";
    String messageKey = null;
    String codiceCIG = null;
    String codiceGara = null;
    String existCredenziali = null;
    
    if (request.getParameter("codiceCIG") != null) {
      codiceCIG = (String) request.getParameter("codiceCIG");
    }
    if (request.getParameter("codiceGara") != null) {
      codiceGara = (String) request.getParameter("codiceGara");
    }
    if (request.getParameter("existCredenziali") != null) {
    	existCredenziali = request.getParameter("existCredenziali");
    }
    
    long codGara = Long.parseLong(codiceGara);
    
    ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    int syscon = profiloUtente.getId();
    String codUffInt = (String) request.getSession().getAttribute(
        CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);

    HashMap<String, Object> result = null;
    
    Boolean isUserTecnicoDellaSA = ((Boolean) request.getSession().getAttribute(
        CostantiW9.IS_USER_TECNICO_SA));
    Boolean isUserConCfNoTecnico = ((Boolean) request.getSession().getAttribute(
        CostantiW9.IS_USER_CONCF_NO_TECNICO_SA));
    
    try {
      if (isUserTecnicoDellaSA || isUserConCfNoTecnico || (! "U".equals(profiloUtente.getAbilitazioneStd()))) {
        String simoguser = null;
        String simogpass = null;
          
        if (existCredenziali != null && existCredenziali.equals("no")) {
        	String recuperaUser = request.getParameter("recuperauser");
          String recuperaPassword = request.getParameter("recuperapassword");
          String memorizza = request.getParameter("memorizza");
  
          // Leggo le eventuali credenziali memorizzate
          HashMap<String, String> hMapSIMOGLAUserPass = new HashMap<String, String>();
          hMapSIMOGLAUserPass = this.exportXmlSimogManager.recuperaSIMOGLAUserPass(new Long(syscon));
          
          // Gestione USER
          if (recuperaUser != null && "1".equals(recuperaUser)) {
        	  simoguser = ((String) hMapSIMOGLAUserPass.get("simoguser"));
          } else {
        	  simoguser = request.getParameter("simoguser");
          }
  
          // Gestione PASSWORD
          if (recuperaPassword != null && "1".equals(recuperaPassword)) {
        	  simogpass = ((String) hMapSIMOGLAUserPass.get("simogpass"));
          } else {
        	  simogpass = request.getParameter("simogpass");
          }
          if (!(memorizza != null && memorizza.equals("credenzialiRup"))) {
        	// Gestione memorizzazione delle credenziali o eventuale cancellazione (se richiesta)
              this.exportXmlSimogManager.gestioneWsSimogLoaderAppaltoUserPass(new Long(syscon), 
                  simoguser, simogpass, memorizza != null);
          }
        }

        result = this.richiestaIdGaraCigManager.riallineaDatiSimog(codiceCIG, codGara, codUffInt,
            syscon, simoguser, simogpass);
  
        if (result != null) {
          Boolean esito = (Boolean) result.get("esito");
          if (esito.booleanValue()) {
            logger.info("Allineamento della gara e del relativo lotto terminato con esito positivo (CODGARA='"
                + codiceGara + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                + " per conto della SA con CODUFFINT=" + codUffInt + ")");
            request.setAttribute("RISULTATO", "OK");
          } else {
            target = "erroreRiallineaSIMOG";
            logger.info("Allineamento della gara e del relativo lotto terminato con esito negativo (CODGARA='"
                + codiceGara + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                + " per conto della SA con CODUFFINT=" + codUffInt + ")");
            this.aggiungiMessaggio(request, "errors.consultaGara.erroreWS", ((String) result.get("errorMsg")));
            request.setAttribute("RISULTATO", "KO");
          }
        } else {
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
          logger.error("Errore inaspettato: il metodo w9Manager.riallineaDatiSimog ha restituito un oggetto null, "
              + "mentre era atteso una HashMap con almeno un oggetto Boolean indicante l'esito positivo/negativo dell'operazione.");
        }
      }  else {
        request.setAttribute("richiestaConfermaCreaNuovaGara", "2");
        request.setAttribute("metodo", "riallineaDatiSIMOG");

        logger.error("Operazione lanciata da un utente che non e' ne' tecnico della S.A. attiva, ne' un " +
        		"utente con codice fiscale valorizzato. (Dettagli: "
                + codiceGara + "', CIG='" + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
                + " per conto della SA con CODUFFINT=" + codUffInt + ")");
        request.setAttribute("RISULTATO", "KO");
        this.aggiungiMessaggio(request, "errors.consultaGara.userNoTecnicoSANoCf");
        target = "erroreRiallineaSIMOG";
      }
      
    } catch (SQLException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.database.dataAccessException";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey);
    } catch (Throwable t) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), t);
      this.aggiungiMessaggio(request, messageKey);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("riallineaDatiSIMOG: fine metodo");
    }
    return mapping.findForward(target);
  }
  
  public ActionForward riallineaSchedaGara(ActionMapping mapping, ActionForm form,
	      HttpServletRequest request, HttpServletResponse response)
	          throws IOException, ServletException, GestoreException {
	    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaSchedaGara: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK + "RiallineaGaraSIMOG";
    String messageKey = null;
    String codiceCIG = null;
    String idAvGara = null;
    String codGara = null;
    String existCredenziali = null;
    HashMap<String, Object> result = null;

    int syscon = ((ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE)).getId();
    String codUffInt = (String) request.getSession().getAttribute(
        CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    
    try {
      if (request.getParameter("codiceCIG") != null) {
        codiceCIG = request.getParameter("codiceCIG");
      }
      if (request.getParameter("idAvGara") != null) {
      	idAvGara = request.getParameter("idAvGara");
      }
      if (request.getParameter("codGara") != null) {
      	codGara = request.getParameter("codGara");
  	  }
      String simoguser = null;
      String simogpass = null;
        
      ConsultaGaraLottiBean consultaGaraLottiBean = null;
        
      if (request.getSession().getAttribute("riallineaSchedaGaraBean") == null) {
	      if (request.getParameter("existCredenziali") != null) {
	       	existCredenziali = request.getParameter("existCredenziali");
	      }
      	if (existCredenziali != null && existCredenziali.equals("no")) {
	        String recuperaUser = request.getParameter("recuperauser");
	        String recuperaPassword = request.getParameter("recuperapassword");
	        String memorizza = request.getParameter("memorizza");
	  
	        // Leggo le eventuali credenziali memorizzate
	  
	        HashMap<String, String> hMapSIMOGLAUserPass = new HashMap<String, String>();
	        hMapSIMOGLAUserPass = this.exportXmlSimogManager.recuperaSIMOGLAUserPass(new Long(syscon));
	  
	        // Gestione USER
	        if ("1".equals(recuperaUser)) {
	          simoguser = ((String) hMapSIMOGLAUserPass.get("simoguser"));
	        } else {
	          simoguser = request.getParameter("simoguser");
	        }
	  
	        // Gestione PASSWORD
	        if ("1".equals(recuperaPassword)) {
	          simogpass = ((String) hMapSIMOGLAUserPass.get("simogpass"));
	        } else {
	          simogpass = request.getParameter("simogpass");
	        }
	        if (!(memorizza != null && memorizza.equals("credenzialiRup"))) {
	        	// Gestione memorizzazione delle credenziali o eventuale cancellazione (se richiesta)
		        this.exportXmlSimogManager.gestioneWsSimogLoaderAppaltoUserPass(new Long(syscon),
		           simoguser, simogpass, memorizza != null);
	        }
	      }
      }
      
      if (codiceCIG.equals("*")) {
      	consultaGaraLottiBean = new ConsultaGaraLottiBean(codiceCIG, idAvGara, simoguser, simogpass);
      	result = this.richiestaIdGaraCigManager.consultaGara(idAvGara, codUffInt, syscon, consultaGaraLottiBean, simoguser, simogpass);
      } else {
      	consultaGaraLottiBean = (ConsultaGaraLottiBean) request.getSession().getAttribute("riallineaSchedaGaraBean");
      	if (this.richiestaIdGaraCigManager.getEsistenzaCodiceCIG(new String[] { codiceCIG })) {
      		result = this.richiestaIdGaraCigManager.riallineaDatiSimog(codiceCIG, new Long(codGara), codUffInt,
      	      syscon, consultaGaraLottiBean.getSimogUser(), consultaGaraLottiBean.getSimogPassword());
      	} else {
      		result = this.richiestaIdGaraCigManager.consultaLotto(codiceCIG, consultaGaraLottiBean.getIdAvGara(), codUffInt,
              syscon, consultaGaraLottiBean.getSimogUser(), consultaGaraLottiBean.getSimogPassword());
      	}
      }
      request.setAttribute("codGara", codGara);
      request.setAttribute("idAvGara", idAvGara);
      if (consultaGaraLottiBean.getListaCig() != null && consultaGaraLottiBean.getListaCig().size() > 0) {
      	consultaGaraLottiBean.incrementaNumeroLottiImportati();
            codiceCIG = consultaGaraLottiBean.getListaCig().removeFirst();
            request.setAttribute("codiceCIG", codiceCIG);
            request.getSession().setAttribute("riallineaSchedaGaraBean", consultaGaraLottiBean);
      } else {
      	request.setAttribute("codiceCIG", "");
      	request.getSession().removeAttribute("riallineaSchedaGaraBean");
      }
      if (result != null) {
        Boolean esito = (Boolean) result.get("esito");
        if (esito.booleanValue()) {
      	  request.setAttribute("RISULTATO", "OK");
        } else {
            target = "erroreRiallineaGaraSIMOG";
            request.setAttribute("RISULTATO", "KO");
            if (result.containsKey("consultaLottoBean")) {
          	  ConsultaLottoBean consultaLottoBean = (ConsultaLottoBean)result.get("consultaLottoBean");
          	  if (consultaLottoBean.getMsg()!= null && !consultaLottoBean.getMsg().equals("")) {
          		  this.aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", consultaLottoBean.getCodiceCIG() + " " + consultaLottoBean.getMsg());
          	  } else {
          		  this.aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", ((String) result.get("errorMsg")));
          	  }
            } else {
          	  this.aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", ((String) result.get("errorMsg")));
            }
            request.getSession().removeAttribute("riallineaSchedaGaraBean");
        }
      } else {
      	request.getSession().removeAttribute("riallineaSchedaGaraBean");
      	this.aggiungiMessaggio(request, "errors.gestoreException.*.commons.validate", "Errore inaspettato: il metodo w9Manager.consultaGara ha restituito un oggetto null.");
      	target = "erroreRiallineaGaraSIMOG";
      	logger.error("Errore inaspettato: il metodo w9Manager.consultaGara ha restituito un oggetto null, "
            + "mentre era atteso una HashMap con almeno un oggetto Boolean indicante l'esito positivo/negativo dell'operazione.");
      }
    } catch (SQLException e) {
    	request.getSession().removeAttribute("riallineaSchedaGaraBean");
    	target = "erroreRiallineaGaraSIMOG";
    	messageKey = "errors.database.dataAccessException";
    	logger.error(this.resBundleGenerale.getString(messageKey), e);
    	this.aggiungiMessaggio(request, messageKey);
    } catch (Throwable t) {
    	request.getSession().removeAttribute("riallineaSchedaGaraBean");
    	target = "erroreRiallineaGaraSIMOG";
    	messageKey = "errors.applicazione.inaspettataException";
    	logger.error(this.resBundleGenerale.getString(messageKey), t);
    	this.aggiungiMessaggio(request, messageKey);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaSchedaGara: fine metodo");
    }
    return mapping.findForward(target);
  }
  
  public ActionForward aggiornaDaAccordoQuadro(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException, GestoreException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("aggiornaDaAccordoQuadro: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK + "AggiornaDaAQ";
    String messageKey = null;

    String codGara = null;
    String strCodLott = null;
    String strNumAppa = null;
    
    //String existCredenziali = null;
    HashMap<String, Object> result = null;

    int syscon = ((ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE)).getId();
    String codUffInt = (String) request.getSession().getAttribute(
        CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);

    if (request.getParameter("codiceGara") != null) {
      codGara = request.getParameter("codiceGara");
      strCodLott = request.getParameter("codiceLotto");
      strNumAppa = request.getParameter("numAppa");
      
      try {
        // Il codice commentato potrebbe servire per sviluppare la logica per inizializzazione della
        // adesione accordo quadro in configurazione Vigilanza.
        
        String simoguser = null;
        String simogpass = null;
        String existCredenziali = null;
        
        if (request.getParameter("existCredenziali") != null) {
          existCredenziali = request.getParameter("existCredenziali");
        }
        if (existCredenziali != null && existCredenziali.equals("no")) {
          String recuperaUser = request.getParameter("recuperauser");
          String recuperaPassword = request.getParameter("recuperapassword");
          String memorizza = request.getParameter("memorizza");
    
          // Leggo le eventuali credenziali memorizzate
          HashMap<String, String> hMapSIMOGLAUserPass = new HashMap<String, String>();
          hMapSIMOGLAUserPass = this.exportXmlSimogManager.recuperaSIMOGLAUserPass(new Long(syscon));
    
          // Gestione USER
          if ("1".equals(recuperaUser)) {
            simoguser = ((String) hMapSIMOGLAUserPass.get("simoguser"));
          } else {
            simoguser = request.getParameter("simoguser");
          }
    
          // Gestione PASSWORD
          if ("1".equals(recuperaPassword)) {
            simogpass = ((String) hMapSIMOGLAUserPass.get("simogpass"));
          } else {
            simogpass = request.getParameter("simogpass");
          }
          if (!(memorizza != null && memorizza.equals("credenzialiRup"))) {
              // Gestione memorizzazione delle credenziali o eventuale cancellazione (se richiesta)
              this.exportXmlSimogManager.gestioneWsSimogLoaderAppaltoUserPass(new Long(syscon),
                 simoguser, simogpass, memorizza != null);
          }
        }
        
        // aggiornare i dati di W9APPA e W9AGGI
        SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", 
            request.getSession().getServletContext(), SqlManager.class);
        String cigAccQuadro = (String) sqlManager.getObject("select CIG_ACCQUADRO from W9GARA where CODGARA=?", 
            new Object[] { new Long(codGara) } );

        // Scarico dei dati del CIG_ACCORDO_QUADRO da SIMOG
        HashMap<String, Object> hm2 = this.richiestaIdGaraCigManager.getXmlSimog(cigAccQuadro, syscon, codUffInt, simoguser, simogpass);

        ConsultaGaraResponseDocument consultaGaraResponseDocument = null;
        if (((Boolean) hm2.get("esito")).booleanValue() ) {
          String garaXml = (String) hm2.get("garaXml");
          try {
            consultaGaraResponseDocument = ConsultaGaraResponseDocument.Factory.parse(garaXml);
          } catch (XmlException xe) {
            logger.error("Errore nel parsing del XML ricevuto da SIMOG per il CIG = " + cigAccQuadro);
          }
          
          if (consultaGaraResponseDocument != null) {
            result = this.w9Manager.aggiornaAdesioneAccordoQuadro(new Long(codGara), new Long(strCodLott), new Long(strNumAppa),
                codUffInt, cigAccQuadro, consultaGaraResponseDocument);
          } else {
            logger.error("Errore nella lettura dei dati ricevuti da SIMOG");
            request.setAttribute("erroreInitAccordoQuadro", "Errore nella lettra dei dati ricevuti da SIMOG");
          }
        } else {
          logger.error("Errore nell'interazione con i servizi SIMOG: " + (String) hm2.get("msgErr"));
          request.setAttribute("erroreInitAccordoQuadro", (String) hm2.get("msgErr"));
        }
        
        request.setAttribute("codGara", codGara);

        if (result != null) {
          Boolean esito = (Boolean) result.get("esito");
          if (esito.booleanValue()) {
            request.setAttribute("RISULTATO", "OK");
          } else {
            target = "erroreAggiornaDaAQ";
            request.setAttribute("RISULTATO", "KO");

          }
        } else {
          this.aggiungiMessaggio(request, "errors.applicazione.inaspettataException");
          target = "erroreAggiornaDaAQ";
          logger.error("Errore inaspettato: il metodo w9Manager.consultaGara ha restituito un oggetto null, "
              + "mentre era atteso una HashMap con almeno un oggetto Boolean indicante l'esito positivo/negativo dell'operazione.");
        }
      //} catch (GestoreException ge) {
      } catch (SQLException e) {
        target = "erroreAggiornaDaAQ";
        messageKey = "errors.database.dataAccessException";
        logger.error(this.resBundleGenerale.getString(messageKey), e);
        this.aggiungiMessaggio(request, messageKey);
      } catch (Throwable t) {
        target = "erroreAggiornaDaAQ";
        messageKey = "errors.applicazione.inaspettataException";
        logger.error(this.resBundleGenerale.getString(messageKey), t);
        this.aggiungiMessaggio(request, messageKey);
      }

    } else {
      logger.error("Impossibile recuperare il codice gara su cui eseguire l'operazione di" +
        " inizializzazione della scheda adesione accordo quadro");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("aggiornaDaAccordoQuadro: fine metodo");
    }
    return mapping.findForward(target);
  }
  
  public ActionForward prendiInCaricoGara(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException, GestoreException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("prendiInCaricoGara: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK.concat("PrendiInCarico");
    //String messageKey = null;

    ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    
    if (request.getSession().getAttribute("sessionPresaInCaricoBean") != null) {
      MigrazioneBean mb = (MigrazioneBean) request.getSession().getAttribute("sessionPresaInCaricoBean");
      
      try {
        this.migrazioneSaManager.updateStazioneAppaltante(mb, profiloUtente);
        long codiceGara = mb.getCodiceGaraDaMigrare(0);
        request.setAttribute("codgara", new Long(codiceGara));
        
      } catch (Throwable t) {
        logger.error("Errore inaspettato nell'operazione di presa in carica della Gara con IdAvGara/CIG= " 
            + mb.getGaraSABean()[0].getIdAvGara() + " da parte dell'utente " + profiloUtente.getNome()
            + " USRSYS.SYSCON=" + profiloUtente.getId(), t);

        // in caso di errore
        mb.setErrore("updatesa");
      } finally {
        if (StringUtils.isNotEmpty(mb.getErrore())) {
          this.aggiungiMessaggio(request, "errors.migrazioneSA.erroreInaspettato");
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        }
      }
      
    } else {
      // In sessione non e' presente l'oggetto con i dati necessari alla presa
      // in carico della gara. Impossibile continuare l'operazione
      
      logger.error("In sessione non esiste l'oggetto sessionPresaInCaricoBean necessario " +
      		"nell'operazione di presa in carica di una gara");
      this.aggiungiMessaggio(request, "errors.migrazioneSA.erroreInaspettato");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    // set nel request del parameter per disabilitare la navigazione in fase di editing
    request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
        CostantiGenerali.DISABILITA_NAVIGAZIONE);
    
    if (logger.isDebugEnabled()) {
      logger.debug("prendiInCaricoGara: fine metodo");
    }
    return mapping.findForward(target);
  }
  
}