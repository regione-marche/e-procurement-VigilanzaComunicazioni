package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.common.CostantiW9;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 * Function per determinare se l'utente loggato e' un tecnico per la stazione appaltante in uso. 
 * 
 * @author Stefano.Sabbadin
 */
public class ControllUserTecniFunction extends AbstractFunzioneTag {

  public ControllUserTecniFunction() {
    super(1, new Class[] { PageContext.class });
  }

  /**
   * @see it.eldasoft.gene.tags.utils.AbstractFunzioneTag#function(javax.servlet.jsp.PageContext,
   *      java.lang.Object[])
   */
  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {
    
    HttpSession sessione = pageContext.getSession();
    boolean isUserTecnicoDellaSA = false;
    boolean isUserConCfNoTecnicoSA = false;
    
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager",
        pageContext, W9Manager.class);
    w9Manager.setSqlManager(sqlManager);
    
    try {
      String ufficioIntestatario = (String) sessione.getAttribute(
          CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
      if (StringUtils.isNotEmpty(ufficioIntestatario)) {
        ProfiloUtente profiloUtente = (ProfiloUtente) sessione.getAttribute(
            CostantiGenerali.PROFILO_UTENTE_SESSIONE);
        
        Long numeroOccorrenze = (Long) sqlManager.getObject(
            "select count(codtec) from tecni where tecni.syscon = ? and tecni.cgentei = ? ",
                new Object[] {new Long(profiloUtente.getId()), ufficioIntestatario});
        
        if (numeroOccorrenze.longValue() > 0) {
          isUserTecnicoDellaSA = true;
        } else {
          String codFisUsrsys = (String) sqlManager.getObject(
              "select syscf from usrsys where syscon = ?",
                  new Object[] {new Long(profiloUtente.getId()) });
          
          if (StringUtils.isNotEmpty(codFisUsrsys)) {
            isUserConCfNoTecnicoSA = true;
          }
        }
      }
    } catch (NumberFormatException e) {
      throw new JspException(e);
    } catch (SQLException e) {
      throw new JspException(e);
    }
    
    pageContext.setAttribute(CostantiW9.IS_USER_TECNICO_SA,
        new Boolean(isUserTecnicoDellaSA), PageContext.SESSION_SCOPE);
    pageContext.setAttribute(CostantiW9.IS_USER_CONCF_NO_TECNICO_SA,
        new Boolean(isUserConCfNoTecnicoSA), PageContext.SESSION_SCOPE);
    
    return "" + isUserTecnicoDellaSA;
  }

}