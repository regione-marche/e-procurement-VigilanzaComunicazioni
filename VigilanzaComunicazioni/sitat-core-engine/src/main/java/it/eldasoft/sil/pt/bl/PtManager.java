package it.eldasoft.sil.pt.bl;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.spring.ContextAwareDataSourceProxy;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.BlobFile;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.db.dao.PtFileDao;
import it.eldasoft.sil.pt.tags.gestori.submit.GestorePIATRI;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt.InterventiTriennali;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt.InterventiTriennali.Intervento;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt.InterventiTriennali.Intervento.BeniImmobili;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt.InterventiTriennali.Intervento.BeniImmobili.Bene;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt.InterventiTriennali.Intervento.InterventoAnnuale;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt.Note;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt.QuadroRisorse;
import it.eldasoft.sil.pt.web.hiprog.xmlbeans.PtDocument.Pt.SetupInfo;
import it.eldasoft.utils.metadata.cache.DizionarioCampi;
import it.eldasoft.utils.metadata.domain.Campo;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityFiscali;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.utils.utility.UtilityWeb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Mirco.Franzoni - Eldasoft S.p.A. Treviso
 *
 */
public class PtManager {

	/** Logger Log4J di classe. */
  private static Logger      logger = Logger.getLogger(PtManager.class);

  /** Manager per le transazioni e selezioni nel database. */
  private SqlManager sqlManager;

  private GeneManager geneManager;

  /** DAO per la gestione dei file allegati. */
  private PtFileDao  ptFileDao;

  /**
   * query per il recupero dei dati per i piani trinennali.
   * */
  private final String sqlSelectPiatri = "select ANNTRI,DV1TRI,DV2TRI,DV3TRI,MU1TRI,MU2TRI,MU3TRI,PR1TRI,PR2TRI,PR3TRI,IM1TRI,IM2TRI,IM3TRI,BI1TRI,BI2TRI,BI3TRI,AL1TRI,AL2TRI,AL3TRI,TO1TRI,TO2TRI,TO3TRI,CENINT from piatri where contri = ? ";
  /** sqlSelectIDPiatri. */
  private final String sqlSelectIDPiatri = "select ID from piatri where contri = ? ";
  /** sqlSelectInttri. */
  private final String sqlSelectInttri = "select * from inttri where contri = ? order by annrif, nprogint";
  /** sqlSelectImmtrai. */
  public static final String sqlSelectImmtrai = "select * from immtrai where contri = ? and conint = ?";
  /** sqlSelectCfisStazioneAppaltante. */
  public static final String sqlSelectCfisStazioneAppaltante = "select cfein from uffint where codein = ? ";
  /** sqlSelectCISTATStazioneAppaltante. */
  public static final String sqlSelectCISTATStazioneAppaltante = "select codcit from uffint where codein = ? ";
  /** sqlSelectIdAmminStazioneAppaltante. */
  public static final String sqlSelectIdAmminStazioneAppaltante = "select idammin from uffint where codein = ? ";
  /** sqlSelectTecni. */
  public static final String sqlSelectTecni = "select CODTEC,COGTEI,NOMETEI,CFTEC from tecni where codtec = ? ";
  /** sqlSelectStatoProgettazione. */
  public static final String sqlSelectStatoProgettazione = "select TABCOD3 from tabsche where tabcod = 'S2017' and tabcod1 = ? ";
  /** sqlSelectFinalitaIntervento. */
  public static final String sqlSelectFinalitaIntervento = "select TABCOD3 from tabsche where tabcod = 'S2016' and tabcod1 = ? ";
  /** sqlSelectCategoriaIntervento. */
  public static final String sqlSelectCategoriaIntervento = "select TABCOD3 from tabsche where tabcod = 'S2006' and tabcod2 = ? ";
  /** query per la cancellazione in AliProgMIT della pubblicazione del programma */
  public static final String SQL_DELETE_PIATRI = "Delete from scppubb.piatri where contri = ?";
  public static final String SQL_DELETE_INTTRI = "Delete from scppubb.inttri where contri = ?";
  public static final String SQL_DELETE_IMMTRAI = "Delete from scppubb.immtrai where contri = ?";
  public static final String SQL_DELETE_ECOTRI = "Delete from scppubb.ecotri where contri = ?";
  public static final String SQL_DELETE_PTFLUSSI = "Delete from scppubb.ptflussi where key01 = ?";

  /**
   * @param sqlManagerParam
   *        sqlManager da settare internamente alla classe.
   */
	public void setSqlManager(SqlManager sqlManagerParam) {
    this.sqlManager = sqlManagerParam;
  }
   public void setGeneManager(GeneManager geneManager) {
		this.geneManager = geneManager;
	}
  /**
   * @param fileDao
   *        ptFileDao da settare internamente alla classe.
   */
  public void setPtFileDao(PtFileDao fileDao) {
    this.ptFileDao = fileDao;
  }
  /**
   *
   * @param entita entita
   * @param params params
   * @return file allegato
   * @throws IOException IOException
   */
  public BlobFile getFileAllegato(String entita, HashMap<String, Object> params)
	      throws IOException {
	    if (logger.isDebugEnabled())
	      logger.debug("getFileAllegato: inizio metodo");
	    BlobFile w4Blob = this.ptFileDao.getFileAllegato(entita, params);
	    if (logger.isDebugEnabled())
	      logger.debug("getFileAllegato: fine metodo");
	    return w4Blob;
	  }

  /**
   *
   * @param entita entita
   * @param params params
   * @param response response
   * @throws IOException IOException
   * @throws GestoreException GestoreException
   */
  public void downloadFileAllegato(String entita,
	      HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response)
	      throws IOException, GestoreException {
	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm");
	    String strDateTime = sdf.format(new Date());
	    if (logger.isDebugEnabled()) {
	      logger.debug("downloadFileAllegato" + entita + ": inizio metodo");
	    }

	    if (entita.equals("PTFLUSSI")) {
	    	String codflusso = params.get("idFlusso").toString();
	    	String tipo = params.get("tipo").toString();

	        if (tipo.equals("pdf")) {
	        	BlobFile w4Blob = this.ptFileDao.getFileAllegato(entita, params);
	        	UtilityWeb.download("allegato_" + strDateTime + ".pdf", w4Blob.getStream(), response);
	        } else {
	        	String clob = this.getClobXml(entita, codflusso);
	        	UtilityWeb.download("flusso_" + codflusso + "_" + strDateTime + ".xml", clob.getBytes(), response);
	        }
	    } else {
	    	creaPdf(params, request);
		    BlobFile w4Blob = this.ptFileDao.getFileAllegato(entita, params);
		    UtilityWeb.download("allegato_" + strDateTime + ".pdf", w4Blob.getStream(), response);
	    }

	    if (logger.isDebugEnabled()) {
	      logger.debug("downloadFileAllegato" + entita + ": fine metodo");
	    }
	  }

  /**
   *
   * @param entita entita
   * @param params params
   * @param response response
   * @throws IOException IOException
   * @throws GestoreException GestoreException
   */
  public void downloadFileAllegatoNuovaNormativa(String entita,
	      HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response)
	      throws IOException, GestoreException {
	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm");
	    String strDateTime = sdf.format(new Date());
	    if (logger.isDebugEnabled()) {
	      logger.debug("downloadFileAllegato" + entita + ": inizio metodo");
	    }

	    if (entita.equals("PTFLUSSI")) {
	    	String codflusso = params.get("idFlusso").toString();
	    	String tipo = params.get("tipo").toString();

	        if (tipo.equals("pdf")) {
	        	BlobFile w4Blob = this.ptFileDao.getFileAllegato(entita, params);
	        	UtilityWeb.download("allegato_" + strDateTime + ".pdf", w4Blob.getStream(), response);
	        } else {
	        	String clob = this.getClobXml(entita, codflusso);
	        	UtilityWeb.download("flusso_" + codflusso + "_" + strDateTime + ".xml", clob.getBytes(), response);
	        }
	    } else {
	    	creaPdfNuovaNormativa(params, request);
		    BlobFile w4Blob = this.ptFileDao.getFileAllegato(entita, params);
		    UtilityWeb.download("allegato_" + strDateTime + ".pdf", w4Blob.getStream(), response);
	    }

	    if (logger.isDebugEnabled()) {
	      logger.debug("downloadFileAllegato" + entita + ": fine metodo");
	    }
	  }

  /** Procedura che crea il file PDF dei programmi triennali e lo memorizza nel DB
   * @param params params
   * @param request request
   * @throws GestoreException GestoreException
   */
  public void creaPdf(HashMap<String, Object> params, HttpServletRequest request) throws GestoreException{

	  ContextAwareDataSourceProxy contextAwareDataSourceProxy =
	      (ContextAwareDataSourceProxy) UtilitySpring.getBean("dataSource",
	    		  request.getSession().getServletContext(), it.eldasoft.gene.commons.web.spring.ContextAwareDataSourceProxy.class);
	  TransactionStatus status = null;
	  boolean commitTransaction = false;
	  Connection jrConnection = null;
	  try {
	    String PROP_JRREPORT_SOURCE_DIR = "/WEB-INF/jrReport/Programmi/";
      String jrReportSourceDir = null;
      // Input stream del file sorgente
      InputStream inputStreamJrReport = null;

      Long tiprog = (Long)params.get("tiprog");
      Long contri = (Long)params.get("contri");
      String piatriID = params.get("piatriID").toString();

      if(tiprog == 1 || tiprog == 3){
        jrReportSourceDir = request.getSession().getServletContext().getRealPath(
            PROP_JRREPORT_SOURCE_DIR) + "/";
        inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
            PROP_JRREPORT_SOURCE_DIR + "report2.jasper");
      } else {
        jrReportSourceDir = request.getSession().getServletContext().getRealPath(
            PROP_JRREPORT_SOURCE_DIR ) + "/";
        inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
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
      status = this.sqlManager.startTransaction();
      LobHandler lobHandler = new DefaultLobHandler(); // reusable object
      this.sqlManager.update("UPDATE PIATRI SET FILE_ALLEGATO = ? WHERE CONTRI = ? and ID = ?",
          new Object[]{new SqlLobValue(baosJrReport.toByteArray(), lobHandler), new Long(contri), piatriID});
      commitTransaction = true;

	  } catch (Exception e) {
	    throw new GestoreException(
	        "Errore durante il recupero del file XML allegato.", "errors.generico", e);
	  } finally {
      try {
      	if (jrConnection != null) {
      		jrConnection.close();
      	}

        if (status != null) {
      	  if (commitTransaction) {
      		  this.sqlManager.commitTransaction(status);
      	  } else {
      		  this.sqlManager.rollbackTransaction(status);
      	  }
        }
      } catch (SQLException e) {
        logger.error("Errore durante la chiusura della transazione", e);
      }
    }
  }

  /**
   * Procedura che crea il file PDF dei programmi triennali secondo
   * l'ex DLgs. 50/2016 e lo memorizza nel DB
   *
   * @param params params
   * @param request request
   * @throws GestoreException GestoreException
   */
  public void creaPdfNuovaNormativa(HashMap<String, Object> params, HttpServletRequest request) throws GestoreException{

	  ContextAwareDataSourceProxy contextAwareDataSourceProxy =
	      (ContextAwareDataSourceProxy) UtilitySpring.getBean("dataSource",
	    		  request.getSession().getServletContext(), it.eldasoft.gene.commons.web.spring.ContextAwareDataSourceProxy.class);
	  TransactionStatus status = null;
	  boolean commitTransaction = false;
	  Connection jrConnection = null;
    try {
      String PROP_JRREPORT_SOURCE_DIR = "/WEB-INF/jrReport/NuovaNormativa/";
      String jrReportSourceDir = null;
      // Input stream del file sorgente
      InputStream inputStreamJrReport = null;

      Long tiprog = (Long)params.get("tiprog");
      Long contri = (Long)params.get("contri");
      String piatriID = params.get("piatriID").toString();

      if(tiprog == 1 || tiprog == 3){
        jrReportSourceDir = request.getSession().getServletContext().getRealPath(
            PROP_JRREPORT_SOURCE_DIR) + "/";
        inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
            PROP_JRREPORT_SOURCE_DIR + "allegatoI.jasper");
      } else {
        jrReportSourceDir = request.getSession().getServletContext().getRealPath(
            PROP_JRREPORT_SOURCE_DIR ) + "/";
        inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
            PROP_JRREPORT_SOURCE_DIR + "allegatoII.jasper");
      }

