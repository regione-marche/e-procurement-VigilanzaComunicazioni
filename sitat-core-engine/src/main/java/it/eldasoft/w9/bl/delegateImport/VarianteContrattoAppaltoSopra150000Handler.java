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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab241Type;
import it.toscana.rete.rfc.sitat.types.Tab24Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della variante contratto di appalto sopra
 * 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class VarianteContrattoAppaltoSopra150000Handler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(VarianteContrattoAppaltoSopra150000Handler.class);

	@Override
	protected String getNomeFlusso() {
		return "Variante";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_VARIANTE_CONTRATTO_APPALTO_SOPRA_150000;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di variante di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di variante di un lotto non appaltato in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAggiudicatari(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto senza imprese aggiudicatarie sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di variante di un lotto senza imprese aggiudicatarie in banca dati");
			// si termina con questo errore
			return;
		}

		if (!(this.existsFase(fase.getW3FACIG(), CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA) || this.existsFase(fase.getW3FACIG(), CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO) || this.existsFase(
				fase.getW3FACIG(), CostantiW9.STIPULA_ACCORDO_QUADRO))) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto senza fase iniziale del contratto o stipula accordo quadro sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di variante di un lotto senza fase iniziale del contratto"
					+ " o stipula accordo quadro in banca dati");
			// si termina con questo errore
			return;
		}

		// if (this.existsVariante(fase)) {
		if (this.existsFase(fase.getW3FACIG(), CostantiW9.VARIANTE_CONTRATTO, fase.getW3NUM5())) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto con una variante gia' presente sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di variante di un lotto con una variante gia' presente in banca dati");
			// si termina con questo errore
			return;
		}

		/*
		 * if (!controlloMotivazioni(doc, PRIMO_INVIO, datiAggiornamento)) {
		 * logger.error("La richiesta di " +
		 * this.getDescrioneTipoInvio(PRIMO_INVIO) +
		 * " della variante ha motivazioni diverse dalla tipologia di contratto del lotto"
		 * ); this.setEsito(datiAggiornamento,
		 * StatoComunicazione.STATO_ERRATA.getStato(),
		 * UtilityStringhe.capitalize(this.getDescrioneTipoInvio(PRIMO_INVIO)) +
		 * " di variante di un lotto con una variante gia' presente in banca dati"
		 * ); // si termina con questo errore return; }
		 */

		// warnings
		if (!ignoreWarnings) {
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);
			boolean isAII = UtilitySITAT.isAII(fase.getW3FACIG(), this.sqlManager);
			boolean isSAQ = UtilitySITAT.isSAQ(fase.getW3FACIG(), this.sqlManager);
			boolean isOrd = UtilitySITAT.isOrd(fase.getW3FACIG(), this.sqlManager);

			if ((!isS2 && !isAII) || isE1 || isSAQ || !isOrd) {
				logger.error("La richiesta di primo invio di una variante non prevista per il lotto con CIG=" + fase.getW3FACIG());
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di variante non prevista");
				if (!isS2 && !isAII) {
					logger.error("La richiesta di primo invio ha un lotto inferiore a 40000 e non e' un intervento per ricostruzione alluvione Lunigiana-Elba");
				}
				if (isE1) {
					logger.error("La richiesta di primo invio ha un lotto escluso");
				}
				if (isSAQ) {
					logger.error("La richiesta di primo invio ha un lotto relativo ad una stipula accordo quadro (TIPO_APP=9)");
				}
				if (!isOrd) {
					logger.error("La richiesta di primo invio ha un lotto con settori non ordinari");
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
	 *            Primo invio
	 * @throws SQLException
	 *             SQLException
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document doc, final DataColumnContainer datiAggiornamento, final boolean primoInvio)
			throws SQLException, GestoreException {

		Tab24Type tab24 = doc.getRichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000().getTab24();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];

			this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

			// predisposizione dati W9VARI (tab24): i dati facoltativi vengono
			// testati
			// con il metodo .isSetXXX per verificare se nel tracciato sono
			// valorizzati
			DataColumnContainer dccVariante = new DataColumnContainer(new DataColumn[] { new DataColumn("W9VARI.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9VARI.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
					new DataColumn("W9VARI.NUM_VARI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });

			if (tab24 != null) {
				if (tab24.getW3DVERBAP() != null) {
					dccVariante.addColumn("W9VARI.DATA_VERB_APPR", JdbcParametro.TIPO_DATA, tab24.getW3DVERBAP().getTime());
				}
				if (tab24.isSetW3ALTREMO()) {
					dccVariante.addColumn("W9VARI.ALTRE_MOTIVAZIONI", JdbcParametro.TIPO_TESTO, tab24.getW3ALTREMO());
				}
				if (tab24.isSetW3IRLAVOR()) {
					dccVariante.addColumn("W9VARI.IMP_RIDET_LAVORI", JdbcParametro.TIPO_DECIMALE, tab24.getW3IRLAVOR());
				}
				if (tab24.isSetW3IRSERVI()) {
					dccVariante.addColumn("W9VARI.IMP_RIDET_SERVIZI", JdbcParametro.TIPO_DECIMALE, tab24.getW3IRSERVI());
				}
				if (tab24.isSetW3IRFORNI()) {
					dccVariante.addColumn("W9VARI.IMP_RIDET_FORNIT", JdbcParametro.TIPO_DECIMALE, tab24.getW3IRFORNI());
				}
				if (tab24.isSetW3IMPSICU()) {
					dccVariante.addColumn("W9VARI.IMP_SICUREZZA", JdbcParametro.TIPO_DECIMALE, tab24.getW3IMPSICU());
				}
				if (tab24.isSetW3IMPPRO1()) {
					dccVariante.addColumn("W9VARI.IMP_PROGETTAZIONE", JdbcParametro.TIPO_DECIMALE, tab24.getW3IMPPRO1());
				}
				if (tab24.isSetW3IMPDIS1()) {
					dccVariante.addColumn("W9VARI.IMP_DISPOSIZIONE", JdbcParametro.TIPO_DECIMALE, tab24.getW3IMPDIS1());
				}
				if (tab24.isSetW3DATAATT()) {
					dccVariante.addColumn("W9VARI.DATA_ATTO_AGGIUNTIVO", JdbcParametro.TIPO_DATA, tab24.getW3DATAATT().getTime());
				}
				if (tab24.isSetW3NUMGIO3()) {
					dccVariante.addColumn("W9VARI.NUM_GIORNI_PROROGA", JdbcParametro.TIPO_NUMERICO, new Long(tab24.getW3NUMGIO3()));
				}
		    if (tab24.isSetW3INONAS1()) {
		      dccVariante.addColumn("W9VARI.IMP_NON_ASSOG", JdbcParametro.TIPO_DECIMALE, tab24.getW3INONAS1());
		    }
		    if (tab24.isSetW3CIGNPROC()) {
		      dccVariante.addColumn("W9VARI.CIG_NUOVA_PROC", JdbcParametro.TIPO_TESTO, tab24.getW3CIGNPROC());
		    }
				
				dccVariante.addColumn("W9VARI.IMP_SUBTOTALE", JdbcParametro.TIPO_DECIMALE, tab24.getW3IMPSUBTO());
				dccVariante.addColumn("W9VARI.IMP_COMPL_APPALTO", JdbcParametro.TIPO_DECIMALE, tab24.getW3IMPCOMAP());
				dccVariante.addColumn("W9VARI.IMP_COMPL_INTERVENTO", JdbcParametro.TIPO_DECIMALE, tab24.getW3IMPCOMIN());
			}
			dccVariante.insert("W9VARI", this.sqlManager);

			Tab241Type tab241 = null;
			if (doc.getRichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000().isSetListaTab241()) {
				for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000().getListaTab241().sizeOfTab241Array(); i++) {
					tab241 = doc.getRichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000().getListaTab241().getTab241Array(i);
					// popolamento dei dati per l'inserimento di una motivazione
					// (tab241): si
					// usa un altro container dato che non e' un singolo
					// elemento ma una lista
					// di elementi da inserire nella medesima tabella

					// si costruisce il container inserendo i campi chiave
					// dell'entita' da salvare
					DataColumnContainer dccMotivazione = new DataColumnContainer(new DataColumn[] { new DataColumn("W9MOTI.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
							new DataColumn("W9MOTI.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
							new DataColumn("W9MOTI.NUM_VARI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))),
							new DataColumn("W9MOTI.NUM_MOTI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(i + 1))) });

					if (tab241 != null) {
						// si aggiungono gli altri dati al container: per i
						// campi non obbligatori
						// da tracciato si effettua il test con il
						// corrispondente metodo isSetXXX
						dccMotivazione.addColumn("W9MOTI.ID_MOTIVO_VAR", JdbcParametro.TIPO_NUMERICO, new Long(tab241.getW3IDMOTI3()));
					}
					// si richiama l'inserimento dell'occorrenza di tab81
					// (W9AGGI)
					dccMotivazione.insert("W9MOTI", this.sqlManager);
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