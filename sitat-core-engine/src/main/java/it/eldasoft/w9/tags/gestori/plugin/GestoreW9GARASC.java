package it.eldasoft.w9.tags.gestori.plugin;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 * Gestore di plugin per la scheda dell'entita' W9GARA.
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9GARASC extends AbstractGestorePreload {

	private static final String SQL_ESTRAZIONE_DATI_W9LOTT = "select CPV, TIPO_CONTRATTO from W9LOTT where CODGARA = ? AND CODLOTT = ?";
	private static final String SQL_ESTRAZIONE_DATI_TABCPV_CPVDESC     = "select CPVDESC from TABCPV where CPVCOD4 = ?";
	private static final String SQL_ESTRAZIONE_DATI_W9LOTT_LUOGO_ISTAT = "select LUOGO_ISTAT from W9LOTT where CODGARA = ? AND CODLOTT = ?";
	/**
	 * Query per estrazione del intestazione della stazione appaltante.
	 */
	private static final String SQL_ESTRAZIONE_UFFINT_CODEIN = "select NOMEIN, VIAEIN, CITEIN, PROEIN from UFFINT where UFFINT.CODEIN = ? ";

	/**
	 *  Query per estrazione di nome e cognome del RUP.
	 */
	private static final String SQL_ESTRAZIONE_TECNI_COD_NOM = "select NOMTEC, CODTEC, CFTEC from TECNI where TECNI.CGENTEI = ? and TECNI.SYSCON = ?";

	/**
	 * Costruttore.
	 * @param tag BodyTagSupportGene
	 */
	public GestoreW9GARASC(final BodyTagSupportGene tag) {
		super(tag);
	}

	/**
	 * Implementazione del metodo astratto AbstractGestorePreload.doAfterFetch.
	 * 
	 * @param arg0 PageContext
	 * @param arg1 Modalita' di apertura della scheda
	 * @throws JspException JspException
	 */
	public void doAfterFetch(final PageContext arg0, final String arg1) throws JspException {
	}

	/**
	 * Implementazione del metodo astratto AbstractGestorePreload.doBeforeBodyProcessing.
	 * 
	 * @param page PageContext
	 * @param modoAperturaScheda Modalita' di apertura della scheda
	 * @throws JspException JspException
	 */
	public final void doBeforeBodyProcessing(final PageContext page, final String modoAperturaScheda)
	throws JspException {

		String codice = "";
		DataColumnContainer key = null;
		Long codgara = null;
		int iduser;
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
				page, SqlManager.class);

		W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager",
				page, W9Manager.class);

		ProfiloUtente profiloUtente = (ProfiloUtente) page.getAttribute(
				CostantiGenerali.PROFILO_UTENTE_SESSIONE, PageContext.SESSION_SCOPE);

		try {

			String codein = page.getSession().getAttribute("uffint").toString();
			iduser = profiloUtente.getId();
			List< ? > datiListTecni = sqlManager.getListVector(
					SQL_ESTRAZIONE_TECNI_COD_NOM, new Object[] { codein, iduser });
			String nomeTecnico = "";
			String idTecnico = "";
			String cfTecnico = "";

			if (datiListTecni.size() > 0) {
				nomeTecnico = ((List< ? >) datiListTecni.get(0)).get(0).toString();
				idTecnico = ((List< ? >) datiListTecni.get(0)).get(1).toString();
				cfTecnico = ((List< ? >) datiListTecni.get(0)).get(2).toString();
			}

			Vector< ? > datiUffint = sqlManager.getVector(
					SQL_ESTRAZIONE_UFFINT_CODEIN, new Object[]{codein});

			String nomein = "";
			String indirizzoSede = "";
			String comuneSede = "";
			String provinciaSede = "";
			if (datiUffint != null && datiUffint.size() > 0) {
				nomein = StringUtils.trimToEmpty(((JdbcParametro) datiUffint.get(0)).getStringValue());
				indirizzoSede = StringUtils.trimToEmpty(((JdbcParametro) datiUffint.get(1)).getStringValue());
				comuneSede = StringUtils.trimToEmpty(((JdbcParametro) datiUffint.get(2)).getStringValue());
				provinciaSede = StringUtils.trimToEmpty(((JdbcParametro) datiUffint.get(3)).getStringValue());
			}

			page.setAttribute("idTecnico", idTecnico, PageContext.REQUEST_SCOPE);
			page.setAttribute("nomeTecnico", nomeTecnico, PageContext.REQUEST_SCOPE);
			page.setAttribute("cfTecnico", cfTecnico, PageContext.REQUEST_SCOPE);
			page.setAttribute("nomein", nomein, PageContext.REQUEST_SCOPE);
			page.setAttribute("indirizzoSede", indirizzoSede, PageContext.REQUEST_SCOPE);
			page.setAttribute("comuneSede", comuneSede, PageContext.REQUEST_SCOPE);
			page.setAttribute("provinciaSede", provinciaSede, PageContext.REQUEST_SCOPE);

			boolean isSAconPermessi = w9Manager.isStazioneAppaltanteConPermessi(codein);

			page.setAttribute("isSAconPermessi", isSAconPermessi, PageContext.REQUEST_SCOPE);

			if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
				codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);

				key = new DataColumnContainer(codice);
				codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();

				page.setAttribute("codgara", codgara, PageContext.REQUEST_SCOPE);

				List< ? > datiW9LottList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_W9LOTT,
						new Object[] { codgara, new Long(1) });
				String tipoContratto = "";
				String cpv = "";
				String cpvdescr = "";
				if (datiW9LottList.size() > 0) {
					cpv = ((List< ? >) datiW9LottList.get(0)).get(0).toString();
					tipoContratto = ((List< ? >) datiW9LottList.get(0)).get(1).toString();
					if (cpv != null && !cpv.equals("")) {
						List< ? > cpvdescrList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_TABCPV_CPVDESC,
								new Object[] { cpv });
						if (cpvdescrList.size() > 0) {
							cpvdescr = ((List< ? >) cpvdescrList.get(0)).get(0).toString();
						}
					}
				}
				page.setAttribute("codcpv", cpv, PageContext.REQUEST_SCOPE);
				page.setAttribute("cpvdescr", cpvdescr, PageContext.REQUEST_SCOPE);
				page.setAttribute("tipoContratto", tipoContratto, PageContext.REQUEST_SCOPE);

				List< ? > datiComuniList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_W9LOTT_LUOGO_ISTAT,
						new Object[] { codgara, new Long(1) });

				String codComune = "";
				String descrComune = "";
				String descrProvincia = "";
				if (datiComuniList.size() > 0) {
					codComune = ((List< ? >) datiComuniList.get(0)).get(0).toString();
					if (codComune != null && !codComune.equals("")) {
						String sqlEtrazioneDatiComune = "select tb1.tabcod3, tabsche.tabdesc from tabsche, tabsche tb1, w9lott where tabsche.tabcod='S2003' and tabsche.tabcod1='09' and tb1.tabcod='S2003' and tb1.tabcod1='07' and w9lott.luogo_istat = tabsche.tabcod3 and " + sqlManager.getDBFunction("substr", new String[] {"tabsche.tabcod3", "4", "3"}) + " = " + sqlManager.getDBFunction("substr", new String[] {"tb1.tabcod2", "2", "3"}) + " and tabsche.tabcod3 = ? and w9lott.codgara = ? and w9lott.codlott = ?";
						List< ? > datiListComune = sqlManager.getListVector(sqlEtrazioneDatiComune,
								new Object[] { codComune, codgara,  new Long(1) });
						if (datiListComune.size() > 0) {
							descrComune = ((List< ? >) datiListComune.get(0)).get(1).toString();
							descrProvincia = ((List< ? >) datiListComune.get(0)).get(0).toString();
						}
					}
				}
				page.setAttribute("denomComuneEsecuzione", descrComune, PageContext.REQUEST_SCOPE);
				page.setAttribute("descrProvinciaEsecuzione", descrProvincia, PageContext.REQUEST_SCOPE);
			}
		} catch (SQLException sqle) {
			throw new JspException(
					"Errore nell'esecuzione della query per l'estrazione dei dati della Gara",
					sqle);
		} catch (Exception exc) {
			throw new JspException("Errore nel caricamento dati della Gara", exc);
		}
	}

}