package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.spring.UtilitySpring;

import org.springframework.transaction.TransactionStatus;

/**
 *
 * @author Luca.Giacomazzo
 *
 */
public class GestoreRISCAPITOLO extends AbstractGestoreChiaveNumerica {

	@Override
	public String[] getAltriCampiChiave() {
		return new String[] { "CONTRI", "CONINT" };
	}

	@Override
	public String getCampoNumericoChiave() {
		return "NUMCB";
	}

	@Override
	public String getEntita() {
		return "RIS_CAPITOLO";
	}

	@Override
	public void postDelete(DataColumnContainer impl) throws GestoreException {

	}

	@Override
	public void postInsert(DataColumnContainer impl) throws GestoreException {

	}

	@Override
  public void afterInsertEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
	  try {
		Long contri = impl.getLong("RIS_CAPITOLO.CONTRI");
		Long conint = impl.getLong("RIS_CAPITOLO.CONINT");
		PtManager ptManager = (PtManager) UtilitySpring.getBean("ptManager", getServletContext(), PtManager.class);
		ptManager.aggiornaCostiInttri(contri, conint);
		if(contri != null && !new Long(0).equals(contri)){
	        ptManager.aggiornaCostiPiatri(contri);
	        ptManager.updateStatoProgramma(contri, new Long(3), new Long(2), new Long(5));
		} else {
			//se il fabbisogno non è ancora stato assegnato ad un programma
			//Una proposta Completata deve essere riportata in stato "In compilazione" quando vengono modificati i dati dei capitoli
			ptManager.updateCapitoli(conint);
		}
      } catch (Exception e) {
	    throw new GestoreException(
	        "Errore durante il ricalcolo dei costi complessivi dell'intervento",
	        "intervento.aggiornamento.ricalcoloCostiProgramma", e);
	  }
	}

	@Override
  public void afterUpdateEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
	  try {
		Long contri = impl.getLong("RIS_CAPITOLO.CONTRI");
		Long conint = impl.getLong("RIS_CAPITOLO.CONINT");
		PtManager ptManager = (PtManager) UtilitySpring.getBean("ptManager", getServletContext(), PtManager.class);
		ptManager.aggiornaCostiInttri(contri, conint);
		if(contri != null && !new Long(0).equals(contri)){
		  ptManager.aggiornaCostiPiatri(contri);
		  ptManager.updateStatoProgramma(contri, new Long(3), new Long(2), new Long(5));
		} else {
			//se il fabbisogno non è ancora stato assegnato ad un programma
			//Una proposta Completata deve essere riportata in stato "In compilazione" quando vengono modificati i dati dei capitoli
			ptManager.updateCapitoli(conint);
		}
	  } catch (Exception e) {
		throw new GestoreException(
		    "Errore durante il ricalcolo dei costi complessivi dell'intervento",
		    "intervento.aggiornamento.ricalcoloCostiProgramma", e);
	  }
	}

	@Override
  public void afterDeleteEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
	  try {
		Long contri = impl.getLong("RIS_CAPITOLO.CONTRI");
		Long conint = impl.getLong("RIS_CAPITOLO.CONINT");
		PtManager ptManager = (PtManager) UtilitySpring.getBean("ptManager", getServletContext(), PtManager.class);
		ptManager.aggiornaCostiInttri(contri, conint);
		if(contri != null && !new Long(0).equals(contri)){
	       ptManager.aggiornaCostiPiatri(contri);
	       ptManager.updateStatoProgramma(contri, new Long(3), new Long(2), new Long(5));
	    } else {
			//se il fabbisogno non è ancora stato assegnato ad un programma
			//Una proposta Completata deve essere riportata in stato "In compilazione" quando vengono modificati i dati dei capitoli
			ptManager.updateCapitoli(conint);
		}
	  } catch (Exception e) {
		throw new GestoreException(
		    "Errore durante il ricalcolo dei costi complessivi dell'intervento",
		    "intervento.aggiornamento.ricalcoloCostiProgramma", e);
	  }
	}

	@Override
	public void postUpdate(DataColumnContainer arg0) throws GestoreException {
	}

	@Override
	public void preDelete(TransactionStatus arg0, DataColumnContainer arg1)
			throws GestoreException {

	}

	@Override
	public void preUpdate(TransactionStatus arg0, DataColumnContainer arg1)
			throws GestoreException {

	}

}
