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
<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9APPA-scheda">

	<%/* inserimento step nel breadcrumb per evitare al ciclo operativo di geneweb di tornare alla selezione della fase lotto */%>
	<gene:redefineInsert name="addHistory">
		<c:if test='${modo eq "VISUALIZZA"}'>
			<gene:historyAdd titolo="Dettaglio Imprese Aggiudicatarie"
				id="${key};FaseAggiudicazione" />
		</c:if>
	</gene:redefineInsert>

	<% /* verifico se provengo da una richiesta di modifica con la presenza di una selezione di imprese partecipanti */ %>
	<c:set var="isW9FasePartecipante" value='${param.isW9FasePartecipante}' />
    <c:set var="isAccordoQuadro"
		value='${param.isAccordoQuadro}' scope="request" />
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
		value='${gene:callFunction3("it.eldasoft.w9.web.struts.GetW9AggiValues", pageContext, key, numImpr)}' />
	</c:if>
		
	<c:set var="id_gruppo" value='${requestScope.idGruppoRicavato}' />
	<c:set var="id_tipoagg" value='${requestScope.idTipoAggRicavato}' />

	<% /* questa chiave serve al recupero da db delle imprese associate */ %>
	<c:set var="key_aggiudicataria"
		value='${key};NUM_AGGI=N:${numImpr};ID_GRUPPO=N:${id_gruppo}' />

	<gene:redefineInsert name="corpo">

		<gene:formScheda entita="W9APPA" gestisciProtezioni="true"
			gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9APPA"
			where='W9APPA.CODGARA = #W9APPA.CODGARA# AND W9APPA.CODLOTT = #W9APPA.CODLOTT# AND W9APPA.NUM_APPA = #W9APPA.NUM_APPA#'>

			<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
			<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
			<c:set var="num" value='${gene:getValCampo(key,"NUM_APPA")}' />

			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />

			<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione || isMonitoraggioMultilotto}'>
				<gene:redefineInsert name="schedaModifica" />
				<gene:redefineInsert name="pulsanteModifica" />
			</c:if>

			<gene:campoScheda campo="CODLOTT" visibile="false" />
			<gene:campoScheda campo="CODGARA" visibile="false" />
			<gene:campoScheda campo="NUM_APPA" visibile="false" />

			<c:choose>
				<c:when test='${numImpr ne null}'>
					<gene:campoScheda entita="W9AGGI" campo="NUM_AGGI"
						value="${numImpr}" visibile="false" />
				</c:when>
			</c:choose>

			<gene:campoScheda entita="W9AGGI" campo="CODGARA" value="${codgara}"
				visibile="false" defaultValue="${datiRiga.W9APPA_CODGARA}" />
			<gene:campoScheda entita="W9AGGI" campo="CODLOTT" value="${codlott}"
				visibile="false" defaultValue="${datiRiga.W9APPA_CODLOTT}" />
			<gene:campoScheda entita="W9AGGI" campo="NUM_APPA" value="${num}"
				visibile="false" defaultValue="${datiRiga.W9APPA_NUM}" />

			<!-- campi per  W9FASI-->
			<gene:campoScheda campo="CODGARA" entita="W9FASI" visibile="false" value="${codgara}"
				where="W9FASI.CODGARA=W9APPA.CODGARA AND W9FASI.CODLOTT=W9APPA.CODLOTT and W9FASI.FASE_ESECUZIONE in (1,987,12)" />
			<gene:campoScheda campo="CODLOTT" entita="W9FASI" visibile="false"  value="${codlott}"
				where="W9FASI.CODGARA=W9APPA.CODGARA AND W9FASI.CODLOTT=W9APPA.CODLOTT and W9FASI.FASE_ESECUZIONE in (1,987,12)"/>
			<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" defaultValue="${flusso}" visibile="false" 
				where="W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT and W9FASI.FASE_ESECUZIONE in (1,987,12)"/>
			<gene:campoScheda campo="NUM" entita="W9FASI" visibile="false" 
				where="W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT and W9FASI.FASE_ESECUZIONE in (1,987,12)"/>	
	
			<!-- scheda multipla -->
			<!-- c:if test='${modo ne "NUOVO"}' -->
			<% /* il test verifica che il modo non sia nuovo , aggiungi richiama comunque il modo modifica */ %>
			<c:if
				test='${param.operazioneModificaAggi == 1 || modo eq "VISUALIZZA" && ! empty numImpr}'>
				<gene:callFunction
					obj="it.eldasoft.w9.tags.funzioni.GetImpreseAggiudicatarieFunction"
					parametro="${key_aggiudicataria}" />
			</c:if>

			<c:choose>
				<c:when
					test='${param.operazioneModificaAggi == 1 || modo eq "VISUALIZZA"}'>
					<gene:campoScheda entita="W9AGGI" campo="ID_TIPOAGG"
						obbligatorio="true" value="${id_tipoagg}" />
				</c:when>
				<c:otherwise>
					<gene:campoScheda entita="W9AGGI" campo="ID_TIPOAGG"
						obbligatorio="true" value="" />
				</c:otherwise>
			</c:choose>

			<jsp:include
				page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp">
				<jsp:param name="entita" value='W9AGGI' />
				<jsp:param name="chiave"
					value='${gene:getValCampo(key, "CODGARA")};${gene:getValCampo(key, "CODLOTT")};${gene:getValCampo(key, "NUM_APPA")}' />
				<jsp:param name="nomeAttributoLista"
					value='listaImpreseAggiudicatarie' />
				<jsp:param name="idProtezioni" value="W9AGGI" />
				<jsp:param name="jspDettaglioSingolo"
					value="/WEB-INF/pages/w9/w9appa/imprese_aggiudicatarie.jsp" />
				<jsp:param name="arrayCampi"
					value="'W9AGGI_CODGARA_','W9AGGI_CODLOTT_','W9AGGI_NUM_APPA_','W9AGGI_NUM_AGGI_','IMPR_NOMEST_','W9AGGI_CODIMP_','W9AGGI_ID_TIPOAGG_','W9AGGI_RUOLO_','W9AGGI_FLAG_AVVALIMENTO_','IMPR_NOMEST_AUSILIARIA_','W9AGGI_CODIMP_AUSILIARIA_','W9AGGI_ID_GRUPPO_','W9AGGI_IMPORTO_AGGIUDICAZIONE_','W9AGGI_PERC_RIBASSO_AGG_','W9AGGI_PERC_OFF_AUMENTO_'" />
				<jsp:param name="arrayVisibilitaCampi"
					value="false,false,false,false,true,false,false,true,true,false,false,false,true,true,true" />
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
				funzione="showSezioni('#W9AGGI_ID_TIPOAGG#')"
				elencocampi="W9AGGI_ID_TIPOAGG" esegui="true" />

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
	
		function showSezioni(tipo) {
			// caso campo vuoto
			if(tipo == null || tipo == '' ){ // caso inserimento nuova Impresa nessun valore settato
				showObj("rowLinkAddW9AGGI",false);
				for(var i=1; i <= lastIdW9AGGIVisualizzata ; i++){
					showObj("rowtitoloW9AGGI_" + i,false);
					showObj("rowIMPR_NOMEST_" + i,false);
					showObj("rowW9AGGI_RUOLO_" + i,false);
					showObj("rowW9AGGI_ID_TIPOAGG_" + i,false);
					showObj("rowW9AGGI_FLAG_AVVALIMENTO_" + i, false);
					showObj("rowIMPR_NOMEST_AUSILIARIA_" + i, false);
				}
			} else {
				// in qualsiasi caso io mi trovi se non ho nessuna impresa visualizzata mi occupo di mostrarne una
				if (lastIdW9AGGIVisualizzata == 0) {
						showNextElementoSchedaMultipla('W9AGGI', new Array('W9AGGI_CODGARA_','W9AGGI_CODLOTT_','W9AGGI_NUM_APPA_','W9AGGI_NUM_AGGI_','IMPR_NOMEST_','W9AGGI_CODIMP_','W9AGGI_ID_TIPOAGG_','W9AGGI_RUOLO_','W9AGGI_FLAG_AVVALIMENTO_','IMPR_NOMEST_AUSILIARIA_','W9AGGI_CODIMP_AUSILIARIA_','W9AGGI_ID_GRUPPO_','W9AGGI_IMPORTO_AGGIUDICAZIONE_','W9AGGI_PERC_RIBASSO_AGG_','W9AGGI_PERC_OFF_AUMENTO_'), new Array(false,false,false,false,true,false,false,true,true,false,false,false,true,true,true));
				}
				if (tipo == 3 || tipo == 4) { //caso Impresa singola e GEIE va mostrato un solo valore
					//nascondo aggiunta schede
					showObj("rowLinkAddW9AGGI",false);

					//ciclo su tutti gli elementi nascondendo titolo , ruolo flag e impresaausiliaria
					var conteggioRigheVisualizzabili = 0;
					for (var i=1; i <= lastIdW9AGGIVisualizzata ; i++) {
						// setto il valore id_tipoagg nascosto
						setValue("W9AGGI_ID_TIPOAGG_" + i, tipo);
						showObj("rowtitoloW9AGGI_" + i,false);
						showObj("rowW9AGGI_RUOLO_" + i,false);
						showObj("rowW9AGGI_ID_TIPOAGG_" + i,false);
						showObj("rowW9AGGI_FLAG_AVVALIMENTO_" + i, false);
						showObj("rowIMPR_NOMEST_AUSILIARIA_" + i, false);
						// per il nome impresa devo ragionare sul fatto che ne devo visualizzare una soltanto
						// se il nome non è vuoto
						// la riga non è stata cancellata			
						if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("W9AGGI_DEL_W9AGGI_" + i) != 1 && conteggioRigheVisualizzabili == 0){
							showObj("rowIMPR_NOMEST_" + i,true);
							showObj("rowW9AGGI_FLAG_AVVALIMENTO_" + i, true);
							
							if (getValue("W9AGGI_FLAG_AVVALIMENTO_" + i) != null && getValue("W9AGGI_FLAG_AVVALIMENTO_" + i) != '' && getValue("W9AGGI_FLAG_AVVALIMENTO_" + i) != 0) {
								showObj("rowIMPR_NOMEST_AUSILIARIA_" + i, true);
							} else {
								showObj("rowIMPR_NOMEST_AUSILIARIA_" + i, false);
							}
							conteggioRigheVisualizzabili ++;
						} else {
							showObj("rowIMPR_NOMEST_" + i,false);
						}
					}
					// funziona se almeno una riga viene popolata, altrimenti mostro la prima dopo averla svuotata
					if (conteggioRigheVisualizzabili == 0){
						setValue("IMPR_NOMEST_" + 1, null);
						showObj("rowIMPR_NOMEST_" + 1,true);
						showObj("rowW9AGGI_ID_TIPOAGG_" + 1,false);
						// setto il valore id_tipoagg nascosto al tipo
						setValue("W9AGGI_ID_TIPOAGG_" + 1, tipo);
						setValue("W9AGGI_FLAG_AVVALIMENTO_" + 1, null);
						showObj("rowW9AGGI_FLAG_AVVALIMENTO_" + 1, true);
						setValue("IMPR_NOMEST_AUSILIARIA_" + 1, null);
						showObj("rowIMPR_NOMEST_AUSILIARIA_" + 1, false);
						//controllo se l'impresa doveva esser cancellata
						if (getValue("W9AGGI_DEL_W9AGGI_" + 1) == 1){
							setValue("W9AGGI_DEL_W9AGGI_" + 1, null);
						}
					}	
				}
				else{ //tutti gli altri casi riguardanti Impresa Multipla
					// visualizzo l'aggiunta schede
					if (lastIdW9AGGIVisualizzata < maxIdW9AGGIVisualizzabile) {
						showObj("rowLinkAddW9AGGI",true);
					}
					var conteggioRigheVisualizzabili = 0;
					for (var i=1; i <= lastIdW9AGGIVisualizzata ; i++) {
						// setto il valore id_tipoagg nascosto al tipo
						setValue("W9AGGI_ID_TIPOAGG_" + i, tipo);
						if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("W9AGGI_DEL_W9AGGI_" + i) != 1) {
							showObj("rowtitoloW9AGGI_" + i,true);
							showObj("rowIMPR_NOMEST_" + i,true);
							showObj("rowW9AGGI_ID_TIPOAGG_" + i,false);
							if (tipo==1) {
								showObj("rowW9AGGI_RUOLO_" + i,true);
							} else {
								showObj("rowW9AGGI_RUOLO_" + i,false);
							}
							showObj("rowW9AGGI_ID_TIPOAGG_" + i,false);
							showObj("rowW9AGGI_FLAG_AVVALIMENTO_" + i, true);
							if (getValue("W9AGGI_FLAG_AVVALIMENTO_" + i) != null && getValue("W9AGGI_FLAG_AVVALIMENTO_" + i) != '' && getValue("W9AGGI_FLAG_AVVALIMENTO_" + i) != 0){
								showObj("rowIMPR_NOMEST_AUSILIARIA_" + i, true);
							} else {
								showObj("rowIMPR_NOMEST_AUSILIARIA_" + i, false);
							}
							conteggioRigheVisualizzabili ++;
						} else {
							showObj("rowtitoloW9AGGI_" + i,false);
							showObj("rowIMPR_NOMEST_" + i,false);
							showObj("rowW9AGGI_RUOLO_" + i,false);
							showObj("rowW9AGGI_ID_TIPOAGG_" + i,false);
							showObj("rowW9AGGI_FLAG_AVVALIMENTO_" + i, false);
							showObj("rowIMPR_NOMEST_AUSILIARIA_" + i, false);
						}	
					}
					if (conteggioRigheVisualizzabili == 0){
						showObj("rowtitoloW9AGGI_" + 1,true);
						setValue("IMPR_NOMEST_" + 1, null);
						setValue("W9AGGI_RUOLO_" + 1, null);
						showObj("rowW9AGGI_ID_TIPOAGG_" + 1,false);
						showObj("rowIMPR_NOMEST_" + 1,true);
						if(tipo==1){
							showObj("rowW9AGGI_RUOLO_" + 1,true);
						} else {
							showObj("rowW9AGGI_RUOLO_" + 1,false);
						}
						setValue("W9AGGI_FLAG_AVVALIMENTO_" + 1, null);
						showObj("rowW9AGGI_FLAG_AVVALIMENTO_" + 1, true);
						setValue("IMPR_NOMEST_AUSILIARIA_" + 1, null);
						showObj("rowIMPR_NOMEST_AUSILIARIA_" + 1, false);
						// setto il valore id_tipoagg nascosto al tipo
						setValue("W9AGGI_ID_TIPOAGG_" + 1, tipo);
						showObj("rowW9AGGI_FLAG_AVVALIMENTO_" + 1, true);
						//controllo se l'impresa doveva esser cancellata
						if (getValue("W9AGGI_DEL_W9AGGI_" + 1) == 1){
							setValue("W9AGGI_DEL_W9AGGI_" + 1, null);
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
			tipo = getValue("W9AGGI_ID_TIPOAGG");
			
			if(tipo == 3 || tipo == 4){
				// ciclo sugli elementi per trovarne almeno 1
				var conteggioRighePopolate = 0;
				for(var i=1; i <= lastIdW9AGGIVisualizzata ; i++) {
					if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("W9AGGI_DEL_W9AGGI_" + i) != 1 && conteggioRighePopolate == 0){					
						// se trovo un valore che faccia al caso mio incremento la sentinella
						conteggioRighePopolate ++;
						// setto i due campi tipo 
						setValue("W9AGGI_ID_TIPOAGG_" + i, tipo);
					
						//essendo impresa singola ruolo e numeroraggruppamento risulteranno nulli
						setValue("W9AGGI_RUOLO_" + i, null);
						setValue("W9AGGI_ID_GRUPPO_" + i, null);
						// se sono in modifica mi occupo di valorizzare il campo necessario allo scopo 
						if(modoAperturaScheda == "MODIFICA"){
							setValue("W9AGGI_MOD_W9AGGI_" + i, 1);
						}
					}else {
						// il valore non deve venire usato
						if(modoAperturaScheda == "NUOVO"){
							// in modalita' nuovo sbianco tutti i campi
							setValue("IMPR_NOMEST_" + i, null);
							setValue("W9AGGI_CODGARA_" + i, null);
							setValue("W9AGGI_ID_TIPOAGG_" + i, null);
							setValue("W9AGGI_RUOLO_" + i, null);
							setValue("W9AGGI_ID_GRUPPO_" + i, null);
							setValue("W9AGGI_CODIMP_" + i, null);
							setValue("W9AGGI_NUM_AGGI_" + i, null);
							setValue("W9AGGI_NUM_APPA_" + i, null);
							setValue("W9AGGI_CODLOTT_" + i, null);
							setValue("W9AGGI_FLAG_AVVALIMENTO_" + i, null);
							setValue("W9AGGI_DEL_W9AGGI_" + i, null);
							setValue("W9AGGI_MOD_W9AGGI_" + i, null);
							setValue("IMPR_NOMEST_AUSILIARIA_" + i, null);
						}
						if(modoAperturaScheda == "MODIFICA"){    
							// in modifica mi limito a contrassegnare l'impresa come eliminabile
							setValue("W9AGGI_DEL_W9AGGI_" + i, 1);
						}	
					}
				}
				// se e solo se ho un'unica voce popolata procedo all'inserimento/modifica
				if (conteggioRighePopolate == 1){
					prosegui();
				}
			}else{
				if(tipo != 3 && tipo != 4 && tipo != null){
					
					var id_gruppo = null;
					if(modoAperturaScheda == "MODIFICA"){
						for(var i=1; i <= lastIdW9AGGIVisualizzata ; i++){
							if (getValue("W9AGGI_ID_GRUPPO_" + i) != null){
								id_gruppo = getValue("W9AGGI_ID_GRUPPO_" + i);
								break;
							}
						}
					}	
				
					var conteggioRighePopolate = 0;
					for(var i=1; i <= lastIdW9AGGIVisualizzata ; i++){

						if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("W9AGGI_DEL_W9AGGI_" + i) != 1){
							conteggioRighePopolate++;
							setValue("W9AGGI_ID_TIPOAGG_" + i, tipo);
							setValue("W9AGGI_ID_GRUPPO_" + i, id_gruppo);
							if(tipo != 1){
								setValue("W9AGGI_RUOLO_" + i, null);
							}
							if(modoAperturaScheda == "MODIFICA"){
								setValue("W9AGGI_MOD_W9AGGI_" + i, 1);
							}
						}else {
							if(modoAperturaScheda == "NUOVO"){
								// in modalita' nuovo sbianco tutti i campi
								setValue("IMPR_NOMEST_" + i, null);
								setValue("W9AGGI_CODGARA_" + i, null);
								setValue("W9AGGI_ID_TIPOAGG_" + i, null);
								setValue("W9AGGI_RUOLO_" + i, null);
								setValue("W9AGGI_ID_GRUPPO_" + i, null);
								setValue("W9AGGI_CODIMP_" + i, null);
								setValue("W9AGGI_NUM_AGGI_" + i, null);
								setValue("W9AGGI_NUM_APPA_" + i, null);
								setValue("W9AGGI_CODLOTT_" + i, null);
								setValue("W9AGGI_FLAG_AVVALIMENTO" + i, null);
								setValue("W9AGGI_DEL_W9AGGI_" + i, null);
								setValue("W9AGGI_MOD_W9AGGI_" + i, null);
								setValue("IMPR_NOMEST_AUSILIARIA_" + i, null);
							}
							if(modoAperturaScheda == "MODIFICA"){
								// in modifica mi limito a contrassegnare l'impresa come eliminabile
								setValue("W9AGGI_DEL_W9AGGI_" + i, 1);
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
					var href = "href=w9/w9appa/w9appa-pg-imprese-scheda.jsp&modo=MODIFICA&isAccordoQuadro=${isAccordoQuadro}&operazioneModificaAggi=1&isW9FasePartecipante=" + isW9FasePartecipante + "&numImpr=" + numImpresa + "&key=" + tempKey;
					document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
				} else {
					// Modifica dettaglio di un gruppo di imprese
					var href = "href=w9/w9appa/w9appa-pg-imprese-scheda.jsp&modo=MODIFICA&isAccordoQuadro=${isAccordoQuadro}&operazioneModificaAggi=1&isW9FasePartecipante=" + isW9FasePartecipante + "&numImpr=" + numImpresa + "&id_gruppo=" + idGruppo + "&key=" + tempKey;
					document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
				}
			}
		</c:if>
		
		var tmp = showNextElementoSchedaMultipla;
		showNextElementoSchedaMultipla = function(tipo, campi, visibilitaCampi) {

			var tipoAggiudicataria = getValue("W9AGGI_ID_TIPOAGG");
			if (tipoAggiudicataria == 1){
				tmp(tipo, campi, new Array(false,false,false,false,true,false,false,true,true,false,false,false,true,true,true));
			}
			else {
				tmp(tipo, campi, new Array(false,false,false,false,true,false,false,false,true,false,false,false,true,true,true));
			}
		}
		
		<c:if test='${not empty RISULTATO and RISULTATO eq "OK"}'>
			bloccaRichiesteServer();
			// torno alla lista
			historyVaiIndietroDi(1);
		</c:if>

	<c:if test='${modo eq "MODIFICA"}' >
		<c:set var="localkey" value='${codgara};${codlott};${datiRiga.W9FASI_FASE_ESECUZIONE};${num};${datiRiga.W9FASI_NUM}' scope="request" />
		<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
	</c:if>

	</gene:javaScript>
</gene:template>
