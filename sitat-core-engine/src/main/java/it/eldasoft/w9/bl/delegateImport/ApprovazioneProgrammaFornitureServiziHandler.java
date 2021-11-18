package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument;
import it.toscana.rete.rfc.sitat.types.Tab1FSType;
import it.toscana.rete.rfc.sitat.types.Tab1Type;
import it.toscana.rete.rfc.sitat.types.Tab2FSType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della richiesta di approvazione di un programma
 * per una gara di appalto per Forniture o Servizi.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class ApprovazioneProgrammaFornitureServiziHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(ApprovazioneProgrammaFornitureServiziHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Programma annuale forniture/servizi";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_APPROVAZIONE_PROGRAMMA_FORNITURE_SERVIZI;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServizi().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServizi().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument) documento;
		Tab1Type tab1 = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServizi().getTab1();

		if (this.existsProgramma(tab1)) {
			logger.error("La richiesta di primo invio ha un id (" + tab1.getT2IDCONTRI() + ") di un programma esistente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di un programma gia' esistente in banca dati");
			// si termina con questo errore
			return;
		}

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
	 *             SQLException
	 */
	private boolean existsProgramma(final Tab1Type tab1) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(contri) from piatri where id = ?", new Object[] { tab1.getT2IDCONTRI() });
		return numeroOccorrenze.intValue() == 1;
	}

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
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument doc, final DataColumnContainer datiAggiornamento) throws SQLException,
			GestoreException {

		Tab1FSType tab1 = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServizi().getTab1();

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
				datiAggiornamento.addColumn("PIATRI.TIPROG", JdbcParametro.TIPO_NUMERICO, new Long(2));
				datiAggiornamento.addColumn("PIATRI.NORMA", JdbcParametro.TIPO_NUMERICO, new Long(1));
				
				if (tab1.isSetT2AL1TRI()) {
					datiAggiornamento.addColumn("PIATRI.AL1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2AL1TRI());
				}
				datiAggiornamento.addColumn("PIATRI.ANNTRI", JdbcParametro.TIPO_NUMERICO, new Long(tab1.getT2ANNTRI()));
				if (tab1.isSetT2BI1TRI()) {
					datiAggiornamento.addColumn("PIATRI.BI1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2BI1TRI());
				}
				if (tab1.isSetT2TO1TRI()) {
					datiAggiornamento.addColumn("PIATRI.TO1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2TO1TRI());
				}
				if (tab1.isSetFSRG1TRI()) {
					datiAggiornamento.addColumn("PIATRI.RG1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getFSRG1TRI());
				}
				if (tab1.isSetFSSU2TRI()) {
					datiAggiornamento.addColumn("PIATRI.IMPRFS", JdbcParametro.TIPO_DECIMALE, tab1.getFSSU2TRI());
				}
				if (tab1.isSetT2BRETRI()) {
					datiAggiornamento.addColumn("PIATRI.BRETRI", JdbcParametro.TIPO_TESTO, tab1.getT2BRETRI());
				}
				if (tab1.isSetT2MU1TRI()) {
					datiAggiornamento.addColumn("PIATRI.MU1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2MU1TRI());
				}
				if (tab1.isSetT2PR1TRI()) {
					datiAggiornamento.addColumn("PIATRI.PR1TRI", JdbcParametro.TIPO_DECIMALE, tab1.getT2PR1TRI());
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
				Tab2FSType tab2 = null;
				for (int i = 0; i < doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServizi().getListaTab2().sizeOfTab2Array(); i++) {
					tab2 = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServizi().getListaTab2().getTab2Array(i);
					// popolamento dei dati per l'inserimento di un intervento
					// (tab2): si usa
					// un altro container dato che non e' un singolo elemento ma
					// una lista di
					// elementi da inserire nella medesima tabella

					if (tab2 != null) {
						// si costruisce il container inserendo i campi chiave
						// dell'entita' da
						// salvare
						DataColumnContainer dccIntervento = new DataColumnContainer(new DataColumn[] { new DataColumn("INTTRI.CONTRI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, idPiatri)),
								new DataColumn("INTTRI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, tab2.getT2CONINT())) });

						// si aggiungono gli altri dati al container: per i
						// campi
						// non obbligatori
						// da tracciato si effettua il test con il
						// corrispondente
						// metodo isSetXXX
						if (tab2.isSetArch2()) {
							String pkTecniRup = this.getTecnico(tab2.getArch2(), pkUffint);
							dccIntervento.addColumn("INTTRI.CODRUP", JdbcParametro.TIPO_TESTO, pkTecniRup);
						}
						if (tab2.getT2DESINT() != null) {
							dccIntervento.addColumn("INTTRI.DESINT", JdbcParametro.TIPO_TESTO, tab2.getT2DESINT());
						}
						if (tab2.isSetT2DITINT()) {
							dccIntervento.addColumn("INTTRI.DITINT", JdbcParametro.TIPO_DECIMALE, tab2.getT2DITINT());
							dccIntervento.addColumn("INTTRI.DI1INT", JdbcParametro.TIPO_DECIMALE, tab2.getT2DITINT());
						}
						if (tab2.isSetT2IAL1TRI()) {
							dccIntervento.addColumn("INTTRI.AL1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IAL1TRI());
						}
						if (tab2.isSetT2IBI1TRI()) {
							dccIntervento.addColumn("INTTRI.BI1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IBI1TRI());
						}
						if (tab2.isSetT2NUTS()) {
							dccIntervento.addColumn("INTTRI.NUTS", JdbcParametro.TIPO_TESTO, tab2.getT2NUTS());
						}
						if (tab2.isSetT2CODCPV()) {
							dccIntervento.addColumn("INTTRI.CODCPV", JdbcParametro.TIPO_TESTO, tab2.getT2CODCPV());
						}
						if (tab2.isSetT2COMINT()) {
							dccIntervento.addColumn("INTTRI.COMINT", JdbcParametro.TIPO_TESTO, tab2.getT2COMINT().toString());
						}
						if (tab2.isSetT2PRGINT()) {
							dccIntervento.addColumn("INTTRI.PRGINT", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab2.getT2PRGINT().toString()));
						}
						if (tab2.isSetSUIAL1TRI()) {
							dccIntervento.addColumn("INTTRI.IMPRFS", JdbcParametro.TIPO_DECIMALE, tab2.getSUIAL1TRI());
						}
						if (tab2.getFSTIPO() != null) {
							dccIntervento.addColumn("INTTRI.TIPOIN", JdbcParametro.TIPO_TESTO, tab2.getFSTIPO().toString());
						}
						if (tab2.getFSMESE() != null) {
							dccIntervento.addColumn("INTTRI.MESEIN", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab2.getFSMESE().toString()));
						}
						if (tab2.isSetFSNORMA()) {
							dccIntervento.addColumn("INTTRI.NORRIF", JdbcParametro.TIPO_TESTO, tab2.getFSNORMA());
						}
						if (tab2.isSetFSSTRUM()) {
							dccIntervento.addColumn("INTTRI.STRUPR", JdbcParametro.TIPO_TESTO, tab2.getFSSTRUM());
						}
						if (tab2.isSetFSIRISREG()) {
							dccIntervento.addColumn("INTTRI.RG1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getFSIRISREG());
						}
						if (tab2.getFSMANOTRI() != null) {
							dccIntervento.addColumn("INTTRI.MANTRI", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab2.getFSMANOTRI()).toString());
						}
						if (tab2.isSetT2CODINT()) {
							dccIntervento.addColumn("INTTRI.CODINT", JdbcParametro.TIPO_TESTO, tab2.getT2CODINT());
						}
						if (tab2.isSetT2IMU1TRI()) {
							dccIntervento.addColumn("INTTRI.PR1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IMU1TRI());
						}
						if (tab2.isSetT2IPR1TRI()) {
							dccIntervento.addColumn("INTTRI.MU1TRI", JdbcParametro.TIPO_DECIMALE, tab2.getT2IPR1TRI());
						}
						if (tab2.isSetT2CUPPRG()) {
							dccIntervento.addColumn("INTTRI.CUPPRG", JdbcParametro.TIPO_TESTO, tab2.getT2CUPPRG());
						}

						// si richiama l'inserimento dell'occorrenza di tab2
						// (INTTRI)
						dccIntervento.insert("INTTRI", this.sqlManager);
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