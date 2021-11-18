<%
	/*
	 * Created on 29-Marzo-2018
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

<div style="width:97%;">

<gene:template file="popup-message-template.jsp">

	<gene:setString name="titoloMaschera" value='Controllo dei dati del lotto e delle fasi esistenti' />
	
	<gene:redefineInsert name="corpo">
		<c:choose>
			<c:when test="${!empty listaMessaggiDiControllo}">
				<jsp:include page="../commons/popup-validazione-internoAL.jsp" />	
			</c:when>
			<c:otherwise>
						<br>&nbsp;<br>
						<b>Non &egrave; stato rilevato alcun problema.</b>
						<br>&nbsp;<br><br>
			</c:otherwise>
		</c:choose>

<c:choose>
	<c:when test='${empty nonEseguireInvioDati}'>
		<form name="formControlloDati" action="${pageContext.request.contextPath}/w9/InviaDatiLottoAL.do" method="post" >
	</c:when>
	<c:otherwise>
		<form name="formControlloDati" action="${pageContext.request.contextPath}/w9/InviaDatiLottoAL.do?eseguireSoloControlloDati=true" method="post" >
	</c:otherwise>
</c:choose>
		<form name="formControlloDati" action="${pageContext.request.contextPath}/w9/InviaDatiLottoAL.do?eseguireSoloControlloDati=true" method="post" >
			<input type="hidden" name="codiceGara" id="codiceGara" value="${param.codiceGara}" />
			<input type="hidden" name="codiceLotto" id="codiceLotto" value="${param.codiceLotto}" />
			<input type="hidden" name="metodo" id="metodo" value="controlloDati" />
		</form>
	
		<form name="formInvioDati" action="${pageContext.request.contextPath}/w9/InviaDatiLottoAL.do" method="post" >
			<input type="hidden" name="codiceGara" id="codiceGara" value="${param.codiceGara}" />
			<input type="hidden" name="codiceLotto" id="codiceLotto" value="${param.codiceLotto}" />
			<input type="hidden" name="metodo" id="metodo" value="eseguiInvioDati" />
		</form>

	</gene:redefineInsert>
	<gene:redefineInsert name="buttons">
			<c:if test='${(empty requestScope.nonEseguireInvioDati) and (numeroErrori eq 0)}'>
				<INPUT type="button" class="bottone-azione" value="Conferma invio" title="Conferma invio" onclick="javascript:confermaInvio()">
			</c:if>

			<c:if test='${numeroErrori ne 0}'>
				<INPUT type="button" class="bottone-azione" value="Controlla nuovamente" title="Controlla nuovamente" onclick="javascript:controlla()">
			</c:if>

			<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
	</gene:redefineInsert>

	<gene:javaScript>
	
		window.opener.currentPopUp = null;
	    window.onfocus = resettaCurrentPopup;
	
		function resettaCurrentPopup() {
			window.opener.currentPopUp = null;
		}
		
		function annulla() {
			window.close();
		}
	
		function controlla() {
			bloccaRichiesteServer();
			document.formControlloDati.submit();
		}
		
		function confermaInvio() {
			bloccaRichiesteServer();
			document.formInvioDati.submit();
		}
	
	</gene:javaScript>
</gene:template>

</div>
