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

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

public class VerificaPresenzaOpereIncompiutePrecedentiFunction extends AbstractFunzioneTag {

	public VerificaPresenzaOpereIncompiutePrecedentiFunction() {
		super(2, new Class[] { PageContext.class, String.class });
	}

	/**
	 * Questa funzione verifica la presenza di opere incompiute nei programmi precedenti
	 */
	public String function(PageContext pageContext, Object[] params) throws JspException {
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

		boolean result = false;
		try {
			String chiavePianoTriennale = params[1].toString().split(":")[1];

			String recuperaDatiPianoTriennaleAttuale = "select ANNTRI, CENINT from PIATRI where CONTRI=? ";
			Vector<?> datiPianoTriennaleAttuale = sqlManager.getVector(recuperaDatiPianoTriennaleAttuale, new Object[] { new Long(chiavePianoTriennale) });

			Long annoPianoTriennaleAttuale = (Long) SqlManager.getValueFromVectorParam(datiPianoTriennaleAttuale, 0).getValue();
			String codiceStazioneAppaltante = (String) SqlManager.getValueFromVectorParam(datiPianoTriennaleAttuale, 1).getValue();
			Long annoPianoTriennalePrecedente = annoPianoTriennaleAttuale - new Long(1);
			
			Long numeroPianiPrecedenti = (Long) sqlManager.getObject("select count(CONTRI) from PIATRI where ANNTRI=? and CENINT=? and TIPROG = 1 ", 
          new Object[] { annoPianoTriennalePrecedente, codiceStazioneAppaltante });
			if (numeroPianiPrecedenti != null && numeroPianiPrecedenti.longValue() > 0) {
				String conteggioOpereSelezionabiliAnniPrecedente = 
				  "select count(NUMOI) from OITRI where CONTRI in ( " +
				    " select CONTRI from PIATRI where ANNTRI=? and CENINT=? and TIPROG = 1)";
				Object opereSelezionabiliAnnoPrecedente = sqlManager.getObject(conteggioOpereSelezionabiliAnniPrecedente, 
						new Object[] { annoPianoTriennalePrecedente, codiceStazioneAppaltante});
				if (opereSelezionabiliAnnoPrecedente != null && ((Long) opereSelezionabiliAnnoPrecedente > new Long(0))) {
					result = true;
				}
			}

		} catch (SQLException e) {
			throw new JspException("Errore nel recupero delle opere", e);
		}

		return (result) ? "TRUE" : "FALSE";

	}
}
