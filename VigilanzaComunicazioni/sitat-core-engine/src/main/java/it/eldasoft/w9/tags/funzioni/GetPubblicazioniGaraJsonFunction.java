package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Function per estrarre le pubblicazione della gara. 
 */
public class GetPubblicazioniGaraJsonFunction extends AbstractFunzioneTag {

  /** Costruttore. */
  public GetPubblicazioniGaraJsonFunction() {
    super(3, new Class[] { PageContext.class, String.class, String.class });
  }

  private String sqlw9cfPubb = "select G.NLOTTI,L.CIG from W9GARA G join W9LOTT L on L.CODGARA=G.CODGARA where G.CODGARA=?";
  private String sqlInCompilazione1 = "select count(*) from (select 1 r from W9PUBBLICAZIONI P join W9PUBLOTTO L on L.CODGARA=P.CODGARA and L.NUM_PUBB=P.NUM_PUBB where P.CODGARA=? and P.TIPDOC=? group by L.CODGARA,L.CODLOTT) Pubb";
  private String sqlInCompilazione2 = "select count(*) from W9FLUSSI F join W9PUBBLICAZIONI P on P.CODGARA= F.KEY01 and F.KEY03=901 and F.key04=P.NUM_PUBB where P.CODGARA=? and P.TIPDOC=?";
  private String sqlPubblicato = "select count(*) from (select 1 r from W9FLUSSI F join W9PUBBLICAZIONI P on P.CODGARA= F.KEY01 and F.KEY03=901 and F.key04=P.NUM_PUBB join W9PUBLOTTO L on L.CODGARA=P.CODGARA and L.NUM_PUBB=P.NUM_PUBB where P.CODGARA=? and P.TIPDOC=? group by L.CODGARA,L.CODLOTT) Pubb";
  private String sqlInCompilazioneParziale1 = "select count(*) from (select 1 r from W9PUBBLICAZIONI P join W9PUBLOTTO L on L.CODGARA=P.CODGARA and L.NUM_PUBB=P.NUM_PUBB where P.CODGARA=? and P.TIPDOC=? group by L.CODGARA,L.CODLOTT) Pubb";
  private String sqlInCompilazioneParziale2 = "select count(*) from W9FLUSSI F join W9PUBBLICAZIONI P on P.CODGARA= F.KEY01 and F.KEY03=901 and F.key04=P.NUM_PUBB where P.CODGARA=? and P.TIPDOC=?";
  private String sqlPubblicatoParziale = "select count(*) from (select 1 r from W9FLUSSI F join W9PUBBLICAZIONI P on P.CODGARA= F.KEY01 and F.KEY03=901 and F.key04=P.NUM_PUBB join W9PUBLOTTO L on L.CODGARA=P.CODGARA and L.NUM_PUBB=P.NUM_PUBB where P.CODGARA=? and P.TIPDOC=? group by L.CODGARA,L.CODLOTT) Pubb";
  private String sqlVuoto = "select count(*) from W9PUBBLICAZIONI P where P.CODGARA=? and P.TIPDOC=?";
  private String sqlIdRicevuto = "select ID_RICEVUTO from W9PUBBLICAZIONI P where P.CODGARA=? and P.TIPDOC=?";
  private String sqlPubblicazione = "select NUM_PUBB from W9PUBBLICAZIONI where CODGARA = ? and TIPDOC=?";
  
