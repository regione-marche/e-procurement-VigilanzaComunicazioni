package it.eldasoft.w9.common.bean;

import java.io.Serializable;

/**
 * Bean contentente tutti gli oggetti necessari all'operazione di migrazione delle
 * stazioni appaltanti per una gara. 
 * 
 * @author Luca.Giacomazzo
 */
public class GaraSABean implements Serializable {

  /** UID */
	private static final long serialVersionUID = 7477314946023293508L;
	private Long     codiceGara    = null;
  private String   idAvGara      = null;
  private String   oggettoGara   = null;
  private String[] codiciCig     = null;
  private String   xmlSimog      = null;
  private String   cfsaOrigine   = null;
  private String   codeinOrigine = null;
  private String   cfsaDestinazione   = null;
  private String   codeinDestinazione = null;
  private String   descrErrore   = null;
  private boolean  ok           = true;
  
  private String   idStazAppaltanteSimog = null;
  private String   denomStazAppaltanteSimog = null;
  private String   codiceCentroDiCosto = null;
  private String   denominazioneCentroDiCosto = null;  

  public GaraSABean(Long codiceGara) {
    this.codiceGara = codiceGara;
  }
  
  /**
   * @return the codiceGara
   */
  public Long getCodiceGara() {
    return this.codiceGara;
  }

  /**
   * @param codiceGara the codiceGara to set
   */
  public void setCodiceGara(Long codiceGara) {
    this.codiceGara = codiceGara;
  }

  /**
   * @return the codiceCig
   */
  public String[] getCodiciCig() {
    return this.codiciCig;
  }

  /**
   * @return the codiciCig
   */
  public String getCodiceCig(int indice) {
    return this.codiciCig[indice];
  }
  
  /**
   * @param codiceCig the codiceCig to set
   */
  public void setCodiciCig(String[] codiceCig) {
    this.codiciCig = codiceCig;
  }

  /**
   * @param codiceCig[i] the codiceCig to set
   */
  public void setCodiceCig(int indice, String codiceCig) {
    this.codiciCig[indice] = codiceCig;
  }
  
  /**
   * @return the xmlSimog
   */
  public String getXmlSimog() {
    return this.xmlSimog;
  }
  
  /**
   * @param xmlSimog the xmlSimog to set
   */
  public void setXmlSimog(String xmlSimog) {
    this.xmlSimog = xmlSimog;
  }

  /**
   * @return the cfsaDestinazione
   */
  public String getCfsaDestinazione() {
    return this.cfsaDestinazione;
  }

  /**
   * @param cfsaDestinazione the cfsaDestinazione to set
   */
  public void setCfsaDestinazione(String cfsaDestinazione) {
    this.cfsaDestinazione = cfsaDestinazione;
  }

  /**
   * @return the codeinDestinazione.
   */
  public String getCodeinDestinazione() {
    return this.codeinDestinazione;
  }

  /**
   * @param codeinDestinazione the codeinDestinazione to set.
   */
  public void setCodeinDestinazione(String codeinDestinazione) {
    this.codeinDestinazione = codeinDestinazione;
  }
  
  
  /**
   * @return the descrErrore
   */
  public String getDescrErrore() {
    return this.descrErrore;
  }

  /**
   * @param descrErrore the descrErrore to set
   */
  public void setDescrErrore(String descrErrore) {
    this.descrErrore = descrErrore;
  }

  /**
   * @return the ok
   */
  public boolean isOk() {
    return this.ok;
  }

  /**
   * @param ok the ok to set
   */
  public void setOk(boolean ok) {
    this.ok = ok;
  }

  /**
   * @return the idAvGara
   */
  public String getIdAvGara() {
    return this.idAvGara;
  }

  /**
   * @param idAvGara the idAvGara to set
   */
  public void setIdAvGara(String idAvGara) {
    this.idAvGara = idAvGara;
  }

  /**
   * @return the oggettoGara
   */
  public String getOggettoGara() {
    return this.oggettoGara;
  }

  /**
   * @param oggettoGara the oggettoGara to set
   */
  public void setOggettoGara(String oggettoGara) {
    this.oggettoGara = oggettoGara;
  }

  /**
   * @return the cfsaOrigine
   */
  public String getCfsaOrigine() {
    return this.cfsaOrigine;
  }

  /**
   * @param cfsaOrigine the cfsaOrigine to set
   */
  public void setCfsaOrigine(String cfsaOrigine) {
    this.cfsaOrigine = cfsaOrigine;
  }

  /**
   * @return the codeinOrigine
   */
  public String getCodeinOrigine() {
    return this.codeinOrigine;
  }

  /**
   * @param codeinOrigine the codeinOrigine to set
   */
  public void setCodeinOrigine(String codeinOrigine) {
    this.codeinOrigine = codeinOrigine;
  }

  /**
   * @return the idStazAppaltanteSimog
   */
  public String getIdStazAppaltanteSimog() {
    return idStazAppaltanteSimog;
  }

  /**
   * @param idStazAppaltanteSimog the idStazAppaltanteSimog to set
   */
  public void setIdStazAppaltanteSimog(String idStazAppaltanteSimog) {
    this.idStazAppaltanteSimog = idStazAppaltanteSimog;
  }

  /**
   * @return the denomStazAppaltanteSimog
   */
  public String getDenomStazAppaltanteSimog() {
    return denomStazAppaltanteSimog;
  }

  /**
   * @param denomStazAppaltanteSimog the denomStazAppaltanteSimog to set
   */
  public void setDenomStazAppaltanteSimog(String denomStazAppaltanteSimog) {
    this.denomStazAppaltanteSimog = denomStazAppaltanteSimog;
  }

  public String getCodiceCentroDiCosto() {
    return codiceCentroDiCosto;
  }

  public void setCodiceCentroDiCosto(String codiceCentroDiCosto) {
    this.codiceCentroDiCosto = codiceCentroDiCosto;
  }

  public String getDenominazioneCentroDiCosto() {
    return denominazioneCentroDiCosto;
  }

  public void setDenominazioneCentroDiCosto(String denominazioneCentroDiCosto) {
    this.denominazioneCentroDiCosto = denominazioneCentroDiCosto;
  }

}
