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
package it.eldasoft.w9.bl;

import it.avlp.simog.massload.xmlbeans.ChiudiSessione;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument;
import it.avlp.simog.massload.xmlbeans.ChiudiSessioneResponseDocument;
import it.avlp.simog.massload.xmlbeans.Collaborazione;
import it.avlp.simog.massload.xmlbeans.Collaborazioni;
import it.avlp.simog.massload.xmlbeans.Login;
import it.avlp.simog.massload.xmlbeans.LoginDocument;
import it.avlp.simog.massload.xmlbeans.LoginResponseDocument;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.avlp.simog.massload.xmlbeans.loader.AccordiBonariType;
import it.avlp.simog.massload.xmlbeans.loader.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.loader.AdesioneType;
import it.avlp.simog.massload.xmlbeans.loader.AggiudicatariType;
import it.avlp.simog.massload.xmlbeans.loader.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.loader.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.loader.AnomaliaType;
import it.avlp.simog.massload.xmlbeans.loader.AppaltoAdesioneType;
import it.avlp.simog.massload.xmlbeans.loader.AppaltoType;
import it.avlp.simog.massload.xmlbeans.loader.AvanzamentiType;
import it.avlp.simog.massload.xmlbeans.loader.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.loader.CUPLOTTOType;
import it.avlp.simog.massload.xmlbeans.loader.CollaudoType;
import it.avlp.simog.massload.xmlbeans.loader.ConclusioneType;
import it.avlp.simog.massload.xmlbeans.loader.CondizioneType;
import it.avlp.simog.massload.xmlbeans.loader.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.loader.DatiCUPType;
import it.avlp.simog.massload.xmlbeans.loader.DatiCollaudoType;
import it.avlp.simog.massload.xmlbeans.loader.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.loader.DatiInizioType;
import it.avlp.simog.massload.xmlbeans.loader.DatiStipulaType;
import it.avlp.simog.massload.xmlbeans.loader.DittaAusiliariaType;
import it.avlp.simog.massload.xmlbeans.loader.FinanziamentoType;
import it.avlp.simog.massload.xmlbeans.loader.FlagEsitoCollaudoType;
import it.avlp.simog.massload.xmlbeans.loader.FlagRitardoType;
import it.avlp.simog.massload.xmlbeans.loader.FlagSNType;
import it.avlp.simog.massload.xmlbeans.loader.FlagSOType;
import it.avlp.simog.massload.xmlbeans.loader.FlagTCType;
import it.avlp.simog.massload.xmlbeans.loader.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.loader.InizioType;
import it.avlp.simog.massload.xmlbeans.loader.LoaderAppalto;
import it.avlp.simog.massload.xmlbeans.loader.LoaderAppaltoDocument;
import it.avlp.simog.massload.xmlbeans.loader.LoaderAppaltoResponseDocument;
import it.avlp.simog.massload.xmlbeans.loader.LoaderAppaltoWSServiceStub;
import it.avlp.simog.massload.xmlbeans.loader.PosizioneType;
import it.avlp.simog.massload.xmlbeans.loader.PubblicazioneType;
import it.avlp.simog.massload.xmlbeans.loader.RecIdSchedaElimType;
import it.avlp.simog.massload.xmlbeans.loader.RecIdSchedaInsType;
import it.avlp.simog.massload.xmlbeans.loader.RecMotivoVarType;
import it.avlp.simog.massload.xmlbeans.loader.RecVarianteType;
import it.avlp.simog.massload.xmlbeans.loader.RequisitoType;
import it.avlp.simog.massload.xmlbeans.loader.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.loader.ResponsabiliType;
import it.avlp.simog.massload.xmlbeans.loader.RitardiType;
import it.avlp.simog.massload.xmlbeans.loader.RitardoType;
import it.avlp.simog.massload.xmlbeans.loader.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.loader.SchedaEsclusoType;
import it.avlp.simog.massload.xmlbeans.loader.SchedaSottosogliaType;
import it.avlp.simog.massload.xmlbeans.loader.SezioneType;
import it.avlp.simog.massload.xmlbeans.loader.SoggAggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.loader.SospensioneType;
import it.avlp.simog.massload.xmlbeans.loader.SospensioniType;
import it.avlp.simog.massload.xmlbeans.loader.SottoEsclusoType;
import it.avlp.simog.massload.xmlbeans.loader.StipulaType;
import it.avlp.simog.massload.xmlbeans.loader.SubappaltiType;
import it.avlp.simog.massload.xmlbeans.loader.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.loader.TipiAppaltoType;
import it.avlp.simog.massload.xmlbeans.loader.TipiSchedeType;
import it.avlp.simog.massload.xmlbeans.loader.TipoSchedaType;
import it.avlp.simog.massload.xmlbeans.loader.TrasferimentoDatiDocument;
import it.avlp.simog.massload.xmlbeans.loader.TrasferimentoDatiDocument.TrasferimentoDati;
import it.avlp.simog.massload.xmlbeans.loader.TrasferimentoType;
import it.avlp.simog.massload.xmlbeans.loader.VarianteType;
import it.avlp.simog.massload.xmlbeans.loader.VariantiType;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.w9.bl.simog.TicketSimogBean;
import it.eldasoft.w9.bl.simog.TicketSimogManager;
import it.eldasoft.w9.common.CostantiSimog;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.tags.gestori.submit.GestoreW9FASI;
import it.eldasoft.w9.utils.QueryExtractor;
import it.eldasoft.w9.utils.UtilityDateExtension;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.ErroreType;
import it.toscana.rete.rfc.sitat.types.ErroreType.Tipo;
import it.toscana.rete.rfc.sitat.types.EsitoType.DettaglioErrori;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlValidationError;
import org.springframework.transaction.TransactionStatus;

/**
 * Classe di utilita' per l'esportazione in formato XML.
 * 
 * @author Stefano.Cestaro
 * @edit Eliseo Marini
 * @version Adeguamento SIMOG 3.0.2
 * 
 */
public class EsportazioneXMLSIMOGManager {
	/** Logger. */
	static Logger logger = Logger.getLogger(EsportazioneXMLSIMOGManager.class);

	// Estrazione delle fasi da esportare, cioe' quelle con W9FASI.DAEXPORT='1'
	// e relative ad una aggiudicazione (o equivalente) con W9APPA.CODCUI valorizzato
	private final String SQL_FASI_DA_ESPORTARE = 
	    "select f.CODGARA, f.CODLOTT, f.FASE_ESECUZIONE, f.NUM "
		 + " from W9FASI f left join (select * from TAB1 where TAB1COD='W3020') tab on f.FASE_ESECUZIONE = TAB.TAB1TIP "
		               + " left join W9APPA a on f.CODGARA=a.CODGARA and f.CODLOTT=a.CODLOTT and f.NUM_APPA=a.NUM_APPA "
		+ " where f.DAEXPORT='1' and (a.CODCUI is not null or f.FASE_ESECUZIONE in (1,12,987,984)) "
 + " order by f.CODGARA, f.CODLOTT, f.NUM_APPA, TAB.TAB1NORD";

	/** Manager SQL per le operazioni su database. */
	private SqlManager sqlManager;

	/** Manager XML per la gestione delle query. */
	private QueryExtractor queryExtractor = null;
	
	/** Manager per ottenere il ticket di sessione per le richieste a Simog */
	private TicketSimogManager ticketSimogManager = null;
	
	private W9Manager w9Manager;
	
	/** Costruttore di default. */
	public EsportazioneXMLSIMOGManager() {
		this.queryExtractor = new QueryExtractor();
	}

	/**
	 * Set SqlManager.
	 * 
	 * @param sqlManager
	 *            sqlManager
	 */
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	/**
	   * Set W9Manager.
	   * @param w9Manager the w9Manager to set
	   */
	  public void setW9Manager(W9Manager w9Manager) {
	    this.w9Manager = w9Manager;
	  }
	  
	/**
   * @param ticketSimogManager the ticketSimogManager to set
   */
  public void setTicketSimogManager(TicketSimogManager ticketSimogManager) {
    this.ticketSimogManager = ticketSimogManager;
  }

  /**
	 * Dati comuni a tutte le fasi/eventi del lotto (contratto).
	 * 
	 * @param datiComuni
	 *            DatiComuniType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setDatiComuni(final DatiComuniType datiComuni, final Long codgara, final Long codlott) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiComuni: inizio metodo");
		}

		String selectW9DatiComuni = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9DATICOMUNI");

		try {
			List<?> datiW9DatiComuni = this.sqlManager.getVector(selectW9DatiComuni, new Object[] { codgara, codlott });

			if (datiW9DatiComuni != null && datiW9DatiComuni.size() > 0) {

				// Valorizzazione forzata.
				// Si tratta del codice fiscale e della denominazione amministrazione
				// per la quale l’amministrazione ha indetto la gara.
				// Nel file XSD sono campi obbligatori, ma non sempre possono essere valorizzati.
				datiComuni.setCFAMMAGENTE("");
				datiComuni.setDENAMMAGENTE("");

				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 0).getValue() != null) {
					datiComuni.setCIG(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 0).toString());
				}

				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 1).getValue() != null) {
					datiComuni.setFLAGENTESPECIALE(FlagSOType.Enum.forString(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 1).toString()));
				}

				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 2).getValue() != null) {
					datiComuni.setTIPOCONTRATTO(TipoSchedaType.Enum.forString(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 2).toString()));
				}

				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 5).getValue() != null) {
					datiComuni.setCODICECC(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 5).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 6).getValue() != null) {
					datiComuni.setDENOMCC(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 6).toString());
				}

				int versioneSimog = UtilitySITAT.getVersioneSimog(this.sqlManager, codgara);
				if (versioneSimog >= 5) {
				  datiComuni.setFLAGSAAGENTE(FlagSNType.N);
				} else {
				  if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 3).getValue() != null) {
	          datiComuni.setCFAMMAGENTE(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 3).toString());
	        }
	        if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 4).getValue() != null) {
	          datiComuni.setDENAMMAGENTE(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 4).toString());
	        }
	        if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 7).longValue() != null) {
	          String valore = SqlManager.getValueFromVectorParam(datiW9DatiComuni, 7).longValue().toString();
	          datiComuni.setIDTIPOLOGIASA(valore);
	        }
	        
  				JdbcParametro flagSN = SqlManager.getValueFromVectorParam(datiW9DatiComuni, 8);
  				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
  					if ("1".equals(flagSN.getStringValue())) {
  						datiComuni.setFLAGSAAGENTE(FlagSNType.S);
  					} else {
  						datiComuni.setFLAGSAAGENTE(FlagSNType.N);
  					}
  				}
				}
				
				// Codice fiscale del RUP ricavato dall'anagrafica dei tecnici
				try {
					String selectW9INCARUP = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9RUP");

					List<?> datiW9INCARUP = this.sqlManager.getListVector(selectW9INCARUP, new Object[] { codgara, codlott });

					if (datiW9INCARUP != null && datiW9INCARUP.size() > 0) {
						String cftec = (String) SqlManager.getValueFromVectorParam(datiW9INCARUP.get(0), 0).getValue();
						String pivatec = (String) SqlManager.getValueFromVectorParam(datiW9INCARUP.get(0), 1).getValue();

						if (cftec != null) {
							datiComuni.setCFRUP(cftec);
						} else {
							datiComuni.setCFRUP(pivatec);
						}
					}
				} catch (SQLException e) {
					throw new GestoreException("Errore nella lettura del codice fiscale/partita iva del RUP", "datiComuniType", e);
				}

				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 10).getValue() != null) {
					datiComuni.setIDCATEGSA(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 10).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 13).getValue() != null) {
					datiComuni.setCFAMM(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 13).toString());
					datiComuni.setCFSA(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 13).toString());
				}

				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 14).getValue() != null) {
					datiComuni.setDENAMM(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 14).toString());
					datiComuni.setDENSA(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 14).toString());
				}

				// Se il campo W9ESITO.ESITO_PROCEDURA e' valorizzato si valorizzato il campo ESITO_PROCEDURA
				// dell'oggetto DatiComuni con il valore di tale campo, altrimenti si valorizza a 1	il campo
				// ESITO_PROCEDURA dell'oggetto DatiComuni
				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 19).getValue() != null) {
					datiComuni.setESITOPROCEDURA(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 19).getStringValue());
				} else {
					datiComuni.setESITOPROCEDURA("1");
				}

				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 16).getValue() != null) {
					datiComuni.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 16).toString());
				}

				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 17).getValue() != null) {
					datiComuni.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 17).toString());
				}

				if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 18).getValue() != null) {
					datiComuni.setTIPOLOGIAPROCEDURA(SqlManager.getValueFromVectorParam(datiW9DatiComuni, 18).toString());
				}
				
				JdbcParametro flagSN = SqlManager.getValueFromVectorParam(datiW9DatiComuni, 20);
        if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
          if ("1".equals(flagSN.getStringValue())) {
            datiComuni.setFLAGCENTRALESTIPULA(FlagSNType.S);
          } else {
            datiComuni.setFLAGCENTRALESTIPULA(FlagSNType.N);
          }
        }

        if (versioneSimog < 3) {
          if (SqlManager.getValueFromVectorParam(datiW9DatiComuni, 21).getValue() != null) {
            datiComuni.setDURATAACCQUADROCONVENZIONE(SqlManager.getValueFromVectorParam(
                datiW9DatiComuni, 21).longValue().intValue());
          }
        }
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati comuni del lotto (contratto)", "datiComuniType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiComuni: fine metodo");
		}
	}

	/**
	 * Dati di pubblicazione (informazioni relative alla pubblicazione del
	 * bando).
	 * 
	 * @param pubblicazione
	 *            PubblicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @return Ritorna true se la pubblicazione e' stata settata correttamente,
	 *         false altrimenti (in caso di SQLException)
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private boolean setPubblicazione(final PubblicazioneType pubblicazione, final Long codgara, final Long codlott) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setPubblicazione: inizio metodo");
		}
		String selectW9PUBB = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9PUBB");
		boolean result = false;
		try {
			List<?> datiW9PUBB = this.sqlManager.getVector(selectW9PUBB, new Object[] { codgara, codlott });

			if (datiW9PUBB != null && datiW9PUBB.size() > 0) {

				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 0).getValue() != null) {
					pubblicazione.setDATAGUCE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9PUBB, 0)));
					result = true;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 1).getValue() != null) {
					pubblicazione.setDATAGURI(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9PUBB, 1)));
					result = true;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 2).getValue() != null) {
					pubblicazione.setDATAALBO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9PUBB, 2)));
					result = true;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 3).getValue() != null) {
					pubblicazione.setQUOTIDIANINAZ(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9PUBB, 3).toString()));
					result = true;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 4).getValue() != null) {
					pubblicazione.setQUOTIDIANIREG(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9PUBB, 4).toString()));
					result = true;
				}

				JdbcParametro flagSN = SqlManager.getValueFromVectorParam(datiW9PUBB, 5);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.S);
					} else {
						pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.N);
					}
					result = true;
				}
				flagSN = SqlManager.getValueFromVectorParam(datiW9PUBB, 6);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.S);
					} else {
						pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.N);
					}
					result = true;
				}
				flagSN = SqlManager.getValueFromVectorParam(datiW9PUBB, 7);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.S);
					} else {
						pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.N);
					}
					result = true;
				}

				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 8).getValue() != null) {
					pubblicazione.setDATABORE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9PUBB, 8)));
					result = true;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 9).getValue() != null) {
					pubblicazione.setPERIODICI(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9PUBB, 9).toString()));
					result = true;
				}

				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 10).getValue() != null) {
					pubblicazione.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9PUBB, 10).toString());
					result = true;
				}

				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 11).getValue() != null) {
					pubblicazione.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9PUBB, 11).toString());
					result = true;
				}
			}

		} catch (SQLException e) {
			result = false;
			throw new GestoreException("Errore nella lettura dei dati pubblicazione", "pubblicazioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setPubblicazione: fine metodo");
		}

		return result;
	}

	/**
	 * Dati dell'appalto relativi alla fase di aggiudicazione per contratti
	 * sopra soglia.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAppalto(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAppalto: inizio metodo");
		}

		String selectW9APPA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPA");

		try {
			List<?> datiW9APPA = this.sqlManager.getVector(selectW9APPA, new Object[] { codgara, codlott, numappa });

			if (datiW9APPA != null && datiW9APPA.size() > 0) {
				AppaltoType appalto = aggiudicazione.addNewAppalto();
				JdbcParametro flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 0);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appalto.setPROCEDURAACC(FlagSNType.S);
					} else {
						appalto.setPROCEDURAACC(FlagSNType.N);
					}
				}

				flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 1);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appalto.setPREINFORMAZIONE(FlagSNType.S);
					} else {
						appalto.setPREINFORMAZIONE(FlagSNType.N);
					}
				}

				flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 2);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appalto.setTERMINERIDOTTO(FlagSNType.S);
					} else {
						appalto.setTERMINERIDOTTO(FlagSNType.N);
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 3).getValue() != null) {
					appalto.setIDMODOINDIZIONE(SqlManager.getValueFromVectorParam(datiW9APPA, 3).getStringValue());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 4).getValue() != null) {
					appalto.setIDMODOGARA(SqlManager.getValueFromVectorParam(datiW9APPA, 4).getStringValue());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 5).getValue() != null) {
					appalto.setNUMIMPRESEINVITATE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPA, 5).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 6).getValue() != null) {
					appalto.setNUMIMPRESERICHIEDENTI(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPA, 6).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 7).getValue() != null) {
					appalto.setNUMIMPRESEOFFERENTI(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPA, 7).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 8).getValue() != null) {
					appalto.setNUMOFFERTEAMMESSE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPA, 8).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 9).getValue() != null) {
					appalto.setDATAVERBAGGIUDICAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 9)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 10).getValue() != null) {
	        Long counter = (Long) sqlManager.getObject(
	            "select count(*) from W9LOTT where ID_SCELTA_CONTRAENTE in (2, 9) and CODGARA=? and CODLOTT=?", 
	                new Object[] { codgara, codlott } );
	        if (counter.longValue() > 0) {
	          appalto.setDATASCADENZARICHIESTAINVITO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 10)));
	        }
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 11).getValue() != null) {
					appalto.setDATASCADENZAPRESOFFERTA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 11)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 12).getValue() != null) {
					appalto.setIMPORTOAGGIUDICAZIONE(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 12).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 13).getValue() != null) {
					appalto.setIDSCELTACONTRAENTE(SqlManager.getValueFromVectorParam(datiW9APPA, 13).getStringValue());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 14).getValue() != null) {
					appalto.setIMPORTOLAVORI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 14).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 15).getValue() != null) {
					appalto.setIMPORTOSERVIZI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 15).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 16).getValue() != null) {
					appalto.setIMPORTOFORNITURE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 16).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 17).getValue() != null) {
					appalto.setIMPORTOATTUAZIONESICUREZZA(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 17).doubleValue())));
				} else {
					appalto.setIMPORTOATTUAZIONESICUREZZA(new BigDecimal(0));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 18).getValue() != null) {
					appalto.setIMPORTODISPOSIZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 18).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 19).getValue() != null) {
					appalto.setIMPORTOPROGETTAZIONE(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 19).doubleValue()));
				}

				flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 20);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appalto.setSISTEMAQUALIFICAZIONE(FlagSNType.S);
					} else {
						appalto.setSISTEMAQUALIFICAZIONE(FlagSNType.N);
					}
				}

				flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 21);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appalto.setCRITERISELEZIONESTABILITISA(FlagSNType.S);
					} else {
						appalto.setCRITERISELEZIONESTABILITISA(FlagSNType.N);
					}
				}

				if (SqlManager.getValueFromVectorParam(datiW9APPA, 22).getValue() != null) {
					appalto.setIDTIPOPRESTAZIONE(SqlManager.getValueFromVectorParam(datiW9APPA, 22).getStringValue());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 23).getValue() != null) {
					appalto.setCUP(SqlManager.getValueFromVectorParam(datiW9APPA, 23).getStringValue());
					appalto.setFLAGCUP(FlagSNType.S.toString());
				} else {
				  appalto.setCUP("");
					appalto.setFLAGCUP(FlagSNType.N.toString());
				}

				flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 24);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appalto.setFLAGACCORDOQUADRO(FlagSNType.S);
					} else {
						appalto.setFLAGACCORDOQUADRO(FlagSNType.N);
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 25).getValue() != null) {
					appalto.setLUOGOISTAT(SqlManager.getValueFromVectorParam(datiW9APPA, 25).getStringValue());
				} else if (SqlManager.getValueFromVectorParam(datiW9APPA, 26).getValue() != null) {
					appalto.setLUOGONUTS(SqlManager.getValueFromVectorParam(datiW9APPA, 26).getStringValue());
				}
				flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 27);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appalto.setASTAELETTRONICA(FlagSNType.S.toString());
					} else {
						appalto.setASTAELETTRONICA(FlagSNType.N.toString());
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 28).getValue() != null) {
					appalto.setPERCRIBASSOAGG(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 28).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 29).getValue() != null) {
					appalto.setPERCOFFAUMENTO(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 29).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 30).getValue() != null) {
					appalto.setDATAINVITO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 30)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 31).getValue() != null) {
					appalto.setNUMMANIFINTERESSE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPA, 31).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 32).getValue() != null) {
					appalto.setDATAMANIFINTERESSE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPA, 32)));
				}

				flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 33);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appalto.setFLAGRICHSUBAPPALTO(FlagSNType.S.toString());
					} else {
						appalto.setFLAGRICHSUBAPPALTO(FlagSNType.N.toString());
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 34).getValue() != null) {
					appalto.setNUMOFFERTEESCLUSE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPA, 34).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 35).getValue() != null) {
					appalto.setOFFERTAMASSIMO(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 35).doubleValue())));
				}

				if (SqlManager.getValueFromVectorParam(datiW9APPA, 36).getValue() != null) {
					appalto.setOFFERTAMINIMA(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 36).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 37).getValue() != null) {
					appalto.setVALSOGLIAANOMALIA(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 37).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 38).getValue() != null) {
					appalto.setNUMOFFERTEFUORISOGLIA(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPA, 38).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 39).getValue() != null) {
					appalto.setNUMIMPESCLINSUFGIUST(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPA, 39).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 40).getValue() != null) {
					appalto.setCODSTRUMENTO(SqlManager.getValueFromVectorParam(datiW9APPA, 40).getStringValue());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 44).getValue() != null) {
					appalto.setIMPNONASSOG(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPA, 44).doubleValue()));
				}

				flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 45);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appalto.setOPEREURBANIZSCOMPUTO(FlagSNType.S);
					} else {
						appalto.setOPEREURBANIZSCOMPUTO(FlagSNType.N);
					}
				}

				if (SqlManager.getValueFromVectorParam(datiW9APPA, 46).getValue() != null) {
					appalto.setMODALITARIAGGIUDICAZIONE(SqlManager.getValueFromVectorParam(datiW9APPA, 46).getStringValue());
				}

				if (SqlManager.getValueFromVectorParam(datiW9APPA, 47).getValue() != null) {
					appalto.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9APPA, 47).toString());
				}

				if (SqlManager.getValueFromVectorParam(datiW9APPA, 48).getValue() != null) {
					appalto.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9APPA, 48).toString());
				}
				
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 49).getValue() != null) {
					appalto.setCODICECONTRATTO(SqlManager.getValueFromVectorParam(datiW9APPA, 49).toString());
					String codiceCIG = (String) this.sqlManager.getObject("select CIG from W9LOTT where CODGARA = ? and CODLOTT = ?", new Object[] { codgara, codlott});
					if (codiceCIG.equalsIgnoreCase(appalto.getCODICECONTRATTO())) {
						appalto.setFLAGAGGIUDPRINCIPALE(FlagSNType.S);
					} else {
						appalto.setFLAGAGGIUDPRINCIPALE(FlagSNType.N);
					}
				}

				//Relazione unica?
				if (UtilitySITAT.getVersioneSimog(this.sqlManager, codgara) >= 6) {
					flagSN = SqlManager.getValueFromVectorParam(datiW9APPA, 50);
					if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
						if ("1".equals(flagSN.getStringValue())) {
							appalto.setRELAZIONEUNICA(FlagSNType.S);
						} else {
							appalto.setRELAZIONEUNICA(FlagSNType.N);
						}
					}
				}
				
				if (numappa > 1) {//Progressivo del CUI relativo alla aggiudicazione cha ha avuto una risoluzione anticipata ed è stata riaggiudicata con la scheda che si sta inviando
					String selectW9CUI = "SELECT CODCUI FROM W9APPA WHERE CODGARA=? AND CODLOTT=? AND NUM_APPA=?";
					String codiceCUI = (String) this.sqlManager.getObject(selectW9CUI, new Object[] { codgara, codlott, new Long(numappa - 1) });
					if (codiceCUI != null) {
						if (codiceCUI.indexOf("-") != - 1) {
							String aggiudicazioneCUI  = codiceCUI.substring(codiceCUI.indexOf("-") + 1);
							appalto.setPROGCUIRIAGGIUDICATO(Integer.parseInt(aggiudicazioneCUI));
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati di aggiudicazione per contratti sopra soglia", "AppaltoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAppalto: fine metodo");
		}
	}

	/**
	 * Dati di aggiudicazione semplificata per contratti sottosoglia.
	 * 
	 * @param schedaSottoSoglia
	 *            SchedaSottoSogliaType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAppaltoSottoSoglia(final SchedaSottosogliaType schedaSottoSoglia, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAppaltoSottoSoglia: inizio metodo");
		}
		String selectW9APPASOTTOSOGLIA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPA_SOTTOSOGLIA");

		try {
			List<?> datiW9APPASOTTOSOGLIA = this.sqlManager.getVector(selectW9APPASOTTOSOGLIA, new Object[] { codgara, codlott, numappa });

			if (datiW9APPASOTTOSOGLIA != null && datiW9APPASOTTOSOGLIA.size() > 0) {

				SottoEsclusoType sottoEscluso = schedaSottoSoglia.addNewAppalto();

				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 0).getValue() != null) {
					sottoEscluso.setLUOGOISTAT(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 0).getStringValue());
				} else if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 1).getValue() != null) {
					sottoEscluso.setLUOGONUTS(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 1).getStringValue());
				} 
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 2).getValue() != null) {
					sottoEscluso.setCUP(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 2).getStringValue());
					sottoEscluso.setFLAGCUP(FlagSNType.S.toString());
				} else {
					sottoEscluso.setFLAGCUP(FlagSNType.N.toString());
				}

				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 3).getValue() != null) {
					sottoEscluso.setIMPORTOCOMPLESSIVO(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 3).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 4).getValue() != null) {
					sottoEscluso.setIMPORTODISPOSIZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 4).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 5).getValue() != null) {
					sottoEscluso.setIDSCELTACONTRAENTE(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 5).getStringValue());
				}
				JdbcParametro flagSN = SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 6);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						sottoEscluso.setASTAELETTRONICA(FlagSNType.S.toString());
					} else {
						sottoEscluso.setASTAELETTRONICA(FlagSNType.N.toString());
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 7).getValue() != null) {
					sottoEscluso.setPERCRIBASSOAGG(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 7).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 8).getValue() != null) {
					sottoEscluso.setPERCOFFAUMENTO(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 8).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 9).getValue() != null) {
					sottoEscluso.setIMPORTOAGGIUDICAZIONE(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 9).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 10).getValue() != null) {
					sottoEscluso.setDATAAGGIUDICAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 10)).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 11).getValue() != null) {
					sottoEscluso.setDATASTIPULA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 11)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 12).getValue() != null) {
					sottoEscluso.setTERMINECONTRATTUALE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 12)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 13).getValue() != null) {
					sottoEscluso.setDURATACONTRATTUALE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 13).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 14).getValue() != null) {
					sottoEscluso.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 14).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 15).getValue() != null) {
					sottoEscluso.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9APPASOTTOSOGLIA, 15).toString());
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati di aggiudicazione per contratti sottosoglia", "AppaltoSottoSogliaType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAppaltoSottoSoglia: fine metodo");
		}
	}

	/**
	 * Dati di aggiudicazione semplificata per contratti esclusi.
	 * 
	 * @param schedaEscluso
	 *            SchedaEsclusoType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAppaltoEscluso(final SchedaEsclusoType schedaEscluso, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("setAppaltoEscluso: inizio metodo");
		}
		String selectW9APPAESCLUSO = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPA_ESCLUSO");

		try {
			List<?> datiW9APPAESCLUSO = this.sqlManager.getVector(selectW9APPAESCLUSO, new Object[] { codgara, codlott, numappa });

			if (datiW9APPAESCLUSO != null && datiW9APPAESCLUSO.size() > 0) {

				SottoEsclusoType sottoEscluso = schedaEscluso.addNewAppalto();

				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 0).getValue() != null) {
					sottoEscluso.setLUOGOISTAT(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 0).getStringValue());
				} else if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 1).getValue() != null) {
					sottoEscluso.setLUOGONUTS(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 1).getStringValue());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 2).getValue() != null) {
					sottoEscluso.setCUP(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 2).getStringValue());
					sottoEscluso.setFLAGCUP(FlagSNType.S.toString());
				} else {
					sottoEscluso.setFLAGCUP(FlagSNType.N.toString());
				}

				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 3).getValue() != null) {
					sottoEscluso.setIMPORTOCOMPLESSIVO(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 3).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 4).getValue() != null) {
					sottoEscluso.setIMPORTODISPOSIZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 4).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 5).getValue() != null) {
					sottoEscluso.setIDSCELTACONTRAENTE(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 5).getStringValue());
				}
				JdbcParametro flagSN = SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 6);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						sottoEscluso.setASTAELETTRONICA(FlagSNType.S.toString());
					} else {
						sottoEscluso.setASTAELETTRONICA(FlagSNType.N.toString());
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 7).getValue() != null) {
					sottoEscluso.setPERCRIBASSOAGG(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 7).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 8).getValue() != null) {
					sottoEscluso.setPERCOFFAUMENTO(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 8).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 9).getValue() != null) {
					sottoEscluso.setIMPORTOAGGIUDICAZIONE(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 9).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 10).getValue() != null) {
					sottoEscluso.setDATAAGGIUDICAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 10)).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 11).getValue() != null) {
					sottoEscluso.setDATASTIPULA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 11)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 12).getValue() != null) {
					sottoEscluso.setTERMINECONTRATTUALE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 12)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 13).getValue() != null) {
					sottoEscluso.setDURATACONTRATTUALE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 13).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 14).getValue() != null) {
					sottoEscluso.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 14).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 15).getValue() != null) {
					sottoEscluso.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9APPAESCLUSO, 15).toString());
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati di aggiudicazione per contratti esclusi", "AppaltoEsclusoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAppaltoEscluso: fine metodo");
		}
	}

	/**
	 * Dati di adesione all'accordo quadro.
	 * 
	 * @param adesione
	 *            AdesioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAppaltoAdesione(final AdesioneType adesione, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAppaltoAdesione: inizio metodo");
		}
		String selectW9APPAADESIONE = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPA_ADESIONE");

		try {
			List<?> datiW9APPAADESIONE = this.sqlManager.getVector(selectW9APPAADESIONE, new Object[] { codgara, codlott, numappa });

			if (datiW9APPAADESIONE != null && datiW9APPAADESIONE.size() > 0) {

				AppaltoAdesioneType appaltoAdesione = adesione.addNewAppalto();

				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 0).getValue() != null) {
					appaltoAdesione.setLUOGOISTAT(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 0).getStringValue());
				} else if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 1).getValue() != null) {
					appaltoAdesione.setLUOGONUTS(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 1).getStringValue());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 2).getValue() != null) {
					appaltoAdesione.setCODSTRUMENTO(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 2).getStringValue());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 3).getValue() != null) {
					appaltoAdesione.setIMPORTOLAVORI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 3).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 4).getValue() != null) {
					appaltoAdesione.setIMPORTOSERVIZI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 4).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 5).getValue() != null) {
					appaltoAdesione.setIMPORTOFORNITURE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 5).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 6).getValue() != null) {
					appaltoAdesione.setPERCRIBASSOAGG(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 6).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 7).getValue() != null) {
					appaltoAdesione.setPERCOFFAUMENTO(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 7).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 8).getValue() != null) {
					appaltoAdesione.setIMPORTOAGGIUDICAZIONE(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 8).doubleValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 9).getValue() != null) {
					appaltoAdesione.setDATAAGGIUDICAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 9)).toString());
				}
				JdbcParametro flagSN = SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 10);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						appaltoAdesione.setFLAGRICHSUBAPPALTO(FlagSNType.S.toString());
					} else {
						appaltoAdesione.setFLAGRICHSUBAPPALTO(FlagSNType.N.toString());
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 11).getValue() != null) {
					appaltoAdesione.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 11).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 12).getValue() != null) {
					appaltoAdesione.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9APPAADESIONE, 12).toString());
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati di aggiudicazione per adesione ad accordo quadro", "AppaltoAdesioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAppaltoAdesione: fine metodo");
		}
	}

	/**
	 * Dati dell'accordo quadro e dei termini di esecuzione.
	 * 
	 * @param datiStipula
	 *            DatiStipulaType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num_iniz
	 *            progressivo
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setStipula(final DatiStipulaType datiStipula, final Long codgara, final Long codlott, final Long num_iniz) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setStipula: inizio metodo");
		}

		String selectW9INIZSTIPULA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9INIZ_STIPULA");

		try {
			List<?> datiW9INIZSTIPULA = this.sqlManager.getVector(selectW9INIZSTIPULA, new Object[] { codgara, codlott, num_iniz });

			if (datiW9INIZSTIPULA != null && datiW9INIZSTIPULA.size() > 0) {
			  
				StipulaType stipula = StipulaType.Factory.newInstance();

				if (SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 0).getValue() != null) {
					stipula.setDATASTIPULA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 0)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 1).getValue() != null) {
					stipula.setDATADECORRRENZA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 1)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 2).getValue() != null) {
					stipula.setDATASCADENZA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 2)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 3).getValue() != null) {
					stipula.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 3).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 4).getValue() != null) {
					stipula.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9INIZSTIPULA, 4).toString());
				}
				
				datiStipula.addNewStipula();
				datiStipula.setStipula(stipula);
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati di stipula dell'accordo quadro", "DatiStipulaType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setStipula: fine metodo");
		}
	}

	/**
	 * Lista delle tipologie lavoro.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setTipiAppaltoLav(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setTipiAppaltoLav: inizio metodo");
		}
		String selectW9APPALAV = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPALAV");

		try {
			List<?> datiW9APPALAV = this.sqlManager.getListVector(selectW9APPALAV, new Object[] { codgara, codlott, numappa });

			if (datiW9APPALAV != null && datiW9APPALAV.size() > 0) {
				ListIterator<?> iterator = datiW9APPALAV.listIterator();

				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					JdbcParametro idAppalto = SqlManager.getValueFromVectorParam(riga, 0);

					if (idAppalto.getValue() != null && !"".equals(idAppalto.getStringValue())) {
						TipiAppaltoType tipiAppalto = aggiudicazione.addNewTipiAppaltoLav();
						tipiAppalto.setIDAPPALTO(idAppalto.getStringValue());
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati delle tipologia di lavorazioni nell'aggiudicazione", "TipiAppaltoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setTipiAppaltoLav: fine metodo");
		}
	}

	/**
	 * Lista delle modalita' di acquisizione forniture/servizi.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setTipiAppaltoForn(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setTipiAppaltoForn: inizio metodo");
		}
		String selectW9APPAFORN = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPAFORN");

		try {
			List<?> datiW9APPAFORN = this.sqlManager.getListVector(selectW9APPAFORN, new Object[] { codgara, codlott, numappa });

			if (datiW9APPAFORN != null && datiW9APPAFORN.size() > 0) {
				ListIterator<?> iterator = datiW9APPAFORN.listIterator();

				while (iterator.hasNext()) {
					List<?> riga = (List<?>) iterator.next();
					JdbcParametro idAppalto = SqlManager.getValueFromVectorParam(riga, 0);

					if (idAppalto.getValue() != null && !"".equals(idAppalto.getStringValue())) {
						TipiAppaltoType tipiAppaltoForn = aggiudicazione.addNewTipiAppaltoForn();
						tipiAppaltoForn.setIDAPPALTO(idAppalto.getStringValue());
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati della modalita' di acquisizione forniture/servizi", "TipiAppaltoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setTipiAppaltoForn: fine metodo");
		}
	}

	/**
	 * Lista delle condizioni che giustificano il ricorso alla procedura
	 * negoziata.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setCondizioni(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setCondizioni: inizio metodo");
		}

		String selectW9COND = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9COND");

		try {
			List<?> datiW9COND = this.sqlManager.getListVector(selectW9COND, new Object[] { codgara, codlott, numappa });

			if (datiW9COND != null && datiW9COND.size() > 0) {
				ListIterator<?> iterator = datiW9COND.listIterator();

				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					JdbcParametro idCondizione = SqlManager.getValueFromVectorParam(riga, 0);
					if (idCondizione.getValue() != null && !"".equals(idCondizione.getStringValue())) {
						CondizioneType condizione = aggiudicazione.addNewCondizioni();
						condizione.setIDCONDIZIONE(idCondizione.getStringValue());
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati delle condizioni dell'aggiudicazione", "CondizioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setCondizioni: fine metodo");
		}
	}

	/**
	 * Lista dei requisiti di partecipazione.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setRequisiti(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setRequisiti: inizio metodo");
		}

		String selectW9REQU = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9REQU");

		try {
			List<?> datiW9REQU = this.sqlManager.getListVector(selectW9REQU, new Object[] { codgara, codlott, codgara, codlott });

			if (datiW9REQU != null && datiW9REQU.size() > 0) {
				ListIterator<?> iterator = datiW9REQU.listIterator();
				boolean isEmpty = true;
				int i = 0;
				while (iterator.hasNext()) {
					List<?> riga = (List<?>) iterator.next();

					RequisitoType requisito = RequisitoType.Factory.newInstance();

					if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null) {
						requisito.setIDCATEGORIA(SqlManager.getValueFromVectorParam(riga, 0).getStringValue());
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1))) {
						requisito.setCLASSEIMPORTO(SqlManager.getValueFromVectorParam(riga, 1).getStringValue());
						isEmpty = false;
					}

					JdbcParametro flagSN = SqlManager.getValueFromVectorParam(riga, 2);
					if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
						if ("1".equals(flagSN.getStringValue())) {
							requisito.setPREVALENTE(FlagSNType.S);
						} else {
							requisito.setPREVALENTE(FlagSNType.N);
						}
						isEmpty = false;
					}
					flagSN = SqlManager.getValueFromVectorParam(riga, 3);
					if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
						if ("1".equals(flagSN.getStringValue())) {
							requisito.setSCORPORABILE(FlagSNType.S);
						} else {
							requisito.setSCORPORABILE(FlagSNType.N);
						}
						isEmpty = false;
					}
					flagSN = SqlManager.getValueFromVectorParam(riga, 4);
					if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
						if ("1".equals(flagSN.getStringValue())) {
							requisito.setSUBAPPALTABILE(FlagSNType.S);
						} else {
							requisito.setSUBAPPALTABILE(FlagSNType.N);
						}
						isEmpty = false;
					}
					if (!isEmpty) {
						aggiudicazione.addNewRequisiti();
						aggiudicazione.setRequisitiArray(i, requisito);
						i++;
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati dei requisiti di partecipazione", "RequisitoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setRequisiti: fine metodo");
		}
	}

	/**
	 * Lista dei finanziamenti.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setFinanziamenti(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setFinanziamenti: inizio metodo");
		}
		String selectW9FINA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9FINA");

		try {
			List<?> datiW9FINA = this.sqlManager.getListVector(selectW9FINA, new Object[] { codgara, codlott, numappa });

			if (datiW9FINA != null && datiW9FINA.size() > 0) {
				ListIterator<?> iterator = datiW9FINA.listIterator();
				boolean isEmpty = true;
				int i = 0;
				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					FinanziamentoType finanziamento = FinanziamentoType.Factory.newInstance();

					if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null) {
						finanziamento.setIDFINANZIAMENTO(SqlManager.getValueFromVectorParam(riga, 0).getStringValue());
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
						finanziamento.setIMPORTOFINANZIAMENTO(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 1).doubleValue())));
						isEmpty = false;
					}
					if (!isEmpty) {
						aggiudicazione.addNewFinanziamenti();
						aggiudicazione.setFinanziamentiArray(i, finanziamento);
						i++;
					}

				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati dei finanziamenti", "FinanziamentoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setFinanziamenti: fine metodo");
		}
	}

	/**
	 * Lista degli aggiudicatari/affidatari. La lista riporta solo indicazioni
	 * generali sugli aggiudicatari. Il dettaglio, per ogni aggiudicatario, e'
	 * contenuto nell'archivio dei soggetti aggiudicatari
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setAggiudicatari(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAggiudicatari: inizio metodo");
		}
		String selectW9AGGI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9AGGI");
		boolean isMultiaggiudicatario = false;
		try {
			//verifico se sono in una situazione con più aggiudicatari
			String selectNumRaggW9AGGI = "select distinct id_gruppo,id_tipoagg ";
			String selectFromWhere = "from W9AGGI, W9FASI " + 
			"where W9AGGI.CODGARA = ? AND " + 
	        "    W9AGGI.CODLOTT = ? AND " +
	        "    W9AGGI.NUM_APPA = ? AND " + 
			"	W9AGGI.CODGARA = W9FASI.CODGARA AND " + 
			"	W9AGGI.CODLOTT = W9FASI.CODLOTT AND " + 
			"	(W9FASI.FASE_ESECUZIONE = 1 OR W9FASI.FASE_ESECUZIONE = 12 OR W9FASI.FASE_ESECUZIONE = 987) AND " + 
			"	W9FASI.NUM_APPA = W9AGGI.NUM_APPA";
			List<?> numeriRaggruppamentiW9AGGI = this.sqlManager.getListVector(selectNumRaggW9AGGI + selectFromWhere, new Object[] { codgara, codlott, numappa });
			if (numeriRaggruppamentiW9AGGI.size()>1) {
				isMultiaggiudicatario = true;
			} else {
				//verifico se ho più imprese singole o GEIE
				String queryImpreseSingole = "select count(*) " + selectFromWhere + " AND id_tipoagg in (3,4)";
				Long numeroSingole = (Long)this.sqlManager.getObject(queryImpreseSingole, new Object[] { codgara, codlott, numappa });
				if (numeroSingole > 1) {
					isMultiaggiudicatario = true;
				}
			}
			
			
			List<?> datiW9AGGI = this.sqlManager.getListVector(selectW9AGGI, new Object[] { codgara, codlott, numappa });

			if (datiW9AGGI != null && datiW9AGGI.size() > 0) {
				ListIterator<?> iterator = datiW9AGGI.listIterator();
				boolean isEmpty = true;

				int i = 0;
				while (iterator.hasNext()) {
					List<?> riga = (List<?>) iterator.next();
					SoggAggiudicatarioType soggAggiudicatari = SoggAggiudicatarioType.Factory.newInstance();

					if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null) {
						soggAggiudicatari.setIDTIPOAGG(SqlManager.getValueFromVectorParam(riga, 0).getStringValue());
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
						soggAggiudicatari.setRUOLO(SqlManager.getValueFromVectorParam(riga, 1).getStringValue());
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
						soggAggiudicatari.setFLAGAVVALIMENTO(SqlManager.getValueFromVectorParam(riga, 2).getStringValue());
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
						soggAggiudicatari.setCODICEFISCALEAGGIUDICATARIO(SqlManager.getValueFromVectorParam(riga, 3).getStringValue());
						isEmpty = false;
					} else {
						if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
							soggAggiudicatari.setCODICEFISCALEAGGIUDICATARIO(SqlManager.getValueFromVectorParam(riga, 5).getStringValue());
							isEmpty = false;
						}
					}

					if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
						soggAggiudicatari.setCFAUSILIARIA(SqlManager.getValueFromVectorParam(riga, 4).getStringValue());
						isEmpty = false;
					} else {
						if (SqlManager.getValueFromVectorParam(riga, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
							soggAggiudicatari.setCFAUSILIARIA(SqlManager.getValueFromVectorParam(riga, 6).getStringValue());
							isEmpty = false;
						}
					}
					//id_gruppo
					if (SqlManager.getValueFromVectorParam(riga, 8).getValue() != null) {
						soggAggiudicatari.setIDGRUPPO(SqlManager.getValueFromVectorParam(riga, 8).longValue().intValue());
						isEmpty = false;
					}
					//se multiaggiudicatario e l'impresa non è una mandante invio anche l'importo di aggiudicazione
					if (isMultiaggiudicatario && (soggAggiudicatari.getRUOLO() == null || soggAggiudicatari.getRUOLO().equals("1"))) {
						//importo aggiudicazione
						if (SqlManager.getValueFromVectorParam(riga, 9).getValue() != null) {
							soggAggiudicatari.setIMPORTOAGGIUDICAZIONE(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 9).doubleValue()));
							isEmpty = false;
						}
						//Ribasso di aggiudicazione
						if (SqlManager.getValueFromVectorParam(riga, 10).getValue() != null) {
							soggAggiudicatari.setPERCRIBASSOAGG(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 10).doubleValue()));
							isEmpty = false;
						}
						//Offerta in aumento
						if (SqlManager.getValueFromVectorParam(riga, 11).getValue() != null) {
							soggAggiudicatari.setPERCOFFAUMENTO(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 11).doubleValue()));
							isEmpty = false;
						}
					}
					
					
					// Gestione della nazione
					if (SqlManager.getValueFromVectorParam(riga, 7) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 7).getStringValue())) {

						// Se lo stato e' 1 - ITALIA
						if ("1".equals(SqlManager.getValueFromVectorParam(riga, 7).getStringValue())) {
							soggAggiudicatari.setCODICESTATO("");
						} else {
							// Ricavo la corrispondenza con il tabellato W3z12
							String codiceNazione = SqlManager.getValueFromVectorParam(riga, 7).getStringValue();
							String statoEstero = this.getStatoEstero(codiceNazione);
							soggAggiudicatari.setCODICESTATO(statoEstero);
						}

						isEmpty = false;
					} else {
						soggAggiudicatari.setCODICESTATO("");
					}

					if (!isEmpty) {
						aggiudicazione.addNewAggiudicatari();
						aggiudicazione.setAggiudicatariArray(i, soggAggiudicatari);
						i++;
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati degli aggiudicatari", "SoggAggiudicatarioType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAggiudicatari: fine metodo");
		}
	}

	/**
	 * Lista delle ditte ausiliarie (in caso di avvalimento).
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param numappa
	 *            progressivo appalto
	 * @throws GestoreException
	 *             GestoeException
	 * @throws IOException
	 *             IOException
	 */
	private void setDitteAusiliarie(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDitteAusiliarie: inizio metodo");
		}
		String selectW9AGGI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9AGGI_AUSILIARIA");

