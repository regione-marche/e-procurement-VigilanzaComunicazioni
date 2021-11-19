package it.eldasoft.w9.dao.ibatis;

import it.eldasoft.gene.commons.web.spring.SqlMapClientDaoSupportBase;
import it.eldasoft.gene.db.domain.BlobFile;
import it.eldasoft.w9.dao.W9FileDao;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;


/**
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 *
 */
public class SqlMapW9FileDao extends SqlMapClientDaoSupportBase implements W9FileDao {
  
  public BlobFile getFileAllegato(String entita, HashMap<String, Object> params) throws DataAccessException {
    return (BlobFile) this.getSqlMapClientTemplate().queryForObject(
        "getFileAllegato" + entita, params);
   }

  public List<?> getDocumentiBando(Long codiceGara) throws DataAccessException {
    return this.getSqlMapClientTemplate().queryForList("getDocumentiBando", codiceGara);
  }

}