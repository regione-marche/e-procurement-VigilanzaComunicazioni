package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityNumeri;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.utils.utility.UtilityWeb;
import it.eldasoft.w9.bl.IndicatoreBean;
import it.eldasoft.w9.bl.IndicatorePreliminareBean;
import it.eldasoft.w9.bl.IndicatoriLottoManager;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action per la creazione del report Indicatori lotto e download del pdf. 
 * 
 * @author luca.giacomazzo
 */
public class GetIndicatoriLottoAction extends ActionBaseNoOpzioni {

  static Logger logger = Logger.getLogger(GetIndicatoriLottoAction.class);
  
  private IndicatoriLottoManager indicatoriLottoManager;
  
  private SqlManager sqlManager;
  
  public void setIndicatoriLottoManager(IndicatoriLottoManager indicatoriLottoManager) {
    this.indicatoriLottoManager = indicatoriLottoManager;
  }

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
  
  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    
    String target = null;
    
    String codiceCIG = request.getParameter("codiceCIG");
    String tipoReport = request.getParameter("tipoReportIndicatori");
    
    try {
      boolean isOk = false;
      if (StringUtils.equals("anomalia", tipoReport)) {
        isOk = this.creaReportAnomalia(codiceCIG, request, response);
      } else {
        isOk = this.creaReportPreliminare(codiceCIG, request, response);
      }
      
      if (!isOk) {
        target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      }
    } catch (Throwable t) {
      logger.error("Errore inaspettato nel calcolo degli indicatori del lotto con CIG=" + codiceCIG, t);
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    
    if (StringUtils.isNotEmpty(target)) {
      return mapping.findForward(target);
    } else {
      return null;
    }
  }

