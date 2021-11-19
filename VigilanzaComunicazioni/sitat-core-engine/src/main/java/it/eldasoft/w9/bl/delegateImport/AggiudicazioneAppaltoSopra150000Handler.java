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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document;
import it.toscana.rete.rfc.sitat.types.Tab101Type;
import it.toscana.rete.rfc.sitat.types.Tab183Type;
import it.toscana.rete.rfc.sitat.types.Tab18Type;
import it.toscana.rete.rfc.sitat.types.Tab81Type;
import it.toscana.rete.rfc.sitat.types.impl.RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000DocumentImpl;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della aggiudicazione appalto sopra 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class AggiudicazioneAppaltoSopra150000Handler extends AbstractRequestHandler {

	/**
	 * Logger di classe.
	 */
	Logger logger = Logger.getLogger(AggiudicazioneAppaltoSopra150000Handler.class);

	@Override
	protected String getNomeFlusso() {
		return "Fase di aggiudicazione o definizione di procedura negoziata";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_AGGIUDICAZIONE_APPALTO_SOPRA_150000;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000DocumentImpl doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000DocumentImpl) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di aggiudicazione appalto di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto gia' appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di aggiudicazione appalto di un lotto gia' appaltato in banca dati");
			// si termina con questo errore
			return;
		}

		// warnings
		if (!ignoreWarnings) {
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);

			if (!isS2 || isE1) {

				logger.error("La richiesta di primo invio di aggiudicazione non prevista per il lotto con CIG=" + fase.getW3FACIG());
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di aggiudicazione non prevista");
				if (!isS2) {
					logger.error("La richiesta di primo invio ha un lotto inferiore a 40000");
				}
				if (isE1) {
					logger.error("La richiesta di primo invio ha un lotto escluso");
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
	 * 
	 * @param doc
	 *            RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document
	 * @param datiAggiornamento
	 *            DataColumnContainer
	 * @param primoInvio
	 *            E' il primo invio?
	 * @throws SQLException
	 *             SQLException
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document doc, final DataColumnContainer datiAggiornamento, final boolean primoInvio)
			throws SQLException, GestoreException {

		// ************ IMPORTANTE **************
		// Per non modificare l'RFC e per risolvere problemi di conversione da
		// Float a
		// Double di numeri con la virgola (ad esempio 11.1 viene convertito con
		// una
		// serie di cifre decimali dalla settima posizione decimale dopo la
		// virgola),
		// si esegue la seguente istruzione
		// new Double(new Float(<oggetto primitivo di tipo float>).toString())
		// ************ IMPORTANTE **************

		Tab18Type tab18 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getTab18();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getFase();

		Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
		Long codGara = longArray[0];
		Long codLott = longArray[1];
		String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

		this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

		// predisposizione dati W9APPA (tab18): i dati facoltativi vengono
		// testati
		// con il metodo .isSetXXX per verificare se nel tracciato sono
		// valorizzati
		DataColumnContainer dccAppalto = new DataColumnContainer(new DataColumn[] { new DataColumn("W9APPA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
				new DataColumn("W9APPA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
				new DataColumn("W9APPA.NUM_APPA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))) });

		if (tab18 != null) {
			if (tab18.isSetW3CODSTRU()) {
				dccAppalto.addColumn("W9APPA.COD_STRUMENTO", JdbcParametro.TIPO_TESTO, tab18.getW3CODSTRU().toString());
			}
			if (tab18.isSetW3ILAVORI()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_LAVORI", JdbcParametro.TIPO_DECIMALE, tab18.getW3ILAVORI());
			}
			if (tab18.isSetW3ISERVIZ()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_SERVIZI", JdbcParametro.TIPO_DECIMALE, tab18.getW3ISERVIZ());
			}
			if (tab18.isSetW3IFORNIT()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_FORNITURE", JdbcParametro.TIPO_DECIMALE, tab18.getW3IFORNIT());
			}
			if (tab18.isSetW3ISUBTOT()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_SUBTOTALE", JdbcParametro.TIPO_DECIMALE, tab18.getW3ISUBTOT());
			}
			if (tab18.isSetW3IATTSIC()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_ATTUAZIONE_SICUREZZA", JdbcParametro.TIPO_DECIMALE, tab18.getW3IATTSIC());
			}
			if (tab18.isSetW3IPROGET()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_PROGETTAZIONE", JdbcParametro.TIPO_DECIMALE, tab18.getW3IPROGET());
			}
			if (tab18.isSetW3INONAS()) {
				dccAppalto.addColumn("W9APPA.IMP_NON_ASSOG", JdbcParametro.TIPO_DECIMALE, tab18.getW3INONAS());
			}
			if (tab18.isSetW3ICAPPA()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_COMPL_APPALTO", JdbcParametro.TIPO_DECIMALE, tab18.getW3ICAPPA());
			}
			if (tab18.isSetW3IDISPOS()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_DISPOSIZIONE", JdbcParametro.TIPO_DECIMALE, tab18.getW3IDISPOS());
			}
			if (tab18.isSetW3ICINTE()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_COMPL_INTERVENTO", JdbcParametro.TIPO_DECIMALE, tab18.getW3ICINTE());
			}
			if (tab18.isSetW9APDURACCQ()) {
				dccAppalto.addColumn("W9APPA.DURATA_ACCQUADRO", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW9APDURACCQ()));
			}
			if (tab18.getW9APOUSCOMP() != null) {
				dccAppalto.addColumn("W9APPA.OPERE_URBANIZ_SCOMPUTO", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab18.getW9APOUSCOMP()).toString());
			}
			if (tab18.isSetW9APMODRIAG()) {
				dccAppalto.addColumn("W9APPA.MODALITA_RIAGGIUDICAZIONE", JdbcParametro.TIPO_TESTO, tab18.getW9APMODRIAG().toString());
			}
			if (tab18.isSetW3APREQSS()) {
				dccAppalto.addColumn("W9APPA.REQUISITI_SETT_SPEC", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3APREQSS().toString()));
			}
			if (tab18.isSetW3ACCOQUA()) {
				dccAppalto.addColumn("W9APPA.FLAG_ACCORDO_QUADRO", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab18.getW3ACCOQUA()).toString());
			}
			if (tab18.getW3PROCEDUR() != null) {
				dccAppalto.addColumn("W9APPA.PROCEDURA_ACC", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab18.getW3PROCEDUR()).toString());
			}
			if (tab18.getW3PREINFOR() != null) {
				dccAppalto.addColumn("W9APPA.PREINFORMAZIONE", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab18.getW3PREINFOR()).toString());
			}
			if (tab18.getW3TERMINE() != null) {
				dccAppalto.addColumn("W9APPA.TERMINE_RIDOTTO", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab18.getW3TERMINE()).toString());
			}
			if (tab18.isSetW3MODIND()) {
				dccAppalto.addColumn("W9APPA.ID_MODO_INDIZIONE", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3MODIND().toString()));
			}
			if (tab18.isSetW3DATAMAN()) {
				dccAppalto.addColumn("W9APPA.DATA_MANIF_INTERESSE", JdbcParametro.TIPO_DATA, tab18.getW3DATAMAN().getTime());
			}
			if (tab18.isSetW3NUMMANI()) {
				dccAppalto.addColumn("W9APPA.NUM_MANIF_INTERESSE", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3NUMMANI()));
			}
			if (tab18.isSetW3DSCARI()) {
				dccAppalto.addColumn("W9APPA.DATA_SCADENZA_RICHIESTA_INVITO", JdbcParametro.TIPO_DATA, tab18.getW3DSCARI().getTime());
			}
			if (tab18.isSetW3IMPRRIC()) {
				dccAppalto.addColumn("W9APPA.NUM_IMPRESE_RICHIEDENTI", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3IMPRRIC()));
			}
			if (tab18.isSetW3DATAINV()) {
				dccAppalto.addColumn("W9APPA.DATA_INVITO", JdbcParametro.TIPO_DATA, tab18.getW3DATAINV().getTime());
			}
			if (tab18.isSetW3IMPRINV()) {
				dccAppalto.addColumn("W9APPA.NUM_IMPRESE_INVITATE", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3IMPRINV()));
			}
			if (tab18.getW3DSCAPO() != null) {
				dccAppalto.addColumn("W9APPA.DATA_SCADENZA_PRES_OFFERTA", JdbcParametro.TIPO_DATA, tab18.getW3DSCAPO().getTime());
			}
			if (tab18.getW3IMPROFF() > 0) {
				dccAppalto.addColumn("W9APPA.NUM_IMPRESE_OFFERENTI", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3IMPROFF()));
			}
			if (tab18.getW3IMPRAMM() > 0) {
				dccAppalto.addColumn("W9APPA.NUM_OFFERTE_AMMESSE", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3IMPRAMM()));
			}
			if (tab18.isSetW3OFFEESC()) {
				dccAppalto.addColumn("W9APPA.NUM_OFFERTE_ESCLUSE", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3OFFEESC()));
			}
			if (tab18.isSetW3OFFEFUO()) {
				dccAppalto.addColumn("W9APPA.NUM_OFFERTE_FUORI_SOGLIA", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3OFFEFUO()));
			}
			if (tab18.isSetW3NUMIMP()) {
				dccAppalto.addColumn("W9APPA.NUM_IMP_ESCL_INSUF_GIUST", JdbcParametro.TIPO_NUMERICO, new Long(tab18.getW3NUMIMP()));
			}
			if (tab18.isSetW3OFFEMAX()) {
				dccAppalto.addColumn("W9APPA.OFFERTA_MASSIMO", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab18.getW3OFFEMAX()).toString()));
			}
			if (tab18.isSetW3OFFEMIN()) {
				dccAppalto.addColumn("W9APPA.OFFERTA_MINIMA", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab18.getW3OFFEMIN()).toString()));
			}
			if (tab18.isSetW3VALSOGL()) {
				dccAppalto.addColumn("W9APPA.VAL_SOGLIA_ANOMALIA", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab18.getW3VALSOGL()).toString()));
			}
			if (tab18.isSetW3ASTAELE()) {
				dccAppalto.addColumn("W9APPA.ASTA_ELETTRONICA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab18.getW3ASTAELE()).toString());
			}
			if (tab18.isSetW3PERCRIB()) {
				dccAppalto.addColumn("W9APPA.PERC_RIBASSO_AGG", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab18.getW3PERCRIB()).toString()));
			}
			if (tab18.isSetW3PERCOFF()) {
				dccAppalto.addColumn("W9APPA.PERC_OFF_AUMENTO", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab18.getW3PERCOFF()).toString()));
			}
			if (tab18.getW3IMPAGGI() > 0) {
				dccAppalto.addColumn("W9APPA.IMPORTO_AGGIUDICAZIONE", JdbcParametro.TIPO_DECIMALE, tab18.getW3IMPAGGI());
			}
			if (tab18.getW3DVERB() != null) {
				dccAppalto.addColumn("W9APPA.DATA_VERB_AGGIUDICAZIONE", JdbcParametro.TIPO_DATA, tab18.getW3DVERB().getTime());
			}
			if (tab18.isSetW9APTIPAT()) {
				dccAppalto.addColumn("W9APPA.TIPO_ATTO", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab18.getW9APTIPAT().toString()));
			}
			if (tab18.isSetW9APDATAT()) {
				dccAppalto.addColumn("W9APPA.DATA_ATTO", JdbcParametro.TIPO_DATA, tab18.getW9APDATAT().getTime());
			}
			if (tab18.isSetW9APNUMAT()) {
				dccAppalto.addColumn("W9APPA.NUMERO_ATTO", JdbcParametro.TIPO_TESTO, tab18.getW9APNUMAT());
			}
			if (tab18.getW3FLAGRIC() != null) {
				dccAppalto.addColumn("W9APPA.FLAG_RICH_SUBAPPALTO", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab18.getW3FLAGRIC()).toString());
			}
			if (tab18.isSetW9APIVA()) {
				dccAppalto.addColumn("W9APPA.IVA", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab18.getW9APIVA()).toString()));
			}
			if (tab18.isSetW9APIMPIVA()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_IVA", JdbcParametro.TIPO_DECIMALE, tab18.getW9APIMPIVA());
			}
			
		}
		dccAppalto.insert("W9APPA", this.sqlManager);

		// ciclo sulle imprese aggiudicatarie (W9AGGI)
		Tab81Type tab181 = null;
		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getListaTab181().sizeOfTab181Array(); i++) {
			tab181 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getListaTab181().getTab181Array(i);
			// popolamento dei dati per l'inserimento di un intervento (tab181):
			// si usa
			// un altro container dato che non e' un singolo elemento ma una
			// lista di
			// elementi da inserire nella medesima tabella

			// si costruisce il container inserendo i campi chiave dell'entita'
			// da
			// salvare
			DataColumnContainer dccAggiudicazione = new DataColumnContainer(new DataColumn[] { new DataColumn("W9AGGI.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9AGGI.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
					new DataColumn("W9AGGI.NUM_APPA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))),
					new DataColumn("W9AGGI.NUM_AGGI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(i + 1))) });

			// si aggiungono gli altri dati al container: per i campi non
			// obbligatori
			// da tracciato si effettua il test con il corrispondente metodo
			// isSetXXX
			if (tab181 != null) {
				if (tab181.getW3IDTIPOA() != null) {
					dccAggiudicazione.addColumn("W9AGGI.ID_TIPOAGG", JdbcParametro.TIPO_NUMERICO, new Long(tab181.getW3IDTIPOA().toString()));
				}
				if (tab181.isSetW3RUOLO()) {
					dccAggiudicazione.addColumn("W9AGGI.RUOLO", JdbcParametro.TIPO_NUMERICO, new Long(tab181.getW3RUOLO().toString()));
				}
				if (tab181.getW3FLAGAVV() != null) {
					dccAggiudicazione.addColumn("W9AGGI.FLAG_AVVALIMENTO", JdbcParametro.TIPO_NUMERICO, new Long(tab181.getW3FLAGAVV().toString()));
				}
				if (tab181.getArch3() != null) {
					String pkImpresa = this.gestioneImpresa(tab181.getArch3(), pkUffint);
					dccAggiudicazione.addColumn("W9AGGI.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);
				}
				if (tab181.isSetArch3Avv()) {
					String pkImpresaAvv = this.gestioneImpresa(tab181.getArch3Avv(), pkUffint);
					dccAggiudicazione.addColumn("W9AGGI.CODIMP_AUSILIARIA", JdbcParametro.TIPO_TESTO, pkImpresaAvv);
				}

				if (tab181.isSetW3AGIDGRP()) {
					dccAggiudicazione.addColumn("W9AGGI.ID_GRUPPO", JdbcParametro.TIPO_NUMERICO, new Long(tab181.getW3AGIDGRP()));
				}
			}
			// si richiama l'inserimento dell'occorrenza di tab181 (W9AGGI)
			dccAggiudicazione.insert("W9AGGI", this.sqlManager);
		}

		// ciclo sulle imprese aggiudicatarie (W9INCA)
		if (doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getListaTab182().sizeOfTab182Array() > 0) {
			// Se esistono gia' incarichi professionali (inseriti
			// dall'anagrafica del lotto)
			// vanno cancellati.
			Long numeroIncarichi = (Long) this.sqlManager.getObject("select count(*) from w9inca where codgara = ? and codlott = ? and sezione = ?", new Object[] { codGara, codLott, "RA" });

			if (numeroIncarichi != null && numeroIncarichi > 0) {
				this.sqlManager.update("delete from w9inca where codgara = ? and codlott = ? and (sezione = ? or sezione = ?)", new Object[] { codGara, codLott, "PA", "RA" });
			}
		}

		Tab101Type tab182 = null;
		int k = 0;
		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getListaTab182().sizeOfTab182Array(); i++) {
			tab182 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getListaTab182().getTab182Array(i);
			// popolamento dei dati per l'inserimento di un intervento (tab182):
			// si usa
			// un altro container dato che non e' un singolo elemento ma una
			// lista di
			// elementi da inserire nella medesima tabella

			// si costruisce il container inserendo i campi chiave dell'entita'
			// da salvare
			String sezione = "PA";

			if (tab182 != null) {
				if (tab182.isSetW3SEZIONE()) {
					String ruolo = tab182.getW3IDRUOLO().toString();
					if (ruolo.trim().equals("5") || ruolo.trim().equals("6") || ruolo.trim().equals("7") || ruolo.trim().equals("8") || ruolo.trim().equals("14") || ruolo.trim().equals("15")
							|| ruolo.trim().equals("16") || ruolo.trim().equals("17") || ruolo.trim().equals("18")) {
						sezione = "RA";
					}
				}
				if (tab182.getW3SEZIONE().equals("PA") || tab182.getW3SEZIONE().equals("RA")) {
					k = k + 1;
					DataColumnContainer dccIncarichi = new DataColumnContainer(new DataColumn[] { new DataColumn("W9INCA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
							new DataColumn("W9INCA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
							new DataColumn("W9INCA.NUM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))),
							new DataColumn("W9INCA.NUM_INCA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(k))),
							new DataColumn("W9INCA.SEZIONE", new JdbcParametro(JdbcParametro.TIPO_TESTO, sezione)) });

					// si aggiungono gli altri dati al container: per i campi
					// non
					// obbligatori
					// da tracciato si effettua il test con il corrispondente
					// metodo
					// isSetXXX
					if (tab182.getArch2() != null) {
						String pkTecnico = this.getTecnico(tab182.getArch2(), pkUffint);
						dccIncarichi.addColumn("W9INCA.CODTEC", JdbcParametro.TIPO_TESTO, pkTecnico);
					}
					if (tab182.getW3IDRUOLO() != null) {
						dccIncarichi.addColumn("W9INCA.ID_RUOLO", JdbcParametro.TIPO_NUMERICO, new Long(tab182.getW3IDRUOLO().toString()));
					}
					if (tab182.isSetW3CIGPROG()) {
						dccIncarichi.addColumn("W9INCA.CIG_PROG_ESTERNA", JdbcParametro.TIPO_TESTO, tab182.getW3CIGPROG());
					}
					if (tab182.isSetW3DATAAFF()) {
						dccIncarichi.addColumn("W9INCA.DATA_AFF_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab182.getW3DATAAFF().getTime());
					}
					if (tab182.isSetW3DATACON()) {
						dccIncarichi.addColumn("W9INCA.DATA_CONS_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab182.getW3DATACON().getTime());
					}

					// si richiama l'inserimento dell'occorrenza di tab182
					// (W9INCA)
					dccIncarichi.insert("W9INCA", this.sqlManager);
				}
			}
		}

		// ciclo sulle imprese aggiudicatarie (W9FINA)
		Tab183Type tab183 = null;
		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getListaTab183().sizeOfTab183Array(); i++) {
			tab183 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000().getListaTab183().getTab183Array(i);
			// popolamento dei dati per l'inserimento di un intervento (tab183):
			// si usa
			// un altro container dato che non e' un singolo elemento ma una
			// lista di
			// elementi da inserire nella medesima tabella

			// si costruisce il container inserendo i campi chiave dell'entita'
			// da salvare
			DataColumnContainer dccFinanziamenti = new DataColumnContainer(new DataColumn[] { new DataColumn("W9FINA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9FINA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
					new DataColumn("W9FINA.NUM_APPA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))),
					new DataColumn("W9FINA.NUM_FINA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(i + 1))) });

			// si aggiungono gli altri dati al container: per i campi non
			// obbligatori
			// da tracciato si effettua il test con il corrispondente metodo
			// isSetXXX
			if (tab183 != null) {
				if (tab183.getW3IDFINAN() != null) {
					dccFinanziamenti.addColumn("W9FINA.ID_FINANZIAMENTO", JdbcParametro.TIPO_TESTO, tab183.getW3IDFINAN().toString());
				}
				dccFinanziamenti.addColumn("W9FINA.IMPORTO_FINANZIAMENTO", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab183.getW3IFINANZ()).toString()));
				// si richiama l'inserimento dell'occorrenza di tab182 (W9INCA)
			}
			dccFinanziamenti.insert("W9FINA", this.sqlManager);
		}

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