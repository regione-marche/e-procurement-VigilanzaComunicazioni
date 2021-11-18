package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esegue il download di un file PDF allegato ad una gara o ad un appalto. 
 */
public class VisualizzaAllegatoAction extends ActionBaseNoOpzioni {

	 /** Logger Log4J di classe. */
  private static Logger     logger = Logger.getLogger(VisualizzaAllegatoAction.class);
  /** manager per operazioni di interrogazione del db. */
  private PtManager ptManager;

  /**
   * @param manager
   *        w9Manager da settare internamente alla classe.
   */
  public void setPtManager(PtManager manager) {
    ptManager = manager;
  }

  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    if (logger.isDebugEnabled()) {
    	logger.debug("runAction: inizio metodo");
    }

    String target = null;
    String messageKey = null;
    HashMap<String, Object> params = new HashMap<String, Object>();

    try {
      String tab = request.getParameter("tab");

      if ("piatri".equals(tab)) {
    	  Long contri = new Long(request.getParameter("contri"));
    	  Long tiprog = new Long(request.getParameter("tiprog"));
          String piatriID = request.getParameter("piatriID");
          params.put("contri", contri);
          params.put("tiprog", tiprog);
          params.put("piatriID", piatriID);
          this.ptManager.downloadFileAllegato("PIATRI", params, request, response);
      } else if ("flussi".equals(tab)) {
          String idflusso = request.getParameter("idflusso");
          String tipo = request.getParameter("tipo");
          params.put("idFlusso", idflusso);
          params.put("tipo", tipo);
          this.ptManager.downloadFileAllegato("PTFLUSSI", params, request, response);

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
