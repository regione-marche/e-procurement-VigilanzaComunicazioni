package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument;
import it.toscana.rete.rfc.sitat.types.Tab33Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'avanzamento contratto appalto sopra 150000.
 * 
 * @author Luca.Giacomazzo
 */
public class AvanzamentoContrattoSemplificatoHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(AvanzamentoContrattoSemplificatoHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Avanzamento contratto semplificato";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_AVANZAMENTO_SEMPLIFICATO_CONTRATTO_APPALTO;
	}

	@Override
	protected XmlObject getXMLDocument(final String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(final XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument doc = (RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificato().getTest();
	}

	@Override
	protected FaseType getFaseInvio(final XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument doc = (RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificato().getFase();
	}

	@Override
	protected void managePrimoInvio(final XmlObject documento, DataColumnContainer datiAggiornamento, final boolean ignoreWarnings) throws GestoreException, SQLException {

		RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument doc = (RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument) documento;

		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificato().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio non ha un lotto con questo cig (" + fase.getW3FACIG() + ") ");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di un avanzamento semplificato del contratto con lotto non esistente in banca dati");
			return;
		}

		Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
		Long codGara = longArray[0];
		Long codLott = longArray[1];

		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio non ha un appalto con questi codGara e codLott (" + codGara + " e " + codLott + ") ");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di un avanzamento contratto semplificato con appalto non esistente in banca dati");
			return;
		}

		if (!(this.existsFase(fase.getW3FACIG(), CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA) || this.existsFase(fase.getW3FACIG(), CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO) || this.existsFase(
				fase.getW3FACIG(), CostantiW9.STIPULA_ACCORDO_QUADRO))) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto senza fase inziale o stipula accorodo quadro sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
					"Primo invio di avanzamento contratto semplificato di un lotto senza fase inziale o stipula accorodo quadro in banca dati");
			// si termina con questo errore
			return;
		}

		if (this.existsFase(fase.getW3FACIG(), CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO, fase.getW3NUM5())) {
			logger.error("La richiesta di primo invio ha un avanzamento semplificato (" + fase.getW3FACIG() + ") con indici gia' presenti sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
					"Primo invio di avanzamento contratto semplificato di un lotto con un avanzamento con indici gia' presenti in banca dati");
			// si termina con questo errore
			return;
		}

		if (!ignoreWarnings) {
			boolean isS3 = UtilitySITAT.isS3(fase.getW3FACIG(), this.sqlManager);

			if (isS3) {
				logger.error("La richiesta di primo invio avanzamento contratto semplificato non previsto per il lotto con CIG=" + fase.getW3FACIG());
				logger.error("La richiesta di primo invio ha il lotto con importo maggiore o uguale a 500000");
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di avanzamento contratto semplificato non previsto");

				return;
			}
		}

		// SE SI ARRIVA A QUESTO PUNTO SIGNIFICA CHE I CONTROLLI SONO STATI
		// SUPERATI E SI
		// PROSEGUE CON IL SALVATAGGIO DEI DATI.
		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	/**
	 * Inserimento dati Avanzamento semplificato.
	 * 
	 * @param doc
	 * @param datiAggiornamento
	 * @param primoInvio
	 * @throws SQLException
	 * @throws GestoreException
	 */
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument doc, DataColumnContainer datiAggiornamento, final boolean primoInvio)
			throws SQLException, GestoreException {

		Tab33Type tab33 = doc.getRichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificato().getTab33();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificato().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];

			this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

			// predisposizione dati W9AVAN (tab20): i dati facoltativi vengono
			// testati
			// con il metodo .isSetXXX per verificare se nel tracciato sono
			// valorizzati
			DataColumnContainer dccAvanzamento = new DataColumnContainer(new DataColumn[] { new DataColumn("W9AVAN.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9AVAN.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
					new DataColumn("W9AVAN.NUM_AVAN", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });
			if (tab33 != null) {
				if (tab33.getW3DATACER() != null) {
					dccAvanzamento.addColumn("W9AVAN.DATA_CERTIFICATO", JdbcParametro.TIPO_DATA, tab33.getW3DATACER().getTime());
				}
				dccAvanzamento.addColumn("W9AVAN.IMPORTO_CERTIFICATO", JdbcParametro.TIPO_DECIMALE, tab33.getW3ICERTIF());
			}
			dccAvanzamento.insert("W9AVAN", this.sqlManager);

			//this.aggiornaStatoExportLotto(codGara, codLott, LOTTO_DA_ESPORTARE);
		}
		// se la procedura di aggiornamento e' andata a buon fine, si aggiorna
		// lo
		// stato dell'importazione
		this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
	}

	@Override
	protected void manageRettifica(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		// TODO Auto-generated method stub

	}

}
