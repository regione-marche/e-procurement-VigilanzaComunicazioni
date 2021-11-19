package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.ListaTab51Type;
import it.toscana.rete.rfc.sitat.types.ListaTab52Type;
import it.toscana.rete.rfc.sitat.types.ListaTab53Type;
import it.toscana.rete.rfc.sitat.types.ListaTab54Type;
import it.toscana.rete.rfc.sitat.types.ListaTab55Type;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument;
import it.toscana.rete.rfc.sitat.types.Tab101Type;
import it.toscana.rete.rfc.sitat.types.Tab184Type;
import it.toscana.rete.rfc.sitat.types.Tab41Type;
import it.toscana.rete.rfc.sitat.types.Tab4Type;
import it.toscana.rete.rfc.sitat.types.Tab51Type;
import it.toscana.rete.rfc.sitat.types.Tab52Type;
import it.toscana.rete.rfc.sitat.types.Tab53Type;
import it.toscana.rete.rfc.sitat.types.Tab54Type;
import it.toscana.rete.rfc.sitat.types.Tab55Type;
import it.toscana.rete.rfc.sitat.types.Tab5Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'anagrafica lotto e/o gara.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class AnagraficaLottoGaraHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(AnagraficaLottoGaraHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Anagrafica gara/lotti";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_ANAGRAFICA_LOTTO_GARA;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc = (RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc = (RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc = (RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument) documento;
		Tab4Type tab4 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab4();

		if (UtilitySITAT.existsGara(tab4.getW3IDGARA(), this.sqlManager)) {
			// Se la gara esiste gia' nella base dati, allora si controlla che
			// sia una
			// anagrafica semplificata. Se lo e', si agisce come
			// rettifica/integrazione,
			// altrimenti si da errore perche' gara gia' esistente

			Long codGara = UtilitySITAT.getCodGaraByIdAvGara(tab4.getW3IDGARA(), this.sqlManager);

			if (UtilitySITAT.existsFlussoAnagrafica(codGara, CostantiW9.ANAGRAFICA_GARA_LOTTI, this.sqlManager)) {

				logger.error("La richiesta di primo invio ha un id (IDAVGARA=" + tab4.getW3IDGARA() + ") di una anagrafica gara esistente in DB");

				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una anagrafica gara gia' esistente in banca dati");
				return;
			}
		}

		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().sizeOfTab5TypeArray(); i++) {
			Tab5Type tab5 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().getTab5TypeArray(i);
			if (UtilitySITAT.existsLotto(tab5.getW3CIG(), this.sqlManager)) {
				logger.error("Il lotto " + (i + 1) + " della richiesta ha un codice cig (" + tab5.getW3CIG() + ") gia' presente nella base dati.");
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo Invio di anagrafica lotto gara con lotto non valido (codice cig gia' presente)" + tab5.getW3CIG());
				return;
			}
		}

		this.insertDatiFlusso(doc, datiAggiornamento, true, null, null, new Long(0));

	}

	/**
	 * Inserimento dell'anagrafica gara e lotti.
	 * 
	 * @param doc
	 * @param datiAggiornamento
	 * @param primoInvio
	 * @param codGaraDeleted
	 * @param hmCig
	 * @throws SQLException
	 * @throws GestoreException
	 */
	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc, DataColumnContainer datiAggiornamento, boolean primoInvio, Long codGaraDeleted,
			HashMap<String, HashMap<String, Object>> hmCig, Long maxCodLott) throws SQLException, GestoreException {

		// Gare
		Tab4Type tab4 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab4();

		if (tab4 != null) {
			if (tab4.getArch1() != null) {
				String pkUffint = this.getStazioneAppaltante(tab4.getArch1());

				Long pkIdcc = null;
				if (tab4.isSetArch5()) {
					pkIdcc = this.getCentroCosto(tab4.getArch5(), pkUffint);
				}

				Long pkIduff = null;
				if (tab4.isSetArch6()) {
					pkIduff = this.getUfficio(tab4.getArch6(), pkUffint);
				}

				Long codGara = new Long(this.genChiaviManager.getMaxId("W9GARA", "CODGARA") + 1);
				if (!primoInvio) {
					codGara = codGaraDeleted;
				}

				String codrup = this.insertDatiGara(doc, datiAggiornamento, primoInvio, tab4, pkUffint, pkIdcc, codGara, pkIduff);

				this.insertDatiLotto(doc, primoInvio, hmCig, pkUffint, codGara, codrup, maxCodLott);
			}
		}
		this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
	}

	/**
	 * Inserimento dei dati dell'anagrafica di gara (W9GARA, W9DOCGARA, W9PUBB
	 * 
	 * @param doc
	 * @param datiAggiornamento
	 * @param primoInvio
	 * @param tab4
	 * @param pkUffint
	 * @param pkIdcc
	 * @param codGara
	 * @param pkIduff
	 * @return
	 * @throws GestoreException
	 * @throws SQLException
	 */
	private String insertDatiGara(RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc, DataColumnContainer datiAggiornamento, boolean primoInvio, Tab4Type tab4, String pkUffint,
			Long pkIdcc, Long codGara, Long pkIduff) throws GestoreException, SQLException {
		datiAggiornamento.addColumn("W9GARA.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
		if (tab4.getW3OGGETTO1() != null) {
			datiAggiornamento.addColumn("W9GARA.OGGETTO", JdbcParametro.TIPO_TESTO, tab4.getW3OGGETTO1());
		}
		// *** Gestione del codice per IDAVGARA ***
		if (tab4.getW3IDGARA() != null) {
			datiAggiornamento.addColumn("W9GARA.IDAVGARA", JdbcParametro.TIPO_TESTO, tab4.getW3IDGARA());
		}
		if (tab4.isSetW3IGARA()) {
			datiAggiornamento.addColumn("W9GARA.IMPORTO_GARA", JdbcParametro.TIPO_DECIMALE, tab4.getW3IGARA());
		}
		if (tab4.getW3NLOTTI() != 0) {
			datiAggiornamento.addColumn("W9GARA.NLOTTI", JdbcParametro.TIPO_NUMERICO, new Long(tab4.getW3NLOTTI()));
		}
		if (tab4.getW9GAFLAGENT() != null) {
			datiAggiornamento.addColumn("W9GARA.FLAG_ENTE_SPECIALE", JdbcParametro.TIPO_TESTO, tab4.getW9GAFLAGENT().toString());
		}
		if (tab4.isSetW9GAMODIND()) {
			datiAggiornamento.addColumn("W9GARA.ID_MODO_INDIZIONE", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab4.getW9GAMODIND().toString()));
		}
		if (tab4.getW3TIPOAPP() != null) {
			datiAggiornamento.addColumn("W9GARA.TIPO_APP", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab4.getW3TIPOAPP().toString()));
		}
		datiAggiornamento.addColumn("W9GARA.CODEIN", JdbcParametro.TIPO_TESTO, pkUffint);
		if (tab4.getW3FLAGSA() != null) {
			datiAggiornamento.addColumn("W9GARA.FLAG_SA_AGENTE", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab4.getW3FLAGSA()).toString());
		}
		if (tab4.isSetW3IDTIPOL()) {
			datiAggiornamento.addColumn("W9GARA.ID_TIPOLOGIA_SA", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab4.getW3IDTIPOL().toString()));
		}
		if (tab4.isSetW3GASAAGENTE()) {
			datiAggiornamento.addColumn("W9GARA.DENOM_SA_AGENTE", JdbcParametro.TIPO_TESTO, tab4.getW3GASAAGENTE());
		}
		if (tab4.isSetW3GACFAGENTE()) {
			datiAggiornamento.addColumn("W9GARA.CF_SA_AGENTE", JdbcParametro.TIPO_TESTO, tab4.getW3GACFAGENTE());
		}

		if (tab4.isSetW9GATIPROC()) {
			datiAggiornamento.addColumn("W9GARA.TIPOLOGIA_PROCEDURA", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab4.getW9GATIPROC().toString()));
		}
		if (tab4.isSetW9GASTIPULA()) {
			datiAggiornamento.addColumn("W9GARA.FLAG_CENTRALE_STIPULA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab4.getW9GASTIPULA()).toString());
		}
		if (tab4.isSetW9GACIGAQ()) {
			datiAggiornamento.addColumn("W9GARA.CIG_ACCQUADRO", JdbcParametro.TIPO_TESTO, tab4.getW9GACIGAQ().toString());
		}
		if (tab4.isSetW3DGURI()) {
			datiAggiornamento.addColumn("W9GARA.DGURI", JdbcParametro.TIPO_DATA, tab4.getW3DGURI().getTime());
		}
		if (tab4.isSetW3DSCADB()) {
			datiAggiornamento.addColumn("W9GARA.DSCADE", JdbcParametro.TIPO_DATA, tab4.getW3DSCADB().getTime());
		}
		if (tab4.getW9GARICALLUV() != null) {
			datiAggiornamento.addColumn("W9GARA.RIC_ALLUV", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab4.getW9GARICALLUV()).toString());
		}

		if (tab4.isSetW9GACAM()) {
			datiAggiornamento.addColumn("W9GARA.CAM", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab4.getW9GACAM()).toString());
		}

		if (tab4.isSetW9GASISMA()) {
			datiAggiornamento.addColumn("W9GARA.SISMA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab4.getW9GASISMA()).toString());
		}

		if (tab4.isSetW9GAINDSEDE()) {
			datiAggiornamento.addColumn("W9GARA.INDSEDE", JdbcParametro.TIPO_TESTO, tab4.getW9GAINDSEDE().toString());
		}

		if (tab4.isSetW9GACOMSEDE()) {
			datiAggiornamento.addColumn("W9GARA.COMSEDE", JdbcParametro.TIPO_TESTO, tab4.getW9GACOMSEDE().toString());
		}

		if (tab4.isSetW9GAPROSEDE()) {
			datiAggiornamento.addColumn("W9GARA.PROSEDE", JdbcParametro.TIPO_TESTO, tab4.getW9GAPROSEDE().toString());
		}

		if (tab4.isSetArch5()) {
			datiAggiornamento.addColumn("W9GARA.IDCC", JdbcParametro.TIPO_NUMERICO, pkIdcc);
		}

		if (tab4.isSetArch6()) {
			datiAggiornamento.addColumn("W9GARA.IDUFFICIO", JdbcParametro.TIPO_NUMERICO, pkIduff);
		}

		if (tab4.isSetW9GABANDO()) {
			datiAggiornamento.addColumn("W9GARA.PREV_BANDO", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab4.getW9GABANDO()).toString());
		}
		if (tab4.isSetW9GASITUAZ()) {
			datiAggiornamento.addColumn("W9GARA.SITUAZIONE", JdbcParametro.TIPO_NUMERICO, new Long(tab4.getW9GASITUAZ().toString()));
		} else {
			datiAggiornamento.addColumn("W9GARA.SITUAZIONE", JdbcParametro.TIPO_NUMERICO, new Long(1));
		}

		Tab5Type tab5 = null;
		String codrup = "";
		if (doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().sizeOfTab5TypeArray() > 0) {
			tab5 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().getTab5TypeArray(0);
			codrup = this.getTecnico(tab5.getArch2(), pkUffint);
			datiAggiornamento.addColumn("W9GARA.RUP", JdbcParametro.TIPO_TESTO, codrup);
		}

		// Inserimento IDEGOV
		if (datiAggiornamento.isColumn("W9INBOX.IDEGOV")) {
			datiAggiornamento.addColumn("W9GARA.IDEGOV", JdbcParametro.TIPO_TESTO, datiAggiornamento.getString("W9INBOX.IDEGOV"));
		}

		datiAggiornamento.insert("W9GARA", this.sqlManager);

		// Importazione in W9PUBB (dati di pubblicita' dell'appalto)
		if (doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().isSetTab184()) {
			Tab184Type tab184 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab184();

			DataColumnContainer dccPubblicazioni = new DataColumnContainer(new DataColumn[] { new DataColumn("W9PUBB.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9PUBB.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1))),
					new DataColumn("W9PUBB.NUM_APPA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1))),
					new DataColumn("W9PUBB.NUM_PUBB", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1))) });

			if (tab184 != null) {
				if (tab184.isSetW3GUCE1()) {
					dccPubblicazioni.addColumn("W9PUBB.DATA_GUCE", JdbcParametro.TIPO_DATA, tab184.getW3GUCE1().getTime());
				}
				if (tab184.isSetW3GURI1()) {
					dccPubblicazioni.addColumn("W9PUBB.DATA_GURI", JdbcParametro.TIPO_DATA, tab184.getW3GURI1().getTime());
				}
				if (tab184.isSetW3ALBO1()) {
					dccPubblicazioni.addColumn("W9PUBB.DATA_ALBO", JdbcParametro.TIPO_DATA, tab184.getW3ALBO1().getTime());
				}
				if (tab184.isSetW3NAZ1()) {
					dccPubblicazioni.addColumn("W9PUBB.QUOTIDIANI_NAZ", JdbcParametro.TIPO_NUMERICO, new Long(tab184.getW3NAZ1()));
				}
				if (tab184.isSetW3REG1()) {
					dccPubblicazioni.addColumn("W9PUBB.QUOTIDIANI_REG", JdbcParametro.TIPO_NUMERICO, new Long(tab184.getW3REG1()));
				}
				// S.C. 16/03/2011 Gli attributi W3PROFILO1, W3MIN1 e W3OSS1
				// sono diventati obbligatori
				if (tab184.getW3PROFILO1() != null) {
					dccPubblicazioni.addColumn("W9PUBB.PROFILO_COMMITTENTE", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab184.getW3PROFILO1()).toString());
				}
				if (tab184.getW3MIN1() != null) {
					dccPubblicazioni.addColumn("W9PUBB.SITO_MINISTERO_INF_TRASP", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab184.getW3MIN1()).toString());
				}
				if (tab184.getW3OSS1() != null) {
					dccPubblicazioni.addColumn("W9PUBB.SITO_OSSERVATORIO_CP", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab184.getW3OSS1()).toString());
				}
				if (tab184.isSetW3BORE()) {
					dccPubblicazioni.addColumn("W9PUBB.DATA_BORE", JdbcParametro.TIPO_DATA, tab184.getW3BORE().getTime());
				}
				if (tab184.isSetW3PERIODIC()) {
					dccPubblicazioni.addColumn("W9PUBB.PERIODICI", JdbcParametro.TIPO_NUMERICO, new Long(tab184.getW3PERIODIC()));
				}
			}
			// si richiama l'inserimento dell'occorrenza di tab184 (W9PUBB)
			dccPubblicazioni.insert("W9PUBB", this.sqlManager);
		}

		// Importazione in W9DOCGARA (documento PDF allegati)
		// Se presente oggetto listaTab41Array contiene oggetti, allora si
		// importano
		// i documenti del bando di gara (gestione prima dell'introduzione delle
		// pubblicazioni)
		if (doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().sizeOfListaTab41Array() > 0) {
			Tab41Type[] listaTab41 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab41Array();

			for (int iu = 0; iu < listaTab41.length; iu++) {
				Tab41Type tab41 = listaTab41[iu];

				datiAggiornamento.addColumn("W9DOCGARA.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
				datiAggiornamento.addColumn("W9DOCGARA.NUMDOC", JdbcParametro.TIPO_NUMERICO, new Long(iu + 1));
				if (tab41 != null) {
					if (tab41.getW9DGTITOLO() != null) {
						datiAggiornamento.addColumn("W9DOCGARA.TITOLO", JdbcParametro.TIPO_TESTO, tab41.getW9DGTITOLO());
					}

					datiAggiornamento.addColumn("W9DOCGARA.NUM_PUBB", JdbcParametro.TIPO_NUMERICO, new Long(1));

					if (tab41.isSetW9DGURL()) {
						datiAggiornamento.addColumn("W9DOCGARA.URL", JdbcParametro.TIPO_TESTO, tab41.getW9DGURL());
					}

					if (tab41.isSetFile()) {
						ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
						try {
							fileAllegato.write(tab41.getFile());
						} catch (IOException e) {
							throw new GestoreException("Errore durante la lettura del file allegato presente nella richiesta XML", null, e);
						}
						datiAggiornamento.addColumn("W9DOCGARA.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, fileAllegato);
					}
				}
				datiAggiornamento.insert("W9DOCGARA", this.sqlManager);
			}
		}

		return codrup;
	}

	/**
	 * Inserimento dei dell'anagrafica del lotto (W9LOTT, W9APPAFORN, W9APPALAV,
	 * W9COND, W9CPV, W9LOTTCATE, W9INCA.
	 * 
	 * @param doc
	 * @param primoInvio
	 * @param hmCig
	 * @param pkUffint
	 * @param codGara
	 * @param codrup
	 * @param maxCodLott
	 * @throws SQLException
	 * @throws GestoreException
	 */
	private void insertDatiLotto(RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument doc, boolean primoInvio, HashMap<String, HashMap<String, Object>> hmCig, String pkUffint, Long codGara,
			String codrup, Long maxCodLott) throws SQLException, GestoreException {

		// Lotti
		Tab5Type tab5 = null;

		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().sizeOfTab5TypeArray(); i++) {
			tab5 = doc.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getListaTab5().getTab5TypeArray(i);

			Long codLott = null;
			String codiceCUI = null;
			String idSchedaSimog = null;
			String idSchedaLocale = null;
			if (tab5 != null) {
				String cig = tab5.getW3CIG();
				if (hmCig != null && hmCig.containsKey(cig)) {
					HashMap<String, Object> hmObjects = hmCig.get(cig);

					codLott = (Long) hmObjects.get("CODLOTT");

					if (hmObjects.containsKey("CODCUI")) {
						codiceCUI = (String) hmObjects.get("CODCUI");
					}

					if (hmObjects.containsKey("ID_SCHEDA_SIMOG")) {
						idSchedaSimog = (String) hmObjects.get("ID_SCHEDA_SIMOG");
					}

					if (hmObjects.containsKey("ID_SCHEDA_LOCALE")) {
						idSchedaLocale = (String) hmObjects.get("ID_SCHEDA_LOCALE");
					}
				} else {
					maxCodLott++;
					codLott = maxCodLott;
				}

				DataColumnContainer dccLotto = new DataColumnContainer(new DataColumn[] { new DataColumn("W9LOTT.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9LOTT.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)) });

				if (tab5.getW3OGGETTO2() != null) {
					dccLotto.addColumn("W9LOTT.OGGETTO", JdbcParametro.TIPO_TESTO, tab5.getW3OGGETTO2());
				}
				if (tab5.getW3SOMMAUR() != null) {
					dccLotto.addColumn("W9LOTT.SOMMA_URGENZA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab5.getW3SOMMAUR()).toString());
				}
				dccLotto.addColumn("W9LOTT.IMPORTO_LOTTO", JdbcParametro.TIPO_DECIMALE, tab5.getW3ILOTTO());
				if (tab5.getW3CPV() != null) {
					dccLotto.addColumn("W9LOTT.CPV", JdbcParametro.TIPO_TESTO, tab5.getW3CPV());
				}
				if (tab5.getW3IDSCEL2() != null) {
					dccLotto.addColumn("W9LOTT.ID_SCELTA_CONTRAENTE", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab5.getW3IDSCEL2().toString()));
				}
				if (tab5.isSetW9IDSCEL50()) {
					dccLotto.addColumn("W9LOTT.ID_SCELTA_CONTRAENTE_50", JdbcParametro.TIPO_NUMERICO, new Long(tab5.getW9IDSCEL50()));
				}
				if (tab5.getW3IDCATE4() != null) {
					dccLotto.addColumn("W9LOTT.ID_CATEGORIA_PREVALENTE", JdbcParametro.TIPO_TESTO, tab5.getW3IDCATE4().toString());
				}
				dccLotto.addColumn("W9LOTT.NLOTTO", JdbcParametro.TIPO_NUMERICO, new Long(tab5.getW3NLOTTO()));
				if (tab5.getW3MANOLO() != null) {
					dccLotto.addColumn("W9LOTT.MANOD", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab5.getW3MANOLO()).toString());
				}
				if (tab5.isSetW3CLASCAT()) {
					dccLotto.addColumn("W9LOTT.CLASCAT", JdbcParametro.TIPO_TESTO, tab5.getW3CLASCAT().toString());
				}
				if (tab5.getW3COMCON() != null) {
					dccLotto.addColumn("W9LOTT.COMCON", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab5.getW3COMCON()).toString());
				}
				if (tab5.isSetW3DESCOM() && !tab5.getW3DESCOM().toString().equals("")) {
					dccLotto.addColumn("W9LOTT.DESCOM", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab5.getW3DESCOM().toString()));
				}
				if (tab5.isSetT2IDCONINT()) {
					dccLotto.addColumn("W9LOTT.CODINT", JdbcParametro.TIPO_TESTO, tab5.getT2IDCONINT());
				}
				if (tab5.isSetW3CIGCOM()) {
					dccLotto.addColumn("W9LOTT.CIGCOM", JdbcParametro.TIPO_TESTO, tab5.getW3CIGCOM());
				}
				if (tab5.getW3TIPOCON() != null) {
					dccLotto.addColumn("W9LOTT.TIPO_CONTRATTO", JdbcParametro.TIPO_TESTO, tab5.getW3TIPOCON().toString());
				}
				if (tab5.getW3FLAGENT() != null) {
					dccLotto.addColumn("W9LOTT.FLAG_ENTE_SPECIALE", JdbcParametro.TIPO_TESTO, tab5.getW3FLAGENT().toString());
				}
				if (tab5.isSetW3MODGAR()) {
					dccLotto.addColumn("W9LOTT.ID_MODO_GARA", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab5.getW3MODGAR().toString()));
				}
				if (tab5.isSetW3LUOGOIS()) {
					dccLotto.addColumn("W9LOTT.LUOGO_ISTAT", JdbcParametro.TIPO_TESTO, tab5.getW3LUOGOIS());
				}
				if (tab5.isSetW3LUOGONU()) {
					dccLotto.addColumn("W9LOTT.LUOGO_NUTS", JdbcParametro.TIPO_TESTO, tab5.getW3LUOGONU());
				}
				if (tab5.isSetW9DPUBBLICAZ()) {
					dccLotto.addColumn("W9LOTT.DATA_PUBBLICAZIONE", JdbcParametro.TIPO_DATA, tab5.getW9DPUBBLICAZ().getTime());
				}
				if (tab5.isSetW9EXSOTTOSOGLIA()) {
					dccLotto.addColumn("W9LOTT.EXSOTTOSOGLIA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab5.getW9EXSOTTOSOGLIA()).toString());
				}
				if (tab5.getW3IDTIPO() != null) {
					dccLotto.addColumn("W9LOTT.ID_TIPO_PRESTAZIONE", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab5.getW3IDTIPO().toString()));
				}
				if (tab5.getW3CIG() != null) {
					// *** Gestione del CIG ***
					dccLotto.addColumn("W9LOTT.CIG", JdbcParametro.TIPO_TESTO, tab5.getW3CIG());
				}
				dccLotto.addColumn("W9LOTT.IMPORTO_ATTUAZIONE_SICUREZZA", JdbcParametro.TIPO_DECIMALE, tab5.getW3IATTSIC());
				if (tab5.isSetW3CUP()) {
					dccLotto.addColumn("W9LOTT.CUP", JdbcParametro.TIPO_TESTO, tab5.getW3CUP());
				}
				if (tab5.getW3LOARTE1() != null) {
					dccLotto.addColumn("W9LOTT.ART_E1", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab5.getW3LOARTE1()).toString());
				}
				if (tab5.getW3LOARTE2() != null) {
					dccLotto.addColumn("W9LOTT.ART_E2", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab5.getW3LOARTE2()).toString());
				}
				// Codice CUI
				if (codiceCUI != null) {
					dccLotto.addColumn("W9LOTT.CODCUI", JdbcParametro.TIPO_TESTO, codiceCUI);
				}
				// CUIINT
	      if (tab5.isSetW9LOCUIINT()) {
	        dccLotto.addColumn("W9LOTT.CUIINT", JdbcParametro.TIPO_TESTO,
	            tab5.getW9LOCUIINT());
	      }
	      // URL_EPROC
	      if (tab5.isSetW9LOURLEPROC()) {
	        dccLotto.addColumn("W9LOTT.URL_EPROC", JdbcParametro.TIPO_TESTO,
	            tab5.getW9LOURLEPROC());
	      }
				
				// ID_SCHEDA_LOCALE
				if (idSchedaLocale != null) {
					dccLotto.addColumn("W9LOTT.ID_SCHEDA_LOCALE", JdbcParametro.TIPO_TESTO, idSchedaLocale);
				} else {
					dccLotto.addColumn("W9LOTT.ID_SCHEDA_LOCALE", JdbcParametro.TIPO_TESTO, tab5.getW3CIG());
				}

				// ID_SCHEDA_SIMOG
				if (idSchedaSimog != null) {
					dccLotto.addColumn("W9LOTT.ID_SCHEDA_SIMOG", JdbcParametro.TIPO_TESTO, idSchedaSimog);
				}

				if (tab5.isSetW9LODAEXP()) {
					dccLotto.addColumn("W9LOTT.DAEXPORT", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab5.getW9LODAEXP()).toString());
				}

				double importoTot = 0;
				if (tab5.getW3IATTSIC() > 0) {
					importoTot += tab5.getW3IATTSIC();
				}
				if (tab5.getW3ILOTTO() > 0) {
					importoTot += tab5.getW3ILOTTO();
				}
				dccLotto.addColumn("W9LOTT.IMPORTO_TOT", JdbcParametro.TIPO_DECIMALE, importoTot);
				if (codrup == null || codrup.trim().equals("")) {
					codrup = this.getTecnico(tab5.getArch2(), pkUffint);
				}
				dccLotto.addColumn("W9LOTT.RUP", JdbcParametro.TIPO_TESTO, codrup);

				if (tab5.isSetW9LOSITUAZ()) {
					dccLotto.addColumn("W9LOTT.SITUAZIONE", JdbcParametro.TIPO_NUMERICO, new Long(tab5.getW9LOSITUAZ().toString()));
				}
				dccLotto.insert("W9LOTT", this.sqlManager);

				if (tab5.isSetListaTab51()) {
					ListaTab51Type listaTab51 = tab5.getListaTab51();
					Tab51Type tab51 = null;
					for (int j = 0; j < listaTab51.sizeOfTab51Array(); j++) {
						tab51 = listaTab51.getTab51Array(j);
						DataColumnContainer dccAppaForn = new DataColumnContainer(new DataColumn[] { new DataColumn("W9APPAFORN.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
								new DataColumn("W9APPAFORN.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
								new DataColumn("W9APPAFORN.NUM_APPAF", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, j + 1)) });
						if (tab51.getW3IDAPP04() != null) {
							dccAppaForn.addColumn("W9APPAFORN.ID_APPALTO", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab51.getW3IDAPP04().toString()));
						}
						dccAppaForn.insert("W9APPAFORN", this.sqlManager);
					}
				}

				if (tab5.isSetListaTab52()) {
					ListaTab52Type listaTab52 = tab5.getListaTab52();
					Tab52Type tab52 = null;
					for (int j = 0; j < listaTab52.sizeOfTab52Array(); j++) {
						tab52 = listaTab52.getTab52Array(j);
						DataColumnContainer dccAppaLav = new DataColumnContainer(new DataColumn[] { new DataColumn("W9APPALAV.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
								new DataColumn("W9APPALAV.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
								new DataColumn("W9APPALAV.NUM_APPAL", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, j + 1)) });
						if (tab52.getW3IDAPP05() != null) {
							dccAppaLav.addColumn("W9APPALAV.ID_APPALTO", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab52.getW3IDAPP05().toString()));
						}
						dccAppaLav.insert("W9APPALAV", this.sqlManager);
					}
				}

				if (tab5.isSetListaTab53()) {
					ListaTab53Type listaTab53 = tab5.getListaTab53();
					Tab53Type tab53 = null;
					for (int j = 0; j < listaTab53.sizeOfTab53Array(); j++) {
						tab53 = listaTab53.getTab53Array(j);
						DataColumnContainer dccCond = new DataColumnContainer(new DataColumn[] { new DataColumn("W9COND.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
								new DataColumn("W9COND.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
								new DataColumn("W9COND.NUM_COND", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, j + 1)) });
						if (tab53.getW3IDCONDI() != null) {
							dccCond.addColumn("W9COND.ID_CONDIZIONE", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab53.getW3IDCONDI().toString()));
						}
						dccCond.insert("W9COND", this.sqlManager);
					}
				}

				if (tab5.isSetListaTab54()) {
					ListaTab54Type listaTab54 = tab5.getListaTab54();
					Tab54Type tab54 = null;
					for (int j = 0; j < listaTab54.sizeOfTab54Array(); j++) {
						tab54 = listaTab54.getTab54Array(j);
						DataColumnContainer dccCpv = new DataColumnContainer(new DataColumn[] { new DataColumn("W9CPV.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
								new DataColumn("W9CPV.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
								new DataColumn("W9CPV.NUM_CPV", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, j + 1)) });
						if (tab54.getW3CPVCOMP() != null) {
							dccCpv.addColumn("W9CPV.CPV", JdbcParametro.TIPO_TESTO, tab54.getW3CPVCOMP());
						}
						dccCpv.insert("W9CPV", this.sqlManager);
					}
				}

				if (tab5.isSetListaTab55()) {
					ListaTab55Type listaTab55 = tab5.getListaTab55();
					Tab55Type tab55 = null;
					for (int j = 0; j < listaTab55.sizeOfTab55Array(); j++) {
						tab55 = listaTab55.getTab55Array(j);
						DataColumnContainer dccLottCate = new DataColumnContainer(new DataColumn[] { new DataColumn("W9LOTTCATE.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
								new DataColumn("W9LOTTCATE.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
								new DataColumn("W9LOTTCATE.NUM_CATE", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, j + 1)) });
						if (tab55.getW3CATEGORI() != null) {
							dccLottCate.addColumn("W9LOTTCATE.CATEGORIA", JdbcParametro.TIPO_TESTO, tab55.getW3CATEGORI().toString());
						}
						if (tab55.getW3CLASCATCA() != null) {
							dccLottCate.addColumn("W9LOTTCATE.CLASCAT", JdbcParametro.TIPO_TESTO, tab55.getW3CLASCATCA().toString());
						}
						if (tab55.getW9LCSCORPORA() != null) {
							dccLottCate.addColumn("W9LOTTCATE.SCORPORABILE", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab55.getW9LCSCORPORA()));
						}
						if (tab55.getW9LCSUBAPPAL() != null) {
							dccLottCate.addColumn("W9LOTTCATE.SUBAPPALTABILE", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab55.getW9LCSUBAPPAL()));
						}
						dccLottCate.insert("W9LOTTCATE", this.sqlManager);
					}
				}

				if (tab5.getTab56Array() != null && tab5.getTab56Array().length > 0) {
					for (int aio = 0; aio < tab5.getTab56Array().length; aio++) {
						Tab101Type tab101 = tab5.getTab56Array(aio);

						// Arch2Type arch2 = tab101.getArch2();
						String pkTecni = this.getTecnico(tab101.getArch2(), pkUffint);
						DataColumnContainer datiInca = new DataColumnContainer(new DataColumn[] { new DataColumn("W9INCA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
								new DataColumn("W9INCA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)) });
						datiInca.addColumn("W9INCA.NUM", JdbcParametro.TIPO_NUMERICO, new Long(1));
						datiInca.addColumn("W9INCA.NUM_INCA", JdbcParametro.TIPO_NUMERICO, new Long(aio));
						datiInca.addColumn("W9INCA.SEZIONE", JdbcParametro.TIPO_TESTO, "RA");
						datiInca.addColumn("W9INCA.CODTEC", JdbcParametro.TIPO_TESTO, pkTecni);

						datiInca.addColumn("W9INCA.ID_RUOLO", JdbcParametro.TIPO_NUMERICO, new Long(tab101.getW3IDRUOLO().toString()));
						if (tab101.isSetW3CIGPROG()) {
							datiInca.addColumn("W9INCA.CIG_PROG_ESTERNA", JdbcParametro.TIPO_TESTO, tab101.getW3CIGPROG());
						}
						if (tab101.isSetW3DATAAFF()) {
							datiInca.addColumn("W9INCA.DATA_AFF_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab101.getW3DATAAFF().getTime());
						}
						if (tab101.isSetW3DATACON()) {
							datiInca.addColumn("W9INCA.DATA_CONS_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab101.getW3DATACON().getTime());
						}

						datiInca.insert("W9INCA", this.sqlManager);
					}
				}
				if (hmCig != null && hmCig.containsKey(cig)) {
					hmCig.remove(cig);
				}
			}
		}
	}

	/**
	 * In caso di aggiornamento dei lotti (e quindi in caso di cancellazione e
	 * reinserimento dei lotti), si salvano alcuni campi dei lotti inserendoli
	 * in una mappa, la cui chiave e' il codice CIG di ciascun lotto. I campi
	 * del lotto salvati sono: CODLOTT, CIG, CODCUI, ID_SCHEDA_SIMOG e
	 * ID_SCHEDA_LOCALE.
	 * 
	 * @param codgara
	 * @return Ritorna una
	 * @throws SQLException
	 */
	private HashMap<String, HashMap<String, Object>> backupDatiLotti(Long codgara) throws SQLException {

		List<?> listaCodLottCigCodCUI = this.sqlManager.getListVector("select CODLOTT, CIG, CODCUI, ID_SCHEDA_SIMOG, ID_SCHEDA_LOCALE " + "from W9LOTT where CODGARA=? ORDER by CODLOTT ASC",
				new Object[] { codgara });

		HashMap<String, HashMap<String, Object>> hmCig = new HashMap<String, HashMap<String, Object>>();
		for (int y = 0; y < listaCodLottCigCodCUI.size(); y++) {
			Vector<?> vector = (Vector<?>) listaCodLottCigCodCUI.get(y);

			Long codLott = (Long) ((JdbcParametro) vector.get(0)).getValue();
			String codiceCig = (String) ((JdbcParametro) vector.get(1)).getValue();
			String codiceCUI = (String) ((JdbcParametro) vector.get(2)).getValue();
			String idSchedaSimog = (String) ((JdbcParametro) vector.get(3)).getValue();
			String idSchedaLocale = (String) ((JdbcParametro) vector.get(4)).getValue();

			HashMap<String, Object> hmObjects = new HashMap<String, Object>();
			hmObjects.put("CODLOTT", codLott);

			if (StringUtils.isNotEmpty(codiceCUI)) {
				hmObjects.put("CODCUI", codiceCUI);
			}

			if (StringUtils.isNotEmpty(idSchedaSimog)) {
				hmObjects.put("ID_SCHEDA_SIMOG", idSchedaSimog);
			}

			if (StringUtils.isNotEmpty(idSchedaLocale)) {
				hmObjects.put("ID_SCHEDA_LOCALE", idSchedaLocale);
			}
			hmCig.put(codiceCig, hmObjects);
		}
		return hmCig;
	}

	/**
	 * Metodo per la gestione dell'importazione dei dati di anagrafica di gara e
	 * del lotto per la funzionalita' 'Importa dati da OR'.
	 * 
	 * @param operazioneGara
	 * @param operazioneLotto
	 * @param anagraficaGaraLotto
	 * @param cfrup
	 *            Codice fiscale del RUP
	 * @param cfsa
	 *            Codice fiscale della S.A.
	 * @throws SQLException
	 * @throws GestoreException
	 */
	public void gestioneAnagraficaGaraLottoDaOR(String operazioneGara, String operazioneLotto, RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument anagraficaGaraLotto, String cfrup,
			String cfsa) throws SQLException, GestoreException {

		Long codGara = UtilitySITAT.getCodGaraByIdAvGara(anagraficaGaraLotto.getRichiestaRichiestaRispostaSincronaAnagraficaLottoGara().getTab4().getW3IDGARA(), this.sqlManager);

		DataColumnContainer datiAggiornamento = new DataColumnContainer(new DataColumn[] { new DataColumn("W9LOTT.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)) });

		HashMap<String, HashMap<String, Object>> hmCig = this.backupDatiLotti(codGara);

		if (StringUtils.isNotEmpty(operazioneGara)) {
			if ("importa".equals(operazioneGara)) {
				this.insertDatiFlusso(anagraficaGaraLotto, datiAggiornamento, true, codGara, hmCig, new Long(0));
			}

		} else {
			String pkUffint = this.getCodiceUfficioIntestatario(cfsa);
			String codrup = (String) this.sqlManager.getObject("select CODTEC from TECNI where CFTEC=? and CGENTEI=?", new Object[] { cfrup, pkUffint });

			if ("importa".equals(operazioneLotto)) {
				this.insertDatiLotto(anagraficaGaraLotto, true, hmCig, pkUffint, codGara, codrup, new Long(0));
			}

			if ("sovrascrivi".equals(operazioneLotto)) {
				Long maxCodLott = (Long) this.sqlManager.getObject("select max(CODLOTT) from W9LOTT where CODGARA=?", new Object[] { codGara });

				this.insertDatiLotto(anagraficaGaraLotto, true, hmCig, pkUffint, codGara, codrup, maxCodLott);
			}
		}
	}

	@Override
	protected void manageRettifica(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		// TODO Auto-generated method stub

	}

}