/**
 * 
 */
package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.ExportSoggettiAggregatoriManager;
import it.eldasoft.utils.utility.UtilityWeb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author luca.giacomazzo
 *
 */
public class GetReportAcquistiSoggettoAggregatoreAction  extends ActionBaseNoOpzioni {

  Logger logger = Logger.getLogger(GetReportAcquistiSoggettoAggregatoreAction.class);
  
  private ExportSoggettiAggregatoriManager exportSoggettiAggregatoriManager;
  
  
  public void setExportSoggettiAggregatoriManager(ExportSoggettiAggregatoriManager exportSoggettiAggregatoriManager) {
    this.exportSoggettiAggregatoriManager = exportSoggettiAggregatoriManager;
  }
  
  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
  
    if (logger.isDebugEnabled()) {
      logger.debug("runAction: inizio metodo");
    }
    
    String target = null;// CostantiGeneraliStruts.FORWARD_OK;
    
    Long chiavePianoTriennale = new Long(request.getParameter("codiceProgramma").split(":")[1]);

    try {
      byte[] byteArrayReport = this.exportSoggettiAggregatoriManager.getReportSoggettiAggregatori(
          chiavePianoTriennale.longValue());
      UtilityWeb.download("reportSoggettiAggregatori.xls", byteArrayReport, response);
    } catch (GestoreException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      logger.error("Errore nell'estrazioe dei dati per i soggetti aggregatori del programma con PIATRI.CONTRI=" + chiavePianoTriennale, e);
      this.aggiungiMessaggio(request, "errors.applicazione.inaspettataException");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("runAction: fine metodo");
    }
    
    if (target != null)
      return mapping.findForward(target);
    else
      return null;
  }
}
