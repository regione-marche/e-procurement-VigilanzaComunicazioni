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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab26Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione del subappalto contratto di appalto sopra
 * 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class SubappaltoContrattoAppaltoSopra150000Handler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(SubappaltoContrattoAppaltoSopra150000Handler.class);

	@Override
	protected String getNomeFlusso() {
		return "Subappalto";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_SUBAPPALTO_CONTRATTO_APPALTO_SOPRA_150000;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {

		RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di subappalto di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di subappalto di un lotto non appaltato in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAggiudicatari(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto senza imprese aggiudicatarie sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di subappalto di un lotto senza imprese aggiudicatarie in banca dati");
			// si termina con questo errore
			return;
		}

		// if (this.existsSubappalto(fase)) {
		if (this.existsFase(fase.getW3FACIG(), CostantiW9.SUBAPPALTO, fase.getW3NUM5())) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto gia' subappaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di subappalto di un lotto gia' subappaltato in banca dati");
			// si termina con questo errore
			return;
		}

		// warnings
		if (!ignoreWarnings) {
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);
			boolean isAII = UtilitySITAT.isAII(fase.getW3FACIG(), this.sqlManager);
			boolean isOrd = UtilitySITAT.isOrd(fase.getW3FACIG(), this.sqlManager);

			if ((!isS2 && !isAII) || isE1 || !isOrd) {
				logger.error("La richiesta di primo invio di un subappalto non previsto per il lotto con CIG=" + fase.getW3FACIG());
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di variante non prevista");
				if (!(isS2)) {
					logger.error("La richiesta di primo invio subappalto fa riferimento ad un lotto non superiore a 40000" + " (cig=" + fase.getW3FACIG() + ")");
				}
				if (isAII) {
					logger.error("La richiesta di primo invio subappalto fa riferimento ad un lotto (cig=" + fase.getW3FACIG() + ") che riferisce ad un intervento che non e' di ricostruzione "
							+ "da alluvione Lunigiana Elba");
				}
				if (isE1) {
					logger.error("La richiesta di primo invio subappalto fa riferimento ad un lotto escluso" + " (cig=" + fase.getW3FACIG() + ")");
				}
				if (!isOrd) {
					logger.error("La richiesta di primo invio subappalto fa riferimento ad un lotto senza settori oridnari" + " (cig=" + fase.getW3FACIG() + ")");
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
	 *            Tipo di invio
	 * @throws SQLException
	 *             SQLException
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document doc, final DataColumnContainer datiAggiornamento, final boolean primoInvio)
			throws SQLException, GestoreException {

		Tab26Type tab26 = doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getTab26();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];
			if (fase.getW9FLCFSA() != null) {
				String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

				this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

				// predisposizione dati W9SUBA (tab26): i dati facoltativi
				// vengono testati
				// con il metodo .isSetXXX per verificare se nel tracciato sono
				// valorizzati
				DataColumnContainer dccSubappalto = new DataColumnContainer(new DataColumn[] { new DataColumn("W9SUBA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9SUBA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
						new DataColumn("W9SUBA.NUM_SUBA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, fase.getW3NUM5())) });

				if (tab26 != null) {
					if (tab26.isSetW3DATAAUT()) {
						dccSubappalto.addColumn("W9SUBA.DATA_AUTORIZZAZIONE", JdbcParametro.TIPO_DATA, tab26.getW3DATAAUT().getTime());
					}
					if (tab26.isSetW3OSUBA()) {
						dccSubappalto.addColumn("W9SUBA.OGGETTO_SUBAPPALTO", JdbcParametro.TIPO_TESTO, tab26.getW3OSUBA());
					}
					dccSubappalto.addColumn("W9SUBA.IMPORTO_PRESUNTO", JdbcParametro.TIPO_DECIMALE, tab26.getW3IPRESUN());
					dccSubappalto.addColumn("W9SUBA.IMPORTO_EFFETTIVO", JdbcParametro.TIPO_DECIMALE, tab26.getW3IEFFETT());
					if (tab26.getW3IDCATE2() != null) {
						dccSubappalto.addColumn("W9SUBA.ID_CATEGORIA", JdbcParametro.TIPO_TESTO, tab26.getW3IDCATE2().toString());
					}
					if (tab26.getW3IDCPV() != null) {
						dccSubappalto.addColumn("W9SUBA.ID_CPV", JdbcParametro.TIPO_TESTO, tab26.getW3IDCPV());
					}
					if (tab26.getArch3() != null) {
						String pkImpresa = this.gestioneImpresa(tab26.getArch3(), pkUffint);

						dccSubappalto.addColumn("W9SUBA.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);
					}
					if (tab26.isSetArch3Impagg()) {
						String pkImpresaAgg = this.gestioneImpresa(tab26.getArch3Impagg(), pkUffint);
						dccSubappalto.addColumn("W9SUBA.CODIMP_AGGIUDICATRICE", JdbcParametro.TIPO_TESTO, pkImpresaAgg);
					}
				}
				dccSubappalto.insert("W9SUBA", this.sqlManager);

				//this.aggiornaStatoExportLotto(codGara, codLott, LOTTO_DA_ESPORTARE);
			}
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