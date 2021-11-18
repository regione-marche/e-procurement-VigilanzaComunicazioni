<%
/*
 * Created on: 08-mar-2007
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

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="inserimento" value="${inserimento}" />

<c:choose>
	<c:when test='${modo eq "NUOVO" and empty inserimento}'>
		<c:set var="contri" value='${param.key}' scope="request" />
		<c:set var="tiprog" value="${param.tiprog}"/>
		${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetNextConintFunctionINR", pageContext, contri)}
		<c:set var="keyINRTRI" value='INRTRI.CONTRI=N:${contri};INRTRI.CONINT=N:${count}' scope="request" />
	</c:when>
	<c:otherwise>
		<c:set var="contri" value='${gene:getValCampo(key,"INRTRI.CONTRI")}' scope="request" />
		<c:set var="keyINRTRI" value='${key}' scope="request" />
	</c:otherwise>
</c:choose>

<gene:template file="popup-template.jsp" gestisciProtezioni="true"
	schema="PIANI" idMaschera="inserisci-intervento-non-riproposto">

	<gene:setString name="titoloMaschera" value='${tiprog eq "2" ? "Inserimento di un acquisto non riproposto" : "Inserimento di un intervento non riproposto"}' />
	<gene:redefineInsert name="corpo">

		<gene:formScheda entita="INRTRI" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreINRTRI">
			<gene:campoScheda campo="CONTRI" visibile="false" value='${contri}' />
			<gene:campoScheda campo="CONINT" visibile="false" />
			<gene:campoScheda campo="CUIINT" modificabile="false" speciale="true" obbligatorio="true" >
	  	 		<c:if test='${modoAperturaScheda ne "VISUALIZZA" or modo ne "VISUALIZZA"}' >
					<gene:popupCampo titolo="Modifica CUI" href='javascript:modificaCui()' />
		 		</c:if>
			</gene:campoScheda>
			<gene:campoScheda campo="CUPPRG" visibile="true" />
			<gene:campoScheda campo="DESINT" visibile="true" />
			<gene:campoScheda campo="TOTINT" visibile="true" />
			<gene:campoScheda campo="PRGINT" visibile="true" />
			<gene:campoScheda campo="MOTIVO" visibile="true" />
			
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>

	</gene:redefineInsert>

	<gene:javaScript>
		$(document).ready(function(){
		 	if('${inserimento}' == 'effettuato' ){
		 		window.opener.historyReload();
				window.close();
		 	}		
		});	
	
		var schedaConferma_Default = schedaConferma;
		
		function schedaConferma_Custom(){
			document.forms[0].jspPathTo.value= "/WEB-INF/pages/piani/piatri/popup-inserisci-intervento-non-riproposto.jsp";
			schedaConferma_Default();
		}

		schedaConferma = schedaConferma_Custom;
		
		function annulla() {
			window.close();
		} 
		
		schedaAnnulla = annulla;
		
		function modificaCui() {
			openPopUpCustom("href=piani/piatri/dettaglio-codice-cuiint.jsp&modo=MODIFICA&key=${keyINRTRI}&cui="+ getValue("INRTRI_CUIINT") + "&tiprog=${tiprog}", "formCUIINT", 700, 300, 1, 1);				
		}
		
	</gene:javaScript>

</gene:template>
