package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9AggiudicatarieManager;


public class GestoreESITI_AGGIUDICATARI extends AbstractGestoreEntita{

	public GestoreESITI_AGGIUDICATARI() {
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
		
		Long codiceGara = datiForm.getColumn("CODGARA").getValue().longValue();
		Long numPubb = datiForm.getColumn("NUM_PUBB").getValue().longValue();
		Long numImpr = datiForm.getColumn("NUM_AGGI").getValue().longValue();
		Long idGruppo = datiForm.getColumn("ID_GRUPPO").getValue().longValue();
		
		try {
			if(idGruppo != null){
				w9AggiudicatarieManager.deleteGruppoImpreseEsito(codiceGara, numPubb, idGruppo);
			}
			else{
				w9AggiudicatarieManager.deleteImpresaSingolaEsito(codiceGara, numPubb, numImpr);
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
