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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document;
import it.toscana.rete.rfc.sitat.types.Tab101Type;
import it.toscana.rete.rfc.sitat.types.Tab81Type;
import it.toscana.rete.rfc.sitat.types.Tab8Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della aggiudicazione appalto sotto 150000.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class AggiudicazioneAppaltoSotto150000Handler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(AggiudicazioneAppaltoSotto150000Handler.class);

	@Override
	protected String getNomeFlusso() {
		return "Fase semplificata di aggiudicazione appalto";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_AGGIUDICAZIONE_APPALTO_SOTTO_150000;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc = (RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di aggiudicazione semplificata appalto di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto gia' appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di aggiudicazione semplificata appalto di un lotto gia' appaltato in banca dati");
			// si termina con questo errore
			return;
		}

		// warnings
		if (!ignoreWarnings) {
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);

			if ((!isE1 && isS2)) {
				logger.error("La richiesta di primo invio ha un lotto con importo maggiore o uguale a 40000 e non escluso");
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di aggiudicazione semplificata non prevista");
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
	 * @throws SQLException
	 * @throws GestoreException
	 */
	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document doc, DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException,
			GestoreException {

		Tab8Type tab8 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getTab8();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getFase();

		Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
		Long codGara = longArray[0];
		Long codLott = longArray[1];
		String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

		this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

		// predisposizione dati W9APPA (tab8): i dati facoltativi vengono
		// testati
		// con il metodo .isSetXXX per verificare se nel tracciato sono
		// valorizzati
		DataColumnContainer dccAppalto = new DataColumnContainer(new DataColumn[] { new DataColumn("W9APPA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
				new DataColumn("W9APPA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)), new DataColumn("W9APPA.NUM_APPA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, 1)) });

		if (tab8 != null) {
			if (tab8.getW3DVERB() != null) {
				dccAppalto.addColumn("W9APPA.DATA_VERB_AGGIUDICAZIONE", JdbcParametro.TIPO_DATA, tab8.getW3DVERB().getTime());
			}
			if (tab8.isSetW3IMPAGGI()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_AGGIUDICAZIONE", JdbcParametro.TIPO_DECIMALE, tab8.getW3IMPAGGI());
			}
			if (tab8.getW3IDISPOS() > 0) {
				dccAppalto.addColumn("W9APPA.IMPORTO_DISPOSIZIONE", JdbcParametro.TIPO_DECIMALE, tab8.getW3IDISPOS());
			}
			if (tab8.isSetW3ASTAELE()) {
				dccAppalto.addColumn("W9APPA.ASTA_ELETTRONICA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab8.getW3ASTAELE()).toString());
			}
			if (tab8.isSetW3PERCRIB()) {
				dccAppalto.addColumn("W9APPA.PERC_RIBASSO_AGG", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab8.getW3PERCRIB())).toString());
			}
			if (tab8.isSetW3PERCOFF()) {
				dccAppalto.addColumn("W9APPA.PERC_OFF_AUMENTO", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab8.getW3PERCOFF())).toString());
			}
			if (tab8.getW3ICAPPA() > 0) {
				dccAppalto.addColumn("W9APPA.IMPORTO_COMPL_APPALTO", JdbcParametro.TIPO_DECIMALE, tab8.getW3ICAPPA());
			}
			if (tab8.getW3ICINTE() > 0) {
				dccAppalto.addColumn("W9APPA.IMPORTO_COMPL_INTERVENTO", JdbcParametro.TIPO_DECIMALE, tab8.getW3ICINTE());
			}
			if (tab8.getW3ISUBTOT() > 0) {
				dccAppalto.addColumn("W9APPA.IMPORTO_SUBTOTALE", JdbcParametro.TIPO_DECIMALE, tab8.getW3ISUBTOT());
			}
			if (tab8.getW3IATTSIC() > 0) {
				dccAppalto.addColumn("W9APPA.IMPORTO_ATTUAZIONE_SICUREZZA", JdbcParametro.TIPO_DECIMALE, tab8.getW3IATTSIC());
			}
			if (tab8.isSetW9APTIPAT()) {
				dccAppalto.addColumn("W9APPA.TIPO_ATTO", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab8.getW9APTIPAT().toString()));
			}
			if (tab8.isSetW9APDATAT()) {
				dccAppalto.addColumn("W9APPA.DATA_ATTO", JdbcParametro.TIPO_DATA, tab8.getW9APDATAT().getTime());
			}
			if (tab8.isSetW9APNUMAT()) {
				dccAppalto.addColumn("W9APPA.NUMERO_ATTO", JdbcParametro.TIPO_TESTO, tab8.getW9APNUMAT());
			}
			if (tab8.isSetW9APIVA()) {
				dccAppalto.addColumn("W9APPA.IVA", JdbcParametro.TIPO_DECIMALE, new Double(new Float(tab8.getW9APIVA()).toString()));
			}
			if (tab8.isSetW9APIMPIVA()) {
				dccAppalto.addColumn("W9APPA.IMPORTO_IVA", JdbcParametro.TIPO_DECIMALE, tab8.getW9APIMPIVA());
			}
			if (tab8.getW9APDATASTI() != null) {
				dccAppalto.addColumn("W9APPA.DATA_STIPULA", JdbcParametro.TIPO_DATA, tab8.getW9APDATASTI().getTime());
			}
			if (tab8.getW9APDURCON() > 0) {
				dccAppalto.addColumn("W9APPA.DURATA_CON", JdbcParametro.TIPO_NUMERICO, new Long(tab8.getW9APDURCON()));
			}
		}
		dccAppalto.insert("W9APPA", this.sqlManager);

		// ciclo sulle imprese aggiudicatarie (W9AGGI)
		Tab81Type tab81 = null;
		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getListaTab81().sizeOfTab81Array(); i++) {
			tab81 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getListaTab81().getTab81Array(i);
			// popolamento dei dati per l'inserimento di un intervento (tab81):
			// si usa
			// un altro container dato che non e' un singolo elemento ma una
			// lista di
			// elementi da inserire nella medesima tabella

			// si costruisce il container inserendo i campi chiave dell'entita'
			// da
			// salvare
			DataColumnContainer dccAggiudicazione = new DataColumnContainer(new DataColumn[] { new DataColumn("W9AGGI.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9AGGI.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)), new DataColumn("W9AGGI.NUM_APPA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, 1)),
					new DataColumn("W9AGGI.NUM_AGGI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, i + 1)) });

			// si aggiungono gli altri dati al container: per i campi non
			// obbligatori
			// da tracciato si effettua il test con il corrispondente metodo
			// isSetXXX
			if (tab81 != null) {
				if (tab81.getW3IDTIPOA() != null) {
					dccAggiudicazione.addColumn("W9AGGI.ID_TIPOAGG", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab81.getW3IDTIPOA().toString()));
				}
				if (tab81.isSetW3RUOLO()) {
					dccAggiudicazione.addColumn("W9AGGI.RUOLO", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab81.getW3RUOLO().toString()));
				}

				if (tab81.isSetW3FLAGAVV()) {
					dccAggiudicazione.addColumn("W9AGGI.FLAG_AVVALIMENTO", JdbcParametro.TIPO_TESTO, tab81.getW3FLAGAVV().toString());
				}

				if (tab81.getArch3() != null) {
					String pkImpresa = this.gestioneImpresa(tab81.getArch3(), pkUffint);
					dccAggiudicazione.addColumn("W9AGGI.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);
				}
				if (tab81.isSetArch3Avv()) {
					String pkImpresaAvv = this.gestioneImpresa(tab81.getArch3Avv(), pkUffint);
					dccAggiudicazione.addColumn("W9AGGI.CODIMP_AUSILIARIA", JdbcParametro.TIPO_TESTO, pkImpresaAvv);
				}

				if (tab81.isSetW3AGIDGRP()) {
					dccAggiudicazione.addColumn("W9AGGI.ID_GRUPPO", JdbcParametro.TIPO_NUMERICO, new Long(tab81.getW3AGIDGRP()));
				}
			}
			// si richiama l'inserimento dell'occorrenza di tab81 (W9AGGI)
			dccAggiudicazione.insert("W9AGGI", this.sqlManager);
		}

		Tab101Type tab101 = null;
		int k = 0;
		for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getListaTab82().sizeOfTab82Array(); i++) {
			tab101 = doc.getRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000().getListaTab82().getTab82Array(i);

			// si costruisce il container inserendo i campi chiave dell'entita'
			// da salvare
			if (tab101 != null) {
				if (tab101.getW3SEZIONE().equals("RE") || tab101.getW3SEZIONE().equals("RS")) {
					k = k + 1;
					DataColumnContainer dccIncarichi = new DataColumnContainer(new DataColumn[] { new DataColumn("W9INCA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
							new DataColumn("W9INCA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
							new DataColumn("W9INCA.NUM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()))),
							new DataColumn("W9INCA.NUM_INCA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(k))),
							new DataColumn("W9INCA.SEZIONE", new JdbcParametro(JdbcParametro.TIPO_TESTO, tab101.getW3SEZIONE())) });

					// si aggiungono gli altri dati al container: per i campi
					// non
					// obbligatori
					// da tracciato si effettua il test con il corrispondente
					// metodo
					// isSetXXX
					if (tab101.getArch2() != null) {
						String pkTecnico = this.getTecnico(tab101.getArch2(), pkUffint);
						dccIncarichi.addColumn("W9INCA.CODTEC", JdbcParametro.TIPO_TESTO, pkTecnico);
					}
					if (tab101.getW3IDRUOLO() != null) {
						dccIncarichi.addColumn("W9INCA.ID_RUOLO", JdbcParametro.TIPO_NUMERICO, new Long(tab101.getW3IDRUOLO().toString()));
					}
					if (tab101.isSetW3CIGPROG()) {
						dccIncarichi.addColumn("W9INCA.CIG_PROG_ESTERNA", JdbcParametro.TIPO_TESTO, tab101.getW3CIGPROG());
					}
					if (tab101.isSetW3DATAAFF()) {
						dccIncarichi.addColumn("W9INCA.DATA_AFF_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab101.getW3DATAAFF().getTime());
					}
					if (tab101.isSetW3DATACON()) {
						dccIncarichi.addColumn("W9INCA.DATA_CONS_PROG_ESTERNA", JdbcParametro.TIPO_DATA, tab101.getW3DATACON().getTime());
					}
					// si richiama l'inserimento dell'occorrenza di tab101
					// (W9INCA)
					dccIncarichi.insert("W9INCA", this.sqlManager);
				}
			}
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