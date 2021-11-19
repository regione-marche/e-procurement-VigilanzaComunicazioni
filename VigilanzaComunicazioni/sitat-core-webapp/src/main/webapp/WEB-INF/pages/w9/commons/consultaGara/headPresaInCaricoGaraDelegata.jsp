<%
/*
 * Created on 10-nov-2010
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
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/controlliFormali.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/forms.js"></script>

<script type="text/javascript">
<!-- 

	function annulla() {
		document.location.href="${pageContext.request.contextPath}/w9/ConsultaGara.do?" + csrfToken + "&metodo=inizializza";
	}

	function gestisciSubmit() {
<c:choose>
	<c:when test='${! empty requestScope.listaTecnici}'>
		var codiceTecnico = document.getElementById("codiceTecnico").value;
		if (codiceTecnico == "") {
			alert("Per continuare selezionare un tecnico");
		} else {
			bloccaRichiesteServer();
			document.formConsultaGara.submit();
		}
	</c:when>
	<c:when test='${! empty requestScope.listaCodiciDenominazione}'>
		var codice = document.getElementById("centroCosto").value;
		if (codice == "") {
			alert("Per continuare selezionare un centro di costo");
		} else {
			bloccaRichiesteServer();
			document.formConsultaGara.submit();
		}
	</c:when>
	<c:otherwise>
		bloccaRichiesteServer();
		document.formConsultaGara.submit();
	</c:otherwise>
</c:choose>
	}

	<c:if test="${! empty requestScope.passaAlCentroDiCosto}">
	$(document).ready(function() {
		bloccaRichiesteServer();
		setTimeout("document.formConsultaGara.submit();", 350);
	});
	</c:if>
	
-->	
</script>