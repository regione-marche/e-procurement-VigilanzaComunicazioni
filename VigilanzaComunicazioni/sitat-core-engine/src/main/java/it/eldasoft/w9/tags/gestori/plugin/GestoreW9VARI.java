package it.eldasoft.w9.tags.gestori.plugin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.utils.UtilitySITAT;

public class GestoreW9VARI extends AbstractGestorePreload {

  /** Costruttore. */
  public GestoreW9VARI(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

  }

  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda) throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", page, SqlManager.class);
    
    String codice = "";
    DataColumnContainer key = null;
    Long codgara = null;
    Long codlott = null;
    Long numVari = null;
	Date dataPubblicazioneGara = null;
	Date data2016_04_18 = null;
    String tipoContratto = "";
    int versioneSimog = 1;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
    
    if (UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA) != null
        && !UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA).equals("")) {
      codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
    } else {
      codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
    }
    
    key = new DataColumnContainer(codice);
    try {
      codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
        numVari = (key.getColumnsBySuffix("NUM_VARI", true))[0].getValue().longValue();
      }
      
      tipoContratto = UtilitySITAT.getTipoContrattoLotto(codgara, codlott, sqlManager);
      versioneSimog = UtilitySITAT.getVersioneSimog(sqlManager, codgara);
      dataPubblicazioneGara = UtilitySITAT.getDataPubblicazioneGara(sqlManager, codgara);
      data2016_04_18 = sdf.parse("18-4-2016");
    } catch (SQLException sqle) {
      throw new JspException("Errore nell'esecuzione della query per l'estrazione dei dati ", sqle);
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati ", exc);
    }
    
    // estrazione dei valori dei tabellati W3017 o W3018
    try {
      List< ? > datiTabellatoList = null;
      if (dataPubblicazioneGara == null || (dataPubblicazioneGara != null && dataPubblicazioneGara.compareTo(data2016_04_18)<=0)) {
        if (StringUtils.isNotEmpty(tipoContratto) 
              && ("L".equals(tipoContratto.toUpperCase()) || "CL".equals(tipoContratto.toUpperCase()))) {
          datiTabellatoList = sqlManager.getListVector(
              "select TAB1TIP, TAB1DESC, TAB1MOD, TAB1NORD from TAB1 where TAB1COD='W3017' and TAB1TIP<=13 order by TAB1TIP",
              new Object[] {});
        } else {
          datiTabellatoList = sqlManager.getListVector(
              "select TAB1TIP, TAB1DESC, TAB1MOD, TAB1NORD from TAB1 where TAB1COD='W3018' order by TAB1TIP",
              new Object[] {});
        }
      } else {
    	  if (dataPubblicazioneGara != null && dataPubblicazioneGara.compareTo(data2016_04_18)>0 && versioneSimog <= 2) {
    		  datiTabellatoList = sqlManager.getListVector(
    		            "select TAB1TIP, TAB1DESC, TAB1MOD, TAB1NORD from TAB1 where TAB1COD='W3017' and TAB1TIP<18 and (TAB1ARC <> '1' OR TAB1ARC is null) order by TAB1TIP",
    		            new Object[] {});
    	  } else if (versioneSimog >= 3) {
    		  datiTabellatoList = sqlManager.getListVector(
  		            "select TAB1TIP, TAB1DESC, TAB1MOD, TAB1NORD from TAB1 where TAB1COD='W3017' and (TAB1ARC <> '1' OR TAB1ARC is null) order by TAB1TIP",
  		            new Object[] {});
    	  } else {
    		  datiTabellatoList = sqlManager.getListVector(
    		            "select TAB1TIP, TAB1DESC, TAB1MOD, TAB1NORD from TAB1 where TAB1COD='W3017' and TAB1ARC is null and TAB1TIP<>18 order by TAB1TIP",
    		            new Object[] {});
    	  }
      }
      page.setAttribute("datiTabellatoList", datiTabellatoList, PageContext.REQUEST_SCOPE);
      page.setAttribute("versioneSimog", new Long(versioneSimog), PageContext.REQUEST_SCOPE);

    } catch (SQLException sqle) {
      throw new JspException(
          "Errore nell'esecuzione della query per l'estrazione dei dati dei tabellati ", sqle);
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati dei tabellati", exc);
    }

  }
}
