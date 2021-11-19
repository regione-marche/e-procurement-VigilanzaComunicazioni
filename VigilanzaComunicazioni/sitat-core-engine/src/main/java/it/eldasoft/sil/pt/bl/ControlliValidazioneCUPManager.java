package it.eldasoft.sil.pt.bl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.metadata.cache.DizionarioCampi;
import it.eldasoft.utils.metadata.domain.Campo;
import it.eldasoft.utils.utility.UtilityDate;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Luca.Giacomazzo - Eldasoft S.p.A. Treviso
 *
 */

public class ControlliValidazioneCUPManager {

	 /** Logger Log4J di classe. */
  private static Logger      logger = Logger.getLogger(ControlliValidazioneCUPManager.class);
  /** manager per operazioni di interrogazione del db. */
  private SqlManager sqlManager;

  /**
   * 
   * @return SqlManager
   */
  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  /**setSqlManager.
   * @param sqlManagerParam
   *        sqlManager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManagerParam) {
    this.sqlManager = sqlManagerParam;
  }

  /**validate.
   * @param params
   *        params.
   * @throws JspException
   * 		JspException
   * @return HashMap
   */
  public HashMap<String, Object> validate(Object[] params) throws JspException {
    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
    Long id = new Long((String) params[1]);
    infoValidazione = this.validate(id);
    return infoValidazione;
  }
  /**validate.
   * @param id
   *        params.
   * @throws JspException
   * 		JspException
   * @return HashMap
   */
  public HashMap<String, Object> validate(Long id) throws JspException {
    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
    String titolo = null;
    List<Object> listaControlli = new Vector<Object>();

    try {
      titolo = "";

      this.validazioneCUPDATIDatiGenerali(sqlManager, id, listaControlli);
      this.validazioneCUPLOC(sqlManager, id, listaControlli);
      this.validazioneCUPDATIFinanziamento(sqlManager, id, listaControlli);

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
   * Validazione dei dati generali del CUP.
   * 
   * @param sqlManagerParam
   * 		sqlManager
   * @param id
   * 		id
   * @param listaControlli
   * 		listaControlli
   * @throws GestoreException
   * 		GestoreException
   */

  private void validazioneCUPDATIDatiGenerali(final SqlManager sqlManagerParam, final Long id,
		  final List<Object> listaControlli) throws GestoreException {
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneCUPDATIDatigenerali: inizio metodo");
    }
    String selectCUPDATI = "select anno_decisione, " // 0
        + "natura, " // 1
        + "tipologia, " // 2
        + "settore, " // 3
        + "sottosettore, " // 4
        + "categoria, " // 5
        + "nome_stru_infrastr, " // 6
        + "str_infrastr_unica, " // 7
        + "tipo_ind_area_rifer, " // 8
        + "ind_area_rifer, " // 9
        + "descrizione_intervento, " // 10
        + "strum_progr, " // 11
        + "desc_strum_progr, " // 12
        + "denom_impr_stab, " // 13
        + "partita_iva, " // 14
        + "denominazione_progetto, " // 15
        + "ente, " // 16
        + "obiett_corso, " // 17
        + "mod_intervento_frequenza, " // 18
        + "servizio, " // 19
        + "bene, " // 20
        + "ragione_sociale, " // 21
        + "finalita, " // 22
        + "beneficiario, " // 23
        + "cumulativo, " // 24
        + "atto_amministr, " // 25
        + "scopo_intervento " // 26
        + "from cupdati where id = ?";

    String pagina = "Dati generali";
    String entita = "CUPDATI";

    try {
      List< ? > datiCUPDATI = sqlManagerParam.getVector(selectCUPDATI,
          new Object[] { id });
      if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

        Long annoDecisione = (Long) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 0).getValue();
        if (annoDecisione == null) {
          this.addCampoObbligatorio(listaControlli, entita, "ANNO_DECISIONE",
              pagina);
        } else {
          // Anno decisione
          int annoCorrente = UtilityDate.getDataOdiernaAsDate().getYear();
          if (annoDecisione.intValue() > annoCorrente + 1900) {
            String messaggio = "Il valore indicato non deve essere superiore all'anno corrente";
            this.addAvviso(listaControlli, entita, "ANNO_DECISIONE", "E", pagina,
                messaggio);
          }
        }

        // Natura e tipologia
        pagina = "Natura e tipologia";
        String natura = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 1).getValue();
        String tipologia = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 2).getValue();

