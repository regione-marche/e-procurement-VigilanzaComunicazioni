/*
 * Created on 25/giu/09
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.bl;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityExcel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.springframework.transaction.TransactionStatus;

/**
 * Classe di gestione delle funzionalita' inerenti  l'esportazione e 
 * l'importazione in formato Excel per OEPV
 * 
 * @author Stefano.Cestaro
 */
public class ImportaAcquistiInterventiManager {

	/** Logger */
	static Logger logger = Logger.getLogger(ImportaAcquistiInterventiManager.class);
	  
	/** Manager SQL per le operazioni su database */
	private SqlManager sqlManager;
	private GeneManager geneManager;

	/** Nome del file associato al file Excel contenente l'export OEPV */
	public static final String NOME_FILE_C0OGGASS_EXPORT = "AliceGare_OEPV.xls";

	/** Titolo del documento associato con cui viene inserito il file Excel
	 * contenente l'export OEPV */
	public static final String TITOLO_FILE_C0OGASS_EXPORT =
			"Criteri valutazione e punteggi ditte formato excel(esportazione)";
	
	/** Nome del file associato al file Excel contenente l'import OEPV */
	public static final String NOME_FILE_C0OGGASS_IMPORT = "AliceGare_OEPV-import.xls";

	/** Titolo del documento associato con cui viene inserito il file Excel
	 * contenente l'import OEPV */	
	public static final String TITOLO_FILE_C0OGASS_IMPORT = 
		"Criteri valutazione e punteggi ditte formato excel(importazione)";

	/** Definizione query sql */
	private static final String querySelectTipoProgramma = "select TIPROG from PIATRI where CONTRI = ?";
	private static final String querySelectSA = "select CENINT from PIATRI where CONTRI = ?";
	private static final String queryDeleteInttri = "delete from INTTRI where CONTRI = ?";
	private static final String querySelectRup = "select CODTEC from TECNI where UPPER(CFTEC) = ? and CGENTEI = ?";
	
	private static final String queryInsertTecni = "INSERT INTO TECNI(CODTEC,NOMETEI,COGTEI,NOMTEC,CFTEC,CGENTEI) VALUES(?,?,?,?,?,?)";
	
	/** Variabile per l'intero foglio EXCEL */
	private HSSFWorkbook workXLS;

