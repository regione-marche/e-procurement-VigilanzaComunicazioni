package it.eldasoft.w9.tags.gestori.plugin;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.spring.UtilitySpring;

public class GestoreW9LOTT extends AbstractGestorePreload {

  private static final String SQL_ESTRAZIONE_DATI_W9LOTT             = "select RUP, CPV, TIPO_CONTRATTO, EXSOTTOSOGLIA from W9LOTT where CODGARA = ? AND CODLOTT = ?";
  private static final String SQL_ESTRAZIONE_DATI_W9GARA             = "select RUP, FLAG_ENTE_SPECIALE from W9GARA where CODGARA = ? ";
  
  private static final String SQL_ESTRAZIONE_DATI_TAB1_FORN          = "select TAB1TIP, TAB1DESC, TAB1MOD, TAB1NORD, TAB1ARC from TAB1 where TAB1COD like '%W3019%' order by TAB1TIP";
  private static final String SQL_ESTRAZIONE_DATI_TAB1_LAV           = "select TAB1TIP, TAB1DESC, TAB1MOD, TAB1NORD, TAB1ARC from TAB1 where TAB1COD like '%W3002%' order by TAB1TIP";
  private static final String SQL_ESTRAZIONE_DATI_TAB1_COND          = "select TAB1TIP, TAB1DESC, TAB1MOD, TAB1NORD, TAB1ARC from TAB1 where TAB1COD like '%W3006%' and TAB1TIP <= 32 order by TAB1TIP";

  private static final String SQL_ESTRAZIONE_DATI_TABCPV_CPVDESC     = "select CPVDESC from TABCPV where CPVCOD4 = ?";
  private static final String SQL_ESTRAZIONE_DATI_W9LOTT_LUOGO_ISTAT = "select LUOGO_ISTAT from W9LOTT where CODGARA = ? AND CODLOTT = ?";
  private static final String SQL_ESTRAZIONE_DATI_TECNI  			 = "select NOMTEC from TECNI,W9LOTT where TECNI.CODTEC=W9LOTT.DEC and W9LOTT.CODGARA =? and W9LOTT.CODLOTT =?";
  
  /** Costruttore. */
  public GestoreW9LOTT(BodyTagSupportGene tag) {
    super(tag);
  }

  public void doAfterFetch(PageContext arg0, String arg1) throws JspException {

  }

