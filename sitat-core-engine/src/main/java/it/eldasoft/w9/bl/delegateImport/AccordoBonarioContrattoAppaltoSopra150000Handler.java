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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab25Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'accordo bonario contratto di appalto sopra
 * 40000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class AccordoBonarioContrattoAppaltoSopra150000Handler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(AccordoBonarioContrattoAppaltoSopra150000Handler.class);

	@Override
	protected String getNomeFlusso() {
		return "Accordo bonario";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_ACCORDO_BONARIO_CONTRATTO_APPALTO_SOPRA_150000;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {

		RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio accordo bonario ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di accordo bonario di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio accordo bonario fa riferimento ad un lotto (cig=" + fase.getW3FACIG() + ") privo di appalto");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di accordo bonario di un lotto privo di appalto in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAggiudicatari(fase)) {
			logger.error("La richiesta di primo invio accordo bonario fa riferimento ad un lotto (cig=" + fase.getW3FACIG() + ") privo di imprese aggiudicatarie sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di accordo bonario di un lotto privo di imprese aggiudicatarie in banca dati");
			// si termina con questo errore
			return;
		}

		// if (!this.existsInizio(fase)) {
		if (!(this.existsFase(fase.getW3FACIG(), CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA) || this.existsFase(fase.getW3FACIG(), CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO) || this.existsFase(
				fase.getW3FACIG(), CostantiW9.STIPULA_ACCORDO_QUADRO))) {
			logger.error("La richiesta di primo invio accordo bonario fa riferimento ad un lotto (cig=" + fase.getW3FACIG()
					+ ") privo della fase iniziale del contratto o stipula accordo quadro sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di accordo bonario di un lotto privo della fase iniziale del contratto"
					+ " o stipula accordo quadro in banca dati");
			// si termina con questo errore
			return;
		}

		// if (this.existsAccordo(fase)) {
		if (this.existsFase(fase.getW3FACIG(), CostantiW9.ACCORDO_BONARIO, fase.getW3NUM5())) {
			logger.error("La richiesta di primo invio accordo bonario fa riferimento ad un lotto (cig=" + fase.getW3FACIG() + ") con accordo bonario gia' presente sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di accordo bonario di un lotto gia' presente in banca dati");
			// si termina con questo errore
			return;
		}

		// warnings
		if (!ignoreWarnings) {
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);
			boolean isSAQ = UtilitySITAT.isSAQ(fase.getW3FACIG(), this.sqlManager);
			boolean isOrd = UtilitySITAT.isOrd(fase.getW3FACIG(), this.sqlManager);

			if (!isS2 || isE1 || isSAQ || !isOrd) {
				logger.error("La richiesta di primo invio accordo bonario non previsto per il lotto con CIG=" + fase.getW3FACIG());
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di accordo bonario non previsto");
				if (!isS2) {
					logger.error("La richiesta di primo invio accordo bonario fa riferimento ad un lotto (cig=" + fase.getW3FACIG() + ") con importo inferiore a 40000");
				}
				if (isSAQ) {
					logger.error("La richiesta di primo invio accordo bonario fa riferimento ad un lotto (cig=" + fase.getW3FACIG() + ") con stipula accordo quadro (TIPO_APP=9)");
				}
				if (isSAQ) {
					logger.error("La richiesta di primo invio accordo bonario fa riferimento ad un lotto (cig=" + fase.getW3FACIG() + ") escluso");
				}
				if (!isOrd) {
					logger.error("La richiesta di primo invio accordo bonario fa riferimento ad un lotto (cig=" + fase.getW3FACIG() + ") senza settori oridnari");
				}
				// si termina con questo errore
				return;
			}
		}
		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	/**
	 * Inserimento dati del flusso in DB.
	 * 
	 * @param doc
	 *            RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document
	 * @param datiAggiornamento
	 *            DataColumnContainer
	 * @param primoInvio
	 *            Primo Invio
	 * @throws SQLException
	 *             SQLException
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document doc, final DataColumnContainer datiAggiornamento, final boolean primoInvio)
			throws SQLException, GestoreException {
		Tab25Type tab25 = doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getTab25();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000().getFase();

		Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
		Long codGara = longArray[0];
		Long codLott = longArray[1];

		this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

		// predisposizione dati W9ACCO (tab18): i dati facoltativi vengono
		// testati
		// con il metodo .isSetXXX per verificare se nel tracciato sono
		// valorizzati
		DataColumnContainer dccAcco = new DataColumnContainer(new DataColumn[] { new DataColumn("W9ACCO.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
				new DataColumn("W9ACCO.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
				new DataColumn("W9ACCO.NUM_ACCO", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });

		if (tab25 != null) {
			if (tab25.getW3DATAACC() != null) {
				dccAcco.addColumn("W9ACCO.DATA_ACCORDO", JdbcParametro.TIPO_DATA, tab25.getW3DATAACC().getTime());
			}

			if (tab25.isSetW3NUMRISE()) {
				dccAcco.addColumn("W9ACCO.NUM_RISERVE", JdbcParametro.TIPO_NUMERICO, new Long(tab25.getW3NUMRISE()));
			}

			if (tab25.isSetW3ONERIDE()) {
				dccAcco.addColumn("W9ACCO.ONERI_DERIVANTI", JdbcParametro.TIPO_DECIMALE, tab25.getW3ONERIDE());
			}
		}
		dccAcco.insert("W9ACCO", this.sqlManager);

		//this.aggiornaStatoExportLotto(codGara, codLott, LOTTO_DA_ESPORTARE);

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