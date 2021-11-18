package it.eldasoft.w9.bl.simog;

import java.io.Serializable;

import it.avlp.simog.massload.xmlbeans.Collaborazioni;

/**
 * Bean per contenere l'esito dell'operazione di login sui servizi SIMOG. 
 * 
 * @author Luca.Giacomazzo
 */
public class TicketSimogBean implements Serializable {

  /** UID */
	private static final long serialVersionUID = 3453123127155389111L;
	
	private boolean success;
  private String ticketSimog;
  private Collaborazioni collaborazioni;
  private String msgError;
  
  public TicketSimogBean() {
    this.success = false;
    this.ticketSimog = null;
    this.collaborazioni = null;
    this.msgError = null;
  }

  /**
   * @return the success
   */
  public boolean isSuccess() {
    return success;
  }

  /**
   * @param success the success to set
   */
  public void setSuccess(boolean success) {
    this.success = success;
  }

  /**
   * @return the ticketSimog
   */
  public String getTicketSimog() {
    return ticketSimog;
  }

  /**
   * @param ticketSimog the ticketSimog to set
   */
  public void setTicketSimog(String ticketSimog) {
    this.ticketSimog = ticketSimog;
  }

  /**
   * @return the msgError
   */
  public String getMsgError() {
    return msgError;
  }

  /**
   * @param msgError the msgError to set
   */
  public void setMsgError(String msgError) {
    this.msgError = msgError;
  }

  /**
   * @return the collaborazioni
   */
  public it.avlp.simog.massload.xmlbeans.Collaborazioni getCollaborazioni() {
    return collaborazioni;
  }

  /**
   * @param collaborazioni the collaborazioniPDD to set
   */
  public void setCollaborazioni(
      it.avlp.simog.massload.xmlbeans.Collaborazioni collaborazioni) {
    this.collaborazioni = collaborazioni;
  }
  
}
