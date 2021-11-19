package it.eldasoft.sil.w3.bl;

import it.avlp.simog.massload.xmlbeans.AllegatoType;
import it.avlp.simog.massload.xmlbeans.Collaborazione;
import it.avlp.simog.massload.xmlbeans.Collaborazioni;
import it.avlp.simog.massload.xmlbeans.ResponseCancellaGara;
import it.avlp.simog.massload.xmlbeans.ResponseCancellaLotto;
import it.avlp.simog.massload.xmlbeans.ResponseCheckLogin;
import it.avlp.simog.massload.xmlbeans.ResponseChiudiSession;
import it.avlp.simog.massload.xmlbeans.ResponseConsultaGara;
import it.avlp.simog.massload.xmlbeans.ResponseInserisciLotto;
import it.avlp.simog.massload.xmlbeans.ResponseInviaRequisiti;
import it.avlp.simog.massload.xmlbeans.ResponseModificaGara;
import it.avlp.simog.massload.xmlbeans.ResponseModificaLotto;
import it.avlp.simog.massload.xmlbeans.ResponsePubblicazioneBando;
import it.avlp.simog.massload.xmlbeans.DatiGaraType;
import it.avlp.simog.massload.xmlbeans.CUPLOTTOType;
import it.avlp.simog.massload.xmlbeans.CancellaGara;
import it.avlp.simog.massload.xmlbeans.CancellaGaraDocument;
import it.avlp.simog.massload.xmlbeans.CancellaGaraResponse;
import it.avlp.simog.massload.xmlbeans.CancellaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.CancellaLotto;
import it.avlp.simog.massload.xmlbeans.CancellaLottoDocument;
import it.avlp.simog.massload.xmlbeans.CancellaLottoResponse;
import it.avlp.simog.massload.xmlbeans.CancellaLottoResponseDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaGara;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraDocument;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponse;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.DatiCUPType;
import it.avlp.simog.massload.xmlbeans.GaraType;
import it.avlp.simog.massload.xmlbeans.InserisciGara.DatiGara;
import it.avlp.simog.massload.xmlbeans.InserisciGaraDocument;
import it.avlp.simog.massload.xmlbeans.InserisciGaraResponse;
import it.avlp.simog.massload.xmlbeans.InserisciGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.InserisciLotto;
import it.avlp.simog.massload.xmlbeans.InserisciLotto.DatiLotto;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.InserisciLottoDocument;
import it.avlp.simog.massload.xmlbeans.InserisciLottoResponse;
import it.avlp.simog.massload.xmlbeans.InserisciLottoResponseDocument;
import it.avlp.simog.massload.xmlbeans.InviaRequisiti;
import it.avlp.simog.massload.xmlbeans.InviaRequisiti.Requisiti;
import it.avlp.simog.massload.xmlbeans.InviaRequisitiDocument;
import it.avlp.simog.massload.xmlbeans.InviaRequisitiResponse;
import it.avlp.simog.massload.xmlbeans.InviaRequisitiResponseDocument;
import it.avlp.simog.massload.xmlbeans.Login;
import it.avlp.simog.massload.xmlbeans.LoginDocument;
import it.avlp.simog.massload.xmlbeans.LoginResponseDocument;
import it.avlp.simog.massload.xmlbeans.LoginResponse;
import it.avlp.simog.massload.xmlbeans.InserisciGara;
import it.avlp.simog.massload.xmlbeans.ChiudiSessione;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneResponseDocument;
import it.avlp.simog.massload.xmlbeans.LottoType;
import it.avlp.simog.massload.xmlbeans.ModificaGara;
import it.avlp.simog.massload.xmlbeans.ModificaGaraDocument;
import it.avlp.simog.massload.xmlbeans.ModificaGaraResponse;
import it.avlp.simog.massload.xmlbeans.ModificaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.ModificaLotto;
import it.avlp.simog.massload.xmlbeans.ModificaLottoDocument;
import it.avlp.simog.massload.xmlbeans.ModificaLottoResponse;
import it.avlp.simog.massload.xmlbeans.ModificaLottoResponseDocument;
import it.avlp.simog.massload.xmlbeans.Pubblica;
import it.avlp.simog.massload.xmlbeans.Pubblica.DatiPubblicazione;
import it.avlp.simog.massload.xmlbeans.PubblicaDocument;
import it.avlp.simog.massload.xmlbeans.PubblicaResponse;
import it.avlp.simog.massload.xmlbeans.PubblicaResponseDocument;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.ReqGaraType;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.avlp.smartCig.comunicazione.ComunicazioneType;
import it.avlp.smartCig.errore.ErroreType;
import it.avlp.smartCig.risultato.CodiceRisultatoType;
import it.avlp.smartCig.risultato.RisultatoType;
import it.avlp.smartCig.user.LoggedUserInfoType;
import it.avlp.smartCig.ws.AnnullaComunicazioneRequest;
import it.avlp.smartCig.ws.AnnullaComunicazioneResponse;
import it.avlp.smartCig.ws.ComunicaSingolaRequest;
import it.avlp.smartCig.ws.ComunicaSingolaResponse;
import it.avlp.smartCig.ws.ConsultaComunicazioneRequest;
import it.avlp.smartCig.ws.ConsultaComunicazioneResponse;
import it.avlp.smartCig.ws.Services;
import it.avlp.smartCig.ws.ServicesServiceLocator;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.utils.utility.UtilityStringhe;

import java.io.File;
import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

public class GestioneServiziIDGARACIGManager {

  /** Logger */
  static Logger                       logger                           = Logger.getLogger(GestioneServiziIDGARACIGManager.class);

  private static final String         PROP_SIMOG_WS_URL                = "it.eldasoft.simog.ws.url";
  private static final String         PROP_SIMOG_WS_SMARTCIG_URL       = "it.eldasoft.simog.ws.smartcig.url";
  
  
  private static final String         PROP_SIMOG_WS_TRUSTSTORE         = "it.eldasoft.simog.ws.truststore";
  private static final String         PROP_SIMOG_WS_TRUSTSTOREPASSWORD = "it.eldasoft.simog.ws.truststorepassword";

  private static final String         PROP_HTTP_PROXY_HOST             = "it.eldasoft.http.proxyhost.url";
  private static final String         PROP_HTTP_PROXY_PORT             = "it.eldasoft.http.proxyhost.port";

  private SqlManager                  sqlManager;

  private GestioneXMLIDGARACIGManager gestioneXMLIDGARACIGManager;

  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  public void setGestioneXMLIDGARACIGManager(
      GestioneXMLIDGARACIGManager gestioneXMLIDGARACIGManager) {
    this.gestioneXMLIDGARACIGManager = gestioneXMLIDGARACIGManager;
  }

