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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument;
import it.toscana.rete.rfc.sitat.types.Tab281Type;
import it.toscana.rete.rfc.sitat.types.Tab28Type;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della stipula accordo quadro.
 * 
 * @author Luca.Giacomazzo
 */
public class StipulaAccordoQuadroHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(StipulaAccordoQuadroHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Stipula accordo quadro";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_STIPULA_ACCORDO_QUADRO;
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument doc = (RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getFase();
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument doc = (RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getTest();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {

		RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument doc = (RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getFase();

		boolean continua = true;

		if (!super.existsLotto(fase)) {
			continua = false;
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di stipula accordo quadro di un lotto non presente in banca dati");
		}

		if (continua && !super.existsAppalto(fase)) {
			continua = false;
			logger.error("La richiesta di primo invio non ha un appalto associato al lotto " + "presente in DB (cig = " + fase.getW3FACIG() + ") ");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una stipula accordo quadro con appalto non esistente in banca dati");
		}

		if (continua && !super.existsAggiudicatari(fase)) {
			continua = false;
			logger.error("La richiesta di primo invio non ha una aggiudicazione associata al lotto " + "presente in DB (cig = " + fase.getW3FACIG() + ") ");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una Stipula accordo quadro con aggiudicazione non esistente in banca dati");
		}

		if (continua && existsIniz(fase)) {
			continua = false;
			logger.error("La richiesta di primo invio di una stipula accordo quadro con fase iniziale " + "non presente nel DB (cig = " + fase.getW3FACIG() + ") ");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una stipula accordo quadro gia' esistente in banca dati");
		}

		if (continua && !ignoreWarnings) {
			boolean isSAQ = UtilitySITAT.isSAQ(fase.getW3FACIG(), this.sqlManager);
			boolean isS2 = UtilitySITAT.isS2(fase.getW3FACIG(), this.sqlManager);
			boolean isE1 = this.isE1(fase.getW3FACIG(), this.sqlManager);
			boolean isOrd = UtilitySITAT.isOrd(fase.getW3FACIG(), this.sqlManager);

			if (!(isSAQ && isS2 && isOrd && !isE1)) {
				continua = false;
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di una stipula accordo quadro non prevista");
				if (!isSAQ) {
					logger.error("Richiesta di primo invio di una stipula accordo quadro relativa ad un " + "lotto che non corrisponde ad una stipula accordo quadro (TIPO_APP <> 9) (cig = "
							+ fase.getW3FACIG() + ")");
				}
				if (!isS2) {
					logger.error("Richiesta di primo invio di una stipula accordo quadro relativa ad un " + "lotto con importo < 40.000 (cig = " + fase.getW3FACIG() + ")");
				}
				if (!isOrd) {
					logger.error("Richiesta di primo invio di una stipula accordo quadro relativa ad un " + "lotto senza settori ordinari (cig = " + fase.getW3FACIG() + ")");
				}
				if (isE1) {
					logger.error("Richiesta di primo invio di una stipula accordo quadro relativa ad un " + "lotto escluso (cig = " + fase.getW3FACIG() + ")");
				}
			}
		}

		if (continua) {
			this.insertDatiFlusso(doc, datiAggiornamento, true);
		}
	}

	/**
	 * Inserimento dei dati del flusso in DB.
	 * 
	 * @param doc
	 *            RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument
	 * @param datiAggiornamento
	 *            DataColumnContainer
	 * @param isPrimoInvio
	 *            E' il primo invio?
	 * @throws SQLException
	 *             SQLException
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument doc, final DataColumnContainer datiAggiornamento, final boolean primoInvio) throws SQLException,
			GestoreException {

		Tab28Type tab28 = doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getTab28();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getFase();

		if (fase.getW3FACIG() != null) {
			Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
			Long codGara = longArray[0];
			Long codLott = longArray[1];

			this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

			DataColumnContainer datiIniz = new DataColumnContainer(new DataColumn[] { new DataColumn("W9INIZ.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
					new DataColumn("W9INIZ.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
					new DataColumn("W9INIZ.NUM_INIZ", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1))) });

			if (tab28 != null) {
				if (tab28.isSetW3DATASTI()) {
					datiIniz.addColumn("W9INIZ.DATA_STIPULA", JdbcParametro.TIPO_DATA, tab28.getW3DATASTI().getTime());
				}
				if (tab28.isSetW9INDECO()) {
					datiIniz.addColumn("W9INIZ.DATA_DECORRENZA", JdbcParametro.TIPO_DATA, tab28.getW9INDECO().getTime());
				}

				if (tab28.isSetW9INSCAD()) {
					datiIniz.addColumn("W9INIZ.DATA_SCADENZA", JdbcParametro.TIPO_DATA, tab28.getW9INSCAD().getTime());
				}
			}
			datiIniz.insert("W9INIZ", this.sqlManager);

			if (doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().isSetTab281()) {
				Tab281Type tab281 = doc.getRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro().getTab281();
				DataColumnContainer datiPues = new DataColumnContainer(new DataColumn[] { new DataColumn("W9PUES.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9PUES.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)) });
				datiPues.addColumn("W9PUES.NUM_INIZ", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW3NUM5()));
				datiPues.addColumn("W9PUES.NUM_PUES", JdbcParametro.TIPO_NUMERICO, new Long(1));
				if (tab281 != null) {
					if (tab281.isSetW3GUCE2()) {
						datiPues.addColumn("W9PUES.DATA_GUCE", JdbcParametro.TIPO_DATA, tab281.getW3GUCE2().getTime());
					}
					if (tab281.isSetW3GURI2()) {
						datiPues.addColumn("W9PUES.DATA_GURI", JdbcParametro.TIPO_DATA, tab281.getW3GURI2().getTime());
					}
					if (tab281.isSetW3NAZ2()) {
						datiPues.addColumn("W9PUES.QUOTIDIANI_NAZ", JdbcParametro.TIPO_NUMERICO, new Long(tab281.getW3NAZ2()));
					}
					if (tab281.isSetW3REG2()) {
						datiPues.addColumn("W9PUES.QUOTIDIANI_REG", JdbcParametro.TIPO_NUMERICO, new Long(tab281.getW3REG2()));
					}
					if (tab281.getW3PROFILO2() != null) {
						datiPues.addColumn("W9PUES.PROFILO_COMMITTENTE", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab281.getW3PROFILO2()).toString());
					}
					if (tab281.getW3MIN2() != null) {
						datiPues.addColumn("W9PUES.SITO_MINISTERO_INF_TRASP", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab281.getW3MIN2()).toString());
					}
					if (tab281.getW3OSS2() != null) {
						datiPues.addColumn("W9PUES.SITO_OSSERVATORIO_CP", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(tab281.getW3OSS2()).toString());
					}
				}
				if (((Long) sqlManager.getObject("select count(*) from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=? and NUM_PUES=1",
						new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) })).intValue() > 0) {
					sqlManager.update("delete from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=? and NUM_PUES=1", new Object[] { codGara, codLott, new Long(fase.getW3NUM5()) });
				}

				datiPues.insert("W9PUES", this.sqlManager);
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