<%/*
       * Created on 18-ott-2007
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

<c:set var="modo" value='NUOVO' />

<c:set var="key" value='${key}' scope="request" />
<gene:redefineInsert name="addHistory"/>
<gene:redefineInsert name="schedaConferma"/>
<gene:redefineInsert name="schedaAnnulla"/>
<gene:redefineInsert name="addToAzioni">
	<tr>
		<td class="vocemenulaterale">
			<a href="javascript:scegliFase('${key}');" title="Conferma" tabindex="1501">Conferma</a>
		</td>
	</tr>
	<tr>
		<td class="vocemenulaterale">
			<a href="javascript:schedaAnnulla();" title="Annulla" tabindex="1502">Annulla</a>
		</td>
	</tr>
</gene:redefineInsert>

<gene:formScheda entita="W9FASI" gestisciProtezioni="true" >
</gene:formScheda>


<form id="form1" name="form1">
	<table class="dettaglio-tab" >
		<tr><td colSpan="2" ><b>Fasi attraverso le quali si sviluppa l'attivit&agrave; di rilevazione dei dati</b></td></tr>
		<c:forEach begin="0" end="${fn:length(datiTabellatoList)-1}" var="indiceTabCond">
			<c:if test='${!(datiTabellatoList[indiceTabCond][1] >= 994 || (datiTabellatoList[indiceTabCond][1]>=6 && datiTabellatoList[indiceTabCond][1] <=10))}'>
				<c:set var="dati" value='${datiTabellatoList[indiceTabCond]}' />
				<c:set var="idFase" value='${idTabellatoList[indiceTabCond]}' />
				<c:if test='${visibleTabellatoList[indiceTabCond] eq "1"}'>
					<c:choose>
						<c:when test='${enabledTabellatoList[indiceTabCond] eq "1"}'>
							<tr class="datilista" ><td><input type="radio" name="group1" value="${idFase[0]}" ></td><td class="datilista">${dati[0]}</td></tr>
						</c:when>
						<c:otherwise>
							<tr class="datilista" ><td><input type="radio" name="group1" value="${idFase[0]}" disabled="true" ></td><td class="datilista">${dati[0]}</td></tr>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
		</c:forEach>
		<c:set var="visualizzaTitoloEventi" value="true" />
		<c:forEach begin="0" end="${fn:length(datiTabellatoList)-1}" var="indiceTabCond100">
			<c:if test='${datiTabellatoList[indiceTabCond100][1] >= 994 || (datiTabellatoList[indiceTabCond100][1]>=6 && datiTabellatoList[indiceTabCond100][1]<=10)}'>
				<c:set var="dati100" value='${datiTabellatoList[indiceTabCond100]}' />
				<c:set var="idFase100" value='${idTabellatoList[indiceTabCond100]}' />
				<c:if test='${visibleTabellatoList[indiceTabCond100] eq "1"}'>
					<c:if test="${visualizzaTitoloEventi eq 'true'}" >
						<tr><td colSpan="2"><b>Eventi significativi</b></td></tr>
						<c:set var="visualizzaTitoloEventi" value="false" />
					</c:if>
					<c:choose>
						<c:when test='${enabledTabellatoList[indiceTabCond100] eq "1"}'>
							<tr class="datilista" ><td><input type="radio" name="group1" value="${idFase100[0]}" ></td><td class="datilista">${dati100[0]}</td></tr>
						</c:when>
						<c:otherwise>
							<tr class="datilista" ><td><input type="radio" name="group1" value="${idFase100[0]}" disabled="true" ></td><td class="datilista">${dati100[0]}</td></tr>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
		</c:forEach>			
		
		
		
		
		<tr>
			<% // Ridefinizione dei pulsanti specificati nel file /WEB-INF/pages/commons/pulsantiListaPage.jsp %>
			<td class="comandi-dettaglio" colSpan="2">
				<gene:insert name="pulsanteListaInserisci">
					<INPUT type="button" class="bottone-azione" title='${gene:resource("label.tags.template.lista.listaPageNuovo")}'
						value="Conferma" onclick="javascript:scegliFase('${key}');">
				</gene:insert>
				<gene:insert name="pulsanteListaEliminaSelezione">
					<INPUT type="button" class="bottone-azione" title='${gene:resource("label.tags.template.lista.listaEliminaSelezione")}'
						value="Annulla" onclick="javascript:schedaAnnulla();">
				</gene:insert>&nbsp;
			</td>
		</tr>
	</table>
</form>

	<gene:javaScript>
		function scegliFase (chiave) {
			var chosen = "";
			var len = 0;
			if(document.form1.group1.length) {
				len = document.form1.group1.length;
				for (i = 0; i < len; i++) {
					if (document.form1.group1[i].checked) {
						chosen = document.form1.group1[i].value;
					}
				}
			} else {
				len = 1;
				if (document.form1.group1.checked) {
						chosen = document.form1.group1.value;
				}
			}			

			if (chosen != '') {
				location.href = '${pageContext.request.contextPath}/w9/RedirectNuovaFase.do?' + csrfToken + '&chosen=' + chosen + '&keyatt=' + chiave;
			} else {
				alert("Per procedere e' necessario selezionare una fase");
			}
		}
	</gene:javaScript>
