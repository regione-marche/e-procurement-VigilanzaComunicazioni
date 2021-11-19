<%
	/*
   * Created on 06-Giugno-2012
   *
   * Copyright (c) EldaSoft S.p.A.
   * Tutti i diritti sono riservati.
   *
   * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
   * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
   * aver prima formalizzato un accordo specifico con EldaSoft.
   */
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>
<c:set var="existCredenziali" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.ExistCredenzialiUserSimogFunction",pageContext)}' />
<gene:template file="popup-message-template.jsp">
	<gene:setString name="titoloMaschera"	value="Riallinea SIMOG" />
	<gene:redefineInsert name="corpo">
		<form action="${contextPath}/w9/ConsultaGara.do?metodo=riallineaDatiSIMOG" name="formRiallineaSIMOG" method="post">
			<input type="hidden" name="metodo" value="riallineaDatiSIMOG" />
			<input type="hidden" name="codiceGara" value="${param.codGara}" />
			<input type="hidden" name="codiceCIG" value="${param.codiceCIG}" />		

			<c:choose>
				<c:when test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "OK"}'>
					<br><br>
					Aggiornamento dei dati della gara e del lotto avvenuto con successo.
					<br><br>
				</c:when>
				<c:when test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "KO"}' >
					<br><br>
					Aggiornamento dei dati della gara e del lotto terminato con errore.
					<br><br>
				</c:when>
				<c:when test='${empty requestScope.RISULTATO}'>
					<br><br>
					Questa funzionalit&agrave; potr&agrave; cambiare i dati della gara e del lotto rileggendoli dal SIMOG.
					<br><br>Premere 'Continua' per continuare l'operazione.
					<br><br>
					Premere 'Annulla' per interrompere l'operazione.
					<br><br>
				</c:when>
			</c:choose>
			
			<input type="hidden" name="existCredenziali" value="${existCredenziali}" />
			<c:set var="userIsRup" value="si"/>
			<c:if test="${existCredenziali eq 'no' and empty requestScope.RISULTATO and gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.INVIISIMOG')}">
			
			<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetSIMOGLAUserFunction" />
			<c:set var="chiaveGara" value="W9GARA.CODGARA=N:${param.codGara}" />
			<c:set var="userIsRup" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsUserRupFunction",pageContext,chiaveGara,"W9GARA")}' />
			<c:if test="${userIsRup eq 'no' }">
				<c:set var="existCredenzialiRup" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.RecuperaCredenzialiRupFunction",pageContext,chiaveGara)}' />
			</c:if>
			<table class="lista">
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
							<input type="text" name="simoguser" id="simoguser" size="15"/>
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
			</table>
		</c:if>
		
		</form>

		<c:if test='${not empty requestScope.RISULTATO}'>
			<gene:redefineInsert name="buttons">
				<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla();">&nbsp;
			</gene:redefineInsert>
		</c:if>
  </gene:redefineInsert>
	<gene:javaScript>
	function annulla(){
	<c:if test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "OK"}'>
		window.opener.bloccaRichiesteServer();
		window.opener.selezionaPagina(0);
	</c:if>
		window.close();
	}

	function conferma() {
		var invia = true;
		var usaCredenzialiRup = false;
	<c:if test="${existCredenziali eq 'no' and gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.INVIISIMOG') }">
		<c:if test="${userIsRup eq 'no' && existCredenzialiRup eq 'si'}">
			usaCredenzialiRup = document.getElementById('credenzialiRup').checked;
		</c:if>
	<c:if test="${empty simoguser}">
		if (!usaCredenzialiRup) 
		{
			var simoguser = document.getElementById('simoguser');
			if (simoguser.value == "") {
				alert("Inserire l'utente");
				invia = false;
			}
			
			var simogpass = document.getElementById('simogpass');			
			if (invia && simogpass.value == "") {
				alert("Inserire la password");
				invia = false;
			}
		}
	</c:if>
	</c:if>
		if (invia) {
			if (usaCredenzialiRup) 
			{
				document.getElementById('simoguser').value="${simoguserrup}";
				document.getElementById('simogpass').value="${simogpassrup}";
				document.getElementById('recuperauser').value="0";
				document.getElementById('recuperapassword').value="0";
				document.getElementById('memorizza').value="credenzialiRup";
			}
			bloccaRichiesteServer();
			document.formRiallineaSIMOG.submit();
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
