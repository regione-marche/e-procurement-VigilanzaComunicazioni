package it.eldasoft.sil.w3.tags.funzioni;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestioneW3AZIENDAUFFICIOFunction extends AbstractFunzioneTag {

  public GestioneW3AZIENDAUFFICIOFunction() {
    super(1, new Class[] { String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {

    if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(UtilityTags.getParametro(
        pageContext, UtilityTags.DEFAULT_HIDDEN_PARAMETRO_MODO))) {

    	String[] parametri = ((String) params[0]).split(";");
    	
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);

      Long syscon = new Long(parametri[0]);
      String rup_codtec = parametri[1];
      try {
        List<?> datiW3AZIENDAUFFICIO = sqlManager.getListVector(
            "select w3aziendaufficio.id, "
                + "w3aziendaufficio.ufficio_denom, "
                + "w3aziendaufficio.ufficio_id, "
                + "w3aziendaufficio.azienda_cf ,"
                + "w3aziendaufficio.azienda_denom, "
                + "w3aziendaufficio.indexcoll "
                + "from w3aziendaufficio, w3usrsyscoll "
                + "where w3aziendaufficio.id = w3usrsyscoll.w3aziendaufficio_id and w3usrsyscoll.syscon = ? and w3usrsyscoll.rup_codtec = ? ",
            new Object[] { syscon, rup_codtec });

        if (datiW3AZIENDAUFFICIO != null && datiW3AZIENDAUFFICIO.size() > 0) {
          pageContext.setAttribute("datiW3AZIENDAUFFICIO",
              datiW3AZIENDAUFFICIO, PageContext.REQUEST_SCOPE);
        }

      } catch (SQLException e) {
        throw new JspException(
            "Errore nella lettura della lista delle collaborazioni", e);
      }

    }
    return null;

  }

}
