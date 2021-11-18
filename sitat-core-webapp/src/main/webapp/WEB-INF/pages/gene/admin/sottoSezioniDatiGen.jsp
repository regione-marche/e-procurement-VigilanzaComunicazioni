<%
/*
 * Created on 15-12-2009
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
 
 // PAGINA CHE CONTIENE L'ISTANZA DELLA SOTTOPARTE DELLA PAGINA DI DETTAGLIO
 // DEI DATI GENERALI DI UN ACCOUNT CONTENENTE LA SEZIONE RELATIVA ALLE
 // SOTTOSEZIONI DELLA PAGINA STESSA.
 // QUESTA PAGINA E' STATA RIDEFINITA NEL PROGETTO SITAT109 PER UNA
 // PERSONALIZZAZIONE DEI DATI GENERALI DELL'ACCOUNT.
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>

<c:set var="listaOpzioniUtenteSys" value="${fn:join(accountForm.opzioniUtenteSys,'#')}#" />
<c:set var="account" value="${accountForm}" />
<c:set var="moduloAttivo" value="${sessionScope.moduloAttivo}" scope="request" />
<c:set var="moduloFabbisogniAttivo" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.pt.moduloFabbisogniAttivo")}' />
<c:set var="accessoProgrammazioneAttivo" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.AccessoProgrammazioneFunction",pageContext,accountForm.idAccount)}' />
	<tr>
		<td colspan="2">
			<b>Configurazione contratti</b>
		</td>
	</tr>
	<tr>
		<td class="etichetta-dato">Privilegi dell'utente sui contratti</td>
		<td class="valore-dato">
			<c:choose>
		<c:when test='${account.abilitazioneLavori eq "A"}'>
			Responsabile (Accesso completo su tutti i contratti)
			<c:if test='${fn:contains(listaOpzioniUtenteSys, "ou89#")}'>
 				<div class="info-wizard">ATTENZIONE: un utente "Amministratore di sistema" dovrebbe avere accesso solo alle funzionalit&agrave; amministrative. Privilegi superiori a quello "utente" implicano una non conformit&agrave; alle norme (GDPR, AGID, ...).</div>
 			</c:if>
		</c:when>
		<c:when test='${account.abilitazioneLavori eq "C"}'>
			Compilatore (Accesso su tutti i contratti senza facoltà di invio)
		</c:when>
		<c:when test='${account.abilitazioneLavori eq "U"}'>
			Utente (Accesso consentito solo ai propri contratti)
		</c:when>
		<c:otherwise>
		</c:otherwise>
 			</c:choose>
		</td>
	</tr>

	<c:if test="${moduloFabbisogniAttivo eq '1'}">
		<tr>
			<td colspan="2">
				<b>Configurazione di accesso alle richieste di acquisto/intervento</b>
			</td>
		</tr>
		<tr>
			<td class="etichetta-dato">Privilegi dell'utente</td>
			<td class="valore-dato">
				<c:choose>
					<c:when test='${account.abilitazioneGare eq "A"}'>
						Responsabile (accesso consentito a tutti i dati)
						<c:if test='${fn:contains(listaOpzioniUtenteSys, "ou89#")}'>
			 				<div class="info-wizard">ATTENZIONE: un utente "Amministratore di sistema" dovrebbe avere accesso solo alle funzionalit&agrave; amministrative. Privilegi superiori a quello "utente" implicano una non conformit&agrave; alle norme (GDPR, AGID, ...).</div>
			 			</c:if>
					</c:when>
					<c:when test='${account.abilitazioneGare eq "U"}'>
						Utente (accesso consentito solo ai dati assegnati)
					</c:when>
				</c:choose>
			</td>
		</tr>
	</c:if>
	
	<c:if test="${accessoProgrammazioneAttivo eq '1'}">
		<tr>
			<td colspan="2">
				<b>Configurazione di accesso alla programmazione</b>
			</td>
		</tr>
		<gene:sqlSelect nome="uffici" tipoOut="VectorString" >
			select DENOM from UFFICI where ID = ${account.ufficioAppartenenza}
		</gene:sqlSelect>
		<tr>
			<td class="etichetta-dato">Ufficio/Centro di costo</td>
			<td class="valore-dato">${uffici[0]} </td>
		</tr>
		
	</c:if>
<script type="text/javascript">
<!--

//document.getElementById("rowAbilitaFunzioniAvanzate").style.display = "none";
//document.getElementById("rowNascondiMenuStrumenti").style.display = "none";

-->
</script>
	