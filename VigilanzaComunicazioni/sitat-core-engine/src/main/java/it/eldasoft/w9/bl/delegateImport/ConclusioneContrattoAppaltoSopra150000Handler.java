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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab21Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della conclusione contratto appalto sopra
 * 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class ConclusioneContrattoAppaltoSopra150000Handler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(ConclusioneContrattoAppaltoSopra150000Handler.class);

	@Override
	protected String getNomeFlusso() {
		return "Fase di conclusione del contratto";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_CONCLUSIONE_CONTRATTO_APPALTO_SOPRA_150000;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000().getFase();

		// Non esiste un lotto di gara col CIG specificato

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di conclusione appalto semplificata di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di conclusione appalto semplificata di un lotto non appaltato in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAggiudicazione(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di una aggiudicazione non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di conclusione appalto semplificata di una aggiudicazione non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsInizio(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto senza fase una inziale sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di conclusione appalto semplificata di un lotto senza una fase inziale in banca dati");
			// si termina con questo errore
			return;
		}

		if (this.existsConclusione(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto avente gia' una fase conclusione sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di conclusione appalto semplificata di un lotto avente gia'a una fase conclusione in banca dati");
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

				logger.error("La richiesta di primo invio di conclusione del contratto non previsto per il lotto con CIG=" + fase.getW3FACIG());
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di conclusione del contratto non previsto");

				if (!isS2) {
					logger.error("La richiesta di primo invio ha il lotto inferiore a 40000");
				}
				if (isE1) {
					logger.error("La richiesta di primo invio ha il lotto escluso");
				}
				if (isSAQ) {
					logger.error("La richiesta di primo invio ha il lotto relativo ad una stipula accordo quadro (TIPO_APP=9)");
				}
				if (!isOrd) {
					logger.error("La richiesta di primo invio ha il lotto senza settori ordinari");
				}
				// si termina con questo errore
				return;
			}
		}
		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document doc, DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException,
			GestoreException {

		Tab21Type tab21 = doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000().getTab21();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];

			this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

			// predisposizione dati W9CONC (tab8): i dati facoltativi vengono
			// testati
			// con il metodo .isSetXXX per verificare se nel tracciato sono
			// valorizzati
			DataColumnContainer dccConc = new DataColumnContainer(new DataColumn[] { new DataColumn("W9CONC.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9CONC.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
					new DataColumn("W9CONC.NUM_CONC", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long("1"))) });
			if (tab21 != null) {
				if (tab21.getW3CSINTANTIC() != null) {
					dccConc.addColumn("W9CONC.INTANTIC", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab21.getW3CSINTANTIC()).toString());
				}
				if (tab21.isSetW3FLAGPOL()) {
					dccConc.addColumn("W9CONC.FLAG_POLIZZA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab21.getW3FLAGPOL()).toString());
				}
				if (tab21.getW9CODVERCON() != null) {
					dccConc.addColumn("W9CONC.DATA_VERBALE_CONSEGNA", JdbcParametro.TIPO_DATA, tab21.getW9CODVERCON().getTime());
				}
				if (tab21.getW9COTERCONT() != null) {
					dccConc.addColumn("W9CONC.TERMINE_CONTRATTO_ULT", JdbcParametro.TIPO_DATA, tab21.getW9COTERCONT().getTime());
				}
				if (tab21.isSetW9COGGPROR()) {
					dccConc.addColumn("W9CONC.NUM_GIORNI_PROROGA", JdbcParametro.TIPO_NUMERICO, new Long(tab21.getW9COGGPROR()));
				}

				if (tab21.isSetW9COORELAVO()) {
					dccConc.addColumn("W9CONC.ORE_LAVORATE", JdbcParametro.TIPO_DECIMALE, new Double(tab21.getW9COORELAVO()));
				}
				if (tab21.getW3DATAULT() != null) {
					dccConc.addColumn("W9CONC.DATA_ULTIMAZIONE", JdbcParametro.TIPO_DATA, tab21.getW3DATAULT().getTime());
				}
				if (tab21.isSetW3DATARIS()) {
					dccConc.addColumn("W9CONC.DATA_RISOLUZIONE", JdbcParametro.TIPO_DATA, tab21.getW3DATARIS().getTime());
				}
				if (tab21.isSetW3ONERIRI()) {
					dccConc.addColumn("W9CONC.ONERI_RISOLUZIONE", JdbcParametro.TIPO_DECIMALE, tab21.getW3ONERIRI());
				}
				if (tab21.isSetW3FLAGONE()) {
					dccConc.addColumn("W9CONC.FLAG_ONERI", JdbcParametro.TIPO_TESTO, tab21.getW3FLAGONE().toString());
				}
				if (tab21.isSetW3IDMOTI1()) {
					dccConc.addColumn("W9CONC.ID_MOTIVO_INTERR", JdbcParametro.TIPO_NUMERICO, new Long(tab21.getW3IDMOTI1()));
				}
				if (tab21.isSetW3IDMOTI2()) {
					dccConc.addColumn("W9CONC.ID_MOTIVO_RISOL", JdbcParametro.TIPO_NUMERICO, new Long(tab21.getW3IDMOTI2()));
				}
				// S.C. 16/03/2011 Gli attributi W3NUMINFO, W3NUMMORT e
				// W3NUMPERM
				// sono diventati obbligatori
				dccConc.addColumn("W9CONC.NUM_INFORTUNI", JdbcParametro.TIPO_NUMERICO, new Long(tab21.getW3NUMINFO()));
				dccConc.addColumn("W9CONC.NUM_INF_MORT", JdbcParametro.TIPO_NUMERICO, new Long(tab21.getW3NUMMORT()));
				dccConc.addColumn("W9CONC.NUM_INF_PERM", JdbcParametro.TIPO_NUMERICO, new Long(tab21.getW3NUMPERM()));
			}
			dccConc.insert("W9CONC", this.sqlManager);

			//this.aggiornaStatoExportLotto(codGara, codLott, LOTTO_DA_ESPORTARE);

		}
		// se la procedura di aggiornamento e' andata a buon fine, si aggiorna
		// lo
		// stato dell'importazione
		this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
	}

	/**
	 * Verifica se esiste in banca dati una fase iniziale per il lotto indicato
	 * a partire dal cig fornito nel tracciato.
	 * 
	 * @param fase
	 *            elemento fase del flusso
	 * @return true se l'elemento esiste, false altrimenti
	 * @throws SQLException
	 */
	private boolean existsInizio(FaseEstesaType fase) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(w9iniz.codgara) from w9iniz, w9lott " + "where w9iniz.codgara = w9lott.codgara and w9iniz.codlott = w9lott.codlott "
				+ "and w9lott.cig = ?", new Object[] { fase.getW3FACIG() });
		return numeroOccorrenze.intValue() > 0;
	}

	/**
	 * Verifica se esiste in banca dati una fase iniziale per il lotto indicato
	 * a partire dal cig fornito nel tracciato.
	 * 
	 * @param fase
	 *            elemento fase del flusso
	 * @return true se l'elemento esiste, false altrimenti
	 * @throws SQLException
	 */
	private boolean existsConclusione(FaseEstesaType fase) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(w9conc.codgara) from w9conc, w9lott " + "where w9conc.codgara = w9lott.codgara and w9conc.codlott = w9lott.codlott "
				+ "and w9lott.cig = ?", new Object[] { fase.getW3FACIG() });
		return numeroOccorrenze.intValue() > 0;
	}

	@Override
	protected void manageRettifica(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		// TODO Auto-generated method stub

	}

}