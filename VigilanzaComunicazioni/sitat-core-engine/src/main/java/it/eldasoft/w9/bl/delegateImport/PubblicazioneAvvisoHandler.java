package it.eldasoft.w9.bl.delegateImport;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.toscana.rete.rfc.sitat.types.FaseType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument;
import it.toscana.rete.rfc.sitat.types.Tab7Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della pubblicazione avviso.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class PubblicazioneAvvisoHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(PubblicazioneAvvisoHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Pubblicazione avviso";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_PUBBLICAZIONE_AVVISO;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument doc = (RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaPubblicazioneAvviso().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument doc = (RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaPubblicazioneAvviso().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {

		RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument doc = (RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument) documento;
		Tab7Type tab7 = doc.getRichiestaRichiestaRispostaSincronaPubblicazioneAvviso().getTab7();
		FaseType fase = doc.getRichiestaRichiestaRispostaSincronaPubblicazioneAvviso().getFase();

		if (!this.existsUffint(fase)) {
			logger.error("La richiesta di primo invio e' associata ad una stazione appaltante (" + fase.getW9FLCFSA() + ") non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di pubblicazione avviso con stazione appaltante non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (this.existsAvviso(new Long(tab7.getW3PAVVISOID()), fase.getW9FLCFSA(), new Long(tab7.getW3PACODSIS()))) {
			logger.error("La richiesta di primo invio ha gia' un avviso con id:" + tab7.getW3PAVVISOID() + ", cfein:" + fase.getW9FLCFSA() + " e codsistema:" + tab7.getW3PACODSIS()
					+ " presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di pubblicazione avviso gia' presente in banca dati");
			// si termina con questo errore
			return;
		}

		// warnings
		// if(!ignoreWarnings) {
		// }
		// SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI
		// SI
		// AGGIORNA IL DB
		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	/**
	 * Verifica se esiste in banca dati la stessa fase di aggiudicazione
	 * semplificata appalto a partire dal cig fornito nel tracciato
	 * 
	 * @param fase
	 *            elemento fase del flusso
	 * @return true se l'elemento NON esiste, false altrimenti
	 * @throws SQLException
	 */
	private boolean existsLotto(String cig) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(cig) from w9lott where cig = ?", new Object[] { cig });
		return numeroOccorrenze.intValue() > 0;
	}

	private boolean existsUffint(FaseType fase) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(codein) from uffint where cfein = ?", new Object[] { fase.getW9FLCFSA() });
		return numeroOccorrenze.intValue() > 0;
	}

	private boolean existsAvviso(Long idAvviso, String cfein, Long codsistema) throws SQLException {
		Long numeroOccorrenze = (Long) this.sqlManager.getObject("select count(avviso.idavviso) from avviso, uffint " + "where idavviso=? and avviso.codein=uffint.codein and uffint.cfein=? "
				+ "and avviso.codsistema=?", new Object[] { idAvviso, cfein, codsistema });
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
	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument doc, DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException, GestoreException {

		Tab7Type tab7 = doc.getRichiestaRichiestaRispostaSincronaPubblicazioneAvviso().getTab7();
		FaseType fase = doc.getRichiestaRichiestaRispostaSincronaPubblicazioneAvviso().getFase();

		if (tab7 != null) {
			if (tab7.isSetW3CIG()) {
				if (this.existsLotto(tab7.getW3CIG())) {
					Long[] longArray = this.getCodGaraCodLottByCIG(tab7.getW3CIG());
					Long codGara = longArray[0];
					Long codLott = longArray[1];

					datiAggiornamento.addColumn("AVVISO.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
					datiAggiornamento.addColumn("AVVISO.CODLOTT", JdbcParametro.TIPO_NUMERICO, codLott);
				}
			}
			if (fase.getW9FLCFSA() != null) {
				String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

				Long idAvviso = new Long(tab7.getW3PAVVISOID());
				Long codSistema = new Long(tab7.getW3PACODSIS());

				if (datiAggiornamento.isColumn("AVVISO.IDAVVISO")) {
					datiAggiornamento.getColumn("AVVISO.IDAVVISO").setObjectValue(idAvviso);
					datiAggiornamento.getColumn("AVVISO.IDAVVISO").setObjectOriginalValue(null);
				} else {
					datiAggiornamento.addColumn("AVVISO.IDAVVISO", JdbcParametro.TIPO_NUMERICO, idAvviso);
				}

				datiAggiornamento.addColumn("AVVISO.CODEIN", JdbcParametro.TIPO_TESTO, pkUffint);
				datiAggiornamento.addColumn("AVVISO.CODSISTEMA", JdbcParametro.TIPO_NUMERICO, codSistema);
				datiAggiornamento.addColumn("AVVISO.TIPOAVV", JdbcParametro.TIPO_NUMERICO, new Long(tab7.getW3PATAVVI().toString()));

				if (tab7.getW3PADATA() != null) {
					datiAggiornamento.addColumn("AVVISO.DATAAVV", JdbcParametro.TIPO_DATA, tab7.getW3PADATA().getTime());
				}
				if (tab7.getW3PADESCRI() != null) {
					datiAggiornamento.addColumn("AVVISO.DESCRI", JdbcParametro.TIPO_TESTO, tab7.getW3PADESCRI());
				}

				ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
				try {
					fileAllegato.write(doc.getRichiestaRichiestaRispostaSincronaPubblicazioneAvviso().getFile());
				} catch (IOException e) {
					throw new GestoreException("Errore durante la lettura del file allegato presente nella richiesta XML", "", e);
				}
				datiAggiornamento.addColumn("AVVISO.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, fileAllegato);
				datiAggiornamento.insert("AVVISO", this.sqlManager);
			}
		}
		// se la procedura di aggiornamento e' andata a buon fine,
		// si aggiorna lo stato dell'importazione
		this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
	}

	@Override
	protected void manageRettifica(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		// TODO Auto-generated method stub

	}

}