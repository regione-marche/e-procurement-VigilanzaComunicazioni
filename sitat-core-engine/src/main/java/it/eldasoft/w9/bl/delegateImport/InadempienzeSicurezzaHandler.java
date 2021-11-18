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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument;
import it.toscana.rete.rfc.sitat.types.Tab15Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'esito negativo della verifica regolarita'
 * contributiva e assicurativa
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class InadempienzeSicurezzaHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(InadempienzeSicurezzaHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Inadempienze predisposizioni sicurezza e regolarita' lavoro";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_INADEMPIENZE_SICUREZZA;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc = (RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc = (RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc = (RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB.");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di Inadempienze con lotto non esistente in banca dati");
			return;
		}

		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di Inadempienze con un lotto non appaltato in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAggiudicazione(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di Inadempienze con  un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		// if (this.existsInadempienze(fase)) {
		if (this.existsFase(fase.getW3FACIG(), CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI, fase.getW3NUM5())) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto che presenta gia' inadempienze in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di inadempienze gia' presenti in banca dati");
			// si termina con questo errore
			return;
		}

		// Warnings
		if (!ignoreWarnings) {
			boolean isLottoConManodopera = UtilitySITAT.isR(fase.getW3FACIG(), sqlManager);

			if (!isLottoConManodopera) {
				logger.error("La richiesta di primo invio ha un lotto che non prevede manodopera");
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di scheda inadempienze di un lotto che non prevede manodopera");
				// si termina con questo errore
				return;
			}
		}

		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument doc, DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException, GestoreException {
		Tab15Type tab15 = doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getTab15();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaInadempienzeSicurezza().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];
			if (fase.getW9FLCFSA() != null) {
				String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

				this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

				// predisposizione dati W9INASIC (tab15): i dati facoltativi
				// vengono testati
				// con il metodo .isSetXXX per verificare se nel tracciato sono
				// valorizzati
				DataColumnContainer dccInasic = new DataColumnContainer(new DataColumn[] { new DataColumn("W9INASIC.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9INASIC.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
						new DataColumn("W9INASIC.NUM_INAD", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });
				if (tab15 != null) {
					if (tab15.isSetArch3()) {
						String pkImpresa = this.gestioneImpresa(tab15.getArch3(), pkUffint);
						dccInasic.addColumn("W9INASIC.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);
					}
					if (tab15.isSetW3COMMA3A()) {
						dccInasic.addColumn("W9INASIC.COMMA3A", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab15.getW3COMMA3A()).toString());
					}
					if (tab15.isSetW3COMMA3B()) {
						dccInasic.addColumn("W9INASIC.COMMA3B", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab15.getW3COMMA3B()).toString());
					}
					if (tab15.isSetW3COMMA45A()) {
						dccInasic.addColumn("W9INASIC.COMMA45A", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab15.getW3COMMA45A()).toString());
					}
					if (tab15.isSetW3COMMA5()) {
						dccInasic.addColumn("W9INASIC.COMMA1", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab15.getW3COMMA5()).toString());
					}
					if (tab15.isSetW3COMMA6()) {
						dccInasic.addColumn("W9INASIC.COMMA2", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab15.getW3COMMA6()).toString());
					}
					if (tab15.isSetW3COMMALTRO()) {
						dccInasic.addColumn("W9INASIC.COMMALTRO", JdbcParametro.TIPO_TESTO, tab15.getW3COMMALTRO());
					}
					if (tab15.getW3DAINASIC() != null) {
						dccInasic.addColumn("W9INASIC.DAINASIC", JdbcParametro.TIPO_DATA, tab15.getW3DAINASIC().getTime());
					}
					if (tab15.isSetW3DESIC()) {
						dccInasic.addColumn("W9INASIC.DESCVIO", JdbcParametro.TIPO_TESTO, tab15.getW3DESIC());
					}
					// campi differenti rispetto alla vers.9
				}
				dccInasic.insert("W9INASIC", this.sqlManager);
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