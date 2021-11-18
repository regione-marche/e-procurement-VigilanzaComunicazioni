package it.eldasoft.w9.bl;


import it.eldasoft.gene.bl.GestoreProfili;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.spring.ContextAwareDataSourceProxy;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.BlobFile;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.bean.AvvisoBean;
import it.eldasoft.w9.common.bean.DatiComuniBean;
import it.eldasoft.w9.common.bean.GaraLottoBean;
import it.eldasoft.w9.common.bean.PianoTriennaleBean;
import it.eldasoft.w9.common.bean.PubblicazioneBean;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.TransformerException;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlValidationError;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.TransactionStatus;

/**
 * Manager per la creazione dell'XML dei flussi in partenza da SA verso ORT. 
 */
public class CreazioneXmlExportManager {

  /**
   * Encoding del XML verso il proxy-sitat.
   */
  private static final String ENCODING = "UTF-8";
  
  /**
   * Logger della classe.
   */
  static Logger logger = Logger.getLogger(CreazioneXmlExportManager.class);
 
  /**
   * 
   * @param avvisoBean
   * @param sqlManager
   * @param w9Manager
   * @param requestHttp
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   * @throws ParseException
   */
  public static XmlObject getXmlAvviso(AvvisoBean avvisoBean, SqlManager sqlManager,
      W9Manager w9Manager) throws SQLException, GestoreException, IOException, ParseException {
    
    XmlObject xmlObject = null;

    if (CostantiW9.PUBBLICAZIONE_AVVISO == avvisoBean.getDatiComuni().getCodiceFase()) {
      // Avviso 989
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaPubblicazioneAvvisoDocument(
          avvisoBean, sqlManager, w9Manager);
    } else {
      avvisoBean.getDatiComuni().setEsito(Boolean.FALSE); 
      logger.error("Tentativo di invio di una fase non gestita. Il codice fase non rientra tra i valori " +
          "del tabellato W3020. Codice fase usato:" + avvisoBean.getDatiComuni().getCodiceFase() );
    }
    
    return xmlObject;
  }

  /**
   * 
   * @param pianoTriXmlBean
   * @param sqlManager
   * @param w9Manager
   * @param requestHttp
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   * @throws ParseException
   */
  public static XmlObject getXmlPianoTriennale(PianoTriennaleBean pianoTriennaleBean,
      SqlManager sqlManager, W9Manager w9Manager, HttpServletRequest requestHttp)
          throws SQLException, GestoreException, IOException, ParseException {
    
    XmlObject xmlObject = null;
    
    switch ((int) pianoTriennaleBean.getDatiComuni().getCodiceFase()) {
    case CostantiW9.PROGRAMMA_TRIENNALE_LAVORI:
      // Programma triennale ed elenco annuale Lavori  992
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument(
          pianoTriennaleBean, sqlManager, w9Manager, requestHttp);
      break;

    case CostantiW9.PROGRAMMA_TRIENNALE_FORNITURE_SERVIZI:
      // Programma triennale ed elenco annuale Forniture e Servizi 991
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument(
          pianoTriennaleBean, sqlManager, w9Manager, requestHttp);
      break;
    case CostantiW9.PROGRAMMA_TRIENNALE_OPERE_PUBBLICHE:
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaApprovazioneProgrammaTriennaleOperePubblicheDocument(
          pianoTriennaleBean, sqlManager, w9Manager, requestHttp);
      break;    
    case CostantiW9.PROGRAMMA_BIENNALE_ACQUISTI:
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquisti(
          pianoTriennaleBean, sqlManager, w9Manager, requestHttp);
      break;
    default:
      pianoTriennaleBean.getDatiComuni().setEsito(Boolean.FALSE); 
      logger.error("Tentativo di invio di una fase non gestita. Il codice fase non rientra tra i valori " +
          "del tabellato W3020. Codice fase usato:" + pianoTriennaleBean.getDatiComuni().getCodiceFase() );
      break;
    }
    
    return xmlObject;
  }
  
  public static XmlObject getXmlFase(long codiceFase, long progressivoFase, long codiceGara,
      long codiceLotto, long codiceCompositore, String nomeCompositore, String mailCompositore,
      Long tipoInvio, String noteInvio, String cfStazioneAppaltante, String profiloAttivo,
      SqlManager sqlManager, W9Manager w9Manager, Object[] params, Boolean esito,
      GestoreProfili gestoreProfili)
          throws SQLException, GestoreException, IOException, ParseException {

    DatiComuniBean datiComuni = new DatiComuniBean();
    datiComuni.setCodiceFase(codiceFase);
    datiComuni.setProgressivoFase(progressivoFase);
    datiComuni.setCodiceCompositore(codiceCompositore);
    datiComuni.setNomeCompositore(nomeCompositore);
    datiComuni.setMailCompositore(mailCompositore);
    datiComuni.setTipoInvio(tipoInvio);
    datiComuni.setNoteInvio(noteInvio);
    datiComuni.setCfStazioneAppaltante(cfStazioneAppaltante);
    datiComuni.setEsito(esito);
    
    
    GaraLottoBean garaLottoBean = new GaraLottoBean();              
    garaLottoBean.setDatiComuni(datiComuni);
    garaLottoBean.setCodiceGara(codiceGara);
    garaLottoBean.setCodiceLotto(codiceLotto);
    garaLottoBean.setExportIncarichiProfessionali(gestoreProfili.checkProtec(
        profiloAttivo, "PAGE", "VIS", "W9.W9LOTT-scheda.INCA"));
    
    return CreazioneXmlExportManager.getXmlGaraLottoFasi(garaLottoBean, sqlManager, w9Manager);
  }
    
  /**
   * 
   * @param garaLottoXmlBean
   * @param sqlManager
   * @param w9Manager
   * @param requestHttp
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   * @throws ParseException
   */
  public static XmlObject getXmlGaraLottoFasi(GaraLottoBean garaLottoBean,
        SqlManager sqlManager, W9Manager w9Manager)
            throws SQLException, GestoreException, IOException, ParseException {
    
    XmlObject xmlObject = null;
    
    switch ((int) garaLottoBean.getDatiComuniXml().getCodiceFase()) {
    case CostantiW9.COMUNICAZIONE_ESITO: // Comunicazione esito
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaComunicazioneEsitoDocument(
          garaLottoBean, sqlManager, w9Manager);
      break;

    case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA: // Fase aggiudicazione/affidamento (>150.000 euro) A05
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaAggiudicazioneAppaltoSopraSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA: // Fase iniziale esecuzione contratto (>150.000 euro) A06
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaInizioContrattoSopraSogliaDocument(
          garaLottoBean, sqlManager, w9Manager);
      break;

    case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA: // Fase esecuzione e avanzamento del contratto (>150.000 euro) A08
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaAvanzamentoContrattoSopraSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA: // Fase di conclusione del contratto (>150.000 euro) A09
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaConclusioneContrattoSopraSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.COLLAUDO_CONTRATTO: // Fase di collaudo del contratto A11
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaCollaudoContrattoSopraSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.SOSPENSIONE_CONTRATTO: // Sospensione del contratto A12
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaSospensioneContrattoSopraSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.VARIANTE_CONTRATTO: // Variante del contratto A13
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaVarianteContrattoSopraSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.ACCORDO_BONARIO: // Accordi bonari A14
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaAccordoBonarioContrattoSopraSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.SUBAPPALTO: // Subappalti A15
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaSubappaltoContrattoSopraSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.ISTANZA_RECESSO: // Istanza di recesso A16
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaRecessoContrattoSopraSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.STIPULA_ACCORDO_QUADRO: // Stipula accordo quadro  A20
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaStipulaAccordoQuadroDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.ADESIONE_ACCORDO_QUADRO: // Adesione accordo quadro A21
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaAdesioneAccordoQuadroDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE: // Fase aggiudicazione/affidamento appalto (<150.000 euro) A04
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaAggiudicazioneAppaltoSottoSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO: // Fase iniziale esecuzione contratto (<150.000 euro) A07
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaInizioContrattoSottoSogliaDocument(
          garaLottoBean, sqlManager, w9Manager);
      break;

    case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO: // Fase di conclusione del contratto (<150.000 euro) A10
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaConclusioneContrattoSottoSogliaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI: // Scheda segnalazione infortuni B07
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaInfortunioDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI: // Inadempienze predisposizioni sicurezza e regolarita' lavoro B06
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaInadempienzeSicurezzaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA: // Esito negativo verifica regolarita' contributiva ed assicurativa B03
      xmlObject =  CreazioneXmlExportManager.getRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA: // Esito negativo verifica idoneita' tecnico-professionale dell'impresa aggiudicataria B02
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaEsitoNegCheckIdoneitaDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.APERTURA_CANTIERE: // Apertura cantiere B08
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaAperturaCantiereDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.ANAGRAFICA_GARA_LOTTI: // Anagrafica Gara e Lotto/i + CIG A03
        xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaAnagraficaLottoGaraDocument(
            garaLottoBean, sqlManager, w9Manager);
      break;

    case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI: // Elenco imprese invitate/partecipanti 101
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument(
          garaLottoBean, sqlManager);
      break;

    case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO: // Avanzamento contratto appalto semplificato 102
      xmlObject = CreazioneXmlExportManager.getRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument(
          garaLottoBean, sqlManager);
      break;

    default:
      garaLottoBean.getDatiComuniXml().setEsito(Boolean.FALSE);
      garaLottoBean.setMessaggioDiErrore("");
      logger.error("Tentativo di invio di una fase non gestita. Il codice fase non rientra tra i valori " +
          "del tabellato W3020. Codice fase usato:" + garaLottoBean.getDatiComuniXml().getCodiceFase());
      break;
    }
    
    return xmlObject;
  }
  
  
  public static XmlObject getXmlPubblicazioneDocumento(PubblicazioneBean pubblicazioneBean,
      SqlManager sqlManager, W9Manager w9Manager)
          throws SQLException, GestoreException, IOException, ParseException {
  
    RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument pubblicazioneDoc =
      RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiDocument.Factory.newInstance();
    pubblicazioneDoc.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiType richiestaPubblicazione =
        pubblicazioneDoc.addNewRichiestaRichiestaRispostaSincronaPubblicazioneDocumenti();
    
    Long codiceGara = pubblicazioneBean.getCodiceGara();
    Long numeroPubblicazione = pubblicazioneBean.getNumeroPubblicazione();
    
    CreazioneXmlExportManager.valorizzaFaseType(richiestaPubblicazione.addNewFase(), pubblicazioneBean.getDatiComuniXml());
    
    PubblicazioneType pubblType = richiestaPubblicazione.addNewPubblicazione();

    String idAvGara = (String) sqlManager.getObject("select IDAVGARA from W9GARA where CODGARA=?", new Object[] { codiceGara });
    pubblType.setW3IDGARA(idAvGara);
    pubblType.setW9PBNUMPUBB(numeroPubblicazione.intValue());

    String sqlW9Pubblicazioni = 
        "select TIPDOC, DESCRIZ, DATAPUBB, DATASCAD, DATA_DECRETO, NUM_PROVVEDIMENTO, DATA_PROVVEDIMENTO, DATA_STIPULA, NUM_REPERTORIO " +
        " from W9PUBBLICAZIONI where CODGARA = ? and NUM_PUBB = ?";

    DataColumnContainer w9pubblicazioni = new DataColumnContainer(sqlManager, "W9PUBBLICAZIONI", sqlW9Pubblicazioni, new Object[] {codiceGara, numeroPubblicazione});
    Calendar date = Calendar.getInstance();
    pubblType.setW9PBTIPDOC(w9pubblicazioni.getLong("TIPDOC").intValue());

    if (StringUtils.isNotEmpty(w9pubblicazioni.getString("DESCRIZ"))) {
      pubblType.setW9PBDESCRIZ(w9pubblicazioni.getString("DESCRIZ"));
    }
    
    if (w9pubblicazioni.getData("DATAPUBB") != null) {
      date.setTime(w9pubblicazioni.getData("DATAPUBB"));
      pubblType.setW9PBDATAPUBB(date);
    }
    
    if (w9pubblicazioni.getData("DATASCAD") != null) {
      date.setTime(w9pubblicazioni.getData("DATASCAD"));
      pubblType.setW9PBDATASCAD(date);
    }
    
    if (w9pubblicazioni.getData("DATA_DECRETO") != null) {
      date.setTime(w9pubblicazioni.getData("DATA_DECRETO"));
      pubblType.setW9PBDATADEC(date);
    }

    if (StringUtils.isNotEmpty(w9pubblicazioni.getString("NUM_PROVVEDIMENTO"))) {
        pubblType.setW9PBNUMPR(w9pubblicazioni.getString("NUM_PROVVEDIMENTO"));
      }
    
    if (w9pubblicazioni.getData("DATA_PROVVEDIMENTO") != null) {
        date.setTime(w9pubblicazioni.getData("DATA_PROVVEDIMENTO"));
        pubblType.setW9PBDATAPR(date);
    }

    if (w9pubblicazioni.getData("DATA_STIPULA") != null) {
        date.setTime(w9pubblicazioni.getData("DATA_STIPULA"));
        pubblType.setW9PBDATAST(date);
      }
    
    if (StringUtils.isNotEmpty(w9pubblicazioni.getString("NUM_REPERTORIO"))) {
      pubblType.setW9PBNUMPER(w9pubblicazioni.getString("NUM_REPERTORIO"));
    }
    
    // Valorizzazione dei CIG dei lotti a cui la pubblicazione e' associata
    List< ? > listaCigPubblicazione = sqlManager.getListHashMap(
        "select l.CIG from W9PUBLOTTO p, W9LOTT l where p.CODGARA=? and p.NUM_PUBB=? " +
          " and p.CODGARA=l.CODGARA and p.CODLOTT=l.CODLOTT order by p.CODLOTT asc",
        new Object[] { codiceGara, numeroPubblicazione });
    
    if (listaCigPubblicazione != null && listaCigPubblicazione.size() > 0) {
      for (int i = 0; i < listaCigPubblicazione.size(); i++) {
        HashMap< ? , ? > hmCigPubbl = (HashMap < ? , ? >) listaCigPubblicazione.get(i);
        richiestaPubblicazione.addCigLotti(((JdbcParametro) hmCigPubbl.get("W9LOTT.CIG")).getStringValue());
      }
    }
    
    // Valorizzazione dei documenti che compongono la pubblicazione
    CreazioneXmlExportManager.valorizzaDocumentiPubblicazione(richiestaPubblicazione, codiceGara, 
        numeroPubblicazione, sqlManager, w9Manager);
    
    return pubblicazioneDoc;
  }
  
  
  /**
   * Validazione del messaggio XML prodotto prima dell'invio al proxy.
   * 
   * @param oggettoDoc xml object
   * @return Ritorna un ArrayList di XmlValidationError nel caso la validazione del messaggio non sia rispettata.
   * @throws GestoreException GestoreException
   * @throws TransformerException TransformerException
   * @throws IOException IOException
   */
  public static final ArrayList<XmlValidationError> validaMsgXML(final XmlObject oggettoDoc)
      throws GestoreException, TransformerException, IOException {
    ArrayList<XmlValidationError> validationErrors = new ArrayList<XmlValidationError>();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    oggettoDoc.save(baos);
    baos.close();
    XmlOptions validationOptions = new XmlOptions();
    validationOptions.setErrorListener(validationErrors);
    boolean esitoCheckSintassi = oggettoDoc.validate(validationOptions);

    if (logger.isDebugEnabled()) {
      logger.debug("Validazione XML della richiesta da inviare:" + esitoCheckSintassi);
    }
    if (validationErrors.size() > 0) {
      return validationErrors;
    } else {
      return null;
    }
  }

  private static void valorizzaFaseType(final FaseType fase, DatiComuniBean datiComuniBean) {
    
    if (StringUtils.isNotEmpty(datiComuniBean.getMailCompositore())) {
      fase.setGUSYSEMAI(datiComuniBean.getMailCompositore());
    } else {
      fase.setGUSYSEMAI("");
    }

    if (StringUtils.isNotEmpty(datiComuniBean.getNomeCompositore())) {
      fase.setW9FLAUTORE(datiComuniBean.getNomeCompositore());
    } else {
      fase.setW9FLAUTORE("");
    }

    // Set della versione del tracciato XML del flusso
    String versioneTracciatoXML = ConfigManager.getValore("it.eldasoft.sitatproxy.versioneTracciatoXML");
    if (StringUtils.isNotEmpty(versioneTracciatoXML)) {
      fase.setW9FLVERXML(versioneTracciatoXML);
    } else {
      fase.setW9FLVERXML("");
    }
      
    if (W3020Type.Enum.forString("" + datiComuniBean.getCodiceFase()) != null) {
    	  fase.setW3FASEESEC(W3020Type.Enum.forString("" + datiComuniBean.getCodiceFase()));
    }

    if (datiComuniBean.getTipoInvio() != null) {
      fase.setW3PGTINVIO2(W3998Type.Enum.forString(datiComuniBean.getTipoInvio().toString()));
    }
    if (StringUtils.isNotEmpty(datiComuniBean.getNoteInvio())) {
      fase.setW9NOTINVIO(datiComuniBean.getNoteInvio());
    }
    if (StringUtils.isNotEmpty(datiComuniBean.getCfStazioneAppaltante())) {
      fase.setW9FLCFSA(datiComuniBean.getCfStazioneAppaltante());
    }
    if (datiComuniBean.getDaExport() != null) {
        fase.setW9FADAEXP(convertiFlag(datiComuniBean.getDaExport().toString()));
    }
  }
  
  /**
   * Metodo per il recupero dei dati relativi alla fase, e' alternativo alla
   * valorizzazione della fase estesa.
   * 
   * @param fase FaseType
   * @param codiceGara Codice della gara
   * @param codiceLotto Codice del lotto
   * @param codiceFase Codice della fase
   * @param progressivoFase Progressivo della fase
   * @param codiceCompositore Codice del compositore del flusso
   * @param dataColumnContainer DataColumnContainer
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private static void valorizzaFaseEstesaType(final FaseEstesaType fase,
      GaraLottoBean garaLottoBean, final SqlManager sqlManager) throws SQLException {

    // Valorizzazione dei dati in comune con FaseType (FaseEstesaType estende FaseType)
    CreazioneXmlExportManager.valorizzaFaseType(fase, garaLottoBean.getDatiComuniXml());

    String cig = UtilitySITAT.getCIGLotto(garaLottoBean.getCodiceGara(),
        garaLottoBean.getCodiceLotto(), sqlManager);
    if (StringUtils.isNotEmpty(cig)) {
      fase.setW3FACIG(cig);
    }

    if (garaLottoBean.getDatiComuniXml().getProgressivoFase() > 0) {
      fase.setW3NUM5((int) garaLottoBean.getDatiComuniXml().getProgressivoFase());
    }
  }

  /**
   * 
   * @param garaLottoBean
   * @param sqlManager
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   * @throws ParseException
   */
  public static RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument
    getRichiestaRispostaSincronaEliminazioneSchedaDocument(GaraLottoBean garaLottoBean,
        SqlManager sqlManager) throws SQLException, GestoreException,
            IOException, ParseException {
    
    RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument eliminazioneSchedaDocument =
    	RichiestaRichiestaRispostaSincronaEliminazioneSchedaDocument.Factory.newInstance();
    eliminazioneSchedaDocument.documentProperties().setEncoding(CreazioneXmlExportManager.ENCODING);
    RichiestaRichiestaRispostaSincronaEliminazioneSchedaType eliminazioneScheda =
    	eliminazioneSchedaDocument.addNewRichiestaRichiestaRispostaSincronaEliminazioneScheda();
 
    FaseEstesaType faseEstesa = eliminazioneScheda.addNewFase();
    
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    return eliminazioneSchedaDocument;
  }
  
