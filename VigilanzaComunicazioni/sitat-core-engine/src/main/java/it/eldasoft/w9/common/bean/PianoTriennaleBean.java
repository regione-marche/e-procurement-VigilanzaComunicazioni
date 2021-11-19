package it.eldasoft.w9.common.bean;

import java.io.Serializable;

/**
 * Bean con gli attributi necessari alla creazione della fase Piano triennale.
 * 
 * @author Luca.Giacomazzo
 */
public class PianoTriennaleBean implements Serializable {

  /** UID */
	private static final long serialVersionUID = -3774226935785937363L;

	private long codicePianoTriennale;
  
  private DatiComuniBean datiComuni;
  
  /**
   * @return the codicePianoTriennale
   */
  public long getCodicePianoTriennale() {
    return codicePianoTriennale;
  }

  /**
   * @param codicePianoTriennale the codicePianoTriennale to set
   */
  public void setCodicePianoTriennale(long codicePianoTriennale) {
    this.codicePianoTriennale = codicePianoTriennale;
  }

  /**
   * @return the datiComuniXml
   */
  public DatiComuniBean getDatiComuni() {
    return datiComuni;
  }

  /**
   * @param datiComuniXml the datiComuniXml to set
   */
  public void setDatiComuni(DatiComuniBean datiComuni) {
    this.datiComuni = datiComuni;
  }
  
}
