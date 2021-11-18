package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityDate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action per il popolamento dello scadenziario delle fasi gara
 * 
 * @author Mirco.Franzoni
 */
public class GetScadenzeFasiGareJSONAction extends ActionBaseNoOpzioni {
	
	Logger             logger = Logger.getLogger(GetScadenzeFasiGareJSONAction.class);

  /**
   * Manager per la gestione delle interrogazioni di database.
   */
  private SqlManager sqlManager;

  /**
   * @param sqlManager
   *        the sqlManager to set
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
	
	public final ActionForward runAction(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
					throws IOException, ServletException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("runAction: inizio metodo");
		}
		
    response.setHeader("cache-control", "no-cache");
    response.setContentType("text/text;charset=utf-8");
    try {
      PrintWriter out = response.getWriter();

      // data inizio in formato ISO8061 (yyyy-mm-ddThh:mm:ss)
      String start = request.getParameter("start");
      Date dataInizio = UtilityDate.convertiData(start, UtilityDate.FORMATO_AAAA_MM_GG_CON_TRATTINI);
      
      // data fine in formato ISO8061 (yyyy-mm-ddThh:mm:ss)
      String end = request.getParameter("end");
      Date dataFine = UtilityDate.convertiData(end, UtilityDate.FORMATO_AAAA_MM_GG_CON_TRATTINI); 
      // entita' del record su cui e' definito lo scadenzario
      
      ProfiloUtente profiloUtente = (ProfiloUtente) request.getSession().getAttribute(
      		CostantiGenerali.PROFILO_UTENTE_SESSIONE);
      
      String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
      boolean isUtenteAmministratore = "A".equals(profiloUtente.getAbilitazioneStd()) || "C".equals(profiloUtente.getAbilitazioneStd());
      
      // Query da eseguire
      String sqlScadenzaFasi;
      // Parametri per la Query
      Object[] paramSql = null;
      
      if (isUtenteAmministratore) {
    	  sqlScadenzaFasi = "select s.codgara, s.codlott, l.cig, s.fase_esecuzione, s.num, s.data_scadenza, s.descrizione from V_W9SCADENZIARIO s, W9LOTT l where s.codgara = l.codgara and s.codlott = l.codlott and s.data_scadenza >= ? and s.data_scadenza <= ? " + 
          " and s.codgara in (select codgara from W9GARA where codein = ?)";
    	  paramSql = new Object[3];
    	  paramSql[0] = dataInizio;
    	  paramSql[1] = dataFine;
    	  paramSql[2] = codein;
      } else {
    	  sqlScadenzaFasi = "select s.codgara, s.codlott, l.cig, s.fase_esecuzione, s.num, s.data_scadenza, s.descrizione from V_W9SCADENZIARIO s, W9LOTT l where s.codgara = l.codgara and s.codlott = l.codlott and s.data_scadenza >= ? and s.data_scadenza <= ? " + 
          " and s.codgara in (select codgara from W9GARA where codein = ? and codgara in " +
          " (select p.CODGARA from V_W9PERMESSI p, V_RUOLOTECNICO r where p.CODGARA=r.CODGARA and p.PERMESSO > 1 and p.RUOLO=r.ID_RUOLO and r.SYSCON = ?))";
          //" (select p.CODGARA from V_W9PERMESSI p, V_RUOLOTECNICO r, TECNI t where p.CODGARA=r.CODGARA and p.PERMESSO > 1 and p.RUOLO=r.ID_RUOLO and r.CODTEC=t.CODTEC and t.SYSCON = ? and t.CGENTEI = ?))";
    	  //paramSql = new Object[5];
    	  paramSql = new Object[4];
    	  paramSql[0] = dataInizio;
    	  paramSql[1] = dataFine;
    	  paramSql[2] = codein;
    	  paramSql[3] = profiloUtente.getId();
    	  //paramSql[4] = codein;
      }
      
      List<Map<String, Object>> risultato = new ArrayList<Map<String, Object>>();

      this.getScadenze(sqlScadenzaFasi, paramSql, risultato);

      // si popola il risultato in formato JSON
      JSONArray jsonArray = JSONArray.fromObject(risultato.toArray());
      out.println(jsonArray);
      if (logger.isDebugEnabled()) {
        logger.debug("Risposta JSON=" + jsonArray);
      }
      out.flush();
    } catch (IOException e) {
      logger.error("Errore durante la lettura del writer della response", e);
      throw e;
    } catch (GestoreException e) {
      throw new RuntimeException("Errore inaspettato durante la lettura dei dati di V_W9SCADENZIARIO", e);
    } catch (SQLException e) {
      logger.error("Errore durante l'estrazione delle attivita' da porre nel calendario", e);
      throw new RuntimeException("Errore durante l'estrazione delle attivita' da porre nel calendario", e);
    }
    
    if (logger.isDebugEnabled()) {
			logger.debug("runAction: fine metodo");
		}
    
    return null;
  }
	
  /**
   * Estrae l'elenco delle scadenze individuate dalla query in input su un'entit&agrave; e popola la lista dei risultati da inviare
   * in formato JSON.
   *
   * @param select
   *        select da eseguire
   * @param params
   *        parametri da utilizzare nella select
   * @param risultato
   *        risultato JSON da popolare
   * @throws SQLException
   * @throws GestoreException
   */
  private void getScadenze(String select, Object[] params, List<Map<String, Object>> risultato)
      throws SQLException, GestoreException {

  	if (logger.isDebugEnabled()) {
			logger.debug("getScadenze: inizio metodo");
		}
  	
    // si estraggono i dati
    List<Vector<JdbcParametro>> listaAttivita = this.sqlManager.getListVector(select.toString(), params);
    if (listaAttivita != null && listaAttivita.size() > 0) {
      
      for (Vector<JdbcParametro> riga : listaAttivita) {
    	  Long codiceGara = SqlManager.getValueFromVectorParam(riga, 0).longValue();
    	  Long codiceLotto = SqlManager.getValueFromVectorParam(riga, 1).longValue();
    	  String cig = SqlManager.getValueFromVectorParam(riga, 2).stringValue();
    	  Long fase_esecuzione = SqlManager.getValueFromVectorParam(riga, 3).longValue();
    	  Long num = SqlManager.getValueFromVectorParam(riga, 4).longValue();
          Date scadenza = SqlManager.getValueFromVectorParam(riga, 5).dataValue();
          String descrizione = SqlManager.getValueFromVectorParam(riga, 6).stringValue();
        
          Map<String, Object> mappaRiga = new HashMap<String, Object>();
          mappaRiga.put("codiceGara", codiceGara);
          mappaRiga.put("codiceLotto", codiceLotto);
          mappaRiga.put("cig", cig);
          mappaRiga.put("fase_esecuzione", fase_esecuzione);
          mappaRiga.put("num", num);
          mappaRiga.put("scadenza", UtilityDate.convertiData(scadenza, UtilityDate.FORMATO_AAAA_MM_GG_CON_TRATTINI));
          mappaRiga.put("descrizione", descrizione);
          risultato.add(mappaRiga);
      }
    }
    
    if (logger.isDebugEnabled()) {
			logger.debug("getScadenze: fine metodo");
		}
  }
	
}
