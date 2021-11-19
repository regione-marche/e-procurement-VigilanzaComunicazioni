package it.eldasoft.w9.utils;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.bean.GaraLottoBean;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.ibm.icu.util.Calendar;

/**
 * Raccolta di metodi di utilita' per la gestione dei controlli sull'esistenza
 * delle gare, dei lotti e delle fasi, sulla loro abilitazione e visualizzazione
 * e sulle condizioni complesse S1, S2, R, E1, SAQ, AAQ ed EA.
 * 
 * @author Stefano.Cestaro
 * 
 */

public class UtilitySITAT {

  /**
   * Costante per la modalita' di realizzazione pari a "Adesione accordo quadro senza confronto competitivo".
   * (W9GARA.TIPO_APP = 11)
   */
  public static final Long ADESIONE_ACCORDO_QUADRO_SENZA_CONFRONTO_COMPETITIVO = new Long(11);

  /**
   * Costante per la modalita' di realizzazione pari a "Stipula Accordo Quadro" (W9GARA.TIPO_APP = 9).
   */
  public static final Long STIPULA_ACCORDO_QUADRO = new Long(9);

  /**
   * Costante per la modalita' di realizzazione pari a "Accordo Quadro" (W9GARA.TIPO_APP = 17).
   */
  public static final Long ACCORDO_QUADRO = new Long(17);

  /**
   * Costante per la modalita' di realizzazione pari a "Convenzione" (W9GARA.TIPO_APP = 18).
   */
  public static final Long CONVENZIONE = new Long(18);
  

  /**
   * Ritorna true se e' abilitato l'invio delle fasi direttamente a SIMOG attraverso la voce 
   * di profilo FUNZ.VIS.ALT.W9.INVIISIMOG, false altrimenti.
   * 
   * @param geneManager
   * @param profiloAttivo
   * @return Ritorna true se per il profilo attivo la voce FUNZ.VIS.ALT.W9.INVIISIMOG e' settata a 1, false altrimenti  
   */
  public static boolean isConfigurazioneVigilanza(GeneManager geneManager, String profiloAttivo) {
    return geneManager.getProfili().checkProtec(profiloAttivo, "FUNZ", "VIS", "ALT.W9.INVIISIMOG");
  }
  
