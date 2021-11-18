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

import it.eldasoft.contratti.rlo.xmlbeans.ACCORDOBONARIOType;
import it.eldasoft.contratti.rlo.xmlbeans.ACQUISIZIONEType;
import it.eldasoft.contratti.rlo.xmlbeans.ACQUISIZIONI;
import it.eldasoft.contratti.rlo.xmlbeans.APCONTRATTOMASTERType;
import it.eldasoft.contratti.rlo.xmlbeans.AggiudicatariType;
import it.eldasoft.contratti.rlo.xmlbeans.AggiudicatarioType;
import it.eldasoft.contratti.rlo.xmlbeans.AmbitoType;
import it.eldasoft.contratti.rlo.xmlbeans.AnomaliaType;
import it.eldasoft.contratti.rlo.xmlbeans.AvanzamentoType;
import it.eldasoft.contratti.rlo.xmlbeans.CATEGORIAType;
import it.eldasoft.contratti.rlo.xmlbeans.CATEGORIE;
import it.eldasoft.contratti.rlo.xmlbeans.COLLAUDOINCARICHIType;
import it.eldasoft.contratti.rlo.xmlbeans.COLLINCARICHI;
import it.eldasoft.contratti.rlo.xmlbeans.CategoriaLavoriType;
import it.eldasoft.contratti.rlo.xmlbeans.ClasseImportoType;
import it.eldasoft.contratti.rlo.xmlbeans.CategoriaType2;
import it.eldasoft.contratti.rlo.xmlbeans.CondizioneGiustificazioneType;
import it.eldasoft.contratti.rlo.xmlbeans.ContrattoEsclusoType;
import it.eldasoft.contratti.rlo.xmlbeans.CriterioAggiudicazioneType;
import it.eldasoft.contratti.rlo.xmlbeans.DIMENSIONAMENTIAGGIUDICATI;
import it.eldasoft.contratti.rlo.xmlbeans.DIMENSIONAMENTOType;
import it.eldasoft.contratti.rlo.xmlbeans.EVENTOR129Type;
import it.eldasoft.contratti.rlo.xmlbeans.EVENTOSUBAPPALTOType;
import it.eldasoft.contratti.rlo.xmlbeans.EsitoCollaudoType;
import it.eldasoft.contratti.rlo.xmlbeans.FASEADESIONEType;
import it.eldasoft.contratti.rlo.xmlbeans.FASEAGGIUDICAZIONEType;
import it.eldasoft.contratti.rlo.xmlbeans.FASECOLLAUDOType;
import it.eldasoft.contratti.rlo.xmlbeans.FASECONCLUSIONEType;
import it.eldasoft.contratti.rlo.xmlbeans.FASEESECUZIONEType;
import it.eldasoft.contratti.rlo.xmlbeans.FASEINIZIOType;
import it.eldasoft.contratti.rlo.xmlbeans.FASESTIPULAType;
import it.eldasoft.contratti.rlo.xmlbeans.FINANZIAMENTIAGGIUDICATI;
import it.eldasoft.contratti.rlo.xmlbeans.FINANZIAMENTOType;
import it.eldasoft.contratti.rlo.xmlbeans.FeedBackDocument;
import it.eldasoft.contratti.rlo.xmlbeans.FlagSNType;
import it.eldasoft.contratti.rlo.xmlbeans.FlussoType;
import it.eldasoft.contratti.rlo.xmlbeans.GIUSTIFICAZIONEType;
import it.eldasoft.contratti.rlo.xmlbeans.GIUSTIFICAZIONI;
import it.eldasoft.contratti.rlo.xmlbeans.IMPRESAType;
import it.eldasoft.contratti.rlo.xmlbeans.IMPRESE;
import it.eldasoft.contratti.rlo.xmlbeans.INCARICHI;
import it.eldasoft.contratti.rlo.xmlbeans.INIZIOINCARICHIType;
import it.eldasoft.contratti.rlo.xmlbeans.ISTATType;
import it.eldasoft.contratti.rlo.xmlbeans.LAVORIAGGIUDICATI;
import it.eldasoft.contratti.rlo.xmlbeans.LAVOROType;
import it.eldasoft.contratti.rlo.xmlbeans.LOCALITA;
import it.eldasoft.contratti.rlo.xmlbeans.ModalitaAcquisizioneType;
import it.eldasoft.contratti.rlo.xmlbeans.ModalitaCollaudoType;
import it.eldasoft.contratti.rlo.xmlbeans.ModoIndizioneType;
import it.eldasoft.contratti.rlo.xmlbeans.ModoRealizzazioneType;
import it.eldasoft.contratti.rlo.xmlbeans.MotivoInterruzioneType;
import it.eldasoft.contratti.rlo.xmlbeans.MotivoOnereType;
import it.eldasoft.contratti.rlo.xmlbeans.MotivoRisoluzioneType;
import it.eldasoft.contratti.rlo.xmlbeans.MotivoSospensioneType;
import it.eldasoft.contratti.rlo.xmlbeans.MotivoVarianteType;
import it.eldasoft.contratti.rlo.xmlbeans.ONEREType;
import it.eldasoft.contratti.rlo.xmlbeans.ONERI;
import it.eldasoft.contratti.rlo.xmlbeans.PRESTAZIONEType;
import it.eldasoft.contratti.rlo.xmlbeans.PRESTAZIONI;
import it.eldasoft.contratti.rlo.xmlbeans.PROFESSIONISTAType;
import it.eldasoft.contratti.rlo.xmlbeans.PROFESSIONISTI;
import it.eldasoft.contratti.rlo.xmlbeans.ProceduraGaraType;
import it.eldasoft.contratti.rlo.xmlbeans.RISERVAType;
import it.eldasoft.contratti.rlo.xmlbeans.RISERVECONTENZIOSO;
import it.eldasoft.contratti.rlo.xmlbeans.RequisitiSpecialiType;
import it.eldasoft.contratti.rlo.xmlbeans.ResponsabileType;
import it.eldasoft.contratti.rlo.xmlbeans.ResponsabiliType;
import it.eldasoft.contratti.rlo.xmlbeans.RiserveType;
import it.eldasoft.contratti.rlo.xmlbeans.RuoloAggiudicatarioType;
import it.eldasoft.contratti.rlo.xmlbeans.RuoloResponsabileType;
import it.eldasoft.contratti.rlo.xmlbeans.SCHEDECONTRATTO;
import it.eldasoft.contratti.rlo.xmlbeans.SOSPENSIONEType;
import it.eldasoft.contratti.rlo.xmlbeans.SnType;
import it.eldasoft.contratti.rlo.xmlbeans.StatoEsteroType;
import it.eldasoft.contratti.rlo.xmlbeans.StrumentiAttuativiType;
import it.eldasoft.contratti.rlo.xmlbeans.StrumentoProgrammazioneType;
import it.eldasoft.contratti.rlo.xmlbeans.TipoAggiudicatarioType;
import it.eldasoft.contratti.rlo.xmlbeans.TipoDimensionamentoType;
import it.eldasoft.contratti.rlo.xmlbeans.TipoFinanziamentoType;
import it.eldasoft.contratti.rlo.xmlbeans.TipoPrestazioneType;
import it.eldasoft.contratti.rlo.xmlbeans.TipologiaComunicazioneType;
import it.eldasoft.contratti.rlo.xmlbeans.TipologiaContratto;
import it.eldasoft.contratti.rlo.xmlbeans.TipologiaFlusso;
import it.eldasoft.contratti.rlo.xmlbeans.TipologiaImportoType;
import it.eldasoft.contratti.rlo.xmlbeans.TipologiaLavoriType;
import it.eldasoft.contratti.rlo.xmlbeans.TipologiaPROCType;
import it.eldasoft.contratti.rlo.xmlbeans.TipologiaSAType;
import it.eldasoft.contratti.rlo.xmlbeans.TipologiaSettore;
import it.eldasoft.contratti.rlo.xmlbeans.TrasferimentoDatiType;
import it.eldasoft.contratti.rlo.xmlbeans.TrasferimentoType;
import it.eldasoft.contratti.rlo.xmlbeans.VARIANTEType;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.metadata.cache.DizionarioCampi;
import it.eldasoft.utils.metadata.domain.Campo;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.QueryExtractor;
import it.eldasoft.w9.utils.UtilityDateExtension;
import it.eldasoft.w9.utils.UtilitySITAT;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
public class XMLRLOManager {
	/** Logger. */
	static Logger logger = Logger.getLogger(XMLRLOManager.class);

	private final String SQL_DATI_SA = "select nomein, cfein, flag_sa_agente, id_tipologia_sa, TIPOLOGIA_PROCEDURA, denom_sa_agente, cf_sa_agente from w9gara left join uffint on w9gara.codein=uffint.codein where codgara = ?";
	private final String SQL_DATI_LOTTO = "select oggetto, luogo_istat, luogo_nuts, CIGCOM, ART_E1,ART_E2, CPV, CUP, NLOTTO, ID_TIPO_PRESTAZIONE, IMPORTO_LOTTO, ID_SCELTA_CONTRAENTE, ID_MODO_GARA from w9lott where codgara = ? and codlott = ?";
	private final String SQL_DATI_GARA = "select TIPO_APP, FLAG_CENTRALE_STIPULA, NLOTTI, CIG_ACCQUADRO from w9gara where codgara = ?";
	private final String SQL_DATI_APPALTO = "select FLAG_ACCORDO_QUADRO, DURATA_ACCQUADRO, OPERE_URBANIZ_SCOMPUTO, COD_STRUMENTO, IMPORTO_LAVORI, IMPORTO_COMPL_APPALTO, MODALITA_RIAGGIUDICAZIONE, ASTA_ELETTRONICA, PROCEDURA_ACC, PREINFORMAZIONE, TERMINE_RIDOTTO, ID_MODO_INDIZIONE, REQUISITI_SETT_SPEC, DATA_MANIF_INTERESSE, DATA_SCADENZA_RICHIESTA_INVITO, DATA_INVITO, DATA_SCADENZA_PRES_OFFERTA, NUM_MANIF_INTERESSE, NUM_IMPRESE_INVITATE, NUM_IMPRESE_OFFERENTI, NUM_OFFERTE_AMMESSE, OFFERTA_MASSIMO, OFFERTA_MINIMA, VAL_SOGLIA_ANOMALIA, NUM_OFFERTE_FUORI_SOGLIA, NUM_OFFERTE_ESCLUSE, NUM_IMP_ESCL_INSUF_GIUST, PERC_RIBASSO_AGG, PERC_OFF_AUMENTO, DATA_VERB_AGGIUDICAZIONE, FLAG_RICH_SUBAPPALTO, IMPORTO_SERVIZI, IMPORTO_FORNITURE, IMPORTO_AGGIUDICAZIONE, DATA_STIPULA, DATA_TERMINE, IMPORTO_SUBTOTALE from w9appa where codgara = ? and codlott = ? and num_appa = ?";
	private final String SQL_DATI_PUBB_ESITO = "select PROFILO_COMMITTENTE, SITO_MINISTERO_INF_TRASP, SITO_OSSERVATORIO_CP, DATA_GUCE, DATA_GURI, QUOTIDIANI_NAZ, QUOTIDIANI_REG from w9pues where codgara = ? and codlott = ? and num_pues = ?";
	private final String SQL_DATI_PUBB_BANDO = "select PROFILO_COMMITTENTE, SITO_MINISTERO_INF_TRASP, SITO_OSSERVATORIO_CP, DATA_GUCE, DATA_GURI, QUOTIDIANI_NAZ, QUOTIDIANI_REG from w9pubb where codgara = ? and codlott = 1 and NUM_APPA = 1";
	private final String SQL_DATI_AGGIUDICATARI = "select NOMIMP, CFIMP, CODIMP_AUSILIARIA, ID_TIPOAGG, RUOLO, ID_GRUPPO, FLAG_AVVALIMENTO, NAZIMP, NOMEST from w9aggi left join impr on w9aggi.codimp = impr.codimp where codgara = ? and codlott = ? and num_appa = ?";
	private final String SQL_DATI_IMPRESA_AUSILIARIA = "select CFIMP from impr where codimp = ?";
	private final String SQL_DATI_CATEGORIE = "select ID_CATEGORIA_PREVALENTE, CLASCAT, '1', '2', '2' from W9LOTT where CODGARA = ? and CODLOTT = ? and ID_CATEGORIA_PREVALENTE IS NOT NULL union select CATEGORIA, CLASCAT, '2', SCORPORABILE, SUBAPPALTABILE from W9LOTTCATE where CODGARA = ? and CODLOTT = ?";
	private final String SQL_DATI_CONDIZIONE = "select ID_CONDIZIONE	from W9COND, W9FASI where W9FASI.CODGARA = ? AND W9FASI.CODLOTT = ? AND W9COND.CODGARA = W9FASI.CODGARA AND W9COND.CODLOTT = W9FASI.CODLOTT AND (W9FASI.FASE_ESECUZIONE = 1 OR W9FASI.FASE_ESECUZIONE = 987)";
	private final String SQL_DATI_INIZ = "select DATA_STIPULA, DATA_SCADENZA, DATA_ESECUTIVITA, IMPORTO_CAUZIONE, DATA_INI_PROG_ESEC, DATA_APP_PROG_ESEC, FLAG_FRAZIONATA, DATA_VERBALE_CONS, DATA_VERBALE_DEF, FLAG_RISERVA, DATA_VERB_INIZIO, DATA_TERMINE FROM W9INIZ WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ? ";
	private final String SQL_DATI_AVANZAMENTO ="select FLAG_PAGAMENTO, IMPORTO_ANTICIPAZIONE, DATA_ANTICIPAZIONE, DATA_RAGGIUNGIMENTO, IMPORTO_SAL, IMPORTO_CERTIFICATO, FLAG_RITARDO, NUM_GIORNI_SCOST, NUM_GIORNI_PROROGA FROM W9AVAN WHERE CODGARA = ? AND CODLOTT = ? AND NUM_AVAN = ? ";
	private final String SQL_DATI_CONCLUSIONE ="select ID_MOTIVO_INTERR, ID_MOTIVO_RISOL, DATA_RISOLUZIONE, FLAG_ONERI, ONERI_RISOLUZIONE, FLAG_POLIZZA, DATA_VERBALE_CONSEGNA, TERMINE_CONTRATTO_ULT, DATA_ULTIMAZIONE, NUM_INFORTUNI, NUM_INF_PERM, NUM_INF_MORT FROM W9CONC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_CONC = ? ";
	private final String SQL_DATI_COLLAUDO ="select DATA_COLLAUDO_STAT, DATA_REGOLARE_ESEC, MODO_COLLAUDO, DATA_NOMINA_COLL, DATA_INIZIO_OPER, DATA_CERT_COLLAUDO, DATA_DELIBERA, ESITO_COLLAUDO, IMP_FINALE_LAVORI, IMP_FINALE_SERVIZI, IMP_FINALE_FORNIT, IMP_PROGETTAZIONE, IMP_DISPOSIZIONE, LAVORI_ESTESI, AMM_NUM_DEFINITE, AMM_NUM_DADEF, AMM_IMPORTO_RICH, AMM_IMPORTO_DEF, ARB_NUM_DEFINITE, ARB_NUM_DADEF, ARB_IMPORTO_RICH, ARB_IMPORTO_DEF, GIU_NUM_DEFINITE, GIU_NUM_DADEF, GIU_IMPORTO_RICH, GIU_IMPORTO_DEF, TRA_NUM_DEFINITE, TRA_NUM_DADEF, TRA_IMPORTO_RICH, TRA_IMPORTO_DEF, IMP_COMPL_APPALTO, IMP_FINALE_SECUR, IMP_PROGETTAZIONE FROM W9COLL WHERE CODGARA = ? AND CODLOTT = ? AND NUM_COLL = ? ";
	private final String SQL_DATI_SOSPENSIONE ="select DATA_VERB_SOSP, DATA_VERB_RIPR, ID_MOTIVO_SOSP, FLAG_SUPERO_TEMPO, FLAG_RISERVE, FLAG_VERBALE FROM W9SOSP WHERE CODGARA = ? AND CODLOTT = ? AND NUM_SOSP = ? ";
	private final String SQL_DATI_VARIANTE ="select DATA_VERB_APPR, ID_MOTIVO_VAR, ALTRE_MOTIVAZIONI, IMP_RIDET_LAVORI, IMP_RIDET_SERVIZI, IMP_RIDET_FORNIT, DATA_ATTO_AGGIUNTIVO FROM W9VARI LEFT JOIN W9MOTI ON W9VARI.CODGARA=W9MOTI.CODGARA and W9VARI.CODLOTT=W9MOTI.CODLOTT and W9VARI.NUM_VARI=W9MOTI.NUM_VARI WHERE W9VARI.CODGARA = ? AND W9VARI.CODLOTT = ? AND W9VARI.NUM_VARI = ? ";
	private final String SQL_DATI_ACCORDO ="select DATA_ACCORDO, ONERI_DERIVANTI, NUM_RISERVE FROM W9ACCO WHERE CODGARA = ? AND CODLOTT = ? AND NUM_ACCO = ? ";
	private final String SQL_DATI_SUBAPPALTO ="select CODIMP, CODIMP_AGGIUDICATRICE, IMPORTO_PRESUNTO, ID_CATEGORIA, ID_CPV FROM W9SUBA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_SUBA = ? ";
	private final String SQL_DATI_RECESSO ="select DATA_TERMINE, DATA_CONSEGNA, TIPO_COMUN, DATA_IST_RECESSO, FLAG_RIPRESA, FLAG_RISERVA FROM W9RITA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_RITA = ? ";
	private final String SQL_DATI_APPALAV ="select ID_APPALTO from W9APPALAV where CODGARA = ? AND CODLOTT = ? AND NUM_APPAL = ?";
	private final String SQL_DATI_APPAFORN ="select ID_APPALTO from W9APPAFORN where CODGARA = ? AND CODLOTT = ?";
	private final String SQL_DATI_FINANZIAMENTI="select ID_FINANZIAMENTO,COALESCE(IMPORTO_FINANZIAMENTO,0) from W9FINA, W9FASI where W9FASI.CODGARA = ? AND W9FASI.CODLOTT = ? AND W9FINA.CODGARA = W9FASI.CODGARA AND W9FINA.CODLOTT = W9FASI.CODLOTT AND W9FASI.NUM = W9FINA.NUM_APPA AND (W9FASI.FASE_ESECUZIONE = 1 OR W9FASI.FASE_ESECUZIONE = 12 OR W9FASI.FASE_ESECUZIONE = ?)";
	
	private final String SQL_DATI_TECNI = "select cogtei, nometei, teltec, ematec, cftec, indtec, faxtec, nomtec from tecni where codtec = ?";
	private final String SQL_DATI_INCARICHI_AGGIUDICAZIONE = "select W9INCA.SEZIONE, W9INCA.ID_RUOLO, W9INCA.CIG_PROG_ESTERNA, W9INCA.DATA_AFF_PROG_ESTERNA, W9INCA.DATA_CONS_PROG_ESTERNA,(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as CODICE_FISCALE_RESPONSABILE,(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as PIVATEC, CODTEC	from W9INCA, W9FASI where W9FASI.CODGARA = ? AND W9FASI.CODLOTT = ? AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND (W9INCA.SEZIONE = 'PA' OR W9INCA.SEZIONE = 'RA') AND W9FASI.FASE_ESECUZIONE = 1";	
	private final String SQL_DATI_INCARICHI_SOTTOSOGLIA = "select W9INCA.SEZIONE, W9INCA.ID_RUOLO, W9INCA.CIG_PROG_ESTERNA, W9INCA.DATA_AFF_PROG_ESTERNA, W9INCA.DATA_CONS_PROG_ESTERNA,(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as CODICE_FISCALE_RESPONSABILE,(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as PIVATEC, CODTEC from W9INCA, W9FASI where W9FASI.CODGARA = ? AND W9FASI.CODLOTT = ? AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND W9INCA.SEZIONE = 'RS' AND W9FASI.FASE_ESECUZIONE = 987";
	private final String SQL_DATI_INCARICHI_ESCLUSO = "select W9INCA.SEZIONE, W9INCA.ID_RUOLO, W9INCA.CIG_PROG_ESTERNA, W9INCA.DATA_AFF_PROG_ESTERNA, W9INCA.DATA_CONS_PROG_ESTERNA,(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as CODICE_FISCALE_RESPONSABILE,(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as PIVATEC, CODTEC from W9INCA, W9FASI where W9FASI.CODGARA = ? AND W9FASI.CODLOTT = ? AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND W9INCA.SEZIONE = 'RE' AND W9FASI.FASE_ESECUZIONE = 987";
	private final String SQL_DATI_INCARICHI_ADESIONE ="select W9INCA.SEZIONE, W9INCA.ID_RUOLO, W9INCA.CIG_PROG_ESTERNA, W9INCA.DATA_AFF_PROG_ESTERNA, W9INCA.DATA_CONS_PROG_ESTERNA,(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as CODICE_FISCALE_RESPONSABILE,(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as PIVATEC, CODTEC from W9INCA, W9FASI where W9FASI.CODGARA = ? AND W9FASI.CODLOTT = ? AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND W9INCA.SEZIONE = 'RQ' AND W9FASI.FASE_ESECUZIONE = 12";
	private final String SQL_DATI_INCARICHI_INIZIO ="select W9INCA.SEZIONE, W9INCA.ID_RUOLO, W9INCA.CIG_PROG_ESTERNA, W9INCA.DATA_AFF_PROG_ESTERNA, W9INCA.DATA_CONS_PROG_ESTERNA,(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as CODICE_FISCALE_RESPONSABILE,(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as PIVATEC, CODTEC from W9INCA, W9FASI where W9FASI.CODGARA = ? AND W9FASI.CODLOTT = ? AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND W9INCA.SEZIONE = 'IN' AND W9FASI.FASE_ESECUZIONE = 2";
	private final String SQL_DATI_INCARICHI_COLLAUDO ="select W9INCA.SEZIONE, W9INCA.ID_RUOLO, W9INCA.CIG_PROG_ESTERNA, W9INCA.DATA_AFF_PROG_ESTERNA, W9INCA.DATA_CONS_PROG_ESTERNA,(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as CODICE_FISCALE_RESPONSABILE,(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as PIVATEC, CODTEC from W9INCA, W9FASI where W9FASI.CODGARA = ? AND W9FASI.CODLOTT = ? AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND W9INCA.SEZIONE = 'CO' AND W9FASI.FASE_ESECUZIONE = 2";
	
	private final String SQL_UPDATE_CUI = "update w9lott set codcui = ? where cig = ?";
	
	private final String SQL_DATI_COMUNI_CONTRATTO = "select codcui, cig, tipo_contratto, flag_ente_speciale from w9lott where codgara = ? and codlott = ?";
	/** Manager SQL per le operazioni su database. */
	private SqlManager sqlManager;
	/** Manager XML per la gestione delle query. */
	private QueryExtractor queryExtractor = new QueryExtractor();
	//Formato data
	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");


	public void setSqlManager(SqlManager sqlManager) {
	    this.sqlManager = sqlManager;
	  }
	
