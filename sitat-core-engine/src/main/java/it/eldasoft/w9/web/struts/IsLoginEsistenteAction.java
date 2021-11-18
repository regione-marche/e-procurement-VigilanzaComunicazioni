package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.admin.AccountManager;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IsLoginEsistenteAction extends Action {

  private AccountManager accountManager;

  public void setAccountManager(AccountManager accountManager) {
    this.accountManager = accountManager;
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    response.setHeader("cache-control", "no-cache");
    response.setContentType("text/text;charset=utf-8");
    PrintWriter out = response.getWriter();

    String login = request.getParameter("login");
    HashMap<String, Boolean> hMapResult = new HashMap<String, Boolean>();

    if (accountManager.isUsedLogin(login, -1)) {
      hMapResult.put("loginEsistente", Boolean.TRUE);
    } else {
      hMapResult.put("loginEsistente", Boolean.FALSE);
    }
    JSONObject jsonResult = JSONObject.fromObject(hMapResult);
    out.println(jsonResult);

    out.flush();
    return null;
  }

}
