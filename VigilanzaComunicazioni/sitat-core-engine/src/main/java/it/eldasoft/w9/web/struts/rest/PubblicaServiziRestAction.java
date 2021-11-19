package it.eldasoft.w9.web.struts.rest;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.bl.rest.ScpManager;
import it.eldasoft.w9.common.CostantiServiziRest;
import it.eldasoft.w9.dao.vo.rest.DatiGeneraliStazioneAppaltanteEntry;
import it.eldasoft.w9.dao.vo.rest.InserimentoResult;
import it.eldasoft.w9.dao.vo.rest.LoginResult;
import it.eldasoft.w9.dao.vo.rest.PubblicaAttoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicaAvvisoEntry;
import it.eldasoft.w9.dao.vo.rest.PubblicazioneAttoResult;
import it.eldasoft.w9.dao.vo.rest.PubblicazioneResult;
import it.eldasoft.w9.dao.vo.rest.programmi.dm112011.PubblicaProgrammaDM112011Entry;
import it.eldasoft.w9.dao.vo.rest.programmi.fornitureservizi.PubblicaProgrammaFornitureServiziEntry;
import it.eldasoft.w9.dao.vo.rest.programmi.lavori.PubblicaProgrammaLavoriEntry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action per eseguire la pubblicazione di una pubblicazione(Atti ex art.29) .
 */
public class PubblicaServiziRestAction extends ActionBaseNoOpzioni {

	static Logger logger = Logger.getLogger(PubblicaServiziRestAction.class);

	private ScpManager scpManager;
	
	public void setScpManager(ScpManager scpManager) {
		this.scpManager = scpManager;
	}
	