      // Parametri
      HashMap<String, Object> jrParameters = new HashMap<String, Object>();
      jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
      jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);
      jrParameters.put("COD_PIANOTRIEN", piatriID);
      //jrParameters.put("contri", new Long(contri));

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
      status = this.sqlManager.startTransaction();
      LobHandler lobHandler = new DefaultLobHandler(); // reusable object
      this.sqlManager.update("UPDATE PIATRI SET FILE_ALLEGATO = ? WHERE CONTRI = ? and ID = ?",
          new Object[]{new SqlLobValue(baosJrReport.toByteArray(), lobHandler), new Long(contri), piatriID});
      commitTransaction = true;

	  } catch (Exception e) {
	    throw new GestoreException(
	        "Errore durante il recupero del file XML allegato.", "errors.generico", e);
	  } finally {
	    try {
	      if (jrConnection != null) {
	        jrConnection.close();
	      }

        if (status != null) {
      	  if (commitTransaction) {
      		  this.sqlManager.commitTransaction(status);
      	  } else {
      		  this.sqlManager.rollbackTransaction(status);
      	  }
        }
      } catch (SQLException e) {
        logger.error("Errore durante la chiusura della transazione", e);
      }
    }
  }

  /**
   *
   * @param entita
   * @param idFlusso
   * @return
   * @throws GestoreException
   */
  private String getClobXml(String entita, String idFlusso) throws GestoreException {

	    String sqlSelectLotti = "select XML from PTFLUSSI where IDFLUSSO = " + idFlusso;
	    String xml = "";
	    try {
	      xml = (String) this.sqlManager.getObject(sqlSelectLotti, null);
	    } catch (Exception e) {
	      throw new GestoreException(
	          "Errore durante il recupero del file XML allegato.", "errors.generico", e);
	    }
	    return xml;
	  }

  /**
   * @param idFlusso id flusso
   * @throws GestoreException  GestoreException
   */
  public void deleteFlussoInvalido(Long idFlusso)
  throws GestoreException {
	  String deleteFlusso = "DELETE FROM PTFLUSSI WHERE IDFLUSSO =?";

	  try {
		  TransactionStatus status = this.sqlManager.startTransaction();
		  this.sqlManager.update(deleteFlusso, new Object[] { idFlusso });
		  this.sqlManager.commitTransaction(status);
	  } catch (SQLException e) {
		  throw new GestoreException(
				  "Errore nell\'esecuzione della query per fa cancellazione del flusso",
				  "", e);
	  }
  }

  /**
   *
   * @param idFlusso codice del flusso
   * @param xml stringa xml da salvare
   * @throws GestoreException GestoreException
   */
  public void updateClobXml(Long idFlusso, String xml) throws GestoreException {
	    String updateValoreXml = "UPDATE PTFLUSSI SET XML =? WHERE IDFLUSSO =?";

	    try {
	      TransactionStatus status = this.sqlManager.startTransaction();
	      this.sqlManager.update(updateValoreXml, new Object[] { xml, idFlusso });
	      this.sqlManager.commitTransaction(status);
	    } catch (SQLException e) {
	      throw new GestoreException(
	          "Errore nel\'esecuzione della query per l'aggiornamento del file xml nel flusso " + idFlusso,
	          "", e);
	    }
	  }

  /**
   * @param idFlusso codice del flusso
   * @param contri codice del programma
   * @throws GestoreException GestoreException
   */
  public void updateFileAllegatoFlussi(Long idFlusso, Long contri) throws GestoreException {
	String updateSql = "UPDATE PTFLUSSI SET FILE_ALLEGATO = (SELECT FILE_ALLEGATO FROM PIATRI WHERE CONTRI = ?) WHERE IDFLUSSO =?";
	try {
	  TransactionStatus status = this.sqlManager.startTransaction();
	  this.sqlManager.update(updateSql, new Object[] { contri, idFlusso });
	  this.sqlManager.commitTransaction(status);
	} catch (SQLException e) {
	  throw new GestoreException(
	      "Errore nel\'esecuzione della query per l'aggiornamento del file pdf nel flusso " + idFlusso,
	       "", e);
	}
  }

  /**
   *
   * @param contri codice programma
   * @param statri nuovo stato da impostare
   * @param verifyValue se è null lo stato viene impostato a statri altrimenti prima di modificarlo verifica se il valore è quello indicato in verifyValue
   * @throws GestoreException GestoreException
   */
  public void updateStatoProgramma(Long contri, Long statri, Long verifyValue1, Long verifyValue2) throws GestoreException {
	    String updateStato = "update piatri set statri = ? where contri =?";
	    String getStato = "select statri from piatri where contri =?";
	    boolean update = true;
	    try {
	      TransactionStatus status = this.sqlManager.startTransaction();
	      if (verifyValue1 != null) {
	    	  update = false;
	    	  Long currentStatri = (Long) this.sqlManager.getObject(getStato, new Object[] { contri });
	    	  if (verifyValue1.equals(currentStatri)) {
	    		  update = true;
	    	  } else if (verifyValue2 != null && verifyValue2.equals(currentStatri)){
	    		  update = true;
	    	  }
	      }
	      if (update) {
		      this.sqlManager.update(updateStato, new Object[] { statri , contri });
	      }
	      this.sqlManager.commitTransaction(status);
	    } catch (SQLException e) {
	      throw new GestoreException(
	          "Errore nel\'esecuzione della query per l\'aggiornamento dello stato del programma",
	          "", e);
	    }
	  }

  /**
   *
   * @param campo campo
   * @param contri codice programma
   * @param tiprog tipo programma
   * @return importo
   * @throws GestoreException GestoreException
   */
  private Double sommaValoriIntervento(final String campo, final Long contri, final Long tiprog) throws GestoreException {
    String sqlSelectSomma = "select SUM(" + this.sqlManager.getDBFunction("isnull", new String[] {campo, "0.00" })
        + ") from INTTRI where CONTRI=? and (ACQALTINT is null or ACQALTINT =1)";
    if (tiprog == 3) {
    	sqlSelectSomma += " AND TIPINT=1";
    }

    List< ? > listaValoreSomma;
    Double somma;
    try {
      listaValoreSomma = this.sqlManager.getListVector(sqlSelectSomma, new Object[] { contri });
      somma = SqlManager.getValueFromVectorParam(listaValoreSomma.get(0), 0).doubleValue();
    } catch (Exception e) {
      somma = new Double(0);
      throw new GestoreException(
          "Errore durante il calcolo dei costi complessivi del Programma",
          "calcolo.sommaValori", e);
    }
    return somma;
  }

 /**
  *
  * @param campo campo
  * @param contri codice programma
  * @param conint progressivo dell'intervento
  * @return importo
  * @throws GestoreException GestoreException
  */
 private Double sommaValoriCapitolo(final String campo, final Long contri, final Long conint) throws GestoreException {
   String sqlSelectSomma = "select SUM(" + this.sqlManager.getDBFunction("isnull", new String[] {campo, "0.00" })
       + ") from RIS_CAPITOLO where CONTRI =? and CONINT=? ";

   List< ? > listaValoreSomma;
   Double somma;
   try {
     listaValoreSomma = this.sqlManager.getListVector(sqlSelectSomma, new Object[] { contri, conint });
     somma = SqlManager.getValueFromVectorParam(listaValoreSomma.get(0), 0).doubleValue();
   } catch (Exception e) {
     somma = new Double(0);
     throw new GestoreException(
         "Errore durante il calcolo dei costi complessivi dell'intervento",
         "calcolo.sommaValoriIntervento", e);
   }
   return somma;
 }

  /**
   * Aggiornamento del quadro economico del piano triennale.
   *
   * @param contri codice programma
   * @throws GestoreException GestoreException
   */
  public void aggiornaCostiPiatri(Long contri) throws GestoreException {

    String updateCostiPiatri = "UPDATE PIATRI SET "
    	+ "DV1TRI =?, DV2TRI =?, DV3TRI =?, "
        + "IM1TRI =?, IM2TRI =?, IM3TRI =?, "
        + "MU1TRI =?, MU2TRI =?, MU3TRI =?, "
        + "PR1TRI =?, PR2TRI =?, PR3TRI =?, "
        + "AL1TRI =?, AL2TRI =?, AL3TRI =?, "
        + "AP1TRI =?, AP2TRI =?, AP3TRI =?, "
        + "BI1TRI =?, BI2TRI =?, BI3TRI =?, "
        + "TO1TRI =?, TO2TRI =?, TO3TRI =?, "
        + "RG1TRI =?, IMPRFS =? WHERE CONTRI =? ";
    boolean commitTransaction = false;
    TransactionStatus status = null;
    try {

      status = this.sqlManager.startTransaction();
      Long tipoProgramma = (Long) this.sqlManager.getObject("select TIPROG from PIATRI where contri = ?", new Object[] { contri });
      this.sqlManager.update(updateCostiPiatri, new Object[] {
		  sommaValoriIntervento("DV1TRI", contri, tipoProgramma), sommaValoriIntervento("DV2TRI", contri, tipoProgramma), sommaValoriIntervento("DV3TRI", contri, tipoProgramma),
		  sommaValoriIntervento("IM1TRI", contri, tipoProgramma), sommaValoriIntervento("IM2TRI", contri, tipoProgramma), sommaValoriIntervento("IM3TRI", contri, tipoProgramma),
          sommaValoriIntervento("MU1TRI", contri, tipoProgramma), sommaValoriIntervento("MU2TRI", contri, tipoProgramma), sommaValoriIntervento("MU3TRI", contri, tipoProgramma),
          sommaValoriIntervento("PR1TRI", contri, tipoProgramma), sommaValoriIntervento("PR2TRI", contri, tipoProgramma), sommaValoriIntervento("PR3TRI", contri, tipoProgramma),
          sommaValoriIntervento("AL1TRI", contri, tipoProgramma), sommaValoriIntervento("AL2TRI", contri, tipoProgramma), sommaValoriIntervento("AL3TRI", contri, tipoProgramma),
          sommaValoriIntervento("AP1TRI", contri, tipoProgramma), sommaValoriIntervento("AP2TRI", contri, tipoProgramma), sommaValoriIntervento("AP3TRI", contri, tipoProgramma),
		  sommaValoriIntervento("BI1TRI", contri, tipoProgramma), sommaValoriIntervento("BI2TRI", contri, tipoProgramma), sommaValoriIntervento("BI3TRI", contri, tipoProgramma),
          sommaValoriIntervento("DI1INT", contri, tipoProgramma), sommaValoriIntervento("DI2INT", contri, tipoProgramma), sommaValoriIntervento("DI3INT", contri, tipoProgramma),
          sommaValoriIntervento("RG1TRI", contri, tipoProgramma), sommaValoriIntervento("IMPRFS", contri, tipoProgramma), contri });

      commitTransaction = true;
      this.sqlManager.commitTransaction(status);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nell\'esecuzione della query per l'aggiornamento dei costi complessivi del Programma",
          "aggiornamento.ricalcoloCostiProgramma", e);
    } finally {
      if (status != null) {
        try {
          if (commitTransaction) {
        	this.sqlManager.commitTransaction(status);
          } else {
          	this.sqlManager.rollbackTransaction(status);
          }
        } catch (SQLException e) {
          logger.error("Errore durante la chiusura della transazione", e);
        }
      }
    }
  }

  /**
   * Aggiornamento del quadro economico dell'intervento.
   *
   * @param contri codice programma
   * @throws GestoreException GestoreException
   */
  public void aggiornaCostiInttri(Long contri, Long conint) throws GestoreException {

    String updateCostiInttri = "UPDATE INTTRI SET "
		+ "AL1TRI =?, AL2TRI =?, AL3TRI =?, AL9TRI=?, "
        + "AP1TRI =?, AP2TRI =?, AP3TRI =?, AP9TRI=?, "
        + "BI1TRI =?, BI2TRI =?, BI3TRI =?, BI9TRI=?, "
    	+ "DV1TRI =?, DV2TRI =?, DV3TRI =?, DV9TRI=?, "
        + "MU1TRI =?, MU2TRI =?, MU3TRI =?, MU9TRI=?, "
        + "IV1TRI =?, IV2TRI =?, IV3TRI =?, IV9TRI=?, "
        + "IMPALT =?, IMPRFS =?, RG1TRI=?, SPESESOST=? "
        + "WHERE CONTRI =? AND CONINT=?";
    boolean commitTransaction = false;
    TransactionStatus status = null;
    try {
      status = this.sqlManager.startTransaction();
      this.sqlManager.update(updateCostiInttri, new Object[] {
          sommaValoriCapitolo("AL1CB", contri, conint), sommaValoriCapitolo("AL2CB", contri, conint), sommaValoriCapitolo("AL3CB", contri, conint), sommaValoriCapitolo("AL9CB", contri, conint),
          sommaValoriCapitolo("AP1CB", contri, conint), sommaValoriCapitolo("AP2CB", contri, conint), sommaValoriCapitolo("AP3CB", contri, conint), sommaValoriCapitolo("AP9CB", contri, conint),
		  sommaValoriCapitolo("BI1CB", contri, conint), sommaValoriCapitolo("BI2CB", contri, conint), sommaValoriCapitolo("BI3CB", contri, conint), sommaValoriCapitolo("BI9CB", contri, conint),
		  sommaValoriCapitolo("DV1CB", contri, conint), sommaValoriCapitolo("DV2CB", contri, conint), sommaValoriCapitolo("DV3CB", contri, conint), sommaValoriCapitolo("DV9CB", contri, conint),
          sommaValoriCapitolo("MU1CB", contri, conint), sommaValoriCapitolo("MU2CB", contri, conint), sommaValoriCapitolo("MU3CB", contri, conint), sommaValoriCapitolo("MU9CB", contri, conint),
          sommaValoriCapitolo("IV1CB", contri, conint), sommaValoriCapitolo("IV2CB", contri, conint), sommaValoriCapitolo("IV3CB", contri, conint), sommaValoriCapitolo("IV9CB", contri, conint),
          sommaValoriCapitolo("IMPALTCB", contri, conint), sommaValoriCapitolo("IMPRFSCB", contri, conint),
          sommaValoriCapitolo("RG1CB", contri, conint), sommaValoriCapitolo("SPESESOST", contri, conint), contri, conint });

      this.sqlManager.update("UPDATE INTTRI SET "
    	+ "DI1INT = COALESCE(BI1TRI,0) + COALESCE(DV1TRI,0) + COALESCE(IM1TRI,0) + COALESCE(MU1TRI,0) + COALESCE(AL1TRI,0) + COALESCE(AP1TRI,0), "
    	+ "DI2INT = COALESCE(BI2TRI,0) + COALESCE(DV2TRI,0) + COALESCE(IM2TRI,0) + COALESCE(MU2TRI,0) + COALESCE(AL2TRI,0) + COALESCE(AP2TRI,0), "
    	+ "DI3INT = COALESCE(BI3TRI,0) + COALESCE(DV3TRI,0) + COALESCE(IM3TRI,0) + COALESCE(MU3TRI,0) + COALESCE(AL3TRI,0) + COALESCE(AP3TRI,0), "
    	+ "DI9INT = COALESCE(BI9TRI,0) + COALESCE(DV9TRI,0) + COALESCE(IM9TRI,0) + COALESCE(MU9TRI,0) + COALESCE(AL9TRI,0) + COALESCE(AP9TRI,0) "
    	+ " where CONTRI=? and CONINT=? ", new Object[] { contri, conint });

      this.sqlManager.update("UPDATE INTTRI SET "
    	+ " DITINT = COALESCE(DI1INT,0) + COALESCE(DI2INT,0) + COALESCE(DI3INT,0) + COALESCE(DI9INT,0), "
    	+ " ICPINT = COALESCE(PR1TRI,0) + COALESCE(PR2TRI,0) + COALESCE(PR3TRI,0) + COALESCE(PR9TRI,0), "
    	+ " TOTINT = COALESCE(DI1INT,0) + COALESCE(DI2INT,0) + COALESCE(DI3INT,0) + COALESCE(DI9INT,0) + " +
    			"COALESCE(PR1TRI,0) + COALESCE(PR2TRI,0) + COALESCE(PR3TRI,0) + " +
    			"COALESCE(PR9TRI,0) + COALESCE(SPESESOST,0) "
    	+ " where CONTRI=? and CONINT=? ", new Object[] { contri, conint });

      commitTransaction = true;
      this.sqlManager.commitTransaction(status);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nell\'esecuzione della query per l'aggiornamento dei costi complessivi dell'intervento",
          "aggiornamento.ricalcoloCostiIntervento", e);
    } finally {
      if (status != null) {
        try {
          if (commitTransaction) {
        	this.sqlManager.commitTransaction(status);
          } else {
          	this.sqlManager.rollbackTransaction(status);
          }
        } catch (SQLException e) {
          logger.error("Errore durante la chiusura della transazione", e);
        }
      }
    }
  }

