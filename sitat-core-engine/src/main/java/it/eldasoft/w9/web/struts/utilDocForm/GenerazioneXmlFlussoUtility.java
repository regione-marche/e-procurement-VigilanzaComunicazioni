package it.eldasoft.w9.web.struts.utilDocForm;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import it.eldasoft.gene.bl.SqlManager;
import it.toscana.rete.rfc.sitat.types.W3020Type;
import it.toscana.rete.rfc.sitat.types.W3998Type;
import it.toscana.rete.rfc.sitat.types.W9FlussoType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GenerazioneXmlFlussoUtility {

	static public void generaXML(String idGara, String percorsoCartellaExport, SqlManager sqlManager) throws SQLException, Exception {

		String separatore = "_";
		String estensione = ".xml";
		Long codGara = getCodGara(idGara, sqlManager);

		// controllo se esiste il flusso
		String sqlStringFlussi = "select " //
				+ "AREA, " // 0
				+ "KEY01, " // 1 --> codgara (sostituire con idgara)
				+ "KEY02, " // 2 --> codlott (sostituire con CIG))
				+ "KEY03, " // 3 --> fase
				+ "KEY04, " // 4 --> progressivo fase
				+ "TINVIO2, " // 5
				+ "DATINV, " // 6 --> data di invio (per fasi multiple)
				+ "NOTEINVIO, " // 7
				+ "XML, " // 8
				+ "CODCOMP, " // 9
				+ "IDCOMUN, " // 10
				+ "CFSA, " // 11
				+ "CODOGG, " // 12
				+ "DATIMP, " // 13
				+ "VERXML, " // 14
				+ "AUTORE,  " // 15
				+ "IDFLUSSO " // 16 --> verra' usato per l'ordinamento dei
								// flussi
				+ "from W9FLUSSI where KEY01 = ?";
		List<Vector> flussiGara = sqlManager.getListVector(sqlStringFlussi, new Object[] { codGara });

		if (flussiGara != null) {
			for (int i = 0; i < flussiGara.size(); i++) {
				W9FlussoType faseFlusso = W9FlussoType.Factory.newInstance();
				XmlObject xmlObjFlusso = valorizzaW9FlussoType(faseFlusso, idGara, flussiGara.get(i), sqlManager);

				String codCIG = "null";
				if (flussiGara.get(i).get(2) != null && StringUtils.isNotEmpty(flussiGara.get(i).get(2).toString())) {
					codCIG = getCodCIG(codGara, new Long(flussiGara.get(i).get(2).toString()), sqlManager);
				}
				String codFase = flussiGara.get(i).get(3).toString();
				String progFase = flussiGara.get(i).get(4).toString();
				String dataInvio = flussiGara.get(i).get(6).toString().replace("/", "");

				String idFlusso = flussiGara.get(i).get(16).toString();

				String nomeFileFlusso = "Flusso_" + idFlusso + separatore + idGara + separatore + codCIG + separatore + codFase + separatore + progFase + separatore + dataInvio + estensione;
				String percorsoFileFlusso = percorsoCartellaExport + File.separatorChar + nomeFileFlusso;

				if (xmlObjFlusso != null) {
					// salvataggio del file nella cartella
					File fileXmlFlusso = new File(percorsoFileFlusso);
					FileUtils.writeStringToFile(fileXmlFlusso, xmlObjFlusso.toString());
				}

			}
		}
	}

	/**
	 * Metodo per ottenere il codice di una gara a aprtire dall'id fornito
	 * 
	 * @param idGara
	 *            codice gara
	 * @return ritorna il codice della gara
	 * @throws SQLException
	 *             SQLException
	 */

	static public Long getCodGara(String idGara, SqlManager sqlManager) throws SQLException {
		String queryGetCodGara = "select CODGARA from W9GARA where idavgara = ?";
		return (Long) sqlManager.getObject(queryGetCodGara, new Object[] { idGara });
	}

	/**
	 * Metodo per ottenere il codice CIG di un lotto
	 * 
	 * @param codGara
	 *            codice gara
	 * @param codLotto
	 *            codice lotto
	 * @return ritorna il codice cig di un lotto
	 * @throws SQLException
	 *             SQLException
	 */

	static public String getCodCIG(Long codGara, Long codLotto, SqlManager sqlManager) throws SQLException {
		String queryGetCIG = "select CIG from W9LOTT where codgara = ? and codlott = ? ";
		return (String) sqlManager.getObject(queryGetCIG, new Object[] { codGara, codLotto });
	}

	/**
	 * Metodo per la creazione del bean W9Flusso atto alla creazione del file
	 * XML
	 * 
	 * @param faseFlusso
	 *            l'oggetto bean per la creazione dell'XML
	 * @param idGara
	 *            identificativo della gara
	 * @param flusso
	 *            flusso trattato
	 * @param sqlManager
	 *            manager per la comunicazione con il db
	 * @return ritorna il codice cig di un lotto
	 * @throws SQLException
	 *             SQLException
	 * @throws Exception
	 *             Exception
	 */

	private static XmlObject valorizzaW9FlussoType(final W9FlussoType faseFlusso, final String idGara, final Vector flusso, final SqlManager sqlManager) throws SQLException, Exception {

		if (flusso.get(0) != null && StringUtils.isNotEmpty(flusso.get(0).toString())) {
			Long area = new Long(flusso.get(0).toString());
			faseFlusso.setW9FLAREA(area.intValue());
		}

		Long key01CodGara = new Long(flusso.get(1).toString());
		faseFlusso.setW9FLEKEY01(idGara);

		if (flusso.get(2) != null && StringUtils.isNotEmpty(flusso.get(2).toString())) {
			Long key02CodLott = new Long(flusso.get(2).toString());
			String codCig = getCodCIG(key01CodGara, key02CodLott, sqlManager);
			faseFlusso.setW9FLEKEY02(codCig);
		}
		if (flusso.get(3) != null && StringUtils.isNotEmpty(flusso.get(3).toString())) {
			Long key03NumFase = new Long(flusso.get(3).toString());
			faseFlusso.setW9FLEKEY03(W3020Type.Enum.forString(key03NumFase.toString()));
		}
		if (flusso.get(4) != null && StringUtils.isNotEmpty(flusso.get(4).toString())) {
			Long key04NProg = new Long(flusso.get(4).toString());
			faseFlusso.setW9FLEKEY04(key04NProg.intValue());
		}
		if (flusso.get(5) != null && StringUtils.isNotEmpty(flusso.get(5).toString())) {
			Long tinvio2 = new Long(flusso.get(5).toString());
			faseFlusso.setW9FLETINVIO(W3998Type.Enum.forString(tinvio2.toString()));
		}
		if (flusso.get(6) != null && StringUtils.isNotEmpty(flusso.get(6).toString())) {
			faseFlusso.setW9FLDATINV(dateString2Calendar(flusso.get(6).toString()));
		}
		if (flusso.get(7) != null && StringUtils.isNotEmpty(flusso.get(7).toString())) {
			String noteinvio = flusso.get(7).toString();
			faseFlusso.setW9NOTINVIO(noteinvio);
		}
		if (flusso.get(8) != null) {
			faseFlusso.setW9FLEXML(flusso.get(8).toString());
		}
		if (flusso.get(9) != null && StringUtils.isNotEmpty(flusso.get(9).toString())) {
			Long codcomp = new Long(flusso.get(9).toString());
			faseFlusso.setW9FLCODCOM(codcomp);
		}
		if (flusso.get(10) != null && StringUtils.isNotEmpty(flusso.get(10).toString())) {
			Long idcomun = new Long(flusso.get(10).toString());
			faseFlusso.setW9FLCOMUN(idcomun);
		}
		if (flusso.get(11) != null && StringUtils.isNotEmpty(flusso.get(11).toString())) {
			String cfsa = flusso.get(11).toString();
			faseFlusso.setW9FLCFSA(cfsa);
		}
		if (flusso.get(12) != null && StringUtils.isNotEmpty(flusso.get(12).toString())) {
			String codogg = flusso.get(12).toString();
			faseFlusso.setW9FLCODOGG(codogg);
		}
		if (flusso.get(13) != null && StringUtils.isNotEmpty(flusso.get(13).toString())) {
			String[] datinv = flusso.get(13).toString().split("/");
			Calendar dataImportazione = Calendar.getInstance();
			dataImportazione.set(new Integer(datinv[2]), new Integer(datinv[1]), new Integer(datinv[0]));
			faseFlusso.setW9IBDTIMP(dataImportazione);
		}
		if (flusso.get(14) != null && StringUtils.isNotEmpty(flusso.get(14).toString())) {
			String verxml = flusso.get(14).toString();
			faseFlusso.setW9FLVERXML(verxml);
		}
		if (flusso.get(15) != null && StringUtils.isNotEmpty(flusso.get(15).toString())) {
			String autore = flusso.get(15).toString();
			faseFlusso.setW9FLEAUTORE(autore);
		}

		return faseFlusso;
	}

	/**
	 * Metodo per ottenere un byte array da un object
	 * 
	 * @param obj
	 *            oggetto da convertire
	 * @return ritorna il byte array dell'oggetto
	 * @throws IOException
	 *             IOException
	 */
	public static byte[] toByteArray(Object obj) throws IOException {
		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
		} finally {
			if (oos != null) {
				oos.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
		return bytes;
	}

	/**
	 * Metodo per cast in calendar delle date.
	 * 
	 * @param data
	 *            String
	 * @return Ritorna la data in un oggetto Calendar a partire dalla stringa
	 *         nel formato gg/mm/aaaa
	 * @throws ParseException
	 *             ParseException
	 */
	public static Calendar dateString2Calendar(final String data) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date parsed = df.parse(data);

		Calendar newCalendar = new GregorianCalendar();
		newCalendar.setTime(parsed);

		return newCalendar;
	}
}
