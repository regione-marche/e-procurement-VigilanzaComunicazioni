/**
 * CUPWSBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eldasoft.cup.ws;

import it.eldasoft.sil.pt.bl.EldasoftCUPWSManager;
import it.eldasoft.utils.spring.SpringAppContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CUPWSBindingImpl implements it.eldasoft.cup.ws.CUPWS{
    public it.eldasoft.cup.ws.EsitoInserisciCUP inserisciCUP(java.lang.String login, java.lang.String password, java.lang.String codein, it.eldasoft.cup.ws.DatiCUP datiCUP) throws java.rmi.RemoteException {
      ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(SpringAppContext.getServletContext());
      EldasoftCUPWSManager eldasoftCUPWSManager = (EldasoftCUPWSManager) ctx.getBean("eldasoftCUPWSManager");
      return eldasoftCUPWSManager.inserisciCUP(login, password, codein, datiCUP);
    }

    public it.eldasoft.cup.ws.EsitoConsultaCUP consultaCUP(java.lang.String uuid) throws java.rmi.RemoteException {
      ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(SpringAppContext.getServletContext());
      EldasoftCUPWSManager eldasoftCUPWSManager = (EldasoftCUPWSManager) ctx.getBean("eldasoftCUPWSManager");
      return eldasoftCUPWSManager.consultaCUP(uuid);
    }

    public it.eldasoft.cup.ws.StazioneAppaltanteType[] listaStazioniAppaltanti(java.lang.String in) throws java.rmi.RemoteException {
      ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(SpringAppContext.getServletContext());
      EldasoftCUPWSManager eldasoftCUPWSManager = (EldasoftCUPWSManager) ctx.getBean("eldasoftCUPWSManager");
      return eldasoftCUPWSManager.listaStazioniAppaltanti();
    }

}
