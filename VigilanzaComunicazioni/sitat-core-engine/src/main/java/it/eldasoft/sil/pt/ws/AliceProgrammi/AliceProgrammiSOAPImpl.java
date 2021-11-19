/**
 * AliceProgrammiSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.sil.pt.ws.AliceProgrammi;

import it.eldasoft.utils.spring.SpringAppContext;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.sil.pt.bl.WebServiceProgrammiTriennaliManager;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AliceProgrammiSOAPImpl implements it.eldasoft.sil.pt.ws.AliceProgrammi.AliceProgrammi_PortType{
    public java.lang.String getListaProgrammi(java.lang.String id, int anno, int tipo) throws java.rmi.RemoteException {
    	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(SpringAppContext.getServletContext());
   	 	WebServiceProgrammiTriennaliManager webServiceProgrammiTriennaliManager = (WebServiceProgrammiTriennaliManager) ctx.getBean("webServiceProgrammiTriennaliManager");
   	 	return webServiceProgrammiTriennaliManager.rispostaListaProgrammi(id, anno, tipo);
    }

    public java.lang.String getListaInterventi(int contri) throws java.rmi.RemoteException {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(SpringAppContext.getServletContext());
   	 	WebServiceProgrammiTriennaliManager webServiceProgrammiTriennaliManager = (WebServiceProgrammiTriennaliManager) ctx.getBean("webServiceProgrammiTriennaliManager");
   	 	return webServiceProgrammiTriennaliManager.rispostaListaInterventi(contri);
    }

    public java.lang.String getSchedaIntervento(int contri, int conint) throws java.rmi.RemoteException {
    	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(SpringAppContext.getServletContext());
   	 	WebServiceProgrammiTriennaliManager webServiceProgrammiTriennaliManager = (WebServiceProgrammiTriennaliManager) ctx.getBean("webServiceProgrammiTriennaliManager");
   	 	return webServiceProgrammiTriennaliManager.rispostaSchedaIntervento(contri, conint);
    }

    public void setSchedaIntervento(java.lang.String in, int contri, javax.xml.rpc.holders.StringHolder out, javax.xml.rpc.holders.BooleanHolder result) throws java.rmi.RemoteException {
    	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(SpringAppContext.getServletContext());
   	 	WebServiceProgrammiTriennaliManager webServiceProgrammiTriennaliManager = (WebServiceProgrammiTriennaliManager) ctx.getBean("webServiceProgrammiTriennaliManager");
        out.value = "";
        result.value = webServiceProgrammiTriennaliManager.inserisciSchedaIntervento(in, contri, out);
        if (result.value)
        {   try {
            	PtManager m = (PtManager)   ctx.getBean("ptManager");
            	m.aggiornaCostiPiatri(new Long(contri));
          	} catch (Exception e) {
          		 out.value += e.getMessage();
          	}
        }
    }
    
    public it.eldasoft.sil.pt.ws.AliceProgrammi.EsitoInserisciProgramma inserisciProgramma(java.lang.String login, java.lang.String password, java.lang.String id, java.lang.String xmlProgramma) throws java.rmi.RemoteException {
    	
    	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(SpringAppContext.getServletContext());
   	 	WebServiceProgrammiTriennaliManager webServiceProgrammiTriennaliManager = (WebServiceProgrammiTriennaliManager) ctx.getBean("webServiceProgrammiTriennaliManager");
   	 	EsitoInserisciProgramma esito;
   	 	esito = webServiceProgrammiTriennaliManager.inserisciProgramma(login, password, id, xmlProgramma);
        return esito;
    }

}
