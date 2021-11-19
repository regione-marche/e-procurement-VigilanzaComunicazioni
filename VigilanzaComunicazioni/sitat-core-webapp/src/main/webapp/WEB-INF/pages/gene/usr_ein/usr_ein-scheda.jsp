<%
/*
 * Created on: 6-Nov-2009
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
/* Scheda ufficio intestatario */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="GENE" idMaschera="USR_EIN-scheda">
	<% // Settaggio delle stringhe utilizzate nel template %>
	<gene:setString name="titoloMaschera" value='Dettaglio utente' />
	
<c:set var="isCodificaAutomatica" value='${gene:callFunction3("it.eldasoft.gene.tags.functions.IsCodificaAutomaticaFunction", pageContext, "TECNI", "CODTEC")}'/>

<c:set var="codein" value='${gene:getValCampo(param.keyParent,"CODEIN")}' scope="request"/>

	<gene:redefineInsert name="corpo">
	<gene:formScheda entita="USR_EIN" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreUSR_EIN">
		<jsp:include page="usr_ein-interno-scheda.jsp"/>
		<gene:campoScheda>
			<gene:redefineInsert name="schedaNuovo">
					<c:if test='${gene:checkProtFunz(pageContext,"INS","SCHEDANUOVO")}'>
					<tr>
						<td class="vocemenulaterale" >
							<c:if test='${isNavigazioneDisattiva ne "1"}'><a href="javascript:schedaNuovoWizard();" title="Inserisci" tabindex="1502"></c:if>
								${gene:resource("label.tags.template.lista.listaNuovo")}
							<c:if test='${isNavigazioneDisattiva ne "1"}'></a></c:if>
						</td>
					</tr>
					</c:if>
			</gene:redefineInsert>	
			<gene:redefineInsert name="pulsanteNuovo">
			<c:if test='${gene:checkProtFunz(pageContext,"INS","SCHEDANUOVO")}'>
				<INPUT type="button"  class="bottone-azione" value='${gene:resource("label.tags.template.lista.listaNuovo")}' title='${gene:resource("label.tags.template.lista.listaNuovo")}' onclick="javascript:schedaNuovoWizard()" id="btnNuovo">
			</c:if>
			</gene:redefineInsert>
			<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
		</gene:campoScheda>
	</gene:formScheda>
	<gene:javaScript>
	function schedaNuovoWizard() {
<c:choose>
	<c:when test='${fn:indexOf(key, "UFFINT.CODEIN") >= 0}'>
		<c:set scope="request" var="paramKey" value="${param.key}" />
	</c:when>
	<c:otherwise>
		<c:set scope="request" var="paramKey" value="${keyParent}" />
	</c:otherwise>
</c:choose>

		document.location.href="${contextPath}/ApriPagina.do?" + csrfToken + "&href=gene/usr_ein/usr_ein-wizard.jsp?keyParent=${paramKey}";
	}

	</gene:javaScript>
	</gene:redefineInsert>
</gene:template>