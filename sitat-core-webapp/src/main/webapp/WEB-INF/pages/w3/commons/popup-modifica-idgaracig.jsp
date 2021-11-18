
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
	<gene:setString name="titoloMaschera" value='Richiesta di modifica dati' />
	
	<gene:redefineInsert name="corpo">
		<form action="${contextPath}/w3/ModificaIDGARACIG.do" method="post" name="formIDGARACIG">
		
			<c:set var="entita" value="${param.entita}" scope="request"/>
		
			<input type="hidden" name="entita" value="${entita}" />
			<input type="hidden" name="numgara" value="${param.numgara}" />
			<input type="hidden" name="numlott" value="${param.numlott}" />
			
			<table class="dettaglio-notab">
				<jsp:include page="sezione-credenziali-simog.jsp"/>
				<tr>
					<td colspan="2" class="comandi-dettaglio">
						<INPUT type="button" class="bottone-azione" value="Invia richiesta di modifica" title="Invia richiesta di modifica"	onclick="javascript:modificaIDGARACIG();">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>	
	</gene:redefineInsert>
	
	<gene:javaScript>
		document.forms[0].jspPathTo.value="w3/commons/popup-modifica-idgaracig.jsp";
		
		function annulla(){
			window.close();
		}
		
		function resetRecuperaUser() {
			document.formIDGARACIG.recuperauser.value = "0";
		}

		function resetRecuperaPassword() {
			document.formIDGARACIG.recuperapassword.value = "0";
		}

		function resetPassword() {
			document.formIDGARACIG.simogwspass.value = "";
		}	
		
		function modificaIDGARACIG(){
		
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

