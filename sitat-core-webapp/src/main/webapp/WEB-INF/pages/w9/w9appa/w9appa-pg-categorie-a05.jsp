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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%/* Dati generali del lavoro */%>
<gene:formScheda entita="W9APPA" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9APPA"
	where='W9APPA.CODGARA = #W9APPA.CODGARA# AND W9APPA.CODLOTT = #W9APPA.CODLOTT# AND W9APPA.NUM_APPA = #W9APPA.NUM_APPA#' >
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	<gene:campoScheda campo="CODLOTT" visibile="false" />
	<gene:campoScheda campo="CODGARA" visibile="false" />
	<gene:campoScheda campo="NUM_APPA" visibile="false" />
	
	<gene:campoScheda entita="W9REQU" campo="CODGARA" where="W9REQU.CODGARA = ${codgara} and W9REQU.CODLOTT = ${codlott}" visibile="false" defaultValue="${datiRiga.W9APPA_CODGARA}"/>
	<gene:campoScheda entita="W9REQU" campo="CODLOTT" where="W9REQU.CODGARA = ${codgara} and W9REQU.CODLOTT = ${codlott}" visibile="false" defaultValue="${datiRiga.W9APPA_CODLOTT}"/>
	<gene:campoScheda entita="W9REQU" campo="NUM_APPA" where="W9REQU.CODGARA = ${codgara} and W9REQU.CODLOTT = ${codlott}" visibile="false" defaultValue="${datiRiga.W9APPA_NUM_APPA}"/>
	
	
	<!-- scheda multipla -->
	<jsp:include page="categorie_qualificazione_soa.jsp">
		<jsp:param name="keyW9requ" value="${key}" />
	</jsp:include>
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
<gene:javaScript>
</gene:javaScript>