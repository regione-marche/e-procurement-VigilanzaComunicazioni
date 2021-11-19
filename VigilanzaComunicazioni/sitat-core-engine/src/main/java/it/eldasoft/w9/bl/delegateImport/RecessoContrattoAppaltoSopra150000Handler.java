package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab27Type;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione del recesso del contratto di appalto sopra
 * 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class RecessoContrattoAppaltoSopra150000Handler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(RecessoContrattoAppaltoSopra150000Handler.class);

	@Override
	protected String getNomeFlusso() {
		return "Istanza di recesso";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_RECESSO_CONTRATTO_APPALTO_SOPRA_150000;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {

		RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getFase();

		// esiste lotto
		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una Istanza di recesso di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		// Il lotto in questione non ha imprese aggiudicatarie (W9AGGI)
		if (!this.existsAggiudicazione(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di una aggiudicazione non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una Istanza di recesso di un appalto con aggiudicazione non esistente in banca dati");
			return;
		}

		// Il lotto in questione non ha appalto (W9APPA)
		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di un Recesso del contratto con appalto non esistente in banca dati");
			return;
		}

		if (this.existsRecesso(fase)) {
			logger.error("La richiesta di primo invio ha un NUM_RITA  (" + fase.getW3NUM5() + ") di un lotto gia' concluso sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una Istanza di recesso di un appalto gia' presente in banca dati");
			// si termina con questo errore
			return;
		}

		// warnings
		if (!ignoreWarnings) {
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);
			boolean isAII = UtilitySITAT.isAII(fase.getW3FACIG(), this.sqlManager);
			boolean isSAQ = UtilitySITAT.isSAQ(fase.getW3FACIG(), this.sqlManager);
			boolean isOrd = UtilitySITAT.isOrd(fase.getW3FACIG(), this.sqlManager);

			String tipo = (String) this.sqlManager.getObject("select tipo_contratto from w9lott where cig = ?", new Object[] { fase.getW3FACIG() });
			boolean isLavori = StringUtils.indexOf(tipo, 'L') >= 0;

			if ((!isS2 && !isAII) || !isLavori || isE1 || isSAQ || !isOrd) {
				logger.error("La richiesta di primo invio istanza di recesso non previsto per il lotto con CIG=" + fase.getW3FACIG());
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio istanza di recesso non previsto");

				if (!isS2 && !isAII) {
					logger.error("La richiesta di primo invio ha un lotto con importo lotto inferiore a 40000 e non e' un intervento per ricostruzione alluvione Lunigiana-Elba");
				}
				if (!isLavori) {
					logger.error("La richiesta di primo invio ha un lotto che non e' per lavori");
				}
				if (isE1) {
					logger.error("La richiesta di primo invio ha un lotto escluso");
				}
				if (isSAQ) {
					logger.error("La richiesta di primo invio ha un lotto che e' una stipula accordo quadro (TIPO_APP=9)");
				}
				if (!isOrd) {
					logger.error("La richiesta di primo invio ha un lotto senza settori ordinari");
				}
				// si termina con questo errore
				return;
			}
		}

		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document doc, DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException,
			GestoreException {

		Tab27Type tab27 = doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getTab27();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];

			this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

			// predisposizione dati W9CONC (tab8): i dati facoltativi vengono
			// testati
			// con il metodo .isSetXXX per verificare se nel tracciato sono
			// valorizzati
			DataColumnContainer dccRita = new DataColumnContainer(new DataColumn[] { new DataColumn("W9RITA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9RITA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
					new DataColumn("W9RITA.NUM_RITA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, fase.getW3NUM5())) });

			if (tab27 != null) {
				if (tab27.getW3DTERM2() != null) {
					dccRita.addColumn("W9RITA.DATA_TERMINE", JdbcParametro.TIPO_DATA, tab27.getW3DTERM2().getTime());
				}
				if (tab27.isSetW3DCONS()) {
					dccRita.addColumn("W9RITA.DATA_CONSEGNA", JdbcParametro.TIPO_DATA, tab27.getW3DCONS().getTime());
				}
				if (tab27.getW3DATAIST() != null) {
					dccRita.addColumn("W9RITA.DATA_IST_RECESSO", JdbcParametro.TIPO_DATA, tab27.getW3DATAIST().getTime());
				}
				if (tab27.isSetW3ACCOLTA()) {
					dccRita.addColumn("W9RITA.FLAG_ACCOLTA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab27.getW3ACCOLTA()).toString());
				}
				if (tab27.isSetW3MOTIVOS()) {
					dccRita.addColumn("W9RITA.MOTIVO_SOSP", JdbcParametro.TIPO_TESTO, tab27.getW3MOTIVOS());
				}

				// Gli attributi W3TIPOCOM, W3FLAGRIP, W3FLAGTAR, W3DURATAS
				// e W3RISER sono diventati obbligatori
				if (tab27.getW3TIPOCOM() != null) {
					dccRita.addColumn("W9RITA.TIPO_COMUN", JdbcParametro.TIPO_TESTO, tab27.getW3TIPOCOM().toString());
				}
				if (tab27.getW3FLAGRIP() != null) {
					dccRita.addColumn("W9RITA.FLAG_RIPRESA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab27.getW3FLAGRIP()).toString());
				}
				if (tab27.getW3FLAGTAR() != null) {
					dccRita.addColumn("W9RITA.FLAG_TARDIVA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab27.getW3FLAGTAR()).toString());
				}
				dccRita.addColumn("W9RITA.DURATA_SOSP", JdbcParametro.TIPO_NUMERICO, new Long(tab27.getW3DURATAS()));
				if (tab27.getW3RISER() != null) {
					dccRita.addColumn("W9RITA.FLAG_RISERVA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab27.getW3RISER()).toString());
				}
				if (tab27.isSetW3RITARDO()) {
					dccRita.addColumn("W9RITA.RITARDO", JdbcParametro.TIPO_NUMERICO, new Long(tab27.getW3RITARDO()));
				}
				if (tab27.isSetW3IONERI()) {
					dccRita.addColumn("W9RITA.IMPORTO_ONERI", JdbcParametro.TIPO_DECIMALE, tab27.getW3IONERI());
				}
				if (tab27.isSetW3ISPESE()) {
					dccRita.addColumn("W9RITA.IMPORTO_SPESE", JdbcParametro.TIPO_DECIMALE, tab27.getW3ISPESE());
				}
			}
			dccRita.insert("W9RITA", this.sqlManager);

			//this.aggiornaStatoExportLotto(codGara, codLott, LOTTO_DA_ESPORTARE);
		}
		// se la procedura di aggiornamento e' andata a buon fine, si aggiorna
		// lo
		// stato dell'importazione
		this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
	}

	/**
	 * Esistenza del recesso in DB.
	 * 
	 * @param fase
	 *            FaseEstesaType
	 * @return Ritorna true se esiste il recesso in DB, false altrimenti
	 * @throws SQLException
	 *             SQLException
	 */
	private boolean existsRecesso(final FaseEstesaType fase) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(w9rita.codgara) from w9rita, w9lott " + "where w9rita.codgara = w9lott.codgara and w9rita.codlott = w9lott.codlott "
				+ "and w9rita.num_rita = ? and w9lott.cig = ?", new Object[] { fase.getW3NUM5(), fase.getW3FACIG() });
		return numeroOccorrenze.intValue() > 0;
	}

	@Override
	protected void manageRettifica(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		// TODO Auto-generated method stub

	}

}