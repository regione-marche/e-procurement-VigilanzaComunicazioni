package it.eldasoft.w9.tags.gestori.plugin;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.decorators.wizard.CostantiWizard;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Plugin per il popolamento dell'elenco delle S.A.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class GestoreSelezioneAccountAltraSA extends AbstractGestorePreload {

  public GestoreSelezioneAccountAltraSA(BodyTagSupportGene tag) {
    super(tag);
  }

  @Override
  public void doAfterFetch(PageContext page, String modoAperturaScheda)
      throws JspException {
    DataColumnContainer datiSessione = (DataColumnContainer) page.getAttribute(
        CostantiWizard.NOME_OGGETTO_WIZARD_SESSIONE, PageContext.SESSION_SCOPE);
    
    try {
      if (datiSessione.getString("SA_IMPORT") != null) {
        SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
            page, SqlManager.class);
        try {
          String[][] utenti = getElencoUtentiSA(sqlManager, datiSessione);
          page.setAttribute("utenti", utenti);
        } catch (Exception e) {
          throw new JspException(
              "Errore nell'esecuzione della query per l'estrazione dei dati dell'elenco degli account associati ad una stazione appaltante",
              e);
        }
      }
    } catch (GestoreException e) {
      // questo errore non si verifichera' mai
    }

  }

  public static String[][] getElencoUtentiSA(SqlManager sqlManager,
      DataColumnContainer datiSessione) throws SQLException, GestoreException,
      CriptazioneException {
    List<Vector<JdbcParametro>> elenco = sqlManager.getListVector(
        "select u.syscon, u.sysute, u.sysnom "
            + "from usr_ein ue, usrsys u, tecni t "
            + "where ue.codein = ? "
            + "and ue.syscon = u.syscon "
            + "and u.syscon = t.syscon "
            + "and t.cgentei = ue.codein "
            + "order by sysnom",
        new Object[] { datiSessione.getString("SA_IMPORT") });
    String[][] utenti = null;
    if (elenco != null) {
      utenti = new String[elenco.size()][3];
      for (int i = 0; i < elenco.size(); i++) {
            utenti[i][0] = elenco.get(i).get(0).toString();
            utenti[i][1] = elenco.get(i).get(1).stringValue();
            utenti[i][2] = new String(FactoryCriptazioneByte.getInstance(
                FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY,
                elenco.get(i).get(2).stringValue().getBytes(),
                ICriptazioneByte.FORMATO_DATO_CIFRATO).getDatoNonCifrato());
      }
    }
    return utenti;
  }

  @Override
  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda)
      throws JspException {
  }

}
