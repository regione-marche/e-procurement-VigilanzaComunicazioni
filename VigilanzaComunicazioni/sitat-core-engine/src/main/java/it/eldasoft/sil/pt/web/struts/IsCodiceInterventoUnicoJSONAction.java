package it.eldasoft.sil.pt.web.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;

/**
 * Action JSON che dalla popup di modifica de
 * 
 * @author Luca.Giacomazzo
 */
public class IsCodiceInterventoUnicoJSONAction extends ActionBaseNoOpzioni {

  Logger logger = Logger.getLogger(IsCodiceInterventoUnicoJSONAction.class);

  /**
	* Manager per la gestione delle interrogazioni di database.
    */
  private SqlManager sqlManager;

  /**
   * @param sqlManager
   *            the sqlManager to set
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
	
  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
      logger.debug("runAction: inizio metodo");
    }

  	String strContri = request.getParameter("contri");
  	String strConint = request.getParameter("conint");
  	String codiceIntervento = request.getParameter("cui");
  	Long contri = new Long(strContri);
  	Long conint = new Long(strConint);
	
    response.setHeader("cache-control", "no-cache");
    response.setContentType("text/text;charset=utf-8");
    
    try {
      PrintWriter out = response.getWriter();

      String sqlIntervento = "select NPROGINT from INTTRI where CONTRI=? and CONINT <> ? and CUIINT=?";
      String sqlInterventoNonRiproposto = "select CONTRI from INRTRI where CONTRI=? and CUIINT=?";
      Object[] sqlParamIntervento = new Object[] { contri, conint, codiceIntervento };
      Object[] sqlParamInterventoNR = new Object[] { contri, codiceIntervento }; 
      
      List<?> listaInterventi = this.sqlManager.getListVector(sqlIntervento, sqlParamIntervento);
      Long numeroInterventi = new Long(0);
      StringBuffer interventi = new StringBuffer("");
      if (listaInterventi != null && listaInterventi.size() > 0) {
        numeroInterventi = new Long(listaInterventi.size());
        for (int i=0; i < listaInterventi.size(); i++) {
      	  JdbcParametro jp = (JdbcParametro) ((Vector<?>) listaInterventi.get(i)).get(0);
      	  interventi.append(jp.getStringValue());
      	  if (i < listaInterventi.size()-1) {
      	    interventi.append(", ");
      	  }
      	}
      }
      
      List<?> listaInterventiNonRiproposti = this.sqlManager.getListVector(sqlInterventoNonRiproposto, sqlParamInterventoNR);
      Long numeroInterventiNonRiproposti = new Long(0);
      StringBuffer interventiNR = new StringBuffer("");
      if (listaInterventiNonRiproposti != null && listaInterventiNonRiproposti.size() > 0) {
        numeroInterventiNonRiproposti = new Long(listaInterventiNonRiproposti.size());
      	for (int i=0; i < listaInterventiNonRiproposti.size(); i++) {
      	  JdbcParametro jp = (JdbcParametro) ((Vector<?>) listaInterventiNonRiproposti.get(i)).get(0);
      	  interventiNR.append(jp.getStringValue());
      	  if (i < listaInterventiNonRiproposti.size()-1) {
      		interventiNR.append(", ");
      	  }
      	}
      }
      
      String[] msgError = new String[2]; 
      if (numeroInterventi + numeroInterventiNonRiproposti > 0) {
     	  msgError[0] = "Il CUI indicato è già utilizzato";
      } 
      
      JSONArray jsonArrayMsg = null;
      if (numeroInterventi > 0 || numeroInterventiNonRiproposti > 0) {
        jsonArrayMsg = JSONArray.fromObject(msgError);
        out.println(jsonArrayMsg);
      } else {
        jsonArrayMsg = new JSONArray();
        out.println(jsonArrayMsg);
      }
      
      out.flush();
    } catch (IOException e) {
      logger.error("Errore durante la lettura del writer della response", e);
      throw e;
    } catch (SQLException e) {
      logger.error("Errore durante l'estrazione dei dati dagli interventi precedenti (", e);
      throw new RuntimeException("Errore durante l'estrazione dei dati dagli interventi precedenti (", e);
    }
	
    if (logger.isDebugEnabled()) {
      logger.debug("runAction: fine metodo");
    }
    return null;
  }

}
