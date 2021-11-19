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

<gene:template file="popup-message-template.jsp">

	<gene:setString name="titoloMaschera" value='Invio dati del lotto ad Appalti Liguria' />

	<gene:redefineInsert name="corpo">
		<!--table class="lista">
			<tr>
				<td-->
					<br><br>
					Si stanno per inviare i dati del lotto con CIG = ${codiceCIG} all'Osservatorio Regionale
					<br><br><br>
				<!--/td>
			</tr>
		</table-->
	
		<form name="formControlloDati" action="${pageContext.request.contextPath}/w9/InviaDatiLottoAL.do" method="post" >
			<input type="hidden" name="codiceGara" id="codiceGara" value="${param.codiceGara}" />
			<input type="hidden" name="codiceLotto" id="codiceLotto" value="${param.codiceLotto}" />
			<input type="hidden" name="metodo" id="metodo" value="controlloDati" />
		</form>
	
		<form name="formInvioDati" action="${pageContext.request.contextPath}/w9/InviaDatiLottoAL.do" method="post" >
			<input type="hidden" name="codiceGara" id="codiceGara" value="${param.codiceGara}" />
			<input type="hidden" name="codiceLotto" id="codiceLotto" value="${param.codiceLotto}" />
			<input type="hidden" name="metodo" id="metodo" value="eseguiInvioDati" />
		</form>
	</gene:redefineInsert>
	
	<gene:redefineInsert name="buttons">
		<c:choose>
			<c:when test="${esitoControllo eq 1}">
				<input id="pulsanteAvanti" type="button" value="Prosegui" title="Prosegui" class="bottone-azione" onclick="javascript:inviaDati();"/>&nbsp;
			</c:when>
			<c:when test="${esitoInvio}">
				<input id="pulsanteAvanti" type="button" value="Prosegui" title="Prosegui" class="bottone-azione" onclick="javascript:prosegui();"/>&nbsp;
			</c:when>
			<c:otherwise>
				<input id="pulsanteAvanti" type="button" value="Prosegui" title="Prosegui" class="bottone-azione" onclick="javascript:controllaDati();"/>&nbsp;
			</c:otherwise>
		</c:choose>
			<input id="pulsanteAnnulla" type="button" value="Annulla" title="Annulla" class="bottone-azione" onclick="javascript:annulla();"/>&nbsp;&nbsp;
			</gene:redefineInsert>

	<gene:javaScript>

		function controllaDati() {
			bloccaRichiesteServer();
			//document.getElementById("pulsanteAvanti").disabled = true;
			document.formControlloDati.submit();
		}
		
		function inviaDati() {
			bloccaRichiesteServer();
			//document.getElementById("pulsanteAvanti").disabled = true;
			document.formInvioDati.submit();
		}
		
		function concludi() {
			bloccaRichiesteServer();
			//document.getElementById("pulsanteAvanti").disabled = true;
			window.opener.historyReload();
			window.close();
		}
		
		function annulla() {
			window.close();
		}

	</gene:javaScript>
</gene:template>

