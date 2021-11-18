<%
	/*
   * Created on 29-Novembre-2018
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
	<gene:setString name="titoloMaschera"	value="Riallinea indici SIMOG" />
	<gene:redefineInsert name="corpo">
		<form action="${contextPath}/w9/AllineaIndici.do" name="formRiallineaIndiciSIMOG" method="post">
			<input type="hidden" name="metodo" value="riallineaIndiciSIMOG" />
			<input type="hidden" name="codiceGara" value="${param.codGara}" />

			<c:choose>
				<c:when test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "OK"}'>
					<br><br>
					Aggiornamento degli indici SIMOG relativi al CIG=${codiceCIG} avvenuto con successo.
					<br><br>
				</c:when>
				<c:when test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "KO"}' >
					<br><br>
					Aggiornamento degli indici SIMOG relativi al CIG=${codiceCIG} terminato con errore.
					<br><br>
				</c:when>
				<c:when test='${empty requestScope.RISULTATO}'>
					<br>
					Questa funzionalit&agrave; aggiorna i campi CODCUI, ID_SCHEDA_LOCALE e ID_SCHEDA_SIMOG del lotto e i campi ID_SCHEDA_LOCALE, ID_SCHEDA_SIMOG delle fasi del lotto, leggendoli da SIMOG.
					<br><br>
					<c:choose>
						<c:when test='${empty param.codiceCIG}'>
							Indicare il codice CIG: <input type="text" name="codiceCIG" id="codiceCIG" >
							<br><br>Inserire il codice CIG e premere 'Conferma' per continuare l'operazione.<br>
						</c:when>
						<c:otherwise>
							<input type="hidden" name="codiceCIG" value="${param.codiceCIG}" id="codiceCIG" />
							<br>Premere 'Conferma' per continuare l'operazione.
						</c:otherwise>
					</c:choose>
					<br><br>
					Premere 'Annulla' per interrompere l'operazione.
					<br><br>
				</c:when>
			</c:choose>
			
			<input type="hidden" name="existCredenziali" value="${existCredenziali}" />
			<c:if test="${existCredenziali eq 'no' and empty requestScope.RISULTATO and gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.INVIISIMOG')}">
			
			<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetSIMOGLAUserFunction" />
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
					<input type="checkbox" id="memorizza" name="memorizza" value="memorizza" checked="checked"/>										
				</td>
			</tr>	
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
	function annulla() {
		window.close();
	}

	function conferma() {
		var invia = document.getElementById("codiceCIG").value != "";
		if (! invia) {
		  alert("Inserire il codice CIG");
		}
	<c:if test="${empty simoguser and existCredenziali eq 'no' and gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.INVIISIMOG') }">
		var simoguser = document.getElementById('simoguser');
		if (invia && simoguser.value == "") {
			alert("Inserire l'utente");
			invia = false;
		}
		
		var simogpass = document.getElementById('simogpass');
		if (invia && simogpass.value == "") {
			alert("Inserire la password");
			invia = false;
		}
	</c:if>
		if (invia) {
			bloccaRichiesteServer();
			document.formRiallineaIndiciSIMOG.submit();
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