  /**
   * Creazione del report anomalia
   *  
   * @param codiceCIG
   * @param request
   * @param response
   * @return Ritorna l'esito dell'operazione di composizione del report con JasperReport
   */
  private boolean creaReportAnomalia(String codiceCIG, HttpServletRequest request, HttpServletResponse response) {
    
    boolean result = true;
    
    try {
      HashMap<String, Long> datiLotto = UtilitySITAT.getCodGaraCodLottByCIG(codiceCIG, this.sqlManager);
      Long codiceGara = datiLotto.get("CODGARA");
      Long codiceLotto = datiLotto.get("CODLOTT");
      
      HashMap<String, Object> hash = this.sqlManager.getHashMap(
          "select NOMEIN, CFEIN from UFFINT where CODEIN=(select CODEIN from W9GARA where CODGARA=?)", 
          new Object[] { codiceGara });
    
      String denominazioneEnte = ((JdbcParametro) hash.get("NOMEIN")).getStringValue();
    
      hash = this.sqlManager.getHashMap(
          "select NOMTEC, CFTEC from TECNI where CODTEC=(select RUP from W9GARA where CODGARA=?) " 
              + " and CGENTEI=(select CODEIN from W9GARA where CODGARA=?)", 
          new Object[] { codiceGara, codiceGara });
      
      String denominazioneRUP = "";
      if (hash != null && hash.containsKey("NOMTEC")) {
        denominazioneRUP = ((JdbcParametro) hash.get("NOMTEC")).getStringValue();
      }

      hash = this.sqlManager.getHashMap(
          "select l.OGGETTO, substr(coalesce(l.CPV,'00'),1,2) as CPV, t2a.TAB2D2 as DESC_TIPO_CONTRATTO, t2.TAB2D2 as ID_CATEGORIA_PREVALENTE, "
               + "g.TIPO_APP, t3.TAB1DESC as DESC_TIPO_APP, t1.TAB1DESC as ID_SCELTA_CONTRAENTE, t4.TAB1DESC as ID_MODO_GARA, "
               + "case when l.IMPORTO_TOT < 40000 then '<40mila' "
            	 +   " when l.IMPORTO_TOT < 150000 then '40mila-150mila' "
            	 +   " when l.IMPORTO_TOT < 1000000 then '150mila-1mln' "
            	 +   " when l.IMPORTO_TOT < 5022500 then '1mln-5.225mln' "
            	 +   " else '>5.225mln' end as CLASSE_IMPORTO, "
            	 +   " l.IMPORTO_TOT as IMPORTO_TOTALE_LOTTO, "
               +   " p.IMPORTO_AGGIUDICAZIONE as IMPORTO_AGGIUDICAZIONE,"
               +   " l.TIPO_CONTRATTO as TIPO_CONTRATTO "
          + " from W9LOTT l join W9GARA g on g.CODGARA = l.CODGARA "
              + " left join TAB1 t1 on t1.TAB1COD='W3005' and t1.TAB1TIP=l.ID_SCELTA_CONTRAENTE "
              + " left join TAB1 t3 on t3.TAB1COD='W3999' and t3.TAB1TIP=g.TIPO_APP "
              +	" left join TAB2 t2 on t2.TAB2COD='W3z03' and t2.TAB2TIP=l.ID_CATEGORIA_PREVALENTE "
              + " left join TAB1 t4 on t4.TAB1COD='W3007' and t4.TAB1TIP=l.ID_MODO_GARA "
              + " left join TAB2 t2a on t2a.TAB2COD='W3z05' and t2a.TAB2TIP=l.TIPO_CONTRATTO "
              + " left join W9APPA p on p.CODGARA = l.CODGARA and p.CODLOTT = l.CODLOTT "
              + " where l.CIG=? ",
              new Object[] { codiceCIG });
      
      String oggettoLotto = ((JdbcParametro) hash.get("OGGETTO")).getStringValue();
      String codiceCPV = ((JdbcParametro) hash.get("CPV")).getStringValue();
      String tipoContratto = ((JdbcParametro) hash.get("TIPO_CONTRATTO")).getStringValue();
      String descrizioneTipoContratto = ((JdbcParametro) hash.get("DESC_TIPO_CONTRATTO")).getStringValue();
      String idCategoriaPrevalente = ((JdbcParametro) hash.get("ID_CATEGORIA_PREVALENTE")).getStringValue();
      String tipoAppalto = ((JdbcParametro) hash.get("TIPO_APP")).getStringValue();
      String descrizioneTipoAppalto = ((JdbcParametro) hash.get("DESC_TIPO_APP")).getStringValue();
      String idSceltaContraente = ((JdbcParametro) hash.get("ID_SCELTA_CONTRAENTE")).getStringValue();
      String classeImporto = ((JdbcParametro) hash.get("CLASSE_IMPORTO")).getStringValue();
      String criterioAggiudicazione = ((JdbcParametro) hash.get("ID_MODO_GARA")).getStringValue();
      Double importoTotaleLotto = ((JdbcParametro) hash.get("IMPORTO_TOTALE_LOTTO")).doubleValue();
      Double importoAggiudicazione = ((JdbcParametro) hash.get("IMPORTO_AGGIUDICAZIONE")).doubleValue();
      
      String strImportoTotaleLotto = "";
      String strImportoAggiudicazione = "";
      if (importoTotaleLotto != null) {
        strImportoTotaleLotto = UtilityNumeri.convertiImporto(importoTotaleLotto, 2);
      }
      
      if (importoAggiudicazione != null) {
        strImportoAggiudicazione = UtilityNumeri.convertiImporto(importoAggiudicazione, 2);
      }
      
      Long idAppalto = null;
      if (StringUtils.isNotEmpty(tipoContratto)) {
        if (tipoContratto.indexOf("L") >= 0) {
          idAppalto = (Long) this.sqlManager.getObject(
              "select case when min(ID_APPALTO) = 8 then 99 else min(ID_APPALTO) end from W9APPALAV where CODGARA=? and CODLOTT=?",
              new Object[] { codiceGara, codiceLotto });
        } else {
          idAppalto = (Long) this.sqlManager.getObject("select min(ID_APPALTO) from W9APPAFORN where CODGARA=? and CODLOTT=?",
              new Object[] { codiceGara, codiceLotto });
        }
      }
      String tipologiaLavori = "";
      if (idAppalto != null) {
        tipologiaLavori = UtilityStringhe.fillLeft(idAppalto.toString(), '0', 2);
      }
      
      List<IndicatoreBean> listaIndicatoriAffidamento = this.indicatoriLottoManager.calcolaIndicatori(codiceCIG, true);
      List<IndicatoreBean> listaIndicatoriEsecuzione  = this.indicatoriLottoManager.calcolaIndicatori(codiceCIG, false);
      
      String descrizioneIncongruita = null;
      
      if (listaIndicatoriAffidamento != null && listaIndicatoriAffidamento.size() > 0) {
        IndicatoreBean indicatore = listaIndicatoriAffidamento.get(0);
        if (StringUtils.isNotEmpty(indicatore.getDescrizioneIncongruita())) {
          descrizioneIncongruita = indicatore.getDescrizioneIncongruita();
        }
      } else {
        descrizioneIncongruita = "";
      }
      
      String PROP_JRREPORT_SOURCE_DIR = "/WEB-INF/jrReport/IndicatoriLotto/";
      String jrReportSourceDir = null;
      // Input stream del file sorgente
      InputStream inputStreamJrReport = null;
  
      jrReportSourceDir = request.getSession().getServletContext().getRealPath(PROP_JRREPORT_SOURCE_DIR) + "/";
      inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
            PROP_JRREPORT_SOURCE_DIR + "indicatoriLotto.jasper");
      
      // Parametri
      HashMap<String, Object> jrParameters = new HashMap<String, Object>();
      jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
      jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);

      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("denominazioneEnte", denominazioneEnte);
      parameters.put("denominazioneRUP", denominazioneRUP);
      parameters.put("codiceCIG", codiceCIG);
      parameters.put("oggettoLotto", oggettoLotto);
      parameters.put("codiceCPV", codiceCPV);
      parameters.put("tipoContratto", tipoContratto);
      parameters.put("descTipoContratto", descrizioneTipoContratto);
      parameters.put("idCategoriaPrevalente", idCategoriaPrevalente);
      parameters.put("idSceltaContraente", idSceltaContraente);
      parameters.put("classeImporto", classeImporto);
      parameters.put("tipologiaLavori", tipologiaLavori);
      parameters.put("criterioAggiudicazione", criterioAggiudicazione);
      parameters.put("importoTotaleLotto", strImportoTotaleLotto);
      parameters.put("importoAggiudicazione", strImportoAggiudicazione);
      
