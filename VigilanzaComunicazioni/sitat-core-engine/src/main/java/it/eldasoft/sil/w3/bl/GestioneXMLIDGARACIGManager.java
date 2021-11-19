package it.eldasoft.sil.w3.bl;

import it.avlp.simog.massload.xmlbeans.AllegatoType;
import it.avlp.simog.massload.xmlbeans.CUPLOTTOType;
import it.avlp.simog.massload.xmlbeans.CondizioneLtType;
import it.avlp.simog.massload.xmlbeans.DatiCUPType;
import it.avlp.simog.massload.xmlbeans.DatiGaraType;
import it.avlp.simog.massload.xmlbeans.ElencoCategMercType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.FlagSOType;
import it.avlp.simog.massload.xmlbeans.GaraType;
import it.avlp.simog.massload.xmlbeans.Pubblica;
import it.avlp.simog.massload.xmlbeans.InviaRequisiti.Requisiti;
import it.avlp.simog.massload.xmlbeans.Pubblica.DatiPubblicazione;
import it.avlp.simog.massload.xmlbeans.LottoType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;
import it.avlp.simog.massload.xmlbeans.ReqDocType;
import it.avlp.simog.massload.xmlbeans.ReqGaraType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;
import it.avlp.smartCig.comunicazione.ComunicazioneInputType;
import it.avlp.smartCig.comunicazione.ComunicazioneType;
import it.avlp.smartCig.user.LoggedUserInfoType;
import it.avlp.smartCig.ws.AnnullaComunicazioneRequest;
import it.avlp.smartCig.ws.ComunicaSingolaRequest;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.BlobFile;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.w3.db.dao.DocumentoAllegatoDao;
import it.eldasoft.sil.w3.utils.UtilityDateExtension;
//import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

public class GestioneXMLIDGARACIGManager {

  /** Logger */
  static Logger                logger                  = Logger.getLogger(GestioneXMLIDGARACIGManager.class);
 
  private SqlManager           sqlManager;

  private DocumentoAllegatoDao documentoAllegatoDao;

  private W3Manager            w3Manager;

  //private static final String  PROP_PROTEZIONE_ARCHIVI = "it.eldasoft.protezionearchivi";

  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  public void setDocumentoAllegatoDao(DocumentoAllegatoDao documentoAllegatoDao) {
    this.documentoAllegatoDao = documentoAllegatoDao;
  }

  public void setW3Manager(W3Manager w3Manager) {
    this.w3Manager = w3Manager;
  }

  /**
   * Restituisce i dati XML degli allegati della gara per l'invio della
   * richiesta di pubblicazione a SIMOG
   * 
   * @param numgara
   * @return
   * @throws GestoreException
   */
  public AllegatoType[] getDocumentiAllegati(Long numgara) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("getDocumentiAllegati: inizio metodo");

    AllegatoType[] documentiAllegati = null;

    String selectW3GARADOC = "select numdoc, tipo_documento, nome_documento, note_documento from w3garadoc where numgara = ?";

