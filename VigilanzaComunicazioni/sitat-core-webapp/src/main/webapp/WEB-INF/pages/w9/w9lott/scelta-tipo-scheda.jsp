<%
/*
 * Created on: 16/04/2013
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
<gene:template file="scheda-template.jsp">
<gene:setString name="titoloMaschera" value='Popolamento massivo schede lotto' />

<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

<gene:redefineInsert name="addToAzioni" >
	<tr>
		<td class="vocemenulaterale" >
			<a href="javascript:Conferma();" title="Conferma" tabindex="1503">Conferma</a>
		</td>
	</tr>
</gene:redefineInsert>

<gene:redefineInsert name="corpo">
<form method="post" name="formSceltaTipoScheda" action="${pageContext.request.contextPath}/w9/SceltaTipoScheda.do">

	<table class="dettaglio-notab">
		<c:choose>
			<c:when test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "OK"}'>
				<tr>
					<td colspan="2">
					Aggiornamento dei dati dei lotti avvenuto con successo.
					</td>
				</tr>
			</c:when>
			<c:when test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "KO"}' >
				<tr>
					<td colspan="2" class="errore-generale">
					Aggiornamento dei dati dei lotti terminato con errore.
					</td>
				</tr>
			</c:when>
		</c:choose>
		
	    <tr>
	    	<td colspan="2">
	    	  <p>
	    	  	  <input type="hidden" name="key" value="${param.key}">
		    	  <br>Scegliere la tipologia di scheda che si desidera modificare per tutti i lotti.
				  <br>
				  <br>
				  <input id="tipoScheda1" type="radio" name="tipoScheda" value="anagrafica-lotto">Anagrafica lotto
				  <br><br>
				  <input id="tipoScheda2" type="radio" name="tipoScheda" value="comunicazione-esito">Comunicazione esito
				  <br><br>
				  <input id="tipoScheda3" type="radio" name="tipoScheda" value="aggiudicazione">Aggiudicazione
				  <br><br>
				  <input id="tipoScheda4" type="radio" name="tipoScheda" value="fase-iniziale">Fase iniziale
				  <br><br>
			  </p>
			</td>
		</tr>
		<tr class="comandi-dettaglio">
			<td class="comandi-dettaglio" colspan="2">
				<input type="button" value="Conferma" title="Conferma" class="bottone-azione" onclick="javascript:Conferma();"/>&nbsp;
			</td>
		</tr>
	</table>
</form>

</gene:redefineInsert>

<gene:javaScript>

	function Conferma(){
		for (i = 0; i < 4; i++) {
			if (document.getElementById("tipoScheda" + (i+1)).checked) {
				bloccaRichiesteServer();
				document.formSceltaTipoScheda.submit();
				return;
			}
		}
		alert("Per continuare devi scegliere un tipo di scheda da modificare!");
	}


</gene:javaScript>

</gene:template>

