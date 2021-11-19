package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.w9.common.CostantiW9;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Function per determinare il link da richiamare per caricare la scheda del flusso a partire 
 * dalla lista dei flussi.
 * 
 * @author Luca.Giacomazzo
 */
public class GetRedirectFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public GetRedirectFunction() {
	super(3, new Class[] { PageContext.class, String.class, String.class });
  }
   
  /**
   * Implementazione del metodo function.
   */
  public String function(PageContext pageContext, Object[] params) throws JspException {
  	String key = params[1].toString();
  	Long aggiudicazione = Long.parseLong((String)params[2]);
  	String pag = "";   
  	String chiave = "";
  	if (!key.trim().equals("")) {
  	  DataColumnContainer keys = new DataColumnContainer(key);
  	  String codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().toString();
  	  String codLott = (keys.getColumnsBySuffix("CODLOTT", true))[0].getValue().toString();
  	  String num = (keys.getColumnsBySuffix("NUM", true))[0].getValue().toString();
  			
  	  Long faseEsecuzione = (Long) (keys.getColumnsBySuffix("FASE_ESECUZIONE", true))[0].getValue().getValue();
  			
  	  switch(faseEsecuzione.intValue()) {
  	    case CostantiW9.A22:
  	      pag = "w9esito/w9esito-scheda.jsp";
  	      chiave = "W9ESITO.CODGARA=N:" + codgara + ";W9ESITO.CODLOTT=N:" + codLott;
  	      break;
  	    case CostantiW9.A24:
  	      pag = "w9imprese/w9imprese-lista.jsp";
          chiave = "W9IMPRESE.CODGARA=N:" + codgara + ";W9IMPRESE.CODLOTT=N:" + codLott;
  	      break;
  	    case CostantiW9.A04:
  	      pag = "w9appa/w9appa-scheda-a04.jsp";
          chiave = "W9APPA.CODGARA=N:" + codgara + ";W9APPA.NUM_APPA=N:1;W9APPA.CODLOTT=N:" + codLott;
          break;
  	    case CostantiW9.A05:
  	      pag = "w9appa/w9appa-scheda-a05.jsp";
  	      chiave = "W9APPA.CODGARA=N:" + codgara + ";W9APPA.NUM_APPA=N:" + aggiudicazione + ";W9APPA.CODLOTT=N:" + codLott;
  	      break;
  	    case CostantiW9.A06:
  	      pag = "w9iniz/w9iniz-scheda-a06.jsp";
  	      chiave = "W9INIZ.CODGARA=N:" + codgara + ";W9INIZ.NUM_INIZ=N:" + num + ";W9INIZ.CODLOTT=N:" + codLott;
  	      break;
  	    case CostantiW9.A07:
  	      pag = "w9iniz/w9iniz-scheda-a07.jsp";
          chiave = "W9INIZ.CODGARA=N:" + codgara + ";W9INIZ.NUM_INIZ=N:" + num + ";W9INIZ.CODLOTT=N:" + codLott;
          break;
    		case CostantiW9.A08:
    		  pag = "w9avan/w9avan-scheda.jsp";
    		  chiave = "W9AVAN.CODGARA=N:" + codgara + ";W9AVAN.NUM_AVAN=N:" + num + ";W9AVAN.CODLOTT=N:" + codLott;
    		  break;
    		case CostantiW9.A25:
          pag = "w9avan/w9avan-scheda-a25.jsp";
          chiave = "W9AVAN.CODGARA=N:" + codgara + ";W9AVAN.NUM_AVAN=N:" + num + ";W9AVAN.CODLOTT=N:" + codLott;
          break;  
    	case CostantiW9.A09:
    		  pag = "w9conc/w9conc-scheda-a09.jsp";
    		  chiave = "W9CONC.CODGARA=N:" + codgara + ";W9CONC.NUM_CONC=N:" + num + ";W9CONC.CODLOTT=N:" + codLott;
    		  break;
    		case CostantiW9.A10:
    		  pag = "w9conc/w9conc-scheda-a10.jsp";
    		  chiave = "W9CONC.CODGARA=N:" + codgara + ";W9CONC.NUM_CONC=N:" + num + ";W9CONC.CODLOTT=N:" + codLott;
          break;
  		  case CostantiW9.A11:
  		    pag = "w9coll/w9coll-scheda.jsp";
  		    chiave = "W9COLL.CODGARA=N:" + codgara + ";W9COLL.NUM_COLL=N:1;W9COLL.CODLOTT=N:" + codLott;
  		    break;
  		  case CostantiW9.A12:
  		    pag = "w9sosp/w9sosp-scheda.jsp";
  		    chiave = "W9SOSP.CODGARA=N:" + codgara + ";W9SOSP.NUM_SOSP=N:" + num + ";W9SOSP.CODLOTT=N:" + codLott;
          break;
    		case CostantiW9.A13:
    		  pag = "w9vari/w9vari-scheda.jsp";
    		  chiave = "W9VARI.CODGARA=N:" + codgara + ";W9VARI.NUM_VARI=N:" + num + ";W9VARI.CODLOTT=N:" + codLott;
    		  break;
    		case CostantiW9.A14:
    		  pag = "w9acco/w9acco-scheda.jsp";
    		  chiave = "W9ACCO.CODGARA=N:" + codgara + ";W9ACCO.NUM_ACCO=N:" + num + ";W9ACCO.CODLOTT=N:" + codLott;
    		  break;
    		case CostantiW9.A15:
    		  pag = "w9suba/w9suba-scheda.jsp";
    		  chiave = "W9SUBA.CODGARA=N:" + codgara + ";W9SUBA.NUM_SUBA=N:" + num + ";W9SUBA.CODLOTT=N:" + codLott;
    		  break;
    		case CostantiW9.A16:
    		  pag = "w9rita/w9rita-scheda.jsp";
    		  chiave = "W9RITA.CODGARA=N:" + codgara + ";W9RITA.NUM_RITA=N:" + num + ";W9RITA.CODLOTT=N:" + codLott;
    		  break;
    		case CostantiW9.A20:
    		  pag = "w9iniz/w9iniz-scheda-a20.jsp";
    		  chiave = "W9INIZ.CODGARA=N:" + codgara + ";W9INIZ.NUM_INIZ=N:1;W9INIZ.CODLOTT=N:" + codLott;
    		  break;
    		case CostantiW9.A21:
    		  pag = "w9appa/w9appa-scheda-a21.jsp";
    		  chiave = "W9APPA.CODGARA=N:" + codgara + ";W9APPA.NUM_APPA=N:1;W9APPA.CODLOTT=N:" + codLott;
    		  break;
    		case CostantiW9.B02:
    		  pag = "w9tecpro/w9tecpro-scheda.jsp";
    		  chiave = "W9TECPRO.CODGARA=N:" + codgara + ";W9TECPRO.NUM_TPRO=N:" + num + ";W9TECPRO.CODLOTT=N:" + codLott;
    		  break;
        case CostantiW9.B03:
          pag = "w9regcon/w9regcon-scheda.jsp";
          chiave = "W9REGCON.CODGARA=N:" + codgara + ";W9REGCON.NUM_REGO=N:" + num + ";W9REGCON.CODLOTT=N:" + codLott;
          break;
        case CostantiW9.B04:
          pag = "w9missic/w9missic-scheda.jsp";
          chiave = "W9MISSIC.CODGARA=N:" + codgara + ";W9MISSIC.NUM_MISS=N:" + num + ";W9MISSIC.CODLOTT=N:" + codLott;
          break;
        case CostantiW9.B06:
          pag = "w9inasic/w9inasic-scheda.jsp";
          chiave = "W9INASIC.CODGARA=N:" + codgara + ";W9INASIC.NUM_INAD=N:" + num + ";W9INASIC.CODLOTT=N:" + codLott;
          break;
        case CostantiW9.B07:
          pag = "w9infor/w9infor-scheda.jsp";
          chiave = "W9INFOR.CODGARA=N:" + codgara + ";W9INFOR.NUM_INFOR=N:" + num + ";W9INFOR.CODLOTT=N:" + codLott;
          break;
        case CostantiW9.B08:
          pag = "w9cant/w9cant-scheda.jsp";
          chiave = "W9CANT.CODGARA=N:" + codgara + ";W9CANT.NUM_CANT=N:" + num + ";W9CANT.CODLOTT=N:" + codLott;
          break;
  	  }
  
  	//		if (faseEsecuzione.equals("988")) {
  	//			pag = "w9sosp/w9sops-scheda-a12.jsp";
  	//			chiave = "W9SOSP.CODGARA=N:" + codgara + ";W9SOSP.NUM_SOSP=N:" + num + ";W9SOSP.CODLOTT=N:" + codLott;
  	//		}
  	//		if (faseEsecuzione.equals("989")) {
  	//			pag = "w9sosp/w9sops-scheda-a12.jsp";
  	//			chiave = "W9SOSP.CODGARA=N:" + codgara + ";W9SOSP.NUM_SOSP=N:" + num + ";W9SOSP.CODLOTT=N:" + codLott;
  	//		}
  	//		if (faseEsecuzione.equals("990")) {
  	//			pag = "w9sosp/w9sops-scheda-a12.jsp";
  	//			chiave = "W9SOSP.CODGARA=N:" + codgara + ";W9SOSP.NUM_SOSP=N:" + num + ";W9SOSP.CODLOTT=N:" + codLott;
  	//		}
  	//		if (faseEsecuzione.equals("991")) {
  	//			pag = "w9sosp/w9sops-scheda-a12.jsp";
  	//			chiave = "W9SOSP.CODGARA=N:" + codgara + ";W9SOSP.NUM_SOSP=N:" + num + ";W9SOSP.CODLOTT=N:" + codLott;
  	//		}
  	//		if (faseEsecuzione.equals("992")) {
  	//			pag = "w9sosp/w9sops-scheda-a12.jsp";
  	//			chiave = "W9SOSP.CODGARA=N:" + codgara + ";W9SOSP.NUM_SOSP=N:" + num + ";W9SOSP.CODLOTT=N:" + codLott;
  	//		}
  			
  	  pageContext.getSession().setAttribute("keyup", chiave);
  	}
  	
  	return pag + "?chiave=" + chiave;
  }

}