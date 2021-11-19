package it.eldasoft.w9.web.struts.sa;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.sa.MigrazioneSaManager;
import it.eldasoft.w9.common.bean.GaraSABean;
import it.eldasoft.w9.common.bean.MigrazioneBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * DispatchAction per la gestione dell'operazione di Migrazione delle
 * Stazioni appaltanti a partire dalla lista delle gare. 
 * L'operazione e' divisa in tre fasi:
 * 1. recupero da Simog degli xml delle gare selezionate;
 * 2. controlli preliminari sui dati degli xml;
 * 3. aggiornamento dei dati delle gare. 
 * 
 * @author Luca.Giacomazzo
 */
public class MigrazioneStazioniAppaltantiAction extends DispatchActionBaseNoOpzioni {

  /** Logger di classe. */
  private static Logger     logger = Logger.getLogger(MigrazioneStazioniAppaltantiAction.class);
  
  private MigrazioneSaManager migrazioneSaManager;
  
  public void setMigrazioneSaManager(MigrazioneSaManager migrazioneSaManager) {
    this.migrazioneSaManager = migrazioneSaManager;
  }
  
  private final String SUCCESS_MIGRAZIONE = CostantiGeneraliStruts.FORWARD_OK + "Migrazione";
  private final String SUCCESS_ANNULLA = CostantiGeneraliStruts.FORWARD_OK + "Annulla";
  private final String ERROR_INTERROMPI = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "Interrompi";
  
  
  /** Nome dell'attributo caricato in sessione contenente tutti gli oggetti necessari all'operazione */
  private final String SESSION_MIGRAZIONE_BEAN = "MIGRAZIONE_SA_BEAN";
  
