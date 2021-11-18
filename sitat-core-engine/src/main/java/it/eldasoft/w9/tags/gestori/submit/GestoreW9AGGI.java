package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9AggiudicatarieManager;


public class GestoreW9AGGI extends AbstractGestoreEntita{

	public GestoreW9AGGI() {
		super(false);
	}
	
	@Override
	public String getEntita() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preDelete(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {
		W9AggiudicatarieManager w9AggiudicatarieManager = (W9AggiudicatarieManager) UtilitySpring.getBean("w9AggiudicatarieManager", this.getServletContext(), W9AggiudicatarieManager.class);
		
		Long flusso = datiForm.getColumn("FASE_ESECUZIONE").getValue().longValue();
		Long codiceGara = datiForm.getColumn("CODGARA").getValue().longValue();
		Long numAppa = datiForm.getColumn("NUM_APPA").getValue().longValue();
		Long codiceLotto = datiForm.getColumn("CODLOTT").getValue().longValue();
		Long numImpr = datiForm.getColumn("NUM_AGGI").getValue().longValue();
		Long idGruppo = datiForm.getColumn("ID_GRUPPO").getValue().longValue();
		
		try {
			if(idGruppo != null){
				w9AggiudicatarieManager.deleteGruppoImprese(flusso, codiceGara, numAppa, codiceLotto, idGruppo);
			}
			else{
				w9AggiudicatarieManager.deleteImpresaSingola(flusso, codiceGara, numAppa, codiceLotto, numImpr);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new GestoreException("Errore nell'eliminazione dei dati dal database.","", e);
		}

		
	}

	@Override
	public void postDelete(DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preInsert(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInsert(DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preUpdate(TransactionStatus status, DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postUpdate(DataColumnContainer datiForm) throws GestoreException {
		// TODO Auto-generated method stub
		
	}

}
