package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveIDAutoincrementante;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.ControlliValidazioneManager;
import it.eldasoft.w9.bl.CreazioneXmlManager;
import it.eldasoft.w9.bl.EsportazioneXMLSIMOGManager;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.bl.rest.ScpManager;
import it.eldasoft.w9.common.CostantiServiziRest;
import it.eldasoft.w9.common.CostantiSimog;
import it.eldasoft.w9.common.CostantiSitatSA;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.bean.AvvisoBean;
import it.eldasoft.w9.common.bean.DatiComuniBean;
import it.eldasoft.w9.common.bean.GaraLottoBean;
import it.eldasoft.w9.common.bean.PianoTriennaleBean;
import it.eldasoft.w9.common.bean.PubblicazioneBean;
import it.eldasoft.w9.dao.vo.rest.DatiGeneraliStazioneAppaltanteEntry;
import it.eldasoft.w9.dao.vo.rest.InserimentoResult;
import it.eldasoft.w9.dao.vo.rest.LoginResult;
import it.eldasoft.w9.dao.vo.rest.PubblicaAttoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicaAvvisoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicaGaraEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicazioneAttoResult;
import it.eldasoft.w9.dao.vo.rest.PubblicazioneResult;
import it.eldasoft.w9.dao.vo.rest.ValidateEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.dm112011.PubblicaProgrammaDM112011Entry;
import it.eldasoft.w9.dao.vo.rest.programmi.fornitureservizi.PubblicaProgrammaFornitureServiziEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.lavori.PubblicaProgrammaLavoriEntry;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.regione.sitat.proxy.ws.client.SitatProxy;
import it.toscana.rete.rfc.sitat.types.ErroreType;
import it.toscana.rete.rfc.sitat.types.EsitoType.DettaglioErrori;
import it.toscana.rete.rfc.sitat.types.RispostaRichiestaRispostaSincronaEventoSitatDocument;
import it.toscana.rete.rfc.sitat.types.RispostaRichiestaRispostaSincronaEventoSitatType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import javax.xml.rpc.JAXRPCException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlValidationError;
import org.springframework.transaction.TransactionStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Gestore di salvataggio del flusso con invio al proxy.
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9FLUSSI extends AbstractGestoreChiaveIDAutoincrementante {

	static Logger logger = Logger.getLogger(GestoreW9FLUSSI.class);
	//@Override
	/*public String[] getAltriCampiChiave() {
		return null;
	}*/

	@Override
	public String getCampoNumericoChiave() {
		return "IDFLUSSO";
	}

	@Override
	public String getEntita() {
		return "W9FLUSSI";
	}

	@Override
	public void postDelete(DataColumnContainer arg0) throws GestoreException {

	}

	@Override
	public void postUpdate(DataColumnContainer arg0) throws GestoreException {

	}

	@Override
	public void preDelete(TransactionStatus arg0, DataColumnContainer arg1) throws GestoreException {

	}

	@Override
	public void preUpdate(TransactionStatus arg0, DataColumnContainer arg1) throws GestoreException {

	}

	public void preInsert(TransactionStatus status, DataColumnContainer impl)
    throws GestoreException {
		super.preInsert(status, impl);
		Timestamp dataAttuale = new Timestamp(new Date().getTime());
		impl.setValue("W9FLUSSI.DATINV", dataAttuale);
	}
	
	@Override
	public void postInsert(DataColumnContainer impl) throws GestoreException {
		W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager", this.getServletContext(),
		    W9Manager.class);
		ControlliValidazioneManager controlliValidazioneManager = (ControlliValidazioneManager)
		    UtilitySpring.getBean("controlliValidazioneManager", this.getServletContext(), 
		        ControlliValidazioneManager.class);
		
		String profiloAttivo = (String) this.getRequest().getSession().getAttribute(
		    CostantiGenerali.PROFILO_ATTIVO);
		
		boolean isLoaderAppaltoEnable = UtilitySITAT.isConfigurazioneVigilanza(this.geneManager, profiloAttivo);
		  
		Long tipoInvio = impl.getLong("TINVIO2");
		if (tipoInvio.longValue() != 91) {

			String codiceErrore = "";
			Long valoreFlusso = null;
			long progressivoFase = 1;

			long codiceGara = 0;
			long codiceLotto = 0;
			long codiceCompositore = 0;
			String mailCompositore = null;

			long codicePianoTriennale = 0;
			boolean erroriBloccanti = false;
			// Oggetto per contenere i dati comuni necessari alla creazione
			// della testata (FaseType o FaseEstesaType) di un flusso.
			DatiComuniBean datiComuniPerCreazione = new DatiComuniBean();
			
			try {
				SqlManager sqlManager = this.getSqlManager();
				StringBuffer idEgov = new StringBuffer();

				String flusso = (String) this.getRequest().getSession().getAttribute("flusso");
				String uffint = (String) this.getRequest().getSession().getAttribute("uffint");
				String custom = (String) this.getRequest().getSession().getAttribute("invioCustom");
				if (custom == null || !custom.equals("RM")) {
					if (impl.getLong("W9FLUSSI.KEY03") != null) {
						valoreFlusso = impl.getLong("W9FLUSSI.KEY03");
					} else {
						valoreFlusso = new Long(flusso);
					}

					datiComuniPerCreazione.setCodiceFase(valoreFlusso.longValue());
					datiComuniPerCreazione.setCodiceCompositore(impl.getLong("W9FLUSSI.CODCOMP"));
					datiComuniPerCreazione.setNomeCompositore(impl.getString("W9FLUSSI.AUTORE"));
					datiComuniPerCreazione.setTipoInvio(tipoInvio);
					
					codiceCompositore = impl.getLong("W9FLUSSI.CODCOMP");

					if (codiceCompositore > 0) {
						mailCompositore = (String) sqlManager.getObject("select EMAIL from USRSYS where SYSCON=?", new Object[] { new Long(codiceCompositore) });
						datiComuniPerCreazione.setMailCompositore(mailCompositore);
					}
					
					if (impl.isColumn("NOTEINVIO") && impl.getString("NOTEINVIO") != null) {
						datiComuniPerCreazione.setNoteInvio(impl.getString("NOTEINVIO"));
					}

					if (impl.isColumn("CFSA") && impl.getString("CFSA") != null) {
						datiComuniPerCreazione.setCfStazioneAppaltante(impl.getString("CFSA"));
					}
					
					if (impl.getLong("W9FLUSSI.KEY04") != null && !impl.getLong("W9FLUSSI.KEY04").toString().trim().equals("")) {
						progressivoFase = impl.getLong("W9FLUSSI.KEY04");
						datiComuniPerCreazione.setProgressivoFase(impl.getLong("W9FLUSSI.KEY04"));
					}

					Object[] params = new Object[6]; 

					datiComuniPerCreazione.setEsito(Boolean.TRUE);
					
					ArrayList<XmlValidationError> xmlValidationError = null;
					XmlObject xmlObject = null;

					AvvisoBean avvisoBean = null;
					PianoTriennaleBean pianoTriennaleBean = null;
					GaraLottoBean garaLottoBean = null;
					PubblicazioneBean pubblicazioniBean = null;

					if (CostantiW9.PUBBLICAZIONE_AVVISO == valoreFlusso.intValue()) {
						params[1] = valoreFlusso;
						params[2] = uffint;
						params[3] = impl.getLong("W9FLUSSI.KEY01");
						params[5] = impl.getLong("W9FLUSSI.KEY02");

						avvisoBean = new AvvisoBean();
						avvisoBean.setCodiceAvviso(impl.getLong("W9FLUSSI.KEY01"));
						avvisoBean.setCodiceSistema(impl.getLong("W9FLUSSI.KEY02"));
						avvisoBean.setDatiComuni(datiComuniPerCreazione);
					} else if (CostantiW9.PROGRAMMA_TRIENNALE_LAVORI == valoreFlusso.intValue() || CostantiW9.PROGRAMMA_TRIENNALE_FORNITURE_SERVIZI == valoreFlusso.intValue()
					    || CostantiW9.PROGRAMMA_TRIENNALE_OPERE_PUBBLICHE == valoreFlusso.intValue() || CostantiW9.PROGRAMMA_BIENNALE_ACQUISTI == valoreFlusso.intValue()) {
						isLoaderAppaltoEnable = false;
						if (impl.getLong("W9FLUSSI.KEY01") != null && !impl.getLong("W9FLUSSI.KEY01").toString().trim().equals("")) {
							codicePianoTriennale = impl.getLong("W9FLUSSI.KEY01");
						}

						pianoTriennaleBean = new PianoTriennaleBean();
						pianoTriennaleBean.setCodicePianoTriennale(codicePianoTriennale);
						pianoTriennaleBean.setDatiComuni(datiComuniPerCreazione);
						
						params[1] = valoreFlusso;
						params[2] = codicePianoTriennale;
					} else if (CostantiW9.PUBBLICAZIONE_DOCUMENTI == valoreFlusso.intValue()) {
					  
						if (impl.getLong("W9FLUSSI.KEY01") != null && !impl.getLong("W9FLUSSI.KEY01").toString().trim().equals("")) {
							codiceGara = impl.getLong("W9FLUSSI.KEY01");
						}
						if (impl.getLong("W9FLUSSI.KEY02") != null && !impl.getLong("W9FLUSSI.KEY02").toString().trim().equals("")) {
						  codiceLotto = impl.getLong("W9FLUSSI.KEY02");
						}
						Long num = new Long(1);
						if (impl.getLong("W9FLUSSI.KEY04") != null && !impl.getLong("W9FLUSSI.KEY04").toString().trim().equals("")) {
						  num = impl.getLong("W9FLUSSI.KEY04");
						}
					  
						pubblicazioniBean = new PubblicazioneBean();
						pubblicazioniBean.setCodiceGara(codiceGara);
						pubblicazioniBean.setNumeroPubblicazione(num);
						pubblicazioniBean.setDatiComuni(datiComuniPerCreazione);
					  
						params[1] = valoreFlusso;
						params[2] = codiceGara;
						params[4] = num;
					  
					} else {
						if (impl.getLong("W9FLUSSI.KEY01") != null && !impl.getLong("W9FLUSSI.KEY01").toString().trim().equals("")) {
							codiceGara = impl.getLong("W9FLUSSI.KEY01");
						}
						if (impl.getLong("W9FLUSSI.KEY02") != null && !impl.getLong("W9FLUSSI.KEY02").toString().trim().equals("")) {
							codiceLotto = impl.getLong("W9FLUSSI.KEY02");
						}
						Long num = new Long(1);
						if (impl.getLong("W9FLUSSI.KEY04") != null && !impl.getLong("W9FLUSSI.KEY04").toString().trim().equals("")) {
							num = impl.getLong("W9FLUSSI.KEY04");
						}

						garaLottoBean = new GaraLottoBean();
						garaLottoBean.setCodiceGara(codiceGara);
						garaLottoBean.setCodiceLotto(codiceLotto);
						garaLottoBean.setDatiComuni(datiComuniPerCreazione);
						garaLottoBean.setExportIncarichiProfessionali(this.geneManager.getProfili().checkProtec(
				        profiloAttivo, "PAGE", "VIS", "W9.W9LOTT-scheda.INCA"));
						
						params[1] = valoreFlusso;
						params[2] = codiceGara;
						params[3] = codiceLotto;
						params[4] = num;
					}
					
					int numeroErrori = 0;
					int numeroWarning = 0;
					
					//se non sto cancellando la fase faccio il controllo di validazione
					if (tipoInvio != -1) {
						HashMap<String, Object> map = controlliValidazioneManager.validate(params, custom, profiloAttivo);

						numeroErrori = (Integer) map.get("numeroErrori");
						numeroWarning = (Integer) map.get("numeroWarning");
						if (logger.isDebugEnabled()) {
							logger.debug("La validazione dei dati da inviare ha rilevato " + numeroErrori + " errori e " + numeroWarning + " warning");
						}
					}

					if (numeroErrori == 0) {
						logger.debug("valoreFlusso:" + valoreFlusso);
						//se è attivo il loader appalto non serve generare il flusso per il proxy
						if (!isLoaderAppaltoEnable) {
							//se non sto cancellando la fase 
							if (tipoInvio != -1) {
								if (garaLottoBean != null) {  
									  xmlObject = CreazioneXmlManager.getXmlGaraLottoFasi(garaLottoBean, sqlManager, w9Manager);
									} else if (pubblicazioniBean != null) {
									  xmlObject = CreazioneXmlManager.getXmlPubblicazioneDocumento(pubblicazioniBean, sqlManager, w9Manager);
									} else if (avvisoBean != null) {
									  xmlObject = CreazioneXmlManager.getXmlAvviso(avvisoBean, sqlManager, w9Manager);
									} else if (pianoTriennaleBean != null) {
									  xmlObject = CreazioneXmlManager.getXmlPianoTriennale(pianoTriennaleBean, sqlManager,
									      w9Manager, this.getRequest());
									}

							} else {
								//se sto cancellando la fase
								if (garaLottoBean != null) {  
									xmlObject = CreazioneXmlManager.getRichiestaRispostaSincronaEliminazioneSchedaDocument(garaLottoBean, sqlManager);
								}
							}

							// Validazione del messaggio XML prodotto
							// La validazione dell'xml non viene eseguita per quei flussi inviati tramite servizio
							// - Avvisi, programmi, pubblicazioni, anagrafiche gara/lotti
							if (avvisoBean == null && pubblicazioniBean == null && pianoTriennaleBean == null && !valoreFlusso.equals(new Long(988)) ) {
								xmlValidationError = CreazioneXmlManager.validaMsgXML(xmlObject);
							}
						}
						
						if (xmlValidationError != null && xmlValidationError.size() > 0) {
							// Validazione XML non passata: gestione degli
							// errori di validazione per portarli a video

							logger.error("Flusso non inviato per mancata validazione XML. Di seguito i dettagli del messaggio oggetto dell'errore.");
							logger.error("Codice del messaggio: " + valoreFlusso + ") - (codGara = " + codiceGara + ", codLott = " + codiceLotto + ", num =" + progressivoFase + ", uffint = " + uffint
									+ ", codComp = " + codiceCompositore + ")");
							logger.error("Errore nella validazione XML della richiesta da inviare, per i seguenti motivi:");
							for (int i = 0; i < xmlValidationError.size(); i++) {
								logger.error(" " + (i + 1) + ") " + xmlValidationError.get(i).getMessage());
							}
							logger.error("Ecco l'XML non validato: ".concat(xmlObject.toString()));
							codiceErrore = "validazioneXML.flusso";

							if (logger.isDebugEnabled()) {
								logger.error("Messaggio XML non validato: " + xmlObject.toString());
							}

							// Aggiungo nel request il dettaglio degli errori di validazione
							// per visualizzarli nella pagina di destinazione
							this.getRequest().setAttribute("erroriValidazionePerDebug", xmlValidationError);
							this.getRequest().setAttribute(
									"datiErroreFlusso",
									"Dati del flusso che ha generato l'errore: Codice del messaggio: " + valoreFlusso + " - codGara = " + codiceGara + ", codLott = " + codiceLotto + ", num ="
											+ progressivoFase + ", uffint = " + uffint + ", codComp = " + codiceCompositore);
							this.getRequest().setAttribute("msgEsito", "ko");

							// Cancellazione del flusso
							w9Manager.deleteFlussoInvalidoClobXml(impl.getLong("W9FLUSSI.IDFLUSSO"));

							throw new GestoreException("Validazione XML del flusso non superata", "inserimento.flusso");
						} else {
							// La validazione dell'xml e' andata buon fine,
							// quindi e' possibile inviare il flusso a ORT
							ErroreType[] erroriProxy = null;
							List<ValidateEntry> erroriDalServizioPubblicazione = null;
							ErroreType[] erroriLoaderAppalto = null;
							
							// Se e' attivo l'invio al LoaderAppalto di SIMOG non faccio l'invio al proxy
							if (isLoaderAppaltoEnable) {
								// Recupero le informazioni per le credenziali
								// di accesso al servzio
								String recuperaUser = impl.getString("RECUPERAUSER");
								String recuperaPassword = impl.getString("RECUPERAPASS");
								String memorizza = impl.getString("MEMORIZZA");

								ProfiloUtente profiloUtente = (ProfiloUtente) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
								Long syscon = new Long(profiloUtente.getId());

								String simoguser = null;
								String simogpass = null;
								// Leggo le eventuali credenziali memorizzate
								EsportazioneXMLSIMOGManager exportXmlSimogManagerm = (EsportazioneXMLSIMOGManager) UtilitySpring.getBean("esportazioneXMLSIMOGManager", getServletContext(),
										EsportazioneXMLSIMOGManager.class);

								HashMap<String, String> hMapSIMOGLAUserPass = new HashMap<String, String>();
								hMapSIMOGLAUserPass = exportXmlSimogManagerm.recuperaSIMOGLAUserPass(syscon);

								// Gestione USER
								if (recuperaUser != null && "1".equals(recuperaUser)) {
									simoguser = ((String) hMapSIMOGLAUserPass.get("simoguser"));
								} else {
									simoguser = impl.getString("SIMOGUSER");
								}

								// Gestione PASSWORD
								if (recuperaPassword != null && "1".equals(recuperaPassword)) {
									simogpass = ((String) hMapSIMOGLAUserPass.get("simogpass"));
								} else {
									simogpass = impl.getString("SIMOGPASS");
								}
								// Gestione memorizzazione delle credenziali o
								// eventuale cancellazione (se richiesta)
								// verifico se è stato scelto di utilizzare le credenziali RUP
								if (!(memorizza != null && memorizza.equals("credenzialiRup"))) {
									exportXmlSimogManagerm.gestioneWsSimogLoaderAppaltoUserPass(syscon, simoguser, simogpass, memorizza != null);
								}
								//se sto cancellando una fase 
								if (tipoInvio == -1) {
									//se è oggetto di monitoraggio simog 
									if (CostantiSimog.TipiSchede.containsKey(valoreFlusso)) {
										erroriLoaderAppalto = exportXmlSimogManagerm.loaderAppalto(codiceGara, codiceLotto, valoreFlusso, progressivoFase, impl.getLong("W9FLUSSI.IDFLUSSO"), simoguser, simogpass, true);
										//verifico se la cancellazione è andata a buon fine
										Long numxml = (Long) this.geneManager.getSql().getObject("select NUMXML from W9XML where IDFLUSSO = ?", new Object[]{impl.getLong("W9FLUSSI.IDFLUSSO")});
										if (numxml != null) {
											String codice = (String)this.geneManager.getSql().getObject("select CODICE from W9XMLANOM where CODGARA = ? AND CODLOTT = ? AND NUMXML = ?", new Object[]{codiceGara, codiceLotto, numxml});
											if (codice != null) {
												if (codice.equals("ELIMINAZIONE") || codice.equals("SIMOG_MASSLOADER_191")) {
													//cancellazione andata a buon fine oppure la scheda non è presente in SIMOG
													//sposto i flussi in W9FLUSSI_ELIMINATI
													exportXmlSimogManagerm.CancellazioneFlusso(codiceGara, codiceLotto, valoreFlusso, progressivoFase,impl.getString("NOTEINVIO"));
													w9Manager.updateSituazioneGaraLotto(codiceGara, codiceLotto);
													this.getRequest().setAttribute("msgCancellazione", "ok");
												}
											}
										}
									} else {
										//se la scheda non è oggetto di monitoraggio simog
										//sposto i flussi in W9FLUSSI_ELIMINATI
										exportXmlSimogManagerm.CancellazioneFlusso(codiceGara, codiceLotto, valoreFlusso, progressivoFase,impl.getString("NOTEINVIO"));
										w9Manager.updateSituazioneGaraLotto(codiceGara, codiceLotto);
										this.getRequest().setAttribute("msgCancellazione", "ok");
									}
									
								} else {
									//invio la fase
									erroriLoaderAppalto = exportXmlSimogManagerm.loaderAppalto(codiceGara, codiceLotto, valoreFlusso, progressivoFase, impl.getLong("W9FLUSSI.IDFLUSSO"), simoguser, simogpass, false);	
								}
							} else {
								if (avvisoBean != null || pubblicazioniBean != null || pianoTriennaleBean != null || (garaLottoBean != null && valoreFlusso.intValue() == CostantiW9.ANAGRAFICA_GARA_LOTTI) ) {
									erroriDalServizioPubblicazione = this.pubblica(avvisoBean, pubblicazioniBean, garaLottoBean, pianoTriennaleBean, impl);
									if (erroriDalServizioPubblicazione != null) {
										for(ValidateEntry errore:erroriDalServizioPubblicazione) {
											if (errore.getTipo().equals("E")) {
												erroriBloccanti = true;
												break;
											}
										}
									}
									//impl.getLong("W9FLUSSI.IDFLUSSO")
								} else {
									erroriProxy = this.inviaXML(xmlObject, impl, idEgov);
								}
							}
							if ((erroriProxy == null || (erroriProxy != null && erroriProxy.length == 0)) && 
									!erroriBloccanti && 
										(erroriLoaderAppalto == null || (erroriLoaderAppalto != null && erroriLoaderAppalto.length == 0))) {
								if (!isLoaderAppaltoEnable) {
									// Invio del flusso al proxy avvenuto con successo

									// Salvataggio di alcuni valori solo se si sta inviando
									// una anagrafica gara/lotto
									if (valoreFlusso.intValue() == CostantiW9.ANAGRAFICA_GARA_LOTTI) {
										// Salvataggio idEgov della Gara
										if (idEgov != null && !"".equals(idEgov)) {
											w9Manager.aggiornaW9GARAIdEgov(codiceGara, idEgov.toString());
										}
										// Dopo l’invio con successo del flusso dell’anagrafica, impostare
										// a ‘2’ il campo DAEXPORT di tutti i lotti contenuti nella gara.
										w9Manager.aggiornaW9LOTT_DAEXPORT(codiceGara);
										
									} else {
										// Dopo l’invio con successo del flusso metodo per aggiornare la fase del lotto
										// impostare a ‘2’ il campo DAEXPORT della fase corrispondente (W9FASI)
										w9Manager.aggiornaW9FASI_DAEXPORT(codiceGara, codiceLotto, valoreFlusso, progressivoFase);
									}

									String valoreXml = impl.getString("W9FLUSSI.XML");
									w9Manager.updateClobXml(impl.getLong("W9FLUSSI.IDFLUSSO"), valoreXml);
									w9Manager.aggiornaCig(codiceGara);

									// Aggiornamento della situazione della gara e del lotto dopo il primo invio
									// di qualsiasi flusso, ad eccezione del flusso Anagrafica Gara/Lotto.
									// Per il flusso Comunicazione Esito e' necessario calcolare la situazione gara lotto
									// perche' una rettifica potrebbe dire che un lotto non e' stato aggiudicato.
									// Per il flusso Anagrafica gara/lotto e' necessario calcolare la situazione gara lotto
									// perche' la rettifica prevede la cancellazione delle occorrenze esistenti e quindi e' necessario
									// rivalorizzare i campi SITUAZIONE di W9GARA e W9LOTT.

									if (valoreFlusso.intValue() != CostantiW9.PROGRAMMA_TRIENNALE_FORNITURE_SERVIZI 
												&& valoreFlusso.intValue() != CostantiW9.PROGRAMMA_TRIENNALE_LAVORI
													&& valoreFlusso.intValue() != CostantiW9.PROGRAMMA_BIENNALE_ACQUISTI 
														&& valoreFlusso.intValue() != CostantiW9.PROGRAMMA_TRIENNALE_OPERE_PUBBLICHE
															&& valoreFlusso.intValue() != CostantiW9.PUBBLICAZIONE_AVVISO 
																&& valoreFlusso.intValue() != CostantiW9.GARE_ENTI_NAZIONALI_O_SOTTO_40000) {
										Long provDato = (Long) sqlManager.getObject("select PROV_DATO from W9GARA where CODGARA=?", new Object[] { new Long(codiceGara) });
										
										//Se la gara non è smartcig aggiorno il campo SITUAZIONE
										if (!new Long(4).equals(provDato)) {
											w9Manager.updateSituazioneGaraLotto(codiceGara, codiceLotto);
										}
									}
									
									getRequest().setAttribute("msgEsito", "ok");
								} else {
									// In modalita' Vigilanza l'esito positivo dell'invio dipende anche se il campo W9XML.NUM_ERRORE
									// e' non valorizzato oppure uguale a 0. 
									
									Long idFlusso = impl.getLong("W9FLUSSI.IDFLUSSO");
									Long numeroErroriLoaderAppalto = (Long) this.sqlManager.getObject(
											"select NUM_ERRORE from W9XML where CODGARA=? and CODLOTT=? and IDFLUSSO=?",
													new Object[] { codiceGara, codiceLotto, idFlusso });
									if (numeroErroriLoaderAppalto == null || (numeroErroriLoaderAppalto != null && numeroErroriLoaderAppalto.longValue() == 0)) {
										
										// Aggiornamento del campo W9FASI.DAEXPORT in base all'esito dell'operazione di invio dei dati a SIMOG
										this.aggiornaStatoFase(codiceGara, codiceLotto, valoreFlusso, progressivoFase, tipoInvio == -1);
										
										getRequest().setAttribute("msgEsito", "ok");
										if (tipoInvio != -1 || (tipoInvio == -1 && CostantiW9.COMUNICAZIONE_ESITO == valoreFlusso)) {
											w9Manager.updateSituazioneGaraLotto(codiceGara, codiceLotto);
										}
									} else {
										getRequest().setAttribute("msgEsito", "ko");
										getRequest().setAttribute("msgEsitoLoaderAppalto", "ko");
									}
								}

							} else {
								
								// Errori di invio del flusso generati dal proxy
								List<String> listaErroriProxy = new ArrayList<String>();
								String tmp0 = null;
								String postCodiceErrore = null;
								
								if (erroriProxy != null && erroriProxy.length > 0) {
									for (int i = 0; i < erroriProxy.length && postCodiceErrore == null; i++) {
										ErroreType errore = erroriProxy[i];

										if (StringUtils.isNotEmpty(errore.getTipo().toString())) {
											postCodiceErrore = errore.getTipo().toString();
										}

										tmp0 = "Errore " + (i + 1) + " - tipo:" + erroriProxy[i].getTipo() + " - codice: " + erroriProxy[i].getCodice() + " - tag errato: " + erroriProxy[i].getTagErrato()
												+ " - descrizione: " + erroriProxy[i].getDescrizione();
										listaErroriProxy.add(tmp0);

										logger.error(tmp0);

										postCodiceErrore = null;
									}
								} else if (erroriDalServizioPubblicazione != null && erroriDalServizioPubblicazione.size() > 0) {
									for(ValidateEntry errore:erroriDalServizioPubblicazione) {
										if (errore.getTipo().equals("E")) {
											tmp0 = "Errore Campo: " + errore.getNome() + " - descrizione: " + errore.getMessaggio();	
										} else {
											tmp0 = "Warning Campo: " + errore.getNome() + " - descrizione: " + errore.getMessaggio();
										}
										listaErroriProxy.add(tmp0);
										logger.error(tmp0);
									}
								} else if (erroriLoaderAppalto != null && erroriLoaderAppalto.length > 0) {
									for (int i = 0; i < erroriLoaderAppalto.length && postCodiceErrore == null; i++) {
										ErroreType errore = erroriLoaderAppalto[i];

										if (StringUtils.isNotEmpty(errore.getTipo().toString())) {
											postCodiceErrore = errore.getTipo().toString();
										}

										tmp0 = "Errore " + (i + 1) + " - tipo:" + erroriLoaderAppalto[i].getTipo() + " - codice: " + erroriLoaderAppalto[i].getCodice() + " - tag errato: " + erroriLoaderAppalto[i].getTagErrato()
												+ " - descrizione: " + erroriLoaderAppalto[i].getDescrizione();
										listaErroriProxy.add(tmp0);
										logger.error(tmp0);
										postCodiceErrore = null;
									}
								}
								// codiceErrore = "invioFlusso.proxy";
								// Aggiungo nel request il dettaglio degli errori del proxy per visualizzarli nella pagina di destinazione
								this.getRequest().setAttribute("erroriProxyPerDebug", listaErroriProxy);
								this.getRequest().setAttribute("datiErroreFlusso",
										"Dati del flusso che ha generato l'errore: Codice del messaggio: " + valoreFlusso + " - codGara = " + codiceGara 
												+ ", codLott = " + codiceLotto + ", num =" + progressivoFase + ", uffint = " + uffint + ", codComp = " + codiceCompositore);
								this.getRequest().setAttribute("msgEsito", "ko");

								w9Manager.deleteFlussoInvalidoClobXml(impl.getLong("W9FLUSSI.IDFLUSSO"));

								throw new GestoreException("Errore sul messaggio XML da parte del proxy", "invioFlusso.proxy");
							}
						}

					} else {
						// La riesecuzione del controllo dei dati ha rilevato ancora errori
						w9Manager.deleteFlussoInvalidoClobXml(impl.getLong("W9FLUSSI.IDFLUSSO"));
						logger.error("Il Controllo dei Dati Inseriti ha rilevato degli errori: non e' possibile creare il flusso " + "(Codice messaggio: " + valoreFlusso + ") - (codGara = "
								+ codiceGara + ", codLott = " + codiceLotto + ", num =" + progressivoFase + ", uffint = " + uffint + ", codComp = " + codiceCompositore + ")");
						// codiceErrore = "inserimento.flusso";
						throw new GestoreException("Il Controllo dei Dati Inseriti ha rilevato degli errori: non e' possibile creare il flusso", "inserimento.flusso");
					}
				}
			} catch (GestoreException e) {
				w9Manager.deleteFlussoInvalidoClobXml(impl.getLong("W9FLUSSI.IDFLUSSO"));
				throw e;
			} catch (Exception e) {
				w9Manager.deleteFlussoInvalidoClobXml(impl.getLong("W9FLUSSI.IDFLUSSO"));
				if (codiceErrore.equals("")) {
					logger.error("Errore durante il recupero dei dati necessari alla creazione del flusso. (Codice messaggio: " + valoreFlusso + ")");
					codiceErrore = "recuperodati.flusso";
				}
				throw new GestoreException("Errore durante il recupero dei dati necessari alla creazione del flusso", codiceErrore, e);
			}
		}
	}

	/**
	 * Validazione del messaggio XML prodotto prima dell'invio al proxy.
	 * 
	 * @param oggettoDoc
	 *            xml object
	 * @return Ritorna un ArrayList di XmlValidationError nel caso la
	 *         validazione del messaggio non sia rispettata.
	 * @throws GestoreException
	 *             GestoreException
	 * @throws TransformerException
	 *             TransformerException
	 * @throws IOException
	 *             IOException
	 */
	public final ArrayList<XmlValidationError> validaMsgXML(final XmlObject oggettoDoc) throws GestoreException, TransformerException, IOException {
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
	 * Invio del flusso al proxy.
	 * 
	 * @param oggettoDoc
	 *            xml object
	 * @param impl
	 *            data column container
	 * @param idEgov
	 *            IdEgov
	 * @return Ritorna l'oggetto ErroreType[] contenente gli eventuali errori
	 *         nell'invio del xml
	 * @throws GestoreException
	 *             GestoreException
	 * @throws TransformerException
	 *             TransformerException
	 * @throws IOException
	 *             IOException
	 */
	private final ErroreType[] inviaXML(final XmlObject oggettoDoc, final DataColumnContainer impl, final StringBuffer idEgov) throws GestoreException, TransformerException, IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("inviaXML: inizio metodo");
		}
		boolean esito = false;
		String ingressoXml = "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		oggettoDoc.save(baos);
		ingressoXml = baos.toString();
		baos.close();

		if (logger.isDebugEnabled()) {
			logger.debug("Richiesta XML da inviare: " + ingressoXml);
		}
		String out;
		ErroreType[] erroriDalProxy = null;
		try {
			SitatProxy servizio = new SitatProxy();
			String endpoint = ConfigManager.getValore(CostantiSitatSA.SITAT_PROXY);
			servizio.setEndpoint(endpoint);

			out = servizio.sendEventoSitat(oggettoDoc.toString());

			RispostaRichiestaRispostaSincronaEventoSitatDocument document = RispostaRichiestaRispostaSincronaEventoSitatDocument.Factory.parse(out);
			RispostaRichiestaRispostaSincronaEventoSitatType risposta = document.getRispostaRichiestaRispostaSincronaEventoSitat();
			esito = risposta.getEsito();

			if (logger.isDebugEnabled()) {
				logger.debug("Esito dell'invio della richiesta: " + esito);
			}
			if (!esito) {
				DettaglioErrori dettaglioErrori = risposta.getDettaglioErrori();
				if (dettaglioErrori != null && dettaglioErrori.getErroreArray() != null && dettaglioErrori.getErroreArray().length > 0) {
					erroriDalProxy = dettaglioErrori.getErroreArray();
				}
				logger.error("Messaggi di errore di ritorno dal proxy:");
				for (int i = 0; i < erroriDalProxy.length; i++) {
					logger.error("Errore " + (i + 1) + " - tipo:" + erroriDalProxy[i].getTipo() + " - codice: " + erroriDalProxy[i].getCodice() + " - tag errato: " + erroriDalProxy[i].getTagErrato()
							+ " - descrizione: " + erroriDalProxy[i].getDescrizione());
				}
			} else {
				idEgov.append(risposta.getIdEgov());
				impl.addColumn("W9FLUSSI.XML", JdbcParametro.TIPO_TESTO, ingressoXml);
			}
		} catch (JAXRPCException e) {
			logger.error("Errore dal web service: segue l'errore lato proxy: " + e.getMessage() + "StackTrace: " + e.getStackTrace());
			logger.error("Messaggio XML non inviato: " + oggettoDoc.toString());
			throw new GestoreException("Errore nell'identificazione url del proxy.", "proxy.url", e);
		} catch (RemoteException e) {
			logger.error("Errore nell'invio del flusso: segue l'errore lato proxy: " + e.getMessage() + "StackTrace: " + e.getStackTrace());
			logger.error("Messaggio XML non inviato: " + oggettoDoc.toString());
			throw new GestoreException("Si sono verificati degli errori in fase di invio all\'Osservatorio Regionale: il flusso non e' stato creato", "invio.flusso", e);
		} catch (XmlException e) {
			logger.error("Errore nella validazione dell'XML prodotto per l'invio del flusso: segue " + "l'errore lato proxy: " + e.getMessage() + "\nStackTrace: " + e.getStackTrace());
			logger.error("Messaggio XML non inviato: " + oggettoDoc.toString());

			throw new GestoreException("La validazione dell\'xml creato per l'invio del flusso ha avuto esito negativo: il flusso non e' stato creato", "validazionexml.flusso", e);
		}

		return erroriDalProxy;
	}

	private final List<ValidateEntry> pubblica(AvvisoBean avvisoBean, PubblicazioneBean attoBean, GaraLottoBean garaLottoBean, PianoTriennaleBean pianoTriennaleBean, final DataColumnContainer impl) throws GestoreException {
		boolean goToPubblicazione = false;
		String token = "";
		int esito;
		Long idAvviso = null;
		Long codSistema = null;
		Long codGara = null;
		Long numeroPubblicazione = null;
		Long contri = null;
		//Long fase = null;
		List<ValidateEntry> erroriDalServizioPubblicazione = new ArrayList<ValidateEntry>();
		try {
			W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager", this.getServletContext(),
				    W9Manager.class);
			if (attoBean != null) {
				codGara = attoBean.getCodiceGara();
				numeroPubblicazione = attoBean.getNumeroPubblicazione();
			} else if (avvisoBean != null) {
				codSistema = avvisoBean.getCodiceSistema();
				idAvviso = avvisoBean.getCodiceAvviso();
			} else if (garaLottoBean != null) {
				codGara = garaLottoBean.getCodiceGara();
				//fase = garaLottoBean.getDatiComuniXml().getCodiceFase();
			} else if (pianoTriennaleBean != null) {
				contri = pianoTriennaleBean.getCodicePianoTriennale();
			}
			
			ScpManager scpManager = (ScpManager) UtilitySpring.getBean("scpManager", this.getServletContext(),
					ScpManager.class);
			String codUffint = (String) this.getRequest().getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
			
			// Indirizzo web service
			String login = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_USERNAME);
			String password = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_PASSWORD);
			String url = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URL);
			String urlLogin = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URL_LOGIN);
			String urlProgrammi = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URLPROGRAMMI);
		    String urlTabelleContesto = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URLTABELLECONTESTO);
		    String idClient = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_IDCLIENT);
		    String keyClient = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_KEYCLIENT);
		    if (StringUtils.isEmpty(login) ||
		        StringUtils.isEmpty(password) ||
		        StringUtils.isEmpty(urlLogin) ||
		        StringUtils.isEmpty(urlTabelleContesto) ||
		        StringUtils.isEmpty(idClient) ||
		        StringUtils.isEmpty(keyClient)) {
		    	throw new GestoreException(
				          "Verificare i parametri per la connessione al servizio di pubblicazione",
				          "gestioneSCPSA.ws.url.error");
		    }
		    if ((attoBean != null || avvisoBean != null || garaLottoBean != null) &&
		    		(StringUtils.isEmpty(url)) ) {
		    	throw new GestoreException(
				          "Verificare i parametri per la connessione al servizio di pubblicazione",
				          "gestioneSCPSA.ws.url.error");
		    }
		    if (pianoTriennaleBean != null  && (urlProgrammi == null || "".equals(urlProgrammi)) ) {
		    	throw new GestoreException(
				          "Verificare i parametri per la connessione al servizio di pubblicazione",
				          "gestioneSCPSA.ws.url.error");
		    }
		    Client client = ClientBuilder.newClient();
		    WebTarget webTarget = client.target(urlLogin).path("Account/LoginPubblica");
		    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
		    formData.add("username", login);
		    formData.add("password", password);
		    formData.add("clientKey", keyClient);
		    formData.add("clientId", idClient);
		    
		    Response accesso = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(formData,MediaType.APPLICATION_FORM_URLENCODED));
		    esito = accesso.getStatus();
		    LoginResult resultAccesso = accesso.readEntity(LoginResult.class);
		    if (resultAccesso.isEsito()) {
		    	token = resultAccesso.getToken();
		    	//Login andato a buon fine - invio dati della stazione appaltante
		    	DatiGeneraliStazioneAppaltanteEntry stazioneAppaltante = new DatiGeneraliStazioneAppaltanteEntry();
		    	scpManager.valorizzaStazioneAppaltante(stazioneAppaltante,codUffint);
		    	webTarget = client.target(urlTabelleContesto).path("StazioniAppaltanti/Pubblica").queryParam("token", token);
		    	Response verificaStazioneAppaltante = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(stazioneAppaltante), Response.class);
		    	esito = verificaStazioneAppaltante.getStatus();
		    	InserimentoResult risultatoValidazioneSA = verificaStazioneAppaltante.readEntity(InserimentoResult.class);
		    	switch (esito) {
		    		case 200:
		    			goToPubblicazione = true;
		    			break;
		    		case 400:
		    			erroriDalServizioPubblicazione = risultatoValidazioneSA.getValidate();
		    			break;
		    		default:
		    			ValidateEntry error = new ValidateEntry();
		    			error.setNome("attenzione");
		    			error.setMessaggio(risultatoValidazioneSA.getError());
		    			erroriDalServizioPubblicazione.add(error);
		    			break;
		    	}
		    	ObjectMapper mapper = new ObjectMapper();
		    	if (goToPubblicazione) {
		    		if (avvisoBean != null) {
		    			PubblicaAvvisoEntry avviso = new PubblicaAvvisoEntry();
						scpManager.valorizzaAvviso(avviso, codUffint, idAvviso, codSistema);
						webTarget = client.target(url).path("Avvisi/Pubblica").queryParam("token", token).queryParam("modalitaInvio", "2");
						Response risultato = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(avviso), Response.class);
						esito = risultato.getStatus();
						PubblicazioneResult risultatoPubblicazione = risultato.readEntity(PubblicazioneResult.class);
						switch (esito) {
							case 200:
								//inserimento flusso
								erroriDalServizioPubblicazione = risultatoPubblicazione.getValidate();
								avviso.setIdRicevuto(risultatoPubblicazione.getId());
								impl.addColumn("W9FLUSSI.XML", JdbcParametro.TIPO_TESTO, mapper.writeValueAsString(avviso));
								w9Manager.updateFlussoAvviso(avviso,idAvviso,codUffint);
								break;
							case 400:
								erroriDalServizioPubblicazione = risultatoPubblicazione.getValidate();
				    			break;
							default:
								ValidateEntry error = new ValidateEntry();
				    			error.setNome("attenzione");
				    			error.setMessaggio(risultatoPubblicazione.getError());
				    			erroriDalServizioPubblicazione.add(error);
								break;
						}
		    		} else if (attoBean != null) {
		    			PubblicaAttoEntry pubblicazione = new PubblicaAttoEntry();
						scpManager.valorizzaAtto(pubblicazione, codGara, numeroPubblicazione);
						webTarget = client.target(url).path("Atti/Pubblica").queryParam("token", token).queryParam("modalitaInvio", "2");
						Response risultato = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(pubblicazione), Response.class);
						esito = risultato.getStatus();
						PubblicazioneAttoResult risultatoPubblicazione = risultato.readEntity(PubblicazioneAttoResult.class);
						switch (esito) {
							case 200:
								//inserimento flusso
								erroriDalServizioPubblicazione = risultatoPubblicazione.getValidate();
								pubblicazione.setIdRicevuto(risultatoPubblicazione.getIdExArt29());
								pubblicazione.getGara().setIdRicevuto(risultatoPubblicazione.getIdGara());
								impl.addColumn("W9FLUSSI.XML", JdbcParametro.TIPO_TESTO, mapper.writeValueAsString(pubblicazione));
								w9Manager.updateFlussoExArt29(pubblicazione, codGara, numeroPubblicazione);
								break;
							case 400:
								erroriDalServizioPubblicazione = risultatoPubblicazione.getValidate();
								break;
							default:
								ValidateEntry error = new ValidateEntry();
				    			error.setNome("attenzione");
				    			error.setMessaggio(risultatoPubblicazione.getError());
				    			erroriDalServizioPubblicazione.add(error);
								break;
						}
		    		} else if (garaLottoBean != null) {
		    			PubblicaGaraEntry gara = new PubblicaGaraEntry();
						scpManager.valorizzaGara(gara, codGara, null);
						webTarget = client.target(url).path("Anagrafiche/GaraLotti").queryParam("token", token).queryParam("modalitaInvio", "2");
						Response risultato = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(gara), Response.class);
						esito = risultato.getStatus();
						PubblicazioneResult risultatoPubblicazione = risultato.readEntity(PubblicazioneResult.class);
						switch (esito) {
							case 200:
								//inserimento flusso
								erroriDalServizioPubblicazione = risultatoPubblicazione.getValidate();
								gara.setIdRicevuto(risultatoPubblicazione.getId());
								impl.addColumn("W9FLUSSI.XML", JdbcParametro.TIPO_TESTO, mapper.writeValueAsString(gara));
								w9Manager.updateFlussoAnagraficaGaraLotti(gara, codGara);
								break;
							case 400:
								erroriDalServizioPubblicazione = risultatoPubblicazione.getValidate();
								break;
							default:
								ValidateEntry error = new ValidateEntry();
				    			error.setNome("attenzione");
				    			error.setMessaggio(risultatoPubblicazione.getError());
				    			erroriDalServizioPubblicazione.add(error);
								break;
						}
		    		} else if (pianoTriennaleBean != null) {
		    			int tipoProgramma = scpManager.getTipoProgramma(contri);
			    		Response risultato = null;
			    		PubblicaProgrammaLavoriEntry programma = null;
			    		PubblicaProgrammaFornitureServiziEntry programmaFS = null;
			    		PubblicaProgrammaDM112011Entry programmaDM112011 = null;
			    		switch (tipoProgramma) {
			    			case 1:
			    				//Programma Lavori
			    				programma = new PubblicaProgrammaLavoriEntry();
								scpManager.valorizzaProgrammaLavori(programma, contri);
								webTarget = client.target(urlProgrammi).path("Programmi/PubblicaLavori").queryParam("token", token).queryParam("modalitaInvio", "2");
								risultato = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(programma), Response.class);
			    				break;
			    			case 2:
			    				//Programma Forniture Servizi
			    				programmaFS = new PubblicaProgrammaFornitureServiziEntry();
								scpManager.valorizzaProgrammaFornitureServizi(programmaFS, contri);
								webTarget = client.target(urlProgrammi).path("Programmi/PubblicaFornitureServizi").queryParam("token", token).queryParam("modalitaInvio", "2");
								risultato = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(programmaFS), Response.class);
			    				break;
			    			case 3:
			    				//Programma DM 11 del 2011
			    				programmaDM112011 = new PubblicaProgrammaDM112011Entry();
								scpManager.valorizzaProgrammaDM112011(programmaDM112011, contri);
								webTarget = client.target(urlProgrammi).path("Programmi/PubblicaDM112011").queryParam("token", token).queryParam("modalitaInvio", "2");
								risultato = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(programmaDM112011), Response.class);
			    				break;
			    		}
			    		esito = risultato.getStatus();
						PubblicazioneResult risultatoPubblicazione = risultato.readEntity(PubblicazioneResult.class);
						switch (esito) {
							case 200:
								//inserimento flusso
								erroriDalServizioPubblicazione = risultatoPubblicazione.getValidate();
								switch (tipoProgramma) {
									case 1:
										//Programma Lavori
										programma.setIdRicevuto(risultatoPubblicazione.getId());
										impl.addColumn("W9FLUSSI.XML", JdbcParametro.TIPO_TESTO, mapper.writeValueAsString(programma));
										break;
									case 2:
					    				//Programma Forniture Servizi
										programmaFS.setIdRicevuto(risultatoPubblicazione.getId());
										impl.addColumn("W9FLUSSI.XML", JdbcParametro.TIPO_TESTO, mapper.writeValueAsString(programmaFS));
										break;
									case 3:
					    				//Programma DM 11 del 2011
										programmaDM112011.setIdRicevuto(risultatoPubblicazione.getId());
										impl.addColumn("W9FLUSSI.XML", JdbcParametro.TIPO_TESTO, mapper.writeValueAsString(programmaDM112011));
										break;
								}
								w9Manager.updateFlussoProgramma(programma,programmaFS,programmaDM112011,contri);
								break;
							case 400:
								erroriDalServizioPubblicazione = risultatoPubblicazione.getValidate();
								break;
							default:
								ValidateEntry error = new ValidateEntry();
				    			error.setNome("attenzione");
				    			error.setMessaggio(risultatoPubblicazione.getError());
				    			erroriDalServizioPubblicazione.add(error);
								break;
						}
		    		}
		    	}
		    } else {
		    	if (resultAccesso.getError() == null || resultAccesso.getError().equals("")) {
		    		resultAccesso.setError("Si è verificato un problema nel servizio di autenticazione. Contattare l'amministratore");
		    	}
		    	ValidateEntry error = new ValidateEntry();
    			error.setNome("attenzione");
    			error.setMessaggio(resultAccesso.getError());
    			erroriDalServizioPubblicazione.add(error);
		    }
			
		} catch (Exception s) {
			ValidateEntry error = new ValidateEntry();
			error.setNome("error");
			error.setMessaggio(s.getMessage());
			erroriDalServizioPubblicazione.add(error);
		}
		return erroriDalServizioPubblicazione;
	}
	
	
	/**
	 * Aggiornamento dello stato di esportazione per la gara ed il lotto indicati.
	 * 
	 * @param codgara codice della gara
	 * @param codlott codice del lotto
	 * @param fase_esecuzione fase di esecuzione
	 * @param num progressivo fase
	 * @throws GestoreException GestoreException
	 */
	private void aggiornaStatoFase(Long codgara, Long codlott, Long fase_esecuzione, Long num, boolean eliminaScheda) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaStatoFase: inizio metodo");
		}
		
		String updateW9FASI = null;
		List<Object> listaParametri = new ArrayList<Object>();
		listaParametri.add(codgara);
		listaParametri.add(codlott);
		listaParametri.add(fase_esecuzione);
		if (num != null) {
			updateW9FASI = "update W9FASI set DAEXPORT=? where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=?";
			listaParametri.add(num);
		} else {
			updateW9FASI = "update W9FASI set DAEXPORT=? where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=?";
		}
		try {
			TransactionStatus status = sqlManager.startTransaction();
			Long numRecordW9INBOX = (Long) this.sqlManager.getObject("select count(*) from W9INBOX", null);
			if (numRecordW9INBOX.longValue() > 0) {
				// Se in configurazione Osservatorio (la W9INBOX contiene record) metto la fase in stato 
				// gia' inviata a prescindere dall'esito dell'operazione di invio dati a SIMOG 
				listaParametri.add(0, "2");
				this.sqlManager.update(updateW9FASI, listaParametri.toArray());
			} else {
				// Se in configurazione Vigilanza (la W9INBOX non contiene record)
				if (eliminaScheda) {
					listaParametri.add(0, "1");
					this.sqlManager.update(updateW9FASI, listaParametri.toArray());
				} else {
					listaParametri.add(0, "2");
					this.sqlManager.update(updateW9FASI, listaParametri.toArray());
				}
			}
			this.sqlManager.commitTransaction(status);
		} catch (SQLException e) {
			throw new GestoreException("Errore nell'aggiornamento dello stato di esportazione della fase", "exportxml.sqlerror", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaStatoFase: fine metodo");
		}
	}

	
}