  /**
   * 
   * @param garaLottoBean
   * @param sqlManager
   * @param w9Manager
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument
    getRichiestaRispostaSincronaComunicazioneEsitoDocument(GaraLottoBean garaLottoBean,
        SqlManager sqlManager, W9Manager w9Manager) throws SQLException, GestoreException,
            IOException, ParseException {
    
    long codiceGara  = garaLottoBean.getCodiceGara();
    long codiceLotto = garaLottoBean.getCodiceLotto();
    
    long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument comunicazioneEsitoDocument =
      RichiestaRichiestaRispostaSincronaComunicazioneEsitoDocument.Factory.newInstance();
    comunicazioneEsitoDocument.documentProperties().setEncoding(CreazioneXmlExportManager.ENCODING);
    RichiestaRichiestaRispostaSincronaComunicazioneEsitoType comunicazioneEsito =
      comunicazioneEsitoDocument.addNewRichiestaRichiestaRispostaSincronaComunicazioneEsito();
 
    FaseEstesaType faseEstesa = comunicazioneEsito.addNewFase();
    Tab30Type tab30 = comunicazioneEsito.addNewTab30();
    
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    CreazioneXmlExportManager.valorizzaTab30Type(tab30, codiceGara, codiceLotto, sqlManager, w9Manager);
    CreazioneXmlExportManager.valorizzaTab192BisType(comunicazioneEsito, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    
    return comunicazioneEsitoDocument;

  }
  
  /**
   * @param impl
   * @param w9Manager
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document
      getRichiestaRispostaSincronaAggiudicazioneAppaltoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, IOException, ParseException {
    
    RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document aggiudicazioneDocument =
      RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Document.Factory.newInstance();
    aggiudicazioneDocument.documentProperties().setEncoding(CreazioneXmlExportManager.ENCODING);
    RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000Type richiestaAgg5 =
      aggiudicazioneDocument.addNewRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSopra150000();

    FaseEstesaType faseEstesa = richiestaAgg5.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);

    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    Tab18Type tab18 = richiestaAgg5.addNewTab18();
    ListaTab181Type listaTab181 = richiestaAgg5.addNewListaTab181();
    ListaTab182Type listaTab182 = richiestaAgg5.addNewListaTab182();
    ListaTab183Type listaTab183 = richiestaAgg5.addNewListaTab183();
    CreazioneXmlExportManager.valorizzaTab18Type(tab18, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab181Type(listaTab181, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab182Type(listaTab182, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab183Type(listaTab183, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    
    return aggiudicazioneDocument;
  }
  
  /**
   * @param impl
   * @param w9Manager
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   * @throws IOException
   */
  private static RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document
      getRichiestaRispostaSincronaInizioContrattoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager, W9Manager w9Manager)
              throws SQLException, GestoreException, ParseException, IOException {

    RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document inizio6 =
      RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Document.Factory.newInstance();
    inizio6.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Type richiestaInizioA06 =
      inizio6.addNewRichiestaRichiestaRispostaSincronaInizioContrattoSopra150000();
 
    FaseEstesaType faseEstesa = richiestaInizioA06.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);

    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    Tab19Type tab19 = richiestaInizioA06.addNewTab19();
    ListaTab191Type listaTab191 = richiestaInizioA06.addNewListaTab191();
    CreazioneXmlExportManager.valorizzaTab19Type(tab19, codiceGara, codiceLotto, progressivoFase,
        sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab191Type(listaTab191, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab192Type(richiestaInizioA06, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab8bis3Type(richiestaInizioA06, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab8bis31Type(richiestaInizioA06, codiceGara, codiceLotto,
        progressivoFase, sqlManager, w9Manager);
    
    if (richiestaInizioA06.isSetTab194() && StringUtils.isEmpty(richiestaInizioA06.getTab194().getW3MSDESCMIS())) {
      richiestaInizioA06.unsetTab194();
    }
    
    Tab8Bis2Type tab19Tab8bis2 = CreazioneXmlExportManager.getTab8Bis2Type(codiceGara,
        codiceLotto, progressivoFase, sqlManager);
    if (tab19Tab8bis2 != null) {
      richiestaInizioA06.setTab8Bis2(tab19Tab8bis2);
    }
    return inizio6;
  }
  
  /**
   * 
   * @param impl
   * @param w9Manager
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @param sqlManager
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   * @throws IOException
   */
  private static RichiestaRichiestaRispostaSincronaAvanzamentoContrattoSopra150000Document
      getRichiestaRispostaSincronaAvanzamentoContrattoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException, IOException {
  
    RichiestaRichiestaRispostaSincronaAvanzamentoContrattoSopra150000Document avanzamento =
      RichiestaRichiestaRispostaSincronaAvanzamentoContrattoSopra150000Document.Factory.newInstance();
    avanzamento.documentProperties().setEncoding(CreazioneXmlExportManager.ENCODING);
    RichiestaRichiestaRispostaSincronaAvanzamentoContrattoSopra150000Type richiestaAvanzamento =
      avanzamento.addNewRichiestaRichiestaRispostaSincronaAvanzamentoContrattoSopra150000();
  
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaAvanzamento.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);

    Tab20Type tab20 = richiestaAvanzamento.addNewTab20();
    CreazioneXmlExportManager.valorizzaTab20Type(tab20, codiceGara, codiceLotto, progressivoFase, sqlManager);
  
    return avanzamento;
  }
  
  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document
      getRichiestaRispostaSincronaConclusioneContrattoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
          throws SQLException, GestoreException, ParseException {
    
    RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document conclusioneSopraSoglia =
      RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Document.Factory.newInstance();
    conclusioneSopraSoglia.documentProperties().setEncoding(CreazioneXmlExportManager.ENCODING);
    RichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000Type richiestaConc11 =
      conclusioneSopraSoglia.addNewRichiestaRichiestaRispostaSincronaConclusioneContrattoSopra150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaConc11.addNewFase();
    Tab21Type tab21 = richiestaConc11.addNewTab21();
 
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    CreazioneXmlExportManager.valorizzaTab21Type(tab21, codiceGara, codiceLotto, progressivoFase, sqlManager);
    
    return conclusioneSopraSoglia;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param codiceGara
   * @param codiceCompositore
   * @param w9man
   * @return
   * @throws GestoreException
   * @throws SQLException
   * @throws ParseException
   * @throws IOException
   */
  private static RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument
      getRichiestaRispostaSincronaPubblicazioneAvvisoDocument(
          final AvvisoBean avvisoBean, SqlManager sqlManager, W9Manager w9Manager)
              throws GestoreException, SQLException, ParseException, IOException {
    
    RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument avviso =
      RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoDocument.Factory.newInstance();
    avviso.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaPubblicazioneAvvisoType richiestaAvviso =
      avviso.addNewRichiestaRichiestaRispostaSincronaPubblicazioneAvviso();
    FaseType fase = richiestaAvviso.addNewFase();
    Tab7Type tab7 = richiestaAvviso.addNewTab7();
 
    String codein = w9Manager.getCodein(avvisoBean.getDatiComuni().getCfStazioneAppaltante());
    CreazioneXmlExportManager.valorizzaFaseType(fase, avvisoBean.getDatiComuni());
    CreazioneXmlExportManager.valorizzaTab7Type(tab7, avvisoBean.getCodiceAvviso(), codein,
        avvisoBean.getCodiceSistema(), sqlManager);

    HashMap<String, Object> hm7 = new HashMap<String, Object>();
    hm7.put("idavviso", tab7.getW3PAVVISOID());
    hm7.put("codein", codein);
    BlobFile fileAllegato7 = w9Manager.getFileAllegato("AVVISO", hm7);
    if (fileAllegato7 != null) {
      richiestaAvviso.setFile(fileAllegato7.getStream());
    }
    return avviso;
  }

  /**
   * @param pianoTriennaleBean
   * @param sqlManager
   * @param w9Manager
   * @param requestHttp
   * 
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   */
  private static RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument
      getRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument(
          PianoTriennaleBean pianoTriennaleBean, SqlManager sqlManager, W9Manager w9Manager, 
          HttpServletRequest requestHttp) throws SQLException, GestoreException, IOException {
    
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument programmaLavori =
      RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriDocument.Factory.newInstance();
    programmaLavori.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavoriType richiestaProgLavori =
      programmaLavori.addNewRichiestaRichiestaRispostaSincronaApprovazioneProgrammaLavori();
    FaseType fase = richiestaProgLavori.addNewFase();
    Tab1L1Type tab1l = richiestaProgLavori.addNewTab1();
    ListaTab2Type listaTab2 = richiestaProgLavori.addNewListaTab2();
    ListaTab2Bis1Type listaTab2bis1 = richiestaProgLavori.addNewListaTab21();
 
    CreazioneXmlExportManager.valorizzaFaseType(fase, pianoTriennaleBean.getDatiComuni());
    CreazioneXmlExportManager.valorizzaTab1LType(tab1l, pianoTriennaleBean.getCodicePianoTriennale(),
        sqlManager, w9Manager, requestHttp);
    CreazioneXmlExportManager.valorizzaListaTab2LType(listaTab2, pianoTriennaleBean.getCodicePianoTriennale(),
        sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab2Bis1Type(listaTab2bis1, pianoTriennaleBean.getCodicePianoTriennale(),
        sqlManager);
    return programmaLavori;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param codiceCompositore
   * @param w9Manager
   * @param params
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   */
  private static RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument
      getRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument(
          PianoTriennaleBean pianoTriennaleBean, SqlManager sqlManager, W9Manager w9Manager, 
          HttpServletRequest requestHttp) throws SQLException, GestoreException, IOException {
    
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument programmaFornitureServizi =
      RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziDocument.Factory.newInstance();
    programmaFornitureServizi.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServiziType richiestaFornitureServizi =
      programmaFornitureServizi.addNewRichiestaRichiestaRispostaSincronaApprovazioneProgrammaFornitureServizi();
    FaseType fase = richiestaFornitureServizi.addNewFase();
    Tab1FSType tab1fs = richiestaFornitureServizi.addNewTab1();
    ListaTab2FSType listaTab2FS = richiestaFornitureServizi.addNewListaTab2();
 
    CreazioneXmlExportManager.valorizzaFaseType(fase, pianoTriennaleBean.getDatiComuni());
    CreazioneXmlExportManager.valorizzaTab1FSType(tab1fs, pianoTriennaleBean.getCodicePianoTriennale(),
        sqlManager, w9Manager);
    CreazioneXmlExportManager.valorizzaListaTab2FSType(listaTab2FS,
        pianoTriennaleBean.getCodicePianoTriennale(), sqlManager);
    return programmaFornitureServizi;
  }
  
  /**
   * @param pianoTriennaleBean
   * @param sqlManager
   * @param w9Manager
   * @param requestHttp
   * 
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   * @throws ParseException 
   */
  private static RichiestaRichiestaRispostaSincronaApprovazioneProgrammaTriennaleOperePubblicheDocument
      getRichiestaRispostaSincronaApprovazioneProgrammaTriennaleOperePubblicheDocument(
          PianoTriennaleBean pianoTriennaleBean, SqlManager sqlManager, W9Manager w9Manager, 
          HttpServletRequest requestHttp) throws SQLException, GestoreException, IOException, ParseException {
    
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaTriennaleOperePubblicheDocument programmaTriennaleOperePubbliche =
      RichiestaRichiestaRispostaSincronaApprovazioneProgrammaTriennaleOperePubblicheDocument.Factory.newInstance();
    programmaTriennaleOperePubbliche.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaTriennaleOperePubblicheType richiestaProgLavoriFornitureServizi =
      programmaTriennaleOperePubbliche.addNewRichiestaRichiestaRispostaSincronaApprovazioneProgrammaTriennaleOperePubbliche();
    FaseType fase = richiestaProgLavoriFornitureServizi.addNewFase();
    ProgrammaOperePubblicheType programma = richiestaProgLavoriFornitureServizi.addNewProgramma();
    CreazioneXmlExportManager.valorizzaFaseType(fase, pianoTriennaleBean.getDatiComuni());
    CreazioneXmlExportManager.valorizzaProgrammaOperePubbliche(programma, pianoTriennaleBean.getCodicePianoTriennale(),
        sqlManager, w9Manager, requestHttp);
    InterventoType[] arrayInterventi = CreazioneXmlExportManager.valorizzaArrayInterventi(
        pianoTriennaleBean.getCodicePianoTriennale(), sqlManager, w9Manager, requestHttp);
    if (arrayInterventi != null && arrayInterventi.length > 0) {
      programma.setInterventiArray(arrayInterventi);
    }
    InterventoNonRipropostoType[] arrayInterventiNonRiproposti = 
        CreazioneXmlExportManager.valorizzaArrayInterventiNonRiproposti(pianoTriennaleBean.getCodicePianoTriennale(),
        sqlManager, w9Manager);
    if (arrayInterventiNonRiproposti != null && arrayInterventiNonRiproposti.length > 0) {
      programma.setInterventiNonRipropostiArray(arrayInterventiNonRiproposti);
    }
    OperaIncompiutaType[] arrayOpereIncompiute = valorizzaOpereIncompiute(pianoTriennaleBean.getCodicePianoTriennale(), sqlManager);
    if (arrayOpereIncompiute != null && arrayOpereIncompiute.length > 0) {
      programma.setOpereIncompiuteArray(arrayOpereIncompiute);
    }
    
    return programmaTriennaleOperePubbliche;
  }
  
  /**
   * @param pianoTriennaleBean
   * @param sqlManager
   * @param w9Manager
   * @param requestHttp
   * 
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws IOException
   * @throws ParseException 
   */
  private static RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument
      getRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquisti(
          PianoTriennaleBean pianoTriennaleBean, SqlManager sqlManager, W9Manager w9Manager, 
          HttpServletRequest requestHttp) throws SQLException, GestoreException, IOException, ParseException {
    
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument programmaBiennaleAcquisti =
      RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument.Factory.newInstance();
    programmaBiennaleAcquisti.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiType richiestaProgrammaBiennaleAcquisti =
      programmaBiennaleAcquisti.addNewRichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquisti();
    FaseType fase = richiestaProgrammaBiennaleAcquisti.addNewFase();
    ProgrammaAcquistiType programma = richiestaProgrammaBiennaleAcquisti.addNewProgramma();
    CreazioneXmlExportManager.valorizzaFaseType(fase, pianoTriennaleBean.getDatiComuni());
    CreazioneXmlExportManager.valorizzaProgrammaAcquisti(programma, pianoTriennaleBean.getCodicePianoTriennale(),
        sqlManager, w9Manager, requestHttp);
    InterventoType[] arrayInterventi = CreazioneXmlExportManager.valorizzaArrayInterventi(
        pianoTriennaleBean.getCodicePianoTriennale(), sqlManager, w9Manager, requestHttp);
    if (arrayInterventi != null && arrayInterventi.length > 0) {
      programma.setInterventiArray(arrayInterventi);
    }
    InterventoNonRipropostoType[] arrayInterventiNonRiproposti = 
      CreazioneXmlExportManager.valorizzaArrayInterventiNonRiproposti(pianoTriennaleBean.getCodicePianoTriennale(),
        sqlManager, w9Manager);
    if (arrayInterventiNonRiproposti != null && arrayInterventiNonRiproposti.length > 0) {
      programma.setInterventiNonRipropostiArray(arrayInterventiNonRiproposti);
    }
    return programmaBiennaleAcquisti;
  }
  
  /**
   * Valorizzazione dell'oggetto ProgrammaOperePubblicheType per i programmi triennali
   * delle opere pubbliche secondo il DLgs 50/2016.
   * 
   * @param programmaOperePubblicheType programma delle opere pubbliche
   * @param codicePianoTriennale
   * @param sqlManager SqlManager
   * @param w9Manager W9Manager
   * @param requestHttp HttpServletRequest
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   * @throws IOException IOException
   */
  private static void valorizzaProgrammaOperePubbliche(ProgrammaOperePubblicheType programma, final long codicePianoTriennale,
      final SqlManager sqlManager, final W9Manager w9Manager, HttpServletRequest requestHttp)
          throws SQLException, GestoreException, IOException, ParseException {

    String sqlRecuperaPIATRI = "SELECT "
      + " CENINT, RESPRO, Al1TRI, AL2TRI, AL3TRI, ANNTRI, BI1TRI, BI2TRI, BI3TRI, "         // 0, 1, 2,...,8
      + " DV1TRI, DV2TRI, DV3TRI, ID,     IM1TRI, IM2TRI, IM3TRI, MU1TRI, MU2TRI, "         // 9,10,11,...,17
      + " MU3TRI, PR1TRI, PR2TRI, PR3TRI, TO1TRI, TO2TRI, TO3TRI, BRETRI, IMPACC, TIPROG, " //18,19,...,26,27
      + " AP1TRI, AP2TRI, AP3TRI, NORMA "   // 28,29,30,31
      + " DADOZI, TADOZI, NADOZI, URLADOZI, " // 32,33,34,35
      + " TAPTRI, NAPTRI, DAPTRI, URLAPPROV " // 36,37,38,39
      + " FROM PIATRI WHERE CONTRI = ? ";
    
    List< ? > listaProgrammi = sqlManager.getListVector(sqlRecuperaPIATRI, 
        new Object[] { new Long(codicePianoTriennale) });
    List< ? > datiProgramma = (List< ? >) listaProgrammi.get(0);

    int indicePiatriVector = 0; // indicePiatriVector=0
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      String codimp = datiProgramma.get(indicePiatriVector).toString();
      Arch1Type arch1 = Arch1Type.Factory.newInstance();
      valorizzaArch1Type(arch1, codimp, sqlManager);
      programma.setArch1(arch1);
    }
    indicePiatriVector++; // indicePiatriVector=1
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      String codein = datiProgramma.get(indicePiatriVector).toString();
      Arch2Type arch2 = programma.addNewArch2();
      valorizzaArch2Type(arch2, codein, sqlManager);
    }
    indicePiatriVector++; // indicePiatriVector=2
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AL1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=3
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AL2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=4
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AL3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=5
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2ANNTRI(Integer.parseInt(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=6
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2BI1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=7
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2BI2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=8
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2BI3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=9
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DV1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=10
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DV2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=11
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DV3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=12
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IDCONTRI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=13
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IM1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=14
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IM2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=15
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IM3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=16
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2MU1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=17
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2MU2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=18
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2MU3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=19
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2PR1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=20
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2PR2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=21
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2PR3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=22
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TO1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=23
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TO2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=24
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TO3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=25
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2BRETRI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=26
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IMPACC(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }

    // creo il file allegato
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("contri", new Long(codicePianoTriennale));
    indicePiatriVector++; // indicePiatriVector=27

    hm.put("tiprog", new Long(datiProgramma.get(indicePiatriVector).toString()));
    hm.put("piatriID", datiProgramma.get(12).toString());
    creaPdfNuovaNormativa(hm, sqlManager, requestHttp);
    BlobFile fileAllegato = w9Manager.getFileAllegato("PIATRI", hm);
    if (fileAllegato != null && fileAllegato.getStream() != null) {
      programma.setFile(fileAllegato.getStream());
    }

    indicePiatriVector++; // indicePiatriVector=28
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AP1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=29
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AP2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=30
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AP3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=31
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2NORMA(PT017Type.Enum.forString(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=32
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DADOZI(dateString2Calendar(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=33
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TADOZI(A2040Type.Enum.forString(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=34
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2NADOZI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=35
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2URLADOZI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=36
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DAPTRI(dateString2Calendar(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=37
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TAPTRI(A2040Type.Enum.forString(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=38
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2NAPTRI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=39
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2URLAPPROV(datiProgramma.get(indicePiatriVector).toString());
    }
  }

  
  /**
   * Valorizzazione dell'oggetto ProgrammaOperePubblicheType per i programmi triennali
   * delle opere pubbliche secondo il DLgs 50/2016.
   * 
   * @param programmaAcquistiType programma degli acquisti
   * @param codicePianoTriennale
   * @param sqlManager SqlManager
   * @param w9Manager W9Manager
   * @param requestHttp HttpServletRequest
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   * @throws IOException IOException
   */
  private static void valorizzaProgrammaAcquisti(ProgrammaAcquistiType programma, final long codicePianoTriennale,
      final SqlManager sqlManager, final W9Manager w9Manager, HttpServletRequest requestHttp)
          throws SQLException, GestoreException, IOException, ParseException {

    String sqlRecuperaPIATRI = "SELECT "
      + " CENINT, RESPRO, Al1TRI, AL2TRI, AL3TRI, ANNTRI, BI1TRI, BI2TRI, BI3TRI, "         // 0, 1, 2,...,8
      + " DV1TRI, DV2TRI, DV3TRI, ID,     IM1TRI, IM2TRI, IM3TRI, MU1TRI, MU2TRI, "         // 9,10,11,...,17
      + " MU3TRI, PR1TRI, PR2TRI, PR3TRI, TO1TRI, TO2TRI, TO3TRI, BRETRI, IMPACC, TIPROG, " //18,19,...,26,27
      + " AP1TRI, AP2TRI, AP3TRI, NORMA "   // 28,29,30,31
      + " DADOZI, TADOZI, NADOZI, URLADOZI, " // 32,33,34,35
      + " TAPTRI, NAPTRI, DAPTRI, URLAPPROV " // 36,37,38,39
      + " FROM PIATRI WHERE CONTRI = ? ";
    
    List< ? > listaProgrammi = sqlManager.getListVector(sqlRecuperaPIATRI, 
        new Object[] { new Long(codicePianoTriennale) });
    List< ? > datiProgramma = (List< ? >) listaProgrammi.get(0);

    int indicePiatriVector = 0; // indicePiatriVector=0
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      String codimp = datiProgramma.get(indicePiatriVector).toString();
      Arch1Type arch1 = Arch1Type.Factory.newInstance();
      valorizzaArch1Type(arch1, codimp, sqlManager);
      programma.setArch1(arch1);
    }
    indicePiatriVector++; // indicePiatriVector=1
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      String codein = datiProgramma.get(indicePiatriVector).toString();
      Arch2Type arch2 = programma.addNewArch2();
      valorizzaArch2Type(arch2, codein, sqlManager);
    }
    indicePiatriVector++; // indicePiatriVector=2
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AL1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=3
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AL2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=4
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AL3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=5
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2ANNTRI(Integer.parseInt(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=6
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2BI1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=7
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2BI2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=8
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2BI3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=9
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DV1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=10
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DV2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=11
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DV3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=12
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IDCONTRI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=13
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IM1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=14
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IM2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=15
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IM3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=16
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2MU1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=17
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2MU2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=18
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2MU3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=19
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2PR1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=20
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2PR2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=21
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2PR3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=22
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TO1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=23
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TO2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=24
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TO3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=25
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2BRETRI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=26
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2IMPACC(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }

    // creo il file allegato
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("contri", new Long(codicePianoTriennale));
    indicePiatriVector++; // indicePiatriVector=27

    hm.put("tiprog", new Long(datiProgramma.get(indicePiatriVector).toString()));
    hm.put("piatriID", datiProgramma.get(12).toString());
    creaPdfNuovaNormativa(hm, sqlManager, requestHttp);
    BlobFile fileAllegato = w9Manager.getFileAllegato("PIATRI", hm);
    if (fileAllegato != null && fileAllegato.getStream() != null) {
      programma.setFile(fileAllegato.getStream());
    }

    indicePiatriVector++; // indicePiatriVector=28
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AP1TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=29
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AP2TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=30
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2AP3TRI(Double.parseDouble(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=31
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2NORMA(PT017Type.Enum.forString(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=32
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DADOZI(dateString2Calendar(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=33
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TADOZI(A2040Type.Enum.forString(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=34
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2NADOZI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=35
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2URLADOZI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=36
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2DAPTRI(dateString2Calendar(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=37
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2TAPTRI(A2040Type.Enum.forString(datiProgramma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=38
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2NAPTRI(datiProgramma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=39
    if (verificaEsistenzaValore(datiProgramma.get(indicePiatriVector))) {
      programma.setT2URLAPPROV(datiProgramma.get(indicePiatriVector).toString());
    }
  }
 
  /**
   * Valorizzazione dell'array di interventi per programmi secondo l'ex DLgs 50/2016.
   * 
   * @param programma
   * @param codicePianoTriennale
   * @param sqlManager
   * @param w9Manager
   * @param requestHttp
   */
  private static InterventoType[] valorizzaArrayInterventi(
      long codicePianoTriennale, SqlManager sqlManager, W9Manager w9Manager,
      HttpServletRequest requestHttp) throws SQLException, ParseException {

    String sqlRecuperaINTERVENTO =
      "select ACQALTINT, ACQVERDI, AL1TRI, AL2TRI, AL3TRI, AL9TRI, "       //  0,.. 5
          + " ANNINT,   ANNRIF,   AP1TRI, AP2TRI, AP3TRI, AP9TRI, "        //  6,.. 11 
          + " APCINT,   AVCPV,  AVIMPNET, AVIMPTOT, AVIVA, "               // 12,.. 16 
          + " AVOGGETT, BI1TRI, BI2TRI,  BI3TRI, BI9TRI, CODAUSA, "        // 17,.. 22  
          + " CODCPV,   CODINT, CODRUP,  COMINT, CONINT, CONTESS, "        // 23,.. 28
          + " CONTRI,   COPFIN, CUICOLL, CUIINT, CUPPRG, DELEGA, "         // 29,.. 34
          + " DESINT,   DI1INT, DI2INT,  DI3INT, DI9INT, DIRGEN, "         // 35,.. 40
          + " DITINT,   DURCONT, DV1TRI, DV2TRI, DV3TRI, DV9TRI, "         // 41,.. 46
          + " FIINTR,   FLAG_CUP, ICPINT, IM1TRI, IM2TRI, IM3TRI, "        // 47,.. 52
          + " IMPALT,   IMPRFS, INTERV, INTNOTE,  IV1TRI, IV2TRI, IV9TRI, "   // 53,.. 59
          + " LAVCOMPL, LOTFUNZ, MATRIC, MESEIN, MRCPV, MRIMPNET, "        // 60,.. 65
          + " MRIMPTOT, MRIVA, MROGGETT, MU1TRI, MU2TRI, MU3TRI, MU9TRI, " // 66,.. 72
          + " NORRIF, NUTS, PR1TRI, PR2TRI, PR3TRI, PR9TRI, PRGINT, "      // 73,.. 79
          + " PRIMANN, PROAFF, QUANTIT, REFERE, RESPUF, RG1TRI, "          // 80,.. 85 
          + " SCAMUT, SEZINT, SOGAGG, SPESESOST, STAPRO, STRUOP, "         // 86,.. 91
          + " TIPCAPPRIV, TIPINT, TIPOIN, TOTINT, UNIMIS, URCINT, "        // 92,.. 97
          + " VALUTAZIONE, VARIATO, CONINT "                               // 98, 100
          + " from INTTRI where CONTRI=? ";

    Long codicePianoTri = new Long(codicePianoTriennale);

    List< ? > listaInterventi = sqlManager.getListVector(sqlRecuperaINTERVENTO,
        new Object[] { codicePianoTri });
    InterventoType[] arrayInterventi = null;
    
    if (listaInterventi != null && listaInterventi.size() > 0) {
      arrayInterventi = new InterventoType[listaInterventi.size()];
      
      for (int i = 0; i < listaInterventi.size(); i++) {
        List< ? > interventoDB = (List< ? >) listaInterventi.get(i);
        
        InterventoType intervento = InterventoType.Factory.newInstance();

        int indiceInterventoVector = 0; // indiceInterventoVector=0
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2ACQALT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=1
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2ACQVERINT(PT018Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=2
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IAL1TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=3
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IAL2TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=4
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IAL3TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=5
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AL9INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=6
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2ANNINT(convertiFlag(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=7
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2ANNRIF(Integer.parseInt(interventoDB.get(indiceInterventoVector).toString()));
        }        
        indiceInterventoVector++; // indiceInterventoVector=8
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AP1INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=9
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AP2INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=10
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AP3INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=11
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AP9INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        
        indiceInterventoVector++; // indiceInterventoVector=12
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2APCINT(convertiFlag(interventoDB.get(indiceInterventoVector).toString()));
        }
        
        indiceInterventoVector++; // indiceInterventoVector=13
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AVCPVINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=14
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AVIMPNETINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=15
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AVIMPTOTINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=16
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AVIVAINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        
        indiceInterventoVector++; // indiceInterventoVector=17
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2AVOGGETINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=18
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IBI1TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=19
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IBI2TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=20
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IBI3TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=21
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2BI9INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=22
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2CAUSAINT(interventoDB.get(indiceInterventoVector).toString());
        }

        indiceInterventoVector++; // indiceInterventoVector=23
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2CODCPV(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=24
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2CODINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=25
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          Arch2Type responsabile = Arch2Type.Factory.newInstance();
          valorizzaArch2Type(responsabile, interventoDB.get(indiceInterventoVector).toString(), sqlManager);
          intervento.setResponsabile(responsabile);
        }
        indiceInterventoVector++; // indiceInterventoVector=26
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2COMINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=27
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2CONINT(Integer.parseInt(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=28
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2CONTE(convertiFlag(interventoDB.get(indiceInterventoVector).toString()));
        }

        indiceInterventoVector++; // indiceInterventoVector=29
        //if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
        //  intervento.setT2CONTRI1(interventoDB.get(indiceInterventoVector).toString());
        //}
        indiceInterventoVector++; // indiceInterventoVector=30
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2COPFININT(convertiFlag(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=31
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2CUICO(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=32
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2CUIINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=33
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2CUPPRG(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=34
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DELEGAINT(convertiFlag(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=35
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DESINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=36
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DI1INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=37
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DI2INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=38
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DI3INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=39
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DI9INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=40
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DIRGENINT(interventoDB.get(indiceInterventoVector).toString());
        }

        indiceInterventoVector++; // indiceInterventoVector=41
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DITINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=42
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DURCO(Integer.parseInt(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=43
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IDV1TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=44
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IDV2TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=45
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IDV3TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=46
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2DV9INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }

        indiceInterventoVector++; // indiceInterventoVector=47
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2FIINTR(PTx03Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=48
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2FLAGCUP(convertiFlag(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=49
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2ICPINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=50
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IIM1TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=51
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IIM2TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=52
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IIM3TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }

        indiceInterventoVector++; // indiceInterventoVector=53
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IMPALT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=54
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setSUIAL1TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=55
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2INTERV(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=56
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2INTNOTE(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=57
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IV1TRIINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=58
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IV2TRIINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=59
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IV9TRIINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }

        indiceInterventoVector++; // indiceInterventoVector=60
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2LAVCO(convertiFlag(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=61
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2LOTFU(convertiFlag(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=62
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2MATRICINT(PT018Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=63
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setFSMESE(W3995Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=64
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2MRCPVINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=65
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2MRIMPNETINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }

        indiceInterventoVector++; // indiceInterventoVector=66
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2MRIMPTOTINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=67
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2MRIVAINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=68
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2MROGGETINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=69
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IMU1TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=70
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IMU2TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=71
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IMU3TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=72
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2MU9INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }

        indiceInterventoVector++; // indiceInterventoVector=73
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setFSNORMA(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=74
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2NUTS(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=75
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IPR1TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=76
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IPR2TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=77
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2IPR3TRI(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=78
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2PR9INT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=79
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2PRGINT(PT008Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }

        indiceInterventoVector++; // indiceInterventoVector=80
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2PRIMA(Integer.parseInt(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=81
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2PROAFFINT(PT020Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=82
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2QUANTITINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=83
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2REFEREINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=84
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2RESPUFINT(interventoDB.get(indiceInterventoVector).toString());
        }
        indiceInterventoVector++; // indiceInterventoVector=85
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setFSIRISREG(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        
        indiceInterventoVector++; // indiceInterventoVector=86
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2SCAMU(dateString2Calendar(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=87
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2SEZINT(PTx01Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        
        indiceInterventoVector++; // indiceInterventoVector=88
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2SOGGAGGINT(interventoDB.get(indiceInterventoVector).toString());
        }

        indiceInterventoVector++; // indiceInterventoVector=89
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2SPESESOST(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=90
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2STAPRO(PT012Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=91
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2STRUOPINT(interventoDB.get(indiceInterventoVector).toString());
        }
        
        indiceInterventoVector++; // indiceInterventoVector=92
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2TCPINT(PTx08Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=93
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2TIPINT(W9002Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=94
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setFSTIPO(W3Z05Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=95
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2TOTINT(Double.parseDouble(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=96
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2UNIMISINT(PT019Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=97
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2URCINT(convertiFlag(interventoDB.get(indiceInterventoVector).toString()));
        }

        indiceInterventoVector++; // indiceInterventoVector=98
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2VALUTAINT(PT021Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }
        indiceInterventoVector++; // indiceInterventoVector=99
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          intervento.setT2VARIA(PT010Type.Enum.forString(interventoDB.get(indiceInterventoVector).toString()));
        }

        indiceInterventoVector++; // indiceInterventoVector=100
        if (verificaEsistenzaValore(interventoDB.get(indiceInterventoVector))) {
          long conint = Long.parseLong(interventoDB.get(indiceInterventoVector).toString());

          ImmobileType[] arrayImmobili = valorizzaImmobiliIntervento(codicePianoTri, conint, sqlManager);
          if (arrayImmobili != null && arrayImmobili.length > 0) {
            intervento.setImmobiliArray(arrayImmobili);
          }

          RisorsaCapitoloBilancioType[] arrayRisorseCapitoloBilancio = valorizzaRisorseCapitoloBilancio(
              codicePianoTri, conint, sqlManager);
          if (arrayRisorseCapitoloBilancio != null && arrayRisorseCapitoloBilancio.length > 0) {
            intervento.setRisorseCapitoloBilancioArray(arrayRisorseCapitoloBilancio);
          }
        }
        arrayInterventi[i] = intervento;
      }
    }
    return arrayInterventi;
  }
  
  
  /**
   * Valorizzazione dell'array di interventi non riproposti per programmi secondo l'ex DLgs 50/2016.
   * 
   * @param programma
   * @param codicePianoTriennale
   * @param sqlManager
   * @param w9Manager
   * @param requestHttp
   */
  private static InterventoNonRipropostoType[] valorizzaArrayInterventiNonRiproposti(long codicePianoTriennale,
      SqlManager sqlManager, W9Manager w9Manager) throws SQLException, ParseException {

    String sqlRecuperaINRTRI = 
      "select CUIINT, CUPPRG, DESINT, TOTINT, MOTIVO, PRGINT "
      + " from INRTRI where CONTRI=? order by CONINT asc";
    
    List< ? > listaInterventiNonRipropostiDB = sqlManager.getListVector(sqlRecuperaINRTRI,
        new Object[] { codicePianoTriennale });
    
    InterventoNonRipropostoType[] arrayInterventiNonRiproposti = null;
    
    if (listaInterventiNonRipropostiDB != null && listaInterventiNonRipropostiDB.size() > 0) {
      arrayInterventiNonRiproposti = new InterventoNonRipropostoType[listaInterventiNonRipropostiDB.size()];
      
      for (int i = 0; i < listaInterventiNonRipropostiDB.size(); i++) {
        List< ? > interventoNonRipropostoDB = (List< ? >) listaInterventiNonRipropostiDB.get(i);
        
        InterventoNonRipropostoType interventoNonRiproposto = InterventoNonRipropostoType.Factory.newInstance();

        int indiceVector = 0; // indiceVector=0
        if (verificaEsistenzaValore(interventoNonRipropostoDB.get(indiceVector))) {
          interventoNonRiproposto.setT2CUIINR(interventoNonRipropostoDB.get(indiceVector).toString());
        }
        indiceVector++; // indiceVector=1
        if (verificaEsistenzaValore(interventoNonRipropostoDB.get(indiceVector))) {
          interventoNonRiproposto.setT2CUPINR(interventoNonRipropostoDB.get(indiceVector).toString());
        }
        indiceVector++; // indiceVector=2
        if (verificaEsistenzaValore(interventoNonRipropostoDB.get(indiceVector))) {
          interventoNonRiproposto.setT2DESINR(interventoNonRipropostoDB.get(indiceVector).toString());
        }
        indiceVector++; // indiceVector=3
        if (verificaEsistenzaValore(interventoNonRipropostoDB.get(indiceVector))) {
          interventoNonRiproposto.setT2DITINR(Double.parseDouble(interventoNonRipropostoDB.get(indiceVector).toString()));
        }
        indiceVector++; // indiceVector=4
        arrayInterventiNonRiproposti[i] = interventoNonRiproposto;
        if (verificaEsistenzaValore(interventoNonRipropostoDB.get(indiceVector))) {
          interventoNonRiproposto.setT2INRNMOT(interventoNonRipropostoDB.get(indiceVector).toString());
        }
        indiceVector++; // indiceVector=5
        if (verificaEsistenzaValore(interventoNonRipropostoDB.get(indiceVector))) {
          interventoNonRiproposto.setT2PRGINR(A2102Type.Enum.forString(interventoNonRipropostoDB.get(indiceVector).toString()));
        }
        arrayInterventiNonRiproposti[i] = interventoNonRiproposto;
      }
    }
    return arrayInterventiNonRiproposti;
  }

  /**
   * Ritorna l'array di immobili associati ad un'opera incompiuta.
   * 
   * @param contri
   * @param conint
   * @param sqlManager
   * @return
   * @throws SQLException
   */
  private static ImmobileType[] valorizzaImmobiliOperaIncompiuta(final Long contri, final Long numoi, 
      final SqlManager sqlManager) throws SQLException {
    
    String sqlRecuperaIMMTRAI = 
      "select NUMIMM, CUIIMM, DESIMM, COMIST, NUTS, "
          + " TITCOR, IMMDISP, PROGDISM, TIPDISP, VALIMM, "
          + " VA1IMM, VA2IMM, VA3IMM from IMMTRAI "
    + " where CONTRI=? AND CONINT=0 and NUMOI=? ";
  
    List< ? > listaImmtrai;
    List< ? > immobileDB = new ArrayList<Object>();
  
    ImmobileType[] arrayImmobili = null;
    
    if (numoi != null && contri != null) {
      listaImmtrai = sqlManager.getListVector(sqlRecuperaIMMTRAI,
          new Object[] { contri, numoi });
      if (listaImmtrai.size() > 0) {
        arrayImmobili = new ImmobileType[listaImmtrai.size()];
  
        for (int i = 0; i < listaImmtrai.size(); i++) {
          immobileDB = (List< ? >) listaImmtrai.get(i);
  
          ImmobileType immobile = ImmobileType.Factory.newInstance();
          int indiceImmTraiVector = 0; // indiceImmTraiVector=0
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2INUMIMM(Integer.parseInt(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=1
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2CUIIMM(immobileDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=2
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2IDESIMM(immobileDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=3
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2COMIST(immobileDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=4
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2NUTS01(immobileDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=5
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2TITCOR(PT013Type.Enum.forString(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=6
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2IMMDIS(PT014Type.Enum.forString(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=7
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2PROGDI(PT015Type.Enum.forString(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=8
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2TIPDIS(PT016Type.Enum.forString(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=9
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2IVALIMM(Double.parseDouble(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=10
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2VA1INT(Double.parseDouble(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=11
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2VA2INT(Double.parseDouble(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=12
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2VA3INT(Double.parseDouble(immobileDB.get(indiceImmTraiVector).toString()));
          }
          arrayImmobili[i] = immobile;
        }
      }
    }
    return arrayImmobili;
  }
  
  
  private static OperaIncompiutaType[] valorizzaOpereIncompiute (final Long contri, 
      final SqlManager sqlManager) throws SQLException {
    
    String sqlRecuperaOITRI =
      "select AMBITOINT, ANNOULTQE, AVANZAMENTO, CAUSA, CESSIONE, "                 // 0,..,4
          + " COP_ALTRAPUBBLICA, COP_COMUNITARIA, COP_COMUNALE, COP_PROVINCIALE, "  // 5,..,8
          + " COP_PRIVATA, COP_REGIONALE, COP_STATALE, CUPMASTER, CUP, "            // 9,..,13
          + " DEMOLIZIONE, DESCRIZIONE, DESTINAZIONEUSO, DETERMINAZIONI, DIM_QTA, " //14,..,18
          + " DIM_UM, FINANZIAMENTO, FINANZA_PROGETTO, FRUIBILE, IMPORTOINT, "      //19,..,23
          + " IMPORTOLAV, IMPORTOSAL, INFRASTRUTTURA, ISTAT, NUMOI, NUTS, "         //24,..,29
          + " ONERIULTIM, REQ_CAP, REQ_PRGE, SPONSORIZZAZIONE, STATOREAL, INTERV, " //30,..,35
          + " SEZINT, UTILIZZORID, VENDITA "  // 36,37,38
      + "from OITRI where CONTRI=? order by NUMOI asc";
    
    List< ? > listaOitri;
    List< ? > operaIncompiutaDB = new ArrayList<Object>();

    OperaIncompiutaType[] arrayOpereIncompiute = null;
    
    if (contri != null) {
      listaOitri = sqlManager.getListVector(sqlRecuperaOITRI, new Object[] { contri });
      if (listaOitri != null && listaOitri.size() > 0) {
        arrayOpereIncompiute = new OperaIncompiutaType[listaOitri.size()];

        for (int i = 0; i < listaOitri.size(); i++) {
          operaIncompiutaDB = (List< ? >) listaOitri.get(i);

          OperaIncompiutaType operaIncompiuta = OperaIncompiutaType.Factory.newInstance();
          
          int indiceImmTraiVector = 0; // indiceImmTraiVector=0
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setAMBIINTOITR(PTx04Type.Enum.forString(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=1
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setANNULTQEOIT(Integer.parseInt(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=2
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setAVANZOITRIP(Float.parseFloat(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=3
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCAUSAOITRIP(PTx05Type.Enum.forString(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=4
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCESSOITRIPI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=5
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCPAPOITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=6
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCPCMOITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=7
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCPCOOITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=8
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCPPROITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=9
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCPPVOITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=10
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCPREOITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=11
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCPSTOITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=12
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCUPMASTEROI(operaIncompiutaDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=13
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setCUPOITRIPIA(operaIncompiutaDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=14
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setDEMOLOITRIP(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=15
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setDESCROITRIP(operaIncompiutaDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=16
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setDESTUSOOITR(PTx07Type.Enum.forString(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=17
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setDETERMOITRI(PTx02Type.Enum.forString(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=18
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setDIMQTOITRP(Double.parseDouble(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=19
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setDIMUMOITRIP(operaIncompiutaDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=20
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setFINANOITRP(Double.parseDouble(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=21
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setFINPRGOITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=22
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setFRUIBOITRIP(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=23
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setIMPINTOITRP(Double.parseDouble(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=24
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setIMPLAVOITRP(Double.parseDouble(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=25
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setIMPSALOITRP(Double.parseDouble(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=26
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setINFRASTROIT(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=27
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setISTATOIT(operaIncompiutaDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=28
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            int numoi = Integer.parseInt(operaIncompiutaDB.get(indiceImmTraiVector).toString());
            operaIncompiuta.setNUMOIOITRIP(numoi);
            // Valorizzazione degli immobili associati all'opera incompiuta
            operaIncompiuta.setImmobiliArray(valorizzaImmobiliOperaIncompiuta(contri, new Long(numoi), sqlManager));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=29
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setNUTCODOI(operaIncompiutaDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=30
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setONERULTOITR(Double.parseDouble(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=31
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setREQCAPOITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=32
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setREQPRGOITRI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=33
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setSPONSOITRIP(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=34
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setSTATREALOIT(PTx06Type.Enum.forString(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=35
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setT2INTEOI(operaIncompiutaDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=36
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setT2SEZOIT(PTx01Type.Enum.forString(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=37
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setUTILRIDOITR(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=38
          if (verificaEsistenzaValore(operaIncompiutaDB.get(indiceImmTraiVector))) {
            operaIncompiuta.setVENDOITRIPI(convertiFlag(operaIncompiutaDB.get(indiceImmTraiVector).toString()));
          }
          arrayOpereIncompiute[i] = operaIncompiuta;
        }
      }
    }
    return arrayOpereIncompiute;
    
  }

  
  /**
   * Ritorna l'array di immobili associati all'intervento.
   * 
   * @param contri
   * @param conint
   * @param sqlManager
   * @return
   * @throws SQLException
   */
  private static ImmobileType[] valorizzaImmobiliIntervento(final Long contri, final Long conint, 
      final SqlManager sqlManager) throws SQLException {
    
    String sqlRecuperaIMMTRAI = 
        "select NUMIMM, CUIIMM,  DESIMM,   COMIST,  NUTS, "
            + " TITCOR, IMMDISP, PROGDISM, TIPDISP, VALIMM,"
            + " VA1IMM, VA2IMM,  VA3IMM "
      + " from IMMTRAI where CONTRI=? AND CONINT=? and NUMOI is null";
    
    List< ? > listaImmtrai;
    List< ? > immobileDB = new ArrayList<Object>();

    ImmobileType[] arrayImmobili = null;
    
    if (conint != null && contri != null) {
      listaImmtrai = sqlManager.getListVector(sqlRecuperaIMMTRAI,
          new Object[] { contri, conint });
      if (listaImmtrai.size() > 0) {
        arrayImmobili = new ImmobileType[listaImmtrai.size()];

        for (int i = 0; i < listaImmtrai.size(); i++) {
          immobileDB = (List< ? >) listaImmtrai.get(i);

          ImmobileType immobile = ImmobileType.Factory.newInstance();
          int indiceImmTraiVector = 0; // indiceImmTraiVector=0
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2INUMIMM(Integer.parseInt(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=1
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2CUIIMM(immobileDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=2
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2IDESIMM(immobileDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=3
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2COMIST(immobileDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=4
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2NUTS01(immobileDB.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=5
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2TITCOR(PT013Type.Enum.forString(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=6
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2IMMDIS(PT014Type.Enum.forString(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=7
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2PROGDI(PT015Type.Enum.forString(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=8
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2TIPDIS(PT016Type.Enum.forString(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=9
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2IVALIMM(Double.parseDouble(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=10
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2VA1INT(Double.parseDouble(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=11
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2VA2INT(Double.parseDouble(immobileDB.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=12
          if (verificaEsistenzaValore(immobileDB.get(indiceImmTraiVector))) {
            immobile.setT2VA3INT(Double.parseDouble(immobileDB.get(indiceImmTraiVector).toString()));
          }
          arrayImmobili[i] = immobile;
        }
      }
    }
    return arrayImmobili;
  }
  
  
  private static RisorsaCapitoloBilancioType[] valorizzaRisorseCapitoloBilancio (final Long contri, final Long conint, 
      final SqlManager sqlManager) throws SQLException {
    
    String sqlRecuperaRisorseCapitoloBilancio = 
      "select NCAPBIL, IMPSPE, RG1CB, IMPRFSCB, IMPALTCB, VERIFBIL, "    // 0,..,5
          + " DV1CB, DV2CB, DV3CB, DV9CB, MU1CB, MU2CB, MU3CB, MU9CB, "  // 6,..,13
          + " BI1CB, BI2CB, BI3CB, BI9CB, AP1CB, AP2CB, AP3CB, AP9CB, "  //14,..,21
          + " AL1CB, AL2CB, AL3CB, AL9CB, TO1CB, TO2CB, TO3CB, TO9CB, "  //22,..,29
          + " IV1CB, IV2CB, IV9CB, CBNOTE, NUMCB "                       //30,..,34
     + " from RIS_CAPITOLO where CONTRI=? and CONINT=? order by NUMCB asc";

    RisorsaCapitoloBilancioType[] arrayRisorseCapitoloBilancio = null;
    
    if (contri != null && conint != null) {
      List<?> listaRisorseCapitoloDB = sqlManager.getListVector(
          sqlRecuperaRisorseCapitoloBilancio, new Object[] { contri, conint });
      if (listaRisorseCapitoloDB != null && listaRisorseCapitoloDB.size() > 0) {
        
        arrayRisorseCapitoloBilancio = new RisorsaCapitoloBilancioType[listaRisorseCapitoloDB.size()]; 
        
        for (int i=0; i < listaRisorseCapitoloDB.size(); i++) {
          RisorsaCapitoloBilancioType risorsaCapitoloBilancio = RisorsaCapitoloBilancioType.Factory.newInstance();
          
          List< ? > risorsaCapitoloDB = new ArrayList<Object>();
          risorsaCapitoloDB = (List< ? >) listaRisorseCapitoloDB.get(i);

          int indiceRisorseCapitoloDbVector = 0; // indiceImmTraiVector=0
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2NCAPBILCB(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString());
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=1
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IMPSPECB(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString());
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=2
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setRG1CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=3
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IMPRFSCB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=4
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IMPALTCB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=5
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2VERBILCB(convertiFlag(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=6
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IDV1CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=7
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IDV2CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=8
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IDV3CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=9
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2DV9CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=10
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IMU1CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=11
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IMU2CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=12
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IMU3CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=13
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2MU9CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=14
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IBI1CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=15
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IBI2CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=16
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IBI3CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=17
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2BI9CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=18
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2AP1CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=19
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2AP2CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=20
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2AP3CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=21
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2AP9CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=22
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IAL1CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=23
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IAL2CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=24
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IAL3CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=25
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2AL9CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=26
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2ITO1CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=27
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2ITO2CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=28
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2ITO3CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=29
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2TO9CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=30
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IV1CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=31
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IV2CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=32
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2IV9CB(Double.parseDouble(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=33
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2NOTECB(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString());
          }
          indiceRisorseCapitoloDbVector++; // indiceImmTraiVector=34
          if (verificaEsistenzaValore(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector))) {
            risorsaCapitoloBilancio.setT2NUMCB(Integer.parseInt(risorsaCapitoloDB.get(indiceRisorseCapitoloDbVector).toString()));
          }
          arrayRisorseCapitoloBilancio[i] = risorsaCapitoloBilancio;
        }
      }
    }
    return arrayRisorseCapitoloBilancio;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument
      getRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException {
    
    RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument avanzamentoContrattoSemplificato =
      RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoDocument.Factory.newInstance();
    avanzamentoContrattoSemplificato.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificatoType richiestaAvanzamentoContrattoAppaltoSemplificato =
      avanzamentoContrattoSemplificato.addNewRichiestaRichiestaRispostaSincronaAvanzamentoContrattoAppaltoSemplificato();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaAvanzamentoContrattoAppaltoSemplificato.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
 
    CreazioneXmlExportManager.valorizzaTab33Type(richiestaAvanzamentoContrattoAppaltoSemplificato.addNewTab33(),
        codiceGara, codiceLotto, progressivoFase, sqlManager);
    return avanzamentoContrattoSemplificato;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument
      getRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException {
    
    RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument elencoImpreseInvitatePartecipanti =
      RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument.Factory.newInstance();
    elencoImpreseInvitatePartecipanti.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiType richiestaElencoImpreseInvitatePartecipanti =
      elencoImpreseInvitatePartecipanti.addNewRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    
    FaseEstesaType faseEstesa = richiestaElencoImpreseInvitatePartecipanti.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    CreazioneXmlExportManager.valorizzaElencoImpreseInvitate(richiestaElencoImpreseInvitatePartecipanti,
        codiceGara, codiceLotto, sqlManager);
    return elencoImpreseInvitatePartecipanti;
  }


  /**
   * @param impl
   * @param codiceFase
   * @param codiceGara
   * @param codiceCompositore
   * @param w9man
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   * @throws IOException
   */
  private static RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument
      getRichiestaRispostaSincronaAnagraficaLottoGaraDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager, W9Manager w9Manager)
              throws SQLException, GestoreException, ParseException, IOException {

    RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument anagraficaGaraLotto =
      RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraDocument.Factory.newInstance();
    anagraficaGaraLotto.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraType richiestaAnagrafica =
      anagraficaGaraLotto.addNewRichiestaRichiestaRispostaSincronaAnagraficaLottoGara();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    
    FaseType fase = richiestaAnagrafica.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseType(fase, garaLottoBean.getDatiComuniXml());

    Tab4Type tab4 = richiestaAnagrafica.addNewTab4();
    ListaTab5Type listaTab5 = richiestaAnagrafica.addNewListaTab5();
    CreazioneXmlExportManager.valorizzaTab4Type(tab4, codiceGara, sqlManager);
    
    // Esportazione in W9DOCGARA (documento PDF allegati)
    // Se non esiste alcun record in W9PUBBLICAZIONI relativo alla gara con CODGARA=codiceGara, allora
    // si esportano i documenti del bando di gara (gestione prima dell'introduzione delle pubblicazioni)
    Long numeroPubblicazioniEsistenti = (Long) sqlManager.getObject(
        "select count(*) from W9PUBBLICAZIONI where CODGARA=?", new Object[] { codiceGara } );
    if (numeroPubblicazioniEsistenti.longValue() == 0) {
      CreazioneXmlExportManager.valorizzaListaTab41Type(richiestaAnagrafica, codiceGara, sqlManager, w9Manager);
    }
    CreazioneXmlExportManager.valorizzaListaTab5Type(listaTab5, codiceGara, sqlManager,
        garaLottoBean.isExportIncarichiProfessionali());
    CreazioneXmlExportManager.valorizzaTab184Type(richiestaAnagrafica, codiceGara, sqlManager);
    
    Tab5Type[] tab5  = listaTab5.getTab5TypeArray();

    for (int yi = 0; yi < tab5.length; yi++) {
      if (tab5[yi].isSetListaTab51() && tab5[yi].getListaTab51().sizeOfTab51Array() == 0) {
        tab5[yi].unsetListaTab51();
      }
      if (tab5[yi].isSetListaTab52() && tab5[yi].getListaTab52().sizeOfTab52Array() == 0) {
        tab5[yi].unsetListaTab52();
      }
    }
    return anagraficaGaraLotto;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaAperturaCantiereDocument
      getRichiestaRispostaSincronaAperturaCantiereDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException {
    
    RichiestaRichiestaRispostaSincronaAperturaCantiereDocument aperturaCantiere =
        RichiestaRichiestaRispostaSincronaAperturaCantiereDocument.Factory.newInstance();
    aperturaCantiere.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaAperturaCantiereType aperturaCantiereRichiesta =
        aperturaCantiere.addNewRichiestaRichiestaRispostaSincronaAperturaCantiere();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = aperturaCantiereRichiesta.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    Tab17Type tab17 = aperturaCantiereRichiesta.addNewTab17();
    CreazioneXmlExportManager.valorizzaTab17Type(tab17, codiceGara, codiceLotto, progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab171Type(aperturaCantiereRichiesta, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab172Type(aperturaCantiereRichiesta, codiceGara, codiceLotto, 
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab173Type(aperturaCantiereRichiesta, codiceGara, codiceLotto, 
        progressivoFase, sqlManager);
    return aperturaCantiere;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaEsitoNegCheckIdoneitaDocument
      getRichiestaRispostaSincronaEsitoNegCheckIdoneitaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException {

    RichiestaRichiestaRispostaSincronaEsitoNegCheckIdoneitaDocument esitoNegIdoneita =
      RichiestaRichiestaRispostaSincronaEsitoNegCheckIdoneitaDocument.Factory.newInstance();
    esitoNegIdoneita.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaEsitoNegCheckIdoneitaType richiestaEsitoIdon =
      esitoNegIdoneita.addNewRichiestaRichiestaRispostaSincronaEsitoNegCheckIdoneita();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaEsitoIdon.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa,garaLottoBean, sqlManager);

    Tab11Type tab11 = richiestaEsitoIdon.addNewTab11();
    CreazioneXmlExportManager.valorizzaTab11Type(tab11, codiceGara, codiceLotto, progressivoFase, sqlManager);
    
    return esitoNegIdoneita;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument
      getRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException {
    
    RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument esitoNegRegolarita =
      RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaDocument.Factory.newInstance();
    esitoNegRegolarita.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaEsitoNegCheckRegolaritaType richiestaRegol =
      esitoNegRegolarita.addNewRichiestaRichiestaRispostaSincronaEsitoNegCheckRegolarita();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaRegol.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    Tab12Type tab12 = richiestaRegol.addNewTab12();
    CreazioneXmlExportManager.valorizzaTab12Type(tab12, codiceGara, codiceLotto, progressivoFase, sqlManager);
    return esitoNegRegolarita;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument
      getRichiestaRispostaSincronaInadempienzeSicurezzaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException {
    
    RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument inadempienzeSicurezza =
      RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaDocument.Factory.newInstance();
    inadempienzeSicurezza.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaInadempienzeSicurezzaType richiestaInadempienze =
      inadempienzeSicurezza.addNewRichiestaRichiestaRispostaSincronaInadempienzeSicurezza();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaInadempienze.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);

    Tab15Type tab15 = richiestaInadempienze.addNewTab15();
    CreazioneXmlExportManager.valorizzaTab15Type(tab15, codiceGara, codiceLotto, progressivoFase, sqlManager);
    
    return inadempienzeSicurezza;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaInfortunioDocument
      getRichiestaRispostaSincronaInfortunioDocument(GaraLottoBean garaLottoBean,
          SqlManager sqlManager) throws SQLException, GestoreException, ParseException {
    
    RichiestaRichiestaRispostaSincronaInfortunioDocument infortunio =
      RichiestaRichiestaRispostaSincronaInfortunioDocument.Factory.newInstance();
    infortunio.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaInfortunioType richiestaInf =
      infortunio.addNewRichiestaRichiestaRispostaSincronaInfortunio();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaInf.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);

    Tab16Type tab16 = richiestaInf.addNewTab16();
    CreazioneXmlExportManager.valorizzaTab16Type(tab16, codiceGara, codiceLotto, progressivoFase, sqlManager);
    return infortunio;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document
      getRichiestaRispostaSincronaConclusioneContrattoSottoSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException {
    RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document conclusioneSemplificata =
      RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Document.Factory.newInstance();
    conclusioneSemplificata.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000Type richiestaConc10 =
      conclusioneSemplificata.addNewRichiestaRichiestaRispostaSincronaConclusioneContrattoSotto150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaConc10.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);

    Tab9Type tab9 = richiestaConc10.addNewTab9();
    CreazioneXmlExportManager.valorizzaTab9Type(tab9, codiceGara, codiceLotto, progressivoFase, sqlManager);
    
    return conclusioneSemplificata;
  }

  /**
   * @param impl
   * @param w9Manager
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   * @throws IOException
   */
  private static RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Document
      getRichiestaRispostaSincronaInizioContrattoSottoSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager, W9Manager w9Manager)
              throws SQLException, GestoreException, ParseException, IOException {
    
    RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Document inizioContrattoSemplificato =
      RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Document.Factory.newInstance();
    inizioContrattoSemplificato.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type richiestaInizioA07 =
      inizioContrattoSemplificato.addNewRichiestaRichiestaRispostaSincronaInizioContrattoSotto150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaInizioA07.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    CreazioneXmlExportManager.valorizzaTab8bisType(richiestaInizioA07, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab8bis1Type(richiestaInizioA07, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab8bis3Type(richiestaInizioA07, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab8bis31Type(richiestaInizioA07, codiceGara, codiceLotto,
        progressivoFase, sqlManager, w9Manager);
    
    if (richiestaInizioA07.isSetTab8Bis3() && StringUtils.isEmpty(
        richiestaInizioA07.getTab8Bis3().getW3MSDESCMIS())) {
      richiestaInizioA07.unsetTab8Bis3();
    }
    
    Tab8Bis2Type tab8Tab8bis2 = CreazioneXmlExportManager.getTab8Bis2Type(codiceGara, codiceLotto, 
        progressivoFase, sqlManager);
    if (tab8Tab8bis2 != null) {
      richiestaInizioA07.setTab8Bis2(tab8Tab8bis2);
    }
    
    return inizioContrattoSemplificato;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document
      getRichiestaRispostaSincronaAggiudicazioneAppaltoSottoSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, ParseException {
    
    RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document aggiudicazioneSemplificata =
      RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Document.Factory.newInstance();
    aggiudicazioneSemplificata.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000Type richiestaAgg4 =
      aggiudicazioneSemplificata.addNewRichiestaRichiestaRispostaSincronaAggiudicazioneAppaltoSotto150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaAgg4.addNewFase();
    Tab8Type tab8 = richiestaAgg4.addNewTab8();
 
    ListaTab81Type listaTab81 = richiestaAgg4.addNewListaTab81();
    ListaTab82Type listaTab82 = richiestaAgg4.addNewListaTab82();
 
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    CreazioneXmlExportManager.valorizzaTab8Type(tab8, codiceGara, codiceLotto, progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab81Type(listaTab81, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab82Type(listaTab82, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    
    return aggiudicazioneSemplificata;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument
      getRichiestaRispostaSincronaAdesioneAccordoQuadroDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, ParseException {
    RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument adesioneAccQuadro =
      RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroDocument.Factory.newInstance();
    adesioneAccQuadro.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaAdesioneAccordoQuadroType richiestaAdesione =
      adesioneAccQuadro.addNewRichiestaRichiestaRispostaSincronaAdesioneAccordoQuadro();
    
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaAdesione.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    Tab29Type tab29 = richiestaAdesione.addNewTab29();
    ListaTab291Type listaTab291 = richiestaAdesione.addNewListaTab291();
    ListaTab292Type listaTab292 = richiestaAdesione.addNewListaTab292();
    ListaTab293Type listaTab293 = richiestaAdesione.addNewListaTab293();
    CreazioneXmlExportManager.valorizzaTab29Type(tab29, codiceGara, codiceLotto, progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab291Type(listaTab291, codiceGara, codiceLotto,
        progressivoFase,sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab292Type(listaTab292, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab293Type(listaTab293, codiceGara, codiceLotto,
        progressivoFase, sqlManager);
    
    return adesioneAccQuadro;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument
      getRichiestaRispostaSincronaStipulaAccordoQuadroDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException {
    
    RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument stipulaAccQuadro =
      RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroDocument.Factory.newInstance();
    stipulaAccQuadro.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaStipulaAccordoQuadroType richiestaStipula =
      stipulaAccQuadro.addNewRichiestaRichiestaRispostaSincronaStipulaAccordoQuadro();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaStipula.addNewFase();
    Tab28Type tab28 = richiestaStipula.addNewTab28();
    Tab281Type tab281 = richiestaStipula.addNewTab281();
 
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    CreazioneXmlExportManager.valorizzaTab28(tab28, codiceGara, codiceLotto, progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaTab281(tab281, codiceGara, codiceLotto, progressivoFase, sqlManager);
    return stipulaAccQuadro;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document
      getRichiestaRispostaSincronaRecessoContrattoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, ParseException {
    
    RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document recesso =
      RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Document.Factory.newInstance();
    recesso.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000Type richiestaRecesso =
      recesso.addNewRichiestaRichiestaRispostaSincronaRecessoContrattoSopra150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaRecesso.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);

    Tab27Type tab27 = richiestaRecesso.addNewTab27();
    CreazioneXmlExportManager.valorizzaTab27Type(tab27, codiceGara, codiceLotto, progressivoFase, sqlManager);
    return recesso;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document
      getRichiestaRispostaSincronaSubappaltoContrattoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, ParseException {
    
    RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document subappalto =
      RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Document.Factory.newInstance();
    subappalto.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000Type richiestaSubappalto =
      subappalto.addNewRichiestaRichiestaRispostaSincronaSubappaltoContrattoSopra150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaSubappalto.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    Tab26Type tab26 = richiestaSubappalto.addNewTab26();
    CreazioneXmlExportManager.valorizzaTab26Type(tab26, codiceGara, codiceLotto, progressivoFase, sqlManager);
    return subappalto;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document
      getRichiestaRispostaSincronaAccordoBonarioContrattoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, ParseException {
    
    RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document accordoBonario =
      RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Document.Factory.newInstance();
    accordoBonario.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000Type richiestaAccordo =
      accordoBonario.addNewRichiestaRichiestaRispostaSincronaAccordoBonarioContrattoSopra150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaAccordo.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    Tab25Type tab25 = richiestaAccordo.addNewTab25();
    CreazioneXmlExportManager.valorizzaTab25Type(tab25, codiceGara, codiceLotto, progressivoFase, sqlManager);
    return accordoBonario;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document
      getRichiestaRispostaSincronaVarianteContrattoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, ParseException {
    
    RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document variante =
      RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Document.Factory.newInstance();
    variante.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000Type richiestaVariante =
      variante.addNewRichiestaRichiestaRispostaSincronaVarianteContrattoSopra150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaVariante.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);

    Tab24Type tab24 = richiestaVariante.addNewTab24();
    ListaTab241Type listaTab241 = richiestaVariante.addNewListaTab241();
    CreazioneXmlExportManager.valorizzaTab24Type(tab24, codiceGara, codiceLotto, 
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab241Type(listaTab241, codiceGara, 
        codiceLotto, progressivoFase, sqlManager);
 
    // L'oggetto ListaTab241 se presente deve contenere almeno un elemento.
    // Quindi se non contiene alcun elemento deve essere rimosso
    if (listaTab241.sizeOfTab241Array() == 0) {
      richiestaVariante.unsetListaTab241();
    }
    return variante;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document
      getRichiestaRispostaSincronaSospensioneContrattoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, ParseException {
    
    RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document sospensione =
      RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Document.Factory.newInstance();
    sospensione.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000Type richiestaSospensione =
      sospensione.addNewRichiestaRichiestaRispostaSincronaSospensioneContrattoSopra150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaSospensione.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    Tab23Type tab23 = richiestaSospensione.addNewTab23();
    CreazioneXmlExportManager.valorizzaTab23Type(tab23, codiceGara, codiceLotto, progressivoFase, sqlManager);
    return sospensione;
  }

  /**
   * @param impl
   * @param codiceFase
   * @param progressivoFase
   * @param codiceGara
   * @param codiceLotto
   * @param codiceCompositore
   * @return
   * @throws SQLException
   * @throws GestoreException
   * @throws ParseException
   */
  private static RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document
      getRichiestaRispostaSincronaCollaudoContrattoSopraSogliaDocument(
          GaraLottoBean garaLottoBean, SqlManager sqlManager)
              throws SQLException, GestoreException, ParseException {
    RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document collaudo =
      RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Document.Factory.newInstance();
    collaudo.documentProperties().setEncoding(ENCODING);
    RichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000Type richiestaCollaudo =
      collaudo.addNewRichiestaRichiestaRispostaSincronaCollaudoContrattoSopra150000();
 
    Long codiceGara = garaLottoBean.getCodiceGara();
    Long codiceLotto = garaLottoBean.getCodiceLotto();
    Long progressivoFase = garaLottoBean.getDatiComuniXml().getProgressivoFase();
    
    FaseEstesaType faseEstesa = richiestaCollaudo.addNewFase();
    CreazioneXmlExportManager.valorizzaFaseEstesaType(faseEstesa, garaLottoBean, sqlManager);
    
    Tab22Type tab22 = richiestaCollaudo.addNewTab22();
    ListaTab221Type listaTab221 = richiestaCollaudo.addNewListaTab221();
    CreazioneXmlExportManager.valorizzaTab22Type(tab22, codiceGara, codiceLotto, 
        progressivoFase, sqlManager);
    CreazioneXmlExportManager.valorizzaListaTab221Type(listaTab221, codiceGara, 
        codiceLotto, progressivoFase, sqlManager);
    return collaudo;
  }

  /**
   * Dati dei programmi triennali (tipologia lavori).
   * 
   * @param tab Tab1LType
   * @param params Parametri
   * @param sqlManager SqlManager
   * @param w9 W9Manager
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   * @throws IOException IOException
   */
  private static void valorizzaTab1LType(final Tab1L1Type tab, final long codicePianoTriennale,
      final SqlManager sqlManager, final W9Manager w9, HttpServletRequest request)
          throws SQLException, GestoreException, IOException {
    
    String sqlRecuperaPIATRI = "SELECT "
      + " CENINT, RESPRO, Al1TRI, AL2TRI, AL3TRI, ANNTRI, BI1TRI, BI2TRI, BI3TRI, "         // 0, 1, 2,...,8
      + " DV1TRI, DV2TRI, DV3TRI, ID,     IM1TRI, IM2TRI, IM3TRI, MU1TRI, MU2TRI, "         // 9,10,11,...,17
      + " MU3TRI, PR1TRI, PR2TRI, PR3TRI, TO1TRI, TO2TRI, TO3TRI, BRETRI, IMPACC, TIPROG, " //18,19,...,26,27
      + " NOTSCHE1, NOTSCHE2, NOTSCHE2B, NOTSCHE3 "   // 28,29,30,31
      + " FROM PIATRI WHERE CONTRI = ? ";

    tab.setT2NORMA(PT017Type.Enum.forString("1"));
    
    List< ? > listaProgrammi = sqlManager.getListVector(sqlRecuperaPIATRI, 
        new Object[] { new Long(codicePianoTriennale) });
    List< ? > programma = (List< ? >) listaProgrammi.get(0);

    int indicePiatriVector = 0; // indicePiatriVector=0
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      String codimp = programma.get(indicePiatriVector).toString();
      Arch1Type arch1 = tab.addNewArch1();
      valorizzaArch1Type(arch1, codimp, sqlManager);
    }
    indicePiatriVector++; // indicePiatriVector=1
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      String codein = programma.get(indicePiatriVector).toString();
      Arch2Type arch2 = tab.addNewArch2();
      valorizzaArch2Type(arch2, codein, sqlManager);
    }
    indicePiatriVector++; // indicePiatriVector=2
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2AL1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=3
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2AL2TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=4
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2AL3TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=5
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2ANNTRI(Integer.parseInt(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=6
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2BI1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=7
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2BI2TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=8
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2BI3TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=9
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2DV1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=10
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2DV2TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=11
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2DV3TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=12
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2IDCONTRI(programma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=13
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2IM1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=14
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2IM2TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=15
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2IM3TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=16
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2MU1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=17
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2MU2TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=18
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2MU3TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=19
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2PR1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=20
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2PR2TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=21
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2PR3TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=22
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2TO1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=23
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2TO2TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=24
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2TO3TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++; // indicePiatriVector=25
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2BRETRI(programma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=26
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2IMPACC(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    
    // creo il file allegato
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("contri", new Long(codicePianoTriennale));
    indicePiatriVector++; // indicePiatriVector=27

    hm.put("tiprog", new Long(programma.get(indicePiatriVector).toString()));
    hm.put("piatriID", programma.get(12).toString());
    creaPdf(hm, sqlManager, request);
    BlobFile fileAllegato = w9.getFileAllegato("PIATRI", hm);
    if (fileAllegato != null && fileAllegato.getStream() != null) {
      tab.setFile(fileAllegato.getStream());
    }
    
    indicePiatriVector++; // indicePiatriVector=28
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2NOTSCH1(programma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=29
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2NOTSCH2(programma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=30
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2NOTSCH2B(programma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++; // indicePiatriVector=31
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) {
      tab.setT2NOTSCH3(programma.get(indicePiatriVector).toString());
    }
  }

  
  /** Procedura che crea il file PDF dei programmi triennali e lo memorizza nel DB.
   * 
   * @param params params
   * @param request request
   * @throws GestoreException GestoreException
   */
  private static void creaPdf(HashMap<String, Object> params, final SqlManager sqlManager,
      HttpServletRequest request) throws GestoreException{
	  
    ContextAwareDataSourceProxy contextAwareDataSourceProxy =
	      (ContextAwareDataSourceProxy) UtilitySpring.getBean("dataSource", 
	    		  request.getSession().getServletContext(),
				  it.eldasoft.gene.commons.web.spring.ContextAwareDataSourceProxy.class);

	TransactionStatus status = null;
	boolean commitTransaction = false;
	Connection jrConnection = null;
	try {
	  String PROP_JRREPORT_SOURCE_DIR = "/WEB-INF/jrReport/Programmi/";
	  String jrReportSourceDir = null;
	  // Input stream del file sorgente
	  InputStream inputStreamJrReport = null;
      
   	Long tiprog = (Long)params.get("tiprog");
	  Long contri = (Long)params.get("contri");
	  String piatriID = params.get("piatriID").toString();
      
	  if (tiprog == 1 || tiprog == 3) {
	  	jrReportSourceDir = request.getSession().getServletContext().getRealPath(
    			PROP_JRREPORT_SOURCE_DIR).concat("/");
    	inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
    			PROP_JRREPORT_SOURCE_DIR.concat("report2.jasper"));
	    } else {
	      jrReportSourceDir = request.getSession().getServletContext().getRealPath(
				  PROP_JRREPORT_SOURCE_DIR ).concat("/");
	      inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
	    		  PROP_JRREPORT_SOURCE_DIR + "reportFornitureServizi.jasper");
	    }

    	// Parametri
    	HashMap<String, Object> jrParameters = new HashMap<String, Object>();
    	jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
    	jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);
    	jrParameters.put("COD_PIANOTRIEN", piatriID);
    	jrParameters.put("contri", new Long(contri));
      
    	// Connessione
    	jrConnection = contextAwareDataSourceProxy.getConnection();
      
    	// Stampa del formulario
    	JasperPrint jrPrint =  JasperFillManager.fillReport( inputStreamJrReport,
    			jrParameters, jrConnection);

    	// Output stream del risultato
    	ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();
    	JRExporter jrExporter = new JRPdfExporter();
    	jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
    	jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baosJrReport);
    	jrExporter.exportReport();

    	inputStreamJrReport.close();
    	baosJrReport.close();

    	// Update di PIATRI per l'inserimento del file composto
    	status = sqlManager.startTransaction();
    	LobHandler lobHandler = new DefaultLobHandler(); // reusable object
    	sqlManager.update("UPDATE PIATRI SET FILE_ALLEGATO = ? WHERE CONTRI = ? and ID = ?",
    			new Object[]{new SqlLobValue(baosJrReport.toByteArray(), lobHandler), new Long(contri), piatriID});
    	commitTransaction = true;
    } catch (Exception e) {
      throw new GestoreException(
          "Errore durante il recupero del file XML allegato.", "errors.generico", e);
    } finally {
      try {
        if (jrConnection != null) {
          jrConnection.close();
        }
        if (status != null) {
          if (commitTransaction) {
            sqlManager.commitTransaction(status);
          } else {
            sqlManager.rollbackTransaction(status);
          }
        }
   	  } catch (SQLException e) {
        logger.error("Errore durante la chiusura della transazione", e);
      }
    }
  }

  /**
   * Procedura che crea il file PDF dei programmi triennali secondo
   * l'ex DLgs. 50/2016 e lo memorizza nel DB
   * 
   * @param params params
   * @param request request
   * @throws GestoreException GestoreException
   */
  private static void creaPdfNuovaNormativa(HashMap<String, Object> params, final SqlManager sqlManager,
      HttpServletRequest request) throws GestoreException {

    ContextAwareDataSourceProxy contextAwareDataSourceProxy =
        (ContextAwareDataSourceProxy) UtilitySpring.getBean("dataSource",
            request.getSession().getServletContext(), it.eldasoft.gene.commons.web.spring.ContextAwareDataSourceProxy.class);
    TransactionStatus status = null;
    boolean commitTransaction = false;
    Connection jrConnection = null;
    try {
      String PROP_JRREPORT_SOURCE_DIR = "/WEB-INF/jrReport/NuovaNormativa/";
      String jrReportSourceDir = null;
      // Input stream del file sorgente
      InputStream inputStreamJrReport = null;

      Long tiprog = (Long)params.get("tiprog");
      Long contri = (Long)params.get("contri");
      String piatriID = params.get("piatriID").toString();

      if(tiprog == 1 || tiprog == 3){
        jrReportSourceDir = request.getSession().getServletContext().getRealPath(
            PROP_JRREPORT_SOURCE_DIR) + "/";
        inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
            PROP_JRREPORT_SOURCE_DIR + "allegatoI.jasper");
      } else {
        jrReportSourceDir = request.getSession().getServletContext().getRealPath(
            PROP_JRREPORT_SOURCE_DIR ) + "/";
        inputStreamJrReport = request.getSession().getServletContext().getResourceAsStream(
            PROP_JRREPORT_SOURCE_DIR + "allegatoII.jasper");
      }

      // Parametri
      HashMap<String, Object> jrParameters = new HashMap<String, Object>();
      jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
      jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);
      jrParameters.put("COD_PIANOTRIEN", piatriID);
      //jrParameters.put("contri", new Long(contri));

      // Connessione
      jrConnection = contextAwareDataSourceProxy.getConnection();

      // Stampa del formulario
      JasperPrint jrPrint =  JasperFillManager.fillReport(inputStreamJrReport,
          jrParameters, jrConnection);

      // Output stream del risultato
      ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();
      JRExporter jrExporter = new JRPdfExporter();
      jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
      jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baosJrReport);
      jrExporter.exportReport();

      inputStreamJrReport.close();
      baosJrReport.close();

      // Update di PIATRI per l'inserimento del file composto
      status = sqlManager.startTransaction();
      LobHandler lobHandler = new DefaultLobHandler(); // reusable object
      sqlManager.update("UPDATE PIATRI SET FILE_ALLEGATO = ? WHERE CONTRI = ? and ID = ?",
          new Object[]{new SqlLobValue(baosJrReport.toByteArray(), lobHandler), new Long(contri), piatriID});
      commitTransaction = true;

    } catch (Exception e) {
      throw new GestoreException(
          "Errore durante il recupero del file XML allegato.", "errors.generico", e);
    } finally {
      try {
        if (jrConnection != null) {
          jrConnection.close();
        }

        if (status != null) {
          if (commitTransaction) {
            sqlManager.commitTransaction(status);
          } else {
            sqlManager.rollbackTransaction(status);
          }
        }
      } catch (SQLException e) {
        logger.error("Errore durante la chiusura della transazione", e);
      }
    }
  }

  
  /**
   * Dati dei programmi triennali (tipologia forniture o servizi).
   * 
   * @param tab1 Tab1FSType
   * @param params Parametri
   * @param sqlManager SqlManager
   * @param w9 W9Manager
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   * @throws IOException IOException
   */
  private static void valorizzaTab1FSType(final Tab1FSType tab1, final long codicePianoTriennale,
      final SqlManager sqlManager, final W9Manager w9) throws SQLException,
      GestoreException, IOException {

    String sqlRecuperaPIATRIFS = "SELECT " +
    		" ID,     TIPROG, BRETRI, ANNTRI, RESPRO, AL1TRI, " + //0, 1, 2, 3, 4, 5
    		" BI1TRI, RG1TRI, IMPRFS, TO1TRI, CENINT, MU1TRI, PR1TRI, " + //6, 7, 8, 9, 10, 11, 12
    		" NOTSCHE1, NOTSCHE4 " +   // 13,14
    		" FROM PIATRI WHERE CONTRI = ? ";
    Long idPianoTriennale = new Long(codicePianoTriennale);

    List< ? > listaProgrammi = sqlManager.getListVector(sqlRecuperaPIATRIFS,
	  new Object[] { idPianoTriennale });	

    List< ? > programma = (List< ? >) listaProgrammi.get(0);

    String codimp = "";
    String codein = "";

    tab1.setT2NORMA(PT017Type.Enum.forString("1"));
    
    int indicePiatriVector = 0;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=0
      tab1.setT2IDCONTRI(programma.get(indicePiatriVector).toString());
    }
    indicePiatriVector = indicePiatriVector + 2;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=2
      tab1.setT2BRETRI(programma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=3
      tab1.setT2ANNTRI(Integer.parseInt(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=4
      codein = programma.get(indicePiatriVector).toString();
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=5
      tab1.setT2AL1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=6
      tab1.setT2BI1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=7
      tab1.setFSRG1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=8
      tab1.setFSSU2TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=9
      tab1.setT2TO1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=10
      codimp = programma.get(indicePiatriVector).toString();
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=11
      tab1.setT2MU1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=12
      tab1.setT2PR1TRI(Double.parseDouble(programma.get(indicePiatriVector).toString()));
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=13
      tab1.setT2NOTSCH1(programma.get(indicePiatriVector).toString());
    }
    indicePiatriVector++;
    if (verificaEsistenzaValore(programma.get(indicePiatriVector))) { // indicePiatriVector=14
      tab1.setT2NOTSCH4(programma.get(indicePiatriVector).toString());
    }
    
    Arch1Type arch1 = tab1.addNewArch1();
    Arch2Type arch2 = tab1.addNewArch2();
    if (!codimp.equals("")) {
      valorizzaArch1Type(arch1, codimp, sqlManager);
    }
    if (!codein.equals("")) {
      valorizzaArch2Type(arch2, codein, sqlManager);
    }

    // codice relativo al recupero del file allegato
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("contri", idPianoTriennale);
    BlobFile fileAllegato = w9.getFileAllegato("PIATRI", hm);
    tab1.setFile(fileAllegato.getStream());
  }
  
  /**
   * ARCH 1: Archivio delle stazioni appaltanti.
   * 
   * @param arch1 Arch1Type
   * @param codein Codice dell'Ufficio Intestatario
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaArch1Type(final Arch1Type arch1, final String codein,
      final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaUFFINT = "SELECT CAPEIN, CFEIN, CODCIT, EMAIIN, FAXEIN, IDAMMIN, "
      + "PROFCO, TIPOIN, NCIEIN, NOMEIN, TELEIN, VIAEIN FROM UFFINT WHERE CODEIN = ?";

    List< ? > listaUffint;
    List< ? > uffint = new ArrayList<Object>();

    if (codein != null) {
      listaUffint = sqlManager.getListVector(sqlRecuperaUFFINT,
          new Object[] { codein });
      if (listaUffint.size() > 0) {
        uffint = (List< ? >) listaUffint.get(0);

        int indiceUffintVector = 0; // indiceUffintVector=0
        if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
          arch1.setCAPEIN(uffint.get(indiceUffintVector).toString());
        }
        indiceUffintVector++; // indiceUffintVector=1
        if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
          arch1.setCFEIN(uffint.get(indiceUffintVector).toString());
        }
        indiceUffintVector++; // indiceUffintVector=2
        if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
          arch1.setCODCIT(uffint.get(indiceUffintVector).toString());
        }
        indiceUffintVector++; // indiceUffintVector=3
        if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
          arch1.setEMAIIN(uffint.get(indiceUffintVector).toString());
        }
        indiceUffintVector++; // indiceUffintVector=4
        if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
          arch1.setFAXEIN(uffint.get(indiceUffintVector).toString());
        }
        indiceUffintVector = indiceUffintVector + 2; // indiceUffintVector=6
        if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
          arch1.setGPROFCO(uffint.get(indiceUffintVector).toString());
        }
        indiceUffintVector++; // indiceUffintVector=7
        if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
          arch1.setTIPOIN(G044Type.Enum.forString(uffint.get(indiceUffintVector).toString()));
          indiceUffintVector++; // indiceUffintVector=8
          if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
            arch1.setNCIEIN(uffint.get(indiceUffintVector).toString());
          }
          indiceUffintVector++; // indiceUffintVector=9
          if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
            arch1.setNOMEIN(uffint.get(indiceUffintVector).toString());
          }
          indiceUffintVector++; // indiceUffintVector=10
          if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
            arch1.setTELEIN(uffint.get(indiceUffintVector).toString());
          }
          indiceUffintVector++; // indiceUffintVector=11
          if (verificaEsistenzaValore(uffint.get(indiceUffintVector))) {
            arch1.setVIAEIN(uffint.get(indiceUffintVector).toString());
          }
        }
      }
    }
  }

  /**
   * ARCH2 - Archivio dei tecnici.
   * 
   * @param arch2 Arch2Type
   * @param codtec Codice del tecnico
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaArch2Type(final Arch2Type arch2, final String codtec,
      final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaTECNI =
      "SELECT CAPTEC, CFTEC, COGTEI, FAXTEC, CITTEC, EMATEC, INDTEC, LOCTEC, " + //0,1,2,3,4,5,6,7
      "NCITEC, NOMETEI, TELTEC, PROTEC FROM TECNI WHERE CODTEC = ? ";     //8,9,10,11

    List< ? > listaTecni;
    List< ? > tecni = new ArrayList<Object>();

    if (codtec != null) {
      listaTecni = sqlManager.getListVector(sqlRecuperaTECNI,
          new Object[] { codtec });

      if (listaTecni.size() > 0) {
        tecni = (List< ? >) listaTecni.get(0);

        int indiceTecniVector = 0; // indiceTecniVector=0
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setCAPTEC1(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=1
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setCFTEC1(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=2
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setCOGTEI(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=3
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setFAXTEC1(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=4
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setGCITTECI(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=5
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setGEMATECI(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=6
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setINDTEC1(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=7
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setLOCTEC1(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=8
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setNCITEC1(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=9
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setNOMETEI(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=10
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setTELTEC1(tecni.get(indiceTecniVector).toString());
        }
        indiceTecniVector++; // indiceTecniVector=11
        if (verificaEsistenzaValore(tecni.get(indiceTecniVector))) {
          arch2.setPROTEC1(tecni.get(indiceTecniVector).toString());
        }
      }
    }
  }

  /**
   * ARCH3 - Archivio delle imprese.
   * 
   * @param arch3 Arch3Type
   * @param codimp Codice impresa
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaArch3Type(final Arch3Type arch3, final String codimp,
      final SqlManager sqlManager) throws SQLException, ParseException {
    String sqlRecuperaIMPR = "SELECT CODIMP, NOMEST, CFIMP, " // 0, 1, 2
      + "PIVIMP, INDIMP, NCIIMP, PROIMP, " // 3, 4, 5, 6
      + "CAPIMP, LOCIMP, CODCIT, NAZIMP, " // 7, 8, 9, 10
      + "TELIMP, FAXIMP, TELCEL, EMAIIP, " // 11, 12, 13, 14
      + "EMAI2IP,INDWEB, NCCIAA, NATGIUI," // 15, 16, 17, 18
      + "TIPIMP " // 19
      + "FROM IMPR WHERE CODIMP = ? ";

    List< ? > listaImpr;
    List< ? > impresa = new ArrayList<Object>();

    if (StringUtils.isNotEmpty(codimp)) {
      listaImpr = sqlManager.getListVector(sqlRecuperaIMPR,
          new Object[] { codimp });
      if (listaImpr.size() > 0) {
        impresa = (List< ? >) listaImpr.get(0);

        int indiceImprVector = 1; // indiceImprVector=1
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setNOMIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=2
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setCFIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=3
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setPIVIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=4
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setINDIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=5
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setNCIIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=6
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setPROIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=7
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setCAPIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=8
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setLOCIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=9
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setGCODCITI(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=10
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setGNAZIMP(Ag010Type.Enum.forString(impresa.get(indiceImprVector).toString()));
        }
        indiceImprVector++; // indiceImprVector=11
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setTELIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=12
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setFAXIMP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=13
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setIMTECEL(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=14
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setGEMAIIP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=15
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setGEMAI2IP(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=16
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setGINDWEBI(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=17
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setNCCIAA(impresa.get(indiceImprVector).toString());
        }
        indiceImprVector++; // indiceImprVector=18
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setGNATGIUI(G044Type.Enum.forString(impresa.get(indiceImprVector).toString()));
        }
        indiceImprVector++; // indiceImprVector=19
        if (verificaEsistenzaValore(impresa.get(indiceImprVector))) {
          arch3.setVINIMP(Ag008Type.Enum.forString(impresa.get(indiceImprVector).toString()));
        }
        indiceImprVector++;
        valorizzaArch4Type(arch3, codimp, sqlManager);
      }
    }
  }

  /**
   * ARCH4 - Archivio dei tecnici dell'impresa.
   * 
   * @param arch3 Arch3Type
   * @param codimp Codice Impresa
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaArch4Type(final Arch3Type arch3, final String codimp,
      final SqlManager sqlManager) throws SQLException, ParseException {

    String sqlRecuperaTEIM = "SELECT TEIM.CODTIM, TEIM.COGTIM, TEIM.NOMETIM, TEIM.CFTIM, IMPLEG.LEGINI, IMPLEG.LEGFIN "
      + " FROM TEIM, IMPLEG WHERE IMPLEG.CODIMP2 = ? AND IMPLEG.CODLEG = TEIM.CODTIM ";

    List< ? > listaTeim;
    List< ? > teim = new ArrayList<Object>();

    if (StringUtils.isNotBlank(codimp)) {
      listaTeim = sqlManager.getListVector(sqlRecuperaTEIM,
          new Object[] { codimp });
      if (listaTeim != null && listaTeim.size() > 0) {
        for (int i = 0; i < listaTeim.size(); i++) {
          teim = (List< ? >) listaTeim.get(i);

          Arch4Type arch4 = arch3.addNewListaArch4();

          int indiceTeimVector = 1; // indiceTeimVector=1
          if (verificaEsistenzaValore(teim.get(indiceTeimVector))) {
            arch4.setCOGTIM(teim.get(indiceTeimVector).toString());
          }
          indiceTeimVector++; // indiceTeimVector=2
          if (verificaEsistenzaValore(teim.get(indiceTeimVector))) {
            arch4.setNOMETIM(teim.get(indiceTeimVector).toString());
          }
          indiceTeimVector++; // indiceTeimVector=3
          if (verificaEsistenzaValore(teim.get(indiceTeimVector))) {
            arch4.setCFTIM(teim.get(indiceTeimVector).toString());
          }
          indiceTeimVector++; // indiceTeimVector=4
        }
      }
    }
  }

  /**
   * ARCH5 - Archivio dei centri di costo.
   * 
   * @param arch5 Arch5Type
   * @param idcc Idcc
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaArch5Type(final Arch5Type arch5, final Long idcc,
      final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaCENTRICOSTO = "SELECT CODCENTRO, DENOMCENTRO, TIPOLOGIA FROM CENTRICOSTO WHERE IDCENTRO = ?";

    List< ? > listaCentri;
    List< ? > centro = new ArrayList<Object>();

    if (idcc != null) {
      listaCentri = sqlManager.getListVector(sqlRecuperaCENTRICOSTO,
          new Object[] { idcc });

      centro = (List< ? >) listaCentri.get(0);
    }

    if (verificaEsistenzaValore(centro.get(0))) {
      arch5.setW9CCCODICE(centro.get(0).toString());
    }
    if (verificaEsistenzaValore(centro.get(1))) {
      arch5.setW9CCDENOM(centro.get(1).toString());
    }
    if (verificaEsistenzaValore(centro.get(2))) {
      arch5.setW9CCTIPO(W9019Type.Enum.forString(centro.get(2).toString()));
    }

  }

  /**
   * ARCH6 - Archivio degli uffici.
   * 
   * @param arch6 Arch6Type
   * @param idufficio Idufficio
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaArch6Type(final Arch6Type arch6, final Long idufficio,
      final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaUFFICI = "SELECT DENOM FROM UFFICI WHERE ID = ?";

    List< ? > listaUffici;
    List< ? > ufficio = new ArrayList<Object>();

    if (idufficio != null) {
    	listaUffici = sqlManager.getListVector(sqlRecuperaUFFICI,
          new Object[] { idufficio });

    	ufficio = (List< ? >) listaUffici.get(0);
    }

    if (verificaEsistenzaValore(ufficio.get(0))) {
      arch6.setW9UFFDENOM(ufficio.get(0).toString());
    }


  }
  
  /**
   * Dati degli immobili.
   * 
   * @param tab2 Tab2LType
   * @param contri Contri
   * @param conint Conint
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaListaTab3Type(final Tab2LType tab2, final Long contri,
      final Long conint, final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaIMMTRAI = "SELECT CONTRI, CONINT, NUMIMM, ANNIMM, DESIMM, PROIMM, VALIMM "
      + "FROM IMMTRAI WHERE CONTRI = ? AND CONINT = ?";
    Tab3Type tab3;

    List< ? > listaImmtrai;
    List< ? > immtrai = new ArrayList<Object>();

    if (conint != null && contri != null) {
      listaImmtrai = sqlManager.getListVector(sqlRecuperaIMMTRAI,
          new Object[] { contri, conint });
      if (listaImmtrai.size() > 0) {

        ListaTab3Type listaTab3 = tab2.addNewListaTab3();

        for (int i = 0; i < listaImmtrai.size(); i++) {
          immtrai = (List< ? >) listaImmtrai.get(i);

          tab3 = listaTab3.addNewTab3();
          int indiceImmTraiVector = 2; // indiceImmTraiVector=2
          if (verificaEsistenzaValore(immtrai.get(indiceImmTraiVector))) {
            tab3.setT2INUMIMM(Integer.parseInt(immtrai.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=3
          if (verificaEsistenzaValore(immtrai.get(indiceImmTraiVector))) {
            tab3.setT2IANNIMM(Integer.parseInt(immtrai.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=4
          if (verificaEsistenzaValore(immtrai.get(indiceImmTraiVector))) {
            tab3.setT2IDESIMM(immtrai.get(indiceImmTraiVector).toString());
          }
          indiceImmTraiVector++; // indiceImmTraiVector=5
          if (verificaEsistenzaValore(immtrai.get(indiceImmTraiVector))) {
            tab3.setT2IPROIMM(A2137Type.Enum.forString(immtrai.get(indiceImmTraiVector).toString()));
          }
          indiceImmTraiVector++; // indiceImmTraiVector=6
          if (verificaEsistenzaValore(immtrai.get(indiceImmTraiVector))) {
            tab3.setT2IVALIMM(Double.parseDouble(immtrai.get(indiceImmTraiVector).toString()));
          }
        }
      }
    }
  }
  
  /**
   * Interventi del programma triennale (lavori).
   * 
   * @param listaTab2 ListaTab2Type
   * @param params Parametri
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private static void valorizzaListaTab2LType(final ListaTab2Type listaTab2,
      final long codicePianoTriennale, final SqlManager sqlManager) throws SQLException, GestoreException {
    String sqlRecuperaINTERVENTO = "SELECT CODRUP, CONINT, AFLINT, " // 0,1,2
      + "AILINT, ANNINT, ANNRIF, APCINT, " // 3, 4, 5, 6
      + "CATINT, CODCPV, CODINT, COMINT, " // 7, 8, 9, 10
      + "CONINT, DESINT, DI1INT, DI2INT, " // 11, 12, 13, 14
      + "DI3INT, FIINTR, AL1TRI, AL2TRI, " // 15, 16, 17, 18
      + "AL3TRI, BI1TRI, BI2TRI, BI3TRI, " // 19, 20, 21, 22
      + "ICPINT, DV1TRI, DV2TRI, DV3TRI, " // 23, 24, 25, 26
      + "IM1TRI, IM2TRI, IM3TRI, MU1TRI, " // 27, 28, 29, 30
      + "MU2TRI, MU3TRI, INTERV, PR1TRI, " // 31, 32, 33, 34
      + "PR2TRI, PR3TRI, NUTS,   PRGINT, " // 35, 36, 37, 38
      + "SEZINT, STAPRO, TCPINT, TFLINT, " // 39, 40, 41, 42
      + "TILINT, TOTINT, URCINT, CODINT,  " // 43, 44, 45, 46
      + "CUPPRG, NPROGINT " // 47, 48
      + "FROM INTTRI WHERE CONTRI = ?";
    Tab2LType tab = null;
    Long codicePianoTri = new Long(codicePianoTriennale);

    List< ? > listaInterventi = sqlManager.getListVector(sqlRecuperaINTERVENTO,
        new Object[] { codicePianoTri });

    for (int i = 0; i < listaInterventi.size(); i++) {
      List< ? > intervento = (List< ? >) listaInterventi.get(i);
      tab = listaTab2.addNewTab2();

      int indiceInterventoVector = 0; // indiceInterventoVector=0
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        String codrup = intervento.get(indiceInterventoVector).toString();
        Arch2Type arch2 = tab.addNewArch2();
        valorizzaArch2Type(arch2, codrup, sqlManager);
      }
      indiceInterventoVector++; // indiceInterventoVector=1
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        long conint = Long.parseLong(intervento.get(indiceInterventoVector).toString());
        valorizzaListaTab3Type(tab, codicePianoTri, conint, sqlManager);
      }
      indiceInterventoVector++; // indiceInterventoVector=2
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2AFLINT(Integer.parseInt(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=3
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2AILINT(Integer.parseInt(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=4
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2ANNINT(convertiFlag(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=5
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2ANNRIF(Integer.parseInt(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=6
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2APCINT(convertiFlag(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=7
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2CATINT(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=8
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2CODCPV(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=9
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2CODINT(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=10
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2COMINT(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=11
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2CONINT(Integer.parseInt(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=12
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2DESINT(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=13
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2DI1INT(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=14
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2DI2INT(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=15
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2DI3INT(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=16
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2FIINTR(S2016Type.Enum.forString(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=17
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IAL1TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=18
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IAL2TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=19
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IAL3TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=20
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IBI1TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=21
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IBI2TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=22
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IBI3TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=23
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2ICPINT(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=24
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IDV1TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=25
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IDV2TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=26
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IDV3TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=27
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IIM1TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=28
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IIM2TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=29
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IIM3TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=30
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IMU1TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=31
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IMU2TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=32
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IMU3TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=33
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2INTERV(S2005Type.Enum.forString(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=34
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IPR1TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=35
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IPR2TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=36
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2IPR3TRI(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=37
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2NUTS(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=38
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2PRGINT(A2102Type.Enum.forString(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=39
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2SEZINT(S2008Type.Enum.forString(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=40
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2STAPRO(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=41
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2TCPINT(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=42
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2TFLINT(Integer.parseInt(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=43
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2TILINT(Integer.parseInt(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=44
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2TOTINT(Double.parseDouble(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=45
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2URCINT(convertiFlag(intervento.get(indiceInterventoVector).toString()));
      }
      indiceInterventoVector++; // indiceInterventoVector=46
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2CODINT(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=47
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
        tab.setT2CUPPRG(intervento.get(indiceInterventoVector).toString());
      }
      indiceInterventoVector++; // indiceInterventoVector=48
      if (verificaEsistenzaValore(intervento.get(indiceInterventoVector))) {
    	tab.setT2NPROGINT(Integer.parseInt(intervento.get(indiceInterventoVector).toString()));
      }
    }
  }

  /**
   * Lavori in economia in piani triennali per lavori.
   * 
   * @param listaTab2bis1 ListaTab2Bis1Type
   * @param params Parametri
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private static void valorizzaListaTab2Bis1Type(final ListaTab2Bis1Type listaTab2bis1, 
      final long codicePianoTriennale, final SqlManager sqlManager) throws SQLException, GestoreException {
    
    String sqlRecuperaECOTRI = "select CONECO, DESCRI, CUPPRG, STIMA from ECOTRI where CONTRI=? order by CONECO ASC";
    Long idPianoTriennali = new Long(codicePianoTriennale);
    
    List< ? > listaOpereInEconomia = sqlManager.getListVector(sqlRecuperaECOTRI, 
        new Object[] { idPianoTriennali } );
    
    if (listaOpereInEconomia != null && listaOpereInEconomia.size() > 0) {
      for (int i = 0; i < listaOpereInEconomia.size(); i++) {
        List< ? > operaInEconomia = (List< ? >) listaOpereInEconomia.get(i);
        
        Tab2Bis1Type tab2bis1Type = listaTab2bis1.addNewTab2();
        int indiceInntriVector = 0;
        if (verificaEsistenzaValore(operaInEconomia.get(indiceInntriVector))) {
          tab2bis1Type.setT2ECONECO(Integer.parseInt(operaInEconomia.get(indiceInntriVector).toString()));
        }
        indiceInntriVector++; // indiceInntriVector=1
        if (verificaEsistenzaValore(operaInEconomia.get(indiceInntriVector))) {
          tab2bis1Type.setT2EDESCRI(operaInEconomia.get(indiceInntriVector).toString());
        }
        indiceInntriVector++; // indiceInntriVector=2
        if (verificaEsistenzaValore(operaInEconomia.get(indiceInntriVector))) {
          tab2bis1Type.setT2ECUPPRG(operaInEconomia.get(indiceInntriVector).toString());
        }
        indiceInntriVector++; // indiceInntriVector=3
        if (verificaEsistenzaValore(operaInEconomia.get(indiceInntriVector))) {
          tab2bis1Type.setT2ESTIMA(Double.parseDouble(operaInEconomia.get(indiceInntriVector).toString()));
        }
      }
    }
  }
  
  /**
   * Interventi del programma triennale (forniture o servizi).
   * 
   * @param listaTab2FS ListaTab2FSType
   * @param params Parametri
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private static void valorizzaListaTab2FSType(final ListaTab2FSType listaTab2FS,
      final long codicePianoTriennale, final SqlManager sqlManager) throws SQLException,
      GestoreException {

    String sqlRecuperaInterventoFS = "SELECT "
      + "CONINT, DESINT, CODRUP, COMINT, NUTS, "   // 0,1,2,3,4
      + "CODCPV, ANNINT, TIPOIN, PRGINT, MESEIN, " // 5,6,7,8,9
      + "NORRIF, STRUPR, MANTRI, AL1TRI, BI1TRI, " // 10,11,12,13,14
      + "RG1TRI, IMPRFS, DI1INT, DITINT, CODINT, " // 15,16,17,18,19
      + "MU1TRI, PR1TRI, CUPPRG "                  // 20,21,22
      + "FROM INTTRI WHERE CONTRI = ? ";
    Long idPianoTriennale = new Long(codicePianoTriennale);
    Tab2FSType tab;

    List< ? > listaInterventi = sqlManager.getListVector(
        sqlRecuperaInterventoFS, new Object[] { idPianoTriennale });

    for (int i = 0; i < listaInterventi.size(); i++) {

      List< ? > intervento = (List< ? >) listaInterventi.get(i);

      tab = listaTab2FS.addNewTab2();
      int indiceInntriVector = 0;
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2CONINT(Integer.parseInt(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=1
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2DESINT(intervento.get(indiceInntriVector).toString());
      }
      indiceInntriVector++; // indiceInntriVector=2
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        String codrup = intervento.get(indiceInntriVector).toString();
        Arch2Type arch2 = tab.addNewArch2();
        valorizzaArch2Type(arch2, codrup, sqlManager);
      }
      indiceInntriVector++; // indiceInntriVector=3
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2COMINT(intervento.get(indiceInntriVector).toString());
      }
      indiceInntriVector++; // indiceInntriVector=4
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2NUTS(intervento.get(indiceInntriVector).toString());
      }
      indiceInntriVector++; // indiceInntriVector=5
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2CODCPV(intervento.get(indiceInntriVector).toString());
      }
      indiceInntriVector = indiceInntriVector + 2; // indiceInntriVector=7
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setFSTIPO(W3Z13Type.Enum.forString(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=8
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2PRGINT(A2102Type.Enum.forString(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=9
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setFSMESE(W3995Type.Enum.forString(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=10
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setFSNORMA(intervento.get(indiceInntriVector).toString());
      }
      indiceInntriVector++; // indiceInntriVector=11
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setFSSTRUM(intervento.get(indiceInntriVector).toString());
      }
      indiceInntriVector++; // indiceInntriVector=12
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setFSMANOTRI(convertiFlag(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=13
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2IAL1TRI(Double.parseDouble(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=14
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2IBI1TRI(Double.parseDouble(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=15
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setFSIRISREG(Double.parseDouble(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=16
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setSUIAL1TRI(Double.parseDouble(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector = indiceInntriVector + 2; // indiceInntriVector=18
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2DITINT(Double.parseDouble(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=19
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2CODINT(intervento.get(indiceInntriVector).toString());
      }
      indiceInntriVector++; // indiceInntriVector=20
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2IMU1TRI(Double.parseDouble(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=21
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2IPR1TRI(Double.parseDouble(intervento.get(indiceInntriVector).toString()));
      }
      indiceInntriVector++; // indiceInntriVector=22
      if (verificaEsistenzaValore(intervento.get(indiceInntriVector))) {
        tab.setT2CUPPRG(intervento.get(indiceInntriVector).toString());
      }
    }
  }

  /**
   * Comunicazione esito.
   * 
   * @param tab30 Tab30Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param sqlManager SqlManager
   * @param w9Manager W9Manager
   * @throws SQLException SQLException
   * @throws IOException IOException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab30Type(final Tab30Type tab30, final long codGara,
      final long codLott, final SqlManager sqlManager, final W9Manager w9Manager)
  throws SQLException, IOException, ParseException {

    String sqlRecuperaW9ESITO = "SELECT ESITO_PROCEDURA, DATA_VERB_AGGIUDICAZIONE, IMPORTO_AGGIUDICAZIONE "
      + " FROM W9ESITO WHERE CODGARA=? AND CODLOTT=?";

    // Estrazione dati dell'esito da esportare
    Vector< ? > datiW9ESITO = sqlManager.getVector(sqlRecuperaW9ESITO, new Object[]{codGara, codLott});
    if (datiW9ESITO != null && datiW9ESITO.size() > 0) {
      Object esitoProcedura = ((JdbcParametro) datiW9ESITO.get(0)).getValue();

      if (verificaEsistenzaValore(esitoProcedura)) {
        tab30.setW9LOESIPROC(W3022Type.Enum.forString(esitoProcedura.toString()));
      }
      if (verificaEsistenzaValore(datiW9ESITO.get(1).toString())) {
        tab30.setW3ESDVERB(dateString2Calendar(datiW9ESITO.get(1).toString()));
      }
      if (verificaEsistenzaValore(datiW9ESITO.get(2))) {
        tab30.setW3ESIMPAGGI(((Double) ((JdbcParametro) datiW9ESITO.get(2)).getValue()).doubleValue());
      }
      // File allegato relativo all'esito
      HashMap<String, Object> hashMapFileEsito = new HashMap<String, Object>();
      hashMapFileEsito.put("codGara", codGara);
      hashMapFileEsito.put("codLotto", codLott);
      BlobFile fileEsito = w9Manager.getFileAllegato("ESITO", hashMapFileEsito);
      if (fileEsito != null && fileEsito.getStream() != null) {
        tab30.setFile(fileEsito.getStream());
      }
    }
  }

  /**
   * Anagrafica gara/lotto e bando di gara.
   * 
   * @param tab4 Tab4Type
   * @param codGara Codice della gara
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab4Type(final Tab4Type tab4, final long codGara,
      final SqlManager sqlManager) throws SQLException, GestoreException, ParseException {
    String sqlRecuperaGara = "select "
      + " FLAG_SA_AGENTE, IDAVGARA, ID_TIPOLOGIA_SA, " // 0, 1, 2
      + " IMPORTO_GARA, IMPDIS, NLOTTI, " // 3, 4, 5
      + " OGGETTO, TIPO_APP, CODEIN, " // 6, 7, 8
      + " IDCC, DENOM_SA_AGENTE, CF_SA_AGENTE, " // 9, 10, 11
      + " RUP, FLAG_CENTRALE_STIPULA, TIPOLOGIA_PROCEDURA, " // 12, 13, 14
      + " FLAG_ENTE_SPECIALE, CIG_ACCQUADRO, ID_MODO_INDIZIONE, " // 15, 16, 17
      + " RIC_ALLUV, INDSEDE, COMSEDE, PROSEDE, CAM, SISMA, IDUFFICIO," // 18, 19, 20, 21, 22, 23, 24
      + " DGURI, DSCADE, " // 25, 26
      + " SITUAZIONE, PREV_BANDO " //27, 28
      + " FROM W9GARA WHERE CODGARA=?";

    String codein = "";
    long idcc = -1;
    long idufficio = -1;

    Vector< ? > gara = sqlManager.getVector(sqlRecuperaGara,
        new Object[] { codGara });

    int indiceW9garaVector = 0; // indiceW9garaVector=0
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3FLAGSA(convertiFlag(gara.get(indiceW9garaVector).toString()));
    }
    indiceW9garaVector++; // indiceW9garaVector=1
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3IDGARA(gara.get(indiceW9garaVector).toString());
    }
    indiceW9garaVector++; // indiceW9garaVector=2
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3IDTIPOL(W3001Type.Enum.forString(gara.get(indiceW9garaVector).toString()));
    }
    indiceW9garaVector++; // indiceW9garaVector=3
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3IGARA(Double.parseDouble(gara.get(indiceW9garaVector).toString()));
    }
    indiceW9garaVector++; // indiceW9garaVector=4
    //if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
    //  tab4.setW3IMPDIS(convertiFlag(gara.get(indiceW9garaVector).toString()));
    //}
    indiceW9garaVector++; // indiceW9garaVector=5
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3NLOTTI(Integer.parseInt(gara.get(indiceW9garaVector).toString()));
    }
    indiceW9garaVector++; // indiceW9garaVector=6
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3OGGETTO1(gara.get(indiceW9garaVector).toString());
    }
    indiceW9garaVector++; // indiceW9garaVector=7
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3TIPOAPP(W3999Type.Enum.forString(gara.get(indiceW9garaVector).toString()));
    }
    indiceW9garaVector++; // indiceW9garaVector=8
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      codein = gara.get(indiceW9garaVector).toString();
      Arch1Type arch1 = tab4.addNewArch1();
      valorizzaArch1Type(arch1, codein, sqlManager);
    }
    indiceW9garaVector++; // indiceW9garaVector=9
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      idcc = Long.parseLong(gara.get(indiceW9garaVector).toString());
      Arch5Type arch5 = tab4.addNewArch5();
      valorizzaArch5Type(arch5, idcc, sqlManager);
    }
    indiceW9garaVector++; // indiceW9garaVector=10
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3GASAAGENTE(gara.get(indiceW9garaVector).toString());
    }
    indiceW9garaVector++; // indiceW9garaVector=11
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3GACFAGENTE(gara.get(indiceW9garaVector).toString());
    }

    // non c'e' il campo RUP ne' l'oggetto arch2
    indiceW9garaVector += 2; // indiceW9garaVector=13
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GASTIPULA(convertiFlag(gara.get(indiceW9garaVector).toString()));
    }
    indiceW9garaVector++; // indiceW9garaVector=14
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GATIPROC(W3024Type.Enum.forString((gara.get(indiceW9garaVector).toString())));
    }
    indiceW9garaVector++; // indiceW9garaVector=15
    // Flag ente speciale
    tab4.setW9GAFLAGENT(W3Z08Type.Enum.forString(gara.get(indiceW9garaVector).toString()));

    // CIG Accordo Quadro
    indiceW9garaVector++; // indiceW9garaVector=16
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GACIGAQ(gara.get(indiceW9garaVector).toString());
    }
    // Modo indizione gara
    indiceW9garaVector++; // indiceW9garaVector=17
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GAMODIND(W3008Type.Enum.forString(gara.get(indiceW9garaVector).toString()));
    }
    // Interv. ai sensi ordinanza ricostr. alluvione Lunigiana ed Elba
    indiceW9garaVector++; // indiceW9garaVector=18
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GARICALLUV(convertiFlag(gara.get(indiceW9garaVector).toString()));
    }
    // Indirizzo sede di gara
    indiceW9garaVector++; // indiceW9garaVector=19
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GAINDSEDE(gara.get(indiceW9garaVector).toString());
    }
    // Comune sede di gara
    indiceW9garaVector++; // indiceW9garaVector=20
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GACOMSEDE(gara.get(indiceW9garaVector).toString());
    }
    // Provincia sede di gara
    indiceW9garaVector++; // indiceW9garaVector=21
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GAPROSEDE(gara.get(indiceW9garaVector).toString());
    }
    // Procedura in ambito delle disposizioni art.34 Dlgs. 50/2016?
    indiceW9garaVector++; // indiceW9garaVector=22
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GACAM(convertiFlag(gara.get(indiceW9garaVector).toString()));
    }
    // Nesso di causalità con gli eventi sismici maggio 2012?
    indiceW9garaVector++; // indiceW9garaVector=23
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GASISMA(convertiFlag(gara.get(indiceW9garaVector).toString()));
    }
    //Archivio uffici
    indiceW9garaVector++; // indiceW9garaVector=24
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      idufficio = Long.parseLong(gara.get(indiceW9garaVector).toString());
      Arch6Type arch6 = tab4.addNewArch6();
      valorizzaArch6Type(arch6, idufficio, sqlManager);
    }
   
    // Data pubbl.bando su GURI,se prevista,ovvero su albo pretorio
    indiceW9garaVector++; // indiceW9garaVector=25
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3DGURI(dateString2Calendar(gara.get(indiceW9garaVector).toString()));
    }
    //Data scadenza del bando
    indiceW9garaVector++; // indiceW9garaVector=26
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW3DSCADB(dateString2Calendar(gara.get(indiceW9garaVector).toString()));
    }
    
    //Situazione
    indiceW9garaVector++; // indiceW9garaVector=27
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GASITUAZ(W9007Type.Enum.forString(gara.get(indiceW9garaVector).toString()));
    }
    
    //Prevista la pubblicazione del bando
    indiceW9garaVector++; // indiceW9garaVector=28
    if (verificaEsistenzaValore(gara.get(indiceW9garaVector))) {
      tab4.setW9GABANDO(convertiFlag(gara.get(indiceW9garaVector).toString()));
    }
    
    
  }

  /**
   * Anagrafica gara/lotto: documenti allegati.
   * 
   * @param richiestaPubblicazioniDoc RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraType
   * @param codiceGara Codice della gara
   * @param sqlManager SqlManager
   * @param w9Manager W9Manager
   * @throws SQLException SQLException
   * @throws IOException IOException
   */
  private static void valorizzaListaTab41Type(RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraType richiestaAnagrafica,
      final Long codiceGara, final SqlManager sqlManager,
      final W9Manager w9Manager) throws SQLException, IOException {
    
    String sqlRecuperaW9DOCGARA = "SELECT TITOLO, URL, NUMDOC "
        + " FROM W9DOCGARA WHERE CODGARA = ? and NUM_PUBB = ? ORDER BY NUMDOC";

    List< ? > datiW9DOCGARA = sqlManager.getListVector(sqlRecuperaW9DOCGARA,
        new Object[] { codiceGara, new Long(1) });

    if (datiW9DOCGARA != null && datiW9DOCGARA.size() > 0) {
      
      for (int i = 0; i < datiW9DOCGARA.size(); i++) {
        List< ? > rigaW9DOCGARA = (List< ? >) datiW9DOCGARA.get(i);
        Tab41Type tab41 = richiestaAnagrafica.addNewListaTab41();

        if (verificaEsistenzaValore(rigaW9DOCGARA.get(0))) {
          tab41.setW9DGTITOLO(rigaW9DOCGARA.get(0).toString());
        }
        if (verificaEsistenzaValore(rigaW9DOCGARA.get(1))) {
          tab41.setW9DGURL(rigaW9DOCGARA.get(1).toString());
        }
        
        Long numdoc = Long.parseLong(rigaW9DOCGARA.get(2).toString());

        HashMap<String, Object> hashMapFileAllegato = new HashMap<String, Object>();
        hashMapFileAllegato.put("codGara", codiceGara);
        hashMapFileAllegato.put("numdoc", numdoc);
        hashMapFileAllegato.put("num_pubb", new Long(1));
        BlobFile fileAllegato = w9Manager.getFileAllegato("GARA", hashMapFileAllegato);
        w9Manager.getListaDocumentiBando(codiceGara);
        if (fileAllegato != null && fileAllegato.getStream() != null) {
          tab41.setFile(fileAllegato.getStream());
        }
      }
    }
  }
  
  private static void valorizzaDocumentiPubblicazione(
      final RichiestaRichiestaRispostaSincronaPubblicazioneDocumentiType richiestaPubblicazioniDoc,
      final Long codiceGara, final Long numeroPubblicazione, final SqlManager sqlManager,
      final W9Manager w9Manager)
          throws SQLException, IOException {

    String sqlRecuperaW9DOCGARA = "SELECT TITOLO, URL, NUMDOC "
      + " FROM W9DOCGARA WHERE CODGARA = ? and NUM_PUBB = ? ORDER BY NUMDOC";

    List< ? > datiW9DOCGARA = sqlManager.getListVector(sqlRecuperaW9DOCGARA,
        new Object[] { codiceGara, numeroPubblicazione });

    if (datiW9DOCGARA != null && datiW9DOCGARA.size() > 0) {

      for (int i = 0; i < datiW9DOCGARA.size(); i++) {
        List< ? > rigaW9DOCGARA = (List< ? >) datiW9DOCGARA.get(i);
        Tab41Type tab41 = richiestaPubblicazioniDoc.addNewDocumenti();

        if (verificaEsistenzaValore(rigaW9DOCGARA.get(0))) {
          tab41.setW9DGTITOLO(rigaW9DOCGARA.get(0).toString());
        }
        if (verificaEsistenzaValore(rigaW9DOCGARA.get(1))) {
          tab41.setW9DGURL(rigaW9DOCGARA.get(1).toString());
        }
        
        Long numdoc = Long.parseLong(rigaW9DOCGARA.get(2).toString());

        HashMap<String, Object> hashMapFileAllegato = new HashMap<String, Object>();
        hashMapFileAllegato.put("codGara", codiceGara);
        hashMapFileAllegato.put("numdoc", numdoc);
        hashMapFileAllegato.put("num_pubb", numeroPubblicazione);
        BlobFile fileAllegato = w9Manager.getFileAllegato("GARA", hashMapFileAllegato);
        if (fileAllegato != null && fileAllegato.getStream() != null) {
          tab41.setFile(fileAllegato.getStream());
        }
      }
    }
  }

  /**
   * Anagrafica gara/lotto: lista dei lotti.
   * 
   * @param listaTab5 ListaTab5Type
   * @param codGara Codice della gara
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException 
   */
  private static void valorizzaListaTab5Type(final ListaTab5Type listaTab5,
      final Long codGara, final SqlManager sqlManager, boolean exportIncarichiProfessionali)
          throws SQLException, ParseException {
    String sqlRecuperaLotti = "SELECT CIG, CIGCOM, CLASCAT, CODLOTT, COMCON, CPV, CUP, " // 0, 1, 2, 3, 4, 5, 6
      + "DESCOM, FLAG_ENTE_SPECIALE, IMPORTO_ATTUAZIONE_SICUREZZA, ID_CATEGORIA_PREVALENTE, " // 7, 8, 9, 10
      + "IMPDISP, ID_SCELTA_CONTRAENTE, ID_TIPO_PRESTAZIONE, IMPORTO_LOTTO, ART_E1, ART_E2, " // 11, 12, 13, 14, 15, 16
      + "LUOGO_ISTAT, LUOGO_NUTS, MANOD, ID_MODO_GARA, NLOTTO, OGGETTO, SOMMA_URGENZA,"  // 17, 18, 19, 20, 21, 22, 23
      +	"TIPO_CONTRATTO, RUP, CODINT, DATA_PUBBLICAZIONE, EXSOTTOSOGLIA, ID_SCELTA_CONTRAENTE_50, " // 24, 25, 26, 27, 28, 29
      + "SITUAZIONE, DAEXPORT, CUIINT, URL_EPROC  " // 30, 31, 32, 33
      + "FROM W9LOTT WHERE CODGARA = ? ";

    if (codGara != null) {
      List< ? > listaLotti = sqlManager.getListVector(sqlRecuperaLotti, new Object[]{ codGara });

      for (int i = 0; i < listaLotti.size(); i++) {
        List< ? >lotto = (List< ? >) listaLotti.get(i);
        long codLott = -1;
        if (verificaEsistenzaValore(lotto.get(3))) {
          codLott = Long.parseLong(lotto.get(3).toString());
        }

        Tab5Type tab5 = listaTab5.addNewTab5Type();
        if (codLott != -1 && codLott > 0) {
          ListaTab51Type listaTab51 = tab5.addNewListaTab51();
          valorizzaListaTab51Type(listaTab51, codGara, codLott, sqlManager);
          ListaTab52Type listaTab52 = tab5.addNewListaTab52();
          valorizzaListaTab52Type(listaTab52, codGara, codLott, sqlManager);
          valorizzaListaTab53Type(tab5, codGara, codLott, sqlManager);
          valorizzaListaTab54Type(tab5, codGara, codLott, sqlManager);
          valorizzaListaTab55Type(tab5, codGara, codLott, sqlManager);
          if (exportIncarichiProfessionali) {
            // Valorizzazione degli incarichi professionali del lotto
            valorizzaListaTab101Type(tab5, codGara, codLott, 1,
                " AND SEZIONE in ('RA', 'PA') ", sqlManager);
          }
        }

        int indiceW9LottVector = 0; // indiceW9LottVector=0
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3CIG(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=1
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3CIGCOM(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=2
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3CLASCAT(W3Z11Type.Enum.forString(lotto.get(indiceW9LottVector).toString()));
        }
        if (codLott != -1 && codLott > 0) {
          tab5.setW3CODLOTT(codLott);
        }
        indiceW9LottVector++; // indiceW9LottVector=3
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          codLott = Long.parseLong(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=4
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3COMCON(convertiFlag(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=5
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3CPV(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=6
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3CUP(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=7
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3DESCOM(W3997Type.Enum.forString(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=8
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          if (lotto.get(indiceW9LottVector).toString().trim().toUpperCase().equals("O")) {
            tab5.setW3FLAGENT(W3Z08Type.O);
          } else if (lotto.get(indiceW9LottVector).toString().trim().toUpperCase().equals("S")) {
            tab5.setW3FLAGENT(W3Z08Type.S);
          }
        }
        indiceW9LottVector++; // indiceW9LottVector=9
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3IATTSIC(Double.parseDouble(lotto.get(indiceW9LottVector).toString()));
        } else {
          tab5.setW3IATTSIC(new Double(0));
        }
        indiceW9LottVector++; // indiceW9LottVector=10
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3IDCATE4(W3Z03Type.Enum.forString(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=11
        //if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
        //  tab5.setW3IDISPLOT(convertiFlag(lotto.get(indiceW9LottVector).toString()));
        //}
        indiceW9LottVector++; // indiceW9LottVector=12
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3IDSCEL2(W3005Type.Enum.forString(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=13
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3IDTIPO(W3003Type.Enum.forString(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=14
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3ILOTTO(Double.parseDouble(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=15
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3LOARTE1(convertiFlag(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=16
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3LOARTE2(convertiFlag(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=17
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3LUOGOIS(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=18
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3LUOGONU(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=19
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3MANOLO(convertiFlag(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=20
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3MODGAR(W3007Type.Enum.forString(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=21
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3NLOTTO(Integer.parseInt(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=22
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3OGGETTO2(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=23
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3SOMMAUR(convertiFlag(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=24
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW3TIPOCON(W3Z05Type.Enum.forString(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=25
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          Arch2Type arch2 = tab5.addNewArch2();
          valorizzaArch2Type(arch2, lotto.get(indiceW9LottVector).toString(), sqlManager);
        }
        indiceW9LottVector++; // indiceW9LottVector=26
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setT2IDCONINT(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=27
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW9DPUBBLICAZ(dateString2Calendar(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=28
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW9EXSOTTOSOGLIA(convertiFlag(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=29
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW9IDSCEL50(Integer.parseInt(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=30
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW9LOSITUAZ(W9007Type.Enum.forString(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=31
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW9LODAEXP(convertiFlag(lotto.get(indiceW9LottVector).toString()));
        }
        indiceW9LottVector++; // indiceW9LottVector=32
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW9LOCUIINT(lotto.get(indiceW9LottVector).toString());
        }
        indiceW9LottVector++; // indiceW9LottVector=33
        if (verificaEsistenzaValore(lotto.get(indiceW9LottVector))) {
          tab5.setW9LOURLEPROC(lotto.get(indiceW9LottVector).toString());
        }
      }
    }
  }

  /**
   * Anagrafica gara/lotto: lista forniture del lotto.
   * 
   * @param listaTab51 ListaTab51Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaListaTab51Type(final ListaTab51Type listaTab51,
      final Long codGara, final Long codLott, final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaW9APPAFORN = "SELECT NUM_APPAF, ID_APPALTO FROM W9APPAFORN WHERE CODGARA = ? AND CODLOTT = ? ";
    Tab51Type tab51;

    List< ? > listaW9appaforn;
    List< ? > w9appaforn = new ArrayList<Object>();

    if (codGara != null && codLott != null) {
      listaW9appaforn = sqlManager.getListVector(sqlRecuperaW9APPAFORN,
          new Object[] { codGara, codLott });

      for (int i = 0; i < listaW9appaforn.size(); i++) {
        w9appaforn = (List< ? >) listaW9appaforn.get(i);

        if (verificaEsistenzaValore(w9appaforn.get(1))) {
          tab51 = listaTab51.addNewTab51();
          tab51.setW3IDAPP04(W3019Type.Enum.forString(w9appaforn.get(1).toString()));
        }
      }
    }
  }

  /**
   * Anagrafica gara/lotto: lista lavorazioni del lotto.
   * 
   * @param listaTab52 ListaTab52Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaListaTab52Type(final ListaTab52Type listaTab52,
      final Long codGara, final Long codLott, final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaW9APPALAV = "SELECT NUM_APPAL, ID_APPALTO FROM W9APPALAV WHERE CODGARA = ? AND CODLOTT = ?";
    Tab52Type tab52;

    List< ? > listaW9appalav;
    List< ? > w9appalav = new ArrayList<Object>();

    if (codGara != null && codLott != null) {
      listaW9appalav = sqlManager.getListVector(sqlRecuperaW9APPALAV,
          new Object[] { codGara, codLott });

      for (int i = 0; i < listaW9appalav.size(); i++) {
        w9appalav = (List< ? >) listaW9appalav.get(i);

        if (verificaEsistenzaValore(w9appalav.get(1))) {
          tab52 = listaTab52.addNewTab52();
          tab52.setW3IDAPP05(W3002Type.Enum.forString(w9appalav.get(1).toString()));
        }
      }
    }
  }

  /**
   * Anagrafica gara/lotto: lista delle condizioni del lotto.
   * 
   * @param tab5 Tab5Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaListaTab53Type(final Tab5Type tab5, final Long codGara,
      final Long codLott, final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaW9COND = "SELECT NUM_COND, ID_CONDIZIONE FROM W9COND WHERE CODGARA = ? AND CODLOTT = ?";
    Tab53Type tab53;

    List< ? > listaW9cond;
    List< ? > w9cond = new ArrayList<Object>();

    if (codGara != null && codLott != null) {
      listaW9cond = sqlManager.getListVector(sqlRecuperaW9COND,
          new Object[] { codGara, codLott });
      if (listaW9cond.size() > 0) {
        ListaTab53Type listaTab53 = tab5.addNewListaTab53();

        for (int i = 0; i < listaW9cond.size(); i++) {
          w9cond = (List< ? >) listaW9cond.get(i);

          if (verificaEsistenzaValore(w9cond.get(1))) {
            tab53 = listaTab53.addNewTab53();
            tab53.setW3IDCONDI(W3006Type.Enum.forString(w9cond.get(1).toString()));
          }
        }
      }
    }
  }

  /**
   * Anagrafica gara/lotto: lista dei codici CPV del lotto.
   * 
   * @param tab5 Tab5Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaListaTab54Type(final Tab5Type tab5, final Long codGara,
      final Long codLott, final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaW9CPV = "SELECT NUM_CPV, CPV FROM W9CPV WHERE CODGARA = ? AND CODLOTT = ?";
    Tab54Type tab54;

    List< ? > listaW9cpv;
    List< ? > w9cpv = new ArrayList<Object>();

    if (codGara != null && codLott != null) {
      listaW9cpv = sqlManager.getListVector(sqlRecuperaW9CPV, new Object[] {
          codGara, codLott });
      if (listaW9cpv.size() > 0) {
        ListaTab54Type listaTab54 = tab5.addNewListaTab54();

        for (int i = 0; i < listaW9cpv.size(); i++) {
          w9cpv = (List< ? >) listaW9cpv.get(i);
          tab54 = listaTab54.addNewTab54();

          if (verificaEsistenzaValore(w9cpv.get(1))) {
            tab54.setW3CPVCOMP(w9cpv.get(1).toString());
          }
        }
      }
    }
  }

  /**
   * Anagrafica gara/lotto: categorie di iscrizione del lotto.
   * 
   * @param tab5 Tab5Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaListaTab55Type(final Tab5Type tab5, final Long codGara,
      final Long codLott, final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaW9LOTTCATE = "SELECT CODGARA, CODLOTT, NUM_CATE, " // 0, 1, 2
      + " CATEGORIA, CLASCAT, SCORPORABILE, SUBAPPALTABILE " // 3, 4, 5, 6
      + " FROM W9LOTTCATE WHERE CODGARA = ? AND CODLOTT = ? ";

    if (codGara != null && codLott != null) {
      List< ? > listaW9lottcate = sqlManager.getListVector(sqlRecuperaW9LOTTCATE,
          new Object[] { codGara, codLott });
      if (listaW9lottcate.size() > 0) {
        ListaTab55Type listaTab55 = tab5.addNewListaTab55();

        for (int i = 0; i < listaW9lottcate.size(); i++) {
          List< ? > w9lottcate = (List< ? >) listaW9lottcate.get(i);
          Tab55Type tab55 = listaTab55.addNewTab55();

          int indiceW9LottCateVector = 3; // indiceW9LottCateVector=3
          if (verificaEsistenzaValore(w9lottcate.get(indiceW9LottCateVector))) {
            tab55.setW3CATEGORI(W3Z03Type.Enum.forString(w9lottcate.get(indiceW9LottCateVector).toString()));
          }
          indiceW9LottCateVector++; // indiceW9LottCateVector=4
          if (verificaEsistenzaValore(w9lottcate.get(indiceW9LottCateVector))) {
            tab55.setW3CLASCATCA(W3Z11Type.Enum.forString(w9lottcate.get(indiceW9LottCateVector).toString()));
          }
          indiceW9LottCateVector++; // indiceW9LottCateVector=5
          if (verificaEsistenzaValore(w9lottcate.get(indiceW9LottCateVector))) {
            tab55.setW9LCSCORPORA(convertiFlag(w9lottcate.get(indiceW9LottCateVector).toString()));
          }
          indiceW9LottCateVector++; // indiceW9LottCateVector=6
          if (verificaEsistenzaValore(w9lottcate.get(indiceW9LottCateVector))) {
            tab55.setW9LCSUBAPPAL(convertiFlag(w9lottcate.get(indiceW9LottCateVector).toString()));
          }
        }
      }
    }
  }

  /**
   * Pubblicazione avviso.
   * 
   * @param tab7 Tab7Type
   * @param idAvviso IdAvviso
   * @param codein Parametro
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab7Type(final Tab7Type tab7, final long idAvviso,
      final String codein, long codiceSistema, final SqlManager sqlManager) throws SQLException, ParseException {
    String sqlRecuperaAVVISI = 
	  "SELECT AVVISO.CIG, AVVISO.TIPOAVV, AVVISO.IDAVVISO, AVVISO.DATAAVV, AVVISO.DESCRI "
      + "FROM AVVISO WHERE AVVISO.IDAVVISO = ? AND AVVISO.CODEIN = ? AND AVVISO.CODSISTEMA = ?";

    if (verificaEsistenzaValore(codein)) {
      List< ? > listaAvvisi = sqlManager.getListVector(sqlRecuperaAVVISI,
          new Object[] { idAvviso, codein, codiceSistema });
      if (listaAvvisi != null && listaAvvisi.size() > 0) {
        List< ? > avviso = (List< ? >) listaAvvisi.get(0);

        int indiceAvvisoVector = 0; // indiceAvvisoVector=0
        if (verificaEsistenzaValore(avviso.get(indiceAvvisoVector))) {
          tab7.setW3CIG(avviso.get(indiceAvvisoVector).toString());
        }
        indiceAvvisoVector++; // indiceAvvisoVector=1
        if (verificaEsistenzaValore(avviso.get(indiceAvvisoVector))) {
          tab7.setW3PATAVVI(W3996Type.Enum.forString(avviso.get(indiceAvvisoVector).toString()));
        }
        indiceAvvisoVector++; // indiceAvviisoVector=2
        if (verificaEsistenzaValore(avviso.get(indiceAvvisoVector))) {
          tab7.setW3PAVVISOID(Long.parseLong(avviso.get(indiceAvvisoVector).toString()));
        }
        indiceAvvisoVector++; // indiceAvviisoVector=3
        if (verificaEsistenzaValore(avviso.get(indiceAvvisoVector))) {
          tab7.setW3PADATA(dateString2Calendar(avviso.get(indiceAvvisoVector).toString()));
        }
        indiceAvvisoVector++; // indiceAvviisoVector=4
        if (verificaEsistenzaValore(avviso.get(indiceAvvisoVector))) {
          tab7.setW3PADESCRI(avviso.get(indiceAvvisoVector).toString());
        }
        tab7.setW3PACODSIS(new Long(codiceSistema));
      }
    }
  }

  /**
   * Fase semplificata aggiudicazione appalto.
   * 
   * @param tab8 Tab8Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param numAppa Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab8Type(final Tab8Type tab8, final long codGara,
      final long codLott, final long numAppa, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaAPPALTI = "SELECT "
      + " ASTA_ELETTRONICA, DATA_VERB_AGGIUDICAZIONE, " // 0, 1
      + " IMPORTO_COMPL_APPALTO, IMPORTO_COMPL_INTERVENTO, " // 2, 3
      + " IMPORTO_DISPOSIZIONE, IMPORTO_AGGIUDICAZIONE, " // 4, 5
      + " IMPORTO_SUBTOTALE, PERC_OFF_AUMENTO, " // 6, 7
      + " PERC_RIBASSO_AGG, IMPORTO_ATTUAZIONE_SICUREZZA, " // 8, 9
      + " DATA_STIPULA, DURATA_CON, " // 10, 11
      + " DATA_ATTO, NUMERO_ATTO, TIPO_ATTO, "  // 12, 13, 14
      + " IVA, IMPORTO_IVA, CODCUI "  // 15, 16, 17
      + "FROM W9APPA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ? ";

    if (codGara > 0 && codLott > 0 && numAppa > 0) {
      List< ? > listaAppalti = sqlManager.getListVector(sqlRecuperaAPPALTI,
          new Object[] { codGara, codLott, numAppa });
      List< ? > appalto = (List< ? >) listaAppalti.get(0);

      int indiceW9appaVector = 0; // indiceW9appaVector=0
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3ASTAELE(convertiFlag(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=1
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3DVERB(dateString2Calendar(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=2
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3ICAPPA(Double.parseDouble(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=3
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3ICINTE(Double.parseDouble(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=4
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3IDISPOS(Double.parseDouble(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=5
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3IMPAGGI(Double.parseDouble(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=6
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3ISUBTOT(Double.parseDouble(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=7
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3PERCOFF(Float.parseFloat(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=8
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3PERCRIB(Float.parseFloat(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=9
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW3IATTSIC(Double.parseDouble(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=10
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW9APDATASTI(dateString2Calendar(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=11
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW9APDURCON(Integer.parseInt(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=12
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW9APDATAT(dateString2Calendar(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=13
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW9APNUMAT(appalto.get(indiceW9appaVector).toString());
      }
      indiceW9appaVector++; //indiceW9appaVector=14
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW9APTIPAT(W9014Type.Enum.forString(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=15
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW9APIVA(Float.parseFloat(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=16
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW9APIMPIVA(Double.parseDouble(appalto.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=17
      if (verificaEsistenzaValore(appalto.get(indiceW9appaVector))) {
        tab8.setW9APPACODCUI(appalto.get(indiceW9appaVector).toString());
      }
    }
  }

  /**
   * Fase di aggiudicazione semplificata: lista delle imprese aggiudicatarie.
   * 
   * @param listaTab81 ListaTab81Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param numAppa Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab81Type(final ListaTab81Type listaTab81,
      final Long codGara, final Long codLott, final Long numAppa, final SqlManager sqlManager)
  throws SQLException, ParseException {
    String sqlRecuperaW9AGGI = "SELECT FLAG_AVVALIMENTO, ID_TIPOAGG, " // 0, 1
      + " RUOLO, CODIMP, CODIMP_AUSILIARIA, ID_GRUPPO " // 2, 3, 4, 5
      + "FROM W9AGGI WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ? ";

    if (codGara != null && codLott != null) {
      List< ? > listaW9aggi = sqlManager.getListVector(sqlRecuperaW9AGGI, new Object[] {
          codGara, codLott, numAppa });

      for (int i = 0; i < listaW9aggi.size(); i++) {
        List< ? > w9aggi = (List< ? >) listaW9aggi.get(i);
        Tab81Type tab81 = listaTab81.addNewTab81();

        int indiceW9aggiVector = 0; // indiceW9aggiVector=0
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          tab81.setW3FLAGAVV(W3Z09Type.Enum.forString(w9aggi.get(indiceW9aggiVector).toString()));
        }
        indiceW9aggiVector++; // indiceW9aggiVector=1
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          tab81.setW3IDTIPOA(W3010Type.Enum.forString(w9aggi.get(indiceW9aggiVector).toString()));
        }
        indiceW9aggiVector++; // indiceW9aggiVector=2
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          tab81.setW3RUOLO(W3011Type.Enum.forString(w9aggi.get(indiceW9aggiVector).toString()));
        }
        indiceW9aggiVector++; // indiceW9aggiVector=3
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          Arch3Type arch3 = tab81.addNewArch3();
          valorizzaArch3Type(arch3, w9aggi.get(indiceW9aggiVector).toString(), sqlManager);
        }
        indiceW9aggiVector++; // indiceW9aggiVector=4
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          Arch3Type arch3 = tab81.addNewArch3Avv();
          valorizzaArch3Type(arch3, w9aggi.get(indiceW9aggiVector).toString(), sqlManager);
        }
        indiceW9aggiVector++; // indiceW9aggiVector=5
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          tab81.setW3AGIDGRP(new Long(w9aggi.get(indiceW9aggiVector).toString()));
        }
      }
    }
  }

  /**
   * Fase semplificata aggiudicazione: lista dei soggetti incaricati.
   * 
   * @param listaTab82Type ListaTab82Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab82Type(final ListaTab82Type listaTab82Type,
      final long codGara, final long codLotto, final long num, final SqlManager sqlManager)
  throws SQLException, ParseException {

    valorizzaListaTab101Type(listaTab82Type, codGara, codLotto, num,
        " AND SEZIONE in ('RE', 'RS') ", sqlManager);
  }

  /**
   * Anagrafica del lotto: lista dei soggetti incaricati.
   * 
   * @param listaTab82Type ListaTab82Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  /*private static void valorizzaListaTab56Type(final ListaTab82Type listaTab82Type,
      final long codGara, final long codLotto, final long num, final SqlManager sqlManager)
  throws SQLException, ParseException {

    valorizzaListaTab101Type(listaTab82Type, codGara, codLotto, num,
        " AND SEZIONE in ('RA') ", sqlManager);
  }*/
  
  /**
   * Fase semplificata iniziale esecuzione contratto.
   * 
   * @param richiestaInizioA07 RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numIniz Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab8bisType(final
      RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type richiestaInizioA07,
      final long codGara, final long codLotto, final long numIniz, final SqlManager sqlManager)
  throws SQLException, ParseException {
    String sqlRecuperaW9INIZ = "SELECT DATA_STIPULA, DATA_TERMINE, DATA_VERB_INIZIO, FLAG_RISERVA "
      + "FROM W9INIZ WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ? ";

    Vector< ? > datiW9INIZ = sqlManager.getVector(sqlRecuperaW9INIZ,
        new Object[] { codGara, codLotto, numIniz });

    if (datiW9INIZ != null && datiW9INIZ.size() > 0) {

      Tab8BisType tab8bis = richiestaInizioA07.addNewTab8Bis();

      int indiceW9inizVector = 0; // indiceW9inizVector=0
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab8bis.setW3DATASTI(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=1
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab8bis.setW3DTERM1(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=2
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab8bis.setW3DVERBIN(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=3
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab8bis.setW3RISERVA(convertiFlag(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
    }
  }

  /**
   * Valorizza la lista degli incaricati per l'inizio contratto appalto sotto 40000.
   * 
   * @param richiestaInizioA07 RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab8bis1Type(final
      RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type richiestaInizioA07,
      final long codGara, final long codLotto, final long num, final SqlManager sqlManager)
  throws SQLException, ParseException {

    ListaTab191Type listaTab191 = ListaTab191Type.Factory.newInstance();
    valorizzaListaTab101Type(listaTab191, codGara, codLotto, num,
        " AND SEZIONE = 'IN'", sqlManager);

    richiestaInizioA07.setTab8Bis1Array(listaTab191.getTab191Array());
  }

  /**
   * Valorizzazione della descrizione delle misure aggiuntive e migliorative per la sicurezza per il
   * flusso Inizio Contratto sotto 40000 euro. 
   * 
   * @param richiestaInizioA07 RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab8bis3Type(final
      RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type richiestaInizioA07,
      final long codGara, final long codLotto, final long num, final SqlManager sqlManager)
  throws SQLException, ParseException {
    
    valorizzaTab13Type(richiestaInizioA07.addNewTab8Bis3(), codGara, codLotto, num, sqlManager);
  }
  
  /**
   * Valorizzazione della descrizione delle misure aggiuntive e migliorative per la sicurezza per il
   * flusso Inizio Contratto sopra 40000 euro. 
   * 
   * @param richiestaInizioA06 RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab8bis3Type(final
      RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Type richiestaInizioA06,
      final long codGara, final long codLotto, final long num, final SqlManager sqlManager)
  throws SQLException, ParseException {
    
    valorizzaTab13Type(richiestaInizioA06.addNewTab194(), codGara, codLotto, num, sqlManager);
  }
  
  /**
   * Valorizzazione dei documenti allegati al flusso Inizio Contratto Sotto 40000. 
   * 
   * @param richiestaInizioA07 RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @param w9Manager W9Manager
   * @throws SQLException SQLException
   * @throws IOException IOException
   */
  private static void valorizzaTab8bis31Type(final
      RichiestaRichiestaRispostaSincronaInizioContrattoSotto150000Type richiestaInizioA07,
      final long codGara, final long codLotto, final long num, final SqlManager sqlManager,
      final W9Manager w9Manager) throws SQLException, IOException {
    String sqlRecuperaW9DOCFASE = "SELECT TITOLO, NUMDOC "
      + " FROM W9DOCFASE WHERE CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM_FASE=?"
      + " ORDER BY NUMDOC ASC";
    
    List< ? > datiW9DOCFASE = sqlManager.getListVector(sqlRecuperaW9DOCFASE,
        new Object[] { codGara, codLotto, new Long(CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA), num });

    if (datiW9DOCFASE != null && datiW9DOCFASE.size() > 0) {
      for (int i = 0; i < datiW9DOCFASE.size(); i++) {
        List< ? > rigaW9DOCGARA = (List< ? >) datiW9DOCFASE.get(i);
        Tab41Type tab41 = richiestaInizioA07.addNewTab8Bis31();

        if (verificaEsistenzaValore(rigaW9DOCGARA.get(0))) {
          tab41.setW9DGTITOLO(rigaW9DOCGARA.get(0).toString());
        }
        Long numdoc = Long.parseLong(rigaW9DOCGARA.get(1).toString());

        HashMap<String, Object> hashMapFileAllegato = new HashMap<String, Object>();
        hashMapFileAllegato.put("codGara", codGara);
        hashMapFileAllegato.put("codLotto", codLotto);
        hashMapFileAllegato.put("fase_esecuzione", CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA);
        hashMapFileAllegato.put("num_fase", num);
        
        hashMapFileAllegato.put("numdoc", numdoc);
        BlobFile fileAllegato = w9Manager.getFileAllegato("FASE", hashMapFileAllegato);
        if (fileAllegato != null && fileAllegato.getStream() != null) {
          tab41.setFile(fileAllegato.getStream());
        }
      }
    }
  }
  
  /**
   * Valorizzazione dei documenti allegati al flusso Inizio Contratto Sotto 40000. 
   * 
   * @param richiestaInizioA06 RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @param w9Manager W9Manager
   * @throws SQLException SQLException
   * @throws IOException IOException
   */
  private static void valorizzaTab8bis31Type(final
      RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Type richiestaInizioA06,
      final long codGara, final long codLotto, final long num, final SqlManager sqlManager,
      final W9Manager w9Manager) throws SQLException, IOException {
    String sqlRecuperaW9DOCFASE = "SELECT TITOLO, NUMDOC "
      + " FROM W9DOCFASE WHERE CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM_FASE=?"
      + " ORDER BY NUMDOC ASC";
    
    List< ? > datiW9DOCFASE = sqlManager.getListVector(sqlRecuperaW9DOCFASE,
        new Object[] { codGara, codLotto, new Long(CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA), num });

    if (datiW9DOCFASE != null && datiW9DOCFASE.size() > 0) {
      for (int i = 0; i < datiW9DOCFASE.size(); i++) {
        List< ? > rigaW9DOCGARA = (List< ? >) datiW9DOCFASE.get(i);
        Tab41Type tab41 = richiestaInizioA06.addNewTab1941();

        if (verificaEsistenzaValore(rigaW9DOCGARA.get(0))) {
          tab41.setW9DGTITOLO(rigaW9DOCGARA.get(0).toString());
        }
        Long numdoc = Long.parseLong(rigaW9DOCGARA.get(1).toString());

        HashMap<String, Object> hashMapFileAllegato = new HashMap<String, Object>();
        hashMapFileAllegato.put("codGara", codGara);
        hashMapFileAllegato.put("codLotto", codLotto);
        hashMapFileAllegato.put("fase_esecuzione", CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA);
        hashMapFileAllegato.put("num_fase", num);
        
        hashMapFileAllegato.put("numdoc", numdoc);
        BlobFile fileAllegato = w9Manager.getFileAllegato("FASE", hashMapFileAllegato);
        if (fileAllegato != null && fileAllegato.getStream() != null) {
          tab41.setFile(fileAllegato.getStream());
        }
      }
    }
  }
  
  /**
   * Valorizza i dettagli sulla sicurezza per l'inizio contratto appalto sotto 40000.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @return Ritorna Tab8Bis2Type
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static Tab8Bis2Type getTab8Bis2Type(final long codgara, final long codlott,
      final long num, final SqlManager sqlManager) throws SQLException, ParseException {

    String sqlRecuperaW9SIC = "SELECT PIANSCIC, DIROPE, TUTOR "
      + "FROM W9SIC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_SIC = ? ";

    Vector< ? > datiW9SIC = sqlManager.getVector(sqlRecuperaW9SIC,
        new Object[] { codgara, codlott, num });

    if (datiW9SIC != null && datiW9SIC.size() > 0) {

      Tab8Bis2Type tab8bis2 = Tab8Bis2Type.Factory.newInstance();

      if (verificaEsistenzaValore(datiW9SIC.get(0))) {
        tab8bis2.setW3PIANSCIC(convertiFlag(datiW9SIC.get(0).toString()));
      }
      if (verificaEsistenzaValore(datiW9SIC.get(1))) {
        tab8bis2.setW3DIROPE(convertiFlag(datiW9SIC.get(1).toString()));
      }
      if (verificaEsistenzaValore(datiW9SIC.get(2))) {
        tab8bis2.setW3SITUTOR(convertiFlag(datiW9SIC.get(2).toString()));
      }
      return tab8bis2;
    }
    return null;
  }

  /**
   * Fase semplficata di conclusione del contratto.
   * 
   * @param tab9 Tab9Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numConc Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab9Type(final Tab9Type tab9, final long codGara,
      final long codLotto, final long numConc, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9CONC = "SELECT INTANTIC, DATA_RISOLUZIONE, DATA_ULTIMAZIONE, " // 0, 1, 2
      + "FLAG_ONERI, FLAG_POLIZZA, ID_MOTIVO_INTERR, ID_MOTIVO_RISOL, ONERI_RISOLUZIONE, " // 3, 4, 5, 6, 7
      + " ORE_LAVORATE "
      + "FROM W9CONC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_CONC = ? ";

    if (codGara > 0 && codLotto > 0 && numConc > 0) {
      List< ? > listaConc = sqlManager.getListVector(sqlRecuperaW9CONC, new Object[] {
          codGara, codLotto, numConc });
      List< ? > conc = (List< ? >) listaConc.get(0);

      int indiceW9concVector = 0; //indiceW9concVector=0
      if (verificaEsistenzaValore(conc.get(indiceW9concVector))) {
        tab9.setW3CSINTANTIC(convertiFlag(conc.get(indiceW9concVector).toString()));
      }
      indiceW9concVector++; //indiceW9concVector=1
      if (verificaEsistenzaValore(conc.get(indiceW9concVector))) {
        tab9.setW3DATARIS(dateString2Calendar(conc.get(indiceW9concVector).toString()));
      }
      indiceW9concVector++; //indiceW9concVector=2
      if (verificaEsistenzaValore(conc.get(indiceW9concVector))) {
        tab9.setW3DATAULT(dateString2Calendar(conc.get(indiceW9concVector).toString()));
      }
      indiceW9concVector++; //indiceW9concVector=3
      if (verificaEsistenzaValore(conc.get(indiceW9concVector))) {
        tab9.setW3FLAGONE(W3Z06Type.Enum.forString(conc.get(indiceW9concVector).toString()));
      }
      indiceW9concVector++; //indiceW9concVector=4
      if (verificaEsistenzaValore(conc.get(indiceW9concVector))) {
        tab9.setW3FLAGPOL(convertiFlag(conc.get(indiceW9concVector).toString()));
      }
      indiceW9concVector++; //indiceW9concVector=5
      if (verificaEsistenzaValore(conc.get(indiceW9concVector))) {
        tab9.setW3IDMOTI1(Integer.parseInt(conc.get(indiceW9concVector).toString()));
      }
      indiceW9concVector++; //indiceW9concVector=6
      if (verificaEsistenzaValore(conc.get(indiceW9concVector))) {
        tab9.setW3IDMOTI2(Integer.parseInt(conc.get(indiceW9concVector).toString()));
      }
      indiceW9concVector++; //indiceW9concVector=7
      if (verificaEsistenzaValore(conc.get(indiceW9concVector))) {
        tab9.setW3ONERIRI(Double.parseDouble(conc.get(indiceW9concVector).toString()));
      }
      indiceW9concVector++; //indiceW9concVector=8
      if (verificaEsistenzaValore(conc.get(indiceW9concVector))) {
        tab9.setW9COORELAVO(Double.parseDouble(conc.get(indiceW9concVector).toString()));
      }
    }
  }

  /**
   * Metodo generico per la gestione dei tecnici incaricati E' utilizzati in
   * vari punti.
   * 
   * @param listaTab101 ListaTab101Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param num Progressivo
   * @param whereSezione Condizione di where sulla sezione
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab101Type(final Object listaTab101, final long codGara,
      final long codLott, final long num, final String whereSezione, final SqlManager sqlManager)
  throws SQLException, ParseException {
    String sqlRecuperaW9INCA = "SELECT "
      // + " CODGARA, CODLOTT, NUM, "
      + " CODTEC, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, " // 0, 1, 2
      + " DATA_CONS_PROG_ESTERNA, ID_RUOLO, SEZIONE " // 3, 4, 5
      + " FROM W9INCA WHERE CODGARA = ? AND CODLOTT = ? AND NUM = ? "
      + whereSezione;
    Tab101Type tab101 = null;

    try {
      if (codGara > 0 && codLott > 0 && num > 0) {
        List< ? > listaW9inca = sqlManager.getListVector(sqlRecuperaW9INCA,
            new Object[] { codGara, codLott, num });

        for (int i = 0; i < listaW9inca.size(); i++) {
          List< ? > w9inca = (List< ? >) listaW9inca.get(i);

          if (listaTab101 instanceof ListaTab221Type) {
            tab101 = ((ListaTab221Type) listaTab101).addNewTab221();
          } else 
          if (listaTab101 instanceof ListaTab191Type) {
            tab101 = ((ListaTab191Type) listaTab101).addNewTab191();
          } else 
          if (listaTab101 instanceof ListaTab182Type) {
            tab101 = ((ListaTab182Type) listaTab101).addNewTab182();
          } else
          if (listaTab101 instanceof ListaTab292Type) {
            tab101 = ((ListaTab292Type) listaTab101).addNewTab292();
          } else
          if (listaTab101 instanceof ListaTab82Type) {
            tab101 = ((ListaTab82Type) listaTab101).addNewTab82();
          } else if (listaTab101 instanceof Tab5Type) {
            tab101 = ((Tab5Type) listaTab101).addNewTab56();
          }

          int indiceW9incaVector = 0; // indiceW9incaVector=0
          if (verificaEsistenzaValore(w9inca.get(indiceW9incaVector))) {
            Arch2Type arch2 = tab101.addNewArch2();
            valorizzaArch2Type(arch2, w9inca.get(indiceW9incaVector).toString(), sqlManager);
          }
          indiceW9incaVector++; // indiceW9incaVector=1
          if (verificaEsistenzaValore(w9inca.get(indiceW9incaVector))) {
            tab101.setW3CIGPROG(w9inca.get(indiceW9incaVector).toString());
          }
          indiceW9incaVector++; // indiceW9incaVector=2
          if (verificaEsistenzaValore(w9inca.get(indiceW9incaVector))) {
            tab101.setW3DATAAFF(dateString2Calendar(w9inca.get(indiceW9incaVector).toString()));
          }
          indiceW9incaVector++; // indiceW9incaVector=3
          if (verificaEsistenzaValore(w9inca.get(indiceW9incaVector))) {
            tab101.setW3DATACON(dateString2Calendar(w9inca.get(indiceW9incaVector).toString()));
          }
          indiceW9incaVector++; // indiceW9incaVector=4
          if (verificaEsistenzaValore(w9inca.get(indiceW9incaVector))) {
            tab101.setW3IDRUOLO(W3004Type.Enum.forString(w9inca.get(indiceW9incaVector).toString()));
          }
          indiceW9incaVector++; // indiceW9incaVector=5
          if (verificaEsistenzaValore(w9inca.get(indiceW9incaVector))) {
            tab101.setW3SEZIONE(w9inca.get(indiceW9incaVector).toString());
          }
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    } catch (ParseException e) {
      throw new ParseException(e.getMessage(), 0);
    }
  }
  
  /**
   * Esito negativo verifica idoneita' tecnico-professionale.
   * 
   * @param tab11 Tab11Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numtpro Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab11Type(final Tab11Type tab11, final long codGara,
      final long codLotto, final long numtpro, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9TECPRO = "SELECT CODIMP, DESCARE, DATAUNI, " // 0, 1, 2
      + "INIDO1, INIDO2, INIDO3, INIDO4, INIDO5, INIDO6, INIDO7 " // 3, 4, 5, 6, 7, 8, 9
      + "FROM W9TECPRO WHERE CODGARA = ? AND CODLOTT = ? AND NUM_TPRO = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numtpro > 0) {

        List< ? > listaTecpro;
        List< ? > tecpro = new ArrayList<Object>();

        listaTecpro = sqlManager.getListVector(sqlRecuperaW9TECPRO,
            new Object[] { codGara, codLotto, numtpro });

        tecpro = (List< ? >) listaTecpro.get(0);

        int indiceW9tecproVector = 0; //indiceW9tecproVector=0
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          Arch3Type arch3 = tab11.addNewArch3();
          valorizzaArch3Type(arch3, tecpro.get(indiceW9tecproVector).toString(), sqlManager);
        }
        indiceW9tecproVector++; //indiceW9tecproVector=1
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          tab11.setW3TPDESCARE(tecpro.get(indiceW9tecproVector).toString());
        }
        indiceW9tecproVector++; //indiceW9tecproVector=2
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          tab11.setW3TPDATAUNI(dateString2Calendar(tecpro.get(indiceW9tecproVector).toString()));
        }
        indiceW9tecproVector++; //indiceW9tecproVector=3
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          tab11.setW3TPINIDO1(convertiFlag(tecpro.get(indiceW9tecproVector).toString()));
        }
        indiceW9tecproVector++; //indiceW9tecproVector=4
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          tab11.setW3TPINIDO2(convertiFlag(tecpro.get(indiceW9tecproVector).toString()));
        }
        indiceW9tecproVector++; //indiceW9tecproVector=5
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          tab11.setW3TPINIDO3(convertiFlag(tecpro.get(indiceW9tecproVector).toString()));
        }
        indiceW9tecproVector++; //indiceW9tecproVector=6
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          tab11.setW3TPINIDO4(convertiFlag(tecpro.get(indiceW9tecproVector).toString()));
        }
        indiceW9tecproVector++; //indiceW9tecproVector=7
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          tab11.setW3TPINIDO5(convertiFlag(tecpro.get(indiceW9tecproVector).toString()));
        }
        indiceW9tecproVector++; //indiceW9tecproVector=8
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          tab11.setW3TPINIDO6(convertiFlag(tecpro.get(indiceW9tecproVector).toString()));
        }
        indiceW9tecproVector++; //indiceW9tecproVector=9
        if (verificaEsistenzaValore(tecpro.get(indiceW9tecproVector))) {
          tab11.setW3TPINIDO7(tecpro.get(indiceW9tecproVector).toString());
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Esito negativo verifica regolarita' contributiva e assicurativa.
   * 
   * @param tab12 Tab12Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numRego Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab12Type(final Tab12Type tab12, final long codGara,
      final long codLotto, final long numRego, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRrecuperaW9REGCON = "SELECT CODIMP, DESCARE, "
      + "IDDURC, DATADURC, CASSAEDI, RISCONTRO_IRR, DATACOMUN "
      + "FROM W9REGCON WHERE CODGARA = ? AND CODLOTT = ? AND NUM_REGO = ? ";

    Vector< ? > datiW9REGCON = sqlManager.getVector(sqlRrecuperaW9REGCON,
        new Object[] { codGara, codLotto, numRego });

    if (datiW9REGCON != null && datiW9REGCON.size() > 0) {
      int indiceW9regonVector = 0; // indiceW9regonVector=0
      if (verificaEsistenzaValore(datiW9REGCON.get(indiceW9regonVector))) {
        Arch3Type arch3 = tab12.addNewArch3();
        valorizzaArch3Type(arch3, datiW9REGCON.get(indiceW9regonVector).toString(), sqlManager);
      }
      indiceW9regonVector++; // indiceW9regonVector=1
      if (verificaEsistenzaValore(datiW9REGCON.get(indiceW9regonVector))) {
        tab12.setW3RCDESCARE(datiW9REGCON.get(indiceW9regonVector).toString());
      }
      indiceW9regonVector++; // indiceW9regonVector=2
      if (verificaEsistenzaValore(datiW9REGCON.get(indiceW9regonVector))) {
        tab12.setW3RCIDDURC(datiW9REGCON.get(indiceW9regonVector).toString());
      }
      indiceW9regonVector++; // indiceW9regonVector=3
      if (verificaEsistenzaValore(datiW9REGCON.get(indiceW9regonVector))) {
        tab12.setW3RCDATADURC(dateString2Calendar(datiW9REGCON.get(indiceW9regonVector).toString()));
      }
      indiceW9regonVector++; // indiceW9regonVector=4
      if (verificaEsistenzaValore(datiW9REGCON.get(indiceW9regonVector))) {
        tab12.setW3RCCASSAEDI(datiW9REGCON.get(indiceW9regonVector).toString());
      }
      indiceW9regonVector++; // indiceW9regonVector=5
      if (verificaEsistenzaValore(datiW9REGCON.get(indiceW9regonVector))) {
        tab12.setW9RCRISCIRR(W9006Type.Enum.forString(datiW9REGCON.get(indiceW9regonVector).toString()));
      }
      indiceW9regonVector++; // indiceW9regonVector=6
      if (verificaEsistenzaValore(datiW9REGCON.get(indiceW9regonVector))) {
        tab12.setW9RCDATACOMU(dateString2Calendar(datiW9REGCON.get(indiceW9regonVector).toString()));
      }
    }
  }

  /**
   * Misure aggiuntive e migliorative della sicurezza.
   * 
   * @param tab13 Tab13Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numMiss Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab13Type(final Tab13Type tab13, final long codGara,
      final long codLotto, final long numMiss, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9MISSIC = "SELECT DESCMIS, CODIMP " // 0, 1
      + "FROM W9MISSIC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_MISS = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numMiss > 0) {
        List< ? > listaMissic = sqlManager.getListVector(sqlRecuperaW9MISSIC,
            new Object[] { codGara, codLotto, numMiss });
        
        if (listaMissic != null && listaMissic.size() == 1) {
          List< ? > missic = (List< ? >) listaMissic.get(0);
  
          int indiceW9missicVector = 0; // indiceW9missicVector=0
          if (verificaEsistenzaValore(missic.get(indiceW9missicVector))) {
            tab13.setW3MSDESCMIS(missic.get(indiceW9missicVector).toString());
          }
          indiceW9missicVector++; // indiceW9missicVector=1
          if (verificaEsistenzaValore(missic.get(indiceW9missicVector))) {
            Arch3Type arch3 = tab13.addNewArch3();
            valorizzaArch3Type(arch3, missic.get(indiceW9missicVector).toString(), sqlManager);
          }
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Inadempienze disposizioni sicurezza e regolarita' del lavoro.
   * 
   * @param tab15 Tab15Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numInad Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab15Type(final Tab15Type tab15, final long codGara,
      final long codLotto, final long numInad, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9INASIC = "SELECT COMMA3A, COMMA3B, COMMA45A, " // 0, 1, 2
      + "COMMA5, COMMA6, COMMALTRO, " // 3, 4, 5
      + "DESCVIO, DAINASIC, CODIMP " // 6, 7, 8
      + "FROM W9INASIC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INAD = ? ";

    Vector< ? > datiW9INASIC = sqlManager.getVector(sqlRecuperaW9INASIC,
        new Object[] { codGara, codLotto, numInad });

    if (datiW9INASIC != null && datiW9INASIC.size() > 0) {
      int indiceW9inasicVector = 0;
      if (verificaEsistenzaValore(datiW9INASIC.get(indiceW9inasicVector))) {
        tab15.setW3COMMA3A(convertiFlag(datiW9INASIC.get(indiceW9inasicVector).toString()));
      }
      indiceW9inasicVector++; // indiceW9inasicVector=1
      if (verificaEsistenzaValore(datiW9INASIC.get(1))) {
        tab15.setW3COMMA3B(convertiFlag(datiW9INASIC.get(indiceW9inasicVector).toString()));
      }
      indiceW9inasicVector++; // indiceW9inasicVector=2
      if (verificaEsistenzaValore(datiW9INASIC.get(2))) {
        tab15.setW3COMMA45A(convertiFlag(datiW9INASIC.get(indiceW9inasicVector).toString()));
      }
      indiceW9inasicVector++; // indiceW9inasicVector=3
      if (verificaEsistenzaValore(datiW9INASIC.get(indiceW9inasicVector))) {
        tab15.setW3COMMA5(convertiFlag(datiW9INASIC.get(indiceW9inasicVector).toString()));
      }
      indiceW9inasicVector++; // indiceW9inasicVector=4
      if (verificaEsistenzaValore(datiW9INASIC.get(indiceW9inasicVector))) {
        tab15.setW3COMMA6(convertiFlag(datiW9INASIC.get(indiceW9inasicVector).toString()));
      }
      indiceW9inasicVector++; // indiceW9inasicVector=5
      if (verificaEsistenzaValore(datiW9INASIC.get(indiceW9inasicVector))) {
        tab15.setW3COMMALTRO(datiW9INASIC.get(indiceW9inasicVector).toString());
      }
      indiceW9inasicVector++; // indiceW9inasicVector=6
      if (verificaEsistenzaValore(datiW9INASIC.get(indiceW9inasicVector))) {
        tab15.setW3DESIC(datiW9INASIC.get(indiceW9inasicVector).toString());
      }
      indiceW9inasicVector++; // indiceW9inasicVector=7
      if (verificaEsistenzaValore(datiW9INASIC.get(indiceW9inasicVector))) {
        tab15.setW3DAINASIC(dateString2Calendar(datiW9INASIC.get(indiceW9inasicVector).toString()));
      }
      indiceW9inasicVector++; // indiceW9inasicVector=8
      if (verificaEsistenzaValore(datiW9INASIC.get(indiceW9inasicVector))) {
        Arch3Type arch3 = tab15.addNewArch3();
        valorizzaArch3Type(arch3, datiW9INASIC.get(indiceW9inasicVector).toString(), sqlManager);
      }
    }

  }

  /**
   * Scheda segnalazione infortuni.
   * 
   * @param tab16 Tab16Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numInfor Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab16Type(final Tab16Type tab16, final long codGara,
      final long codLotto, final long numInfor, final SqlManager sqlManager)
  throws SQLException, ParseException {
    String sqlRecuperaW9INFOR = "SELECT NINFORT, NGIORNATE, IMORTE, IPERMA, CODIMP " // 0, 1, 2, 3, 4
      + "FROM W9INFOR WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INFOR = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numInfor > 0) {
        List< ? > listaInfor = sqlManager.getListVector(sqlRecuperaW9INFOR,
            new Object[] { codGara, codLotto, numInfor });
        List< ? > infor = (List< ? >) listaInfor.get(0);

        int indiceW9inforVector = 0;; // indiceW9inforVector=0
        if (verificaEsistenzaValore(infor.get(indiceW9inforVector))) {
          tab16.setW3NINFORT(Integer.parseInt(infor.get(indiceW9inforVector).toString()));
        }
        indiceW9inforVector++; // indiceW9inforVector=1
        if (verificaEsistenzaValore(infor.get(indiceW9inforVector))) {
          tab16.setW3NGIORNATE(Integer.parseInt(infor.get(indiceW9inforVector).toString()));
        }
        indiceW9inforVector++; // indiceW9inforVector=2
        if (verificaEsistenzaValore(infor.get(indiceW9inforVector))) {
          tab16.setW3IMORTE(Integer.parseInt(infor.get(indiceW9inforVector).toString()));
        }
        indiceW9inforVector++; // indiceW9inforVector=3
        if (verificaEsistenzaValore(infor.get(indiceW9inforVector))) {
          tab16.setW3IPERMA(Integer.parseInt(infor.get(indiceW9inforVector).toString()));
        }
        indiceW9inforVector++; // indiceW9inforVector=4
        if (verificaEsistenzaValore(infor.get(indiceW9inforVector))) {
          Arch3Type arch3 = tab16.addNewArch3();
          valorizzaArch3Type(arch3, infor.get(indiceW9inforVector).toString(), sqlManager);
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Scheda cantiere e notifiche preliminari.
   * 
   * @param tab17 Tab17Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab17Type(final Tab17Type tab17, final long codGara, final long codLotto,
      final long numCant, final SqlManager sqlManager) throws SQLException, ParseException {
    String sqlRecuperaW9CANT = "SELECT " // CODGARA, CODLOTT, NUM_CANT, "
      + "INDCAN, INFRDA, INFRA, INFRDE, DINIZ, DLAV, " //  0,  1,  2,  3,  4, 5
      + "CIVICO, COMUNE, PROV, COORD_X, COORD_Y, "     //  6,  7,  8,  9, 10
      + "LATIT, LONGIT, NUMLAV, NUMIMP, LAVAUT, "      // 11, 12, 13, 14, 15
      + "TIPOPERA, TIPINTERV, TIPCOSTR, MAILRIC "               // 16, 17, 18, 19
      + "FROM W9CANT WHERE CODGARA = ? AND CODLOTT = ? AND NUM_CANT = ?";

    if (codGara > 0 && codLotto > 0) {
      List< ? > listaCantieri = sqlManager.getListVector(sqlRecuperaW9CANT,
          new Object[] { codGara, codLotto, numCant });
      
      for (int i=0; i < listaCantieri.size(); i++) {
        List< ? > cantiere = (List< ? >) listaCantieri.get(i);

        int indiceW9indcan = 0; // indiceW9indcan=0
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW3INDCAN(cantiere.get(indiceW9indcan).toString());
        }
        indiceW9indcan++; // indiceW9indcan=1
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW3INFRDA(Integer.parseInt(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=2
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW3INFRA(Integer.parseInt(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=3
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW3INFRDE(cantiere.get(indiceW9indcan).toString());
        }
        indiceW9indcan++; // indiceW9indcan=4
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW3DINIZCA(dateString2Calendar(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=5
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW3DLAVCA(Integer.parseInt(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=6
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CACIVICO(cantiere.get(indiceW9indcan).toString());
        }
        indiceW9indcan++; // indiceW9indcan=7
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CACOMUNE(cantiere.get(indiceW9indcan).toString());
        }
        indiceW9indcan++; // indiceW9indcan=8
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CAPROV(cantiere.get(indiceW9indcan).toString());
        }
        indiceW9indcan++; // indiceW9indcan=9
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CACOORDX(Integer.parseInt(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=10
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CACOORDY(Integer.parseInt(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=11
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CALATIT(Double.parseDouble(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=12
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CALONGIT(Double.parseDouble(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=13
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CANUMLAV(Integer.parseInt(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=14
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CANUMIMP(Integer.parseInt(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=15
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CALAVAUT(Integer.parseInt(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=16
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CATIPOPERA(W9009Type.Enum.forString(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=17
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CATIPINTER(W9010Type.Enum.forString(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=18
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW9CATIPCOSTR(W9011Type.Enum.forString(cantiere.get(indiceW9indcan).toString()));
        }
        indiceW9indcan++; // indiceW9indcan=19
        if (verificaEsistenzaValore(cantiere.get(indiceW9indcan))) {
          tab17.setW3MAILRIC(cantiere.get(indiceW9indcan).toString());
        }
      }
    }
  }

  /**
   * Valorizzazione dei destinatari di dentro Scheda cantiere/notifiche preliminari.
   *   
   * @param aperturaCantiereRichiesta RichiestaRichiestaRispostaSincronaAperturaCantiereType
   * @param codGara Codice gara
   * @param codLotto codice Lotto
   * @param numCant numero della scheda
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab171Type(
      RichiestaRichiestaRispostaSincronaAperturaCantiereType aperturaCantiereRichiesta,
      final long codGara, final long codLotto, final long numCant,
      final SqlManager sqlManager) throws SQLException {
    
    String sqlRecuperaW9CANTDEST = "SELECT DEST FROM W9CANTDEST " +
    		"WHERE CODGARA=? AND CODLOTT=? AND NUM_CANT=?";

    List< ? > listaDestinatari = sqlManager.getListVector(sqlRecuperaW9CANTDEST, 
        new Object[]{ codGara, codLotto, numCant });
    
    for (int i=0; i < listaDestinatari.size(); i++) {
      Vector< ? > vectDestinatario = (Vector < ? >) listaDestinatari.get(i);
      Long destinatario = (Long) ((JdbcParametro) vectDestinatario.get(0)).getValue();

      if (destinatario != null) {
        Tab171Type tab171 = aperturaCantiereRichiesta.addNewTab171();
        tab171.setW9DEDEST(W9008Type.Enum.forString(destinatario.toString()));
      }
    }
  }
  
  /**
   * Valorizzazione Imprese e dati DURC per Scheda cantiere e Notifiche preliminari.
   * 
   * @param aperturaCantiereRichiesta
   * @param codGara Codice gara
   * @param codLotto Codice lotto
   * @param numCant numero scheda cantiere
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab172Type(
      RichiestaRichiestaRispostaSincronaAperturaCantiereType aperturaCantiereRichiesta,
      final long codGara, final long codLotto, final long numCant,
      final SqlManager sqlManager) throws SQLException, ParseException {
    
    String sqlRecuperaW9CANTIMP = "SELECT CIPDURC, PROTDURC, DATDURC, CODIMP FROM W9CANTIMP " +
        "WHERE CODGARA=? AND CODLOTT=? AND NUM_CANT=?";

    List< ? > listaImprese = sqlManager.getListVector(sqlRecuperaW9CANTIMP, 
        new Object[]{ codGara, codLotto, numCant });
    
    for (int i=0; i < listaImprese.size(); i++) {
      Vector< ? > vectImpresa = (Vector < ? >) listaImprese.get(i);

      Tab172Type tab172 = aperturaCantiereRichiesta.addNewTab172();
      if (verificaEsistenzaValore(vectImpresa.get(0))) {
        tab172.setW9CICIPDURC(vectImpresa.get(0).toString());
      }
      
      if (verificaEsistenzaValore(vectImpresa.get(1))) {
        tab172.setW9CIPROTDURC(vectImpresa.get(1).toString());
      }

      if (verificaEsistenzaValore(vectImpresa.get(2))) {
        tab172.setW9CIDATDURC(dateString2Calendar(vectImpresa.get(2).toString()));
      }
      
      if (verificaEsistenzaValore(vectImpresa.get(3))) {
        String codimp = vectImpresa.get(3).toString();
        valorizzaArch3Type(tab172.addNewArch3(), codimp, sqlManager);
      }
    }
  }
  
  /**
   * Valorizzazione degli incarichi professionali nella Scheda Cantiere/notifiche preliminari.
   * 
   * @param aperturaCantiereRichiesta
   * @param codGara codice gara
   * @param codLotto codice lotto
   * @param numCant numero scheda cantiere
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaTab173Type(
      RichiestaRichiestaRispostaSincronaAperturaCantiereType aperturaCantiereRichiesta,
      final long codGara, final long codLotto, final long numCant,
      final SqlManager sqlManager) throws SQLException {
   
    String sqlRecuperaW9INCA = "SELECT CODTEC, ID_RUOLO, SEZIONE " // 0, 1, 2
      + " FROM W9INCA WHERE CODGARA = ? AND CODLOTT = ? AND NUM = ? AND SEZIONE = 'NP'";

    List< ? > listaW9inca = sqlManager.getListVector(sqlRecuperaW9INCA,
        new Object[] { codGara, codLotto, numCant });

    if (listaW9inca != null && listaW9inca.size() > 0) {
      for (int i = 0; i < listaW9inca.size(); i++) {
        
        Tab173Type tab173 = aperturaCantiereRichiesta.addNewTab173();
        List< ? > w9inca = (List< ? >) listaW9inca.get(i);

        int indiceW9incaVector = 0; // indiceW9incaVector=0
        if (verificaEsistenzaValore(w9inca.get(indiceW9incaVector))) {
          Arch2Type arch2 = tab173.addNewArch2();
          valorizzaArch2Type(arch2, w9inca.get(indiceW9incaVector).toString(), sqlManager);
        }
        indiceW9incaVector++; // indiceW9incaVector=1
        if (verificaEsistenzaValore(w9inca.get(indiceW9incaVector))) {
          tab173.setW3IDRUOLO(W3004Type.Enum.forString(w9inca.get(indiceW9incaVector).toString()));
        }
        indiceW9incaVector++; // indiceW9incaVector=2
        if (verificaEsistenzaValore(w9inca.get(indiceW9incaVector))) {
          tab173.setW3SEZIONE(w9inca.get(indiceW9incaVector).toString());
        }
      }
    }
  }
  
  /**
   * Fase di aggiudicazione (> 40000).
   * 
   * @param tab18 Tab18Type
   * @param codGara Codice della gara
   * @param codLotto codice del lotto
   * @param numAppa progressivo dell'appalto
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab18Type(final Tab18Type tab18, final long codGara,
      final long codLotto, final long numAppa, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9APPA = "SELECT " //CODGARA, CODLOTT, NUM_APPA, "
      + "COD_STRUMENTO, IMPORTO_LAVORI, IMPORTO_SERVIZI, IMPORTO_FORNITURE," // 0,1,2,3
      + "IMPORTO_SUBTOTALE, IMPORTO_ATTUAZIONE_SICUREZZA, IMPORTO_PROGETTAZIONE,"  // 4,5,6
      + "IMP_NON_ASSOG, IMPORTO_COMPL_APPALTO, IMPORTO_DISPOSIZIONE, IMPORTO_COMPL_INTERVENTO,"  // 7,8,9,10
      + "DURATA_ACCQUADRO, OPERE_URBANIZ_SCOMPUTO, MODALITA_RIAGGIUDICAZIONE,"  // 11,12,13
      + "REQUISITI_SETT_SPEC, FLAG_ACCORDO_QUADRO, PROCEDURA_ACC, PREINFORMAZIONE,"  // 14,15,16,17
      + "TERMINE_RIDOTTO, ID_MODO_INDIZIONE, DATA_MANIF_INTERESSE,"  // 18,19,20
      + "NUM_MANIF_INTERESSE, DATA_SCADENZA_RICHIESTA_INVITO, NUM_IMPRESE_RICHIEDENTI, DATA_INVITO,"  // 21,22,23,24
      + "NUM_IMPRESE_INVITATE, DATA_SCADENZA_PRES_OFFERTA, NUM_IMPRESE_OFFERENTI,"   // 25,26,27
      + "NUM_OFFERTE_AMMESSE, NUM_OFFERTE_ESCLUSE, NUM_OFFERTE_FUORI_SOGLIA, NUM_IMP_ESCL_INSUF_GIUST,"  // 28,29,30,31
      + "OFFERTA_MASSIMO, OFFERTA_MINIMA, VAL_SOGLIA_ANOMALIA, ASTA_ELETTRONICA,"   // 32,33,34,35
      + "PERC_RIBASSO_AGG, PERC_OFF_AUMENTO, IMPORTO_AGGIUDICAZIONE, DATA_VERB_AGGIUDICAZIONE,"  // 36,37,38,39
      + "TIPO_ATTO, DATA_ATTO, NUMERO_ATTO, FLAG_RICH_SUBAPPALTO "  // 40,41,42,43
      + "FROM W9APPA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ? ";
    
    if (codGara > 0 && codLotto > 0 && numAppa > 0) {

      Vector< ? > appa = sqlManager.getVector(sqlRecuperaW9APPA,
          new Object[] { codGara, codLotto, numAppa });
      int indiceW9appaVector = 0; // indiceW9appaVector=0
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3CODSTRU(W3Z01Type.Enum.forString(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=1
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3ILAVORI(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=2
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3ISERVIZ(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=3
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3IFORNIT(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=4
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3ISUBTOT(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=5
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3IATTSIC(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=6
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3IPROGET(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=7
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3INONAS(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=8
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3ICAPPA(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=9
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3IDISPOS(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=10
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3ICINTE(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=11
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW9APDURACCQ(Integer.parseInt(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=12
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW9APOUSCOMP(convertiFlag(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=13
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW9APMODRIAG(W3023Type.Enum.forString(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=14
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3APREQSS(W3009Type.Enum.forString(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=15
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3ACCOQUA(convertiFlag(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=16
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3PROCEDUR(convertiFlag(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=17
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3PREINFOR(convertiFlag(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=18
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3TERMINE(convertiFlag(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=19
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3MODIND(W3008Type.Enum.forString(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=20
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3DATAMAN(dateString2Calendar(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=21
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3NUMMANI(Integer.parseInt(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=22
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3DSCARI(dateString2Calendar(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=23
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3IMPRRIC(Integer.parseInt(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=24
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3DATAINV(dateString2Calendar(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=25
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3IMPRINV(Integer.parseInt(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=26
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3DSCAPO(dateString2Calendar(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=27
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3IMPROFF(Integer.parseInt(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=28
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3IMPRAMM(Integer.parseInt(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=29
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3OFFEESC(Integer.parseInt(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=30
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3OFFEFUO(Integer.parseInt(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=31
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3NUMIMP(Integer.parseInt(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=32
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3OFFEMAX(Float.parseFloat(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=33
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3OFFEMIN(Float.parseFloat(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=34
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3VALSOGL(Float.parseFloat(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=35
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3ASTAELE(convertiFlag(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=36
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3PERCRIB(Float.parseFloat(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=37
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3PERCOFF(Float.parseFloat(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=38
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3IMPAGGI(Double.parseDouble(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=39
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3DVERB(dateString2Calendar(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=40
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW9APTIPAT(W9014Type.Enum.forString(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=41
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW9APDATAT(dateString2Calendar(appa.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=42
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW9APNUMAT(appa.get(indiceW9appaVector).toString());
      }      
      indiceW9appaVector++; // indiceW9appaVector=43
      if (verificaEsistenzaValore(appa.get(indiceW9appaVector))) {
        tab18.setW3FLAGRIC(convertiFlag(appa.get(indiceW9appaVector).toString()));
      }
    }
  }

  /**
   * Fase di aggiudicazione: lista delle imprese aggiudicatarie.
   * 
   * @param listaTab181 ListaTab181Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param numAppa Progressivo dell'applato
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab181Type(final ListaTab181Type listaTab181,
      final long codGara, final long codLott, final long numAppa, final SqlManager sqlManager)
  throws SQLException, ParseException {
    String sqlRecuperaW9AGGI = "SELECT " //CODGARA, CODLOTT, NUM_AGGI, "
      + "FLAG_AVVALIMENTO, ID_TIPOAGG, RUOLO, CODIMP, CODIMP_AUSILIARIA, ID_GRUPPO " // 0, 1, 2, 3, 4, 5
      + "FROM W9AGGI WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ? ";
    Tab81Type tab181;

    List< ? > listaW9aggi;
    List< ? > w9aggi = new ArrayList<Object>();

    if (codGara > 0 && codLott > 0 && numAppa > 0) {
      listaW9aggi = sqlManager.getListVector(sqlRecuperaW9AGGI, new Object[] {
          codGara, codLott, numAppa });

      for (int i = 0; i < listaW9aggi.size(); i++) {
        w9aggi = (List< ? >) listaW9aggi.get(i);
        tab181 = listaTab181.addNewTab181();

        int indiceW9aggiVector = 0; // indiceW9aggiVector=0
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          tab181.setW3FLAGAVV(W3Z09Type.Enum.forString(w9aggi.get(indiceW9aggiVector).toString()));
        }
        indiceW9aggiVector++; // indiceW9aggiVector=1
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          tab181.setW3IDTIPOA(W3010Type.Enum.forString(w9aggi.get(indiceW9aggiVector).toString()));
        }
        indiceW9aggiVector++; // indiceW9aggiVector=2
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          tab181.setW3RUOLO(W3011Type.Enum.forString(w9aggi.get(indiceW9aggiVector).toString()));
        }
        indiceW9aggiVector++; // indiceW9aggiVector=3
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          Arch3Type arch3 = tab181.addNewArch3();
          valorizzaArch3Type(arch3, w9aggi.get(indiceW9aggiVector).toString(), sqlManager);
        }
        indiceW9aggiVector++; // indiceW9aggiVector=4
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          Arch3Type arch3 = tab181.addNewArch3Avv();
          valorizzaArch3Type(arch3, w9aggi.get(indiceW9aggiVector).toString(), sqlManager);
        }
        indiceW9aggiVector++; // indiceW9aggiVector=5
        if (verificaEsistenzaValore(w9aggi.get(indiceW9aggiVector))) {
          tab181.setW3AGIDGRP(new Long(w9aggi.get(indiceW9aggiVector).toString()));
        }
      }
    }
  }

  /**
   * Fase di aggiudicazione: lista dei tecnici e dei soggetti incaricati.
   * 
   * @param listaTab182 ListaTab182Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab182Type(final ListaTab182Type listaTab182,
      final long codGara, final long codLott, final long num, final SqlManager sqlManager)
  throws SQLException, ParseException {
    valorizzaListaTab101Type(listaTab182, codGara, codLott, num,
        " AND SEZIONE in ('RA', 'PA') ", sqlManager);
  }

  /**
   * Fase di aggiudicazione: lista dei finanziamenti.
   * 
   * @param listaTab183 ListaTab183Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param numAppa Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaListaTab183Type(final ListaTab183Type listaTab183,
      final long codGara, final long codLott, final long numAppa, final SqlManager sqlManager)
  throws SQLException {
    String sqlRecuperaW9FINA = "SELECT " //CODGARA, CODLOTT, NUM_FINA, "
      + "ID_FINANZIAMENTO, IMPORTO_FINANZIAMENTO "  // 0, 1
      + "FROM W9FINA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ? ";
    Tab183Type tab183;

    List< ? > listaW9fina;
    List< ? > w9fina = new ArrayList<Object>();

    if (codGara > 0 && codLott > 0 && numAppa > 0) {
      listaW9fina = sqlManager.getListVector(sqlRecuperaW9FINA,
          new Object[] { codGara, codLott, numAppa });

      for (int i = 0; i < listaW9fina.size(); i++) {
        w9fina = (List< ? >) listaW9fina.get(i);

        tab183 = listaTab183.addNewTab183();
        int indiceW9finaVector = 0; // indiceW9finaVector=0
        if (verificaEsistenzaValore(w9fina.get(indiceW9finaVector))) {
          tab183.setW3IDFINAN(W3Z02Type.Enum.forString(w9fina.get(indiceW9finaVector).toString()));
        }
        indiceW9finaVector++; // indiceW9finaVector=1
        if (verificaEsistenzaValore(w9fina.get(indiceW9finaVector))) {
          tab183.setW3IFINANZ(Double.parseDouble(w9fina.get(indiceW9finaVector).toString()));
        }
      }
    }
  }

  /**
   * Anagrafica gara/lotto: dati di pubblicita'.
   * 
   * @param anagraficaRichiesta RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraType
   * @param codGara Codice della gara
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab184Type(final
      RichiestaRichiestaRispostaSincronaAnagraficaLottoGaraType anagraficaRichiesta,
      final long codGara, final SqlManager sqlManager) throws SQLException, ParseException {
    String sqlRecuperaW9PUBB = "SELECT " //CODGARA, CODLOTT, NUM_APPA, "
      + "DATA_ALBO, DATA_BORE, DATA_GUCE, DATA_GURI, SITO_MINISTERO_INF_TRASP, " // 0, 1, 2, 3, 4
      + "QUOTIDIANI_NAZ, SITO_OSSERVATORIO_CP, PERIODICI, PROFILO_COMMITTENTE, QUOTIDIANI_REG " // 5, 6, 7, 8, 9
      + "FROM W9PUBB WHERE CODGARA = ? AND CODLOTT = 1 AND NUM_APPA = 1 AND NUM_PUBB = 1 ";

    if (codGara > 0) {
      List< ? > listaPubb = sqlManager.getListVector(sqlRecuperaW9PUBB, new Object[] { codGara });
      if (listaPubb.size() > 0) {
        Tab184Type tab184 = anagraficaRichiesta.addNewTab184();

        List< ? > pubb = (List< ? >) listaPubb.get(0);
        int indiceW9pubbVector = 0; // indiceW9pubbVector=0
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3ALBO1(dateString2Calendar(pubb.get(indiceW9pubbVector).toString()));
        }
        indiceW9pubbVector++; // indiceW9pubbVector=1
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3BORE(dateString2Calendar(pubb.get(indiceW9pubbVector).toString()));
        }
        indiceW9pubbVector++; // indiceW9pubbVector=2
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3GUCE1(dateString2Calendar(pubb.get(indiceW9pubbVector).toString()));
        }
        indiceW9pubbVector++; // indiceW9pubbVector=3
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3GURI1(dateString2Calendar(pubb.get(indiceW9pubbVector).toString()));
        }
        indiceW9pubbVector++; // indiceW9pubbVector=4
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3MIN1(convertiFlag(pubb.get(indiceW9pubbVector).toString()));
        }
        indiceW9pubbVector++; // indiceW9pubbVector=5
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3NAZ1(Integer.parseInt(pubb.get(indiceW9pubbVector).toString()));
        }
        indiceW9pubbVector++; // indiceW9pubbVector=6
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3OSS1(convertiFlag(pubb.get(indiceW9pubbVector).toString()));
        }
        indiceW9pubbVector++; // indiceW9pubbVector=7
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3PERIODIC(Integer.parseInt(pubb.get(indiceW9pubbVector).toString()));
        }
        indiceW9pubbVector++; // indiceW9pubbVector=8
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3PROFILO1(convertiFlag(pubb.get(indiceW9pubbVector).toString()));
        }
        indiceW9pubbVector++; // indiceW9pubbVector=9
        if (verificaEsistenzaValore(pubb.get(indiceW9pubbVector))) {
          tab184.setW3REG1(Integer.parseInt(pubb.get(indiceW9pubbVector).toString()));
        }
      }
    }
  }

  /**
   * Fase iniziale di inizio contratto.
   * 
   * @param tab19 Tab19Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param numIniz Progressiso
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab19Type(final Tab19Type tab19, final long codGara,
      final long codLott, final long numIniz, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9INIZ = "SELECT "  // CODGARA, CODLOTT, NUM_INIZ,
      + "DATA_APP_PROG_ESEC, DATA_ESECUTIVITA, DATA_STIPULA, DATA_INI_PROG_ESEC, DATA_TERMINE, " // 0, 1, 2, 3, 4
      + "DATA_VERBALE_CONS, DATA_VERBALE_DEF, DATA_VERB_INIZIO, FLAG_FRAZIONATA, " // 5, 6, 7, 8
      + "IMPORTO_CAUZIONE, FLAG_RISERVA, INCONTRI_VIGIL " // 9, 10, 11
      + "FROM W9INIZ WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ? ";

    Vector< ? > datiW9INIZ = sqlManager.getVector(sqlRecuperaW9INIZ,
        new Object[] { codGara, codLott, numIniz });
    if (datiW9INIZ != null && datiW9INIZ.size() > 0) {
      int indiceW9inizVector = 0; // indiceW9inizVector=0
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3DATAAPP(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=1
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3DATAESE(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=2
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3DATASTI(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=3
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3DPROGES(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=4
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3DTERM1(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=5
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3DVERBCO(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=6
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3DVERBDE(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=7
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3DVERBIN(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=8
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3FLAGFRA(convertiFlag(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=9
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3ICAUZIO(Double.parseDouble(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=10
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW3RISERVA(convertiFlag(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=11
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab19.setW9ININCVIGIL(W9005Type.Enum.forString(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
    }
  }

  /**
   * Fase iniziale: lista degli incaricati.
   * 
   * @param listaTab191 ListaTab191Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab191Type(final ListaTab191Type listaTab191,
      final long codGara, final long codLott, final long num, final SqlManager sqlManager)
  throws SQLException, ParseException {
    valorizzaListaTab101Type(listaTab191, codGara, codLott, num,
        " AND SEZIONE = 'IN'", sqlManager);
  }

  /**
   * Fase iniziale: dati di pubblicita' dell'esito.
   * 
   * @param richiestaInizio6 RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param numIniz Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab192Type(final RichiestaRichiestaRispostaSincronaInizioContrattoSopra150000Type richiestaInizio6,
      final long codGara, final long codLott, final long numIniz, final SqlManager sqlManager)
  throws SQLException, ParseException {
    String sqlRecuperaW9PUES = "SELECT " //CODGARA, CODLOTT, NUM_INIZ, "
      + "DATA_ALBO, DATA_GUCE, DATA_GURI, SITO_MINISTERO_INF_TRASP, QUOTIDIANI_NAZ, " // 0, 1, 2, 3, 4
      + "SITO_OSSERVATORIO_CP, PROFILO_COMMITTENTE, QUOTIDIANI_REG " // 5, 6, 7
      + "FROM W9PUES WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ? AND NUM_PUES = 1 ";

    List< ? > listaW9pues;
    if (codGara > 0 && codLott > 0 && numIniz > 0) {
      listaW9pues = sqlManager.getListVector(sqlRecuperaW9PUES,
          new Object[] { codGara, codLott, numIniz });

      if (listaW9pues.size() > 0) {
        List<?> w9pues = (List< ? >) listaW9pues.get(0);
        Tab192Type tab192 = richiestaInizio6.addNewTab192();
        int indiceW9puesVector = 0; // indiceW9puesVector=0
        
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192.setW3ALBO2(dateString2Calendar(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=1
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192.setW3GUCE2(dateString2Calendar(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=2
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192.setW3GURI2(dateString2Calendar(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=3
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192.setW3MIN2(convertiFlag(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=4
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192.setW3NAZ2(Integer.parseInt(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=5
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192.setW3OSS2(convertiFlag(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=6
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192.setW3PROFILO2(convertiFlag(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=7
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192.setW3REG2(Integer.parseInt(w9pues.get(indiceW9puesVector).toString()));
        }
      }
    }
  }

  /**
   * Fase Comunicazione esito: dati di pubblicita' dell'esito.
   * Metodo copia del metodo valorizzaTab192Type
   * 
   * @param richiestaComunicEsito RichiestaRichiestaRispostaSincronaComunicazioneEsitoType
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param numIniz Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab192BisType(final RichiestaRichiestaRispostaSincronaComunicazioneEsitoType richiestaComunicEsito,
      final long codGara, final long codLott, final long numIniz, final SqlManager sqlManager)
  throws SQLException, ParseException {
    
    String sqlRecuperaW9PUES = "SELECT " //CODGARA, CODLOTT, NUM_INIZ, "
      + "DATA_ALBO, DATA_GUCE, DATA_GURI, SITO_MINISTERO_INF_TRASP, QUOTIDIANI_NAZ, " // 0, 1, 2, 3, 4
      + "SITO_OSSERVATORIO_CP, PROFILO_COMMITTENTE, QUOTIDIANI_REG " // 5, 6, 7
      + "FROM W9PUES WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ? AND NUM_PUES = 1 ";

    List< ? > listaW9pues;

    if (codGara > 0 && codLott > 0 && numIniz > 0) {
      listaW9pues = sqlManager.getListVector(sqlRecuperaW9PUES,
          new Object[] { codGara, codLott, numIniz });

      if (listaW9pues.size() > 0) {
        Tab192BisType tab192bis = richiestaComunicEsito.addNewTab34();
        List<?> w9pues = (List< ? >) listaW9pues.get(0);
        int indiceW9puesVector = 0; // indiceW9puesVector=0
        
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192bis.setW3ALBO2(dateString2Calendar(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=1
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192bis.setW3GUCE2(dateString2Calendar(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=2
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192bis.setW3GURI2(dateString2Calendar(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=3
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192bis.setW3MIN2(convertiFlag(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=4
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192bis.setW3NAZ2(Integer.parseInt(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=5
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192bis.setW3OSS2(convertiFlag(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=6
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192bis.setW3PROFILO2(convertiFlag(w9pues.get(indiceW9puesVector).toString()));
        }
        indiceW9puesVector++; // indiceW9puesVector=7
        if (verificaEsistenzaValore(w9pues.get(indiceW9puesVector))) {
          tab192bis.setW3REG2(Integer.parseInt(w9pues.get(indiceW9puesVector).toString()));
        }

      }
    }
  }
  
  /**
   * Fase di avanzamento del contratto.
   * 
   * @param tab20 Tab20Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numAvan Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab20Type(final Tab20Type tab20, final long codGara,
      final long codLotto, final long numAvan, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9AVAN = "SELECT " //CODGARA, CODLOTT, NUM_AVAN, NUM_AVAN, "
      + "DATA_ANTICIPAZIONE, DATA_CERTIFICATO, DATA_RAGGIUNGIMENTO, FLAG_PAGAMENTO, FLAG_RITARDO, " // 0, 1, 2, 3, 4
      + "IMPORTO_ANTICIPAZIONE, IMPORTO_CERTIFICATO, IMPORTO_SAL, NUM_GIORNI_SCOST, " // 5, 6, 7, 8
      + "NUM_GIORNI_PROROGA, DENOM_AVANZAMENTO " // 9, 10
      + "FROM W9AVAN WHERE CODGARA = ? AND CODLOTT = ? AND NUM_AVAN = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numAvan > 0) {
        List< ? > listaAvan = sqlManager.getListVector(sqlRecuperaW9AVAN, new Object[] { codGara, codLotto, numAvan });
        List< ? > avan = (List< ? >) listaAvan.get(0);

        int indiceW9avanVector = 0; // indiceW9avanVector=0
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3DATAANT(dateString2Calendar(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=1
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3DATACER(dateString2Calendar(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=2
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3DATARAG(dateString2Calendar(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=3
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3FLAGPAG(W3012Type.Enum.forString(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=4
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3FLAGRIT(W3Z04Type.Enum.forString(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=5
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3IANTICI(Double.parseDouble(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=6
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3ICERTIF(Double.parseDouble(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=7
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3ISAL(Double.parseDouble(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=8
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3NUMGIO1(Integer.parseInt(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=9
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3NUMGIO2(Integer.parseInt(avan.get(indiceW9avanVector).toString()));
        }

        // L.G. 18/03/2011: aggiunto campo per adeguamento SIMOG 3.02
        indiceW9avanVector++; // indiceW9avanVector=10
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab20.setW3AVANDENO(avan.get(indiceW9avanVector).toString());
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Fase di conclusione del contratto.
   * 
   * @param tab21 Tab21Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numConc Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab21Type(final Tab21Type tab21, final long codGara,
      final long codLotto, final long numConc, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9CONC = "SELECT " //CODGARA, CODLOTT, NUM_CONC, "
      +	"INTANTIC, DATA_RISOLUZIONE, DATA_ULTIMAZIONE, FLAG_ONERI, " // 0, 1, 2, 3
      + "FLAG_POLIZZA, ID_MOTIVO_INTERR, ID_MOTIVO_RISOL, ONERI_RISOLUZIONE, " // 4, 5, 6, 7
      + "NUM_INFORTUNI, NUM_INF_MORT, NUM_INF_PERM, NUM_GIORNI_PROROGA, " // 8, 9, 10, 11
      + "DATA_VERBALE_CONSEGNA, TERMINE_CONTRATTO_ULT, ORE_LAVORATE " // 12, 13, 14
      + "FROM W9CONC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_CONC = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numConc > 0) {
        List< ? > listaConc = sqlManager.getListVector(sqlRecuperaW9CONC, new Object[] {
            codGara, codLotto, numConc });
        List< ? > conc = (List< ? >) listaConc.get(0);

        int indiceW9conc = 0; // indiceW9conc=0
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3CSINTANTIC(convertiFlag(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=1
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3DATARIS(dateString2Calendar(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=2
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3DATAULT(dateString2Calendar(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=3
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3FLAGONE(W3Z06Type.Enum.forString(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=4
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3FLAGPOL(convertiFlag(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=5
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3IDMOTI1(Integer.parseInt(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=6
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3IDMOTI2(Integer.parseInt(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=7
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3ONERIRI(Double.parseDouble(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=8
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3NUMINFO(Integer.parseInt(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=9
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3NUMMORT(Integer.parseInt(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=10
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW3NUMPERM(Integer.parseInt(conc.get(indiceW9conc).toString()));
        }

        // L.G. 18/03/2011: aggiunti campi per adeguamento SIMOG 3.02
        indiceW9conc++; // indiceW9conc=11
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          int temp = Integer.parseInt(conc.get(indiceW9conc).toString());
          if (temp > 0) {
            tab21.setW9COGGPROR(temp);
          }
        }
        indiceW9conc++; // indiceW9conc=12
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW9CODVERCON(dateString2Calendar(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=13
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW9COTERCONT(dateString2Calendar(conc.get(indiceW9conc).toString()));
        }
        indiceW9conc++; // indiceW9conc=14
        if (verificaEsistenzaValore(conc.get(indiceW9conc))) {
          tab21.setW9COORELAVO(Double.parseDouble(conc.get(indiceW9conc).toString()));
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Fase di collaudo del contratto.
   * 
   * @param tab22 Tab22Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numColl Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab22Type(final Tab22Type tab22, final long codGara,
      final long codLotto, final long numColl, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9COLL = "SELECT " //CODGARA, CODLOTT, NUM_COLL, "
      + "AMM_NUM_DADEF, AMM_NUM_DEFINITE, AMM_IMPORTO_DEF, AMM_IMPORTO_RICH, ARB_NUM_DADEF, " // 0, 1, 2,3, 4
      + "ARB_NUM_DEFINITE, ARB_IMPORTO_DEF, ARB_IMPORTO_RICH, DATA_COLLAUDO_STAT, DATA_DELIBERA, " // 5, 6, 7, 8, 9
      + "DATA_NOMINA_COLL, DATA_REGOLARE_ESEC, DATA_CERT_COLLAUDO, DATA_INIZIO_OPER, " // 10, 11, 12, 13
      + "ESITO_COLLAUDO, GIU_NUM_DADEF, GIU_NUM_DEFINITE, GIU_IMPORTO_DEF, GIU_IMPORTO_RICH, " // 14, 15, 16, 17, 18
      + "IMP_FINALE_FORNIT, IMP_FINALE_LAVORI, IMP_FINALE_SECUR, IMP_FINALE_SERVIZI, " // 19, 20, 21, 22
      + "IMP_COMPL_APPALTO, IMP_COMPL_INTERVENTO, IMP_DISPOSIZIONE, IMP_PROGETTAZIONE, " // 23, 24, 25, 26
      + "IMP_SUBTOTALE, MODO_COLLAUDO, TRA_NUM_DADEF, TRA_NUM_DEFINITE, TRA_IMPORTO_DEF," // 27, 28, 29, 30, 31
      + "TRA_IMPORTO_RICH, LAVORI_ESTESI " // 32, 33
      + "FROM W9COLL WHERE CODGARA = ? AND CODLOTT = ? AND NUM_COLL = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numColl > 0) {
        List< ? > listaColl = sqlManager.getListVector(sqlRecuperaW9COLL, new Object[] {
            codGara, codLotto, numColl });
        List< ? > coll = (List< ? >) listaColl.get(0);

        int indiceW9collVector = 0; // indiceW9collVector=0
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3AMMDADE(Integer.parseInt(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=1
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3AMMDEF(Integer.parseInt(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=2
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3AMMIDEF(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=3
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3AMMIRIC(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=4
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3ARBDADE(Integer.parseInt(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=5
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3ARBDEF(Integer.parseInt(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=6
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3ARBIDEF(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=7
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3ARBIRIC(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=8
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3DATACOL(dateString2Calendar(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=9
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3DATADEL(dateString2Calendar(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=10
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3DATANOM(dateString2Calendar(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=11
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3DATAREG(dateString2Calendar(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=12
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3DCERTCO(dateString2Calendar(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=13
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3DINIZOP(dateString2Calendar(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=14
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3ESITOCO(W3Z10Type.Enum.forString(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=15
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3GIUDADE(Integer.parseInt(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=16
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3GIUDEF(Integer.parseInt(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=17
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3GIUIDEF(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=18
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3GIUIRIC(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=19
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3IFFORNI(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=20
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3IFLAVOR(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=21
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3IFSECUR(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=22
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3IFSERVI(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=23
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3IMPCOMA2(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=24
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3IMPCOMI2(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=25
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3IMPDIS2(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=26
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3IMPPROG(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=27
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3IMPSUBT2(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=28
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3MODOCOL(W3015Type.Enum.forString(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=29
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3TRADADE(Integer.parseInt(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=30
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3TRADEF(Integer.parseInt(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=31
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3TRAIDEF(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        indiceW9collVector++; // indiceW9collVector=32
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3TRAIRIC(Double.parseDouble(coll.get(indiceW9collVector).toString()));
        }
        // L.G. 18/03/2011: aggiunto campo per adeguamento SIMOG 3.02
        indiceW9collVector++; // indiceW9collVector=33
        if (verificaEsistenzaValore(coll.get(indiceW9collVector))) {
          tab22.setW3COLLLEST(convertiFlag(coll.get(indiceW9collVector).toString()));
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Fase di collaudo del contratto: lista degli incaricati.
   * 
   * @param listaTab221 ListaTab221Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab221Type(final ListaTab221Type listaTab221,
      final long codGara, final long codLott, final long num, final SqlManager sqlManager)
  throws SQLException, ParseException {

    valorizzaListaTab101Type(listaTab221, codGara, codLott, num,
        " AND SEZIONE = 'CO' ", sqlManager);
  }

  /**
   * Fase di sospensione.
   * 
   * @param tab23 Tab23Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numSosp Progressivo sospensione
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab23Type(final Tab23Type tab23, final long codGara,
      final long codLotto, final long numSosp, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9SOSP = "SELECT CODGARA, CODLOTT, NUM_SOSP, DATA_VERB_RIPR,"
      + "DATA_VERB_SOSP, FLAG_SUPERO_TEMPO, FLAG_VERBALE, ID_MOTIVO_SOSP, FLAG_RISERVE "
      + "FROM W9SOSP WHERE CODGARA = ? AND CODLOTT = ? AND NUM_SOSP = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numSosp > 0) {
        List< ? > listaSosp;
        List< ? > sosp = new ArrayList<Object>();
        listaSosp = sqlManager.getListVector(sqlRecuperaW9SOSP, new Object[] {
            codGara, codLotto, numSosp });
        sosp = (List< ? >) listaSosp.get(0);

        int indiceW9sospVector = 2;
        if (verificaEsistenzaValore(sosp.get(indiceW9sospVector))) {
          tab23.setW3NUM21(Long.parseLong(sosp.get(indiceW9sospVector).toString()));
        }
        indiceW9sospVector++; // indiceW9sospVector=3
        if (verificaEsistenzaValore(sosp.get(indiceW9sospVector))) {
          tab23.setW3DVERBRI(dateString2Calendar(sosp.get(indiceW9sospVector).toString()));
        }
        indiceW9sospVector++; // indiceW9sospVector=4
        if (verificaEsistenzaValore(sosp.get(indiceW9sospVector))) {
          tab23.setW3DVERBSO(dateString2Calendar(sosp.get(indiceW9sospVector).toString()));
        }
        indiceW9sospVector++; // indiceW9sospVector=5
        if (verificaEsistenzaValore(sosp.get(indiceW9sospVector))) {
          tab23.setW3FLAGSUP(convertiFlag(sosp.get(indiceW9sospVector).toString()));
        }
        indiceW9sospVector++; // indiceW9sospVector=6
        if (verificaEsistenzaValore(sosp.get(indiceW9sospVector))) {
          tab23.setW3FLAGVER(convertiFlag(sosp.get(indiceW9sospVector).toString()));
        }
        indiceW9sospVector++; // indiceW9sospVector=7
        if (verificaEsistenzaValore(sosp.get(indiceW9sospVector))) {
          tab23.setW3IDMOTI4(W3016Type.Enum.forString(sosp.get(indiceW9sospVector).toString()));
        }
        indiceW9sospVector++; // indiceW9sospVector=8
        if (verificaEsistenzaValore(sosp.get(indiceW9sospVector))) {
          tab23.setW3RISERVE(convertiFlag(sosp.get(indiceW9sospVector).toString()));
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Variante del contratto.
   * 
   * @param tab24 Tab24Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numVari Progressivo variante
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab24Type(final Tab24Type tab24, final long codGara,
      final long codLotto, final long numVari, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9VARI = "SELECT " // CODGARA, CODLOTT, NUM_VARI, "
      + "ALTRE_MOTIVAZIONI, DATA_ATTO_AGGIUNTIVO, DATA_VERB_APPR, IMP_COMPL_APPALTO, " // 0, 1, 2, 3
      + "IMP_COMPL_INTERVENTO, IMP_DISPOSIZIONE, IMP_PROGETTAZIONE, IMP_SICUREZZA, " // 4, 5, 6, 7
      + "IMP_SUBTOTALE, IMP_RIDET_FORNIT, IMP_RIDET_LAVORI, IMP_RIDET_SERVIZI, NUM_GIORNI_PROROGA " // 8, 9, 10, 11, 12
      + "FROM W9VARI WHERE CODGARA = ? AND CODLOTT = ? AND NUM_VARI = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numVari > 0) {
        List< ? > listaVari = sqlManager.getListVector(sqlRecuperaW9VARI, new Object[] {
            codGara, codLotto, numVari });
        List< ? > vari = (List< ? >) listaVari.get(0);

        int indiceW9variVector = 0; // indiceW9variVector=0
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3ALTREMO(vari.get(indiceW9variVector).toString());
        }
        indiceW9variVector++; // indiceW9variVector=1
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3DATAATT(dateString2Calendar(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=2
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3DVERBAP(dateString2Calendar(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=3
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3IMPCOMAP(Double.parseDouble(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=4
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3IMPCOMIN(Double.parseDouble(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=5
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3IMPDIS1(Double.parseDouble(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=6
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3IMPPRO1(Double.parseDouble(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=7
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3IMPSICU(Double.parseDouble(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=8
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3IMPSUBTO(Double.parseDouble(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=9
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3IRFORNI(Double.parseDouble(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=10
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3IRLAVOR(Double.parseDouble(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=11
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3IRSERVI(Double.parseDouble(vari.get(indiceW9variVector).toString()));
        }
        indiceW9variVector++; // indiceW9variVector=12
        if (verificaEsistenzaValore(vari.get(indiceW9variVector))) {
          tab24.setW3NUMGIO3(Integer.parseInt(vari.get(indiceW9variVector).toString()));
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Variante del contratto: motivazioni.
   * 
   * @param listaTab241 ListaTab241Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param numVari Progressivo variante
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   */
  private static void valorizzaListaTab241Type(final ListaTab241Type listaTab241,
      final long codGara, final long codLott, final long numVari,
      final SqlManager sqlManager) throws SQLException {
    String sqlRecuperaW9MOTI = "SELECT ID_MOTIVO_VAR " // CODGARA, CODLOTT, NUM_MOTI,
      + "FROM W9MOTI WHERE CODGARA = ? AND CODLOTT = ? AND NUM_VARI = ? ";
    // String sqlRecuperaTIPO_CONTRATTO = "SELECT TIPO_CONTRATTO WHERE CODGARA = ? AND CODLOTT = ? ";

    try {
      if (codGara > 0 && codLott > 0 && numVari > 0) {
        List< ? > listaW9moti = sqlManager.getListVector(sqlRecuperaW9MOTI,
            new Object[] { codGara, codLott, numVari });
        // String tipoContratto = (String) sqlManager.getObject(sqlRecuperaTIPO_CONTRATTO,
        // new Object[] { codGara, codLott });

        if (listaW9moti.size() > 0) {
          for (int i = 0; i < listaW9moti.size(); i++) {
            List< ? > w9moti = (List< ? >) listaW9moti.get(i);
            Tab241Type tab241 = listaTab241.addNewTab241();
            if (verificaEsistenzaValore(w9moti.get(0))) {
              // Manca la gestione dell'Enumerato W3018 per Forniture/Servizi
              // if (tipoContratto.equals(W3Z05Type.L.toString()))
              tab241.setW3IDMOTI3(Integer.parseInt(w9moti.get(0).toString()));
              // else
              // tab241.setW3IDMOTI3(W3018Type.Enum.forString(w9moti.get(3).toString()));
            }
          }
        } /*
         * else { tab241 = listaTab241.addNewTab241(); }
         */
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Accordi bonari.
   * 
   * @param tab25 Tab25Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numAcco Progressivo accordo bonario
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab25Type(final Tab25Type tab25, final long codGara, final long codLotto,
      final long numAcco, final SqlManager sqlManager) throws SQLException, ParseException {
    String sqlRecuperaW9ACCO = "SELECT " // CODGARA, CODLOTT, NUM_ACCO, "
      + "DATA_ACCORDO, NUM_RISERVE, ONERI_DERIVANTI " // 0, 1, 2,
      + "FROM W9ACCO WHERE CODGARA = ? AND CODLOTT = ? AND NUM_ACCO = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numAcco > 0) {
        List< ? > listaAcco = sqlManager.getListVector(sqlRecuperaW9ACCO, new Object[] {
            codGara, codLotto, numAcco });
        List< ? > acco = (List< ? >) listaAcco.get(0);

        int indiceW9accoVector = 0; // indiceW9accoVector=0
        if (verificaEsistenzaValore(acco.get(indiceW9accoVector))) {
          tab25.setW3DATAACC(dateString2Calendar(acco.get(indiceW9accoVector).toString()));
        }
        indiceW9accoVector++; // indiceW9accoVector=1
        if (verificaEsistenzaValore(acco.get(indiceW9accoVector))) {
          tab25.setW3NUMRISE(Integer.parseInt(acco.get(indiceW9accoVector).toString()));
        }
        indiceW9accoVector++; // indiceW9accoVector=2
        if (verificaEsistenzaValore(acco.get(indiceW9accoVector))) {
          tab25.setW3ONERIDE(Double.parseDouble(acco.get(indiceW9accoVector).toString()));
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Subappalti.
   * 
   * @param tab26 Tab26Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numSuba Progressivo subappalto
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab26Type(final Tab26Type tab26, final long codGara,
      final long codLotto, final long numSuba, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9SUBA = "SELECT " //CODGARA, CODLOTT, NUM_SUBA,
      + "CODIMP, DATA_AUTORIZZAZIONE, ID_CATEGORIA, ID_CPV, " // 0, 1, 2, 3
      + "IMPORTO_EFFETTIVO, IMPORTO_PRESUNTO, OGGETTO_SUBAPPALTO, CODIMP_AGGIUDICATRICE  " // 4, 5, 6, 7
      + "FROM W9SUBA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_SUBA = ? ";

    if (codGara > 0 && codLotto > 0 && numSuba > 0) {
      List< ? > listaSuba = sqlManager.getListVector(sqlRecuperaW9SUBA, new Object[] {
          codGara, codLotto, numSuba });
      List< ? > suba = (List< ? >) listaSuba.get(0);

      int indiceW9subaVector = 0; // indiceW9subaVector=0
      if (verificaEsistenzaValore(suba.get(indiceW9subaVector))) {
        Arch3Type arch3 = tab26.addNewArch3();
        valorizzaArch3Type(arch3, suba.get(indiceW9subaVector).toString(), sqlManager);
      }
      indiceW9subaVector++; // indiceW9subaVector=1
      if (verificaEsistenzaValore(suba.get(indiceW9subaVector))) {
        tab26.setW3DATAAUT(dateString2Calendar(suba.get(indiceW9subaVector).toString()));
      }
      indiceW9subaVector++; // indiceW9subaVector=2
      if (verificaEsistenzaValore(suba.get(indiceW9subaVector))) {
        tab26.setW3IDCATE2(W3Z03Type.Enum.forString(suba.get(indiceW9subaVector).toString()));
      }
      indiceW9subaVector++; // indiceW9subaVector=3
      if (verificaEsistenzaValore(suba.get(indiceW9subaVector))) {
        tab26.setW3IDCPV(suba.get(indiceW9subaVector).toString());
      }
      indiceW9subaVector++; // indiceW9subaVector=4
      if (verificaEsistenzaValore(suba.get(indiceW9subaVector))) {
        tab26.setW3IEFFETT(Double.parseDouble(suba.get(indiceW9subaVector).toString()));
      }
      indiceW9subaVector++; // indiceW9subaVector=5
      if (verificaEsistenzaValore(suba.get(indiceW9subaVector))) {
        tab26.setW3IPRESUN(Double.parseDouble(suba.get(indiceW9subaVector).toString()));
      }
      indiceW9subaVector++; // indiceW9subaVector=6
      if (verificaEsistenzaValore(suba.get(indiceW9subaVector))) {
        tab26.setW3OSUBA(suba.get(indiceW9subaVector).toString());
      }
      indiceW9subaVector++; // indiceW9subaVector=7
      if (verificaEsistenzaValore(suba.get(indiceW9subaVector))) {
        Arch3Type arch3Impagg = tab26.addNewArch3Impagg();
        valorizzaArch3Type(arch3Impagg, suba.get(indiceW9subaVector).toString(), sqlManager);
      }
    }
  }

  /**
   * Istanza di recesso.
   * 
   * @param tab27 Tab27Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numRita Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab27Type(final Tab27Type tab27, final long codGara,
      final long codLotto, final long numRita, final SqlManager sqlManager) throws SQLException,
      ParseException {
    String sqlRecuperaW9RITA = "SELECT " // CODGARA, CODLOTT, NUM_RITA, "
      +	"FLAG_ACCOLTA, DATA_IST_RECESSO, DURATA_SOSP, " // 0, 1, 2
      + "FLAG_RIPRESA, FLAG_TARDIVA, IMPORTO_ONERI, IMPORTO_SPESE, MOTIVO_SOSP, " // 3, 4, 5, 6, 7
      + "FLAG_RISERVA, RITARDO, TIPO_COMUN, DATA_TERMINE, DATA_CONSEGNA " // 8, 9, 10, 11, 12
      + "FROM W9RITA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_RITA = ? ";

    try {
      if (codGara > 0 && codLotto > 0 && numRita > 0) {
        List< ? > listaRita = sqlManager.getListVector(sqlRecuperaW9RITA, new Object[] {
            codGara, codLotto, numRita });
        List< ? > rita = (List< ? >) listaRita.get(0);

        int indiceW9ritaVector = 0; // indiceW9ritaVector=0
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3ACCOLTA(convertiFlag(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=1
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3DATAIST(dateString2Calendar(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=2
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3DURATAS(Integer.parseInt(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=3
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3FLAGRIP(convertiFlag(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=4
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3FLAGTAR(convertiFlag(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=5
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3IONERI(Double.parseDouble(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=6
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3ISPESE(Double.parseDouble(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=7
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3MOTIVOS(rita.get(indiceW9ritaVector).toString());
        }
        indiceW9ritaVector++; // indiceW9ritaVector=8
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3RISER(convertiFlag(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=9
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3RITARDO(Integer.parseInt(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=10
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3TIPOCOM(W3Z07Type.Enum.forString(rita.get(indiceW9ritaVector).toString()));
        }
        // L.G. 18/03/2011: aggiunti campi per adeguamento SIMOG 3.02
        indiceW9ritaVector++; // indiceW9ritaVector=11
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3DTERM2(dateString2Calendar(rita.get(indiceW9ritaVector).toString()));
        }
        indiceW9ritaVector++; // indiceW9ritaVector=12
        if (verificaEsistenzaValore(rita.get(indiceW9ritaVector))) {
          tab27.setW3DCONS(dateString2Calendar(rita.get(indiceW9ritaVector).toString()));
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }

  /**
   * Fase di stipula accordo quadro.
   * 
   * @param tab28 Tab28Type
   * @param codGara Codice della gara
   * @param codLotto Codice del lotto
   * @param numIniz Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab28(final Tab28Type tab28, final long codGara, final long codLotto,
      final long numIniz, final SqlManager sqlManager) throws SQLException, ParseException {

    String sqlRecuperaW9INIZ = "SELECT " //CODGARA, CODLOTT, NUM_INIZ, "
      +	"DATA_STIPULA, DATA_DECORRENZA, DATA_SCADENZA " // 0, 1, 2
      + "FROM W9INIZ WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ? ";

    Vector< ? > datiW9INIZ = sqlManager.getVector(sqlRecuperaW9INIZ,
        new Object[] { codGara, codLotto, numIniz });

    if (datiW9INIZ != null && datiW9INIZ.size() > 0) {
      int indiceW9inizVector = 0; // indiceW9inizVector=0
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab28.setW3DATASTI(dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString()));
      }
      indiceW9inizVector++; // indiceW9inizVector=1
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab28.setW9INDECO((dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString())));
      }
      indiceW9inizVector++; // indiceW9inizVector=2
      if (verificaEsistenzaValore(datiW9INIZ.get(indiceW9inizVector))) {
        tab28.setW9INSCAD((dateString2Calendar(datiW9INIZ.get(indiceW9inizVector).toString())));
      }
    }
  }

  /**
   * Fase di stipula dell'accordo quadro: dati di pubblicazione esito.
   * 
   * @param tab281 Tab281type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab281(final Tab281Type tab281, final long codGara, final long codLott,
      final long num, final SqlManager sqlManager) throws SQLException, ParseException {

    String sqlRecuperaW9PUES = "SELECT " //CODGARA, CODLOTT, NUM_INIZ, "
      + "DATA_GUCE, DATA_GURI, SITO_MINISTERO_INF_TRASP, QUOTIDIANI_NAZ, " // 0, 1, 2, 3
      + "SITO_OSSERVATORIO_CP, PROFILO_COMMITTENTE, QUOTIDIANI_REG " // 4, 5, 6
      + "FROM W9PUES WHERE CODGARA=? AND CODLOTT=? AND NUM_INIZ=? AND NUM_PUES=1 ";

    Vector< ? > datiW9PUES = sqlManager.getVector(sqlRecuperaW9PUES,
        new Object[] { codGara, codLott, num });

    if (datiW9PUES != null && datiW9PUES.size() > 0) {
      int indiceW9puesVector = 0; // indiceW9puesVector=0
      if (verificaEsistenzaValore(datiW9PUES.get(indiceW9puesVector))) {
        tab281.setW3GUCE2(dateString2Calendar(datiW9PUES.get(indiceW9puesVector).toString()));
      }
      indiceW9puesVector++; // indiceW9puesVector=1
      if (verificaEsistenzaValore(datiW9PUES.get(indiceW9puesVector))) {
        tab281.setW3GURI2(dateString2Calendar(datiW9PUES.get(indiceW9puesVector).toString()));
      }
      indiceW9puesVector++; // indiceW9puesVector=2
      if (verificaEsistenzaValore(datiW9PUES.get(indiceW9puesVector))) {
        tab281.setW3MIN2(convertiFlag(datiW9PUES.get(indiceW9puesVector).toString()));
      }
      indiceW9puesVector++; // indiceW9puesVector=3
      if (verificaEsistenzaValore(datiW9PUES.get(indiceW9puesVector))) {
        tab281.setW3NAZ2(Integer.parseInt(datiW9PUES.get(indiceW9puesVector).toString()));
      }
      indiceW9puesVector++; // indiceW9puesVector=4
      if (verificaEsistenzaValore(datiW9PUES.get(indiceW9puesVector))) {
        tab281.setW3OSS2(convertiFlag(datiW9PUES.get(indiceW9puesVector).toString()));
      }
      indiceW9puesVector++; // indiceW9puesVector=5
      if (verificaEsistenzaValore(datiW9PUES.get(indiceW9puesVector))) {
        tab281.setW3PROFILO2(convertiFlag(datiW9PUES.get(indiceW9puesVector).toString()));
      }
      indiceW9puesVector++; // indiceW9puesVector=6
      if (verificaEsistenzaValore(datiW9PUES.get(indiceW9puesVector))) {
        tab281.setW3REG2(Integer.parseInt(datiW9PUES.get(indiceW9puesVector).toString()));
      }
    }
  }

  /**
   * Fase di adesione ad accordo quadro.
   * 
   * @param tab29 Tab29Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaTab29Type(final Tab29Type tab29, final long codGara, final long codLott,
      final long num, final SqlManager sqlManager) throws SQLException, ParseException {
    String sqlRecuperaW9APPA = "select IMPORTO_FORNITURE, " // 0
      + " DATA_VERB_AGGIUDICAZIONE, " // 1
      + " IMPORTO_AGGIUDICAZIONE, " // 2
      + " IMPORTO_LAVORI, " // 3
      + " IMPORTO_SERVIZI, " // 4
      + " PERC_RIBASSO_AGG, " // 5
      + " PERC_OFF_AUMENTO, " // 6
      + " FLAG_RICH_SUBAPPALTO, " // 7
      + " COD_STRUMENTO, " // 8
      + " IMPORTO_SUBTOTALE, " // 9
      + " DATA_ATTO, NUMERO_ATTO, TIPO_ATTO, CODCUI "  // 10, 11, 12, 13
      + " from W9APPA where CODGARA=? and CODLOTT=? and NUM_APPA=?";

    Vector< ? > datiW9APPA = sqlManager.getVector(sqlRecuperaW9APPA,
        new Object[] { codGara, codLott, num });

    if (datiW9APPA != null && datiW9APPA.size() > 0) {
      int indiceW9appaVector = 0;
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3IFORNIT(((Double) ((JdbcParametro) datiW9APPA.get(indiceW9appaVector)).getValue()).doubleValue());
      }
      indiceW9appaVector++; // indiceW9appaVector=1
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3DVERB(dateString2Calendar(datiW9APPA.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=2
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3IMPAGGI(((Double) ((JdbcParametro) datiW9APPA.get(indiceW9appaVector)).getValue()).doubleValue());
      }
      indiceW9appaVector++; // indiceW9appaVector=3
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3ILAVORI(((Double) ((JdbcParametro) datiW9APPA.get(indiceW9appaVector)).getValue()).doubleValue());
      }
      indiceW9appaVector++; // indiceW9appaVector=4
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3ISERVIZ(((Double) ((JdbcParametro) datiW9APPA.get(indiceW9appaVector)).getValue()).doubleValue());
      }
      indiceW9appaVector++; // indiceW9appaVector=5
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3PERCRIB(((Double) ((JdbcParametro) datiW9APPA.get(indiceW9appaVector)).getValue()).floatValue());
      }
      indiceW9appaVector++; // indiceW9appaVector=6
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3PERCOFF(((Double) ((JdbcParametro) datiW9APPA.get(indiceW9appaVector)).getValue()).floatValue());
      }
      indiceW9appaVector++; // indiceW9appaVector=7
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3FLAGRIC(convertiFlag(datiW9APPA.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=8
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3CODSTRU(W3Z01Type.Enum.forString(datiW9APPA.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; // indiceW9appaVector=9
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector).toString())) {
        tab29.setW3ISUBTOT(((Double) ((JdbcParametro) datiW9APPA.get(indiceW9appaVector)).getValue()).doubleValue());
      }
      indiceW9appaVector++; //indiceW9appaVector=10
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector))) {
        tab29.setW9APDATAT(dateString2Calendar(datiW9APPA.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=11
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector))) {
        tab29.setW9APNUMAT(datiW9APPA.get(indiceW9appaVector).toString());
      }
      indiceW9appaVector++; //indiceW9appaVector=12
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector))) {
        tab29.setW9APTIPAT(W9014Type.Enum.forString(datiW9APPA.get(indiceW9appaVector).toString()));
      }
      indiceW9appaVector++; //indiceW9appaVector=13
      if (verificaEsistenzaValore(datiW9APPA.get(indiceW9appaVector))) {
        tab29.setW9APPACODCUI(datiW9APPA.get(indiceW9appaVector).toString());
      }
    }
  }

  /**
   * Adesione accordo quadro: lista degli aggiudicatari.
   * 
   * @param listaTab291 ListaTab291Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param num Progressivo
   * @param impl DataColumnContainer
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab291Type(final ListaTab291Type listaTab291, final long codGara,
      final long codLott, final long num, final SqlManager sqlManager)
          throws SQLException, ParseException {
    String sqlRecuperaW9AGGI = "SELECT " // CODGARA, CODLOTT, NUM_AGGI, "
      + "FLAG_AVVALIMENTO, ID_TIPOAGG, RUOLO, CODIMP, CODIMP_AUSILIARIA, ID_GRUPPO " // 0, 1, 2, 3, 4, 5
      + "FROM W9AGGI WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ? ";

    List< ? > datiW9AGGI = sqlManager.getListVector(sqlRecuperaW9AGGI,
        new Object[] { codGara, codLott, num });

    if (datiW9AGGI != null && datiW9AGGI.size() > 0) {
      for (int i = 0; i < datiW9AGGI.size(); i++) {
        List< ? > rigaW9AGGI = (List< ? >) datiW9AGGI.get(i);
        Tab81Type tab291 = listaTab291.addNewTab291();

        int indiceW9aggiVector = 0; // indiceW9aggiVector=0
        if (verificaEsistenzaValore(rigaW9AGGI.get(indiceW9aggiVector))) {
          tab291.setW3FLAGAVV(W3Z09Type.Enum.forString(rigaW9AGGI.get(indiceW9aggiVector).toString()));
        }
        indiceW9aggiVector++; // indiceW9aggiVector=1
        if (verificaEsistenzaValore(rigaW9AGGI.get(indiceW9aggiVector))) {
          tab291.setW3IDTIPOA(W3010Type.Enum.forString(rigaW9AGGI.get(indiceW9aggiVector).toString()));
        }
        indiceW9aggiVector++; // indiceW9aggiVector=2
        if (verificaEsistenzaValore(rigaW9AGGI.get(indiceW9aggiVector))) {
          tab291.setW3RUOLO(W3011Type.Enum.forString(rigaW9AGGI.get(indiceW9aggiVector).toString()));
        }
        indiceW9aggiVector++; // indiceW9aggiVector=3
        if (verificaEsistenzaValore(rigaW9AGGI.get(indiceW9aggiVector))) {
          Arch3Type arch3 = tab291.addNewArch3();
          valorizzaArch3Type(arch3, rigaW9AGGI.get(indiceW9aggiVector).toString(), sqlManager);
        }
        indiceW9aggiVector++; // indiceW9aggiVector=4
        if (verificaEsistenzaValore(rigaW9AGGI.get(indiceW9aggiVector))) {
          Arch3Type arch3 = tab291.addNewArch3Avv();
          valorizzaArch3Type(arch3, rigaW9AGGI.get(indiceW9aggiVector).toString(), sqlManager);
        }
        indiceW9aggiVector++; // indiceW9aggiVector=5
        if (verificaEsistenzaValore(rigaW9AGGI.get(indiceW9aggiVector))) {
          tab291.setW3AGIDGRP(new Long(rigaW9AGGI.get(indiceW9aggiVector).toString()));
        }
      }
    }
  }

  /**
   * Adesione accordo quadro: lista degli incaricati.
   * 
   * @param listaTab292 ListaTab292Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param num Progressivo fase
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab292Type(final ListaTab292Type listaTab292, final long codGara,
      final long codLott, final long num, final SqlManager sqlManager) throws SQLException, ParseException {

    valorizzaListaTab101Type(listaTab292, codGara, codLott, num,
        " AND SEZIONE = 'RQ' ", sqlManager);
  }

  /**
   * Adesione ad accordo quadro: lista dei finanziamenti.
   * 
   * @param listaTab293 ListaTab293Type
   * @param codGara Codice della gara
   * @param codLott Codice del lotto
   * @param num Progressivo
   * @param sqlManager SqlManager
   * @throws SQLException SQLException
   * @throws ParseException ParseException
   */
  private static void valorizzaListaTab293Type(final ListaTab293Type listaTab293, final long codGara,
      final long codLott, final long num, final SqlManager sqlManager) throws SQLException, ParseException {

    String sqlRecuperaW9FINA = "SELECT " //CODGARA, CODLOTT, NUM_FINA, "
      + "ID_FINANZIAMENTO, IMPORTO_FINANZIAMENTO " // 0, 1
      + "FROM W9FINA WHERE CODGARA = ? AND CODLOTT = ? AND NUM_APPA = ? ";

    List< ? > datiW9FINA = sqlManager.getListVector(sqlRecuperaW9FINA,
        new Object[] { codGara, codLott, num });
    if (datiW9FINA != null && datiW9FINA.size() > 0) {
      for (int i = 0; i < datiW9FINA.size(); i++) {
        List< ? > rigaW9FINA = (List< ? >) datiW9FINA.get(i);
        Tab293Type tab293 = listaTab293.addNewTab293();

        int indiceW9finaVector = 0; // indiceW9finaVector=0
        if (verificaEsistenzaValore(rigaW9FINA.get(indiceW9finaVector))) {
          tab293.setW3IDFINAN(W3Z02Type.Enum.forString(rigaW9FINA.get(indiceW9finaVector).toString()));
        }
        indiceW9finaVector++; // indiceW9finaVector=1
        if (verificaEsistenzaValore(rigaW9FINA.get(indiceW9finaVector))) {
          tab293.setW3IFINANZ(Double.parseDouble(rigaW9FINA.get(indiceW9finaVector).toString()));
        }
      }
    }
  }

  private static void valorizzaElencoImpreseInvitate(final RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiType richiestaElencoImpreseInvitatePartecipanti,
      final long codGara, final long codLott, final SqlManager sqlManager) throws SQLException, ParseException {
    
    String recuperaW9IMPRESE = "SELECT PARTECIP, TIPOAGG, NUM_RAGG, RUOLO, CODIMP from W9IMPRESE " +
    		"where CODGARA=? and CODLOTT=? order by TIPOAGG asc, NUM_IMPR asc, NUM_RAGG asc";

    List < ? > datiW9IMPRESE = sqlManager.getListVector(recuperaW9IMPRESE, new Object[] { codGara, codLott });
    
    if (datiW9IMPRESE != null && datiW9IMPRESE.size() > 0) {
      for (int i=0; i < datiW9IMPRESE.size(); i++) {
        Vector < ? > w9Impresa = (Vector< ? >) datiW9IMPRESE.get(i);
        
        Tab32Type tab32 = richiestaElencoImpreseInvitatePartecipanti.addNewTab32();
        tab32.setW9IMPARTEC(W9015Type.Enum.forString(w9Impresa.get(0).toString()));
        tab32.setW9IMTIPOA(W3010Type.Enum.forString(w9Impresa.get(1).toString()));
        
        Long numRagg = (Long) ((JdbcParametro) w9Impresa.get(2)).getValue();
        
        if (numRagg != null) {
          // Impresa di un gruppo
          tab32.setW9IMRAGGR(numRagg.intValue());
          if (tab32.getW9IMTIPOA().equals(W3010Type.Enum.forInt(1))) {
            // Il ruolo si setta solo per le ATI
            tab32.setW9IMRUOLO(W3011Type.Enum.forString((w9Impresa.get(3).toString())));
          }
        } else {
          // Impresa singola
        }

        Arch3Type arch3 = tab32.addNewArch3();
        valorizzaArch3Type(arch3, w9Impresa.get(4).toString(), sqlManager);
      }
    }
  }
  
  private static void valorizzaTab33Type(Tab33Type tab3, long codGara, long codLotto, long numAvan, SqlManager sqlManager)
    throws SQLException, ParseException {
    
    String sqlRecuperaW9AVAN = "SELECT " //CODGARA, CODLOTT, NUM_AVAN, NUM_AVAN, "
      + "DATA_CERTIFICATO, IMPORTO_CERTIFICATO "
      //+	", DATA_ANTICIPAZIONE, DATA_RAGGIUNGIMENTO, FLAG_PAGAMENTO, FLAG_RITARDO, " // 0, 1, 2, 3, 4
      //+ "IMPORTO_ANTICIPAZIONE, IMPORTO_CERTIFICATO, IMPORTO_SAL, NUM_GIORNI_SCOST, " // 5, 6, 7, 8
      //+ "NUM_GIORNI_PROROGA, DENOM_AVANZAMENTO " // 9, 10
      + "FROM W9AVAN WHERE CODGARA = ? AND CODLOTT = ? AND NUM_AVAN = ? ";

    if (codGara > 0 && codLotto > 0 && numAvan > 0) {
      List< ? > listaAvan = sqlManager.getListVector(sqlRecuperaW9AVAN,
          new Object[] { codGara, codLotto, numAvan });
      
      if (listaAvan != null && listaAvan.size() == 1) {
        List< ? > avan = (List< ? >) listaAvan.get(0);
  
        int indiceW9avanVector = 0; // indiceW9avanVector=0
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab3.setW3DATACER(dateString2Calendar(avan.get(indiceW9avanVector).toString()));
        }
        indiceW9avanVector++; // indiceW9avanVector=1
        if (verificaEsistenzaValore(avan.get(indiceW9avanVector))) {
          tab3.setW3ICERTIF(Double.parseDouble(avan.get(indiceW9avanVector).toString()));
        }
      }
    }
    
  }
  
 
  /**
   * Metodo per cast in calendar delle date.
   * 
   * @param data String
   * @return Ritorna la data in un oggetto Calendar a partire dalla stringa nel formato gg/mm/aaaa
   * @throws ParseException ParseException
   */
  public static Calendar dateString2Calendar(final String data) throws ParseException {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date parsed = df.parse(data);

    Calendar newCalendar = new GregorianCalendar();
    newCalendar.setTime(parsed);

    return newCalendar;
  }

  /**
   * Utility di conversione flag S/N.
   * 
   * @param valore String
   * @return Ritorna FlagSNType.S se valore = '1' o 'S' o 'SI', altrimenti FlagSNType.N
   */
  private static FlagSNType.Enum convertiFlag(final String valore) {
    if (valore != null
        && (valore.trim().toUpperCase().equals("S")
            || valore.trim().toUpperCase().equals("SI") || valore.trim().toUpperCase().equals(
            "1"))) {
      return FlagSNType.S;
    } else {
      return FlagSNType.N;
    }
  }

  /**
   * Utility per il controllo dei valori in arrivo.
   * 
   * @param obj Object
   * @return Ritorna true se obj e' diversa da null, false altrimenti
   */
  private static boolean verificaEsistenzaValore(final Object obj) {
    boolean esistenza;
    if (obj != null && !obj.toString().trim().equals("")) {
      esistenza = true;
    } else {
      esistenza = false;
    }
    return esistenza;
  }

}