  public void doBeforeBodyProcessing(PageContext page, String modoAperturaScheda)
      throws JspException {

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", page, SqlManager.class);
    GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager", page, GeneManager.class);
    
    if (geneManager.esisteTabella("SITAT_IND_INDICATORI")) {
      page.setAttribute("isAttivoIndicatoriLotto", Boolean.TRUE);
    }
    
    String codice = "";
    DataColumnContainer key = null;
    Long codgara = null;
    Long codlott = null;

    if (!UtilityTags.SCHEDA_MODO_INSERIMENTO.equals(modoAperturaScheda)) {
      codice = (String) UtilityTags.getParametro(page,
          UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      key = new DataColumnContainer(codice);
      try {
    	  codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
        codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
        // estrazione dati W9LOTT e TECNI
        List< ? > datiW9LottList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_W9LOTT,
            new Object[] { codgara, codlott });
        String tipoContratto = "";
        String cpv = "";
        String cpvdescr = "";
        String isExSottosoglia = "";
        if (datiW9LottList.size() > 0) {
          cpv = ((List< ? >) datiW9LottList.get(0)).get(1).toString();
          tipoContratto = ((List< ? >) datiW9LottList.get(0)).get(2).toString();
          isExSottosoglia = ((List< ? >) datiW9LottList.get(0)).get(3).toString();
          if (cpv != null && !cpv.equals("")) {
            List< ? > cpvdescrList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_TABCPV_CPVDESC,
                new Object[] { cpv });
            if (cpvdescrList.size() > 0) {
              cpvdescr = ((List< ? >) cpvdescrList.get(0)).get(0).toString();
            }
          }
        }
        page.setAttribute("codcpv", cpv, PageContext.REQUEST_SCOPE);
        page.setAttribute("cpvdescr", cpvdescr, PageContext.REQUEST_SCOPE);
        page.setAttribute("tipoContratto", tipoContratto, PageContext.REQUEST_SCOPE);
        if (StringUtils.isNotEmpty(isExSottosoglia)) {
          page.setAttribute("isExSottosoglia", isExSottosoglia, PageContext.REQUEST_SCOPE);
        }

        List< ? > datiComuniList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_W9LOTT_LUOGO_ISTAT,
            new Object[] { codgara, codlott });

        String codComune = "";
        String descrComune = "";
        String descrProvincia = "";
        String nomeTecnico = "";
        if (datiComuniList.size() > 0) {
          codComune = ((List< ? >) datiComuniList.get(0)).get(0).toString();
          if (codComune != null && !codComune.equals("")) {
            String sqlEtrazioneDatiComune = "select tb1.tabcod3, tabsche.tabdesc from tabsche, tabsche tb1, w9lott where tabsche.tabcod='S2003' and tabsche.tabcod1='09' and tb1.tabcod='S2003' and tb1.tabcod1='07' and w9lott.luogo_istat = tabsche.tabcod3 and " + sqlManager.getDBFunction("substr", new String[] {"tabsche.tabcod3", "4", "3"}) + " = " + sqlManager.getDBFunction("substr", new String[] {"tb1.tabcod2", "2", "3"}) + " and tabsche.tabcod3 = ? and w9lott.codgara = ? and w9lott.codlott = ?";
            List< ? > datiListComune = sqlManager.getListVector(sqlEtrazioneDatiComune,
                new Object[] { codComune, codgara, codlott });
            if (datiListComune.size() > 0) {
              descrComune = ((List< ? >) datiListComune.get(0)).get(1).toString();
              descrProvincia = ((List< ? >) datiListComune.get(0)).get(0).toString();
            }
          }
        }

        page.setAttribute("denomComuneEsecuzione", descrComune, PageContext.REQUEST_SCOPE);
        page.setAttribute("descrProvinciaEsecuzione", descrProvincia, PageContext.REQUEST_SCOPE);

        //Recupero nome tecnico
        List<?> datiListTecni = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_TECNI, new Object[] { codgara, codlott });
        if (datiListTecni.size() > 0) {
        	nomeTecnico = ((List<?>) datiListTecni.get(0)).get(0).toString();
        }
        page.setAttribute("nomeTecnico", nomeTecnico, PageContext.REQUEST_SCOPE);
        /*
        // estrazione dati W9APPAFORN
        List< ? > datiW9FornList = sqlManager.getListVector(
            SQL_ESTRAZIONE_DATI_W9APPAFORN, new Object[] { codgara, codlott });
        page.setAttribute("datiW9FornList", datiW9FornList, PageContext.REQUEST_SCOPE);
        // estrazione dati W9APPALAV
        List< ? > datiW9LavList = sqlManager.getListVector(
            SQL_ESTRAZIONE_DATI_W9APPALAV, new Object[] { codgara, codlott });
        page.setAttribute("datiW9LavList", datiW9LavList, PageContext.REQUEST_SCOPE);
        // estrazione dati W9COND
        List< ? > datiW9CondList = sqlManager.getListVector(
            SQL_ESTRAZIONE_DATI_W9COND, new Object[] { codgara, codlott });
        page.setAttribute("datiW9CondList", datiW9CondList, PageContext.REQUEST_SCOPE);
        */

      } catch (SQLException sqle) {
        throw new JspException("Errore nell'esecuzione della query per l'estrazione dei dati ", sqle);
      } catch (Exception exc) {
        throw new JspException("Errore nel caricamento dati ", exc);
      }
    } else {
      try {
        // estrazione di rup di gara
        codice = (String) UtilityTags.getParametro(page,
            UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA_PARENT);
        if (codice == null) {
          codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
        }
        key = new DataColumnContainer(codice);
        codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();

        Vector< ? > datiW9GARA = sqlManager.getVector(SQL_ESTRAZIONE_DATI_W9GARA, new Object[]{codgara});

        String rupw9gara = "";
        String flagEnteSpeciale = "";
        if (datiW9GARA.size() > 0) {
          rupw9gara = ((JdbcParametro) datiW9GARA.get(0)).toString();
          flagEnteSpeciale = ((JdbcParametro) datiW9GARA.get(1)).toString();
        }
        page.setAttribute("rupw9gara", rupw9gara, PageContext.REQUEST_SCOPE);
        page.setAttribute("flagEnteSpeciale", flagEnteSpeciale, PageContext.REQUEST_SCOPE);
      } catch (SQLException sqle) {
        throw new JspException(
            "Errore nell'esecuzione della query per l'estrazione del rup della Gara",
            sqle);
      } catch (Exception exc) {
        throw new JspException("Errore nel caricamento del rup della Gara", exc);
      }
    }
    // estrazione dei valori dei tabellati w3019, w3002, w3006, W3z03 e W3z11
    try {
      List< ? > datiTabellatoFornList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_TAB1_FORN, new Object[] {});
      List< ? > datiTabellatoLavList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_TAB1_LAV, new Object[] {});
      List< ? > datiTabellatoCondList = sqlManager.getListVector(SQL_ESTRAZIONE_DATI_TAB1_COND, new Object[] {});

      page.setAttribute("datiTabellatoFornList", datiTabellatoFornList, PageContext.REQUEST_SCOPE);
      page.setAttribute("datiTabellatoLavList", datiTabellatoLavList, PageContext.REQUEST_SCOPE);
      page.setAttribute("datiTabellatoCondList", datiTabellatoCondList, PageContext.REQUEST_SCOPE);
    } catch (SQLException sqle) {
      throw new JspException(
          "Errore nell'esecuzione della query per l'estrazione dei dati dei tabellati ", sqle);
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati dei tabellati", exc);
    }
  }

}
