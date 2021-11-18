package it.eldasoft.w9.dao;

import it.eldasoft.gene.db.domain.BlobFile;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;


/**
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 *
 */
public interface W9FileDao {
  
  BlobFile getFileAllegato(String entita, HashMap<String, Object> params) throws DataAccessException;
  
  List<?> getDocumentiBando(Long codiceGara) throws DataAccessException;
  
}
