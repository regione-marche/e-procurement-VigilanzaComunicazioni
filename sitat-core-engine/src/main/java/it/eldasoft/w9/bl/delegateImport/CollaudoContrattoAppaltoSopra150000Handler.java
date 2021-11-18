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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab101Type;
import it.toscana.rete.rfc.sitat.types.Tab22Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione del collaudo contratto di appalto sopra 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class CollaudoContrattoAppaltoSopra150000Handler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(CollaudoContrattoAppaltoSopra150000Handler.class);

	@Override
	protected String getNomeFlusso() {
		return "Fase di collaudo";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_COLLAUDO_CONTRATTO_APPALTO_SOPRA_150000;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig di un lotto non presente in DB (CIG = " + fase.getW3FACIG() + ")");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di fase collaudo di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig  di un lotto non appaltato sul DB (CIG = " + fase.getW3FACIG() + ")");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di fase collaudo di un lotto non appaltato in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAggiudicatari(fase)) {
			logger.error("La richiesta di primo invio ha un cig  di un lotto senza imprese aggiudicatarie sul DB(CIG = " + fase.getW3FACIG() + ")");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di fase collaudo di un lotto senza imprese aggiudicatarie in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsInizio(fase)) {
			logger.error("La richiesta di primo invio ha un cig  di un lotto senza fase iniziale del contratto sul DB (CIG = " + fase.getW3FACIG() + ")");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di fase collaudo di un lotto senza fase iniziale del contratto in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsConclusione(fase)) {
			logger.error("La richiesta di primo invio ha un cig  di un lotto senza fase conclusione del contratto sul DB(" + fase.getW3FACIG() + ")");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di fase collaudo di un lotto senza fase conclusione del contratto in banca dati");
			// si termina con questo errore
			return;
		}

		if (this.existsCollaudo(fase)) {
			logger.error("La richiesta di primo invio ha un cig di un lotto con collaudo gia' presente sul DB (CIG = " + fase.getW3FACIG() + ")");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di fase collaudo di un lotto con collaudo gia' presente in banca dati");
			// si termina con questo errore
			return;
		}

		// warnings
		if (!ignoreWarnings) {
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);
			boolean isOrd = UtilitySITAT.isOrd(fase.getW3FACIG(), this.sqlManager);

			if (!isS2 || isE1 || !isOrd) {

				logger.error("La richiesta di primo invio di collaudo contratto non previsto per il lotto con CIG=" + fase.getW3FACIG());
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di collaudo contratto non previsto");
				// si termina con questo errore

				if (!isS2) {
					logger.error("La richiesta di primo invio ha il lotto inferiore a 40000");
				}
				if (isE1) {
					logger.error("La richiesta di primo invio ha il lotto escluso");
				}
				if (!isOrd) {
					logger.error("La richiesta di primo invio ha il lotto senza settori ordinari");
				}
				return;
			}
		}
		// SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI
		// SI
		// AGGIORNA IL DB
		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	/**
	 * Verifica se esiste in banca dati la fase di collaudo per il lotto
	 * indicato a partire dal cig fornito nel tracciato.
	 * 
	 * @param fase
	 *            elemento fase del flusso
	 * @return true se l'elemento esiste, false altrimenti
	 * @throws SQLException
	 */
	private boolean existsCollaudo(FaseEstesaType fase) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(w9coll.codgara) from w9coll, w9lott " + "where w9coll.codgara = w9lott.codgara and w9coll.codlott = w9lott.codlott "
				+ "and w9coll.num_coll = 1 and w9lott.cig = ?", new Object[] { fase.getW3FACIG() });
		return numeroOccorrenze.intValue() > 0;
	}

	/**
	 * Verifica se esiste in banca dati la fase conclusione per il lotto
	 * indicato a partire dal cig fornito nel tracciato.
	 * 
	 * @param fase
	 *            elemento fase del flusso
	 * @return true se l'elemento esiste, false altrimenti
	 * @throws SQLException
	 */
	private boolean existsConclusione(FaseEstesaType fase) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(w9conc.codgara) from w9conc, w9lott " + "where w9conc.codgara = w9lott.codgara and w9conc.codlott = w9lott.codlott "
				+ "and w9conc.num_conc = 1 and w9lott.cig = ?", new Object[] { fase.getW3FACIG() });
		return numeroOccorrenze.intValue() > 0;
	}

	/**
	 * Verifica se esiste in banca dati la fase iniziale per il lotto indicato a
	 * partire dal cig fornito nel tracciato.
	 * 
	 * @param fase
	 *            elemento fase del flusso
	 * @return true se l'elemento esiste, false altrimenti
	 * @throws SQLException
	 */
	private boolean existsInizio(FaseEstesaType fase) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(w9iniz.codgara) from w9iniz, w9lott " + "where w9iniz.codgara = w9lott.codgara and w9iniz.codlott = w9lott.codlott "
				+ "and w9iniz.num_iniz = 1 and w9lott.cig = ?", new Object[] { fase.getW3FACIG() });
		return numeroOccorrenze.intValue() > 0;
	}

	/**
	 * Effettua l'inserimento vero e proprio dei dati presenti nel flusso.
	 * 
	 * @param doc
	 *            documento XML della richiesta
	 * @param datiAggiornamento
	 *            container con i dati del flusso
	 * @throws SQLException
	 * @throws GestoreException
	 */
	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document doc, DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException,
			GestoreException {

		Tab22Type tab22 = doc.getRichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000().getTab22();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];
			String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

			this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

			// predisposizione dati W9COLL (tab22): i dati facoltativi vengono
			// testati
			// con il metodo .isSetXXX per verificare se nel tracciato sono
			// valorizzati
			DataColumnContainer dccCollaudo = new DataColumnContainer(new DataColumn[] { new DataColumn("W9COLL.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9COLL.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
					new DataColumn("W9COLL.NUM_COLL", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long("1"))) });
			if (tab22 != null) {
				if (tab22.isSetW3DATAREG()) {
					dccCollaudo.addColumn("W9COLL.DATA_REGOLARE_ESEC", JdbcParametro.TIPO_DATA, tab22.getW3DATAREG().getTime());
				}
				if (tab22.isSetW3DATACOL()) {
					dccCollaudo.addColumn("W9COLL.DATA_COLLAUDO_STAT", JdbcParametro.TIPO_DATA, tab22.getW3DATACOL().getTime());
				}
				if (tab22.isSetW3MODOCOL()) {
					dccCollaudo.addColumn("W9COLL.MODO_COLLAUDO", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab22.getW3MODOCOL().toString()));
				}
				if (tab22.isSetW3DATANOM()) {
					dccCollaudo.addColumn("W9COLL.DATA_NOMINA_COLL", JdbcParametro.TIPO_DATA, tab22.getW3DATANOM().getTime());
				}
				if (tab22.isSetW3DINIZOP()) {
					dccCollaudo.addColumn("W9COLL.DATA_INIZIO_OPER", JdbcParametro.TIPO_DATA, tab22.getW3DINIZOP().getTime());
				}
				if (tab22.isSetW3DCERTCO()) {
					dccCollaudo.addColumn("W9COLL.DATA_CERT_COLLAUDO", JdbcParametro.TIPO_DATA, tab22.getW3DCERTCO().getTime());
				}
				if (tab22.isSetW3DATADEL()) {
					dccCollaudo.addColumn("W9COLL.DATA_DELIBERA", JdbcParametro.TIPO_DATA, tab22.getW3DATADEL().getTime());
				}
				if (tab22.getW3ESITOCO() != null) {
					dccCollaudo.addColumn("W9COLL.ESITO_COLLAUDO", JdbcParametro.TIPO_TESTO, tab22.getW3ESITOCO().toString());
				}
				if (tab22.isSetW3IFLAVOR()) {
					dccCollaudo.addColumn("W9COLL.IMP_FINALE_LAVORI", JdbcParametro.TIPO_DECIMALE, tab22.getW3IFLAVOR());
				}
				if (tab22.isSetW3IFSERVI()) {
					dccCollaudo.addColumn("W9COLL.IMP_FINALE_SERVIZI", JdbcParametro.TIPO_DECIMALE, tab22.getW3IFSERVI());
				}
				if (tab22.isSetW3IFFORNI()) {
					dccCollaudo.addColumn("W9COLL.IMP_FINALE_FORNIT", JdbcParametro.TIPO_DECIMALE, tab22.getW3IFFORNI());
				}
				dccCollaudo.addColumn("W9COLL.IMP_FINALE_SECUR", JdbcParametro.TIPO_DECIMALE, tab22.getW3IFSECUR());
				if (tab22.isSetW3IMPPROG()) {
					dccCollaudo.addColumn("W9COLL.IMP_PROGETTAZIONE", JdbcParametro.TIPO_DECIMALE, tab22.getW3IMPPROG());
				}
				dccCollaudo.addColumn("W9COLL.IMP_DISPOSIZIONE", JdbcParametro.TIPO_DECIMALE, tab22.getW3IMPDIS2());
				if (tab22.isSetW3AMMDEF()) {
					dccCollaudo.addColumn("W9COLL.AMM_NUM_DEFINITE", JdbcParametro.TIPO_NUMERICO, new Long(tab22.getW3AMMDEF()));
				}
				if (tab22.isSetW3AMMDADE()) {
					dccCollaudo.addColumn("W9COLL.AMM_NUM_DADEF", JdbcParametro.TIPO_NUMERICO, new Long(tab22.getW3AMMDADE()));
				}
				if (tab22.isSetW3AMMIRIC()) {
					dccCollaudo.addColumn("W9COLL.AMM_IMPORTO_RICH", JdbcParametro.TIPO_DECIMALE, tab22.getW3AMMIRIC());
				}
				if (tab22.isSetW3AMMIDEF()) {
					dccCollaudo.addColumn("W9COLL.AMM_IMPORTO_DEF", JdbcParametro.TIPO_DECIMALE, tab22.getW3AMMIDEF());
				}
				if (tab22.isSetW3ARBDEF()) {
					dccCollaudo.addColumn("W9COLL.ARB_NUM_DEFINITE", JdbcParametro.TIPO_NUMERICO, new Long(tab22.getW3ARBDEF()));
				}
				if (tab22.isSetW3ARBDADE()) {
					dccCollaudo.addColumn("W9COLL.ARB_NUM_DADEF", JdbcParametro.TIPO_NUMERICO, new Long(tab22.getW3ARBDADE()));
				}
				if (tab22.isSetW3ARBIRIC()) {
					dccCollaudo.addColumn("W9COLL.ARB_IMPORTO_RICH", JdbcParametro.TIPO_DECIMALE, tab22.getW3ARBIRIC());
				}
				if (tab22.isSetW3ARBIDEF()) {
					dccCollaudo.addColumn("W9COLL.ARB_IMPORTO_DEF", JdbcParametro.TIPO_DECIMALE, tab22.getW3ARBIDEF());
				}
				if (tab22.isSetW3GIUDEF()) {
					dccCollaudo.addColumn("W9COLL.GIU_NUM_DEFINITE", JdbcParametro.TIPO_NUMERICO, new Long(tab22.getW3GIUDEF()));
				}
				if (tab22.isSetW3GIUDADE()) {
					dccCollaudo.addColumn("W9COLL.GIU_NUM_DADEF", JdbcParametro.TIPO_NUMERICO, new Long(tab22.getW3GIUDADE()));
				}
				if (tab22.isSetW3GIUIRIC()) {
					dccCollaudo.addColumn("W9COLL.GIU_IMPORTO_RICH", JdbcParametro.TIPO_DECIMALE, tab22.getW3GIUIRIC());
				}
				if (tab22.isSetW3GIUIDEF()) {
					dccCollaudo.addColumn("W9COLL.GIU_IMPORTO_DEF", JdbcParametro.TIPO_DECIMALE, tab22.getW3GIUIDEF());
				}
				if (tab22.isSetW3TRADEF()) {
					dccCollaudo.addColumn("W9COLL.TRA_NUM_DEFINITE", JdbcParametro.TIPO_NUMERICO, new Long(tab22.getW3TRADEF()));
				}
				if (tab22.isSetW3TRADADE()) {
					dccCollaudo.addColumn("W9COLL.TRA_NUM_DADEF", JdbcParametro.TIPO_NUMERICO, new Long(tab22.getW3TRADADE()));
				}
				if (tab22.isSetW3TRAIRIC()) {
					dccCollaudo.addColumn("W9COLL.TRA_IMPORTO_RICH", JdbcParametro.TIPO_DECIMALE, tab22.getW3TRAIRIC());
				}
				if (tab22.isSetW3TRAIDEF()) {
					dccCollaudo.addColumn("W9COLL.TRA_IMPORTO_DEF", JdbcParametro.TIPO_DECIMALE, tab22.getW3TRAIDEF());
				}
				dccCollaudo.addColumn("W9COLL.IMP_SUBTOTALE", JdbcParametro.TIPO_DECIMALE, tab22.getW3IMPSUBT2());
				dccCollaudo.addColumn("W9COLL.IMP_COMPL_APPALTO", JdbcParametro.TIPO_DECIMALE, tab22.getW3IMPCOMA2());
				dccCollaudo.addColumn("W9COLL.IMP_COMPL_INTERVENTO", JdbcParametro.TIPO_DECIMALE, tab22.getW3IMPCOMI2());

				if (tab22.isSetW3COLLLEST()) {
					dccCollaudo.addColumn("W9COLL.LAVORI_ESTESI", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab22.getW3COLLLEST()).toString());
				}
			}
			dccCollaudo.insert("W9COLL", this.sqlManager);

			// ciclo sulle imprese aggiudicatarie (W9INCA)
			Tab101Type tab221 = null;
			int k = 0;
			for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000().getListaTab221().sizeOfTab221Array(); i++) {
				tab221 = doc.getRichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000().getListaTab221().getTab221Array(i);
				// popolamento dei dati per l'inserimento di un intervento
				// (tab81): si usa
				// un altro container dato che non e' un singolo elemento ma una
				// lista di
				// elementi da inserire nella medesima tabella
				if (tab221 != null) {
					// si costruisce il container inserendo i campi chiave
					// dell'entita' da salvare
					if (tab221.getW3SEZIONE().equals("CO")) {
						k = k + 1;
						DataColumnContainer dccIncaricato = new DataColumnContainer(new DataColumn[] { new DataColumn("W9INCA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
								new DataColumn("W9INCA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
								new DataColumn("W9INCA.NUM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long("1"))),
								new DataColumn("W9INCA.NUM_INCA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(k))),
								new DataColumn("W9INCA.SEZIONE", new JdbcParametro(JdbcParametro.TIPO_TESTO, "CO")) });

						// si aggiungono gli altri dati al container: per i
						// campi
						// non
						// obbligatori
						// da tracciato si effettua il test con il
						// corrispondente
						// metodo
						// isSetXXX
						if (tab221.getArch2() != null) {
							String pkInc = this.getTecnico(tab221.getArch2(), pkUffint);
							dccIncaricato.addColumn("W9INCA.CODTEC", JdbcParametro.TIPO_TESTO, pkInc);
						}
						if (tab221.getW3IDRUOLO() != null) {
							dccIncaricato.addColumn("W9INCA.ID_RUOLO", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab221.getW3IDRUOLO().toString()));
						}
						if (tab221.isSetW3CIGPROG()) {
							dccIncaricato.addColumn("W9INCA.CIG_PROG_ESTERNA", JdbcParametro.TIPO_TESTO, tab221.getW3CIGPROG());
						}
						if (tab221.isSetW3DATAAFF()) {
							dccIncaricato.addColumn("W9INCA.DATA_AFF_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab221.getW3DATAAFF().getTime());
						}
						if (tab221.isSetW3DATACON()) {
							dccIncaricato.addColumn("W9INCA.DATA_CONS_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab221.getW3DATACON().getTime());
						}
						// si richiama l'inserimento dell'occorrenza di tab81
						// (W9INCA)
						dccIncaricato.insert("W9INCA", this.sqlManager);
					}
				}
			}

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