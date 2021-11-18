/*
 * Created on 15/nov/2010
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.w3.web.struts;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.w3.bl.GestioneServiziIDGARACIGManager;
import it.eldasoft.sil.w3.bl.ValidazioneIDGARACIGManager;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ElaboraRichiesteLottiAction extends ActionBaseNoOpzioni {

  protected static final String           FORWARD_SUCCESS = "elaborarichiestelottisuccess";
  protected static final String           FORWARD_ERROR = "elaborarichiestelottierror";

  static Logger                           logger          = Logger.getLogger(ElaboraRichiesteLottiAction.class);

  private SqlManager                      sqlManager;

  private GestioneServiziIDGARACIGManager gestioneServiziIDGARACIGManager;
 
  private ValidazioneIDGARACIGManager     validazioneIDGARACIGManager;

  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  public void setGestioneServiziIDGARACIGManager(
      GestioneServiziIDGARACIGManager gestioneServiziIDGARACIGManager) {
    this.gestioneServiziIDGARACIGManager = gestioneServiziIDGARACIGManager;
  }

  public void setValidazioneIDGARACIGManager(
      ValidazioneIDGARACIGManager validazioneIDGARACIGManager) {
    this.validazioneIDGARACIGManager = validazioneIDGARACIGManager;
  }

  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    if (logger.isDebugEnabled())
      logger.debug("ElaboraRichiesteLottiAction: inizio metodo");

    String target = FORWARD_SUCCESS;
    String messageKey = null;

    try {
      Long numgara = new Long(request.getParameter("numgara"));
      String recuperaUser = request.getParameter("recuperauser");
      String recuperaPassword = request.getParameter("recuperapassword");
      String memorizza = request.getParameter("memorizza");

      ProfiloUtente profilo = (ProfiloUtente) request.getSession().getAttribute(
          CostantiGenerali.PROFILO_UTENTE_SESSIONE);
      Long syscon = new Long(profilo.getId());

      String codrup = request.getParameter("codrup");
      String simogwsuser = null;
      String simogwspass = null;

      // Leggo le eventuali credenziali memorizzate
      HashMap<String, String> hMapSIMOGWSUserPass = new HashMap<String, String>();
      hMapSIMOGWSUserPass = this.gestioneServiziIDGARACIGManager.recuperaSIMOGWSUserPass(syscon, codrup);

      // Gestione USER
      if (recuperaUser != null && "1".equals(recuperaUser)) {
        simogwsuser = ((String) hMapSIMOGWSUserPass.get("simogwsuser"));
      } else {
        simogwsuser = request.getParameter("simogwsuser");
      }

      // Gestione PASSWORD
      if (recuperaPassword != null && "1".equals(recuperaPassword)) {
        simogwspass = ((String) hMapSIMOGWSUserPass.get("simogwspass"));
      } else {
        simogwspass = request.getParameter("simogwspass");
      }

      // Gestione memorizzazione delle credenziali o eventuale cancellazione
      // (se richiesta)
      if (memorizza == null) {
        //this.gestioneServiziIDGARACIGManager.cancellaSIMOGWSUserPass(syscon, codrup);
      } else {
        this.gestioneServiziIDGARACIGManager.memorizzaSIMOGWSUserPass(syscon, codrup,
            simogwsuser, simogwspass);
      }

      // Gestione lista dei lotti
      List<?> datiW3LOTT = sqlManager.getListVector(
          "select numlott, oggetto, stato_simog from w3lott where numgara = ? and stato_simog in (1,3) order by numlott",
          new Object[] { numgara });

      List<Object> listaStatoElaborazioneRichieste = new Vector<Object>();
      Long numeroRichiesteDaElaborare = new Long(0);
      Long numeroRichiesteElaborate = new Long(0);

      if (datiW3LOTT != null && datiW3LOTT.size() > 0) {
        numeroRichiesteDaElaborare = new Long(datiW3LOTT.size());

        for (int i = 0; i < datiW3LOTT.size(); i++) {
          Long numlott = (Long) SqlManager.getValueFromVectorParam(
              datiW3LOTT.get(i), 0).getValue();

          String oggetto = (String) SqlManager.getValueFromVectorParam(
              datiW3LOTT.get(i), 1).getValue();

          Long stato_simog = (Long) SqlManager.getValueFromVectorParam(
              datiW3LOTT.get(i), 2).getValue();

          HashMap infoValidazione = new HashMap();
          infoValidazione = this.validazioneIDGARACIGManager.validateW3LOTT(
              syscon, numgara, numlott);

          int numeroErrori = 0;

          if (infoValidazione.get("numeroErrori") != null) {
            numeroErrori = ((Long) infoValidazione.get("numeroErrori")).intValue();
          }

          if (numeroErrori == 0) {
            if ((new Long(1)).equals(stato_simog)) {
              this.gestioneServiziIDGARACIGManager.richiestaCIG(simogwsuser,
                  simogwspass, numgara, numlott);
            } else {
              this.gestioneServiziIDGARACIGManager.modificaLOTTO(simogwsuser,
                  simogwspass, numgara, numlott);
            }
            numeroRichiesteElaborate = new Long(numeroRichiesteElaborate.longValue() + 1);
          } else {
            listaStatoElaborazioneRichieste.add(((Object) (new Object[] {
                numlott, oggetto })));
          }
        }

      }
      target = FORWARD_SUCCESS;
      request.getSession().setAttribute("numeroRichiesteDaElaborare",
          numeroRichiesteDaElaborare);
      request.getSession().setAttribute("numeroRichiesteElaborate",
          numeroRichiesteElaborate);
      request.getSession().setAttribute("listaStatoElaborazioneRichieste",
          listaStatoElaborazioneRichieste);
      request.getSession().setAttribute("numgara", numgara);
      request.getSession().setAttribute("numeroPopUp", "1");
    } catch (GestoreException e) {
      target = FORWARD_ERROR;
      messageKey = "errors.gestioneIDGARACIG.error";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey, e.getMessage());      
    } catch (Throwable e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey);
    }

    if (messageKey != null) response.reset();

    if (logger.isDebugEnabled())
      logger.debug("ElaboraRichiesteLottiAction: fine metodo");

    return mapping.findForward(target);

  }

}
