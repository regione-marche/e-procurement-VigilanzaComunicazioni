package it.eldasoft.sil.pt.tags.gestori.plugin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;

/**
 * 
 * @author Mirco 18.07.2012
 * 
 * plugin scheda flussi
 *
 */

public class GestorePTFLUSSI extends AbstractGestorePreload {

  /** query per estrarre il codice fiscale / partita iva della stazione applatante. */
  private String sqlSelectCfisStazioneAppaltante = "select cfein from uffint where codein = ? ";
  /** query per estrarre l'id amministrazione della stazione applatante. */
  private String sqlSelectIDAmminStazioneAppaltante = "select idammin from uffint where codein = ? ";  
  /** query per estrarre il file xml del flusso. */
  private String sqlSelectXML = "select xml from ptflussi where idflusso = ? ";
  /** query per estrarre i flussi del programma selezionato. */
  private String sqlSelectFlussi = "select idflusso from ptflussi where key01 = ? ";
  
  public GestorePTFLUSSI(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

  }

  /**
   * 
   */
  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda)
      throws JspException {
	  
	    String codice = "";
	    DataColumnContainer key = null;
	    Long idFlusso = null;
	    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", page, SqlManager.class);
    
      try {
    	  if (UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
    		  //recupero il codice fiscale della stazione appaltante
        	  String codein = page.getSession().getAttribute("uffint").toString();
       	  
        	  String codiceFiscale = (String) sqlManager.getObject(sqlSelectCfisStazioneAppaltante, new Object[] { codein });
        	  page.setAttribute("cfein", codiceFiscale, PageContext.REQUEST_SCOPE);
        	  Long idAmmin = (Long) sqlManager.getObject(sqlSelectIDAmminStazioneAppaltante, new Object[] { codein });
        	  page.setAttribute("idammin", idAmmin, PageContext.REQUEST_SCOPE);
        	  //verifico se l'invio che sto inserendo è un primo invio (1) o una rettifica (2)
        	  codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
        	  key = new DataColumnContainer(codice);
        	  Long contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
        	  List< ? > listaFlussi = sqlManager.getListVector(sqlSelectFlussi, new Object[] { contri });
        	  if (listaFlussi.size() > 0) {
        		  page.setAttribute("tipinv", "2", PageContext.REQUEST_SCOPE);
        	  } else {
        		  page.setAttribute("tipinv", "1", PageContext.REQUEST_SCOPE);
        	  }
    	  } else {
    		  codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    		  key = new DataColumnContainer(codice);
              idFlusso = (key.getColumnsBySuffix("IDFLUSSO", true))[0].getValue().longValue();
              //Verifico l'esistenza del file XML
    	      String ctrlFile = "" + sqlManager.getObject(sqlSelectXML, new Object[] { idFlusso });
    	      if (ctrlFile == null || ctrlFile.trim().equals("") || ctrlFile.length() == 0 || ctrlFile.trim().equals("null")) {
    	          page.setAttribute("ctrlXml", "false", PageContext.REQUEST_SCOPE);
    	      } else {
    	          page.setAttribute("ctrlXml", "true", PageContext.REQUEST_SCOPE);
    	      }
    	      //Verifico l'esistenza del file PDF
    	      String sqlSelectPdf = "select " + sqlManager.getDBFunction("length", new String[] {"FILE_ALLEGATO"}) + " as FILE_ALLEGATO from PTFLUSSI where IDFLUSSO = ? ";
    	      ctrlFile = "" + sqlManager.getObject(sqlSelectPdf, new Object[] { idFlusso });
    	      if (ctrlFile == null || ctrlFile.trim().equals("") || ctrlFile.length() == 0 || ctrlFile.trim().equals("null")) {
    	          page.setAttribute("ctrlPdf", "false", PageContext.REQUEST_SCOPE);
    	      } else {
    	          page.setAttribute("ctrlPdf", "true", PageContext.REQUEST_SCOPE);
    	      }
   	      
    	  }
        
      } catch (SQLException sqle) {
        throw new JspException(
            "Errore nell'esecuzione della query per la preparazione del flusso",
            sqle);
      } catch (Exception exc) {
        throw new JspException("Errore nel caricamento dati del flusso",
            exc);
      }
    }

}
