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

<%/* Dati generali del lavoro */%>
<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9ESITO-A22-scheda">

	<c:set var="key" value='${key}' scope="request" />
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
	<c:set var="flusso" value='984' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="key2" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
	<c:set var="key3" value='1' scope="request" />

	<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,key,"W9ESITO")}' scope="request" />

	<c:set var="listaExistsFasi" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.ListaExistsFasiGaraLottoFunction", pageContext, codgara, codlott)}' />
	<gene:setString name="titoloMaschera" value='${gene:if("NUOVO"==modo, "Nuovo esito procedura di selezione del contraente", "Esito procedura di selezione del contraente")}' />	
	
	<gene:redefineInsert name="corpo">
	
		<gene:formScheda entita="W9ESITO" gestisciProtezioni="true"
			plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9ESITO"
			gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9ESITO">
			
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />
			
			<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione || ! empty esisteFaseAggiudicazione}' >
				<gene:redefineInsert name="schedaModifica" />
				<gene:redefineInsert name="pulsanteModifica" />
			</c:if>
			
			<c:set var="jspPath" value='w9lott-scheda-a22.jsp' scope="request" />
			
			<gene:campoScheda>
				<td colspan="2"><b>Dati generali </b></td>
			</gene:campoScheda>
					
			<gene:campoScheda campo="CODGARA" visibile="false" value="${codgara}" />
			<gene:campoScheda campo="CODLOTT" visibile="false" value="${codlott}" />
			<gene:campoScheda campo="ESITO_PROCEDURA" obbligatorio="true" defaultValue="${requestScope.esito}" modificabile='${esito ne 1 or esitiUguali eq "true"}' />
			<gene:campoScheda campo="IMPORTO_AGGIUDICAZIONE" defaultValue="${requestScope.importoAggiu}" modificabile="false" visibile="${!empty datiRiga.W9ESITO_IMPORTO_AGGIUDICAZIONE}" />
			<c:set var="labelData" value="Data aggiudicazione definitiva" />
			<c:if test='${esito > 1}'>
				<c:set var="labelData" value="Data verbale" />
			</c:if>
			<gene:campoScheda campo="DATA_VERB_AGGIUDICAZIONE" defaultValue="${requestScope.dataVerbAggiu}" title="${labelData}" modificabile="false" visibile="${!empty datiRiga.W9ESITO_DATA_VERB_AGGIUDICAZIONE}" >
				<gene:checkCampoScheda 
				funzione='controllaESITO_PROCEDURA(${fase_1},${fase_12},${fase_987})' 
				messaggio='L\'esito procedura specificato non &egrave; compatibile con le fasi gi&agrave; create' 
				obbligatorio="true" />	
			</gene:campoScheda>
			
			<gene:campoScheda campo="VISUALIZZAFILEALLEGATO" title="File PDF per la pubblicazione sul sito della Regione"
				campoFittizio="true" modificabile="false" definizione="T200;0" visibile='${ctrlBlob eq "true"}' >
				<c:choose>
					<c:when test='${ctrlBlob eq "false"}'>
						Nessun File Allegato
					</c:when>
					<c:when test='${ctrlBlob eq "true"}'>
						<a href='javascript:apriAllegato();'>Visualizza</a>
						<c:if test='${modoAperturaScheda eq "MODIFICA"}'>
							&nbsp;&nbsp;<A id="icoVISUALIZZAFILEALLEGATO"
								href="javascript:showMenuPopup('icoVISUALIZZAFILEALLEGATO',linksetJsPopUpVISUALIZZAFILEALLEGATO);"><IMG
								src="${pageContext.request.contextPath}/img/opzioni_info.gif" title="" alt="" height="16"
								width="16"></A>
						</c:if>
					</c:when>
				</c:choose>
			</gene:campoScheda>
			<gene:campoScheda campo="FILEDAALLEGARE" campoFittizio="true" visibile="false" definizione="T200;0" />

			<gene:campoScheda campo="CODGARA" entita="W9FASI" visibile="false" defaultValue="${codgara}" 
				where="W9FASI.CODGARA = W9ESITO.CODGARA AND W9FASI.CODLOTT = W9ESITO.CODLOTT AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
			<gene:campoScheda campo="CODLOTT" entita="W9FASI" visibile="false" defaultValue="${codlott}" 
				where="W9FASI.CODGARA = W9ESITO.CODGARA AND W9FASI.CODLOTT = W9ESITO.CODLOTT AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
			<gene:campoScheda campo="NUM_APPA" entita="W9FASI" visibile="false" defaultValue="${aggiudicazioneSelezionata}" 
				where="W9FASI.CODGARA = W9ESITO.CODGARA AND W9FASI.CODLOTT = W9ESITO.CODLOTT AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
			<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
			<gene:campoScheda campo="NUM" entita="W9FASI" visibile="false" 
				where="W9FASI.CODGARA = W9ESITO.CODGARA AND W9FASI.CODLOTT = W9ESITO.CODLOTT AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
			
	<gene:gruppoCampi idProtezioni="campiPubblicazioneEsito" >
		<gene:campoScheda>
			<td colspan="2"><b>Pubblicazione esito di gara </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="CODGARA" entita="W9PUES" visibile="false" value="${codgara}"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="CODLOTT" entita="W9PUES" visibile="false" value="${codlott}"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="NUM_INIZ" entita="W9PUES" visibile="false" value="1"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="NUM_PUES" entita="W9PUES" visibile="false" value="1"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />

		<gene:campoScheda campo="DATA_GUCE" entita="W9PUES" 
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="DATA_GURI" entita="W9PUES"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="DATA_ALBO" entita="W9PUES"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="QUOTIDIANI_NAZ" entita="W9PUES"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="QUOTIDIANI_REG" entita="W9PUES"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="PROFILO_COMMITTENTE" entita="W9PUES"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="SITO_MINISTERO_INF_TRASP" entita="W9PUES"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />
		<gene:campoScheda campo="SITO_OSSERVATORIO_CP" entita="W9PUES"
			where="W9PUES.CODGARA = W9ESITO.CODGARA AND W9PUES.CODLOTT = W9ESITO.CODLOTT AND W9PUES.NUM_INIZ = 1" />

	</gene:gruppoCampi>
			
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
	</gene:redefineInsert>
	<gene:redefineInsert name="addToAzioni">
		<jsp:include page="../commons/addtodocumenti-validazione.jsp" />
	</gene:redefineInsert>
	
	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
	</gene:redefineInsert>
	
	<gene:javaScript>
	
		
	$(window).ready(function () {
		document.forms[0].encoding="multipart/form-data";
	
		var linksetJsPopUpVISUALIZZAFILEALLEGATO="";
		var apertura = ${(modoAperturaScheda ne "VISUALIZZA")};
		var nonEsistenzaBlob = ${(ctrlBlob ne "true")}; 
		var allegatoDaVisualizzare = (apertura && nonEsistenzaBlob);
		showObj("rowNOMEFILEALLEGATO", allegatoDaVisualizzare);

		linksetJsPopUpVISUALIZZAFILEALLEGATO+=creaVocePopUpChiusura("${pageContext.request.contextPath}/");
		linksetJsPopUpVISUALIZZAFILEALLEGATO+=creaPopUpSubmenu("javascript:javascript:eliminaFile();hideMenuPopup();",0,"&nbsp;Elimina");
		
		<c:if test='${! empty removePropostaAggiudicazione}' >
			$("#W9ESITO_ESITO_PROCEDURA option[value='5']").remove();
		</c:if>
		<c:if test='${! empty removeAggiudicazione}' >
			$("#W9ESITO_ESITO_PROCEDURA option[value='1']").remove();
		</c:if>
		
	});
	
		
		function apriAllegato() {
			var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
			var par = new String('codgara=' + getValue("W9ESITO_CODGARA") + '&tab=esito&codlott=' + getValue("W9ESITO_CODLOTT"));
			document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
		}
		
		function eliminaFile() {
			if(confirm("Eliminare il file Pdf allegato ?")){
				showObj("rowVISUALIZZAFILEALLEGATO",false);
				showObj("rowNOMEFILEALLEGATO",true);
				setValue("FILEDAALLEGARE","canc");
			}
		}
	
		function popupValidazione(flusso,key1,key2,key3) {
	  		var comando = "href=w9/commons/popup-validazione-generale.jsp";
	  		comando = comando + "&flusso=" + flusso;
	  		comando = comando + "&key1=" + key1;
	  		comando = comando + "&key2=" + key2;
	  		comando = comando + "&key3=" + key3;
	 		openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
  	}
	  	
	  function controllaESITO_PROCEDURA(fase_1, fase_12, fase_987){
			var esito_procedura = getValue("W9ESITO_ESITO_PROCEDURA");
			if (esito_procedura != 1 && (fase_1 || fase_12 || fase_987 )) {
				return false;
			}
			return true;
		}

	<c:if test='${modo eq "MODIFICA"}' >
		<c:set var="localkey" value='${codgara};${codlott};${flusso};${key3}' scope="request" />
		<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
	</c:if>

	</gene:javaScript>
</gene:template>
