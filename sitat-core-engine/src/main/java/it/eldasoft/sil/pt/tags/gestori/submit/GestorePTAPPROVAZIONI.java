package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

/**
 *
 * @author C.F.
 *
 */
public class GestorePTAPPROVAZIONI extends AbstractGestoreChiaveNumerica {

	@Override
	public String[] getAltriCampiChiave() {
		return new String[] { "CONINT" };
	}

	@Override
	public String getCampoNumericoChiave() {
		return "NUMAPPR";
	}

	@Override
	public String getEntita() {
		return "PTAPPROVAZIONI";
	}

	@Override
	public void postDelete(DataColumnContainer impl) throws GestoreException {

	}

	@Override
	public void postInsert(DataColumnContainer impl) throws GestoreException {
	  String updateFABBISOGNI = "UPDATE FABBISOGNI SET STATO = ? WHERE CONINT = ?";
	  Long  conint = impl.getLong("PTAPPROVAZIONI.CONINT");
	  Long  esitoApprovazione = impl.getLong("PTAPPROVAZIONI.ESITOAPPR");
	  //////////////////////////////////
	  //Raccolta fabbisogni: modifiche per prototipo dicembre 2018
	  //vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018)
	  String funzionalita =  getRequest().getParameter("funzionalita");	  
//	  String chiavi_concatenate =  getRequest().getParameter("chiavi_concatenate");
//	  String[] chiavi = new String[] {};
//      chiavi = chiavi_concatenate.split(";;"); 
//      Long conint2 = (long) 0;
//      for (int i = 0; i < chiavi.length; i++) {
//          DataColumnContainer fabbisogniItem = new DataColumnContainer(
//                  chiavi[i]);          
//          conint2 = (fabbisogniItem.getColumnsBySuffix("CONINT", true))[0]
//                  .getValue().longValue();
//      }
	  ////////////////////////////////// 
	  
      try {
        TransactionStatus status = this.sqlManager.startTransaction();
          //////////////////////////////////
          //Raccolta fabbisogni: modifiche per prototipo dicembre 2018
          //vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018)
          if ("RAF_ApprovaRespingi".equals(funzionalita)) {
            if (new Long(1).equals(esitoApprovazione)) {
              this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(4), conint });
            } else if (new Long(2).equals(esitoApprovazione)) {
              this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(31), conint });
            } else if (new Long(3).equals(esitoApprovazione)) {
              this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(11), conint });
            }
          } else if ("RDP_RichiediRevisioneRespingi".equals(funzionalita)) {
            if (new Long(2).equals(esitoApprovazione)) {
              this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(31), conint });
            } else if (new Long(3).equals(esitoApprovazione)) {
              this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(12), conint });
            }
          } else if ("RDS_RichiediRevisione".equals(funzionalita)) {
        	  this.sqlManager.update(updateFABBISOGNI, new Object[] {new Long(11), conint });
          }
          //////////////////////////////////
        this.sqlManager.commitTransaction(status);
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore nel\'aggiornamento dello stato del fabbisogno",
            "Stato fabbisogno ", e);
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
