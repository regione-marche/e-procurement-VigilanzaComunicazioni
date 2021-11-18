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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.UploadFileForm;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.ImportExportT2Manager;

/**
 * Action per il caricamente di un file excel in una tabella nel database.
 * 
 * @author Mirco.Franzoni
 */
public class ImportFileT2Action extends ActionBaseNoOpzioni {

  static Logger      logger = Logger.getLogger(ImportFileT2Action.class);

  private ImportExportT2Manager importExportT2Manager;
  
  /**
   * @param manager 
   *        manager da settare internamente alla classe.
   */
  
  public void setImportExportT2Manager(ImportExportT2Manager manager) {
	    this.importExportT2Manager = manager;
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
  
    try {
    	UploadFileForm documentoXLS = (UploadFileForm) form;
    	HSSFWorkbook XLS = new HSSFWorkbook(documentoXLS.getSelezioneFile().getInputStream());
    	// Import dei dati dal file Excel
		this.importExportT2Manager.importazione(XLS);
		request.setAttribute("RISULTATO", "OPERAZIONEESEGUITA");
    } catch (GestoreException e) {
		target = "error";
		messageKey = "errors.gestoreException.*." + e.getCodice();
		if (e.getParameters() == null) {
			logger.error(e.getMessage(), e);
			this.aggiungiMessaggio(request, messageKey);
		} else {
			logger.error(e.getMessage(), e);
			this.aggiungiMessaggio(request, messageKey,
					(String) e.getParameters()[0]);
		}
		request.setAttribute("RISULTATO", "ERRORI");

	} catch (Throwable t) {
		target = "errorimportazione";
		messageKey = "errors.applicazione.inaspettataException";
		logger.error(this.resBundleGenerale.getString(messageKey), t);
		this.aggiungiMessaggio(request, messageKey);
		request.setAttribute("RISULTATO", "ERRORI");
	}

    
    if (logger.isDebugEnabled()) {
    	logger.debug("runAction: fine metodo");
    }
    return mapping.findForward(target);
  }
 
}


