<%
/*
 * Created on 16-ott-2020
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>
<%@ page language="java" import="it.eldasoft.utils.properties.ConfigManager" %>

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<form method="post" name="formConsultaGara" action="${pageContext.request.contextPath}/w9/ConsultaGara.do" >
	<table class="dettaglio-notab">

		<c:if test='${not empty requestScope.errorMsgDettaglio}' >
			<tr id="erroreConsultaGara" >
				<td colspan="2" class="valore-dato">
					<br>
					<a href="javascript:visualizzaErroriPerDebug();" alt="Dettaglio dell'errore per assistenza tecnica">Dettaglio dell'errore per assistenza tecnica</a><br>
					<div id="erroriPerDebug" style="display: none" >
						<br>
							<c:out value="${requestScope.errorMsgDettaglio}" escapeXml="false" />
						<br>
					</div>
					<br>
				</td>
			</tr>
			<c:remove var="sessionConsultaGaraLottiBean" scope="session" />
		</c:if>

	<c:choose>
		<c:when test='${! empty sessionScope.presaInCaricoGaraDelegataBean}'>
				<tr>
					<td class="valore-dato">
						<br>
						&nbsp;&nbsp;Controlli preliminari superati
						<br><br>
					</td>
					<td class="valore-dato" style="width:100px">
						<br>
						&nbsp;&nbsp;&nbsp;<img alt="Controlli preliminari" src="${pageContext.request.contextPath}/img/flag_verde.gif" width="20px" >
						<br><br>
					</td>
				</tr>

			<c:if test='${! empty sessionScope.presaInCaricoGaraDelegataBean.cfRup}'>
				<tr>
					<td class="valore-dato">
						<br>
						&nbsp;&nbsp;Rup: ${sessionScope.presaInCaricoGaraDelegataBean.denominazioneRup} (${sessionScope.presaInCaricoGaraDelegataBean.cfRup})
						<br><br>
					</td>
					<td class="valore-dato" style="width:100px">
						<br>
						&nbsp;&nbsp;&nbsp;<img alt="Rup" src="${pageContext.request.contextPath}/img/flag_verde.gif" width="20px" >
						<br><br>
					</td>
				</tr>
			</c:if>

			<c:if test='${! empty sessionScope.presaInCaricoGaraDelegataBean.collaborazione}'>
				<tr>
					<td class="valore-dato">
						<br>
						&nbsp;&nbsp;Centro di costo: ${sessionScope.presaInCaricoGaraDelegataBean.collaborazione.ufficioDenominazione}
						<br><br>
					</td>
					<td class="valore-dato" style="width:100px">
						<br>
						&nbsp;&nbsp;&nbsp;<img alt="Rup" src="${pageContext.request.contextPath}/img/flag_verde.gif" width="20px" >
						<br><br>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="valore-dato">
						<br>
						&nbsp;&nbsp;Si sta per prendere in carico la gara. Premere 'Continua' per proseguire.
						<br><br>
					</td>
				</tr>
				<html:hidden property="metodo" value="eseguiPresaInCaricoGaraDelegata" />
				
				<tr>
		      <td class="comandi-dettaglio" colSpan="2">
		    	<c:if test='${empty requestScope.bloccaOperazione}' >
		      	<INPUT type="button" class="bottone-azione" value="Continua" title="Continua" onclick="javascript:confermaInvio();" >
		        &nbsp;
		      </c:if>
		      	<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla();" >
		        &nbsp;
		      </td>
		    </tr>
			</c:if>

			<c:if test='${empty sessionScope.presaInCaricoGaraDelegataBean.cfRup and ! empty requestScope.listaTecnici}'>
				<tr>
					<td colspan="2" class="valore-dato">
						<br>
						Selezionare il RUP: 
						<select name="codiceTecnico" id="codiceTecnico" >
							<option value=""></option>
							<c:forEach items="${requestScope.listaTecnici}" var="tecnico" >
								<option value="${tecnico[0]}">${tecnico[2]} (CF: ${tecnico[1]})</option>
							</c:forEach>
						</select>
						<br><br>
					</td>
				</tr>
				<tr>
		      <td class="comandi-dettaglio" colSpan="2">
		    	<c:if test='${empty requestScope.bloccaOperazione}' >
		      	<INPUT type="button" class="bottone-azione" value="Continua" title="Continua" onclick="javascript:confermaInvio();" >
		        &nbsp;
		      </c:if>
		      	<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla();" >
		        &nbsp;
		      </td>
		    </tr>
			</c:if>
	
			<c:if test='${empty sessionScope.presaInCaricoGaraDelegataBean.collaborazione and ! empty requestScope.listaCodiciDenominazione}'>
				<tr>
					<td colspan="2" class="valore-dato">
						<br>
						Selezionare il centro di costo:
						<select name="centroCosto" id="centroCosto" >
							<option value=""></option>
							<c:forEach items="${requestScope.listaCodiciDenominazione}" var="centroCosto" >
								<option value="${centroCosto[0]}">${centroCosto[1]}</option>
							</c:forEach>
						</select>
						<br><br>
					</td>
				</tr>
				<tr>
		      <td class="comandi-dettaglio" colSpan="2">
		    	<c:if test='${empty requestScope.bloccaOperazione}' >
		      	<INPUT type="button" class="bottone-azione" value="Continua" title="Continua" onclick="javascript:confermaInvio();" >
		        &nbsp;
		      </c:if>
		      	<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla();" >
		        &nbsp;
		      </td>
		    </tr>
			</c:if>

			<c:choose>
				<c:when test='${empty sessionScope.presaInCaricoGaraDelegataBean.cfRup}'>
					<html:hidden property="metodo" value="setTecnicoGaraDelegata" />
				</c:when>
				<c:when test='${! empty requestScope.passaAlCentroDiCosto}'>
					<html:hidden property="metodo" value="setTecnicoGaraDelegata" />
				</c:when>
				<c:when test='${empty sessionScope.presaInCaricoGaraDelegataBean.collaborazione}'>
					<html:hidden property="metodo" value="setCentroCostoGaraDelegata" />
				</c:when>
			</c:choose>
			
		</c:when>
		<c:otherwise>

	    <tr>
	      <td class="etichetta-dato">CIG / Id gara</td>
	      <td class="valore-dato">
	      	<input type="text" id="codiceGara" name="codiceGara" onchange="validaIDGara()" styleClass="testo" size="12" maxlength="10" value="${requestScope.valoreCodiceGara}"/>
				</td>
	    </tr>
	    <input type="hidden" name="existCredenziali" value="${existCredenziali}" />
		<c:if test="${existCredenziali eq 'no' and gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.INVIISIMOG')}">
			
			<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetSIMOGLAUserFunction" />
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
							<input type="text" name="simoguser" id="simoguser" size="15" value="${requestScope.valoreSimogUser}"/>
							<input type="password" name="simogpass" id="simogpass" size="15" value="${requestScope.valoreSimogPass}" onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();"/>
						</td>
					</tr>
					<c:if test="${empty requestScope.valoreSimogUser}">
						<input type="hidden" id="recuperauser" name="recuperauser" value="0" />
						<input type="hidden" id="recuperapassword" name="recuperapassword" value="0" />
					</c:if>
					<c:if test="${! empty requestScope.valoreSimogUser}">
						<input type="hidden" id="recuperauser" name="recuperauser" value="${requestScope.valoreRecuperaUser}" />
						<input type="hidden" id="recuperapassword" name="recuperapassword" value="${requestScope.valoreRecuperaPassword}" />
					</c:if>
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
							<c:if test="${empty requestScope.valoreSimogUser}">
								<input type="text" name="simoguser" id="simoguser" size="15" value="${simoguser}" onchange="javascript:resetRecuperaUser();"/>
								<input type="password" name="simogpass" id="simogpass" size="15" value="................"  onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" onchange="javascript:resetRecuperaPassword();"/>
							</c:if>
							<c:if test="${! empty requestScope.valoreSimogUser}">
								<input type="text" name="simoguser" id="simoguser" size="15" value="${requestScope.valoreSimogUser}" onchange="javascript:resetRecuperaUser();"/>
								<input type="password" name="simogpass" id="simogpass" size="15" value="${requestScope.valoreSimogPass}"  onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" onchange="javascript:resetRecuperaPassword();"/>
							</c:if>
						</td>
					</tr>
					<c:if test="${empty requestScope.valoreSimogUser}">
						<input type="hidden" id="recuperauser" name="recuperauser" value="1" />
						<input type="hidden" id="recuperapassword" name="recuperapassword" value="1" />
					</c:if>
					<c:if test="${! empty requestScope.valoreSimogUser}">
						<input type="hidden" id="recuperauser" name="recuperauser" value="${requestScope.valoreRecuperaUser}" />
						<input type="hidden" id="recuperapassword" name="recuperapassword" value="${requestScope.valoreRecuperaPassword}" />
					</c:if>	
				</c:otherwise>
			</c:choose>

			<tr>
				<td class="etichetta-dato">Memorizza le credenziali</td>
				<td class="valore-dato">
					<c:if test="${empty requestScope.valoreSimogUser}">
						<input type="checkbox" id="memorizza" name="memorizza" value="memorizza" checked="checked"/>
					</c:if>
					<c:if test="${! empty requestScope.valoreSimogUser and requestScope.valoreMemorizza eq 'memorizza'}">
						<input type="checkbox" id="memorizza" name="memorizza" value="memorizza" checked="checked"/>
					</c:if>
					<c:if test="${! empty requestScope.valoreSimogUser and requestScope.valoreMemorizza ne 'memorizza'}">
						<input type="checkbox" id="memorizza" name="memorizza" value="memorizza"/>
					</c:if>
										
				</td>
			</tr>	
		</c:if>
	    <tr>
	      <td class="comandi-dettaglio" colSpan="2">
	    <c:choose>
	      <c:when test='${! empty newMetodo}'> 
	      	<html:hidden property="metodo" value="${newMetodo}" />
	      </c:when>
	      <c:otherwise>
	      	<html:hidden property="metodo" value="verificaDati" />
	      </c:otherwise>
	    </c:choose>
	    	<c:if test='${empty requestScope.bloccaOperazione}' >
	      	<INPUT type="button" class="bottone-azione" value="Continua" title="Continua" onclick="javascript:confermaInvio();" >
	        &nbsp;
	      </c:if>
	      	<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla();" >
	        &nbsp;
	      </td>
	    </tr>

		</c:otherwise>
	</c:choose>

	</table>
</form>

<script type="text/javascript">
	function resetRecuperaUser() {
		document.getElementById('recuperauser').value = "0";
	}
	
	function resetRecuperaPassword() {
		document.getElementById('recuperapassword').value = "0";
	}
	
	function resetPassword() {
		document.getElementById('simogpass').value = "";
	}
	
	function confermaInvio() {
<c:choose>
	<c:when test='${! empty sessionScope.sessionPresaInCaricoBean}'>
		document.formConsultaGara.metodo.value="prendiInCaricoGara";
		document.formConsultaGara.submit();
	</c:when>
	<c:otherwise>
		var invia = "true";
		<c:if test="${empty simoguser and existCredenziali eq 'no' and gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.INVIISIMOG')}">
			var simoguser = document.getElementById('simoguser');
			if (simoguser.value == "") {
				alert("Inserire l'utente");
				invia = "false";
			}
				
			var simogpass = document.getElementById('simogpass');			
			if (invia == "true" && simogpass.value == "") {
				alert("Inserire la password");
				invia = "false";
			}
		</c:if>
		if (invia == "true") {
			gestisciSubmit();
		}
	</c:otherwise>
</c:choose>
	}
	
<c:choose>
	<c:when test='${! empty sessionScope.sessionConsultaGaraLottiBean}'>

		$(document).ready(function () {
				bloccaRichiesteServer();
				setTimeout("document.formConsultaGara.submit();", 350);
		});
	
		$(function() {
		  $("#progressbar").progressbar({
		    value: ${100 *(sessionScope.sessionConsultaGaraLottiBean.numeroLottiImportati + sessionScope.sessionConsultaGaraLottiBean.numeroLottiEsistenti + sessionScope.sessionConsultaGaraLottiBean.numeroLottiNonImportati) / sessionScope.sessionConsultaGaraLottiBean.numeroTotaleLotti} 
		  });
		});

	</c:when>
	<c:when test='${! empty sessionScope.sessionPresaInCaricoBean}'>
	</c:when>
	<c:otherwise>
		$(document).ready(function () {
			var numeroLottiCaricati = "${valoreNumeroLottiCaricati}";
			var stringaMessaggio = "Il codice gara indicato e' gia' presente in archivio \r\n e contiene ${valoreNumeroLottiCaricati} CIG correttamente caricati. \r\n Proseguire con l'operazione?"
			if (numeroLottiCaricati != "" && numeroLottiCaricati > 0) {
				var r = confirm(stringaMessaggio);
				if (r != true) {
					document.location.href="${contextPath}/w9/ConsultaGara.do?" + csrfToken + "&metodo=inizializza";
				}
			}
		});
		
	</c:otherwise>
</c:choose>
</script>