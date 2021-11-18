/*
 * Created on 10/feb/11
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.bl.rest;

import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.BlobFile;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.common.CostantiServiziRest;
import it.eldasoft.w9.dao.W9FileDao;
import it.eldasoft.w9.dao.vo.rest.AggiudicatarioEntry;
import it.eldasoft.w9.dao.vo.rest.AllegatoEntry;
import it.eldasoft.w9.dao.vo.rest.AppaFornEntry;
import it.eldasoft.w9.dao.vo.rest.AppaLavEntry;
import it.eldasoft.w9.dao.vo.rest.CategoriaLottoEntry;
import it.eldasoft.w9.dao.vo.rest.CpvLottoEntry;
import it.eldasoft.w9.dao.vo.rest.DatiGeneraliStazioneAppaltanteEntry;
import it.eldasoft.w9.dao.vo.rest.ImpresaEntry;
import it.eldasoft.w9.dao.vo.rest.InserimentoResult;
import it.eldasoft.w9.dao.vo.rest.LoginResult;
import it.eldasoft.w9.dao.vo.rest.MotivazioneProceduraNegoziataEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicaAttoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicaAvvisoEntry;
import it.eldasoft.w9.dao.vo.rest.DatiGeneraliTecnicoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicaGaraEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicaLottoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicazioneAttoResult;
import it.eldasoft.w9.dao.vo.rest.PubblicazioneBandoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicazioneResult;
import it.eldasoft.w9.dao.vo.rest.StoricoAnagraficaEntry;
import it.eldasoft.w9.dao.vo.rest.ValidateEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.dm112011.FornitureServiziDM112011Entry;
import it.eldasoft.w9.dao.vo.rest.programmi.dm112011.ImmobileDM112011Entry;
import it.eldasoft.w9.dao.vo.rest.programmi.dm112011.InterventoDM112011Entry;
import it.eldasoft.w9.dao.vo.rest.programmi.dm112011.LavoroEconomiaEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.dm112011.PubblicaProgrammaDM112011Entry;
import it.eldasoft.w9.dao.vo.rest.programmi.fornitureservizi.AcquistoEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.fornitureservizi.AcquistoNonRipropostoEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.fornitureservizi.PubblicaProgrammaFornitureServiziEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.lavori.AltriDatiOperaIncompiutaEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.lavori.ImmobileEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.lavori.InterventoEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.lavori.InterventoNonRipropostoEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.lavori.OperaIncompiutaEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.lavori.PubblicaProgrammaLavoriEntry;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe di utilita' per l'interfacciamento con SCP
 * 
 * @author Mirco.Franzoni
 * 
 */
public class ScpManager {
	/** Logger. */
	static Logger logger = Logger.getLogger(ScpManager.class);

	private static final String selectUFFICI = "SELECT DENOM FROM UFFICI WHERE ID = ?";
	
	private static final String selectCENTRICOSTO = "SELECT CODCENTRO, DENOMCENTRO FROM CENTRICOSTO WHERE IDCENTRO = ?";
	
	private static final String updateAVVISOIdRicevuto = "UPDATE AVVISO SET ID_RICEVUTO = ? WHERE CODEIN = ? AND IDAVVISO = ? AND CODSISTEMA = ?";
	
	private static final String updateW9OUTBOX = "UPDATE W9OUTBOX SET DATINV = ?, FILE_JSON = ?, STATO = ?, MSG = ?, ID_RICEVUTO = ? WHERE IDCOMUN = ?";
	
	private static final String updateW9PUBBLICAZIONIIdRicevuto = "UPDATE W9PUBBLICAZIONI SET ID_RICEVUTO = ? WHERE CODGARA = ? AND NUM_PUBB = ?";
	private static final String updateW9GARAIdRicevuto = "UPDATE W9GARA SET ID_RICEVUTO = ? WHERE CODGARA = ?";
	private static final String updatePIATRIIdRicevuto = "UPDATE PIATRI SET ID_RICEVUTO = ? WHERE CONTRI = ? ";
    
	/** Manager SQL per le operazioni su database. */
	private SqlManager sqlManager;
	
	private GenChiaviManager genChiaviManager;

	/** DAO per la gestione dei file allegati. */
	private W9FileDao  w9FileDao;

	private GeneManager geneManager;
	
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	public void setGenChiaviManager(GenChiaviManager genChiaviManager) {
	  this.genChiaviManager = genChiaviManager;
	}
	  
	public void setW9FileDao(W9FileDao fileDao) {
	  this.w9FileDao = fileDao;
	}

	public void setGeneManager(GeneManager geneManager) {
	  this.geneManager = geneManager;
	}
	
	public int getTipoProgramma(final Long contri) {
		  int tipo = 3;
		  try {
			  Long norma = (Long)this.sqlManager.getObject("SELECT NORMA FROM PIATRI WHERE CONTRI = ?", new Object[] {contri});
			  if (norma != null && norma.equals(new Long(2))) {
				  Long tiprog = (Long)this.sqlManager.getObject("SELECT TIPROG FROM PIATRI WHERE CONTRI = ?", new Object[] {contri});
				  if (tiprog != null) {
					  tipo = tiprog.intValue();
				  }
			  }
		  } catch (Exception e) {
			  logger.error(e);
		  }
		  return tipo;
	  }
	 
