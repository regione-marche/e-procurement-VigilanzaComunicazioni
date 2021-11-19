package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.transaction.TransactionStatus;

/**
 * Esegue l'import di interventi non riproposti da un progetto al progetto
 * all'altro
 */
public class RiportaDaProgrammaPrecedenteAction extends ActionBaseNoOpzioni {

	private static Logger logger = Logger.getLogger(RiportaDaProgrammaPrecedenteAction.class);

	private SqlManager sqlManager;

	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	public ActionForward runAction(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("runAction: inizio metodo");
		}

		String target = null;
		String messageKey = null;
		Long chiavePianoTriennale = new Long(request.getParameter("codiceProgramma").split(":")[1]);

		TransactionStatus status = null;
		try {

			status = this.sqlManager.startTransaction();

			String recuperaDatiPianoTriennaleAttuale = "select Anntri,Cenint,Tiprog from piatri where contri = ? ";
			Vector< ? > datiPianoTriennaleAttuale;

			datiPianoTriennaleAttuale = this.sqlManager.getVector(recuperaDatiPianoTriennaleAttuale, new Object[] { chiavePianoTriennale });

			Long annoPianoTriennaleAttuale = (Long) SqlManager.getValueFromVectorParam(datiPianoTriennaleAttuale, 0).getValue();
			String codiceStazioneAppaltante = (String) SqlManager.getValueFromVectorParam(datiPianoTriennaleAttuale, 1).getValue();
			Long tipologiaProgetto = (Long) SqlManager.getValueFromVectorParam(datiPianoTriennaleAttuale, 2).getValue();
			Long annoPianoTriennalePrecedente = annoPianoTriennaleAttuale - new Long(1);

			String recuperaCodicePianoTriennalePrecedente = "select contri from piatri where anntri = ? and cenint = ? ";

			// non faccio alcun controllo in quanto la funzione compare
			// solo nel caso siano presenti degli interventi (non riproposti)
			// da importare
			Long chiavePianoTriennalePrecedente = (Long) this.sqlManager.getObject(recuperaCodicePianoTriennalePrecedente, new Object[] { annoPianoTriennalePrecedente, codiceStazioneAppaltante });

			String recuperoDatiInterventiSelezionabiliAnnoPrecedente = 
				  "select CONTRI, CONINT, CUIINT, CUPPRG, DESINT, TOTINT, PRGINT from inttri "
				+ " where contri = ? and annint='1'  and tipint = ?  " 
				  + " and cuiint not in (select cuiint from inttri where contri = ?) " // interventi gia' riproposti
					+ " and cuiint not in (select cuiint from inrtri where contri = ?) "; // interventi non riproposti inseriti
			List<?> interventiSelezionabiliAnnoPrecedente = this.sqlManager.getListVector(recuperoDatiInterventiSelezionabiliAnnoPrecedente, 
			    new Object[] { chiavePianoTriennalePrecedente, tipologiaProgetto, chiavePianoTriennale, chiavePianoTriennale });

			for (int i = 0; i < interventiSelezionabiliAnnoPrecedente.size(); i++) {
				Long valoreCONTRI = chiavePianoTriennale;
				Long valoreCONINT = (Long) this.sqlManager.getObject("select " + this.sqlManager.getDBFunction("isnull", new String[] { "max(conint)", "0" }) + "+1 from INRTRI where CONTRI = ?",
						new Object[] { chiavePianoTriennale });
				String valoreCUIINT = SqlManager.getValueFromVectorParam(interventiSelezionabiliAnnoPrecedente.get(i), 2).stringValue();
				String valoreCUPPRG = SqlManager.getValueFromVectorParam(interventiSelezionabiliAnnoPrecedente.get(i), 3).stringValue();
				String valoreDESINT = SqlManager.getValueFromVectorParam(interventiSelezionabiliAnnoPrecedente.get(i), 4).stringValue();
				Double valoreTOTINT = SqlManager.getValueFromVectorParam(interventiSelezionabiliAnnoPrecedente.get(i), 5).doubleValue();
				Long valorePRGINT = SqlManager.getValueFromVectorParam(interventiSelezionabiliAnnoPrecedente.get(i), 6).longValue();

				String inserimentoInterventoNonRiproposto = "INSERT INTO INRTRI(CONTRI,CONINT,CUIINT,CUPPRG,DESINT,TOTINT,PRGINT) VALUES (?,?,?,?,?,?,?)";

				this.sqlManager.update(inserimentoInterventoNonRiproposto, new Object[] { valoreCONTRI, valoreCONINT, valoreCUIINT, valoreCUPPRG, valoreDESINT, valoreTOTINT, valorePRGINT });
			}

			this.sqlManager.commitTransaction(status);
			target = "success";

		} catch (GestoreException e) {
			logger.error("Errore nell'importazione degli interventi non riproposti", e);
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE.concat("Interventi");  
			messageKey = "errors.riportaDaProgrammaPrecedente";
			this.aggiungiMessaggio(request, messageKey);
		} catch (SQLException e) {
			logger.error("Errore nell'importazione degli interventi non riproposti", e);
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE.concat("Interventi");
			messageKey = "errors.riportaDaProgrammaPrecedente";
			this.aggiungiMessaggio(request, messageKey);

			try {
				this.sqlManager.rollbackTransaction(status);
			} catch (SQLException ex) {
				logger.error("Errore nel rollback dopo un errore nell'importazione degli inteventi non riproposti", ex);
				target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
				messageKey = "errors.riportaDaProgrammaPrecedente";
				this.aggiungiMessaggio(request, messageKey);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("runAction: fine metodo");
		}

		if (target != null) {
			return mapping.findForward(target);
		} else {
			return null;
		}
	}

}
