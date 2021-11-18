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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument;
import it.toscana.rete.rfc.sitat.types.Tab32Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'elenco delle imprese invitate/partecipanti.
 * 
 * @author Luca.Giacomazzo - Eldasoft S.p.A. Treviso
 */
public class ElencoImpreseInvitatePartecipantiHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(ElencoImpreseInvitatePartecipantiHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Elenco imprese invitate/partecipanti";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_ELENCO_IMPRESE_INVITATE_PARTECIPANTI;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc = (RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc = (RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {

		RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc = (RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio elenco imprese invitate/partecipanti ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di un elenco imprese invitate/partecipanti di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (existsFase(fase.getW3FACIG(), CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto con fase elenco imprese invitate/partecipanti gia' presente nel DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di un Elenco imprese invitate/partecipanti per questo lotto gia' presente in banca dati");
			// si termina con questo errore
			return;
		}

		// SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE
		// PER CUI SI AGGIORNA IL DB
		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	/**
	 * Inserimento dati del flusso in DB.
	 * 
	 * @param doc
	 *            RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument
	 * @param datiAggiornamento
	 *            DataColumnContainer
	 * @param primoInvio
	 *            Primo invio
	 * @throws SQLException
	 * @throws GestoreException
	 */
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc, final DataColumnContainer datiAggiornamento, final boolean primoInvio)
			throws SQLException, GestoreException {
		Tab32Type[] arrayTab32 = doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getTab32Array();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];

			String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

			this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

			for (int i = 0; i < arrayTab32.length; i++) {
				Tab32Type tab32 = arrayTab32[i];

				// predisposizione dati W9IMPRESE (tab32): i dati facoltativi
				// vengono testati
				// con il metodo .isSetXXX per verificare se nel tracciato sono
				// valorizzati
				DataColumnContainer dccW9Imprese = new DataColumnContainer(new DataColumn[] { new DataColumn("W9IMPRESE.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9IMPRESE.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
						new DataColumn("W9IMPRESE.NUM_IMPR", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(i + 1))) });
				if (tab32 != null) {
					if (tab32.getArch3() != null) {
						String pkImpresa = this.gestioneImpresa(tab32.getArch3(), pkUffint);
						dccW9Imprese.addColumn("W9IMPRESE.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);
					}
					dccW9Imprese.addColumn("W9IMPRESE.PARTECIP", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab32.getW9IMPARTEC().toString()));
					dccW9Imprese.addColumn("W9IMPRESE.TIPOAGG", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab32.getW9IMTIPOA().toString()));

					if (tab32.isSetW9IMRAGGR()) {
						dccW9Imprese.addColumn("W9IMPRESE.NUM_RAGG", JdbcParametro.TIPO_NUMERICO, new Long(tab32.getW9IMRAGGR()));
					}
					if (tab32.isSetW9IMRUOLO()) {
						dccW9Imprese.addColumn("W9IMPRESE.RUOLO", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab32.getW9IMRUOLO().toString()));
					}
				}
				dccW9Imprese.insert("W9IMPRESE", sqlManager);
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
