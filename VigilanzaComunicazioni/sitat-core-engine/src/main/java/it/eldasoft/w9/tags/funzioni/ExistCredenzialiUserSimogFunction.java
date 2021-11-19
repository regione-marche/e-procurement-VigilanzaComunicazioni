package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.common.CostantiSimog;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

public class ExistCredenzialiUserSimogFunction extends AbstractFunzioneTag {
	
	private final String NO                 = "no";
	private final String SI                 = "si";
  	
  public ExistCredenzialiUserSimogFunction() {
  	super(1, new Class[] { PageContext.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {
  	String result = NO;
	  String loginSIMOG = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
	  String passwordSIMOG = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD);
		  	    
  	if (StringUtils.isNotEmpty(loginSIMOG) && StringUtils.isNotEmpty(passwordSIMOG)) {
  		result = SI;
  	}
		return result;
	}

}
