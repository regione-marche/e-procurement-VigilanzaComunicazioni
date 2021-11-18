package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.commons.web.spring.DataSourceTransactionManagerBase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetILFSListaLavoriAction extends Action {

public final ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
      final HttpServletResponse response) throws Exception {

    DataSourceTransactionManagerBase.setRequest(request);

    response.setHeader("cache-control", "no-cache");
    response.setContentType("text/text;charset=utf-8");
    PrintWriter out = response.getWriter();

    String endpoint = request.getParameter("endpoint");
    String search_codice_lavoro = request.getParameter("search_codlav");
    String search_titolo = request.getParameter("search_titolo");
    String search_rup = request.getParameter("search_rup");

    String url = endpoint + "/ricercalavoro?";
    if (search_codice_lavoro != null) {
        url += "codicelavoro=" + search_codice_lavoro;
    } else {
    	url += "codicelavoro=";
    }
    if (search_titolo != null) {
      url += "&titolo=" + search_titolo;
    }
    if (search_rup != null) {
      url += "&rup=" + search_rup;
    }
    
    try {
    	HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse res = client.execute(get);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        
        String message = org.apache.commons.io.IOUtils.toString(rd);
        out.print(message);
        out.flush();
    } catch (Exception ex) {
    	;
    }
    
    

    return null;

  }

}
