package it.eldasoft.sil.pt.bl;

import it.eldasoft.commons.excel.DizionarioStiliExcel;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityExcel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

/**
 * Export dei dati dei soggetti aggregatori
 *
 * @author luca.giacomazzo
 */
public class ExportSoggettiAggregatoriManager {

	static Logger logger = Logger.getLogger(ExportSoggettiAggregatoriManager.class);
	private SqlManager sqlManager;
	
	private final String sqlSoggettiAggregatori =
	  "select codiceFiscaleEnte, codiceIPA, amministrazione, ufficio, dipartimento, regione, provincia, indirizzoEnte, telefonoEnte, mailEnte, pecEnte, "
	  + " nomeReferente, cognomeReferente, codiceFiscaleReferente, telefonoReferente, mailReferente, numeroInterventoCUI, codiceFiscaleAmministrazione, primaAnnualita, "
	  + " anno, identificativoProceduraAcq, codiceCUP, lottoFunzionale, importoStimatoLotto, ambitoGeografico, codiceCUPmaster, settore, CPV, descrizioneAcquisto, "
	  + " conformitaAmbientale, priorita, cfResponsabileProcedimento, cognomeResponsProcedimento, nomeResponsabileProcedimento, quantita, unitaMisura, durataContratto, "
	  + " stimaCostiProgrammaPrimoAnno, stimaCostiProgrammaSecondoAnno, costiAnnualitaSuccessive, stimaCostiProgrammaTotale, "
	  + " importoApportoCapitalePrivato, tipoApportoCapitalePrivato, delega, codiceAUSA, denomAmministrazioneDelegata "
	  + " from v_report_sogg_aggr where contri = ?";
	
	
	public final String MODELLO_ADEMPIMENTI_LEGGE_190 = "soggettiAggregatori.xls";
	public final String FOGLIO_MODELLO_DATI_ENTE = "Dati Ente";
	public final String FOGLIO_MODELLO_SCHEDA_B = "Scheda B";
	
	private final int START_ROW = 2;
	
	// Foglio Dati Ente
	private final String F1_COL_AMMINISTRAZIONE = "Amministrazione";
	private final String F1_COL_REFERENTE = "Referente dei dati di programmazione";

  private final String[] F1_TITOLI_COLONNE = new String[] {
      "Codice Fiscale", "Codice IPA", "Amministrazione", "Dipartimento",
      "Ufficio", "Regione", "Provincia", "Indirizzo",
      "Telefono", "Indirizzo mail", "Indirizzo PEC", "Nome",
      "Cognome", "Codice fiscale", "Telefono", "Indirizzo mail" };

  private final int[] F1_LARGHEZZA_COLONNE = new int[] { 25, 25, 25, 25, 20, 20, 10, 25, 15, 25, 25, 15, 20, 20, 15, 25 };
  
  // Foglio Scheda B
  private final String F2_COL = "SCHEDA B: ELENCO DEGLI ACQUISTI DI BENI E SERVIZI DI IMPORTO UNITARIO STIMATO SUPERIORE A 1 MILIONE DI EURO"; 
  
  private final String[] F2_TITOLI_COLONNE = new String[] { 
      "Numero intervento CUI", "Codice Fiscale Amministrazione", "Prima annualità del primo programma nel quale l'intervento è stato inserito",
      "Annualità nella quale si prevede di dare avvio alla procedura di acquisto", "Identificativo della procedura di acquisto", "Codice CUP",
      "Lotto funzionale", "Importo stimato lotto", "Ambito geografico di esecuzione dell'Acquisto (Regione/i)",
      "Codice eventuale CUP master", "Settore", "CPV", "Descrizione Acquisto", "Conformità ambientale", "Priorità",
      "Codice fiscale responsabile procedimento (RUP)", "Cognome responsabile procedimento (RUP)", "Nome responsabile procedimento (RUP)", "Quantità",
      "Unità di misura", "Durata del contratto", "Stima costi Programma Primo anno", "Stima costi Programma Secondo anno",
      "Costi su annualità successive", "Stima costi Programma Totale", "Apporto di capitale privato - Importo", "Apporto di capitale privato - Tipologia",
      "Si intende delegare a Centrale di Committenza o Soggetto Aggregatore la procedura di acquisto", "Codice AUSA Amministrazione delegata",
      "Denominazione Amministrazione delegata" };

  private final int[] F2_LARGHEZZA_COLONNE = new int[] {
      25, 25, 20, 20, 20, 12, 12, 16, 20, 20,
      16, 12, 25, 12, 12, 18, 25, 16, 12, 16,
      12, 16, 16, 16, 16, 16, 16, 16, 20, 25 };
	
