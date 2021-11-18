package it.eldasoft.sil.pt.tags.funzioni;

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

public class GetNormaFunction extends AbstractFunzioneTag {

  public GetNormaFunction() {
	super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {
	
	String codice = params[1].toString();

	if (codice == null || codice.equals("")) {
	   codice = (String) UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
	}
	
	if (codice == null || codice.equals("")) {
	  codice = (String) UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
	}
	
	SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
	          pageContext, SqlManager.class);
	
	Long norma = null;
	Long tiprog = null;

	try {
	  DataColumnContainer key = new DataColumnContainer(codice);
	  Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();	
		
	  if (StringUtils.isNotEmpty(codice)) {
		norma = (Long) sqlManager.getObject(
		  "select NORMA from PIATRI where CONTRI=?", new Object[] { contri }); 
		tiprog = (Long) sqlManager.getObject(
				  "select TIPROG from PIATRI where CONTRI=?", new Object[] { contri });
	  }
	  
	  if (norma != null) {
		  if (tiprog != null){
			  pageContext.setAttribute("tiprog", tiprog, PageContext.REQUEST_SCOPE);
		  }
		  return norma.toString();
	  }
	  
    } catch (SQLException e) {
      throw new JspException("Errore nell'estrarre il campo norma", e);
    } catch (GestoreException ge) {
      throw new JspException("Errore nell'estrarre il campo norma", ge);
    }
	return null;
  }

}
