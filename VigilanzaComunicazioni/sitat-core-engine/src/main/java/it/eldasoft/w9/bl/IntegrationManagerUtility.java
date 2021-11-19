package it.eldasoft.w9.bl;

import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

//import javax.xml.soap.SOAPPart;
//import javax.xml.soap.SOAPBody;

import org.apache.axis.SOAPPart;
import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPBody;
import org.openspcoop.pdd.services.IntegrationManager;
import org.openspcoop.pdd.services.IntegrationManagerServiceLocator;
import org.openspcoop.pdd.services.SPCoopException;
import org.openspcoop.pdd.services.SPCoopHeaderInfo;
import org.openspcoop.pdd.services.SPCoopMessage;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class IntegrationManagerUtility {

  private String endPoint;
  private String username;
  private String password;
  /** Porta delegata. */
  private String portaDelegata;
  /** Codice del tipo destinatario. */
  private String tipoDestinatario;
  /** Identificativo destinatario. */
  private String destinatario;
  /** Codice del tipo servizio. */
  private String tipoServizio;
  /** Identificativo del servizio. */
  private String servizio;
  
  public IntegrationManagerUtility(String url, String username, String password) {
    this.endPoint = url;
    this.username = username;
    this.password = password;
  }

  /**
   * @param endPoint
   *        endPoint da settare internamente alla classe.
   */
  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  /**
   * @param user
   *        user da settare internamente alla classe.
   */
  public void setUsername(String user) {
    this.username = user;
  }

  /**
   * @param password
   *        password da settare internamente alla classe.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @param newPortaDelegata
   *          portaDelegata da settare internamente alla classe.
   */
  public final void setPortaDelegata(final String newPortaDelegata) {
    this.portaDelegata = newPortaDelegata;
  }

  /**
   * @param newTipoDestinatario
   *          tipoDestinatario da settare internamente alla classe.
   */
  public final void setTipoDestinatario(final String newTipoDestinatario) {
    this.tipoDestinatario = newTipoDestinatario;
  }

  /**
   * @param newDestinatario
   *          destinatario da settare internamente alla classe.
   */
  public final void setDestinatario(final String newDestinatario) {
    this.destinatario = newDestinatario;
  }

  /**
   * @param newTipoServizio
   *          tipoServizio da settare internamente alla classe.
   */
  public final void setTipoServizio(final String newTipoServizio) {
    this.tipoServizio = newTipoServizio;
  }

  /**
   * @param newServizio
   *          servizio da settare internamente alla classe.
   */
  public final void setServizio(final String newServizio) {
    this.servizio = newServizio;
  }

  /**
   * @return endPoint
   */
  public final String getEndPoint() {
    return endPoint;
  }

  /**
   * @return username
   */
  public final String getUsername() {
    return username;
  }

  /**
   * @return password
   */
  public final String getPassword() {
    return password;
  }

  /**
   * @return portaDelegata
   */
  public final String getPortaDelegata() {
    return portaDelegata;
  }

  /**
   * @return tipoDestinatario
   */
  public final String getTipoDestinatario() {
    return tipoDestinatario;
  }

  /**
   * @return destinatario
   */
  public final String getDestinatario() {
    return destinatario;
  }

  /**
   * @return tipoServizio
   */
  public final String getTipoServizio() {
    return tipoServizio;
  }

  /**
   * @return servizio
   */
  public final String getServizio() {
    return servizio;
  }

  public Map<String, SPCoopMessage> getCartMessages(String tipoServizio,
      String servizio, String azione) throws SPCoopException, RemoteException,
      ServiceException {
    // messagesMap e' l'hashmap destinata a contenere i messaggi recuperati
    // dalla
    // message box
    Map<String, SPCoopMessage> messagesMap = new HashMap<String, SPCoopMessage>();
    String[] ids = null;
    // Connessione all'Integration Manager della PdD
    IntegrationManager port = this.connect();
    // Prelievo della lista dei messaggi dalla PdD
    if (servizio != null && tipoServizio != null) {
      // Restituisce i messaggi disponibili per il SIL
      // corrispondenti al servizio ed all'azione richiesti
      ids = port.getAllMessagesIdByService(tipoServizio, servizio, azione);
    } else {// Restituisce tutti i messaggi disponibili per il SIL
      ids = port.getAllMessagesId();
    }
    // se l'array degli id dei messaggi e' non vuoto
    if (ids != null) {
      // si cicla sugli id recuperati
      for (int i = 0; i < ids.length; i++) {
        // si estrae il messaggio avente l'id i-esimo
        SPCoopMessage msg = port.getMessage(ids[i]);
        // si inserisce il messaggio nella Map risultato
        messagesMap.put(ids[i], msg);
        // nel caso si verifichi un'eccezione in fase di prelievo del
        // messaggio
        // questi viene scartato e si mostra la descrizione dell'errore
      }
    }
    return messagesMap;
  }

  public void deleteCartMessageById(String id) throws SPCoopException,
      RemoteException, ServiceException {
    // Connessione all'Integration Manager della PdD
    IntegrationManager port = this.connect();
    // Elimina il messaggio dalla PdD
    port.deleteMessage(id);
  }

  /**
   * Invia un messaggio al CART.
   * 
   * @param message
   *          messaggio XML da inviare all'interno di una busta SOAP.
   * @param azione
   *          azione da utilizzare sulla porta delegata, se prevista.
   * @return risposta della porta delegata
   * @throws ServiceException
   *           eccezione ritornata se non si riesce ad ottenere il proxy del web
   *           service integration manager
   * @throws SOAPException
   *           eccezione ritornata in caso di problemi durante la costruzione
   *           del messaggio SOAP
   * @throws ParserConfigurationException
   *           eccezione ritornata in caso di problemi durante la lettura del
   *           messaggio da wrappare nella busta SOAP
   * @throws SAXException
   *           eccezione ritornata in caso di problemi durante l'interpretazione
   *           del messaggio da wrappare nella busta SOAP
   * @throws IOException
   *           eccezione ritornata in caso di problemi durante la lettura del
   *           messaggio da wrappare nella busta SOAP
   */
  public final SPCoopMessage sendCartMessage(final String message,
      final String azione) throws ServiceException, SOAPException,
      ParserConfigurationException, SAXException, IOException {
    boolean imbustamento = false;
    // Connessione all'Integration Manager della PdD
    IntegrationManager port = this.connect();

    // Creazione del messaggio SOAP
    //MessageFactory factory = MessageFactory.newInstance();
    MessageFactory factory = new org.apache.axis.soap.MessageFactoryImpl();
    SOAPMessage soapMessage = factory.createMessage();
    SOAPPart soapPart = (org.apache.axis.SOAPPart) soapMessage.getSOAPPart();
    SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
    SOAPBody body = (org.apache.axis.message.SOAPBody) soapEnvelope.getBody();
    

    // Traduzione della richiesta XML in documento e aggiunta al body
    DocumentBuilder parser =
        DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document xmlDoc = parser.parse(new InputSource(new StringReader(message)));
    body.addDocument(xmlDoc);

    // Costruzione SPCoopHeaderInfo del Messaggio con i dati di configurazione
    SPCoopHeaderInfo spcoopHeaderInfo = new SPCoopHeaderInfo();
    spcoopHeaderInfo.setTipoDestinatario(tipoDestinatario);
    spcoopHeaderInfo.setDestinatario(destinatario);
    spcoopHeaderInfo.setTipoServizio(tipoServizio);
    spcoopHeaderInfo.setServizio(servizio);
    spcoopHeaderInfo.setAzione(azione);

    // Costruzione del messaggio
    SPCoopMessage msg = new SPCoopMessage();
    msg.setMessage(soapEnvelope.toString().getBytes());
    msg.setImbustamento(imbustamento);
    msg.setSpcoopHeaderInfo(spcoopHeaderInfo);

    // Invocazione della porta delegata per l'invio del messaggio
    //return port.invocaPortaDelegata(portaDelegata, msg);
    return port.invocaPortaDelegata(portaDelegata, msg);
  }
  
  /**
   * Metodo per ottenere una connessione all'Integration Manager: si specifica
   * l'endpoint del servizio e, nel caso di autenticazione necessaria, si
   * forniscono le credenziali.
   * 
   * @return stub per la connessione all'integration manager
   * 
   * @throws ServiceException ServiceException
   */
  private IntegrationManager connect() throws ServiceException {
    IntegrationManagerServiceLocator locator = new IntegrationManagerServiceLocator();
    locator.setIntegrationManagerEndpointAddress(this.endPoint);
    IntegrationManager port = locator.getIntegrationManager();
    if (this.username != null && this.password != null) {
      // Nel caso di Autenticazione HTTP Basic
      ((Stub) port)._setProperty(Call.USERNAME_PROPERTY, this.username);
      ((Stub) port)._setProperty(Call.PASSWORD_PROPERTY, this.password);
    }
    // restituisce l'handle al servizio
    return port;
  }
 
}
 