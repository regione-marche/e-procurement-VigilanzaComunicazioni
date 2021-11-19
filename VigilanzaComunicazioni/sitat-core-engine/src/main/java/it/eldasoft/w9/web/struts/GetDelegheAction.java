/*
 * Created on 29/ott/2014
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action che ritorna le deleghe dei RUP della stazione appaltante. Se l'utente è un amministratore le deleghe riguardano tutti i RUP
 * della stazione appaltante, altrimenti se l'utente è RUP vedrà solo le sue deleghe
 *
 * @author Mirco.Franzoni
 */

public class GetDelegheAction extends Action {

  Logger             logger = Logger.getLogger(GetDelegheAction.class);

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

  /**
   * Estrae l'elenco delle deleghe dei RUP associati alla stazione appaltante in formato JSON.
   *
   * @param codein
   *        codice stazione appaltante per ricavare gli utenti associati come RUP
   * @param onlyActive
   *        true se devo recuperare solo quelli associati, false se devo recuperare turri gli utenti
   * @param codRup
   *        codice Rup
   * @throws SQLException
   * @throws GestoreException
   * @throws CriptazioneException
   */
  private Map<String, Object> getDeleghe(String codein, boolean onlyActive, String codRup) throws SQLException, GestoreException {

	  Map<String, Object> risultato = new HashMap<String, Object>();
	  
	  Map<String, Object> delegheRUP = new HashMap<String, Object>();
	  @SuppressWarnings("unchecked")
	  List<Vector<JdbcParametro>> deleghe = sqlManager.getListVector("select t.codtec, t.nomtec, t.cftec from w9deleghe d1 left join tecni t on  d1.codtec = t.codtec where d1.codrup = ? ", new Object[] {codRup});
	  if (deleghe != null && deleghe.size() > 0) {
	    for (Vector<JdbcParametro> riga : deleghe) {
	      String codice = SqlManager.getValueFromVectorParam(riga, 0).stringValue();
	      delegheRUP.put(codice, codRup);
	    }
	  }

	  if (!onlyActive) {
		  deleghe = sqlManager.getListVector("select codtec, nomtec, cftec from tecni where cgentei = ? and syscon is not null and syscon in (select syscon from usr_ein where codein = ?)", new Object[] {codein, codein});
	  }

	  List<Object> righe = new ArrayList<Object>();
	  if (deleghe != null && deleghe.size() > 0) {

		  for (Vector<JdbcParametro> riga : deleghe) {

			  String codiceTecni = SqlManager.getValueFromVectorParam(riga, 0).stringValue();
			  String nomeTecni = SqlManager.getValueFromVectorParam(riga, 1).stringValue();
			  String cfTecni = SqlManager.getValueFromVectorParam(riga, 2).stringValue();
			  
			  Map<String, Object> mappaRiga = new HashMap<String, Object>();
			  mappaRiga.put("codiceTecni", codiceTecni);
			  mappaRiga.put("nomeTecni", nomeTecni);
			  mappaRiga.put("cfTecni", cfTecni);
			  if (onlyActive) {
				  mappaRiga.put("active", 1);
			  } else {
				  if (delegheRUP.containsKey(codiceTecni)) {
			          mappaRiga.put("active", 1);
			      } else {
			          mappaRiga.put("active", 0);
			      }
			  }
			  righe.add(mappaRiga);
		  }
	  }
	  risultato.put("draw", 0);
	  risultato.put("recordsTotal", deleghe.size());
	  risultato.put("recordsFiltered", deleghe.size());
	  risultato.put("data", righe);
	  return risultato;
  }
  
  @Override
  public final ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
      final HttpServletResponse response) throws IOException, ServletException {
    response.setHeader("cache-control", "no-cache");
    response.setContentType("text/text;charset=utf-8");
    try {
      PrintWriter out = response.getWriter();
      // Codice stazione appaltante
      String codein = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
      // Ricavo il codice Rup
      String codRup = request.getParameter("codRup");
      // Tipo azione
      String modoAperturaScheda = request.getParameter("modoAperturaScheda");
      String listaTecniciAssociati = request.getParameter("listaTecniciAssociati");
      
      if (listaTecniciAssociati != null) {
    	  W9Manager w9Manager = (W9Manager) UtilitySpring.getBean(
  		        "w9Manager", request.getSession().getServletContext(), W9Manager.class);
    	  w9Manager.salvaDeleghe(codRup, listaTecniciAssociati);
      }
      
      Map<String, Object> risultato = getDeleghe(codein, modoAperturaScheda.equals("VISUALIZZA"), codRup);

      // si popola il risultato in formato JSON
      JSONArray jsonArray = JSONArray.fromObject(risultato);
      String json = jsonArray.toString();
      out.println(json.substring(1, json.length() - 1));
      if (logger.isDebugEnabled()) {
        logger.debug("Risposta JSON=" + jsonArray);
      }
      out.flush();
    } catch (IOException e) {
      logger.error("Errore durante la lettura del writer della response", e);
      throw e;
    } catch (GestoreException e) {
      throw new RuntimeException("Errore inaspettato durante la lettura dei dati di W9DELEGHE", e);
    } catch (SQLException e) {
      logger.error("Errore durante l'estrazione dei tecnici per il RUP nella W9DELEGHE", e);
      throw new RuntimeException("Errore durante l'estrazione dei tecnici per il RUP nella W9DELEGHE", e);
    } 
    return null;
  }
}
