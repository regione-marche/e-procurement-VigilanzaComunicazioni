package it.eldasoft.sil.pt.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

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
import org.springframework.transaction.TransactionStatus;

/**
 * @author Luca.Giacomazzo - Eldasoft S.p.A. Treviso
 *
 */
public class IncollaInterventoAction extends ActionBaseNoOpzioni {

	/** manager per operazioni di interrogazione del db. */
  private SqlManager          sqlManager;
  /** Logger Log4J di classe. */
  private static Logger               logger            = Logger.getLogger(IncollaInterventoAction.class);

  /** query per interrogazione interventi. */
  private static final String SQL_SELECT_INT    = "select * from INTTRI where CONTRI = ? and CONINT = ?";
  /** query per interrogazione beni immobili. */
  private static final String SQL_SELECT_IMM    = "select * from IMMTRAI where CONTRI = ? and CONINT = ? and NUMIMM = ?";
  /** query per interrogazione beni immobili. */
  private static final String SQL_SELECT_NUMIMM = "select NUMIMM from IMMTRAI where CONTRI = ? and CONINT = ?";
  /** query per interrogazione del numero di interventi in un programma. */
  private static final String SQL_MAX_CONINT    = "select MAX(CONINT) from INTTRI where CONTRI = ?";
  //private static final String SQL_CTRL_EXIST 	= "select COUNT(CONINT) from INTTRI where CONTRI = ? and CONINT = ?"; 

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

    String target = CostantiGeneraliStruts.FORWARD_OK;
    boolean commitTransazione = false;
    TransactionStatus status = null;
    try {
      // recupero del programma di destinazione
      String chiaveDestinazione = (String) request.getParameter("keyatt");
      DataColumnContainer keyAtt = new DataColumnContainer(chiaveDestinazione);
      Long contriDestinazione = (keyAtt.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();

      // recupero del programma di origine degli interventi da incollare
      String chiave = (String) request.getSession().getAttribute(
          "interventoCopiato");
      String[] chiavi = new String[] {};

      if (chiave != null && !chiave.trim().equals("")) {
        if (chiave.indexOf(";;") != -1) {
          chiavi = chiave.split(";;");
        } else {
          chiavi = new String[] { chiave };
        }
      }

      status = sqlManager.startTransaction();
      
      for (int i = 0; i < chiavi.length; i++) {
        DataColumnContainer key = new DataColumnContainer(chiavi[i]);
        Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
        Long conint = (key.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();

        DataColumnContainer impl = null;
        DataColumnContainer implImmtrai = null;

        Long conintDestinazione = (Long) sqlManager.getObject(SQL_MAX_CONINT,
            new Object[] { contriDestinazione });

        if (conintDestinazione == null) {
          conintDestinazione = new Long(1);
        } else {
          conintDestinazione = conintDestinazione + 1;
        }

        impl = new DataColumnContainer(sqlManager, "INTTRI", SQL_SELECT_INT,
            new Object[] { contri, conint });
        impl.setValue("CONTRI", contriDestinazione);
        impl.setValue("CONINT", conintDestinazione);

        request.getSession().setAttribute("chiaveForward",
            "INTTRI.CONTRI=N:" + contriDestinazione);

        AbstractGestoreEntita gestoreIntri = new DefaultGestoreEntita("INTTRI",
            request);
        gestoreIntri.inserisci(status, impl);

        Long numimm = null;
        List immobiliList = sqlManager.getListVector(SQL_SELECT_NUMIMM,
            new Object[] { contri, conint });
        if (immobiliList != null && immobiliList.size() > 0) {
          for (int j = 0; j < immobiliList.size(); j++) {
            numimm = new Long(((List) immobiliList.get(j)).get(0).toString());
            implImmtrai = new DataColumnContainer(sqlManager, "IMMTRAI",
                SQL_SELECT_IMM, new Object[] { contri, conint, numimm });
            implImmtrai.setValue("CONTRI", contriDestinazione);
            implImmtrai.setValue("CONINT", conintDestinazione);
            implImmtrai.setValue("NUMIMM", numimm);
            AbstractGestoreEntita gestoreImmtrai = new DefaultGestoreEntita(
                "IMMTRAI", request);
            gestoreImmtrai.inserisci(status, implImmtrai);
          }
        }

        request.getSession().setAttribute("contriDestinazione",
            contriDestinazione);
      }

      commitTransazione = true;
    } catch (SQLException e) {
      logger.error(
          "Errore nel recupero dei dati intervento/i da incollare.",
          e);
      aggiungiMessaggio(request, "errors.incollaIntervento.recuperoDati");
      target = "failed"; //CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    } catch (GestoreException e) {
      logger.error("Errore nell\'inserimento dei dati Intervento nel db.", e);
      aggiungiMessaggio(request, "errors.incollaIntervento.inserimentoDati");
      target = "failed"; //CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    } finally {
      if (status != null) {
        try {
          if (commitTransazione) {
            sqlManager.commitTransaction(status);
          } else {
            sqlManager.rollbackTransaction(status);
          }
        } catch (SQLException e) {
          logger.error("Errore durante la chiusura della transazione", e);
        }
      }
    }

    return mapping.findForward(target);
  }

}
