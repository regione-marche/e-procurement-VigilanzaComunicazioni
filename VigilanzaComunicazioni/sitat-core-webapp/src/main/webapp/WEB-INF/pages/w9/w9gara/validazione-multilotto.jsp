
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

<gene:template file="scheda-template.jsp">
	<gene:setString name="titoloMaschera" value='${titolo}' />
	<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>
	<gene:redefineInsert name="corpo">
	<form method="post" name="formMultilotto" action="${pageContext.request.contextPath}/w9/Multilotto.do" >
		<input type="hidden" name="metodo" value="avviaMonitoraggio" />
		<input type="hidden" name="codgara" value="${codgara}" />
		<input type="hidden" name="lottiDaInviare" value="${lottiDaInviare}" />
		<input type="hidden" name="numeroLottiInviati" value="${numeroLottiInviati}" />
		
		<table class="lista">

			<gene:set name="listaGenericaControlli" value="${listaControlli}" />
			<gene:set name="numeroErrori" value="${numeroErrori}" />
			<gene:set name="numeroWarning" value="${numeroWarning}" />
			<gene:set name="noValidazione" value="${noValidazione}" />
			<gene:set name="errore" value="${errore}" />
			
			<c:choose>
				<c:when test="${!empty errore}">
					<tr>
						<td>
							<b>Si sono verificati degli errori durante la procedura per il monitoraggio multilotto</b>
							<br><br>
							<br>
							<label style="color:red"><b>${errore}</b></label>
							<br>
						</td>
					</tr>
				</c:when>
				<c:when test="${!empty numeroLottiDaInviare}">
					<input type="hidden" id="recuperauser" name="recuperauser" value="${recuperauser}" />
					<input type="hidden" id="recuperapassword" name="recuperapassword" value="${recuperapassword}" />
					<input type="hidden" name="simoguser" id="simoguser" value="${simoguser}" />
					<input type="hidden" name="simogpass" id="simogpass" value="${simogpass}"/>
					<input type="hidden" id="memorizza" name="memorizza" value="${memorizza}" />
					<tr>
						<td colspan="2" class="valore-dato">
							<br>
							<c:if test="${numeroLottiInviati<numeroLottiDaInviare}">
								<b>Invio fasi di aggiudicazioni per monitoraggio multilotto</b>
								<br><br>
								<br><br>
								<div id="progressbar"></div>
								<br>
								<center>
									${numeroLottiInviati} / ${numeroLottiDaInviare}
								</center>
							</c:if>
							<c:if test="${numeroLottiInviati eq numeroLottiDaInviare}">
								<b>Invio flussi per monitoraggio multilotto terminato con successo</b>
								<br><br>
							</c:if>
							<br>
						</td>
					</tr>
				</c:when>
				<c:when test="${!empty listaControlli}">
					<tr>
						<td>
							<br>
							Durante il controllo dei dati sono stati rilevati alcuni errori che impediscono il monitoraggio multilotto:
							<br>
						</td>
					</tr>
					<tr>
						<td>
							<table id="listaerroriavvisi" class="datilista">
								<thead>
								<tr>
									<th width="22">&nbsp;</th>
									<th>Fase</th>
									<th>Descrizione errore e messaggio di controllo</th>				
								</tr>
								</thead>
								<tbody>
									<c:forEach items="${listaGenericaControlli}" step="1" var="controllo" varStatus="status" >
										<c:if test="${status.index%2 == 0}">
											<tr class="even">
										</c:if>
										<c:if test="${status.index%2 == 1}">
											<tr class="odd">
										</c:if>
											<td width="22">
												<img width="20" height="20" title="Controllo bloccante: impedisce la validazione dei dati" alt="Controllo bloccante" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>	
											</td>
								
											<td>
												${controllo[1]}											
											</td>
											<td colspan="${valueRow}">
												<b>${controllo[2]}</b>
												<br>${controllo[3]}
											</td>	
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td>
							<br>
							<b>Non &egrave; stato rilevato alcun problema. E' possibile procedere con l'invio per il monitoraggio.</b>
							<br><br>				
						</td>
					</tr>
					<c:set var="userIsRup" value="si"/>
					<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG")}'>
					<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetSIMOGLAUserFunction" />
					<c:set var="chiaveGara" value="W9GARA.CODGARA=N:${codgara}" />
					<c:set var="userIsRup" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsUserRupFunction",pageContext,chiaveGara,"W9GARA")}' />
					<c:if test="${userIsRup eq 'no' }">
						<c:set var="existCredenzialiRup" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.RecuperaCredenzialiRupFunction",pageContext,chiaveGara)}' />
					</c:if>
					<tr>
						<td>
							<table class="dettaglio-tab" id="credenzialiSimog" style="visibility:hidden;">
							<c:choose>
								<c:when test="${empty simoguser}">
									<tr>
										<td class="valore-dato" colspan="2">
											Indicare le <b>credenziali</b> per l'accesso al servizio di LoaderAppalto SIMOG.
											<br>
											<br>
										</td>
									</tr>
									<tr>	
										<td class="etichetta-dato">Utente/Password</td>
										<td class="valore-dato">
											<input type="text" name="simoguser" id="simoguser" size="15" />
											<input type="password" name="simogpass" id="simogpass" size="15" onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();"/>
										</td>
									</tr>
									<input type="hidden" id="recuperauser" name="recuperauser" value="0" />
									<input type="hidden" id="recuperapassword" name="recuperapassword" value="0" />										
								</c:when>
								<c:otherwise>
									<tr>
										<td class="valore-dato" colspan="2">
										Le <b>credenziali</b> per l'accesso al servizio sono state recuperate dalla precedente richiesta.
										<br>
										<br>
										</td>
									</tr>
									<tr>	
										<td class="etichetta-dato">Utente/Password</td>
										<td class="valore-dato">
											<input type="text" name="simoguser" id="simoguser" size="15" value="${simoguser}" onchange="javascript:resetRecuperaUser();"/>
											<input type="password" name="simogpass" id="simogpass" size="15" value="................"  onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" onchange="javascript:resetRecuperaPassword();"/>
										</td>
									</tr>
									<input type="hidden" id="recuperauser" name="recuperauser" value="1" />
									<input type="hidden" id="recuperapassword" name="recuperapassword" value="1" />	
								</c:otherwise>
							</c:choose>

							<tr>
								<td class="etichetta-dato">Memorizza le credenziali</td>
								<td class="valore-dato">
									<c:choose>
										<c:when test="${userIsRup eq 'no' && existCredenzialiRup eq 'si'}">
											<input type="radio" id="memorizza" name="memorizza" value="memorizza" checked="checked"/>
										</c:when>
										<c:otherwise>
											<input type="checkbox" id="memorizza" name="memorizza" value="memorizza" checked="checked"/>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<c:if test="${userIsRup eq 'no' && existCredenzialiRup eq 'si'}">
								<tr>
									<td class="etichetta-dato">Utilizza credenziali RUP</td>
									<td class="valore-dato">
										<input type="radio" id="credenzialiRup" name="memorizza" value="credenzialiRup"/>					
									</td>
								</tr>
							</c:if>
						</table>
					</td>
					</tr>
					</c:if>

				</c:otherwise>
			</c:choose>
			
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
					<c:if test='${numeroErrori eq 0}'>
						<c:choose>
							<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG")}'>
								<INPUT type="button" class="bottone-azione" value="Conferma invio Simog" title="Conferma invio Simog" onclick="javascript:ShowCredenziali()">
							</c:when>
							<c:otherwise>
								<INPUT type="button" class="bottone-azione" value="Conferma invio" title="Conferma invio" onclick="javascript:confermaInvio()">
							</c:otherwise>
						</c:choose>
					</c:if>
					<INPUT type="button" class="bottone-azione" value="Indietro" title="Indietro" onclick="javascript:annulla()">&nbsp;
				</td>
			</tr>
			
		</table>
	</form>
	</gene:redefineInsert>
	<gene:javaScript>
	
	function annulla() {
		historyVaiIndietroDi(1);
	}
	<c:if test="${empty errore && !empty numeroLottiDaInviare && numeroLottiInviati<numeroLottiDaInviare}">
		$(document).ready(function () {
			bloccaRichiesteServer();
			setTimeout("document.formMultilotto.submit();", 350);
		});
		
		$(function() {
		  $("#progressbar").progressbar({
		    value: ${100 * numeroLottiInviati / numeroLottiDaInviare} 
		  });
		});
	</c:if>

		function ShowCredenziali() {
			if (document.getElementById('credenzialiSimog').style.visibility == "visible") {
				confermaInvio();
			} else {
				document.getElementById('credenzialiSimog').style.visibility = "visible";
			}
		}
		
		function confermaInvio(){
			var invia = "true";
			var usaCredenzialiRup = false;
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG")}'>
				<c:if test="${userIsRup eq 'no' && existCredenzialiRup eq 'si'}">
					usaCredenzialiRup = document.getElementById('credenzialiRup').checked;
				</c:if>
				<c:if test="${empty simoguser}">
					if (!usaCredenzialiRup) 
					{
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
					}
				</c:if>
			</c:if>
			if (invia == "true") {
				if (usaCredenzialiRup) 
				{
					document.getElementById('simoguser').value="${simoguserrup}";
					document.getElementById('simogpass').value="${simogpassrup}";
					document.getElementById('recuperauser').value="0";
					document.getElementById('recuperapassword').value="0";
					document.getElementById('memorizza').value="credenzialiRup";
				}
				bloccaRichiesteServer();
				formMultilotto.submit();
			}	
		}
	
		function resetRecuperaUser() {
			document.getElementById('recuperauser').value = "0";
		}
	
		function resetRecuperaPassword() {
			document.getElementById('recuperapassword').value = "0";
		}
	
		function resetPassword() {
			document.getElementById('simogpass').value = "";
		}	
	</gene:javaScript>
</gene:template>

