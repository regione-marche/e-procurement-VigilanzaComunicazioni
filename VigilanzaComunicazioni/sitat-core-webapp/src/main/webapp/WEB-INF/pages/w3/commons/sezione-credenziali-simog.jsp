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

<c:set var="simogwsuser" value='${gene:callFunction3("it.eldasoft.sil.w3.tags.funzioni.GetUsernameSimogFunction", pageContext, param.numgara, param.codrup)}' scope="request"/>
<c:choose>
	<c:when test="${empty simogwsuser}">
		<tr>
			<td class="valore-dato" colspan="2">
				<br>
				Indicare le <b>credenziali</b> per l'accesso al servizio.
				<br>
				<br>
			</td>
		</tr>
		<tr>	
			<td class="etichetta-dato">Utente/Password (*)</td>
			<td class="valore-dato">
				<c:if test="${empty codrup}">
					<gene:callFunction obj="it.eldasoft.sil.w3.tags.funzioni.GetListaRUPFunction" />
					<select name="listaRUP" id="listaRUP" onChange="javascript:cambiaRUP();">
						<option value=""></option>
						<c:if test='${!empty listaRUP}'>
							<c:forEach items="${listaRUP}" var="valoriRUP">
								<option value="${valoriRUP[0]}">${valoriRUP[1]}</option>
							</c:forEach>
						</c:if>
					</select>
					<input type="hidden" name="simogwsuser" id="simogwsuser" size="20" />
				</c:if>
				<c:if test="${!empty codrup}">
					<input type="text" name="simogwsuser" id="simogwsuser" size="20" />
				</c:if>
				<input type="password" name="simogwspass" id="simogwspass" value="................" onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" size="20"/>
			</td>
		</tr>
		<input type="hidden" name="codrup" value="${codrup}" />
		<input type="hidden" name="recuperauser" value="1" />
		<input type="hidden" name="recuperapassword" value="1" />					
	</c:when>
	<c:otherwise>
		<tr>
			<td class="valore-dato" colspan="2">
				<br>
				Le <b>credenziali</b> per l'accesso al servizio sono state recuperate dalla richiesta precedente.
				<br>
				<br>
			</td>
		</tr>
		<tr>	
			<td class="etichetta-dato">Utente/Password (*)</td>
			<td class="valore-dato">
				${simogwsuser}<input type="hidden" name="simogwsuser" id="simogwsuser" size="20" value="${simogwsuser}" />
				<input type="password" name="simogwspass" id="simogwspass" size="20" value="................"  onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" onchange="javascript:resetRecuperaPassword();"/>
			</td>
		</tr>
		<input type="hidden" name="codrup" value="${codrup}" />
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
				
<gene:javaScript>
	
	function resetRecuperaUser() {
		document.formIDGARACIG.recuperauser.value = "0";
	}

	function resetRecuperaPassword() {
		document.formIDGARACIG.recuperapassword.value = "0";
	}

	function resetPassword() {
		document.formIDGARACIG.simogwspass.value = "";
	}	

	function cambiaRUP() {
		var sel = document.getElementById("listaRUP");
   		var codRup = sel.value;
   		document.formIDGARACIG.codrup.value = codRup;
   		document.formIDGARACIG.simogwsuser.value = sel.options[sel.selectedIndex].text;
	}
</gene:javaScript>	