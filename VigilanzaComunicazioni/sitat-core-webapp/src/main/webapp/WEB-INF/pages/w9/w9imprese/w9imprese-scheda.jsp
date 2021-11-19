<%
/*
 * Created on: 10-nov-2008
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 *
 * Popup per campi ulteriori relativi alla ditta presenta nella lista delle
 * fasi di ricezione in analisi
 */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<c:set var="listaOpzioniDisponibili" value="${fn:join(opzDisponibili,'#')}#"/>

<div style="width:97%;">

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" 
	idMaschera="W9IMPRESE-scheda">
	

	<c:choose>
		<c:when test='${not (empty param.codGara and empty param.codLott and empty param.numImpr)}'>
			<c:set var="locCodGara" value="${param.codGara}" scope="request" />
			<c:set var="locCodLott" value="${param.codLott}" scope="request" />
			<c:set var="locNumImpr" value="${param.numImpr}" scope="request" />
			
		</c:when>
		<c:otherwise>
			<c:set var="locCodGara" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
			<c:set var="locCodLott" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
			<c:set var="locNumImpr" value='${gene:getValCampo(key,"NUM_IMPR")}' scope="request" />
		</c:otherwise>
	</c:choose>

	<c:set var="flusso" value='101' scope="request" />
	<c:set var="codgara" value='${locCodGara}' scope="request" />
	<c:set var="codlott" value='${locCodLott}' scope="request" />

	<c:choose>
		<c:when test='${not empty param.numRagg}' >
			<c:set var="locNumRagg" value='${param.numRagg}' scope="request" />
		</c:when>
		<c:otherwise>
			<c:set var="locNumRagg" value='${requestScope.numRagg}' scope="request" />
		</c:otherwise>
	</c:choose>
		

	<gene:redefineInsert name="corpo">
		<gene:setString name="titoloMaschera" value='${gene:if(modo eq "NUOVO","Aggiungi impresa", gene:if(modo eq "MODIFICA", "Modifica impresa", "Dettaglio impresa"))}' />
		
		<c:choose>
			<c:when test='${locCodGara ne null and locCodGara ne ""}' >
				<c:set var="condCODGARA" value='= ${locCodGara}' scope="request" />
			</c:when>
			<c:otherwise>
				<c:set var="condCODGARA" value='IS NULL' scope="request" />
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test='${locCodLott ne null and locCodLott ne ""}' >
				<c:set var="condCODLOTT" value='= ${locCodLott}' scope="request" />
			</c:when>
			<c:otherwise>
				<c:set var="condCODLOTT" value='IS NULL' scope="request" />
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test='${locNumImpr ne null and locNumImpr ne ""}' >
				<c:set var="condNUM_IMPR" value='= ${locNumImpr}' scope="request" />
			</c:when>
			<c:otherwise>
				<c:set var="condNUM_IMPR" value='IS NULL' scope="request" />
			</c:otherwise>
		</c:choose>
		
		<c:set var="conditionWhere" value='W9IMPRESE.CODGARA ${condCODGARA} AND W9IMPRESE.CODLOTT ${condCODLOTT} AND W9IMPRESE.NUM_IMPR ${condNUM_IMPR}' scope="request" />
		
		<gene:formScheda entita="W9IMPRESE" where="${conditionWhere}" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9IMPRESE" >

			<gene:campoScheda campo="CODGARA" visibile="false" />
			<gene:campoScheda campo="CODLOTT" visibile="false" />
			<gene:campoScheda campo="NUM_IMPR" visibile="false" />
			<gene:campoScheda campo="NUM_RAGG" visibile="false" />
			
			<!-- campi per  W9FASI-->
			<gene:campoScheda campo="CODGARA" entita="W9FASI" visibile="false" 
				where="W9FASI.CODGARA = W9IMPRESE.CODGARA AND W9FASI.CODLOTT = W9IMPRESE.CODLOTT" value="${codgara}"/>
			<gene:campoScheda campo="CODLOTT" entita="W9FASI" visibile="false"   
				where="W9FASI.CODGARA = W9IMPRESE.CODGARA AND W9FASI.CODLOTT = W9IMPRESE.CODLOTT" value="${codlott}"/>
			<gene:campoScheda campo="NUM_APPA" entita="W9FASI" visibile="false" value="1"/>
			<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
			<gene:campoScheda campo="NUM" entita="W9FASI" visibile="false" />	
			
			<gene:campoScheda campo="TIPOAGG" obbligatorio="true" />
			<gene:campoScheda campo="PARTECIP" obbligatorio="true"/>
	
			<% // Impresa singola W9IMPRESE_TIPOAGG == 3 
			%>
	
			<c:if test='${modo eq "MODIFICA"}' > 
				<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetImpreseInvitateFunction" parametro="${locCodGara};${locCodLott};${locNumImpr};${locNumRagg}" />
			</c:if>
			
			<% // Impresa Multipla W9IMPRESE_TIPOAGG == 1 --> ATI 
			%>
			
			<c:if test='${modo ne "NUOVO"}' > 			
				<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetImpreseInvitateFunction" parametro="${locCodGara};${locCodLott};${locNumImpr};${locNumRagg}" />
			</c:if>	
			
			<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp">
				<jsp:param name="entita" value='W9IMPRESE' />
				<jsp:param name="chiave" value='${locCodGara};${locCodLott}}' />
				<jsp:param name="nomeAttributoLista" value='listaImpreseInvitatePartecipanti' />
				<jsp:param name="idProtezioni" value="W9IMPRESE" />
				<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9imprese/imprese_invitatePartecipanti.jsp" />
				<jsp:param name="arrayCampi" value="'W9IMPRESE_CODGARA_','W9IMPRESE_CODLOTT_','W9IMPRESE_NUM_IMPR_','W9IMPRESE_NUM_RAGG_','IMPR_NOMEST_','W9IMPRESE_CODIMP_','W9IMPRESE_RUOLO_','W9IMPRESE_PARTECIP_','W9IMPRESE_TIPOAGG_'"/>
				<jsp:param name="arrayVisibilitaCampi" value="false,false,false,false,true,false,true,false,false" />
				<jsp:param name="usaContatoreLista" value="true" />
				<jsp:param name="sezioneListaVuota" value="false" />
				<jsp:param name="titoloSezione" value="Impresa invitata / partecipante" />
				<jsp:param name="titoloNuovaSezione" value="Nuova impresa invitata / partecipante" />
				<jsp:param name="descEntitaVociLink" value="impresa invitata / partecipante" />
				<jsp:param name="msgRaggiuntoMax"  value="e Imprese invitate / partecipanti" />
				<jsp:param name="numMaxDettagliInseribili" value="10" />
			</jsp:include>
			
			<c:if test='${modo ne "NUOVO"}'>
				<gene:fnJavaScriptScheda funzione="modificaPartecipazione()" elencocampi="W9IMPRESE_PARTECIP" esegui="false" />
			</c:if>
			
			<c:set var="MaxImprese" value='${maxIdW9IMPRESEVisualizzabile}' scope="request" />
			
			<gene:fnJavaScriptScheda funzione="showSezioni('#W9IMPRESE_TIPOAGG#')" elencocampi="W9IMPRESE_TIPOAGG" esegui="true" />
			
			<gene:redefineInsert name="schedaNuovo" />

			<gene:redefineInsert name="schedaModifica">
				<c:if test='${gene:checkProtFunz(pageContext,"MOD","SCHEDAMOD")}'>
					<tr>
						<td class="vocemenulaterale" >
							<a href="javascript:schedaModifica();" title="Modifica dati" tabindex="1501">
							${gene:resource("label.tags.template.dettaglio.schedaModifica")}</a></td>
					</tr>
				</c:if>
			</gene:redefineInsert>
			
			<gene:redefineInsert name="schedaConferma">
				<tr id="rowMenuSalva">
					<td class="vocemenulaterale">
						<a href="javascript:schedaConfermaSalvataggio();" title="Salva modifiche" tabindex="1501">
							${gene:resource("label.tags.template.dettaglio.schedaConferma")}</a></td>
				</tr>
			</gene:redefineInsert>
			
			<gene:redefineInsert name="schedaAnnulla">
				<tr id="rowMenuAnnulla">
					<td class="vocemenulaterale">
						<a href="javascript:schedaAnnulla1();" title="Annulla modifiche" tabindex="1502">
							${gene:resource("label.tags.template.dettaglio.schedaAnnulla")}</a></td>
				</tr>
			</gene:redefineInsert>

			<gene:campoScheda>
				<td class="comandi-dettaglio" colSpan="2">
					<c:choose>
						<c:when test='${modo eq "VISUALIZZA"}' >
							<c:if test='${gene:checkProtFunz(pageContext,"MOD","SCHEDAMOD")}'>
								<INPUT type="button"  class="bottone-azione" value='${gene:resource("label.tags.template.dettaglio.schedaModifica")}' title='${gene:resource("label.tags.template.dettaglio.schedaModifica")}' onclick="javascript:schedaModifica();">
							</c:if>
						</c:when>
						<c:when test='${modo eq "NUOVO" or modo eq "MODIFICA"}' >
							<INPUT id="pulsanteSalva" type="button" class="bottone-azione" value="Salva" title="Salva modifiche" onclick="javascript:schedaConfermaSalvataggio();">
							<INPUT id="pulsanteAnnulla" type="button" class="bottone-azione" value="Annulla" title="Annulla modifiche" onclick="javascript:schedaAnnulla1();">
						</c:when>
					</c:choose>
					&nbsp;
				</td>
			</gene:campoScheda>
			<% /*
			<c:if test="${empty param.tipoImpresa}" >
				<gene:fnJavaScriptScheda funzione="gestioneTipoPartecipante()" elencocampi="W9IMPRESE_TIPOAGG" esegui="false" />
			</c:if> */%>
		</gene:formScheda>
	</gene:redefineInsert>

	<gene:javaScript>
	
		// variabile da geneweb per il modo apertura	modoAperturaScheda
	
		// ad ogni cambio di selettore mi occupo di visualizare o nascondere campi da riempire
		// mi occupo inoltre di tenere sempre valorizzato il primo campo di una lista
		// nel caso di un passaggio da impresa singola a impresa multipla
		// nel caso di una scheda multipla mi occupo di visualizzare comunque un primo campo 
		// maxIdW9IMPRESEVisualizzabile numero massimo di occorrenze	
		
		function showSezioni(tipo){
			
			if (tipo == null || tipo == '' ) { // caso inserimento nuova Impresa nessun valore settato
				showObj("rowLinkAddW9IMPRESE",false);
				for (var i=1; i <= lastIdW9IMPRESEVisualizzata ; i++) {
					showObj("rowtitoloW9IMPRESE_" + i,false);
					showObj("rowIMPR_NOMEST_" + i,false);
					showObj("rowW9IMPRESE_PARTECIP_" + i,false);
					showObj("rowW9IMPRESE_RUOLO_" + i,false);
					showObj("rowW9IMPRESE_TIPOAGG_" + i,false);
				}
			} else {
				// in qualsiasi caso io mi trovi se non ho nessuna impresa visualizzata mi occupo di mostrarne una
				if (lastIdW9IMPRESEVisualizzata == 0) {
						showNextElementoSchedaMultipla('W9IMPRESE', new Array('W9IMPRESE_CODGARA_','W9IMPRESE_CODLOTT_','W9IMPRESE_NUM_IMPR_','W9IMPRESE_NUM_RAGG_','IMPR_NOMEST_','W9IMPRESE_CODIMP_','W9IMPRESE_RUOLO_','W9IMPRESE_PARTECIP_','W9IMPRESE_TIPOAGG_'), new Array(false,false,false,false,true,false,true,false,false));	
				}
			
				if (tipo == 3) { //caso Impresa singola va mostrato un solo valore
					//nascondo aggiunta schede
					showObj("rowLinkAddW9IMPRESE",false);
					//ciclo su tutti gli elementi nascondendo titolo e ruolo
					var conteggioRigheVisualizzabili = 0;
					for (var i=1; i <= lastIdW9IMPRESEVisualizzata ; i++) {
						showObj("rowtitoloW9IMPRESE_" + i,false);
						showObj("rowW9IMPRESE_RUOLO_" + i,false);
						// per il nome impresa devo ragionare sul fatto che ne devo visualizzare una soltanto
						// se il nome non è vuoto
						// la riga non è stata cancellata			
						if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("W9IMPRESE_DEL_W9IMPRESE_" + i) != 1 && conteggioRigheVisualizzabili == 0) {
							showObj("rowIMPR_NOMEST_" + i,true);
							conteggioRigheVisualizzabili ++;
						} else {
							showObj("rowIMPR_NOMEST_" + i,false);
						}
					}
					// funziona se almeno una riga viene popolata, altrimenti mostro la prima dopo averla svuotata
					if (conteggioRigheVisualizzabili == 0) {
						setValue("rowIMPR_NOMEST_" + 1, null);
						showObj("rowIMPR_NOMEST_" + 1,true);
						//controllo se l'impresa doveva esser cancellata
						if (getValue("W9IMPRESE_DEL_W9IMPRESE_" + 1) == 1) {
							setValue("W9IMPRESE_DEL_W9IMPRESE_" + 1, null);
						}
					}	
				} else { //tutti gli altri casi riguardanti Impresa Multipla
					// visualizzo l'aggiunta schede
								
					if(lastIdW9IMPRESEVisualizzata < maxIdW9IMPRESEVisualizzabile){
						showObj("rowLinkAddW9IMPRESE",true);
					}
					
					var conteggioRigheVisualizzabili = 0;
					for(var i=1; i <= lastIdW9IMPRESEVisualizzata ; i++){
						if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("W9IMPRESE_DEL_W9IMPRESE_" + i) != 1) {
							showObj("rowtitoloW9IMPRESE_" + i,true);
							showObj("rowIMPR_NOMEST_" + i,true);
							if (tipo == 1) {
								showObj("rowW9IMPRESE_RUOLO_" + i,true);
							} else {
								showObj("rowW9IMPRESE_RUOLO_" + i,false);
							}
							showObj("rowW9IMPRESE_TIPOAGG_" + i,false);
							conteggioRigheVisualizzabili ++;
						} else {
							showObj("rowtitoloW9IMPRESE_" + i,false);
							showObj("rowIMPR_NOMEST_" + i,false);
							showObj("rowW9IMPRESE_RUOLO_" + i,false);
							showObj("rowW9IMPRESE_TIPOAGG_" + i,false);
						}	
					}
					if (conteggioRigheVisualizzabili == 0) {
						showObj("rowtitoloW9IMPRESE_" + 1,true);
						setValue("rowIMPR_NOMEST_" + 1, null);
						setValue("rowW9IMPRESE_RUOLO_" + 1, null);
						showObj("rowIMPR_NOMEST_" + 1,true);
						if (tipo == 1) {
							showObj("rowW9IMPRESE_RUOLO_" + 1,true);
						} else {
							showObj("rowW9IMPRESE_RUOLO_" + 1,false);
						}
						//controllo se l'impresa doveva esser cancellata
						if (getValue("W9IMPRESE_DEL_W9IMPRESE_" + 1) == 1) {
							setValue("W9IMPRESE_DEL_W9IMPRESE_" + 1, null);
						}
					}
				}
			}
		}
		
		
	<c:if test='${modo ne "VISUALIZZA" }'>
	
	
	document.forms[0].jspPathTo.value="w9/w9imprese/w9imprese-scheda.jsp";

	//var openerKeyParent = window.opener.document.forms[0].keyParent.value;
	//var codGara = "" + openerKeyParent.split(";")[0];
	//var codLott = "" + openerKeyParent.split(";")[1];
	var codGara = "${locCodGara}";
	var codLott = "${locCodLott}";
	setValue("W9IMPRESE_CODGARA", codGara);
	setValue("W9IMPRESE_CODLOTT", codLott);
	
	function modificaPartecipazione() {
		var partecipazione = getValue("W9IMPRESE_PARTECIP");
		
		for (var i=1; i <= lastIdW9IMPRESEVisualizzata; i++) {
			if (getValue("elementoSchedaMultiplaVisibileW9IMPRESE_" + i) == "1") {
				setValue("W9IMPRESE_PARTECIP_" + i, partecipazione);
			}
		}
	}
	

	
	function modificaImpresa() {
	<c:choose>
		<c:when test="${empty locNumRagg}">
			var href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9imprese/w9imprese-scheda.jsp&modo=MODIFICA&codGara=${locCodGara}&codLott=${locCodLott}&numImpr=${locNumImpr}&key=${param.key}";
			document.location.href = href;
		</c:when>
		<c:when test="${not empty locNumRagg}">
			var href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9imprese/w9imprese-scheda.jsp&modo=MODIFICA&codGara=${locCodGara}&codLott=${locCodLott}&numImpr=${locNumImpr}&numRagg=${locNumRagg}&key=${param.key}";
			document.location.href = href;
		</c:when>
	</c:choose>
	}
	
	function schedaConfermaSalvataggio() {
	
		//i campi che posso avere sono 2 tipo e partecipazione
		tipoImpresa = getValue("W9IMPRESE_TIPOAGG");
		partecipazioneImpresa = getValue("W9IMPRESE_PARTECIP");
		
		if(tipoImpresa == 3){
			// ciclo sugli elementi per trovarne almeno 1
			var conteggioRighePopolate = 0;
			for(var i=1; i <= lastIdW9IMPRESEVisualizzata ; i++) {
				if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("W9IMPRESE_DEL_W9IMPRESE_" + i) != 1 && conteggioRighePopolate == 0){					
					// se trovo un valore che faccia al caso mio incremento la sentinella
					conteggioRighePopolate ++;
					// setto i due campi tipo e partecipazione
					setValue("W9IMPRESE_TIPOAGG_" + i, tipoImpresa);
					setValue("W9IMPRESE_PARTECIP_" + i, partecipazioneImpresa);
					//essendo impresa singola ruolo e numeroraggruppamento risulteranno nulli
					setValue("W9IMPRESE_RUOLO_" + i, null);
					setValue("W9IMPRESE_NUM_RAGG_" + i, null);
					// se sono in modifica mi occupo di valorizzare il campo necessario allo scopo 
					if(modoAperturaScheda == "MODIFICA"){
						setValue("W9IMPRESE_MOD_W9IMPRESE_" + i, 1);
					}
				}else {
					// il valore non deve venire usato
					if(modoAperturaScheda == "NUOVO"){
						// in modalita' nuovo sbianco tutti i campi
						setValue("IMPR_NOMEST_" + i, null);
						setValue("W9IMPRESE_CODGARA_" + i, null);
						setValue("W9IMPRESE_TIPOAGG_" + i, null);
						setValue("W9IMPRESE_RUOLO_" + i, null);
						setValue("W9IMPRESE_NUM_RAGG_" + i, null);
						setValue("W9IMPRESE_CODIMP_" + i, null);
						setValue("W9IMPRESE_NUM_IMPR_" + i, null);
						setValue("W9IMPRESE_CODLOTT_" + i, null);
						setValue("W9IMPRESE_PARTECIP_" + i, null);
						setValue("W9IMPRESE_DEL_W9IMPRESE_" + i, null);
						setValue("W9IMPRESE_MOD_W9IMPRESE_" + i, null);
					}
					if(modoAperturaScheda == "MODIFICA"){
						// in modifica mi limito a contrassegnare l'impresa come eliminabile
						setValue("W9IMPRESE_DEL_W9IMPRESE_" + i, 1);
					}	
				}
			}
			// se e solo se ho un'unica voce popolata procedo all'inserimento/modifica
			if (conteggioRighePopolate == 1){
				schedaConferma();
			}
		}else{
			if(tipoImpresa != 3 && tipoImpresa != null){
				var conteggioRighePopolate = 0;
				for(var i=1; i <= lastIdW9IMPRESEVisualizzata ; i++){
					if (getValue("IMPR_NOMEST_" + i) != null && getValue("IMPR_NOMEST_" + i) != '' && getValue("W9IMPRESE_DEL_W9IMPRESE_" + i) != 1){
						conteggioRighePopolate++;
						setValue("W9IMPRESE_TIPOAGG_" + i, tipoImpresa);
						setValue("W9IMPRESE_PARTECIP_" + i, partecipazioneImpresa);
						if(tipoImpresa != 1){
							setValue("W9IMPRESE_RUOLO_" + i, null);
						}
						if(modoAperturaScheda == "MODIFICA"){
							setValue("W9IMPRESE_MOD_W9IMPRESE_" + i, 1);
						}
					}else {
						if(modoAperturaScheda == "NUOVO"){
							// in modalita' nuovo sbianco tutti i campi
							setValue("IMPR_NOMEST_" + i, null);
							setValue("W9IMPRESE_CODGARA_" + i, null);
							setValue("W9IMPRESE_TIPOAGG_" + i, null);
							setValue("W9IMPRESE_RUOLO_" + i, null);
							setValue("W9IMPRESE_NUM_RAGG_" + i, null);
							setValue("W9IMPRESE_CODIMP_" + i, null);
							setValue("W9IMPRESE_NUM_IMPR_" + i, null);
							setValue("W9IMPRESE_CODLOTT_" + i, null);
							setValue("W9IMPRESE_PARTECIP_" + i, null);
							setValue("W9IMPRESE_DEL_W9IMPRESE_" + i, null);
							setValue("W9IMPRESE_MOD_W9IMPRESE_" + i, null);
						}
						if(modoAperturaScheda == "MODIFICA"){
							// in modifica mi limito a contrassegnare l'impresa come eliminabile
							setValue("W9IMPRESE_DEL_W9IMPRESE_" + i, 1);
						}	
					}
				}
				// mando in salvataggio solo se ho almeno 1 riga popolata
				if (conteggioRighePopolate > 0){
					schedaConferma();
				}
			}
		}
	}

	</c:if>

	function schedaAnnulla1() {
<c:choose>
	<c:when test='${param.annulla eq 1}'>
		historyVaiIndietroDi(1);
	</c:when>
	<c:otherwise>
		schedaAnnulla();
	</c:otherwise>
</c:choose>
	}
	
	<c:if test='${modo ne "VISUALIZZA"}' >

	var tmp = showNextElementoSchedaMultipla;
	
	showNextElementoSchedaMultipla = function(tipo, campi, visibilitaCampi) {
		var tipoImpresa = getValue("W9IMPRESE_TIPOAGG");
		
		if (tipoImpresa == 1) {
			tmp(tipo, campi, new Array(false,false,false,false,true,false,true,false,false)); 
		} else {
			tmp(tipo, campi, new Array(false,false,false,false,true,false,false,false,false)); 
		}		
	}
	
	</c:if>
	
	
	<c:if test='${not empty RISULTATO and RISULTATO eq "OK"}' >
		bloccaRichiesteServer();
		// torno alla lista
		historyVaiIndietroDi(1);
	</c:if>
	
	<c:if test='${modo eq "MODIFICA" || modo eq "NUOVO"}' >
		<c:set var="localkey" value='${codgara};${codlott};${flusso};1' scope="request" />
		<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
	</c:if>
	
	
	</gene:javaScript>
</gene:template>
</div>