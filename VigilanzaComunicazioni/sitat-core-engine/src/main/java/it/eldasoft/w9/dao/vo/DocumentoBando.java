package it.eldasoft.w9.dao.vo;

import it.eldasoft.gene.db.domain.BlobFile;

/**
 * DocumentoBando.
 */
public class DocumentoBando extends BlobFile {

  /**   UID.   */
  private static final long serialVersionUID = -2102868635816879704L;

  /** Titolo del documento. */
  private String titolo;
  
  /** Costruttore. */
  public DocumentoBando() {
    super();
    this.titolo = null;
  }

  /**
   * Ritorna il titolo del documento del bando.
   * 
   * @return Ritorna il titolo
   */
  public String getTitolo() {
    return titolo;
  }
  
  /**
   * Set del titolo del documento.
   * 
   * @param titolo Titolo del documento del bando
   */
  public void setTitolo(String titolo) {
    this.titolo = titolo;
  }
  
}