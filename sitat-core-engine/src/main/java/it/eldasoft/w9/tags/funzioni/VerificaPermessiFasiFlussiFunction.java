package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.SituazioneGaraLotto;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto
 */
public class VerificaPermessiFasiFlussiFunction extends AbstractFunzioneTag {

  public VerificaPermessiFasiFlussiFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    // preparazione sqlManager
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
    String result = "true";
    
    try {
      String chiave = (String) pageContext.getRequest().getAttribute("key");
      DataColumnContainer key = new DataColumnContainer(chiave);
      Long codgara = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
      Long codlott = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
      
      // verifico se il lotto è soggetto a monitoraggio multilotto e non è master
      Long cigMasterPresente = (Long) sqlManager.getObject("select count(*) from W9LOTT where codgara=? and codlott=? and CIG_MASTER_ML is not null and CIG_MASTER_ML <> '' and CIG_MASTER_ML <> CIG",
              new Object[]{codgara, codlott});
      pageContext.getSession().setAttribute("isMonitoraggioMultilotto", cigMasterPresente > 0); 
      // controllo la situazione
      Long situazioneLotto = (Long) sqlManager.getObject("select situazione from w9lott where codgara=? and codlott=? ",
          new Object[] { codgara, codlott } );
      Long numappaCONC = (Long) sqlManager.getObject("select max(num_appa) from w9conc where codgara=? and codlott=? ", new Object[] { codgara, codlott } );
      Long numappa = (Long) sqlManager.getObject("select max(num_appa) from w9appa where codgara=? and codlott=? ", new Object[] { codgara, codlott } );
      if (numappaCONC == null) {
    	  numappaCONC = new Long(0);
      }
      if (numappa == null) {
    	  numappa = new Long(0);
      }
      
      // solo se l'aggiudicazione della fase di conclusione coincide con l'ultima fase di aggiudicazione 
      if (numappaCONC.equals(numappa) && situazioneLotto != null && SituazioneGaraLotto.SITUAZIONE_COLLAUDATO.getSituazione() == situazioneLotto.intValue()) {
      	// Il lotto e' collaudato se la fase di collaudo e' stata inviata e nei feedback non ci sono errori
      	Long countCollaudoInviato = (Long) sqlManager.getObject(
      			"select count(*) from V_W9INVIIFASI where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=1 and TINVIO2>0 and NUM_ERRORE=0", 
      			new Object[] { codgara, codlott, new Long(CostantiW9.COLLAUDO_CONTRATTO) } );
      	
      	if (countCollaudoInviato.longValue() > 0) {
      		pageContext.getRequest().setAttribute("situazioneCollaudo", true);
      	}
      }

      ////////////////////////////////////////
      // Determinazione della presenza dei flussi propedeutici (inviati con successo) per le diverse 
      // fasi esistenti nel lotto e set nel request di una hashmap contenente la coppia fase_esecuzione-boolean
      if (numappa == null || (numappa != null && numappa.longValue() == 0)) {
      	numappa = new Long(1);
      }
      
      List<Vector<JdbcParametro>> listaFasi = (List<Vector<JdbcParametro>>) sqlManager.getListVector(
      		"select f.FASE_ESECUZIONE from W9FASI f, TAB1 t where f.FASE_ESECUZIONE=t.TAB1TIP and t.TAB1COD='W3020' "
      		+ "and CODGARA=? and CODLOTT=? and NUM_APPA=? order by t.TAB1NORD asc", new Object[] { codgara, codlott, numappa } );
      
      if (listaFasi != null && listaFasi.size() > 0) {
        
      	HashMap<String,Boolean> mappaFlussiPropedeutici = new HashMap<String,Boolean>();
      	HashMap<String,Boolean> mappaFlussiGiaInviati = new HashMap<String,Boolean>();
      	
      	boolean isConclusioneAnticipata = UtilitySITAT.isConclusioneAnticipata(codgara, codlott, sqlManager);
      	boolean isConfigurazioneVigilanza = UtilitySITAT.isConfigurazioneVigilanza();
      	
	      // valorizzazione hashmap flussi e liberi secondo i criteri di propedeuticita'
	      Set<Long> flussiLiberi = new HashSet<Long>();
	      flussiLiberi.add(new Long(CostantiW9.A24));
	      flussiLiberi.add(new Long(CostantiW9.A22));
	      flussiLiberi.add(new Long(CostantiW9.A05));
	      flussiLiberi.add(new Long(CostantiW9.A04));
	      flussiLiberi.add(new Long(CostantiW9.A21));
	      flussiLiberi.add(new Long(CostantiW9.B03));

        // preparazione variabili per il recupero dei dati dei flussi dal db
        List< ? > listaFlussiInviatiVector = null;
        String sqlFlussiProp = 
      	   "select distinct(f.KEY03) from W9FLUSSI f left outer join W9XML x on f.IDFLUSSO=x.IDFLUSSO "
      	  		 + " left outer join W9FASI fa on f.KEY01=fa.CODGARA and f.KEY02=fa.CODLOTT and f.KEY03=fa.FASE_ESECUZIONE and f.KEY04=fa.NUM "
      	  + "where f.KEY01=? and f.KEY02=? and fa.NUM_APPA=? and COALESCE(x.NUM_ERRORE, 0) = 0 and f.KEY03 is not null and f.TINVIO2 > 0";

        // esecuzione della query che recupera l'elenco dei flussi gia' inviati
        listaFlussiInviatiVector = sqlManager.getListVector(sqlFlussiProp, new Object[] { codgara, codlott, numappa } );
        Set<Long> setFlussiInviati = new HashSet<Long>();
        for (int k = 0; k < listaFlussiInviatiVector.size(); k++) {
          setFlussiInviati.add(new Long(((JdbcParametro) ((List< ? >) listaFlussiInviatiVector.get(k)).get(0)).getStringValue()));
        }
	      
	      for (int i = 0; i < listaFasi.size(); i++) {
	      	Vector<JdbcParametro> vecTemp = listaFasi.get(i);
	      	Long flusso = (Long) vecTemp.get(0).getValue();
	      	
	      	boolean isFaseGiaInviata = setFlussiInviati.contains(flusso);
	      	mappaFlussiGiaInviati.put(flusso.toString(), Boolean.valueOf(isFaseGiaInviata));
	      	
	        if (flussiLiberi.contains(flusso)) {
	        	mappaFlussiPropedeutici.put(flusso.toString(), Boolean.TRUE);
	        } else if (setFlussiInviati.size() > 0) {
	        	if (UtilitySITAT.esistonoFlussiPropedeutici(flusso.intValue(), setFlussiInviati,
	              isConfigurazioneVigilanza, isConclusioneAnticipata, numappa))  {
		        	mappaFlussiPropedeutici.put(flusso.toString(), Boolean.TRUE);
		        } else {
		        	mappaFlussiPropedeutici.put(flusso.toString(), Boolean.FALSE);
		        } 
	        } else {
	        	mappaFlussiPropedeutici.put(flusso.toString(), Boolean.FALSE);
	        }
				}
	      pageContext.setAttribute("mappaFlussiGiaInviati", mappaFlussiGiaInviati, PageContext.REQUEST_SCOPE);
	      pageContext.setAttribute("mappaFlussiPropedeutici", mappaFlussiPropedeutici, PageContext.REQUEST_SCOPE);
      }

    } catch (SQLException e) {
      throw new JspException(
          "Errore nel controllo dei flussi propedeutici necessari all'invio del flusso selezionato.", e);
    } catch (GestoreException g) {
      throw new JspException("Errore nel determinare i valori di COGDARA e CODLOTT", g);
    }
    return result;
  }

}