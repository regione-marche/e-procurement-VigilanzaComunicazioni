package it.eldasoft.w9.tags.gestori.plugin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * Gestore di preload della scheda.
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9GARAENTINAZ extends AbstractGestorePreload {

  /**
   * Query per estrazione del intestazione della stazione appaltante.
   */
  private static final String SQL_ESTRAZIONE_UFFINT_CODEIN = "select NOMEIN from UFFINT where UFFINT.CODEIN = ? ";
  
  /**
   *  Query per estrazione di nome e cognome del RUP.
   */
  private static final String SQL_ESTRAZIONE_TECNI_COD_NOM = "select NOMTEC, CODTEC, CFTEC from TECNI where TECNI.CGENTEI = ? and TECNI.SYSCON = ?";
  
  /**
   * Costruttore.
   * @param tag BodyTagSupportGene
   */
  public GestoreW9GARAENTINAZ(final BodyTagSupportGene tag) {
    super(tag);
  }

  /**
   * Implementazione del metodo astratto AbstractGestorePreload.doBeforeBodyProcessing.
   * 
   * Metodo copiato dal omonimo metodo della classe GestoreW9GARA (dello stesso package)
   * 
   * @param page PageContext
   * @param modoAperturaScheda Modalita di apertura della scheda
   * @throws JspException JspException
   */
  @Override
  public final void
      doBeforeBodyProcessing(final PageContext page, final String modoAperturaScheda)
          throws JspException {
    String codice = "";
    DataColumnContainer key = null;
    Long codgara = null;
    int iduser;
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        page, SqlManager.class);

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

      String nomein = (String) sqlManager.getObject(
          SQL_ESTRAZIONE_UFFINT_CODEIN, new Object[]{codein});
      if (nomein == null
          ||
          nomein.trim().equals("")
          ||
          nomein.trim().equals("null")) {
        nomein = "";
      }

      page.setAttribute("idTecnico", idTecnico, PageContext.REQUEST_SCOPE);
      page.setAttribute("nomeTecnico", nomeTecnico, PageContext.REQUEST_SCOPE);
      page.setAttribute("cfTecnico", cfTecnico, PageContext.REQUEST_SCOPE);
      page.setAttribute("nomein", nomein, PageContext.REQUEST_SCOPE);
      
      if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
        codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
        key = new DataColumnContainer(codice);
        codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
  
        page.setAttribute("codgara", codgara, PageContext.REQUEST_SCOPE);
        String SQL_CTRL_BLOB = "select count(*) from W9DOCGARA where CODGARA=? and (FILE_ALLEGATO is not null or " + sqlManager.getDBFunction("length", new String[] { "FILE_ALLEGATO" }) + " > 0)"; 
        
        Long ctrlBlob = (Long) sqlManager.getObject(SQL_CTRL_BLOB, new Object[]{codgara});
        if (ctrlBlob == null
            ||
            ctrlBlob.equals(new Long(0))) {
          page.setAttribute("ctrlBlob", "false", PageContext.REQUEST_SCOPE);
        } else {
          page.setAttribute("ctrlBlob", "true", PageContext.REQUEST_SCOPE);
        }
      }
    } catch (SQLException sqle) {
      throw new JspException(
          "Errore nell'esecuzione della query per l'estrazione dei dati della Gara", sqle);
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati della Gara", exc);
    }
  }

  /**
   * Implementazione del metodo astratto AbstractGestorePreload.doAfterFetch.
   * 
   * @param page PageContext
   * @param modoAperturaScheda Modalita di apertura della scheda
   * @throws JspException JspException
   */
  @Override
  public void doAfterFetch(final PageContext page, final String modoAperturaScheda)
      throws JspException {
  }

}