	/**
	 * Set SqlManager
	 *
	 * @param sqlManager
	 */
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	/**
	 * Esportazione in formato XLS dei soggetti aggregatori
	 *
	 * @param idAnticor la idAnticor dell'adempimento da esportare
	 * @param percorsoExcel il percorso del template excel da compilare
	 * @param profiloUtente
	 * @return il byte array dell'excel da passare all'output stream
	 * @throws GestoreException eccezione sollevata dal manager
	 */
	public byte[] getReportSoggettiAggregatori(long contri) throws GestoreException {

		HSSFWorkbook wb = new HSSFWorkbook();
		
    // Creazione del dizionario degli stili delle celle dell'intero file Excel
    DizionarioStiliExcel dizStiliExcel = new DizionarioStiliExcel(wb);
    HSSFRow riga = null;
    HSSFCell cella = null;
    HSSFCellStyle stile = dizStiliExcel.getStileExcel(DizionarioStiliExcel.CELLA_INTESTAZIONE);
    
    // Creazione del primo foglio e scrittura righe di intestazione 
		HSSFSheet foglioDatiEnte = wb.createSheet(FOGLIO_MODELLO_DATI_ENTE);

    // Prima riga del foglio 'Dati Ente'
    // Gruppi di celle con due titoli
    riga = foglioDatiEnte.createRow(0);
    cella = riga.createCell(0);
    cella.setCellStyle(stile);
    cella.setCellValue(new HSSFRichTextString(F1_COL_AMMINISTRAZIONE));
    cella = riga.createCell(11);
    cella.setCellStyle(stile);
    cella.setCellValue(new HSSFRichTextString(F1_COL_REFERENTE));
    CellRangeAddress cellRange1 = new CellRangeAddress(0, 0, 0, 10);
    CellRangeAddress cellRange2 = new CellRangeAddress(0, 0, 11, 15);
    foglioDatiEnte.addMergedRegion(cellRange1);
    foglioDatiEnte.addMergedRegion(cellRange2);
    
    // Seconda riga del foglio 'Dati Ente'
    // Scrittura dell'intestazione delle sedici colonne del foglio    
    riga = foglioDatiEnte.createRow(1);
    for (int i=0; i < F1_TITOLI_COLONNE.length; i++) {
      cella = riga.createCell(i);
      cella.setCellStyle(stile);
      cella.setCellValue(new HSSFRichTextString(F1_TITOLI_COLONNE[i]));
      UtilityExcel.setLarghezzaColonna(foglioDatiEnte, (i+1), F1_LARGHEZZA_COLONNE[i]);
    }

    // Creazione del secondo foglio e scrittura righe di intestazione		
		HSSFSheet foglioSchedaB = wb.createSheet(FOGLIO_MODELLO_SCHEDA_B);
		// Prima riga del foglio 'Scheda B'
    // Gruppi di celle con un titolo
    riga = foglioSchedaB.createRow(0);
    cella = riga.createCell(0);
    cella.setCellStyle(stile);
    cella.setCellValue(new HSSFRichTextString(F2_COL));
    cellRange1 = new CellRangeAddress(0, 0, 0, 29);
    foglioSchedaB.addMergedRegion(cellRange1);
    
    // Seconda riga del foglio 'Scheda B'
    // Scrittura dell'intestazione delle sedici colonne del foglio    
    riga = foglioSchedaB.createRow(1);
    for (int i=0; i < F2_TITOLI_COLONNE.length; i++) {
      cella = riga.createCell(i);
      cella.setCellStyle(stile);
      cella.setCellValue(new HSSFRichTextString(F2_TITOLI_COLONNE[i]));
      UtilityExcel.setLarghezzaColonna(foglioSchedaB, (i+1), F2_LARGHEZZA_COLONNE[i]);
    }

		this.populateExcel(wb, contri, dizStiliExcel);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			throw new GestoreException("Errore inaspettato durante la procedura di export excel", "importaesportaexcel.erroreinaspettato", e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				throw new GestoreException("Errore inaspettato durante la procedura di export excel", "importaesportaexcel.erroreinaspettato", e);
			}
		}
		return os.toByteArray();
	}

	/**
	 * Scrittura dei dati nei due sheet del foglio Excel
	 *
	 * @param wb
	 * @param contri
	 * @param dizStiliExcel
	 * @throws GestoreException
	 */
	private void populateExcel(HSSFWorkbook wb, long contri, DizionarioStiliExcel dizStiliExcel) throws GestoreException {
		List<?> dati;

		HSSFSheet foglioDatiEnte = wb.getSheet(FOGLIO_MODELLO_DATI_ENTE);
		HSSFSheet foglioSchedaB  = wb.getSheet(FOGLIO_MODELLO_SCHEDA_B);
		
		if (foglioDatiEnte == null) {
			throw new GestoreException("Non trovato il foglio " + FOGLIO_MODELLO_DATI_ENTE,
					"importaesportaexcel.foglionontrovato", new Object[]{FOGLIO_MODELLO_DATI_ENTE},
						null);
		}
		
		try {
			dati = this.sqlManager.getListVector(this.sqlSoggettiAggregatori, new Object[] { contri } );
			
			if (dati != null && dati.size() > 0) {
	      int indiceRigaCorrente = START_ROW;

	      for (int i=0; i < dati.size(); i++) {
	        if (i == 0) {
	          // Scrittura della prima riga di valori estratti nel foglio Dati ente
	          fillDatiEnte(foglioDatiEnte, (indiceRigaCorrente+1), ((Vector<?>) dati.get(i)).subList(0, 16), i, dizStiliExcel);
	        }
          fillSchedaB(foglioSchedaB, (indiceRigaCorrente+1), ((Vector<?>) dati.get(i)).subList(16, ((Vector<?>) dati.get(i)).size()), i, dizStiliExcel);
          indiceRigaCorrente++;
        }
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore inaspettato durante la procedura di import/export excel", "importaesportaexcel.erroreinaspettato", e);
		}
	}

	/**
	 * Scrittura di tutti i dati dell'ente sul foglio 'Dati Ente'
	 * 
	 * @param sheet
	 * @param indiceRigaCorrente
	 * @param dati
	 * @param indiceRecordLotti
	 * @param dizStiliExcel
	 * @throws GestoreException
	 */
	private void fillDatiEnte(HSSFSheet sheet, int indiceRigaCorrente, List<?> dati,
					int indiceRecordLotti, DizionarioStiliExcel dizStiliExcel) throws GestoreException {
	  HSSFRow riga = sheet.createRow(indiceRigaCorrente);
	  
	  for (int j=0; j < dati.size(); j++) {
	    riga.createCell(j);
	    String temp = ((JdbcParametro) dati.get(j)).stringValue();
	    if (StringUtils.isNotEmpty(temp)) {
	      UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, temp, dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
	    } else {
	      UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, "", dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
	    }
	  }
	}

	private void fillSchedaB(HSSFSheet sheet, int indiceRigaCorrente, List<?> dati,
      int indiceRecordLotti, DizionarioStiliExcel dizStiliExcel) throws GestoreException {
	  HSSFRow riga = sheet.createRow(indiceRigaCorrente);
	  
	  for (int j=0; j < dati.size(); j++) {
	    riga.createCell(j);
	    JdbcParametro jdbcParam = ((JdbcParametro) dati.get(j)); 
      if (j==18) {
	      // campo di tipo numero intero o decimale
        if (JdbcParametro.TIPO_DECIMALE == jdbcParam.getTipo()) {
          Double valore = (Double) jdbcParam.getValue();
          if (valore != null) {
            UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, valore,
                dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE5_ALIGN_RIGHT));
          } else {
            UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, new Double(0),
                dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE5_ALIGN_RIGHT));
          }
        } else if (JdbcParametro.TIPO_NUMERICO == jdbcParam.getTipo()) {
          Long valore = (Long) jdbcParam.getValue();
          if (valore != null) {
            UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, valore,
                dizStiliExcel.getStileExcel(DizionarioStiliExcel.INTERO_ALIGN_RIGHT));
          } else {
            UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, new Long(0),
                dizStiliExcel.getStileExcel(DizionarioStiliExcel.INTERO_ALIGN_RIGHT));
          }
        }
      } else if (j==3 || j==14 || j==20) {
        // camp1 di tipo numero intero
        if (jdbcParam.getValue() != null) {
          UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, jdbcParam.getValue(),
              dizStiliExcel.getStileExcel(DizionarioStiliExcel.INTERO_ALIGN_RIGHT));
        } else {
          UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, new Long(0),
              dizStiliExcel.getStileExcel(DizionarioStiliExcel.INTERO_ALIGN_RIGHT));
        }
	    } else if (j==7 || j==21 || j==22 || j==23 || j==24 || j==25) {
        // campi di tipo importo
	      // Gestione manuale del valore estratto da DB, perche' il driver Oracle se il valore 
	      // estratto e' un numero intero ritorna un Long, nonostante il campo sia definito decimale
	      Double importo = null;
	      if (jdbcParam.getValue() != null) {
	        if (jdbcParam.getValue() instanceof Long) {
	          Long temp = (Long) jdbcParam.getValue();
	          importo = new Double(temp.doubleValue());
	        } else {
	          importo = (Double) jdbcParam.getValue();
	        }
	      }
	      if (importo != null) {
	        UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, importo,
	            dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE2_ALIGN_RIGHT));
	      } else {
	        UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, new Double(0), 
	            dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE2_ALIGN_RIGHT));
	      }
	    } else {
	      // Stringa
	      if (jdbcParam.getValue() != null) {
	        String temp = jdbcParam.stringValue();
	        UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, temp, dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_RIGHT));
	      } else {
	        UtilityExcel.scriviCella(sheet, (j+1), indiceRigaCorrente, "", dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_RIGHT));
	      }
	    }
    }
	}
	
}
