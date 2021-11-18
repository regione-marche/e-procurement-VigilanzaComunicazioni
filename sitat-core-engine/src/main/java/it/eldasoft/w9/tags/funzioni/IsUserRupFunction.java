package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Classe per la determinazione se un utente puo' oppure no modificare una gara.
 * 
 */
public class IsUserRupFunction extends AbstractFunzioneTag {

  private static final String MODIFICA_NO                 = "no";
  private static final String MODIFICA_SI                 = "si";
  
  public IsUserRupFunction() {
    super(3, new Class[] { PageContext.class, String.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    String modificabile = MODIFICA_NO;

    try {
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
      W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager", pageContext, W9Manager.class);
      w9Manager.setSqlManager(sqlManager);
      
      String key = (String) params[1];
      String entita = params[2].toString();
      
      if (key == null || key.equals("")) {
        key = UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      }
      
      if (key == null || key.equals("")) {
    	  key = (String) UtilityTags.getParametro(pageContext,
            UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
      }
      DataColumnContainer keys = new DataColumnContainer(key);
      
      if (key != null && (entita.equals("W9GARA") || entita.equals("W9LOTT"))) {
    	  
    	  // La modifica non e' consentita se l'utente non e' amministratore e non e' il RUP della gara
    	  HttpSession sessione = pageContext.getSession();
    	  String ufficioIntestatario = (String) sessione.getAttribute(
    	      CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
    	  ProfiloUtente profiloUtente = (ProfiloUtente) sessione.getAttribute(
    	      CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    	  
    	  Long codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
    	  
    	  if (w9Manager.isUserRupDellaGara(codgara, new Long(profiloUtente.getId()), ufficioIntestatario)) {
    	    modificabile = MODIFICA_SI;
        }
      }

    } catch (SQLException sqle) {
      throw new JspException(
          "Errore in fase di esecuzione della query per i controlli preventivi all'eliminazione "
          + "del Programma, della Gara, del Lotto, o della Fase/evento", sqle);
    } catch (Exception exc) {
      throw new JspException(
          "Errore in fase di esecuzione della query per i controlli preventivi all'eliminazione "
          + "del Programma, della Gara, del Lotto, o della Fase/evento", exc);
    }
    return modificabile;
  }

}