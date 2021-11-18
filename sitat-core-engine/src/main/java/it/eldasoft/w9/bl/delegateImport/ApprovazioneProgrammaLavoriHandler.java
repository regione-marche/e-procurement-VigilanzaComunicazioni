package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.ListaTab2Bis1Type;
import it.toscana.rete.rfc.sitat.types.ListaTab3Type;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument;
import it.toscana.rete.rfc.sitat.types.Tab1LType;
import it.toscana.rete.rfc.sitat.types.Tab2Bis1Type;
import it.toscana.rete.rfc.sitat.types.Tab2LType;
import it.toscana.rete.rfc.sitat.types.Tab3Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della richiesta di approvazione di un programma
 * per una gara di appalto per Lavori.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class ApprovazioneProgrammaLavoriHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(ApprovazioneProgrammaLavoriHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Programma triennale lavori";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_APPROVAZIONE_PROGRAMMA_LAVORI;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument) documento;
		Tab1LType tab1 = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori().getTab1();

		// PRIMA SI EFFETTUANO TUTTI I CONTROLLI PRE AGGIORNAMENTO DB

		// verifica della non esistenza di un programma con lo stesso
		// identificativo
		// in banca dati
		if (this.existsProgramma(tab1)) {
			logger.error("La richiesta di primo invio ha un id (" + tab1.getT2IDCONTRI() + ") di un programma esistente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di un programma gia' esistente in banca dati");
			// si termina con questo errore
			return;
		}

		/*
		 * for (int i = 0; i <
		 * doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori
		 * ().getListaTab2().sizeOfTab2Array(); i++) { Tab2LType tab2 =
		 * doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori
		 * ().getListaTab2().getTab2Array(i);
		 * 
		 * if (tab2.isSetT2CATINT() && tab2.isSetT2INTERV()) { if
		 * (!this.existsCategoriaSottocategoriaIntervento(tab2)) {
		 * logger.error("L'intervento " + (i + 1) +
		 * " della richiesta ha il codice categoria (" +
		 * tab2.getT2INTERV().toString() + ") e sottocategoria (" +
		 * tab2.getT2CATINT() +
		 * ") non coerenti con l'elenco dei valori ammissibili");
		 * this.setEsito(datiAggiornamento,
		 * StatoComunicazione.STATO_ERRATA.getStato(),
		 * "Categoria/sottocategoria intervento non validi per l'intervento " +
		 * tab2.getT2CONINT()); // si termina con questo errore return; } } }
		 */

		// SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI
		// SI
		// AGGIORNA IL DB
		this.insertDatiFlusso(doc, datiAggiornamento);

	}

	/**
	 * Verifica se esiste in banca dati lo stesso programma a partire dall'id
	 * fornito nel tracciato.
	 * 
	 * @param tab1
	 *            elemento tab1 del flusso
	 * @return true se l'elemento esiste, false altrimenti
	 * @throws SQLException
	 */
	private boolean existsProgramma(final Tab1LType tab1) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(contri) from piatri where id = ?", new Object[] { tab1.getT2IDCONTRI() });
		return numeroOccorrenze.intValue() == 1;
	}

	/**
	 * Verifica se esiste in banca dati la coppia categoria/sottocategoria di
	 * intervento.
	 * 
	 * @param tab2
	 *            elemento tab2 del flusso
	 * @return true se l'elemento esiste, false altrimenti
	 * @throws SQLException
	 *             SQLException
	 */
	/*
	 * private boolean existsCategoriaSottocategoriaIntervento(final Tab2LType
	 * tab2) throws SQLException { Long numeroOccorrenze = (Long)
	 * this.sqlManager.getObject(
	 * "select count(tabdesc) from tabsche where tabcod=? and tabcod1=? and tabcod2=?"
	 * , new Object[] { "S2006", tab2.getT2INTERV().toString(),
	 * tab2.getT2CATINT() }); return numeroOccorrenze.intValue() == 1; }
	 */

	/**
	 * Effettua l'inserimento vero e proprio dei dati presenti nel flusso.
	 * 
	 * @param doc
	 *            documento XML della richiesta
	 * @param datiAggiornamento
	 *            container con i dati del flusso
	 * @throws SQLException
	 *             SQLException
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument doc, final DataColumnContainer datiAggiornamento) throws SQLException, GestoreException {

		Tab1LType tab1 = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori().getTab1();

		if (tab1 != null && tab1.getArch1() != null) {
			// estrazione della stazione appaltante di riferimento
			String pkUffint = this.getStazioneAppaltante(tab1.getArch1());
			if (tab1.getArch2() != null) {
				String pkTecni = this.getTecnico(tab1.getArch2(), pkUffint);

				// predisposizione dati PIATRI (tab1): i dati facoltativi
				// vengono testati
				// con il metodo .isSetXXX per verificare se nel tracciato sono
				// valorizzati
				Long idPiatri = new Long(this.genChiaviManager.getMaxId("PIATRI", "CONTRI") + 1);
				datiAggiornamento.addColumn("PIATRI.CONTRI", JdbcParametro.TIPO_NUMERICO, idPiatri);
				if (tab1.getT2IDCONTRI() != null) {
					datiAggiornamento.addColumn("PIATRI.ID", JdbcParametro.TIPO_TESTO, tab1.getT2IDCONTRI());
				}
				datiAggiornamento.addColumn("PIATRI.RESPRO", JdbcParametro.TIPO_TESTO, pkTecni);
				datiAggiornamento.addColumn("PIATRI.CENINT", JdbcParametro.TIPO_TESTO, pkUffint);
				datiAggiornamento.addColumn("PIATRI.TIPROG", JdbcParametro.TIPO_NUMERICO, new Long(1));
				datiAggiornamento.addColumn("PIATRI.NORMA", JdbcParametro.TIPO_NUMERICO, new Long(1));
				
				if (tab1.isSetT2AL1TRI()) {
					datiAggiornamento.addColumn("PIATRI.AL1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2AL1TRI());
				}
				if (tab1.isSetT2AL2TRI()) {
					datiAggiornamento.addColumn("PIATRI.AL2TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2AL2TRI());
				}
				if (tab1.isSetT2AL3TRI()) {
					datiAggiornamento.addColumn("PIATRI.AL3TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2AL3TRI());
				}
				datiAggiornamento.addColumn("PIATRI.ANNTRI", JdbcParametro.TIPO_NUMERICO, new Long(tab1.getT2ANNTRI()));
				if (tab1.isSetT2BI1TRI()) {
					datiAggiornamento.addColumn("PIATRI.BI1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2BI1TRI());
				}
				if (tab1.isSetT2BI2TRI()) {
					datiAggiornamento.addColumn("PIATRI.BI2TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2BI2TRI());
				}
				if (tab1.isSetT2BI3TRI()) {
					datiAggiornamento.addColumn("PIATRI.BI3TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2BI3TRI());
				}
				if (tab1.isSetT2DV1TRI()) {
					datiAggiornamento.addColumn("PIATRI.DV1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2DV1TRI());
				}
				if (tab1.isSetT2DV2TRI()) {
					datiAggiornamento.addColumn("PIATRI.DV2TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2DV2TRI());
				}
				if (tab1.isSetT2DV3TRI()) {
					datiAggiornamento.addColumn("PIATRI.DV3TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2DV3TRI());
				}
				if (tab1.isSetT2IM1TRI()) {
					datiAggiornamento.addColumn("PIATRI.IM1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2IM1TRI());
				}
				if (tab1.isSetT2IM2TRI()) {
					datiAggiornamento.addColumn("PIATRI.IM2TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2IM2TRI());
				}
				if (tab1.isSetT2IM3TRI()) {
					datiAggiornamento.addColumn("PIATRI.IM3TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2IM3TRI());
				}
				if (tab1.isSetT2MU1TRI()) {
					datiAggiornamento.addColumn("PIATRI.MU1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2MU1TRI());
				}
				if (tab1.isSetT2MU2TRI()) {
					datiAggiornamento.addColumn("PIATRI.MU2TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2MU2TRI());
				}
				if (tab1.isSetT2MU3TRI()) {
					datiAggiornamento.addColumn("PIATRI.MU3TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2MU3TRI());
				}
				if (tab1.isSetT2PR1TRI()) {
					datiAggiornamento.addColumn("PIATRI.PR1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2PR1TRI());
				}
				if (tab1.isSetT2PR2TRI()) {
					datiAggiornamento.addColumn("PIATRI.PR2TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2PR2TRI());
				}
				if (tab1.isSetT2PR3TRI()) {
					datiAggiornamento.addColumn("PIATRI.PR3TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2PR3TRI());
				}
				if (tab1.isSetT2TO1TRI()) {
					datiAggiornamento.addColumn("PIATRI.TO1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2TO1TRI());
				}
				if (tab1.isSetT2TO2TRI()) {
					datiAggiornamento.addColumn("PIATRI.TO2TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2TO2TRI());
				}
				if (tab1.isSetT2TO3TRI()) {
					datiAggiornamento.addColumn("PIATRI.TO3TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2TO3TRI());
				}
				if (tab1.isSetT2BRETRI()) {
					datiAggiornamento.addColumn("PIATRI.BRETRI", JdbcParametro.TIPO_TESTO, tab1.getT2BRETRI());
				}
				if (tab1.isSetT2IMPACC()) {
					datiAggiornamento.addColumn("PIATRI.IMPACC", JdbcParametro.TIPO_DECIMALE, tab1.getT2IMPACC());
				}

				// aggiornamento del campo BLOB per il PDF del file allegato
				ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
				try {
					fileAllegato.write(tab1.getFile());
				} catch (IOException e) {
					throw new GestoreException("Errore durante la lettura del file allegato presente nella richiesta XML", "", e);
				}
				datiAggiornamento.addColumn("PIATRI.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, fileAllegato);

				// inserimento in piatri
				datiAggiornamento.insert("PIATRI", this.sqlManager);

				// ciclo sugli interventi (INTTRI)
				Tab2LType tab2 = null;
				for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori().getListaTab2().sizeOfTab2Array(); i++) {
					tab2 = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori().getListaTab2().getTab2Array(i);
					// popolamento dei dati per l'inserimento di un intervento
					// (tab2): si usa
					// un altro container dato che non e' un singolo elemento ma
					// una lista di
					// elementi da inserire nella medesima tabella

					if (tab2 != null) {
						// si costruisce il container inserendo i campi chiave
						// dell'entita' da salvare
						DataColumnContainer dccIntervento = new DataColumnContainer(new DataColumn[] { new DataColumn("INTTRI.CONTRI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, idPiatri)),
								new DataColumn("INTTRI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, tab2.getT2CONINT())) });

						// si aggiungono gli altri dati al container: per i
						// campi
						// non obbligatori
						// da tracciato si effettua il test con il
						// corrispondente
						// metodo isSetXXX

						if (tab2.getT2ANNINT() != null) {
							dccIntervento.addColumn("INTTRI.ANNINT", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab2.getT2ANNINT()));
						}
						dccIntervento.addColumn("INTTRI.ANNRIF", JdbcParametro.TIPO_NUMERICO, new Long(tab2.getT2ANNRIF()));
						if (tab2.getT2DESINT() != null) {
							dccIntervento.addColumn("INTTRI.DESINT", JdbcParametro.TIPO_TESTO, tab2.getT2DESINT());
						}
						if (tab2.isSetT2CATINT()) {
							dccIntervento.addColumn("INTTRI.CATINT", JdbcParametro.TIPO_TESTO, tab2.getT2CATINT().toString());
						}
						double ditint = 0;
						if (tab2.isSetT2DI1INT()) {
							dccIntervento.addColumn("INTTRI.DI1INT", JdbcParametro.TIPO_DECIMALE, tab2.getT2DI1INT());
							ditint += tab2.getT2DI1INT();
						}
						if (tab2.isSetT2DI2INT()) {
							dccIntervento.addColumn("INTTRI.DI2INT", JdbcParametro.TIPO_DECIMALE, tab2.getT2DI2INT());
							ditint += tab2.getT2DI2INT();
						}
						if (tab2.isSetT2DI3INT()) {
							dccIntervento.addColumn("INTTRI.DI3INT", JdbcParametro.TIPO_DECIMALE, tab2.getT2DI3INT());
							ditint += tab2.getT2DI3INT();
						}
						dccIntervento.addColumn("INTTRI.DITINT", JdbcParametro.TIPO_DECIMALE, ditint);
						if (tab2.isSetT2IAL1TRI()) {
							dccIntervento.addColumn("INTTRI.AL1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IAL1TRI());
						}
						if (tab2.isSetT2IAL2TRI()) {
							dccIntervento.addColumn("INTTRI.AL2TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IAL2TRI());
						}
						if (tab2.isSetT2IAL3TRI()) {
							dccIntervento.addColumn("INTTRI.AL3TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IAL3TRI());
						}
						if (tab2.isSetT2IBI1TRI()) {
							dccIntervento.addColumn("INTTRI.BI1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IBI1TRI());
						}
						if (tab2.isSetT2IBI2TRI()) {
							dccIntervento.addColumn("INTTRI.BI2TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IBI2TRI());
						}
						if (tab2.isSetT2IBI3TRI()) {
							dccIntervento.addColumn("INTTRI.BI3TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IBI3TRI());
						}
						if (tab2.isSetT2ICPINT()) {
							dccIntervento.addColumn("INTTRI.ICPINT", JdbcParametro.TIPO_DECIMALE, tab2.getT2ICPINT());
						}
						if (tab2.isSetT2IDV1TRI()) {
							dccIntervento.addColumn("INTTRI.DV1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IDV1TRI());
						}
						if (tab2.isSetT2IDV2TRI()) {
							dccIntervento.addColumn("INTTRI.DV2TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IDV2TRI());
						}
						if (tab2.isSetT2IDV3TRI()) {
							dccIntervento.addColumn("INTTRI.DV3TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IDV3TRI());
						}
						if (tab2.isSetT2IIM1TRI()) {
							dccIntervento.addColumn("INTTRI.IM1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IIM1TRI());
						}
						if (tab2.isSetT2IIM2TRI()) {
							dccIntervento.addColumn("INTTRI.IM2TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IIM2TRI());
						}
						if (tab2.isSetT2IIM3TRI()) {
							dccIntervento.addColumn("INTTRI.IM3TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IIM3TRI());
						}
						if (tab2.isSetT2IMU1TRI()) {
							dccIntervento.addColumn("INTTRI.MU1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IMU1TRI());
						}
						if (tab2.isSetT2IMU2TRI()) {
							dccIntervento.addColumn("INTTRI.MU2TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IMU2TRI());
						}
						if (tab2.isSetT2IMU3TRI()) {
							dccIntervento.addColumn("INTTRI.MU3TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IMU3TRI());
						}
						if (tab2.isSetT2INTERV()) {
							dccIntervento.addColumn("INTTRI.INTERV", JdbcParametro.TIPO_TESTO, tab2.getT2INTERV().toString());
						}
						if (tab2.isSetT2IPR1TRI()) {
							dccIntervento.addColumn("INTTRI.PR1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IPR1TRI());
						}
						if (tab2.isSetT2IPR2TRI()) {
							dccIntervento.addColumn("INTTRI.PR2TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IPR2TRI());
						}
						if (tab2.isSetT2IPR3TRI()) {
							dccIntervento.addColumn("INTTRI.PR3TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IPR3TRI());
						}
						if (tab2.isSetT2TCPINT()) {
							dccIntervento.addColumn("INTTRI.TCPINT", JdbcParametro.TIPO_TESTO, tab2.getT2TCPINT());
						}
						if (tab2.isSetT2NUTS()) {
							dccIntervento.addColumn("INTTRI.NUTS", JdbcParametro.TIPO_TESTO, tab2.getT2NUTS());
						}
						if (tab2.isSetT2CODCPV()) {
							dccIntervento.addColumn("INTTRI.CODCPV", JdbcParametro.TIPO_TESTO, tab2.getT2CODCPV());
						}
						if (tab2.isSetT2AFLINT()) {
							dccIntervento.addColumn("INTTRI.AFLINT", JdbcParametro.TIPO_NUMERICO, new Long(tab2.getT2AFLINT()));
						}
						if (tab2.isSetT2AILINT()) {
							dccIntervento.addColumn("INTTRI.AILINT", JdbcParametro.TIPO_NUMERICO, new Long(tab2.getT2AILINT()));
						}
						if (tab2.isSetT2APCINT()) {
							dccIntervento.addColumn("INTTRI.APCINT", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab2.getT2APCINT()));
						}
						if (tab2.isSetT2COMINT()) {
							dccIntervento.addColumn("INTTRI.COMINT", JdbcParametro.TIPO_TESTO, tab2.getT2COMINT().toString());
						}
						if (tab2.isSetT2FIINTR()) {
							dccIntervento.addColumn("INTTRI.FIINTR", JdbcParametro.TIPO_TESTO, tab2.getT2FIINTR().toString());
						}
						if (tab2.isSetT2PRGINT()) {
							dccIntervento.addColumn("INTTRI.PRGINT", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab2.getT2PRGINT().toString()));
						}
						if (tab2.getT2SEZINT() != null) {
							dccIntervento.addColumn("INTTRI.SEZINT", JdbcParametro.TIPO_TESTO, tab2.getT2SEZINT().toString());
						}
						if (tab2.isSetT2STAPRO()) {
							dccIntervento.addColumn("INTTRI.STAPRO", JdbcParametro.TIPO_TESTO, tab2.getT2STAPRO());
						}
						if (tab2.isSetT2TFLINT()) {
							dccIntervento.addColumn("INTTRI.TFLINT", JdbcParametro.TIPO_NUMERICO, new Long(tab2.getT2TFLINT()));
						}
						if (tab2.isSetT2TILINT()) {
							dccIntervento.addColumn("INTTRI.TILINT", JdbcParametro.TIPO_NUMERICO, new Long(tab2.getT2TILINT()));
						}
						dccIntervento.addColumn("INTTRI.TOTINT", JdbcParametro.TIPO_DECIMALE, tab2.getT2TOTINT());
						if (tab2.isSetT2URCINT()) {
							dccIntervento.addColumn("INTTRI.URCINT", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab2.getT2URCINT()));
						}
						if (tab2.isSetT2CODINT()) {
							dccIntervento.addColumn("INTTRI.CODINT", JdbcParametro.TIPO_TESTO, tab2.getT2CODINT());
						}
						if (tab2.isSetT2CUPPRG()) {
							dccIntervento.addColumn("INTTRI.CUPPRG", JdbcParametro.TIPO_TESTO, tab2.getT2CUPPRG());
						}
						if (tab2.isSetT2NPROGINT()) {
							dccIntervento.addColumn("INTTRI.NPROGINT", JdbcParametro.TIPO_NUMERICO, new Long(tab2.getT2NPROGINT()));
						}

						if (tab2.isSetArch2()) {
							String codTec = this.getTecnico(tab2.getArch2(), pkUffint);
							if (codTec != null && codTec.length() > 0) {
								dccIntervento.addColumn("INTTRI.CODRUP", JdbcParametro.TIPO_TESTO, codTec);
							}
						}

						// si richiama l'inserimento dell'occorrenza di tab2
						// (INTTRI)
						dccIntervento.insert("INTTRI", this.sqlManager);

						if (tab2.isSetListaTab3()) {
							// si cicla sugli immobili dell'intervento , ovvero
							// la
							// tabella tab3
							// (IMMTRAI)
							ListaTab3Type listaTab3 = tab2.getListaTab3();
							Tab3Type tab3 = null;
							for (int j = 0; j < listaTab3.sizeOfTab3Array(); j++) {
								tab3 = listaTab3.getTab3Array(j);

								if (tab3 != null) {
									// si costruisce il container inserendo i
									// campi
									// chiave dell'entita' da
									// salvare
									DataColumnContainer dccImmobile = new DataColumnContainer(new DataColumn[] {
											new DataColumn("IMMTRAI.CONTRI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, idPiatri)),
											new DataColumn("IMMTRAI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, tab2.getT2CONINT())),
											new DataColumn("IMMTRAI.NUMIMM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, tab3.getT2INUMIMM())) });

									// si aggiungono gli altri dati al
									// container:
									// per i
									// campi non
									// obbligatori da tracciato si effettua il
									// test
									// con
									// il corrispondente
									// metodo isSetXXX
									if (tab3.isSetT2IANNIMM()) {
										dccImmobile.addColumn("IMMTRAI.ANNIMM", JdbcParametro.TIPO_NUMERICO, new Long(tab3.getT2IANNIMM()));
									}
									if (tab3.isSetT2IDESIMM()) {
										dccImmobile.addColumn("IMMTRAI.DESIMM", JdbcParametro.TIPO_TESTO, tab3.getT2IDESIMM());
									}
									if (tab3.isSetT2IPROIMM()) {
										dccImmobile.addColumn("IMMTRAI.PROIMM", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab3.getT2IPROIMM().toString()));
									}
									if (tab3.isSetT2IVALIMM()) {
										dccImmobile.addColumn("IMMTRAI.VALIMM", JdbcParametro.TIPO_DECIMALE, tab3.getT2IVALIMM());
									}
									// si richiama l'inserimento dell'occorrenza
									// di
									// tab3
									// (IMMTRAI)
									dccImmobile.insert("IMMTRAI", this.sqlManager);
								}
							}
						}
					}
				}

				// Importazione dei lavori in economia (ECOTRI)
				ListaTab2Bis1Type listaTab2Bis1 = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori().getListaTab21();
				if (listaTab2Bis1 != null && listaTab2Bis1.sizeOfTab2Array() > 0) {
					for (int j = 0; j < listaTab2Bis1.sizeOfTab2Array(); j++) {
						Tab2Bis1Type tab2bis1 = listaTab2Bis1.getTab2Array(j);

						// si costruisce il container inserendo i campi chiave
						// dell'entita' da
						// salvare
						DataColumnContainer dccEcoTri = new DataColumnContainer(new DataColumn[] { new DataColumn("ECOTRI.CONTRI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, idPiatri)),
								new DataColumn("ECOTRI.CONECO", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, tab2bis1.getT2ECONECO())),
								new DataColumn("ECOTRI.DESCRI", new JdbcParametro(JdbcParametro.TIPO_TESTO, tab2bis1.getT2EDESCRI())),
								new DataColumn("ECOTRI.STIMA", new JdbcParametro(JdbcParametro.TIPO_DECIMALE, tab2bis1.getT2ESTIMA())) });

						// si aggiungono gli altri dati al container: per i
						// campi non
						// obbligatori da tracciato si effettua il test con il
						// corrispondente
						// metodo isSetXXX
						if (tab2bis1.isSetT2ECUPPRG()) {
							dccEcoTri.addColumn("ECOTRI.CUPPRG", JdbcParametro.TIPO_TESTO, tab2bis1.getT2ECUPPRG());
						}
						// si richiama l'inserimento dell'occorrenza di tab3
						// (ECOTRI)
						dccEcoTri.insert("ECOTRI", this.sqlManager);
					}
				}
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
