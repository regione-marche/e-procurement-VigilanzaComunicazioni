package it.mef.serviziCUP;

public class ElaborazioniCUPProxy implements it.mef.serviziCUP.ElaborazioniCUP_PortType {
  private String _endpoint = null;
  private it.mef.serviziCUP.ElaborazioniCUP_PortType elaborazioniCUP_PortType = null;
  
  public ElaborazioniCUPProxy() {
    _initElaborazioniCUPProxy();
  }
  
  public ElaborazioniCUPProxy(String endpoint) {
    _endpoint = endpoint;
    _initElaborazioniCUPProxy();
  }
  
  private void _initElaborazioniCUPProxy() {
    try {
      elaborazioniCUP_PortType = (new it.mef.serviziCUP.ElaborazioniCUP_ServiceLocator()).getElaborazioniCUPPort();
      if (elaborazioniCUP_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)elaborazioniCUP_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)elaborazioniCUP_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (elaborazioniCUP_PortType != null)
      ((javax.xml.rpc.Stub)elaborazioniCUP_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.mef.serviziCUP.ElaborazioniCUP_PortType getElaborazioniCUP_PortType() {
    if (elaborazioniCUP_PortType == null)
      _initElaborazioniCUPProxy();
    return elaborazioniCUP_PortType;
  }
  
  public it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type richiestaRispostaSincrona_RichiestaDettaglioCUP(it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type parameters) throws java.rmi.RemoteException{
    if (elaborazioniCUP_PortType == null)
      _initElaborazioniCUPProxy();
    return elaborazioniCUP_PortType.richiestaRispostaSincrona_RichiestaDettaglioCUP(parameters);
  }
  
  public it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_ListaCUP_Type richiestaRispostaSincrona_ListaCUP(it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaListaCUP_Type parameters) throws java.rmi.RemoteException{
    if (elaborazioniCUP_PortType == null)
      _initElaborazioniCUPProxy();
    return elaborazioniCUP_PortType.richiestaRispostaSincrona_ListaCUP(parameters);
  }
  
  public it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_EsitoGenerazioneCUP_Type richiestaRispostaSincrona_RichiestaGenerazioneCUP(it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaGenerazioneCUP_Type parameters) throws java.rmi.RemoteException{
    if (elaborazioniCUP_PortType == null)
      _initElaborazioniCUPProxy();
    return elaborazioniCUP_PortType.richiestaRispostaSincrona_RichiestaGenerazioneCUP(parameters);
  }
  
  public it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_RisultatoChiusuraRevocaCUP_Type richiestaRispostaSincrona_RichiestaChiusuraRevocaCUP(it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaChiusuraRevocaCUP_Type parameters) throws java.rmi.RemoteException{
    if (elaborazioniCUP_PortType == null)
      _initElaborazioniCUPProxy();
    return elaborazioniCUP_PortType.richiestaRispostaSincrona_RichiestaChiusuraRevocaCUP(parameters);
  }
  
  
}