<%
/*
 * Created on: 29-dic-2017
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

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="contri" value='${fn:substringAfter(param.key, ":")}' />

<gene:template file="popup-template.jsp" gestisciProtezioni="true"
	schema="PIANI" idMaschera="popupEsitoRiportaOpera">

	<gene:setString name="titoloMaschera" value="Riporta opere incompiute" />
	<gene:redefineInsert name="corpo">

		<table class="scheda-dettaglio">
			<tr>
				<td>
					<c:if test='${esito eq ko }' >
						L'operazione di riporto delle opere incompiute del programma precedente &egrave; terminato con errori
						<br><br>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
					<center>
						<INPUT type="button"  class="bottone-azione" value='Chiudi' title='Chiudi' onclick="javascript:chiudi()">
					</center>
					&nbsp;
				</td>
			</tr>
		</table>

	</gene:redefineInsert>

	<gene:javaScript>
	
	<c:if test="${esito eq 'ok'}" >
		$(document).ready(function(){
	 		window.opener.historyVaiIndietroDi(0);
			window.close();
		});
	</c:if>
		
		function chiudi() {
			window.close();
		} 

	</gene:javaScript>

</gene:template>
