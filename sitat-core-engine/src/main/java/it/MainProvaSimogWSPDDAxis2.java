package it;

import java.rmi.RemoteException;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlException;

import it.avlp.simog.massload.xmlbeans.ChiudiSessione;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument;
import it.avlp.simog.massload.xmlbeans.Collaborazioni;
import it.avlp.simog.massload.xmlbeans.ConsultaGara;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaNumeroGara;
import it.avlp.simog.massload.xmlbeans.ConsultaNumeroGaraDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaNumeroGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.Login;
import it.avlp.simog.massload.xmlbeans.LoginDocument;
import it.avlp.simog.massload.xmlbeans.LoginResponseDocument;
import it.avlp.simog.massload.xmlbeans.SchedaGaraCig;
import it.avlp.simog.massload.xmlbeans.SchedaGaraCigDocument;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.eldasoft.utils.sicurezza.CriptazioneException;

public class MainProvaSimogWSPDDAxis2 {

  /**
   * @param args
   * @throws CriptazioneException 
   */
  public static void main(String[] args) throws CriptazioneException {
    // TODO Auto-generated method stub

    String urlWs = "https://wstest.anticorruzione.it/COLL/SimogWSPDD/services/SimogWSPDD";
    urlWs = "https://ws.anticorruzione.it/SimogWSPDD/services/SimogWSPDD";
    
    String urlPdd = "https://pda.tix.it/pda/PD/SPCRegioneToscana/SPCavcp/SPCSimogWSOSS";
    String usernameBA = "SPCRegioneToscana_SPCavcpCollaudo_SPCSimogWSOSS_SIL_F";
    usernameBA = "SPCRegioneToscana_SPCavcp_SPCSimogWSOSS_SIL_F";
    String passwordBA = "grj23gKT@"; // "!Zbv,tr#ir"; 
    passwordBA = "raPR3qap";  // "!JFetF@,i"
    
    boolean usaWs = true;
    
    String loginWsPdd = "SRTMRZ64A31A944P";
    String passwordWsPDD = ".MAU.159";
    
    loginWsPdd = "brtndr58c07d612f";
    passwordWsPDD = "orcp@toscana1";
    
    String schede = "3.03.3.0";
    
    String ticket = null;
    SimogWSPDDServiceStub simogWsPDDServiceStub = null;
    HttpTransportProperties.Authenticator basicAuth = null;
    
    try {
      
      if (usaWs) {
        simogWsPDDServiceStub = new  SimogWSPDDServiceStub(urlWs);
      } else {
        simogWsPDDServiceStub = new  SimogWSPDDServiceStub(urlPdd + "/login");
        
     // Creazione della options contenente i parametri necessari alla basic authentication
        try {
          basicAuth = getBasicAuthentication(usernameBA, passwordBA);
        } catch (CriptazioneException ce) {
          System.out.println("Errore nella decriptazione della password per la Basic Authentication "
              + "alla PDD. Contattare un amministratore.") ; //, ce);
          throw ce;
        }
      }
      
      if (! usaWs) {
        simogWsPDDServiceStub._getServiceClient().getOptions().setProperty(
            HTTPConstants.AUTHENTICATE, basicAuth);
      }
      
      LoginDocument loginDoc = LoginDocument.Factory.newInstance();
      Login loginIn = Login.Factory.newInstance();
      loginIn.setLogin(loginWsPdd);
      loginIn.setPassword(passwordWsPDD);
      loginDoc.setLogin(loginIn);

      LoginResponseDocument loginResponseDoc = simogWsPDDServiceStub.login(loginDoc);
      
      if (loginResponseDoc.getLoginResponse() != null && loginResponseDoc.getLoginResponse().isSetReturn() &&
           loginResponseDoc.getLoginResponse().getReturn() != null && loginResponseDoc.getLoginResponse().getReturn().getSuccess()) {
        
        ticket = loginResponseDoc.getLoginResponse().getReturn().getTicket();
        Collaborazioni arrayCollaborazioni = loginResponseDoc.getLoginResponse().getReturn().getColl();
        
        try {
          ConsultaNumeroGaraDocument consultaNumeroGaraDocument = ConsultaNumeroGaraDocument.Factory.newInstance();
          ConsultaNumeroGara consultaNumeroGara = ConsultaNumeroGara.Factory.newInstance();
          consultaNumeroGara.setIdGara("6909366");
          consultaNumeroGara.setSchede(schede);
          consultaNumeroGara.setTicket(ticket);
          consultaNumeroGaraDocument.setConsultaNumeroGara(consultaNumeroGara);
          
          if (! usaWs) {
            simogWsPDDServiceStub = new  SimogWSPDDServiceStub(urlPdd + "/consultaNumeroGara");
            simogWsPDDServiceStub._getServiceClient().getOptions().setProperty(
                HTTPConstants.AUTHENTICATE, basicAuth);
          }
          
          ConsultaNumeroGaraResponseDocument consultaNumeroGaraResponsedocument = simogWsPDDServiceStub.consultaNumeroGara(consultaNumeroGaraDocument);
          if (consultaNumeroGaraResponsedocument.getConsultaNumeroGaraResponse() != null && 
              consultaNumeroGaraResponsedocument.getConsultaNumeroGaraResponse().getReturn() != null &&
              consultaNumeroGaraResponsedocument.getConsultaNumeroGaraResponse().getReturn().getSuccess()) {
            
            SchedaGaraCig schedaGaraCig = consultaNumeroGaraResponsedocument.getConsultaNumeroGaraResponse().getReturn().getSchedaGaraCig();
            
            if (schedaGaraCig != null && schedaGaraCig.getGara() != null);
            String temp01 = schedaGaraCig.toString();  
            System.out.println(temp01);
            String obj0 = consultaNumeroGaraResponsedocument.xmlText();
            String obj1 = consultaNumeroGaraResponsedocument.getConsultaNumeroGaraResponse().xmlText();
            String obj2 = consultaNumeroGaraResponsedocument.getConsultaNumeroGaraResponse().getReturn().xmlText();
            String obj3 = consultaNumeroGaraResponsedocument.getConsultaNumeroGaraResponse().getReturn().getSchedaGaraCig().xmlText();

            try {
              ConsultaNumeroGaraResponseDocument ricostruisci0 = ConsultaNumeroGaraResponseDocument.Factory.parse(obj0);
              ricostruisci0.getConsultaNumeroGaraResponse().getReturn().getSchedaGaraCig().getCIGArray(0);
            } catch (XmlException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
            
            try {
              ConsultaNumeroGaraResponseDocument ricostruisci1 = ConsultaNumeroGaraResponseDocument.Factory.parse(obj1);
            } catch (XmlException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }

            try {
              ConsultaNumeroGaraDocument ricostruisci2 = ConsultaNumeroGaraDocument.Factory.parse(obj2);
            } catch (XmlException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            
            try {
              SchedaGaraCigDocument ricostruisci3 = SchedaGaraCigDocument.Factory.parse(obj3);
            } catch (XmlException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }

            
            String[] arrayCig = schedaGaraCig.getCIGArray();
            
            ConsultaGaraDocument consultaGaraDocument = ConsultaGaraDocument.Factory.newInstance();
            ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
            consultaGara.setSchede(schede);
            consultaGara.setTicket(ticket);
            consultaGara.setCIG(arrayCig[0]);
            consultaGaraDocument.setConsultaGara(consultaGara);
            
            if (! usaWs) {
              simogWsPDDServiceStub = new  SimogWSPDDServiceStub(urlPdd + "/consultaGara");
              simogWsPDDServiceStub._getServiceClient().getOptions().setProperty(
                  HTTPConstants.AUTHENTICATE, basicAuth);
            }
            
            ConsultaGaraResponseDocument responseConsultaGara = simogWsPDDServiceStub.consultaGara(consultaGaraDocument);
            if (responseConsultaGara.getConsultaGaraResponse() != null && 
                responseConsultaGara.getConsultaGaraResponse().getReturn() != null &&
                responseConsultaGara.getConsultaGaraResponse().getReturn().getSuccess()) {
              
              String strScheda = responseConsultaGara.getConsultaGaraResponse().getReturn().getGaraXML().toString();
              System.out.println(strScheda);
            }
          }           
        } catch (RemoteException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        ConsultaGaraDocument consultaGaraDocument = ConsultaGaraDocument.Factory.newInstance();
        ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
        consultaGara.setSchede("3.03.3.0");
        consultaGara.setTicket(ticket);
        consultaGara.setCIG("728347842F");
        consultaGara.setCIG("610548124F");
        consultaGaraDocument.setConsultaGara(consultaGara);
        
        if (! usaWs) {
          simogWsPDDServiceStub = new  SimogWSPDDServiceStub(urlPdd + "/consultaGara");
          simogWsPDDServiceStub._getServiceClient().getOptions().setProperty(
              HTTPConstants.AUTHENTICATE, basicAuth);
        }
        
        ConsultaGaraResponseDocument responseConsultaGara1 = simogWsPDDServiceStub.consultaGara(consultaGaraDocument);
        if (responseConsultaGara1.getConsultaGaraResponse() != null && 
            responseConsultaGara1.getConsultaGaraResponse().getReturn() != null &&
            responseConsultaGara1.getConsultaGaraResponse().getReturn().getSuccess()) {
          
          String strScheda = responseConsultaGara1.getConsultaGaraResponse().getReturn().getGaraXML().toString();
          System.out.println(strScheda);
        }
        
      } else {
        System.out.println("Login terminata senza successo. " + loginResponseDoc.getLoginResponse().getReturn().getError());
      }
      
    } catch (RemoteException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      if (ticket != null && simogWsPDDServiceStub != null) {
        try {
          ChiudiSessioneDocument chiudiSessioneDocument = ChiudiSessioneDocument.Factory.newInstance();
          ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticket);
          chiudiSessioneDocument.setChiudiSessione(chiudiSessione);

          if (! usaWs) {
            simogWsPDDServiceStub = new  SimogWSPDDServiceStub(urlPdd + "/chiudiSessione");
            simogWsPDDServiceStub._getServiceClient().getOptions().setProperty(
                HTTPConstants.AUTHENTICATE, basicAuth);
          }
          simogWsPDDServiceStub.chiudiSessione(chiudiSessioneDocument);
          
        } catch (RemoteException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    
  }

  private static HttpTransportProperties.Authenticator getBasicAuthentication(String usernameBasicAuthPDD, String passwordBasicAuthPDD) throws CriptazioneException {
    HttpTransportProperties.Authenticator auth = null;

    if (StringUtils.isNotEmpty(usernameBasicAuthPDD) && StringUtils.isNotEmpty(passwordBasicAuthPDD)) {
      auth = new HttpTransportProperties.Authenticator();
      auth.setUsername(usernameBasicAuthPDD);
      auth.setPassword(passwordBasicAuthPDD);
      auth.setPreemptiveAuthentication(true);
    }
    
    return auth;
  }
}
