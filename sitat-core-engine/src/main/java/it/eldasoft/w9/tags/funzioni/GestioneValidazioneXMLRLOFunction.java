package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.contratti.rlo.xmlbeans.TrasferimentoDatiType;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.XMLRLOManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlOptions;

public class GestioneValidazioneXMLRLOFunction extends AbstractFunzioneTag {

  public GestioneValidazioneXMLRLOFunction() {
    super(2, new Class[] { PageContext.class, String.class});

  }

  public String function(PageContext pageContext, Object[] params) throws JspException {
	  XMLRLOManager manager = (XMLRLOManager) UtilitySpring.getBean(
        "XMLRLOManager", pageContext, XMLRLOManager.class);
    String chiave = (String)params[1];
    
    ProfiloUtente profilo = (ProfiloUtente) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    Long syscon = new Long(profilo.getId());
    DataColumnContainer key = new DataColumnContainer(chiave);
    Long codgara = (Long) (key.getColumnsBySuffix("CODGARA", true))[0].getValue().getValue();
    Long codlott = (Long) (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().getValue();
    Long progressivo = (Long) (key.getColumnsBySuffix("NUM", true))[0].getValue().getValue();
    Long fase = (Long) (key.getColumnsBySuffix("FASE_ESECUZIONE", true))[0].getValue().getValue();
    TrasferimentoDatiType datiTrasferimento = null;
    HashMap<String, Object> infoValidazione = null;
    try {
    	String username = (String)pageContext.getRequest().getAttribute("username");
    	datiTrasferimento = manager.getTrasferimentoDati(codgara, codlott, fase, progressivo, username, syscon);
    	infoValidazione = manager.validate(datiTrasferimento, fase);
        
    	if ((Integer)infoValidazione.get("numeroErrori") == 0) {
    		ArrayList<Object> validationErrors = new ArrayList<Object>();
      	  	XmlOptions validationOptions = new XmlOptions();
      	  	validationOptions.setErrorListener(validationErrors);
      	      	
      		if (!datiTrasferimento.validate(validationOptions)) {
      			String listaErroriValidazione = "";
      	      	Iterator< ? > iter = validationErrors.iterator();
      	      	while (iter.hasNext()) {
      	      		listaErroriValidazione += iter.next() + "\n";
      	      	}
      	      	infoValidazione.put("titolo", "Controlli validazione XML");
      	      	List<Object> listaControlli = (List<Object>)infoValidazione.get("listaControlli");
      	      	int numeroErrori = (Integer)infoValidazione.get("numeroErrori");
      	      	numeroErrori ++;
      	      	infoValidazione.put("numeroErrori", new Integer(numeroErrori));
    			listaControlli.add(((new Object[] { "E", "Validazione", "Validazione XML", listaErroriValidazione })));
      		}
    	}
    } catch (GestoreException e) {
    	;
    }
	
    pageContext.setAttribute("titolo", infoValidazione.get("titolo"));
    pageContext.setAttribute("listaControlli", infoValidazione.get("listaControlli"));
    pageContext.setAttribute("numeroWarning", infoValidazione.get("numeroWarning"));
    pageContext.setAttribute("numeroErrori", infoValidazione.get("numeroErrori"));

    if (infoValidazione.get("numeroErrori").toString().equals("0")) {
    	datiTrasferimento.documentProperties().setEncoding("utf-8");
        HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
    						"attachment;filename=Scheda_" + codgara + "_" + StringUtils.leftPad(fase.toString(),3,'0') + ".xml");
        try {
        	
        	ServletOutputStream out = response.getOutputStream();
        	String documentString = datiTrasferimento.toString();
        	documentString = documentString.replaceAll("xb:", "");
        	documentString = documentString.replaceAll("<xml-fragment xmlns:xb=\"xmlbeans.rlo.contratti.eldasoft.it\">", "<TrasferimentoDati>");
        	documentString = documentString.replaceAll("</xml-fragment>", "</TrasferimentoDati>");
        	byte[] theByteArray = documentString.getBytes();
        	out.write(theByteArray, 0, theByteArray.length);
        	out.flush();
        	out.close();
        } catch (Exception e) {
        	;
        }
    }

    return null;
  }

}
