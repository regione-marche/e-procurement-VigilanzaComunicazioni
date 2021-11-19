
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
<c:set var="profiloUtente" value="${sessionScope.profiloUtente}" scope="request"/>

<div style="width:97%;">

<gene:template file="popup-template.jsp">

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="gestioneHistory" />
	
	<c:set var="fabbisogni" value="${param.fabbisogni}" />
	<c:set var="funzionalita" value="${param.funzionalita}" />
	
	
	<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
	<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
	<c:choose>
		<c:when test='${funzionalita eq "RDS_ContrassegnaCompletato"}'>
			<gene:setString name="titoloMaschera"  value='Contrassegna Proposte come completate' />
			<c:set var="descr1" value="Il controllo dei dati delle proposte non ha rilevato situazioni anomale:" />
			<c:set var="descr2" value="E' possibile contrassegnarle come completate" />	
			<c:set var="codFunzione" value="INOLTRO_RAF" />
		</c:when>
		<c:when test='${funzionalita eq "RDS_SottoponiAdApprFinanz"}'>
			<gene:setString name="titoloMaschera"  value='Inoltra Proposte' />
			<c:set var="descr1" value="Il controllo dei dati delle proposte non ha rilevato situazioni anomale:" />
			<c:set var="descr2" value="E' possibile pertanto inoltrarli" />			
			<c:set var="codFunzione" value="INOLTRO_RAF" />
		</c:when>
		<c:when test='${funzionalita eq "RDS_InoltraAlRdp"}'>
			<gene:setString name="titoloMaschera"  value='Inoltra proposte al RDP' />
			<c:set var="descr1" value="Il controllo dei dati delle proposte non ha rilevato situazioni anomale:" />
			<c:set var="descr2" value="E' possibile inoltrarle al RDP" />	
			<c:set var="codFunzione" value="INOLTRO_RDP" />			
		</c:when>
		<c:when test='${funzionalita eq "RDS_Annulla"}'>
			<gene:setString name="titoloMaschera"  value='Annulla proposte' />
			<c:set var="descr1" value="Confermi l'annullamento?" />
			<c:set var="descr2" value="" />			
		</c:when>
		<c:when test='${funzionalita eq "RDP_SegnalaProceduraAvviata"}'>
			<gene:setString name="titoloMaschera"  value='Contrassegna come avviato' />
			<c:set var="descr1" value="E' necessario inserire la data di avvio della procedura per poter proseguire. Confermare che la procedura di acquisto è stata avviata? L'operazione non potrà essere annullata" />	
			<c:set var="descr2" value="" />		
		</c:when>
		<c:otherwise>
<%-- 			<gene:setString name="titoloMaschera"  value='...' /> --%>
<%-- 			<c:set var="descr1" value="..." /> --%>
<%-- 			<c:set var="descr2" value="..." />			 --%>
		</c:otherwise>
	</c:choose>
	

	
	
	<gene:redefineInsert name="corpo">
	
	<form action="${contextPath}/piani/ContrassegnaFabbisogno.do" method="post" name="formContrassegnaFabbisogni" >
	
		<input type="hidden" name="fabbisogni" id="fabbisogni" value="${fabbisogni}" />
		<input type="hidden" name="funzionalita" id="funzionalita" value="${funzionalita}" />
		<table class="lista">
<%-- 			<gene:set name="numeroErrori" value="0" /> --%>
<%-- 			<gene:set name="numeroWarning" value="0" /> --%>			
			
<%-- 			Raccolta fabbisogni: aprile 2019. Controllo dei dati inseriti sulla proposta/fabbisogno(TAB_CONTROLLI). --%>
			<c:choose>
				<c:when test='${!(funzionalita eq "RDS_Annulla") or (funzionalita eq "RDP_SegnalaProceduraAvviata")}'>				
					<c:set var="stato" value='${gene:callFunction3("it.eldasoft.sil.pt.tags.funzioni.GestioneValidazioneFabbisogniFunction",pageContext, fabbisogni, codFunzione)}'/>	
					<gene:set name="titoloGenerico" value="${titolo}" />
					<gene:set name="listaGenericaControlli" value="${listaControlli}" />
					<gene:set name="numeroErrori" value="${numeroErrori}" />
					<gene:set name="numeroWarning" value="${numeroWarning}" />
				</c:when>
			</c:choose>	
			
			<c:choose>
				<c:when test="${(numeroErrori > 0) or (numeroWarning > 0)}">			
					<jsp:include page="../commons/popup-validazione-fabbisogni-interno-bean.jsp" />
					<tr>
						<td class="comandi-dettaglio" colSpan="2">					
						<c:if test='${numeroErrori eq 0}'>
							<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma" onclick="javascript:ContrassegnaFabbisogni()">&nbsp;				
						</c:if>
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla()">&nbsp;
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td>
							<b>${titoloGenerico}</b>
							<br>&nbsp;
							<b>${descr1}</b>
							<br>&nbsp;				
							<b>${descr2}</b>
							<br>&nbsp;  
											
						</td>
					</tr>
					<c:if test="${funzionalita eq 'RDP_SegnalaProceduraAvviata'}">
						<tr>
							<td>
							<table class="dettaglio-notab">
							<tr>
								<td class="valore-dato" colspan="2">Data avvio procedura (*)&nbsp;&nbsp;
								<input type="text" name="dataAvvioProcedura" id="dataAvvioProcedura" size="10" styleClass="testo" value="" /></td>
							</tr>
							</table>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="comandi-dettaglio" colSpan="2">
							<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma" onclick="javascript:ContrassegnaFabbisogni();">
							<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla()">&nbsp;
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			
			
		</table>
		</form>
	</gene:redefineInsert>
	
<gene:javaScript>

		$(function() {
			$( "#dataAvvioProcedura" ).datepicker($.datepicker.regional[ "it" ]);
		});
		
		function annulla(){
			window.close();
		}
		
		function ContrassegnaFabbisogni() {	
				<c:if test="${funzionalita eq 'RDP_SegnalaProceduraAvviata'}">
				if(document.getElementById("dataAvvioProcedura").value == '') {
					alert("Per poter proseguire e' obbligatorio inserire la data di avvio della procedura!");
					return false;
				}
				</c:if>
				document.formContrassegnaFabbisogni.submit();
				bloccaRichiesteServer();			
		}
		
		function controlla() {
			window.location.reload();
		}
	

</gene:javaScript>	
	
</gene:template>

</div>
