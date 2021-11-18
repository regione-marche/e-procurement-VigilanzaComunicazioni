/*
 * 	Created on 15/nov/2010
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.web.struts;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.w9.bl.InviaBandoAvvisoSimapManager;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InviaBandoAvvisoSimapAction extends ActionBaseNoOpzioni {

  protected static final String    FORWARD_SUCCESS           = "inviabandoavvisosimapsuccess";
  protected static final String    FORWARD_ERROR             = "inviabandoavvisosimaperror";

  static Logger                    logger            = Logger.getLogger(InviaBandoAvvisoSimapAction.class);

  private InviaBandoAvvisoSimapManager  inviaBandoAvvisoSimapManager;

  public void setInviaBandoAvvisoSimapManager(InviaBandoAvvisoSimapManager inviaBandoAvvisoSimapManager) {
    this.inviaBandoAvvisoSimapManager = inviaBandoAvvisoSimapManager;
  }

  protected ActionForward runAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

    if (logger.isDebugEnabled()) logger.debug("InviaBandoAvvisoSimapAction: inizio metodo");

    String target = FORWARD_SUCCESS;
    String messageKey = null;

    Long codgara = new Long(request.getParameter("codgara"));
    String formulario = request.getParameter("formulario");
    
    String credenziali = request.getParameter("credenziali");
    String username = null;
    String password = null;
        
    if ("CORRENTI".equals(credenziali)) {
      ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
          CostantiGenerali.PROFILO_UTENTE_SESSIONE);
      
      username = profiloUtente.getLogin();
      String passwordCifrata = profiloUtente.getPwd();
      if (passwordCifrata != null) {
        ICriptazioneByte decriptatore;
        try {
          decriptatore = FactoryCriptazioneByte.getInstance(
              ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
              passwordCifrata.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
          password = new String(decriptatore.getDatoNonCifrato());
        } catch (CriptazioneException e) {
          logger.error(
              this.resBundleGenerale.getString(e.getChiaveResourceBundle()), e);
        }
      }
    } else if ("ALTRE".equals(credenziali)) {
      username = request.getParameter("username");
      password = request.getParameter("password");
    }
    
    try {
      inviaBandoAvvisoSimapManager.inviaBandoAvvisoSimap(codgara, formulario, username, password);
      target = FORWARD_SUCCESS;
      
    } catch (Exception e) {
      target = FORWARD_ERROR;
      messageKey = "errors.inviabandoavvisosimap.error";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey, e.getMessage());
    } catch (Throwable t) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), t);
      this.aggiungiMessaggio(request, messageKey);
    }

    if (messageKey != null) response.reset();

    if (logger.isDebugEnabled()) logger.debug("InviaBandoAvvisoSimapAction: fine metodo");

    return mapping.findForward(target);

  }

}