  private String sqlPubblicazioni = "select NUM_PUBB, DESCRIZ from W9PUBBLICAZIONI where CODGARA = ? and TIPDOC = ?";
  private String sqlPubblicazioniCIG = "select LO.CIG from W9PUBBLICAZIONI P join W9PUBLOTTO L on L.CODGARA=P.CODGARA and L.NUM_PUBB=P.NUM_PUBB join W9LOTT LO on L.CODGARA=LO.CODGARA and L.CODLOTT=LO.CODLOTT where P.CODGARA = ? and P.NUM_PUBB = ?";

  
  @Override 
  public String function(PageContext pageContext, Object[] params) 
  throws JspException {
   
	  Long codGara = new Long((String) params[1]);
	  Long codLotto = null;
	  String filtroLotto = "";
	  if (params[2] != null)  {
		  codLotto = new Long((String) params[2]);
		  filtroLotto = "&codiceLotto=" + codLotto;
	  }
	  
	  SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
	        pageContext, SqlManager.class);
	  JSONArray json =  new JSONArray();
	  Long tipoPubblicazione, nrLotti, nrPubblicazioni, nrPubblicazioniTotali, nrFlussi;
	  boolean pubblicazionePresente;
	  Long idRicevuto;
	  Long numPubblicazione;
	  String nome, clausolaWhereVis, tipo, listaCIG;
	  HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
	  int indice = 0;
	  String iconaPubblicitaLegale = "<img src=\"" + request.getContextPath() + "/img/pubblicitaLegale.png\" title=\"Pubblicità ex DM MIT 02/12/2016 ai sensi art. 73 c.4 del Dlgs 50/2016\" alt=\"Pubblicità ex DM MIT 02/12/2016 ai sensi art. 73 c.4 del Dlgs 50/2016\"/> ";
	  try { 
		  List<?> w9cfPubb = sqlManager.getListVector("select ID, NOME, CL_WHERE_VIS, TIPO from W9CF_PUBB order by NUMORD", new Object[] {});
	      if (w9cfPubb != null && w9cfPubb.size() > 0) {
	        for (int i = 0; i < w9cfPubb.size(); i++) {
	        	tipoPubblicazione = SqlManager.getValueFromVectorParam(w9cfPubb.get(i), 0).longValue();
	        	nome = (String) SqlManager.getValueFromVectorParam(w9cfPubb.get(i), 1).getValue();
		        clausolaWhereVis = (String) SqlManager.getValueFromVectorParam(w9cfPubb.get(i), 2).getValue();
		        tipo = (String) SqlManager.getValueFromVectorParam(w9cfPubb.get(i), 3).getValue();
		        if (clausolaWhereVis != null && !clausolaWhereVis.equals("")) {
		        	clausolaWhereVis = " and (" + clausolaWhereVis;
		        	clausolaWhereVis += " or G.CODGARA IN (SELECT CODGARA FROM W9PUBBLICAZIONI WHERE TIPDOC=" + tipoPubblicazione + " AND CODGARA=" + codGara + "))";
		        } 
		        List<?> w9cfPubbVisible = sqlManager.getListVector(sqlw9cfPubb + clausolaWhereVis, new Object[] {codGara});
	        	if (w9cfPubbVisible!= null && w9cfPubbVisible.size() > 0) {
	        		indice++;
	        		nrLotti = SqlManager.getValueFromVectorParam(w9cfPubbVisible.get(0), 0).longValue();
	        		listaCIG = "";
	        		for (int iLotti = 0; iLotti< w9cfPubbVisible.size(); iLotti ++) {
	        			listaCIG += SqlManager.getValueFromVectorParam(w9cfPubbVisible.get(iLotti), 1).stringValue() + ";";
	        		}
	        		JSONObject jsonItem =  new JSONObject();
	        		JSONObject jsonItemLi =  new JSONObject();
	    		    jsonItem.put("id", tipoPubblicazione.toString());
	    		    jsonItem.put("parent", "#");
	    		    nrPubblicazioniTotali = (Long)sqlManager.getObject(sqlVuoto, new Object[] {codGara, tipoPubblicazione});
	    		    pubblicazionePresente = true;
	    		    if (nrPubblicazioniTotali > 0) {
	    		    	if (tipo != null && !tipo.equals("")) {
	    		    		jsonItem.put("text", iconaPubblicitaLegale + "<b>" + nome + "</b><span style=\"display:none;\">" + listaCIG + "</span>");
	    		        } else {
	    		        	jsonItem.put("text", "<b>" + nome + "</b><span style=\"display:none;\">" + listaCIG + "</span>");
	    		        }
	    		    	//verifico lo stato
	    		    	nrPubblicazioni = (Long)sqlManager.getObject(sqlInCompilazione1, new Object[] {codGara, tipoPubblicazione});
	    		    	nrFlussi = (Long)sqlManager.getObject(sqlInCompilazione2, new Object[] {codGara, tipoPubblicazione});
	    		    	if (nrPubblicazioni.equals(nrLotti) && nrFlussi.equals(new Long(0))) {
	    		    		//In compilazione
	    		    		jsonItem.put("icon", request.getContextPath() + "/img/inCompilazione.png");
	    		    		jsonItemLi.put("title", "In compilazione");
	    		    	} else {
	    		    		nrPubblicazioni = (Long)sqlManager.getObject(sqlPubblicato, new Object[] {codGara, tipoPubblicazione});
	    		    		idRicevuto = (Long)sqlManager.getObject(sqlIdRicevuto, new Object[] {codGara, tipoPubblicazione});
	    		    		if (nrPubblicazioni.equals(nrLotti)) {
		    		    		//Pubblicato
	    		    			if (idRicevuto != null) {
	    		    				jsonItem.put("icon", request.getContextPath() + "/img/pubblicazioneEseguita.png");
	    		    			} else {
	    		    				jsonItem.put("icon", request.getContextPath() + "/img/pubblicato.png");
	    		    			}
		    		    		jsonItemLi.put("title", "Pubblicato");
		    		    	} else {
		    		    		nrPubblicazioni = (Long)sqlManager.getObject(sqlInCompilazioneParziale1, new Object[] {codGara, tipoPubblicazione});
			    		    	nrFlussi = (Long)sqlManager.getObject(sqlInCompilazioneParziale2, new Object[] {codGara, tipoPubblicazione});
			    		    	if (nrPubblicazioni < nrLotti && nrPubblicazioni > 0 && nrFlussi.equals(new Long(0))) {
			    		    		//In compilazione (per qualche lotto)
			    		    		jsonItem.put("icon", request.getContextPath() + "/img/inCompilazioneParziale.png");
			    		    		jsonItemLi.put("title", "In compilazione (per " + ((nrPubblicazioni == 1)?"un lotto":nrPubblicazioni + " lotti") + ")");
			    		    	} else {
			    		    		nrPubblicazioni = (Long)sqlManager.getObject(sqlPubblicatoParziale, new Object[] {codGara, tipoPubblicazione});
			    		    		if (nrPubblicazioni < nrLotti && nrPubblicazioni > 0) {
				    		    		//Pubblicato (per qualche lotto)
			    		    			if (idRicevuto != null) {
			    		    				jsonItem.put("icon", request.getContextPath() + "/img/pubblicazioneEseguita.png");
			    		    			} else {
			    		    				jsonItem.put("icon", request.getContextPath() + "/img/pubblicatoParziale.png");
			    		    			}
				    		    		jsonItemLi.put("title", "Pubblicato (per " + ((nrPubblicazioni == 1)?"un lotto":nrPubblicazioni + " lotti") + " )");
				    		    	} else {
				    		    		pubblicazionePresente = false;
				    		    		jsonItem.put("icon", request.getContextPath() + "/img/empty.png");
					    		    	JSONObject jsonItemA =  new JSONObject();
					    		    	String chiaveRigaJava= "W9PUBBLICAZIONI.CODGARA=N:" + codGara;
					    		    	jsonItemA.put("href", request.getContextPath() + "/ApriPagina.do?href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&key=" + chiaveRigaJava + "&modo=NUOVO&tipoPubblicazione=" + tipoPubblicazione + filtroLotto);
					    		    	jsonItem.put("a_attr", jsonItemA); 
				    		    	}
			    		    	}
		    		    	}
	    		    	}
	    		    	if (nrPubblicazioniTotali.equals(new Long(1)) && pubblicazionePresente) {
	    		    		numPubblicazione = (Long)sqlManager.getObject(sqlPubblicazione, new Object[] {codGara, tipoPubblicazione});
	    		    		String chiaveRigaJava= "W9PUBBLICAZIONI.CODGARA=N:" + codGara + ";W9PUBBLICAZIONI.NUM_PUBB=N:" + numPubblicazione;
	    		    		JSONObject jsonItemA =  new JSONObject();
		    		    	//jsonItemA.put("href", "javascript:chiaveRiga='" + chiaveRigaJava + "';listaVisualizza();");
	    		    		jsonItemA.put("href", request.getContextPath() + "/ApriPagina.do?href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&key=" + chiaveRigaJava + "&tipoPubblicazione=" + tipoPubblicazione + filtroLotto);
	    		    		jsonItem.put("a_attr", jsonItemA);
	    		    		//ricavo codice cig
	    		    		
	    		    	}
	    		    } else { 
	    		    	if (tipo != null && !tipo.equals("")) {
	    		    		jsonItem.put("text", iconaPubblicitaLegale + nome + "<span style=\"display:none;\">" + listaCIG + "</span>");
	    		    	} else {
	    		    		jsonItem.put("text", nome + "<span style=\"display:none;\">" + listaCIG + "</span>");
	    		    	}
	    		    	jsonItem.put("icon", request.getContextPath() + "/img/empty.png");
	    		    	JSONObject jsonItemA =  new JSONObject();
	    		    	String chiaveRigaJava= "W9PUBBLICAZIONI.CODGARA=N:" + codGara;
	    		    	jsonItemA.put("href", request.getContextPath() + "/ApriPagina.do?href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&key=" + chiaveRigaJava + "&modo=NUOVO&tipoPubblicazione=" + tipoPubblicazione + filtroLotto);
	    		    	jsonItem.put("a_attr", jsonItemA);
	    		    }
	    		    //
	    		    if (indice % 2 == 0) {
	    		    	jsonItemLi.put("CLASS", "even");
	    		    } else {
	    		    	jsonItemLi.put("CLASS", "odd");
	    		    }
		    		jsonItem.put("li_attr", jsonItemLi);
	    		    //Aggiungo tipologia documento 
	    		    json.add(jsonItem);
	    		    
	    		    //se ci sono più pubblicazioni per questa tipologia creo una riga per ogni pubblicazione
	    		    if (nrPubblicazioniTotali > 1) {
	    		    	List<?> w9pubblicazioni = sqlManager.getListVector(sqlPubblicazioni, new Object[] {codGara, tipoPubblicazione});
	    		    	if (w9pubblicazioni!= null && w9pubblicazioni.size() > 0) {
	    		    		for (int j = 0; j < w9pubblicazioni.size(); j++) {
	    		    			Long numPubb = SqlManager.getValueFromVectorParam(w9pubblicazioni.get(j), 0).longValue();
	    		    			String descrizione = (String) SqlManager.getValueFromVectorParam(w9pubblicazioni.get(j), 1).getValue();
	    		    			List<?> w9pubblicazioniCIG = sqlManager.getListVector(sqlPubblicazioniCIG, new Object[] {codGara, numPubb});
	    		    			listaCIG = "";
	    		    			for (int iLotti = 0; iLotti< w9pubblicazioniCIG.size(); iLotti ++) {
	    		        			listaCIG += SqlManager.getValueFromVectorParam(w9pubblicazioniCIG.get(iLotti), 0).stringValue() + ";";
	    		        		}
	    		    			JSONObject jsonItemChildren =  new JSONObject();
	    		    			jsonItemChildren.put("id", tipoPubblicazione.toString() + "-" + numPubb.toString());
	    		    			jsonItemChildren.put("parent", tipoPubblicazione.toString());
	    		    			jsonItemChildren.put("text", ((descrizione!=null)?descrizione:"Pubb nr." + numPubb) + "<span style=\"display:none;\">" + listaCIG + "</span>");
	    		    			jsonItemChildren.put("icon", request.getContextPath() + "/img/empty.png");
	    		    			String chiaveRigaJava= "W9PUBBLICAZIONI.CODGARA=N:" + codGara + ";W9PUBBLICAZIONI.NUM_PUBB=N:" + numPubb;
		    		    		JSONObject jsonItemA =  new JSONObject();
		    		    		jsonItemA.put("href", request.getContextPath() + "/ApriPagina.do?href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&key=" + chiaveRigaJava + "&tipoPubblicazione=" + tipoPubblicazione + filtroLotto);
			    		    	//jsonItemA.put("href", "javascript:chiaveRiga='" + chiaveRigaJava + "';listaVisualizza();");
			    		    	jsonItemChildren.put("a_attr", jsonItemA);
	    		    			json.add(jsonItemChildren);  
	    		    		} 
	    		    	}
	    		    }
	        	}
	        }
	      }
	    
	  } catch (Exception ex) {
		  ;
	  }
    
    String result = json.toString();
    result = result.replaceAll("\"CLASS\"", "\"class\"");
    return result;
  }

}