	/**
	 * Metodo principale per l'esportazione.
	 * 
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param fase_esecuzione
	 *            fase esecuzione
	 * @param num
	 *            progressivo della fase
	 * @param username
	 *            username
	 * @param syscon
	 *            syscon
	 * @return Ritorna
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	public TrasferimentoDatiType getTrasferimentoDati(final Long codgara, final Long codlott, final Long fase_esecuzione, final Long num, final String username, final Long syscon) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("getTrasferimentoDati: inizio metodo");
		}
		
		TrasferimentoDatiType trasferimentoDati = TrasferimentoDatiType.Factory.newInstance();

		try {
			// Creazione dell'elemento principale trasferimento dati
			// Data di creazione del flusso e numero lotti (sempre 1)
			// Aggiorna username
			if (username != null && username.length()>0) {
				this.updateUsername(username, syscon);
			}
			TrasferimentoType trasferimento = trasferimentoDati.addNewInfoTrasferimento();
			trasferimento.setDATACREAZIONEFLUSSO(df.format(UtilityDateExtension.convertTodayToCalendar().getTime()));
			trasferimento.setNUMEROCONTRATTI(1);

			APCONTRATTOMASTERType contratti = trasferimentoDati.addNewContratti();
			setContratti(contratti, codgara, codlott, fase_esecuzione, num, username);

			// Creazione dell'elemento Responsabili (e' l'archivio di tutte le
			// anagrafiche dei responsabili/tecnici)
			ResponsabiliType responsabili = trasferimentoDati.addNewResponsabili();
			setAnagraficaResponsabili(responsabili, codgara, codlott, fase_esecuzione);


			// Creazione dell'elemento Aggiudicatari (e' l'archivio di tutte le
			// anagrafiche delle ditte aggiudicatarie/affidatarie,
			// subappaltatrici
			// e ausiliarie (in caso di avvalimento)
			AggiudicatariType aggiudicatari = trasferimentoDati.addNewAggiudicatari();
			setAnagraficaAggiudicatari(aggiudicatari, codgara, codlott, fase_esecuzione);

		} catch (GestoreException ioe) {
			logger.error(ioe);
		}

		

		return trasferimentoDati;

	}
	
	/**
	 * Aggiorna lo username associato all'utente per Regione Lombardia.
	 * 
	 * @param username
	 * @param syscon
	 */
	private void updateUsername(final String username, final Long syscon) {
		boolean result = false;
		TransactionStatus transazione = null;
		try {
			Long exist = (Long) sqlManager.getObject("select SYSCON from USRCREDEST where SYSCON=? and CODSISTEMA=?", new Object[] { syscon, "RLO" } );
			transazione = this.sqlManager.startTransaction();
			if (exist != null) {
				sqlManager.update("update USRCREDEST set USERNAME=? where SYSCON=? and CODSISTEMA=?", new Object[] { username, syscon, "RLO" });
			} else {
				sqlManager.update("insert into USRCREDEST(SYSCON, CODSISTEMA, USERNAME) values(?,?,?)", new Object[] { syscon, "RLO", username});
			}
			result = true;
		} catch (SQLException e) {
			logger.error(e);
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
	}
	
	
	/**
	   * Ritorna il codice richiesto tradotto per Regione Lombardia.
	   * 
	   * @param tabellato
	   * @param valore
	   * @return Ritorna il codice richiesto tradotto per Regione Lombardia
	   * @throws SQLException
	   */
	  public String getCodiceSITATtoRLO(final String tabellato, final String valore) throws SQLException {
	    return (String) sqlManager.getObject("select CODAVCP from W9CODICI_AVCP where TABCOD='RLO_" + tabellato + "' and CODSITAT=?", 
	        new Object[] { valore } );
	  }
	  
	/**
	 * Lista delle modalita' di acquisizione forniture/servizi.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setTipiAppaltoForn(final FASEAGGIUDICAZIONEType aggiudicazione, final Long codgara, final Long codlott) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setTipiAppaltoForn: inizio metodo");
		}
		//String selectW9APPAFORN = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPAFORN");

		try {
			List<?> datiW9APPAFORN = sqlManager.getListVector(SQL_DATI_APPAFORN, new Object[] { codgara, codlott });

			if (datiW9APPAFORN != null && datiW9APPAFORN.size() > 0) {
				ListIterator<?> iterator = datiW9APPAFORN.listIterator();

				ACQUISIZIONI acquisizioni = aggiudicazione.addNewMODALITAACQUISIZIONE();
				while (iterator.hasNext()) {
					List<?> riga = (List<?>) iterator.next();
					ACQUISIZIONEType acquisizione = acquisizioni.addNewACQUISIZIONE();
					
					if (verificaEsistenzaValore(riga.get(0))) {
						acquisizione.setCODICEACQUISIZIONE(ModalitaAcquisizioneType.Enum.forString(riga.get(0).toString()));
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati della modalita' di acquisizione forniture/servizi", "TipiAppaltoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setTipiAppaltoForn: fine metodo");
		}
	}

	private void setLavori(final FASEAGGIUDICAZIONEType aggiudicazione, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setLavori: inizio metodo");
		}
		try {
			List<?> datiW9APPALAV = sqlManager.getListVector(SQL_DATI_APPALAV, new Object[] { codgara, codlott, num });

			if (datiW9APPALAV != null && datiW9APPALAV.size() > 0) {
				ListIterator<?> iterator = datiW9APPALAV.listIterator();

				LAVORIAGGIUDICATI lavori = aggiudicazione.addNewLAVORI();
				while (iterator.hasNext()) {
					List<?> riga = (List<?>) iterator.next();
					LAVOROType lavoro = lavori.addNewLAVORO();
					
					if (verificaEsistenzaValore(riga.get(0))) {
						String valoreDaConvertire = riga.get(0).toString();
						String valoreRLO = this.getCodiceSITATtoRLO("W3002", valoreDaConvertire);
						lavoro.setCODICELAVORO(TipologiaLavoriType.Enum.forString(valoreRLO));
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati della tipologia di lavori", "TipologiaLavoriType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setLavori: fine metodo");
		}
	}
	
	/**
	 * Lista delle condizioni che giustificano il ricorso alla procedura
	 * negoziata.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setCondizioni(final FASEAGGIUDICAZIONEType aggiudicazione, final Long codgara, final Long codlott) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setCondizioni: inizio metodo");
		}

		try {
			List<?> datiW9COND = sqlManager.getListVector(SQL_DATI_CONDIZIONE, new Object[] { codgara, codlott });

			if (datiW9COND != null && datiW9COND.size() > 0) {
				
				GIUSTIFICAZIONI condizioni = aggiudicazione.addNewGIUSTIFICAZIONIRICORSO();
				ListIterator<?> iterator = datiW9COND.listIterator();

				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					GIUSTIFICAZIONEType condizione = condizioni.addNewGIUSTIFICAZIONE();
					
					if (verificaEsistenzaValore(riga.get(0))) {
						String valoreRLO = this.getCodiceSITATtoRLO("W3006", riga.get(0).toString());
						condizione.setCODICECONDIZIONE(CondizioneGiustificazioneType.Enum.forString(valoreRLO));
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati delle condizioni dell'aggiudicazione", "CondizioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setCondizioni: fine metodo");
		}
	}

	/**
	 * Lista dei requisiti di partecipazione.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setRequisiti(final FASEAGGIUDICAZIONEType aggiudicazione, final Long codgara, final Long codlott, final double importoLotto) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setRequisiti: inizio metodo");
		}

		try {
			List<?> datiW9REQU = sqlManager.getListVector(SQL_DATI_CATEGORIE, new Object[] { codgara, codlott, codgara, codlott });

			if (datiW9REQU != null && datiW9REQU.size() > 0) {
				ListIterator<?> iterator = datiW9REQU.listIterator();
				CATEGORIE categorie = aggiudicazione.addNewCATEGORIEIMPORTI();
				while (iterator.hasNext()) {
					List<?> riga = (List<?>) iterator.next();

					CATEGORIAType categoria = categorie.addNewCATEGORIA();
					
					if (verificaEsistenzaValore(riga.get(0))) {
						String categoriaSimog = UtilitySITAT.getCategoriaSIMOG(this.sqlManager, riga.get(0).toString());
						categoria.setCATEGORIA(CategoriaType2.Enum.forString(categoriaSimog));
					}
					if (verificaEsistenzaValore(riga.get(1))) {
						categoria.setCLASSEIMPORTO(ClasseImportoType.Enum.forString(riga.get(1).toString()));
					} else {
						if ( importoLotto <= 258228){
							categoria.setCLASSEIMPORTO(ClasseImportoType.Enum.forString("I"));
			            } else if (importoLotto <= 516457) {
			            	categoria.setCLASSEIMPORTO(ClasseImportoType.Enum.forString("II"));
			            } else if (importoLotto <= 1032914) {
			            	categoria.setCLASSEIMPORTO(ClasseImportoType.Enum.forString("III"));
			            } else if (importoLotto <= 2582284) {
			            	categoria.setCLASSEIMPORTO(ClasseImportoType.Enum.forString("IV"));
			            } else if (importoLotto <= 5164569) {
			            	categoria.setCLASSEIMPORTO(ClasseImportoType.Enum.forString("V"));
			            } else if (importoLotto <= 10329138) {
			            	categoria.setCLASSEIMPORTO(ClasseImportoType.Enum.forString("VI"));
			            } else if (importoLotto <= 15493707) {
			            	categoria.setCLASSEIMPORTO(ClasseImportoType.Enum.forString("VII"));
			            } else {
			            	categoria.setCLASSEIMPORTO(ClasseImportoType.Enum.forString("VIII"));
			            }
					}

					JdbcParametro flagSN = SqlManager.getValueFromVectorParam(riga, 2);
					if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
						if ("1".equals(flagSN.getStringValue())) {
							categoria.setTIPOLOGIA(TipologiaImportoType.X_1);
						}
					}
					flagSN = SqlManager.getValueFromVectorParam(riga, 3);
					if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
						if ("1".equals(flagSN.getStringValue())) {
							categoria.setTIPOLOGIA(TipologiaImportoType.X_2);
						}
					}
					flagSN = SqlManager.getValueFromVectorParam(riga, 4);
					if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
						if ("1".equals(flagSN.getStringValue())) {
							categoria.setTIPOLOGIA(TipologiaImportoType.X_3);
						}
					}
					
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati dei requisiti di partecipazione", "RequisitoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setRequisiti: fine metodo");
		}
	}

	/**
	 * Lista dei finanziamenti.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setFinanziamenti(final FASEAGGIUDICAZIONEType aggiudicazione, final Long codgara, final Long codlott, final Long fase) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setFinanziamenti: inizio metodo");
		}
		try {
			List<?> datiW9FINA = sqlManager.getListVector(SQL_DATI_FINANZIAMENTI, new Object[] { codgara, codlott, fase });

			if (datiW9FINA != null && datiW9FINA.size() > 0) {
				ListIterator<?> iterator = datiW9FINA.listIterator();
				FINANZIAMENTIAGGIUDICATI finanziamenti = aggiudicazione.addNewFINANZIAMENTI();
				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					FINANZIAMENTOType finanziamento = finanziamenti.addNewFINANZIAMENTO();

					if (verificaEsistenzaValore(riga.get(0))) {
						finanziamento.setTIPOFINANZIAMENTO(TipoFinanziamentoType.Enum.forString(riga.get(0).toString()));
					} else {
						finanziamento.setNilTIPOFINANZIAMENTO();
					}
					if (verificaEsistenzaValore(riga.get(1))) {
						finanziamento.setIMPORTOFINANZIAMENTO(convertiImporto(Double.parseDouble(riga.get(1).toString())));
					} else {
						finanziamento.setNilIMPORTOFINANZIAMENTO();
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati dei finanziamenti", "FinanziamentoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setFinanziamenti: fine metodo");
		}
	}

	
	/**
	 * Utilizzato per ricavare l'associazione tra i codice nazione presente
	 * nell'archivio delle imprese (IMPR) e la codifica prevista da SIMOG.
	 * 
	 * @param codiceNazione
	 *            codice della nazione
	 * @return Ritorna lo stato estero a partire dal codice dello stato stesso
	 * @throws GestoreException
	 *             GestoreException
	 */
	private String getStatoEstero(final String codiceNazione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("getStatoEstero: inizio metodo");
		}

		String statoEstero = "";
		try {
			statoEstero = (String) sqlManager.getObject("select tab2tip from tab2 where tab2d1 = ? and tab2cod = 'W3z12'", new Object[] { codiceNazione });
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura della nazione dell'impresa", "getStatoEstero", e);
		}
		if (statoEstero == null) {
			statoEstero = "";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getStatoEstero: fine metodo");
		}

		return statoEstero;
	}

