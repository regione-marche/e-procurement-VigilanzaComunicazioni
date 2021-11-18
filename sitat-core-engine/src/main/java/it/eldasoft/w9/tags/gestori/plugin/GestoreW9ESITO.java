package it.eldasoft.w9.tags.gestori.plugin;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.w9.utils.UtilitySITAT;

public class GestoreW9ESITO extends AbstractGestorePreload {

  private static final String SQL_ESITO_LOTTI_GARE   = "select ESITO_PROCEDURA, DATA_VERB_AGGIUDICAZIONE from W9ESITO where CODGARA=?";

  public GestoreW9ESITO(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {
  }

  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda) throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", page, SqlManager.class);
    GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager", page, GeneManager.class);
    
    String codice = "";
    DataColumnContainer key = null;
    Long codgara = null;
    Long codlott = null;

    try {
      codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      key = new DataColumnContainer(codice);

      codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();

      Long numeroAggiudicazioni = (Long) sqlManager.getObject("select count(*) from W9FASI where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE in (1,987)", 
      		new Object[] { codgara, codlott });
      
      if (numeroAggiudicazioni.longValue() > 0) {
      	page.setAttribute("esisteFaseAggiudicazione", "true", PageContext.REQUEST_SCOPE);
      }
      
      if (! UtilityTags.SCHEDA_MODO_VISUALIZZA.equals(modoAperturaScheda)) {
      	Long idfDelegate = (Long) sqlManager.getObject("select ID_F_DELEGATE from W9GARA where CODGARA=?", new Object[] { codgara });
        if ((idfDelegate == null) || (idfDelegate != null && 4 != idfDelegate.longValue())) {
          page.setAttribute("removePropostaAggiudicazione", "true", PageContext.REQUEST_SCOPE);
        }
      }
      String profiloAttivo = (String) page.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
      if (UtilitySITAT.isConfigurazioneVigilanza(geneManager, profiloAttivo)) {
      	page.setAttribute("removeAggiudicazione", "true", PageContext.REQUEST_SCOPE);
      }
      
      page.setAttribute("esitiUguali", "false", PageContext.REQUEST_SCOPE);
      if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
        String SQL_CTRL_BLOB = "select " + sqlManager.getDBFunction("length", new String[] { "FILE_ALLEGATO" }) + " as FILE_ALLEGATO from W9ESITO where CODGARA=? AND CODLOTT=?";
    	  
        String ctrlBlob = ""
            + sqlManager.getObject(SQL_CTRL_BLOB, new Object[] {codgara, codlott});
        if (ctrlBlob == null
            || ctrlBlob.trim().equals("")
            || ctrlBlob.trim().equals("0")
            || ctrlBlob.trim().equals("null")) {
          page.setAttribute("ctrlBlob", "false", PageContext.REQUEST_SCOPE);
        } else {
          page.setAttribute("ctrlBlob", "true", PageContext.REQUEST_SCOPE);
        }
      } else {
        // Se esiste l'aggiudicazione, alla creazione della fase 'Comunicazione Esito', alcuni
        // campi della fase stessa vengono inizializzati con valori di campi dell'aggiudicazione.
        Vector< ? > datiAppa = sqlManager.getVector(
            "select DATA_VERB_AGGIUDICAZIONE, IMPORTO_AGGIUDICAZIONE from W9APPA where CODGARA=? and CODLOTT=?",
            new Object[]{codgara, codlott});

        if (datiAppa != null && datiAppa.size() == 2) {
          // Se esiste l'occorrenza dell'aggiudicazione in W9APPA
          Object dataVerbAggiu = ((JdbcParametro) datiAppa.get(0)).getValue();
          Object importoAggiu = ((JdbcParametro) datiAppa.get(1)).getValue();

          page.setAttribute("esito", "1", PageContext.REQUEST_SCOPE);
          
          if (dataVerbAggiu != null) {
            String dataStr = UtilityDate.convertiData((Date) dataVerbAggiu, UtilityDate.FORMATO_GG_MM_AAAA);
            page.setAttribute("dataVerbAggiu", dataStr, PageContext.REQUEST_SCOPE);
          }
          if (importoAggiu != null) {
            //String strImporto = UtilityNumeri.convertiImporto((Double) importoAggiu, 2);
            page.setAttribute("importoAggiu", importoAggiu, PageContext.REQUEST_SCOPE);
          }
        } else {
        	//Se la gara ha dei lotti con gia' inserita la scheda esito, e tutte le schede esito della gara 
        	//hanno lo stesso valore per l’esito della procedura, valorizzare il campo con questo valore
        	
        	//Se la gara ha dei lotti con gia' inserita la scheda esito, e tutte le schede esito 
        	//della gara hanno lo stesso valore per la data di aggiudicazione, valorizzare il campo con questo valore.
        	
        	List<?> listaLotti = sqlManager.getListVector(SQL_ESITO_LOTTI_GARE, new Object[] { codgara });
        	
        	Long esito = null, esitoLotto = null;
        	Date data = null, dataLotto = null;
        	boolean isEqualEsiti = true, isEqualDate = true;
          if (listaLotti != null && listaLotti.size() > 0) {
          	for (int i = 0; i < listaLotti.size(); i++) {
          		esitoLotto = (Long) SqlManager.getValueFromVectorParam(listaLotti.get(i), 0).getValue();
          		if (esitoLotto != null) {
          			if (isEqualEsiti && (esito == null || esitoLotto.equals(esito))) {
            			esito = esitoLotto;
            		} else {
            			isEqualEsiti = false;
            		}
          		}
          		dataLotto = (Date) SqlManager.getValueFromVectorParam(listaLotti.get(i), 1).getValue();
          		if (dataLotto != null) {
            		if (isEqualDate && (data == null || dataLotto.equals(data))) {
            			data = dataLotto;
            		} else {
            			isEqualDate = false;
            		}
          		}
          	}
          	if (isEqualEsiti) {
          		page.setAttribute("esitiUguali", "true", PageContext.REQUEST_SCOPE);
          		page.setAttribute("esito", esito, PageContext.REQUEST_SCOPE);
          	}
          	if (isEqualDate) {
          		String dataStr = UtilityDate.convertiData((Date) data, UtilityDate.FORMATO_GG_MM_AAAA);
                  page.setAttribute("dataVerbAggiu", dataStr, PageContext.REQUEST_SCOPE);
          	}
          }
        }
      }
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati della fase di comunicazione esito", exc);
    }

  }

}