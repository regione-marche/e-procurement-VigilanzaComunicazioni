/*
 * Created on 07/nov/12
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */

package it.eldasoft.w9.bl;

import it.avlp.simog.massload.xmlbeans.AnomaliaType;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.utils.utility.UtilityNumeri;
import it.eldasoft.w9.common.CostantiSitatORT;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Classe di utilita' per l'invio o la ricezione di dati al server SFTP
 * dell'Autorita' di Vigilanza sui Lavori Pubblici.
 * 
 * @author Mirco.Franzoni
 * 
 */
public class AVCPManager {

  static Logger               logger               = Logger.getLogger(AVCPManager.class);

  private static final String PROP_SFTP_HOST       = CostantiSitatORT.SFTP_HOST;
  private static final String PROP_SFTP_PORT       = CostantiSitatORT.SFTP_PORT;
  private static final String PROP_SFTP_USERNAME   = CostantiSitatORT.SFTP_USER;
  private static final String PROP_SFTP_PASSWORD   = CostantiSitatORT.SFTP_PASS;
  private static final String PROP_SFTP_AREA_IN    = CostantiSitatORT.SFTP_AREAIN;
  private static final String PROP_SFTP_AREA_OUT   = CostantiSitatORT.SFTP_AREAOUT;
  private static final String PROP_SFTP_AREA_BKP   = CostantiSitatORT.SFTP_AREABKP;
  private static final String PROP_SFTP_FEEDBACK   = CostantiSitatORT.SFTP_FEEDBACK;
  private static final String PROP_SFTP_PROXY_HOST = CostantiSitatORT.SFTP_PROXY_HOST;
  private static final String PROP_SFTP_PROXY_PORT = CostantiSitatORT.SFTP_PROXY_PORT;

  private SqlManager          sqlManager;

  /**
   * 
   * @return
   */
  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  /**
   * 
   * @param sqlManager
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   * Invia i file XML contenuti nella cartella it.eldasoft.simog.xml.datapath nella cartella 
   * "IN" del server SFTP dell'Autorita' di vigilanza.
   * 
   * @return risultato del trasferimento
   * @throws GestoreException
   */
  public String inviaXML() throws GestoreException {

    if (logger.isDebugEnabled()) {
      logger.debug("inviaXML: inizio metodo");
    }

    if (logger.isInfoEnabled()) {
      logger.info("Avvio della procedura per l'invio delle esportazioni XML al server SFTP");
    }
    
    String result = "nessun file da inviare all'Autorita' di vigilanza";
    Session session = null;
    Channel channel = null;
    ChannelSftp channelSftp = null;

    String selectW9XML = "select max(numxml) from w9xml where codgara = ? and codlott = ?";
    String insertW9XML = "insert into w9xml(codgara, codlott, numxml, data_export, data_invio, xml) values(?, ?, ?, ?, ?, ?)";

    try {
      String PROP_SIMOG_XML_DATAPATH = CostantiSitatORT.SIMOG_XML_DATAPATH;
      String datapath = ConfigManager.getValore(PROP_SIMOG_XML_DATAPATH);
      
      if (StringUtils.isEmpty(datapath)) {
        throw new GestoreException(
            "Non e' definita la cartella da cui prelevare i file XML SIMOG da inviare",
            "inviaxml.nodatapath", null);
      } else {
        if (!datapath.endsWith(File.separator)) {
          datapath = datapath.concat(File.separator);
        }
      }
      
      File sourceDir = new File(datapath);

      //creo un filtro per i file XML
      FilenameFilter filefilterXml = new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return name.endsWith(".xml");
        }
      };
      