	/**
	 * Gestione dei dati degli incaricati.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param querySezione
	 *            Nome della query:
	 *            <ul>
	 *            <li>W9INCA_AGGIUDICAZIONE per le sezioni PA ed RA
	 *            <li>W9INCA_SOTTOSOGLIA per la sezione RS
	 *            <li>W9INCA_ESCLUSO per la sezione RE
	 *            <li>W9INCA_ADESIONE per la sezione RQ
	 *            <li>W9INCA_INIZIO per la sezione IN
	 *            <li>W9INCA_COLLAUDO per la sezione CO
	 *            </ul>
	 * 
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setIncaricati(final FASEAGGIUDICAZIONEType aggiudicazione, final Long codgara, final Long codlott, final String querySezione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricati: inizio metodo");
		}

		try {
			List<?> datiW9INCA = sqlManager.getListVector(querySezione, new Object[] { codgara, codlott });

			if (datiW9INCA != null && datiW9INCA.size() > 0) {
				PRESTAZIONI incaricati = aggiudicazione.addNewPRESTAZIONIPROGETTUALI();
				PROFESSIONISTI professionisti = aggiudicazione.addNewINCARICHIPROFESSIONISTI();
				
				ListIterator<?> iterator = datiW9INCA.listIterator();

				while (iterator.hasNext()) {

					PRESTAZIONEType incaricato = incaricati.addNewPRESTAZIONE();
					List<?> riga = (List<?>) iterator.next();
					
					if (verificaEsistenzaValore(riga.get(1))) {
						incaricato.setRUOLO(RuoloResponsabileType.Enum.forString(riga.get(1).toString()));
					}
					if (verificaEsistenzaValore(riga.get(2))) {
						incaricato.setCIGAFFIDAMENTO(riga.get(2).toString());
					}
					if (verificaEsistenzaValore(riga.get(3))) {
						incaricato.setDATAAFFIDAMENTO(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 3)).getTime()));
					}
					
					if (verificaEsistenzaValore(riga.get(4))) {
						incaricato.setDATACONSEGNA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 4)).getTime()));
					}
					
					if (verificaEsistenzaValore(riga.get(5))) {
						incaricato.setCODICEFISCALEPROFESSIONISTA(riga.get(5).toString());
					} else {
						if (verificaEsistenzaValore(riga.get(6))) {
							incaricato.setCODICEFISCALEPROFESSIONISTA(riga.get(6).toString());
						}
					}
					
					if (verificaEsistenzaValore(riga.get(7))) {
						List<?> datiTECNI = sqlManager.getVector(SQL_DATI_TECNI, new Object[] { riga.get(7).toString()});
						if (datiTECNI != null && datiTECNI.size() > 0) {
							PROFESSIONISTAType professionista = professionisti.addNewPROFESSIONISTA();
							
							if (verificaEsistenzaValore(datiTECNI.get(0))) {
								professionista.setCOGNOMEPROFESSIONISTA(datiTECNI.get(0).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(1))) {
								professionista.setNOMEPROFESSIONISTA(datiTECNI.get(1).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(2))) {
								professionista.setTELEFONOPROFESSIONISTA(datiTECNI.get(2).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(3))) {
								professionista.setMAILPROFESSIONISTA(datiTECNI.get(3).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(4))) {
								professionista.setCODICEFISCALEPROFESSIONISTA(datiTECNI.get(4).toString());
							}
							if (verificaEsistenzaValore(riga.get(1))) {
								professionista.setIDRUOLO(RuoloResponsabileType.Enum.forString(riga.get(1).toString()));
							}
							if (verificaEsistenzaValore(datiTECNI.get(5))) {
								professionista.setINDIRIZZO(datiTECNI.get(5).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(6))) {
								professionista.setFAX(datiTECNI.get(6).toString());
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati degli incaricati", "IncaricatoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricati: fine metodo");
		}
	}
	
	
	private void setProfessionisti(final FASEADESIONEType adesione, final Long codgara, final Long codlott, final String querySezione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricati: inizio metodo");
		}

		try {
			List<?> datiW9INCA = sqlManager.getListVector(querySezione, new Object[] { codgara, codlott });

			if (datiW9INCA != null && datiW9INCA.size() > 0) {
				PROFESSIONISTI professionisti = adesione.addNewINCARICHIPROFESSIONISTI();
				
				ListIterator<?> iterator = datiW9INCA.listIterator();

				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					
					if (verificaEsistenzaValore(riga.get(7))) {
						List<?> datiTECNI = sqlManager.getVector(SQL_DATI_TECNI, new Object[] { riga.get(7).toString()});
						if (datiTECNI != null && datiTECNI.size() > 0) {
							PROFESSIONISTAType professionista = professionisti.addNewPROFESSIONISTA();
							
							if (verificaEsistenzaValore(datiTECNI.get(0))) {
								professionista.setCOGNOMEPROFESSIONISTA(datiTECNI.get(0).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(1))) {
								professionista.setNOMEPROFESSIONISTA(datiTECNI.get(1).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(2))) {
								professionista.setTELEFONOPROFESSIONISTA(datiTECNI.get(2).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(3))) {
								professionista.setMAILPROFESSIONISTA(datiTECNI.get(3).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(4))) {
								professionista.setCODICEFISCALEPROFESSIONISTA(datiTECNI.get(4).toString());
							}
							if (verificaEsistenzaValore(riga.get(1))) {
								professionista.setIDRUOLO(RuoloResponsabileType.Enum.forString(riga.get(1).toString()));
							}
							if (verificaEsistenzaValore(datiTECNI.get(5))) {
								professionista.setINDIRIZZO(datiTECNI.get(5).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(6))) {
								professionista.setFAX(datiTECNI.get(6).toString());
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati degli incaricati", "IncaricatoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricati: fine metodo");
		}
	}
	
	private void setIncaricatiInizio(final FASEINIZIOType inizio, final Long codgara, final Long codlott, final String querySezione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricatiInizio: inizio metodo");
		}

		try {
			List<?> datiW9INCA = sqlManager.getListVector(querySezione, new Object[] { codgara, codlott });

			if (datiW9INCA != null && datiW9INCA.size() > 0) {
				INCARICHI incaricati = inizio.addNewSOGGETTIINCARICATI();
				
				ListIterator<?> iterator = datiW9INCA.listIterator();

				while (iterator.hasNext()) {

					INIZIOINCARICHIType incaricato = incaricati.addNewINIZIOINCARICHI();
					List<?> riga = (List<?>) iterator.next();
					
					if (verificaEsistenzaValore(riga.get(1))) {
						incaricato.setIDRUOLO(RuoloResponsabileType.Enum.forString(riga.get(1).toString()));
					}
					
					if (verificaEsistenzaValore(riga.get(5))) {
						incaricato.setCODICEFISCALEPROFESSIONISTA(riga.get(5).toString());
					} else {
						if (verificaEsistenzaValore(riga.get(6))) {
							incaricato.setCODICEFISCALEPROFESSIONISTA(riga.get(6).toString());
						}
					}
					
					if (verificaEsistenzaValore(riga.get(7))) {
						List<?> datiTECNI = sqlManager.getVector(SQL_DATI_TECNI, new Object[] { riga.get(7).toString()});
						if (datiTECNI != null && datiTECNI.size() > 0) {

							if (verificaEsistenzaValore(datiTECNI.get(2))) {
								incaricato.setTELEFONOPROFESSIONISTA(datiTECNI.get(2).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(3))) {
								incaricato.setMAILPROFESSIONISTA(datiTECNI.get(3).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(4))) {
								incaricato.setCODICEFISCALEPROFESSIONISTA(datiTECNI.get(4).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(5))) {
								incaricato.setINDIRIZZOPROFESSIONISTA(datiTECNI.get(5).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(6))) {
								incaricato.setFAXPROFESSIONISTA(datiTECNI.get(6).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(7))) {
								incaricato.setNOMINATIVO(datiTECNI.get(7).toString());
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati degli incaricati", "IncaricatoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricati: fine metodo");
		}
	}
	
	private void setIncaricatiCollaudo(final FASECOLLAUDOType collaudo, final Long codgara, final Long codlott, final String querySezione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricatiCollaudo: inizio metodo");
		}

		try {
			List<?> datiW9INCA = sqlManager.getListVector(querySezione, new Object[] { codgara, codlott });

			if (datiW9INCA != null && datiW9INCA.size() > 0) {
				COLLINCARICHI incaricati = collaudo.addNewSOGGETTIINCARICATI();
				
				ListIterator<?> iterator = datiW9INCA.listIterator();

				while (iterator.hasNext()) {

					COLLAUDOINCARICHIType incaricato = incaricati.addNewCOLLAUDOINCARICHI();
					List<?> riga = (List<?>) iterator.next();
					
					if (verificaEsistenzaValore(riga.get(1))) {
						incaricato.setIDRUOLO(RuoloResponsabileType.Enum.forString(riga.get(1).toString()));
					}
					
					if (verificaEsistenzaValore(riga.get(5))) {
						incaricato.setCODICEFISCALEPROFESSIONISTA(riga.get(5).toString());
					} else {
						if (verificaEsistenzaValore(riga.get(6))) {
							incaricato.setCODICEFISCALEPROFESSIONISTA(riga.get(6).toString());
						}
					}
					
					if (verificaEsistenzaValore(riga.get(7))) {
						List<?> datiTECNI = sqlManager.getVector(SQL_DATI_TECNI, new Object[] { riga.get(7).toString()});
						if (datiTECNI != null && datiTECNI.size() > 0) {

							if (verificaEsistenzaValore(datiTECNI.get(2))) {
								incaricato.setTELEFONOPROFESSIONISTA(datiTECNI.get(2).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(3))) {
								incaricato.setMAILPROFESSIONISTA(datiTECNI.get(3).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(4))) {
								incaricato.setCODICEFISCALEPROFESSIONISTA(datiTECNI.get(4).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(5))) {
								incaricato.setINDIRIZZOPROFESSIONISTA(datiTECNI.get(5).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(6))) {
								incaricato.setFAXPROFESSIONISTA(datiTECNI.get(6).toString());
							}
							if (verificaEsistenzaValore(datiTECNI.get(7))) {
								incaricato.setNOMINATIVO(datiTECNI.get(7).toString());
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati degli incaricati", "IncaricatoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricatiCollaudo: fine metodo");
		}
	}
	
	/**
	 * Gestione dell'elemento SchedaCompleta.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param fase_esecuzione
	 *            (opzionale) fase esecuzione
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setSchede(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long fase_esecuzione, Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSchedaCompleta: inizio metodo");
		}

		if (fase_esecuzione != null) {
			switch (fase_esecuzione.intValue()) {
			case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
			case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
				// Dati della fase di aggiudicazione per contratti sopra soglia
				setAggiudicazione(schede, codgara, codlott, num, fase_esecuzione);
				break;
			case CostantiW9.STIPULA_ACCORDO_QUADRO:
				// Fase stipula
				setStipula(schede, codgara, codlott, num);
				break;
			case CostantiW9.ADESIONE_ACCORDO_QUADRO:
				// Adesione ad accordo quadro
				setAdesione(schede, codgara, codlott, num);
				break;
			case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
				// Dati di inizio per contratti sopra soglia
				setInizio(schede, codgara, codlott, num);
				break;
			case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
				// Dati relativi agli stati di avanzamento (SAL) per contratti
				// sopra soglia
				setEsecuzione(schede, codgara, codlott, num);
				break;
			case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
			case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
				// Dati di conclusione per contratti
				setConclusione(schede, codgara, codlott, num);
				break;
			case CostantiW9.COLLAUDO_CONTRATTO:
				// Dati di collaudo per contratti sopra soglia
				setCollaudo(schede, codgara, codlott, num);
				break;
			case CostantiW9.SOSPENSIONE_CONTRATTO:
				setSospensione(schede, codgara, codlott, num);
				break;
			case CostantiW9.VARIANTE_CONTRATTO:
				setVariante(schede, codgara, codlott, num);
				break;
			case CostantiW9.ACCORDO_BONARIO:
				// Dati relativi agli accordi bonaria per contratti sopra soglia
				setAccordoBonario(schede, codgara, codlott, num);
				break;
			case CostantiW9.SUBAPPALTO:
				// Dati relativi ai subappalti per contratti sopra soglia
				setSubappalto(schede, codgara, codlott, num);
				break;				
			case CostantiW9.ISTANZA_RECESSO:
				// Dati di recesso per contratti sopra soglia
				setRecesso(schede, codgara, codlott, num);
				break;
			}

		} 
		if (logger.isDebugEnabled()) {
			logger.debug("setSchedaCompleta: fine metodo");
		}
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
	 * Dati della fase di aggiudicazione o definizione di procedura negoziata
	 * per contratti sopra soglia.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAggiudicazione(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num, final Long fase_esecuzione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAggiudicazione: inizio metodo");
		}
		try {
			
			List<?> datiW9APPA = sqlManager.getVector(SQL_DATI_APPALTO, new Object[] { codgara, codlott, num });

			List<?> datiSA = sqlManager.getVector(SQL_DATI_SA, new Object[] { codgara});

			List<?> datiLOTTO = sqlManager.getVector(SQL_DATI_LOTTO, new Object[] { codgara, codlott});

			List<?> datiGARA = sqlManager.getVector(SQL_DATI_GARA, new Object[] { codgara});

			List<?> datiPUBB = sqlManager.getVector(SQL_DATI_PUBB_BANDO, new Object[] { codgara});
			
			List<?> datiAGGI = sqlManager.getListVector(SQL_DATI_AGGIUDICATARI, new Object[] { codgara, codlott, num});

			List<?> datiW9DatiComuni = sqlManager.getVector(SQL_DATI_COMUNI_CONTRATTO, new Object[] { codgara, codlott });

			String tipoContratto = datiW9DatiComuni.get(2).toString();
			
			FASEAGGIUDICAZIONEType aggiudicazione = schede.addNewFASEAGGIUDICAZIONE();
			
			// contratti esclusi (inviano a simo solo la scheda adesione,
			// altrimenti l'agg. semplificata)
			boolean escluso =  UtilitySITAT.isE1(codgara, codlott, sqlManager);
			// sottosoglia
			boolean sottosoglia = (fase_esecuzione==CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE);
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiSA != null && datiSA.size() > 0) {
				
				if (verificaEsistenzaValore(datiSA.get(0))) {
					aggiudicazione.setDENOMINAZIONESA(datiSA.get(0).toString());
				}
				if (verificaEsistenzaValore(datiSA.get(1))) {
					aggiudicazione.setCODFISCALESA(datiSA.get(1).toString());
				}
				if (verificaEsistenzaValore(datiSA.get(2))) {
					aggiudicazione.setSAALTROSOGGETTO(SnType.Enum.forString(datiSA.get(1).toString().equals("1")?"S":"N"));
				} else {
					aggiudicazione.setSAALTROSOGGETTO(SnType.N);
				}
				if (aggiudicazione.getSAALTROSOGGETTO().equals(SnType.S)) {
					if (verificaEsistenzaValore(datiSA.get(3))) {
						aggiudicazione.setIDTIPOLOGIASA(TipologiaSAType.Enum.forString(datiSA.get(3).toString()));
					}
					if (verificaEsistenzaValore(datiSA.get(4))) {
						aggiudicazione.setIDTIPOLOGIAPROC(TipologiaPROCType.Enum.forString(datiSA.get(4).toString()));
					}
					if (verificaEsistenzaValore(datiSA.get(5))) {
						aggiudicazione.setDENOMINAZIONESOGGETTOSA(datiSA.get(5).toString());
					}
					if (verificaEsistenzaValore(datiSA.get(6))) {
						aggiudicazione.setCODICEFISCALESOGGETTOSA(datiSA.get(6).toString());
					}
				}
			}
			
			if (datiLOTTO != null && datiLOTTO.size() > 0) {
				if (verificaEsistenzaValore(datiLOTTO.get(0))) {
					aggiudicazione.setOGGETTOCONTRATTO(datiLOTTO.get(0).toString());
				}
				if (verificaEsistenzaValore(datiLOTTO.get(1))) {
					LOCALITA localita = aggiudicazione.addNewLOCALIZZAZIONE();
					ISTATType istat = localita.addNewISTAT();
					istat.setAMBITO(AmbitoType.X_5);
					istat.setCODICE(datiLOTTO.get(1).toString());
					istat.setINDIRIZZO("-");
					aggiudicazione.setLOCALIZZAZIONE(localita);
				} 
				if (verificaEsistenzaValore(datiLOTTO.get(2))) {
					aggiudicazione.setCODICENUTS(datiLOTTO.get(2).toString());
				} 
				
				boolean e1 = false;
				boolean e2 = false;
				if (verificaEsistenzaValore(datiLOTTO.get(4))) {
					if (datiLOTTO.get(4).toString().equals("1")) {
						e1 = true;
					}
				}
				if (verificaEsistenzaValore(datiLOTTO.get(5))) {
					if (datiLOTTO.get(5).toString().equals("1")) {
						e2 = true;
					}
				}
				if (e1 || e2) {
					if (e1) {
						aggiudicazione.setIDCONTRATTOESCLUSO(ContrattoEsclusoType.X_1);
					}
					if (e2) {
						aggiudicazione.setIDCONTRATTOESCLUSO(ContrattoEsclusoType.X_8);
					}
				} else {
					aggiudicazione.setIDCONTRATTOESCLUSO(ContrattoEsclusoType.X_99);
				}
				
				if (verificaEsistenzaValore(datiLOTTO.get(6))) {
					aggiudicazione.setCPV(convertiCPV(datiLOTTO.get(6).toString()));
				}
				
				if (verificaEsistenzaValore(datiLOTTO.get(7))) {
					aggiudicazione.setCODICECUP(datiLOTTO.get(7).toString());
				} 
				
				if (verificaEsistenzaValore(datiLOTTO.get(8))) {
					aggiudicazione.setNUMEROLOTTO(Integer.parseInt(datiLOTTO.get(8).toString()));
				}
				
				if (verificaEsistenzaValore(datiLOTTO.get(9))) {
					aggiudicazione.setIDPRESTAZIONE(TipoPrestazioneType.Enum.forString(datiLOTTO.get(9).toString()));
				}
				
				if (verificaEsistenzaValore(datiLOTTO.get(10))) {
					aggiudicazione.setIMPORTOLOTTO(convertiImporto(Double.parseDouble(datiLOTTO.get(10).toString())));
				}
				
				if (verificaEsistenzaValore(datiLOTTO.get(11))) {
					String valoreRLO = this.getCodiceSITATtoRLO("W3005", datiLOTTO.get(11).toString());
					aggiudicazione.setCODICEPROCEDURAGARA(ProceduraGaraType.Enum.forString(valoreRLO));
				}
				
				if (verificaEsistenzaValore(datiLOTTO.get(12))) {
					String valoreRLO = this.getCodiceSITATtoRLO("W3007", datiLOTTO.get(12).toString());
					aggiudicazione.setCODICECRITERIOAGGIUDICAZIONE(CriterioAggiudicazioneType.Enum.forString(valoreRLO));
				} 
				
			}
			
			if (datiGARA != null && datiGARA.size() > 0) {
				if (verificaEsistenzaValore(datiGARA.get(0))) {
					aggiudicazione.setIDMODALITAREALIZZAZIONE(ModoRealizzazioneType.Enum.forString(datiGARA.get(0).toString()));
				}
				if (verificaEsistenzaValore(datiGARA.get(1))) {
					aggiudicazione.setSTIPULA(SnType.Enum.forString(datiGARA.get(1).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiGARA.get(2))) {
					aggiudicazione.setNUMEROLOTTICOMPLESSIVO(Integer.parseInt(datiGARA.get(2).toString()));
				}
				if (verificaEsistenzaValore(datiGARA.get(3))) {
					aggiudicazione.setCIGPADRE(datiGARA.get(3).toString());
				}
			}
			
			if (datiW9APPA != null && datiW9APPA.size() > 0) {
				if (verificaEsistenzaValore(datiW9APPA.get(0))) {
					aggiudicazione.setACCORDOQUADRO(SnType.Enum.forString(datiW9APPA.get(0).toString().equals("1")?"S":"N"));
				} else {
					aggiudicazione.setACCORDOQUADRO(SnType.N);
				}
				
				if (verificaEsistenzaValore(datiW9APPA.get(1))) {
					aggiudicazione.setDURATAGG(Integer.parseInt(datiW9APPA.get(1).toString()));
				}
				
				aggiudicazione.setSTRUMENTIATTUATIVI(StrumentiAttuativiType.X_2005);
				
				if (verificaEsistenzaValore(datiW9APPA.get(2))) {
					aggiudicazione.setOPEREURBANSCOMPUTO(SnType.Enum.forString(datiW9APPA.get(2).toString().equals("1")?"S":"N"));
				}
				
				aggiudicazione.setCODICECATEGORIALAVORI(CategoriaLavoriType.X_9_F);
				
				if (verificaEsistenzaValore(datiW9APPA.get(3))) {
					aggiudicazione.setIDSTRUMENTOPROGRAMMAZIONE(StrumentoProgrammazioneType.Enum.forString(datiW9APPA.get(3).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(4))) {
					aggiudicazione.setIMPORTOLAVORI(convertiImporto(Double.parseDouble(datiW9APPA.get(4).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(5))) {
					aggiudicazione.setIMPORTOTOTALE(convertiImporto(Double.parseDouble(datiW9APPA.get(5).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(6))) {
					if (datiW9APPA.get(6).toString().equals("1")) {
						aggiudicazione.setMODRIAGGAFFID(CondizioneGiustificazioneType.X_27);
					} else {
						aggiudicazione.setMODRIAGGAFFID(CondizioneGiustificazioneType.X_28);
					}
				}
				if (verificaEsistenzaValore(datiW9APPA.get(7))) {
					aggiudicazione.setASTAELETTRONICA(SnType.Enum.forString(datiW9APPA.get(7).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(8))) {
					aggiudicazione.setPROCEDURAACCELERATA(SnType.Enum.forString(datiW9APPA.get(8).toString().equals("1")?"S":"N"));
				} else {
					aggiudicazione.setPROCEDURAACCELERATA(SnType.N);
				}
				if (verificaEsistenzaValore(datiW9APPA.get(9))) {
					aggiudicazione.setPREINFORMAZIONE(SnType.Enum.forString(datiW9APPA.get(9).toString().equals("1")?"S":"N"));
				} else {
					aggiudicazione.setPREINFORMAZIONE(SnType.N);
				}
				
				if (verificaEsistenzaValore(datiW9APPA.get(10))) {
					aggiudicazione.setTERMINERIDOTTO(SnType.Enum.forString(datiW9APPA.get(10).toString().equals("1")?"S":"N"));
				} else {
					aggiudicazione.setTERMINERIDOTTO(SnType.N);
				}
				if (verificaEsistenzaValore(datiW9APPA.get(11))) {
					aggiudicazione.setIDMODALITAINDIZIONE(ModoIndizioneType.Enum.forString(datiW9APPA.get(11).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(12))) {
					aggiudicazione.setREQUISITICOMPONENTI(RequisitiSpecialiType.Enum.forString(datiW9APPA.get(12).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(13))) {
					aggiudicazione.setDATAINTERESSE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 13)).getTime()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(14))) {
					aggiudicazione.setDATARICHIESTA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 14)).getTime()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(15))) {
					aggiudicazione.setDATAINVITO(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 15)).getTime()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(16))) {
					aggiudicazione.setDATASCADENZA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 16)).getTime()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(17))) {
					aggiudicazione.setNUMEROINTERESSE(Integer.parseInt(datiW9APPA.get(17).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(18))) {
					aggiudicazione.setNUMEROINVITATI(Integer.parseInt(datiW9APPA.get(18).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(19))) {
					aggiudicazione.setNUMEROOFFERTA(Integer.parseInt(datiW9APPA.get(19).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(20))) {
					aggiudicazione.setNUMEROAMMESSE(Integer.parseInt(datiW9APPA.get(20).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(21))) {
					aggiudicazione.setOFFERTAMAXRIBASSO(convertiPercentuale(Double.parseDouble(datiW9APPA.get(21).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(22))) {
					aggiudicazione.setOFFERTAMINRIBASSO(convertiPercentuale(Double.parseDouble(datiW9APPA.get(22).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(23))) {
					aggiudicazione.setVALORESOGLIA(convertiPercentuale(Double.parseDouble(datiW9APPA.get(23).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(24))) {
					aggiudicazione.setOFFERTESOGLIA(Integer.parseInt(datiW9APPA.get(24).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(25))) {
					aggiudicazione.setIMPRESEESCLUSE(Integer.parseInt(datiW9APPA.get(25).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(26))) {
					aggiudicazione.setIMPRESEESCLUSEINSUF(Integer.parseInt(datiW9APPA.get(26).toString()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(27))) {
					aggiudicazione.setRIBASSOAGGIUDICAZIONE(convertiPercentuale(Double.parseDouble(datiW9APPA.get(27).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(28))) {
					aggiudicazione.setOFFERTAAUMENTO(convertiPercentuale(Double.parseDouble(datiW9APPA.get(28).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(29))) {
					aggiudicazione.setDATAAGGIUDICAZIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 29)).getTime()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(30))) {
					aggiudicazione.setSUBAPPALTOPRESTAZIONE(SnType.Enum.forString(datiW9APPA.get(30).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(31))) {
					aggiudicazione.setIMPORTOSERVIZI(convertiImporto(Double.parseDouble(datiW9APPA.get(31).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(32))) {
					aggiudicazione.setIMPORTOFORNITURE(convertiImporto(Double.parseDouble(datiW9APPA.get(32).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(34))) {
					aggiudicazione.setDATASTIPULASSE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 34)).getTime()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(35))) {
					aggiudicazione.setDATATERMINESSE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 35)).getTime()));
				}
				
				if (tipoContratto.equals("L")) {
					if (aggiudicazione.getIMPORTOLAVORI() == null) {
						if (verificaEsistenzaValore(datiW9APPA.get(36))) {
							aggiudicazione.setIMPORTOLAVORI(convertiImporto(Double.parseDouble(datiW9APPA.get(36).toString())));
						}
					}
				}
				
			}
			
			if (datiPUBB != null && datiPUBB.size() > 0) {
				if (verificaEsistenzaValore(datiPUBB.get(0))) {
					aggiudicazione.setPROFILOCOMMITTENTE(SnType.Enum.forString(datiPUBB.get(0).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiPUBB.get(1))) {
					aggiudicazione.setSITOINFMI(SnType.Enum.forString(datiPUBB.get(1).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiPUBB.get(2))) {
					aggiudicazione.setSITOINFOCP(SnType.Enum.forString(datiPUBB.get(2).toString().equals("1")?"S":"N"));
				}
			}

			DIMENSIONAMENTIAGGIUDICATI dimensionamenti = aggiudicazione.addNewDIMENSIONAMENTI();
			DIMENSIONAMENTOType dimensionamento = dimensionamenti.addNewDIMENSIONAMENTO();
			dimensionamento.setTIPODIMENSIONAMENTO(TipoDimensionamentoType.X_09);
			// Lista degli incaricati
			if (escluso) {
				idfk += "987";
				setIncaricati(aggiudicazione, codgara, codlott, SQL_DATI_INCARICHI_ESCLUSO);
				// Lista dei finanziamenti
				setFinanziamenti(aggiudicazione, codgara, codlott, new Long(987));
			} else if(sottosoglia) {
				idfk += "987";
				setIncaricati(aggiudicazione, codgara, codlott, SQL_DATI_INCARICHI_SOTTOSOGLIA);
				// Lista dei finanziamenti
				setFinanziamenti(aggiudicazione, codgara, codlott, new Long(987));
			} else {
				idfk += "001";
				setIncaricati(aggiudicazione, codgara, codlott, SQL_DATI_INCARICHI_AGGIUDICAZIONE);
				// Lista dei finanziamenti
				setFinanziamenti(aggiudicazione, codgara, codlott, new Long(1));
			}
			aggiudicazione.setIDFK(idfk);
			
			setCondizioni(aggiudicazione, codgara, codlott);
			
			// Lista dei requisiti di partecipazione
			setRequisiti(aggiudicazione, codgara, codlott, Double.parseDouble(datiLOTTO.get(10).toString()));

			// Lista delle tipologie di forniture
			setTipiAppaltoForn(aggiudicazione, codgara, codlott);

			// Lista delle tipologie di forniture
			setLavori(aggiudicazione, codgara, codlott, num);
			
			if (datiAGGI != null && datiAGGI.size() > 0) {
				IMPRESE aggiudicatari = aggiudicazione.addNewIMPRESEAGGIUDICATARIE();
				ListIterator<?> iterator = datiAGGI.listIterator();

				while (iterator.hasNext()) {
					List<?> riga = (List<?>) iterator.next();
					IMPRESAType aggiudicatario = aggiudicatari.addNewIMPRESA();
					
					if (verificaEsistenzaValore(riga.get(0))) {
						aggiudicatario.setDENOMINAZIONEIMPRESA(riga.get(0).toString());
					} else if (verificaEsistenzaValore(riga.get(8))) {
						aggiudicatario.setDENOMINAZIONEIMPRESA(riga.get(8).toString());
					}
					if (verificaEsistenzaValore(riga.get(1))) {
						aggiudicatario.setCODICEFISCALEIMPRESA(riga.get(1).toString());
					}
					aggiudicatario.setNOMINATIVORL("");
					
					if (verificaEsistenzaValore(riga.get(2))) {
						String codiceFiscaleImpresaAusiliaria = (String)sqlManager.getObject(SQL_DATI_IMPRESA_AUSILIARIA, new Object[] {riga.get(2).toString()});
						if (verificaEsistenzaValore(codiceFiscaleImpresaAusiliaria)) {
							aggiudicatario.setCODFISCDITTAAUSILIARIA(codiceFiscaleImpresaAusiliaria);
						}
					}
					
					if (verificaEsistenzaValore(riga.get(3))) {
						aggiudicatario.setIDTIPOAGGIUDICATARIO(TipoAggiudicatarioType.Enum.forString(riga.get(3).toString()));
					}
					
					if (verificaEsistenzaValore(riga.get(4))) {
						aggiudicatario.setIDRUOLO(RuoloAggiudicatarioType.Enum.forString(riga.get(4).toString()));
					}
					
					if (verificaEsistenzaValore(riga.get(5))) {
						aggiudicatario.setIDGRUPPO(Integer.parseInt(riga.get(5).toString()));
					}
					
					if (verificaEsistenzaValore(riga.get(6))) {
						aggiudicatario.setAVVALIMENTOREQUISITI(SnType.Enum.forString(riga.get(6).toString().equals("1")?"S":"N"));
					}
					
					if (verificaEsistenzaValore(riga.get(7))) {
						aggiudicatario.setCODISO(StatoEsteroType.Enum.forString(getStatoEstero(riga.get(7).toString())));	
					} else {
						aggiudicatario.setCODISO(StatoEsteroType.IT);
					}

				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di aggiudicazione per contratti sopra soglia", "AggiudicazioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAggiudicazione: fine metodo");
		}
	}

	private void setAdesione(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAdesione: inizio metodo");
		}
		try {
			
			List<?> datiW9APPA = sqlManager.getVector(SQL_DATI_APPALTO, new Object[] { codgara, codlott, num });

			List<?> datiSA = sqlManager.getVector(SQL_DATI_SA, new Object[] { codgara});

			List<?> datiLOTTO = sqlManager.getVector(SQL_DATI_LOTTO, new Object[] { codgara, codlott});

			List<?> datiGARA = sqlManager.getVector(SQL_DATI_GARA, new Object[] { codgara});

			List<?> datiAGGI = sqlManager.getListVector(SQL_DATI_AGGIUDICATARI, new Object[] { codgara, codlott, num});

			FASEADESIONEType adesione = schede.addNewFASEADESIONE();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiSA != null && datiSA.size() > 0) {
				
				if (verificaEsistenzaValore(datiSA.get(0))) {
					adesione.setDENOMINAZIONESA(datiSA.get(0).toString());
				}
				if (verificaEsistenzaValore(datiSA.get(1))) {
					adesione.setCODFISCALESA(datiSA.get(1).toString());
				}
				if (verificaEsistenzaValore(datiSA.get(6))) {
					adesione.setCODICEFISCALESOGGETTOSA(datiSA.get(6).toString());
				}
			}
			
			if (datiLOTTO != null && datiLOTTO.size() > 0) {
				if (verificaEsistenzaValore(datiLOTTO.get(0))) {
					adesione.setOGGETTOCONTRATTO(datiLOTTO.get(0).toString());
				}
				if (verificaEsistenzaValore(datiLOTTO.get(1))) {
					LOCALITA localita = adesione.addNewLOCALIZZAZIONE();
					ISTATType istat = localita.addNewISTAT();
					istat.setAMBITO(AmbitoType.X_5);
					istat.setCODICE(datiLOTTO.get(1).toString());
					istat.setINDIRIZZO("-");
					adesione.setLOCALIZZAZIONE(localita);
				} 
				if (verificaEsistenzaValore(datiLOTTO.get(2))) {
					adesione.setCODICENUTS(datiLOTTO.get(2).toString());
				} 
				
				if (verificaEsistenzaValore(datiLOTTO.get(6))) {
					adesione.setCPV(convertiCPV(datiLOTTO.get(6).toString()));
				}
				
				if (verificaEsistenzaValore(datiLOTTO.get(10))) {
					adesione.setIMPORTOLOTTO(convertiImporto(Double.parseDouble(datiLOTTO.get(10).toString())));
				}
		
			}
			
			if (datiGARA != null && datiGARA.size() > 0) {
				if (verificaEsistenzaValore(datiGARA.get(3))) {
					adesione.setCIGPADRE(datiGARA.get(3).toString());
				}
			}
			
			if (datiW9APPA != null && datiW9APPA.size() > 0) {
				
				adesione.setSTRUMENTIATTUATIVI(StrumentiAttuativiType.X_2005);
				
				if (verificaEsistenzaValore(datiW9APPA.get(4))) {
					adesione.setIMPORTOLAVORI(convertiImporto(Double.parseDouble(datiW9APPA.get(4).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(5))) {
					adesione.setIMPORTOTOTALE(convertiImporto(Double.parseDouble(datiW9APPA.get(5).toString())));
				}

				if (verificaEsistenzaValore(datiW9APPA.get(27))) {
					adesione.setRIBASSOAGGIUDICAZIONE(convertiPercentuale(Double.parseDouble(datiW9APPA.get(27).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(28))) {
					adesione.setOFFERTAAUMENTO(convertiPercentuale(Double.parseDouble(datiW9APPA.get(28).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(29))) {
					adesione.setDATAAGGIUDICAZIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 29)).getTime()));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(33))) {
					adesione.setIMPORTOAGGIUDICAZIONE(convertiImporto(Double.parseDouble(datiW9APPA.get(33).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(30))) {
					adesione.setSUBAPPALTOPRESTAZIONE(SnType.Enum.forString(datiW9APPA.get(30).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(31))) {
					adesione.setIMPORTOSERVIZI(convertiImporto(Double.parseDouble(datiW9APPA.get(31).toString())));
				}
				if (verificaEsistenzaValore(datiW9APPA.get(32))) {
					adesione.setIMPORTOFORNITURE(convertiImporto(Double.parseDouble(datiW9APPA.get(32).toString())));
				}
			}
			
			setProfessionisti(adesione, codgara, codlott, SQL_DATI_INCARICHI_ADESIONE);
			
			idfk += "012";
			adesione.setIDFK(idfk);
			
			if (datiAGGI != null && datiAGGI.size() > 0) {
				IMPRESE aggiudicatari = adesione.addNewIMPRESEAGGIUDICATARIE();
				ListIterator<?> iterator = datiAGGI.listIterator();

				while (iterator.hasNext()) {
					List<?> riga = (List<?>) iterator.next();
					IMPRESAType aggiudicatario = aggiudicatari.addNewIMPRESA();
					
					if (verificaEsistenzaValore(riga.get(0))) {
						aggiudicatario.setDENOMINAZIONEIMPRESA(riga.get(0).toString());
					} else if (verificaEsistenzaValore(riga.get(8))) {
						aggiudicatario.setDENOMINAZIONEIMPRESA(riga.get(8).toString());
					}
					if (verificaEsistenzaValore(riga.get(1))) {
						aggiudicatario.setCODICEFISCALEIMPRESA(riga.get(1).toString());
					}
					aggiudicatario.setNOMINATIVORL("");
					
					if (verificaEsistenzaValore(riga.get(2))) {
						String codiceFiscaleImpresaAusiliaria = (String)sqlManager.getObject(SQL_DATI_IMPRESA_AUSILIARIA, new Object[] {riga.get(2).toString()});
						if (verificaEsistenzaValore(codiceFiscaleImpresaAusiliaria)) {
							aggiudicatario.setCODFISCDITTAAUSILIARIA(codiceFiscaleImpresaAusiliaria);
						}
					}
					
					if (verificaEsistenzaValore(riga.get(3))) {
						aggiudicatario.setIDTIPOAGGIUDICATARIO(TipoAggiudicatarioType.Enum.forString(riga.get(3).toString()));
					}
					
					if (verificaEsistenzaValore(riga.get(4))) {
						aggiudicatario.setIDRUOLO(RuoloAggiudicatarioType.Enum.forString(riga.get(4).toString()));
					}
					
					if (verificaEsistenzaValore(riga.get(5))) {
						aggiudicatario.setIDGRUPPO(Integer.parseInt(riga.get(5).toString()));
					}
					
					if (verificaEsistenzaValore(riga.get(6))) {
						aggiudicatario.setAVVALIMENTOREQUISITI(SnType.Enum.forString(riga.get(6).toString().equals("1")?"S":"N"));
					}
					
					if (verificaEsistenzaValore(riga.get(7))) {
						aggiudicatario.setCODISO(StatoEsteroType.Enum.forString(getStatoEstero(riga.get(7).toString())));	
					} else {
						aggiudicatario.setCODISO(StatoEsteroType.IT);
					}

				}
				
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di adesione", "AdesioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAdesione: fine metodo");
		}
	}
	
	private void setStipula(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setStipula: inizio metodo");
		}
		try {
			
			List<?> datiINIZ = sqlManager.getVector(SQL_DATI_INIZ, new Object[] { codgara, codlott, num});

			List<?> datiPUES = sqlManager.getVector(SQL_DATI_PUBB_ESITO, new Object[] { codgara, codlott, num});
			
			FASESTIPULAType stipula = schede.addNewFASESTIPULA();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiINIZ != null && datiINIZ.size() > 0) {

				if (verificaEsistenzaValore(datiINIZ.get(0))) {
					stipula.setDATASTIPULA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 0)).getTime()));
				}
				if (verificaEsistenzaValore(datiINIZ.get(1))) {
					stipula.setDATASCADENZA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 1)).getTime()));
				}
				
			}
			
			if (datiPUES != null && datiPUES.size() > 0) {
				if (verificaEsistenzaValore(datiPUES.get(3))) {
					stipula.setGUCE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiPUES, 3)).getTime()));
				}
				if (verificaEsistenzaValore(datiPUES.get(4))) {
					stipula.setGURI(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiPUES, 4)).getTime()));
				}
				if (verificaEsistenzaValore(datiPUES.get(5))) {
					stipula.setQUOTINAZIONALI(Integer.parseInt(datiPUES.get(5).toString()));
				}
				if (verificaEsistenzaValore(datiPUES.get(6))) {
					stipula.setQUOTILOCALI(Integer.parseInt(datiPUES.get(6).toString()));
				}
			}

			idfk += "011";
			stipula.setIDFK(idfk);
			

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di stipula", "FASESTIPULAType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setStipula: fine metodo");
		}
	}
	
	private void setInizio(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setInizio: inizio metodo");
		}
		try {
			
			List<?> datiPUES = sqlManager.getVector(SQL_DATI_PUBB_ESITO, new Object[] { codgara, codlott, num});
			
			List<?> datiINIZ = sqlManager.getVector(SQL_DATI_INIZ, new Object[] { codgara, codlott, num});

			FASEINIZIOType inizio = schede.addNewFASEINIZIO();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiINIZ != null && datiINIZ.size() > 0) {

				if (verificaEsistenzaValore(datiINIZ.get(0))) {
					inizio.setDATASTIPULA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 0)).getTime()));
				}
				if (verificaEsistenzaValore(datiINIZ.get(2))) {
					inizio.setDATAESECUTIVITA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 2)).getTime()));
				}
				if (verificaEsistenzaValore(datiINIZ.get(3))) {
					inizio.setIMPORTOCAUZIONE(convertiImporto(Double.parseDouble(datiINIZ.get(3).toString())));
				}
				if (verificaEsistenzaValore(datiINIZ.get(4))) {
					inizio.setDATADISPOSIZIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 4)).getTime()));
				}
				if (verificaEsistenzaValore(datiINIZ.get(5))) {
					inizio.setDATAAPPROVAZIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 5)).getTime()));
				}
				if (verificaEsistenzaValore(datiINIZ.get(6))) {
					inizio.setCONSEGNAFRAZIONATA(SnType.Enum.forString(datiINIZ.get(6).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiINIZ.get(7))) {
					inizio.setDATAPRIMACONSEGNA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 7)).getTime()));
				}
				if (verificaEsistenzaValore(datiINIZ.get(8))) {
					inizio.setDATADEFINITIVA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 8)).getTime()));
				}
				if (verificaEsistenzaValore(datiINIZ.get(9))) {
					inizio.setRISERVALEGGE(SnType.Enum.forString(datiINIZ.get(9).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiINIZ.get(10))) {
					inizio.setDATAEFFETTIVA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 10)).getTime()));
				}
				if (verificaEsistenzaValore(datiINIZ.get(11))) {
					inizio.setTERMINECONTRATTUALE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiINIZ, 11)).getTime()));
				}
			}
			
			if (datiPUES != null && datiPUES.size() > 0) {
				if (verificaEsistenzaValore(datiPUES.get(0))) {
					inizio.setPROFILOCOMMITTENTE(SnType.Enum.forString(datiPUES.get(0).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiPUES.get(1))) {
					inizio.setSITOINFMI(SnType.Enum.forString(datiPUES.get(1).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiPUES.get(2))) {
					inizio.setSITOINFOCP(SnType.Enum.forString(datiPUES.get(2).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiPUES.get(3))) {
					inizio.setGUCE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiPUES, 3)).getTime()));
				}
				if (verificaEsistenzaValore(datiPUES.get(4))) {
					inizio.setGURI(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiPUES, 4)).getTime()));
				}
				if (verificaEsistenzaValore(datiPUES.get(5))) {
					inizio.setQUOTINAZIONALI(Integer.parseInt(datiPUES.get(5).toString()));
				}
				if (verificaEsistenzaValore(datiPUES.get(6))) {
					inizio.setQUOTILOCALI(Integer.parseInt(datiPUES.get(6).toString()));
				}
			}

			setIncaricatiInizio(inizio, codgara, codlott, SQL_DATI_INCARICHI_INIZIO);
			
			idfk += "002";
			inizio.setIDFK(idfk);

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di inizio per contratti sopra soglia", "FASEINIZIOType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setInizio: fine metodo");
		}
	}
	
	private void setEsecuzione(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setEsecuzione: inizio metodo");
		}
		try {
			
			List<?> datiAVAN = sqlManager.getVector(SQL_DATI_AVANZAMENTO, new Object[] { codgara, codlott, num});
 
			FASEESECUZIONEType esecuzione = schede.addNewFASEESECUZIONE();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiAVAN != null && datiAVAN.size() > 0) {

				if (verificaEsistenzaValore(datiAVAN.get(0))) {
					if (Integer.parseInt(datiAVAN.get(0).toString()) == 1) {
						esecuzione.setSOMMEINDENARO(SnType.S);
						esecuzione.setTRASFERIMENTOPROPRIETA(SnType.N);
					} else if (Integer.parseInt(datiAVAN.get(0).toString()) == 2) {
						esecuzione.setSOMMEINDENARO(SnType.N);
						esecuzione.setTRASFERIMENTOPROPRIETA(SnType.S);
					} else if (Integer.parseInt(datiAVAN.get(0).toString()) == 3) {
						esecuzione.setSOMMEINDENARO(SnType.S);
						esecuzione.setTRASFERIMENTOPROPRIETA(SnType.S);
					}
				} else {
					esecuzione.setSOMMEINDENARO(SnType.N);
					esecuzione.setTRASFERIMENTOPROPRIETA(SnType.N);
				}
				
				esecuzione.setNUMEROSTATOAVANZAMENTO(num);
				
				if (verificaEsistenzaValore(datiAVAN.get(1))) {
					esecuzione.setANTICIPAZIONE(convertiImporto(Double.parseDouble(datiAVAN.get(1).toString())));
				}
				
				if (verificaEsistenzaValore(datiAVAN.get(2))) {
					esecuzione.setDATAPAGAMENTO(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiAVAN, 2)).getTime()));
				}
				
				if (verificaEsistenzaValore(datiAVAN.get(3))) {
					esecuzione.setDATASAL(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiAVAN, 3)).getTime()));
				}
				
				if (verificaEsistenzaValore(datiAVAN.get(4))) {
					esecuzione.setIMPORTOSAL(convertiImporto(Double.parseDouble(datiAVAN.get(4).toString())));
				}
				
				if (verificaEsistenzaValore(datiAVAN.get(5))) {
					esecuzione.setIMPORTOCERTIFICATO(convertiImporto(Double.parseDouble(datiAVAN.get(5).toString())));
				}
				
				if (verificaEsistenzaValore(datiAVAN.get(6))) {
					if (datiAVAN.get(6).toString().equals("A")) {
						esecuzione.setAVANZAMENTORAGGIUNTO(AvanzamentoType.X_2);
					} else if (datiAVAN.get(6).toString().equals("P")) {
						esecuzione.setAVANZAMENTORAGGIUNTO(AvanzamentoType.X_3);
					} else if (datiAVAN.get(6).toString().equals("R")) {
						esecuzione.setAVANZAMENTORAGGIUNTO(AvanzamentoType.X_1);
					}
				}
				
				if (verificaEsistenzaValore(datiAVAN.get(7))) {
					esecuzione.setSCOSTAMENTO(Integer.parseInt(datiAVAN.get(7).toString()));
				} else {
					esecuzione.setSCOSTAMENTO(0);
				}
				
				if (verificaEsistenzaValore(datiAVAN.get(8))) {
					esecuzione.setPROROGA(Integer.parseInt(datiAVAN.get(8).toString()));
				} else {
					esecuzione.setPROROGA(0);
				}
			}
			
			idfk += "003";
			esecuzione.setIDFK(idfk);

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di inizio per contratti sopra soglia", "FASEINIZIOType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setInizio: fine metodo");
		}
	}

	
	private void setConclusione(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setConclusione: inizio metodo");
		}
		try {
			
			List<?> datiCONC = sqlManager.getVector(SQL_DATI_CONCLUSIONE, new Object[] { codgara, codlott, num});
 
			FASECONCLUSIONEType conclusione = schede.addNewFASECONCLUSIONE();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiCONC != null && datiCONC.size() > 0) {

				if (verificaEsistenzaValore(datiCONC.get(0))) {
					conclusione.setIDMOTIVOINTERRUZIONE(MotivoInterruzioneType.Enum.forString(datiCONC.get(0).toString()));
				} 
				if (verificaEsistenzaValore(datiCONC.get(1))) {
					conclusione.setIDMOTIVORISOLUZIONE(MotivoRisoluzioneType.Enum.forString(datiCONC.get(1).toString()));
				}
				if (verificaEsistenzaValore(datiCONC.get(2))) {
					conclusione.setDATARISOLRECESSO(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCONC, 2)).getTime()));
				}
				if (verificaEsistenzaValore(datiCONC.get(3))) {
					ONERI oneri = conclusione.addNewONERICONCLUSIONE();
					ONEREType onere = oneri.addNewONERE();
					if (datiCONC.get(3).toString().equals("0")) {
						onere.setIDONERE(MotivoOnereType.X_1);
					} else if (datiCONC.get(3).toString().equals("1")) {
						onere.setIDONERE(MotivoOnereType.X_2);
					} else if (datiCONC.get(3).toString().equals("2")) {
						onere.setIDONERE(MotivoOnereType.X_3);
					}
				}
				
				if (verificaEsistenzaValore(datiCONC.get(4))) {
					conclusione.setIMPORTO(convertiImporto(Double.parseDouble(datiCONC.get(4).toString())));
				}
				if (verificaEsistenzaValore(datiCONC.get(5))) {
					conclusione.setPOLIZZA(SnType.Enum.forString(datiCONC.get(5).toString().equals("1")?"S":"N"));
				} else {
					conclusione.setPOLIZZA(SnType.N);
				}
				if (verificaEsistenzaValore(datiCONC.get(6))) {
					conclusione.setDATACONSEGNA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCONC, 6)).getTime()));
				}
				if (verificaEsistenzaValore(datiCONC.get(7))) {
					conclusione.setTERMINECONTRATTUALEULTIMAZIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCONC, 7)).getTime()));
				}
				if (verificaEsistenzaValore(datiCONC.get(8))) {
					conclusione.setDATAULTIMAZIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCONC, 8)).getTime()));
				}
				if (verificaEsistenzaValore(datiCONC.get(9))) {
					conclusione.setNUMEROINFORTUNI(Long.parseLong(datiCONC.get(9).toString()));
				}
				if (verificaEsistenzaValore(datiCONC.get(10))) {
					conclusione.setNUMEROINFORTPOSTUMI(Long.parseLong(datiCONC.get(10).toString()));
				}
				if (verificaEsistenzaValore(datiCONC.get(11))) {
					conclusione.setNUMEROINFORTMORTALI(Long.parseLong(datiCONC.get(11).toString()));
				}

			}
			
			idfk += "004";
			conclusione.setIDFK(idfk);

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di conclusione", "FASECONCLUSIONEType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setConclusione: fine metodo");
		}
	}
	
	private void setCollaudo(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setCollaudo: inizio metodo");
		}
		try {
			
			List<?> datiCOLL = sqlManager.getVector(SQL_DATI_COLLAUDO, new Object[] { codgara, codlott, num});
			
			FASECOLLAUDOType collaudo = schede.addNewFASECOLLAUDO();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiCOLL != null && datiCOLL.size() > 0) {

				if (verificaEsistenzaValore(datiCOLL.get(0))) {
					collaudo.setDATASTATICO(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCOLL, 0)).getTime()));
				}
				if (verificaEsistenzaValore(datiCOLL.get(1))) {
					collaudo.setDATACERTIFICATO(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCOLL, 1)).getTime()));
				}
				if (verificaEsistenzaValore(datiCOLL.get(2))) {
					collaudo.setMODALITACOLLAUDO(ModalitaCollaudoType.Enum.forString(datiCOLL.get(2).toString()));
				} 
				if (verificaEsistenzaValore(datiCOLL.get(3))) {
					collaudo.setDATACOLLAUDATORE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCOLL, 3)).getTime()));
				}
				if (verificaEsistenzaValore(datiCOLL.get(4))) {
					collaudo.setDATAINIZIO(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCOLL, 4)).getTime()));
				}
				if (verificaEsistenzaValore(datiCOLL.get(5))) {
					collaudo.setDATAREDAZIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCOLL, 5)).getTime()));
				}
				if (verificaEsistenzaValore(datiCOLL.get(6))) {
					collaudo.setDATADELIBERA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiCOLL, 6)).getTime()));
				}
				if (verificaEsistenzaValore(datiCOLL.get(7))) {
					if (datiCOLL.get(7).toString().equals("P")) {
						collaudo.setESITOCOLLAUDO(EsitoCollaudoType.X_1);
					} else {
						collaudo.setESITOCOLLAUDO(EsitoCollaudoType.X_2);
					}
				}
				if (verificaEsistenzaValore(datiCOLL.get(8))) {
					collaudo.setIMPORTOLAVORI(convertiImporto(Double.parseDouble(datiCOLL.get(8).toString())));
				}
				if (verificaEsistenzaValore(datiCOLL.get(9))) {
					collaudo.setIMPORTOSERVIZI(convertiImporto(Double.parseDouble(datiCOLL.get(9).toString())));
				}
				if (verificaEsistenzaValore(datiCOLL.get(10))) {
					collaudo.setIMPORTOFORNITURE(convertiImporto(Double.parseDouble(datiCOLL.get(10).toString())));
				}
				if (verificaEsistenzaValore(datiCOLL.get(11))) {
					collaudo.setIMPORTOPROGETTAZIONE(convertiImporto(Double.parseDouble(datiCOLL.get(11).toString())));
				}
				if (verificaEsistenzaValore(datiCOLL.get(12))) {
					collaudo.setIMPORTOSOMMEDISPOSIZIONE(convertiImporto(Double.parseDouble(datiCOLL.get(12).toString())));
				}
				if (verificaEsistenzaValore(datiCOLL.get(13))) {
					collaudo.setLAVORIANNUALIESTESI(SnType.Enum.forString(datiCOLL.get(13).toString().equals("1")?"S":"N"));
				}
				
				long totaleRiserve = 0;
				
				if (verificaEsistenzaValore(datiCOLL.get(14))) {
					totaleRiserve += Long.parseLong(datiCOLL.get(14).toString());
				}
				if (verificaEsistenzaValore(datiCOLL.get(18))) {
					totaleRiserve += Long.parseLong(datiCOLL.get(18).toString());
				}
				if (verificaEsistenzaValore(datiCOLL.get(22))) {
					totaleRiserve += Long.parseLong(datiCOLL.get(22).toString());
				}
				if (verificaEsistenzaValore(datiCOLL.get(26))) {
					totaleRiserve += Long.parseLong(datiCOLL.get(26).toString());
				}
				collaudo.setTOTALERISERVE(totaleRiserve);
				
				if (verificaEsistenzaValore(datiCOLL.get(30))) {
					collaudo.setONERICOMPLESSIVI(convertiImporto(Double.parseDouble(datiCOLL.get(30).toString())));
				}
				
				if (verificaEsistenzaValore(datiCOLL.get(31))) {
					collaudo.setIMPORTOSICUREZZA(convertiImporto(Double.parseDouble(datiCOLL.get(31).toString())));
				} else {
					collaudo.setIMPORTOSICUREZZA(convertiImporto((double)0));
				}
				
				if (verificaEsistenzaValore(datiCOLL.get(32))) {
					collaudo.setIMPORTOPROGETTAZIONE(convertiImporto(Double.parseDouble(datiCOLL.get(32).toString())));
				} else {
					collaudo.setIMPORTOPROGETTAZIONE(convertiImporto((double)0));
				}

				boolean existRiserve = false;
				RISERVECONTENZIOSO riserve = collaudo.addNewMODALITADEFINIZIONE();
				if (verificaEsistenzaValore(datiCOLL.get(14)) || verificaEsistenzaValore(datiCOLL.get(15))) {
					existRiserve = true;
					RISERVAType amm = riserve.addNewRISERVE();
					amm.setIDDEFINIZIONE(RiserveType.X_1);
					if (verificaEsistenzaValore(datiCOLL.get(14))) {
						amm.setNUMERODEFINITE(Long.parseLong(datiCOLL.get(14).toString()));
					}
					if (verificaEsistenzaValore(datiCOLL.get(15))) {
						amm.setNUMERODADEFINIRE(Long.parseLong(datiCOLL.get(15).toString()));
					}
					if (verificaEsistenzaValore(datiCOLL.get(16))) {
						amm.setIMPORTOTOTALE(convertiImporto(Double.parseDouble(datiCOLL.get(16).toString())));
					}
					if (verificaEsistenzaValore(datiCOLL.get(17))) {
						amm.setIMPORTODEFINIZIONE(convertiImporto(Double.parseDouble(datiCOLL.get(17).toString())));
					}
				}
				if (verificaEsistenzaValore(datiCOLL.get(18)) || verificaEsistenzaValore(datiCOLL.get(19))) {
					existRiserve = true;
					RISERVAType arb = riserve.addNewRISERVE();
					arb.setIDDEFINIZIONE(RiserveType.X_2);
					if (verificaEsistenzaValore(datiCOLL.get(18))) {
						arb.setNUMERODEFINITE(Long.parseLong(datiCOLL.get(18).toString()));
					}
					if (verificaEsistenzaValore(datiCOLL.get(19))) {
						arb.setNUMERODADEFINIRE(Long.parseLong(datiCOLL.get(19).toString()));
					}
					if (verificaEsistenzaValore(datiCOLL.get(20))) {
						arb.setIMPORTOTOTALE(convertiImporto(Double.parseDouble(datiCOLL.get(20).toString())));
					}
					if (verificaEsistenzaValore(datiCOLL.get(21))) {
						arb.setIMPORTODEFINIZIONE(convertiImporto(Double.parseDouble(datiCOLL.get(21).toString())));
					}
				}
				if (verificaEsistenzaValore(datiCOLL.get(22)) || verificaEsistenzaValore(datiCOLL.get(23))) {
					existRiserve = true;
					RISERVAType giu = riserve.addNewRISERVE();
					giu.setIDDEFINIZIONE(RiserveType.X_3);
					if (verificaEsistenzaValore(datiCOLL.get(22))) {
						giu.setNUMERODEFINITE(Long.parseLong(datiCOLL.get(22).toString()));
					}
					if (verificaEsistenzaValore(datiCOLL.get(23))) {
						giu.setNUMERODADEFINIRE(Long.parseLong(datiCOLL.get(23).toString()));
					}
					if (verificaEsistenzaValore(datiCOLL.get(24))) {
						giu.setIMPORTOTOTALE(convertiImporto(Double.parseDouble(datiCOLL.get(24).toString())));
					}
					if (verificaEsistenzaValore(datiCOLL.get(25))) {
						giu.setIMPORTODEFINIZIONE(convertiImporto(Double.parseDouble(datiCOLL.get(25).toString())));
					}
				}
				if (verificaEsistenzaValore(datiCOLL.get(26)) || verificaEsistenzaValore(datiCOLL.get(27))) {
					existRiserve = true;
					RISERVAType tra = riserve.addNewRISERVE();
					tra.setIDDEFINIZIONE(RiserveType.X_4);
					if (verificaEsistenzaValore(datiCOLL.get(26))) {
						tra.setNUMERODEFINITE(Long.parseLong(datiCOLL.get(26).toString()));
					}
					if (verificaEsistenzaValore(datiCOLL.get(27))) {
						tra.setNUMERODADEFINIRE(Long.parseLong(datiCOLL.get(27).toString()));
					}
					if (verificaEsistenzaValore(datiCOLL.get(28))) {
						tra.setIMPORTOTOTALE(convertiImporto(Double.parseDouble(datiCOLL.get(28).toString())));
					}
					if (verificaEsistenzaValore(datiCOLL.get(29))) {
						tra.setIMPORTODEFINIZIONE(convertiImporto(Double.parseDouble(datiCOLL.get(29).toString())));
					}
				}
				
				if (!existRiserve) {
					collaudo.unsetMODALITADEFINIZIONE();
				}
				
				setIncaricatiCollaudo(collaudo, codgara, codlott, SQL_DATI_INCARICHI_COLLAUDO);

			}
			
			idfk += "005";
			collaudo.setIDFK(idfk);

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di collaudo", "FASECOLLAUDOType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setCollaudo: fine metodo");
		}
	}
	
	private void setSospensione(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSospensione: inizio metodo");
		}
		try {
			
			List<?> datiSOSP = sqlManager.getVector(SQL_DATI_SOSPENSIONE, new Object[] { codgara, codlott, num});
 
			SOSPENSIONEType sospensione = schede.addNewEVENTOSOSPENSIONE();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiSOSP != null && datiSOSP.size() > 0) {

				if (verificaEsistenzaValore(datiSOSP.get(0))) {
					sospensione.setDATASOSPENSIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiSOSP, 0)).getTime()));
				} 
				if (verificaEsistenzaValore(datiSOSP.get(1))) {
					sospensione.setDATARIPRESA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiSOSP, 1)).getTime()));
				} 
				if (verificaEsistenzaValore(datiSOSP.get(2))) {
					sospensione.setMOTIVOSOSPENSIONE(MotivoSospensioneType.Enum.forString(datiSOSP.get(2).toString()));
				}
				if (verificaEsistenzaValore(datiSOSP.get(3))) {
					sospensione.setRECUPERO(SnType.Enum.forString(datiSOSP.get(3).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiSOSP.get(4))) {
					sospensione.setRISERVE(SnType.Enum.forString(datiSOSP.get(4).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiSOSP.get(5))) {
					sospensione.setVERBALI(SnType.Enum.forString(datiSOSP.get(5).toString().equals("1")?"S":"N"));
				}
			}
			
			idfk += "006";
			sospensione.setIDFK(idfk);

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di sospensione", "SOSPENSIONEType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setSospensione: fine metodo");
		}
	}
	
	private void setVariante(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setVariante: inizio metodo");
		}
		try {
			
			List<?> datiVARI = sqlManager.getVector(SQL_DATI_VARIANTE, new Object[] { codgara, codlott, num});
 
			VARIANTEType variante = schede.addNewEVENTOVARIANTE();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiVARI != null && datiVARI.size() > 0) {

				if (verificaEsistenzaValore(datiVARI.get(0))) {
					variante.setDATAAPPROVAZIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiVARI, 0)).getTime()));
				} 
				if (verificaEsistenzaValore(datiVARI.get(1))) {
					String valoreRLO = this.getCodiceSITATtoRLO("W3017", datiVARI.get(1).toString());
					variante.setMOTIVAZIONE(MotivoVarianteType.Enum.forString(valoreRLO));
				}
				if (verificaEsistenzaValore(datiVARI.get(2))) {
					variante.setALTREMOTIVAZIONI(datiVARI.get(2).toString());
				}
				if (verificaEsistenzaValore(datiVARI.get(3))) {
					variante.setIMPORTOLAVORI(convertiImporto(Double.parseDouble(datiVARI.get(3).toString())));
				}
				if (verificaEsistenzaValore(datiVARI.get(4))) {
					variante.setIMPORTOSERVIZI(convertiImporto(Double.parseDouble(datiVARI.get(4).toString())));
				}
				if (verificaEsistenzaValore(datiVARI.get(5))) {
					variante.setIMPORTOFORNITURE(convertiImporto(Double.parseDouble(datiVARI.get(5).toString())));
				}
				if (verificaEsistenzaValore(datiVARI.get(6))) {
					variante.setDATASOTTOSCRIZIONE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiVARI, 6)).getTime()));
				}
			}
			
			idfk += "007";
			variante.setIDFK(idfk);

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di variante", "VARIANTEType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setVariante: fine metodo");
		}
	}
	
	private void setAccordoBonario(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAccordoBonario: inizio metodo");
		}
		try {
			
			List<?> datiACCO = sqlManager.getVector(SQL_DATI_ACCORDO, new Object[] { codgara, codlott, num});
 
			ACCORDOBONARIOType accordo = schede.addNewEVENTOACCORDOBONARIO();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiACCO != null && datiACCO.size() > 0) {

				if (verificaEsistenzaValore(datiACCO.get(0))) {
					accordo.setDATAACCORDO(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiACCO, 0)).getTime()));
				} 
				if (verificaEsistenzaValore(datiACCO.get(1))) {
					accordo.setONERI(convertiImporto(Double.parseDouble(datiACCO.get(1).toString())));
				}
				if (verificaEsistenzaValore(datiACCO.get(2))) {
					accordo.setRISERVE(Long.parseLong(datiACCO.get(2).toString()));
				}
			}
			
			idfk += "008";
			accordo.setIDFK(idfk);

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di accordo quadro", "ACCORDOBONARIOType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAccordoBonario: fine metodo");
		}
	}
	
	private void setSubappalto(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSubappalto: inizio metodo");
		}
		try {
			
			List<?> datiSUBA = sqlManager.getVector(SQL_DATI_SUBAPPALTO, new Object[] { codgara, codlott, num});
 
			EVENTOSUBAPPALTOType subappalto = schede.addNewEVENTOSUBAPPALTO();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiSUBA != null && datiSUBA.size() > 0) {

				if (verificaEsistenzaValore(datiSUBA.get(0))) {
					String codiceFiscaleImpresa = (String)sqlManager.getObject(SQL_DATI_IMPRESA_AUSILIARIA, new Object[] {datiSUBA.get(0).toString()});
					if (verificaEsistenzaValore(codiceFiscaleImpresa)) {
						subappalto.setCODFISCDITTA(codiceFiscaleImpresa);
					}
				} 
				if (verificaEsistenzaValore(datiSUBA.get(1))) {
					String codiceFiscaleImpresa = (String)sqlManager.getObject(SQL_DATI_IMPRESA_AUSILIARIA, new Object[] {datiSUBA.get(1).toString()});
					if (verificaEsistenzaValore(codiceFiscaleImpresa)) {
						subappalto.setCODFISCDITTAAGGIUD(codiceFiscaleImpresa);
					}
				}
				if (verificaEsistenzaValore(datiSUBA.get(2))) {
					subappalto.setIMPORTOPRESUNTO(convertiImporto(Double.parseDouble(datiSUBA.get(2).toString())));
				}
				
				if (verificaEsistenzaValore(datiSUBA.get(3))) {
					String categoriaSimog = UtilitySITAT.getCategoriaSIMOG(this.sqlManager, datiSUBA.get(3).toString());
					subappalto.setCODICECATEGORIA(CategoriaType2.Enum.forString(categoriaSimog));
				}
				
				if (verificaEsistenzaValore(datiSUBA.get(4))) {
					subappalto.setCODICECPV(convertiCPV(datiSUBA.get(4).toString()));
				}
				
			}
			
			idfk += "009";
			subappalto.setIDFK(idfk);

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di subappalto", "EVENTOSUBAPPALTOType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setSubappalto: fine metodo");
		}
	}
	
	private void setRecesso(final SCHEDECONTRATTO schede, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setRecesso: inizio metodo");
		}
		try {
			
			List<?> datiRECE = sqlManager.getVector(SQL_DATI_RECESSO, new Object[] { codgara, codlott, num});
 
			EVENTOR129Type recesso = schede.addNewEVENTOR129();
			
			String idfk = StringUtils.leftPad(codgara.toString(), 7, '0') + StringUtils.leftPad(codlott.toString(), 4, '0') + StringUtils.leftPad(num.toString(), 3, '0');
			
			if (datiRECE != null && datiRECE.size() > 0) {

				if (verificaEsistenzaValore(datiRECE.get(0))) {
					recesso.setDATATERMINE(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiRECE, 0)).getTime()));
				} 
				if (verificaEsistenzaValore(datiRECE.get(1))) {
					recesso.setDATACONSEGNA(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiRECE, 1)).getTime()));
				} 
				if (verificaEsistenzaValore(datiRECE.get(2))) {
					if (datiRECE.get(2).equals("R")) {
						recesso.setIDTIPOLOGIACOMUNICAZIONE(TipologiaComunicazioneType.X_1);
					} else {
						recesso.setIDTIPOLOGIACOMUNICAZIONE(TipologiaComunicazioneType.X_2);
					}
				}
				if (verificaEsistenzaValore(datiRECE.get(3))) {
					recesso.setDATARECESSO(df.format(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiRECE, 3)).getTime()));
				} 
				if (verificaEsistenzaValore(datiRECE.get(4))) {
					recesso.setRECESSO(SnType.Enum.forString(datiRECE.get(4).toString().equals("1")?"S":"N"));
				}
				if (verificaEsistenzaValore(datiRECE.get(5))) {
					recesso.setRISERVE(SnType.Enum.forString(datiRECE.get(5).toString().equals("1")?"S":"N"));
				}
			}
			
			idfk += "010";
			recesso.setIDFK(idfk);

		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di recesso", "EVENTOR129Type", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setRecesso: fine metodo");
		}
	}
	/**
	 * Gestione dell'archivio dei responsabili indicati nelle varie fasi
	 * dell'elemento SchedaCompleta.
	 * 
	 * @param responsabili
	 *            ResponsabilitYpe
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAnagraficaResponsabili(ResponsabiliType responsabili, Long codgara, Long codlott, final Long fase_esecuzione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAnagraficaResponsabili: inizio metodo");
		}
		try {
			List<?> datiTECNI = null;

			boolean esistonoResponsabili = false;
			switch (fase_esecuzione.intValue()) {
			case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
			case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
			case CostantiW9.ADESIONE_ACCORDO_QUADRO:
			case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
			case CostantiW9.COLLAUDO_CONTRATTO:
				esistonoResponsabili = true;
				break;
			}
			
			if (esistonoResponsabili) {
				String selectTECNI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "TECNI");
				datiTECNI = sqlManager.getListVector(selectTECNI, new Object[] { codgara, codlott });

				if (datiTECNI != null && datiTECNI.size() > 0) {
					ListIterator<?> iterator = datiTECNI.listIterator();
					while (iterator.hasNext()) {

						List<?> riga = (List<?>) iterator.next();
						//ResponsabileType responsabile = ResponsabileType.Factory.newInstance();
						ResponsabileType responsabile = responsabili.addNewResponsabile();
						if (verificaEsistenzaValore(riga.get(0))) {
							String cfResponsabile = SqlManager.getValueFromVectorParam(riga, 0).getStringValue();
							if (cfResponsabile.indexOf("\"") >= 0) {
								cfResponsabile = StringUtils.replace(cfResponsabile, "\"", "''");
							}
							responsabile.setCODICEFISCALERESPONSABILE(cfResponsabile);
							responsabile.setCODICEFISCALE(cfResponsabile);
						}

						if (verificaEsistenzaValore(riga.get(1))) {
							String cognome = SqlManager.getValueFromVectorParam(riga, 1).getStringValue();
							if (cognome.length() > 50)
								cognome = cognome.substring(0, 50);
							if (cognome.indexOf("\"") >= 0) {
								cognome = StringUtils.replace(cognome, "\"", "''");
							}
							responsabile.setCOGNOME(cognome);
						}

						if (verificaEsistenzaValore(riga.get(2))) {
							String nome = SqlManager.getValueFromVectorParam(riga, 2).getStringValue();
							if (nome.length() > 50)
								nome = nome.substring(0, 50);
							if (nome.indexOf("\"") >= 0) {
								nome = StringUtils.replace(nome, "\"", "''");
							}
							responsabile.setNOME(nome);
						}

						if (verificaEsistenzaValore(riga.get(3))) {
							String telefono = SqlManager.getValueFromVectorParam(riga, 3).getStringValue();
							if (telefono.length() > 20)
								telefono = telefono.substring(0, 20);
							if (telefono.indexOf("\"") >= 0) {
								telefono = StringUtils.replace(telefono, "\"", "''");
							}
							responsabile.setTELEFONO(telefono);
						}

						if (verificaEsistenzaValore(riga.get(4))) {
							String email = SqlManager.getValueFromVectorParam(riga, 4).getStringValue();
							if (email.length() > 64)
								email = email.substring(0, 64);
							if (email.indexOf("\"") >= 0) {
								email = StringUtils.replace(email, "\"", "''");
							}
							responsabile.setMAIL(email);
						}

						if (verificaEsistenzaValore(riga.get(5))) {
							String fax = SqlManager.getValueFromVectorParam(riga, 5).getStringValue();
							if (fax.length() > 20)
								fax = fax.substring(0, 20);
							if (fax.indexOf("\"") >= 0) {
								fax = StringUtils.replace(fax, "\"", "''");
							}
							responsabile.setFAX(fax);
						}
						// Gestione dell'indirizzo
						String indirizzo = "";
						if (verificaEsistenzaValore(riga.get(6))) {
							indirizzo += SqlManager.getValueFromVectorParam(riga, 6).getStringValue();
						}
						if (verificaEsistenzaValore(riga.get(7))) {
							indirizzo += " - " + SqlManager.getValueFromVectorParam(riga, 7).getStringValue();
						}
						if (verificaEsistenzaValore(riga.get(8))) {
							indirizzo += ", " + SqlManager.getValueFromVectorParam(riga, 8).getStringValue();
						}

						if (!"".equals(indirizzo)) {
							if (indirizzo.length() > 100)
								indirizzo = indirizzo.substring(0, 100);
							if (indirizzo.indexOf("\"") >= 0) {
								indirizzo = StringUtils.replace(indirizzo, "\"", "''");
							}
							responsabile.setINDIRIZZO(indirizzo);
						}

						if (verificaEsistenzaValore(riga.get(9))) {
							responsabile.setCAP(SqlManager.getValueFromVectorParam(riga, 9).getStringValue());
						}

					}
				}
			}
			
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati dei responsabili", "IncaricatoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAnagraficaResponsabili: fine metodo");
		}
	}

	/**
	 * Gestione dell'archivio delle ditte indicate come aggiudicatarie o
	 * subappaltatrici nelle varie fasi dell'elemento SchedaCompleta.
	 * 
	 * @param aggiudicatari
	 *            AggiudicatariType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAnagraficaAggiudicatari(AggiudicatariType aggiudicatari, Long codgara, Long codlott, final Long fase_esecuzione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAnagraficaAggiudicatari: inizio metodo");
		}
		try {
			List<?> datiIMPR = null;

			boolean esistonoAggiudicatari = false;
			switch (fase_esecuzione.intValue()) {
			case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
			case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
			case CostantiW9.ADESIONE_ACCORDO_QUADRO:
			case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
				esistonoAggiudicatari = true;
				break;
			}
			
			if (esistonoAggiudicatari) {
				String selectIMPR = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "IMPR");
				datiIMPR = sqlManager.getListVector(selectIMPR, new Object[] { codgara, codlott });

				if (datiIMPR != null && datiIMPR.size() > 0) {
					ListIterator<?> iterator = datiIMPR.listIterator();
					
					while (iterator.hasNext()) {
					
						List<?> riga = (List<?>) iterator.next();
						AggiudicatarioType aggiudicatario = aggiudicatari.addNewAggiudicatario();
						String cfImp = null;
						
						if (verificaEsistenzaValore(riga.get(0))) {
							cfImp = SqlManager.getValueFromVectorParam(riga, 0).getStringValue();
							aggiudicatario.setCODICEFISCALEIMPRESA(cfImp);
						}

						String denominazione = SqlManager.getValueFromVectorParam(riga, 1).getStringValue();
						if (denominazione.length() > 250) {
							denominazione = denominazione.substring(0, 250);
						}
						if (denominazione.indexOf("\"") >= 0) {
							denominazione = StringUtils.replace(denominazione, "\"", "''");
						}
						aggiudicatario.setDENOMINAZIONE(denominazione);

						
						// IMPR_LEGALIRAPPRESENTANTI			
						if (cfImp != null) {
							List<?> datiIMPR_LEGALIRAPPRESENTATI = null;

							String selectIMPR_LEGALIRAPPRESENTATI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "IMPR_LEGALIRAPPRESENTANTI");
							datiIMPR_LEGALIRAPPRESENTATI = sqlManager.getListVector(selectIMPR_LEGALIRAPPRESENTATI, new Object[] { codgara, codlott, cfImp });

							if (datiIMPR_LEGALIRAPPRESENTATI != null && datiIMPR_LEGALIRAPPRESENTATI.size() > 0) {
								ListIterator<?> iterator_LR = datiIMPR_LEGALIRAPPRESENTATI.listIterator();
								List<?> riga_LR = (List<?>) iterator_LR.next();
								// nel caso di piu' legali rappresentanti con data di scadenza del mandato nulla scelgo il primo estratto
								
								// COGNOME
								if (SqlManager.getValueFromVectorParam(riga_LR, 0) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 0).getStringValue())) {
									String cognome = SqlManager.getValueFromVectorParam(riga_LR, 0).getStringValue();
									if (cognome.indexOf("\"") >= 0) {
										cognome = StringUtils.replace(cognome, "\"", "''");
									}
									aggiudicatario.setCOGNOME(cognome);
								}
								
								// NOME
								if (SqlManager.getValueFromVectorParam(riga_LR, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 1).getStringValue())) {
									String nome = SqlManager.getValueFromVectorParam(riga_LR, 1).getStringValue();
									if (nome.indexOf("\"") >= 0) {
										nome = StringUtils.replace(nome, "\"", "''");
									}
									aggiudicatario.setNOME(nome);
								}
								
								// CF LEGALE RAPPRESENTANTE
								if (SqlManager.getValueFromVectorParam(riga_LR, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 2).getStringValue())) {
									String cfRappresentante = SqlManager.getValueFromVectorParam(riga_LR, 2).getStringValue();
									if (cfRappresentante.indexOf("\"") >= 0) {
										cfRappresentante = StringUtils.replace(cfRappresentante, "\"", "''");
									}
									aggiudicatario.setCODICEFISCALERL(cfRappresentante);
								}									
							} else {
								// nel caso di nessun legale rappresentante con data di scadenza del mandato nulla seleziono tra queli con data a termine impostata
								selectIMPR_LEGALIRAPPRESENTATI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "IMPR_LEGALIRAPPRESENTANTI_DISPONIBILI");
								datiIMPR_LEGALIRAPPRESENTATI = sqlManager.getListVector(selectIMPR_LEGALIRAPPRESENTATI, new Object[] { codgara, codlott, cfImp });

								if (datiIMPR_LEGALIRAPPRESENTATI != null && datiIMPR_LEGALIRAPPRESENTATI.size() > 0) {
									// la query estrarra' il rappresentante con data di fine mandato superiore
									ListIterator<?> iterator_LR = datiIMPR_LEGALIRAPPRESENTATI.listIterator();
									List<?> riga_LR = (List<?>) iterator_LR.next();
									
									// COGNOME
									if (SqlManager.getValueFromVectorParam(riga_LR, 0) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 0).getStringValue())) {
										String cognome = SqlManager.getValueFromVectorParam(riga_LR, 0).getStringValue();
										if (cognome.indexOf("\"") >= 0) {
											cognome = StringUtils.replace(cognome, "\"", "''");
										}
										aggiudicatario.setCOGNOME(cognome);
									}
									
									// NOME
									if (SqlManager.getValueFromVectorParam(riga_LR, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 1).getStringValue())) {
										String nome = SqlManager.getValueFromVectorParam(riga_LR, 1).getStringValue();
										if (nome.indexOf("\"") >= 0) {
											nome = StringUtils.replace(nome, "\"", "''");
										}
										aggiudicatario.setNOME(nome);
									}
									
									// CF LEGALE RAPPRESENTANTE
									if (SqlManager.getValueFromVectorParam(riga_LR, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 2).getStringValue())) {
										String cfRappresentante = SqlManager.getValueFromVectorParam(riga_LR, 2).getStringValue();
										if (cfRappresentante.indexOf("\"") >= 0) {
											cfRappresentante = StringUtils.replace(cfRappresentante, "\"", "''");
										}
										aggiudicatario.setCODICEFISCALERL(cfRappresentante);
									}									
								}
							}
						}

						if (verificaEsistenzaValore(riga.get(2))) {
							String cameraCommercio = SqlManager.getValueFromVectorParam(riga, 2).getStringValue();
							if (cameraCommercio.length() > 50) {
								cameraCommercio = cameraCommercio.substring(0, 50);
							}
							if (cameraCommercio.indexOf("\"") >= 0) {
								cameraCommercio = StringUtils.replace(cameraCommercio, "\"", "''");
							}
							aggiudicatario.setCAMERACOMMERCIO(cameraCommercio);
						}

						if (verificaEsistenzaValore(riga.get(3))) {
							String pIva = SqlManager.getValueFromVectorParam(riga, 3).getStringValue();
							if (pIva.indexOf("\"") >= 0) {
								pIva = StringUtils.replace(pIva, "\"", "''");
							}
							aggiudicatario.setPARTITAIVA(pIva);
						}

						if (verificaEsistenzaValore(riga.get(4))) {
							String indirizzo = SqlManager.getValueFromVectorParam(riga, 4).getStringValue();
							if (indirizzo.length() > 50) {
								indirizzo = indirizzo.substring(0, 50);
							}
							if (indirizzo.indexOf("\"") >= 0) {
								indirizzo = StringUtils.replace(indirizzo, "\"", "''");
							}
							aggiudicatario.setINDIRIZZO(indirizzo);
						}

						if (verificaEsistenzaValore(riga.get(6))) {
							aggiudicatario.setCAP(SqlManager.getValueFromVectorParam(riga, 6).getStringValue());
						}

						if (verificaEsistenzaValore(riga.get(7))) {
							String citta = SqlManager.getValueFromVectorParam(riga, 7).getStringValue();
							if (citta.length() > 50)
								citta = citta.substring(0, 50);
							if (citta.indexOf("\"") >= 0) {
								citta = StringUtils.replace(citta, "\"", "''");
							}
							aggiudicatario.setCITTA(citta);
						}

						if (verificaEsistenzaValore(riga.get(8))) {
							aggiudicatario.setPROVINCIA(SqlManager.getValueFromVectorParam(riga, 8).getStringValue().toUpperCase());
						}

						// Gestione della nazione
						if (verificaEsistenzaValore(riga.get(9))) {

							// Se lo stato e' 1 - ITALIA
							if ("1".equals(SqlManager.getValueFromVectorParam(riga, 9).getStringValue())) {
								aggiudicatario.setSOGGETTOESTERO(FlagSNType.N);
							} else {
								aggiudicatario.setSOGGETTOESTERO(FlagSNType.S);

								// Ricavo la corrispondenza con il tabellato W3z12
								String codiceNazione = SqlManager.getValueFromVectorParam(riga, 9).getStringValue();
								String statoEstero = getStatoEstero(codiceNazione);
								aggiudicatario.setCODICESTATO(StatoEsteroType.Enum.forString(statoEstero));
							}
						} else {
							aggiudicatario.setSOGGETTOESTERO(FlagSNType.N);
						}

					}
				}
			}
			
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati degli aggiudicatari", "AggiudicatariType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAnagraficaAggiudicatari: fine metodo");
		}
	}

	/**
	 * Crea l'elemento Schede (di tipo DatiAggiudicazioneType) Contiene i dati
	 * comuni, i dati di pubblicazione del bando di gara e l'elemento
	 * SchedaCompleta (a sua volta contiene tutte le fasi o eventi del contratto
	 * in esame).
	 * 
	 * @param datiAggiudicazione
	 *            DatiAggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param fase_esecuzione
	 *            fase esecuzione
	 * @param username
	 *            username
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setContratti(APCONTRATTOMASTERType contratti, Long codgara, Long codlott, Long fase_esecuzione, Long num, String username) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setContratti: inizio metodo");
		}

		boolean escluso = false;
		boolean sottosoglia = false;

		try {
			
			List<?> datiW9DatiComuni = sqlManager.getVector(SQL_DATI_COMUNI_CONTRATTO, new Object[] { codgara, codlott });

			if (datiW9DatiComuni != null && datiW9DatiComuni.size() > 0) {
				// Dati comuni a tutte le fasi
				try {
					if (verificaEsistenzaValore(datiW9DatiComuni.get(0))) {
						contratti.setCODICEUNICOINTERVENTO(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 0).toString()));	
					}
				} catch (Exception ex) {
					;
				}
				if (verificaEsistenzaValore(datiW9DatiComuni.get(1))) {
					contratti.setCIG(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 1).toString());
				}
				if (verificaEsistenzaValore(datiW9DatiComuni.get(2))) {
					String tipoContratto = datiW9DatiComuni.get(2).toString();
					if (tipoContratto.equals("F")) {
						contratti.setTIPOCONTRATTO(TipologiaContratto.X_01);
					} else if (tipoContratto.equals("S")) {
						contratti.setTIPOCONTRATTO(TipologiaContratto.X_02);
					} else if (tipoContratto.equals("L")) {
						contratti.setTIPOCONTRATTO(TipologiaContratto.X_03);
					}
				}
				
				// contratti esclusi (inviano a simo solo la scheda adesione,
				// altrimenti l'agg. semplificata)
				escluso =  UtilitySITAT.isE1(codgara, codlott, sqlManager);
				// sottosoglia
				sottosoglia = !UtilitySITAT.isS2(codgara, codlott, sqlManager);
				
				if (escluso) {
					contratti.setTIPOFLUSSO(TipologiaFlusso.X_1);
				} else if (sottosoglia) {
					contratti.setTIPOFLUSSO(TipologiaFlusso.X_2);
				} else {
					contratti.setTIPOFLUSSO(TipologiaFlusso.X_3);
				}
				
				if (verificaEsistenzaValore(datiW9DatiComuni.get(3))) {
					String settore = datiW9DatiComuni.get(3).toString();
					if (settore.equals("O")) {
						contratti.setCODICESETTORE(TipologiaSettore.X_01);
					} else if (settore.equals("S")) {
						contratti.setCODICESETTORE(TipologiaSettore.X_02);
					} 
				}
				
			}
			
			
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati comuni del lotto (contratto)", "datiComuniType", e);
		}
		
		// Scheda Completa (contiene tutte le possibili fasi/eventi associati al
		// contratto)
		SCHEDECONTRATTO schede = contratti.addNewSCHEDE();
		setSchede(schede, codgara, codlott, fase_esecuzione, num);

		contratti.setUsername(username);
		
		if (logger.isDebugEnabled()) {
			logger.debug("setSchede: fine metodo");
		}
	}
	
	

	/**
	 * Conversione di un importo da Double a Stringa.
	 * 
	 * @param importo
	 *            Importo
	 * @return Ritorna l'importo convertito in stringa
	 */
	private String convertiImporto(Double importo) {
		String result = null;

		if (importo != null) {

			DecimalFormatSymbols simbols = new DecimalFormatSymbols();
			simbols.setDecimalSeparator(',');
			DecimalFormat decimalFormat = new DecimalFormat("0.00", simbols);
			result = decimalFormat.format(importo);
		}
		return result;
	}
	
