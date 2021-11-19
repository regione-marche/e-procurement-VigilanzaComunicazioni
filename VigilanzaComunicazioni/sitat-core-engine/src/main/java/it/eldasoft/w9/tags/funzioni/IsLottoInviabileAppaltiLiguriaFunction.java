package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.utils.UtilitySITAT;

public class IsLottoInviabileAppaltiLiguriaFunction extends AbstractFunzioneTag {

  public IsLottoInviabileAppaltiLiguriaFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    String result = "false";
  
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
  
    String key = params[1].toString();
    if (key == null || key.equals("")) {
      key = UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    }
    
    DataColumnContainer keys = new DataColumnContainer(key);
    try {
      Long codiceGara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      Long codiceLotto = (keys.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      
      boolean isS2 = UtilitySITAT.isS2(codiceGara, codiceLotto, sqlManager);
      boolean isL = StringUtils.equals("L", UtilitySITAT.getTipoContrattoLotto(codiceGara, codiceLotto, sqlManager));
      boolean isAAQ = UtilitySITAT.isAAQ(codiceGara, sqlManager);
      boolean isSAQ = UtilitySITAT.isSAQ(codiceGara, sqlManager);
      
      if (isS2 && isL && !isAAQ && !isSAQ) {
        result = "true";
      }
    } catch (NumberFormatException e) {
      throw new JspException("Errore nell'estrazione dalla base dati del campo W9LOTT.TIPO_CONTRATTO", e);
    } catch (GestoreException ge) {
      throw new JspException("Errore nell'estrazione dalla base dati del campo W9LOTT.TIPO_CONTRATTO", ge);
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrazione dalla base dati del campo W9LOTT.TIPO_CONTRATTO", e);
    }
    
    return result;
  }

}
