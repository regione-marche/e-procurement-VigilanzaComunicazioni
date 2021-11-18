package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetNotificheAvvisiFunction extends AbstractFunzioneTag {

  public GetNotificheAvvisiFunction() {
    super(1, new Class[] { String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    try {
    	 ProfiloUtente profilo = (ProfiloUtente) pageContext.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    	 boolean isUtenteAmministratore = "A".equals(profilo.getAbilitazioneStd()) || "C".equals(profilo.getAbilitazioneStd());
    	 String codein = (String) pageContext.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
         // Ricavo la lista degli avvisi
    	 String sqlNoteAvvisi = null;
    	 List<?> listaAvvisi = null;
    	 if (isUtenteAmministratore) {
    		 sqlNoteAvvisi = 
    		   "select g.notecod, g.datanota, g.titolonota, g.testonota, g.notekey1, g.notekey2, w.PROV_DATO " +
    		 		" from G_NOTEAVVISI g, W9GARA w " +
    		 	 " where g.statonota <> 90 " + 
             " and g.notekey1 = " + sqlManager.getDBFunction("inttostr",new String[] {"w.CODGARA"}) +
             " and w.codein = ? ";
    		       //" and g.notekey1 in (select " + sqlManager.getDBFunction("inttostr",new String[] {"codgara"}) + " from W9GARA where codein = ?)";
    		 listaAvvisi = sqlManager.getListVector(sqlNoteAvvisi, new Object[] { codein });
    		 
    	 } else {
    		 sqlNoteAvvisi = 
    		   "select g.notecod, g.datanota, g.titolonota, g.testonota, g.notekey1, g.notekey2, w.PROV_DATO " +
    		    " from G_NOTEAVVISI g, W9GARA w " +
    		   " where g.statonota <> 90 " +
    		     " and g.notekey1 = " + sqlManager.getDBFunction("inttostr",new String[] {"w.CODGARA"}) +
    		     " and w.codein = ? " +
    		     " and w.CODGARA in (select p.CODGARA from V_W9PERMESSI p, V_RUOLOTECNICO r where p.CODGARA=r.CODGARA and p.PERMESSO > 1 and p.RUOLO=r.ID_RUOLO and r.SYSCON = ?) ";
    		     //" and g.notekey1 in (select " + sqlManager.getDBFunction("inttostr",new String[] {"codgara"}) + " from W9GARA where codein = ? and codgara in " +
             //" (select p.CODGARA from V_W9PERMESSI p, V_RUOLOTECNICO r where p.CODGARA=r.CODGARA and p.PERMESSO > 1 and p.RUOLO=r.ID_RUOLO and r.SYSCON = ?))";
    		 listaAvvisi = sqlManager.getListVector(sqlNoteAvvisi, new Object[] { codein, profilo.getId() });
    	 }
       pageContext.setAttribute("listaAvvisi", listaAvvisi, PageContext.REQUEST_SCOPE);

    } catch (SQLException e) {
      throw new JspException(
          "Errore nell'estrazione dei dati per la popolazione della/e dropdown list",
          e);
    }
    return null;
  }

}
