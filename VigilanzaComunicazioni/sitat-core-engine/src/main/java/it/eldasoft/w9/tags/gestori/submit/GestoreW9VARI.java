package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;

public class GestoreW9VARI extends AbstractGestoreChiaveNumericaEntitaW9 {

  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA", "CODLOTT" };
  }

  public String getCampoNumericoChiave() {
    return "NUM_VARI";
  }

  public String getEntita() {
    return "W9VARI";
  }

  public void postDelete(DataColumnContainer arg0) throws GestoreException {

  }

  public void postInsert(DataColumnContainer arg0) throws GestoreException {

  }

  public void postUpdate(DataColumnContainer arg0) throws GestoreException {

  }

  public void preDelete(TransactionStatus arg0, DataColumnContainer arg1)
      throws GestoreException {

  }

  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
    W9Manager m = (W9Manager) UtilitySpring.getBean("w9Manager",
        getServletContext(), W9Manager.class);
    DataColumnContainer implMotivi = null;
    
    boolean esisteGiaW9MOTI = this.geneManager.countOccorrenze("W9MOTI", 
        " codgara =? and codlott =? and num_vari =? ",
        new Object[]{
          impl.getColumn("W9VARI.CODGARA").getValue().longValue(),
          impl.getColumn("W9VARI.CODLOTT").getValue().longValue(),
          impl.getColumn("W9VARI.NUM_VARI").getValue().longValue() }) > 0;
    
    if (esisteGiaW9MOTI) {
      m.cancellazioneMoti(
          impl.getColumn("W9VARI.CODGARA").getValue().longValue(),
          impl.getColumn("W9VARI.CODLOTT").getValue().longValue(),
          impl.getColumn("W9VARI.NUM_VARI").getValue().longValue());
    }
    
    try {
    	List<?> listaMotivazioni = sqlManager.getListVector("select DISTINCT(TAB1TIP) from TAB1 where TAB1COD='W3017' OR TAB1COD='W3018'", new Object[] { });
        Long motivoValue = null;
        if (listaMotivazioni != null && listaMotivazioni.size()>0) {
        	for (int index = 0; index < listaMotivazioni.size(); index++) {
        		motivoValue = new Long(((List<?>) listaMotivazioni.get(index)).get(0).toString());
        		if((impl.isColumn("W9MOTI.ID_MOTIVO_VAR_" + motivoValue))) {
        			DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + motivoValue, false);
        		    implMotivi = new DataColumnContainer(campiImpl);
        		    Long idMotivo = implMotivi.getLong("W9MOTI.ID_MOTIVO_VAR");
        		    if (idMotivo != null && idMotivo.longValue() == 1) {
        		        implMotivi.setValue("W9MOTI.ID_MOTIVO_VAR",
        		        implMotivi.getLong("W9MOTI.VALORE_ID_MOTIVO_VAR"));
        		        implMotivi.removeColumns(new String[] { "W9MOTI.VALORE_ID_MOTIVO_VAR" });
        		        implMotivi.addColumn("W9MOTI.CODGARA", impl.getColumn("W9VARI.CODGARA"));
        		        implMotivi.addColumn("W9MOTI.CODLOTT", impl.getColumn("W9VARI.CODLOTT"));
        		        implMotivi.addColumn("W9MOTI.NUM_VARI", impl.getColumn("W9VARI.NUM_VARI"));
        		        implMotivi.getColumn("W9MOTI.CODGARA").setChiave(true);
        		        implMotivi.getColumn("W9MOTI.CODLOTT").setChiave(true);
        		        implMotivi.getColumn("W9MOTI.NUM_VARI").setChiave(true);

        		        this.inserisci(status, implMotivi, new GestoreW9MOTI());
        		    }
        		}
        	}
        }
    } catch (SQLException e) {
		throw new GestoreException("Errore nella lettura dei dati delle motivazioni nella variante " , null, e);
	}
    /*
    while (impl.isColumn("W9MOTI.ID_MOTIVO_VAR_" + i)) {

      DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + i, false);
      implMotivi = new DataColumnContainer(campiImpl);
      Long idMotivo = implMotivi.getLong("W9MOTI.ID_MOTIVO_VAR");
      if (idMotivo != null && idMotivo.longValue() == 1) {
        implMotivi.setValue("W9MOTI.ID_MOTIVO_VAR",
            implMotivi.getLong("W9MOTI.VALORE_ID_MOTIVO_VAR"));
        implMotivi.removeColumns(new String[] { "W9MOTI.VALORE_ID_MOTIVO_VAR" });
        implMotivi.addColumn("W9MOTI.CODGARA", impl.getColumn("W9VARI.CODGARA"));
        implMotivi.addColumn("W9MOTI.CODLOTT", impl.getColumn("W9VARI.CODLOTT"));
        implMotivi.addColumn("W9MOTI.NUM_VARI", impl.getColumn("W9VARI.NUM_VARI"));
        implMotivi.getColumn("W9MOTI.CODGARA").setChiave(true);
        implMotivi.getColumn("W9MOTI.CODLOTT").setChiave(true);
        implMotivi.getColumn("W9MOTI.NUM_VARI").setChiave(true);

        this.inserisci(status, implMotivi, new GestoreW9MOTI());
      }

      i++;
    }*/
  }

  public void preInsert(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {

    super.preInsert(status, impl);
    
    DataColumnContainer implMotivi = null;
    
    try {
    	List<?> listaMotivazioni = sqlManager.getListVector("select DISTINCT(TAB1TIP) from TAB1 where TAB1COD='W3017' OR TAB1COD='W3018'", new Object[] { });
        Long motivoValue = null;
        if (listaMotivazioni != null && listaMotivazioni.size()>0) {
        	for (int index = 0; index < listaMotivazioni.size(); index++) {
        		motivoValue = new Long(((List<?>) listaMotivazioni.get(index)).get(0).toString());
        		if((impl.isColumn("W9MOTI.ID_MOTIVO_VAR_" + motivoValue))) {
        			DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + motivoValue, false);
        		    implMotivi = new DataColumnContainer(campiImpl);
        		    Long idMotivo = implMotivi.getLong("W9MOTI.ID_MOTIVO_VAR");
        		    if (idMotivo != null && idMotivo.longValue() == 1) {
        		        implMotivi.setValue("W9MOTI.ID_MOTIVO_VAR",
        		        implMotivi.getLong("W9MOTI.VALORE_ID_MOTIVO_VAR"));
        		        implMotivi.removeColumns(new String[] { "W9MOTI.VALORE_ID_MOTIVO_VAR" });
        		        implMotivi.addColumn("W9MOTI.CODGARA", impl.getColumn("W9VARI.CODGARA"));
        		        implMotivi.addColumn("W9MOTI.CODLOTT", impl.getColumn("W9VARI.CODLOTT"));
        		        implMotivi.addColumn("W9MOTI.NUM_VARI", impl.getColumn("W9VARI.NUM_VARI"));
        		        implMotivi.getColumn("W9MOTI.CODGARA").setChiave(true);
        		        implMotivi.getColumn("W9MOTI.CODLOTT").setChiave(true);
        		        implMotivi.getColumn("W9MOTI.NUM_VARI").setChiave(true);

        		        this.inserisci(status, implMotivi, new GestoreW9MOTI());
        		    }
        		}
        	}
        }
    } catch (SQLException e) {
		throw new GestoreException("Errore nella lettura dei dati delle motivazioni nella variante " , null, e);
	}
    
    /*
    while (impl.isColumn("W9MOTI.ID_MOTIVO_VAR_" + i)) {

      DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + i, false);
      implMotivi = new DataColumnContainer(campiImpl);
      Long idMotivo = implMotivi.getLong("W9MOTI.ID_MOTIVO_VAR");
      if (idMotivo != null && idMotivo.longValue() == 1) {
        implMotivi.setValue("W9MOTI.ID_MOTIVO_VAR",
            implMotivi.getLong("W9MOTI.VALORE_ID_MOTIVO_VAR"));
        implMotivi.removeColumns(new String[] { "W9MOTI.VALORE_ID_MOTIVO_VAR" });
        implMotivi.addColumn("W9MOTI.CODGARA", impl.getColumn("W9VARI.CODGARA"));
        implMotivi.addColumn("W9MOTI.CODLOTT",
            impl.getColumn("W9VARI.CODLOTT"));
        implMotivi.addColumn("W9MOTI.NUM_VARI",
            impl.getColumn("W9VARI.NUM_VARI"));
        implMotivi.getColumn("W9MOTI.CODGARA").setChiave(true);
        implMotivi.getColumn("W9MOTI.CODLOTT").setChiave(true);
        implMotivi.getColumn("W9MOTI.NUM_VARI").setChiave(true);

        this.inserisci(status, implMotivi, new GestoreW9MOTI());
      }

      i++;
    }
    */
  }
}
