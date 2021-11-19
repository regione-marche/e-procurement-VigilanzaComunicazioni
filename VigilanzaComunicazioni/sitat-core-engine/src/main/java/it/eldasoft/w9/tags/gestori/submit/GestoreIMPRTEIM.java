package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreIMPR;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestoreIMPRTEIM extends GestoreIMPR {

	@Override
	public void preInsert(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		
		super.preInsert(status, impl);

		String codiceImpresa = impl.getString("IMPR.CODIMP");
		String nomeTecni = impl.getString("TEIM.NOMETIM");
		String cognomeTecni = impl.getString("TEIM.COGTIM");
		String intestazioneTecni = nomeTecni + " " + cognomeTecni;
		String codiceFiscaleTecni = impl.getString("TEIM.CFTIM");
		String codiceAnagraficoGenerale = impl.getString("IMPR.CGENIMP");
		
		String sqlInsertTecni = "INSERT into TEIM (CODTIM,COGTIM,NOMETIM,NOMTIM,CFTIM,CGENTIM) values (?,?,?,?,?,?)";
		String sqlInsertImpLeg = "INSERT into IMPLEG (ID,CODIMP2,CODLEG,NOMLEG) values (?,?,?,?)";
		
		GenChiaviManager genChiaviManager = (GenChiaviManager) 
        UtilitySpring.getBean("genChiaviManager", this.getServletContext(),
            GenChiaviManager.class);
		
		int nextId = genChiaviManager.getNextId("IMPLEG");
		
		try {
			this.sqlManager.update(sqlInsertTecni, new Object[] { codiceImpresa, cognomeTecni, nomeTecni, intestazioneTecni, codiceFiscaleTecni, codiceAnagraficoGenerale });
			this.sqlManager.update(sqlInsertImpLeg, new Object[] { new Long(nextId), codiceImpresa, codiceImpresa, intestazioneTecni });
		} catch (SQLException e) {
			throw new GestoreException("Errore durante l'inserimento dell'impresa nel portale", null, e);
		}
	}

	@Override
	public void preDelete(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		
		String codiceImpresa = impl.getString("IMPR.CODIMP");

		super.preDelete(status, impl);
		
		String sqlDeleteTecni = "Delete from TEIM where CODTIM=?";
		
		try {
			this.sqlManager.update(sqlDeleteTecni, new Object[] { codiceImpresa });
		} catch (SQLException e) {
			throw new GestoreException("Errore durante l'eliminazione dell'impresa dal portale", null, e);
		}
	
	}

	@Override
	public void preUpdate(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		
	  if (impl.isModifiedTable("TEIM")) {
	    
	    GenChiaviManager genChiaviManager = (GenChiaviManager) 
	        UtilitySpring.getBean("genChiaviManager", this.getServletContext(),
	              GenChiaviManager.class);
	    
  		String codiceImpresa = impl.getString("IMPR.CODIMP");
  		String nomeTecni = null;
  		String cognomeTecni = null;
  		String intestazioneTecni = null;
  		
  		if (StringUtils.isNotEmpty(impl.getString("TEIM.NOMETIM")) && !"null".equalsIgnoreCase(impl.getString("TEIM.NOMETIM"))) {
  		  nomeTecni = impl.getString("TEIM.NOMETIM");
  		  intestazioneTecni = nomeTecni;
  		}
  		if (StringUtils.isNotEmpty(impl.getString("TEIM.COGTIM")) && !"null".equalsIgnoreCase(impl.getString("TEIM.COGTIM"))) {
  		  cognomeTecni = impl.getString("TEIM.COGTIM");
  		  if (intestazioneTecni != null) {
  		    intestazioneTecni = nomeTecni + " " + cognomeTecni;
  		  } else {
  		    intestazioneTecni = cognomeTecni;
  		  }
  		}
  		
  		String codiceFiscaleTecni = impl.getString("TEIM.CFTIM");
  		String codiceAnagraficoGenerale = impl.getString("IMPR.CGENIMP");
  		
  		String sqlInsertTecni = "insert into TEIM (CODTIM,COGTIM,NOMETIM,NOMTIM,CFTIM,CGENTIM) values (?,?,?,?,?,?)";
  		String sqlInsertImpLeg = "INSERT into IMPLEG (ID,CODIMP2,CODLEG,NOMLEG) values (?,?,?,?)";
  		
  		int nextId = genChiaviManager.getNextId("IMPLEG");
  		
  		try {
  		  String codiceTeim = impl.getString("TEIM.CODTIM");
  		  if (StringUtils.isNotEmpty(codiceTeim)) {
  		    this.sqlManager.update("delete from TEIM where CODTIM=?", new Object[] { codiceTeim } );
  		    this.sqlManager.update("delete from IMPLEG where CODIMP2=? and CODLEG=?",
  		        new Object[] { codiceImpresa, codiceTeim } );
  		  }

  			this.sqlManager.update(sqlInsertTecni, new Object[] { codiceImpresa, cognomeTecni, nomeTecni,
  			    intestazioneTecni, codiceFiscaleTecni, codiceAnagraficoGenerale });
  			this.sqlManager.update(sqlInsertImpLeg, new Object[] { new Long(nextId), codiceImpresa, codiceImpresa, intestazioneTecni });
  		} catch (SQLException e) {
  			throw new GestoreException("Errore durante l'aggiornamento dell'impresa nel portale", null, e);
  		}
	  }

		super.preUpdate(status, impl);
	}

}
