package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.LoginManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

public class GestoreW9GARASC extends AbstractGestoreChiaveNumerica {

	public String[] getAltriCampiChiave() {
		return null;
	}

	public String getCampoNumericoChiave() {
		return "CODGARA";
	}

	public String getEntita() {
		return "W9GARA";
	}

	public void postDelete(DataColumnContainer arg0) throws GestoreException {
	}

	public void postInsert(DataColumnContainer impl) throws GestoreException {
	}

	public void postUpdate(DataColumnContainer impl) throws GestoreException {

	}

	public void preDelete(TransactionStatus status, DataColumnContainer impl)
	throws GestoreException {
		String sqlSelectCancellazione = "";
		GeneManager gene = this.getGeneManager();

		// si ricava il valore della chiave primaria di W9GARA
		Long codgara = impl.getColumn("W9GARA.CODGARA").getValue().longValue();

		// l'array listaEntita contiene le tabelle in cui verranno fatte le
		// eliminazioni degli elementi collegati ad una gara
		String[] listaEntita = new String[] { "W9LOTT", "W9APPA", "W9AGGI",
				"W9INCA", "W9INIZ", "W9AVAN", "W9CONC", "W9COLL", "W9SOSP", "W9VARI",
				"W9ACCO", "W9SUBA", "W9RITA", "W9SIC", "W9TECPRO", "W9REGCON",
				"W9MISSIC", "W9INASIC", "W9INFOR", "W9CANT", "W9DOCGARA", "W9PUBB",
				"W9ESITO", "W9IMPRESE" };

		for (int k = 0; k < listaEntita.length; k++) {
			sqlSelectCancellazione = "DELETE FROM "
				+ listaEntita[k]
				              + " WHERE CODGARA = "
				              + codgara;
			try {
				gene.getSql().execute(sqlSelectCancellazione);
			} catch (SQLException e) {
				throw new GestoreException(
						"Errore durante la cancellazione di una Gara",
						"cancellazione.gara", e);
			}
		}
	}

	public void preInsert(TransactionStatus status, DataColumnContainer impl)
	throws GestoreException {
		ProfiloUtente profiloUtente = (ProfiloUtente) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
		impl.addColumn("W9GARA.SYSCON", new Long(profiloUtente.getId()));
		impl.addColumn("W9GARA.OGGETTO", impl.getString("W9LOTT.OGGETTO"));
		impl.addColumn("W9GARA.IMPORTO_GARA", impl.getDouble("W9LOTT.IMPORTO_TOT"));
		impl.addColumn("W9GARA.NLOTTI", new Long(1));
		super.preInsert(status, impl);

		impl.addColumn("W9LOTT.RUP", impl.getString("W9GARA.RUP"));
		impl.addColumn("W9LOTT.FLAG_ENTE_SPECIALE", impl.getString("W9GARA.FLAG_ENTE_SPECIALE"));
		impl.setValue("W9LOTT.CODGARA", impl.getLong("W9GARA.CODGARA"));

		if (!UtilitySITAT.isSmartCigValido(impl.getString(("W9LOTT.CIG")))) {
			throw new GestoreException("Codice smartCIG non valido", "commons.validate", new Object[] { "Codice smartCIG non valido" }, null);
		}

		GeneManager gene = this.getGeneManager();
		if (UtilitySITAT.existsSmartCigSA(impl.getString("W9LOTT.CIG"), impl.getString("W9GARA.CODEIN"), gene.getSql())) {
			throw new GestoreException("Codice smartCIG già esistente per questa stazione appaltante", "commons.validate", new Object[] { "Codice smartCIG già esistente per questa stazione appaltante" }, null);
		}
		
		this.inserisci(status, impl, new GestoreW9LOTT());

		LoginManager loginManager = (LoginManager) UtilitySpring.getBean("loginManager",
				getServletContext(), LoginManager.class);
		W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager",
				getServletContext(), W9Manager.class);
		String codUffInt = (String) this.getRequest().getSession().getAttribute(
				CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);

		try {
			if (w9Manager.isStazioneAppaltanteConPermessiDaApplicareInCreazione(codUffInt)) {
				w9Manager.updateAttribuzioneModello(impl.getLong("W9GARA.CODGARA"), new Long(1));
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nell'inserimento dei permessi nella W9PERMESSI " +
					"per la gara in creazione", null, e);
		}

		if (impl.isColumn("W9GARA.RUP") && impl.getColumn("W9GARA.RUP").getValue().getValue() == null) {
			try {
				// CREARE UN TECNICO e ASSOCIARLO ALL'UTENTE CONNESSO
				Account account = loginManager.getAccountById(profiloUtente.getId());
				DataColumn uffint = new DataColumn("TECNI.CGENTEI", new JdbcParametro(
						JdbcParametro.TIPO_TESTO, codUffInt));
				DataColumn nometec = new DataColumn("TECNI.NOMTEC", new JdbcParametro(
						JdbcParametro.TIPO_TESTO, account.getNome()));
				DataColumn syscon = new DataColumn("TECNI.SYSCON", new JdbcParametro(
						JdbcParametro.TIPO_NUMERICO, new Long(account.getIdAccount())));
				String pk = this.geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
				impl.getColumn("W9GARA.RUP").setValue(
						new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
				DataColumn codiceTecnico = new DataColumn("TECNI.CODTEC",
						new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
				DataColumnContainer dcc = new DataColumnContainer(new DataColumn[] {
						codiceTecnico, uffint, nometec, syscon });
				if (StringUtils.isNotEmpty(account.getCodfisc())) {
					dcc.addColumn("TECNI.CFTEC", new JdbcParametro(
							JdbcParametro.TIPO_TESTO, account.getCodfisc()));
				}
				if (StringUtils.isNotEmpty(account.getEmail())) { 
					dcc.addColumn("TECNI.EMATEC", JdbcParametro.TIPO_TESTO, account.getEmail());
				}
				dcc.getColumn("TECNI.CODTEC").setChiave(true);
				dcc.insert("TECNI", this.sqlManager);
			} catch(CriptazioneException ce) {
				throw new GestoreException("", null, ce);
			} catch(SQLException se) {
				throw new GestoreException("", null, se);
			}
		}
	}

	public void preUpdate(TransactionStatus status, DataColumnContainer impl)
	throws GestoreException {
		if (impl.isColumn("W9GARA.FLAG_ENTE_SPECIALE")) {
			impl.addColumn("W9GARA.OGGETTO", impl.getString("W9LOTT.OGGETTO"));
			impl.addColumn("W9GARA.IMPORTO_GARA", impl.getDouble("W9LOTT.IMPORTO_TOT"));

			impl.addColumn("W9LOTT.RUP", impl.getString("W9GARA.RUP"));
			impl.addColumn("W9LOTT.FLAG_ENTE_SPECIALE", impl.getString("W9GARA.FLAG_ENTE_SPECIALE"));

			this.update(status, impl, new GestoreW9LOTT());
		}
	}

}