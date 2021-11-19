package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.w9.bl.EsitoMessageReader;
import it.eldasoft.w9.bl.delegateImport.AbstractRequestHandler;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.web.struts.utilDocForm.UnzipUtility;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.W9FlussoType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xmlbeans.XmlException;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionStatus;

public class ImportaGaraAction extends ActionBaseNoOpzioni {

	/** Logger Log4J di classe. */
	private static Logger logger = Logger.getLogger(ImportaGaraAction.class);

	private SqlManager sqlManager;

	private AbstractRequestHandler requestHandler;

	private GenChiaviManager genChiaviManager;

	/**
	 * @param sqlManager
	 *            sqlManager da settare internamente alla classe.
	 */
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	/**
	 * @param genChiaviManager
	 *            genChiaviManager da settare internamente alla classe.
	 */
	public void setGenChiaviManager(GenChiaviManager genChiaviManager) {
		this.genChiaviManager = genChiaviManager;
	}

	/**
	 * @param requestHandler
	 *            requestHandler da settare internamente alla classe.
	 */
	public void setRequestHandler(AbstractRequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	protected ActionForward runAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String target = "success";

		List<String> idGareImportate = new ArrayList<String>();
		String messageKey = null;
		String percorsoDirectory = null;
		String elencoIdImportati = null;
		String separatoreIdImportati = ", ";
		String filePath = System.getProperty("java.io.tmpdir") + File.separatorChar;

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		// controllo se la form e' multipart --> contiene un file da elaborare
		if (isMultipart) {

			EsitoMessageReader esito = new EsitoMessageReader();

			try {

				if (logger.isDebugEnabled()) {
					logger.debug("ImportaGara: inizio metodo");
				}

				// carico il file nella cartella temporanea del Tomcat
				String percorsoFileZip = uploadFile(filePath, request);
				percorsoDirectory = StringUtils.remove(percorsoFileZip, ".zip");

				List<FaseEstesaOrdinabile> listaFlussi = new ArrayList<FaseEstesaOrdinabile>();
				List<String> listaFlussiErrati = new ArrayList<String>();

				// Per evitare di importare flussi in modo casuale ed incorrere
				// nell'errore
				// che si importi il flusso di aggiudicazione prima del flusso
				// anagrafica gara
				HashMap<Long, Double> mappaTab1Cod = getListaOrdinamentoFasi();

				Map<String, String> messaggi = new HashMap<String, String>();
				List<String> flussi = new ArrayList<String>();

				esito.setNumeroMsgLetti(messaggi.size());

				// vado a decomprimere in una cartella il file ZIP
				File[] files = decompressioneLetturaZipFile(percorsoFileZip, percorsoDirectory);

				// analizzo ogni singolo file XML e ne gestisco i dati
				letturaContenutoFilesXML(files, mappaTab1Cod, messaggi, flussi, listaFlussi, listaFlussiErrati);

				// Ordinamento della lista dei flussi
				Collections.sort(listaFlussi);

				for (Iterator<FaseEstesaOrdinabile> iter = listaFlussi.iterator(); iter.hasNext();) {
					FaseEstesaOrdinabile faseEO = iter.next();
					String idEgov = faseEO.getIdegov();
					String nomeFile = faseEO.getNomeFile();
					String messaggioXML = messaggi.get(nomeFile);
					if (logger.isDebugEnabled()) {
						logger.debug("Salvataggio messaggio con idEgov=" + idEgov + "\n" + " del file=" + nomeFile + ".xml" + "\n" + new String(messaggioXML));
					}
					this.saveMessage(idEgov, messaggioXML);
					if (!idGareImportate.contains(idEgov)) {
						idGareImportate.add(idEgov);
					}
					esito.setNumeroMsgSalvati(esito.getNumeroMsgSalvati() + 1);
				}

				// Elaborazione degli eventuali flussi errati
				for (int ui = 0; ui < listaFlussiErrati.size(); ui++) {
					String nomeFile = listaFlussiErrati.get(ui);
					String idEgov = (nomeFile.split("_"))[0];
					String messaggioXML = messaggi.get(nomeFile);
					if (logger.isDebugEnabled()) {
						logger.debug("Salvataggio messaggio con idEgov=" + idEgov + "\n" + " del file=" + nomeFile + ".xml" + "\n" + new String(messaggioXML));
					}
					this.saveMessage(idEgov, messaggioXML);
					esito.setNumeroMsgSalvati(esito.getNumeroMsgSalvati() + 1);
				}

				// elaborazione dei flussi
				gestioneFlussiXml(flussi);

				// finita la procedura procedo con la rimozione del file zip e
				// della cartella nell'area temporanea
				rimuoveCartellaTemporanea(percorsoDirectory);

				if (logger.isDebugEnabled()) {
					logger.debug("ImportaGara: fine metodo");
				}

			} catch (FileUploadException e) {
				messageKey = "errors.importGara.sqlException";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} catch (IOException e) {
				messageKey = "errors.importGara.IOException";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} catch (ServiceException e) {
				messageKey = "errors.importGara.serviceException";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} catch (SQLException e) {
				messageKey = "errors.importGara.sqlException";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} catch (Exception e) {
				if (e.getMessage().contains("La gara e' gia' presente in archivio")) {
					this.aggiungiMessaggio(request, "errors.importGara.duplicateException", e.getMessage());
					logger.error(e.getMessage(), e);
					messageKey = "errors.importGara.duplicateException";
				} else {
					messageKey = "errors.importGara.Exception";
					logger.error(this.resBundleGenerale.getString(messageKey), e);
					this.aggiungiMessaggio(request, messageKey);
				}
			} catch (Throwable e) {
				messageKey = "errors.importGara.Exception";
				logger.error(this.resBundleGenerale.getString(messageKey), e);
				this.aggiungiMessaggio(request, messageKey);
			} finally {
				if (StringUtils.isNotEmpty(messageKey)) {
					// Rimuovere cartella di(con i) file xml temporanei e del
					// file zip
					rimuoveCartellaTemporanea(percorsoDirectory);
				} else {
					// e' andato tutto a buon fin rialscio un messaggio di
					// completamento per l'importazione
					messageKey = "info.importGara.success";
					if (idGareImportate.size() == 1) {
						messageKey = "info.importGara.successMono";
					}

					for (int i = 0; i < idGareImportate.size(); i++) {
						if (elencoIdImportati != null) {
							elencoIdImportati = elencoIdImportati + separatoreIdImportati;
						} else {
							elencoIdImportati = "";
						}
						elencoIdImportati = elencoIdImportati + idGareImportate.get(i);
					}
					this.aggiungiMessaggio(request, messageKey, elencoIdImportati);
				}
			}
		}
		return mapping.findForward(target);
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
	 * Metodo per ottenere il codice lotto
	 * 
	 * @param codGara
	 *            codice gara
	 * @param codCIG
	 *            codice CIG
	 * @return ritorna il codice cig di un lotto
	 * @throws SQLException
	 *             SQLException
	 */

	private Long getCodLott(Long codGara, String codCIG) throws SQLException {
		String queryGetCIG = "select codLott from W9LOTT where codgara = ? and CIG = ? ";
		return (Long) this.sqlManager.getObject(queryGetCIG, new Object[] { codGara, codCIG });
	}

	/**
	 * Metodo per gestire i file XML dei FLussi ed inserirli in DB
	 * 
	 * @param flussi
	 *            lista flussi della gara esportati in xml
	 * @throws XmlException
	 * @throws SQLException
	 */

	private void gestioneFlussiXml(List<String> flussi) throws XmlException, SQLException, Exception {
		TransactionStatus status = null;

		status = this.sqlManager.startTransaction();

		for (int i = 0; i < flussi.size(); i++) {

			W9FlussoType w9FlussoType = W9FlussoType.Factory.parse(flussi.get(i));

			String idGara = w9FlussoType.getW9FLEKEY01(); // ottengo idavgara
			Long codGara = getCodGara(idGara);

			Long idFlusso = new Long(this.genChiaviManager.getNextId("W9FLUSSI"));

			DataColumnContainer dccFlusso = new DataColumnContainer(new DataColumn[] { new DataColumn("W9FLUSSI.IDFLUSSO", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, idFlusso)) });

			dccFlusso.addColumn("W9FLUSSI.AREA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(w9FlussoType.getW9FLAREA())));

			dccFlusso.addColumn("W9FLUSSI.KEY01", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara));
			if (w9FlussoType.getW9FLEKEY02() != null) {
				String codCig = w9FlussoType.getW9FLEKEY02(); // ottengo CIG
				if (codGara != null && StringUtils.isNotEmpty(codCig)) {
					Long codLotto = getCodLott(codGara, codCig);
					dccFlusso.addColumn("W9FLUSSI.KEY02", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLotto));
				}
			}
			if (w9FlussoType.getW9FLEKEY03() != null) {
				dccFlusso.addColumn("W9FLUSSI.KEY03", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(w9FlussoType.getW9FLEKEY03().toString())));
			}
			dccFlusso.addColumn("W9FLUSSI.KEY04", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(w9FlussoType.getW9FLEKEY04())));
			if (w9FlussoType.getW9FLETINVIO() != null) {
				dccFlusso.addColumn("W9FLUSSI.TINVIO2", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(w9FlussoType.getW9FLETINVIO().toString())));
			}
			if (w9FlussoType.getW9FLDATINV() != null) {
				dccFlusso.addColumn("W9FLUSSI.DATINV", new JdbcParametro(JdbcParametro.TIPO_DATA, w9FlussoType.getW9FLDATINV().getTime()));
			}
			if (w9FlussoType.getW9NOTINVIO() != null) {
				dccFlusso.addColumn("W9FLUSSI.NOTEINVIO", new JdbcParametro(JdbcParametro.TIPO_TESTO, w9FlussoType.getW9NOTINVIO()));
			}
			if (w9FlussoType.getW9FLEXML() != null) {
				dccFlusso.addColumn("W9FLUSSI.XML", new JdbcParametro(JdbcParametro.TIPO_TESTO, w9FlussoType.getW9FLEXML()));
			}
			dccFlusso.addColumn("W9FLUSSI.CODCOMP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, w9FlussoType.getW9FLCODCOM()));
			dccFlusso.addColumn("W9FLUSSI.IDCOMUN", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, w9FlussoType.getW9FLCOMUN()));

			if (w9FlussoType.getW9FLCFSA() != null) {
				dccFlusso.addColumn("W9FLUSSI.CFSA", new JdbcParametro(JdbcParametro.TIPO_TESTO, w9FlussoType.getW9FLCFSA()));
			}
			if (w9FlussoType.getW9FLCODOGG() != null) {
				dccFlusso.addColumn("W9FLUSSI.CODOGG", new JdbcParametro(JdbcParametro.TIPO_TESTO, w9FlussoType.getW9FLCODOGG()));
			}
			if (w9FlussoType.getW9IBDTIMP() != null) {
				dccFlusso.addColumn("W9FLUSSI.DATIMP", new JdbcParametro(JdbcParametro.TIPO_DATA, w9FlussoType.getW9IBDTIMP().getTime()));
			}
			if (w9FlussoType.getW9FLVERXML() != null) {
				dccFlusso.addColumn("W9FLUSSI.VERXML", new JdbcParametro(JdbcParametro.TIPO_TESTO, w9FlussoType.getW9FLVERXML()));
			}
			if (w9FlussoType.getW9FLEAUTORE() != null) {
				dccFlusso.addColumn("W9FLUSSI.AUTORE", new JdbcParametro(JdbcParametro.TIPO_TESTO, w9FlussoType.getW9FLEAUTORE()));
			}
			dccFlusso.insert("W9FLUSSI", this.sqlManager);

		}

		this.sqlManager.commitTransaction(status);

	}

	/**
	 * Metodo per testare l'esistenza in DB di una gara a partire dal suo ID
	 * 
	 * @param idGara
	 *            l'identificativo della gara
	 * @param messageKey
	 *            il messaggio per la gestioen dell'errore
	 * @throws SQLException
	 *             SQLException
	 * @throws Exception
	 *             Exception
	 */

	private void esisteGaraInArchivio(String idGara) throws SQLException, Exception {
		String esisteGaraInArchivioString = "select count(*) from w9gara where idavgara = ?";
		Long esisteGaraInArchivio = (Long) this.sqlManager.getObject(esisteGaraInArchivioString, new Object[] { idGara });
		if (esisteGaraInArchivio > 0) {
			String message = "I file nell'archivio zip che si riferiscono alla gara con id " + idGara + " non possono essere importati. "
					+ "La gara e' gia' presente in archivio. L'intera operazione viene quindi interrotta. ";
			throw new Exception(message);
		}
	}

	/**
	 * Metodo per analizzare i singoli file XML estratti dal file compresso di
	 * export trasformandoli in fasi e prepararli per l'import
	 * 
	 * @param files
	 *            elenco dei file estratti dal file di export
	 * @param messageKey
	 *            possibile messaggio di errore nella gestione delle eccezioni
	 * @param mappaTab1Cod
	 *            elenco delle fasi ordinate per agibilità
	 * @param messaggi
	 *            elenco dei file xml indicati dal relativo id gara
	 * @param listaFlussi
	 *            lista dei flussi
	 * @param listaFlussiErrati
	 *            lsiat dei flussi non importabili
	 * @throws SQLException
	 *             SQLException
	 * @throws IOException
	 *             IOException
	 * @throws Exception
	 *             Exception
	 */
	private void letturaContenutoFilesXML(File[] files, HashMap<Long, Double> mappaTab1Cod, Map<String, String> messaggi, List<String> flussi, List<FaseEstesaOrdinabile> listaFlussi,
			List<String> listaFlussiErrati) throws SQLException, IOException, Exception {
		String idGaraControllo = "";
		List<Long> numeroFileFlussi = new ArrayList<Long>();
		HashMap<Long, File> numeroFileFileFlussi = new HashMap<Long, File>();

		for (File fileXML : files) {
			// ora procedo per ciascuno file XML alla sua lettura e preparazione
			// per l'importazione
			if (logger.isDebugEnabled()) {
				logger.debug("Inizio Gestione File : " + fileXML.getName());
			}
			// fileXML;
			String nomeFile = StringUtils.remove(fileXML.getName(), ".xml");

			if (nomeFile.contains("Flusso")) {
				String[] arrayNomeFile = nomeFile.split("_");
				Long numeroFile = new Long(arrayNomeFile[1]);
				numeroFileFlussi.add(numeroFile);
				numeroFileFileFlussi.put(numeroFile, fileXML);
			} else {
				String idGara = null;
				String file2StringXML = null;

				String[] arrayNomeFile = nomeFile.split("_");

				idGara = arrayNomeFile[0];

				if (!(idGara.equals(idGaraControllo))) {
					esisteGaraInArchivio(idGara);
					idGaraControllo = idGara;
				}

				file2StringXML = readFile(fileXML);
				FaseType fase = getFase(file2StringXML);

				if (logger.isDebugEnabled()) {
					logger.debug("Lettura del messaggio con idEgov = " + idGara);
				}

				int num = -1;
				if (fase != null) {
					if (fase instanceof FaseEstesaType) {
						num = ((FaseEstesaType) fase).getW3NUM5();
					}
					FaseEstesaOrdinabile faseOrdinabile = new FaseEstesaOrdinabile(fase.getW9FLCFSA(), mappaTab1Cod.get(new Long(fase.getW3FASEESEC().toString())), new Long(fase.getW3PGTINVIO2()
							.toString()), num != -1 ? new Long(num) : null, idGara, nomeFile);
					listaFlussi.add(faseOrdinabile);
				} else {
					listaFlussiErrati.add(nomeFile);
				}
				messaggi.put(nomeFile, file2StringXML);
				if (logger.isDebugEnabled()) {
					logger.debug("Fine Gestione File : " + fileXML.getName());
				}
			}

		}
		// ordinamento dei flussi in base al nome che contine l'id
		Collections.sort(numeroFileFlussi);
		for (int i = 0; i < numeroFileFlussi.size(); i++) {
			// creazione della lista ordinata dei flussi
			flussi.add(readFile(numeroFileFileFlussi.get(numeroFileFlussi.get(i))));
		}
	}

	/**
	 * Metodo per generare la lista ordinata dei codici delle fasi
	 * 
	 * @return Ritorna la lista ordinata delle fasi per una gara
	 * @throws SQLException
	 *             SQLException
	 */
	private HashMap<Long, Double> getListaOrdinamentoFasi() throws SQLException {
		String selectOrdinamentoFasi = "select TAB1TIP, TAB1NORD from TAB1 where TAB1COD = ? order by TAB1NORD asc";
		List<?> listaTab1Cod = this.sqlManager.getListVector(selectOrdinamentoFasi, new Object[] { "W3020" });
		HashMap<Long, Double> mappaTab1Cod = new HashMap<Long, Double>();
		for (int iui = 0; iui < listaTab1Cod.size(); iui++) {
			Vector<?> riga = (Vector<?>) listaTab1Cod.get(iui);
			Long tab1tip = (Long) SqlManager.getValueFromVectorParam(riga, 0).getValue();
			Double tab1nord = (Double) SqlManager.getValueFromVectorParam(riga, 1).getValue();
			mappaTab1Cod.put(tab1tip, tab1nord);
		}
		mappaTab1Cod.put(new Long("988"), -1.0);
		return mappaTab1Cod;
	}

	/**
	 * Metodo per rimuovere una cartella ed i file in essa contenuti
	 * 
	 * @param cartellaExport
	 *            il percorso della cartella da rimuovere
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

	/**
	 * Metodo per il caricamento di un file da forma in una cartella a scelta
	 * dell'applicativo
	 * 
	 * @param filepath
	 *            il percorso dove verra' salvato il file
	 * @param request
	 * 
	 * @return Ritorna il percorso completo del file caricato
	 * @throws FileUploadException
	 *             FileUploadException
	 * @throws Exception
	 *             Exception
	 */
	private String uploadFile(String filePath, HttpServletRequest request) throws FileUploadException, Exception {
		File file = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		List fileItems = upload.parseRequest(request);
		Iterator i = fileItems.iterator();
		while (i.hasNext()) {
			FileItem fi = (FileItem) i.next();
			if (!fi.isFormField()) {
				// ottengo il nome del file
				String fileName = fi.getName();

				// Scrivo il file sul filesistem
				if (fileName.lastIndexOf("\\") >= 0) {
					file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
				} else {
					file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
				}
				fi.write(file);

				if (logger.isDebugEnabled()) {
					logger.debug("File Estratto: " + fileName + "<br>");
				}
			}
		}
		return file.getAbsolutePath();
	}

	/**
	 * Metodo per l'estrazione del file zip nella cartella temporanea del tomcat
	 * 
	 * @param percorsoFileZip
	 *            il percorso del file zip
	 * @param percorsoDirectory
	 *            percorso della cartella di destinazione
	 * 
	 * @return Ritorna la lista dei file contenuti nella cartella scompattata
	 * @throws IOException
	 *             IOException
	 */
	private File[] decompressioneLetturaZipFile(String percorsoFileZip, String percorsoDirectory) throws IOException {
		// Utilizzo l'utility UnZipUtility per la decompressione
		// NOTA : L'utility in questione ha un difetto, si aspetta che
		// nello zip siano presenti solo file, dato che invece lo zip
		// continene una cartella con a sua volta i file xml devo prima
		// procedere a creare una cartella con il nome atteso per poter
		// decomprimere i file letti dall'utility con il percorso
		// completo path+nome
		File directory = new File(percorsoDirectory);
		directory.mkdir();
		String destDirectory = System.getProperty("java.io.tmpdir");
		// decomprimo lo zip
		UnzipUtility unzipper = new UnzipUtility();
		unzipper.unzip(percorsoFileZip, destDirectory);

		return directory.listFiles();
	}

	/**
	 * Metodo per leggere il contenuto di un file ttornato come stringa
	 * 
	 * @param file
	 *            l'oggetto file
	 * @return Ritorna il contenuto del file sotto forma di stringa
	 * @throws IOException
	 *             IOException
	 */
	private String readFile(File file) throws IOException {
		String pathFile = file.getAbsolutePath();
		BufferedReader reader = new BufferedReader(new FileReader(pathFile));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}

	/**
	 * Estrae l'elemento fase della richiesta.
	 * 
	 * @param contenutoXML
	 *            il contenuto come stringa del file XML
	 * @return Ritorna l'elemento fase della richiesta
	 */
	public FaseType getFase(String contenutoXML) {
		return this.requestHandler.getFaseEvento(contenutoXML);
	}

	/**
	 * Salva il messaggio sul DB rendendolo persistente, ne processa il
	 * contenuto
	 * 
	 * @param idEgov
	 *            id egov del messaggio
	 * @param messageXML
	 *            messaggio XML
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	public void saveMessage(String idEgov, String messageXML) throws RemoteException, ServiceException {
		processEvento(messageXML, idEgov);
	}

	/**
	 * Inizializza la transazione, inserisce un'occorrenza nella W9INBOX, e
	 * demanda la gestione della richiesta alla catena, nonche' chiude la
	 * transazione a seconda dell'esito dell'operazione ritornata dalla catena.
	 * L'eventuale generazione di eccezioni comporta una interruzione anomala,
	 * gestita mediante segnalazione sul log, e la non cancellazione del
	 * messaggio in modo da poterlo riprocessare nel seguito una volta corretta
	 * l'anomalia applicativa.
	 * 
	 * @param xmlEvento
	 *            documento xml
	 * @param idEgovString
	 *            id gara
	 * @return true se la richiesta e' stata gestita e quindi va eliminata,
	 *         false altrimenti
	 */
	public boolean processEvento(String xmlEvento, String idEgovString) {
		boolean successProcess = false;

		TransactionStatus status = null;
		int idW9inbox = 0;
		int passo = 1;
		try {
			// si avvia la transazione per l'aggiornamento del DB
			status = this.sqlManager.startTransaction();

			// si genera l'id per la W9INBOX
			idW9inbox = this.genChiaviManager.getNextId("W9INBOX");

			// si creano tutte le colonne di W9INBOX
			DataColumn chiaveW9inbox = new DataColumn("W9INBOX.IDCOMUN", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(idW9inbox)));

			DataColumn statoComunicazione = new DataColumn("W9INBOX.STACOM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, StatoComunicazione.STATO_RICEVUTA.getStato()));

			Timestamp dataAttuale = new Timestamp(new Date().getTime());
			DataColumn dataRicezione = new DataColumn("W9INBOX.DATRIC", new JdbcParametro(JdbcParametro.TIPO_DATA, dataAttuale));

			DataColumn xml = new DataColumn("W9INBOX.XML", new JdbcParametro(JdbcParametro.TIPO_TESTO, xmlEvento));

			DataColumn msg = new DataColumn("W9INBOX.MSG", new JdbcParametro(JdbcParametro.TIPO_TESTO, null));

			DataColumn idEgov = new DataColumn("W9INBOX.IDEGOV", new JdbcParametro(JdbcParametro.TIPO_TESTO, idEgovString));

			// si popola il contenitore e si effettua la insert in W9INBOX
			DataColumnContainer datiAggiornamento = new DataColumnContainer(new DataColumn[] { chiaveW9inbox, statoComunicazione, dataRicezione, xml, msg, idEgov });

			chiaveW9inbox.setChiave(true);
			chiaveW9inbox.setObjectOriginalValue(chiaveW9inbox.getValue());
			statoComunicazione.setObjectOriginalValue(statoComunicazione.getValue());
			dataRicezione.setObjectOriginalValue(dataRicezione.getValue());
			xml.setObjectOriginalValue(xml.getValue());
			msg.setObjectValue(msg.getValue());
			idEgov.setObjectOriginalValue(idEgov.getValue());

			try {
				// Inizio del secondo passo
				passo++;
				// si effettua il process della richiesta (i warning in questo
				// caso
				// NON sono bloccanti)
				this.requestHandler.processEvento(xmlEvento, datiAggiornamento, true);

			} catch (Exception ex) {
				logger.error("Errore nel salvataggio del flusso e dei dati in esso contenuti nella catena degli handler", ex);

				// Aggiornamento dello stato della comunicazione e del relativo
				// messaggio
				statoComunicazione.setValue(new JdbcParametro(JdbcParametro.TIPO_NUMERICO, StatoComunicazione.STATO_ERRATA.getStato()));
				msg.setValue(new JdbcParametro(JdbcParametro.TIPO_TESTO, "Errore nel salvataggio del flusso e dei dati in esso contenuti. Vedere il log per ulteriori dettagli"));
			}

			// Inizio del terzo passo
			passo++;

			successProcess = true;

		} catch (DataAccessException e) {
			if (idW9inbox == 0) {
				logger.error("Errore inaspettato durante la generazione dell'id della chiave per la tabella W9INBOX", e);
			} else {
				logger.error("Errore inaspettato durante l'interazione con la base dati", e);
			}
		} catch (SQLException e) {
			if (status == null) {
				logger.error("Impossibile aprire la transazione per salvare i dati letti nel messaggio", e);
			} else {
				logger.error("Si e' verificato un errore inaspettato durante l'interazione con la base dati", e);
			}
		} catch (Throwable e) {
			// per sicurezza si blinda il codice in modo da avere ogni eccezione
			// catchata e tracciata nel log
			logger.error("Errore inaspettato durante l'elaborazione dell'operazione", e);
		} finally {
			if (status != null) {
				try {
					if (successProcess || (passo <= 2 && !successProcess)) {
						this.sqlManager.commitTransaction(status);
					} else {
						this.sqlManager.rollbackTransaction(status);
					}
				} catch (SQLException e) {
				}
			}
		}

		return successProcess;
	}

	/**
	 * Classe per ordinare i flussi scaricati dal CART secondo alcuni degli
	 * attributi dell'oggetto FaseType o FaseEstesaType.
	 * 
	 * Il tabellato W3998 (Tipologia di invio) prevede i seguenti valori: 1 -
	 * Primo invio 2 - Rettifica 3 - Integrazione 99 - Test
	 * 
	 * Secondo questo tabellato, due flussi con la stessa fase, ma con tipologia
	 * invio diversa, vengono importati per tipologia invio crescente (quindi
	 * rettifica viene importato prima di integrazione)
	 * 
	 * @author Luca.Giacomazzo
	 */
	class FaseEstesaOrdinabile implements Comparable<FaseEstesaOrdinabile> {

		/**
		 * CF della Stazione appaltante.
		 */
		private final String codiceFiscaleSA;
		/**
		 * Numero d'ordine della fase.
		 */
		private Double numeroOrdineFaseFlusso;
		/**
		 * Tipo di invio (primo invio, rettifica, integrazione).
		 */
		private Long tipoInvio;
		/**
		 * Numero Progressivo.
		 */
		private Long numeroProgressivo;
		/**
		 * IdEgov del flusso.
		 */
		private String idegov;
		/**
		 * Nome del file XML ricevuto.
		 */
		private String nomeFile;

		/**
		 * Costruttore senza argomenti.
		 */
		public FaseEstesaOrdinabile() {
			this.codiceFiscaleSA = null;
			this.numeroOrdineFaseFlusso = new Double(Double.MIN_VALUE);
			this.tipoInvio = new Long(Long.MIN_VALUE);
			this.numeroProgressivo = new Long(Long.MIN_VALUE);
			this.idegov = null;
			this.nomeFile = null;
		}

		/**
		 * Costruttore con argomenti.
		 * 
		 * @param cfSA
		 *            CF della Stazione appaltante
		 * @param numeroOrdineFaseFlusso
		 *            Numero d'ordine della fase
		 * @param tipoInvio
		 *            Tipo di invio (primo invio, rettifica, integrazione)
		 * @param numeroProgressivo
		 *            Numero Progressivo
		 * @param idegov
		 *            IdEgov del flusso
		 */
		public FaseEstesaOrdinabile(final String cfSA, final Double numeroOrdineFaseFlusso, final Long tipoInvio, final Long numeroProgressivo, final String idegov, final String nomeFile) {
			this.codiceFiscaleSA = cfSA;

			if (numeroOrdineFaseFlusso != null) {
				this.numeroOrdineFaseFlusso = numeroOrdineFaseFlusso;
			} else {
				this.numeroOrdineFaseFlusso = new Double(Double.MIN_VALUE);
			}
			if (tipoInvio != null) {
				this.tipoInvio = tipoInvio;
			} else {
				this.tipoInvio = new Long(Long.MIN_VALUE);
			}
			if (numeroProgressivo != null) {
				this.numeroProgressivo = numeroProgressivo;
			} else {
				this.numeroProgressivo = new Long(Long.MIN_VALUE);
			}
			if (idegov != null && idegov.length() > 0) {
				this.idegov = idegov;
			}

			if (nomeFile != null && nomeFile.length() > 0) {
				this.nomeFile = nomeFile;
			}
		}

		/**
		 * Ritorna il codice fiscale della stazione appaltante.
		 * 
		 * @return Ritorna il codice fiscale della stazione appaltante
		 */
		public String getCodiceFiscaleSA() {
			return this.codiceFiscaleSA;
		}

		/**
		 * Ritorna il numero d'ordine della fase.
		 * 
		 * @return Ritorna il numero d'ordine della fase
		 */
		public Double getNumeroOrdineFaseFlusso() {
			return this.numeroOrdineFaseFlusso;
		}

		/**
		 * Ritorna il numero progressivo.
		 * 
		 * @return Ritorna il numero progressivo
		 */
		public Long getNumeroProgressivo() {
			return this.numeroProgressivo;
		}

		/**
		 * Ritorna tipo invio.
		 * 
		 * @return Ritorna tipo invio
		 */
		public Long getTipoInvio() {
			return this.tipoInvio;
		}

		/**
		 * Ritorna idegov.
		 * 
		 * @return Ritorna idegov
		 */
		public String getIdegov() {
			return this.idegov;
		}

		/**
		 * Ritorna nomeFile.
		 * 
		 * @return Ritorna nomeFile
		 */
		public String getNomeFile() {
			return this.nomeFile;
		}

		/**
		 * Implementazione del metodo compareTo per l'oggetto
		 * FaseEstesaOrdinabile.
		 * 
		 * @param obj
		 *            Oggetto di tipo FaseEstesaOrdinabile da confrontare
		 * @return Ritorna 1 se l'oggetto e' maggiore dell'oggetto obj, -1 se
		 *         l'oggetto e' minore dell'oggetto obj e 0 se se l'oggetto e'
		 *         uguale all'oggetto obj
		 */
		public int compareTo(final FaseEstesaOrdinabile obj) {
			int result = -1;
			if (obj != null) {
				result = this.codiceFiscaleSA.compareTo(obj.getCodiceFiscaleSA());
				if (result == 0) {
					result = this.numeroOrdineFaseFlusso.compareTo(obj.getNumeroOrdineFaseFlusso());
					if (result == 0) {
						// Nel confrontare il tipo di invio bisogna
						// considerare che in caso di flusso di richiesta
						// cancellazione di una fase il tipo invio vale -1
						// che è sempre minore degli altri valori assunti
						// dal tipo invio. (Vedi TAB1 - W3998). Per questo
						// si usano due variabili temporanee per fare il
						// confronto tra i due tipi di invio e in caso i
						// valori dei tipi invio da confrontare siano minori
						// di zero, allora si valorizza la variabile
						// temporanea a 98.
						result = this.tipoInvio.compareTo(obj.getTipoInvio());
						if (result == 0) {
							result = this.numeroProgressivo.compareTo(obj.getNumeroProgressivo());
							if (result == 0) {
								result = this.nomeFile.compareTo(obj.getNomeFile());
							}
						}
					}
				}
			} else {
				throw new NullPointerException("argument is null");
			}

			return result;
		}

		/**
		 * Implementazione del metodo toString().
		 * 
		 * @return Ritorna il toString() dell'oggetto FaseEstesaOrdinabile
		 */
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer("");
			buffer.append("CFSA = " + this.getCodiceFiscaleSA() + ", ");
			buffer.append("NumeroOrdineFaseFlusso = " + this.getNumeroOrdineFaseFlusso() + ", ");
			buffer.append("TipoInvio = " + this.getTipoInvio() + ", ");
			buffer.append("NumeroProgressivo = " + this.getNumeroProgressivo() + ", ");
			buffer.append("Idegov = " + this.getIdegov() + ", ");
			buffer.append("NomeFile = " + this.getNomeFile());
			return buffer.toString();
		}
	}

}
