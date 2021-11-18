/*
 * Created on 10/feb/11
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.bl;

import javax.servlet.jsp.JspException;

import it.eldasoft.contratti.rlo.xmlbeans.AnomaliaType;
import it.eldasoft.contratti.rlo.xmlbeans.FeedBackDocument;
import it.eldasoft.contratti.rlo.xmlbeans.FlussoType;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.BlobFile;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.metadata.cache.DizionarioCampi;
import it.eldasoft.utils.metadata.domain.Campo;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.dao.W9FileDao;
import it.eldasoft.w9.schede.sitar.xmlbeans.PeFiAltriIncarichi;
import it.eldasoft.w9.schede.sitar.xmlbeans.PeFiIncarichiCollaudo;
import it.eldasoft.w9.schede.sitar.xmlbeans.PeFiIncarichiIEC;
import it.eldasoft.w9.schede.sitar.xmlbeans.PeFiProgett;
import it.eldasoft.w9.schede.sitar.xmlbeans.PeGiIEC;
import it.eldasoft.w9.schede.sitar.xmlbeans.PeGiSub;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAccordoBonarioDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggLavoriSezOssAdesioneDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggLavoriSezOssAdesioneDocument.SchedaAggLavoriSezOssAdesione;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggLavoriSezOssDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggLavoriSezOssDocument.SchedaAggLavoriSezOss;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggLavoriSezOssDocument.SchedaAggLavoriSezOss.AggiudicazioneLavoriSezioneOsservatorio;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggServFornSezOssAdesioneDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggServFornSezOssAdesioneDocument.SchedaAggServFornSezOssAdesione;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggServFornSezOssDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggServFornSezOssDocument.SchedaAggServFornSezOss;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggServFornSezOssDocument.SchedaAggServFornSezOss.AggiudicazioneServFornSezioneOsservatorio;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggiudicazioneSezOssRidDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggiudicazioneSezOssRidDocument.SchedaAggiudicazioneSezOssRid;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAggiudicazioneSezOssRidDocument.SchedaAggiudicazioneSezOssRid.AggiudicazioneSezioneOsservatorioRidotta;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaBandoLavoriDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaBandoLavoriDocument.SchedaBandoLavori;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaBandoLavoriDocument.SchedaBandoLavori.BandoLavori;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaBandoServFornDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaBandoServFornDocument.SchedaBandoServForn;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaBandoServFornDocument.SchedaBandoServForn.BandoServiziForniture;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaChiusuraAnticipataContrattoDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaChiusuraAnticipataContrattoDocument.SchedaChiusuraAnticipataContratto;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaChiusuraAnticipataContrattoDocument.SchedaChiusuraAnticipataContratto.ChiusuraAnticipataContratto;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaCollaudoDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaCollaudoDocument.SchedaCollaudo.Collaudo;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaConclusioneContrattoDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaConclusioneContrattoDocument.SchedaConclusioneContratto.ConclusioneContratto;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaSALDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaSALDocument.SchedaSAL;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaSospensioneDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaCollaudoDocument.SchedaCollaudo.Collaudo.IncarichiCollaudo;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaIecDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAccordoBonarioDocument.SchedaAccordoBonario;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaAccordoBonarioDocument.SchedaAccordoBonario.AccordoBonario;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaCollaudoDocument.SchedaCollaudo;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaConclusioneContrattoDocument.SchedaConclusioneContratto;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaIecDocument.SchedaIec;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaIecDocument.SchedaIec.InizioEsecuzioneContratto;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaIecDocument.SchedaIec.InizioEsecuzioneContratto.IncarichiIEC;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaIecDocument.SchedaIec.InizioEsecuzioneContratto.PosContrImprese;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaSospensioneDocument.SchedaSospensione;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaSospensioneDocument.SchedaSospensione.Sospensione;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaSubappaltoDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaSubappaltoDocument.SchedaSubappalto;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaSubappaltoDocument.SchedaSubappalto.Subappalto;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaSubappaltoDocument.SchedaSubappalto.Subappalto.ImpreseSubappalto;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaUltimazLavoriSottosogliaDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaUltimazLavoriSottosogliaDocument.SchedaUltimazLavoriSottosoglia;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaUltimazLavoriSottosogliaDocument.SchedaUltimazLavoriSottosoglia.UltimazioneLavoriSott;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaVarianteLavDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaVarianteLavDocument.SchedaVarianteLav;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaVarianteServFornDocument;
import it.eldasoft.w9.schede.sitar.xmlbeans.SchedaVarianteServFornDocument.SchedaVarianteServForn;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.springframework.transaction.TransactionStatus;

/**
 * Classe di utilita' per l'esportazione in formato XML.
 * 
 * @author Stefano.Cestaro
 * @edit Eliseo Marini
 * @version Adeguamento SIMOG 3.0.2
 * 


 */
public class ExportSitarManager {
	/** Logger. */
	static Logger logger = Logger.getLogger(ExportSitarManager.class);

	private final String SQL_DATI_LOTTO = "SELECT CPV, CIG, CUP, LUOGO_ISTAT, IMPORTO_TOT, IMPORTO_ATTUAZIONE_SICUREZZA, ID_CATEGORIA_PREVALENTE, CLASCAT, ID_MODO_GARA, ID_SCELTA_CONTRAENTE, LUOGO_NUTS, ID_TIPO_PRESTAZIONE FROM W9LOTT WHERE CODGARA = ? AND CODLOTT = ?";
	private final String SQL_DATI_GARA = "SELECT FLAG_ENTE_SPECIALE, NLOTTI, TIPO_APP, IMPORTO_GARA, DSCADE FROM W9GARA WHERE CODGARA = ?";
	private final String SQL_DATI_APPALTO = "SELECT COD_STRUMENTO, FLAG_RICH_SUBAPPALTO, PERC_OFF_AUMENTO, NUM_IMP_ESCL_INSUF_GIUST, NUM_OFFERTE_ESCLUSE, NUM_OFFERTE_FUORI_SOGLIA, VAL_SOGLIA_ANOMALIA, OFFERTA_MASSIMO, OFFERTA_MINIMA, NUM_MANIF_INTERESSE, DATA_INVITO, DATA_SCADENZA_RICHIESTA_INVITO, DATA_MANIF_INTERESSE, FLAG_ACCORDO_QUADRO, IMPORTO_LAVORI, IMPORTO_SERVIZI, IMPORTO_FORNITURE, IMPORTO_ATTUAZIONE_SICUREZZA, IMP_NON_ASSOG, IMPORTO_PROGETTAZIONE, IMPORTO_DISPOSIZIONE, ID_MODO_INDIZIONE, IMPORTO_COMPL_APPALTO, DATA_STIPULA, DURATA_CON, TERMINE_RIDOTTO, PREINFORMAZIONE, PROCEDURA_ACC, IMPORTO_SUBTOTALE, DATA_SCADENZA_PRES_OFFERTA, ASTA_ELETTRONICA FROM W9APPA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ?";
	private final String SQL_DATI_PUBB_ESITO = "SELECT DATA_GUCE, DATA_GURI, DATA_ALBO, QUOTIDIANI_NAZ, QUOTIDIANI_REG, PROFILO_COMMITTENTE, SITO_MINISTERO_INF_TRASP, SITO_OSSERVATORIO_CP FROM W9PUES WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ? AND NUM_PUES = 1";
	private final String SQL_DATI_PUBB_BANDO = "SELECT PROFILO_COMMITTENTE, SITO_MINISTERO_INF_TRASP, SITO_OSSERVATORIO_CP, DATA_GUCE, DATA_ALBO, DATA_GURI, QUOTIDIANI_NAZ, QUOTIDIANI_REG FROM W9PUBB WHERE CODGARA = ? AND codlott = 1 and NUM_APPA = 1";
	private final String SQL_DATI_CATEGORIE = "select CATEGORIA, CLASCAT, SCORPORABILE, SUBAPPALTABILE from W9LOTTCATE where CODGARA = ? and CODLOTT = ? ORDER BY NUM_CATE";
	private final String SQL_DATI_CONDIZIONE = "select ID_CONDIZIONE from W9COND where CODGARA = ? AND CODLOTT = ? ";
	private final String SQL_DATI_INIZ = "SELECT DATA_STIPULA, DATA_ESECUTIVITA, IMPORTO_CAUZIONE, DATA_INI_PROG_ESEC, DATA_APP_PROG_ESEC, FLAG_FRAZIONATA, DATA_VERBALE_CONS, DATA_VERBALE_DEF, FLAG_RISERVA, DATA_VERB_INIZIO, DATA_TERMINE, DATA_DECORRENZA, DATA_SCADENZA, INCONTRI_VIGIL FROM W9INIZ WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ?";
	private final String SQL_DATI_AVANZAMENTO ="SELECT DATA_CERTIFICATO, IMPORTO_CERTIFICATO, FLAG_RITARDO, NUM_GIORNI_SCOST, NUM_GIORNI_PROROGA, FLAG_PAGAMENTO, DATA_ANTICIPAZIONE, IMPORTO_ANTICIPAZIONE, DATA_RAGGIUNGIMENTO, IMPORTO_SAL FROM W9AVAN WHERE CODGARA = ? AND CODLOTT = ? AND NUM_AVAN = ?";
	private final String SQL_DATI_CONCLUSIONE ="SELECT ID_MOTIVO_INTERR, ID_MOTIVO_RISOL, DATA_RISOLUZIONE, FLAG_ONERI, ONERI_RISOLUZIONE, FLAG_POLIZZA, DATA_ULTIMAZIONE, NUM_INFORTUNI, NUM_INF_PERM, NUM_INF_MORT, ORE_LAVORATE FROM W9CONC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_CONC = ?";
	private final String SQL_DATI_COLLAUDO ="SELECT DATA_REGOLARE_ESEC, DATA_COLLAUDO_STAT, MODO_COLLAUDO, DATA_NOMINA_COLL, DATA_INIZIO_OPER, DATA_CERT_COLLAUDO, DATA_DELIBERA, ESITO_COLLAUDO, IMP_FINALE_LAVORI, IMP_FINALE_SERVIZI, IMP_FINALE_FORNIT, IMP_FINALE_SECUR, IMP_PROGETTAZIONE, IMP_DISPOSIZIONE, AMM_NUM_DEFINITE, AMM_NUM_DADEF, AMM_IMPORTO_RICH, AMM_IMPORTO_DEF, ARB_NUM_DEFINITE, ARB_NUM_DADEF, ARB_IMPORTO_RICH, ARB_IMPORTO_DEF, GIU_NUM_DEFINITE, GIU_NUM_DADEF, GIU_IMPORTO_RICH, GIU_IMPORTO_DEF, TRA_NUM_DEFINITE, TRA_NUM_DADEF, TRA_IMPORTO_RICH, TRA_IMPORTO_DEF, IMP_COMPL_APPALTO, IMP_COMPL_INTERVENTO, IMP_SUBTOTALE, LAVORI_ESTESI FROM W9COLL WHERE CODGARA = ? AND CODLOTT = ? AND NUM_COLL = ?";
	private final String SQL_DATI_SOSPENSIONE ="SELECT DATA_VERB_SOSP, DATA_VERB_RIPR, ID_MOTIVO_SOSP, FLAG_SUPERO_TEMPO, FLAG_RISERVE, FLAG_VERBALE FROM W9SOSP WHERE CODGARA = ? AND CODLOTT = ? AND NUM_SOSP = ?";
	private final String SQL_DATI_VARIANTE ="SELECT DATA_VERB_APPR, ALTRE_MOTIVAZIONI, IMP_RIDET_LAVORI, IMP_RIDET_SERVIZI, IMP_RIDET_FORNIT, IMP_SICUREZZA, IMP_PROGETTAZIONE, IMP_DISPOSIZIONE, DATA_ATTO_AGGIUNTIVO, NUM_GIORNI_PROROGA, IMP_SUBTOTALE, IMP_COMPL_APPALTO, IMP_COMPL_INTERVENTO FROM W9VARI WHERE CODGARA = ? AND CODLOTT = ? AND NUM_VARI = ?";
	private final String SQL_DATI_MOTIVAZIONI ="SELECT ID_MOTIVO_VAR FROM W9MOTI WHERE CODGARA = ? AND CODLOTT = ? AND NUM_VARI = ?";
	private final String SQL_DATI_ACCORDO ="SELECT DATA_ACCORDO, ONERI_DERIVANTI, NUM_RISERVE FROM W9ACCO WHERE CODGARA = ? AND CODLOTT = ? AND NUM_ACCO = ?";
	private final String SQL_DATI_SUBAPPALTO ="SELECT DATA_AUTORIZZAZIONE, OGGETTO_SUBAPPALTO, IMPORTO_PRESUNTO, IMPORTO_EFFETTIVO, ID_CATEGORIA, ID_CPV, CODIMP, CODIMP_AGGIUDICATRICE FROM W9SUBA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_SUBA = ?";
	private final String SQL_DATI_APPALAV ="select ID_APPALTO from W9APPALAV where CODGARA = ? AND CODLOTT = ? AND NUM_APPAL = ?";
	private final String SQL_DATI_APPAFORN ="select ID_APPALTO from W9APPAFORN where CODGARA = ? AND CODLOTT = ? AND NUM_APPAF = ?";
	private final String SQL_DATI_CPV = "SELECT CPV FROM W9CPV WHERE CODGARA = ? AND CODLOTT = ? ORDER BY NUM_CPV";
	  
	private final String SQL_DATI_FINANZIAMENTI="SELECT ID_FINANZIAMENTO, IMPORTO_FINANZIAMENTO FROM W9FINA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ?";
	
			
	private final String SQL_DATI_INCARICHI_AGGIUDICAZIONE = "select ID_RUOLO, W9INCA.CODTEC, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, DATA_CONS_PROG_ESTERNA from W9INCA join TECNI on W9INCA.CODTEC=TECNI.CODTEC where CODGARA = ? AND CODLOTT = ? AND NUM = ? AND ID_RUOLO IN(?,?,?,?) AND (SEZIONE = 'PA' OR SEZIONE = 'RA')";
	private final String SQL_DATI_INCARICHI_ADESIONE = "select ID_RUOLO, W9INCA.CODTEC, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, DATA_CONS_PROG_ESTERNA from W9INCA join TECNI on W9INCA.CODTEC=TECNI.CODTEC where CODGARA = ? AND CODLOTT = ? AND NUM = ? AND ID_RUOLO IN(?,?,?,?) AND SEZIONE = 'RQ'";
	private final String SQL_DATI_INCARICHI_INIZIO ="SELECT ID_RUOLO, W9INCA.CODTEC FROM W9INCA join TECNI on W9INCA.CODTEC=TECNI.CODTEC WHERE CODGARA = ? AND CODLOTT = ? AND NUM = ? AND ID_RUOLO IN(?,?,?,?,?,?) AND SEZIONE = 'IN'";
	private final String SQL_DATI_INCARICHI_COLLAUDO ="SELECT ID_RUOLO, W9INCA.CODTEC FROM W9INCA join TECNI on W9INCA.CODTEC=TECNI.CODTEC WHERE CODGARA = ? AND CODLOTT = ? AND NUM = ? AND ID_RUOLO IN(?,?,?,?,?) AND SEZIONE = 'CO'";
	private final String SQL_DATI_ALTRI_INCARICHI ="SELECT ID_RUOLO, W9INCA.CODTEC FROM W9INCA join TECNI on W9INCA.CODTEC=TECNI.CODTEC WHERE CODGARA = ? AND CODLOTT = ? AND NUM = ? AND ID_RUOLO IN(?,?,?,?,?)";
	
	private final String SQL_UPDATE_CUI = "update w9lott set codcui = ? where cig = ?";
	
	private final String SQL_DATI_POSIZIONE_AGGI = "select CODIMP, RUOLO, CODICE_INPS, CODICE_INAIL, CODICE_CASSA from W9AGGI, W9FASI where W9AGGI.CODGARA = ? AND W9AGGI.CODLOTT = ? AND W9FASI.CODGARA = W9AGGI.CODGARA AND W9FASI.CODLOTT = W9AGGI.CODLOTT AND W9FASI.FASE_ESECUZIONE = 2";
    
    
	/** Manager SQL per le operazioni su database. */
	private SqlManager sqlManager;
	
	/** DAO per la gestione dei file allegati. */
	private W9FileDao  w9FileDao;

	public void setSqlManager(SqlManager sqlManager) {
	    this.sqlManager = sqlManager;
	  }
	
	/**
	   * Set fileDao.
	   * 
	   * @param fileDao
	   *        w9FileDao da settare internamente alla classe.
	   */
	  public void setW9FileDao(W9FileDao fileDao) {
	    this.w9FileDao = fileDao;
	  }
	  
