
<%
	/*
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

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div style="width:97%;">

<gene:template file="popup-template.jsp">

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="gestioneHistory" />
	
	<c:choose>
		<c:when test="${entita eq 'W3GARA'}">
			<gene:setString name="titoloMaschera"  value='Richiesta assegnazione numero gara' />
		</c:when>
		<c:when test="${entita eq 'W3LOTT'}">
			<gene:setString name="titoloMaschera"  value='Richiesta assegnazione codice CIG' />
		</c:when>
		<c:when test="${entita eq 'W3SMARTCIG'}">
			<gene:setString name="titoloMaschera"  value='Richiesta assegnazione codice SMARTCIG' />
		</c:when>
	</c:choose>

	<gene:redefineInsert name="corpo">
		<form action="${contextPath}/w3/RichiestaIDGARACIG.do" method="post" name="formIDGARACIG">
		
			<c:set var="entita" value="${param.entita}" scope="request"/>
		
			<input type="hidden" name="entita" value="${entita}" />
			<input type="hidden" name="numgara" value="${param.numgara}" />
			<input type="hidden" name="numlott" value="${param.numlott}" />
			
			<table class="dettaglio-notab">
				<jsp:include page="sezione-credenziali-simog.jsp"/>
				<tr>
					<td colspan="2" class="comandi-dettaglio">
						<INPUT type="button" class="bottone-azione" value="Invia richiesta di assegnazione" title="Invia richiesta di assegnazione" onclick="javascript:richiestaIDGARACIG();">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>	
	</gene:redefineInsert>
	
	<gene:javaScript>
		document.forms[0].jspPathTo.value="w3/commons/popup-richiesta-idgaracig.jsp";
		
		function annulla(){
			window.close();
		}
		
		function richiestaIDGARACIG(){
		
			var invia = "true";
		
			var simogwsuser = document.formIDGARACIG.simogwsuser;
			if (simogwsuser.value == "") {
				alert("Inserire l'utente");
				invia = "false";
			}
			
			var simogwspass = document.formIDGARACIG.simogwspass;			
			if (invia == "true" && simogwspass.value == "") {
				alert("Inserire la password");
				invia = "false";
			}

			if (invia == "true") {
				document.formIDGARACIG.submit();
				bloccaRichiesteServer();
			}				
				
		}
	
	</gene:javaScript>	
</gene:template>

</div>

