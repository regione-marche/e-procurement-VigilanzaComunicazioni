package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9AggiudicatarieManager;
import it.eldasoft.w9.bl.W9ImpreseManager;
import it.eldasoft.w9.dao.vo.ImpresaPartecipante;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * DispatchAction per la gestione della lista delle imprese invitate/partecipanti
 * 
 * @author Luca.Giacomazzo
 */
public class ListaW9SelezionePartecipantiAction extends ListaW9ImpreseAction {

  private static Logger logger = Logger.getLogger(ListaW9ImpreseAction.class);

  private W9ImpreseManager w9ImpreseManager;
  
  private W9AggiudicatarieManager w9AggiudicatarieManager;
  
  /**
   * @param w9ImpreseManager the w9ImpreseManager to set
   */
  public void setW9ImpreseManager(W9ImpreseManager w9ImpreseManager) {
    this.w9ImpreseManager = w9ImpreseManager;
  }
  
  public void setw9AggiudicatarieManager(W9AggiudicatarieManager w9AggiudicatarieManager){
	  this.w9AggiudicatarieManager = w9AggiudicatarieManager;
  }
  
  public ActionForward visualizza(final ActionMapping mapping, final ActionForm form, 
	      final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	    if (logger.isDebugEnabled()) {
	      logger.debug("visualizza: inizio metodo");
	    }
	    
	    String target = CostantiGeneraliStruts.FORWARD_OK + "Lista";
	    Long codiceGara  = null;
	    Long numeroAppalto =null;
	    Long codiceLotto = null;
	    
	    
	    String tempKey = (String) request.getAttribute("key");
	    if (tempKey != null) {
	      codiceGara = new Long(tempKey.split(";")[0].split(":")[1]);
	      numeroAppalto = new Long(tempKey.split(";")[1].split(":")[1]);
	      codiceLotto = new Long(tempKey.split(";")[2].split(":")[1]);
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
	  
	        request.setAttribute("listaW9SelezionePartecipanti", listaW9Imprese);
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
  
  public ActionForward inserisciSelez(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	  
    if (logger.isDebugEnabled()) {
      logger.debug("inserisciSelez: inizio metodo");
    }
    
    String target = "successInsert";
    // String messageKey = null;

  
    String keys[] = request.getParameterValues(UtilityTags.DEFAULT_HIDDEN_KEYS_SELECTED);
    
    try {
	    for(int i = 0; i < keys.length; i++){
	    	String[] arrayChiavi = keys[i].split(";");
	    	Long codGara = new Long(arrayChiavi[0]);
	    	Long numAppa = new Long(arrayChiavi[1]);
	    	Long codLott = new Long(arrayChiavi[2]);
	    	Long numImpr = new Long(arrayChiavi[3]);
	    	Long numRagg = null;
	    	if(arrayChiavi.length > 4){
	    		numRagg = new Long(arrayChiavi[4]);
	    	}   	
	    	this.w9AggiudicatarieManager.insertW9Aggiudicataria(codGara, numAppa, codLott, numImpr, numRagg);
	    }
	}  catch (GestoreException ge) {
	      logger.error("Errore nell'inserimento in imprese aggiudicatarie delle imprese invitate/partecipanti", ge);
	      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
	} 
      
    if (logger.isDebugEnabled()) {
      logger.debug("inserisciSelez: fine metodo");
    }
    
    // Se l'operazione di insert e' andata a buon fine (cioe' nessuna
    // eccezione) inserisco nel request l'attributo RISULTATO valorizzato con
    // "OK", che permettera' alla popup di inserimento ditta di richiamare
    // il refresh della finestra padre e di chiudere se stessa
    request.setAttribute("RISULTATO", "OK");
    
    return mapping.findForward(target);
    
 
  }

  
}
