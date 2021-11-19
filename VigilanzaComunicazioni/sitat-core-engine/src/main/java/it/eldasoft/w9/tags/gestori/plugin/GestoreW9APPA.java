package it.eldasoft.w9.tags.gestori.plugin;

import it.avlp.simog.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaEsclusoType;
import it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.BodyTagSupportGene;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestorePreload;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.utils.utility.UtilityNumeri;
import it.eldasoft.w9.bl.RichiestaIdGaraCigManager;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class GestoreW9APPA extends AbstractGestorePreload {

  private static Logger logger = Logger.getLogger(GestoreW9APPA.class);
  
  /** Query per lotto flag ente speciale. */
  private static final String SQL_DATI_LOTTO 		= "select FLAG_ENTE_SPECIALE, ID_SCELTA_CONTRAENTE from W9LOTT where CODGARA=? AND CODLOTT=?";
  /** Query per data verbale aggiudicazione e importo aggiudicazione dalla fase Comunicazione esito. */
  private static final String SQL_W9ESITO           = "select DATA_VERB_AGGIUDICAZIONE, IMPORTO_AGGIUDICAZIONE from W9ESITO where CODGARA=? AND CODLOTT=?";
  /** Recupero il valore iva tabellato*/
  private static final String SQL_IVA            	= "select TAB1DESC from TAB1 where TAB1COD = 'W9013' AND TAB1TIP = 1";
  private static final String SQL_AGGIUDICAZIONE_LOTTI_GARE   = "select DATA_VERB_AGGIUDICAZIONE, DATA_STIPULA, DURATA_CON, TIPO_ATTO, DATA_ATTO, NUMERO_ATTO from W9APPA where CODGARA=?";
  private static final String SQL_AGGIUDICAZIONE_INIZIALE   = "select COD_STRUMENTO,IMPORTO_LAVORI,IMPORTO_SERVIZI,IMPORTO_FORNITURE,IMPORTO_ATTUAZIONE_SICUREZZA,FLAG_SICUREZZA,IMPORTO_PROGETTAZIONE,IMP_NON_ASSOG,ALTRE_SOMME,FLAG_IMPORTI,OPERE_URBANIZ_SCOMPUTO,FLAG_LIVELLO_PROGETTUALE,"+
  "REQUISITI_SETT_SPEC,FLAG_ACCORDO_QUADRO,PROCEDURA_ACC,PREINFORMAZIONE,TERMINE_RIDOTTO,ID_MODO_INDIZIONE,VERIFICA_CAMPIONE,FIN_REGIONALE," +
  "DATA_MANIF_INTERESSE,NUM_MANIF_INTERESSE,DATA_SCADENZA_RICHIESTA_INVITO,NUM_IMPRESE_RICHIEDENTI,DATA_INVITO,NUM_IMPRESE_INVITATE,DATA_SCADENZA_PRES_OFFERTA," +
  "NUM_IMPRESE_OFFERENTI,NUM_OFFERTE_AMMESSE,NUM_OFFERTE_ESCLUSE,NUM_OFFERTE_FUORI_SOGLIA,NUM_IMP_ESCL_INSUF_GIUST,OFFERTA_MASSIMO,OFFERTA_MINIMA,VAL_SOGLIA_ANOMALIA,ASTA_ELETTRONICA," +
  "IMPORTO_SUBTOTALE,IMPORTO_COMPL_APPALTO,IMPORTO_IVA,IMPORTO_DISPOSIZIONE,IMPORTO_COMPL_INTERVENTO from W9APPA where CODGARA = ? and CODLOTT = ? and NUM_APPA = 1";
  
  /**
   * Costruttore.
   * 
   * @param tag Tag
   */
  public GestoreW9APPA(BodyTagSupportGene tag) {
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

    try {
      codice = (String) UtilityTags.getParametro(page, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      key = new DataColumnContainer(codice);

      codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      
      boolean isSAQ = UtilitySITAT.isSAQ(codgara, sqlManager);
      boolean isAAQ = UtilitySITAT.isAAQ(codgara, sqlManager);
      page.setAttribute("isSAQ", isSAQ, PageContext.REQUEST_SCOPE);
      page.setAttribute("isAAQ", isAAQ, PageContext.REQUEST_SCOPE);
      
      // VIGI-186
      String aggiudicazioneInibita = ConfigManager.getValore("AggiudicazioneInibita");
      boolean esisteAttoEsitoAggiudicazioneInviato = false;
      if ("S".equalsIgnoreCase(aggiudicazioneInibita) || "W".equalsIgnoreCase(aggiudicazioneInibita)) {
      	// query per determinare se un atto di esito/aggiudicazione esiste ed e' stato inviato con successo
      	Long counter = (Long) sqlManager.getObject(
      			"select count(p1.CODGARA) from W9PUBBLICAZIONI p1 join W9PUBLOTTO p2 on p1.CODGARA=p2.CODGARA and p1.NUM_PUBB=p2.NUM_PUBB join W9CF_PUBB p3 on p1.TIPDOC=p3.ID "
      			+ "where p1.CODGARA=? and p2.CODLOTT=? and p3.TIPO in ('A','E') and p1.ID_RICEVUTO is not null",
      					new Object[] { codgara, codlott });
      	if (counter.intValue() == 0) {
      		page.setAttribute("aggiudicazioneInibita", aggiudicazioneInibita, PageContext.REQUEST_SCOPE);
      	} else {
      		esisteAttoEsitoAggiudicazioneInviato = true;
      	}
      }
      
      Vector<?> datiLotto = sqlManager.getVector(SQL_DATI_LOTTO, new Object[] {codgara, codlott });

      String flagEnteSpeciale = null;
      Long idSceltaContraente = null;
      
      if (datiLotto != null) {
        JdbcParametro jdbcFlagEnteSpeciale = ((JdbcParametro) datiLotto.get(0));
        JdbcParametro jdbcIdSceltaContraente = ((JdbcParametro) datiLotto.get(1));
        
        flagEnteSpeciale = jdbcFlagEnteSpeciale.getStringValue();
        idSceltaContraente = jdbcIdSceltaContraente.longValue();
      }
      
      if ("S".equalsIgnoreCase(flagEnteSpeciale)) {
        page.setAttribute("isFlagEnteSpeciale", true, PageContext.REQUEST_SCOPE);
      } else {
        page.setAttribute("isFlagEnteSpeciale", false, PageContext.REQUEST_SCOPE);
      }
      
      if (idSceltaContraente != null) {
        page.setAttribute("idSceltaContraente", idSceltaContraente, PageContext.REQUEST_SCOPE);
      }
      
      //Ricavo il valore dell'iva
      Vector<?> datiIva = sqlManager.getVector(SQL_IVA, new Object[] { });
      if (datiIva != null) {
    	  double iva = Double.parseDouble(((JdbcParametro) datiIva.get(0)).getStringValue());
    	  page.setAttribute("iva", iva, PageContext.REQUEST_SCOPE);
      }
      
      //versioneSimog
      int versioneSimog = UtilitySITAT.getVersioneSimog(sqlManager, codgara);
      page.setAttribute("ver_simog", new Long(versioneSimog), PageContext.REQUEST_SCOPE);
      
      if ("NUOVO".equals(modoAperturaScheda)) {
    	  //se sto inserendo una riaggiudicazione recupero i dati che rimangono fissi dalla prima aggiudicazione
    	  Long nuovaAggiudicazione = new Long(page.getSession().getAttribute("aggiudicazioneSelezionata").toString());
    	  if (nuovaAggiudicazione > 1) {
    		  Vector< ? > datiPrimaAggiudicazione =  sqlManager.getVector(SQL_AGGIUDICAZIONE_INIZIALE, new Object[] {codgara, codlott });
    		  if (datiPrimaAggiudicazione != null) {
    			  JdbcParametro valore = (JdbcParametro) datiPrimaAggiudicazione.get(0);
	          if (valore.getValue() != null) {
	              page.setAttribute("initCOD_STRUMENTO", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(1);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_LAVORI", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(2);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_SERVIZI", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(3);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_FORNITURE", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(4);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_ATTUAZIONE_SICUREZZA", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(5);
	          if (valore.getValue() != null) {
	              page.setAttribute("initFLAG_SICUREZZA", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(6);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_PROGETTAZIONE", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(7);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMP_NON_ASSOG", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(8);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initALTRE_SOMME", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(9);
	          if (valore.getValue() != null) {
	              page.setAttribute("initFLAG_IMPORTI", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(10);
	          if (valore.getValue() != null) {
	              page.setAttribute("initOPERE_URBANIZ_SCOMPUTO", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(11);
	          if (valore.getValue() != null) {
	              page.setAttribute("initFLAG_LIVELLO_PROGETTUALE", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(12);
	          if (valore.getValue() != null) {
	              page.setAttribute("initREQUISITI_SETT_SPEC", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(13);
	          if (valore.getValue() != null) {
	              page.setAttribute("initFLAG_ACCORDO_QUADRO", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(14);
	          if (valore.getValue() != null) {
	              page.setAttribute("initPROCEDURA_ACC", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(15);
	          if (valore.getValue() != null) {
	              page.setAttribute("initPREINFORMAZIONE", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(16);
	          if (valore.getValue() != null) {
	              page.setAttribute("initTERMINE_RIDOTTO", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(17);
	          if (valore.getValue() != null) {
	              page.setAttribute("initID_MODO_INDIZIONE", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(18);
	          if (valore.getValue() != null) {
	              page.setAttribute("initVERIFICA_CAMPIONE", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(19);
	          if (valore.getValue() != null) {
	              page.setAttribute("initFIN_REGIONALE", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(20);
	          if (valore.getValue() != null) {
	              page.setAttribute("initDATA_MANIF_INTERESSE", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(21);
	          if (valore.getValue() != null) {
	              page.setAttribute("initNUM_MANIF_INTERESSE", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(22);
	          if (valore.getValue() != null) {
	              page.setAttribute("initDATA_SCADENZA_RICHIESTA_INVITO", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(23);
	          if (valore.getValue() != null) {
	              page.setAttribute("initNUM_IMPRESE_RICHIEDENTI", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(24);
	          if (valore.getValue() != null) {
	              page.setAttribute("initDATA_INVITO", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(25);
	          if (valore.getValue() != null) {
	              page.setAttribute("initNUM_IMPRESE_INVITATE", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(26);
	          if (valore.getValue() != null) {
	              page.setAttribute("initDATA_SCADENZA_PRES_OFFERTA", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(27);
	          if (valore.getValue() != null) {
	              page.setAttribute("initNUM_IMPRESE_OFFERENTI", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(28);
	          if (valore.getValue() != null) {
	              page.setAttribute("initNUM_OFFERTE_AMMESSE", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(29);
	          if (valore.getValue() != null) {
	              page.setAttribute("initNUM_OFFERTE_ESCLUSE", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(30);
	          if (valore.getValue() != null) {
	              page.setAttribute("initNUM_OFFERTE_FUORI_SOGLIA", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(31);
	          if (valore.getValue() != null) {
	              page.setAttribute("initNUM_IMP_ESCL_INSUF_GIUST", new Long(valore.getStringValue()), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(32);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initOFFERTA_MASSIMO", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(33);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initOFFERTA_MINIMA", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(34);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initVAL_SOGLIA_ANOMALIA", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(35);
	          if (valore.getValue() != null) {
	              page.setAttribute("initASTA_ELETTRONICA", valore.getStringValue(), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(36);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_SUBTOTALE", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(37);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_COMPL_APPALTO", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(38);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_IVA", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(39);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_DISPOSIZIONE", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
	          valore = (JdbcParametro) datiPrimaAggiudicazione.get(40);
	          if (valore.getValue() != null) {
	        	  page.setAttribute("initIMPORTO_COMPL_INTERVENTO", UtilityNumeri.convertiDouble((Double)valore.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2), PageContext.REQUEST_SCOPE);
	          }
    		  }
    	  }
    	  
        if (isAAQ) {
          // VIGILANZA-139: all'apertura dei dati dell'adesione in inserimento prevalorizzare alcuni campi
          // e gli aggiudicatari scaricando da SIMOG (accedendovi con le credenziali associate all'utente
          // attivo) i dati del CIG = W9GARA.CIG_ACCQUADRO. Se si e' in configurazione Vigilanza, non si fa nulla,
        	// perché si potrebbe non disporre delle credenziali SIMOG nella tabella W9LOADER_APPALTO_USR.
          GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager", page, GeneManager.class);

          ProfiloUtente profiloUtente = (ProfiloUtente) page.getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
          String profiloAttivo = (String) page.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
          String codein = (String) page.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
          
          boolean isConfigurazioneVigilanza = UtilitySITAT.isConfigurazioneVigilanza(geneManager, profiloAttivo); 
          
          String cigAccQuadro = (String) sqlManager.getObject("select CIG_ACCQUADRO from W9GARA where CODGARA=?", new Object[] { codgara } );
          
          ConsultaGaraResponseDocument consultaGaraResponseDocument = null;
          
          if (isConfigurazioneVigilanza) {
            // VIGILANZA-146 
            /*Long syscon = new Long(profiloUtente.getId());

            String simoguser = null;
            String simogpass = null;
            // Leggo le eventuali credenziali memorizzate
            EsportazioneXMLSIMOGManager exportXmlSimogManagerm = (EsportazioneXMLSIMOGManager) UtilitySpring.getBean(
                "esportazioneXMLSIMOGManager", page.getSession().getServletContext(), EsportazioneXMLSIMOGManager.class);

            HashMap<String, String> hMapSIMOGLAUserPass = new HashMap<String, String>();
            hMapSIMOGLAUserPass = exportXmlSimogManagerm.recuperaSIMOGLAUserPass(syscon);

            simoguser = ((String) hMapSIMOGLAUserPass.get("simoguser"));
            simogpass = ((String) hMapSIMOGLAUserPass.get("simogpass"));
            
            if (StringUtils.isEmpty(simoguser) || StringUtils.isEmpty(simogpass)) {
              // bisogna richiedere le credenziali di accesso a SIMOG all'utente
              page.setAttribute("richiedereCredenzialiSimog", "true", PageContext.REQUEST_SCOPE);
              
            } else {
              SimogManager simogManager = (SimogManager) UtilitySpring.getBean("simogManager",
                  page, SimogManager.class);
              HashMap<String, Object> resultMap = new HashMap<String, Object>();
              simogManager.consultaLottoSimog(cigAccQuadro, resultMap, simoguser, simogpass);

              if (resultMap.containsKey("esito") && (Boolean) resultMap.get("esito)")) {
                if (resultMap.containsKey("responseConsultaGara")) {
                  consultaGaraResponseDocument = (ConsultaGaraResponseDocument) resultMap.get("responseConsultaGara");
                }
              } else {
                // Errore con il servizio simog
                if (resultMap.containsKey("esito")) {
                  
                }
              }
            }*/
          } else {
            RichiestaIdGaraCigManager richiestaIdGaraCigManager = (RichiestaIdGaraCigManager) UtilitySpring.getBean("richiestaIdGaraCigManager",
                page.getServletContext(), RichiestaIdGaraCigManager.class);
            HashMap<String, Object> hm2 = richiestaIdGaraCigManager.getXmlSimog(cigAccQuadro, profiloUtente.getId(), codein, null, null);

            if (((Boolean) hm2.get("esito")).booleanValue() ) {
              String garaXml = (String) hm2.get("garaXml");
              consultaGaraResponseDocument = ConsultaGaraResponseDocument.Factory.parse(garaXml);
            } else {
              logger.error("Errore nell'interazione con i servizi SIMOG: " + (String) hm2.get("msgErr"));
              page.setAttribute("erroreInitAccordoQuadro", (String) hm2.get("msgErr"), PageContext.REQUEST_SCOPE);
            }
          }
          
          if (consultaGaraResponseDocument != null) {
            if (consultaGaraResponseDocument.getConsultaGaraResponse().isSetReturn()) {
              if (consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getSuccess()) {
                if (consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().isSetGaraXML()) {
                  SchedaType schedaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML();
                  if (schedaType.isSetDatiScheda()) {
                    SchedaCompletaType[] arraySchedeComplete = schedaType.getDatiScheda().getSchedaCompletaArray();
                    AggiudicazioneType aggiudicazioneType = null;
                    SchedaEsclusoType schedaEsclusoType = null;
                    SchedaSottosogliaType schedaSottosogliaType = null;

                    if (arraySchedeComplete.length > 1) {
                      // Ciclo l'array per cercare la scheda con CUI maggiore, cioe' quello che
                      // contiene i dati dell'ultima aggiudicazione (nei casi di riaggiudicazione)
                      List<String> listaCodiciCUI = new ArrayList<String>();
                      HashMap<String, Long> mappa = new HashMap<String, Long>();
                      for (int i=0; i < arraySchedeComplete.length; i++) {
                        if (arraySchedeComplete[i].isSetAggiudicazione()) {
                          String codiceCUI = arraySchedeComplete[i].getCUI();
                          mappa.put(codiceCUI, new Long(i));
                          listaCodiciCUI.add(codiceCUI);
                        }
                      }
                      
                      if (listaCodiciCUI.size() > 0) {
                        // Ordinamento della lista dei codici CUI
                        Collections.sort(listaCodiciCUI);
                        // Considero l'indice dell'ultimo codice CUI (quindi l'ultima aggiudicazione)
                        Long indice = mappa.get(listaCodiciCUI.get(listaCodiciCUI.size()-1));
                        if (arraySchedeComplete[indice.intValue()].isSetAggiudicazione()) {
                        	aggiudicazioneType = arraySchedeComplete[indice.intValue()].getAggiudicazione();
                        }
                        if (arraySchedeComplete[indice.intValue()].isSetEscluso()) {
                        	schedaEsclusoType = arraySchedeComplete[indice.intValue()].getEscluso();
                        }
                        if (arraySchedeComplete[indice.intValue()].isSetSottosoglia()) {
                        	schedaSottosogliaType = arraySchedeComplete[indice.intValue()].getSottosoglia();
                        }
                      } else {
                        // nessuna delle Schede complete ha l'aggiudicazione
                        logger.error("Su SIMOG l'aggiudicazione dell'accordo quadro per il CIG " + cigAccQuadro + " non e' stata confermata (1).");
                        page.setAttribute("erroreInitAccordoQuadro", "La scheda di aggiudicazione dell'accordo quadro per il CIG "
                            + cigAccQuadro + " non e' stata confermata su SIMOG.", PageContext.REQUEST_SCOPE);
                      }
                    } else {
                      if (arraySchedeComplete.length == 1) {
                      	if (arraySchedeComplete[0].isSetAggiudicazione()) {
                      		aggiudicazioneType = arraySchedeComplete[0].getAggiudicazione();
                      	}
                      	if (arraySchedeComplete[0].isSetEscluso()) {
                        	schedaEsclusoType = arraySchedeComplete[0].getEscluso();
                        }
                        if (arraySchedeComplete[0].isSetSottosoglia()) {
                        	schedaSottosogliaType = arraySchedeComplete[0].getSottosoglia();
                        }
                      } else {
                        // l'unica scheda completa non ha l'aggiudicazione
                        logger.error("Su SIMOG l'aggiudicazione dell'accordo quadro per il CIG " + cigAccQuadro + " non e' stata confermata (2).");
                        page.setAttribute("erroreInitAccordoQuadro", "La scheda di aggiudicazione dell'accordo quadro per il CIG "
                            + cigAccQuadro + " non e' stata confermata su SIMOG.", PageContext.REQUEST_SCOPE);
                      }
                    }
                    if (aggiudicazioneType != null) {
                    	this.initDatiAggiudicazione(page, schedaType, aggiudicazioneType);
                    } else if (schedaEsclusoType != null) {
                    	this.initDatiEscluso(page, schedaType, schedaEsclusoType);
                    } else if (schedaSottosogliaType != null) {
                    	this.initDatiSottosoglia(page, schedaType, schedaSottosogliaType);
                    }
                  } else {
                    // SIMOG risponde senza dati della Scheda
                    logger.error("Errore nell'inizializzazione della scheda adesione accordo quadro: Simog risponde senza dati della Scheda (1) - CIG ".concat(cigAccQuadro));
                    page.setAttribute("erroreInitAccordoQuadro", "Errore nell'inizializzazione della scheda adesione per i dati incompleti forniti da SIMOG", PageContext.REQUEST_SCOPE);
                    //throw new JspException("Errore nel formato dei dati ricevuti da SIMOG per il CIG " + cigAccQuadro + " (1)");
                  }
                } else {
                  // SIMOG risponde senza oggetto XML
                  logger.error("Errore nell'inizializzazione della scheda adesione accordo quadro: Simog risponde senza oggetto XML (2) - CIG ".concat(cigAccQuadro));
                  page.setAttribute("erroreInitAccordoQuadro", "Errore nell'inizializzazione della scheda adesione per i dati incompleti forniti da SIMOG", PageContext.REQUEST_SCOPE);
                  //throw new JspException("Errore nel formato dei dati ricevuti da SIMOG per il CIG " + cigAccQuadro + " (2)");
                }
              } else {
                // SIMOG risponde con errore (no success)
                if (StringUtils.isNotEmpty(consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getError())) {
                  logger.error("Errore nell'inizializzazione della scheda adesione accordo quadro: Simog risponde con errore (3) - CIG ".concat(cigAccQuadro)
                      + " : ".concat(consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getError()));
                } else {
                  logger.error("Errore nell'inizializzazione della scheda adesione accordo quadro: Simog risponde con errore (3) - CIG ".concat(cigAccQuadro));
                }
                
                page.setAttribute("erroreInitAccordoQuadro", "Errore nell'inizializzazione della scheda adesione per i dati incompleti forniti da SIMOG", PageContext.REQUEST_SCOPE);
                //throw new JspException("Errore nel formato dei dati ricevuti da SIMOG per il CIG " + cigAccQuadro + " (3)");
              }
            } else {
              // no return
              logger.error("Errore nell'inizializzazione della scheda adesione accordo quadro: Simog risponde senza oggetto di ritorno (4)");
              page.setAttribute("erroreInitAccordoQuadro", "Errore nell'inizializzazione della scheda adesione per i dati incompleti forniti da SIMOG", PageContext.REQUEST_SCOPE);
              //throw new JspException("Errore nel formato dei dati ricevuti da SIMOG per il CIG " + cigAccQuadro + " (4)");
            }
          }

        } else {
        	
        	if (esisteAttoEsitoAggiudicazioneInviato || (! "S".equalsIgnoreCase(aggiudicazioneInibita))) {
          	Long numLotti = (Long) sqlManager.getObject("select count(*) from W9LOTT where CODGARA=?", new Object[] { codgara });
          	if (numLotti.intValue() > 1) {
          		Date dataVerbAggiudic = (Date) sqlManager.getObject("select p1.DATA_VERB_AGGIUDICAZIONE from W9PUBBLICAZIONI p1 join W9PUBLOTTO p2 on p1.CODGARA=p2.CODGARA and p1.NUM_PUBB=p2.NUM_PUBB join W9CF_PUBB p3 on p1.TIPDOC=p3.ID "
          		  			+ "where p1.CODGARA=? and p2.CODLOTT=? and p3.TIPO in ('A','E')", 
          						new Object[] { codgara, codlott } );
          		if (dataVerbAggiudic != null) {
          			String initDataVerbAggiu = UtilityDate.convertiData((Date) dataVerbAggiudic, UtilityDate.FORMATO_GG_MM_AAAA);
          			page.setAttribute("initDataVerbAggiu", initDataVerbAggiu, PageContext.REQUEST_SCOPE);
          		}
          	} else {
          		Vector<JdbcParametro> datiAttoEsito = sqlManager.getVector("select p1.DATA_VERB_AGGIUDICAZIONE, p1.IMPORTO_AGGIUDICAZIONE, p1.PERC_RIBASSO_AGG, p1.PERC_OFF_AUMENTO "
          				+ " from W9PUBBLICAZIONI p1 join W9PUBLOTTO p2 on p1.CODGARA=p2.CODGARA and p1.NUM_PUBB=p2.NUM_PUBB join W9CF_PUBB p3 on p1.TIPDOC=p3.ID "
          		  			+ "where p1.CODGARA=? and p2.CODLOTT=? and p3.TIPO in ('A','E')", 
          						new Object[] { codgara, codlott } );
          		
          		if (datiAttoEsito != null) {
                JdbcParametro dataVerbAggiu = (JdbcParametro) datiAttoEsito.get(0);
                JdbcParametro importoAggiudicazione = (JdbcParametro) datiAttoEsito.get(1);
                JdbcParametro percRibassoAggiu = (JdbcParametro) datiAttoEsito.get(2);
                JdbcParametro percOffAumento = (JdbcParametro) datiAttoEsito.get(3);
                
                if (dataVerbAggiu.getValue() != null) {
                	if (dataVerbAggiu.getValue() instanceof Timestamp) {
                		page.setAttribute("initDataVerbAggiu", UtilityDate.convertiData((Timestamp) dataVerbAggiu.getValue(), UtilityDate.FORMATO_GG_MM_AAAA),
                  		PageContext.REQUEST_SCOPE);
                	} else if (dataVerbAggiu.getValue() instanceof java.sql.Date) {
                		page.setAttribute("initDataVerbAggiu", UtilityDate.convertiData((java.sql.Date) dataVerbAggiu.getValue(), UtilityDate.FORMATO_GG_MM_AAAA),
                    		PageContext.REQUEST_SCOPE);
                  } else if (dataVerbAggiu.getValue() instanceof java.util.Date) {
                		page.setAttribute("initDataVerbAggiu", UtilityDate.convertiData((Date) dataVerbAggiu.getValue(), UtilityDate.FORMATO_GG_MM_AAAA),
                    		PageContext.REQUEST_SCOPE);
                  } 
                }
                if (importoAggiudicazione.getValue() != null) {
                  page.setAttribute("initImportoAggiu", UtilityNumeri.convertiDouble((Double)
                      importoAggiudicazione.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2),
                      PageContext.REQUEST_SCOPE);
                }
                if (percRibassoAggiu.getValue() != null) {
                	 page.setAttribute("initPercRibassoAggiu", UtilityNumeri.convertiDouble((Double)
                			 percRibassoAggiu.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2),
                       PageContext.REQUEST_SCOPE);
                }
                if (percOffAumento.getValue() != null) { 
                  page.setAttribute("initPercOffAumento", UtilityNumeri.convertiDouble((Double)
                  		percOffAumento.getValue(), UtilityNumeri.FORMATO_DOUBLE_CON_PUNTO_DECIMALE, 2),
                      PageContext.REQUEST_SCOPE);
                }
          		}
          	}
        	}
        
          List< ? > listaLotti = sqlManager.getListVector(SQL_AGGIUDICAZIONE_LOTTI_GARE, new Object[] { codgara });
  
          //Se la gara ha altri lotti con gia' inserita la scheda aggiudicazione, e tutte le schede aggiudicazione 
          //della gara hanno lo stesso valore per la data di stipula contratto, valorizzare il campo con questo valore
          Date dataStipula = null, dataStipulaLotto = null, dataAtto = null, dataAttoLotto = null;
          Long durataContratto = null, durataContrattoLotto = null, tipoAtto = null, tipoAttoLotto = null;
          String numeroAtto = null, numeroAttoLotto = null;
          
          boolean isEqualDateStipula = true;
          boolean isEqualDurataContratto = true;
          boolean isEqualTipoAtto = true;
          boolean isEqualDataAtto = true;
          boolean isEqualNumeroAtto = true;
          
          if (listaLotti != null && listaLotti.size() > 0) {
          	for (int i = 0; i < listaLotti.size(); i++) {
          		// Data stipula
          		dataStipulaLotto = (Date) SqlManager.getValueFromVectorParam(listaLotti.get(i), 1).getValue();
          		if (dataStipulaLotto != null) {
          			if (isEqualDateStipula && (dataStipula == null || dataStipulaLotto.equals(dataStipula))) {
              		dataStipula = dataStipulaLotto;
              	} else {
              		isEqualDateStipula = false;
              	}
          		}
          		// Durata contratto
          		durataContrattoLotto = (Long) SqlManager.getValueFromVectorParam(listaLotti.get(i), 2).getValue();
          		if (durataContrattoLotto != null) {
              	if (isEqualDurataContratto && (durataContratto == null || durataContrattoLotto.equals(durataContratto))) {
              		durataContratto = durataContrattoLotto;
              	} else {
              		isEqualDurataContratto = false;
              	}
          		}
          		// Tipo atto
          		tipoAttoLotto = (Long) SqlManager.getValueFromVectorParam(listaLotti.get(i), 3).getValue();
          		if (tipoAttoLotto != null) {
          		  if (isEqualTipoAtto && (tipoAtto == null || tipoAttoLotto.equals(tipoAtto))) {
          		    tipoAtto = tipoAttoLotto;
          		  } else {
          		    isEqualTipoAtto = false;
          		  }
          		}
          		// Data atto
          		dataAttoLotto = (Date) SqlManager.getValueFromVectorParam(listaLotti.get(i), 4).getValue();
          		if (dataAttoLotto != null) {
          		  if (isEqualDataAtto && (dataAtto == null || dataAttoLotto.equals(dataAtto))) {
          		    dataAtto = dataAttoLotto;
          		  } else {
          		    isEqualDataAtto = false;
          		  }
          		}
          		// Numero atto
          		numeroAttoLotto = (String) SqlManager.getValueFromVectorParam(listaLotti.get(i), 5).getValue();
          		if (numeroAttoLotto != null) {
          		  if (isEqualNumeroAtto && (numeroAtto == null || numeroAttoLotto.equals(numeroAtto))) {
                  numeroAtto = numeroAttoLotto;
                } else {
                  isEqualNumeroAtto = false;
                }
          		}
          	}
          	
          	if (isEqualDateStipula) {
          		String dataStr = UtilityDate.convertiData((Date) dataStipula, UtilityDate.FORMATO_GG_MM_AAAA);
              page.setAttribute("initDataStipula", dataStr, PageContext.REQUEST_SCOPE);
          	}
          	if (isEqualDurataContratto) {
              page.setAttribute("initDurataContratto", durataContratto, PageContext.REQUEST_SCOPE);
          	}
          	if(isEqualTipoAtto && tipoAtto != null) {
          	  page.setAttribute("initTipoAtto", tipoAtto, PageContext.REQUEST_SCOPE);
          	}
          	if (isEqualDataAtto && dataAtto != null) {
          	  String dataStr = UtilityDate.convertiData((Date) dataAtto, UtilityDate.FORMATO_GG_MM_AAAA);
          	  page.setAttribute("initDataAtto", dataStr, PageContext.REQUEST_SCOPE);
          	}
          	if (isEqualNumeroAtto && StringUtils.isNotEmpty(numeroAtto)) {
          	  page.setAttribute("initNumeroAtto", numeroAtto, PageContext.REQUEST_SCOPE);
            }
          }
        }
      }
    } catch (Exception exc) {
      throw new JspException("Errore nel caricamento dati dell/'Appalto", exc);
    }
  }

	/**
   * Inizializzazione dell'adesione accordo quadro con i dati dell'aggiudicazione della gara padre
   * 
   * @param page
   * @param schedaType
   * @param aggiudicazioneType
   */
	private void initDatiAggiudicazione(PageContext page, SchedaType schedaType, AggiudicazioneType aggiudicazioneType) {
	  HashMap<String, Object> mappa = new HashMap<String, Object>();
	  mappa.put("IMPORTO_AGGIUDICAZIONE", aggiudicazioneType.getAppalto().getIMPORTOAGGIUDICAZIONE());
	  mappa.put("DATA_VERB_AGGIUDICAZIONE", UtilityDate.convertiData(
	      aggiudicazioneType.getAppalto().getDATAVERBAGGIUDICAZIONE().getTime(),
	          UtilityDate.FORMATO_GG_MM_AAAA));
	  if (StringUtils.equals("S", aggiudicazioneType.getAppalto().getFLAGRICHSUBAPPALTO()))
	    mappa.put("FLAG_RICH_SUBAPPALTO", "1");
	  else if (StringUtils.equals("N", aggiudicazioneType.getAppalto().getFLAGRICHSUBAPPALTO()))
	    mappa.put("FLAG_RICH_SUBAPPALTO", "2");
	  if (aggiudicazioneType.getAppalto().isSetPERCRIBASSOAGG()) {
	    mappa.put("PERC_RIBASSO_AGG", aggiudicazioneType.getAppalto().getPERCRIBASSOAGG());
	  }
	  if (aggiudicazioneType.getAppalto().isSetPERCOFFAUMENTO()) {
	  	// Se PERCOFFAUMENTO e' uguale a zero, allora non si inizializza il campo nella form
	  	String temp = aggiudicazioneType.getAppalto().getPERCOFFAUMENTO();
	  	// Dalla stringa temp si rimuovono i caratteri: '0', '.' e ','
	  	String a = StringUtils.remove(StringUtils.remove(StringUtils.remove(temp,'0'),'.'),',');
	  	// Se la stringa a != "" allora PERCOFFAUMENTO e' diverso da zero
	  	if (StringUtils.isNotEmpty(a)) {
	  		mappa.put("PERC_OFF_AUMENTO", aggiudicazioneType.getAppalto().getPERCOFFAUMENTO());
	  	}
	  }
	  
	  // Aggiudicatari (solo dati per la W9AGGI)
	  if (aggiudicazioneType.getAggiudicatariArray() != null && aggiudicazioneType.getAggiudicatariArray().length > 0) {
	    mappa.put("arrayAggiudicatari", aggiudicazioneType.getAggiudicatariArray());
	  }
	  // Ditte ausiliarie (dati per W9AGGI)                          
	  if (aggiudicazioneType.getDitteAusiliarieArray() != null && aggiudicazioneType.getDitteAusiliarieArray().length > 0) {
	    mappa.put("arrayDitteAusiliarie", aggiudicazioneType.getDitteAusiliarieArray());
	  }
	  // Anagrafica delle imprese aggiudicatarie (
	  if (schedaType.isSetAggiudicatari() && schedaType.getAggiudicatari().sizeOfAggiudicatarioArray() > 0) {
	    mappa.put("arrayAnagraficaAggiudicatari", schedaType.getAggiudicatari().getAggiudicatarioArray());
	  }
	  
	  page.getSession().setAttribute("AGGIUDICAZIONE_ACC_QUADRO", mappa);
	}

	/**
	 * Inizializzazione dell'adesione accordo quadro con i dati dell'aggiudicazione sotto soglia della gara padre
	 * 
	 * @param page
	 * @param schedaType
	 * @param schedaSottosogliaType
	 */
  private void initDatiSottosoglia(PageContext page, SchedaType schedaType,
			SchedaSottosogliaType schedaSottosogliaType) {
  	HashMap<String, Object> mappa = new HashMap<String, Object>();
	  mappa.put("IMPORTO_AGGIUDICAZIONE", schedaSottosogliaType.getAppalto().getIMPORTOAGGIUDICAZIONE());
	  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	  Date date1 = null;
	  try {
	  	date1 = dateFormatter.parse(StringUtils.substringBefore(schedaSottosogliaType.getAppalto().getDATAAGGIUDICAZIONE(), "T"));
	  	mappa.put("DATA_VERB_AGGIUDICAZIONE", UtilityDate.convertiData(date1, UtilityDate.FORMATO_GG_MM_AAAA));
	  } catch (ParseException p) {
			logger.error("Errore nel parsing della data di aggiudicazione letta nell'oggetto SIMOG schedaSottosogliaType", p);
		}
	  if (schedaSottosogliaType.getAppalto().isSetPERCRIBASSOAGG()) {
	    mappa.put("PERC_RIBASSO_AGG", schedaSottosogliaType.getAppalto().getPERCRIBASSOAGG());
	  }
	  if (schedaSottosogliaType.getAppalto().isSetPERCOFFAUMENTO()) {
	  	// Se PERCOFFAUMENTO e' uguale a zero, allora non si inizializza il campo nella form
	  	String temp = schedaSottosogliaType.getAppalto().getPERCOFFAUMENTO();
	  	// Dalla stringa temp si rimuovono i caratteri: '0', '.' e ','
	  	String a = StringUtils.remove(StringUtils.remove(StringUtils.remove(temp,'0'),'.'),',');
	  	// Se la stringa a != "" allora PERCOFFAUMENTO e' diverso da zero
	  	if (StringUtils.isNotEmpty(a)) {
	  		mappa.put("PERC_OFF_AUMENTO", schedaSottosogliaType.getAppalto().getPERCOFFAUMENTO());
	  	}
	  }
	  
	  // Aggiudicatari (solo dati per la W9AGGI)
	  if (schedaSottosogliaType.getAggiudicatariArray() != null && schedaSottosogliaType.getAggiudicatariArray().length > 0) {
	    mappa.put("arrayAggiudicatari", schedaSottosogliaType.getAggiudicatariArray());
	  }
	  // Anagrafica delle imprese aggiudicatarie (
	  if (schedaType.isSetAggiudicatari() && schedaType.getAggiudicatari().sizeOfAggiudicatarioArray() > 0) {
	    mappa.put("arrayAnagraficaAggiudicatari", schedaType.getAggiudicatari().getAggiudicatarioArray());
	  }
	  page.getSession().setAttribute("AGGIUDICAZIONE_ACC_QUADRO", mappa);
	}

  /**
   * Inizializzazione dell'adesione accordo quadro con i dati dell'aggiudicazione escluso della gara padre
   * 
   * @param page
   * @param schedaType
   * @param schedaEsclusoType
   */
	private void initDatiEscluso(PageContext page, SchedaType schedaType, SchedaEsclusoType schedaEsclusoType) {
		HashMap<String, Object> mappa = new HashMap<String, Object>();
	  mappa.put("IMPORTO_AGGIUDICAZIONE", schedaEsclusoType.getAppalto().getIMPORTOAGGIUDICAZIONE());
	  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	  Date date1 = null;
	  try {
	  	date1 = dateFormatter.parse(StringUtils.substringBefore(schedaEsclusoType.getAppalto().getDATAAGGIUDICAZIONE(), "T"));
	  	mappa.put("DATA_VERB_AGGIUDICAZIONE", UtilityDate.convertiData(date1, UtilityDate.FORMATO_GG_MM_AAAA));
	  } catch (ParseException p) {
			logger.error("Errore nel parsing della data di aggiudicazione letta nell'oggetto SIMOG schedaEsclusoType", p);
		}
	  if (schedaEsclusoType.getAppalto().isSetPERCRIBASSOAGG()) {
	    mappa.put("PERC_RIBASSO_AGG", schedaEsclusoType.getAppalto().getPERCRIBASSOAGG());
	  }
	  if (schedaEsclusoType.getAppalto().isSetPERCOFFAUMENTO()) {
	  	// Se PERCOFFAUMENTO e' uguale a zero, allora non si inizializza il campo nella form
	  	String temp = schedaEsclusoType.getAppalto().getPERCOFFAUMENTO();
	  	// Dalla stringa temp si rimuovono i caratteri: '0', '.' e ','
	  	String a = StringUtils.remove(StringUtils.remove(StringUtils.remove(temp,'0'),'.'),',');
	  	// Se la stringa a != "" allora PERCOFFAUMENTO e' diverso da zero
	  	if (StringUtils.isNotEmpty(a)) {
	  		mappa.put("PERC_OFF_AUMENTO", schedaEsclusoType.getAppalto().getPERCOFFAUMENTO());
	  	}
	  }
	  
	  // Aggiudicatari (solo dati per la W9AGGI)
	  if (schedaEsclusoType.getAggiudicatariArray() != null && schedaEsclusoType.getAggiudicatariArray().length > 0) {
	    mappa.put("arrayAggiudicatari", schedaEsclusoType.getAggiudicatariArray());
	  }
	  // Anagrafica delle imprese aggiudicatarie (
	  if (schedaType.isSetAggiudicatari() && schedaType.getAggiudicatari().sizeOfAggiudicatarioArray() > 0) {
	    mappa.put("arrayAnagraficaAggiudicatari", schedaType.getAggiudicatari().getAggiudicatarioArray());
	  }
	  
	  page.getSession().setAttribute("AGGIUDICAZIONE_ACC_QUADRO", mappa);
	}
	
}
