package it.eldasoft.w9.web.struts.navigazione;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.decorators.wizard.CostantiWizard;
import it.eldasoft.gene.web.struts.tags.AbstractCustomWizardAction;
import it.eldasoft.gene.web.struts.tags.GestoreEccezioni;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.WizardAction;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.tags.gestori.plugin.GestoreSelezioneAccountAltraSA;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe per la gestione della navigazione nella pagina di selezione
 * dell'account associato ad un'altra stazione appaltante da utilizzare per
 * creare un nuovo utente.
 * 
 * @author Stefano.Sabbadin
 */
public class SelezioneAccountAltraSAAction extends AbstractCustomWizardAction {

  /**
   * Costruttore.
   * 
   * @param action WizardAction
   */
  public SelezioneAccountAltraSAAction(WizardAction action) {
    super(action);
  }

  /** Logger della classe. */
  private static Logger logger = Logger.getLogger(SelezioneAccountAltraSAAction.class);

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
   * Gestisce l'inserimento di un nuovo utente memorizzando in sessione i dati
   * del tecnico selezionato.
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

    try {
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          request.getSession().getServletContext(), SqlManager.class);

      Long syscon = datiSessione.getLong("USRSYS.SYSCON");
      Vector<JdbcParametro> datiAccount = sqlManager.getVector(
          "select SYSUTE, SYSNOM, EMAIL, SYSPWBOU, SYSAB3 from USRSYS where syscon = ?",
          new Object[] { syscon });
      if (datiAccount != null) {
        datiSessione.addColumn("USRSYS.SYSUTE", datiAccount.get(0));
        datiSessione.addColumn("USRSYS.SYSNOM", datiAccount.get(1));
        datiSessione.addColumn("USRSYS.EMAIL", datiAccount.get(2));
        datiSessione.addColumn("USRSYS.SYSPWBOU", datiAccount.get(3));
        datiSessione.addColumn("USRSYS.SYSAB3", datiAccount.get(4));
      }

      String uffint = datiSessione.getString("SA_IMPORT");
      Vector<JdbcParametro> datiTecnico = sqlManager.getVector(
          "select COGTEI, NOMETEI, NOMTEC, CFTEC, INDTEC, NCITEC, PROTEC, CAPTEC, LOCTEC, CITTEC, TELTEC, FAXTEC, EMATEC from TECNI where syscon = ? and cgentei = ?",
          new Object[] { syscon, uffint });
      if (datiTecnico != null) {
        datiSessione.addColumn("TECNI.COGTEI", datiTecnico.get(0));
        datiSessione.addColumn("TECNI.NOMETEI", datiTecnico.get(1));
        datiSessione.addColumn("TECNI.NOMTEC", datiTecnico.get(2));
        datiSessione.addColumn("TECNI.CFTEC", datiTecnico.get(3));
        datiSessione.addColumn("TECNI.INDTEC", datiTecnico.get(4));
        datiSessione.addColumn("TECNI.NCITEC", datiTecnico.get(5));
        datiSessione.addColumn("TECNI.PROTEC", datiTecnico.get(6));
        datiSessione.addColumn("TECNI.CAPTEC", datiTecnico.get(7));
        datiSessione.addColumn("TECNI.LOCTEC", datiTecnico.get(8));
        datiSessione.addColumn("TECNI.CITTEC", datiTecnico.get(9));
        datiSessione.addColumn("TECNI.TELTEC", datiTecnico.get(10));
        datiSessione.addColumn("TECNI.FAXTEC", datiTecnico.get(11));
        datiSessione.addColumn("TECNI.EMATEC", datiTecnico.get(12));
      }

    } catch (GestoreException e) {
      return GestoreEccezioni.gestisciEccezioneAction(e, this.getAction(),
          request, logger, mapping);
    } catch (SQLException e) {
      return GestoreEccezioni.gestisciEccezioneAction(e, this.getAction(),
          request, logger, mapping);
    }

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
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        request.getSession().getServletContext(), SqlManager.class);

    DataColumnContainer datiSessione = (DataColumnContainer) request.getSession().getAttribute(
        CostantiWizard.NOME_OGGETTO_WIZARD_SESSIONE);

    // ne aggiorno i dati letti dal request (ovvero la SA selezionata)
    DataColumn[] datiRequest = UtilityStruts.getDatiRequest(request);
    datiSessione.addColumns(datiRequest, true);

    try {
      String[][] utenti = GestoreSelezioneAccountAltraSA.getElencoUtentiSA(
          sqlManager, datiSessione);
      request.setAttribute("utenti", utenti);
    } catch (Exception e) {
      return GestoreEccezioni.gestisciEccezioneAction(e, this.getAction(),
          request, logger, mapping);
    }

    return null;
  }

}