	/**
	 * @see it.eldasoft.gene.commons.web.struts.ActionBase#runAction(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward runAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String target = CostantiGeneraliStruts.FORWARD_OK;
		
		ProfiloUtente profilo = (ProfiloUtente) request.getSession().getAttribute(
		          CostantiGenerali.PROFILO_UTENTE_SESSIONE);
		String codUffint = (String) request.getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
		boolean goToPubblicazione = false;
		Long codGara = null;
		Long numeroPubblicazione = null;
		String codein = null;
		Long idAvviso = null;
		Long codSistema = null;
		Long contri = null;
		String entita = request.getParameter("entita");
		if (entita.equals("pubblicazioni")) {
			codGara = new Long(request.getParameter("codGara"));
			numeroPubblicazione = new Long(request.getParameter("numeroPubblicazione"));
		} else if (entita.equals("programmi")) {
			contri = new Long(request.getParameter("contri"));
		} else if (entita.equals("avvisi")) {
			codein = request.getParameter("codein");
			idAvviso = new Long(request.getParameter("idAvviso"));
			codSistema = new Long(request.getParameter("codSistema"));
		}
		String token = "";
		int esito;
		try {
			// Indirizzo web service
			String login = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_USERNAME);
			String password = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_PASSWORD);
			String url = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URL);
			String urlProgrammi = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URLPROGRAMMI);
		    String urlLogin = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URL_LOGIN);
		    String urlTabelleContesto = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_URLTABELLECONTESTO);
		    String idClient = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_IDCLIENT);
		    String keyClient = ConfigManager.getValore(CostantiServiziRest.PROP_WS_PUBBLICAZIONI_KEYCLIENT);
		    if (login == null || "".equals(login) ||
		    		password == null || "".equals(password) ||
		    		urlLogin == null || "".equals(urlLogin) ||
		    		urlTabelleContesto == null || "".equals(urlTabelleContesto) ||
		    		idClient == null || "".equals(idClient) ||
		    		keyClient == null || "".equals(keyClient)) {
		    	throw new GestoreException(
				          "Verificare i parametri per la connessione al servizio di pubblicazione",
				          "gestioneSCPSA.ws.url.error");
		    }
		    if ((entita.equals("pubblicazioni") || entita.equals("avvisi")) &&
		    		(url == null || "".equals(url)) ) {
		    	throw new GestoreException(
				          "Verificare i parametri per la connessione al servizio di pubblicazione",
				          "gestioneSCPSA.ws.url.error");
		    }
		    if (entita.equals("programmi")  && (urlProgrammi == null || "".equals(urlProgrammi)) ) {
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
		    	webTarget = client.target(urlTabelleContesto).path("StazioniAppaltanti/PubblicaConStorico").queryParam("token", token);
		    	Response verificaStazioneAppaltante = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(stazioneAppaltante), Response.class);
		    	esito = verificaStazioneAppaltante.getStatus();
		    	InserimentoResult risultatoValidazioneSA = verificaStazioneAppaltante.readEntity(InserimentoResult.class);
		    	switch (esito) {
		    		case 200:
		    			goToPubblicazione = true;
		    			break;
		    		case 400:
		    			request.setAttribute("validate", risultatoValidazioneSA.getValidate());
						target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
		    			break;
		    		default:
		    			request.setAttribute("error", risultatoValidazioneSA.getError());
						target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
		    			break;
		    	}
		    	if (goToPubblicazione) {
		    		if (entita.equals("pubblicazioni")) {
			    		PubblicaAttoEntry pubblicazione = new PubblicaAttoEntry();
						scpManager.valorizzaAtto(pubblicazione, codGara, numeroPubblicazione);
						webTarget = client.target(url).path("Atti/Pubblica").queryParam("token", token).queryParam("modalitaInvio", "2");
						Response risultato = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(pubblicazione), Response.class);
						esito = risultato.getStatus();
						PubblicazioneAttoResult risultatoPubblicazione = risultato.readEntity(PubblicazioneAttoResult.class);
						switch (esito) {
							case 200:
								//inserimento flusso
								pubblicazione.setIdRicevuto(risultatoPubblicazione.getIdExArt29());
								pubblicazione.getGara().setIdRicevuto(risultatoPubblicazione.getIdGara());
								scpManager.inserimentoFlussoExArt29(pubblicazione, profilo, codGara, numeroPubblicazione);
								request.setAttribute("flusso", "ok");
								break;
							case 400:
								request.setAttribute("validate", risultatoPubblicazione.getValidate());
								target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
								break;
							default:
								request.setAttribute("error", risultatoPubblicazione.getError());
								target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
								break;
						}
			    	} else if (entita.equals("programmi")) {
			    		//String nuovoTitoloProgramma = "";
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
								//Gestione automatica campo Titolo del programma
								//nuovoTitoloProgramma = programma.getDescrizione();
								//nuovoTitoloProgramma = scpManager.gestioneAutomaticaTitoloProgramma(programma.getDescrizione(), new Long(1), programma.getAnno(), programma.getDataApprovazione(), contri);
								//programma.setDescrizione(nuovoTitoloProgramma);
								webTarget = client.target(urlProgrammi).path("Programmi/PubblicaLavori").queryParam("token", token).queryParam("modalitaInvio", "2");
								risultato = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(programma), Response.class);
			    				break;
			    			case 2:
			    				//Programma Forniture Servizi
			    				programmaFS = new PubblicaProgrammaFornitureServiziEntry();
								scpManager.valorizzaProgrammaFornitureServizi(programmaFS, contri);
								//Gestione automatica campo Titolo del programma
								//nuovoTitoloProgramma = programmaFS.getDescrizione();
								//nuovoTitoloProgramma = scpManager.gestioneAutomaticaTitoloProgramma(programmaFS.getDescrizione(), new Long(2), programmaFS.getAnno(), programmaFS.getDataApprovazione(), contri);
								//programmaFS.setDescrizione(nuovoTitoloProgramma);
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
								switch (tipoProgramma) {
									case 1:
										//Programma Lavori
										programma.setIdRicevuto(risultatoPubblicazione.getId());
										scpManager.inserimentoFlussoProgrammaLavori(programma, profilo, contri);
										break;
									case 2:
					    				//Programma Forniture Servizi
										programmaFS.setIdRicevuto(risultatoPubblicazione.getId());
										scpManager.inserimentoFlussoProgrammaFornitureServizi(programmaFS, profilo, contri);
										break;
									case 3:
					    				//Programma DM 11 del 2011
										programmaDM112011.setIdRicevuto(risultatoPubblicazione.getId());
										scpManager.inserimentoFlussoProgrammaDM112011(programmaDM112011, profilo, contri);
										break;
								}
								request.setAttribute("flusso", "ok");
								break;
							case 400:
								request.setAttribute("validate", risultatoPubblicazione.getValidate());
								target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
								break;
							default:
								request.setAttribute("error", risultatoPubblicazione.getError());
								target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
								break;
						}
			    	} else if (entita.equals("avvisi")) {
			    		PubblicaAvvisoEntry avviso = new PubblicaAvvisoEntry();
						scpManager.valorizzaAvviso(avviso, codein, idAvviso, codSistema);
						webTarget = client.target(url).path("Avvisi/Pubblica").queryParam("token", token).queryParam("modalitaInvio", "2");
						Response risultato = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(avviso), Response.class);
						esito = risultato.getStatus();
						PubblicazioneResult risultatoPubblicazione = risultato.readEntity(PubblicazioneResult.class);
						switch (esito) {
							case 200:
								//inserimento flusso
								avviso.setIdRicevuto(risultatoPubblicazione.getId());
								scpManager.inserimentoFlussoAvviso(avviso, profilo, idAvviso, codein);
								request.setAttribute("flusso", "ok");
								break;
							case 400:
								request.setAttribute("validate", risultatoPubblicazione.getValidate());
								target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
								break;
							default:
								request.setAttribute("error", risultatoPubblicazione.getError());
								target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
								break;
						}
			    	}
		    	}
		    } else {
		    	if (resultAccesso.getError() == null || resultAccesso.getError().equals("")) {
		    		resultAccesso.setError("Si è verificato un problema nel servizio di autenticazione. Contattare l'amministratore");
		    	}
		    	request.setAttribute("error", resultAccesso.getError());
				target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
		    }
			
		} catch (Exception s) {
			request.setAttribute("error", s.getMessage());
			target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
		} 
		return mapping.findForward(target);
	}

}