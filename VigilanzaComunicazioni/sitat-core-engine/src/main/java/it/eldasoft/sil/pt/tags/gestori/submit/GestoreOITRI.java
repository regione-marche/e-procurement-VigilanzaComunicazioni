package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.GestioneServiziCUPManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

public class GestoreOITRI extends AbstractGestoreChiaveNumerica {

	/** manager per operazioni di interrogazione del db. */
	private GestioneServiziCUPManager gestioneServiziCUPManager;

	public String[] getAltriCampiChiave() {
		return new String[] { "NUMOI" };
	}

	public String getCampoNumericoChiave() {
		return "CONINT";
	}

	public String getEntita() {
		return "OITRI";
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		super.setRequest(request);
		// Estraggo il manager di Piattaforma Gare
		gestioneServiziCUPManager = (GestioneServiziCUPManager) UtilitySpring
				.getBean("gestioneServiziCUPManager", this.getServletContext(),
						GestioneServiziCUPManager.class);
	}

	public void postDelete(DataColumnContainer impl) throws GestoreException {
	}

	public void postInsert(DataColumnContainer impl) throws GestoreException {

	}

	public void postUpdate(DataColumnContainer impl) throws GestoreException {

	}

	public void preDelete(TransactionStatus status, DataColumnContainer impl)
			throws GestoreException {

		String sqlSelectCancellazione = "";
		GeneManager geneOpere = this.getGeneManager();

		Long contri = impl.getColumn("OITRI.CONTRI").getValue().longValue();
		Long numoi = impl.getColumn("OITRI.NUMOI").getValue().longValue();

		String[] listaEntita = new String[] { "IMMTRAI" };

		for (int k = 0; k < listaEntita.length; k++) {
			sqlSelectCancellazione = "DELETE FROM " + listaEntita[k]
					+ " WHERE CONTRI = " + contri + " AND NUMOI = " + numoi;
			try {
				geneOpere.getSql().execute(sqlSelectCancellazione);
			} catch (SQLException e) {
				throw new GestoreException(
						"Errore durante la cancellazione di un Intervento",
						"cancellazione.intervento", e);
			}
		}
	}

	public void preInsert(TransactionStatus status, DataColumnContainer impl)
			throws GestoreException {

		Long lvendita = new Long(0);
		Long lcessione = new Long(0);
		if(StringUtils.isNotEmpty(impl.getColumn("OITRI.VENDITA").getValue().toString())){
			lvendita = new Long(impl.getColumn("OITRI.VENDITA").getValue().toString());
		}
		if(StringUtils.isNotEmpty(impl.getColumn("OITRI.CESSIONE").getValue().toString())){
			lcessione = new Long(impl.getColumn("OITRI.CESSIONE").getValue().toString());
		}
		// gestione schede multiple di IMMTRAI
		GestoreIMMTRAIMultiplo gest = new GestoreIMMTRAIMultiplo();
		gest.setGeneManager(this.geneManager);
		// Calcolo il max progressivo del cui immobili in db
		Long lcontri = impl.getLong("OITRI.CONTRI");

		String sqlSelectMaxNumoi = "select coalesce(max(NUMOI),0)+1 from OITRI where CONTRI = ? ";
		try {
			Long lnumoi = (Long) this.geneManager.getSql().getObject(
					sqlSelectMaxNumoi, new Object[] { lcontri });
			impl.setValue("OITRI.NUMOI", lnumoi);

			if (lvendita.equals(new Long(1)) || lcessione.equals(new Long(1))) {
			    ////////////////////////////////////////////////////////////////////////
	            //Nuova gestione Calcolo CUIIM (febbraio 2019)
			    //...
			    ////////////////////////////////////////////////////////////////////////
				this.getRequest().setAttribute("contri", lcontri);
				this.getRequest().setAttribute("numoi", lnumoi);

				gest.gestioneIMMTRAIdaOITRI(this.getRequest(), status, impl, this.getServletContext());
			}
		} catch (SQLException ec) {
			throw new GestoreException(
					"Errore durante la determinazione dei CUI associati all'intervento",
					"cui.intervento", ec);
		}

		//super.preInsert(status, impl);

	}

	public void preUpdate(TransactionStatus status, DataColumnContainer impl)
			throws GestoreException {
		// gestione schede multiple di IMMTRAI
		GestoreIMMTRAIMultiplo gest = new GestoreIMMTRAIMultiplo();
		gest.setGeneManager(this.geneManager);
		// Calcolo il max progressivo del cui immobili in db
		Long lcontri = impl.getLong("OITRI.CONTRI");
		Long lnumoi = impl.getLong("OITRI.NUMOI");
		      ////////////////////////////////////////////////////////////////////////
              //Nuova gestione Calcolo CUIIM (febbraio 2019)
              //...
              ////////////////////////////////////////////////////////////////////////
    			this.getRequest().setAttribute("contri", lcontri);
    			this.getRequest().setAttribute("numoi", lnumoi);
    			gest.gestioneIMMTRAIdaOITRI(this.getRequest(), status, impl,
    					this.getServletContext());
	}

}
