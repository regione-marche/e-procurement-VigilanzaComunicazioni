package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class VerificaPresenzaProgrammazione extends AbstractFunzioneTag {

  public VerificaPresenzaProgrammazione() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    String codice = params[1].toString();
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    if (codice == null || codice.equals("")) {
      codice = UtilityTags.getParametro(pageContext,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    boolean checkProgrammazione = false;;
    try {
      DataColumnContainer key = new DataColumnContainer(codice);
      //Long contri = new Long(0);
      Long conint = (key.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);
      String selectCount = "SELECT COUNT(CONTRI) from PIATRI " +
      		"where exists(select 1 from INTTRI,PTAPPROVAZIONI where piatri.CONTRI=PTAPPROVAZIONI.ID_PROGRAMMA " +
      		"AND PTAPPROVAZIONI.ID_PROGRAMMA = INTTRI.CONTRI and PTAPPROVAZIONI.ID_INTERV_PROGRAMMA = INTTRI.CONINT " +
      		"AND PTAPPROVAZIONI.CONINT = ?)";
      Long occorrenze = (Long) sqlManager.getObject(selectCount, new Object[] { conint });
      checkProgrammazione = (occorrenze > 0);
    } catch (SQLException e) {
      throw new JspException("Errore nella verifica presenza programmazione", e);
    } catch (GestoreException e) {
      throw new JspException("Errore nella verifica presenza programmazione", e);
    }
    return String.valueOf(checkProgrammazione);
  }

}
