/*
 * Created on 23/06/10
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.tags.funzioni;

import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;

public class VerificaPresenzaPianiTriennaliPrecedentiFunction extends AbstractFunzioneTag {

	public VerificaPresenzaPianiTriennaliPrecedentiFunction() {
		super(2, new Class[] { PageContext.class, String.class });
	}

	/**
	 * Questa funzione verifica la corretta sequenza degli interventi, in caso
	 * contrario provvede a stabilirla.
	 */
	public String function(PageContext pageContext, Object[] params) throws JspException {
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

		boolean result = false;
		Long numeroInterventiNonRipropostiPresenti = new Long(0);
		try {
			String chiavePianoTriennale = params[1].toString().split(":")[1];

			String recuperaDatiPianoTriennaleAttuale = "select ANNTRI, CENINT, TIPROG from PIATRI where CONTRI=? ";
			Vector<?> datiPianoTriennaleAttuale = sqlManager.getVector(recuperaDatiPianoTriennaleAttuale, new Object[] { new Long(chiavePianoTriennale) });

			Long annoPianoTriennaleAttuale = (Long) SqlManager.getValueFromVectorParam(datiPianoTriennaleAttuale, 0).getValue();
			String codiceStazioneAppaltante = (String) SqlManager.getValueFromVectorParam(datiPianoTriennaleAttuale, 1).getValue();
			Long tipologiaProgetto = (Long) SqlManager.getValueFromVectorParam(datiPianoTriennaleAttuale, 2).getValue();
			Long annoPianoTriennalePrecedente = annoPianoTriennaleAttuale - new Long(1);
			
			Long numeroPianiPrecedenti = (Long) sqlManager.getObject("select count(CONTRI) from PIATRI where ANNTRI=? and CENINT=? and TIPROG in (?, 3) ", 
          new Object[] { annoPianoTriennalePrecedente, codiceStazioneAppaltante, tipologiaProgetto });
			if (numeroPianiPrecedenti != null && numeroPianiPrecedenti.longValue() > 0) {
				String conteggioInterventiSelezionabiliAnniPrecedente = 
				  "select count(CONINT) from INTTRI where CONTRI in ( " +
				    " select CONTRI from PIATRI where ANNTRI=? and CENINT=? and TIPROG in (?, 3) " +
				    " ) and TIPINT in (?, 3) and (CUIINT not in ( " +
				    " select CUIINT from INTTRI where CONTRI=? union select CUIINT from INRTRI where CONTRI=?) or CUIINT is not null)";
				Object interventiSelezionabiliAnnoPrecedente = sqlManager.getObject(conteggioInterventiSelezionabiliAnniPrecedente, 
						new Object[] { annoPianoTriennalePrecedente, codiceStazioneAppaltante, tipologiaProgetto, tipologiaProgetto,
				    new Long(chiavePianoTriennale), new Long(chiavePianoTriennale) });
				if (interventiSelezionabiliAnnoPrecedente != null && ((Long) interventiSelezionabiliAnnoPrecedente > new Long(0))) {
					result = true;
				}
			}

			numeroInterventiNonRipropostiPresenti = (Long) sqlManager.getObject(
					"select count(CONINT) from INRTRI where CONTRI=?", new Object[] { new Long(chiavePianoTriennale) });
			pageContext.getRequest().setAttribute("numeroInterventiNonRipropostiPresenti",
					numeroInterventiNonRipropostiPresenti);

			GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager", pageContext, GeneManager.class);
			if (geneManager.esisteTabella("V_REPORT_SOGG_AGGR")) {
			  pageContext.getRequest().setAttribute("esisteViewReportSoggAggr", Boolean.TRUE);
			}
			
		} catch (SQLException e) {
			throw new JspException("Errore nel recupero degli interventi", e);
		}
		
		String valore = ConfigManager.getValore("it.eldasoft.pt.moduloFabbisogniAttivo");
		if ("1".equals(valore)) {
		  pageContext.getRequest().setAttribute("moduloFabbisogniAttivo", "1");
		}

		return (result) ? "TRUE" : "FALSE";

	}
}
