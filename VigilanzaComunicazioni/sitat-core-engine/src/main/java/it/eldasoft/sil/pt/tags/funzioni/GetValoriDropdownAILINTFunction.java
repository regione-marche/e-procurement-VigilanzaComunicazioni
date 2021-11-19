package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetValoriDropdownAILINTFunction extends AbstractFunzioneTag {

  public GetValoriDropdownAILINTFunction() {
    super(3, new Class[] {PageContext.class, String.class, String.class });
  }
  
  public String function(PageContext pageContext, Object[] args)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);
    
    String sContri = (String) args[1];
    String sConint = (String) args[2];
    
    Long lContri = null;
    Long lConint = null;     
    
    int iAilintDB = 0;
    
    List listaValoriAILINT = new ArrayList();
    
    String sSelect = "";
    
    try {
      
      if (!"".equals(sContri)) {
        lContri = new Long(sContri);
      }  
      
      if (!"".equals(sConint)) {
        lConint = new Long(sConint);
      }
      
      if(lContri != null && lConint != null){ 
        sSelect = "select AILINT from INTTRI where CONTRI=? and CONINT=?";
        Long lAilintDB = (Long) sqlManager.getObject(sSelect, new Object[] { lContri, lConint } );
        if(lAilintDB != null){
          iAilintDB = lAilintDB.intValue();
        } 
      }      
      
      Calendar cal = Calendar.getInstance();
      int annoCorrente = cal.get(Calendar.YEAR);
      int yearInLista = 0;
      if(iAilintDB != 0 && iAilintDB < annoCorrente){
        listaValoriAILINT.add(iAilintDB);
      } 
      for (int i = 0; i <= 3; i++) {
        yearInLista = annoCorrente + i;
        listaValoriAILINT.add(yearInLista);
      }       
      
      
      pageContext.setAttribute("listaValoriAILINT", listaValoriAILINT,
          PageContext.REQUEST_SCOPE);
  
   
     } catch (Exception e) {
      throw new JspException(
          "Errore nell'estrazione dei dati per la popolazione della/e dropdown list",
          e);
    }
    return null;
  }

}
