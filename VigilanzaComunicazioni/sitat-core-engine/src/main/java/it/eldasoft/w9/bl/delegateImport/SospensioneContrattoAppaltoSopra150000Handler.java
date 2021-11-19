package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab23Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della sospensione contratto di appalto sopra
 * 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class SospensioneContrattoAppaltoSopra150000Handler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(SospensioneContrattoAppaltoSopra150000Handler.class);

	@Override
	protected String getNomeFlusso() {
		return "Sospensione";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_SOSPENSIONE_CONTRATTO_APPALTO_SOPRA_150000;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di sospensione di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di sospensione di un lotto non appaltato in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAggiudicatari(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto senza imprese aggiudicatarie sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di sospensione di un lotto senza imprese aggiudicatarie in banca dati");
			// si termina con questo errore
			return;
		}

		if (!(this.existsFase(fase.getW3FACIG(), CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA) || this.existsFase(fase.getW3FACIG(), CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO) || this.existsFase(
				fase.getW3FACIG(), CostantiW9.STIPULA_ACCORDO_QUADRO))) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto senza fase iniziale del contratto o stipula accordo quadro sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di sospensione di un lotto senza fase iniziale"
					+ " o stipula accordo quadro del contratto in banca dati");
			// si termina con questo errore
			return;
		}

		// if (this.existsSospensione(fase)) {
		if (this.existsFase(fase.getW3FACIG(), CostantiW9.SOSPENSIONE_CONTRATTO, fase.getW3NUM5())) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto con una sospensione gia' presente sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di sospensione di un lotto con una sospensione gia' presente in banca dati");
			// si termina con questo errore
			return;
		}

		// warnings
		if (!ignoreWarnings) {
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);
			boolean isAII = UtilitySITAT.isAII(fase.getW3FACIG(), this.sqlManager);
			boolean isSAQ = UtilitySITAT.isSAQ(fase.getW3FACIG(), this.sqlManager);
			boolean isR = UtilitySITAT.isR(fase.getW3FACIG(), this.sqlManager);
			boolean isOrd = UtilitySITAT.isOrd(fase.getW3FACIG(), this.sqlManager);

			if ((!isS2 && !isAII && !isR) || isE1 || isSAQ || !isOrd) {
				logger.error("La richiesta di primo invio di una sospensione non previsto per il lotto con CIG=" + fase.getW3FACIG());
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di sospensione non previsto");

				if (!isS2 && !isAII) {
					logger.error("La richiesta di primo invio ha un lotto sotto 40000 e non e' un intervento ricostruzione alluvione Lunigiana-Elba (!isS2 && !isAII)");
				}
				if (!isR) {
					logger.error("La richiesta di primo invio ha un lotto senza manodopera/posa in opera");
				}
				if (isE1) {
					logger.error("La richiesta di primo invio ha un lotto escluso");
				}
				if (isSAQ) {
					logger.error("La richiesta di primo invio ha un lotto relativo ad una stipula accordo quadro (TIPO_APP=9)");
				}
				if (!isOrd) {
					logger.error("La richiesta di primo invio ha un lotto senza settori ordinari");
				}
				// si termina con questo errore
				return;
			}
		}
		// SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI
		// SI
		// AGGIORNA IL DB
		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	/**
	 * Effettua l'inserimento vero e proprio dei dati presenti nel flusso.
	 * 
	 * @param doc
	 *            documento XML della richiesta
	 * @param datiAggiornamento
	 *            container con i dati del flusso
	 * @param primoInvio
	 *            E' il primo invio?
	 * @throws SQLException
	 *             SQLException
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document doc, final DataColumnContainer datiAggiornamento, final boolean primoInvio)
			throws SQLException, GestoreException {

		Tab23Type tab23 = doc.getRichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000().getTab23();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];

			this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

			// predisposizione dati W9SOSP (tab23): i dati facoltativi vengono
			// testati
			// con il metodo .isSetXXX per verificare se nel tracciato sono
			// valorizzati
			DataColumnContainer dccSospensione = new DataColumnContainer(new DataColumn[] { new DataColumn("W9SOSP.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9SOSP.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
					new DataColumn("W9SOSP.NUM_SOSP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });

			if (tab23 != null) {
				if (tab23.getW3DVERBSO() != null) {
					dccSospensione.addColumn("W9SOSP.DATA_VERB_SOSP", JdbcParametro.TIPO_DATA, tab23.getW3DVERBSO().getTime());
				}
				if (tab23.isSetW3DVERBRI()) {
					dccSospensione.addColumn("W9SOSP.DATA_VERB_RIPR", JdbcParametro.TIPO_DATA, tab23.getW3DVERBRI().getTime());
				}
				if (tab23.getW3IDMOTI4() != null) {
					dccSospensione.addColumn("W9SOSP.ID_MOTIVO_SOSP", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab23.getW3IDMOTI4().toString()));
				}
				if (tab23.getW3FLAGSUP() != null) {
					dccSospensione.addColumn("W9SOSP.FLAG_SUPERO_TEMPO", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab23.getW3FLAGSUP()).toString());
				}
				if (tab23.getW3RISERVE() != null) {
					dccSospensione.addColumn("W9SOSP.FLAG_RISERVE", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab23.getW3RISERVE()).toString());
				}
				if (tab23.getW3FLAGVER() != null) {
					dccSospensione.addColumn("W9SOSP.FLAG_VERBALE", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab23.getW3FLAGVER()).toString());
				}
			}
			dccSospensione.insert("W9SOSP", this.sqlManager);

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