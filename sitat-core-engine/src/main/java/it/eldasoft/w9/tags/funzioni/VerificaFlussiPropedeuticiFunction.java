package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto
 */
public class VerificaFlussiPropedeuticiFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public VerificaFlussiPropedeuticiFunction() {
    super(2, new Class[] { PageContext.class, String.class });
  }

  /**
   * Implementazione del metodo AbstractFunzioneTag.function.
   * 
   * @param pageContext PageContext
   * @param params Array di Object
   * @return Ritorna "true" se
   * @throws JspException JspException
   */
  @Override
  public final String function(final PageContext pageContext, final Object[] params)
      throws JspException {
    // preparazione sqlManager
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
    GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager", pageContext, GeneManager.class);
    String profilo = (String)this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
    
    // preparazione variabili necessarie alla funzione
    String codGaraProg = "";
    String codLottAvv = "";
    Integer flusso = null;

    // controllo dei parametri passati dalla jsp e smistamento dei dati nelle
    // relative variabili interne
    if (params != null
        && params[0] != null
        && !params[0].toString().trim().equals("")) {
      DataColumnContainer key = new DataColumnContainer(params[1].toString());

      codGaraProg = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().toString();
      codLottAvv = (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().toString();
      String tmp = (key.getColumnsBySuffix("FASE_ESECUZIONE", true))[0].getValue().toString();
      flusso = new Integer(tmp);
    } else {
      return "false";
    }

    // valorizzazione hashmap flussi e liberi secondo i criteri di
    // propedeuticita' sopra elencati nel caso si presenti la necessita'
    // di aggiungere ulteriori fasi/flussi, sara' sufficiente aggiornare
    // solo aggiungendone i valori nel codice sottostante in base alla
    // propedeuticita' ad essi correlata.
    Set<Long> flussiLiberi = new HashSet<Long>();
    flussiLiberi.add(new Long(CostantiW9.A24));
    flussiLiberi.add(new Long(CostantiW9.A22));
    flussiLiberi.add(new Long(CostantiW9.A05));
    flussiLiberi.add(new Long(CostantiW9.A04));
    flussiLiberi.add(new Long(CostantiW9.A21));
    flussiLiberi.add(new Long(CostantiW9.B03));
    
    // controllo sui flussi liberi, se verificato si restituisce direttamente
    // il risultato positivo altrimenti si recupera il valore del flusso in
    // questione direttamente dall'hashmap precedentemente valorizzata
    if (flusso != null && flussiLiberi.contains(new Long(flusso)) ) {
      return "true";
    }

    try {
    	Long numappa = new Long(pageContext.getSession().getAttribute("aggiudicazioneSelezionata").toString());
      // preparazione variabili per il recupero dei dati dei flussi dal db
      List< ? > listaFlussiInviatiVector = null;
     String sqlFlussiProp = 
    	   "select f.KEY03 from W9FLUSSI f left outer join W9XML x on f.IDFLUSSO=x.IDFLUSSO "
    	  		 + " left outer join W9FASI fa on f.KEY01=fa.CODGARA and f.KEY02=fa.CODLOTT and f.KEY03=fa.FASE_ESECUZIONE and f.KEY04=fa.NUM "
    	  + "where f.KEY01=" + codGaraProg
    	  + "  and f.KEY02=" + codLottAvv 
    	  + "  and fa.NUM_APPA=" + numappa 
    	  + "  and COALESCE(x.NUM_ERRORE, 0) = 0 and f.KEY03 is not null and f.TINVIO2 > 0";

      // esecuzione della query che recupera l'elenco dei flussi gia' inviati
      listaFlussiInviatiVector = sqlManager.getListVector(sqlFlussiProp, new Object[] {});

      Set<Long> setFlussiInviati = new HashSet<Long>();
      
      for (int k = 0; k < listaFlussiInviatiVector.size(); k++) {
        setFlussiInviati.add(new Long(((JdbcParametro) ((List< ? >) listaFlussiInviatiVector.get(k)).get(0)).getStringValue()));
      }

      // ciclo sull'elenco dei flussi propedeutici del flusso selezionato.
      // sui flussi propedeutici viene effettuato un confronto con quelli
      // presenti nel risultato della query se anche uno solo dei flussi
      // necessari non viene trovato nei risultati, il controllo non e' verificato
      // altrimenti, se tutti i flussi necessari vengono riscontrati,
      // l'esecuzione procede verificando il controllo
      if (setFlussiInviati.size() > 0) {
        boolean isConclusioneAnticipata = UtilitySITAT.isConclusioneAnticipata(
            Long.parseLong(codGaraProg), Long.parseLong(codLottAvv), sqlManager);
        
        return "" + UtilitySITAT.esistonoFlussiPropedeutici(flusso, setFlussiInviati,
            UtilitySITAT.isConfigurazioneVigilanza(geneManager, profilo),
            isConclusioneAnticipata, numappa);
      } else return "false";

    } catch (SQLException e) {
      throw new JspException(
          "Errore nel controllo dei flussi propedeutici necessari all'invio del flusso selezionato.", e);
    }

  }

}