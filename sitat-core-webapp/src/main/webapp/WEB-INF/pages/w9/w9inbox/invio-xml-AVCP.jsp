
<%
	/*
	 * Created on 07-nov-2012
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

<gene:template file="scheda-template.jsp">
		<gene:setString name="titoloMaschera" value="Invio dei dati al SIMOG" />
		
		<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>
		
		<gene:redefineInsert name="corpo" >
			<table class="lista">
			<c:set var="count" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.GetCountFileXMLToSendAVCPFunction", pageContext)}' />

			<tr>
				<td>
				    Server SFTP: ${requestScope.urlSimogFTP } <br>
					<br>
				<c:choose>
					<c:when test='${count == "0"}'>
						<b>Non ci sono file da inviare.</b>
						<br>
						<br>Premi <b>Indietro</b> per tornare alla pagina precedente.
					</c:when>
					<c:when test='${count == "1"}'>
						<b>C'&egrave; un file da inviare.</b>
						<br>
						<br>Premi <b>Invia</b> per trasmettere il file al SIMOG.
						<br>Premi <b>Indietro</b> per tornare alla pagina precedente.
					</c:when>
					<c:otherwise>
						<b>Ci sono ${count} file da inviare.</b>
						<br>
						<br>Premi <b>Invia</b> per trasmettere i file al SIMOG.
						<br>Premi <b>Indietro</b> per tornare alla pagina precedente.
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<div id="message"></div>
				</td>
			</tr>
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
					<c:if test='${count != 0}'>
						<INPUT type="button" class="bottone-azione" value="Invia" title="Invia" onclick="javascript:Invia()">
					</c:if>
					<input type="button" value="Indietro" title="Indietro" class="bottone-azione" onclick="javascript:Indietro();"/>&nbsp;&nbsp;
				</td>
			</tr>
			</table>
		</gene:redefineInsert>
		<gene:javaScript>

	if (window.opener != null) {
		window.opener.currentPopUp=null;

    window.onfocus=resettaCurrentPopup;
	}
	
	function resettaCurrentPopup() {
		window.opener.currentPopUp=null;
	}
	
	function Indietro(){
		document.location.href = '${pageContext.request.contextPath}/Home.do?' + csrfToken;
	}

	function Invia(){
		$(":input").attr("disabled", true);
		$("a").on('click', false);
		$("#message").text("Invio in corso...");
		$.ajax({ url : '${pageContext.request.contextPath}/w9/InvioXMLAVCP.do', 
	  	data : "",
		success: function(response){
					$("#message").text(response);
					$(":input").attr("disabled", false);
					$("a").unbind('click', false);
		         },
		error: function(e){
					$("#message").text("Errore");
					$("#message").css('color', 'red');
					$(":input").attr("disabled", false);
					$("a").unbind('click', false);
		         }
		});
	}
	

	
</gene:javaScript>	

		<gene:redefineInsert name="debugDefault"></gene:redefineInsert>
		
</gene:template>

