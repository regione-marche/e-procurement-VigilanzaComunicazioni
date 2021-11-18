package it.eldasoft.sil.pt.db.dao;

import it.eldasoft.gene.db.domain.BlobFile;

import java.util.HashMap;

import org.springframework.dao.DataAccessException;


/**
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 *
 */
public interface PtFileDao {
  
	  /**
	   * Funzione che restituisce il file allegato dell'entità specificata.
	   * 
	   * @param entita 
	   *        tabella relativa all'allegato
	   * @param params 
	   *        parametri
	   * @return file allegato
	   */
	
  BlobFile getFileAllegato(String entita, HashMap<String, Object> params) throws DataAccessException;
  
}