      File[] listFileXml = sourceDir.listFiles(filefilterXml);
      if (listFileXml.length > 0) {
      	// Gestione dei parametri
      	String areain = ConfigManager.getValore(PROP_SFTP_AREA_IN);
      	if (areain == null || "".equals(areain)) {
      		if (logger.isInfoEnabled()) {
      			logger.info("La cartella in cui copiare il file XML sul server SFTP non e' definita");
      		}
      		throw new GestoreException(
      				"La cartella in cui copiare il file XML sul server SFTP non e' definita",
      				"xmlsftp.areain", null);
      	}
      	// Creazione della sessione
      	session = this.gestioneConnessione(session);
      	// Creazione del canale e spostamento nell'area IN
      	try {
          channel = session.openChannel("sftp");
          channel.connect();
          channelSftp = (ChannelSftp) channel;
          channelSftp.cd(areain);
      	} catch (SftpException s) {
      	  if (logger.isInfoEnabled()) {
      		logger.info("La cartella in cui copiare il file XML non esiste sul server SFTP");
      	  }
      	  throw new GestoreException(
      	      "La cartella in cui copiare il file XML non esiste sul server SFTP",
      	      "xmlsftp.areaserverin", s);
      	}
      	int mode = ChannelSftp.OVERWRITE;

      	// inizio trasferimento dei file
      	for (File file:listFileXml) {
          // Copia del file nella cartella IN del server SFTP
          try {
            channelSftp.put(file.getAbsolutePath(), file.getName(), mode);

            // salva nel db
            // ricavo contatore
            String fileName = file.getName();
            int index = fileName.indexOf("_");
            String dataExport = fileName.substring(0, index);
            fileName = fileName.substring(index + 1);
            index = fileName.indexOf("_");
            Long codgara = new Long(fileName.substring(0, index));
            fileName = fileName.substring(index + 1);
            index = fileName.indexOf(".");
            Long codlotto = new Long(fileName.substring(0, index));
            Long conteggio = (Long) sqlManager.getObject(selectW9XML,
                new Object[] { codgara, codlotto });
            if (conteggio != null && conteggio.longValue() > 0) {
              conteggio++;
            } else {
              conteggio = new Long(1);
            }

            this.sqlManager.update(
                insertW9XML,
                new Object[] {
                    codgara,
                    codlotto,
                    conteggio,
                    UtilityDate.convertiData(dataExport, UtilityDate.FORMATO_AAAAMMGG),
                    new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
                    FileUtils.readFileToString(file) });

            file.delete();
          } catch (SftpException s) {
            if (logger.isInfoEnabled()) {
              logger.info("Si e' verificato un problema durante la copia dei file XML sul server SFTP");
            }
            throw new GestoreException(
                "Si e' verificato un problema durante la copia dei file XML sul server SFTP",
                "xmlsftp.puterror", s);
          }
      	}
      	result = "Trasferimento completato con successo";
      	channelSftp.quit();
      	channel.disconnect();
      	session.disconnect();
      	if (logger.isInfoEnabled()) {
      		logger.info("Conclusione dell'importazione e chiusura della connessione");
      	}
      } else {
      	result = "nessun file da inviare all'Autorita' di vigilanza";
      }
       
    } catch (Exception e) {
      if (channelSftp != null) {
        channelSftp.quit();
      }
      if (channel != null) {
        channel.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
      result = e.getMessage();
    }

    if (logger.isDebugEnabled()) {
    	logger.debug("inviaXML: fine metodo");
    }
    return result;
  }

  /**
   * Unzip del file archivio in una cartella specificata
   * 
   * @param zipFile File zip da scomprimere
   * @param unzipHomeParentDir Directory di destinazione 
   */
