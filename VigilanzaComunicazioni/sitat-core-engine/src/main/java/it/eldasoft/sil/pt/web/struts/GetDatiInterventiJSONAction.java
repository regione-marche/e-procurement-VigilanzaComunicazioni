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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;

/**
 * Action JSON per l'estrazione dei campi DIRGEN, STRUOP, REFERE, RESPUF di
 * INNTRI dagli interventi esistenti per aiutare l'utente nella compilazione dei
 * campi stessi.
 * 
 * @author Luca.Giacomazzo
 */
public class GetDatiInterventiJSONAction extends ActionBaseNoOpzioni {

  Logger logger = Logger.getLogger(GetDatiInterventiJSONAction.class);

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
		HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

  	if (logger.isDebugEnabled()) {
  		logger.debug("runAction: inizio metodo");
  	}
  
  	String strContri = request.getParameter("contri");
  	String strConint = request.getParameter("conint");
  	String strAnnint = request.getParameter("anno");
  	String strCuiint = request.getParameter("cuiint");

  	String nomeCampo = request.getParameter("nomeCampo");
  	String nomeCampoWhere = new String(request.getParameter("nomeCampo"));
  	
    String strDesc = request.getParameter("strDesc");
  	Long contri = new Long(strContri);
  	Long conint = null;
  	if (StringUtils.isNotEmpty(strConint)) {
  	  conint = new Long(strConint);
  	}
  	Long annint = null;
  	if (StringUtils.isNotEmpty(strAnnint)) {
  		annint = new Long(strAnnint);
  	}

  	String cuiint = null;
  	if (StringUtils.isNotEmpty(strCuiint)) {
      cuiint = new String(strCuiint);
    }
  	
    response.setHeader("cache-control", "no-cache");
    response.setContentType("text/text;charset=utf-8");
    
    if (StringUtils.equals(nomeCampo, "CODAUSA") | StringUtils.equals(nomeCampo, "SOGAGG")) {
      nomeCampo = " CODAUSA, SOGAGG ";
    } else {
    	if (StringUtils.equals(nomeCampo, "CUIINT")) {
    		nomeCampo = " CUIINT, DESINT ";
    	} else {
    		nomeCampo = "distinct(" + nomeCampo + ") "; 
    	}
    }
    
    try {
      PrintWriter out = response.getWriter();

      List<Vector<JdbcParametro>> datiIntervento = null;
      String sql = null;
      Object[] sqlParam = null;
      int parametriSql = 0;
      if (annint != null) {
          if (cuiint != null) {
            sql = "select " + nomeCampo + " from INTTRI where CONTRI=? and ANNRIF = ? and CUIINT <> '" + cuiint + "'";
          } else {
            sql = "select " + nomeCampo + " from INTTRI where CONTRI=? and ANNRIF = ?";
          }
        	parametriSql = 5;
      } else {
	      if (conint != null) {
	        if (cuiint != null) {
	          sql = "select " + nomeCampo + " from INTTRI where CONTRI=? and CONINT <> ? and CUIINT <> '" + cuiint + "'";
	        } else {
	          sql = "select " + nomeCampo + " from INTTRI where CONTRI=? and CONINT <> ?";
	        }
	        parametriSql = 1;
	      } else {
	        sql = "select " + nomeCampo + " from INTTRI where CONTRI=?";
	        parametriSql = 2;
        }
      }
      
      if (StringUtils.isNotEmpty(strDesc)) {
        sql = sql.concat(" and UPPER(" + nomeCampoWhere + ") like ?");
        strDesc = "%".concat(strDesc.toUpperCase()).concat("%");
      	if (parametriSql == 1)
      	  parametriSql = 3;
      	else if (parametriSql == 2)
      	  parametriSql = 4;
      }
      
      switch (parametriSql) {
      	case 1:
      	  sqlParam = new Object[] { contri, conint };
      	  break;
      	case 2:
      	  sqlParam = new Object[] { contri };
      	  break;
      	case 3:
      	  sqlParam = new Object[] { contri, conint, strDesc };
      	  break;
      	case 4:
      	  sqlParam = new Object[] { contri, strDesc };
      	  break;
      	case 5:
      	  sqlParam = new Object[] { contri, annint, strDesc };
      	  break;
      	default:
      	  break;
      }

      datiIntervento = this.sqlManager.getListVector(sql, sqlParam);

      JSONArray jsonArrayINTTRI = null;
      if (datiIntervento != null && datiIntervento.size() > 0) {
    	if (datiIntervento.size() > 15) {
    	  datiIntervento = datiIntervento.subList(0, 15);
    	}
        
      jsonArrayINTTRI = JSONArray.fromObject(datiIntervento.toArray());
        out.println(jsonArrayINTTRI);
      } else {
        jsonArrayINTTRI = new JSONArray();
        out.println(jsonArrayINTTRI);
      }
      
      out.flush();
    } catch (IOException e) {
      logger.error("Errore durante la lettura del writer della response", e);
      throw e;
    } catch (SQLException e) {
      logger.error("Errore durante l'estrazione dei dati dagli interventi precedenti (".concat(nomeCampo).concat(")"), e);
      throw new RuntimeException("Errore durante l'estrazione dei dati dagli interventi precedenti (".concat(nomeCampo).concat(")"), e);
    }
	
    if (logger.isDebugEnabled()) {
      logger.debug("runAction: fine metodo");
    }
    return null;
  }

}
