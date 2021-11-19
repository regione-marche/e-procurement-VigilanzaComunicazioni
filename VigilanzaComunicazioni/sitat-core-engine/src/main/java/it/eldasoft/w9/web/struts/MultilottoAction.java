package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.commons.web.struts.DispatchActionBaseNoOpzioni;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.bl.ControlliValidazioneManager;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.common.CostantiSitatSA;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.tags.gestori.submit.GestoreW9FLUSSI;
import it.eldasoft.w9.utils.UtilitySITAT;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.transaction.TransactionStatus;

/**
 * DispatchAction per la gestione del multilotto nella gara
 * 
 * @author Mirco.franzoni
 */
public class MultilottoAction extends DispatchActionBaseNoOpzioni {

  private static Logger logger = Logger.getLogger(MultilottoAction.class);

  private GeneManager geneManager;
  
  private SqlManager sqlManager;
  
  private W9Manager w9Manager;
  
  private ControlliValidazioneManager controlliValidazioneManager;
  
  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }
  
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
  
  public void setControlliValidazioneManager(ControlliValidazioneManager controlliValidazioneManager) {
    this.controlliValidazioneManager = controlliValidazioneManager;
  }

  public void setW9Manager(W9Manager w9Manager) {
	  this.w9Manager = w9Manager;
  }
  
  public ActionForward inizializza(final ActionMapping mapping, final ActionForm form, 
      final HttpServletRequest request, final HttpServletResponse response) throws Exception {

    if (logger.isDebugEnabled()) {
      logger.debug("inizializza: inizio metodo");
    }
    
    String target = CostantiGeneraliStruts.FORWARD_OK + "Inizializza";
    Long codiceGara  = null;
    List<Object> risultato = null;
    if (request.getParameter("codgara") != null) {
        codiceGara = Long.parseLong(request.getParameter("codgara"));
        risultato = getLotti(codiceGara, true);
        if (request.getParameter("reset") == null && risultato.size()>0) {
        	request.setAttribute("alert", "ok");
        } else {
        	if (this.resetCigMaster(codiceGara, risultato, null)) {
        		this.w9Manager.updateSituazioneLottiGara(codiceGara);
        		risultato = getLotti(codiceGara, false);
                // si popola il risultato in formato JSON
                JSONArray jsonArray = JSONArray.fromObject(risultato);
                String json = jsonArray.toString();
                request.setAttribute("lotti", json);
        	}
        }
    } 
    if (risultato == null && request.getAttribute("alert") != null) {
    	request.setAttribute("errore", "Si è verificato un errore imprevisto durante l'inizializzazione del monitoraggio multilotto");
    }
    request.setAttribute("codgara", codiceGara);
    if (logger.isDebugEnabled()) {
      logger.debug("inizializza: fine metodo");
    }
        
    return mapping.findForward(target);
  }

  public ActionForward validazione(final ActionMapping mapping, final ActionForm form, 
		  final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	  if (logger.isDebugEnabled()) {
		  logger.debug("validazione: inizio metodo");
	  }
	  String target = CostantiGeneraliStruts.FORWARD_OK + "Validazione";
	  
	  try {
		  String profiloAttivo = (String) request.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
		  Long codiceGara  = null;
		  String associazioni  = null;
		  if (request.getParameter("codgara") != null && request.getParameter("associazioni") != null) {
			  //Validazione dati
			  codiceGara = Long.parseLong(request.getParameter("codgara"));
			  associazioni = request.getParameter("associazioni");
			  HashMap<String, Object> infoValidazione = controlliValidazioneManager.validateMultilotto(codiceGara, associazioni, profiloAttivo);
			  request.setAttribute("titolo", infoValidazione.get("titolo"));
			  request.setAttribute("listaControlli", infoValidazione.get("listaControlli"));
			  request.setAttribute("numeroWarning", infoValidazione.get("numeroWarning"));
			  request.setAttribute("numeroErrori", infoValidazione.get("numeroErrori"));
			  request.setAttribute("lottiDaInviare", infoValidazione.get("lottiDaInviare"));
			  
			  request.setAttribute("codgara", codiceGara);
		  } else {
			  request.setAttribute("errore", "Si è verificato un errore imprevisto durante la validazione dei dati");
		  }
	  } catch(Exception ex) {
		  logger.error("Errore durante la validazione del monitoraggio multilotto", ex);
		  request.setAttribute("errore", "Si è verificato un errore imprevisto durante la validazione dei dati");
	  }
	  
	  if (logger.isDebugEnabled()) {
		  logger.debug("validazione: fine metodo");
	  }

	  return mapping.findForward(target);
  }
  
  public ActionForward avviaMonitoraggio(final ActionMapping mapping, final ActionForm form, 
		  final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	  if (logger.isDebugEnabled()) {
		  logger.debug("avviaMonitoraggio: inizio metodo");
	  }
	  String target = CostantiGeneraliStruts.FORWARD_OK + "AvviaMonitoraggio";
	  
		
	  Long codiceGara  = null;
	  String lottiDaInviare  = null;
	  int indiceLottoDaInviare = 0;
	  try {
		  String profiloAttivo = (String) request.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
		  boolean isConfigurazioneVigilanza = UtilitySITAT.isConfigurazioneVigilanza(this.geneManager, profiloAttivo);
		  codiceGara = Long.parseLong(request.getParameter("codgara"));
		  lottiDaInviare = request.getParameter("lottiDaInviare");
		  request.setAttribute("titolo", "Invio flussi per il monitoraggio multilotto");
		  request.setAttribute("codgara", codiceGara);
		  request.setAttribute("lottiDaInviare", lottiDaInviare);
		  request.setAttribute("numeroLottiDaInviare", lottiDaInviare.split("-").length);
		  if (request.getParameter("numeroLottiInviati") != null && !request.getParameter("numeroLottiInviati").equals("")) {
			  //invio lotto successivo
			  indiceLottoDaInviare = Integer.parseInt(request.getParameter("numeroLottiInviati"));
			  //ricavo il lotto
			  String[] arrayLottiDaInviare = lottiDaInviare.split("-");
			  String codiceLotto = arrayLottiDaInviare[indiceLottoDaInviare].split(";")[1];
			  if (this.invioSchedaAggiudicazioneLotto(codiceGara, new Long(codiceLotto), request, isConfigurazioneVigilanza)) {
				  indiceLottoDaInviare++;
			  } else {
				  //se c'è un errore durante l'invio dei flussi per il monitoraggio multilotto
				  //ripristino la situazione resettando il campo CIG_MASTER_ML e ripristinando la situazione lotti/gara
				  if (this.resetCigMaster(codiceGara, null, lottiDaInviare)) {
					  this.w9Manager.updateSituazioneLottiGara(codiceGara);
				  }
			  }
		  } else {
			  //valorizzo il CIG Master per i lotti accorpati
			  if (this.valorizzaCigMaster(codiceGara, lottiDaInviare)) {
				  indiceLottoDaInviare = 0;
				  //se non sono in configurazione vigilanza invio l'anagrafica gara / lotti
				  if (!isConfigurazioneVigilanza) {
					  if (!this.invioAnagraficaGaraLotti(codiceGara, request)) {
						  this.resetCigMaster(codiceGara, null, lottiDaInviare);
					  }
				  }
			  } else {
				  request.setAttribute("errore", "Errore durante la valorizzazione del CIG Master nei lotti accorpati");
			  }
		  }
		  //recupero le credenziali
		  request.setAttribute("recuperauser", request.getParameter("recuperauser"));
		  request.setAttribute("recuperapassword", request.getParameter("recuperapassword"));
		  request.setAttribute("simoguser", request.getParameter("simoguser"));
		  request.setAttribute("simogpass", request.getParameter("simogpass"));
		  request.setAttribute("memorizza", request.getParameter("memorizza"));
		  request.setAttribute("numeroLottiInviati", indiceLottoDaInviare);
		  
	  } catch(Exception ex) {
		  logger.error("Errore durante l'invio del monitoraggio multilotto", ex);
		  request.setAttribute("errore", "Si è verificato un errore imprevisto durante l'invio dei dati per il monitoraggio multilotto");
	  }
	  
	  if (logger.isDebugEnabled()) {
		  logger.debug("avviaMonitoraggio: fine metodo");
	  }

	  return mapping.findForward(target);
  }
  
  private boolean invioSchedaAggiudicazioneLotto(Long codgara, Long codlotto, final HttpServletRequest request, boolean isConfigurazioneVigilanza) {
	  boolean result = false;
	  TransactionStatus status = null;
	  String cig = "";
	  try {
		  cig = (String)this.sqlManager.getObject("select CIG from W9LOTT where CODGARA = ? and CODLOTT = ?", new Object[] { codgara, codlotto});
		  status = this.sqlManager.startTransaction();
		  DataColumn[] arrayDataW9FLUSSI = new DataColumn[18]; 

		  String versioneTracciatoXML = ConfigManager.getValore(CostantiSitatSA.VERSIONE_TRACCIATO_XML);
		  ProfiloUtente profilo = (ProfiloUtente) request.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
	    	 
		  arrayDataW9FLUSSI[0] = new DataColumn("W9FLUSSI.KEY01", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codgara));
		  arrayDataW9FLUSSI[1] = new DataColumn("W9FLUSSI.KEY02", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codlotto));
		  arrayDataW9FLUSSI[2] = new DataColumn("W9FLUSSI.KEY03", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
		  arrayDataW9FLUSSI[3] = new DataColumn("W9FLUSSI.KEY04", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
		  arrayDataW9FLUSSI[4] = new DataColumn("W9FLUSSI.AREA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
		  arrayDataW9FLUSSI[5] = new DataColumn("W9FLUSSI.IDFLUSSO", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, null));
		  arrayDataW9FLUSSI[6] = new DataColumn("W9FLUSSI.NOTEINVIO", new JdbcParametro(JdbcParametro.TIPO_TESTO, "Monitoraggio multilotto"));
		  arrayDataW9FLUSSI[7] = new DataColumn("W9FLUSSI.TINVIO2", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(2)));
		  arrayDataW9FLUSSI[8] = new DataColumn("W9FLUSSI.DATINV", new JdbcParametro(JdbcParametro.TIPO_DATA, null));
		  arrayDataW9FLUSSI[9] = new DataColumn("W9FLUSSI.VERXML", new JdbcParametro(JdbcParametro.TIPO_TESTO, versioneTracciatoXML));
		  arrayDataW9FLUSSI[10] = new DataColumn("W9FLUSSI.CODCOMP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(profilo.getId())));
		  arrayDataW9FLUSSI[11] = new DataColumn("W9FLUSSI.AUTORE", new JdbcParametro(JdbcParametro.TIPO_TESTO, profilo.getNome()));
		  String cfein = (String)this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN = ?", new Object[] { (String)request.getSession().getAttribute("uffint")});
		  arrayDataW9FLUSSI[12] = new DataColumn("W9FLUSSI.CFSA", new JdbcParametro(JdbcParametro.TIPO_TESTO, cfein));
		  
		  arrayDataW9FLUSSI[13] = new DataColumn("RECUPERAUSER", new JdbcParametro(JdbcParametro.TIPO_TESTO, request.getParameter("recuperauser")));
		  arrayDataW9FLUSSI[14] = new DataColumn("RECUPERAPASS", new JdbcParametro(JdbcParametro.TIPO_TESTO, request.getParameter("recuperapassword")));
		  arrayDataW9FLUSSI[15] = new DataColumn("MEMORIZZA", new JdbcParametro(JdbcParametro.TIPO_TESTO, request.getParameter("memorizza")));
		  arrayDataW9FLUSSI[16] = new DataColumn("SIMOGUSER", new JdbcParametro(JdbcParametro.TIPO_TESTO, request.getParameter("simoguser")));
		  arrayDataW9FLUSSI[17] = new DataColumn("SIMOGPASS", new JdbcParametro(JdbcParametro.TIPO_TESTO, request.getParameter("simogpass")));
		  
		  DataColumnContainer dataContW9FLUSSI = new DataColumnContainer(arrayDataW9FLUSSI);

		  dataContW9FLUSSI.getColumn("W9FLUSSI.IDFLUSSO").setChiave(true);

		  GestoreW9FLUSSI gestoreW9FLUSSI = new GestoreW9FLUSSI();
		  gestoreW9FLUSSI.setRequest(request);
		  gestoreW9FLUSSI.inserisci(status, dataContW9FLUSSI);
		  if (request.getAttribute("msgEsito") != null && request.getAttribute("msgEsito").equals("ok")) {
			  result = true;
		  } else {
			  String dettaglioErrore = "";
			  if (isConfigurazioneVigilanza) {
				  dettaglioErrore = "Per il dettaglio degli errori entrare nella scheda invii del lotto segnalato e visualizzare la scheda dell'ultimo flusso inviato";
			  }
			  request.setAttribute("errore", "Si sono verificati degli errori durante l'invio della fase di aggiudicazione per il CIG " + cig + ". <br>" + 
					  dettaglioErrore);
		  }
	  } catch (Exception ex) {
		  logger.error("Errore durante l'invio dell'aggiudicazione per il monitoraggio multilotto", ex);
		  String dettaglioErrore = "";
		  if (request.getAttribute("erroriValidazionePerDebug") != null) {
			  dettaglioErrore = " - " + request.getAttribute("erroriValidazionePerDebug");
		  }
		  request.setAttribute("errore", "Si sono verificati degli errori durante l'invio della fase di aggiudicazione per il CIG " + cig + ". <br>" +
				  "Dettaglio errore = " + ex.getMessage() + dettaglioErrore);
	  } finally {
		  if (status != null) {
	          try {
	        	  if (result) {
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
  
  private boolean invioAnagraficaGaraLotti(Long codgara, final HttpServletRequest request) {
	  boolean result = false;
	  TransactionStatus status = null;
	  try {
		  status = this.sqlManager.startTransaction();
		  DataColumn[] arrayDataW9FLUSSI = new DataColumn[13]; 

		  String versioneTracciatoXML = ConfigManager.getValore(CostantiSitatSA.VERSIONE_TRACCIATO_XML);
		  ProfiloUtente profilo = (ProfiloUtente) request.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
	    	 
		  arrayDataW9FLUSSI[0] = new DataColumn("W9FLUSSI.KEY01", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codgara));
		  arrayDataW9FLUSSI[1] = new DataColumn("W9FLUSSI.KEY02", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, null));
		  arrayDataW9FLUSSI[2] = new DataColumn("W9FLUSSI.KEY03", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(988)));
		  arrayDataW9FLUSSI[3] = new DataColumn("W9FLUSSI.KEY04", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, null));
		  arrayDataW9FLUSSI[4] = new DataColumn("W9FLUSSI.AREA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(2)));
		  arrayDataW9FLUSSI[5] = new DataColumn("W9FLUSSI.IDFLUSSO", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, null));
		  arrayDataW9FLUSSI[6] = new DataColumn("W9FLUSSI.NOTEINVIO", new JdbcParametro(JdbcParametro.TIPO_TESTO, "Monitoraggio multilotto"));
		  arrayDataW9FLUSSI[7] = new DataColumn("W9FLUSSI.TINVIO2", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(2)));
		  arrayDataW9FLUSSI[8] = new DataColumn("W9FLUSSI.DATINV", new JdbcParametro(JdbcParametro.TIPO_DATA, null));
		  arrayDataW9FLUSSI[9] = new DataColumn("W9FLUSSI.VERXML", new JdbcParametro(JdbcParametro.TIPO_TESTO, versioneTracciatoXML));
		  arrayDataW9FLUSSI[10] = new DataColumn("W9FLUSSI.CODCOMP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(profilo.getId())));
		  arrayDataW9FLUSSI[11] = new DataColumn("W9FLUSSI.AUTORE", new JdbcParametro(JdbcParametro.TIPO_TESTO, profilo.getNome()));
		  String cfein = (String)this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN = ?", new Object[] { (String)request.getSession().getAttribute("uffint")});
		  arrayDataW9FLUSSI[12] = new DataColumn("W9FLUSSI.CFSA", new JdbcParametro(JdbcParametro.TIPO_TESTO, cfein));
		  
		  DataColumnContainer dataContW9FLUSSI = new DataColumnContainer(arrayDataW9FLUSSI);

		  dataContW9FLUSSI.getColumn("W9FLUSSI.IDFLUSSO").setChiave(true);

		  GestoreW9FLUSSI gestoreW9FLUSSI = new GestoreW9FLUSSI();
		  gestoreW9FLUSSI.setRequest(request);
		  gestoreW9FLUSSI.inserisci(status, dataContW9FLUSSI);
		  if (request.getAttribute("msgEsito") != null && request.getAttribute("msgEsito").equals("ok")) {
			  result = true;
		  } else {
			  request.setAttribute("errore", "Si sono verificati degli errori durante l'invio dell'anagrafica gara lotti");
		  }
	  } catch (Exception ex) {
		  logger.error("Errore durante l'invio dell'anagrafica gara lotti per il monitoraggio multilotto", ex);
		  request.setAttribute("errore", ex.getMessage());
	  } finally {
		  if (status != null) {
	          try {
	        	  if (result) {
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
  
  /*private boolean cigMasterValorizzato(Long codgara) {
	  boolean result = false;
	  try {
		  Long cigMasterValorizzato = (Long) this.sqlManager.getObject("select count(*) from W9LOTT where CODGARA =  ? and CIG_MASTER_ML is not null", new Object[] { codgara});
		  if (cigMasterValorizzato != null && cigMasterValorizzato > 0) {
			  result = true;
		  }
	  } catch (Exception ex) {
		  logger.error("Errore durante la verifica di CIG Master presenti", ex);
	  }
	  return result;
  }*/
  
  private boolean resetCigMaster(Long codgara, List<Object> lottiDaSbiancare, String lottiDaInviare) {
	  boolean result = false;
	  TransactionStatus status = null;
	  try {
		  status = this.sqlManager.startTransaction();
		  if (lottiDaSbiancare != null) {
			  for (Object entry : lottiDaSbiancare) {
				  @SuppressWarnings("unchecked")
				  Map<String, Object> lotto = (Map<String, Object>)entry;
				  Long codlott = (Long)lotto.get("nlott");
				  this.sqlManager.update("update W9LOTT set CIG_MASTER_ML=null where CODGARA = ? and CODLOTT = ?", new Object[] { codgara, codlott});
			  }
		  } else if (lottiDaInviare != null) {
			  String[] arrayLottiDaInviare = lottiDaInviare.split("-");
			  for (int i = 0; i < arrayLottiDaInviare.length; i++) {
				  String codiceLotto = arrayLottiDaInviare[i].split(";")[1];
				  this.sqlManager.update("update W9LOTT set CIG_MASTER_ML=null where CODGARA = ? and CODLOTT = ?", new Object[] { codgara, new Long(codiceLotto)});
			  }
		  }
		  result = true;
	  } catch (Exception ex) {
		  logger.error("Errore durante l'azzeramento dei CIG Master presenti", ex);
	  }
	  finally {
		  if (status != null) {
	          try {
	        	  if (result) {
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
  
  private boolean valorizzaCigMaster(Long codgara, String lottiDaValorizzare) {
	  boolean result = false;
	  TransactionStatus status = null;
	  try {
		  status = this.sqlManager.startTransaction();
		  HashMap<String, String> raggruppamentoCigMasterMap = new HashMap<String,String>();
		  String[] lottiDaValorizzareArray = lottiDaValorizzare.split("-");
		  for (int i = 0; i<lottiDaValorizzareArray.length ; i ++) {
			  String[] lottoArray = lottiDaValorizzareArray[i].split(";");
			  if (lottoArray[2].equals("1")) {
				  //se il lotto è master recupero il cig
				  String cigMaster = (String) this.sqlManager.getObject("select CIG from W9LOTT where CODGARA = ? and CODLOTT = ?", new Object[] { codgara, new Long(lottoArray[1])});
				  if(!raggruppamentoCigMasterMap.containsKey(lottoArray[0])) {
					  raggruppamentoCigMasterMap.put(lottoArray[0], cigMaster);
				  }
			  }
		  }
		  for (int i = 0; i<lottiDaValorizzareArray.length ; i ++) {
			  String[] lottoArray = lottiDaValorizzareArray[i].split(";");
			  String cigMaster = raggruppamentoCigMasterMap.get(lottoArray[0]);
			  this.sqlManager.update("update W9LOTT set CIG_MASTER_ML = ? where CODGARA = ? and CODLOTT = ?", new Object[] { cigMaster, codgara, new Long(lottoArray[1])});
		  }
		  
		  result = true;
	  } catch (Exception ex) {
		  logger.error("Errore durante la valorizzazione del CIG Master nei lotti accorpati", ex);
	  }
	  finally {
		  if (status != null) {
	          try {
	        	  if (result) {
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
  
@SuppressWarnings("rawtypes")
private List<Object> getLotti(Long codgara, boolean verificaCigMasterPresente) {

	  List<Object> risultato = new ArrayList<Object>();
	  try {
		  List< ? > lottiAggiudicati = this.sqlManager.getListVector(
			        "select w9lott.codlott from w9lott left join w9fasi on w9lott.codgara=w9fasi.codgara and w9lott.codlott=w9fasi.codlott " + 
			        "where fase_esecuzione=1 and w9fasi.daexport='2' and w9lott.codgara=? and w9lott.codlott not in " + 
			        "(select codlott from w9fasi where codgara=? and fase_esecuzione in (?,?,?,?,?,?,?,?,?,?))",
			        new Object[] { codgara, codgara, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA,
			        		CostantiW9.STIPULA_ACCORDO_QUADRO,
			        		CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA,
			        		CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA,
			        		CostantiW9.COLLAUDO_CONTRATTO,
			        		CostantiW9.ACCORDO_BONARIO,
			        		CostantiW9.SOSPENSIONE_CONTRATTO,
			        		CostantiW9.VARIANTE_CONTRATTO,
			        		CostantiW9.SUBAPPALTO,
			        		CostantiW9.ISTANZA_RECESSO
			        		});
		  HashMap<String, List< Long >> aggiudicatariLottiMap = new HashMap<String, List< Long >>();
		  if (lottiAggiudicati != null && lottiAggiudicati.size() > 1) {
		      for (int i = 0; i < lottiAggiudicati.size(); i++) {
		    	  Long codlott = (Long) SqlManager.getValueFromVectorParam(lottiAggiudicati.get(i), 0).getValue();
		    	  List< ? > aggiudicatari = sqlManager.getListVector("select CODIMP from W9AGGI where CODGARA=? and CODLOTT=? order by CODIMP", new Object[] {codgara, codlott});
		    	  String impreseAggiudicatrici = "";
		    	  if (aggiudicatari != null && aggiudicatari.size() > 0) {
		    		  for (int j = 0; j < aggiudicatari.size(); j++) {
		    			  String codimp = (String) SqlManager.getValueFromVectorParam(aggiudicatari.get(j), 0).getValue();
		    			  impreseAggiudicatrici += codimp + "-";
		    		  }
		    		  List<Long> listaLotti = null;
		    		  if (aggiudicatariLottiMap.containsKey(impreseAggiudicatrici)) {
		    			  listaLotti = aggiudicatariLottiMap.get(impreseAggiudicatrici);
		    			  listaLotti.add(codlott);
		    		  } else {
		    			  listaLotti = new ArrayList<Long>();
		    			  listaLotti.add(codlott);
		    			  aggiudicatariLottiMap.put(impreseAggiudicatrici, listaLotti);
		    		  }
		    	  }
		      }
		  }
		  int raggruppamento = 1;
		  for (Map.Entry item : aggiudicatariLottiMap.entrySet()) {
			  @SuppressWarnings("unchecked")
			  List<Long> listaLotti = (List<Long>)item.getValue();
			  if (listaLotti.size() > 1) {
				  for (Long codice : listaLotti) {
					  List<?> datiLotto = null;
					  //recupero i dati del lotto
					  if (verificaCigMasterPresente) {
						  datiLotto = this.sqlManager.getVector("select NLOTTO,CIG,OGGETTO,IMPORTO_LOTTO from W9LOTT where CODGARA=? and CODLOTT=? and CIG_MASTER_ML is not null", new Object[] { codgara, codice });
					  } else {
						  datiLotto = this.sqlManager.getVector("select NLOTTO,CIG,OGGETTO,IMPORTO_LOTTO from W9LOTT where CODGARA=? and CODLOTT=?", new Object[] { codgara, codice });
					  }
					  if (datiLotto != null && datiLotto.size() > 0) {
				    	  Map<String, Object> mappaLotto = new HashMap<String, Object>();
				    	  mappaLotto.put("nlott", (Long) SqlManager.getValueFromVectorParam(datiLotto, 0).getValue());
				    	  mappaLotto.put("cig", (String) SqlManager.getValueFromVectorParam(datiLotto, 1).getValue());
				    	  mappaLotto.put("oggetto", (String) SqlManager.getValueFromVectorParam(datiLotto, 2).getValue());
				    	  mappaLotto.put("importo", (Double) SqlManager.getValueFromVectorParam(datiLotto, 3).getValue());
				    	  mappaLotto.put("raggruppamento", raggruppamento);
				    	  mappaLotto.put("visibile", 1);
				    	  mappaLotto.put("accoppiato", 0);
				    	  mappaLotto.put("master", 0);
				    	  risultato.add(mappaLotto);
				      }
				  }
				  raggruppamento ++;
			  }
			  
	      }
	  } catch(Exception ex) {
		  risultato = null;
		  logger.error("Errore durante l'estrazione dei dati per il multilotto", ex);
	  }
	  return risultato;
  }

}
