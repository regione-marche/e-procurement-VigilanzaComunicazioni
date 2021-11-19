package it.avlp.smartCig.ws;

public class ServicesProxy implements Services {
  private String _endpoint = null;
  private Services services = null;
  
  public ServicesProxy() {
    _initServicesProxy();
  }
  
  public ServicesProxy(String endpoint) {
    _endpoint = endpoint;
    _initServicesProxy();
  }
  
  private void _initServicesProxy() {
    try {
      services = (new ServicesServiceLocator()).getServicesSoap11();
      if (services != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)services)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)services)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (services != null)
      ((javax.xml.rpc.Stub)services)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public Services getServices() {
    if (services == null)
      _initServicesProxy();
    return services;
  }
  
  public ConsultaComunicazioneResponse consultaComunicazione(ConsultaComunicazioneRequest consultaComunicazioneRequest) throws java.rmi.RemoteException{
    if (services == null)
      _initServicesProxy();
    return services.consultaComunicazione(consultaComunicazioneRequest);
  }
  
  public AnnullaComunicazioneResponse annullaComunicazione(AnnullaComunicazioneRequest annullaComunicazioneRequest) throws java.rmi.RemoteException{
    if (services == null)
      _initServicesProxy();
    return services.annullaComunicazione(annullaComunicazioneRequest);
  }
  
  public ComunicaSingolaResponse comunicaSingola(ComunicaSingolaRequest comunicaSingolaRequest) throws java.rmi.RemoteException{
    if (services == null)
      _initServicesProxy();
    return services.comunicaSingola(comunicaSingolaRequest);
  }
  
  
}