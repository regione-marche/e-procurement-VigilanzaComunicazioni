package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;

public class GestoreINRTRI extends AbstractGestoreChiaveNumerica {

	public String[] getAltriCampiChiave() {
		return new String[] { "CONTRI" };
	}

	public String getCampoNumericoChiave() {
		return "CONINT";
	}

	public String getEntita() {
		return "INRTRI";
	}

	public void postDelete(DataColumnContainer impl) throws GestoreException {
	}

	public void postInsert(DataColumnContainer impl) throws GestoreException {
	}

	public void postUpdate(DataColumnContainer impl) throws GestoreException {
	}

	public void preDelete(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
	}

	public void preInsert(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		Long lcontri = impl.getLong("INRTRI.CONTRI");
		String lcuiint = impl.getString("INRTRI.CUIINT");
		try {
			if (!controlloUnivocitaCui(lcuiint, lcontri, true)) {
				throw new GestoreException(
						"Errore nella valorizzazione del campo CUIINT, il codice non e' univoco.", "univocitaCuiInt.error");
			}
		} catch (SQLException e) {
				throw new GestoreException("Errore durante la modifica di un Intervento non riproposto", "modifica.intervento", e);
		}	
		
		super.preInsert(status, impl);

		this.getRequest().setAttribute("inserimento", "effettuato");
	}

	public void preUpdate(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

		int i = 1;
		while (impl.isColumn("INRTRI.CONTRI_" + i)) {
			Long parametro_contri = impl.getColumn("INRTRI.CONTRI_" + i).getValue().longValue();
			Long parametro_conint = impl.getColumn("INRTRI.CONINT_" + i).getValue().longValue();
			
			String parametro_cuiint;
      try {
        parametro_cuiint = (String) this.sqlManager.getObject("select CUIINT from INRTRI where CONTRI=? and CONINT=?",
            new Object[] { parametro_contri, parametro_conint });
      } catch (SQLException e1) {
        throw new GestoreException(
            "Errore nell'estrazione del campo CUIINT dell'intervento non riproposto", null);
      }
			//String parametro_cuiint = impl.getColumn("INRTRI.CUIINT_" + i).getValue().toString();
			
			String parametro_motivo = impl.getColumn("INRTRI.MOTIVO_" + i).getValue().stringValue();
			impl.removeColumns(new String[] { "INRTRI.CONTRI_" + i, "INRTRI.CONINT_" + i, "INRTRI.MOTIVO_" + i });
			i++;
			try {
				if (!controlloUnivocitaCui(parametro_cuiint, parametro_contri, false)) {
					throw new GestoreException(
							"Errore nella valorizzazione del campo CUIINT, il codice " + parametro_cuiint + " non e' univoco.", "univocitaCuiInt.error");
				}

				this.sqlManager.update("update INRTRI set MOTIVO = ? where CONTRI = ? and CONINT = ? ", new Object[] { parametro_motivo, parametro_contri, parametro_conint });
			} catch (SQLException e) {
				throw new GestoreException("Errore durante la modifica di un Intervento non riproposto", "modifica.intervento", e);
			}
		}
	}

	public boolean controlloUnivocitaCui(String cui, Long contri, boolean isInsert) throws SQLException {
	  boolean result = true;
		Object[] paramCui = new Object[] { cui, contri };
		String fromInttri = "select count(*) from inttri where cuiint= ? and contri = ? ";
		String fromInrtri = "select count(*) from inrtri where cuiint= ? and contri = ? ";
		Long numCuiInttri = (Long) sqlManager.getObject(fromInttri, paramCui);
		Long numCuiInrtri = (Long) sqlManager.getObject(fromInrtri, paramCui);
		if (isInsert) {
		  if (numCuiInttri.longValue() != 0 || numCuiInrtri.longValue() != 0)
		    result = false;
		} else {
		  if (numCuiInttri.longValue() != 0 || numCuiInrtri.longValue() != 1)
        result = false;
		}
		
		return result;
	}

}