	/**
	 * Set SqlManager
	 *	 
	 * @param sqlManager
	 */
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}
	public void setGeneManager(GeneManager geneManager) {
		this.geneManager = geneManager;
	}
	/**
	 * Importazione da formato XLS
	 * 
	 * @param XLS file excel
	 * @throws GestoreException
	 */
	public void importazione(HSSFWorkbook XLS, Long contri) throws GestoreException {
		this.workXLS = XLS;

		boolean commitTransaction = false;
	    TransactionStatus status = null;
	    int riga = 1;
	    try {
	    	status = this.sqlManager.startTransaction();
			//ricavo il tipo di programma
			Long tiprog = (Long) this.sqlManager.getObject(querySelectTipoProgramma, new Object[] {contri});
			if (tiprog != null) {
				String codein = (String) this.sqlManager.getObject(querySelectSA, new Object[] {contri});
				//cancello tutti gli interventi del programma
				this.sqlManager.update(queryDeleteInttri, new Object[] {contri});
				HSSFSheet mainSheet;
				if (tiprog.equals(new Long(1))) {
					mainSheet = workXLS.getSheetAt(1);
				} else {
					mainSheet = workXLS.getSheetAt(0);
				}
				
				FormulaEvaluator evaluator = workXLS.getCreationHelper().createFormulaEvaluator();
				//
				//--DV3TRI,MU3TRI,PR3TRI,BI3TRI,AP3TRI,IM3TRI,AL3TRI,DI3INT--
				logger.info("Inserimento programma tipo " + tiprog + " con contri = " + contri);
				String queryAquisti = "INSERT INTO INTTRI(CONTRI,CONINT,NPROGINT,TIPOIN,CUIINT,DESINT,ANNRIF,FLAG_CUP,CUPPRG,ACQALTINT,CUICOLL,LOTFUNZ,NUTS,CODCPV,PRGINT,DURCONT,CONTESS,DV1TRI,MU1TRI,PR1TRI,BI1TRI,AP1TRI,IM1TRI,AL1TRI,DI1INT,DV2TRI,MU2TRI,PR2TRI,BI2TRI,AP2TRI,IM2TRI,AL2TRI,DI2INT,DV9TRI,MU9TRI,PR9TRI,BI9TRI,AP9TRI,IM9TRI,AL9TRI,DI9INT,SPESESOST,DITINT,TOTINT,TCPINT,DELEGA,CODAUSA,SOGAGG,VARIATO)";
				String valuesAcquisti = "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String queryInterventi = "INSERT INTO INTTRI(CONTRI,CONINT,NPROGINT,TIPOIN,CODINT,CUIINT,DESINT,ANNRIF,FLAG_CUP,CUPPRG,LOTFUNZ,LAVCOMPL,NUTS,COMINT,PRGINT,SEZINT,INTERV,FIINTR,URCINT,APCINT,STAPRO,DV1TRI,MU1TRI,PR1TRI,BI1TRI,AP1TRI,IM1TRI,AL1TRI,DI1INT,DV2TRI,MU2TRI,PR2TRI,BI2TRI,AP2TRI,IM2TRI,AL2TRI,DI2INT,DV3TRI,MU3TRI,PR3TRI,BI3TRI,AP3TRI,IM3TRI,AL3TRI,DI3INT,DV9TRI,MU9TRI,PR9TRI,BI9TRI,AP9TRI,IM9TRI,AL9TRI,DI9INT,SPESESOST,DITINT,TOTINT,TCPINT,SCAMUT,DELEGA,CODAUSA,SOGAGG,VARIATO)";
				String valuesInterventi = "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				String queryUpdateInttri = "UPDATE INTTRI SET TIPINT = ?, ANNINT = ? , CODRUP = ?, ICPINT = COALESCE(PR1TRI,0) + COALESCE(PR2TRI,0) + COALESCE(PR3TRI,0) + COALESCE(PR9TRI,0) WHERE CONTRI = ? AND CONINT = ?";
				if (mainSheet != null) {
					
					//String cfSA = UtilityExcel.leggiCellaString(mainSheet,2,4);
					for(int i = 12; i < mainSheet.getLastRowNum() + 1; i ++) {
						List<Object> parametri = new ArrayList<Object>();
						HSSFRow row = mainSheet.getRow(i - 1);
						Double rigavalida = UtilityExcel.leggiCellaDouble(mainSheet,1,i);
						if (row.getLastCellNum()>=49 && rigavalida != null) {
							//ricavo il codice rup
							String nomeRUP = null;
							String cognomeRUP = null;
							String cfRUP = null;
							Double anntri = null;
							Double annrif = null;
							if (tiprog.equals(new Long(1))) {
								String tipoin = "L";
								String codint = (UtilityExcel.leggiCellaDouble(mainSheet,2,i)!=null?""+UtilityExcel.leggiCellaDouble(mainSheet,2,i).intValue():UtilityExcel.leggiCellaString(mainSheet,2,i));
								anntri = UtilityExcel.leggiCellaDouble(mainSheet,3,5);
								CellValue cellValue = evaluator.evaluate(row.getCell(3));
								String cuiint = cellValue.getStringValue();
								String desint = UtilityExcel.leggiCellaString(mainSheet,5,i);
								annrif = UtilityExcel.leggiCellaDouble(mainSheet,6,i);
								String cup = UtilityExcel.leggiCellaString(mainSheet,7,i);
								String lottofunz = UtilityExcel.leggiCellaString(mainSheet,8,i);
								String lavcompl = UtilityExcel.leggiCellaString(mainSheet,9,i);
								
								String nuts = (UtilityExcel.leggiCellaDouble(mainSheet,10,i)!=null?""+UtilityExcel.leggiCellaDouble(mainSheet,10,i).intValue():UtilityExcel.leggiCellaString(mainSheet,10,i));
								
								String prgint = UtilityExcel.leggiCellaString(mainSheet,11,i);
								
								nomeRUP = UtilityExcel.leggiCellaString(mainSheet,12,i);
								cognomeRUP = UtilityExcel.leggiCellaString(mainSheet,13,i);
								cfRUP = UtilityExcel.leggiCellaString(mainSheet,14,i);
								
								String sezint = UtilityExcel.leggiCellaString(mainSheet,15,i);
								String interv = UtilityExcel.leggiCellaString(mainSheet,16,i);
								String fiintr = UtilityExcel.leggiCellaString(mainSheet,17,i);
								
								String urcint = UtilityExcel.leggiCellaString(mainSheet,18,i);
								String apcint = UtilityExcel.leggiCellaString(mainSheet,19,i);
								String stapro = UtilityExcel.leggiCellaString(mainSheet,20,i);
								
								Double dv1tri = UtilityExcel.leggiCellaDouble(mainSheet,21,i);
								Double mu1tri = UtilityExcel.leggiCellaDouble(mainSheet,22,i);
								Double pr1tri = UtilityExcel.leggiCellaDouble(mainSheet,23,i);
								Double bi1tri = UtilityExcel.leggiCellaDouble(mainSheet,24,i);
								Double ap1tri = UtilityExcel.leggiCellaDouble(mainSheet,25,i);
								Double im1tri = UtilityExcel.leggiCellaDouble(mainSheet,26,i);
								Double al1tri = UtilityExcel.leggiCellaDouble(mainSheet,27,i);
								Double di1int = UtilityExcel.leggiCellaDouble(mainSheet,28,i);
								
								Double dv2tri = UtilityExcel.leggiCellaDouble(mainSheet,29,i);
								Double mu2tri = UtilityExcel.leggiCellaDouble(mainSheet,30,i);
								Double pr2tri = UtilityExcel.leggiCellaDouble(mainSheet,31,i);
								Double bi2tri = UtilityExcel.leggiCellaDouble(mainSheet,32,i);
								Double ap2tri = UtilityExcel.leggiCellaDouble(mainSheet,33,i);
								Double im2tri = UtilityExcel.leggiCellaDouble(mainSheet,34,i);
								Double al2tri = UtilityExcel.leggiCellaDouble(mainSheet,35,i);
								Double di2int = UtilityExcel.leggiCellaDouble(mainSheet,36,i);
								
								Double dv3tri = UtilityExcel.leggiCellaDouble(mainSheet,37,i);
								Double mu3tri = UtilityExcel.leggiCellaDouble(mainSheet,38,i);
								Double pr3tri = UtilityExcel.leggiCellaDouble(mainSheet,39,i);
								Double bi3tri = UtilityExcel.leggiCellaDouble(mainSheet,40,i);
								Double ap3tri = UtilityExcel.leggiCellaDouble(mainSheet,41,i);
								Double im3tri = UtilityExcel.leggiCellaDouble(mainSheet,42,i);
								Double al3tri = UtilityExcel.leggiCellaDouble(mainSheet,43,i);
								Double di3int = UtilityExcel.leggiCellaDouble(mainSheet,44,i);
								
								Double dv9tri = UtilityExcel.leggiCellaDouble(mainSheet,45,i);
								Double mu9tri = UtilityExcel.leggiCellaDouble(mainSheet,46,i);
								Double pr9tri = UtilityExcel.leggiCellaDouble(mainSheet,47,i);
								Double bi9tri = UtilityExcel.leggiCellaDouble(mainSheet,48,i);
								Double ap9tri = UtilityExcel.leggiCellaDouble(mainSheet,49,i);
								Double im9tri = UtilityExcel.leggiCellaDouble(mainSheet,50,i);
								Double al9tri = UtilityExcel.leggiCellaDouble(mainSheet,51,i);
								Double di9int = UtilityExcel.leggiCellaDouble(mainSheet,52,i);
								
								Double spesesost = UtilityExcel.leggiCellaDouble(mainSheet,53,i);
								Double ditint = UtilityExcel.leggiCellaDouble(mainSheet,54,i);
								String tcpint = UtilityExcel.leggiCellaString(mainSheet,55,i);

								java.util.Date scamut = row.getCell(55).getDateCellValue();
								
								String codausa = (UtilityExcel.leggiCellaDouble(mainSheet,57,i)!=null?""+UtilityExcel.leggiCellaDouble(mainSheet,57,i).intValue():UtilityExcel.leggiCellaString(mainSheet,57,i));
								String sogagg = UtilityExcel.leggiCellaString(mainSheet,58,i);
								String variato = UtilityExcel.leggiCellaString(mainSheet,59,i);
								
								parametri.add(contri);
								parametri.add(new Long(riga));
								parametri.add(new Long(riga));
								parametri.add(tipoin.substring(0, 1));
								parametri.add((codint != null)?codint:null);
								parametri.add(cuiint);
								parametri.add(desint);
								parametri.add((long)(annrif - anntri + 1));
								if (cup != null) {
									parametri.add("2");
								} else {
									parametri.add("1");
								}
								parametri.add(cup);
								
								parametri.add((lottofunz != null && lottofunz.length() > 0)?lottofunz.substring(0, 1):null);
								parametri.add((lavcompl != null && lavcompl.length() > 0)?lavcompl.substring(0, 1):null);
								if (nuts != null) {
									if("0123456789".indexOf(nuts.substring(0, 1)) == -1) {
										parametri.add((nuts != null && nuts.length() > 0)?nuts:null);
										parametri.add(null);
									} else {
										parametri.add(null);
										parametri.add((nuts != null && nuts.length() > 0)?StringUtils.leftPad(nuts, 9, '0'):null);
									}
								} else {
									parametri.add(null);
									parametri.add(null);
								}
								parametri.add((prgint != null && prgint.length() > 0)?new Long(prgint.substring(0, 1)):null);
								
								parametri.add((sezint != null && sezint.length() > 1)?sezint.substring(0, 2):null);
								parametri.add((interv != null && interv.length() > 4)?interv.substring(0, 5):null);
								if (fiintr != null) {
									int index = fiintr.indexOf(".");
									if (index != -1) {
										parametri.add(fiintr.substring(0, index));
									} else {
										parametri.add(null);
									}
								} else {
									parametri.add(null);
								}
								
								parametri.add((urcint != null && urcint.length() > 0)?urcint.substring(0, 1):null);
								parametri.add((apcint != null && apcint.length() > 0)?apcint.substring(0, 1):null);
								parametri.add((stapro != null && stapro.length() > 0)?stapro.substring(0, 1):null);
																
								parametri.add(dv1tri);
								parametri.add(mu1tri);
								parametri.add(pr1tri);
								parametri.add(bi1tri);
								parametri.add(ap1tri);
								parametri.add(im1tri);
								parametri.add(al1tri);
								parametri.add(di1int);
								
								parametri.add(dv2tri);
								parametri.add(mu2tri);
								parametri.add(pr2tri);
								parametri.add(bi2tri);
								parametri.add(ap2tri);
								parametri.add(im2tri);
								parametri.add(al2tri);
								parametri.add(di2int);
								
								parametri.add(dv3tri);
								parametri.add(mu3tri);
								parametri.add(pr3tri);
								parametri.add(bi3tri);
								parametri.add(ap3tri);
								parametri.add(im3tri);
								parametri.add(al3tri);
								parametri.add(di3int);
								
								parametri.add(dv9tri);
								parametri.add(mu9tri);
								parametri.add(pr9tri);
								parametri.add(bi9tri);
								parametri.add(ap9tri);
								parametri.add(im9tri);
								parametri.add(al9tri);
								parametri.add(di9int);
								
								parametri.add(spesesost);
								parametri.add(ditint);
								parametri.add(ditint);
								parametri.add((tcpint != null && tcpint.length() > 0)?tcpint.substring(0, 1):null);

								parametri.add(scamut);
								if (codausa!=null && codausa.length()>0) {
									parametri.add("1");
								} else {
									parametri.add("2");
								}
								parametri.add(codausa);
								parametri.add(sogagg);
								parametri.add((variato != null && variato.length() > 0)?new Long(variato.substring(0, 1)):null);
								
							} else {
								String tipoin = UtilityExcel.leggiCellaString(mainSheet,2,i);
								anntri = UtilityExcel.leggiCellaDouble(mainSheet,2,5);
								CellValue cellValue = evaluator.evaluate(row.getCell(3));
								String cuiint = cellValue.getStringValue();
								String desint = UtilityExcel.leggiCellaString(mainSheet,5,i);
								annrif = UtilityExcel.leggiCellaDouble(mainSheet,6,i);
								String flagcup = UtilityExcel.leggiCellaString(mainSheet,7,i);
								String cup = UtilityExcel.leggiCellaString(mainSheet,8,i);
								String acqaltint = UtilityExcel.leggiCellaString(mainSheet,9,i);
								String cuicoll = UtilityExcel.leggiCellaString(mainSheet,10,i);
								String lottofunz = UtilityExcel.leggiCellaString(mainSheet,11,i);
								String nuts = UtilityExcel.leggiCellaString(mainSheet,12,i);
								String codcpv = UtilityExcel.leggiCellaString(mainSheet,13,i);
								String prgint = UtilityExcel.leggiCellaString(mainSheet,14,i);
								
								nomeRUP = UtilityExcel.leggiCellaString(mainSheet,15,i);
								cognomeRUP = UtilityExcel.leggiCellaString(mainSheet,16,i);
								cfRUP = UtilityExcel.leggiCellaString(mainSheet,17,i);
								
								Double durcont = UtilityExcel.leggiCellaDouble(mainSheet,18,i);
								String contess = UtilityExcel.leggiCellaString(mainSheet,19,i);
								
								Double dv1tri = UtilityExcel.leggiCellaDouble(mainSheet,20,i);
								Double mu1tri = UtilityExcel.leggiCellaDouble(mainSheet,21,i);
								Double pr1tri = UtilityExcel.leggiCellaDouble(mainSheet,22,i);
								Double bi1tri = UtilityExcel.leggiCellaDouble(mainSheet,23,i);
								Double ap1tri = UtilityExcel.leggiCellaDouble(mainSheet,24,i);
								Double im1tri = UtilityExcel.leggiCellaDouble(mainSheet,25,i);
								Double al1tri = UtilityExcel.leggiCellaDouble(mainSheet,26,i);
								Double di1int = UtilityExcel.leggiCellaDouble(mainSheet,27,i);
								
								Double dv2tri = UtilityExcel.leggiCellaDouble(mainSheet,28,i);
								Double mu2tri = UtilityExcel.leggiCellaDouble(mainSheet,29,i);
								Double pr2tri = UtilityExcel.leggiCellaDouble(mainSheet,30,i);
								Double bi2tri = UtilityExcel.leggiCellaDouble(mainSheet,31,i);
								Double ap2tri = UtilityExcel.leggiCellaDouble(mainSheet,32,i);
								Double im2tri = UtilityExcel.leggiCellaDouble(mainSheet,33,i);
								Double al2tri = UtilityExcel.leggiCellaDouble(mainSheet,34,i);
								Double di2int = UtilityExcel.leggiCellaDouble(mainSheet,35,i);
								
								Double dv9tri = UtilityExcel.leggiCellaDouble(mainSheet,36,i);
								Double mu9tri = UtilityExcel.leggiCellaDouble(mainSheet,37,i);
								Double pr9tri = UtilityExcel.leggiCellaDouble(mainSheet,38,i);
								Double bi9tri = UtilityExcel.leggiCellaDouble(mainSheet,39,i);
								Double ap9tri = UtilityExcel.leggiCellaDouble(mainSheet,40,i);
								Double im9tri = UtilityExcel.leggiCellaDouble(mainSheet,41,i);
								Double al9tri = UtilityExcel.leggiCellaDouble(mainSheet,42,i);
								Double di9int = UtilityExcel.leggiCellaDouble(mainSheet,43,i);
								
								Double spesesost = UtilityExcel.leggiCellaDouble(mainSheet,44,i);
								Double ditint = UtilityExcel.leggiCellaDouble(mainSheet,45,i);
								String tcpint = UtilityExcel.leggiCellaString(mainSheet,46,i);
								String codausa = (UtilityExcel.leggiCellaDouble(mainSheet,47,i)!=null?""+UtilityExcel.leggiCellaDouble(mainSheet,47,i).intValue():UtilityExcel.leggiCellaString(mainSheet,47,i));
								
								String sogagg = UtilityExcel.leggiCellaString(mainSheet,48,i);
								String variato = UtilityExcel.leggiCellaString(mainSheet,49,i);
								
								parametri.add(contri);
								parametri.add(new Long(riga));
								parametri.add(new Long(riga));
								parametri.add(tipoin.substring(0, 1));
								parametri.add(cuiint);
								parametri.add(desint);
								parametri.add((long)(annrif - anntri + 1));
								parametri.add((flagcup != null && flagcup.length() > 0)?flagcup.substring(0, 1):null);
								parametri.add(cup);
								parametri.add((acqaltint != null && acqaltint.length() > 0)?new Long(acqaltint.substring(0, 1)):null);
								parametri.add((cuicoll != null)?cuicoll:null);
								parametri.add((lottofunz != null && lottofunz.length() > 0)?lottofunz.substring(0, 1):null);
								parametri.add((nuts != null && nuts.length() > 0)?nuts.trim():null);
								parametri.add(codcpv);
								parametri.add((prgint != null && prgint.length() > 0)?new Long(prgint.substring(0, 1)):null);
								parametri.add((durcont != null)?new Long(durcont.longValue()):null);
								parametri.add((contess != null && contess.length() > 0)?contess.substring(0, 1):null);
								
								parametri.add(dv1tri);
								parametri.add(mu1tri);
								parametri.add(pr1tri);
								parametri.add(bi1tri);
								parametri.add(ap1tri);
								parametri.add(im1tri);
								parametri.add(al1tri);
								parametri.add(di1int);
								
								parametri.add(dv2tri);
								parametri.add(mu2tri);
								parametri.add(pr2tri);
								parametri.add(bi2tri);
								parametri.add(ap2tri);
								parametri.add(im2tri);
								parametri.add(al2tri);
								parametri.add(di2int);
								
								parametri.add(dv9tri);
								parametri.add(mu9tri);
								parametri.add(pr9tri);
								parametri.add(bi9tri);
								parametri.add(ap9tri);
								parametri.add(im9tri);
								parametri.add(al9tri);
								parametri.add(di9int);
								
								parametri.add(spesesost);
								parametri.add(ditint);
								parametri.add(ditint);
								parametri.add((tcpint != null && tcpint.length() > 0)?tcpint.substring(0, 1):null);
								if (codausa!=null && codausa.length()>0) {
									parametri.add("1");
								} else {
									parametri.add("2");
								}
								parametri.add(codausa);
								parametri.add(sogagg);
								parametri.add((variato != null && variato.length() > 0)?new Long(variato.substring(0, 1)):null);
								
							}
							//recupero rup
							String codrup = "";
							if (cfRUP != null && cfRUP.length()>0) {
								codrup = (String) this.sqlManager.getObject(querySelectRup, new Object[] {cfRUP.toUpperCase(), codein});
								if (codrup == null) {
									//procedo con l'inserimento del nuovo tecnico e il recupero del codice
									codrup = geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
									this.sqlManager.update(queryInsertTecni, new Object[]{codrup, nomeRUP, cognomeRUP, (nomeRUP!=null?nomeRUP:"") + " " + (cognomeRUP!=null?cognomeRUP:""), cfRUP, codein});
								}
							}
							
							if (tiprog.equals(new Long(1))) {
								this.sqlManager.update(queryInterventi + valuesInterventi, parametri.toArray());
								Long testAnnint = (long)(annrif - anntri + 1);
								this.sqlManager.update(queryUpdateInttri, new Object[]{new Long(1), (testAnnint>1)?"2":"1", codrup, contri, new Long(riga)});
							} else {
								this.sqlManager.update(queryAquisti + valuesAcquisti, parametri.toArray());
								Long testAnnint = (long)(annrif - anntri + 1);
								this.sqlManager.update(queryUpdateInttri, new Object[]{new Long(2), (testAnnint>1)?"2":"1", codrup, contri, new Long(riga)});
							}
							
							//aggiorna inttri
							riga ++;
						}
						else {
							break;
						}
					}
				}
				commitTransaction = true;
			} else {
				logger.info("il programma " + contri + " non esiste");
			}
			
		} catch (Exception e) {
			logger.error(e);
			throw new GestoreException("Errore durante l'importazione degli interventi / acquisti","commons.validate",new Object[] { e.getMessage() },null);
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
	
}