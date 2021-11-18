package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 * Funzione per l'inizializzazione delle sezioni multiple dei file che
 * costituiscono il Pubblicazione di gara.
 * 
 * @author Luca.Giacomazzo
 */
public class GetListaLottiPubblicazioneFunction extends AbstractFunzioneTag {

	/** Costruttore. */
	public GetListaLottiPubblicazioneFunction() {
		super(4, new Class[] { PageContext.class, String.class, String.class, String.class });
	}

	@Override
	public String function(PageContext pageContext, Object[] params) throws JspException {

		HashMap<String, String> elencoLotti = new HashMap<String, String>();
		HashMap<String, String> elencoLottiAbilitati = new HashMap<String, String>();
		Long codGara = new Long((String) params[1]);
		Long numPubb = new Long((String) params[2]);
		Long tipoDocumenti = new Long((String) params[3]);
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
		try {
			
			// Creo la lista dei lotti selezionati per la pubblicazione
			List<?> listaLottiPubblicazione = sqlManager.getListVector("select W9LOTT.CODLOTT from W9LOTT where W9LOTT.CODGARA=? AND W9LOTT.CODLOTT IN ( "
					+ " select CODLOTT from W9PUBLOTTO where CODGARA=? AND W9PUBLOTTO.NUM_PUBB=?)", new Object[] { codGara, codGara, numPubb });

			if (listaLottiPubblicazione != null && listaLottiPubblicazione.size() > 0) {
				for (int i = 0; i < listaLottiPubblicazione.size(); i++) {
					Vector<?> vettore = (Vector<?>) listaLottiPubblicazione.get(i);

					String str = ((JdbcParametro) vettore.get(0)).toString();
					elencoLotti.put(str, str);
				}

				pageContext.setAttribute("listaLottiPubblicazioneEstratti", elencoLotti, PageContext.REQUEST_SCOPE);
			}
			// Creo la lista dei lotti che e' possibile abilitare per la pubblicazione
			String sqlelencoLottiAbilitati = "select L.CODLOTT as codLott from W9LOTT L left join W9GARA G ON L.CODGARA=G.CODGARA where L.CODGARA=? ";

			String strWhere = (String) sqlManager.getObject("select CL_WHERE_VIS from W9CF_PUBB where ID=?", new Object[] { tipoDocumenti });
			if (StringUtils.isNotEmpty(strWhere)) {
				sqlelencoLottiAbilitati = sqlelencoLottiAbilitati.concat(" AND " + strWhere);
			}

			List<?> listaLottiPubblicazioneAbilitati = sqlManager.getListVector(sqlelencoLottiAbilitati, new Object[] { codGara });

			if (listaLottiPubblicazioneAbilitati != null && listaLottiPubblicazioneAbilitati.size() > 0) {
				for (int i = 0; i < listaLottiPubblicazioneAbilitati.size(); i++) {
					Vector<?> vettore = (Vector<?>) listaLottiPubblicazioneAbilitati.get(i);

					String str = ((JdbcParametro) vettore.get(0)).toString();
					elencoLottiAbilitati.put(str, str);
				}

				pageContext.setAttribute("listaLottiPubblicazioneAbilitati", elencoLottiAbilitati, PageContext.REQUEST_SCOPE);
			}

		} catch (SQLException e) {
			throw new JspException("Errore nell'estrarre le pubblicazioni della gara " + codGara, e);
		}
		return null;
	}

}