package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.spring.UtilitySpring;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

//import it.eldasoft.utils.utility.UtilityStringhe;
//import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
//import java.sql.SQLException;
//import org.apache.commons.lang.StringUtils;

public class GetBaseCuiImmobileFunction extends AbstractFunzioneTag {

    public GetBaseCuiImmobileFunction() {
        super(3, new Class[] { PageContext.class, String.class, String.class });
    }

    @Override
    public String function(PageContext pageContext, Object[] params)
            throws JspException {

        SqlManager sqlManager = (SqlManager) UtilitySpring.getBean(
                "sqlManager", pageContext, SqlManager.class);

        if ((params[1] != null && !"".equals(params[1]))
                && (params[2] != null && !"".equals(params[2]))) {

            Long contri = new Long((String) params[1]);
            String anno = "";
            String cfamm = "";
            String progr = "";
            
            try {
                String codein = (String) pageContext.getSession().getAttribute(
                        CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);

                
                PtManager  ptManager = (PtManager) UtilitySpring.getBean("ptManager",pageContext, PtManager.class);
                ////////////////////////////////////
                //Nuova gestione Calcolo CUIIM (febbraio 2019)
                String maxCUIIM = ptManager.calcolaCUIImmobile(contri, codein);
                maxCUIIM = ptManager.incrementaCUIImmobile(maxCUIIM);
                ////////////////////////////////////
                
                cfamm = maxCUIIM.substring(1, 12);
                anno = maxCUIIM.substring(12, 16);
                progr = maxCUIIM.substring(16, 21);

                pageContext.setAttribute("cfamm", cfamm, PageContext.REQUEST_SCOPE);
                pageContext.setAttribute("anno", anno, PageContext.REQUEST_SCOPE);
                pageContext.setAttribute("progr", progr, PageContext.REQUEST_SCOPE);

            } catch (Exception e) {
                throw new JspException(
                        "Errore nella determinazione della base per il cui dell'immobile",
                        e);
            }
        }

        return null;
    }
}
