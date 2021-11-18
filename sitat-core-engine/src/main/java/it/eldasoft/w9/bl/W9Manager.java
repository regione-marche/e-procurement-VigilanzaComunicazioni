package it.eldasoft.w9.bl;

import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.DittaAusiliariaType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.BlobFile;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityWeb;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.SituazioneGaraLotto;
import it.eldasoft.w9.dao.W9FileDao;
import it.eldasoft.w9.dao.vo.rest.PubblicaAttoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicaAvvisoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicaGaraEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.dm112011.PubblicaProgrammaDM112011Entry;
import it.eldasoft.w9.dao.vo.rest.programmi.fornitureservizi.PubblicaProgrammaFornitureServiziEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.lavori.PubblicaProgrammaLavoriEntry;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

public class W9Manager {

  static Logger      logger = Logger.getLogger(W9Manager.class);

  /** Manager per le transazioni e selezioni nel database. */
  private SqlManager sqlManager;
  
  /** Manager per operazioni sql di tipo generale. */
  private GeneManager geneManager;
  
  /** Manager per la generazioni di nuove chiavi */
  private GenChiaviManager genChiaviManager;
  
  /** DAO per la gestione dei file allegati. */
  private W9FileDao  w9FileDao;

  /**
   * Set sqlManager.
   * 
   * @param sqlManager
   *        sqlManager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   * Set geneManager.
   * 
   * @param geneManager
   *        geneManager da settare internamente alla classe.
   */
  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }
  
  /**
   * Set genChiaviManager.
   * 
   * @param genChiaviManager
   *        genChiaviManager da settare internamente alla classe.
   */
  public void setGenChiaviManager(GenChiaviManager genChiaviManager) {
    this.genChiaviManager = genChiaviManager;
  }
  
  /**
   * Set fileDao.
   * 
   * @param fileDao
   *        w9FileDao da settare internamente alla classe.
   */
  public void setW9FileDao(W9FileDao fileDao) {
    this.w9FileDao = fileDao;
  }
  
  private Long sommaLotti(Long codgara) throws GestoreException {
    String sqlSelectLotti = "select COUNT(*) from W9LOTT where CODGARA = ? ";
    Long numLotti;
    Long somma;
    try {
      numLotti = (Long) this.sqlManager.getObject(sqlSelectLotti, new Object[]{codgara});
      somma = numLotti;
    } catch (Exception e) {
      somma = new Long(0);
      throw new GestoreException(
          "Errore durante il calcolo dei Lotti della Gara",
          "calcolo.sommaLotti", e);
    }
    return somma;
  }

  /**
   * Aggiornamento del CIG del lotto.
   * 
   * @param codgara Codice della gara
   * @throws GestoreException GestoreException
   */
  public void aggiornaCig(Long codgara) throws GestoreException {
    // aggiorna il CIG dei lotti se == null o vuoto.
    TransactionStatus status = null;
    String cigLotti = "select cig, codlott from w9lott where codgara = ?";
    String updateCigLotti = "UPDATE W9LOTT SET CIG =? WHERE CODGARA=? AND CODLOTT=?";
    try {
      List< ? > listaCig = sqlManager.getListVector(cigLotti, new Object[] { codgara } );

      if (listaCig.size() > 0) {
        for (int i = 0; i < listaCig.size(); i++) {

          List< ? > elemento = (List< ? >) listaCig.get(i);
          String par = (elemento.get(0).toString());
          Integer codlott = new Integer(elemento.get(1).toString());

          if (par.toString() == null || par.toString().trim().equals("")) {
            status = sqlManager.startTransaction();
            this.sqlManager.update(updateCigLotti, new Object[] { "In Attesa",
                codgara, codlott });
            this.sqlManager.commitTransaction(status);
          }
        }
      }
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nel\'esecuzione della query per l'aggiornamento dei CIG dei Lotti",
          "aggiornamento.ricalcoloCigLotti", e);
    }
  }

  public void aggiornaNumLotti(Long codgara) throws GestoreException {
    String updateNumLotti = "UPDATE W9GARA SET NLOTTI =? WHERE CODGARA=?";

    try {
      TransactionStatus status = this.sqlManager.startTransaction();
      Long totaliLotti = sommaLotti(codgara);
      this.sqlManager.update(updateNumLotti, new Object[] { totaliLotti, codgara });
      this.sqlManager.commitTransaction(status);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nel\'esecuzione della query per l'aggiornamento dei numeri dei Lotti",
          "aggiornamento.ricalcoloNumeroLotti", e);
    }
  }

  public void cancellazioneLott(String tabella, Long codgara, Long codlott) throws GestoreException {
    String queryEsistenza = "delete from "
      + tabella
      + " where codgara =? and codlott =?";
    try {
      TransactionStatus status = this.sqlManager.startTransaction();
      this.sqlManager.update(queryEsistenza, new Object[] { codgara, codlott });
      this.sqlManager.commitTransaction(status);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nel\'esecuzione della query per la cancellazione dei dati di un Lotto",
          "cancellazione.lotto", e);
    }
  }

  public void cancellazioneMoti(Long codgara, Long codlott, Long numvari) throws GestoreException {
    String queryEsistenza = "delete from w9moti"
      + " where codgara =? and codlott =? and num_vari =?";
    try {
      TransactionStatus status = this.sqlManager.startTransaction();
      this.sqlManager.update(queryEsistenza, new Object[] { codgara, codlott,
          numvari });
      this.sqlManager.commitTransaction(status);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nel\'esecuzione della query per la cancellazione dei dati di un motivo",
          "cancellazione.variazione", e);
    }
  }

  public BlobFile getFileAllegato(String entita, HashMap<String, Object> params) throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("getFileAllegato: inizio metodo");
    }
    BlobFile w4Blob = this.w9FileDao.getFileAllegato(entita, params);
    if (logger.isDebugEnabled()) {
      logger.debug("getFileAllegato: fine metodo");
    }
    return w4Blob;
  }

  public List< ? > getListaDocumentiBando(Long codiceGara) throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("getListaFileAllegati: inizio metodo");
    }
    List< ? > listaFileBlob = this.w9FileDao.getDocumentiBando(codiceGara);

    if (logger.isDebugEnabled()) {
      logger.debug("getListaFileAllegati: fine metodo");
    }
    return listaFileBlob;
  }

  public void downloadFileAllegato(String entita, HashMap<String, Object> params, 
      HttpServletResponse response) throws IOException, GestoreException {
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm");
    String strDateTime = sdf.format(new Date());
    if (logger.isDebugEnabled()) {
      logger.debug("downloadFileAllegato" + entita + ": inizio metodo");
    }
    if (entita.equals("W9FLUSSI")) {
      String clob = this.getClobXml(entita, params.get("idFlusso").toString());
        String codflusso = this.getCodFlusso(entita,params.get("idFlusso").toString());
        if (StringUtils.isNotEmpty(codflusso)) {
        UtilityWeb.download("flusso_" + codflusso + "_" + strDateTime + ".xml",
            clob.getBytes(), response);
      } else {
        UtilityWeb.download("datiLotto_" + "_" + strDateTime + ".xml",
            clob.getBytes(), response);
      }
    } else if (entita.equals("W9FLUSSI_ELIMINATI")) {
    	String clob = this.getClobXml(entita, params.get("idFlusso").toString());
        String codflusso = this.getCodFlusso(entita, params.get("idFlusso").toString());
        UtilityWeb.download("flusso_" + codflusso + "_" + strDateTime + ".xml",
            clob.getBytes(), response);
    } else if (entita.equals("W9INBOX")) {
      String clob = this.getClobXml(entita, params.get("idcomun").toString());
      UtilityWeb.download("flusso_" + strDateTime + ".xml",
          clob.getBytes(), response);
    } else if (entita.equals("W9XML")) {
      String clob = this.getClobXml(entita, (Long) params.get("codGara"), 
          (Long) params.get("codLotto"), (Long) params.get("numXml"));
      UtilityWeb.download("file_" + strDateTime + ".xml",
          clob.getBytes(), response);
    } else if (entita.equals("W9OUTBOX")) {
        String clob = this.getClobXml(entita, params.get("idcomun").toString());
        UtilityWeb.download("flusso_" + strDateTime + ".json",
            clob.getBytes(), response);
    } else {
      BlobFile w4Blob = this.w9FileDao.getFileAllegato(entita, params);
      UtilityWeb.download("allegato_" + strDateTime + ".pdf",
          w4Blob.getStream(), response);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("downloadFileAllegato" + entita + ": fine metodo");
    }
  }

  private String getClobXml(String entita, String idFlusso) throws GestoreException {

    String sqlSelectLotti = "select XML from W9FLUSSI where IDFLUSSO = ";
    if ("W9INBOX".equalsIgnoreCase(entita)) {
      sqlSelectLotti = "select XML from W9INBOX where IDCOMUN = ";
    } else if ("W9OUTBOX".equalsIgnoreCase(entita)) {
      sqlSelectLotti = "select FILE_JSON from W9OUTBOX where IDCOMUN = ";
    } else if ("W9FLUSSI_ELIMINATI".equalsIgnoreCase(entita)) {
      sqlSelectLotti = "select XML from W9FLUSSI_ELIMINATI where IDFLUSSO = ";
    } else if ("W_COOPUSR".equalsIgnoreCase(entita)) {
      sqlSelectLotti = "select FILE_ALLEGATO from W_COOPUSR where ID_RICHIESTA = ";
    }
    String xml = "";
    try {
      xml = (String) this.sqlManager.getObject(sqlSelectLotti + idFlusso, null);
    } catch (Exception e) {
      throw new GestoreException(
          "Errore durante il recupero del file XML allegato.", "select.xml", e);
    }
    return xml;
  }

  private String getClobXml(String entita, Long codgara, Long codlott, Long num) throws GestoreException {
    String sqlSelect = null;
    Object[] sqlParam = null;
    
    if ("W9XML".equalsIgnoreCase(entita)) {
      sqlSelect = "select XML from W9XML where CODGARA=? and CODLOTT=? and NUMXML=?";
      sqlParam = new Object[] {codgara, codlott, num};
    }
    String xml = "";
    try {
      xml = (String) this.sqlManager.getObject(sqlSelect, sqlParam);
    } catch (Exception e) {
      throw new GestoreException(
          "Errore durante il recupero del file XML allegato.", "select.xml", e);
    }
    return xml;
    
  }
  
  public void updateClobXml(Long idFlusso, String xml) throws GestoreException {
    String updateValoreXml = "UPDATE W9FLUSSI SET XML =? WHERE IDFLUSSO =?";

    try {
      TransactionStatus status = this.sqlManager.startTransaction();
      this.sqlManager.update(updateValoreXml, new Object[] { xml, idFlusso });
      this.sqlManager.commitTransaction(status);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nel\'esecuzione della query per l'aggiornamento del clob xml",
          "", e);
    }
  }

  public void deleteFlussoInvalidoClobXml(Long idFlusso) throws GestoreException {
    String deleteFlusso = "DELETE FROM W9FLUSSI WHERE IDFLUSSO =?";

    try {
      TransactionStatus status = this.sqlManager.startTransaction();
      this.sqlManager.update(deleteFlusso, new Object[] { idFlusso });
      this.sqlManager.commitTransaction(status);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nell\'esecuzione della query per fa cancellazione del flusso",
          "", e);
    }
  }

  public void updateFlussoAvviso(final PubblicaAvvisoEntry avviso, final Long idAvviso, final String codein) throws GestoreException {

	    String updateIdRicevuto = "UPDATE AVVISO SET ID_RICEVUTO = ? WHERE CODEIN = ? AND IDAVVISO = ? AND CODSISTEMA = ?";
	    
	    TransactionStatus status = null;
	    boolean commitTransaction = false;
	    try {
	    	status = this.sqlManager.startTransaction();
	      	this.sqlManager.update(updateIdRicevuto, new Object[] {avviso.getIdRicevuto(), codein, idAvviso, new Long(1)});
	      	commitTransaction = true;
	    } catch (Exception e) {
	      throw new GestoreException(
	          "errore durante l'archiviazione della gara",
	          "archiviagara.error", e);
	    } finally {
	        if (status != null) {
	          try {
	            if (commitTransaction) {
	            	this.sqlManager.commitTransaction(status);
	            } else {
	            	this.sqlManager.rollbackTransaction(status);
	            }
	          } catch (SQLException e) {
	            logger.error("Errore durante la chiusura della transazione", e);
	          }
	        }
	      }
	    }
  
  public void updateFlussoExArt29(final PubblicaAttoEntry pubblicazione, final Long codGara, final Long numeroPubblicazione) throws GestoreException {

	  String updateIdRicevutoPubblicazioni = "UPDATE W9PUBBLICAZIONI SET ID_RICEVUTO = ? WHERE CODGARA = ? AND NUM_PUBB = ?";
	  String updateIdRicevutoGara = "UPDATE W9GARA SET ID_RICEVUTO = ? WHERE CODGARA = ?";
	  TransactionStatus status = null;
	    boolean commitTransaction = false;
	    try {
	    	status = this.sqlManager.startTransaction();
	    	
	      	this.sqlManager.update(updateIdRicevutoPubblicazioni, new Object[] {pubblicazione.getIdRicevuto(), codGara, numeroPubblicazione});
	      	this.sqlManager.update(updateIdRicevutoGara, new Object[] {pubblicazione.getGara().getIdRicevuto(), codGara});
	      	commitTransaction = true;
	    } catch (Exception e) {
	      throw new GestoreException(
	          "errore durante l'archiviazione della gara",
	          "archiviagara.error", e);
	    } finally {
	        if (status != null) {
	          try {
	            if (commitTransaction) {
	            	this.sqlManager.commitTransaction(status);
	            } else {
	            	this.sqlManager.rollbackTransaction(status);
	            }
	          } catch (SQLException e) {
	            logger.error("Errore durante la chiusura della transazione", e);
	          }
	        }
	      }
	    }
  
  public void updateFlussoAnagraficaGaraLotti(final PubblicaGaraEntry gara, final Long codGara) throws GestoreException {

	  String updateIdRicevutoGara = "UPDATE W9GARA SET ID_RICEVUTO = ? WHERE CODGARA = ?";
	  TransactionStatus status = null;
	    boolean commitTransaction = false;
	    try {
	    	status = this.sqlManager.startTransaction();
	    	
	      	this.sqlManager.update(updateIdRicevutoGara, new Object[] {gara.getIdRicevuto(), codGara});
	      	commitTransaction = true;
	    } catch (Exception e) {
	      throw new GestoreException(
	          "errore durante l'archiviazione della gara",
	          "archiviagara.error", e);
	    } finally {
	        if (status != null) {
	          try {
	            if (commitTransaction) {
	            	this.sqlManager.commitTransaction(status);
	            } else {
	            	this.sqlManager.rollbackTransaction(status);
	            }
	          } catch (SQLException e) {
	            logger.error("Errore durante la chiusura della transazione", e);
	          }
	        }
	      }
	    }
  
  public void updateFlussoProgramma(final PubblicaProgrammaLavoriEntry programma, final PubblicaProgrammaFornitureServiziEntry programmaFS, final PubblicaProgrammaDM112011Entry programmaDM112011,final Long contri) throws GestoreException {

	  String updateIdRicevutoProgramma = "UPDATE PIATRI SET ID_RICEVUTO = ? WHERE CONTRI = ?";
	  TransactionStatus status = null;
	    boolean commitTransaction = false;
	    try {
	    	status = this.sqlManager.startTransaction();
	    	if (programma != null) {
	    		this.sqlManager.update(updateIdRicevutoProgramma, new Object[] {programma.getIdRicevuto(), contri});
	    	} else if (programmaFS != null) {
	    		this.sqlManager.update(updateIdRicevutoProgramma, new Object[] {programmaFS.getIdRicevuto(), contri});
	    	} else if (programmaDM112011 != null) {
	    		this.sqlManager.update(updateIdRicevutoProgramma, new Object[] {programmaDM112011.getIdRicevuto(), contri});
	    	}
	      	commitTransaction = true;
	    } catch (Exception e) {
	      throw new GestoreException(
	          "errore durante l'archiviazione della gara",
	          "archiviagara.error", e);
	    } finally {
	        if (status != null) {
	          try {
	            if (commitTransaction) {
	            	this.sqlManager.commitTransaction(status);
	            } else {
	            	this.sqlManager.rollbackTransaction(status);
	            }
	          } catch (SQLException e) {
	            logger.error("Errore durante la chiusura della transazione", e);
	          }
	        }
	      }
	    }
  
  /**
   * Aggiornamento della gara con il valore di IDEGOV restituito dal PROXY.
   * 
   * @param codGara Codice della gara
   * @param idEgov IdEgov
   * @throws GestoreException GestoreException
   */
  public void aggiornaW9GARAIdEgov(Long codGara, String idEgov) throws GestoreException {
    String updateW9GARA = "UPDATE W9GARA SET IDEGOV = ? WHERE CODGARA = ?";

    try {
      TransactionStatus status = this.sqlManager.startTransaction();
      this.sqlManager.update(updateW9GARA, new Object[] { idEgov, codGara });
      this.sqlManager.commitTransaction(status);
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nell\'aggiornamento del campo IDEGOV della GARA",
          "", e);
    }
  }


  /**
   * Aggiornamento del campo W9LOTT.DAEXPORT a '2' per tutti i lotti della gara.
   * 
   * @param codgara Codice della gara
   * @throws GestoreException GestoreException
   */
  public void aggiornaW9LOTT_DAEXPORT(Long codgara) throws GestoreException {
	    try {
	      String updateW9LOTT = "UPDATE W9LOTT SET DAEXPORT = '2' WHERE CODGARA = ?";

	      TransactionStatus status = this.sqlManager.startTransaction();
	      this.sqlManager.update(updateW9LOTT, new Object[] { codgara });
	      this.sqlManager.commitTransaction(status);
	    } catch (SQLException e) {
	      throw new GestoreException(
	          "Errore nell\'aggiornamento del campo DAEXPORT dei lotti della gara", null, e);
	    }
	  }
  
  /**
   * Aggiornamento del campo DAEXPORT a '2' per l'istanza W9FASI interessata.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param faseesecuzione Fase d'esecuzione
   * @param num Progressivo della fase d'esecuzione
   * @throws GestoreException GestoreException
   */
  public void aggiornaW9FASI_DAEXPORT(Long codgara, Long codlott, Long faseesecuzione, Long num) throws GestoreException {
    try {
      TransactionStatus status = this.sqlManager.startTransaction();
      this.sqlManager.update("UPDATE W9FASI SET DAEXPORT='2' WHERE CODGARA=? AND CODLOTT=? AND FASE_ESECUZIONE=? AND NUM=?", 
      		new Object[] { codgara, codlott, faseesecuzione, num });
      this.sqlManager.commitTransaction(status);
         
    } catch (SQLException e) {
      throw new GestoreException(
          "Errore nell\'aggiornamento del campo DAEXPORT dei lotti della gara", null, e);
    }
  }

  private String getCodFlusso(String entita, String idFlusso) throws GestoreException {
    String sqlSelectCodFlusso = "select KEY03 from W9FLUSSI where IDFLUSSO = ";
    Long codFlusso;
    try {
    	if ("W9FLUSSI_ELIMINATI".equalsIgnoreCase(entita)) {
    		sqlSelectCodFlusso = "select KEY03 from W9FLUSSI_ELIMINATI where IDFLUSSO = ";
    	}
      codFlusso = (Long) this.sqlManager.getObject(sqlSelectCodFlusso + idFlusso,
          new Object[] {});
      if (codFlusso == null) {
        codFlusso = new Long(idFlusso);
      }
    } catch (Exception e) {
      throw new GestoreException(
          "Errore durante il recupero del codice del flusso.", "codice.flusso",
          e);
    }
    return codFlusso.toString();
  }

  /**
   * Estrae il campo UFFINT.CODEIN a partire dal CF della stazione appaltante.
   * 
   * @param cfein
   * @return
   * @throws GestoreException
   */
  public String getCodein(final String cfein) throws GestoreException {
    String sqlSelectCodFlusso = "select CODEIN from UFFINT where CFEIN = ?";
    String codein = "";
    try {
      codein = this.sqlManager.getObject(sqlSelectCodFlusso, new Object[] { cfein }).toString();
    } catch (SQLException e) {
      throw new GestoreException("Errore durante il recupero del codein", null, e);
    }
    return codein;
  }

  public String getCfTec(final String codein, final long syscon) throws GestoreException {
    String cfTec = "";
    try {
      cfTec = (String) this.sqlManager.getObject("select CFTEC from TECNI where SYSCON = ? and CGENTEI = ?",
        new Object[] { new Long (syscon), codein } );
    } catch (SQLException se) {
      throw new GestoreException("Errore durante il recupero del cftec", null, se);
    }
    return cfTec;
  }
  
  /**
   * Determina se una certa fase e' stata inviata con successo o meno.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param codiceFase Codice della fase
   * @param num Numero Progressivo del flusso (per quei flussi che possono essere piu' di uno)
   * @return Ritorna true se esiste il flusso in W9FLUSSI relativo alla fase richiesta.
   * @throws SQLException SQLException
   */
 /* public boolean esisteFlusso(Long codgara, Long codlott, Long codiceFase, Long num) throws SQLException {
    boolean esiste = true;
    
    String sqlSelect = "select count(*) from w9flussi where key01=? and key02=? and key03=?";
    Object[] sqlObjArray = new Object[]{codgara, codlott, codiceFase};
    
    if (num != null) {
      sqlSelect = sqlSelect.concat(" key04=?");
      sqlObjArray = new Object[]{codgara, codlott, codiceFase, num};
    }
    
    Long valore = null;
    try {
      valore = (Long) sqlManager.getObject(sqlSelect, sqlObjArray);
      if (valore != null && valore.longValue() > 0) {
        esiste = true;
      }
    } catch (SQLException e) {
      logger.error("Errore nella verifica di esistenza di un flusso", e);
      throw e;
    }
    return esiste;
  }*/
  
  /**
   * Determina la situazione del lotto di gara.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @return Ritorna la situazione del lotto.
   * @throws SQLException SQLException
   */
  public Long getSituazioneLotto(Long codgara, Long codlott) throws SQLException {
    int situazione = -1;
    
    try {
    	Long numappa = (Long) sqlManager.getObject("select MAX(NUM_APPA) from W9APPA where CODGARA=? and CODLOTT=?", new Object[]{ codgara, codlott });
    	if (numappa == null) {
    	  numappa = new Long(1);
    	}
    	// Test per capire se il lotto e' nello stato di "SITUAZIONE_RICHIESTA_CANCELLAZIONE" (Situazione = 95)
      Long numeroRichiesteCancellazione = (Long) sqlManager.getObject(
          "select count(*) from w9flussi " +
            "f1 join w9flussi f2 on f1.key01 = f2.key01 and f1.key02 = f2.key02 and f1.key03 = f2.key03 and f1.key04 = f2.key04 and f1.datinv > f2.datinv " + 
            "where f1.tinvio2 = -1 and f2.tinvio2 > 0 and f1.key01=? and f1.key02=?",
            new Object[]{ codgara, codlott });
      
      // esiste richiesta di cancellazione aperta se esiste un record con W9FLUSSI.TINVIO2 = -1 e 
      // per la stessa fase esistono dei flussi precedenti (filtro su DATINV) e W9FLUSSI.TINVIO2 > 0
      // Quando la richiesta di cancellazione viene chiusa (accettata in ORT) i record con W9FLUSSI.TINVIO2 > 0
      // vengono spostati in W9FLUSSI_ELIMINATI.
      
      if (numeroRichiesteCancellazione > 0) {
        situazione = SituazioneGaraLotto.SITUAZIONE_RICHIESTA_CANCELLAZIONE.getSituazione();
      }
      //Ho aggiunto un ciclo per ricavare lo stato del lotto nel caso in cui ad esempio cancello una riaggiudicazione
      //a quel punto lo stato sarebbe 1 cioè da aggiudicare in realtà lo stato è monitoraggio terminato derivato dall'aggiudicazione precedente
       while (situazione < 0) {
    	// Test per capire se il lotto e' nello stato di "ANNULLATO" (Situazione = 8)
    	      if (situazione < 0) {
    	      	Long esitoProcedura = (Long) sqlManager.getObject(
    		          "select e.esito_procedura from w9esito e, w9flussi fl, w9fasi fa " 
    		          + "where e.codgara=? and e.codlott=? "
    		          + "  and e.codgara=fa.codgara and e.codlott=fa.codlott " 
    		          + "  and fa.codgara=fl.key01 and fa.codlott=fl.key02 "
    		          + "  and fa.fase_esecuzione=fl.key03 and fa.num_appa=? and fa.fase_esecuzione=? and fa.num = fl.key04 "
    		          + "  and  e.codgara=fl.key01 and e.codlott=fl.key02 and fl.tinvio2 > 0",
    		          new Object[]{codgara, codlott, numappa, new Long(CostantiW9.COMUNICAZIONE_ESITO)});
    		      
    		      if (esitoProcedura != null && esitoProcedura.longValue() > 1) {
    		        situazione = SituazioneGaraLotto.SITUAZIONE_ANNULLATO.getSituazione();
    		      }
    	      }
    	      

    	      // Test per capire se il lotto e' nello stato di "SOSPESO" (Situazione = 5)
    	      if (situazione < 0) {
    	        Long lottoSospeso = (Long) sqlManager.getObject(
    	            "select count(s.codgara) from w9sosp s, w9flussi fl, w9fasi fa "
    	            + "where  s.codgara=? and s.codlott=? and s.DATA_VERB_RIPR IS NULL "
    	            + "  and  s.codgara=fa.codgara and s.codlott=fa.codlott "
    	            + "  and fa.codgara=fl.key01 and fa.codlott=fl.key02 "
    	            + "  and fa.fase_esecuzione=fl.key03 and fa.num_appa=? and fa.fase_esecuzione=? and fa.num = fl.key04 "
    	            + "  and  s.codgara=fl.key01 and  s.codlott=fl.key02 and fl.tinvio2 > 0",
    	            new Object[]{codgara, codlott, numappa, new Long(CostantiW9.SOSPENSIONE_CONTRATTO)});
    	        if (lottoSospeso != null && lottoSospeso.longValue() > 0) {
    	          situazione = SituazioneGaraLotto.SITUAZIONE_SOSPESO.getSituazione();
    	        }
    	      }
    	      
    	      // Test per capire se il lotto e' nello stato di "COLLAUDATO" (Situazione = 7)
    	      if (situazione < 0) {
    	        Long lottoCollaudato = (Long) sqlManager.getObject(
    	           " select count(fa.codgara) from w9flussi fl, w9fasi fa " 
    	           + "where fa.codgara=? and fa.codlott=? and fa.num_appa=? " 
    	           + "  and fa.codgara=fl.key01 and fa.codlott=fl.key02 "
    	           + "  and fa.fase_esecuzione=fl.key03 and fa.num=fl.key04 " 
    	           + "  and fa.fase_esecuzione in (?,?,?) and fl.tinvio2 > 0",
    	           new Object[]{codgara, codlott, numappa, new Long(CostantiW9.COLLAUDO_CONTRATTO),
    	               new Long(CostantiW9.STIPULA_ACCORDO_QUADRO),
    	               new Long(CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO) });
    	        
    	        /*Long lottoConclusoAnticipatamente = (Long) sqlManager.getObject(
    	           " select count(fa.codgara) from w9flussi fl, w9fasi fa "
    	           + "where fa.codgara=? and fa.codlott=? and fa.num_appa=? and fa.fase_esecuzione=? " 
    	           + "  and fa.codgara=fl.key01 and fa.codlott=fl.key02 "
    	           + "  and fa.fase_esecuzione=fl.key03 and fa.num=fl.key04 "
    	           + "  and fl.tinvio2 > 0 "
    	           + "  and (select count(codgara) from W9CONC where codgara=? and codlott=? and num_appa=? and INTANTIC='1') > 0",
    	           new Object[] { codgara, codlott, numappa, new Long(CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA), codgara, codlott, numappa } );*/
    	        
    	        Long lottoAccorpatoInCIGMaster = (Long) sqlManager.getObject("select count(*) "
    	        	+ "from W9LOTT where CIG<>CIG_MASTER_ML and CODGARA = ? and CODLOTT = ?", new Object[] { codgara, codlott});
    	        
    	        if ((lottoCollaudato != null && lottoCollaudato.longValue() > 0 ) ||
    	            //(lottoConclusoAnticipatamente != null && lottoConclusoAnticipatamente.longValue() > 0) ||
    	            (lottoAccorpatoInCIGMaster != null && lottoAccorpatoInCIGMaster.longValue() > 0)) {
    	          situazione = SituazioneGaraLotto.SITUAZIONE_COLLAUDATO.getSituazione();
    	        }
    	      }
    	      
    	      // Test per capire se il lotto e' nello stato di "CONCLUSO" (Situazione = 6)
    	      if (situazione < 0) {
    	        Long lottoConcluso = (Long) sqlManager.getObject(
    	            "select count(fa.codgara) from w9flussi fl, w9fasi fa " 
    	            + "where fa.codgara=? and fa.codlott=? and fa.num_appa=? " 
    	            + "  and fa.codgara=fl.key01 and fa.codlott=fl.key02 "
    	            + "  and fa.fase_esecuzione=fl.key03 and fa.fase_esecuzione in (?,?) "
    	            + "  and fa.num = fl.key04 and fl.tinvio2 > 0",
    	            new Object[]{codgara, codlott, numappa, new Long(CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA),
    	                  new Long(CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO)});
    	        if (lottoConcluso != null && lottoConcluso.longValue() > 0) {
    	          situazione = SituazioneGaraLotto.SITUAZIONE_CONCLUSO.getSituazione();
    	        }
    	      }
    	      
    	      // Test per capire se il lotto e' nello stato di "IN ESECUZIONE" (Situazione = 4)
    	      if (situazione < 0) {
    	        Long lottoInEsecuzione = (Long) sqlManager.getObject(
    	            "select count(fa.codgara) from w9flussi fl, w9fasi fa " 
    	            + "where fa.codgara=? and fa.codlott=? and fa.num_appa=? " 
    	            + "  and fa.codgara=fl.key01 and fa.codlott=fl.key02 "
    	            + "  and fa.fase_esecuzione=fl.key03 and fa.fase_esecuzione in (?,?) "
    	            + "  and fa.num = fl.key04 and fl.tinvio2 > 0",
    	            new Object[]{codgara, codlott, numappa, new Long(CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA),
    	                 new Long(CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO)});
    	        if (lottoInEsecuzione != null && lottoInEsecuzione.longValue() > 0) {
    	          situazione = SituazioneGaraLotto.SITUAZIONE_IN_ESECUZIONE.getSituazione();
    	        }
    	      }
    	      
    	      // Test per capire se il lotto e' nello stato di "INIZIATO" (Situazione = 3)
    	      if (situazione < 0) {
    	        Long lottoIniziato = (Long) sqlManager.getObject(
    	            "select count(fa.codgara) from w9flussi fl, w9fasi fa " 
    	            + "where fa.codgara=? and fa.codlott=? and fa.num_appa=? " 
    	            + "  and fa.codgara=fl.key01 and fa.codlott=fl.key02 "
    	            + "  and fa.fase_esecuzione=fl.key03 and fa.fase_esecuzione in (?,?,?) "
    	            + "  and fa.num = fl.key04 and fl.tinvio2 > 0",
    	            new Object[]{codgara, codlott, numappa, new Long(CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA),
    	                  new Long(CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO), 
    	                  new Long(CostantiW9.STIPULA_ACCORDO_QUADRO)});
    	        if (lottoIniziato != null && lottoIniziato.longValue() > 0) {
    	          situazione = SituazioneGaraLotto.SITUAZIONE_INIZIATO.getSituazione();
    	        }
    	      }

    	      // Test per capire se il lotto e' nello stato di "AGGIUDICATO" (Situazione = 2)
    	      if (situazione < 0) {
    	        Long lottoAggiudicato = (Long) sqlManager.getObject(
    	            "select count(fa.codgara) from w9flussi fl, w9fasi fa " 
    	            + "where fa.codgara=? and fa.codlott=? and fa.num_appa=? " 
    	            + "  and fa.codgara=fl.key01 and fa.codlott=fl.key02 "
    	            + "  and fa.fase_esecuzione=fl.key03 and fa.fase_esecuzione in (?,?,?) "
    	            + "  and fa.num = fl.key04 and fl.tinvio2 > 0",
    	            new Object[]{codgara, codlott, numappa, new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA),
    	                  new Long(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE), 
    	                  new Long(CostantiW9.ADESIONE_ACCORDO_QUADRO)});
    	        if (lottoAggiudicato != null && lottoAggiudicato.longValue() > 0) {
    	          situazione = SituazioneGaraLotto.SITUAZIONE_AGGIUDICATO.getSituazione();
    	        }
    	      }
    	      
    	      // Test per capire se il lotto e' nello stato di "IN GARA" (Situazione = 1)
    	      if (situazione < 0) {
    	    	  if (numappa == 1) {
    	    		  //esco dal ciclo
    	    		  situazione = SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione();
    	    		  break;
    	    	  } else {
    	    		  numappa = numappa - 1;
    	    	  }
    	      }
       }
      
    } catch (SQLException e) {
      logger.error("Errore nel determinare la situazione del lotto numero " + codlott
          + " della gara con codgara=" + codgara, e);
      throw e;
    }
    return new Long(situazione);
  }
  
  /**
   * Determina la situazione della gara in funzione della situazione dei lotti.
   * 
   * @param codGara Codice della gara
   * @return Ritorna la situazione della gara in funzione della situazione dei lotti.
   * @throws SQLException SQLException
   */
  public int getSituazioneGara(Long codGara) throws SQLException {
    int situazione = -1;
    String sqlSituazioneLotti = "select count(*) from w9lott where codgara=? and situazione=?";
    
    try {
      Long numeroLotti = (Long) this.sqlManager.getObject(
          "select NLOTTI from W9GARA where CODGARA=?", new Object[]{codGara});
      
      if (numeroLotti != null && numeroLotti.longValue() > 0) {
        Long numeroLottiAnnullati = (Long) this.sqlManager.getObject(sqlSituazioneLotti,
            new Object[]{codGara, new Long(SituazioneGaraLotto.SITUAZIONE_ANNULLATO.getSituazione())});
        
        if (numeroLotti.equals(numeroLottiAnnullati)) {
          situazione = SituazioneGaraLotto.SITUAZIONE_ANNULLATO.getSituazione();
        } else {
          for (int i = SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione();
                  i <= SituazioneGaraLotto.SITUAZIONE_ANNULLATO.getSituazione() && situazione < 0; i++) {
            Long count = (Long)  this.sqlManager.getObject(sqlSituazioneLotti, new Object[]{codGara, new Long(i)});
            
            if (count != null && count.longValue() > 0) {
              situazione = i;
            }
          }
        }
      }
      if (situazione < 0) {
        situazione = SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione();
      }
    } catch (SQLException e) {
      logger.error("Errore nel determinare la situazione della gara in funzione della situazione dei lotti"
          + " (codgara=" + codGara + ")", e);
      throw e;
    }
    return situazione;
  }
  
  /**
   * Aggiornamento della situazione del lotto e la situazione della gara stessa.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @throws SQLException SQLException
   */
  public void updateSituazioneGaraLotto(final Long codgara, final Long codlott) throws SQLException {

    if (codlott != null) {
      try {
        // Situazione del lotto
        Long situazione = this.getSituazioneLotto(codgara, codlott);
        // Aggiornamento della situazione del lotto
        this.sqlManager.update("update W9LOTT set SITUAZIONE=? where CODGARA=? and CODLOTT=?",
            new Object[]{new Long(situazione), codgara, codlott});
      } catch (SQLException e) {
        logger.error("Errore nell'aggiornamento della situazione del lotto (codgara="
            + codgara + ", codlott=" + codlott + ")", e);
        throw e;
      }
    }
    
    try {
      // Situazione della gara
      int situazione = this.getSituazioneGara(codgara);
      // Aggiornamento della situazione della gara
      this.sqlManager.update("update W9GARA set SITUAZIONE=? where CODGARA=?",
          new Object[]{new Long(situazione), codgara});
    } catch (SQLException e) {
      logger.error("Errore nell'aggiornamento della situazione della gara (codgara=" + codgara + ")", e);
      throw e;
    }
  }

  
  /**
   * Aggiornamento della situazione di ciascun lotto della gara ed infine
   * aggiornamento della situazione della gara stessa.
   * 
   * @param codgara Codice della gara
   * @throws SQLException SQLException
   */
  public void updateSituazioneLottiGara(Long codgara) throws SQLException {

    List< ? > listaCodLott = this.sqlManager.getListVector(
        "select CODLOTT from W9LOTT where CODGARA=? order by codlott asc",
        new Object[] {codgara}); 
    
    if (listaCodLott != null && listaCodLott.size() > 0) {
      for (int oi = 0; oi < listaCodLott.size(); oi++) {
        Vector< ? > tempVct = (Vector< ? >) listaCodLott.get(oi);
        Long codlott = (Long) ((JdbcParametro) tempVct.get(0)).getValue();
        if (codlott != null) {
          try {
            // Situazione del lotto
            Long situazione = this.getSituazioneLotto(codgara, codlott);
            // Aggiornamento della situazione del lotto
            this.sqlManager.update("update W9LOTT set SITUAZIONE=? where CODGARA=? and CODLOTT=?",
                new Object[] { situazione, codgara, codlott } );
          } catch (SQLException e) {
            logger.error("Errore nell'aggiornamento della situazione del lotto (codgara="
                + codgara + ", codlott=" + codlott + ")", e);
            throw e;
          }
        }
      }
    }
    try {
      // Situazione della gara
      int situazione = this.getSituazioneGara(codgara);
      // Aggiornamento della situazione della gara
      this.sqlManager.update("update W9GARA set SITUAZIONE=? where CODGARA=?",
          new Object[] { new Long(situazione), codgara } );
    } catch (SQLException e) {
      logger.error("Errore nell'aggiornamento della situazione della gara (codgara=" + codgara + ")", e);
      throw e;
    }
  }
  
  /**
   * Ritorna il permesso dell'utente sulla gara e/o sul lotto
   * 
   * 
   * @param codiceGara
   * @param codiceLotto
   * @param idUtente
   * @param codiceStazioneApp
   * @param idScheda
   * @return
   * @throws SQLException 
   * @throws GestoreException 
   */
  public Long getPermessoUtente(Long codiceGara, Long codiceLotto, Long idUtente,
      String codiceStazioneApp, int idScheda) throws SQLException, GestoreException {
    
    Long permessoUtente = null;
    List<?> listaPermessiUtente = null;
    if (codiceLotto == null) {
      listaPermessiUtente = this.sqlManager.getListVector(
             "select w.PERMESSO from V_W9PERMESSI w " +
              "where w.CODGARA = ? " +
                "and w.MACROFASE = (select IDMACROFASE from W9CF_MACROFASI where IDSCHEDA=?) " + 
                "and w.RUOLO in ( " +
                    "select r.ID_RUOLO from V_RUOLOTECNICO r " +
                     "where r.CODGARA = ? " +
                     "and r.SYSCON = ?)",
        new Object[] { codiceGara, new Long(idScheda), codiceGara, idUtente } );
    } else {
      listaPermessiUtente = this.sqlManager.getListVector(
          "select w.PERMESSO from V_W9PERMESSI w " +
           "where w.CODGARA = ? " +
             "and w.MACROFASE = (select IDMACROFASE from W9CF_MACROFASI where IDSCHEDA=?) " +
             "and w.RUOLO in ( " +
                 "select r.ID_RUOLO from V_RUOLOTECNICO r " +
                  "where r.CODGARA = ? " +
                    "and r.CODLOTT = ? " +
                    "and r.SYSCON = ?)",
                    //"and r.CODTEC in (select CODTEC FROM TECNI WHERE SYSCON=? and CGENTEI=?))",
        new Object[] { codiceGara, new Long(idScheda), codiceGara, codiceLotto, idUtente } );
    }
    
    if (listaPermessiUtente != null && listaPermessiUtente.size() > 0) {
      if (listaPermessiUtente.size() == 1) {
        Vector<?> tempDato = (Vector<?>) listaPermessiUtente.get(0);
        permessoUtente = ((JdbcParametro) tempDato.get(0)).longValue();
      } else {
        String tempPermessi = "";
        ArrayList<Long> listaPermessi = new ArrayList<Long>();
        for (int i=0; i < listaPermessiUtente.size(); i++) {
          Vector<?> tempDato = (Vector<?>) listaPermessiUtente.get(i);
          tempPermessi = tempPermessi.concat(((JdbcParametro) tempDato.get(0)).getValue().toString());
          listaPermessi.add(((JdbcParametro) tempDato.get(0)).longValue());
        }
        
        if (tempPermessi.contains("3") && tempPermessi.contains("4")) {
          permessoUtente = new Long(5);
        } else {
          permessoUtente = Collections.max(listaPermessi);
        }
      }
    }
    
    if (permessoUtente != null && permessoUtente.longValue() > 0) {
      return permessoUtente;
    } else {
      return null;
    } 
  }

  /**
   * Ritorna true se la stazione appaltante (individuata dal CODEIN) ha dei permessi configurati,
   * cioe' se esiste un record di W9CF_MODPERMESSI_SA con CODEIN uguale a quello della 
   * stazione appaltante.
   * 
   * @param codein
   * @return
   */
  public boolean isStazioneAppaltanteConPermessi(String codein) throws SQLException {
    Long numeroRecord = (Long) this.sqlManager.getObject(
        "select count (*) from W9CF_MODPERMESSI_SA where CODEIN=?", new Object[] { codein } );
    if (numeroRecord != null && numeroRecord > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Ritorna true se la stazione appaltante (individuata dal CODEIN) ha dei permessi configurati
   * da applicare in fase di creazione/update della gara/lotto, cioe' se esiste un record di
   * W9CF_MODPERMESSI_SA con CODEIN uguale a quello della stazione appaltante e APPLICA='1'.
   * 
   * @param codein
   * @return
   */
  public boolean isStazioneAppaltanteConPermessiDaApplicareInCreazione(String codein) throws SQLException {
    Long numeroRecord = (Long) this.sqlManager.getObject(
        "select count (*) from W9CF_MODPERMESSI_SA where CODEIN=? and APPLICA='1'",
        new Object[] { codein } );
    if (numeroRecord != null && numeroRecord > 0) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Ritorna true se l'utente e' il RUP della gara, cioe' se esiste la gara 
   * con GARA.CODGARA=<i>codiceGara</i> ha il campo W9GARA.RUP valorizzato
   * con il valore di TECNI.CODTEC, che rappresenta il tecnico della S.A. con 
   * UFFINT.CODEIN=<i>codiceStazioneApp</i> e TECNI.SYSCON=<i>idUtente</i>
   * dell'utente. 
   * 
   * @param idUtente
   * @param codiceStazioneApp
   * @param idScheda
   * @throws SQLException
   * @throws GestoreException
   */
  public boolean isUserRupDellaGara(Long codiceGara, Long idUtente, String codiceStazioneApp)
      throws SQLException {
    boolean result = false;
    
    //String SQL_UTENTE_RUP = "select RUP from W9GARA where CODGARA=? and CODEIN=? " +
    //		"and RUP IN (select CODTEC from TECNI where SYSCON=? and CGENTEI=?)";
    
    String SQL_UTENTE_RUP = "select TECNI.CFTEC from W9GARA join TECNI on W9GARA.RUP = TECNI.CODTEC where CODGARA = ? " +
	"and TECNI.CFTEC = (select SYSCF from USRSYS where SYSCON=? and SYSCF is not null)";

    String rup = (String) this.sqlManager.getObject(SQL_UTENTE_RUP,
        new Object[] { codiceGara, idUtente });
    
    if (StringUtils.isNotEmpty(rup)) {
      result = true;
    }
    
    return result;
  }
  
  
  /**
   * Determina se, a partire dal codice della SA e dall' ID utente, l'utente e' un
   * tecnico della SA attiva.
   * 
   * @param idUtente
   * @param codiceStazioneApp
   * @return Ritorna true se l'utente e' tecnico della SA attiva, false altrimenti.
   * @throws SQLException
   */
  public boolean isUserTecnicoSAById(Long idUtente, String codiceStazioneApp) throws SQLException {
    boolean result = false;
    
    String SQL_UTENTE_RUP = "select count(CODTEC) from TECNI where SYSCON=? and CGENTEI=?";
    Long countCODTEC = (Long) this.sqlManager.getObject(SQL_UTENTE_RUP,
        new Object[] { idUtente, codiceStazioneApp });
    
    if (countCODTEC.longValue() > 0) {
      result = true;
    }
    
    return result;
  }
  
  /**
   * Determina se, a partire dal codice della SA e dal CF dell'utente, l'utente e' un
   * tecnico della SA attiva.
   * 
   * @param userCF codice fiscale dell'utente
   * @param codiceStazioneApp
   * @return Ritorna true se l'utente e' tecnico della SA attiva, false altrimenti.
   * @throws SQLException
   */
  public boolean isUserTecnicoSAByCF(String userCF, String codiceStazioneApp) throws SQLException {
    boolean result = false;
    
    String SQL_UTENTE_RUP = "select count(CODTEC) from TECNI t, USRSYS u " +
    		"where t.SYSCON=u.SYSCON and t.CGENTEI=? and t.CFTEC=? and (u.SYSCF=? or SYSCF is null)";
    Long countCODTEC = (Long) this.sqlManager.getObject(SQL_UTENTE_RUP,
        new Object[] { codiceStazioneApp, userCF, userCF });
    
    if (countCODTEC.longValue() > 0) {
      result = true;
    }
    
    return result;
  }
  
  /**
   * Ceazione dei permessi di una gara a partire da un modello esistente.
   * 
   * @param codGara
   * @param codiceConfig
   * @throws SQLException
   */
  public void updateAttribuzioneModello(Long codGara, Long codiceConfig) throws SQLException {
    List<?> listaPermessi = this.sqlManager.getListVector(
        "select NUMREG, RUOLO, MACROFASE, PERMESSO from W9CF_REGPERMESSI where CODCONF=?",
        new Object[] {codiceConfig} );

    this.updateCancellazioneModello(codGara);
    
    if (listaPermessi != null && listaPermessi.size() > 0) {
      for (int i=0 ; i < listaPermessi.size(); i++) {
        Vector<?> vettore = (Vector<?>) listaPermessi.get(i);
        
        this.sqlManager.update("INSERT INTO W9PERMESSI (CODGARA, NUMREG, RUOLO, MACROFASE, PERMESSO) values (?,?,?,?,?)",
            new Object[] {codGara, ((JdbcParametro)vettore.get(0)).getValue(), 
            ((JdbcParametro)vettore.get(1)).getValue(),((JdbcParametro)vettore.get(2)).getValue(),
            ((JdbcParametro)vettore.get(3)).getValue() } );
      }
    }

  }
  
  /**
   * Cancellazione dei permessi di una gara, cancellando i record dalla tabella
   * W9PERMESSI a partire dal codgara.
   * 
   * @param codGara
   * @throws SQLException
   */
  public void updateCancellazioneModello(Long codGara) throws SQLException {
    this.sqlManager.update("delete from W9PERMESSI where CODGARA=?", new Object[] { codGara } );
  }
  
  
  /**
   * 
   * @param codgara codice gara
   * @param archivia true se gara da archiviare, false per annullare l'archiviazione
   * @throws GestoreException GestoreException
   */
  public void archiviaGara(Long codgara, boolean archivia) throws GestoreException {

    String updateSituazioneGara = "update w9gara set situazione = ? where codgara = ?";
    Long situazione = new Long(91);
    TransactionStatus status = null;
    boolean commitTransaction = false;
    try {
      status = this.sqlManager.startTransaction();
      if (archivia) {
      	this.sqlManager.update(updateSituazioneGara, new Object[] { situazione, codgara });
      } else {
      	this.updateSituazioneLottiGara(codgara);
      }
      commitTransaction = true;
    } catch (SQLException e) {
      throw new GestoreException(
          "errore durante l'archiviazione della gara",
          "archiviagara.error", e);
    } finally {
        if (status != null) {
          try {
            if (commitTransaction) {
            	this.sqlManager.commitTransaction(status);
            } else {
            	this.sqlManager.rollbackTransaction(status);
            }
          } catch (SQLException e) {
            logger.error("Errore durante la chiusura della transazione", e);
          }
        }
      }
    }

  /**
   * 
   * @param codRup
   *          codice del rup
   * @param listaTecniciAssociati
   *          tecnici da associare al RUP in W9DELEGHE
   * @throws GestoreException
   *           GestoreException
   */
  public void salvaDeleghe(String codrup, String listaTecniciAssociati)
      throws GestoreException {

    String deleteDelegheRUP = "delete from w9deleghe where codrup = ?";
    TransactionStatus status = null;
    boolean commitTransaction = false;
    try {
      status = this.sqlManager.startTransaction();
      this.sqlManager.update(deleteDelegheRUP, new Object[] { codrup });
      String[] arrayTecnici = listaTecniciAssociati.split(";");
      for (int i = 0; i < arrayTecnici.length; i++) {
        if (arrayTecnici[i] != null && !arrayTecnici[i].equals("")) {
          this.sqlManager.update(
              "insert into w9deleghe(codrup, codtec) values(? , ?)",
              new Object[] { codrup, arrayTecnici[i] });
        }
      }
      commitTransaction = true;
    } catch (SQLException e) {
      throw new GestoreException("errore durante il salvataggio delle deleghe",
          "salvadeleghe.error", e);
    } finally {
      if (status != null) {
        try {
          if (commitTransaction) {
            this.sqlManager.commitTransaction(status);
          } else {
            this.sqlManager.rollbackTransaction(status);
          }
        } catch (SQLException e) {
          logger.error("Errore durante la chiusura della transazione", e);
        }
      }
    }
  }
 
  /**
   * Aggiornamento dei dati dell'adesione accordo quadro a partire dai dati dell'aggiudicazione
   * della stipula accordo quadro (W9GARA.CIG_ACCQUADRO)
   * 
   * @param codGara Codice gara
   * @param codlott Codice lotto
   * @param numAppa numero appalto
   * @param cigAccordoQuadro Cig Accordo Quadro da cui dipende l'attuale lotto
   * @param consultaGaraResponseDocument Oggetto scaricato da SIMOG con i dati del CIG Accordo quadro
   * @throws SQLException 
   * @throws GestoreException 
   */
  public HashMap<String, Object> aggiornaAdesioneAccordoQuadro(Long codGara, Long codLott, Long numAppa, String codein,
      String cigAccordoQuadro, ConsultaGaraResponseDocument consultaGaraResponseDocument) 
          throws SQLException, GestoreException {
    
    HashMap<String, Object> result = new HashMap<String, Object>();
    
    if (consultaGaraResponseDocument != null) {
      if (consultaGaraResponseDocument.getConsultaGaraResponse().isSetReturn()) {
        if (consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getSuccess()) {
          if (consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().isSetGaraXML()) {
            SchedaType schedaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML();
            if (schedaType.isSetDatiScheda()) {
              SchedaCompletaType[] arraySchedeComplete = schedaType.getDatiScheda().getSchedaCompletaArray();
              AggiudicazioneType aggiudicazioneType = null;
              if (arraySchedeComplete.length > 1) {
                // Ciclo l'array per cercare la scheda con CUI maggiore, cioe' quello che
                // contiene i dati dell'ultima aggiudicazione (nei casi di riaggiudicazione)
                List<String> listaCodiciCUI = new ArrayList<String>();
                HashMap<String, Long> mappa = new HashMap<String, Long>();
                for (int i=0; i < arraySchedeComplete.length; i++) {
                  if (arraySchedeComplete[i].isSetAggiudicazione()) {
                    String codiceCUI = arraySchedeComplete[i].getCUI();
                    mappa.put(codiceCUI, new Long(i));
                    listaCodiciCUI.add(codiceCUI);
                  }
                }
                
                if (listaCodiciCUI.size() > 0) {
                  // Ordinamento della lista dei codici CUI
                  Collections.sort(listaCodiciCUI);
                  // Considero l'indice dell'ultimo codice CUI (quindi l'ultima aggiudicazione)
                  Long indice = mappa.get(listaCodiciCUI.get(listaCodiciCUI.size()-1));
                  aggiudicazioneType = arraySchedeComplete[indice.intValue()].getAggiudicazione();
                } else {
                  // nessuna delle Schede complete ha l'aggiudicazione
                  logger.error("Su SIMOG l'aggiudicazione dell'accordo quadro per il CIG " + cigAccordoQuadro + " non e' stata confermata su SIMOG (1).");
                  
                  result.put("esito", Boolean.FALSE);
                  result.put("msgError", "La scheda di aggiudicazione dell'accordo quadro per il CIG " + cigAccordoQuadro + " non e' stata confermata su SIMOG");
                }
              } else {
                if (arraySchedeComplete[0].isSetAggiudicazione()) {
                  aggiudicazioneType = arraySchedeComplete[0].getAggiudicazione();
                } else {
                  // l'unica scheda completa non ha l'aggiudicazione
                  logger.error("Su SIMOG l'aggiudicazione dell'accordo quadro per il CIG " + cigAccordoQuadro + " non e' stata confermata (2).");
                  result.put("esito", Boolean.FALSE);
                  result.put("msgError", "La scheda di aggiudicazione dell'accordo quadro per il CIG " + cigAccordoQuadro + " non e' stata confermata su SIMOG");
                }
              }
              
              if (aggiudicazioneType != null) {
                String strUpdateW9APPA = "update W9APPA set IMPORTO_AGGIUDICAZIONE=?, DATA_VERB_AGGIUDICAZIONE=?, FLAG_RICH_SUBAPPALTO=? ";
                List<Object> listaParametriUpdateW9APPA = new ArrayList<Object>();
                listaParametriUpdateW9APPA.add(new Double(aggiudicazioneType.getAppalto().getIMPORTOAGGIUDICAZIONE()));
                listaParametriUpdateW9APPA.add(aggiudicazioneType.getAppalto().getDATAVERBAGGIUDICAZIONE().getTime());
                listaParametriUpdateW9APPA.add("S".equals(aggiudicazioneType.getAppalto().getFLAGRICHSUBAPPALTO()) ? "1" : "2");
                
                if (aggiudicazioneType.getAppalto().isSetPERCRIBASSOAGG()) {
                  strUpdateW9APPA = strUpdateW9APPA.concat(", PERC_RIBASSO_AGG=? ");
                  listaParametriUpdateW9APPA.add(new Double(aggiudicazioneType.getAppalto().getPERCRIBASSOAGG()));
                }
                if (aggiudicazioneType.getAppalto().isSetPERCOFFAUMENTO()) {
                  strUpdateW9APPA = strUpdateW9APPA.concat(", PERC_OFF_AUMENTO=? ");
                  listaParametriUpdateW9APPA.add(new Double(aggiudicazioneType.getAppalto().getPERCOFFAUMENTO()));
                }
                strUpdateW9APPA = strUpdateW9APPA.concat(" where CODGARA=? and CODLOTT=? and NUM_APPA=?");
                listaParametriUpdateW9APPA.add(codGara);
                listaParametriUpdateW9APPA.add(codLott);
                listaParametriUpdateW9APPA.add(numAppa);

                // Update W9APPA
                this.sqlManager.update(strUpdateW9APPA, listaParametriUpdateW9APPA.toArray());
                
                // Gestione degli aggiudicatari
                // Aggiudicatari (solo dati per la W9AGGI)
                if (aggiudicazioneType.getAggiudicatariArray() != null && aggiudicazioneType.getAggiudicatariArray().length > 0
                        && schedaType.getAggiudicatari() != null && schedaType.getAggiudicatari().getAggiudicatarioArray() != null
                            && schedaType.getAggiudicatari().getAggiudicatarioArray().length > 0) {

                  this.gestioneAggiudicatariPerInizializzazioneAdesioneAccordoQuadro(codGara, codLott, numAppa, codein, 
                      aggiudicazioneType.getAggiudicatariArray(), aggiudicazioneType.getDitteAusiliarieArray(), 
                      schedaType.getAggiudicatari().getAggiudicatarioArray());
                  result.put("esito", Boolean.TRUE);
                }
              }
            } else {
              // SIMOG risponde senza dati della Scheda
              logger.error("Errore nell'inizializzazione della scheda adesione accordo quadro: Simog risponde senza dati della Scheda (1) - CIG ".concat(cigAccordoQuadro));
              
              result.put("esito", Boolean.FALSE);
              result.put("msgError", "Errore nell'inizializzazione della scheda adesione per i dati incompleti forniti da SIMOG");
            }
          } else {
            // SIMOG risponde senza oggetto XML
            logger.error("Errore nell'inizializzazione della scheda adesione accordo quadro: Simog risponde senza oggetto XML (2) - CIG ".concat(cigAccordoQuadro));
            
            result.put("esito", Boolean.FALSE);
            result.put("msgError", "Errore nell'inizializzazione della scheda adesione per i dati incompleti forniti da SIMOG");
          }
        } else {
          // SIMOG risponde con errore (no success)
          if (StringUtils.isNotEmpty(consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getError())) {
            logger.error("Errore nell'aggiornamento della scheda adesione accordo quadro: Simog risponde con errore (3) - CIG ".concat(cigAccordoQuadro)
                + " : ".concat(consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getError()));
          } else {
            logger.error("Errore nell'aggiornamento della scheda adesione accordo quadro: Simog risponde con errore (3) - CIG ".concat(cigAccordoQuadro));
          }
          result.put("esito", Boolean.FALSE);
          result.put("msgError", "Errore nell'aggiornamento della scheda adesione per i dati incompleti forniti da SIMOG");
        }
      } else {
        // no return
        logger.error("Errore nell'aggiornamento della scheda adesione accordo quadro: Simog risponde senza oggetto di ritorno (4)");
        result.put("esito", Boolean.FALSE);
        result.put("msgError", "Errore nell'aggiornamento della scheda adesione per i dati incompleti forniti da SIMOG");
      }
    } else {
      logger.error("Metodo invocato con oggetto consultaGaraResponseDocument non valorizzato");
      throw new NullPointerException("consultaGaraResponseDocument is null");
    }
    
    return result;
  }
  
  /**
   * Inserimento delle imprese aggiudicatarie W9AGGI e relative anagrafiche (IMPR, TEIM e IMPLEG)
   * nell'inizializzazione dell'aggiudicazione. Cancellazione dei record esistenti prima dell'inserimento. 
   * 
   * @param codGara
   * @param codLott
   * @param numAppa
   * @param codein
   * @param soggettiAggiudicatari
   * @param ditteAusiliarie
   * @param anagraficaAggiudicatari
   * @throws GestoreException
   * @throws SQLException
   */
  public void gestioneAggiudicatariPerInizializzazioneAdesioneAccordoQuadro(Long codGara, Long codLott, 
      Long numAppa, String codein, SoggAggiudicatarioType[] soggettiAggiudicatari, DittaAusiliariaType[] ditteAusiliarie,
      AggiudicatarioType[] anagraficaAggiudicatari) throws GestoreException, SQLException {
    
    if (soggettiAggiudicatari != null && soggettiAggiudicatari.length > 0 
          && anagraficaAggiudicatari!= null && anagraficaAggiudicatari.length > 0) {
      // Cancellazione dei record esistenti
      this.sqlManager.update("delete from W9AGGI where CODGARA=? and CODLOTT=? and NUM_APPA=?",
          new Object[] { codGara, codLott, numAppa } );
      
      for (int i = 0; i < soggettiAggiudicatari.length; i++) {
        DataColumn codGarW9AGGI  = new DataColumn("W9AGGI.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara));
        DataColumn codLottW9AGGI = new DataColumn("W9AGGI.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott));
        DataColumn numAppaW9AGGI = new DataColumn("W9AGGI.NUM_APPA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numAppa));
        DataColumn numAggiW9AGGI = new DataColumn("W9AGGI.NUM_AGGI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(i+1)));
        DataColumnContainer dccW9AGGI = new DataColumnContainer(new DataColumn[] { codGarW9AGGI, codLottW9AGGI, numAppaW9AGGI, numAggiW9AGGI });
        dccW9AGGI.getColumn("W9AGGI.CODGARA").setChiave(true);
        dccW9AGGI.getColumn("W9AGGI.CODLOTT").setChiave(true);
        dccW9AGGI.getColumn("W9AGGI.NUM_APPA").setChiave(true);
        dccW9AGGI.getColumn("W9AGGI.NUM_AGGI").setChiave(true);
        
        String cfImpresa = soggettiAggiudicatari[i].getCODICEFISCALEAGGIUDICATARIO();
        String codImp = (String) this.sqlManager.getObject("select CODIMP from IMPR where UPPER(CFIMP)=? and CGENIMP=?",
            new Object[] { cfImpresa.toUpperCase(), codein });
        if (StringUtils.isEmpty(codImp)) {
          codImp = this.gestioneImpresaLegaleRappresentante(codein, anagraficaAggiudicatari, cfImpresa);
        }
        dccW9AGGI.addColumn("W9AGGI.CODIMP", JdbcParametro.TIPO_TESTO, codImp);
        dccW9AGGI.addColumn("W9AGGI.ID_TIPOAGG", JdbcParametro.TIPO_NUMERICO, new Long(soggettiAggiudicatari[i].getIDTIPOAGG()));
        dccW9AGGI.addColumn("W9AGGI.FLAG_AVVALIMENTO", JdbcParametro.TIPO_TESTO, soggettiAggiudicatari[i].getFLAGAVVALIMENTO());
        
        if (soggettiAggiudicatari[i].isSetCFAUSILIARIA()) {
          String cfImpresaAusiliaria = soggettiAggiudicatari[i].getCFAUSILIARIA();
          String codImpAusiliaria = (String) this.sqlManager.getObject("select CODIMP from IMPR where UPPER(CFIMP)=? and CGENIMP=?",
              new Object[] { cfImpresaAusiliaria.toUpperCase(), codein });
          if (StringUtils.isEmpty(codImpAusiliaria)) {
            codImpAusiliaria = this.gestioneImpresaLegaleRappresentante(codein, anagraficaAggiudicatari, cfImpresaAusiliaria);
          }
          dccW9AGGI.addColumn("W9AGGI.CODIMP_AUSILIARIA", JdbcParametro.TIPO_TESTO, codImpAusiliaria);
        } else if (ditteAusiliarie != null && ditteAusiliarie.length > 0) {
          boolean trovato = false;
          for (int ii=0; ii < ditteAusiliarie.length && !trovato; ii++) {
            if (StringUtils.equalsIgnoreCase(ditteAusiliarie[ii].getCODICEFISCALEAGGIUDICATARIO(), cfImpresa)) {
              String cfImpresaAusiliaria = ditteAusiliarie[ii].getCODICEFISCALEAUSILIARIA();
              String codImpAusiliaria = (String) this.sqlManager.getObject("select CODIMP from IMPR where UPPER(CFIMP)=? and CGENIMP=?",
                  new Object[] { cfImpresaAusiliaria.toUpperCase(), codein });
              if (StringUtils.isEmpty(codImpAusiliaria)) {
              	codImpAusiliaria = this.gestioneImpresaLegaleRappresentante(codein, anagraficaAggiudicatari, cfImpresaAusiliaria);
              }
              dccW9AGGI.addColumn("W9AGGI.CODIMP_AUSILIARIA", JdbcParametro.TIPO_TESTO, codImpAusiliaria);
              trovato = true;
            }
          }
        }
        if (soggettiAggiudicatari[i].isSetRUOLO()) {
          dccW9AGGI.addColumn("W9AGGI.RUOLO", JdbcParametro.TIPO_NUMERICO, new Long(soggettiAggiudicatari[i].getRUOLO()));
        }
        if (soggettiAggiudicatari[i].isSetIDGRUPPO()) {
          dccW9AGGI.addColumn("W9AGGI.ID_GRUPPO", JdbcParametro.TIPO_NUMERICO, new Long(soggettiAggiudicatari[i].getIDGRUPPO()));
        }
        dccW9AGGI.insert("W9AGGI", this.sqlManager);
      }
    } else {
      logger.error("Metodo invocato con gli oggetti soggettiAggiudicatari e anagraficaAggiudicatari non valorizzati");
    }
  }

  /**
   * Inserimento dell'impresa e del legale rappresentante (IMPR - IMPLEG e TEIM).
   * 
   * @param codein
   * @param aggiudicatari
   * @param cfImpresa
   * @throws GestoreException
   * @throws SQLException
   */
  private String gestioneImpresaLegaleRappresentante(String codein, AggiudicatarioType[] aggiudicatari, String cfImpresa)
  		throws GestoreException, SQLException {
  	
    boolean trovato = false;
    String codiceImpresa = null;
    
    if (aggiudicatari != null && aggiudicatari.length > 0) {
      for (int j=0; j < aggiudicatari.length && !trovato; j++) {
        if (cfImpresa.equalsIgnoreCase(aggiudicatari[j].getCODICEFISCALEAGGIUDICATARIO().trim())) {
          List<DataColumn> listaCampi = new ArrayList<DataColumn>(); 
          DataColumn codimp = new DataColumn("IMPR.CODIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, null));
          listaCampi.add(codimp);

          DataColumn cgenimp = new DataColumn("IMPR.CGENIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, codein));
          listaCampi.add(cgenimp);
          
          if (StringUtils.isNotEmpty(aggiudicatari[j].getDENOMINAZIONE().trim())) {
            DataColumn nomimp = new DataColumn("IMPR.NOMIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getDENOMINAZIONE().trim(),0,60)));
            DataColumn nomest = new DataColumn("IMPR.NOMEST", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getDENOMINAZIONE().trim(),0,2000)));
            listaCampi.add(nomimp);
            listaCampi.add(nomest);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCODICEFISCALEAGGIUDICATARIO().trim())) {
            DataColumn cfimp = new DataColumn("IMPR.CFIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getCODICEFISCALEAGGIUDICATARIO(),0,16)));
            listaCampi.add(cfimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getPARTITAIVA().trim())) {
            DataColumn pivimp = new DataColumn("IMPR.PIVIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getPARTITAIVA().trim(),0,16)));
            listaCampi.add(pivimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCITTA().trim())) {
            DataColumn locimp = new DataColumn("IMPR.LOCIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getCITTA().trim(),0,60)));
            listaCampi.add(locimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCIVICO().trim())) {
            DataColumn nciimp = new DataColumn("IMPR.NCIIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getCIVICO().trim(),0,10)));
            listaCampi.add(nciimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getINDIRIZZO().trim())) {
            DataColumn indimp = new DataColumn("IMPR.INDIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getINDIRIZZO().trim(),0,60)));
            listaCampi.add(indimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCAP().trim())) {
            DataColumn capimp = new DataColumn("IMPR.CAPIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getCAP().trim(),0,5)));
            listaCampi.add(capimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getPROVINCIA().trim())) {
            DataColumn proimp = new DataColumn("IMPR.PROIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getPROVINCIA().trim(),0,2)));
            listaCampi.add(proimp);
          }
          
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCODICESTATO().trim())) {
            String statoEstero = "";
            try {
              statoEstero = (String) sqlManager.getObject("select tab2d1 from tab2 where tab2cod='W3z12' and tab2tip=?",
                  new Object[] { aggiudicatari[j].getCODICESTATO().trim() });
            } catch (SQLException e) {
              throw new GestoreException("Errore nella lettura della nazione dell'impresa", "getStatoEstero", e);
            }
            if (StringUtils.isNotEmpty(statoEstero)) {
              DataColumn nazimp = new DataColumn("IMPR.NAZIMP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, 
                  new Long(statoEstero)));
              listaCampi.add(nazimp);
              
            }
          } else {
            DataColumn nazimp = new DataColumn("IMPR.NAZIMP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
            listaCampi.add(nazimp);
          }
  
          DataColumnContainer dccImpresa = new DataColumnContainer(listaCampi.toArray(new DataColumn[0]));
          dccImpresa.getColumn("IMPR.CODIMP").setChiave(true);
          String strCodImp = this.geneManager.calcolaCodificaAutomatica("IMPR", "CODIMP");
          dccImpresa.setValue("IMPR.CODIMP", strCodImp);
          dccImpresa.insert("IMPR", this.sqlManager);
          
          codiceImpresa = new String(strCodImp);
  
          // Legale rappresentante
          DataColumn codtim = new DataColumn("TEIM.CODTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, strCodImp));
          DataColumn nometim = null;
          DataColumn cogtim  = null;
          DataColumn nomtim  = null;
          String nome = "";
          String cognome = "";
          String nomeCognome = "";
          if (StringUtils.isNotEmpty(aggiudicatari[j].getNOME())) {
            nome = StringUtils.substring(aggiudicatari[j].getNOME().trim(),0,80);
            nometim = new DataColumn("TEIM.NOMETIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, StringUtils.substring(aggiudicatari[j].getNOME().trim(),0,80)));
          } else {
          	nometim = new DataColumn("TEIM.NOMETIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, " "));
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCOGNOME())) {
            cognome = StringUtils.substring(aggiudicatari[j].getCOGNOME().trim(),0,80);
            cogtim = new DataColumn("TEIM.COGTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, StringUtils.substring(aggiudicatari[j].getCOGNOME().trim(),0,80)));
          } else {
          	cogtim = new DataColumn("TEIM.COGTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, " "));
          }
          
          nomeCognome = new String(nome.concat(" ").concat(cognome));
          if (StringUtils.isNotEmpty(nomeCognome)) {
            nomtim = new DataColumn("TEIM.NOMTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, StringUtils.substring(nomeCognome,0,160)));
          }
          DataColumn cftim = new DataColumn("TEIM.CFTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, StringUtils.substring(
          		aggiudicatari[j].getCFRAPPRESENTANTE().trim().toUpperCase(),0,16)));
          DataColumn cgentim = new DataColumn("TEIM.CGENTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, codein));
          DataColumnContainer dccTecnicoImpresa = new DataColumnContainer(new DataColumn[] { codtim, cogtim, nometim, nomtim, cftim, cgentim });
          dccTecnicoImpresa.getColumn("TEIM.CODTIM").setChiave(true);
          dccTecnicoImpresa.insert("TEIM", this.sqlManager);
          
          DataColumn codimp2 = new DataColumn("IMPLEG.CODIMP2", new JdbcParametro(JdbcParametro.TIPO_TESTO, strCodImp));
          DataColumn codleg  = new DataColumn("IMPLEG.CODLEG", new JdbcParametro(JdbcParametro.TIPO_TESTO, strCodImp));
          DataColumn nomleg  = new DataColumn("IMPLEG.NOMLEG", new JdbcParametro(JdbcParametro.TIPO_TESTO, StringUtils.substring(nomeCognome,0,160)));

          int idImpLeg = this.genChiaviManager.getNextId("IMPLEG");
          DataColumn id = new DataColumn("IMPLEG.ID", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(idImpLeg)));
          id.setChiave(true);
          
          DataColumnContainer dccLegaleRapp = new DataColumnContainer(new DataColumn[] { id, codimp2, codleg, nomleg } );
          dccLegaleRapp.insert("IMPLEG", this.sqlManager);
          
          trovato = true;
        }
      }
    }      
    
    return codiceImpresa;
  }
  
  
  
  
 
  public String insertTecnico(final String codUffInt, final String cfRup) throws SQLException, GestoreException {
    String codtec, nomeUtente, mailUtente, cfUtente;

    Long countTecni = (Long) this.sqlManager.getObject("select count(*) from TECNI where UPPER(CFTEC)=? ", 
        new Object[] { cfRup.toUpperCase() });
    
    if (countTecni != null && countTecni.longValue() > 0) {
      // Copia del tecnico da un'altra stazione appaltante
      String codiceTecnicoSorgente = (String) this.sqlManager.getObject(
          "select CODTEC from TECNI where UPPER(CFTEC)=?", new Object[] { cfRup.toUpperCase() });

      DataColumnContainer dccTecni = new DataColumnContainer(this.sqlManager, "TECNI",
          "select CODTEC, CGENTEI, CFTEC, CITTEC, COGTEI, NOMETEI, NOMTEC, INDTEC, NCITEC, LOCTEC, CAPTEC, TELTEC, " +
          "FAXTEC, EMATEC, PROTEC, SYSCON from TECNI where CODTEC=? ", new Object[] { codiceTecnicoSorgente });
    
      this.copiaValori(dccTecni);
      dccTecni.getColumn("TECNI.CODTEC").setChiave(true);
      codtec = this.geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
      dccTecni.getColumn("TECNI.CODTEC").setObjectValue(codtec);
      dccTecni.getColumn("TECNI.CGENTEI").setObjectValue(codUffInt);
      dccTecni.getColumn("TECNI.SYSCON").setObjectValue(null);
      dccTecni.insert("TECNI", this.sqlManager);
    } else {
      Long syscon = null;
      Vector< ? > datiUsrsys = this.sqlManager.getVector(
          "select SYSUTE, EMAIL, UPPER(SYSCF), SYSCON from USRSYS where UPPER(SYSCF)=?",
              new Object[] { cfRup.toUpperCase() });
      if (datiUsrsys != null && datiUsrsys.size() > 0) {
        nomeUtente = SqlManager.getValueFromVectorParam(datiUsrsys, 0).getStringValue();
        mailUtente = SqlManager.getValueFromVectorParam(datiUsrsys, 1).getStringValue();
        cfUtente = SqlManager.getValueFromVectorParam(datiUsrsys, 2).getStringValue();
        syscon = SqlManager.getValueFromVectorParam(datiUsrsys, 3).longValue();
      } else {
        nomeUtente = cfRup;
        mailUtente = "";
        cfUtente = cfRup;
      }
      
      codtec = this.geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
      
      if (syscon != null) {
        this.sqlManager.update("insert into TECNI (CODTEC,NOMTEC,CFTEC,CGENTEI,EMATEC,SYSCON) values (?,?,?,?,?,?)",
              new Object[] {codtec, nomeUtente, cfUtente, codUffInt, mailUtente, syscon } );
      } else {
        this.sqlManager.update("insert into TECNI (CODTEC,NOMTEC,CFTEC,CGENTEI,EMATEC) values (?,?,?,?,?)",
            new Object[] {codtec, nomeUtente, cfUtente, codUffInt, mailUtente } );
      }
    }
    
    return codtec;
  }
  

  
  private void copiaValori(DataColumnContainer dcc) throws GestoreException {
    HashMap<String, DataColumn> hm = dcc.getColonne();
    Set<String> set = hm.keySet();
    Iterator<String> iter = set.iterator();
    while(iter.hasNext()) {
      String tmp = iter.next();
      dcc.setValue(tmp, dcc.getColumn(tmp).getOriginalValue().getValue());
      dcc.getColumn(tmp).setObjectOriginalValue(null);
    }
  }
  
}