		try {
			List<?> datiW9AGGI = this.sqlManager.getListVector(selectW9AGGI, new Object[] { codgara, codlott, numappa });

			if (datiW9AGGI != null && datiW9AGGI.size() > 0) {
				ListIterator<?> iterator = datiW9AGGI.listIterator();
				boolean isEmpty = true;

				int i = 0;
				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					DittaAusiliariaType dittaAusiliaria = DittaAusiliariaType.Factory.newInstance();

					if (SqlManager.getValueFromVectorParam(riga, 0) != null && !"0".equals(SqlManager.getValueFromVectorParam(riga, 0).getStringValue())) {
						dittaAusiliaria.setFLAGAVVALIMENTO(SqlManager.getValueFromVectorParam(riga, 0).getStringValue());

						if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
							dittaAusiliaria.setCODICEFISCALEAGGIUDICATARIO(SqlManager.getValueFromVectorParam(riga, 1).getStringValue());
							isEmpty = false;
						} else {
							if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
								dittaAusiliaria.setCODICEFISCALEAGGIUDICATARIO(SqlManager.getValueFromVectorParam(riga, 2).getStringValue());
								isEmpty = false;
							}
						}

						if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
							// Se lo stato e' 1 - ITALIA
							if ("1".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
								dittaAusiliaria.setCODICESTATOAGGIUDICATARIO("");
							} else {
								// Ricavo la corrispondenza con il tabellato
								// W3z12
								String codiceNazione = SqlManager.getValueFromVectorParam(riga, 3).getStringValue();
								String statoEstero = this.getStatoEstero(codiceNazione);
								dittaAusiliaria.setCODICESTATOAGGIUDICATARIO(statoEstero);
							}
							isEmpty = false;
						} else {
							dittaAusiliaria.setCODICESTATOAGGIUDICATARIO("");
						}

