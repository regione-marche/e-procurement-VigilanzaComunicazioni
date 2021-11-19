
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
	<gene:setString name="titoloMaschera"  value='Richiesta di generazione del codice CUP'  />

	<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetCUPWSUserFunction" />
	<c:set var="id" value="${param.id}" />
	
	<gene:redefineInsert name="corpo">
		<form action="${contextPath}/piani/RichiestaGenerazioneCUP.do" method="post" name="formRichiestaGenerazioneCUP">
		
			<input type="hidden" name="id" value="${param.id}" />
			
			<table class="dettaglio-notab">
				<c:choose>
					<c:when test="${empty cupwsuser}">
						<tr>
							<td class="valore-dato" colspan="2">
								<br>
								Indicare le <b>credenziali</b> per l'accesso al servizio di generazione CUP.
								<br>
								<br>
							</td>
						</tr>
						<tr>	
							<td class="etichetta-dato">Utente/Password</td>
							<td class="valore-dato">
								<input type="text" name="cupwsuser" id="cupwsuser" size="15" />
								<input type="password" name="cupwspass" id="cupwspass" size="15" onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();"/>
							</td>
						</tr>
						<input type="hidden" name="recuperauser" value="0" />
						<input type="hidden" name="recuperapassword" value="0" />										
					</c:when>
					<c:otherwise>
						<tr>
							<td class="valore-dato" colspan="2">
								<br>
								Le <b>credenziali</b> per l'accesso al servizio sono state recuperate dalla precedente richiesta di generazione.
								<br>
								<br>
							</td>
						</tr>
						<tr>	
							<td class="etichetta-dato">Utente/Password</td>
							<td class="valore-dato">
								<input type="text" name="cupwsuser" id="cupwsuser" size="15" value="${cupwsuser}" onchange="javascript:resetRecuperaUser();"/>
								<input type="password" name="cupwspass" id="cupwspass" size="15" value="................"  onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" onchange="javascript:resetRecuperaPassword();"/>
							</td>
						</tr>
						<input type="hidden" name="recuperauser" value="1" />
						<input type="hidden" name="recuperapassword" value="1" />	
					</c:otherwise>
				</c:choose>
	
				<tr>
					<td class="etichetta-dato">Memorizza le credenziali</td>
					<td class="valore-dato">
						<input type="checkbox" name="memorizza" value="memorizza" checked="checked"/>					
					</td>
				</tr>
	
				<tr>
					<td colspan="2" class="comandi-dettaglio">
						<INPUT type="button" class="bottone-azione" value="Invia dati" title="Invia dati"	onclick="javascript:richiestaCUP();">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>	
	</gene:redefineInsert>
	
	<gene:javaScript>
		document.forms[0].jspPathTo.value="piani/cupdati/cupdati-popup-richiesta.jsp";
		
		function annulla(){
			window.close();
		}
		
		function resetRecuperaUser() {
			document.formRichiestaGenerazioneCUP.recuperauser.value = "0";
		}
	
		function resetRecuperaPassword() {
			document.formRichiestaGenerazioneCUP.recuperapassword.value = "0";
		}
	
		function resetPassword() {
			document.formRichiestaGenerazioneCUP.cupwspass.value = "";
		}	
		
		function richiestaCUP(){
		
			<c:choose>
				<c:when test="${empty cupwsuser}">
					var invia = "true";
				
					var cupwsuser = document.formRichiestaGenerazioneCUP.cupwsuser;
					if (cupwsuser.value == "") {
						alert("Inserire l'utente");
						invia = "false";
					}
					
					var cupwspass = document.formRichiestaGenerazioneCUP.cupwspass;			
					if (invia == "true" && cupwspass.value == "") {
						alert("Inserire la password");
						invia = "false";
					}
		
					if (invia == "true") {
						document.formRichiestaGenerazioneCUP.submit();
						bloccaRichiesteServer();
					}				
				</c:when>
				<c:otherwise>
					document.formRichiestaGenerazioneCUP.submit();
					bloccaRichiesteServer();
				</c:otherwise>
			</c:choose>
				
		}
	
	</gene:javaScript>	
</gene:template>

</div>

