package it.eldasoft.w9.bl;

import java.io.Serializable;
import java.util.LinkedList;

public class ConsultaGaraLottiBean implements Serializable {

  /** UID */
	private static final long serialVersionUID = 3684793146383094785L;
	// informazioni necessarie per ogni richiesta
  private String codiceCIG;
  private String idAvGara;
  private String simogUser;
  private String simogPassword;
  private Long codgara;
  
  private LinkedList<String> listaCig;
  private int numeroTotaleLotti = 0;
  private int numeroLottiImportati = 0;
  private int numeroLottiEsistenti = 0;
  private int numeroLottiNonImportati = 0;

  /**
   * Costruttore unico.
   * 
   * @param codiceCig
   * @param idAvGara
   * @param simogUser
   * @param simogPassword
   */
  public ConsultaGaraLottiBean(String codiceCig, String idAvGara, String simogUser, String simogPassword) {
    this.codiceCIG = codiceCig;
    this.idAvGara = idAvGara;
    this.simogUser = simogUser;
    this.simogPassword = simogPassword;
    this.listaCig = new LinkedList<String>();
  }
  
  /**
   * @return the codiceCIG
   */
  public String getCodiceCIG() {
    return this.codiceCIG;
  }
  
  /**
   * @return the idAvGara
   */
  public String getIdAvGara() {
    return this.idAvGara;
  }
  
  /**
   * @return the simogUser
   */
  public String getSimogUser() {
    return this.simogUser;
  }
  
  /**
   * @return the simogPassword
   */
  public String getSimogPassword() {
    return this.simogPassword;
  }
  
  /**
   * @return the listaCig
   */
  public LinkedList<String> getListaCig() {
    return this.listaCig;
  }

  /**
   * @param listaCig the listaCig to set
   */
  /*public void setListaCig(LinkedList<String> listaCig) {
    this.listaCig = listaCig;
  }*/
  
  /**
   * @return the numeroTotaleLotti
   */
  public int getNumeroTotaleLotti() {
    return this.numeroTotaleLotti;
  }

  /**
   * @param numeroTotaleLotti the numeroTotaleLotti to set
   */
  public void setNumeroTotaleLotti(int numeroTotaleLotti) {
    this.numeroTotaleLotti = numeroTotaleLotti;
  }

  /**
   * @return the numeroLottiImportati
   */
  public int getNumeroLottiImportati() {
    return this.numeroLottiImportati;
  }

  /**
   * Incremento del numero di lotti importati
   */
  public void incrementaNumeroLottiImportati() {
    this.numeroLottiImportati++;
  }

  /**
   * @return the numeroLottiEsistenti
   */
  public int getNumeroLottiEsistenti() {
    return this.numeroLottiEsistenti;
  }

  /**
   * Incremento del numero di lotti esistenti
   */
  public void incrementaNumeroLottiEsistenti() {
    this.numeroLottiEsistenti++;
  }

  /**
   * @return the numeroLottiNonImportati
   */
  public int getNumeroLottiNonImportati() {
    return this.numeroLottiNonImportati;
  }

  /**
   * Incremento del numero di lotti non importati
   */
  public void incrementaNumeroLottiNonImportati() {
    this.numeroLottiNonImportati++;
  }

public void setCodgara(Long codgara) {
	this.codgara = codgara;
}

public Long getCodgara() {
	return codgara;
}
  
}
