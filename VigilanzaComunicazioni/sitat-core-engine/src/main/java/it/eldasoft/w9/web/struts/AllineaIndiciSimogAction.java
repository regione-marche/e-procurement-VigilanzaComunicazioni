package it.eldasoft.w9.web.struts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.w9.bl.AllineaIndiciSimogManager;
import it.eldasoft.w9.bl.EsportazioneXMLSIMOGManager;

public class AllineaIndiciSimogAction extends ActionBaseNoOpzioni {

  /** Logger di classe. */
  private static Logger     logger = Logger.getLogger(AllineaIndiciSimogAction.class);
  
  /**
   * Manager per gestione della logica di business e delle transazioni sul db.
   */
  private AllineaIndiciSimogManager allineaIndiciSimogManager;

  /**
   * Manager per la gestione della logica di esportazione XML verso SIMOG.
   */
  private EsportazioneXMLSIMOGManager exportXmlSimogManager;
  
  /**
   * @param allineaIndiciSimogManager
   *        allineaIndiciSimogManager da settare internamente alla classe.
   */
  public void setAllineaIndiciSimogManager(AllineaIndiciSimogManager allineaIndiciSimogManager) {
    this.allineaIndiciSimogManager = allineaIndiciSimogManager;
  }
  
  /**
   * @param exportXmlSimogManager the esportXmlSimogManager to set.
   */
  public void setEsportazioneXMLSIMOGManager(
      EsportazioneXMLSIMOGManager exportXmlSimogManager) {
    this.exportXmlSimogManager = exportXmlSimogManager;
  }
  
  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSIMOG: inizio metodo");
    }

    String target = CostantiGeneraliStruts.FORWARD_OK.concat("RiallineaIndiciSIMOG");
    String messageKey = null;
    String codiceCIG = null;
    String existCredenziali = null;
    
    if (request.getParameter("codiceCIG") != null) {
      codiceCIG = (String) request.getParameter("codiceCIG");
    }
    if (request.getParameter("existCredenziali") != null) {
      existCredenziali = request.getParameter("existCredenziali");
    }

    int syscon = ((ProfiloUtente) request.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE)).getId();
    String codUffInt = (String) request.getSession().getAttribute(
        CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);

    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    
    try {
      String simoguser = null;
      String simogpass = null;
        
      if (existCredenziali != null && existCredenziali.equals("no")) {
        String recuperaUser = request.getParameter("recuperauser");
        String recuperaPassword = request.getParameter("recuperapassword");
        String memorizza = request.getParameter("memorizza");

        // Leggo le eventuali credenziali memorizzate
        HashMap<String, String> hMapSIMOGLAUserPass = new HashMap<String, String>();
        hMapSIMOGLAUserPass = this.exportXmlSimogManager.recuperaSIMOGLAUserPass(new Long(syscon));
        
        // Gestione USER
        if ("1".equals(recuperaUser)) {
          simoguser = ((String) hMapSIMOGLAUserPass.get("simoguser"));
        } else {
          simoguser = request.getParameter("simoguser");
        }

        // Gestione PASSWORD
        if ("1".equals(recuperaPassword)) {
          simogpass = ((String) hMapSIMOGLAUserPass.get("simogpass"));
        } else {
          simogpass = request.getParameter("simogpass");
        }
        // Gestione memorizzazione delle credenziali o eventuale cancellazione (se richiesta)
        this.exportXmlSimogManager.gestioneWsSimogLoaderAppaltoUserPass(new Long(syscon), 
            simoguser, simogpass, memorizza != null);
      }

      this.allineaIndiciSimogManager.riallineaIndiciSimog(codiceCIG, codUffInt, resultMap, simoguser, simogpass);

      if (resultMap != null) {
        Boolean esito = (Boolean) resultMap.get("esito");
        if (esito.booleanValue()) {
          logger.info("Allineamento della gara e del relativo lotto terminato con esito positivo (CIG='"
              + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
              + " per conto della SA con CODUFFINT=" + codUffInt + ")");
          request.setAttribute("RISULTATO", "OK");
          request.setAttribute("codiceCIG", codiceCIG);
        } else {
          target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE.concat("RiallineaIndiciSIMOG");
          logger.info("Allineamento della gara e del relativo lotto terminato con esito negativo (CIG='"
              + codiceCIG + "' da parte dell'utente con SYSCON=" + syscon
              + " per conto della SA con CODUFFINT=" + codUffInt + ")");
          if (resultMap.containsKey("errorMsg")) {
            this.aggiungiMessaggio(request, "errors.indiciSimog.erroreDaMettereAVideo",
                ((String) resultMap.get("errorMsg")));
          } else {
            this.aggiungiMessaggio(request, "errors.indiciSimog.erroreWS", ((String) resultMap.get("errorMsg")));
          }
          request.setAttribute("RISULTATO", "KO");
          request.setAttribute("codiceCIG", codiceCIG);
        }
      }
      
    } catch (SQLException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.database.dataAccessException";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey);
    } catch (Throwable t) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), t);
      this.aggiungiMessaggio(request, messageKey);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("riallineaIndiciSIMOG: fine metodo");
    }
    return mapping.findForward(target);
  }

}
