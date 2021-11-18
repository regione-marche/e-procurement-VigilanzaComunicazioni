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
package it.eldasoft.sil.pt.web.struts;

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

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.UploadFileForm;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.ImportExportXMLManager;
import it.eldasoft.sil.pt.bl.PtManager;

/**
 * Action per il caricamente di un programma triennale dall'export del programma ministeriale HiProg3 in formato XML.
 * 
 * @author Mirco.Franzoni
 */
public class ImportHiProg3Action extends ActionBaseNoOpzioni {

  static Logger      logger = Logger.getLogger(ImportHiProg3Action.class);

  /** ptManager manager della classe. */
  private PtManager ptManager;
  
  private ImportExportXMLManager importExportXMLManager;
  
  /**
   * @param manager 
   *        manager da settare internamente alla classe.
   */
  public void setPtManager(PtManager manager) {
	    this.ptManager = manager;
  }
  
  public void setImportExportXMLManager(ImportExportXMLManager manager) {
	    this.importExportXMLManager = manager;
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
    String messageKey = null;
    String target = "success";
  
    UploadFileForm formXML = (UploadFileForm) form;
    XmlOptions options = new XmlOptions();
	Map<String,String> namespaceMap = new HashMap<String,String>();
    try {
    	//String contri = request.getParameter("contri");
    	try {
    		it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument doc;
    		//verifico se il file xml proviene dall'HiProg3  
        	namespaceMap.put("", "xmlbeans.hiprog.web.pt.sil.eldasoft.it");
        	//namespaceMap.put("xmlns", "");
        	options = options.setLoadSubstituteNamespaces(namespaceMap);
        	String fileXML = IOUtils.toString(formXML.getSelezioneFile().getInputStream());
    		doc = it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Factory.parse(fileXML, options);
    		it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt pt = doc.getPt();
    		it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt.SetupInfo setupInfo = pt.getSetupInfo();
        	String ver = setupInfo.getHiprog3Version();
        	if (ver.startsWith("2.1.")){
        		messageKey = "warnings.importXML.compatibility";
        		this.aggiungiMessaggio(request, messageKey);
        		//this.ptManager.importHiProg3(doc, request.getSession().getAttribute("uffint").toString()); 
        		this.ptManager.importHiProg3(doc, request); 
        	} else {
        		throw new GestoreException(
    					"Il documento XML non è compatibile con la versione HiProg3",
    					"importHiProg3.version");
        	}
    	} catch (GestoreException e) { 
    		throw new GestoreException(
					"Il documento XML non è compatibile con la versione HiProg3",
					e.getCodice());
    	} catch (Exception ex) {
    		//verifico se il file xml proviene dall'AliProg4
    		namespaceMap.put("", "xmlbeans.aliprog4.web.pt.sil.eldasoft.it");
        	options = options.setLoadSubstituteNamespaces(namespaceMap);
    		it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument doc;
    		
    		doc = it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Factory.parse(IOUtils.toString(formXML.getSelezioneFile().getInputStream()),options);
        	String ver = doc.getPt().getSetupInfo().getHiprog3Version();
        	if (ver.startsWith("3.0.")){
        		//verifico hash code
	    		String hashCode1 = doc.getPt().getFirmaHash();
	    		doc.getPt().setFirmaHash("");
	    		String hashCode2 = "" + (doc.toString() + "AliProg4 by Eldasoft S.p.a").hashCode();
	    		if (hashCode1 == null || !hashCode1.equals(hashCode2)) {
	    			messageKey = "warnings.importXML.compatibility";
	    			this.aggiungiMessaggio(request, messageKey);
	    		}
        		this.importExportXMLManager.importAliProg4(doc, request.getSession().getAttribute("uffint").toString()); 
        		//this.ptManager.importHiProg3(doc); 
        	} else {
        		throw new GestoreException(
    					"Il documento XML non è compatibile con la versione AliProg4",
    					"importAliProg4.version");
        	}
    	}
    	
    	messageKey = "info.importXML.success";
    	this.aggiungiMessaggio(request, messageKey);
    } catch (GestoreException e) {
    	target="error";
		messageKey = "errors.gestoreException.*." + e.getCodice();
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


