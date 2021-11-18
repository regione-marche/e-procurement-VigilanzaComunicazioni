<%
/*
 * Created on: 30/04/2013
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

<%/* Dati generali del lavoro */%>
<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="modifica-fase-iniziale">

	<gene:setString name="titoloMaschera" value='Modifica fase iniziale' />	
	
	<gene:redefineInsert name="corpo">
	
		<gene:formScheda entita="W9PUES" gestisciProtezioni="true"
			gestore="it.eldasoft.w9.tags.gestori.submit.GestoreModificheLottiGara">
			
			<c:set var="key" value='${key}' scope="request" />
			<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
			<c:if test='${(codgara eq "")||(empty codgara)}'>
				<c:set var="codgara" value='${gene:getValCampo(keyParent,"CODGARA")}' />
			</c:if>
			<input type="hidden" name="codgara" value="${codgara}" />
			<input type="hidden" name="tipoScheda" value="fase-iniziale" />
			
			<gene:campoScheda>
				<td colspan="2"><b>Pubblicazione esito di gara </b></td>
			</gene:campoScheda>
					
			<gene:campoScheda campo="DATA_GUCE"/>
			<gene:campoScheda campo="DATA_GURI"/>
			<gene:campoScheda campo="DATA_ALBO"/>
			<gene:campoScheda campo="QUOTIDIANI_NAZ"/>
			<gene:campoScheda campo="QUOTIDIANI_REG"/>
			<gene:campoScheda campo="PROFILO_COMMITTENTE"/>
			<gene:campoScheda campo="SITO_MINISTERO_INF_TRASP"/>
			<gene:campoScheda campo="SITO_OSSERVATORIO_CP"/>
			
			<gene:redefineInsert name="pulsanteSalva" >
				<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma " onclick="javascript:Conferma()">
			</gene:redefineInsert>
			<gene:redefineInsert name="schedaConferma">
				<tr>
					<td class="vocemenulaterale"><a href="javascript:Conferma();" title="Conferma" tabindex="1501">Conferma</a></td>
				</tr>
			</gene:redefineInsert>
		
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />
		
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
	</gene:redefineInsert>
	
	<gene:redefineInsert name="addToAzioni">
		<jsp:include page="../commons/addtodocumenti-validazione.jsp" />
	</gene:redefineInsert>
	
	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
	</gene:redefineInsert>
	
	<gene:javaScript>
		document.forms[0].jspPathTo.value="w9/w9lott/scelta-tipo-scheda.jsp?key=${key}";
		document.forms[0].jspPath.value="/WEB-INF/pages/w9/w9lott/scelta-tipo-scheda.jsp?key=${key}";
		
		function Conferma() {
			response = confirm("Questa operazione modificher&agrave; i dati nelle maschere dei lotti. Continuare?");
			if (response) {
				schedaConferma();
			}
			return;
		}
	</gene:javaScript>
</gene:template>
