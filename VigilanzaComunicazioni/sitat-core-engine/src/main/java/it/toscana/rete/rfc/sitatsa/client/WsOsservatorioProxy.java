package it.toscana.rete.rfc.sitatsa.client;

public class WsOsservatorioProxy implements it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_PortType {
  private String _endpoint = null;
  private it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_PortType wsOsservatorio_PortType = null;
  
  public WsOsservatorioProxy() {
    _initWsOsservatorioProxy();
  }
  
  public WsOsservatorioProxy(String endpoint) {
    _endpoint = endpoint;
    _initWsOsservatorioProxy();
  }
  
  private void _initWsOsservatorioProxy() {
    try {
      wsOsservatorio_PortType = (new it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_ServiceLocator()).getWsOsservatorio();
      if (wsOsservatorio_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wsOsservatorio_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wsOsservatorio_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wsOsservatorio_PortType != null)
      ((javax.xml.rpc.Stub)wsOsservatorio_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.toscana.rete.rfc.sitatsa.client.WsOsservatorio_PortType getWsOsservatorio_PortType() {
    if (wsOsservatorio_PortType == null)
      _initWsOsservatorioProxy();
    return wsOsservatorio_PortType;
  }
  
  public it.toscana.rete.rfc.sitatsa.client.ResponseConsultaGara getGaraXML(java.lang.String codiceCIG, java.lang.String idGara, java.lang.String cfrup, java.lang.String cfsa, boolean controlliPreliminari) throws java.rmi.RemoteException{
    if (wsOsservatorio_PortType == null)
      _initWsOsservatorioProxy();
    return wsOsservatorio_PortType.getGaraXML(codiceCIG, idGara, cfrup, cfsa, controlliPreliminari);
  }
  
  public it.toscana.rete.rfc.sitatsa.client.ResponseElencoFeedback getElencoFeedback(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatsa.client.TipoFeedbackType tipoFeedBack, it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException{
    if (wsOsservatorio_PortType == null)
      _initWsOsservatorioProxy();
    return wsOsservatorio_PortType.getElencoFeedback(cig, idgara, cfrup, cfsa, tipoFeedBack, faseEsecuzione);
  }
  
  public it.toscana.rete.rfc.sitatsa.client.ResponseElencoSchedeType getElencoSchede(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa) throws java.rmi.RemoteException{
    if (wsOsservatorio_PortType == null)
      _initWsOsservatorioProxy();
    return wsOsservatorio_PortType.getElencoSchede(cig, idgara, cfrup, cfsa);
  }
  
  public it.toscana.rete.rfc.sitatsa.client.ResponseSchedaType getScheda(java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa, it.toscana.rete.rfc.sitatsa.client.FaseEsecuzioneType faseEsecuzione) throws java.rmi.RemoteException{
    if (wsOsservatorio_PortType == null)
      _initWsOsservatorioProxy();
    return wsOsservatorio_PortType.getScheda(cig, idgara, cfrup, cfsa, faseEsecuzione);
  }
  
  public it.toscana.rete.rfc.sitatsa.client.ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegata(java.lang.String idAvGara, java.lang.String cfRup, java.lang.String indiceColl) throws java.rmi.RemoteException{
    if (wsOsservatorio_PortType == null)
      _initWsOsservatorioProxy();
    return wsOsservatorio_PortType.presaInCaricoGaraDelegata(idAvGara, cfRup, indiceColl);
  }
  
  public it.toscana.rete.rfc.sitatsa.client.ResponseLoginRPNT getLoginRPNT(java.lang.String cfRup) throws java.rmi.RemoteException{
    if (wsOsservatorio_PortType == null)
      _initWsOsservatorioProxy();
    return wsOsservatorio_PortType.getLoginRPNT(cfRup);
  }
  
  
}