	private String convertiCPV(String cpv) {
		String result = cpv;
		if (cpv != null) {
			result = cpv.substring(0,cpv.indexOf('-'));
		}
		return result;
	}

	private Double convertiImporto(String importo) {
		Double result = 0.0;

		if (importo != null) {
			DecimalFormat df = new DecimalFormat();
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator(',');
			symbols.setGroupingSeparator(' ');
			df.setDecimalFormatSymbols(symbols);
			try {
				result = df.parse(importo).doubleValue();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Conversione di una percentuale da Double a Stringa.
	 * 
	 * @param importo
	 *            Importo
	 * @return Ritorna l'importo convertito in stringa
	 */
	private String convertiPercentuale(Double percentuale) {
		String result = null;

		if (percentuale != null) {

			DecimalFormatSymbols simbols = new DecimalFormatSymbols();
			simbols.setDecimalSeparator(',');
			DecimalFormat decimalFormat = new DecimalFormat("0.00", simbols);
			result = decimalFormat.format(percentuale);
		}
		return result;
	}



	public HashMap<String, Object> validate(TrasferimentoDatiType trasferimentoDati, Long fase) throws JspException {
		HashMap<String, Object> infoValidazione = new HashMap<String, Object>();

		try {
			String titolo = null;
			List<Object> listaControlli = new Vector<Object>();
			this.validazione(trasferimentoDati, listaControlli);
			switch (fase.intValue()) {
				case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
					titolo = "Fase Aggiudicazione";
					this.validazioneAggiudicazione(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
					titolo = "Fase Aggiudicazione sotto soglia";
					this.validazioneAggiudicazioneSottoSoglia(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.ADESIONE_ACCORDO_QUADRO:
					titolo = "Fase Adesione";
					this.validazioneAdesione(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.STIPULA_ACCORDO_QUADRO:
					titolo = "Fase Stipula accordo quadro";
					this.validazioneStipula(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
					titolo = "Fase Esecuzione e Avanzamento";
					this.validazioneEsecuzione(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
					titolo = "Fase Inizio";
					this.validazioneInizio(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
				case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
					titolo = "Fase Conclusione";
					this.validazioneConclusione(trasferimentoDati, listaControlli);
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
					this.validazioneVariante(trasferimentoDati, listaControlli);
					break;	
				case CostantiW9.ACCORDO_BONARIO:
					titolo = "Evento Accordo Bonario";
					this.validazioneAccordoBonario(trasferimentoDati, listaControlli);
					break;	
				case CostantiW9.SUBAPPALTO:
					titolo = "Evento Subappalto";
					this.validazioneSubappalto(trasferimentoDati, listaControlli);
					break;
				case CostantiW9.ISTANZA_RECESSO:
					titolo = "Evento Istanza di Recesso";
					this.validazioneRecesso(trasferimentoDati, listaControlli);
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
	
	private void validazione(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			APCONTRATTOMASTERType contratto = trasferimentoDati.getContrattiArray(0);
			nomeTabella = "W9LOTT";
			pagina = "Dati generali del lotto";
			if (contratto.getCIG() != null) {
				if (!UtilitySITAT.isCigValido(contratto.getCIG())) {
					this.addAvviso(listaControlli, nomeTabella, "CIG", "E", pagina, "Il codice cig non è valido");
				}
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto", e);
		}


		if (logger.isDebugEnabled()) {
			logger.debug("validazione: fine metodo");
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
	private void validazioneAggiudicazione(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		boolean obbligatorio = false;
		try {
			FASEAGGIUDICAZIONEType aggiudicazione = trasferimentoDati.getContrattiArray(0).getSCHEDE().getFASEAGGIUDICAZIONE();
			nomeTabella = "UFFINT";
			pagina = "Stazione appaltante";
			if (aggiudicazione.getCODFISCALESA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CFEIN", pagina);
			}
			if (aggiudicazione.getDENOMINAZIONESA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMEIN", pagina);
			}
			
			
			nomeTabella = "W9GARA";
			pagina = "Dati generali della gara";
			if (aggiudicazione.getCIGPADRE() != null) {
				if (!UtilitySITAT.isCigValido(aggiudicazione.getCIGPADRE())) {
					this.addAvviso(listaControlli, nomeTabella, "CIG_ACCQUADRO", "E", pagina, "Il codice cig non è valido");
				}
			} else {
				if (aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_11) || aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_2)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CIG_ACCQUADRO", pagina);
				}
			}
			
			if (aggiudicazione.getIDMODALITAREALIZZAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPO_APP", pagina);
			} else if (aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_11)){
				this.addAvviso(listaControlli, nomeTabella, "TIPO_APP", "E", pagina, "La modalità di realizzazione deve essere diversa da \"Contratto d'appalto discendente da Accordo quadro/Convenzione senza successivo confronto competitivo\"");
			}
			
			if (aggiudicazione.getNUMEROLOTTO() > 0) {
				if (aggiudicazione.getNUMEROLOTTICOMPLESSIVO() == 0) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NLOTTI", pagina);
				}
			}
			pagina = "Dati generali della Gara - Stazione appaltante";
			if (aggiudicazione.getSAALTROSOGGETTO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_SA_AGENTE", pagina);
			} else {
				if (aggiudicazione.getSAALTROSOGGETTO().equals(FlagSNType.S)) {
					if (aggiudicazione.getIDTIPOLOGIASA() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPOLOGIA_SA", pagina);
					}
					if (aggiudicazione.getIDTIPOLOGIAPROC() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPOLOGIA_PROCEDURA", pagina);
					}
					if (aggiudicazione.getCODICEFISCALESOGGETTOSA() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CF_SA_AGENTE", pagina);
					}
					if (aggiudicazione.getDENOMINAZIONESOGGETTOSA() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DENOM_SA_AGENTE", pagina);
					}
					if (aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_9)){
						if (aggiudicazione.getSTIPULA() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_CENTRALE_STIPULA", pagina);
						}
					}
				}
			}
			nomeTabella = "W9LOTT";
			pagina = "Dati generali del lotto";
			if (aggiudicazione.getOGGETTOCONTRATTO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "OGGETTO", pagina);
			}
			if (aggiudicazione.getIDPRESTAZIONE()== null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPO_PRESTAZIONE", pagina);
			}
			if (aggiudicazione.getCODICEPROCEDURAGARA()== null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_SCELTA_CONTRAENTE", pagina);
			}
			obbligatorio = false;
			if (aggiudicazione.getCODICECRITERIOAGGIUDICAZIONE() == null) {
				if (aggiudicazione.getGIUSTIFICAZIONIRICORSO() != null) {
					for(GIUSTIFICAZIONEType giustificazione:aggiudicazione.getGIUSTIFICAZIONIRICORSO().getGIUSTIFICAZIONEArray()) {
						switch (giustificazione.getCODICECONDIZIONE().intValue()) {
						case 2: case 5: case 7: case 9: case 10: case 11
						: case 14: case 16: case 17: case 18: case 23: case 24: case 28:
							;
							break;
						default:
							obbligatorio = true;
							break;
						}
					}
				} else {
					obbligatorio = true;
				}
			}
			if (obbligatorio) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MODO_GARA", pagina);
			}
			pagina = "Dati generali lotto - Dati economici";
			if (aggiudicazione.getCPV() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CPV", pagina);
			}
			if (aggiudicazione.getCODICECUP() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CUP", pagina);
			} else if (aggiudicazione.getCODICECUP().length() != 15) {
				this.addAvviso(listaControlli, nomeTabella, "CUP", "E", pagina, "Il codice cup non è valido");
			}
			
			if (trasferimentoDati.getContrattiArray(0).getTIPOFLUSSO().equals(TipologiaFlusso.X_2)) {
				if (aggiudicazione.getIMPORTOLOTTO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_LOTTO", pagina);
				}
			}
			pagina = "Dati generali lotto - Localizzazione";
			if (aggiudicazione.getLOCALIZZAZIONE() == null) {
				if (aggiudicazione.getCODICENUTS() == null) {
					this.addAvviso(listaControlli, nomeTabella, "LUOGO_ISTAT", "E", pagina, "Inserire almeno uno tra codice istat e codice nuts");
				}
			}
			if (aggiudicazione.getCODICENUTS() != null) {
				if (!aggiudicazione.getCODICENUTS().startsWith("ITC4")) {
					this.addAvviso(listaControlli, nomeTabella, "LUOGO_NUTS", "E", pagina, "Il codice nuts deve appartenere alla regione lombardia");
				}
			}
			nomeTabella = "W9COND";
			pagina = "Dati generali lotto - Condizioni che giustificano il ricorso alla procedura negoziata";
			
			if (aggiudicazione.getCODICEPROCEDURAGARA().equals(ProceduraGaraType.X_08) || aggiudicazione.getCODICEPROCEDURAGARA().equals(ProceduraGaraType.X_10)) {
				if (aggiudicazione.getGIUSTIFICAZIONIRICORSO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CONDIZIONE", pagina);
				}
			}
			
			//W9PUBB
			nomeTabella = "W9PUBB";
			pagina = "Pubblicità gara";
			if (aggiudicazione.getPROFILOCOMMITTENTE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "PROFILO_COMMITTENTE", pagina);
			}
			if (aggiudicazione.getSITOINFMI() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_MINISTERO_INF_TRASP", pagina);
			}
			if (aggiudicazione.getSITOINFOCP() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_OSSERVATORIO_CP", pagina);
			}
			//ACCORDO QUADRO
			nomeTabella = "W9APPA";
			pagina = "Dati aggiudicazione - Dati procedurali dell'appalto";
			if (aggiudicazione.getACCORDOQUADRO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ACCORDO_QUADRO", pagina);
			}
			//PROCEDURA ACCELLERATA
			if (aggiudicazione.getPROCEDURAACCELERATA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "PROCEDURA_ACC", pagina);
			}
			if (aggiudicazione.getPREINFORMAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "PREINFORMAZIONE", pagina);
			}
			if (aggiudicazione.getTERMINERIDOTTO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "TERMINE_RIDOTTO", pagina);
			}
			
			if (trasferimentoDati.getContrattiArray(0).getCODICESETTORE() != null && trasferimentoDati.getContrattiArray(0).getCODICESETTORE().equals(TipologiaSettore.X_02)) {
				if (!aggiudicazione.getCODICEPROCEDURAGARA().equals(ProceduraGaraType.X_10) && !aggiudicazione.getCODICEPROCEDURAGARA().equals(ProceduraGaraType.X_11)) {
					if (aggiudicazione.getIDMODALITAINDIZIONE() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MODO_INDIZIONE", pagina);
					}
				}
				if (aggiudicazione.getREQUISITICOMPONENTI() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "REQUISITI_SETT_SPEC", pagina);
				}
			}
			
			
			pagina = "Dati aggiudicazione - Inviti ed offerte / soglia di anomalia ";
			if (aggiudicazione.getASTAELETTRONICA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ASTA_ELETTRONICA", pagina);
			}
			if (!aggiudicazione.getCODICEPROCEDURAGARA().equals(ProceduraGaraType.X_02) && !aggiudicazione.getCODICEPROCEDURAGARA().equals(ProceduraGaraType.X_03)) {
				if (aggiudicazione.getDATARICHIESTA() != null) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_SCADENZA_RICHIESTA_INVITO", "E", pagina, "Non deve essere valorizzata per procedure gara diverse da \"Procedura ristretta\" e \"Procedura negoziata previa pubblicazione\"");
				}
			}
			if (aggiudicazione.getDATASCADENZA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_SCADENZA_PRES_OFFERTA", pagina);
			}
			if (aggiudicazione.getDATAINTERESSE() != null) {
				if (aggiudicazione.getNUMEROINTERESSE() == 0) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_MANIF_INTERESSE", pagina);
				}
			}
			if (aggiudicazione.getDATAINVITO() != null) {
				if (aggiudicazione.getNUMEROINVITATI() == 0) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_IMPRESE_INVITATE", pagina);
				}
			}
			if (aggiudicazione.getNUMEROOFFERTA() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_IMPRESE_OFFERENTI", pagina);
			} else {
				if (aggiudicazione.getNUMEROOFFERTA() > aggiudicazione.getNUMEROINVITATI() && aggiudicazione.getDATAINVITO() != null) {
					this.addAvviso(listaControlli, nomeTabella, "NUM_IMPRESE_OFFERENTI", "E", pagina, "Deve essere minore o uguale di \"Imprese invitate\"");
				}
			}
			if (aggiudicazione.getNUMEROAMMESSE() == 0) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_OFFERTE_AMMESSE", pagina);
			} else {
				if (aggiudicazione.getNUMEROAMMESSE() > aggiudicazione.getNUMEROOFFERTA()) {
					this.addAvviso(listaControlli, nomeTabella, "NUM_OFFERTE_AMMESSE", "E", pagina, "Deve essere minore o uguale di \"Imprese con offerta\"");
				}
			}
			if (trasferimentoDati.getContrattiArray(0).getCODICESETTORE() != null && trasferimentoDati.getContrattiArray(0).getCODICESETTORE().equals(TipologiaSettore.X_01) 
					&& aggiudicazione.getCODICECRITERIOAGGIUDICAZIONE() != null && aggiudicazione.getCODICECRITERIOAGGIUDICAZIONE().equals(CriterioAggiudicazioneType.X_2)
					&& aggiudicazione.getNUMEROAMMESSE() > 1) {
				if (aggiudicazione.getOFFERTAMAXRIBASSO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "OFFERTA_MASSIMO", pagina);
				}
				if (aggiudicazione.getOFFERTAMAXRIBASSO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "OFFERTA_MINIMA", pagina);
				}
			}
			if(aggiudicazione.getCODICECRITERIOAGGIUDICAZIONE() != null && aggiudicazione.getCODICECRITERIOAGGIUDICAZIONE().equals(CriterioAggiudicazioneType.X_1)) {
				if (aggiudicazione.getVALORESOGLIA() != null) {
					this.addAvviso(listaControlli, nomeTabella, "VAL_SOGLIA_ANOMALIA", "E", pagina, "Non deve essere valorizzato se Criterio di aggiudicazione è \"offerta economicamente più vantaggiosa\"");
				}
			}
			if (aggiudicazione.getVALORESOGLIA() != null) {
				if (convertiImporto(aggiudicazione.getVALORESOGLIA()) < convertiImporto(aggiudicazione.getOFFERTAMAXRIBASSO()) || convertiImporto(aggiudicazione.getVALORESOGLIA()) > convertiImporto(aggiudicazione.getOFFERTAMINRIBASSO())) {
					this.addAvviso(listaControlli, nomeTabella, "VAL_SOGLIA_ANOMALIA", "E", pagina, "deve essere >= dell'offerta massimo ribasso e <= dell'offerta minimo ribasso");
				}
			}
			if (aggiudicazione.getOFFERTESOGLIA() > 0) {
				if (aggiudicazione.getOFFERTESOGLIA() > aggiudicazione.getNUMEROAMMESSE()) {
					this.addAvviso(listaControlli, nomeTabella, "NUM_OFFERTE_FUORI_SOGLIA", "E", pagina, "deve essere <= del numero di offerte ammesse");
				}
			}
			if (aggiudicazione.getIMPRESEESCLUSE() > 0) {
				if (aggiudicazione.getIMPRESEESCLUSE() > aggiudicazione.getNUMEROAMMESSE()) {
					this.addAvviso(listaControlli, nomeTabella, "NUM_OFFERTE_ESCLUSE", "E", pagina, "deve essere <= del numero di offerte ammesse");
				}
			}
			if (aggiudicazione.getIMPRESEESCLUSEINSUF() > 0) {
				if (aggiudicazione.getIMPRESEESCLUSEINSUF() > aggiudicazione.getNUMEROAMMESSE()) {
					this.addAvviso(listaControlli, nomeTabella, "NUM_IMP_ESCL_INSUF_GIUST", "E", pagina, "deve essere <= del numero di offerte ammesse");
				}
			}
			
			pagina = "Dati aggiudicazione - Aggiudicazione / affidamento";
			if (aggiudicazione.getRIBASSOAGGIUDICAZIONE() != null && aggiudicazione.getOFFERTAAUMENTO() != null) {
				this.addAvviso(listaControlli, nomeTabella, "PERC_RIBASSO_AGG", "E", pagina, "non deve essere valorizzato contemporaneamente a \"Offerta in aumento %\"");
			}
			if (aggiudicazione.getDATAAGGIUDICAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", pagina);
			} else {
				if (df.parse(aggiudicazione.getDATAAGGIUDICAZIONE()).compareTo(new Date()) > 0 || df.parse(aggiudicazione.getDATAAGGIUDICAZIONE()).compareTo(df.parse("01-01-2008")) < 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", "E", pagina, "deve essere <= alla data odierna e >= a 01/01/2008");
				}
			}
			if (aggiudicazione.getSUBAPPALTOPRESTAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RICH_SUBAPPALTO", pagina);
			}
			
			pagina = "Dati aggiudicazione - Dati economici dell'appalto";
			if (aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_9)){
				if (aggiudicazione.getDURATAGG() == 0) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DURATA_ACCQUADRO", pagina);
				}
			}
			if (aggiudicazione.getIMPORTOTOTALE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_COMPL_APPALTO", pagina);
			}
			if (trasferimentoDati.getContrattiArray(0).getTIPOCONTRATTO().equals(TipologiaContratto.X_03)) {
				//LAVORI
				if (trasferimentoDati.getContrattiArray(0).getCODICESETTORE() != null && trasferimentoDati.getContrattiArray(0).getCODICESETTORE().equals(TipologiaSettore.X_01)) {
					if (aggiudicazione.getIDSTRUMENTOPROGRAMMAZIONE() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "COD_STRUMENTO", pagina);
					}
				}
				if (aggiudicazione.getOPEREURBANSCOMPUTO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "OPERE_URBANIZ_SCOMPUTO", pagina);
				}
				if (aggiudicazione.getIMPORTOLAVORI() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_LAVORI", pagina);
				}
				nomeTabella = "W9APPALAV";
				pagina = "Dati aggiudicazione - Modalità tipologia lavoro";
				if (aggiudicazione.getLAVORI() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_APPALTO", pagina);
				}
				nomeTabella = "W9INCA";
				pagina = "Incarichi professionali";
				obbligatorio = true;

				if (aggiudicazione.getPRESTAZIONIPROGETTUALI() != null) {
					for(PRESTAZIONEType prestazione:aggiudicazione.getPRESTAZIONIPROGETTUALI().getPRESTAZIONEArray()) {
						if (prestazione.getRUOLO().equals(RuoloResponsabileType.X_1) || prestazione.getRUOLO().equals(RuoloResponsabileType.X_2) || prestazione.getRUOLO().equals(RuoloResponsabileType.X_4)) {
							obbligatorio = false;
							if (prestazione.getRUOLO().equals(RuoloResponsabileType.X_2)) {
								if (prestazione.getDATAAFFIDAMENTO() == null) {
									this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_AFF_PROG_ESTERNA", pagina);
								}
								if (prestazione.getDATACONSEGNA() == null) {
									this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_CONS_PROG_ESTERNA", pagina);
								}
							}
						}
					}
				}
				if (obbligatorio) {
					this.addAvviso(listaControlli, nomeTabella, "ID_RUOLO", "E", pagina, "Deve esistere almeno un Progettista tra i professionisti");
				}
				nomeTabella = "W9FINA";
				pagina = "Finanziamenti";
				if (trasferimentoDati.getContrattiArray(0).getCODICESETTORE() != null && trasferimentoDati.getContrattiArray(0).getCODICESETTORE().equals(TipologiaSettore.X_01)) {
					if (aggiudicazione.getFINANZIAMENTI() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_FINANZIAMENTO", pagina);
					}
				}
			} else {
				
				if (trasferimentoDati.getContrattiArray(0).getTIPOCONTRATTO().equals(TipologiaContratto.X_01)) {
					//FORNITURE
					if (aggiudicazione.getIMPORTOFORNITURE() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_FORNITURE", pagina);
					}
				} else {
					//SERVIZI
					if (aggiudicazione.getIMPORTOSERVIZI() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_SERVIZI", pagina);
					}
				}
				//FORNITURE E SERVIZI
				nomeTabella = "W9APPAFORN";
				pagina = "Dati aggiudicazione - Modalità di acquisizione forniture / servizi";
				if (aggiudicazione.getMODALITAACQUISIZIONE() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_APPALTO", pagina);
				}
			}
			
			
			nomeTabella = "W9INCA";
			pagina = "Incarichi professionali";
			obbligatorio = true;
			if (aggiudicazione.getINCARICHIPROFESSIONISTI() != null) {
				for(PROFESSIONISTAType professionista:aggiudicazione.getINCARICHIPROFESSIONISTI().getPROFESSIONISTAArray()) {
					if (professionista.getIDRUOLO().equals(RuoloResponsabileType.X_14)) {
						obbligatorio = false;
					}
				}
			}
			if (obbligatorio) {
				this.addAvviso(listaControlli, nomeTabella, "ID_RUOLO", "E", pagina, "Deve esistere almeno un RUP tra i professionisti");
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di aggiudicazione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAggiudicazione)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazione: fine metodo");
		}
	}
	
	private void validazioneAggiudicazioneSottoSoglia(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazioneSottoSoglia: inizio metodo");
		} 

		String nomeTabella = "";
		String pagina = "";

		boolean obbligatorio = false;
		try {
			FASEAGGIUDICAZIONEType aggiudicazione = trasferimentoDati.getContrattiArray(0).getSCHEDE().getFASEAGGIUDICAZIONE();
			nomeTabella = "UFFINT";
			pagina = "Stazione appaltante";
			if (aggiudicazione.getCODFISCALESA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CFEIN", pagina);
			}
			if (aggiudicazione.getDENOMINAZIONESA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMEIN", pagina);
			}
			
			nomeTabella = "W9GARA";
			pagina = "Dati generali - Dati generali della gara";
			if (aggiudicazione.getCIGPADRE() != null) {
				if (!UtilitySITAT.isCigValido(aggiudicazione.getCIGPADRE())) {
					this.addAvviso(listaControlli, nomeTabella, "CIG_ACCQUADRO", "E", pagina, "Il codice cig non è valido");
				}
			} else {
				if (aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_11) || aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_2)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CIG_ACCQUADRO", pagina);
				}
			}
			
			if (aggiudicazione.getIDMODALITAREALIZZAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPO_APP", pagina);
			} else if (aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_11)){
				this.addAvviso(listaControlli, nomeTabella, "TIPO_APP", "E", pagina, "La modalità di realizzazione deve essere diversa da \"Contratto d'appalto discendente da Accordo quadro/Convenzione senza successivo confronto competitivo\"");
			}
			
			if (aggiudicazione.getNUMEROLOTTO() > 0) {
				if (aggiudicazione.getNUMEROLOTTICOMPLESSIVO() == 0) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NLOTTI", pagina);
				}
			}
			
			pagina = "Dati generali - Stazione appaltante";
			if (aggiudicazione.getSAALTROSOGGETTO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_SA_AGENTE", pagina);
			} else {
				if (aggiudicazione.getSAALTROSOGGETTO().equals(FlagSNType.S)) {
					if (aggiudicazione.getIDTIPOLOGIASA() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPOLOGIA_SA", pagina);
					}
					if (aggiudicazione.getIDTIPOLOGIAPROC() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPOLOGIA_PROCEDURA", pagina);
					}
					if (aggiudicazione.getCODICEFISCALESOGGETTOSA() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CF_SA_AGENTE", pagina);
					}
					if (aggiudicazione.getDENOMINAZIONESOGGETTOSA() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DENOM_SA_AGENTE", pagina);
					}
					if (aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_9)){
						if (aggiudicazione.getSTIPULA() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_CENTRALE_STIPULA", pagina);
						}
					}
				}
			}
			
			nomeTabella = "W9LOTT";
			pagina = "Dati generali - Dati generali del lotto";
			if (aggiudicazione.getOGGETTOCONTRATTO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "OGGETTO", pagina);
			}
			if (aggiudicazione.getCODICEPROCEDURAGARA()== null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_SCELTA_CONTRAENTE", pagina);
			}
			
			pagina = "Dati generali - Dati economici";
			if (aggiudicazione.getCPV() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CPV", pagina);
			}
			if (aggiudicazione.getCODICECUP() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CUP", pagina);
			} else if (aggiudicazione.getCODICECUP().length() != 15) {
				this.addAvviso(listaControlli, nomeTabella, "CUP", "E", pagina, "Il codice cup non è valido");
			}
			if (trasferimentoDati.getContrattiArray(0).getTIPOFLUSSO().equals(TipologiaFlusso.X_2)) {
				if (aggiudicazione.getIMPORTOLOTTO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_LOTTO", pagina);
				}
			}
			
			pagina = "Dati generali - Localizzazione";
			if (aggiudicazione.getLOCALIZZAZIONE() == null) {
				if (aggiudicazione.getCODICENUTS() == null) {
					this.addAvviso(listaControlli, nomeTabella, "LUOGO_ISTAT", "E", pagina, "Inserire almeno uno tra codice istat e codice nuts");
				}
			}
			if (aggiudicazione.getCODICENUTS() != null) {
				if (!aggiudicazione.getCODICENUTS().startsWith("ITC4")) {
					this.addAvviso(listaControlli, nomeTabella, "LUOGO_NUTS", "E", pagina, "Il codice nuts deve appartenere alla regione lombardia");
				}
			}
			
			nomeTabella = "W9COND";
			pagina = "Dati generali - Condizioni che giustificano il ricorso alla procedura negoziata";
			
			if (trasferimentoDati.getContrattiArray(0).getTIPOFLUSSO().equals(TipologiaFlusso.X_2)) {
				if (aggiudicazione.getCODICEPROCEDURAGARA().equals(ProceduraGaraType.X_08) || aggiudicazione.getCODICEPROCEDURAGARA().equals(ProceduraGaraType.X_10)) {
					if (aggiudicazione.getGIUSTIFICAZIONIRICORSO() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CONDIZIONE", pagina);
					}
				}
			}
			
			//W9PUBB
			nomeTabella = "W9PUBB";
			pagina = "Pubblicità gara";
			if (aggiudicazione.getPROFILOCOMMITTENTE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "PROFILO_COMMITTENTE", pagina);
			}
			if (aggiudicazione.getSITOINFMI() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_MINISTERO_INF_TRASP", pagina);
			}
			if (aggiudicazione.getSITOINFOCP() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_OSSERVATORIO_CP", pagina);
			}
			
			
			//ACCORDO QUADRO
			nomeTabella = "W9APPA";
			pagina = "Dati aggiudicazione - Dati procedurali dell'appalto";
			if (aggiudicazione.getACCORDOQUADRO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ACCORDO_QUADRO", pagina);
			}
			
			pagina = "Dati aggiudicazione - Inviti ed offerte / soglia di anomalia ";
			if (aggiudicazione.getASTAELETTRONICA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ASTA_ELETTRONICA", pagina);
			}
			if (aggiudicazione.getDATASCADENZA() == null) {
				//per i contratti sottosoglia imposto la data di scadenza con quella di aggiudicazione
				aggiudicazione.setDATASCADENZA(aggiudicazione.getDATAAGGIUDICAZIONE());
			}
			
			if (aggiudicazione.getNUMEROOFFERTA() == 0) {
				aggiudicazione.setNUMEROOFFERTA(0);
			}
			
			if (aggiudicazione.getNUMEROAMMESSE() == 0) {
				aggiudicazione.setNUMEROAMMESSE(0);
			}
			
			pagina = "Dati aggiudicazione - Aggiudicazione / affidamento";
			if (aggiudicazione.getRIBASSOAGGIUDICAZIONE() != null && aggiudicazione.getOFFERTAAUMENTO() != null) {
				this.addAvviso(listaControlli, nomeTabella, "PERC_RIBASSO_AGG", "E", pagina, "non deve essere valorizzato contemporaneamente a \"Offerta in aumento %\"");
			}
			if (aggiudicazione.getDATAAGGIUDICAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", pagina);
			} else {
				if (df.parse(aggiudicazione.getDATAAGGIUDICAZIONE()).compareTo(new Date()) > 0 || df.parse(aggiudicazione.getDATAAGGIUDICAZIONE()).compareTo(df.parse("01-01-2008")) < 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", "E", pagina, "deve essere <= alla data odierna e >= a 01/01/2008");
				}
			}
			if (aggiudicazione.getSUBAPPALTOPRESTAZIONE() == null) {
				aggiudicazione.setSUBAPPALTOPRESTAZIONE(SnType.N);
			}
			pagina = "Dati aggiudicazione - Dati economici dell'appalto";
			if (aggiudicazione.getIDMODALITAREALIZZAZIONE().equals(ModoRealizzazioneType.X_9)){
				if (aggiudicazione.getDURATAGG() == 0) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DURATA_ACCQUADRO", pagina);
				}
			}
			if (aggiudicazione.getIMPORTOTOTALE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_COMPL_APPALTO", pagina);
			}
			
			if (trasferimentoDati.getContrattiArray(0).getTIPOFLUSSO().equals(TipologiaFlusso.X_2)) {
				if (aggiudicazione.getDATASTIPULASSE()== null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_STIPULA", pagina);
				} else {
					if (aggiudicazione.getDATATERMINESSE()== null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_TERMINE", pagina);
					} else {
						if (df.parse(aggiudicazione.getDATATERMINESSE()).compareTo(df.parse(aggiudicazione.getDATASTIPULASSE())) < 0) {
							this.addAvviso(listaControlli, nomeTabella, "DATA_STIPULA", "E", pagina, "deve essere < della data termine");
						}
					}
				}
			}
			
			if (trasferimentoDati.getContrattiArray(0).getTIPOCONTRATTO().equals(TipologiaContratto.X_03)) {
				//LAVORI
				if (aggiudicazione.getIMPORTOLAVORI() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_LAVORI", pagina);
				}
				
				nomeTabella = "W9FINA";
				pagina = "Finanziamenti";
				if (trasferimentoDati.getContrattiArray(0).getCODICESETTORE() != null && trasferimentoDati.getContrattiArray(0).getCODICESETTORE().equals(TipologiaSettore.X_01)) {
					if (aggiudicazione.getFINANZIAMENTI() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_FINANZIAMENTO", pagina);
					}
				}
			} else {
				if (trasferimentoDati.getContrattiArray(0).getTIPOCONTRATTO().equals(TipologiaContratto.X_01)) {
					//FORNITURE
					if (aggiudicazione.getIMPORTOFORNITURE() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_FORNITURE", pagina);
					}
				} else {
					//SERVIZI
					if (aggiudicazione.getIMPORTOSERVIZI() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_SERVIZI", pagina);
					}
				}
				
			}
			
			nomeTabella = "W9INCA";
			pagina = "Incarichi professionali";
			obbligatorio = true;
			if (aggiudicazione.getINCARICHIPROFESSIONISTI() != null) {
				for(PROFESSIONISTAType professionista:aggiudicazione.getINCARICHIPROFESSIONISTI().getPROFESSIONISTAArray()) {
					if (professionista.getIDRUOLO().equals(RuoloResponsabileType.X_14)) {
						obbligatorio = false;
					}
				}
			}
			if (obbligatorio) {
				this.addAvviso(listaControlli, nomeTabella, "ID_RUOLO", "E", pagina, "Deve esistere almeno un RUP tra i professionisti");
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase Aggiudicazione Sotto Soglia/ Contratto Escluso"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAggiudicazioneSottoSoglia)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAggiudicazioneSottoSoglia: fine metodo");
		}
	}
	
	private void validazioneInizio(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneInizio: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			FASEINIZIOType inizio = trasferimentoDati.getContrattiArray(0).getSCHEDE().getFASEINIZIO();
			String idFk = inizio.getIDFK();
			Long codgara = Long.parseLong(idFk.substring(0,7));
			Long codlott = Long.parseLong(idFk.substring(7,11));
			Long num = Long.parseLong(idFk.substring(11,14));
			Date dataAggiudicazione = (Date) sqlManager.getObject("select DATA_VERB_AGGIUDICAZIONE from W9APPA where codgara = ? and codlott = ? and num_appa = ? ", new Object[] { codgara, codlott, num });
			nomeTabella = "W9PUES";
			pagina = "Fase iniziale di esecuzione del contratto - Dati generali - Pubblicazione esito di gara";
			if (inizio.getGUCE() != null) {
				if (df.parse(inizio.getGUCE()).compareTo(dataAggiudicazione) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_GUCE", "E", pagina, "deve essere > della data di aggiudicazione");
				}
			}
			if (inizio.getGURI() != null) {
				if (df.parse(inizio.getGURI()).compareTo(dataAggiudicazione) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_GURI", "E", pagina, "deve essere > della data di aggiudicazione");
				}
			}
			if (inizio.getQUOTINAZIONALI() > 20) {
				this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_NAZ", "E", pagina, "deve essere <= 20");
			}
			if (inizio.getQUOTILOCALI() > 20) {
				this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_REG", "E", pagina, "deve essere <= 20");
			}
			
			if (inizio.getPROFILOCOMMITTENTE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "PROFILO_COMMITTENTE", pagina);
			}
			
			if (inizio.getSITOINFMI() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_MINISTERO_INF_TRASP", pagina);
			}
			
			if (inizio.getSITOINFOCP() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_OSSERVATORIO_CP", pagina);
			}
			
			nomeTabella = "W9INIZ";
			pagina = "Fase iniziale di esecuzione del contratto - Dati generali - Contratto di appalto";
			if (inizio.getDATASTIPULA() != null) {
				if (df.parse(inizio.getDATASTIPULA()).compareTo(dataAggiudicazione) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_STIPULA", "E", pagina, "deve essere > della data di aggiudicazione");
				}
				if (inizio.getDATAESECUTIVITA() != null) {
					if (df.parse(inizio.getDATAESECUTIVITA()).compareTo(df.parse(inizio.getDATASTIPULA())) < 0) {
						this.addAvviso(listaControlli, nomeTabella, "DATA_ESECUTIVITA", "E", pagina, "deve essere >= della data di stipula");
					}
				}
			}
			if (inizio.getIMPORTOCAUZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_CAUZIONE", pagina);
			}
			
			pagina = "Fase iniziale di esecuzione del contratto - Dati generali - termine di esecuzione";
			if (inizio.getDATADISPOSIZIONE() != null) {
				if (inizio.getDATAAPPROVAZIONE() != null) {
					if (df.parse(inizio.getDATAAPPROVAZIONE()).compareTo(df.parse(inizio.getDATADISPOSIZIONE())) <= 0) {
						this.addAvviso(listaControlli, nomeTabella, "DATA_APP_PROG_ESEC", "E", pagina, "deve essere > della data di disposizione");
					}
				}
			}
			if (inizio.getCONSEGNAFRAZIONATA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_FRAZIONATA", pagina);
			} else {
				if (inizio.getCONSEGNAFRAZIONATA().equals(SnType.S)) {
					if (inizio.getDATAPRIMACONSEGNA() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERBALE_CONS", pagina);
					}
				} else {
					if (inizio.getDATADEFINITIVA() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERBALE_DEF", pagina);
					}
					if (inizio.getTERMINECONTRATTUALE() != null) {
						if (inizio.getDATASTIPULA() != null) {
							if (df.parse(inizio.getTERMINECONTRATTUALE()).compareTo(df.parse(inizio.getDATASTIPULA())) <= 0) {
								this.addAvviso(listaControlli, nomeTabella, "DATA_TERMINE", "E", pagina, "deve essere > della data di stipula");
							}
						} else if (inizio.getDATAEFFETTIVA() != null) {
							if (df.parse(inizio.getTERMINECONTRATTUALE()).compareTo(df.parse(inizio.getDATAEFFETTIVA())) <= 0) {
								this.addAvviso(listaControlli, nomeTabella, "DATA_TERMINE", "E", pagina, "deve essere > della data effettivo inizio");
							}
						}
					}
				}
			}
			if (inizio.getRISERVALEGGE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RISERVA", pagina);
			}
			
			nomeTabella = "W9INCA";
			pagina = "Fase iniziale di esecuzione del contratto - Incarichi professionali";
			if (inizio.getSOGGETTIINCARICATI() == null) {
				this.addAvviso(listaControlli, nomeTabella, "ID_RUOLO", "E", pagina, "Inserisci almeno un soggetto negli incarichi professionali");
			}
			
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di inizio"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneInizio)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneInizio: fine metodo");
		}
	}
	
	
	private void validazioneConclusione(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneConclusione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			FASECONCLUSIONEType conclusione = trasferimentoDati.getContrattiArray(0).getSCHEDE().getFASECONCLUSIONE();
			String idFk = conclusione.getIDFK();
			Long codgara = Long.parseLong(idFk.substring(0,7));
			Long codlott = Long.parseLong(idFk.substring(7,11));
			Long num = Long.parseLong(idFk.substring(11,14));
			Date dataAggiudicazione = (Date) sqlManager.getObject("select DATA_VERB_AGGIUDICAZIONE from W9APPA where codgara = ? and codlott = ? and num_appa = ? ", new Object[] { codgara, codlott, num });
			Double importoComplessivoIntervento = (Double) sqlManager.getObject("select " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_LAVORI","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_SERVIZI","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_FORNITURE","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_PROGETTAZIONE","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_ATTUAZIONE_SICUREZZA","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"ALTRE_SOMME","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_DISPOSIZIONE","0"}) +
					" from W9APPA where codgara = ? and codlott = ? and num_appa = ? ", new Object[] { codgara, codlott, num });
			
			nomeTabella = "W9CONC";
			pagina = "Fase di conclusione del contratto - Interruzione anticipata del procedimento";
			if (conclusione.getIDMOTIVOINTERRUZIONE() != null) {
				if (conclusione.getIDMOTIVOINTERRUZIONE().equals(MotivoInterruzioneType.X_2)) {
					if (conclusione.getIDMOTIVORISOLUZIONE() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MOTIVO_RISOL", pagina);
					}
				}
				if (conclusione.getDATARISOLRECESSO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_RISOLUZIONE", pagina);
				} else if (df.parse(conclusione.getDATARISOLRECESSO()).compareTo(dataAggiudicazione) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_RISOLUZIONE", "E", pagina, "deve essere > della data di aggiudicazione");
				}
				if (conclusione.getIDMOTIVOINTERRUZIONE().equals(MotivoInterruzioneType.X_2) || conclusione.getIDMOTIVOINTERRUZIONE().equals(MotivoInterruzioneType.X_4) || conclusione.getIDMOTIVOINTERRUZIONE().equals(MotivoInterruzioneType.X_5)) {
					if (conclusione.getONERICONCLUSIONE() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ONERI", pagina);
					} 
				}
			}
			if (conclusione.getONERICONCLUSIONE() != null) {
				if (conclusione.getIMPORTO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ONERI_RISOLUZIONE", pagina);
				} else if (conclusione.getONERICONCLUSIONE().getONEREArray(0).getIDONERE().equals(MotivoOnereType.X_1)) {
					if (convertiImporto(conclusione.getIMPORTO()) != 0) {
						this.addAvviso(listaControlli, nomeTabella, "ONERI_RISOLUZIONE", "E", pagina, "deve valere 0");
					}
				} else {
					if (convertiImporto(conclusione.getIMPORTO()) >= importoComplessivoIntervento) {
						this.addAvviso(listaControlli, nomeTabella, "ONERI_RISOLUZIONE", "E", pagina, "deve essere < dell'importo complessivo dell'intervento");
					}
				}
			}
			if (conclusione.getPOLIZZA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_POLIZZA", pagina);
			}
			
			pagina = "Fase di conclusione del contratto - Ultimazione lavori";
			if (conclusione.getDATACONSEGNA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERBALE_CONSEGNA", pagina);
			}
			if (conclusione.getTERMINECONTRATTUALEULTIMAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "TERMINE_CONTRATTO_ULT", pagina);
			}
			if (conclusione.getDATARISOLRECESSO() == null) {
				if (conclusione.getDATAULTIMAZIONE() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_ULTIMAZIONE", pagina);
				} 
			} 
				
			if (conclusione.getDATAULTIMAZIONE() != null) {
				if (df.parse(conclusione.getDATAULTIMAZIONE()).compareTo(dataAggiudicazione) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_ULTIMAZIONE", "E", pagina, "deve essere > della data di aggiudicazione");
				}
			}
			
			if (conclusione.getNUMEROINFORTPOSTUMI() == 0) {
				conclusione.setNUMEROINFORTPOSTUMI(0);
			}
			if (conclusione.getNUMEROINFORTMORTALI() == 0) {
				conclusione.setNUMEROINFORTMORTALI(0);
			}
			if (conclusione.getNUMEROINFORTUNI() < conclusione.getNUMEROINFORTMORTALI() + conclusione.getNUMEROINFORTPOSTUMI()) {
				this.addAvviso(listaControlli, nomeTabella, "NUMERO_INFORTUNI", "E", pagina, "deve essere >= della somma degli infortuni postumi permanente e mortali");
			}

		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di conclusione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneConclusione)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneConclusione: fine metodo");
		}
	}
	
	private void validazioneCollaudo(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneCollaudo: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			FASECOLLAUDOType collaudo = trasferimentoDati.getContrattiArray(0).getSCHEDE().getFASECOLLAUDO();
			String idFk = collaudo.getIDFK();
			Long codgara = Long.parseLong(idFk.substring(0,7));
			Long codlott = Long.parseLong(idFk.substring(7,11));
			Long num = Long.parseLong(idFk.substring(11,14));
			Date dataEffettiva = (Date) sqlManager.getObject("select DATA_VERB_INIZIO from W9INIZ where codgara = ? and codlott = ? and num_iniz = ? ", new Object[] { codgara, codlott, num });
			Date dataUltimazione = (Date) sqlManager.getObject("select DATA_ULTIMAZIONE from W9CONC where codgara = ? and codlott = ? and num_conc = ? ", new Object[] { codgara, codlott, num });
			
			Double importoComplessivoIntervento = (Double) sqlManager.getObject("select " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_LAVORI","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_SERVIZI","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_FORNITURE","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_PROGETTAZIONE","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_ATTUAZIONE_SICUREZZA","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"ALTRE_SOMME","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_DISPOSIZIONE","0"}) +
					" from W9APPA where codgara = ? and codlott = ? and num_appa = ? ", new Object[] { codgara, codlott, num });
			
			nomeTabella = "W9COLL";
			pagina = "Fase di collaudo - Dati generali - Collaudo/verifica di conformità delle prestazioni eseguite";
			if (collaudo.getDATASTATICO() != null) {
				if (df.parse(collaudo.getDATASTATICO()).compareTo(dataEffettiva) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_COLLAUDO_STAT", "E", pagina, "deve essere > della data effettiva di inizio");
				}
			}
			
			if (collaudo.getDATACERTIFICATO() == null && collaudo.getMODALITACOLLAUDO() == null) {
				this.addAvviso(listaControlli, nomeTabella, "DATA_REGOLARE_ESEC", "E", pagina, "deve essere valorizzata se modalità collaudo non è valorizzato");
				this.addAvviso(listaControlli, nomeTabella, "MODO_COLLAUDO", "E", pagina, "deve essere valorizzata se data Regolare esecuzione non è valorizzato");
			} else if (collaudo.getDATACERTIFICATO() != null) {
				if (collaudo.getMODALITACOLLAUDO() != null) {
					this.addAvviso(listaControlli, nomeTabella, "MODO_COLLAUDO", "E", pagina, "non deve essere valorizzata se data Regolare esecuzione è valorizzato");
				}
				if (df.parse(collaudo.getDATACERTIFICATO()).compareTo(dataUltimazione) < 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_REGOLARE_ESEC", "E", pagina, "deve essere >= della data di ultimazione");
				}
			} else if (collaudo.getMODALITACOLLAUDO() != null) {
				if (collaudo.getDATACERTIFICATO() != null) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_REGOLARE_ESEC", "E", pagina, "non deve essere valorizzata se modalità collaudo è valorizzato");
				}
				if (collaudo.getDATACOLLAUDATORE() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_NOMINA_COLL", pagina);
				} else if (collaudo.getDATAINIZIO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_INIZIO_OPER", pagina);
				} else if (df.parse(collaudo.getDATAINIZIO()).compareTo(df.parse(collaudo.getDATACOLLAUDATORE())) < 0) {
						this.addAvviso(listaControlli, nomeTabella, "DATA_REGOLARE_ESEC", "E", pagina, "deve essere >= della data nomina collaudatore");
				} else if (collaudo.getDATAREDAZIONE() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_CERT_COLLAUDO", pagina);
				} else if (df.parse(collaudo.getDATAREDAZIONE()).compareTo(df.parse(collaudo.getDATAINIZIO())) < 0) {
						this.addAvviso(listaControlli, nomeTabella, "DATA_CERT_COLLAUDO", "E", pagina, "deve essere >= della data inizio operazioni di collaudo");
				} else if (collaudo.getDATADELIBERA() != null) {
					if (df.parse(collaudo.getDATADELIBERA()).compareTo(df.parse(collaudo.getDATAREDAZIONE())) < 0) {
						this.addAvviso(listaControlli, nomeTabella, "DATA_DELIBERA", "E", pagina, "deve essere >= della data redazione certificato di collaudo");
					}
				}
			}
			if (collaudo.getESITOCOLLAUDO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ESITO_COLLAUDO", pagina);
			}
			
			if (trasferimentoDati.getContrattiArray(0).getTIPOCONTRATTO().equals(TipologiaContratto.X_03)) {
				//LAVORI
				if (convertiImporto(collaudo.getIMPORTOLAVORI()) == 0) {
					this.addAvviso(listaControlli, nomeTabella, "IMP_FINALE_LAVORI", "E", pagina, "deve essere > 0");
				}
			} else {
				if (trasferimentoDati.getContrattiArray(0).getTIPOCONTRATTO().equals(TipologiaContratto.X_01)) {
					//FORNITURE
					if (convertiImporto(collaudo.getIMPORTOFORNITURE()) == 0) {
						this.addAvviso(listaControlli, nomeTabella, "IMP_FINALE_FORNIT", "E", pagina, "deve essere > 0");
					}
				} else {
					//SERVIZI
					if (convertiImporto(collaudo.getIMPORTOSERVIZI()) == 0) {
						this.addAvviso(listaControlli, nomeTabella, "IMP_FINALE_SERVIZI", "E", pagina, "deve essere > 0");
					}
				}
				
			}
			if (convertiImporto(collaudo.getIMPORTOPROGETTAZIONE()) > convertiImporto(collaudo.getIMPORTOLAVORI()) + convertiImporto(collaudo.getIMPORTOFORNITURE()) + convertiImporto(collaudo.getIMPORTOSERVIZI())) {
				this.addAvviso(listaControlli, nomeTabella, "IMP_PROGETTAZIONE", "E", pagina, "deve essere <= IMPORTO_LAVORI + IMPORTO_SERVIZI + IMPORTO_FORNITURE");
			}
			if (collaudo.getIMPORTOSOMMEDISPOSIZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_DISPOSIZIONE", pagina);
			} else {
				if (convertiImporto(collaudo.getIMPORTOSOMMEDISPOSIZIONE()) < 0) {
					this.addAvviso(listaControlli, nomeTabella, "IMP_DISPOSIZIONE", "E", pagina, "deve essere >= 0");
				}
			}
			
			if (collaudo.getLAVORIANNUALIESTESI() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "LAVORI_ESTESI", pagina);
			}
			if (collaudo.getTOTALERISERVE() > 0) {
				if (collaudo.getONERICOMPLESSIVI() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_COMPL_APPALTO", pagina);
				} else {
					if (convertiImporto(collaudo.getONERICOMPLESSIVI()) > importoComplessivoIntervento) {
						this.addAvviso(listaControlli, nomeTabella, "IMP_COMPL_APPALTO", "E", pagina, "deve essere <= importo consuntivo dell'intervento");
					}
				}
			}
			
			String importoTotale = "", importoDaDefinire = "";
			if (collaudo.getMODALITADEFINIZIONE() != null) {
				for(RISERVAType modalita:collaudo.getMODALITADEFINIZIONE().getRISERVEArray()) {
					if (modalita.getIDDEFINIZIONE().equals(RiserveType.X_1)) {
						pagina = "Fase di collaudo - Dati generali - Ulteriori riserve definite o da definire in via amministrativa in sede di collaudo";
						importoTotale = "AMM_IMPORTO_RICH";
						importoDaDefinire = "AMM_IMPORTO_DEF";
					} else if (modalita.getIDDEFINIZIONE().equals(RiserveType.X_2)) {
						pagina = "Fase di collaudo - Dati generali - Ulteriori riserve definite o da definire in via arbitrale";
						importoTotale = "ARB_IMPORTO_RICH";
						importoDaDefinire = "ARB_IMPORTO_DEF";
					} else if (modalita.getIDDEFINIZIONE().equals(RiserveType.X_3)) {
						pagina = "Fase di collaudo - Dati generali - Ulteriori riserve definite o da definire in via giudiziale";
						importoTotale = "GIU_IMPORTO_RICH";
						importoDaDefinire = "GIU_IMPORTO_DEF";
					} else if (modalita.getIDDEFINIZIONE().equals(RiserveType.X_4)) {
						pagina = "Fase di collaudo - Dati generali - Ulteriori riserve definite o da definire in via transattiva";
						importoTotale = "TRA_IMPORTO_RICH";
						importoDaDefinire = "TRA_IMPORTO_DEF";
					}
					if (modalita.getNUMERODEFINITE() > 0) {
						if (modalita.getIMPORTOTOTALE() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, importoTotale, pagina);
						} else {
							if (convertiImporto(modalita.getIMPORTOTOTALE()) > importoComplessivoIntervento) {
								this.addAvviso(listaControlli, nomeTabella, importoTotale, "E", pagina, "deve essere <= importo consuntivo dell'intervento");
							}
						}
					}
					if (modalita.getNUMERODADEFINIRE() > 0) {
						if (modalita.getIMPORTODEFINIZIONE() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, importoDaDefinire, pagina);
						} else {
							if (convertiImporto(modalita.getIMPORTODEFINIZIONE()) > importoComplessivoIntervento) {
								this.addAvviso(listaControlli, nomeTabella, importoDaDefinire, "E", pagina, "deve essere <= importo consuntivo dell'intervento");
							}
						}
					}
				}
			}
			
			nomeTabella = "W9INCA";
			pagina = "Fase di collaudo - Incarichi professionali";
			if (collaudo.getSOGGETTIINCARICATI() == null) {
				this.addAvviso(listaControlli, nomeTabella, "ID_RUOLO", "E", pagina, "Inserisci almeno un soggetto negli incarichi professionali");
			}

		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di collaudo"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneCollaudo)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneCollaudo: fine metodo");
		}
	}
	
	private void validazioneAdesione(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAdesione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		boolean obbligatorio = false;
		try {
			FASEADESIONEType adesione = trasferimentoDati.getContrattiArray(0).getSCHEDE().getFASEADESIONE();
			
			nomeTabella = "UFFINT";
			pagina = "Stazione appaltante";
			if (adesione.getCODFISCALESA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CFEIN", pagina);
			}
			if (adesione.getDENOMINAZIONESA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMEIN", pagina);
			}
			
			nomeTabella = "W9GARA";
			pagina = "Dati generali della gara";
			if (adesione.getCIGPADRE() != null) {
				if (!UtilitySITAT.isCigValido(adesione.getCIGPADRE())) {
					this.addAvviso(listaControlli, nomeTabella, "CIG_ACCQUADRO", "E", pagina, "Il codice cig non è valido");
				}
			} else {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CIG_ACCQUADRO", pagina);
			}
			nomeTabella = "W9LOTT";
			pagina = "Dati generali del lotto";
			if (adesione.getOGGETTOCONTRATTO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "OGGETTO", pagina);
			}
			pagina = "Dati generali - Dati economici";
			if (adesione.getCPV() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "CPV", pagina);
			}
			if (trasferimentoDati.getContrattiArray(0).getTIPOFLUSSO().equals(TipologiaFlusso.X_2)) {
				if (adesione.getIMPORTOLOTTO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_LOTTO", pagina);
				}
			}
			
			pagina = "Dati generali - Localizzazione";
			if (adesione.getLOCALIZZAZIONE() == null) {
				if (adesione.getCODICENUTS() == null) {
					this.addAvviso(listaControlli, nomeTabella, "LUOGO_ISTAT", "E", pagina, "Inserire almeno uno tra codice istat e codice nuts");
				}
			}
			if (adesione.getCODICENUTS() != null) {
				if (!adesione.getCODICENUTS().startsWith("ITC4")) {
					this.addAvviso(listaControlli, nomeTabella, "LUOGO_NUTS", "E", pagina, "Il codice nuts deve appartenere alla regione lombardia");
				}
			}
			
			nomeTabella = "W9APPA";
			pagina = "Dati aggiudicazione - Aggiudicazione / affidamento";
			if (adesione.getRIBASSOAGGIUDICAZIONE() != null && adesione.getOFFERTAAUMENTO() != null) {
				this.addAvviso(listaControlli, nomeTabella, "PERC_RIBASSO_AGG", "E", pagina, "non deve essere valorizzato contemporaneamente a \"Offerta in aumento %\"");
			}
			if (adesione.getDATAAGGIUDICAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", pagina);
			} else {
				if (df.parse(adesione.getDATAAGGIUDICAZIONE()).compareTo(new Date()) > 0 || df.parse(adesione.getDATAAGGIUDICAZIONE()).compareTo(df.parse("01-01-2008")) < 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", "E", pagina, "deve essere <= alla data odierna e >= a 01/01/2008");
				}
			}
			if (adesione.getSUBAPPALTOPRESTAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RICH_SUBAPPALTO", pagina);
			}
			
			pagina = "Dati aggiudicazione - Dati economici dell'appalto";
			if (adesione.getIMPORTOTOTALE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_COMPL_APPALTO", pagina);
			}
			if (adesione.getIMPORTOAGGIUDICAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", pagina);
			}
			
			if (trasferimentoDati.getContrattiArray(0).getTIPOCONTRATTO().equals(TipologiaContratto.X_03)) {
				//LAVORI
				if (adesione.getIMPORTOLAVORI() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_LAVORI", pagina);
				}
				
			} else {
				if (trasferimentoDati.getContrattiArray(0).getTIPOCONTRATTO().equals(TipologiaContratto.X_01)) {
					//FORNITURE
					if (adesione.getIMPORTOFORNITURE() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_FORNITURE", pagina);
					}
				} else {
					//SERVIZI
					if (adesione.getIMPORTOSERVIZI() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_SERVIZI", pagina);
					}
				}
				
			}
			
			nomeTabella = "W9INCA";
			pagina = "Incarichi professionali";
			obbligatorio = true;
			if (adesione.getINCARICHIPROFESSIONISTI() != null) {
				for(PROFESSIONISTAType professionista:adesione.getINCARICHIPROFESSIONISTI().getPROFESSIONISTAArray()) {
					if (professionista.getIDRUOLO().equals(RuoloResponsabileType.X_14)) {
						obbligatorio = false;
					}
				}
			}
			if (obbligatorio) {
				this.addAvviso(listaControlli, nomeTabella, "ID_RUOLO", "E", pagina, "Deve esistere almeno un RUP tra i professionisti");
			}
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di adesione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAdesione)", e);
		}


		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAdesione: fine metodo");
		}
	}
	
	private void validazioneStipula(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneStipula: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			
			FASESTIPULAType stipula = trasferimentoDati.getContrattiArray(0).getSCHEDE().getFASESTIPULA();
			String idFk = stipula.getIDFK();
			Long codgara = Long.parseLong(idFk.substring(0,7));
			Long codlott = Long.parseLong(idFk.substring(7,11));
			Long num = Long.parseLong(idFk.substring(11,14));
			Date dataAggiudicazione = (Date) sqlManager.getObject("select DATA_VERB_AGGIUDICAZIONE from W9APPA where codgara = ? and codlott = ? and num_appa = ? ", new Object[] { codgara, codlott, num });
			nomeTabella = "W9PUES";
			pagina = "Dati generali - Pubblicazione esito di gara ";
			if (stipula.getGUCE() != null) {
				if (df.parse(stipula.getGUCE()).compareTo(dataAggiudicazione) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_GUCE", "E", pagina, "deve essere > della data di aggiudicazione");
				}
			}
			if (stipula.getGURI() != null) {
				if (df.parse(stipula.getGURI()).compareTo(dataAggiudicazione) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_GURI", "E", pagina, "deve essere > della data di aggiudicazione");
				}
			}
			if (stipula.getQUOTINAZIONALI() > 20) {
				this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_NAZ", "E", pagina, "deve essere <= 20");
			}
			if (stipula.getQUOTILOCALI() > 20) {
				this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_REG", "E", pagina, "deve essere <= 20");
			}
			
			nomeTabella = "W9INIZ";
			pagina = "Dati generali - Contratto di appalto";
			if (stipula.getDATASTIPULA() != null) {
				if (df.parse(stipula.getDATASTIPULA()).compareTo(dataAggiudicazione) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_STIPULA", "E", pagina, "deve essere > della data di aggiudicazione");
				}
				if (stipula.getDATASCADENZA() != null) {
					if (df.parse(stipula.getDATASTIPULA()).compareTo(df.parse(stipula.getDATASCADENZA())) >= 0) {
						this.addAvviso(listaControlli, nomeTabella, "DATA_STIPULA", "E", pagina, "deve essere < della data di scadenza");
					}
				}
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di stipula"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneStipula)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneStipula: fine metodo");
		}
	}
	
	private void validazioneEsecuzione(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneEsecuzione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			FASEESECUZIONEType esecuzione = trasferimentoDati.getContrattiArray(0).getSCHEDE().getFASEESECUZIONEArray(0);
			String idFk = esecuzione.getIDFK();
			Long codgara = Long.parseLong(idFk.substring(0,7));
			Long codlott = Long.parseLong(idFk.substring(7,11));
			Long num = Long.parseLong(idFk.substring(11,14));
			Double importoComplessivo = (Double) sqlManager.getObject("select " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_LAVORI","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_SERVIZI","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_FORNITURE","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_PROGETTAZIONE","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"IMPORTO_ATTUAZIONE_SICUREZZA","0"}) +
					", " + sqlManager.getDBFunction("isnull",new String[] {"ALTRE_SOMME","0"}) +
					" from W9APPA where codgara = ? and codlott = ? and num_appa = ? ", new Object[] { codgara, codlott, num });
			
			nomeTabella = "W9AVAN";
			pagina = "Fase di esecuzione ed avanzamento del contratto - Stato di avanzamento";
			if (esecuzione.getSOMMEINDENARO() == esecuzione.getTRASFERIMENTOPROPRIETA() && (esecuzione.getSOMMEINDENARO().equals(SnType.N) || esecuzione.getSOMMEINDENARO() == null)){
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_PAGAMENTO", pagina);
			}
			if (esecuzione.getANTICIPAZIONE() != null) {
				if(convertiImporto(esecuzione.getANTICIPAZIONE()) > importoComplessivo) {
					this.addAvviso(listaControlli, nomeTabella, "IMPORTO_ANTICIPAZIONE", "E", pagina, "deve essere <= dell'importo complessivo dell'appalto");
				}
				if (esecuzione.getDATAPAGAMENTO() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_ANTICIPAZIONE", pagina);
				}
			}
			if (esecuzione.getDATASAL() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_RAGGIUNGIMENTO", pagina);
			}
			
			if (esecuzione.getIMPORTOSAL() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_SAL", pagina);
			} else if (convertiImporto(esecuzione.getIMPORTOSAL()).compareTo(importoComplessivo) > 0) {
				this.addAvviso(listaControlli, nomeTabella, "IMPORTO_SAL", "E", pagina, "deve essere <= dell'importo complessivo dell'appalto");
			}
			if (esecuzione.getIMPORTOCERTIFICATO() != null) {
				this.addAvviso(listaControlli, nomeTabella, "IMPORTO_CERTIFICATO", "E", pagina, "deve essere <= dell'importo complessivo dell'appalto");
			}
			if (esecuzione.getAVANZAMENTORAGGIUNTO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RITARDO", pagina);
			} else {
				if (esecuzione.getAVANZAMENTORAGGIUNTO() != AvanzamentoType.X_3) {
					if (esecuzione.getSCOSTAMENTO() == 0) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_GIORNI_SCOST", pagina);
					}
				}
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di esecuzione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneEsecuzione)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneEsecuzione: fine metodo");
		}
	}
	
	private void validazioneSospensione(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneSospensione: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			
			SOSPENSIONEType sospensione = trasferimentoDati.getContrattiArray(0).getSCHEDE().getEVENTOSOSPENSIONEArray(0);
			String idFk = sospensione.getIDFK();
			Long codgara = Long.parseLong(idFk.substring(0,7));
			Long codlott = Long.parseLong(idFk.substring(7,11));
			Long num = Long.parseLong(idFk.substring(11,14));
			Date dataEffettiva = (Date) sqlManager.getObject("select DATA_VERB_INIZIO from W9INIZ where codgara = ? and codlott = ? and num_iniz = ? ", new Object[] { codgara, codlott, num });
			
			nomeTabella = "W9SOSP";
			pagina = "Sospensione dell'esecuzione";
			if (sospensione.getDATASOSPENSIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_SOSP", pagina);
			} else {
				if (df.parse(sospensione.getDATASOSPENSIONE()).compareTo(dataEffettiva) < 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_SOSP", "E", pagina, "deve essere >= della data di effettivo inizio");
				}
			}
			if (sospensione.getDATARIPRESA() != null) {
				if (df.parse(sospensione.getDATARIPRESA()).compareTo(df.parse(sospensione.getDATASOSPENSIONE())) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_RIPR", "E", pagina, "deve essere > della data del verbale di sospensione");
				}
			}
			
			if (sospensione.getMOTIVOSOSPENSIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MOTIVO_SOSP", pagina);
			}
			
			if (sospensione.getRECUPERO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_SUPERO_TEMPO", pagina);
			}
			
			if (sospensione.getRISERVE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RISERVE", pagina);
			}
			
			if (sospensione.getVERBALI() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_VERBALE", pagina);
			}

		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione della fase di sospensione"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneSospensione)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneSospensione: fine metodo");
		}
	}
	
	private void validazioneVariante(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneVariante: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			
			VARIANTEType variante= trasferimentoDati.getContrattiArray(0).getSCHEDE().getEVENTOVARIANTEArray(0);
			String idFk = variante.getIDFK();
			Long codgara = Long.parseLong(idFk.substring(0,7));
			Long codlott = Long.parseLong(idFk.substring(7,11));
			Long num = Long.parseLong(idFk.substring(11,14));
			Date dataEffettiva = (Date) sqlManager.getObject("select DATA_VERB_INIZIO from W9INIZ where codgara = ? and codlott = ? and num_iniz = ? ", new Object[] { codgara, codlott, num });
			Date dataStipula = (Date) sqlManager.getObject("select DATA_STIPULA from W9INIZ where codgara = ? and codlott = ? and num_iniz = ? ", new Object[] { codgara, codlott, num });
			
			nomeTabella = "W9VARI";
			pagina = "Variante";
			if (variante.getDATAAPPROVAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_APPR", pagina);
			} else {
				if (dataEffettiva != null && df.parse(variante.getDATAAPPROVAZIONE()).compareTo(dataEffettiva) < 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_APPR", "E", pagina, "deve essere >= della data di effettivo inizio");
				}
			}
			if (variante.getMOTIVAZIONE() == null) {
				if (variante.getALTREMOTIVAZIONI() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ALTRE_MOTIVAZIONI", pagina);
				}
			}
			if (variante.getIMPORTOFORNITURE() == null && variante.getIMPORTOSERVIZI() == null) {
				if (variante.getIMPORTOLAVORI() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_RIDET_LAVORI", pagina);
				}
			}
			if (variante.getIMPORTOLAVORI() == null && variante.getIMPORTOSERVIZI() == null) {
				if (variante.getIMPORTOFORNITURE() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_RIDET_FORNIT", pagina);
				}
			}
			if (variante.getIMPORTOFORNITURE() == null && variante.getIMPORTOLAVORI() == null) {
				if (variante.getIMPORTOSERVIZI() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_RIDET_SERVIZI", pagina);
				}
			}
			if (variante.getDATASOTTOSCRIZIONE() != null) {
				if (dataStipula != null && df.parse(variante.getDATASOTTOSCRIZIONE()).compareTo(dataStipula) <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_ATTO_AGGIUNTIVO", "E", pagina, "deve essere > della data di stipula contratto");
				}
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione dell'evento variante"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneVariante)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneVariante: fine metodo");
		}
	}
	
	private void validazioneAccordoBonario(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAccordoBonario: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			
			ACCORDOBONARIOType accordo = trasferimentoDati.getContrattiArray(0).getSCHEDE().getEVENTOACCORDOBONARIO();
			String idFk = accordo.getIDFK();
			Long codgara = Long.parseLong(idFk.substring(0,7));
			Long codlott = Long.parseLong(idFk.substring(7,11));
			Long num = Long.parseLong(idFk.substring(11,14));
			Date dataEffettiva = (Date) sqlManager.getObject("select DATA_VERB_INIZIO from W9INIZ where codgara = ? and codlott = ? and num_iniz = ? ", new Object[] { codgara, codlott, num });
			
			nomeTabella = "W9ACCO";
			pagina = "Accordo bonario ";
			if (accordo.getDATAACCORDO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_ACCORDO", pagina);
			} else {
				if (df.parse(accordo.getDATAACCORDO()).compareTo(dataEffettiva) < 0) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_ACCORDO", "E", pagina, "deve essere >= della data di effettivo inizio");
				}
			}

		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione dell'evento Accordo Bonario"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneAccordoBonario)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneAccordoBonario: fine metodo");
		}
	}
	
	private void validazioneSubappalto(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneSubappalto: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			
			EVENTOSUBAPPALTOType subappalto= trasferimentoDati.getContrattiArray(0).getSCHEDE().getEVENTOSUBAPPALTOArray(0);
			
			nomeTabella = "W9SUBA";
			pagina = "Subappalto";
			if (subappalto.getCODFISCDITTA() == null) {
				this.addAvviso(listaControlli, nomeTabella, "CODIMP", "E", pagina, "codice fiscale obbligatorio");
			} 
			if (subappalto.getCODFISCDITTAAGGIUD() == null) {
				this.addAvviso(listaControlli, nomeTabella, "CODIMP_AGGIUDICATRICE", "E", pagina, "codice fiscale obbligatorio");
			}
			if (convertiImporto(subappalto.getIMPORTOPRESUNTO()) <= 0) {
				this.addAvviso(listaControlli, nomeTabella, "IMPORTO_PRESUNTO", "E", pagina, "deve essere > 0");
			}
			if (subappalto.getCODICECATEGORIA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CATEGORIA", pagina);
			}
			if (subappalto.getCODICECPV() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CPV", pagina);
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione dell'evento Subappalto"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneSubappalto)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneSubappalto: fine metodo");
		}
	}
	
	private void validazioneRecesso(TrasferimentoDatiType trasferimentoDati, List<Object> listaControlli) {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneRecesso: inizio metodo");
		}

		String nomeTabella = "";
		String pagina = "";

		try {
			
			EVENTOR129Type recesso= trasferimentoDati.getContrattiArray(0).getSCHEDE().getEVENTOR129();
			
			nomeTabella = "W9RITA";
			pagina = "Ritardo o sospensione nella consegna";
			
			if (recesso.getDATATERMINE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_TERMINE", pagina);
			} 
			if (recesso.getDATACONSEGNA() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_CONSEGNA", pagina);
			} 
			if (recesso.getIDTIPOLOGIACOMUNICAZIONE() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPO_COMUN", pagina);
			}
			
			pagina = "Istanza di recesso";
			if (recesso.getDATARECESSO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_IST_RECESSO", pagina);
			}
			if (recesso.getRECESSO() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RIPRESA", pagina);
			}
			if (recesso.getRISERVE()== null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RISERVA", pagina);
			}
			
		} catch (Exception e) {
			listaControlli.add(((new Object[] { "E", "Errore imprevisto", "", "Errore durante la validazione dell'evento Istanza di Recesso"})));
			logger.error("Errore nella lettura delle informazioni relative al contratto (validazioneRecesso)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneRecesso: fine metodo");
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
	 * Aggiunge un messaggio di avviso alla listaControlli.
	 * 
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param nomeTabella
	 *            Nome della tabella
	 * @param nomeCampo
	 *            Nome del campo
	 * @param tipo
	 *            Tipo di messaggio
	 * @param pagina
	 *            Pagina
	 * @param messaggio
	 *            Messaggio
	 */
	private void addAvviso(List<Object> listaControlli, final String nomeTabella, final String nomeCampo, final String tipo, final String pagina, final String messaggio) {
		if (logger.isDebugEnabled()) {
			logger.debug("addAvviso: inizio metodo");
		}

		listaControlli.add(((new Object[] { tipo, pagina, this.getDescrizioneCampo(nomeTabella, nomeCampo), messaggio })));

		if (logger.isDebugEnabled()) {
			logger.debug("addAvviso: fine metodo");
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

