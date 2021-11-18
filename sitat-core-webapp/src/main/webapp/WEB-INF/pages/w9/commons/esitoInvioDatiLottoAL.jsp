<%
	/*
	 * Created on 02-Aprile-2018
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

<gene:template file="popup-message-template.jsp">
	<gene:redefineInsert name="addHistory" />
	
	<gene:setString name="titoloMaschera" value='Invio dati del lotto ad Appalti Liguria' />
	
	<gene:redefineInsert name="corpo">
			<c:choose>
				<c:when test="${esito eq 'OK'}">
					<br>&nbsp;
					<br>
					<b>Invio all'Osservatorio effettuato con successo</b>
					<br>&nbsp;<br>
				</c:when>
				<c:otherwise>
					<br>&nbsp;
					<br><b>Errore durante l'operazione di invio dei all'Osservatorio</b>
					<br>&nbsp;
					<br>
					<br><b>Messaggi di errore:</b>
					<br>
					<c:forEach items="${messaggiErrore}" var="msg" varStatus="status">
						<c:if test="${status.first}" >
							<ul>
							</c:if>
								<li>${msg}</li>
						<c:if test="${status.last}" >
							</ul>
						</c:if>
					</c:forEach>
					<br>
					<br>
				</c:otherwise>
			</c:choose>
			
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
			<c:when test="${esito eq 'OK'}">
				<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:ritornaListaLotti()">&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				<input type="button" value="Annulla" title="Annulla" class="bottone-azione" onclick="javascript:annulla();"/>&nbsp;&nbsp;
			</c:otherwise>
		</c:choose>
	</gene:redefineInsert>

	<gene:javaScript>

		function annulla() {
			window.close();
		}
	
		function ritornaListaLotti() {
			window.opener.historyVaiIndietroDi(0);
			window.close();
		}
	
	</gene:javaScript>
</gene:template>

</div>
