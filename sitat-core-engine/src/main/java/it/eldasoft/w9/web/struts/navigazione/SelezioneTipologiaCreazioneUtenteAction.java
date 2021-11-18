package it.eldasoft.w9.web.struts.navigazione;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.decorators.wizard.CostantiWizard;
import it.eldasoft.gene.web.struts.tags.AbstractCustomWizardAction;
import it.eldasoft.gene.web.struts.tags.WizardAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe per la gestione della navigazione nella pagina di selezione del
 * tecnico della stazione appaltante da utilizzare per creare un nuovo utente.
 * 
 * @author Stefano.Sabbadin
 */
public class SelezioneTipologiaCreazioneUtenteAction extends AbstractCustomWizardAction {

  /**
   * Costruttore.
   * 
   * @param action WizardAction
   */
  public SelezioneTipologiaCreazioneUtenteAction(WizardAction action) {
    super(action);
  }

  /**
   * Nessuna personalizzazione dell'azione indietro, quindi implementazione
   * vuota.
   * 
   * @see it.eldasoft.gene.web.struts.tags.AbstractCustomWizardAction#indietro(org.apache.struts.action.ActionMapping,
   *      org.apache.struts.action.ActionForm,
   *      javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public ActionForward indietro(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {
    return null;
  }

  /**
   * Gestisce la selezione della tipologia andando a resettare i dati nel
   * container in modo da partire da una situazione pulita (si potrebbe ad
   * esempio selezionare una tipologia, arrivare fino all'inserimento del
   * dettaglio, quindi tornare indietro, selezionare la tipologia di creazione
   * di un nuovo utente, ma non ci si deve trovare i dati impostati in
   * precedenza).
   * 
   * @see it.eldasoft.gene.web.struts.tags.AbstractCustomWizardAction#avanti(org.apache.struts.action.ActionMapping,
   *      org.apache.struts.action.ActionForm,
   *      javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public ActionForward avanti(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {
    DataColumnContainer datiSessione = (DataColumnContainer) request.getSession().getAttribute(
        CostantiWizard.NOME_OGGETTO_WIZARD_SESSIONE);

    String[] elencoColonne = new String[] {"USR_EIN.SYSCON",
        "TECNI.CODTEC",
        "TECNI.COGTEI",
        "TECNI.NOMETEI",
        "TECNI.NOMTEC",
        "TECNI.CFTEC",
        "TECNI.INDTEC",
        "TECNI.NCITEC",
        "TECNI.PROTEC",
        "TECNI.CAPTEC",
        "TECNI.LOCTEC",
        "TECNI.CITTEC",
        "TECNI.TELTEC",
        "TECNI.FAXTEC",
        "TECNI.EMATEC",
        "TECNI.SYSCON",
        "USRSYS.SYSCON",
        "USRSYS.SYSUTE",
        "USRSYS.SYSNOM",
        "USRSYS.SYSPWD",
        "USRSYS.EMAIL",
        "USRSYS.SYSPWBOU",
    "USRSYS.SYSAB3"};

    datiSessione.removeColumns(elencoColonne);

    return null;
  }

  /**
   * Non e' presente il pulsante Fine, per cui implementazione vuota del metodo.
   * 
   * @see it.eldasoft.gene.web.struts.tags.AbstractCustomWizardAction#fine(org.apache.struts.action.ActionMapping,
   *      org.apache.struts.action.ActionForm,
   *      javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public ActionForward fine(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {
    return null;
  }

  /**
   * Non viene svolto alcun codice in quanto non presente nella pagina.
   * 
   * @see it.eldasoft.gene.web.struts.tags.AbstractCustomWizardAction#salva(org.apache.struts.action.ActionMapping,
   *      org.apache.struts.action.ActionForm,
   *      javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public ActionForward salva(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {
    return null;
  }

  /**
   * Nessuna operazione speciale realizzata nella pagina, quindi implementazione
   * vuota.
   * 
   * @see it.eldasoft.gene.web.struts.tags.AbstractCustomWizardAction#extra(org.apache.struts.action.ActionMapping,
   *      org.apache.struts.action.ActionForm,
   *      javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public ActionForward extra(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {
    return null;
  }

}
