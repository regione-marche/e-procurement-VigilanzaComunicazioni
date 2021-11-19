package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.ConfigurazioneProfilo;
import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.admin.ProfiliManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.gene.db.domain.admin.Profilo;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.GestoreEccezioni;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe si apertura della scheda gara a seconda della provenienza
 * 
 * @author mirco.franzoni
 * 
 */
public class SchedaANASAction extends DispatchActionBaseNoOpzioni {

  private static Logger logger = Logger.getLogger(SchedaANASAction.class);

  private ProfiliManager profiliManager;
  
  /** 
   * Reference al mamager delle informazioni  relative alle librerie generali
   * per la gestione dei profili
   */
  private GeneManager        geneManager;
  
  public void setProfiliManager(ProfiliManager profiliManager) {
	 this.profiliManager = profiliManager;
  }
  
  /**
   * @param geneManager geneManager da settare internamente alla classe.
   */
  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }
  
  public ActionForward apri(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    // Aggiunta del settaggio del path del file in apertura
    String isPopUp = UtilityStruts.getParametroString(request,
        UtilityTags.DEFAULT_HIDDEN_IS_POPUP);
    String pathScheda = null;
    if (isPopUp != null && isPopUp.equals("1")) {
      pathScheda = UtilityStruts.getParametroString(request,
          UtilityTags.DEFAULT_HIDDEN_PATH_SCHEDA_POPUP);
    } else {
      pathScheda = UtilityStruts.getParametroString(request,
          UtilityTags.DEFAULT_HIDDEN_PATH_SCHEDA);
    }
    request.setAttribute(UtilityTags.DEFAULT_HIDDEN_FORM_TO_JSP,
        pathScheda == null ? "" : pathScheda);
    // Recupero il nome del profilo attivo
    String profiloAttivo = (String) request.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
    String prefissoProfilo = profiloAttivo;
    int index = profiloAttivo.indexOf("#");
    if (index != -1) {
    	prefissoProfilo = profiloAttivo.substring(0, index);
    } 
    // Recupero il valore di PROV_DATO
    String prov_dato = request.getParameter("PROV_DATO");
    String nuovoProfilo = prefissoProfilo;
    if (prov_dato != null && !prov_dato.equals("")) {
    	nuovoProfilo = prefissoProfilo + "#" + prov_dato;
    }
    // Se il profilo da caricare è diverso da quello attivo
    boolean caricato = false;
    ConfigurazioneProfilo exist = null;
    if (! nuovoProfilo.equals(profiloAttivo)) {
    	exist = this.geneManager.getProfili().getProfilo((nuovoProfilo));
    	if (exist != null && exist.isOk()) {
    		caricato = true;
      } else {
      	nuovoProfilo = prefissoProfilo + "#00";
       	if (! nuovoProfilo.equals(profiloAttivo)) {
       		exist = this.geneManager.getProfili().getProfilo((nuovoProfilo));
       		caricato = exist != null && exist.isOk();
       	}
      }
    }
    //Se il nuovo profilo è stato caricato
    if (caricato) {
    	HashMap<String, HashSet<String>> hashProfiliKeys = (HashMap<String, HashSet<String>>) request.getSession().getAttribute(CostantiGenerali.PROFILI_KEYS);
    	HashMap<String, HashSet<String>> newProfiloKeys = new HashMap<String, HashSet<String>>();
    	newProfiloKeys.put(nuovoProfilo, hashProfiliKeys.get(profiloAttivo));
    	request.getSession().setAttribute(CostantiGenerali.PROFILI_KEYS, newProfiloKeys);
    	
    	hashProfiliKeys = (HashMap<String, HashSet<String>>) request.getSession().getAttribute(CostantiGenerali.PROFILI_KEY_PARENTS);
    	newProfiloKeys = new HashMap<String, HashSet<String>>();
    	newProfiloKeys.put(nuovoProfilo, hashProfiliKeys.get(profiloAttivo));
    	request.getSession().setAttribute(CostantiGenerali.PROFILI_KEY_PARENTS, newProfiloKeys);
    	
    	Profilo profiloSelezionato = this.profiliManager.getProfiloByCodProfilo(nuovoProfilo);
    	// Set in sessione il codice e il nome del profilo attivo
    	request.getSession().setAttribute(CostantiGenerali.PROFILO_ATTIVO, nuovoProfilo);
    	request.getSession().setAttribute(CostantiGenerali.NOME_PROFILO_ATTIVO, profiloSelezionato.getNome());
    	request.getSession().setAttribute(CostantiGenerali.DESC_PROFILO_ATTIVO, profiloSelezionato.getDescrizione());
    }
    
    // Ridireziono sull'azione che gestisce le schede
    return UtilityStruts.redirectToPage("/Scheda.do", true, request);
  }

  /**
   * MOdifica dell'occorrenza
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward modifica(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    // {MF150207} Aggiunta delle gestione del controllo sulle protezioni
    try {
      // Setto il metodo in modifica
      request.setAttribute(UtilityTags.DEFAULT_HIDDEN_PARAMETRO_MODO,
          UtilityTags.SCHEDA_MODO_MODIFICA);
      // Ridireziono sull'azione che gestisce le schede
      return this.apri(mapping, form, request, response);
    } catch (Throwable t) {
      if (UtilityStruts.getParametroString(request,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT) != null)
        request.setAttribute(UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA,
            UtilityStruts.getParametroString(request,
                UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT));
      return GestoreEccezioni.gestisciEccezioneAction(t, this, request, logger,
          mapping);
    }

  }
  
}
