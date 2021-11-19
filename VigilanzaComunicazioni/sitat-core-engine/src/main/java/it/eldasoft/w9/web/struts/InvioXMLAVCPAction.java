package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.AVCPManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InvioXMLAVCPAction extends ActionBaseNoOpzioni {

  private AVCPManager objAVCPManager;

  public void setAVCPManager(AVCPManager manager) {
    this.objAVCPManager = manager;
  }

  public ActionForward runAction(ActionMapping mapping, ActionForm form,
	      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
	  
	  response.setHeader("cache-control", "no-cache");
	  response.setContentType("text/text;charset=utf-8");
	  PrintWriter out = response.getWriter();
	  String result;
	  try {
		  result = this.objAVCPManager.inviaXML();
	  } catch (GestoreException e) {
		  result = "errore inaspettato!";
	  }
	  
      out.print(result);
      out.flush();
      return null;
  }

}
