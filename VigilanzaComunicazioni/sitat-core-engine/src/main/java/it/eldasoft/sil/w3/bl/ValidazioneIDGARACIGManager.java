package it.eldasoft.sil.w3.bl;
 
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.metadata.cache.DizionarioCampi;
import it.eldasoft.utils.metadata.domain.Campo;
import it.eldasoft.utils.utility.UtilityFiscali;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

public class ValidazioneIDGARACIGManager {

  static Logger      logger = Logger.getLogger(ValidazioneIDGARACIGManager.class);

  private SqlManager sqlManager;

  /**
   *
   * @return
   */
  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  /**
   * @param sqlManager
   *        sqlManager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   *
   * @param params
   * @return
   * @throws JspException
   */
  public HashMap<String, Object> validate(Object[] params) throws JspException {
    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
    String entita = (String) params[1];
    Long syscon = new Long((Integer) params[2]);
    Long numgara = new Long((String) params[3]);
    entita = UtilityStringhe.convertiNullInStringaVuota(entita);
    Long numlott = null;
    Long numreq = null;

    if(!"".equals(entita)){
      if("W3LOTT".equals(entita)){
        numlott = new Long((String) params[4]);
      }
      if("W3GARAREQ".equals(entita)){
        numreq = new Long((String) params[4]);
      }
    }

    infoValidazione = this.validate(entita, syscon, numgara, numlott, numreq);
    return infoValidazione;
  }

  /**
   * Validazione dati della gara (specifico per la pubblicazione della gara e
   * dei lotti)
   *
   * @param syscon
   * @param numgara
   * @return
   * @throws JspException
   */
  public HashMap<String, Object> validateW3GARAPUBBLICA(Long syscon,
      Long numgara) throws JspException {

    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
    infoValidazione = this.validate("W3GARAPUBBLICA", syscon, numgara, null, null);
    return infoValidazione;

  }

  /**
   * Validazione dei dati della gara
   *
   * @param numgara
   * @return
   * @throws JspException
   */
  public HashMap<String, Object> validateW3GARA(Long syscon, Long numgara)
      throws JspException {

    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
    infoValidazione = this.validate("W3GARA", syscon, numgara, null, null);
    return infoValidazione;
  }

  /**
   * Validazione dei dati del lotto.
   *
   * @param numgara
   * @param numlott
   * @return
   * @throws JspException
   */
  public HashMap<String, Object> validateW3LOTT(Long syscon, Long numgara,
      Long numlott) throws JspException {

    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
    infoValidazione = this.validate("W3LOTT", syscon, numgara, numlott, null);
    return infoValidazione;
  }

  /**
   * Validazione dei dati smartcig
   *
   * @param numgara
   * @return
   * @throws JspException
   */
  public HashMap<String, Object> validateW3SMARTCIG(Long syscon, Long numgara)
      throws JspException {

    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
    infoValidazione = this.validate("W3SMARTCIG", syscon, numgara, null, null);
    return infoValidazione;
  }
  /**
   * Validazione dei dati del requisito.
   *
   * @param numgara
   * @param numreq
   * @return
   * @throws JspException
   */
  public HashMap<String, Object> validateW3GARAREQ(Long syscon, Long numgara,
      Long numreq) throws JspException {

    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
    infoValidazione = this.validate("W3GARAREQ", syscon, numgara, null, numreq);
    return infoValidazione;
  }

  /**
   * Validate generico
   *
   * @param entita
   * @param syscon
   * @param numgara
   * @param numlott
   * @return
   * @throws JspException
   */
  private HashMap<String, Object> validate(String entita, Long syscon,
      Long numgara, Long numlott, Long numreq) throws JspException {
    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();

    List<Object> listaControlli = new Vector<Object>();

    try {

      String titolo = null;

      if ("W3GARA".equals(entita)) {
        titolo = "Dati della gara";
        this.validazioneW3GARA(sqlManager, numgara, syscon, listaControlli);

      } else if ("W3LOTT".equals(entita)) {
        titolo = "Dati del lotto";
        this.validazioneW3LOTT(sqlManager, numgara, numlott, syscon, listaControlli);

      } else if ("W3GARAPUBBLICA".equals(entita)) {
        titolo = "Dati di pubblicazione";
        this.validazioneW3GARAPUBBLICA(sqlManager, numgara, syscon, listaControlli);

      } else if ("W3GARAREQ".equals(entita)) {
        titolo = "Dati requisiti";
        this.validazioneW3GARAREQ(sqlManager, numgara, numreq, syscon, listaControlli);
      } else if ("W3SMARTCIG".equals(entita)) {
        titolo = "Dati richiesta SMARTCIG";
        this.validazioneW3SMARTCIG(sqlManager, numgara, syscon, listaControlli);
      }

      infoValidazione.put("titolo", titolo);
      infoValidazione.put("listaControlli", listaControlli);

      int numeroErrori = 0;
      int numeroWarning = 0;

      if (!listaControlli.isEmpty()) {
        for (int i = 0; i < listaControlli.size(); i++) {
          Object[] controllo = (Object[]) listaControlli.get(i);
          String tipo = (String) controllo[0];

          if ("E".equals(tipo)) {
            numeroErrori++;
          }
          if ("W".equals(tipo)) {
            numeroWarning++;
          }
        }
      }

      infoValidazione.put("numeroErrori", new Long(numeroErrori));
      infoValidazione.put("numeroWarning", new Long(numeroWarning));

    } catch (GestoreException e) {
      throw new JspException("Errore nella funzione di controllo dei dati", e);
    }

    return infoValidazione;
  }

  /**
   *
   * @param sqlManager
   * @param numgara
   * @param syscon
   * @param listaControlli
   * @throws GestoreException
   */
  private void validazioneW3GARAPUBBLICA(SqlManager sqlManager, Long numgara,
      Long syscon, List<Object> listaControlli) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("validazioneW3GARA-PUBBLICA: inizio metodo");

    String pagina = "Pubblicazione";

    try {
      String selectW3GARA = "select data_perfezionamento_bando, " // 0
        + "data_termine_pagamento, " // 1
        + "tipo_operazione, " // 2
        + "data_guce, " // 3
        + "numero_guce, " // 4
        + "data_guri, " // 5
        + "numero_guri, " // 6
        + "data_bore, " // 7
        + "numero_bore, " // 8
        + "ora_scadenza, " // 9
        + "dscad_richiesta_invito, " // 10
        + "data_lettera_invito, " // 11
        + "flag_benicult, " // 12
        + "MODO_REALIZZAZIONE, " // 13
        + "SITO_MINISTERO_INF_TRASF, " // 14
        + "rup_codtec "// 15
        + "from w3gara where numgara = ?";

      List<?> datiW3GARA = sqlManager.getVector(selectW3GARA, new Object[] { numgara });

      if (datiW3GARA != null && datiW3GARA.size() > 0) {

    	  String rup_codtec = (String) SqlManager.getValueFromVectorParam(
    	            datiW3GARA, 15).getValue();
    	  if (isStringaValorizzata(rup_codtec)) {
    		  //verifico che il rup sia effettivamente associato all'utente loggato nella tabella w3usrsys
              Long esisteAssociazione = (Long) sqlManager.getObject("select count(*) from w3usrsys where syscon = ? and rup_codtec = ?", new Object[] {syscon, rup_codtec});
              if (esisteAssociazione.equals(new Long(0))) {
            	  String descrizione = "Responsabile unico del procedimento";
                  String messaggio = "Il RUP della gara non è presente nella lista dei tuoi RUP associati. Verifica dalla Home Page al link \"Collega/Importa RUP e Centri di Costo da SIMOG\"";
                  listaControlli.add(((new Object[] { "E", pagina,
                      descrizione, messaggio })));
              }
    	  }
    	  
        Object importoObj = sqlManager.getObject("select SUM(" + sqlManager.getDBFunction("isnull", new String[] {"IMPORTO_LOTTO", "0.00" }) + ") from W3LOTT where NUMGARA=?", 
            new Object[] { numgara });

    	  Double importo_gara = new Double(0);
        if (importoObj != null) {
          if (!(importoObj instanceof Double)) {
          	importo_gara = new Double(importoObj.toString());
          } else {
          	importo_gara = (Double) importoObj;
          }
        } 
    	  //modalità realizzazione
    	  Long modalitaRealizzazione = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 13).getValue();
        // Data pubblicazione
        Date data_perfezionamento_bando = (Date) SqlManager.getValueFromVectorParam(
            datiW3GARA, 0).getValue();
        if (data_perfezionamento_bando == null) {
          this.addCampoObbligatorio(listaControlli, "W3GARA", "DATA_PERFEZIONAMENTO_BANDO",
              pagina);
        }

        // Data termine pagamento
        Date data_termine_pagamento = (Date) SqlManager.getValueFromVectorParam(
            datiW3GARA, 1).getValue();
        if (data_termine_pagamento == null) {
        	if (modalitaRealizzazione == null || (!modalitaRealizzazione.equals(new Long(2)) && !modalitaRealizzazione.equals(new Long(11)))) {
        		this.addAvviso(listaControlli, "W3GARA", "Data scadenza per la presentazione delle offerte (2)", "E", pagina,
                        "Il campo è obbligatorio.", "");
        	}
        }
        
        if(data_perfezionamento_bando != null && data_termine_pagamento != null &&
        		data_perfezionamento_bando.compareTo(data_termine_pagamento)>=0)
        {
        	this.addAvviso(listaControlli, "W3GARA", "Data scadenza per la presentazione delle offerte (2)", "E", pagina,
                    "La data di scadenza per la presentazione delle offerte deve essere successiva alla data di pubblicazione", "");
        }
        
        // Tipo operazione
        String tipo_operazione = (String) SqlManager.getValueFromVectorParam(
            datiW3GARA, 2).getValue();
        if (!isStringaValorizzata(tipo_operazione)) {
          this.addCampoObbligatorio(listaControlli, "W3GARA", "TIPO_OPERAZIONE",
              pagina);
        }

        // Pubblicazioni
        Date data_guce = (Date) SqlManager.getValueFromVectorParam(
            datiW3GARA, 3).getValue();
        String numero_guce = (String) SqlManager.getValueFromVectorParam(
            datiW3GARA, 4).getValue();
        if (data_guce != null && numero_guce == null) {
          this.addCampoObbligatorio(listaControlli, "W3GARA", "NUMERO_GUCE",
              pagina);
        }

        Date data_guri = (Date) SqlManager.getValueFromVectorParam(
            datiW3GARA, 5).getValue();
        String numero_guri = (String) SqlManager.getValueFromVectorParam(
           datiW3GARA, 6).getValue();
        if (data_guri != null && numero_guri == null) {
          this.addCampoObbligatorio(listaControlli, "W3GARA", "NUMERO_GURI",
              pagina);
        }

        Date data_bore = (Date) SqlManager.getValueFromVectorParam(
            datiW3GARA, 7).getValue();
        String numero_bore = (String) SqlManager.getValueFromVectorParam(
            datiW3GARA, 8).getValue();
        if (data_bore != null && numero_bore == null) {
          this.addCampoObbligatorio(listaControlli, "W3GARA", "NUMERO_BORE",
              pagina);
        }

        // Ora scadenza pagamenti
        String ora_scadenza = (String) SqlManager.getValueFromVectorParam(
            datiW3GARA, 9).getValue();
        if (isStringaValorizzata(ora_scadenza)) {
          this.validazioneOrario(ora_scadenza, "W3GARA", "ORA_SCADENZA", pagina, listaControlli);
        }
        
        //Sito Informatico Ministero Infrastrutture e piattaforma digitale ANAC tramite i sistemi informatizzati regionali
        String flagSitoMinistero = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 14).getValue();
        if (isStringaValorizzata(flagSitoMinistero)) {
        	if(flagSitoMinistero.equals("2")) {
        		if (importo_gara>= 500000) {
            		this.addAvviso(listaControlli, "W3GARA", "Sito Informatico Ministero Infrastrutture", "E", pagina,
                            "Non è stata effettuata la pubblicazione sul sito informatico del Ministero delle Infrastrutture", "");
            	}
        	}
        }
        