/**
 *
 * @param contri codice programma
 * @throws GestoreException GestoreException
 */
  public void annullaPubblicazione(Long contri, HttpServletRequest request) throws GestoreException {

    String deleteFlussi = "delete from ptflussi where key01 = ?";
    String updateStatoProgramma = "update piatri set statri=1 where contri = ?";
    TransactionStatus status = null;
    boolean commitTransaction = false;
    try {
      status = this.sqlManager.startTransaction();
      this.sqlManager.update(deleteFlussi, new Object[] { contri });
      this.sqlManager.update(updateStatoProgramma, new Object[] { contri });
      String profilo = (String)request.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
      //Verifico se per il profilo corrente è abilitata la funzionalità di pubblicazione ALIPROGMIT
      if (geneManager.getProfili().checkProtec(profilo, "FUNZ", "VIS", "ALT.PIANI.ALIPROGMIT")) {
    	  //Cancello le eventuali occorrenze se il programma era già stato pubblicato
    	  this.sqlManager.update(SQL_DELETE_PIATRI, new Object[] {contri});
    	  this.sqlManager.update(SQL_DELETE_INTTRI, new Object[] {contri});
    	  this.sqlManager.update(SQL_DELETE_IMMTRAI, new Object[] {contri});
    	  this.sqlManager.update(SQL_DELETE_ECOTRI, new Object[] {contri});
    	  this.sqlManager.update(SQL_DELETE_PTFLUSSI, new Object[] {contri});
      }
      commitTransaction = true;
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nel\'esecuzione della query per l'annullamento della pubblicazione del Programma",
          "errors.generico", e);
    } finally {
        if (status != null) {
          try {
            if (commitTransaction) {
            	this.sqlManager.commitTransaction(status);
            } else {
            	this.sqlManager.rollbackTransaction(status);
            }
          } catch (SQLException e) {
            logger.error("Errore durante la chiusura della transazione", e);
          }
        }
      }
  }

  /**
   * @param conint codice intervento
   * @param nprogint numero progressivo intervento
   * @param operazione tipo operazione ("up", "down")
   * @throws GestoreException GestoreException
   */
    public void aggiornaSequenzaInterventi(String codice, Long nprogint, String action) throws GestoreException {

      String updateIntervento1 = "update inttri set nprogint = ? where contri = ? and conint = ?";
      String updateIntervento2 = "update inttri set nprogint = ? where contri = ? and nprogint = ? and conint <> ?";
      TransactionStatus status = null;
      boolean commitTransaction = false;
      try {
    	DataColumnContainer inttriItem = new DataColumnContainer(codice);
		Long contri = (inttriItem.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
		Long conint = (inttriItem.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();
        status = this.sqlManager.startTransaction();
        if (action.equals("up")) {
        	this.sqlManager.update(updateIntervento1, new Object[] { nprogint - 1, contri, conint});
        	this.sqlManager.update(updateIntervento2, new Object[] { nprogint , contri, nprogint - 1, conint});
        } else {
        	this.sqlManager.update(updateIntervento1, new Object[] { nprogint + 1, contri, conint});
        	this.sqlManager.update(updateIntervento2, new Object[] { nprogint , contri, nprogint + 1, conint});
        }
        commitTransaction = true;
      } catch (Exception e) {
        throw new GestoreException(
            "Errore nel\'esecuzione della query per l'aggiornamento della sequenza degli interventi",
            "errors.generico", e);
      } finally {
          if (status != null) {
            try {
              if (commitTransaction) {
              	this.sqlManager.commitTransaction(status);
              } else {
              	this.sqlManager.rollbackTransaction(status);
              }
            } catch (SQLException e) {
              logger.error("Errore durante la chiusura della transazione", e);
            }
          }
        }
    }

	/**
	 * Questa funzione verifica la corretta sequenza degli interventi del
	 * programma, in caso contrario provvede a stabilirla.
	 *
	 * @param contri
	 *            codice programma
	 * @return risultato operazione
	 * @throws GestoreException
	 *             GestoreException
	 */
	public boolean verificaSequenzaInterventi(Long contri) throws GestoreException {
		TransactionStatus transazione = null;
		String sqlInterventi = "select nprogint, conint from inttri where contri = ? and (tipint = 1 or tipint is null) order by annrif, nprogint, conint";
		String sqlUpdateIntervento = "update inttri set nprogint = ? where contri = ? and conint = ?";
		String nprogint, conint;
		boolean result = false;
		try {
			List<?> listaInterventi = null;
			listaInterventi = this.sqlManager.getListVector(sqlInterventi,
					new Object[] { contri });

			if (listaInterventi != null && listaInterventi.size() > 0) {
				transazione = this.sqlManager.startTransaction();
				for (int i = 0; i < listaInterventi.size(); i++) {
					nprogint = SqlManager.getValueFromVectorParam(
							listaInterventi.get(i), 0).getStringValue();
					if (nprogint == null || nprogint.equals("")
							|| Integer.parseInt(nprogint) != (i + 1)) {
						conint = SqlManager.getValueFromVectorParam(
								listaInterventi.get(i), 1).getStringValue();
						// effettua l'aggiornamento del progressivo intervento
						this.sqlManager.update(sqlUpdateIntervento, new Object[] {
								i + 1, contri, new Long(conint) });
					}
				}
			}
			result = true;
		} catch (Exception e) {
			throw new GestoreException(
					"Errore nel\'esecuzione della query per l'aggiornamento della sequenza degli interventi",
					"errors.generico", e);
		} finally {
			if (transazione != null) {
				try {
					if (result) {
						this.sqlManager.commitTransaction(transazione);
					} else {
						this.sqlManager.rollbackTransaction(transazione);
					}
				} catch (SQLException e) {
					logger.error(
							"Errore durante la chiusura della transazione", e);
				}
			}
		}
		return result;
	}

  /**
   *
   * @param interventi lista interventi da modificare
   * @param rup nuovo codice tecnico
   * @throws GestoreException GestoreException
   */
	public void cambiaRUP(String interventi, String rup)
			throws GestoreException {

		String updateRUP = "UPDATE INTTRI SET CODRUP = ? WHERE CONTRI = ? AND CONINT = ?";

		try {
			TransactionStatus status = this.sqlManager.startTransaction();
			String[] chiavi = new String[] {};
			chiavi = interventi.split(";;");
			for (int i = 0; i < chiavi.length; i++) {
				DataColumnContainer inttriItem = new DataColumnContainer(
						chiavi[i]);
				Long contri = (inttriItem.getColumnsBySuffix("CONTRI", true))[0]
						.getValue().longValue();
				Long conint = (inttriItem.getColumnsBySuffix("CONINT", true))[0]
						.getValue().longValue();
				this.sqlManager.update(updateRUP, new Object[] {rup, contri,
						conint });
			}
			this.sqlManager.commitTransaction(status);
		} catch (SQLException e) {
			throw new GestoreException(
					"Errore nel\'esecuzione della query per l'aggiornamento del tecnico negli interventi selezionati",
					"Interventi aggiornati " + interventi, e);
		}
	}

	 /**
	   *
	   * @param contri numero programma
	   * @return true se esiste il file allegato
	   * @throws GestoreException GestoreException
	   */
	public boolean esisteFileAllegato(Long contri) throws GestoreException {
		boolean result = false;
		String SQL_CTRL_BLOB = "select "
				+ this.sqlManager.getDBFunction("length",
						new String[] { "FILE_ALLEGATO" })
				+ " as FILE_ALLEGATO from PIATRI where CONTRI = ? ";

		try {
			String ctrlBlob = ""
					+ sqlManager.getObject(SQL_CTRL_BLOB,
							new Object[] { contri });
			if (ctrlBlob == null || ctrlBlob.trim().equals("")
					|| ctrlBlob.trim().equals("0")
					|| ctrlBlob.trim().equals("null")) {
				result = false;
			} else {
				result = true;
			}
		} catch (Exception e) {
			throw new GestoreException("Errore durante la verifica della presenza del file allegato",
					"errors.generico", e);
		}
		return result;
	}

  /**
   *
   * @param cfein codice fiscale stazione appaltante
   * @return codice stazione appaltante
   * @throws GestoreException GestoreException
   */
   public String getCodein(String cfein) throws GestoreException {
    String sqlSelectCodFlusso = "select CODEIN from UFFINT where CFEIN = ?";
    String codein = "";
    try {
      codein = this.sqlManager.getObject(sqlSelectCodFlusso,
          new Object[] { cfein }).toString();
    } catch (Exception e) {
      throw new GestoreException("Errore durante il recupero del codein",
          "codice.codein", e);
    }
    return codein;
  }

   /**
    * Crea il documento XML per l'importazione del piano triennale nel servizi contratti pubblici.
    *
    * @param contri codice del piano triennale
    *
    * @return oggetto documento contenente i dati del piano triennale
    * @throws GestoreException GestoreException
    */
	public XmlObject getXmlDocumentProgrammiTriennali(Long contri)
			throws GestoreException {
		XmlObject document = null;
		DataColumnContainer piatri = null;
		PtDocument doc = PtDocument.Factory.newInstance();
		Pt piano = doc.addNewPt();

		try {
			piatri = new DataColumnContainer(this.sqlManager, "PIATRI",
					sqlSelectPiatri, new Object[] { contri });
			//dati generali
			piano.setAnnoElencoAnnuale(piatri.getLong("ANNTRI"));
			piano.setIdEnte(1);
			piano.setTriennio(piatri.getLong("ANNTRI") + "/"
					+ (piatri.getLong("ANNTRI") + 2));
			String codiceFiscale = (String) this.sqlManager.getObject(sqlSelectCfisStazioneAppaltante, new Object[] { piatri.getString("CENINT") });
			piano.setAmmCodfisc(codiceFiscale);
			//setup info
			SetupInfo setupInfo = piano.addNewSetupInfo();

			setupInfo.setDbDecSeparator(".");
			setupInfo.setSysDecSeparator(",");
			setupInfo.setHiprog3Version("2.1.1");
			//note
			Note note = piano.addNewNote();
			note.setScheda1("");
			note.setScheda2("");
			note.setScheda2B("");
			note.setScheda3("");
			//quadro risorse
			QuadroRisorse quadroRisorse = piano.addNewQuadroRisorse();
			quadroRisorse.setPtTriennio(piatri.getLong("ANNTRI") + "/" + (piatri.getLong("ANNTRI") + 2));
			quadroRisorse.setAnnoInserim(piatri.getLong("ANNTRI"));

			quadroRisorse.setImpVincolato1(doubleToMoney(piatri.getDouble("DV1TRI")));
			quadroRisorse.setImpVincolato2(doubleToMoney(piatri.getDouble("DV2TRI")));
			quadroRisorse.setImpVincolato3(doubleToMoney(piatri.getDouble("DV3TRI")));
			quadroRisorse.setImpVincolatoTotale(sumToMoney(new Double[]{piatri.getDouble("DV1TRI"),piatri.getDouble("DV2TRI"),piatri.getDouble("DV3TRI")}));

			quadroRisorse.setImpMutuo1(doubleToMoney(piatri.getDouble("MU1TRI")));
			quadroRisorse.setImpMutuo2(doubleToMoney(piatri.getDouble("MU2TRI")));
			quadroRisorse.setImpMutuo3(doubleToMoney(piatri.getDouble("MU3TRI")));
			quadroRisorse.setImpMutuoTotale(sumToMoney(new Double[]{piatri.getDouble("MU1TRI"),piatri.getDouble("MU2TRI"),piatri.getDouble("MU3TRI")}));

			quadroRisorse.setImpCapitaliPrivati1(doubleToMoney(piatri.getDouble("PR1TRI")));
			quadroRisorse.setImpCapitaliPrivati2(doubleToMoney(piatri.getDouble("PR2TRI")));
			quadroRisorse.setImpCapitaliPrivati3(doubleToMoney(piatri.getDouble("PR3TRI")));
			quadroRisorse.setImpCapitaliPrivatiTotale(sumToMoney(new Double[]{piatri.getDouble("PR1TRI"),piatri.getDouble("PR2TRI"),piatri.getDouble("PR3TRI")}));

			quadroRisorse.setImpTrasfImmob1(doubleToMoney(piatri.getDouble("IM1TRI")));
			quadroRisorse.setImpTrasfImmob2(doubleToMoney(piatri.getDouble("IM2TRI")));
			quadroRisorse.setImpTrasfImmob3(doubleToMoney(piatri.getDouble("IM3TRI")));
			quadroRisorse.setImpTrasfImmobTotale(sumToMoney(new Double[]{piatri.getDouble("IM1TRI"),piatri.getDouble("IM2TRI"),piatri.getDouble("IM3TRI")}));

			quadroRisorse.setImpStanzBilancio1(doubleToMoney(piatri.getDouble("BI1TRI")));
			quadroRisorse.setImpStanzBilancio2(doubleToMoney(piatri.getDouble("BI2TRI")));
			quadroRisorse.setImpStanzBilancio3(doubleToMoney(piatri.getDouble("BI3TRI")));
			quadroRisorse.setImpStanzBilancioTotale(sumToMoney(new Double[]{piatri.getDouble("BI1TRI"),piatri.getDouble("BI2TRI"),piatri.getDouble("BI3TRI")}));

			quadroRisorse.setImpAltro1(doubleToMoney(piatri.getDouble("AL1TRI")));
			quadroRisorse.setImpAltro2(doubleToMoney(piatri.getDouble("AL2TRI")));
			quadroRisorse.setImpAltro3(doubleToMoney(piatri.getDouble("AL3TRI")));
			quadroRisorse.setImpAltroTotale(sumToMoney(new Double[]{piatri.getDouble("AL1TRI"),piatri.getDouble("AL2TRI"),piatri.getDouble("AL3TRI")}));

			quadroRisorse.setImp1Totale(sumToMoney(new Double[]{piatri.getDouble("TO1TRI"),piatri.getDouble("PR1TRI")}));
			quadroRisorse.setImp2Totale(sumToMoney(new Double[]{piatri.getDouble("TO2TRI"),piatri.getDouble("PR2TRI")}));
			quadroRisorse.setImp3Totale(sumToMoney(new Double[]{piatri.getDouble("TO3TRI"),piatri.getDouble("PR3TRI")}));
			//interventi triennali
			InterventiTriennali interventiTriennali = piano.addNewInterventiTriennali();
			List< ? > interventi = this.sqlManager.getListHashMap(sqlSelectInttri, new Object[] { contri });
			if (interventi != null && interventi.size() > 0) {

				for (int i = 0; i < interventi.size(); i++) {
					DataColumnContainer inttri = new DataColumnContainer(sqlManager, "INTTRI", sqlSelectInttri, new Object[] { contri });
					inttri.setValoriFromMap((HashMap< ?, ? >) interventi.get(i), true);
					InterventiTriennali.Intervento intervento = interventiTriennali.addNewIntervento();
					intervento.setNProgressivo(inttri.getLong("CONINT"));
					intervento.setCodIntervAmm((inttri.getString("CODINT") != null) ? inttri.getString("CODINT") : "");
					intervento.setAnnoIntervento(piatri.getLong("ANNTRI") + inttri.getLong("ANNRIF") - 1);
					intervento.setLocalizzazioneCodIstat((inttri.getString("COMINT") != null) ? inttri.getString("COMINT") : "");
					intervento.setDescrizioneInterv((inttri.getString("DESINT") != null) ? inttri.getString("DESINT") : "");
					intervento.setTipoOpera((inttri.getString("SEZINT") != null) ? inttri.getString("SEZINT") : "");
					intervento.setIdCategoria((String) this.sqlManager.getObject(sqlSelectCategoriaIntervento, new Object[] { inttri.getString("CATINT") }));
					intervento.setCostoStimato1(sumToMoney(new Double[]{inttri.getDouble("DI1INT"),inttri.getDouble("PR1TRI")}));
					intervento.setCostoStimato2(sumToMoney(new Double[]{inttri.getDouble("DI2INT"),inttri.getDouble("PR2TRI")}));
					intervento.setCostoStimato3(sumToMoney(new Double[]{inttri.getDouble("DI3INT"),inttri.getDouble("PR3TRI")}));
					intervento.setCostoStimatoTot(sumToMoney(new Double[]{inttri.getDouble("DI1INT"),inttri.getDouble("PR1TRI"),inttri.getDouble("DI2INT"),inttri.getDouble("PR2TRI"),inttri.getDouble("DI3INT"),inttri.getDouble("PR3TRI")}));
					if ((!intervento.getCostoStimato1().equals("0,00") && !intervento.getCostoStimato2().equals("0,00")) || (!intervento.getCostoStimato1().equals("0,00") && !intervento.getCostoStimato3().equals("0,00")) || (!intervento.getCostoStimato2().equals("0,00") && !intervento.getCostoStimato3().equals("0,00"))) {
						intervento.setPiuAnnualita("Y");
					} else {
						intervento.setPiuAnnualita("N");
					}

					if (!quadroRisorse.getImpTrasfImmobTotale().equals("0,00")) {
						intervento.setTrasfImmobFlag("Y");
					} else {
						intervento.setTrasfImmobFlag("N");
					}
					intervento.setTipoApportoCapitale((inttri.getString("TCPINT") != null) ? inttri.getString("TCPINT") : "0");
					intervento.setImpCapitalePrivato(doubleToMoney(inttri.getDouble("ICPINT")));
					intervento.setHasIntAnnuale((inttri.getString("ANNINT") != null && inttri.getString("ANNINT").equals("1")) ? "Y" : "N");
					intervento.setOrderId(i + 1);
					//intervento annuale
					if (intervento.getHasIntAnnuale().equals("Y")) {
						InterventiTriennali.Intervento.InterventoAnnuale annuale = intervento.addNewInterventoAnnuale();
						annuale.setConfUrbanistica((inttri.getString("URCINT") != null && inttri.getString("URCINT").equals("1")) ? "Y" : "N");
						annuale.setConfAmbientale((inttri.getString("APCINT") != null && inttri.getString("APCINT").equals("1")) ? "Y" : "N");
						annuale.setStatoProgettazione((String) this.sqlManager.getObject(sqlSelectStatoProgettazione, new Object[] { inttri.getString("STAPRO") }));
						annuale.setFinalita((String) this.sqlManager.getObject(sqlSelectFinalitaIntervento, new Object[] { inttri.getString("FIINTR") }));
						annuale.setPriorita(inttri.getLong("PRGINT"));
						annuale.setTrimInizioLav(inttri.getLong("TILINT") + "°");
						annuale.setAnnoInizioLav(inttri.getLong("AILINT"));
						annuale.setTrimFineLav(inttri.getLong("TFLINT") + "°");
						annuale.setAnnoFineLav(inttri.getLong("AFLINT"));
						//tecnico
						DataColumnContainer tecnico = new DataColumnContainer(this.sqlManager, "TECNI",	sqlSelectTecni, new Object[] { (inttri.getString("CODRUP") != null) ? inttri.getString("CODRUP") : "" });
						annuale.setIdRproc((inttri.getString("CODRUP") != null) ? inttri.getString("CODRUP") : "");
						annuale.setNomeRproc((tecnico.getString("NOMETEI") != null) ? tecnico.getString("NOMETEI") : "");
						annuale.setCognomeRproc((tecnico.getString("COGTEI") != null) ? tecnico.getString("COGTEI") : "");
						annuale.setCfRproc((tecnico.getString("CFTEC") != null) ? tecnico.getString("CFTEC") : "");
					}
					//beni immobili
					if (!quadroRisorse.getImpTrasfImmobTotale().equals("0,00"))	{
						List< ? > immobili = this.sqlManager.getListHashMap(sqlSelectImmtrai, new Object[] { contri , inttri.getLong("CONINT")});
						if (immobili != null && immobili.size() > 0) {
							InterventiTriennali.Intervento.BeniImmobili beniImmobili = intervento.addNewBeniImmobili();
							for (int j = 0; j < immobili.size(); j++) {
								DataColumnContainer immtrai = new DataColumnContainer(sqlManager, "IMMTRAI", sqlSelectImmtrai, new Object[] { contri, inttri.getLong("CONINT") });
								immtrai.setValoriFromMap((HashMap<?,?>) immobili.get(j), true);
								InterventiTriennali.Intervento.BeniImmobili.Bene bene = beniImmobili.addNewBene();
								bene.setDescImmobile((immtrai.getString("DESIMM") != null) ? immtrai.getString("DESIMM") : "");
								bene.setPienaProprieta((immtrai.getLong("PROIMM") == 1) ? "Y" : "N");
								bene.setSoloSuperficie((immtrai.getLong("PROIMM") == 2) ? "Y" : "N");
								bene.setValoreStimato(doubleToMoney(immtrai.getDouble("VALIMM")));
								bene.setAnnualita(immtrai.getLong("ANNIMM"));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new GestoreException(
					"Errore durante la creazione del documento xml per il piano triennale",
					"Piano triennale n " + contri, e);
		}

		//procediamo alla validazione del document
    	ArrayList<Object> validationErrors = new ArrayList<Object>();
	    XmlOptions validationOptions = new XmlOptions();
	    validationOptions.setErrorListener(validationErrors);

		if (!doc.validate(validationOptions)) {
			String listaErroriValidazione = "";
	      	Iterator< ? > iter = validationErrors.iterator();
	      	while (iter.hasNext()) {
	      		listaErroriValidazione += iter.next() + "\n";
	      	}
	      	logger.error("Errore durante la validazione formale del documento xml per l'esportazione HiProg"
	      				+ "\n"
	      				+ listaErroriValidazione);
	      	throw new GestoreException(
	      				"Errore durante la validazione formale del documento xml per l'esportazione HiProg",
	      				"commons.validate",
	      				new Object[] { listaErroriValidazione }, null);
		}
		document = doc;
		document.documentProperties().setEncoding("iso-8859-1");

		return document;
	}

	/**
	 *
	 * @param imp importo
	 * @return importo con separatore decimale = ,
	 */
	public static String doubleToMoney(final Double imp)
	{
		String importo = "0,00";
		if (imp!=null)
		{
			DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
			simbolo.setDecimalSeparator(',');
			DecimalFormat formatter = new DecimalFormat("#0.00",simbolo);
			importo = formatter.format(imp);
		}
		return importo;
	}

	/**
	 *
	 * @param imp array di importi
	 * @return importo somma con separatore decimale = ,
	 */
	public static String sumToMoney(final Double[] imp)
	{
		String importo = "0,00";
		double sum = 0;
		for (int i = 0; i < imp.length; i++)
		{
			sum += (imp[i]!=null)?imp[i]:0;
		}
		DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
		simbolo.setDecimalSeparator(',');
		DecimalFormat formatter = new DecimalFormat("#0.00",simbolo);
		importo = formatter.format(sum);
		return importo;
	}
	/**
	   *
	   * @param sys_dec_separator
	   * @param importoStringa
	   * @return
	   * @throws ParseException
	   */
	public static Double getImporti(String sys_dec_separator,
	      String importoStringa) throws ParseException {
	    DecimalFormat df = new DecimalFormat();
	    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	    symbols.setDecimalSeparator(sys_dec_separator.charAt(0));
	    symbols.setGroupingSeparator(' ');
	    df.setDecimalFormatSymbols(symbols);
	    return (df.parse(importoStringa)).doubleValue();
	  }

	/**
	 * Effettua la validazione dei dati del programma per la generazione del file XML di esportazione
	 * @param params parametri ingr esso
	 * @return risultato della validazione
	 * @throws JspException JspException
	 */
	 public HashMap<String, Object> validateProgrammi(Object[] params)  throws JspException {
		 HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
		 List<Object> resultValidateList = new Vector<Object>();
		 String titolo = null;
		 String messaggio = null;
		 List< ? > controlli;
		 String sqlControlli;
		 int codiceControllo;
		 String valore,querySql;
		 try {
			 Long contri = new Long((String) params[1]); // codice programma triennale
			 String pagina;
			 try {
				 titolo = "Programma " + (String) this.sqlManager.getObject(sqlSelectIDPiatri, new Object[] {contri});
				 pagina = "Dati generali";
				 //recupero i controlli da effettuare per i dati generali del programma
				 sqlControlli = "SELECT codcontrollo, tipocontrollo, msgcontrollo FROM ptcontrolli WHERE codcontrollo < 3000 AND tipocontrollo in ('W','E')";
				 controlli = this.sqlManager.getListVector(sqlControlli, new Object[] {});
				 //recupero i dati generali del programma
				 querySql = "SELECT ANNTRI, TIPROG, CENINT, NAPTRI, DAPTRI, NADOZI, DADOZI, RESPRO FROM PIATRI WHERE CONTRI = ? ";
				 DataColumnContainer piatri = new DataColumnContainer(this.sqlManager, "PIATRI", querySql, new Object[] { contri });
				 Long tiprog = piatri.getLong("TIPROG");


				 if (controlli != null && controlli.size() > 0) {
					 for (int i = 0; i < controlli.size(); i++) {
						 //verifico il controllo da effettuare
						 valore = SqlManager.getValueFromVectorParam(controlli.get(i),1).getStringValue();
						 if (valore != null && !valore.equals("")) {
							 codiceControllo = new Integer(SqlManager.getValueFromVectorParam(controlli.get(i),0).getStringValue());
							 messaggio = SqlManager.getValueFromVectorParam(controlli.get(i),2).getStringValue();
							 switch (codiceControllo) {
							 case 1001:
								//valorizzazione codice fiscale stazione appaltante
							 case 1002:
								 //Il C.F. della stazione appaltante deve essere un C.F. valido di persona giuridica
								 String codiceFiscale = (String) this.sqlManager.getObject(sqlSelectCfisStazioneAppaltante, new Object[] { piatri.getString("CENINT") });
								 //controllo partita iva / codice fiscale stazione appaltante obbligatoria
								 if (codiceControllo == 1001) {
									 if (!isStringaValorizzata(codiceFiscale)) {
								         this.addAvviso(resultValidateList, valore, "Dati generali - stazione appaltante", messaggio);
									 }
								 } else if (codiceControllo == 1002) {
									 if (!UtilityFiscali.isValidPartitaIVA(codiceFiscale)) {
								         this.addAvviso(resultValidateList, valore, "Dati generali - stazione appaltante", messaggio);
									 }
								 }
								 break;
							 case 1003:
								 //verifica ID amministrazione stazione appaltante
								 try {
									 //Long idammin = (Long) this.sqlManager.getObject(sqlSelectIdAmminStazioneAppaltante, new Object[] { piatri.getString("CENINT") });
									 //int x = Integer.parseInt(idammin.toString());
								 } catch (Exception ex) {
									 this.addAvviso(resultValidateList, "E", "Dati generali - stazione appaltante", "L'ID Amministrazione deve essere compreso tra 0 e 2,147,483,647");
								 }
								 break;
							 case 1004:
								 //Campo Codice ISTAT obbligatorio (CODCIT)
								 String codiceISTAT = (String) this.sqlManager.getObject(sqlSelectCISTATStazioneAppaltante, new Object[] { piatri.getString("CENINT") });
								 if (!isStringaValorizzata(codiceISTAT) || codiceISTAT.length() != 9) {
									 this.addAvviso(resultValidateList, valore, "Dati generali - stazione appaltante", messaggio);
							 	 }
								 break;
							 case 2001:
								 //valorizzazione dell'anno
								 if (piatri.getLong("ANNTRI") == null) {
							         this.addAvviso(resultValidateList, valore, pagina, messaggio);
								 }
								 break;
							 case 2002:
								 //L'anno di inizio deve essere >= dell'anno corrente
							 case 2007:
								 //L'anno di inizio deve essere >= 2000
								 Long annoInizio = piatri.getLong("ANNTRI");
								 if (annoInizio != null) {
									 if (codiceControllo == 2002) {
										 GregorianCalendar dataOdierna = new GregorianCalendar();
									     if (dataOdierna != null) {
									    	 if (annoInizio < dataOdierna.get(1) || annoInizio >= dataOdierna.get(1) + 1) {
									    		 this.addAvviso(resultValidateList, valore, pagina, messaggio);
									         }
									      }
									 } else if (codiceControllo == 2007) {
										 if (annoInizio < 2000) {
								    		 this.addAvviso(resultValidateList, valore, pagina, messaggio);
								         }
									 }
								 }
								 break;
							 case 2003:
								 //Valorizzare numero e data approvazione
								 if (!isStringaValorizzata(piatri.getString("NAPTRI")) || piatri.getData("DAPTRI") == null) {
							         this.addAvviso(resultValidateList, valore, pagina, messaggio);
								 }
								 break;
							 case 2004:
								 //Valorizzare numero e data adozione
								 if (!isStringaValorizzata(piatri.getString("NADOZI")) || piatri.getData("DADOZI") == null) {
							         this.addAvviso(resultValidateList, valore, pagina, messaggio);
								 }
								 break;
							 case 2005:
								 //verifico esistenza intervento
								 Long nrInterventi = (Long) this.sqlManager.getObject("select count(*) from inttri where contri = ? ", new Object[] {contri});
								 if (nrInterventi == 0) {
									 this.addAvviso(resultValidateList, valore, pagina, messaggio);
								 }
								 break;
							 case 2006:
								 //File pdf obbligatorio
								 if (!esisteFileAllegato(contri)) {
							         this.addAvviso(resultValidateList, valore, pagina, messaggio);
								 }
								 break;
							 case 2008:
								 //C.F. del responsabile del programma deve essere un C.F. valido di persona fisica
								 if (!isStringaValorizzata(piatri.getString("RESPRO"))) {
									 this.addAvviso(resultValidateList, valore, pagina, messaggio);
								 } else {
									 List<?> tecnico = this.sqlManager.getListHashMap(sqlSelectTecni, new Object[] { piatri.getString("RESPRO") });
									 if (tecnico.size() == 1) {
										 DataColumnContainer tecni = new DataColumnContainer(this.sqlManager, "TECNI", sqlSelectTecni, new Object[] { piatri.getString("RESPRO") });
										 if (!isStringaValorizzata(tecni.getString("CFTEC")) || !UtilityFiscali.isValidCodiceFiscale(tecni.getString("CFTEC"))) {
											 this.addAvviso(resultValidateList, valore, pagina, messaggio);
										 }
									 } else {
										 this.addAvviso(resultValidateList, valore, pagina, messaggio);
									 }
								 }
								 break;
							 default:
								 break;
							 }
						 }
			     }
			   }

				 //recupero i controlli da effettuare per i dati degli interventi
				 sqlControlli = "SELECT codcontrollo, tipocontrollo, msgcontrollo FROM ptcontrolli WHERE codcontrollo >= 3000 AND codcontrollo < 4000  AND tipocontrollo in ('W','E')";
				 controlli = this.sqlManager.getListVector(sqlControlli, new Object[] {});
				 if (controlli != null && controlli.size() > 0) {
					// recupero i dati degli interventi
					String annuale;
					querySql = "select * from inttri where contri = ? ";
					List<?> interventi = null;
					if (tiprog == 3) {
						querySql += "and tipint=1";
					}
					querySql += " order by annrif, nprogint";
					if (tiprog == 1 || tiprog == 3) {
						interventi = this.sqlManager.getListHashMap(querySql, new Object[] { contri });
					}
					if (interventi != null && interventi.size() > 0) {
						sqlControlli = "SELECT codcontrollo, tipocontrollo, msgcontrollo FROM ptcontrolli WHERE codcontrollo >= 4000 AND codcontrollo < 5000  AND tipocontrollo in ('W','E')";
						List<?> controlliImmobili = this.sqlManager.getListVector(sqlControlli, new Object[] {});
						for (int i = 0; i < interventi.size(); i++) {
							DataColumnContainer inttri = new DataColumnContainer(sqlManager, "INTTRI", querySql, new Object[] { contri });
							inttri.setValoriFromMap((HashMap<?, ?>) interventi.get(i), true);
							pagina = "intervento n." + inttri.getLong("NPROGINT");
							annuale = inttri.getString("ANNINT");

							for (int j = 0; j < controlli.size(); j++) {
								// verifico il controllo da effettuare
								valore = SqlManager.getValueFromVectorParam(controlli.get(j), 1).getStringValue();
								if (valore != null && !valore.equals("")) {
									codiceControllo = new Integer(SqlManager.getValueFromVectorParam(controlli.get(j), 0).getStringValue());
									messaggio = SqlManager.getValueFromVectorParam(controlli.get(j), 2).getStringValue();
									switch (codiceControllo) {
									case 3001:
										// Campo descrizione (DESINT) obbligatorio
										if (!isStringaValorizzata(inttri.getString("DESINT"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3002:
										// Campo codice CPV (CODCPV) obbligatorio
										if (!isStringaValorizzata(inttri.getString("CODCPV"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3003:
										// Campo codice ISTAT oppure il NUTS obbligatorio
										if (!isStringaValorizzata(inttri.getString("COMINT")) && !isStringaValorizzata(inttri.getString("NUTS"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3004:
										// Non specificare il codice NUTS se c’è il codice ISTAT
										if (isStringaValorizzata(inttri.getString("COMINT")) && isStringaValorizzata(inttri.getString("NUTS"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3005:
										// Campo Intervento e' stato inserito nel Piano annuale obbligatorio
										if (!isStringaValorizzata(annuale)) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3006:
										// Campo Annualità di riferimento (ANNRIF) obbligatorio
										if (inttri.getLong("ANNRIF") == null) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3007:
										// Campo tipologia (SEZINT) obbligatorio
										if (!isStringaValorizzata(inttri.getString("SEZINT"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3008:
										// Campo categoria dell’intervento obbligatorio
										if (!isStringaValorizzata(inttri.getString("CATINT"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3009:
										// L’importo dell’intervento deve essere >0
										if (inttri.getDouble("TOTINT") == null || inttri.getDouble("TOTINT") <= 0) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3010:
										// La somma degli importi per “Trasferimento immobili” deve essere uguale alla somma del “Valore stimato dell’immobile” della scheda immobile
									case 3012:
										// Se c’è importo da trasferimento immobili (IM1TRI, IM2TRI o IM3TRI >0) deve essere specificato almeno un immobile (record di IMMTRAI)
										double importoTrasferimentoImmobili = 0;
										if (inttri.getDouble("IM1TRI") != null) {
											importoTrasferimentoImmobili += inttri.getDouble("IM1TRI");
										}
										if (inttri.getDouble("IM2TRI") != null) {
											importoTrasferimentoImmobili += inttri.getDouble("IM2TRI");
										}
										if (inttri.getDouble("IM3TRI") != null) {
											importoTrasferimentoImmobili += inttri.getDouble("IM3TRI");
										}
										Long countImmobili = (Long) this.sqlManager.getObject("select count(*) from immtrai where contri = ? and conint = ?", new Object[] {contri,	inttri.getLong("CONINT") });
										Double stimaImmobili = (Double) this.sqlManager.getObject("select sum("	+ this.sqlManager.getDBFunction("isnull", new String[] {"VALIMM", "0.00" }) + ") from immtrai where contri = ? and conint = ?", new Object[] {contri, inttri.getLong("CONINT") });
										if (codiceControllo == 3010) {
											if (stimaImmobili == null) {
												stimaImmobili = new Double(0);
											}
											if (importoTrasferimentoImmobili != stimaImmobili.doubleValue()) {
												this.addAvviso(resultValidateList, valore, pagina, messaggio);
											}
										} else if (codiceControllo == 3012) {
											if (importoTrasferimentoImmobili > 0) {
												if (countImmobili == null || countImmobili == 0) {
													this.addAvviso(resultValidateList, valore, pagina, messaggio);
												}
											}
										}
										break;
									case 3011:
										// Se c’è apporto da capitale privato (campo “Tipologia apporto di capitale privato” not null) deve esserci almeno un importo per “Entrate con apporti di capitale privato” >0
										if (isStringaValorizzata(inttri.getString("TCPINT")) && !inttri.getString("TCPINT").equals("00")) {
											if (inttri.getDouble("ICPINT") == null || inttri.getDouble("ICPINT") <= 0) {
												this.addAvviso(resultValidateList, valore, pagina,	messaggio);
											}
										}
										break;
									case 3013:
										// Se il programma è annuale il campo finalità del procedimento è obbligatorio
										if (isStringaValorizzata(annuale) && annuale.equals("1") && !isStringaValorizzata(inttri.getString("FIINTR"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3014:
										// Se il programma è annuale il campo priorità del procedimento è obbligatorio
										if (isStringaValorizzata(annuale) && annuale.equals("1") && (inttri.getLong("PRGINT") == null || inttri.getLong("PRGINT") == 0)) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3015:
										// Se il programma è annuale il campo stato del procedimento è obbligatorio
										if (isStringaValorizzata(annuale) && annuale.equals("1") && !isStringaValorizzata(inttri.getString("STAPRO"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3016:
										// Se il programma è annuale il campo anno e trimestre inizio procedimento è obbligatorio
										if (isStringaValorizzata(annuale) && annuale.equals("1")) {
											if (inttri.getLong("AILINT") == null || inttri.getLong("TILINT") == null) {
												this.addAvviso(resultValidateList, valore, pagina, messaggio);
											}
										}
										break;
									case 3017:
										// Se il programma è annuale il campo anno e trimestre fine procedimento è obbligatorio
										if (isStringaValorizzata(annuale) && annuale.equals("1")) {
											if (inttri.getLong("AFLINT") == null || inttri.getLong("TFLINT") == null) {
												this.addAvviso(resultValidateList, valore, pagina, messaggio);
											}
										}
										break;
									case 3018:
										// La data inizio deve essere inferiore o uguale alla data di fine (se compilate)
										if (inttri.getLong("AILINT") != null && inttri.getLong("TILINT") != null && inttri.getLong("AFLINT") != null && inttri.getLong("TFLINT") != null) {
											if (((inttri.getLong("AFLINT") * 10)	+ inttri.getLong("TFLINT") < (inttri.getLong("AILINT") * 10) + inttri.getLong("TILINT")) || inttri.getLong("AFLINT") < 2000 || inttri.getLong("AILINT") < 2000) {
												this.addAvviso(resultValidateList, valore, pagina, messaggio);
											}
										}
										break;
									case 3020:
										// Se il programma è annuale devono essere specificati nome e cognome responsabile del procedimento
									case 3021:
										// Se il programma è annuale il responsabile del procedimento deve avere un codice fiscale valido
										if (isStringaValorizzata(annuale) && annuale.equals("1")) {
											if (!isStringaValorizzata(inttri.getString("CODRUP"))) {
												this.addAvviso(resultValidateList, valore, pagina, messaggio);
											} else {
												List<?> tecnico = this.sqlManager.getListHashMap(sqlSelectTecni, new Object[] { inttri.getString("CODRUP") });
												if (tecnico.size() == 1) {
													DataColumnContainer tecni = new DataColumnContainer(this.sqlManager, "TECNI", sqlSelectTecni, new Object[] { inttri.getString("CODRUP") });
													if (codiceControllo == 3020) {
														if (!isStringaValorizzata(tecni.getString("NOMETEI")) || !isStringaValorizzata(tecni.getString("COGTEI"))) {
															this.addAvviso(resultValidateList, valore, pagina, messaggio);
														}
													} else {
														if (!isStringaValorizzata(tecni.getString("CFTEC"))	|| !UtilityFiscali.isValidCodiceFiscale(tecni.getString("CFTEC"))) {
															this.addAvviso(resultValidateList, valore, pagina, messaggio);
														}
													}
												}
												else {
													this.addAvviso(resultValidateList, valore, pagina, messaggio);
												}
											}
										}
										break;
									case 3022:
										// Campo codice CUP (CUPPRG) obbligatorio per intervento annuale e FLAG_CUP non esente
										if (isStringaValorizzata(annuale) && annuale.equals("1") && (inttri.getLong("FLAG_CUP") == null || inttri.getLong("FLAG_CUP").longValue() == 2) && !isStringaValorizzata(inttri.getString("CUPPRG"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 3023:
										// Campo codice CUP (CUPPRG) obbligatorio per intervento della seconda e terza annualità
										if (isStringaValorizzata(annuale) && !annuale.equals("1") && (inttri.getLong("FLAG_CUP") == null || inttri.getLong("FLAG_CUP").longValue() == 2) && !isStringaValorizzata(inttri.getString("CUPPRG"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									default:
										break;
									}
								}
							}

							// verifico gli eventuali immobili dell'intervento
							if (controlliImmobili != null && controlliImmobili.size() > 0) {
								List<?> immobili = this.sqlManager.getListHashMap(sqlSelectImmtrai, new Object[] { contri, inttri.getLong("CONINT") });
								if (immobili != null && immobili.size() > 0) {
									for (int j = 0; j < immobili.size(); j++) {
										DataColumnContainer immtrai = new DataColumnContainer(sqlManager, "IMMTRAI", sqlSelectImmtrai, new Object[] {contri, inttri.getLong("CONINT") });
										immtrai.setValoriFromMap((HashMap<?, ?>) immobili.get(j), true);
										pagina = "immobile n." + immtrai.getLong("NUMIMM") + " nell'intervento n." + inttri.getLong("NPROGINT");


										for (int y = 0; y < controlliImmobili.size(); y++) {
											// verifico il controllo da effettuare
											valore = SqlManager.getValueFromVectorParam(controlliImmobili.get(y), 1).getStringValue();
											if (valore != null && !valore.equals("")) {
												codiceControllo = new Integer(SqlManager.getValueFromVectorParam(controlliImmobili.get(y), 0).getStringValue());
												messaggio = SqlManager.getValueFromVectorParam(controlliImmobili.get(y), 2).getStringValue();
												switch (codiceControllo) {
												case 4001:
													// Campo Valore stimato dell’immobile (VALIMM) obbligatorio
													if (immtrai.getDouble("VALIMM") == null	|| immtrai.getDouble("VALIMM") <= 0) {
														this.addAvviso(resultValidateList, valore, pagina, messaggio);
													}
													break;
												case 4002:
													// Campo descrizione dell'immobile (DESIMM) obbligatorio
													if (!isStringaValorizzata(immtrai.getString("DESIMM"))) {
														this.addAvviso(resultValidateList, valore, pagina, messaggio);
													}
													break;
												case 4003:
													// Campo tipo di proprietà (PROIMM) obbligatorio
													if (immtrai.getLong("PROIMM") == null) {
														this.addAvviso(resultValidateList, valore, pagina, messaggio);
													}
													break;
												}
											}
										}
									}
								}
							}
						}
					}
				 }

				 //verifico gli eventuali lavori in economia
				 //recupero i controlli da effettuare per i dati dei lavori in economia
				 sqlControlli = "SELECT codcontrollo, tipocontrollo, msgcontrollo FROM ptcontrolli WHERE codcontrollo >= 5000 AND codcontrollo < 6000 AND tipocontrollo in ('W','E')";
				 controlli = this.sqlManager.getListVector(sqlControlli, new Object[] {});
				 if (controlli != null && controlli.size() > 0) {
					 querySql = "select * from ecotri where contri = ? ";
					 List<?> lavoriEconomia = null;
					 lavoriEconomia = this.sqlManager.getListHashMap(querySql, new Object[] { contri });
					 if (lavoriEconomia != null && lavoriEconomia.size() > 0) {
						 for (int i = 0; i < lavoriEconomia.size(); i++) {
							 DataColumnContainer ecotri = new DataColumnContainer(sqlManager, "ECOTRI", querySql, new Object[] { contri });
							 ecotri.setValoriFromMap((HashMap<?,?>)lavoriEconomia.get(i), true);
							 pagina = "lavoro in economia n." + ecotri.getLong("CONECO");

							 for (int j = 0; j < controlli.size(); j++) {
								// verifico il controllo da effettuare
								valore = SqlManager.getValueFromVectorParam(controlli.get(j), 1).getStringValue();
								if (valore != null && !valore.equals("")) {
									codiceControllo = new Integer(SqlManager.getValueFromVectorParam(controlli.get(j), 0).getStringValue());
									messaggio = SqlManager.getValueFromVectorParam(controlli.get(j), 2).getStringValue();
									switch (codiceControllo) {
									case 5001:
										//Campo descrizione (DESCRI) obbligatorio
										if (!isStringaValorizzata(ecotri.getString("DESCRI"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 5002:
										//Campo costo stimato (STIMA) obbligatorio
										if (ecotri.getDouble("STIMA") == null	|| ecotri.getDouble("STIMA") <= 0) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									}
								}
							 }
						 }
					 }
				 }

				 //verifico gli eventuali interventi forniture e servizi
				 //recupero i controlli da effettuare per i dati degli interventi forniture e servizi
				 sqlControlli = "SELECT codcontrollo, tipocontrollo, msgcontrollo FROM ptcontrolli WHERE codcontrollo >= 6000 AND codcontrollo < 7000 AND tipocontrollo in ('W','E')";
				 controlli = this.sqlManager.getListVector(sqlControlli, new Object[] {});
				 if (controlli != null && controlli.size() > 0) {

					 querySql = "select * from inttri where contri = ? ";
					 List<?> fornitureEservizi = null;
					 if (tiprog == 3) {
						 querySql += "and tipint=2";
					 }
					 if (tiprog == 2 || tiprog == 3) {
						 fornitureEservizi = this.sqlManager.getListHashMap(querySql, new Object[] { contri });
					 }

					 if (fornitureEservizi != null && fornitureEservizi.size() > 0) {
						 for (int i = 0; i < fornitureEservizi.size(); i++) {
							 DataColumnContainer inttri = new DataColumnContainer(sqlManager, "INTTRI", querySql, new Object[] { contri });
							 inttri.setValoriFromMap((HashMap<?,?>)fornitureEservizi.get(i), true);
							 pagina = "forniture e servizi n." + inttri.getLong("CONINT");
							 for (int j = 0; j < controlli.size(); j++) {
								// verifico il controllo da effettuare
								valore = SqlManager.getValueFromVectorParam(controlli.get(j), 1).getStringValue();
								if (valore != null && !valore.equals("")) {
									codiceControllo = new Integer(SqlManager.getValueFromVectorParam(controlli.get(j), 0).getStringValue());
									messaggio = SqlManager.getValueFromVectorParam(controlli.get(j), 2).getStringValue();
									switch (codiceControllo) {
									case 6001:
										//Campo descrizione (DESINT) obbligatorio
										if (!isStringaValorizzata(inttri.getString("DESINT"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 6002:
										// Campo codice CPV (CODCPV) obbligatorio
										if (!isStringaValorizzata(inttri.getString("CODCPV"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 6003:
										//Campo tipologia (TIPOIN) obbligatorio
										if (!isStringaValorizzata(inttri.getString("TIPOIN"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									case 6004:
										//Devono essere specificati nome e cognome responsabile del procedimento
									case 6005:
										//Il responsabile del procedimento deve avere un codice fiscale valido
										if (!isStringaValorizzata(inttri.getString("CODRUP"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										} else {
											List<?> tecnico = this.sqlManager.getListHashMap(sqlSelectTecni, new Object[] { inttri.getString("CODRUP") });
											if (tecnico.size() == 1) {
												DataColumnContainer tecni = new DataColumnContainer(this.sqlManager, "TECNI", sqlSelectTecni, new Object[] { inttri.getString("CODRUP") });
												if (codiceControllo == 6004) {
													if (!isStringaValorizzata(tecni.getString("NOMETEI")) || !isStringaValorizzata(tecni.getString("COGTEI"))) {
														this.addAvviso(resultValidateList, valore, pagina, messaggio);
													}
												} else {
													if (!isStringaValorizzata(tecni.getString("CFTEC"))	|| !UtilityFiscali.isValidCodiceFiscale(tecni.getString("CFTEC"))) {
														this.addAvviso(resultValidateList, valore, pagina, messaggio);
													}
												}
											} else {
												this.addAvviso(resultValidateList, valore, pagina, messaggio);
											}
										}
										break;
									case 6006:
										// Campo codice CUP (CUPPRG) non presente per forniture e servizi
										if ((inttri.getLong("FLAG_CUP") == null || inttri.getLong("FLAG_CUP").equals(2)) && !isStringaValorizzata(inttri.getString("CUPPRG"))) {
											this.addAvviso(resultValidateList, valore, pagina, messaggio);
										}
										break;
									}
								}
							 }
						 }
					 }
				 }

			 } catch (SQLException e) {
				 this.addAvviso(resultValidateList, "E", "Attenzione", "Si sono verificati degli errori durante il controllo dei dati.");
				 logger.error("Si sono verificati degli errori durante il controllo dei dati.", e);
			 }

		 } catch (GestoreException e) {
			 this.addAvviso(resultValidateList, "E", "Attenzione", "Errore nella funzione di controllo dei dati.");
			 logger.error("Errore nella funzione di controllo dei dati.", e);
		 }
		 finally {
			 infoValidazione.put("titolo", titolo);
		     infoValidazione.put("listaControlli", resultValidateList);

		     // Controllo errori e warning
		     int numeroErrori = 0;
		     int numeroWarning = 0;

		     if (!resultValidateList.isEmpty()) {
		        if (logger.isDebugEnabled()) {
		          logger.debug("Messaggi di errore durante la validazione dell'esportazione Xml del piano." );
		        }

		        for (int i = 0; i < resultValidateList.size(); i++) {
		          Object[] controllo = (Object[]) resultValidateList.get(i);
		          String tipo = (String) controllo[0];
		          if ("E".equals(tipo)) {
		        	  numeroErrori++;
		          }
		          if ("W".equals(tipo)) {
		        	  numeroWarning++;
		          }

		          if (logger.isDebugEnabled()) {
		            logger.debug(controllo[0] + " - " + controllo[1] + " - " + controllo[2] + " - " + controllo[3]);
		          }
		        }
		      }

		      infoValidazione.put("numeroErrori", numeroErrori);
		      infoValidazione.put("numeroWarning", numeroWarning);
		 }
		 return infoValidazione;
	 }

	 /**
		 * verifica se i dati presenti nel programma permettono la generazione corretta del file xml da esportare in HiProg.
		 * @param params parametri ingr esso
		 * @return risultato della validazione
		 * @throws JspException JspException
		 */
		 public HashMap<String, Object> validateXmlDocumentProgrammiTriennali(Object[] params)  throws JspException {
			 HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
			 List<Object> listaControlli = new Vector<Object>();
			 String titolo = null;
			 String messaggio = null;
			 try {

				 Long contri = new Long((String) params[1]); // codice programma triennale
				 String pagina , tabella;
				 try {
					 titolo = "Programma " + (String) this.sqlManager.getObject(sqlSelectIDPiatri, new Object[] {contri});
					 tabella = "PIATRI";
					 pagina = "Dati generali";
					 DataColumnContainer piatri = new DataColumnContainer(this.sqlManager, "PIATRI", sqlSelectPiatri, new Object[] { contri });
					 // Anno del programma annuale
					 Long annoInizioProgrammazione = piatri.getLong("ANNTRI");
					 if (annoInizioProgrammazione == null) {
						this.addCampoObbligatorio(listaControlli, tabella, "ANNRIF", pagina);
					 }

				     if (annoInizioProgrammazione != null) {
				        GregorianCalendar dataOdierna = new GregorianCalendar();
				        if (dataOdierna != null) {
				          if (annoInizioProgrammazione < dataOdierna.get(1)) {
				            messaggio = "L\'anno digitato &egrave; inferiore all\'anno in corso";
				            this.addAvviso(listaControlli, tabella, "ANNTRI", "E", pagina, messaggio);
				            }
				          }
				        }

				     String codiceFiscale = (String) this.sqlManager.getObject(sqlSelectCfisStazioneAppaltante, new Object[] { piatri.getString("CENINT") });
					 tabella = "UFFINT";
					 pagina = "Dati generali - stazione appaltante";
					 //controllo partita iva / codice fiscale stazione appaltante obbligatoria
					 if (!isStringaValorizzata(codiceFiscale)) {
						this.addCampoObbligatorio(listaControlli, tabella, "CFEIN", pagina);
					 }
					 //interventi triennali
					 List<?> interventi = this.sqlManager.getListHashMap(sqlSelectInttri, new Object[] { contri });
					 if (interventi != null && interventi.size() > 0) {
						 for (int i = 0; i < interventi.size(); i++) {

							 DataColumnContainer inttri = new DataColumnContainer(sqlManager, "INTTRI", sqlSelectInttri, new Object[] { contri });
							 tabella = "INTTRI";
							 inttri.setValoriFromMap((HashMap<?,?>) interventi.get(i), true);
							 pagina = "intervento n." + inttri.getLong("CONINT");

								//controllo descrizione intervento obbligatoria
								if (!isStringaValorizzata(inttri.getString("DESINT"))) {
									this.addCampoObbligatorio(listaControlli, tabella, "DESINT", pagina);
								}
								//controllo tipologia intervento obbligatoria
								if (!isStringaValorizzata(inttri.getString("SEZINT"))) {
									this.addCampoObbligatorio(listaControlli, tabella, "SEZINT", pagina);
								}
								//controllo categoria intervento obbligatoria
								if (!isStringaValorizzata(inttri.getString("CATINT"))) {
									this.addCampoObbligatorio(listaControlli, tabella, "CATINT", pagina);
								}
								//controllo annualità di riferimento obbligatoria
								if (inttri.getLong("ANNRIF") == null) {
									this.addCampoObbligatorio(listaControlli, tabella, "ANNRIF", pagina);
								}
								//controllo codice istat di esecuzione obbligatorio
								if (!isStringaValorizzata(inttri.getString("COMINT"))) {
									this.addCampoObbligatorio(listaControlli, tabella, "COMINT", pagina);
								}
								//se il programma è stato inserito nell'elenco annuale
								if (inttri.getString("ANNINT") != null && inttri.getString("ANNINT").equals("1"))
								{
									//controllo finalità del procedimento obbligatorio
									if (!isStringaValorizzata(inttri.getString("FIINTR"))) {
										this.addCampoObbligatorio(listaControlli, tabella, "FIINTR", pagina);
									}
									//controllo priorità del procedimento obbligatorio
									if (inttri.getLong("PRGINT") == null) {
										this.addCampoObbligatorio(listaControlli, tabella, "PRGINT", pagina);
									}
									//controllo stato del procedimento obbligatorio
									if (!isStringaValorizzata(inttri.getString("STAPRO"))) {
										this.addCampoObbligatorio(listaControlli, tabella, "STAPRO", pagina);
									}
									//controllo trimestre inizio procedimento obbligatorio
									Long trimestreInizioLavoro = inttri.getLong("TILINT");
									if (inttri.getLong("TILINT") == null) {
										this.addCampoObbligatorio(listaControlli, tabella, "TILINT", pagina);
									}
									//controllo anno inizio procedimento obbligatorio
									Long annoInizioLavoro = inttri.getLong("AILINT");
									if (inttri.getLong("AILINT") == null) {
										this.addCampoObbligatorio(listaControlli, tabella, "AILINT", pagina);
									}
									//controllo trimestre fine procedimento obbligatorio
									Long trimestreFineLavoro = inttri.getLong("TFLINT");
									if (inttri.getLong("TFLINT") == null) {
										this.addCampoObbligatorio(listaControlli, tabella, "TFLINT", pagina);
									}
									//controllo anno fine procedimento obbligatorio
									Long annoFineLavoro = inttri.getLong("AFLINT");
									if (inttri.getLong("AFLINT") == null) {
										this.addCampoObbligatorio(listaControlli, tabella, "AFLINT", pagina);
									}
									//verifico che le date di inizio e fine lavori siano corrette
									if (trimestreInizioLavoro != null && annoInizioLavoro != null && trimestreFineLavoro != null && annoFineLavoro != null)
									{
										if (annoFineLavoro < annoInizioProgrammazione || annoFineLavoro > annoInizioProgrammazione + 2)
										{
											messaggio = "L\'anno di fine lavori sfora dalla durata della programmazione";
								            this.addAvviso(listaControlli, tabella, "AFLINT", "E", pagina, messaggio);
										}
										if (annoInizioLavoro < annoInizioProgrammazione || annoInizioLavoro > annoInizioProgrammazione + 2)
										{
											messaggio = "L\'anno di inizio lavori sfora dalla durata della programmazione";
								            this.addAvviso(listaControlli, tabella, "AILINT", "E", pagina, messaggio);
										}
										if ((annoFineLavoro * 10) + trimestreFineLavoro < (annoInizioLavoro * 10) + trimestreInizioLavoro)
										{
											messaggio = "La data di inizio lavori &egrave; maggiore della data di fine";
								            this.addAvviso(listaControlli, tabella, "AILINT", "E", pagina, messaggio);
										}
									}
									//DATI TECNICO
									//controllo responsabile del procedimento obbligatorio
									if (!isStringaValorizzata(inttri.getString("CODRUP"))) {
										this.addCampoObbligatorio(listaControlli, tabella, "CODRUP", pagina);
									} else {
										List<?> tecnico = this.sqlManager.getListHashMap(sqlSelectTecni, new Object[] { inttri.getString("CODRUP")});
										if (tecnico.size() == 1) {
											tabella = "TECNI";
											DataColumnContainer tecni = new DataColumnContainer(this.sqlManager, "TECNI",	sqlSelectTecni, new Object[] { inttri.getString("CODRUP") });
											//controllo nome responsabile obbligatorio
											if (!isStringaValorizzata(tecni.getString("NOMETEI"))) {
												this.addCampoObbligatorio(listaControlli, tabella, "NOMETEI", pagina);
											}
											//controllo cognome responsabile obbligatorio
											if (!isStringaValorizzata(tecni.getString("COGTEI"))) {
												this.addCampoObbligatorio(listaControlli, tabella, "COGTEI", pagina);
											}
											//controllo validità codice fiscale
											if (isStringaValorizzata(tecni.getString("CFTEC"))) {
												if (!UtilityFiscali.isValidCodiceFiscale(tecni.getString("CFTEC"))) {
													messaggio = "Codice fiscale RUP errato";
													this.addAvviso(listaControlli, tabella, "CFTEC", "E", pagina, messaggio);
												}
											}
										} else {
											this.addCampoObbligatorio(listaControlli, tabella, "CODRUP", pagina);
										}
									}
									//eventuali beni immobili
									tabella = "IMMTRAI";
									List<?> immobili = this.sqlManager.getListHashMap(sqlSelectImmtrai, new Object[] { contri , inttri.getLong("CONINT")});
									if (immobili != null && immobili.size() > 0) {
										for (int j = 0; j < immobili.size(); j++) {
											DataColumnContainer immtrai = new DataColumnContainer(sqlManager, "IMMTRAI", sqlSelectImmtrai, new Object[] { contri, inttri.getLong("CONINT") });
											immtrai.setValoriFromMap((HashMap<?,?>) immobili.get(j), true);
											pagina = "immobile n." + immtrai.getLong("NUMIMM") + " nell'intervento n." + inttri.getLong("CONINT");
											//controllo descrizione immobile obbligatoria
											if (!isStringaValorizzata(immtrai.getString("DESIMM"))) {
												this.addCampoObbligatorio(listaControlli, tabella, "DESIMM", pagina);
											}
										}
									}
								}
						 }
					 }

				 } catch (SQLException e) {
				      throw new GestoreException(
				          "Errore nella lettura delle informazioni degli interventi",
				          "interventi", e);
				    }

				 infoValidazione.put("titolo", titolo);
			     infoValidazione.put("listaControlli", listaControlli);

			     // Controllo errori e warning
			     int numeroErrori = 0;
			     int numeroWarning = 0;

			     if (!listaControlli.isEmpty()) {
			        if (logger.isDebugEnabled()) {
			          logger.debug("Messaggi di errore durante la validazione dell'esportazione Xml del piano = " + contri);
			        }

			        for (int i = 0; i < listaControlli.size(); i++) {
			          Object[] controllo = (Object[]) listaControlli.get(i);
			          String tipo = (String) controllo[0];
			          if ("E".equals(tipo)) {
			        	  numeroErrori++;
			          }
			          if ("W".equals(tipo)) {
			        	  numeroWarning++;
			          }

			          if (logger.isDebugEnabled()) {
			            logger.debug(controllo[0] + " - " + controllo[1] + " - " + controllo[2] + " - " + controllo[3]);
			          }
			        }
			      }

			      infoValidazione.put("numeroErrori", numeroErrori);
			      infoValidazione.put("numeroWarning", numeroWarning);
			 } catch (GestoreException e) {
			      throw new JspException("Errore nella funzione di controllo dei dati", e);
			    }
			 return infoValidazione;
		 }

	 /**
	   * Aggiunge un messaggio bloccante alla listaControlli.
	   *
	   * @param listaControlli lista Controlli
	   * @param nomeTabella nome Tabella
	   * @param nomeCampo nome Campo
	   * @param pagina pagina
	   */
	  private void addCampoObbligatorio(final List<Object> listaControlli,
			  final String nomeTabella, final String nomeCampo, final String pagina) {
	    if (logger.isDebugEnabled()) {
	      logger.debug("addCampoObbligatorio: inizio metodo");
	    }

	    listaControlli.add(((new Object[] { "E", pagina,
	        this.getDescrizioneCampo(nomeTabella, nomeCampo),
	        "Il campo &egrave; obbligatorio" })));

	    if (logger.isDebugEnabled()) {
	      logger.debug("addCampoObbligatorio: fine metodo");
	    }
	  }

	  /**
	   * Aggiunge un messaggio di avviso alla listaControlli.
	   * @param listaControlli lista Controlli
	   * @param nomeTabella nome Tabella
	   * @param nomeCampo nome Campo
	   * @param tipo tipo
	   * @param pagina pagina
	   * @param messaggio messaggio
	   */
	  private void addAvviso(final List<Object> listaControlli, final String nomeTabella,
			  final String nomeCampo, final String tipo, final String pagina, final String messaggio) {
	    if (logger.isDebugEnabled()) {
	    	logger.debug("addAvviso: inizio metodo");
	    }

	    listaControlli.add(((new Object[] { tipo, pagina,
	        this.getDescrizioneCampo(nomeTabella, nomeCampo), messaggio })));

	    if (logger.isDebugEnabled()) {
	    	logger.debug("addAvviso: fine metodo");
	    }
	  }
	  private void addAvviso(final List<Object> listaControlli, final String tipo, final String pagina, final String messaggio) {
	    if (logger.isDebugEnabled()) {
	    	logger.debug("addAvviso: inizio metodo");
	    }

	    listaControlli.add(((new Object[] { tipo, pagina, "", messaggio })));

	    if (logger.isDebugEnabled()) {
	    	logger.debug("addAvviso: fine metodo");
	    }
	  }

	  /**
	   * Restituisce la descrizione WEB del campo.
	   *
	   * @param nomeTabella nome Tabella
	   * @param nomeCampo nome Campo
	   * @return descrizione campo
	   */
	  private String getDescrizioneCampo(final String nomeTabella, final String nomeCampo) {
	    if (logger.isDebugEnabled()) {
	      logger.debug("getDescrizioneCampo: inizio metodo");
	    }

	    String descrizione = "";
	    Campo c = DizionarioCampi.getInstance().getCampoByNomeFisico(
	        nomeTabella + "." + nomeCampo);
	    if (c != null) {
	    	descrizione = c.getDescrizioneWEB();
	    }

	    if (logger.isDebugEnabled()) {
	      logger.debug("getDescrizioneCampo: fine metodo");
	    }
	    return descrizione;
	  }

	  /**
	   * Valorizzazione di una stringa.
	   *
	   * @param str stringa
	   * @return Ritorna true se str e' null oppure, se diversa da null, se
	   *         str.trim() ha un numero di caratteri maggiore di zero
	   */
	  private boolean isStringaValorizzata(final String str) {
	    boolean result = false;

	    if (str != null && str.trim().length() > 0) {
	      result = true;
	    }

	    return result;
	  }

	  /**
	   * Importazione documento XML HiProg3 nel database.
	   *
	   * @param doc documento XML HiProg3.
	   * @return Ritorna true se l'inserimento nel database va a buon fine.
	   *         false altrimenti.
	   */
	  public boolean importHiProg3(final PtDocument doc, HttpServletRequest request) throws GestoreException {
	    boolean result = false;
	    boolean withoutError = true;
	    TransactionStatus transazione = null;
	    try {
	    	String codein = request.getSession().getAttribute("uffint").toString();

	    	ArrayList<Object> validationErrors = new ArrayList<Object>();
	    	//Map<String,String> namespaceMap = new HashMap<String,String>();
	        XmlOptions validationOptions = new XmlOptions();
	        validationOptions.setErrorListener(validationErrors);
	        //namespaceMap.put("xmlns", "xmlbeans.hiprog.web.pt.sil.eldasoft.it");
	        //namespaceMap.put("", "");
	        //validationOptions.setLoadSubstituteNamespaces(null);
	        boolean isValid = doc.validate(validationOptions);

	        if (!isValid) {
	          String listaErroriValidazione = "";
	          Iterator<?> iter = validationErrors.iterator();
	          while (iter.hasNext()) {
	            listaErroriValidazione += iter.next() + "\n";
	          }
	        }


	    	/*XmlOptions options = new XmlOptions();
	    	Map<String,String> namespaceMap2 = new HashMap<String,String>();
	    	//namespaceMap.put("", "xmlbeans.hiprog.web.pt.sil.eldasoft.it");
	    	namespaceMap2.put("xmlns", "");
	    	options = options.setLoadAdditionalNamespaces(namespaceMap2); */

	    	if (!doc.validate()) {
	    		throw new GestoreException("Documento xml non valido","importXML.validate");
	    	} else {
	    		transazione = this.sqlManager.startTransaction();
	    		//inserimento programma
	    		Long contri = insertPiatriHiProg3(doc, codein);
	    		//inserimento interventi
	    		withoutError = insertInttriHiProg3(doc, contri, codein);
	    		result = true;
	    	}
	    } catch (SQLException e) {
	    	throw new GestoreException("Non e' possibile importare il file XML allegato", "importXML.generic", e);
	    } finally {
	        if (transazione != null) {
	          try {
	            if (result)
	              sqlManager.commitTransaction(transazione);
	            else
	              sqlManager.rollbackTransaction(transazione);
	            } catch (SQLException e1) {
	            }
	            if (!withoutError) {
	                 UtilityStruts.addMessage(request, "warning", "warnings.gestoreException.*.importXML.inttri.alert", null);
	            }
	        }
	    }
	    return result;
	  }

	  /**
	   * Inserimento nella tabella PIATRI a partire dall'XML HiProg3.
	   *
	   * @param doc documento XML HiProg3.
	   * @param codein codice stazione appaltante.
	   */
	private Long insertPiatriHiProg3(final PtDocument doc, String codein)
			throws GestoreException {
		String query = "";
		String fields = "";
		Long contri;
		ArrayList<Object> values = new ArrayList<Object>();
		try {
			Pt programma = doc.getPt();
			String cenint = codein;
			// Separatore decimale
		    String sys_dec_separator = programma.getSetupInfo().getSysDecSeparator();
			//ricavo il codice del programma
			contri = (Long) sqlManager.getObject(
					"select max(contri) from piatri", new Object[] {});
			if (contri == null) {
				contri = new Long(1);
			} else {
				contri = contri + 1;
			}
			//dati generali
			//verifico se esiste è previsto il tipo programma 3 altrimenti il tipo di programma da importare è 1
			Long existTiprog3 = (Long) this.sqlManager.getObject("select count(*) from TAB1 where tab1cod='W9002' and tab1tip=3", new Object[] { });
			Long tiprog = new Long(1);
		    if (existTiprog3 > 0) {
		    	tiprog = new Long(3);
		    }
			//String id = "LP" + programma.getAmmCodfisc() + programma.getAnnoElencoAnnuale();
			String id = GestorePIATRI.gestoreIdPiatri(sqlManager, cenint, tiprog, programma.getAnnoElencoAnnuale());
			String bretri = "Programma triennale lavori pubblici del triennio " + programma.getTriennio();
			fields = "CONTRI, ID, BRETRI, TIPROG, CENINT, STATRI, ";
			values.add(contri);
			values.add(id);
			values.add(bretri);
			values.add(tiprog);
			values.add(cenint);
			values.add(1);

	    	fields += "ANNTRI, ";
	    	values.add(programma.getAnnoElencoAnnuale());

	    	//quadro risorse
	    	QuadroRisorse quadroRisorse = programma.getQuadroRisorse();
	    	fields += "DV1TRI, DV2TRI, DV3TRI, ";
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpVincolato1()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpVincolato2()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpVincolato3()));
	    	fields += "MU1TRI, MU2TRI, MU3TRI, ";
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpMutuo1()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpMutuo2()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpMutuo3()));
	    	fields += "PR1TRI, PR2TRI, PR3TRI, ";
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati1()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati2()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati3()));
	    	fields += "IM1TRI, IM2TRI, IM3TRI, ";
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpTrasfImmob1()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpTrasfImmob2()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpTrasfImmob3()));
	    	fields += "BI1TRI, BI2TRI, BI3TRI, ";
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpStanzBilancio1()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpStanzBilancio2()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpStanzBilancio3()));
	    	fields += "AL1TRI, AL2TRI, AL3TRI, ";
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpAltro1()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpAltro2()));
	    	values.add(getImporti(sys_dec_separator, quadroRisorse.getImpAltro3()));
	    	fields += "TO1TRI, TO2TRI, TO3TRI, ";
	    	values.add((getImporti(sys_dec_separator, quadroRisorse.getImp1Totale()) + getImporti(sys_dec_separator, quadroRisorse.getImpTrasfImmob1()) - getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati1())));
	    	values.add((getImporti(sys_dec_separator, quadroRisorse.getImp2Totale()) + getImporti(sys_dec_separator, quadroRisorse.getImpTrasfImmob2()) - getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati2())));
	    	values.add((getImporti(sys_dec_separator, quadroRisorse.getImp3Totale()) + getImporti(sys_dec_separator, quadroRisorse.getImpTrasfImmob3()) - getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati3())));

	    	fields = fields.substring(0, fields.length()-2);
	    	query = "INSERT INTO PIATRI (" + fields + ") VALUES (";
	    	for (int i =0 ; i < values.size(); i++)
	    		query += "?,";
	    	query = query.substring(0, query.length()-1) + ")";
	    	//inserisco l'intervento
			this.sqlManager.update(query, values.toArray());

		} catch (GestoreException e) {
			throw new GestoreException(e.getMessage(),e.getCodice(), e);
		} catch (Exception e) {
			throw new GestoreException("Errore di inserimento nella tabella PIATRI","importXML.piatri.insert", e);
		}
		return contri;
	}

	 /**
	   * Inserimento nella tabella INNTRI degli interventi a partire dall'XML HiProg3.
	   *
	   * @param doc documento XML HiProg3.
	   * @param codein codice stazione appaltante.
	   * @return true se l'inserimento è completato senza errori, false se l'inserimento è completato con degli errori
	   */
	private boolean insertInttriHiProg3(final PtDocument doc, Long contri, String codein)
			throws GestoreException {
		boolean result= true;
		String query = "";
		String fields = "";
		NumberFormat formatter = new DecimalFormat("00");
		ArrayList<Object> values = new ArrayList<Object>();
		try {
			Pt programma = doc.getPt();
			// Separatore decimale
		    String sys_dec_separator = programma.getSetupInfo().getSysDecSeparator();
			//interventi triennali
			InterventiTriennali interventiTriennali = programma.getInterventiTriennali();
			for (int i = 0; i < interventiTriennali.sizeOfInterventoArray(); i++) {
				values.clear();
				Intervento intervento = interventiTriennali.getInterventoArray(i);
				fields = "CONTRI, ";
				values.add(contri);
				fields += "CONINT, ";
				values.add(intervento.getNProgressivo());
				fields += "CODINT, ";
				values.add(intervento.getCodIntervAmm());
				fields += "TIPOIN, ";
				values.add("L");
				fields += "MANTRI, ";
				values.add("1");
				fields += "ANNRIF, ";
				values.add(intervento.getAnnoIntervento() - programma.getAnnoElencoAnnuale() + 1);
				fields += "COMINT, ";
				values.add(intervento.getLocalizzazioneCodIstat());
				fields += "DESINT, ";
				values.add(intervento.getDescrizioneInterv());
				fields += "SEZINT, ";
				values.add(formatter.format(Integer.parseInt(intervento.getTipoOpera())));

				List< ? > categoria = this.sqlManager.getVector("select tabcod1, tabcod2 from tabsche where tabcod = 'S2006' and tabcod3 = ?",
				          new Object[] { intervento.getIdCategoria() });
				if (categoria != null && categoria.size() > 0) {
					fields += "INTERV, ";
					values.add(SqlManager.getValueFromVectorParam(categoria, 0).getValue());
					fields += "CATINT, ";
					values.add(formatter.format(Integer.parseInt(SqlManager.getValueFromVectorParam(categoria, 1).getStringValue())));
				}

				fields += "DI1INT, DI2INT, DI3INT, DITINT, TOTINT, ";
		    	values.add(getImporti(sys_dec_separator, intervento.getCostoStimato1()));
		    	values.add(getImporti(sys_dec_separator, intervento.getCostoStimato2()));
		    	values.add(getImporti(sys_dec_separator, intervento.getCostoStimato3()));
		    	if (intervento.getCostoStimatoTot()!=null && !intervento.getCostoStimatoTot().equals("") && !intervento.getCostoStimatoTot().equals("0")) {
		    		values.add(getImporti(sys_dec_separator, intervento.getCostoStimatoTot()));
			    	values.add(getImporti(sys_dec_separator, intervento.getCostoStimatoTot()));
		    	} else {
		    		values.add(getImporti(sys_dec_separator, intervento.getCostoStimato1()) + getImporti(sys_dec_separator, intervento.getCostoStimato2()) + getImporti(sys_dec_separator, intervento.getCostoStimato3()));
			    	values.add(getImporti(sys_dec_separator, intervento.getCostoStimato1()) + getImporti(sys_dec_separator, intervento.getCostoStimato2()) + getImporti(sys_dec_separator, intervento.getCostoStimato3()));
		    	}

		    	fields += "TCPINT, ";
				values.add(formatter.format(Integer.parseInt(intervento.getTipoApportoCapitale())));
				fields += "ICPINT, ";
		    	values.add(getImporti(sys_dec_separator, intervento.getImpCapitalePrivato()));
				//fields += "PR1TRI, ";
		    	//values.add(getImporti(sys_dec_separator, intervento.getImpCapitalePrivato()));
		    	fields += "ANNINT, ";
		    	if (intervento.getHasIntAnnuale()!=null && intervento.getHasIntAnnuale().equalsIgnoreCase("Y")) {
		    		if (intervento.getInterventoAnnualeArray().length > 0) {
		    			values.add("1");
			    		InterventoAnnuale annuale = intervento.getInterventoAnnualeArray(0);
			    		fields += "URCINT, ";
						values.add((annuale.getConfUrbanistica()!=null && annuale.getConfUrbanistica().equalsIgnoreCase("Y"))?"1":"2");
						fields += "APCINT, ";
						values.add((annuale.getConfAmbientale()!=null && annuale.getConfAmbientale().equalsIgnoreCase("Y"))?"1":"2");
						String stapro = (String)sqlManager.getObject("select tabcod1 from tabsche where tabcod = 'S2017' and tabcod3 = ? ", new Object[] {annuale.getStatoProgettazione()});
						if (stapro != null) {
							fields += "STAPRO, ";
							values.add(stapro);
						}
						String fiintr = (String)sqlManager.getObject("select tabcod1 from tabsche where tabcod = 'S2016' and tabcod3 = ? ", new Object[] {annuale.getFinalita()});
						if (fiintr != null) {
							fields += "FIINTR, ";
							values.add(fiintr);
						}
						fields += "PRGINT, ";
						values.add(annuale.getPriorita());
						fields += "TILINT, ";
						values.add(new Long(annuale.getTrimInizioLav().substring(0, 1)));
						fields += "AILINT, ";
						values.add(annuale.getAnnoInizioLav());
						fields += "TFLINT, ";
						values.add(new Long(annuale.getTrimFineLav().substring(0, 1)));
						fields += "AFLINT, ";
						values.add(annuale.getAnnoFineLav());
						fields += "CODRUP, ";
						values.add(gestioneTECNI(annuale.getCognomeRproc(), annuale.getNomeRproc(), annuale.getCfRproc(), codein));
		    		} else {
		    			result = false;
			    		values.add("2");
			    	}
		    	} else {
		    		values.add("2");
		    	}
		    	fields += "TIPINT, ";
				values.add(new Long(1));
		    	fields = fields.substring(0, fields.length()-2);
		    	query = "INSERT INTO INTTRI (" + fields + ") VALUES (";
		    	for (int j =0 ; j < values.size(); j++)
		    		query += "?,";
		    	query = query.substring(0, query.length()-1) + ")";
		    	//inserisco l'intervento
				this.sqlManager.update(query, values.toArray());
				//beni immobili
				if (intervento.getBeniImmobiliArray().length > 0) {
					BeniImmobili beniImmobili = intervento.getBeniImmobiliArray(0);
					for (int y = 0; y < beniImmobili.sizeOfBeneArray(); y++) {
						values.clear();
						Bene bene = beniImmobili.getBeneArray(y);
						Long numimm = new Long(y + 1);
						fields = "CONTRI, CONINT, NUMIMM, ";
						values.add(contri);
						values.add(intervento.getNProgressivo());
						values.add(numimm);
						fields += "DESIMM, ";
						values.add(bene.getDescImmobile());

						if (bene.getPienaProprieta().equalsIgnoreCase("Y")) {
							fields += "PROIMM, ";
							values.add(new Long(1));
						} else if (bene.getSoloSuperficie().equalsIgnoreCase("Y")) {
							fields += "PROIMM, ";
							values.add(new Long(2));
						}
						fields += "VALIMM, ";
				    	values.add(getImporti(sys_dec_separator, bene.getValoreStimato()));
						fields += "ANNIMM, ";
						values.add(bene.getAnnualita());

						fields = fields.substring(0, fields.length()-2);
				    	query = "INSERT INTO IMMTRAI (" + fields + ") VALUES (";
				    	for (int j =0 ; j < values.size(); j++)
				    		query += "?,";
				    	query = query.substring(0, query.length()-1) + ")";
				    	//inserisco il bene
						this.sqlManager.update(query, values.toArray());
					}
				}

			}
			this.sqlManager.update("UPDATE INTTRI SET PRGINT = null WHERE PRGINT=0 AND CONTRI = " + contri, null);
		} catch (Exception e) {
			throw new GestoreException("Errore di inserimento nella tabella INTTRI","importXML.inttri.insert", e);
		}
		return result;
	}

		/**
		   * ATTENZIONE - Uguale al metodo in ImportExportXMLManager -
		   * Restituisce TECNI.CODTEC del tecnico
		   * Se il tecnico non esiste provvede ad inserirlo
		   * @param cognome_rproc
		   * @param nome_rproc
		   * @param cf_rproc
		   * @param cenint
		   * @return codice tecnico
		   * @throws SQLException
		   */
	private String gestioneTECNI(String cognome_rproc, String nome_rproc, String cf_rproc, String cenint)
    throws GestoreException {

  String codtec = null;

  try {
  	codtec = (String)this.sqlManager.getObject("select codtec from tecni where UPPER(cftec) = ? and cgentei = ?", new Object[] {cf_rproc.toUpperCase(), cenint});

	    if (codtec == null || (codtec != null && "".equals(codtec))) {
	    	// Il tecnico non esiste si procede all'inserimento nell'archivio dei
	    	// tecnici
	        // Calcolo delle chiave di TECNI
	        codtec = geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
	        String sqlTECNI = "insert into tecni (codtec, " // 1
		          + "cogtei, " // 2
		          + "nometei, " // 3
		          + "nomtec, " // 4
		          + "cftec, " // 5
		          + "cgentei) " // 6
		          + "values "
		          + "(?,?,?,?,?,?)";
	        this.sqlManager.update(sqlTECNI, new Object[] {codtec, cognome_rproc, nome_rproc, cognome_rproc + " " + nome_rproc, cf_rproc, cenint});
	    }
  }  catch (SQLException e) {
		throw new GestoreException("Errore di inserimento nella tabella TECNI.", null, e);
	}
  return codtec;
}


	  /**
	   * Verifica se è settato nelle l'indirizzo del file delle condizioni d'uso,
	   * se valorizzato, verifica che il file esista e che non sia vuoto.
	   * @param context
	   * @return
	   */
	  public String recuperaNomeFileCondizioniDUso(ServletContext context) {
	    String indirizzoFileRelativo = ConfigManager.getValore("it.eldasoft.registrazione.fileCondizioniDUso");
	    String indirizzoFile = null;
	    File modulo = null;
	    if (indirizzoFileRelativo != null && !indirizzoFileRelativo.trim().equals("")) {
	      String tomcatHome = context.getRealPath(File.separator);
	      indirizzoFileRelativo = indirizzoFileRelativo.trim();
	      modulo = new File(tomcatHome + indirizzoFileRelativo);
	      if (modulo != null && modulo.length() > 0) {
	        indirizzoFile = indirizzoFileRelativo;
	      }
	    }
	    return indirizzoFile;
	  }

	  /**
	   *
	   * @param contri codice programma
	   * @param request
	   * @return int 0-utente disabilitato, 1-programma pubblicato, -1 errore durante la procedura di pubblicazione, -2 programma per SA e anno già pubblicato, -3 necessaria richiesta di subentro
	   * @throws GestoreException GestoreException
	   */
	  public int pubblicaProgrammaOriginale(Long contri, Long idFlusso, StringBuilder subentro, HttpServletRequest request) throws GestoreException {

		  int result = 0;
		  String SQL_EXIST_USER = "Select syscon from pubblicazione.usrsys where sysnom = ?";
		  String SQL_INSERT_USER = "Insert into pubblicazione.usrsys(syscon, sysute, sysnom, syspwd, sysdat, sysab1, sysab2, sysab3, " +
            "sysliv, syspwbou, codimp, impimp, sysabg, syslig, sysabc, syslic, " +
            "sysabu, codtec, abilap, syspri, codtei, uducla1, flag_ldap, dn, " +
            "syscongrp, sysdisab, email, sysscad, sysuffapp, emailpec) Select syscon, sysute, sysnom, syspwd, sysdat, sysab1, sysab2, sysab3, " +
            "sysliv, syspwbou, codimp, impimp, sysabg, syslig, sysabc, syslic, " +
            "sysabu, codtec, abilap, syspri, codtei, uducla1, flag_ldap, dn, " +
            "syscongrp, sysdisab, email, sysscad, sysuffapp, emailpec from usrsys where syscon = ?";
		  String SQL_INSERT_USR_EIN = "Insert into pubblicazione.usr_ein (syscon, codein) values(?, ?)";
		  String SQL_EXIST_USR_EIN_SYSCON = "Select syscon from pubblicazione.usr_ein where codein = ? and syscon = ?";
		  String SQL_EXIST_USR_EIN = "Select syscon from pubblicazione.usr_ein where codein = ?";
		  String SQL_DELETE_USR_EIN = "Delete from pubblicazione.usr_ein where syscon = ?";
		  String SQL_DISABLE_USER = "Update pubblicazione.usrsys set sysdisab = 1 where syscon = ?";
		  String SQL_IS_DISABLED_USER = "Select sysdisab from pubblicazione.usrsys where sysnom = ?";
		  String SQL_DATA_USER = "Select sysute from pubblicazione.usrsys where syscon = ?";
		  String SQL_EXIST_SA = "Select codein from pubblicazione.uffint where cfein = ? and idammin = ?";
		  //String SQL_EMAIL_USER = "Select email from pubblicazione.usrsys where sysnom = ?";
		  String SQL_ANNO_SA = "Select anntri, cenint from piatri where contri = ?";
		  String SQL_SA_IDAMMIN = "Select cfein, idammin from uffint where codein = ?";
		  String SQL_EXIST_PROGRAMMA_SA = "Select count(*) from pubblicazione.piatri where contri <> ? and anntri = ? and cenint in (select codein from pubblicazione.uffint where cfein = ? and idammin = ?) ";
		  String SQL_INSERT_RESPRO = "Insert into pubblicazione.tecni(codtec, cogtei, nometei, nomtec, inctec, dintec, dfitec, indtec, " +
		  "ncitec, loctec, protec, captec, teltec, faxtec, cftec, doftec, " +
		  "albtec, cnatec, dnatec, naztei, sextei, cgentei, dgentei, nottec, " +
		  "codstu, cittec, ematec, datalb, proalb, isccol, iscco1, telcel, " +
		  "mgsflg, webusr, webpwd, pivatec, syscon, datmod, emailpec, pronas, " +
		  "ema2tec, tipalb, tcapre, ncapre) Select codtec, cogtei, nometei, nomtec, inctec, dintec, dfitec, indtec, " +
		  "ncitec, loctec, protec, captec, teltec, faxtec, cftec, doftec, " +
		  "albtec, cnatec, dnatec, naztei, sextei, cgentei, dgentei, nottec, " +
		  "codstu, cittec, ematec, datalb, proalb, isccol, iscco1, telcel, " +
		  "mgsflg, webusr, webpwd, pivatec, syscon, datmod, emailpec, pronas, " +
		  "ema2tec, tipalb, tcapre, ncapre from tecni where codtec in (select respro from piatri where respro is not null and contri = ?) and codtec not in (select codtec from pubblicazione.tecni)";
		  String SQL_INSERT_RUP = "Insert into pubblicazione.tecni(codtec, cogtei, nometei, nomtec, inctec, dintec, dfitec, indtec, " +
		  "ncitec, loctec, protec, captec, teltec, faxtec, cftec, doftec, " +
		  "albtec, cnatec, dnatec, naztei, sextei, cgentei, dgentei, nottec, " +
		  "codstu, cittec, ematec, datalb, proalb, isccol, iscco1, telcel, " +
		  "mgsflg, webusr, webpwd, pivatec, syscon, datmod, emailpec, pronas, " +
		  "ema2tec, tipalb, tcapre, ncapre) Select codtec, cogtei, nometei, nomtec, inctec, dintec, dfitec, indtec, " +
		  "ncitec, loctec, protec, captec, teltec, faxtec, cftec, doftec, " +
		  "albtec, cnatec, dnatec, naztei, sextei, cgentei, dgentei, nottec, " +
		  "codstu, cittec, ematec, datalb, proalb, isccol, iscco1, telcel, " +
		  "mgsflg, webusr, webpwd, pivatec, syscon, datmod, emailpec, pronas, " +
		  "ema2tec, tipalb, tcapre, ncapre from tecni where codtec in (select codrup from inttri where codrup is not null and contri = ?) and codtec not in (select codtec from pubblicazione.tecni)";
		  String SQL_INSERT_SA = "Insert into pubblicazione.uffint(codein, nomein, viaein, nciein, citein, proein, capein, telein, " +
		  "faxein, cfein, dofein, lnaein, dnaein, ivaein, notein, codsta, " +
		  "natgiu, tipoin, numicc, daticc, proicc, codcit, codnaz, emaiin, " +
		  "codres, nomres, resini, resfin, prouff, indweb, profco, idammin, " +
		  "userid, pronas, emailpec, emai2in) Select codein, nomein, viaein, nciein, citein, proein, capein, telein, " +
		  "faxein, cfein, dofein, lnaein, dnaein, ivaein, notein, codsta, " +
		  "natgiu, tipoin, numicc, daticc, proicc, codcit, codnaz, emaiin, " +
		  "codres, nomres, resini, resfin, prouff, indweb, profco, idammin, " +
		  "userid, pronas, emailpec, emai2in from uffint where codein = ?";
		  String SQL_UPDATE_SA = "Update pubblicazione.uffint a set nomein = b.nomein, viaein = b.viaein, nciein = b.nciein, " +
		  "citein = b.citein, proein = b.proein, capein = b.capein, ivaein = b.ivaein,  natgiu = b.natgiu,  " +
		  "codcit = b.codcit, codnaz = b.codnaz, emaiin = b.emaiin, indweb = b.indweb , emailpec = b.emailpec, idammin = b.idammin " +
		  "from uffint b where a.codein=b.codein and b.codein = ?";
		  String SQL_INSERT_PIATRI = "Insert into pubblicazione.piatri(contri, id, anntri, bretri, esttri, imptri, dattri, numtri, puitri," +
		  "puftri, taptri, daptri, olvtri, ciptri, puetri, dv1tri, dv2tri, " +
		  "dv3tri, mu1tri, mu2tri, mu3tri, pr1tri, pr2tri, pr3tri, im1tri, " +
		  "im2tri, im3tri, bi1tri, bi2tri, bi3tri, al1tri, al2tri, al3tri, " +
		  "ac1tri, ac2tri, ac3tri, to1tri, to2tri, to3tri, at1tri, at2tri, " +
		  "at3tri, notsche1, notsche3, notsche2b, approv, cenint, respro, " +
		  "tadozi, nadozi, dadozi, tiprog, rg1tri, imprfs, file_allegato, " +
		  "impacc, statri, naptri, notsche2, notsche4, pubblica) Select contri, id, anntri, bretri, esttri, imptri, dattri, numtri, puitri," +
		  "puftri, taptri, daptri, olvtri, ciptri, puetri, dv1tri, dv2tri, " +
		  "dv3tri, mu1tri, mu2tri, mu3tri, pr1tri, pr2tri, pr3tri, im1tri, " +
		  "im2tri, im3tri, bi1tri, bi2tri, bi3tri, al1tri, al2tri, al3tri, " +
		  "ac1tri, ac2tri, ac3tri, to1tri, to2tri, to3tri, at1tri, at2tri, " +
		  "at3tri, notsche1, notsche3, notsche2b, approv, ?, respro, " +
		  "tadozi, nadozi, dadozi, tiprog, rg1tri, imprfs, file_allegato, " +
		  "impacc, 2, naptri, notsche2, notsche4, pubblica from piatri where contri = ?";
		  String SQL_INSERT_INTTRI = "Insert into pubblicazione.inttri(contri, conint, codint, proint, sezint, interv, catint, setint, " +
		  "claint, desint, ubiint, finint, im1int, im2int, im3int, totint, " +
		  "fatint, datint, affint, estint, temint, prgint, risint, ambint, " +
		  "sodint, prsint, pruint, priass, cidint, priint, comint, di1int, " +
		  "di2int, di3int, annint, annrif, urcint, apcint, legint, altint, " +
		  "pprint, pdeint, pesint, garint, cntint, cnsint, lavint, colint, " +
		  "stiint, utiint, icpint, dcpint, iciint, dciint, manint, nloint, " +
		  "tloint, prpint, cefint, urainttab, apainttab, cupprg, cupmst, " +
		  "tcpint, fiintr, cuiint, ailint, tilint, aflint, tflint, codrup, " +
		  "stapro, isnuovo, isarchi, intnote, dv1tri, dv2tri, dv3tri, mu1tri, " +
		  "mu2tri, mu3tri, pr1tri, pr2tri, pr3tri, im1tri, im2tri, im3tri, " +
		  "bi1tri, bi2tri, bi3tri, al1tri, al2tri, al3tri, ac1tri, ac2tri, " +
		  "ac3tri, at1tri, at2tri, at3tri, rg1tri, imprfs, tipoin, mesein, " +
		  "norrif, strupr, mantri, nuts, codcpv, ditint, tipint, nprogint, flag_cup) Select contri, conint, codint, proint, sezint, interv, catint, setint, " +
		  "claint, desint, ubiint, finint, im1int, im2int, im3int, totint, " +
		  "fatint, datint, affint, estint, temint, prgint, risint, ambint, " +
		  "sodint, prsint, pruint, priass, cidint, priint, comint, di1int, " +
		  "di2int, di3int, annint, annrif, urcint, apcint, legint, altint, " +
		  "pprint, pdeint, pesint, garint, cntint, cnsint, lavint, colint, " +
		  "stiint, utiint, icpint, dcpint, iciint, dciint, manint, nloint, " +
		  "tloint, prpint, cefint, urainttab, apainttab, cupprg, cupmst, " +
		  "tcpint, fiintr, cuiint, ailint, tilint, aflint, tflint, codrup, " +
		  "stapro, isnuovo, isarchi, intnote, dv1tri, dv2tri, dv3tri, mu1tri, " +
		  "mu2tri, mu3tri, pr1tri, pr2tri, pr3tri, im1tri, im2tri, im3tri, " +
		  "bi1tri, bi2tri, bi3tri, al1tri, al2tri, al3tri, ac1tri, ac2tri, " +
		  "ac3tri, at1tri, at2tri, at3tri, rg1tri, imprfs, tipoin, mesein, " +
		  "norrif, strupr, mantri, nuts, codcpv, ditint, tipint, nprogint, flag_cup from inttri where contri = ?";
		  String SQL_INSERT_IMMTRAI = "Insert into pubblicazione.immtrai(contri, conint, numimm, desimm, proimm, annimm, valimm, sezimm1, " +
		  "fogimm1, parimm1, subimm1, sezimm2, fogimm2, parimm2, subimm2, " +
		  "sezimm3, fogimm3, parimm3, subimm3) Select contri, conint, numimm, desimm, proimm, annimm, valimm, sezimm1, " +
		  "fogimm1, parimm1, subimm1, sezimm2, fogimm2, parimm2, subimm2, " +
		  "sezimm3, fogimm3, parimm3, subimm3 from immtrai where contri = ?";
		  String SQL_INSERT_ECOTRI = "Insert into pubblicazione.ecotri(contri, coneco, descri, cupprg, stima) Select contri, coneco, descri, cupprg, stima from ecotri where contri = ?";
		  String SQL_INSERT_PTFLUSSI = "Insert into pubblicazione.ptflussi(idflusso, key01, autore, tipinv, datinv, noteinvio, verxml, cfsa, xml, file_allegato) Select idflusso, key01, autore, tipinv, datinv, noteinvio, verxml, cfsa, xml, file_allegato from ptflussi where key01 = ? and idflusso = ?";

		  TransactionStatus status = null;
		  boolean commitTransaction = false;
		  try {
			  HttpSession sessione = request.getSession();
			  ProfiloUtente profiloUtente = (ProfiloUtente) sessione.getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
			  ICriptazioneByte criptatore = FactoryCriptazioneByte.getInstance(
					  ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
					  profiloUtente.getLogin().getBytes(),
					  ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);

			  String login = new String(criptatore.getDatoCifrato());
			  String codein = sessione.getAttribute("uffint").toString();
			  String cfein = null;
			  Long idammin = null;
			  String codeinExist = null;
			  Long syscon_corrente = null;
			  status = this.sqlManager.startTransaction();
			  Long syscon = (Long)this.sqlManager.getObject(SQL_EXIST_USER, new Object[] {login});
			  //Recupero il CF e l'IDAMMIN della stazione appaltante
			  List< ? > saAdminn = this.sqlManager.getVector(SQL_SA_IDAMMIN, new Object[] {codein});
			  if (saAdminn != null && saAdminn.size() > 0) {
				  cfein = (String) SqlManager.getValueFromVectorParam(saAdminn, 0).getValue();
				  idammin = (Long) SqlManager.getValueFromVectorParam(saAdminn, 1).getValue();
				  if (isStringaValorizzata(cfein)) {
					  //verifico l'esistenza della stazione appaltante nel database di pubblicazione
					  codeinExist = (String)this.sqlManager.getObject(SQL_EXIST_SA, new Object[] {cfein, idammin});
					  if (codeinExist == null) {
						  //La stazione appaltante non esiste
						  //Creo la stazione appaltante
						  //Ci sono dei casi però in cui la stazione appatante in realtà già esiste, ma l'utente ha modificato l'idammin
						  //verifico quindi se esiste già la SA con il codice indicato
						  String presente = (String)this.sqlManager.getObject("select codein from pubblicazione.uffint where codein = ?", new Object[] {codein});
						  if (presente != null && presente.equals(codein)) {
							//se la stazione appaltante già esiste la aggiorno (è probabile che sia stato aggiornato il codice idammin)
							  this.sqlManager.update(SQL_UPDATE_SA, new Object[] {codein});
						  } else {
							  this.sqlManager.update(SQL_INSERT_SA, new Object[] {codein});
						  }
						  codeinExist = codein;
					  } else {
						  //se la stazione appaltante già esiste la aggiorno
						  this.sqlManager.update(SQL_UPDATE_SA, new Object[] {codein});

						  //verifico se è già associata ad un'utente
						  syscon_corrente = (Long)this.sqlManager.getObject(SQL_EXIST_USR_EIN, new Object[] {codeinExist});
						  if (syscon_corrente != null && !syscon_corrente.equals(syscon)) {
							  //SA già associata ad un'utente
							  //verifico se l'utente esiste veramente
							  List< ? > user = this.sqlManager.getVector(SQL_DATA_USER, new Object[] {syscon_corrente});
							  if (user != null && user.size() > 0) {
								  //L'utente esiste, devo avvisare che è necessario fare una richiesta di subentro
								  subentro.append((String) SqlManager.getValueFromVectorParam(user, 0).getValue());
								  result = -3;
							  } else {
								  //l'utente non esiste più
								  //elimino la coriispondenze in usr_ein
								  this.sqlManager.update(SQL_DELETE_USR_EIN, new Object[] {syscon_corrente});
								  syscon_corrente = null;
							  }
						  }
					  }
				  } else {
					  logger.error("Problema durante la pubblicazione del programma: CF della SA non valorizzato.");
					  result = -1;
				  }
			  } else {
				  logger.error("Problema durante la pubblicazione del programma: SQL_SA_IDAMMIN.");
				  result = -1;
			  }
			  //Se non si è già verificato un errore bloccante
			  if (result != -1) {
				  if (syscon != null) {
					  //L'utente è già presente
					  //verifico se è abilitato
					  String sysdisab = (String)this.sqlManager.getObject(SQL_IS_DISABLED_USER, new Object[] {login});
					  if (sysdisab != null && sysdisab.equals("1")) {
						  //l'utente è disabilitato
						  //Invio una mail all'utente e all'amministratore
						  //Non sei abilitato a pubblicare il programma..., riceverai comunicazioni in merito
						  result = 0;
					  } else {
						  //l'utente è abilitato
						  //Se non devo chiedere un subentro
						  if (result != -3) {
							  //Pubblico il programma
							  //Ricavo dal programmma l'anno e la SA
							  List< ? > annoSa = this.sqlManager.getVector(SQL_ANNO_SA, new Object[] {contri});
							  if (annoSa != null && annoSa.size() > 0) {
								  Long annoProgramma = (Long) SqlManager.getValueFromVectorParam(annoSa, 0).getValue();
								  //String sa = (String) SqlManager.getValueFromVectorParam(annoSa, 1).getValue();
								  //Verifico se esiste già un programma pubblicato per la SA e l'anno di riferimento
								  Long existProgrammaSa = (Long)this.sqlManager.getObject(SQL_EXIST_PROGRAMMA_SA, new Object[] {contri, annoProgramma, cfein, idammin});
								  if (existProgrammaSa != null && existProgrammaSa == 0) {
									  //Associo la SA all'utente
									  if (syscon_corrente == null) {
										  //puo essere che l'associazione ente utente esista già..., devo prima verificarne la presenza
										  Long syscon_codein = (Long)this.sqlManager.getObject(SQL_EXIST_USR_EIN_SYSCON, new Object[] {codeinExist, syscon});
										  if (syscon_codein == null) {
											  this.sqlManager.update(SQL_INSERT_USR_EIN, new Object[] {syscon, codeinExist});
										  }
									  }
									  //Cancello le eventuali occorrenze se il programma era già stato pubblicato
									  this.sqlManager.update(SQL_DELETE_PIATRI, new Object[] {contri});
									  this.sqlManager.update(SQL_DELETE_INTTRI, new Object[] {contri});
									  this.sqlManager.update(SQL_DELETE_IMMTRAI, new Object[] {contri});
									  this.sqlManager.update(SQL_DELETE_ECOTRI, new Object[] {contri});
									  //PIATRI
									  this.sqlManager.update(SQL_INSERT_PIATRI, new Object[] {codeinExist, contri});
									  //INTTRI
									  this.sqlManager.update(SQL_INSERT_INTTRI, new Object[] {contri});
									  //IMMTRAI
									  this.sqlManager.update(SQL_INSERT_IMMTRAI, new Object[] {contri});
									  //ECOTRI
									  this.sqlManager.update(SQL_INSERT_ECOTRI, new Object[] { contri});
									  //PTFLUSSI
									  this.sqlManager.update(SQL_INSERT_PTFLUSSI, new Object[] {contri, idFlusso});
									  //TECNI
									  this.sqlManager.update(SQL_INSERT_RESPRO, new Object[] {contri});
									  this.sqlManager.update(SQL_INSERT_RUP, new Object[] {contri});
									  //Il Programma è stato pubblicato con successo
									  result = 1;
								  } else {
									  logger.error("Problema durante la pubblicazione del programma: Esiste già un programma pubblicato per questa SA " + cfein + " e anno di riferimento " + annoProgramma + ".");
									  result = -2;
								  }
							  } else {
								  logger.error("Problema durante la pubblicazione del programma: SQL_ANNO_SA.");
								  result = -1;
							  }
						  }
					  }
				  } else {
					  //L'utente non esiste
					  //Creo l'utente
					  this.sqlManager.update(SQL_INSERT_USER, new Object[] {profiloUtente.getId()});
					  //Disabilito l'utente
					  this.sqlManager.update(SQL_DISABLE_USER, new Object[] {profiloUtente.getId()});
					  //Invio una mail all'utente e all'amministratore
					  //Non sei abilitato a pubblicare il programma..., riceverai comunicazioni in merito
					  result = 0;
				  }
			  }
			  commitTransaction = true;
		  } catch (Exception e) {
			  logger.error("Problema durante la pubblicazione del programma.", e);
			  result = -1;
		  } finally {
			  if (status != null) {
				  try {
					  if (commitTransaction) {
						  this.sqlManager.commitTransaction(status);
					  } else {
						  this.sqlManager.rollbackTransaction(status);
					  }
				  } catch (SQLException e) {
					  logger.error("Errore durante la chiusura della transazione", e);
				  }
			  }
		  }
		  return result;
	  }


	  public int pubblicaProgramma(Long contri, Long idFlusso, StringBuilder subentro, HttpServletRequest request) throws GestoreException {

		  int result = 0;
		  //String SQL_EXIST_USER = "Select syscon from pubblicazione.usrsys where sysnom = ?";
		  /*String SQL_INSERT_USER = "Insert into pubblicazione.usrsys(syscon, sysute, sysnom, syspwd, sysdat, sysab1, sysab2, sysab3, " +
            "sysliv, syspwbou, codimp, impimp, sysabg, syslig, sysabc, syslic, " +
            "sysabu, codtec, abilap, syspri, codtei, uducla1, flag_ldap, dn, " +
            "syscongrp, sysdisab, email, sysscad, sysuffapp, emailpec) Select syscon, sysute, sysnom, syspwd, sysdat, sysab1, sysab2, sysab3, " +
            "sysliv, syspwbou, codimp, impimp, sysabg, syslig, sysabc, syslic, " +
            "sysabu, codtec, abilap, syspri, codtei, uducla1, flag_ldap, dn, " +
            "syscongrp, sysdisab, email, sysscad, sysuffapp, emailpec from usrsys where syscon = ?";
		  String SQL_INSERT_USR_EIN = "Insert into pubblicazione.usr_ein (syscon, codein) values(?, ?)";
		  String SQL_EXIST_USR_EIN_SYSCON = "Select syscon from pubblicazione.usr_ein where codein = ? and syscon = ?";
		  String SQL_EXIST_USR_EIN = "Select syscon from pubblicazione.usr_ein where codein = ?";
		  String SQL_DELETE_USR_EIN = "Delete from pubblicazione.usr_ein where syscon = ?";
		  String SQL_DISABLE_USER = "Update pubblicazione.usrsys set sysdisab = 1 where syscon = ?";
		  String SQL_IS_DISABLED_USER = "Select sysdisab from pubblicazione.usrsys where sysnom = ?";
		  String SQL_DATA_USER = "Select sysute from pubblicazione.usrsys where syscon = ?";
		  String SQL_EXIST_SA = "Select codein from scppubb.uffint where cfein = ?";
		  //String SQL_EMAIL_USER = "Select email from pubblicazione.usrsys where sysnom = ?";
		  String SQL_ANNO_SA = "Select anntri, cenint from piatri where contri = ?";
		  String SQL_SA_IDAMMIN = "Select cfein from uffint where codein = ?";
		  String SQL_EXIST_PROGRAMMA_SA = "Select count(*) from pubblicazione.piatri where contri <> ? and anntri = ? and cenint in (select codein from pubblicazione.uffint where cfein = ? and idammin = ?) ";
		  String SQL_INSERT_RESPRO = "Insert into scppubb.tecni(codtec, cogtei, nometei, nomtec, inctec, dintec, dfitec, indtec, " +
		  "ncitec, loctec, protec, captec, teltec, faxtec, cftec, doftec, " +
		  "albtec, cnatec, dnatec, naztei, sextei, cgentei, dgentei, nottec, " +
		  "codstu, cittec, ematec, datalb, proalb, isccol, iscco1, telcel, " +
		  "mgsflg, webusr, webpwd, pivatec, syscon, datmod, emailpec, pronas, " +
		  "ema2tec, tipalb, tcapre, ncapre) Select codtec, cogtei, nometei, nomtec, inctec, dintec, dfitec, indtec, " +
		  "ncitec, loctec, protec, captec, teltec, faxtec, cftec, doftec, " +
		  "albtec, cnatec, dnatec, naztei, sextei, cgentei, dgentei, nottec, " +
		  "codstu, cittec, ematec, datalb, proalb, isccol, iscco1, telcel, " +
		  "mgsflg, webusr, webpwd, pivatec, syscon, datmod, emailpec, pronas, " +
		  "ema2tec, tipalb, tcapre, ncapre from tecni where codtec in (select respro from piatri where respro is not null and contri = ?) and codtec not in (select codtec from scppubb.tecni)";
		  String SQL_INSERT_RUP = "Insert into scppubb.tecni(codtec, cogtei, nometei, nomtec, inctec, dintec, dfitec, indtec, " +
		  "ncitec, loctec, protec, captec, teltec, faxtec, cftec, doftec, " +
		  "albtec, cnatec, dnatec, naztei, sextei, cgentei, dgentei, nottec, " +
		  "codstu, cittec, ematec, datalb, proalb, isccol, iscco1, telcel, " +
		  "mgsflg, webusr, webpwd, pivatec, syscon, datmod, emailpec, pronas, " +
		  "ema2tec, tipalb, tcapre, ncapre) Select codtec, cogtei, nometei, nomtec, inctec, dintec, dfitec, indtec, " +
		  "ncitec, loctec, protec, captec, teltec, faxtec, cftec, doftec, " +
		  "albtec, cnatec, dnatec, naztei, sextei, cgentei, dgentei, nottec, " +
		  "codstu, cittec, ematec, datalb, proalb, isccol, iscco1, telcel, " +
		  "mgsflg, webusr, webpwd, pivatec, syscon, datmod, emailpec, pronas, " +
		  "ema2tec, tipalb, tcapre, ncapre from tecni where codtec in (select codrup from inttri where codrup is not null and contri = ?) and codtec not in (select codtec from scppubb.tecni)";
		  String SQL_INSERT_SA = "Insert into scppubb.uffint(codein, nomein, viaein, nciein, citein, proein, capein, telein, " +
		  "faxein, cfein, dofein, lnaein, dnaein, ivaein, notein, codsta, " +
		  "natgiu, tipoin, numicc, daticc, proicc, codcit, codnaz, emaiin, " +
		  "codres, nomres, resini, resfin, prouff, indweb, profco, idammin, " +
		  "userid, pronas, emailpec, emai2in) Select codein, nomein, viaein, nciein, citein, proein, capein, telein, " +
		  "faxein, cfein, dofein, lnaein, dnaein, ivaein, notein, codsta, " +
		  "natgiu, tipoin, numicc, daticc, proicc, codcit, codnaz, emaiin, " +
		  "codres, nomres, resini, resfin, prouff, indweb, profco, idammin, " +
		  "userid, pronas, emailpec, emai2in from uffint where codein = ?";
		  String SQL_UPDATE_SA = "Update pubblicazione.uffint a set nomein = b.nomein, viaein = b.viaein, nciein = b.nciein, " +
		  "citein = b.citein, proein = b.proein, capein = b.capein, ivaein = b.ivaein,  natgiu = b.natgiu,  " +
		  "codcit = b.codcit, codnaz = b.codnaz, emaiin = b.emaiin, indweb = b.indweb , emailpec = b.emailpec, idammin = b.idammin " +
		  "from uffint b where a.codein=b.codein and b.codein = ?";
		  String SQL_INSERT_PIATRI = "Insert into scppubb.piatri(contri, id, anntri, bretri, esttri, imptri, dattri, numtri, puitri," +
		  "puftri, taptri, daptri, olvtri, ciptri, puetri, dv1tri, dv2tri, " +
		  "dv3tri, mu1tri, mu2tri, mu3tri, pr1tri, pr2tri, pr3tri, im1tri, " +
		  "im2tri, im3tri, bi1tri, bi2tri, bi3tri, al1tri, al2tri, al3tri, " +
		  "ac1tri, ac2tri, ac3tri, to1tri, to2tri, to3tri, at1tri, at2tri, " +
		  "at3tri, notsche1, notsche3, notsche2b, approv, cenint, respro, " +
		  "tadozi, nadozi, dadozi, tiprog, rg1tri, imprfs, file_allegato, " +
		  "impacc, statri, naptri, notsche2, notsche4, pubblica) Select contri, id, anntri, bretri, esttri, imptri, dattri, numtri, puitri," +
		  "puftri, taptri, daptri, olvtri, ciptri, puetri, dv1tri, dv2tri, " +
		  "dv3tri, mu1tri, mu2tri, mu3tri, pr1tri, pr2tri, pr3tri, im1tri, " +
		  "im2tri, im3tri, bi1tri, bi2tri, bi3tri, al1tri, al2tri, al3tri, " +
		  "ac1tri, ac2tri, ac3tri, to1tri, to2tri, to3tri, at1tri, at2tri, " +
		  "at3tri, notsche1, notsche3, notsche2b, approv, ?, respro, " +
		  "tadozi, nadozi, dadozi, tiprog, rg1tri, imprfs, file_allegato, " +
		  "impacc, 2, naptri, notsche2, notsche4, pubblica from piatri where contri = ?";
		  String SQL_INSERT_INTTRI = "Insert into scppubb.inttri(contri, conint, codint, proint, sezint, interv, catint, setint, " +
		  "claint, desint, ubiint, finint, im1int, im2int, im3int, totint, " +
		  "fatint, datint, affint, estint, temint, prgint, risint, ambint, " +
		  "sodint, prsint, pruint, priass, cidint, priint, comint, di1int, " +
		  "di2int, di3int, annint, annrif, urcint, apcint, legint, altint, " +
		  "pprint, pdeint, pesint, garint, cntint, cnsint, lavint, colint, " +
		  "stiint, utiint, icpint, dcpint, iciint, dciint, manint, nloint, " +
		  "tloint, prpint, cefint, urainttab, apainttab, cupprg, cupmst, " +
		  "tcpint, fiintr, cuiint, ailint, tilint, aflint, tflint, codrup, " +
		  "stapro, isnuovo, isarchi, intnote, dv1tri, dv2tri, dv3tri, mu1tri, " +
		  "mu2tri, mu3tri, pr1tri, pr2tri, pr3tri, im1tri, im2tri, im3tri, " +
		  "bi1tri, bi2tri, bi3tri, al1tri, al2tri, al3tri, ac1tri, ac2tri, " +
		  "ac3tri, at1tri, at2tri, at3tri, rg1tri, imprfs, tipoin, mesein, " +
		  "norrif, strupr, mantri, nuts, codcpv, ditint, tipint, nprogint, flag_cup) Select contri, conint, codint, proint, sezint, interv, catint, setint, " +
		  "claint, desint, ubiint, finint, im1int, im2int, im3int, totint, " +
		  "fatint, datint, affint, estint, temint, prgint, risint, ambint, " +
		  "sodint, prsint, pruint, priass, cidint, priint, comint, di1int, " +
		  "di2int, di3int, annint, annrif, urcint, apcint, legint, altint, " +
		  "pprint, pdeint, pesint, garint, cntint, cnsint, lavint, colint, " +
		  "stiint, utiint, icpint, dcpint, iciint, dciint, manint, nloint, " +
		  "tloint, prpint, cefint, urainttab, apainttab, cupprg, cupmst, " +
		  "tcpint, fiintr, cuiint, ailint, tilint, aflint, tflint, codrup, " +
		  "stapro, isnuovo, isarchi, intnote, dv1tri, dv2tri, dv3tri, mu1tri, " +
		  "mu2tri, mu3tri, pr1tri, pr2tri, pr3tri, im1tri, im2tri, im3tri, " +
		  "bi1tri, bi2tri, bi3tri, al1tri, al2tri, al3tri, ac1tri, ac2tri, " +
		  "ac3tri, at1tri, at2tri, at3tri, rg1tri, imprfs, tipoin, mesein, " +
		  "norrif, strupr, mantri, nuts, codcpv, ditint, tipint, nprogint, flag_cup from inttri where contri = ?";
		  /*String SQL_INSERT_IMMTRAI = "Insert into scppubb.immtrai(contri, conint, numimm, desimm, proimm, annimm, valimm, sezimm1, " +
		  "fogimm1, parimm1, subimm1, sezimm2, fogimm2, parimm2, subimm2, " +
		  "sezimm3, fogimm3, parimm3, subimm3) Select contri, conint, numimm, desimm, proimm, annimm, valimm, sezimm1, " +
		  "fogimm1, parimm1, subimm1, sezimm2, fogimm2, parimm2, subimm2, " +
		  "sezimm3, fogimm3, parimm3, subimm3 from immtrai where contri = ?";*/
		  //String SQL_INSERT_ECOTRI = "Insert into scppubb.ecotri(contri, coneco, descri, cupprg, stima) Select contri, coneco, descri, cupprg, stima from ecotri where contri = ?";
		  //String SQL_INSERT_PTFLUSSI = "Insert into scppubb.ptflussi(idflusso, key01, autore, tipinv, datinv, noteinvio, verxml, cfsa, xml, file_allegato) Select idflusso, key01, autore, tipinv, datinv, noteinvio, verxml, cfsa, xml, file_allegato from ptflussi where key01 = ? and idflusso = ?";

		  TransactionStatus status = null;
		  boolean commitTransaction = false;
		  try {
			  status = this.sqlManager.startTransaction();
			  this.sqlManager.update("update piatri set statri=2 where contri=?", new Object[] { contri } );
			  commitTransaction = true;
			  result = 1;
		  } catch (Exception e) {
			  logger.error("Problema durante la pubblicazione del programma.", e);
			  result = -1;
		  } finally {
			  if (status != null) {
				  try {
					  if (commitTransaction) {
						  this.sqlManager.commitTransaction(status);
					  } else {
						  this.sqlManager.rollbackTransaction(status);
					  }
				  } catch (SQLException e) {
					  logger.error("Errore durante la chiusura della transazione", e);
				  }
			  }
		  }
		  return result;
	  }


    /**
     * Questa funzione verifica il formato  del CUI dell'immobile.
     *
     * @param cuiimm codice CUI
     * @param progCUI
     * @return risultato operazione
     * @throws GestoreException
     *             GestoreException
     */
    public boolean verificaFormatoCuiImmobile(Long contri, Long conint, String cuiimm) throws GestoreException {
      boolean resVerifica = true;

      int lc = cuiimm.length();
      if (lc-5 > 0) {
        String vstrprog = cuiimm.substring(lc-5);
        for (int k = 0; k < 5; k++) {
          if (!Character.isDigit(vstrprog.charAt(k))) {
            resVerifica = false;
          }
        }
        //verifico che il progressivo non esista gia in db
        String sqlSelCui = "select count(*) from IMMTRAI where CONTRI=? and CUIIMM=? and CONINT not in (" + conint.toString() + ")";
        try {
          Long countCui = (Long) this.sqlManager.getObject(sqlSelCui, new Object[] { contri, cuiimm });
          if (countCui > new Long(0)) {
            resVerifica = false;
          }
        } catch (SQLException ec) {
          throw new GestoreException(
              "Errore durante la determinazione dei CUI associati all'intervento",
              "cui.intervento", ec);
        }
      } else {
        resVerifica = false;
      }
      return resVerifica;
    }

    /**
     * Calcolo del codice CUI dell'intervento.
     *
     * @param contri
     * @param tipoin
     * @param cfein
     * @param anntri
     * @return Ritorna il codice CUI dell'intervento.
     * @throws SQLException
     * @throws GestoreException
     */
    public String calcolaCUIIntervento(Long contri, String tipoin, String cfein, String anntri) throws SQLException, GestoreException {


      String result = null;
      Long progressivoCalcolato = new Long(0);
      String prefissoCUIINT = tipoin.concat(cfein).concat(anntri)+"%";
      String datiComuniPianiTriennali = "select coalesce(anntri,0), coalesce(cenint,''), coalesce(tiprog,0) from piatri where contri=? ";
      //String maxCuiInterventi = "select coalesce(max(cuiint),'') from inttri where contri in (select contri from piatri where anntri=? and cenint=? and tiprog=?)";
      String maxCuiInterventi = "select coalesce(max(cuiint),'') from inttri where cuiint like ?";
      //String maxCuiInterventiNonRiproposti = "select coalesce(max(cuiint),'') from inrtri where contri in (select contri from piatri where anntri=? and cenint=? and tiprog=?)";
      String maxCuiInterventiNonRiproposti = "select coalesce(max(cuiint),'') from inrtri where cuiint like ?";

      //Object[] parametriCui = null;
      try {
        Vector< ? > datiCalcoloMaxCui = this.sqlManager.getVector(datiComuniPianiTriennali, new Object[] { contri });

        if (datiCalcoloMaxCui.size() > 0) {
          //parametriCui = new Object[] { new Long(datiCalcoloMaxCui.get(0).toString()), datiCalcoloMaxCui.get(1).toString(), new Long(datiCalcoloMaxCui.get(2).toString()) };

          String maxCuiInt = (String) this.sqlManager.getObject(maxCuiInterventi, new Object[] { prefissoCUIINT } ); // parametriCui);
          String maxCuiIntNRip = (String) this.sqlManager.getObject(maxCuiInterventiNonRiproposti, new Object[] { prefissoCUIINT } ); // parametriCui);

          if (StringUtils.isNotEmpty(maxCuiInt) ) {
            if (StringUtils.isNotEmpty(maxCuiIntNRip)) {
              if (maxCuiInt.compareTo(maxCuiIntNRip) >= 0) {
                progressivoCalcolato = new Long(maxCuiInt.substring((maxCuiInt.length()-5), maxCuiInt.length()));
              } else {
                progressivoCalcolato = new Long(maxCuiIntNRip.substring((maxCuiIntNRip.length()-5), maxCuiIntNRip.length()));
              }
            } else {
              progressivoCalcolato = new Long(maxCuiInt.substring((maxCuiInt.length()-5), maxCuiInt.length()));
            }
          } else {
            if (StringUtils.isNotEmpty(maxCuiIntNRip)) {
              progressivoCalcolato = new Long(maxCuiIntNRip.substring((maxCuiIntNRip.length()-5), maxCuiIntNRip.length()));
            }
          }
        }
        progressivoCalcolato = progressivoCalcolato + 1;
        result = tipoin.concat(cfein).concat(anntri).concat(UtilityStringhe.fillLeft(progressivoCalcolato.toString(), '0', 5));
      } catch (SQLException e) {
        throw new GestoreException("Errore nel calcolo del progressivo per l'intervento " + contri, "", e);
      }
      return result;

    }

    /**
    *
    * @param interventi lista interventi da modificare
    * @param rup nuovo codice tecnico
    * @param dataAvvioProcedura
    * @throws GestoreException GestoreException
    */
     public void contrassegnaFabbisogno(String fabbisogni,String funzionalita, String dataAvvioProcedura)
             throws GestoreException {

         String updateFABBISOGNI = "UPDATE FABBISOGNI SET STATO = ? WHERE CONINT = ?";
         String updateFABBISOGNI_DATA_AVVIO = "UPDATE FABBISOGNI SET STATO = ?, DATAAVVPRO = ? WHERE CONINT = ?";
         String updateFABBISOGNI_QE = "UPDATE FABBISOGNI SET STATO = ?, QE_SL = ? WHERE CONINT = ? AND QE_SL = '1'";
         try {
             TransactionStatus status = this.sqlManager.startTransaction();
             
             ///////////////////////////////////////////////////////////////
             //Raccolta fabbisogni: modifiche per prototipo dicembre 2018
             //vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018)
             
             String[] chiavi = new String[] {};
             chiavi = fabbisogni.split(";;");

             for (int i = 0; i < chiavi.length; i++) {
                 DataColumnContainer fabbisogniItem = new DataColumnContainer(
                         chiavi[i]);

                 Long conint = (fabbisogniItem.getColumnsBySuffix("CONINT", true))[0]
                         .getValue().longValue();
               
                 if("RDS_ContrassegnaCompletato".equals(funzionalita)){ //Profilo RDS: contrassegna come completato --> imposta stato della proposta a 2
                   this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(2), conint });
                 }else if("RDS_SottoponiAdApprFinanz".equals(funzionalita)){ //Profilo RDS: sottoponi proposta ad approvazione finanziaria (RAF) --> imposta stato della proposta a 3 se QE_SL='2' a 4 se QE_SL='1'                
                     this.sqlManager.update(updateFABBISOGNI + " AND (QE_SL='2' OR QE_SL IS NULL)", new Object[] {new Long(3), conint });
                     this.sqlManager.update(updateFABBISOGNI_QE , new Object[] {new Long(4), "2", conint });
                 }else if("RDS_InoltraAlRdp".equals(funzionalita)){  //Profilo RDS: inoltra proposta al referente della propogrammazione (RDP) --> imposta stato della proposta a 4               
                   this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(4), conint });
                 }else if("RDS_Annulla".equals(funzionalita)){  //Profilo RDS: Annulla proposta --> imposta stato della proposta a 32               
                   this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(32), conint });
                 }else if("RDP_SegnalaProceduraAvviata".equals(funzionalita)){  //Profilo RDP: Segnala procedura avviata --> imposta stato della proposta a 22
                	 Date dataAvvio = new Date();
                	 if (dataAvvioProcedura != null) {
                		 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                		 dataAvvio = df.parse(dataAvvioProcedura);
                	 }
                	 this.sqlManager.update(updateFABBISOGNI_DATA_AVVIO, new Object[] {new Long(22), dataAvvio, conint });
                 }
               }                 
             ///////////////////////////////////////////////////////////////
             this.sqlManager.commitTransaction(status);
         } catch (SQLException e) {
             throw new GestoreException(
                     "Errore nel\'esecuzione della query per contrassegnare i fabbisogni selezionati",
                     "Fabbisogni contrassegnati " + fabbisogni, e);
         } catch (ParseException e) {
        	 throw new GestoreException(
                     "Errore nella data di avvio procedura per contrassegnare i fabbisogni selezionati",
                     "Fabbisogni contrassegnati " + fabbisogni, e);
		}
     }
     
     /**
     *
     * Una proposta Completata deve essere riportata in stato "In compilazione" quando vengono modificati i dati dei capitoli
     *
     * @param conint codice fabbisogno
     * @throws GestoreException GestoreException
     */
    public void updateCapitoli(Long conint) throws GestoreException {
  	    try {
  	      TransactionStatus status = this.sqlManager.startTransaction();
  	      Long statoFabbisogno = (Long) this.sqlManager.getObject("select STATO from FABBISOGNI where CONINT = ? ", new Object[] { conint });
    	  if (statoFabbisogno != null && statoFabbisogno.equals(new Long(2))) {
    		  this.sqlManager.update("update FABBISOGNI set STATO=1 where CONINT=?", new Object[] { conint });
    	  }
  	      this.sqlManager.commitTransaction(status);
  	    } catch (SQLException e) {
  	      throw new GestoreException(
  	          "Errore nel\'esecuzione della query per l\'aggiornamento dello stato del fabbisogno a seguito di una modifica dei capitoli",
  	          "", e);
  	    }
  	  }

     /**
      * Calcolo del Codice univoco dell'immobile.
      *
      * @param contri
      * @param codein
      * @return Ritorna il Codice univoco immobile.
      * @throws SQLException
      * @throws GestoreException
      */
     public String calcolaCUIImmobile(Long contri, String codein) throws SQLException, GestoreException {

       //Nuova gestione Calcolo CUIIM (febbraio 2019)
       String result = null;
       String startchar = "I";
       Long progressivoCalcolato = new Long(0);

       String selCFEIN = "select CFEIN from UFFINT where CODEIN = ?";
       String cfein = (String) this.geneManager.getSql().getObject(selCFEIN, new Object[]{codein});
       cfein = UtilityStringhe.convertiNullInStringaVuota(cfein);

       String anntriStr = "";
       String selANNTRI = "select ANNTRI from PIATRI where CONTRI = ?";
       Long anntri = (Long) this.geneManager.getSql().getObject(selANNTRI, new Object[]{contri});
       if (anntri != null) {
         anntriStr = anntri.toString();
       }

       String prefissoCUIIMM = startchar.concat(cfein).concat(anntriStr)+"%";
       String maxCuiImmobili = "select coalesce(max(cuiimm),'') from immtrai where cuiimm like ?";

       try {

         String maxCuiImm = (String) this.sqlManager.getObject(maxCuiImmobili, new Object[] { prefissoCUIIMM } );

         if (StringUtils.isNotEmpty(maxCuiImm) ) {
           progressivoCalcolato = new Long(maxCuiImm.substring((maxCuiImm.length()-5), maxCuiImm.length()));
         }
         //progressivoCalcolato = progressivoCalcolato + 1;
         result = startchar.concat(cfein).concat(anntriStr).concat(UtilityStringhe.fillLeft(progressivoCalcolato.toString(), '0', 5));
       } catch (SQLException e) {
         throw new GestoreException("Errore nel calcolo del progressivo per il Codice univoco immobile dell'intervento " + contri, "", e);
       }
       return result;

     }

     /**
      * Calcolo del Codice univoco dell'immobile.
      *
      * @param maxCuiImm
      * @return Incrementa il Codice univoco immobile.
      * @throws SQLException
      * @throws GestoreException
      */
     public String incrementaCUIImmobile(String maxCuiImm) {

       //Nuova gestione Calcolo CUIIM (febbraio 2019)
       String result = null;
       Long progressivoCalcolato = new Long(0);
       
       if (StringUtils.isNotEmpty(maxCuiImm) ) {
         progressivoCalcolato = new Long(maxCuiImm.substring((maxCuiImm.length()-5), maxCuiImm.length()));
         progressivoCalcolato = progressivoCalcolato + 1;
         result = maxCuiImm.substring(0,(maxCuiImm.length()-5)).concat(UtilityStringhe.fillLeft(progressivoCalcolato.toString(), '0', 5));
       }         
       return result;

     }



}