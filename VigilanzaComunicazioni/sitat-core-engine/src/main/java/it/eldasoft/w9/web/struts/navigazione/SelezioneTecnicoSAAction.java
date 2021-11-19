package it.eldasoft.w9.web.struts.navigazione;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.decorators.wizard.CostantiWizard;
import it.eldasoft.gene.web.struts.tags.AbstractCustomWizardAction;
import it.eldasoft.gene.web.struts.tags.GestoreEccezioni;
import it.eldasoft.gene.web.struts.tags.WizardAction;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

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
 * Classe per la gestione della navigazione nella pagina di selezione del
 * tecnico della stazione appaltante da utilizzare per creare un nuovo utente.
 * 
 * @author Stefano.Sabbadin
 */
public class SelezioneTecnicoSAAction extends AbstractCustomWizardAction {

  /**
   * Costruttore.
   * 
   * @param action WizardAction
   */
  public SelezioneTecnicoSAAction(WizardAction action) {
    super(action);
  }

  /** Logger della classe. */
  private static Logger      logger             = Logger.getLogger(SelezioneTecnicoSAAction.class);


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
   * Gestisce l'inserimento di un nuovo utente memorizzando in sessione i
   * dati del tecnico selezionato.
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
      String codtec = datiSessione.getString("TECNI.CODTEC");

      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          request.getSession().getServletContext(), SqlManager.class);

      Vector<JdbcParametro> datiTecnico = sqlManager.getVector(
          "select COGTEI, NOMETEI, NOMTEC, CFTEC, INDTEC, NCITEC, "
          + "PROTEC, CAPTEC, LOCTEC, CITTEC, TELTEC, FAXTEC, "
          + "EMATEC, CGENTEI from TECNI where CODTEC = ?",
          new Object[]{codtec});

      if (datiTecnico != null) {
        int indiceTecnicoVector = 0;
        datiSessione.addColumn("TECNI.COGTEI", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=1
        datiSessione.addColumn("TECNI.NOMETEI", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=2
        datiSessione.addColumn("TECNI.NOMTEC", datiTecnico.get(indiceTecnicoVector));
        datiSessione.addColumn("USRSYS.SYSUTE", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=3
        datiSessione.addColumn("TECNI.CFTEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=4
        datiSessione.addColumn("TECNI.INDTEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=5
        datiSessione.addColumn("TECNI.NCITEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=6
        datiSessione.addColumn("TECNI.PROTEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=7
        datiSessione.addColumn("TECNI.CAPTEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=8
        datiSessione.addColumn("TECNI.LOCTEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=9
        datiSessione.addColumn("TECNI.CITTEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=10
        datiSessione.addColumn("TECNI.TELTEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=11
        datiSessione.addColumn("TECNI.FAXTEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=12
        datiSessione.addColumn("TECNI.EMATEC", datiTecnico.get(indiceTecnicoVector));
        indiceTecnicoVector++; // indiceTecnicoVector=13
        datiSessione.addColumn("TECNI.CGENTEI", datiTecnico.get(indiceTecnicoVector));
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
    return null;
  }

}