/*  private void unzipFileIntoDirectory(ZipFile zipFile, File unzipHomeParentDir) throws IOException {
    Enumeration<?> files = zipFile.entries();
    File f = null;
    FileOutputStream fos = null;
    while (files.hasMoreElements()) {
      try {
        ZipEntry entry = (ZipEntry) files.nextElement();
        InputStream eis = zipFile.getInputStream(entry);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
  
        f = new File(unzipHomeParentDir.getAbsolutePath() + File.separator + entry.getName());
        
        if (entry.isDirectory()) {
          f.mkdirs();
          continue;
        } else {
          f.getParentFile().mkdirs();
          f.createNewFile();
        }
        
        fos = new FileOutputStream(f);
  
        while ((bytesRead = eis.read(buffer)) != -1) {
          fos.write(buffer, 0, bytesRead);
        }
      } catch (IOException e) {
        throw e;
      } finally {
        if (fos != null) {
          try {
            fos.close();
          } catch (IOException e) {
            throw e;
         }
        }
      }
    }
    zipFile.close();
  } */
  
  /**
   * Legge tutti i file XML di feedback prodotti dall'Autorita' di Vigilanza e
   * memorizzati nella cartella "OUT" del server SFTP.
   * 
   * @throws GestoreException
   */
  public void importFeedback() throws GestoreException {

    if (logger.isDebugEnabled()) {
      logger.debug("importFeedback: inizio metodo");
    }

    if (logger.isInfoEnabled()) {
      logger.info("Avvio della procedura di importazione delle elaborazioni dal server SFTP");
    }
    
    Session session = null;
    Channel channel = null;
    ChannelSftp channelSftp = null;

    String selectW9XML = "select numxml from w9xml where codgara = ? and codlott = ? and data_export = ?";
    String updateW9XML = "update w9xml set data_feedback = ?, data_elaborazione = ?, num_elaborate = ?,"
        + " num_errore = ?, num_warning = ?, num_caricate = ?, feedback_analisi = ? where codgara = ? and codlott = ? and numxml = ?";
    String deleteW9XMLANOM = "delete from w9xmlanom where codgara = ? and codlott = ? and numxml = ?";
    String insertW9XMLANOM = "insert into w9xmlanom (codgara, codlott, numxml, num, codice, descrizione, livello, elemento, "
        + " scheda, progressivo, campo_xml, id_scheda_locale, id_scheda_simog)"
        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    try {

      // Gestione dei parametri
      String areaout = ConfigManager.getValore(PROP_SFTP_AREA_OUT);
      if (areaout == null || "".equals(areaout)) {
        if (logger.isInfoEnabled()) {
          logger.info("La cartella da cui prelevare i file XML dal server SFTP non e' definita");
        }
        throw new GestoreException(
            "La cartella da cui prelevare i file XML dal server SFTP non e' definita",
            "xmlsftp.areaout");
      }

      String areabkp = ConfigManager.getValore(PROP_SFTP_AREA_BKP);
      if (areabkp == null || "".equals(areabkp)) {
        if (logger.isInfoEnabled()) {
          logger.info("La cartella di backup in cui copiare i file XML elaborati sul server SFTP non e' definita");
        }
        throw new GestoreException(
            "La cartella di backup in cui copiare i file XML elaborati sul server SFTP non e' definita",
            "xmlsftp.areabkp");
      }

      String feedback = ConfigManager.getValore(PROP_SFTP_FEEDBACK);
      if (feedback == null || "".equals(feedback)) {
        if (logger.isInfoEnabled()) {
          logger.info("Non e' stata definita la stringa di ricerca sul nome dei file delle elaborazioni");
        }
        throw new GestoreException(
            "Non e' stata definita la stringa di ricerca sul nome dei file delle elaborazioni",
            "xmlsftp.nomifeedback");
      }

      // Creazione della sessione
      session = this.gestioneConnessione(session);

      // Creazione del canale e spostamento nell'area OUT
      if (logger.isInfoEnabled()) {
        logger.info("Spostamento all'interno della cartella "
            + areaout
            + " contenente i file XML");
      }
      
      try {
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        channelSftp.cd(areaout);
      } catch (SftpException s) {
        if (logger.isInfoEnabled()) {
          logger.info("La cartella da cui prelevare i file XML non esiste sul server SFTP");
        }
        throw new GestoreException(
            "La cartella da cui prelevare i file XML non esiste sul server SFTP",
            "xmlsftp.areaserverout", s);
      }

      // Lettura delle lista dei file
      if (logger.isInfoEnabled()) {
        logger.info("Lettura della lista dei file XML all'interno della cartella "
            + areaout);
      }
      Vector< ? > feedbackLs = channelSftp.ls(feedback);

      if (feedbackLs != null && feedbackLs.size() > 0) {
 
        // Per ogni elemento (file XML) della lista devo ricavare il codice della
        // gara (CODGARA), del lotto (CODLOTT) ed il numero di esportazione (NUMXML)
        // direttamente dal nome del file per poter impostare la chiave delle tabelle
        // W9XML e W9XMLANOM e procedere cosi' alla importazione dei risultati della
        // elaborazione
        
        if (logger.isInfoEnabled()) {
          logger.info("Inizio lettura di " + feedbackLs.size() + " file XML ");
        }
        
        for (int i = 0; i < feedbackLs.size(); i++) {
          Object objLsEntry = feedbackLs.elementAt(i);
          if (objLsEntry instanceof LsEntry) {
            String feedbackFileName = ((LsEntry) objLsEntry).getFilename();

            if (logger.isInfoEnabled()) {
              logger.info("Lettura del contenuto del file XML "
                  + feedbackFileName);
            }
            // Ricavo i valori per i campi chiave (SCHEDA_ID e NUMXML)
            Long codgara = null;
            Long codlotto = null;
            Date dataExport = null;

            //Il Formato del file e' il seguente NEW_AAAAMMGG_CODGARA_CODLOTTO.XML_FEEDBACK
            String fileName = feedbackFileName;
        	int index = fileName.indexOf("_");
        	if (index != -1) {
        	  fileName = fileName.substring(index + 1);
        	  index = fileName.indexOf("_");
        	  if (index != -1) {
        	    try {
        	      dataExport = UtilityDate.convertiData(fileName.substring(0, index),
        	          UtilityDate.FORMATO_AAAAMMGG);
        	    } catch (Throwable t) {
        	      ;
        	    }
                fileName = fileName.substring(index + 1);
                index = fileName.indexOf("_");
                if (index != -1) {
                  try {
                    codgara = new Long(fileName.substring(0, index));
                  } catch (Throwable t) {
                    ;
                  }
                  fileName = fileName.substring(index + 1);
                  index = fileName.indexOf(".");
                  if (index != -1) {
                    try {
                      codlotto = new Long(fileName.substring(0, index));
                    } catch (Throwable t) {
                      ;
                    }
                  }
                }
        	  }
        	}
        	
        	if (codgara != null && codlotto != null && dataExport != null) {
        	  // Lettura del file dal server SFTP
        	  ByteArrayOutputStream baos = new ByteArrayOutputStream();

              try {
                channelSftp.get(feedbackFileName, baos);
              } catch (SftpException s) {
                if (logger.isInfoEnabled()) {
                  logger.info("Si e' verificato un problema durante la lettura dei file XML dal server SFTP");
                }
                throw new GestoreException(
                    "Si e' verificato un problema durante la lettura dei file XML dal server SFTP",
                    "xmlsftp.geterror", s);
              }

              // Controllo esistenza esportazione ricavando il numxml
              Long numxml = (Long) sqlManager.getObject(selectW9XML,
                  new Object[] { codgara, codlotto, dataExport });
  
              if (numxml != null && numxml.longValue() > 0) {
                
                if (baos.size() > 0) {
              
                  // Lettura del contenuto del file XML di feedback
                  FeedBackDocument feedBackDocument = FeedBackDocument.Factory.parse(baos.toString());
  
                  // Controllo validazione
                  if (feedBackDocument.validate()) {
                    Calendar dataElaborazione =
                        feedBackDocument.getFeedBack().getInfoFlusso().getDATAELABORAZIONE();
                    Long numElaborate = new Long(
                        feedBackDocument.getFeedBack().getInfoFlusso().getNUMELABORATE());
                    Long numErrore = new Long(
                        feedBackDocument.getFeedBack().getInfoFlusso().getNUMERRORE());
                    Long numWarning = new Long(
                        feedBackDocument.getFeedBack().getInfoFlusso().getNUMWARNING());
                    Long numCaricate = new Long(
                        feedBackDocument.getFeedBack().getInfoFlusso().getNUMCARICATE());
  
                    // Aggiornamento dati principali del feedback
                    if (logger.isInfoEnabled()) {
                      logger.info("Inserimento, in base dati, dei dati generali dell'elaborazione");
                    }
                    this.sqlManager.update(updateW9XML, new Object[] {
                        new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
                        new Timestamp(dataElaborazione.getTimeInMillis()),
                        numElaborate, numErrore, numWarning, numCaricate, "2",
                        codgara, codlotto, numxml });
  
                    // Cancellazione della tabella W9XMLANOM
                    this.sqlManager.update(deleteW9XMLANOM, new Object[] {
                		  codgara, codlotto, numxml });
  
                    // Lettura dell'elemento AnomalieSchede
                    AnomalieSchede[] anomalieSchede = feedBackDocument.getFeedBack().getAnomalieSchedeArray();
  
                    if (anomalieSchede.length > 0) {
                      if (logger.isInfoEnabled()) {
                        logger.info("Inserimento, in base dati, dei dettagli dell'elaborazione");
                      }
    
                      // Codice CUI
                      /*if (feedBackDocument.getFeedBack().getAnomalieSchedeArray(0).isSetCUI()) {
                        String cui = feedBackDocument.getFeedBack().getAnomalieSchedeArray(
                            0).getCUI();
                        this.sqlManager.update(
                            "update w3fasi set cui = ? where scheda_id = ? and schedacompleta_id = ? and fase_esecuzione = 1",
                            new Object[] { cui, scheda_id, scheda_id });
                      }*/
    
                      int counter = 0;
                      for (int f = 0; f < anomalieSchede.length; f++) {
                        
                        // Lista delle segnalazioni
                        AnomaliaType[] anomalie = anomalieSchede[f].getAnomaliaArray();
                        
                        for (int j = 0; j < anomalie.length; j++) {
                          AnomaliaType anomalia = anomalie[j];
      
                          String codice = null;
                          if (anomalia.isSetCODICE()) {
                            codice = anomalia.getCODICE();
                          }
                          String descrizione = anomalia.getDESCRIZIONE();
                          String livello = (anomalia.getLIVELLO()).toString();
                          Long elemento = new Long(anomalia.getELEMENTO()); 
      
                          String scheda = null;
                          if (anomalia.isSetSCHEDA()) {
                            scheda = anomalia.getSCHEDA().toString();
                          }
                          Long progressivo = null;
                          if (anomalia.isSetPROGRESSIVO()) {
                            progressivo = new Long(anomalia.getPROGRESSIVO());
                          }
                          String campoXml = null;
                          if (anomalia.isSetCAMPOXML()) {
                            campoXml = anomalia.getCAMPOXML();
                          }
      
                          String idSchedaLocale = null;
                          if (anomalia.isSetIDSCHEDALOCALE()) {
                            idSchedaLocale = anomalia.getIDSCHEDALOCALE();
                          }
                          String idSchedaSimog = null;
                          if (anomalia.isSetIDSCHEDASIMOG()) {
                            idSchedaSimog = anomalia.getIDSCHEDASIMOG();
                          }
                          
                          counter++;
                          this.sqlManager.update(insertW9XMLANOM, new Object[] {
                              codgara, codlotto, numxml, new Long(counter), codice,
                              descrizione, livello, elemento, scheda, progressivo,
                              campoXml, idSchedaLocale, idSchedaSimog });
                        }
                      }
                    }
                  } else {
                    if (logger.isInfoEnabled()) {
                      logger.info("Il file "
                          + feedbackFileName
                          + " non puo' essere elaborato"
                          + " in quanto non rispetta le specifiche dello schema XSD previsto");
                    }
                  }
                } else {
                  if (logger.isInfoEnabled()) {
                    logger.info("Il file "
                        + feedbackFileName
                        + " non puo' essere importato perche' ha dimensione nulla");
                  }
                  
                  // Aggiornamento dati principali del feedback
                  if (logger.isInfoEnabled()) {
                    logger.info("Inserimento, in base dati, dei dati generali dell'elaborazione");
                  }
                  
                  this.sqlManager.update(updateW9XML, new Object[] {
                      new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
                      null, new Long(0), new Long(0), new Long(0), new Long(0), "2",
                      codgara, codlotto, numxml });
                  
                }
          	  } else {
                if (logger.isInfoEnabled()) {
                  logger.info("Il file "
                      + feedbackFileName
                      + " non puo' essere importato in quanto non esiste l'esportazione correlata");
                }
          	  }
            
              baos.close();

              if (logger.isInfoEnabled()) {
                logger.info("Rename del file " + feedbackFileName);
              }
              try {
                channelSftp.rename(feedbackFileName, feedbackFileName + "_bkp");
              } catch (SftpException s) {
                if (logger.isInfoEnabled()) {
                  logger.info("Errore durante il rename del file");
                }
                throw new GestoreException("Errore durante il rename del file",
                    "xmlsftp.rename", s);
              }
            }
          }
        }
      } else {
        if (logger.isInfoEnabled()) {
          logger.info("Non c'e' nessun file XML da leggere");
        }
      }

      channelSftp.quit();
      channel.disconnect();
      session.disconnect();
      if (logger.isInfoEnabled()) {
        logger.info("Conclusione dell'importazione e chiusura della connessione");
      }
    } catch (GestoreException e) {
      if (channelSftp != null) {
        channelSftp.quit();
      }
      if (channel != null) {
        channel.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
      throw e;
    } catch (SQLException e) {
      if (channelSftp != null) {
        channelSftp.quit();
      }
      if (channel != null) {
        channel.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
      throw new GestoreException(
          "Si e' verificato un errore nell'aggiornamento della base dati",
          "xmlsftp.sqlimportelaborazione", e);
    } catch (SftpException s) {
      if (channelSftp != null) {
        channelSftp.quit();
      }
      if (channel != null) {
        channel.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
      throw new GestoreException(
          "Errore generico nella lettura delle elaborazioni", "xmlsftp.get", s);
    } catch (Throwable t) {
      if (channelSftp != null) {
        channelSftp.quit();
      }
      if (channel != null) {
        channel.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
      throw new GestoreException(
          "Errore generico nella lettura delle elaborazioni", "xmlsftp.get", t);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("importFeedback: fine metodo");
    }
  }

  
  /**
   * Metodo utilizzato per la gestione della connessione al server SFTP.
   * 
   * @param session
   * @return
   * @throws GestoreException
   */
  private Session gestioneConnessione(Session session) throws GestoreException {

    JSch jsch = new JSch();

    if (logger.isInfoEnabled()) {
      logger.info("Connessione al server SFTP");
    }

    try {
      String host = ConfigManager.getValore(PROP_SFTP_HOST);
      if (host == null || "".equals(host)) {
        if (logger.isInfoEnabled()) {
          logger.info("L'indirizzo IP per la connessione al server SFTP non e' definito");
        }
        throw new GestoreException(
            "L'indirizzo IP per la connessione al server SFTP non e' definito",
            "xmlsftp.host");
      }

      Integer port = UtilityNumeri.convertiIntero(ConfigManager.getValore(PROP_SFTP_PORT));
      if (port == null) {
        if (logger.isInfoEnabled()) {
          logger.info("La porta di comunicazione per la connessione al server SFTP non e' definita");
        }
        throw new GestoreException(
            "La porta di comunicazione per la connessione al server SFTP non e' definita",
            "xmlsftp.port");
      }

      String username = ConfigManager.getValore(PROP_SFTP_USERNAME);
      if (username == null || "".equals(username)) {
        if (logger.isInfoEnabled()) {
          logger.info("L'utente di accesso al server SFTP non e' definito");
        }
        throw new GestoreException(
            "L'utente di accesso al server SFTP non e' definito",
            "xmlsftp.username");
      }

      String password = ConfigManager.getValore(PROP_SFTP_PASSWORD);
      if (password == null || "".equals(password)) {
        if (logger.isInfoEnabled()) {
          logger.info("La password di accesso al server SFTP non e' definita");
        }
        throw new GestoreException(
            "La password di accesso al server SFTP non e' definita",
            "xmlsftp.password");
      }

      // Gestione del proxy
      String proxyHost = ConfigManager.getValore(PROP_SFTP_PROXY_HOST);
      Integer proxyPort = UtilityNumeri.convertiIntero(ConfigManager.getValore(PROP_SFTP_PROXY_PORT));

      try {
        session = jsch.getSession(username, host, port.intValue());
        session.setPassword(password);

        if (proxyHost != null && !"".equals(proxyHost) && proxyPort != null) {
          session.setProxy(new ProxyHTTP(proxyHost, proxyPort.intValue()));
        }

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
      } catch (JSchException j) {
        logger.error("Errore di connessione al server SFTP", j);
        throw new GestoreException("Errore di connessione al server SFTP",
            "xmlsftp.connessione", j);
      }

    } catch (GestoreException e) {
      throw e;
    }
    return session;
  }

}
