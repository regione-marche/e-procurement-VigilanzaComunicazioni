package it.eldasoft.w9.bl.sa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.Stub;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.Header;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eldasoft.commons.beans.ResultBean;
import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilityDateExtension;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.AppaltiLiguriaWebServiceStub;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionException;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.WsException;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ClasseImportoType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ContrattoDocument;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCPVSecondari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCondizioni;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaTipologiaFS;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaTipologiaL;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoSchedaType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL;

/**
 * Manager per l'esportazione dei dati della gara verso Liguria Digitale.
 * 
 * @author Luca.Giacomazzo
 */
public class ExportLiguriaDigitaleManager {

	/** Logger. */
	static Logger logger = Logger.getLogger(ExportLiguriaDigitaleManager.class);
	
	private SqlManager sqlManager;
	
	private GenChiaviManager genChiaviManager;
	
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}
	
	public SqlManager getSqlManager() {
		return this.sqlManager;
	}
	
	public void setGenChiaviManager(GenChiaviManager genChiaviManager) {
		this.genChiaviManager = genChiaviManager;
	}

	public ResultBean exportXml(long codiceGara, long codiceLotto, ProfiloUtente profiloUtente) throws SQLException, GestoreException {

	if (logger.isDebugEnabled()) {
		logger.debug("exportXml: inizio metodo");
	}

	String codiceCIG = UtilitySITAT.getCIGLotto(codiceGara, codiceLotto, this.sqlManager);

	ResultBean result = new ResultBean();
	result.setOk(Boolean.TRUE);

	String urlServizio = ConfigManager.getValore("it.eldasoft.appaltiLiguria.wsUrl");
	String nomeHttpHeaderToken = ConfigManager.getValore("it.eldasoft.appaltiLiguria.httpHeader.tokenName");
	String valoreHttpHeaderToken = ConfigManager.getValore("it.eldasoft.appaltiLiguria.httpHeader.tokenValue");
	String codiceFiscaleEnte = ConfigManager.getValore("it.eldasoft.appaltiLiguria.datiEnte.codiceFiscale");
	//String idUserEnte = ConfigManager.getValore("it.eldasoft.appaltiLiguria.datiEnte.idUserEnte");

	if (StringUtils.isNotEmpty(urlServizio)
		&& StringUtils.isNotEmpty(nomeHttpHeaderToken)
			&& StringUtils.isNotEmpty(valoreHttpHeaderToken)
				&& StringUtils.isNotEmpty(codiceFiscaleEnte)) {

		Contratto contratto = Contratto.Factory.newInstance();
		DatiEnte datiEnte = DatiEnte.Factory.newInstance();
		datiEnte.setCODICEFISCALE(codiceFiscaleEnte);
		
		String cfTec = (String) this.sqlManager.getObject(
		    "select CFTEC from TECNI where CODTEC=(select RUP from W9GARA where CODGARA=?)",
		        new Object[] { codiceGara });
		if (StringUtils.isNotEmpty(cfTec)) {
		  datiEnte.setIDUSERENTE(cfTec);
		}
		contratto.setDATIENTE(datiEnte);

		DatiGeneraliContratto datiGeneraliContratto = DatiGeneraliContratto.Factory.newInstance();
		datiGeneraliContratto.setCONTROLLOINVIO("1");

		this.setDatiComuni(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setDatiComuniEstesi(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setListaOfferenti(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneAggiudicazione(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneInizio(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneSAL(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneVarianti(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneSospensioni(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneConclusione(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneCollaudo(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneSubappalti(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneRitardiRecessi(codiceGara, codiceLotto, datiGeneraliContratto);
		this.setSezioneAccordiBonari(codiceGara, codiceLotto, datiGeneraliContratto);

		contratto.setDATIGENERALICONTRATTO(datiGeneraliContratto);

		String contrattoXml = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ContrattoDocument contrattoDocument = ContrattoDocument.Factory.newInstance();
			contrattoDocument.setContratto(contratto);
			contrattoDocument.save(baos);
			contrattoXml = baos.toString();
			baos.close();
		} catch (IOException e1) {
			logger.error("Errore nella creazione del XML del contratto", e1);
		}

		if (StringUtils.isNotEmpty(contrattoXml)) {
			logger.debug("Contratto in invio a Liguria Digitale:\n" + contrattoXml );
		}

		try {
			AppaltiLiguriaWebServiceStub appaltiLiguriaStub = new AppaltiLiguriaWebServiceStub(urlServizio);
			// Set timeout di 5 minuti (5 * 60 * 1000 ms)
			int timeOutInMilliSeconds = 5 * 60 * 1000;
			
			((Stub) appaltiLiguriaStub)._getServiceClient().getOptions().setProperty(
          HTTPConstants.SO_TIMEOUT, new Integer(timeOutInMilliSeconds));
			((Stub) appaltiLiguriaStub)._getServiceClient().getOptions().setProperty(
          HTTPConstants.CONNECTION_TIMEOUT, new Integer(timeOutInMilliSeconds));
			
			//String httpHeaderTokenName = ConfigManager.getValore("it.eldasoft.appaltiLiguria.httpHeader.tokenName");
			String httpHeaderTokenValue = ConfigManager.getValore("it.eldasoft.appaltiLiguria.httpHeader.tokenValue");

			// Istruzioni recuperate da questo sito:
			// https://www.programcreek.com/java-api-examples/?class=org.apache.axis2.client.Options&method=setProperty

			Options options = appaltiLiguriaStub._getServiceClient().getOptions();
			List<Header> headerList = new ArrayList<Header>();
			Header header = new Header();
			header.setName(HTTPConstants.HEADER_AUTHORIZATION);
			header.setValue(httpHeaderTokenValue);
			headerList.add(header);
			options.setProperty(org.apache.axis2.transport.http.HTTPConstants.HTTP_HEADERS, headerList);
			appaltiLiguriaStub._getServiceClient().setOptions(options);
			
			appaltiLiguriaStub._getServiceClient().getOptions().setProperty(
					HTTPConstants.HEADER_AUTHORIZATION, httpHeaderTokenValue);
			
			InvioContratto invioContratto = InvioContratto.Factory.newInstance();
			invioContratto.setContratto(contratto);
			InvioContrattoDocument invioContrattoDocument = InvioContrattoDocument.Factory.newInstance();
			invioContrattoDocument.setInvioContratto(invioContratto);
			InvioContrattoResponseDocument invioContrattoResponseDocument = appaltiLiguriaStub.invioContratto(invioContrattoDocument);
			
			if (invioContrattoResponseDocument != null) {
				if (invioContrattoResponseDocument.getInvioContrattoResponse() != null) {
					InvioContrattoResponse invioContrattoResponse = invioContrattoResponseDocument.getInvioContrattoResponse();
					if (invioContrattoResponse.isSetReturn() && StringUtils.isNotEmpty(invioContrattoResponse.getReturn())) {
						String rispostaWs = invioContrattoResponse.getReturn();
						if (StringUtils.contains(rispostaWs, "ERR_") || StringUtils.contains(rispostaWs, "SAL/VARIANTI/SOSPENSIONI/CONTENSIOSI")) {
							logger.error("L'invio dei dati del lotto con CIG = " + codiceCIG
								+ " e' terminato con errore. Questo il messaggio di ritorno dal servizio: "
								+ rispostaWs
								+ " (Riferimento al lotto: CODGARA=" + codiceGara + ", CODLOTT=" + codiceLotto 
								+ ". Rifermiento all'utente esecutore: SYSCON=" + profiloUtente.getId() + ")" );
							result.setOk(Boolean.FALSE);
							result.setMessaggi(new String[] { "" });
						} 
					}

					// Creazione del record in W9FLUSSI
					synchronized (contratto) {
						int w9FlussiId = this.genChiaviManager.getNextId("W9FLUSSI");
						this.sqlManager.update("insert into W9FLUSSI (IDFLUSSO,AREA,KEY01,KEY02,TINVIO2,DATINV,XML,CFSA,CODOGG,CODCOMP,AUTORE) values (?,?,?,?,?,?,?,?,?,?,?)",
							new Object[] { w9FlussiId, 1, codiceGara, codiceLotto, 1, new Timestamp(new GregorianCalendar().getTimeInMillis()),  
								contrattoXml, codiceFiscaleEnte, codiceCIG, profiloUtente.getId(), profiloUtente.getNome() });
					}
				}
			}
		} catch (AxisFault e) {
			StringBuffer strBuffer = new StringBuffer("Errore inaspettato durante l'interazione con il servizio");
			if (StringUtils.isNotEmpty(e.getMessage())) {
				strBuffer.append(". Message: ");
				strBuffer.append(e.getMessage());
			}
			logger.error(strBuffer.toString(), e);

			result.setOk(Boolean.FALSE);
			result.setMessaggi(new String[] { e.getMessage() });
			
			} catch (RemoteException re) {
				logger.error("Errore remoto inaspettato durante l'interazione con il servizio", re);

				result.setOk(Boolean.FALSE);
				String str = "Errore remoto inaspettato durante l'invio dei dati";
				if (StringUtils.isNotEmpty(re.getLocalizedMessage())) {
					str = str.concat(" (".concat(re.getLocalizedMessage()).concat(")"));
				} else if (StringUtils.isNotEmpty(re.getMessage())) {
					str = str.concat(" (".concat(re.getMessage()).concat(")"));
				}
				result.setMessaggi(new String[] { str });
			} catch (JAXBExceptionException je) {
				StringBuffer strBuffer = new StringBuffer("Errore inaspettato durante l'interazione con il servizio");
				if (StringUtils.isNotEmpty(je.getMessage())) {
					strBuffer.append(". Message: ");
					strBuffer.append(je.getMessage());
				}
				logger.error(strBuffer.toString(), je);

				result.setOk(Boolean.FALSE);
				result.setMessaggi(new String[] { je.getMessage() });

			} catch (WsException e) {
				StringBuffer strBuffer = new StringBuffer("Errore inaspettato durante l'interazione con il servizio");
				if (e.getFaultMessage()!= null) {
					strBuffer.append(". Message: ");
					strBuffer.append(e.getFaultMessage().getCheckVerifyFault().getMessage());
				}
				logger.error(strBuffer.toString(), e);

				result.setOk(Boolean.FALSE);
				result.setMessaggi(new String[] { e.getFaultMessage().getCheckVerifyFault().getMessage() });
			}

		} else {
			logger.error("Valorizzare tutte le properties della sezione 'Comune di Genova - Osservatorio Regione Liguria'");

			result.setOk(Boolean.FALSE);
			result.setMessaggi(new String[] { "Configurazione per la connessione all'Osservatorio Regionale incompleta. " });
		}

		if (logger.isDebugEnabled()) {
			logger.debug("exportXml: fine metodo");
		}

		return result;
	}


  /**
   * Ritorna il numero di fasi presenti nella W9FASI per il lotto.
   * 
   * @param codiceGara
   * @param codiceLotto
   * @param faseEsecuzione
   * @return Ritorna il numero di fasi presenti nella W9FASI
   * @throws SQLException
   */
	private long getNumeroFasi(long codiceGara, long codiceLotto, int faseEsecuzione) throws SQLException {
		Long result = (Long) this.sqlManager.getObject(
			"select count(*) from W9FASI where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=?",
			new Object[] { codiceGara, codiceLotto, faseEsecuzione } ); 
		if (result != null) {
			return result.longValue();
		} else {
			return 0L;
		}
	}


	private FlagSNType.Enum convertiFlagSN(String flag) {
		if ("1".equals(flag)) {
			return FlagSNType.Enum.forString("S");
		} else {
			return FlagSNType.Enum.forString("N");
		}
	}


	private void setDatiComuni(Long codiceGara, Long codiceLotto, DatiGeneraliContratto datiGeneraliContratto) 
		throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiComuni: inizio metodo");
		}

		DatiComuni datiComuni = DatiComuni.Factory.newInstance();

		Vector<?> vectDatiComuni = this.sqlManager.getVector(
    		"select '1', 'Comune di Genova', W9GARA.OGGETTO, W9GARA.IDAVGARA, W9GARA.IMPDIS, W9GARA.IMPORTO_GARA, "
				+ "W9GARA.NLOTTI, W9LOTT.FLAG_ENTE_SPECIALE, TO_CHAR(W9GARA.ID_MODO_INDIZIONE) as ID_MODO_INDIZIONE, TO_CHAR(W9GARA.TIPO_APP) as TIPO_APP, "
				+ "W9GARA.FLAG_SA_AGENTE, W9GARA.CIG_ACCQUADRO, TO_CHAR(W9GARA.ID_TIPOLOGIA_SA) as ID_TIPOLOGIA_SA, W9GARA.DENOM_SA_AGENTE, "
				+ "W9GARA.CF_SA_AGENTE, TO_CHAR(W9GARA.TIPOLOGIA_PROCEDURA) as TIPOLOGIA_PROCEDURA, W9GARA.FLAG_CENTRALE_STIPULA, W9GARA.DGURI, W9GARA.DSCADE, "
				+ "W9GARA.CAM, W9GARA.SISMA, W9GARA.INDSEDE, W9GARA.COMSEDE, W9GARA.PROSEDE "
			+ "from W9GARA, W9LOTT "
			+ "where W9LOTT.CODGARA=? AND W9LOTT.CODLOTT=? AND W9GARA.CODGARA = W9LOTT.CODGARA", new Object[] { codiceGara, codiceLotto } );

		int i=0;
		// CODICE_UFFICIO" column="CODICE_UFFICIO"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setCODICEUFFICIO(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i = 1, DESCR_UFFICIO" column="DENOM"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setDESCRUFFICIO(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i = 2, OGGETTO_GARA" column="OGGETTO"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setOGGETTOGARA(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i = 3, ID_GARA" column="IDAVGARA"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setIDGARA(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i = 4, FLAG_IMPORTO_GARA_DISP" column="IMPDIS" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString();
			datiComuni.setFLAGIMPORTOGARADISP(convertiFlagSN(flag));
		}
		i++;
		// i = 5, IMPORTO_GARA" column="IMPORTO_GARA"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setIMPORTOGARA(SqlManager.getValueFromVectorParam(vectDatiComuni, i).doubleValue());
		}
		i++;
		// i = 6, NUM_LOTTI" column="NLOTTI"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setNUMLOTTI(Long.parseLong(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString()));
		}
		i++;
		// i = 7, FLAG_ENTE" column="FLAG_ENTE_SPECIALE" typeHandler="flagSOHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setFLAGENTE(FlagSOType.Enum.forString(
				SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString()));
		}
		i++;
		// i = 8,  MODO_INDIZIONE" column="ID_MODO_INDIZIONE"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
		  datiComuni.setMODOINDIZIONE(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i = 9, MODO_REALIZZAZIONE" column="TIPO_APP"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setMODOREALIZZAZIONE(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i = 10, FLAG_SA_AGENTE" column="FLAG_SA_AGENTE"  typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString();
			datiComuni.setFLAGSAAGENTE(convertiFlagSN(flag));
		}
		i++;
		// i = 11, CIG_AQ" column="CIG_ACCQUADRO"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setCIGAQ(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i=12, ID_TIPOLOGIA_SA" column="ID_TIPOLOGIA_SA"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setTIPOLOGIAPROCEDURA(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i=13, DEN_AMM_AGENTE" column="DENOM_SA_AGENTE"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setDENAMMAGENTE(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i=14, CF_AMM_AGENTE" column="CF_SA_AGENTE"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setCFAMMAGENTE(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i=15, TIPOLOGIA_PROCEDURA" column="TIPOLOGIA_PROCEDURA"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setTIPOLOGIAPROCEDURA(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i=16, FLAG_CENTRALE_STIPULA" column="FLAG_CENTRALE_STIPULA" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString();
			datiComuni.setFLAGCENTRALESTIPULA(convertiFlagSN(flag));
		}
		i++;
		// i=17, DATA_GURI_GARA" column="DGURI" typeHandler="calendarHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setDATAGURIGARA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
				SqlManager.getValueFromVectorParam(vectDatiComuni, i)));
		}
		i++;
		// i=18, DATA_SCADENZA_GARA" column="DSCADE" typeHandler="calendarHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setDATASCADENZAGARA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
				SqlManager.getValueFromVectorParam(vectDatiComuni, i)));
		}
		i++;
		// i=19, CAM_GARA" column="CAM" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString();
			datiComuni.setCAMGARA(convertiFlagSN(flag));
		}
		i++;
		// i=20, SISMA_GARA" column="SISMA" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString();
			datiComuni.setSISMAGARA(convertiFlagSN(flag));
		}
		i++;
		// i=21, INDSEDE_GARA" column="INDSEDE"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setINDSEDEGARA(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i=22, COMSEDE_GARA" column="COMSEDE"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setCOMSEDEGARA(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		i++;
		// i=23, PROSEDE_GARA" column="PROSEDE"
		if (SqlManager.getValueFromVectorParam(vectDatiComuni, i).getValue() != null) {
			datiComuni.setPROSEDEGARA(SqlManager.getValueFromVectorParam(vectDatiComuni, i).toString());
		}
		
		long numeroSAL_sopraSoglia = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA);
		long numeroSAL_sottoSoglia = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO);
		if ((numeroSAL_sopraSoglia + numeroSAL_sottoSoglia) > 0) {
		  List<?> listaImportiCertificati = this.sqlManager.getListVector(
			  "select IMPORTO_CERTIFICATO from W9AVAN where CODGARA=? and CODLOTT=? and IMPORTO_CERTIFICATO is not null",
				new Object[] { codiceGara, codiceLotto });
		  
		  if (listaImportiCertificati != null && listaImportiCertificati.size() > 0) {
		    double importo = 0;
		    for (int j=0; j < listaImportiCertificati.size(); j++) {
		      Vector<?> vectImportoCertificato = (Vector<?>) listaImportiCertificati.get(j);
		      importo = importo + SqlManager.getValueFromVectorParam(vectImportoCertificato, 0).doubleValue();
		    }
		    datiComuni.setIMPORTOLIQUIDATO(importo);
		  } else {
		    datiComuni.setIMPORTOLIQUIDATO(0);
		  }
		} else {
		  datiComuni.setIMPORTOLIQUIDATO(0);
		}
		
		datiGeneraliContratto.setDATICOMUNI(datiComuni);
		
		if (logger.isDebugEnabled()) {
			logger.debug("setDatiComuni: fine metodo");
		}
	}


	private void setDatiComuniEstesi(Long codiceGara, Long codiceLotto, DatiGeneraliContratto datiGeneraliContratto) 
		throws SQLException, GestoreException {
  
		if (logger.isDebugEnabled()) {
			logger.debug("setDatiComuniEstesi: inizio metodo");
		}

		DatiComuniEstesi datiComuniEstesi = DatiComuniEstesi.Factory.newInstance();

		Vector<?> vectDatiComuniEstesi = this.sqlManager.getVector(
			"select l.OGGETTO, COMCON, IMPORTO_LOTTO, CPV, g.SOMMA_URGENZA as SOMMA_URGENZA, TO_CHAR(ID_SCELTA_CONTRAENTE) as ID_SCELTA_CONTRAENTE, "
			  + "(select CODAVCP from W9CODICI_AVCP where TABCOD='W3z03' and CODSITAT=l.ID_CATEGORIA_PREVALENTE) as CAT_PREVALENTE, "
			  + "TO_CHAR(ID_SCELTA_CONTRAENTE_50) as ID_SCELTA_CONTRAENTE_50, NLOTTO as NUM_PROGR_LOTTO, NLOTTO as NUM_LOTTO_GARA, l.MANOD, IMPDISP, CLASCAT, " 
			  + "CIGCOM, TO_CHAR(NLOTTO) as NUM_PROGRESSIVO, l.TIPO_CONTRATTO as TIPO_CONTRATTO, l.FLAG_ENTE_SPECIALE as FLAG_ENTE_SPECIALE, "
			  + "TO_CHAR(ID_MODO_GARA) as ID_MODO_GARA, SUBSTR(LUOGO_ISTAT,4,6) as LUOGO_ISTAT, ART_E1, LUOGO_NUTS, "  
			  + "to_CHAR(ID_TIPO_PRESTAZIONE) as ID_TIPO_PRESTAZIONE, CIG, l.ART_E2, coalesce(IMPORTO_ATTUAZIONE_SICUREZZA, 0) as IMPORTO_ATTUAZIONE_SICUREZZA, "
			  + "CUP, l.DATA_PUBBLICAZIONE, '2' as FLAG_SOTTOSOGLIA "
			+ "from W9LOTT l, W9GARA g where l.CODGARA=g.CODGARA and l.CODGARA=? AND l.CODLOTT=?", new Object[] { codiceGara, codiceLotto } );
		
		int i=0;
		// i= 0 "OGGETTO" column="OGGETTO"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setOGGETTO(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 1 "FLAG_LOTTO_DERIVANTE" column="COMCON" typeHandler="flagSNHandler"	
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString();
			datiComuniEstesi.setFLAGLOTTODERIVANTE(convertiFlagSN(flag));
		}
		i++;
		// i= 2 "IMPORTO_LOTTO" column="IMPORTO_LOTTO" nullValue="-9999"/>
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setIMPORTOLOTTO(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).doubleValue());
		}
		i++;
		// i= 3 "CPV" column="CPV"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setCPV(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 4 "FLAG_SOMMA_URGENZA" column="SOMMA_URGENZA" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString();
			datiComuniEstesi.setFLAGSOMMAURGENZA(convertiFlagSN(flag));
		}
		i++;
		// i= 5 "ID_SCELTA_CONTRAENTE" column="ID_SCELTA_CONTRAENTE"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setIDSCELTACONTRAENTE(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 6 "CAT_PREVALENTE" column="CAT_PREVALENTE" typeHandler="categoriaHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setCATPREVALENTE(CategoriaType.Enum.forString(
				SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString()));
		}
		i++;
		// i= 7 "PROC_SCELTA_502016" column="ID_SCELTA_CONTRAENTE_50"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setPROCSCELTA502016( SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 8 "NUM_PROGR_LOTTO" column="NUM_PROGR_LOTTO"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setNUMPROGRLOTTO(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).longValue());
		}
		i++;
		// i= 9 "NUM_LOTTO_GARA" column="NUM_LOTTO_GARA"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setNUMLOTTOGARA(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).longValue());
		}
		i++;
		// i= 10 "FLAG_POSA" column="MANOD" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString();
			datiComuniEstesi.setFLAGPOSA(convertiFlagSN(flag));
		}
		i++;
		// i= 11 "FLAG_IMPORTO_DISP" column="IMPDISP" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString();
			datiComuniEstesi.setFLAGIMPORTODISP(convertiFlagSN(flag));
		}
		i++;
		// i= 12 "CLASSE_IMPORTO" column="CLASCAT" typeHandler="classeImportoHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setCLASSEIMPORTO(ClasseImportoType.Enum.forString(
				SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString()));
		}
		i++;
		// i= 13 "CIG_PADRE" column="CIGCOM"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setCIGPADRE(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 14 "NUM_PROGRESSIVO" column="NUM_PROGRESSIVO"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setNUMPROGRESSIVO(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 15 "TIPO_APPALTO" column="TIPO_CONTRATTO" typeHandler="tipoSchedaHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setTIPOAPPALTO(TipoSchedaType.Enum.forString(
				SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString()));
		}
		i++;
		// i= 16 "FLAG_TIPO_SETTORE" column="FLAG_ENTE_SPECIALE" typeHandler="flagSOHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setFLAGTIPOSETTORE(FlagSOType.Enum.forString(
				SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString()));
		}
		i++;
		// i= 17 "ID_MODO_GARA" column="ID_MODO_GARA"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setIDMODOGARA(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 18 "LUOGO_ISTAT" column="LUOGO_ISTAT"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setLUOGOISTAT(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 19 "FLAG_1632006_1" column="ART_E1" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setFLAG16320061(FlagSNType.Enum.forString(
				SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString()));
		}
		i++;
		// i= 20 "NUTS" column="LUOGO_NUTS"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setNUTS(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 21 "ID_TIPO_PRESTAZIONE" column="ID_TIPO_PRESTAZIONE"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setIDTIPOPRESTAZIONE(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 22 "CIG" column="CIG"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setCIG(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 23 "FLAG_1632006_2" column="ART_E2" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setFLAG16320062(FlagSNType.Enum.forString(
				SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString()));
		}
		i++;
		// i= 24 "IMPORTO_SIC" column="IMPORTO_ATTUAZIONE_SICUREZZA"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setIMPORTOSIC(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).doubleValue());
		}
		i++;
		// i= 25 "CUP" column="CUP"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setCUP(SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString());
		}
		i++;
		// i= 26 "DATA_PUBBLICAZIONE" column="DATA_PUBBLICAZIONE" typeHandler="calendarHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			datiComuniEstesi.setDATAPUBBLICAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
				SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i)));
		}
		i++;
		// i= 27 "FLAG_SOTTOSOGLIA" column="FLAG_SOTTOSOGLIA" typeHandler="flagSNHandler" javaType="java.lang.String"
		if (SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiComuniEstesi, i).toString();
			datiComuniEstesi.setFLAGSOTTOSOGLIA(convertiFlagSN(flag));
		}
	
		String codiceRUP = (String) this.sqlManager.getObject("select RUP from W9LOTT where CODGARA=? and CODLOTT=?",
			new Object[] { codiceGara, codiceLotto } );

		if (StringUtils.isNotEmpty(codiceRUP)) {
			DatiResponsabile datiResponsabile = DatiResponsabile.Factory.newInstance();
			this.getTecnico(codiceRUP, datiResponsabile);
			datiResponsabile.setALBOPROFESSIONALE(convertiFlagSN("2"));
			datiComuniEstesi.setResponsabile(datiResponsabile);
		}
	
		// Valorizzazione lista tipologia forniture sevizi
		List<?> listVectTipologiaFS = this.sqlManager.getListVector("select ID_APPALTO from W9APPAFORN WHERE CODGARA=? AND CODLOTT=?", new Object[] { codiceGara, codiceLotto } );
		if (listVectTipologiaFS != null && listVectTipologiaFS.size() > 0) {
			ListaTipologiaFS listaTipologiaFS = ListaTipologiaFS.Factory.newInstance();
			List <TipologiaFS> arrayTipologiaFS = new ArrayList<TipologiaFS>();
			for (int j=0; j < listVectTipologiaFS.size(); j++) {
				Vector<?> vectTipologiaFS = (Vector<?>) listVectTipologiaFS.get(j);
			
				TipologiaFS tipologiaFS = TipologiaFS.Factory.newInstance();
				tipologiaFS.setIDAPPALTOFS(((JdbcParametro) vectTipologiaFS.get(0)).getStringValue());
				arrayTipologiaFS.add(tipologiaFS);
			}
			listaTipologiaFS.setIdTipologiaFSArray(arrayTipologiaFS.toArray(new TipologiaFS[0]));
			datiComuniEstesi.setListaIdTipologiaFS(listaTipologiaFS);
		}
	
		// Valorizzazione lista tipologia lavori
		List<?> listVectTipologiaL = this.sqlManager.getListVector("select ID_APPALTO from W9APPALAV WHERE CODGARA=? AND CODLOTT=?", new Object[] { codiceGara, codiceLotto } );
		if (listVectTipologiaL != null && listVectTipologiaL.size() > 0) {
			ListaTipologiaL listaTipologiaL = ListaTipologiaL.Factory.newInstance();
		  List <TipologiaL> arrayTipologiaL = new ArrayList<TipologiaL>();
		  for (int j=0; j < listVectTipologiaL.size(); j++) {
			Vector<?> vectTipologiaL = (Vector<?>) listVectTipologiaL.get(j);
			
			TipologiaL tipologiaL = TipologiaL.Factory.newInstance();
			tipologiaL.setIDAPPALTOL(((JdbcParametro) vectTipologiaL.get(0)).getStringValue());
			arrayTipologiaL.add(tipologiaL);
		  }
		  listaTipologiaL.setIdTipologiaLArray(arrayTipologiaL.toArray(new TipologiaL[0]));
		  datiComuniEstesi.setListaIdTipologiaL(listaTipologiaL);
		}
		
		
		// Valorizzazione lista condizioni
		List<?> listVectCondizioni = this.sqlManager.getListVector(
		    "select ID_CONDIZIONE from W9COND WHERE CODGARA=? AND CODLOTT=? "
		    + " UNION "
		    + " select 1 as ID_CONDIZIONE from W9GARA where CODGARA=? AND VER_SIMOG>1",
		    new Object[] { codiceGara, codiceLotto, codiceGara } );
		if (listVectCondizioni != null && listVectCondizioni.size() > 0) {
			ListaCondizioni listaCondizioni = ListaCondizioni.Factory.newInstance();
		  List <Condizioni> arrayCondizioni = new ArrayList<Condizioni>();
		  for (int j=0; j < listVectCondizioni.size(); j++) {
		    Vector<?> vectCondizione = (Vector<?>) listVectCondizioni.get(j);
			
		    Condizioni condizione = Condizioni.Factory.newInstance();
		    condizione.setIDCONDIZIONE(((JdbcParametro) vectCondizione.get(0)).getStringValue());
		    arrayCondizioni.add(condizione);
		  }
		  listaCondizioni.setIDCondizioniArray(arrayCondizioni.toArray(new Condizioni[0]));
		  datiComuniEstesi.setListaIDCondizioni(listaCondizioni);
		}
		
		// Valorizzazione lista cpv secondari
		List<?> listVectCpv = this.sqlManager.getListVector("select CPV from W9CPV WHERE CODGARA=? AND CODLOTT=?", new Object[] { codiceGara, codiceLotto } );
		if (listVectCpv != null && listVectCpv.size() > 0) {
			ListaCPVSecondari listaCpvSecondari = ListaCPVSecondari.Factory.newInstance();
			List <CPVSecondari> arrayCpvSecondari = new ArrayList<CPVSecondari>();
			for (int j=0; j < listVectCpv.size(); j++) {
				Vector<?> vectCpv = (Vector<?>) listVectCpv.get(j);
				CPVSecondari cpvSecondario = CPVSecondari.Factory.newInstance();
				cpvSecondario.setCPVCOMP(((JdbcParametro) vectCpv.get(0)).getStringValue());
				arrayCpvSecondari.add(cpvSecondario);
			}
			listaCpvSecondari.setIDCPVSecondariArray(arrayCpvSecondari.toArray(new CPVSecondari[0]));
			datiComuniEstesi.setListaIDCPVSecondari(listaCpvSecondari);
		}

		// Lista degli incarichi professionali
		ListaDatiSoggettiEstesi listaDatiSoggettiEstesi = ListaDatiSoggettiEstesi.Factory.newInstance();
		this.setDatiSoggettiEstesi(codiceGara, codiceLotto, null, listaDatiSoggettiEstesi);
		if (listaDatiSoggettiEstesi != null && listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray() != null && listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray().length > 0) {
			datiComuniEstesi.setListaDatiSoggettiEstesiArray(listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray());
		}

		// Dati Soggetti Estesi
		List<?> listVectCodiciTecnici = this.sqlManager.getListVector(
			"select CODTEC from W9INCA where CODGARA=? AND CODLOTT=? AND SEZIONE not in ('NP') order by ID_RUOLO asc, SEZIONE asc",
			new Object[] { codiceGara, codiceLotto } );

		if (listVectCodiciTecnici != null && listVectCodiciTecnici.size() > 0) {
			for (int j=0; j < listVectCodiciTecnici.size(); j++) {
				Vector<?> vectCodiciTecnici = (Vector<?>) listVectCodiciTecnici.get(j);

				if (SqlManager.getValueFromVectorParam(vectCodiciTecnici, 0).getValue() != null) {
					String codiceTecnico = ((JdbcParametro)vectCodiciTecnici.get(0)).toString();
					Long countTecni = (Long) this.sqlManager.getObject("select count(*) from TECNI where CODTEC=?", new Object[] { codiceTecnico } );
					if (countTecni > 0) {
						DatiResponsabile datiResponsabile = DatiResponsabile.Factory.newInstance();
						this.getTecnico(codiceTecnico, datiResponsabile);
						datiComuniEstesi.getListaDatiSoggettiEstesiArray(j).setResponsabile(datiResponsabile);
					}
				}
			}
		}

		datiGeneraliContratto.setDATICOMUNIESTESI(datiComuniEstesi);

		if (logger.isDebugEnabled()) {
			logger.debug("setDatiComuniEstesi: fine metodo");
		}
	}
  
	private void setListaOfferenti(Long codiceGara, Long codiceLotto, DatiGeneraliContratto datiGeneraliContratto) 
		throws SQLException, GestoreException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("setListaOfferenti: inizio metodo");
		}

		ListaOfferenti listaOfferenti = ListaOfferenti.Factory.newInstance();


		List<?> dblistaOfferenti = this.sqlManager.getListVector("select TIPOAGG, PARTECIP, RUOLO, NUM_RAGG from W9IMPRESE where CODGARA=? AND CODLOTT=? order by TIPOAGG asc, NUM_IMPR asc, NUM_RAGG asc",
			new Object[] { codiceGara, codiceLotto} );
    
		if (dblistaOfferenti != null && dblistaOfferenti.size() > 0) {

			Offerente[] arrayOfferenti = new Offerente[dblistaOfferenti.size()];
			for (int j=0; j < dblistaOfferenti.size(); j++) {
				Vector<?> vectDatiOfferente = (Vector<?>) dblistaOfferenti.get(j);

				Offerente offerente = Offerente.Factory.newInstance();
				int i = 0;
				// i = 0, "TIPOLOGIA_SOGGETTO" column="TIPOAGG"
				if (SqlManager.getValueFromVectorParam(vectDatiOfferente, i).getValue() != null) {
					offerente.setTIPOLOGIASOGGETTO(SqlManager.getValueFromVectorParam(vectDatiOfferente, i).toString());
				}
				i++;
				// i = 1, "flagPartecipante" column="PARTECIP"
				if (SqlManager.getValueFromVectorParam(vectDatiOfferente, i).getValue() != null) {
					offerente.setFlagPartecipante(SqlManager.getValueFromVectorParam(vectDatiOfferente, i).toString());
				}
				i++;
				// i = 2, "RUOLO" column="RUOLO"
				if (SqlManager.getValueFromVectorParam(vectDatiOfferente, i).getValue() != null) {
					offerente.setRUOLO(SqlManager.getValueFromVectorParam(vectDatiOfferente, i).toString());
				}
				i++;
				// i = 3, "ID_GRUPPO" column="NUM_RAGG"
				if (SqlManager.getValueFromVectorParam(vectDatiOfferente, i).getValue() != null) {
				offerente.setIDGRUPPO(Integer.parseInt(SqlManager.getValueFromVectorParam(vectDatiOfferente, i).toString()));
				}
				arrayOfferenti[j] = offerente;
			}
			listaOfferenti.setIdOfferentiArray(arrayOfferenti);
			datiGeneraliContratto.setLISTAOFFERENTI(listaOfferenti);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setListaOfferenti: fine metodo");
		}
	}


	private void setSezioneAggiudicazione(Long codiceGara, Long codiceLotto, DatiGeneraliContratto datiGeneraliContratto) 
		throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneAggiudicazione: inizio metodo");
		}

		long numeroAggiudicazione = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA) + 
			this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE) +
				this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.ADESIONE_ACCORDO_QUADRO);

		if (numeroAggiudicazione > 0) {
			Vector<?> vectDatiAggiudicazione = this.sqlManager.getVector(
				"select DATA_VERB_AGGIUDICAZIONE, IMPORTO_LAVORI, IMPORTO_SERVIZI, IMPORTO_FORNITURE, W9APPA.IMPORTO_ATTUAZIONE_SICUREZZA, "     // 0, .., 4
					+ "IMPORTO_PROGETTAZIONE, OPERE_URBANIZ_SCOMPUTO, FLAG_LIVELLO_PROGETTUALE, FIN_REGIONALE, IMPORTO_AGGIUDICAZIONE, IMPORTO_DISPOSIZIONE, "  // 5, .., 10
					+ "ROUND(PERC_OFF_AUMENTO,5) as PERC_OFF_AUMENTO, IMPORTO_COMPL_APPALTO, REQUISITI_SETT_SPEC, IMPORTO_SUBTOTALE, ASTA_ELETTRONICA, " // 11, .., 15
					+ "ROUND(PERC_RIBASSO_AGG,5) as PERC_RIBASSO_AGG, TO_CHAR(ID_MODO_INDIZIONE) as ID_MODO_INDIZIONE, coalesce(NUM_IMPRESE_INVITATE,0) as NUM_IMPRESE_INVITATE, " // 16, .., 18
					+ "NUM_OFFERTE_AMMESSE, PREINFORMAZIONE, NUM_IMPRESE_RICHIEDENTI, NUM_IMPRESE_OFFERENTI, " // 19, .., 22
					+ "TERMINE_RIDOTTO, DATA_SCADENZA_RICHIESTA_INVITO, DATA_SCADENZA_PRES_OFFERTA, FLAG_ACCORDO_QUADRO, "  // 23, .., 26
					+ "DATA_INVITO, NUM_MANIF_INTERESSE, DATA_MANIF_INTERESSE, NUM_OFFERTE_ESCLUSE, OFFERTA_MASSIMO, "  // 27, .., 31
					+ "OFFERTA_MINIMA, ROUND(VAL_SOGLIA_ANOMALIA,5) as VAL_SOGLIA_ANOMALIA, NUM_OFFERTE_FUORI_SOGLIA, NUM_IMP_ESCL_INSUF_GIUST, "  // 32, .., 35
					+ "IMP_NON_ASSOG, TO_CHAR(MODALITA_RIAGGIUDICAZIONE) as MODALITA_RIAGGIUDICAZIONE, DURATA_ACCQUADRO, NUMERO_ATTO, DATA_ATTO, " // 36, .., 40
					+ "TO_CHAR(TIPO_ATTO) as TIPO_ATTO, IVA, IMPORTO_IVA, FLAG_SICUREZZA, COD_STRUMENTO, PROCEDURA_ACC, FLAG_RICH_SUBAPPALTO, VERIFICA_CAMPIONE, FLAG_IMPORTI "  // 41, .., 49
				+ "from W9APPA, W9LOTT where W9APPA.CODGARA=? AND W9APPA.CODLOTT=? AND W9LOTT.CODGARA = W9APPA.CODGARA AND W9LOTT.CODLOTT = W9APPA.CODLOTT", 
				new Object[] { codiceGara, codiceLotto });

			if (vectDatiAggiudicazione != null) {
				SezioneAggiudicazione sezioneAggiudicazione = SezioneAggiudicazione.Factory.newInstance();
				DatiAggiudicazione datiAggiudicazione = DatiAggiudicazione.Factory.newInstance();

				int i = 0;
				// i = 0, "DATA_VERB_AGGIUDICAZIONE" column="DATA_VERB_AGGIUDICAZIONE" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setDATAVERBAGGIUDICAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i)));
				}
				i++;
				// i = 1, "IMPORTO_LAVORI" column="IMPORTO_LAVORI"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTOLAVORI(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 2, "IMPORTO_SERVIZI" column="IMPORTO_SERVIZI"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTOFORNITURE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 3, "IMPORTO_FORNITURE" column="IMPORTO_FORNITURE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTOFORNITURE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 4, "IMPORTO_ATTUAZIONE_SICUREZZA" column="IMPORTO_ATTUAZIONE_SICUREZZA"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTOATTUAZIONESICUREZZA(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 5, "IMPORTO_PROGETTAZIONE" column="IMPORTO_PROGETTAZIONE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTOPROGETTAZIONE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 6, "OPERE_URBANIZ_SCOMPUTO" column="OPERE_URBANIZ_SCOMPUTO" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString(); 
					datiAggiudicazione.setOPEREURBANIZSCOMPUTO(convertiFlagSN(flag));
				}
				i++;
				// i = 7, "FLAG_LIVELLO_PROGETTUALE" column="FLAG_LIVELLO_PROGETTUALE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setFLAGLIVELLOPROGETTUALE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString());
				}
				i++;
				// i = 8, "FIN_REGIONALE" column="FIN_REGIONALE" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString(); 
					datiAggiudicazione.setFINREGIONALE(convertiFlagSN(flag));
				}
				i++;
				// i = 9, "IMPORTO_AGGIUDICAZIONE" column="IMPORTO_AGGIUDICAZIONE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTOAGGIUDICAZIONE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 10, "IMPORTO_DISPOSIZIONE" column="IMPORTO_DISPOSIZIONE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTODISPOSIZIONE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 11, "PERC_OFF_AUMENTO" column="PERC_OFF_AUMENTO"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setPERCOFFAUMENTO(new Float(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue()).floatValue());
				}
				i++;
				// i = 12, "IMPORTO_APPALTO" column="IMPORTO_COMPL_APPALTO"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTOAPPALTO(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 13, "REQUISITI_SS" column="REQUISITI_SETT_SPEC"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setREQUISITISS(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString());
				}
				i++;
				// i = 14, "IMPORTO_NETTO_SIC" column="IMPORTO_SUBTOTALE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTONETTOSIC(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 15, "ASTA_ELETTRONICA" column="ASTA_ELETTRONICA" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString(); 
					datiAggiudicazione.setASTAELETTRONICA(convertiFlagSN(flag));
				}
				i++;
				// i = 16, "PERC_RIBASSO_AGG" column="PERC_RIBASSO_AGG"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setPERCRIBASSOAGG(new Float(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue()).floatValue());
				}
				i++;
				// i = 17, "ID_MODO_INDIZIONE" column="ID_MODO_INDIZIONE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIDMODOINDIZIONE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString());
				}
				i++;
				// i = 18, "NUM_IMPRESE_INVITATE" column="NUM_IMPRESE_INVITATE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setNUMIMPRESEINVITATE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).longValue());
				}
				i++;
				// i = 19, "NUM_OFFERTE_AMMESSE" column="NUM_OFFERTE_AMMESSE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setNUMOFFERTEAMMESSE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).longValue());
				}
				i++;
				// i = 20, "PREINFORMAZIONE" column="PREINFORMAZIONE" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString();
					datiAggiudicazione.setPREINFORMAZIONE(convertiFlagSN(flag));
				}
				i++;
				// i = 21, "NUM_IMPRESE_RICHIESTA_INVITO" column="NUM_IMPRESE_RICHIEDENTI"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setNUMIMPRESERICHIESTAINVITO(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).longValue());
				}
				i++;
				// i = 22, "NUM_IMPRESE_OFFERENTI" column="NUM_IMPRESE_OFFERENTI"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setNUMIMPRESEOFFERENTI(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).longValue());
				}
				i++;
				// i = 23, "TERMINE_RIDOTTO" column="TERMINE_RIDOTTO" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString();
					datiAggiudicazione.setTERMINERIDOTTO(convertiFlagSN(flag));
				}
				i++;
				// i = 24, "DATA_SCADENZA_RICHIESTA_INVITO" column="DATA_SCADENZA_RICHIESTA_INVITO" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setDATASCADENZARICHIESTAINVITO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i)));
				}
				i++;
				// i = 25, "DATA_SCADENZA_PRES_OFFERTA" column="DATA_SCADENZA_PRES_OFFERTA" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setDATASCADENZAPRESOFFERTA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i)));
				}
				i++;
				// i = 26, "FLAG_AQ" column="FLAG_ACCORDO_QUADRO" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString(); 
					datiAggiudicazione.setFLAGAQ(convertiFlagSN(flag));
				}
				i++;
				// i = 27, "DATA_INVITO" column="DATA_INVITO" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setDATAINVITO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i)));
				}
				i++;
				// i = 28, "NUM_MANIF_INTERESSE" column="NUM_MANIF_INTERESSE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setNUMMANIFINTERESSE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).longValue());
				}
				i++;
				// i = 29, "DATA_MANIF_INTERESSE" column="DATA_MANIF_INTERESSE" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setDATAMANIFINTERESSE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i)));
				}
				i++;
				// i = 30, "NUM_OFFERTE_ESCLUSE" column="NUM_OFFERTE_ESCLUSE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setNUMOFFERTEESCLUSE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).longValue());
				}
				i++;
				// i = 31, "OFFERTA_MASSIMO" column="OFFERTA_MASSIMO"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setOFFERTAMASSIMO(new Float(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString()).floatValue());
				}
				i++;
				// i = 32, "OFFERTA_MINIMA" column="OFFERTA_MINIMA"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setOFFERTAMINIMA(new Float(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString()).floatValue());
				}
				i++;
				// i = 33, "VAL_SOGLIA_ANOMALIA" column="VAL_SOGLIA_ANOMALIA"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					Double temp = (Double) SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue();
					if (temp.doubleValue() != 0d) {
						datiAggiudicazione.setVALSOGLIAANOMALIA(new Float(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString()).floatValue());
					}
				}
				i++;
					// i = 34, "NUM_OFFERTE_FUORI_SOGLIA" column="NUM_OFFERTE_FUORI_SOGLIA"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setNUMOFFERTEFUORISOGLIA(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).longValue());
				}
				i++;
				// i = 35, "NUM_IMP_ESCL_INSUF_GIUST" column="NUM_IMP_ESCL_INSUF_GIUST"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setNUMIMPESCLINSUFGIUST(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).longValue());
				}
				i++;
				// i = 36, "IMP_NON_ASSOG" column="IMP_NON_ASSOG"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPNONASSOG(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 37, "MODALITA_RIAGGIUDICAZIONE" column="MODALITA_RIAGGIUDICAZIONE"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setMODALITARIAGGIUDICAZIONE(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString());
				}
				i++;
				// i = 38, "DURATA_AQ" column="DURATA_ACCQUADRO"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setDURATAAQ(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).longValue());
				}
				i++;
				// i = 39, "DATA_ATTO" column="DATA_ATTO" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setDATAATTO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i)));
				}
				i++;
				// i = 40, "NUM_ATTO" column="NUMERO_ATTO"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setNUMATTO(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString());
				}
				i++;
				// i = 41, "TIPO_ATTO" column="TIPO_ATTO"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setTIPOATTO(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString());
				}
				i++;
				// i = 42, "PERC_IVA" column="IVA"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setPERCIVA(new Float(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue()).floatValue());
				}
				i++;
				// i = 43, "IMPORTO_IVA" column="IMPORTO_IVA"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setIMPORTOIVA(SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).doubleValue());
				}
				i++;
				// i = 44, "FLAG_SICUREZZA" column="FLAG_SICUREZZA" typeHandler="flagImportiHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setFLAGSICUREZZA(FlagImportiType.Enum.forString(
						SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString()));
				}
				i++;
				// i = 45, "COD_STRUMENTO" column="COD_STRUMENTO" typeHandler="tipoStrumentoHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setCODSTRUMENTO(TipoStrumentoType.Enum.forString(
						SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString()));
				}
				i++;
				// i = 46, "PROCEDURA_ACC" column="PROCEDURA_ACC" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString();
					datiAggiudicazione.setPROCEDURAACC(convertiFlagSN(flag));
				}
				i++;
				// i = 47, "FLAG_RICH_SUBAPPALTO" column="FLAG_RICH_SUBAPPALTO" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString(); 
					datiAggiudicazione.setFLAGRICHSUBAPPALTO(convertiFlagSN(flag));
				}
				i++;
				// i = 48, "VERIFICA_CAMPIONE" column="VERIFICA_CAMPIONE" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString(); 
					datiAggiudicazione.setVERIFICACAMPIONE(convertiFlagSN(flag));
				}
				i++;
				// i = 49, "FLAG_IMPORTI" column="FLAG_IMPORTI" typeHandler="flagImportiHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).getValue() != null) {
					datiAggiudicazione.setFLAGIMPORTI(FlagImportiType.Enum.forString(
						SqlManager.getValueFromVectorParam(vectDatiAggiudicazione, i).toString()));
				}

				sezioneAggiudicazione.setDATIAGGIUDICAZIONE(datiAggiudicazione);

				// Lista finanziamenti
				List<?> listVectFinanziamenti = this.sqlManager.getListVector("select ID_FINANZIAMENTO, IMPORTO_FINANZIAMENTO from W9FINA where CODGARA=? AND CODLOTT=? order by ID_FINANZIAMENTO asc",
					new Object[] { codiceGara, codiceLotto });
				if (listVectFinanziamenti != null && listVectFinanziamenti.size() > 0) {
					ListaFinanziamenti listaFinanziamenti = ListaFinanziamenti.Factory.newInstance();

					Finanziamenti[] arrayDatiFinanziamento = new Finanziamenti[listVectFinanziamenti.size()];
					for (int j=0; j < listVectFinanziamenti.size(); j++) {
						Finanziamenti datiFinanziamento = 	Finanziamenti.Factory.newInstance();
						Vector<?> vectFinanziamento = (Vector<?>) listVectFinanziamenti.get(j);

						// "IMPORTO_FINANZIAMENTO" column="IMPORTO_FINANZIAMENTO"
						if (SqlManager.getValueFromVectorParam(vectFinanziamento, 1).getValue() != null) {
							datiFinanziamento.setIMPORTOFINANZIAMENTO(SqlManager.getValueFromVectorParam(vectFinanziamento, 1).doubleValue());
						}
						
						// "ID_FINANZIAMENTO" column="ID_FINANZIAMENTO" typeHandler="flagTipoFinanziamentoHandler"
						if (SqlManager.getValueFromVectorParam(vectFinanziamento, 0).getValue() != null) {
							datiFinanziamento.setIDFINANZIAMENTO(TipoFinanziamentoType.Enum.forString(
								SqlManager.getValueFromVectorParam(vectFinanziamento, 0).toString()));
						}
						arrayDatiFinanziamento[j] = datiFinanziamento;
					}
					listaFinanziamenti.setIdFinanziamentiArray(arrayDatiFinanziamento);
					sezioneAggiudicazione.setLISTAFINANZIAMENTI(listaFinanziamenti);
				}

				List<?> listVectDatiCategorie = this.sqlManager.getListVector(
					"select C2.CODAVCP as ID_CATEGORIA, CLASCAT, '1' as PREVALENTE, '2' as SCORPORABILE, '2' as SUBAPPALTABILE "
						+ "from W9LOTT LEFT JOIN W9CODICI_AVCP C2 ON (C2.TABCOD = 'W3z03' and C2.CODSITAT = W9LOTT.ID_CATEGORIA_PREVALENTE) "
						+ "where W9LOTT.CODGARA=? and W9LOTT.CODLOTT=? "
					+ "union "
					+ "select C4.CODAVCP as ID_CATEGORIA, W9LOTTCATE.CLASCAT, '2' as PREVALENTE, W9LOTTCATE.SCORPORABILE as SCORPORABILE, W9LOTTCATE.SUBAPPALTABILE "
						+ "from W9LOTTCATE LEFT JOIN W9CODICI_AVCP C4 ON (C4.TABCOD = 'W3z03' and C4.CODSITAT = W9LOTTCATE.CATEGORIA) "
						+ "where W9LOTTCATE.CODGARA=? and W9LOTTCATE.CODLOTT=?", 
					new Object[] { codiceGara, codiceLotto, codiceGara, codiceLotto });
				if (listVectDatiCategorie != null && listVectDatiCategorie.size() > 0) {
					ListaCategorie listaCategorie = ListaCategorie.Factory.newInstance();
					Categorie[] arrayDatiCategorie = new Categorie[listVectDatiCategorie.size()];

					for (int j=0; j < listVectDatiCategorie.size(); j++) {
							Categorie datiCategoria = 	Categorie.Factory.newInstance();
						Vector<?> vectDatiCategorie = (Vector<?>) listVectDatiCategorie.get(j);

						// i = 0, "ID_CATEGORIA" column="ID_CATEGORIA" typeHandler="categoriaHandler"
						if (SqlManager.getValueFromVectorParam(vectDatiCategorie, 0).getValue() != null) {
							datiCategoria.setIDCATEGORIA(	CategoriaType.Enum.forString(
								SqlManager.getValueFromVectorParam(vectDatiCategorie, 0).toString()));
						}
						// i = 1, "CLASSE_IMPORTO" column="CLASCAT" typeHandler="classeImportoHandler"
						if (SqlManager.getValueFromVectorParam(vectDatiCategorie, 1).getValue() != null) {
							datiCategoria.setCLASSEIMPORTO(	ClasseImportoType.Enum.forString(
								SqlManager.getValueFromVectorParam(vectDatiCategorie, 1).toString()));
						}
						// i = 2, "PREVALENTE" column="PREVALENTE" typeHandler="flagSNHandler"
						if (SqlManager.getValueFromVectorParam(vectDatiCategorie, 2).getValue() != null) {
							String flag = SqlManager.getValueFromVectorParam(vectDatiCategorie, 2).toString();
							datiCategoria.setPREVALENTE(convertiFlagSN(flag));
						}
						// i = 3, "SCORPORABILE" column="SCORPORABILE" typeHandler="flagSNHandler"
						if (SqlManager.getValueFromVectorParam(vectDatiCategorie, 3).getValue() != null) {
							String flag = SqlManager.getValueFromVectorParam(vectDatiCategorie, 3).toString();
							datiCategoria.setSCORPORABILE(convertiFlagSN(flag));
						}
						// i = 4, "SUBAPPALTABILE" column="SUBAPPALTABILE" typeHandler="flagSNHandler"
						if (SqlManager.getValueFromVectorParam(vectDatiCategorie, 4).getValue() != null) {
							String flag = SqlManager.getValueFromVectorParam(vectDatiCategorie, 4).toString();
							datiCategoria.setSUBAPPALTABILE(convertiFlagSN(flag));
						}
						arrayDatiCategorie[j] = datiCategoria;
					}
					listaCategorie.setIdCategorieArray(arrayDatiCategorie);
					sezioneAggiudicazione.setLISTACATEGORIE(listaCategorie);
				}

				List<?> listVectCampiChiaveAggiudicatari = this.sqlManager.getListVector(
					"select CODGARA, CODLOTT, NUM_APPA, NUM_AGGI, CODIMP, CODIMP_AUSILIARIA from W9AGGI "
					+ "where CODGARA=? and CODLOTT=? order by NUM_APPA asc, NUM_AGGI asc",
					new Object[] { codiceGara, codiceLotto } );
        
				if (listVectCampiChiaveAggiudicatari != null && listVectCampiChiaveAggiudicatari.size() > 0) {

					ListaAggiudicatari listaAggiudicatari = ListaAggiudicatari.Factory.newInstance();
					List<	Aggiudicatario> arrayListAggiudicatari = new ArrayList<	Aggiudicatario>();
					for (int j=0; j < listVectCampiChiaveAggiudicatari.size(); j++) {
						Vector<?> vectCampiChiaveAggiudicatari = (Vector<?>) listVectCampiChiaveAggiudicatari.get(j);
						// i = 0, "codiceGara" column="CODGARA"
						// i = 1, "codiceLotto" column="CODLOTT"
						// i = 2, "numAppalto" column="NUM_APPA"
						Long numeroAppalto = new Long(SqlManager.getValueFromVectorParam(vectCampiChiaveAggiudicatari, 2).longValue());
						// i = 3, "numeroAggiudicataria" column="NUM_AGGI"
						Long numeroAggiudicataria = new Long(SqlManager.getValueFromVectorParam(vectCampiChiaveAggiudicatari, 3).longValue());

						String codiceImpresa = null;
						String codiceImpresaAvvallimento = null;
						// i = 4, "codiceImpresa" column="CODIMP"
						if (SqlManager.getValueFromVectorParam(vectCampiChiaveAggiudicatari, 4).getValue() != null) {
							codiceImpresa = SqlManager.getValueFromVectorParam(vectCampiChiaveAggiudicatari, 4).toString();
						}
						// i = 5, "codiceImpresaAusiliaria" column="CODIMP_AUSILIARIA"
						if (SqlManager.getValueFromVectorParam(vectCampiChiaveAggiudicatari, 5).getValue() != null) {
							codiceImpresaAvvallimento = SqlManager.getValueFromVectorParam(vectCampiChiaveAggiudicatari, 5).toString();
						}

							Aggiudicatario aggiudicatario = 	Aggiudicatario.Factory.newInstance(); 

						Vector<?> vectDatiAggiudicataria = this.sqlManager.getVector(
							"select TO_CHAR(ID_TIPOAGG) as ID_TIPOAGG, TO_CHAR(RUOLO) as RUOLO, FLAG_AVVALIMENTO, ID_GRUPPO from W9AGGI "
							+ "where CODGARA=? and CODLOTT=? and NUM_APPA=? AND NUM_AGGI=?", 
							new Object[] { codiceGara, codiceLotto, numeroAppalto, numeroAggiudicataria });

						// i = 0, "TIPOLOGIA_SOGGETTO" column="ID_TIPOAGG"
						if (SqlManager.getValueFromVectorParam(vectDatiAggiudicataria, 0).getValue() != null) {
							aggiudicatario.setTIPOLOGIASOGGETTO(SqlManager.getValueFromVectorParam(vectDatiAggiudicataria, 0).toString());
						}
						// i = 1, "RUOLO" column="RUOLO"
						if (SqlManager.getValueFromVectorParam(vectDatiAggiudicataria, 1).getValue() != null) {
							aggiudicatario.setRUOLO(SqlManager.getValueFromVectorParam(vectDatiAggiudicataria, 1).toString());
						}
						// i = 2, "FLAG_AVVALIMENTO" column="FLAG_AVVALIMENTO"
						if (SqlManager.getValueFromVectorParam(vectDatiAggiudicataria, 2).getValue() != null) {
							aggiudicatario.setFLAGAVVALIMENTO(SqlManager.getValueFromVectorParam(vectDatiAggiudicataria, 2).toString());
						}
						// i = 3, "ID_GRUPPO" column="ID_GRUPPO"
						if (SqlManager.getValueFromVectorParam(vectDatiAggiudicataria, 3).getValue() != null) {
							aggiudicatario.setIDGRUPPO(SqlManager.getValueFromVectorParam(vectDatiAggiudicataria, 3).longValue());
						}

					// Dati Aggiudicatario
					if (StringUtils.isNotEmpty(codiceImpresa)) {
							DatiAggiudicatari datiAggiudicatario = 	DatiAggiudicatari.Factory.newInstance();
						boolean isAggiudicatarioValorizzato = this.getDatiAggiudicatario(codiceImpresa, datiAggiudicatario);
						if (isAggiudicatarioValorizzato) {
							this.getLegaleRappresentante(codiceImpresa, datiAggiudicatario);
						}
						aggiudicatario.setAggiudicatario(datiAggiudicatario);
					}

					// Dati Aggiudicatario avvallimento
					if (StringUtils.isNotEmpty(codiceImpresaAvvallimento)) {
							DatiAggiudicatari datiAggiudicatario = 	DatiAggiudicatari.Factory.newInstance();
						boolean isAggiudicatarioValorizzato = this.getDatiAggiudicatario(codiceImpresaAvvallimento, datiAggiudicatario);
						if (isAggiudicatarioValorizzato) {
							this.getLegaleRappresentante(codiceImpresaAvvallimento, datiAggiudicatario);
						}
							aggiudicatario.setAggiudicatarioAvv(datiAggiudicatario);
						}
						arrayListAggiudicatari.add(aggiudicatario);
					}

					if (arrayListAggiudicatari != null && arrayListAggiudicatari.size() > 0) {
						listaAggiudicatari.setIdAggiudicatariArray(arrayListAggiudicatari.toArray(new 	Aggiudicatario[0]));
						sezioneAggiudicazione.setLISTAAGGIUDICATARI(listaAggiudicatari);
					}

					// Lista degli incarichi professionali (Soggetti estesi)
						ListaDatiSoggettiEstesi listaDatiSoggettiEstesi = 	ListaDatiSoggettiEstesi.Factory.newInstance();
					this.setDatiSoggettiEstesiAggiudicazione(codiceGara, codiceLotto, listaDatiSoggettiEstesi);
					if (listaDatiSoggettiEstesi != null && listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray() != null && listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray().length > 0) {
						sezioneAggiudicazione.setLISTADATISOGGETTIESTESIAGG(listaDatiSoggettiEstesi);
					}

					// Dati Soggetti Estesi Aggiudicazione
					List<?> listVectCodiciTecnici = this.sqlManager.getListVector(
						"select CODTEC from W9INCA where CODGARA=? AND CODLOTT=? AND SEZIONE in ('PA','RA','RE','RS','RQ') and CODTEC is not null order by ID_RUOLO asc, SEZIONE asc",
							new Object[] { codiceGara, codiceLotto } );

					if (listVectCodiciTecnici != null && listVectCodiciTecnici.size() > 0) {
						for (int j=0; j < listVectCodiciTecnici.size(); j++) {
							Vector<?> vectCodiciTecnici = (Vector<?>) listVectCodiciTecnici.get(j);

							if (SqlManager.getValueFromVectorParam(vectCodiciTecnici, 0).getValue() != null) {
								String codiceTecnico = ((JdbcParametro) vectCodiciTecnici.get(0)).toString();
							
								Long countTecni = (Long) this.sqlManager.getObject("select count(*) from TECNI where CODTEC=?", new Object[] { codiceTecnico } );
								if (countTecni > 0) {
										DatiResponsabile datiResponsabile = 	DatiResponsabile.Factory.newInstance();
									this.getTecnico(codiceTecnico, datiResponsabile);
									sezioneAggiudicazione.getLISTADATISOGGETTIESTESIAGG().getIdDatiSoggettiEstesiArray()[j].setResponsabile(datiResponsabile);
								}
							}
						}
					}

						Pubblicazione pubblicazioneAggiudicazione = 	Pubblicazione.Factory.newInstance();          
					this.setPubblicazioni(codiceGara, codiceLotto, pubblicazioneAggiudicazione);
					sezioneAggiudicazione.setPUBBLICAZIONEAGGIUDICAZIONE(pubblicazioneAggiudicazione);
					datiGeneraliContratto.setSEZIONEAGGIUDICAZIONE(sezioneAggiudicazione);
				}

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneAggiudicazione: fine metodo");
		}
	}


	private void setPubblicazioni(Long codiceGara, Long codiceLotto,
			Pubblicazione pubblicazioneAggiudicazione)
			throws SQLException, GestoreException {
	
		if (logger.isDebugEnabled()) {
		logger.debug("setPubblicazioni: inizio metodo");
		}

		Long countPubblicazioni = (Long) this.sqlManager.getObject(
			"select count(*) from W9PUBB where CODGARA=? and CODLOTT=? and NUM_APPA=1 and NUM_PUBB=1 order by NUM_PUBB asc", 
			new Object[] { codiceGara, codiceLotto } );
		if (countPubblicazioni > 0) {
			Vector<?> vectDatiPubblicazioni = this.sqlManager.getVector(
				"select DATA_GUCE, DATA_GURI, DATA_ALBO, QUOTIDIANI_NAZ, QUOTIDIANI_REG, PROFILO_COMMITTENTE, SITO_MINISTERO_INF_TRASP, SITO_OSSERVATORIO_CP " 
				+ "from W9PUBB where CODGARA=? and CODLOTT=? and NUM_APPA=1 and NUM_PUBB=1 order by NUM_PUBB asc",
				new Object[] { codiceGara, codiceLotto } );
 
			// i = 0, "DATA_GUCE" column="DATA_GUCE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 0).getValue() != null) {
				pubblicazioneAggiudicazione.setDATAGUCE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 0)));
			}
			// i = 1, "DATA_GURI" column="DATA_GURI" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 1).getValue() != null) {
				pubblicazioneAggiudicazione.setDATAGURI(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 1)));
			}
			// i = 2, "DATA_ALBO" column="DATA_ALBO" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 2).getValue() != null) {
				pubblicazioneAggiudicazione.setDATAALBO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 2)));
			}
			// i = 3, "QUOTIDIANI_NAZ" column="QUOTIDIANI_NAZ"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 3).getValue() != null) {
				pubblicazioneAggiudicazione.setQUOTIDIANINAZ(SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 3).longValue());
			}
			// i = 4, "QUOTIDIANI_REG" column="QUOTIDIANI_REG"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 4).getValue() != null) {
				pubblicazioneAggiudicazione.setQUOTIDIANIREG(SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 3).longValue());
			}
			// i = 5, "PROFILO_COMMITTENTE" column="PROFILO_COMMITTENTE" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 5).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 5).toString();
				pubblicazioneAggiudicazione.setPROFILOCOMMITTENTE(convertiFlagSN(flag));
			}
			// i = 6, "SITO_MINISTERO_INF_TRASP" column="SITO_MINISTERO_INF_TRASP" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 6).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 6).toString();
				pubblicazioneAggiudicazione.setSITOMINISTEROINFTRASP(convertiFlagSN(flag));
			}
			// i = 7, "SITO_OSSERVATORIO_CP" column="SITO_OSSERVATORIO_CP" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 7).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 7).toString();
				pubblicazioneAggiudicazione.setSITOOSSERVATORIOCP(convertiFlagSN(flag));
			}
		} else {
			pubblicazioneAggiudicazione.setPROFILOCOMMITTENTE(	FlagSNType.Enum.forString("N"));
			pubblicazioneAggiudicazione.setSITOMINISTEROINFTRASP(	FlagSNType.Enum.forString("N"));
			pubblicazioneAggiudicazione.setSITOOSSERVATORIOCP(	FlagSNType.Enum.forString("N"));
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("setPubblicazioni: fine metodo");
		}
	}


	private void setPubblicazioniPues(Long codiceGara, Long codiceLotto,
			Pubblicazione pubblicazionePues)
			throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setPubblicazioniPues: inizio metodo");
		}

		Long countPubblicazioni = (Long) this.sqlManager.getObject(
	      	"select count(*) from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=1 order by NUM_PUES asc", 
	      	new Object[] { codiceGara, codiceLotto } );
	  if (countPubblicazioni > 0) {
	    Vector<?> vectDatiPubblicazioni = this.sqlManager.getVector(
	    	"select DATA_GUCE, DATA_GURI, DATA_ALBO, QUOTIDIANI_NAZ, QUOTIDIANI_REG, PROFILO_COMMITTENTE, SITO_MINISTERO_INF_TRASP, SITO_OSSERVATORIO_CP "
	    	+ "from W9PUES where CODGARA=? and CODLOTT=? and NUM_INIZ=1 order by NUM_PUES asc",
	  		new Object[] { codiceGara, codiceLotto } );
	    
			// i = 0, "DATA_GUCE" column="DATA_GUCE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 0).getValue() != null) {
				pubblicazionePues.setDATAGUCE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 0)));
			}
			// i = 1, "DATA_GURI" column="DATA_GURI" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 1).getValue() != null) {
				pubblicazionePues.setDATAGURI(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 1)));
			}
			// i = 2, "DATA_ALBO" column="DATA_ALBO" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 2).getValue() != null) {
				pubblicazionePues.setDATAALBO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 2)));
			}
			// i = 3, "QUOTIDIANI_NAZ" column="QUOTIDIANI_NAZ"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 3).getValue() != null) {
				pubblicazionePues.setQUOTIDIANINAZ(SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 3).longValue());
			}
			// i = 4, "QUOTIDIANI_REG" column="QUOTIDIANI_REG"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 4).getValue() != null) {
				pubblicazionePues.setQUOTIDIANIREG(SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 3).longValue());
			}
			// i = 5, "PROFILO_COMMITTENTE" column="PROFILO_COMMITTENTE" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 5).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 5).toString();
				pubblicazionePues.setPROFILOCOMMITTENTE(convertiFlagSN(flag));
			}
			// i = 6, "SITO_MINISTERO_INF_TRASP" column="SITO_MINISTERO_INF_TRASP" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 6).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 6).toString();
				pubblicazionePues.setSITOMINISTEROINFTRASP(convertiFlagSN(flag));
			}
			// i = 7, "SITO_OSSERVATORIO_CP" column="SITO_OSSERVATORIO_CP" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 7).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiPubblicazioni, 7).toString();
				pubblicazionePues.setSITOOSSERVATORIOCP(convertiFlagSN(flag));
			}
		} else {
			pubblicazionePues.setPROFILOCOMMITTENTE(	FlagSNType.Enum.forString("N"));
			pubblicazionePues.setSITOMINISTEROINFTRASP(	FlagSNType.Enum.forString("N"));
			pubblicazionePues.setSITOOSSERVATORIOCP(	FlagSNType.Enum.forString("N"));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setPubblicazioniPues: fine metodo");
		}
	}


	private void setSezioneInizio(Long codiceGara, Long codiceLotto, 	DatiGeneraliContratto datiGeneraliContratto) throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneInizio: inizio metodo");
		}

		long numeroInizio = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA) + 
			this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO) +
				this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.STIPULA_ACCORDO_QUADRO);

		if (numeroInizio > 0) {

			Vector<?> vectDatiInizio = this.sqlManager.getVector(
				"select DATA_STIPULA, FLAG_RISERVA, DATA_VERB_INIZIO, DATA_TERMINE, DATA_ESECUTIVITA, IMPORTO_CAUZIONE, DATA_INI_PROG_ESEC, "
					+ "DATA_APP_PROG_ESEC, FLAG_FRAZIONATA, DATA_VERBALE_CONS, DATA_VERBALE_DEF "
				+ "from W9INIZ where CODGARA=? and CODLOTT=? and NUM_INIZ=1", new Object[] { codiceGara, codiceLotto });
				SezioneInizio sezioneInizio = 	SezioneInizio.Factory.newInstance();
				DatiInizio datiInizio = 	DatiInizio.Factory.newInstance();

			// i = 0, "DATA_STIPULA" column="DATA_STIPULA" typeHandler="calendarHandler
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 0).getValue() != null) {
				datiInizio.setDATASTIPULA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiInizio, 0)));
			}
			// i = 1, "FLAG_RISERVA" column="FLAG_RISERVA" typeHandler="flagSNHandler
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 1).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiInizio, 1).toString();
				datiInizio.setFLAGRISERVA(convertiFlagSN(flag));
			}
			// i = 2, "DATA_VERB_INIZIO" column="DATA_VERB_INIZIO" typeHandler="calendarHandler
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 2).getValue() != null) {
				datiInizio.setDATAVERBINIZIO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiInizio, 2)));
			}
			// i = 3, "DATA_TERMINE" column="DATA_TERMINE" typeHandler="calendarHandler
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 3).getValue() != null) {
				datiInizio.setDATATERMINE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiInizio, 3)));
			}
			// i = 4, "DATA_ESECUTIVITA" column="DATA_ESECUTIVITA" typeHandler="calendarHandler
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 4).getValue() != null) {
				datiInizio.setDATAESECUTIVITA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiInizio, 4)));
			}
			// i = 5, "IMPORTO_CAUZIONE" column="IMPORTO_CAUZIONE
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 0).getValue() != null) {
				datiInizio.setIMPORTOCAUZIONE(SqlManager.getValueFromVectorParam(vectDatiInizio, 5).doubleValue());
			}
			// i = 6, "DATA_INI_PROG_ESEC" column="DATA_INI_PROG_ESEC" typeHandler="calendarHandler
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 6).getValue() != null) {
				datiInizio.setDATAINIPROGESEC(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					  SqlManager.getValueFromVectorParam(vectDatiInizio, 6)));
			}
			// i = 7, "DATA_APP_PROG_ESEC" column="DATA_APP_PROG_ESEC" typeHandler="calendarHandler
			  if (SqlManager.getValueFromVectorParam(vectDatiInizio, 7).getValue() != null) {
				datiInizio.setDATAAPPPROGESEC(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					  SqlManager.getValueFromVectorParam(vectDatiInizio, 7)));
			}
			// i = 8, "FLAG_FRAZIONATA" column="FLAG_FRAZIONATA" typeHandler="flagSNHandler
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 8).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiInizio, 8).toString();
				datiInizio.setFLAGFRAZIONATA(convertiFlagSN(flag));
			}
			// i = 9, "DATA_VERB_PRIMA_CONSEGNA" column="DATA_VERBALE_CONS" typeHandler="calendarHandler
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 9).getValue() != null) {
				datiInizio.setDATAVERBPRIMACONSEGNA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					  SqlManager.getValueFromVectorParam(vectDatiInizio, 9)));
			}
			// i = 10, "DATA_VERBALE_DEF" column="DATA_VERBALE_DEF" typeHandler="calendarHandler
			if (SqlManager.getValueFromVectorParam(vectDatiInizio, 10).getValue() != null) {
				datiInizio.setDATAVERBALEDEF(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiInizio, 10)));
			}
			sezioneInizio.setDATIINIZIO(datiInizio);

				Pubblicazione pubblicazioneInizio = 	Pubblicazione.Factory.newInstance();          
			this.setPubblicazioniPues(codiceGara, codiceLotto, pubblicazioneInizio);
      
			if (pubblicazioneInizio != null) {
				sezioneInizio.setPUBBLICAZIONEINIZIO(pubblicazioneInizio);
			}

				ListaDatiSoggettiEstesi listaDatiSoggettiEstesi = 	ListaDatiSoggettiEstesi.Factory.newInstance();
			this.setDatiSoggettiEstesiInizio(codiceGara, codiceLotto, listaDatiSoggettiEstesi);
			if (listaDatiSoggettiEstesi != null && listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray() != null && listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray().length > 0) {
				sezioneInizio.setLISTADATISOGGETTIESTESIINIZIO(listaDatiSoggettiEstesi);

				// Dati Soggetti Estesi Inzio
				List<?> listVectCodiciTecnici = this.sqlManager.getListVector(
					"select CODTEC from W9INCA where CODGARA=? AND CODLOTT=? AND SEZIONE in ('IN') and CODTEC is not null order by ID_RUOLO asc, SEZIONE asc",
						new Object[] { codiceGara, codiceLotto } );

				if (listVectCodiciTecnici != null && listVectCodiciTecnici.size() > 0) {
					for (int j=0; j < listVectCodiciTecnici.size(); j++) {
						Vector<?> vectCodiciTecnici = (Vector<?>) listVectCodiciTecnici.get(j);

						if (SqlManager.getValueFromVectorParam(vectCodiciTecnici, 0).getValue() != null) {
							String codiceTecnico = ((JdbcParametro) vectCodiciTecnici.get(0)).toString();

							Long countTecni = (Long) this.sqlManager.getObject("select count(*) from TECNI where CODTEC=?", new Object[] { codiceTecnico } );
							if (countTecni > 0) {
									DatiResponsabile datiResponsabile = 	DatiResponsabile.Factory.newInstance();
								this.getTecnico(codiceTecnico, datiResponsabile);
								sezioneInizio.getLISTADATISOGGETTIESTESIINIZIO().getIdDatiSoggettiEstesiArray()[j].setResponsabile(datiResponsabile);
							}
						}
					}
				}
			}

			datiGeneraliContratto.setSEZIONEINIZIO(sezioneInizio);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneInizio: fine metodo");
		}
	}


	private void setSezioneSAL(Long codiceGara, Long codiceLotto, 
			DatiGeneraliContratto datiGeneraliContratto) 
			throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneSAL: inizio metodo");
		}

		// Se esiste almeno un SAL
		long numeroSAL = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA);

		if (numeroSAL > 0) {
			SezioneSAL sezioneSAL = SezioneSAL.Factory.newInstance();
			ListaDatiAvanzamento listaDatiAvanzamento = ListaDatiAvanzamento.Factory.newInstance();
			List<DatiAvanzamento> arrayDatiAvanzamento = new ArrayList<	DatiAvanzamento>();

			List<?> listVectDatiAvanzamneto = this.sqlManager.getListVector(
				"select DATA_CERTIFICATO, IMPORTO_CERTIFICATO, FLAG_RITARDO, NUM_GIORNI_SCOST, "
					+ "NUM_GIORNI_PROROGA, TO_CHAR(FLAG_PAGAMENTO) as FLAG_PAGAMENTO, DATA_ANTICIPAZIONE, IMPORTO_ANTICIPAZIONE, "
					+ "DATA_RAGGIUNGIMENTO, IMPORTO_SAL, DENOM_AVANZAMENTO, SUBAPPALTI "
					+ "from W9AVAN where CODGARA=? and CODLOTT=? order by NUM_AVAN asc",
					new Object[] { codiceGara, codiceLotto });

			if (listVectDatiAvanzamneto != null && listVectDatiAvanzamneto.size() > 0) {
				for (int j=0; j < listVectDatiAvanzamneto.size(); j++) {
					DatiAvanzamento datiAvanzamento = 	DatiAvanzamento.Factory.newInstance();
					Vector<?> vectDatiAvanzamento = (Vector<?>) listVectDatiAvanzamneto.get(j);

					// i = 0, "DATA_CERTIFICATO" column="DATA_CERTIFICATO" typeHandler="calendarHandler
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 0).getValue() != null) {
						datiAvanzamento.setDATACERTIFICATO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 0)));
					}
					// i = 1, "IMPORTO_CERTIFICATO" column="IMPORTO_CERTIFICATO
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 1).getValue() != null) {
						datiAvanzamento.setIMPORTOCERTIFICATO(SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 1).doubleValue());
					}
					// i = 2, "FLAG_RITARDO" column="FLAG_RITARDO" typeHandler="flagRitardoHandler
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 2).getValue() != null) {
						datiAvanzamento.setFLAGRITARDO(	FlagRitardoType.Enum.forString(
							SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 2).toString()));
					}
					// i = 3, "NUM_GIORNI_SCOST" column="NUM_GIORNI_SCOST"
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 3).getValue() != null) {
						datiAvanzamento.setNUMGIORNISCOST(SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 3).longValue());
					}
					// i = 4, "NUM_GIORNI_PROROGA" column="NUM_GIORNI_PROROGA"
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 4).getValue() != null) {
						datiAvanzamento.setNUMGIORNIPROROGA(SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 4).longValue());
					}
					// i = 5, "FLAG_PAGAMENTO" column="FLAG_PAGAMENTO
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 5).getValue() != null) {
						datiAvanzamento.setFLAGPAGAMENTO(SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 5).toString());
					}
					// i = 6, "DATA_ANTICIPAZIONE" column="DATA_ANTICIPAZIONE" typeHandler="calendarHandler
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 6).getValue() != null) {
						datiAvanzamento.setDATAANTICIPAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 6)));
					}
					// i = 7, "IMPORTO_ANTICIPAZIONE" column="IMPORTO_ANTICIPAZIONE
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 7).getValue() != null) {
						datiAvanzamento.setIMPORTOANTICIPAZIONE(SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 7).doubleValue());
					}
					// i = 8, "DATA_RAGGIUNGIMENTO" column="DATA_RAGGIUNGIMENTO" typeHandler="calendarHandler
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 8).getValue() != null) {
						datiAvanzamento.setDATARAGGIUNGIMENTO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 8)));
					}
					// i = 9, "IMPORTO_SAL" column="IMPORTO_SAL
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 9).getValue() != null) {
						datiAvanzamento.setIMPORTOSAL(SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 9).doubleValue());
					}
					// i = 10, "DENOMINAZIONE" column="DENOM_AVANZAMENTO
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 10).getValue() != null) {
						datiAvanzamento.setDENOMINAZIONE(SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 10).toString());
					}
					// i = 11, "SUBAPPALTI" column="SUBAPPALTI" typeHandler="flagSNHandler
					if (SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 11).getValue() != null) {
						String flag = SqlManager.getValueFromVectorParam(vectDatiAvanzamento, 11).toString();
						datiAvanzamento.setSUBAPPALTI(convertiFlagSN(flag));
					}
					arrayDatiAvanzamento.add(datiAvanzamento);
				}
				listaDatiAvanzamento.setIdDatiAvanzamentoArray(arrayDatiAvanzamento.toArray(new 	DatiAvanzamento[0]));
				sezioneSAL.setDATISAL(listaDatiAvanzamento);
			}
			datiGeneraliContratto.setSEZIONESAL(sezioneSAL);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneSAL: fine metodo");
		}
	}


	private void setSezioneVarianti(Long codiceGara, Long codiceLotto, 
			DatiGeneraliContratto datiGeneraliContratto)
  			throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneVarianti: inizio metodo");
		}

		// Se esiste almeno una variante
		long numeroVarianti = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.VARIANTE_CONTRATTO);
		if (numeroVarianti > 0) {

			List<?> listVectNumeroVarianti = this.sqlManager.getListVector("select NUM_VARI from W9VARI where CODGARA=? and CODLOTT=? order by NUM_VARI asc", 
				new Object[] { codiceGara, codiceLotto });
			if (listVectNumeroVarianti != null && listVectNumeroVarianti.size() > 0) {

				SezioneVarianti sezioneVarianti = SezioneVarianti.Factory.newInstance();	
				ListaDatiVariante listaDatiVarianti = ListaDatiVariante.Factory.newInstance();
				List<DatiVariante> arrayDatiVarianti = new ArrayList<DatiVariante>();

				for (int j=0; j < listVectNumeroVarianti.size(); j++) {
					Vector<?> vectNumeroVarianti = (Vector<?>) listVectNumeroVarianti.get(j);

					Long numeroVariante = new Long(SqlManager.getValueFromVectorParam(vectNumeroVarianti, 0).longValue());
					Vector<?> vectDatiVarianti = this.sqlManager.getVector(
						"select FLAG_VARIANTE, QUINTO_OBBLIGO, FLAG_IMPORTI, FLAG_SICUREZZA, DATA_VERB_APPR, ALTRE_MOTIVAZIONI, "
							+ "IMP_RIDET_LAVORI, IMP_RIDET_SERVIZI, IMP_RIDET_FORNIT, IMP_SICUREZZA, IMP_PROGETTAZIONE, IMP_DISPOSIZIONE, "
							+ "DATA_ATTO_AGGIUNTIVO, NUM_GIORNI_PROROGA, IMP_SUBTOTALE, IMP_COMPL_APPALTO, IMP_COMPL_INTERVENTO "
						+ "from W9VARI where CODGARA=? and CODLOTT=? and NUM_VARI=? order by NUM_VARI asc",
						new Object[] { codiceGara, codiceLotto, numeroVariante });

					DatiVariante datiVariante = 	DatiVariante.Factory.newInstance();

					// i = 0, "FLAG_VARIANTE" column="FLAG_VARIANTE" typeHandler="flagVarianteHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 0).getValue() != null) {
						datiVariante.setFLAGVARIANTE(FlagVarianteType.Enum.forString(
							SqlManager.getValueFromVectorParam(vectDatiVarianti, 0).toString()));
					 }
					// i = 1, "QUINTO_OBBLIGO" column="QUINTO_OBBLIGO" typeHandler="flagSNHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 1).getValue() != null) {
						String flag = SqlManager.getValueFromVectorParam(vectDatiVarianti, 1).toString();
						datiVariante.setQUINTOOBBLIGO(convertiFlagSN(flag));
					}
					// i = 2, "FLAG_IMPORTI" column="FLAG_IMPORTI" typeHandler="flagImportiHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 2).getValue() != null) {
						datiVariante.setFLAGIMPORTI(	FlagImportiType.Enum.forString(
							SqlManager.getValueFromVectorParam(vectDatiVarianti, 2).toString()));
					}
					// i = 3, "FLAG_SICUREZZA" column="FLAG_SICUREZZA" typeHandler="flagImportiHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 3).getValue() != null) {
						datiVariante.setFLAGSICUREZZA(	FlagImportiType.Enum.forString(
							SqlManager.getValueFromVectorParam(vectDatiVarianti, 3).toString()));
					}
					// i = 4, "DATA_VERB_APPR" column="DATA_VERB_APPR" typeHandler="calendarHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 4).getValue() != null) {
						datiVariante.setDATAVERBAPPR(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiVarianti, 4)));
					}
					// i = 5, "ALTRE_MOTIVAZIONI" column="ALTRE_MOTIVAZIONI"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 5).getValue() != null) {
						datiVariante.setALTREMOTIVAZIONI(SqlManager.getValueFromVectorParam(vectDatiVarianti, 5).toString());
					}
					// i = 6, "IMP_RIDET_LAVORI" column="IMP_RIDET_LAVORI"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 6).getValue() != null) {
						datiVariante.setIMPRIDETLAVORI(SqlManager.getValueFromVectorParam(vectDatiVarianti, 6).doubleValue());
					}
					// i = 7, "IMP_RIDET_SERVIZI" column="IMP_RIDET_SERVIZI"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 7).getValue() != null) {
						datiVariante.setIMPRIDETSERVIZI(SqlManager.getValueFromVectorParam(vectDatiVarianti, 7).doubleValue());
					}
					// i = 8, "IMP_RIDET_FORNIT" column="IMP_RIDET_FORNIT"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 8).getValue() != null) {
						datiVariante.setIMPRIDETFORNIT(SqlManager.getValueFromVectorParam(vectDatiVarianti, 8).doubleValue());
					}
					// i = 9, "IMP_SICUREZZA" column="IMP_SICUREZZA"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 9).getValue() != null) {
						datiVariante.setIMPSICUREZZA(SqlManager.getValueFromVectorParam(vectDatiVarianti, 9).doubleValue());
					}
					// i = 10, "IMP_PROGETTAZIONE" column="IMP_PROGETTAZIONE"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 10).getValue() != null) {
						datiVariante.setIMPPROGETTAZIONE(SqlManager.getValueFromVectorParam(vectDatiVarianti, 10).doubleValue());
					}
					// i = 11, "IMP_DISPOSIZIONE" column="IMP_DISPOSIZIONE"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 11).getValue() != null) {
						datiVariante.setIMPDISPOSIZIONE(SqlManager.getValueFromVectorParam(vectDatiVarianti, 11).doubleValue());
					}
					// i = 12, "DATA_ATTO_AGGIUNTIVO" column="DATA_ATTO_AGGIUNTIVO" typeHandler="calendarHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 12).getValue() != null) {
						datiVariante.setDATAATTOAGGIUNTIVO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiVarianti, 12)));
					}
					// i = 13, "NUM_GIORNI_PROROGA" column="NUM_GIORNI_PROROGA"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 13).getValue() != null) {
						datiVariante.setNUMGIORNIPROROGA(SqlManager.getValueFromVectorParam(vectDatiVarianti, 13).longValue());
					}
					// i = 14, "IMPORTO_SUBTOTALE" column="IMP_SUBTOTALE"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 14).getValue() != null) {
						datiVariante.setIMPORTOSUBTOTALE(SqlManager.getValueFromVectorParam(vectDatiVarianti, 14).doubleValue());
					}
					// i = 15, "IMPORTO_RIDETERMINATO" column="IMP_COMPL_APPALTO"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 15).getValue() != null) {
						datiVariante.setIMPORTORIDETERMINATO(SqlManager.getValueFromVectorParam(vectDatiVarianti, 15).doubleValue());
					}
					// i = 16, "IMPORTO_COMPLESSIVO" column="IMP_COMPL_INTERVENTO"
					if (SqlManager.getValueFromVectorParam(vectDatiVarianti, 16).getValue() != null) {
						datiVariante.setIMPORTOCOMPLESSIVO(SqlManager.getValueFromVectorParam(vectDatiVarianti, 16).doubleValue());
					}

					// Motivi variante
					Vector<?> vectMotiviVarianti = this.sqlManager.getVector(
						"select TO_CHAR(ID_MOTIVO_VAR) from W9MOTI where CODGARA=? AND CODLOTT=? AND NUM_VARI=?",
						new Object[] { codiceGara, codiceLotto, numeroVariante });

							if (vectMotiviVarianti != null && vectMotiviVarianti.size() > 0) {
								if (SqlManager.getValueFromVectorParam(vectMotiviVarianti, 0).getValue() != null) {
									MotiviVariante motivoVariante = MotiviVariante.Factory.newInstance();
									ListaMotiviVariante listaMotiviVariante = ListaMotiviVariante.Factory.newInstance();
									motivoVariante.setIDMOTIVOVAR(SqlManager.getValueFromVectorParam(vectMotiviVarianti, 0).toString());
									listaMotiviVariante.setIdMotiviVarianteArray(new MotiviVariante[] { motivoVariante });
									datiVariante.setLISTAMOTIVIVARIANTE(listaMotiviVariante);
							 }
						}
						arrayDatiVarianti.add(datiVariante);
				}

				listaDatiVarianti.setIdDatiVarianteArray(arrayDatiVarianti.toArray(new 	DatiVariante[0]));
				
				sezioneVarianti.setDATIVARIANTI(listaDatiVarianti);
				datiGeneraliContratto.setSEZIONEVARIANTI(sezioneVarianti);
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneVarianti: fine metodo");
		}
	}


	private void setSezioneSospensioni(Long codiceGara, Long codiceLotto, 
			DatiGeneraliContratto datiGeneraliContratto) 
				throws SQLException, GestoreException {
	  
		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneSospensioni: inizio metodo");
		}

		// Se esiste almeno una sospensione
		long numeroSospensioni = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.SOSPENSIONE_CONTRATTO);
		if (numeroSospensioni > 0) {
			SezioneSospensioni sezioneSospensioni = SezioneSospensioni.Factory.newInstance();
			ListaDatiSospensione listaDatiSospensione = ListaDatiSospensione.Factory.newInstance();
			List<DatiSospensione> arrayDatiSospensione = new ArrayList<DatiSospensione>();
      
			List<?> listVectDatiSospensioni = this.sqlManager.getListVector(
				"select NUM_SOSP, DATA_VERB_SOSP, DATA_VERB_RIPR, ID_MOTIVO_SOSP, FLAG_SUPERO_TEMPO, FLAG_RISERVE, FLAG_VERBALE "
					+ " from W9SOSP where CODGARA=? and CODLOTT=? order by NUM_SOSP asc", new Object[] { codiceGara, codiceLotto });
			for (int j=0; j < listVectDatiSospensioni.size(); j++) {
				DatiSospensione datiSospensione = 	DatiSospensione.Factory.newInstance(); 
				
				Vector<?> vectDatiSospensioni = (Vector<?>) listVectDatiSospensioni.get(j);
				
				// i = 0, "NUM_PROGRESSIVO" column="NUM_SOSP"
				if (SqlManager.getValueFromVectorParam(vectDatiSospensioni, 0).getValue() != null) {
					datiSospensione.setNUMPROGRESSIVO(SqlManager.getValueFromVectorParam(vectDatiSospensioni, 0).longValue());
				}
				// i = 1, "DATA_VERB_SOSP" column="DATA_VERB_SOSP" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiSospensioni, 1).getValue() != null) {
					datiSospensione.setDATAVERBSOSP(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatiSospensioni, 1)));
				}
				// i = 2, "DATA_VERB_RIPR" column="DATA_VERB_RIPR" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiSospensioni, 2).getValue() != null) {
					datiSospensione.setDATAVERBRIPR(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatiSospensioni, 2)));
				}
				// i = 3, "ID_MOTIVO_SOSP" column="ID_MOTIVO_SOSP"
				if (SqlManager.getValueFromVectorParam(vectDatiSospensioni, 3).getValue() != null) {
					datiSospensione.setIDMOTIVOSOSP(SqlManager.getValueFromVectorParam(vectDatiSospensioni, 3).toString());
				}
				// i = 4, "FLAG_SUPERO_TEMPO" column="FLAG_SUPERO_TEMPO" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiSospensioni, 4).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiSospensioni, 4).toString();
					datiSospensione.setFLAGSUPEROTEMPO(convertiFlagSN(flag));
				}
				// i = 5, "FLAG_RISERVE" column="FLAG_RISERVE" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiSospensioni, 5).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiSospensioni, 5).toString();
					datiSospensione.setFLAGRISERVE(convertiFlagSN(flag));
				}
				// i = 6, "FLAG_VERBALE" column="FLAG_VERBALE" typeHandler="flagSNHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiSospensioni, 6).getValue() != null) {
					String flag = SqlManager.getValueFromVectorParam(vectDatiSospensioni, 6).toString();
					datiSospensione.setFLAGVERBALE(convertiFlagSN(flag));
				}
				arrayDatiSospensione.add(datiSospensione);
			}
			
			listaDatiSospensione.setIdDatiSospensioneArray(arrayDatiSospensione.toArray(new 	DatiSospensione[0]));
			sezioneSospensioni.setDATISOSPENSIONI(listaDatiSospensione);
			datiGeneraliContratto.setSEZIONESOSPENSIONI(sezioneSospensioni);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneSospensioni: fine metodo");
		}
	}
 

	private void setSezioneConclusione(Long codiceGara, Long codiceLotto, 
			DatiGeneraliContratto datiGeneraliContratto) throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneConclusione: inizio metodo");
		}

		// Se esiste almeno un tipo di conclusione
		long numeroConclusioni = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA) + 
			this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO);
		if (numeroConclusioni > 0) {
				SezioneConclusione sezioneConclusione = 	SezioneConclusione.Factory.newInstance();
				DatiConclusione datiConclusione = 	DatiConclusione.Factory.newInstance();

			Vector<?> vectDatiConclusione = this.sqlManager.getVector(
				"select INTANTIC, TO_CHAR(ID_MOTIVO_INTERR) as ID_MOTIVO_INTERR, TO_CHAR(ID_MOTIVO_RISOL) as ID_MOTIVO_RISOL, DATA_RISOLUZIONE, "  // 0,.., 3
					+ "coalesce(FLAG_POLIZZA, '2') as FLAG_POLIZZA, TO_CHAR(FLAG_ONERI) as FLAG_ONERI, ONERI_RISOLUZIONE, DATA_VERBALE_CONSEGNA, "   // 4,.., 7
					+ "TERMINE_CONTRATTO_ULT, NUM_INFORTUNI, DATA_ULTIMAZIONE, NUM_INF_PERM, NUM_INF_MORT, ORE_LAVORATE, NUM_GIORNI_PROROGA "        // 8,.., 14
					+ "from W9CONC where CODGARA=? and CODLOTT=?", new Object[] { codiceGara, codiceLotto } );

			// i = 0, "FLAG_INTERR_ANTICIPATA" column="INTANTIC" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 0).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiConclusione, 0).toString(); 
				datiConclusione.setFLAGINTERRANTICIPATA(convertiFlagSN(flag));
			}
			// i = 1, "ID_MOTIVO_INTERR" column="ID_MOTIVO_INTERR"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 1).getValue() != null) {
				datiConclusione.setIDMOTIVOINTERR(SqlManager.getValueFromVectorParam(vectDatiConclusione, 1).toString());
			}
			// i = 2, "ID_MOTIVO_RISOL" column="ID_MOTIVO_RISOL"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 2).getValue() != null) {
				datiConclusione.setIDMOTIVORISOL(SqlManager.getValueFromVectorParam(vectDatiConclusione, 2).toString());
			}
			// i = 3, "DATA_RISOLUZIONE" column="DATA_RISOLUZIONE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 3).getValue() != null) {
				datiConclusione.setDATARISOLUZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiConclusione, 3)));
			}
			// i = 4, "FLAG_POLIZZA" column="FLAG_POLIZZA" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 4).getValue() != null) {
				datiConclusione.setFLAGPOLIZZA(convertiFlagSN(SqlManager.getValueFromVectorParam(vectDatiConclusione, 4).toString()));
			}
			// i = 5, "FLAG_ONERI" column="FLAG_ONERI"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 5).getValue() != null) {
				datiConclusione.setFLAGONERI(SqlManager.getValueFromVectorParam(vectDatiConclusione, 5).toString());
			}
			// i = 6, "ONERI_RISOLUZIONE" column="ONERI_RISOLUZIONE"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 6).getValue() != null) {
				datiConclusione.setONERIRISOLUZIONE(SqlManager.getValueFromVectorParam(vectDatiConclusione, 6).doubleValue());
			}
			// i = 7, "DATA_VERB_CONSEGNA_AVVIO" column="DATA_VERBALE_CONSEGNA" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 7).getValue() != null) {
				datiConclusione.setDATAVERBCONSEGNAAVVIO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiConclusione, 7)));
			}
			// i = 8, "DATA_TERMINE_CONTRATTUALE" column="TERMINE_CONTRATTO_ULT" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 8).getValue() != null) {
				datiConclusione.setDATATERMINECONTRATTUALE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiConclusione, 8)));
			}
			// i = 9, "NUM_INFORTUNI" column="NUM_INFORTUNI"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 9).getValue() != null) {
				datiConclusione.setNUMINFORTUNI(SqlManager.getValueFromVectorParam(vectDatiConclusione, 9).longValue());
			}
			// i = 10, "DATA_ULTIMAZIONE" column="DATA_ULTIMAZIONE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 8).getValue() != null) {
				datiConclusione.setDATAULTIMAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiConclusione, 8)));
			}
			// i = 11, "NUM_INF_PERM" column="NUM_INF_PERM" 
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 11).getValue() != null) {
				datiConclusione.setNUMINFPERM(SqlManager.getValueFromVectorParam(vectDatiConclusione, 11).longValue());
			}
			// i = 12, "NUM_INF_MORT" column="NUM_INF_MORT" 
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 12).getValue() != null) {
				datiConclusione.setNUMINFMORT(SqlManager.getValueFromVectorParam(vectDatiConclusione, 12).longValue());
			}
			// i = 13, "ORE_LAVORATE" column="ORE_LAVORATE"
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 13).getValue() != null) {
				datiConclusione.setORELAVORATE(SqlManager.getValueFromVectorParam(vectDatiConclusione, 13).longValue());
			}
			// i = 14, "NUM_GIORNI_PROROGA" column="NUM_GIORNI_PROROGA" 
			if (SqlManager.getValueFromVectorParam(vectDatiConclusione, 14).getValue() != null) {
				datiConclusione.setNUMGIORNIPROROGA(SqlManager.getValueFromVectorParam(vectDatiConclusione, 14).longValue());
			}
			sezioneConclusione.setDATICONCLUSIONE(datiConclusione);
			datiGeneraliContratto.setSEZIONECONCLUSIONE(sezioneConclusione);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneConclusione: fine metodo");
		}
	}


	private void setSezioneCollaudo(Long codiceGara, Long codiceLotto, 
			DatiGeneraliContratto datiGeneraliContratto)
			throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneCollaudo: inizio metodo");
		}

		// Se esiste almeno un tipo di conclusione
		long numeroCollaudo = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.COLLAUDO_CONTRATTO);
		if (numeroCollaudo > 0) {
				SezioneCollaudo sezioneCollaudo = 	SezioneCollaudo.Factory.newInstance();
				DatiCollaudo datiCollaudo = 	DatiCollaudo.Factory.newInstance();

			Vector<?> vectDatiCollaudo = this.sqlManager.getVector(
				"select DATA_APPROVAZIONE, FLAG_IMPORTI, FLAG_SICUREZZA, FLAG_SUBAPPALTATORI, DATA_REGOLARE_ESEC, FLAG_SINGOLO_COMMISSIONE, "
					+ "DATA_COLLAUDO_STAT, TO_CHAR(MODO_COLLAUDO) as MODO_COLLAUDO, DATA_NOMINA_COLL, DATA_INIZIO_OPER, DATA_CERT_COLLAUDO, DATA_DELIBERA, ESITO_COLLAUDO, "
					+ "IMP_FINALE_LAVORI, IMP_FINALE_SERVIZI, IMP_FINALE_FORNIT, IMP_FINALE_SECUR, IMP_PROGETTAZIONE, IMP_DISPOSIZIONE, "
					+ "AMM_NUM_DEFINITE, AMM_NUM_DADEF, AMM_IMPORTO_RICH, AMM_IMPORTO_DEF, ARB_NUM_DEFINITE, ARB_NUM_DADEF, ARB_IMPORTO_RICH, "
					+ "ARB_IMPORTO_DEF, GIU_NUM_DEFINITE, GIU_NUM_DADEF, GIU_IMPORTO_RICH, GIU_IMPORTO_DEF, TRA_NUM_DEFINITE, TRA_NUM_DADEF, "
					+ "TRA_IMPORTO_RICH, TRA_IMPORTO_DEF, IMP_SUBTOTALE, IMP_COMPL_APPALTO, IMP_COMPL_INTERVENTO, LAVORI_ESTESI, "
					+ "DATA_INIZIO_AMM_RISERVE, DATA_FINE_AMM_RISERVE, DATA_INIZIO_ARB_RISERVE, DATA_FINE_ARB_RISERVE, "
					+ "DATA_INIZIO_GIU_RISERVE, DATA_FINE_GIU_RISERVE, DATA_INIZIO_TRA_RISERVE, DATA_FINE_TRA_RISERVE "
					+ "from W9COLL where CODGARA=? and CODLOTT=?" , new Object[] { codiceGara, codiceLotto });

			int i=0;
			// i = 0, "DATA_APPROVAZIONE" column="DATA_APPROVAZIONE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAAPPROVAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 1, "FLAG_IMPORTI" column="FLAG_IMPORTI" typeHandler="flagImportiHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setFLAGIMPORTI(	FlagImportiType.Enum.forString(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).toString()));
			}
			i++;
			// i = 2, "FLAG_SICUREZZA" column="FLAG_SICUREZZA" typeHandler="flagImportiHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setFLAGSICUREZZA(	FlagImportiType.Enum.forString(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).toString()));
			}
			i++;
			// i = 3, "FLAG_SUBAPPALTATORI" column="FLAG_SUBAPPALTATORI" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				String flag = SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).toString(); 
				datiCollaudo.setFLAGSUBAPPALTATORI(convertiFlagSN(flag));
			}
			i++;
			// i = 4, "DATA_REGOLARE_ESEC" column="DATA_REGOLARE_ESEC" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAREGOLAREESEC(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 5, "FLAG_SINGOLO_COMMISSIONE" column="FLAG_SINGOLO_COMMISSIONE" typeHandler="flagSingoloCommissioneHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setFLAGSINGOLOCOMMISSIONE(FlagSingoloCommissioneType.Enum.forString(
						SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).toString()));
			}
			i++;
			// i = 6, "DATA_COLLAUDO_STAT" column="DATA_COLLAUDO_STAT" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATACOLLAUDOSTAT(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 7, "MODO_COLLAUDO" column="MODO_COLLAUDO"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setMODOCOLLAUDO(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).toString());
			}
			i++;
			// i = 8, "DATA_NOMINA_COLL" column="DATA_NOMINA_COLL" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATANOMINACOLL(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 9, "DATA_INIZIO_OPER" column="DATA_INIZIO_OPER" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAINIZIOOPER(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 10, "DATA_CERT_COLLAUDO" column="DATA_CERT_COLLAUDO" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATACERTCOLLAUDO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 11, "DATA_DELIBERA" column="DATA_DELIBERA" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATADELIBERA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 12, "ESITO_COLLAUDO" column="ESITO_COLLAUDO" typeHandler="flagEsitoCollaudoHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setESITOCOLLAUDO(FlagEsitoCollaudoType.Enum.forString(
							SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).toString()));
			}
			i++;
			// i = 13, "IMP_FINALE_LAVORI" column="IMP_FINALE_LAVORI"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setIMPFINALELAVORI(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 14, "IMP_FINALE_SERVIZI" column="IMP_FINALE_SERVIZI"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setIMPFINALESERVIZI(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 15, "IMP_FINALE_FORNIT" column="IMP_FINALE_FORNIT"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setIMPFINALEFORNIT(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 16, "IMP_FINALE_SECUR" column="IMP_FINALE_SECUR"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setIMPFINALESECUR(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 17, "IMP_PROGETTAZIONE" column="IMP_PROGETTAZIONE"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setIMPPROGETTAZIONE(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 18, "IMP_DISPOSIZIONE" column="IMP_DISPOSIZIONE"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setIMPDISPOSIZIONE(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 19, "AMM_NUM_DEFINITE" column="AMM_NUM_DEFINITE"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setAMMNUMDEFINITE(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).longValue());
			}
			i++;
			// i = 20, "AMM_NUM_DADEF" column="AMM_NUM_DADEF"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setAMMNUMDADEF(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).longValue());
			}
			i++;
			// i = 21, "AMM_IMPORTO_RICH" column="AMM_IMPORTO_RICH"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setAMMIMPORTORICH(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 22, "AMM_IMPORTO_DEF" column="AMM_IMPORTO_DEF"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setAMMIMPORTODEF(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 23, "ARB_NUM_DEFINITE" column="ARB_NUM_DEFINITE"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setARBNUMDEFINITE(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).longValue());
			}
			i++;
			// i = 24, "ARB_NUM_DADEF" column="ARB_NUM_DADEF"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setARBNUMDADEF(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).longValue());
			}
			i++;
			// i = 25, "ARB_IMPORTO_RICH" column="ARB_IMPORTO_RICH"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setARBIMPORTORICH(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 26, "ARB_IMPORTO_DEF" column="ARB_IMPORTO_DEF"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setARBIMPORTODEF(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 27, "GIU_NUM_DEFINITE" column="GIU_NUM_DEFINITE"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setGIUNUMDEFINITE(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).longValue());
			}
			i++;
			// i = 28, "GIU_NUM_DADEF" column="GIU_NUM_DADEF"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setGIUNUMDADEF(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).longValue());
			}
			i++;
			// i = 29, "GIU_IMPORTO_RICH" column="GIU_IMPORTO_RICH"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setGIUIMPORTORICH(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 30, "GIU_IMPORTO_DEF" column="GIU_IMPORTO_DEF"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setGIUIMPORTODEF(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 31, "TRA_NUM_DEFINITE" column="TRA_NUM_DEFINITE"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setTRANUMDEFINITE(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).longValue());
			}
			i++;
			// i = 32, "TRA_NUM_DADEF" column="TRA_NUM_DADEF" 
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setTRANUMDADEF(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).longValue());
			}
			i++;
			// i = 33, "TRA_IMPORTO_RICH" column="TRA_IMPORTO_RICH"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setTRAIMPORTORICH(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 34, "TRA_IMPORTO_DEF" column="TRA_IMPORTO_DEF"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setTRAIMPORTODEF(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 35, "IMPORTO_SUBTOTALE" column="IMP_SUBTOTALE"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setIMPORTOSUBTOTALE(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 36, "IMPORTO_FINALE" column="IMP_COMPL_APPALTO"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setIMPORTOFINALE(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 37, "IMPORTO_CONSUNTIVO" column="IMP_COMPL_INTERVENTO"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setIMPORTOCONSUNTIVO(SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).doubleValue());
			}
			i++;
			// i = 38, "FLAG_LAVORI_ESTESI" column="LAVORI_ESTESI" typeHandler="flagSNHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setFLAGLAVORIESTESI(convertiFlagSN(
						SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).toString()));
			}
			i++;
			// i = 39, "DATA_INIZIO_AMM_RISERVE" column="DATA_INIZIO_AMM_RISERVE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAINIZIOAMMRISERVE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 40, "DATA_FINE_AMM_RISERVE" column="DATA_FINE_AMM_RISERVE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAFINEAMMRISERVE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 41, "DATA_INIZIO_ARB_RISERVE" column="DATA_INIZIO_ARB_RISERVE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAINIZIOARBRISERVE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
				SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 42, "DATA_FINE_ARB_RISERVE" column="DATA_FINE_ARB_RISERVE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAFINEARBRISERVE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 43, "DATA_INIZIO_GIU_RISERVE" column="DATA_INIZIO_GIU_RISERVE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAINIZIOGIURISERVE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 44, "DATA_FINE_GIU_RISERVE" column="DATA_FINE_GIU_RISERVE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAFINEGIURISERVE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
				i++;
			// i = 45, "DATA_INIZIO_TRA_RISERVE" column="DATA_INIZIO_TRA_RISERVE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAINIZIOTRARISERVE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}
			i++;
			// i = 46, "DATA_FINE_TRA_RISERVE" column="DATA_FINE_TRA_RISERVE" typeHandler="calendarHandler"
			if (SqlManager.getValueFromVectorParam(vectDatiCollaudo, i).getValue() != null) {
				datiCollaudo.setDATAFINETRARISERVE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
					SqlManager.getValueFromVectorParam(vectDatiCollaudo, i)));
			}

			sezioneCollaudo.setDATICOLLAUDO(datiCollaudo);
			
			// Soggetti estesi del collaudo
			ListaDatiSoggettiEstesi listaDatiSoggettiEstesi = 	ListaDatiSoggettiEstesi.Factory.newInstance();
			this.setDatiSoggettiEstesiCollaudo(codiceGara, codiceLotto, listaDatiSoggettiEstesi);
			
			if (listaDatiSoggettiEstesi != null && listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray() != null && listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray().length > 0) {
				for (int j=0; j < listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray().length; j++) {
						DatiSoggettiEstesi datiSoggettiEstesi = listaDatiSoggettiEstesi.getIdDatiSoggettiEstesiArray()[j];
					datiSoggettiEstesi.setFLAGCOLLAUDATORE(	FlagCollaudatoreType.Enum.forString("I"));
					datiSoggettiEstesi.setIMPORTOCOLLAUDATORE(0);
				}
				sezioneCollaudo.setLISTADATISOGGETTIESTESICOLL(listaDatiSoggettiEstesi);
			}


			List<?> listVectCodiceTecniciSoggettiEstesi = this.sqlManager.getListVector(
				"select CODTEC from W9INCA where CODGARA=? AND CODLOTT=? AND SEZIONE in 'CO' AND CODTEC is not null order by ID_RUOLO asc, SEZIONE asc",
					new Object[] { codiceGara, codiceLotto });

			if (listVectCodiceTecniciSoggettiEstesi != null && listVectCodiceTecniciSoggettiEstesi.size() > 0) {
				for (int j=0; j < listVectCodiceTecniciSoggettiEstesi.size(); j++) {
					Vector<?> vectCodiceTecniciSoggettiEstesi = (Vector<?>) listVectCodiceTecniciSoggettiEstesi.get(j);
					String codiceTecnico = SqlManager.getValueFromVectorParam(vectCodiceTecniciSoggettiEstesi, 0).toString();

					Long countTecni = (Long) this.sqlManager.getObject("select count(*) from TECNI where CODTEC=?", new Object[] { codiceTecnico } );
					if (countTecni > 0) {
							DatiResponsabile datiResponsabile = 	DatiResponsabile.Factory.newInstance();
						this.getTecnico(codiceTecnico, datiResponsabile);
						sezioneCollaudo.getLISTADATISOGGETTIESTESICOLL().getIdDatiSoggettiEstesiArray()[j].setResponsabile(datiResponsabile);
					}
				}
			}

			datiGeneraliContratto.setSEZIONECOLLAUDO(sezioneCollaudo);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneCollaudo: fine metodo");
		}
	}


	private void setSezioneSubappalti(Long codiceGara, Long codiceLotto, 
			DatiGeneraliContratto datiGeneraliContratto) 
			throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneSubappalti: inizio metodo");
		}

		// Se esiste almeno un subappalto
		long numeroSubappalti = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.SUBAPPALTO);
		if (numeroSubappalti > 0) {
			SezioneSubappalti sezioneSubappalti = SezioneSubappalti.Factory.newInstance();
			ListaDatiSubappalto listaDatiSubappalti = ListaDatiSubappalto.Factory.newInstance();
			List<DatiSubappalto> arrayDatiSubappalti = new ArrayList<DatiSubappalto>();

			List<?> listVectCampiSubappalto = this.sqlManager.getListVector(
				"select NUM_SUBA, CODIMP, CODIMP_AGGIUDICATRICE from W9SUBA where CODGARA=? and CODLOTT=? order by NUM_SUBA asc",
					new Object[] { codiceGara, codiceLotto });
			for (int j=0; j < listVectCampiSubappalto.size(); j++) {
				Vector<?> vectCampiSubappalto = (Vector<?>) listVectCampiSubappalto.get(j);
				Long numeroSubAppalto = ((JdbcParametro) vectCampiSubappalto.get(0)).longValue();
				String codiceImpresa = ((JdbcParametro) vectCampiSubappalto.get(1)).toString();
				String codiceImpresaAggiud = ((JdbcParametro) vectCampiSubappalto.get(2)).toString();

				DatiSubappalto datiSubappalto =   DatiSubappalto.Factory.newInstance();
				
				Vector<?> vectDatiSubappalto = this.sqlManager.getVector(
					"select W9SUBA.DATA_AUTORIZZAZIONE, W9SUBA.OGGETTO_SUBAPPALTO, W9SUBA.IMPORTO_PRESUNTO, "
						+ " W9SUBA.IMPORTO_EFFETTIVO, C.CODAVCP as ID_CATEGORIA, TO_CHAR(W9SUBA.ID_CPV) as ID_CPV "
						+ "from W9SUBA, W9CODICI_AVCP C "
						+ "where W9SUBA.CODGARA=? and W9SUBA.CODLOTT=? and W9SUBA.NUM_SUBA=? "
						+ "AND W9SUBA.ID_CATEGORIA=C.CODSITAT and C.TABCOD='W3z03'",
					new Object[] { codiceGara, codiceLotto, numeroSubAppalto });

				// i = 0, "DATA_AUTORIZZAZIONE" column="DATA_AUTORIZZAZIONE" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiSubappalto, 0).getValue() != null) {
					datiSubappalto.setDATAAUTORIZZAZIONE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatiSubappalto, 0)));
				}
				// i = 1, "OGGETTO_SUBAPPALTO" column="OGGETTO_SUBAPPALTO"
				if (SqlManager.getValueFromVectorParam(vectDatiSubappalto, 1).getValue() != null) {
					datiSubappalto.setOGGETTOSUBAPPALTO(SqlManager.getValueFromVectorParam(vectDatiSubappalto, 1).toString());
				}
				// i = 2, "IMPORTO_PRESUNTO"  column="IMPORTO_PRESUNTO"
				if (SqlManager.getValueFromVectorParam(vectDatiSubappalto, 2).getValue() != null) {
					datiSubappalto.setIMPORTOPRESUNTO(SqlManager.getValueFromVectorParam(vectDatiSubappalto, 2).doubleValue());
				}
				// i = 3, "IMPORTO_EFFETTIVO" column="IMPORTO_EFFETTIVO"
				if (SqlManager.getValueFromVectorParam(vectDatiSubappalto, 3).getValue() != null) {
					datiSubappalto.setIMPORTOEFFETTIVO(SqlManager.getValueFromVectorParam(vectDatiSubappalto, 3).doubleValue());
				}
				// i = 4, "ID_CATEGORIA"	column="ID_CATEGORIA"	columnIndex="5" typeHandler="categoriaHandler"
				if (SqlManager.getValueFromVectorParam(vectDatiSubappalto, 4).getValue() != null) {
					datiSubappalto.setIDCATEGORIA(
							CategoriaType.Enum.forString(
							SqlManager.getValueFromVectorParam(vectDatiSubappalto, 4).toString()));
				}
				// i = 5, "ID_CPV" column="ID_CPV"    	
				if (SqlManager.getValueFromVectorParam(vectDatiSubappalto, 5).getValue() != null) {
					datiSubappalto.setIDCPV(SqlManager.getValueFromVectorParam(vectDatiSubappalto, 5).toString());
				}

				// Dati Aggiudicatario
				if (StringUtils.isNotEmpty(codiceImpresa)) {
						DatiAggiudicatari datiAggiudicatario = 	DatiAggiudicatari.Factory.newInstance();
					boolean isAggiudicatarioValorizzato = this.getDatiAggiudicatario(codiceImpresa, datiAggiudicatario);
					if (isAggiudicatarioValorizzato) {
						this.getLegaleRappresentante(codiceImpresa, datiAggiudicatario);
					}
					datiSubappalto.setAggiudicatario(datiAggiudicatario);
				}

				// Dati Aggiudicatario avvallimento
				if (StringUtils.isNotEmpty(codiceImpresaAggiud)) {
						DatiAggiudicatari datiAggiudicatario = 	DatiAggiudicatari.Factory.newInstance();
					boolean isAggiudicatarioValorizzato = this.getDatiAggiudicatario(codiceImpresaAggiud, datiAggiudicatario);
					if (isAggiudicatarioValorizzato) {
						this.getLegaleRappresentante(codiceImpresaAggiud, datiAggiudicatario);
					}
					datiSubappalto.setArch3Impagg(datiAggiudicatario);
				} else {
					// L'oggetto Arch3_impagg e' obbligatorio per Liguria Digitale, quindi nel caso non sia valorizzato
					// il campo W9SUBA.CODIMP_AGGIUDICATICE, si considera il campo W9AGGI.CODIMP
					String codiceImpr = (String) this.sqlManager.getObject(
						"select CODIMP from W9AGGI where CODGARA=? and CODLOTT=? and NUM_APPA=1 and NUM_AGGI=1",
							new Object[] { codiceGara, codiceLotto });
					if (StringUtils.isNotEmpty(codiceImpr)) {
							DatiAggiudicatari datiAggiudicatario = 	DatiAggiudicatari.Factory.newInstance();
						boolean isAggiudicatarioValorizzato = this.getDatiAggiudicatario(codiceImpr, datiAggiudicatario);
						if (isAggiudicatarioValorizzato) {
							this.getLegaleRappresentante(codiceImpr, datiAggiudicatario);
						}
						datiSubappalto.setArch3Impagg(datiAggiudicatario);
					}
				}
				arrayDatiSubappalti.add(datiSubappalto);
			}

			listaDatiSubappalti.setIdDatiSubappaltoArray(arrayDatiSubappalti.toArray(new 	DatiSubappalto[0]));
			sezioneSubappalti.setDATISUBAPPALTI(listaDatiSubappalti);
			datiGeneraliContratto.setSEZIONESUBAPPALTI(sezioneSubappalti);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneSubappalti: fine metodo");
		}
	}



	private void setSezioneRitardiRecessi(Long codiceGara, Long codiceLotto, 
			DatiGeneraliContratto datiGeneraliContratto) 
			throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneRitardiRecessi: inizio metodo");
		}

		// Se esiste almeno un recesso
		long numeroRitardiRecessi = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.ISTANZA_RECESSO);
		if (numeroRitardiRecessi > 0) {
			SezioneRitardiRecessi sezioneRitardiRecessi = SezioneRitardiRecessi.Factory.newInstance();
			ListaDatiRitardiRecessi listaDatiRitardiRecessi = ListaDatiRitardiRecessi.Factory.newInstance();

			List<	DatiRecesso> arrayDatiRitardiRecessi = new ArrayList<	DatiRecesso>();

			List<?> listVectDatiRitardiRecessi = this.sqlManager.getListVector(
				"select DATA_TERMINE, DATA_CONSEGNA, TIPO_COMUN, DURATA_SOSP, TO_CHAR(MOTIVO_SOSP) as MOTIVO_SOSP, DATA_IST_RECESSO, "
				+ "FLAG_ACCOLTA, FLAG_TARDIVA, FLAG_RIPRESA, FLAG_RISERVA, IMPORTO_SPESE, IMPORTO_ONERI, RITARDO "
				+ "from W9RITA where CODGARA=? and CODLOTT=? order NUM_RTA asc", new Object[] { codiceGara, codiceLotto });

			if (listVectDatiRitardiRecessi != null && listVectDatiRitardiRecessi.size() > 0) {
				for (int j=0; j<listVectDatiRitardiRecessi.size(); j++) {
					Vector<?> vectDatiRitardiRecessi = (Vector<?>) listVectDatiRitardiRecessi.get(j);
					DatiRecesso datiRitardiRecessi = DatiRecesso.Factory.newInstance();

					// i = 0, "DATA_TERMINE" column="DATA_TERMINE" typeHandler="calendarHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 0).getValue() != null) {
						datiRitardiRecessi.setDATATERMINE(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 0)));
					}
					// i = 1, "DATA_CONSEGNA" column="DATA_CONSEGNA" typeHandler="calendarHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 1).getValue() != null) {
						datiRitardiRecessi.setDATACONSEGNA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 1)));
					}
					// i = 2, "FLAG_TIPO_COMUNICAZIONE" column="TIPO_COMUN" typeHandler="flagTCHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 2).getValue() != null) {
						datiRitardiRecessi.setDATACONSEGNA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 2)));
					}
					// i = 3, "DURATA_SOSP" column="DURATA_SOSP"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 3).getValue() != null) {
						datiRitardiRecessi.setDURATASOSP(SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 3).longValue());
					}
					// i = 4, "MOTIVO_SOSP" column="MOTIVO_SOSP"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 4).getValue() != null) {
						datiRitardiRecessi.setMOTIVOSOSP(SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 4).toString());
					}
					// i = 5, "DATA_IST_RECESSO" column="DATA_IST_RECESSO" typeHandler="calendarHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 5).getValue() != null) {
						datiRitardiRecessi.setDATACONSEGNA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 5)));
					}
					// i = 6, "FLAG_ACCOLTA" column="FLAG_ACCOLTA" typeHandler="flagSNHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 6).getValue() != null) {
						String flag = SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 6).toString(); 
						datiRitardiRecessi.setFLAGACCOLTA(convertiFlagSN(flag));
					}
					// i = 7, "FLAG_TARDIVA" column="FLAG_TARDIVA" typeHandler="flagSNHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 7).getValue() != null) {
						String flag = SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 7).toString(); 
						datiRitardiRecessi.setFLAGTARDIVA(convertiFlagSN(flag));
					}
					// i = 8, "FLAG_RIPRESA" column="FLAG_RIPRESA" typeHandler="flagSNHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 8).getValue() != null) {
						String flag = SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 8).toString(); 
						datiRitardiRecessi.setFLAGRIPRESA(convertiFlagSN(flag));
					}
					// i = 9, "FLAG_RISERVA" column="FLAG_RISERVA" typeHandler="flagSNHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 9).getValue() != null) {
						String flag = SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 9).toString(); 
						datiRitardiRecessi.setFLAGRISERVA(convertiFlagSN(flag));
					}
					// i = 10, "IMPORTO_SPESE" column="IMPORTO_SPESE"   
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 12).getValue() != null) {
						datiRitardiRecessi.setIMPORTOSPESE(SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 12).doubleValue());
					}
					// i = 11, "IMPORTO_ONERI" column="IMPORTO_ONERI"
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 12).getValue() != null) {
						datiRitardiRecessi.setIMPORTOONERI(SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 12).doubleValue());
					}
					// i = 12, "GG_RITARDO" column="RITARDO" 
					if (SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 12).getValue() != null) {
						datiRitardiRecessi.setGGRITARDO(SqlManager.getValueFromVectorParam(vectDatiRitardiRecessi, 12).longValue());
					}
					arrayDatiRitardiRecessi.add(datiRitardiRecessi);
				}
				listaDatiRitardiRecessi.setIdDatiRitardiRecessiArray(arrayDatiRitardiRecessi.toArray(new 	DatiRecesso[0]));
				sezioneRitardiRecessi.setDATIRITARDIRECESSI(listaDatiRitardiRecessi);
				datiGeneraliContratto.setSEZIONERITARDIRECESSI(sezioneRitardiRecessi);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneRitardiRecessi: fine metodo");
		}
	}


	private void setSezioneAccordiBonari(Long codiceGara, Long codiceLotto, 
			DatiGeneraliContratto datiGeneraliContratto)
			throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneAccordiBonari: inizio metodo");
		}

		// Se esiste almeno un accordo bonario
		long numeroAccordiBonari = this.getNumeroFasi(codiceGara, codiceLotto, CostantiW9.ACCORDO_BONARIO);
		if (numeroAccordiBonari > 0) {
			SezioneAccordiBonari sezioneAccordiBonari = SezioneAccordiBonari.Factory.newInstance();
			ListaDatiAccordiBonari listaDatiAccordiBonari = ListaDatiAccordiBonari.Factory.newInstance();
			List<DatiAccordo> arrayDatiAccordo = new ArrayList<DatiAccordo>();

			List<?> listVectDatiAccordiBonari = this.sqlManager.getListVector(
				"select DATA_INIZIO_ACC, DATA_FINE_ACC, DATA_ACCORDO, ONERI_DERIVANTI, NUM_RISERVE "
				+ "from W9ACCO where CODGARA=? and CODLOTT=? order by NUM_ACCO asc", new Object[] { codiceGara, codiceLotto });

			if (listVectDatiAccordiBonari != null && listVectDatiAccordiBonari.size() > 0) {
				for (int j=0; j < listVectDatiAccordiBonari.size(); j++) {
					Vector<?> vectDatiAccordiBonari = (Vector<?>) listVectDatiAccordiBonari.get(j);

					DatiAccordo datiAccordo = DatiAccordo.Factory.newInstance();

					// i = 0, "DATA_INIZIO_ACC" column="DATA_INIZIO_ACC" typeHandler="calendarHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 0).getValue() != null) {
						datiAccordo.setDATAINIZIOACC(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 0)));
					}
					// i = 1, "DATA_FINE_ACC" column="DATA_FINE_ACC" typeHandler="calendarHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 1).getValue() != null) {
						datiAccordo.setDATAFINEACC(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 1)));
					}
					// i = 2, "DATA_ACCORDO" column="DATA_ACCORDO" typeHandler="calendarHandler"
					if (SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 2).getValue() != null) {
						datiAccordo.setDATAACCORDO(UtilityDateExtension.convertJdbcParametroDateToCalendar(
							SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 2)));
					}
					// i = 3, "ONERI_DERIVANTI" column="ONERI_DERIVANTI"
					if (SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 3).getValue() != null) {
						datiAccordo.setONERIDERIVANTI(SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 3).doubleValue());
					}
					// i = 4, "NUM_RISERVE" column="NUM_RISERVE"
					if (SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 4).getValue() != null) {
						datiAccordo.setNUMRISERVE(SqlManager.getValueFromVectorParam(vectDatiAccordiBonari, 4).longValue());
					}

					arrayDatiAccordo.add(datiAccordo);
				}
				listaDatiAccordiBonari.setIdDatiAccordiBonariArray(arrayDatiAccordo.toArray(new 	DatiAccordo[0]));
				sezioneAccordiBonari.setDATIACCORDIBONARI(listaDatiAccordiBonari);
				datiGeneraliContratto.setSEZIONEACCORDIBONARI(sezioneAccordiBonari);
			}

		}
		if (logger.isDebugEnabled()) {
			logger.debug("setSezioneAccordiBonari: fine metodo");
		}
	}
  
  
  
	private void setDatiSoggettiEstesiAggiudicazione(Long codiceGara, Long codiceLotto, 
			ListaDatiSoggettiEstesi listaDatiSoggettiEstesi)
			throws SQLException, GestoreException {
		String strSql = "select ID_RUOLO, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, DATA_CONS_PROG_ESTERNA, SEZIONE "
			+ "from W9INCA where CODGARA=? AND CODLOTT=? AND SEZIONE in ('PA','RA','RE','RS','RQ') " 
			+ "order by ID_RUOLO asc, SEZIONE asc";
		this.setDatiSoggettiEstesi(codiceGara, codiceLotto, strSql, listaDatiSoggettiEstesi);
	}

	private void setDatiSoggettiEstesiInizio(Long codiceGara, Long codiceLotto, 
			ListaDatiSoggettiEstesi listaDatiSoggettiEstesi)
			throws SQLException, GestoreException {
		String strSql = "select ID_RUOLO, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, DATA_CONS_PROG_ESTERNA, SEZIONE "
			+ "from W9INCA where CODGARA=? AND CODLOTT=? AND SEZIONE in ('IN') " 
			+ "order by ID_RUOLO asc, SEZIONE asc";
		this.setDatiSoggettiEstesi(codiceGara, codiceLotto, strSql, listaDatiSoggettiEstesi);
	}

	private void setDatiSoggettiEstesiCollaudo(Long codiceGara, Long codiceLotto, 
			ListaDatiSoggettiEstesi listaDatiSoggettiEstesi)
			throws SQLException, GestoreException {

		String strSql = "select ID_RUOLO, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, DATA_CONS_PROG_ESTERNA, SEZIONE "
			+ "from W9INCA where CODGARA=? AND CODLOTT=? AND SEZIONE in ('CO') " 
			+ "order by ID_RUOLO asc, SEZIONE asc";
		this.setDatiSoggettiEstesi(codiceGara, codiceLotto, strSql, listaDatiSoggettiEstesi);
	}

	private void setDatiSoggettiEstesi(Long codiceGara, Long codiceLotto, String strSql,
			ListaDatiSoggettiEstesi listaDatiSoggettiEstesi)
			throws SQLException, GestoreException {

		if (StringUtils.isEmpty(strSql)) {
			strSql = "select ID_RUOLO, CIG_PROG_ESTERNA, DATA_AFF_PROG_ESTERNA, DATA_CONS_PROG_ESTERNA, SEZIONE "
				+ "from W9INCA where CODGARA=? AND CODLOTT=? AND SEZIONE not in ('NP') order by ID_RUOLO asc, SEZIONE asc";
		}

		List<?> listVectDatiSoggettiEstesi = this.sqlManager.getListVector(strSql, new Object[] { codiceGara, codiceLotto });

		if (listVectDatiSoggettiEstesi != null && listVectDatiSoggettiEstesi.size() > 0) {

			List<	DatiSoggettiEstesi> arrayDatiSoggettiEstesi = new ArrayList<	DatiSoggettiEstesi>();
			for (int j=0; j < listVectDatiSoggettiEstesi.size(); j++) {
				Vector<?> vectDatisoggettiEstesi = (Vector<?>) listVectDatiSoggettiEstesi.get(j);

					DatiSoggettiEstesi datiSoggettiEstesi = 	DatiSoggettiEstesi.Factory.newInstance();
				
				// i = 0, "ID_RUOLO" column="ID_RUOLO"
				if (SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 0).getValue() != null) {
					datiSoggettiEstesi.setIDRUOLO(SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 0).toString());
				}
				// i = 1, "CIG_PROG_ESTERNA" column="CIG_PROG_ESTERNA"
				if (SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 1).getValue() != null) {
					datiSoggettiEstesi.setCIGPROGESTERNA(SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 1).toString());
				}
				// i = 2, "DATA_AFF_PROG_ESTERNA" column="DATA_AFF_PROG_ESTERNA" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 2).getValue() != null) {
					datiSoggettiEstesi.setDATAAFFPROGESTERNA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 2)));
				}
				// i = 3, "DATA_CONS_PROG_ESTERNA" column="DATA_CONS_PROG_ESTERNA" typeHandler="calendarHandler"
				if (SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 3).getValue() != null) {
					datiSoggettiEstesi.setDATACONSPROGESTERNA(UtilityDateExtension.convertJdbcParametroDateToCalendar(
						SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 3)));
				}
				// i = 4, "SEZIONE" column="SEZIONE"
				if (SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 4).getValue() != null) {
					datiSoggettiEstesi.setSEZIONE(SqlManager.getValueFromVectorParam(vectDatisoggettiEstesi, 4).toString());
				}
				// Solo per i progettisti esterni si va a settare il costo del progetto
				if (StringUtils.equals("2", datiSoggettiEstesi.getIDRUOLO())) {
					datiSoggettiEstesi.setCOSTOPROGETTO(new Double(0));
				}
				arrayDatiSoggettiEstesi.add(datiSoggettiEstesi);
			}
			listaDatiSoggettiEstesi.setIdDatiSoggettiEstesiArray(arrayDatiSoggettiEstesi.toArray(new 	DatiSoggettiEstesi[0]));
		} else {
			listaDatiSoggettiEstesi = null;
		}
	}


	private boolean getDatiAggiudicatario(String codiceImpresa,
			DatiAggiudicatari datiAggiudicatario)
			throws SQLException, GestoreException {

		Long countImprese = (Long) this.sqlManager.getObject("select count(*) from IMPR where CODIMP=?", new Object[] { codiceImpresa });
		boolean result = true;
		if (countImprese == 0)
			result = false;
	
		if (result) {
			// Dati Aggiudicatario
			Vector<?> vectDatiAggiudicatario = this.sqlManager.getVector(
				"SELECT NINPS, NOMEST, CFIMP, PIVIMP, INDIMP, NCIIMP, PROIMP, CAPIMP, LOCIMP, substr(CODCIT,4) as CODCIT, "
					+ "CASE WHEN (NAZIMP is not null and NAZIMP <> 1) THEN (SELECT TAB2TIP from TAB2 WHERE TAB2COD='W9z04' and TAB2D1 = TO_CHAR(NAZIMP)) end as NAZIMP, "
					+ "TELIMP, FAXIMP, TELCEL, EMAIIP, EMAI2IP, INDWEB, NCCIAA, TO_CHAR(NATGIUI) as NATGIUI, TO_CHAR(TIPIMP) as TIPIMP, "
					+ "CASE WHEN NINPS is null and NATGIUI = 10 THEN 'Libero professionista' else null end as MOTIVO_ASSENZA_INPS, "
					+ "CASE WHEN (NAZIMP is not null and NAZIMP <> 1) THEN LOCIMP end as COMUNE_ESTERO "
				+ " FROM IMPR WHERE CODIMP=?", new Object[] { codiceImpresa }) ;
		  
	 
			// i = 0, "INPS" column="NINPS"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 0).getValue() != null) {
				datiAggiudicatario.setINPS(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 0).toString());
			}
			// i = 1, "DENOMINAZIONE" column="NOMEST"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 1).getValue() != null) {
				datiAggiudicatario.setDENOMINAZIONE(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 1).toString());
			}
			// i = 2, "CODICE_FISCALE" column="CFIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 2).getValue() != null) {
				datiAggiudicatario.setCODICEFISCALE(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 2).toString());
			}
			// i = 3, "PARTITA_IVA" column="PIVIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 3).getValue() != null) {
				datiAggiudicatario.setPARTITAIVA(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 3).toString());
			}
			// i = 4, "INDIRIZZO" column="INDIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 4).getValue() != null) {
				datiAggiudicatario.setINDIRIZZO(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 4).toString());
			}
			// i = 5, "CIVICO" column="NCIIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 5).getValue() != null) {
				datiAggiudicatario.setCIVICO(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 5).toString());
			}
			// i = 6, "PROVINCIA" column="PROIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 6).getValue() != null) {
				datiAggiudicatario.setPROVINCIA(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 6).toString());
			}
			// i = 7, "CAP" column="CAPIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 7).getValue() != null) {
				datiAggiudicatario.setCAP(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 7).toString());
			}
			// i = 8, "LOCALITA_IMP" column="LOCIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 8).getValue() != null) {
				datiAggiudicatario.setLOCALITAIMP(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 8).toString());
			}
			// i = 9, "CITTA" column="CODCIT"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 9).getValue() != null) {
				datiAggiudicatario.setCITTA(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 9).toString());
			}
			// i = 10, "CODICE_STATO" column="NAZIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 10).getValue() != null) {
				datiAggiudicatario.setCODICESTATO(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 10).toString());
			}
			// i = 11, "TELEFONO" column="TELIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 11).getValue() != null) {
				datiAggiudicatario.setTELEFONO(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 11).toString());
			}
			// i = 12, "FAX" column="FAXIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 12).getValue() != null) {
				datiAggiudicatario.setFAX(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 12).toString());
			}
			// i = 13, "CELL_IMP" column="TELCEL"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 13).getValue() != null) {
				datiAggiudicatario.setCELLIMP(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 13).toString());
			}
			// i = 14, "EMAIL" column="EMAIIP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 14).getValue() != null) {
				datiAggiudicatario.setEMAIL(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 14).toString());
			}
			// i = 15, "PEC_IMP" column="EMAI2IP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 15).getValue() != null) {
				datiAggiudicatario.setPECIMP(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 15).toString());
			}
			// i = 16, "WEB_IMP" column="INDWEB"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 16).getValue() != null) {
				datiAggiudicatario.setWEBIMP(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 16).toString());
			}
			// i = 17, "NCCIAA" column="NCCIAA"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 17).getValue() != null) {
				datiAggiudicatario.setNCCIAA(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 17).toString());
			}
			// i = 18, "NATURA_GIURIDICA" column="NATGIUI"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 18).getValue() != null) {
				datiAggiudicatario.setNATURAGIURIDICA(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 18).toString());
			}
			// i = 19, "TIPOLOGIA_SOGGETTO" column="TIPIMP"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 19).getValue() != null) {
				datiAggiudicatario.setTIPOLOGIASOGGETTO(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 19).toString());				
			}
			// i = 20, "MOTIVO_ASSENZA_INPS" column="MOTIVO_ASSENZA_INPS"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 20).getValue() != null) {
				datiAggiudicatario.setMOTIVOASSENZAINPS(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 18).toString());
			}
			// i = 21, "COMUNE_ESTERO" column="COMUNE_ESTERO"
			if (SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 21).getValue() != null) {
				datiAggiudicatario.setCOMUNEESTERO(SqlManager.getValueFromVectorParam(vectDatiAggiudicatario, 21).toString());
			}
		}
		return result;
	}


	private void getLegaleRappresentante(String codiceImpresa, 
			DatiAggiudicatari datiAggiudicatario) throws SQLException {

		Long countTEIM = (Long) this.sqlManager.getObject("select count(*) from TEIM where CODTIM=?", new Object[] { codiceImpresa });
		
		if (countTEIM > 0) {
			Vector<?> vectDatiLegaleRapp = this.sqlManager.getVector("select COGTIM, NOMETIM, CFTIM from TEIM where CODTIM=?", new Object[] { codiceImpresa });

				DatiLegaleRappresentante datiLegaleRappresentante = 	DatiLegaleRappresentante.Factory.newInstance();
			// i = 0, "COGNOME" column="COGTIM"		  
			if (SqlManager.getValueFromVectorParam(vectDatiLegaleRapp, 0).getValue() != null) {
				datiLegaleRappresentante.setCOGNOME(SqlManager.getValueFromVectorParam(vectDatiLegaleRapp, 0).toString());
			}
			// i = 1, "NOME" column="NOMETIM"
			if (SqlManager.getValueFromVectorParam(vectDatiLegaleRapp, 1).getValue() != null) {
				datiLegaleRappresentante.setNOME(SqlManager.getValueFromVectorParam(vectDatiLegaleRapp, 1).toString());
			}
			// i = 2, "CODICE_FISCALE" column="CFTIM"		  
			if (SqlManager.getValueFromVectorParam(vectDatiLegaleRapp, 2).getValue() != null) {
				datiLegaleRappresentante.setCODICEFISCALE(SqlManager.getValueFromVectorParam(vectDatiLegaleRapp, 2).toString());
			}

			datiAggiudicatario.setListaLegaleRappresentanteArray(
				new 	DatiLegaleRappresentante[] { datiLegaleRappresentante });
		}
	}
  
	private void getTecnico(String codiceTecnico, DatiResponsabile datiResponsabile) throws SQLException {
		Vector<?> vectDatiTecnico = this.sqlManager.getVector(
			"select INDTEC, NCITEC, case when ALBTEC is not null then '1' else '2' end as ALBTEC, LOCTEC, CAPTEC, TELTEC, "
				+ " FAXTEC, CFTEC, SUBSTR(CITTEC,4,6) as CITTEC, EMATEC, COGTEI, NOMETEI, PROTEC, "
				+ " CASE WHEN (NAZTEI is not null and NAZTEI <> 1) THEN (SELECT TAB2TIP from TAB2 WHERE TAB2COD='W9z04' and TAB2D1 = TO_CHAR(NAZTEI)) end as NAZTEI, "
				+ " CASE WHEN (NAZTEI is not null and NAZTEI <> 1) THEN LOCTEC end as COMUNE_ESTERO "
				+ "from TECNI where CODTEC=?", new Object[] { codiceTecnico });
		  
		// i = 0, "INDIRIZZO" column="INDTEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 0).getValue() != null) {
			datiResponsabile.setINDIRIZZO(SqlManager.getValueFromVectorParam(vectDatiTecnico, 0).toString());
		}
		// i = 1, "CIVICO" column="NCITEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 1).getValue() != null) {
			datiResponsabile.setCIVICO(SqlManager.getValueFromVectorParam(vectDatiTecnico, 1).toString());
		}
		// i = 2, "ALBO_PROFESSIONALE" column="ALBTEC" typeHandler="flagSNHandler"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 2).getValue() != null) {
			String flag = SqlManager.getValueFromVectorParam(vectDatiTecnico, 2).toString();
			datiResponsabile.setALBOPROFESSIONALE(convertiFlagSN(flag));
		}
		// i = 3, "LOCALITA_TECN" column="LOCTEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 3).getValue() != null) {
			datiResponsabile.setLOCALITATECN(SqlManager.getValueFromVectorParam(vectDatiTecnico, 3).toString());
		}
		// i = 4, "CAP" column="CAPTEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 4).getValue() != null) {
			datiResponsabile.setCAP(SqlManager.getValueFromVectorParam(vectDatiTecnico, 4).toString());
		}
		// i = 5, "TELEFONO" column="TELTEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 5).getValue() != null) {
			datiResponsabile.setTELEFONO(SqlManager.getValueFromVectorParam(vectDatiTecnico, 5).toString());
		}
		// i = 6, "FAX" column="FAXTEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 6).getValue() != null) {
			datiResponsabile.setFAX(SqlManager.getValueFromVectorParam(vectDatiTecnico, 6).toString());
		}
		// i = 7, "CODICE_FISCALE" column="CFTEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 7).getValue() != null) {
			datiResponsabile.setCODICEFISCALE(SqlManager.getValueFromVectorParam(vectDatiTecnico, 7).toString());
		}
		// i = 8, "CODICE_ISTAT_COMUNE" column="CITTEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 8).getValue() != null) {
		datiResponsabile.setCODICEISTATCOMUNE(SqlManager.getValueFromVectorParam(vectDatiTecnico, 8).toString());
		}
		// i = 9, "EMAIL" column="EMATEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 9).getValue() != null) {
			datiResponsabile.setEMAIL(SqlManager.getValueFromVectorParam(vectDatiTecnico, 9).toString());
		}
		// i = 10, "COGNOME" column="COGTEI"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 10).getValue() != null) {
			datiResponsabile.setCOGNOME(SqlManager.getValueFromVectorParam(vectDatiTecnico, 10).toString());
		}
		// i = 11, "NOME" column="NOMETEI"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 11).getValue() != null) {
			datiResponsabile.setNOME(SqlManager.getValueFromVectorParam(vectDatiTecnico, 11).toString());
		}
		// i = 12, "PRO_TECN" column="PROTEC"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 12).getValue() != null) {
			datiResponsabile.setPROTECN(SqlManager.getValueFromVectorParam(vectDatiTecnico, 12).toString());
		}
		// i = 13, "CODICE_STATO" column="NAZTEI"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 13).getValue() != null) {
			datiResponsabile.setCODICESTATO(SqlManager.getValueFromVectorParam(vectDatiTecnico, 13).toString());
		}
		// i = 14, "COMUNE_ESTERO" column="COMUNE_ESTERO"
		if (SqlManager.getValueFromVectorParam(vectDatiTecnico, 4).getValue() != null) {
			datiResponsabile.setCOMUNEESTERO(SqlManager.getValueFromVectorParam(vectDatiTecnico, 14).toString());
		}
	}

	/**
	 *  Prototipo di un metodo di settaggio di campi XML senza dover passare uno ad uno i campi.
	 *  
	 *  
	 * @param codiceTecnico
	 * @param datiResponsabile
	 * @throws SQLException
	 * @throws GestoreException
	 */
	/*private void getTecnicoNew(String entita, String codiceTecnico, DatiResponsabile datiResponsabile) throws SQLException, GestoreException {
		java.util.HashMap<String, ?> hashMapDatiTecnico = this.sqlManager.getHashMap(
			"select INDTEC, NCITEC, case when ALBTEC is not null then '1' else '2' end as ALBTEC, LOCTEC, CAPTEC, TELTEC, "
				+ " FAXTEC, CFTEC, SUBSTR(CITTEC,4,6) as CITTEC, EMATEC, COGTEI, NOMETEI, PROTEC, "
				+ " CASE WHEN (NAZTEI is not null and NAZTEI <> 1) THEN (SELECT TAB2TIP from TAB2 WHERE TAB2COD='W9z04' and TAB2D1 = TO_CHAR(NAZTEI)) end as NAZTEI, "
				+ " CASE WHEN (NAZTEI is not null and NAZTEI <> 1) THEN LOCTEC end as COMUNE_ESTERO "
				+ "from TECNI where CODTEC=?", new Object[] { codiceTecnico });
		
		Map<String, String> mappaturaCampiDBCampiXML = new HashMap<String, String>();
		mappaturaCampiDBCampiXML.put("INDTEC", "INDIRIZZO");
    mappaturaCampiDBCampiXML.put("NCITEC", "CIVICO");
    mappaturaCampiDBCampiXML.put("LOCTEC", "LOCALITATECN");
    mappaturaCampiDBCampiXML.put("ALBTEC", "ALBOPROFESSIONALE");
    mappaturaCampiDBCampiXML.put("CAPTEC", "CAP");
    mappaturaCampiDBCampiXML.put("TELTEC", "TELEFONO");
    mappaturaCampiDBCampiXML.put("FAXTEC", "FAX");
    mappaturaCampiDBCampiXML.put("CFTEC", "CODICEFISCALE");
    mappaturaCampiDBCampiXML.put("CITTEC", "CODICEISTATCOMUNE");
    mappaturaCampiDBCampiXML.put("EMATEC", "EMAIL");
    mappaturaCampiDBCampiXML.put("COGTEI", "COGNOME");
    mappaturaCampiDBCampiXML.put("NOMETEI", "NOME");
    mappaturaCampiDBCampiXML.put("PROTEC", "PROTECN");
    mappaturaCampiDBCampiXML.put("NAZTEI", "CODICESTATO");
    mappaturaCampiDBCampiXML.put("COMUNE_ESTERO", "COMUNEESTERO");
    
    Set<Entry<String, String>> set = mappaturaCampiDBCampiXML.entrySet();
    Iterator<Entry<String, String>> iterator = set.iterator();

    while(iterator.hasNext()) {
      Entry<String, String> entry = iterator.next();
      String nomeCampoDB = entry.getKey();
      String nomeCampoXml = entry.getValue();
      
      JdbcParametro jdbcParam = null; 
      if (hashMapDatiTecnico.containsKey(entita.concat(nomeCampoDB))) {
        jdbcParam = (JdbcParametro) hashMapDatiTecnico.get(entita.concat(nomeCampoDB));
      } else if (hashMapDatiTecnico.containsKey(nomeCampoDB)) {
        jdbcParam = (JdbcParametro) hashMapDatiTecnico.get(nomeCampoDB);
      }
      
      if (jdbcParam != null) {
        if (jdbcParam.getValue() != null) {
          try {
            java.lang.reflect.Method metodo = datiResponsabile.getClass().getMethod("set".concat(nomeCampoXml), new Class[] {java.lang.String.class});
            if (metodo != null) {
              Class<?>[] arrayParametri = metodo.getParameterTypes();
              if (arrayParametri[0].getName().equals("java.lang.String")) {
                // tipo String
                metodo.invoke(datiResponsabile, jdbcParam.getStringValue());
              } else if (arrayParametri[0].getName().equals("java.lang.Double") || arrayParametri[0].getName().equals("D")) {
                // tipo Double o tipo primitivo double
                metodo.invoke(datiResponsabile, jdbcParam.doubleValue());
              } else if (arrayParametri[0].getName().equals("java.lang.Long") || arrayParametri[0].getName().equals("J")) {
                // tipo Long o tipo primitivo long
                metodo.invoke(datiResponsabile, jdbcParam.longValue());
              } else if (arrayParametri[0].getName().equals("java.lang.Float") || arrayParametri[0].getName().equals("F")) {
                // tipo Float o tipo primitivo float
                metodo.invoke(datiResponsabile, new Float(jdbcParam.doubleValue()).floatValue());
              } else 
              if (arrayParametri[0].getName().equals("java.util.Calendar")) {
                // tipo Calendar
                metodo.invoke(datiResponsabile, UtilityDateExtension.convertJdbcParametroDateToCalendar(jdbcParam));
              } else if (arrayParametri[0].getName().indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types") == 0) {
                
                // Bisogna riuscire ad invocare per reflection il metodo arrayParametri[0].getName() + ".Enum.forString(jdbcParam.toString())"
                // oppure il metodo arrayParametri[0].getName() + ".forString(jdbcParam.toString())"
                // Esempio:
                // it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType.Enum.forString(jdbcParam.toString());
                // Altrimenti usare questa serie di if else
              /*} else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoSchedaType") == 0) {
                } else if (nomeParametro.indexOf("it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType") == 0) {
                } else *//*
              }
              
              metodo.invoke(datiResponsabile, jdbcParam.getStringValue());
            } else {
              logger.error("L'oggetto " + datiResponsabile.getClass().getName() + " non ha il metodo set" + nomeCampoXml);
            }
          } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        } else {
          // Campo estratto, ma non valorizzato
        }
      } else {
        logger.error("Il campo " + entita + "." + nomeCampoDB + " non e' fra i campi estratti ");
      } 
    }
	}*/
  
}
