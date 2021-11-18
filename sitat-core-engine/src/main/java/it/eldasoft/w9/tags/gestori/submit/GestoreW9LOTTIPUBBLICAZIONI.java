package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import org.springframework.transaction.TransactionStatus;

public class GestoreW9LOTTIPUBBLICAZIONI extends AbstractGestoreEntita {

	public GestoreW9LOTTIPUBBLICAZIONI(){
		super(false);
	}
	
	@Override
	public String getEntita() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postDelete(DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void postInsert(DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void postUpdate(DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void preDelete(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void preInsert(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void preUpdate(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {

		HttpServletRequest request = this.getRequest();
		String keys[] = request.getParameterValues(UtilityTags.DEFAULT_HIDDEN_KEYS_SELECTED);

		Long codiceGara = new Long(request.getParameter("codiceGara"));
		Long numeroPubblicazione = new Long(request.getParameter("numeroPubblicazione"));

		try {
			// rimuovo tutte le occorrenze in W9PUBLOTTO
			sqlManager.update("DELETE FROM W9PUBLOTTO WHERE CODGARA=? AND NUM_PUBB=?", new Object[] { codiceGara, numeroPubblicazione });

			// ciclo sulle keys inviate e reinserisco ogni occorrenza
			if (keys != null && keys.length > 0) {
				for (int i = 0; i < keys.length; i++) {
					Long codiceLotto = new Long(keys[i].split(";")[1].split(":")[1]);
					sqlManager.update("INSERT INTO W9PUBLOTTO (CODGARA,NUM_PUBB,CODLOTT) VALUES (?,?,?)", new Object[] { codiceGara, numeroPubblicazione, codiceLotto });
				}
			}

		} catch (SQLException e) {
			new GestoreException("Errore nell'aggiornamento dell'associazione tra lotti e pubblicazioni in una gara", "", e);
		}

	}

}