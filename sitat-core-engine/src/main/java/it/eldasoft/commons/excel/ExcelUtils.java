package it.eldasoft.commons.excel;

import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.odftoolkit.odfdom.doc.table.OdfTable;

/**
 *
 * @author marco.perazzetta
 */
public class ExcelUtils {

	public static final String TYPE_XLS = "XLS";
	public static final String TYPE_XLSX = "XLSX";
	public static final String TYPE_OTS = "OTS";
	public static final String TYPE_ODS = "ODS";

	public static String readStringFromCell(Object sheet, int rowIndex, int columnIndex) throws GestoreException {

		AbstractCell absCell = AbstractCell.getInstance(sheet, rowIndex, columnIndex);
		return absCell != null ? absCell.getStringFromCell() : null;
	}

	public static Number readNumberFromCell(Object sheet, int rowIndex, int columnIndex, Integer numDecimals) throws GestoreException {

		AbstractCell absCell = AbstractCell.getInstance(sheet, rowIndex, columnIndex);
		return absCell != null ? absCell.getNumberFromCell(numDecimals) : null;
	}

	public static Date readDateFromCell(Object sheet, int rowIndex, int columnIndex) throws GestoreException {

		AbstractCell absCell = AbstractCell.getInstance(sheet, rowIndex, columnIndex);
		return absCell != null ? absCell.getDateFromCell() : null;
	}

	/**
	 * Funzione per rimuovere gli spazi doppi all'interno della stringa, toglierli
	 * all'inizio e alla fine
	 *
	 * @param s la stringa da processare
	 * @return la stringa ripulita dagli spazi
	 */
	public static String myTrim(String s) {

		if (s == null) {
			return null;
		}
		String[] sTmp = s.split(" ");
		String temp = "";
		for (int i = 0; i <= sTmp.length - 1; i++) {
			if ((sTmp[i].equals(" ") == false) && (sTmp[i].equals("") == false)) {
				temp = temp + " " + sTmp[i];
			}
			temp = temp.trim();
		}
		return temp;
	}

