package it.eldasoft.w9.tags.gestori.plugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.utils.UtilitySITAT;

public class GestoreW9SUBA extends AbstractGestorePreload {

	private static final String SQL_ESTRAZIONE_DATI_W9SUBA = "select CODIMP, ID_CPV from W9SUBA where CODGARA = ? AND CODLOTT = ? AND NUM_SUBA = ?";
	private static final String SQL_ESTRAZIONE_IMPR        = "select NOMEST from IMPR where codimp = ? ";
	private static final String SQL_ESTRAZIONE_DATI_TABCPV_CPVDESC = "select CPVDESC from TABCPV where CPVCOD4 = ?";
	private static final String SQL_ESTRAZIONE_DATI_W9AGGI =
	  "SELECT i.CODIMP, i.NOMIMP, g.ID_TIPOAGG " +
	    "FROM W9AGGI g, IMPR i " +
	   "WHERE g.CODGARA=? and g.CODLOTT=? and g.NUM_APPA=? and g.CODIMP=i.CODIMP and g.ID_TIPOAGG=3";  // id_tipoagg=3 <--> impresa singola
	
	
	public GestoreW9SUBA(BodyTagSupportGene tag) {
		super(tag);
	}

	public void doAfterFetch(PageContext arg0, String arg1) throws JspException {
	}

	public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda) throws JspException {
		SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        page, SqlManager.class);

    String codice = "";
    DataColumnContainer key = null;
    Long codgara = null;
    Long codlott = null;
    Long numsuba = null;
    Long numappa = null;
    
    codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    key = new DataColumnContainer(codice);
    codgara = (Long) (key.getColumnsBySuffix("CODGARA", true))[0].getValue().getValue();
    codlott = (Long) (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().getValue();
    
    if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
    	numsuba = (Long) (key.getColumnsBySuffix("NUM_SUBA", true))[0].getValue().getValue();
      try {
    	  numappa = (Long) sqlManager.getObject("SELECT NUM_APPA FROM W9SUBA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_SUBA = ?", new Object[]{codgara, codlott, numsuba});
    	  List<?> datiList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_W9SUBA,
            new Object[]{codgara, codlott, numsuba});
        String codimpr = "";
        String nomeimpr = "";
        String cpvid = "";
        String cpvdescr = "";
        if (datiList.size() > 0) {
          codimpr = ((List<?>) datiList.get(0)).get(0).toString();
          cpvid = ((List<?>) datiList.get(0)).get(1).toString();
          if (codimpr != null && !codimpr.equals("")) {
            List<?> nomeimprList = sqlManager.getListVector(
                SQL_ESTRAZIONE_IMPR, new Object[] { codimpr });
            if (nomeimprList.size() > 0) {
              nomeimpr = ((List<?>) nomeimprList.get(0)).get(0).toString();
            }
          }
          if (cpvid != null && !cpvid.equals("")) {
            List<?> cpvList = sqlManager.getListVector(
                SQL_ESTRAZIONE_DATI_TABCPV_CPVDESC, new Object[] { cpvid });
            if (cpvList.size() > 0) {
              cpvdescr = ((List<?>) cpvList.get(0)).get(0).toString();
            }
          }
        }
        page.setAttribute("nomeimpr", nomeimpr, PageContext.REQUEST_SCOPE);
        page.setAttribute("cpvdescr", cpvdescr, PageContext.REQUEST_SCOPE);
        
        List<?> datiLista = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_W9AGGI,
            new Object[] { codgara, codlott, numappa});
      
        if (datiList != null && datiList.size() > 0) {
          page.setAttribute("numImpreseAggiudicatarie", datiLista.size(), PageContext.REQUEST_SCOPE);
        }
        
      } catch (SQLException sqle) {
        throw new JspException(
            "Errore nell'esecuzione della query per l'estrazione dei dati relativi al Subappalto",
            sqle);
      } catch (Exception exc) {
        throw new JspException(
            "Errore nel caricamento dati relativi al Subappalto", exc);
      }
    } else {
      codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      key = new DataColumnContainer(codice);
      codgara = (Long) (key.getColumnsBySuffix("CODGARA", true))[0].getValue().getValue();
      codlott = (Long) (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().getValue();
      numappa = new Long(page.getSession().getAttribute("aggiudicazioneSelezionata").toString());
      try {
        List<?> datiList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_W9AGGI,
            new Object[] { codgara, codlott, numappa });
      
        if (datiList != null && datiList.size() > 0) {
          page.setAttribute("numImpreseAggiudicatarie", datiList.size(), PageContext.REQUEST_SCOPE);
        }

      } catch (SQLException sqle) {
        throw new JspException(
            "Errore nell'esecuzione della query per l'estrazione dei dati relativi al Subappalto",
            sqle);
      } catch (Exception exc) {
        throw new JspException(
            "Errore nel caricamento dati relativi al Subappalto", exc);
      }
    }
    
    try {
      int versioneSimog = UtilitySITAT.getVersioneSimog(sqlManager, codgara);
      if (versioneSimog >= 5) {
        // carico i CPV del lotto per 
        List<Vector<?>> listaCPV = sqlManager.getListVector(
            "select CPV from W9LOTT where CODGARA=? and CODLOTT=? union select CPV from W9CPV  where CODGARA=? and CODLOTT=?",
            new Object[] { codgara, codlott, codgara, codlott } );

        if (listaCPV != null && listaCPV.size() > 0) {
          List<String[]> listaCpvCodiceDescr = new ArrayList<String[]>();
          for (int i=0; i < listaCPV.size(); i++) {
            Vector<?> vect = (Vector<?>) listaCPV.get(i);
            String codCpv = ((JdbcParametro) vect.get(0)).getStringValue();
            String descrCpv = (String) sqlManager.getObject("select CPVDESC from TABCPV where CPVCOD4=?", new Object[] { codCpv });
            
            String[] arrayCpv = new String[] { codCpv, descrCpv };
            listaCpvCodiceDescr.add(arrayCpv);
          }
          page.setAttribute("listaCpvCodiceDescr", listaCpvCodiceDescr, PageContext.REQUEST_SCOPE);
        }
        if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
        	numsuba = (Long) (key.getColumnsBySuffix("NUM_SUBA", true))[0].getValue().getValue();
          numappa = (Long) sqlManager.getObject("SELECT NUM_APPA FROM W9SUBA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_SUBA = ?", new Object[]{codgara, codlott, numsuba});
          String valoreCPV = (String) sqlManager.getObject("select ID_CPV from W9SUBA where CODGARA=? and CODLOTT=? and NUM_SUBA=? and NUM_APPA=?",
            new Object[] { codgara, codlott, numsuba, numappa } );
          if (StringUtils.isNotEmpty(valoreCPV)) {
            page.setAttribute("valoreCPV", valoreCPV, PageContext.REQUEST_SCOPE);
            String descrCPV = (String) sqlManager.getObject("select CPVDESC from TABCPV where CPVCOD4=?", new Object[] { valoreCPV });
            page.setAttribute("descrCPV", descrCPV, PageContext.REQUEST_SCOPE);
          }
        }
      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}