        if (natura == null) {
          this.addCampoObbligatorio(listaControlli, entita, "NATURA", pagina);
        }
        if (tipologia == null) {
          this.addCampoObbligatorio(listaControlli, entita, "TIPOLOGIA", pagina);
        }

        // Settore, sottosettore e categoria
        pagina = "Settore, sottosettore e categoria";
        if (SqlManager.getValueFromVectorParam(datiCUPDATI, 3).getValue() == null) {
          this.addCampoObbligatorio(listaControlli, entita, "SETTORE", pagina);
        }
        if (SqlManager.getValueFromVectorParam(datiCUPDATI, 4).getValue() == null) {
          this.addCampoObbligatorio(listaControlli, entita, "SOTTOSETTORE",
              pagina);
        }
        if (SqlManager.getValueFromVectorParam(datiCUPDATI, 5).getValue() == null) {
          this.addCampoObbligatorio(listaControlli, entita, "CATEGORIA", pagina);
        }

        // Cumulativo
        String cumulativo = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 24).getValue();
        
        // atto_amministr
        String atto_amministr = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 25).getValue();
        
        // scopo_intervento
        String scopo_intervento = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 26).getValue();
        

        // Descrizione del progetto
        pagina = "Descrizione del progetto";
        boolean ctrNomeStruInfrastu = false;
        boolean ctrStrInfrastrUnica = false;
        boolean ctrTipoIndAreaRifer = true;
        boolean ctrIndAreaRifer = true;
        boolean ctrDescrizioneIntervento = false;
        boolean ctrStrumProgr = true;
        boolean ctrDescStrumProgr = true;
        boolean ctrDenomImprStab = false;
        boolean ctrPartitaIva = false;
        boolean ctrDenominazioneProgetto = false;
        boolean ctrEnte = false;
        boolean ctrObiettivoCorso = false;
        boolean ctrModInterventoFrequenza = false;
        boolean ctrServizio = false;
        boolean ctrBene = false;
        boolean ctrFinalita = false;
        boolean ctrRagioneSociale = false;
        boolean ctrBeneficiario = false;
        boolean ctrCupCumulativo = false;
        
        if ("1".equals(cumulativo)) {
          ctrTipoIndAreaRifer = false;
          ctrIndAreaRifer = false;
          ctrStrumProgr = false;
          ctrDescStrumProgr = false;
          ctrCupCumulativo = true;
        } else {
          if ("03".equals(natura)) {
            ctrNomeStruInfrastu = true;
            ctrStrInfrastrUnica = true;
            ctrDescrizioneIntervento = true;
          }
          if ("07".equals(natura)) {
            ctrDenomImprStab = true;
            ctrPartitaIva = true;
            ctrDescrizioneIntervento = true;
          }
          if ("02".equals(natura)) {
            if ("14".equals(tipologia)) {
              ctrDenominazioneProgetto = true;
              ctrEnte = true;
              ctrDescrizioneIntervento = true;
            }
            if ("12".equals(tipologia)) {
              ctrDenominazioneProgetto = true;
              ctrEnte = true;
              ctrObiettivoCorso = true;
              ctrModInterventoFrequenza = true;
            }
            if (!"14".equals(tipologia) && !"12".equals(tipologia)) {
              ctrNomeStruInfrastu = true;
              ctrServizio = true;
            }
          }
          if ("01".equals(natura)) {
            ctrNomeStruInfrastu = true;
            ctrBene = true;
          }
          if ("08".equals(natura)) {
            ctrRagioneSociale = true;
            ctrPartitaIva = true;
            ctrFinalita = true;
          }
          if ("06".equals(natura)) {
            ctrBeneficiario = true;
            ctrPartitaIva = true;
            ctrNomeStruInfrastu = true;
            ctrDescrizioneIntervento = true;
          }
        }

        // Nome delle struttura coinvolte
        if (ctrNomeStruInfrastu) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 6).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita,
                "NOME_STRU_INFRASTR", pagina);
          } else if (SqlManager.getValueFromVectorParam(datiCUPDATI, 6).getValue().toString().length() < 5) {
        	  String messaggio = "Il campo deve essere di almeno 5 caratteri";
              this.addAvviso(listaControlli, entita, "NOME_STRU_INFRASTR", "E",
                  pagina, messaggio);
          } else {
        	  try {
        		  Integer.parseInt(SqlManager.getValueFromVectorParam(datiCUPDATI, 6).getValue().toString());
        		  String messaggio = "Il campo deve contenere almeno un carattere alfabetico";
                  this.addAvviso(listaControlli, entita, "NOME_STRU_INFRASTR", "E",
                      pagina, messaggio);
        	  } catch (Exception ex) {
        		  ;
        	  }
          }
        }
        // Struttura unica ?
        if (ctrStrInfrastrUnica) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 7).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita,
                "STR_INFRASTR_UNICA", pagina);
          }
        }
        // Denominazione impresa
        if (ctrDenomImprStab) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 13).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita,
                "DENOM_IMPR_STAB", pagina);
          }
        }
        // Ragione sociale
        if (ctrRagioneSociale) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 21).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita,
                "RAGIONE_SOCIALE", pagina);
          }
        }
        // Finalita'
        if (ctrFinalita) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 22).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "FINALITA",
                pagina);
          }
        }
        // Beneficiario
        if (ctrBeneficiario) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 23).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "BENEFICIARIO",
                pagina);
          }
        }
        // Partita IVA
        if (ctrPartitaIva) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 14).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "PARTITA_IVA",
                pagina);
          }
        }
        // Denominazione progetto
        if (ctrDenominazioneProgetto) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 15).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita,
                "DESCRIZIONE_INTERVENTO", pagina);
          }
        }
        // Ente
        if (ctrEnte) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 16).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "ENTE", pagina);
          }
        }
        // Tipo indirizzo
        if (ctrTipoIndAreaRifer) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 8).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita,
                "TIPO_IND_AREA_RIFER", pagina);
          }
        }
        // Indirizzo
        if (ctrIndAreaRifer) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 9).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "IND_AREA_RIFER",
                pagina);
          }
        }
        // Descrizione intervento
        if (ctrDescrizioneIntervento) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 10).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita,
                "DESCRIZIONE_INTERVENTO", pagina);
          }
        }
        // Obiettivo del corso
        if (ctrObiettivoCorso) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 17).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "OBIETT_CORSO",
                pagina);
          }
        }
        // Modalita' dell'intervento
        if (ctrModInterventoFrequenza) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 18).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita,
                "MOD_INTERVENTO_FREQUENZA", pagina);
          }
        }
        // Servizio
        if (ctrServizio) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 19).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "SERVIZIO",
                pagina);
          }
        }
        // Bene
        if (ctrBene) {
          if (SqlManager.getValueFromVectorParam(datiCUPDATI, 20).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "BENE", pagina);
          }
        }

        // Strumento di programmazione e Descrizione dello strumento di
        // programmazione
        if (ctrStrumProgr) {
          String strumProgr = (String) SqlManager.getValueFromVectorParam(
              datiCUPDATI, 11).getValue();
          if (strumProgr == null) {
            this.addCampoObbligatorio(listaControlli, entita, "STRUM_PROGR",
                pagina);
          } else if (ctrDescStrumProgr && "99".equals(strumProgr)) {
            if (SqlManager.getValueFromVectorParam(datiCUPDATI, 12).getValue() == null) {
              this.addCampoObbligatorio(listaControlli, entita,
                  "DESC_STRUM_PROGR", pagina);
            }
          }
        }
        
        // se il Cup è Cumulativo sono obbligatori l'atto amministrativo e lo scopo intervento 
        if (ctrCupCumulativo) {
        	if (StringUtils.isEmpty(atto_amministr)) {
        		this.addAvviso(listaControlli, entita, "atto_amministr", "E",
                        pagina, "L'atto amministrativo è obbligatorio se il cup è cumulativo"); 
        	}
        	if (StringUtils.isEmpty(scopo_intervento)) {
        		this.addAvviso(listaControlli, entita, "scopo_intervento", "E",
                        pagina, "Lo scopo intervento è obbligatorio se il cup è cumulativo"); 
        	}
        }
      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella lettura delle informazioni relative ai dati generali per la richiesta CUP",
          "validazioneCUPDATIDatiGenerali", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validazioneCUPDATIDatiGenerali: fine metodo");
    }
  }

  /**
   * Validazione dei dati di finanziamento.
   * 
   * @param sqlManagerParam
   * 		sqlManager
   * @param id
   * 		id
   * @param listaControlli listaControlli
   * @throws GestoreException
   * 		GestoreException
   */
  private void validazioneCUPDATIFinanziamento(final SqlManager sqlManagerParam, final Long id,
      final List<Object> listaControlli) throws GestoreException {
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneCUPDATIFinanziamento: inizio metodo");
    }
    String selectCUPDATI = "select sponsorizzazione, " // 0
        + "finanza_progetto, " // 1
        + "cop_statale, " // 2
        + "cop_regionale, " // 3
        + "cop_provinciale, " // 4
        + "cop_comunale, " // 5
        + "cop_altrapubblica, " // 6
        + "cop_comunitaria, " // 7
        + "cop_privata, " // 8
        + "costo, " // 9
        + "finanziamento, " // 10
        + "cumulativo, " // 11
        + "natura, " // 12
        + "cop_pubbdaconfermare " // 13
        + "from cupdati where id = ?";

    String pagina = "Finanziamento";
    String entita = "CUPDATI";

    try {
      List< ? > datiCUPDATI = sqlManagerParam.getVector(selectCUPDATI,
          new Object[] { id });
      if (datiCUPDATI != null && datiCUPDATI.size() > 0) {

        // Cumulativo
        String cumulativo = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 11).getValue();

        // Natura
        String natura = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 12).getValue();

        // Sponsorizzazione
        String sponsorizzazione = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 0).getValue();

        // Finanza di progetto
        String finanzaProgetto = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 1).getValue();

        if (natura != null && !"02".equals(natura) && !"03".equals(natura)) {
          if (sponsorizzazione != null) {
            String messaggio = "Il campo non deve essere valorizzato se "
                + "la natura del CUP è diversa da"
                + " \"Realizzazione di lavori pubblici\" e"
                + " da \"Realizzazione e acquisto di servizi\"";
            this.addAvviso(listaControlli, entita, "SPONSORIZZAZIONE", "E",
                pagina, messaggio);
          }
        }

        if (cumulativo != null && "1".equals(cumulativo)) {
          if (sponsorizzazione != null && !"N".equals(sponsorizzazione)) {
            String messaggio = "Per un progetto cumulativo il valore del campo "
                + "deve essere impostato a \"No\"";
            this.addAvviso(listaControlli, entita, "SPONSORIZZAZIONE", "E",
                pagina, messaggio);
          }
        }

        if (sponsorizzazione != null
            && finanzaProgetto != null
            && "T".equals(sponsorizzazione)
            && !"N".equals(finanzaProgetto)) {
          String messaggio = "Se impostato a \"Totale\", \"Finanza di progetto\" deve essere impostato a \"No\"";
          this.addAvviso(listaControlli, entita, "SPONSORIZZAZIONE", "E",
              pagina, messaggio);
        }

        if (sponsorizzazione != null
            && finanzaProgetto != null
            && !"N".equals(sponsorizzazione)
            && "P".equals(finanzaProgetto)) {
          String messaggio = "Se impostata a \"Pura\", \"Sponsorizzazione\" deve essere impostato a \"No\"";
          this.addAvviso(listaControlli, entita, "FINANZA_PROGETTO", "E",
              pagina, messaggio);

        }

        // Costo del progetto
        Double costo = (Double) SqlManager.getValueFromVectorParam(datiCUPDATI,
            9).getValue();
        if (costo == null) {
          this.addCampoObbligatorio(listaControlli, entita, "COSTO", pagina);
        } else if (costo.doubleValue() < 0) {
          this.addAvviso(listaControlli, entita, "COSTO", "E", pagina,
              "Indicare un valore maggiore o uguale a 0");
        }

        // Finanziamento
        Double finanziamento = (Double) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 10).getValue();
        if (finanziamento == null) {
          this.addCampoObbligatorio(listaControlli, entita, "FINANZIAMENTO",
              pagina);
        } else {
          if (finanziamento.doubleValue() < 0) {
            this.addAvviso(listaControlli, entita, "FINANZIAMENTO", "E",
                pagina, "Indicare un valore maggiore o uguale a 0");
          }
          if (finanziamento.doubleValue() >= 1000000
              && cumulativo != null
              && "1".equals(cumulativo)) {
            String messaggio = "Per un progetto cumulativo il finanziamento "
                + "assegnato deve essere inferiore ad 1.000.000 di euro";
            this.addAvviso(listaControlli, entita, "FINANZIAMENTO", "E",
                pagina, messaggio);
          }
        }

        // Tipologia di copertura finanziamento
        boolean validAltre = false;
        boolean validPrivata = false;

        String copStatale = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 2).getValue();
        String copRegionale = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 3).getValue();
        String copProvinciale = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 4).getValue();
        String copComunale = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 5).getValue();
        String copAltrapubblica = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 6).getValue();
        String copComunitaria = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 7).getValue();
        String copPrivata = (String) SqlManager.getValueFromVectorParam(
            datiCUPDATI, 8).getValue();
        String copPubblicaDaConfermare = (String) SqlManager.getValueFromVectorParam(
                datiCUPDATI, 13).getValue();
        
        if (copStatale != null && "1".equals(copStatale)) {
        	validAltre = true;
        }
        if (copRegionale != null && "1".equals(copRegionale)) {
          validAltre = true;
        }
        if (copProvinciale != null && "1".equals(copProvinciale)) {
          validAltre = true;
        }
        if (copComunale != null && "1".equals(copComunale)) {
          validAltre = true;
        }
        if (copAltrapubblica != null && "1".equals(copAltrapubblica)) {
          validAltre = true;
        }
        if (copComunitaria != null && "1".equals(copComunitaria)) {
          validAltre = true;
        }
        if (copPrivata != null && "1".equals(copPrivata)) {
          validPrivata = true;
        }
        if (copPubblicaDaConfermare != null && "1".equals(copPubblicaDaConfermare)) {
        	validAltre = true;
        }

        // Controlli su costo e finanziamento
        // Per natura diversa da 07 - CONCESSIONE DI AIUTI e tipologia di
        // copertura finanziaria non contenente la tipologia 007 - PRIVATA,
        // l’importo del finanziamento deve essere uguale a quello del costo.
        if (!"07".equals(natura)
            && !validPrivata
            && costo != null
            && finanziamento != null
            && (!costo.equals(finanziamento))) {
          String messaggio = "Per Natura diversa da \"Concessione di aiuti\" e Tipologia di copertura "
              + "finanziaria non contenente la tipologia \"Privata\", l'importo "
              + "del Finanziamento deve essere uguale a quello del Costo";
          String campi = this.getDescrizioneCampo(entita, "COSTO");
          campi += ", " + this.getDescrizioneCampo(entita, "FINANZIAMENTO");
          listaControlli.add(((Object) (new Object[] { "E", pagina, campi,
              messaggio })));
        }

        // Per tipologia di copertura finanziaria contente la tipologia 007 -
        // PRIVATA,
        // l’importo del finanziamento deve essere minore di quello del costo.
        if (validPrivata
            && costo != null
            && finanziamento != null
            && (finanziamento.doubleValue() >= costo.doubleValue())) {
          String messaggio = "Per Tipologia di copertura finanziaria "
              + "contenente la tipologia \"Privata\" l'importo del "
              + "finanziamento deve essere minore di quello del costo";
          String campi = this.getDescrizioneCampo(entita, "COSTO");
          campi += ", " + this.getDescrizioneCampo(entita, "FINANZIAMENTO");
          listaControlli.add(((Object) (new Object[] { "E", pagina, campi,
              messaggio })));
        }

        // Nel caso di finanza di progetto “pura” l’importo del
        // finanziamento deve essere 0.
        if (finanzaProgetto != null
            && "P".equals(finanzaProgetto)
            && finanziamento != null
            && finanziamento.doubleValue() != 0) {
          String messaggio = "Nel caso di Finanza di progetto \"Pura\" "
              + "l'importo del finanziamento deve essere uguale a 0";
          String campo = this.getDescrizioneCampo(entita, "FINANZIAMENTO");
          listaControlli.add(((Object) (new Object[] { "E", pagina, campo,
              messaggio })));
        }

        // Nel caso di Finanza di progetto “assistita”, il finanziamento
        // deve essere diverso da 0 ed inferiore al costo del progetto.
        if (finanzaProgetto != null
            && "A".equals(finanzaProgetto)
            && finanziamento != null
            && finanziamento.doubleValue() == 0) {
          String messaggio = "Nel caso di Finanza di progetto \"Assistita\" "
              + "l'importo del finanziamento deve essere diverso da 0";
          String campo = this.getDescrizioneCampo(entita, "FINANZIAMENTO");
          listaControlli.add(((Object) (new Object[] { "E", pagina, campo,
              messaggio })));
        }

        if (finanzaProgetto != null
            && "A".equals(finanzaProgetto)
            && finanziamento != null
            && costo != null
            && finanziamento.doubleValue() >= costo.doubleValue()) {
          String messaggio = "Nel caso di Finanza di progetto \"Assistita\" "
              + "l'importo del finanziamento deve essere inferiore al costo";
          String campi = this.getDescrizioneCampo(entita, "COSTO");
          campi += ", " + this.getDescrizioneCampo(entita, "FINANZIAMENTO");
          listaControlli.add(((Object) (new Object[] { "E", pagina, campi,
              messaggio })));
        }

        // Nel caso di Sponsorizzazione Totale il finanziamento deve essere
        // uguale a 0.
        if (sponsorizzazione != null
            && "T".equals(sponsorizzazione)
            && finanziamento != null
            && finanziamento.doubleValue() != 0) {
          String messaggio = "Nel caso di Sponsorizzazione \"Totale\" "
              + "l'importo del finanziamento deve essere uguale a 0";
          String campo = this.getDescrizioneCampo(entita, "FINANZIAMENTO");
          listaControlli.add(((Object) (new Object[] { "E", pagina, campo,
              messaggio })));

        }

        // Per Natura diversa da Aiuti il finanziamento deve essere minore
        // del costo se è presente la tipologia di copertura finanziaria
        // “Privata”
        if (natura != null
            && !"07".equals(natura)
            && validPrivata 
            && finanziamento != null
            && costo != null
            && finanziamento.doubleValue() >= costo.doubleValue()) {
          String messaggio = "Per Natura diversa da \"Concessione di aiuti\" e Tipologia di copertura "
              + "finanziaria contenente la tipologia \"Privata\", l'importo "
              + "del Finanziamento deve essere minore dell'importo del Costo";
          String campi = this.getDescrizioneCampo(entita, "COSTO");
          campi += ", " + this.getDescrizioneCampo(entita, "FINANZIAMENTO");
          listaControlli.add(((Object) (new Object[] { "E", pagina, campi,
              messaggio })));
        }

        if (!validAltre  && !validPrivata) {
          listaControlli.add(((Object) (new Object[] { "E", pagina,
              "Tipologia di copertura finanziaria",
              "Valorizzare a 'Si' almeno una tipologia" })));
        }

        // Nel caso di \"Finanza di progetto\" \"Pura\" la \"Tipologia di
        // copertura finanziaria\" puo' essere solo \"Privata\"
        if (finanzaProgetto != null
            && "P".equals(finanzaProgetto)
            && (!validPrivata  || validAltre)) {
          String messaggio = "Nel caso di Finanza di progetto \"Pura\" "
              + "la Tipologia di copertura finanziaria puo' essere solo \"Privata\"";
          listaControlli.add(((Object) (new Object[] { "E", pagina,
              "Tipologia di copertura finanziaria", messaggio })));
        }

        // Nel caso di \"Finanza di progetto\" \"Assistita\" la \"Tipologia di
        // copertura finanziaria\" deve
        // essere caratterizzata dalla tipologia \"Privata\” e, necessariamente,
        // da altre tipologie inserite dall’utente
        if (finanzaProgetto != null
            && "A".equals(finanzaProgetto)
            && (!validAltre || !validPrivata)) {
          String messaggio = "Nel caso di Finanza di progetto \"Assistita\" "
              + "la Tipologia di copertura finanziaria deve "
              + "essere caratterizzata dalla tipologia \"Privata\" e, "
              + "necessariamente, da altre tipologie inserite dall'utente";
          listaControlli.add(((Object) (new Object[] { "E", pagina,
              "Tipologia di copertura finanziaria", messaggio })));
        }

        // Nel caso di "\Sponsorizzazione\" \"Totale\" la \"Tipologia di
        // copertura finanziaria\" deve essere solo “Privata”.
        if (sponsorizzazione != null
            && "T".equals(sponsorizzazione)
            && (!validPrivata || validAltre)) {
          String messaggio = "Nel caso di Sponsorizzazione \"Totale\" "
              + "la Tipologia di copertura finanziaria deve essere solo \"Privata\"";
          listaControlli.add(((Object) (new Object[] { "E", pagina,
              "Tipologia di copertura finanziaria", messaggio })));
        }

      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella lettura delle informazioni relative ai dati generali per la richiesta CUP",
          "validazioneCUPDATIFinanziamento", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validazioneCUPDATIFinanziamento: fine metodo");
    }
  }
  
  /**
   * Validazione dei dati di localizzazione.
   * 
   * @param sqlManagerParam sqlManager
   * @param id id
   * @param listaControlli listaControlli
   * @throws GestoreException GestoreException
   */
  private void validazioneCUPLOC(final SqlManager sqlManagerParam, final Long id,
      final List<Object> listaControlli) throws GestoreException {

    if (logger.isDebugEnabled()) {
      logger.debug("validazioneCUPLOC: inizio metodo");
    }

    String selectCUPLOC = "select stato, " // 0
        + "regione, " // 1
        + "provincia, " // 2
        + "comune " // 3
        + "from cuploc where id = ?";

    String entita = "CUPLOC";

    try {
      List< ? > datiCUPLOC = sqlManagerParam.getListVector(selectCUPLOC,
          new Object[] { id });
      if (datiCUPLOC != null && datiCUPLOC.size() > 0) {
        for (int i = 0; i < datiCUPLOC.size(); i++) {
          String pagina = "Localizzazione n. " + (i + 1);
          // Stato
          if (SqlManager.getValueFromVectorParam(datiCUPLOC.get(i), 0).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "STATO", pagina);
          }
          // Regione
          if (SqlManager.getValueFromVectorParam(datiCUPLOC.get(i), 1).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "REGIONE", pagina);
          }
          // Provincia
          if (SqlManager.getValueFromVectorParam(datiCUPLOC.get(i), 2).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "PROVINCIA",
                pagina);
          }
          // Comune
          if (SqlManager.getValueFromVectorParam(datiCUPLOC.get(i), 3).getValue() == null) {
            this.addCampoObbligatorio(listaControlli, entita, "COMUNE", pagina);
          }
        }

      } else {
        listaControlli.add(((Object) (new Object[] { "E", "Localizzazione",
            "Localizzazione", "Indicare almeno una localizzazione" })));
      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella lettura delle informazioni relative alla localizzazione",
          "validazioneCUPLOC", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validazioneCUPLOC: fine metodo");
    }
  }

  /**
   * Aggiunge un messaggio bloccante alla listaControlli.
   * 
   * @param listaControlli listaControlli
   * @param entita entita
   * @param campo campo
   * @param pagina pagina
   */
  private void addCampoObbligatorio(final List<Object> listaControlli, final String entita,
      final String campo, final String pagina) {
    String descrizione = this.getDescrizioneCampo(entita, campo);
    String messaggio = "Il campo &egrave; obbligatorio";
    listaControlli.add(((Object) (new Object[] { "E", pagina, descrizione,
        messaggio })));
  }

  /**
   * Aggiunge un messaggio alla listaControlli.
   * 
   * @param listaControlli listaControlli
   * @param entita entita
   * @param campo campo
   * @param tipo tipo
   * @param pagina pagina
   * @param messaggio messaggio
   */
  private void addAvviso(final List<Object> listaControlli, final String entita,
		  final String campo, final String tipo, final String pagina, final String messaggio) {
    String descrizione = this.getDescrizioneCampo(entita, campo);
    listaControlli.add(((Object) (new Object[] { tipo, pagina, descrizione,
        messaggio })));

  }

  /**
   * Restituisce la descrizione WEB del campo.
   * 
   * @param entita entita
   * @param campo campo
   * @return descrizione campo
   */
  private String getDescrizioneCampo(final String entita, final String campo) {
    String descrizione = "";
    try {
      Campo c = DizionarioCampi.getInstance().getCampoByNomeFisico(
          entita + "." + campo);
      descrizione = c.getDescrizioneWEB();
    } catch (Throwable t) {
;
    }

    return descrizione;
  }

}