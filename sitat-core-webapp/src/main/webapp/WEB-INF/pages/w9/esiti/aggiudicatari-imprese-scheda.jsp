<%
	/*
	 * Created on: 04/08/2009
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<% /* Dati generali del lavoro */ %>
<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="ESITI_AGGIUDICATARI-scheda">

	<%/* inserimento step nel breadcrumb per evitare al ciclo operativo di geneweb di tornare alla selezione della fase lotto */%>
	<gene:redefineInsert name="addHistory">
		<c:if test='${modo eq "VISUALIZZA"}'>
			<gene:historyAdd titolo="Dettaglio Imprese Aggiudicatarie"
				id="${key};FaseAggiudicazione" />
		</c:if>
	</gene:redefineInsert>

	<% /* verifico se provengo da una richiesta di modifica con la presenza di una selezione di imprese partecipanti */ %>
	<c:set var="isW9FasePartecipante" value='${param.isW9FasePartecipante}' />

	<c:set var="operazioneModificaAggi"
		value='${param.operazioneModificaAggi}' />

	<% /* recupero il numero aggiudicataria o come parametro o come attributo per la visualizzazione */ %>
	<c:set var="numImprLoc" value='${param.numImpr}' />

	<c:if test='${empty numImprLoc}'>
		<c:set var="numImprLoc" value='${requestScope.numImpr}' />
	</c:if>

	<c:set var="numImpr" value='${numImprLoc}' />

    <% /* idgruppo e tipoagg li recupero dalla callfunction onde evitare problemi di distinzione tra aggiudicatarie singole e multiple */ %>
	<c:if test='${! empty numImpr}'>
	<c:set var="valuesW9Aggi"
		value='${gene:callFunction3("it.eldasoft.w9.web.struts.GetW9AggiEsitiValues", pageContext, key, numImpr)}' />
	</c:if>
		
	<c:set var="id_gruppo" value='${requestScope.idGruppoRicavato}' />
	<c:set var="id_tipoagg" value='${requestScope.idTipoAggRicavato}' />

	<% /* questa chiave serve al recupero da db delle imprese associate */ %>
	<c:set var="key_aggiudicataria"
		value='${key};NUM_AGGI=N:${numImpr};ID_GRUPPO=N:${id_gruppo}' />

	<gene:redefineInsert name="corpo">

		<gene:formScheda entita="W9PUBBLICAZIONI" gestisciProtezioni="true"
			gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9PUBBLICAZIONI"
			where='W9PUBBLICAZIONI.CODGARA = #W9PUBBLICAZIONI.CODGARA# AND W9PUBBLICAZIONI.NUM_PUBB = #W9PUBBLICAZIONI.NUM_PUBB#'>

			<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
			<c:set var="numPubb" value='${gene:getValCampo(key,"NUM_PUBB")}' />

			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />

			<c:if test='${permessoUser lt 4}'>
				<gene:redefineInsert name="schedaModifica" />
				<gene:redefineInsert name="pulsanteModifica" />
			</c:if>

			<gene:campoScheda campo="CODGARA" visibile="false" />
			<gene:campoScheda campo="NUM_PUBB" visibile="false" />

			<c:choose>
				<c:when test='${numImpr ne null}'>
					<c:set var="addSelectNumImpr"
						value=" and ESITI_AGGIUDICATARI.NUM_AGGI = ${numImpr} " />
					<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="NUM_AGGI"
						value="${numImpr}" visibile="false" />
				</c:when>
				<c:otherwise>
					<c:set var="addSelectNumImpr" value="" />
				</c:otherwise>
			</c:choose>

			<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="CODGARA"
				where="ESITI_AGGIUDICATARI.CODGARA = ${codgara} and ESITI_AGGIUDICATARI.NUM_PUBB = ${numPubb} ${addSelectNumImpr}"
				visibile="false" defaultValue="${datiRiga.W9PUBBLICAZIONI_CODGARA}" />
			<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="NUM_PUBB"
				where="ESITI_AGGIUDICATARI.CODGARA = ${codgara} and ESITI_AGGIUDICATARI.NUM_PUBB = ${numPubb} ${addSelectNumImpr}"
				visibile="false" defaultValue="${datiRiga.W9PUBBLICAZIONI_NUM_PUBB}" />

			<!-- scheda multipla -->
			<!-- c:if test='${modo ne "NUOVO"}' -->
			<% /* il test verifica che il modo non sia nuovo , aggiungi richiama comunque il modo modifica */ %>
			<c:if
				test='${param.operazioneModificaAggi == 1 || modo eq "VISUALIZZA" && ! empty numImpr}'>
				<gene:callFunction
					obj="it.eldasoft.w9.tags.funzioni.GetImpreseEsitoFunction"
					parametro="${key_aggiudicataria}" />
			</c:if>

			<c:choose>
				<c:when
					test='${param.operazioneModificaAggi == 1 || modo eq "VISUALIZZA"}'>
					<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="ID_TIPOAGG"
						obbligatorio="true" value="${id_tipoagg}" />
				</c:when>
				<c:otherwise>
					<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="ID_TIPOAGG"
						obbligatorio="true" value="" />
				</c:otherwise>
			</c:choose>
				
			<jsp:include
				page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp">
				<jsp:param name="entita" value='ESITI_AGGIUDICATARI' />
				<jsp:param name="chiave"
					value='${gene:getValCampo(key, "CODGARA")};${gene:getValCampo(key, "NUM_PUBB")}' />
				<jsp:param name="nomeAttributoLista"
					value='listaImpreseAggiudicatarie' />
				<jsp:param name="idProtezioni" value="ESITI_AGGIUDICATARI" />
				<jsp:param name="jspDettaglioSingolo"
					value="/WEB-INF/pages/w9/esiti/imprese_aggiudicatarie.jsp" />
				<jsp:param name="arrayCampi"
					value="'ESITI_AGGIUDICATARI_CODGARA_','ESITI_AGGIUDICATARI_NUM_PUBB_','ESITI_AGGIUDICATARI_NUM_AGGI_','IMPR_NOMEST_','ESITI_AGGIUDICATARI_CODIMP_','ESITI_AGGIUDICATARI_ID_TIPOAGG_','ESITI_AGGIUDICATARI_RUOLO_','ESITI_AGGIUDICATARI_ID_GRUPPO_'" />
				<jsp:param name="arrayVisibilitaCampi"
					value="false,false,false,true,false,false,true,false" />
				<jsp:param name="usaContatoreLista" value="true" />
				<jsp:param name="sezioneListaVuota" value="false" />
				<jsp:param name="numMaxDettagliInseribili" value="10" />
				<jsp:param name="titoloSezione"
					value="Imprese aggiudicatarie / affidatarie" />
				<jsp:param name="titoloNuovaSezione"
					value="Nuova impresa aggiudicataria / affidataria" />
				<jsp:param name="descEntitaVociLink"
					value="Imprese aggiudicatarie / affidatarie" />
				<jsp:param name="msgRaggiuntoMax"
					value="e Imprese aggiudicatarie / affidatarie" />
			</jsp:include>
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>

			<gene:fnJavaScriptScheda
				funzione="showSezioni('#ESITI_AGGIUDICATARI_ID_TIPOAGG#')"
				elencocampi="ESITI_AGGIUDICATARI_ID_TIPOAGG" esegui="true" />

		</gene:formScheda>
	</gene:redefineInsert>

	<gene:javaScript>
		document.forms[0].action=document.forms[0].action + "?numImpr=${numImpr}&id_gruppo=${id_gruppo}";	
		document.forms[0].jspPathTo.value = document.forms[0].jspPath.value;
		
		// in inserimento devo forzare l'annulla per tornare alla scheda precedente senza errori
		<c:if test='${true or param.noBackButton eq 1}'>
			function schedaAnnulla(){
				bloccaRichiesteServer();
				// torno alla lista
				historyVaiIndietroDi(0);
			}
		</c:if> 
	
		function showSezioni(tipo){
			// caso campo vuoto
			if(tipo == null || tipo == '' ){ // caso inserimento nuova Impresa nessun valore settato
				showObj("rowLinkAddESITI_AGGIUDICATARI",false);
				for(var i=1; i <= lastIdESITI_AGGIUDICATARIVisualizzata ; i++){
					showObj("rowtitoloESITI_AGGIUDICATARI_" + i,false);
					showObj("rowIMPR_NOMEST_" + i,false);
					showObj("rowESITI_AGGIUDICATARI_RUOLO_" + i,false);
					showObj("rowESITI_AGGIUDICATARI_ID_TIPOAGG_" + i,false);
				}
			}
			else{
				// in qualsiasi caso io mi trovi se non ho nessuna impresa visualizzata mi occupo di mostrarne una
				if (lastIdESITI_AGGIUDICATARIVisualizzata == 0) {
						showNextElementoSchedaMultipla('ESITI_AGGIUDICATARI', new Array('ESITI_AGGIUDICATARI_CODGARA_','ESITI_AGGIUDICATARI_NUM_PUBB_','ESITI_AGGIUDICATARI_NUM_AGGI_','IMPR_NOMEST_','ESITI_AGGIUDICATARI_CODIMP_','ESITI_AGGIUDICATARI_ID_TIPOAGG_','ESITI_AGGIUDICATARI_RUOLO_','ESITI_AGGIUDICATARI_ID_GRUPPO_'), new Array(false,false,false,true,false,false,true,false));
				}
				if(tipo == 3){ //caso Impresa singola va mostrato un solo valore
					//nascondo aggiunta schede
					showObj("rowLinkAddESITI_AGGIUDICATARI",false);

					//ciclo su tutti gli elementi nascondendo titolo , ruolo flag e impresaausiliaria
					var conteggioRigheVisualizzabili = 0;
					for(var i=1; i <= lastIdESITI_AGGIUDICATARIVisualizzata ; i++) {
						// setto il valore id_tipoagg nascosto
						setValue("ESITI_AGGIUDICATARI_ID_TIPOAGG_" + i, tipo);
						showObj("rowtitoloESITI_AGGIUDICATARI_" + i,false);
						showObj("rowESITI_AGGIUDICATARI_RUOLO_" + i,false);
						showObj("rowESITI_AGGIUDICATARI_ID_TIPOAGG_" + i,false);
						// per il nome impresa devo ragionare sul fatto che ne devo visualizzare una soltanto
						// se il nome non è vuoto
						// la riga non è stata cancellata			
						if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + i) != 1 && conteggioRigheVisualizzabili == 0){
							showObj("rowIMPR_NOMEST_" + i,true);
							conteggioRigheVisualizzabili ++;
						} else {
							showObj("rowIMPR_NOMEST_" + i,false);
						}
					}
					// funziona se almeno una riga viene popolata, altrimenti mostro la prima dopo averla svuotata
					if (conteggioRigheVisualizzabili == 0){
						setValue("IMPR_NOMEST_" + 1, null);
						showObj("rowIMPR_NOMEST_" + 1,true);
						showObj("rowESITI_AGGIUDICATARI_ID_TIPOAGG_" + 1,false);
						// setto il valore id_tipoagg nascosto al tipo
						setValue("ESITI_AGGIUDICATARI_ID_TIPOAGG_" + 1, tipo);
						//controllo se l'impresa doveva esser cancellata
						if (getValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + 1) == 1){
							setValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + 1, null);
						}
					}	
				}
				else{ //tutti gli altri casi riguardanti Impresa Multipla
					// visualizzo l'aggiunta schede
					if(lastIdESITI_AGGIUDICATARIVisualizzata < maxIdESITI_AGGIUDICATARIVisualizzabile){
						showObj("rowLinkAddESITI_AGGIUDICATARI",true);
					}
					var conteggioRigheVisualizzabili = 0;
					for(var i=1; i <= lastIdESITI_AGGIUDICATARIVisualizzata ; i++){
						// setto il valore id_tipoagg nascosto al tipo
						setValue("ESITI_AGGIUDICATARI_ID_TIPOAGG_" + i, tipo);
						if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + i) != 1){
							showObj("rowtitoloESITI_AGGIUDICATARI_" + i,true);
							showObj("rowIMPR_NOMEST_" + i,true);
							showObj("rowESITI_AGGIUDICATARI_ID_TIPOAGG_" + i,false);
							if(tipo==1){
								showObj("rowESITI_AGGIUDICATARI_RUOLO_" + i,true);
							} else {
								showObj("rowESITI_AGGIUDICATARI_RUOLO_" + i,false);
							}
							showObj("rowESITI_AGGIUDICATARI_ID_TIPOAGG_" + i,false);
							conteggioRigheVisualizzabili ++;
						}
						else{
							showObj("rowtitoloESITI_AGGIUDICATARI_" + i,false);
							showObj("rowIMPR_NOMEST_" + i,false);
							showObj("rowESITI_AGGIUDICATARI_RUOLO_" + i,false);
							showObj("rowESITI_AGGIUDICATARI_ID_TIPOAGG_" + i,false);
						}	
					}
					if (conteggioRigheVisualizzabili == 0){
						showObj("rowtitoloESITI_AGGIUDICATARI_" + 1,true);
						setValue("IMPR_NOMEST_" + 1, null);
						setValue("ESITI_AGGIUDICATARI_RUOLO_" + 1, null);
						showObj("rowESITI_AGGIUDICATARI_ID_TIPOAGG_" + 1,false);
						showObj("rowIMPR_NOMEST_" + 1,true);
						if(tipo==1){
							showObj("rowESITI_AGGIUDICATARI_RUOLO_" + 1,true);
						} else {
							showObj("rowESITI_AGGIUDICATARI_RUOLO_" + 1,false);
						}
						// setto il valore id_tipoagg nascosto al tipo
						setValue("ESITI_AGGIUDICATARI_ID_TIPOAGG_" + 1, tipo);
						//controllo se l'impresa doveva esser cancellata
						if (getValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + 1) == 1){
							setValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + 1, null);
						}
					}
				}
			}
		}		
		
		var prosegui = schedaConferma;
		
		var schedaConferma = function() {

			var modoAperturaScheda = "NUOVO";
			if ('${operazioneModificaAggi}'==1){
				modoAperturaScheda = "MODIFICA";
			}
	
			//i campi che posso avere sono il tipo
			tipo = getValue("ESITI_AGGIUDICATARI_ID_TIPOAGG");
			
			if(tipo == 3){
				// ciclo sugli elementi per trovarne almeno 1
				var conteggioRighePopolate = 0;
				for(var i=1; i <= lastIdESITI_AGGIUDICATARIVisualizzata ; i++) {
					if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + i) != 1 && conteggioRighePopolate == 0){					
						// se trovo un valore che faccia al caso mio incremento la sentinella
						conteggioRighePopolate ++;
						// setto i due campi tipo 
						setValue("ESITI_AGGIUDICATARI_ID_TIPOAGG_" + i, tipo);
					
						//essendo impresa singola ruolo e numeroraggruppamento risulteranno nulli
						setValue("ESITI_AGGIUDICATARI_RUOLO_" + i, null);
						setValue("ESITI_AGGIUDICATARI_ID_GRUPPO_" + i, null);
						// se sono in modifica mi occupo di valorizzare il campo necessario allo scopo 
						if(modoAperturaScheda == "MODIFICA"){
							setValue("ESITI_AGGIUDICATARI_MOD_ESITI_AGGIUDICATARI_" + i, 1);
						}
					}else {
						// il valore non deve venire usato
						if(modoAperturaScheda == "NUOVO"){
							// in modalita' nuovo sbianco tutti i campi
							setValue("IMPR_NOMEST_" + i, null);
							setValue("ESITI_AGGIUDICATARI_CODGARA_" + i, null);
							setValue("ESITI_AGGIUDICATARI_ID_TIPOAGG_" + i, null);
							setValue("ESITI_AGGIUDICATARI_RUOLO_" + i, null);
							setValue("ESITI_AGGIUDICATARI_ID_GRUPPO_" + i, null);
							setValue("ESITI_AGGIUDICATARI_CODIMP_" + i, null);
							setValue("ESITI_AGGIUDICATARI_NUM_AGGI_" + i, null);
							setValue("ESITI_AGGIUDICATARI_NUM_PUBB_" + i, null);
							setValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + i, null);
							setValue("ESITI_AGGIUDICATARI_MOD_ESITI_AGGIUDICATARI_" + i, null);
						}
						if(modoAperturaScheda == "MODIFICA"){    
							// in modifica mi limito a contrassegnare l'impresa come eliminabile
							setValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + i, 1);
						}	
					}
				}
				// se e solo se ho un'unica voce popolata procedo all'inserimento/modifica
				if (conteggioRighePopolate == 1){
					prosegui();
				}
			}else{
				if(tipo != 3 && tipo != null){
					
					var id_gruppo = null;
					if(modoAperturaScheda == "MODIFICA"){
						for(var i=1; i <= lastIdESITI_AGGIUDICATARIVisualizzata ; i++){
							if (getValue("ESITI_AGGIUDICATARI_ID_GRUPPO_" + i) != null){
								id_gruppo = getValue("ESITI_AGGIUDICATARI_ID_GRUPPO_" + i);
								break;
							}
						}
					}	
				
					var conteggioRighePopolate = 0;
					for(var i=1; i <= lastIdESITI_AGGIUDICATARIVisualizzata ; i++){

						if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + i) != 1){
							conteggioRighePopolate++;
							setValue("ESITI_AGGIUDICATARI_ID_TIPOAGG_" + i, tipo);
							setValue("ESITI_AGGIUDICATARI_ID_GRUPPO_" + i, id_gruppo);
							if(tipo != 1){
								setValue("ESITI_AGGIUDICATARI_RUOLO_" + i, null);
							}
							if(modoAperturaScheda == "MODIFICA"){
								setValue("ESITI_AGGIUDICATARI_MOD_ESITI_AGGIUDICATARI_" + i, 1);
							}
						}else {
							if(modoAperturaScheda == "NUOVO"){
								// in modalita' nuovo sbianco tutti i campi
								setValue("IMPR_NOMEST_" + i, null);
								setValue("ESITI_AGGIUDICATARI_CODGARA_" + i, null);
								setValue("ESITI_AGGIUDICATARI_ID_TIPOAGG_" + i, null);
								setValue("ESITI_AGGIUDICATARI_RUOLO_" + i, null);
								setValue("ESITI_AGGIUDICATARI_ID_GRUPPO_" + i, null);
								setValue("ESITI_AGGIUDICATARI_CODIMP_" + i, null);
								setValue("ESITI_AGGIUDICATARI_NUM_AGGI_" + i, null);
								setValue("ESITI_AGGIUDICATARI_NUM_PUBB_" + i, null);
								setValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + i, null);
								setValue("ESITI_AGGIUDICATARI_MOD_ESITI_AGGIUDICATARI_" + i, null);
							}
							if(modoAperturaScheda == "MODIFICA"){
								// in modifica mi limito a contrassegnare l'impresa come eliminabile
								setValue("ESITI_AGGIUDICATARI_DEL_ESITI_AGGIUDICATARI_" + i, 1);
							}	
						}
					}
					// mando in salvataggio solo se ho almeno 1 riga popolata
					if (conteggioRighePopolate > 0){
						prosegui();
					}
				}
			}
		}
	
		<c:if test='${numImpr ne null}'>
			function schedaModifica(){
			
				var isW9FasePartecipante = 0;
				if ("${isW9FasePartecipante}" > 0){
					isW9FasePartecipante = 1;
				}
			
				var numImpresa = ${numImpr};
				var idGruppo = "${id_gruppo}";
				
				var tempKey = document.forms[0].key.value ;
				
				if ("" == idGruppo) {
					// Modifica dettaglio di una impresa singola
					var href = "href=w9/esiti/aggiudicatari-imprese-scheda.jsp&modo=MODIFICA&operazioneModificaAggi=1&isW9FasePartecipante=" + isW9FasePartecipante + "&numImpr=" + numImpresa + "&key=" + tempKey;
					document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
				} else {
					// Modifica dettaglio di un gruppo di imprese
					var href = "href=w9/esiti/aggiudicatari-imprese-scheda.jsp&modo=MODIFICA&operazioneModificaAggi=1&isW9FasePartecipante=" + isW9FasePartecipante + "&numImpr=" + numImpresa + "&id_gruppo=" + idGruppo + "&key=" + tempKey;
					document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
				}
			}
		</c:if>
		
		var tmp = showNextElementoSchedaMultipla;
		showNextElementoSchedaMultipla = function(tipo, campi, visibilitaCampi) {

			var tipoAggiudicataria = getValue("ESITI_AGGIUDICATARI_ID_TIPOAGG");
			if (tipoAggiudicataria == 1){
				tmp(tipo, campi, new Array(false,false,false,true,false,false,true,false));
			}
			else {
				tmp(tipo, campi, new Array(false,false,false,true,false,false,false,false));
			}
		}
		
		<c:if test='${not empty RISULTATO and RISULTATO eq "OK"}'>
			bloccaRichiesteServer();
			// torno alla lista
			historyVaiIndietroDi(1);
		</c:if>

	<c:if test='${modo eq "MODIFICA"}' >
		<c:set var="localkey" value='${codgara};${numPubb}' scope="request" />
	</c:if>

	</gene:javaScript>
</gene:template>
