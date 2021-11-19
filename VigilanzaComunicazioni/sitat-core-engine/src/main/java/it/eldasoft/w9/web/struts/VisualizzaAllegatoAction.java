package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.W9Manager;

import java.io.IOException;
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
 * Esegue il download di un file PDF allegato ad una gara o ad un appalto. 
 */
public class VisualizzaAllegatoAction extends ActionBaseNoOpzioni {

  /**
   * Logger della classe.
   */
  static Logger     logger = Logger.getLogger(VisualizzaAllegatoAction.class);

  /**
   * Manager della classe.
   */
  private W9Manager w9Manager;

  /**
   * Set di W9Manager tramite Spring.
   * @param manager w9Manager da settare internamente alla classe.
   */
  public final void setW9Manager(final W9Manager manager) {
    w9Manager = manager;
  }

  /**
   * Implementazione del metodo ActionBaseNoOpzioni.runAction.
   * 
   * @param mapping ActionMapping
   * @param form ActionForm
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @throws IOException IOException
   * @throws ServletException ServletException
   */
  protected ActionForward runAction(final ActionMapping mapping, final ActionForm form,
      final HttpServletRequest request, final HttpServletResponse response)
      throws IOException, ServletException {
    if (logger.isDebugEnabled()) {
      logger.debug("runAction: inizio metodo");
    }
    
    String target = null;
    String messageKey = null;
    HashMap<String, Object> params = new HashMap<String, Object>();

    try {
      String tab = request.getParameter("tab");
      String strCodGara = request.getParameter("codgara");
      String strNumPubb = request.getParameter("numPubb");
      Long codGara = null;
      if (StringUtils.isNotEmpty(strCodGara)) {
        codGara = Long.parseLong(strCodGara);
      }

      if ("gara".equals(tab)) {
        Long numdoc = Long.parseLong(request.getParameter("numdoc"));
        params.put("codGara", codGara);
        params.put("numdoc", numdoc);
        if (StringUtils.isNotEmpty(strNumPubb)) {
          params.put("num_pubb", Long.parseLong(strNumPubb));
        }
        this.w9Manager.downloadFileAllegato("GARA", params, response);
        
      } else if ("esito".equals(tab)) {
        Long codLotto = Long.parseLong(request.getParameter("codlott"));
        params.put("codGara", codGara);
        params.put("codLotto", codLotto);
        this.w9Manager.downloadFileAllegato("ESITO", params, response);
        
      } else if ("fase".equals(tab)) {
        Long codLotto = Long.parseLong(request.getParameter("codlott"));
        Long faseEsecuzione = Long.parseLong(request.getParameter("fase_esecuzione"));
        Long numFase = Long.parseLong(request.getParameter("num_fase"));
        Long numdoc = Long.parseLong(request.getParameter("numdoc"));
        params.put("codGara", codGara);
        params.put("codLotto", codLotto);
        params.put("fase_esecuzione", faseEsecuzione); 
        params.put("num_fase", numFase); 
        params.put("numdoc", numdoc);
        this.w9Manager.downloadFileAllegato("FASE", params, response);

      } else if ("flussi".equals(tab)) {
        String idflusso = request.getParameter("idflusso");
        params.put("idFlusso", idflusso);
        this.w9Manager.downloadFileAllegato("W9FLUSSI", params, response);

      } else if ("flussi_eliminati".equals(tab)) {
          String idflusso = request.getParameter("idflusso");
          params.put("idFlusso", idflusso);
          this.w9Manager.downloadFileAllegato("W9FLUSSI_ELIMINATI", params, response);

      } else if ("avviso".equals(tab)) {
        Long idavviso = Long.parseLong(request.getParameter("idavviso"));
        Long codsistema = Long.parseLong(request.getParameter("codsistema"));
        String codein = request.getParameter("codein");
        Long numdoc = new Long(request.getParameter("numdoc"));
        params.put("idavviso", idavviso);
        params.put("codein", codein);
        params.put("codsistema", codsistema);
        params.put("numdoc", numdoc);
        this.w9Manager.downloadFileAllegato("AVVISO", params, response);
      } else if ("inbox".equals(tab)) {
        String idcomun = request.getParameter("idcomun");
        params.put("idcomun", idcomun);
        this.w9Manager.downloadFileAllegato("W9INBOX", params, response);
      } else if ("entinaz".equals(tab)) {
        Long codLotto = Long.parseLong(request.getParameter("codlott"));
        params.put("codGara", codGara);
        params.put("codLotto", codLotto);
        this.w9Manager.downloadFileAllegato("W9LOTT_ENTINAZ", params, response);
      } else if ("w9xml".equals(tab)) {
        Long codLotto = Long.parseLong(request.getParameter("codlott"));
        Long numxml = Long.parseLong(request.getParameter("numxml"));
        params.put("codGara", codGara);
        params.put("codLotto", codLotto);
        params.put("numXml", numxml);
        
        this.w9Manager.downloadFileAllegato("W9XML", params, response);
      } else if ("outbox".equals(tab)) {
          String idcomun = request.getParameter("idcomun");
          params.put("idcomun", idcomun);
          this.w9Manager.downloadFileAllegato("W9OUTBOX", params, response);
      } else if ("w_coopusr".equals(tab)) {
          String id_richiesta = request.getParameter("id_richiesta");
          params.put("id_richiesta", id_richiesta);
          this.w9Manager.downloadFileAllegato("W_COOPUSR", params, response);
      }
    } catch (IOException io) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.download";
      this.aggiungiMessaggio(request, messageKey);
    } catch (GestoreException e) {
      logger.error("Errore nella visualizzazione/download del file allegato", e);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.download";
      this.aggiungiMessaggio(request, messageKey);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("runAction: fine metodo");
    }

    if (target != null) {
      return mapping.findForward(target);
    } else {
      return null;
    }
  }

}
