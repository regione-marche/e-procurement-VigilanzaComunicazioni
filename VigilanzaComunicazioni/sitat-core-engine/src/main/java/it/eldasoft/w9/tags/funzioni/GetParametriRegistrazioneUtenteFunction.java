package it.eldasoft.w9.tags.funzioni;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiSitatSA;

public class GetParametriRegistrazioneUtenteFunction extends AbstractFunzioneTag {

  public GetParametriRegistrazioneUtenteFunction() {
    super(1, new Class[] {PageContext.class });

  }

  public final String function(final PageContext pageContext, final Object[] params) throws JspException {

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

    String indirizzoFileRelativo = ConfigManager.getValore(CostantiSitatSA.REGISTRAZIONE_FILE_CONDIZIONI_USO);
    if (indirizzoFileRelativo != null && !"".equals(indirizzoFileRelativo.trim())) {
      String tomcatHome = pageContext.getServletContext().getRealPath(File.separator);
      indirizzoFileRelativo = indirizzoFileRelativo.trim();
      File modulo = new File(tomcatHome + indirizzoFileRelativo);
      if (modulo != null && modulo.length() > 0) {
        pageContext.setAttribute("moduloCondizioniDuso", indirizzoFileRelativo, PageContext.REQUEST_SCOPE);
      }
    }

    try {
      String profiliDisponibili = ConfigManager.getValore(CostantiSitatSA.REGISTRAZIONE_PROFILI_DISPONIBILI);
      if (profiliDisponibili != null && !"".equals(profiliDisponibili.trim())) {
        String[] arrayProfiliDisponibili = profiliDisponibili.split(";");
        if (arrayProfiliDisponibili != null && arrayProfiliDisponibili.length > 0) {
          List<Object> listaProfiliDisponibili = new Vector<Object>();
          for (int i = 0; i < arrayProfiliDisponibili.length; i++) {
            String cod_profilo = arrayProfiliDisponibili[i];
            List<?> datiW_PROFILI = sqlManager.getVector("select nome, descrizione from w_profili where cod_profilo = ? and codapp = ?",
                new Object[] {cod_profilo, ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE) });
            if (datiW_PROFILI != null && datiW_PROFILI.size() > 0) {
              listaProfiliDisponibili.add(new Object[] {cod_profilo, datiW_PROFILI.get(0), datiW_PROFILI.get(1) });
            }
          }
          pageContext.setAttribute("listaProfiliDisponibili", listaProfiliDisponibili, PageContext.REQUEST_SCOPE);
        }
      }
    } catch (SQLException e) {
      throw new JspException("Errore nella lettura della lista dei profili disponibili", e);
    }
    
    try {
      String consentiCreazioneNuovaSA = (String) sqlManager.getObject(
          "select VALORE from W_CONFIG where CODAPP=? and CHIAVE=?",
          new Object[] { 
              ConfigManager.getValore(CostantiGenerali.PROP_CODICE_APPLICAZIONE), 
              "registrazione.consentiCreazioneNuovaSA" });

      if (StringUtils.isNotEmpty(consentiCreazioneNuovaSA)) {
        pageContext.setAttribute("consentiCreazioneNuovaSA", consentiCreazioneNuovaSA, PageContext.REQUEST_SCOPE);
      }
      
    } catch (SQLException sqle) {
      throw new JspException("Errore nella lettura del parametro per disabilitare la creazione di una nuova " +
      		"stazione appaltante al momento della registrazione", sqle);
    }
    
    return null;
  }
}
