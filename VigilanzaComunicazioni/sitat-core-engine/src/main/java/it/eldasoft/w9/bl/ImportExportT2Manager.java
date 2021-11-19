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
package it.eldasoft.w9.bl;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityExcel;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Classe di gestione delle funzionalita' inerenti  l'esportazione e 
 * l'importazione in formato Excel per OEPV
 * 
 * @author Stefano.Cestaro
 */
public class ImportExportT2Manager {

	/** Logger */
	static Logger logger = Logger.getLogger(ImportExportT2Manager.class);
	  
	/** Manager SQL per le operazioni su database */
	private SqlManager sqlManager;

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
	private static final String querySelectW9Lott = "select codgara, codlott from w9lott where cig = ?";
	
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
	
	/**
	 * Importazione da formato XLS
	 * 
	 * @param XLS file excel
	 * @throws GestoreException
	 */
	public void importazione(HSSFWorkbook XLS) throws GestoreException {
		this.workXLS = XLS;

		try {
			//ricaviamo i dati dal foglio "Prodotti"
			HSSFSheet mainSheet = workXLS.getSheetAt(0);
			if (mainSheet != null) {
				// Cancellazione preliminare delle righe di T2
				String deleteT2 = "delete from filet2";
				this.sqlManager.update(deleteT2,new Object[] {});
				for(int i = mainSheet.getFirstRowNum() + 2; i < mainSheet.getLastRowNum() + 2; i ++) {
					//Ricavo codice gara e codice lotto dal codice CIG
					String CIG = UtilityExcel.leggiCellaString(mainSheet,1,i);
					if (CIG != null && !CIG.trim().equals("")) {
						Vector<?> lottoExist = this.sqlManager.getVector(querySelectW9Lott,	new Object[]{CIG});
						if (lottoExist != null && lottoExist.size() != 0) {
							//corrispondenza trovata
							Long codiceGara = SqlManager.getValueFromVectorParam(lottoExist, 0).longValue();
							Long codiceLotto = SqlManager.getValueFromVectorParam(lottoExist, 1).longValue();
							String gruppo = UtilityExcel.leggiCellaString(mainSheet,2,i);
							gruppo = (gruppo != null)? gruppo:"";
							//Long lotto = new Long(UtilityExcel.leggiCellaString(mainSheet, 3, i));
							String sublotto = UtilityExcel.leggiCellaString(mainSheet,4,i);
							sublotto = (sublotto != null)? sublotto:"";
							String compsublotto = UtilityExcel.leggiCellaString(mainSheet,5,i);
							compsublotto = (compsublotto != null)? compsublotto:"";
							String descprod = UtilityExcel.leggiCellaString(mainSheet,6,i);
							descprod = (descprod != null)? descprod:"";
							String descagg = UtilityExcel.leggiCellaString(mainSheet,7,i);
							descagg = (descagg != null)? descagg:"";
							String aic_paraf = UtilityExcel.leggiCellaString(mainSheet,8,i);
							aic_paraf = (aic_paraf != null)? aic_paraf:"";
							String uni_mis = UtilityExcel.leggiCellaString(mainSheet,9,i);
							uni_mis = (uni_mis != null)? uni_mis:"";
							String forma = UtilityExcel.leggiCellaString(mainSheet,10,i);
							forma = (forma != null)? forma:"";
							Double quant = UtilityExcel.leggiCellaDouble(mainSheet,11,i);
							quant = (quant != null)? quant:0;
							String cod_forn = UtilityExcel.leggiCellaString(mainSheet,12,i);
							cod_forn = (cod_forn != null)? cod_forn:"";
							String piva_forn = UtilityExcel.leggiCellaString(mainSheet,13,i);
							piva_forn = (piva_forn != null)? piva_forn:"";
							String rag_soc_forn = UtilityExcel.leggiCellaString(mainSheet,14,i);
							rag_soc_forn = (rag_soc_forn != null)? rag_soc_forn:"";
							String nom_comme = UtilityExcel.leggiCellaString(mainSheet,15,i);
							nom_comme = (nom_comme != null)? nom_comme:"";
							String atc_cnd = UtilityExcel.leggiCellaString(mainSheet,16,i);
							atc_cnd = (atc_cnd != null)? atc_cnd:"";
							String cla_farm = UtilityExcel.leggiCellaString(mainSheet,17,i);
							cla_farm = (cla_farm != null)? cla_farm:"";
							String cod_rep_dm = UtilityExcel.leggiCellaString(mainSheet,18,i);
							cod_rep_dm = (cod_rep_dm != null)? cod_rep_dm:"";
							String cod_pro_int_forn = UtilityExcel.leggiCellaString(mainSheet,19,i);
							cod_pro_int_forn = (cod_pro_int_forn != null)? cod_pro_int_forn:"";
							String cod_barre = UtilityExcel.leggiCellaString(mainSheet,20,i);
							cod_barre = (cod_barre != null)? cod_barre:"";
							String classe_merc = UtilityExcel.leggiCellaString(mainSheet,21,i);
							classe_merc = (classe_merc != null)? classe_merc:"";
							String num_pezz_conf = UtilityExcel.leggiCellaDouble(mainSheet,22,i).toString();
							num_pezz_conf = (num_pezz_conf != null)? num_pezz_conf:"";
							String cod_list_acq = UtilityExcel.leggiCellaString(mainSheet,23,i);
							cod_list_acq = (cod_list_acq != null)? cod_list_acq:"";
							Double prez_list_conf = UtilityExcel.leggiCellaDouble(mainSheet,24,i);
							prez_list_conf = (prez_list_conf != null)? prez_list_conf:0;
							Double prez_offe_unit = UtilityExcel.leggiCellaDouble(mainSheet,25,i);
							prez_offe_unit = (prez_offe_unit != null)? prez_offe_unit:0;
							String flag_ex = UtilityExcel.leggiCellaString(mainSheet,26,i);
							flag_ex = (flag_ex != null)? flag_ex:"";
							Double sconto = UtilityExcel.leggiCellaDouble(mainSheet,27,i);
							sconto = (sconto != null)? sconto:0;
							Double sconto2 = UtilityExcel.leggiCellaDouble(mainSheet,28,i);
							sconto2 = (sconto2 != null)? sconto2:0;
							String ali_iva = UtilityExcel.leggiCellaString(mainSheet,29,i);
							ali_iva = (ali_iva != null)? ali_iva:"";
							String ali_iva_age = UtilityExcel.leggiCellaString(mainSheet,30,i);
							ali_iva_age = (ali_iva_age != null)? ali_iva_age:"";
							Double cod_list_vend = UtilityExcel.leggiCellaDouble(mainSheet,31,i);
							cod_list_vend = (cod_list_vend != null)? cod_list_vend:0;
							String class_1_liv = UtilityExcel.leggiCellaString(mainSheet,32,i);
							class_1_liv = (class_1_liv != null)? class_1_liv:"";
							String class_2_liv = UtilityExcel.leggiCellaString(mainSheet,33,i);
							class_2_liv = (class_2_liv != null)? class_2_liv:"";
							String stato_ari = UtilityExcel.leggiCellaString(mainSheet,34,i);
							stato_ari = (stato_ari != null)? stato_ari:"";
							Double gg_scorta_min = UtilityExcel.leggiCellaDouble(mainSheet,35,i);
							gg_scorta_min = (gg_scorta_min != null)? gg_scorta_min:0;
							Double gg_scorta_max = UtilityExcel.leggiCellaDouble(mainSheet,36,i);
							gg_scorta_max = (gg_scorta_max != null)? gg_scorta_max:0;
							String temp_cons = UtilityExcel.leggiCellaString(mainSheet,37,i);
							temp_cons = (temp_cons != null)? temp_cons:"";
							Double gg_pre_cap_cons = UtilityExcel.leggiCellaDouble(mainSheet,38,i);
							gg_pre_cap_cons = (gg_pre_cap_cons != null)? gg_pre_cap_cons:0;
							Double imb_sec = UtilityExcel.leggiCellaDouble(mainSheet,39,i);
							imb_sec = (imb_sec != null)? imb_sec:0;
							Double min_fatt = UtilityExcel.leggiCellaDouble(mainSheet,40,i);
							min_fatt = (min_fatt != null)? min_fatt:0;
							String prev_tras_spec = UtilityExcel.leggiCellaString(mainSheet,41,i);
							prev_tras_spec = (prev_tras_spec != null)? prev_tras_spec:"";
							String conf_vend_picc = UtilityExcel.leggiCellaString(mainSheet,42,i);
							conf_vend_picc = (conf_vend_picc != null)? conf_vend_picc:"";
							Double valore_lotto = UtilityExcel.leggiCellaDouble(mainSheet,43,i);
							valore_lotto = (valore_lotto != null)? valore_lotto:0;
							Double peso = UtilityExcel.leggiCellaDouble(mainSheet,44,i);
							peso = (peso != null)? peso:0;
							Double dim_x = UtilityExcel.leggiCellaDouble(mainSheet,45,i);
							dim_x = (dim_x != null)? dim_x:0;
							Double dim_y = UtilityExcel.leggiCellaDouble(mainSheet,46,i);
							dim_y = (dim_y != null)? dim_y:0;
							Double dim_z = UtilityExcel.leggiCellaDouble(mainSheet,47,i);
							dim_z = (dim_z != null)? dim_z:0;
							String voluminoso = UtilityExcel.leggiCellaString(mainSheet,48,i);
							voluminoso = (voluminoso != null)? voluminoso:"";
							String cod_sost = UtilityExcel.leggiCellaString(mainSheet,49,i);
							cod_sost = (cod_sost != null)? cod_sost:"";
							String mod_sostit = UtilityExcel.leggiCellaString(mainSheet,50,i);
							mod_sostit = (mod_sostit != null)? mod_sostit:"";
							String quant_min_rior = UtilityExcel.leggiCellaString(mainSheet,51,i);
							quant_min_rior = (quant_min_rior != null)? quant_min_rior:"";
							String quant_min_reint = UtilityExcel.leggiCellaString(mainSheet,52,i);
							quant_min_reint = (quant_min_reint != null)? quant_min_reint:"";
							//inserisco la riga in filet2
							String insertT2 = "insert into filet2(gruppo, codgara, codlott, sublotto, compsublotto, descprod, descagg, aic_paraf, uni_mis, " +
							"forma, quant, cod_forn, piva_forn, rag_soc_forn, nom_comme, atc_cnd, cla_farm, cod_rep_dm, cod_pro_int_forn, cod_barre, " +
							"classe_merc, num_pezz_conf, cod_list_acq, prez_list_conf, prez_offe_unit, flag_ex, sconto, sconto2, ali_iva, ali_iva_age, " +
							"cod_list_vend, class_1_liv, class_2_liv, stato_ari, gg_scorta_min, gg_scorta_max, temp_cons, gg_pre_cap_cons, imb_sec, " +
							"min_fatt, prev_tras_spec, conf_vend_picc, valore_lotto, peso, dim_x, dim_y, dim_z, voluminoso, cod_sost, mod_sostit, " +
							"quant_min_rior, quant_min_reint) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
							this.sqlManager.update(insertT2,new Object[] {gruppo, codiceGara, codiceLotto, sublotto, compsublotto, descprod, descagg, aic_paraf, uni_mis, 
									forma, quant, cod_forn, piva_forn, rag_soc_forn, nom_comme, atc_cnd, cla_farm, cod_rep_dm, cod_pro_int_forn, cod_barre, 
									classe_merc, num_pezz_conf, cod_list_acq, prez_list_conf, prez_offe_unit, flag_ex, sconto, sconto2, ali_iva, ali_iva_age, 
									cod_list_vend, class_1_liv, class_2_liv, stato_ari, gg_scorta_min, gg_scorta_max, temp_cons, gg_pre_cap_cons, imb_sec, 
									min_fatt, prev_tras_spec, conf_vend_picc, valore_lotto, peso, dim_x, dim_y, dim_z, voluminoso, cod_sost, mod_sostit, 
									quant_min_rior, quant_min_reint});
						}
					}
					
				}
			}
		} catch (Exception e) {
			throw new GestoreException("Errore durante la cancellazione della l'importazione della tabella T2","importExportT2.import");
		}
	}
	
}