  /**
   * Invio della richiesta di generazione del numero identificativo per una gara
   *
   * @param numgara
   * @throws GestoreException
   */
  public String GareMassivo(String simogwsuser, String simogwspass,
      String sql, String index) throws GestoreException {

    String idgara = null;

    if (logger.isDebugEnabled())
      logger.debug("richiestaIdGara: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();
    try {
    	
      simogWS = this.getSimogWS();
      //String index = this.getIndiceCollaborazione(numgara, "W3GARA");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);

      // Creazione del contenuto XML
      String ufficio_id = ((String) hMapwsLogin.get("ufficio_id"));
      String ufficio_denominazione = ((String) hMapwsLogin.get("ufficio_denominazione"));
      String azienda_codicefiscale = ((String) hMapwsLogin.get("azienda_codicefiscale"));
      String azienda_denominazione = ((String) hMapwsLogin.get("azienda_denominazione"));

      // Gestione identificativo per i lotti (CIG)
      // Si richiede il codice CIG per ogni lotto che ne è privo.
      List<?> datiW3 = this.sqlManager.getListVector(sql, new Object[] { });
      if (datiW3 != null && datiW3.size() > 0) {

        for (int i = 0; i < datiW3.size(); i++) {
        	Long numgara = new Long(0);
        	try {
        		numgara = (Long) SqlManager.getValueFromVectorParam(
        				datiW3.get(i), 0).getValue();
        		GaraType gara = this.gestioneXMLIDGARACIGManager.getDatiGara(
                        numgara, ufficio_id, ufficio_denominazione, azienda_codicefiscale,
                        azienda_denominazione);
        		DatiGara datiGara = DatiGara.Factory.newInstance();
        	    datiGara.setDatiGara(gara);
                idgara = this.wsInserisciGara(simogWS, hMapwsLogin, datiGara);
                //aggiorna idgara
       
                this.aggiornaW3GARACIGMassivo(numgara, idgara);
                
                logger.info("Richiesta id gara " + numgara + " eseguita con successo IDANAC=" + idgara);
                //Richiest CIG Lotti
                List<?> datiW3LOTTI = this.sqlManager.getListVector("SELECT NUMLOTT FROM W3LOTT WHERE NUMGARA = ?", new Object[] {numgara });
                if (datiW3LOTTI != null && datiW3LOTTI.size() > 0) {
                    	for (int j = 0; j < datiW3LOTTI.size(); j++) {
                    		Long numlott = (Long) SqlManager.getValueFromVectorParam(
                    				datiW3LOTTI.get(j), 0).getValue();
                    		// Ricavo il contenuto XML
                    		LottoType lotto = this.gestioneXMLIDGARACIGManager.getDatiLotto(numgara, numlott);
                    		DatiLotto datiLotto = DatiLotto.Factory.newInstance();
                    	    datiLotto.setLotto(lotto);
                    	    
                            HashMap<String, Object> hMapwsInserisciLotto = this.wsInserisciLotto(simogWS, hMapwsLogin, datiLotto, idgara);
                            
                            this.aggiornaW3LOTTCIGMassivo(numgara, numlott, hMapwsInserisciLotto );
                    	}
                }
                logger.info("Richiesta cig per gara " + numgara + " eseguita con successo");
              //Pubblica Gara
              String selectW3GARA = "select data_perfezionamento_bando, data_termine_pagamento, id_gara, tipo_operazione," +
              		" ora_scadenza, dscad_richiesta_invito, data_lettera_invito" +
              		" from w3gara where numgara = ?";
              List<?> datiW3GARA = this.sqlManager.getVector(selectW3GARA, new Object[] {numgara});

              if (datiW3GARA != null && datiW3GARA.size() > 0 ) {
                // ********** Data pubblicazione
                Date dataPubblicazione = (Date) SqlManager.getValueFromVectorParam(
                    datiW3GARA, 0).getValue();
                String dataPubblicazioneS = UtilityDate.convertiData(dataPubblicazione, UtilityDate.FORMATO_AAAAMMGG);

                // ********** Data scadenza pagamenti
                Date dataScadenzaPagamenti = (Date) SqlManager.getValueFromVectorParam(
                    datiW3GARA, 1).getValue();
                String dataScadenzaPagamentiS = UtilityDate.convertiData(dataScadenzaPagamenti, UtilityDate.FORMATO_AAAAMMGG);

                // IDGARA (CIG)
                String cig = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 2).getValue();

                // ********** Progressivo aggiudicazione
                String progCui = "";

                String tipoOperazione = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 3).getValue();

                String oraScadenza = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 4).getValue();

                Date dataScadenzaRichiestaInvito = (Date) SqlManager.getValueFromVectorParam(datiW3GARA, 5).getValue();
                String dataScadenzaRichiestaInvitoS = "";
                if (dataScadenzaRichiestaInvito != null) {
                  dataScadenzaRichiestaInvitoS = UtilityDate.convertiData(dataScadenzaRichiestaInvito, UtilityDate.FORMATO_AAAAMMGG);
                }

                Date dataLetteraInvito = (Date) SqlManager.getValueFromVectorParam(datiW3GARA, 6).getValue();
                String dataLetteraInvitoS = "";
                if (dataLetteraInvito != null) {
                  dataLetteraInvitoS = UtilityDate.convertiData(dataLetteraInvito, UtilityDate.FORMATO_AAAAMMGG);
                }
                
                // verifico la scelta del contraente se la procedura è negoziata non devo inviare i dati della pubblicazione
                Long tipoProcedura = (Long) this.sqlManager.getObject("SELECT ID_SCELTA_CONTRAENTE FROM W3LOTT WHERE NUMGARA = ? AND STATO_SIMOG IN (2,3,4)",  new Object[] {numgara});
                // Pubblicazione
                DatiPubblicazione datiPubblicazione = null;
                if (!tipoProcedura.equals(new Long("4")) && !tipoProcedura.equals(new Long("10")) && !tipoProcedura.equals(new Long("14"))) {
                	datiPubblicazione = this.gestioneXMLIDGARACIGManager.getDatiPubblicazione(numgara);
                }
                // Documenti allegati
                AllegatoType[] documentiAllegati = null;
                documentiAllegati = this.gestioneXMLIDGARACIGManager.getDocumentiAllegati(numgara);

                // Cup lotti
                CUPLOTTOType[] cuplotti = null;
                cuplotti = this.gestioneXMLIDGARACIGManager.getCupLotti(numgara);

                this.wsPubblica(simogWS, hMapwsLogin, dataPubblicazioneS,
                  dataScadenzaPagamentiS, cig, progCui, datiPubblicazione, tipoOperazione,
                  documentiAllegati, oraScadenza, dataScadenzaRichiestaInvitoS, dataLetteraInvitoS, cuplotti);

                this.aggiornaPubblicazioneMassivo(numgara);

              }

              logger.info("Pubblicazione gara " + numgara + " eseguita con successo");
              
              
        	}
        	catch (Exception e) {
        		logger.error("Errore nella gara " + numgara + "", e);
        	}
        }
      }
    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);
    }
    catch (Exception e) {
    	throw new GestoreException(
    	          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
    	          "gestioneIDGARACIG.ws.error", e);

    }finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("richiestaIdGara: fine metodo");

    return idgara;

  }
  
  /**
   * Invio della richiesta di generazione del numero identificativo per una gara
   *
   * @param numgara
   * @throws GestoreException
   */
  public String richiestaIDGARA(String simogwsuser, String simogwspass,
      Long numgara) throws GestoreException {

    String idgara = null;

    if (logger.isDebugEnabled())
      logger.debug("richiestaIdGara: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3GARA");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);

      // Creazione del contenuto XML
      String ufficio_id = ((String) hMapwsLogin.get("ufficio_id"));
      String ufficio_denominazione = ((String) hMapwsLogin.get("ufficio_denominazione"));
      String azienda_codicefiscale = ((String) hMapwsLogin.get("azienda_codicefiscale"));
      String azienda_denominazione = ((String) hMapwsLogin.get("azienda_denominazione"));

      GaraType gara = this.gestioneXMLIDGARACIGManager.getDatiGara(
          numgara, ufficio_id, ufficio_denominazione, azienda_codicefiscale,
          azienda_denominazione);

      DatiGara datiGara = DatiGara.Factory.newInstance();
      datiGara.setDatiGara(gara);
     
      idgara = this.wsInserisciGara(simogWS, hMapwsLogin, datiGara);
      this.aggiornaW3GARACIG(numgara, idgara);

    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("richiestaIdGara: fine metodo");

    return idgara;

  }


  /**
   * Invio della richiesta di generazione SMARTCIG
   *
   * @param numgara
   * @throws GestoreException
   */
  public String richiestaSMARTCIG(String simogwsuser, String simogwspass,
      Long numgara) throws GestoreException {

    String smartCig = null;

    if (logger.isDebugEnabled())
      logger.debug("richiestaSMARTCIG: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3SMARTCIG");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);
      // Creazione della Request comunicazione 
      String ticket = ((String) hMapwsLogin.get("ticket"));

      ComunicaSingolaRequest comunicazioneRequest = this.gestioneXMLIDGARACIGManager.getComunicazioneSmartCig(numgara, index, ticket);

      Services simogSmartCigWS = this.getSimogWSSmartCig();
      
      smartCig = this.wsComunicaSingola(simogSmartCigWS, comunicazioneRequest);
      this.aggiornaW3SMARTCIG(numgara, smartCig);

    } catch (GestoreException e) {
      throw e;

    } catch (SocketTimeoutException e) {
    	throw new GestoreException(
  	          "Il servizio di elaborazione degli identificativi di gara", "gestioneIDGARACIG.error",
  	          new Object[] { "Il servizio di elaborazione degli identificativi di gara" }, null);
    } 
      catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } 
    catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("richiestaSMARTCIG: fine metodo");

    return smartCig;

  }
  
  /**
   * Invio della richiesta dei requisiti per una gara
   *
   * @param numgara
   * @throws GestoreException
   */
  public String richiestaRequisiti(String simogwsuser, String simogwspass,
      Long numgara, String idgara) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("richiestaRequisiti: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();



    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3GARA");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);

      // Creazione del contenuto XML
      Requisiti datiRequisiti = this.gestioneXMLIDGARACIGManager.getDatiRequisiti(numgara);
      
      HashMap<String, Object> hMapwsInviaRequisiti = this.wsInviaRequisiti(simogWS, hMapwsLogin, datiRequisiti, idgara);
      String messaggio = (String) (hMapwsInviaRequisiti.get("messaggio"));
      if (logger.isDebugEnabled())
          logger.debug("wsInviaRequisiti.messaggio: " + messaggio);
    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione dei requisiti di gara",
          "gestioneIDGARACIG.ws.error", e);
    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione dei requisiti di gara",
          "gestioneIDGARACIG.ws.remote.error", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("richiestaRequisiti: fine metodo");

    return idgara;

  }


  /**
   * Richiesta lista collaborazioni
   *
   * @param simogwsuser
   * @param simogwspass
   * @return
   * @throws GestoreException
   */
  public String richiestaCollaborazioni(Long syscon, String codrup, String simogwsuser,
      String simogwspass) throws GestoreException {

    String idgara = null;

    if (logger.isDebugEnabled())
      logger.debug("richiestaCollaborazioni: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();

      hMapwsLogin = this.wsLoginCollaborazioni(simogWS, simogwsuser,
          simogwspass);

      // Gestione e memorizzazione della lista delle collaborazioni
      ResponseCheckLogin responseCheckLogin = ((ResponseCheckLogin) hMapwsLogin.get("responseCheckLogin"));
      if (responseCheckLogin.getColl() != null) {
        if (responseCheckLogin.getColl().getCollaborazioniArray() != null) {
          Collaborazione[] arrayOfCollaborazione = responseCheckLogin.getColl().getCollaborazioniArray();
          if (arrayOfCollaborazione != null && arrayOfCollaborazione.length > 0) {

            // Elimino le associazioni alle collaborazioni di archivio
            sqlManager.update("delete from w3usrsyscoll where syscon = ? and rup_codtec = ?",
                new Object[] { syscon, codrup });

            for (int i = 0; i < arrayOfCollaborazione.length; i++) {
              String index = arrayOfCollaborazione[i].getIndex();
              String ufficio_id = arrayOfCollaborazione[i].getUfficioId();
              String ufficio_denominazione = arrayOfCollaborazione[i].getUfficioDenominazione();
              String ufficio_profilo = arrayOfCollaborazione[i].getUfficioProfilo();
              String azienda_codicefiscale = arrayOfCollaborazione[i].getAziendaCodiceFiscale();
              String azienda_denominazione = arrayOfCollaborazione[i].getAziendaDenominazione();

              // Verifico se esiste gia' una collaborazione nell'archivio
              // generale
              Long conteggioW3AZIENDAUFFICIO = (Long) sqlManager.getObject(
                  "select count(*) from w3aziendaufficio where indexcoll = ? and ufficio_id = ?",
                  new Object[] { index, ufficio_id });

              Long id = null;

              if (conteggioW3AZIENDAUFFICIO != null
                  && conteggioW3AZIENDAUFFICIO.longValue() == 0) {
                // E' necessario inserire una nuova riga nella lista delle
                // amministrazioni/stazioni appaltanti
                id = (Long) sqlManager.getObject(
                    "select max(id) from w3aziendaufficio", new Object[] {});
                if (id == null) id = new Long(0);
                id = new Long(id.longValue() + 1);
                sqlManager.update("insert into w3aziendaufficio (id, "
                    + "azienda_cf, "
                    + "azienda_denom, "
                    + "indexcoll, "
                    + "ufficio_denom, "
                    + "ufficio_id, "
                    + "ufficio_profilo) "
                    + "values (?,?,?,?,?,?,?)", new Object[] { id,
                    azienda_codicefiscale, azienda_denominazione, index,
                    ufficio_denominazione, ufficio_id, ufficio_profilo });
              } else {
                id = (Long) sqlManager.getObject(
                    "select id from w3aziendaufficio where indexcoll = ? and ufficio_id = ?",
                    new Object[] { index, ufficio_id });
                sqlManager.update("update w3aziendaufficio set azienda_cf = ?," +
                		"azienda_denom = ?," +
                		"ufficio_denom = ?," +
                		"ufficio_profilo = ?" + 
                		" where id = ?", new Object[] {
                        azienda_codicefiscale, azienda_denominazione,
                        ufficio_denominazione, ufficio_profilo, id });
              }

              // Aggiungo l'associazione nella tabella delle associazioni
              // W3USRSYSCOLL
              sqlManager.update(
                  "insert into w3usrsyscoll (syscon, rup_codtec, w3aziendaufficio_id) values (?,?,?)",
                  new Object[] { syscon, codrup, id });
            }
          }
        }
      }

    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli lista delle collaborazioni",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio",
          "gestioneIDGARACIG.ws.remote.error", e);
    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled())
      logger.debug("richiestaCollaborazioni: fine metodo");

    return idgara;

  }

  /**
   * Invio della richiesta di pubblicazione della gara e dei lotti associati
   *
   * @param simogwsuser
   * @param simogwspass
   * @param numgara
   * @throws GestoreException
   */
  public void pubblica(String simogwsuser, String simogwspass, Long numgara)
      throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("pubblica: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3GARA");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);

      String selectW3GARA = "select data_perfezionamento_bando, data_termine_pagamento, id_gara, tipo_operazione," +
      		" ora_scadenza, dscad_richiesta_invito, data_lettera_invito" +
      		" from w3gara where numgara = ?";
      List<?> datiW3GARA = this.sqlManager.getVector(selectW3GARA, new Object[] {numgara});

      if (datiW3GARA != null && datiW3GARA.size() > 0 ) {
        // ********** Data pubblicazione
        Date dataPubblicazione = (Date) SqlManager.getValueFromVectorParam(
            datiW3GARA, 0).getValue();
        String dataPubblicazioneS = UtilityDate.convertiData(dataPubblicazione, UtilityDate.FORMATO_AAAAMMGG);

        // ********** Data scadenza pagamenti
        Date dataScadenzaPagamenti = (Date) SqlManager.getValueFromVectorParam(
            datiW3GARA, 1).getValue();
        String dataScadenzaPagamentiS = UtilityDate.convertiData(dataScadenzaPagamenti, UtilityDate.FORMATO_AAAAMMGG);

        // IDGARA (CIG)
        String cig = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 2).getValue();

        // ********** Progressivo aggiudicazione
        String progCui = "";

        String tipoOperazione = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 3).getValue();

        String oraScadenza = (String) SqlManager.getValueFromVectorParam(datiW3GARA, 4).getValue();

        Date dataScadenzaRichiestaInvito = (Date) SqlManager.getValueFromVectorParam(datiW3GARA, 5).getValue();
        String dataScadenzaRichiestaInvitoS = "";
        if (dataScadenzaRichiestaInvito != null) {
          dataScadenzaRichiestaInvitoS = UtilityDate.convertiData(dataScadenzaRichiestaInvito, UtilityDate.FORMATO_AAAAMMGG);
        }

        Date dataLetteraInvito = (Date) SqlManager.getValueFromVectorParam(datiW3GARA, 6).getValue();
        String dataLetteraInvitoS = "";
        if (dataLetteraInvito != null) {
          dataLetteraInvitoS = UtilityDate.convertiData(dataLetteraInvito, UtilityDate.FORMATO_AAAAMMGG);
        }
        
        // verifico la scelta del contraente se la procedura è negoziata non devo inviare i dati della pubblicazione
        //Long tipoProcedura = (Long) this.sqlManager.getObject("SELECT ID_SCELTA_CONTRAENTE FROM W3LOTT WHERE NUMGARA = ? AND STATO_SIMOG IN (2,3,4)",  new Object[] {numgara});
        // Pubblicazione
        DatiPubblicazione datiPubblicazione = null;
        //if (!tipoProcedura.equals(new Long("4")) && !tipoProcedura.equals(new Long("10")) && !tipoProcedura.equals(new Long("14"))) {
        datiPubblicazione = this.gestioneXMLIDGARACIGManager.getDatiPubblicazione(numgara);
        //}
        // Documenti allegati
        AllegatoType[] documentiAllegati = null;
        documentiAllegati = this.gestioneXMLIDGARACIGManager.getDocumentiAllegati(numgara);

        // Cup lotti
        CUPLOTTOType[] cuplotti = null;
        cuplotti = this.gestioneXMLIDGARACIGManager.getCupLotti(numgara);


        this.wsPubblica(simogWS, hMapwsLogin, dataPubblicazioneS,
          dataScadenzaPagamentiS, cig, progCui, datiPubblicazione, tipoOperazione,
          documentiAllegati, oraScadenza, dataScadenzaRichiestaInvitoS, dataLetteraInvitoS, cuplotti);
      }

      this.aggiornaPubblicazione(numgara);

    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);
    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("pubblica: fine metodo");

  }

  /**
   * Invio della richiesta di modifica dei dati della gara successivamente alla
   * generazione del numero identificativo della gara
   *
   * @param simogwsuser
   * @param simogwspass
   * @param numgara
   * @throws GestoreException
   */
  public void modificaGARA(String simogwsuser, String simogwspass, Long numgara)
      throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("modificaGARA: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3GARA");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);

      String idgara = (String) this.sqlManager.getObject(
          "select id_gara from w3gara where numgara = ?",
          new Object[] { numgara });

      String ufficio_id = ((String) hMapwsLogin.get("ufficio_id"));
      String ufficio_denominazione = ((String) hMapwsLogin.get("ufficio_denominazione"));
      String azienda_codicefiscale = ((String) hMapwsLogin.get("azienda_codicefiscale"));
      String azienda_denominazione = ((String) hMapwsLogin.get("azienda_denominazione"));

      GaraType gara = this.gestioneXMLIDGARACIGManager.getDatiGara(
          numgara, ufficio_id, ufficio_denominazione, azienda_codicefiscale,
          azienda_denominazione);

      ModificaGara.DatiGara datiGara = ModificaGara.DatiGara.Factory.newInstance();
      datiGara.setDatiGara(gara);
      
      this.wsModificaGara(simogWS, hMapwsLogin, idgara, datiGara);
      this.aggiornaW3GARASTATO(numgara, new Long(4));

    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("modificaGARA: fine metodo");

  }

  /**
   * Consultazione della gara e del lotto
   *
   * @param simogwsuser
   * @param simogwspass
   * @param cig
   * @throws GestoreException
   */
  public HashMap<String, Object> consultaGARALOTTO(String simogwsuser,
      String simogwspass, String cig, Long syscon, String codUffInt, String codrup) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("consultaGARALOTTO: inizio metodo");

    HashMap<String, Object> hMapConsultaGaraLotto = new HashMap<String, Object>();

    // Verifico esistenza di un lotto con il CIG indicato
    if (this.esisteCIG(cig)) {
      throw new GestoreException(
          "Il lotto identificato dal CIG indicato è già presente in base dati. Non è possibile procedere con il recupero dei dati.",
          "gestioneIDGARACIG.consultagaralotto.cigesistente");
    }

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();
    int validitaCig = this.controlloCIG(cig);
    if (validitaCig == -1) {
    	throw new GestoreException(
    	          "Il codice cig inserito non è valido",
    	          "gestioneIDGARACIG.consultagaralotto.cigesistente");
    }
    // Interrogazione del servizio di consultazione gara
    try {
      simogWS = this.getSimogWS();
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass);

      if (validitaCig == 0) {
    	  String datiGaraLottoXML = this.wsConsultaGara(simogWS, hMapwsLogin, cig);
    	  hMapConsultaGaraLotto = this.gestioneXMLIDGARACIGManager.inserisciGaraLottodaSIMOG(
    	          datiGaraLottoXML, cig, syscon, codUffInt, codrup);
      } else if (validitaCig == 1) {
    	  Services simogSmartCigWS = this.getSimogWSSmartCig();
    	  ComunicazioneType comunicazione = this.wsConsultaSmartCig(simogSmartCigWS, hMapwsLogin, cig, syscon, codrup);
    	  hMapConsultaGaraLotto = this.gestioneXMLIDGARACIGManager.inserisciSMARTCIGdaSIMOG(
    			  comunicazione, cig, syscon, codUffInt, codrup, hMapwsLogin);
      }
    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante la consultazione della gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di consultazione della gara",
          "gestioneIDGARACIG.ws.remote.error", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled())
      logger.debug("consultaGARALOTTO: fine metodo");

    return hMapConsultaGaraLotto;

  }

  /**
   * Restituisce la validita' del CIG
   *
   * @param codiceCIG cig
   * @return validita'(-1:cig non valido, 0:cig, 1:smartcig)
   */
  private int controlloCIG(final String codiceCIG) {

	    String strC1_7 = "";// primi 7 caratteri
	    String strC4_10 = "";// dal 4 al 10 carattere
	    String strK = ""; //Firma
	    long nDecStrK_chk = 0;//Firma decimale
	    long nDecStrK = 0;//controllo della firma decimale
	    int result = -1;

	    if ("".equals(codiceCIG) || codiceCIG.length() != 10 || "0000000000".equals(codiceCIG)) {
	      //Errori di struttura
	      return result;
	    }
	    //Verifico se si tratta di cig o smart cig
	    String strC1 = "" + codiceCIG.charAt(0); //Estraggo il primo carattere
	    if(StringUtils.isNumeric(strC1)){

	    //CIG
	      try {
	        strK = codiceCIG.substring(7,10); //Estraggo la firma
	        nDecStrK = Integer.parseInt (strK, 16); //trasformo in decimale
	        strC1_7 = codiceCIG.substring(0,7); //Estraggo la parte significativa
	        long nStrC1 = Integer.parseInt(strC1_7);
	         //Calcola Firma
	        nDecStrK_chk = ((nStrC1 * 1/1) * 211 % 4091);
	        result = 0;
	      }catch(Exception e){
	          //Impossibile calcolare la firma
	          return result;
	      }

	    }else{

	      //SMART CIG
	      if(!strC1.equals("X") && !strC1.equals("Z") && !strC1.equals("Y")){
	        return result;
	      }
	      try {
	        strK=codiceCIG.substring(1,3);//Estraggo la firma
	        nDecStrK = Integer.parseInt (strK, 16); //trasformo in decimale
	        strC4_10 = codiceCIG.substring(3,10);
	        long nDecStrC4_10 = Integer.parseInt (strC4_10, 16); //trasformo in decimale
	        //Calcola Firma
	        nDecStrK_chk = ((nDecStrC4_10 * 1/1) * 211 % 251);
	        result = 1;
	      }catch(Exception e){
	        //Impossibile calcolare la firma
	        return result;
	      }
	    }

	    if (nDecStrK_chk != nDecStrK) {
	      //La firma non coincide
	      return result;
	    }

	    return result;

	  }
  
  /**
   * Richiesta di cancellazione della gara già assegnata
   *
   * @param simogwsuser
   * @param simogwspass
   * @param numgara
   * @param idmotivazione
   * @param notecanc
   * @throws GestoreException
   */
  public void cancellaGARA(String simogwsuser, String simogwspass,
      Long numgara, String idmotivazione, String notecanc)
      throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("cancellaGARA: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3GARA");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);

      String idgara = (String) this.sqlManager.getObject(
          "select id_gara from w3gara where numgara = ?",
          new Object[] { numgara });
      this.wsCancellaGara(simogWS, hMapwsLogin, idgara, idmotivazione, notecanc);
      this.aggiornaW3GARASTATO(numgara, new Long(6));
      this.aggiornaW3GARACancellazione(numgara, idmotivazione, notecanc);

    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("cancellaGARA: fine metodo");

  }

  /**
   * Aggiornamento dell'identificativo IDGARA in base dati
   *
   * @param numgara
   * @param idgara
   * @throws GestoreException
   */
  public void aggiornaW3GARACIG(Long numgara, String idgara)
      throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3GARACIG: inizio metodo");

    try {
      String updateW3GARA = "update w3gara set id_gara = ?, data_creazione = ?, stato_simog = ? where numgara = ?";
      this.sqlManager.update(updateW3GARA, new Object[] { idgara,
          new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
          new Long(2), numgara });

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3GARACIG: fine metodo");

  }
  
  public void aggiornaW3GARACIGMassivo(Long numgara, String idgara)
  throws GestoreException {

if (logger.isDebugEnabled())
  logger.debug("aggiornaW3GARACIG: inizio metodo");
TransactionStatus status = null;
try {
	status = this.sqlManager.startTransaction();
  String updateW3GARA = "update w3gara set id_gara = ?, data_creazione = ?, stato_simog = ? where numgara = ?";
  this.sqlManager.update(updateW3GARA, new Object[] { idgara,
      new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
      new Long(2), numgara });
  

} catch (SQLException e) {
  throw new GestoreException(
      "Si e' verificato un errore durante l'interazione con la base dati",
      "gestioneIDGARACIG.sqlerror", e);
} finally {
	try {
		this.sqlManager.commitTransaction(status);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

if (logger.isDebugEnabled())
  logger.debug("aggiornaW3GARACIG: fine metodo");

}
  
  /**
   * Aggiornamento dell'identificativo SMARTCIG in base dati
   *
   * @param numgara
   * @param smartCig
   * @throws GestoreException
   */
  public void aggiornaW3SMARTCIG(Long numgara, String smartCig)
      throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3SMARTCIG: inizio metodo");

    try {
      String updateW3GARA = "update W3SMARTCIG set CIG = ?, DATA_OPERAZIONE = ?, STATO = ? where CODRICH = ?";
      this.sqlManager.update(updateW3GARA, new Object[] { smartCig,
          new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
          new Long(2), numgara });

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3SMARTCIG: fine metodo");

  }
  
  /**
   * Aggiornamento dello stato SMARTCIG in base dati a seguito dell'annullamento
   *
   * @param numgara
   * @param smartCig
   * @throws GestoreException
   */
  public void annullaW3SMARTCIG(Long numgara)
      throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("annullaW3SMARTCIG: inizio metodo");

    try {
      String updateW3GARA = "update W3SMARTCIG set STATO = ?, CIG = null, DATA_OPERAZIONE = null where CODRICH = ?";
      this.sqlManager.update(updateW3GARA, new Object[] {new Long(1), numgara });

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("annullaW3SMARTCIG: fine metodo");

  }
  /**
   * Aggiornamento dello stato della gara
   *
   * @param numgara
   * @param stato
   * @throws GestoreException
   */
  public void aggiornaW3GARASTATO(Long numgara, Long stato)
      throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3GARASTATO: inizio metodo");

    try {
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
    	Long numero_lotti = (Long) this.sqlManager.getObject("select count(*) from w3lott where numgara = ? and stato_simog in (1,2,3,4,7,99)", new Object[] { numgara });
        
    	String updateW3GARA = "update w3gara set stato_simog = ?, importo_gara = ?, numero_lotti = ? where numgara = ?";
    	this.sqlManager.update(updateW3GARA, new Object[] { stato, importo, numero_lotti, numgara });

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3GARASTATO: fine metodo");

  }

  /**
   * Aggiornamento dei motivi della cancellazione per la gara
   *
   * @param numgara
   * @param idmotivazione
   * @param notecanc
   * @throws GestoreException
   */
  public void aggiornaW3GARACancellazione(Long numgara, String idmotivazione,
      String notecanc) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3GARACancellazione: inizio metodo");

    try {
      String updateW3GARA = "update w3gara set id_motivazione = ?, note_canc = ?, data_cancellazione_gara = ? where numgara = ?";
      this.sqlManager.update(updateW3GARA,
          new Object[] { idmotivazione, notecanc,
              new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
              numgara });

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3GARACancellazione: fine metodo");

  }


  /**
   * Verifica l'esistenza di un lotto con un determinato CIG
   *
   * @param cig
   * @return
   * @throws GestoreException
   */
  public boolean esisteCIG(String cig) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("esisteCIG: inizio metodo");

    boolean esisteCIG = false;

    try {
    	Long conteggioCig = null;
    	Long conteggioSmartCig = null;
    	if (cig != null && !cig.equals("")) {
    		conteggioCig = (Long) this.sqlManager.getObject(
    		          "select count(*) from w3lott where cig = ?", new Object[] { cig });
    		conteggioSmartCig = (Long) this.sqlManager.getObject(
  		          "select count(*) from w3smartcig where cig = ?", new Object[] { cig });
    	} 
    	if ((conteggioCig != null && conteggioCig.longValue() > 0) || 
    		(conteggioSmartCig != null && conteggioSmartCig.longValue() > 0)) esisteCIG = true;

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled()) logger.debug("esisteCIG: fine metodo");

    return esisteCIG;

  }

  /**
   * Verifica l'esistenza di un terminato numero gara IDGARA
   *
   * @param idgara
   * @return
   * @throws GestoreException
   */
  public boolean esisteIDGARA(String idgara) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("esisteIDGARA: inizio metodo");

    boolean esisteIDGARA = false;

    try {
      Long conteggio = (Long) this.sqlManager.getObject(
          "select count(*) from w3gara where id_gara = ?",
          new Object[] { idgara });
      if (conteggio != null && conteggio.longValue() > 0) esisteIDGARA = true;

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled()) logger.debug("esisteIDGARA: fine metodo");

    return esisteIDGARA;

  }

  /**
   * Ricava l'indice della collaborazione associato alla gara o allo smartcig
   *
   * @param numgara
   * @param entita
   * @return
   * @throws GestoreException
   */
  public String getIndiceCollaborazione(Long numgara, String entita) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("getIndiceCollaborazione: inizio metodo");

    String index = null;
    try {
    	if (entita.equals("W3GARA")) {
    	      index = (String) sqlManager.getObject(
    	              "select w3aziendaufficio.indexcoll from w3aziendaufficio, w3gara where w3aziendaufficio.id = w3gara.collaborazione and w3gara.numgara = ?",
    	              new Object[] { numgara });
    	} else if (entita.equals("W3SMARTCIG")) {
    	      index = (String) sqlManager.getObject(
    	              "select w3aziendaufficio.indexcoll from w3aziendaufficio, W3SMARTCIG where w3aziendaufficio.id = W3SMARTCIG.collaborazione and W3SMARTCIG.CODRICH = ?",
    	              new Object[] { numgara });
    	}
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore durante la lettura dell'indice della collaborazione associata alla gara",
          "controlloW3GARA", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("getIndiceCollaborazione: inizio metodo");

    return index;
  }

  /**
   * Aggiornamento dell'identificativo CIG in base dati
   *
   * @param numgara
   * @param numlott
   * @param HashMap<String, Object>
   * @throws GestoreException
   */
  public void aggiornaW3LOTTCIG(Long numgara, Long numlott, HashMap<String, Object> hMapwsLotto)
      throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3LOTTCIG: inizio metodo");

    String cig = ((String) hMapwsLotto.get("cig"));
    CUPLOTTOType cuplotto = (CUPLOTTOType) ( hMapwsLotto.get("cuplotto"));

    try {
      if(cig!=null){
        String updateW3LOTT = "update w3lott set cig = ?, data_creazione_lotto = ?, stato_simog = ? where numgara = ? and numlott = ?";
        this.sqlManager.update(updateW3LOTT, new Object[] { cig,
            new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
            new Long(2), numgara, numlott });
      }
      if(cuplotto!=null && cuplotto.getCODICICUPArray() !=null){
    	  DatiCUPType[] codiciCup = cuplotto.getCODICICUPArray();
    	  if (codiciCup != null && codiciCup.length > 0) {
    		  String updateW3LOTTCUP = "update w3lottcup set dati_dipe = ? where numgara = ? and numlott = ? and cup = ? ";
    		  for (int i = 0; i < codiciCup.length; i++) {
    			  String cup = codiciCup[i].getCUP();
    			  String dati_dipe = codiciCup[i].getDATIDIPE();
    			  this.sqlManager.update(updateW3LOTTCUP, new Object[] {dati_dipe, numgara, numlott, cup });
    		  }
    	  }
      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3LOTTCIG: fine metodo");

  }

  public void aggiornaW3LOTTCIGMassivo(Long numgara, Long numlott, HashMap<String, Object> hMapwsLotto)
  throws GestoreException {

if (logger.isDebugEnabled())
  logger.debug("aggiornaW3LOTTCIG: inizio metodo");

String cig = ((String) hMapwsLotto.get("cig"));
CUPLOTTOType cuplotto = (CUPLOTTOType) ( hMapwsLotto.get("cuplotto"));
TransactionStatus status = null;
try {
	status = this.sqlManager.startTransaction();
  if(cig!=null){
    String updateW3LOTT = "update w3lott set cig = ?, data_creazione_lotto = ?, stato_simog = ? where numgara = ? and numlott = ?";
    this.sqlManager.update(updateW3LOTT, new Object[] { cig,
        new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
        new Long(2), numgara, numlott });
  }
  if(cuplotto!=null && cuplotto.getCODICICUPArray()!=null){
	  DatiCUPType[] codiciCup = cuplotto.getCODICICUPArray();
	  if (codiciCup != null && codiciCup.length > 0) {
		  String updateW3LOTTCUP = "update w3lottcup set dati_dipe = ? where numgara = ? and numlott = ? and cup = ? ";
		  for (int i = 0; i < codiciCup.length; i++) {
			  String cup = codiciCup[i].getCUP();
			  String dati_dipe = codiciCup[i].getDATIDIPE();
			  this.sqlManager.update(updateW3LOTTCUP, new Object[] {dati_dipe, numgara, numlott, cup });
		  }
	  }
  }
} catch (SQLException e) {
  throw new GestoreException(
      "Si e' verificato un errore durante l'interazione con la base dati",
      "gestioneIDGARACIG.sqlerror", e);
}
finally {
	try {
		this.sqlManager.commitTransaction(status);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
if (logger.isDebugEnabled())
  logger.debug("aggiornaW3LOTTCIG: fine metodo");

}
  
  /**
   * Aggiornamento dello stato del lotto
   *
   * @param numgara
   * @param numlott
   * @param stato
   * @throws GestoreException
   */
  public void aggiornaW3LOTTSTATO(Long numgara, Long numlott, Long stato)
      throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3LOTTSTATO: inizio metodo");

    try {
      String updateW3LOTT = "update w3lott set stato_simog = ? where numgara = ? and numlott = ?";
      this.sqlManager.update(updateW3LOTT, new Object[] { stato, numgara,
          numlott });

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3LOTTSTATO: fine metodo");

  }

  /**
   * Aggiorna stato e data di pubblicazione della gara e dei lotti a fronte
   * della pubblicazione SIMOG
   *
   * @param numgara
   * @throws GestoreException
   */
  public void aggiornaPubblicazione(Long numgara) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("aggiornaPubblicazione: inizio metodo");

    try {

      Timestamp dataOdierna = new Timestamp(
          UtilityDate.getDataOdiernaAsDate().getTime());

      String updateW3GARA = "update w3gara set stato_simog = ?, data_conferma_gara = ? where numgara = ?";
      this.sqlManager.update(updateW3GARA, new Object[] { new Long(7),
          dataOdierna, numgara });

      Date data_perfezionamento_bando = (Date) this.sqlManager.getObject("select data_perfezionamento_bando from w3gara where numgara = ?", new Object[] {numgara});
      String updateW3LOTT = "update w3lott set stato_simog = ?, data_pubblicazione = ? where numgara = ? and stato_simog in (2,4)";
      this.sqlManager.update(updateW3LOTT, new Object[] { new Long(7),
          data_perfezionamento_bando, numgara });

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("aggiornaPubblicazione: fine metodo");

  }

  public void aggiornaPubblicazioneMassivo(Long numgara) throws GestoreException {

	    if (logger.isDebugEnabled())
	      logger.debug("aggiornaPubblicazione: inizio metodo");

	    TransactionStatus status = null;
	    try {
	    	status = this.sqlManager.startTransaction();
	      Timestamp dataOdierna = new Timestamp(
	          UtilityDate.getDataOdiernaAsDate().getTime());

	      String updateW3GARA = "update w3gara set stato_simog = ?, data_conferma_gara = ? where numgara = ?";
	      this.sqlManager.update(updateW3GARA, new Object[] { new Long(7),
	          dataOdierna, numgara });

	      Date data_perfezionamento_bando = (Date) this.sqlManager.getObject("select data_perfezionamento_bando from w3gara where numgara = ?", new Object[] {numgara});
	      String updateW3LOTT = "update w3lott set stato_simog = ?, data_pubblicazione = ? where numgara = ? and stato_simog in (2,4)";
	      this.sqlManager.update(updateW3LOTT, new Object[] { new Long(7),
	          data_perfezionamento_bando, numgara });

	    } catch (SQLException e) {
	      throw new GestoreException(
	          "Si e' verificato un errore durante l'interazione con la base dati",
	          "gestioneIDGARACIG.sqlerror", e);
	    } finally {
	    	try {
				this.sqlManager.commitTransaction(status);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    if (logger.isDebugEnabled())
	      logger.debug("aggiornaPubblicazione: fine metodo");

	  }
  
  /**
   * Aggiornamento dei motivi della cancellazione del lotto
   *
   * @param numgara
   * @param numlott
   * @param idmotivazione
   * @param notecanc
   * @throws GestoreException
   */
  public void aggiornaW3LOTTCancellazione(Long numgara, Long numlott,
      String idmotivazione, String notecanc) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3LOTTCancellazione: inizio metodo");

    try {
      String updateW3LOTT = "update w3lott set id_motivazione = ?, note_canc = ?, data_cancellazione_lotto = ? where numgara = ? and numlott = ?";
      this.sqlManager.update(updateW3LOTT, new Object[] { idmotivazione,
          notecanc,
          new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()), numgara,
          numlott });

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("aggiornaW3LOTTCancellazione: fine metodo");

  }

  /**
   * Gestione dei requisiti per la gara
   *
   * @param numgara
   * @throws GestoreException
   */
  public void gestioneRequisiti(Long numgara) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("gestioneRequisiti: inizio metodo");

    boolean result = false;
    TransactionStatus status = null;
    try {
    	// Ricavo l'importo totale dei lotti con stato 2 
    	Double importo = new Double(0);
    	/*List<?> datiW3LOTT = this.sqlManager.getListVector(
		          "select IMPORTO_LOTTO from W3LOTT where NUMGARA = ? and CIG IS NOT NULL",
		          new Object[] {numgara });
    	if (datiW3LOTT != null && datiW3LOTT.size() > 0) {
    		for (int i = 0; i < datiW3LOTT.size(); i++) {
    			Double importo_lotto = (Double) SqlManager.getValueFromVectorParam(datiW3LOTT.get(i), 0).getValue();
    			if (importo_lotto != null) {
        			importo += importo_lotto;	
    			}
    		}
    	}*/
    	status = this.sqlManager.startTransaction();
    	//Controllo AVCPASS
    	String avcpass = (String)sqlManager.getObject("select ESCLUSO_AVCPASS from W3GARA where NUMGARA = ?", new Object[] { numgara });
    	if (avcpass != null && !avcpass.equals("1")) {
    		//Verifico se i requisiti sono già stati creati
        	Long numeroRequisiti = (Long)sqlManager.getObject(
    	              "select COUNT(*) from W3GARAREQ where NUMGARA = ?",
    	              new Object[] { numgara });
        	if (numeroRequisiti.equals(new Long(0))) {
        		//se i requisiti non sono stati ancora inseriti allora li inserisco
        		List<?> datiW3TABREQ = this.sqlManager.getListVector(
        		          "select CODICE_DETTAGLIO, DESCRIZIONE, DOCUMENTI_DEFAULT from W3TABREQ where INIZIALE = '1'",
        		          new Object[] { });
        		if (datiW3TABREQ != null && datiW3TABREQ.size() > 0) {
        		    for (int i = 0; i < datiW3TABREQ.size(); i++) {
        		          String codice = (String) SqlManager.getValueFromVectorParam(
        		        		  datiW3TABREQ.get(i), 0).getValue();
        		          String descrizione = (String) SqlManager.getValueFromVectorParam(
        		        		  datiW3TABREQ.get(i), 1).getValue();
        		          String documenti = (String) SqlManager.getValueFromVectorParam(
        		        		  datiW3TABREQ.get(i), 2).getValue();
        		          int indexDescrizione = descrizione.indexOf("-");
        		          if (indexDescrizione != -1) {
        		        	  descrizione = descrizione.substring(indexDescrizione + 2);
        		          }
        		          if (descrizione.length() > 80) {
        		        	  descrizione = descrizione.substring(0,80);
        		          }
        		          Long numreq = new Long(i+1);
        		          this.sqlManager.update("insert into W3GARAREQ(NUMGARA, NUMREQ, CODICE_DETTAGLIO, DESCRIZIONE, FLAG_ESCLUSIONE, FLAG_COMPROVA_OFFERTA, FLAG_AVVALIMENTO, FLAG_BANDO_TIPO, FLAG_RISERVATEZZA) VALUES(?,?,?,?,?,?,?,?,?)", new Object[] {
        		        		  numgara, numreq, codice, descrizione, "2", "2", "2", "2", "2" });
        		          if (documenti != null && !documenti.trim().equals("")) {
        		        	  String[] listaDocumenti = documenti.split("-");
        		        	  int j = 1;
        		        	  for(String tipoDocumento:listaDocumenti) {
        		        		  String descrizioneDoc = (String)sqlManager.getObject(
        		        	              "select TAB1DESC from TAB1 where TAB1COD = 'W3029' and TAB1TIP = ?",
        		        	              new Object[] { new Long(tipoDocumento) });
        		        		  if (descrizioneDoc != null) {
        		        			  this.sqlManager.update("insert into W3GARAREQDOC(NUMGARA, NUMREQ, NUMDOC, CODICE_TIPO_DOC, DESCRIZIONE, EMETTITORE, FAX, TELEFONO, MAIL, MAIL_PEC) VALUES(?,?,?,?,?,?,?,?,?,?)", new Object[] {
                    		        		  numgara, numreq, new Long(j), new Long(tipoDocumento), descrizioneDoc, "-", "0", "0", "-", "-" });
            		        		  j++;
        		        		  }
        		        	  }
        		          }
        		     }
        		}
        	}
        	result = true;
    	} else {
    		//se importo è inferiore a 40000 elimino i requisiti
    		this.sqlManager.update("delete from W3GARAREQDOC where NUMGARA = ?", new Object[] {numgara});
    		this.sqlManager.update("delete from W3GARAREQ where NUMGARA = ?", new Object[] {numgara});
    		result = true;
    	}
    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    } finally {
    	if (status != null) {
			try {
				if (result) {
					this.sqlManager.commitTransaction(status);
				} else {
					this.sqlManager.rollbackTransaction(status);
				}
			} catch (SQLException ex) {
				throw new GestoreException(
				          "Si e' verificato un errore durante l'interazione con la base dati",
				          "gestioneIDGARACIG.sqlerror", ex);
			}
		}
    }

    if (logger.isDebugEnabled())
      logger.debug("gestioneRequisiti: fine metodo");

  }
  
  /**
   * Invio della richiesta di generazione del codice CIG per un lotto
   *
   * @param simogwsuser
   * @param simogwspass
   * @param numgara
   * @param numlott
   * @return
   * @throws GestoreException
   */
  public String richiestaCIG(String simogwsuser, String simogwspass,
      Long numgara, Long numlott) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("richiestaCIG: inizio metodo");

    String cig = null;

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3GARA");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);
      this.AggiornaImportoNumeroLottiGara(numgara,hMapwsLogin,simogWS);
      String idgara = (String) this.sqlManager.getObject(
          "select id_gara from w3gara where numgara = ?",
          new Object[] { numgara });

      // Ricavo il contenuto XML
      LottoType lotto = this.gestioneXMLIDGARACIGManager.getDatiLotto(numgara, numlott);

      DatiLotto datiLotto = DatiLotto.Factory.newInstance();
      datiLotto.setLotto(lotto);
      
      HashMap<String, Object> hMapwsInserisciLotto = this.wsInserisciLotto(simogWS, hMapwsLogin, datiLotto, idgara);
      this.aggiornaW3LOTTCIG(numgara, numlott, hMapwsInserisciLotto );
      //this.gestioneRequisiti(numgara);
      cig = ((String) hMapwsInserisciLotto.get("cig"));
      
    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("richiestaCIG: fine metodo");

    return cig;

  }

  /**
   * Invio della richiesta di modifica per un lotto con CIG già assegnato
   *
   * @param simogwsuser
   * @param simogwspass
   * @param numgara
   * @param numlott
   * @throws GestoreException
   */
  public void modificaLOTTO(String simogwsuser, String simogwspass,
      Long numgara, Long numlott) throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("modificaLOTTO: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3GARA");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);
      this.AggiornaImportoNumeroLottiGara(numgara,hMapwsLogin,simogWS);
      String cig = (String) this.sqlManager.getObject(
          "select cig from w3lott where numgara = ? and numlott = ?",
          new Object[] { numgara, numlott });

      LottoType lotto = this.gestioneXMLIDGARACIGManager.getDatiLotto(
          numgara, numlott);

      ModificaLotto.DatiLotto datiLotto = ModificaLotto.DatiLotto.Factory.newInstance();
      datiLotto.setLotto(lotto);
      
      HashMap<String, Object> hMapwsModificaLotto =  this.wsModificaLotto(simogWS, hMapwsLogin, datiLotto, cig);
      this.aggiornaW3LOTTCIG(numgara, numlott, hMapwsModificaLotto);
      this.aggiornaW3LOTTSTATO(numgara, numlott, new Long(4));
      //this.gestioneRequisiti(numgara);
      
    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("richiestaLOTTO: fine metodo");

  }

  /**
   * Invio della richiesta di cancellazione di un lotto
   *
   * @param simogwsuser
   * @param simogwspass
   * @param numgara
   * @param numlott
   * @throws GestoreException
   */
  public void cancellaLOTTO(String simogwsuser, String simogwspass,
      Long numgara, Long numlott, String idmotivazione, String notecanc)
      throws GestoreException {

    if (logger.isDebugEnabled()) logger.debug("cancellaLOTTO: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3GARA");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);

      String cig = (String) this.sqlManager.getObject(
          "select cig from w3lott where numgara = ? and numlott = ?",
          new Object[] { numgara, numlott });
      this.wsCancellaLotto(simogWS, hMapwsLogin, cig, idmotivazione, notecanc);
      this.aggiornaW3LOTTSTATO(numgara, numlott, new Long(6));
      this.aggiornaW3LOTTCancellazione(numgara, numlott, idmotivazione,
          notecanc);
      //this.AggiornaImportoNumeroLottiGara(numgara,hMapwsLogin,simogWS);
      this.aggiornaW3GARASTATO(numgara, new Long(3));
    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);

    } catch (SQLException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con la base dati",
          "gestioneIDGARACIG.sqlerror", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("cancellaLOTTO: fine metodo");

  }

  /**
   * Invio della richiesta di annullamento SMARTCIG
   *
   * @param numgara
   * @throws GestoreException
   */
  public void cancellaSMARTCIG(String simogwsuser, String simogwspass,
      Long numgara) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("cancellaSMARTCIG: inizio metodo");

    SimogWSPDDServiceStub simogWS = null;
    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    try {
      simogWS = this.getSimogWS();
      String index = this.getIndiceCollaborazione(numgara, "W3SMARTCIG");
      hMapwsLogin = this.wsLogin(simogWS, simogwsuser, simogwspass, index);
      // Creazione della Request comunicazione 
      String ticket = ((String) hMapwsLogin.get("ticket"));

      AnnullaComunicazioneRequest comunicazioneRequest = this.gestioneXMLIDGARACIGManager.getAnnullaComunicazioneSmartCig(numgara, index, ticket);

      Services simogSmartCigWS = this.getSimogWSSmartCig();
      
      this.wsAnnullaComunicazione(simogSmartCigWS, comunicazioneRequest);
      this.annullaW3SMARTCIG(numgara);

    } catch (GestoreException e) {
      throw e;

    } catch (ServiceException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.error", e);

    } catch (RemoteException e) {
      throw new GestoreException(
          "Si e' verificato un errore durante l'interazione con il servizio di elaborazione degli identificativi di gara",
          "gestioneIDGARACIG.ws.remote.error", e);
    } finally {
      this.wsChiudiSessione(simogWS, hMapwsLogin);
    }

    if (logger.isDebugEnabled()) logger.debug("cancellaSMARTCIG: fine metodo");
  }
  
  /**
   * Procedura che aggiorna in simog l'importo gara e il numero lotti ogni volta che viene 
   * fatta una modifica in simog sui lotti
   *
   * @param numgara
   * @param hMapwsLogin
   * @param simogWS
   * @return
   */
  private void AggiornaImportoNumeroLottiGara(Long numgara, HashMap<String, Object> hMapwsLogin, SimogWSPDDServiceStub simogWS) {
	  try {
		  String idgara = (String) this.sqlManager.getObject(
	              "select id_gara from w3gara where numgara = ?",
	              new Object[] { numgara });
	      
	      String ufficio_id = ((String) hMapwsLogin.get("ufficio_id"));
	      String ufficio_denominazione = ((String) hMapwsLogin.get("ufficio_denominazione"));
	      String azienda_codicefiscale = ((String) hMapwsLogin.get("azienda_codicefiscale"));
	      String azienda_denominazione = ((String) hMapwsLogin.get("azienda_denominazione"));

	      GaraType gara = this.gestioneXMLIDGARACIGManager.getDatiGara(
	              numgara, ufficio_id, ufficio_denominazione, azienda_codicefiscale,
	              azienda_denominazione);

	      ModificaGara.DatiGara datiGara = ModificaGara.DatiGara.Factory.newInstance();
	      datiGara.setDatiGara(gara);
	          
	      this.wsModificaGara(simogWS, hMapwsLogin, idgara, datiGara);
	      this.aggiornaW3GARASTATO(numgara, new Long(4));
	  } catch (Exception ex) {
		  logger.error("AggiornaImportoNumeroLottiGara : si è verificato un errore imprevito",ex);
	  }
  }
  
  /**
   * Chiamata al metodo login del WS SIMOG per ricavare l'identificativo di
   * sessione (ticket) e la lista delle collaborazioni Utilizzata solamente per
   * ricava la lista delle collaborazioni
   *
   * @param simogWS
   * @param login
   * @param password
   * @return
   * @throws ServiceException
   * @throws GestoreException
   * @throws RemoteException
   */
  private HashMap<String, Object> wsLoginCollaborazioni(SimogWSPDDServiceStub simogWS,
      String login, String password) throws ServiceException, GestoreException,
      RemoteException {

    if (logger.isDebugEnabled())
      logger.debug("wsLoginCollaborazioni: inizio metodo");

    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    LoginDocument loginDoc = LoginDocument.Factory.newInstance();
    Login loginIn = Login.Factory.newInstance();
    loginIn.setLogin(login);
    loginIn.setPassword(password);
    loginDoc.setLogin(loginIn);
    
    LoginResponseDocument loginResponseDoc = simogWS.login(loginDoc);
    
    LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
    
    ResponseCheckLogin responseCheckLogin = null;

    String error = null;
    if (loginResponse.isSetReturn()) {
    	responseCheckLogin = loginResponse.getReturn();
    	if (responseCheckLogin.getSuccess()) {
    		// Ticket della sessione
    		String ticket = responseCheckLogin.getTicket();
    	    hMapwsLogin.put("ticket", ticket);
    	    hMapwsLogin.put("responseCheckLogin", responseCheckLogin);
    	} else {
    		error = responseCheckLogin.getError();
    	}
    } else {
    	error = "Errore imprevisto nel servizio di login";
    }
    
    if (error != null) {
    	logger.error("Il servizio di login ha risposto con il seguente messaggio: "
    	          + error);
    	      throw new GestoreException(
    	          "Il servizio di login ha risposto con il seguente messaggio: "
    	              + error, "gestioneIDGARACIG.ws.login.error",
    	          new Object[] { error }, null);
    }

    if (logger.isDebugEnabled())
      logger.debug("wsLoginCollaborazioni: fine metodo");

    return hMapwsLogin;
  }

  /**
   * Chiamata al metodo login del WS SIMOG per ricavare l'identificativo di
   * sessione (ticket) e la lista delle collaborazioni In questo caso viene
   * indicato anche l'indice della collaborazione e controllato che tale indice
   * sia ancora disponibile nella lista della collaborazioni SIMOG
   *
   * @param simogWS
   * @param login
   * @param password
   * @param index
   * @return
   * @throws ServiceException
   * @throws GestoreException
   * @throws RemoteException
   */
  private HashMap<String, Object> wsLogin(SimogWSPDDServiceStub simogWS, String login,
      String password, String index) throws ServiceException, GestoreException,
      RemoteException {

    if (logger.isDebugEnabled()) logger.debug("wsLogin: inizio metodo");

    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    LoginDocument loginDoc = LoginDocument.Factory.newInstance();
    Login loginIn = Login.Factory.newInstance();
    loginIn.setLogin(login);
    loginIn.setPassword(password);
    loginDoc.setLogin(loginIn);

    LoginResponseDocument loginResponseDoc = simogWS.login(loginDoc);
    
    LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
    
    ResponseCheckLogin responseCheckLogin = null;
    
    if (loginResponse.isSetReturn() && loginResponse.getReturn().getSuccess()) {
    	responseCheckLogin = loginResponse.getReturn();
    	String ticket = responseCheckLogin.getTicket();
    	hMapwsLogin.put("ticket", ticket);

    	// Controllo se l'indice della collaborazione indicato esiste
    	// tra quelli ammessi da SIMOG
    	boolean indiceAmmesso = false;
    	if (index != null && !("".equals(index))) {
    		if (responseCheckLogin.getColl() != null) {
    			if (responseCheckLogin.getColl().getCollaborazioniArray() != null) {
    				Collaborazione[] arrayOfCollaborazione = responseCheckLogin.getColl().getCollaborazioniArray();
    				if (arrayOfCollaborazione != null
    						&& arrayOfCollaborazione.length > 0) {
    					for (int i = 0; i < arrayOfCollaborazione.length; i++) {
    						if (index.endsWith(arrayOfCollaborazione[i].getIndex())) {
    							indiceAmmesso = true;
    							hMapwsLogin.put("ufficio_id", arrayOfCollaborazione[i].getUfficioId());
    							hMapwsLogin.put("ufficio_denominazione", arrayOfCollaborazione[i].getUfficioDenominazione());
    							hMapwsLogin.put("azienda_codicefiscale", arrayOfCollaborazione[i].getAziendaCodiceFiscale());
    							hMapwsLogin.put("azienda_denominazione", arrayOfCollaborazione[i].getAziendaDenominazione());
    						}
    					}
    				}
    			}
        	}
    	}

    	if (!indiceAmmesso) {
        logger.error("L'indice della collaborazione indicato "
            + index
            + " non e' piu' presente nella lista degli indici ammessi dai servizi SIMOG per l'utente connesso.");
        throw new GestoreException(
            "L'indice della collaborazione indicato "
                + index
                + " non e' presente nella lista degli indici ammessi dai servizi SIMOG per l'utente connesso.",
            "gestioneIDGARACIG.ws.login.indexNonAmmesso",
            new Object[] { index }, null);
    	}

    	hMapwsLogin.put("index", index);

    } else {
    	String messaggio = "Errore imprevisto nel servizio di login";
    	if (loginResponse.isSetReturn()) {
    		responseCheckLogin = loginResponse.getReturn();
    		messaggio = responseCheckLogin.getError();
    	}
      
      logger.error("Il servizio di login ha risposto con il seguente messaggio: "
          + messaggio);
      throw new GestoreException(
          "Il servizio di login ha risposto con il seguente messaggio: "
              + messaggio, "gestioneIDGARACIG.ws.login.error",
          new Object[] { messaggio }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsLogin: fine metodo");

    return hMapwsLogin;
  }

  /**
   * Chiamata al metodo login del WS SIMOG per ricavare l'identificativo di
   * sessione (ticket). Non gestisce la lista delle collaborazioni e l'indice
   * della collaborazione. E' un metodo utilizzato dai metodi SIMOG che non
   * richiedono l'indicazione dell'index (consultaGara)
   *
   * @param simogWS
   * @param login
   * @param password
   * @return
   * @throws ServiceException
   * @throws GestoreException
   * @throws RemoteException
   */
  private HashMap<String, Object> wsLogin(SimogWSPDDServiceStub simogWS, String login,
      String password) throws ServiceException, GestoreException,
      RemoteException {

    if (logger.isDebugEnabled()) logger.debug("wsLogin: inizio metodo");

    HashMap<String, Object> hMapwsLogin = new HashMap<String, Object>();

    LoginDocument loginDoc = LoginDocument.Factory.newInstance();
    Login loginIn = Login.Factory.newInstance();
    loginIn.setLogin(login);
    loginIn.setPassword(password);
    loginDoc.setLogin(loginIn);
    
    LoginResponseDocument loginResponseDoc = simogWS.login(loginDoc);
    
    LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
    
    ResponseCheckLogin responseCheckLogin = null;
    
    String error = null;
    if (loginResponse.isSetReturn()) {
    	responseCheckLogin = loginResponse.getReturn();
    	if (responseCheckLogin.getSuccess()) {
    		// Ticket della sessione
    	    String ticket = responseCheckLogin.getTicket();
    	    hMapwsLogin.put("ticket", ticket);
    	    hMapwsLogin.put("collaborazioni", responseCheckLogin.getColl());
    	} else {
    		error = responseCheckLogin.getError();
    	}
    } else {
    	error = "Errore imprevisto nel servizio di login";
    }
    
    if (error != null) {
    	logger.error("Il servizio di login ha risposto con il seguente messaggio: "
    	          + error);
    	      throw new GestoreException(
    	          "Il servizio di login ha risposto con il seguente messaggio: "
    	              + error, "gestioneIDGARACIG.ws.login.error",
    	          new Object[] { error }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsLogin: fine metodo");

    return hMapwsLogin;
  }

  /**
   * Chiamata al metodo inserisciGara del WS SIMOG per ottenere il codice
   * identificativo gara (IDGARA)
   *
   * @param simogWS
   * @param hMapwsLogin
   * @param datiGaraXML
   * @return
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   */
  private String wsInserisciGara(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin, DatiGara datiGara)
      throws ServiceException, RemoteException, GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("wsInserisciGara: inizio metodo");

    String idgara = null;
    String ticket = ((String) hMapwsLogin.get("ticket"));
    String index = ((String) hMapwsLogin.get("index"));
    
    InserisciGaraDocument inserisciGaraDoc = InserisciGaraDocument.Factory.newInstance();
    InserisciGara inserisciGara = InserisciGara.Factory.newInstance();
    inserisciGara.setTicket(ticket);
    inserisciGara.setIndexCollaborazione(index);
    inserisciGara.setDatiGara(datiGara);
    inserisciGaraDoc.setInserisciGara(inserisciGara);
    InserisciGaraResponseDocument InserisciresponseGaraDoc = simogWS.inserisciGara(inserisciGaraDoc);
    InserisciGaraResponse responseInserisciGara = InserisciresponseGaraDoc.getInserisciGaraResponse();
    
    if (responseInserisciGara.isSetReturn() && responseInserisciGara.getReturn().getSuccess()) {
      idgara = responseInserisciGara.getReturn().getIdGara();
    } else {
    	String messaggio = "Errore imprevisto nel servizio di inserisciGara";
    	if (responseInserisciGara.isSetReturn()) {
    		messaggio = responseInserisciGara.getReturn().getError();
    	}
      
      logger.error("Il servizio di inserimento della gara ha risposto con il seguente messaggio: "
          + messaggio);
      throw new GestoreException(
          "Il servizio di inserimento della gara ha risposto con il seguente messaggio: "
              + messaggio, "gestioneIDGARACIG.ws.inseriscigara.error",
          new Object[] { messaggio }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsInserisciGara: fine metodo");

    return idgara;

  }

  /**
   * Chiamata al metodo comunicaSingola del WS SIMOG SMARTCIG per ottenere il codice SmartCig
   * identificativo gara (IDGARA)
   *
   * @param simogSmartCigWS
   * @param comunicazioneRequest
   * @return
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   */
  private String wsComunicaSingola(Services simogSmartCigWS, ComunicaSingolaRequest comunicazioneRequest)
      throws ServiceException, RemoteException, GestoreException, SocketTimeoutException {

    if (logger.isDebugEnabled())
      logger.debug("wsComunicaSingola: inizio metodo");

    String smartCig = null;
    
    ComunicaSingolaResponse responseComunicazione = simogSmartCigWS.comunicaSingola(comunicazioneRequest);
    smartCig = responseComunicazione.getCig();
    if (smartCig == null || smartCig.equals("")) {
    	ErroreType[] errori = responseComunicazione.getErrore();
    	String messaggio = "";
    	for (int i = 0; i < errori.length; i++) {
    		messaggio += errori[i].getErrore() + "\r\n";
    	}
    	logger.error("Il servizio di comunicazione singola ha risposto con il seguente messaggio: "
          + messaggio);
      throw new GestoreException(
          "Il servizio di comunicazione singola ha risposto con il seguente messaggio: "
              + messaggio, "gestioneIDGARACIG.ws.inseriscigara.error",
          new Object[] { messaggio }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsComunicaSingola: fine metodo");

    return smartCig;

  }
  
  /**
   * Chiamata al metodo AnnullaComunicazione del WS SIMOG SMARTCIG per annullare il codice SmartCig
   * identificativo gara (IDGARA)
   *
   * @param simogSmartCigWS
   * @param comunicazioneRequest
   * @return
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   */
  private void wsAnnullaComunicazione(Services simogSmartCigWS, AnnullaComunicazioneRequest comunicazioneRequest)
      throws ServiceException, RemoteException, GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("wsAnnullaComunicazione: inizio metodo");

    AnnullaComunicazioneResponse responseComunicazione = simogSmartCigWS.annullaComunicazione(comunicazioneRequest);
    RisultatoType result = responseComunicazione.getCodiceRisultato();
    if (!result.getCodice().equals(CodiceRisultatoType.COD_001)) {
    	//se codice di ritorno non è COD_001 OK
    	String messaggio = result.getIdTransazione() + " - "
    	+ result.getCodice().getValue() + " - "
    	+ result.getDescrizione().getValue();
    	
    	logger.error("Il servizio di annullacomunicazione ha risposto con il seguente messaggio: "
          + messaggio);
      throw new GestoreException(
          "Il servizio di annullacomunicazione ha risposto con il seguente messaggio: "
              + messaggio, "gestioneIDGARACIG.ws.cancellagara.error",
          new Object[] { messaggio }, null);
    }
    if (logger.isDebugEnabled()) logger.debug("wsAnnullaComunicazione: fine metodo");
  }
  
  /**
   * Chiamata al metodo modificaGara del WS SIMOG per comunicare le modifiche ai
   * dati di una gara identificata dal codice IDGARA
   *
   * @param simogWS
   * @param hMapwsLogin
   * @param numgara
   * @param idgara
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   */
  private void wsModificaGara(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin, String idgara, ModificaGara.DatiGara datiGara)
      throws ServiceException, RemoteException, GestoreException {

    if (logger.isDebugEnabled()) logger.debug("wsModificaGara: inizio metodo");

    String ticket = ((String) hMapwsLogin.get("ticket"));
    String index = ((String) hMapwsLogin.get("index"));

    ModificaGaraDocument modificaGaraDoc = ModificaGaraDocument.Factory.newInstance();
    ModificaGara modificaGara = ModificaGara.Factory.newInstance();
    modificaGara.setTicket(ticket);
    modificaGara.setIndexCollaborazione(index);
    modificaGara.setDatiGara(datiGara);
    modificaGara.setIdGara(idgara);
    modificaGaraDoc.setModificaGara(modificaGara);
    ModificaGaraResponseDocument modificaResponseGaraDoc = simogWS.modificaGara(modificaGaraDoc);
    ModificaGaraResponse responseModificaGara = modificaResponseGaraDoc.getModificaGaraResponse();
  
    String messaggio = null;
    if (responseModificaGara.isSetReturn()) {
    	ResponseModificaGara response = responseModificaGara.getReturn();
    	if (!response.getSuccess()) {
    		 messaggio = response.getError();
    	}
    } else {
    	messaggio = "Errore imprevisto nel servizio di modificaGara";
    }
    
    if (messaggio != null) {
    	logger.error("Il servizio di modifica dei dati della gara ha risposto con il seguente messaggio: "
  	          + messaggio);
  	      throw new GestoreException(
  	          "Il servizio di modifica dei dati della gara ha risposto con il seguente messaggio: "
  	              + messaggio, "gestioneIDGARACIG.ws.modificagara.error",
  	          new Object[] { messaggio }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsModificaGara: fine metodo");

  }

  /**
   * Chiamata al metodo cancellaGara del WS SIMOG per richiedere la
   * cancellazione dei dati di una gara identificata dal codice IDGARA
   *
   * @param simogWS
   * @param hMapwsLogin
   * @param idgara
   * @param idmotivazione
   * @param notecanc
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   */
  private void wsCancellaGara(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin, String idgara, String idmotivazione,
      String notecanc) throws ServiceException, RemoteException,
      GestoreException {

    if (logger.isDebugEnabled()) logger.debug("wsCancellaGara: inizio metodo");

    String ticket = ((String) hMapwsLogin.get("ticket"));
    String index = ((String) hMapwsLogin.get("index"));

    CancellaGaraDocument cancellaGaraDoc = CancellaGaraDocument.Factory.newInstance();
    CancellaGara cancellaGara = CancellaGara.Factory.newInstance();
    cancellaGara.setTicket(ticket);
    cancellaGara.setIndexCollaborazione(index);
    cancellaGara.setIdMotivazione(idmotivazione);
    cancellaGara.setNoteCanc(notecanc);
    cancellaGara.setIdGara(idgara);
    cancellaGaraDoc.setCancellaGara(cancellaGara);
    CancellaGaraResponseDocument cancellaResponseGaraDoc = simogWS.cancellaGara(cancellaGaraDoc);
    CancellaGaraResponse responseCancellaGara = cancellaResponseGaraDoc.getCancellaGaraResponse();
  
    String messaggio = null;
    if (responseCancellaGara.isSetReturn()) {
    	ResponseCancellaGara response = responseCancellaGara.getReturn();
    	if (!response.getSuccess()) {
    		 messaggio = response.getError();
    	}
    } else {
    	messaggio = "Errore imprevisto nel servizio di cancellaGara";
    }
    
    if (messaggio != null) {
    	logger.error("Il servizio di cancellazione della gara ha risposto con il seguente messaggio: "
    	          + messaggio);
    	      throw new GestoreException(
    	          "Il servizio di cancellazione della gara ha risposto con il seguente messaggio: "
    	              + messaggio, "gestioneIDGARACIG.ws.cancellagara.error",
    	          new Object[] { messaggio }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsCancellaGara: fine metodo");

  }

  /**
   * Chiamata al metodo inserisciLotto del WS SIMOG per ottenere il codice
   * identificativo del lotto (CIG)
   *
   * @param simogWS
   * @param hMapwsLogin
   * @param datiLottoXML
   * @param idgara
   * @return HashMap
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   * @throws SQLException
   */
  private HashMap<String, Object> wsInserisciLotto(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin, DatiLotto datiLotto, String idgara)
      throws ServiceException, RemoteException, GestoreException, SQLException {

    if (logger.isDebugEnabled())
      logger.debug("wsInserisciLotto: inizio metodo");
    HashMap<String, Object> hMapwsInserisciLotto = new HashMap<String, Object>();
    String cig = null;
    String ticket = ((String) hMapwsLogin.get("ticket"));
    String index = ((String) hMapwsLogin.get("index"));

    InserisciLottoDocument inserisciLottoDoc = InserisciLottoDocument.Factory.newInstance();
    InserisciLotto inserisciLotto = InserisciLotto.Factory.newInstance();
    inserisciLotto.setTicket(ticket);
    inserisciLotto.setIndexCollaborazione(index);
    inserisciLotto.setDatiLotto(datiLotto);
    inserisciLotto.setIdGara(idgara);
    inserisciLottoDoc.setInserisciLotto(inserisciLotto);
    InserisciLottoResponseDocument InserisciresponseLottoDoc = simogWS.inserisciLotto(inserisciLottoDoc);
    InserisciLottoResponse responseInserisciLotto = InserisciresponseLottoDoc.getInserisciLottoResponse();
    
    if (responseInserisciLotto.isSetReturn())
    {
    	ResponseInserisciLotto response = responseInserisciLotto.getReturn();
    	if (response.getSuccess()) {
    		cig = response.getCig().getCig();
            cig += response.getCig().getCigKKK();
            hMapwsInserisciLotto.put("cig", cig);
            CUPLOTTOType cuplotto = response.getCUPLOTTO();
            hMapwsInserisciLotto.put("cuplotto", cuplotto);
    	} else {
    		
    		CUPLOTTOType cuplotto = response.getCUPLOTTO();
    	      String msgCupAnomali = "";
    	      if(cuplotto != null && cuplotto.getCODICICUPArray() != null){
    	    	  DatiCUPType[] codiciCup = cuplotto.getCODICICUPArray();
    	    	  for (int i = 0; i < codiciCup.length; i++) {
    	    		  String cup = codiciCup[i].getCUP();
    	    		  if(codiciCup[i].getVALIDO().equals(FlagSNType.N)){
    	    			  msgCupAnomali = "\r\n" + msgCupAnomali + "\r\n" + cup;
    	    		  }
    	        	}
    	      }
    	      String messaggio = response.getError();
    	      if(!"".equals(msgCupAnomali)){
    	        msgCupAnomali = " - Correggere i seguenti Cup Anomali: " + msgCupAnomali;
    	        messaggio = messaggio + msgCupAnomali;
    	      }
    	      logger.error("Il servizio di inserimento del lotto ha risposto con il seguente messaggio: "
    	          + messaggio);
    	      throw new GestoreException(
    	          "Il servizio di inserimento del lotto ha risposto con il seguente messaggio: "
    	              + messaggio, "gestioneIDGARACIG.ws.inseriscilotto.error",
    	          new Object[] { messaggio }, null);
    	}
    }
    else {
    	String messaggio = "Errore imprevisto nel servizio di inserisciLotto";
    	logger.error(messaggio);
    	throw new GestoreException(
    	          "Il servizio di inserimento del lotto ha risposto con il seguente messaggio: "
    	              + messaggio, "gestioneIDGARACIG.ws.inseriscilotto.error",
    	          new Object[] { messaggio }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsInserisciLotto: fine metodo");

    return hMapwsInserisciLotto;
  }

  /**
   * Chiamata al metodo modificaLotto del WS SIMOG per comunicare le modifiche
   * ai dati del lotto identificato dal codice CIG
   *
   * @param simogWS
   * @param hMapwsLogin
   * @param numgara
   * @param numlott
   * @param cig
   * @return HashMap
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   * @throws SQLException
   */
  private HashMap<String, Object> wsModificaLotto(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin, ModificaLotto.DatiLotto datiLotto, String cig)
      throws ServiceException, RemoteException, GestoreException, SQLException {

    if (logger.isDebugEnabled())
      logger.debug("wsModificaLotto: inizio metodo");

    String ticket = ((String) hMapwsLogin.get("ticket"));
    String index = ((String) hMapwsLogin.get("index"));

    HashMap<String, Object> hMapwsModificaLotto = new HashMap<String, Object>();

    ModificaLottoDocument modificaLottoDoc = ModificaLottoDocument.Factory.newInstance();
    ModificaLotto modificaLotto = ModificaLotto.Factory.newInstance();
    modificaLotto.setTicket(ticket);
    modificaLotto.setIndexCollaborazione(index);
    modificaLotto.setDatiLotto(datiLotto);
    modificaLotto.setCig(cig);
    modificaLottoDoc.setModificaLotto(modificaLotto);
    ModificaLottoResponseDocument modificaResponseLottoDoc = simogWS.modificaLotto(modificaLottoDoc);
    ModificaLottoResponse responseModificaLotto = modificaResponseLottoDoc.getModificaLottoResponse();
  
    String messaggio = null;
    if (responseModificaLotto.isSetReturn()) {
    	ResponseModificaLotto response = responseModificaLotto.getReturn();
    	if (!response.getSuccess()) {
    	      CUPLOTTOType cuplotto = response.getCUPLOTTO();
    	      String msgCupAnomali = "";
    	      if(cuplotto != null && cuplotto.getCODICICUPArray() != null){
    	    	  DatiCUPType[] codiciCup = cuplotto.getCODICICUPArray();
    	    	  for (int i = 0; i < codiciCup.length; i++) {
    	    		  String cup = codiciCup[i].getCUP();
    	    		  if(codiciCup[i].getVALIDO().equals(FlagSNType.N)){
    	    			  msgCupAnomali = "\r\n" + msgCupAnomali + "\r\n" + cup;
    	    		  }
    	    	  }
    	      }
    	      messaggio = response.getError();
    	      if(!"".equals(msgCupAnomali)){
    	        msgCupAnomali = " - Correggere i seguenti Cup Anomali: " + msgCupAnomali;
    	        messaggio = messaggio + msgCupAnomali;
    	      }
    	    }else{
    	    	CUPLOTTOType cuplotto = response.getCUPLOTTO();
    	    	hMapwsModificaLotto.put("cuplotto", cuplotto);
    	    }
    } else {
    	messaggio = "Errore imprevisto nel servizio di modificaLotto";
    }
    
    if (messaggio != null) {
    	logger.error("Il servizio di modifica dei dati del lotto ha risposto con il seguente messaggio: "
    	          + messaggio);
    	      throw new GestoreException(
    	          "Il servizio di modifica dei dati del lotto ha risposto con il seguente messaggio: "
    	              + messaggio, "gestioneIDGARACIG.ws.modificalotto.error",
    	          new Object[] { messaggio }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsModificaLotto: fine metodo");

    return hMapwsModificaLotto;
  }

  /**
   * Chiamata al metodo cancellaLotto del WS SIMOG per inviare la richiesta di
   * cancellazione del lotto identificato dal codice CIG
   *
   * @param simogWS
   * @param hMapwsLogin
   * @param cig
   * @param idmotivazione
   * @param notecanc
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   * @throws SQLException
   */
  private void wsCancellaLotto(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin, String cig, String idmotivazione,
      String notecanc) throws ServiceException, RemoteException,
      GestoreException, SQLException {

    if (logger.isDebugEnabled())
      logger.debug("wsCancellaLotto: inizio metodo");

    String ticket = ((String) hMapwsLogin.get("ticket"));
    String index = ((String) hMapwsLogin.get("index"));

    CancellaLottoDocument cancellaLottoDoc = CancellaLottoDocument.Factory.newInstance();
    CancellaLotto cancellaLotto = CancellaLotto.Factory.newInstance();
    cancellaLotto.setTicket(ticket);
    cancellaLotto.setIndexCollaborazione(index);
    cancellaLotto.setIdMotivazione(idmotivazione);
    cancellaLotto.setNoteCanc(notecanc);
    cancellaLotto.setCig(cig);
    cancellaLottoDoc.setCancellaLotto(cancellaLotto);
    CancellaLottoResponseDocument cancellaResponseLottoDoc = simogWS.cancellaLotto(cancellaLottoDoc);
    CancellaLottoResponse responseCancellaLotto = cancellaResponseLottoDoc.getCancellaLottoResponse();

    String messaggio = null;
    if (responseCancellaLotto.isSetReturn()) {
    	ResponseCancellaLotto response = responseCancellaLotto.getReturn();
    	if (!response.getSuccess()) {
    		 messaggio = response.getError();
    	}
    } else {
    	messaggio = "Errore imprevisto nel servizio di cancellaLotto";
    }
    
    if (messaggio != null) {
    	logger.error("Il servizio di cancellazione del lotto ha risposto con il seguente messaggio: "
    	          + messaggio);
    	      throw new GestoreException(
    	          "Il servizio di cancellazione del lotto ha risposto con il seguente messaggio: "
    	              + messaggio, "gestioneIDGARACIG.ws.cancellalotto.error",
    	          new Object[] { messaggio }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsCancellaLotto: fine metodo");

  }

  /**
   * Chiamata al metodo consultaGara del WS SIMOG per ottenere i dati della
   * gara, del lotto e delle eventuali schede associate al lotto identificato
   * dal codice CIG
   *
   * @param simogWS
   * @param hMapwsLogin
   * @param cig
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   */
  private String wsConsultaGara(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin, String cig) throws ServiceException,
      RemoteException, GestoreException {

    if (logger.isDebugEnabled()) logger.debug("wsConsultaGara: inizio metodo");

    String ticket = ((String) hMapwsLogin.get("ticket"));

    ConsultaGaraDocument consultaGaraDoc = ConsultaGaraDocument.Factory.newInstance();
    ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
    consultaGara.setTicket(ticket);
    consultaGara.setCIG(cig);
    consultaGara.setSchede("3.04.2.0");
    consultaGaraDoc.setConsultaGara(consultaGara);
    ConsultaGaraResponseDocument consultaGaraResponseDoc = simogWS.consultaGara(consultaGaraDoc);
    ConsultaGaraResponse responseConsultaGara = consultaGaraResponseDoc.getConsultaGaraResponse();
  
    String error = null;
    ResponseConsultaGara response = null;
    if (responseConsultaGara.isSetReturn()) {
    	response = responseConsultaGara.getReturn();
    	if (!response.getSuccess()) {
    		error = response.getError();
    	}
    } else {
    	error = "Errore imprevisto nel servizio di consultaGara";
    }
    
    if (error != null) {
    	logger.error("Il servizio di consultazione della gara ha risposto con il seguente messaggio: "
    	          + error);
    	      throw new GestoreException(
    	          "Il servizio di consultazione della gara ha risposto con il seguente messaggio: "
    	              + error, "gestioneIDGARACIG.ws.consultagaralotto.error",
    	          new Object[] { error }, null);
    }
    
    if (logger.isDebugEnabled()) logger.debug("wsConsultaGara: fine metodo");

    return response.getGaraXML().toString();

  }

  /**
   * Chiamata al metodo consultaGara del WS SIMOG per ottenere i dati della
   * gara, del lotto e delle eventuali schede associate al lotto identificato
   * dal codice CIG
   *
   * @param simogSmartCigWS
   * @param hMapwsLogin
   * @param smartcig
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   */
  private ComunicazioneType wsConsultaSmartCig(Services simogSmartCigWS,
      HashMap<String, Object> hMapwsLogin, String smartcig, Long syscon, String codrup) throws ServiceException,
      RemoteException, GestoreException {

    if (logger.isDebugEnabled()) logger.debug("wsConsultaSmartCig: inizio metodo");
    String messaggio = null;
    String ticket = ((String) hMapwsLogin.get("ticket"));
    Collaborazioni collaborazioni = ((Collaborazioni) hMapwsLogin.get("collaborazioni"));
    ConsultaComunicazioneRequest richiesta = new ConsultaComunicazioneRequest();
    richiesta.setCig(smartcig);
    ConsultaComunicazioneResponse comunicazioneResponse = null;
    LoggedUserInfoType user = new LoggedUserInfoType();
    
    user.setTicket(ticket);
    if (collaborazioni != null && collaborazioni.getCollaborazioniArray() != null && collaborazioni.getCollaborazioniArray().length>0) {
    	for(Collaborazione collaborazione:collaborazioni.getCollaborazioniArray()) {
        	user.setIndex(collaborazione.getIndex());
        	richiesta.setUser(user);
        	try {
        		comunicazioneResponse = simogSmartCigWS.consultaComunicazione(richiesta);
        		if (comunicazioneResponse.getComunicazione() != null) {
        			Long idCollaborazione = getIndiceCollaborazione(collaborazione, syscon, codrup);
        			hMapwsLogin.put("collaborazione", idCollaborazione);
        			break;
        		}
        	} catch (Exception ex) {
        		messaggio = ex.getMessage();
        	}
        }
    	if (comunicazioneResponse == null) {
    		throw new GestoreException(
    		          "Il servizio di consultazione dello SMARTCIG ha risposto con il seguente messaggio: "
    		              + messaggio, "gestioneIDGARACIG.ws.consultagaralotto.error",
    		          new Object[] { messaggio }, null);
    	}
    } else {
    	user.setIndex("0");
    	richiesta.setUser(user);
    	comunicazioneResponse = simogSmartCigWS.consultaComunicazione(richiesta);
    }

    if (comunicazioneResponse.getComunicazione() == null) {
    	RisultatoType risultato = comunicazioneResponse.getCodiceRisultato();
    	messaggio = risultato.getDescrizione().getValue();
    	logger.error("Il servizio di consultazione dello SMARTCIG ha risposto con il seguente messaggio: "
          + messaggio);
    	throw new GestoreException(
          "Il servizio di consultazione dello SMARTCIG ha risposto con il seguente messaggio: "
              + messaggio, "gestioneIDGARACIG.ws.consultagaralotto.error",
          new Object[] { messaggio }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsConsultaSmartCig: fine metodo");

    return comunicazioneResponse.getComunicazione();

  }
  
  /**
   * Ritorna l'indice di collaborazione utilizzato se non esiste lo crea
   *
   * @param collaborazione
   * @throws GestoreException
   */
  private Long getIndiceCollaborazione(Collaborazione collaborazione, Long syscon, String codrup) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("getIndiceCollaborazione: inizio metodo");
    Long result = new Long(0);
    try {
    	// Verifico se esiste gia' una collaborazione nell'archivio
        // generale
        Long indiceCollaborazione = (Long) sqlManager.getObject(
            "select ID from w3aziendaufficio where indexcoll = ? and ufficio_id = ?",
            new Object[] { collaborazione.getIndex(), collaborazione.getUfficioId() });
        
        if (indiceCollaborazione != null) {
        	result = indiceCollaborazione;
        } else {
        	TransactionStatus status = null;
        	try {
        		status = this.sqlManager.startTransaction();
            	// inserisco l'indice di collaborazione per il rup
            	Long id = (Long) sqlManager.getObject(
                        "select max(id) from w3aziendaufficio", new Object[] {});
                    if (id == null) id = new Long(0);
                    result = new Long(id.longValue() + 1);
                    sqlManager.update("insert into w3aziendaufficio (id, "
                        + "azienda_cf, "
                        + "azienda_denom, "
                        + "indexcoll, "
                        + "ufficio_denom, "
                        + "ufficio_id, "
                        + "ufficio_profilo) "
                        + "values (?,?,?,?,?,?,?)", new Object[] { result,
                        		collaborazione.getAziendaCodiceFiscale(), collaborazione.getAziendaDenominazione(), collaborazione.getIndex(),
                        		collaborazione.getUfficioDenominazione(), collaborazione.getUfficioId(), collaborazione.getUfficioProfilo() });
                    
                    sqlManager.update(
                            "insert into w3usrsyscoll (syscon, rup_codtec, w3aziendaufficio_id) values (?,?,?)",
                            new Object[] { syscon, codrup, result });
        	} catch(SQLException e) {
        		;
        	}
        	
                
                finally {
                	try {
                		this.sqlManager.commitTransaction(status);
                	} catch (SQLException e) {
                		// TODO Auto-generated catch block
                		e.printStackTrace();
                	}
                }
        }

    } catch (SQLException e) {
    	throw new GestoreException(
    	          "Si e' verificato un errore durante l'interazione con la base dati",
    	          "gestioneIDGARACIG.sqlerror", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("getIndiceCollaborazione: fine metodo");

    return result;
  }
  
  /**
   * Chiamata la metodo pubblica del WS SIMOG per inviare richiesta di
   * pubblicazione della gara e dei lotti associati
   *
   * @param simogWS
   * @param hMapwsLogin
   * @param numgara
   * @return
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   */
  private String wsPubblica(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin, String dataPubblicazione,
      String dataScadenzaPagamenti, String cig, String progCui,
      DatiPubblicazione pubblicazione, String tipoOperazione, AllegatoType[] allegati,
      String oraScadenza, String dataScadenzaRichiestaInvito, String dataLetteraInvito, CUPLOTTOType[] cuplotti)
      throws ServiceException, RemoteException, GestoreException {

    if (logger.isDebugEnabled()) logger.debug("wsPubblica: inizio metodo");

    String ticket = ((String) hMapwsLogin.get("ticket"));
    String index = ((String) hMapwsLogin.get("index"));

    PubblicaDocument pubblicaDoc = PubblicaDocument.Factory.newInstance();
    Pubblica pubblica = Pubblica.Factory.newInstance();
    pubblica.setTicket(ticket);
    pubblica.setIndexCollaborazione(index);
    if (allegati != null) {
    	pubblica.setAllegatoArray(allegati);
    }
    pubblica.setCig(cig);
    if (cuplotti != null && cuplotti.length > 0) {
    	pubblica.setCUPLOTTOArray(cuplotti);
    }
    if (dataLetteraInvito != null && !dataLetteraInvito.equals("")) {
    	pubblica.setDataLetteraInvito(dataLetteraInvito);
    }
    if (dataPubblicazione != null && !dataPubblicazione.equals("")) {
    	pubblica.setDataPubblicazione(dataPubblicazione);
    }
    if (oraScadenza != null && !oraScadenza.equals("")) {
    	pubblica.setOraScadenza(oraScadenza);
    }
    if (progCui != null && !progCui.equals("")) {
    	pubblica.setProgCui(progCui);
    }
    if (tipoOperazione != null && !tipoOperazione.equals("")) {
    	pubblica.setTipoOperazione(tipoOperazione);
    }
    if (dataScadenzaPagamenti != null && !dataScadenzaPagamenti.equals("")) {
    	pubblica.setDataScadenzaPag(dataScadenzaPagamenti);
    }
    if (dataScadenzaRichiestaInvito != null && !dataScadenzaRichiestaInvito.equals("")) {
    	pubblica.setDataScadenzaRichiestaInvito(dataScadenzaRichiestaInvito);
    }
    if (pubblicazione != null) {
    	pubblica.setDatiPubblicazione(pubblicazione);
    }
    pubblicaDoc.setPubblica(pubblica);
    
    PubblicaResponseDocument pubblicaResponseDoc = simogWS.pubblica(pubblicaDoc);
    PubblicaResponse pubblicaResponse = pubblicaResponseDoc.getPubblicaResponse();
  
    String error = null;
    if (pubblicaResponse.isSetReturn()) {
    	ResponsePubblicazioneBando response = pubblicaResponse.getReturn();
    	if(!response.getSuccess()) {
    		error = response.getError();
    	} else {
    		try {
        		//Chiamo il consulta gara per ricavare i requisiti
    			ConsultaGaraDocument consultaGaraDoc = ConsultaGaraDocument.Factory.newInstance();
    			ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
    			consultaGara.setCIG(cig);
    			consultaGara.setTicket(ticket);
    			consultaGara.setSchede("3.04.0.0");
    			consultaGaraDoc.setConsultaGara(consultaGara);
    			ConsultaGaraResponseDocument  responseConsultaGaraDoc = simogWS.consultaGara(consultaGaraDoc);
    			ConsultaGaraResponse consultaGaraResponse = responseConsultaGaraDoc.getConsultaGaraResponse();
    			if (consultaGaraResponse.isSetReturn()) {
    				ResponseConsultaGara responseCG = consultaGaraResponse.getReturn();
    				if (responseCG.getSuccess()) {
    					SchedaType scheda = responseCG.getGaraXML();
    					DatiGaraType datiGaraLotto = scheda.getDatiGara();
    					Long idgara =  datiGaraLotto.getGara().getIDGARA();
    		           	Long numgara = (Long) this.sqlManager.getObject("select numgara from w3gara where id_gara = ?", new Object[] { idgara });
    		           	if (numgara != null) {
    		           		ReqGaraType[] requisitiGara = datiGaraLotto.getRequisitoArray();
    		           		// Inserimento requisiti
    		           		if (requisitiGara != null) {
    		           			this.gestioneXMLIDGARACIGManager.inserisciW3GARAREQdaSIMOG(requisitiGara, numgara);
    		           		}
    		            }
    				}
    			}
        	} catch (Exception e) {
        		;
        	}
    	}
    } else {
    	error = "Errore imprevisto nel servizio di pubblica";
    }
    
    if (error != null) {
    	logger.error("Il servizio di pubblicazione ha risposto con il seguente messaggio: "
    	          + error);
    	      throw new GestoreException(
    	          "Il servizio di pubblicazione ha risposto con il seguente messaggio: "
    	              + error, "gestioneIDGARACIG.ws.pubblica.error",
    	          new Object[] { error }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsPubblica: fine metodo");

    return "";

  }

  /**
   * Chiamata al metodo invioRequisiti del WS SIMOG per inviare i requisiti
   * (restituisce identificativo gara (IDGARA))
   *
   * @param simogWS
   * @param hMapwsLogin
   * @param datiRequisitiXML
   * @return hMapwsInviaRequisiti
   * @throws ServiceException
   * @throws RemoteException
   * @throws GestoreException
   */
  private HashMap<String, Object> wsInviaRequisiti(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin, Requisiti datiRequisiti, String idGara)
      throws ServiceException, RemoteException, GestoreException {
    if (logger.isDebugEnabled())
      logger.debug("wsInviaRequisiti: inizio metodo");
    HashMap<String, Object> hMapwsInviaRequisiti = new HashMap<String, Object>();

    String idgara = idGara;
    String ticket = ((String) hMapwsLogin.get("ticket"));
    String index = ((String) hMapwsLogin.get("index"));
    hMapwsInviaRequisiti.put("idgara", idgara);
    
    InviaRequisitiDocument inviaRequisitiDoc = InviaRequisitiDocument.Factory.newInstance();
    InviaRequisiti inviaRequisiti = InviaRequisiti.Factory.newInstance();
    inviaRequisiti.setTicket(ticket);
    inviaRequisiti.setIndexCollaborazione(index);
    inviaRequisiti.setRequisiti(datiRequisiti);
    inviaRequisiti.setIdGara(idgara);
    inviaRequisitiDoc.setInviaRequisiti(inviaRequisiti);
    logger.error(inviaRequisitiDoc);
    InviaRequisitiResponseDocument inviaRequisitiResponseDoc = simogWS.inviaRequisiti(inviaRequisitiDoc);
    InviaRequisitiResponse responseInviaRequisiti = inviaRequisitiResponseDoc.getInviaRequisitiResponse();
   
    String errore = null;
    String messaggio = null;
    if (responseInviaRequisiti.isSetReturn()) {
    	ResponseInviaRequisiti response = responseInviaRequisiti.getReturn();
    	if (response.getSuccess()) {
    		messaggio = response.getMessaggio();
    	    hMapwsInviaRequisiti.put("messaggio", messaggio);
    	} else {
    		errore = response.getError();
    	}
    } else {
    	errore = "Errore imprevisto nel servizio di inviaRequisiti";
    }
    
    if (errore != null) {
    	hMapwsInviaRequisiti.put("messaggio", errore);
        logger.error("Il servizio di invio requisiti ha risposto con il seguente messaggio: "
            + messaggio);
        throw new GestoreException(
            "Il servizio di invio requisiti ha risposto con il seguente messaggio: "
                + errore, "gestioneIDGARACIG.ws.inviorequisiti.error",
            new Object[] { errore }, null);
    }

    if (logger.isDebugEnabled()) logger.debug("wsInviaRequisiti: fine metodo");

    return hMapwsInviaRequisiti;

  }


  /**
   * Collegamento al WS SIMOG. Ottiene l'oggetto SimogWS
   *
   * @return
   * @throws ServiceException
   * @throws GestoreException
 * @throws AxisFault 
   */
  private SimogWSPDDServiceStub getSimogWS() throws ServiceException, GestoreException, AxisFault {

    if (logger.isDebugEnabled()) logger.debug("getSimogWS: inizio metodo");

    // Indirizzo web service
    String url = ConfigManager.getValore(PROP_SIMOG_WS_URL);
    if (url == null || "".equals(url)) {
      throw new GestoreException(
          "L'indirizzo per la connessione al web service di elaborazione degli identificativi di gara non e' definito",
          "gestioneIDGARACIG.ws.url.error");
    }


    SimogWSPDDServiceStub simogWS = new SimogWSPDDServiceStub(url);

    if (logger.isDebugEnabled()) logger.debug("getSimogWS: fine metodo");

    return simogWS;

  }

  /**
   * Collegamento al WS SIMOG per gestione SMARTCIG. Ottiene l'oggetto Service
   *
   * @return
   * @throws ServiceException
   * @throws GestoreException
   */
  private Services getSimogWSSmartCig() throws ServiceException, GestoreException {

    if (logger.isDebugEnabled()) logger.debug("getSimogWSSmartCig: inizio metodo");

    // Indirizzo web service
    String url = ConfigManager.getValore(PROP_SIMOG_WS_SMARTCIG_URL);
    if (url == null || "".equals(url)) {
      throw new GestoreException(
          "L'indirizzo per la connessione al web service di elaborazione dei smartcig non e' definito",
          "gestioneIDGARACIG.ws.url.error");
    }


    ServicesServiceLocator WSLocator = new ServicesServiceLocator();
    WSLocator.setServicesSoap11EndpointAddress(url);
    Services simogWS = WSLocator.getServicesSoap11();

    if (logger.isDebugEnabled()) logger.debug("getSimogWSSmartCig: fine metodo");

    return simogWS;

  }
  
  /**
   * Recupero delle credenziali di accesso al WS SIMOG
   *
   * @param syscon
   * @param codrup
   * @return
   * @throws GestoreException
   */
  public HashMap<String, String> recuperaSIMOGWSUserPass(Long syscon, String codrup)
      throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("recuperaSIMOGWSUserPass: inizio metodo");

    HashMap<String, String> hMapSIMOGWSUserPass = new HashMap<String, String>();

    try {
      List<?> datiW3USRSYS = this.sqlManager.getVector(
          "select simogwsuser, simogwspass from w3usrsys where syscon = ? and rup_codtec = ?",
          new Object[] { syscon, codrup });

      if (datiW3USRSYS != null && datiW3USRSYS.size() > 0) {

        // Utente
        String simogwsuser = (String) SqlManager.getValueFromVectorParam(
            datiW3USRSYS, 0).getValue();
        hMapSIMOGWSUserPass.put("simogwsuser", simogwsuser);

        // Password
        String simogwspass = (String) SqlManager.getValueFromVectorParam(
            datiW3USRSYS, 1).getValue();
        String simogwspassDecriptata = null;
        if (simogwspass != null && simogwspass.trim().length() > 0) {
          ICriptazioneByte simogwspassICriptazioneByte = null;
          simogwspassICriptazioneByte = FactoryCriptazioneByte.getInstance(
              ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
              simogwspass.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
          simogwspassDecriptata = new String(
              simogwspassICriptazioneByte.getDatoNonCifrato());
        }
        hMapSIMOGWSUserPass.put("simogwspass", simogwspassDecriptata);

      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella gestione delle credenziali di accesso al servizio di elaborazione degli identificativi di gara",
          "gestioneSIMOGWSUserPass.error", e);

    } catch (CriptazioneException e) {
      throw new GestoreException(
          "Errore nella gestione delle credenziali di accesso al servizio di elaborazione degli identificativi di gara",
          "gestioneSIMOGWSUserPass.error", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("recuperaSIMOGWSUserPass: fine metodo");

    return hMapSIMOGWSUserPass;

  }

  /**
   * Memorizzazione delle credenziali di accesso al WS SIMOG
   *
   * @param syscon
   * @param codrup
   * @param simogwsuser
   * @param simogwspass
   * @throws GestoreException
   */
  public void memorizzaSIMOGWSUserPass(Long syscon, String codrup, String simogwsuser,
      String simogwspass) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("memorizzaSIMOGWSUserPass: inizio metodo");

    try {
      // Password
      String simogwspassCriptata = null;
      if (simogwspass != null && simogwspass.trim().length() > 0) {
        ICriptazioneByte simogwspassICriptazioneByte = null;
        simogwspassICriptazioneByte = FactoryCriptazioneByte.getInstance(
            ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
            simogwspass.getBytes(), ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);
        simogwspassCriptata = new String(
            simogwspassICriptazioneByte.getDatoCifrato());
      }

      this.sqlManager.update(
              "update w3usrsys set simogwsuser = ?, simogwspass = ? where syscon = ? and rup_codtec = ?",
              new Object[] { simogwsuser, simogwspassCriptata, syscon, codrup });
      
      // Controllo se esiste gia' una riga con lo stesso SYSCON
      /*Long conteggio = (Long) this.sqlManager.getObject(
          "select count(*) from w3usrsys where syscon = ?",
          new Object[] { syscon });
      if (conteggio != null && conteggio.longValue() > 0) {
        this.sqlManager.update(
            "update w3usrsys set simogwsuser = ?, simogwspass = ? where syscon = ?",
            new Object[] { simogwsuser, simogwspassCriptata, syscon });
      } else {
        this.sqlManager.update(
            "insert into w3usrsys (simogwsuser, simogwspass, syscon) values (?,?,?)",
            new Object[] { simogwsuser, simogwspassCriptata, syscon });
      }*/

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella gestione delle credenziali di accesso al servizio di elaborazione degli identificativi di gara",
          "gestioneSIMOGWSUserPass.error", e);

    } catch (CriptazioneException e) {
      throw new GestoreException(
          "Errore nella gestione delle credenziali di accesso al servizio di elaborazione degli identificativi di gara",
          "gestioneSIMOGWSUserPass.error", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("memorizzaSIMOGWSUserPass: fine metodo");

  }

  /**
   * Cancellazione delle credenziali di accesso al WS SIMOG
   *
   * @param syscon
   * @param codrup 
   * @throws GestoreException
   */
  public void cancellaSIMOGWSUserPass(Long syscon, String codrup) throws GestoreException {

    if (logger.isDebugEnabled())
      logger.debug("cancellaSIMOGWSUserPass: inizio metodo");

    try {
      this.sqlManager.update("delete from w3usrsys where syscon = ? and RUP_CODTEC = ?",
          new Object[] { syscon, codrup });

    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nella gestione delle credenziali di accesso al servizio di elaborazione degli identificativi di gara",
          "gestioneSIMOGWSUserPass.error", e);
    }

    if (logger.isDebugEnabled())
      logger.debug("cancellaSIMOGWSUserPass: fine metodo");

  }

  /**
   * Chiamata al metodo chiudiSessione del WS SIMOG per comunicare la chiusura
   * della sessione
   *
   * @param simogWS
   * @param hMapwsLogin
   */
  private void wsChiudiSessione(SimogWSPDDServiceStub simogWS,
      HashMap<String, Object> hMapwsLogin) {

    if (logger.isDebugEnabled())
      logger.debug("wsChiudiSessione: inizio metodo");

    // In caso di errore si scrive solamente sul log.
    // Non deve essere scatenata alcuna ulteriore segnalazione di errore
    // in modo da non scartare tutta la transazione
    try {
      String ticket = ((String) hMapwsLogin.get("ticket"));
      if (ticket != null) {
    	  ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
          chiudiSessione.setTicket(ticket);
        
          ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
          chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
         
          ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = 
        	  simogWS.chiudiSessione(chiudiSessioneDoc);
          logger.info("Invocazione del metodo chiudiSessione verso i Servizi SIMOG (Ticket="
              + ticket + ")");
          ResponseChiudiSession responseChiudiSessione = 
              chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
          logger.info("Invocato il metodo chiudiSessione verso i Servizi SIMOG (Ticket="
              + ticket + ")");
          
          if (!responseChiudiSessione.getSuccess()) {
            logger.error("La chiusura della connessione identificata dal ticket "
                + ticket
                + " ha generato il seguente errore: "
                + responseChiudiSessione.getError());
          } else {
            logger.info("Logout al WS SIMOG avvenuta con successo. La sessione con ticket "
                + ticket
                + " e' stata chiusa.");
            ticket = null;
          }
      }
    } catch (Throwable t) {

    }
    if (logger.isDebugEnabled()) logger.debug("wsChiudiSessione: fine metodo");

  }

}
