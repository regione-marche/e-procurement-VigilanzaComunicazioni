<%
/*
 * Created on: 03/09/2012
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<gene:template file="scheda-template.jsp">
<c:if test="${!empty param.procedura}">
	<c:set var="procedura" value="${param.procedura }"/>
</c:if>
<c:if test="${!empty param.chiavi}">
	<c:set var="chiavi" value="${param.chiavi }"/>
</c:if>
<c:if test="${procedura eq 'avvisi'}">
	<gene:setString name="titoloMaschera" value='Trasferisci avvisi ad un altro referente' />
</c:if>
<c:if test="${procedura eq 'gare'}">
	<gene:setString name="titoloMaschera" value='Trasferisci appalti ad un altro referente' />
</c:if>
<c:if test="${procedura eq 'programmi'}">
	<gene:setString name="titoloMaschera" value='Trasferisci programmi ad un altro referente' />
</c:if>
<c:set var="temp" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.GetUtentiStazioneAppaltanteFunction",pageContext)}'/>
<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

<gene:redefineInsert name="addToAzioni" >
</gene:redefineInsert>

<gene:redefineInsert name="corpo">
	<form method="post" name="formCambioUtente" action="${pageContext.request.contextPath}/w9/TrasferisciAppalti.do" >
		<input type="hidden" id="procedura" name="procedura" value="${procedura}" />
		<input type="hidden" id="chiavi" name="chiavi" value="${chiavi}" />
		<table class="dettaglio-tab" >
			<tr><td colSpan="3" ><b>Seleziona uno dei referenti</b></td></tr>
			
			<c:forEach items="${utenti}" step="1"  var="utente">
				<fmt:parseNumber var="syscon" type="number" value="${utente[0]}" />
				<c:if test='${syscon eq sessionScope.profiloUtente.id}'>
					<tr class="datilista" ><td><input type="radio" checked="checked" name="groupUtenti" value="${utente[0]}" ></td><td class="datilista">${utente[1]}</td><td class="datilista">${utente[2]}</td></tr>
				</c:if>
				<c:if test='${syscon ne sessionScope.profiloUtente.id}'>
					<tr class="datilista" ><td><input type="radio" name="groupUtenti" value="${utente[0]}" ></td><td class="datilista">${utente[1]}</td><td class="datilista">${utente[2]}</td></tr>
				</c:if>
			</c:forEach>
		
			<tr class="comandi-dettaglio">
				<td class="comandi-dettaglio" colspan="3">
					<input type="button" value="Annulla" title="Annulla" class="bottone-azione" onclick="javascript:historyVaiIndietroDi(1);"/>&nbsp;&nbsp;
					<INPUT type="submit" class="bottone-azione" value="Conferma" title="Conferma" onclick="javascript:conferma()">
				</td>
			</tr>
		</table>
	</form>
</gene:redefineInsert>

<gene:javaScript>
	function conferma(){
		document.formCambioUtente.submit();
		bloccaRichiesteServer();
	}
</gene:javaScript>

</gene:template>

