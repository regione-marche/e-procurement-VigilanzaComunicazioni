package it.eldasoft.sil.pt.db.dao.ibatis;

import it.eldasoft.gene.commons.web.spring.SqlMapClientDaoSupportBase;
import it.eldasoft.gene.db.domain.BlobFile;
import it.eldasoft.sil.pt.db.dao.PtFileDao;

import java.util.HashMap;

import org.springframework.dao.DataAccessException;


/**
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 *
 */
public class SqlMapPtFileDao extends SqlMapClientDaoSupportBase implements PtFileDao {
  
	  /**
	   * Funzione che restituisce il file allegato dell'entità specificata.
	   * 
	   * @param entita 
	   *        tabella relativa all'allegato
	   * @param params 
	   *        parametri
	   * @return file allegato
	   */
	
  public BlobFile getFileAllegato(String entita, HashMap<String, Object> params) throws DataAccessException {
    return (BlobFile) this.getSqlMapClientTemplate().queryForObject(
        "getFileAllegato" + entita, params);
   }

 

}