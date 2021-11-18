package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcSqlSelect;
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
public class GetListaComuniAction extends Action {

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

    String comune = request.getParameter("comune");
    comune = comune.toUpperCase().trim();
    comune = comune.replaceAll("'", "''");
    int maxRows = 10;
    
    String selectTABSCHE = "select tabsche.tabdesc, tb1.tabcod3, tb1.tabdesc, tabsche.tabcod3, tabsche.tabcod4 "
        + " from tabsche, tabsche tb1 "
        + " where tabsche.tabcod='S2003' and "
        + " tabsche.tabcod1='09' and "
        + " tb1.tabcod='S2003' and "
        + " tb1.tabcod1='07' and "
        + sqlManager.getDBFunction("substr", new String[] {"tabsche.tabcod3", "4", "3" })
        + " = "
        + sqlManager.getDBFunction("substr", new String[] {"tb1.tabcod2", "2", "3" })
        + " and "
        + sqlManager.getDBFunction("upper")
        + "(tabsche.tabdesc) like '"
        + comune
        + "' "
        + " order by tabsche.tabdesc";

    JdbcSqlSelect jdbcSqlTABSCHE = new JdbcSqlSelect(selectTABSCHE);
    List<?> datiTABSCHE = this.sqlManager.getListVector(jdbcSqlTABSCHE, 0, maxRows);
    
    if (datiTABSCHE != null && datiTABSCHE.size() > 0) {
      JSONArray jsonArrayTABSCHE = JSONArray.fromObject(datiTABSCHE.toArray());
      out.println(jsonArrayTABSCHE);
    }

    out.flush();
    return null;
  }

}
