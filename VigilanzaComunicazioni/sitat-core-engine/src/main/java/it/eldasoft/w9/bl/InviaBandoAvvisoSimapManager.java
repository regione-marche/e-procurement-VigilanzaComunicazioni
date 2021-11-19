package it.eldasoft.w9.bl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.simap.ws.EldasoftSimapWS;
import it.eldasoft.simap.ws.EldasoftSimapWSServiceLocator;
import it.eldasoft.simap.ws.EsitoSimapWS;
import it.eldasoft.simap.ws.xmlbeans.Ag010Type;
import it.eldasoft.simap.ws.xmlbeans.AuthorityType;
import it.eldasoft.simap.ws.xmlbeans.AvvisoAggiudicazioneDocument;
import it.eldasoft.simap.ws.xmlbeans.AvvisoAggiudicazioneType;
import it.eldasoft.simap.ws.xmlbeans.BandoGaraDocument;
import it.eldasoft.simap.ws.xmlbeans.BandoGaraSemplificatoDocument;
import it.eldasoft.simap.ws.xmlbeans.BandoGaraSemplificatoType;
import it.eldasoft.simap.ws.xmlbeans.BandoGaraType;
import it.eldasoft.simap.ws.xmlbeans.CPVType;
import it.eldasoft.simap.ws.xmlbeans.EconomicOperatorType;
import it.eldasoft.simap.ws.xmlbeans.IIIVCommonsType;
import it.eldasoft.simap.ws.xmlbeans.LottoAggiudicatoType;
import it.eldasoft.simap.ws.xmlbeans.LottoType;
import it.eldasoft.simap.ws.xmlbeans.NUTSType;
import it.eldasoft.simap.ws.xmlbeans.ResponsibleType;
import it.eldasoft.simap.ws.xmlbeans.SnType;
import it.eldasoft.simap.ws.xmlbeans.W3Z40Type;
import it.eldasoft.simap.ws.xmlbeans.W3Z41Type;
import it.eldasoft.simap.ws.xmlbeans.W3Z42Type;
import it.eldasoft.simap.ws.xmlbeans.W3Z46Type;
import it.eldasoft.simap.ws.xmlbeans.W3Z47Type;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.common.CostantiSitatSA;

import org.apache.log4j.Logger;

public class InviaBandoAvvisoSimapManager {

  static Logger               logger                         = Logger.getLogger(InviaBandoAvvisoSimapManager.class);

  private static final String PROP_BANDO_AVVISO_SIMAP_WS_URL = CostantiSitatSA.BANDO_AVVISO_SIMAP;

  private static final String CTR_AUTHORITY                  = "L'amministrazione aggiudicatrice non e' valorizzata";
  private static final String CTR_TITLE_CONTRACT             = "La denominazione dell'appalto non e' valorizzata";
  private static final String CTR_TYPE_CONTRACT              = "Il tipo di appalto non e' valorizzato";
  private static final String CTR_SCOPE_COST                 = "Il valore stimato dell'appalto non e' valorizzato";
  private static final String CTR_TYPE_PROCEDURE             = "Il tipo di procedura non e' valorizzato";
  private static final String CTR_TYPE_PROCEDURE_UNDEFINED   = "Il tipo di procedura e' valorizzato ma non e' gestito da SIMAP";
  private static final String CTR_AWARD_CRITERIA             = "Il criterio di aggiudicazione non e' valorizzato";
  private static final String CTR_AWARD_CRITERIA_UNDEFINED   = "Il criterio di aggiudicazione e' valorizzato ma non e' gestito da SIMAP";
  private static final String CTR_IDGARA                     = "L'identificativo SIMOG della gara non e' valorizzato";
  private static final String CTR_NO_LOTTO                   = "La gara selezionata non contiene alcun lotto";
  private static final String CTR_NO_LOTTOAGGIUDICATO        = "La gara selezionata non contiene lotti aggiudicati";

  private static final String CTR_LOTTO_TITLE                = "Il titolo del lotto non e' valorizzato";
  private static final String CTR_LOTTO_CIG                  = "L'identificativo SIMOG (CIG) del lotto non e' valorizzato";
  private static final String CTR_LOTTO_COST                 = "Il valore stimato del lotto non e' valorizzato";

  private static final String CTR_LOTTO_CONTRACT_AWARD_DATE  = "La data di aggiudicazione del lotto non e' valorizzata";
  private static final String CTR_LOTTO_ECONOMIC_OPERATOR    = "L'operatore economico aggiudicatario non e' valorizzato";
  private static final String CTR_LOTTO_INITIAL_COST         = "Il valore totale inizialmente stimato del lotto non e' valorizzato";
  private static final String CTR_LOTTO_FINAL_COST           = "Il valore totale finale del lotto non e' valorizzato";

  private static final String CTR_AUTHORITY_NOMEIN           = "La denominazione dell'amministrazione aggiudicatrice non e' valorizzata";

  private static final String CTR_RESPONSIBLE_NOMTEC         = "L'intestazione del responsabile dell'amministrazione aggiudicatrice non e' valorizzata";

