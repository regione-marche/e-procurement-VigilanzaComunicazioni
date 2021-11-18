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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="modifica-fase-aggiudicazione">
	
	<gene:redefineInsert name="corpo">
	<gene:setString name="titoloMaschera" value='Modifica Fase Aggiudicazione' />
	<gene:formScheda entita="W9APPA" gestisciProtezioni="true"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreModificheLottiGara">
		<c:set var="key" value='${key}' scope="request" />
		<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
		<c:if test='${(codgara eq "")||(empty codgara)}'>
			<c:set var="codgara" value='${gene:getValCampo(keyParent,"CODGARA")}' />
		</c:if>
		<input type="hidden" name="codgara" value="${codgara}" />
		<input type="hidden" name="tipoScheda" value="aggiudicazione" />
			
		<gene:campoScheda>
			<td colspan="2"><b>Inviti ed offerte / soglia di anomalia </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="DATA_SCADENZA_PRES_OFFERTA" />
		
		<gene:campoScheda>
			<td colspan="2"><b>Dati procedurali dell'appalto </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="PROCEDURA_ACC" />
		<gene:campoScheda campo="PREINFORMAZIONE" />
		<gene:campoScheda campo="TERMINE_RIDOTTO" />
	
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



