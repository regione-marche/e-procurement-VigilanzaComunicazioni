package it.eldasoft.sil.pt.tags.gestori.plugin;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.axis.utils.StringUtils;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiServiziRest;

public class GestoreINTTRI extends AbstractGestorePreload {

  private final String SQL_ESTRAZIONE_DATI_INTTRI1  = "select INTTRI.CODCPV, TABCPV.CPVDESC from TABCPV, INTTRI where TABCPV.CPVCOD4 = INTTRI.CODCPV AND INTTRI.CONTRI = ? and INTTRI.CONINT = ?";
  private final String SQL_ESTRAZIONE_DATI_INTTRI2 = "select INTTRI.AVCPV, TABCPV.CPVDESC from TABCPV, INTTRI where TABCPV.CPVCOD4 = INTTRI.AVCPV AND INTTRI.CONTRI = ? and INTTRI.CONINT = ?";
  private final String SQL_ESTRAZIONE_DATI_INTTRI3 = "select INTTRI.MRCPV, TABCPV.CPVDESC from TABCPV, INTTRI where TABCPV.CPVCOD4 = INTTRI.MRCPV AND INTTRI.CONTRI = ? and INTTRI.CONINT = ?";
  private final String SQL_ESTRAZIONE_DATI_TECNI  = "select NOMTEC from TECNI,INTTRI where TECNI.CODTEC=INTTRI.CODRUP and INTTRI.CONTRI =? and INTTRI.CONINT =?";
  private final String SQL_ESTRAZIONE_DATI_CATINT = "select INTERV, CATINT from INTTRI where INTTRI.CONTRI =? and INTTRI.CONINT =?";
  private final String SQL_ESTRAZIONE_DATI_CUIINT = "select CUIINT from INTTRI where CONTRI =? and CONINT =?";
  private final String SQL_ESTRAZIONE_CONTEGGIO_CUI = "select count(*) from INTTRI where CUIINT = ?";

