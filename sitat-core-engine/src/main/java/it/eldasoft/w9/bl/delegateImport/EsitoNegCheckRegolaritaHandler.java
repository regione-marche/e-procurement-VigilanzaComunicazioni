package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.common.CostantiW9;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument;
import it.toscana.rete.rfc.sitat.types.Tab12Type;

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
public class EsitoNegCheckRegolaritaHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(EsitoNegCheckRegolaritaHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Esito negativo verifica regolarita' contributiva ed assicurativa";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_ESITO_NEG_CHECK_REGOLARITA;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument doc = (RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaEsitoNegCheckRegolarita().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument doc = (RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaEsitoNegCheckRegolarita().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument doc = (RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaEsitoNegCheckRegolarita().getFase();

		// Non esiste un lotto di gara col CIG specificato
		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio non ha un lotto con questo cig (" + fase.getW3FACIG() + ") ");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
					"Primo invio di Esito negativo verifica regolarita' contributiva ed assicurativa con lotto non esistente in banca dati");
			return;
		}
		// if (this.existsRegolita(fase)) {
		if (this.existsFase(fase.getW3FACIG(), CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA, fase.getW3NUM5())) {
			logger.error("La richiesta di primo invio ha un num_rego(" + fase.getW3NUM5() + ")  gia presente sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
					"Primo invio di Esito negativo verifica regolarita' contributiva ed assicurativa e' gia' presente in banca dati");
			return;
		}

		/*
		 * if (!ignoreWarnings) { boolean isGaraStipulaAccordoQuadro =
		 * this.isLottoStipulaAccordoQuadro(fase); if
		 * (isGaraStipulaAccordoQuadro) { logger.error(
		 * "La richiesta di primo invio e' relativa ad una stipula accordo quadro"
		 * ); this.setEsito(datiAggiornamento,
		 * CostantiStatoComunicazione.STATO_WARNING,
		 * "Primo invio di Esito negativo idoneita' tecnico-professionale relativo ad una stipula accordo quadro"
		 * ); // si termina con questo errore return; } }
		 */

		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument doc, DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException,
			GestoreException {

		Tab12Type tab12 = doc.getRichiestaRichiestaRispostaSincronaEsitoNegCheckRegolarita().getTab12();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaEsitoNegCheckRegolarita().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];
			if (fase.getW9FLCFSA() != null) {
				String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

				this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

				// predisposizione dati W9REGCON (tab12): i dati facoltativi
				// vengono testati
				// con il metodo .isSetXXX per verificare se nel tracciato sono
				// valorizzati
				DataColumnContainer dccRegcon = new DataColumnContainer(new DataColumn[] { new DataColumn("W9REGCON.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9REGCON.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
						new DataColumn("W9REGCON.NUM_REGO", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });
				if (tab12 != null) {
					if (tab12.getArch3() != null) {
						String pkImpresa = this.gestioneImpresa(tab12.getArch3(), pkUffint);
						dccRegcon.addColumn("W9REGCON.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);
					}
					if (tab12.getW9RCRISCIRR() != null) {
						dccRegcon.addColumn("W9REGCON.RISCONTRO_IRR", JdbcParametro.TIPO_NUMERICO, new Long(tab12.getW9RCRISCIRR().toString()));
					}
					if (tab12.getW3RCDESCARE() != null) {
						dccRegcon.addColumn("W9REGCON.DESCARE", JdbcParametro.TIPO_TESTO, tab12.getW3RCDESCARE().toString());
					}
					if (tab12.getW9RCDATACOMU() != null) {
						dccRegcon.addColumn("WREGCON.DATACOMUN", JdbcParametro.TIPO_DATA, tab12.getW9RCDATACOMU().getTime());
					}
					if (tab12.getW3RCIDDURC() != null) {
						dccRegcon.addColumn("W9REGCON.IDDURC", JdbcParametro.TIPO_TESTO, tab12.getW3RCIDDURC().toString());
					}
					if (tab12.getW3RCDATADURC() != null) {
						dccRegcon.addColumn("W9REGCON.DATADURC", JdbcParametro.TIPO_DATA, tab12.getW3RCDATADURC().getTime());
					}
					if (tab12.getW3RCCASSAEDI() != null) {
						dccRegcon.addColumn("W9REGCON.CASSAEDI", JdbcParametro.TIPO_TESTO, tab12.getW3RCCASSAEDI().toString());
					}
				}
				dccRegcon.insert("W9REGCON", this.sqlManager);
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
