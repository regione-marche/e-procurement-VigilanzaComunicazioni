/*
 * Created on 3/set/12
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.web.struts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xmlbeans.XmlOptions;

import it.eldasoft.contratti.rlo.xmlbeans.FeedBackDocument;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.UploadFileForm;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.XMLRLOManager;

/**
 * Action per il caricamente di una gara a partire da un file XML.
 * 
 * @author Mirco.Franzoni
 */
public class ImportXMLRLOAction extends ActionBaseNoOpzioni {

  static Logger      logger = Logger.getLogger(ImportXMLRLOAction.class);

  private XMLRLOManager importXMLRLOManager;
  
  /**
   * @param manager 
   *        manager da settare internamente alla classe.
   */
  
  public void setXMLRLOManager(XMLRLOManager manager) {
	    this.importXMLRLOManager = manager;
  }
  /*
   * (non-Javadoc)
   * 
   * @see
   * it.eldasoft.gene.commons.web.struts.ActionBase#runAction(org.apache.struts
   * .action.ActionMapping, org.apache.struts.action.ActionForm,
   * javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse)
   */
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
	  if (logger.isDebugEnabled()) logger.debug("runAction: inizio metodo");
	    String target = "success";
	    String messageKey= null;
	    UploadFileForm formXML = (UploadFileForm) form;
	    XmlOptions options = new XmlOptions();
		Map<String,String> namespaceMap = new HashMap<String,String>();
		HashMap<String, Object> infoValidazione = null;
	    try {
	    		//verifico se il file xml proviene dal'xsd di Regione Marche
	    		namespaceMap.put("", "xmlbeans.lombardia.contratti.eldasoft.it");
	        	options = options.setLoadSubstituteNamespaces(namespaceMap);
	        	FeedBackDocument doc;
	        	doc = FeedBackDocument.Factory.parse(IOUtils.toString(formXML.getSelezioneFile().getInputStream()), options);
	        	infoValidazione = importXMLRLOManager.aggiornaCUI(doc);
	        	request.setAttribute("titolo", infoValidazione.get("titolo"));
	        	request.setAttribute("listaControlli", infoValidazione.get("listaControlli"));
	        	request.setAttribute("numeroWarning", infoValidazione.get("numeroWarning"));
	        	request.setAttribute("numeroErrori", infoValidazione.get("numeroErrori"));
	        	request.setAttribute("esito", "ok");
	    } catch (GestoreException e) {
	    	target="error";
			messageKey = e.getCodice();
			if (e.getParameters() == null) {
				logger.error(e.getMessage(), e);
				this.aggiungiMessaggio(request, messageKey);
			} else {
				logger.error(e.getMessage(), e);
				this.aggiungiMessaggio(request, messageKey,
						(String) e.getParameters()[0]);
			}
		} catch (Exception e) {
	      target="error";
	      messageKey = "errors.gestoreException.*.importXML.generic";
	      this.aggiungiMessaggio(request, messageKey, e.getMessage());
	    } 
	    
	    if (logger.isDebugEnabled()) {
	    	logger.debug("runAction: fine metodo");
	    }
	    return mapping.findForward(target);
	  }
 
}


