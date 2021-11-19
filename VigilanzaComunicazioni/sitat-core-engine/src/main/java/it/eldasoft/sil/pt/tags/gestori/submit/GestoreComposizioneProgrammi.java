package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.commons.web.spring.ContextAwareDataSourceProxy;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.TransactionStatus;

/**
 * Gestore per la compilazione, attraverso jasperReport, del programma triennale per 
 * le opere pubbliche / programma annuale per forniture e servizi in formato pdf 
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreComposizioneProgrammi extends AbstractGestoreChiaveNumerica {

  @Override
  public String getEntita() {
    return "PIATRI";
  }

  @Override
  public String[] getAltriCampiChiave() {
    return null;
  }

  @Override
  public String getCampoNumericoChiave() {
    return "CONTRI";
  }
  
  public GestoreComposizioneProgrammi(){
    super(false);
  }
  
  @Override
  public void postDelete(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void postInsert(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void postUpdate(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
    
    ContextAwareDataSourceProxy contextAwareDataSourceProxy =
      (ContextAwareDataSourceProxy) UtilitySpring.getBean("dataSource", 
           this.getServletContext(), it.eldasoft.gene.commons.web.spring.ContextAwareDataSourceProxy.class);

    Connection jrConnection = null;
    try {
      
      String PROP_JRREPORT_SOURCE_DIR = "/WEB-INF/jrReport/Programmi/";
      String jrReportSourceDir = null;
      // Input stream del file sorgente
      InputStream inputStreamJrReport = null;
      
      String tiprog = UtilityStruts.getParametroString(this.getRequest(), "tiprog");
      String contri = UtilityStruts.getParametroString(this.getRequest(), "contri");
      String piatriID = UtilityStruts.getParametroString(this.getRequest(), "piatriID");
      
      if("1".equals(tiprog) || "3".equals(tiprog)){
    	  double accantonamento = 0;
    	  if (UtilityStruts.getParametroString(this.getRequest(), "accantonamento") != null) {
    		  accantonamento = new Double(UtilityStruts.getParametroString(this.getRequest(), "accantonamento"));
    	  }
    	  this.sqlManager.update("UPDATE PIATRI SET IMPACC = ? WHERE CONTRI = ?",
    	          new Object[]{accantonamento, new Long(contri)});

        jrReportSourceDir = this.getRequest().getSession().getServletContext().getRealPath(
            PROP_JRREPORT_SOURCE_DIR) + "/";
        inputStreamJrReport = this.getRequest().getSession().getServletContext().getResourceAsStream(
            PROP_JRREPORT_SOURCE_DIR + "report2.jasper");
      } else {
        jrReportSourceDir = this.getRequest().getSession().getServletContext().getRealPath(
            PROP_JRREPORT_SOURCE_DIR ) + "/";
        inputStreamJrReport = this.getRequest().getSession().getServletContext().getResourceAsStream(
            PROP_JRREPORT_SOURCE_DIR + "reportFornitureServizi.jasper");
      }

      // Parametri
      HashMap<String, Object> jrParameters = new HashMap<String, Object>();
      jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
      jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);
      jrParameters.put("COD_PIANOTRIEN", piatriID);
      jrParameters.put("contri", new Long(contri));
            
      // Connessione
      jrConnection = contextAwareDataSourceProxy.getConnection();
      
      // Stampa del formulario
      JasperPrint jrPrint =  JasperFillManager.fillReport(inputStreamJrReport,
          jrParameters, jrConnection);

      // Output stream del risultato
      ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();
      JRExporter jrExporter = new JRPdfExporter();
      jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
      jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baosJrReport);
      jrExporter.exportReport();

      inputStreamJrReport.close();
      baosJrReport.close();

      // Update di PIATRI per l'inserimento del file composto
      LobHandler lobHandler = new DefaultLobHandler(); // reusable object
      this.sqlManager.update("UPDATE PIATRI SET FILE_ALLEGATO = ? WHERE CONTRI = ? and ID = ?",
          new Object[]{new SqlLobValue(baosJrReport.toByteArray(), lobHandler), new Long(contri), piatriID});

      // Ripristino dei parametri letti dal request per la riapertura della popup
      // a prescindere dell'esito dell'operazione
      this.getRequest().setAttribute("tiprog ", tiprog);
      this.getRequest().setAttribute("contri ", contri);
      this.getRequest().setAttribute("piatriID ", piatriID);

      // Esito positivo dell'intera operazione: generazione del pdf e update del campo PIATRI.FILE_ALLEGATO
      this.getRequest().setAttribute("RISULTATO", "OK");
      
    } catch (SQLException sqlEx) {
      this.getRequest().setAttribute("RISULTATO", "KO");
      throw new GestoreException("Errore durante l'aggiornamento del campo PIATRI.FILE_ALLEGATO", null, sqlEx);
    } catch (JRException jrEx) {
      this.getRequest().setAttribute("RISULTATO", "KO");
      throw new GestoreException("Errore durante la generazione del pdf con jasper report", null, jrEx);
    } catch (IOException ioEx) {
      this.getRequest().setAttribute("RISULTATO", "KO");
      throw new GestoreException("Errore per una IOException sull'oggetto inputStreamJrReport", null, ioEx);
    } finally {
    	try {
    		if (jrConnection != null) {
    			jrConnection.close();
    		}
    	} catch (SQLException ex) {
    		;
    	}
    }
    

  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
  }

}
