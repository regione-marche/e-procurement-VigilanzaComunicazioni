package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.StatoComunicazione;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' un handler fittizio di termine, il quale consente la terminazione della
 * catena di gestioni e ritorna un errore incondizionato in quanto se si arriva
 * a questo gestore vuol dire che &egrave; un messaggio che non rientra nelle
 * casistiche precedenti
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class UnknownMessageHandler extends AbstractRequestHandler {

  Logger logger = Logger.getLogger(UnknownMessageHandler.class);

  /**
   * Override del metodo del padre, in modo da skippare l'algoritmo standard ed
   * andare automaticamente in errore.
   * 
   * @see it.eldasoft.w9.bl.delegate.AbstractRequestHandler#processEvento(java.lang.String,
   *      it.eldasoft.gene.db.datautils.DataColumnContainer, boolean)
   */
  @Override
  public void processEvento(String xmlEvento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    // se si arriva all'esecuzione di questo codice, vuol dire che per qualche
    // motivo inspiegabile il messaggio recapitato non e' nel formato previsto
    // dalla RFC 125, per cui e' una richiesta che va scartata
    logger.error("Richiesta XML non riconosciuta: " + xmlEvento);
    datiAggiornamento.getColumn("W9INBOX.STACOM").setObjectValue(
        StatoComunicazione.STATO_ERRATA.getStato());
    datiAggiornamento.getColumn("W9INBOX.MSG").setObjectValue(
    "Richiesta non riconosciuta");
  }

  @Override
  protected String getNomeFlusso() {
    // implementazione vuota in quanto e' un handler fittizio
    return null;
  }

  @Override
  protected String getMainTagRequest() {
    // implementazione vuota in quanto e' un handler fittizio
    return null;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    // implementazione vuota in quanto e' un handler fittizio
    return null;
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    // implementazione vuota in quanto e' un handler fittizio
    return false;
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    // implementazione vuota in quanto e' un handler fittizio
    return null;
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    // implementazione vuota in quanto e' un handler fittizio
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    // implementazione vuota in quanto e' un handler fittizio
  }

  @Override
  public FaseType getFaseEvento(String xmlEvento) {
    // se si arriva all'esecuzione di questo codice, vuol dire che per qualche
    // motivo inspiegabile il messaggio recapitato non e' nel formato previsto
    // dalla RFC 125, per cui e' una richiesta che va scartata
    logger.error("Richiesta XML non riconosciuta: " + xmlEvento);
    return null;
  }
}