        Long proceduraContraente = (Long)sqlManager.getObject("select ID_SCELTA_CONTRAENTE from W3LOTT where NUMGARA = ? and NUMLOTT = 1", new Object[] { numgara });
    	//Data di scadenza per la presentazione della richiesta di invito 2 e 8 
        Date dscad_richiesta_invito = (Date) SqlManager.getValueFromVectorParam(
                    datiW3GARA, 10).getValue();
                if (dscad_richiesta_invito == null && (new Long(2).equals(proceduraContraente) || new Long(8).equals(proceduraContraente) || new Long(30).equals(proceduraContraente)
                		|| new Long(29).equals(proceduraContraente))) {
                  this.addCampoObbligatorio(listaControlli, "W3GARA", "DSCAD_RICHIESTA_INVITO",
                      pagina);
                }

                //Data della lettera di invito
                
                
        Date data_lettera_invito = (Date) SqlManager.getValueFromVectorParam(
                  datiW3GARA, 11).getValue();
        if (data_lettera_invito == null && (new Long(2).equals(proceduraContraente) || new Long(8).equals(proceduraContraente) || new Long(30).equals(proceduraContraente)
        		|| new Long(29).equals(proceduraContraente))) {
            this.addCampoObbligatorio(listaControlli, "W3GARA", "DATA_LETTERA_INVITO",
                 pagina);
        }

        String selectCIGW3LOTT = "select numlott," //0
          + " cig " // 1
          + "from w3lott where numgara = ? and cig is not null ";