	/**
	   * Fase Bando Lavori Sitar.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numAppa Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaBandoLavori(final long codGara,
		      final long codLott, final long numAppa) throws GestoreException {

		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaBandoLavori: inizio metodo");
		  } 
		  
		  DataColumnContainer w9gara, w9lott, w9appa, w9pubb, w9appalav;
		  SchedaBandoLavoriDocument doc = SchedaBandoLavoriDocument.Factory.newInstance();
		  
		  try {
			  
			  SchedaBandoLavori schedaBandoLavori = doc.addNewSchedaBandoLavori();
			  SchedaBandoLavori.Settings settings = schedaBandoLavori.addNewSettings();
			  w9gara = new DataColumnContainer(sqlManager, "W9GARA", SQL_DATI_GARA, new Object[] {codGara});
			  w9lott = new DataColumnContainer(sqlManager, "W9LOTT", SQL_DATI_LOTTO, new Object[] {codGara, codLott});
			  w9pubb = new DataColumnContainer(sqlManager, "W9PUBB", SQL_DATI_PUBB_BANDO, new Object[] {codGara});
			  
			  try {
				  w9appa = new DataColumnContainer(sqlManager, "W9APPA", SQL_DATI_APPALTO, new Object[] {codGara, codLott, numAppa});
			  } catch (Exception ex) {
				  w9appa = null;
			  }
			  
			  try {
				  w9appalav = new DataColumnContainer(sqlManager, "W9APPALAV", SQL_DATI_APPALAV, new Object[] {codGara, codLott, numAppa});
			  } catch (Exception ex) {
				  w9appalav = null;
			  }
			  
			  settings.setTiposcheda("BandoLavori");
			  SchedaBandoLavori.BandoLavori bando = schedaBandoLavori.addNewBandoLavori();

			  Calendar date = Calendar.getInstance();
			  
			  if (w9gara.getString("FLAG_ENTE_SPECIALE") != null) {
				  bando.setAppartenenteA((w9gara.getString("FLAG_ENTE_SPECIALE").equals("O"))?"settori ordinari":"settori speciali");
			  }
			  
			  bando.setInfrastrutturaStrategica("N");
			  bando.setDaRealizzarsiCon("appalto");
			  bando.setContraenteGenerale("N");
			  
			  if (w9gara.getLong("TIPO_APP") != null) {
				  int tipoAppalto = w9gara.getLong("TIPO_APP").intValue();
				  switch (tipoAppalto) {
				  	case 3:
				  	case 4:
				  		bando.setDaRealizzarsiCon("concessione");
				  		break;
				  	case 6:
				  		bando.setContraenteGenerale("Y");
				  		break;
				  }
			  }
			  
			  bando.setDivisioneLotti((w9gara.getLong("NLOTTI") != null && w9gara.getLong("NLOTTI") > 1)?"Y":"N");
			  bando.setNumeroLotti((w9gara.getLong("NLOTTI") != null)?new BigInteger(w9gara.getLong("NLOTTI").toString()):new BigInteger("0"));
			  
			  if(w9lott.getString("CPV") != null) {
				  bando.setTipologiaCpvLav(w9lott.getString("CPV"));
			  } else {
				  bando.setTipologiaCpvLav("");
			  }
			  //bando.setTipologiaCpvLav2("");
			  //bando.setTipologiaCpvLav3("");
			  
			  List< ? > w9cpv = sqlManager.getListVector(SQL_DATI_CPV, new Object[] {codGara, codLott});
			  if (w9cpv != null && w9cpv.size() > 0) {
				  for (int i = 0; i < w9cpv.size(); i++) {
					  if (i == 0) {
						  bando.setTipologiaCpvLav2(w9cpv.get(i).toString());
					  } else if (i == 1) {
						  bando.setTipologiaCpvLav3(w9cpv.get(i).toString());
					  }
				  } 
			  }
			  
			  if (w9appalav != null && w9appalav.getLong("ID_APPALTO") != null) {
				  bando.setTipologiaIntervento(Integer.parseInt(codificaSitatToSitar("W3002", w9appalav.getLong("ID_APPALTO").toString())));
			  }
			
			  if(w9lott.getString("CIG") != null) {
				  bando.setCig(w9lott.getString("CIG"));
			  } else {
				  bando.setCig("");
			  }
			  
			  if(verificaEsistenzaValore(w9lott.getString("CUP"))) {
				  bando.setCup(w9lott.getString("CUP"));
			  }
			  
			  if(verificaEsistenzaValore(w9lott.getString("LUOGO_ISTAT"))) {
				  bando.setCodIstatLocEsec(w9lott.getString("LUOGO_ISTAT"));
			  }
			  
			  if (w9gara.getDouble("IMPORTO_GARA") != null) {
				  bando.setImpBase(new BigDecimal(convertiImporto(w9gara.getDouble("IMPORTO_GARA"))));
			  } else {
				  bando.setImpBase(new BigDecimal(0));
			  }
			  
			  bando.setPrevOneriSicurez((w9lott.getDouble("IMPORTO_ATTUAZIONE_SICUREZZA") != null && w9lott.getDouble("IMPORTO_ATTUAZIONE_SICUREZZA") > 0)?"Y":"N");
			  if (w9lott.getDouble("IMPORTO_ATTUAZIONE_SICUREZZA") != null) {
				  bando.setImpOneriSicurez(new BigDecimal(convertiImporto(w9lott.getDouble("IMPORTO_ATTUAZIONE_SICUREZZA"))));
			  } else {
				  bando.setNilImpOneriSicurez();
			  }
			  
			  //******************************************************
			  if (w9lott.getLong("ID_SCELTA_CONTRAENTE") != null) {
				  bando.setProcedGaraId(codificaSitatToSitar("W3005", w9lott.getLong("ID_SCELTA_CONTRAENTE").toString()));
			  }
			  
			  bando.setAstaElettronica((w9appa != null && w9appa.getString("ASTA_ELETTRONICA")!=null && w9appa.getString("ASTA_ELETTRONICA").equals("1"))?"Y":"N");
			  bando.setForcella("N"); 
			  bando.setRiservato("N");
			  
			  if (w9pubb.getData("DATA_GUCE") != null) {
				  date.setTime(w9pubb.getData("DATA_GUCE"));
				  bando.setPubGuCee(date);
			  } 
			  
			  if (w9pubb.getData("DATA_GURI") != null) {
				  date.setTime(w9pubb.getData("DATA_GURI"));
				  bando.setPubGuRi(date);
			  } 
			  
			  if (w9pubb.getData("DATA_ALBO") != null) {
				  date.setTime(w9pubb.getData("DATA_ALBO"));
				  bando.setPubAlboPret(date);
			  } 
			  
			  if(verificaEsistenzaValore(w9pubb.getString("PROFILO_COMMITTENTE"))) {
				  bando.setProfiloCommittente((w9pubb.getString("PROFILO_COMMITTENTE").equals("1"))?"Y":"N");
			  }
			  
			  if(verificaEsistenzaValore(w9pubb.getLong("QUOTIDIANI_NAZ"))) {
				  bando.setPubQuotNaz(w9pubb.getLong("QUOTIDIANI_NAZ").intValue());
			  }
			  
			  if(verificaEsistenzaValore(w9pubb.getLong("QUOTIDIANI_REG"))) {
				  bando.setPubQuotLoc(w9pubb.getLong("QUOTIDIANI_REG").intValue());
			  }
			  
			  if (w9appa != null) {
				  bando.setProcAcc((w9appa.getString("PROCEDURA_ACC")!=null && w9appa.getString("PROCEDURA_ACC").equals("1"))?"Y":"N");
				  bando.setPreinfo((w9appa.getString("PREINFORMAZIONE")!=null && w9appa.getString("PREINFORMAZIONE").equals("1"))?"Y":"N");
				  bando.setTermRidPreinfo((w9appa.getString("TERMINE_RIDOTTO")!=null && w9appa.getString("TERMINE_RIDOTTO").equals("1"))?"Y":"N");
			  } else {
				  bando.setProcAcc("N");
				  bando.setPreinfo("N");
				  bando.setTermRidPreinfo("N");
			  }
			  
			  if (w9appa != null && w9appa.getData("DATA_SCADENZA_PRES_OFFERTA") != null) {
				  date.setTime(w9appa.getData("DATA_SCADENZA_PRES_OFFERTA"));
				  bando.setDataScadenza(date);
			  } else if (w9gara.getData("DSCADE") != null) {
				  date.setTime(w9gara.getData("DSCADE"));
				  bando.setDataScadenza(date);
			  }
			  
			  bando.setNilImpCorpo();
			  bando.setNilImpMis();
			  bando.setNilImpCorpoMis();
			  
			  String categoriaPrevalente = null;
			  String impCategoriaPrevalente = null;
			  if (w9lott.getString("ID_CATEGORIA_PREVALENTE") != null) {
				  categoriaPrevalente = codificaSitatToSitar("W3z03", w9lott.getString("ID_CATEGORIA_PREVALENTE"));
			  }
			  if (w9lott.getString("CLASCAT") != null) {
				  impCategoriaPrevalente = codificaSitatToSitar("W3z11", w9lott.getString("CLASCAT"));
			  }
			  
			  if (categoriaPrevalente!=null) {
				  bando.setCatPrevId(Integer.parseInt(categoriaPrevalente));
			  }
			  if (impCategoriaPrevalente!=null) {
				  bando.setImpCatPrevId(Integer.parseInt(impCategoriaPrevalente));
			  }
			  
			  List< ? > w9cate = sqlManager.getListVector(SQL_DATI_CATEGORIE, new Object[] {codGara, codLott});
			  if (w9cate != null && w9cate.size() > 0) {
				  for (int i = 0; i < w9cate.size(); i++) {
					  List< ?> cate = (List< ? >)w9cate.get(i);
					  categoriaPrevalente = null;
					  impCategoriaPrevalente = null;
					  if (cate.get(0) != null) {
						  categoriaPrevalente = codificaSitatToSitar("W3z03", cate.get(0).toString());
					  }
					  if (cate.get(1) != null) {
						  impCategoriaPrevalente = codificaSitatToSitar("W3z11", cate.get(1).toString());
					  }
					  switch (i) {
					  	case 0:
					  		if (categoriaPrevalente != null) {
					  			bando.setCatScorp1Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			bando.setImpCatScorp1Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 1:
					  		if (categoriaPrevalente != null) {
					  			bando.setCatScorp2Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			bando.setImpCatScorp2Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 2:
					  		if (categoriaPrevalente != null) {
					  			bando.setCatScorp3Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			bando.setImpCatScorp3Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 3:
					  		if (categoriaPrevalente != null) {
					  			bando.setCatScorp4Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			bando.setImpCatScorp4Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 4:
					  		if (categoriaPrevalente != null) {
					  			bando.setCatScorp5Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			bando.setImpCatScorp5Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 5:
					  		if (categoriaPrevalente != null) {
					  			bando.setCatScorp6Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			bando.setImpCatScorp6Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 6:
					  		if (categoriaPrevalente != null) {
					  			bando.setCatScorp7Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			bando.setImpCatScorp7Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  }
				  }
			  }
			  
			  bando.setTermEsecLav((w9appa != null && w9appa.getLong("DURATA_CON") != null)?w9appa.getLong("DURATA_CON").intValue():0);
			  bando.setGeoarea("N");

			  HashMap<String, Object> hashMapFileAllegato = new HashMap<String, Object>();
		      hashMapFileAllegato.put("codGara", codGara);
		      hashMapFileAllegato.put("numdoc", 1);
		      hashMapFileAllegato.put("num_pubb", 1);
		      BlobFile w4Blob = this.w9FileDao.getFileAllegato("GARA", hashMapFileAllegato);
		      
		      if (w4Blob != null) {
		    	  bando.setAllegatoFlag("Y");
				  bando.setAllegatoValue(w4Blob.getStream());
				  bando.setAllegatoName("Bando di gara");
				  bando.setAllegatoContentType("application/pdf");
		      } else {
		    	  bando.setAllegatoFlag("N");
				  bando.setAllegatoValue(new byte[0]);
				  bando.setAllegatoName("-");
				  bando.setAllegatoContentType("application/pdf");
		      }

		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaBandoLavori", "getSchedaBandoLavori", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaBandoLavori: fine metodo");
		  }
		  
		  return doc;
	  }
	  
	  /**
	   * Fase Bando Lavori Sitar.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numAppa Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaBandoServForn(final long codGara,
		      final long codLott, final long numAppa) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaBandoServForn: inizio metodo");
		  } 
		  
		  DataColumnContainer w9gara, w9lott, w9appa, w9pubb;
		  SchedaBandoServFornDocument doc = SchedaBandoServFornDocument.Factory.newInstance();
		  
		  try {
			  
			  SchedaBandoServForn schedaBandoServForn = doc.addNewSchedaBandoServForn();
			  SchedaBandoServForn.Settings settings = schedaBandoServForn.addNewSettings();
			  w9gara = new DataColumnContainer(sqlManager, "W9GARA", SQL_DATI_GARA, new Object[] {codGara});
			  w9lott = new DataColumnContainer(sqlManager, "W9LOTT", SQL_DATI_LOTTO, new Object[] {codGara, codLott});
			  w9pubb = new DataColumnContainer(sqlManager, "W9PUBB", SQL_DATI_PUBB_BANDO, new Object[] {codGara});
			  
			  try {
				  w9appa = new DataColumnContainer(sqlManager, "W9APPA", SQL_DATI_APPALTO, new Object[] {codGara, codLott, numAppa});
			  } catch (Exception ex) {
				  w9appa = null;
			  }
			  
			  settings.setTiposcheda("BandoServiziForniture");
			  SchedaBandoServForn.BandoServiziForniture bando = schedaBandoServForn.addNewBandoServiziForniture();

			  Calendar date = Calendar.getInstance();
			  
			  bando.setDivisioneLotti((w9gara.getLong("NLOTTI") != null && w9gara.getLong("NLOTTI") > 1)?"Y":"N");
			  bando.setNumeroLotti((w9gara.getLong("NLOTTI") != null)?new BigInteger(w9gara.getLong("NLOTTI").toString()):new BigInteger("0"));
			  
			  bando.setTipologiaCpv(w9lott.getString("CPV"));
			  
			  List< ? > w9cpv = sqlManager.getListVector(SQL_DATI_CPV, new Object[] {codGara, codLott});
			  if (w9cpv != null && w9cpv.size() > 0) {
				  for (int i = 0; i < w9cpv.size(); i++) {
					  if (i == 0) {
						  bando.setTipologiaCpv2(w9cpv.get(i).toString());
					  } else if (i == 1) {
						  bando.setTipologiaCpv3(w9cpv.get(i).toString());
					  }
				  } 
			  }
			
			  bando.setCig(w9lott.getString("CIG"));
			  if(verificaEsistenzaValore(w9lott.getString("CUP"))) {
				  bando.setCup(w9lott.getString("CUP"));
			  }
			  bando.setCodIstatLocEsec(w9lott.getString("LUOGO_ISTAT"));
			  
			  if (w9gara.getDouble("IMPORTO_GARA") != null) {
				  bando.setImpBase(new BigDecimal(convertiImporto(w9gara.getDouble("IMPORTO_GARA"))));
			  } else {
				  bando.setImpBase(new BigDecimal(0));
			  }
			  
			  if (w9lott.getLong("ID_SCELTA_CONTRAENTE") != null) {
				  bando.setProcedGaraId(codificaSitatToSitar("W3005", w9lott.getLong("ID_SCELTA_CONTRAENTE").toString()));
			  }
			  
			  bando.setAstaElettronica((w9appa != null && w9appa.getString("ASTA_ELETTRONICA")!=null && w9appa.getString("ASTA_ELETTRONICA").equals("1"))?"Y":"N");
			  bando.setForcella("N"); 
			  bando.setRiservato("N");
			  
			  if (w9pubb.getData("DATA_GUCE") != null) {
				  date.setTime(w9pubb.getData("DATA_GUCE"));
				  bando.setPubGuCee(date);
			  } 
			  
			  if (w9pubb.getData("DATA_GURI") != null) {
				  date.setTime(w9pubb.getData("DATA_GURI"));
				  bando.setPubGuRi(date);
			  } 
			  
			  if (w9pubb.getData("DATA_ALBO") != null) {
				  date.setTime(w9pubb.getData("DATA_ALBO"));
				  bando.setPubAlboPret(date);
			  } 
			  
			  if(verificaEsistenzaValore(w9pubb.getString("PROFILO_COMMITTENTE"))) {
				  bando.setProfiloCommittente((w9pubb.getString("PROFILO_COMMITTENTE").equals("1"))?"Y":"N");
			  }
			  
			  if(verificaEsistenzaValore(w9pubb.getLong("QUOTIDIANI_NAZ"))) {
				  bando.setPubQuotNaz(w9pubb.getLong("QUOTIDIANI_NAZ").intValue());
			  }
			  
			  if(verificaEsistenzaValore(w9pubb.getLong("QUOTIDIANI_REG"))) {
				  bando.setPubQuotLoc(w9pubb.getLong("QUOTIDIANI_REG").intValue());
			  }
			  
			  if (w9appa != null) {
				  bando.setProcAcc((w9appa.getString("PROCEDURA_ACC")!=null && w9appa.getString("PROCEDURA_ACC").equals("1"))?"Y":"N");
				  bando.setPreinfo((w9appa.getString("PREINFORMAZIONE")!=null && w9appa.getString("PREINFORMAZIONE").equals("1"))?"Y":"N");
				  bando.setTermRidPreinfo((w9appa.getString("TERMINE_RIDOTTO")!=null && w9appa.getString("TERMINE_RIDOTTO").equals("1"))?"Y":"N");
			  } else {
				  bando.setProcAcc("N");
				  bando.setPreinfo("N");
				  bando.setTermRidPreinfo("N");
			  }
			  
			  if (w9appa != null && w9appa.getData("DATA_SCADENZA_PRES_OFFERTA") != null) {
				  date.setTime(w9appa.getData("DATA_SCADENZA_PRES_OFFERTA"));
				  bando.setDataScadenza(date);
			  } else if (w9gara.getData("DSCADE") != null) {
				  date.setTime(w9gara.getData("DSCADE"));
				  bando.setDataScadenza(date);
			  }

			  HashMap<String, Object> hashMapFileAllegato = new HashMap<String, Object>();
		      hashMapFileAllegato.put("codGara", codGara);
		      hashMapFileAllegato.put("numdoc", 1);
		      hashMapFileAllegato.put("num_pubb", 1);
		      BlobFile w4Blob = this.w9FileDao.getFileAllegato("GARA", hashMapFileAllegato);
		      
		      if (w4Blob != null) {
		    	  bando.setAllegatoFlag("Y");
				  bando.setAllegatoValue(w4Blob.getStream());
				  bando.setAllegatoName("Bando di gara");
				  bando.setAllegatoContentType("application/pdf");
		      } else {
		    	  bando.setAllegatoFlag("N");
				  bando.setAllegatoValue(new byte[0]);
				  bando.setAllegatoName("-");
				  bando.setAllegatoContentType("application/pdf");
		      }

		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaBandoServForn", "getSchedaBandoServForn", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaBandoServForn: fine metodo");
		  }
		  
		  return doc;
	  }
	/**
	   * Fase aggiudicazione lavori Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numAppa Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaAggiudicazioneLavori(final long codGara,
		      final long codLott, final long numAppa) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAggiudicazioneLavori: inizio metodo");
		  }
		  
		  DataColumnContainer w9gara, w9lott, w9appa, w9pubb;
		  SchedaAggLavoriSezOssDocument doc = SchedaAggLavoriSezOssDocument.Factory.newInstance();
		  
		  try { 
			  SchedaAggLavoriSezOss schedaAGOss = doc.addNewSchedaAggLavoriSezOss();
			  SchedaAggLavoriSezOss.Settings settings = schedaAGOss.addNewSettings();
			  w9gara = new DataColumnContainer(sqlManager, "W9GARA", SQL_DATI_GARA, new Object[] {codGara});
			  w9lott = new DataColumnContainer(sqlManager, "W9LOTT", SQL_DATI_LOTTO, new Object[] {codGara, codLott});
			  w9appa = new DataColumnContainer(sqlManager, "W9APPA", SQL_DATI_APPALTO, new Object[] {codGara, codLott, numAppa});
			  try {
				  w9pubb = new DataColumnContainer(sqlManager, "W9PUBB", SQL_DATI_PUBB_BANDO, new Object[] {codGara});
			  } catch (Exception ex) {
				  w9pubb = null;
			  }

			  settings.setTiposcheda("AggiudicazioneLavoriSezioneOsservatorio");
			  
			  SchedaAggLavoriSezOss.AggiudicazioneLavoriSezioneOsservatorio aggiudicazione = schedaAGOss.addNewAggiudicazioneLavoriSezioneOsservatorio();

			  Calendar date = Calendar.getInstance();

			  aggiudicazione.setCodiceNuts((w9lott.getString("LUOGO_NUTS")!= null)?w9lott.getString("LUOGO_NUTS"):"");
			  aggiudicazione.setNLotto((int)codLott);
			  aggiudicazione.setNumTotLotti((w9gara.getLong("NLOTTI") != null)?w9gara.getLong("NLOTTI").intValue():0);
			  aggiudicazione.setAccordoQuadro((w9appa.getString("FLAG_ACCORDO_QUADRO")!=null && w9appa.getString("FLAG_ACCORDO_QUADRO").equals("1"))?"Y":"N");

			  if (w9lott.getLong("ID_TIPO_PRESTAZIONE") != null) {
				  aggiudicazione.setIdPrestazComprese(w9lott.getLong("ID_TIPO_PRESTAZIONE").intValue());
			  }
			  //Codice dello strumento di programmazione  
			  if (w9appa.getString("COD_STRUMENTO") != null) {
				  aggiudicazione.setStrumProgId(codificaSitatToSitar("W3z01", w9appa.getString("COD_STRUMENTO")));
			  } 
			  aggiudicazione.setImpCompLav(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_LAVORI"))));
			  aggiudicazione.setImpCompServ(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_SERVIZI"))));
			  aggiudicazione.setImpCompForn(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_FORNITURE"))));
			  aggiudicazione.setImpTotSic(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_ATTUAZIONE_SICUREZZA"))));
			  aggiudicazione.setImpNonRibAsta(new BigDecimal(convertiImporto(w9appa.getDouble("IMP_NON_ASSOG"))));
			  aggiudicazione.setImpProgett(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_PROGETTAZIONE"))));
			  aggiudicazione.setImpTotSommeDisp(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_DISPOSIZIONE"))));
			  
			  aggiudicazione.setCondizRicArt57C2A("N");
			  aggiudicazione.setCondizRicArt57C2B("N");
			  aggiudicazione.setCondizRicArt57C2C("N");
			  aggiudicazione.setCondizRicArt57C3A("N");
			  aggiudicazione.setCondizRicArt57C3B("N");
			  aggiudicazione.setCondizRicArt57C3C("N");
			  aggiudicazione.setCondizRicArt57C3D("N");
			  aggiudicazione.setCondizRicArt57C4("N");
			  aggiudicazione.setCondizRicArt57C5A1("N");
			  aggiudicazione.setCondizRicArt57C5A2("N");
			  aggiudicazione.setCondizRicArt57C5B("N");
			  aggiudicazione.setCondizRicArt221C1A("N");
			  aggiudicazione.setCondizRicArt221C1B("N");
			  aggiudicazione.setCondizRicArt221C1C("N");
			  aggiudicazione.setCondizRicArt221C1D("N");
			  aggiudicazione.setCondizRicArt221C1E("N");
			  aggiudicazione.setCondizRicArt221C1F("N");
			  aggiudicazione.setCondizRicArt221C1G("N");
			  aggiudicazione.setCondizRicArt221C1H("N");
			  aggiudicazione.setCondizRicArt221C1I("N");
			  aggiudicazione.setCondizRicArt221C1L("N");
			  aggiudicazione.setCondizRicArt122C7Bis("N");
			  aggiudicazione.setCondizRicArt122C8("N");
			  aggiudicazione.setCondizRicArt99C5("N");
			  aggiudicazione.setCondizRicRicorsoMercatoElettronico("N");
			  aggiudicazione.setCondizRicAdesioneAccordoQ("N");
			  aggiudicazione.setCondizRicAltreMotivazioni("N");
			  
			  List< ? > w9cond = sqlManager.getListVector(SQL_DATI_CONDIZIONE, new Object[] {codGara, codLott});
			  if (w9cond != null && w9cond.size() > 0) {
				  for (int i = 0; i < w9cond.size(); i++) {
					  List< ?> cond = (List< ? >)w9cond.get(i);
					  switch (Integer.parseInt(cond.get(0).toString())) {
					  	case 1:
					  		aggiudicazione.setCondizRicArt57C2A("Y");
					  		break;
					  	case 2:
					  		aggiudicazione.setCondizRicArt57C2B("Y");
					  		break;
					  	case 3:
							aggiudicazione.setCondizRicArt57C2C("Y");
					  		break;
					  	case 4:
							aggiudicazione.setCondizRicArt57C3A("Y");
					  		break;
					  	case 5:
							aggiudicazione.setCondizRicArt57C3B("Y");
					  		break;
					  	case 6:
							aggiudicazione.setCondizRicArt57C3C("Y");
					  		break;
					  	case 7:
							aggiudicazione.setCondizRicArt57C3D("Y");
					  		break;
					  	case 8:
							aggiudicazione.setCondizRicArt57C4("Y");
					  		break;
					  	case 9:
							aggiudicazione.setCondizRicArt57C5A1("Y");
					  		break;
					  	case 10:
							aggiudicazione.setCondizRicArt57C5A2("Y");
					  		break;
					  	case 11:
							aggiudicazione.setCondizRicArt57C5B("Y");
					  		break;
					  	case 12:
							aggiudicazione.setCondizRicArt221C1A("Y");
					  		break;
					  	case 13:
							aggiudicazione.setCondizRicArt221C1B("Y");
					  		break;
					  	case 14:
							aggiudicazione.setCondizRicArt221C1C("Y");
					  		break;
					  	case 15:
							aggiudicazione.setCondizRicArt221C1D("Y");
					  		break;
					  	case 16:
							aggiudicazione.setCondizRicArt221C1E("Y");
					  		break;
					  	case 17:
							aggiudicazione.setCondizRicArt221C1F("Y");
					  		break;
					  	case 18:
							aggiudicazione.setCondizRicArt221C1G("Y");
					  		break;
					  	case 19:
							aggiudicazione.setCondizRicArt221C1H("Y");
					  		break;
					  	case 20:
							aggiudicazione.setCondizRicArt221C1I("Y");
					  		break;
					  	case 21:
							aggiudicazione.setCondizRicArt221C1L("Y");
					  		break;
					  	case 31:
							aggiudicazione.setCondizRicArt122C7Bis("Y");
					  		break;
					  	case 27:
							aggiudicazione.setCondizRicArt122C8("Y");
					  		break;
					  	case 28:
							aggiudicazione.setCondizRicArt99C5("Y");
					  		break;
					  	case 29:
							aggiudicazione.setCondizRicRicorsoMercatoElettronico("Y");
					  		break;
					  	case 30:
							aggiudicazione.setCondizRicAdesioneAccordoQ("Y");
					  		break;
					  	default:
							aggiudicazione.setCondizRicAltreMotivazioni("Y");
					  		break;
					}
			     }
			  }
			  
			  if (w9appa.getLong("ID_MODO_INDIZIONE") != null) {
				  aggiudicazione.setModIndizioneGaraId(w9appa.getLong("ID_MODO_INDIZIONE").intValue());
			  } 
			  
			  String categoriaPrevalente = null;
			  String impCategoriaPrevalente = null;
			  if (w9lott.getString("ID_CATEGORIA_PREVALENTE") != null) {
				  categoriaPrevalente = codificaSitatToSitar("W3z03", w9lott.getString("ID_CATEGORIA_PREVALENTE"));
			  }
			  if (w9lott.getString("CLASCAT") != null) {
				  impCategoriaPrevalente = codificaSitatToSitar("W3z11", w9lott.getString("CLASCAT"));
			  }
			  
			  if (categoriaPrevalente!=null) {
				  aggiudicazione.setCatPrevId(Integer.parseInt(categoriaPrevalente));
			  }
			  if (impCategoriaPrevalente!=null) {
				  aggiudicazione.setImpCatPrevId(Integer.parseInt(impCategoriaPrevalente));
			  }
			  aggiudicazione.setTipologiaCatPrevId(1);
			  
			  List< ? > w9cate = sqlManager.getListVector(SQL_DATI_CATEGORIE, new Object[] {codGara, codLott});
			  if (w9cate != null && w9cate.size() > 0) {
				  for (int i = 0; i < w9cate.size(); i++) {
					  List< ?> cate = (List< ? >)w9cate.get(i);
					  categoriaPrevalente = null;
					  impCategoriaPrevalente = null;
					  if (cate.get(0) != null) {
						  categoriaPrevalente = codificaSitatToSitar("W3z03", cate.get(0).toString());
					  }
					  if (cate.get(1) != null) {
						  impCategoriaPrevalente = codificaSitatToSitar("W3z11", cate.get(1).toString());
					  }
					  switch (i) {
					  	case 0:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp1Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp1Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 1:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp2Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp2Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 2:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp3Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp3Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 3:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp4Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp4Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 4:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp5Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp5Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 5:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp6Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp6Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 6:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp7Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp7Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  }
				  }
			  }
 
			  //NON USATI
			  aggiudicazione.setRequisitiCritSelezStazapp("N");
			  aggiudicazione.setRequisitiSistQualificInterno("N");
			  
			  if (w9pubb != null) {
				  aggiudicazione.setHasProfCommitt((w9pubb.getString("PROFILO_COMMITTENTE")!=null && w9pubb.getString("PROFILO_COMMITTENTE").equals("1"))?"Y":"N");
				  aggiudicazione.setHasSitoMinistero((w9pubb.getString("SITO_MINISTERO_INF_TRASP")!=null && w9pubb.getString("SITO_MINISTERO_INF_TRASP").equals("1"))?"Y":"N");
				  aggiudicazione.setHasSitoOsservat((w9pubb.getString("SITO_OSSERVATORIO_CP")!=null && w9pubb.getString("SITO_OSSERVATORIO_CP").equals("1"))?"Y":"N");
			  }
			  
			  if (w9appa.getData("DATA_MANIF_INTERESSE") != null) {
				  date.setTime(w9appa.getData("DATA_MANIF_INTERESSE"));
				  aggiudicazione.setDataScadPreInteresse(date);
			  } 
			  
			  if (w9appa.getData("DATA_SCADENZA_RICHIESTA_INVITO") != null) {
				  date.setTime(w9appa.getData("DATA_SCADENZA_RICHIESTA_INVITO"));
				  aggiudicazione.setDataScadPresRichInvito(date);
			  }
			  
			  if (w9appa.getData("DATA_INVITO") != null) {
				  date.setTime(w9appa.getData("DATA_INVITO"));
				  aggiudicazione.setDataInvito(date);
			  }
			  
			  if (w9appa.getLong("NUM_MANIF_INTERESSE") != null) {
				  aggiudicazione.setNSoggPresInteresse(w9appa.getLong("NUM_MANIF_INTERESSE").intValue());
			  }
			  
			  if (w9appa.getDouble("OFFERTA_MASSIMO") != null) {
				  aggiudicazione.setMaxRibasso(new BigDecimal(w9appa.getDouble("OFFERTA_MASSIMO")));
			  } else {
				  aggiudicazione.setNilMaxRibasso();
			  }
			  
			  if (w9appa.getDouble("OFFERTA_MINIMA") != null) {
				  aggiudicazione.setMinRibasso(new BigDecimal(w9appa.getDouble("OFFERTA_MINIMA")));
			  } else {
				  aggiudicazione.setNilMinRibasso();
			  }
			  
			  if (w9appa.getDouble("VAL_SOGLIA_ANOMALIA") != null) {
				  aggiudicazione.setSogliaAnomalia(new BigDecimal(w9appa.getDouble("OFFERTA_MINIMA")));
			  }
			  
			  if (w9appa.getLong("NUM_OFFERTE_FUORI_SOGLIA") != null) {
				  aggiudicazione.setNSupSogliaAnom(w9appa.getLong("NUM_OFFERTE_FUORI_SOGLIA").intValue());
			  }
			  
			  if (w9appa.getLong("NUM_OFFERTE_ESCLUSE") != null) {
				  aggiudicazione.setNEsclAutomatico(w9appa.getLong("NUM_OFFERTE_ESCLUSE").intValue());
			  }
			  
			  if (w9appa.getLong("NUM_IMP_ESCL_INSUF_GIUST") != null) {
				  aggiudicazione.setNEsclInsGiustific(w9appa.getLong("NUM_IMP_ESCL_INSUF_GIUST").intValue());
			  }
			  
			  if (w9appa.getDouble("PERC_OFF_AUMENTO") != null) {
				  aggiudicazione.setOffertaAumento(new BigDecimal(w9appa.getDouble("PERC_OFF_AUMENTO")));
			  }
			  
			  aggiudicazione.setChiestiSubapp((w9appa.getString("FLAG_RICH_SUBAPPALTO")!=null && w9appa.getString("FLAG_RICH_SUBAPPALTO").equals("1"))?"Y":"N");
			  
			  //Soggetti coinvolti
			  AggiudicazioneLavoriSezioneOsservatorio.IncarichiProgettazione soggettiCoinvolti = aggiudicazione.addNewIncarichiProgettazione();
			  //1,2,3,4
			  List< ? > incarichi = sqlManager.getListHashMap(SQL_DATI_INCARICHI_AGGIUDICAZIONE, new Object[] {codGara, codLott, numAppa , "1", "2", "3", "4"});
			  if (incarichi != null && incarichi.size() > 0) {
				  for (int i = 0; i < incarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_INCARICHI_AGGIUDICAZIONE, new Object[] { codGara, codLott, numAppa , "1", "2", "3", "4"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) incarichi.get(i), true);
						PeFiProgett personaFisica = soggettiCoinvolti.addNewPersonaFisica();
						personaFisica.setNum(i);
						if (verificaEsistenzaValore(w9inca.getString("CIG_PROG_ESTERNA"))) {
							personaFisica.setCigAffidIncEst(w9inca.getString("CIG_PROG_ESTERNA"));
						}
						if (w9inca.getData("DATA_AFF_PROG_ESTERNA") != null) {
							date.setTime(w9inca.getData("DATA_AFF_PROG_ESTERNA"));
							personaFisica.setDataAffidInc(date);
						}
						if (w9inca.getData("DATA_CONS_PROG_ESTERNA") != null) {
							date.setTime(w9inca.getData("DATA_CONS_PROG_ESTERNA"));
							personaFisica.setDataConsProg(date);
						}
						personaFisica.setProfilo(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	

			  //Finanziamenti
			  AggiudicazioneLavoriSezioneOsservatorio.Finanziamenti finanziamenti = aggiudicazione.addNewFinanziamenti();
			  List< ? > listaFinanziamenti = sqlManager.getListHashMap(SQL_DATI_FINANZIAMENTI, new Object[] {codGara, codLott, numAppa});
			  if (listaFinanziamenti != null && listaFinanziamenti.size() > 0) {
				  for (int i = 0; i < listaFinanziamenti.size(); i++) {
						DataColumnContainer w9fina = new DataColumnContainer(sqlManager, "W9FINA", SQL_DATI_FINANZIAMENTI, new Object[] { codGara, codLott, numAppa});
						w9fina.setValoriFromMap((HashMap< ? , ? >) listaFinanziamenti.get(i), true);
						AggiudicazioneLavoriSezioneOsservatorio.Finanziamenti.Finanziamento finanziamento = finanziamenti.addNewFinanziamento();
						finanziamento.setTipoFinId(Integer.parseInt(codificaSitatToSitar("W3z02",w9fina.getString("ID_FINANZIAMENTO"))));
						finanziamento.setImpFin(new BigDecimal(w9fina.getDouble("IMPORTO_FINANZIAMENTO")));
						finanziamento.setNum(i);
				  }	
			  }	
			  //Altri Soggetti coinvolti
			  //6,7,8,16,17
			  AggiudicazioneLavoriSezioneOsservatorio.AltriIncarichi altriSoggettiCoinvolti = aggiudicazione.addNewAltriIncarichi();
			  List< ? > altriIncarichi = sqlManager.getListHashMap(SQL_DATI_ALTRI_INCARICHI, new Object[] {codGara, codLott, numAppa, "6", "7", "8", "16", "17"});
			  if (altriIncarichi != null && altriIncarichi.size() > 0) {
				  for (int i = 0; i < altriIncarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_ALTRI_INCARICHI, new Object[] { codGara, codLott, numAppa, "6", "7", "8", "16", "17"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) altriIncarichi.get(i), true);
						PeFiAltriIncarichi personaFisica = altriSoggettiCoinvolti.addNewPersonaFisica();
						personaFisica.setProfiloAltriIncarichi(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						personaFisica.setNum(i);
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	

		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaAggiudicazioneLavori", "getSchedaAggiudicazioneLavori", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAggiudicazioneLavori: fine metodo");
		  }
		  
		  return doc;
	  }
	  
	  public XmlObject getSchedaAggiudicazioneServForn(final long codGara,
		      final long codLott, final long numAppa) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAggiudicazioneFornServ: inizio metodo");
		  }
		  
		  DataColumnContainer w9gara, w9lott, w9appa, w9appaforn, w9pubb;
		  SchedaAggServFornSezOssDocument doc = SchedaAggServFornSezOssDocument.Factory.newInstance();
		  
		  try { 
			  SchedaAggServFornSezOss schedaAGOss = doc.addNewSchedaAggServFornSezOss();
			  SchedaAggServFornSezOss.Settings settings = schedaAGOss.addNewSettings();
			  w9gara = new DataColumnContainer(sqlManager, "W9GARA", SQL_DATI_GARA, new Object[] {codGara});
			  w9lott = new DataColumnContainer(sqlManager, "W9LOTT", SQL_DATI_LOTTO, new Object[] {codGara, codLott});
			  w9appa = new DataColumnContainer(sqlManager, "W9APPA", SQL_DATI_APPALTO, new Object[] {codGara, codLott, numAppa});
			  try {
				  w9pubb = new DataColumnContainer(sqlManager, "W9PUBB", SQL_DATI_PUBB_BANDO, new Object[] {codGara});
			  } catch (Exception ex) {
				  w9pubb = null;
			  }

			  try {
				  w9appaforn = new DataColumnContainer(sqlManager, "W9APPAFORN", SQL_DATI_APPAFORN, new Object[] {codGara, codLott, numAppa});
			  } catch (Exception ex) {
				  w9appaforn = null;
			  }
			  settings.setTiposcheda("AggiudicazioneServFornSezioneOsservatorio");

			  
			  SchedaAggServFornSezOss.AggiudicazioneServFornSezioneOsservatorio aggiudicazione = schedaAGOss.addNewAggiudicazioneServFornSezioneOsservatorio();

			  Calendar date = Calendar.getInstance();

			  aggiudicazione.setCodiceNuts((w9lott.getString("LUOGO_NUTS")!= null)?w9lott.getString("LUOGO_NUTS"):"");
			  aggiudicazione.setNLotto((int)codLott);
			  aggiudicazione.setNumTotLotti((w9gara.getLong("NLOTTI") != null)?w9gara.getLong("NLOTTI").intValue():0);
			  aggiudicazione.setAccordoQuadro((w9appa.getString("FLAG_ACCORDO_QUADRO")!=null && w9appa.getString("FLAG_ACCORDO_QUADRO").equals("1"))?"Y":"N");

			  if (w9appaforn != null && w9appaforn.getLong("ID_APPALTO") != null) {
				  aggiudicazione.setIdModAcqFornserv(w9appaforn.getLong("ID_APPALTO").intValue());
			  }


			  if (w9lott.getLong("ID_TIPO_PRESTAZIONE") != null) {
				  aggiudicazione.setIdPrestazComprese(w9lott.getLong("ID_TIPO_PRESTAZIONE").intValue());
			  }
			  //Codice dello strumento di programmazione  
			  if (w9appa.getString("COD_STRUMENTO") != null) {
				  aggiudicazione.setStrumProgId(codificaSitatToSitar("W3z01", w9appa.getString("COD_STRUMENTO")));
			  } else {
				  aggiudicazione.setStrumProgId("0");
			  }
			  aggiudicazione.setImpCompLav(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_LAVORI"))));
			  aggiudicazione.setImpCompServ(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_SERVIZI"))));
			  aggiudicazione.setImpCompForn(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_FORNITURE"))));
			  aggiudicazione.setImpTotSic(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_ATTUAZIONE_SICUREZZA"))));
			  aggiudicazione.setImpNonRibAsta(new BigDecimal(convertiImporto(w9appa.getDouble("IMP_NON_ASSOG"))));
			  aggiudicazione.setImpProgett(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_PROGETTAZIONE"))));
			  aggiudicazione.setImpTotSommeDisp(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_DISPOSIZIONE"))));
			  
			  aggiudicazione.setCondizRicArt57C2A("N");
			  aggiudicazione.setCondizRicArt57C2B("N");
			  aggiudicazione.setCondizRicArt57C2C("N");
			  aggiudicazione.setCondizRicArt57C3A("N");
			  aggiudicazione.setCondizRicArt57C3B("N");
			  aggiudicazione.setCondizRicArt57C3C("N");
			  aggiudicazione.setCondizRicArt57C3D("N");
			  aggiudicazione.setCondizRicArt57C4("N");
			  aggiudicazione.setCondizRicArt57C5A1("N");
			  aggiudicazione.setCondizRicArt57C5A2("N");
			  aggiudicazione.setCondizRicArt57C5B("N");
			  aggiudicazione.setCondizRicArt221C1A("N");
			  aggiudicazione.setCondizRicArt221C1B("N");
			  aggiudicazione.setCondizRicArt221C1C("N");
			  aggiudicazione.setCondizRicArt221C1D("N");
			  aggiudicazione.setCondizRicArt221C1E("N");
			  aggiudicazione.setCondizRicArt221C1F("N");
			  aggiudicazione.setCondizRicArt221C1G("N");
			  aggiudicazione.setCondizRicArt221C1H("N");
			  aggiudicazione.setCondizRicArt221C1I("N");
			  aggiudicazione.setCondizRicArt221C1L("N");
			  aggiudicazione.setCondizRicArt122C7Bis("N");
			  aggiudicazione.setCondizRicArt122C8("N");
			  aggiudicazione.setCondizRicArt99C5("N");
			  aggiudicazione.setCondizRicRicorsoMercatoElettronico("N");
			  aggiudicazione.setCondizRicAdesioneAccordoQ("N");
			  aggiudicazione.setCondizRicAltreMotivazioni("N");
			  
			  List< ? > w9cond = sqlManager.getListVector(SQL_DATI_CONDIZIONE, new Object[] {codGara, codLott});
			  if (w9cond != null && w9cond.size() > 0) {
				  for (int i = 0; i < w9cond.size(); i++) {
					  List< ?> cond = (List< ? >)w9cond.get(i);
					  switch (Integer.parseInt(cond.get(0).toString())) {
					  	case 1:
					  		aggiudicazione.setCondizRicArt57C2A("Y");
					  		break;
					  	case 2:
					  		aggiudicazione.setCondizRicArt57C2B("Y");
					  		break;
					  	case 3:
							aggiudicazione.setCondizRicArt57C2C("Y");
					  		break;
					  	case 4:
							aggiudicazione.setCondizRicArt57C3A("Y");
					  		break;
					  	case 5:
							aggiudicazione.setCondizRicArt57C3B("Y");
					  		break;
					  	case 6:
							aggiudicazione.setCondizRicArt57C3C("Y");
					  		break;
					  	case 7:
							aggiudicazione.setCondizRicArt57C3D("Y");
					  		break;
					  	case 8:
							aggiudicazione.setCondizRicArt57C4("Y");
					  		break;
					  	case 9:
							aggiudicazione.setCondizRicArt57C5A1("Y");
					  		break;
					  	case 10:
							aggiudicazione.setCondizRicArt57C5A2("Y");
					  		break;
					  	case 11:
							aggiudicazione.setCondizRicArt57C5B("Y");
					  		break;
					  	case 12:
							aggiudicazione.setCondizRicArt221C1A("Y");
					  		break;
					  	case 13:
							aggiudicazione.setCondizRicArt221C1B("Y");
					  		break;
					  	case 14:
							aggiudicazione.setCondizRicArt221C1C("Y");
					  		break;
					  	case 15:
							aggiudicazione.setCondizRicArt221C1D("Y");
					  		break;
					  	case 16:
							aggiudicazione.setCondizRicArt221C1E("Y");
					  		break;
					  	case 17:
							aggiudicazione.setCondizRicArt221C1F("Y");
					  		break;
					  	case 18:
							aggiudicazione.setCondizRicArt221C1G("Y");
					  		break;
					  	case 19:
							aggiudicazione.setCondizRicArt221C1H("Y");
					  		break;
					  	case 20:
							aggiudicazione.setCondizRicArt221C1I("Y");
					  		break;
					  	case 21:
							aggiudicazione.setCondizRicArt221C1L("Y");
					  		break;
					  	case 31:
							aggiudicazione.setCondizRicArt122C7Bis("Y");
					  		break;
					  	case 27:
							aggiudicazione.setCondizRicArt122C8("Y");
					  		break;
					  	case 28:
							aggiudicazione.setCondizRicArt99C5("Y");
					  		break;
					  	case 29:
							aggiudicazione.setCondizRicRicorsoMercatoElettronico("Y");
					  		break;
					  	case 30:
							aggiudicazione.setCondizRicAdesioneAccordoQ("Y");
					  		break;
					  	default:
							aggiudicazione.setCondizRicAltreMotivazioni("Y");
					  		break;
					}
			     }
			  }
			  
			  if (w9appa.getLong("ID_MODO_INDIZIONE") != null) {
				  aggiudicazione.setModIndizioneGaraId(w9appa.getLong("ID_MODO_INDIZIONE").intValue());
			  } 
			  
			  String categoriaPrevalente = null;
			  String impCategoriaPrevalente = null;
			  if (w9lott.getString("ID_CATEGORIA_PREVALENTE") != null) {
				  categoriaPrevalente = codificaSitatToSitar("W3z03", w9lott.getString("ID_CATEGORIA_PREVALENTE"));
			  }
			  if (w9lott.getString("CLASCAT") != null) {
				  impCategoriaPrevalente = codificaSitatToSitar("W3z11", w9lott.getString("CLASCAT"));
			  } 
			  
			  if (categoriaPrevalente!=null) {
				  aggiudicazione.setCatPrevId(Integer.parseInt(categoriaPrevalente));
			  }
			  if (impCategoriaPrevalente!=null) {
				  aggiudicazione.setImpCatPrevId(Integer.parseInt(impCategoriaPrevalente));
			  } else {
				  aggiudicazione.setImpCatPrevId(9);
			  }
			  aggiudicazione.setTipologiaCatPrevId(1);
			  
			  List< ? > w9cate = sqlManager.getListVector(SQL_DATI_CATEGORIE, new Object[] {codGara, codLott});
			  if (w9cate != null && w9cate.size() > 0) {
				  for (int i = 0; i < w9cate.size(); i++) {
					  List< ?> cate = (List< ? >)w9cate.get(i);
					  categoriaPrevalente = null;
					  impCategoriaPrevalente = null;
					  if (cate.get(0) != null) {
						  categoriaPrevalente = codificaSitatToSitar("W3z03", cate.get(0).toString());
					  }
					  if (cate.get(1) != null) {
						  impCategoriaPrevalente = codificaSitatToSitar("W3z11", cate.get(1).toString());
					  }
					  switch (i) {
					  	case 0:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp1Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp1Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 1:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp2Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp2Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 2:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp3Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp3Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 3:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp4Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp4Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 4:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp5Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp5Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 5:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp6Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp6Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  	case 6:
					  		if (categoriaPrevalente != null) {
					  			aggiudicazione.setCatScorp7Id(Integer.parseInt(categoriaPrevalente));
					  		}
					  		if (impCategoriaPrevalente != null) {
					  			aggiudicazione.setImpCatScorp7Id(Integer.parseInt(impCategoriaPrevalente));
					  		}
					  		break;
					  }
				  }
			  }
 
			  //NON USATI
			  aggiudicazione.setRequisitiCritSelezStazapp("N");
			  aggiudicazione.setRequisitiSistQualificInterno("N");
			  
			  if (w9pubb != null) {
				  aggiudicazione.setHasProfCommitt((w9pubb.getString("PROFILO_COMMITTENTE")!=null && w9pubb.getString("PROFILO_COMMITTENTE").equals("1"))?"Y":"N");
				  aggiudicazione.setHasSitoMinistero((w9pubb.getString("SITO_MINISTERO_INF_TRASP")!=null && w9pubb.getString("SITO_MINISTERO_INF_TRASP").equals("1"))?"Y":"N");
				  aggiudicazione.setHasSitoOsservat((w9pubb.getString("SITO_OSSERVATORIO_CP")!=null && w9pubb.getString("SITO_OSSERVATORIO_CP").equals("1"))?"Y":"N");
			  }
			  
			  if (w9appa.getData("DATA_MANIF_INTERESSE") != null) {
				  date.setTime(w9appa.getData("DATA_MANIF_INTERESSE"));
				  aggiudicazione.setDataScadPreInteresse(date);
			  } 
			  
			  if (w9appa.getData("DATA_SCADENZA_RICHIESTA_INVITO") != null) {
				  date.setTime(w9appa.getData("DATA_SCADENZA_RICHIESTA_INVITO"));
				  aggiudicazione.setDataScadPresRichInvito(date);
			  }
			  
			  if (w9appa.getData("DATA_INVITO") != null) {
				  date.setTime(w9appa.getData("DATA_INVITO"));
				  aggiudicazione.setDataInvito(date);
			  }
			  
			  if (w9appa.getLong("NUM_MANIF_INTERESSE") != null) {
				  aggiudicazione.setNSoggPresInteresse(w9appa.getLong("NUM_MANIF_INTERESSE").intValue());
			  }
			  
			  if (w9appa.getDouble("OFFERTA_MASSIMO") != null) {
				  aggiudicazione.setMaxRibasso(new BigDecimal(w9appa.getDouble("OFFERTA_MASSIMO")));
			  } else {
				  aggiudicazione.setNilMaxRibasso();
			  }
			  
			  if (w9appa.getDouble("OFFERTA_MINIMA") != null) {
				  aggiudicazione.setMinRibasso(new BigDecimal(w9appa.getDouble("OFFERTA_MINIMA")));
			  } else {
				  aggiudicazione.setNilMinRibasso();
			  }
			  
			  if (w9appa.getDouble("VAL_SOGLIA_ANOMALIA") != null) {
				  aggiudicazione.setSogliaAnomalia(new BigDecimal(w9appa.getDouble("OFFERTA_MINIMA")));
			  }
			  
			  if (w9appa.getLong("NUM_OFFERTE_FUORI_SOGLIA") != null) {
				  aggiudicazione.setNSupSogliaAnom(w9appa.getLong("NUM_OFFERTE_FUORI_SOGLIA").intValue());
			  }
			  
			  if (w9appa.getLong("NUM_OFFERTE_ESCLUSE") != null) {
				  aggiudicazione.setNEsclAutomatico(w9appa.getLong("NUM_OFFERTE_ESCLUSE").intValue());
			  }
			  
			  if (w9appa.getLong("NUM_IMP_ESCL_INSUF_GIUST") != null) {
				  aggiudicazione.setNEsclInsGiustific(w9appa.getLong("NUM_IMP_ESCL_INSUF_GIUST").intValue());
			  }
			  
			  if (w9appa.getDouble("PERC_OFF_AUMENTO") != null) {
				  aggiudicazione.setOffertaAumento(new BigDecimal(w9appa.getDouble("PERC_OFF_AUMENTO")));
			  }
			  
			  aggiudicazione.setChiestiSubapp((w9appa.getString("FLAG_RICH_SUBAPPALTO")!=null && w9appa.getString("FLAG_RICH_SUBAPPALTO").equals("1"))?"Y":"N");
			  
			  //Soggetti coinvolti
			  AggiudicazioneServFornSezioneOsservatorio.IncarichiProgettazione soggettiCoinvolti = aggiudicazione.addNewIncarichiProgettazione();
			  //1,2,3,4
			  List< ? > incarichi = sqlManager.getListHashMap(SQL_DATI_INCARICHI_AGGIUDICAZIONE, new Object[] {codGara, codLott, numAppa , "1", "2", "3", "4"});
			  if (incarichi != null && incarichi.size() > 0) {
				  for (int i = 0; i < incarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_INCARICHI_AGGIUDICAZIONE, new Object[] { codGara, codLott, numAppa , "1", "2", "3", "4"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) incarichi.get(i), true);
						PeFiProgett personaFisica = soggettiCoinvolti.addNewPersonaFisica();
						personaFisica.setNum(i);
						if (verificaEsistenzaValore(w9inca.getString("CIG_PROG_ESTERNA"))) {
							personaFisica.setCigAffidIncEst(w9inca.getString("CIG_PROG_ESTERNA"));
						}
						if (w9inca.getData("DATA_AFF_PROG_ESTERNA") != null) {
							date.setTime(w9inca.getData("DATA_AFF_PROG_ESTERNA"));
							personaFisica.setDataAffidInc(date);
						}
						if (w9inca.getData("DATA_CONS_PROG_ESTERNA") != null) {
							date.setTime(w9inca.getData("DATA_CONS_PROG_ESTERNA"));
							personaFisica.setDataConsProg(date);
						}
						personaFisica.setProfilo(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	

			  //Finanziamenti
			  AggiudicazioneServFornSezioneOsservatorio.Finanziamenti finanziamenti = aggiudicazione.addNewFinanziamenti();
			  List< ? > listaFinanziamenti = sqlManager.getListHashMap(SQL_DATI_FINANZIAMENTI, new Object[] {codGara, codLott, numAppa});
			  if (listaFinanziamenti != null && listaFinanziamenti.size() > 0) {
				  for (int i = 0; i < listaFinanziamenti.size(); i++) {
						DataColumnContainer w9fina = new DataColumnContainer(sqlManager, "W9FINA", SQL_DATI_FINANZIAMENTI, new Object[] { codGara, codLott, numAppa});
						w9fina.setValoriFromMap((HashMap< ? , ? >) listaFinanziamenti.get(i), true);
						AggiudicazioneServFornSezioneOsservatorio.Finanziamenti.Finanziamento finanziamento = finanziamenti.addNewFinanziamento();
						finanziamento.setTipoFinId(Integer.parseInt(codificaSitatToSitar("W3z02",w9fina.getString("ID_FINANZIAMENTO"))));
						finanziamento.setImpFin(new BigDecimal(w9fina.getDouble("IMPORTO_FINANZIAMENTO")));
						finanziamento.setNum(i);
				  }	
			  }	
			  //Altri Soggetti coinvolti
			  //6,7,8,16,17
			  AggiudicazioneServFornSezioneOsservatorio.AltriIncarichi altriSoggettiCoinvolti = aggiudicazione.addNewAltriIncarichi();
			  List< ? > altriIncarichi = sqlManager.getListHashMap(SQL_DATI_ALTRI_INCARICHI, new Object[] {codGara, codLott, numAppa, "6", "7", "8", "16", "17"});
			  if (altriIncarichi != null && altriIncarichi.size() > 0) {
				  for (int i = 0; i < altriIncarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_ALTRI_INCARICHI, new Object[] { codGara, codLott, numAppa, "6", "7", "8", "16", "17"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) altriIncarichi.get(i), true);
						PeFiAltriIncarichi personaFisica = altriSoggettiCoinvolti.addNewPersonaFisica();
						personaFisica.setProfiloAltriIncarichi(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						personaFisica.setNum(i);
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	

		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaAggiudicazioneFornServ", "getSchedaAggiudicazioneFornServ", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAggiudicazioneFornServ: fine metodo");
		  }
		  
		  return doc;
	  }
	  /**
	   * Fase aggiudicazione lavori Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numAppa Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaAggiudicazioneSemplificata(final long codGara,
		      final long codLott, final long numAppa) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAggiudicazioneSemplificata: inizio metodo");
		  } 
		  
		  DataColumnContainer w9gara, w9lott, w9appa;
		  SchedaAggiudicazioneSezOssRidDocument doc = SchedaAggiudicazioneSezOssRidDocument.Factory.newInstance();
		  
		  try { 
			  SchedaAggiudicazioneSezOssRid schedaAGOss = doc.addNewSchedaAggiudicazioneSezOssRid();
			  SchedaAggiudicazioneSezOssRid.Settings settings = schedaAGOss.addNewSettings();
			  w9gara = new DataColumnContainer(sqlManager, "W9GARA", SQL_DATI_GARA, new Object[] {codGara});
			  w9lott = new DataColumnContainer(sqlManager, "W9LOTT", SQL_DATI_LOTTO, new Object[] {codGara, codLott});
			  w9appa = new DataColumnContainer(sqlManager, "W9APPA", SQL_DATI_APPALTO, new Object[] {codGara, codLott, numAppa});
			  
			  settings.setTiposcheda("AggiudicazioneSezioneOsservatorioRidotta");
			  
			  SchedaAggiudicazioneSezOssRid.AggiudicazioneSezioneOsservatorioRidotta aggiudicazione = schedaAGOss.addNewAggiudicazioneSezioneOsservatorioRidotta();

			  Calendar date = Calendar.getInstance();

			  aggiudicazione.setCodiceNuts((w9lott.getString("LUOGO_NUTS")!= null)?w9lott.getString("LUOGO_NUTS"):"");
			  aggiudicazione.setNLotto((int)codLott);
			  aggiudicazione.setNumTotLotti((w9gara.getLong("NLOTTI") != null)?w9gara.getLong("NLOTTI").intValue():0);
			  
			  if(w9appa.getDouble("IMPORTO_COMPL_APPALTO") != null) {
				  aggiudicazione.setImpComplessApp(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_COMPL_APPALTO"))));
			  } else {
				  aggiudicazione.setNilImpComplessApp();
			  }
			  
			  if(w9appa.getDouble("IMPORTO_DISPOSIZIONE") != null) {
				  aggiudicazione.setImpTotSommeDisp(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_DISPOSIZIONE"))));
			  } else {
				  aggiudicazione.setNilImpTotSommeDisp();
			  }
			  
			  if (w9appa.getDouble("PERC_OFF_AUMENTO") != null) {
				  aggiudicazione.setOffertaAumento(new BigDecimal(w9appa.getDouble("PERC_OFF_AUMENTO")));
			  }
			  
			  if (w9appa.getData("DATA_STIPULA") != null) {
				  date.setTime(w9appa.getData("DATA_STIPULA"));
				  aggiudicazione.setDataStipulaContratto(date);
				  if (w9appa.getLong("DURATA_CON") != null) {
					  date.add(Calendar.DAY_OF_YEAR, w9appa.getLong("DURATA_CON").intValue());
					  aggiudicazione.setTermContrattUltimazioneLavServForn(date);
				  }
			  }

		  } catch (Exception e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaAggiudicazioneSemplificata", "getSchedaAggiudicazioneSemplificata", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAggiudicazioneSemplificata: fine metodo");
		  } 
		  
		  return doc;
	  }
	  /**
	   * Fase aggiudicazione lavori Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numAppa Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaAdesioneLav(final long codGara,
		      final long codLott, final long numAppa) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAdesioneLav: inizio metodo");
		  } 
		  
		  DataColumnContainer w9lott, w9appa;
		  SchedaAggLavoriSezOssAdesioneDocument doc = SchedaAggLavoriSezOssAdesioneDocument.Factory.newInstance();
		  
		  try { 
			  SchedaAggLavoriSezOssAdesione schedaAdesione = doc.addNewSchedaAggLavoriSezOssAdesione();
			  SchedaAggLavoriSezOssAdesione.Settings settings = schedaAdesione.addNewSettings();
			  w9lott = new DataColumnContainer(sqlManager, "W9LOTT", SQL_DATI_LOTTO, new Object[] {codGara, codLott});
			  w9appa = new DataColumnContainer(sqlManager, "W9APPA", SQL_DATI_APPALTO, new Object[] {codGara, codLott, numAppa});
			  
			  settings.setTiposcheda("AggiudicazioneLavoriSezioneOsservatorio");

			  SchedaAggLavoriSezOssAdesione.AggiudicazioneLavoriSezioneOsservatorio adesione = schedaAdesione.addNewAggiudicazioneLavoriSezioneOsservatorio();

			  Calendar date = Calendar.getInstance();
			  adesione.setCodiceNuts((w9lott.getString("LUOGO_NUTS")!= null)?w9lott.getString("LUOGO_NUTS"):"");
			  //Codice dello strumento di programmazione  
			  if (w9appa.getString("COD_STRUMENTO") != null) {
				  adesione.setStrumProgId(codificaSitatToSitar("W3z01", w9appa.getString("COD_STRUMENTO")));
			  } 
			  adesione.setImpAdesioneCompLav(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_LAVORI"))));
			  adesione.setImpAdesioneCompServ(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_SERVIZI"))));
			  adesione.setImpAdesioneCompForn(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_FORNITURE"))));
			  
			  if (w9appa.getDouble("PERC_OFF_AUMENTO") != null) {
				  adesione.setOffertaAumento(new BigDecimal(w9appa.getDouble("PERC_OFF_AUMENTO")));
			  }
			  adesione.setChiestiSubapp((w9appa.getString("FLAG_RICH_SUBAPPALTO")!=null && w9appa.getString("FLAG_RICH_SUBAPPALTO").equals("1"))?"Y":"N");

			  //Soggetti coinvolti
			  SchedaAggLavoriSezOssAdesione.AggiudicazioneLavoriSezioneOsservatorio.IncarichiProgettazione soggettiCoinvolti = adesione.addNewIncarichiProgettazione();
			  //1,2,3,4
			  List< ? > incarichi = sqlManager.getListHashMap(SQL_DATI_INCARICHI_ADESIONE, new Object[] {codGara, codLott, numAppa , "1", "2", "3", "4"});
			  if (incarichi != null && incarichi.size() > 0) {
				  for (int i = 0; i < incarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_INCARICHI_ADESIONE, new Object[] { codGara, codLott, numAppa , "1", "2", "3", "4"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) incarichi.get(i), true);
						PeFiProgett personaFisica = soggettiCoinvolti.addNewPersonaFisica();
						personaFisica.setNum(i);
						if (verificaEsistenzaValore(w9inca.getString("CIG_PROG_ESTERNA"))) {
							personaFisica.setCigAffidIncEst(w9inca.getString("CIG_PROG_ESTERNA"));
						}
						if (w9inca.getData("DATA_AFF_PROG_ESTERNA") != null) {
							date.setTime(w9inca.getData("DATA_AFF_PROG_ESTERNA"));
							personaFisica.setDataAffidInc(date);
						}
						if (w9inca.getData("DATA_CONS_PROG_ESTERNA") != null) {
							date.setTime(w9inca.getData("DATA_CONS_PROG_ESTERNA"));
							personaFisica.setDataConsProg(date);
						}
						personaFisica.setProfilo(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	

			  //Finanziamenti
			  SchedaAggLavoriSezOssAdesione.AggiudicazioneLavoriSezioneOsservatorio.Finanziamenti finanziamenti = adesione.addNewFinanziamenti();
			  List< ? > listaFinanziamenti = sqlManager.getListHashMap(SQL_DATI_FINANZIAMENTI, new Object[] {codGara, codLott, numAppa});
			  if (listaFinanziamenti != null && listaFinanziamenti.size() > 0) {
				  for (int i = 0; i < listaFinanziamenti.size(); i++) {
						DataColumnContainer w9fina = new DataColumnContainer(sqlManager, "W9FINA", SQL_DATI_FINANZIAMENTI, new Object[] { codGara, codLott, numAppa});
						w9fina.setValoriFromMap((HashMap< ? , ? >) listaFinanziamenti.get(i), true);
						SchedaAggLavoriSezOssAdesione.AggiudicazioneLavoriSezioneOsservatorio.Finanziamenti.Finanziamento finanziamento = finanziamenti.addNewFinanziamento();
						finanziamento.setTipoFinId(Integer.parseInt(codificaSitatToSitar("W3z02",w9fina.getString("ID_FINANZIAMENTO"))));
						finanziamento.setImpFin(new BigDecimal(w9fina.getDouble("IMPORTO_FINANZIAMENTO")));
						finanziamento.setNum(i);
				  }	
			  }	
			  //Altri Soggetti coinvolti
			  //6,7,8,16,17
			  SchedaAggLavoriSezOssAdesione.AggiudicazioneLavoriSezioneOsservatorio.AltriIncarichi altriSoggettiCoinvolti = adesione.addNewAltriIncarichi();
			  List< ? > altriIncarichi = sqlManager.getListHashMap(SQL_DATI_ALTRI_INCARICHI, new Object[] {codGara, codLott, numAppa, "6", "7", "8", "16", "17"});
			  if (altriIncarichi != null && altriIncarichi.size() > 0) {
				  for (int i = 0; i < altriIncarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_ALTRI_INCARICHI, new Object[] { codGara, codLott, numAppa, "6", "7", "8", "16", "17"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) altriIncarichi.get(i), true);
						PeFiAltriIncarichi personaFisica = altriSoggettiCoinvolti.addNewPersonaFisica();
						personaFisica.setProfiloAltriIncarichi(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						personaFisica.setNum(i);
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	

		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaAdesioneLav", "getSchedaAdesioneLav", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAdesioneLav: fine metodo");
		  }
		  
		  return doc;
	  }
	  
	  /**
	   * Fase aggiudicazione lavori Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numAppa Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaAdesioneServForn(final long codGara,
		      final long codLott, final long numAppa) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAdesioneServForn: inizio metodo");
		  } 
		  
		  DataColumnContainer w9lott, w9appa;
		  SchedaAggServFornSezOssAdesioneDocument doc = SchedaAggServFornSezOssAdesioneDocument.Factory.newInstance();
		  
		  try { 
			  SchedaAggServFornSezOssAdesione schedaAdesione = doc.addNewSchedaAggServFornSezOssAdesione();
			  SchedaAggServFornSezOssAdesione.Settings settings = schedaAdesione.addNewSettings();
			  w9lott = new DataColumnContainer(sqlManager, "W9LOTT", SQL_DATI_LOTTO, new Object[] {codGara, codLott});
			  w9appa = new DataColumnContainer(sqlManager, "W9APPA", SQL_DATI_APPALTO, new Object[] {codGara, codLott, numAppa});
			  
			  settings.setTiposcheda("AggiudicazioneServFornSezioneOsservatorioAdesione");

			  SchedaAggServFornSezOssAdesione.AggiudicazioneServFornSezioneOsservatorioAdesione adesione = schedaAdesione.addNewAggiudicazioneServFornSezioneOsservatorioAdesione();

			  Calendar date = Calendar.getInstance();
			  adesione.setCodiceNuts((w9lott.getString("LUOGO_NUTS")!= null)?w9lott.getString("LUOGO_NUTS"):"");
			  //Codice dello strumento di programmazione  
			  if (w9appa.getString("COD_STRUMENTO") != null) {
				  adesione.setStrumProgId(codificaSitatToSitar("W3z01", w9appa.getString("COD_STRUMENTO")));
			  } else {
				  adesione.setStrumProgId("0");
			  }
			  adesione.setImpAdesioneCompLav(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_LAVORI"))));
			  adesione.setImpAdesioneCompServ(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_SERVIZI"))));
			  adesione.setImpAdesioneCompForn(new BigDecimal(convertiImporto(w9appa.getDouble("IMPORTO_FORNITURE"))));
			  if (w9appa.getLong("NUM_IMP_ESCL_INSUF_GIUST") != null) {
				  adesione.setNEsclInsGiustific(w9appa.getLong("NUM_IMP_ESCL_INSUF_GIUST").intValue());
			  }
			  if (w9appa.getDouble("PERC_OFF_AUMENTO") != null) {
				  adesione.setOffertaAumento(new BigDecimal(w9appa.getDouble("PERC_OFF_AUMENTO")));
			  }
			  adesione.setChiestiSubapp((w9appa.getString("FLAG_RICH_SUBAPPALTO")!=null && w9appa.getString("FLAG_RICH_SUBAPPALTO").equals("1"))?"Y":"N");

			  //Soggetti coinvolti
			  SchedaAggServFornSezOssAdesione.AggiudicazioneServFornSezioneOsservatorioAdesione.IncarichiProgettazione soggettiCoinvolti = adesione.addNewIncarichiProgettazione();
			  //1,2,3,4
			  List< ? > incarichi = sqlManager.getListHashMap(SQL_DATI_INCARICHI_ADESIONE, new Object[] {codGara, codLott, numAppa , "1", "2", "3", "4"});
			  if (incarichi != null && incarichi.size() > 0) {
				  for (int i = 0; i < incarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_INCARICHI_ADESIONE, new Object[] { codGara, codLott, numAppa , "1", "2", "3", "4"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) incarichi.get(i), true);
						PeFiProgett personaFisica = soggettiCoinvolti.addNewPersonaFisica();
						personaFisica.setNum(i);
						if (verificaEsistenzaValore(w9inca.getString("CIG_PROG_ESTERNA"))) {
							personaFisica.setCigAffidIncEst(w9inca.getString("CIG_PROG_ESTERNA"));
						}
						if (w9inca.getData("DATA_AFF_PROG_ESTERNA") != null) {
							date.setTime(w9inca.getData("DATA_AFF_PROG_ESTERNA"));
							personaFisica.setDataAffidInc(date);
						}
						if (w9inca.getData("DATA_CONS_PROG_ESTERNA") != null) {
							date.setTime(w9inca.getData("DATA_CONS_PROG_ESTERNA"));
							personaFisica.setDataConsProg(date);
						}
						personaFisica.setProfilo(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	

			  //Finanziamenti
			  SchedaAggServFornSezOssAdesione.AggiudicazioneServFornSezioneOsservatorioAdesione.Finanziamenti finanziamenti = adesione.addNewFinanziamenti();
			  List< ? > listaFinanziamenti = sqlManager.getListHashMap(SQL_DATI_FINANZIAMENTI, new Object[] {codGara, codLott, numAppa});
			  if (listaFinanziamenti != null && listaFinanziamenti.size() > 0) {
				  for (int i = 0; i < listaFinanziamenti.size(); i++) {
						DataColumnContainer w9fina = new DataColumnContainer(sqlManager, "W9FINA", SQL_DATI_FINANZIAMENTI, new Object[] { codGara, codLott, numAppa});
						w9fina.setValoriFromMap((HashMap< ? , ? >) listaFinanziamenti.get(i), true);
						SchedaAggServFornSezOssAdesione.AggiudicazioneServFornSezioneOsservatorioAdesione.Finanziamenti.Finanziamento finanziamento = finanziamenti.addNewFinanziamento();
						finanziamento.setTipoFinId(Integer.parseInt(codificaSitatToSitar("W3z02",w9fina.getString("ID_FINANZIAMENTO"))));
						finanziamento.setImpFin(new BigDecimal(w9fina.getDouble("IMPORTO_FINANZIAMENTO")));
						finanziamento.setNum(i);
				  }	
			  }	
			  //Altri Soggetti coinvolti
			  //6,7,8,16,17
			  SchedaAggServFornSezOssAdesione.AggiudicazioneServFornSezioneOsservatorioAdesione.AltriIncarichi altriSoggettiCoinvolti = adesione.addNewAltriIncarichi();
			  List< ? > altriIncarichi = sqlManager.getListHashMap(SQL_DATI_ALTRI_INCARICHI, new Object[] {codGara, codLott, numAppa, "6", "7", "8", "16", "17"});
			  if (altriIncarichi != null && altriIncarichi.size() > 0) {
				  for (int i = 0; i < altriIncarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_ALTRI_INCARICHI, new Object[] { codGara, codLott, numAppa, "6", "7", "8", "16", "17"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) altriIncarichi.get(i), true);
						PeFiAltriIncarichi personaFisica = altriSoggettiCoinvolti.addNewPersonaFisica();
						personaFisica.setProfiloAltriIncarichi(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						personaFisica.setNum(i);
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	

		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaAdesioneServForn", "getSchedaAdesioneServForn", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAdesioneServForn: fine metodo");
		  }
		  
		  return doc;
	  }
	  
	  
	
	/**
	   * Fase iniziale esecuzione contratto Sitar.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numIniz Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  public XmlObject getSchedaInizialeEsecuzioneContratto(final long codGara,
		      final long codLott, final long numIniz) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaInizialeEsecuzioneContratto: inizio metodo");
		  }
		   
		  DataColumnContainer w9iniz, w9pues;
		  SchedaIecDocument doc = SchedaIecDocument.Factory.newInstance();
		  
		  try {
			  
			  SchedaIec schedaIec = doc.addNewSchedaIec();
			  SchedaIec.Settings settings = schedaIec.addNewSettings();
			  w9iniz = new DataColumnContainer(sqlManager, "W9INIZ", SQL_DATI_INIZ, new Object[] {codGara, codLott, numIniz});
			  try {
				  w9pues = new DataColumnContainer(sqlManager, "W9PUES", SQL_DATI_PUBB_ESITO, new Object[] {codGara, codLott, numIniz});
			  } catch (Exception ex) {
				  w9pues = null;
			  }
			  settings.setTiposcheda("IEC");
			 
			  SchedaIec.InizioEsecuzioneContratto inizioec = schedaIec.addNewInizioEsecuzioneContratto();
			  Calendar date = Calendar.getInstance();
			  if (w9pues != null && w9pues.getData("DATA_ALBO") != null) {
				  date.setTime(w9pues.getData("DATA_ALBO"));
				  inizioec.setPubAlboPret(date);
			  } else {
				  inizioec.setNilPubAlboPret();
			  }
			  
			  if (w9pues != null && w9pues.getData("DATA_GUCE") != null) {
				  date.setTime(w9pues.getData("DATA_GUCE"));
				  inizioec.setPubGuCee(date);
			  } else {
				  inizioec.setNilPubGuCee();
			  }
			  
			  if (w9pues != null && w9pues.getData("DATA_GURI") != null) {
				  date.setTime(w9pues.getData("DATA_GURI"));
				  inizioec.setPubGuRi(date);
			  } else {
				  inizioec.setNilPubGuRi();
			  }
			  
			  if (w9pues != null && w9pues.getLong("QUOTIDIANI_NAZ") != null) {
				  inizioec.setQuotNaz(w9pues.getLong("QUOTIDIANI_NAZ").intValue());
			  } else {
				  inizioec.setNilQuotNaz();
			  }
			  if (w9pues != null && w9pues.getLong("QUOTIDIANI_REG") != null) {
				  inizioec.setQuotLoc(w9pues.getLong("QUOTIDIANI_REG").intValue());
			  } else {
				  inizioec.setNilQuotLoc();
			  }
			  
			  inizioec.setProfiloCommittente((w9pues != null && w9pues.getString("PROFILO_COMMITTENTE")!=null && w9pues.getString("PROFILO_COMMITTENTE").equals("1"))?"Y":"N");
			  inizioec.setSitoMinInfr((w9pues != null && w9pues.getString("SITO_MINISTERO_INF_TRASP")!=null && w9pues.getString("SITO_MINISTERO_INF_TRASP").equals("1"))?"Y":"N");
			  inizioec.setSitoOss((w9pues != null && w9pues.getString("SITO_OSSERVATORIO_CP")!=null && w9pues.getString("SITO_OSSERVATORIO_CP").equals("1"))?"Y":"N");
			  
			  if (w9iniz.getData("DATA_STIPULA") != null) {
				  date.setTime(w9iniz.getData("DATA_STIPULA"));
				  inizioec.setDataStipulaContratto(date);
			  } else {
				  inizioec.setNilDataStipulaContratto();
			  }
			  
			  if (w9iniz.getData("DATA_ESECUTIVITA") != null) {
				  date.setTime(w9iniz.getData("DATA_ESECUTIVITA"));
				  inizioec.setDataEsecutivitaContratto(date);
			  } else {
				  inizioec.setNilDataEsecutivitaContratto();
			  }
			  
			  if (w9iniz.getDouble("IMPORTO_CAUZIONE") != null) {
				  inizioec.setImpCauzioneDefinitiva(new BigDecimal(convertiImporto(w9iniz.getDouble("IMPORTO_CAUZIONE"))));
			  } else {
				  inizioec.setNilImpCauzioneDefinitiva();
			  }
			  
			  if (w9iniz.getData("DATA_INI_PROG_ESEC") != null) {
				  date.setTime(w9iniz.getData("DATA_INI_PROG_ESEC"));
				  inizioec.setDataInizioProgEsecutivo(date);
			  } else {
				  inizioec.setNilDataInizioProgEsecutivo();
			  }
			  
			  if (w9iniz.getData("DATA_APP_PROG_ESEC") != null) {
				  date.setTime(w9iniz.getData("DATA_APP_PROG_ESEC"));
				  inizioec.setDataAppProgEsecutivo(date);
			  } else {
				  inizioec.setNilDataAppProgEsecutivo();
			  }
			  
			  
			  inizioec.setConsegnaFrazionata((w9iniz.getString("FLAG_FRAZIONATA")!=null && w9iniz.getString("FLAG_FRAZIONATA").equals("1"))?"Y":"N");
			  
			  if (w9iniz.getData("DATA_VERB_INIZIO") != null) {
				  date.setTime(w9iniz.getData("DATA_VERB_INIZIO"));
				  inizioec.setDataVerbaleAvvioIecFasi(date);
			  } else {
				  inizioec.setNilDataVerbaleAvvioIecFasi();
			  }
			  
			  if (w9iniz.getData("DATA_VERBALE_DEF") != null) {
				  date.setTime(w9iniz.getData("DATA_VERBALE_DEF"));
				  inizioec.setDataVerbConsegnaOAvvioIec(date);
			  } else {
				  inizioec.setDataVerbConsegnaOAvvioIec(null);
			  }
			  
			  inizioec.setRiservaLegge((w9iniz.getString("FLAG_RISERVA")!=null && w9iniz.getString("FLAG_RISERVA").equals("1"))?"Y":"N");
			  
			  if (w9iniz.getData("DATA_VERB_INIZIO") != null) {
				  date.setTime(w9iniz.getData("DATA_VERB_INIZIO"));
				  inizioec.setDataInizioLavori(date);
			  } else {
				  inizioec.setDataInizioLavori(null);
			  }
	 
			  if (w9iniz.getData("DATA_TERMINE") != null) {
				  date.setTime(w9iniz.getData("DATA_TERMINE"));
				  inizioec.setDataFineLavori(date);
			  } else {
				  inizioec.setDataFineLavori(null);
			  }
			  
			  //Soggetti coinvolti
			  IncarichiIEC incarichiIEC = inizioec.addNewIncarichiIEC();
			  //5,6,7,8,16,17
			  List< ? > incarichi = sqlManager.getListHashMap(SQL_DATI_INCARICHI_INIZIO, new Object[] {codGara, codLott, numIniz, "5", "6", "7", "8", "16", "17"});
			  if (incarichi != null && incarichi.size() > 0) {
				  for (int i = 0; i < incarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_INCARICHI_INIZIO, new Object[] { codGara, codLott, numIniz, "5", "6", "7", "8", "16", "17"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) incarichi.get(i), true);
						PeFiIncarichiIEC personaFisica = incarichiIEC.addNewPersonaFisica();
						personaFisica.setNum(i);
						personaFisica.setProfiloIncarichiIEC(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	
			  
			  //imprese
			  this.setPosizioni(inizioec, codGara, codLott);
			  
		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaInizialeEsecuzioneContratto", "getSchedaInizialeEsecuzioneContratto", e);
		  }  
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaInizialeEsecuzioneContratto: fine metodo");
		  }

		  return doc;
	  }
	  
	  
	  /**
	   * Fase accordo bonario Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numAcco Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaAccordoBonario(final long codGara,
		      final long codLott, final long numAcco) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAccordoBonario: inizio metodo");
		  } 
		  
		  DataColumnContainer w9acco;
		  SchedaAccordoBonarioDocument doc = SchedaAccordoBonarioDocument.Factory.newInstance();
		  
		  try {
			  
			  SchedaAccordoBonario schedaACCBON = doc.addNewSchedaAccordoBonario();
			  SchedaAccordoBonario.Settings settings = schedaACCBON.addNewSettings();
			  w9acco = new DataColumnContainer(sqlManager, "W9ACCO", SQL_DATI_ACCORDO, new Object[] {codGara, codLott, numAcco});
			  
			  settings.setTiposcheda("AccordoBonario");
			  settings.setNumScheda(1);
			  
			  AccordoBonario accordoBonario = schedaACCBON.addNewAccordoBonario();

			  Calendar date = Calendar.getInstance();
			  
			  if (w9acco.getData("DATA_ACCORDO") != null) {
				  date.setTime(w9acco.getData("DATA_ACCORDO"));
				  accordoBonario.setDataAccBon(date);
			  } 
			  
			  if (w9acco.getLong("NUM_RISERVE") != null) {
				  accordoBonario.setNRisTrans(w9acco.getLong("NUM_RISERVE").intValue());
			  } else {
				  accordoBonario.setNRisTrans(0);
			  }
			  
			  if (w9acco.getDouble("ONERI_DERIVANTI") != null) {
				  accordoBonario.setOneri(new BigDecimal(convertiImporto(w9acco.getDouble("ONERI_DERIVANTI"))));
			  } else {
				  accordoBonario.setOneri(new BigDecimal(0));
			  }
			  
		  } catch (Exception e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaAccordoBonario", "getSchedaAccordoBonario", e);
		  }
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaAccordoBonario: fine metodo");
		  } 
		  
		  return doc;
		  
	  }
	  
	  
	  /**
	   * Fase collaudo Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numColl Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaCollaudo(final long codGara,
		      final long codLott, final long numColl) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaCollaudo: inizio metodo");
		  }
		  
		  String sqlW9appa = "SELECT IMP_NON_ASSOG FROM W9APPA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = 1";
		  
		  DataColumnContainer w9coll, w9appa;
		  SchedaCollaudoDocument doc = SchedaCollaudoDocument.Factory.newInstance();
		  
		  try {
			  
			  SchedaCollaudo schedaCOLL = doc.addNewSchedaCollaudo();
			  SchedaCollaudo.Settings settings = schedaCOLL.addNewSettings();
			  w9coll = new DataColumnContainer(sqlManager, "W9COLL", SQL_DATI_COLLAUDO, new Object[] {codGara, codLott, numColl});
			  w9appa = new DataColumnContainer(sqlManager, "W9APPA", sqlW9appa, new Object[] {codGara, codLott});
			  
			  settings.setTiposcheda("Collaudo");
			  
			  SchedaCollaudo.Collaudo collaudo = schedaCOLL.addNewCollaudo();

			  Calendar date = Calendar.getInstance();
			  
			  if (w9coll.getData("DATA_COLLAUDO_STAT") != null) {
				  date.setTime(w9coll.getData("DATA_COLLAUDO_STAT"));
				  collaudo.setDataCollStat(date);
			  } 
			  
			  if (w9coll.getData("DATA_REGOLARE_ESEC") != null) {
				  date.setTime(w9coll.getData("DATA_REGOLARE_ESEC"));
				  collaudo.setDataCertRegEsec(date);
			  } 
			  
			  if (w9coll.getData("DATA_NOMINA_COLL") != null) {
				  date.setTime(w9coll.getData("DATA_NOMINA_COLL"));
				  collaudo.setDataNomina(date);
			  } 
			  
			  if (w9coll.getData("DATA_INIZIO_OPER") != null) {
				  date.setTime(w9coll.getData("DATA_INIZIO_OPER"));
				  collaudo.setDataInizioOperaz(date);
			  } 
			  
			  if (w9coll.getData("DATA_CERT_COLLAUDO") != null) {
				  date.setTime(w9coll.getData("DATA_CERT_COLLAUDO"));
				  collaudo.setDataRedazCert(date);
			  } 
			  
			  if (w9coll.getData("DATA_DELIBERA") != null) {
				  date.setTime(w9coll.getData("DATA_DELIBERA"));
				  collaudo.setDataDeliberaAmmiss(date);
			  } 
		  
			  if (w9coll.getString("ESITO_COLLAUDO")!=null) {
				  if (w9coll.getString("ESITO_COLLAUDO").equals("P")) {
					  collaudo.setEsitoColl("positivo");
				  } else {
					  collaudo.setEsitoColl("negativo");
				  }
			  }
			  
			  collaudo.setNilImpFinaleCompLav();
			  collaudo.setNilImpFinaleCompServ();
			  collaudo.setNilImpFinaleCompForn();
			  collaudo.setNilImpFinaleSic();
			  collaudo.setNilImpNonRibAsta();
			  collaudo.setNilImpProgett();
			  collaudo.setNilImpSommeEffImp();
			  
			  if (w9coll.getDouble("IMP_FINALE_LAVORI") != null) {
				  collaudo.setImpFinaleCompLav(new BigDecimal(convertiImporto(w9coll.getDouble("IMP_FINALE_LAVORI"))));
			  } 
			  
			  if (w9coll.getDouble("IMP_FINALE_SERVIZI") != null) {
				  collaudo.setImpFinaleCompServ(new BigDecimal(convertiImporto(w9coll.getDouble("IMP_FINALE_SERVIZI"))));
			  }
			  
			  if (w9coll.getDouble("IMP_FINALE_FORNIT") != null) {
				  collaudo.setImpFinaleCompForn(new BigDecimal(convertiImporto(w9coll.getDouble("IMP_FINALE_FORNIT"))));
			  }
			  
			  if (w9coll.getDouble("IMP_FINALE_SECUR") != null) {
				  collaudo.setImpFinaleSic(new BigDecimal(convertiImporto(w9coll.getDouble("IMP_FINALE_SECUR"))));
			  }
			  
			  if (w9appa.getDouble("IMP_NON_ASSOG") != null) {
				  collaudo.setImpNonRibAsta(new BigDecimal(convertiImporto(w9appa.getDouble("IMP_NON_ASSOG"))));
			  }
			  
			  if (w9coll.getDouble("IMP_PROGETTAZIONE") != null) {
				  collaudo.setImpProgett(new BigDecimal(convertiImporto(w9coll.getDouble("IMP_PROGETTAZIONE"))));
			  }
			  
			  if (w9coll.getDouble("IMP_DISPOSIZIONE") != null) {
				  collaudo.setImpSommeEffImp(new BigDecimal(convertiImporto(w9coll.getDouble("IMP_DISPOSIZIONE"))));
			  }
			  
			  if (w9coll.getLong("MODO_COLLAUDO") != null) {
				  if (w9coll.getLong("MODO_COLLAUDO") == 1) {
					  collaudo.setModCollTecAmmFinale("Y");
				  } else if (w9coll.getLong("MODO_COLLAUDO") == 2) {
					  collaudo.setModCollTecAmmIncorso("Y");
				  }
			  } 
			  
			  collaudo.setRisAmmCollNDef(0);
			  collaudo.setRisAmmCollNDaDef(0);
			  collaudo.setRisAmmCollImpRic(new BigDecimal(0));
			  collaudo.setRisAmmCollImpTotEvDef(new BigDecimal(0));
			  
			  if (w9coll.getLong("AMM_NUM_DEFINITE") != null) {
				  collaudo.setRisAmmCollNDef(w9coll.getLong("AMM_NUM_DEFINITE").intValue());
			  } 
			  
			  if (w9coll.getLong("AMM_NUM_DADEF") != null) {
				  collaudo.setRisAmmCollNDaDef(w9coll.getLong("AMM_NUM_DADEF").intValue());
			  } 
			  
			  if (w9coll.getDouble("AMM_IMPORTO_RICH") != null) {
				  collaudo.setRisAmmCollImpRic(new BigDecimal(convertiImporto(w9coll.getDouble("AMM_IMPORTO_RICH"))));
			  } 
			  
			  if (w9coll.getDouble("AMM_IMPORTO_DEF") != null) {
				  collaudo.setRisAmmCollImpTotEvDef(new BigDecimal(convertiImporto(w9coll.getDouble("AMM_IMPORTO_DEF"))));
			  } 
			  
			  collaudo.setRisArbitrImpDef(new BigDecimal(0));
			  collaudo.setRisArbitrImpRic(new BigDecimal(0));
			  collaudo.setRisArbitrNDaDef(0);
			  collaudo.setRisArbitrNDef(0);
			  
			  if (w9coll.getDouble("ARB_IMPORTO_DEF") != null) {
				  collaudo.setRisArbitrImpDef(new BigDecimal(convertiImporto(w9coll.getDouble("ARB_IMPORTO_DEF"))));
			  }
			  
			  if (w9coll.getDouble("ARB_IMPORTO_RICH") != null) {
				  collaudo.setRisArbitrImpRic(new BigDecimal(convertiImporto(w9coll.getDouble("ARB_IMPORTO_RICH"))));
			  }
			  
			  if (w9coll.getLong("ARB_NUM_DADEF") != null) {
				  collaudo.setRisArbitrNDaDef(w9coll.getLong("ARB_NUM_DADEF").intValue());
			  }
			  
			  if (w9coll.getLong("ARB_NUM_DEFINITE") != null) {
				  collaudo.setRisArbitrNDef(w9coll.getLong("ARB_NUM_DEFINITE").intValue());
			  }
			  
			  collaudo.setRisGiudImpDef(new BigDecimal(0));
			  collaudo.setRisGiudImpRic(new BigDecimal(0));
			  collaudo.setRisGiudNDaDef(0);
			  collaudo.setRisGiudNDef(0);
			  
			  if (w9coll.getDouble("GIU_IMPORTO_DEF") != null) {
				  collaudo.setRisGiudImpDef(new BigDecimal(convertiImporto(w9coll.getDouble("GIU_IMPORTO_DEF"))));
			  }
			  
			  if (w9coll.getDouble("GIU_IMPORTO_RICH") != null) {
				  collaudo.setRisGiudImpRic(new BigDecimal(convertiImporto(w9coll.getDouble("GIU_IMPORTO_RICH"))));
			  }
			  
			  if (w9coll.getLong("GIU_NUM_DADEF") != null) {
				  collaudo.setRisGiudNDaDef(w9coll.getLong("GIU_NUM_DADEF").intValue());
			  }
			  
			  if (w9coll.getLong("GIU_NUM_DEFINITE") != null) {
				  collaudo.setRisGiudNDef(w9coll.getLong("GIU_NUM_DEFINITE").intValue());
			  }
			  
			  collaudo.setRisTransattImpEvDef(new BigDecimal(0));
			  collaudo.setRisTransattImpRic(new BigDecimal(0));
			  collaudo.setRisTransattNDaDef(0);
			  collaudo.setRisTransattNDef(0);
			  
			  if (w9coll.getDouble("TRA_IMPORTO_DEF") != null) {
				  collaudo.setRisTransattImpEvDef(new BigDecimal(convertiImporto(w9coll.getDouble("TRA_IMPORTO_DEF"))));
			  }
			  
			  if (w9coll.getDouble("TRA_IMPORTO_RICH") != null) {
				  collaudo.setRisTransattImpRic(new BigDecimal(convertiImporto(w9coll.getDouble("TRA_IMPORTO_RICH"))));
			  }
			  
			  if (w9coll.getLong("TRA_NUM_DADEF") != null) {
				  collaudo.setRisTransattNDaDef(w9coll.getLong("TRA_NUM_DADEF").intValue());
			  }
			  
			  if (w9coll.getLong("TRA_NUM_DEFINITE") != null) {
				  collaudo.setRisTransattNDef(w9coll.getLong("TRA_NUM_DEFINITE").intValue());
			  }
			  
			  //Soggetti coinvolti
			  IncarichiCollaudo soggettiCoinvolti = collaudo.addNewIncarichiCollaudo();
			  //9,10,11,12,13
			  List< ? > incarichi = sqlManager.getListHashMap(SQL_DATI_INCARICHI_COLLAUDO, new Object[] {codGara, codLott, numColl, "9", "10", "11", "12", "13"});
			  if (incarichi != null && incarichi.size() > 0) {
				  for (int i = 0; i < incarichi.size(); i++) {
						DataColumnContainer w9inca = new DataColumnContainer(sqlManager, "W9INCA", SQL_DATI_INCARICHI_COLLAUDO, new Object[] { codGara, codLott, numColl, "9", "10", "11", "12", "13"});
						w9inca.setValoriFromMap((HashMap< ? , ? >) incarichi.get(i), true);
						PeFiIncarichiCollaudo personaFisica = soggettiCoinvolti.addNewPersonaFisica();
						personaFisica.setNum(i);
						personaFisica.setProfiloIncarichiCollaudo(Integer.parseInt(codificaSitatToSitar("W3004", w9inca.getLong("ID_RUOLO").toString())));
						addPersonaFisica(personaFisica, w9inca.getString("CODTEC"));
				  }	
			  }	
			  
		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaCollaudo", "getSchedaCollaudo", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaCollaudo: fine metodo");
		  }

		  return doc;
	  }
	  
	  
	  /**
	   * Fase conclusione Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numConc Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaConclusione(final long codGara,
		      final long codLott, final long numConc) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaConclusione: inizio metodo");
		  }
		  
		  DataColumnContainer w9conc;
		  SchedaConclusioneContrattoDocument doc = SchedaConclusioneContrattoDocument.Factory.newInstance();
		  
		  try {
			  
			  SchedaConclusioneContratto schedaCONC = doc.addNewSchedaConclusioneContratto();
			  SchedaConclusioneContratto.Settings settings = schedaCONC.addNewSettings();
			  w9conc = new DataColumnContainer(sqlManager, "W9CONC", SQL_DATI_CONCLUSIONE, new Object[] {codGara, codLott, numConc});
			  
			  settings.setTiposcheda("Conclusione_contratto");
			   
			  SchedaConclusioneContratto.ConclusioneContratto conclusioneContratto = schedaCONC.addNewConclusioneContratto();

			  Calendar date = Calendar.getInstance();
			  
			  if (verificaEsistenzaValore(w9conc.getLong("ID_MOTIVO_INTERR"))) {
				  conclusioneContratto.setCausaInterrAnt(w9conc.getLong("ID_MOTIVO_INTERR").intValue());
			  } else {
				  conclusioneContratto.setCausaInterrAnt(6);
			  }
			  
			  if (verificaEsistenzaValore(w9conc.getLong("ID_MOTIVO_RISOL"))) {
				  conclusioneContratto.setMotivRisol(w9conc.getLong("ID_MOTIVO_RISOL").intValue());
			  }
			  
			  if (w9conc.getData("DATA_RISOLUZIONE") != null) {
				  date.setTime(w9conc.getData("DATA_RISOLUZIONE"));
				  conclusioneContratto.setDataRisoluz(date);
			  } 
			  
			  if (verificaEsistenzaValore(w9conc.getString("FLAG_ONERI"))) {
				  conclusioneContratto.setTipoOneriRisRec(Integer.parseInt(w9conc.getString("FLAG_ONERI"))+1);
			  }
			  
			  if (verificaEsistenzaValore(w9conc.getDouble("ONERI_RISOLUZIONE"))) {
				  conclusioneContratto.setImportoOneri(new BigDecimal(convertiImporto(w9conc.getDouble("ONERI_RISOLUZIONE"))));
			  }
			  
			  conclusioneContratto.setIncamerataPolizza((w9conc.getString("FLAG_POLIZZA")!=null && w9conc.getString("FLAG_POLIZZA").equals("1"))?"Y":"N");
			  
			  if (w9conc.getData("DATA_ULTIMAZIONE") != null) {
				  date.setTime(w9conc.getData("DATA_ULTIMAZIONE"));
				  conclusioneContratto.setDataUltimazLav(date);
			  } 
			  
			  if (w9conc.getLong("NUM_INFORTUNI") != null) {
				  conclusioneContratto.setNInfort(w9conc.getLong("NUM_INFORTUNI").intValue());
			  } 
			  
			  if (w9conc.getLong("NUM_INF_MORT") != null) {
				  conclusioneContratto.setNInfortMort(w9conc.getLong("NUM_INF_MORT").intValue());
			  } 
			  
			  if (w9conc.getLong("NUM_INF_PERM") != null) {
				  conclusioneContratto.setNInfortPostumiPermanenti(w9conc.getLong("NUM_INF_PERM").intValue());
			  } 
			  
		  } catch (Exception e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaConclusione", "getSchedaConclusione", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaConclusione: fine metodo");
		  } 
		  
		  return doc;
	  }
	  
	  
	  public XmlObject getSchedaChiusuraAnticipataContratto(final long codGara,
		      final long codLott, final long numConc) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaChiusuraAnticipataContratto: inizio metodo");
		  }
		  
		  DataColumnContainer w9conc;
		  SchedaChiusuraAnticipataContrattoDocument doc = SchedaChiusuraAnticipataContrattoDocument.Factory.newInstance();
		  
		  try {
			  
			  SchedaChiusuraAnticipataContratto schedaCONC = doc.addNewSchedaChiusuraAnticipataContratto();
			  SchedaChiusuraAnticipataContratto.Settings settings = schedaCONC.addNewSettings();
			  w9conc = new DataColumnContainer(sqlManager, "W9CONC", SQL_DATI_CONCLUSIONE, new Object[] {codGara, codLott, numConc});
			  
			  settings.setTiposcheda("ChiusuraAnticipataContratto");
			   
			  SchedaChiusuraAnticipataContratto.ChiusuraAnticipataContratto chiusuraAnticipata = schedaCONC.addNewChiusuraAnticipataContratto();

			  Calendar date = Calendar.getInstance();
			  
			  Long causaInterruzioneAnticipata = w9conc.getLong("ID_MOTIVO_INTERR");
			  Long motivazioneRisoluzione = w9conc.getLong("ID_MOTIVO_RISOL");
			  chiusuraAnticipata.setIdRescissione(0);
			  if (verificaEsistenzaValore(causaInterruzioneAnticipata)) {
				  switch (causaInterruzioneAnticipata.intValue()){
				  	case 2:
				  		chiusuraAnticipata.setTipoChiusura("risoluzione");
				  		if (verificaEsistenzaValore(motivazioneRisoluzione)) {
				  			chiusuraAnticipata.setIdRescissione(motivazioneRisoluzione.intValue());
						}
				  		break;
				  	case 1:case 3:
				  		chiusuraAnticipata.setTipoChiusura("risoluzione");
				  		break;
					default:
						chiusuraAnticipata.setTipoChiusura("rescissione");
						break;
				  }
			  }
			  
			  if (w9conc.getData("DATA_RISOLUZIONE") != null) {
				  date.setTime(w9conc.getData("DATA_RISOLUZIONE"));
				  chiusuraAnticipata.setDataRescissione(date);
			  } else {
				  chiusuraAnticipata.setNilDataRescissione();
			  }
			  chiusuraAnticipata.setAltroSpecificare("");
			  chiusuraAnticipata.setPolizza((w9conc.getString("FLAG_POLIZZA")!=null && w9conc.getString("FLAG_POLIZZA").equals("1"))?"Y":"N");
			  
			  String flagOneri = w9conc.getString("FLAG_ONERI");
			  chiusuraAnticipata.setRisarcimento("N");
			  if (verificaEsistenzaValore(flagOneri)) {
				  if (flagOneri.equals("1")) {
					  chiusuraAnticipata.setRisarcimento("Y");
				  }
			  }
			  chiusuraAnticipata.setInizLavori("N");
			  if (verificaEsistenzaValore(w9conc.getLong("ORE_LAVORATE")) && w9conc.getLong("ORE_LAVORATE")>0) {
				  chiusuraAnticipata.setInizLavori("Y");
			  }
			  
			  chiusuraAnticipata.setGiudizio("N");
			  chiusuraAnticipata.setGiudizioArbitrale("N");
			  chiusuraAnticipata.setGiudizioOrdinario("N");
			  chiusuraAnticipata.setNilDataInizioGiudizio();
			  chiusuraAnticipata.setNilDataDefinizioneGiudizio();
			  chiusuraAnticipata.setEsitoGiudizio("N");
			  chiusuraAnticipata.setIsRisolBon("N");
			  chiusuraAnticipata.setNilDataRisolBon();
			  chiusuraAnticipata.setIsRisAnt("N");
			  chiusuraAnticipata.setNilDataRisAnt();
			  
		  } catch (Exception e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaChiusuraAnticipataContratto", "getSchedaChiusuraAnticipataContratto", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaChiusuraAnticipataContratto: fine metodo");
		  } 
		  
		  return doc;
	  }
	  
	  
	  /**
	   * Fase conclusione Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numConc Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaConclusioneSemplificata(final long codGara,
		      final long codLott, final long numConc) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaConclusioneSemplificata: inizio metodo");
		  }
		  
		  DataColumnContainer w9conc;
		  SchedaUltimazLavoriSottosogliaDocument doc = SchedaUltimazLavoriSottosogliaDocument.Factory.newInstance();
		  
		  try {
			  
			  SchedaUltimazLavoriSottosoglia schedaCONC = doc.addNewSchedaUltimazLavoriSottosoglia();
			  SchedaUltimazLavoriSottosoglia.Settings settings = schedaCONC.addNewSettings();
			  w9conc = new DataColumnContainer(sqlManager, "W9CONC", SQL_DATI_CONCLUSIONE, new Object[] {codGara, codLott, numConc});
			  
			  settings.setTiposcheda("Ultimazione_lavori_sott");
			   
			  SchedaUltimazLavoriSottosoglia.UltimazioneLavoriSott ultimazioneLavori = schedaCONC.addNewUltimazioneLavoriSott();

			  Calendar date = Calendar.getInstance();
			  
			  if (w9conc.getData("DATA_ULTIMAZIONE") != null) {
				  date.setTime(w9conc.getData("DATA_ULTIMAZIONE"));
				  ultimazioneLavori.setDataCertUltimLav(date);
			  } 
			  
			  ultimazioneLavori.setNilScostDurContratt();
			  if (verificaEsistenzaValore(w9conc.getDouble("ONERI_RISOLUZIONE"))) {
				  ultimazioneLavori.setMaggioriOneri(new BigDecimal(convertiImporto(w9conc.getDouble("ONERI_RISOLUZIONE"))));
			  } else {
				  ultimazioneLavori.setNilMaggioriOneri();
			  }
			  
		  } catch (Exception e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaConclusioneSemplificata", "getSchedaConclusioneSemplificata", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaConclusioneSemplificata: fine metodo");
		  }
		  
		  return doc;
	  }
	  
	  /**
	   * Fase sospensione Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numSosp Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaSospensione(final long codGara,
		      final long codLott, final long numSosp) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaSospensione: inizio metodo");
		  }
		  
		  DataColumnContainer w9sosp;
		  SchedaSospensioneDocument doc = SchedaSospensioneDocument.Factory.newInstance();
		  
		  try {
			  
			  SchedaSospensione schedaSospenzione = doc.addNewSchedaSospensione();
			  SchedaSospensione.Settings settings = schedaSospenzione.addNewSettings();
			  w9sosp = new DataColumnContainer(sqlManager, "W9SOSP", SQL_DATI_SOSPENSIONE, new Object[] {codGara, codLott, numSosp});

			  settings.setTiposcheda("Sospensione");
			  settings.setNumScheda((int)numSosp);
			  SchedaSospensione.Sospensione sospensione = schedaSospenzione.addNewSospensione();

			  Calendar date = Calendar.getInstance();
			  sospensione.setContrattoAttSospeso("N");
			  if (w9sosp.getData("DATA_VERB_SOSP") != null) {
				  date.setTime(w9sosp.getData("DATA_VERB_SOSP"));
				  sospensione.setDataVerbSosp(date);
			  } 
			  
			  if (w9sosp.getData("DATA_VERB_RIPR") != null) {
				  date.setTime(w9sosp.getData("DATA_VERB_RIPR"));
				  sospensione.setDataVerbRipr(date);
			  } else {
				  if (w9sosp.getData("DATA_VERB_SOSP") != null) {
					  sospensione.setContrattoAttSospeso("Y");
				  }
			  }
			  
			  sospensione.setMotivForzaMagg("N");
			  sospensione.setMotivNatTecn("N");
			  sospensione.setMotivNatAmm("N");
			  sospensione.setMotivVarInEsec("N");
			  sospensione.setMotivAutGiud("N");
			  sospensione.setMotivPubblInt("N");
			  sospensione.setMotivCondClim("N");
			  
			  if (w9sosp.getLong("ID_MOTIVO_SOSP") != null) {
				  Long idMotivoSospensione = w9sosp.getLong("ID_MOTIVO_SOSP");
				  switch (idMotivoSospensione.intValue()) {
				  	case 1:
				  		sospensione.setMotivForzaMagg("Y");
				  		break;
				  	case 2:
				  		sospensione.setMotivNatTecn("Y");
				  		break;
				  	case 3:
				  		sospensione.setMotivNatAmm("Y");
				  		break;
				  	case 4:
				  		sospensione.setMotivVarInEsec("Y");
				  		break;
				  	case 5:
				  		sospensione.setMotivAutGiud("Y");
				  		break;
				  	case 6:
				  		sospensione.setMotivCondClim("Y");
				  		break;
				  	case 7:
				  		sospensione.setMotivPubblInt("Y");
				  		break;
				  	default:
				  		break;
				  }
			  }
			  
			  if (w9sosp.getString("FLAG_SUPERO_TEMPO")!=null) {
				  sospensione.setSupQuartoTempContr((w9sosp.getString("FLAG_SUPERO_TEMPO").equals("1"))?"Y":"N");
			  }
			  if (w9sosp.getString("FLAG_RISERVE")!=null) {
				  sospensione.setIscrRisApp((w9sosp.getString("FLAG_RISERVE").equals("1"))?"Y":"N");
			  }
			  if (w9sosp.getString("FLAG_VERBALE")!=null) {
				  sospensione.setVerbNonSottoscr((w9sosp.getString("FLAG_VERBALE").equals("1"))?"Y":"N");
			  }
			  
		  } catch (Exception e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaSospensione", "getSchedaSospensione", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaSospensione: fine metodo");
		  }
		  
		  return doc;
	  }
	  
	  /**
	   * Fase subappalto Sitar.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numSuba Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaSubappalto(final long codGara,
		      final long codLott, final long numSuba) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaSubappalto: inizio metodo");
		  }
		  
		  String sqlIMPR = "SELECT * FROM IMPR WHERE CODIMP = ? ";
		  
		  DataColumnContainer w9suba, impr;
		  SchedaSubappaltoDocument doc = SchedaSubappaltoDocument.Factory.newInstance();
		  
		  try {
			  SchedaSubappalto schedaSubappalto = doc.addNewSchedaSubappalto();
			  SchedaSubappalto.Settings settings = schedaSubappalto.addNewSettings();
			  w9suba = new DataColumnContainer(sqlManager, "W9SUBA", SQL_DATI_SUBAPPALTO, new Object[] {codGara, codLott, numSuba});
			  
			  settings.setTiposcheda("Subappalto");
			  settings.setNumScheda((int)numSuba);
			  SchedaSubappalto.Subappalto subappalto = schedaSubappalto.addNewSubappalto();

			  Calendar date = Calendar.getInstance();
			  
			  if (w9suba.getData("DATA_AUTORIZZAZIONE") != null) {
				  date.setTime(w9suba.getData("DATA_AUTORIZZAZIONE"));
				  subappalto.setDataAutorizz(date);
			  } 
			  
			  subappalto.setLavServFornSub((w9suba.getString("OGGETTO_SUBAPPALTO")!=null)?w9suba.getString("OGGETTO_SUBAPPALTO"):"");
			  if (w9suba.getDouble("IMPORTO_PRESUNTO") != null) {
				  subappalto.setImpPresuntoSub(new BigDecimal(convertiImporto(w9suba.getDouble("IMPORTO_PRESUNTO"))));
			  } else {
				  subappalto.setImpPresuntoSub(new BigDecimal(0));
			  }
			  
			  if (w9suba.getString("ID_CPV")!=null) {
				  subappalto.setCpv(w9suba.getString("ID_CPV"));
			  }
			  
			  if (w9suba.getString("ID_CATEGORIA") != null) {
				  String categoria = this.codificaSitatToSitar("W3z03",w9suba.getString("ID_CATEGORIA"));
				  if (categoria != null) {
					  subappalto.setIdCategoria(Integer.parseInt(categoria));
				  }
			  }
			  
			  if (w9suba.getDouble("IMPORTO_EFFETTIVO") != null) {
				  subappalto.setImpEffettivoSub(new BigDecimal(convertiImporto(w9suba.getDouble("IMPORTO_EFFETTIVO"))));
			  } else {
				  subappalto.setImpEffettivoSub(new BigDecimal(0));
			  }
			  
			  ImpreseSubappalto imprese = subappalto.addNewImpreseSubappalto();

			  if (w9suba.getString("CODIMP")!=null) {
				  impr = new DataColumnContainer(sqlManager, "IMPR", sqlIMPR, new Object[] {w9suba.getString("CODIMP")});
				  
				  PeGiSub ditta = imprese.addNewPersonaGiuridica();
				  if (impr.getString("CFIMP") != null) {
					  ditta.setCodiceFiscale(impr.getString("CFIMP"));
				  } else {
					  ditta.setCodiceFiscale((impr.getString("PIVIMP") != null)?impr.getString("PIVIMP"):"N.D");
				  }
				  if (impr.getString("PIVIMP") != null) {
					  ditta.setPartitaIva(impr.getString("PIVIMP"));
				  } else {
					  if (impr.getString("CFIMP") != null && impr.getString("CFIMP").length() == 11) {
						  ditta.setPartitaIva(impr.getString("CFIMP"));
					  } else {
						  ditta.setPartitaIva("N.D");
					  }
				  }
				  if (verificaEsistenzaValore(impr.getString("NOMEST"))) {
					  ditta.setRagioneSociale(impr.getString("NOMEST"));
				  } else {
					  ditta.setRagioneSociale("-");
				  }
				  ditta.setIndirizzo(impr.getString("INDIMP"));
				  ditta.setCap(impr.getString("CAPIMP"));
				  if (verificaEsistenzaValore(impr.getString("CODCIT"))) {
					  ditta.setCodIstatComune(impr.getString("CODCIT"));
				  }
				  ditta.setProvincia(impr.getString("PROIMP"));
				  ditta.setNum(0);
				  if (verificaEsistenzaValore(impr.getLong("NAZIMP"))) {
					  ditta.setCodiceISO(getStatoEstero(impr.getLong("NAZIMP").toString()));	
				  } else {
					  ditta.setCodiceISO("IT");
				  }
			  }
			  
			  /*if (w9suba.getString("CODIMP_AGGIUDICATRICE")!=null) {
				  impr = new DataColumnContainer(sqlManager, "IMPR", sqlIMPR, new Object[] {w9suba.getString("CODIMP_AGGIUDICATRICE")});
				  
				  PeGiSub ditta = imprese.addNewPersonaGiuridica();
				  if (impr.getString("CFIMP") != null) {
					  ditta.setCodiceFiscale(impr.getString("CFIMP"));
				  } else {
					  ditta.setCodiceFiscale((impr.getString("PIVIMP") != null)?impr.getString("PIVIMP"):"N.D");
				  }
				  if (impr.getString("PIVIMP") != null) {
					  ditta.setPartitaIva(impr.getString("PIVIMP"));
				  } else {
					  if (impr.getString("CFIMP") != null && impr.getString("CFIMP").length() == 11) {
						  ditta.setPartitaIva(impr.getString("CFIMP"));
					  } else {
						  ditta.setPartitaIva("N.D");
					  }
				  }
				  if (verificaEsistenzaValore(impr.getString("NOMEST"))) {
					  ditta.setRagioneSociale(impr.getString("NOMEST"));
				  } else {
					  ditta.setRagioneSociale("-");
				  }
				  ditta.setIndirizzo(impr.getString("INDIMP"));
				  ditta.setCap(impr.getString("CAPIMP"));
				  if (verificaEsistenzaValore(impr.getString("CODCIT"))) {
					  ditta.setCodIstatComune(impr.getString("CODCIT"));
				  }
				  ditta.setProvincia(impr.getString("PROIMP"));
				  ditta.setNum(1);
				  if (verificaEsistenzaValore(impr.getLong("NAZIMP"))) {
					  ditta.setCodiceISO(getStatoEstero(impr.getLong("NAZIMP").toString()));	
				  } else {
					  ditta.setCodiceISO("IT");
				  }
			  }*/
			  
		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaSubappalto", "getSchedaSubappalto", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaSubappalto: fine metodo");
		  }
		  
		  return doc;
	  }
	  
	  
	  /**
	   * Fase Esecuzione Avanzamento Sitar.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numAvan Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaEsecuzioneAvanzamento(final long codGara,
		      final long codLott, final long numAvan) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaEsecuzioneAvanzamento: inizio metodo");
		  }
		  
		  DataColumnContainer w9avan;
		  SchedaSALDocument doc = SchedaSALDocument.Factory.newInstance();
		  
		  try {
			  SchedaSAL schedaSAL = doc.addNewSchedaSAL();
			  SchedaSAL.Settings settings = schedaSAL.addNewSettings();
			  w9avan = new DataColumnContainer(sqlManager, "W9AVAN", SQL_DATI_AVANZAMENTO, new Object[] {codGara, codLott, numAvan});
			  
			  settings.setTiposcheda("SAL");
			  settings.setNumScheda((int)numAvan);
			  SchedaSAL.SAL sal = schedaSAL.addNewSAL();

			  Calendar date = Calendar.getInstance();
			  
			  sal.setModPagDenaro("N");
			  sal.setModPagImmobili("N");
			  
			  if (w9avan.getLong("FLAG_PAGAMENTO") != null) {
				  Long pagamento = w9avan.getLong("FLAG_PAGAMENTO");
				  switch (pagamento.intValue()) {
				  	case 1:
				  		sal.setModPagDenaro("Y");
				  		sal.setModPagImmobili("N");
				  		break;
				  	case 2:
				  		sal.setModPagDenaro("N");
				  		sal.setModPagImmobili("Y");
				  		break;
				  	case 3:
				  		sal.setModPagDenaro("Y");
				  		sal.setModPagImmobili("Y");
				  		break;			  		
				  	default:
				  		break;
				  }
			  }
			  
			  if (w9avan.getDouble("IMPORTO_ANTICIPAZIONE") != null) {
				  sal.setEventAnticip(new BigDecimal(convertiImporto(w9avan.getDouble("IMPORTO_ANTICIPAZIONE"))));
			  }
			  
			  if (w9avan.getData("DATA_RAGGIUNGIMENTO") != null) {
				  date.setTime(w9avan.getData("DATA_RAGGIUNGIMENTO"));
				  sal.setDataStatoAv(date);
			  } 
			  
			  if (w9avan.getDouble("IMPORTO_SAL") != null) {
				  sal.setImportoSal(new BigDecimal(convertiImporto(w9avan.getDouble("IMPORTO_SAL"))));
			  } else {
				  sal.setImportoSal(new BigDecimal(0));
			  }
			  
			  if (w9avan.getData("DATA_CERTIFICATO") != null) {
				  date.setTime(w9avan.getData("DATA_CERTIFICATO"));
				  sal.setDataEmissCertPag(date);
			  } 
			  
			  if (w9avan.getDouble("IMPORTO_CERTIFICATO") != null) {
				  sal.setImportoCertPag(new BigDecimal(convertiImporto(w9avan.getDouble("IMPORTO_CERTIFICATO"))));
			  } else {
				  sal.setImportoCertPag(new BigDecimal(0));
			  }

			  if (w9avan.getString("FLAG_RITARDO") != null) {
				  if (w9avan.getString("FLAG_RITARDO").equals("A")) {
					  sal.setAvanzRaggiunto("anticipo");
				  } else if (w9avan.getString("FLAG_RITARDO").equals("P")) {
					  sal.setAvanzRaggiunto("puntuale");
				  } else if (w9avan.getString("FLAG_RITARDO").equals("R")) {
					  sal.setAvanzRaggiunto("ritardo");
				  }
			  }
			  
			  if (w9avan.getLong("NUM_GIORNI_PROROGA") != null) {
				  sal.setNGgProroga(w9avan.getLong("NUM_GIORNI_PROROGA").intValue());
			  } 
			  
			  if (w9avan.getLong("NUM_GIORNI_SCOST") != null) {
				  sal.setNGgScostamento(w9avan.getLong("NUM_GIORNI_SCOST").intValue());
			  } 
			  
		  } catch (Exception e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaEsecuzioneAvanzamento", "getSchedaEsecuzioneAvanzamento", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaEsecuzioneAvanzamento: fine metodo");
		  }

		  return doc;
	  }
	  
	  /**
	   * Fase Variante Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numVari Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaVarianteServForn(final long codGara,
		      final long codLott, final long numVari) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaVarianteServForn: inizio metodo");
		  }
		  
		  DataColumnContainer w9vari;
		  
		  SchedaVarianteServFornDocument doc = SchedaVarianteServFornDocument.Factory.newInstance();
		  
		  try {
			  SchedaVarianteServForn schedaVarianteServForn = doc.addNewSchedaVarianteServForn();
			  SchedaVarianteServForn.Settings settings = schedaVarianteServForn.addNewSettings();
			  w9vari = new DataColumnContainer(sqlManager, "W9VARI", SQL_DATI_VARIANTE, new Object[] {codGara, codLott, numVari});
			  
			  settings.setTiposcheda("Variante_serv_forn");
			  settings.setNumScheda((int)numVari);
			  SchedaVarianteServForn.Variante variante = schedaVarianteServForn.addNewVariante();

			  Calendar date = Calendar.getInstance();
			  
			  if (w9vari.getData("DATA_VERB_APPR") != null) {
				  date.setTime(w9vari.getData("DATA_VERB_APPR"));
				  variante.setDataAppr(date);
			  } 
			  
			  variante.setMotivSopravvEsig("N");
			  variante.setMotivCauseImprev("N");
			  variante.setMotiviDipNatSpec("N");
			  variante.setMotivMiglFunzPrestaz("N");
			  variante.setMotivAltro("N");
			  variante.setMotivAltroDesc("");
			  
			  List< ? > w9moti = sqlManager.getListVector(SQL_DATI_MOTIVAZIONI, new Object[] {codGara, codLott, numVari});
			  if (w9moti != null && w9moti.size() > 0) {
				  for (int i = 0; i < w9moti.size(); i++) {
					  List< ?> moti = (List< ? >)w9moti.get(i);
					  switch (Integer.parseInt(moti.get(0).toString())) {
					  	case 7:
					  		variante.setMotivSopravvEsig("Y");
					  		break;
					  	case 8:
					  		variante.setMotivCauseImprev("Y");
					  		break;
					  	case 9:
					  		variante.setMotiviDipNatSpec("Y");
					  		break;
					  	case 10:
					  		variante.setMotivMiglFunzPrestaz("Y");
					  		break;
					}
			      }
			  }
			  
			  if (w9vari.getString("ALTRE_MOTIVAZIONI") != null) {
				  variante.setMotivAltro("Y");
				  variante.setMotivAltroDesc(w9vari.getString("ALTRE_MOTIVAZIONI"));
			  }
			  
			  if (w9vari.getDouble("IMP_RIDET_LAVORI") != null) {
				  variante.setImpContrRidetLav(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_RIDET_LAVORI"))));
			  } else {
				  variante.setImpContrRidetLav(new BigDecimal(0));
			  }
			  
			  if (w9vari.getDouble("IMP_RIDET_SERVIZI") != null) {
				  variante.setImpContrRidetServ(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_RIDET_SERVIZI"))));
			  } else {
				  variante.setImpContrRidetServ(new BigDecimal(0));
			  }
			  
			  if (w9vari.getDouble("IMP_RIDET_FORNIT") != null) {
				  variante.setImpContrRidetForn(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_RIDET_FORNIT"))));
			  } else {
				  variante.setImpContrRidetForn(new BigDecimal(0));
			  }
			  
			  if (w9vari.getDouble("IMP_SICUREZZA") != null) {
				  variante.setImpSic(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_SICUREZZA"))));
			  }
			  
			  if (w9vari.getDouble("IMP_DISPOSIZIONE") != null) {
				  variante.setImpNonRibAsta(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_DISPOSIZIONE"))));
			  } 
			  
			  if (w9vari.getDouble("IMP_PROGETTAZIONE") != null) {
				  variante.setImpProg(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_PROGETTAZIONE"))));
			  } 
			  
			  if (w9vari.getDouble("IMP_DISPOSIZIONE") != null) {
				  variante.setImpTotaleSpeseADisposizione(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_DISPOSIZIONE"))));
			  } 
			  
			  if (w9vari.getData("DATA_ATTO_AGGIUNTIVO") != null) {
				  date.setTime(w9vari.getData("DATA_ATTO_AGGIUNTIVO"));
				  variante.setDataAttoAggSottomiss(date);
			  } 

			  if (w9vari.getLong("NUM_GIORNI_PROROGA") != null) {
				  variante.setNGgProroga(w9vari.getLong("NUM_GIORNI_PROROGA").intValue());
			  } else {
				  variante.setNGgProroga(0);
			  }
			  	  
		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaVarianteServForn", "getSchedaVarianteServForn", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaVarianteServForn: fine metodo");
		  }
		  return doc;
	  }
	  
	  
	  /**
	   * Fase Variante Sitar ver 0.2.
	   * 
	   * @param codGara Codice della gara
	   * @param codLotto Codice del lotto
	   * @param numVari Progressivo
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public XmlObject getSchedaVarianteLav(final long codGara,
		      final long codLott, final long numVari) throws GestoreException {
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaVarianteLav: inizio metodo");
		  }
		  
		  DataColumnContainer w9vari;
		  
		  SchedaVarianteLavDocument doc = SchedaVarianteLavDocument.Factory.newInstance();
		  
		  try {
			  SchedaVarianteLav schedaVarianteLav = doc.addNewSchedaVarianteLav();
			  SchedaVarianteLav.Settings settings = schedaVarianteLav.addNewSettings();
			  w9vari = new DataColumnContainer(sqlManager, "W9VARI", SQL_DATI_VARIANTE, new Object[] {codGara, codLott, numVari});
			  
			  settings.setTiposcheda("Variante_lav");
			  settings.setNumScheda((int)numVari);
			  SchedaVarianteLav.Variante variante = schedaVarianteLav.addNewVariante();

			  Calendar date = Calendar.getInstance();
			  
			  if (w9vari.getData("DATA_VERB_APPR") != null) {
				  date.setTime(w9vari.getData("DATA_VERB_APPR"));
				  variante.setDataAppr(date);
			  } 
			  
			  variante.setArt132Comma1LettA("N");
			  variante.setArt132Comma1LettB("N");
			  variante.setArt132Comma1LettC("N");
			  variante.setArt132Comma1LettD("N");
			  variante.setArt132Comma1LettE("N");
			  
			  variante.setArt205Comma1("N");
			  variante.setArt205Comma3("N");
			  variante.setArt205Comma4("N");
			  variante.setArt132Comma3("N");
			  
			  variante.setMotivAltro("N");
			  variante.setNilMotivAltroDesc();
			  
			  List< ? > w9moti = sqlManager.getListVector(SQL_DATI_MOTIVAZIONI, new Object[] {codGara, codLott, numVari});
			  if (w9moti != null && w9moti.size() > 0) {
				  for (int i = 0; i < w9moti.size(); i++) {
					  List< ?> moti = (List< ? >)w9moti.get(i);
					  switch (Integer.parseInt(moti.get(0).toString())) {
					  	case 1:
					  		variante.setArt132Comma1LettA("Y");
					  		break;
					  	case 2:
					  		variante.setArt132Comma1LettB("Y");
					  		break;
					  	case 3:
					  		variante.setArt132Comma1LettC("Y");
					  		break;
					  	case 5:
					  		variante.setArt132Comma1LettE("Y");
					  		break;
					  	case 11:
					  		variante.setArt205Comma1("Y");
					  		break;
					  	case 12:
					  		variante.setArt205Comma3("Y");
					  		break;
					  	case 13:
					  		variante.setArt205Comma4("Y");
					  		break;
					  	case 6:
					  		variante.setArt132Comma3("Y");
					  		break;
					}
			      }
			  }
			  
			  if (w9vari.getString("ALTRE_MOTIVAZIONI") != null) {
				  variante.setMotivAltro("Y");
				  variante.setMotivAltroDesc(w9vari.getString("ALTRE_MOTIVAZIONI"));
			  }
			  
			  if (w9vari.getDouble("IMP_RIDET_LAVORI") != null) {
				  variante.setImpContrRidetLav(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_RIDET_LAVORI"))));
			  } else {
				  variante.setImpContrRidetLav(new BigDecimal(0));
			  }
			  
			  if (w9vari.getDouble("IMP_RIDET_SERVIZI") != null) {
				  variante.setImpContrRidetServ(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_RIDET_SERVIZI"))));
			  } else {
				  variante.setImpContrRidetServ(new BigDecimal(0));
			  }
			  
			  if (w9vari.getDouble("IMP_RIDET_FORNIT") != null) {
				  variante.setImpContrRidetForn(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_RIDET_FORNIT"))));
			  } else {
				  variante.setImpContrRidetForn(new BigDecimal(0));
			  }
			  
			  if (w9vari.getDouble("IMP_SICUREZZA") != null) {
				  variante.setImpSic(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_SICUREZZA"))));
			  }
			  
			  if (w9vari.getDouble("IMP_DISPOSIZIONE") != null) {
				  variante.setImpNonRibAsta(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_DISPOSIZIONE"))));
			  } 
			  
			  if (w9vari.getDouble("IMP_PROGETTAZIONE") != null) {
				  variante.setImpProg(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_PROGETTAZIONE"))));
			  } 
			  
			  if (w9vari.getDouble("IMP_DISPOSIZIONE") != null) {
				  variante.setImpTotaleSpeseADisposizione(new BigDecimal(convertiImporto(w9vari.getDouble("IMP_DISPOSIZIONE"))));
			  } 
			  
			  if (w9vari.getData("DATA_ATTO_AGGIUNTIVO") != null) {
				  date.setTime(w9vari.getData("DATA_ATTO_AGGIUNTIVO"));
				  variante.setDataAttoAggSottomiss(date);
			  } 

			  if (w9vari.getLong("NUM_GIORNI_PROROGA") != null) {
				  variante.setNGgProroga(w9vari.getLong("NUM_GIORNI_PROROGA").intValue());
			  } 
			  	  
		  } catch (SQLException e) {
			  throw new GestoreException("Sitar: Errore nel metodo getSchedaVarianteLav", "getSchedaVarianteLav", e);
		  } 
		  
		  if (logger.isDebugEnabled()) {
				logger.debug("getSchedaVarianteLav: fine metodo");
		  } 
		  
		  return doc;
	  }
	  
	  /**
	   * Fase aggiungi persona fisica a scheda Sitar ver 0.2.
	   * 
	   * @param personaFisica da aggiungere
	   * @param codTec Codice del tecnico 
	   * @param sqlManager SqlManager
	   * @throws GestoreException GestoreException
	   */
	  
	  public void addPersonaFisica(final PeFiAltriIncarichi personaFisica, final String codTec) throws GestoreException {
		  String sqlTECNI = "SELECT CFTEC, NOMETEI, COGTEI, INDTEC, CAPTEC, CITTEC, TELTEC, FAXTEC, EMATEC FROM TECNI WHERE CODTEC = ?";
		  try {
				DataColumnContainer tecni = new DataColumnContainer(sqlManager, "TECNI", sqlTECNI, new Object[] { codTec });
				personaFisica.setCodiceFiscale(tecni.getString("CFTEC"));
				if (verificaEsistenzaValore(tecni.getString("NOMETEI"))) {
					personaFisica.setNome(tecni.getString("NOMETEI"));
				}
				if (verificaEsistenzaValore(tecni.getString("COGTEI"))) {
					personaFisica.setCognome(tecni.getString("COGTEI"));
				}
				if (verificaEsistenzaValore(tecni.getString("INDTEC"))) {
					personaFisica.setIndirizzo(tecni.getString("INDTEC"));
				} else {
					personaFisica.setNilIndirizzo();
				}
				if (verificaEsistenzaValore(tecni.getString("CAPTEC"))) {
					personaFisica.setCap(tecni.getString("CAPTEC"));
				} else {
					personaFisica.setNilCap();
				}
				if (verificaEsistenzaValore(tecni.getString("CITTEC"))) {
					personaFisica.setCodIstatComune(tecni.getString("CITTEC"));
				}
				if (verificaEsistenzaValore(tecni.getString("TELTEC"))) {
					personaFisica.setTelefono(tecni.getString("TELTEC"));
				} else {
					personaFisica.setNilTelefono();
				}
				if (verificaEsistenzaValore(tecni.getString("FAXTEC"))) {
					personaFisica.setFax(tecni.getString("FAXTEC"));
				} else {
					personaFisica.setNilFax();
				}
				if (verificaEsistenzaValore(tecni.getString("EMATEC"))) {
					personaFisica.setEmail(tecni.getString("EMATEC"));
				} else {
					personaFisica.setNilEmail();
				}
			} catch (Exception ex) {
				personaFisica.setNil();
			}
	  }
	  
	  public void addPersonaFisica(final PeFiProgett personaFisica, final String codTec) throws GestoreException {
		  String sqlTECNI = "SELECT CFTEC, NOMETEI, COGTEI, INDTEC, CAPTEC, CITTEC, TELTEC, FAXTEC, EMATEC FROM TECNI WHERE CODTEC = ?";
		  try {
				DataColumnContainer tecni = new DataColumnContainer(sqlManager, "TECNI", sqlTECNI, new Object[] { codTec });
				personaFisica.setCodiceFiscale(tecni.getString("CFTEC"));
				if (verificaEsistenzaValore(tecni.getString("NOMETEI"))) {
					personaFisica.setNome(tecni.getString("NOMETEI"));
				}
				if (verificaEsistenzaValore(tecni.getString("COGTEI"))) {
					personaFisica.setCognome(tecni.getString("COGTEI"));
				}
				
				if (verificaEsistenzaValore(tecni.getString("INDTEC"))) {
					personaFisica.setIndirizzo(tecni.getString("INDTEC"));
				} else {
					personaFisica.setNilIndirizzo();
				}
				if (verificaEsistenzaValore(tecni.getString("CAPTEC"))) {
					personaFisica.setCap(tecni.getString("CAPTEC"));
				} else {
					personaFisica.setNilCap();
				}
				if (verificaEsistenzaValore(tecni.getString("CITTEC"))) {
					personaFisica.setCodIstatComune(tecni.getString("CITTEC"));
				}
				if (verificaEsistenzaValore(tecni.getString("TELTEC"))) {
					personaFisica.setTelefono(tecni.getString("TELTEC"));
				} else {
					personaFisica.setNilTelefono();
				}
				if (verificaEsistenzaValore(tecni.getString("FAXTEC"))) {
					personaFisica.setFax(tecni.getString("FAXTEC"));
				} else {
					personaFisica.setNilFax();
				}
				if (verificaEsistenzaValore(tecni.getString("EMATEC"))) {
					personaFisica.setEmail(tecni.getString("EMATEC"));
				} else {
					personaFisica.setNilEmail();
				}
			} catch (Exception ex) {
				personaFisica.setNil();
			}
	  }
	  
	  public void addPersonaFisica(final PeFiIncarichiIEC personaFisica, final String codTec) throws GestoreException {
		  String sqlTECNI = "SELECT CFTEC, NOMETEI, COGTEI, INDTEC, CAPTEC, CITTEC, TELTEC, FAXTEC, EMATEC FROM TECNI WHERE CODTEC = ?";
		  try {
				DataColumnContainer tecni = new DataColumnContainer(sqlManager, "TECNI", sqlTECNI, new Object[] { codTec });
				personaFisica.setCodiceFiscale(tecni.getString("CFTEC"));
				if (verificaEsistenzaValore(tecni.getString("NOMETEI"))) {
					personaFisica.setNome(tecni.getString("NOMETEI"));
				}
				if (verificaEsistenzaValore(tecni.getString("COGTEI"))) {
					personaFisica.setCognome(tecni.getString("COGTEI"));
				}
				
				if (verificaEsistenzaValore(tecni.getString("INDTEC"))) {
					personaFisica.setIndirizzo(tecni.getString("INDTEC"));
				} else {
					personaFisica.setNilIndirizzo();
				}
				if (verificaEsistenzaValore(tecni.getString("CAPTEC"))) {
					personaFisica.setCap(tecni.getString("CAPTEC"));
				} else {
					personaFisica.setNilCap();
				}
				if (verificaEsistenzaValore(tecni.getString("CITTEC"))) {
					personaFisica.setCodIstatComune(tecni.getString("CITTEC"));
				}
				if (verificaEsistenzaValore(tecni.getString("TELTEC"))) {
					personaFisica.setTelefono(tecni.getString("TELTEC"));
				} else {
					personaFisica.setNilTelefono();
				}
				if (verificaEsistenzaValore(tecni.getString("FAXTEC"))) {
					personaFisica.setFax(tecni.getString("FAXTEC"));
				} else {
					personaFisica.setNilFax();
				}
				if (verificaEsistenzaValore(tecni.getString("EMATEC"))) {
					personaFisica.setEmail(tecni.getString("EMATEC"));
				} else {
					personaFisica.setNilEmail();
				}
			} catch (Exception ex) {
				personaFisica.setNil();
			}
	  }
	  
	  public void addPersonaFisica(final PeFiIncarichiCollaudo personaFisica, final String codTec) throws GestoreException {
		  String sqlTECNI = "SELECT CFTEC, NOMETEI, COGTEI, INDTEC, CAPTEC, CITTEC, TELTEC, FAXTEC, EMATEC FROM TECNI WHERE CODTEC = ?";
		  try {
				DataColumnContainer tecni = new DataColumnContainer(sqlManager, "TECNI", sqlTECNI, new Object[] { codTec });
				personaFisica.setCodiceFiscale(tecni.getString("CFTEC"));
				if (verificaEsistenzaValore(tecni.getString("NOMETEI"))) {
					personaFisica.setNome(tecni.getString("NOMETEI"));
				}
				if (verificaEsistenzaValore(tecni.getString("COGTEI"))) {
					personaFisica.setCognome(tecni.getString("COGTEI"));
				}
				
				if (verificaEsistenzaValore(tecni.getString("INDTEC"))) {
					personaFisica.setIndirizzo(tecni.getString("INDTEC"));
				} else {
					personaFisica.setNilIndirizzo();
				}
				if (verificaEsistenzaValore(tecni.getString("CAPTEC"))) {
					personaFisica.setCap(tecni.getString("CAPTEC"));
				} else {
					personaFisica.setNilCap();
				}
				if (verificaEsistenzaValore(tecni.getString("CITTEC"))) {
					personaFisica.setCodIstatComune(tecni.getString("CITTEC"));
				}
				if (verificaEsistenzaValore(tecni.getString("TELTEC"))) {
					personaFisica.setTelefono(tecni.getString("TELTEC"));
				} else {
					personaFisica.setNilTelefono();
				}
				if (verificaEsistenzaValore(tecni.getString("FAXTEC"))) {
					personaFisica.setFax(tecni.getString("FAXTEC"));
				} else {
					personaFisica.setNilFax();
				}
				if (verificaEsistenzaValore(tecni.getString("EMATEC"))) {
					personaFisica.setEmail(tecni.getString("EMATEC"));
				} else {
					personaFisica.setNilEmail();
				}
			} catch (Exception ex) {
				personaFisica.setNil();
			}
	  }
	  
	  /**
		 * Set posizioni.
		 * 
		 * @param datiInizio
		 *            DatiInizioType
		 * @param codgara
		 *            codice gara
		 * @param codlott
		 *            codice lotto
		 * @throws GestoreException
		 *             GestoreException
		 */
		private void setPosizioni(InizioEsecuzioneContratto datiInizio, Long codgara, Long codlott) throws GestoreException {

			if (logger.isDebugEnabled())
				logger.debug("setPosizioni: inizio metodo");

			String sqlIMPR = "SELECT CFIMP, PIVIMP, NOMEST, INDIMP, CAPIMP, CODCIT, PROIMP, NAZIMP FROM IMPR WHERE CODIMP = ?";
			try {
				List<?> datiW9AGGI = this.sqlManager.getListVector(SQL_DATI_POSIZIONE_AGGI, new Object[] { codgara, codlott });

				if (datiW9AGGI != null && datiW9AGGI.size() > 0) {
					ListIterator<?> iterator = datiW9AGGI.listIterator();

					PosContrImprese posContr = datiInizio.addNewPosContrImprese();
					while (iterator.hasNext()) {

						List<?> riga = (List<?>) iterator.next();
						PeGiIEC posizione = posContr.addNewPersonaGiuridica();
						DataColumnContainer impr = new DataColumnContainer(sqlManager, "IMPR", sqlIMPR, new Object[] { SqlManager.getValueFromVectorParam(riga, 0).getStringValue() });
						
						if (verificaEsistenzaValore(impr.getString("CFIMP"))) {
							posizione.setCodiceFiscale(impr.getString("CFIMP"));
						}
						
						if (verificaEsistenzaValore(impr.getString("PIVIMP"))) {
							posizione.setPartitaIva(impr.getString("PIVIMP"));
						}
						
						if (verificaEsistenzaValore(impr.getString("NOMEST"))) {
							posizione.setRagioneSociale(impr.getString("NOMEST"));
						} else {
							posizione.setRagioneSociale("-");
						}
						
						if (verificaEsistenzaValore(impr.getString("INDIMP"))) {
							posizione.setIndirizzo(impr.getString("INDIMP"));
						} else {
							posizione.setNilIndirizzo();
						}
						
						if (verificaEsistenzaValore(impr.getString("CAPIMP"))) {
							posizione.setCap(impr.getString("CAPIMP"));
						}
						
						if (verificaEsistenzaValore(impr.getString("CODCIT"))) {
							posizione.setCodIstatComune(impr.getString("CODCIT"));
						}
						
						if (verificaEsistenzaValore(impr.getString("PROIMP"))) {
							posizione.setProvincia(impr.getString("PROIMP"));
						}
						
						if (verificaEsistenzaValore(impr.getLong("NAZIMP"))) {
							posizione.setCodiceISO(getStatoEstero(impr.getLong("NAZIMP").toString()));	
						} else {
							posizione.setCodiceISO("IT");
						}
						
						if (verificaEsistenzaValore(SqlManager.getValueFromVectorParam(riga, 1))) {
							posizione.setRuolo(Integer.parseInt(SqlManager.getValueFromVectorParam(riga, 1).getStringValue()));
						} else {
							posizione.setRuolo(3);
						}
						
						if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
							posizione.setCodiceINPS(SqlManager.getValueFromVectorParam(riga, 2).getStringValue());
							posizione.setINPSSino("Y");
						} else {
							posizione.setINPSSino("N");
						}
						
						if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
							posizione.setCodiceINAIL(SqlManager.getValueFromVectorParam(riga, 3).getStringValue());
							posizione.setINAILSino("Y");
						} else {
							posizione.setINAILSino("N");
						}
						
						if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
							posizione.setCodiceCE(SqlManager.getValueFromVectorParam(riga, 4).getStringValue());
							posizione.setCESino("Y");
						} else {
							posizione.setCESino("N");
						}
						
					}
				}
			} catch (SQLException e) {

				throw new GestoreException("Errore nella lettura dei dati delle posizioni contributive / assicurative", "PosizioneType", e);
			}

			if (logger.isDebugEnabled())
				logger.debug("setPosizioni: fine metodo");

		}
		
		
		
		  
	  /**
	   * Ritorna il codice della categoria usato in Sitar a partire dal codice usato da Sitat.
	   * 
	   * @param sqlManager
	   * @param categoria
	   * @param valore sitar
	   * @return Ritorna il codice della categoria usato in Sitar a partire dal codice usato da Sitat.
	   * @throws SQLException
	   */
	  	public String codificaSitarToSitat(final String categoria, final String valore) throws SQLException {
	  		return (String) sqlManager.getObject("select CODSITAT from W9CODICI_SITAR where TABCOD=? and CODSITAR=?", 
	  				new Object[] { categoria, valore } );
	  	}
	  
	   /**
	    * Ritorna il codice della categoria usato in Sitat a partire dal codice usato da Sitar.
	    * 
	    * @param sqlManager
	    * @param categoria
	    * @param valore sitat
	    * @return Ritorna il codice della categoria usato in Sitat a partire dal codice usato da Sitar.
	    * @throws SQLException
	    */
	    public String codificaSitatToSitar(final String categoria, final String valore) throws SQLException {
	    	return (String) sqlManager.getObject("select CODSITAR from W9CODICI_SITAR where TABCOD=? and CODSITAT=?", 
	    			new Object[] { categoria, valore } );
	    }
	    	
	    
	  /**
	   * Utility per il controllo dei valori in arrivo.
	   * 
	   * @param obj Object
	   * @return Ritorna true se obj e' diversa da null, false altrimenti
	   */
	  private boolean verificaEsistenzaValore(final Object obj) {
	    boolean esistenza;
	    if (obj != null && !obj.toString().trim().equals("")) {
	      esistenza = true;
	    } else {
	      esistenza = false;
	    }
	    return esistenza;
	  }
	  

	
	

	/**
	 * Conversione di un importo da Double a Stringa.
	 * 
	 * @param importo
	 *            Importo
	 * @return Ritorna l'importo convertito in stringa
	 */
	private Double convertiImporto(Double importo) {
		if (importo != null) {
			return importo;
		}
		return 0.0;
	}
	
	public HashMap<String, Object> validate(XmlObject trasferimentoDati, Long fase) throws JspException {
		HashMap<String, Object> infoValidazione = new HashMap<String, Object>();

		try {
			String titolo = null;
			List<Object> listaControlli = new Vector<Object>();

			switch (fase.intValue()) {
				case CostantiW9.ANAGRAFICA_GARA_LOTTI:
					titolo = "Scheda bando";
					if (trasferimentoDati.getClass().getSimpleName().equals("SchedaBandoLavoriDocumentImpl")) {
						this.validazioneBandoLavori(trasferimentoDati, listaControlli);
					} else {
						this.validazioneBandoServForn(trasferimentoDati, listaControlli);
					}
					break;
				case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
					titolo = "Fase Aggiudicazione";
					if (trasferimentoDati.getClass().getSimpleName().equals("SchedaAggLavoriSezOssDocumentImpl")) {
						this.validazioneAggiudicazioneLavori(trasferimentoDati, listaControlli);
					} else {
						this.validazioneAggiudicazioneServForn(trasferimentoDati, listaControlli);
					}
					break;
				case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
					titolo = "Fase Aggiudicazione sotto soglia";
					this.validazioneAggiudicazioneSemplificata(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.ADESIONE_ACCORDO_QUADRO:
					titolo = "Fase Adesione";
					if (trasferimentoDati.getClass().getSimpleName().equals("SchedaAggLavoriSezOssAdesioneDocumentImpl")) {
						this.validazioneAdesioneLavori(trasferimentoDati, listaControlli);
					} else {
						this.validazioneAdesioneServForn(trasferimentoDati, listaControlli);
					}
					break;
				case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
					titolo = "Fase Esecuzione e Avanzamento";
					this.validazioneEsecuzioneAvanzamento(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
					titolo = "Fase Inizio";
					this.validazioneInizio(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
					titolo = "Fase Conclusione";
					if (trasferimentoDati.getClass().getSimpleName().equals("SchedaChiusuraAnticipataContrattoDocumentImpl")) {
						this.validazioneChiusuraAnticipataContratto(trasferimentoDati, listaControlli);
					} else {
						this.validazioneConclusione(trasferimentoDati, listaControlli);
					}
					break;
				case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
					titolo = "Fase Conclusione";
					if (trasferimentoDati.getClass().getSimpleName().equals("SchedaChiusuraAnticipataContrattoDocumentImpl")) {
						this.validazioneChiusuraAnticipataContratto(trasferimentoDati, listaControlli);
					} else {
						this.validazioneConclusioneSemplificata(trasferimentoDati, listaControlli);
					}
					break;
				case CostantiW9.COLLAUDO_CONTRATTO:
					titolo = "Fase Collaudo";
					this.validazioneCollaudo(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.SOSPENSIONE_CONTRATTO:
					titolo = "Evento Sospensione";
					this.validazioneSospensione(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.VARIANTE_CONTRATTO:
					titolo = "Evento Variante";
					if (trasferimentoDati.getClass().getSimpleName().equals("SchedaVarianteLavDocumentImpl")) {
						this.validazioneVarianteLav(trasferimentoDati, listaControlli);
					} else {
						this.validazioneVarianteServForn(trasferimentoDati, listaControlli);
					}
					break;	
				case CostantiW9.ACCORDO_BONARIO:
					titolo = "Evento Accordo Bonario";
					this.validazioneAccordoBonario(trasferimentoDati, listaControlli);
					break;	
				case CostantiW9.SUBAPPALTO:
					titolo = "Evento Subappalto";
					this.validazioneSubappalto(trasferimentoDati, listaControlli);
					break;
			}

			infoValidazione.put("titolo", titolo);
			infoValidazione.put("listaControlli", listaControlli);

			// Controllo errori e warning
			int numeroErrori = 0;
			int numeroWarning = 0;

			if (!listaControlli.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug("Messaggi di errore durante la validazione del flusso con codice = " + fase.intValue());
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
		} catch (Exception e) {
			throw new JspException("Errore nella funzione di controllo dei dati", e);
		}
		return infoValidazione;
	}
	
	private void validazioneBandoLavori(XmlObject datiAggiudicazione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneBandoLavori: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaBandoLavoriDocument docBando = (SchedaBandoLavoriDocument) datiAggiudicazione;
			BandoLavori bando = docBando.getSchedaBandoLavori().getBandoLavori();
			nomeTabella = "W9GARA";
			pagina = "Dati generali della gara";
			
			if (bando.getAppartenenteA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ENTE_SPECIALE", pagina);
			}
			
			pagina = "Bando gara";
			if (bando.getDataScadenza() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DSCADE", pagina);
			}
			
			nomeTabella = "W9APPALAV";
			pagina = "Dati generali del lotto - Modalità tipologia lavoro";
			
			if (bando.getTipologiaIntervento() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_APPALTO", pagina);
			}
			
			nomeTabella = "W9LOTT";
			pagina = "Dati generali del lotto";
			
			if (bando.getCig() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CIG", pagina);
			}
			
			if (bando.getProcedGaraId() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_SCELTA_CONTRAENTE", pagina);
			}
			
			pagina = "Dati generali lotto - Dati economici";
			
			if (bando.getCatPrevId() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CATEGORIA_PREVALENTE", pagina);
			}
			if (bando.getImpCatPrevId() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CLASCAT", pagina);
			}
			
			pagina = "Dati generali del lotto - Luogo di esevuzione";
			if (bando.getCodIstatLocEsec() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "LUOGO_ISTAT", pagina);
			}

		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione del bando lavori"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneBandoLavori)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneBandoLavori: fine metodo");
		}
	}
	
	private void validazioneBandoServForn(XmlObject datiAggiudicazione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneBandoServForn: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaBandoServFornDocument docBando = (SchedaBandoServFornDocument) datiAggiudicazione;
			BandoServiziForniture bando = docBando.getSchedaBandoServForn().getBandoServiziForniture();
			
			nomeTabella = "W9GARA";
			pagina = "Bando gara";
			
			if (bando.getDataScadenza() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DSCADE", pagina);
			}
			
			nomeTabella = "W9LOTT";
			pagina = "Dati generali del lotto";
			
			if (bando.getCig() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CIG", pagina);
			}
			
			if (bando.getProcedGaraId() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_SCELTA_CONTRAENTE", pagina);
			}
			
			pagina = "Dati generali lotto - Dati economici";
			if (bando.getTipologiaCpv() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CPV", pagina);
			}
			
			pagina = "Dati generali del lotto - Luogo di esevuzione";
			if (bando.getCodIstatLocEsec() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "LUOGO_ISTAT", pagina);
			}

			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione del bando Servizi Forniture"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneBandoServForn)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneBandoServForn: fine metodo");
		}
	}
	
	/**
	 * Controllo dei dati per la fase di aggiudicazione per appalti di importo
	 * superiore a 150.000 euro (codice 1).
	 * 
	 * @param codgara
	 * @param codlott
	 * @param num
	 * @param listaControlli
	 * @throws GestoreException
	 */
	private void validazioneAggiudicazioneLavori(XmlObject datiAggiudicazione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazioneLavori: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaAggLavoriSezOssDocument docAggiudicazione = (SchedaAggLavoriSezOssDocument) datiAggiudicazione;
			AggiudicazioneLavoriSezioneOsservatorio aggiudicazione = docAggiudicazione.getSchedaAggLavoriSezOss().getAggiudicazioneLavoriSezioneOsservatorio();
			nomeTabella = "W9LOTT";
			pagina = "Dati generali lotto - Dati economici";
			if (aggiudicazione.getCatPrevId() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CATEGORIA_PREVALENTE", pagina);
			}
			if (aggiudicazione.getImpCatPrevId() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CLASCAT", pagina);
			}
			if (aggiudicazione.getIdPrestazComprese() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPO_PRESTAZIONE", pagina);
			}
			nomeTabella = "W9APPA";
			pagina = "Dati aggiudicazione - Dati economici dell'appalto";
			if (aggiudicazione.getStrumProgId() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "COD_STRUMENTO", pagina);
			}
			
			nomeTabella = "TECNI";
			pagina = "Fase di aggiudicazione/affidamento - Incarichi professionali";
			for(PeFiAltriIncarichi persona:aggiudicazione.getAltriIncarichi().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
			for(PeFiProgett persona:aggiudicazione.getIncarichiProgettazione().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di aggiudicazione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAggiudicazioneLavori)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazioneLavori: fine metodo");
		}
	}
	
	private void validazioneAggiudicazioneServForn(XmlObject datiAggiudicazione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazioneServForn: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaAggServFornSezOssDocument docAggiudicazione = (SchedaAggServFornSezOssDocument) datiAggiudicazione;
			AggiudicazioneServFornSezioneOsservatorio aggiudicazione = docAggiudicazione.getSchedaAggServFornSezOss().getAggiudicazioneServFornSezioneOsservatorio();
			nomeTabella = "W9LOTT";
			pagina = "Dati generali lotto - Dati economici";
			if (aggiudicazione.getCatPrevId() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CATEGORIA_PREVALENTE", pagina);
			}
			if (aggiudicazione.getIdPrestazComprese() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPO_PRESTAZIONE", pagina);
			}
			
			nomeTabella = "TECNI";
			pagina = "Fase di aggiudicazione/affidamento - Incarichi professionali";
			for(PeFiAltriIncarichi persona:aggiudicazione.getAltriIncarichi().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
			for(PeFiProgett persona:aggiudicazione.getIncarichiProgettazione().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di aggiudicazione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAggiudicazioneServForn)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazioneServForn: fine metodo");
		}
	}
	
	private void validazioneAggiudicazioneSemplificata(XmlObject datiAggiudicazione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazioneSemplificata: inizio metodo");
		} 

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaAggiudicazioneSezOssRidDocument docAggiudicazione = (SchedaAggiudicazioneSezOssRidDocument) datiAggiudicazione;
			AggiudicazioneSezioneOsservatorioRidotta aggiudicazione = docAggiudicazione.getSchedaAggiudicazioneSezOssRid().getAggiudicazioneSezioneOsservatorioRidotta();
			
			nomeTabella = "W9APPA";
			pagina = "Fase semplificata di aggiudicazione/affidamento - Dati adesione - Dati economici dell'appalto";
			
			if (aggiudicazione.getDataStipulaContratto() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_STIPULA", pagina);
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase Aggiudicazione Sotto Soglia/ Contratto Escluso"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAggiudicazioneSemplificata)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazioneSemplificata: fine metodo");
		}
	}
	
	private void validazioneInizio(XmlObject datiInizio, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneInizio: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaIecDocument docInizio = (SchedaIecDocument) datiInizio;
			InizioEsecuzioneContratto inizio = docInizio.getSchedaIec().getInizioEsecuzioneContratto();
			
			nomeTabella = "W9INIZ";
			pagina = "Fase iniziale di esecuzione del contratto - Dati generali - Termine di esecuzione";
			
			if (inizio.getDataVerbConsegnaOAvvioIec() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERBALE_DEF", pagina);
			}
			
			if (inizio.getDataInizioLavori() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_INIZIO", pagina);
			}
			
			if (inizio.getDataFineLavori() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_TERMINE", pagina);
			}
			
			pagina = "Fase di aggiudicazione - Imprese aggiudicatarie / affidatarie";
			
			if (inizio.getPosContrImprese() == null) {
				listaControlli.add(((new Object[] { "E", pagina, "Impresa", "Indicare almeno un'impresa aggiudicatrice nella scheda aggiudicazione" })));
			} else {
				nomeTabella = "IMPR";
				pagina = "Scheda impresa";
				for(PeGiIEC impresa:inizio.getPosContrImprese().getPersonaGiuridicaArray()) {
					if (impresa.getCodiceFiscale() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CFIMP", pagina);
					}
					if (impresa.getPartitaIva() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "PIVIMP", pagina);
					}
					if (impresa.getRagioneSociale() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMEST", pagina);
					}
					if (impresa.getCodIstatComune() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CODCIT", pagina);
					}
					if (impresa.getProvincia() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "PROIMP", pagina);
					}
				}
			}
			
			nomeTabella = "TECNI";
			pagina = "Fase iniziale di esecuzione del contratto - Incarichi professionali";
			for(PeFiIncarichiIEC persona:inizio.getIncarichiIEC().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di inizio"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneInizio)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneInizio: fine metodo");
		}
	}
	
	
	private void validazioneConclusione(XmlObject datiConclusione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneConclusione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaConclusioneContrattoDocument docConclusione = (SchedaConclusioneContrattoDocument)datiConclusione;
			ConclusioneContratto conclusione = docConclusione.getSchedaConclusioneContratto().getConclusioneContratto();
			
			nomeTabella = "W9CONC";
			pagina = "Fase di conclusione del contratto - Ultimazione lavori";
			
			if (conclusione.getDataUltimazLav() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_ULTIMAZIONE", pagina);
			} 
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di conclusione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneConclusione)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneConclusione: fine metodo");
		}
	}
	
	private void validazioneConclusioneSemplificata(XmlObject datiConclusione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneConclusioneSemplificata: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaUltimazLavoriSottosogliaDocument docConclusione = (SchedaUltimazLavoriSottosogliaDocument)datiConclusione;
			UltimazioneLavoriSott conclusione = docConclusione.getSchedaUltimazLavoriSottosoglia().getUltimazioneLavoriSott();
			
			nomeTabella = "W9CONC";
			pagina = "Fase di conclusione del contratto - Ultimazione lavori";
			
			if (conclusione.getDataCertUltimLav() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_ULTIMAZIONE", pagina);
			} 
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di conclusione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneConclusioneSemplificata)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneConclusioneSemplificata: fine metodo");
		}
	}
	
	private void validazioneChiusuraAnticipataContratto(XmlObject datiConclusione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneChiusuraAnticipataContratto: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaChiusuraAnticipataContrattoDocument docConclusione = (SchedaChiusuraAnticipataContrattoDocument)datiConclusione;
			ChiusuraAnticipataContratto chiusura = docConclusione.getSchedaChiusuraAnticipataContratto().getChiusuraAnticipataContratto();
			
			nomeTabella = "W9CONC";
			pagina = "Fase di conclusione del contratto - Interruzione anticipata del procedimento";

			if (chiusura.getTipoChiusura() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MOTIVO_INTERR", pagina);
			} 

		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di conclusione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneChiusuraAnticipataContratto)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneChiusuraAnticipataContratto: fine metodo");
		}
	}
	
	private void validazioneCollaudo(XmlObject datiCollaudo, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneCollaudo: inizio metodo");
		}
		
		String nomeTabella = "";
		String pagina = "";
		try {
			SchedaCollaudoDocument docCollaudo = (SchedaCollaudoDocument)datiCollaudo;
			Collaudo collaudo = docCollaudo.getSchedaCollaudo().getCollaudo();
			
			nomeTabella = "W9COLL";
			pagina = "Fase di collaudo - Dati generali - Collaudo/verifica di conformità delle prestazioni eseguite";
			
			if (collaudo.getDataCertRegEsec() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_REGOLARE_ESEC", pagina);
			}
			
			if (collaudo.getEsitoColl() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ESITO_COLLAUDO", pagina);
			}
			  
			nomeTabella = "TECNI";
			pagina = "Fase di collaudo - Incarichi professionali";
			for(PeFiIncarichiCollaudo persona:collaudo.getIncarichiCollaudo().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di collaudo"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneCollaudo)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneCollaudo: fine metodo");
		}
	}
	
	private void validazioneAdesioneLavori(XmlObject datiAdesione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAdesione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaAggLavoriSezOssAdesioneDocument docAdesione = (SchedaAggLavoriSezOssAdesioneDocument)datiAdesione;
			SchedaAggLavoriSezOssAdesione.AggiudicazioneLavoriSezioneOsservatorio adesione = docAdesione.getSchedaAggLavoriSezOssAdesione().getAggiudicazioneLavoriSezioneOsservatorio();
			
			nomeTabella = "W9APPA";
			pagina = "Fase di adesione accordo quadro - Dati adesione - Dati economici dell'adesione";
			
			if (adesione.getStrumProgId() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "COD_STRUMENTO", pagina);
			}
			
			nomeTabella = "TECNI";
			pagina = "Fase di adesione accordo quadro - Incarichi professionali";
			for(PeFiAltriIncarichi persona:adesione.getAltriIncarichi().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
			for(PeFiProgett persona:adesione.getIncarichiProgettazione().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di adesione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAdesione)", e);
		}


		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAdesione: fine metodo");
		}
	}
	
	private void validazioneAdesioneServForn(XmlObject datiAdesione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAdesione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaAggServFornSezOssAdesioneDocument docAdesione = (SchedaAggServFornSezOssAdesioneDocument)datiAdesione;
			SchedaAggServFornSezOssAdesione.AggiudicazioneServFornSezioneOsservatorioAdesione adesione = docAdesione.getSchedaAggServFornSezOssAdesione().getAggiudicazioneServFornSezioneOsservatorioAdesione();
			
			nomeTabella = "TECNI";
			pagina = "Fase di adesione accordo quadro - Incarichi professionali";
			for(PeFiAltriIncarichi persona:adesione.getAltriIncarichi().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
			for(PeFiProgett persona:adesione.getIncarichiProgettazione().getPersonaFisicaArray()) {
				if (persona.getNome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMETEI", pagina);
				}
				if (persona.getCognome() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COGTEI", pagina);
				}
				if (persona.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CITTEC", pagina);
				}
			}
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di adesione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAdesione)", e);
		}


		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAdesione: fine metodo");
		}
	}
	
	private void validazioneEsecuzioneAvanzamento(XmlObject datiAvanzamento, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneEsecuzioneAvanzamento: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";
		
		try {
			SchedaSALDocument docEsecuzione = (SchedaSALDocument)datiAvanzamento;
			SchedaSAL.SAL esecuzione = docEsecuzione.getSchedaSAL().getSAL();
			
			nomeTabella = "W9AVAN";
			pagina = "Fase di esecuzione ed avanzamento del contratto - Stato di avanzamento";
			
			if (esecuzione.getDataStatoAv() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_RAGGIUNGIMENTO", pagina);
			}
			if (esecuzione.getDataEmissCertPag() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_CERTIFICATO", pagina);
			}
			if (esecuzione.getAvanzRaggiunto() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RITARDO", pagina);
			}
			  
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di esecuzione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneEsecuzioneAvanzamento)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneEsecuzioneAvanzamento: fine metodo");
		}
	}
	
	private void validazioneSospensione(XmlObject datiSospensione, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneSospensione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaSospensioneDocument docSospensione = (SchedaSospensioneDocument)datiSospensione;
			Sospensione sospensione = docSospensione.getSchedaSospensione().getSospensione();
			
			nomeTabella = "W9SOSP";
			pagina = "Sospensione dell'esecuzione";
			
			if (sospensione.getDataVerbSosp() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_SOSP", pagina);
			} 
			
			if (sospensione.getSupQuartoTempContr() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_SUPERO_TEMPO", pagina);
			} 
			
			if (sospensione.getIscrRisApp() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RISERVE", pagina);
			} 
			
			if (sospensione.getVerbNonSottoscr() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_VERBALE", pagina);
			} 
			
			if (sospensione.getMotivForzaMagg().equals("N") && 
					sospensione.getMotivNatTecn().equals("N") &&
					sospensione.getMotivNatAmm().equals("N") &&
					sospensione.getMotivVarInEsec().equals("N") &&
					sospensione.getMotivAutGiud().equals("N") &&
					sospensione.getMotivPubblInt().equals("N") &&
					sospensione.getMotivCondClim().equals("N")) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MOTIVO_SOSP", pagina);
			}
			  
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di sospensione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneSospensione)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneSospensione: fine metodo");
		}
	}
	
	private void validazioneVarianteLav(XmlObject datiVariante, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneVarianteLav: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaVarianteLavDocument docVariante = (SchedaVarianteLavDocument)datiVariante;
			SchedaVarianteLav.Variante variante = docVariante.getSchedaVarianteLav().getVariante();
			
			nomeTabella = "W9VARI";
			
			pagina = "Variante";
			
			if (variante.getDataAppr() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_APPR", pagina);
			}
			
			pagina = "Variante - Quadro economico della variante";
			
			if (variante.getImpTotaleSpeseADisposizione() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_DISPOSIZIONE", pagina);
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione dell'evento variante"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneVarianteLav)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneVarianteLav: fine metodo");
		}
	}
	
	private void validazioneVarianteServForn(XmlObject datiVariante, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneVarianteServForn: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaVarianteServFornDocument docVariante = (SchedaVarianteServFornDocument)datiVariante;
			SchedaVarianteServForn.Variante variante = docVariante.getSchedaVarianteServForn().getVariante();
			
			nomeTabella = "W9VARI";
			
			pagina = "Variante";
			
			if (variante.getDataAppr() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_APPR", pagina);
			}
			
			pagina = "Variante - Quadro economico della variante";
			
			if (variante.getImpTotaleSpeseADisposizione() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_DISPOSIZIONE", pagina);
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione dell'evento variante"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneVarianteServForn)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneVarianteServForn: fine metodo");
		}
	}
	
	private void validazioneAccordoBonario(XmlObject datiAccordo, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAccordoBonario: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaAccordoBonarioDocument docAccordo = (SchedaAccordoBonarioDocument) datiAccordo;
			AccordoBonario accordo = docAccordo.getSchedaAccordoBonario().getAccordoBonario();
			
			nomeTabella = "W9ACCO";
			pagina = "Accordo bonario";
			
			if (accordo.getDataAccBon() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_ACCORDO", pagina);
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione dell'evento Accordo Bonario"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAccordoBonario)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAccordoBonario: fine metodo");
		}
	}
	
	private void validazioneSubappalto(XmlObject datiSubappalto, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneSubappalto: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			SchedaSubappaltoDocument docSubappalto = (SchedaSubappaltoDocument)datiSubappalto;
			Subappalto subappalto = docSubappalto.getSchedaSubappalto().getSubappalto();
			
			nomeTabella = "W9SUBA";
			pagina = "Subappalto";
			
			if (subappalto.getDataAutorizz() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_AUTORIZZAZIONE", pagina);
			}
			if (subappalto.getIdCategoria() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CATEGORIA", pagina);
			}
			if (subappalto.getCpv() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CPV", pagina);
			}
			if (subappalto.getImpreseSubappalto().getPersonaGiuridicaArray().length == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CODIMP_AGGIUDICATRICE", pagina);
			}
			nomeTabella = "IMPR";
			pagina = "Scheda impresa subappaltatrice";
			for(PeGiSub impresa:subappalto.getImpreseSubappalto().getPersonaGiuridicaArray()) {
				if (impresa.getCodiceFiscale() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CFIMP", pagina);
				}
				if (impresa.getCodIstatComune() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CODCIT", pagina);
				}
				if (impresa.getProvincia() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "PROIMP", pagina);
				}
			}
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione dell'evento Subappalto"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneSubappalto)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneSubappalto: fine metodo");
		}
	}
	
	
	/**
	 * Aggiunge un messaggio bloccante alla listaControlli.
	 * 
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param nomeTabella
	 *            Nome della tabella
	 * @param nomeCampo
	 *            Nome del campo
	 * @param pagina
	 *            Pagina
	 */
	private void addCampoObbligatorio(List<Object> listaControlli, final String nomeTabella, final String nomeCampo, final String pagina) {
		if (logger.isDebugEnabled()) {
			logger.debug("addCampoObbligatorio: inizio metodo");
		}

		listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, nomeCampo), "Il campo &egrave; obbligatorio" })));

		if (logger.isDebugEnabled()) {
			logger.debug("addCampoObbligatorio: fine metodo");
		}
	}
	
	/**
	 * Restituisce la descrizione WEB del campo.
	 * 
	 * @param nomeTabella
	 *            Nome della tabella
	 * @param nomeCampo
	 *            Nome del campo
	 * @return Ritorna la descrizione del campo a partire da tabella e campo
	 */
	private String getDescrizioneCampo(final String nomeTabella, final String nomeCampo) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDescrizioneCampo: inizio metodo");
		}
		String descrizione = "";
		Campo c = DizionarioCampi.getInstance().getCampoByNomeFisico(nomeTabella + "." + nomeCampo);
		if (c != null) {
			descrizione = c.getDescrizioneWEB();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDescrizioneCampo: fine metodo");
		}
		return descrizione;
	}
	
	private String getStatoEstero(final String codiceNazione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("getStatoEstero: inizio metodo");
		}

		String statoEstero = "IT";
		try {
			statoEstero = (String) sqlManager.getObject("select tab2tip from tab2 where tab2d1 = ? and tab2cod = 'W3z12'", new Object[] { codiceNazione });
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura della nazione dell'impresa", "getStatoEstero", e);
		}
		if (statoEstero == null) {
			statoEstero = "IT";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getStatoEstero: fine metodo");
		}

		return statoEstero;
	}
	
	public HashMap<String, Object> aggiornaCUI(final FeedBackDocument doc) throws GestoreException {
		boolean result = false;
		TransactionStatus transazione = null;
		HashMap<String, Object> infoValidazione = new HashMap<String, Object>();

	    try {
	    	List<Object> listaControlli = new Vector<Object>();
	    	ArrayList<Object> validationErrors = new ArrayList<Object>();
	        XmlOptions validationOptions = new XmlOptions();
	        validationOptions.setErrorListener(validationErrors);
	        boolean isValid = doc.validate(validationOptions);

	        if (!isValid) {
	          Iterator<?> iter = validationErrors.iterator();
	          while (iter.hasNext()) {
	        	  String messaggioErrore = (String)iter.next();
	        	  listaControlli.add(((new Object[] { "E", "Osservatorio Contratti Pubblici", "Report", messaggioErrore })));
	          }
	        } else {
	    		FlussoType infoFlusso = doc.getFeedBack().getInfoFlusso();
	    		if (infoFlusso.getNUMERRORE() == 0) {
	    			transazione = this.sqlManager.startTransaction();
	    			//inserimento avvenuto con successo
	    			sqlManager.update(SQL_UPDATE_CUI, new Object[] {doc.getFeedBack().getEsitoArray()[0].getCODICEUNICOINTERVENTO(), doc.getFeedBack().getEsitoArray()[0].getCIG()});
	    			result = true;
	    		} else {
	    			for(AnomaliaType anomalia:doc.getFeedBack().getEsitoArray(0).getAnomaliaArray()) {
	    				String messaggioAnomalia = anomalia.getDESCRIZIONE();
	    				if (messaggioAnomalia.startsWith("Il professionista con codice fiscale")) {
	    					messaggioAnomalia += ". Collegarsi al sistema online per l'inserimento dell'anagrafica del professionista.";
	  	        	  	} else if (messaggioAnomalia.startsWith("Impresa con codice fiscale")) {
	  	        	  	messaggioAnomalia += ". Collegarsi al sistema online per l'inserimento dell'anagrafica dell'impresa.";
	  	        	  	}
	    				listaControlli.add(((new Object[] { "E", "Osservatorio Contratti Pubblici", "Report", messaggioAnomalia })));
					}
	    		}
	    	}
	        infoValidazione.put("titolo", "Esito importazione XML");
			infoValidazione.put("listaControlli", listaControlli);
			infoValidazione.put("numeroErrori", listaControlli.size());
			infoValidazione.put("numeroWarning", 0);
			
	    } catch (SQLException e) {
	    	throw new GestoreException("Non e' possibile importare il file XML allegato", "importXML.generic");
	    } 
	    finally {
			  if (transazione != null) {
		          try {
		        	  if (result) {
		        		  sqlManager.commitTransaction(transazione);
		        	  } else {
		        		  sqlManager.rollbackTransaction(transazione);
		        	  }
		          } catch (SQLException e1) {
		          }
		      }
		  }
	    return infoValidazione;
	}

}

