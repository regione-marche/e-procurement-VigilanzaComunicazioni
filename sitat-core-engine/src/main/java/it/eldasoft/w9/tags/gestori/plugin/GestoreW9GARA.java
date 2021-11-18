package it.eldasoft.w9.tags.gestori.plugin;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 * Gestore di plugin per la scheda dell'entita' W9GARA.
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9GARA extends AbstractGestorePreload {

  /**
   * Query per estrazione del intestazione della stazione appaltante.
   */
  private static final String SQL_ESTRAZIONE_UFFINT_CODEIN = "select NOMEIN, VIAEIN, CITEIN, PROEIN from UFFINT where UFFINT.CODEIN = ? ";
  
  /**
   *  Query per estrazione di nome e cognome del RUP.
   */
  private static final String SQL_ESTRAZIONE_TECNI_COD_NOM = "select NOMTEC, CODTEC, CFTEC from TECNI where TECNI.CGENTEI = ? and TECNI.SYSCON = ?";
  
  /**
   *  Query per estrazione denominazione centro di costo.
   */
  private static final String SQL_ESTRAZIONE_DENOMCENTRO = "select DENOMCENTRO from W9GARA left join CENTRICOSTO ON CENTRICOSTO.IDCENTRO=W9GARA.IDCC where CODGARA = ?";
  
  /**
   * Costruttore.
   * @param tag BodyTagSupportGene
   */
  public GestoreW9GARA(final BodyTagSupportGene tag) {
    super(tag);
  }

  /**
   * Implementazione del metodo astratto AbstractGestorePreload.doAfterFetch.
   * 
   * @param arg0 PageContext
   * @param arg1 Modalita' di apertura della scheda
   * @throws JspException JspException
   */
  public void doAfterFetch(final PageContext arg0, final String arg1) throws JspException {
  }

  /**
   * Implementazione del metodo astratto AbstractGestorePreload.doBeforeBodyProcessing.
   * 
   * @param page PageContext
   * @param modoAperturaScheda Modalita' di apertura della scheda
   * @throws JspException JspException
   */
  public final void doBeforeBodyProcessing(final PageContext page, final String modoAperturaScheda)
      throws JspException {

    String codice = "";
    DataColumnContainer key = null;
    Long codgara = null;
    int iduser;
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        page, SqlManager.class);

    W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager",
        page, W9Manager.class);
    
    ProfiloUtente profiloUtente = (ProfiloUtente) page.getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE, PageContext.SESSION_SCOPE);

    try {

      String codein = page.getSession().getAttribute("uffint").toString();
      iduser = profiloUtente.getId();
      List< ? > datiListTecni = sqlManager.getListVector(
          SQL_ESTRAZIONE_TECNI_COD_NOM, new Object[] { codein, iduser });
      String nomeTecnico = "";
      String idTecnico = "";
      String cfTecnico = "";
      
      if (datiListTecni.size() > 0) {
        nomeTecnico = ((List< ? >) datiListTecni.get(0)).get(0).toString();
        idTecnico = ((List< ? >) datiListTecni.get(0)).get(1).toString();
        cfTecnico = ((List< ? >) datiListTecni.get(0)).get(2).toString();
      }

      Vector< ? > datiUffint = sqlManager.getVector(
          SQL_ESTRAZIONE_UFFINT_CODEIN, new Object[]{codein});
      
      String nomein = "";
      String indirizzoSede = "";
      String comuneSede = "";
      String provinciaSede = "";
      if (datiUffint != null && datiUffint.size() > 0) {
        nomein = StringUtils.trimToEmpty(((JdbcParametro) datiUffint.get(0)).getStringValue());
        indirizzoSede = StringUtils.trimToEmpty(((JdbcParametro) datiUffint.get(1)).getStringValue());
        comuneSede = StringUtils.trimToEmpty(((JdbcParametro) datiUffint.get(2)).getStringValue());
        provinciaSede = StringUtils.trimToEmpty(((JdbcParametro) datiUffint.get(3)).getStringValue());
      }

      page.setAttribute("idTecnico", idTecnico, PageContext.REQUEST_SCOPE);
      page.setAttribute("nomeTecnico", nomeTecnico, PageContext.REQUEST_SCOPE);
      page.setAttribute("cfTecnico", cfTecnico, PageContext.REQUEST_SCOPE);
      page.setAttribute("nomein", nomein, PageContext.REQUEST_SCOPE);
      page.setAttribute("indirizzoSede", indirizzoSede, PageContext.REQUEST_SCOPE);
      page.setAttribute("comuneSede", comuneSede, PageContext.REQUEST_SCOPE);
      page.setAttribute("provinciaSede", provinciaSede, PageContext.REQUEST_SCOPE);
      
      boolean isSAconPermessi = w9Manager.isStazioneAppaltanteConPermessi(codein);
      
      page.setAttribute("isSAconPermessi", isSAconPermessi, PageContext.REQUEST_SCOPE);
      
      if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
        codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
        
        key = new DataColumnContainer(codice);
        codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();

        page.setAttribute("codgara", codgara, PageContext.REQUEST_SCOPE);
        String denomcentro = (String)sqlManager.getObject(SQL_ESTRAZIONE_DENOMCENTRO, new Object[]{codgara});
        page.setAttribute("denomcentro", denomcentro, PageContext.REQUEST_SCOPE);
        
        /*String SQL_CTRL_BLOB = "select count(*) from W9DOCGARA where CODGARA=? and (FILE_ALLEGATO is not null or "
          + sqlManager.getDBFunction("length", new String[] { "FILE_ALLEGATO" }) + " > 0 or URL is not null or "
          + sqlManager.getDBFunction("length", new String[] { "URL" }) + " > 0 )";
        Long ctrlBlob = (Long) sqlManager.getObject(SQL_CTRL_BLOB, new Object[]{codgara});
        if (ctrlBlob == null
            ||
            ctrlBlob.equals(new Long(0))) {
          page.setAttribute("ctrlBlob", "false", PageContext.REQUEST_SCOPE);
        } else {
          page.setAttribute("ctrlBlob", "true", PageContext.REQUEST_SCOPE);
        }*/
      }
    } catch (SQLException sqle) {
      throw new JspException(
          "Errore nell'esecuzione della query per l'estrazione dei dati della Gara",
          sqle);
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati della Gara", exc);
    }
  }

}