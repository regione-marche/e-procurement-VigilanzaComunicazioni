
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

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div style="width:97%;">

<gene:template file="popup-template.jsp">

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="gestioneHistory" />
	<gene:setString name="titoloMaschera" value='Richiesta di cancellazione dati' />
	
	<gene:callFunction obj="it.eldasoft.sil.w3.tags.funzioni.GetValoriTabellatiSIMOGFunction" />
	
	<gene:redefineInsert name="corpo">
		<form action="${contextPath}/w3/CancellaIDGARACIG.do" method="post" name="formIDGARACIG">
		
			<c:set var="entita" value="${param.entita}" scope="request"/>
			
			<input type="hidden" name="entita" value="${entita}" />
			<input type="hidden" name="numgara" value="${param.numgara}" />
			<input type="hidden" name="numlott" value="${param.numlott}" />
			
			<table class="dettaglio-notab">
				<jsp:include page="sezione-credenziali-simog.jsp"/>
				<c:if test="${entita ne 'W3SMARTCIG'}">
				<tr>
					<td class="valore-dato" colspan="2">
						<b><br>Informazioni sulla richiesta di cancellazione</b>
					</td>
				</tr>
				<tr>
					<td class="etichetta-dato">Motivo(*)</td>
					<td class="valore-dato">
						<select name="idmotivazione" id="idmotivazione" >
							<option value="">&nbsp;</option>
							<c:choose>
								<c:when test="${entita eq 'W3GARA'}">
									<c:if test='${!empty listaW3z14}'>
										<c:forEach items="${listaW3z14}" var="listaW3z14">
											<option value="${listaW3z14[0]}">${listaW3z14[1]}</option>
										</c:forEach>
									</c:if>
								</c:when>
								<c:when test="${entita eq 'W3LOTT'}">
									<c:if test='${!empty listaW3z13}'>
										<c:forEach items="${listaW3z13}" var="listaW3z13">
											<option value="${listaW3z13[0]}">${listaW3z13[1]}</option>
										</c:forEach>
									</c:if>
								</c:when>
							</c:choose>
						</select>
					</td>
				</tr>
				<tr>
					<td class="etichetta-dato">Note</td>
					<td class="valore-dato">
						<textarea name="notecanc" rows="4" cols="57"></textarea>
					</td>
				</tr>
				</c:if>
				<tr>
					<td colspan="2" class="comandi-dettaglio">
						<INPUT type="button" class="bottone-azione" value="Invia richiesta di cancellazione" title="Invia richiesta di cancellazione" onclick="javascript:cancellaIDGARACIG();">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>	
	</gene:redefineInsert>
	
	<gene:javaScript>
		document.forms[0].jspPathTo.value="w3/commons/popup-cancella-idgaracig.jsp";
		
		function annulla(){
			window.close();
		}
		
		function cancellaIDGARACIG(){

			var invia = "true";
		
			var simogwsuser = document.formIDGARACIG.simogwsuser;
			if (simogwsuser.value == "") {
				alert("Inserire l'utente");
				invia = "false";
			}
			
			var simogwspass = document.formIDGARACIG.simogwspass;			
			if (invia == "true" && simogwspass.value == "") {
				alert("Inserire la password");
				invia = "false";
			}
			<c:if test="${entita ne 'W3SMARTCIG'}">
				var idmotivazione = document.formIDGARACIG.idmotivazione;
				if (invia == "true" && idmotivazione.value == "") {
					alert("Indicare il motivo della cancellazione");
					invia = "false";
				}
			</c:if>
			
			if (invia == "true") {
				document.formIDGARACIG.submit();
				bloccaRichiesteServer();
			}				

		}
	
	</gene:javaScript>	
</gene:template>

</div>

