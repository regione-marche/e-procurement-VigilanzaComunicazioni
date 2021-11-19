
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



<div style="width:97%;">

<gene:template file="popup-template.jsp">

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="gestioneHistory" />
	
	<c:if test="${!empty param.idAvviso}">
		<gene:setString name="titoloMaschera" value='Pubblicazione avviso SCP' />
		<c:set var="params" value=";${param.flusso};${key1};${key2};${key3};${key4};${invio}" />
			
	</c:if>
	<c:if test="${!empty param.numeroPubblicazione}">
		<gene:setString name="titoloMaschera" value='Pubblicazione Atti Ex Art.29 SCP' />
		<c:set var="params" value=";${param.flusso};${key1};${key2};${key3};${key4};${invio}" />
			
	</c:if>
	<c:if test="${!empty param.contri}">
		<gene:setString name="titoloMaschera" value='Pubblicazione Programma' />
		<c:set var="params" value=";${param.flusso};${param.contri};;;;ok" />
	</c:if>
					
	<gene:redefineInsert name="corpo">
	<c:choose>
		<c:when test="${!empty validate}">
		<table class="lista">
			<tr>
				<td>
					<br>
					Non è stato possibile eseguire la pubblicazione:
					<br><br>
					<i>Il servizio di pubblicazione a seguito della validazione ha prodotto le seguenti segnalazioni</i>
					<br>
					<img width="20" height="20" title="Controllo bloccante: impedisce la validazione dei dati" alt="Controllo bloccante" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>	
					&nbsp;Controllo bloccante
					<br>
					<img width="20" height="20" title="Avviso non bloccante: &egrave; una segnalazione che l'utente pu&ograve; anche ignorare" alt="Controllo non bloccante" src="${pageContext.request.contextPath}/img/controllo_w.gif"/>	
					&nbsp;Avviso non bloccante
				</td>
			</tr>
		</table>
		<table id="listaerrori" class="datilista">
			<thead>
				<tr>
					<th width="22">
						&nbsp;
					</th>
					<th>
						Campo
					</th>
					<th>
						Messaggio di controllo
					</th>				
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${validate}" step="1" var="item" varStatus="status" >
					<c:if test="${status.index%2 == 0}">
						<tr class="even">
					</c:if>
					<c:if test="${status.index%2 == 1}">
						<tr class="odd">
					</c:if>
						<td width="22">
							<c:choose>
								<c:when test="${item.tipo == 'E'}">
									<img width="20" height="20" title="Controllo bloccante: impedisce la validazione dei dati" alt="Controllo bloccante" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>	
								</c:when>
								<c:when test="${item.tipo == 'W'}">
									<img width="20" height="20" title="Avviso non bloccante: &egrave; una segnalazione che l'utente pu&ograve; anche ignorare" alt="Controllo non bloccante" src="${pageContext.request.contextPath}/img/controllo_w.gif"/>	
								</c:when>
							</c:choose>
						</td>
						<td>
							${item.nome}											
						</td>
						<td>
							${item.messaggio}											
						</td>

					</tr>
				</c:forEach>
			</tbody>
			<tr>
				<td class="comandi-dettaglio" colspan="3" align="right">
				<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
				</td>
			</tr>
		</table>
		
		</c:when>
		<c:when test="${!empty error}">
			<table class="lista">
				
				<tr>
					<td>
						<br>
						Non è stato possibile eseguire la pubblicazione:
						<br><br>
						&nbsp;
						<img width="20" height="20"  alt="Controllo bloccante" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>
						<i>${error}</i>
						<br>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="comandi-dettaglio">
					<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
					</td>
				</tr>
			</table>
		</c:when>
		<c:when test="${!empty flusso}">
			<table class="lista">
				<tr>
					<td>
						<br>
						pubblicazione eseguita con successo
						<br><br>
						<br>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="comandi-dettaglio">
					<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
					</td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			
		<form method="post" name="formPubblicazione" action="${pageContext.request.contextPath}/w9/PubblicaServiziRest.do" >
		<input type="hidden" id="entita" name="entita" value="avvisi" />
		<input type="hidden" id="codein" name="codein" value="${param.codein}" />
		<input type="hidden" id="idAvviso" name="idAvviso" value="${param.idAvviso}" />
		<input type="hidden" id="codSistema" name="codSistema" value="${param.codSistema}" />
		<input type="hidden" id="codGara" name="codGara" value="${param.codGara}" />
		<input type="hidden" id="numeroPubblicazione" name="numeroPubblicazione" value="${param.numeroPubblicazione}" />
		<input type="hidden" id="contri" name="contri" value="${param.contri}" />
		
		
		<table class="lista">
			<gene:set name="numeroErrori" value="0" />
			
			<c:if test="${!empty param.contri}">
			<c:set var="stato" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GestioneValidazioneFunction",pageContext, params)}'/>		
			<gene:set name="titoloGenerico" value="${titolo}" />
			<gene:set name="listaGenericaControlli" value="${listaControlli}" />
			<gene:set name="numeroErrori" value="${numeroErrori}" />
			<gene:set name="numeroWarning" value="${numeroWarning}" />
		
			<c:choose>
				<c:when test="${!empty listaControlli and empty controlliSuBean}">
					<jsp:include page="../commons/popup-validazione-interno.jsp" />	
				</c:when>
				<c:when test="${!empty listaControlli and !empty controlliSuBean}">
					<jsp:include page="../commons/popup-validazione-interno-bean.jsp" />	
				</c:when>
				<c:otherwise>
					<tr>
						<td>
							<b>${titoloGenerico}</b>
							<br>&nbsp;<br>
							<b>Non &egrave; stato rilevato alcun problema.</b>
							<br>&nbsp;<br>				
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			</c:if>
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
					<c:if test='${numeroErrori eq 0}'>
						<INPUT type="submit" class="bottone-azione" value="Conferma pubblicazione" title="Conferma pubblicazione" onclick="javascript:confermaPubblicazione()">
					</c:if>
					<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
				</td>
			</tr>
		</table>
		</form>
		
		</c:otherwise>
	</c:choose>
	
	</gene:redefineInsert>
	<gene:javaScript>
	
	<c:if test="${!empty flusso}">
		window.opener.historyReload();
	</c:if>
	
		window.opener.currentPopUp = null;
	    window.onfocus = resettaCurrentPopup;
	
		function resettaCurrentPopup() {
			window.opener.currentPopUp=null;
		}
		
		function annulla() {
			window.close();
		}
		
		function confermaPubblicazione(){
			var invia = "true";
			<c:if test="${!empty param.numeroPubblicazione}">
				document.getElementById('entita').value="pubblicazioni";
			</c:if>
			<c:if test="${!empty param.contri}">
				document.getElementById('entita').value="programmi";
			</c:if>
			document.formPubblicazione.submit();
			bloccaRichiesteServer();
		}

	</gene:javaScript>
</gene:template>

</div>
