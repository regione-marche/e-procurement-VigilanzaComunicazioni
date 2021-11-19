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
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>

<c:set var="moduloFabbisogniAttivo" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.pt.moduloFabbisogniAttivo")}' />

	<tr>
		<td colspan="2">
			<b>Configurazione contratti</b>
		</td>
	</tr>
	<tr>
		<td class="etichetta-dato">Privilegi dell'utente sui contratti</td>
		<td class="valore-dato">
			<c:choose>
		<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "A"}'>
			Responsabile (Accesso completo su tutti i contratti)
		</c:when>
		<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "C"}'>
			Compilatore (Accesso su tutti i contratti senza facoltà di invio)
		</c:when>
		<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "U"}'>
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
				<b>Configurazione di accesso alle richieste di acquisto/intervento </b>
			</td>
		</tr>
		<tr>
			<td class="etichetta-dato">Privilegi dell'utente</td>
			<td class="valore-dato">
				<c:choose>
					<c:when test='${sessionScope.profiloUtente.abilitazioneGare eq "A"}'>
						Responsabile (accesso consentito a tutti i dati)
					</c:when>
					<c:when test='${sessionScope.profiloUtente.abilitazioneGare eq "U"}'>
						Utente (accesso consentito solo ai dati assegnati)
					</c:when>
				</c:choose>
			</td>
		</tr>
	</c:if>		

<script type="text/javascript">

</script>
	