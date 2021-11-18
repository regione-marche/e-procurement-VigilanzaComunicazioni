package it.eldasoft.w9.web.struts.sa;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.bl.sa.SaManager;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType;
import it.toscana.rete.rfc.sitatsa.client.FaseType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Dispatch Action per la gestione della funzionalita' di caricamento delle fasi
 * Anagrafica gara lotto e Elenco imprese invitate da SitatORT in SitatSA.
 * 
 * @author Luca.Giacomazzo
 */
public class ImportaDatiDaOrAction extends DispatchActionBaseNoOpzioni {

  private final String SUCCESS_INIT = CostantiGeneraliStruts.FORWARD_OK + "Inizializza";
  
  private final String SUCCESS_GARA = CostantiGeneraliStruts.FORWARD_OK + "IdgaraCIG";
  
  private final String SUCCESS_FASI = CostantiGeneraliStruts.FORWARD_OK + "SelezionaFasi";
  
  private final String SUCCESS_RESULT = CostantiGeneraliStruts.FORWARD_OK + "ImportDati";
  
  private final String SUCCESS_ANNULLA = CostantiGeneraliStruts.FORWARD_OK + "Annulla";
  
  /**
   * Nome dell'oggetto salvato in sessione con diverse variabili utili per
   * l'implementazione della logica della funzione 'Importa dati da OR'.
   */
  private final String IMPORTA_DATI_DA_OR_SESSION = "ImportaDatiDaOR";
  
  /** Logger di classe. */
  private static Logger     logger = Logger.getLogger(ImportaDatiDaOrAction.class);

  private SqlManager sqlManager;
  private W9Manager w9Manager;
  private SaManager saManager;
  
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
  
  public void setW9Manager(W9Manager w9Manager) {
    this.w9Manager = w9Manager;
  }
  
  public void setSaManager(SaManager saManager) {
    this.saManager = saManager;
  }
  
