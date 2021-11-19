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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument;
import it.toscana.rete.rfc.sitat.types.Tab101Type;
import it.toscana.rete.rfc.sitat.types.Tab293Type;
import it.toscana.rete.rfc.sitat.types.Tab29Type;
import it.toscana.rete.rfc.sitat.types.Tab81Type;
import it.toscana.rete.rfc.sitat.types.impl.RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocumentImpl;

import java.sql.SQLException;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della adesione accordo quadro.
 * 
 * @author Luca.Giacomazzo
 */
public class AdesioneAccordoQuadroHandler extends AbstractRequestHandler {

	@Override
	protected String getNomeFlusso() {
		return "Adesione accordo quadro";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_ADESIONE_ACCORDO_QUADRO;
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument doc = (RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getFase();
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument doc = (RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getTest();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {

		RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocumentImpl doc = (RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocumentImpl) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getFase();

		boolean continua = true;

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di adesione accordo quadro di un lotto non presente in banca dati");
			continua = false;
		}

		if (continua && this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto gia' appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di adesione accordo quadro di un lotto gia' appaltato in banca dati");
			continua = false;
		}

		// warnings
		if (continua && !ignoreWarnings) {
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);
			boolean isOrd = UtilitySITAT.isOrd(fase.getW3FACIG(), this.sqlManager);
			boolean isAAQ = UtilitySITAT.isAAQ(fase.getW3FACIG(), this.sqlManager);

			if (!(isAAQ && (isS2 || !(isOrd || isE1)))) {
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di una adesione accordo quadro non prevista");

				if (!isAAQ) {
					logger.error("La richiesta di prmo invio di adesione accordo quadro e' relativo ad una gara che "
							+ "non e' una adesione ad accordo quadro senza confronto competitivo (TIPO_APP <> 11)");
				}
				if (!isS2) {
					logger.error("La richiesta di primo invio di una adesione accordo quadro e' relativa ad " + "un lotto con importo < 40000");
				}
				if (isOrd && isE1) {
					logger.error("La richiesta di primo invio di una adesione accordo quadro e' relativa ad " + "un lotto escluso e con settori ordinari");
				}
				continua = false;
			}
		}

		if (continua) {
			this.insertDatiFlusso(doc, datiAggiornamento, true);
		}
	}

	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument doc, DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException, GestoreException {

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

		Tab29Type tab29 = doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getTab29();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getFase();

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

		if (tab29 != null) {
			if (tab29.isSetW3ILAVORI()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_LAVORI", JdbcParametro.TIPO_DECIMALE, tab29.getW3ILAVORI());
			}
			if (tab29.isSetW3ISERVIZ()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_SERVIZI", JdbcParametro.TIPO_DECIMALE, tab29.getW3ISERVIZ());
			}
			if (tab29.isSetW3IFORNIT()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_FORNITURE", JdbcParametro.TIPO_DECIMALE, tab29.getW3IFORNIT());
			}
			if (tab29.isSetW3CODSTRU()) {
				dccAppalto.addColumn("W9APPA.COD_STRUMENTO", JdbcParametro.TIPO_TESTO, tab29.getW3CODSTRU().toString());
			}
			if (tab29.isSetW3ISUBTOT()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_SUBTOTALE", JdbcParametro.TIPO_DECIMALE, tab29.getW3ISUBTOT());
				dccAppalto.addColumn("W9APPA.IMPORTO_COMPL_APPALTO", JdbcParametro.TIPO_DECIMALE, tab29.getW3ISUBTOT());
				dccAppalto.addColumn("W9APPA.IMPORTO_COMPL_INTERVENTO", JdbcParametro.TIPO_DECIMALE, tab29.getW3ISUBTOT());
			}

			if (tab29.isSetW3PERCRIB()) {
				dccAppalto.addColumn("W9APPA.PERC_RIBASSO_AGG", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab29.getW3PERCRIB()).toString()));
			}
			if (tab29.isSetW3PERCOFF()) {
				dccAppalto.addColumn("W9APPA.PERC_OFF_AUMENTO", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab29.getW3PERCOFF()).toString()));
			}

			if (tab29.getW3IMPAGGI() > 0.0) {
				dccAppalto.addColumn("W9APPA.IMPORTO_AGGIUDICAZIONE", JdbcParametro.TIPO_DECIMALE, tab29.getW3IMPAGGI());
			}
			if (tab29.getW3DVERB() != null) {
				dccAppalto.addColumn("W9APPA.DATA_VERB_AGGIUDICAZIONE", JdbcParametro.TIPO_DATA, tab29.getW3DVERB().getTime());
			}
			if (tab29.getW3FLAGRIC() != null) {
				dccAppalto.addColumn("W9APPA.FLAG_RICH_SUBAPPALTO", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab29.getW3FLAGRIC()).toString());
			}

			if (tab29.isSetW9APTIPAT()) {
				dccAppalto.addColumn("W9APPA.TIPO_ATTO", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab29.getW9APTIPAT().toString()));
			}
			if (tab29.isSetW9APDATAT()) {
				dccAppalto.addColumn("W9APPA.DATA_ATTO", JdbcParametro.TIPO_DATA, tab29.getW9APDATAT().getTime());
			}
			if (tab29.isSetW9APNUMAT()) {
				dccAppalto.addColumn("W9APPA.NUMERO_ATTO", JdbcParametro.TIPO_TESTO, tab29.getW9APNUMAT());
			}
		}
		dccAppalto.insert("W9APPA", this.sqlManager);

		// ciclo sulle imprese aggiudicatarie (W9AGGI)
		Tab81Type tab291 = null;
		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getListaTab291().sizeOfTab291Array(); i++) {
			tab291 = doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getListaTab291().getTab291Array(i);
			// popolamento dei dati per l'inserimento di un intervento (tab291):
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
			if (tab291 != null) {
				if (tab291.getW3IDTIPOA() != null) {
					dccAggiudicazione.addColumn("W9AGGI.ID_TIPOAGG", JdbcParametro.TIPO_NUMERICO, new Long(tab291.getW3IDTIPOA().toString()));
				}
				if (tab291.isSetW3RUOLO()) {
					dccAggiudicazione.addColumn("W9AGGI.RUOLO", JdbcParametro.TIPO_NUMERICO, new Long(tab291.getW3RUOLO().toString()));
				}
				if (tab291.getW3FLAGAVV() != null) {
					dccAggiudicazione.addColumn("W9AGGI.FLAG_AVVALIMENTO", JdbcParametro.TIPO_NUMERICO, new Long(tab291.getW3FLAGAVV().toString()));
				}
				if (tab291.getArch3() != null) {
					String pkImpresa = this.gestioneImpresa(tab291.getArch3(), pkUffint);
					dccAggiudicazione.addColumn("W9AGGI.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);
				}
				if (tab291.isSetArch3Avv()) {
					String pkImpresaAvv = this.gestioneImpresa(tab291.getArch3Avv(), pkUffint);
					dccAggiudicazione.addColumn("W9AGGI.CODIMP_AUSILIARIA", JdbcParametro.TIPO_TESTO, pkImpresaAvv);
				}
				if (tab291.isSetW3AGIDGRP()) {
					dccAggiudicazione.addColumn("W9AGGI.ID_GRUPPO", JdbcParametro.TIPO_NUMERICO, new Long(tab291.getW3AGIDGRP()));
				}
			}
			// si richiama l'inserimento dell'occorrenza di tab291 (W9AGGI)
			dccAggiudicazione.insert("W9AGGI", this.sqlManager);
		}

		// ciclo sulle imprese aggiudicatarie (W9INCA)
		Tab101Type tab292 = null;
		int k = 0;
		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getListaTab292().sizeOfTab292Array(); i++) {
			tab292 = doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getListaTab292().getTab292Array(i);
			// popolamento dei dati per l'inserimento di un intervento (tab292):
			// si usa
			// un altro container dato che non e' un singolo elemento ma una
			// lista di
			// elementi da inserire nella medesima tabella

			// si costruisce il container inserendo i campi chiave dell'entita'
			// da salvare
			String sezione = "RS";

			if (tab292 != null) {
				if (tab292.getW3SEZIONE().equals(sezione)) {
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

					if (tab292.getArch2() != null) {
						String pkTecnico = this.getTecnico(tab292.getArch2(), pkUffint);
						dccIncarichi.addColumn("W9INCA.CODTEC", JdbcParametro.TIPO_TESTO, pkTecnico);
					}
					if (tab292.getW3IDRUOLO() != null) {
						dccIncarichi.addColumn("W9INCA.ID_RUOLO", JdbcParametro.TIPO_NUMERICO, new Long(tab292.getW3IDRUOLO().toString()));
					}
					if (tab292.isSetW3CIGPROG()) {
						dccIncarichi.addColumn("W9INCA.CIG_PROG_ESTERNA", JdbcParametro.TIPO_TESTO, tab292.getW3CIGPROG());
					}
					if (tab292.isSetW3DATAAFF()) {
						dccIncarichi.addColumn("W9INCA.DATA_AFF_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab292.getW3DATAAFF().getTime());
					}
					if (tab292.isSetW3DATACON()) {
						dccIncarichi.addColumn("W9INCA.DATA_CONS_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab292.getW3DATACON().getTime());
					}

					// si richiama l'inserimento dell'occorrenza di tab292
					// (W9INCA)
					dccIncarichi.insert("W9INCA", this.sqlManager);
				}
			}
		}

		// ciclo sulle imprese aggiudicatarie (W9FINA)
		Tab293Type tab293 = null;
		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getListaTab293().sizeOfTab293Array(); i++) {
			tab293 = doc.getRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro().getListaTab293().getTab293Array(i);
			// popolamento dei dati per l'inserimento di un intervento (tab293):
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
			if (tab293 != null) {
				if (tab293.getW3IDFINAN() != null) {
					dccFinanziamenti.addColumn("W9FINA.ID_FINANZIAMENTO", JdbcParametro.TIPO_TESTO, tab293.getW3IDFINAN().toString());
				}
				if (tab293.getW3IFINANZ() != 0) {
					dccFinanziamenti.addColumn("W9FINA.IMPORTO_FINANZIAMENTO", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab293.getW3IFINANZ()).toString()));
				}
			}
			// si richiama l'inserimento dell'occorrenza di tab293 (W9INCA)
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