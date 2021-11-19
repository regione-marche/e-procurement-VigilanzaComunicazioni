package it.eldasoft.w9.tags.gestori.plugin;

import java.util.Date;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityDate;

/**
 * Gestore di inizializzazione della scheda fase iniziale del contratto
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9CANT extends AbstractGestorePreload {

  public GestoreW9CANT(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext page, String modoAperturaScheda) throws JspException {
  }

  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda) throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        page, SqlManager.class);
    
    Long numappa = null;
    
    if (UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
      try {
    	  numappa = new Long(page.getSession().getAttribute("aggiudicazioneSelezionata").toString());
        String codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
        DataColumnContainer key = new DataColumnContainer(codice);

        Long codGara = new Long(key.getColumn("CODGARA").getValue().getStringValue());
        Long codLott = new Long(key.getColumn("CODLOTT").getValue().getStringValue());
        
        Object obj = sqlManager.getObject(
            "select DATA_VERB_INIZIO from W9INIZ where CODGARA=? and CODLOTT=? and NUM_APPA=?",
            new Object[] { codGara, codLott, numappa });

        if (obj != null) {
          Date data = (Date) obj;
          page.setAttribute("dataInizioLavori", UtilityDate.convertiData(data, UtilityDate.FORMATO_GG_MM_AAAA), PageContext.REQUEST_SCOPE);
        }
        
        //Ricavo valore di default per TIPINTERV
        Object idAppalto = sqlManager.getObject("select ID_APPALTO from W9APPALAV where CODGARA=? and CODLOTT=? and NUM_APPAL=?", new Object[] { codGara, codLott, new Long(1) });
        if (idAppalto != null) {
            int tipInterv = 1;
            switch (Integer.parseInt(idAppalto.toString())) {
    		case 7:
    			tipInterv = 3;
    			break;
    		case 8:
    			tipInterv = 6;
    			break;
    		case 9:
    			tipInterv = 4;
    			break;
    		case 10:
    			tipInterv = 7;
    			break;
    		case 11:
    			tipInterv = 2;
    			break;			
    		}
            page.setAttribute("tipoIntervento", tipInterv, PageContext.REQUEST_SCOPE);
        }
        
        //Ricavo valore di default per l'indirizzo email di ricezione attestato ovvero la email del RUP della gara
        Object mailric = sqlManager.getObject("select TECNI.EMATEC from W9GARA left outer join TECNI ON W9GARA.RUP = TECNI.CODTEC where W9GARA.CODGARA=?", new Object[] { codGara});
        if (mailric != null) {
        	page.setAttribute("mailric", mailric, PageContext.REQUEST_SCOPE);
        }
        
      } catch (Exception exc) {
        throw new JspException("Errore nel caricamento dati della Scheda cantiere/notifiche preliminari", exc);
      }
    }
    
    if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
    	try {
    		String codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
            DataColumnContainer key = new DataColumnContainer(codice);

            Long codGara = new Long(key.getColumn("CODGARA").getValue().getStringValue());
            Long codLott = new Long(key.getColumn("CODLOTT").getValue().getStringValue());
            Long numCant = new Long(key.getColumn("NUM_CANT").getValue().getStringValue());
            String comune = "";
        	String SQL_ESTRAZIONE_DATI_COMUNE = "select tb1.tabcod3, tabsche.tabdesc from tabsche, tabsche tb1, w9cant where tabsche.tabcod='S2003' and tabsche.tabcod1='09' and tb1.tabcod='S2003' and tb1.tabcod1='07' and w9cant.comune=tabsche.tabcod3 and " + sqlManager.getDBFunction("substr",new String[] {"tabsche.tabcod3","4","3"}) + " = " + sqlManager.getDBFunction("substr",new String[] {"tb1.tabcod2","2","3"}) + " and tabsche.tabcod3=w9cant.comune and w9cant.codgara=? and w9cant.codlott=? and w9cant.num_cant = ?";
            List<?> datiListComune = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_COMUNE, new Object[] { codGara, codLott, numCant });
            if (datiListComune.size() > 0) {
              comune = ((List<?>) datiListComune.get(0)).get(1).toString();
            }
            page.setAttribute("comune", comune, PageContext.REQUEST_SCOPE);
    	} catch (Exception exc) {
            throw new JspException("Errore nel caricamento dati della Scheda cantiere/notifiche preliminari", exc);
        }
    	
    }
    
    try {

      List< ? > datiTabellatoW9008List = sqlManager.getListVector(
          "select TAB1TIP, TAB1DESC, TAB1MOD, TAB1NORD from TAB1 where TAB1COD like '%W9008%' order by TAB1TIP", 
          new Object[] {});
      page.setAttribute("datiTabellatoW9008List", datiTabellatoW9008List, PageContext.REQUEST_SCOPE);
    
    } catch (Exception exc) {
      throw new JspException(
          "Errore nel caricamento dati della Scheda cantiere/notifiche preliminari", exc);
    }
  }

}