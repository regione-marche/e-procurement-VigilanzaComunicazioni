<%
  /*
   * Created on 03/08/2009
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

<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9XML-lista">
	<gene:setString name="titoloMaschera" value="Lista Feedback" />

	<gene:redefineInsert name="addToAzioni">
		<c:if test="${gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.INVIAINDICISIMOG') and (userIsRup eq 'si' || permessoUser ge 4 || sessionScope.profiloUtente.abilitazioneStd eq 'A')}">
			<tr>
				<td class="vocemenulaterale">
					<a href='javascript:riallineaIndiciSIMOG();' tabindex="1507" title="Riallinea indici SIMOG">Riallinea indici SIMOG</a>
				</td>
			</tr>
		</c:if>
	</gene:redefineInsert>

	<gene:redefineInsert name="corpo">
		<gene:redefineInsert name="pulsanteListaInserisci" />
		<gene:redefineInsert name="listaNuovo" />

		<table class="lista">
			<tr>
				<td>
					<gene:formLista entita="W9XML" where="" pagesize="20" tableclass="datilista" sortColumn="-11" gestisciProtezioni="true"
					gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9XML" >
						<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" 	width="50">
						<c:if test="${currentRow >= 0}">
							<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
								<c:if	test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9XML-scheda")}'>
									<gene:PopUpItemResource resource="popupmenu.tags.lista.visualizza" title="Visualizza dettaglio" />
								</c:if>
								
								<c:if test='${datiRiga.W9XML_NUM_ERRORE ne 0 and !empty datiRiga.W9XML_FASE_ESECUZIONE and !empty datiRiga.W9XML_NUM}'>
									<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9XML-lista")}'>
										<gene:PopUpItemResource resource="" title="Riprova invio" href="javascript:eseguiInvio('${datiRiga.W9XML_CODGARA}','${datiRiga.W9XML_CODLOTT}','${datiRiga.W9XML_NUMXML}','${datiRiga.W9XML_FASE_ESECUZIONE}','${datiRiga.W9XML_NUM}','${datiRiga.W9XML_IDFLUSSO}');" />
									</c:if>
								</c:if>
																
								<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") and not empty datiRiga.W9XML_NUM_ERRORE}'>
									<gene:PopUpItemResource	resource="popupmenu.tags.lista.elimina" title="Elimina" />
									<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
								</c:if>
								
								
							</gene:PopUp>
						</c:if>
						</gene:campoLista>
					<% 
					  // Campi veri e propri
					%>
						<gene:campoLista title="Esito" width="50">
							<center>
								<c:choose>
									<c:when test='${empty datiRiga.W9XML_NUM_ERRORE}'>
										&nbsp;
									</c:when>
									<c:when test='${datiRiga.W9XML_NUM_ERRORE eq 0}'>
										<img src="${pageContext.request.contextPath}/img/flag_verde.gif" height="16" width="16" title="Positivo">
									</c:when>
									<c:when test='${datiRiga.W9XML_NUM_ERRORE > 0 && datiRiga.W9XML_FEEDBACK_ANALISI eq "2"}'>
										<img src="${pageContext.request.contextPath}/img/flag_da_analizzare.gif" height="16" width="16" title="Da Analizzare">
									</c:when>
									<c:when test='${datiRiga.W9XML_NUM_ERRORE > 0 && datiRiga.W9XML_FEEDBACK_ANALISI eq "1"}'>
										<img src="${pageContext.request.contextPath}/img/flag_rosso.gif" height="16" width="16" title="Negativo">
									</c:when>
								</c:choose>
							</center>
						</gene:campoLista>
						<gene:campoLista campo="CODGARA" visibile="false"/>
						<gene:campoLista campo="CODLOTT" visibile="false"/>
						<gene:campoLista campo="NUMXML" visibile="false"/>
						<gene:campoLista campo="IDFLUSSO" visibile="false"/>
						
						<gene:campoLista campo="NUM_ERRORE" visibile="false"/>
						<gene:campoLista campo="FEEDBACK_ANALISI" visibile="false"/>
						<gene:campoLista campo="NOMEIN" title="Denominazione stazione appaltante" entita="UFFINT" from="W9GARA" where="W9XML.CODGARA=W9GARA.CODGARA AND W9GARA.CODEIN=UFFINT.CODEIN"	ordinabile="false" />
						<gene:campoLista campo="CIG" entita="W9LOTT" where="W9LOTT.CODGARA=W9XML.CODGARA AND W9LOTT.CODLOTT=W9XML.CODLOTT" ordinabile="false" />
						<gene:campoLista campo="DATA_FEEDBACK" headerClass="sortable" />
						<gene:campoLista campo="FASE_ESECUZIONE" headerClass="sortable" />
						<gene:campoLista campo="NUM" />
					</gene:formLista>
				</td>
			</tr>
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
	</gene:redefineInsert>
	<gene:javaScript>
		
		var newListaVisualizza = listaVisualizza;
		
		function listaVisualizza1() {
			document.forms[0].pgLastSort.value = "";
			document.forms[0].trovaAddWhere.value = "";
			document.forms[0].trovaParameter.value = "";
			newListaVisualizza();
		}
		
		listaVisualizza = listaVisualizza1;

		function eseguiInvio(codgara, codlott, numxml, fase_esecuzione, num, idflusso) {
			if (confirm("Riprovare ad inviare il flusso selezionato?")) {
				location.href = '${pageContext.request.contextPath}/w9/EseguiInvioSchedaSimog.do?' + csrfToken + '&codgara=' + codgara + '&codlott=' + codlott + '&numxml=' + numxml + '&fase_esecuzione=' + fase_esecuzione + '&num=' + num + '&idflusso=' + idflusso;
			}
		}
		
		function riallineaIndiciSIMOG() {
			var codGara = getValue("W9LOTT_CODGARA");
			var codiceCIG = getValue("W9LOTT_CIG");
			openPopUpCustom('href=w9/w9lott/riallineaIndiciSimogPopup.jsp&codGara='+codGara+'&codiceCIG='+codiceCIG, 'formRiallineaIndiciSIMOG', 545, 280, 1, 1);
		}
		
		<c:if test='${not empty RISULTATOREINVIO and RISULTATOREINVIO eq "OK"}' >
			window.alert('Invio effettuato correttamente');
			<c:remove var="RISULTATOREINVIO" />
		</c:if>
		<c:if test='${not empty RISULTATOREINVIO and RISULTATOREINVIO eq "KO"}' >
			window.alert("Errori durante l'esecuzione del nuovo invio");
			<c:remove var="RISULTATOREINVIO" />
		</c:if>
		
		
		
	</gene:javaScript>
</gene:template>