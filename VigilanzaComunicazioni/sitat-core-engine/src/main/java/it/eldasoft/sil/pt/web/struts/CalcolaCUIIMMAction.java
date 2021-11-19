package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Cristian.Febas
 *
 */
public class CalcolaCUIIMMAction extends Action {

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

  @Override
  public final ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
      final HttpServletResponse response) throws Exception {
    response.setHeader("cache-control", "no-cache");
    response.setContentType("text/text;charset=utf-8");
    PrintWriter out = response.getWriter();
    JSONObject jsonCUIIMM = new JSONObject();

    Long contri = new Long(request.getParameter("contri"));

    String cuiimm = null;
    String stranno = null;
    String strprog = null;

    String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    String selCFAMM = "select CFEIN from UFFINT where CODEIN = ?";
    String cfamm = (String) sqlManager.getObject(selCFAMM, new Object[]{codein});
    cfamm = UtilityStringhe.convertiNullInStringaVuota(cfamm);

    String selAnno = "select ANNTRI from PRIATRI where CONTRI = ?";
    Long anno = (Long) sqlManager.getObject(selAnno, new Object[]{contri});
    if(anno != null){
      stranno = anno.toString();
      UtilityStringhe.convertiNullInStringaVuota(stranno);
    }

    String selProgressivo = "select TAB2D2 from TAB2 where TAB2COD = ? and TAB2D1 = ?";
    strprog = (String) sqlManager.getObject(selProgressivo, new Object[]{"PT001","1"});
    strprog = UtilityStringhe.convertiNullInStringaVuota(strprog);
    if(!"".equals(strprog)){
      Long prog = new Long(strprog);
      prog++;
      strprog = prog.toString();
      strprog = UtilityStringhe.fillLeft(strprog, '0', 5);
    }


    if(!"".equals(cfamm) && !"".equals(stranno) && !"".equals(strprog)){
      cuiimm = cfamm + stranno +strprog;
    }

    if (!"".equals(cuiimm)) {
      jsonCUIIMM.put("cuiimm",cuiimm);
    }
    out.println(jsonCUIIMM);
    out.flush();
    return null;
  }

}
