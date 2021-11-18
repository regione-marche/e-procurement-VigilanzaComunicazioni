package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9AggiudicatarieManager;
import it.eldasoft.w9.dao.vo.ImpresaAggiudicataria;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * DispatchAction per la gestione della lista delle imprese invitate/partecipanti
 * 
 * @author Luca.Giacomazzo
 */
public class ListaW9AggiudicatarieAction extends AbstractFunzioneTag {
	//extends DispatchActionBaseNoOpzioni {
	
	
public ListaW9AggiudicatarieAction() {
	super(3, new Class[] { String.class, String.class, String.class });
		//super(numParam, paramClasses);
		// todo ...
	}
@Override
public String function(PageContext arg0, Object[] arg1) throws JspException {
	
	// generazione del SQL
	
	// ogni riga è un hashmap--> vector
	
	// pongo un List<Vector> nella request
	
	// poi nella jsp  genelista  usnaod il varname sui campilista
	
	// TODO Auto-generated method stub
	return null;
}


  private static Logger logger = Logger.getLogger(ListaW9AggiudicatarieAction.class);

  private W9AggiudicatarieManager w9AggiudicatarieManager;
  
  /**
   * @param w9ImpreseManager the w9ImpreseManager to set
   */
  public void setW9AggiudicatarieManager(W9AggiudicatarieManager w9AggiudicatarieManager) {
    this.w9AggiudicatarieManager = w9AggiudicatarieManager;
  }

//  public ActionForward visualizza(final ActionMapping mapping, final ActionForm form, 
//      final HttpServletRequest request, final HttpServletResponse response) throws Exception {
//
//    if (logger.isDebugEnabled()) {
//      logger.debug("visualizza: inizio metodo");
//    }
//    
//    String target = CostantiGeneraliStruts.FORWARD_OK + "Lista";
//    Long codiceGara  = null;
//    Long numAppa = null;
//    Long codiceLotto = null;
//
//    
//    String tempKey = (String) request.getSession().getAttribute("key");
//    if (tempKey != null) {
//      codiceGara = new Long(tempKey.split(";")[0].split(":")[1]);
//      numAppa = new Long(tempKey.split(";")[1].split(":")[1]);
//      codiceLotto = new Long(tempKey.split(";")[2].split(":")[1]);
//    } else {
//      if (request.getParameter("codGara") != null) {
//        codiceGara = Long.parseLong(request.getParameter("codGara"));
//      } else if (request.getAttribute("codGara") != null) {
//        codiceGara = (Long) request.getAttribute("codGara");
//      }
//      
//      if (request.getParameter("numAppa") != null) {
//    	  numAppa = Long.parseLong(request.getParameter("numAppa"));
//        } else if (request.getAttribute("numAppa") != null) {
//        	numAppa = (Long) request.getAttribute("numAppa");
//        }
//      
//      if (request.getParameter("codLott") != null) {
//        codiceLotto = Long.parseLong(request.getParameter("codLott"));
//      } else if (request.getAttribute("codLott") != null) {
//        codiceLotto = (Long) request.getAttribute("codLott");
//      }
//      
//
//    }
////    try {
////      if (codiceGara != null && codiceLotto != null && numAppa != null) {
////        List< ImpresaAggiudicataria > listaW9Aggiudicatarie = this.w9AggiudicatarieManager.getListaW9Aggiudicatarie(
////            codiceGara, codiceLotto, numAppa);
////  
////        request.setAttribute("listaW9Aggiudicatarie", listaW9Aggiudicatarie);
////        request.setAttribute("risultatiPerPagina", new Integer(20));
////      }
////    } catch (GestoreException ge) {
////      logger.error("Errore nell'estrarre le imprese invitate/partecipanti", ge);
////      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
////
////    } catch (SQLException e) {
////      logger.error("Errore nell'estrarre le imprese invitate/partecipanti", e);
////      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
////    }
////    
////    if (logger.isDebugEnabled()) {
////      logger.debug("visualizza: fine metodo");
////    }
////        
////    return mapping.findForward(target);
//  }
  
  
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
      
      this.w9AggiudicatarieManager.deleteImpreseSelez(keys);

      String[] arrayChiave = keys[0].split(";");
      
      request.setAttribute("codGara", Long.parseLong(arrayChiave[1]));
      request.setAttribute("numAppa", Long.parseLong(arrayChiave[2]));
      request.setAttribute("codLott", Long.parseLong(arrayChiave[3]));

    } catch (SQLException se) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.database.dataAccessException";
      logger.error(this.resBundleGenerale.getString(messageKey), se);
      //this.aggiungiMessaggio(request, messageKey);
    } catch (Throwable t) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), t);
      //this.aggiungiMessaggio(request, messageKey);
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
      
      if (arrayChiave.length == 5) {
        // Delete impresa singola
        this.w9AggiudicatarieManager.deleteImpresaSingola(
            Long.parseLong(arrayChiave[0]), Long.parseLong(arrayChiave[1]), Long.parseLong(arrayChiave[2]), Long.parseLong(arrayChiave[3]), Long.parseLong(arrayChiave[4]));
      } else {
        // Delete gruppo di impresa
        this.w9AggiudicatarieManager.deleteGruppoImprese(
            Long.parseLong(arrayChiave[0]), Long.parseLong(arrayChiave[1]), Long.parseLong(arrayChiave[2]), Long.parseLong(arrayChiave[3]), Long.parseLong(arrayChiave[5]));
      }

      request.setAttribute("codGara", Long.parseLong(arrayChiave[1]));
      request.setAttribute("numAppa", Long.parseLong(arrayChiave[2]));
      request.setAttribute("codLott", Long.parseLong(arrayChiave[3]));
      request.setAttribute("metodo", "visualizza");
    } catch (SQLException se) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), se);
      //this.aggiungiMessaggio(request, messageKey);
    } catch (Throwable t) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), t);
      //this.aggiungiMessaggio(request, messageKey);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("elimina: fine metodo");
    }
    return mapping.findForward(target);
  }



}
