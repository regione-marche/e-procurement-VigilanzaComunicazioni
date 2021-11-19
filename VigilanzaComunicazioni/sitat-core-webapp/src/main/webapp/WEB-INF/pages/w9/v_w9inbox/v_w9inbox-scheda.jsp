<%
	/*
	 * Created on: 04/08/2009
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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9INBOX-scheda">
	<gene:redefineInsert name="pulsanteNuovo" />
	<gene:redefineInsert name="schedaModifica" />
	<gene:redefineInsert name="pulsanteModifica" />
	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="V_W9INBOX" gestisciProtezioni="true"	plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9INBOX" >
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />
			<gene:gruppoCampi idProtezioni="GEN">
				<gene:campoScheda campo="IDCOMUN" />
				<gene:campoScheda campo="STACOM" />
				<gene:campoScheda campo="TINVIO2" />
				<gene:campoScheda campo="KEY03" />
				<gene:campoScheda campo="KEY04" visibile="${requestScope.area eq 1 or (requestScope.area eq 2 and ! empty datiRiga.V_W9INBOX_KEY04)}" />
				<gene:campoScheda campo="CFSA" />
				<gene:campoScheda campo="NOMEIN" title="Denominazione stazione appaltante" />
				<gene:campoScheda campo="AUTORE" />
		<c:choose>
			<c:when test='${empty requestScope.area}' >
				<gene:campoScheda campo="CODOGG" />
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test='${requestScope.area eq 1}'>
						<c:set var="titleCODOGG" value="Codice CIG"/>
					</c:when>
					<c:when test='${requestScope.area eq 2 or requestScope.area eq 5}'>
						<c:set var="titleCODOGG" value="Codice Gara ANAC"/>
					</c:when>
					<c:when test='${requestScope.area eq 3}'>
						<c:set var="titleCODOGG" value="ID AVVISO"/>
					</c:when>
					<c:when test='${requestScope.area eq 4}'>
						<c:set var="titleCODOGG" value="ID del programma"/>
					</c:when>
				</c:choose>
				<gene:campoScheda campo="CODOGG" title="${titleCODOGG}"/>
			</c:otherwise>
		</c:choose>

				<gene:campoScheda campo="DATRIC" />
				<gene:campoScheda campo="DATIMP"/>
				<gene:campoScheda campo="MSG" />
				<gene:campoScheda campo="IDEGOV" />
				<gene:campoScheda title="File XML" campoFittizio="true" visibile='${modoAperturaScheda eq "VISUALIZZA"}' modificabile="false">
					<a href='javascript:apriAllegato();'><img
						src="${pageContext.request.contextPath}/img/exportXML.gif" /> </a>
				</gene:campoScheda>
				<gene:campoScheda campo="VERXML" />
			</gene:gruppoCampi>
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
	</gene:redefineInsert>
	<gene:javaScript>
	
		function apriAllegato() {
			var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
			var par = new String('idcomun=' + getValue("V_W9INBOX_IDCOMUN") + '&tab=inbox');
			document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
		}
		
	
	</gene:javaScript>
</gene:template>