						if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
							dittaAusiliaria.setCODICEFISCALEAUSILIARIA(SqlManager.getValueFromVectorParam(riga, 4).getStringValue());
							isEmpty = false;
						} else {
							if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
								dittaAusiliaria.setCODICEFISCALEAUSILIARIA(SqlManager.getValueFromVectorParam(riga, 5).getStringValue());
								isEmpty = false;
							}
						}

						if (SqlManager.getValueFromVectorParam(riga, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
							// Se lo stato e' 1 - ITALIA
							if ("1".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
								dittaAusiliaria.setCODICESTATOAUSILIARIA("");
							} else {
								// Ricavo la corrispondenza con il tabellato
								// W3z12
								String codiceNazione = SqlManager.getValueFromVectorParam(riga, 6).getStringValue();
								String statoEstero = getStatoEstero(codiceNazione);
								dittaAusiliaria.setCODICESTATOAUSILIARIA(statoEstero);
							}
							isEmpty = false;
						} else {
							dittaAusiliaria.setCODICESTATOAUSILIARIA("");
						}

						if (!isEmpty) {
							aggiudicazione.addNewDitteAusiliarie();
							aggiudicazione.setDitteAusiliarieArray(i, dittaAusiliaria);
							i++;
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati delle ditte ausiliarie", "SoggAggiudicatarioType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDitteAusiliarie: fine metodo");
		}
	}

	/**
	 * Elenco codici CUP e flag conferma esplicita
	 * 
	 * @param aggiudicazione
	 * @param codgara
	 * @param codlott
	 * @throws GestoreException
	 * @throws IOException
	 */
	private void setCupLotto(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott, String nomeSchedaCupLotto) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setCupLotto: inizio metodo");
		}

		String selectW9LOTT_CUP = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9LOTT_CUP");

		try {
			List<?> datiW9CUPLotto = this.sqlManager.getListVector(selectW9LOTT_CUP, new Object[] { codgara, codlott });

			if (datiW9CUPLotto != null && datiW9CUPLotto.size() > 0) {

				List<?> riga = (List<?>) datiW9CUPLotto.get(0);

				JdbcParametro tempParam = SqlManager.getValueFromVectorParam(riga, 0);
				if (tempParam.getValue() != null && tempParam.getValue().toString() != "") {

					CUPLOTTOType cupLotto = aggiudicazione.addNewCUPLOTTO();
					cupLotto.setCIG(SqlManager.getValueFromVectorParam(riga, 1).getStringValue());

					DatiCUPType datiCup = cupLotto.addNewCODICICUP();
					datiCup.setCUP(tempParam.toString());
					datiCup.setOKUTENTE(FlagSNType.S);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati del CUP della " + nomeSchedaCupLotto, "CupLottoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setCupLotto: fine metodo");
		}
	}

	/**
	 * Utilizzato per ricavare l'associazione tra i codice nazione presente
	 * nell'archivio delle imprese (IMPR) e la codifica prevista da SIMOG.
	 * 
	 * @param codiceNazione
	 *            codice della nazione
	 * @return Ritorna lo stato estero a partire dal codice dello stato stesso
	 * @throws GestoreException
	 *             GestoreException
	 */
	private String getStatoEstero(final String codiceNazione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("getStatoEstero: inizio metodo");
		}

		String statoEstero = "";
		try {
			statoEstero = (String) sqlManager.getObject("select tab2tip from tab2 where tab2d1 = ? and tab2cod = 'W3z12'", new Object[] { codiceNazione });
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura della nazione dell'impresa", "getStatoEstero", e);
		}
		if (statoEstero == null) {
			statoEstero = "";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getStatoEstero: fine metodo");
		}

		return statoEstero;
	}

	/**
	 * Gestione dei dati degli incaricati.
	 * 
	 * @param aggiudicazione
	 *            AggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            progressivo 
	 * @param querySezione
	 *            Nome della query:
	 *            <ul>
	 *            <li>W9INCA_AGGIUDICAZIONE per le sezioni PA ed RA
	 *            <li>W9INCA_SOTTOSOGLIA per la sezione RS
	 *            <li>W9INCA_ESCLUSO per la sezione RE
	 *            <li>W9INCA_ADESIONE per la sezione RQ
	 *            <li>W9INCA_INIZIO per la sezione IN
	 *            <li>W9INCA_COLLAUDO per la sezione CO
	 *            </ul>
	 * 
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setIncaricati(final AggiudicazioneType aggiudicazione, final Long codgara, final Long codlott, final Long num, final String querySezione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricati: inizio metodo");
		}

		String selectW9INCA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", querySezione);

		try {
			List<?> datiW9INCA = this.sqlManager.getListVector(selectW9INCA, new Object[] { codgara, codlott, num });

			if (datiW9INCA != null && datiW9INCA.size() > 0) {
				ListIterator<?> iterator = datiW9INCA.listIterator();

				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					IncaricatoType incaricato = aggiudicazione.addNewIncaricati();

					if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null) {
						incaricato.setSEZIONE(SezioneType.Enum.forString(SqlManager.getValueFromVectorParam(riga, 0).getStringValue()));
					}
					if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
						incaricato.setIDRUOLO(SqlManager.getValueFromVectorParam(riga, 1).getStringValue());
					}
					if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
						incaricato.setCIGPROGESTERNA(SqlManager.getValueFromVectorParam(riga, 2).getStringValue());
					}
					if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
						incaricato.setDATAAFFPROGESTERNA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 3)));
					}
					if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
						incaricato.setDATACONSPROGESTERNA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 4)));
					}
					if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
						incaricato.setCODICEFISCALERESPONSABILE(SqlManager.getValueFromVectorParam(riga, 5).getStringValue());
					} else {
						if (SqlManager.getValueFromVectorParam(riga, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
							incaricato.setCODICEFISCALERESPONSABILE(SqlManager.getValueFromVectorParam(riga, 6).getStringValue());
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati degli incaricati", "IncaricatoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setIncaricati: fine metodo");
		}
	}

	/**
	 * Gestione dell'elemento SchedaCompleta.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param fase_esecuzione
	 *            (opzionale) fase esecuzione
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setSchedaCompleta(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long fase_esecuzione, Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSchedaCompleta: inizio metodo");
		}
		// CUI - Codice di aggiudicazione
		this.setCUI(schedaCompleta, codgara, codlott, num, fase_esecuzione);
		Long ultimaAggiudicazione = null;
		boolean proseguiNonE1 = true;
		boolean proseguiNonS2 = true;
		try {
			// contratti esclusi (inviano a simog solo la scheda adesione, altrimenti l'agg. semplificata)
			if (UtilitySITAT.isE1(codgara, codlott, this.sqlManager)) {
				proseguiNonE1 = false;
			}
			// sottosoglia
			if (!UtilitySITAT.isS2(codgara, codlott, this.sqlManager)) {
				proseguiNonS2 = false;
			}
			
			ultimaAggiudicazione = (Long) sqlManager.getObject(
					"select MAX(NUM_APPA) from W9APPA where CODGARA=? and CODLOTT=?",
							new Object[]{ codgara, codlott});

		} catch (SQLException e) {
			throw new GestoreException("Errore nel controllo appartenenza di un lotto ad accordo quadro escluso", "", e);
		}
		
		if (fase_esecuzione != null) {
			switch (fase_esecuzione.intValue()) {
			case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
				// Dati della fase di aggiudicazione per contratti sopra soglia
				if (proseguiNonE1) {
					this.setAggiudicazione(schedaCompleta, codgara, codlott, num);
				}
				break;
			case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
				// Dati della fase di aggiudicazione semplificata per contratti sotto soglia
				this.setSottosoglia(schedaCompleta, codgara, codlott, num);
				// Dati della fase di aggiudicazione semplificata per contratti esclusi
				this.setEscluso(schedaCompleta, codgara, codlott, num);
				break;
			case CostantiW9.ADESIONE_ACCORDO_QUADRO:
				// Adesione ad accordo quadro
				this.setAdesione(schedaCompleta, codgara, codlott, num);
				break;
			case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
				// Dati di inizio per contratti sopra soglia
				if (proseguiNonE1) {
					this.setDatiInizio(schedaCompleta, codgara, codlott, num);
				}
				break;
			case CostantiW9.STIPULA_ACCORDO_QUADRO:
				// Dati di stipula per l'accordo quadro
				if (proseguiNonE1 && proseguiNonS2) {
					this.setDatiStipula(schedaCompleta, codgara, codlott, num);
				}
				break;
			case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
				// Dati relativi agli stati di avanzamento (SAL) per contratti sopra soglia
				if (proseguiNonE1) {
					this.setDatiAvanzamenti(schedaCompleta, codgara, codlott, num, ultimaAggiudicazione);
				}
				break;
			case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
				// Dati di conclusione per contratti sopra soglia
				if (proseguiNonE1) {
					this.setDatiConclusione(schedaCompleta, codgara, codlott, num);
				}
				break;
			case CostantiW9.COLLAUDO_CONTRATTO:
				// Dati di collaudo per contratti sopra soglia
				if (proseguiNonE1) {
					this.setDatiCollaudo(schedaCompleta, codgara, codlott, num);
				}
				break;
			case CostantiW9.ISTANZA_RECESSO:
				// Dati di recesso per contratti sopra soglia
				if (proseguiNonE1) {
					this.setDatiRitardo(schedaCompleta, codgara, codlott, num);
				}
				break;
			case CostantiW9.ACCORDO_BONARIO:
				// Dati relativi agli accordi bonaria per contratti sopra soglia
				if (proseguiNonE1) {
					this.setDatiAccordi(schedaCompleta, codgara, codlott, num, ultimaAggiudicazione);
				}
				break;
			case CostantiW9.SOSPENSIONE_CONTRATTO:
				// Dati relativi alle sospensioni per contratti sopra soglia
				if (proseguiNonE1) {
					this.setDatiSospensioni(schedaCompleta, codgara, codlott, num, ultimaAggiudicazione);
				}
				break;
			case CostantiW9.VARIANTE_CONTRATTO:
				// Dati relativi alle varianti per contratti sopra soglia
				if (proseguiNonE1) {
					this.setDatiVarianti(schedaCompleta, codgara, codlott, num);
				}
				break;
			case CostantiW9.SUBAPPALTO:
				// Dati relativi ai subappalti per contratti sopra soglia
				if (proseguiNonE1) {
					this.setDatiSubappalti(schedaCompleta, codgara, codlott, num, ultimaAggiudicazione);
				}
				break;
			}

		} else {
			// Dati della fase di aggiudicazione per contratti sopra soglia
			if (proseguiNonE1) {
				this.setAggiudicazione(schedaCompleta, codgara, codlott, num);
			}
			// Dati della fase di aggiudicazione semplificata per contratti sotto soglia
			this.setSottosoglia(schedaCompleta, codgara, codlott, num);

			// Dati della fase di aggiudicazione semplificata per contratti esclusi
			if (proseguiNonE1) {
				this.setEscluso(schedaCompleta, codgara, codlott, num);
			}

			// Adesione ad accordo quadro
			this.setAdesione(schedaCompleta, codgara, codlott, num);

			// Dati di inizio per contratti sopra soglia
			if (proseguiNonE1) {
				this.setDatiInizio(schedaCompleta, codgara, codlott, num);
			}

			// Dati di stipula per l'accordo quadro
			if (proseguiNonE1 && proseguiNonS2) {
				this.setDatiStipula(schedaCompleta, codgara, codlott, num);
			}

			// Dati relativi agli stati di avanzamento (SAL) per contratti sopra soglia
			if (proseguiNonE1) {
				this.setDatiAvanzamenti(schedaCompleta, codgara, codlott, num, ultimaAggiudicazione);
			}

			// Dati di conclusione per contratti sopra soglia
			if (proseguiNonE1) {
				this.setDatiConclusione(schedaCompleta, codgara, codlott, num);
			}

			// Dati di collaudo per contratti sopra soglia
			if (proseguiNonE1) {
				this.setDatiCollaudo(schedaCompleta, codgara, codlott, num);
			}

			// Dati di recesso per contratti sopra soglia
			if (proseguiNonE1) {
				this.setDatiRitardo(schedaCompleta, codgara, codlott, num);
			}

			// Dati relativi agli accordi bonaria per contratti sopra soglia
			if (proseguiNonE1) {
				this.setDatiAccordi(schedaCompleta, codgara, codlott, num, ultimaAggiudicazione);
			}

			// Dati relativi alle sospensioni per contratti sopra soglia
			if (proseguiNonE1) {
				this.setDatiSospensioni(schedaCompleta, codgara, codlott, num, ultimaAggiudicazione);
			}

			// Dati relativi alle varianti per contratti sopra soglia
			if (proseguiNonE1) {
				this.setDatiVarianti(schedaCompleta, codgara, codlott, num);
			}

			// Dati relativi ai subappalti per contratti sopra soglia
			if (proseguiNonE1) {
				this.setDatiSubappalti(schedaCompleta, codgara, codlott, num, ultimaAggiudicazione);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setSchedaCompleta: fine metodo");
		}
	}

	/**
	 * Fase di adesione ad accordo quadro.
	 * 
	 * @param schedaCompleta
	 *            ScheCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAdesione(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAdesione: inizio metodo");
		}

		try {
			String selectW9APPAADESIONE = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPA_ADESIONE");
			List<?> datiW9APPAADESIONE = this.sqlManager.getVector(selectW9APPAADESIONE, new Object[] { codgara, codlott, numappa });

			if (datiW9APPAADESIONE != null && datiW9APPAADESIONE.size() > 0) {

				AdesioneType adesione = schedaCompleta.addNewAdesione();

				// Dati dell'aggiudicazione
				this.setAppaltoAdesione(adesione, codgara, codlott, numappa);

				// Elemento di "appoggio" per la composizione dei sottoelementi
				// successivi.
				// I sottoelementi Finanziamenti, Aggiudicatari, Incaricati e
				// Ditte ausiliarie vengono composti collegandoli all'elemento
				// "aggiudicazioneType" come se fossero relativi alla
				// Fase di Aggiudicazione per contratti sopra soglia.
				// Successivamente il contenuto viene copiato nell'elemento
				// "appaltoAdesioneType"
				AggiudicazioneType aggiudicazione = AggiudicazioneType.Factory.newInstance();

				// Lista dei finanziamenti
				this.setFinanziamenti(aggiudicazione, codgara, codlott, numappa);
				if (aggiudicazione.sizeOfFinanziamentiArray() > 0) {
					adesione.setFinanziamentiArray(aggiudicazione.getFinanziamentiArray());
				}
				// Lista delle ditta aggiudicatarie/affidatarie
				this.setAggiudicatari(aggiudicazione, codgara, codlott, numappa);
				if (aggiudicazione.sizeOfAggiudicatariArray() > 0) {
					adesione.setAggiudicatariArray(aggiudicazione.getAggiudicatariArray());
				}
				// Lista degli incaricati (sezione RQ)
				this.setIncaricati(aggiudicazione, codgara, codlott, numappa, "W9INCA_ADESIONE");
				if (aggiudicazione.sizeOfIncaricatiArray() > 0) {
					adesione.setIncaricatiArray(aggiudicazione.getIncaricatiArray());
				}
				// Lista delle ditte ausiliarie (in caso di avvalimento)
				this.setDitteAusiliarie(aggiudicazione, codgara, codlott, numappa);
				if (aggiudicazione.sizeOfDitteAusiliarieArray() > 0) {
					adesione.setDitteAusiliarieArray(aggiudicazione.getDitteAusiliarieArray());
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di adesione all'accordo quadro", "AppaltoAdesioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAdesione: fine metodo");
		}

	}

	/**
	 * Dati della stipula dell'accordo quadro.
	 * 
	 * @param schedaCompleta
	 *            ScheCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num_iniz
	 *            progressivo
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setDatiStipula(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long num_iniz) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiStipula: inizio metodo");
		}

		try {
			String selectW9INIZSTIPULA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9INIZ_STIPULA");
			List<?> datiW9INIZSTIPULA = this.sqlManager.getVector(selectW9INIZSTIPULA, new Object[] { codgara, codlott, num_iniz });

			if (datiW9INIZSTIPULA != null && datiW9INIZSTIPULA.size() > 0) {

				DatiStipulaType datiStipula = schedaCompleta.addNewDatiStipula();

				// Dati dell'accordo quadro e dei termini di esecuzione
				this.setStipula(datiStipula, codgara, codlott, num_iniz);

				// Dati di pubblicazione dell'esito procedura di selezione
				this.setPubblicazioneStipula(datiStipula, codgara, codlott, num_iniz);

			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di adesione all'accordo quadro", "AppaltoAdesioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiStipula: fine metodo");
		}

	}

	/**
	 * Fase di aggiudicazione ridotta per contratti esclusi.
	 * 
	 * @param schedaCompleta
	 *            ScheCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setEscluso(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setEscluso: inizio metodo");
		}

		try {
			String selectW9APPAESCLUSI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPA_ESCLUSO");

			List<?> datiW9APPAESCLUSI = this.sqlManager.getVector(selectW9APPAESCLUSI, new Object[] { codgara, codlott, numappa });

			if (datiW9APPAESCLUSI != null && datiW9APPAESCLUSI.size() > 0) {

				SchedaEsclusoType schedaEscluso = schedaCompleta.addNewEscluso();

				// Dati dell'aggiudicazione
				this.setAppaltoEscluso(schedaEscluso, codgara, codlott, numappa);

				// Elemento di "appoggio" per la composizione dei sottoelementi
				// successivi.
				// I sottoelementi Aggiudicatari e Incaricati vengono composti
				// collegandoli all'elemento "aggiudicazioneType" come se
				// fossero relativi alla Fase di Aggiudicazione
				// per contratti sopra soglia.
				// Successivamente il contenuto viene copiato nell'elemento
				// "sottoEsclusoType"
				AggiudicazioneType aggiudicazione = AggiudicazioneType.Factory.newInstance();

				// Lista delle ditte aggiudicatarie/affidatarie
				this.setAggiudicatari(aggiudicazione, codgara, codlott, numappa);
				if (aggiudicazione.sizeOfAggiudicatariArray() > 0) {
					schedaEscluso.setAggiudicatariArray(aggiudicazione.getAggiudicatariArray());
				}
				// Lista degli incaricati (sezione RE)
				this.setIncaricati(aggiudicazione, codgara, codlott, numappa, "W9INCA_ESCLUSO");
				if (aggiudicazione.sizeOfIncaricatiArray() > 0) {
					schedaEscluso.setIncaricatiArray(aggiudicazione.getIncaricatiArray());
				}

				// Lista dei CUP
				this.setCupLotto(aggiudicazione, codgara, codlott, "aggiudicazione per contratti esclusi");
				if (aggiudicazione.isSetCUPLOTTO()) {
					schedaEscluso.setCUPLOTTO(aggiudicazione.getCUPLOTTO());
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione dei dati di aggiudicazione per contratti esclusi", "AppaltoEsclusoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setEscluso: fine metodo");
		}

	}

	/**
	 * Fase di aggiudicazione per contratti sotto soglia.
	 * 
	 * @param schedaCompleta
	 *            ScheCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setSottosoglia(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSottosoglia: inizio metodo");
		}

		try {
			String selectW9APPASOTTOSOGLIA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPA_SOTTOSOGLIA");

			List<?> datiW9APPASOTTOSOGLIA = this.sqlManager.getVector(selectW9APPASOTTOSOGLIA, new Object[] { codgara, codlott, numappa });

			if (datiW9APPASOTTOSOGLIA != null && datiW9APPASOTTOSOGLIA.size() > 0) {

				SchedaSottosogliaType schedaSottoSoglia = schedaCompleta.addNewSottosoglia();

				// Dati dell'appalto
				this.setAppaltoSottoSoglia(schedaSottoSoglia, codgara, codlott, numappa);

				// Elemento di "appoggio" per la composizione dei sottoelementi successivi.
				// I sottoelementi Condizioni, Aggiudicatari e Incaricati vengono
				// composti collegandoli all'elemento "aggiudicazioneType" come se fossero
				// relativi alla Fase di Aggiudicazione per contratti sopra soglia.
				// Successivamente il contenuto viene copiato nell'elemento
				// "schedaSottoSogliaType"
				AggiudicazioneType aggiudicazione = AggiudicazioneType.Factory.newInstance();

				// Lista delle condizioni negoziate
				// per aggiungerle all'invio su Simog controllo prima che il
				// ricorso alla procedura negoziata sia giustificato
				if (UtilitySITAT.getVersioneSimog(this.sqlManager, codgara) == 1) {
					if (UtilitySITAT.isNegoziabile(this.sqlManager, codgara, codlott)) {
						this.setCondizioni(aggiudicazione, codgara, codlott, numappa);
						if (aggiudicazione.sizeOfCondizioniArray() > 0) {
							schedaSottoSoglia.setCondizioniArray(aggiudicazione.getCondizioniArray());
						}
					}
				}
				// Lista degli aggiudicatari
				this.setAggiudicatari(aggiudicazione, codgara, codlott, numappa);
				if (aggiudicazione.sizeOfAggiudicatariArray() > 0) {
					schedaSottoSoglia.setAggiudicatariArray(aggiudicazione.getAggiudicatariArray());
				}
				// Lista degli incaricati (sezione RS)
				this.setIncaricati(aggiudicazione, codgara, codlott, numappa, "W9INCA_SOTTOSOGLIA");
				if (aggiudicazione.sizeOfIncaricatiArray() > 0) {
					schedaSottoSoglia.setIncaricatiArray(aggiudicazione.getIncaricatiArray());
				}
				// Lista dei CUP
				this.setCupLotto(aggiudicazione, codgara, codlott, "aggiudicazione per contratti sottosoglia");
				if (aggiudicazione.isSetCUPLOTTO()) {
					schedaSottoSoglia.setCUPLOTTO(aggiudicazione.getCUPLOTTO());
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione dei di aggiudicazione per contratti sottosoglia", "AppaltoSottoSogliaType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setSottosoglia: fine metodo");
		}
	}

	/**
	 * Dati della fase di aggiudicazione o definizione di procedura negoziata
	 * per contratti sopra soglia.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param numappa
	 *            codice dell'appalto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAggiudicazione(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long numappa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAggiudicazione: inizio metodo");
		}
		try {
			String selectW9APPA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9APPA");

			List<?> datiW9APPA = this.sqlManager.getVector(selectW9APPA, new Object[] { codgara, codlott, numappa});

			if (datiW9APPA != null && datiW9APPA.size() > 0) {

				AggiudicazioneType aggiudicazione = schedaCompleta.addNewAggiudicazione();

				// Dati dell'appalto
				this.setAppalto(aggiudicazione, codgara, codlott, numappa);

				// Lista delle tipologie lavoro
				this.setTipiAppaltoLav(aggiudicazione, codgara, codlott, numappa);

				// Lista delle tipologie di forniture
				this.setTipiAppaltoForn(aggiudicazione, codgara, codlott, numappa);

				// Lista delle condizioni negoziate
				// per aggiungerle all'invio su Simog controllo prima che il
				// ricorso alla procedura negoziata sia giustificato
				if (UtilitySITAT.getVersioneSimog(this.sqlManager, codgara) == 1) {
					if (UtilitySITAT.isNegoziabile(this.sqlManager, codgara, codlott)) {
						this.setCondizioni(aggiudicazione, codgara, codlott, numappa);
					}
				}
				// Lista dei requisiti di partecipazione
				this.setRequisiti(aggiudicazione, codgara, codlott);

				// Lista dei finanziamenti
				this.setFinanziamenti(aggiudicazione, codgara, codlott, numappa);

				// Lista delle ditte aggiudicatarie/affidatarie
				this.setAggiudicatari(aggiudicazione, codgara, codlott, numappa);

				// Lista degli incaricati (sezioni PA ed RA)
				this.setIncaricati(aggiudicazione, codgara, codlott, numappa, "W9INCA_AGGIUDICAZIONE");

				// Lista delle ditte ausiliarie (in caso di avvalimento)
				this.setDitteAusiliarie(aggiudicazione, codgara, codlott, numappa);

				// Elenco codici CUP e flag conferma esplicita
				this.setCupLotto(aggiudicazione, codgara, codlott, "aggiudicazione per contratti sopra soglia");

			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione della fase di aggiudicazione per contratti sopra soglia", "AggiudicazioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAggiudicazione: fine metodo");
		}
	}

	/**
	 * CUI - Codice identificativo dell'aggiudicazione.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num
	 *            progressivo fase
	 * @param fase_esecuzione
	 *            (opzionale) fase esecuzione
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setCUI(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long num, final Long fase_esecuzione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setCUI: inizio metodo");
		}

		//String selectW9CUI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9CUI");
		String selectW9CUI = "SELECT CODCUI FROM W9APPA WHERE CODGARA=? AND CODLOTT=? AND NUM_APPA=?";

		try {
			Long numappa = (Long)sqlManager.getObject("SELECT NUM_APPA FROM W9FASI WHERE CODGARA = ? AND CODLOTT = ? AND FASE_ESECUZIONE = ? AND NUM = ?", new Object[]{codgara, codlott, fase_esecuzione, num});
			String codiceCUI = (String) this.sqlManager.getObject(selectW9CUI, new Object[] { codgara, codlott, numappa });
			if (codiceCUI != null) {
				schedaCompleta.setCUI(codiceCUI);
				/*if (codiceCUI.indexOf("-") != - 1) {
					String aggiudicazioneCUI  = codiceCUI.substring(codiceCUI.indexOf("-") + 1);
					if (!aggiudicazioneCUI.equals(numappa.toString())) {
						//se sto mandando una nuova aggiudicazione sbianco il CUI
						schedaCompleta.setCUI("");
					}
				}*/
			} else {
				schedaCompleta.setCUI("");
			}

		} catch (Exception e) {
			throw new GestoreException("Errore nella lettura del codice identificativo dell'aggiudicazione (CUI)", "SchedaCompletaType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setCUI: fine metodo");
		}
	}

	/**
	 * Dati inizio per contratti sopra soglia.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num_iniz
	 *            progressivo
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setDatiInizio(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long num_iniz) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiInizio: inizio metodo");
		}

		try {
			String selectW9INIZ = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9INIZ");

			List<?> datiW9INIZ = this.sqlManager.getVector(selectW9INIZ, new Object[] { codgara, codlott, num_iniz });

			if (datiW9INIZ != null && datiW9INIZ.size() > 0) {
				DatiInizioType datiInizio = schedaCompleta.addNewDatiInizio();

				// Pubblicazione dell'esito
				this.setPubblicazioneEsito(datiInizio, codgara, codlott, num_iniz);

				// Inizio
				this.setInizio(datiInizio, codgara, codlott, num_iniz);
				
				Long numappa = (Long)sqlManager.getObject("SELECT NUM_APPA FROM W9INIZ WHERE CODGARA = ? AND CODLOTT = ? AND NUM_INIZ = ?", new Object[]{codgara, codlott, num_iniz});
				// Posizioni contributive/assicurative delle imprese aggiudicatrici
				this.setPosizioni(datiInizio, codgara, codlott, numappa);

				// Lista degli incaricati (sezione IN)
				AggiudicazioneType aggiudicazione = AggiudicazioneType.Factory.newInstance();
				this.setIncaricati(aggiudicazione, codgara, codlott, num_iniz, "W9INCA_INIZIO");
				if (aggiudicazione.sizeOfIncaricatiArray() > 0) {
					datiInizio.setIncaricatiArray(aggiudicazione.getIncaricatiArray());
				}
			}
		} catch (Exception e) {
			throw new GestoreException("Errore nella lettura dei dati della fase di inizio", "DatiInizioType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiInizio: fine metodo");
		}
	}

	/**
	 * Lista dei subappalti.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num
	 *            progressivo
	 * @param num_appa
	 *            progressivo appalto
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setDatiSubappalti(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long num, final Long num_appa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiSubappalti: inizio metodo");
		}
		String selectW9SUBA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9SUBA");

		try {
			List<?> datiW9SUBA = this.sqlManager.getListVector(selectW9SUBA, new Object[] { codgara, codlott, num_appa });

			if (datiW9SUBA != null && datiW9SUBA.size() > 0) {
				ListIterator<?> iterator = datiW9SUBA.listIterator();
				SubappaltiType subappalti = SubappaltiType.Factory.newInstance();

				boolean isEmpty = true;
				boolean subappaltoValorizzato = false;

				while (iterator.hasNext() && !subappaltoValorizzato) {
					List<?> riga = (List<?>) iterator.next();

					if (num != null && num.longValue() > 0) {
						Long numSubappalto = (Long) SqlManager.getValueFromVectorParam(riga, 12).getValue();
						if (num.equals(numSubappalto)) {
							SubappaltoType subappalto = subappalti.addNewSubappalto();
							isEmpty = this.valorizzaSubappalto(isEmpty, riga, subappalto);
							subappaltoValorizzato = true;
						}
					} else {
						SubappaltoType subappalto = subappalti.addNewSubappalto();
						isEmpty = this.valorizzaSubappalto(isEmpty, riga, subappalto);
					}
				}

				if (!isEmpty) {
					schedaCompleta.addNewDatiSubappalti();
					schedaCompleta.setDatiSubappalti(subappalti);
				}

			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati dei subappalti", "SubappaltiType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiSubappalti: fine metodo");
		}
	}

	/**
	 * @param isEmpty
	 * @param riga
	 * @param subappalto
	 * @return
	 * @throws GestoreException
	 */
	private boolean valorizzaSubappalto(boolean isEmpty, List<?> riga, SubappaltoType subappalto) throws GestoreException {

		if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 0).getStringValue())) {
			subappalto.setCFDITTA(SqlManager.getValueFromVectorParam(riga, 0).getStringValue());
			isEmpty = false;
		} else {
			// Se il codice fiscale non e' valorizzato si deve considerare la partita IVA
			if (SqlManager.getValueFromVectorParam(riga, 7).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 7).getStringValue())) {
				subappalto.setCFDITTA(SqlManager.getValueFromVectorParam(riga, 7).getStringValue());
				isEmpty = false;
			}
		}

		if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
			subappalto.setDATAAUTORIZZAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 1)));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
			subappalto.setOGGETTOSUBAPPALTO(SqlManager.getValueFromVectorParam(riga, 2).getStringValue());
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
			subappalto.setIMPORTOPRESUNTO(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 3).doubleValue())));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
			subappalto.setIMPORTOEFFETTIVO(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 4).doubleValue())));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
			subappalto.setIDCATEGORIA(SqlManager.getValueFromVectorParam(riga, 5).getStringValue());
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
			subappalto.setIDCPV(SqlManager.getValueFromVectorParam(riga, 6).getStringValue());
			isEmpty = false;
		}

		// Ditta aggiudicataria
		if (SqlManager.getValueFromVectorParam(riga, 8).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 8).getStringValue())) {
			subappalto.setCODICEFISCALEAGGIUDICATARIO(SqlManager.getValueFromVectorParam(riga, 8).getStringValue());
			isEmpty = false;
		} else {
			// Se il codice fiscale non e' valorizzato si deve considerare la partita IVA
			if (SqlManager.getValueFromVectorParam(riga, 9).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 9).getStringValue())) {
				subappalto.setCODICEFISCALEAGGIUDICATARIO(SqlManager.getValueFromVectorParam(riga, 9).getStringValue());
				isEmpty = false;
			}
		}
		if (SqlManager.getValueFromVectorParam(riga, 10).getValue() != null) {
			subappalto.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(riga, 10).toString());
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 11).getValue() != null) {
			subappalto.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(riga, 11).toString());
			isEmpty = false;
		}
		return isEmpty;
	}

	/**
	 * Lista delle varianti.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setDatiVarianti(SchedaCompletaType schedaCompleta, Long codgara, Long codlott, Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiVarianti: inizio metodo");
		}
		VariantiType datiVarianti = null;

		String selectW9VARI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9VARI");

		try {
			List<?> datiW9VARI = this.sqlManager.getListVector(selectW9VARI, new Object[] { codgara, codlott, num });

			if (datiW9VARI != null && datiW9VARI.size() > 0) {
				datiVarianti = schedaCompleta.addNewDatiVarianti();

				ListIterator<?> iteratorW9VARI = datiW9VARI.listIterator();

				while (iteratorW9VARI.hasNext()) {
					Long numP = null;

					List<?> rigaW9VARI = (List<?>) iteratorW9VARI.next();

					// Prende i parametri per la seconda selezione
					if (SqlManager.getValueFromVectorParam(rigaW9VARI, 10) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 10).getStringValue())) {
						numP = new Long(SqlManager.getValueFromVectorParam(rigaW9VARI, 10).getStringValue());
					}

					if (num != null && num.intValue() != 0) {
						Long numeroVariante = (Long) SqlManager.getValueFromVectorParam(rigaW9VARI, 10).getValue();

						if (num.equals(numeroVariante)) {
							numP = new Long(num);

							VarianteType variante = datiVarianti.addNewVariante();
							RecVarianteType recVariante = variante.addNewVariante();

							this.valorizzaVariante(rigaW9VARI, recVariante);
							// Gestione dei motivi
							this.valorizzaMotivazioniVariante(codgara, codlott, numP, variante);
						}
					} else {
						numP = (Long) SqlManager.getValueFromVectorParam(rigaW9VARI, 10).getValue();

						VarianteType variante = datiVarianti.addNewVariante();
						RecVarianteType recVariante = variante.addNewVariante();

						this.valorizzaVariante(rigaW9VARI, recVariante);
						// Gestione dei motivi
						this.valorizzaMotivazioniVariante(codgara, codlott, numP, variante);
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati delle varianti", "DatiVariantiType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiVarianti: fine metodo");
		}
	}

	/**
	 * @param codgara
	 * @param codlott
	 * @param selectW9MOTI
	 * @param numP
	 * @param variante
	 * @throws SQLException
	 */
	private void valorizzaMotivazioniVariante(Long codgara, Long codlott, Long numP, VarianteType variante) throws SQLException {

		String selectW9MOTI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9MOTI");

		List<?> datiW9MOTI = this.sqlManager.getListVector(selectW9MOTI, new Object[] { codgara, codlott, numP });

		if (datiW9MOTI != null && datiW9MOTI.size() > 0) {
			ListIterator<?> iteratorW9MOTI = datiW9MOTI.listIterator();

			while (iteratorW9MOTI.hasNext()) {
				List<?> rigaW9MOTI = (List<?>) iteratorW9MOTI.next();
				RecMotivoVarType recMotivo = variante.addNewMotivi();

				if (SqlManager.getValueFromVectorParam(rigaW9MOTI, 0).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9MOTI, 0).getStringValue())) {
					recMotivo.setIDMOTIVOVAR(SqlManager.getValueFromVectorParam(rigaW9MOTI, 0).getStringValue());
				}
			}
		}
	}

	/**
	 * @param rigaW9VARI
	 * @param recVariante
	 * @throws GestoreException
	 */
	private void valorizzaVariante(List<?> rigaW9VARI, RecVarianteType recVariante) throws GestoreException {

		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 0).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 0).getStringValue())) {
			recVariante.setDATAVERBAPPR(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(rigaW9VARI, 0)));
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 1).getStringValue())) {
			recVariante.setALTREMOTIVAZIONI(SqlManager.getValueFromVectorParam(rigaW9VARI, 1).getStringValue());
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 2).getStringValue())) {
			recVariante.setIMPRIDETLAVORI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(rigaW9VARI, 2).doubleValue())));
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 3).getStringValue())) {
			recVariante.setIMPRIDETSERVIZI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(rigaW9VARI, 3).doubleValue())));
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 4).getStringValue())) {
			recVariante.setIMPRIDETFORNIT(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(rigaW9VARI, 4).doubleValue())));
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 5).getStringValue())) {
			recVariante.setIMPSICUREZZA(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(rigaW9VARI, 5).doubleValue())));
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 6).getStringValue())) {
			recVariante.setIMPPROGETTAZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(rigaW9VARI, 6).doubleValue())).toString());
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 7) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 7).getStringValue())) {
			recVariante.setIMPDISPOSIZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(rigaW9VARI, 7).doubleValue())).toString());
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 8) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 8).getStringValue())) {
			recVariante.setDATAATTOAGGIUNTIVO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(rigaW9VARI, 8)));
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 9) != null && !"".equals(SqlManager.getValueFromVectorParam(rigaW9VARI, 9).getStringValue())) {
			recVariante.setNUMGIORNIPROROGA(Integer.parseInt(SqlManager.getValueFromVectorParam(rigaW9VARI, 9).getStringValue()));
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 11).getValue() != null) {
			recVariante.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(rigaW9VARI, 11).toString());
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 12).getValue() != null) {
			recVariante.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(rigaW9VARI, 12).toString());
		}
		if (SqlManager.getValueFromVectorParam(rigaW9VARI, 13).getValue() != null) {
		  recVariante.setULTERIORISOMME(new BigDecimal(this.convertiImporto(
			SqlManager.getValueFromVectorParam(rigaW9VARI, 13).doubleValue())));
    }

	}

	/**
	 * Lista delle sospensioni.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num
	 *            progressivo della fase
	 * @param num_appa
	 *            progressivo appalto
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setDatiSospensioni(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long num, final Long num_appa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiSospensioni: inizio metodo");
		}
		String selectW9SOSP = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9SOSP");

		try {
			List<?> datiW9SOSP = this.sqlManager.getListVector(selectW9SOSP, new Object[] { codgara, codlott, num_appa });

			if (datiW9SOSP != null && datiW9SOSP.size() > 0) {

				SospensioniType sospensioni = SospensioniType.Factory.newInstance();
				ListIterator<?> iterator = datiW9SOSP.listIterator();
				boolean isEmpty = true;

				boolean sospensioneValorizzata = false;

				while (iterator.hasNext() && !sospensioneValorizzata) {
					List<?> riga = (List<?>) iterator.next();

					if (num != null && num.longValue() > 0) {
						Long numSospensione = (Long) SqlManager.getValueFromVectorParam(riga, 8).getValue();
						if (num.equals(numSospensione)) {
							SospensioneType sospensione = sospensioni.addNewSospensione();
							isEmpty = this.valorizzaSospensione(isEmpty, riga, sospensione);
							sospensioneValorizzata = true;
						}
					} else {
						SospensioneType sospensione = sospensioni.addNewSospensione();
						isEmpty = this.valorizzaSospensione(isEmpty, riga, sospensione);
					}
				}
				if (!isEmpty) {
					schedaCompleta.addNewDatiSospensioni();
					schedaCompleta.setDatiSospensioni(sospensioni);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati delle sospensioni", "SospensioniType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiSospensioni: fine metodo");
		}
	}

	/**
	 * @param isEmpty
	 * @param riga
	 * @param sospensione
	 * @return
	 * @throws GestoreException
	 */
	private boolean valorizzaSospensione(boolean isEmpty, List<?> riga, SospensioneType sospensione) throws GestoreException {

		if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 0).getStringValue())) {
			sospensione.setDATAVERBSOSP(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 0)));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
			sospensione.setDATAVERBRIPR(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 1)));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
			sospensione.setIDMOTIVOSOSP(SqlManager.getValueFromVectorParam(riga, 2).getStringValue());
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
			if ("1".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
				sospensione.setFLAGSUPEROTEMPO(FlagSNType.S);
			} else {
				sospensione.setFLAGSUPEROTEMPO(FlagSNType.N);
			}
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
			if ("1".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
				sospensione.setFLAGRISERVE(FlagSNType.S);
			} else {
				sospensione.setFLAGRISERVE(FlagSNType.N);
			}
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
			if ("1".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
				sospensione.setFLAGVERBALE(FlagSNType.S);
			} else {
				sospensione.setFLAGVERBALE(FlagSNType.N);
			}
			isEmpty = false;
		}

		if (SqlManager.getValueFromVectorParam(riga, 6).getValue() != null) {
			sospensione.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(riga, 6).toString());
			isEmpty = false;
		}

		if (SqlManager.getValueFromVectorParam(riga, 7).getValue() != null) {
			sospensione.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(riga, 7).toString());
			isEmpty = false;
		}

		return isEmpty;
	}

	/**
	 * Lista degli accordi bonari.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num
	 *            progressivo della fase
	 * @param num_appa
	 *            progressivo appalto
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setDatiAccordi(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long num, final Long num_appa) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiAccordi: inizio metodo");
		}

		String selectW9ACCO = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9ACCO");

		try {
			List<?> datiW9ACCO = this.sqlManager.getListVector(selectW9ACCO, new Object[] { codgara, codlott, num_appa });

			if (datiW9ACCO != null && datiW9ACCO.size() > 0) {
				
				AccordiBonariType accordiBonari = AccordiBonariType.Factory.newInstance();
				ListIterator<?> iterator = datiW9ACCO.listIterator();
				boolean isEmpty = true;
				
				boolean accordoValorizzato = false;
				
				while (iterator.hasNext() && !accordoValorizzato) {
					List<?> riga = (List<?>) iterator.next();
					
					if (num != null && num.longValue() > 0) {
						Long numAccordo = (Long) SqlManager.getValueFromVectorParam(riga, 5).getValue();
						if (num.equals(numAccordo)) {
							AccordoBonarioType accordoBonario = accordiBonari.addNewAccordoBonario();
							isEmpty = this.valorizzaAccordo(isEmpty, riga, accordoBonario);
							accordoValorizzato = true;
						}
					} else {
						AccordoBonarioType accordoBonario = accordiBonari.addNewAccordoBonario();
						isEmpty = this.valorizzaAccordo(isEmpty, riga, accordoBonario);
					}
				}
				if (!isEmpty) {
					schedaCompleta.addNewDatiAccordi();
					schedaCompleta.setDatiAccordi(accordiBonari);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati dei dati degli accordi", "AccordiType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiAccordi: fine metodo");
		}
	}

	/**
	 * @param isEmpty
	 * @param riga
	 * @param accordoBonario
	 * @return
	 * @throws GestoreException
	 */
	private boolean valorizzaAccordo(boolean isEmpty, List<?> riga, AccordoBonarioType accordoBonario) throws GestoreException {

		if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 0).getStringValue())) {
			accordoBonario.setDATAACCORDO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 0)));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
			accordoBonario.setONERIDERIVANTI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 1).doubleValue())));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
			accordoBonario.setNUMRISERVE(Integer.parseInt(SqlManager.getValueFromVectorParam(riga, 2).getStringValue()));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 3).getValue() != null) {
			accordoBonario.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(riga, 3).toString());
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 4).getValue() != null) {
			accordoBonario.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(riga, 4).toString());
			isEmpty = false;
		}

		return isEmpty;
	}
	
	/**
	 * Lista delle istanze di recesso.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num
	 *            progressivo fase
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setDatiRitardo(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiRitardo: inizio metodo");
		}

		String selectW9RITA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9RITA");

		try {
			List<?> datiW9RITA = this.sqlManager.getListVector(selectW9RITA, new Object[] { codgara, codlott, num });

			if (datiW9RITA != null && datiW9RITA.size() > 0) {
				boolean isEmpty = true;
				RitardiType ritardi = RitardiType.Factory.newInstance();
				ListIterator<?> iterator = datiW9RITA.listIterator();

				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					RitardoType ritardo = ritardi.addNewRitardo();

					if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null) {
						ritardo.setDATATERMINE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 0)).toString());
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
						ritardo.setDATACONSEGNA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 1)));
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
						ritardo.setTIPOCOMUN(FlagTCType.Enum.forString(SqlManager.getValueFromVectorParam(riga, 2).getStringValue()));
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
						ritardo.setDURATASOSP(Integer.parseInt(SqlManager.getValueFromVectorParam(riga, 3).getStringValue()));
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
						ritardo.setMOTIVOSOSP(SqlManager.getValueFromVectorParam(riga, 4).getStringValue());
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
						ritardo.setDATAISTRECESSO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 5)));
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
						if ("1".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
							ritardo.setFLAGACCOLTA(FlagSNType.S);
						} else {
							ritardo.setFLAGACCOLTA(FlagSNType.N);
						}
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 7) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 7).getStringValue())) {
						if ("1".equals(SqlManager.getValueFromVectorParam(riga, 7).getStringValue())) {
							ritardo.setFLAGTARDIVA(FlagSNType.S);
						} else {
							ritardo.setFLAGTARDIVA(FlagSNType.N);
						}
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 8) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 8).getStringValue())) {
						if ("1".equals(SqlManager.getValueFromVectorParam(riga, 8).getStringValue())) {
							ritardo.setFLAGRIPRESA(FlagSNType.S);
						} else {
							ritardo.setFLAGRIPRESA(FlagSNType.N);
						}
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 9) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 9).getStringValue())) {
						if ("1".equals(SqlManager.getValueFromVectorParam(riga, 9).getStringValue())) {
							ritardo.setFLAGRISERVA(FlagSNType.S.toString());
						} else {
							ritardo.setFLAGRISERVA(FlagSNType.N.toString());
						}
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 10) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 10).getStringValue())) {
						ritardo.setIMPORTOSPESE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 10).doubleValue())));
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 11) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 11).getStringValue())) {
						ritardo.setIMPORTOONERI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 11).doubleValue())));
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 12).getValue() != null) {
						ritardo.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(riga, 12).toString());
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 13).getValue() != null) {
						ritardo.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(riga, 13).toString());
						isEmpty = false;
					}
				}
				if (!isEmpty) {
					schedaCompleta.addNewDatiRitardi();
					schedaCompleta.setDatiRitardi(ritardi);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati relativi all'istanza di recesso", "DatiRitardiType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiRitardo: fine metodo");
		}
	}

	/**
	 * Dati della fase di collaudo per contratti sopra soglia.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num
	 *            progressivo
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setDatiCollaudo(final SchedaCompletaType schedaCompleta, final Long codgara, final Long codlott, final Long num) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiCollaudo: inizio metodo");
		}
		String selectW9COLL = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9COLL");

		try {
			List<?> datiW9COLL = this.sqlManager.getVector(selectW9COLL, new Object[] { codgara, codlott, num });

			if (datiW9COLL != null && datiW9COLL.size() > 0) {

				DatiCollaudoType datiCollaudo = schedaCompleta.addNewDatiCollaudo();
				CollaudoType collaudo = datiCollaudo.addNewCollaudo();

				Long tipoCollaudo = null;
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 31).getValue() != null) {
					tipoCollaudo = SqlManager.getValueFromVectorParam(datiW9COLL, 31).longValue();
				}
				
				if (new Long(2).equals(tipoCollaudo)) {
					if (SqlManager.getValueFromVectorParam(datiW9COLL, 0).getValue() != null) {
						collaudo.setDATAREGOLAREESEC(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9COLL, 0)));
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 1).getStringValue())) {
					collaudo.setDATACOLLAUDOSTAT(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9COLL, 1)));
				}
				if (new Long(1).equals(tipoCollaudo)) {
					if (SqlManager.getValueFromVectorParam(datiW9COLL, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 2).getStringValue())) {
						collaudo.setMODOCOLLAUDO(SqlManager.getValueFromVectorParam(datiW9COLL, 2).getStringValue());
					}
				
					if (SqlManager.getValueFromVectorParam(datiW9COLL, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 3).getStringValue())) {
						collaudo.setDATANOMINACOLL(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9COLL, 3)));
					}
	
					if (SqlManager.getValueFromVectorParam(datiW9COLL, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 4).getStringValue())) {
						collaudo.setDATAINIZIOOPER(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9COLL, 4)));
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 5).getStringValue())) {
					collaudo.setDATACERTCOLLAUDO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9COLL, 5)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 6).getStringValue())) {
					collaudo.setDATADELIBERA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9COLL, 6)));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 7) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 7).getStringValue())) {
					collaudo.setESITOCOLLAUDO(FlagEsitoCollaudoType.Enum.forString(SqlManager.getValueFromVectorParam(datiW9COLL, 7).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 8) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 8).getStringValue())) {
					collaudo.setIMPFINALELAVORI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 8).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 9) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 9).getStringValue())) {
					collaudo.setIMPFINALESERVIZI(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 9).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 10) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 10).getStringValue())) {
					collaudo.setIMPFINALEFORNIT(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 10).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 11) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 11).getStringValue())) {
					collaudo.setIMPFINALESECUR(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 11).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 12) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 12).getStringValue())) {
					collaudo.setIMPPROGETTAZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 12).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 13) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 13).getStringValue())) {
					collaudo.setIMPDISPOSIZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 13).doubleValue())).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 14) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 14).getStringValue())) {
					collaudo.setAMMNUMDEFINITE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9COLL, 14).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 15) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 15).getStringValue())) {
					collaudo.setAMMNUMDADEF(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9COLL, 15).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 16) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 16).getStringValue())) {
					collaudo.setAMMIMPORTORICH(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 16).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 17) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 17).getStringValue())) {
					collaudo.setAMMIMPORTODEF(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 17).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 18) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 18).getStringValue())) {
					collaudo.setARBNUMDEFINITE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9COLL, 18).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 19) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 19).getStringValue())) {
					collaudo.setARBNUMDADEF(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9COLL, 19).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 20) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 20).getStringValue())) {
					collaudo.setARBIMPORTORICH(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 20).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 21) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 21).getStringValue())) {
					collaudo.setARBIMPORTODEF(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 21).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 22) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 22).getStringValue())) {
					collaudo.setGIUNUMDEFINITE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9COLL, 22).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 23) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 23).getStringValue())) {
					collaudo.setGIUNUMDADEF(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9COLL, 23).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 24) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 24).getStringValue())) {
					collaudo.setGIUIMPORTORICH(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 24).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 25) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 25).getStringValue())) {
					collaudo.setGIUIMPORTODEF(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 25).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 26) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 26).getStringValue())) {
					collaudo.setTRANUMDEFINITE(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9COLL, 26).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 27) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 27).getStringValue())) {
					collaudo.setTRANUMDADEF(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9COLL, 27).getStringValue()));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 28) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 28).getStringValue())) {
					collaudo.setTRAIMPORTORICH(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 28).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 29) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 29).getStringValue())) {
					collaudo.setTRAIMPORTODEF(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9COLL, 29).doubleValue())));
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 30) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 30).getStringValue())) {
					if ("1".equals(SqlManager.getValueFromVectorParam(datiW9COLL, 30).getStringValue())) {
						collaudo.setLAVORIESTESI(FlagSNType.S);
					} else {
						collaudo.setLAVORIESTESI(FlagSNType.N);
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 32).getValue() != null) {
					collaudo.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9COLL, 31).toString());
				}
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 33).getValue() != null) {
					collaudo.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9COLL, 32).toString());
				}

				// Incaricati del collaudo
				AggiudicazioneType aggiudicazioneType = AggiudicazioneType.Factory.newInstance();
				// Lista degli incaricati (sezione CO)
				this.setIncaricati(aggiudicazioneType, codgara, codlott, num, "W9INCA_COLLAUDO");
				if (aggiudicazioneType.sizeOfIncaricatiArray() > 0) {
					datiCollaudo.setIncaricatiArray(aggiudicazioneType.getIncaricatiArray());
				}
			}
		} catch (Exception e) {
			throw new GestoreException("Errore nella lettura dei dati del collaudo", "CollaudoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiCollaudo: fine metodo");
		}
	}

	/**
	 * Dati della fase di conclusione per contratti sopra soglia.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num
	 *            progressivo
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setDatiConclusione(SchedaCompletaType schedaCompleta, Long codgara, Long codlott, Long num) throws GestoreException {

		if (logger.isDebugEnabled())
			logger.debug("setDatiConclusione: inizio metodo");

		String selectW9CONC = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9CONC");

		try {
			List<?> datiW9CONC = this.sqlManager.getVector(selectW9CONC, new Object[] { codgara, codlott, num });

			if (datiW9CONC != null && datiW9CONC.size() > 0) {

				boolean isEmpty = true;
				ConclusioneType conclusione = ConclusioneType.Factory.newInstance();

				if (SqlManager.getValueFromVectorParam(datiW9CONC, 0).getValue() != null) {
					conclusione.setIDMOTIVOINTERR(SqlManager.getValueFromVectorParam(datiW9CONC, 0).getStringValue());
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 1).getStringValue())) {
					conclusione.setIDMOTIVORISOL(SqlManager.getValueFromVectorParam(datiW9CONC, 1).getStringValue());
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 2).getStringValue())) {
					conclusione.setDATARISOLUZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9CONC, 2)));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 3).getStringValue())) {
					conclusione.setFLAGONERI(SqlManager.getValueFromVectorParam(datiW9CONC, 3).getStringValue());
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 4).getStringValue())) {
					conclusione.setONERIRISOLUZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(datiW9CONC, 4).doubleValue())));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 5).getStringValue())) {
					if ("1".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 5).getStringValue())) {
						conclusione.setFLAGPOLIZZA(FlagSNType.S);
					} else {
						conclusione.setFLAGPOLIZZA(FlagSNType.N);
					}
					isEmpty = false;
				}

				if (SqlManager.getValueFromVectorParam(datiW9CONC, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 6).getStringValue())) {
					conclusione.setDATAULTIMAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9CONC, 6)));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 7) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 7).getStringValue())) {
					conclusione.setNUMINFORTUNI(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9CONC, 7).getStringValue()));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 8) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 8).getStringValue())) {
					conclusione.setNUMINFPERM(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9CONC, 8).getStringValue()));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 9) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 9).getStringValue())) {
					conclusione.setNUMINFMORT(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9CONC, 9).getStringValue()));
					isEmpty = false;
				}
				// Data verbale consegna definitiva
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 10) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 10).getStringValue())) {
					conclusione.setDATAVERBCONSEGNAAVVIO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9CONC, 10)));
					isEmpty = false;
				}
				// Termine contrattuale ultimazione
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 11) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 11).getStringValue())) {
					conclusione.setTERMINECONTRATTULTIMAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9CONC, 11)));
					isEmpty = false;
				}
				// Numero giorni di proroga concessi
				if (SqlManager.getValueFromVectorParam(datiW9CONC, 12) != null && !"".equals(SqlManager.getValueFromVectorParam(datiW9CONC, 12).getStringValue())) {
					conclusione.setNUMGIORNIPROROGA(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9CONC, 12).getStringValue()));
					isEmpty = false;
				}

				if (SqlManager.getValueFromVectorParam(datiW9CONC, 13).getValue() != null) {
					conclusione.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9CONC, 13).toString());
					isEmpty = false;
				}

				if (SqlManager.getValueFromVectorParam(datiW9CONC, 14).getValue() != null) {
					conclusione.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9CONC, 14).toString());
					isEmpty = false;
				}
				if (!isEmpty) {
					schedaCompleta.addNewDatiConclusione();
					schedaCompleta.setDatiConclusione(conclusione);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati di conclusione", "ConclusioneType", e);
		}

		if (logger.isDebugEnabled())
			logger.debug("setDatiConclusione: fine metodo");

	}

	/**
	 * Lista degli stati di avanzamento per contratti sopra soglia.
	 * 
	 * @param schedaCompleta
	 *            SchedaCompletaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num_appa
	 *            progressivo appalto
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setDatiAvanzamenti(SchedaCompletaType schedaCompleta, Long codgara, Long codlott, Long num, Long numappa) throws GestoreException {

		if (logger.isDebugEnabled())
			logger.debug("setDatiAvanzamenti: inizio metodo");

		String selectW9AVAN = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9AVAN");

		try {
			List<?> datiW9AVAN = this.sqlManager.getListVector(selectW9AVAN, new Object[] { codgara, codlott, numappa });

			if (datiW9AVAN != null && datiW9AVAN.size() > 0) {
				ListIterator<?> iterator = datiW9AVAN.listIterator();

				boolean isEmpty = true;
				AvanzamentiType avanzamenti = AvanzamentiType.Factory.newInstance();

				boolean avanzamentoValorizzato = false;

				while (iterator.hasNext() && !avanzamentoValorizzato) {
					List<?> riga = (List<?>) iterator.next();

					if (num != null && num.longValue() > 0) {
						Long numAvanzamento = (Long) SqlManager.getValueFromVectorParam(riga, 13).getValue();
						if (num.equals(numAvanzamento)) {
							AvanzamentoType avanzamento = avanzamenti.addNewAvanzamento();
							isEmpty = valorizzaAvanzamento(isEmpty, riga, avanzamento);
							avanzamentoValorizzato = true;
						}
					} else {
						AvanzamentoType avanzamento = avanzamenti.addNewAvanzamento();
						isEmpty = valorizzaAvanzamento(isEmpty, riga, avanzamento);
					}
				}

				if (!isEmpty) {
					schedaCompleta.addNewDatiAvanzamenti();
					schedaCompleta.setDatiAvanzamenti(avanzamenti);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati degli avanzamenti", "AvanzamentoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiAvanzamenti: fine metodo");
		}
	}

	/**
	 * @param isEmpty
	 * @param riga
	 * @param avanzamento
	 * @return
	 * @throws GestoreException
	 */
	private boolean valorizzaAvanzamento(boolean isEmpty, List<?> riga, AvanzamentoType avanzamento) throws GestoreException {

		if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null) {
			avanzamento.setFLAGPAGAMENTO(SqlManager.getValueFromVectorParam(riga, 0).getStringValue());
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
			avanzamento.setDATAANTICIPAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 1)));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
			avanzamento.setIMPORTOANTICIPAZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 2).doubleValue())));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
			avanzamento.setDATARAGGIUNGIMENTO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 3)));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
			avanzamento.setIMPORTOSAL(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 4).doubleValue())));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
			avanzamento.setDATACERTIFICATO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 5)));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
			avanzamento.setIMPORTOCERTIFICATO(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 6).doubleValue())));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 7) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 7).getStringValue())) {
			avanzamento.setFLAGRITARDO(FlagRitardoType.Enum.forString(SqlManager.getValueFromVectorParam(riga, 7).getStringValue()));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 8) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 8).getStringValue())) {
			avanzamento.setNUMGIORNISCOST(Integer.parseInt(SqlManager.getValueFromVectorParam(riga, 8).getStringValue()));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 9) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 9).getStringValue())) {
			avanzamento.setNUMGIORNIPROROGA(Integer.parseInt(SqlManager.getValueFromVectorParam(riga, 9).getStringValue()));
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 10) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 10).getStringValue())) {
			avanzamento.setDENOMAVANZAMENTO(SqlManager.getValueFromVectorParam(riga, 10).getStringValue());
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 11).getValue() != null) {
			avanzamento.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(riga, 11).toString());
			isEmpty = false;
		}
		if (SqlManager.getValueFromVectorParam(riga, 12).getValue() != null) {
			avanzamento.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(riga, 12).toString());
			isEmpty = false;
		}
		return isEmpty;
	}

	/**
	 * Set posizioni.
	 * 
	 * @param datiInizio
	 *            DatiInizioType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param numappa
	 *            progressivo appalto
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setPosizioni(DatiInizioType datiInizio, Long codgara, Long codlott, Long numappa) throws GestoreException {

		if (logger.isDebugEnabled())
			logger.debug("setPosizioni: inizio metodo");

		String selectW9AGGI_POSIZIONI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9AGGI_POSIZIONI");

		try {
			List<?> datiW9AGGI = this.sqlManager.getListVector(selectW9AGGI_POSIZIONI, new Object[] { codgara, codlott, numappa });

			if (datiW9AGGI != null && datiW9AGGI.size() > 0) {
				ListIterator<?> iterator = datiW9AGGI.listIterator();

				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					PosizioneType posizione = datiInizio.addNewPosizioni();

					if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null) {
						posizione.setCODICEFISCALEAGGIUDICATARIO(SqlManager.getValueFromVectorParam(riga, 0).getStringValue());
					} else {
						if (SqlManager.getValueFromVectorParam(riga, 4).getValue() != null) {
							posizione.setCODICEFISCALEAGGIUDICATARIO(SqlManager.getValueFromVectorParam(riga, 4).getStringValue());
						}
					}

					if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
						posizione.setCODICEINPS(SqlManager.getValueFromVectorParam(riga, 1).getStringValue());
					}
					if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
						posizione.setCODICEINAIL(SqlManager.getValueFromVectorParam(riga, 2).getStringValue());
					}
					if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
						posizione.setCODICECASSA(SqlManager.getValueFromVectorParam(riga, 3).getStringValue());
					}

					if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
						// Se lo stato e' 1 - ITALIA
						if ("1".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
							posizione.setCODICESTATO("");
						} else {
							// Ricavo la corrispondenza con il tabellato W3z12
							String codiceNazione = SqlManager.getValueFromVectorParam(riga, 5).getStringValue();
							String statoEstero = getStatoEstero(codiceNazione);
							posizione.setCODICESTATO(statoEstero);
						}
					} else {
						posizione.setCODICESTATO("");
					}

				}
			}
		} catch (SQLException e) {

			throw new GestoreException("Errore nella lettura dei dati delle posizioni contributive / assicurative", "PosizioneType", e);
		}

		if (logger.isDebugEnabled())
			logger.debug("setPosizioni: fine metodo");

	}

	/**
	 * Set Inizio.
	 * 
	 * @param datiInizio
	 *            DatiInizioType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num_iniz
	 *            progressivo
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setInizio(DatiInizioType datiInizio, Long codgara, Long codlott, Long num_iniz) throws GestoreException {

		if (logger.isDebugEnabled())
			logger.debug("setInizio: inizio metodo");

		String selectW9INIZ = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9INIZ");

		try {
			List<?> datiW9INIZ = this.sqlManager.getListVector(selectW9INIZ, new Object[] { codgara, codlott, num_iniz });

			if (datiW9INIZ != null && datiW9INIZ.size() > 0) {
				ListIterator<?> iterator = datiW9INIZ.listIterator();

				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					InizioType inizio = datiInizio.addNewInizio();

					if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null) {
						inizio.setDATASTIPULA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 0)));
					}
					if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
						inizio.setDATAESECUTIVITA(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 1)));
					}
					if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
						inizio.setIMPORTOCAUZIONE(new BigDecimal(this.convertiImporto(SqlManager.getValueFromVectorParam(riga, 2).doubleValue())));
					}
					if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
						inizio.setDATAINIPROGESEC(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 3)));
					}
					if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
						inizio.setDATAAPPPROGESEC(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 4)));
					}
					if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
						if ("1".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
							inizio.setFLAGFRAZIONATA(FlagSNType.S);
						} else {
							inizio.setFLAGFRAZIONATA(FlagSNType.N);
						}
					}
					if (SqlManager.getValueFromVectorParam(riga, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
						inizio.setDATAVERBALECONS(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 6)));
					}
					if (SqlManager.getValueFromVectorParam(riga, 7) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 7).getStringValue())) {
						inizio.setDATAVERBALEDEF(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 7)));
					}
					if (SqlManager.getValueFromVectorParam(riga, 8) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 8).getStringValue())) {
						if ("1".equals(SqlManager.getValueFromVectorParam(riga, 8).getStringValue())) {
							inizio.setFLAGRISERVA(FlagSNType.S.toString());
						} else {
							inizio.setFLAGRISERVA(FlagSNType.N.toString());
						}
					}
					if (SqlManager.getValueFromVectorParam(riga, 9) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 9).getStringValue())) {
						inizio.setDATAVERBINIZIO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 9)));
					}
					if (SqlManager.getValueFromVectorParam(riga, 10) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 10).getStringValue())) {
						inizio.setDATATERMINE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(riga, 10)).toString());
					}
					if (SqlManager.getValueFromVectorParam(riga, 11).getValue() != null) {
						inizio.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(riga, 11).toString());
					}
					if (SqlManager.getValueFromVectorParam(riga, 12).getValue() != null) {
						inizio.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(riga, 12).toString());
					}

				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati di inizio", "InizioType", e);
		}

		if (logger.isDebugEnabled())
			logger.debug("setInizio: fine metodo");

	}

	/**
	 * Set Pubblicazione esito.
	 * 
	 * @param datiInizio
	 *            DatiInizioType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num_iniz
	 *            progressivo
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setPubblicazioneEsito(DatiInizioType datiInizio, Long codgara, Long codlott, Long num_iniz) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setPubblicazioneEsito: inizio metodo");
		}
		String selectW9PUES = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9PUES");

		try {
			List<?> datiW9PUES = this.sqlManager.getVector(selectW9PUES, new Object[] { codgara, codlott, num_iniz });

			if (datiW9PUES != null && datiW9PUES.size() > 0) {

				boolean isEmpty = true;

				PubblicazioneType pubblicazioneEsito = PubblicazioneType.Factory.newInstance();

				if (SqlManager.getValueFromVectorParam(datiW9PUES, 0).getValue() != null) {
					pubblicazioneEsito.setDATAGUCE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9PUES, 0)));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUES, 1).getValue() != null) {
					pubblicazioneEsito.setDATAGURI(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9PUES, 1)));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUES, 2).getValue() != null) {
					pubblicazioneEsito.setDATAALBO(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9PUES, 2)));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUES, 3).getValue() != null) {
					pubblicazioneEsito.setQUOTIDIANINAZ(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9PUES, 3).toString()));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUES, 4).getValue() != null) {
					pubblicazioneEsito.setQUOTIDIANIREG(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9PUES, 4).toString()));
					isEmpty = false;
				}

				JdbcParametro flagSN = SqlManager.getValueFromVectorParam(datiW9PUES, 5);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						pubblicazioneEsito.setPROFILOCOMMITTENTE(FlagSNType.S);
						isEmpty = false;
					} else {
						pubblicazioneEsito.setPROFILOCOMMITTENTE(FlagSNType.N);
						isEmpty = false;
					}
				}
				flagSN = SqlManager.getValueFromVectorParam(datiW9PUES, 6);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						pubblicazioneEsito.setSITOMINISTEROINFTRASP(FlagSNType.S);
						isEmpty = false;
					} else {
						pubblicazioneEsito.setSITOMINISTEROINFTRASP(FlagSNType.N);
						isEmpty = false;
					}
				}
				flagSN = SqlManager.getValueFromVectorParam(datiW9PUES, 7);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						pubblicazioneEsito.setSITOOSSERVATORIOCP(FlagSNType.S);
						isEmpty = false;
					} else {
						pubblicazioneEsito.setSITOOSSERVATORIOCP(FlagSNType.N);
						isEmpty = false;
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUES, 8).getValue() != null) {
					pubblicazioneEsito.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9PUES, 8).toString());
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUES, 9).getValue() != null) {
					pubblicazioneEsito.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9PUES, 9).toString());
					isEmpty = false;
				}

				if (!isEmpty) {
					datiInizio.addNewPubblicazioneEsito();
					datiInizio.setPubblicazioneEsito(pubblicazioneEsito);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati di pubblicazione dell'esito", "PubblicazioneType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setPubblicazioneEsito: fine metodo");
		}
	}

	/**
	 * Dati di pubblicazione esito procedura per la stipula.
	 * 
	 * @param datiStipula
	 *            DatiStipulaType
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param num_iniz
	 *            progressivo
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void setPubblicazioneStipula(DatiStipulaType datiStipula, Long codgara, Long codlott, Long num_iniz) throws GestoreException {

		if (logger.isDebugEnabled())
			logger.debug("setPubblicazioneStipula: inizio metodo");

		String selectW9PUESSTIPULA = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9PUES_STIPULA");

		try {
			List<?> datiW9PUESSTIPULA = this.sqlManager.getVector(selectW9PUESSTIPULA, new Object[] { codgara, codlott, num_iniz });

			if (datiW9PUESSTIPULA != null && datiW9PUESSTIPULA.size() > 0) {

				boolean isEmpty = true;

				PubblicazioneType pubblicazione = PubblicazioneType.Factory.newInstance();

				if (SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 0).getValue() != null) {
					pubblicazione.setDATAGUCE(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 0)));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 1).getValue() != null) {
					pubblicazione.setDATAGURI(UtilityDateExtension.convertJdbcParametroDateToCalendar(SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 1)));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 2).getValue() != null) {
					pubblicazione.setQUOTIDIANINAZ(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 2).toString()));
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 3).getValue() != null) {
					pubblicazione.setQUOTIDIANIREG(Integer.parseInt(SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 3).toString()));
					isEmpty = false;
				}

				JdbcParametro flagSN = SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 4);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.S);
						isEmpty = false;
					} else {
						pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.N);
						isEmpty = false;
					}
				}
				flagSN = SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 5);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.S);
						isEmpty = false;
					} else {
						pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.N);
						isEmpty = false;
					}
				}
				flagSN = SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 6);
				if (flagSN.getValue() != null && !"".equals(flagSN.getStringValue())) {
					if ("1".equals(flagSN.getStringValue())) {
						pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.S);
						isEmpty = false;
					} else {
						pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.N);
						isEmpty = false;
					}
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 7).getValue() != null) {
					pubblicazione.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 7).toString());
					isEmpty = false;
				}
				if (SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 8).getValue() != null) {
					pubblicazione.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiW9PUESSTIPULA, 8).toString());
					isEmpty = false;
				}

				if (!isEmpty) {
					datiStipula.addNewPubblicazioneEsito();
					datiStipula.setPubblicazioneEsito(pubblicazione);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati di pubblicazione dell'esito", "PubblicazioneType", e);
		}

		if (logger.isDebugEnabled())
			logger.debug("setPubblicazioneStipula: fine metodo");

	}

	/**
	 * Gestione dell'archivio dei responsabili indicati nelle varie fasi
	 * dell'elemento SchedaCompleta.
	 * 
	 * @param responsabili
	 *            ResponsabilitYpe
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAnagraficaResponsabili(ResponsabiliType responsabili, Long codgara, Long codlott) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAnagraficaResponsabili: inizio metodo");
		}
		try {
			List<?> datiTECNI = null;

			String selectTECNI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "TECNI");
			datiTECNI = this.sqlManager.getListVector(selectTECNI, new Object[] { codgara, codlott });

			if (datiTECNI != null && datiTECNI.size() > 0) {
				ListIterator<?> iterator = datiTECNI.listIterator();
				boolean isEmpty = true;
				int i = 0;
				while (iterator.hasNext()) {

					List<?> riga = (List<?>) iterator.next();
					ResponsabileType responsabile = ResponsabileType.Factory.newInstance();

					if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 0).getStringValue())) {
						String cfResponsabile = SqlManager.getValueFromVectorParam(riga, 0).getStringValue();
						if (cfResponsabile.indexOf("\"") >= 0) {
							cfResponsabile = StringUtils.replace(cfResponsabile, "\"", "''");
						}
						responsabile.setCODICEFISCALERESPONSABILE(cfResponsabile);
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 1).getStringValue())) {
						String cognome = SqlManager.getValueFromVectorParam(riga, 1).getStringValue();
						if (cognome.length() > 50)
							cognome = cognome.substring(0, 50);
						if (cognome.indexOf("\"") >= 0) {
							cognome = StringUtils.replace(cognome, "\"", "''");
						}
						responsabile.setCOGNOME(cognome);
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
						String nome = SqlManager.getValueFromVectorParam(riga, 2).getStringValue();
						if (nome.length() > 50)
							nome = nome.substring(0, 50);
						if (nome.indexOf("\"") >= 0) {
							nome = StringUtils.replace(nome, "\"", "''");
						}
						responsabile.setNOME(nome);
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
						String telefono = SqlManager.getValueFromVectorParam(riga, 3).getStringValue();
						if (telefono.length() > 20)
							telefono = telefono.substring(0, 20);
						if (telefono.indexOf("\"") >= 0) {
							telefono = StringUtils.replace(telefono, "\"", "''");
						}
						responsabile.setTELEFONO(telefono);
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
						String email = SqlManager.getValueFromVectorParam(riga, 4).getStringValue();
						if (email.length() > 64)
							email = email.substring(0, 64);
						if (email.indexOf("\"") >= 0) {
							email = StringUtils.replace(email, "\"", "''");
						}
						responsabile.setEMAIL(email);
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
						String fax = SqlManager.getValueFromVectorParam(riga, 5).getStringValue();
						if (fax.length() > 20)
							fax = fax.substring(0, 20);
						if (fax.indexOf("\"") >= 0) {
							fax = StringUtils.replace(fax, "\"", "''");
						}
						responsabile.setFAX(fax);
						isEmpty = false;
					}
					// Gestione dell'indirizzo
					String indirizzo = "";
					if (SqlManager.getValueFromVectorParam(riga, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
						indirizzo += SqlManager.getValueFromVectorParam(riga, 6).getStringValue();
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 7) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 7).getStringValue())) {
						indirizzo += " - " + SqlManager.getValueFromVectorParam(riga, 7).getStringValue();
						isEmpty = false;
					}
					if (SqlManager.getValueFromVectorParam(riga, 8) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 8).getStringValue())) {
						indirizzo += ", " + SqlManager.getValueFromVectorParam(riga, 8).getStringValue();
						isEmpty = false;
					}

					if (!"".equals(indirizzo)) {
						if (indirizzo.length() > 100)
							indirizzo = indirizzo.substring(0, 100);
						if (indirizzo.indexOf("\"") >= 0) {
							indirizzo = StringUtils.replace(indirizzo, "\"", "''");
						}
						responsabile.setINDIRIZZO(indirizzo);
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 9) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 9).getStringValue())) {
						responsabile.setCAP(SqlManager.getValueFromVectorParam(riga, 9).getStringValue());
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 10) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 10).getStringValue())) {
						responsabile.setCODICEISTATCOMUNE(SqlManager.getValueFromVectorParam(riga, 10).getStringValue());
						isEmpty = false;
					}

					if (!isEmpty) {
						responsabili.addNewResponsabile();
						responsabili.setResponsabileArray(i, responsabile);
						i++;
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati dei responsabili", "IncaricatoType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAnagraficaResponsabili: fine metodo");
		}
	}

	/**
	 * Gestione dell'archivio delle ditte indicate come aggiudicatarie o
	 * subappaltatrici nelle varie fasi dell'elemento SchedaCompleta.
	 * 
	 * @param aggiudicatari
	 *            AggiudicatariType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setAnagraficaAggiudicatari(AggiudicatariType aggiudicatari, Long codgara, Long codlott) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setAnagraficaAggiudicatari: inizio metodo");
		}
		try {
			List<?> datiIMPR = null;

			String selectIMPR = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "IMPR");
			datiIMPR = this.sqlManager.getListVector(selectIMPR, new Object[] { codgara, codlott, codgara, codlott });

			if (datiIMPR != null && datiIMPR.size() > 0) {
				ListIterator<?> iterator = datiIMPR.listIterator();
				boolean isEmpty = true;
				int i = 0;
				
				while (iterator.hasNext()) {
				  
					List<?> riga = (List<?>) iterator.next();
					AggiudicatarioType aggiudicatario = AggiudicatarioType.Factory.newInstance();
					String cfImp = null;
					
					if (SqlManager.getValueFromVectorParam(riga, 0).getValue() != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 0).getStringValue())) {
						cfImp = SqlManager.getValueFromVectorParam(riga, 0).getStringValue();
						aggiudicatario.setCODICEFISCALEAGGIUDICATARIO(cfImp);
						isEmpty = false;
					}

					String denominazione = SqlManager.getValueFromVectorParam(riga, 1).getStringValue();
					if (denominazione.length() > 250) {
						denominazione = denominazione.substring(0, 250);
					}
					if (denominazione.indexOf("\"") >= 0) {
						denominazione = StringUtils.replace(denominazione, "\"", "''");
					}
					aggiudicatario.setDENOMINAZIONE(denominazione);

					
					// IMPR_LEGALIRAPPRESENTANTI			
					if (cfImp != null) {
						List<?> datiIMPR_LEGALIRAPPRESENTATI = null;
						String codimp = SqlManager.getValueFromVectorParam(riga, 10).getStringValue();
						String selectIMPR_LEGALIRAPPRESENTATI = this.queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "IMPR_LEGALIRAPPRESENTANTI");
						datiIMPR_LEGALIRAPPRESENTATI = this.sqlManager.getListVector(selectIMPR_LEGALIRAPPRESENTATI, new Object[] { codimp });

						if (datiIMPR_LEGALIRAPPRESENTATI != null && datiIMPR_LEGALIRAPPRESENTATI.size() > 0) {
							ListIterator<?> iterator_LR = datiIMPR_LEGALIRAPPRESENTATI.listIterator();
							List<?> riga_LR = (List<?>) iterator_LR.next();
							// nel caso di piu' legali rappresentanti con data di scadenza del mandato nulla scelgo il primo estratto
							
							// COGNOME
							if (SqlManager.getValueFromVectorParam(riga_LR, 0) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 0).getStringValue())) {
								String cognome = SqlManager.getValueFromVectorParam(riga_LR, 0).getStringValue();
								if (cognome.indexOf("\"") >= 0) {
									cognome = StringUtils.replace(cognome, "\"", "''");
								}
								aggiudicatario.setCOGNOME(StringUtils.substring(cognome,0,50));
								isEmpty = false;
							}
							
							// NOME
							if (SqlManager.getValueFromVectorParam(riga_LR, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 1).getStringValue())) {
								String nome = SqlManager.getValueFromVectorParam(riga_LR, 1).getStringValue();
								if (nome.indexOf("\"") >= 0) {
									nome = StringUtils.replace(nome, "\"", "''");
								}
								aggiudicatario.setNOME(StringUtils.substring(nome,0,50));
								isEmpty = false;
							}
							
							// CF LEGALE RAPPRESENTANTE
							if (SqlManager.getValueFromVectorParam(riga_LR, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 2).getStringValue())) {
								String cfRappresentante = SqlManager.getValueFromVectorParam(riga_LR, 2).getStringValue();
								if (cfRappresentante.indexOf("\"") >= 0) {
									cfRappresentante = StringUtils.replace(cfRappresentante, "\"", "''");
								}
								aggiudicatario.setCFRAPPRESENTANTE(cfRappresentante);
								isEmpty = false;
							}									
						} else {
							// nel caso di nessun legale rappresentante con data di scadenza del mandato nulla seleziono tra queli con data a termine impostata
							selectIMPR_LEGALIRAPPRESENTATI = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "IMPR_LEGALIRAPPRESENTANTI_DISPONIBILI");
							datiIMPR_LEGALIRAPPRESENTATI = this.sqlManager.getListVector(selectIMPR_LEGALIRAPPRESENTATI, new Object[] { codimp });

							if (datiIMPR_LEGALIRAPPRESENTATI != null && datiIMPR_LEGALIRAPPRESENTATI.size() > 0) {
								// la query estrarra' il rappresentante con data di fine mandato superiore
								ListIterator<?> iterator_LR = datiIMPR_LEGALIRAPPRESENTATI.listIterator();
								List<?> riga_LR = (List<?>) iterator_LR.next();
								
								// COGNOME
								if (SqlManager.getValueFromVectorParam(riga_LR, 0) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 0).getStringValue())) {
									String cognome = SqlManager.getValueFromVectorParam(riga_LR, 0).getStringValue();
									if (cognome.indexOf("\"") >= 0) {
										cognome = StringUtils.replace(cognome, "\"", "''");
									}
									aggiudicatario.setCOGNOME(StringUtils.substring(cognome,0,50));
									isEmpty = false;
								}
								
								// NOME
								if (SqlManager.getValueFromVectorParam(riga_LR, 1) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 1).getStringValue())) {
									String nome = SqlManager.getValueFromVectorParam(riga_LR, 1).getStringValue();
									if (nome.indexOf("\"") >= 0) {
										nome = StringUtils.replace(nome, "\"", "''");
									}
									aggiudicatario.setNOME(StringUtils.substring(nome,0,50));
									isEmpty = false;
								}
								
								// CF LEGALE RAPPRESENTANTE
								if (SqlManager.getValueFromVectorParam(riga_LR, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga_LR, 2).getStringValue())) {
									String cfRappresentante = SqlManager.getValueFromVectorParam(riga_LR, 2).getStringValue();
									if (cfRappresentante.indexOf("\"") >= 0) {
										cfRappresentante = StringUtils.replace(cfRappresentante, "\"", "''");
									}
									aggiudicatario.setCFRAPPRESENTANTE(cfRappresentante);
									isEmpty = false;
								}									
							}
						}
					}

					if (SqlManager.getValueFromVectorParam(riga, 2) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 2).getStringValue())) {
						String cameraCommercio = SqlManager.getValueFromVectorParam(riga, 2).getStringValue();
						if (cameraCommercio.length() > 50) {
							cameraCommercio = cameraCommercio.substring(0, 50);
						}
						if (cameraCommercio.indexOf("\"") >= 0) {
							cameraCommercio = StringUtils.replace(cameraCommercio, "\"", "''");
						}
						aggiudicatario.setCAMERACOMMERCIO(cameraCommercio);
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 3) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 3).getStringValue())) {
						String pIva = SqlManager.getValueFromVectorParam(riga, 3).getStringValue();
						if (pIva.indexOf("\"") >= 0) {
							pIva = StringUtils.replace(pIva, "\"", "''");
						}
						aggiudicatario.setPARTITAIVA(pIva);
						isEmpty = false;
					}

			

					if (SqlManager.getValueFromVectorParam(riga, 4) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 4).getStringValue())) {
						String indirizzo = SqlManager.getValueFromVectorParam(riga, 4).getStringValue();
						if (indirizzo.length() > 50) {
							indirizzo = indirizzo.substring(0, 50);
						}
						if (indirizzo.indexOf("\"") >= 0) {
							indirizzo = StringUtils.replace(indirizzo, "\"", "''");
						}
						aggiudicatario.setINDIRIZZO(indirizzo);
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 5) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 5).getStringValue())) {
						String civico = SqlManager.getValueFromVectorParam(riga, 5).getStringValue();
						if (civico.indexOf("\"") >= 0) {
							civico = StringUtils.replace(civico, "\"", "''");
						}
						aggiudicatario.setCIVICO(SqlManager.getValueFromVectorParam(riga, 5).getStringValue());
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 6) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 6).getStringValue())) {
						aggiudicatario.setCAP(SqlManager.getValueFromVectorParam(riga, 6).getStringValue());
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 7) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 7).getStringValue())) {
						String citta = SqlManager.getValueFromVectorParam(riga, 7).getStringValue();
						if (citta.length() > 50)
							citta = citta.substring(0, 50);
						if (citta.indexOf("\"") >= 0) {
							citta = StringUtils.replace(citta, "\"", "''");
						}
						aggiudicatario.setCITTA(citta);
						isEmpty = false;
					}

					if (SqlManager.getValueFromVectorParam(riga, 8) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 8).getStringValue())) {
						aggiudicatario.setPROVINCIA(SqlManager.getValueFromVectorParam(riga, 8).getStringValue().toUpperCase());
						isEmpty = false;
					}

					// Gestione della nazione
					if (SqlManager.getValueFromVectorParam(riga, 9) != null && !"".equals(SqlManager.getValueFromVectorParam(riga, 9).getStringValue())) {

						// Se lo stato e' 1 - ITALIA
						if ("1".equals(SqlManager.getValueFromVectorParam(riga, 9).getStringValue())) {
							aggiudicatario.setSOGGETTOESTERO(FlagSNType.N);
							aggiudicatario.setCODICESTATO("");
						} else {
							aggiudicatario.setSOGGETTOESTERO(FlagSNType.S);

							// Ricavo la corrispondenza con il tabellato W3z12
							String codiceNazione = SqlManager.getValueFromVectorParam(riga, 9).getStringValue();
							String statoEstero = this.getStatoEstero(codiceNazione);
							aggiudicatario.setCODICESTATO(statoEstero);
						}

						isEmpty = false;
					} else {
						aggiudicatario.setSOGGETTOESTERO(FlagSNType.N);
						aggiudicatario.setCODICESTATO("");
					}

					if (!isEmpty) {
						aggiudicatari.addNewAggiudicatario();
						aggiudicatari.setAggiudicatarioArray(i, aggiudicatario);
						i++;
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati degli aggiudicatari", "AggiudicatariType", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAnagraficaAggiudicatari: fine metodo");
		}
	}

	/**
	 * Crea l'elemento Schede (di tipo DatiAggiudicazioneType) Contiene i dati
	 * comuni, i dati di pubblicazione del bando di gara e l'elemento
	 * SchedaCompleta (a sua volta contiene tutte le fasi o eventi del contratto
	 * in esame).
	 * 
	 * @param datiAggiudicazione
	 *            DatiAggiudicazioneType
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param fase_esecuzione
	 *            fase esecuzione
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private void setSchede(DatiAggiudicazioneType datiAggiudicazione, Long codgara, Long codlott,
			Long fase_esecuzione, Long num) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("setSchede: inizio metodo");
		}

		// Dati comuni a tutte le fasi
		DatiComuniType datiComuni = datiAggiudicazione.addNewDatiComuni();
		this.setDatiComuni(datiComuni, codgara, codlott);

		// Dati di pubblicazione del bando di gara
		PubblicazioneType pubblicazione = PubblicazioneType.Factory.newInstance();
		if (this.setPubblicazione(pubblicazione, codgara, codlott)) {
			datiAggiudicazione.addNewPubblicazione();
			datiAggiudicazione.setPubblicazione(pubblicazione);
		}

		// Se il lotto e' stato aggiudicato, allora si aggiunge la scheda completa
		if ("1".equals(datiComuni.getESITOPROCEDURA()) || "5".equals(datiComuni.getESITOPROCEDURA())) {
			// Scheda Completa (contiene tutte le possibili fasi/eventi associati al contratto)
			SchedaCompletaType schedaCompleta = datiAggiudicazione.addNewSchedaCompleta();
			this.setSchedaCompleta(schedaCompleta, codgara, codlott, fase_esecuzione, num);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("setSchede: fine metodo");
		}
	}

	/**
	 * Metodo principale per l'esportazione.
	 * 
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @return Ritorna
	 * @throws GestoreException
	 *             GestoreException
	 * @throws IOException
	 *             IOException
	 */
	private Object[] exportXML(final Long codgara, final Long codlott) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("exportXML: inizio metodo");
		}

		String trasferimentoDatiXML = null;
		String trasferimentoDatiError = null;

		// Selezione dei lotti da esportare
		try {
			// Creazione del documento TrasferimentoDati
			TrasferimentoDatiDocument trasferimentoDatiDocument = TrasferimentoDatiDocument.Factory.newInstance();
			trasferimentoDatiDocument.documentProperties().setEncoding("UTF-8");

			// Creazione dell'elemento principale trasferimento dati
			TrasferimentoDati trasferimentoDati = trasferimentoDatiDocument.addNewTrasferimentoDati();

			// Data di creazione del flusso e numero lotti (sempre 1)
			TrasferimentoType trasferimento = trasferimentoDati.addNewInfoTrasferimento();
			trasferimento.setDATACREAZIONEFLUSSO(UtilityDateExtension.convertTodayToCalendar());
			trasferimento.setNUMSCHEDE(1);
			DatiAggiudicazioneType datiAggiudicazione = trasferimentoDati.addNewSchede();
			this.setSchede(datiAggiudicazione, codgara, codlott, null, null);

			// Creazione dell'elemento Responsabili (e' l'archivio di tutte le
			// anagrafiche dei responsabili/tecnici)
			ResponsabiliType responsabili = trasferimentoDati.addNewResponsabili();
			this.setAnagraficaResponsabili(responsabili, codgara, codlott);
  		
			// Se il lotto e' stato aggiudicato, allora  
			if ("1".equals(datiAggiudicazione.getDatiComuni().getESITOPROCEDURA()) || "5".equals(datiAggiudicazione.getDatiComuni().getESITOPROCEDURA())) {
				// Creazione dell'elemento Aggiudicatari (e' l'archivio di tutte le
				// anagrafiche delle ditte aggiudicatarie/affidatarie,
				// subappaltatrici e ausiliarie (in caso di avvalimento)
				AggiudicatariType aggiudicatari = trasferimentoDati.addNewAggiudicatari();
				this.setAnagraficaAggiudicatari(aggiudicatari, codgara, codlott);
			}

			// Conversione in stringa
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			trasferimentoDatiDocument.save(baos);
			trasferimentoDatiXML = baos.toString();
			baos.close();

			// Validazione e restituzione eventuali errori
			ArrayList<?> validationErrors = new ArrayList<Object>();
			XmlOptions validationOptions = new XmlOptions();
			validationOptions.setErrorListener(validationErrors);
			boolean isValid = trasferimentoDatiDocument.validate(validationOptions);
			if (!isValid) {
				trasferimentoDatiError = "";
				Iterator<?> iter = validationErrors.iterator();
				while (iter.hasNext()) {
					trasferimentoDatiError += iter.next() + "\n";
				}
			}

		} catch (IOException e) {
			throw new GestoreException("Errore generico di interazione con la base dati durante l'esportazione dei dati XML in formato SIMOG", "exportxml.sqlerror", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("exportXML: fine metodo");
		}

		return new Object[] { trasferimentoDatiXML, trasferimentoDatiError };

	}

	/**
	 * LoaderAppalto
	 * 
	 * @param codgaraP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa gara e lotto
	 * @param codlottoP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa gara e lotto
	 * @param codlottoP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa fase del lotto
	 * @param idFlusso
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questo flusso
	 * @param simoguser
	 *            se valorizzato la chiamata a LoaderAppalto sara' fatta con
	 *            questo utente
	 * @param simogpass
	 *            se valorizzato la chiamata a LoaderAppalto sara' fatta con
	 *            questa password
	 * @param eliminaScheda
	 *            se true il loader appalto viene chiamato per eliminare la 
	 *            scheda
	 */
	public ErroreType[] loaderAppalto(Long codgaraP, Long codlottoP, Long fase_esecuzioneP, Long numP, Long idFlusso, String simoguser, String simogpass, boolean eliminaScheda) throws GestoreException {
		return loaderAppalto(codgaraP, codlottoP, null, fase_esecuzioneP, numP, idFlusso, simoguser, simogpass, eliminaScheda);
	}

	public ErroreType[] loaderAppalto(Long codgaraP, Long codlottoP, Long numxmlP, Long fase_esecuzioneP, Long numP, Long idFlusso, String simoguser, String simogpass, boolean eliminaScheda) throws GestoreException {
		//boolean result = false;
		ErroreType[] erroriDalLoaderAppalto = null;
		if (logger.isDebugEnabled()) {
			logger.debug("loaderAppalto: inizio metodo (eliminaScheda: " + eliminaScheda + ")");
		}

		// Valore di default per il tipo di accesso ai servizi Simog
		int tipoAccessoWSSimog = 0;

		String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
		
		if (a != null && StringUtils.isNotEmpty(a)) {
			tipoAccessoWSSimog = Integer.parseInt(a);
		}

		switch (tipoAccessoWSSimog) {
		case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
		case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS:
		case CostantiSimog.ORT_CONSULTA_GARA_VIA_WS:
		  if (StringUtils.isNotEmpty(simoguser) && StringUtils.isNotEmpty(simogpass)) {
			  erroriDalLoaderAppalto = this.loaderAppaltoAccessoDiretto(codgaraP, codlottoP, numxmlP, fase_esecuzioneP, numP, idFlusso, simoguser, simogpass, eliminaScheda);
		  } else {
			  erroriDalLoaderAppalto = this.loaderAppaltoAccessoDiretto(codgaraP, codlottoP, numxmlP, fase_esecuzioneP, numP, idFlusso, eliminaScheda);
		  }
			break;

		default:
			String msgError = "Errore nella configurazione dell'applicazione: non e' stato " + "indicato il tipo di accesso al WS Simog per la funzione 'Consulta gara'";
			logger.error(msgError);
			break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("loaderAppalto: fine metodo (eliminaScheda: " + eliminaScheda + ")");
		}

		return erroriDalLoaderAppalto;
	}

	/**
	 * LoaderAppalto su WS con indicazione di user e password per SIMOG.
	 * 
	 * @param codgaraP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa gara e lotto
	 * @param codlottoP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa gara e lotto
	 * @param codlottoP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa fase del lotto
	 * @param idFlusso
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questo flusso
	 * @param simoguser
	 *            se valorizzato la chiamata a LoaderAppalto sara' fatta con
	 *            questo utente
	 * @param simogpass
	 *            se valorizzato la chiamata a LoaderAppalto sara' fatta con
	 *            questa password
	 * @param eliminaScheda
	 *            se true il loader appalto viene chiamato per eliminare la 
	 *            scheda
	 */
	private ErroreType[] loaderAppaltoAccessoDiretto(Long codgaraP, Long codlottoP, Long numxmlP, Long fase_esecuzioneP, Long numP, Long idFlusso, String simoguser, String simogpass, boolean eliminaScheda)
			throws GestoreException {

		ErroreType[] erroriDalLoaderAppalto = null;
		//boolean result = false;
		if (logger.isDebugEnabled()) {
			logger.debug("loaderAppaltoWS: inizio metodo");
		}

		// Se codgaraP, codlottoP, fase_esecuzioneP sono valorizzati allora si
		// e' in configurazione Vigilanza perche' si sta inviando la singola fase
		boolean invioSingolaFase = !(codgaraP == null && codlottoP == null && fase_esecuzioneP == null);
		String urlWsSimog = null;
		String loginWsSimog = null;
		String passwordWsSimog = null;
		String urlLoaderAppalto = null;
		String a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
		if (a != null && StringUtils.isNotEmpty(a)) {
			urlWsSimog = new String(a);
		} else {
			logger.error("URL per accesso ai servizi SIMOG non definita");
		}

		a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
		if (a != null && StringUtils.isNotEmpty(a)) {
			loginWsSimog = new String(a);
		} else {
			logger.error("Login per accesso ai servizi SIMOG non definita");
		}

		a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD);
		if (a != null && StringUtils.isNotEmpty(a)) {
			try {
				ICriptazioneByte cript = FactoryCriptazioneByte.getInstance(ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI), a.getBytes(),
						ICriptazioneByte.FORMATO_DATO_CIFRATO);
				passwordWsSimog = new String(cript.getDatoNonCifrato());
			} catch (CriptazioneException e) {
				logger.error("Errore nella decriptazione della password di accesso ai servizi SIMOG", e);
			}
		} else {
			logger.error("Password per accesso ai servizi SIMOG non definita");
		}

		a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_URL_LOADERAPPALTO);
		if (a != null && StringUtils.isNotEmpty(a)) {
			urlLoaderAppalto = new String(a);
		} else {
			logger.error("URL per accesso al LoaderAppalto non definita");
		}

		String ticket = null;
		if (simoguser != null) {
			loginWsSimog = simoguser;
		}
		if (simogpass != null) {
			passwordWsSimog = simogpass;
		}

		SimogWSPDDServiceStub simogWsPddServiceStub = null;
		if (StringUtils.isNotEmpty(urlWsSimog) && StringUtils.isNotEmpty(urlLoaderAppalto) 
		      && StringUtils.isNotEmpty(loginWsSimog) && StringUtils.isNotEmpty(passwordWsSimog)) {
			try {
				// Numero delle fasi da inviare. Se invioSingolaFase vale true allora si e'
				// in configurazione Vigilanza perche' si sta inviando la singola fase e
				// quindi il numero delle fasi da inviare e' pari a 1.
				// Se invece invioSingolaFase vale false si e' in configurazione Osservatorio e
				// bisogna determinare quante fasi ed esistono fasi con il campo DAEXPORT=1
				// allora il numero delle fasi da inviare e' pari al numero di record
				// contenuti in datiW9ListaFasi.
				// I numeroFasiDaInviare viene utilizzato anche per ricavare l'indice di collaborazione.
				// infatti se devo inviare una singola fase da vigilanza devo ricavare l'indice altrimenti in
				// configurazione osservatorio deve essere valorizzato a "-1"
				int numeroFasiDaInviare = 0;
				List<?> datiW9ListaFasi = null;
				if (invioSingolaFase) {
					numeroFasiDaInviare = 1;
				} else {
					datiW9ListaFasi = sqlManager.getListVector(SQL_FASI_DA_ESPORTARE, new Object[] {});
					if (datiW9ListaFasi != null && datiW9ListaFasi.size() > 0) {
						numeroFasiDaInviare = datiW9ListaFasi.size();
					}
				}
				if (numeroFasiDaInviare > 0) {
					// Ci sono delle fasi da inviare, quindi ci si collega ai servizi SIMOG effettuando la login
					simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimog);
					
					// Se definite, si settano url, port, username e password di accesso al proxy
				  // di rete del cliente per accedere al di fuori della intranet.
				  this.ticketSimogManager.setProxy(simogWsPddServiceStub);
					
				  LoginDocument loginDoc = LoginDocument.Factory.newInstance();
			    Login loginIn = Login.Factory.newInstance();
			    loginIn.setLogin(loginWsSimog);
			    loginIn.setPassword(passwordWsSimog);
			    loginDoc.setLogin(loginIn);

			    LoginResponseDocument loginResponseDoc = null; 

					try {
					  loginResponseDoc = simogWsPddServiceStub.login(loginDoc);
					} catch (RemoteException re) {
						logger.error("Errore lato server durante l'operazione di login ai Servizi SIMOG", re);
					}
					if (loginResponseDoc != null && loginResponseDoc.getLoginResponse() != null &&
					    loginResponseDoc.getLoginResponse().getReturn() != null) {
						if (loginResponseDoc.getLoginResponse().getReturn().getSuccess()) {
							ticket = loginResponseDoc.getLoginResponse().getReturn().getTicket();
							Collaborazioni arrayCollaborazioni = loginResponseDoc.getLoginResponse().getReturn().getColl();
							LoaderAppaltoDocument loaderAppaltoDocument = it.avlp.simog.massload.xmlbeans.loader.LoaderAppaltoDocument.Factory.newInstance();
							LoaderAppalto loaderAppalto = loaderAppaltoDocument.addNewLoaderAppalto();
							loaderAppalto.setTicket(ticket);
							try {
								LoaderAppaltoWSServiceStub loaderAppaltoWSServiceStub = new LoaderAppaltoWSServiceStub(urlLoaderAppalto);
								
                String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
                if (StringUtils.isNotEmpty(strTimeOut)) {
                  Integer timeOutClient = new Integer(strTimeOut);
                  loaderAppaltoWSServiceStub._getServiceClient().getOptions().setProperty(
                      HTTPConstants.SO_TIMEOUT, timeOutClient);
                  loaderAppaltoWSServiceStub._getServiceClient().getOptions().setProperty(
                      HTTPConstants.CONNECTION_TIMEOUT, timeOutClient);
                }
								
                // Se definite, si settano url, port, username e password di accesso al proxy di rete del
                // cliente per accedere al di fuori della intranet.
                this.setProxy(loaderAppaltoWSServiceStub);
                
								// if (loaderAppaltoWSServiceStub != null) {
								LoaderAppaltoResponseDocument responseLoaderAppalto = null;
								Long codgara;
								Long codlott;
								Long fase_esecuzione = null;
								Long num = null;
								for (int i = 0; i < numeroFasiDaInviare; i++) {
									String indexCollaborazione = "-1";
									if (invioSingolaFase) {
										codgara = codgaraP;
										codlott = codlottoP;
										fase_esecuzione = fase_esecuzioneP;
										num = numP;
										// Ricavo l'indice di collabolazione
										try {
											indexCollaborazione = this.getIndiceCollaborazioneAccessoDiretto(arrayCollaborazioni, codgara);
										} catch (SQLException sqle) {
											StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore nel determinare il centro di costo. ");
											strBuf.append("Messaggio di errore: ");
											strBuf.append(sqle.getMessage());
											strBuf.append(", causa errore: ");
											strBuf.append(sqle.getCause());
											strBuf.append(".\n\tDi seguito i dettagli della gara: CodiceGara=");
											strBuf.append(codgara);
											strBuf.append(", CodiceLotto=");
											strBuf.append(codlott);
											strBuf.append(", faseEsecuzione=");
											strBuf.append(fase_esecuzione);
											if (num != null) {
												strBuf.append(", progressivoFase=");
												strBuf.append(num);
											}
											logger.error(strBuf.toString());
										}
									} else {
										codgara = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 0).getValue();
										codlott = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 1).getValue();
										fase_esecuzione = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 2).getValue();
										num = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 3).getValue();
									}
									
									loaderAppalto.setIndexCollaborazione(indexCollaborazione);
									LoaderAppalto.TrasferimentoDati trasferimentoDati = this.getTrasferimentoDati(codgara, codlott, fase_esecuzione, num, eliminaScheda);
									loaderAppalto.setTrasferimentoDati(trasferimentoDati);

									if (loaderAppaltoDocument.validate()) {
										try {
											responseLoaderAppalto = loaderAppaltoWSServiceStub.loaderAppalto(loaderAppaltoDocument);
											// Inserimento record in W9XML
											Long numxml = this.insertW9XML(codgara, codlott, idFlusso, trasferimentoDati,fase_esecuzione,num);
											if (numxml > 0) {
												// aggiorno il feedback
												String CUI = this.readResponseLoaderAppalto(codgara, codlott, fase_esecuzione, num, numxml,
													eliminaScheda, responseLoaderAppalto);
												if (StringUtils.isNotEmpty(CUI)) {
													boolean isCancellazioneAggiudazione = eliminaScheda && 
														(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA == fase_esecuzione.intValue() || 
																CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE == fase_esecuzione.intValue() ||
																		CostantiW9.ADESIONE_ACCORDO_QUADRO == fase_esecuzione.intValue());
													this.aggiornaCODCUI(codgara, codlott, CUI, isCancellazioneAggiudazione);
												}
												
												if (StringUtils.equals("5", ConfigManager.getValore("it.eldasoft.simog.tipoAccesso"))) {
													// aggiorno lo stato della fase (DAEXPORT='2') solo se e' ORT ad eseguire questo codice 
													// ed il metodo readResponseLoaderAppalto diversa da 'SIMOGWS_WSSMANAGER_NULL_15',
													// Infatti ORT mette DAEXPORT='2' ogni fase inviata a SIMOG a prescindere dall'esito dell'invio,
													// cioe' a prescindere dal numeri di errori
													if (!"SIMOGWS_WSSMANAGER_NULL_15".equals(CUI)) {
														this.aggiornaStatoFase(codgara, codlott, fase_esecuzione, num);
													}
												}
											}
										} catch (RemoteException re) {
											// Errore lato server: si logga su  file l'errore e si continua a ciclare
											StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore lato server ANAC nell'interazione con il LoaderAppalto. ");
											strBuf.append("Messaggio di errore: ");
											strBuf.append(re.getMessage());
											strBuf.append(", causa errore: ");
											strBuf.append(re.getCause());
											strBuf.append(".\nDi seguito i dettagli della gara: CodiceGara=");
											strBuf.append(codgara);
											strBuf.append(", CodiceLotto=");
											strBuf.append(codlott);
											strBuf.append(", faseEsecuzione=");
											strBuf.append(fase_esecuzione);
											if (num != null) {
												strBuf.append(", progressivoFase=");
												strBuf.append(num);
											}
											logger.error(strBuf.toString());
											throw new GestoreException(
											          "errore lato server ANAC nell'interazione con il LoaderAppalto.", "errors.generico", re);
										}
									} else {
										// L'oggetto trasferimentoDati non e' stato validato.
										// Saltare il flusso, loggare qualcosa di esplicativo.
										StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: il messaggio XML composto non ha superato la validazione. ");
										strBuf.append(".Di seguito i dettagli della gara: CodiceGara=");
										strBuf.append(codgara);
										strBuf.append(", CodiceLotto=");
										strBuf.append(codlott);
										strBuf.append(", faseEsecuzione=");
										strBuf.append(fase_esecuzione);
										if (num != null) {
											strBuf.append(", progressivoFase=");
											strBuf.append(num);
										}
										try {
											ArrayList<XmlValidationError> arrayValidazione = this.validaMsgXML(loaderAppaltoDocument);
											if (arrayValidazione != null && arrayValidazione.size() > 0) {
												DettaglioErrori dettErrori = DettaglioErrori.Factory.newInstance();
												strBuf.append(".\n\t");
												for (XmlValidationError xmlValidationError : arrayValidazione) {
													ErroreType itemError = dettErrori.addNewErrore();// erroriDalLoaderAppalto[j];
													itemError.setDescrizione(xmlValidationError.getMessage());
													itemError.setTipo(Tipo.VALIDAZXML);
													strBuf.append(xmlValidationError.getMessage());
													strBuf.append("\n\t");
												}
												erroriDalLoaderAppalto = dettErrori.getErroreArray();
												logger.error(strBuf.toString());
											}
										} catch (IOException ioe) {
											strBuf = new StringBuffer("Errore nella validazione della scheda. ");
											strBuf.append("Messaggio di errore: ");
											strBuf.append(ioe.getMessage());
											strBuf.append(". Causa errore: ");
											strBuf.append(ioe.getCause());
											strBuf.append("\n\tDi seguito i dettagli: CodiceGara=");
											strBuf.append(codgara);
											strBuf.append(", CodiceLotto=");
											strBuf.append(codlott);
											strBuf.append(", faseEsecuzione=");
											strBuf.append(fase_esecuzione);
											if (num != null) {
												strBuf.append(", progressivoFase=");
												strBuf.append(num);
											}
											logger.error(strBuf.toString());
											throw new GestoreException(
											          "Errore nella validazione della scheda.", "errors.generico", ioe);
										}
									}
								}
								// }
							} catch (AxisFault af) {
								StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore nell'istanziare l'oggetto LoaderAppaltoWSServiceStub. Messaggio di errore: ");
								strBuf.append(af.getMessage());
								strBuf.append(", causa errore: ");
								strBuf.append(af.getCause());
								strBuf.append(", faultType: ");
								strBuf.append(af.getFaultType());
								logger.error(strBuf.toString());
								throw new GestoreException(
								          "Errore durante il caricamento del servizio loaderAppalto", "errors.generico", af);
							}
						} else {
							// Errore nell'istanziazione
							String message = "SIMOG-loaderAppalto: problema durante l'autenticazione al servizio SIMOG";
							message = (new StringBuilder()).append(message).append(loginResponseDoc.getLoginResponse().getReturn().getError()).toString();
							logger.error(message);
							throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'autenticazione al servizio SIMOG LoaderAppalto", "loaderAppalto.login");
						}
					} else {
						logger.error("Il metodo login del servizio WsSimog ha restituito un oggetto null");
						throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'autenticazione al servizio SIMOG LoaderAppalto", "loaderAppalto.login");
					}
				} else {
					// in base dati non ci sono fasi da inviare a SIMOG
				}
			} catch (GestoreException e) {
				throw new GestoreException(e.getMessage(), e.getCodice(), e);
			} catch (Exception e) {
				logger.error("SIMOG-loaderAppalto: ", e);
				throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'invio delle schede del lotto al servizio SIMOG LoaderAppalto", "loaderAppalto.invio");
			} finally {
				if (ticket != null) {
					// Chiusura della connessione
			    ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
			    chiudiSessione.setTicket(ticket);
			    ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
			    chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
			    
					try {
						ChiudiSessioneResponseDocument rispostaChiudiSessione = simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
						if (!rispostaChiudiSessione.getChiudiSessioneResponse().getReturn().getSuccess()) {
							logger.error("SIMOG-loaderAppalto: La chiusura della connessione identificata dal ticket " + ticket + " ha generato il seguente errore: "
									+ rispostaChiudiSessione.getChiudiSessioneResponse().getReturn().getError());
						}
					} catch (RemoteException re) {
						logger.error("SIMOG-loaderAppalto: ", re);
					}
				}
			}
		} else {
			String message = "SIMOG-loaderAppalto: I parametri di connessione al servizio SIMOG LoaderAppalto non sono specificati correttamente";
			logger.error(message);
			throw new GestoreException("SIMOG-loaderAppalto: I parametri di connessione al servizio SIMOG LoaderAppalto non sono specificati correttamente", "loaderAppalto.notParam");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("loaderAppaltoWS: fine metodo");
		}
		return erroriDalLoaderAppalto;
	}

	
	/**
   * Loader appalto su WS.
   * 
   * @param codgaraP
   *            se valorizzato la chiamata a LoaderAppalto sara' relativa
   *            solamente a questa gara e lotto
   * @param codlottoP
   *            se valorizzato la chiamata a LoaderAppalto sara' relativa
   *            solamente a questa gara e lotto
   * @param codlottoP
   *            se valorizzato la chiamata a LoaderAppalto sara' relativa
   *            solamente a questa fase del lotto
   * @param idFlusso
   *            se valorizzato la chiamata a LoaderAppalto sara' relativa
   *            solamente a questo flusso
   * @param simoguser
   *            se valorizzato la chiamata a LoaderAppalto sara' fatta con
   *            questo utente
   * @param simogpass
   *            se valorizzato la chiamata a LoaderAppalto sara' fatta con
   *            questa password
   * @param eliminaScheda
   *            se true il loader appalto viene chiamato per eliminare la 
   *            scheda
   */
  private ErroreType[] loaderAppaltoAccessoDiretto(Long codgaraP, Long codlottoP, Long numxmlP, Long fase_esecuzioneP,
      Long numP, Long idFlusso, boolean eliminaScheda) throws GestoreException {
	  ErroreType[] erroriDalLoaderAppalto = null;
    //boolean result = false;
    if (logger.isDebugEnabled()) {
      logger.debug("loaderAppaltoWS: inizio metodo");
    }

    // Se codgaraP, codlottoP, fase_esecuzioneP sono valorizzati allora si e' in
    // configurazione Vigilanza perche' si sta inviando la singola fase
    boolean invioSingolaFase = !(codgaraP == null && codlottoP == null && fase_esecuzioneP == null);
    String urlLoaderAppalto = null;
    String a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_URL_LOADERAPPALTO);
    if (StringUtils.isNotEmpty(a)) {
      urlLoaderAppalto = new String(a);
    } else {
      logger.error("URL per accesso al LoaderAppalto non definita");
    }

    String ticket = null;

    if (StringUtils.isNotEmpty(urlLoaderAppalto)) {
      try {
        // Numero delle fasi da inviare. Se invioSingolaFase vale true allora si e'
        // in configurazione Vigilanza perche' si sta inviando la singola fase e
        // quindi il numero delle fasi da inviare e' pari a 1.
        // Se invece invioSingolaFase vale false si e' in configurazione Osservatorio e
        // bisogna determinare quante fasi ed esistono fasi con il campo DAEXPORT=1
        // allora il numero delle fasi da inviare e' pari a al numero di record
        // contenuti in datiW9ListaFasi.
        // I numeroFasiDaInviare viene utilizzato anche per ricavare l'indice di collaborazione.
        // infatti se devo inviare una singola fase da vigilanza devo ricavare l'indice altrimenti in
        // configurazione osservatorio deve essere valorizzato a "-1"
        int numeroFasiDaInviare = 0;
        List<?> datiW9ListaFasi = null;
        if (invioSingolaFase) {
          numeroFasiDaInviare = 1;
        } else {
          datiW9ListaFasi = this.sqlManager.getListVector(SQL_FASI_DA_ESPORTARE, new Object[] {});
          if (datiW9ListaFasi != null && datiW9ListaFasi.size() > 0) {
            numeroFasiDaInviare = datiW9ListaFasi.size();
          }
        }
        if (numeroFasiDaInviare > 0) {
          // Ci sono delle fasi da inviare, quindi ci si collega ai servizi SIMOG
          
          TicketSimogBean ticketSimogBean = this.ticketSimogManager.getTicket();
          if (ticketSimogBean != null) {
            if (ticketSimogBean.isSuccess()) {
              ticket = ticketSimogBean.getTicketSimog();
              LoaderAppaltoDocument inputLoaderAppalto = LoaderAppaltoDocument.Factory.newInstance();
              LoaderAppalto loaderAppalto = inputLoaderAppalto.addNewLoaderAppalto();
              loaderAppalto.setTicket(ticket);
              try {
                LoaderAppaltoWSServiceStub loaderAppaltoWSServiceStub = new LoaderAppaltoWSServiceStub(urlLoaderAppalto);
  
                String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
                if (StringUtils.isNotEmpty(strTimeOut)) {
                  Integer timeOutClient = new Integer(strTimeOut);
                  loaderAppaltoWSServiceStub._getServiceClient().getOptions().setProperty(
                      HTTPConstants.SO_TIMEOUT, timeOutClient);
                  loaderAppaltoWSServiceStub._getServiceClient().getOptions().setProperty(
                      HTTPConstants.CONNECTION_TIMEOUT, timeOutClient);
                }
                
                // Se definite, si settano url, port, username e password di accesso al proxy
                // di rete del cliente per accedere al di fuori della intranet.
                this.setProxy(loaderAppaltoWSServiceStub);
                
                // if (loaderAppaltoWSServiceStub != null) {
                LoaderAppaltoResponseDocument responseLoaderAppalto = null;
                Long codgara;
                Long codlott;
                Long fase_esecuzione = null;
                Long num = null;
                
                if (logger.isDebugEnabled()) {
                	logger.debug("Numero fasi da inviare: " + numeroFasiDaInviare + ". Inizio del ciclo su tutte le fasi (flag elimina scheda=" + eliminaScheda + ")");
                }
                
                for (int i = 0; i < numeroFasiDaInviare; i++) {
              	  String indexCollaborazione = "-1";
              	  
              	  if (invioSingolaFase) {
              		  codgara = codgaraP;
              		  codlott = codlottoP;
              		  fase_esecuzione = fase_esecuzioneP;
              		  num = numP;
              	  } else {
              		  codgara = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 0).getValue();
              		  codlott = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 1).getValue();
              		  fase_esecuzione = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 2).getValue();
              		  num = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 3).getValue();
              	  }
  
              	  // Ricavo l'indice di collabolazione            		  
              	  try {
              	    indexCollaborazione = this.getIndiceCollaborazioneAccessoDiretto(
              	        ticketSimogBean.getCollaborazioni(), codgara);
              	  } catch (SQLException sqle) {
                    StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore nel determinare l'indice di collaborazione. ");
                    strBuf.append("Di seguito i dettagli della gara: codice gara=");
                    strBuf.append(codgara);
                    strBuf.append(", codice lotto=");
                    strBuf.append(codlott);
                    strBuf.append(", fase esecuzione=");
                    strBuf.append(fase_esecuzione);
                    if (num != null) {
                      strBuf.append(", progressivo fase=");
                      strBuf.append(num);
                    }
                    logger.error(strBuf.toString());
              	  }
              	  
                  if (logger.isDebugEnabled()) {
                    logger.debug("Preparazione dati e invio dati al loaderAppalto per CodiceGara=" + codgara + 
                        ", CodiceLotto=" + codlott + ", Fase esecuzione=" + fase_esecuzione + 
                        ", Progressivo fase=" + num + " e Elimina scheda=" + eliminaScheda);
                  }

                  loaderAppalto.setIndexCollaborazione(indexCollaborazione);
                  it.avlp.simog.massload.xmlbeans.loader.LoaderAppalto.TrasferimentoDati trasferimentoDati =
                      this.getTrasferimentoDati(codgara, codlott, fase_esecuzione, num, eliminaScheda);
                  loaderAppalto.setTrasferimentoDati(trasferimentoDati);

                  if (inputLoaderAppalto.validate()) {
                    try {
                      responseLoaderAppalto = loaderAppaltoWSServiceStub.loaderAppalto(inputLoaderAppalto);
                      // Inserimento record in W9XML
                      Long numxml = this.insertW9XML(codgara, codlott, idFlusso, trasferimentoDati,fase_esecuzione,num);
                      if (numxml > 0) {
                        // aggiorno il feedback
                        String CUI = this.readResponseLoaderAppalto(codgara, codlott, fase_esecuzione, num, numxml,
                            eliminaScheda, responseLoaderAppalto);
                        if (StringUtils.isNotEmpty(CUI)) {
                          boolean isCancellazioneAggiudazione = eliminaScheda && 
                          		(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA == fase_esecuzione.intValue() || 
                              	CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE == fase_esecuzione.intValue() ||
                                  	CostantiW9.ADESIONE_ACCORDO_QUADRO == fase_esecuzione.intValue());
                          this.aggiornaCODCUI(codgara, codlott, CUI, isCancellazioneAggiudazione);
                          logger.info("XML INVIATO A SIMOG = " + trasferimentoDati.toString());
                        }
                        if (StringUtils.equals("5", ConfigManager.getValore("it.eldasoft.simog.tipoAccesso"))) {
                        	// aggiorno lo stato della fase (DAEXPORT='2') solo se e' ORT ad eseguire questo codice 
                        	// ed il metodo readResponseLoaderAppalto diversa da 'SIMOGWS_WSSMANAGER_NULL_15'.
                        	// Infatti ORT mette DAEXPORT='2' ogni fase inviata a SIMOG a prescindere dall'esito dell'invio,
                        	// cioe' a prescindere dal numeri di errori 
                        	if (!"SIMOGWS_WSSMANAGER_NULL_15".equals(CUI)) {
                        		this.aggiornaStatoFase(codgara, codlott, fase_esecuzione, num);
                        	}
                        }
                      }
                    } catch (RemoteException re) {
                      // Errore lato server: si logga su file l'errore e si continua a ciclare
                      StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore lato server ANAC nell'interazione con il LoaderAppalto. ");
                      strBuf.append("Di seguito i dettagli della gara: codice gara=");
                      strBuf.append(codgara);
                      strBuf.append(", codice lotto=");
                      strBuf.append(codlott);
                      strBuf.append(", fase esecuzione=");
                      strBuf.append(fase_esecuzione);
                      if (num != null) {
                        strBuf.append(", progressivo fase=");
                        strBuf.append(num);
                      }
                      logger.error(strBuf.toString(), re);
                      throw new GestoreException("errore lato server ANAC nell'interazione con il LoaderAppalto.", "errors.generico", re);
                      
                    } catch (Throwable t) {
                      StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore inaspettato nell'interazione con il LoaderAppalto. ");
                      strBuf.append("Di seguito i dettagli della gara: codice gara=");
                      strBuf.append(codgara);
                      strBuf.append(", codice lotto=");
                      strBuf.append(codlott);
                      strBuf.append(", fase esecuzione=");
                      strBuf.append(fase_esecuzione);
                      if (num != null) {
                        strBuf.append(", progressivo fase=");
                        strBuf.append(num);
                      }
                      
                      logger.error(strBuf.toString(), t);
                      throw new GestoreException("errore lato server ANAC nell'interazione con il LoaderAppalto.", "errors.generico", t);
                      
                    }
                  } else {
                    // l'oggetto trasferimentoDati non e' stato validato.
                    // Saltare il flusso, loggare qualcosa di esplicativo
                    StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: il messaggio XML composto non ha superato la validazione. ");
                    strBuf.append(".Di seguito i dettagli della gara: codice gara=");
                    strBuf.append(codgara);
                    strBuf.append(", codice lotto=");
                    strBuf.append(codlott);
                    strBuf.append(", fase esecuzione=");
                    strBuf.append(fase_esecuzione);
                    if (num != null) {
                      strBuf.append(", progressivo fase=");
                      strBuf.append(num);
                    }
                    try {
                      ArrayList<XmlValidationError> arrayValidazione = this.validaMsgXML(inputLoaderAppalto);
                      if (arrayValidazione != null && arrayValidazione.size() > 0) {
                        DettaglioErrori dettErrori = DettaglioErrori.Factory.newInstance();
                        strBuf.append(".\n\t");
                        for (XmlValidationError xmlValidationError : arrayValidazione) {
                          ErroreType itemError = dettErrori.addNewErrore();// erroriDalLoaderAppalto[j];
                          itemError.setDescrizione(xmlValidationError.getMessage());
                          itemError.setTipo(Tipo.VALIDAZXML);
                          strBuf.append(xmlValidationError.getMessage());
                          strBuf.append("\n\t");
                        }
                        erroriDalLoaderAppalto = dettErrori.getErroreArray();
                        logger.error(strBuf.toString());
                      }
                    } catch (IOException ioe) {
                      strBuf = new StringBuffer("Errore nella validazione della scheda. ");
                      strBuf.append("Di seguito i dettagli: codice gara=");
                      strBuf.append(codgara);
                      strBuf.append(", codice lotto=");
                      strBuf.append(codlott);
                      strBuf.append(", fase esecuzione=");
                      strBuf.append(fase_esecuzione);
                      if (num != null) {
                        strBuf.append(", progressivo fase=");
                        strBuf.append(num);
                      }
                      logger.error(strBuf.toString(), ioe);
                      throw new GestoreException(
  					          "Errore nella validazione della scheda.", "errors.generico", ioe);
                    }
                  }
                } // fine ciclo sulla fasi da inviare
                
              } catch (AxisFault af) {
                StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore nell'istanziare l'oggetto LoaderAppaltoWSServiceStub. Messaggio di errore: ");
                logger.error(strBuf.toString(), af);
                throw new GestoreException(
  			          "errore nell'istanziare l'oggetto LoaderAppaltoWSServiceStub.", "errors.generico", af);
              }
            } else {
              // Errore nell'istanziazione
              String message = "SIMOG-loaderAppalto: problema durante l'autenticazione al servizio SIMOG";
              message = message.concat(ticketSimogBean.getMsgError());
              logger.error(message);
              throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'autenticazione al servizio SIMOG LoaderAppalto", "loaderAppalto.login");
            }
          } else {
            // Ticket simog e' null
          }
        } else {
          // in base dati non ci sono fasi da inviare a SIMOG
        }
      } catch (SQLException sqle) {
        logger.error("SIMOG-loaderAppalto: ", sqle);
        throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'invio delle schede del lotto al servizio SIMOG LoaderAppalto", "loaderAppalto.invio");
      } catch (GestoreException e) {
        throw new GestoreException(e.getMessage(), e.getCodice(), e);
      } catch (Throwable t) {
        logger.error("SIMOG-loaderAppalto: errore inaspettato nell'invio dei flussi al LoaderAppalto (2)", t);
        throw new GestoreException(
		          "errore inaspettato nell'invio dei flussi al LoaderAppalto (2)", "errors.generico", t);
      } finally {
        if (ticket != null) {
          this.ticketSimogManager.rilasciaTicket();
        }
      }
    } else {
      String message = "SIMOG-loaderAppalto: I parametri di connessione al servizio SIMOG LoaderAppalto non sono specificati correttamente";
      logger.error(message);
      throw new GestoreException("SIMOG-loaderAppalto: I parametri di connessione al servizio SIMOG LoaderAppalto non sono specificati correttamente", "loaderAppalto.notParam");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("loaderAppaltoWS: fine metodo");
    }
    return erroriDalLoaderAppalto;
  }

	/**
	 * Loader Appalto su PDD con indicazione di user e password di SIMOG.
	 * 
	 * @param codgaraP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa gara e lotto
	 * @param codlottoP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa gara e lotto
	 * @param fase_esecuzioneP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa fase del lotto
	 * @param numP
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questa i-esima fase del lotto
	 * @param idFlusso
	 *            se valorizzato la chiamata a LoaderAppalto sara' relativa
	 *            solamente a questo flusso
	 * @param simoguser
	 *            se valorizzato la chiamata a LoaderAppalto sara' fatta con
	 *            questo utente
	 * @param simogpass
	 *            se valorizzato la chiamata a LoaderAppalto sara' fatta con
	 *            questa password
	 * @param eliminaScheda
	 *            se true il loader appalto viene chiamato per eliminare la 
	 *            scheda
	 */
	/*private ErroreType[] loaderAppaltoAccessoIndiretto(Long codgaraP, Long codlottoP, Long numxmlP, Long fase_esecuzioneP,
	    Long numP, Long idFlusso, String simoguser, String simogpass, boolean eliminaScheda) throws GestoreException {
		//boolean result = false;
		ErroreType[] erroriDalLoaderAppalto = null;
		if (logger.isDebugEnabled()) {
			logger.debug("loaderAppaltoAccessoIndiretto con credenziali simog: inizio metodo");
		}

		// Se codgaraP, codlottoP, fase_esecuzioneP sono valorizzati allora si
		// e' in configurazione Vigilanza perche' si sta inviando la singola fase
		boolean invioSingolaFase = !(codgaraP == null && codlottoP == null && fase_esecuzioneP == null);
		String urlLoaderAppaltoPDD = null;
		String urlWsSimogPDD = null;
		String loginWsSimog = null;
		String passwordWsSimog = null;

		String a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
		if (a != null && StringUtils.isNotEmpty(a)) {
			urlWsSimogPDD = new String(a);
		} else {
			logger.error("URL della PDD per accesso ai servizi SIMOG non definita");
		}

		a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
		if (a != null && StringUtils.isNotEmpty(a)) {
			loginWsSimog = new String(a);
		} else {
			logger.error("Login per accesso ai servizi SIMOG non definita");
		}

		a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD);
		if (a != null && StringUtils.isNotEmpty(a)) {
			try {
				ICriptazioneByte cript = FactoryCriptazioneByte.getInstance(
				    ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI), a.getBytes(),
						ICriptazioneByte.FORMATO_DATO_CIFRATO);
				passwordWsSimog = new String(cript.getDatoNonCifrato());
			} catch (CriptazioneException e) {
				logger.error("Errore nella decriptazione della password di accesso ai servizi SIMOG", e);
			}
		} else {
			logger.error("Password per accesso ai servizi SIMOG non definita");
		}

		a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_URL_LOADERAPPALTO);
		if (a != null && StringUtils.isNotEmpty(a)) {
			urlLoaderAppaltoPDD = new String(a);
		} else {
			logger.error("URL della PDD per il LoaderAppalto non definita");
		}

		String ticket = null;
		if (simoguser != null) {
			loginWsSimog = simoguser;
		}
		if (simogpass != null) {
			passwordWsSimog = simogpass;
		}
		if (StringUtils.isNotEmpty(urlWsSimogPDD) && StringUtils.isNotEmpty(urlLoaderAppaltoPDD) && StringUtils.isNotEmpty(loginWsSimog) && StringUtils.isNotEmpty(passwordWsSimog)) {

			it.avlp.simog.massload.xmlbeans.ResponseCheckLogin responseCheckLogin = null;
			it.avlp.simog.massload.xmlbeans.ResponseChiudiSession responseChiudiSessione = null;
			SimogWSPDDServiceStub simogWsPddServiceStub = null;

			try {
				// Numero delle fasi da inviare. Se invioSingolaFase vale true allora si e'
				// in configurazione Vigilanza perche' si sta inviando la singola fase e
				// quindi il numero delle fasi da inviare e' pari a 1.
				// Se invece invioSingolaFase vale false si e' in configurazione Osservatorio e
				// bisogna determinare quante fasi ed esistono fasi con il campo DAEXPORT=1
				// allora il numero delle fasi da inviare e' pari a al numero di
				// record contenuti in datiW9ListaFasi.
				// I numeroFasiDaInviare viene utilizzato anche per ricavare l'indice di collaborazione.
				// infatti se devo inviare una singola fase da vigilanza devo ricavare l'indice altrimenti in
				// configurazione osservatorio deve essere valorizzato a "-1"
				int numeroFasiDaInviare = 0;
				List<?> datiW9ListaFasi = null;
				if (invioSingolaFase) {
					numeroFasiDaInviare = 1;
				} else {
					datiW9ListaFasi = this.sqlManager.getListVector(SQL_FASI_DA_ESPORTARE, new Object[] {});
					if (datiW9ListaFasi != null && datiW9ListaFasi.size() > 0) {
						numeroFasiDaInviare = datiW9ListaFasi.size();
					}
				}
				if (numeroFasiDaInviare > 0) {
					// Ci sono delle fasi da inviare, quindi ci si collega ai servizi SIMOG effettuando la login

					try {
				    simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimogPDD);
				    this.ticketSimogManager.setProxy(simogWsPddServiceStub);

					} catch (AxisFault af) {
						StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore nell'istanziare l'oggetto SimogWSOSSServiceStub. Messaggio di errore: ");
						strBuf.append(af.getMessage());
						strBuf.append(", causa errore: ");
						strBuf.append(af.getCause());
						strBuf.append(", faultType: ");
						strBuf.append(af.getFaultType());
						logger.error(strBuf.toString());
						
						simogWsPddServiceStub = null;
						throw new GestoreException(
						    "errore nell'istanziare l'oggetto SimogWSOSSServiceStub.", "errors.generico", af);
					}
					
					if (simogWsPddServiceStub != null) {
						LoginDocument loginDoc = it.avlp.simog.massload.xmlbeans.LoginDocument.Factory.newInstance();
						Login loginIn = it.avlp.simog.massload.xmlbeans.Login.Factory.newInstance();
						loginIn.setLogin(loginWsSimog);
						loginIn.setPassword(passwordWsSimog);
						loginDoc.setLogin(loginIn);
						LoginResponseDocument loginResponseDoc = null;
						try {
							loginResponseDoc = simogWsPddServiceStub.login(loginDoc);
						} catch (RemoteException re) {
							StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore nell'operazione di login ai servizi SIMOG. Messaggio di errore: ");
							strBuf.append(re.getMessage());
							strBuf.append(", causa errore: ");
							strBuf.append(re.getCause());
							logger.error(strBuf.toString());
							throw new GestoreException(
							          "errore nell'operazione di login ai servizi SIMOG.", "errors.generico", re);
						}
						if (loginResponseDoc != null) {
							LoginResponse loginResponse = loginResponseDoc.getLoginResponse();
							if (loginResponse.isSetReturn() && loginResponse.getReturn().getSuccess()) {
								responseCheckLogin = loginResponse.getReturn();
								ticket = responseCheckLogin.getTicket();
								it.avlp.simog.massload.xmlbeans.Collaborazioni arrayCollaborazioni = responseCheckLogin.getColl();
								LoaderAppaltoDocument inputLoaderAppalto = it.avlp.simog.massload.xmlbeans.loader.LoaderAppaltoDocument.Factory.newInstance();
								LoaderAppalto loaderAppalto = inputLoaderAppalto.addNewLoaderAppalto();
								loaderAppalto.setTicket(ticket);
								LoaderAppaltoWSServiceStub loaderAppaltoWSServiceStub = null;
								try {
							    loaderAppaltoWSServiceStub = new LoaderAppaltoWSServiceStub(urlLoaderAppaltoPDD);
								} catch (AxisFault af) {
									StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore nell'istanziare l'oggetto LoaderAppaltoWSServiceStub. Messaggio di errore: ");
									strBuf.append(af.getMessage());
									strBuf.append(", causa errore: ");
									strBuf.append(af.getCause());
									strBuf.append(", faultType: ");
									strBuf.append(af.getFaultType());
									logger.error(strBuf.toString());
									
									loaderAppaltoWSServiceStub = null;
									throw new GestoreException(
									          "errore nell'istanziare l'oggetto LoaderAppaltoWSServiceStub.", "errors.generico", af);
								}

								if (loaderAppaltoWSServiceStub != null) {
	                String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
	                if (StringUtils.isNotEmpty(strTimeOut)) {
	                  Integer timeOutClient = new Integer(strTimeOut);
	                  loaderAppaltoWSServiceStub._getServiceClient().getOptions().setProperty(
	                      HTTPConstants.SO_TIMEOUT, timeOutClient);
	                  loaderAppaltoWSServiceStub._getServiceClient().getOptions().setProperty(
	                      HTTPConstants.CONNECTION_TIMEOUT, timeOutClient);
	                }
	                // Se definite, si settano url, port, username e password di accesso al proxy
	                // di rete del cliente per accedere al di fuori della intranet.
	                this.setProxy(loaderAppaltoWSServiceStub);
	                
									LoaderAppaltoResponseDocument responseLoaderAppalto = null;
									Long codgara = null;
									Long codlott = null;
									Long fase_esecuzione = null;
									Long num = null;
									for (int i = 0; i < numeroFasiDaInviare; i++) {
										String indexCollaborazione = "-1";
										if (invioSingolaFase) {
											codgara = codgaraP;
											codlott = codlottoP;
											fase_esecuzione = fase_esecuzioneP;
											num = numP;
											// Ricavo l'indice di collabolazione
											indexCollaborazione = this.getIndiceCollaborazioneAccessoDiretto(arrayCollaborazioni, codgara);
										} else {
											codgara = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 0).getValue();
											codlott = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 1).getValue();
											fase_esecuzione = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 2).getValue();
											num = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 3).getValue();
										}
										
										loaderAppalto.setIndexCollaborazione(indexCollaborazione);
										LoaderAppalto.TrasferimentoDati trasferimentoDati = 
										    this.getTrasferimentoDati(codgara, codlott, fase_esecuzione, num, eliminaScheda);
										loaderAppalto.setTrasferimentoDati(trasferimentoDati);
										if (inputLoaderAppalto.validate()) {
											try {
												responseLoaderAppalto = loaderAppaltoWSServiceStub.loaderAppalto(inputLoaderAppalto);
												// inserisco record in W9XML
												Long numxml = this.insertW9XML(codgara, codlott, idFlusso, trasferimentoDati,fase_esecuzione,num);
												if (numxml > 0) {
													// aggiorno il feedback
													String CUI = this.readResponseLoaderAppalto(codgara, codlott, fase_esecuzione, num, numxml,
													    eliminaScheda, responseLoaderAppalto);
													if (StringUtils.isNotEmpty(CUI)) {
													  boolean isCancellazioneAggiudazione = eliminaScheda && 
                            (CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA == fase_esecuzione.intValue() || 
                                CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE == fase_esecuzione.intValue() ||
                                    CostantiW9.ADESIONE_ACCORDO_QUADRO == fase_esecuzione.intValue());
														this.aggiornaCODCUI(codgara, codlott, CUI, isCancellazioneAggiudazione);
													}

													// aggiorno lo stato del lotto, solo il metodo readResponseLoaderAppalto
													// diversa da 'SIMOGWS_WSSMANAGER_NULL_15'
													if (!"SIMOGWS_WSSMANAGER_NULL_15".equals(CUI)) {
													  this.aggiornaStatoFase(codgara, codlott, fase_esecuzione, num, eliminaScheda);
													}
												}
											} catch (RemoteException re) {
												// Errore lato server: si logga su file l'errore e si continua a ciclare
												StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore lato server ANAC nell'interazione con il LoaderAppalto. ");
												strBuf.append("Messaggio di errore: ");
												strBuf.append(re.getMessage());
												strBuf.append(", causa errore: ");
												strBuf.append(re.getCause());

												strBuf.append(".\nDi seguito i dettagli della gara: CodiceGara=");
												strBuf.append(codgara);
												strBuf.append(", CodiceLotto=");
												strBuf.append(codlott);
												strBuf.append(", faseEsecuzione=");
												strBuf.append(fase_esecuzione);
												if (num != null) {
													strBuf.append(", progressivoFase=");
													strBuf.append(num);
												}
												logger.error(strBuf.toString());
												throw new GestoreException(
												          "errore lato server ANAC nell'interazione con il LoaderAppalto.", "errors.generico", re);
											}
										} else {
											// l'oggetto trasferimentoDati non e' stato validato.
											// saltare il flusso, loggare qualcosa di esplicativo.
											try {
												ArrayList<XmlValidationError> arrayValidazione = this.validaMsgXML(inputLoaderAppalto);
												if (arrayValidazione != null && arrayValidazione.size() > 0) {
													StringBuffer strBuf = new StringBuffer("Il messaggio XML composto non ha superato la validazione. Di seguito i dettagli: CodiceGara=");
													strBuf.append(codgara);
													strBuf.append(", CodiceLotto=");
													strBuf.append(codlott);
													strBuf.append(", faseEsecuzione=");
													strBuf.append(fase_esecuzione);
													if (num != null) {
														strBuf.append(", progressivoFase=");
														strBuf.append(num);
													}
													
													DettaglioErrori dettErrori = DettaglioErrori.Factory.newInstance();
													strBuf.append(".\\n\\t");
													for (XmlValidationError xmlValidationError : arrayValidazione) {
														ErroreType itemError = dettErrori.addNewErrore();
														itemError.setDescrizione(xmlValidationError.getMessage());
														itemError.setTipo(Tipo.VALIDAZXML);
														strBuf.append(xmlValidationError.getMessage());
														strBuf.append("\\n\\t");
													}
													erroriDalLoaderAppalto = dettErrori.getErroreArray();
													logger.error(strBuf.toString());
												}
											} catch (IOException ioe) {
												StringBuffer strBuf = new StringBuffer("Errore nella validazione della scheda. ");
												strBuf.append("Messaggio di errore: ");
												strBuf.append(ioe.getMessage());
												strBuf.append(". Causa errore: ");
												strBuf.append(ioe.getCause());
												strBuf.append("\\n\\tDi seguito i dettagli: CodiceGara=");
												strBuf.append(codgara);
												strBuf.append(", CodiceLotto=");
												strBuf.append(codlott);
												strBuf.append(", faseEsecuzione=");
												strBuf.append(fase_esecuzione);
												if (num != null) {
													strBuf.append(", progressivoFase=");
													strBuf.append(num);
												}
												logger.error(strBuf.toString());
												throw new GestoreException(
												          "Errore nella validazione della scheda.", "errors.generico", ioe);
											}
										}
									}
								}
							} else {
								String message = "SIMOG-loaderAppalto: problema durante l'autenticazione. ";
								message += loginResponse.getReturn().getError();
								logger.error(message);
								throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'autenticazione al servizio SIMOG LoaderAppalto", "loaderAppalto.login");
							}
						//} else {
							// errore durante l'operazione di login: errore gia' gestito,
							// loggando opportuno messaggio con i dettagli del  caso
						}
					//} else {
						// errore nella creazione dello stub per login: errore gia' gestito,
						// loggando opportuno messaggio con i dettagli del caso
					}
				}
			} catch (GestoreException e) {
				throw new GestoreException(e.getMessage(), e.getCodice(), e);
			} catch (Exception e) {
				logger.error("SIMOG-loaderAppalto: ", e);
				throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'invio delle schede del lotto al servizio SIMOG LoaderAppalto", "loaderAppalto.invio");
			} finally {
				if (ticket != null) {
					// Chiusura della connessione
					try {
						ChiudiSessione chiudiSessione = it.avlp.simog.massload.xmlbeans.ChiudiSessione.Factory.newInstance();
						chiudiSessione.setTicket(ticket);
						ChiudiSessioneDocument chiudiSessioneDoc = it.avlp.simog.massload.xmlbeans.ChiudiSessioneDocument.Factory.newInstance();
						chiudiSessioneDoc.setChiudiSessione(chiudiSessione);
						
					  simogWsPddServiceStub = new SimogWSPDDServiceStub(urlWsSimogPDD);

						ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
						responseChiudiSessione = chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
						if (!responseChiudiSessione.getSuccess()) {
							logger.error("La chiusura della connessione identificata dal ticket " + ticket + " ha generato il seguente errore: " + responseChiudiSessione.getError());
						} else {
							logger.error("Chiusura della connessione identificata dal ticket " + ticket + " terminata con successo");
						}
					} catch (RemoteException re) {
						logger.error("SIMOG-loaderAppalto: ", re);
					}
				}
			}
		} else {
			String message = "SIMOG-loaderAppalto: I parametri di connessione al servizio SIMOG LoaderAppalto non sono specificati correttamente";
			logger.error(message);
			throw new GestoreException(message, "loaderAppalto.notParam");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loaderAppaltoAccessoIndiretto con credenziali simog: fine metodo");
		}
		return erroriDalLoaderAppalto;
	} */

	/**
   * Loader Appalto su PDD. 
   * 
   * @param codgaraP
   *            se valorizzato la chiamata a LoaderAppalto sara' relativa
   *            solamente a questa gara e lotto
   * @param codlottoP
   *            se valorizzato la chiamata a LoaderAppalto sara' relativa
   *            solamente a questa gara e lotto
   * @param fase_esecuzioneP
   *            se valorizzato la chiamata a LoaderAppalto sara' relativa
   *            solamente a questa fase del lotto
   * @param numP
   *            se valorizzato la chiamata a LoaderAppalto sara' relativa
   *            solamente a questa i-esima fase del lotto
   * @param idFlusso
   *            se valorizzato la chiamata a LoaderAppalto sara' relativa
   *            solamente a questo flusso
   * @param eliminaScheda
   *            se true il loader appalto viene chiamato per eliminare la 
   *            scheda
   */
	/*private ErroreType[] loaderAppaltoAccessoIndiretto(Long codgaraP, Long codlottoP, Long numxmlP, Long fase_esecuzioneP,
	     Long numP, Long idFlusso, boolean eliminaScheda) throws GestoreException {
	ErroreType[] erroriDalLoaderAppalto = null;
		//boolean result = false;
		if (logger.isDebugEnabled()) {
			logger.debug("loaderAppaltoAccessoIndiretto: inizio metodo");
		}
   
    String ticket = null;
    // Se codgaraP, codlottoP, fase_esecuzioneP sono valorizzati allora si
    // e' in configurazione Vigilanza perche' si sta inviando la singola fase
    boolean invioSingolaFase = !(codgaraP == null && codlottoP == null && fase_esecuzioneP == null);
    String urlLoaderAppaltoPDD = null;
    
    String a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_URL_LOADERAPPALTO);
    if (a != null && StringUtils.isNotEmpty(a)) {
      urlLoaderAppaltoPDD = new String(a);
    } else {
      logger.error("URL della PDD per il LoaderAppalto non definita");
    }
    
    if (StringUtils.isNotEmpty(urlLoaderAppaltoPDD)) {
    
      try {
        // Numero delle fasi da inviare. Se invioSingolaFase vale true allora si e'
        // in configurazione Vigilanza perche' si sta inviando la singola fase e
        // quindi il numero delle fasi da inviare e' pari a 1.
        // Se invece invioSingolaFase vale false si e' in configurazione Osservatorio e
        // bisogna determinare quante fasi ed esistono fasi con il campo DAEXPORT=1
        // allora il numero delle fasi da inviare e' pari a al numero di
        // record contenuti in datiW9ListaFasi.
        // I numeroFasiDaInviare viene utilizzato anche per ricavare l'indice di collaborazione.
        // infatti se devo inviare una singola fase da vigilanza devo ricavare l'indice altrimenti in
        // configurazione osservatorio deve essere valorizzato a "-1"
        int numeroFasiDaInviare = 0;
        List<?> datiW9ListaFasi = null;
        if (invioSingolaFase) {
          numeroFasiDaInviare = 1;
        } else {
          datiW9ListaFasi = this.sqlManager.getListVector(SQL_FASI_DA_ESPORTARE, new Object[] {});
          if (datiW9ListaFasi != null && datiW9ListaFasi.size() > 0) {
            numeroFasiDaInviare = datiW9ListaFasi.size();
          }
        }
        if (numeroFasiDaInviare > 0) {
          // Ci sono delle fasi da inviare, quindi ci si collega ai servizi SIMOG
          TicketSimogBean ticketSimogBean = this.ticketSimogManager.getTicket();
          if (ticketSimogBean != null) {
            if (ticketSimogBean.isSuccess()) {
              
              ticket = ticketSimogBean.getTicketSimog();
              Collaborazioni arrayCollaborazioni = ticketSimogBean.getCollaborazioni();
      
              LoaderAppaltoDocument inputLoaderAppalto = LoaderAppaltoDocument.Factory.newInstance();
              LoaderAppalto loaderAppalto = inputLoaderAppalto.addNewLoaderAppalto();
              loaderAppalto.setTicket(ticket);

              LoaderAppaltoWSServiceStub loaderAppaltoWSServiceStub = null;
              try {
                loaderAppaltoWSServiceStub = new LoaderAppaltoWSServiceStub(urlLoaderAppaltoPDD + "/loaderAppalto");
                
                String strTimeOut = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CLIENT_TIMEOUT);
                if (StringUtils.isNotEmpty(strTimeOut)) {
                  Integer timeOutClient = new Integer(strTimeOut);
                  loaderAppaltoWSServiceStub._getServiceClient().getOptions().setProperty(
                      HTTPConstants.SO_TIMEOUT, timeOutClient);
                  loaderAppaltoWSServiceStub._getServiceClient().getOptions().setProperty(
                      HTTPConstants.CONNECTION_TIMEOUT, timeOutClient);
                }
 
                this.setProxy(loaderAppaltoWSServiceStub);

                LoaderAppaltoResponseDocument responseLoaderAppalto = null;
                Long codgara = null;
                Long codlott = null;
                Long fase_esecuzione = null;
                Long num = null;
                
                if (logger.isDebugEnabled()) {
                	logger.debug("Numero fasi da inviare: " + numeroFasiDaInviare + ". Inizio del ciclo su tutte le fasi.");
                }
              
                for (int i = 0; i < numeroFasiDaInviare; i++) {
  
              	  String indexCollaborazione = "-1";
                  if (invioSingolaFase) {
                    codgara = codgaraP;
                    codlott = codlottoP;
                    fase_esecuzione = fase_esecuzioneP;
                    num = numP;
                  } else {
                    codgara = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 0).getValue();
                    codlott = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 1).getValue();
                    fase_esecuzione = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 2).getValue();
                    num = (Long) SqlManager.getValueFromVectorParam(datiW9ListaFasi.get(i), 3).getValue();
                  }
                  // Ricavo l'indice di collabolazione
                  indexCollaborazione = this.getIndiceCollaborazioneAccessoDiretto(arrayCollaborazioni, codgara);
                  loaderAppalto.setIndexCollaborazione(indexCollaborazione);
  
                  if (logger.isDebugEnabled()) {
                	  logger.debug("Preparazione dati e invio dati al loaderAppalto per CodiceGara=" + codgara + ", CodiceLotto=" + codlott + ", Fase esecuzione=" + fase_esecuzione + ", Progressivo fase=" + num + " e Elimina scheda=" + eliminaScheda);
                  }
  
                  try {
                    it.avlp.simog.massload.xmlbeans.loader.LoaderAppalto.TrasferimentoDati trasferimentoDati =
                      this.getTrasferimentoDati(codgara, codlott, fase_esecuzione, num, eliminaScheda);
                    loaderAppalto.setTrasferimentoDati(trasferimentoDati);
                    if (inputLoaderAppalto.validate()) {
                      responseLoaderAppalto = loaderAppaltoWSServiceStub.loaderAppalto(inputLoaderAppalto);
                      logger.info("Feedback da parte di SIMOG: " + responseLoaderAppalto.toString() );
                      // inserisco record in W9XML
                      Long numxml = this.insertW9XML(codgara, codlott, idFlusso, trasferimentoDati,fase_esecuzione,num);
                      if (numxml > 0) {
                        // aggiorno il feedback
                        String CUI = this.readResponseLoaderAppalto(codgara, codlott, fase_esecuzione, num, numxml,
                            eliminaScheda, responseLoaderAppalto);
                        if (CUI != null && !"SIMOGWS_WSSMANAGER_NULL_15".equals(CUI)) {
                          boolean isCancellazioneAggiudazione = eliminaScheda && 
                            (CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA == fase_esecuzione.intValue() || 
                                CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE == fase_esecuzione.intValue() ||
                                    CostantiW9.ADESIONE_ACCORDO_QUADRO == fase_esecuzione.intValue());
                          this.aggiornaCODCUI(codgara, codlott, CUI, isCancellazioneAggiudazione);
                        }
                        
                        // aggiorno lo stato del lotto, solo il metodo readResponseLoaderAppalto
                        // diversa da 'SIMOGWS_WSSMANAGER_NULL_15'
                        if (!"SIMOGWS_WSSMANAGER_NULL_15".equals(CUI)) {
                          this.aggiornaStatoFase(codgara, codlott, fase_esecuzione, num, eliminaScheda);
                        }
                      }
                    } else {
                     // l'oggetto trasferimentoDati non e' stato validato.
                     // Saltare il flusso e loggare qualcosa di esplicativo.
                     try {
                       ArrayList<XmlValidationError> arrayValidazione = this.validaMsgXML(inputLoaderAppalto);
                       if (arrayValidazione != null && arrayValidazione.size() > 0) {
                         StringBuffer strBuf = new StringBuffer("Il messaggio XML composto non ha superato la validazione. Di seguito i dettagli: CodiceGara=");
                         strBuf.append(codgara);
                         strBuf.append(", CodiceLotto=");
                         strBuf.append(codlott);
                         strBuf.append(", faseEsecuzione=");
                         strBuf.append(fase_esecuzione);
                         if (num != null) {
                           strBuf.append(", progressivoFase=");
                           strBuf.append(num);
                         }
  
                         DettaglioErrori dettErrori = DettaglioErrori.Factory.newInstance();
                         strBuf.append(".\n\t");
                         for (XmlValidationError xmlValidationError : arrayValidazione) {
                           ErroreType itemError = dettErrori.addNewErrore();
                           itemError.setDescrizione(xmlValidationError.getMessage());
                           itemError.setTipo(Tipo.VALIDAZXML);
                           strBuf.append(xmlValidationError.getMessage());
                           strBuf.append("\n\t");
                         }
                         erroriDalLoaderAppalto = dettErrori.getErroreArray();
                         logger.error(strBuf.toString());
                       }
                     } catch (IOException ioe) {
                         StringBuffer strBuf = new StringBuffer("Errore nella validazione della scheda. ");
                         strBuf.append("Di seguito i dettagli: codice gara=");
                         strBuf.append(codgara);
                         strBuf.append(", codice lotto=");
                         strBuf.append(codlott);
                         strBuf.append(", fase esecuzione=");
                         strBuf.append(fase_esecuzione);
                         if (num != null) {
                           strBuf.append(", progressivo fase=");
                           strBuf.append(num);
                         }
                         logger.error(strBuf.toString(), ioe);
                         throw new GestoreException(
    					          "Errore nella validazione della scheda.", "errors.generico", ioe);
                     } catch (Throwable t) {
                       // Errore inaspettato nella validazione del flusso che si sta per inviare a SIMO
                       StringBuffer strBuf = new StringBuffer("Errore inaspettato nella validazione della scheda. ");
                       strBuf.append("Di seguito i dettagli: codice gara=");
                       strBuf.append(codgara);
                       strBuf.append(", codice lotto=");
                       strBuf.append(codlott);
                       strBuf.append(", fase esecuzione=");
                       strBuf.append(fase_esecuzione);
                       if (num != null) {
                         strBuf.append(", progressivo fase=");
                         strBuf.append(num);
                       }
                       logger.error(strBuf.toString(), t);
                       throw new GestoreException(
                      		 "Errore inaspettato nella validazione della scheda.", "errors.generico", t);
                     }
                   }
                 } catch (RemoteException re) {
                     // Errore lato server: si logga su file l'errore e si continua a ciclare
                     StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore lato server ANAC nell'interazione con il LoaderAppalto. ");
                     strBuf.append("Di seguito i dettagli della gara: codice gara=");
                     strBuf.append(codgara);
                     strBuf.append(", codice lotto=");
                     strBuf.append(codlott);
                     strBuf.append(", fase esecuzione=");
                     strBuf.append(fase_esecuzione);
                     if (num != null) {
                       strBuf.append(", progressivo fase=");
                       strBuf.append(num);
                     }
                     logger.error(strBuf.toString(), re);
                     throw new GestoreException(
  					          "errore lato server ANAC nell'interazione con il LoaderAppalto.", "errors.generico", re);
                   /*} catch (IOException ioe) {
                   
                     // Errore lato server: si logga su file l'errore e si continua a ciclare
                     StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore lato server ANAC nell'interazione con il LoaderAppalto. ");
                     strBuf.append("Di seguito i dettagli della gara: codice gara=");
                     strBuf.append(codgara);
                     strBuf.append(", codice lotto=");
                     strBuf.append(codlott);
                     strBuf.append(", fase esecuzione=");
                     strBuf.append(fase_esecuzione);
                     if (num != null) {
                       strBuf.append(", progressivo fase=");
                       strBuf.append(num);
                     }
                     logger.error(strBuf.toString(), ioe);
                     throw new GestoreException(
  					          "errore lato server ANAC nell'interazione con il LoaderAppalto.", "errors.generico", ioe);
                     */
                   /*} catch (Throwable t) {
                     // Errore inaspettato nell'invio al loaderappalto dell'i-esimo flusso.
                     StringBuffer strBuf = new StringBuffer("SIMOG-loaderAppalto: errore inaspettato nelle "
                         + "fasi di creazione dell'XML da inviare, nella validazione e nell'invio dello stesso e "
                         + "nel salvataggio dei feedback. ");
                     strBuf.append("Di seguito i dettagli della gara: codice gara=");
                     strBuf.append(codgara);
                     strBuf.append(", codice lotto=");
                     strBuf.append(codlott);
                     strBuf.append(", fase esecuzione=");
                     strBuf.append(fase_esecuzione);
                     if (num != null) {
                       strBuf.append(", progressivo fase=");
                       strBuf.append(num);
                     }
                     logger.error(strBuf.toString(), t);
                     throw new GestoreException(
  					          "errore lato server ANAC nell'interazione con il LoaderAppalto.", "errors.generico", t);
                   }
                  } // fine ciclo su flussi da inviare
                } catch (AxisFault af) {
                  logger.error("SIMOG-loaderAppalto: errore nell'istanziare l'oggetto LoaderAppaltoWSServiceStub ", af);
                  throw new GestoreException(
                      "errore nell'istanziare l'oggetto LoaderAppaltoWSServiceStub", "errors.generico", af);
                } catch (GestoreException e) {
                  throw new GestoreException(e.getMessage(), e.getCodice(), e);
                } catch (Throwable t) {
                  logger.error("SIMOG-loaderAppalto: errore inaspettato nell'invio dei flussi al LoaderAppalto (1)", t);
                  throw new GestoreException(
                      "errore inaspettato nell'invio dei flussi al LoaderAppalto (1)", "errors.generico", t);
                } finally {
                  // Terminato l'invio dei dati a LoaderAppalto e salvataggio dei feedback
                  // Rilascio del ticket usato
                  this.ticketSimogManager.rilasciaTicket();
                  ticket = null;
               
                  logger.info("SIMOG-loaderAppalto: fine invio delle fasi");
                }
              } else {
                String message = null;
                if (StringUtils.isNotEmpty(ticketSimogBean.getMsgError())) {
                  message = "SIMOG-loaderAppalto: problema durante l'autenticazione. ".concat(
                      ticketSimogBean.getMsgError());
                } else {
               message = "SIMOG-loaderAppalto: problema durante l'autenticazione. Errore non indicato";
             }
             logger.error(message);
             throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'autenticazione al servizio SIMOG LoaderAppalto", "loaderAppalto.login");
           }
          } else {
            String message = "SIMOG-loaderAppalto: problema durante l'autenticazione. Probabilmente e' rimasta una sessione aperta";
            logger.error(message);
            throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'autenticazione al servizio SIMOG LoaderAppalto", "loaderAppalto.login");
          }
         }
       } catch (SQLException sqle) {
         logger.error("SIMOG-loaderAppalto: ", sqle);
         throw new GestoreException("SIMOG-loaderAppalto: Errore durante l'invio delle schede del lotto al servizio SIMOG LoaderAppalto", "loaderAppalto.invio");
       } catch (GestoreException e) {
         throw new GestoreException(e.getMessage(), e.getCodice(), e);
       } catch (Throwable t) {
         logger.error("SIMOG-loaderAppalto: errore inaspettato nell'invio dei flussi al LoaderAppalto (2)", t);
         throw new GestoreException(
		          "errore inaspettato nell'invio dei flussi al LoaderAppalto (2)", "errors.generico", t);
       } finally {
         if (ticket != null) {
           this.ticketSimogManager.rilasciaTicket();
         }
       }
     } else {
       String message = "SIMOG-loaderAppalto: I parametri di connessione al servizio SIMOG LoaderAppalto non sono specificati correttamente";
       logger.error(message);
       throw new GestoreException(message, "loaderAppalto.notParam");
     }
    
     if (logger.isDebugEnabled()) {
       logger.debug("loaderAppaltoAccessoIndiretto: fine metodo");
     }
     return erroriDalLoaderAppalto;
	} */
	
	
	private String getIndiceCollaborazioneAccessoDiretto(Collaborazioni arrayCollaborazioni, Long codgara)
			throws SQLException, GestoreException {

	  String indexCollaborazione = "-1";
		boolean trovato = false;
    
		if (arrayCollaborazioni != null) {
		  if (arrayCollaborazioni.getCollaborazioniArray().length > 1) {
				Collaborazione coll = null;
				// ricavo l'ente e il centro di costo dalla gara
				List<?> datiGara = this.sqlManager.getVector("select CODEIN, IDCC from W9GARA where CODGARA = ?",
				    new Object[] { codgara });

				if (SqlManager.getValueFromVectorParam(datiGara, 0).getValue() != null) {
					// ricavo i dati dell'Ente
					String codein = SqlManager.getValueFromVectorParam(datiGara, 0).toString();
					List<?> datiEnte = this.sqlManager.getVector("select CFEIN, NOMEIN from UFFINT where CODEIN = ?",
					    new Object[] { codein });
					String cfAmministrazione = null;
					if (SqlManager.getValueFromVectorParam(datiEnte, 0).getValue() != null) {
						cfAmministrazione = SqlManager.getValueFromVectorParam(datiEnte, 0).toString();
					}

					// ricavo i dati del centro di costo
					Long idcentro = SqlManager.getValueFromVectorParam(datiGara, 1).longValue();
					List<?> datiCentroC = this.sqlManager.getVector(
					    "select CODCENTRO, DENOMCENTRO from CENTRICOSTO where IDCENTRO = ?",
					    new Object[] { idcentro });
					String codcentro = null;
					if (SqlManager.getValueFromVectorParam(datiEnte, 0).getValue() != null) {
						codcentro = SqlManager.getValueFromVectorParam(datiCentroC, 0).toString();
					}
					for (int yi = 0; yi < arrayCollaborazioni.getCollaborazioniArray().length && !trovato; yi++) {
						coll = arrayCollaborazioni.getCollaborazioniArray()[yi];
						if ("66".equals(coll.getUfficioProfilo())) {
		          //Se l'utente è di tipo Osservatorio Regionale
		          indexCollaborazione = "-1";
		          trovato = true;
		        } else if (cfAmministrazione.trim().equalsIgnoreCase(coll.getAziendaCodiceFiscale().trim())
								&& codcentro.trim().equalsIgnoreCase(coll.getUfficioId())) {
							indexCollaborazione = coll.getIndex();
							trovato = true;
						}
					}
				}
  		} else {
  		  Collaborazione coll = arrayCollaborazioni.getCollaborazioniArray()[0];
  		  if ("66".equals(coll.getUfficioProfilo())) {
  			  //Se l'utente è di tipo Osservatorio Regionale
  		    indexCollaborazione = "-1";
  		  } else {
  		    indexCollaborazione = coll.getIndex();
  		  }
  		}
		}
		return indexCollaborazione;
	}

	/**
	 * Il metodo, utilizzato mediante un trigger di spring, chiama il WS
	 * loaderAppalto per ogni lotto modificato.
	 * 
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param numxml
	 *            progressivo XML
	 * @param readResponseLoaderAppalto
	 *            risposta ws loaderAppalto SIMOG
	 * 
	 * @return ritorna il codice CUI
	 */
	private String readResponseLoaderAppalto(final Long codgara, final Long codlott, final Long fase_esecuzione, final Long num,
			final Long numxml, boolean eliminaScheda, LoaderAppaltoResponseDocument responseLoaderAppalto) throws GestoreException{

		String codCUI = null;
		boolean commitTransaction = false;
		TransactionStatus status = null;
		if (logger.isDebugEnabled()) {
			logger.debug("readResponseLoaderAppalto: inizio metodo");
			logger.info("Messaggio di ritorno dal LoaderAppalto: \n".concat(responseLoaderAppalto.toString()));
		}
		String updateW9XML = "update w9xml set data_feedback = ?, data_elaborazione = ?, num_elaborate = ?,"
				+ " num_errore = ?, num_warning = ?, num_caricate = ?, feedback_analisi = ? where codgara = ? and codlott = ? and numxml = ?";
		String deleteW9XMLANOM = "delete from w9xmlanom where codgara = ? and codlott = ? and numxml = ?";
		String insertW9XMLANOM = "insert into w9xmlanom (codgara, codlott, numxml, num, codice, descrizione, livello, elemento, "
				+ " scheda, progressivo, campo_xml, id_scheda_locale, id_scheda_simog)" + " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String feedBackAnalisi = "2";
		
		try {
			if (responseLoaderAppalto.getLoaderAppaltoResponse().isSetReturn()) {
				if (responseLoaderAppalto.getLoaderAppaltoResponse().getReturn().isSetFeedBack()) {
					it.avlp.simog.massload.xmlbeans.loader.ResponseLoaderAppalto.FeedBack feedback = responseLoaderAppalto.getLoaderAppaltoResponse().getReturn().getFeedBack();
					Calendar dataElaborazione = new GregorianCalendar();
					Long numElaborate = new Long(0);
					Long numErrore = new Long(0);
					Long numWarning = new Long(0);
					Long numCaricate = new Long(0);

					if (feedback.getInfoFlusso() != null) {
						dataElaborazione = feedback.getInfoFlusso().getDATAELABORAZIONE();
						numElaborate = new Long(feedback.getInfoFlusso().getNUMELABORATE());
						numErrore = new Long(feedback.getInfoFlusso().getNUMERRORE());
						numWarning = new Long(feedback.getInfoFlusso().getNUMWARNING());
						numCaricate = new Long(feedback.getInfoFlusso().getNUMCARICATE());
						
						Long contatoreAnomalie = new Long(0);
						for (int i = 0; i < feedback.getAnomalieSchedeArray().length; i++ ) {
							for (int j = 0; j < feedback.getAnomalieSchedeArray(i).getAnomaliaArray().length; j++ ) {
								if (feedback.getAnomalieSchedeArray(i).getAnomaliaArray(j).getLIVELLO().toString().equals("ERRORE")) {
									contatoreAnomalie++;
								}
							}
						}
						if (contatoreAnomalie > 0L) {
							numErrore = new Long(1);
						} else {
							feedBackAnalisi = "1";
						}
						
					} else {
						// Se infoFlusso non  e' valorizzato, allora si e' verificato un errore
						// legato all'utente che ha fatto accesso ai servizi SIMOG e all'indice
						// di collaborazione usato nella richiesta per il Loader Appalto.
						numErrore = new Long(1);
					}
					
					status = this.sqlManager.startTransaction();
					this.sqlManager.update(updateW9XML, new Object[] { new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()), 
					    new Timestamp(dataElaborazione.getTimeInMillis()), numElaborate, numErrore, numWarning, numCaricate,
					    feedBackAnalisi, codgara, codlott, numxml });

					// Cancellazione della tabella W9XMLANOM
					this.sqlManager.update(deleteW9XMLANOM, new Object[] { codgara, codlott, numxml });

					// Lettura dell'elemento AnomalieSchede
					it.avlp.simog.massload.xmlbeans.loader.ResponseLoaderAppalto.FeedBack.AnomalieSchede anomalieSchede[] = feedback.getAnomalieSchedeArray();
					int index = 1;
					for (int j = 0; j < anomalieSchede.length; j++) {
						if (StringUtils.isEmpty(codCUI) && anomalieSchede[j].isSetCUI() && StringUtils.isNotEmpty(anomalieSchede[j].getCUI())) {
							codCUI = anomalieSchede[j].getCUI();
						}
						
						AnomaliaType anomalie[] = anomalieSchede[j].getAnomaliaArray();

						String id_scheda_locale = null;
						String id_scheda_simog = null;
						String scheda = null;

						if (anomalie.length > 0) {
							// Ciclo su tutte le anomalie presenti nell'oggetto anomalieSchede
							for (int i = 0; i < anomalie.length; i++) {
								id_scheda_locale = null;
								id_scheda_simog = null;
								scheda = null;

								AnomaliaType anomalia = anomalie[i];

								String codice = null;
								if (anomalia.isSetCODICE()) {
									codice = anomalia.getCODICE();
								}
								String descrizione = anomalia.getDESCRIZIONE();
								String livello = anomalia.getLIVELLO().toString();
								Long elemento = new Long(anomalia.getELEMENTO());
								Long progressivo = null;
								if (anomalia.isSetPROGRESSIVO()) {
									progressivo = new Long(anomalia.getPROGRESSIVO());
								}
								String campo_xml = null;
								if (anomalia.isSetCAMPOXML()) {
									campo_xml = anomalia.getCAMPOXML();
								}
								if (anomalia.isSetIDSCHEDASIMOG()) {
									id_scheda_simog = anomalia.getIDSCHEDASIMOG();
								}
								if (anomalia.isSetIDSCHEDALOCALE()) {
									id_scheda_locale = anomalia.getIDSCHEDALOCALE();
								}
								if (anomalia.isSetSCHEDA()) {
								  scheda = anomalia.getSCHEDA().toString();
								}

								if (!StringUtils.equals("SIMOGWS_WSSMANAGER_NULL_15", codice)) {
									this.sqlManager.update(insertW9XMLANOM, new Object[] {
										codgara, codlott, numxml, new Long(index), codice, StringUtils.substring(descrizione, 0, 1950),
											livello, elemento, scheda, progressivo, campo_xml, id_scheda_locale, id_scheda_simog });
								}

								//this.aggiornaIdSchedaLocaleIdSchedaSimog(codgara, codlott, fase_esecuzione, num, id_scheda_locale, id_scheda_simog, scheda);

								index++;
							}
						}

						id_scheda_locale = null;
						id_scheda_simog = null;
						scheda = null;
						
						if (!eliminaScheda && anomalieSchede[j].getIdSchedaArray() != null && 
							anomalieSchede[j].getIdSchedaArray().length > 0) {
							RecIdSchedaInsType[] idSchedaArray = feedback.getAnomalieSchedeArray(j).getIdSchedaArray();
							RecIdSchedaInsType idSchedaIns = idSchedaArray[0];
							if (idSchedaIns != null) {
								scheda = idSchedaIns.getSCHEDA().toString();
								id_scheda_simog = idSchedaIns.getIDSCHEDASIMOG();
								if (idSchedaIns.isSetIDSCHEDALOCALE()) {
									id_scheda_locale = idSchedaIns.getIDSCHEDALOCALE();
								}
							}

							this.aggiornaIdSchedaLocaleIdSchedaSimog(codgara, codlott, fase_esecuzione, num, 
									id_scheda_locale, id_scheda_simog, scheda);

						} else {
							if (numErrore == 0 && eliminaScheda) {
								RecIdSchedaInsType[] arrayRecIdSchedaIns = anomalieSchede[j].getIdSchedaArray();
								if (arrayRecIdSchedaIns.length > 0) {
									scheda = null;
									id_scheda_locale = null;
									id_scheda_simog = null;

									RecIdSchedaInsType idSchedaIns = null;
									int u = 0;
									do {
										if (arrayRecIdSchedaIns[u] != null) {
											idSchedaIns = arrayRecIdSchedaIns[u];
										} else {
											u++;
										}
									} while (u < arrayRecIdSchedaIns.length && idSchedaIns == null);

									if (idSchedaIns != null) {
										scheda = idSchedaIns.getSCHEDA().toString();
 
										id_scheda_simog = idSchedaIns.getIDSCHEDASIMOG();
										if (idSchedaIns.isSetIDSCHEDALOCALE()) {
											id_scheda_locale = idSchedaIns.getIDSCHEDALOCALE();
										}
									}

									this.sqlManager.update(insertW9XMLANOM, new Object[] {
											codgara, codlott, numxml, new Long(index), "ELIMINAZIONE", 
											"Cancellazione fase avvenuta con successo", "AVVISO", null,
											scheda, null, null, id_scheda_locale, id_scheda_simog });

									this.aggiornaIdSchedaLocaleIdSchedaSimog(codgara, codlott, fase_esecuzione, num, 
											id_scheda_locale, null, scheda);
								}
							}
							index++;
						}
					}
					commitTransaction = true;
				}
			} else {
				// TODO - gestire il caso in cui l'oggetto LoaderAppaltoResponse non 
				// sia settato l'oggetto return
			}
		} catch (Exception ex) {
			logger.error("SIMOG-loaderAppalto(readResponseLoaderAppalto): ", ex);
			throw new GestoreException("SIMOG-loaderAppalto: (readResponseLoaderAppalto)", "loaderAppalto.invio");
		} finally {
			if (status != null) {
				try {
					if (commitTransaction) {
						this.sqlManager.commitTransaction(status);
					} else {
						this.sqlManager.rollbackTransaction(status);
					}
				} catch (SQLException e) {
					logger.error("SIMOG-loaderAppalto(readResponseLoaderAppalto): ", e);
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("readResponseLoaderAppalto: fine metodo");
		}
		return codCUI;
	}

  /**
   * Aggiornamento dei campi ID_SCHEDA_LOCALE e ID_SCHEDA_SIMOG di W9LOTT
   * se scheda='DATI_COMUNI' altrimenti di W9FASI 
   * 
   * @param codgara
   * @param codlott
   * @param fase_esecuzione
   * @param num
   * @param id_scheda_locale
   * @param id_scheda_simog
   * @param scheda
   * @throws SQLException
   */
  private void aggiornaIdSchedaLocaleIdSchedaSimog(final Long codgara, final Long codlott, final Long fase_esecuzione, final Long num,
      String id_scheda_locale, String id_scheda_simog, String scheda) throws SQLException {
    
  	String updateW9FASI = null;
    String updateW9LOTT = null;
    List<Object> listaParametriW9LOTT = new ArrayList<Object>();
    List<Object> listaParametriW9FASI = new ArrayList<Object>();
    
    if ("DATI_COMUNI".equals(scheda)) {
    	updateW9LOTT = "update W9LOTT set ID_SCHEDA_LOCALE=VAL_ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG=VAL_ID_SCHEDA_SIMOG " +
            " WHERE CODGARA=? and CODLOTT=?";
      if (StringUtils.isEmpty(id_scheda_locale)) {
      	updateW9LOTT = StringUtils.replace(updateW9LOTT, " ID_SCHEDA_LOCALE=VAL_ID_SCHEDA_LOCALE,", "");
      } else {
      	updateW9LOTT = StringUtils.replace(updateW9LOTT, "VAL_ID_SCHEDA_LOCALE", "?");
      	listaParametriW9LOTT.add(id_scheda_locale);
      }
   
      if (StringUtils.isEmpty(id_scheda_simog)) {
      	updateW9LOTT = StringUtils.replace(updateW9LOTT, "VAL_ID_SCHEDA_SIMOG", "null");
      } else {
      	updateW9LOTT = StringUtils.replace(updateW9LOTT, "VAL_ID_SCHEDA_SIMOG", "?");
      	listaParametriW9LOTT.add(id_scheda_simog);
      }
      listaParametriW9LOTT.add(codgara);
      listaParametriW9LOTT.add(codlott);
    	this.sqlManager.update(updateW9LOTT, listaParametriW9LOTT.toArray() );
    } else {
   		if (num != null) {
   			updateW9FASI = "update W9FASI set ID_SCHEDA_LOCALE=VAL_ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG=VAL_ID_SCHEDA_SIMOG " +
   					" WHERE CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?";
   		} else {
   			updateW9FASI = "update W9FASI set ID_SCHEDA_LOCALE=VAL_ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG=VAL_ID_SCHEDA_SIMOG " +
   					" WHERE CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=?";
   		}
 
  	  if (StringUtils.isEmpty(id_scheda_locale)) {
	    	updateW9FASI = StringUtils.replace(updateW9FASI, " ID_SCHEDA_LOCALE=VAL_ID_SCHEDA_LOCALE,", "");
    	} else {
    		updateW9FASI = StringUtils.replace(updateW9FASI, "VAL_ID_SCHEDA_LOCALE", "?");
    		listaParametriW9FASI.add(id_scheda_locale);
    	}
 
    	if (StringUtils.isEmpty(id_scheda_simog)) {
    		updateW9FASI = StringUtils.replace(updateW9FASI, "VAL_ID_SCHEDA_SIMOG", "null");
    	} else {
    		updateW9FASI = StringUtils.replace(updateW9FASI, "VAL_ID_SCHEDA_SIMOG", "?");
    		listaParametriW9FASI.add(id_scheda_simog);
    	}
  	  listaParametriW9FASI.add(codgara);
	    listaParametriW9FASI.add(codlott);
   		listaParametriW9FASI.add(fase_esecuzione);
   		if (num != null) {
   			listaParametriW9FASI.add(num);
    	}
   	
    	this.sqlManager.update(updateW9FASI, listaParametriW9FASI.toArray());
    }
  }

	/**
	 * Il metodo, inserisce nella tabella W9XML la scheda inviata a
	 * loaderAppalto
	 * 
	 * @param codgara
	 *            codice gara
	 * @param codlott
	 *            codice lotto
	 * @param idFlusso
	 *            da associare a W9XML
	 * @param trasferimentoDati
	 *            dati trasferiti
	 * 
	 * @return num_xml -1 se l'inserimento non va a buon fine, >0 altrimenti
	 * 
	 */
	// overloading del metodo, nel primo invio non compare il numxml
	public Long insertW9XML(final Long codgara, final Long codlott, final Long idFlusso, LoaderAppalto.TrasferimentoDati trasferimentoDati, final Long fase_esecuzione, final Long num) throws GestoreException{
		return insertW9XML(codgara, codlott, null, idFlusso, trasferimentoDati, fase_esecuzione, num);
	}

	public Long insertW9XML(final Long codgara, final Long codlott, final Long numxml, final Long idFlusso, LoaderAppalto.TrasferimentoDati trasferimentoDati, final Long fase_esecuzione,
			final Long num) throws GestoreException{

		boolean commitTransaction = false;
		TransactionStatus status = null;

		Long num_xml = new Long(-1);
		if (logger.isDebugEnabled()) {
			logger.debug("insertW9XML: inizio metodo");
		}

		String selectW9XML = "select max(NUMXML) from W9XML where CODGARA=? and CODLOTT=?";
		String insertW9XML = "insert into w9xml(CODGARA,CODLOTT,NUMXML,DATA_EXPORT,DATA_INVIO,XML,IDFLUSSO,FASE_ESECUZIONE,NUM) values (?,?,?,?,?,?,?,?,?)";

		// queste due istruzioni di if valgono per retrocompatibilita'
		// in quanto dall'aggiornamento e l'inserimento di
		// fase_esecuzione e num, questi due valori andranno sempre popolati
		String stringSqlFaseEsecuzione = "";
		if (fase_esecuzione != null) {
			stringSqlFaseEsecuzione = " and FASE_ESECUZIONE=" + fase_esecuzione;
		} else {
			stringSqlFaseEsecuzione = " and FASE_ESECUZIONE is null ";
		}

		String stringSqlNum = "";
		if (num != null) {
			stringSqlNum = " and NUM = " + num;
		} else {
			stringSqlNum = " and NUM is null ";
		}

		String updateW9XML = "update W9XML set DATA_EXPORT=?,DATA_INVIO=?,XML=?,IDFLUSSO=? where CODGARA=? and CODLOTT=? and NUMXML=? " + stringSqlFaseEsecuzione + stringSqlNum;

		String updateValoreXml = "UPDATE W9FLUSSI SET XML =? WHERE IDFLUSSO =?";

		try {

			status = this.sqlManager.startTransaction();
			if (idFlusso != null) {
				// Aggiorno la W9FLUSSI solo nella configurazione Vigilanza, ovvero quando
				// associata la chiamata del LoaderAppalto viene creato anche un flusso
				this.sqlManager.update(updateValoreXml, new Object[] { trasferimentoDati.toString(), idFlusso });
			}

			// test se il valore del w9xml è già presente sono nel caso di un update/reinvio
			if (numxml != null) {
				num_xml = numxml;
				this.sqlManager.update(updateW9XML, new Object[] { new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
						new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()), trasferimentoDati.toString(), idFlusso,
						codgara, codlott, numxml });
			} else {
				// altrimenti mi trovo nel caso di un primo invio ed il numerico chiave devo calcolarlo
				Long conteggio = (Long) sqlManager.getObject(selectW9XML, new Object[] { codgara, codlott });
				if (conteggio != null && conteggio.longValue() > 0) {
					conteggio++;
				} else {
					conteggio = new Long(1);
				}
				num_xml = conteggio;
				this.sqlManager.update(insertW9XML, new Object[] { codgara, codlott, num_xml, 
						new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()), new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()),
						trasferimentoDati.toString(), idFlusso, fase_esecuzione, num });
			}

			commitTransaction = true;

		} catch (Exception ex) {
			logger.error("SIMOG-loaderAppalto(insertW9XML): ", ex);
			throw new GestoreException("SIMOG-loaderAppalto: (insertW9XML)", "loaderAppalto.invio");
		} finally {
			if (status != null) {
				try {
					if (commitTransaction) {
						this.sqlManager.commitTransaction(status);
					} else {
						this.sqlManager.rollbackTransaction(status);
					}
				} catch (SQLException e) {
					logger.error("SIMOG-loaderAppalto(insertW9XML): ", e);
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("insertW9XML: fine metodo");
		}
		return num_xml;
	}

	
	/**
	 * Esportazione di tutti i lotti (contratti). Il metodo, utilizzato mediante
	 * un trigger di spring, produce a seconda della configurazione, un file
	 * distinto per ogni lotto (contratto), oppure una chiamata a WS
	 * loaderAppalto
	 * 
	 * @throws GestoreException
	 *             GestoreException
	 */
	public void generaSchedeSimog() throws GestoreException {
		String urlWSLoader = null;
		String a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_URL_LOADERAPPALTO);
		if (a != null && StringUtils.isNotEmpty(a)) {
			urlWSLoader = new String(a);
		}
		if (urlWSLoader != null && !urlWSLoader.equals("")) {
			loaderAppalto(null, null, null, null, null, null, null, false);
		} else {
			exportXML();
		}
	}

	/**
	 * Esportazione di tutti i lotti (contratti). Il metodo, utilizzato mediante
	 * un trigger di spring, produce un file distinto per ogni lotto (contratto)
	 * 
	 * @throws GestoreException
	 *             GestoreException
	 */
	public void exportXML() throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("exportXML: inizio metodo");
		}
		String datapath = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_XML_DATAPATH);
		if (StringUtils.isEmpty(datapath)) {
			throw new GestoreException("Non e' definita la cartella in cui salvare i file XML SIMOG prodotti", "exportxml.nodatapath", null);
		}
		if (!datapath.endsWith(File.separator)) {
			datapath = datapath.concat(File.separator);
		}
		String selectW9ListaLotti = this.queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9LOTT_LISTA");
		try {
			List<?> datiW9ListaLotti = this.sqlManager.getListVector(selectW9ListaLotti, new Object[0]);
			GregorianCalendar dataOggi = new GregorianCalendar();
			String dataOdierna = UtilityDate.convertiData(dataOggi.getTime(), UtilityDate.FORMATO_AAAAMMGG);
			if (datiW9ListaLotti != null && datiW9ListaLotti.size() > 0) {

				// Directory di destinazione dei file generati
				File destDir = new File(datapath);
				// File tempDestDir = new File(datapath + "temp" +
				// dataOraAttuale + File.separator);
				// tempDestDir.mkdir();

				// File destErrorDir = new File(datapath + "temp" +
				// dataOraAttuale + File.separator + "errori" + File.separator);
				File destErrorDir = new File(datapath + "errori" + File.separator);

				// String strZipRegEx = "ExportSIMOG-[0-9]{14}.zip";
				// File[] fileZipPrecedenti = destDir.listFiles(new
				// DirFilter(strZipRegEx));

				// File fileZipEsistente = null;

				/*
				 * if (fileZipPrecedenti != null && fileZipPrecedenti.length >
				 * 0) { List<String> listaFilePrecedenti = new
				 * ArrayList<String>(); for (int ui = 0; ui <
				 * fileZipPrecedenti.length; ui++) {
				 * listaFilePrecedenti.add(fileZipPrecedenti
				 * [ui].getAbsolutePath()); }
				 * 
				 * // Si lavora sull'ultimo file zip della lista perche' quello
				 * piu' recente fileZipEsistente = new
				 * File(Collections.max(listaFilePrecedenti));
				 * 
				 * // Unzip del ultimo archivio (in senso temporale) esistente
				 * if (fileZipEsistente.exists()) {
				 * this.unzipFileIntoDirectory(new ZipFile(fileZipEsistente),
				 * tempDestDir); } }
				 */

				// Se non esiste la sotto cartella 'errori', la si crea
				if (!destErrorDir.exists()) {
					destErrorDir.mkdir();
				}

				// Se ci sono dati da esportare, allora: si cerca un file zip
				// con nome del tipo ExportSIMOG-AAAAMMGGHHMMSS.zip (zip originale);
				// se non esiste, si crea la cartella tempAAAAMMGGHHMMSS;
				// tale cartella diventa la cartella in cui la funzionalita' di
				// export deposita i file XML validati e non;
				// (cioe' si creano sia i file XML validati, sia la cartella
				// errori, con al suo interno i file XML ed i relativi file di error);
				// se esiste, lo si estrae nella cartella tempAAAAMMGGHHMMSS;
				// tale cartella diventa la cartella in cui la funzionalita' di
				// export deposita i file XML validati e non;
				// (cioe' si creano/si sovrascrivono sia i file XML validati,
				// sia la cartella errori, con al suo interno i file XML ed i
				// relativi file di error);

				// terminato l'export nella cartella tempAAAAMMGGHHMMSS, si
				// comprimono tutti i file in essa presenti nel file ExportSIMOG-AAAAMMGGHHMMSS.zip;
				// si copia lo zip nella cartella di destinazione (vedi global.properties);
				// si rimuovono la cartella tempAAAAMMGGHHMMSS e lo zip originale (se presente);

				for (int i = 0; i < datiW9ListaLotti.size(); i++) {
					Long codgara = (Long) SqlManager.getValueFromVectorParam(datiW9ListaLotti.get(i), 0).getValue();
					Long codlott = (Long) SqlManager.getValueFromVectorParam(datiW9ListaLotti.get(i), 1).getValue();
					String trasferimentoDatiXML = null;
					String trasferimentoDatiError = null;
					Object trasferimento[] = exportXML(codgara, codlott);
					trasferimentoDatiXML = (String) trasferimento[0];
					trasferimentoDatiError = (String) trasferimento[1];
					// Prima di salvare i file di export si controlla se nella direcotry di destinazione
					// non esista l'XML (e l'eventuale file error) dello stesso lotto di gara creato in
					// precedenza. Se presente l'XML (e l'eventuale file error), allora si procede con
					// la cancellazione.

					// Regular expression per cercare i file con nome costituito da:
					// 8 numeri (che rappresentano AAAAMMGG) + _ + codice gara + _ + codice lotto
					// e con estensione .xml o .error
					String strRegEx = (new StringBuilder()).append("[0-9]{8}_").append(codgara).append("_").append(codlott).append(".(xml|error)").toString();
					// La regola generale potrebbe essere:
					// ^([0-9]{8}_[\w]{10}_[0-9]{1,3}).(xml|error)

					// Array di File con nome che soddisfa la regular expression
					// File[] fileEsistenti = tempDestDir.listFiles(new
					// DirFilter(strRegEx));
					File fileEsistenti[] = destDir.listFiles(new DirFilter(strRegEx));
					if (fileEsistenti != null && fileEsistenti.length > 0) {
						for (int j = 0; j < fileEsistenti.length; j++) {
							if (fileEsistenti[j] != null) {
								fileEsistenti[j].delete();
							}
						}
					}

					// Array di File nella cartella error con nome che soddisfa
					// la regular expression
					File fileErrorEsistenti[] = destErrorDir.listFiles(new DirFilter(strRegEx));
					if (fileErrorEsistenti != null && fileErrorEsistenti.length > 0) {
						for (int j = 0; j < fileErrorEsistenti.length; j++) {
							if (fileErrorEsistenti[j] != null) {
								fileErrorEsistenti[j].delete();
							}
						}
					}
					if (StringUtils.isNotEmpty(trasferimentoDatiXML)) {
						File trasferimentoDatiXMLFile = null;
						File trasferimentoDatiErrorFile = null;
						if (StringUtils.isEmpty(trasferimentoDatiError)) {
							// Salvataggio del contenuto XML su un file con
							// estensione .xml nella cartella principale
							// trasferimentoDatiXMLFile = new
							// File(tempDestDir.getAbsolutePath() +
							// File.separator
							trasferimentoDatiXMLFile = new File(destDir.getAbsolutePath() + File.separator + dataOdierna + "_" + codgara.toString() + "_" + codlott.toString() + ".xml");
						} else {
							// Salvataggio del contenuto XML su un file con
							// estensione .xml nella sotto cartella error
							trasferimentoDatiXMLFile = new File(destErrorDir.getAbsolutePath() + File.separator + dataOdierna + "_" + codgara.toString() + "_" + codlott.toString() + ".xml");

							trasferimentoDatiErrorFile = new File(destErrorDir.getAbsoluteFile() + File.separator + dataOdierna + "_" + codgara.toString() + "_" + codlott.toString() + ".error");
						}
						FileOutputStream outputStreamTrasferimentoDati = new FileOutputStream(trasferimentoDatiXMLFile);
						outputStreamTrasferimentoDati.write(trasferimentoDatiXML.getBytes());
						outputStreamTrasferimentoDati.close();
						if (StringUtils.isNotEmpty(trasferimentoDatiError)) {
							// Salvataggio degli eventuali errori su un file con
							// estensione .error nella sotto cartella error
							FileOutputStream outputStreamTrasferimentoDatiError = new FileOutputStream(trasferimentoDatiErrorFile);
							outputStreamTrasferimentoDatiError.write(trasferimentoDatiError.getBytes());
							outputStreamTrasferimentoDatiError.close();
						}
						if (trasferimentoDatiError == null) {
							this.aggiornaStatoEsportazione(codgara, codlott);
							this.aggiornaCODCUI(codgara, codlott, null, false);
						}
					}
				}

			}
		} catch (SQLException e) {
			throw new GestoreException("Errore generico di interazione con la base dati durante l'esportazione dei dati XML in formato SIMOG", "exportxml.sqlerror", e);
		} catch (IOException e) {
			throw new GestoreException("Errore generico I/O durante l'esportazione dei dati XML in formato SIMOG", "exportxml.ioerror", e);
		} catch (Throwable t) {
			logger.error("Errore inaspettato nell'operazione di esportazione degli XMl per SIMOG", t);
			throw new GestoreException("", "exportxml.genericerror");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("exportXML: fine metodo");
		}
	}

	/**
	 * Aggiornamento dello stato di esportazione per la gara ed il lotto
	 * indicati.
	 * 
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void aggiornaStatoEsportazione(Long codgara, Long codlott) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaStatoEsportazione: inizio metodo");
		}
		String updateW9LOTT = queryExtractor.getQuery(SqlManager.getTipoDB(), "selectObject", "W9LOTT_AGGIORNADAEXPORT");

		try {
			TransactionStatus status = this.sqlManager.startTransaction();
			this.sqlManager.update(updateW9LOTT, new Object[] { codgara, codlott });
			this.sqlManager.commitTransaction(status);
		} catch (SQLException e) {
			throw new GestoreException("Errore nell'aggiornamento dello stato di esportazione del lotto", "exportxml.sqlerror", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaStatoEsportazione: fine metodo");
		}
	}

	/**
	 * Aggiornamento dello stato di esportazione per la gara ed il lotto
	 * indicati.
	 * 
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param fase_esecuzione
	 *            fase di esecuzione
	 * @param num
	 *            progressivo fase
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void aggiornaStatoFase(Long codgara, Long codlott, Long fase_esecuzione, Long num) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaStatoFase: inizio metodo");
		}
		String updateW9FASI = "update W9FASI set DAEXPORT='2' where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?";
		try {
			TransactionStatus status = sqlManager.startTransaction();
			this.sqlManager.update(updateW9FASI, new Object[] { codgara, codlott, fase_esecuzione, num });
			this.sqlManager.commitTransaction(status);
		} catch (SQLException e) {
			throw new GestoreException("Errore nell'aggiornamento dello stato di esportazione della fase", "exportxml.sqlerror", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaStatoFase: fine metodo");
		}
	}

	/**
	 * Aggiornamento del codice CUI (W9APPA.CODCUI) con il valore del CIG + '-1'
	 * dopo aver effettuato con successo l'esportazione del lotto per SIMOG.
	 * 
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param CUI
	 *            se presente valorizzo il CODCUI del lotto con questo valore
	 * @param isCancellazioneAggiudicazione 
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void aggiornaCODCUI(Long codgara, Long codlott, String CUI, boolean isCancellazioneAggiudicazione) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaCODCUI: inizio metodo");
		}

		try {
			TransactionStatus status = sqlManager.startTransaction();
			if (isCancellazioneAggiudicazione) {
				String CODCUI = null;
				Long ultimaAggiudicazione = (Long) sqlManager.getObject("select MAX(NUM_APPA) from W9APPA where CODGARA=? and CODLOTT=?",
			              new Object[]{ codgara, codlott});
				if (ultimaAggiudicazione>1) {
					CODCUI = (String) sqlManager.getObject("select CODCUI from W9APPA where CODGARA=? and CODLOTT=? and NUM_APPA=?",
				              new Object[] { codgara, codlott, ultimaAggiudicazione-1 } );
				} 
				this.sqlManager.update("update W9LOTT set CODCUI=? where CODGARA=? AND CODLOTT=?", new Object[] { CODCUI, codgara, codlott });
				this.sqlManager.update("update W9APPA set CODCUI=? where CODGARA=? AND CODLOTT=? AND NUM_APPA=?", 
					    new Object[] { CODCUI, codgara, codlott, ultimaAggiudicazione });
			} else if (StringUtils.isNotEmpty(CUI)) {
				Long ultimaAggiudicazione = (Long) sqlManager.getObject("select MAX(NUM_APPA) from W9APPA where CODGARA=? and CODLOTT=?",
			              new Object[]{ codgara, codlott});
				this.sqlManager.update("update W9LOTT set CODCUI=? where CODGARA=? AND CODLOTT=?", new Object[] { CUI, codgara, codlott });
				this.sqlManager.update("update W9APPA set CODCUI=? where CODGARA=? AND CODLOTT=? AND NUM_APPA=?", 
					    new Object[] { CUI, codgara, codlott, ultimaAggiudicazione });
			} else {
			  this.sqlManager.update("update W9LOTT set CODCUI=null where CODGARA=? AND CODLOTT=?", 
            new Object[] { codgara, codlott });
			}

			this.sqlManager.commitTransaction(status);
		} catch (SQLException e) {
			throw new GestoreException("Errore nell'aggiornamento dei campi W9LOTT.CODCUI e W9APPA.CODCUI", "exportxml.sqlerror", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaCODCUI: fine metodo");
		}
	}

	/**
	 * Conversione di un importo da Double a Stringa.
	 * 
	 * @param importo
	 *            Importo
	 * @return Ritorna l'importo convertito in stringa
	 */
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

	/**
	 * Metodo principale per l'esportazione.
	 * 
	 * @param codgara
	 *            codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param fase_esecuzione
	 *            fase esecuzione
	 * @param num
	 *            progressivo della fase
	 * @param eliminaScheda
	 * 			indica se è stata fatta richiesta di eliminazione della scheda
	 * @return Ritorna
	 * @throws GestoreException
	 *             GestoreException
	 */
	private LoaderAppalto.TrasferimentoDati getTrasferimentoDati(
	    final Long codgara, final Long codlott, final Long fase_esecuzione, final Long num, final boolean eliminaScheda) {

		if (logger.isDebugEnabled()) {
			logger.debug("getTrasferimentoDati: inizio metodo");
		}
		it.avlp.simog.massload.xmlbeans.loader.LoaderAppalto.TrasferimentoDati trasferimentoDati =
		  it.avlp.simog.massload.xmlbeans.loader.LoaderAppalto.TrasferimentoDati.Factory.newInstance();

		// Selezione dei lotti da esportare
		try {
			// Creazione dell'elemento principale trasferimento dati
			// Data di creazione del flusso e numero lotti (sempre 1)
			TrasferimentoType trasferimento = trasferimentoDati.addNewInfoTrasferimento();
			trasferimento.setDATACREAZIONEFLUSSO(UtilityDateExtension.convertTodayToCalendar());
			trasferimento.setNUMSCHEDE(1);
			if (eliminaScheda) {
				RecIdSchedaElimType schedeEliminate = trasferimentoDati.addNewSchedeEliminate();
				
				List<?> datiCigCui = this.sqlManager.getVector("select CIG, CODCUI from W9LOTT where CODGARA = ? and CODLOTT = ?", new Object[] { codgara, codlott });

				if (datiCigCui != null && datiCigCui.size() > 0) {
					if (SqlManager.getValueFromVectorParam(datiCigCui, 0).getValue() != null) {
						schedeEliminate.setCIG(SqlManager.getValueFromVectorParam(datiCigCui, 0).toString());
					}

					if (SqlManager.getValueFromVectorParam(datiCigCui, 1).getValue() != null) {
						schedeEliminate.setCUI(SqlManager.getValueFromVectorParam(datiCigCui, 1).toString());
					} else {
						schedeEliminate.setCUI("");
					}

					schedeEliminate.setSCHEDA(TipiSchedeType.Enum.forString(CostantiSimog.TipiSchede.get(fase_esecuzione)));
					
					List<?> datiSchedeId = this.sqlManager.getVector("select ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG from W9FASI where CODGARA = ? and CODLOTT = ? and FASE_ESECUZIONE=? and NUM=?", new Object[] { codgara, codlott, fase_esecuzione, num });

					if (datiSchedeId != null && datiSchedeId.size() > 0) {
						if (SqlManager.getValueFromVectorParam(datiSchedeId, 0).getValue() != null) {
							schedeEliminate.setIDSCHEDALOCALE(SqlManager.getValueFromVectorParam(datiSchedeId, 0).toString());
						}
						if (SqlManager.getValueFromVectorParam(datiSchedeId, 1).getValue() != null) {
							schedeEliminate.setIDSCHEDASIMOG(SqlManager.getValueFromVectorParam(datiSchedeId, 1).toString());
						}
					}
				}
			} else {
				DatiAggiudicazioneType datiAggiudicazione = trasferimentoDati.addNewSchede();
				this.setSchede(datiAggiudicazione, codgara, codlott, fase_esecuzione, num);

				// Se il lotto e' stato aggiudicato, allora  
				if ("1".equals(datiAggiudicazione.getDatiComuni().getESITOPROCEDURA()) || "5".equals(datiAggiudicazione.getDatiComuni().getESITOPROCEDURA())) {
					// Creazione dell'elemento Responsabili (e' l'archivio di tutte le
					// anagrafiche dei responsabili/tecnici)
					ResponsabiliType responsabili = trasferimentoDati.addNewResponsabili();
					this.setAnagraficaResponsabili(responsabili, codgara, codlott);
  
					// Creazione dell'elemento Aggiudicatari
					if (fase_esecuzione.equals(new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA))
					    || 
					    fase_esecuzione.equals(new Long(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE))
					    || 
					    fase_esecuzione.equals(new Long(CostantiW9.ADESIONE_ACCORDO_QUADRO))) {
						AggiudicatariType aggiudicatari = trasferimentoDati.addNewAggiudicatari();
						this.setAnagraficaAggiudicatari(aggiudicatari, codgara, codlott);	
					//} else if (fase_esecuzione.equals(new Long(CostantiW9.SUBAPPALTO))) {
					  
					}
				}
			}
		} catch (GestoreException ioe) {
			logger.error(ioe);
		} catch (SQLException e) {
			logger.error(e);
		}

		if (logger.isDebugEnabled()) {
		  logger.info("Messaggio inviato al LoaderAppalto:\n".concat(trasferimentoDati.toString()));
			logger.debug("getTrasferimentoDati: fine metodo");
		}

		return trasferimentoDati;
	}

	/**
	 * Recupera le credenziali di accesso al servizio LoaderAppalto di SIMOG
	 * 
	 * @param syscon
	 * @return
	 * @throws GestoreException
	 */
	public HashMap<String, String> recuperaSIMOGLAUserPass(Long syscon) throws GestoreException {

		HashMap<String, String> hMapSIMOGLAUserPass = new HashMap<String, String>();

		try {
			List<?> datiSIMOGLAUSRSYS = this.sqlManager.getVector("select simoguser, simogpass from W9LOADER_APPALTO_USR where syscon = ?", new Object[] { syscon });
			if (datiSIMOGLAUSRSYS != null && datiSIMOGLAUSRSYS.size() > 0) {

				String simoguser = (String) SqlManager.getValueFromVectorParam(datiSIMOGLAUSRSYS, 0).getValue();
				hMapSIMOGLAUserPass.put("simoguser", simoguser);

				String simogpass = (String) SqlManager.getValueFromVectorParam(datiSIMOGLAUSRSYS, 1).getValue();

				// Decodifica della password
				ICriptazioneByte cupwspassICriptazioneByte = null;
				cupwspassICriptazioneByte = FactoryCriptazioneByte.getInstance(ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI), simogpass.getBytes(),
						ICriptazioneByte.FORMATO_DATO_CIFRATO);
				String simogpassDecriptata = new String(cupwspassICriptazioneByte.getDatoNonCifrato());

				hMapSIMOGLAUserPass.put("simogpass", simogpassDecriptata);

			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione delle credenziali di accesso al servizio LoaderAppalto di SIMOG", "loaderAppaltoUserPass.error", e);
		} catch (CriptazioneException e) {
			throw new GestoreException("Errore nella gestione delle credenziali di accesso al servizio LoaderAppalto di SIMOG", "loaderAppaltoUserPass.error", e);
		}
		return hMapSIMOGLAUserPass;

	}

	/**
	 * Gestione credenziali di accesso al servizio LoaderAppalto di SIMOG
	 * 
	 * @param simoguser
	 * @param syscon
	 * @param inserimento
	 *            TRUE se si deve inserire la riga, FALSE se deve essere
	 *            cancellata
	 * @throws GestoreException
	 */
	public void gestioneWsSimogLoaderAppaltoUserPass(Long syscon, String simoguser, String simogpass, boolean inserimento) throws GestoreException {

		boolean commitTransaction = false;
		TransactionStatus status = null;
		try {
			status = this.sqlManager.startTransaction();
			if (inserimento == true) {
				// Controllo se esiste gia' una riga con lo stesso SYSCON
				Long conteggio = (Long) this.sqlManager.getObject("select count(*) from W9LOADER_APPALTO_USR where syscon = ?", new Object[] { syscon });

				// Codifica della password
				ICriptazioneByte cupwspassICriptazioneByte = null;
				cupwspassICriptazioneByte = FactoryCriptazioneByte.getInstance(ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI), simogpass.getBytes(),
						ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);

				String simogpassCriptata = new String(cupwspassICriptazioneByte.getDatoCifrato());

				if (conteggio != null && conteggio.longValue() > 0) {
					this.sqlManager.update("update W9LOADER_APPALTO_USR set simoguser = ?, simogpass = ? where syscon = ?", new Object[] { simoguser, simogpassCriptata, syscon });
				} else {
					this.sqlManager.update("insert into W9LOADER_APPALTO_USR (simoguser, simogpass, syscon) values (?,?,?)", new Object[] { simoguser, simogpassCriptata, syscon });
				}
			} else {
				this.sqlManager.update("delete from W9LOADER_APPALTO_USR where syscon = ?", new Object[] { syscon });
			}
			commitTransaction = true;
		} catch (SQLException e) {
			throw new GestoreException("Errore nella gestione delle credenziali di accesso al servizio LoaderAppalto di SIMOG", "gestioneSIMOGLAUserPass.error", e);
		} catch (CriptazioneException e) {
			throw new GestoreException("Errore nella gestione delle credenziali di accesso al servizio LoaderAppalto di SIMOG", "gestioneSIMOGLAUserPass.error", e);
		} finally {
			if (status != null) {
				try {
					if (commitTransaction) {
						this.sqlManager.commitTransaction(status);
					} else {
						this.sqlManager.rollbackTransaction(status);
					}
				} catch (SQLException e) {
					logger.error("gestioneCUPWSUserPass: ", e);
				}
			}
		}
	}

	private final ArrayList<XmlValidationError> validaMsgXML(final XmlObject oggettoDoc) throws IOException {

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
	
	/**
	   * copio i flussi relativi alla fase nella tabella W9FLUSSI_ELIMINATI e  
	   * li cancello dalla W9FLUSSI
	   * 
	   * @param codgara Codice della gara
	   * @param codlott Codice del lotto
	   * @param faseesecuzione Fase d'esecuzione
	   * @param num Progressivo della fase d'esecuzione
	   * @param motivoCancellazione motivazione cancellazione
	   * @throws GestoreException GestoreException
	   */
	  
	  public void CancellazioneFlusso(Long codgara, Long codlott, Long faseesecuzione, Long num, String motivoCancellazione) throws GestoreException {
		  boolean commitTransaction = false;
		  TransactionStatus status = null;
		 
	    try {
	       String sqlCancellaFlusso = "insert into W9FLUSSI_ELIMINATI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP) select IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP FROM W9FLUSSI WHERE KEY01 = ? AND KEY02 = ? AND KEY03 = ? AND KEY04 = ? and TINVIO2 > 0";
	       String updateW9FlussiEliminati = "update W9FLUSSI_ELIMINATI set DATCANC = ?, MOTIVOCANC = ? where KEY01 = ? AND KEY02 = ? AND KEY03 = ? AND KEY04 = ? ";
	       String deleteW9Flussi = "delete from W9FLUSSI where KEY01 = ? AND KEY02 = ? AND KEY03 = ? AND KEY04 = ? and TINVIO2 > 0";
	       status = this.sqlManager.startTransaction();
	       this.sqlManager.update(sqlCancellaFlusso, new Object[] {codgara, codlott, faseesecuzione, num });
	       this.sqlManager.update(updateW9FlussiEliminati, new Object[] {new Timestamp(UtilityDate.getDataOdiernaAsDate().getTime()), motivoCancellazione, codgara, codlott, faseesecuzione, num });
	       this.sqlManager.update(deleteW9Flussi, new Object[] {codgara, codlott, faseesecuzione, num });
	       commitTransaction = true;
	    } catch (SQLException e) {
	    	logger.error("Si sono verificati degli errori durante la cancellazione logica dei flussi", e);
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
	 * Procedura che effettua la cancellazione di una scheda simog
	 * 
	 * @param fase_esecuzione fase da eliminare
	 * @return result 0=Conclusione positiva, 1=Abort procedura, 2=Conclusione negativa, 3=La scheda non esiste in Simog
	 * @throws GestoreException
	 */
	  public int eliminaSchedaSimogInORT(final DataColumnContainer datiAggiornamento, final HttpServletRequest request, final TransactionStatus status) throws GestoreException {

		  if (logger.isDebugEnabled()) {
			  logger.debug("eliminaSchedaSimogInORT: inizio metodo");
		  }
		  
		  int result = 0;
		  boolean cancellazioneLogica = false;
		  ErroreType[] erroriDalLoaderAppalto = null;
		  try {
 
			  Long idFlusso = datiAggiornamento.getLong("W9FLUSSI.IDFLUSSO");
			  Long codiceGara = datiAggiornamento.getLong("W9FLUSSI.KEY01");
			  Long codiceLotto = datiAggiornamento.getLong("W9FLUSSI.KEY02");
			  Long fase_esecuzione = datiAggiornamento.getLong("W9FLUSSI.KEY03");
			  Long progressivoFase = datiAggiornamento.getLong("W9FLUSSI.KEY04");
			  String noteInvio = datiAggiornamento.getString("W9FLUSSI.NOTEINVIO");

				String codiceCui = (String) this.sqlManager.getObject("select CODCUI from W9LOTT where CODGARA = ? and CODLOTT = ?", new Object[] { codiceGara, codiceLotto });
			  
			  // Se la fase e' oggetto di monitoraggio simog e (fase_esecuzione = 984 e il campo W9LOTT.CODCUI non e' valorizzato)
				// oppure (fase_esecuzione != 984 e il campo W9LOTT.CODCUI e' valorizzato)
			  // (se il codice non e' valorizzato significa che in SIMOG non e' mai arrivata l'aggiudicazione o qualsiasi altra fase successiva) 
			  if (CostantiSimog.TipiSchede.containsKey(fase_esecuzione) && 
			  			((CostantiW9.COMUNICAZIONE_ESITO == fase_esecuzione && StringUtils.isEmpty(codiceCui)) || 
			  				(CostantiW9.COMUNICAZIONE_ESITO != fase_esecuzione && StringUtils.isNotEmpty(codiceCui)))) {
				  erroriDalLoaderAppalto = this.loaderAppalto(codiceGara, codiceLotto, fase_esecuzione, progressivoFase, idFlusso, null, null, true);
				  if (erroriDalLoaderAppalto == null) {
					  //se la chiamata al loader appalto va a buon fine
					  Long numxml = (Long) this.sqlManager.getObject("select max(NUMXML) from W9XML where IDFLUSSO = ?", new Object[]{idFlusso});
					  if (numxml != null) {
						  String codice = (String) this.sqlManager.getObject("select CODICE from W9XMLANOM where CODGARA = ? AND CODLOTT = ? AND NUMXML = ?", new Object[]{codiceGara, codiceLotto, numxml});
						  if (codice != null) {
							  if (codice.equals("ELIMINAZIONE") || codice.equals("SIMOG_MASSLOADER_191")) {
								  //cancellazione andata a buon fine oppure la scheda non è presente in SIMOG
								  //sposto i flussi in W9FLUSSI_ELIMINATI
								  cancellazioneLogica = true;
								  if (codice.equals("SIMOG_MASSLOADER_191")) {
									  //la scheda non esiste in simog
									  result = 3;
								  }
							  } else {
								  //conclusione negativa
								  result = 2;
								  
								  // Si riporta il messaggio di errore nel campo W9INBOX.MSG
								  Long idComun = (Long) this.sqlManager.getObject(
                      "select IDCOMUN from W9FLUSSI where AREA=1 and KEY01=? and KEY02=? and KEY03=? and KEY04=? and TINVIO2 < 0 order by DATINV desc", 
                      new Object[] { codiceGara, codiceLotto, fase_esecuzione, progressivoFase });
								  
								  if (idComun != null && idFlusso != null) {
  								  String descrizione = (String) this.sqlManager.getObject(
  								        "select a.DESCRIZIONE from W9XMLANOM a, w9xml x, w9flussi f "
  								      + " where a.codgara = x.codgara "
  								        + " and a.codlott = x.codlott "
  								        + " and a.numXml  = x.numXml "
  								        + " and a.livello = 'ERRORE' "
  								        + " and f.idFlusso = x.idFlusso "
  								        + " and f.tinvio2  < 0 "
  								        + " and x.idFlusso = ? "
  								        + " and f.idFlusso = ? "
  								        + " and f.idComun  = ? ",
  								      new Object[] { idFlusso, idFlusso, idComun } );
  								  if (StringUtils.isNotEmpty(descrizione)) {
  								    this.sqlManager.update("update W9INBOX set MSG=? where IDCOMUN=?",
  								        new Object[] { descrizione, idComun });
  								  }
								  }
							  }
						  } else {
							  result = 1;
						  }
					  }
				  } else {
					  result = 1;
				  }
			  } else {
				  cancellazioneLogica = true;
			  }

			  if (cancellazioneLogica) {
				  //se la scheda non è oggetto di monitoraggio simog
				  //sposto i flussi in W9FLUSSI_ELIMINATI
				  this.CancellazioneFlusso(codiceGara, codiceLotto, fase_esecuzione, progressivoFase, noteInvio);
				  //cancella scheda in OR
				  DataColumn[] arrayDataContW9FASI = new DataColumn[4]; 

				  arrayDataContW9FASI[0] = new DataColumn("V_W9INVIIFASI.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codiceGara));
				  arrayDataContW9FASI[1] = new DataColumn("V_W9INVIIFASI.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codiceLotto));
				  arrayDataContW9FASI[2] = new DataColumn("V_W9INVIIFASI.FASE_ESECUZIONE", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, fase_esecuzione));
				  arrayDataContW9FASI[3] = new DataColumn("V_W9INVIIFASI.NUM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, progressivoFase));

				  DataColumnContainer dataContW9FASI = new DataColumnContainer(arrayDataContW9FASI);

				  dataContW9FASI.getColumn("V_W9INVIIFASI.CODGARA").setChiave(true);
				  dataContW9FASI.getColumn("V_W9INVIIFASI.CODLOTT").setChiave(true);
				  dataContW9FASI.getColumn("V_W9INVIIFASI.FASE_ESECUZIONE").setChiave(true);
				  dataContW9FASI.getColumn("V_W9INVIIFASI.NUM").setChiave(true);

				  GestoreW9FASI gestoreW9FASI = new GestoreW9FASI();
				  gestoreW9FASI.setRequest(request);
				  gestoreW9FASI.elimina(status, dataContW9FASI);
				  
				  this.w9Manager.updateSituazioneGaraLotto(codiceGara, codiceLotto);
			  }
		  } catch (SQLException e) {
			  throw new GestoreException("Errore nella gestione delle credenziali di accesso al servizio LoaderAppalto di SIMOG", "gestioneSIMOGLAUserPass.error", e);
		  }
		  
		  if (logger.isDebugEnabled()) {
			  logger.debug("eliminaSchedaSimogInORT: fine metodo");
		  }  
		  return result;
	  }

	  /**
	   * Set del proxy per uscire dalla intranet
	   * 
	   * @param loaderAppaltoWSServiceStub
	   */
	  public void setProxy(LoaderAppaltoWSServiceStub loaderAppaltoWSServiceStub) {
	    if (logger.isDebugEnabled()) {
	      logger.debug("setProxy: inizio metodo");
	    }
	    
	    String strProxyUrl      = ConfigManager.getValore(CostantiSimog.PROP_SITAT_SERVIZI_SIMOG_PROXY_URL);
	    String strProxyPort     = ConfigManager.getValore(CostantiSimog.PROP_SITAT_SERVIZI_SIMOG_PROXY_PORT);
	    String strProxyUsername = ConfigManager.getValore(CostantiSimog.PROP_SITAT_SERVIZI_SIMOG_PROXY_USERNAME);
	    String strProxyPassword = ConfigManager.getValore(CostantiSimog.PROP_SITAT_SERVIZI_SIMOG_PROXY_PASSWORD);
	    
	    if (StringUtils.isNotEmpty(strProxyUrl) && StringUtils.isNotEmpty(strProxyPort)) {
	      HttpTransportProperties.ProxyProperties proxyProperties = new HttpTransportProperties.ProxyProperties();
	      proxyProperties.setProxyName(strProxyUrl);
	      proxyProperties.setProxyPort(Integer.parseInt(strProxyPort));
	      if (StringUtils.isNotEmpty(strProxyUsername)) {
	        proxyProperties.setUserName(strProxyUsername);
	      }
	      if (StringUtils.isNotEmpty(strProxyPassword)) {
	        proxyProperties.setPassWord(strProxyPassword);
	      }
	      loaderAppaltoWSServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.PROXY, proxyProperties);
	    }
	    
	    if (logger.isDebugEnabled()) {
	      logger.debug("setProxy: fine metodo");
	    }
	  }
	  
	}


/**
 * Inner class.
 * 
 * @author Luca.Giacomazzo
 */
class DirFilter implements FilenameFilter {
	/**
	 * pattern.
	 */
	private final Pattern pattern;

	/**
	 * Costruttore.
	 * 
	 * @param regex
	 *            Regual Expression
	 */
	public DirFilter(final String regex) {
		pattern = Pattern.compile(regex);
	}

	/**
	 * 
	 * 
	 * @param dir
	 *            File che rappresenta la directory di destinazione
	 * @param name
	 *            Nome del file
	 * @return Ritorna true se il file esiste nella directory dir, false
	 *         altrimenti
	 */
	public boolean accept(final File dir, final String name) {
		// Strip path information, search for regex:
		return pattern.matcher(new File(name).getName()).matches();
	}

}
