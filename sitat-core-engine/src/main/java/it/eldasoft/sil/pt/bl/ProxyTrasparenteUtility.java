package it.eldasoft.sil.pt.bl;

import it.mef.serviziCUP.ElaborazioniCUP_PortType;
import it.mef.serviziCUP.ElaborazioniCUP_ServiceLocator;
import it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaChiusuraRevocaCUP_Type;
import it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type;
import it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type;
import it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaListaCUP_Type;
import it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_EsitoGenerazioneCUP_Type;
import it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_ListaCUP_Type;
import it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_RisultatoChiusuraRevocaCUP_Type;
import it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type;

import java.rmi.RemoteException;


import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;

/**
 * @author Mirco.Franzoni - Eldasoft S.p.A. Treviso
 */
public class ProxyTrasparenteUtility {

  private String endPoint;
  private String username;
  private String password;
  
  public ProxyTrasparenteUtility(String url, String username, String password) {
    this.endPoint = url;
    this.username = username;
    this.password = password;
  }
  
  
  public Risposta_RichiestaRispostaSincrona_ListaCUP_Type invokeListaCUP(Richiesta_RichiestaRispostaSincrona_RichiestaListaCUP_Type message) 
  {
	  String error;
	  Risposta_RichiestaRispostaSincrona_ListaCUP_Type response = null;
	  // Si istanzia il locator del servizio
	  ElaborazioniCUP_ServiceLocator locator = new ElaborazioniCUP_ServiceLocator();
	  locator.setElaborazioniCUPPortEndpointAddress(this.endPoint);
	  ElaborazioniCUP_PortType port;
	  
	  try {
	  port = (ElaborazioniCUP_PortType)locator.getElaborazioniCUPPort();

	  if (this.username != null && this.password != null ) {
	  	  // Autenticazione http basic
		  ((Stub) port)._setProperty(Call.USERNAME_PROPERTY, this.username);
		  ((Stub) port)._setProperty(Call.PASSWORD_PROPERTY, this.password);
	  }
	  // invocazione del servizio e contestuale restituzione del messaggio di risposta
		  response = port.richiestaRispostaSincrona_ListaCUP(message);
	  } catch (ServiceException e1) {
		  error = e1.getMessage();
	  } catch (RemoteException e2) {
		  error = e2.getMessage();
	  }
	  return response;
  }

  public Risposta_RichiestaRispostaSincrona_EsitoGenerazioneCUP_Type invokeEsitoGenerazioneCUP(Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type message) 
  {
	  String error;
	  Risposta_RichiestaRispostaSincrona_EsitoGenerazioneCUP_Type response = null;
	  // Si istanzia il locator del servizio
	  ElaborazioniCUP_ServiceLocator locator = new ElaborazioniCUP_ServiceLocator();
	  locator.setElaborazioniCUPPortEndpointAddress(this.endPoint);
	  ElaborazioniCUP_PortType port;
	  
	  try {
	  port = (ElaborazioniCUP_PortType)locator.getElaborazioniCUPPort();

	  if (this.username != null && this.password != null ) {
	  	  // Autenticazione http basic
		  ((Stub) port)._setProperty(Call.USERNAME_PROPERTY, this.username);
		  ((Stub) port)._setProperty(Call.PASSWORD_PROPERTY, this.password);
	  }
	  // invocazione del servizio e contestuale restituzione del messaggio di risposta
		  response = port.richiestaRispostaSincrona_RichiestaGenerazioneCUP(message);
	  } catch (ServiceException e1) {
		  error = e1.getMessage();
	  } catch (RemoteException e2) {
		  error = e2.getMessage();
	  }
	  return response;
  }
  
  public Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type invokeRisultatoDettaglioCUP(Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type message) 
  {
	  String error;
	  Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type response = null;
	  // Si istanzia il locator del servizio
	  ElaborazioniCUP_ServiceLocator locator = new ElaborazioniCUP_ServiceLocator();
	  locator.setElaborazioniCUPPortEndpointAddress(this.endPoint);
	  ElaborazioniCUP_PortType port;
	  
	  try {
	  port = (ElaborazioniCUP_PortType)locator.getElaborazioniCUPPort();

	  if (this.username != null && this.password != null ) {
	  	  // Autenticazione http basic
		  ((Stub) port)._setProperty(Call.USERNAME_PROPERTY, this.username);
		  ((Stub) port)._setProperty(Call.PASSWORD_PROPERTY, this.password);
	  }
	  // invocazione del servizio e contestuale restituzione del messaggio di risposta
		  response = port.richiestaRispostaSincrona_RichiestaDettaglioCUP(message);
	  } catch (ServiceException e1) {
		  error = e1.getMessage();
	  } catch (RemoteException e2) {
		  error = e2.getMessage();
	  }
	  return response;
  }
  
  public Risposta_RichiestaRispostaSincrona_RisultatoChiusuraRevocaCUP_Type invokeRisultatoChiusuraRevocaCUP(Richiesta_RichiestaRispostaSincrona_RichiestaChiusuraRevocaCUP_Type message) 
  {
	  String error;
	  Risposta_RichiestaRispostaSincrona_RisultatoChiusuraRevocaCUP_Type response = null;
	  // Si istanzia il locator del servizio
	  ElaborazioniCUP_ServiceLocator locator = new ElaborazioniCUP_ServiceLocator();
	  locator.setElaborazioniCUPPortEndpointAddress(this.endPoint);
	  ElaborazioniCUP_PortType port;
	  
	  try {
	  port = (ElaborazioniCUP_PortType)locator.getElaborazioniCUPPort();

	  if (this.username != null && this.password != null ) {
	  	  // Autenticazione http basic
		  ((Stub) port)._setProperty(Call.USERNAME_PROPERTY, this.username);
		  ((Stub) port)._setProperty(Call.PASSWORD_PROPERTY, this.password);
	  }
	  // invocazione del servizio e contestuale restituzione del messaggio di risposta
		  response = port.richiestaRispostaSincrona_RichiestaChiusuraRevocaCUP(message);
	  } catch (ServiceException e1) {
		  error = e1.getMessage();
	  } catch (RemoteException e2) {
		  error = e2.getMessage();
	  }
	  return response;
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

}
 