    try {
      List<?> datiW3GARADOC = this.sqlManager.getListVector(selectW3GARADOC, new Object[] { numgara });

      if (datiW3GARADOC != null && datiW3GARADOC.size() > 0) {

        documentiAllegati = new AllegatoType[datiW3GARADOC.size()];

        for (int i = 0; i < datiW3GARADOC.size(); i++) {

        	AllegatoType allegato = AllegatoType.Factory.newInstance();
          

        	Long numdoc = (Long) SqlManager.getValueFromVectorParam(datiW3GARADOC.get(i), 0).getValue();

        	String tipo_documento = (String) SqlManager.getValueFromVectorParam(datiW3GARADOC.get(i), 1).getValue();
        	if (tipo_documento != null) allegato.setTipoDocumento(tipo_documento);

        	String nome_documento = (String) SqlManager.getValueFromVectorParam(datiW3GARADOC.get(i), 2).getValue();
        	if (nome_documento != null) allegato.setNomeFile(nome_documento);

        	String note_documento = (String) SqlManager.getValueFromVectorParam(datiW3GARADOC.get(i), 3).getValue();
        	if (note_documento != null) allegato.setNote(note_documento);

        	HashMap<String, Long> hMapDocumentoAllegato = new HashMap<String, Long>();
        	hMapDocumentoAllegato.put("numgara", numgara);
        	hMapDocumentoAllegato.put("numdoc", numdoc);
        	BlobFile documentoAllegato = documentoAllegatoDao.getDocumentoAllegato(hMapDocumentoAllegato);
        	if (documentoAllegato != null) allegato.setDocumento(documentoAllegato.getStream());

        	documentiAllegati[i] = allegato;
        }
      } 

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore generico nella creazione del contenuto XML per la richiesta di generazione degli identificativi di gara",
          "gestioneIDGARACIG.error", e);
    }

    if (logger.isDebugEnabled()) logger.debug("getDocumentiAllegati: fine metodo");

    return documentiAllegati;

  }

  /**
   * Restituisce i dati XML dei cup dei lotti della gara per l'invio della
   * richiesta di pubblicazione a SIMOG
   * 
   * @param numgara
   * @return
   * @throws GestoreException
   */
  public CUPLOTTOType[] getCupLotti(Long numgara) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("getCupLotti: inizio metodo");

    Pubblica pubblica = Pubblica.Factory.newInstance();
    //CUPLOTTOType[] cuplotti = null;
    String selectW3LOTT = null;
    selectW3LOTT = "select numlott, cig from w3lott where numgara = ? and STATO_SIMOG <> 2";

    String selectW3LOTTCUP = "select numcup, cup, dati_dipe from w3lottcup where numgara = ? and numlott = ? ";

    try {
      List<?> datiW3LOTT = this.sqlManager.getListVector(selectW3LOTT, new Object[] { numgara });
      if (datiW3LOTT != null && datiW3LOTT.size() > 0) {
        //cuplotti = new CUPLOTTOType[datiW3LOTT.size()];
        for (int i = 0; i < datiW3LOTT.size(); i++) {
        	//CUPLOTTOType cuplotto = CUPLOTTOType.Factory.newInstance();
          
        	Long numlott = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT.get(i), 0).getValue();
        	String cig = (String) SqlManager.getValueFromVectorParam(datiW3LOTT.get(i), 1).getValue();
        	/*if (cig != null) {
        		cuplotto.setCIG(cig);
        	}*/
        	List<?> datiW3LOTTCUP = this.sqlManager.getListVector(selectW3LOTTCUP, new Object[] { numgara, numlott });
        	if (datiW3LOTTCUP != null && datiW3LOTTCUP.size() > 0) {
        		DatiCUPType[] codiciCup = new DatiCUPType[datiW3LOTTCUP.size()];
        		for (int j = 0; j < datiW3LOTTCUP.size(); j++) {
        			DatiCUPType codiceCup = DatiCUPType.Factory.newInstance();
        			//Long numcup = (Long) SqlManager.getValueFromVectorParam(datiW3LOTTCUP.get(j), 0).getValue();
        			String cup = (String) SqlManager.getValueFromVectorParam(datiW3LOTTCUP.get(j), 1).getValue();
        			if (cup != null) {
        				codiceCup.setCUP(cup);
        			}
        			String dati_dipe = (String) SqlManager.getValueFromVectorParam(datiW3LOTTCUP.get(j), 2).getValue();
        			if (dati_dipe != null) {
        				codiceCup.setDATIDIPE(dati_dipe);
        			}
        			codiceCup.setOKUTENTE(FlagSNType.S);
        			codiciCup[j] = codiceCup;
        		}
        		CUPLOTTOType cuplotto = pubblica.addNewCUPLOTTO();
        		cuplotto.setCODICICUPArray(codiciCup);
        		if (cig != null) {
            		cuplotto.setCIG(cig);
            	}
        	} 
        	
        	//cuplotti[i] = cuplotto;
        }
      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore generico nella creazione del contenuto XML per la richiesta di generazione dei cup dei lotti di gara",
          "gestioneIDGARACIG.error", e);
    }

    if (logger.isDebugEnabled()) logger.debug("getCupLotti: fine metodo");

    return pubblica.getCUPLOTTOArray();

  }

  public DatiPubblicazione getDatiPubblicazione(Long numgara) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("getDatiPubblicazione: inizio metodo");

    DatiPubblicazione datiPubblicazione;

    try {
    	datiPubblicazione = DatiPubblicazione.Factory.newInstance();
    	PubblicazioneType pubblicazione = datiPubblicazione.addNewPubblicazione();
    	boolean esistonoDatiPubblicazione = false;

    	// Lettura dei dati e composizione del contenuto XML
    	String selectW3GARA = "select data_guce, " // 0
          + "data_guri, " // 1
          + "data_albo, " // 2
          + "data_bore, " // 3
          + "quotidiani_naz, " // 4
          + "quotidiani_reg, " // 5
          + "periodici, " // 6
          + "profilo_committente, " // 7
          + "sito_ministero_inf_trasf, " // 8
          + "sito_osservatorio, " // 9
          + "numero_guce, " // 10
          + "numero_guri, " // 11
          + "numero_bore, " // 12
          + "link_sito, " // 13
          + "flag_benicult, " // 14
          + "flag_sospeso " // 15
          + "from w3gara where numgara = ?";

    	List<?> datiW3GARA = this.sqlManager.getVector(selectW3GARA, new Object[] { numgara });

    	if (datiW3GARA != null && datiW3GARA.size() > 0) {

    		// Data pubblicazione Gazzetta Ufficiale Unione Europea
    		if (SqlManager.getValueFromVectorParam(datiW3GARA, 0).getValue() != null) {
    			pubblicazione.setDATAGUCE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW3GARA,
    					0)));
    			esistonoDatiPubblicazione = true;
    		}

    		// Data pubblicazione Gazzetta Ufficile Repubblica Italiana
    		if (SqlManager.getValueFromVectorParam(datiW3GARA, 1).getValue() != null) {
    			pubblicazione.setDATAGURI(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW3GARA,
    					1)));
    			esistonoDatiPubblicazione = true;
    		}

    		// Data pubblicazione Albo Pretorio
    		if (SqlManager.getValueFromVectorParam(datiW3GARA, 2).getValue() != null) {
    			pubblicazione.setDATAALBO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW3GARA,
    					2)));
    			esistonoDatiPubblicazione = true;
    		}

    		// Data pubblicazione Bollettino Regionale
    		if (SqlManager.getValueFromVectorParam(datiW3GARA, 3).getValue() != null) {
    			pubblicazione.setDATABORE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW3GARA,
    					3)));
    			esistonoDatiPubblicazione = true;
    		}

    		// Numero quotidiani nazionali
    		Long quotidiani_naz = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 4).getValue();
    		if (quotidiani_naz != null) {
    			pubblicazione.setQUOTIDIANINAZ(quotidiani_naz.intValue());
    			esistonoDatiPubblicazione = true;
    		}

    		// Numero quotidiani regionali
    		Long quotidiani_reg = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 5).getValue();
    		if (quotidiani_reg != null) {
    			pubblicazione.setQUOTIDIANIREG(quotidiani_reg.intValue());
    			esistonoDatiPubblicazione = true;
    		}

    		// Numero periodici
    		Long periodici = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 6).getValue();
    		if (periodici != null) {
    			pubblicazione.setPERIODICI(periodici.intValue());
    			esistonoDatiPubblicazione = true;
    		}

    		// Profilo committente
    		JdbcParametro profilo_committente = SqlManager.getValueFromVectorParam(datiW3GARA, 7);
    		if (profilo_committente.getValue() != null && !"".equals(profilo_committente.getStringValue())) {
    			if ("1".equals(profilo_committente.getStringValue())) {
    				pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.S);
    			} else {
    				pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.N);
    			}
    		}

    		// Sito Ministero Infrastrutture
    		JdbcParametro sito_ministero_inf_trasf = SqlManager.getValueFromVectorParam(datiW3GARA, 8);
    		if (sito_ministero_inf_trasf.getValue() != null && !"".equals(sito_ministero_inf_trasf.getStringValue())) {
    			if ("1".equals(sito_ministero_inf_trasf.getStringValue())) {
    				pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.S);
    			} else {
    				pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.N);
    			}
    			esistonoDatiPubblicazione = true;
    		}

    		// Sito osservatorio
    		JdbcParametro sito_osservatorio = SqlManager.getValueFromVectorParam(datiW3GARA, 9);
    		if (sito_osservatorio.getValue() != null && !"".equals(sito_osservatorio.getStringValue())) {
    			if ("1".equals(sito_osservatorio.getStringValue())) {
    				pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.S);
    			} else {
    				pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.N);
    			}
    			esistonoDatiPubblicazione = true;
    		}

    		// Numero GUCE
        	String numero_guce = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 10).getValue();
        	if (numero_guce != null) {
        		pubblicazione.setNUMEROGUCE(numero_guce);
        		esistonoDatiPubblicazione = true;
        	}

        	// Numero GURI
        	String numero_guri = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 11).getValue();
        	if (numero_guri != null) {
        		pubblicazione.setNUMEROGURI(numero_guri);
        		esistonoDatiPubblicazione = true;
        	}

        	// Numero BORE
        	String numero_bore = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 12).getValue();
        	if (numero_bore != null) {
        		pubblicazione.setNUMEROBORE(numero_bore);
        		esistonoDatiPubblicazione = true;
        	}

        	// Link sito
        	String link_sito = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 13).getValue();
        	if (link_sito != null) {
        		pubblicazione.setLINKSITO(link_sito);
        		esistonoDatiPubblicazione = true;
        	}

        	// FLAG_BENICULT
        	JdbcParametro flag_benicult = SqlManager.getValueFromVectorParam(datiW3GARA, 14);
        	if (flag_benicult.getValue() != null && !"".equals(flag_benicult.getStringValue())) {
        		if ("1".equals(flag_benicult.getStringValue())) {
        			pubblicazione.setFLAGBENICULT(FlagSNType.S);
        		} else {
        			pubblicazione.setFLAGBENICULT(FlagSNType.N);
        		}
        		esistonoDatiPubblicazione = true;
        	} else {
        		pubblicazione.setFLAGBENICULT(FlagSNType.N);
        	}

        	// FLAG_SOSPESO
        	JdbcParametro flag_sospeso = SqlManager.getValueFromVectorParam(datiW3GARA, 15);
        	if (flag_sospeso.getValue() != null && !"".equals(flag_sospeso.getStringValue())) {
        		if ("1".equals(flag_sospeso.getStringValue())) {
        			pubblicazione.setFLAGSOSPESO(FlagSNType.S);
        		} else {
        			pubblicazione.setFLAGSOSPESO(FlagSNType.N);
        		}
        		esistonoDatiPubblicazione = true;
        	}

    	}

    	if (esistonoDatiPubblicazione) {

        // Controllo degli errori di validazione XML
        ArrayList<Object> validationErrors = new ArrayList<Object>();
        XmlOptions validationOptions = new XmlOptions();
        validationOptions.setErrorListener(validationErrors);
        boolean isValid = pubblicazione.validate(validationOptions);

        if (!isValid) {
          String listaErroriValidazione = "";
          Iterator<?> iter = validationErrors.iterator();
          while (iter.hasNext()) {
            listaErroriValidazione += iter.next() + "\n";
          }
          logger.error("Il formato dei dati di pubblicazione non rispetta il formato previsto: "
              + pubblicazione.toString()
              + "\n"
              + listaErroriValidazione);
          throw new GestoreException("Il formato dei dati di pubblicazione non rispetta il formato previsto: " + listaErroriValidazione,
              "gestioneIDGARACIG.validate", new Object[] { listaErroriValidazione }, null);
        }
      } else {
    	  datiPubblicazione = null;
      }

    } catch (SQLException e) {
      throw new GestoreException("Errore generico nella creazione del contenuto XML per la pubblicazione della gara e dei lotti",
          "gestioneIDGARACIG.error", e);
    } 

    if (logger.isDebugEnabled()) logger.debug("getDatiPubblicazione: fine metodo");

    return datiPubblicazione;

  }

  /**
   * Restituisce i dati anagrafici della gara in formato XML per l'invio delle
   * richieste a SIMOG
   * 
   * @param numgara
   * @param ufficio_id
   * @param ufficio_denominazione
   * @param azienda_codicefiscale
   * @param azienda_denominazione
   * @return
   * @throws GestoreException
   */
  public GaraType getDatiGara(Long numgara, String ufficio_id, String ufficio_denominazione, String azienda_codicefiscale,
	      String azienda_denominazione) throws GestoreException {

	    if (logger.isDebugEnabled()) logger.debug("getDatiGara: inizio metodo");
	    boolean v3_04_6_Attiva = false;
	    GaraType datiGara;
	    try {
	    	datiGara = GaraType.Factory.newInstance();
	    	
	        // Lettura dei dati e composizione del contenuto XML
	        String selectW3GARA = "select oggetto, " // 0
	          + "tipo_scheda, " 		// 1
	          + "modo_indizione, " 		// 2
	          + "modo_realizzazione, " 	// 3
	          + "importo_gara, " 		// 4
	          + "rup_codtec, " 			// 5
	          + "cig_acc_quadro, " 		// 6
	          + "escluso_avcpass, " 	// 7
	          + "m_rich_cig, " 			// 8
	          + "m_rich_cig_comuni, " 	// 9
	          + "numero_lotti, " 		// 10
	          + "ALLEGATO_IX, " 		// 11
	          + "STRUMENTO_SVOLGIMENTO, " // 12
	          + "ESTREMA_URGENZA, " 	// 13
	          + "URGENZA_DL133, " 		// 14
	          + "DURATA_ACCQUADRO, " 	// 15
	          + "VER_SIMOG, " 			// 16
	          + "FLAG_SA_AGENTE_GARA, " // 17
	          + "ID_F_DELEGATE, " 		// 18
	          + "CF_AMM_AGENTE_GARA, " 	// 19
	          + "DEN_AMM_AGENTE_GARA " 	// 20
	          + "from w3gara where numgara = ?";
			
	      List<?> datiW3GARA = this.sqlManager.getVector(selectW3GARA, new Object[] { numgara });

	      if (datiW3GARA != null && datiW3GARA.size() > 0) {

	    	  Long versioneSimog = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 16).getValue();
	    	  v3_04_6_Attiva = UtilitySITAT.v3_04_6_Attiva(sqlManager, numgara);
	          
	        // Oggetto delle gara
	        String oggetto = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 0).getValue();
	        if (oggetto != null) datiGara.setOGGETTO(oggetto);

	        // Set dei valori di collaborazione ricavati dall'autenticazione
	        // ID_STAZIONE_APPALTANTE <--> UFFICIO_ID
	        // DENOM_STAZIONE_APPALTANTE <--> UFFICIO_DENOMINAZIONE
	        // CF_AMMINISTRAZIONE <--> AZIENDA_CODICE_FISCALE
	        // DENOM_AMMINISTRAZIONE <--> AZIENDA_DENOMINAZIONE
	        datiGara.setIDSTAZIONEAPPALTANTE(ufficio_id);
	        datiGara.setDENOMSTAZIONEAPPALTANTE(ufficio_denominazione);
	        datiGara.setCFAMMINISTRAZIONE(azienda_codicefiscale);
	        datiGara.setDENOMAMMINISTRAZIONE(azienda_denominazione);
	        
	        // Tipo di settore (ordinario o speciale)
	        String tipo_scheda = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 1).getValue();
	        if (tipo_scheda != null) datiGara.setTIPOSCHEDA(FlagSOType.Enum.forString(tipo_scheda));

	        // Modalità di indizione
	        if (tipo_scheda != null && "S".equals(tipo_scheda)) {
	          Long modo_indizione = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 2).getValue();
	          if (modo_indizione != null) datiGara.setMODOINDIZIONE(modo_indizione.toString());
	        }

	        // Modalità di realizzazione (contratto d'appalto, contratto di
	        // concessione lavori...)
	        Long modo_realizzazione = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 3).getValue();
	        if (modo_realizzazione != null) datiGara.setMODOREALIZZAZIONE(modo_realizzazione.toString());

	        // Importo della gara
	        Object importoObj = sqlManager.getObject("select SUM(" + sqlManager.getDBFunction("isnull", new String[] {"IMPORTO_LOTTO", "0.00" }) + ") from W3LOTT where NUMGARA=? and STATO_SIMOG in (1,2,3,4,7,99)", 
	            new Object[] { numgara });

	        Double importo = new Double(0);
	        if (importoObj != null) {
	              if (!(importoObj instanceof Double)) {
	                importo = new Double(importoObj.toString());
	              } else {
	                importo = (Double) importoObj;
	              }
	            } 
	        if (importoObj != null) {
	          datiGara.setIMPORTOGARA(new BigDecimal(importo.toString()));
	        } else {
	          datiGara.setIMPORTOGARA(new BigDecimal("-1"));
	        }

	        // Codice fiscale del RUP
	        String rup_codtec = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 5).getValue();
	        if (rup_codtec != null) {
	          String cftec = (String) sqlManager.getObject("select cftec from tecni where codtec = ?", new Object[] { rup_codtec });
	          if (cftec != null) datiGara.setCFUTENTE(cftec);
	        }

	        // Eventuale riferimento ad un CIG di una accordo quadro
	        String cig_acc_quadro = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 6).getValue();
	        if (cig_acc_quadro != null) datiGara.setCIGACCQUADRO(cig_acc_quadro);

	        // Escluso AVCPASS
	        String escluso_avcpass = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 7).getValue();
	        if (escluso_avcpass != null) {
	          if ("1".equals(escluso_avcpass)) {
	            datiGara.setESCLUSOAVCPASS(FlagSNType.S);
	          } else {
	            datiGara.setESCLUSOAVCPASS(FlagSNType.N);
	          }
	        }
	        
	        //Long numero_lotti = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 10).getValue();
	        Long numero_lotti = (Long) this.sqlManager.getObject("select count(*) from w3lott where numgara = ? and stato_simog in (1,2,3,4,7,99)", new Object[] { numgara });
	        datiGara.setNUMEROLOTTI(1);
	        if (numero_lotti != null && !numero_lotti.equals(new Long(0))) {
	        	datiGara.setNUMEROLOTTI(numero_lotti.intValue());
	        }
	        // Motivo della richiesta CIG
	        Long m_rich_cig = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 8).getValue();
	        if (m_rich_cig != null) {
	          datiGara.setMOTIVORICHCIG(m_rich_cig.toString());
	        }

	        // Motivo della richiesta CIG per i comuni
	        /*Long m_rich_cig_comuni = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 9).getValue();
	        if (m_rich_cig_comuni != null) {
	          datiGara.setMOTIVORICHCIGCOMUNI(m_rich_cig_comuni.toString());
	        }*/
	        
	        // Modalità di indizione servizi di cui all’allegato IX
	        Long allegato9 = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 11).getValue();
	        if (allegato9 != null) {
	          datiGara.setALLEGATOIX(allegato9.toString());
	        }
	        
	        // Strumenti per lo svolgimento delle procedure
	        Long strumentoSvolgimento = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 12).getValue();
	        if (strumentoSvolgimento != null) {
	          datiGara.setSTRUMENTOSVOLGIMENTO(strumentoSvolgimento.toString());
	        }
	        
	        // Deroghe ai sensi dell’articolo
	        Long estremaUrgenza = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 13).getValue();
	        if (estremaUrgenza != null) {
	          datiGara.setESTREMAURGENZA(estremaUrgenza.toString());
	        }
	        
	        // Informazione ‘Estrema urgenza’ o ‘Esecuzione di lavori di somma urgenza’
	        String flagUrgenza = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 14).getValue();
	        if (flagUrgenza != null) {
	          if ("1".equals(flagUrgenza)) {
	            datiGara.setURGENZADL133(FlagSNType.S);
	          } else {
	            datiGara.setURGENZADL133(FlagSNType.N);
	          }
	        }
	        //Durata della convenzione o accordo quadro in giorni
	        if (!v3_04_6_Attiva) {
	        	Long durataAccQuadro = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 15).getValue();
		        if (durataAccQuadro != null) {
		        	datiGara.setDURATAACCQUADROCONVENZIONEGARA(durataAccQuadro.intValue());
		        }
	        }
	        
	        // Categorie merceologiche
	        List<?> datiW3GAREMERC = this.sqlManager.getListVector("select categoria from w3garamerc where numgara = ? and categoria is not null",
	            new Object[] { numgara });
	        if (datiW3GAREMERC != null && datiW3GAREMERC.size() > 0) {
	        	ElencoCategMercType categorieMerceologiche = datiGara.addNewCATEGORIEMERC();
	        	/*String[] categorieMerceologiche = new String[datiW3GAREMERC.size()];
	        	for (int m = 0; m < datiW3GAREMERC.size(); m++) {
	        		Long categoria = (Long) SqlManager.getValueFromVectorParam(datiW3GAREMERC.get(m), 0).getValue();
	        		categorieMerceologiche[m] = categoria.toString();
	        	}
	        	datiGara.setCATEGORIEMERC(categorieMerceologiche);*/
	        	for (int m = 0; m < datiW3GAREMERC.size(); m++) {
	        		Long categoria = (Long) SqlManager.getValueFromVectorParam(datiW3GAREMERC.get(m), 0).getValue();
	        		categorieMerceologiche.addCATEGORIA(categoria.toString());
	        	}
	        }
	        
	        //Se la versione simog della gara è 3.04.4
	        if (versioneSimog.equals(new Long(4))) {
	        	String flagDelega = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 17).getValue();
		        if (flagDelega != null) {
		          if ("1".equals(flagDelega)) {
		            datiGara.setFLAGSAAGENTEGARA(FlagSNType.S);
		          } else {
		            datiGara.setFLAGSAAGENTEGARA(FlagSNType.N);
		          }
		        }
		        Long funzioneDelegata = (Long) SqlManager.getValueFromVectorParam(datiW3GARA, 18).getValue();
		        if (funzioneDelegata != null) {
		        	datiGara.setIDFDELEGATE(funzioneDelegata.toString());
		        }
		        String cfDelegante = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 19).getValue();
		        if (cfDelegante != null) {
		        	datiGara.setCFAMMAGENTEGARA(cfDelegante);
		        }
		        String denominazioneDelegante = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 20).getValue();
		        if (denominazioneDelegante != null) {
		        	datiGara.setDENAMMAGENTEGARA(denominazioneDelegante);
		        }
	        }
	      }
	      
	      // Controllo degli errori di validazione XML
	      ArrayList<Object> validationErrors = new ArrayList<Object>();
	      XmlOptions validationOptions = new XmlOptions();
	      validationOptions.setErrorListener(validationErrors);
	      boolean isValid = datiGara.validate(validationOptions);

	      if (!isValid) {
	        String listaErroriValidazione = "";
	        Iterator<?> iter = validationErrors.iterator();
	        while (iter.hasNext()) {
	          listaErroriValidazione += iter.next() + "\n";
	        }
	        logger.error("La richiesta di generazione degli identificativi di gara non rispetta il formato previsto: "
	            + datiGara.toString()
	            + "\n"
	            + listaErroriValidazione);
	        throw new GestoreException("La richiesta di generazione degli identificativi di gara non rispetta il formato previsto: "
	            + listaErroriValidazione, "gestioneIDGARACIG.validate", new Object[] { listaErroriValidazione }, null);
	      }

	    } catch (SQLException e) {
	      throw new GestoreException(
	          "Errore generico nella creazione del contenuto XML per la richiesta di generazione degli identificativi di gara",
	          "gestioneIDGARACIG.error", e);
	    } 

	    if (logger.isDebugEnabled()) logger.debug("getDatiGara: fine metodo");

	    return datiGara;

	  }
  
  /**
   * Restituisce i dati di richiesta SmartCig in formato XML per l'invio delle
   * richieste a SIMOG
   * 
   * @param numgara
   * @param index
   * @param ticket
   * @return
   * @throws GestoreException
   */
  public ComunicaSingolaRequest getComunicazioneSmartCig(Long numgara, String index, String ticket) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("getComunicazioneSmartCig: inizio metodo");

    ComunicaSingolaRequest comunicazioneRequest = null;
    try {

      // Lettura dei dati W3SMARTCIG
      String selectW3SMARTCIG = "select FATTISPECIE, " // 0
          + "IMPORTO, " // 1
          + "OGGETTO, " // 2
          + "ID_SCELTA_CONTRAENTE, " // 3
          + "TIPO_CONTRATTO, " // 4
          + "CIG_ACC_QUADRO, " // 5
          + "CUP, " // 6
          + "M_RICH_CIG_COMUNI, " // 7
          + "M_RICH_CIG, " // 8
          + "COLLABORAZIONE, " // 9
          + "CIG " // 10
          + "from W3SMARTCIG where CODRICH = ?";

      List<?> datiW3SMARTCIG = this.sqlManager.getVector(selectW3SMARTCIG, new Object[] { numgara });

      if (datiW3SMARTCIG != null && datiW3SMARTCIG.size() > 0) {
    	  comunicazioneRequest = new ComunicaSingolaRequest();

    	  ComunicazioneInputType comunicazione = new ComunicazioneInputType();
    	  String cig = (String)SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 10).getValue();
    	  comunicazione.setCig((cig==null)?"":cig);
    	  // Codice Fattispecie contrattuale
    	  String codiceFattispecie = (String)SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 0).getValue();
    	  if (codiceFattispecie != null) {
    		  comunicazione.setCodiceFattispecieContrattuale(codiceFattispecie);
    	  } else {
    		  comunicazione.setCodiceFattispecieContrattuale("");
    	  }
    	  // Importo
    	  Double importo = (Double)SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 1).getValue();
    	  if (importo != null) {
    		  comunicazione.setImporto(new BigDecimal(importo));
    		  comunicazione.setImporto(comunicazione.getImporto().setScale(2, BigDecimal.ROUND_CEILING));
    	  }
    	  // Oggetto delle gara
    	  String oggetto = (String) SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 2).getValue();
    	  if (oggetto != null) comunicazione.setOggetto(oggetto);
    	  // Scelta Contraente
    	  String sceltaContraente = (String)SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 3).getValue();
    	  if (sceltaContraente != null) {comunicazione.setCodiceProceduraSceltaContraente(sceltaContraente);}
    	  else {
    		  comunicazione.setCodiceProceduraSceltaContraente("");
    	  }
    	  // Classificazione gara
    	  String classificazioneGara = (String) SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 4).getValue();
    	  if (classificazioneGara != null) comunicazione.setCodiceClassificazioneGara(classificazioneGara);
    	  // Cig Accordo quadro
    	  String cigAccordoQuadro = (String) SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 5).getValue();
    	  if (cigAccordoQuadro != null) {comunicazione.setCigAccordoQuadro(cigAccordoQuadro);}
    	  else {
    		  comunicazione.setCigAccordoQuadro("");
    	  }
    	  // CUP
    	  String cup = (String) SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 6).getValue();
    	  if (cup != null) {
    		  comunicazione.setCup(cup);
    	  } else {
    		  comunicazione.setCup("");
    	  }
    	  // Motivi Comuni
    	  String motivoRich = (String)SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 7).getValue();
    	  if (motivoRich != null) {comunicazione.setMotivo_rich_cig_comuni(motivoRich);}
    	  else {
    		  comunicazione.setMotivo_rich_cig_comuni(""); 
    	  }
    	  // Tipo indicatore
    	  String motivoRichCat = (String)SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 8).getValue();
    	  if (motivoRichCat != null) {comunicazione.setMotivo_rich_cig_catmerc(motivoRichCat);}
    	  else {
    		  comunicazione.setMotivo_rich_cig_catmerc("");
    	  }
    	  // Categorie merceologiche
    	  List<?> datiW3SMARTCIGMERC = this.sqlManager.getListVector("select CATEGORIA from W3SMARTCIGMERC where CODRICH = ? and CATEGORIA is not null",
            new Object[] { numgara });
    	  if (datiW3SMARTCIGMERC != null && datiW3SMARTCIGMERC.size() > 0) {
    		  String[] categorie_merc = new String[datiW3SMARTCIGMERC.size()];
    		  for (int m = 0; m < datiW3SMARTCIGMERC.size(); m++) {
    			  Long categoria = (Long) SqlManager.getValueFromVectorParam(datiW3SMARTCIGMERC.get(m), 0).getValue();
    			  categorie_merc[m] = categoria.toString();
    		  }
    		  comunicazione.setCategorie_merc(categorie_merc);
    	  }
    	  comunicazioneRequest.setComunicazione(comunicazione);
    	  
    	  LoggedUserInfoType user = new LoggedUserInfoType();
    	  user.setIndex(index);
    	  user.setTicket(ticket);
    	  
    	  comunicazioneRequest.setUser(user);
      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore generico nella creazione della Request per la richiesta di generazione SMARTCIG",
          "gestioneIDGARACIG.error", e);
    } 

    if (logger.isDebugEnabled()) logger.debug("getComunicazioneSmartCig: fine metodo");

    return comunicazioneRequest;

  }

  /**
   * Restituisce i dati di richiesta di annullamento SmartCig in formato XML
   * 
   * @param numgara
   * @param index
   * @param ticket
   * @return
   * @throws GestoreException
   */
  public AnnullaComunicazioneRequest getAnnullaComunicazioneSmartCig(Long numgara, String index, String ticket) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("getAnnullaComunicazioneSmartCig: inizio metodo");

    AnnullaComunicazioneRequest comunicazioneRequest = null;
    try {

      // Lettura dei dati W3SMARTCIG
      String selectW3SMARTCIG = "select CIG from W3SMARTCIG where CODRICH = ?";

      List<?> datiW3SMARTCIG = this.sqlManager.getVector(selectW3SMARTCIG, new Object[] { numgara });

      if (datiW3SMARTCIG != null && datiW3SMARTCIG.size() > 0) {
    	  comunicazioneRequest = new AnnullaComunicazioneRequest();
    	  // Codice Cig
    	  String cig = (String)SqlManager.getValueFromVectorParam(datiW3SMARTCIG, 0).getValue();
    	  comunicazioneRequest.setCig(cig);
    	  
    	  LoggedUserInfoType user = new LoggedUserInfoType();
    	  user.setIndex(index);
    	  user.setTicket(ticket);
    	  
    	  comunicazioneRequest.setUser(user);
      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore generico nella creazione della Request per la richiesta di annullamento SMARTCIG",
          "gestioneIDGARACIG.error", e);
    } 

    if (logger.isDebugEnabled()) logger.debug("getAnnullaComunicazioneSmartCig: fine metodo");

    return comunicazioneRequest;

  }  
  /**
   * Restituisce i dati anagrafici della gara in formato XML per l'invio delle
   * richieste a SIMOG
   * 
   * @param numgara
   * @param ufficio_id
   * @param ufficio_denominazione
   * @param azienda_codicefiscale
   * @param azienda_denominazione
   * @return
   * @throws GestoreException
   */
  public Requisiti getDatiRequisiti(Long numgara) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("getDatiRequisiti: inizio metodo");
    Requisiti datiRequisiti = Requisiti.Factory.newInstance();
    try {
      String selectW3GARAREQ = null;

      // Lettura dei dati e composizione del contenuto XML
      selectW3GARAREQ = "select numgara, " // 0
          + "numreq, " // 1
          + "codice_dettaglio, " // 2
          + "descrizione, " // 3
          + "valore, " // 4
          + "flag_esclusione, " // 5
          + "flag_comprova_offerta, " // 6
          + "flag_avvalimento, " // 7
          + "flag_bando_tipo, " // 8
          + "flag_riservatezza " // 9
          + "from w3garareq where numgara = ?";

      List<?> datiW3GARAREQ = this.sqlManager.getListVector(selectW3GARAREQ, new Object[] { numgara });

      if (datiW3GARAREQ != null && datiW3GARAREQ.size() > 0) {

        for (int i = 0; i < datiW3GARAREQ.size(); i++) {
        	ReqGaraType datiRequisito = datiRequisiti.addNewRequisito();
          // Numero del requisito
          Long numreq = (Long) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 1).getValue();
          // Codice dettaglio del requisito
          String codiceDettaglio = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 2).getValue();
          codiceDettaglio = UtilityStringhe.convertiNullInStringaVuota(codiceDettaglio);
          if (codiceDettaglio != null) datiRequisito.setCodiceDettaglio(codiceDettaglio);
          // Descrizione del requisito
          String descrizione = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 3).getValue();
          descrizione = UtilityStringhe.convertiNullInStringaVuota(descrizione);
          if ("999".equals(codiceDettaglio)) {
            descrizione = "";
          }
          if (descrizione != null) datiRequisito.setDescrizione(descrizione);
          // Valore del requisito
          String valore = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 4).getValue();
          if (valore != null) {
        	  datiRequisito.setValore(valore);
          } else {
        	  datiRequisito.setValore("");
          }
          // flag esclusione del requisito
          String flagEsclusione = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 5).getValue();
          if ("1".equals(flagEsclusione)) {
            datiRequisito.setFlagEsclusione(FlagSNType.S);
          } else {
            datiRequisito.setFlagEsclusione(FlagSNType.N);
          }
          // flag comprova offerta del requisito
          String flagComprovaOfferta = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 6).getValue();
          if ("1".equals(flagComprovaOfferta)) {
            datiRequisito.setFlagComprovaOfferta(FlagSNType.S);
          } else {
            datiRequisito.setFlagComprovaOfferta(FlagSNType.N);
          }
          // flag avvalimento del requisito
          String flagAvvalimento = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 7).getValue();
          if ("1".equals(flagAvvalimento)) {
            datiRequisito.setFlagAvvalimento(FlagSNType.S);
          } else {
            datiRequisito.setFlagAvvalimento(FlagSNType.N);
          }
          // flag bando tipo del requisito
          String flagBandoTipo = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 8).getValue();
          if ("1".equals(flagBandoTipo)) {
            datiRequisito.setFlagBandoTipo(FlagSNType.S);
          } else {
            datiRequisito.setFlagBandoTipo(FlagSNType.N);
          }
          // flag riservatezza del requisito
          String flagRiservatezza = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQ.get(i), 9).getValue();
          if ("1".equals(flagRiservatezza)) {
            datiRequisito.setFlagRiservatezza(FlagSNType.S);
          } else {
            datiRequisito.setFlagRiservatezza(FlagSNType.N);
          }

          String selectW3GARAREQCIG = "select cig from w3garareqcig where numgara = ? and numreq = ?";

          List<?> datiW3GARAREQCIG = sqlManager.getListVector(selectW3GARAREQCIG, new Object[] { numgara, numreq });
          if (datiW3GARAREQCIG != null && datiW3GARAREQCIG.size() > 0) {
            String[] reqCigType = new String[datiW3GARAREQCIG.size()];
            for (int j = 0; j < datiW3GARAREQCIG.size(); j++) {
              // CigType datiCigRequisito =
              // requisitiWSDocument.getRequisitiWS().getRequisitoArray(i).addNewCIG();
              // CIG
              String cig = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQCIG.get(j), 0).getValue();
              if (cig != null) {
                reqCigType[j] = cig;
              }

            }
            datiRequisito.setCIGArray(reqCigType);
          }

          String selectW3GARAREQDOC = "select codice_tipo_doc, " // 0
              + "descrizione, " // 1
              + "emettitore, " // 2
              + "fax, " // 3
              + "telefono, " // 4
              + "mail, " // 5
              + "mail_pec " // 6
              + "from w3garareqdoc where numgara = ? and numreq = ?";

          List<?> datiW3GARAREQDOC = sqlManager.getListVector(selectW3GARAREQDOC, new Object[] { numgara, numreq });
          if (datiW3GARAREQDOC != null && datiW3GARAREQDOC.size() > 0) {
            ReqDocType[] reqDocType = new ReqDocType[datiW3GARAREQDOC.size()];
            for (int j = 0; j < datiW3GARAREQDOC.size(); j++) {
            	ReqDocType datiDocRequisito = datiRequisito.addNewDOCUMENTO();
              // Codice Tipo Doc
              Long codice_tipo_doc = (Long) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 0).getValue();
              if (codice_tipo_doc != null) {
                String codiceTipoDoc = codice_tipo_doc.toString();
                datiDocRequisito.setCodiceTipoDoc(codiceTipoDoc);
              }
              // Descrizione
              String descrDoc = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 1).getValue();
              descrDoc = UtilityStringhe.convertiNullInStringaVuota(descrDoc);
              if (new Long(999).equals(codice_tipo_doc)) {
                descrDoc = "";
              }
              if (descrDoc != null) {
                datiDocRequisito.setDescrizioneDocumento(descrDoc);
              }
              // Emettitore
              String emettitore = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 2).getValue();
              if (emettitore != null) {
                datiDocRequisito.setEmettitore(emettitore);
              }
              // Fax
              String fax = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 3).getValue();
              if (fax != null) {
                datiDocRequisito.setFax(fax);
              }

              // Telefono
              String telefono = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 4).getValue();
              if (telefono != null) {
                //NumTelType numTelType = null;
                datiDocRequisito.setTelefono(telefono);
              }
              // Mail
              String mail = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 5).getValue();
              if (mail != null) {
                datiDocRequisito.setMail(mail);
              }
              // Mail Pec
              String mailPec = (String) SqlManager.getValueFromVectorParam(datiW3GARAREQDOC.get(j), 6).getValue();
              if (mailPec != null) {
                datiDocRequisito.setMailPec(mailPec);
              }

              reqDocType[j] = datiDocRequisito;

            }// for doc
            datiRequisito.setDOCUMENTOArray(reqDocType);
          }


        }// for Req

      }

      // Controllo degli errori di validazione XML
      ArrayList<Object> validationErrors = new ArrayList<Object>();
      XmlOptions validationOptions = new XmlOptions();
      validationOptions.setErrorListener(validationErrors);
      boolean isValid = datiRequisiti.validate(validationOptions);

      if (!isValid) {
        String listaErroriValidazione = "";
        Iterator<?> iter = validationErrors.iterator();
        while (iter.hasNext()) {
          listaErroriValidazione += iter.next() + "\n";
        }
        logger.error("La richiesta di generazione dei requisiti di gara non rispetta il formato previsto: "
            + datiRequisiti.toString()
            + "\n"
            + listaErroriValidazione);
        throw new GestoreException("La richiesta di generazione degli identificativi di gara non rispetta il formato previsto: "
            + listaErroriValidazione, "gestioneIDGARACIG.validate", new Object[] { listaErroriValidazione }, null);
      }

    } catch (SQLException e) {
      throw new GestoreException("Errore generico nella creazione del contenuto XML per la richiesta di generazione dei requisiti di gara",
          "gestioneIDGARACIG.error", e);
    }

    if (logger.isDebugEnabled()) logger.debug("getDatiGaraXML: fine metodo");

    return datiRequisiti;

  }

  /**
   * Restituisce i dati anagrafici del lotto in formato XML per l'invio delle
   * richieste a SIMOG
   * 
   * @param numgara
   * @param numlott
   * @return
   * @throws GestoreException
   */
  public LottoType getDatiLotto(Long numgara, Long numlott) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("getDatiLotto: inizio metodo");
    LottoType datiLotto = LottoType.Factory.newInstance();
    boolean v3_04_6_Attiva = false;
    try {

      // Lettura dei dati e composizione del contenuto XML
      String selectW3LOTT = "select oggetto, " // 0
          + "somma_urgenza, " // 1
          + "tipo_contratto, " // 2
          + "flag_escluso, " // 3
          + "cpv, " // 4
          + "id_scelta_contraente, " // 5
          + "importo_lotto, " // 6
          + "importo_attuazione_sicurezza, " // 7
          + "importo_sa, " // 8
          + "importo_impresa, " // 9
          + "id_categoria_prevalente, " // 10
          + "luogo_istat, " // 11
          + "luogo_nuts, " // 12
          + "triennio_anno_inizio, " // 13
          + "triennio_anno_fine, " // 14
          + "triennio_progressivo, " // 15
          + "id_esclusione, " // 16
          + "flag_prevede_rip, " // 17
          + "MOTIVO_COLLEGAMENTO, " // 18
          + "cig_origine_rip, " // 19
          + "dscad_richiesta_invito, " // 20
          + "data_lettera_invito, " // 21
          + "flag_cup, " // 22
          + "annuale_cui_mininf, " // 23
          + "ID_AFF_RISERVATI, " // 24
          + "FLAG_REGIME, " // 25
          + "ART_REGIME, " // 26
          + "FLAG_DL50, " // 27
          + "PRIMA_ANNUALITA, " // 28
          + "CATEGORIA_MERC, " // 29
          + "DATA_CREAZIONE_LOTTO, " // 30
          + "IMPORTO_OPZIONI, " // 31
          + "DURATA_AFFIDAMENTO, " // 32
          + "DURATA_RINNOVI " // 33
          + "from w3lott where numgara = ? and numlott = ?";

      List<?> datiW3LOTT = this.sqlManager.getVector(selectW3LOTT, new Object[] { numgara, numlott });

      if (datiW3LOTT != null && datiW3LOTT.size() > 0) {
    	  Date dataCreazioneLotto = (Date) SqlManager.getValueFromVectorParam(datiW3LOTT, 30).getValue();
    	  SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
      	  String dateInString = "22-10-2019";
          Date dataVer3_04_3 = sdf.parse(dateInString);
          v3_04_6_Attiva = UtilitySITAT.v3_04_6_Attiva(sqlManager, numgara);
          
          boolean verPre3_04_3 = false;
          if (dataCreazioneLotto != null && dataVer3_04_3.compareTo(dataCreazioneLotto) > 0) {
        	  verPre3_04_3 = true;
          }
        // Oggetto delle gara
        String oggetto = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 0).getValue();
        if (oggetto != null) datiLotto.setOGGETTO(oggetto);

        // Somma urgenza ?
        String somma_urgenza = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 1).getValue();
        if (somma_urgenza != null) {
          if ("1".equals(somma_urgenza)) {
            datiLotto.setSOMMAURGENZA(FlagSNType.S);
          } else if ("2".equals(somma_urgenza)) {
            datiLotto.setSOMMAURGENZA(FlagSNType.N);
          }
        } else {
        	datiLotto.setSOMMAURGENZA(FlagSNType.N);
        }

        // Tipo contratto
        String tipo_contratto = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 2).getValue();
        if (tipo_contratto != null) datiLotto.setTIPOCONTRATTO(tipo_contratto);

        // Contratto escluso ?
        String flag_escluso = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 3).getValue();
        if (flag_escluso != null) {
          if ("1".equals(flag_escluso)) {
            datiLotto.setFLAGESCLUSO(FlagSNType.S.toString());

            Long id_esclusione = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 16).getValue();
            if (id_esclusione != null) {
              datiLotto.setIDESCLUSIONE(id_esclusione.toString());
            }

          } else if ("2".equals(flag_escluso)) {
            datiLotto.setFLAGESCLUSO(FlagSNType.N.toString());
          }
        }

        // CPV
        String cpv = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 4).getValue();
        if (cpv != null) datiLotto.setCPV(cpv);

        // Procedura di scelta del contraente
        Long id_scelta_contraente = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 5).getValue();
        if (id_scelta_contraente != null) {
        	datiLotto.setIDSCELTACONTRAENTE(id_scelta_contraente.toString());
        	if (id_scelta_contraente.equals(new Long(4)) || id_scelta_contraente.equals(new Long(10))) {
        		// Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara
                List<?> datiW3COND = this.sqlManager.getListVector("select ID_CONDIZIONE from W3COND where numgara = ? and numlott = ?",
                    new Object[] { numgara, numlott });
                if (datiW3COND != null && datiW3COND.size() > 0) {
                  for (int i = 0; i < datiW3COND.size(); i++) {
                    Long idCondizione = (Long) SqlManager.getValueFromVectorParam(datiW3COND.get(i), 0).getValue();
                    if (idCondizione != null) {
                    	CondizioneLtType condizione = datiLotto.addNewCondizioni();
                    	condizione.setIDCONDIZIONE(idCondizione.toString());
                    }
                  }
                }
        	}
        }

        //Tipo appalto riservato
        Long idAppaltoRiservato = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 24).getValue();
        if (idAppaltoRiservato != null) {
        	datiLotto.setIDAFFRISERVATI(idAppaltoRiservato.toString());
        }

        // Importo totale del lotto
        Double importo_lotto = (Double) SqlManager.getValueFromVectorParam(datiW3LOTT, 6).getValue();
        if (importo_lotto != null) {
          datiLotto.setIMPORTOLOTTO(new BigDecimal(importo_lotto.toString()));
        }

        // Importo totale per l'attuazione della sicurezza
        Double importo_attuazione_sicurezza = (Double) SqlManager.getValueFromVectorParam(datiW3LOTT, 7).getValue();
        if (importo_attuazione_sicurezza != null) {
          datiLotto.setIMPORTOATTUAZIONESICUREZZA(this.convertiImporto(importo_attuazione_sicurezza));
        }

        // Importo di cui per opzioni/ripetizioni
        Double importo_opzioni = (Double) SqlManager.getValueFromVectorParam(datiW3LOTT, 31).getValue();
        if (importo_opzioni != null) {
          datiLotto.setIMPORTOOPZIONI(new BigDecimal(importo_opzioni.toString()));
        }
        
        // Importo dovuto dalla stazione appaltante
        Double importo_sa = (Double) SqlManager.getValueFromVectorParam(datiW3LOTT, 8).getValue();
        if (importo_sa != null) {
          datiLotto.setIMPORTOSA(new BigDecimal(importo_sa.toString()));
        } else {
          datiLotto.setIMPORTOSA(new BigDecimal("0"));
        }

        // Importo dovuto dall'impresa
        Double importo_impresa = (Double) SqlManager.getValueFromVectorParam(datiW3LOTT, 9).getValue();
        if (importo_impresa != null) {
          datiLotto.setIMPORTOIMPRESA(new BigDecimal(importo_impresa.toString()));
        } else {
          datiLotto.setIMPORTOIMPRESA(new BigDecimal("0"));
        }

        // Contratti regimi particolari di appalto (speciale o alleggerito)
        String flag_regime = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 25).getValue();
        if (flag_regime != null) {
          if ("1".equals(flag_regime)) {
            datiLotto.setFLAGREGIME(FlagSNType.S);
          } else if ("2".equals(flag_regime)) {
            datiLotto.setFLAGREGIME(FlagSNType.N);
          }
        }
        
        //Art. regimi particolari di appalto
        String art_regime = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 26).getValue();
        if (art_regime != null) datiLotto.setARTREGIME(art_regime);

        
        // Categoria prevalente
        String id_categoria_prevalente = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 10).getValue();
        //String descCategoriaPrev = (String) sqlManager.getObject("select tab2d1 from tab2" + " where tab2cod = ? and tab2tip = ?",
        //    new Object[] { "W3z03", id_categoria_prevalente });
        String descCategoriaPrev = UtilitySITAT.getCategoriaSIMOG(this.sqlManager, id_categoria_prevalente);
        if (id_categoria_prevalente != null) datiLotto.setIDCATEGORIAPREVALENTE(descCategoriaPrev);

        // Ulteriori categorie
        List<?> datiW3LOTTCATE = this.sqlManager.getListVector("select categoria from w3lottcate where numgara = ? and numlott = ?",
            new Object[] { numgara, numlott });
        if (datiW3LOTTCATE != null && datiW3LOTTCATE.size() > 0) {
          datiLotto.addNewCATEGORIE();
          for (int i = 0; i < datiW3LOTTCATE.size(); i++) {
            String categoria = (String) SqlManager.getValueFromVectorParam(datiW3LOTTCATE.get(i), 0).getValue();
            String descCategoriaUlt = UtilitySITAT.getCategoriaSIMOG(this.sqlManager, categoria);
            //String descCategoriaUlt = (String) sqlManager.getObject("select tab2d1 from tab2" + " where tab2cod = ? and tab2tip = ?",
            //    new Object[] { "W3z03", categoria });
            if (categoria != null) {
              datiLotto.getCATEGORIE().addCATEGORIA(descCategoriaUlt);
            }
          }
        }

        // Luogo ISTAT
        String luogo_istat = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 11).getValue();
        if (luogo_istat != null) {
          luogo_istat = luogo_istat.substring(3);
          datiLotto.setLUOGOISTAT(luogo_istat);
        }

        // Luogo NUTS
        String luogo_nuts = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 12).getValue();
        if (luogo_nuts != null) datiLotto.setLUOGONUTS(luogo_nuts);

        // Anno iniziale del triennio
        Long triennio_anno_inizio = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 13).getValue();
        if (triennio_anno_inizio != null) datiLotto.setTRIENNIOANNOINIZIO(triennio_anno_inizio.intValue());

        // Anno finale del triennio
        Long triennio_anno_fine = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 14).getValue();
        if (triennio_anno_fine != null) datiLotto.setTRIENNIOANNOFINE(triennio_anno_fine.intValue());

        // Progressivo nell'ambito del triennio
        Long triennio_progressivo = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 15).getValue();
        if (triennio_progressivo != null) datiLotto.setTRIENNIOPROGRESSIVO(triennio_progressivo.intValue());

        // Il lavoro o l’acquisto di bene o servizio è stato previsto all’interno della programmazione
        String flagDL50 = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 27).getValue();
        if (flagDL50 != null) {
          if ("1".equals(flagDL50)) {
            datiLotto.setFLAGDL50(FlagSNType.S);
            //Prima annualità dell’ultimo programma nel quale è stato inserito l’intervento o l’acquisto
            Long primaAnnualita = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 28).getValue();
            if (primaAnnualita != null) {
            	datiLotto.setPRIMAANNUALITA(primaAnnualita.toString());
            }
            // Riferimento alla programmazione annuale
            String annuale_cui_mininf = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 23).getValue();
            if (annuale_cui_mininf != null) {
          	  datiLotto.setANNUALECUIMININF(annuale_cui_mininf);
            }
          } else if ("2".equals(flagDL50)) {
            datiLotto.setFLAGDL50(FlagSNType.N);
          }
        }
        
        // L'appalto prevede ripetizioni ?
        String flag_rip = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 17).getValue();
        if (flag_rip != null) {
          if ("1".equals(flag_rip)) {
            datiLotto.setFLAGPREVEDERIP(FlagSNType.S);
          } else if ("2".equals(flag_rip)) {
            datiLotto.setFLAGPREVEDERIP(FlagSNType.N);
          }
        }

        if (!verPre3_04_3) {
        	Long motivoCollegamento = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 18).getValue();
            if (motivoCollegamento != null) {
            	datiLotto.setIDMOTIVOCOLLCIG(motivoCollegamento.toString());
            }
        }
        
        // Il lotto e' ripetizione di un appalto precedente ?
        /*String flag_ripetizione = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 18).getValue();
        if (flag_ripetizione != null) {
          if ("1".equals(flag_ripetizione)) {
            datiLotto.setFLAGRIPETIZIONE(FlagSNType.S);
          } else if ("2".equals(flag_ripetizione)) {
            datiLotto.setFLAGRIPETIZIONE(FlagSNType.N);
          }
        }*/

        // CIG Appalto origine
        String cig_origine_rip = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 19).getValue();
        if (cig_origine_rip != null) {
          datiLotto.setCIGORIGINERIP(cig_origine_rip);
        }

        // Data scadenza per la presentazione delle richieste di invito nelle
        // procedure ristrette
        Date data_scadenza_richiesta_invito = (Date) SqlManager.getValueFromVectorParam(datiW3LOTT, 20).getValue();
        if (data_scadenza_richiesta_invito != null) {
          Calendar dataScadenzaRichiestaInvito = Calendar.getInstance();
          dataScadenzaRichiestaInvito.setTime(data_scadenza_richiesta_invito);
          datiLotto.setDATASCADENZARICHIESTAINVITO(
              UtilityDate.convertiData(dataScadenzaRichiestaInvito.getTime(),
                  UtilityDate.FORMATO_AAAAMMGG));
        }

        // Data invio lettere di invito nelle procedure ristrette
        Date data_lettera_invito = (Date) SqlManager.getValueFromVectorParam(datiW3LOTT, 21).getValue();
        if (data_lettera_invito != null) {
          Calendar dataLetteraInvito = Calendar.getInstance();
          dataLetteraInvito.setTime(data_lettera_invito);
          datiLotto.setDATALETTERAINVITO(dataLetteraInvito);
        }

        // E' obbligatoria l'indicazione del CUP ?
        String flag_cup = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 22).getValue();
        if (flag_cup != null) {
          if ("1".equals(flag_cup)) {
            datiLotto.setFLAGCUP(FlagSNType.S);
          } else if ("2".equals(flag_cup)) {
            datiLotto.setFLAGCUP(FlagSNType.N);
          }
        }

        if (tipo_contratto.equals("L")) {
        	String selectW3LOTTTIPI_L = "select numtipi, idappalto from w3lotttipi" + " where numgara = ? and numlott = ? and idappalto >= 6 ";
            List<?> datiW3LOTTTIPI_L = this.sqlManager.getListVector(selectW3LOTTTIPI_L, new Object[] { numgara, numlott });
            if (datiW3LOTTTIPI_L != null && datiW3LOTTTIPI_L.size() > 0) {
              for (int j = 0; j < datiW3LOTTTIPI_L.size(); j++) {
                datiLotto.addNewTipiAppaltoLav();
                //Long numtipi = (Long) SqlManager.getValueFromVectorParam(datiW3LOTTTIPI_L.get(j), 0).getValue();
                Long idappalto = (Long) SqlManager.getValueFromVectorParam(datiW3LOTTTIPI_L.get(j), 1).getValue();
                datiLotto.getTipiAppaltoLavArray(j).setIDAPPALTO(idappalto.toString());
              }
            }
        }
        
        if (tipo_contratto.equals("F")) {
        	String selectW3LOTTTIPI_F = "select numtipi, idappalto from w3lotttipi" + " where numgara = ? and numlott = ? and idappalto < 6 ";
            List<?> datiW3LOTTTIPI_F = this.sqlManager.getListVector(selectW3LOTTTIPI_F, new Object[] { numgara, numlott });
            if (datiW3LOTTTIPI_F != null && datiW3LOTTTIPI_F.size() > 0) {
              for (int j = 0; j < datiW3LOTTTIPI_F.size(); j++) {
                datiLotto.addNewTipiAppaltoForn();
                //Long numtipi = (Long) SqlManager.getValueFromVectorParam(datiW3LOTTTIPI_F.get(j), 0).getValue();
                Long idappalto = (Long) SqlManager.getValueFromVectorParam(datiW3LOTTTIPI_F.get(j), 1).getValue();
                datiLotto.getTipiAppaltoFornArray(j).setIDAPPALTO(idappalto.toString());
              }
            }
        }
        

        // CUP Lotti
        if (FlagSNType.S.equals(datiLotto.getFLAGCUP())) {
          String selectW3LOTTCUP = "select numcup, cup, dati_dipe from w3lottcup where numgara = ? and numlott = ? ";
          List<?> datiW3LOTTCUP = this.sqlManager.getListVector(selectW3LOTTCUP, new Object[] { numgara, numlott });
          if (datiW3LOTTCUP != null && datiW3LOTTCUP.size() > 0) {
            datiLotto.addNewCUPLOTTO();
            for (int j = 0; j < datiW3LOTTCUP.size(); j++) {
              //Long numcup = (Long) SqlManager.getValueFromVectorParam(datiW3LOTTCUP.get(j), 0).getValue();
              String cup = (String) SqlManager.getValueFromVectorParam(datiW3LOTTCUP.get(j), 1).getValue();
              datiLotto.getCUPLOTTO().addNewCODICICUP();
              DatiCUPType datiCup = datiLotto.getCUPLOTTO().getCODICICUPArray(j);
              datiCup.setCUP(cup);
              datiCup.setOKUTENTE(FlagSNType.S);
              datiLotto.getCUPLOTTO().setCODICICUPArray(j, datiCup);
            }
          }
        }
        //CPV secondarie
        String selectW3CPV = "select cpv from w3cpv where numgara = ? and numlott = ? order by num_cpv ";
        List<?> datiW3CPV = this.sqlManager.getListVector(selectW3CPV, new Object[] { numgara, numlott });
        if (datiW3CPV != null && datiW3CPV.size() > 0) {
          for (int j = 0; j < datiW3CPV.size(); j++) {
            datiLotto.addNewCPVSecondaria();
            String cpvSecondaria = (String) SqlManager.getValueFromVectorParam(datiW3CPV.get(j), 0).getValue();
            datiLotto.getCPVSecondariaArray(j).setCODCPVSECONDARIA(cpvSecondaria);
          }
        }
        
        //Categoria merceologica oggetto della fornitura di cui al DPCM soggetti aggregatori
        Long categoriaMerceologica = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 29).getValue();
        if (categoriaMerceologica != null) datiLotto.setCATEGORIAMERC(categoriaMerceologica.toString());
        
        //Durata dell'affidamento in giorni (al netto di rinnovi e ripetizioni)
        //Durata dei rinnovi e delle ripetizioni in giorni
        if (v3_04_6_Attiva) {
        	Long durataAffidamento = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 32).getValue();
            if (durataAffidamento != null) {
            	datiLotto.setDURATAAFFIDAMENTO(durataAffidamento.intValue());
            }
            Long durataRinnovi = (Long) SqlManager.getValueFromVectorParam(datiW3LOTT, 33).getValue();
            if (durataRinnovi != null) {
            	datiLotto.setDURATARINNOVI(durataRinnovi.intValue());
            }
        }
        
        //che questa stazione appaltante non è soggetta agli obblighi del DPCM 24 dicembre 2015 e ss.mm.ii.
        /*String flag_no_adesione = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 30).getValue();
        if (flag_no_adesione != null) {
          if ("1".equals(flag_no_adesione)) {
            datiLotto.setFLAGNOADESIONEINIZIATIVA(FlagSNType.S);
          } else if ("2".equals(flag_no_adesione)) {
            datiLotto.setFLAGNOADESIONEINIZIATIVA(FlagSNType.N);
          }
        }
        //che nessuna delle iniziative disponibili presso i soggetti aggregatori di riferimento ha caratteristiche in grado di soddisfare i fabbisogni di questa stazione appaltante
        String flag_non_classificata = (String) SqlManager.getValueFromVectorParam(datiW3LOTT, 31).getValue();
        if (flag_non_classificata != null) {
          if ("1".equals(flag_non_classificata)) {
            datiLotto.setFLAGSANONCLASSIFICATA(FlagSNType.S);
          } else if ("2".equals(flag_non_classificata)) {
            datiLotto.setFLAGSANONCLASSIFICATA(FlagSNType.N);
          }
        }*/
      }

      // Controllo degli errori di validazione XML
      ArrayList<Object> validationErrors = new ArrayList<Object>();
      XmlOptions validationOptions = new XmlOptions();
      validationOptions.setErrorListener(validationErrors);
      boolean isValid = datiLotto.validate(validationOptions);

      if (!isValid) {
        String listaErroriValidazione = "";
        Iterator<?> iter = validationErrors.iterator();
        while (iter.hasNext()) {
          listaErroriValidazione += iter.next() + "\n";
        }
        logger.error("La richiesta di generazione degli identificativi di gara non rispetta il formato previsto: "
            + datiLotto.toString()
            + "\n"
            + listaErroriValidazione);
        throw new GestoreException("La richiesta di generazione degli identificativi di gara non rispetta il formato previsto: "
            + listaErroriValidazione, "gestioneIDGARACIG.validate", new Object[] { listaErroriValidazione }, null);
      }

    } catch (Exception e) {
      throw new GestoreException(
          "Errore generico nella creazione del contenuto XML per la richiesta di generazione degli identificativi di gara",
          "gestioneIDGARACIG.error", e);
    } 

    if (logger.isDebugEnabled()) logger.debug("getDatiLottoXML: fine metodo");

    return datiLotto;

  }

  /**
   * Gestisce i dati provenienti dal WS di consultazione gara e se vi sono le
   * condizioni li memorizza in base dati
   * 
   * @param datiGaraXML
   * @throws GestoreException
   */
  public HashMap<String, Object> inserisciGaraLottodaSIMOG(String datiGaraLottoXML, String cig, Long syscon, String codUffInt, String codrup) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("setGaraLotto: inizio metodo");

    HashMap<String, Object> hMapConsultaGaraLotto = new HashMap<String, Object>();

    try {
    	SchedaType scheda = SchedaType.Factory.parse(datiGaraLottoXML);
      //SchedaDocument schedaDocument = SchedaDocument.Factory.parse(datiGaraLottoXML);

      // Controllo degli errori di validazione XML
      ArrayList<Object> validationErrors = new ArrayList<Object>();
      XmlOptions validationOptions = new XmlOptions();
      validationOptions.setErrorListener(validationErrors);
      boolean isValid = scheda.validate(validationOptions);

      if (false) {
        String listaErroriValidazione = "";
        Iterator<?> iter = validationErrors.iterator();
        while (iter.hasNext()) {
          listaErroriValidazione += iter.next() + "\n";
        }
        logger.error("La risposta alla richiesta di consultazione della gara non rispetta il formato previsto: "
            + datiGaraLottoXML
            + "\n"
            + listaErroriValidazione);
        throw new GestoreException("La risposta alla richiesta di consultazione della gara non rispetta il formato previsto",
            "gestioneIDGARACIG.consultagaralotto.validate", new Object[] { listaErroriValidazione }, null);
      } else {

        DatiGaraType datiGaraLotto = scheda.getDatiGara();
        GaraType datiGara = datiGaraLotto.getGara();
        LottoType datiLotto = datiGaraLotto.getLotto();
        ReqGaraType[] requisitiGara = datiGaraLotto.getRequisitoArray();
        // Controllo corrispondenza tra codice fiscale dell'utente connesso
        // e codice fiscale del RUP indicato nei dati restituiti
        /*String cftec = (String) sqlManager.getObject("select tecni.cftec from tecni, w3usrsys where "
            + "tecni.codtec = w3usrsys.rup_codtec and "
            + "w3usrsys.syscon = ?", new Object[] { syscon });*/
        String cftec = (String) sqlManager.getObject("select cftec from tecni where codtec = ? "
                , new Object[] { codrup });
        if (cftec != null) {
          if (!cftec.toUpperCase().equals(datiGara.getCFUTENTE().toUpperCase())) {
            throw new GestoreException(
                "Il lotto identificato dal CIG indicato e la gara associata appartengono ad un RUP differente da quello connesso. "
                    + "Non è possibile procedere con il recupero dei dati.", "gestioneIDGARACIG.consultagaralotto.rupdifferente");
          }
        }

        // Gestione dei dati di W3GARA.
        // Controllo di esistenza della gara, se la gara esiste gia'
        // non è necessario inserire nulla in W3GARA, è sufficiente
        // recuperare il NUMGARA per il successivo inserimento del lotto
        boolean esisteW3GARA = this.w3Manager.esisteW3GARA_IDGARA("" + datiGara.getIDGARA());
        Long numgara = null;

        if (!esisteW3GARA) {
          numgara = (Long) sqlManager.getObject("select max(numgara) from w3gara", new Object[] {});
          if (numgara == null) numgara = new Long(0);
          numgara = new Long(numgara.longValue() + 1);
        } else {
          numgara = this.w3Manager.getNUMGARA_IDGARA("" + datiGara.getIDGARA());
        }

        // Se non esiste la gara provvedo ad inserirla
        if (!esisteW3GARA) {
          this.inserisciW3GARAdaSIMOG(datiGara, numgara, syscon, codUffInt, codrup);
        }
        // Inserimento requisiti
        if (requisitiGara != null) {
        	this.inserisciW3GARAREQdaSIMOG(requisitiGara, numgara);
        }
        // Inserimento dei dati del lotto
        Long numlott = new Long(1);
        
        boolean esisteW3LOTT = this.w3Manager.esisteW3LOTT_CIG(cig);
        
        if (esisteW3LOTT) {
        	numlott = (Long) sqlManager.getObject("select max(numlott) from w3lott where numgara = ?", new Object[] { numgara });
        } else {
        	numlott = this.inserisciW3LOTTdaSIMOG(datiLotto, numgara, cig);
        }
        
        // HashMap di restituzione valori di controllo
        if (esisteW3GARA) {
          hMapConsultaGaraLotto.put("w3gara_esistente", "1");
        } else {
          hMapConsultaGaraLotto.put("w3gara_esistente", "2");
        }
        hMapConsultaGaraLotto.put("w3gara_numgara", numgara);
        hMapConsultaGaraLotto.put("w3gara_id_gara", "" + datiGara.getIDGARA());
        hMapConsultaGaraLotto.put("w3gara_oggetto", datiGara.getOGGETTO());
        hMapConsultaGaraLotto.put("w3lott_numlott", numlott);
        hMapConsultaGaraLotto.put("w3lott_cig", cig);
        hMapConsultaGaraLotto.put("w3lott_oggetto", datiLotto.getOGGETTO());
        hMapConsultaGaraLotto.put("w3gara_provv_presa_carico", datiGara.getPROVVPRESACARICO());
        hMapConsultaGaraLotto.put("w3gara_escluso_avcpass", datiGara.getESCLUSOAVCPASS());
      }

    } catch (XmlException e) {
      throw new GestoreException("Si e' verificato un errore durante la consultazione della gara",
          "gestioneIDGARACIG.consultagaralotto.error", e);
    } catch (SQLException e) {
      throw new GestoreException("Si e' verificato un errore durante la consultazione della gara",
          "gestioneIDGARACIG.consultagaralotto.error", e);
    }

    if (logger.isDebugEnabled()) logger.debug("setGaraLotto: fine metodo");

    return hMapConsultaGaraLotto;

  }

  /**
   * Gestisce i dati provenienti dal WS di consultazione SMARTCIG e se vi sono le
   * condizioni li memorizza in base dati
   * 
   * @param comunicazione
   * @param smartcig
   * @param syscon
   * @throws GestoreException
   */
  public HashMap<String, Object> inserisciSMARTCIGdaSIMOG(ComunicazioneType comunicazione, String smartcig, Long syscon, String codUffInt, String codrup, HashMap<String, Object> hMapwsLogin) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("inserisciSMARTCIGdaSIMOG: inizio metodo");

    HashMap<String, Object> hMapConsultaSmartCig = new HashMap<String, Object>();

    try {
        String[] categorieMerc = comunicazione.getCategorie_merc();
        // Controllo di esistenza dello smartcig, se lo smartcig esiste gia'
        // non è necessario inserire nulla in W3SMARTCIG
        boolean esisteW3SMARTCIG = this.w3Manager.esisteW3SMARTCIG(comunicazione.getCig());
        Long numgara = null;

        if (!esisteW3SMARTCIG) {
        	numgara = (Long) sqlManager.getObject("select max(CODRICH) from W3SMARTCIG", new Object[] {});
        	if (numgara == null) numgara = new Long(0);
        	numgara = new Long(numgara.longValue() + 1);
          
        	DataColumnContainer dccW3SMARTCIG = new DataColumnContainer(new DataColumn[] { new DataColumn("W3SMARTCIG.CODRICH", new JdbcParametro(
        	        JdbcParametro.TIPO_NUMERICO, numgara)) });
   	    	// Utente proprietario
        	dccW3SMARTCIG.addColumn("W3SMARTCIG.SYSCON", JdbcParametro.TIPO_NUMERICO, syscon);

       	    Long stato_simog = null;
        	if (comunicazione.getStato().equals("03")) {
        		stato_simog = new Long(6);
        	} else if (comunicazione.getStato().equals("02")){
        	    stato_simog = new Long(99);
        	}
        	
        	dccW3SMARTCIG.addColumn("W3SMARTCIG.RUP", JdbcParametro.TIPO_TESTO, codrup);
        	if(hMapwsLogin.get("collaborazione") != null) {
        		dccW3SMARTCIG.addColumn("W3SMARTCIG.COLLABORAZIONE", JdbcParametro.TIPO_NUMERICO, (Long) hMapwsLogin.get("collaborazione"));
        	}
        	
        	dccW3SMARTCIG.addColumn("W3SMARTCIG.STATO", JdbcParametro.TIPO_NUMERICO, stato_simog);

        	dccW3SMARTCIG.addColumn("W3SMARTCIG.OGGETTO", JdbcParametro.TIPO_TESTO, comunicazione.getOggetto());
        	dccW3SMARTCIG.addColumn("W3SMARTCIG.CODEIN", JdbcParametro.TIPO_TESTO, codUffInt);

        	// Gestione del collegamento all'archivio dei tecnici
        	//String codtec = null;


        	dccW3SMARTCIG.addColumn("W3SMARTCIG.CIG", JdbcParametro.TIPO_TESTO, comunicazione.getCig());
        	dccW3SMARTCIG.addColumn("W3SMARTCIG.IMPORTO", JdbcParametro.TIPO_DECIMALE, new Double(comunicazione.getImporto().doubleValue()));

        	if (comunicazione.getCodiceFattispecieContrattuale() != null)
        		dccW3SMARTCIG.addColumn("W3SMARTCIG.FATTISPECIE", JdbcParametro.TIPO_TESTO, comunicazione.getCodiceFattispecieContrattuale());

        	if (comunicazione.getCodiceProceduraSceltaContraente() != null)
        		dccW3SMARTCIG.addColumn("W3SMARTCIG.ID_SCELTA_CONTRAENTE", JdbcParametro.TIPO_TESTO, comunicazione.getCodiceProceduraSceltaContraente());

        	if (comunicazione.getCodiceClassificazioneGara()!= null)
        		dccW3SMARTCIG.addColumn("W3SMARTCIG.TIPO_CONTRATTO", JdbcParametro.TIPO_TESTO, comunicazione.getCodiceClassificazioneGara());

        	if (comunicazione.getCigAccordoQuadro()!= null)
        		dccW3SMARTCIG.addColumn("W3SMARTCIG.CIG_ACC_QUADRO", JdbcParametro.TIPO_TESTO, comunicazione.getCigAccordoQuadro());
        	
        	if (comunicazione.getCup()!= null)
        		dccW3SMARTCIG.addColumn("W3SMARTCIG.CUP", JdbcParametro.TIPO_TESTO, comunicazione.getCup());

        	if (comunicazione.getMotivo_rich_cig_comuni()!= null)
        		dccW3SMARTCIG.addColumn("W3SMARTCIG.M_RICH_CIG_COMUNI", JdbcParametro.TIPO_TESTO, comunicazione.getMotivo_rich_cig_comuni());

        	if (comunicazione.getMotivo_rich_cig_catmerc()!= null)
        		dccW3SMARTCIG.addColumn("W3SMARTCIG.M_RICH_CIG", JdbcParametro.TIPO_TESTO, comunicazione.getMotivo_rich_cig_catmerc());

        	if (comunicazione.getDataOperazione() != null)
        		dccW3SMARTCIG.addColumn("W3SMARTCIG.DATA_OPERAZIONE", JdbcParametro.TIPO_DATA, comunicazione.getDataOperazione().getTime());

       	    if (categorieMerc != null) {
       	        for (int i = 0; i < categorieMerc.length; i++) {
       	        	DataColumnContainer dccW3SMARTCIGMERC = new DataColumnContainer(new DataColumn[] { new DataColumn("W3SMARTCIGMERC.CODRICH",
        	              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numgara)) });
       	        	dccW3SMARTCIGMERC.addColumn("W3SMARTCIGMERC.NUMMERC", JdbcParametro.TIPO_NUMERICO, new Long(i + 1));
       	        	dccW3SMARTCIGMERC.addColumn("W3SMARTCIGMERC.CATEGORIA", JdbcParametro.TIPO_NUMERICO, new Long(categorieMerc[i]));
       	        	dccW3SMARTCIGMERC.insert("W3SMARTCIGMERC", this.sqlManager);
       	        }

        	}

       	    dccW3SMARTCIG.insert("W3SMARTCIG", this.sqlManager);
        } 

        // HashMap di restituzione valori di controllo
        if (esisteW3SMARTCIG) {
        	hMapConsultaSmartCig.put("w3smartcig_esistente", "1");
        } else {
        	hMapConsultaSmartCig.put("w3smartcig_esistente", "2");
        }
        hMapConsultaSmartCig.put("w3smartcig_numgara", numgara);
        hMapConsultaSmartCig.put("w3smartcig_cig", comunicazione.getCig());
        hMapConsultaSmartCig.put("w3smartcig_oggetto", comunicazione.getOggetto());

    } catch (SQLException e) {
      throw new GestoreException("Si e' verificato un errore durante la consultazione dello smartcig",
          "gestioneIDGARACIG.consultagaralotto.error", e);
    }

    if (logger.isDebugEnabled()) logger.debug("inserisciSMARTCIGdaSIMOG: fine metodo");

    return hMapConsultaSmartCig;
  }
  
  /**
   * Inserimento dei dati, provenienti da SIMOG, nella tabella W3LOTT
   * 
   * @param datiLotto
   * @param numgara
   * @param cig
   * @return
   * @throws SQLException
   * @throws GestoreException
   */
  private Long inserisciW3LOTTdaSIMOG(LottoType datiLotto, Long numgara, String cig) throws SQLException, GestoreException {
    DataColumnContainer dccW3LOTT = new DataColumnContainer(new DataColumn[] { new DataColumn("W3LOTT.NUMGARA", new JdbcParametro(
        JdbcParametro.TIPO_NUMERICO, numgara)) });

    Long numlott = (Long) sqlManager.getObject("select max(numlott) from w3lott where numgara = ?", new Object[] { numgara });
    if (numlott == null) numlott = new Long(0);
    numlott = new Long(numlott.longValue() + 1);
    dccW3LOTT.addColumn("W3LOTT.NUMLOTT", JdbcParametro.TIPO_NUMERICO, numlott);
    dccW3LOTT.addColumn("W3LOTT.STATO_SIMOG", JdbcParametro.TIPO_NUMERICO, new Long(99));

    // UUID
    UUID uuid = UUID.randomUUID();
    dccW3LOTT.addColumn("W3LOTT.LOTTO_UUID", uuid.toString());

    dccW3LOTT.addColumn("W3LOTT.CIG", JdbcParametro.TIPO_TESTO, cig);
    dccW3LOTT.addColumn("W3LOTT.OGGETTO", JdbcParametro.TIPO_TESTO, datiLotto.getOGGETTO());
    dccW3LOTT.addColumn("W3LOTT.SOMMA_URGENZA", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(datiLotto.getSOMMAURGENZA()) ? "1" : "2");
    dccW3LOTT.addColumn("W3LOTT.IMPORTO_LOTTO", JdbcParametro.TIPO_DECIMALE, new Double(datiLotto.getIMPORTOLOTTO().doubleValue()));
    dccW3LOTT.addColumn("W3LOTT.IMPORTO_SA", JdbcParametro.TIPO_DECIMALE, new Double(datiLotto.getIMPORTOSA().doubleValue()));
    dccW3LOTT.addColumn("W3LOTT.IMPORTO_IMPRESA", JdbcParametro.TIPO_DECIMALE, new Double(datiLotto.getIMPORTOIMPRESA().doubleValue()));
    dccW3LOTT.addColumn("W3LOTT.CPV", JdbcParametro.TIPO_TESTO, datiLotto.getCPV());
    dccW3LOTT.addColumn("W3LOTT.ID_SCELTA_CONTRAENTE", JdbcParametro.TIPO_NUMERICO, new Long(datiLotto.getIDSCELTACONTRAENTE()));
    dccW3LOTT.addColumn("W3LOTT.ID_CATEGORIA_PREVALENTE", JdbcParametro.TIPO_TESTO, UtilitySITAT.getCategoriaSITAT(this.sqlManager,datiLotto.getIDCATEGORIAPREVALENTE()));

    if (datiLotto.isSetDATAPUBBLICAZIONE())
      dccW3LOTT.addColumn("W3LOTT.DATA_PUBBLICAZIONE", JdbcParametro.TIPO_DATA, datiLotto.getDATAPUBBLICAZIONE().getTime());

    if (datiLotto.isSetDATASCADENZAPAGAMENTI())
      dccW3LOTT.addColumn("W3LOTT.DATA_SCADENZA_PAGAMENTI", JdbcParametro.TIPO_DATA, datiLotto.getDATASCADENZAPAGAMENTI().getTime());

    if (datiLotto.isSetDATACOMUNICAZIONE())
      dccW3LOTT.addColumn("W3LOTT.DATA_COMUNICAZIONE", JdbcParametro.TIPO_DATA, datiLotto.getDATACOMUNICAZIONE().getTime());

    if (datiLotto.isSetDATAINIBPAGAMENTO())
      dccW3LOTT.addColumn("W3LOTT.DATA_INIB_PAGAMENTO", JdbcParametro.TIPO_DATA, datiLotto.getDATAINIBPAGAMENTO().getTime());

    if (datiLotto.isSetIDMOTIVAZIONE())
      dccW3LOTT.addColumn("W3LOTT.ID_MOTIVAZIONE", JdbcParametro.TIPO_TESTO, datiLotto.getIDMOTIVAZIONE());

    if (datiLotto.isSetNOTECANC()) dccW3LOTT.addColumn("W3LOTT.NOTE_CANC", JdbcParametro.TIPO_TESTO, datiLotto.getNOTECANC());

    if (datiLotto.isSetTIPOCONTRATTO()) {
      dccW3LOTT.addColumn("W3LOTT.TIPO_CONTRATTO", JdbcParametro.TIPO_TESTO, datiLotto.getTIPOCONTRATTO().toString());
      int i = 0;
      TipiAppaltoType[] datiTipiAppaltoForn = datiLotto.getTipiAppaltoFornArray();
      if (datiTipiAppaltoForn != null && datiTipiAppaltoForn.length > 0) {
        for (i = 0; i < datiTipiAppaltoForn.length; i++) {
          DataColumnContainer dccW3LOTTTIPI = new DataColumnContainer(new DataColumn[] { new DataColumn("W3LOTTTIPI.NUMGARA",
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numgara)) });
          dccW3LOTTTIPI.addColumn("W3LOTTTIPI.NUMLOTT", JdbcParametro.TIPO_NUMERICO, numlott);
          dccW3LOTTTIPI.addColumn("W3LOTTTIPI.NUMTIPI", JdbcParametro.TIPO_NUMERICO, new Long(i + 1));
		  dccW3LOTTTIPI.addColumn("W3LOTTTIPI.IDAPPALTO", JdbcParametro.TIPO_NUMERICO, new Long(datiTipiAppaltoForn[i].getIDAPPALTO()));
          dccW3LOTTTIPI.insert("W3LOTTTIPI", this.sqlManager);
        }
      }

      TipiAppaltoType[] datiTipiAppaltoLav = datiLotto.getTipiAppaltoLavArray();
      if (datiTipiAppaltoLav != null && datiTipiAppaltoLav.length > 0) {
        for (int j = 0; j < datiTipiAppaltoLav.length; j++) {
          DataColumnContainer dccW3LOTTTIPI = new DataColumnContainer(new DataColumn[] { new DataColumn("W3LOTTTIPI.NUMGARA",
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numgara)) });
          dccW3LOTTTIPI.addColumn("W3LOTTTIPI.NUMLOTT", JdbcParametro.TIPO_NUMERICO, numlott);
          dccW3LOTTTIPI.addColumn("W3LOTTTIPI.NUMTIPI", JdbcParametro.TIPO_NUMERICO, new Long(i + j + 1));
          dccW3LOTTTIPI.addColumn("W3LOTTTIPI.IDAPPALTO", JdbcParametro.TIPO_NUMERICO, new Long(datiTipiAppaltoLav[j].getIDAPPALTO()));
          dccW3LOTTTIPI.insert("W3LOTTTIPI", this.sqlManager);
        }
      }
    }

    if (datiLotto.isSetFLAGESCLUSO())
      dccW3LOTT.addColumn("W3LOTT.FLAG_ESCLUSO", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(datiLotto.getFLAGESCLUSO()) ? "1" : "2");

    if (datiLotto.isSetLUOGOISTAT()) dccW3LOTT.addColumn("W3LOTT.LUOGO_ISTAT", JdbcParametro.TIPO_TESTO, datiLotto.getLUOGOISTAT());

    if (datiLotto.isSetLUOGONUTS()) dccW3LOTT.addColumn("W3LOTT.LUOGO_NUTS", JdbcParametro.TIPO_TESTO, datiLotto.getLUOGONUTS());

    if (datiLotto.isSetIMPORTOATTUAZIONESICUREZZA())
      dccW3LOTT.addColumn("W3LOTT.IMPORTO_ATTUAZIONE_SICUREZZA", JdbcParametro.TIPO_DECIMALE, new Double(
          datiLotto.getIMPORTOATTUAZIONESICUREZZA()));

    if (datiLotto.isSetTRIENNIOANNOINIZIO())
      dccW3LOTT.addColumn("W3LOTT.TRIENNIO_ANNO_INIZIO", JdbcParametro.TIPO_NUMERICO, new Long(datiLotto.getTRIENNIOANNOINIZIO()));

    if (datiLotto.isSetTRIENNIOANNOFINE())
      dccW3LOTT.addColumn("W3LOTT.TRIENNIO_ANNO_FINE", JdbcParametro.TIPO_NUMERICO, new Long(datiLotto.getTRIENNIOANNOFINE()));

    if (datiLotto.isSetTRIENNIOPROGRESSIVO())
      dccW3LOTT.addColumn("W3LOTT.TRIENNIO_PROGRESSIVO", JdbcParametro.TIPO_NUMERICO, new Long(datiLotto.getTRIENNIOPROGRESSIVO()));

    if (datiLotto.isSetANNUALECUIMININF())
      dccW3LOTT.addColumn("W3LOTT.ANNUALE_CUI_MININF", JdbcParametro.TIPO_TESTO, datiLotto.getANNUALECUIMININF());

    if (datiLotto.isSetDATACREAZIONELOTTO())
      dccW3LOTT.addColumn("W3LOTT.DATA_CREAZIONE_LOTTO", JdbcParametro.TIPO_DATA, datiLotto.getDATACREAZIONELOTTO().getTime());

    if (datiLotto.isSetDATACANCELLAZIONELOTTO())
      dccW3LOTT.addColumn("W3LOTT.DATA_CANCELLAZIONE_LOTTO", JdbcParametro.TIPO_DATA, datiLotto.getDATACANCELLAZIONELOTTO().getTime());

    // Inserimento dei dati delle ulteriori categorie
    if (datiLotto.isSetCATEGORIE()) {
      String[] datiCategorie = datiLotto.getCATEGORIE().getCATEGORIAArray();
      if (datiCategorie != null && datiCategorie.length > 0) {
        for (int i = 0; i < datiCategorie.length; i++) {
          DataColumnContainer dccW3LOTTCATE = new DataColumnContainer(new DataColumn[] { new DataColumn("W3LOTTCATE.NUMGARA",
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numgara)) });
          dccW3LOTTCATE.addColumn("W3LOTTCATE.NUMLOTT", JdbcParametro.TIPO_NUMERICO, numlott);
          dccW3LOTTCATE.addColumn("W3LOTTCATE.NUMCATE", JdbcParametro.TIPO_NUMERICO, new Long(i + 1));
          dccW3LOTTCATE.addColumn("W3LOTTCATE.CATEGORIA", JdbcParametro.TIPO_TESTO, UtilitySITAT.getCategoriaSITAT(this.sqlManager,datiCategorie[i]));
          dccW3LOTTCATE.insert("W3LOTTCATE", this.sqlManager);
        }
      }
    }

    if (datiLotto.isSetFLAGPREVEDERIP()) {
      dccW3LOTT.addColumn("W3LOTT.FLAG_PREVEDE_RIP", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(datiLotto.getFLAGPREVEDERIP())
          ? "1"
          : "2");
    }

    /*if (datiLotto.isSetFLAGRIPETIZIONE()) {
      dccW3LOTT.addColumn("W3LOTT.FLAG_RIPETIZIONE", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(datiLotto.getFLAGRIPETIZIONE())
          ? "1"
          : "2");
    }*/
    if (datiLotto.isSetIDMOTIVOCOLLCIG()) {
    	dccW3LOTT.addColumn("W3LOTT.MOTIVO_COLLEGAMENTO", JdbcParametro.TIPO_NUMERICO, new Long(datiLotto.getIDMOTIVOCOLLCIG()));
    }
    
    if (datiLotto.isSetCIGORIGINERIP()) {
      dccW3LOTT.addColumn("W3LOTT.CIG_ORIGINE_RIP", JdbcParametro.TIPO_TESTO, datiLotto.getCIGORIGINERIP());
    }

    if (datiLotto.isSetORASCADENZA()) {
      dccW3LOTT.addColumn("W3LOTT.ORA_SCADENZA", JdbcParametro.TIPO_TESTO, datiLotto.getORASCADENZA());
    }

    if (datiLotto.isSetSTATOAVCPASS()) {
      dccW3LOTT.addColumn("W3LOTT.STATO_AVCPASS", JdbcParametro.TIPO_NUMERICO, datiLotto.getSTATOAVCPASS());
    }

    if (datiLotto.isSetDATASCADENZARICHIESTAINVITO()) {
      dccW3LOTT.addColumn("W3LOTT.DSCAD_RICHIESTA_INVITO", JdbcParametro.TIPO_DATA, 
          UtilityDate.convertiData(datiLotto.getDATASCADENZARICHIESTAINVITO(),
              UtilityDate.FORMATO_AAAAMMGG));
    }

    if (datiLotto.isSetDATALETTERAINVITO()) {
      dccW3LOTT.addColumn("W3LOTT.DATA_LETTERA_INVITO", JdbcParametro.TIPO_DATA, datiLotto.getDATALETTERAINVITO().getTime());
    }

    if (datiLotto.isSetFLAGCUP()) {
      dccW3LOTT.addColumn("W3LOTT.FLAG_CUP", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(datiLotto.getFLAGCUP()) ? "1" : "2");
    }

    if (datiLotto.isSetCUPLOTTO()) {
      CUPLOTTOType cuplottoType = datiLotto.getCUPLOTTO();
      cuplottoType.getCIG();
      DatiCUPType[] datiCup = cuplottoType.getCODICICUPArray();
      if (datiCup != null && datiCup.length > 0) {
        for (int i = 0; i < datiCup.length; i++) {
          DataColumnContainer dccW3LOTTCUP = new DataColumnContainer(new DataColumn[] { new DataColumn("W3LOTTCUP.NUMGARA",
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numgara)) });
          dccW3LOTTCUP.addColumn("W3LOTTCUP.NUMLOTT", JdbcParametro.TIPO_NUMERICO, numlott);
          dccW3LOTTCUP.addColumn("W3LOTTCUP.NUMCUP", JdbcParametro.TIPO_NUMERICO, new Long(i + 1));
          dccW3LOTTCUP.addColumn("W3LOTTCUP.CUP", JdbcParametro.TIPO_TESTO, datiCup[i].getCUP());
          dccW3LOTTCUP.addColumn("W3LOTTCUP.DATI_DIPE", JdbcParametro.TIPO_TESTO, datiCup[i].getDATIDIPE());
          dccW3LOTTCUP.insert("W3LOTTCUP", this.sqlManager);
        }
      }
    }

    dccW3LOTT.insert("W3LOTT", this.sqlManager);
    
    return numlott;
  }

  /**
   * Inserimento dei dati, provenienti dalla consultazione SIMOG, in W3GARA
   * 
   * @param datiGara
   * @param numgara
   * @param syscon
   * @throws GestoreException
   * @throws SQLException
   */
  private void inserisciW3GARAdaSIMOG(GaraType datiGara, Long numgara, Long syscon, String codUffInt, String codrup) throws GestoreException, SQLException {

    DataColumnContainer dccW3GARA = new DataColumnContainer(new DataColumn[] { new DataColumn("W3GARA.NUMGARA", new JdbcParametro(
        JdbcParametro.TIPO_NUMERICO, numgara)) });

    // Utente proprietario
    dccW3GARA.addColumn("W3GARA.SYSCON", JdbcParametro.TIPO_NUMERICO, syscon);

    // UUID
    UUID uuid = UUID.randomUUID();
    dccW3GARA.addColumn("W3GARA.GARA_UUID", uuid.toString());

    Long stato_simog = null;
    if (datiGara.isSetDATACONFERMAGARA()) {
      stato_simog = new Long(7);
    } else {
      stato_simog = new Long(99);
    }
    dccW3GARA.addColumn("W3GARA.STATO_SIMOG", JdbcParametro.TIPO_NUMERICO, stato_simog);

    dccW3GARA.addColumn("W3GARA.OGGETTO", JdbcParametro.TIPO_TESTO, datiGara.getOGGETTO());

    // Gestione del collegamento all'archivio dei tecnici
    /*String codtec = null;
    String protezioneArchivi = ConfigManager.getValore(PROP_PROTEZIONE_ARCHIVI);
    if (protezioneArchivi != null && "1".equals(protezioneArchivi)) {
      String selectTECNI = "select tecni.codtec from tecni, w3permessi where "
          + " tecni.codtec = w3permessi.codtec and "
          + " tecni.cftec = ? and w3permessi.syscon = ?";
      codtec = (String) sqlManager.getObject(selectTECNI, new Object[] { datiGara.getCFUTENTE(), syscon });
    } else {
      String selectTECNI = "select tecni.codtec from tecni where cftec = ?";
      codtec = (String) sqlManager.getObject(selectTECNI, new Object[] { datiGara.getCFUTENTE() });
    }*/

    if (codrup != null) dccW3GARA.addColumn("W3GARA.RUP_CODTEC", JdbcParametro.TIPO_TESTO, codrup);

    // Gestione della collaborazione
    String id_stazione_appaltante = datiGara.getIDSTAZIONEAPPALTANTE();
    Long id_collaborazione = (Long) sqlManager.getObject("select id from w3aziendaufficio where ufficio_id = ?",
        new Object[] { id_stazione_appaltante });

    if (id_collaborazione != null) {
      dccW3GARA.addColumn("W3GARA.COLLABORAZIONE", JdbcParametro.TIPO_DECIMALE, id_collaborazione);
    }

    dccW3GARA.addColumn("W3GARA.CODEIN", JdbcParametro.TIPO_TESTO, codUffInt);
    dccW3GARA.addColumn("W3GARA.ID_GARA", JdbcParametro.TIPO_TESTO, "" + datiGara.getIDGARA());
    dccW3GARA.addColumn("W3GARA.IMPORTO_GARA", JdbcParametro.TIPO_DECIMALE, new Double(datiGara.getIMPORTOGARA().doubleValue()));

    if (datiGara.isSetIMPORTOSAGARA())
      dccW3GARA.addColumn("W3GARA.IMPORTO_SA_GARA", JdbcParametro.TIPO_DECIMALE, new Double(datiGara.getIMPORTOSAGARA().doubleValue()));

    if (datiGara.isSetDATACANCELLAZIONEGARA())
      dccW3GARA.addColumn("W3GARA.DATA_CANCELLAZIONE_GARA", JdbcParametro.TIPO_DATA, datiGara.getDATACANCELLAZIONEGARA().getTime());

    if (datiGara.isSetDATATERMINEPAGAMENTO())
      dccW3GARA.addColumn("W3GARA.DATA_TERMINE_PAGAMENTO", JdbcParametro.TIPO_DATA, datiGara.getDATATERMINEPAGAMENTO().getTime());

    if (datiGara.isSetDATACOMUN()) dccW3GARA.addColumn("W3GARA.DATA_COMUN", JdbcParametro.TIPO_DATA, datiGara.getDATACOMUN().getTime());

    if (datiGara.isSetDATAINIBPAGAM())
      dccW3GARA.addColumn("W3GARA.DATA_INIB_PAGAM", JdbcParametro.TIPO_DATA, datiGara.getDATAINIBPAGAM().getTime());

    if (datiGara.isSetDATACONFERMAGARA())
      dccW3GARA.addColumn("W3GARA.DATA_CONFERMA_GARA", JdbcParametro.TIPO_DATA, datiGara.getDATACONFERMAGARA().getTime());

    if (datiGara.isSetTIPOSCHEDA())
      dccW3GARA.addColumn("W3GARA.TIPO_SCHEDA", JdbcParametro.TIPO_TESTO, datiGara.getTIPOSCHEDA().toString());

    if (datiGara.isSetMODOINDIZIONE())
      dccW3GARA.addColumn("W3GARA.MODO_INDIZIONE", JdbcParametro.TIPO_NUMERICO, new Long(datiGara.getMODOINDIZIONE()));

    if (datiGara.isSetMODOREALIZZAZIONE())
      dccW3GARA.addColumn("W3GARA.MODO_REALIZZAZIONE", JdbcParametro.TIPO_NUMERICO, new Long(datiGara.getMODOREALIZZAZIONE()));

    if (datiGara.isSetIDMOTIVAZIONE()) dccW3GARA.addColumn("W3GARA.ID_MOTIVAZIONE", JdbcParametro.TIPO_TESTO, datiGara.getIDMOTIVAZIONE());

    if (datiGara.isSetNOTECANC()) dccW3GARA.addColumn("W3GARA.NOTE_CANC", JdbcParametro.TIPO_TESTO, datiGara.getNOTECANC());

    if (datiGara.isSetCIGACCQUADRO()) dccW3GARA.addColumn("W3GARA.CIG_ACC_QUADRO", JdbcParametro.TIPO_TESTO, datiGara.getCIGACCQUADRO());

    if (datiGara.isSetNUMEROLOTTI())
      dccW3GARA.addColumn("W3GARA.NUMERO_LOTTI", JdbcParametro.TIPO_NUMERICO, new Long(datiGara.getNUMEROLOTTI()));

    if (datiGara.isSetDATACREAZIONE())
      dccW3GARA.addColumn("W3GARA.DATA_CREAZIONE", JdbcParametro.TIPO_DATA, datiGara.getDATACREAZIONE().getTime());

    if (datiGara.isSetIDOSSERVATORIO())
      dccW3GARA.addColumn("W3GARA.ID_OSSERVATORIO", JdbcParametro.TIPO_TESTO, datiGara.getIDOSSERVATORIO());

    if (datiGara.isSetIDSTATOGARA()) dccW3GARA.addColumn("W3GARA.ID_STATO_GARA", JdbcParametro.TIPO_TESTO, datiGara.getIDSTATOGARA());

    if (datiGara.isSetDATAPERFEZIONAMENTOBANDO())
      dccW3GARA.addColumn("W3GARA.DATA_PERFEZIONAMENTO_BANDO", JdbcParametro.TIPO_DATA, datiGara.getDATAPERFEZIONAMENTOBANDO().getTime());

    if (datiGara.isSetPROVVPRESACARICO()) {
      dccW3GARA.addColumn("W3GARA.PROVV_PRESA_CARICO", JdbcParametro.TIPO_TESTO, datiGara.getPROVVPRESACARICO());
    }

    if (datiGara.isSetESCLUSOAVCPASS()) {
      dccW3GARA.addColumn("W3GARA.ESCLUSO_AVCPASS", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(datiGara.getESCLUSOAVCPASS()) ? "1" : "2");
    }

    /*
    if (datiGara.isSetMOTIVORICHCIG()) {
      dccW3GARA.addColumn("W3GARA.M_RICH_CIG", JdbcParametro.TIPO_NUMERICO, new Long(datiGara.getMOTIVORICHCIG()));
    }

    if (datiGara.isSetMOTIVORICHCIGCOMUNI()) {
      dccW3GARA.addColumn("W3GARA.M_RICH_CIG_COMUNI", JdbcParametro.TIPO_NUMERICO, new Long(datiGara.getMOTIVORICHCIGCOMUNI()));
    }

    if (datiGara.isSetCATEGORIEMERC()) {
      ElencoCategMercType elencoCategorieMerceologiche = datiGara.getCATEGORIEMERC();
      String[] categorie = elencoCategorieMerceologiche.getCATEGORIAArray();
      if (categorie != null && categorie.length > 0) {
        for (int c = 0; c < categorie.length; c++) {
          DataColumnContainer dccW3GARAMERC = new DataColumnContainer(new DataColumn[] { new DataColumn("W3GARAMERC.NUMGARA",
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numgara)) });
          dccW3GARAMERC.addColumn("W3GARAMERC.NUMMERC", JdbcParametro.TIPO_NUMERICO, new Long(c + 1));
          dccW3GARAMERC.addColumn("W3GARAMERC.CATEGORIA", JdbcParametro.TIPO_NUMERICO, new Long(categorie[c]));
          dccW3GARAMERC.insert("W3GARAMERC", this.sqlManager);
        }
      }
    }*/

    dccW3GARA.insert("W3GARA", this.sqlManager);

  }
  
  /**
   * Inserimento dei dati, provenienti da SIMOG, nella tabella W3GARAREQ
   * 
   * @param datiLotto
   * @param numgara
   * @return
   * @throws SQLException
   * @throws GestoreException
   */
  public void inserisciW3GARAREQdaSIMOG(ReqGaraType[] requisitiGara, Long numgara) throws SQLException, GestoreException {
	  
	  //cancello requisiti per la gara
	  this.sqlManager.update("delete from W3GARAREQ where NUMGARA = ?", new Object[] {numgara});
	  for (int i = 0; i< requisitiGara.length; i++) {
		  ReqGaraType requisito = requisitiGara[i];
		  DataColumnContainer dccW3GARAREQ = new DataColumnContainer(new DataColumn[] { new DataColumn("W3GARAREQ.NUMGARA", new JdbcParametro(
			        JdbcParametro.TIPO_NUMERICO, numgara)) });
		  dccW3GARAREQ.addColumn("W3GARAREQ.NUMREQ", JdbcParametro.TIPO_NUMERICO, new Long(i + 1));
		  dccW3GARAREQ.addColumn("W3GARAREQ.CODICE_DETTAGLIO", JdbcParametro.TIPO_TESTO, requisito.getCodiceDettaglio());
		  dccW3GARAREQ.addColumn("W3GARAREQ.DESCRIZIONE", JdbcParametro.TIPO_TESTO, requisito.getDescrizione());
		  dccW3GARAREQ.addColumn("W3GARAREQ.VALORE", JdbcParametro.TIPO_TESTO, requisito.getValore());
		  dccW3GARAREQ.addColumn("W3GARAREQ.FLAG_ESCLUSIONE", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(requisito.getFlagEsclusione()) ? "1" : "2");
		  dccW3GARAREQ.addColumn("W3GARAREQ.FLAG_COMPROVA_OFFERTA", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(requisito.getFlagComprovaOfferta()) ? "1" : "2");
		  dccW3GARAREQ.addColumn("W3GARAREQ.FLAG_AVVALIMENTO", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(requisito.getFlagAvvalimento()) ? "1" : "2");
		  dccW3GARAREQ.addColumn("W3GARAREQ.FLAG_BANDO_TIPO", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(requisito.getFlagBandoTipo()) ? "1" : "2");
		  dccW3GARAREQ.addColumn("W3GARAREQ.FLAG_RISERVATEZZA", JdbcParametro.TIPO_TESTO, FlagSNType.S.equals(requisito.getFlagRiservatezza()) ? "1" : "2");
		  dccW3GARAREQ.insert("W3GARAREQ", this.sqlManager);
	  }

  }
  

  private String convertiImporto(Double importo) {
    String result = null;

    if (importo != null) {

      DecimalFormatSymbols simbols = new DecimalFormatSymbols();
      simbols.setDecimalSeparator('.');
      DecimalFormat decimalFormat = new DecimalFormat("0.00", simbols);
      result = decimalFormat.format(importo);
    }
    return result;
  }
  
}
