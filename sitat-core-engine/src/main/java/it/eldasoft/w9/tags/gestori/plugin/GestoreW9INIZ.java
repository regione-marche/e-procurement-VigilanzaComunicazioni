package it.eldasoft.w9.tags.gestori.plugin;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityDate;

/**
 * Gestore inizializzazione della scheda fase iniziale del contratto.
 * 
 * @author Vincenzo Margiotta
 */
public class GestoreW9INIZ extends AbstractGestorePreload {

  public GestoreW9INIZ(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {
  }

  public void doBeforeBodyProcessing(PageContext pageContext, String modoAperturaScheda)
      throws JspException {

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    try {
      String codice = (String) UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      DataColumnContainer key = new DataColumnContainer(codice);

      Long codGara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      Long codLott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      Long numappa = null;
      
      Object obj = null;

      if (UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
    	  numappa = new Long(pageContext.getSession().getAttribute("aggiudicazioneSelezionata").toString());
        obj = null;
        obj = sqlManager.getObject(
            "select DINIZ from W9CANT where CODGARA=? and CODLOTT=? and NUM_APPA=?",
            new Object[] { codGara, codLott, numappa });
        if (obj != null) {
          Date data = (Date) obj;
          pageContext.setAttribute("dataEffettivoInizioLavori",
              UtilityDate.convertiData(data, UtilityDate.FORMATO_GG_MM_AAAA),
              PageContext.REQUEST_SCOPE);
        }
        
        long occorrenzePUES = (Long) sqlManager.getObject(
            "select count(*) from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=1 and NUM_PUES=1",
            new Object[]{ codGara, codLott });
        
        if (occorrenzePUES > 0) {
          DataColumnContainer w9pues;
          String sqlW9pues = "SELECT DATA_GUCE, DATA_GURI, DATA_ALBO, QUOTIDIANI_NAZ, QUOTIDIANI_REG, PROFILO_COMMITTENTE, SITO_MINISTERO_INF_TRASP, SITO_OSSERVATORIO_CP FROM W9PUES WHERE CODGARA=? AND CODLOTT=? AND NUM_INIZ=1 AND NUM_PUES=1";
          try {
            w9pues = new DataColumnContainer(sqlManager, "W9PUES", sqlW9pues, new Object[] { codGara, codLott });
          } catch (Exception ex) {
            w9pues = null;
          }

          if (w9pues != null) {
            if (w9pues.getData("DATA_GUCE") != null) {
              pageContext.setAttribute("inizDATAGUCE",
                  UtilityDate.convertiData(w9pues.getData("DATA_GUCE"), UtilityDate.FORMATO_GG_MM_AAAA),
                  PageContext.REQUEST_SCOPE);
            }
            if (w9pues.getData("DATA_GURI") != null) {
              pageContext.setAttribute("inizDATAGURI",
                  UtilityDate.convertiData(w9pues.getData("DATA_GURI"), UtilityDate.FORMATO_GG_MM_AAAA),
                  PageContext.REQUEST_SCOPE);
            }
            if (w9pues.getData("DATA_ALBO") != null) {
              pageContext.setAttribute("inizDATAALBO",
                  UtilityDate.convertiData(w9pues.getData("DATA_ALBO"), UtilityDate.FORMATO_GG_MM_AAAA),
                  PageContext.REQUEST_SCOPE);
            }
            if (w9pues.getLong("QUOTIDIANI_NAZ") != null) {
              pageContext.setAttribute("inizQUOTIDNAZ", w9pues.getLong("QUOTIDIANI_NAZ"), PageContext.REQUEST_SCOPE);
            }
            if (w9pues.getLong("QUOTIDIANI_REG") != null) {
              pageContext.setAttribute("inizQUOTIDREG", w9pues.getLong("QUOTIDIANI_REG"), PageContext.REQUEST_SCOPE);
            }
            if (w9pues.getString("PROFILO_COMMITTENTE")!=null) {
              pageContext.setAttribute("inizPROFCOMMIT", w9pues.getString("PROFILO_COMMITTENTE"), PageContext.REQUEST_SCOPE);
            }
            if (w9pues.getString("SITO_MINISTERO_INF_TRASP")!=null) {
              pageContext.setAttribute("inizINFTRASP", w9pues.getString("SITO_MINISTERO_INF_TRASP"), PageContext.REQUEST_SCOPE);
            }
            if (w9pues.getString("SITO_OSSERVATORIO_CP") != null) {
              pageContext.setAttribute("inizSITOOSS", w9pues.getString("SITO_OSSERVATORIO_CP"), PageContext.REQUEST_SCOPE);
            }
          }
        }
      } else {
    	  Long numIniz = (key.getColumnsBySuffix("NUM_INIZ", true))[0].getValue().longValue();
    	  numappa = (Long)sqlManager.getObject("SELECT NUM_APPA FROM W9INIZ WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ?", new Object[]{codGara, codLott, numIniz});
      }

      String tipoContratto = (String) sqlManager.getObject(
          "select tipo_contratto from w9lott where codgara = ? and codlott = ?",
          new Object[] { codGara, codLott });
      Double importoAggiudicazione = (Double) sqlManager.getObject(
          "select importo_aggiudicazione from w9appa where codgara = ? and codlott = ? and num_appa = ?",
          new Object[] { codGara, codLott, numappa });

      if (tipoContratto != null && importoAggiudicazione != null) {
        if ("L".equals(tipoContratto)
            && importoAggiudicazione.doubleValue() > 1500000) {
          pageContext.setAttribute("visualizzaIncontriVigil", "TRUE",
              PageContext.REQUEST_SCOPE);
        } else {
          pageContext.setAttribute("visualizzaIncontriVigil", "FALSE",
              PageContext.REQUEST_SCOPE);
        }
      }

    } catch (Exception exc) {
      throw new JspException(
          "Errore nel caricamento dati della Fase Iniziale della Gara", exc);
    }
  }

}