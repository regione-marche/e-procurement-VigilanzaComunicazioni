package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luca.Giacomazzo - Eldasoft S.p.A. Treviso
 *
 */
public class RedirectCUPAction extends ActionBaseNoOpzioni {

	/** manager per operazioni di interrogazione del db. */
  private SqlManager            sqlManager;

  /** FORWARD_NUOVO_CUP forward per nuovo cup. */
  protected static final String FORWARD_NUOVO_CUP      = "nuovoCUP";
  /** FORWARD_VISUALIZZA_CUP forward per la visualizzazione del cup. */
  protected static final String FORWARD_VISUALIZZA_CUP = "visualizzaCUP";
  /** Logger Log4J di classe. */
  private static Logger                 logger                 = Logger.getLogger(RedirectCUPAction.class);

  /**
   * @param sqlManager
   *        manager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String messageKey = null;
    String target = null;
    Long contri = new Long(request.getParameter("contri"));
    Long conint = new Long(request.getParameter("conint"));

    // Verifico esistenza occorrenza di CUPDATI
    String selectCUPDATI = "select id from cupdati where contri = ? and conint = ?";
    try {

      Long id = (Long) this.sqlManager.getObject(selectCUPDATI, new Object[] {
          contri, conint });

      if (id != null) {
        
        String key = "CUPDATI.ID=N:" + id.toString();
        request.setAttribute("key", key);
        target = FORWARD_VISUALIZZA_CUP;
        
      } else {
        
        request.setAttribute("contri", contri);
        request.setAttribute("conint", conint);

        // Inizializzazioni
        String selectPIATRI_INTTRI = "select inttri.desint, piatri.anntri, inttri.sezint, inttri.interv ,"
            + "inttri.catint, inttri.cupmst, inttri.totint, inttri.codcpv, piatri.tiprog "
            + "from piatri, inttri where "
            + "piatri.contri = inttri.contri and inttri.contri = ? and inttri.conint = ?";
        List<?> datiPIATRI_INTTRI = this.sqlManager.getVector(
            selectPIATRI_INTTRI, new Object[] { contri, conint });
        if (datiPIATRI_INTTRI != null && datiPIATRI_INTTRI.size() > 0) {

          // Descrizione
          String desest = (String) SqlManager.getValueFromVectorParam(
              datiPIATRI_INTTRI, 0).getValue();
          request.setAttribute("descrizione_intervento", desest);

          // Anno della decisione
          Long anntri = (Long) SqlManager.getValueFromVectorParam(
              datiPIATRI_INTTRI, 1).getValue();
          request.setAttribute("anno_decisione", anntri);

          
          Long tiprog =  (Long) SqlManager.getValueFromVectorParam(
              datiPIATRI_INTTRI, 8).getValue();
          
          if (tiprog != null && new Long(1).equals(tiprog)) {
            // Natura
            request.setAttribute("natura", "03");
          
            // Tipologia
            String sezint = (String) SqlManager.getValueFromVectorParam(
                datiPIATRI_INTTRI, 2).getValue();
            request.setAttribute("tipologia", sezint);

            // Settore
            String interv = (String) SqlManager.getValueFromVectorParam(
                datiPIATRI_INTTRI, 3).getValue();
            if (interv != null && interv.length() > 2) {
              request.setAttribute("settore", interv.substring(1));
            }
  
            // Sotto settore
            String catint = (String) SqlManager.getValueFromVectorParam(
                datiPIATRI_INTTRI, 4).getValue();
            request.setAttribute("sottosettore", catint);
            
          }

          // CUP Master
          String cupmst = (String) SqlManager.getValueFromVectorParam(
              datiPIATRI_INTTRI, 5).getValue();
          request.setAttribute("cup_master", cupmst);

          // Costo e Finanziamento
          Double totint = (Double) SqlManager.getValueFromVectorParam(
              datiPIATRI_INTTRI, 6).getValue();
          request.setAttribute("costo", totint);
          request.setAttribute("finanziamento", totint);

          // Codice CPV
          String codcpv = (String) SqlManager.getValueFromVectorParam(
              datiPIATRI_INTTRI, 7).getValue();
          request.setAttribute("cpv", codcpv);
          
        }

        target = FORWARD_NUOVO_CUP;
      }

    } catch (SQLException e) {
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
      messageKey = "errors.applicazione.inaspettataException";
      logger.error(this.resBundleGenerale.getString(messageKey), e);
      this.aggiungiMessaggio(request, messageKey);
    }

    return mapping.findForward(target);
  }

}
