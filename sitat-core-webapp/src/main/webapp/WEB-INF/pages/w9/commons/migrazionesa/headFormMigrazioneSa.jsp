<%
/*
 * Created on 11-apr-2017
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>

<gene:historyAdd titolo='Migra gara' id='migraGara' replaceParam='' />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/controlliFormali.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/forms.js"></script>

<script type="text/javascript">
<!--

	$(document).ready(function() {
		initPagina();
	});

	function initPagina() {
	<c:choose>
		<c:when test="${not empty sessionScope.MIGRAZIONE_SA_BEAN.errore}" >	
			document.formMigrazioneSA.metodo.value="annulla";
		</c:when>
		<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareRecuperate lt sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}">
			document.formMigrazioneSA.metodo.value="scaricaXml";
			document.formMigrazioneSA.submit();
		</c:when>
		<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareRecuperate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare and
					sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate lt sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}">
			document.formMigrazioneSA.metodo.value="controlliPreliminari";
			document.formMigrazioneSA.submit();
		</c:when>
		<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare and
						sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate lt sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}">
			document.formMigrazioneSA.metodo.value="eseguiMigrazione";
		</c:when>
		<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}">
			document.formMigrazioneSA.metodo.value="annulla";  // 1
			document.formMigrazioneSA.submit();
		</c:when>
		<c:otherwise>
			document.formMigrazioneSA.metodo.value="annulla";  // 2
			document.formMigrazioneSA.submit();
		</c:otherwise>
	</c:choose>
	}
  
	function eseguiMigrazione() {
		document.formMigrazioneSA.submit();
	}
	
  function annulla() {
	  <c:if test="${empty sessionScope.MIGRAZIONE_SA_BEAN.errore}" >
	  if (confirm("Confermi l'interruzione dell'operazione di migrazione stazioni appaltanti?")) {
		</c:if>
			document.location.href = "${pageContext.request.contextPath}/w9/MigraSA.do?" + csrfToken + "&metodo=annulla";
		<c:if test="${empty sessionScope.MIGRAZIONE_SA_BEAN.errore}" >
	  }
	  </c:if>
  }

  function esci() {
	  document.location.href = "${pageContext.request.contextPath}/w9/MigraSA.do?" + csrfToken + "&metodo=annulla";
  }
-->
</script>