        List<?> datiCIGW3LOTT = sqlManager.getVector(selectCIGW3LOTT, new Object[] { numgara });
        if (datiCIGW3LOTT != null && datiCIGW3LOTT.size() > 0) {
          ;
        }else{
          // Controllo presenza documento allegato
          this.addAvviso(listaControlli, "W3LOTT", "CIG assegnati", "E", pagina,
              "Non esiste alcun CIG assegnato", "");
        }
        String beniCulturali = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 12).getValue();
        Long documentoBando = (Long)sqlManager.getObject("select count(*) from w3garadoc where NUMGARA = ? and tipo_documento = '1'", new Object[] { numgara });
        Long documentoLetteraInvito = (Long)sqlManager.getObject("select count(*) from w3garadoc where NUMGARA = ? and tipo_documento = '5'", new Object[] { numgara });
        Long documentoDisciplinare = (Long)sqlManager.getObject("select count(*) from w3garadoc where NUMGARA = ? and tipo_documento = '2'", new Object[] { numgara });
    	
        //Bando di gara
        if ("1".equals(tipo_operazione)) {
        	Long contratto_lavori = (Long)sqlManager.getObject("select count(*) from W3LOTT where NUMGARA = ? and TIPO_CONTRATTO='L'", new Object[] { numgara });
        	Long scelta_contraente = (Long)sqlManager.getObject("select count(*) from W3LOTT where NUMGARA = ? and ID_SCELTA_CONTRAENTE in(1,2,3,9,13)", new Object[] { numgara });
        	
        	if (importo_gara >= 500000 && contratto_lavori>0 && (scelta_contraente>0 || beniCulturali.equals("1"))) {
        		//se non cè il doc bando do errore
        		if (documentoBando == 0) {
            		this.addAvviso(listaControlli, "W3GARADOC", "Bando di gara", "E", pagina,
            	              "previsto l'inserimento dell'allegato", "");
            	}
            }
        	if (documentoLetteraInvito > 0) {
        		this.addAvviso(listaControlli, "W3GARADOC", "Lettera di Invito o avviso di preinformazione", "E", pagina,
        	              "allegato non previsto", "");
        	}
        } else if ("3".equals(tipo_operazione)) {
        	if(documentoBando > 0) {
        		this.addAvviso(listaControlli, "W3GARADOC", "Bando di gara", "E", pagina,
      	              "allegato non previsto", "");
        	}
        	if(documentoDisciplinare > 0) {
        		this.addAvviso(listaControlli, "W3GARADOC", "Disciplinare", "E", pagina,
      	              "allegato non previsto", "");
        	}
        	/*if (documentoLetteraInvito == 0) {
        		this.addAvviso(listaControlli, "W3GARADOC", "Lettera di Invito o avviso di preinformazione", "E", pagina,
        	              "previsto l'inserimento dell'allegato", "");
        	}*/
        }
      }

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella lettura delle informazioni relative alla gara",
          "validazioneW3GARA", e);
    }
    if (logger.isDebugEnabled())
      logger.debug("validazioneW3GARA-PUBBLICA: fine metodo");

  }

  /**
   * Validazione dei dati della gara
   *
   * @param sqlManager
   * @param id
   * @param listaControlli
   * @throws GestoreException
   */
  private void validazioneW3GARA(SqlManager sqlManager, Long numgara,
      Long syscon, List<Object> listaControlli) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("validazioneW3GARA: inizio metodo");

    String pagina = "";
    boolean v3_04_6_Attiva = false;

    try {

      String selectW3GARA = "select oggetto, " // 0
          + "tipo_scheda, " 		// 1
          + "modo_indizione, " 		// 2
          + "modo_realizzazione, " 	// 3
          + "rup_codtec, " 			// 4
          + "collaborazione, " 		// 5
          + "escluso_avcpass, " 	// 6
          + "m_rich_cig, " 			// 7
          + "cig_acc_quadro, "		// 8
          + "NUMERO_LOTTI, "		// 9
          + "URGENZA_DL133, "		// 10
          + "ESTREMA_URGENZA, "		// 11
          + "ALLEGATO_IX, "			// 12
          + "STRUMENTO_SVOLGIMENTO, "// 13
          + "DURATA_ACCQUADRO, "// 14
          + "STATO_SIMOG, "// 15
          + "VER_SIMOG, "// 16
          + "FLAG_SA_AGENTE_GARA, " // 17
          + "ID_F_DELEGATE, " 		// 18
          + "CF_AMM_AGENTE_GARA " 	// 19
          + "from w3gara where numgara = ?";

      String selectW3GARAMERC = "select categoria " 
          + "from w3garamerc where numgara = ?";
      
      String selectCategorieLottiNonPresenti = "SELECT DISTINCT CATEGORIA_MERC, CIG FROM W3LOTT WHERE "
    	  +" NUMGARA = ? AND STATO_SIMOG IN (2,4) "
    	  +" AND CATEGORIA_MERC NOT IN (SELECT CATEGORIA FROM W3GARAMERC WHERE NUMGARA = ?)";
      
      List<?> datiW3GARA = sqlManager.getVector(selectW3GARA,
          new Object[] { numgara });

      if (datiW3GARA != null && datiW3GARA.size() > 0) {

    	  v3_04_6_Attiva = UtilitySITAT.v3_04_6_Attiva(sqlManager, numgara);
        
    	  pagina = "Sezione I: RUP e COLLABORAZIONE";
        Long statoSimog = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 15).getValue();
        Long versioneSimog = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 16).getValue();
        // Codice fiscale del responsabile unico del procedimento
        String rup_codtec = (String) SqlManager.getValueFromVectorParam(
            datiW3GARA, 4).getValue();
        if (!isStringaValorizzata(rup_codtec)) {
          String descrizione = "Responsabile unico del procedimento";
          String messaggio = "E' obbligatorio indicare il RUP.";
          listaControlli.add(((new Object[] { "E", pagina,
              descrizione, messaggio })));
        } else {
          String cftec = (String) sqlManager.getObject(
              "select cftec from tecni where codtec = ?",
              new Object[] { rup_codtec });
          if (!isStringaValorizzata(cftec))
            this.addCampoObbligatorio(listaControlli, "TECNI", "CFTEC", pagina);
          //verifico che il rup sia effettivamente associato all'utente loggato nella tabella w3usrsys
          Long esisteAssociazione = (Long) sqlManager.getObject("select count(*) from w3usrsys where syscon = ? and rup_codtec = ?", new Object[] {syscon, rup_codtec});
          if (esisteAssociazione.equals(new Long(0))) {
        	  String descrizione = "Responsabile unico del procedimento";
              String messaggio = "Il RUP della gara non è presente nella lista dei tuoi RUP associati. Verifica dalla Home Page al link \"Collega/Importa RUP e Centri di Costo da SIMOG\"";
              listaControlli.add(((new Object[] { "E", pagina,
                  descrizione, messaggio })));
          }
        }

        /*String w3usrsys_rup_codtec = (String) sqlManager.getObject(
            "select rup_codtec from w3usrsys where syscon = ?",
            new Object[] { syscon });
        if (!isStringaValorizzata(w3usrsys_rup_codtec)) {
          String descrizione = "Responsabile unico del procedimento";
          String messaggio = "Non e' stato definito il RUP associato all'utente applicativo. "
              + "Per aggiornare questa informazione e la lista delle collaborazioni "
              + "utilizzare la scheda \"Informazioni RUP\" accessibile dalla pagina iniziale.";
          listaControlli.add(((new Object[] { "W", pagina,
              descrizione, messaggio })));
        }*/

        // Controllo che il RUP indicato nella gara sia lo stesso RUP associato
        // all'utente corrente
        /*if (isStringaValorizzata(rup_codtec)
            && isStringaValorizzata(w3usrsys_rup_codtec)) {
          if (!rup_codtec.equals(w3usrsys_rup_codtec)) {
            String descrizione = "Responsabile unico del procedimento";
            String messaggio = "Il RUP indicato tra i dati della gara ed "
                + "il RUP associato all'utente corrente non sono la stessa persona. "
                + "Per l'invio dei dati è necessario che le due figure coincidano.";
            listaControlli.add(((new Object[] { "W", pagina,
                descrizione, messaggio })));
          }
        }*/

        if (SqlManager.getValueFromVectorParam(datiW3GARA, 5).getValue() == null) {
          String descrizione = "Collaborazione associata al RUP";
          String messaggio = "E' obbligatorio indicare la collaborazione.";
          listaControlli.add(((new Object[] { "E", pagina,
              descrizione, messaggio })));
        }

        pagina = "Sezione II: dati della gara";

        // Oggetto della gara
        String oggetto = (String) SqlManager.getValueFromVectorParam(
            datiW3GARA, 0).getValue();
        if (!isStringaValorizzata(oggetto)) {
          this.addCampoObbligatorio(listaControlli, "W3GARA", "OGGETTO", pagina);
        }

        // Tipo di settore (ordinario o speciale)
        String tipo_scheda = (String) SqlManager.getValueFromVectorParam(
            datiW3GARA, 1).getValue();
        if (!isStringaValorizzata(tipo_scheda)) {
          this.addCampoObbligatorio(listaControlli, "W3GARA", "TIPO_SCHEDA",
              pagina);
        }

        // Modalità di indizione
        Long modo_indizione = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 2).getValue();
        Long allegato_ix = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 12).getValue();
        if (modo_indizione == null) {
        	if (tipo_scheda != null && "S".equals(tipo_scheda) && allegato_ix == null) {
        		listaControlli.add(((new Object[] { "E", pagina,
        				this.getDescrizioneCampo("W3GARA", "MODO_INDIZIONE"), "Modalita' di indizione (settori speciali) oppure Modalita' di indizione servizi di cui all'allegato IX: selezionare un valore tra quelli previsti" })));
        	}
        } else {
        	if (tipo_scheda != null && "O".equals(tipo_scheda)) {
        		listaControlli.add(((new Object[] { "E", pagina,
        				this.getDescrizioneCampo("W3GARA", "MODO_INDIZIONE"), "Valore non richiesto per il settore del contratto selezionato" })));
        	} else if (allegato_ix != null) {
        		listaControlli.add(((new Object[] { "E", pagina,
        				this.getDescrizioneCampo("W3GARA", "MODO_INDIZIONE"), "I campi Modalità di indizione (settori speciali) e Modalità di indizione servizi di cui all'allegato IX non possono essere valorizzati contemporaneamente" })));
        	}
        	//A partire dalla versione 3.04.4 in fase di modifica 
            if (versioneSimog.equals(new Long(4))) {
            	if(statoSimog.equals(new Long(3))) {
            		//il valore del campo 'modalità di indizione (settori speciali)' nella Scheda GARA non è coerente con il valore dei campi 'scelta del contraente' e ' Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara' del/dei CIG
            		if (!modo_indizione.equals(new Long(2))) {
            			String selectW3LOTT = "select numlott, cig from w3lott where numgara = ? and cig is not null";
            			List<?> datiW3LOTT = sqlManager.getListVector(selectW3LOTT, new Object[] { numgara });
            			if (datiW3LOTT != null && datiW3LOTT.size() > 0) {
            	            for (int i = 0; i < datiW3LOTT.size(); i++) {
            	            	Long numlott = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT.get(i), 0).getValue();
            	            	String cig = (String) SqlManager.getValueFromVectorParam(datiW3LOTT.get(i), 1).getValue();
            	            	String selectW3COND = "select count(*) from w3cond where numgara = ? and numlott = ? ";
                                Long countCond = (Long) this.sqlManager.getObject(selectW3COND, new Object[] {numgara, numlott });
                                if (!(countCond > 0)) {
                                	// Controllo presenza almeno un valore
                                	listaControlli.add(((new Object[] { "E", pagina,
                            				this.getDescrizioneCampo("W3GARA", "MODO_INDIZIONE"), "il valore del campo 'modalità di indizione (settori speciali)' nella Scheda GARA non è coerente con il valore dei campi 'scelta del contraente' e ' Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara' del CIG " + cig})));
                                }
            	            }
            			}
            		}
            	}
            }
        }
        //Modalità di indizione servizi di cui all’allegato IX
        if (allegato_ix == null) {
        	if (tipo_scheda != null && "S".equals(tipo_scheda) && modo_indizione == null) {
        		listaControlli.add(((new Object[] { "E", pagina,
        				this.getDescrizioneCampo("W3GARA", "ALLEGATO_IX"), "Modalita' di indizione (settori speciali) oppure Modalita' di indizione servizi di cui all'allegato IX: selezionare un valore tra quelli previsti" })));
        	}
        } else {
        	if (modo_indizione != null) {
        		listaControlli.add(((new Object[] { "E", pagina,
        				this.getDescrizioneCampo("W3GARA", "ALLEGATO_IX"), "I campi i Modalità di indizione (settori speciali) e Modalità di indizione servizi di cui all'allegato IX non possono essere valorizzati contemporaneamente" })));
        	}
        }
        // Modalità di realizzazione
        Long modo_realizzazione = (Long) SqlManager.getValueFromVectorParam(
            datiW3GARA, 3).getValue();
        if (modo_realizzazione == null) {
          this.addCampoObbligatorio(listaControlli, "W3GARA",
              "MODO_REALIZZAZIONE", pagina);
        } 
        //Durata della convenzione o accordo quadro in giorni
        if (!v3_04_6_Attiva) {
        	Long durataAccQuadro = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 14).getValue();
            if (durataAccQuadro == null) {
            	if (modo_realizzazione != null && (modo_realizzazione.equals(new Long(9)) || 
            			modo_realizzazione.equals(new Long(17)) || modo_realizzazione.equals(new Long(18)))) {
            		listaControlli.add(((new Object[] { "E", pagina,
                            "Durata della convenzione o accordo quadro in giorni", "Indicare la durata della convenzione o accordo quadro in giorni" })));
            	}
            } else {
            	if (modo_realizzazione != null && !(modo_realizzazione.equals(new Long(9)) || 
            			modo_realizzazione.equals(new Long(17)) || modo_realizzazione.equals(new Long(18)))) {
            		listaControlli.add(((new Object[] { "E", pagina,
                            "Durata della convenzione o accordo quadro in giorni", "il campo 'Durata della convenzione o accordo quadro in giorni' non è previsto" })));
            	}
            }
        }
        
        //Strumenti per lo svolgimento delle procedure
        Long strumentiSvolgimento = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 13).getValue();
        if (strumentiSvolgimento == null) {
        	this.addCampoObbligatorio(listaControlli, "W3GARA", "STRUMENTO_SVOLGIMENTO", pagina);
        } else {
        	//Se valorizzato a 6 “Accordo quadro” e S01.13 <> 3, 4, 5 , 8, 12, 17, 18
        	if (strumentiSvolgimento.equals(new Long(6))) {
        		if(!modo_realizzazione.equals(new Long(3)) && !modo_realizzazione.equals(new Long(4)) && !modo_realizzazione.equals(new Long(5)) && 
        				!modo_realizzazione.equals(new Long(8)) && !modo_realizzazione.equals(new Long(12)) && !modo_realizzazione.equals(new Long(17)) && !modo_realizzazione.equals(new Long(18))) {
        			listaControlli.add(((new Object[] { "E", pagina,
                            "Strumenti per lo svolgimento delle procedure", "e' possibile selezionare 'Accordo quadro' solo in caso di modalita' di realizzazione equivalente in concessioni, scelta del socio privato nella societa' mista, accordo quadro o convenzione" })));
        		}
        	} else {
        		//Se non valorizzato a 6 e S01.13=17 o 18
        		if(modo_realizzazione.equals(new Long(17)) || modo_realizzazione.equals(new Long(18))) {
        			listaControlli.add(((new Object[] { "E", pagina,
                            "Strumenti per lo svolgimento delle procedure", "in caso di accordo quadro o convenzione, il valore di ' Strumenti per lo svolgimento delle procedure ' deve essere 'Accordo quadro'" })));
        		}
        	}
        }
        //CIG relativo all’accordo quadro/convenzione cui si aderisce
        String cig_acc_quadro = (String) SqlManager.getValueFromVectorParam(
	            datiW3GARA, 8).getValue();
        if (!isStringaValorizzata(cig_acc_quadro)) {
        	if (modo_realizzazione.equals(new Long(11)) || modo_realizzazione.equals(new Long(2))) {
        		this.addCampoObbligatorio(listaControlli, "W3GARA",
      	              "CIG_ACC_QUADRO", pagina);
        	}
        } else {
        	if (cig_acc_quadro.length() != 10) {
        		listaControlli.add(((new Object[] { "E", pagina,
                        "CIG relativo all’accordo quadro/convenzione cui si aderisce", "Codice CIG non valido" })));
        	}
        }
        //Numero lotti
        //Long numeroLotti = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 9).getValue();
        /*if (numeroLotti == null || numeroLotti<1) {
        	String descrizione = "Numero Totale Lotti";
            String messaggio = "Il numero dei lotti deve essere maggiore o uguale ad 1.";
            listaControlli.add(((new Object[] { "E", pagina,
                descrizione, messaggio })));
        }*/
        // Esclusione AVCPASS
        String esclusione_avcpass = (String) SqlManager.getValueFromVectorParam(
            datiW3GARA, 6).getValue();
        if (!isStringaValorizzata(esclusione_avcpass)) {
          this.addCampoObbligatorio(listaControlli, "W3GARA",
              "ESCLUSO_AVCPASS", pagina);
        }
        // Estrema urgenza
        String flagEstremaUrgenza = (String) SqlManager.getValueFromVectorParam(
            datiW3GARA, 10).getValue();
        if (!isStringaValorizzata(flagEstremaUrgenza)) {
          this.addCampoObbligatorio(listaControlli, "W3GARA",
              "URGENZA_DL133", pagina);
        } 
        //Motivo urgenza
        Long estremaUrgenza = (Long) SqlManager.getValueFromVectorParam(
                datiW3GARA, 11).getValue();
        if(estremaUrgenza == null) {
        	if(isStringaValorizzata(flagEstremaUrgenza) && flagEstremaUrgenza.equals("1")) {
        		this.addCampoObbligatorio(listaControlli, "W3GARA",
      	              "ESTREMA_URGENZA", pagina);
        	}
        }
        else {
        	if(!isStringaValorizzata(flagEstremaUrgenza) || flagEstremaUrgenza.equals("2")) {
        		listaControlli.add(((new Object[] { "E", pagina,
                        "Motivo urgenza", "campo non previsto. Il campo Estrema urgenza/Esecuzione di lavori di somma urgenza è stato impostato a NO" })));
        	}
        }
        
        //Motivazione richiesta CIG
        Long motivazione_richiesta_cig = (Long) SqlManager.getValueFromVectorParam(
                datiW3GARA, 7).getValue();
            /*if (motivazione_richiesta_cig == null) {
              this.addCampoObbligatorio(listaControlli, "W3GARA",
                  "M_RICH_CIG", pagina);
            }*/
        
        //Categorie merceologica
        List<?> datiW3GARAMERC = sqlManager.getListVector(selectW3GARAMERC,
                new Object[] { numgara });
        if (datiW3GARAMERC != null && datiW3GARAMERC.size() > 0) {
        	boolean checkMotivazioneCig = false;
            for (int i = 0; i < datiW3GARAMERC.size(); i++) {
            	Long categoria = (Long) SqlManager.getValueFromVectorParam(datiW3GARAMERC.get(i), 0).getValue();
            	if (!checkMotivazioneCig && !categoria.equals(new Long("999"))) {
            		checkMotivazioneCig = true;
                	if (motivazione_richiesta_cig == null) {
                        this.addCampoObbligatorio(listaControlli, "W3GARA", "M_RICH_CIG", pagina);
                    }
                }
            }
            //A partire dalla versione 3.04.4 verifico in fase di modifica successiva all’acquisizione del/dei CIG, se la lista di valori non comprende i valori indicati nei lotti della gara
            if (versioneSimog.equals(new Long(4))) {
            	if(statoSimog.equals(new Long(3))) {
            		List<?> datiCATEGORIE = sqlManager.getListVector(selectCategorieLottiNonPresenti, new Object[] { numgara, numgara });
            		if (datiCATEGORIE != null && datiCATEGORIE.size() > 0) {
            			for (int i = 0; i < datiCATEGORIE.size(); i++) {
            				Long categoria = (Long) SqlManager.getValueFromVectorParam(datiCATEGORIE.get(i), 0).getValue();
                			String cig = (String) SqlManager.getValueFromVectorParam(datiCATEGORIE.get(i), 1).getValue();
                			listaControlli.add(((new Object[] { "E", pagina,
                                    "Categoria merceologica", "La categoria merceologica " + categoria + " del lotto con cig " + cig + " non e' compresa tra quelle indicate nella scheda gara" })));
            			}
            		}
            	}
            }
        } else {
        	this.addCampoObbligatorio(listaControlli, "W3GARAMERC", "CATEGORIA", pagina);
        }

        /*Long categoria = (Long)sqlManager.getObject(selectW3GARAMERC,
                new Object[] { numgara });
        if (categoria == null) {
            
        } else if (!categoria.equals(new Long("999"))) {
        	if (motivazione_richiesta_cig == null) {
                this.addCampoObbligatorio(listaControlli, "W3GARA",
                    "M_RICH_CIG", pagina);
            }
        }*/
        
        if (versioneSimog.equals(new Long(4))) {
        	
        	String flagDelega = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 17).getValue();
        	Long funzioneDelega = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 18).getValue();
        	String cfDelegante = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 19).getValue();
        	if (!isStringaValorizzata(flagDelega)) {
                this.addCampoObbligatorio(listaControlli, "W3GARA", "FLAG_SA_AGENTE_GARA", pagina);
            }  else {
            	if (flagDelega.equals("1") && funzioneDelega == null) {
            		this.addCampoObbligatorio(listaControlli, "W3GARA", "ID_F_DELEGATE", pagina);
            	} else if (flagDelega.equals("2") && funzioneDelega != null) {
            		listaControlli.add(((new Object[] { "E", pagina,
                            "Funzioni delegate", "campo non previsto." })));
            	}
            }
        	
        	if (funzioneDelega != null && !isStringaValorizzata(cfDelegante)) {
        		listaControlli.add(((new Object[] { "E", pagina,
                        "Codice fiscale soggetto per conto del quale agisce la S.A.", "Attenzione, non è stato indicato il codice fiscale del soggetto" })));
        	}
        	
        	if (isStringaValorizzata(cfDelegante) && !UtilityFiscali.isValidCodiceFiscale(cfDelegante) && !UtilityFiscali.isValidPartitaIVA(cfDelegante)) {
        		listaControlli.add(((new Object[] { "E", pagina,
                        "Codice fiscale soggetto per conto del quale agisce la S.A.", "Inserire un CF valido" })));
        	}
        }
      }

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella lettura delle informazioni relative alla gara",
          "validazioneW3GARA", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("validazioneW3GARA: fine metodo");

  }

  /**
   * Validazione dei dati del lotto
   *
   * @param sqlManager
   * @param numgara
   * @param numlott
   * @param syscon
   * @param listaControlli
   * @throws GestoreException
   */
  private void validazioneW3LOTT(SqlManager sqlManager, Long numgara,
      Long numlott, Long syscon, List<Object> listaControlli) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("validazioneW3LOTT: inizio metodo");

    String pagina = "Sezione I: dati del lotto";
    boolean v3_04_6_Attiva = false;
    try {

      String selectW3LOTT = "select oggetto, " // 0
          + "somma_urgenza, " // 1
          + "tipo_contratto, " // 2
          + "flag_escluso, " // 3
          + "cpv, " // 4
          + "id_scelta_contraente, " // 5
          + "importo_lotto, " // 6
          + "id_categoria_prevalente, " // 7
          + "luogo_istat, " // 8
          + "luogo_nuts, " // 9
          + "id_esclusione, " // 10
          + "flag_cup, " // 11
          + "ANNUALE_CUI_MININF, " // 12
          + "ID_AFF_RISERVATI, " // 13
          + "FLAG_REGIME, " // 14
          + "ART_REGIME, " // 15
          + "FLAG_DL50, " // 16
          + "PRIMA_ANNUALITA, " // 17
          + "TRIENNIO_PROGRESSIVO, " // 18
          + "FLAG_PREVEDE_RIP, " // 19
          + "MOTIVO_COLLEGAMENTO, " // 20
          + "CIG_ORIGINE_RIP, " // 21
          + "CATEGORIA_MERC, " // 22
          + "IMPORTO_OPZIONI, " // 23
          + "DURATA_AFFIDAMENTO, " // 24
          + "DURATA_RINNOVI " // 25
          + "from w3lott where numgara = ? and numlott = ?";

      List<?> datiW3LOTT = sqlManager.getVector(selectW3LOTT, new Object[] {
          numgara, numlott });

      if (datiW3LOTT != null && datiW3LOTT.size() > 0) {
    	  String rup_codtec = (String)this.sqlManager.getObject("select RUP_CODTEC from W3GARA where NUMGARA = ?", new Object[] { numgara});;
    	  Long versioneSimog = (Long) this.sqlManager.getObject("select VER_SIMOG from W3GARA where NUMGARA = ?", new Object[] { numgara});
    	  v3_04_6_Attiva = UtilitySITAT.v3_04_6_Attiva(sqlManager, numgara);
          
    	  if (isStringaValorizzata(rup_codtec)) {
    		  //verifico che il rup sia effettivamente associato all'utente loggato nella tabella w3usrsys
              Long esisteAssociazione = (Long) sqlManager.getObject("select count(*) from w3usrsys where syscon = ? and rup_codtec = ?", new Object[] {syscon, rup_codtec});
              if (esisteAssociazione.equals(new Long(0))) {
            	  String descrizione = "Responsabile unico del procedimento";
                  String messaggio = "Il RUP della gara non è presente nella lista dei tuoi RUP associati. Verifica dalla Home Page al link \"Collega/Importa RUP e Centri di Costo da SIMOG\"";
                  listaControlli.add(((new Object[] { "E", pagina,
                      descrizione, messaggio })));
              }
    	  }
        // Oggetto del lotto
        String oggetto = (String) SqlManager.getValueFromVectorParam(
            datiW3LOTT, 0).getValue();
        if (!isStringaValorizzata(oggetto)) {
          this.addCampoObbligatorio(listaControlli, "W3LOTT", "OGGETTO", pagina);
        }

        // Somma urgenza ?
        /*String somma_urgenza = (String) SqlManager.getValueFromVectorParam(
            datiW3LOTT, 1).getValue();
        if (!isStringaValorizzata(somma_urgenza)) {
          this.addCampoObbligatorio(listaControlli, "W3LOTT", "SOMMA_URGENZA",
              pagina);
        }*/

        // Tipo contratto
        String tipo_contratto = (String) SqlManager.getValueFromVectorParam(
            datiW3LOTT, 2).getValue();
        if (!isStringaValorizzata(tipo_contratto)) {
          this.addCampoObbligatorio(listaControlli, "W3LOTT", "TIPO_CONTRATTO",
              pagina);
        }else{
          String selectW3LOTTTIPI = "";
          String tipologia = "";
          if("L".equals(tipo_contratto)){
            tipologia = "Lavori";
            selectW3LOTTTIPI = "select count(numtipi) from w3lotttipi" +
       		" where numgara = ? and numlott = ? and idappalto >= 6 ";
          }
          if("F".equals(tipo_contratto)){
            tipologia = "Forniture";
            selectW3LOTTTIPI = "select count(numtipi) from w3lotttipi" +
            " where numgara = ? and numlott = ? and idappalto < 6 ";
          }
          if("S".equals(tipo_contratto)){
            tipologia = "Servizi";
            selectW3LOTTTIPI = "select count(numtipi) from w3lotttipi" +
            " where numgara = ? and numlott = ? and idappalto < 6 ";
          }

          Long countTipi = (Long) this.sqlManager.getObject(selectW3LOTTTIPI,
              new Object[] { numgara, numlott });

          if (!(countTipi > 0) && !"S".equals(tipo_contratto)) {
            // Controllo presenza elenco tipologie lavoro/forniture - servizi
        	  if("L".equals(tipo_contratto)){
        		  this.addAvviso(listaControlli, "W3LOTTTIPI", "Dettaglio tipologia contratto", "E", pagina,
        	                "E' necessario indicare almeno un valore per la tipologia "+tipologia, "");
        	  }
        	  else if("F".equals(tipo_contratto) || "S".equals(tipo_contratto)){
        		  this.addAvviso(listaControlli, "W3LOTTTIPI", "Dettaglio tipologia contratto", "W", pagina,
      	                "E' necessario indicare almeno un valore per la tipologia "+tipologia, "");
        	  }
          }
        }

        // Contratto escluso ?
        String contratto_escluso = (String) SqlManager.getValueFromVectorParam(
            datiW3LOTT, 3).getValue();
        String flagRegimi = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 14).getValue();
        Long id_esclusione = (Long) SqlManager.getValueFromVectorParam(
                datiW3LOTT, 10).getValue();
        if (!isStringaValorizzata(contratto_escluso)) {
          this.addCampoObbligatorio(listaControlli, "W3LOTT", "FLAG_ESCLUSO",
              pagina);
        } else {
          if ("1".equals(contratto_escluso)) {
            if (id_esclusione == null) {
              this.addCampoObbligatorio(listaControlli, "W3LOTT", "ID_ESCLUSIONE",
                  pagina);
            }
            if ("1".equals(flagRegimi)) {
            	listaControlli.add(((new Object[] { "E", pagina,
        				this.getDescrizioneCampo("W3LOTT", "FLAG_ESCLUSO"), "I campi 'Contratto escluso o rientrante nel regime alleggerito' e 'Contratto regime particolare di appalto (speciale o alleggerito)' non possono essere valorizzati entrambi a 'SI'" })));
            }
          } else if ("2".equals(contratto_escluso)) {
        	  if (id_esclusione != null) {
        		  listaControlli.add(((new Object[] { "E", pagina,
          				this.getDescrizioneCampo("W3LOTT", "ID_ESCLUSIONE"), "Campo non previsto. Il campo Contrato escluso o rientrante nel regime alleggerito è stato impostato a NO" })));
        	  }
          }
          
        }
        // Contratti regimi particolari di appalto (speciale o alleggerito)
        if (!isStringaValorizzata(flagRegimi)) {
          this.addCampoObbligatorio(listaControlli, "W3LOTT", "FLAG_REGIME", pagina);
        } 
        //Regime particolare di appalto
        Long allegato_ix = (Long)sqlManager.getObject("SELECT ALLEGATO_IX FROM W3GARA WHERE NUMGARA = ?", new Object[] {numgara });
        String articoloRegimi = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 15).getValue();
        if (!isStringaValorizzata(articoloRegimi)) {
        	if ("1".equals(flagRegimi)) {
        		this.addCampoObbligatorio(listaControlli, "W3LOTT", "ART_REGIME",pagina);
        	}
        } else {
        	if ("2".equals(flagRegimi)) {
        		listaControlli.add(((new Object[] { "E", pagina,
        				this.getDescrizioneCampo("W3LOTT", "ART_REGIME"), "Campo non previsto. Il campo Contratto regime particolare di appalto (speciale o alleggerito) è stato impostato a NO" })));
        	}
        	//Deve essere valorizzato a 37, 39 o 40 se il campo S01.12.1 è valorizzato
        	if (allegato_ix != null && !"37".equals(articoloRegimi) && !"39".equals(articoloRegimi) && !"40".equals(articoloRegimi)) {
        		listaControlli.add(((new Object[] { "E", pagina,
        				this.getDescrizioneCampo("W3LOTT", "ART_REGIME"), "Selezionare una delle voci relative all'allegato IX" })));
        	}
        }
        // CPV
        String cpv = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 4).getValue();
        if (!isStringaValorizzata(cpv)) {
          this.addCampoObbligatorio(listaControlli, "W3LOTT", "CPV", pagina);
        } else {
        	//verifico se il cpv e superiore al 3° livello
        	int index = cpv.indexOf("-");
        	if (index != -1) {
        		if (cpv.substring(0, index).endsWith("00000")) {
        			//verifico se il codice CPV è una foglia
        			Long numeroFoglie = (Long) sqlManager.getObject("select count(*) from tabcpv where CPVCOD0='" + cpv.substring(0,2) + "' and CPVCOD1 like '" + cpv.substring(2,3) + "%'", new Object[] { });
        			if (numeroFoglie != null && numeroFoglie > 1) {
        				listaControlli.add(((new Object[] { "E", pagina,
            	    			this.getDescrizioneCampo("W3LOTT", "CPV"), "Indicare un valore di livello tre o successivo, presente nel catalogo cpv" })));
        			}
        		}
        	}
        }
        
        // CPV Secondarie
        String selectW3CPV = "select cpv from w3cpv where numgara = ? and numlott = ? order by num_cpv ";
        List<?> datiW3CPV = this.sqlManager.getListVector(selectW3CPV, new Object[] { numgara, numlott });
        if (datiW3CPV != null && datiW3CPV.size() > 0) {
        	for (int j = 0; j < datiW3CPV.size(); j++) {
        		JdbcParametro jdbcParam = SqlManager.getValueFromVectorParam(datiW3CPV.get(j), 0);
        		if (jdbcParam.getValue() != null) {
	        		String cpvSecondaria = (String) SqlManager.getValueFromVectorParam(datiW3CPV.get(j), 0).getValue();
	        		int index = cpvSecondaria.indexOf("-");
	       	  	if (index != -1) {
	       	  		if (cpvSecondaria.substring(0, index).endsWith("00000")) {
	       	  			//verifico se il codice CPV è una foglia
	       	  			Long numeroFoglie = (Long) sqlManager.getObject("select count(*) from tabcpv where CPVCOD0='" + cpvSecondaria.substring(0,2) + "' and CPVCOD1 like '" + cpvSecondaria.substring(2,3) + "%'", new Object[] { });
	       	  			if (numeroFoglie != null && numeroFoglie > 1) {
	       	  				listaControlli.add(((new Object[] { "E", pagina,
	       	    			this.getDescrizioneCampo("W3CPV", "CPV"), cpvSecondaria + " Indicare un valore di livello tre o successivo, presente nel catalogo cpv" })));
	         				}
	         			}
	       	  	}
        		} else {
        			listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo("W3LOTT", "CPV"), "Valorizzare il CPV secondario numero " + (j+1) })));
        		}
        	}
        } else {
        	listaControlli.add(((new Object[] { "W", pagina, "CPV Secondarie", "Attenzione, non sarà possibile comunicare subappalti per servizi e forniture non ricomprese nelle CPV principale e secondarie indicate" })));
        }
        // Categoria merceologica oggetto della fornitura di cui al DPCM soggetti aggregatori
        if (versioneSimog.equals(new Long(4))) {
        	Long categoriaMerceologica = (Long)SqlManager.getValueFromVectorParam(datiW3LOTT, 22).getValue();
            if (categoriaMerceologica == null) {
                this.addCampoObbligatorio(listaControlli, "W3LOTT", "CATEGORIA_MERC", pagina);
            } else {
              	//Se il valore inserito non fa parte delle categorie indicate nella gara
              	String selectCategoriaNonPresente = "select count(*) from w3lott where numgara = ? and numlott = ? and categoria_merc not in (Select CATEGORIA from W3GARAMERC where numgara = ?)";
                Long count = (Long) this.sqlManager.getObject(selectCategoriaNonPresente, new Object[] {numgara, numlott, numgara });
                if (count > 0) {
                    this.addAvviso(listaControlli, "W3LOTT", "Categoria merceologica oggetto della fornitura di cui al DPCM soggetti aggregatori", "E", pagina, "Il valore indicato non fa parte delle categorie merceologiche definite in gara", "");
                }
            } 
        }
        // Procedura di scelta del contraente
        Long sceltaContraente = (Long)SqlManager.getValueFromVectorParam(datiW3LOTT, 5).getValue();
        String selectW3COND = "select count(*) from w3cond where numgara = ? and numlott = ? ";
        Long countCond = (Long) this.sqlManager.getObject(selectW3COND, new Object[] {numgara, numlott });
        Long modo_indizione = (Long) this.sqlManager.getObject("Select modo_indizione from w3gara where numgara = ? ", new Object[] { numgara});
        if (sceltaContraente == null) {
          this.addCampoObbligatorio(listaControlli, "W3LOTT",
              "ID_SCELTA_CONTRAENTE", pagina);
        } else if (sceltaContraente.equals(new Long(4)) || (sceltaContraente.equals(new Long(10)) && !new Long(2).equals(modo_indizione)) ){
        	//se procedura negoziata
            if (!(countCond > 0)) {
              // Controllo presenza almeno un valore
              this.addAvviso(listaControlli, "W3COND", "Condizioni che giustificano il ricorso alla procedura negoziata", "E", pagina, "E' necessario indicare almeno un valore", "");
            }
        } else {
        	if (countCond > 0 && !sceltaContraente.equals(new Long(10))) {
                // Campo non previsto
                this.addAvviso(listaControlli, "W3COND", "Condizioni che giustificano il ricorso alla procedura negoziata", "E", pagina, "Campo non previsto", "");
            }
        }
        //Tipo appalto riservato
        Long affidamentoRiservato = (Long)SqlManager.getValueFromVectorParam(datiW3LOTT, 13).getValue();
        if (sceltaContraente != null) {
        	if (sceltaContraente.equals(new Long(32))) {
        		if (affidamentoRiservato == null) {
        			this.addCampoObbligatorio(listaControlli, "W3LOTT","ID_AFF_RISERVATI", pagina);
        		}
        	} else {
        		if (affidamentoRiservato != null) {
        			listaControlli.add(((new Object[] { "E", pagina,
            				this.getDescrizioneCampo("W3LOTT", "ID_AFF_RISERVATI"), "Tipo appalto riservato non previsto per la procedura di scelta del contraente selezionata" })));
        		}
        	}
        }
        // Importo del lotto
        Double importo = (Double)SqlManager.getValueFromVectorParam(datiW3LOTT, 6).getValue();
        if (importo == null) {
        	this.addCampoObbligatorio(listaControlli, "W3LOTT", "IMPORTO_LOTTO", pagina);
        } else if (importo <= 0) {
        	this.addAvviso(listaControlli, "W3LOTT", "Importo lotto", "E", pagina,
                    "L'importo deve essere maggiore di 0", "");
        } else if (importo > 300000) {
        	Long estremaUrgenza = (Long)sqlManager.getObject("SELECT ESTREMA_URGENZA FROM W3GARA WHERE NUMGARA = ?", new Object[] {numgara });
        	if (estremaUrgenza != null && estremaUrgenza.equals(new Long(1))) {
        		this.addAvviso(listaControlli, "W3LOTT", "Importo lotto", "W", pagina,
                        "l'importo del lotto è superiore a quello normativamente utilizzabile per la procedura selezionata", "");
        	}
        }
        
        // Categoria prevalente
        String id_categoria_prevalente = (String) SqlManager.getValueFromVectorParam(
            datiW3LOTT, 7).getValue();
        if (!isStringaValorizzata(id_categoria_prevalente)) {
          this.addCampoObbligatorio(listaControlli, "W3LOTT",
              "ID_CATEGORIA_PREVALENTE", pagina);
        } else {
        	List<String> categorie = new ArrayList<String>();
        	categorie.add(id_categoria_prevalente);
        	List<?> datiW3LOTTCATE = this.sqlManager.getListVector("select categoria from w3lottcate where numgara = ? and numlott = ?",
                    new Object[] { numgara, numlott });
        	if (datiW3LOTTCATE != null && datiW3LOTTCATE.size() > 0) {
        		for (int i = 0; i < datiW3LOTTCATE.size(); i++) {
        			String categoria = (String) SqlManager.getValueFromVectorParam(datiW3LOTTCATE.get(i), 0).getValue();
        			if (categorie.contains(categoria)) {
        				listaControlli.add(((new Object[] { "E", pagina,
        			              "Altre categorie", "Ci sono delle categorie doppie" })));
        				break;
        			} else {
        				categorie.add(categoria);
        			}
        		}
        	}
        }

        // Luogo di esecuzione
        String luogo_istat = (String) SqlManager.getValueFromVectorParam(
            datiW3LOTT, 8).getValue();
        String luogo_nuts = (String) SqlManager.getValueFromVectorParam(
            datiW3LOTT, 9).getValue();

        if ((!isStringaValorizzata(luogo_istat))
            && (!isStringaValorizzata(luogo_nuts))) {
          String descrizione = this.getDescrizioneCampo("W3LOTT", "LUOGO_ISTAT")
              + ", "
              + this.getDescrizioneCampo("W3LOTT", "LUOGO_NUTS");
          listaControlli.add(((new Object[] { "E", pagina,
              descrizione, "Valorizzare uno dei due campi" })));
        } else if (isStringaValorizzata(luogo_istat) && isStringaValorizzata(luogo_nuts)) {
        	String descrizione = this.getDescrizioneCampo("W3LOTT", "LUOGO_ISTAT")
            + ", "
            + this.getDescrizioneCampo("W3LOTT", "LUOGO_NUTS");
        listaControlli.add(((new Object[] { "E", pagina,
            descrizione, "Sono stati indicati sia codice ISTAT che codice NUTS" })));
        }

        //Progressivo nell’ambito del triennio
        /*Long triennioProgressivo = (Long)SqlManager.getValueFromVectorParam(datiW3LOTT, 18).getValue();
        if (triennioProgressivo == null && "L".equals(tipo_contratto)) {
        	listaControlli.add(((new Object[] { "W", pagina,
  	    			this.getDescrizioneCampo("W3LOTT", "TRIENNIO_PROGRESSIVO"), "Estremi del programma triennale incompleti" })));
        }*/
        
        // Il lavoro o l’acquisto di bene o servizio è stato previsto all’interno della programmazione
        String flagProgrammazione = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 16).getValue();
        String cui = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 12).getValue();
  	    if (!isStringaValorizzata(flagProgrammazione)) {
          this.addCampoObbligatorio(listaControlli, "W3LOTT", "FLAG_DL50", pagina);
        } else {
          if ("1".equals(flagProgrammazione)) {
        	  Long primaAnnualita = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 17).getValue();
        	  if (primaAnnualita == null) {
        		  this.addCampoObbligatorio(listaControlli, "W3LOTT", "PRIMA_ANNUALITA",pagina);
        	  } else if (primaAnnualita < 1000 || primaAnnualita > 9999) {
        		  listaControlli.add(((new Object[] { "E", pagina,
        	    			this.getDescrizioneCampo("W3LOTT", "PRIMA_ANNUALITA"), "Inserire un valore valido (AAAA)" })));
        	  }
        	  if (!isStringaValorizzata(cui)) {
        		  this.addCampoObbligatorio(listaControlli, "W3LOTT", "ANNUALE_CUI_MININF",pagina);
        	  } 
          }
        }
  	    //se presente verifico lunghezza codice cui
  	    if (isStringaValorizzata(cui) && (cui.length()<20 || cui.length()>22)) {
  	    	listaControlli.add(((new Object[] { "E", pagina,
  	    			this.getDescrizioneCampo("W3LOTT", "ANNUALE_CUI_MININF"), "CUI assegnato dal sistema del Min. Infrastrutture non valido" })));
  	    }
        
  	    // L’appalto prevede ripetizioni o altre opzioni?
  	    // Ripetizione di precedente contratto
        String prevedeRipetizioni = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 19).getValue();
        Long motivoCollegamento = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 20).getValue();

        if (!isStringaValorizzata(prevedeRipetizioni)){
        	this.addCampoObbligatorio(listaControlli, "W3LOTT", "FLAG_PREVEDE_RIP",pagina);
        } else {
        	Double importoOpzioni = (Double)SqlManager.getValueFromVectorParam(datiW3LOTT, 23).getValue();
        	if (prevedeRipetizioni.equals("1")) {
        		if (importoOpzioni == null || importoOpzioni.equals(0)) {
                	listaControlli.add(((new Object[] { "E", pagina,
          	    			this.getDescrizioneCampo("W3LOTT", "IMPORTO_OPZIONI"), "Non è stato valorizzato il campo 'di cui per opzioni/ripetizioni'" })));
                }
        	} else {
        		if (importoOpzioni != null && !importoOpzioni.equals(0)) {
        			listaControlli.add(((new Object[] { "E", pagina,
          	    			this.getDescrizioneCampo("W3LOTT", "IMPORTO_OPZIONI"), "E' stato indicato un importo per le ripetizioni ma il campo 'L'appalto prevede ripetizioni o altre opzioni?' e' stato valorizzato a NO" })));
        		}
        	}
        }
        
        if (motivoCollegamento == null) {
        	this.addCampoObbligatorio(listaControlli, "W3LOTT", "MOTIVO_COLLEGAMENTO",pagina);
        } else {
        	if (isStringaValorizzata(prevedeRipetizioni) && prevedeRipetizioni.equals("1")){
        		if(!motivoCollegamento.equals(new Long(10))) {
        			listaControlli.add(((new Object[] { "E", pagina,
        		              "L'appalto prevede una delle seguenti ipotesi di collegamento?", "Se l'appalto prevede ripetizioni, il motivo collegamento CIG deve essere 'No, nessuna ipotesi di collegamento'" })));
        		}
        	}  
        	if(new Long(31).equals(sceltaContraente) && !motivoCollegamento.equals(new Long(8))) {
        		listaControlli.add(((new Object[] { "E", pagina,
  		              "L'appalto prevede una delle seguenti ipotesi di collegamento?", "In caso di affidamento diretto per variante oltre il 20%, la motivazione deve essere 'Nuovo contratto originato da variante oltre il 20%'" })));
        	} else {
        		selectW3COND = "select ID_CONDIZIONE from w3cond where numgara = ? and numlott = ? and num_cond = 1";
                Long idCondizione = (Long) this.sqlManager.getObject(selectW3COND, new Object[] {numgara, numlott });
                if(idCondizione != null) {
                	if (new Long(39).equals(idCondizione) && !new Long(2).equals(motivoCollegamento)) {
                		listaControlli.add(((new Object[] { "E", pagina,
          		              "L'appalto prevede una delle seguenti ipotesi di collegamento?", "In caso di consegne complementari, la motivazione deve essere 'Consegne complementari'" })));
                	}
                	if (new Long(43).equals(idCondizione) && !new Long(1).equals(motivoCollegamento)) {
                		listaControlli.add(((new Object[] { "E", pagina,
          		              "L'appalto prevede una delle seguenti ipotesi di collegamento?", "In caso di ripetizione lavori o servizi analoghi, la motivazione deve essere 'Ripetizione di lavori o servizi analoghi'" })));
                	}
                	if (new Long(42).equals(idCondizione) && !new Long(9).equals(motivoCollegamento)) {
                		listaControlli.add(((new Object[] { "E", pagina,
          		              "L'appalto prevede una delle seguenti ipotesi di collegamento?", "In caso di II fase Concorso di progettazione e idee, la motivazione deve essere 'II fase Concorso di progettazione e idee'" })));
                	}
                }
        	}
        }
        
        //Durata affidamento
        //Durata dei rinnovi e delle ripetizioni in giorni
        if (v3_04_6_Attiva) {
        	Long durataAffidamento = (Long)SqlManager.getValueFromVectorParam(datiW3LOTT, 24).getValue();
            if (durataAffidamento == null) {
            	this.addCampoObbligatorio(listaControlli, "W3LOTT", "DURATA_AFFIDAMENTO", pagina);
            }
            Long durataRinnovi = (Long)SqlManager.getValueFromVectorParam(datiW3LOTT, 25).getValue();
            if (durataRinnovi == null && isStringaValorizzata(prevedeRipetizioni) && prevedeRipetizioni.equals("1")) {
            	this.addCampoObbligatorio(listaControlli, "W3LOTT", "DURATA_RINNOVI", pagina);
            }
        }
        
        //CIG collegato
        Long modoRealizzazione = (Long)sqlManager.getObject("SELECT MODO_REALIZZAZIONE FROM W3GARA WHERE NUMGARA = ?", new Object[] {numgara });
        String cigOriginario = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 21).getValue();
        if (!isStringaValorizzata(cigOriginario)) {
        	if (!new Long(10).equals(motivoCollegamento)) {
        		listaControlli.add(((new Object[] { "E", pagina,
      	    			this.getDescrizioneCampo("W3LOTT", "CIG_ORIGINE_RIP"), "Obbligatorio se 'L'appalto prevede una delle seguenti ipotesi di collegamento' e' valorizzato" })));
        	} 
        } else {
        	Long modoRealizzazionePadre = (Long)sqlManager.getObject("SELECT W3GARA.MODO_REALIZZAZIONE FROM W3GARA left join W3LOTT on W3GARA.NUMGARA=W3LOTT.NUMGARA WHERE W3LOTT.CIG = ?", new Object[] {cigOriginario });
            
        	if (new Long(10).equals(motivoCollegamento)) {
        		listaControlli.add(((new Object[] { "E", pagina,
      	    			this.getDescrizioneCampo("W3LOTT", "CIG_ORIGINE_RIP"), "Il campo CIG collegato non deve essere valorizzato" })));
        	} else if (!UtilitySITAT.isCigValido(cigOriginario)) {
        		listaControlli.add(((new Object[] { "E", pagina,
      	    			this.getDescrizioneCampo("W3LOTT", "CIG_ORIGINE_RIP"), "CIG non valido" })));
        	} else if (new Long(9).equals(motivoCollegamento) && (new Long(2).equals(modoRealizzazione) || new Long(11).equals(modoRealizzazione))) {
        		//verifico se ho in casa il cig padre
        		if (modoRealizzazionePadre != null) {
        			if (!modoRealizzazionePadre.equals(new Long(10))) {
        				listaControlli.add(((new Object[] { "E", pagina,
              	    			this.getDescrizioneCampo("W3LOTT", "CIG_ORIGINE_RIP"), "Il CIG fa parte di una gara che non presenta come modalita' di realizzazione 'Concorsi di progettazione/Concorsi di idee'" })));
        			}
        		} else {
        			listaControlli.add(((new Object[] { "W", pagina,
          	    			this.getDescrizioneCampo("W3LOTT", "CIG_ORIGINE_RIP"), "Il CIG potrebbe far parte di una gara che non presenta come modalita' di realizzazione 'Concorsi di progettazione/Concorsi di idee'" })));
        		}
        	} else if (new Long(11).equals(motivoCollegamento)) {
        		//verifico se ho in casa il cig padre
        		if (modoRealizzazionePadre != null) {
        			if (!modoRealizzazionePadre.equals(new Long(19))) {
        				listaControlli.add(((new Object[] { "E", pagina,
              	    			this.getDescrizioneCampo("W3LOTT", "CIG_ORIGINE_RIP"), "Il CIG fa parte di una gara che non presenta come modalita' di realizzazione 'appalto pre-commerciale'" })));
        			}
        		} else {
        			listaControlli.add(((new Object[] { "W", pagina,
          	    			this.getDescrizioneCampo("W3LOTT", "CIG_ORIGINE_RIP"), "Il CIG potrebbe far parte di una gara che non presenta come modalita' di realizzazione 'appalto pre-commerciale'" })));
        		}
        	}
        }
        // Flag CUP
        String flag_cup = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 11).getValue();
        //Long estremaUrgenza = (Long)sqlManager.getObject("SELECT ESTREMA_URGENZA FROM W3GARA WHERE NUMGARA = ?", new Object[] {numgara });
        Long manutenzioneOrdinaria = (Long) this.sqlManager.getObject("SELECT COUNT(*) FROM W3LOTTTIPI WHERE IDAPPALTO = 12 AND NUMGARA=? AND NUMLOTT=?",
                new Object[] { numgara, numlott });
        if (!isStringaValorizzata(flag_cup)) {
        	this.addCampoObbligatorio(listaControlli, "W3LOTT", "FLAG_CUP", pagina);
        }
        else if ("2".equals(flag_cup)) {
        	// se modalità realizzazione non è un accordo quando oppure
        	if ((modoRealizzazione.equals(new Long(9)) || modoRealizzazione.equals(new Long(17))) ||
        			("F".equals(tipo_contratto) || "S".equals(tipo_contratto)) || 
        			("L".equals(tipo_contratto) && manutenzioneOrdinaria>0)) {
        		;//non obbligatorio
        	}
        	else {
        		listaControlli.add(((new Object[] { "E", pagina,
      	    			this.getDescrizioneCampo("W3LOTT", "FLAG_CUP"), "L'indicazione del CUP è obbligatoria" })));
        	}
        }
        else if("1".equals(flag_cup)){
            String selectW3LOTTCUP = "select count(cup) from w3lottcup" + " where numgara = ? and numlott = ? ";
            Long countCup = (Long) this.sqlManager.getObject(selectW3LOTTCUP, new Object[] {numgara, numlott });
            if (!(countCup > 0)) {
              // Controllo presenza almeno cup
              this.addAvviso(listaControlli, "W3LOTTCUP", "Indicazione cup obbligatoria", "E", pagina, "E' necessario indicare almeno un cup", "");
            }
        }
        
      }

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella lettura delle informazioni relative al lotto",
          "validazioneW3LOTT", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("validazioneW3LOTT: fine metodo");

  }

  /**
   * Validazione dei dati della richiesta SMARTCIG
   *
   * @param sqlManager
   * @param id
   * @param listaControlli
   * @throws GestoreException
   */
  private void validazioneW3SMARTCIG(SqlManager sqlManager, Long numgara,
      Long syscon, List<Object> listaControlli) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("validazioneW3SMARTCIG: inizio metodo");

    String pagina = "";

    try { 

      String selectW3SMARTCIG = "select OGGETTO, " // 0
          + "M_RICH_CIG, " // 1
          + "IMPORTO, " // 2
          + "FATTISPECIE, " // 3
          + "CUP, " // 4
          + "RUP, " // 5
          + "COLLABORAZIONE, " // 6	
          + "TIPO_CONTRATTO, " // 7	
          + "CIG_ACC_QUADRO, " // 8
          + "ID_SCELTA_CONTRAENTE "	//9
          + "from W3SMARTCIG where CODRICH = ?";

      String selectW3SMARTCIGMERC = "select categoria " 
          + "from W3SMARTCIGMERC where CODRICH = ? and NUMMERC=1";
      
      List<?> datiW3SMARTCIG = sqlManager.getVector(selectW3SMARTCIG,
          new Object[] { numgara });

      if (datiW3SMARTCIG != null && datiW3SMARTCIG.size() > 0) {

        pagina = "Sezione I: RUP e COLLABORAZIONE";

        // Codice fiscale del responsabile unico del procedimento
        String rup_codtec = (String) SqlManager.getValueFromVectorParam(
        		datiW3SMARTCIG, 5).getValue();
        if (!isStringaValorizzata(rup_codtec)) {
          String descrizione = "Responsabile unico del procedimento";
          String messaggio = "E' obbligatorio indicare il RUP.";
          listaControlli.add(((new Object[] { "E", pagina,
              descrizione, messaggio })));
        } else {
          String cftec = (String) sqlManager.getObject(
              "select cftec from tecni where codtec = ?",
              new Object[] { rup_codtec });
          if (!isStringaValorizzata(cftec))
            this.addCampoObbligatorio(listaControlli, "TECNI", "CFTEC", pagina);
          
          //verifico che il rup sia effettivamente associato all'utente loggato nella tabella w3usrsys
          Long esisteAssociazione = (Long) sqlManager.getObject("select count(*) from w3usrsys where syscon = ? and rup_codtec = ?", new Object[] {syscon, rup_codtec});
          if (esisteAssociazione.equals(new Long(0))) {
        	  String descrizione = "Responsabile unico del procedimento";
              String messaggio = "Il RUP della gara non è presente nella lista dei tuoi RUP associati. Verifica dalla Home Page al link \"Collega/Importa RUP e Centri di Costo da SIMOG\"";
              listaControlli.add(((new Object[] { "E", pagina,
                  descrizione, messaggio })));
          }
        }

        /*String w3usrsys_rup_codtec = (String) sqlManager.getObject(
            "select rup_codtec from w3usrsys where syscon = ?",
            new Object[] { syscon });
        if (!isStringaValorizzata(w3usrsys_rup_codtec)) {
          String descrizione = "Responsabile unico del procedimento";
          String messaggio = "Non e' stato definito il RUP associato all'utente applicativo. "
              + "Per aggiornare questa informazione e la lista delle collaborazioni "
              + "utilizzare la scheda \"Informazioni RUP\" accessibile dalla pagina iniziale.";
          listaControlli.add(((new Object[] { "E", pagina,
              descrizione, messaggio })));
        }*/

        // Controllo che il RUP indicato nella gara sia lo stesso RUP associato
        // all'utente corrente
        /*if (isStringaValorizzata(rup_codtec)
            && isStringaValorizzata(w3usrsys_rup_codtec)) {
          if (!rup_codtec.equals(w3usrsys_rup_codtec)) {
            String descrizione = "Responsabile unico del procedimento";
            String messaggio = "Il RUP indicato tra i dati della gara ed "
                + "il RUP associato all'utente corrente non sono la stessa persona. "
                + "Per l'invio dei dati è necessario che le due figure coincidano.";
            listaControlli.add(((new Object[] { "E", pagina,
                descrizione, messaggio })));
          }
        }*/

        if (SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 6).getValue() == null) {
          String descrizione = "Collaborazione associata al RUP";
          String messaggio = "E' obbligatorio indicare la collaborazione.";
          listaControlli.add(((new Object[] { "E", pagina,
              descrizione, messaggio })));
        }

        pagina = "Sezione II: Richiesta CIG";

        // Oggetto della gara
        String oggetto = (String) SqlManager.getValueFromVectorParam(
        		datiW3SMARTCIG, 0).getValue();
        if (!isStringaValorizzata(oggetto)) {
        	this.addCampoObbligatorio(listaControlli, "W3SMARTCIG", "OGGETTO", pagina);
        }
        // Codice Fattispecie contrattuale
        String fattispecieContrattuale = (String) SqlManager.getValueFromVectorParam(
        		datiW3SMARTCIG, 3).getValue();
        if (!isStringaValorizzata(fattispecieContrattuale)) {
        	this.addCampoObbligatorio(listaControlli, "W3SMARTCIG", "FATTISPECIE", pagina);
        }
        // Importo della gara
        Double importo = (Double) SqlManager.getValueFromVectorParam(
        		datiW3SMARTCIG, 2).getValue();
        if (importo == null || importo<0) {
        	this.addAvviso(listaControlli, "W3SMARTCIG", "Importo", "E", pagina, "L’importo indicato non è valido, deve essere maggiore o uguale a zero", "");
        } else {
        	//fattispecie Articolo Escluso
            if (isStringaValorizzata(fattispecieContrattuale)) {
            	switch (Integer.parseInt(fattispecieContrattuale)) {
    			case 19: case 20: case 21: case 22: case 23:
    			case 24: case 25: case 26: case 27: case 28:
    			case 30: case 31: case 32: case 33: case 34: 
    		    case 35: case 36: case 39: case 40: case 41:
    		    case 42: case 43: case 44: case 45:
    				if (importo >= 40000) {
    					this.addAvviso(listaControlli, "W3SMARTCIG", "Importo", "E", pagina, "L'importo non è coerente con la fattispecie indicata, ricontrollare il valore immesso", "");
    				}
    				break;
    			case 18: 
    				if (importo >= 150000) {
    					this.addAvviso(listaControlli, "W3SMARTCIG", "Importo", "E", pagina, "L'importo non è coerente con la fattispecie indicata, ricontrollare il valore immesso", "");
    				}
    				break;
    			default:
    				break;
    			}
            }
        }
        
        //Tipo Contratto
        String tipoContratto = (String) SqlManager.getValueFromVectorParam(
        		datiW3SMARTCIG, 7).getValue();
        if (!isStringaValorizzata(tipoContratto)) {
        	this.addCampoObbligatorio(listaControlli, "W3SMARTCIG", "TIPO_CONTRATTO", pagina);
        } 
        
        //procedura di scelta contraente
        String sceltaContraente = (String) SqlManager.getValueFromVectorParam(
        		datiW3SMARTCIG, 9).getValue();
        if (!isStringaValorizzata(sceltaContraente)) {
        	this.addCampoObbligatorio(listaControlli, "W3SMARTCIG", "ID_SCELTA_CONTRAENTE", pagina);
        }
        
        //CIG_ACC_QUADRO
        String cigAccQuadro = (String) SqlManager.getValueFromVectorParam(
        		datiW3SMARTCIG, 8).getValue();
        if (isStringaValorizzata(cigAccQuadro) && cigAccQuadro.length()!= 10) {
        	this.addAvviso(listaControlli, "W3SMARTCIG", "CIG dell'accordo quadro", "E", pagina, "E’ stato indicato un CIG accordo quadro non valido", "");
        }
        
        //CUP
        String cup = (String) SqlManager.getValueFromVectorParam(
        		datiW3SMARTCIG, 4).getValue();
        if (isStringaValorizzata(cup) && cup.length()!= 15) {
        	this.addAvviso(listaControlli, "W3SMARTCIG", "CUP", "E", pagina, "Il codice cup deve essere composto esattamente da 15 caratteri alfanumerici", "");
        }
        
        //Motivazione richiesta CIG
        String motivazione_richiesta_cig = (String) SqlManager.getValueFromVectorParam(
        		datiW3SMARTCIG, 1).getValue();
            /*if (!isStringaValorizzata(motivazione_richiesta_cig)) {
              this.addCampoObbligatorio(listaControlli, "W3SMARTCIG",
                  "M_RICH_CIG", pagina);
            }*/
        
        //Categoria merceologica
        Long categoria = (Long)sqlManager.getObject(selectW3SMARTCIGMERC,
                new Object[] { numgara });
        if (categoria == null) {
            this.addCampoObbligatorio(listaControlli, "W3SMARTCIGMERC",
                "CATEGORIA", pagina);
        } else if (!categoria.equals(new Long(999))) {
        	if (!isStringaValorizzata(motivazione_richiesta_cig)) {
                this.addCampoObbligatorio(listaControlli, "W3SMARTCIG",
                    "M_RICH_CIG", pagina);
            }
        }
      }

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella lettura delle informazioni relative alla gara",
          "validazioneW3SMARTCIG", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("validazioneW3SMARTCIG: fine metodo");

  }
  
  /**
   * Validazione dei dati dei requisiti
   *
   * @param sqlManager
   * @param numgara
   * @param numreq
   * @param listaControlli
   * @throws GestoreException
   */
  private void validazioneW3GARAREQ(SqlManager sqlManager, Long numgara,
      Long numreq, Long syscon, List<Object> listaControlli) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("validazioneW3GARAREQ: inizio metodo");

    if (new Long(0).equals(numreq)) {
      String selectW3GARAREQ = "select numreq from w3garareq where numgara = ? ";

      List<?> datiW3GARAREQ;
      try {
        datiW3GARAREQ = sqlManager.getListVector(selectW3GARAREQ, new Object[] {numgara });
        if (datiW3GARAREQ != null && datiW3GARAREQ.size() > 0) {
        	String rup_codtec = (String)this.sqlManager.getObject("select RUP_CODTEC from W3GARA where NUMGARA = ?", new Object[] { numgara});;
        	if (isStringaValorizzata(rup_codtec)) {
      		  //verifico che il rup sia effettivamente associato all'utente loggato nella tabella w3usrsys
                Long esisteAssociazione = (Long) sqlManager.getObject("select count(*) from w3usrsys where syscon = ? and rup_codtec = ?", new Object[] {syscon, rup_codtec});
                if (esisteAssociazione.equals(new Long(0))) {
              	  String descrizione = "Responsabile unico del procedimento";
                    String messaggio = "Il RUP della gara non è presente nella lista dei tuoi RUP associati. Verifica dalla Home Page al link \"Collega/Importa RUP e Centri di Costo da SIMOG\"";
                    listaControlli.add(((new Object[] { "E", "RUP e COLLABORAZIONE",
                        descrizione, messaggio })));
                }
      	  	}
        	//Controllo se la gara prevede i requisiti
        	Object importoObj = sqlManager.getObject("select SUM(" + sqlManager.getDBFunction("isnull", new String[] {"IMPORTO_LOTTO", "0.00" }) + ") from W3LOTT where NUMGARA=? and STATO_SIMOG in (1,2,3,4,7,99)", new Object[] { numgara });
   	        Double importo = new Double(0);
   	        if (importoObj != null) {
   	        	if (!(importoObj instanceof Double)) {
   	        		importo = new Double(importoObj.toString());
    	        } else {
    	            importo = (Double) importoObj;
    	        }
    	    }
   	        if (importo < 40000) {
   	        	listaControlli.add(((new Object[] { "E", "Gestione dei requisiti",
                        "Requisiti definiti", "Per la gara non e' prevista la trasmissione dei requisiti" })));
   	        }
          for (int i = 0; i < datiW3GARAREQ.size(); i++) {
            List<Object> listaControlliReq = new Vector<Object>();
            String titolo = "Requisito n. " + (i + 1);
            numreq = (Long) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 0).getValue();
            this.validazioneSingleW3GARAREQ(sqlManager, numgara, numreq, listaControlliReq);
            if(listaControlliReq != null && listaControlliReq.size() >0 ){
              this.setTitolo(listaControlli, titolo);
              listaControlli.addAll(listaControlliReq);
            }
          }
        }

      } catch (SQLException e) {
        throw new GestoreException("Errore nella lettura delle informazioni relative ai requisiti", "validazioneW3GARAREQ", e);
      }

    } else {
      this.validazioneSingleW3GARAREQ(sqlManager, numgara, numreq, listaControlli);
    }

    if (logger.isDebugEnabled()) logger.debug("validazioneW3GARAREQ: fine metodo");

  }


  /**
   * Validazione dei dati dei requisiti
   *
   * @param sqlManager
   * @param numgara
   * @param numreq
   * @param listaControlli
   * @throws GestoreException
   */
  private void validazioneSingleW3GARAREQ(SqlManager sqlManager, Long numgara,
      Long numreq, List<Object> listaControlli) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("validazioneW3GARAREQ: inizio metodo");

    String pagina = "Sezione I: dati del requisito";

    try {

      String selectW3GARAREQ = "select codice_dettaglio, " // 0
          + "descrizione, " // 1
          + "valore, " // 2
          + "flag_esclusione, " // 3
          + "flag_comprova_offerta, " // 4
          + "flag_avvalimento, " // 5
          + "flag_bando_tipo, " // 6
          + "flag_riservatezza " // 7
          + "from w3garareq where numgara = ? and numreq = ?";

      // poi bisogna mettere anche i controlli per i CIG e DOC

      List<?> datiW3GARAREQ = sqlManager.getVector(selectW3GARAREQ, new Object[] {numgara, numreq });
      if (datiW3GARAREQ != null && datiW3GARAREQ.size() > 0) {
        // Codice dettaglio
        String codice_dettaglio = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ, 0).getValue();
        if (!isStringaValorizzata(codice_dettaglio)) {
          this.addCampoObbligatorio(listaControlli, "W3GARAREQ", "CODICE_DETTAGLIO", pagina);
        }

        // Descrizione
        String descrizione = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ, 1).getValue();
        if (!isStringaValorizzata(descrizione)) {
          this.addCampoObbligatorio(listaControlli, "W3GARAREQ", "DESCRIZIONE", pagina);
        }
        // Valore
        //String valore = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ, 2).getValue();
        //if (!isStringaValorizzata(valore)) {
        //  this.addCampoObbligatorio(listaControlli, "W3GARAREQ", "VALORE", pagina);
        //}
        // Flag esclusione
        String flag_esclusione = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ, 3).getValue();
        if (!isStringaValorizzata(flag_esclusione)) {
          this.addCampoObbligatorio(listaControlli, "W3GARAREQ", "FLAG_ESCLUSIONE", pagina);
        }
        // Flag comprova offerta
        String flag_comprova_offerta = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ, 4).getValue();
        if (!isStringaValorizzata(flag_comprova_offerta)) {
          this.addCampoObbligatorio(listaControlli, "W3GARAREQ", "FLAG_COMPROVA_OFFERTA", pagina);
        }

        // Flag avvalimento
        String flag_avvalimento = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ, 5).getValue();
        if (!isStringaValorizzata(flag_avvalimento)) {
          this.addCampoObbligatorio(listaControlli, "W3GARAREQ", "FLAG_AVVALIMENTO", pagina);
        }
        // Flag bando tipo
        String flag_bando_tipo = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ, 6).getValue();
        if (!isStringaValorizzata(flag_bando_tipo)) {
          this.addCampoObbligatorio(listaControlli, "W3GARAREQ", "FLAG_BANDO_TIPO", pagina);
        }
        // Flag riservatezza
        String flag_riservatezza = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ, 7).getValue();
        if (!isStringaValorizzata(flag_riservatezza)) {
          this.addCampoObbligatorio(listaControlli, "W3GARAREQ", "FLAG_RISERVATEZZA", pagina);
        }
      }

      // controllare eventualmente la bontà per i CIG

      pagina = "Sezione I.2) Eventuali CIG associati";
      String selectW3GARAREQCIG = "select cig from w3garareqcig where numgara = ? and numreq = ?";

      List<?> datiW3GARAREQCIG = sqlManager.getListVector(selectW3GARAREQCIG, new Object[] {numgara, numreq });
      if (datiW3GARAREQCIG != null && datiW3GARAREQCIG.size() > 0) {
        for (int j = 0; j < datiW3GARAREQCIG.size(); j++) {
          List<Object> listaControlliCig = new Vector<Object>();
          String titolo = "CIG n. " + (j + 1);
          // CIG
          String cig = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQCIG.get(j), 0).getValue();
          if (!isStringaValorizzata(cig)) {
            this.addCampoObbligatorio(listaControlliCig, "W3GARAREQCIG", "CIG", pagina);
          }
          if(listaControlliCig!=null && listaControlliCig.size() > 0){
            this.setTitolo(listaControlliCig, titolo);
            listaControlli.addAll(listaControlliCig);
          }
        }
      }
      // controllare eventualmente la bontà per i DOC
      pagina = "Sezione I.3) Eventuali documenti richiesti";
      String selectW3GARAREQDOC = "select codice_tipo_doc, " // 0
          + "descrizione, " // 1
          + "emettitore, " // 2
          + "fax, " // 3
          + "telefono, " // 4
          + "mail, " // 5
          + "mail_pec " // 6
          + "from w3garareqdoc where numgara = ? and numreq = ?";

      List<?> datiW3GARAREQDOC = sqlManager.getListVector(selectW3GARAREQDOC, new Object[] {numgara, numreq });
      if (datiW3GARAREQDOC != null && datiW3GARAREQDOC.size() > 0) {
        for (int j = 0; j < datiW3GARAREQDOC.size(); j++) {
          List<Object> listaControlliDoc = new Vector<Object>();
          String titolo = "Documento n. " + (j + 1);

          // Codice Tipo Doc
          Long codice_tipo_doc = (Long) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 0).getValue();
          if (codice_tipo_doc == null) {
            this.addCampoObbligatorio(listaControlliDoc, "W3GARAREQDOC", "CODICE_TIPO_DOC", pagina);
          }
          // Descrizione
          String descrizione = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 1).getValue();
          if (!isStringaValorizzata(descrizione)) {
            this.addCampoObbligatorio(listaControlliDoc, "W3GARAREQDOC", "DESCRIZIONE", pagina);
          }

          // Emettitore
          String emettitore = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 2).getValue();
          if (!isStringaValorizzata(emettitore)) {
            this.addCampoObbligatorio(listaControlliDoc, "W3GARAREQDOC", "EMETTITORE", pagina);
          }
          // Fax
          String fax = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 3).getValue();
          if (!isStringaValorizzata(fax)) {
            this.addCampoObbligatorio(listaControlliDoc, "W3GARAREQDOC", "FAX", pagina);
          } else {
            this.validazioneNumTelFax(fax, "W3GARAREQDOC", "FAX", pagina, listaControlliDoc);
          }
          // Telefono
          String telefono = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 4).getValue();
          if (!isStringaValorizzata(telefono)) {
            this.addCampoObbligatorio(listaControlliDoc, "W3GARAREQDOC", "TELEFONO", pagina);
          } else {
            this.validazioneNumTelFax(telefono, "W3GARAREQDOC", "TELEFONO", pagina, listaControlliDoc);
          }
          // Mail
          String mail = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 5).getValue();
          if (!isStringaValorizzata(mail)) {
            this.addCampoObbligatorio(listaControlliDoc, "W3GARAREQDOC", "MAIL", pagina);
          }
          // Mail PEC
          String mailpec = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 6).getValue();
          if (!isStringaValorizzata(mailpec)) {
            this.addCampoObbligatorio(listaControlliDoc, "W3GARAREQDOC", "MAIL_PEC", pagina);
          }
          if (listaControlliDoc != null && listaControlliDoc.size() > 0) {
            this.setTitolo(listaControlli, titolo);
            listaControlli.addAll(listaControlliDoc);
          }
        }
      }

    } catch (SQLException e) {
      throw new GestoreException("Errore nella lettura delle informazioni relative ai requisiti", "validazioneW3GARAREQ", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("validazioneW3GARAREQ: fine metodo");

  }

  /**
   * Aggiunge un messaggio alla listaControlli.
   *
   * @param listaControlli listaControlli
   * @param entita entita
   * @param descrizione descrizione
   * @param tipo tipo
   * @param pagina pagina
   * @param messaggio messaggio
   */
  private void addAvviso(final List<Object> listaControlli, final String entita,
          final String descrizione, final String tipo, final String pagina, final String messaggio, final String codice) {
    listaControlli.add(((new Object[] { tipo, pagina, descrizione,
        messaggio, codice })));
  }

  /**
   * Aggiunge un messaggio bloccante alla lista dei controlli
   *
   * @param listaControlli
   * @param entita
   * @param campo
   * @param pagina
   */
  private void addCampoObbligatorio(List<Object> listaControlli, String entita,
      String campo, String pagina) {
    String descrizione = this.getDescrizioneCampo(entita, campo);
    String messaggio = "Il campo &egrave; obbligatorio.";
    listaControlli.add(((new Object[] { "E", pagina, descrizione,
        messaggio })));
  }

  /**
   * Restituisce la descrizione del campo
   *
   * @param entita
   * @param campo
   * @return
   */
  private String getDescrizioneCampo(String entita, String campo) {
    String descrizione = "";
    try {
      Campo c = DizionarioCampi.getInstance().getCampoByNomeFisico(
          entita + "." + campo);
      descrizione = c.getDescrizioneWEB();
    } catch (Throwable t) {

    }

    return descrizione;
  }

  /**
   * Valorizzazione di una stringa
   *
   * @param valore
   * @return Ritorna true se <i>valore<i> e' null oppure, se diversa da null, se
   *         <i>valore.trim()<i> ha un numero di caratteri maggiore di zero
   */
  private boolean isStringaValorizzata(String valore) {
    boolean result = false;

    if (valore != null && valore.trim().length() > 0) result = true;

    return result;
  }

  /**
   * Utilizzata per settare il tipo T ossia il titolo all'interno di una tabella
   *
   * @param listaControlli
   * @param pagina
   */
  private void setTitolo(List<Object> listaControlli, String titolo) {
    listaControlli.add(((new Object[] { "T", titolo, "", "" })));
  }

  /**
   * Validazione Numero Telefono
   *
   * @param telefono
   * @param entita
   * @param campo
   * @param pagina
   * @param listaControlli
   */
  private void validazioneNumTelFax(String numTelFax, String entita, String campo, String pagina, List<Object> listaControlli)
      throws GestoreException {

    try {
      // Pattern XSD -->  [0-9]+
      String regex = "[0-9]+";
      String messaggioNumTelFax = "Il campo non rispetta il formato previsto <br>Esempio:<br>[1230123456789]";
      if (numTelFax != null) {
        if (!numTelFax.matches(regex)) {
          this.addAvviso(listaControlli, entita, campo, "E", pagina, messaggioNumTelFax,"");
        }
      }
    } catch (PatternSyntaxException pse) {
      throw new GestoreException("Errore in validazione del numero di telefono o fax", "validazioneNumTelFax", pse);
    }
  }

  /**
   * Validazione Orario
   *
   * @param orario
   * @param entita
   * @param campo
   * @param pagina
   * @param listaControlli
   */
  private void validazioneOrario(String orario, String entita, String campo, String pagina, List<Object> listaControlli)
      throws GestoreException {

    try {
      // Pattern XSD -->  Formato HH:MI
      String regex = "^([01][0-9]|2[0-3]|[1-9]):([0-5][0-9]|[0-9])$";
      String messaggioOrario = "Il campo non rispetta il formato previsto <br>Esempio:<br>[12:18]";
      if (orario != null) {
        if (!orario.matches(regex)) {
          this.addAvviso(listaControlli, entita, "Orario scadenza pagamenti", "E", pagina, messaggioOrario,"");
        }
      }
    } catch (PatternSyntaxException pse) {
      throw new GestoreException("Errore in validazione dell'ora", "validazioneOrario", pse);
    }
  }
}
