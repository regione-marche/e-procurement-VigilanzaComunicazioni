package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetContatoriInboxFunction extends AbstractFunzioneTag {

  public GetContatoriInboxFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    try {
    	 String sqlFlussiErrore = "select count(*) from V_W9INBOX where STACOM=3";
    	 String sqlFlussiWarning = "select count(*) from V_W9INBOX where STACOM=4 AND IDCOMUN NOT IN(SELECT IDCOMUN FROM W9FLUSSI WHERE TINVIO2=-1) AND IDCOMUN NOT IN(SELECT IDCOMUN FROM W9FLUSSI_ELIMINATI WHERE TINVIO2=-1)";
    	 String sqlFlussiCancellazioniNonEvase = "select count(*) from V_W9INBOX where STACOM=4 AND (IDCOMUN IN(SELECT IDCOMUN FROM W9FLUSSI WHERE TINVIO2=-1) OR IDCOMUN IN(SELECT IDCOMUN FROM W9FLUSSI_ELIMINATI WHERE TINVIO2=-1))";
    	 String sqlFeedbackNonAnalizzati = "select count(*) from W9XML where FEEDBACK_ANALISI = '2'";
    	 String sqlComunicazioniSCPErrore = "select count(*) from W9OUTBOX where STATO=3";
    	 
    	 Long numeroErrori = (Long) sqlManager.getObject(sqlFlussiErrore, new Object[]{ });
    	 Long numeroWarnings = (Long) sqlManager.getObject(sqlFlussiWarning, new Object[]{ });
    	 Long numeroCancellazioniNonEvase = (Long) sqlManager.getObject(sqlFlussiCancellazioniNonEvase, new Object[]{ });
    	 Long numeroFeedbackNonAnalizzati = (Long) sqlManager.getObject(sqlFeedbackNonAnalizzati, new Object[]{ });
    	 Long numeroComunicazioniSCPErrori = (Long) sqlManager.getObject(sqlComunicazioniSCPErrore, new Object[]{ });
    	 
       pageContext.setAttribute("numeroErrori", numeroErrori, PageContext.REQUEST_SCOPE);
       pageContext.setAttribute("numeroWarnings", numeroWarnings, PageContext.REQUEST_SCOPE);
       pageContext.setAttribute("numeroCancellazioniNonEvase", numeroCancellazioniNonEvase, PageContext.REQUEST_SCOPE);
       pageContext.setAttribute("numeroFeedbackNonAnalizzati", numeroFeedbackNonAnalizzati, PageContext.REQUEST_SCOPE);
       pageContext.setAttribute("numeroComunicazioniSCPErrore", numeroComunicazioniSCPErrori, PageContext.REQUEST_SCOPE);
         
    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrazione dei dati per la popolazione della/e dropdown list",
          e);
    }
    return null;
  }

}