  /**
   * Ritorna true se la property it.eldasoft.simog.tipoAccesso vale 1, false altrimenti
   * @param sqlManager
   * @return Ritorna true se la property it.eldasoft.simog.tipoAccesso vale 1, false altrimenti
   */
  public static boolean isConfigurazioneVigilanza() {
  	return StringUtils.equals("1",ConfigManager.getValore("it.eldasoft.simog.tipoAccesso"));
  }
  
  
  /**
   * Controllo di esistenza di una gara (W9GARA) identificata dal codice
   * IDAVGARA assegnato da SIMOG.
   * 
   * @param idavgara IdAvGara della gara
   * @param sqlManager SqlManager
   * @return Ritorna true se esiste una gara identificata dal codice IDAVGARA
   * @throws SQLException SqlException
   */
  public static boolean existsGara(final String idavgara, final SqlManager sqlManager)
  throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9gara where idavgara = ?",
        new Object[] { idavgara });
    return numeroOccorrenze.intValue() == 1;
  }

  /**
   * Controllo di esistenza di un lotto (W9LOTT) identificato dal codice CIG
   * assegnato da SIMOG.
   * 
   * @param codiceCig Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se esiste il lotto identificato dal codice CIG
   * @throws SQLException SqlException
   */
  public static boolean existsLotto(final String codiceCig, final SqlManager sqlManager)
      throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9lott where cig = ?", new Object[] { codiceCig });
    return numeroOccorrenze.intValue() == 1;
  }

  /**
   * Controllo di esistenza di una pubblicazione (W9PUBBLICAZIONI) identificata dal
   * codice IDAVGARA assegnato da SIMOG.
   * 
   * @param idavgara
   * @param numeroPubblicazione
   * @param sqlManager
   * @return Ritorna true se esiste una pubblicazione, false altrimenti
   * @throws SQLException
   */
  public static boolean existsPubblicazione(final String idavgara, final Long numeroPubblicazione,
      final SqlManager sqlManager) throws SQLException {
    
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from W9PUBBLICAZIONI where CODGARA=(select CODGARA from W9GARA where IDAVGARA=?) and NUM_PUBB=?",
        new Object[] { idavgara, numeroPubblicazione });
    return numeroOccorrenze.intValue() == 1;
  }
  
  /**
   * Ritorna true se il lotto con codice CIG=<i>cig</i> e' un lotto della gara con
   * IdGara=<i>idavgara</i>.
   * 
   * @param idavgara
   * @param codiceCig
   * @param sqlManager
   * @return Ritorna true se il lotto con codice CIG=<i>cig</i> e' un lotto della gara con
   * IdGara=<i>idavgara</i>.
   * @throws SQLException
   */
  public static boolean isCigLottoDellaGara(final String idavgara, final String codiceCig,
      final SqlManager sqlManager) throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9gara g, w9lott l " +
         "where g.codgara=l.codgara " +
           "and g.idavgara = ? " +
           "and l.cig = ?", new Object[] { idavgara, codiceCig });
    return numeroOccorrenze.intValue() == 1;
  }
  
  /**
   * Controllo di esistenza di uno smartCIG per la stessa stazione appaltante
   * 
   * @param codiceSmartCig CIG del lotto
   * @param codein Codice stazione appaltante 
   * @param sqlManager SqlManager
   * @return Ritorna true se esiste già uno smartCIG per la stessa stazione appaltante
   * @throws SQLException SqlException
   */
  public static boolean existsSmartCigSA(final String codiceSmartCig, final String codein, final SqlManager sqlManager)
  {
	  Long numeroOccorrenze = new Long(0);
	  try {
		  numeroOccorrenze = (Long) sqlManager.getObject(
			        "select count(*) from w9lott left join w9gara on w9lott.codgara=w9gara.codgara where w9lott.cig = ? and w9gara.codein = ?", new Object[] { codiceSmartCig, codein });
	  } catch (Exception ex) {
		  ;
	  }
	  return numeroOccorrenze.intValue() > 0;
  }

  /**
   * Controllo di esistenza di una fase su un lotto identificato dal CIG. Nel
   * controllo si considerano tutte le occorrenze di fasi del tipo indicato
   * indipendentemente dal numero progressivo.
   * 
   * @param numappa progressivo dell'appalto
   * @param codiceCig Codice CIG del lotto
   * @param faseEsecuzione Fase di esecuzione
   * @param sqlManager SqlMAnager
   * @return Ritorna true se esiste una fase specifica su un lotto identificato dal CIG
   * @throws SQLException SqlException
   */
  public static boolean existsFase(final int numappa, final String codiceCig, final int faseEsecuzione, 
      final SqlManager sqlManager) throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9fasi, w9lott "
        + "where w9fasi.codgara = w9lott.codgara "
        + "and w9fasi.codlott = w9lott.codlott "
        + "and w9fasi.num_appa = ? and w9lott.cig = ? "
        + "and w9fasi.fase_esecuzione = ? ", new Object[] { new Long(numappa), codiceCig, new Long(faseEsecuzione) });
    return numeroOccorrenze.intValue() > 0;
  }
  
  /**
   * Controllo di esistenza di una fase su un lotto identificato dal CIG. Nel
   * controllo si considera anche il numero progressivo (NUM) della fase.
   * 
   * @param codiceCig Codice CIG del lotto
   * @param faseEsecuzione Fase di esecuzione
   * @param num Numero progressivo della fase
   * @param sqlManager SqlMAnager
   * @return Ritorna true se esiste una fase specifica su un lotto identificato dal CIG
   * @throws SQLException SqlException
   */
  public static boolean existsFase(final int numappa, final String codiceCig, final int faseEsecuzione, final int num,
      final SqlManager sqlManager) throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9fasi, w9lott "
        + "where w9fasi.codgara = w9lott.codgara "
        + "and w9fasi.codlott = w9lott.codlott "
        + "and w9fasi.num_appa = ? and w9lott.cig = ? "
        + "and w9fasi.fase_esecuzione = ? "
        + "and w9fasi.num = ?", new Object[] { numappa, codiceCig, new Long(faseEsecuzione), num });
    return numeroOccorrenze.intValue() > 0;
  }

  /**
   * Controllo di esistenza di una fase su un lotto identificato dal codice gara
   * e dal codice lotto. Nel controllo si considerano tutte le occorrenze di
   * fasi del tipo indicato indipendentemente dal numero progressivo.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param numappa Codice dell'appalto
   * @param faseEsecuzione Fase di esecuzione
   * @param sqlManager SqlManager
   * @return TRUE se la fase esiste, FALSE altrimenti
   * @throws SQLException SqlException
   */
  public static boolean existsFase(final Long codgara, final Long codlott, final Long numappa,
      final int faseEsecuzione, final SqlManager sqlManager) throws SQLException {
	  Long numeroOccorrenze = null;
	  if (numappa != null) {
		  numeroOccorrenze = (Long) sqlManager.getObject(
			        "select count(*) from w9fasi "
			        + "where w9fasi.codgara = ? "
			        + "and w9fasi.codlott = ? "
			        + "and w9fasi.num_appa = ? "
			        + "and w9fasi.fase_esecuzione = ? ", new Object[] { codgara,
			          codlott, numappa, new Long(faseEsecuzione) });
	  } else {
		  numeroOccorrenze = (Long) sqlManager.getObject(
			        "select count(*) from w9fasi "
			        + "where w9fasi.codgara = ? "
			        + "and w9fasi.codlott = ? "
			        + "and w9fasi.fase_esecuzione = ? ", new Object[] { codgara,
			          codlott, new Long(faseEsecuzione) });
	  }
    return numeroOccorrenze.intValue() > 0;
  }

  public static boolean existsFlussoAnagrafica(final Long codgara,
      final int faseEsecuzione, final SqlManager sqlManager) throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9flussi "
        + "where AREA = 2 "
        + "and KEY01 = ? "
        + "and KEY03 = ? ", new Object[] { codgara,
          new Long(faseEsecuzione) });
    return numeroOccorrenze.intValue() > 0;
  }
  
  /**
   * Controllo di esistenza di una fase su un lotto identificato dal codice gara
   * e dal codice lotto. Nel controllo si considera anche il numero progressivo
   * (NUM) della fase.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param faseEsecuzione Fase di esecuzione
   * @param num Numero della fase
   * @param sqlManager SqlManager
   * @return TRUE se la fase esiste, FALSE altrimenti
   * @throws SQLException SqlExeption
   */
  public static boolean existsFase(final Long codgara, final Long codlott,
      final int faseEsecuzione, final Long num, final SqlManager sqlManager)
  throws SQLException {
    return UtilitySITAT.existsFase(codgara, codlott, null, faseEsecuzione, sqlManager);
  }

  /**
   * Controllo dell'esistenza di una fase in almeno un lotto di una gara.
   * 
   * @param codgara Codice della gara
   * @param faseEsecuzione Fase di esecuzione
   * @param sqlManager SqlManager
   * @return Ritorna true se esiste una fase in almeno un lotto di una gara
   * @throws SQLException SqlException
   */
  public static boolean existsFaseInteraGara(final Long codgara, final int faseEsecuzione,
      final SqlManager sqlManager) throws SQLException {
	  Long numappa = (Long)sqlManager.getObject("select MAX(NUM_APPA) from W9APPA where CODGARA=?", new Object[] { codgara });
      if (numappa == null) {
      	numappa = new Long(1);
      }
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9fasi "
        + "where w9fasi.codgara = ? "
        + "and w9fasi.fase_esecuzione = ? and w9fasi.num_appa = ?", new Object[] { codgara,
            new Long(faseEsecuzione), numappa });
    return numeroOccorrenze.intValue() > 0;
  }

  /**
   * Restituisce la lista di tutte le fasi possibili (ricavate dal tabellato
   * W3020). Ad ogni tipologia di fase e' associato un booleano che definisce se
   * per la gara ed il lotto considerato e' stata inserita almeno una occorrenza
   * in W9FASI
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return Ritorna la lista di tutte le fasi possibili (ricavate dal tabellato W3020)
   * @throws SQLException SqlException
   */
  public static List<Object[]> listaExistsFasi(final Long codgara, final Long codlott,
      final SqlManager sqlManager) throws SQLException {
    List<Object[]> listaExistsFasi = new Vector<Object[]>();
    List< ? > datiTAB1 = sqlManager.getListVector(
        "select tab1tip from tab1 where tab1cod = ? and tab1tip not in (988,989,990,991,992)",
        new Object[] { "W3020" });
    if (datiTAB1 != null && datiTAB1.size() > 0) {
      for (int i = 0; i < datiTAB1.size(); i++) {
        Long faseEsecuzione = (Long) SqlManager.getValueFromVectorParam(
            datiTAB1.get(i), 0).getValue();
        Long numappa = (Long)sqlManager.getObject("select MAX(NUM_APPA) from W9APPA where CODGARA=? and CODLOTT=?", new Object[] { codgara, codlott });
        if (numappa == null) {
        	numappa = new Long(1);
        }
        boolean existFase = UtilitySITAT.existsFase(codgara, codlott, numappa, faseEsecuzione.intValue(), sqlManager);
        listaExistsFasi.add(new Object[] {
            "fase_" + faseEsecuzione.toString(), existFase });
      }
    }
    return listaExistsFasi;
  }

  /**
   * Restituisce la lista di tutte le fasi possibili (ricavate dal tabellato
   * W3020). Ad ogni tipologia di fase e' associato un booleano che definisce se
   * per la gara (indipendentemente dal lotto) e' stata inserita almeno una
   * occorrenza in W9FASI
   * 
   * @param codgara Codice della gara
   * @param sqlManager SqlManager
   * @return Ritorna la lista di tutte le fasi possibili
   * @throws SQLException SqlException
   */
  public static List<Object[]> listaExistsFasiInteraGara(final Long codgara,
      final SqlManager sqlManager) throws SQLException {

    List<Object[]> listaExistsFasiInteraGara = new Vector<Object[]>();
    List< ? > datiTAB1 = sqlManager.getListVector(
        "select tab1tip from tab1 where tab1cod = ? and tab1tip not in (988,989,990,991,992)",
        new Object[] { "W3020" });
    if (datiTAB1 != null && datiTAB1.size() > 0) {
      for (int i = 0; i < datiTAB1.size(); i++) {
        Long faseEsecuzione = (Long) SqlManager.getValueFromVectorParam(
            datiTAB1.get(i), 0).getValue();
        boolean existFaseInteraGara = existsFaseInteraGara(codgara,
            faseEsecuzione.intValue(), sqlManager);
        listaExistsFasiInteraGara.add(new Object[] {
            "fase_" + faseEsecuzione.toString(), existFaseInteraGara });
      }
    }
    return listaExistsFasiInteraGara;
  }
  
  /** 
   * Ritorna il codice CIG del lotto a partire da CODGARA e CODLOTT.
   * @param codiceGara
   * @param codiceLotto
   * @param sqlManager
   * @return
   * @throws SQLException
   */
  public static String getCIGLotto(final Long codiceGara, final Long codiceLotto,
      SqlManager sqlManager) throws SQLException {
    return (String) sqlManager.getObject("select CIG from W9LOTT where CODGARA=? and CODLOTT=?",
        new Object[] { codiceGara, codiceLotto } );
  }
  
  /** 
   * Ritorna il progressivo dell'aggiudicazione a cui la fase fa riferimento.
   * @param garaLottoBean
   * @param sqlManager
   * @return
   * @throws SQLException
   */
  public static Long getNUMAPPA(final GaraLottoBean garaLottoBean, SqlManager sqlManager) throws SQLException {
    return (Long) sqlManager.getObject("select NUM_APPA from W9FASI where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?",
        new Object[] { garaLottoBean.getCodiceGara(), garaLottoBean.getCodiceLotto(), garaLottoBean.getDatiComuniXml().getCodiceFase(), garaLottoBean.getDatiComuniXml().getProgressivoFase() } );
  }
  /**
   * Ritorna l'array dei codici CIG dei lotti associati alla gara con Numero della gara pari a <i>idAvGara</i>.
   * 
   * @param idAvGara
   * @param sqlManager
   * @return
   * @throws SQLException
   */
  public static HashSet<String> getCIGbyIdAvGara(final String idAvGara, SqlManager sqlManager)
      throws SQLException {
    List< ? > listaCig = sqlManager.getListVector(
        "select CODLOTT, CIG from W9LOTT where CODGARA=? order by CODLOTT asc",
        new Object[] { idAvGara, } );
    if (listaCig != null && listaCig.size() > 0) {
      HashSet<String> setCig = new HashSet<String>(); 
      
      for (int i=0; i < listaCig.size(); i++) {
        Vector< ? > vector = (Vector< ? >) listaCig.get(i);
        setCig.add((String) ((JdbcParametro) vector.get(1)).getValue());
      }
      
      return setCig;
    } else {
      return null;
    }
  }
  
  public static HashMap<String, Long> getCodGaraCodLottByCIG(final String codiceCIG,
      SqlManager sqlManager) throws SQLException {

    HashMap<String, Long> hm = null;
    Vector<?> temp = sqlManager.getVector("select CODGARA, CODLOTT from W9LOTT where CIG=?",
        new Object[] { codiceCIG } );
    
    if (temp != null) {
      hm = new HashMap<String, Long>();
      hm.put("CODGARA", (Long) SqlManager.getValueFromVectorParam(temp, 0).getValue());
      hm.put("CODLOTT", (Long) SqlManager.getValueFromVectorParam(temp, 1).getValue());
    }
    
    return hm;
  }
  
  public static Long getCodGaraByIdAvGara(final String idAvGAra, SqlManager sqlManager) throws SQLException {
    return (Long) sqlManager.getObject("select CODGARA from W9GARA where IDAVGARA=?",
        new Object[] { idAvGAra } );
  }
  
  /**
   * Test sulla condizione S1. Restituisce TRUE se l'importo totale del lotto e'
   * maggiore di 20000 euro per forniture o servizi oppure se l'importo totale
   * del lotto e' maggiore di 40000 euro per lavori
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se S1
   * @throws SQLException SqlException
   */
  /*public static boolean isS1(final Long codgara, final Long codlott, final SqlManager sqlManager)
  throws SQLException {
    
    //Double importoTot = getImportoTotaleLotto(codgara, codlott, sqlManager);
    //String tipoContratto = getTipoContrattoLotto(codgara, codlott, sqlManager);
    //return isS1(importoTot, tipoContratto);
    //return isS1(importoTot);
  }*/

  /**
   * Test sulla condizione S1 a partire dal codice CIG del lotto.
   * Restituisce TRUE se l'importo totale del lotto e' maggiore di 20000 euro per forniture
   * o servizi oppure se l'importo totale del lotto e' maggiore di 40000 euro per lavori
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se S1
   * @throws SQLException SqlException
   */
  /*public static boolean isS1(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {

    Double importoTot = getImportoTotaleLotto(codiceCIG, sqlManager);
    String tipoContratto = getTipoContrattoLotto(codiceCIG, sqlManager);
    return isS1(importoTot, tipoContratto);
    //return isS1(importoTot);
  }*/

  /**
   * Test sulla condizione S1. Restituisce TRUE se l'importo totale del lotto e'
   * maggiore di 20000 euro per forniture o servizi oppure se l'importo totale
   * del lotto e' maggiore di 40000 euro per lavori
   * 
   * @param importoTot Importo totale del lotto
   * @param tipoContratto Tipo di contratto
   * @return Ritorna true se S1
   */
  /*private static boolean isS1(final Double importoTot) {
    boolean isS1 = false;
    if (importoTot != null) {
      if (importoTot.doubleValue() > 40000) {
        isS1 = true;
      }
    }
    return isS1;
  }*/

  /**
   * Test sulla condizione S2. Restituisce TRUE se l'importo totale del lotto (W9LOTT.IMPORTO_TOT)
   * e' maggiore di 40000 euro e il lotto non e' un lotto ex sottosoglia (W9LOTT.EXSOTTOSOGLIA = '2' o null)
   * e il lotto non e' escluso (W9LOTT.ART_E1 = '2' o null)
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se l'importo totale del lotto e' maggiore di 40000 euro e il lotto non e' un lotto ex sottosoglia
   * @throws SQLException SqlException
   */
  public static boolean isS2(final Long codgara, final Long codlott, final SqlManager sqlManager) throws SQLException {
    boolean isExSottoSoglia = UtilitySITAT.isLottoExSottoSoglia(codgara, codlott, sqlManager); 
    Double importoTot = UtilitySITAT.getImportoTotaleLotto(codgara, codlott, sqlManager);
    boolean isE1 = UtilitySITAT.isE1(codgara, codlott, sqlManager);
    return isS2(importoTot, isExSottoSoglia, isE1);
  }

  /**
   * Test sulla condizione S2. Restituisce TRUE se l'importo totale del lotto (W9LOTT.IMPORTO_TOT)
   * e' maggiore di 40000 euro e il lotto non e' un lotto ex sottosoglia (W9LOTT.EXSOTTOSOGLIA = '2' o null)
   * e il lotto non e' escluso (W9LOTT.ART_E1 = '2' o null) 
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se l'importo totale del lotto e' maggiore di 40000 euro e il lotto non e' un lotto ex sottosoglia
   * @throws SQLException SqlException
   */
  public static boolean isS2(final String codiceCIG, final SqlManager sqlManager) throws SQLException {
    boolean isExSottoSoglia = UtilitySITAT.isLottoExSottoSoglia(codiceCIG, sqlManager);
    Double importoTot = UtilitySITAT.getImportoTotaleLotto(codiceCIG, sqlManager);
    boolean isE1 = UtilitySITAT.isE1(codiceCIG, sqlManager);
    return isS2(importoTot, isExSottoSoglia, isE1);
  }
  
  /**
   * Test sulla condizione S2. Restituisce TRUE se l'importo totale del lotto e' maggiore di 40000 euro e il lotto
   * non e' un ex sottosoglia e il lotto non e' escluso.
   * 
   * @param importoTot Importo totale del lotto
   * @param isExSottosoglia is lotto ex sottosoglia
   * @return Ritorna true se l'importo totale del lotto e' maggiore di 40000 euro
   */
  private static boolean isS2(final Double importoTot, boolean isExSottoSoglia, boolean isE1) {
    boolean isS2 = false;
    if (importoTot != null && importoTot.doubleValue() >= 40000 && !isExSottoSoglia && !isE1) {
      isS2 = true;
    }
    return isS2;
  }

  /**
   * Ritorna true se l'importo del lotto e' sopra i 40000 euro, false altrimenti.
   * 
   * @param codiceCIG
   * @param sqlManager
   * @return
   * @throws SQLException
   */
  public static boolean isImportoLottoSoprasoglia(final String codiceCIG, final SqlManager sqlManager) throws SQLException {
    Double importoTot = UtilitySITAT.getImportoTotaleLotto(codiceCIG, sqlManager);
    if (importoTot != null && importoTot.doubleValue() >= 40000) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Ritorna true se l'importo del lotto e' sopra i 40000 euro, false altrimenti.
   * 
   * @param codgara
   * @param codlott
   * @param sqlManager
   * @return
   * @throws SQLException
   */
  public static boolean isImportoLottoSoprasoglia(final Long codgara, final Long codlott, final SqlManager sqlManager) throws SQLException {
    Double importoTot = UtilitySITAT.getImportoTotaleLotto(codgara, codlott, sqlManager);
    if (importoTot != null && importoTot.doubleValue() >= 40000) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Test is lotto ex sotto soglia. Ritorna true se il lotto ha il campo W9LOTT.EXSOTTOSOGLIA valorizzato a 1.
   * 
   * @param codiceCIG
   * @param sqlManager
   * @return Ritorna true se il lotto ha il campo W9LOTT.EXSOTTOSOGLIA valorizzato a 1, false altrimenti.
   * @throws SQLException 
   */
  public static boolean isLottoExSottoSoglia(final Long codgara, final Long codlott,
      final SqlManager sqlManager) throws SQLException {
    boolean isExSottoSoglia = false;
    
    String exSottoSoglia = (String) sqlManager.getObject(
        "select EXSOTTOSOGLIA from W9LOTT where CODGARA=? and CODLOTT=? ",
        new Object[] { codgara, codlott });
    if ("1".equals(exSottoSoglia)) {
      isExSottoSoglia = true;
    }
    return isExSottoSoglia;
  }
  
  /**
   * Test is lotto ex sotto soglia. Ritorna true se il lotto ha il campo W9LOTT.EXSOTTOSOGLIA valorizzato a 1.
   * 
   * @param codiceCIG
   * @param sqlManager
   * @return Ritorna true se il lotto ha il campo W9LOTT.EXSOTTOSOGLIA valorizzato a 1, false altrimenti.
   * @throws SQLException 
   */
  public static boolean isLottoExSottoSoglia(final String codiceCIG, final SqlManager sqlManager) throws SQLException {
    boolean isExSottoSoglia = false;
    
    String exSottoSoglia = (String) sqlManager.getObject(
        "select EXSOTTOSOGLIA from W9LOTT where CIG=? ",
        new Object[] { codiceCIG });
    
    if ("1".equals(exSottoSoglia)) {
      isExSottoSoglia = true;
    }
    return isExSottoSoglia;
  }
  
  /**
   * Test sulla condizione S2. Restituisce TRUE se l'importo totale del lotto e' maggiore di 500000 euro.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se l'importo totale del lotto e' maggiore di 500000 euro
   * @throws SQLException SqlException
   */
  public static boolean isS3(final Long codgara, final Long codlott, final SqlManager sqlManager)
  throws SQLException {
    Double importoTot = UtilitySITAT.getImportoTotaleLotto(codgara, codlott, sqlManager);
    return isS3(importoTot);
  }

  /**
   * Test sulla condizione S2. Restituisce TRUE se l'importo totale del lotto e' maggiore di 500000 euro.
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se l'importo totale del lotto e' maggiore di 40000 euro
   * @throws SQLException SqlException
   */
  public static boolean isS3(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {
    Double importoTot = UtilitySITAT.getImportoTotaleLotto(codiceCIG, sqlManager);
    return isS3(importoTot);
  }

  /**
   * Test sulla condizione S3. Restituisce TRUE se l'importo totale del lotto e' maggiore di 500000 euro.
   * 
   * @param importoTot Importo totale del lotto
   * @return Ritorna true se l'importo totale del lotto e' maggiore di 500000 euro
   * @throws SQLException
   */
  private static boolean isS3(final Double importoTot) {
    boolean isS3 = false;
    if (importoTot != null && importoTot.doubleValue() >= 500000) {
      isS3 = true;
    }
    return isS3;
  }
  
  
  /**
   * Test sulla condizione R. Restituisce TRUE se Manodopera/Posa in opera vale Si.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return  Ritorna true se Manodopera/Posa in opera vale Si
   * @throws SQLException SqlException
   */
  public static boolean isR(final Long codgara, final Long codlott, final SqlManager sqlManager)
  throws SQLException {
    String manod = (String) sqlManager.getObject("select manod from w9lott "
        + "where w9lott.codgara = ? and w9lott.codlott = ? ",
        new Object[] { codgara, codlott });
    return isR(manod);
  }

  /**
   * Test sulla condizione R a partire dal codice CIG del lotto.
   * Restituisce TRUE se Manodopera/Posa in opera vale Si
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return  Ritorna true se Manodopera/Posa in opera vale Si
   * @throws SQLException SqlException
   */
  public static boolean isR(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {
    String manod = (String) sqlManager.getObject("select manod from w9lott "
        + "where w9lott.cig = ? ", new Object[] { codiceCIG });
    return isR(manod);
  }

  /**
   * Test sulla condizione R. Restituisce TRUE se Manodopera/Posa in opera vale Si.
   * 
   * @param manod Mano d'opera
   * @return Ritorna true se Manodopera/Posa in opera vale Si
   */
  private static boolean isR(final String manod) {
    boolean isR = false;
    if (manod != null && "1".equals(manod)) {
      isR = true;
    }
    return isR;
  }

  /**
   * Test sulla condizione E1. Restituisce TRUE se il campo "Contratto escluso
   * ex art 10, 20, 21, 22, 23, 24, 25, 26 D. Lgs. 163/06?" = "Si"
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se il campo "Contratto escluso ex art 10, 20, 21, 22, 23, 24, 25, 26 D. Lgs. 163/06?" = "Si"
   * @throws SQLException SqlException
   */
  public static boolean isE1(final Long codgara, final Long codlott, final SqlManager sqlManager)
  throws SQLException {
    String artE1 = (String) sqlManager.getObject("select art_e1 from w9lott "
        + "where w9lott.codgara = ? "
        + "and w9lott.codlott = ? ", new Object[] { codgara, codlott });
    return isE1(artE1);
  }

  /**
   * Test sulla condizione E1 a partire dal codice CIG del lotto.
   * Restituisce TRUE se il campo "Contratto escluso ex art 10, 20, 21, 22,
   * 23, 24, 25, 26 D. Lgs. 163/06?" = "Si"
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se il campo "Contratto escluso ex art 10, 20, 21, 22, 23, 24, 25, 26 D. Lgs. 163/06?" = "Si"
   * @throws SQLException SqlException
   */
  private static boolean isE1(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {
    String artE1 = (String) sqlManager.getObject("select art_e1 from w9lott "
        + "where w9lott.cig = ? ", new Object[] { codiceCIG });
    return isE1(artE1);
  }

  /**
   * Test sulla condizione E1. Restituisce TRUE se il campo "Contratto escluso ex art 10, 20, 21, 22, 23, 24, 25, 26 D. Lgs. 163/06?" = "Si"
   * 
   * @param artE1 Campo ART_E1
   * @return Ritorna true se il campo "Contratto escluso ex art 10, 20, 21, 22, 23, 24, 25, 26 D. Lgs. 163/06?" = "Si"
   */
  private static boolean isE1(final String artE1) {
    boolean isArtE1 = false;
    if ("1".equals(artE1)) {
      isArtE1 = true;
    }
    return isArtE1;
  }
  
  /**
   * Test sulla condizione SAQ. Restituisce TRUE se la modalita' di realizzazione (W9GARA.TIPO_APP) e' Stipula Accordo Quadro (TIPO_APP = 9).
   * 
   * @param codgara Codice della gara
   * @param sqlManager SqlManager
   * @return Ritorna true se la modalita' di realizzazione (W9GARA.TIPO_APP) e' Stipula Accordo Quadro (TIPO_APP = 9)
   * @throws SQLException SqlException
   */
  public static boolean isSAQ(final Long codgara, final SqlManager sqlManager)
  throws SQLException {
    return isSAQ(UtilitySITAT.getTipoAppalto(codgara, sqlManager));
  }

  /**
   * Test se la modalita' di realizzazione e' Stipula Accordo Quadro (SAQ) a partire dal codice CIG (TIPO_APP = 9).
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se la modalita' di realizzazione e' Stipula Accordo Quadro (SAQ) a partire dal codice CIG (TIPO_APP = 9)
   * @throws SQLException SqlException
   */
  public static boolean isSAQ(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {
    return isSAQ(UtilitySITAT.getTipoAppalto(codiceCIG, sqlManager));
  }

  /**
   * Restituisce TRUE se la modalita' di realizzazione (W9GARA.TIPO_APP) e' Stipula Accordo Quadro, Accordo quadro o Convenzione
   * (TIPO_APP = 9 o 17 o 18) Tabellato TAB1COD = W3999)
   * @param tipoApp Tipo Appalto
   * @return Ritorna true se la modalita' di realizzazione (W9GARA.TIPO_APP) e' Stipula Accordo Quadro, Accordo quadro o Convenzione (TIPO_APP = 9, 17, 18)
   */
  private static boolean isSAQ(final Long tipoApp) {
    boolean isSAQ = false;
    if (tipoApp != null && (UtilitySITAT.STIPULA_ACCORDO_QUADRO.equals(tipoApp)
          || UtilitySITAT.ACCORDO_QUADRO.equals(tipoApp)
              || UtilitySITAT.CONVENZIONE.equals(tipoApp))) {
      isSAQ = true;
    }
    return isSAQ;
  }

  /**
   * Metodo per testare con JUnit l'omonimo metodo privato isSAQ, che altrimenti non sarebbe testabile.
   * 
   * @param tipoApp Tipo Appalto
   * @return Ritorna true se la modalita' di realizzazione (W9GARA.TIPO_APP) e' Stipula Accordo Quadro (TIPO_APP = 9)
   */
  protected static boolean isSAQ_(final Long tipoApp) {
    return isSAQ(tipoApp);
  }
  
  /**
   * Test se la modalita' di realizzazione e' Adesione Accordo Quadro senza
   * confronto competitivo (AAQ).
   * (TIPO_APP = 11)
   * 
   * @param codgara Codice della gara
   * @param sqlManager SqlManager
   * @return Ritorna true se la modalita' di realizzazione e' Adesione Accordo Quadro senza
   * confronto competitivo (AAQ)
   * @throws SQLException SqlException
   */
  public static boolean isAAQ(final Long codgara, final SqlManager sqlManager)
  throws SQLException {
    return isAAQ(UtilitySITAT.getTipoAppalto(codgara, sqlManager));
  }

  /**
   * Test se la modalita' di realizzazione e' Adesione Accordo Quadro senza
   * confronto competitivo a partire dal codice CIG (AAQ). (TIPO_APP = 11)
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se la modalita' di realizzazione e' Adesione Accordo Quadro senza confronto competitivo a partire dal codice CIG (AAQ). (TIPO_APP = 11)
   * @throws SQLException SqlException
   */
  public static boolean isAAQ(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {
    return isAAQ(UtilitySITAT.getTipoAppalto(codiceCIG, sqlManager));
  }

  /**
   * Restituisce TRUE se la modalita' di realizzazione e' "Adesione accordo quadro senza confronto competitivo" (TIPO_APP = 11).
   * 
   * @param tipoApp Tipo appalto
   * @return Ritorna true se il tipo appalto e' uguale a 11
   */
  private static boolean isAAQ(final Long tipoApp) {
    boolean isAAQ = false;

    if (tipoApp != null && UtilitySITAT.ADESIONE_ACCORDO_QUADRO_SENZA_CONFRONTO_COMPETITIVO.equals(tipoApp)) {
      isAAQ = true;
    }
    return isAAQ;
  }

  /**
   * Test sulla condizione EA. Restituisce TRUE se il lotto e' aggiudicato ossia
   * se nella fase "Esito" il lotto e' indicato come aggiudicato (W9ESITO.ESITO_PROCEDURA = 1)
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se il lotto e' aggiudicato
   * @throws SQLException SqlException
   */
  public static boolean isEA(final Long codgara, final Long codlott, final SqlManager sqlManager)
  throws SQLException {
    Long numOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from W9ESITO where CODGARA=? and CODLOTT=? and ESITO_PROCEDURA in (2,3,4)", new Object[] { codgara, codlott });
    if (numOccorrenze > 0) {
    	return false;
    } else {
      return true && !UtilitySITAT.isD4(codgara, sqlManager);
    }
  }

  /**
   * Test sulla condizione EA. Restituisce TRUE se il lotto e' aggiudicato ossia
   * se nella fase "Esito" il lotto e' indicato come aggiudicato (W9ESITO.ESITO_PROCEDURA = 1) 
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se il lotto e' aggiudicato
   * @throws SQLException SqlException
   */
  public static boolean isEA(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {
    Long numOccorrenze = (Long) sqlManager.getObject(
        "select count(e.CODGARA) from W9ESITO e, W9LOTT l where e.CODGARA=l.CODGARA and e.CODLOTT=l.CODLOTT and l.CIG=?"
    				+ " and e.ESITO_PROCEDURA in (2,3,4)",
        new Object[] { codiceCIG });
    if (numOccorrenze > 0) {
    	return false; 
    } else {
    	
      return true && !UtilitySITAT.isD4(
      		(Long) sqlManager.getObject("select CODGARA from W9LOTT where CIG=?", new Object[] { codiceCIG }), sqlManager);
    }
  }

  
  /**
   * Ritorna se le fasi di conclusione o di conclusione semplificata sono delle interruzioni anticipate.  
   * 
   * @param sqlManager
   * @param codiceGara
   * @param codiceLotto
   * @return Ritorna true se esiste la fase conclusione con il campo W9CONC.INTANTIC valorizzato a '1'
   */
  public static boolean isConclusioneAnticipata(final long codiceGara, final long codiceLotto, final SqlManager sqlManager)
      throws SQLException {
  	Long numappa = (Long) sqlManager.getObject(
  			"select MAX(NUM_APPA) from W9APPA where CODGARA=? and CODLOTT=?", 
  			new Object[] { codiceGara, codiceLotto });
  	
    Long esisteConclusioneAnticipata = (Long) sqlManager.getObject(
        "select count(*) from W9CONC where CODGARA=? and CODLOTT=? and NUM_APPA=? and INTANTIC='1'", 
        new Object[] { codiceGara, codiceLotto, numappa });
    
    boolean isConclusioneAnticipata = false;
    if (esisteConclusioneAnticipata != null && (new Long(1)).equals(esisteConclusioneAnticipata)) {
      isConclusioneAnticipata = true;
    }
    return isConclusioneAnticipata;
  }

  /**
   * Test sulla condizione AII. Restituisce TRUE se la gara e' per un intervento
   * di ricostruzione alluvione Lunigiana e Elba (W9GARA.RIC_ALLUV = '1').
   * 
   * @param codgara Codice della gara
   * @param sqlManager SqlManager
   * @return Ritorna true se il campo W9GARA.RIC_ALLUV='1', false altrimenti
   * @throws SQLException SQLException
   */
  public static boolean isAII(final Long codgara, final SqlManager sqlManager) throws SQLException {
    boolean result = false;
    String ricostruzioneAlluzione = (String) sqlManager.getObject(
        "select RIC_ALLUV from W9GARA where CODGARA=?", new Object[]{codgara});

    if ("1".equals(ricostruzioneAlluzione)) {
      result = true;
    }
    return result;
  }

  /**
   * Test sulla condizione AII. Restituisce TRUE se la gara e' per un intervento
   * di ricostruzione alluvione Lunigiana e Elba (W9GARA.RIC_ALLUV = '1').
   * 
   * @param cig Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se il campo W9GARA.RIC_ALLUV='1', false altrimenti
   * @throws SQLException SQLException
   */
  public static boolean isAII(final String cig, final SqlManager sqlManager) throws SQLException {
    boolean result = false;
    String ricostruzioneAlluzione = (String) sqlManager.getObject(
        "select g.RIC_ALLUV from W9LOTT l, W9GARA g where l.CODGARA=g.CODGARA and l.CIG=?", new Object[]{cig});

    if ("1".equals(ricostruzioneAlluzione)) {
      result = true;
    }
    return result;
  }

  /**
   * Test sulla condizione Ord Restituisce TRUE se il flag ente speciale e' uguale a 'O'
   *  
   * @param codgara Codice della gara
   * @param sqlManager SqlManager
   * @return Ritorna true se il campo W9LOTT.FLAG_ENTE_SPECIALE='O', false altrimenti
   * @throws SQLException SQLException
   */
  public static boolean isOrd(final Long codgara, final SqlManager sqlManager) throws SQLException {
    boolean result = false;
    String settoriOrdinari = (String) sqlManager.getObject(
        "select l.FLAG_ENTE_SPECIALE from W9LOTT l, W9GARA g where l.CODGARA=g.CODGARA and g.CODGARA=?", new Object[]{codgara});

    if ("O".equals(settoriOrdinari)) {
      result = true;
    } else {
    	Long versioneSimog = (Long) sqlManager.getObject(
    	        "select VER_SIMOG from W9GARA where CODGARA=?", new Object[] { codgara } );
    	result = (versioneSimog != null && versioneSimog>=4);
    }
    return result;
  }

  /**
   * Test sulla condizione Ord Restituisce TRUE se il flag ente speciale e' uguale a 'O'.
   * 
   * @param cig Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna true se il campo W9LOTT.FLAG_ENTE_SPECIALE='O', false altrimenti
   * @throws SQLException SQLException
   */
  public static boolean isOrd(final String cig, final SqlManager sqlManager) throws SQLException {
    boolean result = false;
    String settoriOrdinari = (String) sqlManager.getObject(
        "select l.FLAG_ENTE_SPECIALE from W9LOTT l where l.CIG=?", new Object[]{cig});

    if ("O".equals(settoriOrdinari)) {
      result = true;
    } else {
    	Long versioneSimog = (Long) sqlManager.getObject(
    	        "select W9GARA.VER_SIMOG from W9LOTT left join W9GARA on W9LOTT.CODGARA = W9GARA.CODGARA where W9LOTT.CIG=?", new Object[] { cig } );
    	result = (versioneSimog != null && versioneSimog>=4);
    }
    return result;
  }
  
  
  /**
   * Restituisce il tipo appalto (W9GARA.TIPO_APP) a partire da CODGARA.
   * 
   * @param codgara Codice della gara
   * @param sqlManager SqlManager
   * @return Ritorna W9GARA.TIPO_APP a partire da CODGARA
   * @throws SQLException SqlException
   */
  private static Long getTipoAppalto(final Long codgara, final SqlManager sqlManager)
  throws SQLException {

    return (Long) sqlManager.getObject("select tipo_app from w9gara "
        + "where w9gara.codgara = ?", new Object[] { codgara });
  }

  /**
   * Restituisce il tipo appalto (W9GARA.TIPO_APP) a partire dal CIG di un lotto della gara.
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna il tipo di appalto della gara
   * @throws SQLException SqlException
   */
  private static Long getTipoAppalto(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {

    return (Long) sqlManager.getObject("select tipo_app from w9gara, w9lott "
        + "where w9gara.codgara = w9lott.codgara and w9lott.cig = ?",
        new Object[] { codiceCIG });
  }

  /**
   * Restituisce l'importo totale del lotto (W9LOTT.IMPORTO_TOT).
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return Ritorna l'importo totale del lotto
   * @throws SQLException SqlException
   */
  private static Double getImportoTotaleLotto(final Long codgara, final Long codlott,
      final SqlManager sqlManager) throws SQLException {
    Double importoTot = (Double) sqlManager.getObject(
        "select importo_tot from w9lott "
        + "where w9lott.codgara = ? "
        + "and w9lott.codlott = ? ", new Object[] { codgara, codlott });
    return importoTot;
  }

  /**
   * Restituisce l'importo totale del lotto a partire dal CIG del lotto (W9LOTT.IMPORTO_TOT).
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna l'importo totale del lotto
   * @throws SQLException SqlException
   */
  private static Double getImportoTotaleLotto(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {
    Double importoTot = (Double) sqlManager.getObject(
        "select importo_tot from w9lott where w9lott.cig = ? ",
        new Object[] { codiceCIG });
    return importoTot;
  }

  /** Restituisce il tipo di contratto del lotto (W9LOTT.TIPO_CONTRATTO).
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param sqlManager SqlManager
   * @return Ritorna il tipo di contratto del lotto
   * @throws SQLException SqlException
   */
  public static String getTipoContrattoLotto(final Long codgara, final Long codlott,
      final SqlManager sqlManager) throws SQLException {
    String tipoContratto = (String) sqlManager.getObject(
        "select tipo_contratto from w9lott "
        + "where w9lott.codgara = ? "
        + "and w9lott.codlott = ? ", new Object[] { codgara, codlott });
    return tipoContratto;
  }

  /**
   * Restituisce il tipo di contratto del lotto a partire dal codice CIG (W9LOTT.TIPO_CONTRATTO).
   * 
   * @param codiceCIG Codice CIG del lotto
   * @param sqlManager SqlManager
   * @return Ritorna il tipo di contratto del lotto
   * @throws SQLException SqlException
   */
  /*private static String getTipoContrattoLotto(final String codiceCIG, final SqlManager sqlManager)
  throws SQLException {
    String tipoContratto = (String) sqlManager.getObject(
        "select tipo_contratto from w9lott where w9lott.cig = ? ",
        new Object[] { codiceCIG });
    return tipoContratto;
  }*/

  

  /**
   * Ritorna TRUE se una fase e' gia' stata inviata (cioe' esiste record in W9FLUSSI).
   * 
   * @param codgara
   * @param codlott
   * @param faseEsecuzione
   * @param sqlManager
   * @return Ritorna TRUE se una fase e' gia' stata inviata, false altrimenti
   * @throws SQLException
   *//*
   *
   * Metodo da non usare perche' attualmente non considera il caso della cancellazione
   * e della richiesta di annullamento dell'invio
   *
  public static boolean isFaseInviata(final Long codgara, final Long codlott,
      final int faseEsecuzione, final SqlManager sqlManager)
          throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9flussi "
        + "where w9flussi.key01 = ? "
        + "and w9flussi.key02 = ? "
        + "and w9flussi.key03 = ? ", new Object[] { codgara, codlott,
            new Long(faseEsecuzione) });
    return numeroOccorrenze.intValue() > 0;
  }*/
  
  /**
   * Ritorna TRUE se una fase e' gia' stata inviata (cioe' esiste record in W9FLUSSI).
   * 
   * @param codgara
   * @param codlott
   * @param faseEsecuzione
   * @param num
   * @param sqlManager
   * @return Ritorna TRUE se una fase e' gia' stata inviata, false altrimenti
   * @throws SQLException
   *//*
   *
   * Metodo da non usare perche' attualmente non considera il caso della cancellazione
   * e della richiesta di annullamento dell'invio
   *
  public static boolean isFaseInviata(final Long codgara, final Long codlott,
      final int faseEsecuzione, final Long num, final SqlManager sqlManager)
          throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9flussi "
        + "where w9flussi.key01 = ? "
        + "and w9flussi.key02 = ? "
        + "and w9flussi.key03 = ? "
        + "and w9flussi.key04 = ?", new Object[] { codgara, codlott,
            new Long(faseEsecuzione), num });
    return numeroOccorrenze.intValue() > 0;
  } */
  
  /**
   * Ritorna TRUE se una fase e' gia' stata inviata (cioe' esiste record in W9FLUSSI). 
   * 
   * @param codgara
   * @param codlott
   * @param faseEsecuzione
   * @param sqlManager
   * @return Ritorna <i>true</i> se la fase e' da reinviare, <i>false</i> altrimenti 
   * @throws SQLException
   */
  public static boolean isFaseDaReInviare(final Long codgara, final Long codlott,
      final int faseEsecuzione, final SqlManager sqlManager)
          throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9flussi u, w9fasi a "
        + "where u.key01 = a.codgara "
        + "and u.key02 = a.codlott "
        + "and u.key03 = a.fase_esecuzione "
        + "and u.key01 = ? "
        + "and u.key02 = ? "
        + "and u.key03 = ? "
        + "and (a.daexport <> '1' or a.daexport is null)"
        + "and u.tinvio2 > 0 ", new Object[] { codgara, codlott, new Long(faseEsecuzione) });
    return numeroOccorrenze.intValue() > 0;
  }
  
  /**
   * Ritorna TRUE se una fase e' gia' stata inviata (cioe' esiste record in W9FLUSSI) e
   * il campo W9FASI.DAEXPORT e' diverso da '1'. Funzione usata dalla gene:callFunction 
   * IsFaseDaReinviare, la quale e' invocata all'apertura in modifica di una scheda di 
   * una fase.
   * 
   * @param codgara
   * @param codlott
   * @param faseEsecuzione
   * @param num
   * @param sqlManager
   * @return Ritorna <i>true</i> se la fase e' da reinviare, <i>false</i> altrimenti 
   * @throws SQLException
   */
  public static boolean isFaseDaReInviare(final Long codgara, final Long codlott,
      final int faseEsecuzione, final Long num, final SqlManager sqlManager)
          throws SQLException {
    Long numeroOccorrenze = (Long) sqlManager.getObject(
        "select count(*) from w9flussi u, w9fasi a "
        + "where u.key01 = a.codgara "
        + "and u.key02 = a.codlott "
        + "and u.key03 = a.fase_esecuzione "
        + "and u.key04 = a.num "
        + "and u.key01 = ? "
        + "and u.key02 = ? "
        + "and u.key03 = ? "
        + "and u.key04 = ? "
        + "and (a.daexport <> '1' or a.daexport is null)"
        + "and u.tinvio2 > 0 ", new Object[] { codgara, codlott,
            new Long(faseEsecuzione), num });
    return numeroOccorrenze.intValue() > 0;
  }

  /** Test sulla condizione di visualizzazione. Restituisce TRUE se la fase e'
   * visualizzabile.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param faseEsecuzione Fase di Esecuzione
   * @param sqlManager SqlManager
   * @return Ritorna true se la fase e' visualizzabile, false altrimenti
   * @throws SQLException SqlException
   */
  /*public static boolean isFaseVisualizzabile(final Long codgara, final Long codlott,
      final int faseEsecuzione, final SqlManager sqlManager) throws SQLException {

    boolean isVisualizzabile = false;

    if (faseEsecuzione == CostantiW9.A22) {
      isVisualizzabile = !UtilitySITAT.isAAQ(codgara, sqlManager);
    } else {
      isVisualizzabile = isEA(codgara, codlott, sqlManager)
          && isFaseVisualizzabile_(codgara, codlott, faseEsecuzione, sqlManager);
    }
    return isVisualizzabile;
  }*/

  /**
   * Metodo privato per il test sulla condizione di visualizzazione. In questo
   * test si considerano i controlli sulle fasi diverse da quella di
   * "Esito comunicazione - A22".
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param faseEsecuzione Fase di Esecuzione
   * @param sqlManager SqlManager
   * @return Ritorna true se la fase e' visualizzabile, false altrimenti
   * @throws SQLException SqlException
   */
/*  private static boolean isFaseVisualizzabile_(final Long codgara, final Long codlott,
      final int faseEsecuzione, final SqlManager sqlManager) throws SQLException {

    boolean isVisualizzabile = false;

    String tipoContratto = UtilitySITAT.getTipoContrattoLotto(codgara, codlott, sqlManager);

    switch (faseEsecuzione) {

      case CostantiW9.A05:
        isVisualizzabile = isS2(codgara, codlott, sqlManager)
          && !isE1(codgara, codlott, sqlManager)
          && !isAAQ(codgara, sqlManager);
        break;
  
      case CostantiW9.A04:
        isVisualizzabile = (isE1(codgara, codlott, sqlManager) || !isS2(codgara, codlott,
            sqlManager)) && !isAAQ(codgara, sqlManager);
        break;
  
      case CostantiW9.A20:
        isVisualizzabile = isSAQ(codgara, sqlManager);
        break;
  
      case CostantiW9.A21:
        isVisualizzabile = !isSAQ(codgara, sqlManager)
        && isAAQ(codgara, sqlManager);
        break;
  
      case CostantiW9.A06:
      case CostantiW9.A08:
      case CostantiW9.A11:
      case CostantiW9.A12:
      case CostantiW9.A13:
      case CostantiW9.A14:
        isVisualizzabile = isS2(codgara, codlott, sqlManager)
        && !isE1(codgara, codlott, sqlManager)
        && !isSAQ(codgara, sqlManager);
        break;
  
      case CostantiW9.A07:
        isVisualizzabile = (isE1(codgara, codlott, sqlManager) || !isS2(codgara, codlott,
            sqlManager)) && !isSAQ(codgara, sqlManager);
        break;
  
      case CostantiW9.A09:
        isVisualizzabile = isS2(codgara, codlott, sqlManager)
        && (!isE1(codgara, codlott, sqlManager));
        break;
  
      case CostantiW9.A10:
        isVisualizzabile = (isE1(codgara, codlott, sqlManager) || !isS2(codgara, codlott, sqlManager));
        break;
  
      case CostantiW9.A15:
        /*if (StringUtils.indexOf(tipoContratto, 'L') >= 0) {
          isVisualizzabile = isS1(codgara, codlott, sqlManager)
          && isR(codgara, codlott, sqlManager)
          && !isE1(codgara, codlott, sqlManager);
        } else if ("S".equals(tipoContratto) || "F".equals(tipoContratto)) {
          isVisualizzabile = isS1(codgara, codlott, sqlManager)
          && !isE1(codgara, codlott, sqlManager)
          && (isS2(codgara, codlott, sqlManager) || isR(codgara, codlott,
              sqlManager));
        }*/ /*
        isVisualizzabile = (isS2(codgara, codlott, sqlManager) || isAII(codgara, sqlManager))
            && !isE1(codgara, codlott, sqlManager);
        break;
  
      case CostantiW9.A16:
        if (StringUtils.indexOf(tipoContratto, 'L') >= 0) {
          isVisualizzabile = isS2(codgara, codlott, sqlManager)
          && !isE1(codgara, codlott, sqlManager)
          && !isSAQ(codgara, sqlManager);
        }
        break;
  
      case CostantiW9.B02:
      case CostantiW9.B03:
        isVisualizzabile = !isSAQ(codgara, sqlManager);
        break;
  
      case CostantiW9.B04:
      case CostantiW9.B06:
      case CostantiW9.B07:
      case CostantiW9.B08:
        isVisualizzabile = isR(codgara, codlott, sqlManager)
        && !isSAQ(codgara, sqlManager);
        break;
    default:
      break;

    }
    return isVisualizzabile;
  }*/

  /**
   * Ritorna true se il flusso e' un flusso dell'area 1, cioe' 
   * se e' un flusso per le fasi di gara (Area 1).
   * 
   * @param codiceFlusso Codice del flusso (vedi CostantiW9)
   * @return Ritorna true se il flusso e' un flusso per le fasi di gara, false altrimenti
   */
  public static boolean isArea1(final int codiceFlusso) {
    int areaFlusso = UtilitySITAT.getAreaFlusso(codiceFlusso);
    if (CostantiW9.AREA_FASI_DI_GARA == areaFlusso) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Ritorna true se il flusso e' un flusso dell'area 2, cioe' 
   * se e' il flusso anagrafica gara lotto (Area 2).
   * 
   * @param codiceFlusso Codice del flusso (vedi CostantiW9)
   * @return Ritorna true se il flusso e' anagrafica gara lotto, false altrimenti
   */
  public static boolean isArea2(final int codiceFlusso) {
    int areaFlusso = UtilitySITAT.getAreaFlusso(codiceFlusso);
    if (CostantiW9.AREA_ANAGRAFICA_GARE == areaFlusso || 
        CostantiW9.PUBBLICAZIONE_DOCUMENTI == areaFlusso) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Ritorna true se il flusso e' un flusso dell'area 3, cioe' 
   * se e' il flusso pubblicazione avvisi (Area 3).
   * 
   * @param codiceFlusso Codice del flusso (vedi CostantiW9)
   * @return Ritorna true se il flusso e' pubblicazione avvisi, false altrimenti
   */
  public static boolean isArea3(final int codiceFlusso) {
    int areaFlusso = UtilitySITAT.getAreaFlusso(codiceFlusso);
    if (CostantiW9.AREA_AVVISI == areaFlusso) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Ritorna true se il flusso e' un flusso dell'area 4, cioe' 
   * se e' un flusso per piani triennali o annuali (Area 4).
   * 
   * @param codiceFlusso Codice del flusso (vedi CostantiW9)
   * @return Ritorna true se il flusso e' piano triennale/annuale, false altrimenti
   */
  public static boolean isArea4(final int codiceFlusso) {
    int areaFlusso = UtilitySITAT.getAreaFlusso(codiceFlusso);
    if (CostantiW9.AREA_PROGRAMMA_TRIENNALI_ANNUALI == areaFlusso) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Ritorna true se il flusso e' un flusso dell'area 5, cioe' 
   * se e' il flusso Gara per enti nazionali o sotto i 40000 euro (Area 5).
   * 
   * @param codiceFlusso Codice del flusso (vedi CostantiW9)
   * @return Ritorna true se il flusso e' Gara per enti nazionali o sotto i 40000 euro,
   *         false altrimenti
   */
  public static boolean isArea5(final int codiceFlusso) {
    int areaFlusso = UtilitySITAT.getAreaFlusso(codiceFlusso);
    if (CostantiW9.AREA_GARE_ENTINAZIONALI == areaFlusso) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Ritorna l'area di appartenenza del flusso, a partire dal codice del flusso stesso.
   *  
   * Le aree sono 5:
   * - area 1: fasi di gara;
   * - area 2: anagrafica gara/lotto;
   * - area 3: pubblicazione avvisi
   * - area 4: piani triennali per lavori/annuali per forniture e servizi 
   * - area 5: Gare per enti nazionali o sotto i 40000 euro
   * 
   * @param codiceFlusso Codice del flusso (vedi CostantiW9)
   * @return Ritorna l'area di appartenenza del flusso
   */
  public static int getAreaFlusso(final String codiceFlusso) {
    int codice_Flusso = Integer.parseInt(codiceFlusso);
    return UtilitySITAT.getAreaFlusso(codice_Flusso);
  }
  
  /**
   * Ritorna l'area di appartenenza del flusso, a partire dal codice del flusso stesso.
   *  
   * Le aree sono 5:
   * - area 1: fasi di gara;
   * - area 2: anagrafica gara/lotto;
   * - area 3: pubblicazione avvisi
   * - area 4: piani triennali per lavori/annuali per forniture e servizi 
   * - area 5: Gare per enti nazionali o sotto i 40000 euro
   * 
   * @param codiceFlusso Codice del flusso (vedi CostantiW9)
   * @return Ritorna l'area di appartenenza del flusso
   */
  public static int getAreaFlusso(final int codiceFlusso) {
    int areaInvio = 0;
    
    switch (codiceFlusso) {
    case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
      // Fase aggiudicazione/affidamento (>150.000 euro)
    case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
      // Fase iniziale esecuzione contratto (>150.000 euro)
    case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
      // Fase esecuzione e avanzamento del contratto
    case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
      // Fase di conclusione del contratto (>150.000 euro)
    case CostantiW9.COLLAUDO_CONTRATTO:
      // Fase di collaudo del contratto
    case CostantiW9.SOSPENSIONE_CONTRATTO:
      // Sospensione del contratto
    case CostantiW9.VARIANTE_CONTRATTO:
      // Variante del contratto
    case CostantiW9.ACCORDO_BONARIO:
      // Accordi bonari
    case CostantiW9.SUBAPPALTO:
      // Subappalti
    case CostantiW9.ISTANZA_RECESSO:
      // Istanza di recesso
    case CostantiW9.STIPULA_ACCORDO_QUADRO:
      // Stipula accordo quadro
    case CostantiW9.ADESIONE_ACCORDO_QUADRO:
      // Adesioneaccordo quadro
    case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
      // Fase aggiudicazione/affidamento appalto (<150.000 euro)
    case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:
      // Fase iniziale esecuzione contratto (<150.000 euro)
    case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
      // Fase di conclusione del contratto (<150.000 euro)
    //case CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA:
      // Misure aggiuntive e migliorative sicurezza
    case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI:
      // Scheda segnalazione infortuni
    case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:
      // Inadempienze predisposizioni sicurezza e regolarita' lavoro
    case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:
      // Esito negativo verifica regolarita' contributiva ed assicurativa
    case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:
      // Esito negativo verifica idoneita' tecnico-professionale
      // dell'impresa aggiudicataria
    case CostantiW9.APERTURA_CANTIERE: // Apertura cantiere
    case CostantiW9.COMUNICAZIONE_ESITO: // Comunicazione esito
    case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI: // Elenco imprese invitate/partecipanti
    case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO: // Avanzamento contratto semplificato

      areaInvio = CostantiW9.AREA_FASI_DI_GARA;
      break;

    case CostantiW9.ANAGRAFICA_GARA_LOTTI: // Anagrafica Gara e Lotto/i + CIG + Bando di gara + Pubblicita'
    case CostantiW9.ANAGRAFICA_SEMPLIFICATA_BANDO_DI_GARA: // Anagrafica Gara semplificata e Bando di gara
    case CostantiW9.PUBBLICAZIONE_DOCUMENTI: // Pubblicazione documenti
      // gara
      areaInvio = CostantiW9.AREA_ANAGRAFICA_GARE;
      break;

    case CostantiW9.PUBBLICAZIONE_AVVISO: // Avviso
      areaInvio = CostantiW9.AREA_AVVISI;
      break;

    case CostantiW9.PROGRAMMA_TRIENNALE_LAVORI:            // Programma triennale ed elenco annuale Lavori (992)
    case CostantiW9.PROGRAMMA_TRIENNALE_FORNITURE_SERVIZI: // Programma triennale ed elenco annuale Forniture e Servizi (991)
    case CostantiW9.PROGRAMMA_TRIENNALE_OPERE_PUBBLICHE:   // Programma triennale delle opere pubbliche (982)
    case CostantiW9.PROGRAMMA_BIENNALE_ACQUISTI:           // Programma biennale degli acquisti (981)
      areaInvio = CostantiW9.AREA_PROGRAMMA_TRIENNALI_ANNUALI;
      break;
    case CostantiW9.GARE_ENTI_NAZIONALI_O_SOTTO_40000:
      areaInvio = CostantiW9.AREA_GARE_ENTINAZIONALI;

    default:
      break;
    }
    
    return areaInvio;
  }

  /**
   * Ritorna il codice della categoria usato in Sitat a partire dal codice usato da SIMOG.
   * 
   * @param sqlManager
   * @param categSimog
   * @return Ritorna il codice della categoria usato in Sitat a partire dal codice usato da SIMOG.
   * @throws SQLException
   */
  public static String getCategoriaSITAT(final SqlManager sqlManager, final String categSimog) throws SQLException {
    return (String) sqlManager.getObject("select CODSITAT from W9CODICI_AVCP where TABCOD='W3z03' and CODAVCP=?", 
        new Object[] { categSimog } );
  }
  
  /**
   * Ritorna il codice della categoria usato in SIMOG a partire dal codice usato da Sitat.
   * 
   * @param sqlManager
   * @param categSimog
   * @return Ritorna il codice della categoria usato in Sitat a partire dal codice usato da SIMOG.
   * @throws SQLException
   */
  public static String getCategoriaSIMOG(final SqlManager sqlManager, final String categSitat) throws SQLException {
    return (String) sqlManager.getObject("select CODAVCP from W9CODICI_AVCP where TABCOD='W3z03' and CODSITAT=?", 
        new Object[] { categSitat } );
  }
   
  /**
   * Ritorna il codice della classe di importo usata in Sitat a partire dal codice usato in SIMOG.
   * 
   * @param sqlManager
   * @param categSimog
   * @return Ritorna il codice della classe di importo usata in Sitat a partire dal codice usato in SIMOG.
   * @throws SQLException
   */
  public static String getClasseImportoSITAT(final SqlManager sqlManager, final String categSimog) throws SQLException {
    return (String) sqlManager.getObject("select CODSITAT from W9CODICI_AVCP where TABCOD='W3z11' and CODAVCP=?", 
        new Object[] { categSimog } );
  }
  
  /**
   * Ritorna il codice della classe di importo usato in SIMOG a partire dal codice usato in Sitat.
   * 
   * @param sqlManager
   * @param categSitat
   * @return Ritorna il codice della classe di importo usata in SIMOG a partire dal codice usato in Sitat.
   * @throws SQLException
   */
  public static String getClasseImportoSIMOG(final SqlManager sqlManager, final String categSitat) throws SQLException {
    return (String) sqlManager.getObject("select CODAVCP from W9CODICI_AVCP where TABCOD='W3z11' and CODSITAT=?", 
        new Object[] { categSitat } );
  } 
  
  /**
   * Determina se l'idAvGara passato come argomento e' formalmente valido, cioe' se
   * e' composto da soli numeri e non e' un CIG valido.
   * 
   * @param idAvGara Numero della gara
   * @return Ritorna true se l'idAvGara passato come argomento e' formalmente valido
   */
  public static boolean isIdGaraValido(final String idAvGara) {
    boolean result = false;
    
    if (StringUtils.isNotEmpty(idAvGara)) {
      String idGara = new String(idAvGara.trim());
      if (idGara.length() < 10 && StringUtils.isNumeric(idGara)) {
        result = true;
      }
    }

    return result;
  }

  /**
   * Determina se il codice CIG passato come argomento e' formalmente corretto, cioe' se
   * - ha lunghezza di 10 caratteri
   * - i primi 7 caratteri sono numerici
   * - gli ultimi tre caratteri sono il resto in esadecimale di questa operazione:
   *   (primi 7 caratteri del CIG) * 211 /4091 
   *   eventualmente completato a tre caratteri con degli zeri.
   * 
   * @param codiceCIG codice CIG
   * @return Ritorna true se il codice CIG passato come argomento e' formalmente corretto.
   */
  public static boolean isCigValido(final String codiceCIG) {
    boolean result = false;
    
    if (StringUtils.isNotEmpty(codiceCIG)) {
      
      String codCig = new String(codiceCIG.trim());
      if (codCig.length() == 10) {
        if (!"0000000000".equals(codCig)) {
          String primi7CaratteriCig = StringUtils.substring(codCig.trim(), 0, 7);
          String ultimi3CaratteriCig = StringUtils.substring(codCig.trim(), 7);
          
          if (StringUtils.isNumeric(primi7CaratteriCig)
              && StringUtils.isAlphanumeric(ultimi3CaratteriCig)) {
            while (primi7CaratteriCig.indexOf('0') == 0) {
              primi7CaratteriCig = primi7CaratteriCig.substring(1);
            }
            Long numeroCIG =  new Long(primi7CaratteriCig);
            long oper1 = numeroCIG.longValue();
            long resto = (oper1 * 211) % 4091;
            
            String strResto = StringUtils.leftPad(Long.toHexString(resto), 3, '0').toUpperCase();
            
            if (ultimi3CaratteriCig.equalsIgnoreCase(strResto)) {
              result = true;
            }
          }
        }
      }
    }
    return result;
  }
  
  
  public static boolean isSmartCigValido(final String codiceSmartCIG) {
	  boolean result = false;

	  if (StringUtils.isNotEmpty(codiceSmartCIG)) {

		  String codCig = new String(codiceSmartCIG.trim().toUpperCase());
		  if (codCig.length() == 10) {
			  if(codCig.startsWith("X") || codCig.startsWith("Z") || codCig.startsWith("Y")) {
				  try {
					  String strK=codCig.substring(1,3);//Estraggo la firma
					  long nDecStrK = Integer.parseInt (strK, 16); //trasformo in decimale
					  String strC4_10 = codCig.substring(3,10);
					  long nDecStrC4_10 = Integer.parseInt (strC4_10, 16); //trasformo in decimale
					  //Calcola Firma
					  long nDecStrK_chk = ((nDecStrC4_10 * 1/1) * 211 % 251);
					  if (nDecStrK == nDecStrK_chk) {
						  result = true;
					  }
				  } catch(Exception e){
					  //Impossibile calcolare la firma
					  ;
				  }
			  }
		  }
	  }
	  return result;
  }
  
  /**
   * 
   * Determina le condizioni che giustificano il ricorso alla procedura negoziata
   * (vale se ID_SCELTA_CONTRAENTE del lotto e' uguale a 4 o a 10)
   * 
   * @param sqlManager
   * @param codgara
   * @param codlott
   * @return
   * @throws SQLException
   */
  public static boolean isNegoziabile(final SqlManager sqlManager, final Long codgara, final Long codlott) throws SQLException {
      boolean result = false;
      
      Long idSceltaContraente = (Long) sqlManager.getObject("select ID_SCELTA_CONTRAENTE from W9LOTT where CODGARA = ? and CODLOTT = ?", new Object[] { codgara, codlott });
      if(new Long(4).equals(idSceltaContraente) || new Long(10).equals(idSceltaContraente)){
          result = true;
      }
      
      return result;
  }
  
  /**
   * Metodo che, a partire dai flussi gia' inviati da SitatSA a SitatORT, determina se per un certo flusso
   * sono gia' stati inviati i flussi propedeutici
   * 
   * @param codiceFlusso
   * @param flussiInviati
   * @param isInvioVigilanza true se si e' in modalita' Vigilanza, false altrimenti
   * @param numappa progressivo aggiudicazione corrente
   * @return Ritorna true se per il flusso <i>codiceFlusso</i> risultano inviati tutti i flussi propedeutici
   * @throws SQLException
   */
  public static boolean esistonoFlussiPropedeutici(final int codiceFlusso, Set<Long> flussiInviati,
      boolean isInvioVigilanza, final boolean isConclusioneAnticipata, final Long numappa) throws SQLException {
    boolean esistonoFlussiPropedeutici = false;
    
    switch (codiceFlusso) {

    case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI: // Elenco imprese invitate/partecipanti
    case CostantiW9.COMUNICAZIONE_ESITO: // Comunicazione esito
    case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA: // Fase aggiudicazione/affidamento (>150.000 euro)
    case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE: // Fase aggiudicazione/affidamento appalto semplificata
    case CostantiW9.ADESIONE_ACCORDO_QUADRO: // Adesioneaccordo quadro
      esistonoFlussiPropedeutici = true;
      break;
    case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA: // Fase iniziale esecuzione contratto (>500.000 euro)
      if ((flussiInviati.contains(new Long(CostantiW9.A05)) || flussiInviati.contains(new Long(CostantiW9.A21))) && 
          ! flussiInviati.contains(new Long(CostantiW9.A09))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO: // Fase iniziale esecuzione contratto semplificata
      if ((flussiInviati.contains(new Long(CostantiW9.A04)) || flussiInviati.contains(new Long(CostantiW9.A05)) || flussiInviati.contains(new Long(CostantiW9.A21))) &&
      		! flussiInviati.contains(new Long(CostantiW9.A10))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.STIPULA_ACCORDO_QUADRO: // Stipula accordo quadro
      if (flussiInviati.contains(new Long(CostantiW9.A04)) || flussiInviati.contains(new Long(CostantiW9.A05))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA: // Fase esecuzione e avanzamento del contratto
        if ((flussiInviati.contains(new Long(CostantiW9.A06))
            || flussiInviati.contains(new Long(CostantiW9.A07))
            || flussiInviati.contains(new Long(CostantiW9.A20)))
            && ! flussiInviati.contains(new Long(CostantiW9.A09))) {
          esistonoFlussiPropedeutici = true;
        }
        break;
    case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO: // Avanzamento contratto semplificato
      if ((flussiInviati.contains(new Long(CostantiW9.A06))
          || flussiInviati.contains(new Long(CostantiW9.A07))
          || flussiInviati.contains(new Long(CostantiW9.A20)))
          && ! (flussiInviati.contains(new Long(CostantiW9.A09)) || flussiInviati.contains(new Long(CostantiW9.A10)))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA: // Fase di conclusione del contratto (>500.000 euro)
      if (flussiInviati.contains(new Long(CostantiW9.A05)) || flussiInviati.contains(new Long(CostantiW9.A21))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO: // Fase di conclusione del contratto semplificata
      if (flussiInviati.contains(new Long(CostantiW9.A04)) || flussiInviati.contains(new Long(CostantiW9.A05))
          || flussiInviati.contains(new Long(CostantiW9.A21))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.COLLAUDO_CONTRATTO: // Fase di collaudo del contratto
      if (flussiInviati.contains(new Long(CostantiW9.A09))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.SOSPENSIONE_CONTRATTO: // Sospensione del contratto
      if ((flussiInviati.contains(new Long(CostantiW9.A06))
          || flussiInviati.contains(new Long(CostantiW9.A07))
          || flussiInviati.contains(new Long(CostantiW9.A20)))
          && ! flussiInviati.contains(new Long(CostantiW9.A09))
          && ! flussiInviati.contains(new Long(CostantiW9.A10))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.VARIANTE_CONTRATTO: // Variante del contratto
      if ((flussiInviati.contains(new Long(CostantiW9.A04))
          || flussiInviati.contains(new Long(CostantiW9.A05))
          || flussiInviati.contains(new Long(CostantiW9.A21)))
          && ! flussiInviati.contains(new Long(CostantiW9.A11))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.ACCORDO_BONARIO: // Accordi bonari
      if (flussiInviati.contains(new Long(CostantiW9.A06))
          || flussiInviati.contains(new Long(CostantiW9.A07))
          || flussiInviati.contains(new Long(CostantiW9.A20))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.SUBAPPALTO: // Subappalti
      if ((((flussiInviati.contains(new Long(CostantiW9.A22)) || isInvioVigilanza || numappa > 1) &&
            (flussiInviati.contains(new Long(CostantiW9.A04)) || flussiInviati.contains(new Long(CostantiW9.A05)))
          ) || flussiInviati.contains(new Long(CostantiW9.A21)))
          && ! flussiInviati.contains(new Long(CostantiW9.A09))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.ISTANZA_RECESSO: // Istanza di recesso
      if ((
          ((flussiInviati.contains(new Long(CostantiW9.A22)) || isInvioVigilanza || numappa > 1)
              && flussiInviati.contains(new Long(CostantiW9.A05)))
            || flussiInviati.contains(new Long(CostantiW9.A21)))
          && ! flussiInviati.contains(new Long(CostantiW9.A06))
          && ! flussiInviati.contains(new Long(CostantiW9.A07))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:
      // Esito negativo verifica idoneita' tecnico-professionale dell'impresa aggiudicataria
    case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:
      // Inadempienze predisposizioni sicurezza e regolarita' lavoro
      if ((((flussiInviati.contains(new Long(CostantiW9.A22)) || isInvioVigilanza || numappa > 1) &&
          (flussiInviati.contains(new Long(CostantiW9.A04)) || flussiInviati.contains(new Long(CostantiW9.A05)))
        ) || flussiInviati.contains(new Long(CostantiW9.A21)))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:
      // Esito negativo verifica regolarita' contributiva ed assicurativa
    	esistonoFlussiPropedeutici = true;
      break;
    case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI: // Scheda segnalazione infortuni
      if (flussiInviati.contains(new Long(CostantiW9.A06))
          || flussiInviati.contains(new Long(CostantiW9.A07))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    case CostantiW9.APERTURA_CANTIERE: // Apertura cantiere
      if (((flussiInviati.contains(new Long(CostantiW9.A22)) || isInvioVigilanza || numappa > 1) && flussiInviati.contains(new Long(CostantiW9.A05)))
            || flussiInviati.contains(new Long(CostantiW9.A21))) {
        esistonoFlussiPropedeutici = true;
      }
      break;
    default:
      esistonoFlussiPropedeutici = false;
      break;
    }

    return esistonoFlussiPropedeutici;
  }
  
  /**
   * Ritorna la versione SIMOG della gara in funzione della data di creazione della gara su SIMOG e 
   * della data di perfezionamento del bando.
   * 
   * @param dataCreazioneGara
   * @param dataPerfezionamentoBando
   * @return Ritorna la versione SIMOG della gara
   * @throws SQLException
   */
  public static int getVersioneSimog(GregorianCalendar dataCreazioneGara, GregorianCalendar dataPerfezionamentoBando) throws SQLException {
    int versioneSimog = 1;
    
    GregorianCalendar dataAttuazioneSimogVer2 = UtilitySITAT.getDataAttuazioneSimogVer2();  //23/06/2019
    GregorianCalendar dataAttuazioneSimogVer3 = UtilitySITAT.getDataAttuazioneSimogVer3();  //21/10/2019
    GregorianCalendar dataAttuazioneSimogVer4 = UtilitySITAT.getDataAttuazioneSimogVer4();  //01/01/2020
    GregorianCalendar dataAttuazioneSimogVer5 = UtilitySITAT.getDataAttuazioneSimogVer5();  //12/05/2020
    GregorianCalendar dataAttuazioneSimogVer6 = UtilitySITAT.getDataAttuazioneSimogVer6();  //10/12/2020
    
    if (dataCreazioneGara != null && dataCreazioneGara.compareTo(dataAttuazioneSimogVer6) >= 0) {
        versioneSimog = 6;
    } else if (dataCreazioneGara != null && dataCreazioneGara.before(dataAttuazioneSimogVer2)) {
      versioneSimog = 1;
    } else if (dataCreazioneGara != null && dataCreazioneGara.before(dataAttuazioneSimogVer3)) {
      versioneSimog = 2;
    } else if (dataCreazioneGara != null && (DateUtils.isSameDay(dataCreazioneGara, dataAttuazioneSimogVer5)
        || dataCreazioneGara.after(dataAttuazioneSimogVer5))) {
      versioneSimog = 5;
    } else if (dataPerfezionamentoBando != null && dataPerfezionamentoBando.before(dataAttuazioneSimogVer4)) {
      versioneSimog = 3;
    } else if (dataCreazioneGara != null && dataCreazioneGara.before(dataAttuazioneSimogVer5)) {
      versioneSimog = 4;
    } else {
      versioneSimog = 1;
    }
    
    return versioneSimog;
  }

  public static GregorianCalendar getDataAttuazioneSimogVer2() throws SQLException {
    GregorianCalendar dataAttuazioneSimog1 = new GregorianCalendar(2019,Calendar.JUNE,23);
    return dataAttuazioneSimog1;
  }
  
  public static GregorianCalendar getDataAttuazioneSimogVer3() throws SQLException {
    GregorianCalendar dataAttuazioneSimog2 = new GregorianCalendar(2019,Calendar.OCTOBER,21);
    return dataAttuazioneSimog2;
  }
  
  public static GregorianCalendar getDataAttuazioneSimogVer4() throws SQLException {
    GregorianCalendar dataAttuazioneSimog4 = new GregorianCalendar(2020,Calendar.JANUARY,1);
    return dataAttuazioneSimog4;
  }
  
  public static GregorianCalendar getDataAttuazioneSimogVer5() throws SQLException {
    GregorianCalendar dataAttuazioneSimog5 = new GregorianCalendar(2020,Calendar.MAY,12);
    return dataAttuazioneSimog5;
  }
  
  public static GregorianCalendar getDataAttuazioneSimogVer6() throws SQLException {
	GregorianCalendar dataAttuazioneSimog6 = new GregorianCalendar(2020,Calendar.DECEMBER,10);
	return dataAttuazioneSimog6;
  }
  
  /**
   * Estrazione della Data attuazione Simog 3.04.6: data discrimanante di attuazione della nuova versione di SIMOG
   * dal tabellato W9023 in TAB1 con TAB1TIP=3
   * 
   * @return
   * @throws SQLException
   */
  public static Date getDataAttuazioneSimogVer3_04_6(final SqlManager sqlManager) throws SQLException {
	  String strDataAttuazioneSimog6 = (String) sqlManager.getObject(
        "select TAB1DESC from TAB1 where TAB1COD='W9023' and TAB1TIP=3", null);
    
	  Date dataAttuazioneSimog6 = null;
	  if (StringUtils.isNotEmpty(strDataAttuazioneSimog6)) {
		  dataAttuazioneSimog6 = UtilityDate.convertiData(strDataAttuazioneSimog6.substring(0, 10), 
          UtilityDate.FORMATO_GG_MM_AAAA);
	  }
	  return dataAttuazioneSimog6;
  }
  
  public static boolean v3_04_6_Attiva(final SqlManager sqlManager, final Long numgara) {
	  boolean attiva = false;
	  try {
		  Date dataAttivazione = getDataAttuazioneSimogVer3_04_6(sqlManager);
			  Date dataCreazioneGara = null;
			  if (numgara != null) {
				  dataCreazioneGara = (Date)sqlManager.getObject("Select DATA_CREAZIONE from W3GARA where ID_GARA is not null and ID_GARA<>'' and NUMGARA=?",new Object[] { numgara });
			  }
			  if (dataCreazioneGara == null) {
				  attiva = (new Date().compareTo(dataAttivazione)> 0);
			  } else {
				  attiva =  dataCreazioneGara.compareTo(dataAttivazione)>0;
			  }
	  } catch(Exception ex) {
		  ;
	  }
	  return attiva;
  }
  /**
   * Estrazione della Data attuazione Simog 3.04.5: data discrimanante di attuazione della nuova versione di SIMOG
   * dal tabellato W9023 in TAB1 con TAB1TIP=2
   * 
   * @return
   * @throws SQLException
   */
  /*public static GregorianCalendar getDataAttuazioneSimogVer6(final SqlManager sqlManager) throws SQLException {
	  String strDataAttuazioneSimog6 = (String) sqlManager.getObject(
        "select TAB1DESC from TAB1 where TAB1COD='W9023' and TAB1TIP=2", null);
    
	  GregorianCalendar dataAttuazioneSimog6 = null;
	  if (StringUtils.isNotEmpty(strDataAttuazioneSimog6)) {
		  Date tempDataAttuazioneSimog6 = UtilityDate.convertiData(strDataAttuazioneSimog6.substring(0, 10), 
          UtilityDate.FORMATO_GG_MM_AAAA);
		  if (tempDataAttuazioneSimog6 != null) {
			  dataAttuazioneSimog6 = new GregorianCalendar(tempDataAttuazioneSimog6.getYear()+1900,
					  tempDataAttuazioneSimog6.getMonth(), tempDataAttuazioneSimog6.getDate(), 0, 0, 0);
		  }
	  }
	  return dataAttuazioneSimog6;
  }*/
  
  /**
   * Ritorna il valore del campo W9GARA.VER_SIMOG. 
   * 
   * @param sqlManager
   * @param codgara
   * @return ritorna il valore del campo W9GARA.VER_SIMOG
   * @throws SQLException
   */
  public static int getVersioneSimog(final SqlManager sqlManager, final Long codgara) throws SQLException {
    int result = 1;
    Long versioneSimog = (Long) sqlManager.getObject(
        "select VER_SIMOG from W9GARA where CODGARA=?", new Object[] { codgara } );
    
    if (versioneSimog != null) {
      result = versioneSimog.intValue();
    }
    return result;
  }

  public static int getVersioneSimog(final SqlManager sqlManager, final String idAvGara) throws SQLException {
    int result = 1;
    Long versioneSimog = (Long) sqlManager.getObject(
        "select VER_SIMOG from W9GARA where IDAVGARA=?", new Object[] {idAvGara } );
    
    if (versioneSimog != null) {
      result = versioneSimog.intValue();
    }
    return result;
  }
  
  /**
   * Ritorna il valore del campo W9GARA.DATA_PUBBLICAZIONE. 
   * @param sqlManager
   * @param codgara
   * @return Ritorna il valore del campo W9GARA.DATA_PUBBLICAZIONE. 
   * @throws SQLException
   */
  public static Date getDataPubblicazioneGara(final SqlManager sqlManager, final Long codgara) throws SQLException {
	  Date result = (Date) sqlManager.getObject(
		        "select DATA_PUBBLICAZIONE from W9GARA where CODGARA=?", new Object[] { codgara } );
	  return result;
  }
 
  /**
   * Ritorna true se la gara e' in carico alla stazione appaltante delegata con compito di 
   * aggiudicazione, cioe' UFFINT.CFEIN <> W9GARA.CF_SA_AGENTE e ID_F_DELEGATE = 1 o 2, false altrimenti
   * 
   * @param sqlManager
   * @param codgara
   * @return Ritorna true se la gara e' in carico alla stazione appaltante delegata con compito di aggiudicazione, false altrimenti
   * @throws SQLException
   */
  public static boolean isD1(final Long codgara, final SqlManager sqlManager) throws SQLException {
    Long count = (Long) sqlManager.getObject(
        "select count(u.CFEIN) from UFFINT u, W9GARA g where g.CODGARA=? and g.CODEIN=u.CODEIN and UPPER(u.CFEIN) <> UPPER(g.CF_SA_AGENTE) and g.ID_F_DELEGATE in (1,2)", 
        new Object[] { codgara });
    if (count.longValue() > 0) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Ritorna true se la gara e' in carico alla stazione appaltante delegata con compito di proposta 
   * di aggiudicazione, cioe' UFFINT.CFEIN <> W9GARA.CF_SA_AGENTE e ID_F_DELEGATE = 4, false altrimenti
   * 
   * @param sqlManager
   * @param codgara
   * @return Ritorna true se la gara e' in carico alla stazione appaltante delegata con compito di proposta di aggiudicazione, false altrimenti
   * @throws SQLException
   */
  private static boolean isD4(final Long codgara, final SqlManager sqlManager) throws SQLException {
    Long count = (Long) sqlManager.getObject(
        "select count(u.CFEIN) from UFFINT u, W9GARA g where g.CODGARA=? and g.CODEIN=u.CODEIN "
        		+ "and UPPER(u.CFEIN) <> UPPER(g.CF_SA_AGENTE) and g.ID_F_DELEGATE = 4",
            new Object[] { codgara });
    
    if (count.longValue() > 0) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Verifica se la gara è nella situazione per cui può essere impostato il multilotto
   * Questo avviene quando più lotti simulataneamente
   * - abbiano la scheda Aggiudicazione (FASE_ESECUZIONE=1) già trasmessa;
   * - abbiano lo stesso aggiudicatario (o gli stessi aggiudicatari) tra loro;
   * - non abbiamo la scheda Inizio a prescindere se è stata inviata o meno.
   * 
   * @param codgara Codice della gara
   * @param sqlManager SqlManager
   * @return Ritorna true se la gara contiene lotti che possono essere accorpati, false altrimenti
   * @throws SQLException SqlException
   */
  public static boolean isMultilotto(final Long codgara,
      final SqlManager sqlManager) throws SQLException {

    List< ? > lottiAggiudicati = sqlManager.getListVector(
        "select w9lott.codlott from w9lott left join w9fasi on w9lott.codgara=w9fasi.codgara and w9lott.codlott=w9fasi.codlott " + 
        "where fase_esecuzione=1 and w9fasi.daexport='2' and w9lott.codgara=? and w9lott.codlott not in " + 
        "(select codlott from w9fasi where codgara=? and fase_esecuzione in (?,?,?,?,?,?,?,?,?,?))",
        new Object[] { codgara, codgara, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA,
        		CostantiW9.STIPULA_ACCORDO_QUADRO,
        		CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA,
        		CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA,
        		CostantiW9.COLLAUDO_CONTRATTO,
        		CostantiW9.ACCORDO_BONARIO,
        		CostantiW9.SOSPENSIONE_CONTRATTO,
        		CostantiW9.VARIANTE_CONTRATTO,
        		CostantiW9.SUBAPPALTO,
        		CostantiW9.ISTANZA_RECESSO
        		});
    List<String> listaAggiudicatari = new ArrayList<String>();
    if (lottiAggiudicati != null && lottiAggiudicati.size() > 1) {
      for (int i = 0; i < lottiAggiudicati.size(); i++) {
        Long codlott = (Long) SqlManager.getValueFromVectorParam(lottiAggiudicati.get(i), 0).getValue();
        List< ? > aggiudicatari = sqlManager.getListVector("select CODIMP from W9AGGI where CODGARA=? and CODLOTT=? order by CODIMP", new Object[] {codgara, codlott});
        String impreseAggiudicatrici = "";
        if (aggiudicatari != null && aggiudicatari.size() > 0) {
        	for (int j = 0; j < aggiudicatari.size(); j++) {
        		String codimp = (String) SqlManager.getValueFromVectorParam(aggiudicatari.get(j), 0).getValue();
        		impreseAggiudicatrici += codimp + "-";
        	}
        	if (listaAggiudicatari.contains(impreseAggiudicatrici)) {
        		return true;
        	} else {
        		listaAggiudicatari.add(impreseAggiudicatrici);
        	}
        }
      }
    }
    return false;
  }
  
  /**
   * Test sulla condizione di abilitazione. Restituisce TRUE se la fase è
   * abilitata.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param faseEsecuzione Fase di esecuzione
   * @param sqlManager SqlManager
   * @return Ritorna true se la fase e' abilitata, false altrimenti
   * @throws SQLException SqlException
   */
  public static boolean isFaseAbilitata(final Long codgara, final Long codlott, final Long numAppa,
      final int faseEsecuzione, final SqlManager sqlManager) throws SQLException {

    boolean isAbilitata = false;
    
    switch (faseEsecuzione) {
    case CostantiW9.COMUNICAZIONE_ESITO:  // A22 - 984
    case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI: // A24 - 101
    	isAbilitata = !UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A04, sqlManager)
  				&& !UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager);
    	break;
    case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:  // A05 - 1
    case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:  // A04 - 987
    case CostantiW9.ADESIONE_ACCORDO_QUADRO:  // A21 - 12
    case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA: // B03 - 996
    	isAbilitata = true;
      break;

    case CostantiW9.STIPULA_ACCORDO_QUADRO: // A20 - 11a
    	isAbilitata = UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A04, sqlManager)
    							|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager);
      break;

    case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA: // A06 - 2
   		isAbilitata = (UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager)
     			|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A21, sqlManager)) &&
       				!UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A09, sqlManager);
      break;

    case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO: // A07 - 986
   		isAbilitata = (UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A04, sqlManager) 
   				|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager)
     					|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A21, sqlManager)) &&
     							!UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A10, sqlManager);
      break;

    case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA: // A08 - 3
    	isAbilitata = (UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A06, sqlManager)
    			|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A07, sqlManager)
    					|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A20, sqlManager)) 
    					&& !UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A09, sqlManager);
      break;
    case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO: // A25 - 102
    	isAbilitata = (UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A06, sqlManager)
    			|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A07, sqlManager)
							|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A20, sqlManager)) 
    							&& !(UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A09, sqlManager)
    										|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A10, sqlManager));
      break;

    case CostantiW9.SOSPENSIONE_CONTRATTO: // A12 - 6
    	isAbilitata = (UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A06, sqlManager)
    			|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A07, sqlManager)
    					|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A20, sqlManager)) 
    							&& !UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A09, sqlManager)
    							&& !UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A10, sqlManager);
    	break;
      
    case CostantiW9.VARIANTE_CONTRATTO: // A13 - 7
    	isAbilitata = (UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A04, sqlManager)
    			|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager)
    					|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A21, sqlManager)) 
    							&& !UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A11, sqlManager);
      break;

    case CostantiW9.ACCORDO_BONARIO: // A14 - 8
      isAbilitata = (UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A06, sqlManager) 
      		|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A07, sqlManager)
      				|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A20, sqlManager));
      break;
      
    case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA: // A09 - 4
      isAbilitata = UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager)
      	|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A21, sqlManager);
      break;

    case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO: // A10 - 985
      isAbilitata = UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A04, sqlManager)
      		|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager)
      				|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A21, sqlManager);
      break;

    case CostantiW9.COLLAUDO_CONTRATTO: // A11 - 5
      isAbilitata = UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A09, sqlManager);
      break;

    case CostantiW9.SUBAPPALTO: // A15 - 9
   		isAbilitata = (UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A04, sqlManager)
       			|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager)
       					|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A21, sqlManager))
       							&& !UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A09, sqlManager);
      break;
    case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA: // B02 - 997
    case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI: // B06 - 995
   		isAbilitata = UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A04, sqlManager)
   				|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager)
   						|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A21, sqlManager);
      break;
      
    case CostantiW9.ISTANZA_RECESSO: // A16 - 10
  		// In configurazione Vigilanza non e' richiesta la fase di 'Comunicazione sito' (A22 - 984)
   		isAbilitata = (UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager)
					|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A21, sqlManager))
 							&& !UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A06, sqlManager)
									&& !UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A07, sqlManager);
      break;

    case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI: // B07 - 994
      isAbilitata = UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A06, sqlManager)
      	|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A07, sqlManager);
      break;
      
    case CostantiW9.APERTURA_CANTIERE: // B08 - 998
   		isAbilitata = UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A05, sqlManager)
					|| UtilitySITAT.existsFase(codgara, codlott, numAppa, CostantiW9.A21, sqlManager);
    	break;
    }

    return isAbilitata;
  }

  /**
   * Metodo privato per il test sulla condizione di visualizzazione. In questo
   * test si considerano i controlli sulle fasi diverse da quella di
   * "Esito comunicazione - A22".
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param faseEsecuzione Fase di Esecuzione
   * @param sqlManager SqlManager
   * @return Ritorna true se la fase e' visualizzabile, false altrimenti
   * @throws SQLException SqlException
   */
  public static boolean isFaseVisualizzabile(final Long codgara, final Long codlott,
      final int faseEsecuzione, final SqlManager sqlManager) throws SQLException {

    boolean isVisualizzabile = false;

    String tipoContratto = UtilitySITAT.getTipoContrattoLotto(codgara, codlott, sqlManager);

    switch (faseEsecuzione) {
    	case CostantiW9.COMUNICAZIONE_ESITO:  // A22 - 984
    	case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI:  // A24 - 101
    		isVisualizzabile = !isAAQ(codgara, sqlManager);
    	break;
    	
      case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA: // A05 - 1
        isVisualizzabile = isS2(codgara, codlott, sqlManager) && !isAAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager);
        break;
  
      case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE: // A04 - 987
        isVisualizzabile = !isS2(codgara, codlott, sqlManager) && !isAAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager);
        break;
        
      case CostantiW9.ADESIONE_ACCORDO_QUADRO: // A21 - 12
      	isVisualizzabile = isAAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager);
        break;

      case CostantiW9.STIPULA_ACCORDO_QUADRO: // A20 - 11
        isVisualizzabile = isSAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager) && !isD1(codgara, sqlManager);
        break;

      case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA: // A06 - 2
      	isVisualizzabile = isS2(codgara, codlott, sqlManager) && !isSAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager) 
      			 && isOrd(codgara, sqlManager) && !isD1(codgara, sqlManager);
        break;

      case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO: // A07 - 986
        isVisualizzabile = (!isS2(codgara, codlott, sqlManager) || !isOrd(codgara, sqlManager)) && !isSAQ(codgara, sqlManager)
        		 && isEA(codgara, codlott, sqlManager) && !isD1(codgara, sqlManager);
        break;
        
      case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA: // A08 - 3
      	isVisualizzabile = (isS3(codgara, codlott, sqlManager) || isAII(codgara, sqlManager)) && !isSAQ(codgara, sqlManager)
      			&& isEA(codgara, codlott, sqlManager) && isOrd(codgara, sqlManager);
      	break;
      	
      case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO: // A25 - 102
      	isVisualizzabile = ((!isS3(codgara, codlott, sqlManager) && !isAII(codgara, sqlManager)) || !isOrd(codgara, sqlManager))
      		&& !isSAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager);
      	break;
      	
      case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA: // A09 - 4
        isVisualizzabile = isS2(codgara, codlott, sqlManager) && isEA(codgara, codlott, sqlManager)
        	&& !isSAQ(codgara, sqlManager) && isOrd(codgara, sqlManager);
        break;
      	
      case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO: // A10 - 985
        isVisualizzabile = (!isS2(codgara, codlott, sqlManager) || !isOrd(codgara, sqlManager))
        		&& isEA(codgara, codlott, sqlManager) && !isSAQ(codgara, sqlManager);
        break;
        
      case CostantiW9.COLLAUDO_CONTRATTO: // A11 - 5
      	isVisualizzabile = isS2(codgara, codlott, sqlManager) && !isSAQ(codgara, sqlManager) 
      			 && isEA(codgara, codlott, sqlManager) && isOrd(codgara, sqlManager);
        break;
        
      case CostantiW9.SOSPENSIONE_CONTRATTO: // A12 - 6
      	isVisualizzabile = (isS2(codgara, codlott, sqlManager) || isAII(codgara, sqlManager)
      			|| isR(codgara, codlott, sqlManager)) && !isSAQ(codgara, sqlManager)
      			&& isEA(codgara, codlott, sqlManager) && isOrd(codgara, sqlManager);
        break;

      case CostantiW9.VARIANTE_CONTRATTO: // A13 - 7
      	isVisualizzabile = (isS2(codgara, codlott, sqlManager) || isAII(codgara, sqlManager))
      				&& !isSAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager) 
      				&& isOrd(codgara, sqlManager);
        break;

      case CostantiW9.ACCORDO_BONARIO: // A14 - 8
      	isVisualizzabile = isS2(codgara, codlott, sqlManager) && !isSAQ(codgara, sqlManager)
      			&& isEA(codgara, codlott, sqlManager) && isOrd(codgara, sqlManager);
      	break;
  
      case CostantiW9.SUBAPPALTO: // A15 - 9
          isVisualizzabile = (isS2(codgara, codlott, sqlManager) || isAII(codgara, sqlManager))
          		&& isEA(codgara, codlott, sqlManager) && isOrd(codgara, sqlManager) && !isD1(codgara, sqlManager);
        break;
  
      case CostantiW9.ISTANZA_RECESSO: // A16 _ 10
        if (StringUtils.indexOf(tipoContratto, 'L') >= 0) {
          isVisualizzabile = (isS2(codgara, codlott, sqlManager) || isAII(codgara, sqlManager))
          		&& !isSAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager)
          		&& isOrd(codgara, sqlManager) && !isD1(codgara, sqlManager);
        }
        break;
  
      case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA: // B02 - 997
      case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA: // B03 - 996
        isVisualizzabile = !isSAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager) && !isD1(codgara, sqlManager);
        break;
  
      case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI: // B06 - 995
      case CostantiW9.APERTURA_CANTIERE: // B08 - 998
      	isVisualizzabile = isR(codgara, codlott, sqlManager) && !isSAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager)
      			&& !isD1(codgara, sqlManager);
      	break;
      case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI: // B07 - 994
      	isVisualizzabile = isR(codgara, codlott, sqlManager) && !isSAQ(codgara, sqlManager) && isEA(codgara, codlott, sqlManager);
        break;
        
    default:
      break;

    }
    return isVisualizzabile;
  }
  
  public static String generaPasswordRobusta() {
	  String password = "";
	  String alfabeto = "#?!@$%^&*-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	  try {
		  Random rand = new Random();
		  final SecureRandom rnd = new SecureRandom();
		  //la lunghezza password varia da minimo 8 massimo 15 caratteri
		  int lunghezzaPassword = 8 + rand.nextInt(8);
		  while (!Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", password))
		  {
			  password = "";
			  for( int i = 0; i < lunghezzaPassword; i++ ) {
				  password = password + alfabeto.charAt( rnd.nextInt(alfabeto.length()) );
			  }
		  }
	  } catch(Exception ex) {
		  ;
	  }
	  return password;
  }
  
}