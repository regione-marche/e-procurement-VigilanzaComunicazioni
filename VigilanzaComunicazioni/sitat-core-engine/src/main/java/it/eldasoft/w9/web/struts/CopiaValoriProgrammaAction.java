package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityStringhe;

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
 * Action per la copia dei valori di un programma triennale.
 * 
 * @author Luca.Giacomazzo
 */
public class CopiaValoriProgrammaAction extends ActionBaseNoOpzioni {

  /**
   * Logger della classe.
   */
  static Logger               logger               = Logger.getLogger(CopiaValoriProgrammaAction.class);

  /**
   * Manager delle transazioni SQL.
   */
  private SqlManager          sqlManager;

  /**
   * Query per l'estrazione dei dati degli interventi.
   */
  private static final String SQL_SELECT_PROGRAMMA = "select INTTRI.CONINT, PIATRI.TIPROG, PIATRI.ANNTRI, "
    + "INTTRI.ANNRIF, INTTRI.DESINT, "
    + "INTTRI.TOTINT, INTTRI.CODCPV, INTTRI.MANTRI, "
    + "INTTRI.COMINT, INTTRI.NUTS, PIATRI.ID, INTTRI.CONINT, INTTRI.TIPOIN "
    + "from PIATRI, INTTRI where INTTRI.CONTRI=PIATRI.CONTRI and PIATRI.CONTRI = ? "
    + "and INTTRI.CONINT = ? and INTTRI.CONTRI IN (SELECT CONTRI FROM "
    + "PIATRI WHERE PIATRI.CENINT = ?)";

  /**
   * Query per l'aggiornamento di W9LOTT.
   */
  private static final String SQL_UPDATE_LOTTO     = "update W9LOTT set OGGETTO =?, IMPORTO_LOTTO =?, CPV =?, MANOD =?, CODINT =?, "
    + "LUOGO_ISTAT =?, LUOGO_NUTS =?, IMPORTO_TOT =?, TIPO_CONTRATTO =? where CODGARA =? AND CODLOTT =?";

  /**
   * Query per estrarre l'importo attuazione sicurezza dal lotto.
   */
  private static final String SQL_SELECT_IMP_SIC   = "select IMPORTO_ATTUAZIONE_SICUREZZA from W9LOTT where CODGARA =? AND CODLOTT =?";

  /**
   * Set sqlManager.
   * 
   * @param sqlManager the sqlManager to set
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String target = CostantiGeneraliStruts.FORWARD_OK;
    DataColumnContainer containerKeyProgr;
    DataColumnContainer containerKeyLott;

    try {
      String keyLott = request.getParameter("keyLott");
      String keyProgr = request.getParameter("keyProgr");
      String cenint = request.getParameter("cenint");
      containerKeyProgr = new DataColumnContainer(keyProgr);
      containerKeyLott = new DataColumnContainer(keyLott);
      
      Long contri = (containerKeyProgr.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
      Long conint = (containerKeyProgr.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();

      List< ? > programmaSelezionatoList = sqlManager.getListVector(
          SQL_SELECT_PROGRAMMA, new Object[] { contri, conint, cenint });
      List< ? > programmaSelezionato = null;

      String oggetto = "";
      Double importoLotto = new Double(0);
      String cpv = "";
      String manod = "";
      String codint = "";
      String luogoIstat = "";
      String luogoNuts = "";
      String tipoIn = "";
      Long codGara = new Long((containerKeyLott.getColumnsBySuffix("CODGARA", true))[0].getValue().toString());
      Long codLott = new Long((containerKeyLott.getColumnsBySuffix("CODLOTT", true))[0].getValue().toString());
      Object importoSicurezzaObj = sqlManager.getObject(SQL_SELECT_IMP_SIC, new Object[] { codGara, codLott });
      Double importoSicurezza = new Double(0);
      Double importoTotale = new Double(0);
      if (importoSicurezzaObj != null) {
        importoSicurezza = new Double(importoSicurezzaObj.toString());
      }
      if (programmaSelezionatoList != null && programmaSelezionatoList.size() > 0) {
        programmaSelezionato = (List< ? >) programmaSelezionatoList.get(0);
        if (programmaSelezionato.get(4) != null
            && !programmaSelezionato.get(4).toString().equals("")) {
          oggetto = programmaSelezionato.get(4).toString();
        }
        if (programmaSelezionato.get(5) != null
            && !programmaSelezionato.get(5).toString().equals("")) {
          importoLotto = new Double(programmaSelezionato.get(5).toString());
        }
        importoTotale = importoLotto + importoSicurezza;
        if (programmaSelezionato.get(6) != null
            && !programmaSelezionato.get(6).toString().equals("")) {
          cpv = programmaSelezionato.get(6).toString();
        }
        if (programmaSelezionato.get(7) != null && !programmaSelezionato.get(7).toString().equals("")) {
          manod = programmaSelezionato.get(7).toString();
        }
        if (programmaSelezionato.get(10) != null
            && !programmaSelezionato.get(10).toString().equals("")
            && programmaSelezionato.get(11) != null
            && !programmaSelezionato.get(11).toString().equals("")) {
          codint = programmaSelezionato.get(10).toString()
            + UtilityStringhe.fillLeft(programmaSelezionato.get(11).toString(), '0', 3);
        }
        if (programmaSelezionato.get(1) != null
            && programmaSelezionato.get(1).toString().equals("1")) {
          if (programmaSelezionato.get(8) != null
              && !programmaSelezionato.get(8).toString().equals("")) {
            luogoIstat = programmaSelezionato.get(8).toString();
          }
          if (programmaSelezionato.get(9) != null
              && !programmaSelezionato.get(9).toString().equals("")) {
            luogoNuts = programmaSelezionato.get(9).toString();
          }
        }
        if (programmaSelezionato.get(12) != null
            && !programmaSelezionato.get(12).toString().equals("")) {
          tipoIn = programmaSelezionato.get(12).toString();
        }
        TransactionStatus status = this.sqlManager.startTransaction();
        this.sqlManager.update(SQL_UPDATE_LOTTO, new Object[] { oggetto,
            importoLotto, cpv, manod, codint, luogoIstat, luogoNuts,
            importoTotale, tipoIn, codGara, codLott });
        this.sqlManager.commitTransaction(status);
        request.setAttribute("esitoInserimento", "ok");
      }
    } catch (SQLException e) {
      logger.error("Problema nel recupero dei dati del Programma.", e);
      aggiungiMessaggio(request, "errors.copiaProgramma.inLotto");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    } catch (GestoreException g) {
      logger.error("Problema nel recupero dei dati del Programma.", g);
      aggiungiMessaggio(request, "errors.copiaProgramma.inLotto");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }
    return mapping.findForward(target);
  }

}
