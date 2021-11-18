package it.eldasoft.w9.tags.funzioni;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import it.eldasoft.gene.bl.admin.UffintManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;



public class ContaStazioniAppaltantiPresentiFunction extends AbstractFunzioneTag {

	/** Costruttore. */
	public ContaStazioniAppaltantiPresentiFunction() {
		super(1, new Class[] { PageContext.class });
	}

	public String function(PageContext pageContext, Object[] params) throws JspException {

		Logger logger = Logger.getLogger(ContaStazioniAppaltantiPresentiFunction.class);

		UffintManager uffintManager = (UffintManager) UtilitySpring.getBean("uffintManager", pageContext, UffintManager.class);

		Integer conteggioSA = new Integer(0);
		
		if (logger.isDebugEnabled()) {
			logger.debug("runAction: inizio metodo");
		}

		String messageKey = null;

		ProfiloUtente profiloUtente = (ProfiloUtente) pageContext.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);

		try {
			List elencoUfficiIntestatari = uffintManager.getUfficiIntestatariAccount(profiloUtente.getId());
			if (elencoUfficiIntestatari.size() > 0) {
				conteggioSA = elencoUfficiIntestatari.size();
			}

		} catch (DataAccessException e) {
			messageKey = "errors.database.dataAccessException";
			logger.debug(messageKey, e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("runAction: fine metodo");
		}

		return conteggioSA.toString();

	}

}
