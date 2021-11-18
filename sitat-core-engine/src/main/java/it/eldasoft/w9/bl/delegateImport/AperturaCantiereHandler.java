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
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAperturaCantiereDocument;
import it.toscana.rete.rfc.sitat.types.Tab171Type;
import it.toscana.rete.rfc.sitat.types.Tab172Type;
import it.toscana.rete.rfc.sitat.types.Tab173Type;
import it.toscana.rete.rfc.sitat.types.Tab17Type;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della scheda cantiere
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class AperturaCantiereHandler extends AbstractRequestHandler {

	Logger logger = Logger.getLogger(AperturaCantiereHandler.class);

	@Override
	protected String getNomeFlusso() {
		return "Scheda Cantiere";
	}

	@Override
	protected String getMainTagRequest() {
		return CostantiFlussi.MAIN_TAG_RICHIESTA_APERTURA_CANTIERE;
	}

	@Override
	protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
		return RichiestaRichiestaRispostaSincronaAperturaCantiereDocument.Factory.parse(xmlEvento);
	}

	@Override
	protected boolean isTest(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc = (RichiestaRichiestaRispostaSincronaAperturaCantiereDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTest();
	}

	@Override
	protected FaseType getFaseInvio(XmlObject documento) {
		RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc = (RichiestaRichiestaRispostaSincronaAperturaCantiereDocument) documento;
		return doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getFase();
	}

	@Override
	protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc = (RichiestaRichiestaRispostaSincronaAperturaCantiereDocument) documento;
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getFase();

		if (!this.existsLotto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non presente in DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di fase scheda cantiere di un lotto non presente in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAppalto(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto non appaltato sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di fase scheda cantiere di un lotto non appaltato in banca dati");
			// si termina con questo errore
			return;
		}

		if (!this.existsAggiudicatari(fase)) {
			logger.error("La richiesta di primo invio ha un cig (" + fase.getW3FACIG() + ") di un lotto senza imprese aggiudicatarie sul DB");
			this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio di fase scheda cantiere di un lotto senza imprese aggiudicatarie in banca dati");
			// si termina con questo errore
			return;
		}

		/*
		 * if (this.existsFase(fase.getW3FACIG(), CostantiW9.APERTURA_CANTIERE))
		 * { logger.error("La richiesta di primo invio ha un cig (" +
		 * fase.getW3FACIG() + ") di una scheda cantiere gia' presente sul DB");
		 * this.setEsito(datiAggiornamento,
		 * StatoComunicazione.STATO_ERRATA.getStato(),
		 * "Primo invio di fase scheda cantiere gia' presente in banca dati");
		 * // si termina con questo errore return; }
		 */

		// Warnings
		if (!ignoreWarnings) {
			boolean isR = UtilitySITAT.isR(fase.getW3FACIG(), this.sqlManager);

			if (!isR) {
				logger.error("La richiesta di primo invio ha un lotto che non prevede manodopera");
				this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(), "Primo invio di fase scheda cantiere di un lotto che non prevede manodopera");
				// si termina con questo errore
				return;
			}

		}

		this.insertDatiFlusso(doc, datiAggiornamento, true);
	}

	private void insertDatiFlusso(RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc, DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException, GestoreException {

		Tab17Type tab17 = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTab17();
		FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getFase();

		Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
		Long codGara = longArray[0];
		Long codLott = longArray[1];
		int numCant = fase.getW3NUM5();

		String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

		this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

		DataColumnContainer dccCantiere = new DataColumnContainer(new DataColumn[] { new DataColumn("W9CANT.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
				new DataColumn("W9CANT.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
				new DataColumn("W9CANT.NUM_CANT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numCant))) });

		if (tab17 != null) {
			if (tab17.getW3DINIZCA() != null) {
				dccCantiere.addColumn("W9CANT.DINIZ", JdbcParametro.TIPO_DATA, tab17.getW3DINIZCA().getTime());
			}
			dccCantiere.addColumn("W9CANT.DLAV", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW3DLAVCA()));
			if (tab17.getW3INDCAN() != null) {
				dccCantiere.addColumn("W9CANT.INDCAN", JdbcParametro.TIPO_TESTO, tab17.getW3INDCAN());
			}
			if (tab17.isSetW9CATIPOPERA()) {
				dccCantiere.addColumn("W9CANT.TIPOPERA", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CATIPOPERA().toString()));
			}

			if (tab17.isSetW9CATIPINTER()) {
				dccCantiere.addColumn("W9CANT.TIPINTERV", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CATIPINTER().toString()));
			}

			if (tab17.isSetW9CATIPCOSTR()) {
				dccCantiere.addColumn("W9CANT.TIPCOSTR", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CATIPCOSTR().toString()));
			}

			if (tab17.isSetW3INFRA()) {
				dccCantiere.addColumn("W9CANT.INFRA", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW3INFRA()));
			}
			if (tab17.isSetW3INFRDA()) {
				dccCantiere.addColumn("W9CANT.INFRDA", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW3INFRDA()));
			}
			if (tab17.isSetW3INFRDE()) {
				dccCantiere.addColumn("W9CANT.INFRDE", JdbcParametro.TIPO_TESTO, tab17.getW3INFRDE());
			}
			if (tab17.isSetW3MAILRIC()) {
				dccCantiere.addColumn("W9CANT.MAILRIC", JdbcParametro.TIPO_TESTO, tab17.getW3MAILRIC());
			}
			if (tab17.isSetW9CACIVICO()) {
				dccCantiere.addColumn("W9CANT.CIVICO", JdbcParametro.TIPO_TESTO, tab17.getW9CACIVICO());
			}
			if (tab17.getW9CACOMUNE() != null) {
				dccCantiere.addColumn("W9CANT.COMUNE", JdbcParametro.TIPO_TESTO, tab17.getW9CACOMUNE());
			}
			if (tab17.getW9CAPROV() != null) {
				dccCantiere.addColumn("W9CANT.PROV", JdbcParametro.TIPO_TESTO, tab17.getW9CAPROV());
			}
			if (tab17.isSetW9CACOORDX()) {
				dccCantiere.addColumn("W9CANT.COORD_X", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CACOORDX()));
			}
			if (tab17.isSetW9CACOORDY()) {
				dccCantiere.addColumn("W9CANT.COORD_Y", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CACOORDY()));
			}
			if (tab17.isSetW9CALATIT()) {
				dccCantiere.addColumn("W9CANT.LATIT", JdbcParametro.TIPO_DECIMALE, new Double(tab17.getW9CALATIT()));
			}
			if (tab17.isSetW9CALONGIT()) {
				dccCantiere.addColumn("W9CANT.LONGIT", JdbcParametro.TIPO_DECIMALE, new Double(tab17.getW9CALONGIT()));
			}
			if (tab17.isSetW9CANUMLAV()) {
				dccCantiere.addColumn("W9CANT.NUMLAV", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CANUMLAV()));
			}
			if (tab17.isSetW9CANUMIMP()) {
				dccCantiere.addColumn("W9CANT.NUMIMP", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CANUMIMP()));
			}
			if (tab17.isSetW9CALAVAUT()) {
				dccCantiere.addColumn("W9CANT.LAVAUT", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CALAVAUT()));
			}
		}
		if (primoInvio) {
			dccCantiere.addColumn("W9CANT.VERSNOT", JdbcParametro.TIPO_NUMERICO, new Long(0));
		}

		Tab171Type[] arrayTab171 = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTab171Array();
		if (arrayTab171 != null && arrayTab171.length > 0) {
			dccCantiere.addColumn("W9CANT.DAEXPORT", JdbcParametro.TIPO_TESTO, "1");
		} else {
			dccCantiere.addColumn("W9CANT.DAEXPORT", JdbcParametro.TIPO_TESTO, "2");
		}
		dccCantiere.insert("W9CANT", this.sqlManager);

		// W9CANTDEST
		if (arrayTab171 != null && arrayTab171.length > 0) {
			for (int numTab171 = 0; numTab171 < arrayTab171.length; numTab171++) {
				Long tipoDestinatario = Long.parseLong(arrayTab171[numTab171].getW9DEDEST().toString());
				DataColumnContainer dccCantiereDest = new DataColumnContainer(new DataColumn[] { new DataColumn("W9CANTDEST.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9CANTDEST.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
						new DataColumn("W9CANTDEST.NUM_CANT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numCant))),
						new DataColumn("W9CANTDEST.DEST", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, tipoDestinatario)) });
				dccCantiereDest.insert("W9CANTDEST", this.sqlManager);
			}
		}

		// W9CANTIMP
		Tab172Type[] arrayTab172 = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTab172Array();
		if (arrayTab172 != null && arrayTab172.length > 0) {
			for (int numTab172 = 0; numTab172 < arrayTab172.length; numTab172++) {

				Tab172Type impresaCantiere = arrayTab172[numTab172];
				DataColumnContainer dccCantiereImpresa = new DataColumnContainer(new DataColumn[] { new DataColumn("W9CANTIMP.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9CANTIMP.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
						new DataColumn("W9CANTIMP.NUM_CANT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numCant))),
						new DataColumn("W9CANTIMP.NUM_IMP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numTab172 + 1))) });
				if (impresaCantiere.isSetW9CICIPDURC()) {
					dccCantiereImpresa.addColumn("W9CANTIMP.CIPDURC", new JdbcParametro(JdbcParametro.TIPO_TESTO, impresaCantiere.getW9CICIPDURC()));
				}
				if (impresaCantiere.isSetW9CIPROTDURC()) {
					dccCantiereImpresa.addColumn("W9CANTIMP.PROTDURC", new JdbcParametro(JdbcParametro.TIPO_TESTO, impresaCantiere.getW9CIPROTDURC()));
				}
				if (impresaCantiere.isSetW9CIDATDURC()) {
					dccCantiereImpresa.addColumn("W9CANTIMP.DATDURC", new JdbcParametro(JdbcParametro.TIPO_DATA, impresaCantiere.getW9CIDATDURC().getTime()));
				}

				if (impresaCantiere.getArch3() != null) {
					String codiceImpresa = this.gestioneImpresa(impresaCantiere.getArch3(), pkUffint);
					if (StringUtils.isNotEmpty(codiceImpresa)) {
						dccCantiereImpresa.addColumn("W9CANTIMP.CODIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, codiceImpresa));
					}
				}
				dccCantiereImpresa.insert("W9CANTIMP", this.sqlManager);
			}
		}

		// W9INCA (SEZIONE = 'NP')
		Tab173Type[] arrayTab173 = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTab173Array();
		if (arrayTab173 != null && arrayTab173.length > 0) {
			for (int numTab173 = 0; numTab173 < arrayTab173.length; numTab173++) {

				Tab173Type incaricatoProfessionale = arrayTab173[numTab173];
				DataColumnContainer dccIncaricoProfessionale = new DataColumnContainer(new DataColumn[] { new DataColumn("W9INCA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
						new DataColumn("W9INCA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
						new DataColumn("W9INCA.NUM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numCant))),
						new DataColumn("W9INCA.NUM_INCA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numTab173 + 1))),
						new DataColumn("W9INCA.SEZIONE", new JdbcParametro(JdbcParametro.TIPO_TESTO, "NP")) });

				dccIncaricoProfessionale.addColumn("W9INCA.ID_RUOLO", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(incaricatoProfessionale.getW3IDRUOLO().toString())));

				if (incaricatoProfessionale.getArch2() != null) {
					String codiceTecnico = this.getTecnico(incaricatoProfessionale.getArch2(), pkUffint);
					if (StringUtils.isNotEmpty(codiceTecnico)) {
						dccIncaricoProfessionale.addColumn("W9INCA.CODTEC", new JdbcParametro(JdbcParametro.TIPO_TESTO, codiceTecnico));
					}
				}
				dccIncaricoProfessionale.insert("W9INCA", this.sqlManager);
			}
		}

		this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
	}

	@Override
	protected void manageRettifica(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings) throws GestoreException, SQLException {
		// TODO Auto-generated method stub

	}

}