  /**
   * Metodo per aprire la prima pagina della funzione Importa dati da OR.
   * Vi si accede solo se la sessione e' valida.
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward inizializza(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
  
    if (logger.isDebugEnabled()) {
      logger.debug("inizializza: inizio metodo");
    }
    
    String target = SUCCESS_INIT;
    
    // set nel request del parameter per disabilitare la navigazione in fase
    // di editing
    request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
        CostantiGenerali.DISABILITA_NAVIGAZIONE);
    
    if (logger.isDebugEnabled()) {
      logger.debug("inizializza: fine metodo");
    }
    
    return mapping.findForward(target);
  }

  /**
   * Metodo per uscire dal wizard di importazione dei dati da OR, 
   * con svuotamento dei dati in sessione.
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward annulla(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
  
    if (logger.isDebugEnabled()) {
      logger.debug("annulla: inizio metodo");
    }
    
    // Rimozione dalla sessione dell'oggetto contenente i dati recuperati durante il wizard
    request.getSession().removeAttribute(IMPORTA_DATI_DA_OR_SESSION);
    
    String target = SUCCESS_ANNULLA;
    
    if (logger.isDebugEnabled()) {
      logger.debug("annulla: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
  
  /**
   * Controllo dei valori inseriti per IdAvGara e CIG. Se i controlli sono positivi
   * allora si passa alla pagina per la scelta delle fase da importare.
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return 
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward verificaDatiGara(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
  
    if (logger.isDebugEnabled()) {
      logger.debug("verificaDati: inizio metodo");
    }
  
    HashMap<String, String> hmImportaDatiDaOr = null;
    
    if (request.getSession().getAttribute(IMPORTA_DATI_DA_OR_SESSION) == null) {
      hmImportaDatiDaOr = new HashMap<String, String>();
      request.getSession().setAttribute(IMPORTA_DATI_DA_OR_SESSION, hmImportaDatiDaOr);
    } else {
      hmImportaDatiDaOr = (HashMap<String, String>)
          request.getSession().getAttribute(IMPORTA_DATI_DA_OR_SESSION);
    }
    
    String target = SUCCESS_GARA;
    String codiceCIG = null;
    String idAvGara = null;
    
    if (request.getParameter("codiceCIG") != null) {
      codiceCIG = request.getParameter("codiceCIG");
      hmImportaDatiDaOr.put("cig", codiceCIG);
    }

    if (request.getParameter("codiceGara") != null) {
      idAvGara = request.getParameter("codiceGara");
      hmImportaDatiDaOr.put("idavgara", idAvGara);
    }
    
    if (codiceCIG != null && idAvGara != null) {
      boolean isIdGaraValido = UtilitySITAT.isIdGaraValido(idAvGara);
      boolean isCigValido = UtilitySITAT.isCigValido(codiceCIG);

      if (isIdGaraValido && isCigValido) {
        
        // Verifica esistenza delle fasi Anagrafica Gara-lotto e dell'elenco
        // delle imprese invitate nella base dati dell'Osservatorio
        
        ProfiloUtente utente = (ProfiloUtente)
            request.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
        int syscon = utente.getId();

        boolean esisteAnagraficaInOR = false;
        boolean esisteElencoImpreseInOR = false;
        
        String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
        try {
          String cfSA = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?",
              new Object[] { codein });
          if (StringUtils.isNotEmpty(cfSA)) {
            String cfRup = this.w9Manager.getCfTec(cfSA, syscon);

            if (StringUtils.isNotEmpty(cfRup)) {
              FaseEsecuzioneType faseEsec = new FaseEsecuzioneType();
              faseEsec.setCodiceFase(FaseType.FASE_988);
              
              esisteAnagraficaInOR = this.saManager.esisteSchedaInOR(faseEsec, codiceCIG, idAvGara, cfRup, cfSA);
              if (esisteAnagraficaInOR) {
                hmImportaDatiDaOr.put("esisteA03InOR", "true");

                faseEsec.setCodiceFase(FaseType.FASE_101);
                esisteElencoImpreseInOR = this.saManager.esisteSchedaInOR(faseEsec, codiceCIG, idAvGara, cfRup, cfSA);
  
                if (esisteElencoImpreseInOR) {
                  hmImportaDatiDaOr.put("esisteA24InOR", "true");
                } else {
                  hmImportaDatiDaOr.put("esisteA24InOR", "false");
                }
              } else {
                hmImportaDatiDaOr.put("esisteA03InOR", "false");
                hmImportaDatiDaOr.put("esisteA24InOR", "false");
              }
              
              if (!esisteAnagraficaInOR) {
                target = SUCCESS_INIT;
                this.aggiungiMessaggio(request, "error.importDaOr.dbORT.garaInesistente", idAvGara, codiceCIG);
              } else {
                try {
                  boolean esisteGaraInSA = UtilitySITAT.existsGara(idAvGara, this.sqlManager);
                  boolean esisteLottoInSA = UtilitySITAT.existsLotto(codiceCIG, this.sqlManager);

                  if (!esisteGaraInSA && !esisteLottoInSA) {
                    hmImportaDatiDaOr.put("esisteGaraInSA", "false");
                    hmImportaDatiDaOr.put("esisteLottoInSA", "false");
                  } else if (esisteGaraInSA && esisteLottoInSA) {
                    boolean isCigLottoDellaGara = UtilitySITAT.isCigLottoDellaGara(idAvGara, codiceCIG, this.sqlManager);
                    if (isCigLottoDellaGara) {
                      hmImportaDatiDaOr.put("esisteGaraInSA", "true");
                      hmImportaDatiDaOr.put("esisteLottoInSA", "true");
                    } else {
                      // in base dati esiste il lotto con il CIG indicato,
                      // ma non e' lotto della gara indicata
                      this.aggiungiMessaggio(request, "error.importDaOr.codiceCIG.nonLottoDellaGara");
                      target = SUCCESS_INIT;
                    }
                  } else if (esisteGaraInSA && !esisteLottoInSA) {
                    hmImportaDatiDaOr.put("esisteGaraInSA", "true");
                    hmImportaDatiDaOr.put("esisteLottoInSA", "false");
                  } else {
                    logger.error("Errore nei dati. In base dati non esiste la gara con IdGara=" + idAvGara +
                        ", ma esiste il lotto con CIG=" + codiceCIG);
                    this.aggiungiMessaggio(request, "error.importDaOr.garaLotto.situazioneAnomala");
                    target = SUCCESS_INIT;
                  }
                } catch (SQLException se) {
                  target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
                  logger.error("Errore nel determinare se esiste gara con IdGara=" + idAvGara
                      + " e/o lotto con CIG=" + codiceCIG, se);
                  this.aggiungiMessaggio(request, "errors.database.dataAccessException");
                }
              }
            } else {
              // Errore impossibile che si verifichi: non esiste in base dati il tecnico della SA in uso e
              // associato all'utente che sta usando l'operazione 'Importa dati da OR'. IMPOSSIBILE!
              logger.error("Errore impossibile che si verifichi: non esiste in base dati il tecnico della SA in uso e "
                + "associato all'utente che sta usando l'operazione 'Importa dati da OR'. UFFINT.CFEIN=".concat(cfSA)
                + ", USRSYS.SYSCON=" + syscon);
              this.aggiungiMessaggio(request, "errors.database.dataAccessException");
              target = SUCCESS_INIT;
            }
          } else {
            // Errore impossibile che si verifichi: non esiste in base dati la stazione appaltante
            // con cui l'utente e' entrato in Sitat. IMPOSSIBILE!
            logger.error("Errore impossibile che si verifichi: non esiste in base dati la stazione appaltante"
              + "con cui l'utente e' entrato in Sitat. UFFINT.CODEIN=".concat(codein));
            this.aggiungiMessaggio(request, "errors.database.dataAccessException");
            target = SUCCESS_INIT;
          }
        } catch (SQLException se) {
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
          logger.error("Errore nel determinare CFSA necessario per richiedere l'esistenza delle fasi " +
              "anagrafica gara lotto e elelnco imprese invitate nella base dati di SitatORT. Dettagli: idGara="
              + idAvGara + ", CIG=" + codiceCIG + ", CODEIN=" + codein + ", SYSCON="+ syscon, se);
          this.aggiungiMessaggio(request, "errors.database.dataAccessException");
        } catch (GestoreException ge) {
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
          logger.error("Errore nel determinare CFRUP necessario per richiedere l'esistenza delle fasi " +
              "anagrafica gara lotto e elelnco imprese invitate nella base dati di SitatORT. Dettagli: idGara="
              + idAvGara + ", CIG=" + codiceCIG + ", CODEIN=" + codein + ", SYSCON="+ syscon, ge);
          this.aggiungiMessaggio(request, "errors.database.dataAccessException");
        }

      } else {
        if (!isIdGaraValido) {
          this.aggiungiMessaggio(request, "errors.consultaGara.idGaraNonValido");
        } else {
          if (!isCigValido) {
            this.aggiungiMessaggio(request, "errors.consultaGara.codiceCigNonValido");
          }
        }
        target = SUCCESS_INIT;
      }

    } else {
      // errore nella lettura dei parametri in ingresso.
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("Errore nel determinare se esiste gara con IdGara=" + idAvGara
          + " e/o lotto con CIG=" + codiceCIG);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("verificaDati: fine metodo");
    }
    
    // set nel request del parameter per disabilitare la navigazione in fase di editing
    request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
        CostantiGenerali.DISABILITA_NAVIGAZIONE);
    
    return mapping.findForward(target);
  }
  
  
  public ActionForward operazioniDaEseguire(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
      logger.debug("operazioniDaEseguire: inizio metodo");
    }
    
    String target = SUCCESS_RESULT;
    
    HashMap<String, String> hmImportaDatiDaOr = null;
    
    if (request.getSession().getAttribute(IMPORTA_DATI_DA_OR_SESSION) != null) {
      hmImportaDatiDaOr = (HashMap<String, String>)
      request.getSession().getAttribute(IMPORTA_DATI_DA_OR_SESSION);
    }
    
    
    
    
    
    // set nel request del parameter per disabilitare la navigazione in fase di editing
    request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
        CostantiGenerali.DISABILITA_NAVIGAZIONE);
    
    if (logger.isDebugEnabled()) {
      logger.debug("operazioniDaEseguire: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
  
  /**
   * 
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward importaDati(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
  
    if (logger.isDebugEnabled()) {
      logger.debug("importaDati: inizio metodo");
    }
  
    String target = SUCCESS_RESULT;
    
    HashMap<String, String> hmImportaDatiDaOr = null;
    
    if (request.getSession().getAttribute(IMPORTA_DATI_DA_OR_SESSION) != null) {
      hmImportaDatiDaOr = (HashMap<String, String>)
      request.getSession().getAttribute(IMPORTA_DATI_DA_OR_SESSION);
    }
    
    ProfiloUtente utente = (ProfiloUtente)
    request.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    int syscon = utente.getId();
    String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    //try {
    String cfSA = null;
      //String cfSA = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?",
      //    new Object[] { codein });
      //if (StringUtils.isNotEmpty(cfSA)) {
    String cfRup = null;
      //  String cfRup = this.w9Manager.getCfTec(cfSA, syscon);
    
    if (hmImportaDatiDaOr != null) {
      String operazioneAnagraficaGara = null;
      String operazioneAnagraficaLotto = null;
      String operazioneElencoImprese = null;
      
      if (request.getParameter("operazioneSullaGara") != null) {
        operazioneAnagraficaGara = request.getParameter("operazioneSullaGara");
      }
      
      if (request.getParameter("operazioneSulLotto") != null) {
        operazioneAnagraficaLotto = request.getParameter("operazioneSulLotto");
      }
      
      if (request.getParameter("operazioneSulElencoImprese") != null) {
        operazioneElencoImprese = request.getParameter("operazioneSulElencoImprese");
      }
      
      if (StringUtils.isNotEmpty(operazioneAnagraficaGara)
          || StringUtils.isNotEmpty(operazioneAnagraficaLotto)
          || StringUtils.isNotEmpty(operazioneElencoImprese)) {
        
        String codiceCIG = hmImportaDatiDaOr.get("cig");
        String idAvGara = hmImportaDatiDaOr.get("idavgara");
      
        FaseEsecuzioneType faseEsec = new FaseEsecuzioneType();
        faseEsec.setCodiceFase(FaseType.FASE_988);
        
        it.toscana.rete.rfc.sitatsa.client.ResponseSchedaType responseScheda = this.saManager.getScheda(
            codiceCIG, idAvGara, cfRup, cfSA, faseEsec);
        
        this.saManager.importaDatiDaOR(idAvGara, codiceCIG, cfRup, cfSA, operazioneAnagraficaGara, 
            operazioneAnagraficaLotto, operazioneElencoImprese);
      
      }
    } else {
      target = SUCCESS_INIT;
      
    }
    
    // set nel request del parameter per disabilitare la navigazione in fase di editing
    request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
        CostantiGenerali.DISABILITA_NAVIGAZIONE);

    if (logger.isDebugEnabled()) {
      logger.debug("importaDati: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
}