  private SqlManager          sqlManager;

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   * 
   * @param codgara
   * @param formulario
   * @throws SQLException
   * @throws IOException
   * @throws GestoreException
   * @throws ServiceException
   */
  public EsitoSimapWS inviaBandoAvvisoSimap(Long codgara, String formulario,
      String username, String password) throws Exception, Throwable {

    if (logger.isDebugEnabled())
      logger.debug("inviaBandoAvvisoSimap: inizio metodo");

    EsitoSimapWS esitoSimapWS = new EsitoSimapWS();

    String url = ConfigManager.getValore(PROP_BANDO_AVVISO_SIMAP_WS_URL);
    if (url == null || "".equals(url)) {
      throw new GestoreException(
          "L'indirizzo per la connessione al web service non e' definito",
          "inviabandoavvisosimap.ws.url");
    }

    try {
      if ("FS2".equals(formulario)) {
        String xml = this.getXMLBandoGara(codgara);
        if (xml != null) {
          EldasoftSimapWSServiceLocator locator = new EldasoftSimapWSServiceLocator();
          locator.setEldasoftSimapWSEndpointAddress(url);
          EldasoftSimapWS servizio = locator.getEldasoftSimapWS();
          esitoSimapWS = servizio.inserisciBandoGara(username, password, xml);
        }
      } else if ("FS3".equals(formulario)) {
        String xml = this.getXMLAvvisoAggiudicazione(codgara);
        if (xml != null) {
          EldasoftSimapWSServiceLocator locator = new EldasoftSimapWSServiceLocator();
          locator.setEldasoftSimapWSEndpointAddress(url);
          EldasoftSimapWS servizio = locator.getEldasoftSimapWS();
          esitoSimapWS = servizio.inserisciAvvisoAggiudicazione(username,
              password, xml);
        }
      } else if ("FS9".equals(formulario)) {
        String xml = this.getXMLBandoGaraSemplificato(codgara);
        if (xml != null) {
          EldasoftSimapWSServiceLocator locator = new EldasoftSimapWSServiceLocator();
          locator.setEldasoftSimapWSEndpointAddress(url);
          EldasoftSimapWS servizio = locator.getEldasoftSimapWS();
          esitoSimapWS = servizio.inserisciBandoGaraSemplificato(username,
              password, xml);
        }
      }

      if (!esitoSimapWS.isEsito()) {
        throw new Exception(esitoSimapWS.getMessaggio());
      }
    } catch (Throwable t) {
      throw t;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("inviaBandoAvvisoSimap: fine metodo");
    }
    return esitoSimapWS;

  }

  /**
   * Restituisce XML contenente i dati del bando di gara.
   * 
   * @param codgara
   * @return
   * @throws SQLException
   * @throws IOException
   */
  private String getXMLBandoGara(Long codgara) throws SQLException, IOException, Exception {

    if (logger.isDebugEnabled()) {
      logger.debug("inserisciBandoGara: inizio metodo");
    }
    String xml = null;

    BandoGaraDocument bandoGaraDocument = BandoGaraDocument.Factory.newInstance();
    bandoGaraDocument.documentProperties().setEncoding("UTF-8");
    BandoGaraType bandoGara = bandoGaraDocument.addNewBandoGara();

    IIIVCommonsType iiiVcommons = this.getII_IV_CommonsType(codgara);

    bandoGara.setAUTHORITY(iiiVcommons.getAUTHORITY());
    bandoGara.setTITLECONTRACT(iiiVcommons.getTITLECONTRACT());
    bandoGara.setTYPECONTRACT(iiiVcommons.getTYPECONTRACT());

    if (iiiVcommons.isSetTYPEWORKCONTRACT()) {
      bandoGara.setTYPEWORKCONTRACT(iiiVcommons.getTYPEWORKCONTRACT());
    }
    if (iiiVcommons.isSetTYPESUPPLIES()) {
      bandoGara.setTYPESUPPLIES(iiiVcommons.getTYPESUPPLIES());
    }
    if (iiiVcommons.isSetTYPECATEGORY()) {
      bandoGara.setTYPECATEGORY(iiiVcommons.getTYPECATEGORY());
    }
    if (iiiVcommons.isSetSITELABEL()) {
      bandoGara.setSITELABEL(iiiVcommons.getSITELABEL());
    }
    if (iiiVcommons.isSetSITENUTS()) {
      bandoGara.setSITENUTS(iiiVcommons.getSITENUTS());
    }
    if (iiiVcommons.isSetSHORTDESCRIPTION()) {
      bandoGara.setSHORTDESCRIPTION(iiiVcommons.getSHORTDESCRIPTION());
    }
    if (iiiVcommons.isSetCPV()) {
      bandoGara.setCPV(iiiVcommons.getCPV());
    }

    bandoGara.setSCOPECOST(iiiVcommons.getSCOPECOST());
    bandoGara.setTYPEPROCEDURE(iiiVcommons.getTYPEPROCEDURE());
    bandoGara.setAWARDCRITERIA(iiiVcommons.getAWARDCRITERIA());
    bandoGara.setIDGARA(iiiVcommons.getIDGARA());

    // Allegato B
    // Si devono fornire indicazione sui lotti solo se
    // il numero di lotti e' maggiore di 1
    String selectW9LOTT = "select codlott, "
        + "cig, "
        + "oggetto, "
        + "importo_tot "
        + "from w9lott where codgara = ? order by nlotto ";
    List< ? > datiW9LOTT = sqlManager.getListVector(selectW9LOTT,
        new Object[] { codgara });

    if (datiW9LOTT != null && datiW9LOTT.size() > 1) {

      for (int i = 0; i < datiW9LOTT.size(); i++) {
        LottoType lotto = bandoGara.addNewLOTTI();

        Long codlott = (Long) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 0).getValue();

        // Numero progressivo
        lotto.setNUM(i + 1);

        // CIG
        String cig = (String) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 1).getValue();
        if (cig != null) {
          lotto.setCIG(cig);
        } else {
          throw new Exception("Lotto "
              + codlott.toString()
              + ": "
              + CTR_LOTTO_CIG);
        }

        // Titolo e descrizione breve
        String oggettoLotto = (String) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 2).getValue();
        if (oggettoLotto != null) {
          lotto.setTITLE(oggettoLotto);
          lotto.setDESCRIPTION(oggettoLotto);
        } else {
          throw new Exception("Lotto "
              + codlott.toString()
              + ": "
              + CTR_LOTTO_TITLE);
        }

