package it.eldasoft.w9.web.struts.sa;

import it.eldasoft.commons.TabControlliManager;
import it.eldasoft.commons.beans.MessaggioControlloBean;
import it.eldasoft.commons.beans.ResultBean;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.w9.bl.sa.ExportLiguriaDigitaleManager;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * DispatchAction per la gestione delle operazioni di invio dei dati di un lotto ad
 * Appalti Liguria, che è l'applicativo dell'Osservatorio Regionale della regione Liguria.
 * 
 * @author Luca.Giacomazzo
 */
public class InviaDatiLottoOsservatorioLiguriaAction extends DispatchActionBaseNoOpzioni {

  /** Logger di classe. */
  private static Logger     logger = Logger.getLogger(InviaDatiLottoOsservatorioLiguriaAction.class);
  
  private TabControlliManager tabControlliManager;
  
  private ExportLiguriaDigitaleManager exportLiguriaDigitaleManager;
  
  public void setTabControlliManager(TabControlliManager tabControlliManager) {
    this.tabControlliManager = tabControlliManager;
  }
  
  public void setExportLiguriaDigitaleManager(ExportLiguriaDigitaleManager exportLiguriaDigitaleManager) {
    this.exportLiguriaDigitaleManager = exportLiguriaDigitaleManager;
  }
  
  public ActionForward inizio(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
      logger.debug("inizio: inizio metodo");
    }
    
    String target = CostantiGeneraliStruts.FORWARD_OK + "_inizio";
    String strCodiceGara = request.getParameter("codiceGara");
    String strCodiceLotto = request.getParameter("codiceLotto");

    try {
      String codiceCIG = UtilitySITAT.getCIGLotto(
          new Long(strCodiceGara), new Long(strCodiceLotto), this.exportLiguriaDigitaleManager.getSqlManager());
      
      if (StringUtils.isNotEmpty(codiceCIG)) {
        request.setAttribute("codiceCIG", codiceCIG);
      }
      
      request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA, CostantiGenerali.DISABILITA_NAVIGAZIONE);
    } catch (Throwable t) {
      logger.error("Errore inaspettato durante l'inizializzazione della funzione di invio dati all'osservatorio regionale", t);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("inizio: fine metodo");
    }
    return mapping.findForward(target);
  }
  
  /**
   * Metodo che effettua i controlli preliminari sui dati da inviare all'Osservatorio Regionale.
   */
  public ActionForward controlloDati(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
      logger.debug("controlloDati: inizio metodo");
    }
    
    String target = CostantiGeneraliStruts.FORWARD_OK + "_controlli";

    String strCodiceGara = request.getParameter("codiceGara");
    String strCodiceLotto = request.getParameter("codiceLotto");
    String eseguireSoloControlloDati = request.getParameter("eseguireSoloControlloDati");
    
    if (StringUtils.isNotEmpty(eseguireSoloControlloDati)) {
    	request.setAttribute("nonEseguireInvioDati", "true"); 
    }
    
    try {
      List<MessaggioControlloBean> listaMessaggiDiControllo = new ArrayList<MessaggioControlloBean>();
      
      this.tabControlliManager.eseguiControlliLotto(new Long(strCodiceGara), new Long(strCodiceLotto),
          "APPALTILIGURIA", listaMessaggiDiControllo);
      
      // Se ritorna una lista di oggetti MessaggioControllo allora si visualizza tale lista e si blocca l'invio
      // altrimenti si torna a video, consentendo all'utente di effettuare l'invio
      
      if (listaMessaggiDiControllo.size() == 0) {
        // Tutti i controlli di validazione sono stati superati e quindi si puo'
        // proseguire con l'invio dei dati all'osservatorio di Regione Liguria
        request.setAttribute("numeroErrori", "0");
        request.setAttribute("numeroWarning", "0");
        request.setAttribute("esitoControlli", "1");
      } else {
        Long numeroErrori = new Long(0);
        Long numeroWarning = new Long(0);
        for (Iterator<MessaggioControlloBean> iterator = listaMessaggiDiControllo.iterator(); iterator.hasNext();) {
          MessaggioControlloBean messaggioControlloBean = iterator.next();
          if (StringUtils.equals("E", messaggioControlloBean.getTipoMessaggio())) {
            numeroErrori++;
          } else if (StringUtils.equals("W", messaggioControlloBean.getTipoMessaggio())) {
            numeroWarning++;
          }
        }

        // I dati sono incompleti. Vengono mostrati a video tutti i messaggi di controllo. 
        request.setAttribute("listaMessaggiDiControllo", listaMessaggiDiControllo);
        request.setAttribute("numeroErrori", numeroErrori);
        request.setAttribute("numeroWarning", numeroWarning);
        request.setAttribute("esitoControlli", "2");
        
        request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA, CostantiGenerali.DISABILITA_NAVIGAZIONE);
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "_controlli";
      }
    } catch (SQLException se) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("Errore inaspettato nel recupero dei dati del lotto e delle fasi " +
          "in fase esecuzione dei controlli preliminari", se);
    } catch (Throwable t) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("Errore inaspettato nel controllo dei dati delle gare da migrare", t);
    }
    
    // set nel request del parameter per disabilitare la navigazione in fase di editing
    //request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA,
    //    CostantiGenerali.DISABILITA_NAVIGAZIONE);
    
    if (logger.isDebugEnabled()) {
      logger.debug("controlloDati: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
  /**
   * Metodo che effettua i controlli preliminari sui dati da inviare all'Osservatorio Regionale.
   */
  public ActionForward eseguiInvioDati(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
      logger.debug("eseguiInvioDati: inizio metodo");
    }
    
    String target = CostantiGeneraliStruts.FORWARD_OK + "_invio";
    
    String strCodiceGara = request.getParameter("codiceGara");
    String strCodiceLotto = request.getParameter("codiceLotto");

    Long codiceGara = null;  
    Long codiceLotto = null; 
    try {
      codiceGara = Long.parseLong(strCodiceGara);
      codiceLotto = Long.parseLong(strCodiceLotto);
    } catch (NumberFormatException e) {
      logger.error("Errore nella inizializzazione del codice gara e codice lotto necessari al proseguo dell'operazione", e);
      this.aggiungiMessaggio(request, "");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    
    try {
      ResultBean result = this.exportLiguriaDigitaleManager.exportXml(codiceGara, codiceLotto, profiloUtente);
      if (result.getOk()) {
        request.setAttribute("esito", "OK");
        request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA, CostantiGenerali.DISABILITA_NAVIGAZIONE);
      } else {
        StringBuffer strBuf = null;
        if (result.getMessaggi().length == 1) {
          strBuf = new StringBuffer(result.getMessaggi()[0]);
        } else {
          strBuf = new StringBuffer("");
          for (int i = 0; i < result.getMessaggi().length; i++) {
            strBuf.append(result.getMessaggi()[i]);
            strBuf.append(" - ");
          }
        }
        logger.error(strBuf.toString());
        
        request.setAttribute("esito", "KO");
        request.setAttribute("messaggiErrore", result.getMessaggi());
        request.setAttribute(CostantiGenerali.NAVIGAZIONE_DISABILITATA, CostantiGenerali.DISABILITA_NAVIGAZIONE);

        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE + "_invio";
      }
    } catch (SQLException e) {
      logger.error("Errore sql inaspettato nell'invio dei dati all'osservatorio", e);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;

    } catch (Throwable t) {
      logger.error("Errore inaspettato nell'invio dei dati all'osservatorio", t);
      
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiInvioDati: fine metodo");
    }
    
    return mapping.findForward(target);
  }
  
}
