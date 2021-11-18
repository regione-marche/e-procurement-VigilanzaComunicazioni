package it.eldasoft.sil.pt.rest;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.spring.DataSourceTransactionManagerBase;
import it.eldasoft.utils.spring.UtilitySpring;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.transaction.TransactionStatus;

@Path("/IntegrazioneLFS")
public class IntegrazioneLFS {

  static Logger                logger                       = Logger.getLogger(IntegrazioneLFS.class);

  private static final String  ERROR_MSG                		= "Si e' verificato il seguente errore: ";
  private static final String  RICERCA_NO_DATA       			= "Nessun intervento/acquisto trovato";
  private static final String  RICERCA_NO_PARAMETRI  			= "Indicare almeno un parametro di ricerca tra quelli previsti (tipoProgramma, codiceCUI, descrizione)";
  private static final String  DETTAGLIO_NO_PARAMETRI  			= "Indicare il codiceCUI dell'intervento/acquisto";
  private static final String  TIPO_PROGRAMMA_ERRATO			= "Il parametro tipoProgramma accetta i valori 1 - Programma Lavori, 2 - Programma Forniture/Servizi";
  private static final String  ASSOCIA_INTERVENTO_NO_PARAMETRI 	= "Indicare i valori richiesti (codiceLavoro e codiceCUI)";
  private static final String  ASSOCIA_INTERVENTO_NO_CUI        = "Non esiste un intervento/acquisto con il codice CUI indicato";
  private static final String  ASSOCIA_INTERVENTO_CUI_ASSOCIATO = "L'intervento/acquisto con il codice CUI indicato e' gia' associato ad un lavoro";

