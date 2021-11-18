package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.W9ImpreseManager;
import it.eldasoft.w9.dao.vo.ImpresaPartecipante;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * DispatchAction per la gestione della lista delle imprese invitate/partecipanti
 * 
 * @author Luca.Giacomazzo
 */
public class ListaW9ImpreseAction extends DispatchActionBaseNoOpzioni {

  private static Logger logger = Logger.getLogger(ListaW9ImpreseAction.class);

  private W9ImpreseManager w9ImpreseManager;
  
  /**
   * @param w9ImpreseManager the w9ImpreseManager to set
   */
  public void setW9ImpreseManager(W9ImpreseManager w9ImpreseManager) {
    this.w9ImpreseManager = w9ImpreseManager;
  }

  public ActionForward visualizza(final ActionMapping mapping, final ActionForm form, 
      final HttpServletRequest request, final HttpServletResponse response) throws Exception {

    if (logger.isDebugEnabled()) {
      logger.debug("visualizza: inizio metodo");
    }
    
    String target = CostantiGeneraliStruts.FORWARD_OK + "Lista";
    Long codiceGara  = null;
    Long codiceLotto = null;
    
    String tempKey = (String) request.getSession().getAttribute("key");
    if (tempKey != null) {
      codiceGara = new Long(tempKey.split(";")[0].split(":")[1]);
      codiceLotto = new Long(tempKey.split(";")[1].split(":")[1]);
    } else {
      if (request.getParameter("codGara") != null) {
        codiceGara = Long.parseLong(request.getParameter("codGara"));
      } else if (request.getAttribute("codGara") != null) {
        codiceGara = (Long) request.getAttribute("codGara");
      }
      
      if (request.getParameter("codLott") != null) {
        codiceLotto = Long.parseLong(request.getParameter("codLott"));
      } else if (request.getAttribute("codLott") != null) {
        codiceLotto = (Long) request.getAttribute("codLott");
      }

    }
    try {
      if (codiceGara != null && codiceLotto != null) {
        List< ImpresaPartecipante > listaW9Imprese = this.w9ImpreseManager.getListaW9Imprese(
            codiceGara, codiceLotto);
  
        request.setAttribute("listaW9Imprese", listaW9Imprese);
        request.setAttribute("risultatiPerPagina", new Integer(20));
      }
    } catch (GestoreException ge) {
      logger.error("Errore nell'estrarre le imprese invitate/partecipanti", ge);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;

    } catch (SQLException e) {
      logger.error("Errore nell'estrarre le imprese invitate/partecipanti", e);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("visualizza: fine metodo");
    }
        
    return mapping.findForward(target);
  }
  
  
  public ActionForward eliminaSelez(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    if (logger.isDebugEnabled()) {
      logger.debug("eliminaSelez: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK + "Delete";
    String messageKey = null;

    try {
      String keys[] = request.getParameterValues(UtilityTags.DEFAULT_HIDDEN_KEYS_SELECTED);
      
      this.w9ImpreseManager.deleteImpreseSelez(keys);

      String[] arrayChiave = keys[0].split(";");
      request.setAttribute("codGara", Long.parseLong(arrayChiave[0]));
      request.setAttribute("codLott", Long.parseLong(arrayChiave[1]));

    } catch (SQLException se) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.database.dataAccessException";
      logger.error(this.resBundleGenerale.getString(messageKey), se);
      this.aggiungiMessaggio(request, messageKey);
    } catch (Throwable t) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), t);
      this.aggiungiMessaggio(request, messageKey);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("eliminaSelez: fine metodo");
    }
    
    return mapping.findForward(target);
  }

  
  public ActionForward elimina(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    
    if (logger.isDebugEnabled()) {
      logger.debug("elimina: inizio metodo");
    }
    
    String target = CostantiGeneraliStruts.FORWARD_OK + "Delete";
    String messageKey = null;

    String key = request.getParameter("key");
    
    try {
      String[] arrayChiave = key.split(";");
      
      if (arrayChiave.length == 3) {
        // Delete impresa singola
        this.w9ImpreseManager.deleteImpresaSingola(
            Long.parseLong(arrayChiave[0]), Long.parseLong(arrayChiave[1]), Long.parseLong(arrayChiave[2]));
      } else {
        // Delete gruppo di impresa
        this.w9ImpreseManager.deleteGruppoImprese(
            Long.parseLong(arrayChiave[0]), Long.parseLong(arrayChiave[1]), Long.parseLong(arrayChiave[3]));
      }

      request.setAttribute("codGara", Long.parseLong(arrayChiave[0]));
      request.setAttribute("codLott", Long.parseLong(arrayChiave[1]));
      request.setAttribute("metodo", "visualizza");
    } catch (SQLException se) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), se);
      this.aggiungiMessaggio(request, messageKey);
    } catch (Throwable t) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), t);
      this.aggiungiMessaggio(request, messageKey);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("elimina: fine metodo");
    }
    return mapping.findForward(target);
  }

}
