
<%
	/*
	 * Created on 15-lug-2008
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

<gene:template file="popup-template.jsp">

	<c:set var="flusso" value="${param.flusso}" />
	<c:set var="key1" value="${param.key1}" />
	<c:set var="key2" value="${param.key2}" />
	<c:set var="key3" value="${param.key3}" />
	<c:set var="key4" value="${param.key4}" />
	<c:set var="invio" value="${param.invio}" />	
	<c:set var="programmi" value="0" />
	<c:if test="${flusso eq 981 || flusso eq 982 || flusso eq 991 || flusso eq 992 }">
		<c:set var="programmi" value="1" />
	</c:if>
	<gene:setString name="titoloMaschera" value='${gene:callFunction5("it.eldasoft.w9.tags.funzioni.GestioneValidazioneTitleFunction",pageContext,flusso,key1,key2,key3)}' />
	
	<c:set var="userIsRup" value="si"/>
	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG") && programmi eq "0"}'>
		<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetSIMOGLAUserFunction" />
		<c:set var="chiaveGara" value="W9GARA.CODGARA=N:${key1}" />
		<c:set var="userIsRup" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsUserRupFunction",pageContext,chiaveGara,"W9GARA")}' />
	</c:if>
	<c:if test="${userIsRup eq 'no' }">
		<c:set var="existCredenzialiRup" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.RecuperaCredenzialiRupFunction",pageContext,chiaveGara)}' />
	</c:if>
	<gene:redefineInsert name="corpo">
		<table class="lista">
			<c:if test='${!empty invio && gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG") && programmi eq "0"}'>
				<c:set var="invio" value="loaderAppalto" />
			</c:if>
			<c:set var="params" value=";${flusso};${key1};${key2};${key3};${key4};${invio}" />
			<c:set var="stato" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GestioneValidazioneFunction",pageContext, params)}'/>		
			<gene:set name="titoloGenerico" value="${titolo}" />
			<gene:set name="listaGenericaControlli" value="${listaControlli}" />
			<gene:set name="numeroErrori" value="${numeroErrori}" />
			<gene:set name="numeroWarning" value="${numeroWarning}" />
			
			<c:choose>
				<c:when test="${!empty listaControlli and empty controlliSuBean}">
					<jsp:include page="../commons/popup-validazione-interno.jsp" />	
				</c:when>
				<c:when test="${!empty listaControlli and !empty controlliSuBean}">
					<jsp:include page="../commons/popup-validazione-interno-bean.jsp" />	
				</c:when>
				<c:otherwise>
					<tr>
						<td>
							<b>${titoloGenerico}</b>
							<br>&nbsp;<br>
							<b>Non &egrave; stato rilevato alcun problema.</b>
							<br>&nbsp;<br>				
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG") && programmi eq "0"}'>
			<tr><td><table class="dettaglio-tab" id="credenzialiSimog" style="visibility:hidden;">
			<c:choose>
				<c:when test="${empty simoguser}">
					<tr>
						<td class="valore-dato" colspan="2">
							<br>
							Indicare le <b>credenziali</b> per l'accesso al servizio di LoaderAppalto SIMOG.
							<br>
							<br>
						</td>
					</tr>
					<tr>	
						<td class="etichetta-dato">Utente/Password</td>
						<td class="valore-dato">
							<input type="text" name="simoguser" id="simoguser" size="15" />
							<input type="password" name="simogpass" id="simogpass" size="15" onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();"/>
						</td>
					</tr>
					<input type="hidden" id="recuperauser" name="recuperauser" value="0" />
					<input type="hidden" id="recuperapassword" name="recuperapassword" value="0" />										
				</c:when>
				<c:otherwise>
					<tr>
						<td class="valore-dato" colspan="2">
							<br>
							Le <b>credenziali</b> per l'accesso al servizio sono state recuperate dalla precedente richiesta.
							<br>
							<br>
						</td>
					</tr>
					<tr>	
						<td class="etichetta-dato">Utente/Password</td>
						<td class="valore-dato">
							<input type="text" name="simoguser" id="simoguser" size="15" value="${simoguser}" onchange="javascript:resetRecuperaUser();"/>
							<input type="password" name="simogpass" id="simogpass" size="15" value="................"  onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" onchange="javascript:resetRecuperaPassword();"/>
						</td>
					</tr>
					<input type="hidden" id="recuperauser" name="recuperauser" value="1" />
					<input type="hidden" id="recuperapassword" name="recuperapassword" value="1" />	
				</c:otherwise>
			</c:choose>

			<tr>
				<td class="etichetta-dato">Memorizza le credenziali</td>
				<td class="valore-dato">
				<c:choose>
					<c:when test="${userIsRup eq 'no' && existCredenzialiRup eq 'si'}">
						<input type="radio" id="memorizza" name="memorizza" value="memorizza" checked="checked"/>
					</c:when>
					<c:otherwise>
						<input type="checkbox" id="memorizza" name="memorizza" value="memorizza" checked="checked"/>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<c:if test="${userIsRup eq 'no' && existCredenzialiRup eq 'si'}">
				<tr>
					<td class="etichetta-dato">Utilizza credenziali RUP</td>
					<td class="valore-dato">
						<input type="radio" id="credenzialiRup" name="memorizza" value="credenzialiRup"/>					
					</td>
				</tr>
			</c:if>
			</table></td></tr>
			</c:if>
				
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
				
					<c:if test='${numeroErrori eq 0 && !empty invio}'>
						<c:choose>
							<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG") && programmi eq "0"}'>
								<INPUT type="button" class="bottone-azione" value="Conferma invio Simog" title="Conferma invio Simog" onclick="javascript:ShowCredenziali()">
							</c:when>
							<c:otherwise>
								<INPUT type="button" class="bottone-azione" value="Conferma invio" title="Conferma invio" onclick="javascript:confermaInvio()">
							</c:otherwise>
						</c:choose>
					</c:if>

					<c:if test='${numeroErrori ne 0}'>
						<INPUT type="button" class="bottone-azione" value="Controlla nuovamente" title="Controlla nuovamente" onclick="javascript:controlla()">
					</c:if>
					
					<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
				</td>
			</tr>
			
		</table>

	</gene:redefineInsert>
	<gene:javaScript>
	
		window.opener.currentPopUp = null;
	    window.onfocus = resettaCurrentPopup;
	
		function resettaCurrentPopup() {
			window.opener.currentPopUp=null;
		}
		
		function annulla() {
			window.close();
		}
	
		function controlla() {
			window.location.reload();
		}
		
		function ShowCredenziali() {
			if (document.getElementById('credenzialiSimog').style.visibility == "visible") {
				confermaInvio();
			} else {
				document.getElementById('credenzialiSimog').style.visibility = "visible";
			}
		}
		
		function confermaInvio(){
			var invia = "true";
			var usaCredenzialiRup = false;
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG") && programmi eq "0"}'>
				<c:if test="${userIsRup eq 'no' && existCredenzialiRup eq 'si'}">
					usaCredenzialiRup = document.getElementById('credenzialiRup').checked;
				</c:if>
				<c:if test="${empty simoguser}">
					if (!usaCredenzialiRup) 
					{
						var simoguser = document.getElementById('simoguser');
						if (simoguser.value == "") {
							alert("Inserire l'utente");
							invia = "false";
						}
					
						var simogpass = document.getElementById('simogpass');			
						if (invia == "true" && simogpass.value == "") {
							alert("Inserire la password");
							invia = "false";
						}
					}
				</c:if>
				if (invia == "true") {
					if (usaCredenzialiRup) 
					{
						opener.setValue("SIMOGUSER","${simoguserrup}");
						opener.setValue("SIMOGPASS","${simogpassrup}");
						opener.setValue("RECUPERAUSER","0");
						opener.setValue("RECUPERAPASS","0");
						opener.setValue("MEMORIZZA","credenzialiRup");
					}
					else 
					{
						opener.setValue("SIMOGUSER",document.getElementById('simoguser').value);
						opener.setValue("SIMOGPASS",document.getElementById('simogpass').value);
						opener.setValue("RECUPERAUSER",document.getElementById('recuperauser').value);
						opener.setValue("RECUPERAPASS",document.getElementById('recuperapassword').value);
						opener.setValue("MEMORIZZA",document.getElementById('memorizza').value);
					}
					
				}
			</c:if>
			if (invia == "true") {
				window.close();
				window.opener.schedaConferma();
			}	
		}
	
		function resetRecuperaUser() {
			document.getElementById('recuperauser').value = "0";
		}
	
		function resetRecuperaPassword() {
			document.getElementById('recuperapassword').value = "0";
		}
	
		function resetPassword() {
			document.getElementById('simogpass').value = "";
		}	
	</gene:javaScript>
</gene:template>

</div>
