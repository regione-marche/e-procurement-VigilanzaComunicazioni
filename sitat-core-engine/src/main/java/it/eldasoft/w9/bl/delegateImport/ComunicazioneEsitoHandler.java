package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.common.CostantiW9;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument;
import it.toscana.rete.rfc.sitat.types.Tab192BisType;
import it.toscana.rete.rfc.sitat.types.Tab30Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della comunicazione dell'esito di aggiudicazione
 * 
 * @author Luca.Giacomazzo
 */
public class ComunicazioneEsitoHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(ComunicazioneEsitoHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Comunicazione esito";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_COMUNICAZIONE_ESITO;
	}

	@Override
	protected XmlObject getXMLDocument(final String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(final XmlObject documento) {
		RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument doc = (RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaComunicazioneEsito().getTest();
	}

	@Override
	protected FaseType getFaseInvio(final XmlObject documento) {
		RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument doc = (RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaComunicazioneEsito().getFase();
	}

	@Override
	protected void managePrimoInvio(final XmlObject documento, final DataColumnContainer datiAggiornamento, final boolean ignoreWarnings) throws GestoreException, SQLException {

		RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument doc = (RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaComunicazioneEsito().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB.");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una Comunicazione esito con lotto non esistente in banca dati");
			return;
		}

		if (existsFase(fase.getW3FACIG(), CostantiW9.COMUNICAZIONE_ESITO)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto con fase comunicazione esito gia' presente nel DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una Comunicazione esito per questo lotto, gia' presente in banca dati");
			// si termina con questo errore
			return;
		}

		// SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI
		// SI
		// AGGIORNA IL DB
		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument doc, final DataColumnContainer datiAggiornamento, final boolean primoInvio) throws SQLException,
			GestoreException {

		Tab30Type tab30 = doc.getRichiestaRichiestaRispostaSincronaComunicazioneEsito().getTab30();
		Tab192BisType tab192 = null;
		if (doc.getRichiestaRichiestaRispostaSincronaComunicazioneEsito().isSetTab34()) {
			tab192 = doc.getRichiestaRichiestaRispostaSincronaComunicazioneEsito().getTab34();
		}
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaComunicazioneEsito().getFase();
		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];

			if (tab30 != null) {
				// Se l'esito del lotto e' aggiudicato si invoca il metodo
				// AbstractRequestHandler.updateW9Fasi,
				// altrimenti si invoca la sovrascrittura della stesso metodo
				// ComunicazioneEsitoHandler.updateW9Fasi
				// per settare il campo W9FASI.DAEXPORT='1' ed effettuare poi
				// l'invio di questa informazione al SIMOG
				if ("1".equals(tab30.getW9LOESIPROC().toString())) {
					super.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);
				} else {
					this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);
				}

				DataColumn codiceGara = new DataColumn("W9ESITO.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara));
				DataColumn numeroLotto = new DataColumn("W9ESITO.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott));

				DataColumnContainer dccLotto = new DataColumnContainer(new DataColumn[] { codiceGara, numeroLotto });
				if (tab30.getW9LOESIPROC() != null) {
					dccLotto.addColumn("W9ESITO.ESITO_PROCEDURA", JdbcParametro.TIPO_NUMERICO, new Long(tab30.getW9LOESIPROC().toString()));
				}
				if (tab30.getW3ESDVERB() != null) {
					dccLotto.addColumn("W9ESITO.DATA_VERB_AGGIUDICAZIONE", JdbcParametro.TIPO_DATA, tab30.getW3ESDVERB().getTime());
				}
				if (tab30.isSetW3ESIMPAGGI()) {
					dccLotto.addColumn("W9ESITO.IMPORTO_AGGIUDICAZIONE", JdbcParametro.TIPO_DECIMALE, tab30.getW3ESIMPAGGI());
				}
				if (tab30.isSetFile()) {
					ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
					try {
						fileAllegato.write(tab30.getFile());
					} catch (IOException e) {
						throw new GestoreException("Errore durante la lettura del file allegato presente nella richiesta XML", null, e);
					}
					dccLotto.addColumn("W9ESITO.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, fileAllegato);
				}

				dccLotto.insert("W9ESITO", this.sqlManager);
			}
			if (tab192 != null) {
				Long numeroRecordW9PUES = (Long) this.sqlManager
						.getObject("select count(*) from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=1 and NUM_PUES=1", new Object[] { codGara, codLott });

				DataColumnContainer datiPues = new DataColumnContainer(new DataColumn[] { new DataColumn("W9PUES.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9PUES.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)) });

				datiPues.addColumn("W9PUES.NUM_INIZ", JdbcParametro.TIPO_NUMERICO, new Long(1));
				datiPues.addColumn("W9PUES.NUM_PUES", JdbcParametro.TIPO_NUMERICO, new Long(1));

				datiPues.getColumn("W9PUES.CODGARA").setChiave(true);
				datiPues.getColumn("W9PUES.CODLOTT").setChiave(true);
				datiPues.getColumn("W9PUES.NUM_INIZ").setChiave(true);
				datiPues.getColumn("W9PUES.NUM_PUES").setChiave(true);

				if (tab192.isSetW3GUCE2()) {
					datiPues.addColumn("W9PUES.DATA_GUCE", JdbcParametro.TIPO_DATA, tab192.getW3GUCE2().getTime());
				}
				if (tab192.isSetW3GURI2()) {
					datiPues.addColumn("W9PUES.DATA_GURI", JdbcParametro.TIPO_DATA, tab192.getW3GURI2().getTime());
				}
				if (tab192.isSetW3ALBO2()) {
					datiPues.addColumn("W9PUES.DATA_ALBO", JdbcParametro.TIPO_DATA, tab192.getW3ALBO2().getTime());
				}
				if (tab192.isSetW3NAZ2()) {
					datiPues.addColumn("W9PUES.QUOTIDIANI_NAZ", JdbcParametro.TIPO_NUMERICO, new Long(tab192.getW3NAZ2()));
				}
				if (tab192.isSetW3REG2()) {
					datiPues.addColumn("W9PUES.QUOTIDIANI_REG", JdbcParametro.TIPO_NUMERICO, new Long(tab192.getW3REG2()));
				}
				if (tab192.isSetW3PROFILO2()) {
					datiPues.addColumn("W9PUES.PROFILO_COMMITTENTE", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab192.getW3PROFILO2()).toString());
				}

				if (tab192.isSetW3MIN2()) {
					datiPues.addColumn("W9PUES.SITO_MINISTERO_INF_TRASP", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab192.getW3MIN2()).toString());
				}

				if (tab192.isSetW3OSS2()) {
					datiPues.addColumn("W9PUES.SITO_OSSERVATORIO_CP", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab192.getW3OSS2()).toString());
				}

				if (numeroRecordW9PUES == null || (numeroRecordW9PUES != null && numeroRecordW9PUES.longValue() == 0)) {
					datiPues.insert("W9PUES", this.sqlManager);
				} else {
					datiPues.getColumn("W9PUES.CODGARA").setOriginalValue(new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara));
					datiPues.getColumn("W9PUES.CODLOTT").setOriginalValue(new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott));
					datiPues.getColumn("W9PUES.NUM_INIZ").setOriginalValue(new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
					datiPues.getColumn("W9PUES.NUM_PUES").setOriginalValue(new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
					datiPues.update("W9PUES", this.sqlManager);
				}
			}

			// 27/02/2012: Al contrario degli altri flussi, l'import del flusso
			// Comunicazione esito
			// non deve aggiornare il flag W9LOTT.DAEXPORT ad uno per
			// l'esportazione dati SIMOG
			// this.aggiornaStatoExportLotto(codGara, codLott,
			// LOTTO_DA_ESPORTARE);
		}
		// se la procedura di aggiornamento e' andata a buon fine, si aggiorna
		// lo stato dell'importazione
		this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
	}

	/**
	 * Inserimento dei campi nel record W9FASI, o aggiornamento del campo
	 * DAEXPORT.
	 * 
	 * Metodo di AbstractRequestHandler sovrascritto per personalizzare
	 * l'inserimento/aggiornamento del record in W9FASI per la fase
	 * Comunicazione Esito: infatti solo se l'esito del lotto e' non
	 * aggiudicato, bisogna valorizzare il campo W9FASI.DAEXPORT='1' in modo da
	 * inviare al SIMOG questa informazione nella valorizzazione dei DatiComuni.
	 * 
	 * @param codGara
	 *            codice gara
	 * @param codLott
	 *            codice lotto
	 * @param fase
	 *            fase
	 * @param datiAggiornamento
	 *            contenitore dati da aggiornare
	 * @param primoInvio
	 *            Primo Invio
	 * @throws GestoreException
	 *             GestoreException
	 * @throws SQLException
	 *             SQLException
	 */
	protected void updateW9Fasi(final Long codGara, final Long codLott, final FaseEstesaType fase, final DataColumnContainer datiAggiornamento, final boolean primoInvio) throws GestoreException,
			SQLException {

		// predisposizione dati W9FASI: i dati facoltativi vengono testati
		// con il metodo .isSetXXX per verificare se nel tracciato sono
		// valorizzati
		datiAggiornamento.addColumn("W9FASI.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
		datiAggiornamento.addColumn("W9FASI.CODLOTT", JdbcParametro.TIPO_NUMERICO, codLott);
		datiAggiornamento.addColumn("W9FASI.FASE_ESECUZIONE", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3FASEESEC().toString()));
		datiAggiornamento.addColumn("W9FASI.DAEXPORT", JdbcParametro.TIPO_TESTO, LOTTO_DA_ESPORTARE);

		datiAggiornamento.addColumn("W9FASI.NUM", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()));
		datiAggiornamento.addColumn("W9FASI.ID_SCHEDA_LOCALE", JdbcParametro.TIPO_TESTO, fase.getW3FACIG() + "_" + UtilityStringhe.fillLeft(fase.getW3FASEESEC().toString(), '0', 3) + "_"
				+ UtilityStringhe.fillLeft("" + fase.getW3NUM5(), '0', 3));

		if (primoInvio) {
			datiAggiornamento.insert("W9FASI", this.sqlManager);
		} else {
			this.sqlManager.update("update W9FASI set DAEXPORT=? where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?", new Object[] { LOTTO_DA_ESPORTARE, codGara, codLott,
					new Long(fase.getW3FASEESEC().toString()), new Long(fase.getW3NUM5()) }, 1);
		}
	}

	@Override
	protected void manageRettifica(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		// TODO Auto-generated method stub

	}

}