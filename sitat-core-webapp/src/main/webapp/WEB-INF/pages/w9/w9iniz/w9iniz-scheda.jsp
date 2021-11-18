<%
	/*
	 * Created on 25-set-2009
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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9INIZ-scheda">
	<c:set var="key" value='${key}' scope="request" />
	<c:set var="reindi" value="${param.jspPath}"></c:set>
	<c:choose>
		<c:when test='${fn:indexOf(reindi, "a06") >= "0"}'>
			<jsp:forward page="w9iniz-scheda-a06.jsp" />
		</c:when>
		<c:when test='${fn:indexOf(reindi, "a07") >= "0"}'>
			<jsp:forward page="w9iniz-scheda-a07.jsp" />
		</c:when>
		<c:when test='${fn:indexOf(reindi, "a20") >= "0"}'>
			<jsp:forward page="w9iniz-scheda-a20.jsp" />
		</c:when>
	</c:choose>
</gene:template>
