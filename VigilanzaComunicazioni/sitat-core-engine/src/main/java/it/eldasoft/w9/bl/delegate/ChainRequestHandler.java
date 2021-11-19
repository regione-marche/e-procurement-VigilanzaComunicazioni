package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;

import java.sql.SQLException;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' un handler fittizio di inizio, il quale delega la gestione del messaggio
 * ai successori; &egrave; stato definito esclusivamente per non fornire al
 * chiamante un handler concreto significativo bens&igrave; un handler generico.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class ChainRequestHandler extends AbstractRequestHandler {

  /**
   * Override del metodo del padre, in modo da inoltrare la gestione all'handler
   * successivo nella catena, essendo questo il primo handler e fittizio.
   * 
   * @see it.eldasoft.w9.bl.delegate.AbstractRequestHandler#processEvento(java.lang.String,
   *      it.eldasoft.gene.db.datautils.DataColumnContainer, boolean)
   */
  public void processEvento(String xmlEvento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
    this.forwardSendEvento(xmlEvento, datiAggiornamento, ignoreWarnings);
  }

  @Override
  protected String getNomeFlusso() {
    return null;
  }

  @Override
  protected String getMainTagRequest() {
    return null;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return null;
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    return false;
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    return null;
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
  }

  public FaseType getFaseEvento(String xmlEvento) {
    return this.forwardGetFaseEvento(xmlEvento);
  }
  
}
