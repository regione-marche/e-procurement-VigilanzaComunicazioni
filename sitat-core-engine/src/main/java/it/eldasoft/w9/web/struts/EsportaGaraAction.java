package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.CreazioneXmlExportManager;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.common.bean.DatiComuniBean;
import it.eldasoft.w9.common.bean.GaraLottoBean;
import it.eldasoft.w9.common.bean.PubblicazioneBean;
import it.eldasoft.w9.web.struts.utilDocForm.GenerazioneXmlFlussoUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xmlbeans.XmlObject;

/**
 * @author Mirco.Franzoni - Eldasoft S.p.A. Treviso
 * 
 */

public class EsportaGaraAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
	private static Logger logger = Logger.getLogger(EsportaGaraAction.class);

	private SqlManager sqlManager;

	private W9Manager w9Manager;

	/**
	 * @param sqlManager
	 *            sqlManager da settare internamente alla classe.
	 */
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	/**
	 * @param w9Manager
	 *            w9Manager da settare internamente alla classe.
	 */
	public void setW9Manager(W9Manager w9Manager) {
		this.w9Manager = w9Manager;
	}

	@Override
	protected ActionForward runAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String target = "success";

		// recupero valori idgara, id utente , cf uffint
		String listaIdGaraStringa = request.getParameter("idGara");
		String cfsa = null;
		String percorsoCartellaExport = null;
		String messageKey = "";
		Long syscon = null;
		if (syscon == null && StringUtils.isNotEmpty(request.getParameter("syscon"))) {
			syscon = new Long(request.getParameter("syscon"));
		}
		if (listaIdGaraStringa != null) {

			if (logger.isDebugEnabled()) {
				logger.debug("EsportaGara: inizio metodo");
			}

			percorsoCartellaExport = creaCartellaTemporanea();

			String[] listaIdGara = listaIdGaraStringa.split("-");

			try {
				// ciclo sugli id
				for (int i = 0; i < listaIdGara.length; i++) {

					String idgara = listaIdGara[i];



					cfsa = getCfSa(idgara);

					if (existGara(idgara)) {
						if (logger.isDebugEnabled()) {
							logger.debug("Gestione della gara " + idgara + " inizio.");
						}

						List<GaraLottoBean> listaGareLotti = getGaraLottiBeans(cfsa, syscon, idgara);

						for (int j = 0; j < listaGareLotti.size(); j++) {

							GaraLottoBean garaLottoBean = listaGareLotti.get(j);
							// generazione del file xml
							XmlObject xmlObj = CreazioneXmlExportManager.getXmlGaraLottoFasi(garaLottoBean, this.sqlManager, this.w9Manager);

							Long codGara = garaLottoBean.getCodiceGara();
							Long codLotto = garaLottoBean.getCodiceLotto();
							Long codFase = garaLottoBean.getDatiComuniXml().getCodiceFase();
							Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
							String codCIG = getCodCIG(codGara, codLotto);
							if (StringUtils.isNotEmpty(codCIG) && codCIG.contains("_")) {
								codCIG.replace("_", "");
							}
							String separatore = "_";
							String estensione = ".xml";

							// formato nome del file xml
							// -->idgara_cig_n.fase
							String nomeFile = idgara + separatore + codCIG + separatore + codFase.toString() + separatore + progressivoFase.toString() + estensione;
							String percorsoFile = percorsoCartellaExport + File.separatorChar + nomeFile;

							// salvataggio del file nella cartella
							File fileXml = new File(percorsoFile);
							FileUtils.writeStringToFile(fileXml, xmlObj.toString());

						}

						List<PubblicazioneBean> listaPubblicazioni = getPubblicazioniBeans(cfsa, idgara);

						for (int j = 0; j < listaPubblicazioni.size(); j++) {

							PubblicazioneBean pubblicazioneBean = listaPubblicazioni.get(j);
							// generazione del file xml
							XmlObject xmlObj = CreazioneXmlExportManager.getXmlPubblicazioneDocumento(pubblicazioneBean, sqlManager, w9Manager);

							Long numeroPubblicazione = pubblicazioneBean.getNumeroPubblicazione();
							String separatore = "_";
							String estensione = ".xml";

							// formato nome del file xml
							// -->idgara_numeroPubblicazione
							String nomeFile = idgara + separatore + numeroPubblicazione.toString() + estensione;
							String percorsoFile = percorsoCartellaExport + File.separatorChar + nomeFile;

							// salvataggio del file nella cartella
							File fileXml = new File(percorsoFile);
							FileUtils.writeStringToFile(fileXml, xmlObj.toString());

						}

						// mi occupo della generazione dei file legati ai flussi
						GenerazioneXmlFlussoUtility.generaXML(idgara, percorsoCartellaExport, sqlManager);

						if (logger.isDebugEnabled()) {
							logger.debug("Gestione della gara " + idgara + "  fine.");
						}

					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("Gara " + idgara + " non presente nel database.");
						}
					}
				}

				// Zip della cartella
				String pathOutputFileZip = percorsoCartellaExport + ".zip";

				zipFolder(percorsoCartellaExport, pathOutputFileZip);

				File exportZipFile = new File(pathOutputFileZip);

				byte[] byteArray = new byte[(int) exportZipFile.length()];

				byteArray = FileUtils.readFileToByteArray(exportZipFile);

				// Rimuovere cartella di(con i) file xml temporanei e del file
				// zip
				rimuoveCartellaTemporanea(percorsoCartellaExport);

				// Restituzione del file zippato nella response
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + exportZipFile.getName() + "\"");
				response.getOutputStream().write(byteArray);

				if (logger.isDebugEnabled()) {
					logger.debug("EsportaGara: fine metodo");
				}

				return null;

			} catch (SQLException e) {
				messageKey = "errors.exportGara.sqlException";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} catch (GestoreException e) {
				messageKey = "errors.exportGara.gestoreException";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} catch (ParseException e) {
				messageKey = "errors.exportGara.parseException";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} catch (IOException e) {
				messageKey = "errors.exportGara.IOException";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} catch (Exception e) {
				messageKey = "errors.exportGara.Exception";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} catch (Throwable e) {
				messageKey = "errors.exportGara.Exception";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} finally {
				if (StringUtils.isNotEmpty(messageKey)) {
					// Rimuovere cartella di(con i) file xml temporanei e del
					// file zip
					rimuoveCartellaTemporanea(percorsoCartellaExport);
				}
			}
		}
		return mapping.findForward(target);
	}

	/**
	 * Metodo per controllare l'esistenza della gara a partire dal suo id
	 * 
	 * @param idGara
	 *            identificativo gara
	 * @return se la gara esiste
	 * @throws SQLException
	 *             SQLException
	 */

	private Boolean existGara(String idGara) throws SQLException {
		String existGaraString = "select count(codgara) from W9GARA where idavgara =?";
		Long garaCount = (Long) this.sqlManager.getObject(existGaraString, new Object[] { idGara });
		if (garaCount > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo per ottenere il codice fiscale della stazione appaltante a partire
	 * dal codice della gara 
	 * 
	 * @param idgara
	 *            codice gara
	 * @return ritorna il codice fiscale della stazione appaltante
	 * @throws SQLException
	 *             SQLException
	 */

	private String getCfSa(String idgara) throws SQLException {
		String queryGetSA = "select uff.CFEIN from uffint uff join w9gara gara on uff.codein = gara.codein where gara.idavgara = ?";
		return (String) this.sqlManager.getObject(queryGetSA, new Object[] { idgara });
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

	private Long getCodGara(String idGara) throws SQLException {
		String queryGetCodGara = "select CODGARA from W9GARA where idavgara = ?";
		return (Long) this.sqlManager.getObject(queryGetCodGara, new Object[] { idGara });
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

	private String getCodCIG(Long codGara, Long codLotto) throws SQLException {
		String queryGetCIG = "select CIG from W9LOTT where codgara = ? and codlott = ? ";
		return (String) this.sqlManager.getObject(queryGetCIG, new Object[] { codGara, codLotto });
	}

	/**
	 * Metodo per generareil bean dell'anagrafica della gara non contenuta nella
	 * cartella w9fasi quindi non estraibile da db
	 * 
	 * @param idgara
	 *            codice gara
	 * @param datiComuni
	 *            dati comuni necessari alla creazione dei bean Gara Lotto
	 * @return ritorna la lista di oggetti GaraLottoBean
	 * @throws SQLException
	 *             SQLException
	 */

	private GaraLottoBean generazioneAnagraficaGaraBean(String idGara, String cfsa, Long syscon, String nomeCompositore, String mailCompositore) throws SQLException {

		Long codiceGara = getCodGara(idGara);
		Long codiceLotto = new Long(0);

		DatiComuniBean datiComuni = new DatiComuniBean();
		datiComuni.setCfStazioneAppaltante(cfsa);
		if (syscon != null) {
			datiComuni.setCodiceCompositore(syscon);
		}
		if (StringUtils.isNotEmpty(nomeCompositore)) {
			datiComuni.setNomeCompositore(nomeCompositore);
		}
		if (StringUtils.isNotEmpty(mailCompositore)) {
			datiComuni.setMailCompositore(mailCompositore);
		}
		datiComuni.setCodiceFase(new Long(988));
		datiComuni.setProgressivoFase(new Long(1));
		datiComuni.setTipoInvio(new Long(1));

		GaraLottoBean garaLottoBean = new GaraLottoBean();
		garaLottoBean.setDatiComuni(datiComuni);
		garaLottoBean.setCodiceGara(codiceGara);
		garaLottoBean.setCodiceLotto(codiceLotto);

		return garaLottoBean;
	}

	/**
	 * Metodo per generare la lista gara lotti fasi a partire dall'id della gara
	 * 
	 * @param cfsa
	 *            codice della stazione appaltante
	 * @param sysConRup
	 *            codice fiscale del rup
	 * @param sysCon
	 *            codice fiscale dell'utente che esegue l'export
	 * @param idgara
	 *            codice gara
	 * @return ritorna la lista di oggetti GaraLottoBean
	 * @throws SQLException
	 *             SQLException
	 */

	private List<GaraLottoBean> getGaraLottiBeans(String cfsa, Long syscon, String idgara) throws SQLException {

		List<GaraLottoBean> listaGareLotti = new ArrayList<GaraLottoBean>();
		String nomeCompositore = null;
		String mailCompositore = null;
		if (syscon != null) {
			nomeCompositore = (String) sqlManager.getObject("select SYSUTE from USRSYS where SYSCON=?", new Object[] { syscon });
			mailCompositore = (String) sqlManager.getObject("select EMAIL from USRSYS where SYSCON=?", new Object[] { syscon });
		}

		String selectDati = "select " //
				+ " gara.codgara, " // 0
				+ " lotto.codlott, " // 1
				+ " fasi.fase_esecuzione, " // 2
				+ " fasi.num, " // 3
				+ " fasi.daexport, " // 4
				+ " uff.cfein " // 5
				+ " from w9gara gara left join w9lott lotto " // join lotto
				+ " on gara.codgara = lotto.codgara" //
				+ " left join uffint uff" // join uffint
				+ " on uff.codein=gara.codein" //
				+ " left join w9fasi fasi" // join fasi
				+ " on lotto.codgara = fasi.codgara and lotto.codlott = fasi.codlott" //
				+ " where gara.idavgara = ?";

		List listaDati = this.sqlManager.getListVector(selectDati, new Object[] { idgara });

		// l'anagarafica non e' presente come fase w9fasi nel db
		listaGareLotti.add(generazioneAnagraficaGaraBean(idgara, cfsa, syscon, nomeCompositore, mailCompositore));

		for (int i = 0; i < listaDati.size(); i++) {

			Vector riga = (Vector) listaDati.get(i);

			if (StringUtils.isNotEmpty(riga.get(0).toString()) && StringUtils.isNotEmpty(riga.get(1).toString()) && StringUtils.isNotEmpty(riga.get(2).toString())
					&& StringUtils.isNotEmpty(riga.get(3).toString())) {
				Long codiceGara = new Long(riga.get(0).toString());
				Long codiceLotto = new Long(riga.get(1).toString());
				Long codiceFase = new Long(riga.get(2).toString());
				Long progressivoFase = new Long(riga.get(3).toString());
				Long daExport = null;
				if (riga.get(4) != null && StringUtils.isNotEmpty(riga.get(4).toString())) {
					daExport = new Long(riga.get(4).toString());
				}
				String cfsaGara = null;
				if (riga.get(5) != null && StringUtils.isNotEmpty(riga.get(5).toString())) {
					cfsaGara = riga.get(5).toString();
				}

				// Oggetto per contenere i dati comuni necessari alla creazione
				// della testata (FaseType o FaseEstesaType) di un flusso.
				DatiComuniBean datiComuni = new DatiComuniBean();

				datiComuni.setCfStazioneAppaltante(cfsaGara);
				if (syscon != null) {
					datiComuni.setCodiceCompositore(syscon);
				}
				if (StringUtils.isNotEmpty(nomeCompositore)) {
					datiComuni.setNomeCompositore(nomeCompositore);
				}
				if (StringUtils.isNotEmpty(mailCompositore)) {
					datiComuni.setMailCompositore(mailCompositore);
				}
				datiComuni.setNoteInvio("");
				datiComuni.setTipoInvio(new Long(1));
				datiComuni.setEsito(Boolean.TRUE);
				datiComuni.setCodiceFase(codiceFase);
				datiComuni.setProgressivoFase(progressivoFase);
				datiComuni.setDaExport(daExport);

				GaraLottoBean garaLottoBean = new GaraLottoBean();
				garaLottoBean.setDatiComuni(datiComuni);
				garaLottoBean.setCodiceGara(codiceGara);
				garaLottoBean.setCodiceLotto(codiceLotto);

				listaGareLotti.add(garaLottoBean);
			}
		}

		return listaGareLotti;
	}

	/**
	 * Metodo per generare la lista delle pubblicazioni di una gara
	 * 
	 * @param cfsa
	 *            codice della stazione appaltante
	 * @param sysConRup
	 *            codice fiscale del rup
	 * @param idgara
	 *            codice gara
	 * @return lista delle pubblicazioni
	 * @throws SQLException
	 *             SQLException
	 */

	private List<PubblicazioneBean> getPubblicazioniBeans(String cfsa, String idgara) throws SQLException {

		List<PubblicazioneBean> listaPubblicazioni = new ArrayList<PubblicazioneBean>();

		String selectDati = "select " + " gara.codgara," + " pubb.num_pubb " + " from w9gara gara " + " left join w9pubblicazioni pubb " + " on gara.codgara = pubb.codgara "
				+ " where gara.idavgara = ? ";

		List listaDati = this.sqlManager.getListVector(selectDati, new Object[] { idgara });

		for (int i = 0; i < listaDati.size(); i++) {

			Vector riga = (Vector) listaDati.get(i);

			if (StringUtils.isNotEmpty(riga.get(0).toString()) && StringUtils.isNotEmpty(riga.get(1).toString())) {

				Long codiceGara = new Long(riga.get(0).toString());
				Long numero_pubblicazione = new Long(riga.get(1).toString());

				// Oggetto per contenere i dati comuni necessari alla creazione
				// della testata (FaseType o FaseEstesaType) di un flusso.
				DatiComuniBean datiComuni = new DatiComuniBean();
				datiComuni.setCodiceFase(new Long(901));
				datiComuni.setProgressivoFase(new Long(1));
				datiComuni.setCfStazioneAppaltante(cfsa);
				datiComuni.setEsito(Boolean.TRUE);
				datiComuni.setTipoInvio(new Long(1));

				PubblicazioneBean pubblicazioniBean = new PubblicazioneBean();
				pubblicazioniBean.setCodiceGara(codiceGara);
				pubblicazioniBean.setNumeroPubblicazione(numero_pubblicazione);
				pubblicazioniBean.setDatiComuni(datiComuni);

				listaPubblicazioni.add(pubblicazioniBean);
			}
		}
		return listaPubblicazioni;
	}

	/**
	 * Metodo per comprimere una cartella contenente solo file NOTA: il percorso
	 * del file zippato non lo deve vedere contenuto nella cartella d'origine
	 * della funzione.
	 * 
	 * @param srcFolder
	 *            percorso completo della cartella da comprimere
	 * @param destZipFile
	 *            percorso completo del file zippato da ottenere
	 * @throws Exception
	 *             Exception
	 */

	static public void zipFolder(String srcFolder, String destZipFile) throws Exception {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);

		addFolderToZip("", srcFolder, zip);
		zip.flush();
		zip.close();
		fileWriter.flush();
		fileWriter.close();
	}

	static private void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {

		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
			in.close();
		}
	}

	static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}

	/**
	 * Metodo per creare una cartella temporanea propria di un singolo export
	 * 
	 * @return Ritorna il percorso della cartella riservata all'export
	 */

	private String creaCartellaTemporanea() {

		// per evitare sovrapposizione ogni esportazione avra'
		// una propria cartella definita da una data di creazione
		Date dataCreazioneDocumento = new Date();

		Format formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String dataCreazioneDocumentoStringa = formatter.format(dataCreazioneDocumento);

		String cartellaExport = "ExportXML_" + dataCreazioneDocumentoStringa;

		// cartella da usare - Temp di Tomcat java.io.tmpdir -
		// javax.servlet.context.tempdir
		String percorsoCartellaExport = System.getProperty("java.io.tmpdir") + File.separatorChar + cartellaExport;

		boolean success = (new File(percorsoCartellaExport)).mkdir();

		if (success) {
			return percorsoCartellaExport;
		}

		return null;
	}

	/**
	 * Metodo per rimuovere una cartella ed i file in essa contenuti
	 */

	private void rimuoveCartellaTemporanea(String cartellaExport) {
		File directory = new File(cartellaExport);
		File[] files = directory.listFiles();
		for (File f : files) {
			f.delete();
		}
		directory.delete();
		String pathFileExportZip = cartellaExport + ".zip";
		File fileExportZip = new File(pathFileExportZip);
		fileExportZip.delete();
		return;
	}

}
