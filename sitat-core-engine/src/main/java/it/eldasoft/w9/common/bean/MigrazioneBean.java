package it.eldasoft.w9.common.bean;

import java.io.Serializable;

public class MigrazioneBean implements Serializable {

  /** UID */
	private static final long serialVersionUID = -7318668953213605691L;

	private int numeroGareDaMigrare = 0;

  private String codiceFiscaleStazAppOrigine = null;
  private String codiceStazAppOrigine = null;
  private String nomeStazAppOrigine = null;
  //private String codiceFiscaleStazAppDestinazione = null;
  //private String codiceStazAppDestinazione = null;
  //private String nomeStazAppDestinazione = null;
  
  private long[] arrayCodiciGareDaMigrare = null;
  
  private int garaInElaborazione = Integer.MIN_VALUE;
  private int numeroGareRecuperate = 0;
  private int numeroGareControllate = 0;
  private int numeroGareMigrate = 0;
  
  private String errore = null;
  
  private GaraSABean[] garaSABean = null;
  
  public MigrazioneBean(int numeroGareDaMigrare) {
    this.numeroGareDaMigrare = numeroGareDaMigrare;
    this.arrayCodiciGareDaMigrare = new long[numeroGareDaMigrare];
    this.garaSABean = new GaraSABean[numeroGareDaMigrare];
  }
  
  /**
   * @return the numeroGareDaMigrare
   */
  public int getNumeroGareDaMigrare() {
    return this.numeroGareDaMigrare;
  }

  /**
   * @return the codiceFiscaleStazAppOrigine
   */
  public String getCodiceFiscaleStazAppOrigine() {
    return codiceFiscaleStazAppOrigine;
  }

  /**
   * @param codiceFiscaleStazAppOrigine the codiceFiscaleStazAppOrigine to set
   */
  public void setCodiceFiscaleStazAppOrigine(String codiceFiscaleStazAppOrigine) {
    this.codiceFiscaleStazAppOrigine = codiceFiscaleStazAppOrigine;
  }

  /**
   * @return the codiceFiscaleStazAppDestinazione
   *
  public String getCodiceFiscaleStazAppDestinazione() {
    return codiceFiscaleStazAppDestinazione;
  }*/

  /**
   * @param codiceFiscaleStazAppDestinazione the codiceFiscaleStazAppDestinazione to set
   *
  public void setCodiceFiscaleStazAppDestinazione(String codiceFiscaleStazAppDestinazione) {
    this.codiceFiscaleStazAppDestinazione = codiceFiscaleStazAppDestinazione;
  }*/

  /**
   * @return the codiceStazAppOrigine
   */
  public String getCodiceStazAppOrigine() {
    return codiceStazAppOrigine;
  }

  /**
   * @param codiceStazAppOrigine the codiceStazAppOrigine to set
   */
  public void setCodiceStazAppOrigine(String codiceStazAppOrigine) {
    this.codiceStazAppOrigine = codiceStazAppOrigine;
  }

  /**
   * @return the codiceStazAppDestinazione
   *
  public String getCodiceStazAppDestinazione() {
    return codiceStazAppDestinazione;
  }*/

  /**
   * @param codiceStazAppDestinazione the codiceStazAppDestinazione to set
   *
  public void setCodiceStazAppDestinazione(String codiceStazAppDestinazione) {
    this.codiceStazAppDestinazione = codiceStazAppDestinazione;
  }*/

  /**
   * @return the nomeStazAppOrigine
   */
  public String getNomeStazAppOrigine() {
    return nomeStazAppOrigine;
  }

  /**
   * @param nomeStazAppOrigine the nomeStazAppOrigine to set
   */
  public void setNomeStazAppOrigine(String nomeStazAppOrigine) {
    this.nomeStazAppOrigine = nomeStazAppOrigine;
  }

  /**
   * @return the nomeStazAppDestinazione
   *
  public String getNomeStazAppDestinazione() {
    return nomeStazAppDestinazione;
  }*/

  /**
   * @param nomeStazAppDestinazione the nomeStazAppDestinazione to set
   *
  public void setNomeStazAppDestinazione(String nomeStazAppDestinazione) {
    this.nomeStazAppDestinazione = nomeStazAppDestinazione;
  }*/

  /**
   * @return the arrayCodiciGareDaMigrare
   */
  public long[] getArrayCodiciGareDaMigrare() {
    return this.arrayCodiciGareDaMigrare;
  }
  
  /**
   * Ritorna il CODGAR della gara dell'indice indicato.
   * @param indice
   * @return Ritorna il CODGAR della gara dell'indice indicato 
   */
  public long getCodiceGaraDaMigrare(int indice) {
    return this.arrayCodiciGareDaMigrare[indice];
  }

  /**
   * Ritorna il CODGAR della gara in elaborazione.
   * @return Ritorna il CODGAR della gara in elaborazione.
   */
  public long getCodiceGaraDaMigrareInElaborazione() {
    return this.arrayCodiciGareDaMigrare[this.garaInElaborazione];
  }
  
  /**
   * @param arrayCodiciGareDaMigrare the arrayCodiciGareDaMigrare to set
   */
  public void setArrayCodiciGareDaMigrare(long[] arrayCodiciGareDaMigrare) {
    this.arrayCodiciGareDaMigrare = arrayCodiciGareDaMigrare;
  }

  /**
   * @return the garaInElaborazione
   */
  public long getGaraInElaborazione() {
    return this.garaInElaborazione;
  }

  public void passaAllaGaraSuccessiva() {
    if (this.garaInElaborazione < 0) {
      this.garaInElaborazione = 0;
    } else {
      this.garaInElaborazione++;
    }
  }
  
  /**
   * Reset della gara in elaborazione.
   */
  public void resetGaraInElaborazione() {
    this.garaInElaborazione = Integer.MIN_VALUE;
  }

  /**
   * @return the migrazioneStazioneAppaltanteBean
   */
  public GaraSABean getMigrazioneSABean(int indice) {
    return this.garaSABean[indice];
  }
  
  /**
   * Ritorna l'oggetto migrazioneStazioneAppaltanteBean in elaborazione.
   * @return the migrazioneStazioneAppaltanteBean in elaborazione
   */
  public GaraSABean getGaraSABeanInElaborazione() {
    return this.garaSABean[this.garaInElaborazione];
  }

  public void setGaraBeanInElaborazione(
      GaraSABean garaSABean) {
    this.garaSABean[this.garaInElaborazione] = garaSABean;
  }
  
  /**
   * @return the numeroGareRecuperate
   */
  public int getNumeroGareRecuperate() {
    return this.numeroGareRecuperate;
  }

  public void incrementaNumeroXmlRecuperati() {
    this.numeroGareRecuperate++;
  }
  
  public void setNumeroGareRecuperate(int numeroGare) {
    this.numeroGareRecuperate = numeroGare;
  }
  
  /**
   * @return the numeroGareControllate
   */
  public int getNumeroGareControllate() {
    return this.numeroGareControllate;
  }

  public void incrementaNumeroGareControllate() {
    this.numeroGareControllate++;
  }
  
  /**
   * @return the numeroGareMigrate
   */
  public int getNumeroGareMigrate() {
    return numeroGareMigrate;
  }
  
  public void incrementaNumeroGareMigrate() {
    this.numeroGareMigrate++;
  }

  /**
   * @return the errore
   */
  public String getErrore() {
    return errore;
  }

  /**
   * @param errore the errore to set
   */
  public void setErrore(String errore) {
    this.errore = errore;
  }

  /**
   * @return the garaSABean
   */
  public GaraSABean[] getGaraSABean() {
    return garaSABean;
  }
  
}