  private static final boolean ESITO_OK                     = true;
  private static final boolean ESITO_KO                     = false;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/LeggiInterventoAcquisto")
  public Response leggiInterventoAcquisto(@QueryParam("tipoProgramma") String tipoProgramma, @QueryParam("codiceCUI") String codiceCUI,
      @QueryParam("descrizione") String descrizione, @QueryParam("codiceFiscaleSA") String codiceFiscaleSA, @Context HttpServletRequest request) {
    return Response.status(200).entity(getInterventiAcquisti(tipoProgramma, codiceCUI, descrizione, codiceFiscaleSA, request)).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/DettaglioInterventoAcquisto")
  public Response dettaglioInterventoAcquisto(@QueryParam("codiceCUI") String codiceCUI, @Context HttpServletRequest request) {
    return Response.status(200).entity(getDettaglioInterventoAquisto(codiceCUI, request)).build();
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/CollegaInterventoAcquisto")
  public Response collegaInterventoAcquisto(String input, @Context HttpServletRequest request) {

    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", request.getSession().getServletContext(), SqlManager.class);
    DataSourceTransactionManagerBase.setRequest(request);
    TransactionStatus status = null;
    boolean commitTransaction = false;

    JSONObject output = new JSONObject();

    try {
      JSONObject in = null;

      in = new ObjectMapper().readValue(input, JSONObject.class);
      String codicelavoro = (String) in.get("codiceLavoro");
      String codicecui = (String) in.get("codiceCUI");

      boolean _queryParamsOk = false;
      if (StringUtils.isNotEmpty(codicelavoro) && StringUtils.isNotEmpty(codicecui)) {
        _queryParamsOk = true;
      } else {
        _queryParamsOk = false;
        output.put("esito", ESITO_KO);
        output.put("messaggio", ASSOCIA_INTERVENTO_NO_PARAMETRI);
      }

      if (_queryParamsOk) {
    	  String query = "from inttri join piatri on inttri.contri=piatri.contri " +
      	"where inttri.CUIINT = ? and piatri.norma=2 and (piatri.DADOZI is not null OR piatri.DAPTRI is not null) " ;
      	
        // Verifica esistenza cui
        Long cntCUI = (Long) sqlManager.getObject("select count(*) " + query, new Object[] { codicecui });
        if (cntCUI == null || (cntCUI != null && cntCUI.longValue() == 0)) {
          output.put("esito", ESITO_KO);
          output.put("messaggio", ASSOCIA_INTERVENTO_NO_CUI);
        } else {
        	//ricavo il programma e l'intervento a cui è associato il CUI passato
        	query = "select piatri.contri, inttri.conint " + query + 
        	" ORDER BY inttri.CUIINT, piatri.DADOZI DESC, piatri.DAPTRI DESC";
        	List<?> datiINTTRI = sqlManager.getListVector(query, new Object[] {codicecui});
        	if (datiINTTRI != null && datiINTTRI.size() > 0) {
        		Long contri = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 0).getValue();
        		Long conint = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 1).getValue();
        		// Verifica che il cui indicato non sia gia' associato ad un lavoro
                Long cntInterventoAssociatoCUI = (Long) sqlManager.getObject("select count(*) from inttri where CUIINT = ? and CONTRI = ? and CONINT = ? and CODINT is not null and CODINT<>'' and CEFINT='1'",
                    new Object[] { codicecui, contri, conint });
                if (cntInterventoAssociatoCUI != null && cntInterventoAssociatoCUI.longValue() > 0) {
                  output.put("esito", ESITO_KO);
                  output.put("messaggio", ASSOCIA_INTERVENTO_CUI_ASSOCIATO);
                } else {
                  // In questo caso si provvede ad associare il codice CUI
                  status = sqlManager.startTransaction();
                  sqlManager.update("update INTTRI set CODINT = ?, CEFINT='1' where CUIINT = ? and CONTRI = ? and CONINT = ?", new Object[] { codicelavoro, codicecui, contri, conint });
                  commitTransaction = true;
                  output.put("esito", ESITO_OK);
                }
        	}
        }
      }

    } catch (JsonParseException e) {
      commitTransaction = false;
      output.accumulate("STATO", "0");
      output.accumulate("MESSAGGIO", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } catch (JsonMappingException e) {
      commitTransaction = false;
      output.accumulate("STATO", "0");
      output.accumulate("MESSAGGIO", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } catch (IOException e) {
      commitTransaction = false;
      output.accumulate("STATO", "0");
      output.accumulate("MESSAGGIO", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } catch (SQLException e) {
      commitTransaction = false;
      output.accumulate("STATO", "0");
      output.accumulate("MESSAGGIO", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } finally {
      if (status != null) {
        if (commitTransaction) {
          try {
            sqlManager.commitTransaction(status);
          } catch (SQLException e) {

          }
        } else {
          try {
            sqlManager.rollbackTransaction(status);
          } catch (SQLException e) {

          }
        }
      }
    }
    return Response.status(200).entity(output).build();

  }

  /**
   * Ricerca per tipo programma (lavori o fornitura/servizi) codice CUI dell'intervento/acquisto
   * descrizione tra i dati di programmi adottati o approvati con norma='2'
   * 
   * @param tipoProgramma
   * @param codiceCUI
   * @param descrizione
   * @param codiceFiscaleSA
   * @param request
   * @return
   */
  private JSONObject getInterventiAcquisti(String tipoProgramma, String codiceCUI, String descrizione, String codiceFiscaleSA, HttpServletRequest request) {
    JSONObject in = new JSONObject();
    JSONObject output = new JSONObject();
    if (tipoProgramma != null) in.put("tipoProgramma", tipoProgramma);
    if (codiceCUI != null) in.put("codiceCUI", codiceCUI);
    if (descrizione != null) in.put("descrizione", descrizione);
    if (codiceFiscaleSA != null) in.put("codiceFiscaleSA", codiceFiscaleSA);
    output = getRicerca(in.toString(), request);
    return output;
  }

  /**
   * Ricerca per codice CUI dell'intervento/acquisto
   * restituisce i dati più significativi dell'intervento/acquisto con il codice CUI indicato
   * 
   * @param codiceCUI
   * @param request
   * @return
   */
  private JSONObject getDettaglioInterventoAquisto(String codiceCUI, HttpServletRequest request) {
    JSONObject in = new JSONObject();
    JSONObject output = new JSONObject();
    if (codiceCUI != null) in.put("codiceCUI", codiceCUI);
    output = getDettaglio(in.toString(), request);
    return output;
  }
  /**
   * Restituisce gli interventi/acquisti che soddisfano i parametri di ricerca.
   * 
   * @param in
   * @param request
   * @return
   */
  private JSONObject getRicerca(String in, HttpServletRequest request) {

    JSONObject output = new JSONObject();
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", request.getSession().getServletContext(), SqlManager.class);

    try {
      Long tipoProgramma = null;
      String codiceCUI = null;
      String descrizione = null;
      String codiceFiscaleSA = null;

      boolean queryParamsOk = false;
      boolean validateTipoProgrammaOk = true;

      JSONObject input = new ObjectMapper().readValue(in, JSONObject.class);
  
      if (input.containsKey("tipoProgramma")) {
    	  try {
    		  if (StringUtils.isNotEmpty(input.getString("tipoProgramma"))) {
    			  tipoProgramma = new Long(input.getString("tipoProgramma"));
        		  if (!tipoProgramma.equals(new Long(1)) && !tipoProgramma.equals(new Long(2))) {
            		  validateTipoProgrammaOk = false;
            	  }
    		  }
    	  }
    	  catch(Exception ex) {
    		  validateTipoProgrammaOk = false;
    	  }
      }
      if (input.containsKey("codiceCUI")) {
    	  codiceCUI = input.getString("codiceCUI");
      }
      if (input.containsKey("descrizione")) {
    	  descrizione = input.getString("descrizione");
      }
      if (input.containsKey("codiceFiscaleSA")) {
    	  codiceFiscaleSA = input.getString("codiceFiscaleSA");
      }

      if (validateTipoProgrammaOk && (tipoProgramma != null || StringUtils.isNotEmpty(codiceCUI) || StringUtils.isNotEmpty(descrizione) || StringUtils.isNotEmpty(codiceFiscaleSA)) ) {
        queryParamsOk = true;
      } else {
        queryParamsOk = false;
        output.put("esito", ESITO_KO);
        if (!validateTipoProgrammaOk) {
        	output.put("messaggio", TIPO_PROGRAMMA_ERRATO);
        } else {
        	output.put("messaggio", RICERCA_NO_PARAMETRI);
        }
      }

      if (queryParamsOk && validateTipoProgrammaOk) {
    	  String query = "select piatri.ANNTRI, inttri.CUIINT, inttri.DESINT, tecni.CFTEC, tecni.NOMETEI, tecni.COGTEI, inttri.ANNRIF, inttri.CODINT, inttri.CEFINT, inttri.CUPPRG, inttri.CODCPV " + 
    	  "from inttri join piatri on inttri.contri=piatri.contri left join tecni on inttri.codrup=tecni.codtec left join uffint on piatri.cenint=uffint.codein " +
    	  "where piatri.norma=2 and inttri.CUIINT is not null and (piatri.DADOZI is not null OR piatri.DAPTRI is not null)";

    	  // Ricerca per tipoProgramma
    	  if (tipoProgramma != null) {
    		  query += " and piatri.TIPROG=" + tipoProgramma;
    	  }

    	  // Ricerca per codiceCUI
    	  if (StringUtils.isNotEmpty(codiceCUI)) {
    		  query += " and UPPER(inttri.CUIINT) like '%" + codiceCUI.toUpperCase() + "%'";
    	  }
    	  
    	  // Ricerca per descrizione
    	  if (StringUtils.isNotEmpty(descrizione)) {
    		  query += " and UPPER(inttri.DESINT) like '%" + descrizione.toUpperCase() + "%'";
    	  }
    	  
    	  // Ricerca per codiceFiscaleSA
    	  if (StringUtils.isNotEmpty(codiceFiscaleSA)) {
    		  query += " and UPPER(uffint.CFEIN) = '" + codiceFiscaleSA.toUpperCase() + "'";
    	  }
    	  
    	  query += " ORDER BY inttri.CUIINT, piatri.DADOZI DESC, piatri.DAPTRI DESC";
    	  
    	  List<?> datiINTTRI = sqlManager.getListVector(query, new Object[] {});
    	  if (datiINTTRI != null && datiINTTRI.size() > 0) {
    		  String cuiPrecedente = "";
    		  for (int l = 0; l < datiINTTRI.size(); l++) {
    			  String cui = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 1).getValue();
    			  //se esistono più interventi con lo stesso CUI, restituire quelli del programma con data di adozione o approvazione più recente.
    			  if(!cui.equals(cuiPrecedente)) {
    				  JSONObject item = new JSONObject();
    				  Long anno = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 0).getValue();
        			  String oggetto = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 2).getValue();
        			  String codiceFiscaleRUP = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 3).getValue();
        			  String nomeRUP = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 4).getValue();
        			  String cognomeRUP = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 5).getValue();
        			  Long annoRiferimento = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 6).getValue();
        			  String codiceLavoro = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 7).getValue();
        			  String associato = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 8).getValue();
        			  String cup = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 9).getValue();
        			  String cpv = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(l), 10).getValue();
        			  
        			  item.put("anno", anno.toString());
        			  item.put("annualitaRiferimento", annoRiferimento.toString());
        			  item.put("cui", cui);
        			  item.put("descrizione", oggetto);
        			  item.put("codiceFiscaleRUP", codiceFiscaleRUP);
        			  item.put("nomeRUP", nomeRUP);
        			  item.put("cognomeRUP", cognomeRUP);
        			  if (associato != null && associato.equals("1")) {
        				  item.put("codiceLavoro", codiceLavoro);
        			  } else {
        				  item.put("codiceLavoro", "");
        			  }
        			  item.put("cup", cup);
        			  item.put("cpv", cpv);
        			  output.accumulate("interventoAcquisto", item);
    			  }
    			  cuiPrecedente = cui;
    		  }

    		  output.put("esito", ESITO_OK);
    	  } else {
    		  output.put("esito", ESITO_KO);
    		  output.put("messaggio", RICERCA_NO_DATA);
    	  }
      }

    } catch (JsonParseException e) {
      output.put("esito", ESITO_KO);
      output.put("messaggio", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } catch (JsonMappingException e) {
      output.put("esito", ESITO_KO);
      output.put("messaggio", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } catch (IOException e) {
      output.put("esito", ESITO_KO);
      output.put("messaggio", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } catch (SQLException e) {
      output.put("esito", ESITO_KO);
      output.put("messaggio", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    }

    return output;

  }

  /**
   * Restituisce il dettaglio dell' intervento/acquisto con il codice cui passato come parametro
   * 
   * @param in
   * @param request
   * @return
   */
  private JSONObject getDettaglio(String in, HttpServletRequest request) {

    JSONObject output = new JSONObject();
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", request.getSession().getServletContext(), SqlManager.class);

    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
	simbolo.setDecimalSeparator(',');
	simbolo.setGroupingSeparator('.');
	DecimalFormat decFormat = new DecimalFormat("###,###,###,##0.00", simbolo);
    try {
      String codiceCUI = null;

      boolean queryParamsOk = false;

      JSONObject input = new ObjectMapper().readValue(in, JSONObject.class);
  
      if (input.containsKey("codiceCUI")) {
    	  codiceCUI = input.getString("codiceCUI");
      }
      

      if (StringUtils.isNotEmpty(codiceCUI)) {
        queryParamsOk = true;
      } else {
        queryParamsOk = false;
        output.put("esito", ESITO_KO);
        output.put("messaggio", DETTAGLIO_NO_PARAMETRI);
      } 

      if (queryParamsOk) {
    	  String query = "select piatri.ANNTRI, inttri.ANNRIF, inttri.CUIINT, inttri.CUPPRG, inttri.DESINT, inttri.TOTINT, tecni.NOMETEI, tecni.COGTEI, " +
    	  "inttri.PRGINT, inttri.COMINT, inttri.NUTS, inttri.TIPINT, " +
    	  "inttri.DV1TRI, inttri.DV2TRI, inttri.DV3TRI, inttri.DV9TRI, " +
    	  "inttri.MU1TRI, inttri.MU2TRI, inttri.MU3TRI, inttri.MU9TRI, " +
    	  "inttri.PR1TRI, inttri.PR2TRI, inttri.PR3TRI, inttri.PR9TRI, " +
    	  "inttri.BI1TRI, inttri.BI2TRI, inttri.BI3TRI, inttri.BI9TRI, " +
    	  "inttri.AP1TRI, inttri.AP2TRI, inttri.AP3TRI, inttri.AP9TRI, " +
    	  "inttri.IM1TRI, inttri.IM2TRI, inttri.IM3TRI, inttri.IM9TRI, " +
    	  "inttri.AL1TRI, inttri.AL2TRI, inttri.AL3TRI, inttri.AL9TRI " +
    	  "from inttri join piatri on inttri.contri=piatri.contri left join tecni on inttri.codrup=tecni.codtec " +
    	  "where piatri.norma=2 and inttri.CUIINT = ? and (piatri.DADOZI is not null OR piatri.DAPTRI is not null) " +
    	  "ORDER BY inttri.CUIINT, piatri.DADOZI DESC, piatri.DAPTRI DESC";
    	  
    	  List<?> datiINTTRI = sqlManager.getListVector(query, new Object[] { codiceCUI });
    	  if (datiINTTRI != null && datiINTTRI.size() > 0) {
    		  JSONObject item = new JSONObject();
			  Long anno = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 0).getValue();
			  Long annoRiferimento = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 1).getValue();
			  String cui = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 2).getValue();
			  String cup = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 3).getValue();
			  String oggetto = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 4).getValue();
			  Double importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 5).getValue();
			  String nomeRUP = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 6).getValue();
			  String cognomeRUP = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 7).getValue();
			  Long priorita = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 8).getValue();
			  String istat = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 9).getValue();
			  String nuts = (String) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 10).getValue();
			  Long tipo = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 11).getValue();
			  
			  item.put("CUIINT", cui);
			  item.put("CUPPRG", cup);
			  item.put("DESINT", oggetto);
			  if (anno != null && annoRiferimento != null) {
				  item.put("ANNTRI", new Long(anno + annoRiferimento - 1).toString());
			  } else {
				  item.put("ANNTRI", "N.D");
			  }
			  if (importo != null) {
				  item.put("TOTINT", decFormat.format(importo));
			  } else {
				  item.put("TOTINT", decFormat.format(0));
			  }
			  String RUP = "";
			  if (nomeRUP != null) {
				  RUP += nomeRUP + " ";
			  }
			  if (cognomeRUP != null) {
				  RUP += cognomeRUP;
			  }
			  item.put("RUP", RUP);
			  if (priorita != null) {
				  String descrizionePriorita = (String)sqlManager.getObject("select TAB1DESC from TAB1 where TAB1COD='PT008' and TAB1TIP = ?", new Object[] { priorita });
				  item.put("PRGINT", descrizionePriorita);
			  }
			  if (istat != null) {
				  String comune = (String)sqlManager.getObject("select tabdesc from tabsche where tabcod='S2003' and tabcod1='09' and tabcod3 = ?", new Object[] { istat });
				  item.put("LOCALITA", comune);
				  if (istat.length() == 9) {
					  istat = istat.substring(3,6);
					  String provincia = (String)sqlManager.getObject("select tabdesc from tabsche where tabcod='S2003' and tabcod1='07' and " +
							  sqlManager.getDBFunction("SUBSTR", new String[]{"tabsche.tabcod2","2","3"}) + " = ?", new Object[] { istat });
					  item.put("PROVINCIA", provincia);
				  }
			  }
			  item.put("NUTS", nuts);
			  
			  // Quadro economico
			  Double totale = new Double(0);
			  Double totale1 = new Double(0);
			  Double totale2 = new Double(0);
			  Double totale3 = new Double(0);
			  Double totale4 = new Double(0);
			  
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 12).getValue();
			  totale += (importo != null)?importo:0;
			  totale1 += (importo != null)?importo:0;
			  item.put("DV1TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 13).getValue();
			  totale += (importo != null)?importo:0;
			  totale2 += (importo != null)?importo:0;
			  item.put("DV2TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 14).getValue();
			  totale += (importo != null)?importo:0;
			  totale3 += (importo != null)?importo:0;
			  item.put("DV3TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 15).getValue();
			  totale += (importo != null)?importo:0;
			  totale4 += (importo != null)?importo:0;
			  item.put("DV9TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  item.put("DVNTRI", (totale != null)?decFormat.format(totale):decFormat.format(0));
			  
			  totale = new Double(0);
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 16).getValue();
			  totale += (importo != null)?importo:0;
			  totale1 += (importo != null)?importo:0;
			  item.put("MU1TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 17).getValue();
			  totale += (importo != null)?importo:0;
			  totale2 += (importo != null)?importo:0;
			  item.put("MU2TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 18).getValue();
			  totale += (importo != null)?importo:0;
			  totale3 += (importo != null)?importo:0;
			  item.put("MU3TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 19).getValue();
			  totale += (importo != null)?importo:0;
			  totale4 += (importo != null)?importo:0;
			  item.put("MU9TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  item.put("MUNTRI", (totale != null)?decFormat.format(totale):decFormat.format(0));
			  
			  totale = new Double(0);
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 20).getValue();
			  totale += (importo != null)?importo:0;
			  totale1 += (importo != null)?importo:0;
			  item.put("PR1TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 21).getValue();
			  totale += (importo != null)?importo:0;
			  totale2 += (importo != null)?importo:0;
			  item.put("PR2TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 22).getValue();
			  totale += (importo != null)?importo:0;
			  totale3 += (importo != null)?importo:0;
			  item.put("PR3TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 23).getValue();
			  totale += (importo != null)?importo:0;
			  totale4 += (importo != null)?importo:0;
			  item.put("PR9TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  item.put("PRNTRI", (totale != null)?decFormat.format(totale):decFormat.format(0));
			  
			  totale = new Double(0);
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 24).getValue();
			  totale += (importo != null)?importo:0;
			  totale1 += (importo != null)?importo:0;
			  item.put("BI1TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 25).getValue();
			  totale += (importo != null)?importo:0;
			  totale2 += (importo != null)?importo:0;
			  item.put("BI2TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 26).getValue();
			  totale += (importo != null)?importo:0;
			  totale3 += (importo != null)?importo:0;
			  item.put("BI3TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 27).getValue();
			  totale += (importo != null)?importo:0;
			  totale4 += (importo != null)?importo:0;
			  item.put("BI9TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  item.put("BINTRI", (totale != null)?decFormat.format(totale):decFormat.format(0));
			  
			  totale = new Double(0);
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 28).getValue();
			  totale += (importo != null)?importo:0;
			  totale1 += (importo != null)?importo:0;
			  item.put("AP1TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 29).getValue();
			  totale += (importo != null)?importo:0;
			  totale2 += (importo != null)?importo:0;
			  item.put("AP2TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 30).getValue();
			  totale += (importo != null)?importo:0;
			  totale3 += (importo != null)?importo:0;
			  item.put("AP3TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 31).getValue();
			  totale += (importo != null)?importo:0;
			  totale4 += (importo != null)?importo:0;
			  item.put("AP9TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  item.put("APNTRI", (totale != null)?decFormat.format(totale):decFormat.format(0));
			  
			  totale = new Double(0);
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 32).getValue();
			  totale += (importo != null)?importo:0;
			  totale1 += (importo != null)?importo:0;
			  item.put("IM1TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 33).getValue();
			  totale += (importo != null)?importo:0;
			  totale2 += (importo != null)?importo:0;
			  item.put("IM2TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 34).getValue();
			  totale += (importo != null)?importo:0;
			  totale3 += (importo != null)?importo:0;
			  item.put("IM3TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 35).getValue();
			  totale += (importo != null)?importo:0;
			  totale4 += (importo != null)?importo:0;
			  item.put("IM9TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  item.put("IMNTRI", (totale != null)?decFormat.format(totale):decFormat.format(0));
			  
			  totale = new Double(0);
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 36).getValue();
			  totale += (importo != null)?importo:0;
			  totale1 += (importo != null)?importo:0;
			  item.put("AL1TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 37).getValue();
			  totale += (importo != null)?importo:0;
			  totale2 += (importo != null)?importo:0;
			  item.put("AL2TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 38).getValue();
			  totale += (importo != null)?importo:0;
			  totale3 += (importo != null)?importo:0;
			  item.put("AL3TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  importo = (Double) SqlManager.getValueFromVectorParam(datiINTTRI.get(0), 39).getValue();
			  totale += (importo != null)?importo:0;
			  totale4 += (importo != null)?importo:0;
			  item.put("AL9TRI", (importo != null)?decFormat.format(importo):decFormat.format(0));
			  item.put("ALNTRI", (totale != null)?decFormat.format(totale):decFormat.format(0));
			  
			  item.put("TO1INT", (totale1 != null)?decFormat.format(totale1):decFormat.format(0));
			  item.put("TO2INT", (totale2 != null)?decFormat.format(totale2):decFormat.format(0));
			  item.put("TO3INT", (totale3 != null)?decFormat.format(totale3):decFormat.format(0));
			  item.put("TO9INT", (totale4 != null)?decFormat.format(totale4):decFormat.format(0));
			  
			  output.put("interventoAcquisto", item);
    		  output.put("esito", ESITO_OK);
    	  } else {
    		  output.put("esito", ESITO_KO);
    		  output.put("messaggio", RICERCA_NO_DATA);
    	  }
      }

    } catch (JsonParseException e) {
      output.put("esito", ESITO_KO);
      output.put("messaggio", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } catch (JsonMappingException e) {
      output.put("esito", ESITO_KO);
      output.put("messaggio", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } catch (IOException e) {
      output.put("esito", ESITO_KO);
      output.put("messaggio", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    } catch (SQLException e) {
      output.put("esito", ESITO_KO);
      output.put("messaggio", ERROR_MSG + e.getMessage());
      logger.error("Errore durante la richiesta", e);
    }

    return output;

  }
  
}
