package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Stefano.Cestaro
 * 
 */
public class GetListaCIGPubblicatiAction extends Action {

  /**
   * Manager per la gestione delle interrogazioni di database.
   */
  private SqlManager sqlManager;

  /**
   * @param sqlManager
   *        the sqlManager to set
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  public final ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
      final HttpServletResponse response) throws Exception {
    response.setHeader("cache-control", "no-cache");
    response.setContentType("text/text;charset=utf-8");
    PrintWriter out = response.getWriter();

    String codgara = request.getParameter("codgara");
    String cig = request.getParameter("cig");
    cig = cig.trim();
    cig = cig.replaceAll("'", "''");

    String selectCIG = "select L.CIG from W9GARA G join W9LOTT L on L.CODGARA=G.CODGARA where G.CODGARA=? and L.CIG like '" + cig + "%'";

    List<?> datiCIG = sqlManager.getListVector(selectCIG, new Object[]{new Long(codgara)});
    JSONArray jsonArrayCIG = null;
    if (datiCIG != null && datiCIG.size() > 0) {
      jsonArrayCIG = JSONArray.fromObject(datiCIG.toArray());
      
    }
    out.println(jsonArrayCIG);
    out.flush();
    return null;
  }

}