  /**
   * Metodo per aprire la prima pagina della funzione di Migrazione delle
   * Stazione appaltanti nelle gare selezionate.
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
    
    if (this.getMigrazioneBean(request) != null) {
      request.getSession().removeAttribute(SESSION_MIGRAZIONE_BEAN);
      logger.warn("All'avvia dell'operazione e' stato inaspettatamente trovato in sessione l'oggetto "
          + SESSION_MIGRAZIONE_BEAN + ". Prima di continuare e' stato rimosso.");
    }
    
    String target = SUCCESS_MIGRAZIONE;
    try {
      // Array con le chiavi delle gare da migrare nel formato W9GARA.CODGARA=N:*****
      String[] keys = request.getParameterValues(UtilityTags.DEFAULT_HIDDEN_KEYS_SELECTED);
      
      MigrazioneBean mb = new MigrazioneBean(keys.length);
      long[] arrayCodiciGara = new long[keys.length];
      for (int i=0; i < keys.length; i++) {
        arrayCodiciGara[i] = new Long(keys[i].split(":")[1]).longValue();
      }
      
      mb.setArrayCodiciGareDaMigrare(arrayCodiciGara);
      mb.setCodiceStazAppOrigine((String) request.getSession().getAttribute(
          CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO));
      mb.setNomeStazAppOrigine((String)(String) request.getSession().getAttribute(
          CostantiGenerali.NOME_UFFICIO_INTESTATARIO_ATTIVO));
      
      request.getSession().setAttribute(SESSION_MIGRAZIONE_BEAN, mb);
  
      // set nel request del parameter per disabilitare la navigazione in fase di editing
      request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
          CostantiGenerali.DISABILITA_NAVIGAZIONE);
      
    } catch (Throwable t) {
      request.getSession().removeAttribute(SESSION_MIGRAZIONE_BEAN);
      
      ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
          CostantiGenerali.PROFILO_UTENTE_SESSIONE);
      logger.error("Errore inaspettato nella fase di inizializzazione dell'operazione di migrazione " +
      		"delle stazioni appaltanti da parte dell'utente " + profiloUtente.getNome()
      		+ " (USRSYS.SYSCON=" + profiloUtente.getId() + "), per conto della S.A. " +
      		(String) request.getSession().getAttribute(CostantiGenerali.DESC_PROFILO_ATTIVO) +
      		" (UFFINT.CODEIN=" + (String) request.getSession().getAttribute(
              CostantiGenerali.PROFILO_ATTIVO) + ")", t);
      
      request.getSession().removeAttribute(SESSION_MIGRAZIONE_BEAN);
      
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    if (logger.isDebugEnabled()) {
      logger.debug("inizializza: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
  /**
   * Metodo per la gestione del caricamento da Simog del Xml delle gare selezionate.
   * (Fase 1) 
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward scaricaXml(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
  
    if (logger.isDebugEnabled()) {
      logger.debug("scaricaXml: inizio metodo");
    }
    String target = SUCCESS_MIGRAZIONE;
    
    try {
      MigrazioneBean mb = this.getMigrazioneBean(request);
      
      ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
          CostantiGenerali.PROFILO_UTENTE_SESSIONE);
      
      if (mb != null) {
        if (mb.getNumeroGareRecuperate() <= mb.getNumeroGareDaMigrare()) {
          mb.passaAllaGaraSuccessiva();
          try {
            GaraSABean garaSaBean = this.migrazioneSaManager.valorizzaGaraSABean(
                mb.getCodiceGaraDaMigrareInElaborazione(), (long) profiloUtente.getId(),
                mb.getCodiceStazAppOrigine()); 
            if (garaSaBean.isOk()) {
              mb.setGaraBeanInElaborazione(garaSaBean);
              mb.incrementaNumeroXmlRecuperati();
            } else {
              mb.setErrore("scaricaxml");
              this.aggiungiMessaggio(request, "errors.migrazioneSA.erroreServiziSimog");
              target = ERROR_INTERROMPI;
            }
          } catch (SQLException se) {
            logger.error("Errore inaspettato nel recupero dei dati della gara", se);
            
            request.getSession().removeAttribute(SESSION_MIGRAZIONE_BEAN);
            target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
          }
        } else {
          // E' terminata la fase di recupero da Simog degli XML delle gare.
          // Si puo' passare alla fase di verifica dei controlli preliminari.
          
          // Il numero gare recuperate ha superato il valore massimo, 
          // quindi lo si va a correggere.
          mb.resetGaraInElaborazione();
        }
      } else {
        logger.error("scaricaXml: in sessione non esiste l'oggetto " + SESSION_MIGRAZIONE_BEAN);
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      }
      
      // set nel request del parameter per disabilitare la navigazione in fase di editing
      request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
          CostantiGenerali.DISABILITA_NAVIGAZIONE);
    } catch (Throwable t) {
      logger.error("Errore inaspettato nello scaricamento dei codici CIG e dei dati comuni di un lotto", t);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("scaricaXml: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
  /**
   * Metodo che effettua i controlli preliminari sui dati recuperati da Simog.
   * (Fase 2) 
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward controlliPreliminari(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
      logger.debug("controlliPreliminari: inizio metodo");
    }
    
    String target = SUCCESS_MIGRAZIONE;
    MigrazioneBean mb = this.getMigrazioneBean(request);

    ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    
    if (mb != null) {
      try {
        boolean result = this.migrazioneSaManager.controlloDatiGaraSABean(mb, (long) profiloUtente.getId());
        
        if (! result) {
          this.aggiungiMessaggio(request, mb.getGaraSABeanInElaborazione().getDescrErrore(),
              mb.getGaraSABeanInElaborazione().getIdAvGara());
          mb.setErrore("controlli");
          target = ERROR_INTERROMPI;
        } else {
          mb.resetGaraInElaborazione();
        }
      } catch (GestoreException ge) {
        logger.error("Errore inaspettato nel controllo dei dati delle gare", ge);
        
        request.getSession().removeAttribute(SESSION_MIGRAZIONE_BEAN);
        this.aggiungiMessaggio(request, "errors.migrazioneSA.erroreInaspettato");
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      } catch (Throwable t) {
        logger.error("Errore inaspettato nel controllo dei dati delle gare da migrare", t);
        
        request.getSession().removeAttribute(SESSION_MIGRAZIONE_BEAN);
        this.aggiungiMessaggio(request, "errors.migrazioneSA.erroreInaspettato");
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      }
    } else {
      logger.error("controlliPreliminari: in sessione non esiste l'oggetto " + SESSION_MIGRAZIONE_BEAN);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    // set nel request del parameter per disabilitare la navigazione in fase di editing
    request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
        CostantiGenerali.DISABILITA_NAVIGAZIONE);
    
    if (logger.isDebugEnabled()) {
      logger.debug("controlliPreliminari: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  

  /**
   * Metodo per l'esecuzione della migrazione delle stazioni appaltanti con 
   * l'effettivo aggiornamento delle occorrenze nella base dati.
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return 
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward eseguiMigrazione(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
      logger.debug("eseguiMigrazione: inizio metodo");
    }
    
    String target = SUCCESS_MIGRAZIONE;
    MigrazioneBean mb = this.getMigrazioneBean(request);
    
    ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    
    String codeinStazApp = (String) request.getSession().getAttribute(
        CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    
    if (mb != null) {
      try {
        this.migrazioneSaManager.updateStazioneAppaltante(mb, profiloUtente);

      } catch (Throwable t) {
        logger.error("Errore inaspettato nell'operazione di migrazione delle stazioni appaltanti"
           + " da parte dell'utente " + profiloUtente.getNome() + " USRSYS.SYSCON=" + profiloUtente.getId()
           + " nel migrare la gara dalla S.A. con UFFINT.CODEIN=" + codeinStazApp, t);

        // in caso di errore
        mb.setErrore("updatesa");
      } finally {
        if (StringUtils.isNotEmpty(mb.getErrore())) {
          this.aggiungiMessaggio(request, "errors.migrazioneSA.erroreInaspettato");
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
        }
      }
    } else {
      logger.error("eseguiMigrazione: in sessione non esiste l'oggetto " + SESSION_MIGRAZIONE_BEAN);
      this.aggiungiMessaggio(request, "errors.migrazioneSA.erroreInaspettato");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    // set nel request del parameter per disabilitare la navigazione in fase di editing
    request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
        CostantiGenerali.DISABILITA_NAVIGAZIONE);
    
    return mapping.findForward(target);
  }
  
  
  /**
   * Metodo per l'annullamento dell'intera operazione in seguito a degli errori
   * nell'interazione con i servizi Simog, nella validazione dei dati, ecc...
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
    
    String target = SUCCESS_ANNULLA;
    
    logger.info("Interruzione dell'operazione di migrazione stazioni appaltanti");
    
    request.getSession().removeAttribute(SESSION_MIGRAZIONE_BEAN);
    
    if (logger.isDebugEnabled()) {
      logger.debug("annulla: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
  
  /** 
   * Ritorna l'oggetto MigrazioneBean recuperandolo dalla sessione, <i>null</i> altrimenti.
   * 
   * @param request HTTPRequest
   * @return Ritorna l'oggetto MigrazioneBean, null altrimenti 
   */
  private MigrazioneBean getMigrazioneBean(HttpServletRequest request) {
    return (MigrazioneBean) request.getSession().getAttribute(SESSION_MIGRAZIONE_BEAN);
  }
  
}