        // CPV
        CPVType cpvLotto = CPVType.Factory.newInstance();
        cpvLotto = this.getCPV(codgara, codlott);
        if (cpvLotto != null && cpvLotto.getCPVMAIN() != null) {
          lotto.setCPV(cpvLotto);
        }
        
        // Quantitativo o valore stimato
        Double importoTot = (Double) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 3).getValue();
        if (importoTot != null) {
          lotto.setCOST(importoTot.doubleValue());
        } else {
          throw new Exception("Lotto "
              + codlott.toString()
              + ": "
              + CTR_LOTTO_COST);
        }
      }
    }

    ByteArrayOutputStream baosBandoGara = new ByteArrayOutputStream();
    bandoGaraDocument.save(baosBandoGara);
    xml = baosBandoGara.toString();
    baosBandoGara.close();

    if (logger.isDebugEnabled()) {
      logger.debug("inserisciBandoGara: fine metodo");
    }
    return xml;

  }

  /**
   * Restituisce il tipo comune II_IV_Commons utilizzato nel bando di gara e
   * nell'avviso di aggiudicazione.
   * 
   * @param codgara
   * @return
   * @throws SQLException, Exception
   */
  private IIIVCommonsType getII_IV_CommonsType(Long codgara)
      throws SQLException, Exception {

    IIIVCommonsType iiiVcommons = IIIVCommonsType.Factory.newInstance();

    String selectW9GARA = "select codein, "
        + " oggetto, "
        + " importo_gara, "
        + " idavgara "
        + " from w9gara where codgara = ?";
    List< ? > datiW9GARA = sqlManager.getVector(selectW9GARA,
        new Object[] { codgara });
    if (datiW9GARA != null && datiW9GARA.size() > 0) {

      // Amministrazione
      String codein = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 0).getValue();
      if (codein != null) {
        AuthorityType authority = AuthorityType.Factory.newInstance();
        authority = this.getAuthority(codein);
        iiiVcommons.setAUTHORITY(authority);
      } else {
        throw new Exception(CTR_AUTHORITY);
      }

      // Titolo del contratto e descrizione breve
      String oggetto = (String) SqlManager.getValueFromVectorParam(datiW9GARA,
          1).getValue();
      if (oggetto != null) {
        iiiVcommons.setTITLECONTRACT(oggetto);
        iiiVcommons.setSHORTDESCRIPTION(oggetto);
      } else {
        throw new Exception(CTR_TITLE_CONTRACT);
      }

      // Quantitativo o entita' totale
      Double importoGara = (Double) SqlManager.getValueFromVectorParam(
          datiW9GARA, 2).getValue();
      if (importoGara != null) {
        iiiVcommons.setSCOPECOST(importoGara.doubleValue());
      } else {
        throw new Exception(CTR_SCOPE_COST);
      }

      // Identificativo della gara
      String idavgara = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 3).getValue();
      if (idavgara != null) {
        iiiVcommons.setIDGARA(idavgara);
      } else {
        throw new Exception(CTR_IDGARA);
      }

      String selectW9LOTT = "select codlott, "
          + "tipo_contratto, "
          + "id_tipo_prestazione, "
          + "luogo_istat, "
          + "luogo_nuts, "
          + "id_scelta_contraente, "
          + "id_modo_gara "
          + "from w9lott where codgara = ? order by nlotto ";
      List< ? > datiW9LOTT = sqlManager.getListVector(selectW9LOTT,
          new Object[] { codgara });
      if (datiW9LOTT != null && datiW9LOTT.size() > 0) {

        // Dati principali del bando di gara: si considera il primo lotto.
        // Se e' l'unico lotto, allora non deve essere indicata alcuna
        // suddivisione in lotti
        // Tipo di contratto
        String tipoContratto = (String) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(0), 1).getValue();
        if (tipoContratto != null) {
          if ("L".equals(tipoContratto) || "CL".equals(tipoContratto)) {
            iiiVcommons.setTYPECONTRACT(W3Z40Type.WORK);
            // Tipo prestazione per lavori
            Long idTipoPrestazione = (Long) SqlManager.getValueFromVectorParam(
                datiW9LOTT.get(0), 2).getValue();
            if (idTipoPrestazione != null) {
              if (new Long(1).equals(idTipoPrestazione)) {
                iiiVcommons.setTYPEWORKCONTRACT(W3Z41Type.X_2);
              } else if (new Long(2).equals(idTipoPrestazione)
                  || new Long(3).equals(idTipoPrestazione)) {
                iiiVcommons.setTYPEWORKCONTRACT(W3Z41Type.X_1);
              }
            }
          } else if ("S".equals(tipoContratto) || "CS".equals(tipoContratto)) {
            iiiVcommons.setTYPECONTRACT(W3Z40Type.SERV);
          } else if ("F".equals(tipoContratto)) {
            iiiVcommons.setTYPECONTRACT(W3Z40Type.SUPP);
            // Tipologia di forniture
            String selectW9APPAFORN = "select distinct id_appalto from w9appaforn where codgara = ?";
            List< ? > datiW9APPAFORN = sqlManager.getListVector(selectW9APPAFORN,
                new Object[] { codgara });
            if (datiW9APPAFORN != null && datiW9APPAFORN.size() > 0) {
              if (datiW9APPAFORN.size() > 1) {
                iiiVcommons.setTYPESUPPLIES(W3Z42Type.X_5);
              } else {
                Long idAppalto = (Long) SqlManager.getValueFromVectorParam(
                    datiW9APPAFORN.get(0), 0).getValue();
                if (idAppalto != null) {
                  switch (idAppalto.intValue()) {
                  case 1:
                    iiiVcommons.setTYPESUPPLIES(W3Z42Type.X_4);
                    break;
                  case 2:
                    iiiVcommons.setTYPESUPPLIES(W3Z42Type.X_3);
                    break;
                  case 3:
                    iiiVcommons.setTYPESUPPLIES(W3Z42Type.X_2);
                    break;
                  case 4:
                    iiiVcommons.setTYPESUPPLIES(W3Z42Type.X_1);
                    break;
                  case 5:
                    iiiVcommons.setTYPESUPPLIES(W3Z42Type.X_5);
                    break;
                  default:
                    break;
                  }
                }
              }
            }
          }
        } else {
          throw new Exception(CTR_TYPE_CONTRACT);
        }

        // Luogo principale di esecuzione - descrizione
        String luogoIstat = (String) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(0), 3).getValue();
        if (luogoIstat != null) {
          String luogoIstatDescrizione = (String) sqlManager.getObject(
              "select tabdesc from tabsche where tabcod = 'S2003' and tabcod1 = '09' and tabcod3 = ?",
              new Object[] { luogoIstat });
          if (luogoIstatDescrizione != null) {
            iiiVcommons.setSITELABEL(luogoIstatDescrizione);
          }
        }

        // Luogo principale di esecuzione - NUTS
        String luogoNuts = (String) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(0), 4).getValue();
        
        if (luogoNuts != null) {
          if (NUTSType.Enum.forString(luogoNuts) != null) { 
            iiiVcommons.setSITENUTS(NUTSType.Enum.forString(luogoNuts));
          }
        }

        // CPV
        Long codlott = (Long) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(0), 0).getValue();

        CPVType cpvGara = CPVType.Factory.newInstance();
        cpvGara = this.getCPV(codgara, codlott);
        if (cpvGara != null && cpvGara.getCPVMAIN() != null) {
          iiiVcommons.setCPV(cpvGara);
        }
        // Tipo di procedura
        Long idSceltaContraente = (Long) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(0), 5).getValue();
        if (idSceltaContraente != null) {
          switch (idSceltaContraente.intValue()) {
          case 1:
            iiiVcommons.setTYPEPROCEDURE(W3Z46Type.X_1);
            break;
          case 2:
            iiiVcommons.setTYPEPROCEDURE(W3Z46Type.X_2);
            break;
          case 8:
            iiiVcommons.setTYPEPROCEDURE(W3Z46Type.X_6);
            break;
          default:
            throw new Exception(CTR_TYPE_PROCEDURE_UNDEFINED);
          }
        } else {
          throw new Exception(CTR_TYPE_PROCEDURE);
        }

        // Criterio di aggiudicazione
        Long idModoGara = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT.get(0), 6).getValue();
        if (idModoGara != null) {
          switch (idModoGara.intValue()) {
          case 1:
            iiiVcommons.setAWARDCRITERIA(W3Z47Type.X_1);
            break;
          case 2:
            iiiVcommons.setAWARDCRITERIA(W3Z47Type.X_2);
            break;
          default:
            throw new Exception(CTR_AWARD_CRITERIA_UNDEFINED);
          }
        } else {
          throw new Exception(CTR_AWARD_CRITERIA);
        }
      } else {
        throw new Exception(CTR_NO_LOTTO);
      }
    }

    return iiiVcommons;

  }

  /**
   * Gestione dell'amministrazione.
   * 
   * @param codein
   * @return
   * @throws SQLException
   */
  private AuthorityType getAuthority(String codein) throws SQLException, Exception {

    AuthorityType authority = AuthorityType.Factory.newInstance();
    String selectUFFINT = "select nomein, "
        + "cfein, "
        + "ivaein, "
        + "viaein, "
        + "nciein, "
        + "proein, "
        + "capein, "
        + "citein, "
        + "telein, "
        + "faxein, "
        + "emaiin, "
        + "indweb, "
        + "profco, "
        + "codres "
        + "from uffint where codein = ?";

    List< ? > datiUFFINT = sqlManager.getVector(selectUFFINT,
        new Object[] { codein });

    if (datiUFFINT != null && datiUFFINT.size() > 0) {
      String nomein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 0).getValue();
      if (nomein != null) {
        authority.setNOMEIN(nomein);
      } else {
        throw new Exception(CTR_AUTHORITY_NOMEIN);
      }

      String cfein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 1).getValue();
      if (cfein != null) {
        authority.setCFEIN(cfein);
      }

      String ivaein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 2).getValue();
      if (ivaein != null) {
        authority.setIVAEIN(ivaein);
      }

      String viaein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 3).getValue();
      if (viaein != null) {
        authority.setVIAEIN(viaein);
      }

      String nciein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 4).getValue();
      if (nciein != null) {
        authority.setNCIEIN(nciein);
      }

      String proein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 5).getValue();
      if (proein != null) {
        authority.setPROEIN(proein);
      }

      String capein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 6).getValue();
      if (capein != null) {
        authority.setCAPEIN(capein);
      }

      String citein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 7).getValue();
      if (citein != null) {
        authority.setCITEIN(citein);
      }

      String telein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 8).getValue();
      if (telein != null) {
        authority.setTELEIN(telein);
      }

      String faxein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 9).getValue();
      if (faxein != null) {
        authority.setFAXEIN(faxein);
      }

      String emaiin = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 10).getValue();
      if (emaiin != null) {
        authority.setEMAIIN(emaiin);
      }

      String indweb = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 11).getValue();
      if (indweb != null) {
        authority.setINDWEB(indweb);
      }

      String profco = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 12).getValue();
      if (profco != null) {
        authority.setPROFCO(profco);
      }

      // Gestione del responsabile
      String codres = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 13).getValue();
      if (codres != null) {
        String selectTECNI = "select cogtei, "
            + "nometei, "
            + "nomtec, "
            + "cftec, "
            + "pivatec "
            + "from tecni where codtec = ?";

        List< ? > datiTECNI = sqlManager.getVector(selectTECNI,
            new Object[] { codres });
        if (datiTECNI != null && datiTECNI.size() > 0) {
          ResponsibleType responsible = authority.addNewRESPONSIBLE();

          String cogtei = (String) SqlManager.getValueFromVectorParam(
              datiTECNI, 0).getValue();
          if (cogtei != null) {
            responsible.setCOGTEI(cogtei);
          }

          String nometei = (String) SqlManager.getValueFromVectorParam(
              datiTECNI, 1).getValue();
          if (nometei != null) {
            responsible.setNOMETEI(nometei);
          }

          String nomtec = (String) SqlManager.getValueFromVectorParam(
              datiTECNI, 2).getValue();
          if (nomtec != null) {
            responsible.setNOMTEC(nomtec);
          } else {
            throw new Exception(CTR_RESPONSIBLE_NOMTEC);
          }

          String cftec = (String) SqlManager.getValueFromVectorParam(datiTECNI, 3).getValue();
          if (cftec != null) {
            responsible.setCFTEC(cftec);
          }

          String pivatec = (String) SqlManager.getValueFromVectorParam(datiTECNI, 4).getValue();
          if (pivatec != null) {
            responsible.setPIVATEC(pivatec);
          }
        }
      }
    }
    return authority;
  }

  /**
   * Gestione oggetto CPV (Principale e complementare) per un lotto.
   * 
   * @param codgara
   * @param codlott
   * @return
   * @throws SQLException
   */
  private CPVType getCPV(Long codgara, Long codlott) throws SQLException {

    CPVType cpv = CPVType.Factory.newInstance();

    String selectW9LOTT = "select cpv from w9lott where codgara = ? and codlott = ?";
    String cpvMain = (String) sqlManager.getObject(selectW9LOTT, new Object[] {
        codgara, codlott });

    if (cpvMain != null) {
      cpv.setCPVMAIN(cpvMain);
      String selectW9CPV = "select cpv from w9cpv where codgara = ? and codlott = ? order by num_cpv";
      List< ? > datiW9CPV = sqlManager.getListVector(selectW9CPV, new Object[] {
          codgara, codlott });

      if (datiW9CPV != null && datiW9CPV.size() > 0) {
        for (int j = 0; j < datiW9CPV.size(); j++) {
          String cpvAdditional = (String) SqlManager.getValueFromVectorParam(
              datiW9CPV.get(j), 0).getValue();
          if (cpvAdditional != null) {
            cpv.addCPVADDITIONAL(cpvAdditional);
          }
        }
      }
    }

    return cpv;

  }

  /**
   * Restituisce XML contenente i dati dell'avviso di aggiudicazione.
   * 
   * @param codgara
   * @return
   * @throws SQLException
   * @throws IOException
   */
  private String getXMLAvvisoAggiudicazione(Long codgara) throws SQLException,
      IOException, Exception {

    if (logger.isDebugEnabled()) {
      logger.debug("inserisciAvvisoAggiudicazione: inizio metodo");
    }
    String xml = null;

    AvvisoAggiudicazioneDocument avvisoAggiudicazioneDocument = AvvisoAggiudicazioneDocument.Factory.newInstance();
    avvisoAggiudicazioneDocument.documentProperties().setEncoding("UTF-8");
    AvvisoAggiudicazioneType avvisoAggiudicazione = avvisoAggiudicazioneDocument.addNewAvvisoAggiudicazione();

    IIIVCommonsType iiiVcommons = this.getII_IV_CommonsType(codgara);

    avvisoAggiudicazione.setAUTHORITY(iiiVcommons.getAUTHORITY());
    avvisoAggiudicazione.setTITLECONTRACT(iiiVcommons.getTITLECONTRACT());
    avvisoAggiudicazione.setTYPECONTRACT(iiiVcommons.getTYPECONTRACT());

    if (iiiVcommons.isSetTYPEWORKCONTRACT()) {
      avvisoAggiudicazione.setTYPEWORKCONTRACT(iiiVcommons.getTYPEWORKCONTRACT());
    }
    if (iiiVcommons.isSetTYPESUPPLIES()) {
      avvisoAggiudicazione.setTYPESUPPLIES(iiiVcommons.getTYPESUPPLIES());
    }
    if (iiiVcommons.isSetTYPECATEGORY()) {
      avvisoAggiudicazione.setTYPECATEGORY(iiiVcommons.getTYPECATEGORY());
    }
    if (iiiVcommons.isSetSITELABEL()) {
      avvisoAggiudicazione.setSITELABEL(iiiVcommons.getSITELABEL());
    }
    if (iiiVcommons.isSetSITENUTS()) {
      avvisoAggiudicazione.setSITENUTS(iiiVcommons.getSITENUTS());
    }

    if (iiiVcommons.isSetSHORTDESCRIPTION()) {
      avvisoAggiudicazione.setSHORTDESCRIPTION(iiiVcommons.getSHORTDESCRIPTION());
    }
    if (iiiVcommons.isSetCPV()) {
      avvisoAggiudicazione.setCPV(iiiVcommons.getCPV());
    }
    avvisoAggiudicazione.setSCOPECOST(iiiVcommons.getSCOPECOST());
    avvisoAggiudicazione.setTYPEPROCEDURE(iiiVcommons.getTYPEPROCEDURE());
    avvisoAggiudicazione.setAWARDCRITERIA(iiiVcommons.getAWARDCRITERIA());
    avvisoAggiudicazione.setIDGARA(iiiVcommons.getIDGARA());

    // Sezione V: aggiudicazione dell'appalto
    String selectW9LOTT = "select w9lott.codlott, "
        + " w9lott.cig, "
        + " w9lott.oggetto, "
        + " w9appa.data_verb_aggiudicazione, "
        + " w9lott.importo_tot, "
        + " w9appa.importo_aggiudicazione,"
        + " w9appa.flag_rich_subappalto from w9lott, w9appa"
        + " where w9lott.codgara = w9appa.codgara "
        + " and w9lott.codlott = w9appa.codlott"
        + " and w9appa.num_appa = 1 and w9lott.codgara = ? "
        + " order by w9lott.nlotto";

    List< ? > datiW9LOTT = sqlManager.getListVector(selectW9LOTT,
        new Object[] { codgara });
    if (datiW9LOTT != null && datiW9LOTT.size() > 0) {
      for (int i = 0; i < datiW9LOTT.size(); i++) {
        LottoAggiudicatoType lottoAggiudicato = avvisoAggiudicazione.addNewLOTTIAGGIUDICATI();

        Long codlott = (Long) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 0).getValue();

        // Numero progressivo
        lottoAggiudicato.setNUM(i + 1);

        // CIG
        String cig = (String) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 1).getValue();
        if (cig != null) {
          lottoAggiudicato.setCIG(cig);
        } else {
          throw new Exception("Lotto "
              + codlott.toString()
              + ": "
              + CTR_LOTTO_CIG);
        }

        // Denominazione
        String oggetto = (String) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 2).getValue();
        if (oggetto != null) {
          lottoAggiudicato.setTITLE(oggetto);
        } else {
          throw new Exception("Lotto "
              + codlott.toString()
              + ": "
              + CTR_LOTTO_TITLE);
        }

        // Data di aggiudicazione
        Date dataVerbaleAggiudicazione = (Date) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 3).getValue();
        if (dataVerbaleAggiudicazione != null) {
          Calendar dataVerbCalendar = new GregorianCalendar();
          dataVerbCalendar.setTime(dataVerbaleAggiudicazione);
          lottoAggiudicato.setCONTRACTAWARDDATE(dataVerbCalendar);
        } else {
          throw new Exception("Lotto "
              + codlott.toString()
              + ": "
              + CTR_LOTTO_CONTRACT_AWARD_DATE);
        }

        // Operatore economico aggiudicatario
        String selectW9AGGI = "select codimp "
            + " from w9aggi"
            + " where codgara = ? "
            + " and codlott = ?"
            + " and num_appa = 1 "
            + " order by num_aggi";
        String codimp = (String) sqlManager.getObject(selectW9AGGI,
            new Object[] { codgara, codlott });
        if (codimp != null) {
          String selectIMPR = "select nomest, "
              + " indimp, "
              + " nciimp, "
              + " proimp, "
              + " capimp, "
              + " locimp, "
              + " nazimp, "
              + " telimp, "
              + " faximp, "
              + " emaiip, "
              + " cfimp, "
              + " pivimp, "
              + " indweb "
              + " from impr where codimp = ?";
          List< ? > datiIMPR = sqlManager.getVector(selectIMPR,
              new Object[] { codimp });
          if (datiIMPR != null && datiIMPR.size() > 0) {

            EconomicOperatorType economicOperator = lottoAggiudicato.addNewECONOMICOPERATOR();

            String nomest = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 0).getValue();
            if (nomest != null) {
              economicOperator.setNOMEST(nomest);
            }

            String indimp = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 1).getValue();
            if (indimp != null) {
              economicOperator.setINDIMP(indimp);
            }

            String nciimp = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 2).getValue();
            if (nciimp != null) {
              economicOperator.setNCIIMP(nciimp);
            }

            String proimp = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 3).getValue();
            if (proimp != null) {
              economicOperator.setPROIMP(proimp);
            }

            String capimp = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 4).getValue();
            if (capimp != null) {
              economicOperator.setCAPIMP(capimp);
            }

            String locimp = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 5).getValue();
            if (locimp != null) {
              economicOperator.setLOCIMP(locimp);
            }

            Long nazimp = (Long) SqlManager.getValueFromVectorParam(datiIMPR, 6).getValue();
            if (nazimp != null) {
              economicOperator.setNAZIMP(Ag010Type.Enum.forInt(nazimp.intValue()));
            }
            String telimp = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 7).getValue();
            if (telimp != null) {
              economicOperator.setTELIMP(telimp);
            }

            String faximp = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 8).getValue();
            if (faximp != null) {
              economicOperator.setFAXIMP(faximp);
            }

            String emaiip = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 9).getValue();
            if (emaiip != null) {
              economicOperator.setEMAIIP(emaiip);
            }

            String cfimp = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 10).getValue();
            if (cfimp != null) {
              economicOperator.setCFIMP(cfimp);
            }

            String pivimp = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 11).getValue();
            if (pivimp != null) {
              economicOperator.setPIVIMP(pivimp);
            }

            String indweb = (String) SqlManager.getValueFromVectorParam(
                datiIMPR, 12).getValue();
            if (indweb != null) {
              economicOperator.setINDWEB(indweb);
            }
          }

        } else {
          throw new Exception("Lotto "
              + codlott.toString()
              + ": "
              + CTR_LOTTO_ECONOMIC_OPERATOR);
        }

        // Valore totale inizialmente stimato
        Double importo_tot = (Double) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 4).getValue();
        if (importo_tot != null) {
          lottoAggiudicato.setINITIALCOST(importo_tot.doubleValue());
        } else {
          throw new Exception("Lotto "
              + codlott.toString()
              + ": "
              + CTR_LOTTO_INITIAL_COST);
        }

        // Valore totale finale
        Double importoAggiudicazione = (Double) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 5).getValue();
        if (importoAggiudicazione != null) {
          lottoAggiudicato.setFINALCOST(importoAggiudicazione.doubleValue());
        } else {
          throw new Exception("Lotto "
              + codlott.toString()
              + ": "
              + CTR_LOTTO_FINAL_COST);
        }

        // E' possibile che venga subappaltato
        String flagRichSubappalto = (String) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(i), 6).getValue();
        if (flagRichSubappalto != null) {
          lottoAggiudicato.setSUBCONTRACTED(SnType.Enum.forString(flagRichSubappalto));
        }
      }
    } else {
      throw new Exception(CTR_NO_LOTTOAGGIUDICATO);
    }

    ByteArrayOutputStream baosAvvisoAggiudicazione = new ByteArrayOutputStream();
    avvisoAggiudicazioneDocument.save(baosAvvisoAggiudicazione);
    xml = baosAvvisoAggiudicazione.toString();
    baosAvvisoAggiudicazione.close();

    if (logger.isDebugEnabled()) {
      logger.debug("inserisciAvvisoAggiudicazione: fine metodo");
    }
    return xml;

  }

  /**
   * Restituisce XML con i dati del bando di gara semplificato.
   * 
   * @param codgara
   * @return
   * @throws SQLException
   * @throws IOException
   * @throws Exception
   */
  private String getXMLBandoGaraSemplificato(Long codgara) throws SQLException,
      IOException, Exception {

    if (logger.isDebugEnabled())
      logger.debug("inserisciBandoGaraSemplificato: inizio metodo");

    String xml = null;

    BandoGaraSemplificatoDocument bandoGaraSemplificatoDocument = BandoGaraSemplificatoDocument.Factory.newInstance();
    bandoGaraSemplificatoDocument.documentProperties().setEncoding("UTF-8");
    BandoGaraSemplificatoType bandoGaraSemplificato = bandoGaraSemplificatoDocument.addNewBandoGaraSemplificato();

    String selectW9GARA = "select codein, "
        + " oggetto, "
        + " importo_gara, "
        + " idavgara "
        + " from w9gara "
        + " where codgara = ?";

    List< ? > datiW9GARA = sqlManager.getVector(selectW9GARA,
        new Object[] { codgara });
    if (datiW9GARA != null && datiW9GARA.size() > 0) {

      // Amministrazione
      String codein = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 0).getValue();
      if (codein != null) {
        AuthorityType authority = AuthorityType.Factory.newInstance();
        authority = this.getAuthority(codein);
        bandoGaraSemplificato.setAUTHORITY(authority);
      } else {
        throw new Exception(CTR_AUTHORITY);
      }

      // Titolo del contratto e descrizione breve
      String oggetto = (String) SqlManager.getValueFromVectorParam(datiW9GARA,
          1).getValue();
      if (oggetto != null) {
        bandoGaraSemplificato.setTITLECONTRACT(oggetto);
        bandoGaraSemplificato.setSHORTDESCRIPTION(oggetto);
      } else {
        throw new Exception(CTR_TITLE_CONTRACT);
      }

      // Quantitativo o entita' totale
      Double importo_gara = (Double) SqlManager.getValueFromVectorParam(
          datiW9GARA, 2).getValue();
      if (importo_gara != null) {
        bandoGaraSemplificato.setSCOPECOST(importo_gara.doubleValue());
      } else {
        throw new Exception(CTR_SCOPE_COST);
      }
      
      // Identificativo della gara
      String idavgara = (String) SqlManager.getValueFromVectorParam(datiW9GARA,
          3).getValue();
      if (idavgara != null) {
        bandoGaraSemplificato.setIDGARA(idavgara);
      } else {
        throw new Exception(CTR_IDGARA);
      }
      
      String selectW9LOTT = "select codlott,"
          + " tipo_contratto "
          + " from w9lott "
          + " where codgara = ? order by nlotto ";
      List< ? > datiW9LOTT = sqlManager.getListVector(selectW9LOTT,
          new Object[] { codgara });
      if (datiW9LOTT != null && datiW9LOTT.size() > 0) {

        // Dati principali del bando di gara: si considera il primo lotto.
        // Se e' l'unico lotto, allora non deve essere indicata alcuna
        // suddivisione in lotti
        // Tipo di contratto
        String tipoContratto = (String) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(0), 1).getValue();
        if (tipoContratto != null) {
          if ("L".equals(tipoContratto) || "CL".equals(tipoContratto)) {
            bandoGaraSemplificato.setTYPECONTRACT(W3Z40Type.WORK);
          } else if ("S".equals(tipoContratto) || "CS".equals(tipoContratto)) {
            bandoGaraSemplificato.setTYPECONTRACT(W3Z40Type.SERV);
          } else if ("F".equals(tipoContratto)) {
            bandoGaraSemplificato.setTYPECONTRACT(W3Z40Type.SUPP);
          }
        } else {
          throw new Exception(CTR_TYPE_CONTRACT);
        }

        // CPV
        Long codlott = (Long) SqlManager.getValueFromVectorParam(
            datiW9LOTT.get(0), 0).getValue();

        CPVType cpvGara = CPVType.Factory.newInstance();
        cpvGara = this.getCPV(codgara, codlott);
        if (cpvGara != null && cpvGara.getCPVMAIN() != null) {
          bandoGaraSemplificato.setCPV(cpvGara);
        }
      } else {
        throw new Exception(CTR_NO_LOTTO);
      }
    }

    ByteArrayOutputStream baosBandoGaraSemplificato = new ByteArrayOutputStream();
    bandoGaraSemplificatoDocument.save(baosBandoGaraSemplificato);
    xml = baosBandoGaraSemplificato.toString();
    baosBandoGaraSemplificato.close();

    if (logger.isDebugEnabled()) {
      logger.debug("inserisciBandoGaraSemplificato: fine metodo");
    }
    return xml;

  }

}
