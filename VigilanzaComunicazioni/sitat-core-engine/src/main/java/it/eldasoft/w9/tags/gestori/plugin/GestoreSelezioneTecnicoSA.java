package it.eldasoft.w9.tags.gestori.plugin;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.decorators.wizard.CostantiWizard;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Plugin per il popolamento dell'elenco tecnici collegati ad una S.A.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class GestoreSelezioneTecnicoSA extends AbstractGestorePreload {

  public GestoreSelezioneTecnicoSA(BodyTagSupportGene tag) {
    super(tag);
  }

  @Override
  public void doAfterFetch(PageContext page, String modoAperturaScheda)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        page, SqlManager.class);
    DataColumnContainer datiInserimento = (DataColumnContainer) page.getAttribute(
        CostantiWizard.NOME_OGGETTO_WIZARD_SESSIONE, PageContext.SESSION_SCOPE);

    String[][] tecnici = null;

    try {
      List<Vector<JdbcParametro>> elenco = sqlManager.getListVector(
          "select codtec, nomtec, cftec from tecni where cgentei = ? and syscon is null order by nomtec",
          new Object[] { datiInserimento.getString("USR_EIN.CODEIN") });
      if (elenco != null) {
        tecnici = new String[elenco.size()][3];
        for (int i = 0; i < elenco.size(); i++) {
          for (int j = 0; j < elenco.get(i).size(); j++)
            tecnici[i][j] = elenco.get(i).get(j).stringValue();
        }
      }
    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'esecuzione della query per l'estrazione dei dati dell'elenco dei tecnici per la stazione appaltante",
          e);

    } catch (Exception e) {
      throw new JspException(
          "Errore inaspettato durante l'estrazione dei dati dell'elenco dei tecnici per la stazione appaltante",
          e);
    }
    page.setAttribute("tecnici", tecnici);
  }

  @Override
  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda)
      throws JspException {
  }

}
