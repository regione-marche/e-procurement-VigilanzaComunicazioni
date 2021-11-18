/**
 * SitatSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.toscana.rete.rfc.sitatort;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.spring.SpringAppContext;
import it.eldasoft.w9.bl.GaraDelegataManager;
import it.eldasoft.w9.bl.GeneraIdGaraCigManager;
import it.eldasoft.w9.bl.ort.OsservatorioManager;
import it.eldasoft.w9.bl.simog.AccessoSimogManager;
import it.eldasoft.w9.common.CostantiSimog;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SitatSoapBindingImpl implements it.toscana.rete.rfc.sitatort.WsOsservatorio_PortType{

    public it.toscana.rete.rfc.sitatort.ResponseConsultaGara getGaraXML(
		java.lang.String codiceCIG, java.lang.String idGara,
		java.lang.String cfrup, java.lang.String cfsa, boolean controlliPreliminari)
		throws java.rmi.RemoteException {
        
		    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
        SpringAppContext.getServletContext());
    GeneraIdGaraCigManager generaIdGaraCigManager =
        (GeneraIdGaraCigManager) ctx.getBean("generaIdGaraCigManager");

    return generaIdGaraCigManager.consultaGaraORT(codiceCIG, idGara, cfrup, cfsa, controlliPreliminari);
    }

    public it.toscana.rete.rfc.sitatort.ResponseElencoFeedback getElencoFeedback(
    java.lang.String cig, java.lang.String idgara, java.lang.String cfrup, java.lang.String cfsa,
    it.toscana.rete.rfc.sitatort.TipoFeedbackType tipoFeedBack,
    it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione)
	    throws java.rmi.RemoteException {
    
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
        SpringAppContext.getServletContext());
    OsservatorioManager osservatorioManager =
        (OsservatorioManager) ctx.getBean("osservatorioManager");

    return osservatorioManager.getElencoFeedback(cig, idgara, cfrup, cfsa, tipoFeedBack, faseEsecuzione);

  }

  public it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType getElencoSchede(
      java.lang.String cig, java.lang.String idgara, java.lang.String cfrup,
      java.lang.String cfsa) throws java.rmi.RemoteException {
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
        SpringAppContext.getServletContext());
    OsservatorioManager osservatorioManager =
        (OsservatorioManager) ctx.getBean("osservatorioManager");

    return osservatorioManager.getElencoSchede(cig, idgara, cfrup, cfsa);
  }

  public it.toscana.rete.rfc.sitatort.ResponseSchedaType getScheda(
    java.lang.String cig, java.lang.String idgara, java.lang.String cfrup,
    java.lang.String cfsa, it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione)
        throws java.rmi.RemoteException {

    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
        SpringAppContext.getServletContext());
    OsservatorioManager osservatorioManager =
        (OsservatorioManager) ctx.getBean("osservatorioManager");

    return osservatorioManager.getScheda(cig, idgara, cfrup, cfsa, faseEsecuzione);
  }

  public it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegata(
      java.lang.String idAvGara, java.lang.String cfRup, java.lang.String indiceColl) 
          throws java.rmi.RemoteException {

    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
        SpringAppContext.getServletContext());
    GaraDelegataManager garaDelegataManager =
        (GaraDelegataManager) ctx.getBean("garaDelegataManager");

    return garaDelegataManager.presaInCaricoGaraDelegata(idAvGara, cfRup, indiceColl, null, null);
  }
  
  public ResponseLoginRPNT getLoginRPNT(java.lang.String cfRup) 
      throws java.rmi.RemoteException {
    
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
        SpringAppContext.getServletContext());
    AccessoSimogManager accessoSimogManager =
        (AccessoSimogManager) ctx.getBean("accessoSimogManager");
    
    // Gestione della connessione
    String loginServiziSimog = null;
    String passwordServiziSimog = null;
    
    String a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
    if (StringUtils.isNotEmpty(a)) {
      loginServiziSimog = new String(a);
    }
    
    a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD);
    if (StringUtils.isNotEmpty(a)) {
      try {
        ICriptazioneByte cript = FactoryCriptazioneByte.getInstance(
            ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
            a.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
        passwordServiziSimog = new String(cript.getDatoNonCifrato());
      } catch (CriptazioneException e) {

      }
    }
    
    return accessoSimogManager.getLoginRPNT(cfRup, loginServiziSimog, passwordServiziSimog);
  }
  
}