	public void inserimentoFlussoAvviso(final PubblicaAvvisoEntry avviso, final ProfiloUtente profilo, final Long idAvviso, final String codein) throws GestoreException {

	    String insertW9Flussi = "INSERT INTO W9FLUSSI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, TINVIO2, DATINV, CODCOMP, AUTORE, CFSA , XML ) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	    
	    TransactionStatus status = null;
	    boolean commitTransaction = false;
	    try {
	    	Long tipoInvio = new Long(1);
	    	Long idFlusso = new Long(this.genChiaviManager.getNextId("W9FLUSSI"));
	    	Long countFlussi = (Long)this.sqlManager.getObject("select count(*) from W9FLUSSI where AREA = ? and KEY01 = ? and KEY02 = ? and KEY03 = ? and CFSA = ? ",
	    			new Object[] {new Long(3), idAvviso, new Long(1), new Long(989), avviso.getCodiceFiscaleSA()});
	    	if (countFlussi > 0) {
	    		tipoInvio = new Long(2);
	    	}
	    	status = this.sqlManager.startTransaction();
	    	ObjectMapper mapper = new ObjectMapper();
	      	this.sqlManager.update(insertW9Flussi, new Object[] { idFlusso, new Long(3), idAvviso, new Long(1),
	      			new Long(989), tipoInvio, new Timestamp(System.currentTimeMillis()), profilo.getId(), profilo.getNome(), avviso.getCodiceFiscaleSA(), mapper.writeValueAsString(avviso) });
	      	this.sqlManager.update(updateAVVISOIdRicevuto, new Object[] {avviso.getIdRicevuto(), codein, idAvviso, new Long(1)});
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

	public void inserimentoFlussoExArt29(final PubblicaAttoEntry pubblicazione, final ProfiloUtente profilo, final Long codGara, final Long numeroPubblicazione) throws GestoreException {

	    String insertW9Flussi = "INSERT INTO W9FLUSSI(IDFLUSSO, AREA, KEY01, KEY03, KEY04, TINVIO2, DATINV, CODCOMP, AUTORE, CFSA, XML ) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	    TransactionStatus status = null;
	    boolean commitTransaction = false;
	    try {
	    	Long tipoInvio = new Long(1);
	    	Long idFlusso = new Long(this.genChiaviManager.getNextId("W9FLUSSI"));
	    	Long countFlussi = (Long)this.sqlManager.getObject("select count(*) from W9FLUSSI where AREA = ? and KEY01 = ? and KEY03 = ? and KEY04 = ? ",
	    			new Object[] {new Long(2), codGara, new Long(901), numeroPubblicazione});
	    	if (countFlussi > 0) {
	    		tipoInvio = new Long(2);
	    	}
	    	status = this.sqlManager.startTransaction();
	    	ObjectMapper mapper = new ObjectMapper();
	    	
	      	this.sqlManager.update(insertW9Flussi, new Object[] { idFlusso, new Long(2), codGara,
	      			new Long(901), numeroPubblicazione, tipoInvio, new Timestamp(System.currentTimeMillis()), profilo.getId(), profilo.getNome(), pubblicazione.getGara().getCodiceFiscaleSA(),
	      			mapper.writeValueAsString(pubblicazione)});
	      	this.sqlManager.update(updateW9PUBBLICAZIONIIdRicevuto, new Object[] {pubblicazione.getIdRicevuto(), codGara, numeroPubblicazione});
	      	this.sqlManager.update(updateW9GARAIdRicevuto, new Object[] {pubblicazione.getGara().getIdRicevuto(), codGara});
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
	
	public void inserimentoFlussoProgrammaLavori(final PubblicaProgrammaLavoriEntry programma, final ProfiloUtente profilo, final Long contri) throws GestoreException {

	    String insertW9Flussi = "INSERT INTO W9FLUSSI(IDFLUSSO, AREA, KEY01, KEY03, TINVIO2, DATINV, CODCOMP, AUTORE, CFSA , XML ) VALUES(?,?,?,?,?,?,?,?,?,?)";
	    String updateIdRicevuto = "UPDATE PIATRI SET ID_RICEVUTO = ?, BRETRI = ? WHERE CONTRI = ? ";
	    TransactionStatus status = null;
	    boolean commitTransaction = false;
	    try {
	    	Long tipoInvio = new Long(1);
	    	Long idFlusso = new Long(this.genChiaviManager.getNextId("W9FLUSSI"));
	    	Long key03 = new Long(982);
	    	Long countFlussi = (Long)this.sqlManager.getObject("select count(*) from W9FLUSSI where AREA = ? and KEY01 = ? and KEY03 = ? and CFSA = ? ",
	    			new Object[] {new Long(4), contri, key03, programma.getCodiceFiscaleSA()});
	    	if (countFlussi > 0) {
	    		tipoInvio = new Long(2);
	    	}
	    	status = this.sqlManager.startTransaction();
	    	ObjectMapper mapper = new ObjectMapper();
	      	this.sqlManager.update(insertW9Flussi, new Object[] { idFlusso, new Long(4), contri,
	      			key03, tipoInvio, new Timestamp(System.currentTimeMillis()), profilo.getId(), profilo.getNome(), programma.getCodiceFiscaleSA(), mapper.writeValueAsString(programma) });
	      	this.sqlManager.update(updateIdRicevuto, new Object[] {programma.getIdRicevuto(), programma.getDescrizione(), contri});
	      	commitTransaction = true;
	    } catch (Exception e) {
	      throw new GestoreException(
	          "errore durante l'inserimento del flusso del programma",
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
	
	public void inserimentoFlussoProgrammaFornitureServizi(final PubblicaProgrammaFornitureServiziEntry programma, final ProfiloUtente profilo, final Long contri) throws GestoreException {

	    String insertW9Flussi = "INSERT INTO W9FLUSSI(IDFLUSSO, AREA, KEY01, KEY03, TINVIO2, DATINV, CODCOMP, AUTORE, CFSA , XML ) VALUES(?,?,?,?,?,?,?,?,?,?)";
	    String updateIdRicevuto = "UPDATE PIATRI SET ID_RICEVUTO = ?, BRETRI = ? WHERE CONTRI = ? ";
	    
	    TransactionStatus status = null;
	    boolean commitTransaction = false;
	    try {
	    	Long tipoInvio = new Long(1);
	    	Long idFlusso = new Long(this.genChiaviManager.getNextId("W9FLUSSI"));
	    	Long key03 = new Long(981);
	    	Long countFlussi = (Long)this.sqlManager.getObject("select count(*) from W9FLUSSI where AREA = ? and KEY01 = ? and KEY03 = ? and CFSA = ? ",
	    			new Object[] {new Long(4), contri, key03, programma.getCodiceFiscaleSA()});
	    	if (countFlussi > 0) {
	    		tipoInvio = new Long(2);
	    	}
	    	status = this.sqlManager.startTransaction();
	    	ObjectMapper mapper = new ObjectMapper();
	      	this.sqlManager.update(insertW9Flussi, new Object[] { idFlusso, new Long(4), contri,
	      			key03, tipoInvio, new Timestamp(System.currentTimeMillis()), profilo.getId(), profilo.getNome(), programma.getCodiceFiscaleSA(), mapper.writeValueAsString(programma) });
	      	this.sqlManager.update(updateIdRicevuto, new Object[] {programma.getIdRicevuto(), programma.getDescrizione(), contri});
	      	commitTransaction = true;
	    } catch (Exception e) {
	      throw new GestoreException(
	          "errore durante l'inserimento del flusso del programma",
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
	
	public void inserimentoFlussoProgrammaDM112011(final PubblicaProgrammaDM112011Entry programma, final ProfiloUtente profilo, final Long contri) throws GestoreException {

	    String insertW9Flussi = "INSERT INTO W9FLUSSI(IDFLUSSO, AREA, KEY01, KEY03, TINVIO2, DATINV, CODCOMP, AUTORE, CFSA , XML ) VALUES(?,?,?,?,?,?,?,?,?,?)";
	    String updateIdRicevuto = "UPDATE PIATRI SET ID_RICEVUTO = ?, BRETRI = ? WHERE CONTRI = ? ";
	    
	    TransactionStatus status = null;
	    boolean commitTransaction = false;
	    try {
	    	Long tipoInvio = new Long(1);
	    	Long idFlusso = new Long(this.genChiaviManager.getNextId("W9FLUSSI"));
	    	Long key03 = new Long(991);
	    	Long countFlussi = (Long)this.sqlManager.getObject("select count(*) from W9FLUSSI where AREA = ? and KEY01 = ? and KEY03 = ? and CFSA = ? ",
	    			new Object[] {new Long(4), contri, key03, programma.getCodiceFiscaleSA()});
	    	if (countFlussi > 0) {
	    		tipoInvio = new Long(2);
	    	}
	    	status = this.sqlManager.startTransaction();
	    	ObjectMapper mapper = new ObjectMapper();
	      	this.sqlManager.update(insertW9Flussi, new Object[] { idFlusso, new Long(4), contri,
	      			key03, tipoInvio, new Timestamp(System.currentTimeMillis()), profilo.getId(), profilo.getNome(), programma.getCodiceFiscaleSA(), mapper.writeValueAsString(programma) });
	      	this.sqlManager.update(updateIdRicevuto, new Object[] {programma.getIdRicevuto(), programma.getDescrizione(), contri});
	      	commitTransaction = true;
	    } catch (Exception e) {
	      throw new GestoreException(
	          "errore durante l'inserimento del flusso del programma",
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
	 * Metodo valorizzazione Dati Avviso
	 * 
	 * @param avviso
	 * 			oggetto da valorizzare
	 * @param codein
	 *            codice stazione appaltante
	 * @param idAvviso
	 *            id avviso
	 * @param codiceSistema
	 *            codice sistema
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	public void valorizzaAvviso(final PubblicaAvvisoEntry avviso,
			final String codein, final long idAvviso, final long codiceSistema)
	throws SQLException, ParseException {
		String sqlRecuperaAVVISO = "SELECT TIPOAVV, DATAAVV, DESCRI, CIG, "
			+ "DATASCADENZA, CUP, CUIINT, RUP, IDUFFICIO, INDSEDE, COMSEDE, PROSEDE, ID_RICEVUTO "
			+ "FROM AVVISO WHERE CODEIN = ? AND IDAVVISO = ? AND CODSISTEMA = ? ";

		List< ? > listaAvviso;
		List< ? > itemAvviso = new ArrayList<Object>();

		listaAvviso = sqlManager.getListVector(sqlRecuperaAVVISO, new Object[] {
				codein, idAvviso, codiceSistema });
		avviso.setCodiceFiscaleSA(this.getCFStazioneAppaltante(codein));
		//avviso.setCodiceSistema(codiceSistema);
		if (listaAvviso.size() > 0) {
			itemAvviso = (List< ? >) listaAvviso.get(0);

			if (verificaEsistenzaValore(itemAvviso.get(0))) {
				avviso.setTipologia(new Long(itemAvviso.get(0).toString()));
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(1))) {
				avviso.setData(itemAvviso.get(1).toString());
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(2))) {
				avviso.setDescrizione(itemAvviso.get(2).toString());
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(3))) {
				avviso.setCig(itemAvviso.get(3).toString());
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(4))) {
				avviso.setScadenza(itemAvviso.get(4).toString());
			}
			if (verificaEsistenzaValore(itemAvviso.get(5))) {
				avviso.setCup(itemAvviso.get(5).toString());
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(6))) {
				avviso.setCui(itemAvviso.get(6).toString());
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(7))) {
				DatiGeneraliTecnicoEntry tecnico = new DatiGeneraliTecnicoEntry();
				this.valorizzaTecnico(tecnico, itemAvviso.get(7).toString());
				avviso.setRup(tecnico);
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(8))) {
				//valorizza ufficio
				try {
					String denomUfficio = (String)sqlManager.getObject(selectUFFICI, new Object[] {new Long(itemAvviso.get(8).toString())});
					avviso.setUfficio(denomUfficio);
				} catch (Exception ex) {
					;
				}
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(9))) {
				avviso.setIndirizzo(itemAvviso.get(9).toString());
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(10))) {
				avviso.setComune(itemAvviso.get(10).toString());
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(11))) {
				avviso.setProvincia(itemAvviso.get(11).toString());
			}
			
			if (verificaEsistenzaValore(itemAvviso.get(12))) {
				avviso.setIdRicevuto(new Long(itemAvviso.get(12).toString()));
			}
			
			this.valorizzaDocumentiAvviso(avviso.getDocumenti(), codein, idAvviso, codiceSistema);
		}
	}

	/**
	 * Metodo valorizzazione Dati Pubblicazione
	 * 
	 * @param pubblicazione
	 * 			oggetto da valorizzare
	 * @param codGara
	 *            codice della gara
	 * @param numeroPubblicazione
	 *            numero pubblicazione gara
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	public void valorizzaAtto(final PubblicaAttoEntry pubblicazione,
			final long codGara, final long numeroPubblicazione)
	throws SQLException, ParseException {
		String sqlRecuperaW9PUBBLICAZIONE = "SELECT tipdoc, descriz, datapubb, datascad, "
			+ "data_decreto, data_provvedimento, num_provvedimento, data_stipula, num_repertorio, "
			+ "perc_ribasso_agg, perc_off_aumento, importo_aggiudicazione, data_verb_aggiudicazione, url_committente, url_eproc, id_ricevuto FROM W9PUBBLICAZIONI WHERE CODGARA = ? AND NUM_PUBB = ?";

		List< ? > listaPubblicazione;
		List< ? > itemPubblicazione = new ArrayList<Object>();

		listaPubblicazione = sqlManager.getListVector(sqlRecuperaW9PUBBLICAZIONE, new Object[] {
				codGara, numeroPubblicazione});
		
		if (listaPubblicazione.size() > 0) {
			itemPubblicazione = (List< ? >) listaPubblicazione.get(0);

			if (verificaEsistenzaValore(itemPubblicazione.get(0))) {
				pubblicazione.setTipoDocumento(new Long(itemPubblicazione.get(0).toString()));
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(1))) {
				pubblicazione.setEventualeSpecificazione(itemPubblicazione.get(1).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(2))) {
				pubblicazione.setDataPubblicazione(itemPubblicazione.get(2).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(3))) {
				pubblicazione.setDataScadenza(itemPubblicazione.get(3).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(4))) {
				pubblicazione.setDataDecreto(itemPubblicazione.get(4).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(5))) {
				pubblicazione.setDataProvvedimento(itemPubblicazione.get(5).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(6))) {
				pubblicazione.setNumeroProvvedimento(itemPubblicazione.get(6).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(7))) {
				pubblicazione.setDataStipula(itemPubblicazione.get(7).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(8))) {
				pubblicazione.setNumeroRepertorio(itemPubblicazione.get(8).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(9))) {
				pubblicazione.setRibassoAggiudicazione(new Double(itemPubblicazione.get(9).toString()));
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(10))) {
				pubblicazione.setOffertaAumento(new Double(itemPubblicazione.get(10).toString()));
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(11))) {
				pubblicazione.setImportoAggiudicazione(new Double(itemPubblicazione.get(11).toString()));
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(12))) {
				pubblicazione.setDataAggiudicazione(itemPubblicazione.get(12).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(13))) {
				pubblicazione.setUrlCommittente(itemPubblicazione.get(13).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(14))) {
				pubblicazione.setUrlEProcurement(itemPubblicazione.get(14).toString());
			}
			
			if (verificaEsistenzaValore(itemPubblicazione.get(15))) {
				pubblicazione.setIdRicevuto(new Long(itemPubblicazione.get(15).toString()));
			}
			
			PubblicaGaraEntry gara = new PubblicaGaraEntry();
			this.valorizzaGara(gara, codGara, numeroPubblicazione);
			pubblicazione.setGara(gara);
			this.valorizzaAggiudicatari(pubblicazione.getAggiudicatari(), codGara, numeroPubblicazione);
			this.valorizzaDocumentiPubblicazione(pubblicazione.getDocumenti(), codGara, numeroPubblicazione);
		}
	}

	/**
	 * Metodo valorizzazione Dati Programma Forniture Servizi
	 * 
	 * @param programma
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice univoco programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	public void valorizzaProgrammaFornitureServizi(final PubblicaProgrammaFornitureServiziEntry programma, final long contri)
	throws SQLException, ParseException {
		
		String sqlRecuperaPROGRAMMA = "SELECT ID, ANNTRI, BRETRI, CENINT, "
			+ "NAPTRI, DAPTRI, DPAPPROV, TITAPPROV, URLAPPROV, RESPRO, IDUFFICIO, ID_RICEVUTO "
			+ "FROM PIATRI WHERE CONTRI = ? ";

		List< ? > listaProgramma;
		List< ? > itemProgramma = new ArrayList<Object>();

		listaProgramma = sqlManager.getListVector(sqlRecuperaPROGRAMMA, new Object[] {contri });
		
		if (listaProgramma.size() > 0) {
			itemProgramma = (List< ? >) listaProgramma.get(0);

			if (verificaEsistenzaValore(itemProgramma.get(0))) {
				programma.setId(itemProgramma.get(0).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(1))) {
				programma.setAnno(new Long(itemProgramma.get(1).toString()));
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(2))) {
				programma.setDescrizione(itemProgramma.get(2).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(3))) {
				programma.setCodiceFiscaleSA(this.getCFStazioneAppaltante(itemProgramma.get(3).toString()));
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(4))) {
				programma.setNumeroApprovazione(itemProgramma.get(4).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(5))) {
				programma.setDataApprovazione(itemProgramma.get(5).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(6))) {
				programma.setDataPubblicazioneApprovazione(itemProgramma.get(6).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(7))) {
				programma.setTitoloAttoApprovazione(itemProgramma.get(7).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(8))) {
				programma.setUrlAttoApprovazione(itemProgramma.get(8).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(9))) {
				DatiGeneraliTecnicoEntry referente = new DatiGeneraliTecnicoEntry();
				this.valorizzaTecnico(referente, itemProgramma.get(9).toString());
				programma.setReferente(referente);
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(10))) {
				//valorizza ufficio
				try {
					String denomUfficio = (String)sqlManager.getObject(selectUFFICI, new Object[] {new Long(itemProgramma.get(10).toString())});
					programma.setUfficio(denomUfficio);
				} catch (Exception ex) {
					;
				}
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(11))) {
				programma.setIdRicevuto(new Long(itemProgramma.get(11).toString()));
			}
		
			this.valorizzaAcquisti(programma.getAcquisti(), contri);
			this.valorizzaAcquistiNonRiproposti(programma.getAcquistiNonRiproposti(), contri);
		}
	}
	
	/**
	 * Metodo valorizzazione acquisti non riproposti per programma forniture servizi
	 * 
	 * @param interventi
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaAcquistiNonRiproposti(final List<AcquistoNonRipropostoEntry> acquisti,
			final Long contri)
	throws SQLException, ParseException {
		String sqlRecuperaINRTRI = "SELECT CUIINT, CUPPRG, DESINT, TOTINT, PRGINT, MOTIVO "
			+ "FROM INRTRI WHERE CONTRI = ? ORDER BY CONINT";

		List< ? > listaAcquisti;
		List< ? > itemAcquisto = new ArrayList<Object>();

		listaAcquisti = sqlManager.getListVector(sqlRecuperaINRTRI, new Object[] {contri});
		
		if (listaAcquisti.size() > 0) {
			for (int i = 0; i < listaAcquisti.size(); i++) {
				itemAcquisto = (List< ? >) listaAcquisti.get(i);
				AcquistoNonRipropostoEntry acquisto = new AcquistoNonRipropostoEntry();
				if (verificaEsistenzaValore(itemAcquisto.get(0))) {
					acquisto.setCui(itemAcquisto.get(0).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(1))) {
					acquisto.setCup(itemAcquisto.get(1).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(2))) {
					acquisto.setDescrizione(itemAcquisto.get(2).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(3))) {
					acquisto.setImporto(Double.parseDouble(itemAcquisto.get(3).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(4))) {
					acquisto.setPriorita(new Long(itemAcquisto.get(4).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(5))) {
					acquisto.setMotivo(itemAcquisto.get(5).toString());
				}
				
				acquisti.add(acquisto);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione acquisti programma forniture servizi
	 * 
	 * @param acquisti
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaAcquisti(final List<AcquistoEntry> acquisti,
			final Long contri)
	throws SQLException, ParseException {
		String sqlRecuperaINTTRI = "SELECT CONINT, TIPOIN, CUIINT, CODINT, DESINT, ANNRIF, FLAG_CUP, CUPPRG, "
			+ "ACQALTINT, CUICOLL, CODCPV, COMINT, NUTS, QUANTIT, UNIMIS, PRGINT, LOTFUNZ, DURCONT, CONTESS, "
			+ "DV1TRI, DV2TRI, DV9TRI, MU1TRI, MU2TRI, MU9TRI, "
			+ "PR1TRI, PR2TRI, PR9TRI, BI1TRI, BI2TRI, BI9TRI, "
			+ "AP1TRI, AP2TRI, AP9TRI, IM1TRI, IM2TRI, IM9TRI, "
			+ "AL1TRI, AL2TRI, AL9TRI, SPESESOST, TCPINT, DELEGA, CODAUSA, SOGAGG, VARIATO, INTNOTE, CODRUP, "
			+ "IMPRFS, RG1TRI, IMPALT, MESEIN, DIRGEN, STRUOP, REFERE, RESPUF, PROAFF, "
			+ "ACQVERDI, NORRIF, AVOGGETT, AVCPV, AVIMPNET, AVIVA, AVIMPTOT, "
			+ "MATRIC, MROGGETT, MRCPV, MRIMPNET, MRIVA, MRIMPTOT, "
			+ "IV1TRI, IV2TRI, IV9TRI, COPFIN, VALUTAZIONE "
			+ "FROM INTTRI WHERE CONTRI = ? ORDER BY CONINT";

		List< ? > listaAcquisti;
		List< ? > itemAcquisto = new ArrayList<Object>();

		listaAcquisti = sqlManager.getListVector(sqlRecuperaINTTRI, new Object[] {contri});
		
		if (listaAcquisti.size() > 0) {
			for (int i = 0; i < listaAcquisti.size(); i++) {
				itemAcquisto = (List< ? >) listaAcquisti.get(i);
				AcquistoEntry acquisto = new AcquistoEntry();
				
				if (verificaEsistenzaValore(itemAcquisto.get(1))) {
					acquisto.setSettore(itemAcquisto.get(1).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(2))) {
					acquisto.setCui(itemAcquisto.get(2).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(3))) {
					acquisto.setCodiceInterno(itemAcquisto.get(3).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(4))) {
					acquisto.setDescrizione(itemAcquisto.get(4).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(5))) {
					acquisto.setAnno(new Long(itemAcquisto.get(5).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(6))) {
					acquisto.setEsenteCup(itemAcquisto.get(6).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(7))) {
					acquisto.setCup(itemAcquisto.get(7).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(8))) {
					acquisto.setAcquistoRicompreso(new Long(itemAcquisto.get(8).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(9))) {
					acquisto.setCuiCollegato(itemAcquisto.get(9).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(10))) {
					acquisto.setCpv(itemAcquisto.get(10).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(11))) {
					acquisto.setIstat(itemAcquisto.get(11).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(12))) {
					acquisto.setNuts(itemAcquisto.get(12).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(13))) {
					acquisto.setQuantita(Double.parseDouble(itemAcquisto.get(13).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(14))) {
					acquisto.setUnitaMisura(new Long(itemAcquisto.get(14).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(15))) {
					acquisto.setPriorita(new Long(itemAcquisto.get(15).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(16))) {
					acquisto.setLottoFunzionale(itemAcquisto.get(16).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(17))) {
					acquisto.setDurataInMesi(new Long(itemAcquisto.get(17).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(18))) {
					acquisto.setNuovoAffidamento(itemAcquisto.get(18).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(19))) {
					acquisto.setRisorseVincolatePerLegge1(Double.parseDouble(itemAcquisto.get(19).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(20))) {
					acquisto.setRisorseVincolatePerLegge2(Double.parseDouble(itemAcquisto.get(20).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(21))) {
					acquisto.setRisorseVincolatePerLeggeSucc(Double.parseDouble(itemAcquisto.get(21).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(22))) {
					acquisto.setRisorseMutuo1(Double.parseDouble(itemAcquisto.get(22).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(23))) {
					acquisto.setRisorseMutuo2(Double.parseDouble(itemAcquisto.get(23).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(24))) {
					acquisto.setRisorseMutuoSucc(Double.parseDouble(itemAcquisto.get(24).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(25))) {
					acquisto.setRisorsePrivati1(Double.parseDouble(itemAcquisto.get(25).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(26))) {
					acquisto.setRisorsePrivati2(Double.parseDouble(itemAcquisto.get(26).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(27))) {
					acquisto.setRisorsePrivatiSucc(Double.parseDouble(itemAcquisto.get(27).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(28))) {
					acquisto.setRisorseBilancio1(Double.parseDouble(itemAcquisto.get(28).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(29))) {
					acquisto.setRisorseBilancio2(Double.parseDouble(itemAcquisto.get(29).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(30))) {
					acquisto.setRisorseBilancioSucc(Double.parseDouble(itemAcquisto.get(30).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(31))) {
					acquisto.setRisorseArt3_1(Double.parseDouble(itemAcquisto.get(31).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(32))) {
					acquisto.setRisorseArt3_2(Double.parseDouble(itemAcquisto.get(32).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(33))) {
					acquisto.setRisorseArt3_Succ(Double.parseDouble(itemAcquisto.get(33).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(34))) {
					acquisto.setRisorseImmobili1(Double.parseDouble(itemAcquisto.get(34).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(35))) {
					acquisto.setRisorseImmobili2(Double.parseDouble(itemAcquisto.get(35).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(36))) {
					acquisto.setRisorseImmobiliSucc(Double.parseDouble(itemAcquisto.get(36).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(37))) {
					acquisto.setRisorseAltro1(Double.parseDouble(itemAcquisto.get(37).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(38))) {
					acquisto.setRisorseAltro2(Double.parseDouble(itemAcquisto.get(38).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(39))) {
					acquisto.setRisorseAltroSucc(Double.parseDouble(itemAcquisto.get(39).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(40))) {
					acquisto.setSpeseSostenute(Double.parseDouble(itemAcquisto.get(40).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(41))) {
					acquisto.setTipologiaCapitalePrivato(itemAcquisto.get(41).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(42))) {
					acquisto.setDelega(itemAcquisto.get(42).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(43))) {
					acquisto.setCodiceSoggettoDelegato(itemAcquisto.get(43).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(44))) {
					acquisto.setNomeSoggettoDelegato(itemAcquisto.get(44).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(45))) {
					acquisto.setVariato(new Long(itemAcquisto.get(45).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(46))) {
					acquisto.setNote(itemAcquisto.get(46).toString());
				}
				//il 47 è il CODICE RUP
				if (verificaEsistenzaValore(itemAcquisto.get(48))) {
					acquisto.setImportoRisorseFinanziarie(Double.parseDouble(itemAcquisto.get(48).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(49))) {
					acquisto.setImportoRisorseFinanziarieRegionali(Double.parseDouble(itemAcquisto.get(49).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(50))) {
					acquisto.setImportoRisorseFinanziarieAltro(Double.parseDouble(itemAcquisto.get(50).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(51))) {
					acquisto.setMeseAvvioProcedura(new Long(itemAcquisto.get(51).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(52))) {
					acquisto.setDirezioneGenerale(itemAcquisto.get(52).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(53))) {
					acquisto.setStrutturaOperativa(itemAcquisto.get(53).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(54))) {
					acquisto.setReferenteDati(itemAcquisto.get(54).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(55))) {
					acquisto.setDirigenteResponsabile(itemAcquisto.get(55).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(56))) {
					acquisto.setProceduraAffidamento(new Long(itemAcquisto.get(56).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(57))) {
					acquisto.setAcquistoVerdi(new Long(itemAcquisto.get(57).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(58))) {
					acquisto.setNormativaRiferimento(itemAcquisto.get(58).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(59))) {
					acquisto.setOggettoVerdi(itemAcquisto.get(59).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(60))) {
					acquisto.setCpvVerdi(itemAcquisto.get(60).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(61))) {
					acquisto.setImportoNettoIvaVerdi(Double.parseDouble(itemAcquisto.get(61).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(62))) {
					acquisto.setImportoIvaVerdi(Double.parseDouble(itemAcquisto.get(62).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(63))) {
					acquisto.setImportoTotVerdi(Double.parseDouble(itemAcquisto.get(63).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(64))) {
					acquisto.setAcquistoMaterialiRiciclati(new Long(itemAcquisto.get(64).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(65))) {
					acquisto.setOggettoMatRic(itemAcquisto.get(65).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(66))) {
					acquisto.setCpvMatRic(itemAcquisto.get(66).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(67))) {
					acquisto.setImportoNettoIvaMatRic(Double.parseDouble(itemAcquisto.get(67).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(68))) {
					acquisto.setImportoIvaMatRic(Double.parseDouble(itemAcquisto.get(68).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(69))) {
					acquisto.setImportoTotMatRic(Double.parseDouble(itemAcquisto.get(69).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(70))) {
					acquisto.setImportoIva1(Double.parseDouble(itemAcquisto.get(70).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(71))) {
					acquisto.setImportoIva2(Double.parseDouble(itemAcquisto.get(71).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(72))) {
					acquisto.setImportoIvaSucc(Double.parseDouble(itemAcquisto.get(72).toString()));
				}
				if (verificaEsistenzaValore(itemAcquisto.get(73))) {
					acquisto.setCoperturaFinanziaria(itemAcquisto.get(73).toString());
				}
				if (verificaEsistenzaValore(itemAcquisto.get(74))) {
					acquisto.setValutazione(new Long(itemAcquisto.get(74).toString()));
				}
				
				if (verificaEsistenzaValore(itemAcquisto.get(47))) {
					DatiGeneraliTecnicoEntry rup = new DatiGeneraliTecnicoEntry();
					this.valorizzaTecnico(rup, itemAcquisto.get(47).toString());
					acquisto.setRup(rup);
				}
				
				acquisti.add(acquisto);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Dati Programma Lavori
	 * 
	 * @param programma
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice univoco programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	public void valorizzaProgrammaLavori(final PubblicaProgrammaLavoriEntry programma, final long contri)
	throws SQLException, ParseException {
		
		String sqlRecuperaPROGRAMMA = "SELECT ID, ANNTRI, BRETRI, CENINT, "
			+ "NAPTRI, DAPTRI, DPAPPROV, TITAPPROV, URLAPPROV, NADOZI, DADOZI, DPADOZI, TITADOZI, URLADOZI, RESPRO, IDUFFICIO, ID_RICEVUTO "
			+ "FROM PIATRI WHERE CONTRI = ? ";

		List< ? > listaProgramma;
		List< ? > itemProgramma = new ArrayList<Object>();

		listaProgramma = sqlManager.getListVector(sqlRecuperaPROGRAMMA, new Object[] {contri });
		
		if (listaProgramma.size() > 0) {
			itemProgramma = (List< ? >) listaProgramma.get(0);

			if (verificaEsistenzaValore(itemProgramma.get(0))) {
				programma.setId(itemProgramma.get(0).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(1))) {
				programma.setAnno(new Long(itemProgramma.get(1).toString()));
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(2))) {
				programma.setDescrizione(itemProgramma.get(2).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(3))) {
				programma.setCodiceFiscaleSA(this.getCFStazioneAppaltante(itemProgramma.get(3).toString()));
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(4))) {
				programma.setNumeroApprovazione(itemProgramma.get(4).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(5))) {
				programma.setDataApprovazione(itemProgramma.get(5).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(6))) {
				programma.setDataPubblicazioneApprovazione(itemProgramma.get(6).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(7))) {
				programma.setTitoloAttoApprovazione(itemProgramma.get(7).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(8))) {
				programma.setUrlAttoApprovazione(itemProgramma.get(8).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(9))) {
				programma.setNumeroAdozione(itemProgramma.get(9).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(10))) {
				programma.setDataAdozione(itemProgramma.get(10).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(11))) {
				programma.setDataPubblicazioneAdozione(itemProgramma.get(11).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(12))) {
				programma.setTitoloAttoAdozione(itemProgramma.get(12).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(13))) {
				programma.setUrlAttoAdozione(itemProgramma.get(13).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(14))) {
				DatiGeneraliTecnicoEntry referente = new DatiGeneraliTecnicoEntry();
				this.valorizzaTecnico(referente, itemProgramma.get(14).toString());
				programma.setReferente(referente);
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(15))) {
				//valorizza ufficio
				try {
					String denomUfficio = (String)sqlManager.getObject(selectUFFICI, new Object[] {new Long(itemProgramma.get(15).toString())});
					programma.setUfficio(denomUfficio);
				} catch (Exception ex) {
					;
				}
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(16))) {
				programma.setIdRicevuto(new Long(itemProgramma.get(16).toString()));
			}
		
			this.valorizzaOpereIncompiute(programma.getOpereIncompiute(), contri);
			this.valorizzaInterventi(programma.getInterventi(), contri);
			this.valorizzaInterventiNonRiproposti(programma.getInterventiNonRiproposti(), contri);
		}
	}
	
	/**
	 * Metodo valorizzazione interventi lavori
	 * 
	 * @param interventi
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaInterventi(final List<InterventoEntry> interventi,
			final Long contri)
	throws SQLException, ParseException {
		String sqlRecuperaINTTRI = "SELECT CONINT, NPROGINT, CUIINT, CODINT, DESINT, ANNRIF, FLAG_CUP, CUPPRG, "
			+ "CODCPV, COMINT, NUTS, PRGINT, LOTFUNZ, LAVCOMPL, SEZINT, INTERV, SCAMUT, FIINTR, "
			+ "URCINT, APCINT, STAPRO, "
			+ "DV1TRI, DV2TRI, DV3TRI, DV9TRI, MU1TRI, MU2TRI, MU3TRI, MU9TRI, "
			+ "PR1TRI, PR2TRI, PR3TRI, PR9TRI, BI1TRI, BI2TRI, BI3TRI, BI9TRI, "
			+ "AP1TRI, AP2TRI, AP3TRI, AP9TRI, IM1TRI, IM2TRI, IM3TRI, IM9TRI, "
			+ "AL1TRI, AL2TRI, AL3TRI, AL9TRI, SPESESOST, TCPINT, DELEGA, CODAUSA, SOGAGG, VARIATO, INTNOTE, CODRUP, "
			+ "MESEIN, DIRGEN, STRUOP, REFERE, RESPUF, PROAFF, "
			+ "ACQVERDI, NORRIF, AVOGGETT, AVCPV, AVIMPNET, AVIVA, AVIMPTOT, "
			+ "MATRIC, MROGGETT, MRCPV, MRIMPNET, MRIVA, MRIMPTOT, "
			+ "COPFIN, VALUTAZIONE "
			+ "FROM INTTRI WHERE CONTRI = ? ORDER BY CONINT";

		List< ? > listaInterventi;
		List< ? > itemIntervento = new ArrayList<Object>();

		listaInterventi = sqlManager.getListVector(sqlRecuperaINTTRI, new Object[] {contri});
		
		if (listaInterventi.size() > 0) {
			for (int i = 0; i < listaInterventi.size(); i++) {
				itemIntervento = (List< ? >) listaInterventi.get(i);
				InterventoEntry intervento = new InterventoEntry();
				
				if (verificaEsistenzaValore(itemIntervento.get(1))) {
					intervento.setNumeroProgressivo(new Long(itemIntervento.get(1).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(2))) {
					intervento.setCui(itemIntervento.get(2).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(3))) {
					intervento.setCodiceInterno(itemIntervento.get(3).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(4))) {
					intervento.setDescrizione(itemIntervento.get(4).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(5))) {
					intervento.setAnno(new Long(itemIntervento.get(5).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(6))) {
					intervento.setEsenteCup(itemIntervento.get(6).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(7))) {
					intervento.setCup(itemIntervento.get(7).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(8))) {
					intervento.setCpv(itemIntervento.get(8).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(9))) {
					intervento.setIstat(itemIntervento.get(9).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(10))) {
					intervento.setNuts(itemIntervento.get(10).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(11))) {
					intervento.setPriorita(new Long(itemIntervento.get(11).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(12))) {
					intervento.setLottoFunzionale(itemIntervento.get(12).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(13))) {
					intervento.setLavoroComplesso(itemIntervento.get(13).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(14))) {
					intervento.setTipologia(itemIntervento.get(14).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(15))) {
					intervento.setCategoria(itemIntervento.get(15).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(16))) {
					intervento.setScadenzaFinanziamento(itemIntervento.get(16).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(17))) {
					intervento.setFinalita(itemIntervento.get(17).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(18))) {
					intervento.setConformitaUrbanistica(itemIntervento.get(18).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(19))) {
					intervento.setConformitaAmbientale(itemIntervento.get(19).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(20))) {
					intervento.setStatoProgettazione(itemIntervento.get(20).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(21))) {
					intervento.setRisorseVincolatePerLegge1(Double.parseDouble(itemIntervento.get(21).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(22))) {
					intervento.setRisorseVincolatePerLegge2(Double.parseDouble(itemIntervento.get(22).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(23))) {
					intervento.setRisorseVincolatePerLegge3(Double.parseDouble(itemIntervento.get(23).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(24))) {
					intervento.setRisorseVincolatePerLeggeSucc(Double.parseDouble(itemIntervento.get(24).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(25))) {
					intervento.setRisorseMutuo1(Double.parseDouble(itemIntervento.get(25).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(26))) {
					intervento.setRisorseMutuo2(Double.parseDouble(itemIntervento.get(26).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(27))) {
					intervento.setRisorseMutuo3(Double.parseDouble(itemIntervento.get(27).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(28))) {
					intervento.setRisorseMutuoSucc(Double.parseDouble(itemIntervento.get(28).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(29))) {
					intervento.setRisorsePrivati1(Double.parseDouble(itemIntervento.get(29).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(30))) {
					intervento.setRisorsePrivati2(Double.parseDouble(itemIntervento.get(30).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(31))) {
					intervento.setRisorsePrivati3(Double.parseDouble(itemIntervento.get(31).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(32))) {
					intervento.setRisorsePrivatiSucc(Double.parseDouble(itemIntervento.get(32).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(33))) {
					intervento.setRisorseBilancio1(Double.parseDouble(itemIntervento.get(33).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(34))) {
					intervento.setRisorseBilancio2(Double.parseDouble(itemIntervento.get(34).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(35))) {
					intervento.setRisorseBilancio3(Double.parseDouble(itemIntervento.get(35).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(36))) {
					intervento.setRisorseBilancioSucc(Double.parseDouble(itemIntervento.get(36).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(37))) {
					intervento.setRisorseArt3_1(Double.parseDouble(itemIntervento.get(37).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(38))) {
					intervento.setRisorseArt3_2(Double.parseDouble(itemIntervento.get(38).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(39))) {
					intervento.setRisorseArt3_3(Double.parseDouble(itemIntervento.get(39).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(40))) {
					intervento.setRisorseArt3_Succ(Double.parseDouble(itemIntervento.get(40).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(41))) {
					intervento.setRisorseImmobili1(Double.parseDouble(itemIntervento.get(41).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(42))) {
					intervento.setRisorseImmobili2(Double.parseDouble(itemIntervento.get(42).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(43))) {
					intervento.setRisorseImmobili3(Double.parseDouble(itemIntervento.get(43).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(44))) {
					intervento.setRisorseImmobiliSucc(Double.parseDouble(itemIntervento.get(44).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(45))) {
					intervento.setRisorseAltro1(Double.parseDouble(itemIntervento.get(45).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(46))) {
					intervento.setRisorseAltro2(Double.parseDouble(itemIntervento.get(46).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(47))) {
					intervento.setRisorseAltro3(Double.parseDouble(itemIntervento.get(47).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(48))) {
					intervento.setRisorseAltroSucc(Double.parseDouble(itemIntervento.get(48).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(49))) {
					intervento.setSpeseSostenute(Double.parseDouble(itemIntervento.get(49).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(50))) {
					intervento.setTipologiaCapitalePrivato(itemIntervento.get(50).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(51))) {
					intervento.setDelega(itemIntervento.get(51).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(52))) {
					intervento.setCodiceSoggettoDelegato(itemIntervento.get(52).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(53))) {
					intervento.setNomeSoggettoDelegato(itemIntervento.get(53).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(54))) {
					intervento.setVariato(new Long(itemIntervento.get(54).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(55))) {
					intervento.setNote(itemIntervento.get(55).toString());
				}
				//il 56 è il CODICE RUP
				if (verificaEsistenzaValore(itemIntervento.get(57))) {
					intervento.setMeseAvvioProcedura(new Long(itemIntervento.get(57).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(58))) {
					intervento.setDirezioneGenerale(itemIntervento.get(58).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(59))) {
					intervento.setStrutturaOperativa(itemIntervento.get(59).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(60))) {
					intervento.setReferenteDati(itemIntervento.get(60).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(61))) {
					intervento.setDirigenteResponsabile(itemIntervento.get(61).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(62))) {
					intervento.setProceduraAffidamento(new Long(itemIntervento.get(62).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(63))) {
					intervento.setAcquistoVerdi(new Long(itemIntervento.get(63).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(64))) {
					intervento.setNormativaRiferimento(itemIntervento.get(64).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(65))) {
					intervento.setOggettoVerdi(itemIntervento.get(65).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(66))) {
					intervento.setCpvVerdi(itemIntervento.get(66).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(67))) {
					intervento.setImportoNettoIvaVerdi(Double.parseDouble(itemIntervento.get(67).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(68))) {
					intervento.setImportoIvaVerdi(Double.parseDouble(itemIntervento.get(68).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(69))) {
					intervento.setImportoTotVerdi(Double.parseDouble(itemIntervento.get(69).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(70))) {
					intervento.setAcquistoMaterialiRiciclati(new Long(itemIntervento.get(70).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(71))) {
					intervento.setOggettoMatRic(itemIntervento.get(71).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(72))) {
					intervento.setCpvMatRic(itemIntervento.get(72).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(73))) {
					intervento.setImportoNettoIvaMatRic(Double.parseDouble(itemIntervento.get(73).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(74))) {
					intervento.setImportoIvaMatRic(Double.parseDouble(itemIntervento.get(74).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(75))) {
					intervento.setImportoTotMatRic(Double.parseDouble(itemIntervento.get(75).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(76))) {
					intervento.setCoperturaFinanziaria(itemIntervento.get(76).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(77))) {
					intervento.setValutazione(new Long(itemIntervento.get(77).toString()));
				}

				if (verificaEsistenzaValore(itemIntervento.get(56))) {
					DatiGeneraliTecnicoEntry rup = new DatiGeneraliTecnicoEntry();
					this.valorizzaTecnico(rup, itemIntervento.get(56).toString());
					intervento.setRup(rup);
				}
				
				this.valorizzaImmobili(intervento.getImmobili(), contri, new Long(itemIntervento.get(0).toString()), null);
				interventi.add(intervento);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione interventi non riproposti per programma lavori
	 * 
	 * @param interventi
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaInterventiNonRiproposti(final List<InterventoNonRipropostoEntry> interventi,
			final Long contri)
	throws SQLException, ParseException {
		String sqlRecuperaINRTRI = "SELECT CUIINT, CUPPRG, DESINT, TOTINT, PRGINT, MOTIVO "
			+ "FROM INRTRI WHERE CONTRI = ? ORDER BY CONINT";

		List< ? > listaInterventi;
		List< ? > itemIntervento = new ArrayList<Object>();

		listaInterventi = sqlManager.getListVector(sqlRecuperaINRTRI, new Object[] {contri});
		
		if (listaInterventi.size() > 0) {
			for (int i = 0; i < listaInterventi.size(); i++) {
				itemIntervento = (List< ? >) listaInterventi.get(i);
				InterventoNonRipropostoEntry intervento = new InterventoNonRipropostoEntry();
				if (verificaEsistenzaValore(itemIntervento.get(0))) {
					intervento.setCui(itemIntervento.get(0).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(1))) {
					intervento.setCup(itemIntervento.get(1).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(2))) {
					intervento.setDescrizione(itemIntervento.get(2).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(3))) {
					intervento.setImporto(Double.parseDouble(itemIntervento.get(3).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(4))) {
					intervento.setPriorita(new Long(itemIntervento.get(4).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(5))) {
					intervento.setMotivo(itemIntervento.get(5).toString());
				}
				
				interventi.add(intervento);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione opere incompiute per programma lavori
	 * 
	 * @param opere
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaOpereIncompiute(final List<OperaIncompiutaEntry> opere,
			final Long contri)
	throws SQLException, ParseException {
		String sqlRecuperaOITRI = "SELECT NUMOI, CUP, DESCRIZIONE, DETERMINAZIONI, AMBITOINT, " //  0, .. ,4
			+ "ANNOULTQE, IMPORTOINT, IMPORTOLAV, ONERIULTIM, IMPORTOSAL, AVANZAMENTO, CAUSA, "   //  5, .., 11 
			+ "STATOREAL, INFRASTRUTTURA, FRUIBILE, UTILIZZORID, DESTINAZIONEUSO, "               // 12, .., 16
			+ "CESSIONE, VENDITA, DEMOLIZIONE, ONERI_SITO, DISCONTINUITA_RETE "                   // 17, .., 21
			+ "FROM OITRI WHERE CONTRI = ? ORDER BY NUMOI";

		List< ? > listaOpere;
		List< ? > itemOpera = new ArrayList<Object>();

		listaOpere = sqlManager.getListVector(sqlRecuperaOITRI, new Object[] {contri});
		
		if (listaOpere.size() > 0) {
			for (int i = 0; i < listaOpere.size(); i++) {
				itemOpera = (List< ? >) listaOpere.get(i);
				OperaIncompiutaEntry opera = new OperaIncompiutaEntry();
				if (verificaEsistenzaValore(itemOpera.get(1))) {
					opera.setCup(itemOpera.get(1).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(2))) {
					opera.setDescrizione(itemOpera.get(2).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(3))) {
					opera.setDeterminazioneAmministrazione(itemOpera.get(3).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(4))) {
					opera.setAmbito(itemOpera.get(4).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(5))) {
					opera.setAnno(new Long(itemOpera.get(5).toString()));
				}
				if (verificaEsistenzaValore(itemOpera.get(6))) {
					opera.setImportoIntervento(Double.parseDouble(itemOpera.get(6).toString()));
				}
				if (verificaEsistenzaValore(itemOpera.get(7))) {
					opera.setImportoLavori(Double.parseDouble(itemOpera.get(7).toString()));
				}
				if (verificaEsistenzaValore(itemOpera.get(8))) {
					opera.setOneri(Double.parseDouble(itemOpera.get(8).toString()));
				}
				if (verificaEsistenzaValore(itemOpera.get(9))) {
					opera.setImportoAvanzamento(Double.parseDouble(itemOpera.get(9).toString()));
				}
				if (verificaEsistenzaValore(itemOpera.get(10))) {
					opera.setPercentualeAvanzamento(Double.parseDouble(itemOpera.get(10).toString()));
				}
				if (verificaEsistenzaValore(itemOpera.get(11))) {
					opera.setCausa(itemOpera.get(11).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(12))) {
					opera.setStato(itemOpera.get(12).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(13))) {
					opera.setInfrastruttura(itemOpera.get(13).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(14))) {
					opera.setFruibile(itemOpera.get(14).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(15))) {
					opera.setRidimensionato(itemOpera.get(15).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(16))) {
					opera.setDestinazioneUso(itemOpera.get(16).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(17))) {
					opera.setCessione(itemOpera.get(17).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(18))) {
					opera.setPrevistaVendita(itemOpera.get(18).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(19))) {
					opera.setDemolizione(itemOpera.get(19).toString());
				}
				if (verificaEsistenzaValore(itemOpera.get(20))) {
					opera.setOneriSito(Double.parseDouble(itemOpera.get(20).toString()));
				}
				if (verificaEsistenzaValore(itemOpera.get(21))) {
          opera.setDiscontinuitaRete(itemOpera.get(21).toString());
        }
				
				AltriDatiOperaIncompiutaEntry altriDati = new AltriDatiOperaIncompiutaEntry();
				this.valorizzaAltriDatiOpera(altriDati, contri, new Long(itemOpera.get(0).toString()));
				opera.setAltriDati(altriDati);
				
				this.valorizzaImmobili(opera.getImmobili(), contri, new Long(0), new Long(itemOpera.get(0).toString()));
				
				opere.add(opera);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Altri dati opera incompiuta
	 * 
	 * @param altriDati
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice programma
	 * @param numoi
	 *            codice opera incompiuta
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaAltriDatiOpera(final AltriDatiOperaIncompiutaEntry altriDati,
			final Long contri, final Long numoi)
	throws SQLException, ParseException {
		String sqlRecuperaOITRI = "SELECT ISTAT, NUTS, SEZINT, INTERV, "
			+ "REQ_CAP, REQ_PRGE, DIM_UM, DIM_QTA, SPONSORIZZAZIONE, FINANZA_PROGETTO, COSTO, FINANZIAMENTO, "
			+ "COP_STATALE, COP_REGIONALE, COP_PROVINCIALE, COP_COMUNALE, COP_ALTRAPUBBLICA, COP_COMUNITARIA, COP_PRIVATA "
			+ "FROM OITRI WHERE CONTRI = ? AND NUMOI = ?";

		List< ? > listaOpera;
		List< ? > itemOpera = new ArrayList<Object>();

		listaOpera = sqlManager.getListVector(sqlRecuperaOITRI, new Object[] {
				contri, numoi });
		if (listaOpera.size() > 0) {
			itemOpera = (List< ? >) listaOpera.get(0);

			if (verificaEsistenzaValore(itemOpera.get(0))) {
				altriDati.setIstat(itemOpera.get(0).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(1))) {
				altriDati.setNuts(itemOpera.get(1).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(2))) {
				altriDati.setTipologiaIntervento(itemOpera.get(2).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(3))) {
				altriDati.setCategoriaIntervento(itemOpera.get(3).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(4))) {
				altriDati.setRequisitiCapitolato(itemOpera.get(4).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(5))) {
				altriDati.setRequisitiApprovato(itemOpera.get(5).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(6))) {
				altriDati.setUnitaMisura(itemOpera.get(6).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(7))) {
				altriDati.setDimensione(Double.parseDouble(itemOpera.get(7).toString()));
			}
			if (verificaEsistenzaValore(itemOpera.get(8))) {
				altriDati.setSponsorizzazione(itemOpera.get(8).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(9))) {
				altriDati.setFinanzaDiProgetto(itemOpera.get(9).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(10))) {
				altriDati.setCostoProgetto(Double.parseDouble(itemOpera.get(10).toString()));
			}
			if (verificaEsistenzaValore(itemOpera.get(11))) {
				altriDati.setFinanziamento(Double.parseDouble(itemOpera.get(11).toString()));
			}
			if (verificaEsistenzaValore(itemOpera.get(12))) {
				altriDati.setCoperturaStatale(itemOpera.get(12).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(13))) {
				altriDati.setCoperturaRegionale(itemOpera.get(13).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(14))) {
				altriDati.setCoperturaProvinciale(itemOpera.get(14).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(15))) {
				altriDati.setCoperturaComunale(itemOpera.get(15).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(16))) {
				altriDati.setCoperturaAltro(itemOpera.get(16).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(17))) {
				altriDati.setCoperturaComunitaria(itemOpera.get(17).toString());
			}
			if (verificaEsistenzaValore(itemOpera.get(18))) {
				altriDati.setCoperturaPrivata(itemOpera.get(18).toString());
			}
		}
	}
	
	
	/**
	 * Metodo valorizzazione Immobili opereIncompiute e interventi lavori
	 * 
	 * @param immobili
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice del programma
	 * @param conint
	 *            codice dell'intervento
	 * @param numoi
	 *            codice opera incompiuta
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaImmobili(final List<ImmobileEntry> immobili,
			final Long contri, final Long conint, final Long numoi)
	throws SQLException, ParseException {
		String sqlRecuperaIMMTRAI = "SELECT CUIIMM, COMIST, NUTS, TITCOR, IMMDISP, "
			+ "PROGDISM, DESIMM, TIPDISP, VA1IMM, VA2IMM, VA3IMM, VA9IMM "
			+ "FROM IMMTRAI WHERE CONTRI = ? AND CONINT = ? ";

		if (conint.equals(new Long(0)) && numoi != null) {
			sqlRecuperaIMMTRAI += " AND NUMOI = ?";
		} 
		sqlRecuperaIMMTRAI += " ORDER BY NUMIMM";
		
		List< ? > listaImmobili;
		List< ? > itemImmobile = new ArrayList<Object>();

		if (conint.equals(new Long(0)) && numoi != null) {
			listaImmobili = sqlManager.getListVector(sqlRecuperaIMMTRAI, new Object[] {
					contri, conint, numoi });
		} else {
			listaImmobili = sqlManager.getListVector(sqlRecuperaIMMTRAI, new Object[] {
					contri, conint });
		}

		if (listaImmobili.size() > 0) {
			for (int i = 0; i < listaImmobili.size(); i++) {
				itemImmobile = (List< ? >) listaImmobili.get(i);
				ImmobileEntry immobile = new ImmobileEntry();
				if (verificaEsistenzaValore(itemImmobile.get(0))) {
					immobile.setCui(itemImmobile.get(0).toString());
				}
				if (verificaEsistenzaValore(itemImmobile.get(1))) {
					immobile.setIstat(itemImmobile.get(1).toString());
				}
				if (verificaEsistenzaValore(itemImmobile.get(2))) {
					immobile.setNuts(itemImmobile.get(2).toString());
				}
				if (verificaEsistenzaValore(itemImmobile.get(3))) {
					immobile.setTrasferimentoTitoloCorrispettivo(new Long(itemImmobile.get(3).toString()));
				}
				if (verificaEsistenzaValore(itemImmobile.get(4))) {
					immobile.setImmobileDisponibile(new Long(itemImmobile.get(4).toString()));
				}
				if (verificaEsistenzaValore(itemImmobile.get(5))) {
					immobile.setInclusoProgrammaDismissione(new Long(itemImmobile.get(5).toString()));
				}
				if (verificaEsistenzaValore(itemImmobile.get(6))) {
					immobile.setDescrizione(itemImmobile.get(6).toString());
				}
				if (verificaEsistenzaValore(itemImmobile.get(7))) {
					immobile.setTipoDisponibilita(new Long(itemImmobile.get(7).toString()));
				}
				if (verificaEsistenzaValore(itemImmobile.get(8))) {
					immobile.setValoreStimato1(Double.parseDouble(itemImmobile.get(8).toString()));
				}
				if (verificaEsistenzaValore(itemImmobile.get(9))) {
					immobile.setValoreStimato2(Double.parseDouble(itemImmobile.get(9).toString()));
				}
				if (verificaEsistenzaValore(itemImmobile.get(10))) {
					immobile.setValoreStimato3(Double.parseDouble(itemImmobile.get(10).toString()));
				}
				if (verificaEsistenzaValore(itemImmobile.get(11))) {
					immobile.setValoreStimatoSucc(Double.parseDouble(itemImmobile.get(11).toString()));
				}
				immobili.add(immobile);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Dati Programma DM 11/2011
	 * 
	 * @param programma
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice univoco programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	public void valorizzaProgrammaDM112011(final PubblicaProgrammaDM112011Entry programma, final long contri)
	throws SQLException, ParseException {
		
		String sqlRecuperaPROGRAMMA = "SELECT ID, ANNTRI, BRETRI, CENINT, NOTSCHE1, NOTSCHE2, NOTSCHE3, NOTSCHE4, NOTSCHE2B, IMPACC, "
			+ "NAPTRI, DAPTRI, DPAPPROV, TITAPPROV, URLAPPROV, NADOZI, DADOZI, DPADOZI, TITADOZI, URLADOZI, RESPRO, IDUFFICIO, ID_RICEVUTO "
			+ "FROM PIATRI WHERE CONTRI = ? ";

		List< ? > listaProgramma;
		List< ? > itemProgramma = new ArrayList<Object>();

		listaProgramma = sqlManager.getListVector(sqlRecuperaPROGRAMMA, new Object[] {contri });
		
		if (listaProgramma.size() > 0) {
			itemProgramma = (List< ? >) listaProgramma.get(0);

			if (verificaEsistenzaValore(itemProgramma.get(0))) {
				programma.setId(itemProgramma.get(0).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(1))) {
				programma.setAnno(new Long(itemProgramma.get(1).toString()));
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(2))) {
				programma.setDescrizione(itemProgramma.get(2).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(3))) {
				programma.setCodiceFiscaleSA(this.getCFStazioneAppaltante(itemProgramma.get(3).toString()));
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(4))) {
				programma.setNote1(itemProgramma.get(4).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(5))) {
				programma.setNote2(itemProgramma.get(5).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(6))) {
				programma.setNote3(itemProgramma.get(6).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(7))) {
				programma.setNote4(itemProgramma.get(7).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(8))) {
				programma.setNote2b(itemProgramma.get(8).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(9))) {
				programma.setAccantonamento(new Double(itemProgramma.get(9).toString()));
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(10))) {
				programma.setNumeroApprovazione(itemProgramma.get(10).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(11))) {
				programma.setDataApprovazione(itemProgramma.get(11).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(12))) {
				programma.setDataPubblicazioneApprovazione(itemProgramma.get(12).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(13))) {
				programma.setTitoloAttoApprovazione(itemProgramma.get(13).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(14))) {
				programma.setUrlAttoApprovazione(itemProgramma.get(14).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(15))) {
				programma.setNumeroAdozione(itemProgramma.get(15).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(16))) {
				programma.setDataAdozione(itemProgramma.get(16).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(17))) {
				programma.setDataPubblicazioneAdozione(itemProgramma.get(17).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(18))) {
				programma.setTitoloAttoAdozione(itemProgramma.get(18).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(19))) {
				programma.setUrlAttoAdozione(itemProgramma.get(19).toString());
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(20))) {
				DatiGeneraliTecnicoEntry referente = new DatiGeneraliTecnicoEntry();
				this.valorizzaTecnico(referente, itemProgramma.get(20).toString());
				programma.setReferente(referente);
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(21))) {
				//valorizza ufficio
				try {
					String denomUfficio = (String)sqlManager.getObject(selectUFFICI, new Object[] {new Long(itemProgramma.get(21).toString())});
					programma.setUfficio(denomUfficio);
				} catch (Exception ex) {
					;
				}
			}
			
			if (verificaEsistenzaValore(itemProgramma.get(22))) {
				programma.setIdRicevuto(new Long(itemProgramma.get(22).toString()));
			}

			this.valorizzaInterventiDM112011(programma.getInterventi(), contri);
			this.valorizzaFornitureServiziDM112011(programma.getFornitureEServizi(), contri);
			this.valorizzaLavoriEconomia(programma.getLavoriEconomia(), contri);
		}
	}
	
	/**
	 * Metodo valorizzazione interventi lavori per programma DM 11 2011
	 * 
	 * @param interventi
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaInterventiDM112011(final List<InterventoDM112011Entry> interventi,
			final Long contri)
	throws SQLException, ParseException {
		String sqlRecuperaINTTRI = "SELECT NPROGINT, CODINT, DESINT, ANNRIF, SEZINT, INTERV, "
			+ "CATINT, COMINT, NUTS, FLAG_CUP, CUPPRG, CODCPV, ANNINT, FIINTR, "
			+ "PRGINT, URCINT, APCINT, STAPRO, AILINT, TILINT, AFLINT, TFLINT, "
			+ "DV1TRI, DV2TRI, DV3TRI, MU1TRI, MU2TRI, MU3TRI, "
			+ "IM1TRI, IM2TRI, IM3TRI, BI1TRI, BI2TRI, BI3TRI, "
			+ "AL1TRI, AL2TRI, AL3TRI, PR1TRI, PR2TRI, PR3TRI, TCPINT, CODRUP, CONINT "
			+ "FROM INTTRI WHERE CONTRI = ? AND TIPINT = 1 ORDER BY CONINT";

		List< ? > listaInterventi;
		List< ? > itemIntervento = new ArrayList<Object>();

		listaInterventi = sqlManager.getListVector(sqlRecuperaINTTRI, new Object[] {contri});
		
		if (listaInterventi.size() > 0) {
			for (int i = 0; i < listaInterventi.size(); i++) {
				itemIntervento = (List< ? >) listaInterventi.get(i);
				InterventoDM112011Entry intervento = new InterventoDM112011Entry();
				if (verificaEsistenzaValore(itemIntervento.get(0))) {
					intervento.setNumeroProgressivo(new Long(itemIntervento.get(0).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(1))) {
					intervento.setCodiceInterno(itemIntervento.get(1).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(2))) {
					intervento.setDescrizione(itemIntervento.get(2).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(3))) {
					intervento.setAnnualitaRiferimento(new Long(itemIntervento.get(3).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(4))) {
					intervento.setTipologia(itemIntervento.get(4).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(5))) {
					intervento.setCategoria(itemIntervento.get(5).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(6))) {
					intervento.setSubCategoria(itemIntervento.get(6).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(7))) {
					intervento.setIstat(itemIntervento.get(7).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(8))) {
					intervento.setNuts(itemIntervento.get(8).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(9))) {
					intervento.setEsenteCup(itemIntervento.get(9).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(10))) {
					intervento.setCup(itemIntervento.get(10).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(11))) {
					intervento.setCpv(itemIntervento.get(11).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(12))) {
					intervento.setAnnuale(itemIntervento.get(12).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(13))) {
					intervento.setFinalita(itemIntervento.get(13).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(14))) {
					intervento.setPriorita(new Long(itemIntervento.get(14).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(15))) {
					intervento.setConformitaUrbanistica(itemIntervento.get(15).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(16))) {
					intervento.setConformitaAmbientale(itemIntervento.get(16).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(17))) {
					intervento.setStatoProgettazione(itemIntervento.get(17).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(18))) {
					intervento.setAnnoInizioLavori(new Long(itemIntervento.get(18).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(19))) {
					intervento.setTrimestreInizioLavori(new Long(itemIntervento.get(19).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(20))) {
					intervento.setAnnoFineLavori(new Long(itemIntervento.get(20).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(21))) {
					intervento.setTrimestreFineLavori(new Long(itemIntervento.get(21).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(22))) {
					intervento.setRisorseVincolatePerLegge1(Double.parseDouble(itemIntervento.get(22).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(23))) {
					intervento.setRisorseVincolatePerLegge2(Double.parseDouble(itemIntervento.get(23).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(24))) {
					intervento.setRisorseVincolatePerLegge3(Double.parseDouble(itemIntervento.get(24).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(25))) {
					intervento.setRisorseMutuo1(Double.parseDouble(itemIntervento.get(25).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(26))) {
					intervento.setRisorseMutuo2(Double.parseDouble(itemIntervento.get(26).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(27))) {
					intervento.setRisorseMutuo3(Double.parseDouble(itemIntervento.get(27).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(28))) {
					intervento.setRisorseImmobili1(Double.parseDouble(itemIntervento.get(28).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(29))) {
					intervento.setRisorseImmobili2(Double.parseDouble(itemIntervento.get(29).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(30))) {
					intervento.setRisorseImmobili3(Double.parseDouble(itemIntervento.get(30).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(31))) {
					intervento.setRisorseBilancio1(Double.parseDouble(itemIntervento.get(31).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(32))) {
					intervento.setRisorseBilancio2(Double.parseDouble(itemIntervento.get(32).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(33))) {
					intervento.setRisorseBilancio3(Double.parseDouble(itemIntervento.get(33).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(34))) {
					intervento.setRisorseAltro1(Double.parseDouble(itemIntervento.get(34).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(35))) {
					intervento.setRisorseAltro2(Double.parseDouble(itemIntervento.get(35).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(36))) {
					intervento.setRisorseAltro3(Double.parseDouble(itemIntervento.get(36).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(37))) {
					intervento.setRisorsePrivati1(Double.parseDouble(itemIntervento.get(37).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(38))) {
					intervento.setRisorsePrivati2(Double.parseDouble(itemIntervento.get(38).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(39))) {
					intervento.setRisorsePrivati3(Double.parseDouble(itemIntervento.get(39).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(40))) {
					intervento.setTipologiaCapitalePrivato(itemIntervento.get(40).toString());
				}
				
				if (verificaEsistenzaValore(itemIntervento.get(41))) {
					DatiGeneraliTecnicoEntry rup = new DatiGeneraliTecnicoEntry();
					this.valorizzaTecnico(rup, itemIntervento.get(41).toString());
					intervento.setRup(rup);
				}
				
				this.valorizzaImmobiliDM112011(intervento.getImmobili(), contri, new Long(itemIntervento.get(42).toString()));
				interventi.add(intervento);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione interventi forniture e servizi per programma DM 11 2011
	 * 
	 * @param interventi
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaFornitureServiziDM112011(final List<FornitureServiziDM112011Entry> interventi,
			final Long contri)
	throws SQLException, ParseException {
		String sqlRecuperaINTTRIFS = "SELECT CODINT, DESINT, COMINT, NUTS, FLAG_CUP, "
			+ "CUPPRG, CODCPV, TIPOIN, PRGINT, NORRIF, STRUPR, MANTRI, "
			+ "MU1TRI, PR1TRI, BI1TRI, AL1TRI, CODRUP, IMPRFS, RG1TRI, MESEIN "
			+ "FROM INTTRI WHERE CONTRI = ? AND TIPINT = 2 ORDER BY CODINT";

		List< ? > listaInterventi;
		List< ? > itemIntervento = new ArrayList<Object>();

		listaInterventi = sqlManager.getListVector(sqlRecuperaINTTRIFS, new Object[] {contri});
		
		if (listaInterventi.size() > 0) {
			for (int i = 0; i < listaInterventi.size(); i++) {
				itemIntervento = (List< ? >) listaInterventi.get(i);
				FornitureServiziDM112011Entry intervento = new FornitureServiziDM112011Entry();
				if (verificaEsistenzaValore(itemIntervento.get(0))) {
					intervento.setCodiceInterno(itemIntervento.get(0).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(1))) {
					intervento.setDescrizione(itemIntervento.get(1).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(2))) {
					intervento.setIstat(itemIntervento.get(2).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(3))) {
					intervento.setNuts(itemIntervento.get(3).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(4))) {
					intervento.setEsenteCup(itemIntervento.get(4).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(5))) {
					intervento.setCup(itemIntervento.get(5).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(6))) {
					intervento.setCpv(itemIntervento.get(6).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(7))) {
					intervento.setSettore(itemIntervento.get(7).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(8))) {
					intervento.setPriorita(new Long(itemIntervento.get(8).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(9))) {
					intervento.setNormativaRiferimento(itemIntervento.get(9).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(10))) {
					intervento.setStrumentoProgrammazione(itemIntervento.get(10).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(11))) {
					intervento.setPrevistaManodopera(itemIntervento.get(11).toString());
				}
				if (verificaEsistenzaValore(itemIntervento.get(12))) {
					intervento.setRisorseMutuo(Double.parseDouble(itemIntervento.get(12).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(13))) {
					intervento.setRisorsePrivati(Double.parseDouble(itemIntervento.get(13).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(14))) {
					intervento.setRisorseBilancio(Double.parseDouble(itemIntervento.get(14).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(15))) {
					intervento.setRisorseAltro(Double.parseDouble(itemIntervento.get(15).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(17))) {
					intervento.setImportoRisorseFinanziarie(Double.parseDouble(itemIntervento.get(17).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(18))) {
					intervento.setImportoRisorseFinanziarieRegionali(Double.parseDouble(itemIntervento.get(18).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(19))) {
					intervento.setMeseAvvioProcedura(new Long(itemIntervento.get(19).toString()));
				}
				if (verificaEsistenzaValore(itemIntervento.get(16))) {
					DatiGeneraliTecnicoEntry rup = new DatiGeneraliTecnicoEntry();
					this.valorizzaTecnico(rup, itemIntervento.get(16).toString());
					intervento.setRup(rup);
				}
				
				interventi.add(intervento);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione lavori in economia per programma DM 11 2011
	 * 
	 * @param lavori
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaLavoriEconomia(final List<LavoroEconomiaEntry> lavori,
			final Long contri)
	throws SQLException, ParseException {
		String sqlRecuperaECOTRI = "SELECT DESCRI, CUPPRG, STIMA "
			+ "FROM ECOTRI WHERE CONTRI = ? ORDER BY CONECO";

		List< ? > listaLavori;
		List< ? > itemLavoro = new ArrayList<Object>();

		listaLavori = sqlManager.getListVector(sqlRecuperaECOTRI, new Object[] {contri});
		
		if (listaLavori.size() > 0) {
			for (int i = 0; i < listaLavori.size(); i++) {
				itemLavoro = (List< ? >) listaLavori.get(i);
				LavoroEconomiaEntry lavoro = new LavoroEconomiaEntry();
				if (verificaEsistenzaValore(itemLavoro.get(0))) {
					lavoro.setDescrizione(itemLavoro.get(0).toString());
				}
				if (verificaEsistenzaValore(itemLavoro.get(1))) {
					lavoro.setCup(itemLavoro.get(1).toString());
				}
				if (verificaEsistenzaValore(itemLavoro.get(2))) {
					lavoro.setStimaLavori(Double.parseDouble(itemLavoro.get(2).toString()));
				}
				
				lavori.add(lavoro);
			}
		}
	}
	
	
	/**
	 * Metodo valorizzazione Immobili DM 11/2011
	 * 
	 * @param immobili
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice del programma
	 * @param conint
	 *            codice dell'intervento
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaImmobiliDM112011(final List<ImmobileDM112011Entry> immobili,
			final Long contri, final Long conint)
	throws SQLException, ParseException {
		String sqlRecuperaIMMTRAI = "SELECT DESIMM, PROIMM, VALIMM "
			+ "FROM IMMTRAI WHERE CONTRI = ? AND CONINT = ? ORDER BY NUMIMM";

		List< ? > listaImmobili;
		List< ? > itemImmobile = new ArrayList<Object>();

		listaImmobili = sqlManager.getListVector(sqlRecuperaIMMTRAI, new Object[] {
				contri, conint });

		if (listaImmobili.size() > 0) {
			for (int i = 0; i < listaImmobili.size(); i++) {
				itemImmobile = (List< ? >) listaImmobili.get(i);
				ImmobileDM112011Entry immobile = new ImmobileDM112011Entry();
				if (verificaEsistenzaValore(itemImmobile.get(0))) {
					immobile.setDescrizione(itemImmobile.get(0).toString());
				}
				if (verificaEsistenzaValore(itemImmobile.get(1))) {
					immobile.setTipoProprieta(new Long(itemImmobile.get(1).toString()));
				}
				if (verificaEsistenzaValore(itemImmobile.get(2))) {
					immobile.setValoreStimato(Double.parseDouble(itemImmobile.get(2).toString()));
				}
				immobili.add(immobile);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione titolo Programma
	 * 
	 * @param programma
	 * 			oggetto da valorizzare
	 * @param contri
	 *            codice univoco programma
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	public String gestioneAutomaticaTitoloProgramma(final String descrizione, final long tipologia, final long anno, final String dataApprovazione,final long contri)
	throws SQLException, ParseException {
		String titolo = descrizione;
		
		String verificoEsistenzaProgrammaDaPubblicare = "SELECT CONTRI FROM PIATRI JOIN W9FLUSSI ON PIATRI.CONTRI = W9FLUSSI.KEY01 AND W9FLUSSI.AREA = 4 "
			+ " WHERE TIPROG = ? AND ANNTRI = ? AND CENINT = ? AND "
			+ " DAPTRI IS NOT NULL AND TITAPPROV IS NOT NULL AND URLAPPROV IS NOT NULL";

		List< ? > listaProgramma;

		String cenint = (String)sqlManager.getObject("SELECT CENINT FROM PIATRI WHERE CONTRI = ?", new Object[] {contri});
		listaProgramma = sqlManager.getListVector(verificoEsistenzaProgrammaDaPubblicare, new Object[] {tipologia, anno, cenint });
		
		if (listaProgramma.size() > 0 && dataApprovazione != null) {
			//ESISTE GIA UN PROGRAMMA APPROVATO QUINDI CAMBIO LA DESCRIZIONE DI QUESTO PROGRAMMA
			titolo = descrizione + " - Aggiornamento " + dataApprovazione;
		}
		return titolo;
	}
	
	public BlobFile getPdfProgramma(Long contri) throws DataAccessException, SQLException {
		
		HashMap<String, Object> hashMapFileAllegato = new HashMap<String, Object>();
	    hashMapFileAllegato.put("contri", contri);
	    BlobFile filePdf = this.w9FileDao.getFileAllegato("PIATRI", hashMapFileAllegato);

		return filePdf;
	}
	  
	public BlobFile getDocumentoAvviso(Long idavviso, String codein, Long codsistema, Long numdoc) throws DataAccessException, SQLException {
		HashMap<String, Object> hashMapFileAllegato = new HashMap<String, Object>();
	    hashMapFileAllegato.put("idavviso", idavviso);
	    hashMapFileAllegato.put("codein", codein);
	    hashMapFileAllegato.put("codsistema", codsistema);
	    hashMapFileAllegato.put("numdoc", numdoc);
	    BlobFile documento = this.w9FileDao.getFileAllegato("AVVISO", hashMapFileAllegato);
		return documento;
	}
	
	public BlobFile getDocumentoAtto(Long codiceGara, Long numeroPubblicazione, Long numdoc) throws DataAccessException, SQLException {
		HashMap<String, Object> hashMapFileAllegato = new HashMap<String, Object>();
		hashMapFileAllegato.put("codGara", codiceGara);
        hashMapFileAllegato.put("numdoc", numdoc);
        hashMapFileAllegato.put("num_pubb", numeroPubblicazione);
	    BlobFile documento = this.w9FileDao.getFileAllegato("GARA", hashMapFileAllegato);
		return documento;
	}
	
	/**
	 * Metodo valorizzazione Dati Tecnico
	 * 
	 * @param tecnico
	 * 			oggetto da valorizzare
	 * @param codtec
	 *            codice tecnico
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaTecnico(final DatiGeneraliTecnicoEntry tecnico,
			final String codtec)
	throws SQLException, ParseException {
		String sqlRecuperaTECNICO = "SELECT cogtei, nometei, nomtec, indtec, "
			+ "ncitec, loctec, protec, captec, teltec, faxtec, cftec, ematec, CITTEC "
			+ "FROM TECNI WHERE CODTEC = ? ";

		List< ? > listaTecnico;
		List< ? > itemTecnico = new ArrayList<Object>();

		listaTecnico = sqlManager.getListVector(sqlRecuperaTECNICO, new Object[] {
				codtec });
		if (listaTecnico.size() > 0) {
			itemTecnico = (List< ? >) listaTecnico.get(0);

			if (verificaEsistenzaValore(itemTecnico.get(0))) {
				tecnico.setCognome(itemTecnico.get(0).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(1))) {
				tecnico.setNome(itemTecnico.get(1).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(2))) {
				tecnico.setNomeCognome(itemTecnico.get(2).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(3))) {
				tecnico.setIndirizzo(itemTecnico.get(3).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(4))) {
				tecnico.setCivico(itemTecnico.get(4).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(5))) {
				tecnico.setLocalita(itemTecnico.get(5).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(6))) {
				tecnico.setProvincia(itemTecnico.get(6).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(7))) {
				tecnico.setCap(itemTecnico.get(7).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(8))) {
				tecnico.setTelefono(itemTecnico.get(8).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(9))) {
				tecnico.setFax(itemTecnico.get(9).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(10))) {
				tecnico.setCfPiva(itemTecnico.get(10).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(11))) {
				tecnico.setMail(itemTecnico.get(11).toString());
			}
			if (verificaEsistenzaValore(itemTecnico.get(12))) {
				tecnico.setLuogoIstat(itemTecnico.get(12).toString());
			}
		}
	}

	/**
	 * Metodo valorizzazione Dati Stazione Appaltante
	 * 
	 * @param stazioneAppaltante
	 * 			oggetto da valorizzare
	 * @param codein
	 *            codice stazione appaltante
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	public void valorizzaStazioneAppaltante(final DatiGeneraliStazioneAppaltanteEntry stazioneAppaltante,
			final String codein)
	throws SQLException, ParseException {
		String sqlRecuperaUFFINT = "SELECT NOMEIN,VIAEIN,NCIEIN,CITEIN,CODCIT,PROEIN,CAPEIN,CODNAZ,TELEIN,FAXEIN,CFEIN,TIPOIN,EMAIIN,EMAI2IN,CFANAC,ISCUC "
			+ "FROM UFFINT WHERE CODEIN = ? ";

		List< ? > listaUffint;
		List< ? > itemUffint = new ArrayList<Object>();

		listaUffint = sqlManager.getListVector(sqlRecuperaUFFINT, new Object[] {
				codein });
		if (listaUffint.size() > 0) { 
			itemUffint = (List< ? >) listaUffint.get(0);

			if (verificaEsistenzaValore(itemUffint.get(0))) {
				stazioneAppaltante.setDenominazione(itemUffint.get(0).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(1))) {
				stazioneAppaltante.setIndirizzo(itemUffint.get(1).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(2))) {
				stazioneAppaltante.setCivico(itemUffint.get(2).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(3))) {
				stazioneAppaltante.setLocalita(itemUffint.get(3).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(4))) {
				stazioneAppaltante.setCodiceIstat(itemUffint.get(4).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(5))) {
				stazioneAppaltante.setProvincia(itemUffint.get(5).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(6))) {
				stazioneAppaltante.setCap(itemUffint.get(6).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(7))) {
				stazioneAppaltante.setCodiceNazione(Integer.parseInt(itemUffint.get(7).toString()));
			}
			if (verificaEsistenzaValore(itemUffint.get(8))) {
				stazioneAppaltante.setTelefono(itemUffint.get(8).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(9))) {
				stazioneAppaltante.setFax(itemUffint.get(9).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(10))) {
				stazioneAppaltante.setCodiceFiscale(itemUffint.get(10).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(11))) {
				stazioneAppaltante.setTipoAmministrazione(new Long(itemUffint.get(11).toString()));
			}
			if (verificaEsistenzaValore(itemUffint.get(12))) {
				stazioneAppaltante.setEmail(itemUffint.get(12).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(13))) {
				stazioneAppaltante.setPec(itemUffint.get(13).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(14))) {
				stazioneAppaltante.setCfAnac(itemUffint.get(14).toString());
			}
			if (verificaEsistenzaValore(itemUffint.get(15))) {
			  stazioneAppaltante.setIscuc(itemUffint.get(15).toString());
			}
			
			if (this.geneManager.esisteTabella("STO_UFFINT")) {
  			try {
  				this.valorizzaStoricoUffint(stazioneAppaltante.getStorico(), codein);
  			} catch(Exception ex) {
  				logger.error("Errore nella storicizzazione della UFFINT. " + ex);
  			}
			}
		}
	}

	/**
	 * Metodo valorizzazione Storico anagrafica UFFINT
	 * 
	 * @param storico
	 * 			oggetto da valorizzare
	 * @param codein
	 *            codice della stazione appaltante
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaStoricoUffint(final List<StoricoAnagraficaEntry> storico, final String codein)
	    throws SQLException, ParseException {
		
	  String sqlRecuperaStoricoUffint = "SELECT DATA_FINE_VALIDITA,NOMEIN,VIAEIN,NCIEIN,CITEIN,CODCIT,PROEIN,CAPEIN,CODNAZ,TELEIN,FAXEIN,CFEIN,TIPOIN,EMAIIN,EMAI2IN,CFANAC,ISCUC "
			+ " FROM STO_UFFINT WHERE CODEIN = ? ORDER BY ID";

		List< ? > listaStorico;
		List< ? > itemStorico = new ArrayList<Object>();

		listaStorico = sqlManager.getListVector(sqlRecuperaStoricoUffint, new Object[] { codein });

		if (listaStorico.size() > 0) {
			for (int i = 0; i < listaStorico.size(); i++) {
				itemStorico = (List< ? >) listaStorico.get(i);
				StoricoAnagraficaEntry stoUffint = new StoricoAnagraficaEntry();
				if (verificaEsistenzaValore(itemStorico.get(0))) {
					stoUffint.setDataFineValidita(itemStorico.get(0).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(1))) {
					stoUffint.setDenominazione(itemStorico.get(1).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(2))) {
					stoUffint.setIndirizzo(itemStorico.get(2).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(3))) {
					stoUffint.setCivico(itemStorico.get(3).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(4))) {
					stoUffint.setLocalita(itemStorico.get(4).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(5))) {
					stoUffint.setCodiceIstat(itemStorico.get(5).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(6))) {
					stoUffint.setProvincia(itemStorico.get(6).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(7))) {
					stoUffint.setCap(itemStorico.get(7).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(8))) {
					stoUffint.setCodiceNazione(Integer.parseInt(itemStorico.get(8).toString()));
				}
				if (verificaEsistenzaValore(itemStorico.get(9))) {
					stoUffint.setTelefono(itemStorico.get(9).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(10))) {
					stoUffint.setFax(itemStorico.get(10).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(11))) {
					stoUffint.setCodiceFiscale(itemStorico.get(11).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(12))) {
					stoUffint.setTipoAmministrazione(new Long(itemStorico.get(12).toString()));
				}
				if (verificaEsistenzaValore(itemStorico.get(13))) {
					stoUffint.setEmail(itemStorico.get(13).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(14))) {
					stoUffint.setPec(itemStorico.get(14).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(15))) {
					stoUffint.setCfAnac(itemStorico.get(15).toString());
				}
				if (verificaEsistenzaValore(itemStorico.get(16))) {
					stoUffint.setIscuc(itemStorico.get(16).toString());
				}
				storico.add(stoUffint);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Dati Impresa
	 * 
	 * @param impresa
	 * 			oggetto da valorizzare
	 * @param codimp
	 *            codice impresa
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaImpresa(final ImpresaEntry impresa,
			final String codimp)
	throws SQLException, ParseException {
		String sqlRecuperaIMPRESA = "SELECT nomimp, indimp, nciimp, locimp, "
			+ "proimp, capimp, telimp, faximp, cfimp, pivimp, ncciaa, natgiui, nazimp "
			+ "FROM IMPR WHERE CODIMP = ? ";

		List< ? > listaImpresa;
		List< ? > itemImpresa = new ArrayList<Object>();

		listaImpresa = sqlManager.getListVector(sqlRecuperaIMPRESA, new Object[] {codimp });
		if (listaImpresa.size() > 0) {
			itemImpresa = (List< ? >) listaImpresa.get(0);

			if (verificaEsistenzaValore(itemImpresa.get(0))) {
				impresa.setRagioneSociale(itemImpresa.get(0).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(1))) {
				impresa.setIndirizzo(itemImpresa.get(1).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(2))) {
				impresa.setNumeroCivico(itemImpresa.get(2).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(3))) {
				impresa.setLocalita(itemImpresa.get(3).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(4))) {
				impresa.setProvincia(itemImpresa.get(4).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(5))) {
				impresa.setCap(itemImpresa.get(5).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(6))) {
				impresa.setTelefono(itemImpresa.get(6).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(7))) {
				impresa.setFax(itemImpresa.get(7).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(8))) {
				impresa.setCodiceFiscale(itemImpresa.get(8).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(9))) {
				impresa.setPartitaIva(itemImpresa.get(9).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(10))) {
				impresa.setNumeroCCIAA(itemImpresa.get(10).toString());
			}
			if (verificaEsistenzaValore(itemImpresa.get(11))) {
				impresa.setFormaGiuridica(new Long(itemImpresa.get(11).toString()));
			}
			if (verificaEsistenzaValore(itemImpresa.get(12))) {
				impresa.setCodiceNazione(new Long(itemImpresa.get(12).toString()));
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Dati Stazione Appaltante
	 * 
	 * @param stazioneAppaltante
	 * 			oggetto da valorizzare
	 * @param codein
	 *            codice stazione appaltante
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private String getCFStazioneAppaltante(final String codein)
	throws SQLException, ParseException {
		String sqlRecuperaUFFINT = "SELECT cfein FROM UFFINT WHERE CODEIN = ? ";

		String codiceFiscale = (String)sqlManager.getObject(sqlRecuperaUFFINT, new Object[] {codein });
		
		return codiceFiscale;
	}

	/**
	 * Metodo valorizzazione Documenti avviso
	 * 
	 * @param documenti
	 * 			oggetto da valorizzare
	 * @param codein
	 *            codice stazione appaltante
	 * @param idAvviso
	 *            id avviso
	 * @param codiceSistema
	 *            codice sistema
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaDocumentiAvviso(final List<AllegatoEntry> documenti,
			final String codein, final long idAvviso, final long codiceSistema)
	throws SQLException, ParseException {
		
		String urlAvvisiDoc = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URLAVVISIDOC);
		
		String sqlRecuperaW9DOCAVVISO = "SELECT TITOLO, URL, NUMDOC "
			+ "FROM W9DOCAVVISO WHERE CODEIN = ? AND IDAVVISO = ? AND CODSISTEMA = ? ";

		List< ? > listaDocumenti;
		List< ? > itemDocumento = new ArrayList<Object>();

		listaDocumenti = sqlManager.getListVector(sqlRecuperaW9DOCAVVISO, new Object[] {
				codein, idAvviso, codiceSistema });

		if (listaDocumenti.size() > 0) {
			for (int i = 0; i < listaDocumenti.size(); i++) {
				itemDocumento = (List< ? >) listaDocumenti.get(i);
				AllegatoEntry documento = new AllegatoEntry();
				
				if (verificaEsistenzaValore(itemDocumento.get(0))) {
					documento.setTitolo(itemDocumento.get(0).toString());
				}
				if (verificaEsistenzaValore(itemDocumento.get(1))) {
					documento.setUrl(itemDocumento.get(1).toString());
				}
				
				BlobFile documentoAllegato = this.getDocumentoAvviso(idAvviso, codein, codiceSistema, new Long(itemDocumento.get(2).toString()));
				if (documentoAllegato.getStream() != null) {
					if (verificaEsistenzaValore(urlAvvisiDoc)) {
						String urlAvviso = new String(urlAvvisiDoc);
						urlAvviso = urlAvviso.replaceAll("<CODEIN>", codein);
						urlAvviso = urlAvviso.replaceAll("<IDAVVISO>", "" + idAvviso);
						urlAvviso = urlAvviso.replaceAll("<NUMDOC>", itemDocumento.get(2).toString());
						documento.setUrl(urlAvviso);
					} else {
						documento.setFile(documentoAllegato.getStream());
					}
				}
				documenti.add(documento);
			}
		}
	}

	/**
	 * Metodo valorizzazione Documenti Pubblicazione
	 * 
	 * @param documenti
	 * 			oggetto da valorizzare
	 * @param codGara
	 *            codice della gara
	 * @param numeroPubblicazione
	 *            numero pubblicazione gara
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaDocumentiPubblicazione(final List<AllegatoEntry> documenti,
			final long codGara, final long numeroPubblicazione)
	throws SQLException, ParseException {
		
		String urlAttiDoc = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URLATTIDOC);
		
		String sqlRecuperaW9DOCGARA = "SELECT TITOLO, URL, NUMDOC "
			+ "FROM W9DOCGARA WHERE CODGARA = ? AND NUM_PUBB = ?";

		List< ? > listaDocumenti;
		List< ? > itemDocumento = new ArrayList<Object>();

		listaDocumenti = sqlManager.getListVector(sqlRecuperaW9DOCGARA, new Object[] {
				codGara, numeroPubblicazione});

		if (listaDocumenti.size() > 0) {
			for (int i = 0; i < listaDocumenti.size(); i++) {
				itemDocumento = (List< ? >) listaDocumenti.get(i);
				AllegatoEntry documento = new AllegatoEntry();
				if (verificaEsistenzaValore(itemDocumento.get(0))) {
					documento.setTitolo(itemDocumento.get(0).toString());
				}
				if (verificaEsistenzaValore(itemDocumento.get(1))) {
					documento.setUrl(itemDocumento.get(1).toString());
				}
				
				BlobFile documentoAllegato = this.getDocumentoAtto(codGara, numeroPubblicazione, new Long(itemDocumento.get(2).toString()));
				if (documentoAllegato.getStream() != null) {
					if (verificaEsistenzaValore(urlAttiDoc)) {
						String urlAtto = new String(urlAttiDoc);
						urlAtto = urlAtto.replaceAll("<CODGARA>", "" + codGara);
						urlAtto = urlAtto.replaceAll("<NUM_PUBB>", "" + numeroPubblicazione);
						urlAtto = urlAtto.replaceAll("<NUMDOC>", itemDocumento.get(2).toString());
						documento.setUrl(urlAtto);
					} else {
						documento.setFile(documentoAllegato.getStream());
					}
				}
				documenti.add(documento);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Dati Aggiudicatari
	 * 
	 * @param aggiudicatari
	 * 			oggetto da valorizzare
	 * @param codgara
	 *            codice gara
	 * @param numeroPubblicazione
	 *            numero pubblicazione gara
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaAggiudicatari(final List<AggiudicatarioEntry> aggiudicatari,
			final Long codgara, final Long numeroPubblicazione)
	throws SQLException, ParseException {
		String sqlRecuperaAGGIUDICATARI = "SELECT ID_TIPOAGG, RUOLO, CODIMP, ID_GRUPPO "
			+ "FROM ESITI_AGGIUDICATARI WHERE CODGARA = ? AND NUM_PUBB = ?";

		List< ? > listaAggiudicatari;
		List< ? > itemAggiudicatario = new ArrayList<Object>();

		listaAggiudicatari = sqlManager.getListVector(sqlRecuperaAGGIUDICATARI, new Object[] {
				codgara, numeroPubblicazione});
		
		
		if (listaAggiudicatari.size() > 0) {
			for (int i = 0; i < listaAggiudicatari.size(); i++) {
				itemAggiudicatario = (List< ? >) listaAggiudicatari.get(i);
				AggiudicatarioEntry aggiudicatario = new AggiudicatarioEntry();
				
				if (verificaEsistenzaValore(itemAggiudicatario.get(0))) {
					aggiudicatario.setTipoAggiudicatario(new Long(itemAggiudicatario.get(0).toString()));
				}
				if (verificaEsistenzaValore(itemAggiudicatario.get(1))) {
					aggiudicatario.setRuolo(new Long(itemAggiudicatario.get(1).toString()));
				}
				if (verificaEsistenzaValore(itemAggiudicatario.get(2))) {
					ImpresaEntry impresa = new ImpresaEntry();
					this.valorizzaImpresa(impresa, itemAggiudicatario.get(2).toString());
					aggiudicatario.setImpresa(impresa);
				}
				if (verificaEsistenzaValore(itemAggiudicatario.get(3))) {
					aggiudicatario.setIdGruppo(new Long(itemAggiudicatario.get(3).toString()));
				}
				aggiudicatari.add(aggiudicatario);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Dati Gara
	 * 
	 * @param gara
	 * 			oggetto da valorizzare
	 * @param codgara
	 *            codice gara
	 * @param numeroPubblicazione
	 *            numero pubblicazione gara
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	public void valorizzaGara(final PubblicaGaraEntry gara,
			final Long codgara, final Long numeroPubblicazione)
	throws SQLException, ParseException {
		String sqlRecuperaGARA = "SELECT OGGETTO, SITUAZIONE, PROV_DATO, IDAVGARA, CODEIN, "
			+ "INDSEDE, COMSEDE, PROSEDE, IDCC, IDUFFICIO, ID_MODO_INDIZIONE, CIG_ACCQUADRO, ID_TIPOLOGIA_SA, "
			+ "DENOM_SA_AGENTE, CF_SA_AGENTE, ALTRE_SA, TIPOLOGIA_PROCEDURA, FLAG_CENTRALE_STIPULA, RUP, RIC_ALLUV, "
			+ "CAM, SISMA, IMPORTO_GARA, FLAG_ENTE_SPECIALE, TIPO_APP, FLAG_SA_AGENTE, ID_RICEVUTO, DGURI, DSCADE, "
			+ "SOMMA_URGENZA, DURATA_ACCQUADRO, VER_SIMOG, DATA_PUBBLICAZIONE, ID_F_DELEGATE "
			+ "FROM W9GARA WHERE CODGARA = ? ";

		List< ? > listaGara;
		List< ? > itemGara = new ArrayList<Object>();

		listaGara = sqlManager.getListVector(sqlRecuperaGARA, new Object[] {
				codgara });
		if (listaGara.size() > 0) {
			itemGara = (List< ? >) listaGara.get(0);

			if (verificaEsistenzaValore(itemGara.get(0))) {
				gara.setOggetto(itemGara.get(0).toString());
			}
			/*if (verificaEsistenzaValore(itemGara.get(1))) {
				gara.setSituazione(Integer.parseInt(itemGara.get(1).toString()));
			}
			*/
			if (verificaEsistenzaValore(itemGara.get(2))) {
				gara.setProvenienzaDato(Integer.parseInt(itemGara.get(2).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(3))) {
				gara.setIdAnac(itemGara.get(3).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(4))) {
				gara.setCodiceFiscaleSA(this.getCFStazioneAppaltante(itemGara.get(4).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(5))) {
				gara.setIndirizzo(itemGara.get(5).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(6))) {
				gara.setComune(itemGara.get(6).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(7))) {
				gara.setProvincia(itemGara.get(7).toString());
			}
			
			if (verificaEsistenzaValore(itemGara.get(8))) {
				//valorizza centro di costo
				try {
					DataColumnContainer centroCosto = new DataColumnContainer(this.sqlManager, "CENTRICOSTO", selectCENTRICOSTO, new Object[] {new Long(itemGara.get(8).toString())});
					gara.setCodiceCentroCosto(centroCosto.getString("CODCENTRO"));
					gara.setCentroCosto(centroCosto.getString("DENOMCENTRO"));
				} catch (Exception ex) {
					;
				}
			}
			
			if (verificaEsistenzaValore(itemGara.get(9))) {
				//valorizza ufficio
				try {
					String denomUfficio = (String)sqlManager.getObject(selectUFFICI, new Object[] {new Long(itemGara.get(9).toString())});
					gara.setUfficio(denomUfficio);
				} catch (Exception ex) {
					;
				}
			}
			if (verificaEsistenzaValore(itemGara.get(10))) {
				gara.setModoIndizione(new Long(itemGara.get(10).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(11))) {
				gara.setCigAccQuadro(itemGara.get(11).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(12))) {
				gara.setTipoSA(new Long(itemGara.get(12).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(13))) {
				gara.setNomeSA(itemGara.get(13).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(14))) {
				gara.setCfAgente(itemGara.get(14).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(15))) {
				gara.setAltreSA(itemGara.get(15).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(16))) {
				gara.setTipoProcedura(new Long(itemGara.get(16).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(17))) {
				gara.setCentraleCommittenza(itemGara.get(17).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(18))) {
				DatiGeneraliTecnicoEntry rup = new DatiGeneraliTecnicoEntry();
				this.valorizzaTecnico(rup, itemGara.get(18).toString());
				gara.setTecnicoRup(rup);
			}
			if (verificaEsistenzaValore(itemGara.get(19))) {
				gara.setRicostruzioneAlluvione(itemGara.get(19).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(20))) {
				gara.setCriteriAmbientali(itemGara.get(20).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(21))) {
				gara.setSisma(itemGara.get(21).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(22))) {
				gara.setImportoGara(new Double(itemGara.get(22).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(23))) {
				gara.setSettore(itemGara.get(23).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(24))) {
				gara.setRealizzazione(new Long(itemGara.get(24).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(25))) {
				gara.setSaAgente(itemGara.get(25).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(26))) {
				gara.setIdRicevuto(new Long(itemGara.get(26).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(27))) {
				gara.setDataPubblicazione(itemGara.get(27).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(28))) {
				gara.setDataScadenza(itemGara.get(28).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(29))) {
				gara.setSommaUrgenza(itemGara.get(29).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(30))) {
				gara.setDurataAccordoQuadro(new Long(itemGara.get(30).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(31))) {
				gara.setVersioneSimog(new Long(itemGara.get(31).toString()));
			}
			if (verificaEsistenzaValore(itemGara.get(32))) {
				gara.setDataPerfezionamentoBando(itemGara.get(32).toString());
			}
			if (verificaEsistenzaValore(itemGara.get(33))) {
				gara.setFunzioniDelegate(new Long(itemGara.get(33).toString()));
			}
			PubblicazioneBandoEntry pubblicazioneBando = new PubblicazioneBandoEntry();
			this.valorizzaPubblicazioneBando(pubblicazioneBando, codgara);
			gara.setPubblicazioneBando(pubblicazioneBando);
			
			this.valorizzaLotti(gara.getLotti(), codgara, numeroPubblicazione);
		}
	}
	
	/**
	 * Metodo valorizzazione Pubbligazione bando gara
	 * 
	 * @param pubblicazioneBando
	 * 			oggetto da valorizzare
	 * @param codgara
	 *            codice gara
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaPubblicazioneBando(final PubblicazioneBandoEntry pubblicazioneBando,
			final Long codgara)
	throws SQLException, ParseException {
		String sqlRecuperaW9PUBB = "SELECT DATA_GUCE, DATA_GURI, DATA_ALBO, QUOTIDIANI_NAZ, "
			+ "QUOTIDIANI_REG, PROFILO_COMMITTENTE, SITO_MINISTERO_INF_TRASP, SITO_OSSERVATORIO_CP, DATA_BORE, PERIODICI "
			+ "FROM W9PUBB WHERE CODGARA = ? AND CODLOTT = 1 AND NUM_APPA = 1 AND NUM_PUBB = 1";

		List< ? > listaBando;
		List< ? > itemBando = new ArrayList<Object>();

		listaBando = sqlManager.getListVector(sqlRecuperaW9PUBB, new Object[] {codgara });
		if (listaBando.size() > 0) {
			itemBando = (List< ? >) listaBando.get(0);

			if (verificaEsistenzaValore(itemBando.get(0))) {
				pubblicazioneBando.setDataGuce(itemBando.get(0).toString());
			}
			if (verificaEsistenzaValore(itemBando.get(1))) {
				pubblicazioneBando.setDataGuri(itemBando.get(1).toString());
			}
			if (verificaEsistenzaValore(itemBando.get(2))) {
				pubblicazioneBando.setDataAlbo(itemBando.get(2).toString());
			}
			if (verificaEsistenzaValore(itemBando.get(3))) {
				pubblicazioneBando.setQuotidianiNazionali(new Long(itemBando.get(3).toString()));
			}
			if (verificaEsistenzaValore(itemBando.get(4))) {
				pubblicazioneBando.setQuotidianiLocali(new Long(itemBando.get(4).toString()));
			}
			if (verificaEsistenzaValore(itemBando.get(5))) {
				pubblicazioneBando.setProfiloCommittente(itemBando.get(5).toString());
			}
			if (verificaEsistenzaValore(itemBando.get(6))) {
				pubblicazioneBando.setProfiloInfTrasp(itemBando.get(6).toString());
			}
			if (verificaEsistenzaValore(itemBando.get(7))) {
				pubblicazioneBando.setProfiloOsservatorio(itemBando.get(7).toString());
			}
			if (verificaEsistenzaValore(itemBando.get(8))) {
				pubblicazioneBando.setDataBore(itemBando.get(8).toString());
			}
			if (verificaEsistenzaValore(itemBando.get(9))) {
				pubblicazioneBando.setPeriodici(new Long(itemBando.get(9).toString()));
			}
		} 
	}
	
	/**
	 * Metodo valorizzazione Dati Lotto
	 * 
	 * @param lotti
	 * 			oggetto da valorizzare
	 * @param codgara
	 *            codice gara
	 * @param numeroPubblicazione
	 *            numero pubblicazione gara
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaLotti(final List<PubblicaLottoEntry> lotti,
			final Long codgara, final Long numeroPubblicazione)
	throws SQLException, ParseException {
		String sqlRecuperaLOTTO = "SELECT W9LOTT.CODLOTT, OGGETTO, IMPORTO_LOTTO, IMPORTO_ATTUAZIONE_SICUREZZA, IMPORTO_TOT, CPV, "
			+ "ID_SCELTA_CONTRAENTE, ID_SCELTA_CONTRAENTE_50, ID_CATEGORIA_PREVALENTE, NLOTTO, CLASCAT, COMCON, DESCOM, CIGCOM, "
			+ "TIPO_CONTRATTO, FLAG_ENTE_SPECIALE, ID_MODO_GARA, LUOGO_ISTAT, LUOGO_NUTS, CIG, "
			+ "CUPESENTE, CUP, RUP, URL_EPROC, URL_COMMITTENTE, CUIINT, SOMMA_URGENZA, MANOD, CODINT, ID_TIPO_PRESTAZIONE, ART_E1, ART_E2, "
			+ "EXSOTTOSOGLIA, CONTRATTO_ESCLUSO_ALLEGGERITO, ESCLUSIONE_REGIME_SPECIALE, CIG_MASTER_ML, ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG ";
		
		List< ? > listaLotto;
		List< ? > itemLotto = new ArrayList<Object>();
		
		if (numeroPubblicazione != null) {
			sqlRecuperaLOTTO += "FROM W9LOTT JOIN W9PUBLOTTO ON W9LOTT.CODGARA=W9PUBLOTTO.CODGARA AND W9LOTT.CODLOTT=W9PUBLOTTO.CODLOTT AND NUM_PUBB = ? WHERE W9LOTT.CODGARA = ?";
			listaLotto = sqlManager.getListVector(sqlRecuperaLOTTO, new Object[] {
					numeroPubblicazione, codgara });
		} else {
			sqlRecuperaLOTTO += "FROM W9LOTT WHERE W9LOTT.CODGARA = ?";
			listaLotto = sqlManager.getListVector(sqlRecuperaLOTTO, new Object[] {codgara });
		}
		
		if (listaLotto.size() > 0) {
			for (int i = 0; i < listaLotto.size(); i++) {
				itemLotto = (List< ? >) listaLotto.get(i);
				PubblicaLottoEntry lotto = new PubblicaLottoEntry();
				if (verificaEsistenzaValore(itemLotto.get(1))) {
					lotto.setOggetto(itemLotto.get(1).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(2))) {
					lotto.setImportoLotto(Double.parseDouble(itemLotto.get(2).toString()));
				}
				if (verificaEsistenzaValore(itemLotto.get(3))) {
					lotto.setImportoSicurezza(Double.parseDouble(itemLotto.get(3).toString()));
				}
				if (verificaEsistenzaValore(itemLotto.get(4))) {
					lotto.setImportoTotale(Double.parseDouble(itemLotto.get(4).toString()));
				}
				if (verificaEsistenzaValore(itemLotto.get(5))) {
					lotto.setCpv(itemLotto.get(5).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(6))) {
					lotto.setIdSceltaContraente(new Long(itemLotto.get(6).toString()));
				}
				if (verificaEsistenzaValore(itemLotto.get(7))) {
					lotto.setIdSceltaContraente50(new Long(itemLotto.get(7).toString()));
				}
				if (verificaEsistenzaValore(itemLotto.get(8))) {
					lotto.setCategoria(itemLotto.get(8).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(9))) {
					lotto.setNumeroLotto(new Long(itemLotto.get(9).toString()));
				}
				if (verificaEsistenzaValore(itemLotto.get(10))) {
					lotto.setClasse(itemLotto.get(10).toString());
				}
				/*if (verificaEsistenzaValore(itemLotto.get(11))) {
					lotto.setLottoPrecedente(itemLotto.get(11).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(12))) {
					lotto.setMotivo(new Long(itemLotto.get(12).toString()));
				}
				if (verificaEsistenzaValore(itemLotto.get(13))) {
					lotto.setCigCollegato(itemLotto.get(13).toString());
				}*/
				if (verificaEsistenzaValore(itemLotto.get(14))) {
					lotto.setTipoAppalto(itemLotto.get(14).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(15))) {
					lotto.setSettore(itemLotto.get(15).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(16))) {
					lotto.setCriterioAggiudicazione(new Long(itemLotto.get(16).toString()));
				}
				if (verificaEsistenzaValore(itemLotto.get(17))) {
					lotto.setLuogoIstat(itemLotto.get(17).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(18))) {
					lotto.setLuogoNuts(itemLotto.get(18).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(19))) {
					lotto.setCig(itemLotto.get(19).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(20))) {
					lotto.setCupEsente(itemLotto.get(20).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(21))) {
					lotto.setCup(itemLotto.get(21).toString());
				}
				/*
				if (verificaEsistenzaValore(itemLotto.get(22))) {
					DatiGeneraliTecnicoEntry rup = new DatiGeneraliTecnicoEntry();
					this.valorizzaTecnico(rup, itemLotto.get(22).toString());
					lotto.setTecnicoRup(rup);
				}*/
				if (verificaEsistenzaValore(itemLotto.get(23))) {
					lotto.setUrlPiattaformaTelematica(itemLotto.get(23).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(24))) {
					lotto.setUrlCommittente(itemLotto.get(24).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(25))) {
					lotto.setCui(itemLotto.get(25).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(26))) {
					lotto.setSommaUrgenza(itemLotto.get(26).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(27))) {
					lotto.setManodopera(itemLotto.get(27).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(28))) {
					lotto.setCodiceIntervento(itemLotto.get(28).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(29))) {
					lotto.setPrestazioniComprese(new Long(itemLotto.get(29).toString()));
				}
				if (verificaEsistenzaValore(itemLotto.get(30))) {
					lotto.setContrattoEsclusoArt19e26(itemLotto.get(30).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(31))) {
					lotto.setContrattoEsclusoArt16e17e18(itemLotto.get(31).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(32))) {
					lotto.setExSottosoglia(itemLotto.get(32).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(33))) {
					lotto.setContrattoEsclusoAlleggerito(itemLotto.get(33).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(34))) {
					lotto.setEsclusioneRegimeSpeciale(itemLotto.get(34).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(35))) {
					lotto.setCigMaster(itemLotto.get(35).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(36))) {
					lotto.setIdSchedaLocale(itemLotto.get(36).toString());
				}
				if (verificaEsistenzaValore(itemLotto.get(37))) {
					lotto.setIdSchedaSimog(itemLotto.get(37).toString());
				}
				
				this.valorizzaModalitaAcquisizioneForniture(lotto.getModalitaAcquisizioneForniture(), codgara, new Long(itemLotto.get(0).toString()));
				this.valorizzaTipologieLavori(lotto.getTipologieLavori(), codgara, new Long(itemLotto.get(0).toString()));
				this.valorizzaMotivazioniProceduraNegoziata(lotto.getMotivazioniProceduraNegoziata(), codgara, new Long(itemLotto.get(0).toString()));
				this.valorizzaCategorie(lotto.getCategorie(), codgara, new Long(itemLotto.get(0).toString()));
				this.valorizzaCpvSecondari(lotto.getCpvSecondari(), codgara, new Long(itemLotto.get(0).toString()));
				lotti.add(lotto);
				
			}
		}
	}

	/**
	 * Metodo valorizzazione Motivazione ricorso a procedura negoziata.
	 * 
	 * @param motivazioniProceduraNegoziata
	 * 			oggetto da valorizzare
	 * @param codgara
	 *            codice della gara
	 * @param codlotto
	 *            codice del lotto
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaMotivazioniProceduraNegoziata(final List<MotivazioneProceduraNegoziataEntry> motivazioniProceduraNegoziata,
			final Long codgara, final Long codlotto)
	throws SQLException, ParseException {
		String sqlRecuperaW9COND = "SELECT NUM_COND, ID_CONDIZIONE "
			+ "FROM W9COND WHERE CODGARA = ? AND CODLOTT = ?";

		List< ? > listaCondizioni;
		List< ? > itemCondizione = new ArrayList<Object>();

		listaCondizioni = sqlManager.getListVector(sqlRecuperaW9COND, new Object[] {
				codgara, codlotto });

		if (listaCondizioni.size() > 0) {
			for (int i = 0; i < listaCondizioni.size(); i++) {
				itemCondizione = (List< ? >) listaCondizioni.get(i);
				MotivazioneProceduraNegoziataEntry condizione = new MotivazioneProceduraNegoziataEntry();
				if (verificaEsistenzaValore(itemCondizione.get(1))) {
					condizione.setCondizione(new Long(itemCondizione.get(1).toString()));
				}
				motivazioniProceduraNegoziata.add(condizione);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Tipologie Lavori.
	 * 
	 * @param tipologieLavori
	 * 			oggetto da valorizzare
	 * @param codgara
	 *            codice della gara
	 * @param codlotto
	 *            codice del lotto
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaTipologieLavori(final List<AppaLavEntry> tipologieLavori,
			final Long codgara, final Long codlotto)
	throws SQLException, ParseException {
		String sqlRecuperaW9APPALAV = "SELECT NUM_APPAL, ID_APPALTO "
			+ "FROM W9APPALAV WHERE CODGARA = ? AND CODLOTT = ?";

		List< ? > listaTipologie;
		List< ? > itemTipologia = new ArrayList<Object>();

		listaTipologie = sqlManager.getListVector(sqlRecuperaW9APPALAV, new Object[] {
				codgara, codlotto });

		if (listaTipologie.size() > 0) {
			for (int i = 0; i < listaTipologie.size(); i++) {
				itemTipologia = (List< ? >) listaTipologie.get(i);
				AppaLavEntry tipologia = new AppaLavEntry();
				if (verificaEsistenzaValore(itemTipologia.get(1))) {
					tipologia.setTipologiaLavoro(new Long(itemTipologia.get(1).toString()));
				}
				tipologieLavori.add(tipologia);
			}
		}
	}
	

	/**
	 * Metodo valorizzazione Categorie lotto
	 * 
	 * @param categorie
	 * 			oggetto da valorizzare
	 * @param codgara
	 *            codice della gara
	 * @param codlotto
	 *            codice del lotto
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaCategorie(final List<CategoriaLottoEntry> categorie,
			final Long codgara, final Long codlotto)
	throws SQLException, ParseException {
		String sqlRecuperaW9LOTTCATE = "SELECT NUM_CATE, CATEGORIA, CLASCAT, SCORPORABILE, SUBAPPALTABILE "
			+ "FROM W9LOTTCATE WHERE CODGARA = ? AND CODLOTT = ?";

		List< ? > listaCategorie;
		List< ? > itemCategoria = new ArrayList<Object>();

		listaCategorie = sqlManager.getListVector(sqlRecuperaW9LOTTCATE, new Object[] {
				codgara, codlotto });

		if (listaCategorie.size() > 0) {
			for (int i = 0; i < listaCategorie.size(); i++) {
				itemCategoria = (List< ? >) listaCategorie.get(i);
				CategoriaLottoEntry categoria = new CategoriaLottoEntry();
				if (verificaEsistenzaValore(itemCategoria.get(1))) {
					categoria.setCategoria(itemCategoria.get(1).toString());
				}
				if (verificaEsistenzaValore(itemCategoria.get(2))) {
					categoria.setClasse(itemCategoria.get(2).toString());
				}
				if (verificaEsistenzaValore(itemCategoria.get(3))) {
					categoria.setScorporabile(itemCategoria.get(3).toString());
				}
				if (verificaEsistenzaValore(itemCategoria.get(4))) {
					categoria.setSubappaltabile(itemCategoria.get(4).toString());
				}
				categorie.add(categoria);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Cpv Secondari lotto
	 * 
	 * @param cpvSecondari
	 * 			oggetto da valorizzare
	 * @param codgara
	 *            codice della gara
	 * @param codlotto
	 *            codice del lotto
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaCpvSecondari(final List<CpvLottoEntry> cpvSecondari,
			final Long codgara, final Long codlotto)
	throws SQLException, ParseException {
		String sqlRecuperaW9CPV = "SELECT NUM_CPV, CPV "
			+ "FROM W9CPV WHERE CODGARA = ? AND CODLOTT = ?";

		List< ? > listaCpv;
		List< ? > itemCpv = new ArrayList<Object>();

		listaCpv = sqlManager.getListVector(sqlRecuperaW9CPV, new Object[] {
				codgara, codlotto });

		if (listaCpv.size() > 0) {
			for (int i = 0; i < listaCpv.size(); i++) {
				itemCpv = (List< ? >) listaCpv.get(i);
				CpvLottoEntry cpv = new CpvLottoEntry();
				if (verificaEsistenzaValore(itemCpv.get(1))) {
					cpv.setCpv(itemCpv.get(1).toString());
				}
				cpvSecondari.add(cpv);
			}
		}
	}
	
	/**
	 * Metodo valorizzazione Modalità acquisizione forniture.
	 * 
	 * @param modalitaAcquisizioneForniture
	 * 			oggetto da valorizzare
	 * @param codgara
	 *            codice della gara
	 * @param codlotto
	 *            codice del lotto
	 * @throws SQLException
	 *             SQLException
	 * @throws ParseException
	 *             ParseException
	 */
	private void valorizzaModalitaAcquisizioneForniture(final List<AppaFornEntry> modalitaAcquisizioneForniture,
			final Long codgara, final Long codlotto)
	throws SQLException, ParseException {
		String sqlRecuperaW9APPAFORN = "SELECT NUM_APPAF, ID_APPALTO "
			+ "FROM W9APPAFORN WHERE CODGARA = ? AND CODLOTT = ?";

		List< ? > listaModalita;
		List< ? > itemModalita = new ArrayList<Object>();

		listaModalita = sqlManager.getListVector(sqlRecuperaW9APPAFORN, new Object[] {
				codgara, codlotto });

		if (listaModalita.size() > 0) {
			for (int i = 0; i < listaModalita.size(); i++) {
				itemModalita = (List< ? >) listaModalita.get(i);
				AppaFornEntry modalita = new AppaFornEntry();
				if (verificaEsistenzaValore(itemModalita.get(1))) {
					modalita.setModalitaAcquisizione(new Long(itemModalita.get(1).toString()));
				}
				modalitaAcquisizioneForniture.add(modalita);
			}
		}
	}
	
	
	public void exportORtoSCP() {
		logger.info("exportORtoSCP: inizio metodo");
		String sqlRecuperaW9OUTBOX = "SELECT IDCOMUN, AREA, KEY01, KEY02, KEY03, KEY04, CFSA FROM W9OUTBOX WHERE STATO = 1 ORDER BY IDCOMUN";
		String cfsa, codein;
		List< ? > listaW9OUTBOX;
		List< ? > itemW9OUTBOX = new ArrayList<Object>();
		String msg = null;
		Long idRicevuto = null;
		Long idComun = null;
		String json = null;
		Long stato = new Long(1);
		boolean goToPubblicazione = false;
		Long key01 = null,key02 = null,key03 = null,key04 = null;
		ObjectMapper mapper = new ObjectMapper();
		TransactionStatus status = null;
		try {
			
			String login = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_USERNAME);
			String password = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_PASSWORD);
			String url = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URL);
			String urlProgrammi = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URLPROGRAMMI);
		    String urlLogin = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URL_LOGIN);
		    String urlTabelleContesto = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URLTABELLECONTESTO);
		    String idClient = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_IDCLIENT);
		    String keyClient = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_KEYCLIENT);
		    
			listaW9OUTBOX = sqlManager.getListVector(sqlRecuperaW9OUTBOX, new Object[] { });
			
			if (listaW9OUTBOX.size() > 0) {
				//accesso ai servizi rest
				Client client = ClientBuilder.newClient();
				WebTarget webTarget = client.target(urlLogin).path("Account/LoginPubblica");
				MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
			    formData.add("username", login);
			    formData.add("password", password);
			    formData.add("clientKey", keyClient);
			    formData.add("clientId", idClient);
			    
			    Response accesso = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(formData,MediaType.APPLICATION_FORM_URLENCODED));
			    int esito = accesso.getStatus();
			    LoginResult resultAccesso = accesso.readEntity(LoginResult.class);
			    
			    if (resultAccesso.isEsito()) {
			    	String token = resultAccesso.getToken();
			    	for (int i = 0; i < listaW9OUTBOX.size(); i++) {
			    		status = this.sqlManager.startTransaction();
						try {
							itemW9OUTBOX = (List< ? >) listaW9OUTBOX.get(i);
							goToPubblicazione = false;
							msg = "";
							idRicevuto = null;
							json = null;
							stato = new Long(3);
							//verifico stazione appaltante
							cfsa = null;
							codein = null;
							idComun = new Long(itemW9OUTBOX.get(0).toString());
							if (verificaEsistenzaValore(itemW9OUTBOX.get(6))) {
								cfsa = itemW9OUTBOX.get(6).toString();
							}
							if (cfsa != null) {
								codein = (String)sqlManager.getObject("SELECT CODEIN FROM UFFINT WHERE CFEIN = ?", new Object[] { cfsa});
							} else {
								msg = "CSFA nullo";
								logger.info("Flusso IDCOMUN = " + itemW9OUTBOX.get(0).toString() + " non inviato, CFSA nullo");
							}
							if (codein != null) {
								DatiGeneraliStazioneAppaltanteEntry stazioneAppaltante = new DatiGeneraliStazioneAppaltanteEntry();
						    	this.valorizzaStazioneAppaltante(stazioneAppaltante,codein);
						    	json = mapper.writeValueAsString(stazioneAppaltante);
						    	webTarget = client.target(urlTabelleContesto).path("StazioniAppaltanti/Pubblica").queryParam("token", token);
						    	Response verificaStazioneAppaltante = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(stazioneAppaltante), Response.class);
						    	esito = verificaStazioneAppaltante.getStatus();
						    	InserimentoResult risultatoValidazioneSA = verificaStazioneAppaltante.readEntity(InserimentoResult.class);
						    	switch (esito) {
					    		case 200:
					    			goToPubblicazione = true;
					    			break;
					    		case 400:
					    			for(ValidateEntry errore:risultatoValidazioneSA.getValidate()) {
										msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio() + "\r\n";
									}
									break;
					    		default:
					    			msg = risultatoValidazioneSA.getError();
									break;
						    	}
						    	if (goToPubblicazione) {
						    		key01 = new Long(itemW9OUTBOX.get(2).toString());
						    		if (verificaEsistenzaValore(itemW9OUTBOX.get(3))) {
						    			key02 = new Long(itemW9OUTBOX.get(3).toString());
						    		}
					    			key03 = new Long(itemW9OUTBOX.get(4).toString());
						    		if (verificaEsistenzaValore(itemW9OUTBOX.get(5))) {
						    			key04 = new Long(itemW9OUTBOX.get(5).toString());
						    		}
						    		
						    		switch (key03.intValue()) {
									case 989:
										//avviso
										PubblicaAvvisoEntry avviso = new PubblicaAvvisoEntry();
										this.valorizzaAvviso(avviso, codein, key01, key02);
										json = mapper.writeValueAsString(avviso);
										webTarget = client.target(url).path("Avvisi/Pubblica").queryParam("token", token).queryParam("modalitaInvio", "2");
										Response risultatoAvviso = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(avviso), Response.class);
										esito = risultatoAvviso.getStatus();
										PubblicazioneResult risultatoPubblicazioneAvviso = risultatoAvviso.readEntity(PubblicazioneResult.class);
										switch (esito) {
											case 200:
												avviso.setIdRicevuto(risultatoPubblicazioneAvviso.getId());
												this.sqlManager.update(updateAVVISOIdRicevuto, new Object[] {avviso.getIdRicevuto(), codein, key01, key02});
												idRicevuto = risultatoPubblicazioneAvviso.getId();
												msg = "ok";
												stato = new Long(2);
												break;
											case 400:
												for(ValidateEntry errore:risultatoPubblicazioneAvviso.getValidate()) {
													msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio() + "\r\n";
												} 
												break;
											default:
												msg = risultatoPubblicazioneAvviso.getError();
												break;
										}
										break;
									case 901:
										//atti
										PubblicaAttoEntry pubblicazione = new PubblicaAttoEntry();
										this.valorizzaAtto(pubblicazione, key01, key04);
										json = mapper.writeValueAsString(pubblicazione);
										webTarget = client.target(url).path("Atti/Pubblica").queryParam("token", token).queryParam("modalitaInvio", "2");
										Response risultatoAtto = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(pubblicazione), Response.class);
										esito = risultatoAtto.getStatus();
										PubblicazioneAttoResult risultatoPubblicazioneAtto = risultatoAtto.readEntity(PubblicazioneAttoResult.class);
										switch (esito) {
											case 200:
												pubblicazione.setIdRicevuto(risultatoPubblicazioneAtto.getIdExArt29());
												pubblicazione.getGara().setIdRicevuto(risultatoPubblicazioneAtto.getIdGara());
												this.sqlManager.update(updateW9PUBBLICAZIONIIdRicevuto, new Object[] {pubblicazione.getIdRicevuto(), key01, key04});
										      	this.sqlManager.update(updateW9GARAIdRicevuto, new Object[] {pubblicazione.getGara().getIdRicevuto(), key01});
										      	idRicevuto = risultatoPubblicazioneAtto.getIdExArt29();
												msg = "ok";
												stato = new Long(2);
												break;
											case 400:
												for(ValidateEntry errore:risultatoPubblicazioneAtto.getValidate()) {
													msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio() + "\r\n";
												}
												break;
											default:
												msg = risultatoPubblicazioneAtto.getError();
												break;
										}
										break;
									case 988:
										//anagrafica gara lotti
										PubblicaGaraEntry gara = new PubblicaGaraEntry();
										this.valorizzaGara(gara, key01, null);
										json = mapper.writeValueAsString(gara);
										webTarget = client.target(url).path("Anagrafiche/GaraLotti").queryParam("token", token).queryParam("modalitaInvio", "2");
										Response risultatoGaraLotti = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(gara), Response.class);
										esito = risultatoGaraLotti.getStatus();
										PubblicazioneResult risultatoPubblicazioneGaraLotti = risultatoGaraLotti.readEntity(PubblicazioneResult.class);
										switch (esito) {
											case 200:
												gara.setIdRicevuto(risultatoPubblicazioneGaraLotti.getId());
												this.sqlManager.update(updateW9GARAIdRicevuto, new Object[] {gara.getIdRicevuto(), key01});
												idRicevuto = risultatoPubblicazioneGaraLotti.getId();
												msg = "ok";
												stato = new Long(2);
												break;
											case 400:
												for(ValidateEntry errore:risultatoPubblicazioneGaraLotti.getValidate()) {
													msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio() + "\r\n";
												}
												break;
											default:
												msg = risultatoPubblicazioneGaraLotti.getError();
												break;
										}
										break;
									case 982:
										//programma lavori
										PubblicaProgrammaLavoriEntry programma = new PubblicaProgrammaLavoriEntry();
										this.valorizzaProgrammaLavori(programma, key01);
										json = mapper.writeValueAsString(programma);
										webTarget = client.target(urlProgrammi).path("Programmi/PubblicaLavori").queryParam("token", token).queryParam("modalitaInvio", "2");
										Response risultatoProgrammaLavori = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(programma), Response.class);
										esito = risultatoProgrammaLavori.getStatus();
										PubblicazioneResult risultatoPubblicazioneProgrammaLavori = risultatoProgrammaLavori.readEntity(PubblicazioneResult.class);
										switch (esito) {
											case 200:
												programma.setIdRicevuto(risultatoPubblicazioneProgrammaLavori.getId());
												this.sqlManager.update(updatePIATRIIdRicevuto, new Object[] {programma.getIdRicevuto(), key01});
												idRicevuto = risultatoPubblicazioneProgrammaLavori.getId();
												msg = "ok";
												stato = new Long(2);
												break;
											case 400:
												for(ValidateEntry errore:risultatoPubblicazioneProgrammaLavori.getValidate()) {
													msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio() + "\r\n";
												}
												break;
											default:
												msg = risultatoPubblicazioneProgrammaLavori.getError();
												break;
										}
										break;
									case 981:
										//programma forniture servizi
										PubblicaProgrammaFornitureServiziEntry programmaFS = new PubblicaProgrammaFornitureServiziEntry();
										this.valorizzaProgrammaFornitureServizi(programmaFS, key01);
										json = mapper.writeValueAsString(programmaFS);
										webTarget = client.target(urlProgrammi).path("Programmi/PubblicaFornitureServizi").queryParam("token", token).queryParam("modalitaInvio", "2");
										Response risultatoProgrammaFS = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(programmaFS), Response.class);
										esito = risultatoProgrammaFS.getStatus();
										PubblicazioneResult risultatoPubblicazioneProgrammaFS = risultatoProgrammaFS.readEntity(PubblicazioneResult.class);
										switch (esito) {
											case 200:
												programmaFS.setIdRicevuto(risultatoPubblicazioneProgrammaFS.getId());
												this.sqlManager.update(updatePIATRIIdRicevuto, new Object[] {programmaFS.getIdRicevuto(), key01});
												idRicevuto = risultatoPubblicazioneProgrammaFS.getId();
												msg = "ok";
												stato = new Long(2);
												break;
											case 400:
												for(ValidateEntry errore:risultatoPubblicazioneProgrammaFS.getValidate()) {
													msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio() + "\r\n";
												}
												break;
											default:
												msg = risultatoPubblicazioneProgrammaFS.getError();
												break;
										}
										break;
									case 991:
									case 992:
										//programma DM11/2011
										PubblicaProgrammaDM112011Entry programmaDM112011 = new PubblicaProgrammaDM112011Entry();
										this.valorizzaProgrammaDM112011(programmaDM112011, key01);
										json = mapper.writeValueAsString(programmaDM112011);
										webTarget = client.target(urlProgrammi).path("Programmi/PubblicaDM112011").queryParam("token", token).queryParam("modalitaInvio", "2");
										Response risultatoProgrammaDM112011 = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(programmaDM112011), Response.class);
										esito = risultatoProgrammaDM112011.getStatus();
										PubblicazioneResult risultatoPubblicazioneProgrammaDM112011 = risultatoProgrammaDM112011.readEntity(PubblicazioneResult.class);
										switch (esito) {
											case 200:
												programmaDM112011.setIdRicevuto(risultatoPubblicazioneProgrammaDM112011.getId());
												this.sqlManager.update(updatePIATRIIdRicevuto, new Object[] {programmaDM112011.getIdRicevuto(), key01});
												idRicevuto = risultatoPubblicazioneProgrammaDM112011.getId();
												msg = "ok";
												stato = new Long(2);
												break;
											case 400:
												for(ValidateEntry errore:risultatoPubblicazioneProgrammaDM112011.getValidate()) {
													msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio() + "\r\n";
												}
												break;
											default:
												msg = risultatoPubblicazioneProgrammaDM112011.getError();
												break;
										}
										break;
									default:
										break;
									}
						    	}
							} else {
								msg = "CODEIN nullo";
								logger.info("Flusso IDCOMUN = " + itemW9OUTBOX.get(0).toString() + " non inviato, CODEIN nullo");
							}
						} catch(Exception ex) {
							msg = "Errore non previsto " + ex.getMessage();
							logger.error(ex);
						}
						//aggiorno record tabella W9OUTBOX
						if (msg.length()> 2000) {
							msg = msg.substring(0,1999);
						}
						this.sqlManager.update(updateW9OUTBOX, new Object[] {new Timestamp(System.currentTimeMillis()), json, stato, msg, idRicevuto, idComun});
						this.sqlManager.commitTransaction(status);
					}
			    } else {
			    	logger.error("L'accesso al servizio di login ha restituito questo errore : " + resultAccesso.getError());
			    }
			}
		} catch(Exception ex) {
			logger.error(ex);
		}
		logger.info("exportORtoSCP: fine metodo");
	}
	
	
	/**
	 * Utility per il controllo dei valori in arrivo.
	 * 
	 * @param obj Object
	 * @return Ritorna true se obj e' diversa da null, false altrimenti
	 */
	private boolean verificaEsistenzaValore(final Object obj) {
		boolean esistenza;
		if (obj != null && !obj.toString().trim().equals("")) {
			esistenza = true;
		} else {
			esistenza = false;
		}
		return esistenza;
	}

	
}