	public static void copyRow(HSSFSheet sheet, int sourceRowNum, int destinationRowNum, Map<Integer, HSSFCellStyle> styleMap) {

		//Manage a list of merged zone in order to not insert two times a merged zone  
		Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();
		HSSFRow srcRow = sheet.getRow(sourceRowNum);
		HSSFRow destRow = sheet.getRow(destinationRowNum);
		if (destRow != null) {
			sheet.shiftRows(destinationRowNum, sheet.getLastRowNum(), 1, true, false);
		} else {
			destRow = sheet.createRow(destinationRowNum);
		}
		destRow.setHeight(srcRow.getHeight());

		int deltaRows = destRow.getRowNum() - srcRow.getRowNum();

		for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {

			HSSFCell oldCell = srcRow.getCell(j);
			HSSFCell newCell = destRow.getCell(j);
			if (oldCell != null) {
				if (newCell == null) {
					newCell = destRow.createCell(j);
				}
				copyCell(oldCell, newCell, styleMap);
				CellRangeAddress mergedRegion = getMergedRegion(sheet, srcRow.getRowNum(), (short) oldCell.getColumnIndex());
				if (mergedRegion != null) {
					CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow() + deltaRows, mergedRegion.getLastRow() + deltaRows, mergedRegion.getFirstColumn(), mergedRegion.getLastColumn());
					CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(newMergedRegion);
					if (isNewMergedRegion(wrapper, mergedRegions)) {
						mergedRegions.add(wrapper);
						sheet.addMergedRegion(wrapper.range);
					}
				}
			}
		}
	}

	private static void copyCell(HSSFCell oldCell, HSSFCell newCell, Map<Integer, HSSFCellStyle> styleMap) {

		if (styleMap != null) {
			if (oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()) {
				newCell.setCellStyle(oldCell.getCellStyle());
			} else {
				int stHashCode = oldCell.getCellStyle().hashCode();
				HSSFCellStyle newCellStyle = styleMap.get(stHashCode);
				if (newCellStyle == null) {
					newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();
					newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
					styleMap.put(stHashCode, newCellStyle);
				}
				newCell.setCellStyle(newCellStyle);
			}
		}
		switch (oldCell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				newCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(oldCell.getCellFormula());
				break;
			default:
				break;
		}
	}

	private static CellRangeAddress getMergedRegion(HSSFSheet sheet, int rowNum, short cellNum) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress merged = sheet.getMergedRegion(i);
			if (merged.isInRange(rowNum, cellNum)) {
				return merged;
			}
		}
		return null;
	}

	private static boolean isNewMergedRegion(CellRangeAddressWrapper newMergedRegion, Set<CellRangeAddressWrapper> mergedRegions) {
		return !mergedRegions.contains(newMergedRegion);
	}

	public static String getFileExtension(String fileName) throws GestoreException {

		String ext = fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase();
		if (!ext.equals(TYPE_XLS) && !ext.equals(TYPE_XLSX) && !ext.equals(TYPE_OTS) && !ext.equals(TYPE_ODS)) {
			throw new GestoreException("Estensione del file errata", "excel.wrongextension");
		}
		return ext;
	}

	public static int getNumberOfRows(Object sheet) throws GestoreException {

		int rowNumber = 0;
		try {
			if (sheet instanceof Sheet) {
				rowNumber = ((Sheet) sheet).getLastRowNum();
			} else if (sheet instanceof OdfTable) {
				rowNumber = ((OdfTable) sheet).getRowCount();
			}
		} catch (Exception ex) {
			throw new GestoreException("Impossibile recuperare il foglio di lavoro, tipo di oggetto sconosciuto", "excel.cannotdecodesheetype");
		}
		return rowNumber;
	}

	public static Object getRow(Object sheet, int rowIndex) throws GestoreException {

		Object row = null;
		try {
			if (sheet instanceof Sheet) {
				row = ((Sheet) sheet).getRow(rowIndex);
			} else if (sheet instanceof OdfTable) {
				row = ((OdfTable) sheet).getRowByIndex(rowIndex);
			}
		} catch (Exception ex) {
			throw new GestoreException("Impossibile recuperare il record", "excel.cannotretrieverecord", new Object[]{rowIndex}, null);
		}
		return row;
	}

	public static void checkFieldMandatory(Object field, String fieldName, List<String> erroriRiga, ResourceBundle resourceBundle) throws GestoreException {

		boolean error = false;
		if (field == null) {
			error = true;
		} else if (field instanceof String && StringUtils.isBlank((String) field)) {
			error = true;
		} else if (field instanceof Number && StringUtils.isBlank(((Number) field).toString())) {
			error = true;
		} else if (field instanceof Date && StringUtils.isBlank(((Date) field).toString())) {
			error = true;
		}
		if (error) {
			if (erroriRiga == null) {
				erroriRiga = new ArrayList<String>();
			}
			erroriRiga.add(resourceBundle.getString("errors.gestoreException.*.excel.mandatoyfield").replace("{0}", fieldName));
		}
	}

	public static void checkMaxFieldSize(Object field, String fieldName, Integer maxSize, List<String> erroriRiga, ResourceBundle resourceBundle) throws GestoreException {

		boolean error = false;
		int fieldsize = 0;
		if (maxSize != null && field != null) {
			if (field instanceof String) {
				fieldsize = ((String) field).length();
			} else if (field instanceof Number) {
				fieldsize = ((Number) field).toString().length();
			}
			if (field instanceof String && fieldsize > maxSize) {
				error = true;
			}
		}
		if (error) {
			if (erroriRiga == null) {
				erroriRiga = new ArrayList<String>();
			}
			erroriRiga.add(resourceBundle.getString("errors.gestoreException.*.excel.fieldovermaxsize").replace("{0}", fieldName).replace("{1}", maxSize.toString()));
		}
	}

	public static void checkMinFieldValue(Number field, String fieldName, Integer minValue, List<String> erroriRiga, ResourceBundle resourceBundle) throws GestoreException {

		boolean error = false;
		if (minValue != null && field != null && field.intValue() <= minValue) {
			error = true;
		}
		if (error) {
			if (erroriRiga == null) {
				erroriRiga = new ArrayList<String>();
			}
			erroriRiga.add(resourceBundle.getString("errors.gestoreException.*.excel.fieldunderminvalues").replace("{0}", fieldName).replace("{1}", minValue.toString()));
		}
	}

	public static void checkValidValue(String field, String fieldName, List<String> correctValues, List<String> erroriRiga, ResourceBundle resourceBundle) throws GestoreException {

		boolean found = false;
		for (String aCorrectValue : correctValues) {
			if (field.toUpperCase().equals(aCorrectValue)) {
				found = true;
				break;
			}
		}
		if (!found) {
			if (erroriRiga == null) {
				erroriRiga = new ArrayList<String>();
			}
			erroriRiga.add(resourceBundle.getString("errors.gestoreException.*.excel.wrongValue").replace("{0}", field).replace("{1}", fieldName));
		}
	}
}
