<%
	/*
	 * Created on 04-Nov-2008
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */

	// Scheda degli intestatari della concessione stradale
%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" idMaschera="W9XML-Scheda" schema="W9">
	<gene:redefineInsert name="pulsanteNuovo" />
	
	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="W9XML" gestisciProtezioni="true" >
			<gene:setString name="titoloMaschera" value="Dettaglio Feedback" />
			
			<gene:campoScheda>
				<td colspan="2"><b>Esportazione dei dati in formato XML ed invio all'ANAC</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CODGARA" visibile="false"/>
			<gene:campoScheda campo="CODLOTT" visibile="false"/>
			<gene:campoScheda campo="NUMXML" modificabile="false"/>
			<gene:campoScheda campo="DATA_EXPORT" modificabile="false"/>
			<gene:campoScheda campo="DATA_INVIO" modificabile="false"/>
			
			<gene:campoScheda campo="CIG" entita="W9LOTT" where="W9LOTT.CODGARA=W9XML.CODGARA and W9LOTT.CODLOTT=W9XML.CODLOTT" modificabile="false" />
			
			<gene:campoScheda campo="CODEIN" entita="W9GARA" where="W9GARA.CODGARA=W9XML.CODGARA" visibile="false" />
			<gene:campoScheda campo="NOMEIN" entita="UFFINT" where="UFFINT.CODEIN=W9GARA.CODEIN and W9GARA.CODGARA=W9XML.CODGARA" from="W9GARA" modificabile="false" />
			
			<gene:campoScheda campo="FASE_ESECUZIONE" modificabile="false"/>
			<gene:campoScheda campo="NUM" modificabile="false"/>
			
			<gene:campoScheda campo="XML" visibile="false" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreLunghezzaCampo" />
			
			<gene:campoScheda title="File XML" campoFittizio="true" computed="true" value="" visibile='${modoAperturaScheda eq "VISUALIZZA"}' modificabile="false" >
				<c:choose>
					<c:when test='${datiRiga.W9XML_XML eq "1"}' >
						<a href='javascript:apriAllegato();'><img src="${pageContext.request.contextPath}/img/exportXML.gif" /></a>
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
			</gene:campoScheda>
			<gene:campoScheda campo="NOTE" />
			
			<gene:campoScheda>
				<td colspan="2"><b>Elaborazione eseguita dall'ANAC</b></td>
			</gene:campoScheda>
			
			<gene:campoScheda title="Esito elaborazione" modificabile="false">
							<left>
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
							</left>
			</gene:campoScheda>
			
			<gene:campoScheda campo="DATA_ELABORAZIONE" modificabile="false"/>
			<gene:campoScheda campo="NUM_ERRORE" modificabile="false"/>
			<gene:campoScheda campo="NUM_WARNING" modificabile="false"/>
			<gene:campoScheda campo="DATA_FEEDBACK" modificabile="false"/>
			<gene:campoScheda campo="FEEDBACK_ANALISI"/>
		</gene:formScheda>
		
	<c:set var="codgara" value="${datiRiga.W9XML_CODGARA}" />
	<c:set var="codlott" value="${datiRiga.W9XML_CODLOTT}" />
	<c:set var="numxml" value="${datiRiga.W9XML_NUMXML}" />
		
		<table class="lista" id="listaAnom">
			<tr>
				<td>
					<br>&nbsp;<br>
					<b>Lista degli errori ed avvisi</b>
				</td>
			</tr>
			<tr>
				<td>
					<gene:formLista entita="W9XMLANOM" 
						where="W9XMLANOM.CODGARA = ${codgara} AND W9XMLANOM.CODLOTT = ${codlott} AND W9XMLANOM.NUMXML = ${numxml}"
						tableclass="datilista" 
						sortColumn="-1">
						
										
						<gene:campoLista campo="CODGARA" visibile="false" ordinabile="false"/>
						<gene:campoLista campo="CODLOTT" visibile="false" ordinabile="false"/> 
						<gene:campoLista campo="NUMXML" visibile="false" ordinabile="false"/> 
						<gene:campoLista campo="NUM" visibile="false" ordinabile="false"/>
						<gene:campoLista campo="LIVELLO" visibile="false"/>
						<gene:campoLista title="" width="22">
							<c:choose>
								<c:when test="${datiRiga.W9XMLANOM_LIVELLO == 'ERRORE'}">
									<img width="20" height="20" title="Errore" alt="Errore" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>	
								</c:when>
								<c:when test="${datiRiga.W9XMLANOM_LIVELLO == 'ATTENZIONE'}">
									<img width="20" height="20" title="Attenzione" alt="Attenzione" src="${pageContext.request.contextPath}/img/controllo_w.gif"/>	
								</c:when>
								<c:when test="${datiRiga.W9XMLANOM_LIVELLO == 'AVVISO'}">
									<img width="20" height="20" title="Avviso" alt="Avviso" src="${pageContext.request.contextPath}/img/controllo_i.gif"/>	
								</c:when>
							</c:choose>
						</gene:campoLista>
						<gene:campoLista campo="CODICE" />
						<gene:campoLista campo="DESCRIZIONE" />
						<gene:campoLista campo="ELEMENTO" visibile="false"/>
						<gene:campoLista campo="SCHEDA" />
					</gene:formLista>
				</td>
			</tr>
			<tr>
				<td>
					<!-- Ho necessita' di reinsrire un gene form scheda perche' il form lista sovrascrive i menu' della pagina -->
					<gene:formScheda entita="W9XML" gestisciProtezioni="true" >
						<gene:setString name="titoloMaschera" value="Dettaglio Feedback" />
						<gene:campoScheda campo="CODGARA" visibile="false"/>
						<gene:campoScheda campo="CODLOTT" visibile="false"/>
						<gene:campoScheda campo="NUMXML" visibile="false"/>
						<gene:campoScheda campo="DATA_EXPORT" visibile="false"/>
						<gene:campoScheda campo="DATA_INVIO" visibile="false"/>
					</gene:formScheda>
				</td>
			</tr>
			
			<tr>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</tr>
		</table>
		
	</gene:redefineInsert>
	
	<gene:javaScript>

		var zeroAnom = function(){
			var numRigheLight = $('.even').length;
			var numRigheDark = $('.odd').length;
			var numRighe = numRigheDark + numRigheLight;
			if(numRighe == 0){
				showObj('listaAnom',false);
			}	
		}
		window.onload = zeroAnom;

		function apriAllegato() {
			var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
			var par = new String('tab=w9xml&codgara=${codgara}&codlott=${codlott}&numxml=${numxml}');
			document.location.href = actionAllegato + "?" + csrfToken + "&" + par;
		}

		function listaVaiAPagina(numpg){
			document.forms[1].metodo.value="leggi";
			document.forms[1].pgVaiA.value=numpg;
			document.forms[1].key.value=document.forms[1].keyParent.value;
			document.forms[1].submit();
		}

	</gene:javaScript>

</gene:template>