  public GestoreINTTRI(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

  }

  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        page, SqlManager.class);

    GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager",
        page, GeneManager.class);
    
    String codice = "";
    DataColumnContainer key = null;
    Long contri = null;
    Long conint = null;

    try {
      if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
        codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
        key = new DataColumnContainer(codice);
   	    contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
   	    conint = (key.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();
    	
        List<?> datiListInttri = sqlManager.getListVector(
            this.SQL_ESTRAZIONE_DATI_INTTRI1, new Object[] { contri, conint });
        String codcpv = "";
        String cpvdescr = "";
        String comune = "";
        String provincia = "";
        String nomeTecnico = "";
        
        if (datiListInttri.size() > 0) {
          codcpv = ((List<?>) datiListInttri.get(0)).get(0).toString();
          cpvdescr = ((List<?>) datiListInttri.get(0)).get(1).toString();
        }

        datiListInttri = sqlManager.getListVector(
                this.SQL_ESTRAZIONE_DATI_INTTRI2, new Object[] { contri, conint });
        String avcpv = "";
        String avcpvdescr = "";
        if (datiListInttri.size() > 0) {
          avcpv = ((List<?>) datiListInttri.get(0)).get(0).toString();
          avcpvdescr = ((List<?>) datiListInttri.get(0)).get(1).toString();
        }
        
        datiListInttri = sqlManager.getListVector(
                this.SQL_ESTRAZIONE_DATI_INTTRI3, new Object[] { contri, conint });
        String mrcpv = "";
        String mrcpvdescr = "";
        if (datiListInttri.size() > 0) {
          mrcpv = ((List<?>) datiListInttri.get(0)).get(0).toString();
          mrcpvdescr = ((List<?>) datiListInttri.get(0)).get(1).toString();
        }
        
        String SQL_ESTRAZIONE_DATI_COMUNE = "select tb1.tabcod3, tabsche.tabdesc from tabsche, tabsche tb1, inttri where tabsche.tabcod='S2003' and tabsche.tabcod1='09' and tb1.tabcod='S2003' and tb1.tabcod1='07' and inttri.comint=tabsche.tabcod3 and " + sqlManager.getDBFunction("substr",new String[] {"tabsche.tabcod3","4","3"}) + " = " + sqlManager.getDBFunction("substr",new String[] {"tb1.tabcod2","2","3"}) + " and tabsche.tabcod3=inttri.comint and inttri.contri=? and inttri.conint=?";
        List<?> datiListComune = sqlManager.getListVector(
            SQL_ESTRAZIONE_DATI_COMUNE, new Object[] { contri, conint });
        if (datiListComune.size() > 0) {
          comune = ((List<?>) datiListComune.get(0)).get(1).toString();
          provincia = ((List<?>) datiListComune.get(0)).get(0).toString();
        }

        List<?> datiListTecni = sqlManager.getListVector(
            this.SQL_ESTRAZIONE_DATI_TECNI, new Object[] { contri, conint });
        if (datiListTecni.size() > 0) {
          nomeTecnico = ((List<?>) datiListTecni.get(0)).get(0).toString();
        }

        String valoreCatint = "";
        List<?> datiListCatint = sqlManager.getListVector(
            this.SQL_ESTRAZIONE_DATI_CATINT, new Object[] { contri, conint });
        if (datiListCatint.size() > 0) {
          List<?> rigoListCatint = (List<?>) datiListCatint.get(0);
          if (rigoListCatint.get(0) != null
              && !rigoListCatint.get(0).toString().equals("")
              && rigoListCatint.get(1) != null
              && !rigoListCatint.get(1).toString().equals("")) {
            valoreCatint = rigoListCatint.get(0) + "-" + rigoListCatint.get(1);
          }
        }

        page.setAttribute("codcpv", codcpv, PageContext.REQUEST_SCOPE);
        page.setAttribute("cpvdescr", cpvdescr, PageContext.REQUEST_SCOPE);
        
        page.setAttribute("avcpv", avcpv, PageContext.REQUEST_SCOPE);
        page.setAttribute("avcpvdescr", avcpvdescr, PageContext.REQUEST_SCOPE);
        
        page.setAttribute("avcpv", mrcpv, PageContext.REQUEST_SCOPE);
        page.setAttribute("mrcpvdescr", mrcpvdescr, PageContext.REQUEST_SCOPE);
        
        page.setAttribute("comune", comune, PageContext.REQUEST_SCOPE);
        page.setAttribute("provincia", provincia, PageContext.REQUEST_SCOPE);
        page.setAttribute("nomeTecnico", nomeTecnico, PageContext.REQUEST_SCOPE);
        page.setAttribute("valoreCatint", valoreCatint, PageContext.REQUEST_SCOPE);
        
        String profilo = (String) page.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
        if (geneManager.getProfili().checkProtec(profilo, "FUNZ", "VIS", "ALT.INTTRI_CAMPI_COMUNE_PISA")) {
          List<?> importiSocietaHouse = sqlManager.getListVector(
          		  "select IMPORTO,1 from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=7 and ANNO=1 union "
              + "select IMPORTO,2 from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=7 and ANNO=2 union "
              + "select IMPORTO,3 from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=7 and ANNO=3 union "
              + "select IMPORTO,9 from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=7 and ANNO=9 ",
              new Object[] { contri, conint, contri, conint, contri, conint, contri, conint });
          
          if (importiSocietaHouse != null && importiSocietaHouse.size() > 0) {
            double importoSocietaHouseTotale = 0;
            for (int i=0; i < importiSocietaHouse.size(); i++) {
              Vector<?> importoSocietaHouse = (Vector<?>) importiSocietaHouse.get(i);
              Object importoObj = ((JdbcParametro) importoSocietaHouse.get(0)).getValue();
              String anno = ((JdbcParametro) importoSocietaHouse.get(1)).getStringValue();
              if (importoObj != null) {
                page.setAttribute("HOUTRI".concat(anno), importoObj);
                importoSocietaHouseTotale += ((Double) importoObj).doubleValue();
              }
            }
            if (importoSocietaHouseTotale > 0) {
              page.setAttribute("HOUTRITOT", importoSocietaHouseTotale);
            }
          }
          
          List<?> importiDaAltriSoggetti = sqlManager.getListVector(
              "select IMPORTO,1 from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=8 and ANNO=1 union "
            + "select IMPORTO,2 from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=8 and ANNO=2 union "
            + "select IMPORTO,3 from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=8 and ANNO=3 union "
            + "select IMPORTO,9 from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=8 and ANNO=9 ",
            new Object[] { contri, conint, contri, conint, contri, conint, contri, conint });
          
          if (importiDaAltriSoggetti != null && importiDaAltriSoggetti.size() > 0) {
            double importoDaAltriSoggettiTotale = 0;
            for (int i=0; i < importiDaAltriSoggetti.size(); i++) {
              Vector<?> importoAltroSoggetto = (Vector<?>) importiDaAltriSoggetti.get(i);
              Object importoObj = ((JdbcParametro) importoAltroSoggetto.get(0)).getValue();
              String anno = ((JdbcParametro) importoAltroSoggetto.get(1)).getStringValue();
              if (importoObj != null) {
                page.setAttribute("IASTRI".concat(anno), importoObj);
                importoDaAltriSoggettiTotale += ((Double) importoObj).doubleValue();
              }
            }
            if (importoDaAltriSoggettiTotale > 0) {
              page.setAttribute("IASTRITOT", importoDaAltriSoggettiTotale);
            }
          }
        }
        //
        if (!UtilityTags.SCHEDA_MODO_VISUALIZZA.equals(modoAperturaScheda)) {
        	page.setAttribute("modificaCUI", "si", PageContext.REQUEST_SCOPE);
        	String cuiint = (String) sqlManager.getObject(SQL_ESTRAZIONE_DATI_CUIINT, new Object[] { contri, conint });
            if ( !StringUtils.isEmpty(cuiint) ) {
            	Long nrCui = (Long) sqlManager.getObject(SQL_ESTRAZIONE_CONTEGGIO_CUI, new Object[] { cuiint });
            	if (nrCui > 1) {
            		page.setAttribute("modificaCUI", "no", PageContext.REQUEST_SCOPE);
            	}
            }
        }
        
      } else {
        codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
        key = new DataColumnContainer(codice);
   	    contri = (key.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
      }
      
      Long tiprog = (Long) sqlManager.getObject("select TIPROG from PIATRI where CONTRI =?", new Object[] { contri });
      
      if (tiprog != null) {
        page.setAttribute("tiprog", tiprog, PageContext.REQUEST_SCOPE);
      }
 	  
 	  if (UtilityTags.SCHEDA_MODO_VISUALIZZA.equals(modoAperturaScheda)) {
 		  String urlIntegrazioneLFS = ConfigManager.getValore(CostantiServiziRest.PROP_WS_INTEGRAZIONE_LFS_URL);
 		  if (urlIntegrazioneLFS != null && !urlIntegrazioneLFS.equals("")) {
 			page.setAttribute("urlIntegrazioneLFS", urlIntegrazioneLFS, PageContext.REQUEST_SCOPE);  
 		  }
 	  }
    } catch (SQLException sqle) {
      throw new JspException(
          "Errore nell'esecuzione della query per l'estrazione dei dati dell'intervento", sqle);
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati dell'Intervento", exc);
    }
  }

}
