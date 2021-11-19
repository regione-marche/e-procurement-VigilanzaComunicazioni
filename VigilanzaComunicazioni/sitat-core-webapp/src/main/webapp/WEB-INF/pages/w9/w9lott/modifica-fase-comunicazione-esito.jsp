<%
/*
 * Created on: 30/04/2013
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
<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="modifica-fase-comunicazione-esito">

	<gene:setString name="titoloMaschera" value='Modifica fase comunicazione esito' />	
	
	<gene:redefineInsert name="corpo">
	
		<gene:formScheda entita="W9ESITO" gestisciProtezioni="true"
			gestore="it.eldasoft.w9.tags.gestori.submit.GestoreModificheLottiGara">
			<c:set var="key" value='${key}' scope="request" />
			<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
			<c:if test='${(codgara eq "")||(empty codgara)}'>
				<c:set var="codgara" value='${gene:getValCampo(keyParent,"CODGARA")}' />
			</c:if>
			<input type="hidden" name="codgara" value="${codgara}" />
			<input type="hidden" name="tipoScheda" value="comunicazione-esito" />
		
			<gene:campoScheda>
				<td colspan="2"><b>Dati generali </b></td>
			</gene:campoScheda>
					
			<gene:campoScheda campo="ESITO_PROCEDURA"/>

			<gene:campoScheda campo="DATA_VERB_AGGIUDICAZIONE"/>
			
			<gene:campoScheda campo="NOMEFILEALLEGATO"
				title="Seleziona il File PDF per la pubblicazione sul sito della Regione"
				campoFittizio="true" modificabile="false" definizione="T200;0">
						<input id="selezioneFile" name="selezioneFile" type="file" size="50"
						onchange='javascript:scegliFile(this.value);' onkeydown="blur()" onkeyup="blur()"/>
					&nbsp;&nbsp;
			</gene:campoScheda>
			<gene:campoScheda campo="FILEDAALLEGARE" campoFittizio="true" visibile="false" definizione="T200;0" />
			
			<gene:redefineInsert name="pulsanteSalva" >
			<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma " onclick="javascript:Conferma()">
			</gene:redefineInsert>
			<gene:redefineInsert name="schedaConferma">
				<tr>
					<td class="vocemenulaterale"><a href="javascript:Conferma();" title="Conferma" tabindex="1501">Conferma</a></td>
				</tr>
			</gene:redefineInsert>
		
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />
			
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
		document.forms[0].jspPathTo.value="w9/w9lott/scelta-tipo-scheda.jsp?key=${key}";
		document.forms[0].jspPath.value="/WEB-INF/pages/w9/w9lott/scelta-tipo-scheda.jsp?key=${key}";
		document.forms[0].encoding="multipart/form-data";

		function scegliFile(valore) {
			var selezioneFile = document.getElementById("selezioneFile").value;
			var setta = true;
			//BrowserDetect.browser + " ver. " + BrowserDetect.version + " su " + BrowserDetect.OS
			if ((BrowserDetect.browser == "Explorer") && (selezioneFile.indexOf("fakepath") != -1)) {
				alert('A causa di una restrizione del browser in uso (' + BrowserDetect.browser + ' ver. ' + BrowserDetect.version + '),\nnon &egrave; possibile effettuare l\'upload del file.\nPer ovviare a tale inconveniente, &egrave; necessario\neffettuare le seguenti operazioni: dal men&ugrave;\nStrumenti di Internet Explorer, selezionare Opzioni Internet,\nposizionarsi sul tab Protezione e premere il pulsante Livello personalizzato,\nquindi nella lista di opzioni attivare quella chiamata Includi\nil percorso locale durante il caricamento dei file in un server.');
				setta = false;
			}
			if (setta) {
				setValue("FILEDAALLEGARE",selezioneFile);
				setValue("VISUALIZZAFILEALLEGATO","");
				posizione_punto=selezioneFile.lastIndexOf(".");
				lunghezza_stringa=selezioneFile.length;
				estensione=selezioneFile.substring(posizione_punto+1,lunghezza_stringa).toUpperCase();
				
				if(estensione!="PDF"){
					alert("E' permessa la selezione solo di file in formato PDF");
					document.getElementById("selezioneFile").value="";
				}
			}
		}
		
		function Conferma() {
			response = confirm("Questa operazione modificher&agrave; i dati nelle maschere dei lotti. Continuare?");
			if (response) {
				schedaConferma();
			}
			return;
		}
	
	</gene:javaScript>
</gene:template>
