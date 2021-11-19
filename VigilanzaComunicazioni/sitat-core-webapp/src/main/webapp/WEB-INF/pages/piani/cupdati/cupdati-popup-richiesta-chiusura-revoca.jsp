
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


<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${contextPath}/js/controlliFormali.js"></script>

<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetCUPWSUserFunction" />

<div style="width: 97%;"><gene:template file="popup-template.jsp">

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="gestioneHistory" />
	<gene:setString name="titoloMaschera" value='Chiusura Revoca CUP' />

	<gene:redefineInsert name="corpo">
	
		<form action="${contextPath}/piani/RichiestaChiusuraRevocaCUP.do" method="post" name="formRichiestaChiusuraRevocaCUP" >

			<input type="hidden" name="id" value="${param.id}" />
			<input type="hidden" name="codiceCUP" value="${param.codiceCUP}" />
			<input type="hidden" name="tipoOperazione" value="${param.tipoOperazione}" />
	
			<table class="ricerca">
				<tr>
					<td class="valore-dato-trova" colspan="2">
						<br>
						Questa funzione permette chiudere/revocare CUP.</b>
						<br>
						<br>
					</td>
				</tr>
				
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
					<td class="valore-dato-trova" colspan="2">
						<br>
						Indicare uno o pi&ugrave; <b>parametri di ricerca.</b>
						<br>
						<br>
					</td>
				</tr>

				<tr>
					<td colspan="2" class="comandi-dettaglio">
						<INPUT type="button" class="bottone-azione" value="Procedi" title="Procedi"	onclick="javascript:procedi();">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>	
	</gene:redefineInsert>

	<gene:javaScript>
	
		function resetRecuperaUser() {
			document.formRichiestaChiusuraRevocaCUP.recuperauser.value = "0";
		}
	
		function resetRecuperaPassword() {
			document.formRichiestaChiusuraRevocaCUP.recuperapassword.value = "0";
		}
	
		function resetPassword() {
			document.formRichiestaChiusuraRevocaCUP.cupwspass.value = "";
		}	
		
		function annulla(){
			window.close();
		}
		
		function procedi() {
			<c:choose>
				<c:when test="${empty cupwsuser}">
					var invia = "true";
				
					var cupwsuser = document.formRichiestaChiusuraRevocaCUP.cupwsuser;
					if (cupwsuser.value == "") {
						alert("Inserire l'utente");
						invia = "false";
					}
					
					var cupwspass = document.formRichiestaChiusuraRevocaCUP.cupwspass;			
					if (invia == "true" && cupwspass.value == "") {
						alert("Inserire la password");
						invia = "false";
					}
		
					if (invia == "true") {
						document.formRichiestaChiusuraRevocaCUP.submit();
						bloccaRichiesteServer();
					}				
				</c:when>
				<c:otherwise>
					document.formRichiestaChiusuraRevocaCUP.submit();
					bloccaRichiesteServer();
				</c:otherwise>
			</c:choose>
		}
		
	</gene:javaScript>
</gene:template></div>

