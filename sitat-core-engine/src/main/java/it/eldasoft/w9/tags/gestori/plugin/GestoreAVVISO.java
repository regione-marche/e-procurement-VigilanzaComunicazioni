package it.eldasoft.w9.tags.gestori.plugin;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestoreAVVISO extends AbstractGestorePreload {

  private static final String SQL_SELECT_CIG = "select W9LOTT.CIG from W9LOTT, AVVISO where AVVISO.CODGARA = W9LOTT.CODGARA AND AVVISO.CODLOTT = W9LOTT.CODLOTT AND AVVISO.IDAVVISO = ? AND AVVISO.CODEIN = ?";

  /**
   * Query per estrazione del intestazione della stazione appaltante.
   */
  private static final String SQL_ESTRAZIONE_UFFINT_CODEIN = "select NOMEIN from UFFINT where UFFINT.CODEIN = ? ";
 
  /**
   *  Query per estrazione di nome e cognome del RUP.
   */
  private static final String SQL_ESTRAZIONE_TECNI_COD_NOM = "select CODTEC, NOMTEC from TECNI where TECNI.CGENTEI = ? and TECNI.SYSCON = ?";
 
  public GestoreAVVISO(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

  }

  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda)
      throws JspException {
    String codice = "";
    DataColumnContainer key = null;
    Long idavviso = null;
    int iduser;
    String codein = "";
    String nomein = "";
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        page, SqlManager.class);

    ProfiloUtente profiloUtente = (ProfiloUtente) page.getAttribute(
            CostantiGenerali.PROFILO_UTENTE_SESSIONE, PageContext.SESSION_SCOPE);

    try {

    	
      if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
        codice = (String) UtilityTags.getParametro(page,
            UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
        key = new DataColumnContainer(codice);
        idavviso = (key.getColumnsBySuffix("IDAVVISO", true))[0].getValue().longValue();
        codein = (key.getColumnsBySuffix("CODEIN", true))[0].getValue().toString();
        String SQL_CTRL_BLOB  = "select " + sqlManager.getDBFunction("length", new String[] { "FILE_ALLEGATO" }) + " as FILE_ALLEGATO from AVVISO where IDAVVISO = ? AND CODEIN = ?";
        
        String ctrlBlob = ""
            + sqlManager.getObject(SQL_CTRL_BLOB, new Object[] { idavviso,
                codein });
        String cig = "";
        if (sqlManager.getObject(SQL_SELECT_CIG, new Object[] { idavviso,
            codein }) != null) {
          cig = sqlManager.getObject(SQL_SELECT_CIG,
              new Object[] { idavviso, codein }).toString();
        }
        if (ctrlBlob == null
            || ctrlBlob.trim().equals("")
            || ctrlBlob.trim().equals("0")
            || ctrlBlob.trim().equals("null"))
          page.setAttribute("ctrlBlob", "false", PageContext.REQUEST_SCOPE);
        else
          page.setAttribute("ctrlBlob", "true", PageContext.REQUEST_SCOPE);

        page.setAttribute("cig", cig, PageContext.REQUEST_SCOPE);
      	} else {
    	  	codein = page.getSession().getAttribute("uffint").toString();
      		iduser = profiloUtente.getId();
      		List< ? > datiListTecni = sqlManager.getListVector(
      	          SQL_ESTRAZIONE_TECNI_COD_NOM, new Object[] { codein, iduser });
      		String idTecnico = "";
      		String nomeTecnico = "";
      		if (datiListTecni.size() > 0) {
      			idTecnico = ((List< ? >) datiListTecni.get(0)).get(0).toString();
      			nomeTecnico = ((List< ? >) datiListTecni.get(0)).get(1).toString();
            }
      		page.setAttribute("idTecnico", idTecnico, PageContext.REQUEST_SCOPE);
      		page.setAttribute("nomeTecnico", nomeTecnico, PageContext.REQUEST_SCOPE);
      	}

      Vector< ? > datiUffint = sqlManager.getVector(
	          SQL_ESTRAZIONE_UFFINT_CODEIN, new Object[]{codein});
      if (datiUffint != null && datiUffint.size() > 0) {
          nomein = StringUtils.trimToEmpty(((JdbcParametro) datiUffint.get(0)).getStringValue());
      }
      page.setAttribute("nomein", nomein, PageContext.REQUEST_SCOPE);
    } catch (SQLException sqle) {
      throw new JspException(
          "Errore nell'esecuzione della query per l'estrazione dei dati dell'Avviso",
          sqle);
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati dell'Avviso", exc);
    }

  }

}
