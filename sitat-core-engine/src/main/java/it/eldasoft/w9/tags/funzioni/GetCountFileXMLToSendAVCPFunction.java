/*
 * Created on 7-nov-2012
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.tags.funzioni;

import java.io.File;
import java.io.FilenameFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.common.CostantiSitatORT;

/**
 * Classe che realizza una funzione per ottenere il numero di file XML da inviare all'Autorita' di vigilanza
 * 
 * @author Mirco.Franzoni
 */
public class GetCountFileXMLToSendAVCPFunction extends AbstractFunzioneTag {

  public GetCountFileXMLToSendAVCPFunction() {
    super(1, new Class[] { Object.class });
  }

  /**
   * @see it.eldasoft.gene.tags.utils.AbstractFunzioneTag#function(javax.servlet.jsp.PageContext,
   *      java.lang.Object[])
   */
  public String function(PageContext pageContext, Object[] params) throws JspException {
    
    String count = "0";
    try {
      
      String urlSimogFTP = ConfigManager.getValore(CostantiSitatORT.SFTP_HOST);
      if (StringUtils.isNotEmpty(urlSimogFTP)) {
        pageContext.setAttribute("urlSimogFTP", urlSimogFTP, PageContext.REQUEST_SCOPE);
      }
      
      String PROP_SIMOG_XML_DATAPATH = CostantiSitatORT.SIMOG_XML_DATAPATH;
      String datapath = ConfigManager.getValore(PROP_SIMOG_XML_DATAPATH);
      File dir = new File(datapath);

      //creo un filtro per i file XML
      FilenameFilter filefilterXml = new FilenameFilter() {
         public boolean accept(File dir, String name) {
           return name.endsWith(".xml");
         }
      };
      File[] listFileXml = dir.listFiles(filefilterXml);
      count = Integer.toString(Integer.parseInt(count) + listFileXml.length);
    } catch (Exception e) {
    	count = "0";
    }
    return count;
  }
  
}