      if (StringUtils.isNotEmpty(tipoAppalto)) {
        if (StringUtils.isNotEmpty(descrizioneTipoAppalto)) {
          parameters.put("tipoAppalto", tipoAppalto.concat(" - ").concat(descrizioneTipoAppalto));
        } else {
          parameters.put("tipoAppalto", tipoAppalto);
        }
      } else {
        parameters.put("tipoAppalto", "");
      }
      
      parameters.put("messaggioValoriNonCongrui01", descrizioneIncongruita);
      if (listaIndicatoriAffidamento != null && listaIndicatoriAffidamento.size() > 0) {
        parameters.put("indicatoriAffidamento", listaIndicatoriAffidamento);
      } else {
        parameters.put("indicatoriAffidamento", null);
      }
      if (listaIndicatoriEsecuzione != null && listaIndicatoriEsecuzione.size() > 0) {
        parameters.put("indicatoriEsecuzione", listaIndicatoriEsecuzione);
      } else {
        parameters.put("indicatoriEsecuzione", null);
      }
      
      JasperPrint jasperPrint = JasperFillManager.fillReport(inputStreamJrReport, parameters, new JREmptyDataSource());

      // Output stream del risultato
      ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();
      JRExporter jrExporter = new JRPdfExporter();
      jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
      jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baosJrReport);
      jrExporter.exportReport();

      inputStreamJrReport.close();
      baosJrReport.close();
      
      UtilityWeb.download("reportAnomalia_" + codiceCIG + ".pdf", baosJrReport.toByteArray(), response);

    } catch (GestoreException ge) {
      logger.error("Errore nell'estrazione degli importi dei dati generali del lotto nel calcolo degli indicatori del lotto con CIG="
          + codiceCIG, ge);
      result = false;
    } catch (SQLException se) {
      logger.error("Errore nell'estrazione dei dati generali del lotto nel calcolo degli indicatori del lotto con CIG="
          + codiceCIG, se);
      result = false;
    } catch (JRException je) {
      logger.error("Errore nella generazione del pdf con jasperReport", je);
      result = false;
    } catch (IOException ioe) {
      logger.error("Errore di accesso al pdf generato con jasperReport", ioe);
      result = false;
    }
   
    return result;
  }
  
  /**
   * Creazione del report preliminare: il valore degli indicatori per contratti simili
   *  
   * @param codiceCIG
   * @param request
   * @param response
   * @return Ritorna l'esito dell'operazione di composizione del report con JasperReport
   */
  private boolean creaReportPreliminare(String codiceCIG, HttpServletRequest request, HttpServletResponse response) {
    
    boolean result = true;
    
    try {
      String codiceSA = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
      
      HashMap<String, Long> datiLotto = UtilitySITAT.getCodGaraCodLottByCIG(codiceCIG, this.sqlManager);
      Long codiceGara = datiLotto.get("CODGARA");
      Long codiceLotto = datiLotto.get("CODLOTT");
    
      HashMap<String, Object> hash = this.sqlManager.getHashMap(
          "select NOMEIN, CFEIN from UFFINT where CODEIN=(select CODEIN from W9GARA where CODGARA=?)", 
          new Object[] { codiceGara });
    
      String denominazioneEnte = ((JdbcParametro) hash.get("NOMEIN")).getStringValue();
    
      hash = this.sqlManager.getHashMap(
          "select NOMTEC, CFTEC from TECNI where CODTEC=(select RUP from W9GARA where CODGARA=?) " 
              + " and CGENTEI=(select CODEIN from W9GARA where CODGARA=?)", 
          new Object[] { codiceGara, codiceGara });
    
      String denominazioneRUP = "";
      if (hash != null && hash.containsKey("NOMTEC")) {
        denominazioneRUP = ((JdbcParametro) hash.get("NOMTEC")).getStringValue();
      }
    
      hash = this.sqlManager.getHashMap(
          "select l.OGGETTO, substr(coalesce(l.CPV,'00'),1,2) as CPV, t2a.TAB2D2 as DESC_TIPO_CONTRATTO, t2.TAB2D2 as ID_CATEGORIA_PREVALENTE, "
               + "g.TIPO_APP, t3.TAB1DESC as DESC_TIPO_APP, t1.TAB1DESC as ID_SCELTA_CONTRAENTE, t4.TAB1DESC as ID_MODO_GARA, "
            + " case when l.IMPORTO_TOT < 40000 then '<40mila' "
               +   " when l.IMPORTO_TOT < 150000 then '40mila-150mila' "
               +   " when l.IMPORTO_TOT < 1000000 then '150mila-1mln' "
               +   " when l.IMPORTO_TOT < 5022500 then '1mln-5.225mln' "
               +   " else '>5.225mln' end as CLASSE_IMPORTO, "
               +   " l.IMPORTO_TOT as IMPORTO_TOTALE_LOTTO, "
               +   " p.IMPORTO_AGGIUDICAZIONE as IMPORTO_AGGIUDICAZIONE,"
               +   " l.TIPO_CONTRATTO as TIPO_CONTRATTO "
          + " from W9LOTT l join W9GARA g on g.codgara=l.codgara "
              + " left join TAB1 t1 on t1.TAB1COD='W3005' and t1.TAB1TIP=l.ID_SCELTA_CONTRAENTE "
              + " left join TAB1 t3 on t3.TAB1COD='W3999' and t3.TAB1TIP=g.TIPO_APP "
              + " left join TAB2 t2 on t2.TAB2COD='W3z03' and t2.TAB2TIP=l.ID_CATEGORIA_PREVALENTE "
              + " left join TAB1 t4 on t4.TAB1COD='W3007' and t4.TAB1TIP=l.ID_MODO_GARA "
              + " left join TAB2 t2a on t2a.TAB2COD='W3z05' and t2a.TAB2TIP=l.TIPO_CONTRATTO "
              + " left join W9APPA p on p.CODGARA = l.CODGARA and p.CODLOTT = l.CODLOTT "
              + " where l.CIG=? ",
              new Object[] { codiceCIG });

      String oggettoLotto = ((JdbcParametro) hash.get("OGGETTO")).getStringValue();
      String codiceCPV = ((JdbcParametro) hash.get("CPV")).getStringValue();
      String tipoContratto = ((JdbcParametro) hash.get("TIPO_CONTRATTO")).getStringValue();
      String descrizioneTipoContratto = ((JdbcParametro) hash.get("DESC_TIPO_CONTRATTO")).getStringValue();
      String idCategoriaPrevalente = ((JdbcParametro) hash.get("ID_CATEGORIA_PREVALENTE")).getStringValue();
      String tipoAppalto = ((JdbcParametro) hash.get("TIPO_APP")).getStringValue();
      String descrizioneTipoAppalto = ((JdbcParametro) hash.get("DESC_TIPO_APP")).getStringValue();
      String idSceltaContraente = ((JdbcParametro) hash.get("ID_SCELTA_CONTRAENTE")).getStringValue();
      String classeImporto = ((JdbcParametro) hash.get("CLASSE_IMPORTO")).getStringValue();
      String criterioAggiudicazione = ((JdbcParametro) hash.get("ID_MODO_GARA")).getStringValue();
      Double importoTotaleLotto = ((JdbcParametro) hash.get("IMPORTO_TOTALE_LOTTO")).doubleValue();
      Double importoAggiudicazione = ((JdbcParametro) hash.get("IMPORTO_AGGIUDICAZIONE")).doubleValue();
      
      String strImportoTotaleLotto = "";
      String strImportoAggiudicazione = "";
      if (importoTotaleLotto != null) {
        strImportoTotaleLotto = UtilityNumeri.convertiImporto(importoTotaleLotto, 2);
      }
      
      if (importoAggiudicazione != null) {
        strImportoAggiudicazione = UtilityNumeri.convertiImporto(importoAggiudicazione, 2);
      }
      
      Long idAppalto = null;
      if (StringUtils.isNotEmpty(tipoContratto)) {
        if (tipoContratto.indexOf("L") >= 0) {
          idAppalto = (Long) this.sqlManager.getObject(
              "select case when min(ID_APPALTO) = 8 then 99 else min(ID_APPALTO) end from W9APPALAV where CODGARA=? and CODLOTT=?",
              new Object[] { codiceGara, codiceLotto });
        } else {
          idAppalto = (Long) this.sqlManager.getObject("select min(ID_APPALTO) from W9APPAFORN where CODGARA=? and CODLOTT=?",
              new Object[] { codiceGara, codiceLotto });
        }
      }
      String tipologiaLavori = "";
      if (idAppalto != null) {
        tipologiaLavori = UtilityStringhe.fillLeft(idAppalto.toString(), '0', 2);
      }
      
      List<IndicatorePreliminareBean> listaIndicatoriAffidamento = this.indicatoriLottoManager.calcolaIndicatoriPreliminare(codiceCIG, codiceSA, true);
      List<IndicatorePreliminareBean> listaIndicatoriEsecuzione  = this.indicatoriLottoManager.calcolaIndicatoriPreliminare(codiceCIG, codiceSA, false);
      
      String descrizioneIncongruita = null;
      
      if (listaIndicatoriAffidamento != null && listaIndicatoriAffidamento.size() > 0) {
        IndicatorePreliminareBean indicatore = listaIndicatoriAffidamento.get(0);
        if (StringUtils.isNotEmpty(indicatore.getDescrizioneIncongruita())) {
          descrizioneIncongruita = indicatore.getDescrizioneIncongruita();
        }
      } else {
        descrizioneIncongruita = "";
      }
      
      String PROP_JRREPORT_SOURCE_DIR = "/WEB-INF/jrReport/IndicatoriLotto/";
      String jrReportSourceDir = null;
      // Input stream del file sorgente
      InputStream inputStreamJrReport = null;
  
      jrReportSourceDir = request.getSession().getServletContext().getRealPath(PROP_JRREPORT_SOURCE_DIR) + "/";
      inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
            PROP_JRREPORT_SOURCE_DIR + "indicatoriLottoPreliminare.jasper");
      
      // Parametri
      HashMap<String, Object> jrParameters = new HashMap<String, Object>();
      jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
      jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);

      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("denominazioneEnte", denominazioneEnte);
      parameters.put("denominazioneRUP", denominazioneRUP);
      parameters.put("codiceCIG", codiceCIG);
      parameters.put("oggettoLotto", oggettoLotto);
      parameters.put("codiceCPV", codiceCPV);
      parameters.put("tipoContratto", tipoContratto);
      parameters.put("descTipoContratto", descrizioneTipoContratto);
      parameters.put("idCategoriaPrevalente", idCategoriaPrevalente);
      parameters.put("idSceltaContraente", idSceltaContraente);
      parameters.put("classeImporto", classeImporto);
      parameters.put("criterioAggiudicazione", criterioAggiudicazione);
      parameters.put("tipologiaLavori", tipologiaLavori);
      parameters.put("importoTotaleLotto", strImportoTotaleLotto);
      parameters.put("importoAggiudicazione", strImportoAggiudicazione);
      
      parameters.put("messaggioValoriNonCongrui01", descrizioneIncongruita);
      if (listaIndicatoriAffidamento != null && listaIndicatoriAffidamento.size() > 0) {
        parameters.put("indicatoriAffidamento", listaIndicatoriAffidamento);
      } else {
        parameters.put("indicatoriAffidamento", null);
      }
      if (listaIndicatoriEsecuzione != null && listaIndicatoriEsecuzione.size() > 0) {
        parameters.put("indicatoriEsecuzione", listaIndicatoriEsecuzione);
      } else {
        parameters.put("indicatoriEsecuzione", null);
      }
      
      if (StringUtils.isNotEmpty(tipoAppalto)) {
        if (StringUtils.isNotEmpty(descrizioneTipoAppalto)) {
          parameters.put("tipoAppalto", tipoAppalto.concat(" - ").concat(descrizioneTipoAppalto));
        } else {
          parameters.put("tipoAppalto", tipoAppalto);
        }
      } else {
        parameters.put("tipoAppalto", "");
      }
      
      JasperPrint jasperPrint = JasperFillManager.fillReport(inputStreamJrReport, parameters, new JREmptyDataSource());
      
      // Output stream del risultato
      ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();
      JRExporter jrExporter = new JRPdfExporter();
      jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
      jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baosJrReport);
      jrExporter.exportReport();

      inputStreamJrReport.close();
      baosJrReport.close();
      
      UtilityWeb.download("reportPreliminari_" + codiceCIG + ".pdf", baosJrReport.toByteArray(), response);

    } catch (GestoreException ge) {
      logger.error("Errore nell'estrazione degli importi dei dati generali del lotto nel calcolo degli indicatori del lotto con CIG="
          + codiceCIG, ge);
      result = false;
    } catch (SQLException se) {
      logger.error("Errore nell'estrazione dei dati generali del lotto nel calcolo degli indicatori del lotto con CIG="
          + codiceCIG, se);
      result = false;
    } catch (JRException je) {
      logger.error("Errore nella generazione del pdf con jasperReport", je);
      result = false;
    } catch (IOException ioe) {
      logger.error("Errore di accesso al pdf generato con jasperReport", ioe);
      result = false;
    }
   
    return result;
  }
  
}
